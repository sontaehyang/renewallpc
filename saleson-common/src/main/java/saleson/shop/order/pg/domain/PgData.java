package saleson.shop.order.pg.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.ToString;
import saleson.shop.order.pg.easypay.domain.EasypayRequest;
import saleson.shop.order.pg.kcp.domain.KcpRequest;
@ToString
public class PgData {

	private String returnUrlParam;

	private String salesonId;
	private String salesonToken;
	private String salesonTokenType;
	private String failUrl;
	private String successUrl;

	private boolean isMobilePage;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String mid;
	private String keypass;
	public String getKeypass() {
		return keypass;
	}

	public void setKeypass(String keypass) {
		this.keypass = keypass;
	}

	private String orderCode;
	private String amount;
	private String taxFreeAmount;
	private String approvalType;
	private String deviceType;
	
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getTaxFreeAmount() {
		return taxFreeAmount;
	}

	public void setTaxFreeAmount(String taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	// CJ
	private String userID;
	private String username;
	private String userEmail;
	private String userPhone;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	// 이니시스
	private String uid;
	private String oid;
	private String goodname;
	private String currency;
	private String encrypted;
	private String sessionkey;
	private String buyername;
	private String buyertel;
	private String buyeremail;
	private String url;
	private String cardcode;
	private String parentemail;
	private String recvname;
	private String recvtel;
	private String recvaddr;
	private String recvpostnum;
	private String recvmsg;
	private String joincard;
	private String joinexpire;
	private String id_customer;
	private String encfield;
	private String certid;
	private String paymethod;

	// 이니시스 웹 결제
//	private String resultCode;
	private String authUrl;
	private String authToken;
	private String netCancelUrl;
	
	// 2017.05.25 Jun-Eu Son KSPAY 결제 
	private String sndPaymethod;
	private String sndStoreid;
	private String sndOrdernumber;
	private String sndGoodname;
	private String sndAmount;
	private String sndOrdername;
	private String sndEmail;
	private String sndMobile;
	private String sndServicePeriod;
	private String sndReply;
	private String sndGoodType;
	private String sndShowcard;
	private String sndCurrencytype;
	private String sndInstallmenttype;
	private String sndInteresttype;
	private String reWHCid;
	private String reWHCtype;
	private String reWHHash;
	
	// Son Jun-Eu 2017.07.28 NHN KCP 결제
	
	private KcpRequest kcpRequest;
	
	// Son Jun-Eu 2017.09.27 EASYPAY 결제
	private EasypayRequest easypayRequest;
		
//	public String getResultCode() {
//		return resultCode;
//	}
//
//	public void setResultCode(String resultCode) {
//		this.resultCode = resultCode;
//	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getNetCancelUrl() {
		return netCancelUrl;
	}

	public void setNetCancelUrl(String netCancelUrl) {
		this.netCancelUrl = netCancelUrl;
	}

	// 이니시스 모바일
	private String P_TID;
	private String P_MID;
	private String P_AUTH_DT;
	private String P_STATUS;
	private String P_TYPE;
	private String P_OID;
	private String P_FN_CD1;
	private String P_FN_CD2;
	private String P_FN_NM;
	private String P_UNAME;
	private String P_AMT;
	private String P_RMESG1;
	private String P_RMESG2;
	private String P_NOTI;
	private String P_AUTH_NO;
	private String P_REQ_URL;
	
	
	// 이니시스 가상계좌
	private String P_VACT_NUM; // 입금할 계좌 번호 
	private String P_VACT_DATE; // 입금 마감 일자
	private String P_VACT_TIME; // 입금 마감 시간
	private String P_VACT_NAME; // 계좌주명
	private String P_VACT_BANK_CODE; // 은행코드
	private String transactionType;
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	// 이니시스 가상계좌 입금통보
	private String no_tid;	// 이니시스 거래번호
	private String id_merchant;	//??
	private String no_oid;	// 주문번호
	private String no_vacct;	// 계좌번호
	private String amt_input;	// 입금금액
	private String nm_inputbank;	// 입금은행명
	private String nm_input;	// 입금자명
	private String dt_inputstd;	// 입금일자
	private String no_cshr_appl; // 현금영수증 발급번호
	private String no_cshr_tid;	// 현금영수증 TID
	private String no_req_tid;	// 요청 TID
	private String dt_cshr; // 현금영수증 발급일자
	private String tm_cshr; // 현금영수증 발급시간
	
	
	public String getDt_cshr() {
		return dt_cshr;
	}

	public void setDt_cshr(String dt_cshr) {
		this.dt_cshr = dt_cshr;
	}

