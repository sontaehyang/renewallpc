package saleson.shop.order.pg;

import com.lgcns.kmpay.dto.DealApproveDto;
import com.lgcns.kmpay.service.CallWebService;
import kr.co.lgcns.module.lite.CnsPayWebConnector;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.kakaopay.domain.KakaopayInfo;
import saleson.shop.order.support.OrderException;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("kakaopayService")
public class KakaopayServiceImpl implements PgService, KakaopayInfoService {

	@Autowired
	Environment environment;

	private static final Logger log = LoggerFactory.getLogger(KakaopayServiceImpl.class);

	// 응답구분
	private static final String RESPONSE_PAY = "pay";
	private static final String RESPONSE_CANCEL = "cancel";

	// 취소구분
	private static final String CANCEL_ALL = "0";
	private static final String CANCEL_PART = "1";

	@Override
	public String getPayType(String payType) {

		return null;
	}

	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) {
		
		// 서버로부터 받은 결과값 저장 JSONObject
	    JSONObject  resultJSONObject =  new JSONObject();
	    
		// 가맹점에서 MPay로 전문을 보내기 위한 객체 생성
	    // 타임아웃 설정
	    int timeOut = 20;
	    CallWebService webService = new CallWebService(timeOut);
	    
	    // 전문 Parameter DTO 객체 생성
	    //DealApproveDto approveDto = new DealApproveDto();
	    DealApproveDto approveDto = (DealApproveDto) data;
	    
	    
	    try {
			resultJSONObject = webService.requestDealApprove(approveDto);
		} catch (Exception e) {
			log.error("kakaopay init(..) Error", e);
			throw new OrderException(e.getMessage());
		}
	    
	    
	    String resultString = resultJSONObject.toString();
	    String resultCode = "";
	    String resultMsg = "";
	    String txnId = "";
	    String merchantTxnNum = "";
	    String prDt = "";
	    
	    if( !resultString.equals("{}") ) {
	        resultCode = resultJSONObject.getString("RESULT_CODE");
	        resultMsg = resultJSONObject.getString("RESULT_MSG");
	        
	        if( resultCode.equals("00") ) {
	            txnId = resultJSONObject.getString("TXN_ID");
	            merchantTxnNum = resultJSONObject.getString("MERCHANT_TXN_NUM");
	            prDt = resultJSONObject.getString("PR_DT");
	        }
	    }
	    
	    // 결과값.
	    Map<String, String> txnResponse = new HashMap<>();
	    txnResponse.put("resultString", resultString);
	    txnResponse.put("resultCode", resultCode);
	    txnResponse.put("resultMsg", resultMsg);
	    txnResponse.put("txnId", txnId);
	    txnResponse.put("merchantTxnNum", merchantTxnNum);
	    txnResponse.put("prDt", prDt);
	    
	    
	    // 위변조 처리
	    String EdiDate = getyyyyMMddHHmmss(); // 전문생성일시

		//결제요청용 키값
		String md_src = null;
		try {
			md_src = EdiDate + approveDto.getMerchantID() + approveDto.getAmount();
		} catch (Exception e) {
			log.error("KakaopayServiceImpl : {}", e.getMessage(), e);
		}
		
		String hash_String  = SHA256Salt(md_src, environment.getProperty("kakaopay.encode.key"));
		
		txnResponse.put("EdiDate", EdiDate);
		txnResponse.put("EncryptData", hash_String);
		
	    
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("txnRequest", approveDto);
	    map.put("txnResponse", txnResponse);
		return map;
	}

	@Override
	public OrderPgData pay(Object data, HttpSession session) {
		PgData pgData = (PgData) data;
		HttpServletRequest request = pgData.getRequest();
		
		String encodeKey = environment.getProperty("kakaopay.encode.key");
		String kakaopayLogHome = environment.getProperty("kakaopay.log.path");
		String kakaopayCnspayHome = environment.getProperty("kakaopay.cnspay.path");
		
		// 모듈이 설치되어 있는 경로 설정
		CnsPayWebConnector connector = new CnsPayWebConnector();
		
		// 1. 로그 디렉토리 생성 : cnsPayHome/log 로 생성
		connector.setLogHome(kakaopayLogHome);
		connector.setCnsPayHome(kakaopayCnspayHome);
		
		// 2. 요청 페이지 파라메터 셋팅
		connector.setRequestData(request);
		
		// 3. 추가 파라메터 셋팅
		connector.addRequestData("actionType", "PY0");              // actionType : CL0 취소, PY0 승인
		connector.addRequestData("MallIP", saleson.common.utils.CommonUtils.getClientIp(request));// 가맹점 고유 ip
		
		//가맹점키 셋팅 (MID 별로 틀림) 
		connector.addRequestData("EncodeKey", encodeKey);
		
		// 4. CNSPAY Lite 서버 접속하여 처리
		connector.requestAction();
		
		// 5. 결과 처리
		String resultCode = connector.getResultData("ResultCode");      // 결과코드 (정상 :3001 , 그 외 에러)
		String resultMsg = connector.getResultData("ResultMsg");        // 결과메시지
		String payMethod = connector.getResultData("PayMethod");        // 결제수단
		String tid = connector.getResultData("TID");                    // 거래ID
		String authCode = connector.getResultData("AuthCode");          // 승인번호
		String mid = connector.getResultData("MID");                    // 가맹점ID
		String amt = connector.getResultData("Amt");                    // 금액
		String ccPartCl = connector.getResultData("CcPartCl");        	// 부분취소 가능여부 (0:불가, 1:가능)
		
		
		/*
		String resultCode = connector.getResultData("ResultCode");      // 결과코드 (정상 :3001 , 그 외 에러)
		String resultMsg = connector.getResultData("ResultMsg");        // 결과메시지
		String authDate = connector.getResultData("AuthDate");          // 승인일시 YYMMDDHH24mmss
		String authCode = connector.getResultData("AuthCode");          // 승인번호
		String buyerName = connector.getResultData("BuyerName");        // 구매자명
		String goodsName = connector.getResultData("GoodsName");        // 상품명
		String payMethod = connector.getResultData("PayMethod");        // 결제수단
		String mid = connector.getResultData("MID");                    // 가맹점ID
		String tid = connector.getResultData("TID");                    // 거래ID
		String moid = connector.getResultData("Moid");                  // 주문번호
		String amt = connector.getResultData("Amt");                    // 금액
		String cardCode = connector.getResultData("CardCode");          // 카드사 코드
		String cardName = connector.getResultData("CardName");          // 결제카드사명
		String cardQuota = connector.getResultData("CardQuota");        // 할부개월수 ex) 00:일시불,02:2개월
		String cardInterest = connector.getResultData("CardInterest");  // 무이자 여부 (0:일반, 1:무이자)
		String cardCl = connector.getResultData("CardCl");              // 체크카드여부 (0:일반, 1:체크카드)
		String cardBin = connector.getResultData("CardBin");            // 카드BIN번호
		String cardPoint = connector.getResultData("CardPoint");        // 카드사포인트사용여부 (0:미사용, 1:포인트사용, 2:세이브포인트사용)
		
		String ccPartCl = connector.getResultData("CcPartCl");        // 부분취소 가능여부 (0:불가, 1:가능)
		
		//부인방지토큰값
		String nonRepToken = request.getParameter("NON_REP_TOKEN");
		*/
		
		boolean paySuccess = false;                                     // 결제 성공 여부
		
		/** 위의 응답 데이터 외에도 전문 Header와 개별부 데이터 Get 가능 */
		if(payMethod.equals("CARD")){                                   //신용카드
			if(resultCode.equals("3001")) paySuccess = true;            // 결과코드 (정상 :3001 , 그 외 에러)
		}
		
		OrderPgData orderPgData = new OrderPgData();
		if (paySuccess){
			// 결제 성공
			orderPgData.setSuccess(true);
			orderPgData.setPgKey(tid);
			orderPgData.setPgAuthCode(authCode);
			orderPgData.setPgProcInfo(getPgProcInfo(connector, RESPONSE_PAY));
			orderPgData.setPgPaymentType(payMethod);
			orderPgData.setPgServiceMid(mid);
			orderPgData.setPgServiceKey(encodeKey);
			orderPgData.setPartCancelFlag("1".equals(ccPartCl) ? "Y" : "N");
			orderPgData.setPgAmount(Integer.parseInt(amt));
			orderPgData.setMessage(resultMsg);
			
		} else{
			// 결제 실패
			orderPgData.setSuccess(false);
			orderPgData.setMessage(resultMsg);
		}
		
		return orderPgData;
	}

	@Override
	public boolean cancel(OrderPgData orderPgData) {
		// 결제 정보 조회 - 필요한 경우 사용
		// KakaopayInfo kakaopayInfo = getPayInfo(orderPgData.getPgKey(), "");
		
		return cancelKakaopay(orderPgData, CANCEL_ALL); 
	}
	
	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) {
		// 결제 정보 조회 - 필요한 경우 사용
		//KakaopayInfo kakaopayInfo = getPayInfo(orderPgData.getPgKey(), "");

		cancelKakaopay(orderPgData, CANCEL_PART); 
		return orderPgData;
	}
	

	@Override
	public String confirmationOfPayment(PgData pgData) {

		return null;
	}

    @Override
    public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam) {
        return null;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {
        return null;
    }

    /**
	 * 결제 취소 처리 (전체, 부분취소)
	 * @param orderPgData
	 * @param partialCancelCode 취소구분 (0: 전체, 1: 부분취소)
	 * @return
	 */
	private boolean cancelKakaopay(OrderPgData orderPgData, String partialCancelCode) {
		orderPgData.setSuccess(false);
		orderPgData.setErrorMessage("PG 데이터가 넘어오지 않았습니다.");
		
		String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
		
		String encodeKey = environment.getProperty("kakaopay.encode.key");
		String kakaopayLogHome = environment.getProperty("kakaopay.log.path");
		String kakaopayCnspayHome = environment.getProperty("kakaopay.cnspay.path");
		
		String MID = environment.getProperty("kakaopay.mid");
		String CancelAmt = Integer.toString(orderPgData.getRemainAmount());			// 취소금액
	    
		

	    ////////위변조 처리/////////
	    //결제 취소 요청용 키값
	    String md_src = ediDate + MID + CancelAmt;
	    String hash_String = SHA256Salt(md_src, encodeKey);

	    CnsPayWebConnector connector = new CnsPayWebConnector();

	    // 1. 로그 디렉토리 생성
	    connector.setLogHome(kakaopayLogHome);
	    connector.setCnsPayHome(kakaopayCnspayHome);

	    // 2. 요청 페이지 파라메터 셋팅
	    // connector.setRequestData(request);
	    connector.addRequestData("MID", MID);
	    connector.addRequestData("TID", orderPgData.getPgKey());
		connector.addRequestData("CancelAmt", CancelAmt);
		connector.addRequestData("SupplyAmt", "0");
		connector.addRequestData("GoodsVat", "0");
		connector.addRequestData("ServiceAmt", "0");
		connector.addRequestData("CancelMsg", orderPgData.getCancelReason());
		connector.addRequestData("PartialCancelCode", partialCancelCode);
		connector.addRequestData("CancelNo", "");
		connector.addRequestData("CheckRemainAmt", "");
		connector.addRequestData("PreCancelCode", "");
	    

	    // 3. 추가 파라메터 셋팅
	    connector.addRequestData("actionType", "CL0");
	    connector.addRequestData("EdiDate", ediDate);
	    connector.addRequestData("EncryptData", hash_String);
	    connector.addRequestData("EncodeKey", encodeKey);
	    connector.addRequestData("CancelIP", "");

	    // 4. CNSPAY Lite 서버 접속하여 처리
	    connector.requestAction();

	    // 5. 결과 처리
	    String resultCode = connector.getResultData("ResultCode"); // 결과코드 (정상 :2001(취소성공), 그 외 에러)
	    String errorCD = connector.getResultData("ErrorCD"); // 에러코드
	    String errorMsg = connector.getResultData("ErrorMsg"); // 에러메시지
	    
	    /*
	    String resultMsg = connector.getResultData("ResultMsg"); // 결과메시지
	    String cancelAmt = connector.getResultData("CancelAmt"); // 취소금액
	    String cancelDate = connector.getResultData("CancelDate"); // 취소일
	    String cancelTime = connector.getResultData("CancelTime"); // 취소시간
	    String cancelNum = connector.getResultData("CancelNum"); // 취소번호
	    String payMethod = connector.getResultData("PayMethod"); // 취소 결제수단
	    String mid = connector.getResultData("MID"); // MID
	    String tid = connector.getResultData("TID"); // TID
	    String errorCD = connector.getResultData("ErrorCD"); // 에러코드
	    String errorMsg = connector.getResultData("ErrorMsg"); // 에러메시지
	    String AuthDate = cancelDate + cancelTime; // 취소거래시간
	    String stateCD = connector.getResultData("StateCD"); // 거래상태코드 (0: 승인, 1:전취소, 2:후취소)
	    String ccPartCl = connector.getResultData("CcPartCl"); // 부분취소 가능여부 (0:부분취소불가, 1:부분취소가능)
	    String PreCancelCode = connector.getResultData("PreCancelCode"); // 취소 종류
	    */
	    orderPgData.setPgProcInfo(getPgProcInfo(connector, RESPONSE_CANCEL));
	    
	    if ("2001".equals(resultCode)) {
	    	orderPgData.setSuccess(true);
	    	
	    	if (CANCEL_PART.equals(partialCancelCode)) {
	    		orderPgData.setPartCancelDetail("PART_CANCEL");
	    	}
	    	
	    	return true;
	    } else {
	    	orderPgData.setSuccess(false);
	    	orderPgData.setErrorMessage("[" + errorCD + "] " + errorMsg);
	    	return false;
	    }
	}
	
	
	/**
	 * 결제 로그 생성.
	 * @return
	 */
	private String getPgProcInfo(CnsPayWebConnector connector, String responseType) {
		String[] responsePayParameters = new String[] {
				 "PG",
				 "ResultCode",			// 결제결과코드 : 결제 성공 코드값 (신용카드 - 3001)
				 "ResultMsg",			// 결제결과메시지 : 예)카드 결제 성
				 "ErrorMsg",
				 "ErrorCD",
				 "AuthDate",			// 승인날짜
				 "AuthCode",			// 승인코드
				 "BuyerName",
				 "MallUserID",
				 "buyerName",
				 "mallUserID",
				 "GoodsName",
				 "PayMethod",
				 "MID",
				 "TID",					// 거래번호 : 거래를 구분하는 Transaction ID
				 "Moid",				// 가맹점 주문번호 : 가맹점에서 부여한 거래 주문번
				 "Amt",
				 "CardCode",
				 "CardName",
				 "CardNo",
				 "CardQuota",
				 "CardInterest",
				 "AcquCardCode",
				 "AcquCardName",
				 "CardCl",
				 "CardBin",
				 "CardPoint",
				 "PromotionCd",
				 "DiscountAmt",
				 "OsType",
				 "Imei",
				 "Uuid",
				 "UsimNo",
				 "ChannelType",
				 "MpayDeviceMac",
				 "TxnId",
				 "CcPartCl",			// 부분취소 가능여부 : 0:부분취소불가, 1: 부분취소가능 
				 "VanCode",
				 "FnNo"
		};
		
		String[] responseCancelParameters = new String[] {
				 "PG",
				 "ResultCode",			// 결제결과코드 : 결제 성공 코드값 (신용카드 - 3001)
				 "ResultMsg",			// 결제결과메시지 : 예)카드 결제 성
				 "CancelAmt",
				 "CancelDate",
				 "CancelTime",
				 "CancelNum",
				 "PayMethod",
				 "MID",
				 "TID",
				 "ErrorMsg",
				 "ErrorCD",
				 "StateCD",
				 "PromotionCd",
				 "RecoverYn",
				 "DiscountAmt",
				 "PreCancelCode",
				 "VanCode"
		};
		
		Map<String, String[]> responseData = new HashMap<String, String[]>();
		responseData.put(RESPONSE_PAY, responsePayParameters);
		responseData.put(RESPONSE_CANCEL, responseCancelParameters);
		

		StringBuilder sb = new StringBuilder();
		 
		for (String key : responseData.get(responseType)) {
			sb.append(key + " -> " + connector.getResultData(key) + "\n");
		}
		 
		return sb.toString();
	}
	
	
	/**
	 * 기준날짜에서 몇일 전,후의 날짜를 구한다.
	 * @param	sourceTS	기준날짜
	 * @param	day			변경할 일수
	 * @return	기준날짜에서 입력한 일수를 계산한 날짜
	 */
	public static Timestamp getTimestampWithSpan(Timestamp sourceTS, long day) throws Exception {
		Timestamp targetTS = null;

		if (sourceTS != null) {
			targetTS = new Timestamp(sourceTS.getTime() + (day * 1000 * 60 * 60 * 24));
		}

		return targetTS;
	}

	/**
	 * 현재날짜를 YYYYMMDDHHMMSS로 리턴
	 */
	public final synchronized String getyyyyMMddHHmmss(){
		/** yyyyMMddHHmmss Date Format */
		SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat(Const.DATETIME_FORMAT);

		return yyyyMMddHHmmss.format(new Date());
	}


	public class DataEncrypt {
		MessageDigest md;
		String strSRCData = "";
		String strENCData = "";
		String strOUTData = "";

		public DataEncrypt() {}

		public String encrypt(String strData) { // 암호화 시킬 데이터
			try {
				MessageDigest md = MessageDigest.getInstance("MD5"); // "MD5 형식으로 암호화"
				md.reset();
				//byte[] bytData = strData.getBytes();
				//md.update(bytData);
				md.update(strData.getBytes());
				byte[] digest = md.digest();
	
				StringBuffer hashedpasswd = new StringBuffer();
				String hx;
	
				for (int i=0;i<digest.length;i++){
					hx =  Integer.toHexString(0xFF & digest[i]);
					//0x03 is equal to 0x3, but we need 0x03 for our md5sum
					if(hx.length() == 1){hx = "0" + hx;}
					hashedpasswd.append(hx);
	
				}
				strOUTData = hashedpasswd.toString();
				byte[] raw = strOUTData.getBytes();
				byte[] encodedBytes = Base64.encodeBase64(raw);
				strOUTData = new String(encodedBytes);
		   }
		   catch (NoSuchAlgorithmException e) {
			   log.error("암호화 에러: {}", e.getMessage(), e);
		   }
	
	
			return strOUTData;  // 암호화된 데이터를 리턴...
		}

		public String SHA256(String strData) { // 암호화 시킬 데이터
			String SHA = "";
			try{
				MessageDigest sh = MessageDigest.getInstance("SHA-256");
				sh.update(strData.getBytes());
				byte byteData[] = sh.digest();
				StringBuffer sb = new StringBuffer();
				for(int i = 0 ; i < byteData.length ; i++){
					sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

				}
				SHA = sb.toString();
				byte[] raw = SHA.getBytes();
				byte[] encodedBytes = Base64.encodeBase64(raw);
				SHA = new String(encodedBytes);
			} catch (NoSuchAlgorithmException e) {
				log.error("ERROR: {}", e.getMessage(), e);
				SHA = null;
			}
			return SHA;
		}
	} // end class

	public String SHA256Salt(String strData, String salt) { 
		String SHA = "";
		  
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.reset();
			sh.update(salt.getBytes());
			byte byteData[] = sh.digest(strData.getBytes());
			
			//Hardening against the attacker's attack
			sh.reset();
			byteData = sh.digest(byteData);
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

			}
			
			SHA = sb.toString();
			byte[] raw = SHA.getBytes();
			byte[] encodedBytes = Base64.encodeBase64(raw);
			SHA = new String(encodedBytes);
		} catch(NoSuchAlgorithmException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			SHA = null;
		}
		
		return SHA;
	}

	@Override
	public KakaopayInfo getPayInfo(String TID, String CancelNo) {
		String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
		
		String encodeKey = environment.getProperty("kakaopay.encode.key");
		String kakaopayLogHome = environment.getProperty("kakaopay.log.path");
		String kakaopayCnspayHome = environment.getProperty("kakaopay.cnspay.path");
		
		String MID = environment.getProperty("kakaopay.mid");
	    
	    //상품가격 - hash 생성 시 필요하므로 화면로딩시 가지고 있어야 하는 값 
	    
	    ////////위변조 처리/////////
	    //결제요청용 키값
	    String md_src = ediDate + MID + TID;
	    String hash_String  = SHA256Salt(md_src, encodeKey);

	    CnsPayWebConnector connector = new CnsPayWebConnector();

	    connector.setLogHome(kakaopayLogHome);
	    connector.setCnsPayHome(kakaopayCnspayHome);
	    
	    //1. 요청 페이지 파라메터 셋팅
	    connector.addRequestData("actionType", "CI0");
	    connector.addRequestData("PayMethod", "TID_INFO");
	    
	    connector.addRequestData("EdiDate", ediDate);
	    connector.addRequestData("EncryptData", hash_String);
	    connector.addRequestData("EncodeKey", encodeKey);
	    connector.addRequestData("TID", TID);
	    connector.addRequestData("MID", MID);
	    connector.addRequestData("CancelNo", CancelNo);
	    
	    //2. CNSPAY Lite 서버 접속하여 처리
	    connector.requestAction();  
	    
	    //3. 결과 처리
	    boolean paySuccess = false;     // 결제 상태 조회 성공 여부
	    String resultCode = connector.getResultData("ResultCode"); // 결과코드 (정상 :00 , 그 외 에러)
	    String resultMsg = connector.getResultData("ResultMsg");   // 결과메시지
	    String StateCd = connector.getResultData("StateCd");   // 결과메시지
	    
	    if (resultCode != null) {
	        if(resultCode.equals("00")) {
	            paySuccess = true;      // 결제 상태 조회 성공 여부
	        }       
	    }
	    
	    
	    // 변수들에 대한 한글 주석 추가 필요
	    KakaopayInfo kakaopayInfo = new KakaopayInfo();
	    
	    kakaopayInfo.setPaySuccess(paySuccess);
	    kakaopayInfo.setResultCode(resultCode);
	    kakaopayInfo.setResultMsg(resultMsg);
	    kakaopayInfo.setErrorCD(connector.getResultData("ErrorCD"));                  
	    kakaopayInfo.setErrorMsg(connector.getResultData("ErrorMsg"));                
	    
	    kakaopayInfo.setTID(connector.getResultData("TID"));                // 거래ID  
	    kakaopayInfo.setStateCd(StateCd);        							// 거래상태코드
	    kakaopayInfo.setAppAmt(connector.getResultData("AppAmt"));          // 승인금액
	    kakaopayInfo.setCcAmt(connector.getResultData("CcAmt"));            // 취소금액
	    kakaopayInfo.setRemainAmt(connector.getResultData("RemainAmt"));    // 승인잔액
	    kakaopayInfo.setCancelYn(connector.getResultData("CancelYn"));      // 요청 취소건 취소결과
	    
	    String StateNm = "";
	    
	    if (paySuccess) { 
	       // 결제 상태 조회 성공시 DB처리 하세요.
	        if (StateCd.equals("0")) {
	            StateNm = "승인";
	        } else if (StateCd.equals("1")) {
	            StateNm = "전체취소";
	        } else if (StateCd.equals("2")) {
	            StateNm = "부분취소";
	        }
	        kakaopayInfo.setStateNm(StateNm);
	        
	    } else {
	       // 결제 상태 조회 실패시 DB처리 하세요.
	    }
	    
	    return kakaopayInfo;
	}

	@Override
	public boolean delivery(HashMap<String, Object> paramMap) {

		return false;
	}
	@Override
	public boolean escrowConfirmPurchase(HttpServletRequest request) {

		return false;
	}
	@Override
	public boolean escrowDenyConfirm(List<String> param) {

		return false;
	}
}
