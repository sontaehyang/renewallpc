package saleson.shop.order.pg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.kspay.domain.KSPayApprovalCancelBean;
import saleson.shop.order.pg.kspay.domain.KSPayWebHostBean;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


@Service("kspayService")
public class KspayServiceImpl implements PgService {
	
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	Environment environment;

/*	(참조) 카드사 코드 표
	카드사코드는 6byte 또는 2byte로 사용됩니다.
	ex) 현대카드 : 010008 또는 08
	╔═══════════════════════════════════════════╗
	║	비씨카드		:	010001		ISP인증		║	
	║	국민카드		:	010002		ISP인증		║
	║	외환카드		:	010003		MPI인증		║
	║	삼성카드		:	010004		MPI인증		║
	║	신한카드		:	010005		MPI인증		║
	║	현대카드		:	010008		MPI인증		║
	║	롯데카드		:	010009		MPI인증		║
	║	한미은행		:	010011		MPI인증		║
	║	수협			:	010012		MPI인증		║
	║	우리은행		:	010014		ISP인증		║
	║	농협			:	010015		일반	  		║
	║	제주은행		:	010016		MPI인증		║
	║	광주은행		:	010017		MPI인증		║
	║	전북은행		:	010018		MPI인증		║
	║	조흥은행		:	010019		MPI인증		║
	║	주택은행		:	010023		승인불가		║
	║	하나은행		:	010024		MPI인증		║
	║	씨티은행		:	010026		MPI인증		║
	║	해외카드사	:	010025		일반			║
	║	기타			:	010099					║
	╚═══════════════════════════════════════════╝
	은행 및 증권사 코드표
	╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
	║	한국은행		:		01			장기신용		:		09			제일은행		:		23			대구은행		:		31			충북은행		:		40		║
	║	산업은행		:		02			신농협중앙	:		10			한일은행		:		24			부산은행		:		32			새마을금고	:		45		║
	║	기업은행		:		03			농협중앙		:		11			서울은행		:		25			충청은행		:		33			씨티은행		:		53		║
	║	국민은행		:		04			농협회원		:		12~15		신한은행		:	26(가상계좌)		광주은행		:		34			우체국		:		71		║
	║	외환은행		:		05			축협중앙		:		16			한미/시티	:		27			제주은행		:		35			신용보증		:		76		║
	║	주택은행		:		06			우리은행		:		20			동화은행		:		28			경기은행		:		36			하나은행		:		81		║
	║	수협은행		:		07			조흥은행		:		21			동남은행		:		29			전북은행		:		38			보람은행		:		82		║
	║	수출입		:		08			상업은행		:		22			대동은행		:		30			경남은행		:		39			평화은행		:		83		║
	╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝
	※신한은행은 가상계좌는 26, 계좌이체는 88.
	╔═══════════════════════════════════════════════════════════════════════════════════════════════╗
	║	신용카드 영수증 출력 																			║
	║	http://pgims.ksnet.co.kr/pg_infoc/src/bill/credit_view.jsp?tr_no=해당거래번호					║
	║	설정 예) http://pgims.ksnet.co.kr/pg_infoc/src/bill/credit_view.jsp?tr_no=120422012465		║
	╠═══════════════════════════════════════════════════════════════════════════════════════════════╣
	║	신용카드 영수증 조회																			║ 
	║	http://pgims.ksnet.co.kr/pg_infoc/src/bill/credit01.jsp										║
	╠═══════════════════════════════════════════════════════════════════════════════════════════════╣
	║	현금영수증 영수증 출력																			║
	║	http://pgims.ksnet.co.kr/pg_infoc/src/bill/ps2.jsp?s_pg_deal_numb=H62770003201				║
	║	설정 예)http://pgims.ksnet.co.kr/pg_infoc/src/bill/ps2.jsp?s_pg_deal_numb=H20452002296		║
	╠═══════════════════════════════════════════════════════════════════════════════════════════════╣
	║	현금영수증 영수증 조회 																		║
	║	http://pgims.ksnet.co.kr/pg_infoc/src/bill/credit_cash.jsp									║
	╚═══════════════════════════════════════════════════════════════════════════════════════════════╝

 */