	public String getTm_cshr() {
		return tm_cshr;
	}

	public void setTm_cshr(String tm_cshr) {
		this.tm_cshr = tm_cshr;
	}

	public String getNo_req_tid() {
		return no_req_tid;
	}

	public void setNo_req_tid(String no_req_tid) {
		this.no_req_tid = no_req_tid;
	}

	public String getNo_cshr_appl() {
		return no_cshr_appl;
	}

	public void setNo_cshr_appl(String no_cshr_appl) {
		this.no_cshr_appl = no_cshr_appl;
	}

	public String getNo_cshr_tid() {
		return no_cshr_tid;
	}

	public void setNo_cshr_tid(String no_cshr_tid) {
		this.no_cshr_tid = no_cshr_tid;
	}

	public String getDt_inputstd() {
		return dt_inputstd;
	}

	public void setDt_inputstd(String dt_inputstd) {
		this.dt_inputstd = dt_inputstd;
	}

	public String getNo_tid() {
		return no_tid;
	}

	public void setNo_tid(String no_tid) {
		this.no_tid = no_tid;
	}

	public String getId_merchant() {
		return id_merchant;
	}

	public void setId_merchant(String id_merchant) {
		this.id_merchant = id_merchant;
	}

	public String getNo_oid() {
		return no_oid;
	}

	public void setNo_oid(String no_oid) {
		this.no_oid = no_oid;
	}

	public String getNo_vacct() {
		return no_vacct;
	}

	public void setNo_vacct(String no_vacct) {
		this.no_vacct = no_vacct;
	}

	public String getAmt_input() {
		return amt_input;
	}

	public void setAmt_input(String amt_input) {
		this.amt_input = amt_input;
	}

	public String getNm_inputbank() {
		return nm_inputbank;
	}

	public void setNm_inputbank(String nm_inputbank) {
		this.nm_inputbank = nm_inputbank;
	}

	public String getNm_input() {
		return nm_input;
	}

	public void setNm_input(String nm_input) {
		this.nm_input = nm_input;
	}

	// 엘지데이콤
	private String LGD_BUYER;
	private String LGD_PRODUCTINFO;
	private String LGD_BUYEREMAIL;
	private String LGD_CUSTOM_USABLEPAY;
	private String LGD_PAYKEY;
	private String LGD_RESPCODE;
	private String LGD_RESPMSG;
	private String LGD_MID;
	private String LGD_OID;
	private String LGD_AMOUNT;
	private String LGD_TID;
	private String LGD_PAYTYPE;
	private String LGD_PAYDATE;
	private String LGD_HASHDATA;
	private String LGD_FINANCECODE;
	private String LGD_FINANCENAME;
	private String LGD_ESCROWYN;
	private String LGD_TIMESTAMP;
	private String LGD_ACCOUNTNUM;
	private String LGD_CASTAMOUNT;
	private String LGD_CASCAMOUNT;
	private String LGD_CASFLAG;
	private String LGD_CASSEQNO;
	private String LGD_CASHRECEIPTNUM;
	private String LGD_CASHRECEIPTSELFYN;
	private String LGD_CASHRECEIPTKIND;
	private String LGD_PAYER;
	private String LGD_BUYERID;
	private String LGD_BUYERADDRESS;
	private String LGD_BUYERPHONE;
	private String LGD_BUYERSSN;
	private String LGD_PRODUCTCODE;
	private String LGD_RECEIVER;
	private String LGD_RECEIVERPHONE;
	private String LGD_DELIVERYINFO;
	
	public String getLGD_RESPCODE() {
		return LGD_RESPCODE;
	}

	public void setLGD_RESPCODE(String lGD_RESPCODE) {
		LGD_RESPCODE = lGD_RESPCODE;
	}

	public String getLGD_RESPMSG() {
		return LGD_RESPMSG;
	}

	public void setLGD_RESPMSG(String lGD_RESPMSG) {
		LGD_RESPMSG = lGD_RESPMSG;
	}

	public String getLGD_MID() {
		return LGD_MID;
	}

	public void setLGD_MID(String lGD_MID) {
		LGD_MID = lGD_MID;
	}

	public String getLGD_OID() {
		return LGD_OID;
	}

	public void setLGD_OID(String lGD_OID) {
		LGD_OID = lGD_OID;
	}

	public String getLGD_AMOUNT() {
		return LGD_AMOUNT;
	}

	public void setLGD_AMOUNT(String lGD_AMOUNT) {
		LGD_AMOUNT = lGD_AMOUNT;
	}

	public String getLGD_TID() {
		return LGD_TID;
	}

