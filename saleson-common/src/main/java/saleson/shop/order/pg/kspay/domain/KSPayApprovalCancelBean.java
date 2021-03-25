package saleson.shop.order.pg.kspay.domain;

import com.onlinepowers.framework.common.ServiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Properties;

/*
	Class Name    : KSPayApprovalCancelBean
	                승인/취소 요청 처리
	
	최초 작성일자 : 2004/12/29
	최초 작성자   : 박영준
	최종 수정일자 : 2013/09/23
	최종 수정자   : 이재석
	Version       : V1.3
*/
@Slf4j
@Getter @Setter @NoArgsConstructor
public class KSPayApprovalCancelBean{

	private static final String MSG_ENCODING = "ksc5601";
	
	private String              IPAddr;
	private int                 Port;
	
	private KSPaySocketBean     KSPaySocket;
	
	private String               HeadMsg;            //Head Message
	private String               DataMsg;
	private String               SendMsg;
	private String               ReceiveMsg;
	
	private int                  SendCount = 0, ReceiveCount = 0;
	
	private int MAXSIZE = 9;
	
    /* Haeder */
    private String   EncType         ,                            // 0: 암화안함, 1:openssl, 2: seed
                    Version         ,                            // 전문버전
                    Type            ,                            // 구분
                    Resend          ,                            // 전송구분 : 0 : 처음,  2: 재전송
                    RequestDate     ,                            // 요청일자 : yyyymmddhhmmss
                    StoreId         ,                            // 상점아이디
                    OrderNumber     ,                            // 주문번호
                    UserName        ,                            // 주문자명
                    IdNum           ,                            // 주민번호 or 사업자번호
                    Email           ,                            // email
                    GoodType        ,                            // 제품구분 1 : 실물, 2 : 디지털
                    GoodName        ,                            // 제품명
                    KeyInType       ,                            // KeyInType 여부 : S : Swap, K: KeyInType
                    LineType        ,                            // lineType 0 : offline, 1:internet, 2:Mobile
                    PhoneNo         ,                            // 휴대폰번호
                    ApprovalCount   ,                            // 복합승인갯수
                    HeadFiller      ;                            // 예비

    /* 신용카드승인결과 */
    private String[] ApprovalType     = new String[MAXSIZE],      // 승인구분
                    TransactionNo    = new String[MAXSIZE],      // 거래번호
                    Status           = new String[MAXSIZE],      // 상태 O : 승인 , X : 거절
                    TradeDate        = new String[MAXSIZE],      // 거래일자
                    TradeTime        = new String[MAXSIZE],      // 거래시간
                    IssCode          = new String[MAXSIZE],      // 발급사코드
                    AquCode          = new String[MAXSIZE],      // 매입사코드
                    AuthNo           = new String[MAXSIZE],      // 승인번호 or 거절시 오류코드
                    Message1         = new String[MAXSIZE],      // 메시지1
                    Message2         = new String[MAXSIZE],      // 메시지2
                    CardNo           = new String[MAXSIZE],      // 카드번호
                    ExpDate          = new String[MAXSIZE],      // 유효기간
                    Installment      = new String[MAXSIZE],      // 할부
                    Amount           = new String[MAXSIZE],      // 금액
                    MerchantNo       = new String[MAXSIZE],      // 가맹점번호
                    AuthSendType     = new String[MAXSIZE],      // 전송구분= new String[MAXSIZE]
                    ApprovalSendType = new String[MAXSIZE],      // 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
                    Point1           = new String[MAXSIZE],      
                    Point2           = new String[MAXSIZE],      
                    Point3           = new String[MAXSIZE],      
                    Point4           = new String[MAXSIZE],      
                    VanTransactionNo = new String[MAXSIZE],      // Van 거래번호
                    Filler           = new String[MAXSIZE],      // 예비
                    AuthType         = new String[MAXSIZE],      // ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
                    MPIPositionType  = new String[MAXSIZE],      // K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
                    MPIReUseType     = new String[MAXSIZE],      // Y : 재사용, N : 재사용아님
                    EncData          = new String[MAXSIZE];      // MPI, ISP 데이터

    /* 가상계좌승인결과 */
    private String[]
                    VATransactionNo  = new String[MAXSIZE],
                    VAStatus         = new String[MAXSIZE],
                    VATradeDate      = new String[MAXSIZE],
                    VATradeTime      = new String[MAXSIZE],
                    VABankCode       = new String[MAXSIZE],
                    VAVirAcctNo      = new String[MAXSIZE],
                    VAName           = new String[MAXSIZE],
                    VACloseDate      = new String[MAXSIZE],
                    VACloseTime      = new String[MAXSIZE],
                    VARespCode       = new String[MAXSIZE],
                    VAMessage1       = new String[MAXSIZE],
                    VAMessage2       = new String[MAXSIZE],
                    VAAmount         = new String[MAXSIZE],
                    VAFiller         = new String[MAXSIZE];

    /* 월드패스승인결과 */
    private String[]
                    WPTransactionNo  = new String[MAXSIZE],
                    WPStatus         = new String[MAXSIZE],
                    WPTradeDate      = new String[MAXSIZE],
                    WPTradeTime      = new String[MAXSIZE],
                    WPIssCode        = new String[MAXSIZE],      // 발급사코드
                    WPAuthNo         = new String[MAXSIZE],      // 승인번호
                    WPBalanceAmount  = new String[MAXSIZE],      // 잔액
                    WPLimitAmount    = new String[MAXSIZE],      // 한도액
                    WPMessage1       = new String[MAXSIZE],      // 메시지1
                    WPMessage2       = new String[MAXSIZE],      // 메시지2
                    WPCardNo         = new String[MAXSIZE],      // 카드번호
                    WPAmount         = new String[MAXSIZE],      // 금액
                    WPMerchantNo     = new String[MAXSIZE],      // 가맹점번호
                    WPFiller         = new String[MAXSIZE];      // 예비

    /* 포인트카드승인결과 */
    private String[]
                    PTransactionNo   = new String[MAXSIZE],      // 거래번호
                    PStatus          = new String[MAXSIZE],      // 상태 O : 승인 , X : 거절
                    PTradeDate       = new String[MAXSIZE],      // 거래일자
                    PTradeTime       = new String[MAXSIZE],      // 거래시간
                    PIssCode         = new String[MAXSIZE],      // 발급사코드
                    PAuthNo          = new String[MAXSIZE],      // 승인번호 or 거절시 오류코드
                    PMessage1        = new String[MAXSIZE],      // 메시지1
                    PMessage2        = new String[MAXSIZE],      // 메시지2
                    PPoint1          = new String[MAXSIZE],      // 거래포인트
                    PPoint2          = new String[MAXSIZE],      // 가용포인트
                    PPoint3          = new String[MAXSIZE],      // 누적포인트
                    PPoint4          = new String[MAXSIZE],      // 가맹점포인트
                    PMerchantNo      = new String[MAXSIZE],      // 가맹점번호
                    PNotice1         = new String[MAXSIZE],      //
                    PNotice2         = new String[MAXSIZE],      //
                    PNotice3         = new String[MAXSIZE],      //
                    PNotice4         = new String[MAXSIZE],      //
                    PFiller          = new String[MAXSIZE];      // 예비

    /* 현금영수증승인결과 */
    private String[]
                    HTransactionNo     = new String[MAXSIZE],    // 거래번호
                    HStatus            = new String[MAXSIZE],    // 오류구분 O:정상 X:거절
                    HCashTransactionNo = new String[MAXSIZE],    // 현금영수증 거래번호
                    HIncomeType        = new String[MAXSIZE],    // 0: 소득      1: 비소득
                    HTradeDate         = new String[MAXSIZE],    // 거래 개시 일자
                    HTradeTime         = new String[MAXSIZE],    // 거래 개시 시간
                    HMessage1          = new String[MAXSIZE],    // 응답 message1
                    HMessage2          = new String[MAXSIZE],    // 응답 message2
                    HCashMessage1      = new String[MAXSIZE],    // 국세청 메시지 1
                    HCashMessage2      = new String[MAXSIZE],    // 국세청 메시지 2
                    HFiller            = new String[MAXSIZE];    // 예비

    /*핸드폰 인증1차 승인결과*/
    private String[]
                    MB1ApprovalType  = new String[MAXSIZE],      /* 구분코드 */
                    MB1TransactionNo = new String[MAXSIZE],      /* 거래번호 */
                    MB1Status        = new String[MAXSIZE],      /* 상태 : O, X */
                    MB1TradeDate     = new String[MAXSIZE],      /* 거래일자 */
                    MB1TradeTime     = new String[MAXSIZE],      /* 거래시간 */
                    MB1Serverinfo    = new String[MAXSIZE],      /* 서버INFO : 업체에서는 보관 필요 없음 예비로 */
                    MB1Smsval        = new String[MAXSIZE],      /* 다날의 경우 space */
                    MB1Stanrespcode  = new String[MAXSIZE],      /* 응답코드 */
                    MB1Message       = new String[MAXSIZE],      /* 에러메시지 */
                    MB1Filler        = new String[MAXSIZE];

    /*핸드폰 인증2차 승인결과*/
    private String[]
                    MB2ApprovalType  = new String[MAXSIZE],      /* 구분코드 */
                    MB2TransactionNo = new String[MAXSIZE],      /* 거래번호 */
                    MB2Status        = new String[MAXSIZE],      /* 상태     */
                    MB2TradeDate     = new String[MAXSIZE],      /* 거래일자 */
                    MB2TradeTime     = new String[MAXSIZE],      /* 거래시간 */
                    MB2Stanrespcode  = new String[MAXSIZE],      /* 응답코드 */
                    MB2Message       = new String[MAXSIZE],      /* 응답메시지 */
                    MB2Filler        = new String[MAXSIZE];      /* 예비 */

    // 휴대폰결제결과
    private String[]
                    MTransactionNo   = new String[MAXSIZE],      // 거래번호 
                    MStatus          = new String[MAXSIZE],      // 오류구분 O:정상 X:거절
                    MTradeDate       = new String[MAXSIZE],      // 거래 일자
                    MTradeTime       = new String[MAXSIZE],      // 거래 시간
                    MBalAmount       = new String[MAXSIZE],      // 잔액
                    MRespCode        = new String[MAXSIZE],      // 응답코드
                    MRespMsg         = new String[MAXSIZE],      // 거래 개시 시간
                    MBypassMsg       = new String[MAXSIZE],      // Echo항목
                    MCompCode        = new String[MAXSIZE],      // 업체코드
                    MTid             = new String[MAXSIZE],      // 서비스제공업체 승인번호
                    MCommSele        = new String[MAXSIZE],      // SKT,KTF,LGT
                    MMobileNo        = new String[MAXSIZE],      // 휴대폰번호
                    MApprAmt         = new String[MAXSIZE],      // 승인금액
                    MFiller          = new String[MAXSIZE];      // 예비

