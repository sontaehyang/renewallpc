package saleson.shop.order.pg;

import lgdacom.XPayClient.XPayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.support.OrderException;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

@Service("lgDacomService")
public class LgDacomServiceImpl implements PgService {

	@Autowired
	Environment environment;

	private static final Logger log = LoggerFactory.getLogger(LgDacomServiceImpl.class);

	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) {
		HashMap<String, Object> payReqMap = new HashMap<>();
		/*
	     * [결제 인증요청 페이지(STEP2-1)]
	     *
	     * 샘플페이지에서는 기본 파라미터만 예시되어 있으며, 별도로 필요하신 파라미터는 연동메뉴얼을 참고하시어 추가 하시기 바랍니다.
	     */

	    /*
	     * 1. 기본결제 인증요청 정보 변경
	     *
	     * 기본정보를 변경하여 주시기 바랍니다.(파라미터 전달시 POST를 사용하세요)
	     */
	    String CST_PLATFORM         = environment.getProperty("pg.lgdacom.serviceType");                 //LG유플러스 결제서비스 선택(test:테스트, service:서비스)
	    String CST_MID              = environment.getProperty("pg.lgdacom.mid");                      //LG유플러스로 부터 발급받으신 상점아이디를 입력하세요.
	    String LGD_MID              = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;  //테스트 아이디는 't'를 제외하고 입력하세요.
	                                                                                        //상점아이디(자동생성)
	    String LGD_OID              = ((PgData) data).getOrderCode();                      //주문번호(상점정의 유니크한 주문번호를 입력하세요)
	    String LGD_AMOUNT           = ((PgData) data).getAmount();                   //결제금액("," 를 제외한 결제금액을 입력하세요)
	    String LGD_MERTKEY          = environment.getProperty("pg.lgdacom.key");                  									//상점MertKey(mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다)
	    String LGD_BUYER            = ((PgData) data).getLGD_BUYER();                    //구매자명
	    String LGD_PRODUCTINFO      = ((PgData) data).getLGD_PRODUCTINFO();              //상품명
	    String LGD_BUYEREMAIL       = ((PgData) data).getLGD_BUYEREMAIL();               //구매자 이메일
	    String LGD_TIMESTAMP        = Long.toString(System.currentTimeMillis() / 1000L);                //타임스탬프
	    String LGD_CUSTOM_USABLEPAY  = ((PgData) data).getLGD_CUSTOM_USABLEPAY();          //상점정의 초기결제수단
	    String LGD_CUSTOM_SKIN      = "red";                                                //상점정의 결제창 스킨(red, purple, yellow)
	    String LGD_WINDOW_VER       = "2.5";                                                //결제창 버젼정보


	    /*
	     * 가상계좌(무통장) 결제 연동을 하시는 경우 아래 LGD_CASNOTEURL 을 설정하여 주시기 바랍니다.
	     */
	    String LGD_CASNOTEURL		= environment.getProperty("saleson.url.shoppingmall") + "/order/lgdacom/note-url";

	    /*
	     * LGD_RETURNURL 을 설정하여 주시기 바랍니다. 반드시 현재 페이지와 동일한 프로트콜 및  호스트이어야 합니다. 아래 부분을 반드시 수정하십시요.
	     */
	    String LGD_RETURNURL		= environment.getProperty("saleson.url.shoppingmall") + "/order/lgdacom/return-url";// FOR MANUAL
	    if ("MOBILE".equals(((PgData) data).getDeviceType())) {
	    	LGD_RETURNURL		= environment.getProperty("saleson.url.shoppingmall") + "/m/order/lgdacom/return-url/" + ((PgData) data).getOrderCode();// FOR MANUAL
	    }
	    /*
	     *************************************************
	     * 2. MD5 해쉬암호화 (수정하지 마세요) - BEGIN
	     *
	     * MD5 해쉬암호화는 거래 위변조를 막기위한 방법입니다.
	     *************************************************
	     *
	     * 해쉬 암호화 적용( LGD_MID + LGD_OID + LGD_AMOUNT + LGD_TIMESTAMP + LGD_MERTKEY )
	     * LGD_MID          : 상점아이디
	     * LGD_OID          : 주문번호
	     * LGD_AMOUNT       : 금액
	     * LGD_TIMESTAMP    : 타임스탬프
	     * LGD_MERTKEY      : 상점MertKey (mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다)
	     *
	     * MD5 해쉬데이터 암호화 검증을 위해
	     * LG유플러스에서 발급한 상점키(MertKey)를 환경설정 파일(lgdacom/conf/mall.conf)에 반드시 입력하여 주시기 바랍니다.
	     */
	    StringBuffer sb = new StringBuffer();
	    sb.append(LGD_MID);
	    sb.append(LGD_OID);
	    sb.append(LGD_AMOUNT);
	    sb.append(LGD_TIMESTAMP);
	    sb.append(LGD_MERTKEY);

	    byte[] bNoti = sb.toString().getBytes();
	    MessageDigest md;
		
	    try {
			md = MessageDigest.getInstance("MD5");
		
			byte[] digest = md.digest(bNoti);

	    	StringBuffer strBuf = new StringBuffer();
	    	for (int i=0 ; i < digest.length ; i++) {
		        int c = digest[i] & 0xff;
		        if (c <= 15){
		            strBuf.append("0");
		        }
	        	strBuf.append(Integer.toHexString(c));
	    	}

	    	String LGD_HASHDATA = strBuf.toString();
	    	String LGD_CUSTOM_PROCESSTYPE = "TWOTR";
	    	
		    /*
		     *************************************************
		     * 2. MD5 해쉬암호화 (수정하지 마세요) - END
		     *************************************************
		     */

	     	String CST_WINDOW_TYPE = environment.getProperty("pg.lgdacom.viewType");	//수정불가
	     
	     	payReqMap.put("CST_PLATFORM"                , CST_PLATFORM);                   	// 테스트, 서비스 구분
	     	payReqMap.put("CST_MID"                     , CST_MID );                        	// 상점아이디
	     	payReqMap.put("CST_WINDOW_TYPE"             , CST_WINDOW_TYPE );                        	// 상점아이디
	     	payReqMap.put("LGD_MID"                     , LGD_MID );                        	// 상점아이디
	     	payReqMap.put("LGD_OID"                     , LGD_OID );                        	// 주문번호
	     	payReqMap.put("LGD_BUYER"                   , LGD_BUYER );                      	// 구매자
	     	payReqMap.put("LGD_PRODUCTINFO"             , LGD_PRODUCTINFO );                	// 상품정보
	     	payReqMap.put("LGD_AMOUNT"                  , LGD_AMOUNT );                     	// 결제금액
	     	payReqMap.put("LGD_BUYEREMAIL"              , LGD_BUYEREMAIL );                 	// 구매자 이메일
	     
	     	payReqMap.put("LGD_WINDOW_VER"              , LGD_WINDOW_VER );                	// 결제창 버젼정보
	     	payReqMap.put("LGD_CUSTOM_PROCESSTYPE"      , LGD_CUSTOM_PROCESSTYPE );         	// 트랜잭션 처리방식
	     	payReqMap.put("LGD_TIMESTAMP"               , LGD_TIMESTAMP );                  	// 타임스탬프
	     	payReqMap.put("LGD_HASHDATA"                , LGD_HASHDATA );      	           	// MD5 해쉬암호값
	     	payReqMap.put("LGD_RETURNURL"   			, LGD_RETURNURL );      			   	// 응답수신페이지
	     	payReqMap.put("LGD_CUSTOM_USABLEPAY"  		, LGD_CUSTOM_USABLEPAY );				// 디폴트 결제수단
	     	payReqMap.put("LGD_CUSTOM_SWITCHINGTYPE"  	, "SUBMIT" );							// 신용카드 카드사 인증 페이지 연동 방식

	     	if ("MOBILE".equals(((PgData) data).getDeviceType())) {
	     		payReqMap.put("LGD_CUSTOM_FIRSTPAY", LGD_CUSTOM_USABLEPAY); // 상점의 결제 수단
		     	payReqMap.put("LGD_VERSION"         		, "JSP_SmartXPay_1.0");
		     	LGD_CUSTOM_SKIN = "SMART_XPAY2";
	     	}
	     	
	    	payReqMap.put("LGD_CUSTOM_SKIN"             , LGD_CUSTOM_SKIN );                	// 결제창 SKIN
	     	
	     	// 가상계좌(무통장) 결제연동을 하시는 경우  할당/입금 결과를 통보받기 위해 반드시 LGD_CASNOTEURL 정보를 LG 유플러스에 전송해야 합니다 .
	      	payReqMap.put("LGD_CASNOTEURL"          , LGD_CASNOTEURL );               // 가상계좌 NOTEURL

	      	/*Return URL에서 인증 결과 수신 시 셋팅될 파라미터 입니다.*/
	      	payReqMap.put("LGD_RESPCODE"  		 , "" );
	      	payReqMap.put("LGD_RESPMSG"  		 , "" );
	      	payReqMap.put("LGD_PAYKEY"  		 , "" );

	      	// 면세금액
	      	payReqMap.put("LGD_TAXFREEAMOUNT", ((PgData) data).getTaxFreeAmount());

	      	// 사용하는 부분이 없음. - skc
	      	//session.setAttribute("PAYREQ_MAP", payReqMap);
		
		} catch (NoSuchAlgorithmException e) {
			log.error("init() :  {}", e.getMessage(), e);

		}
		
		return payReqMap;
	}

	@Override
	public OrderPgData pay(Object data, HttpSession session) {
		
		OrderPgData orderPgData = new OrderPgData();
		
		/*
	     * [최종결제요청 페이지(STEP2-2)]
	     *
	     * LG유플러스으로 부터 내려받은 LGD_PAYKEY(인증Key)를 가지고 최종 결제요청.(파라미터 전달시 POST를 사용하세요)
	     */

		/* ※ 중요
		* 환경설정 파일의 경우 반드시 외부에서 접근이 가능한 경로에 두시면 안됩니다.
		* 해당 환경파일이 외부에 노출이 되는 경우 해킹의 위험이 존재하므로 반드시 외부에서 접근이 불가능한 경로에 두시기 바랍니다. 
		* 예) [Window 계열] C:\inetpub\wwwroot\lgdacom ==> 절대불가(웹 디렉토리)
		*/
		
		String configPath = environment.getProperty("pg.lgdacom.home");  //LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf,/conf/mall.conf") 위치 지정.
	    
	    /*
	     *************************************************
	     * 1.최종결제 요청 - BEGIN
	     *  (단, 최종 금액체크를 원하시는 경우 금액체크 부분 주석을 제거 하시면 됩니다.)
	     *************************************************
	     */
	    
		String CST_PLATFORM                 = environment.getProperty("pg.lgdacom.serviceType");
		String CST_MID                      = environment.getProperty("pg.lgdacom.mid");
		String LGD_MID                      = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;
		String LGD_PAYKEY                   = ((PgData) data).getLGD_PAYKEY();
		
		//해당 API를 사용하기 위해 WEB-INF/lib/XPayClient.jar 를 Classpath 로 등록하셔야 합니다. 
		XPayClient xpay = new XPayClient();
		boolean isInitOK = xpay.Init(configPath, CST_PLATFORM);   	

		if ( !isInitOK ) {
			//API 초기화 실패 화면처리
		    //out.println( "결제요청을 초기화 하는데 실패하였습니다.<br>");
		    //out.println( "LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.<br>");        
		    //out.println( "mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.<br><br>");
		    //out.println( "문의전화 LG유플러스 1544-7772<br>");
		    //return;
		
		} else {      
			try {
	   			/*
	   	   	     *************************************************
	   	   	     * 1.최종결제 요청(수정하지 마세요) - END
	   	   	     *************************************************
	   	   	     */
				xpay.Init_TX(LGD_MID);
				xpay.Set("LGD_TXNAME", "PaymentByKey");
				xpay.Set("LGD_PAYKEY", LGD_PAYKEY);
		    
		    	//금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
		    	String DB_AMOUNT = ((PgData) data).getAmount(); //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
		    	xpay.Set("LGD_AMOUNTCHECKYN", "Y");
		    	xpay.Set("LGD_AMOUNT", DB_AMOUNT);
		    
	    	} catch(Exception e) {
				log.error("ERROR: {}", e.getMessage(), e);
	    		throw new OrderException("LG유플러스 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다. ");
	    	}
	   	}

	    /*
	     * 2. 최종결제 요청 결과처리
	     *
	     * 최종 결제요청 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
	     */
		if ( xpay.TX() ) {
	    	
			if( "0000".equals( xpay.m_szResCode ) ) {
				
				orderPgData.setSuccess(true);
				orderPgData.setPgKey(xpay.Response("LGD_TID",0));
				orderPgData.setPgAuthCode(xpay.m_szResCode);
				orderPgData.setPgProcInfo(this.makePgLog(xpay, ((PgData) data).getApprovalType()));
				orderPgData.setErrorMessage(xpay.Response("LGD_RESPMSG",0));
				
				// 신용카드 결제의 경우 부분취소 가능여부를 조회
				if ("card".equals(((PgData) data).getApprovalType()) || "SC0010".equals(((PgData) data).getApprovalType())) {
			    	orderPgData.setPartCancelFlag(xpay.Response("LGD_PCANCELFLAG",0));
			    	orderPgData.setPartCancelDetail(xpay.Response("LGD_PCANCELSTR",0));
				}
		    	
			} else {
				orderPgData.setSuccess(false);
				orderPgData.setErrorMessage(xpay.m_szResMsg);
			}
	         //1)결제결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
	         //out.println( "결제요청이 완료되었습니다.  <br>");
	         //out.println( "TX 결제요청 Response_code = " + xpay.m_szResCode + "<br>");
	         //out.println( "TX 결제요청 Response_msg = " + xpay.m_szResMsg + "<p>");
	         
	         //out.println("거래번호 : " + xpay.Response("LGD_TID",0) + "<br>");
	    	 //out.println("상점아이디 : " + xpay.Response("LGD_MID",0) + "<br>");
	         //out.println("상점주문번호 : " + xpay.Response("LGD_OID",0) + "<br>");
	         //out.println("결제금액 : " + xpay.Response("LGD_AMOUNT",0) + "<br>");
	         //out.println("결과코드 : " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	         //out.println("결과메세지 : " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         /*
	         for (int i = 0; i < xpay.ResponseNameCount(); i++)
	         {
	             out.println(xpay.ResponseName(i) + " = ");
	             for (int j = 0; j < xpay.ResponseCount(); j++)
	             {
	                 out.println("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
	             }
	         }
	         out.println("<p>");
	         
	         if( "0000".equals( xpay.m_szResCode ) ) {
	         	//최종결제요청 결과 성공 DB처리
	         	out.println("최종결제요청 결과 성공 DB처리하시기 바랍니다.<br>");
	         	            	
	         	//최종결제요청 결과 성공 DB처리 실패시 Rollback 처리
	         	boolean isDBOK = true; //DB처리 실패시 false로 변경해 주세요.
	         	if( !isDBOK ) {
	         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
	         		
	                 out.println( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	                 out.println( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         		
	                 if( "0000".equals( xpay.m_szResCode ) ) {
	                 	out.println("자동취소가 정상적으로 완료 되었습니다.<br>");
	                 }else{
	         			out.println("자동취소가 정상적으로 처리되지 않았습니다.<br>");
	                 }
	         	}
	         	
	         }else{
	         	//최종결제요청 결과 실패 DB처리
	         	out.println("최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");            	
	         }
	         */
		} else {
			
			orderPgData.setSuccess(false);
			orderPgData.setErrorMessage(xpay.m_szResMsg);
			
			//2)API 요청실패 화면처리
			//out.println( "결제요청이 실패하였습니다.  <br>");
			//out.println( "TX 결제요청 Response_code = " + xpay.m_szResCode + "<br>");
			//out.println( "TX 결제요청 Response_msg = " + xpay.m_szResMsg + "<p>");
			 
			//최종결제요청 결과 실패 DB처리
			//out.println("최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");            	            
		}
	     
		return orderPgData;
	}

	@Override
	public boolean cancel(OrderPgData orderPgData) {
	    /*
	     * [결제취소 요청 페이지]
	     *
	     * LG유플러스으로 부터 내려받은 거래번호(LGD_TID)를 가지고 취소 요청을 합니다.(파라미터 전달시 POST를 사용하세요)
	     * (승인시 LG유플러스으로 부터 내려받은 PAYKEY와 혼동하지 마세요.)
	     */
	    String CST_PLATFORM         = environment.getProperty("pg.lgdacom.serviceType");                 //LG유플러스 결제서비스 선택(test:테스트, service:서비스)
	    String CST_MID              = environment.getProperty("pg.lgdacom.mid");                      //LG유플러스으로 부터 발급받으신 상점아이디를 입력하세요.
	    String LGD_MID              = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;  //테스트 아이디는 't'를 제외하고 입력하세요.
	                                                                                        //상점아이디(자동생성)
	    String LGD_TID              = orderPgData.getPgKey();                      //LG유플러스으로 부터 내려받은 거래번호(LGD_TID)

		/* ※ 중요
		* 환경설정 파일의 경우 반드시 외부에서 접근이 가능한 경로에 두시면 안됩니다.
		* 해당 환경파일이 외부에 노출이 되는 경우 해킹의 위험이 존재하므로 반드시 외부에서 접근이 불가능한 경로에 두시기 바랍니다. 
		* 예) [Window 계열] C:\inetpub\wwwroot\lgdacom ==> 절대불가(웹 디렉토리)
		*/
	    String configPath 			= environment.getProperty("pg.lgdacom.home");  										//LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf") 위치 지정.
	        
	    LGD_TID     				= ( LGD_TID == null )?"":LGD_TID; 
	    
		XPayClient xpay = new XPayClient();
		xpay.Init(configPath, CST_PLATFORM);
		xpay.Init_TX(LGD_MID);
		xpay.Set("LGD_TXNAME", "Cancel");
		xpay.Set("LGD_TID", LGD_TID);
	 
	    /*
	     * 1. 결제취소 요청 결과처리
	     *
	     * 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
		 *
		 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
		 * 1. 신용카드 : 0000, AV11  
		 * 2. 계좌이체 : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
		 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
		 *
	     */
		
		boolean isSuccess = false;
		if (xpay.TX()) {
			isSuccess = true;
		}
		
		return isSuccess;
	}

	private String makePgLog(XPayClient xpay, String approvalType) {
		StringBuffer sb = new StringBuffer();
		
		String[] keys = new String[]{
			"LGD_RESPCODE", "LGD_RESPMSG", "LGD_AMOUNT", "LGD_PAYTYPE", "LGD_PAYDATE", "LGD_FINANCECODE", "LGD_FINANCENAME", "LGD_ESCROWYN",
			"LGD_TRANSAMOUNT", "LGD_EXCHANGERATE", "LGD_CARDINSTALLMONTH", "LGD_CARDNOINTYN" 
		};
		
		if (approvalType.equals("vbank")) {
			keys = new String[]{
				"LGD_RESPCODE", "LGD_RESPMSG", "LGD_AMOUNT", "LGD_PAYTYPE", "LGD_PAYDATE", "LGD_FINANCECODE", "LGD_FINANCENAME", "LGD_ESCROWYN",
				"LGD_TRANSAMOUNT", "LGD_EXCHANGERATE", "LGD_CASHRECEIPTNUM", "LGD_CASHRECEIPTSELFYN", "LGD_CASHRECEIPTKIND"
			};
		}
		
		for(String key : keys) {
			String value = xpay.Response(key,0);
			sb.append(key + " -> " + value + "\n");
		}
		
		return sb.toString();
	}
	
	
	@Override
	/**
	 * 결제 수단 코드
	 * SC0010 : 신용카드
	 * SC0030 : 계좌이체
	 * SC0040 : 무통장입금
	 * SC0060 : 휴대폰
	 * SC0070 : 유선전화결제
	 * SC0090 : OK캐쉬백
	 * SC0111 : 문화상품권
	 * SC0112 : 게임문화 상품권
	 */
	public String getPayType(String payType) {
		if ("card".equals(payType)) {
			return "SC0010";
		} else if ("vbank".equals(payType)) {
			return "SC0030";
		}
		
		return "";
	}

	@Override
	public String confirmationOfPayment(PgData pgData) {
		
	    /*
	     * [상점 결제결과처리(DB) 페이지]
	     *
	     * 1) 위변조 방지를 위한 hashdata값 검증은 반드시 적용하셔야 합니다.
	     *
	     */

	    String LGD_RESPCODE = "";           // 응답코드: 0000(성공) 그외 실패
	    String LGD_RESPMSG = "";            // 응답메세지
	    String LGD_MID = "";                // 상점아이디 
	    String LGD_OID = "";                // 주문번호
	    String LGD_AMOUNT = "";             // 거래금액
	    String LGD_TID = "";                // LG유플러스에서 부여한 거래번호
	    String LGD_PAYTYPE = "";            // 결제수단코드
	    String LGD_PAYDATE = "";            // 거래일시(승인일시/이체일시)
	    String LGD_HASHDATA = "";           // 해쉬값
	    String LGD_FINANCECODE = "";        // 결제기관코드(은행코드)
	    String LGD_FINANCENAME = "";        // 결제기관이름(은행이름)
	    String LGD_ESCROWYN = "";           // 에스크로 적용여부
	    String LGD_TIMESTAMP = "";          // 타임스탬프
	    String LGD_ACCOUNTNUM = "";         // 계좌번호(무통장입금) 
	    String LGD_CASTAMOUNT = "";         // 입금총액(무통장입금)
	    String LGD_CASCAMOUNT = "";         // 현입금액(무통장입금)
	    String LGD_CASFLAG = "";            // 무통장입금 플래그(무통장입금) - 'R':계좌할당, 'I':입금, 'C':입금취소 
	    String LGD_CASSEQNO = "";           // 입금순서(무통장입금)
	    String LGD_CASHRECEIPTNUM = "";     // 현금영수증 승인번호
	    String LGD_CASHRECEIPTSELFYN = "";  // 현금영수증자진발급제유무 Y: 자진발급제 적용, 그외 : 미적용
	    String LGD_CASHRECEIPTKIND = "";    // 현금영수증 종류 0: 소득공제용 , 1: 지출증빙용
	    String LGD_PAYER = "";    			// 임금자명
	    
	    /*
	     * 구매정보
	     */
	    String LGD_BUYER = "";              // 구매자
	    String LGD_PRODUCTINFO = "";        // 상품명
	    String LGD_BUYERID = "";            // 구매자 ID
	    String LGD_BUYERADDRESS = "";       // 구매자 주소
	    String LGD_BUYERPHONE = "";         // 구매자 전화번호
	    String LGD_BUYEREMAIL = "";         // 구매자 이메일
	    String LGD_BUYERSSN = "";           // 구매자 주민번호
	    String LGD_PRODUCTCODE = "";        // 상품코드
	    String LGD_RECEIVER = "";           // 수취인
	    String LGD_RECEIVERPHONE = "";      // 수취인 전화번호
	    String LGD_DELIVERYINFO = "";       // 배송지

	    LGD_RESPCODE            = pgData.getLGD_RESPCODE();
	    LGD_RESPMSG             = pgData.getLGD_RESPMSG();
	    LGD_MID                 = pgData.getLGD_MID();
	    LGD_OID                 = pgData.getLGD_OID();
	    LGD_AMOUNT              = pgData.getLGD_AMOUNT();
	    LGD_TID                 = pgData.getLGD_TID();
	    LGD_PAYTYPE             = pgData.getLGD_PAYTYPE();
	    LGD_PAYDATE             = pgData.getLGD_PAYDATE();
	    LGD_HASHDATA            = pgData.getLGD_HASHDATA();
	    LGD_FINANCECODE         = pgData.getLGD_FINANCECODE();
	    LGD_FINANCENAME         = pgData.getLGD_FINANCENAME();
	    LGD_ESCROWYN            = pgData.getLGD_ESCROWYN();
	    LGD_TIMESTAMP           = pgData.getLGD_TIMESTAMP();
	    LGD_ACCOUNTNUM          = pgData.getLGD_ACCOUNTNUM();
	    LGD_CASTAMOUNT          = pgData.getLGD_CASTAMOUNT();
	    LGD_CASCAMOUNT          = pgData.getLGD_CASCAMOUNT();
	    LGD_CASFLAG             = pgData.getLGD_CASFLAG();
	    LGD_CASSEQNO            = pgData.getLGD_CASSEQNO();
	    LGD_CASHRECEIPTNUM      = pgData.getLGD_CASHRECEIPTNUM();
	    LGD_CASHRECEIPTSELFYN   = pgData.getLGD_CASHRECEIPTSELFYN();
	    LGD_CASHRECEIPTKIND     = pgData.getLGD_CASHRECEIPTKIND();
	    LGD_PAYER     			= pgData.getLGD_PAYER();

	    LGD_BUYER               = pgData.getLGD_BUYER();
	    LGD_PRODUCTINFO         = pgData.getLGD_PRODUCTINFO();
	    LGD_BUYERID             = pgData.getLGD_BUYERID();
	    LGD_BUYERADDRESS        = pgData.getLGD_BUYERADDRESS();
	    LGD_BUYERPHONE          = pgData.getLGD_BUYERPHONE();
	    LGD_BUYEREMAIL          = pgData.getLGD_BUYEREMAIL();
	    LGD_BUYERSSN            = pgData.getLGD_BUYERSSN();
	    LGD_PRODUCTCODE         = pgData.getLGD_PRODUCTCODE();
	    LGD_RECEIVER            = pgData.getLGD_RECEIVER();
	    LGD_RECEIVERPHONE       = pgData.getLGD_RECEIVERPHONE();
	    LGD_DELIVERYINFO        = pgData.getLGD_DELIVERYINFO();

	    /*
	     * hashdata 검증을 위한 mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다. 
	     * LG유플러스에서 발급한 상점키로 반드시변경해 주시기 바랍니다.
	     */  
	    String LGD_MERTKEY = environment.getProperty("pg.lgdacom.key"); //mertkey

	    StringBuffer sb = new StringBuffer();
	    sb.append(LGD_MID);
	    sb.append(LGD_OID);
	    sb.append(LGD_AMOUNT);
	    sb.append(LGD_RESPCODE);
	    sb.append(LGD_TIMESTAMP);
	    sb.append(LGD_MERTKEY);

	    byte[] bNoti = sb.toString().getBytes();
	    MessageDigest md;
	    String resultMSG = "결제결과 상점 DB처리(LGD_CASNOTEURL) 결과값을 입력해 주시기 바랍니다.";
	    
		try {
			md = MessageDigest.getInstance("MD5");
		
		    byte[] digest = md.digest(bNoti);
	
		    StringBuffer strBuf = new StringBuffer();
		    for (int i=0 ; i < digest.length ; i++) {
		        int c = digest[i] & 0xff;
		        if (c <= 15){
		            strBuf.append("0");
		        }
		        strBuf.append(Integer.toHexString(c));
		    }
	
		    String LGD_HASHDATA2 = strBuf.toString();  //상점검증 해쉬값  
		    
		    /*
		     * 상점 처리결과 리턴메세지
		     *
		     * OK  : 상점 처리결과 성공
		     * 그외 : 상점 처리결과 실패
		     *
		     * ※ 주의사항 : 성공시 'OK' 문자이외의 다른문자열이 포함되면 실패처리 되오니 주의하시기 바랍니다.
		     */    
		      
		    
		    if (LGD_HASHDATA2.trim().equals(LGD_HASHDATA)) { //해쉬값 검증이 성공이면
		        if ( ("0000".equals(LGD_RESPCODE.trim())) ){ //결제가 성공이면
		        	if( "R".equals( LGD_CASFLAG.trim() ) ) {
		                /*
		                 * 무통장 할당 성공 결과 상점 처리(DB) 부분
		                 * 상점 결과 처리가 정상이면 "OK"
		                 */    
		                //if( 무통장 할당 성공 상점처리결과 성공 ) 
		                	resultMSG = "OK";   
		        		
		        	}else if( "I".equals( LGD_CASFLAG.trim() ) ) {
		 	            /*
		    	         * 무통장 입금 성공 결과 상점 처리(DB) 부분
		        	     * 상점 결과 처리가 정상이면 "OK"
		            	 */    
		            	//if( 무통장 입금 성공 상점처리결과 성공 ) 
		            		resultMSG = "OK";
		        	}else if( "C".equals( LGD_CASFLAG.trim() ) ) {
		 	            /*
		    	         * 무통장 입금취소 성공 결과 상점 처리(DB) 부분
		        	     * 상점 결과 처리가 정상이면 "OK"
		            	 */    
		            	//if( 무통장 입금취소 성공 상점처리결과 성공 ) 
		            		resultMSG = "OK";
		        	}
		        } else { //결제가 실패이면
		            /*
		             * 거래실패 결과 상점 처리(DB) 부분
		             * 상점결과 처리가 정상이면 "OK"
		             */  
		           //if( 결제실패 상점처리결과 성공 ) 
		        	   resultMSG = "OK";     
		        }
		    } else { //해쉬값이 검증이 실패이면
		        /*
		         * hashdata검증 실패 로그를 처리하시기 바랍니다. 
		         */      
		        resultMSG = "결제결과 상점 DB처리(LGD_CASNOTEURL) 해쉬값 검증이 실패하였습니다.";     
		    }
		} catch (NoSuchAlgorithmException e) {
			log.error("confirmationOfPayment() :  {}", e.getMessage(), e);
		}
		return resultMSG;
	}
	
	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) {
		
		/*
	     * [결제 부분취소 요청 페이지]
	     *
	     * LG유플러스으로 부터 내려받은 거래번호(LGD_TID)를 가지고 취소 요청을 합니다.(파라미터 전달시 POST를 사용하세요)
	     * (승인시 LG유플러스으로 부터 내려받은 PAYKEY와 혼동하지 마세요.)
	     */
	    String CST_PLATFORM         = environment.getProperty("pg.lgdacom.serviceType");                 //LG유플러스 결제서비스 선택(test:테스트, service:서비스)
	    String CST_MID              = environment.getProperty("pg.lgdacom.mid");                      //LG유플러스으로 부터 발급받으신 상점아이디를 입력하세요.
	    String LGD_MID              = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;  //테스트 아이디는 't'를 제외하고 입력하세요.
	                                                                                        //상점아이디(자동생성)
	    String LGD_TID              = orderPgData.getPgKey();                      //LG유플러스으로 부터 내려받은 거래번호(LGD_TID)

		/* ※ 중요
		* 환경설정 파일의 경우 반드시 외부에서 접근이 가능한 경로에 두시면 안됩니다.
		* 해당 환경파일이 외부에 노출이 되는 경우 해킹의 위험이 존재하므로 반드시 외부에서 접근이 불가능한 경로에 두시기 바랍니다. 
		* 예) [Window 계열] C:\inetpub\wwwroot\lgdacom ==> 절대불가(웹 디렉토리)
		*/
	    String configPath 			= environment.getProperty("pg.lgdacom.home");  										//LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf") 위치 지정.
	        
	    LGD_TID     				= ( LGD_TID == null )?"":LGD_TID; 
	    
		XPayClient xpay = new XPayClient();
		xpay.Init(configPath, CST_PLATFORM);
		xpay.Init_TX(LGD_MID);
		xpay.Set("LGD_TXNAME", "PartialCancel");
		xpay.Set("LGD_TID", LGD_TID);
		xpay.Set("LGD_CANCELAMOUNT", Integer.toString(orderPgData.getCancelAmount())); //부분취소 금액
		xpay.Set("LGD_REMAINAMOUNT", Integer.toString(orderPgData.getRemainAmount())); //취소전 남은금액
		xpay.Set("LGD_CANCELTAXFREEAMOUNT", Integer.toString(orderPgData.getCancelTexFreeAmount())); //면세대상 부분취소 금액 (과세/면세 혼용상점만 적용)  
		xpay.Set("LGD_CANCELREASON", orderPgData.getCancelReason()); //취소사유
		xpay.Set("LGD_RFACCOUNTNUM", ""); //환불계좌 번호(가상계좌 환불인경우만 필수)
		xpay.Set("LGD_RFBANKCODE", ""); //환불계좌 은행코드(가상계좌 환불인경우만 필수)
		xpay.Set("LGD_RFCUSTOMERNAME", ""); //환불계좌 예금주(가상계좌 환불인경우만 필수)
	    xpay.Set("LGD_RFPHONE", ""); //요청자 연락처(가상계좌 환불인경우만 필수)
		xpay.Set("LGD_REQREMAIN", "1");
		
	    /*
	     * 1. 결제취소 요청 결과처리
	     *
	     * 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
		 *
		 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
		 * 1. 신용카드 : 0000, AV11  
		 * 2. 계좌이체 : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
		 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
		 *
	     */
		
		boolean isSuccess = false;
		if (xpay.TX()) {
			
			if( "0000".equals( xpay.m_szResCode ) ) {
				isSuccess = true;
			}
		}
		
		orderPgData.setSuccess(isSuccess);
		return orderPgData;
	}

    @Override
    public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam) {
//        /*
//         * [현금영수증 발급요청 페이지]
//         *
//         * LG유플러스으로 부터 내려받은 거래번호(LGD_TID)를 가지고 취소 요청을 합니다.(파라미터 전달시 POST를 사용하세요)
//         * (승인시 LG유플러스으로 부터 내려받은 PAYKEY와 혼동하지 마세요.)
//         */
//
//        String CST_PLATFORM         = environment.getProperty("pg.lgdacom.serviceType"); //LG유플러스 결제서비스 선택(test:테스트, service:서비스)
//        String CST_MID              = environment.getProperty("pg.lgdacom.mid"); //LG유플러스으로 부터 발급받으신 상점아이디를 입력하세요.
//        String LGD_MID				= ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID; //테스트 아이디는 't'를 제외하고 입력하세요.
//        String LGD_METHOD			= "AUTH"; //메소드('AUTH':승인, 'CANCEL' 취소)
//        String LGD_OID				= cashReceipt.getOrderCode(); //주문번호(상점정의 유니크한 주문번호를 입력하세요)
//        String LGD_PAYTYPE			= cashReceipt.getPgApprovalType(); //결제수단 코드 (SC0030:계좌이체, SC0040:가상계좌, SC0100:무통장입금 단독)
//        String LGD_AMOUNT			= Integer.toString(cashReceipt.getCashReceiptAmount());
//        String LGD_CASHCARDNUM		= cashReceipt.getCashReceiptCode(); //발급번호(현금영수증카드번호,휴대폰번호 등등)
//
//        Config shopConfig = ShopUtils.getConfig();
//
//        String LGD_CUSTOM_MERTNAME	= shopConfig.getCompanyName(); //상점명
//        String LGD_CUSTOM_BUSINESSNUM = shopConfig.getCompanyNumber(); //사업자등록번호
//        String LGD_CUSTOM_MERTPHONE	= shopConfig.getTelNumber(); //상점 전화번호
//        String LGD_CASHRECEIPTUSE	= "2".equals(cashReceipt.getCashReceiptType()) ? "2" : "1"; //현금영수증발급용도('1':소득공제, '2':지출증빙)
//        String LGD_PRODUCTINFO 		= cashReceipt.getProductName(); //상품명
//        String LGD_TID				= cashReceipt.getPgTid(); //LG유플러스 거래번호 (취소시만 사용)
//
//        /* ※ 중요
//         * 환경설정 파일의 경우 반드시 외부에서 접근이 가능한 경로에 두시면 안됩니다.
//         * 해당 환경파일이 외부에 노출이 되는 경우 해킹의 위험이 존재하므로 반드시 외부에서 접근이 불가능한 경로에 두시기 바랍니다.
//         * 예) [Window 계열] C:\inetpub\wwwroot\lgdacom ==> 절대불가(웹 디렉토리)
//         */
//
//        String configPath 			= environment.getProperty("pg.lgdacom.home");
//        LGD_METHOD       		= ( LGD_METHOD == null )?"":LGD_METHOD;
//        LGD_OID       		    = ( LGD_OID == null )?"":LGD_OID;
//        LGD_PAYTYPE       		= ( LGD_PAYTYPE == null )?"":LGD_PAYTYPE;
//        LGD_AMOUNT   		    = ( LGD_AMOUNT == null )?"":LGD_AMOUNT;
//        LGD_CASHCARDNUM         = ( LGD_CASHCARDNUM == null )?"":LGD_CASHCARDNUM;
//        LGD_CUSTOM_MERTNAME  	= ( LGD_CUSTOM_MERTNAME == null )?"":LGD_CUSTOM_MERTNAME;
//        LGD_CUSTOM_BUSINESSNUM  = ( LGD_CUSTOM_BUSINESSNUM == null )?"":LGD_CUSTOM_BUSINESSNUM;
//        LGD_CUSTOM_MERTPHONE  	= ( LGD_CUSTOM_MERTPHONE == null )?"":LGD_CUSTOM_MERTPHONE;
//        LGD_CASHRECEIPTUSE      = ( LGD_CASHRECEIPTUSE == null )?"":LGD_CASHRECEIPTUSE;
//        LGD_PRODUCTINFO         = ( LGD_PRODUCTINFO == null )?"":LGD_PRODUCTINFO;
//        LGD_TID         		= ( LGD_TID == null )?"":LGD_TID;
//
//        XPayClient xpay = new XPayClient();
//        xpay.Init(configPath, CST_PLATFORM);
//        xpay.Init_TX(LGD_MID);
//        xpay.Set("LGD_TXNAME", "CashReceipt");
//        xpay.Set("LGD_METHOD", LGD_METHOD);
//        xpay.Set("LGD_PAYTYPE", LGD_PAYTYPE);
//
//        if (LGD_METHOD.equals("AUTH")){    // 현금영수증 발급 요청
//            xpay.Set("LGD_OID", LGD_OID);
//            xpay.Set("LGD_CUSTOM_MERTNAME", LGD_CUSTOM_MERTNAME);
//            xpay.Set("LGD_CUSTOM_BUSINESSNUM", LGD_CUSTOM_BUSINESSNUM);
//            xpay.Set("LGD_CUSTOM_MERTPHONE", LGD_CUSTOM_MERTPHONE);
//            xpay.Set("LGD_CASHCARDNUM", LGD_CASHCARDNUM);
//            xpay.Set("LGD_AMOUNT", LGD_AMOUNT);
//            xpay.Set("LGD_CASHRECEIPTUSE", LGD_CASHRECEIPTUSE);
//            xpay.Set("LGD_TAXFREEAMOUNT", Integer.toString(cashReceipt.getCashReceiptTaxFreeAmount()));
//
//            if (LGD_PAYTYPE.equals("SC0030")){  //기결제된 계좌이체건 현금영수증 발급요청시 필수
//                xpay.Set("LGD_TID", LGD_TID);
//            }
//            else if (LGD_PAYTYPE.equals("SC0040")){  //기결제된 가상계좌건 현금영수증 발급요청시 필수
//                xpay.Set("LGD_TID", LGD_TID);
//                xpay.Set("LGD_SEQNO", "001");
//            }
//            else {  								//무통장입금 단독건 발급요청
//                xpay.Set("LGD_PRODUCTINFO", LGD_PRODUCTINFO);
//            }
//        }
//
//        /*
//         * 1. 현금영수증 발급/취소 요청 결과처리
//         *
//         * 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
//         */
//        if (xpay.TX()) {
//            //1)현금영수증 발급/취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
//
//            if ("0000".equals(xpay.m_szResCode)) {
//
//                cashReceipt.setSuccess(true);
//                cashReceipt.setCashReceiptIssueNumber(xpay.Response("LGD_TID",0));
//                cashReceipt.setCashReceiptIssueDate(DateUtils.getToday(Const.DATETIME_FORMAT));
//
//            } else {
//                cashReceipt.setSuccess(false);
//            }
//        }else {
//
//            cashReceipt.setSuccess(false);
//
//        }
//
//        return cashReceipt;

        return null;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {
//        /*
//         * [현금영수증 발급요청 페이지]
//         *
//         * LG유플러스으로 부터 내려받은 거래번호(LGD_TID)를 가지고 취소 요청을 합니다.(파라미터 전달시 POST를 사용하세요)
//         * (승인시 LG유플러스으로 부터 내려받은 PAYKEY와 혼동하지 마세요.)
//         */
//
//        String CST_PLATFORM         = environment.getProperty("pg.lgdacom.serviceType"); //LG유플러스 결제서비스 선택(test:테스트, service:서비스)
//        String CST_MID              = environment.getProperty("pg.lgdacom.mid"); //LG유플러스으로 부터 발급받으신 상점아이디를 입력하세요.
//        String LGD_MID				= ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID; //테스트 아이디는 't'를 제외하고 입력하세요.
//        String LGD_METHOD			= "CANCEL"; //메소드('AUTH':승인, 'CANCEL' 취소)
//        String LGD_OID				= cashReceipt.getOrderCode(); //주문번호(상점정의 유니크한 주문번호를 입력하세요)
//        String LGD_PAYTYPE			= cashReceipt.getPgApprovalType(); //결제수단 코드 (SC0030:계좌이체, SC0040:가상계좌, SC0100:무통장입금 단독)
//        String LGD_AMOUNT			= Integer.toString(cashReceipt.getCashReceiptAmount());
//        String LGD_CASHCARDNUM		= cashReceipt.getCashReceiptCode(); //발급번호(현금영수증카드번호,휴대폰번호 등등)
//
//        Config shopConfig = ShopUtils.getConfig();
//
//        String LGD_CUSTOM_MERTNAME	= shopConfig.getCompanyName(); //상점명
//        String LGD_CUSTOM_BUSINESSNUM = shopConfig.getCompanyNumber(); //사업자등록번호
//        String LGD_CUSTOM_MERTPHONE	= shopConfig.getTelNumber(); //상점 전화번호
//        String LGD_CASHRECEIPTUSE	= "2".equals(cashReceipt.getCashReceiptType()) ? "2" : "1"; //현금영수증발급용도('1':소득공제, '2':지출증빙)
//        String LGD_PRODUCTINFO 		= cashReceipt.getProductName(); //상품명
//        String LGD_TID				= cashReceipt.getPgTid(); //LG유플러스 거래번호 (취소시만 사용)
//
//        /* ※ 중요
//         * 환경설정 파일의 경우 반드시 외부에서 접근이 가능한 경로에 두시면 안됩니다.
//         * 해당 환경파일이 외부에 노출이 되는 경우 해킹의 위험이 존재하므로 반드시 외부에서 접근이 불가능한 경로에 두시기 바랍니다.
//         * 예) [Window 계열] C:\inetpub\wwwroot\lgdacom ==> 절대불가(웹 디렉토리)
//         */
//
//        String configPath 			= environment.getProperty("pg.lgdacom.home");
//        LGD_METHOD       		= ( LGD_METHOD == null )?"":LGD_METHOD;
//        LGD_OID       		    = ( LGD_OID == null )?"":LGD_OID;
//        LGD_PAYTYPE       		= ( LGD_PAYTYPE == null )?"":LGD_PAYTYPE;
//        LGD_AMOUNT   		    = ( LGD_AMOUNT == null )?"":LGD_AMOUNT;
//        LGD_CASHCARDNUM         = ( LGD_CASHCARDNUM == null )?"":LGD_CASHCARDNUM;
//        LGD_CUSTOM_MERTNAME  	= ( LGD_CUSTOM_MERTNAME == null )?"":LGD_CUSTOM_MERTNAME;
//        LGD_CUSTOM_BUSINESSNUM  = ( LGD_CUSTOM_BUSINESSNUM == null )?"":LGD_CUSTOM_BUSINESSNUM;
//        LGD_CUSTOM_MERTPHONE  	= ( LGD_CUSTOM_MERTPHONE == null )?"":LGD_CUSTOM_MERTPHONE;
//        LGD_CASHRECEIPTUSE      = ( LGD_CASHRECEIPTUSE == null )?"":LGD_CASHRECEIPTUSE;
//        LGD_PRODUCTINFO         = ( LGD_PRODUCTINFO == null )?"":LGD_PRODUCTINFO;
//        LGD_TID         		= ( LGD_TID == null )?"":LGD_TID;
//
//        XPayClient xpay = new XPayClient();
//        xpay.Init(configPath, CST_PLATFORM);
//        xpay.Init_TX(LGD_MID);
//        xpay.Set("LGD_TXNAME", "CashReceipt");
//        xpay.Set("LGD_METHOD", LGD_METHOD);
//        xpay.Set("LGD_PAYTYPE", LGD_PAYTYPE);
//
//        // 부분취소시 amount가 0보다 클듯
//        if (Integer.parseInt(LGD_AMOUNT) > 0) {
//            xpay.Set("LGD_AMOUNT", LGD_AMOUNT);
//            xpay.Set("LGD_TAXFREEAMOUNT", Integer.toString(cashReceipt.getCashReceiptTaxFreeAmount()));
//        }
//        xpay.Set("LGD_TID", LGD_TID);
//        if (LGD_PAYTYPE.equals("SC0040")){  //가상계좌건 현금영수증 발급취소시 필수
//            xpay.Set("LGD_SEQNO", "001");
//
//        }
//
//        /*
//         * 1. 현금영수증 발급/취소 요청 결과처리
//         *
//         * 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
//         */
//        if (xpay.TX()) {
//
//            if ("0000".equals(xpay.m_szResCode)) {
//
//                cashReceipt.setSuccess(true);
//
//            } else {
//                cashReceipt.setSuccess(false);
//            }
//
//
//        }else {
//
//            cashReceipt.setSuccess(false);
//
//        }
//
//        return cashReceipt;

        return null;
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