	public void setLGD_TID(String lGD_TID) {
		LGD_TID = lGD_TID;
	}

	public String getLGD_PAYTYPE() {
		return LGD_PAYTYPE;
	}

	public void setLGD_PAYTYPE(String lGD_PAYTYPE) {
		LGD_PAYTYPE = lGD_PAYTYPE;
	}

	public String getLGD_PAYDATE() {
		return LGD_PAYDATE;
	}

	public void setLGD_PAYDATE(String lGD_PAYDATE) {
		LGD_PAYDATE = lGD_PAYDATE;
	}

	public String getLGD_HASHDATA() {
		return LGD_HASHDATA;
	}

	public void setLGD_HASHDATA(String lGD_HASHDATA) {
		LGD_HASHDATA = lGD_HASHDATA;
	}

	public String getLGD_FINANCECODE() {
		return LGD_FINANCECODE;
	}

	public void setLGD_FINANCECODE(String lGD_FINANCECODE) {
		LGD_FINANCECODE = lGD_FINANCECODE;
	}

	public String getLGD_FINANCENAME() {
		return LGD_FINANCENAME;
	}

	public void setLGD_FINANCENAME(String lGD_FINANCENAME) {
		LGD_FINANCENAME = lGD_FINANCENAME;
	}

	public String getLGD_ESCROWYN() {
		return LGD_ESCROWYN;
	}

	public void setLGD_ESCROWYN(String lGD_ESCROWYN) {
		LGD_ESCROWYN = lGD_ESCROWYN;
	}

	public String getLGD_TIMESTAMP() {
		return LGD_TIMESTAMP;
	}

	public void setLGD_TIMESTAMP(String lGD_TIMESTAMP) {
		LGD_TIMESTAMP = lGD_TIMESTAMP;
	}

	public String getLGD_ACCOUNTNUM() {
		return LGD_ACCOUNTNUM;
	}

	public void setLGD_ACCOUNTNUM(String lGD_ACCOUNTNUM) {
		LGD_ACCOUNTNUM = lGD_ACCOUNTNUM;
	}

	public String getLGD_CASTAMOUNT() {
		return LGD_CASTAMOUNT;
	}

	public void setLGD_CASTAMOUNT(String lGD_CASTAMOUNT) {
		LGD_CASTAMOUNT = lGD_CASTAMOUNT;
	}

	public String getLGD_CASCAMOUNT() {
		return LGD_CASCAMOUNT;
	}

	public void setLGD_CASCAMOUNT(String lGD_CASCAMOUNT) {
		LGD_CASCAMOUNT = lGD_CASCAMOUNT;
	}

	public String getLGD_CASFLAG() {
		return LGD_CASFLAG;
	}

	public void setLGD_CASFLAG(String lGD_CASFLAG) {
		LGD_CASFLAG = lGD_CASFLAG;
	}

	public String getLGD_CASSEQNO() {
		return LGD_CASSEQNO;
	}

	public void setLGD_CASSEQNO(String lGD_CASSEQNO) {
		LGD_CASSEQNO = lGD_CASSEQNO;
	}

	public String getLGD_CASHRECEIPTNUM() {
		return LGD_CASHRECEIPTNUM;
	}

	public void setLGD_CASHRECEIPTNUM(String lGD_CASHRECEIPTNUM) {
		LGD_CASHRECEIPTNUM = lGD_CASHRECEIPTNUM;
	}

	public String getLGD_CASHRECEIPTSELFYN() {
		return LGD_CASHRECEIPTSELFYN;
	}

	public void setLGD_CASHRECEIPTSELFYN(String lGD_CASHRECEIPTSELFYN) {
		LGD_CASHRECEIPTSELFYN = lGD_CASHRECEIPTSELFYN;
	}

	public String getLGD_CASHRECEIPTKIND() {
		return LGD_CASHRECEIPTKIND;
	}

	public void setLGD_CASHRECEIPTKIND(String lGD_CASHRECEIPTKIND) {
		LGD_CASHRECEIPTKIND = lGD_CASHRECEIPTKIND;
	}

	public String getLGD_PAYER() {
		return LGD_PAYER;
	}

	public void setLGD_PAYER(String lGD_PAYER) {
		LGD_PAYER = lGD_PAYER;
	}

	public String getLGD_BUYERID() {
		return LGD_BUYERID;
	}

	public void setLGD_BUYERID(String lGD_BUYERID) {
		LGD_BUYERID = lGD_BUYERID;
	}

	public String getLGD_BUYERADDRESS() {
		return LGD_BUYERADDRESS;
	}