    /*카드 BIN check 승인결과*/
    private String[]
                    BINTransactionNo = new String[MAXSIZE],      /* 거래번호					  */
                    BINStatus        = new String[MAXSIZE],      /* 오류구분 => O:승인 X:거절	  */
                    BINTradeDate     = new String[MAXSIZE],      /* 거래 일자(YYYYMMDD)           */
                    BINTradeTime     = new String[MAXSIZE],      /* 거래 시간(HHMMSS)             */
                    BINAquCode       = new String[MAXSIZE],      /* 발급기관코드                  */
                    BINMessage1      = new String[MAXSIZE],      /* 응답 message1                 */
                    BINMessage2      = new String[MAXSIZE],      /* 응답 message2                 */
                    BINAuthType      = new String[MAXSIZE],      /* 'I':ISP, 'M':MPI, ' ':기타    */
                    BINMpiLoc        = new String[MAXSIZE],      /* MPI모듈위치: '1':ILK, '2':e-paygen, '3':티지코프 */
                    BINFiller        = new String[MAXSIZE];      /* 예비                          */

    /* 상점상세정보 조회결과 */
    private String[]
                    SITransactionNo    = new String[MAXSIZE],    // 거래번호
                    SIStatus           = new String[MAXSIZE],    // 성공:O, 실패: X
                    SIRespCode         = new String[MAXSIZE],    // '0000' : 정상처리
                    SIAgenMembDealSele = new String[MAXSIZE],    // 자체대행구분
                    SIStartSele        = new String[MAXSIZE],    // 개시여부
                    SIEntrNumb         = new String[MAXSIZE],    // 사업자번호
                    SIShopName         = new String[MAXSIZE],    // 상점명
                    SIMembNumbGene     = new String[MAXSIZE],    // 일반 가맹점번호
                    SIMembNumbNoin     = new String[MAXSIZE],    // 무이자 가맹점번호
                    SIAlloMontType     = new String[MAXSIZE],    // 할부유형
                    SIFiller           = new String[MAXSIZE];    // 예비

    /* 계좌이체승인결과 */
    private String[]
                    ACTransactionNo     = new String[MAXSIZE],
                    ACStatus            = new String[MAXSIZE],
                    ACTradeDate         = new String[MAXSIZE],
                    ACTradeTime         = new String[MAXSIZE],
                    ACAcctSele          = new String[MAXSIZE],
                    ACFeeSele           = new String[MAXSIZE],
                    ACInjaName          = new String[MAXSIZE],
                    ACPareBankCode      = new String[MAXSIZE],
                    ACPareAcctNo        = new String[MAXSIZE],
                    ACCustBankCode      = new String[MAXSIZE],
                    ACCustAcctNo        = new String[MAXSIZE],
                    ACAmount	        = new String[MAXSIZE],
                    ACBankTransactionNo = new String[MAXSIZE],
                    ACIpgumNm           = new String[MAXSIZE],
                    ACBankFee           = new String[MAXSIZE],
                    ACBankAmount        = new String[MAXSIZE],
                    ACBankRespCode      = new String[MAXSIZE],
                    ACMessage1          = new String[MAXSIZE],
                    ACMessage2          = new String[MAXSIZE],
                    ACEntrNumb	        = new String[MAXSIZE],
                    ACShopPhone	        = new String[MAXSIZE],
                    ACCavvSele          = new String[MAXSIZE],
                    ACFiller            = new String[MAXSIZE],
                    ACEncData           = new String[MAXSIZE];
	
	public KSPayApprovalCancelBean(String IPAddr, int Port) {
		this.IPAddr = IPAddr;
		this.Port   = Port;
		
		this.SendCount    = 0;
		this.ReceiveCount = 0;
		this.SendMsg      = "";
	}
	
	public KSPayApprovalCancelBean(String ServerName, String PathName) throws IOException
	{
		Properties  Props    = new Properties();
		String      FileName = null;
		String      tmp;

		FileInputStream in = null;
		try {
			FileName = PathName + "new_env.cfg";

			in = new FileInputStream(FileName);
			Props.load(in);
		}
		catch( IOException e ) {
			throw new IOException("[KSPayApprovalCancelBean] new_env.cfg file not found!");
		} finally {
			if (in != null) {
				in.close();
			}
		}
		
		if( (this.IPAddr = Props.getProperty(ServerName + "_IPADDRESS")) == null )
			throw new IOException("[KSPayApprovalCancelBean] Exception on get property named 'IPADDRESS'");
		
		if( (tmp = Props.getProperty(ServerName + "_PORT")) != null )
			this.Port = Integer.parseInt(tmp);
		else
			throw new IOException("[KSPayApprovalCancelBean] Exception on get property named 'PORT'");
		
		this.SendCount    = 0;
		this.ReceiveCount = 0;
		this.SendMsg = "";
	}
	
	public KSPayApprovalCancelBean(String ServerName) throws IOException
	{
		Properties  Props    = new Properties();
		String      FileName = System.getProperty("IPGADMIN_PATH");
		String      tmp;

		FileInputStream in = null;
		try {
			if( FileName == null ) FileName = "/export/home/ipgadmin/cfg/new_env.cfg";

			in = new FileInputStream(FileName);
			Props.load(in);
		}
		catch( IOException e ) {
			throw new IOException("[KSPayApprovalCancelBean] Exception on config file\nYou must execute java -D IPGADMIN_PATH=filenameWithAbsolutePath classfile");

		} finally {
			if (in != null) {
				in.close();
			}
		}
		
		if( (this.IPAddr = Props.getProperty(ServerName + "_IPADDRESS")) == null )
			throw new IOException("[KSPayApprovalCancelBean] Exception on get property named 'IPADDRESS'");
		
		if( (tmp = Props.getProperty(ServerName + "_PORT")) != null )
			this.Port = Integer.parseInt(tmp);
		else
			throw new IOException("[KSPayApprovalCancelBean] Exception on get property named 'PORT'");
		
		this.SendMsg = "";
		this.SendCount    = 0;
		this.ReceiveCount = 0;
	}
	
	public boolean HeadMessage
	(
		String  pEncType         ,     // 0: 암화안함, 1:openssl, 2: seed
		String  pVersion         ,     // 전문버전
		String  pType            ,     // 구분
		String  pResend          ,     // 전송구분 : 0 : 처음,  2: 재전송
		String  pRequestDate     ,     // 요청일자 : yyyymmddhhmmss
		String  pStoreId         ,     // 상점아이디
		String  pOrderNumber     ,     // 주문번호
		String  pUserName        ,     // 주문자명
		String  pIdNum           ,     // 주민번호 or 사업자번호
		String  pEmail           ,     // email
		String  pGoodType        ,     // 제품구분 0 : 실물, 1 : 디지털
		String  pGoodName        ,     // 제품명
		String  pKeyInType       ,     // KeyInType 여부 : S : Swap, K: KeyInType
		String  pLineType        ,     // lineType 0 : offline, 1:internet, 2:Mobile
		String  pPhoneNo         ,     // 휴대폰번호
		String  pApprovalCount   ,     // 복합승인갯수
		String  pFiller          )     // 예비
	{
		StringBuffer TmpHeadMsg = new StringBuffer();
		
		TmpHeadMsg.append(this.format(pEncType         ,    1, 'X'));
		TmpHeadMsg.append(this.format(pVersion         ,    4, 'X'));
		TmpHeadMsg.append(this.format(pType            ,    2, 'X'));
		TmpHeadMsg.append(this.format(pResend          ,    1, 'X'));
		TmpHeadMsg.append(this.format(pRequestDate     ,   14, 'X'));
		TmpHeadMsg.append(this.format(pStoreId         ,   10, 'X'));
		TmpHeadMsg.append(this.format(pOrderNumber     ,   50, 'X'));
		TmpHeadMsg.append(this.format(pUserName        ,   50, 'X'));
		TmpHeadMsg.append(this.format(pIdNum           ,   13, 'X'));
		TmpHeadMsg.append(this.format(pEmail           ,   50, 'X'));
		TmpHeadMsg.append(this.format(pGoodType        ,    1, 'X'));
		TmpHeadMsg.append(this.format(pGoodName        ,   50, 'X'));
		TmpHeadMsg.append(this.format(pKeyInType       ,    1, 'X'));
		TmpHeadMsg.append(this.format(pLineType        ,    1, 'X'));
		TmpHeadMsg.append(this.format(pPhoneNo         ,   12, 'X'));
		TmpHeadMsg.append(this.format(pApprovalCount   ,    1, 'X'));
		TmpHeadMsg.append(this.format(pFiller          ,   35, 'X'));
		
		this.HeadMsg  = TmpHeadMsg.toString();
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] HeadMsg=[{}]", TmpHeadMsg.toString());
		