    @Override
    public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam) {
//        /*
//         * [현금영수증 발급요청] - 무통장입금
//         *
//         */
//        String ApprovalType  	= "H000";																	//H000:일반발급, H200:계좌이체, H600:가상계좌, H001,H201,H601 : 현금영수증취소
//        String TransactionNo 	= "";																		//입금완료된 계좌이체, 가상계좌 거래번호
//        String IssuSele      	= "2";																		//0:일반발급(PG원거래번호 중복체크), 1:단독발급(주문번호 중복체크 : PG원거래 없음), 2:강제발급(중복체크 안함)
//        String UserInfoSele  	= "3";																		//0:주민등록번호, 1:사업자번호, 2:카드번호, 3:휴대폰번호, 4:기타(?)
//        String UserInfo      	= cashReceipt.getCashReceiptCode().replaceAll("-", "");						//주민등록번호, 사업자번호, 카드번호, 휴대폰번호, 기타
//        String TranSele      	= cashReceipt.getCashReceiptType().equals("1") ? "1" : "0";					//0: 개인, 1: 사업자
//        String CallCode      	= "0";																		//통화코드  (0: 원화, 1: 미화)
//        String SvcAmt        	= "0";																		//봉사료
//        String TotAmt        	= Integer.toString(cashReceipt.getCashReceiptAmount());						//현금영수증 발급금액
//        String TaxAmt        	= Integer.toString((int)(cashReceipt.getCashReceiptAmount() / 1.1 * 0.1));	//세금
//        String SupplyAmt     	= Integer.toString(Integer.parseInt(TotAmt) - Integer.parseInt(TaxAmt));	//공급가액
//        String Filler			= "";     													 				//예비
//
//        // Server로 부터 응답이 없을시 자체응답
//        String HTransactionNo		= ""; 				// 거래번호
//        String HStatus    			= "X";				// 오류구분 O:정상 X:거절
//        String HCashTransactionNo   = "";				// 현금영수증 거래번호
//        String HIncomeType          = ""; 				// 0: 소득      1: 비소득
//        String HTradeDate          	= ""; 				// 거래 개시 일자
//        String HTradeTime           = ""; 				// 거래 개시 시간
//        String HMessage1			= "요청거절"; 		// 응답 message1
//        String HMessage2            = "C잠시후재시도"; 	// 응답 message2
//        String HCashMessage1        = ""; 				// 국세청 메시지1
//        String HCashMessage2        = "";				// 국세청 메시지1
//        String HFiller             	= ""; 				// 예비
//
//        // Default(수정항목이 아님)-------------------------------------------------------
//        String	EncType       = "2";					// 0: 암화안함, 1:openssl, 2: seed
//        String	Version       = "0603";				    // 전문버전
//        String	Type          = "00";					// 구분
//        String	Resend        = "0";					// 전송구분 : 0 : 처음,  2: 재전송
//        String	RequestDate   = new SimpleDateFormat(Const.DATETIME_FORMAT).format(new java.util.Date()); // 요청일자 : yyyymmddhhmmss
//        String	KeyInType     = "K";					// KeyInType 여부 : S : Swap, K: KeyInType
//        String	LineType      = "1";			        // lineType 0 : offline, 1:internet, 2:Mobile
//        String	ApprovalCount = "1";				    // 복합승인갯수
//        String 	GoodType      = "0";	                // 제품구분 0 : 실물, 1 : 디지털
//        String	HeadFiller    = "";				        // 예비
//        //-------------------------------------------------------------------------------
//
//        // Header (입력값 (*) 필수항목)--------------------------------------------------
//        String	StoreId		= environment.getProperty("pg.kspay.mid");		// *상점아이디
//        String	OrderNumber	= cashReceipt.getOrderCode();						// *주문번호
//        String	UserName    = "";												// 주문자명
//        String	IdNum       = "";												// 주민번호 or 사업자번호(개인정보 보호법 개정으로 주민번호 수집불가)
//        String	Email       = "";												// email
//        String 	GoodName    = "";												// 제품명
//        String	PhoneNo     = "";												// 휴대폰번호
//        // Header end -------------------------------------------------------------------
//
//        KSPayApprovalCancelBean ipg;
//        String addr = environment.getProperty("pg.kspay.addr");
//        int port = Integer.parseInt(environment.getProperty("pg.kspay.port"));
//
//        try
//        {
//            ipg = new KSPayApprovalCancelBean(addr, port);
//
//            ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email,
//                    GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);
//
//            ipg.CashBillDataMessage(ApprovalType, TransactionNo, IssuSele, UserInfoSele, UserInfo, TranSele, CallCode, SupplyAmt, TaxAmt, SvcAmt, TotAmt, Filler);
//
//            if(ipg.SendSocket("1")) {
//
//                HTransactionNo		= ipg.HTransactionNo[0]; 		// 거래번호
//                HStatus    			= ipg.HStatus[0];				// 오류구분 O:정상 X:거절
//                HCashTransactionNo  = ipg.HCashTransactionNo[0];	// 현금영수증 거래번
//                HIncomeType         = ipg.HIncomeType[0]; 			// 0: 소득      1: 비소득
//                HTradeDate          = ipg.HTradeDate[0]; 			// 거래 개시 일자
//                HTradeTime          = ipg.HTradeTime[0]; 			// 거래 개시 시간
//                HMessage1			= ipg.HMessage1[0]; 			// 응답 message1
//                HMessage2           = ipg.HMessage2[0]; 			// 응답 message2
//                HCashMessage1       = ipg.HCashMessage1[0]; 		// 국세청 메시지1
//                HCashMessage2       = ipg.HCashMessage2[0];			// 국세청 메시지2
//                HFiller             = ipg.HFiller[0]; 				// 예비
//            }
//        }
//        catch(Exception e)
//        {
//            HMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
//        } // end of catch
//
//        /*
//         * 1. 현금영수증 발급/취소 요청 결과처리
//         */
//        if (HStatus.equals("O"))
//        {
//            //	1)현금영수증 발급/취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
//            cashReceipt.setSuccess(true);
//            cashReceipt.setCashReceiptIssueNumber(HTransactionNo);
//            cashReceipt.setCashReceiptIssueDate(DateUtils.getToday(Const.DATETIME_FORMAT));
//        }
//        else
//        {
//            cashReceipt.setSuccess(false);
//        }

        return null;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {
//        /*
//         * [현금영수증 취소요청]
//         *
//         */
//        String ApprovalType  = "H210";           // 승인구분
//        String TrNo          = cashReceipt.getCashReceiptIssueNumber() == null ? "":cashReceipt.getCashReceiptIssueNumber();             // 거래번호
//        String Refundcheck   = "1";   // 취소구분(1.거래취소, 2.오류발급취소, 3.기타)
//
//
//        // Server로 부터 응답이 없을시 자체응답
//        String rApprovalType        = "H011";
//        String rHTransactionNo      = "";               // 거래번호
//        String rHStatus             = "X";              // 상태 O : 승인, X : 거절
//        String rHCashTransactionNo  = "";               // 현금현수증 거래확인번호
//        String rHIncomeType         = "";               // 소득비소득 구분
//        String rHTradeDate          = "";               // 거래일자
//        String rHTradeTime	        = "";               // 거래시간
//        String rHMessage1           = "취소거절";        // 메시지1
//        String rHMessage2           = "C잠시후재시도";   // 메시지2
//        String rHCashMessage1       = "";
//        String rHCashMessage2       = "";
//
//        KSPayApprovalCancelBean ipg;
//        String addr = environment.getProperty("pg.kspay.addr");
//        int port = Integer.parseInt(environment.getProperty("pg.kspay.port"));
//
//        try
//        {
//            ipg = new KSPayApprovalCancelBean(addr, port);
//
//            headMessage(ipg);
//
//            ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "",Refundcheck,"");
//
//            if(ipg.SendSocket("1")) {
//                rApprovalType       = ipg.ApprovalType[0];
//                rHTransactionNo     = ipg.HTransactionNo[0];         // 거래번호
//                rHStatus            = ipg.HStatus[0];                // 상태 O : 승인, X : 거절
//                rHCashTransactionNo = ipg.HCashTransactionNo[0];     // 거래일자
//                rHIncomeType        = ipg.HIncomeType[0];            // 거래시간
//                rHTradeDate         = ipg.HTradeDate[0];             // 발급사코드
//                rHTradeTime         = ipg.HTradeTime[0];             // 매입사코드
//                rHMessage1          = ipg.HMessage1[0];              // 승인번호 or 거절시 오류코드
//                rHMessage2          = ipg.HMessage2[0];              // 메시지1
//                rHCashMessage1      = ipg.HCashMessage1[0];          // 메시지2
//                rHCashMessage2      = ipg.HCashMessage2[0];          // 카드번호
//            }
//        }
//        catch(Exception e)
//        {
//            rHMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
//        } // end of catch
//
//        /*
//         * 1. 현금영수증 발급/취소 요청 결과처리
//         */
//        if (rHStatus.equals("O"))
//        {
//            //1)현금영수증 발급/취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
//            cashReceipt.setSuccess(true);
//            cashReceipt.setCashReceiptIssueNumber(rHTransactionNo);
//            cashReceipt.setCashReceiptIssueDate(DateUtils.getToday(Const.DATETIME_FORMAT));
//            cashReceipt.setCashReceiptStatusCode("3");
//        }
//        else
//        {
//            cashReceipt.setSuccess(false);
//        }

        return null;
    }

	@Override
	public String confirmationOfPayment(PgData pgData) {

		return null;
	}
		
	@Override
	public String getPayType(String payType) 
	{
		if ("card".equals(payType)) 
		{
			return "1000000000";
		} 
//		else if ("vbank".equals(payType)) 
//		{
//			return "0100000000";
//		} 
		else if ("realtimebank".equals(payType)) 
		{
			return "0010000000";
		} 
//		else if ("hp".equals(payType)) 
//		{
//			return "0000010000";
//		}
		
		return "";
	}
	
	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) 
	{
		String payMethod = this.getPayType(((PgData) data).getApprovalType());
		
		HashMap<String, Object> payReqMap = new HashMap<>();
		payReqMap.put("sndPaymethod", payMethod);
		payReqMap.put("sndOrdernumber", ((PgData) data).getOrderCode());
		payReqMap.put("sndGoodname", ((PgData) data).getGoodname());
		payReqMap.put("sndStoreid", environment.getProperty("pg.kspay.mid"));
		payReqMap.put("sndOrdername", ((PgData) data).getUsername());
		payReqMap.put("sndEmail", ((PgData) data).getUserEmail());
		payReqMap.put("sndMobile", ((PgData) data).getUserPhone());
		payReqMap.put("sndAmount", ((PgData) data).getAmount());		
		payReqMap.put("orderCode", ((PgData) data).getOrderCode());

		////////////////////	0.공통 환경설정	////////////////////
		
		//payReqMap.put("sndReply", sndReply);
		/**
		 * 상품유형: 실물(1),디지털(2)
		 */
		payReqMap.put("sndGoodType", "1");
		
		////////////////////	1.신용카드 관련설정	////////////////////	
		
		/**
		 * I(ISP), M(안심결제), N(일반승인:구인증방식), A(해외카드), W(해외안심)
		 */
		payReqMap.put("sndShowcard", "I,M");
		/**
		 * 신용카드(해외카드) 통화코드: 해외카드결제시 달러결제를 사용할경우 변경
		 * 원화(WON), 달러(USD)
		 */
		payReqMap.put("sndCurrencytype", "WON");
		/**
		 * 할부개월수 선택범위
		 * 상점에서 적용할 할부개월수를 세팅합니다. 여기서 세팅하신 값은 결제창에서 고객이 스크롤하여 선택하게 됩니다
		 * 아래의 예의경우 고객은 0~12개월의 할부거래를 선택할수있게 됩니다.    
		 */
		payReqMap.put("sndInstallmenttype", "ALL(0:2:3:4:5:6:7:8:9:10:11:12)");
		/**
		 * 가맹점부담 무이자할부설정
		 * 카드사 무이자행사만 이용하실경우  또는 무이자 할부를 적용하지 않는 업체는  "NONE"로 세팅
		 * 예 : 전체카드사 및 전체 할부에대해서 무이자 적용할 때는 value="ALL" / 무이자 미적용할 때는 value="NONE"
		 * 예 : 전체카드사 3,4,5,6개월 무이자 적용할 때는 value="ALL(3:4:5:6)" 
		 * 예 : 삼성카드(카드사코드:04) 2,3개월 무이자 적용할 때는 value="04(3:4:5:6)"
		 */
		payReqMap.put("sndInteresttype", "NONE");

		////////////////////	2.계좌이체 현금영수증 발급여부 설정		////////////////////
		
		/**
		 * 계좌이체시 현금영수증 발급여부 (0: 발급안함, 1:발급)
		 */
		payReqMap.put("sndCashReceipt", "1");
				
		return payReqMap;
	}
	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) 
	{
		/*
	     * [결제 부분취소 요청]
	     *
		*/
		int seq;
		
		if(orderPgData.getPartCancelDetail() == null || orderPgData.getPartCancelDetail().equals("N"))
			seq = 1;
		else	
			seq = Integer.parseInt(orderPgData.getPartCancelDetail().substring(13, 14)) + 1;

	// Data Default(수정항목이 아님)-------------------------------------------------		
		String ApprovalType	  = orderPgData.getPgPaymentType().equals("1000000000") ? "1010" : "2010";		// 카드취소유형 신용카드취소 : 1010 / 휴대폰취소 : M110
		String TrNo   		  = orderPgData.getPgKey();						// 거래번호
		String Canc_amt		  = cancelAmount_calculate(orderPgData.getCancelAmount()+"");	// 취소금액		9자리	ex) 000010000
		String Canc_seq		  = seq < 10 ? "0"+seq : seq+"";	// 취소일련번호	2자리	ex)	01~99  하나의 거래에 대해 일련번호가 중복될 경우 취소 거절
		String Canc_type	  = "3";	// 취소유형 0 :거래번호취소 1: 주문번호취소 3:부분취소
	// Data Default end -------------------------------------------------------------

	// Server로 부터 응답이 없을시 자체응답
		String rApprovalType	   = "1011"; 
		String rTransactionNo      = "";			// 거래번호
		String rStatus             = "X";			// 상태 O : 승인, X : 거절
		String rTradeDate          = ""; 			// 거래일자
		String rTradeTime          = ""; 			// 거래시간
		String rIssCode            = "00"; 			// 발급사코드
		String rAquCode			   = "00"; 			// 매입사코드
		String rAuthNo             = "9999"; 		// 승인번호 or 거절시 오류코드
		String rMessage1           = "취소거절"; 	// 메시지1
		String rMessage2           = "C잠시후재시도";// 메시지2
		String rCardNo             = ""; 			// 카드번호
		String rExpDate            = ""; 			// 유효기간
		String rInstallment        = ""; 			// 할부
		String rAmount             = ""; 			// 금액
		String rMerchantNo         = ""; 			// 가맹점번호
		String rAuthSendType       = "N"; 			// 전송구분
		String rApprovalSendType   = "N"; 			// 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
		String rPoint1             = "000000000000";// Point1
		String rPoint2             = "000000000000";// Point2
		String rPoint3             = "000000000000";// Point3
		String rPoint4             = "000000000000";// Point4
		String rVanTransactionNo   = "";
		String rFiller             = ""; 			// 예비
		String rAuthType	 	   = ""; 			// ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
		String rMPIPositionType	   = ""; 			// K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
		String rMPIReUseType	   = ""; 			// Y : 재사용, N : 재사용아님
		String rEncData			   = ""; 			// MPI, ISP 데이터
		
		KSPayApprovalCancelBean ipg;
		String addr = environment.getProperty("pg.kspay.addr");
		int port = Integer.parseInt(environment.getProperty("pg.kspay.port"));
		
		try 
		{
			ipg = new KSPayApprovalCancelBean(addr, port); 

			headMessage(ipg);

			ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");

			if(ipg.SendSocket("1")) 
			{
				rApprovalType		= ipg.getApprovalType()[0];
				rTransactionNo		= ipg.getTransactionNo()[0];	  		// 거래번호
				rStatus				= ipg.getStatus()[0];		  		// 상태 O : 승인, X : 거절
				rTradeDate			= ipg.getTradeDate()[0];		  		// 거래일자
				rTradeTime			= ipg.getTradeTime()[0];		  		// 거래시간
				rIssCode			= ipg.getIssCode()[0];		  		// 발급사코드
				rAquCode			= ipg.getAquCode()[0];		  		// 매입사코드
				rAuthNo				= ipg.getAuthNo()[0];		  		// 승인번호 or 거절시 오류코드
				rMessage1			= ipg.getMessage1()[0];		  		// 메시지1
				rMessage2			= ipg.getMessage2()[0];		  		// 메시지2
				rCardNo				= ipg.getCardNo()[0];		  		// 카드번호
				rExpDate			= ipg.getExpDate()[0];		  		// 유효기간
				rInstallment		= ipg.getInstallment()[0];	  		// 할부
				rAmount				= ipg.getAmount()[0];		  		// 금액
				rMerchantNo			= ipg.getMerchantNo()[0];	  		// 가맹점번호
				rAuthSendType		= ipg.getAuthSendType()[0];	  		// 전송구분= new String(this.read(2));
				rApprovalSendType	= ipg.getApprovalSendType()[0];		// 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
				rPoint1				= ipg.getPoint1()[0];		  		// Point1
				rPoint2				= ipg.getPoint2()[0];		  		// Point2
				rPoint3				= ipg.getPoint3()[0];		  		// Point3
				rPoint4				= ipg.getPoint4()[0];		  		// Point4
				rVanTransactionNo   = ipg.getVanTransactionNo()[0];      // Van거래번호
				rFiller				= ipg.getFiller()[0];		  		// 예비
				rAuthType			= ipg.getAuthType()[0];		  		// ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
				rMPIPositionType	= ipg.getMPIPositionType()[0]; 		// K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
				rMPIReUseType		= ipg.getMPIReUseType()[0];			// Y : 재사용, N : 재사용아님
				rEncData			= ipg.getEncData()[0];		  		// MPI, ISP 데이터
			}
		}
		catch(Exception e) 
		{
			rMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
		} // end of catch

		boolean isSuccess = false;

		if(rStatus.equals("O")) 
		{
			isSuccess = true;
			
			if ("card".equals(orderPgData.getPgPaymentType().toLowerCase())) 
			{			
				String partCancelFlag = "Y";
				orderPgData.setPartCancelFlag(partCancelFlag);
			} 			
			
			orderPgData.setPgAmount(orderPgData.getRemainAmount());
			orderPgData.setPartCancelDetail("PART_CANCEL_"+Canc_seq);
		}
		
		orderPgData.setSuccess(isSuccess);
		return orderPgData;
	}
	
	@Override
	public OrderPgData pay(Object data, HttpSession session) 
	{		
		OrderPgData orderPgData = new OrderPgData();
		int isMobile = ShopUtils.isMobilePage() ? 1 : 0;

		String rcid		= ((PgData) data).getReWHCid();
		String rctype	= ((PgData) data).getReWHCtype();
		String rhash	= ((PgData) data).getReWHHash();

		String	authyn =  "";
		String	trno   =  "";
		String	trddt  =  "";
		String	trdtm  =  "";
		String	amt    =  "";
		String	authno =  "";
		String	msg1   =  "";
		String	msg2   =  "";
		String	ordno  =  "";
		String	isscd  =  "";
		String	aqucd  =  "";
		String	temp_v =  "";
		String	result =  "";
		String	cardno =  "";
		String	halbu  =  "";
		String	cbtrno =  "";
		String	cbauthno =  "";
		String	resultcd =  "";

		//업체에서 추가하신 인자값을 받는 부분입니다
//		String	a =  request.getParameter("a"); 
//		String	b =  request.getParameter("b"); 
//		String	c =  request.getParameter("c"); 
//		String	d =  request.getParameter("d");

		
		
		KSPayWebHostBean ipg = new KSPayWebHostBean(rcid,isMobile);
		if (ipg.kspay_send_msg("1"))		//KSNET 결제결과 중 아래에 나타나지 않은 항목이 필요한 경우 Null 대신 필요한 항목명을 설정할 수 있습니다.
		{
			authyn	 = ipg.kspay_get_value("authyn");
			trno	 = ipg.kspay_get_value("trno"  );
			trddt	 = ipg.kspay_get_value("trddt" );
			trdtm	 = ipg.kspay_get_value("trdtm" );
			amt		 = ipg.kspay_get_value("amt"   );
			authno	 = ipg.kspay_get_value("authno");
			msg1	 = ipg.kspay_get_value("msg1"  );
			msg1	 = msg1 != null ? msg1.trim() : "";
			msg2	 = ipg.kspay_get_value("msg2"  );
			msg2	 = msg2 != null ? msg2.trim() : "";
			ordno	 = ipg.kspay_get_value("ordno" );
			isscd	 = ipg.kspay_get_value("isscd" );
			aqucd	 = ipg.kspay_get_value("aqucd" );
			temp_v	 = "";
			result	 = ipg.kspay_get_value("result");
			cardno	 = ipg.kspay_get_value("cardno");
			halbu	 = ipg.kspay_get_value("halbu");
			cbtrno	 = ipg.kspay_get_value("cbtrno");	// 현금영수증 거래번호
			cbauthno = ipg.kspay_get_value("cbauthno");	// 현금영수증 국세청 승인번호
			
			if (null != authyn && 1 == authyn.length())
			{
				if (authyn.equals("O"))
				{
					resultcd = "0000";
				}
				else
				{
					resultcd = authno.trim();
				}

//				ipg.kspay_send_msg("3");		// 정상처리가 완료되었을 경우 호출합니다.(이 과정이 없으면 일시적으로 kspay_send_msg("1")을 호출하여 거래내역 조회가 가능합니다.)
			}
		}

	    /*
	     * 2. 최종결제 요청 결과처리
	     *
	     * 최종 결제요청 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
	     */
		if (ipg.kspay_send_msg("1")) 
		{
			if( "0000".equals(resultcd) ) 
			{
				orderPgData.setSuccess(true);
				orderPgData.setPgKey(trno);	//PG사 거래번호
				orderPgData.setPgAuthCode(resultcd);
				orderPgData.setCbtrno(cbtrno);	//현금영수증 거래번호
				orderPgData.setPgProcInfo(this.makePgLog(ipg, ((PgData) data).getApprovalType()));
				orderPgData.setErrorMessage(msg2);
				
				String partCancelFalg= "N";
				
				//신용카드 결제의 경우 부분취소 가능여부를 조회
				if ("card".equals(((PgData) data).getApprovalType()) && "0".equals(authyn))
				{
					partCancelFalg = "Y";
				}
				orderPgData.setPartCancelFlag(partCancelFalg);
		    	orderPgData.setPartCancelDetail("N");
			} 
			else 
			{
				orderPgData.setSuccess(false);
				orderPgData.setErrorMessage("[" + resultcd + "] " + msg2 + " " +msg1);
			}
		} 
		else 
		{
			orderPgData.setSuccess(false);
			orderPgData.setErrorMessage("[" + resultcd + "] " + msg2 + " " +msg1);
		}
	     
		return orderPgData;
	}

	@Override
	public boolean cancel(OrderPgData orderPgData) 
	{
		/*
	     * [결제취소 요청]
	     *
		*/

	// Data Default(수정항목이 아님)-------------------------------------------------
		String ApprovalType	  = orderPgData.getPgPaymentType().equals("1000000000") ? "1010" : "2010";		// 카드취소유형 신용카드취소 : 1010 / 휴대폰취소 : M110
		String TrNo   		  = orderPgData.getPgKey();						// 거래번호
		String Canc_type	  = "0";	// 취소유형 0 :거래번호취소 1: 주문번호취소 3:부분취소
	// Data Default end -------------------------------------------------------------

	// Server로 부터 응답이 없을시 자체응답
		String rApprovalType	   = "1011"; 
		String rTransactionNo      = "";			// 거래번호
		String rStatus             = "X";			// 상태 O : 승인, X : 거절
		String rTradeDate          = ""; 			// 거래일자
		String rTradeTime          = ""; 			// 거래시간
		String rIssCode            = "00"; 			// 발급사코드
		String rAquCode			   = "00"; 			// 매입사코드
		String rAuthNo             = "9999"; 		// 승인번호 or 거절시 오류코드
		String rMessage1           = "취소거절"; 	// 메시지1
		String rMessage2           = "C잠시후재시도";// 메시지2
		String rCardNo             = ""; 			// 카드번호
		String rExpDate            = ""; 			// 유효기간
		String rInstallment        = ""; 			// 할부
		String rAmount             = ""; 			// 금액
		String rMerchantNo         = ""; 			// 가맹점번호
		String rAuthSendType       = "N"; 			// 전송구분
		String rApprovalSendType   = "N"; 			// 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
		String rPoint1             = "000000000000";// Point1
		String rPoint2             = "000000000000";// Point2
		String rPoint3             = "000000000000";// Point3
		String rPoint4             = "000000000000";// Point4
		String rVanTransactionNo   = "";
		String rFiller             = ""; 			// 예비
		String rAuthType	 	   = ""; 			// ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
		String rMPIPositionType	   = ""; 			// K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
		String rMPIReUseType	   = ""; 			// Y : 재사용, N : 재사용아님
		String rEncData			   = ""; 			// MPI, ISP 데이터
		
		String acApprovalType		=	"2421";			    
		String acTransactionNo		=	"";		
		String acStatus				=	"X";
		String acTradeDate			=	"";	
		String acTradeTime			=	"";	
		String acctSele				=	"";  
		String acFeeSele			=	""; 
		String acInjaName			=	"";      
		String acPareBankCode		=	"";  
		String acPareAcctNo			=	"";    
		String acCustBankCode		=	"";  
		String acCustAcctNo			=	"";    
		String acAmount				=	"";        
		String acBankTransactionNo	=	"";
		String acIpgumNm			=	"";          
		String acBankFee			=	"";          
		String acBankAmount			=	"";       
		String acBankRespCode		=	"";     
		String acMessage1			=	"취소거절";         
		String acMessage2			=	"C잠시후재시도";         
		String acCavvSele			=	"";         
		String acFiller				=	"";           

		String addr = environment.getProperty("pg.kspay.addr");
		int port = Integer.parseInt(environment.getProperty("pg.kspay.port"));
		
		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(addr, port); 

			headMessage(ipg);

			ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", "","","");	//전체취소일 경우

			if(ipg.SendSocket("1"))
			{
				if(ipg.getApprovalType()[0].substring(0,1).equals("2"))	//계좌이체 취소응답
				{
					acApprovalType		= ipg.getApprovalType()[0];
					acTransactionNo		= ipg.getACTransactionNo()[0];	 	// 거래번호
					acStatus			= ipg.getACStatus()[0];		  		// 상태 O : 승인, X : 거절
					acTradeDate			= ipg.getACTradeDate()[0];		 	// 거래일자
					acTradeTime			= ipg.getACTradeTime()[0];		 	// 거래시간
					acctSele 			= ipg.getACAcctSele()[0];  			// 계좌이체 구분 -	1:Dacom, 2:Pop Banking,	3:실시간계좌이체 4: 승인형계좌이체
					acFeeSele 			= ipg.getACFeeSele()[0];  			// 선/후불제구분 -	1:선불,	2:후불
					acInjaName 			= ipg.getACInjaName()[0];  			// 인자명(통장인쇄메세지-상점명)
					acPareBankCode		= ipg.getACPareBankCode()[0]; 		// 입금모계좌코드
					acPareAcctNo		= ipg.getACPareAcctNo()[0];  		// 입금모계좌번호
					acCustBankCode		= ipg.getACCustBankCode()[0]; 		// 출금모계좌코드
					acCustAcctNo		= ipg.getACCustAcctNo()[0];  		// 출금모계좌번호
					acAmount			= ipg.getACAmount()[0];  			// 금액	(결제대상금액)
					acBankTransactionNo	= ipg.getACBankTransactionNo()[0];   // 은행거래번호
					acIpgumNm			= ipg.getACIpgumNm()[0];   			// 입금자명
					acBankFee			= ipg.getACBankFee()[0];  			// 계좌이체 수수료
					acBankAmount		= ipg.getACBankAmount()[0];  		// 총결제금액(결제대상금액+ 수수료
					acBankRespCode		= ipg.getACBankRespCode()[0];  		// 오류코드
					acMessage1			= ipg.getACMessage1()[0];  			// 오류 message 1
					acMessage2			= ipg.getACMessage2()[0]; 			// 오류 message 2
					acCavvSele			= ipg.getACCavvSele()[0]; 			// 암호화데이터응답여부
					acFiller			= ipg.getACFiller()[0];  			// 예비
				}
				else	//신용카드 취소응답
				{
					rApprovalType		= ipg.getApprovalType()[0];
					rTransactionNo		= ipg.getTransactionNo()[0];	  		// 거래번호
					rStatus				= ipg.getStatus()[0];		  		// 상태 O : 승인, X : 거절
					rTradeDate			= ipg.getTradeDate()[0];		  		// 거래일자
					rTradeTime			= ipg.getTradeTime()[0];		  		// 거래시간
					rIssCode			= ipg.getIssCode()[0];		  		// 발급사코드
					rAquCode			= ipg.getAquCode()[0];		  		// 매입사코드
					rAuthNo				= ipg.getAuthNo()[0];		  		// 승인번호 or 거절시 오류코드
					rMessage1			= ipg.getMessage1()[0];		  		// 메시지1
					rMessage2			= ipg.getMessage2()[0];		  		// 메시지2
					rCardNo				= ipg.getCardNo()[0];		  		// 카드번호
					rExpDate			= ipg.getExpDate()[0];		  		// 유효기간
					rInstallment		= ipg.getInstallment()[0];	  		// 할부
					rAmount				= ipg.getAmount()[0];		  		// 금액
					rMerchantNo			= ipg.getMerchantNo()[0];	  		// 가맹점번호
					rAuthSendType		= ipg.getAuthSendType()[0];	  		// 전송구분= new String(this.read(2));
					rApprovalSendType	= ipg.getApprovalSendType()[0];		// 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
					rPoint1				= ipg.getPoint1()[0];		  		// Point1
					rPoint2				= ipg.getPoint2()[0];		  		// Point2
					rPoint3				= ipg.getPoint3()[0];		  		// Point3
					rPoint4				= ipg.getPoint4()[0];		  		// Point4
					rVanTransactionNo   = ipg.getVanTransactionNo()[0];      // Van거래번호
					rFiller				= ipg.getFiller()[0];		  		// 예비
					rAuthType			= ipg.getAuthType()[0];		  		// ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
					rMPIPositionType	= ipg.getMPIPositionType()[0]; 		// K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
					rMPIReUseType		= ipg.getMPIReUseType()[0];			// Y : 재사용, N : 재사용아님
					rEncData			= ipg.getEncData()[0];		  		// MPI, ISP 데이터
				}
			}
		}
		catch(Exception e) 
		{
			acMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
			rMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
		} // end of catch

		boolean isSuccess = false;
		
		if (rStatus.equals("O") || acStatus.equals("O")) 
		{
			isSuccess = true;
		}
		
		return isSuccess;
	}
	
	private String makePgLog(KSPayWebHostBean ipg, String approvalType) 
	{
		StringBuffer sb = new StringBuffer();
		
		String[] keys = new String[]{	"authyn", "trno", "trddt", "trdtm", "amt", "authno", "msg1", "msg2",
										"ordno", "isscd", "aqucd", "result" , "halbu" , "cbtrno" , "cbauthno" };
									
		for(String key : keys) 
		{
			String value = ipg.kspay_get_value(key);
			sb.append(key + " -> " + value + "\n");
		}
		
		return sb.toString();
	}
	
	private void headMessage(KSPayApprovalCancelBean ipg)
	{
		// Default(수정항목이 아님)-------------------------------------------------------
				String	EncType       = "2";					// 0: 암화안함, 1:openssl, 2: seed
				String	Version       = "0603";				    // 전문버전
				String	Type          = "00";					// 구분
				String	Resend        = "0";					// 전송구분 : 0 : 처음,  2: 재전송
				String	RequestDate   = new SimpleDateFormat(Const.DATETIME_FORMAT).format(new java.util.Date()); // 요청일자 : yyyymmddhhmmss
				String	KeyInType     = "K";					// KeyInType 여부 : S : Swap, K: KeyInType
				String	LineType      = "1";			        // lineType 0 : offline, 1:internet, 2:Mobile
				String	ApprovalCount = "1";				    // 복합승인갯수
				String 	GoodType      = "0";	                // 제품구분 0 : 실물, 1 : 디지털
				String	HeadFiller    = "";				        // 예비
			//-------------------------------------------------------------------------------

			// Header (입력값 (*) 필수항목)--------------------------------------------------
				String	StoreId		= environment.getProperty("pg.kspay.mid");		// *상점아이디
				String	OrderNumber	= "";			// 주문번호
				String	UserName    = "";			// *주문자명
				String	IdNum       = "";			// 주민번호 or 사업자번호(개인정보 보호법 개정으로 주민번호 수집불가)
				String	Email       = "";			// *email
				String 	GoodName    = "";			// *제품명
				String	PhoneNo     = "";			// *휴대폰번호
			// Header end -------------------------------------------------------------------
				
			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
					GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);
	}
	
	// 부분취소시 취소금액을 PG사 요구형식으로 변경 (ex: 9000 -> 000009000)
	private String cancelAmount_calculate(String cancAmount)
	{
		int prefixCnt = 9-cancAmount.length();
		String prefix = "";
				
		for(int i = 0; i < prefixCnt; i++)
		{
			prefix += "0";
		}
		return prefix + cancAmount;
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