	public void setLGD_BUYERADDRESS(String lGD_BUYERADDRESS) {
		LGD_BUYERADDRESS = lGD_BUYERADDRESS;
	}

	public String getLGD_BUYERPHONE() {
		return LGD_BUYERPHONE;
	}

	public void setLGD_BUYERPHONE(String lGD_BUYERPHONE) {
		LGD_BUYERPHONE = lGD_BUYERPHONE;
	}

	public String getLGD_BUYERSSN() {
		return LGD_BUYERSSN;
	}

	public void setLGD_BUYERSSN(String lGD_BUYERSSN) {
		LGD_BUYERSSN = lGD_BUYERSSN;
	}

	public String getLGD_PRODUCTCODE() {
		return LGD_PRODUCTCODE;
	}

	public void setLGD_PRODUCTCODE(String lGD_PRODUCTCODE) {
		LGD_PRODUCTCODE = lGD_PRODUCTCODE;
	}

	public String getLGD_RECEIVER() {
		return LGD_RECEIVER;
	}

	public void setLGD_RECEIVER(String lGD_RECEIVER) {
		LGD_RECEIVER = lGD_RECEIVER;
	}

	public String getLGD_RECEIVERPHONE() {
		return LGD_RECEIVERPHONE;
	}

	public void setLGD_RECEIVERPHONE(String lGD_RECEIVERPHONE) {
		LGD_RECEIVERPHONE = lGD_RECEIVERPHONE;
	}

	public String getLGD_DELIVERYINFO() {
		return LGD_DELIVERYINFO;
	}

	public void setLGD_DELIVERYINFO(String lGD_DELIVERYINFO) {
		LGD_DELIVERYINFO = lGD_DELIVERYINFO;
	}

	public String getLGD_PAYKEY() {
		return LGD_PAYKEY;
	}

	public void setLGD_PAYKEY(String lGD_PAYKEY) {
		LGD_PAYKEY = lGD_PAYKEY;
	}

	public String getLGD_BUYER() {
		return LGD_BUYER;
	}

	public void setLGD_BUYER(String lGD_BUYER) {
		LGD_BUYER = lGD_BUYER;
	}

	public String getLGD_PRODUCTINFO() {
		return LGD_PRODUCTINFO;
	}

	public void setLGD_PRODUCTINFO(String lGD_PRODUCTINFO) {
		LGD_PRODUCTINFO = lGD_PRODUCTINFO;
	}

	public String getLGD_BUYEREMAIL() {
		return LGD_BUYEREMAIL;
	}

	public void setLGD_BUYEREMAIL(String lGD_BUYEREMAIL) {
		LGD_BUYEREMAIL = lGD_BUYEREMAIL;
	}

	public String getLGD_CUSTOM_USABLEPAY() {
		return LGD_CUSTOM_USABLEPAY;
	}

	public void setLGD_CUSTOM_USABLEPAY(String lGD_CUSTOM_USABLEPAY) {
		LGD_CUSTOM_USABLEPAY = lGD_CUSTOM_USABLEPAY;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getGoodname() {
		return goodname;
	}

	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

	public String getBuyername() {
		return buyername;
	}

	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}

	public String getBuyertel() {
		return buyertel;
	}

	public void setBuyertel(String buyertel) {
		this.buyertel = buyertel;
	}

	public String getBuyeremail() {
		return buyeremail;
	}