		return true;
	}
	
	// 신용카드승인요청 Body 1
	public boolean CreditDataMessage(
		String ApprovalType    ,    // 승인구분
		String InterestType    ,    // 일반/무이자구분 1:일반 2:무이자
		String TrackII         ,    // 카드번호=유효기간  or 거래번호
		String Installment     ,    // 할부  00일시불
		String Amount          ,    // 금액
		String Passwd          ,    // 비밀번호 앞2자리
		String IdNum           ,    // 주민번호  뒤7자리, 사업자번호10
		String CurrencyType    ,    // 통화구분 0:원화 1: 미화
		String BatchUseType    ,    // 거래번호배치사용구분  0:미사용 1:사용
		String CardSendType    ,    // 카드정보전송 0:미정송 1:카드번호,유효기간,할부,금액,가맹점번호 2:카드번호앞14자리 + "XXXX",유효기간,할부,금액,가맹점번호
		String VisaAuthYn      ,    // 비자인증유무 0:사용안함,7:SSL,9:비자인증
		String Domain          ,    // 도메인 자체가맹점(PG업체용)
		String IpAddr          ,    // IP ADDRESS 자체가맹점(PG업체용)
		String BusinessNumber  ,    // 사업자 번호 자체가맹점(PG업체용)
		String Filler          ,    // 예비
		String AuthType        ,    // ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
		String MPIPositionType ,    // K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
		String MPIReUseType    ,    // Y :  재사용, N : 재사용아님
		String EncData         )    // MPI, ISP 데이터
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType    ,   4, 'X'));
		TmpSendMsg.append(this.format(InterestType    ,   1, 'X'));
		TmpSendMsg.append(this.format(TrackII         ,  40, 'X'));
		TmpSendMsg.append(this.format(Installment     ,   2, '9'));
		TmpSendMsg.append(this.format(Amount          ,   9, '9'));
		TmpSendMsg.append(this.format(Passwd          ,   2, 'X'));
		TmpSendMsg.append(this.format(IdNum           ,  10, 'X'));
		TmpSendMsg.append(this.format(CurrencyType    ,   1, 'X'));
		TmpSendMsg.append(this.format(BatchUseType    ,   1, 'X'));
		TmpSendMsg.append(this.format(CardSendType    ,   1, 'X'));
		TmpSendMsg.append(this.format(VisaAuthYn      ,   1, 'X'));
		TmpSendMsg.append(this.format(Domain          ,  40, 'X'));
		TmpSendMsg.append(this.format(IpAddr          ,  20, 'X'));
		TmpSendMsg.append(this.format(BusinessNumber  ,  10, 'X'));
		TmpSendMsg.append(this.format(Filler          , 135, 'X'));
		TmpSendMsg.append(this.format(AuthType        ,   1, 'X'));
		TmpSendMsg.append(this.format(MPIPositionType ,   1, 'X'));
		TmpSendMsg.append(this.format(MPIReUseType    ,   1, 'X'));
		TmpSendMsg.append(EncData                                );
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] CreditDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	public boolean VirtualAccountDataMessage(
		String ApprovalType     ,    // 승인구분
		String BankCode         ,    // 은행코드
		String Amount           ,    // 금액
		String CloseDate        ,    // 마감일자
		String CloseTime        ,    // 마감시간
		String EscrowSele       ,    // 에스크로적용구분: 0:적용안함, 1:적용, 2:강제적용
		String VirFixSele       ,    // 가상계좌번호지정구분
		String VirAcctNo        ,    // 가상계좌번호
		String OrgTransactionNo ,    // 원거래거래번호
		String Filler           )    // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType      ,    4, 'X'));
		TmpSendMsg.append(this.format(BankCode          ,    6, 'X'));
		TmpSendMsg.append(this.format(Amount            ,    9, '9'));
		TmpSendMsg.append(this.format(CloseDate         ,    8, 'X'));
		TmpSendMsg.append(this.format(CloseTime         ,    6, 'X'));
		TmpSendMsg.append(this.format(EscrowSele        ,    1, 'X'));
		TmpSendMsg.append(this.format(VirFixSele        ,    1, 'X'));
		TmpSendMsg.append(this.format(VirAcctNo         ,   15, 'X'));
		TmpSendMsg.append(this.format(OrgTransactionNo  ,   12, 'X'));
		TmpSendMsg.append(this.format(Filler			,   52, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] VirtualAccountDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	public boolean VirtualAccountDataMessage(
		String ApprovalType   ,      // 승인구분
		String BankCode       ,      // 은행코드
		String Amount         ,      // 금액
		String Filler         )      // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType   ,    4, 'X'));
		TmpSendMsg.append(this.format(BankCode       ,    6, 'X'));
		TmpSendMsg.append(this.format(Amount         ,    9, '9'));
		TmpSendMsg.append(this.format(Filler         ,   81, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] VirtualAccountDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	// 월드패스카드
	public boolean WorldPassDataMessage(
		String ApprovalType   ,      // 승인구분
		String TrackII        ,      // 카드번호=유효기간  or 거래번호
		String Passwd         ,      // 비밀번호 앞2자리
		String Amount         ,      // 금액
		String WorldPassType  ,      // 선후불카드구분
		String AdultType      ,      // 성인확인구분
		String CardSendType   ,      // 카드정보전송 0:미전송 1:카드번호,유효기간,할부,금액,가맹점번호 2:카드번호앞14자리 + "XXXX",유효기간,할부,금액,가맹점번호
		String Filler         )      // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType    ,  4, 'X'));
		TmpSendMsg.append(this.format(TrackII+"=4912" , 40, 'X'));
		TmpSendMsg.append(this.format(Passwd          ,  4, 'X'));
		TmpSendMsg.append(this.format(Amount          ,  9, '9'));
		TmpSendMsg.append(this.format(WorldPassType   ,  1, 'X'));
		TmpSendMsg.append(this.format(AdultType       ,  1, 'X'));
		TmpSendMsg.append(this.format(CardSendType    ,  1, 'X'));
		TmpSendMsg.append(this.format(Filler          , 40, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] WorldPassDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	// 포인트카드승인
	public boolean PointDataMessage(
		String ApprovalType   ,       // 승인구분
		String TrackII        ,       // 카드번호=유효기간  or 거래번호
		String Amount         ,       // 금액
		String Passwd         ,       // 비밀번호 앞4자리
		String SaleType       ,       // 판매구분
		String Filler         )       // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType    ,  4, 'X'));
		TmpSendMsg.append(this.format(TrackII+"=4912" , 40, 'X'));
		TmpSendMsg.append(this.format(Amount          ,  9, '9'));
		TmpSendMsg.append(this.format(Passwd          ,  4, 'X'));
		TmpSendMsg.append(this.format(SaleType        ,  2, 'X'));
		TmpSendMsg.append(this.format(Filler          , 40, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] PointDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}

	// 현금영수증발급
	public boolean CashBillDataMessage(
		String ApprovalType  ,//H000:일반발급, H200:계좌이체, H600:가상계좌
		String TransactionNo ,//입금완료된 계좌이체, 가상계좌 거래번호
		String IssuSele      ,//0:일반발급(PG원거래번호 중복체크), 1:단독발급(주문번호 중복체크 : PG원거래 없음), 2:강제발급(중복체크 안함)
		String UserInfoSele  ,//0:주민등록번호, 1:사업자번호, 2:카드번호, 3:휴대폰번호, 4:기타
		String UserInfo      ,//주민등록번호, 사업자번호, 카드번호, 휴대폰번호, 기타
		String TranSele      ,//0: 개인, 1: 사업자
		String CallCode      ,//통화코드  (0: 원화, 1: 미화)
		String SupplyAmt     ,//공급가액
		String TaxAmt        ,//세금
		String SvcAmt        ,//봉사료
		String TotAmt        ,//현금영수증 발급금액
		String Filler)        //예비
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType  ,     4, 'X'));
		TmpSendMsg.append(this.format(TransactionNo ,    12, 'X'));
		TmpSendMsg.append(this.format(IssuSele      ,     1, 'X'));
		TmpSendMsg.append(this.format(UserInfoSele  ,     1, 'X'));
		TmpSendMsg.append(this.format(UserInfo      ,    37, 'X'));
		TmpSendMsg.append(this.format(TranSele      ,     1, 'X'));
		TmpSendMsg.append(this.format(CallCode      ,     1, 'X'));
		TmpSendMsg.append(this.format(SupplyAmt     ,     9, '9'));
		TmpSendMsg.append(this.format(TaxAmt        ,     9, '9'));
		TmpSendMsg.append(this.format(SvcAmt        ,     9, '9'));
		TmpSendMsg.append(this.format(TotAmt        ,     9, '9'));
		TmpSendMsg.append(this.format(Filler        ,   147, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] CashBillDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	//핸드폰 인증1차
	public boolean Mobile1DataMessage(
		String ApprovalType ,  /* 구분코드                     */
		String MobileNo	    ,  /* 휴대폰번호                   */
		String SocialNo     ,  /* 명의자 주민번호              */
		String UsersocialNo ,  /* LGT 실사용자 인증시에만 사용 */
		String Commsele     ,  /* SKT, KTF, LGT                */
		String Filler       )  /* 여비                         */
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType ,  4, 'X'));
		TmpSendMsg.append(this.format(MobileNo	   , 12, 'X'));
		TmpSendMsg.append(this.format(SocialNo     , 13, 'X'));
		TmpSendMsg.append(this.format(UsersocialNo , 13, 'X'));
		TmpSendMsg.append(this.format(Commsele     ,  3, 'X'));
		TmpSendMsg.append(this.format(Filler       ,155, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] Mobile1DataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	//핸드폰 인증2차
	public boolean Mobile2DataMessage(
		String ApprovalType    ,  /* 구분코드     */
		String TransactionNo   ,  /* 거래번호     */
		String Smsval          ,  /* sms 인증코드 */
		String Filler          )
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType   ,   4, 'X'));
		TmpSendMsg.append(this.format(TransactionNo  ,  12, 'X'));
		TmpSendMsg.append(this.format(Smsval         ,  20, 'X'));
		TmpSendMsg.append(this.format(Filler         , 164, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] Mobile2DataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	//핸드폰 결제1차
	public boolean MobileAppr1DataMessage(
		String ApprovalType     ,   /* 구분코드                     */
		String MobileNo         ,   /* 휴대폰번호                   */
		String SocialNo         ,   /* 명의자 주민번호              */
		String UsersocialNo     ,   /* LGT 실사용자 인증시에만 사용 */
		String Commsele         ,   /* SKT, KTF, LGT                */
		String ApprAmount       ,   /* 승인금액                     */
		String BypassMsg        ,   /* Echo Msg                     */
		String CompSele         ,   /* 기관코드                     */
		String Filler           )   /* 예비                         */
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType   ,         4, 'X'));
		TmpSendMsg.append(this.format(MobileNo       ,        12, 'X'));
		TmpSendMsg.append(this.format(SocialNo       ,        13, 'X'));
		TmpSendMsg.append(this.format(UsersocialNo   ,        13, 'X'));
		TmpSendMsg.append(this.format(Commsele       ,         3, 'X'));
		TmpSendMsg.append(this.format(ApprAmount     ,         9, '9'));
		TmpSendMsg.append(this.format(BypassMsg      ,       100, 'X'));
		TmpSendMsg.append(this.format(CompSele       ,         1, 'X'));
		TmpSendMsg.append(this.format(Filler         ,       145, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] MobileAppr1DataMessage=[{}]", TmpSendMsg.toString());
		return true;
	}
	
	//핸드폰 결제2차
	public boolean MobileAppr2DataMessage(
		String ApprovalType    ,  /* 구분코드     */
		String TransactionNo   ,  /* 거래번호     */
		String Smsval          ,  /* sms 인증코드 */
		String BypassMsg       ,  /* BypassMsg    */
		String Filler          )
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType   ,    4, 'X'));
		TmpSendMsg.append(this.format(TransactionNo  ,   12, 'X'));
		TmpSendMsg.append(this.format(Smsval         ,   20, 'X'));
		TmpSendMsg.append(this.format(BypassMsg      ,   20, 'X'));
		TmpSendMsg.append(this.format(Filler         ,  164, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] Mobile2DataMessage=[{}]", TmpSendMsg.toString());
		return true;
	}
	
	//계좌이체시작요청전문을 만든다.(send)
	public boolean AcctRequest_send(
		String ApprovalType,        // 승인구분
		String AcctSele    ,        // 계좌이체유형구문
		String FeeSele     ,        // 선/후불제구분
		String PareBankCode,        // 모계좌은행코드
		String PareAcctNo  ,        // 모계좌번호
		String CustBankCode,        // 고객계좌은행코드
		String Amount      ,        // 금액
		String InjaName    ,        // 인자명(상점명)
		String Filler      )        // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType,        4, 'X'));
		TmpSendMsg.append(this.format(AcctSele    ,        1, 'X'));
		TmpSendMsg.append(this.format(FeeSele 	  ,        1, 'X'));
		TmpSendMsg.append(this.format(PareBankCode,        6, 'X'));
		TmpSendMsg.append(this.format(PareAcctNo  ,       15, 'X'));
		TmpSendMsg.append(this.format(CustBankCode,        6, 'X'));
		TmpSendMsg.append(this.format(Amount      ,       13, '9'));
		TmpSendMsg.append(this.format(InjaName    ,       16, 'X'));
		TmpSendMsg.append(this.format(Filler      ,       38, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] AcctRequest_send=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	//계좌이체 인증승인 요청전문을 만든다.
	public boolean AcctRequest_iappr(
		String ApprovalType            ,  // 승인구분 코드
		String AcctSele                ,  // 계좌이체 구분 - 1:Dacom, 2:Pop Banking,	3:Scrapping 계좌이체, 4:승인형계좌이체, 5:금결원계좌이체
		String FeeSele                 ,  // 계좌이체 구분 - 선/후불제구분 -	1:선불,	2:후불
		String TransactionNo           ,  // 거래번호
		String BankCode                ,  // 입금모계좌코드
		String Amount                  ,  // 금액	(결제대상금액)
		String CustBankInja            ,  // 출금모계좌코드
		String BankTransactionNo       ,  // 은행거래번호
		String Filler                  ,  //
		String CertData                )  // 인증정보
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType        ,       4, 'X'));
		TmpSendMsg.append(this.format(AcctSele            ,       1, 'X'));
		TmpSendMsg.append(this.format(FeeSele             ,       1, 'X'));
		TmpSendMsg.append(this.format(TransactionNo       ,      12, 'X'));
		TmpSendMsg.append(this.format(BankCode            ,       6, 'X'));
		TmpSendMsg.append(this.format(Amount              ,      13, '9'));
		TmpSendMsg.append(this.format(CustBankInja        ,      30, 'X'));
		TmpSendMsg.append(this.format(BankTransactionNo   ,      30, 'X'));
		TmpSendMsg.append(this.format(Filler              ,      53, 'X'));
		TmpSendMsg.append(CertData                                       );
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("AcctRequest_iappr=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	//카드 취소
	public boolean CancelDataMessage(
		String ApprovalType  ,         // 카드취소유형
		String CancelType    ,         // 취소처리구분 1:거래번호, 2:주문번호
		String TransactionNo ,         // 거래번호
		String TradeDate     ,         // 거래일자
		String OrderNumber   ,         // 주문번호
		String CancelData    ,         // 취소data(차후추가)
		String Refundcheck   ,         // 현금영수증 취소여부 (1.거래취소, 2.오류발급취소, 3.기타)
		String Filler        )         // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType  ,  4, 'X'));
		TmpSendMsg.append(this.format(CancelType    ,  1, 'X'));
		TmpSendMsg.append(this.format(TransactionNo , 12, 'X'));
		TmpSendMsg.append(this.format(TradeDate     ,  8, 'X'));
		TmpSendMsg.append(this.format(OrderNumber   , 50, 'X'));
		TmpSendMsg.append(this.format(CancelData    , 42, 'X'));
		TmpSendMsg.append(this.format(Refundcheck   ,  1, 'X'));
		TmpSendMsg.append(this.format(Filler        , 32, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] CancelDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	//카드 BIN check
	public boolean CardBinDataMessage(
		String ApprovalType ,       // 승인구분
		String TrackII      ,       // 카드번호
		String Filler       )       // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType ,   4, 'X'));
		TmpSendMsg.append(this.format(TrackII      ,  40, 'X'));
		TmpSendMsg.append(this.format(Filler       ,  56, 'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] CardBinDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	// 상점정보 상세요청(A700)
	public boolean ShopInfoDetailDataMessage(
		String ApprovalType  ,       // 승인구분
		String ShopId        ,       // 상점아이디
		String BusiSele      ,       // 업무구분
		String CardCode      ,       // 카드코드
		String Filler        )       // 기타
	{
		StringBuffer TmpSendMsg = new StringBuffer();
		
		TmpSendMsg.append(this.format(ApprovalType  ,   4  ,'X'));
		TmpSendMsg.append(this.format(ShopId        ,  10  ,'X'));
		TmpSendMsg.append(this.format(BusiSele      ,   1  ,'X'));
		TmpSendMsg.append(this.format(CardCode      ,   6  ,'X'));
		TmpSendMsg.append(this.format(Filler        ,  79  ,'X'));
		
		this.SendMsg += TmpSendMsg.toString();
		this.SendCount++;
		if(ServiceType.LOCAL)
			log.info("[KSPayApprovalCancelBean] ShopInfoDetailDataMessage=[{}]", TmpSendMsg.toString());
		
		return true;
	}
	
	//승인이후에 결과값을 가지고 온다.
	
	public boolean ReceiveMessage() throws IOException
	{
		this.ReceiveMsg = "";
		StringBuffer TmpReceiveMsg = new StringBuffer();
		
		String Len         = new String(this.KSPaySocket.read( 4) ,MSG_ENCODING);          // 데이터 길이
		if(Len == null || Len.trim().equals("")) return false;
		this.EncType       = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);          // 0: 암화안함, 1:openssl, 2: seed
		this.Version       = new String(this.KSPaySocket.read( 4) ,MSG_ENCODING);          // 전문버전
		this.Type          = new String(this.KSPaySocket.read( 2) ,MSG_ENCODING);          // 구분
		this.Resend        = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);          // 전송구분 : 0 : 처음,  2: 재전송
		this.RequestDate   = new String(this.KSPaySocket.read(14) ,MSG_ENCODING);          // 요청일자 : yyyymmddhhmmss
		this.StoreId       = new String(this.KSPaySocket.read(10) ,MSG_ENCODING);          // 상점아이디
		this.OrderNumber   = new String(this.KSPaySocket.read(50) ,MSG_ENCODING);          // 주문번호
		this.UserName      = new String(this.KSPaySocket.read(50) ,MSG_ENCODING);          // 주문자명
		this.IdNum         = new String(this.KSPaySocket.read(13) ,MSG_ENCODING);          // 주민번호 or 사업자번호
		this.Email         = new String(this.KSPaySocket.read(50) ,MSG_ENCODING);          // email
		this.GoodType      = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);          // 제품구분 0 : 실물, 1 : 디지털
		this.GoodName      = new String(this.KSPaySocket.read(50) ,MSG_ENCODING);          // 제품명
		this.KeyInType     = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);          // KeyInType 여부 : 1 : Swap, 2: KeyIn
		this.LineType      = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);          // lineType 0 : offline, 1:internet, 2:Mobile
		this.PhoneNo       = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);          // 휴대폰번호
		this.ApprovalCount = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);          // 승인갯수
		this.HeadFiller    = new String(this.KSPaySocket.read(35) ,MSG_ENCODING);          // 예비
		
		TmpReceiveMsg.append(Len               );
		TmpReceiveMsg.append(this.EncType      );
		TmpReceiveMsg.append(this.Version      );
		TmpReceiveMsg.append(this.Type         );
		TmpReceiveMsg.append(this.Resend       );
		TmpReceiveMsg.append(this.RequestDate  );
		TmpReceiveMsg.append(this.StoreId      );
		TmpReceiveMsg.append(this.OrderNumber  );
		TmpReceiveMsg.append(this.UserName     );
		TmpReceiveMsg.append(this.IdNum        );
		TmpReceiveMsg.append(this.Email        );
		TmpReceiveMsg.append(this.GoodType     );
		TmpReceiveMsg.append(this.GoodName     );
		TmpReceiveMsg.append(this.KeyInType    );
		TmpReceiveMsg.append(this.LineType     );
		TmpReceiveMsg.append(this.PhoneNo      );
		TmpReceiveMsg.append(this.ApprovalCount);
		TmpReceiveMsg.append(this.HeadFiller   );
		
		this.ReceiveMsg = TmpReceiveMsg.toString();
		if(ServiceType.LOCAL)
		{
			log.info("[KSPayApprovalCancelBean] Header ReceiveMsg=[{}]", TmpReceiveMsg.toString());
			log.info("[KSPayApprovalCancelBean] ReceiveCount=[{}]", this.ReceiveCount+"]\n") ;
		}
		this.ReceiveCount = Integer.parseInt(this.ApprovalCount);
		
		return ReceiveDataMessage(Integer.parseInt(this.ApprovalCount));
	}
	
	public boolean ReceiveDataMessage(int iCnt) throws IOException
	{
		int iCreidtCnt = 0;
		int iVirAcctCnt = 0;
		int iPhoneCnt = 0;
		
		StringBuffer TmpReceiveMsg = new StringBuffer();
		
		//log.info("[KSPayApprovalCancelBean] Header ApprovalCount=[{}]", iCnt);
		for(int i=0; i < iCnt; i++)
		{
			this.ApprovalType[i]     = new String(KSPaySocket.read(4) ,MSG_ENCODING); // 승인구분
			if(ServiceType.LOCAL)
				log.info("[KSPayApprovalCancelBean] ApprovalType=[{}]", this.ApprovalType[i]);
			
			// 신용카드
			if(this.ApprovalType[i].substring(0,1).equals("1") || this.ApprovalType[i].substring(0,1).equals("I")) {
				if(this.ApprovalType[i].substring(1,2).equals("5")) {
					this.TransactionNo [i]   = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);    // 거래번호
					this.Status        [i]   = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);    // 상태 O : 승인, X : 거절
					this.TradeDate     [i]   = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);    // 거래일자
					this.TradeTime     [i]   = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);    // 거래시간
					this.IssCode       [i]   = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);    // 발급사코드
					this.Message1      [i]   = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);    // 메시지1
					this.Message2      [i]   = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);    // 메시지2
					
					TmpReceiveMsg = new StringBuffer();
					TmpReceiveMsg.append(this.ApprovalType  [i]   );
					TmpReceiveMsg.append(this.TransactionNo [i]   );
					TmpReceiveMsg.append(this.Status        [i]   );
					TmpReceiveMsg.append(this.TradeDate     [i]   );
					TmpReceiveMsg.append(this.TradeTime     [i]   );
					TmpReceiveMsg.append(this.IssCode       [i]   );
					TmpReceiveMsg.append(this.Message1      [i]   );
					TmpReceiveMsg.append(this.Message2      [i]   );
				}
				else {
					this.TransactionNo     [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // 거래번호
					this.Status            [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // 상태 O : 승인, X : 거절
					this.TradeDate         [i] = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);   // 거래일자
					this.TradeTime         [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);   // 거래시간
					this.IssCode           [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);   // 발급사코드
					this.AquCode           [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);   // 매입사코드
					this.AuthNo            [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // 승인번호 or 거절시 오류코드
					this.Message1          [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);   // 메시지1
					this.Message2          [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);   // 메시지2
					this.CardNo            [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);   // 카드번호
					this.ExpDate           [i] = new String(this.KSPaySocket.read( 4) ,MSG_ENCODING);   // 유효기간
					this.Installment       [i] = new String(this.KSPaySocket.read( 2) ,MSG_ENCODING);   // 할부
					this.Amount            [i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);   // 금액
					this.MerchantNo        [i] = new String(this.KSPaySocket.read(15) ,MSG_ENCODING);   // 가맹점번호
					this.AuthSendType      [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // 전송구분= new String(this.read(2));
					this.ApprovalSendType  [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
					this.Point1            [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // Point1
					this.Point2            [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // Point2
					this.Point3            [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // Point3
					this.Point4            [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // Point4
					this.VanTransactionNo  [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // Point4
					this.Filler            [i] = new String(this.KSPaySocket.read(82) ,MSG_ENCODING);   // 예비
					this.AuthType          [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // I : ISP거래, M : MPI거래, SPACE : 일반거래
					this.MPIPositionType   [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
					this.MPIReUseType      [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // Y : 재사용, N : 재사용아님
					
					String EncLen = "";
					if( "".equals(AuthType[i]) || AuthType[i].trim().equals("") )
					{
						this.EncData[i]      = "";
					}
					else
					{
						EncLen               = new String(this.KSPaySocket.read(5) ,MSG_ENCODING);
						this.EncData[i]      = new String(this.KSPaySocket.read(Integer.parseInt(EncLen)) ,MSG_ENCODING); // MPI, ISP 데이터
					}
					
					TmpReceiveMsg = new StringBuffer();
					TmpReceiveMsg.append(this.ApprovalType      [i]);
					TmpReceiveMsg.append(this.TransactionNo     [i]);
					TmpReceiveMsg.append(this.Status            [i]);
					TmpReceiveMsg.append(this.TradeDate         [i]);
					TmpReceiveMsg.append(this.TradeTime         [i]);
					TmpReceiveMsg.append(this.IssCode           [i]);
					TmpReceiveMsg.append(this.AquCode           [i]);
					TmpReceiveMsg.append(this.AuthNo            [i]);
					TmpReceiveMsg.append(this.Message1          [i]);
					TmpReceiveMsg.append(this.Message2          [i]);
					TmpReceiveMsg.append(this.CardNo            [i]);
					TmpReceiveMsg.append(this.ExpDate           [i]);
					TmpReceiveMsg.append(this.Installment       [i]);
					TmpReceiveMsg.append(this.Amount            [i]);
					TmpReceiveMsg.append(this.MerchantNo        [i]);
					TmpReceiveMsg.append(this.AuthSendType      [i]);
					TmpReceiveMsg.append(this.ApprovalSendType  [i]);
					TmpReceiveMsg.append(this.Point1            [i]);
					TmpReceiveMsg.append(this.Point2            [i]);
					TmpReceiveMsg.append(this.Point3            [i]);
					TmpReceiveMsg.append(this.Point4            [i]);
					TmpReceiveMsg.append(this.VanTransactionNo  [i]);
					TmpReceiveMsg.append(this.Filler            [i]);
					TmpReceiveMsg.append(this.AuthType          [i]);
					TmpReceiveMsg.append(this.MPIPositionType   [i]);
					TmpReceiveMsg.append(this.MPIReUseType      [i]);
					TmpReceiveMsg.append(EncLen                    );
					TmpReceiveMsg.append(this.EncData           [i]);
				}
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] Credit ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 포인트카드
			else if(this.ApprovalType[i].substring(0,1).equals("4")) {
				this.PTransactionNo[i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);      // 거래번호
				this.PStatus       [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);      // 상태 O : 승인 , X : 거절
				this.PTradeDate    [i] = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);      // 거래일자
				this.PTradeTime    [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);      // 거래시간
				this.PIssCode      [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);      // 발급사코드
				this.PAuthNo       [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);      // 승인번호 or 거절시 오류코드
				this.PMessage1     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);      // 메시지1
				this.PMessage2     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);      // 메시지2
				this.PPoint1       [i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);      // 거래포인트
				this.PPoint2       [i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);      // 가용포인트
				this.PPoint3       [i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);      // 누적포인트
				this.PPoint4       [i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);      // 가맹점포인트
				this.PMerchantNo   [i] = new String(this.KSPaySocket.read(15) ,MSG_ENCODING);      // 가맹점번호
				this.PNotice1      [i] = new String(this.KSPaySocket.read(40) ,MSG_ENCODING);      //
				this.PNotice2      [i] = new String(this.KSPaySocket.read(40) ,MSG_ENCODING);      //
				this.PNotice3      [i] = new String(this.KSPaySocket.read(40) ,MSG_ENCODING);      //
				this.PNotice4      [i] = new String(this.KSPaySocket.read(40) ,MSG_ENCODING);      //
				this.PFiller       [i] = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);      // 예비
				
				TmpReceiveMsg = new StringBuffer();
				TmpReceiveMsg.append(this.ApprovalType  [i] );
				TmpReceiveMsg.append(this.PTransactionNo[i] );
				TmpReceiveMsg.append(this.PStatus       [i] );
				TmpReceiveMsg.append(this.PTradeDate    [i] );
				TmpReceiveMsg.append(this.PTradeTime    [i] );
				TmpReceiveMsg.append(this.PIssCode      [i] );
				TmpReceiveMsg.append(this.PAuthNo       [i] );
				TmpReceiveMsg.append(this.PMessage1     [i] );
				TmpReceiveMsg.append(this.PMessage2     [i] );
				TmpReceiveMsg.append(this.PPoint1       [i] );
				TmpReceiveMsg.append(this.PPoint2       [i] );
				TmpReceiveMsg.append(this.PPoint3       [i] );
				TmpReceiveMsg.append(this.PPoint4       [i] );
				TmpReceiveMsg.append(this.PMerchantNo   [i] );
				TmpReceiveMsg.append(this.PNotice1      [i] );
				TmpReceiveMsg.append(this.PNotice2      [i] );
				TmpReceiveMsg.append(this.PNotice3      [i] );
				TmpReceiveMsg.append(this.PNotice4      [i] );
				TmpReceiveMsg.append(this.PFiller       [i] );
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] Point ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 가상계좌
			else if(this.ApprovalType[i].substring(0,1).equals("6")) {
				this.VATransactionNo[i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);
				this.VAStatus       [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);
				this.VATradeDate    [i] = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);
				this.VATradeTime    [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);
				this.VABankCode     [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);
				this.VAVirAcctNo    [i] = new String(this.KSPaySocket.read(15) ,MSG_ENCODING);
				this.VAName         [i] = new String(this.KSPaySocket.read(30) ,MSG_ENCODING);
				this.VACloseDate    [i] = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);
				this.VACloseTime    [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);
				this.VARespCode     [i] = new String(this.KSPaySocket.read( 4) ,MSG_ENCODING);
				this.VAMessage1     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);
				this.VAMessage2     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);
				this.VAFiller       [i] = new String(this.KSPaySocket.read(36) ,MSG_ENCODING);
				
				TmpReceiveMsg = new StringBuffer();
				TmpReceiveMsg.append(this.ApprovalType   [i] );
				TmpReceiveMsg.append(this.VATransactionNo[i] );
				TmpReceiveMsg.append(this.VAStatus       [i] );
				TmpReceiveMsg.append(this.VATradeDate    [i] );
				TmpReceiveMsg.append(this.VATradeTime    [i] );
				TmpReceiveMsg.append(this.VABankCode     [i] );
				TmpReceiveMsg.append(this.VAVirAcctNo    [i] );
				TmpReceiveMsg.append(this.VAName         [i] );
				TmpReceiveMsg.append(this.VACloseDate    [i] );
				TmpReceiveMsg.append(this.VACloseTime    [i] );
				TmpReceiveMsg.append(this.VARespCode     [i] );
				TmpReceiveMsg.append(this.VAMessage1     [i] );
				TmpReceiveMsg.append(this.VAMessage2     [i] );
				TmpReceiveMsg.append(this.VAFiller       [i] );
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] Virtual ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 월드패스
			else if(this.ApprovalType[i].substring(0,1).equals("7")) {
				this.WPTransactionNo[i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);
				this.WPStatus       [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);
				this.WPTradeDate    [i] = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);
				this.WPTradeTime    [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);
				this.WPIssCode      [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);
				this.WPAuthNo       [i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);
				this.WPBalanceAmount[i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);
				this.WPLimitAmount  [i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);
				this.WPMessage1     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);
				this.WPMessage2     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);
				this.WPCardNo       [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);
				this.WPAmount       [i] = new String(this.KSPaySocket.read( 9) ,MSG_ENCODING);
				this.WPMerchantNo   [i] = new String(this.KSPaySocket.read(15) ,MSG_ENCODING);
				this.WPFiller       [i] = new String(this.KSPaySocket.read(11) ,MSG_ENCODING);
				
				TmpReceiveMsg = new StringBuffer();
				TmpReceiveMsg.append(this.ApprovalType   [i] );
				TmpReceiveMsg.append(this.WPTransactionNo[i] );
				TmpReceiveMsg.append(this.WPStatus       [i] );
				TmpReceiveMsg.append(this.WPTradeDate    [i] );
				TmpReceiveMsg.append(this.WPTradeTime    [i] );
				TmpReceiveMsg.append(this.WPIssCode      [i] );
				TmpReceiveMsg.append(this.WPAuthNo       [i] );
				TmpReceiveMsg.append(this.WPBalanceAmount[i] );
				TmpReceiveMsg.append(this.WPLimitAmount  [i] );
				TmpReceiveMsg.append(this.WPMessage1     [i] );
				TmpReceiveMsg.append(this.WPMessage2     [i] );
				TmpReceiveMsg.append(this.WPCardNo       [i] );
				TmpReceiveMsg.append(this.WPAmount       [i] );
				TmpReceiveMsg.append(this.WPMerchantNo   [i] );
				TmpReceiveMsg.append(this.WPFiller       [i] );
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] WorldPass ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 현금영수증
			else if(this.ApprovalType[i].substring(0,1).equals("H")) {
				
				this.HTransactionNo     [i] = new String(this.KSPaySocket.read(12 ) ,MSG_ENCODING);   // 거래번호
				this.HStatus            [i] = new String(this.KSPaySocket.read(1  ) ,MSG_ENCODING);   // 오류구분 O:정상 X:거절
				this.HCashTransactionNo [i] = new String(this.KSPaySocket.read(12 ) ,MSG_ENCODING);   // 현금영수증 거래번호
				this.HIncomeType        [i] = new String(this.KSPaySocket.read(1  ) ,MSG_ENCODING);   // 0: 소득      1: 비소득
				this.HTradeDate         [i] = new String(this.KSPaySocket.read(8  ) ,MSG_ENCODING);   // 거래 개시 일자
				this.HTradeTime         [i] = new String(this.KSPaySocket.read(6  ) ,MSG_ENCODING);   // 거래 개시 시간
				this.HMessage1          [i] = new String(this.KSPaySocket.read(16 ) ,MSG_ENCODING);   // 응답 message1
				this.HMessage2          [i] = new String(this.KSPaySocket.read(16 ) ,MSG_ENCODING);   // 응답 message2
				this.HCashMessage1      [i] = new String(this.KSPaySocket.read(20 ) ,MSG_ENCODING);   // 국세청 메시지 1
				this.HCashMessage2      [i] = new String(this.KSPaySocket.read(20 ) ,MSG_ENCODING);   // 국세청 메시지 2
				this.HFiller            [i] = new String(this.KSPaySocket.read(150) ,MSG_ENCODING);   // 예비
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.HTransactionNo    [i]);
				TmpReceiveMsg.append(this.HStatus           [i]);
				TmpReceiveMsg.append(this.HCashTransactionNo[i]);
				TmpReceiveMsg.append(this.HIncomeType       [i]);
				TmpReceiveMsg.append(this.HTradeDate        [i]);
				TmpReceiveMsg.append(this.HTradeTime        [i]);
				TmpReceiveMsg.append(this.HMessage1         [i]);
				TmpReceiveMsg.append(this.HMessage2         [i]);
				TmpReceiveMsg.append(this.HCashMessage1     [i]);
				TmpReceiveMsg.append(this.HCashMessage2     [i]);
				TmpReceiveMsg.append(this.HFiller           [i]);
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] CashBill ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 핸드폰 인증 1차 : M020
			else if(this.ApprovalType[i].substring(0,3).equals("M02"))  {
				
				this.MB1TransactionNo [i] = new String(this.KSPaySocket.read(12  ) ,MSG_ENCODING);   /* 거래번호 */
				this.MB1Status        [i] = new String(this.KSPaySocket.read(1   ) ,MSG_ENCODING);   /* 상태 : O, X */
				this.MB1TradeDate     [i] = new String(this.KSPaySocket.read(8   ) ,MSG_ENCODING);   /* 거래일자 */
				this.MB1TradeTime     [i] = new String(this.KSPaySocket.read(6   ) ,MSG_ENCODING);   /* 거래시간 */
				this.MB1Serverinfo    [i] = new String(this.KSPaySocket.read(128 ) ,MSG_ENCODING);   /* 서버INFO : 업체에서는 보관 필요 없음 예비로 */
				this.MB1Smsval        [i] = new String(this.KSPaySocket.read(20  ) ,MSG_ENCODING);   /* 다날의 경우 space */
				this.MB1Stanrespcode  [i] = new String(this.KSPaySocket.read(4   ) ,MSG_ENCODING);   /* 응답코드 */
				this.MB1Message       [i] = new String(this.KSPaySocket.read(200 ) ,MSG_ENCODING);   /* 에러메시지 */
				this.MB1Filler        [i] = new String(this.KSPaySocket.read(117 ) ,MSG_ENCODING);
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.MB1TransactionNo[i]);
				TmpReceiveMsg.append(this.MB1Status       [i]);
				TmpReceiveMsg.append(this.MB1TradeDate    [i]);
				TmpReceiveMsg.append(this.MB1TradeTime    [i]);
				TmpReceiveMsg.append(this.MB1Serverinfo   [i]);
				TmpReceiveMsg.append(this.MB1Smsval       [i]);
				TmpReceiveMsg.append(this.MB1Stanrespcode [i]);
				TmpReceiveMsg.append(this.MB1Message      [i]);
				TmpReceiveMsg.append(this.MB1Filler       [i]);
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] Mobile1 ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 핸드폰 인증 2차 : M030
			else if(this.ApprovalType[i].substring(0,3).equals("M03")) {
				
				this.MB2TransactionNo[i] = new String(this.KSPaySocket.read( 12   ) ,MSG_ENCODING);   /* 거래번호 */
				this.MB2Status       [i] = new String(this.KSPaySocket.read(  1   ) ,MSG_ENCODING);   /* 상태     */
				this.MB2TradeDate    [i] = new String(this.KSPaySocket.read(  8   ) ,MSG_ENCODING);   /* 거래일자 */
				this.MB2TradeTime    [i] = new String(this.KSPaySocket.read(  6   ) ,MSG_ENCODING);   /* 거래시간 */
				this.MB2Stanrespcode [i] = new String(this.KSPaySocket.read(  4   ) ,MSG_ENCODING);   /* 응답코드 */
				this.MB2Message      [i] = new String(this.KSPaySocket.read(200   ) ,MSG_ENCODING);   /* 응답메시지 */
				this.MB2Filler       [i] = new String(this.KSPaySocket.read(115   ) ,MSG_ENCODING);   /* 예비 */
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.MB2TransactionNo[i]);
				TmpReceiveMsg.append(this.MB2Status       [i]);
				TmpReceiveMsg.append(this.MB2TradeDate    [i]);
				TmpReceiveMsg.append(this.MB2TradeTime    [i]);
				TmpReceiveMsg.append(this.MB2Stanrespcode [i]);
				TmpReceiveMsg.append(this.MB2Message      [i]);
				TmpReceiveMsg.append(this.MB2Filler       [i]);
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] Mobile2 ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 핸드폰 결제 1차 : M120
			else if(this.ApprovalType[i].substring(0,3).equals("M12") ||	//핸드폰결제1차
				this.ApprovalType[i].substring(0,3).equals("M11")) 	//핸드폰결제취소
			{
				
				this.MTransactionNo  [i] = new String(this.KSPaySocket.read(12  ) ,MSG_ENCODING);     /* 거래번호 */
				this.MStatus         [i] = new String(this.KSPaySocket.read(1   ) ,MSG_ENCODING);     /* 상태 : O, X */
				this.MTradeDate      [i] = new String(this.KSPaySocket.read(8   ) ,MSG_ENCODING);     /* 거래일자 */
				this.MTradeTime      [i] = new String(this.KSPaySocket.read(6   ) ,MSG_ENCODING);     /* 거래시간 */
				this.MBalAmount      [i] = new String(this.KSPaySocket.read(9   ) ,MSG_ENCODING);     /* 잔액 */
				this.MRespCode       [i] = new String(this.KSPaySocket.read(4   ) ,MSG_ENCODING);     /* 응답코드 */
				this.MRespMsg        [i] = new String(this.KSPaySocket.read(200 ) ,MSG_ENCODING);     /* 응답메시지 */
				this.MBypassMsg      [i] = new String(this.KSPaySocket.read(100 ) ,MSG_ENCODING);     /* Echo 메시지 */
				this.MCompCode       [i] = new String(this.KSPaySocket.read(6   ) ,MSG_ENCODING);     /* 기관코드 */
				this.MFiller         [i] = new String(this.KSPaySocket.read(150 ) ,MSG_ENCODING);     /* 예비 */
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.MTransactionNo[i]);
				TmpReceiveMsg.append(this.MStatus       [i]);
				TmpReceiveMsg.append(this.MTradeDate    [i]);
				TmpReceiveMsg.append(this.MTradeTime    [i]);
				TmpReceiveMsg.append(this.MBalAmount    [i]);
				TmpReceiveMsg.append(this.MRespCode     [i]);
				TmpReceiveMsg.append(this.MRespMsg	    [i]);
				TmpReceiveMsg.append(this.MBypassMsg    [i]);
				TmpReceiveMsg.append(this.MCompCode     [i]);
				TmpReceiveMsg.append(this.MFiller       [i]);
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] Mobile ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 핸드폰 결제 2차 : M130
			else if(this.ApprovalType[i].substring(0,3).equals("M13"))
			{
				
				this.MTransactionNo   [i] = new String(this.KSPaySocket.read(12  ) ,MSG_ENCODING);      /* 거래번호 */
				this.MStatus          [i] = new String(this.KSPaySocket.read(1   ) ,MSG_ENCODING);      /* 상태 : O, X */
				this.MTradeDate       [i] = new String(this.KSPaySocket.read(8   ) ,MSG_ENCODING);      /* 거래일자 */
				this.MTradeTime       [i] = new String(this.KSPaySocket.read(6   ) ,MSG_ENCODING);      /* 거래시간 */
				this.MBalAmount       [i] = new String(this.KSPaySocket.read(9   ) ,MSG_ENCODING);      /* 잔액 */
				this.MTid             [i] = new String(this.KSPaySocket.read(20  ) ,MSG_ENCODING);      /* Tid */
				this.MRespCode        [i] = new String(this.KSPaySocket.read(4   ) ,MSG_ENCODING);      /* 응답코드 */
				this.MRespMsg         [i] = new String(this.KSPaySocket.read(200 ) ,MSG_ENCODING);      /* 응답메시지 */
				this.MBypassMsg       [i] = new String(this.KSPaySocket.read(100 ) ,MSG_ENCODING);      /* Echo 메시지 */
				this.MCompCode        [i] = new String(this.KSPaySocket.read(6   ) ,MSG_ENCODING);      /* 기관코드 */
				this.MCommSele        [i] = new String(this.KSPaySocket.read(3   ) ,MSG_ENCODING);      /* SKT,KTF,LGT */
				this.MMobileNo        [i] = new String(this.KSPaySocket.read(12  ) ,MSG_ENCODING);      /* 휴대폰번호 */
				this.MApprAmt         [i] = new String(this.KSPaySocket.read(9   ) ,MSG_ENCODING);      /* 승인금액 */
				this.MFiller          [i] = new String(this.KSPaySocket.read(106 ) ,MSG_ENCODING);      /* 예비 */
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.MTransactionNo[i]);
				TmpReceiveMsg.append(this.MStatus       [i]);
				TmpReceiveMsg.append(this.MTradeDate    [i]);
				TmpReceiveMsg.append(this.MTradeTime    [i]);
				TmpReceiveMsg.append(this.MBalAmount    [i]);
				TmpReceiveMsg.append(this.MTid          [i]);
				TmpReceiveMsg.append(this.MRespCode     [i]);
				TmpReceiveMsg.append(this.MRespMsg      [i]);
				TmpReceiveMsg.append(this.MBypassMsg    [i]);
				TmpReceiveMsg.append(this.MCompCode     [i]);
				TmpReceiveMsg.append(this.MFiller       [i]);
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] Mobile ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 계좌이체시작요청
			else if(this.ApprovalType[i].substring(0,3).equals("210")||this.ApprovalType[i].substring(0,3).equals("240")) {
				this.ACTransactionNo[i] = new String(this.KSPaySocket.read(12) ,MSG_ENCODING);   // 거래번호
				this.ACStatus       [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // 오류구분:- O:승인 X:거절
				this.ACTradeDate    [i] = new String(this.KSPaySocket.read( 8) ,MSG_ENCODING);   // 거래 개시 일자(YYYYMMDD)
				this.ACTradeTime    [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);   // 거래 개시 시간(HHMMSS)
				this.ACAcctSele     [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // 계좌이체 구분 -	1:Dacom, 2:Pop Banking,	3:실시간계좌이체, 4:X
				this.ACFeeSele      [i] = new String(this.KSPaySocket.read( 1) ,MSG_ENCODING);   // 선/후불제구분 -	1:선불,	2:후불
				this.ACPareBankCode [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);   // 입금모계좌은행코드
				this.ACPareAcctNo   [i] = new String(this.KSPaySocket.read(15) ,MSG_ENCODING);   // 입금모계좌 번호
				this.ACCustBankCode [i] = new String(this.KSPaySocket.read( 6) ,MSG_ENCODING);   // 출급은행코드
				this.ACAmount       [i] = new String(this.KSPaySocket.read(13) ,MSG_ENCODING);   // 금액
				this.ACInjaName     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);   // 인자명(상점명)
				this.ACMessage1     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);   // 응답 message1
				this.ACMessage2     [i] = new String(this.KSPaySocket.read(16) ,MSG_ENCODING);   // 응답 message2
				this.ACEntrNumb     [i] = new String(this.KSPaySocket.read(10) ,MSG_ENCODING);   // 사업자번호
				this.ACShopPhone    [i] = new String(this.KSPaySocket.read(20) ,MSG_ENCODING);   // 전화번호
				this.ACFiller       [i] = new String(this.KSPaySocket.read(49) ,MSG_ENCODING);   // 예비
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.ACTransactionNo   [i]);
				TmpReceiveMsg.append(this.ACStatus          [i]);
				TmpReceiveMsg.append(this.ACTradeDate       [i]);
				TmpReceiveMsg.append(this.ACTradeTime       [i]);
				TmpReceiveMsg.append(this.ACAcctSele        [i]);
				TmpReceiveMsg.append(this.ACFeeSele         [i]);
				TmpReceiveMsg.append(this.ACPareBankCode    [i]);
				TmpReceiveMsg.append(this.ACPareAcctNo      [i]);
				TmpReceiveMsg.append(this.ACCustBankCode    [i]);
				TmpReceiveMsg.append(this.ACAmount          [i]);
				TmpReceiveMsg.append(this.ACInjaName        [i]);
				TmpReceiveMsg.append(this.ACMessage1        [i]);
				TmpReceiveMsg.append(this.ACMessage2        [i]);
				TmpReceiveMsg.append(this.ACEntrNumb        [i]);
				TmpReceiveMsg.append(this.ACShopPhone       [i]);
				TmpReceiveMsg.append(this.ACFiller          [i]);
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] AcctRequest_send(2100,2400) ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 계좌이체결과반영요청 || 계좌이체승인요청 || 계좌이체취소요청
			else if	(this.ApprovalType[i].substring(0,1).equals("2")) {
				this.ACTransactionNo    [i] = new String(this.KSPaySocket.read( 12) ,MSG_ENCODING);   // 거래번호
				this.ACStatus           [i] = new String(this.KSPaySocket.read(  1) ,MSG_ENCODING);   // 오류구분 :승인 X:거절
				this.ACTradeDate        [i] = new String(this.KSPaySocket.read(  8) ,MSG_ENCODING);   // 거래 개시 일자(YYYYMMDD)
				this.ACTradeTime        [i] = new String(this.KSPaySocket.read(  6) ,MSG_ENCODING);   // 거래 개시 시간(HHMMSS)
				this.ACAcctSele         [i] = new String(this.KSPaySocket.read(  1) ,MSG_ENCODING);   // 계좌이체 구분 -	1:Dacom, 2:Pop Banking,	3:실시간계좌이체 4: 승인형계좌이체
				this.ACFeeSele          [i] = new String(this.KSPaySocket.read(  1) ,MSG_ENCODING);   // 선/후불제구분 -	1:선불,	2:후불
				this.ACInjaName         [i] = new String(this.KSPaySocket.read( 16) ,MSG_ENCODING);   // 인자명(통장인쇄메세지-상점명)
				this.ACPareBankCode     [i] = new String(this.KSPaySocket.read(  6) ,MSG_ENCODING);   // 입금모계좌코드
				this.ACPareAcctNo       [i] = new String(this.KSPaySocket.read( 15) ,MSG_ENCODING);   // 입금모계좌번호
				this.ACCustBankCode     [i] = new String(this.KSPaySocket.read(  6) ,MSG_ENCODING);   // 출금모계좌코드
				this.ACCustAcctNo       [i] = new String(this.KSPaySocket.read( 15) ,MSG_ENCODING);   // 출금모계좌번호
				this.ACAmount           [i] = new String(this.KSPaySocket.read( 13) ,MSG_ENCODING);   // 금액	(결제대상금액)
				this.ACBankTransactionNo[i] = new String(this.KSPaySocket.read( 30) ,MSG_ENCODING);   // 은행거래번호
				this.ACIpgumNm          [i] = new String(this.KSPaySocket.read( 20) ,MSG_ENCODING);   // 입금자명
				this.ACBankFee          [i] = new String(this.KSPaySocket.read( 13) ,MSG_ENCODING);   // 계좌이체 수수료
				this.ACBankAmount       [i] = new String(this.KSPaySocket.read( 13) ,MSG_ENCODING);   // 총결제금액(결제대상금액+ 수수료
				this.ACBankRespCode     [i] = new String(this.KSPaySocket.read(  4) ,MSG_ENCODING);   // 오류코드
				this.ACMessage1         [i] = new String(this.KSPaySocket.read( 16) ,MSG_ENCODING);   // 오류 message 1
				this.ACMessage2         [i] = new String(this.KSPaySocket.read( 16) ,MSG_ENCODING);   // 오류 message 2
				this.ACCavvSele         [i] = new String(this.KSPaySocket.read(  1) ,MSG_ENCODING);   // 암호화데이터응답여부
				this.ACFiller           [i] = new String(this.KSPaySocket.read(183) ,MSG_ENCODING);   // 예비
				
				String EncLen = "";
				this.ACEncData[i] = "";
				if( ACCavvSele[i].equals("1") )
				{
					EncLen               = new String(this.KSPaySocket.read(5) ,MSG_ENCODING);
					this.ACEncData[i]    = new String(this.KSPaySocket.read(Integer.parseInt(EncLen)) ,MSG_ENCODING); // 금결원암호화응답
				}
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.ACTransactionNo       [i]);
				TmpReceiveMsg.append(this.ACStatus              [i]);
				TmpReceiveMsg.append(this.ACTradeDate           [i]);
				TmpReceiveMsg.append(this.ACTradeTime           [i]);
				TmpReceiveMsg.append(this.ACAcctSele            [i]);
				TmpReceiveMsg.append(this.ACFeeSele             [i]);
				TmpReceiveMsg.append(this.ACInjaName            [i]);
				TmpReceiveMsg.append(this.ACPareBankCode        [i]);
				TmpReceiveMsg.append(this.ACPareAcctNo          [i]);
				TmpReceiveMsg.append(this.ACCustBankCode        [i]);
				TmpReceiveMsg.append(this.ACCustAcctNo          [i]);
				TmpReceiveMsg.append(this.ACAmount              [i]);
				TmpReceiveMsg.append(this.ACBankTransactionNo   [i]);
				TmpReceiveMsg.append(this.ACIpgumNm             [i]);
				TmpReceiveMsg.append(this.ACBankFee             [i]);
				TmpReceiveMsg.append(this.ACBankAmount          [i]);
				TmpReceiveMsg.append(this.ACBankRespCode        [i]);
				TmpReceiveMsg.append(this.ACMessage1            [i]);
				TmpReceiveMsg.append(this.ACMessage2            [i]);
				TmpReceiveMsg.append(this.ACCavvSele            [i]);
				TmpReceiveMsg.append(this.ACFiller              [i]);
				
				if( EncLen.length() == 5 )
				{
					TmpReceiveMsg.append(EncLen                    );
					TmpReceiveMsg.append(this.ACEncData         [i]);
				}
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] AcctRequest_recv,appr(2200,2300,2420) ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
			// 상점상세정보 조회결과
			else if(this.ApprovalType[i].substring(0,2).equals("A7")) {
				this.SITransactionNo   [i] = new String(this.KSPaySocket.read( 12) ,MSG_ENCODING);   // 거래번호
				this.SIStatus          [i] = new String(this.KSPaySocket.read(  1) ,MSG_ENCODING);   // 성공:O, 실패: X
				this.SIRespCode        [i] = new String(this.KSPaySocket.read(  4) ,MSG_ENCODING);   // '0000' : 정상처리
				this.SIAgenMembDealSele[i] = new String(this.KSPaySocket.read(  1) ,MSG_ENCODING);   // 자체대행구분
				this.SIStartSele       [i] = new String(this.KSPaySocket.read(  1) ,MSG_ENCODING);   // 개시여부
				this.SIEntrNumb        [i] = new String(this.KSPaySocket.read( 10) ,MSG_ENCODING);   // 사업자번호
				this.SIShopName        [i] = new String(this.KSPaySocket.read( 30) ,MSG_ENCODING);   // 상점명
				this.SIMembNumbGene    [i] = new String(this.KSPaySocket.read( 15) ,MSG_ENCODING);   // 일반 가맹점번호
				this.SIMembNumbNoin    [i] = new String(this.KSPaySocket.read( 15) ,MSG_ENCODING);   // 무이자 가맹점번호
				this.SIAlloMontType    [i] = new String(this.KSPaySocket.read(200) ,MSG_ENCODING);   // 할부유형
				this.SIFiller          [i] = new String(this.KSPaySocket.read(207) ,MSG_ENCODING);   // 예비
				
				TmpReceiveMsg = new StringBuffer();
				
				TmpReceiveMsg.append(this.SITransactionNo   [i]);
				TmpReceiveMsg.append(this.SIStatus          [i]);
				TmpReceiveMsg.append(this.SIRespCode        [i]);
				TmpReceiveMsg.append(this.SIAgenMembDealSele[i]);
				TmpReceiveMsg.append(this.SIStartSele       [i]);
				TmpReceiveMsg.append(this.SIEntrNumb        [i]);
				TmpReceiveMsg.append(this.SIShopName        [i]);
				TmpReceiveMsg.append(this.SIMembNumbGene    [i]);
				TmpReceiveMsg.append(this.SIMembNumbNoin    [i]);
				TmpReceiveMsg.append(this.SIAlloMontType    [i]);
				TmpReceiveMsg.append(this.SIFiller          [i]);
				
				this.ReceiveMsg += TmpReceiveMsg.toString();
				if(ServiceType.LOCAL)
					log.info("[KSPayApprovalCancelBean] ShopInfoDetail(A700) ReceiveMsg[{}] [{}]", i, TmpReceiveMsg.toString());
			}
		}
		return true;
	}
	
	
	public boolean SendSocket(String Flag)
	{
		int	    state_flag        ;     /*최종상태플래그*/
		String  real_send_msg = "";
		
		try	{
			real_send_msg = this.HeadMsg+this.SendMsg;
			
			String	pDataLen = this.format(""+real_send_msg.getBytes(MSG_ENCODING).length, 4, '9');
			real_send_msg = pDataLen + real_send_msg;
			
			if(ServiceType.LOCAL)
			{
				log.info("[KSPayApprovalCancelBean] >>>>>>>  SendSocket Start~!! <<<<<<<<");
				log.info("[KSPayApprovalCancelBean] SendMessage=[{}]", real_send_msg);
			}
			state_flag = 9;
			/*
				state_flag = 0 성공
				state_flag = 1 FEP와통신실패
				state_flag = 2 BackUrl쓰기에 실패해 재취소를 하였음.
				state_flag = 3 BackUrl쓰기에 실패해 재취소를 하였으나 취소실패하였음.
				state_flag =
			*/
			
			if( !this.ProcessRequest(this.IPAddr, this.Port, Flag, real_send_msg) )	//FEP와 통신실패의경우
			{
				state_flag = 1 ;
			}
		}catch(IOException e)
		{
			log.error("ERROR: {}", e.getMessage(), e);
			if(ServiceType.LOCAL)
			{
				log.info("[KSPayApprovalCancelBean] {}", e.toString());
				log.info("[KSPayApprovalCancelBean] 승인요청 실패");
			}
			return false;
		}
		
		/*예외상황(통신실패,BU에러로재취소)*/
		if(state_flag == 1) /*FEP와통신실패*/
		{
			for(int i = 0; i < this.ReceiveCount; i++)
			{
				Status         [i]  = "X";
				Message1       [i]  = "KSPAY와통신실패";    // 메시지1
				Message2       [i]  = "잠시후재시도";       // 메시지2
				Point1         [i]  = "000000000000";
				Point2         [i]  = "000000000000";
				Point3         [i]  = "000000000000";
				Point4         [i]  = "000000000000";
				
				VAStatus       [i]  = "X";
				VAMessage1     [i]  = "KSPAY와통신실패";    // 메시지1
				VAMessage2     [i]  = "잠시후재시도";       // 메시지2
				
				WPStatus       [i]  = "X";
				WPMessage1     [i]  = "KSPAY와통신실패";    // 메시지1
				WPMessage2     [i]  = "잠시후재시도";       // 메시지2
				WPAuthNo       [i]  = "9999";
				WPBalanceAmount[i]  = "000000000";
				WPLimitAmount  [i]  = "000000000";
				
				PStatus        [i]  = "X";
				PMessage1      [i]  = "KSPAY와통신실패";    // 메시지1
				PMessage2      [i]  = "잠시후재시도";       // 메시지2
				PPoint1        [i]  = "000000000";
				PPoint2        [i]  = "000000000";
				PPoint3        [i]  = "000000000";
				PPoint4        [i]  = "000000000";
				
				HStatus        [i]  = "X";
				HMessage1      [i]  = "KSPAY와통신실패";    // 메시지1
				HMessage2      [i]  = "잠시후재시도";       // 메시지2
				
				MB1Status      [i]  = "X";
				MB1Message     [i]  = "KSPAY와통신실패";     // 메시지
				
				MB2Status      [i]  = "X";
				MB2Message     [i]      = "KSPAY와통신실패"; // 메시지
				
				BINStatus      [i]  = "X";
				BINMessage1    [i]  = "KSPAY와통신실패";     // 메시지1
				BINMessage2    [i]  = "잠시후재시도";        // 메시지2
			}
		}
		
		return true;
	}
	
	private boolean ProcessRequest(String addr, int port, String ServiceType, String SendMsg) throws IOException
	{
		boolean ret = false;
		
		this.KSPaySocket = new KSPaySocketBean(addr, port);
		
		this.KSPaySocket.ConnectSocket();   //IPG_Server와 연결을 맺는다
		this.KSPaySocket.write(SendMsg.getBytes(MSG_ENCODING)); //IPG_Server에 승인/취소요청 데이타를 보낸다.
		
		ret = ReceiveMessage();
		
		this.KSPaySocket.CloseSocket();
		
		return ret;
	}
	
	public static String format(String str, int len, char ctype)
	{
		byte[] buff;
		int filllen = 0;
		
		String       trim_str = null;
		StringBuffer sb       = new StringBuffer();
		
		try
		{
			buff = (str == null) ? new byte[0] : str.getBytes(MSG_ENCODING);
			
			filllen = len - buff.length;
			if (filllen < 0)
			{
				for(int i=0, j=0; j<len-4; i++)//적당히 여유를 두고 잘라버리자
				{
					if (str == null) {
						continue;
					}
					j += (str.charAt(i) > 127) ? 2 : 1;
					sb.append(str.charAt(i));
				}
				
				trim_str = sb.toString();
				buff = trim_str.getBytes(MSG_ENCODING);
				filllen = len - buff.length;
				
				if (filllen <= 0) return new String(buff, 0, len ,MSG_ENCODING);//여기는 절대로 안타겠지...
				sb.setLength(0);
			}else
			{
				trim_str = str;
			}
			
			if(ctype == '9')   // 숫자열인 경우
			{
				for(int i = 0; i<filllen;i++) sb.append('0');
				sb.append(trim_str);
			}else              // 문자열인 경우
			{
				for(int i = 0; i<filllen;i++) sb.append(' ');
				sb.insert(0, trim_str);
			}
		}catch(UnsupportedEncodingException ue)
		{
			log.error(ue.getMessage(), ue);
		}
		return sb.toString();
	}
	
	private String setTrim(String str, int len)
	{
		byte[] subbytes = null;
		String tmpStr = null;
		subbytes = new byte[len];
		
		try
		{
			System.arraycopy(str.getBytes(MSG_ENCODING), 0, subbytes, 0, len);
			tmpStr = new String(subbytes ,MSG_ENCODING);
			if(tmpStr.length() == 0)
			{
				subbytes = new byte[len-1];
				System.arraycopy(str.getBytes(MSG_ENCODING), 0, subbytes, 0, len-1);
				tmpStr = new String(subbytes ,MSG_ENCODING);
			}
		}catch(UnsupportedEncodingException ue)
		{
			log.error(ue.getMessage(), ue);
		}
		return tmpStr;
	}
	
	private String setLogMsg(String str)
	{
		String strBuf = "";
		for(int i=0; i < str.length(); i++)
		{
			if(str.substring(i, i+1).equals(" "))
				strBuf = strBuf + "_";
			else
				strBuf = strBuf + str.substring(i, i+1);
		}
		return strBuf;
	}
	
	public static String[] split(String srcStr, char c1)
	{
		return split(srcStr, String.valueOf(c1));
	}
	
	public static String[] split(String srcStr, String str1)
	{
		if (srcStr == null) return new String[0];
		
		String[] tokenArr = null;
		if (srcStr.indexOf(str1) == -1)
		{
			tokenArr = new String[1];
			tokenArr[0] = srcStr;
			
			return tokenArr;
		}
		
		LinkedList linkedlist = new LinkedList();
		
		int srcLength    = srcStr.length();
		int tockenLength = str1.length();
		
		int pos = 0, startPos = 0;
		while(startPos < srcLength)
		{
			pos = srcStr.indexOf(str1, startPos);
			
			if (-1 == pos) break;
			
			linkedlist.add(srcStr.substring(startPos, pos));
			startPos = pos + tockenLength;
		}
		
		if (startPos <= srcLength) linkedlist.add(srcStr.substring(startPos));
		
		return (String[])linkedlist.toArray(new String[0]);
	}//split
}