	public void setBuyeremail(String buyeremail) {
		this.buyeremail = buyeremail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getParentemail() {
		return parentemail;
	}

	public void setParentemail(String parentemail) {
		this.parentemail = parentemail;
	}

	public String getRecvname() {
		return recvname;
	}

	public void setRecvname(String recvname) {
		this.recvname = recvname;
	}

	public String getRecvtel() {
		return recvtel;
	}

	public void setRecvtel(String recvtel) {
		this.recvtel = recvtel;
	}

	public String getRecvaddr() {
		return recvaddr;
	}

	public void setRecvaddr(String recvaddr) {
		this.recvaddr = recvaddr;
	}

	public String getRecvpostnum() {
		return recvpostnum;
	}

	public void setRecvpostnum(String recvpostnum) {
		this.recvpostnum = recvpostnum;
	}

	public String getRecvmsg() {
		return recvmsg;
	}

	public void setRecvmsg(String recvmsg) {
		this.recvmsg = recvmsg;
	}

	public String getJoincard() {
		return joincard;
	}

	public void setJoincard(String joincard) {
		this.joincard = joincard;
	}

	public String getJoinexpire() {
		return joinexpire;
	}

	public void setJoinexpire(String joinexpire) {
		this.joinexpire = joinexpire;
	}

	public String getId_customer() {
		return id_customer;
	}

	public void setId_customer(String id_customer) {
		this.id_customer = id_customer;
	}

	public String getEncfield() {
		return encfield;
	}

	public void setEncfield(String encfield) {
		this.encfield = encfield;
	}

	public String getCertid() {
		return certid;
	}

	public void setCertid(String certid) {
		this.certid = certid;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getP_TID() {
		return P_TID;
	}

	public void setP_TID(String p_TID) {
		P_TID = p_TID;
	}

	public String getP_MID() {
		return P_MID;
	}

	public void setP_MID(String p_MID) {
		P_MID = p_MID;
	}

	public String getP_AUTH_DT() {
		return P_AUTH_DT;
	}

	public void setP_AUTH_DT(String p_AUTH_DT) {
		P_AUTH_DT = p_AUTH_DT;
	}

	public String getP_STATUS() {
		return P_STATUS;
	}

	public void setP_STATUS(String p_STATUS) {
		P_STATUS = p_STATUS;
	}

	public String getP_TYPE() {
		return P_TYPE;
	}

	public void setP_TYPE(String p_TYPE) {
		P_TYPE = p_TYPE;
	}

	public String getP_OID() {
		return P_OID;
	}

	public void setP_OID(String p_OID) {
		P_OID = p_OID;
	}

	public String getP_FN_CD1() {
		return P_FN_CD1;
	}

	public void setP_FN_CD1(String p_FN_CD1) {
		P_FN_CD1 = p_FN_CD1;
	}

	public String getP_FN_CD2() {
		return P_FN_CD2;
	}

	public void setP_FN_CD2(String p_FN_CD2) {
		P_FN_CD2 = p_FN_CD2;
	}

	public String getP_FN_NM() {
		return P_FN_NM;
	}

	public void setP_FN_NM(String p_FN_NM) {
		P_FN_NM = p_FN_NM;
	}

	public String getP_UNAME() {
		return P_UNAME;
	}

	public void setP_UNAME(String p_UNAME) {
		P_UNAME = p_UNAME;
	}

	public String getP_AMT() {
		return P_AMT;
	}

	public void setP_AMT(String p_AMT) {
		P_AMT = p_AMT;
	}

	public String getP_RMESG1() {
		return P_RMESG1;
	}

	public void setP_RMESG1(String p_RMESG1) {
		P_RMESG1 = p_RMESG1;
	}

	public String getP_RMESG2() {
		return P_RMESG2;
	}

	public void setP_RMESG2(String p_RMESG2) {
		P_RMESG2 = p_RMESG2;
	}

	public String getP_NOTI() {
		return P_NOTI;
	}

	public void setP_NOTI(String p_NOTI) {
		P_NOTI = p_NOTI;
	}

	public String getP_AUTH_NO() {
		return P_AUTH_NO;
	}

	public void setP_AUTH_NO(String p_AUTH_NO) {
		P_AUTH_NO = p_AUTH_NO;
	}

	public String getP_VACT_NUM() {
		return P_VACT_NUM;
	}

	public void setP_VACT_NUM(String p_VACT_NUM) {
		P_VACT_NUM = p_VACT_NUM;
	}

	public String getP_VACT_DATE() {
		return P_VACT_DATE;
	}

	public void setP_VACT_DATE(String p_VACT_DATE) {
		P_VACT_DATE = p_VACT_DATE;
	}

	public String getP_VACT_TIME() {
		return P_VACT_TIME;
	}

	public void setP_VACT_TIME(String p_VACT_TIME) {
		P_VACT_TIME = p_VACT_TIME;
	}

	public String getP_VACT_NAME() {
		return P_VACT_NAME;
	}

	public void setP_VACT_NAME(String p_VACT_NAME) {
		P_VACT_NAME = p_VACT_NAME;
	}

	public String getP_VACT_BANK_CODE() {
		return P_VACT_BANK_CODE;
	}

	public void setP_VACT_BANK_CODE(String p_VACT_BANK_CODE) {
		P_VACT_BANK_CODE = p_VACT_BANK_CODE;
	}

	public String getP_REQ_URL() {
		return P_REQ_URL;
	}

	public void setP_REQ_URL(String p_REQ_URL) {
		P_REQ_URL = p_REQ_URL;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	// kakaopay 결제 시 필요.
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public boolean isMobilePage() {
		return isMobilePage;
	}

	public void setMobilePage(boolean isMobilePage) {
		this.isMobilePage = isMobilePage;
	}
	
	public String getSndPaymethod() {
		return sndPaymethod;
	}

	public void setSndPaymethod(String sndPaymethod) {
		this.sndPaymethod = sndPaymethod;
	}

	public String getSndStoreid() {
		return sndStoreid;
	}

	public void setSndStoreid(String sndStoreid) {
		this.sndStoreid = sndStoreid;
	}

	public String getSndOrdernumber() {
		return sndOrdernumber;
	}

	public void setSndOrdernumber(String sndOrdernumber) {
		this.sndOrdernumber = sndOrdernumber;
	}

	public String getSndGoodname() {
		return sndGoodname;
	}

	public void setSndGoodname(String sndGoodname) {
		this.sndGoodname = sndGoodname;
	}

	public String getSndAmount() {
		return sndAmount;
	}

	public void setSndAmount(String sndAmount) {
		this.sndAmount = sndAmount;
	}

	public String getSndOrdername() {
		return sndOrdername;
	}

	public void setSndOrdername(String sndOrdername) {
		this.sndOrdername = sndOrdername;
	}

	public String getSndEmail() {
		return sndEmail;
	}

	public void setSndEmail(String sndEmail) {
		this.sndEmail = sndEmail;
	}

	public String getSndMobile() {
		return sndMobile;
	}

	public void setSndMobile(String sndMobile) {
		this.sndMobile = sndMobile;
	}

	public String getSndServicePeriod() {
		return sndServicePeriod;
	}

	public void setSndServicePeriod(String sndServicePeriod) {
		this.sndServicePeriod = sndServicePeriod;
	}

	public String getSndReply() {
		return sndReply;
	}

	public void setSndReply(String sndReply) {
		this.sndReply = sndReply;
	}

	public String getSndGoodType() {
		return sndGoodType;
	}

	public void setSndGoodType(String sndGoodType) {
		this.sndGoodType = sndGoodType;
	}

	public String getSndShowcard() {
		return sndShowcard;
	}

	public void setSndShowcard(String sndShowcard) {
		this.sndShowcard = sndShowcard;
	}

	public String getSndCurrencytype() {
		return sndCurrencytype;
	}

	public void setSndCurrencytype(String sndCurrencytype) {
		this.sndCurrencytype = sndCurrencytype;
	}

	public String getSndInstallmenttype() {
		return sndInstallmenttype;
	}

	public void setSndInstallmenttype(String sndInstallmenttype) {
		this.sndInstallmenttype = sndInstallmenttype;
	}

	public String getSndInteresttype() {
		return sndInteresttype;
	}

	public void setSndInteresttype(String sndInteresttype) {
		this.sndInteresttype = sndInteresttype;
	}

	public String getReWHCid() {
		return reWHCid;
	}

	public void setReWHCid(String reWHCid) {
		this.reWHCid = reWHCid;
	}

	public String getReWHCtype() {
		return reWHCtype;
	}

	public void setReWHCtype(String reWHCtype) {
		this.reWHCtype = reWHCtype;
	}

	public String getReWHHash() {
		return reWHHash;
	}

	public void setReWHHash(String reWHHash) {
		this.reWHHash = reWHHash;
	}

	public KcpRequest getKcpRequest() {
		return kcpRequest;
	}

	public void setKcpRequest(KcpRequest kcpRequest) {
		this.kcpRequest = kcpRequest;
	}

	public EasypayRequest getEasypayRequest() {
		return easypayRequest;
	}

	public void setEasypayRequest(EasypayRequest easypayRequest) {
		this.easypayRequest = easypayRequest;
	}

	// 나이스페이
	private String GoodsCnt;
	private String GoodsName;
	private String Amt;
	private String BuyerName;
	private String BuyerTel;
	private String Moid;
	private String PayMethod;
	private String UserIP;
	private String MallIP;
	private String VbankExpDate;
	private String CharSet;
	private String BuyerEmail;
	private String GoodsCl;
	private String TransType;
	private String EncodeParameters;
	private String EdiDate;
	private String EncryptData;
	private String TrKey;
	private String ResultMsg;
	private String AuthDate;
	private String AuthCode;
	private String MallUserID;
	private String Tid;
	private String CardCode;
	private String CardName;
	private String CardQuota;
	private String BankCode;
	private String BankName;
	private String RcptType;
	private String RcptAuthCode;
	private String RcptTID;
	private String Carrier;
	private String DstAddr;
	private String VbankBankCode;
	private String VbankBankName;
	private String VbankNum;
	private String VbankInputName;
	private String OptionList;
	private String SelectCardCode;
	private String SelectQuota;
	private String SocketYN;
	private String ReturnURL;
	private String AcsNoIframe;
	private String AuthResultCode;
	private String AuthResultMsg;
	private String CardCl;
	private String CcPartCl;

	// 나이스페이 가상계좌 입금통보
	private String MID;
	private String TID;
	private String MOID;
	private String FnCd;
	private String name;
	private String CancelDate;

    // 네이버페이 결제형 결제번호
    private String paymentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

	public String getAuthResultCode() {
		return AuthResultCode;
	}

	public void setAuthResultCode(String authResultCode) {
		this.AuthResultCode = authResultCode;
	}

	public String getAuthResultMsg() {
		return AuthResultMsg;
	}

	public void setAuthResultMsg(String authResultMsg) {
		this.AuthResultMsg = authResultMsg;
	}

	public String getCardCl() {
		return CardCl;
	}

	public void setCardCl(String cardCl) {
		this.CardCl = cardCl;
	}

	public String getCcPartCl() {
		return CcPartCl;
	}

	public void setCcPartCl(String ccPartCl) {
		this.CcPartCl = ccPartCl;
	}

	public String getGoodsCnt() {
		return GoodsCnt;
	}

	public void setGoodsCnt(String goodsCnt) {
		this.GoodsCnt = goodsCnt;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		this.GoodsName = goodsName;
	}

	public String getAmt() {
		return Amt;
	}

	public void setAmt(String amt) {
		this.Amt = amt;
	}

	public String getBuyerName() {
		return BuyerName;
	}

	public void setBuyerName(String buyerName) {
		this.BuyerName = buyerName;
	}

	public String getBuyerTel() {
		return BuyerTel;
	}

	public void setBuyerTel(String buyerTel) {
		this.BuyerTel = buyerTel;
	}

	public String getMoid() {
		return Moid;
	}

	public void setMoid(String moid) {
		this.Moid = moid;
	}

	public String getPayMethod() {
		return PayMethod;
	}

	public void setPayMethod(String payMethod) {
		this.PayMethod = payMethod;
	}

	public String getUserIP() {
		return UserIP;
	}

	public void setUserIP(String userIP) {
		this.UserIP = userIP;
	}

	public String getMallIP() {
		return MallIP;
	}

	public void setMallIP(String mallIP) {
		this.MallIP = mallIP;
	}

	public String getVbankExpDate() {
		return VbankExpDate;
	}

	public void setVbankExpDate(String vbankExpDate) {
		this.VbankExpDate = vbankExpDate;
	}

	public String getCharSet() {
		return CharSet;
	}

	public void setCharSet(String charSet) {
		this.CharSet = charSet;
	}

	public String getBuyerEmail() {
		return BuyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.BuyerEmail = buyerEmail;
	}

	public String getGoodsCl() {
		return GoodsCl;
	}

	public void setGoodsCl(String goodsCl) {
		this.GoodsCl = goodsCl;
	}

	public String getTransType() {
		return TransType;
	}

	public void setTransType(String transType) {
		this.TransType = transType;
	}

	public String getEncodeParameters() {
		return EncodeParameters;
	}

	public void setEncodeParameters(String encodeParameters) {
		this.EncodeParameters = encodeParameters;
	}

	public String getEdiDate() {
		return EdiDate;
	}

	public void setEdiDate(String ediDate) {
		this.EdiDate = ediDate;
	}

	public String getEncryptData() {
		return EncryptData;
	}

	public void setEncryptData(String encryptData) {
		this.EncryptData = encryptData;
	}

	public String getTrKey() {
		return TrKey;
	}

	public void setTrKey(String trKey) {
		this.TrKey = trKey;
	}

	public String getResultMsg() {
		return ResultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.ResultMsg = resultMsg;
	}

	public String getAuthDate() {
		return AuthDate;
	}

	public void setAuthDate(String authDate) {
		this.AuthDate = authDate;
	}

	public String getAuthCode() {
		return AuthCode;
	}

	public void setAuthCode(String authCode) {
		this.AuthCode = authCode;
	}

	public String getMallUserID() {
		return MallUserID;
	}

	public void setMallUserID(String mallUserID) {
		this.MallUserID = mallUserID;
	}

	public String getTid() {
		return Tid;
	}

	public void setTid(String tid) {
		this.Tid = Tid;
	}

	public String getCardCode() {
		return CardCode;
	}

	public void setCardCode(String cardCode) {
		this.CardCode = cardCode;
	}

	public String getCardName() {
		return CardName;
	}

	public void setCardName(String cardName) {
		this.CardName = cardName;
	}

	public String getCardQuota() {
		return CardQuota;
	}

	public void setCardQuota(String cardQuota) {
		this.CardQuota = cardQuota;
	}

	public String getBankCode() {
		return BankCode;
	}

	public void setBankCode(String bankCode) {
		this.BankCode = bankCode;
	}

	public String getBankName() {
		return BankName;
	}

	public void setBankName(String bankName) {
		this.BankName = bankName;
	}

	public String getRcptType() {
		return RcptType;
	}

	public void setRcptType(String rcptType) {
		this.RcptType = rcptType;
	}

	public String getRcptAuthCode() {
		return RcptAuthCode;
	}

	public void setRcptAuthCode(String rcptAuthCode) {
		this.RcptAuthCode = rcptAuthCode;
	}

	public String getRcptTID() {
		return RcptTID;
	}

	public void setRcptTID(String rcptTID) {
		this.RcptTID = rcptTID;
	}

	public String getCarrier() {
		return Carrier;
	}

	public void setCarrier(String carrier) {
		this.Carrier = carrier;
	}

	public String getDstAddr() {
		return DstAddr;
	}

	public void setDstAddr(String dstAddr) {
		this.DstAddr = dstAddr;
	}

	public String getVbankBankCode() {
		return VbankBankCode;
	}

	public void setVbankBankCode(String vbankBankCode) {
		this.VbankBankCode = vbankBankCode;
	}

	public String getVbankBankName() {
		return VbankBankName;
	}

	public void setVbankBankName(String vbankBankName) {
		this.VbankBankName = vbankBankName;
	}

	public String getVbankNum() {
		return VbankNum;
	}

	public void setVbankNum(String vbankNum) {
		this.VbankNum = vbankNum;
	}

	public String getOptionList() {
		return OptionList;
	}

	public void setOptionList(String optionList) {
		this.OptionList = optionList;
	}

	public String getSelectCardCode() {
		return SelectCardCode;
	}

	public void setSelectCardCode(String selectCardCode) {
		this.SelectCardCode = selectCardCode;
	}

	public String getSelectQuota() {
		return SelectQuota;
	}

	public void setSelectQuota(String selectQuota) {
		this.SelectQuota = selectQuota;
	}

	public String getSocketYN() {
		return SocketYN;
	}

	public void setSocketYN(String socketYN) {
		this.SocketYN = socketYN;
	}

	public String getReturnURL() {
		return ReturnURL;
	}

	public void setReturnURL(String returnURL) {
		this.ReturnURL = returnURL;
	}

	public String getAcsNoIframe() {
		return AcsNoIframe;
	}

	public void setAcsNoIframe(String acsNoIframe) {
		this.AcsNoIframe = acsNoIframe;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getVbankInputName() {
		return VbankInputName;
	}

	public void setVbankInputName(String vbankInputName) {
		this.VbankInputName = vbankInputName;
	}

	public String getSalesonId() {
		return salesonId;
	}

	public void setSalesonId(String salesonId) {
		this.salesonId = salesonId;
	}

	public String getSalesonToken() {
		return salesonToken;
	}

	public void setSalesonToken(String salesonToken) {
		this.salesonToken = salesonToken;
	}

	public String getSalesonTokenType() {
		return salesonTokenType;
	}

	public void setSalesonTokenType(String salesonTokenType) {
		this.salesonTokenType = salesonTokenType;
	}

	public String getReturnUrlParam() {
		return returnUrlParam;
	}

	public void setReturnUrlParam(String returnUrlParam) {
		this.returnUrlParam = returnUrlParam;
	}

	public String getFailUrl() {
		return failUrl;
	}

	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String MID) {
		this.MID = MID;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String TID) {
		this.TID = TID;
	}

	public String getFnCd() {
		return FnCd;
	}

	public void setFnCd(String FnCd) {
		this.FnCd = FnCd;
	}
	public String getMOID() {
		return MOID;
	}

	public void setMOID(String MOID) {
		this.MOID = MOID;
	}

	public String getCancelDate() {
		return CancelDate;
	}

	public void setCancelDate(String CancelDate) {
		this.CancelDate = CancelDate;
	}


	// BS 렌탈 페이
	public String prodName;
    public String contPer;
    public String prodRent;
    public String resultTimes;
    public String resultCode;
    public String apprKey;
    public String orderNo;


	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getContPer() {
		return contPer;
	}

	public void setContPer(String contPer) {
		this.contPer = contPer;
	}

	public String getProdRent() {
		return prodRent;
	}

	public void setProdRent(String prodRent) {
		this.prodRent = prodRent;
	}

	public String getResultTimes() {
		return resultTimes;
	}

	public void setResultTimes(String resultTimes) {
		this.resultTimes = resultTimes;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getApprKey() {
		return apprKey;
	}

	public void setApprKey(String apprKey) {
		this.apprKey = apprKey;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
