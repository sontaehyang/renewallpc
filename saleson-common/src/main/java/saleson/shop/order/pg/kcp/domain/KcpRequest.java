package saleson.shop.order.pg.kcp.domain;

import javax.servlet.http.HttpServletRequest;

import com.onlinepowers.framework.util.StringUtils;

public class KcpRequest {
		
	public KcpRequest() {
	
	}
	
	public KcpRequest(HttpServletRequest request) {
		setPay_method(request.getParameter("pay_method"));
		setOrdr_idxx(request.getParameter("ordr_idxx"));
		setGood_name(request.getParameter("good_name"));
		setGood_mny(request.getParameter("good_mny"));
		setBuyr_name(request.getParameter("buyr_name"));
		setBuyr_mail(request.getParameter("buyr_mail"));
		setBuyr_tel1(request.getParameter("buyr_tel1"));
		setBuyr_tel2(request.getParameter("buyr_tel2"));
		setReq_tx(request.getParameter("req_tx"));
		setCurrency(request.getParameter("currency"));
		setQuotaopt(request.getParameter("quotaopt"));
		setRet_url(request.getParameter("ret_url"));
				
		setTno(request.getParameter("tno"));
		setCard_pay_method(request.getParameter("card_pay_method"));
		setCard_cd(request.getParameter("card_cd"));
	
	    setSession_key(request.getParameter("session_key"));
	    setEnc_data(request.getParameter("enc_data"));
	    setKvp_quota(request.getParameter("kvp_quota"));
	    setKvp_pgid(request.getParameter("kvp_pgid"));
	    setEasy_pay_flg(request.getParameter("easy_pay_flg"));
	    setUsing_point(request.getParameter("using_point"));		    		
		
		setQuota(request.getParameter("quota"));
		setCard_expiry(request.getParameter("card_expiry"));
		setCavv(request.getParameter("cavv"));
		setXid(request.getParameter("xid"));
		setEci(request.getParameter("eci"));
		setEnc_cardno_yn(request.getParameter("enc_cardno_yn"));
		setCard_enc_no(request.getParameter("card_enc_no"));	
		
		setMod_type(request.getParameter("mod_type"));
		setMod_desc(request.getParameter("mod_desc"));
		setMod_tax_mny(request.getParameter("mod_tax_mny"));
		setMod_vat_mny(request.getParameter("mod_vat_mny"));
		setMod_free_mny(request.getParameter("mod_free_mny"));
		
		setTran_cd(request.getParameter("tran_cd"));
		setShop_user_id(request.getParameter("shop_user_id"));
		setCash_yn(request.getParameter("cash_yn"));
		setCash_tr_code(request.getParameter("cash_tr_code"));
		setCash_id_info(request.getParameter("cash_id_info"));
		setEnc_info(request.getParameter("enc_info"));
		
		setSite_cd(request.getParameter("site_cd"));
		setOrder_no(request.getParameter("order_no"));
		setTx_cd(request.getParameter("tx_cd"));
		setTx_tm(request.getParameter("tx_tm"));
		setIpgm_name(request.getParameter("ipgm_name"));
		setRemitter(request.getParameter("remitter"));
		setIpgm_mnyx(request.getParameter("ipgm_mnyx"));
		setBank_code(request.getParameter("bank_code"));
		setAccount(request.getParameter("account"));
		setOp_cd(request.getParameter("op_cd"));
		setNoti_id(request.getParameter("noti_id"));
		setCash_a_no(request.getParameter("cash_a_no"));
		setCash_a_dt(request.getParameter("cash_a_dt"));
		setCash_no(request.getParameter("cash_no"));
		
		setRes_cd(request.getParameter("res_cd"));
		setUse_pay_method(request.getParameter("use_pay_method"));
		setResponse_type(request.getParameter("response_type"));
		
		setEscw_used(request.getParameter("escw_used"));
		setPay_mod(request.getParameter("pay_mod"));
		setDeli_term(request.getParameter("deli_term"));
		setBask_cntx(request.getParameter("bask_cntx"));
		setGood_info(request.getParameter("good_info"));
		setRcvr_zipx(request.getParameter("rcvr_zipx"));
		setRcvr_add1(request.getParameter("rcvr_add1"));
		setRcvr_add2(request.getParameter("rcvr_add2"));
		setVcnt_yn(request.getParameter("vcnt_yn"));
	}

	// 결제 요청 정보
	private String pay_method;  	// 결제 방법
	private String ordr_idxx; 	 	// 주문 번호
	private String good_name;  		// 상품 정보
	private String good_mny;  		// 결제 금액
	private String buyr_name;  		// 주문자 이름
	private String buyr_mail;  		// 주문자 E-Mail
	private String buyr_tel1; 	 	// 주문자 전화번호
	private String buyr_tel2;  		// 주문자 휴대폰번호
	private String req_tx;  		// 요청 종류
	private String currency;  		// 화폐단위 (WON/USD)
	private String quotaopt;  		// 할부개월수 옵션
	private String ret_url;			// 리턴 URL
	
	// 카드 결제 정보(공통)
	private String tno;				// KCP 거래 고유 번호    
    private String card_pay_method;	// 카드 결제 방법    
    private String card_cd;			// 카드사 코드
    
    //카드 결제 정보(V3D)    
    private String quota;			// V3D 할부개월
    private String card_expiry;    
	private String cavv;
    private String xid;
    private String eci;
    private String enc_cardno_yn;
    private String card_enc_no;
    
    //카드 결제 정보(ISP)
    private String session_key;
	private String enc_data;
    private String kvp_quota;		// ISP 할부개월
    private String kvp_pgid;		// ISP PgId
    private String easy_pay_flg;	// K-Motion 사용여부
    private String using_point;		// K-Motion 포인트 금액

	// 결제 취소 정보
    private String mod_type;		// 변경TYPE(승인취소시 필요)
    private String mod_desc;		// 변경사유    
    private String mod_tax_mny;		// 공급가 부분 취소 요청 금액
    private String mod_vat_mny;		// 부과세 부분 취소 요청 금액
    private String mod_free_mny;	// 비과세 부분 취소 요청 금액
    
    // 웹표준(결제정보) 추가
    private String tran_cd;
    private String shop_user_id;
    private String cash_yn;
    private String cash_tr_code;
    private String cash_id_info;
    private String enc_info;
    // 웹표준 모바일
    private String res_cd;
    private String use_pay_method;
    private String response_type;

	// 가상계좌
    private String site_cd;
    private String order_no;
    private String tx_cd;
    private String tx_tm;
    private String ipgm_name;
    private String remitter;
    private String ipgm_mnyx;
    private String bank_code;
    private String account;
    private String op_cd;
    private String noti_id;
    private String cash_a_no;
    private String cash_a_dt;
    private String cash_no;
    
    private String escw_used;      
    private String pay_mod;        
    private String deli_term;      
    private String bask_cntx;
    private String good_info;
    private String vcnt_yn;
        
    public String getEscw_used() {
		return escw_used;
	}

	public void setEscw_used(String escw_used) {
		this.escw_used = escw_used;
	}

	public String getPay_mod() {
		return pay_mod;
	}

	public void setPay_mod(String pay_mod) {
		this.pay_mod = pay_mod;
	}

	public String getDeli_term() {
		return deli_term;
	}

	public void setDeli_term(String deli_term) {
		this.deli_term = deli_term;
	}

	public String getBask_cntx() {
		return bask_cntx;
	}

	public void setBask_cntx(String bask_cntx) {
		this.bask_cntx = bask_cntx;
	}

	public String getGood_info() {
		return good_info;
	}

	public void setGood_info(String good_info) {
		this.good_info = good_info;
	}

	public String getRcvr_zipx() {
		return rcvr_zipx;
	}

	public void setRcvr_zipx(String rcvr_zipx) {
		this.rcvr_zipx = rcvr_zipx;
	}

	public String getRcvr_add1() {
		return rcvr_add1;
	}

	public void setRcvr_add1(String rcvr_add1) {
		this.rcvr_add1 = rcvr_add1;
	}

	public String getRcvr_add2() {
		return rcvr_add2;
	}

	public void setRcvr_add2(String rcvr_add2) {
		this.rcvr_add2 = rcvr_add2;
	}

	private String rcvr_zipx;
    private String rcvr_add1;
    private String rcvr_add2;
    
    public String getRes_cd() {
		return res_cd;
	}

	public void setRes_cd(String res_cd) {
		this.res_cd = res_cd;
	}

	public String getUse_pay_method() {
		return use_pay_method;
	}

	public void setUse_pay_method(String use_pay_method) {
		this.use_pay_method = use_pay_method;
	}
   
	public String getIpgm_name() {
		return ipgm_name;
	}

	public void setIpgm_name(String ipgm_name) {
		this.ipgm_name = ipgm_name;
	}

	public String getRemitter() {
		return remitter;
	}

	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}

	public String getIpgm_mnyx() {
		return ipgm_mnyx;
	}

	public void setIpgm_mnyx(String ipgm_mnyx) {
		this.ipgm_mnyx = ipgm_mnyx;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOp_cd() {
		return op_cd;
	}

	public void setOp_cd(String op_cd) {
		this.op_cd = op_cd;
	}

	public String getNoti_id() {
		return noti_id;
	}

	public void setNoti_id(String noti_id) {
		this.noti_id = noti_id;
	}

	public String getCash_a_no() {
		return cash_a_no;
	}

	public void setCash_a_no(String cash_a_no) {
		this.cash_a_no = cash_a_no;
	}

	public String getCash_a_dt() {
		return cash_a_dt;
	}

	public void setCash_a_dt(String cash_a_dt) {
		this.cash_a_dt = cash_a_dt;
	}

	public String getCash_no() {
		return cash_no;
	}

	public void setCash_no(String cash_no) {
		this.cash_no = cash_no;
	}

	public String getTx_cd() {
		return tx_cd;
	}

	public void setTx_cd(String tx_cd) {
		this.tx_cd = tx_cd;
	}

	public String getTx_tm() {
		return tx_tm;
	}

	public void setTx_tm(String tx_tm) {
		this.tx_tm = tx_tm;
	}

	public String getShop_user_id() {
		return shop_user_id;
	}

	public void setShop_user_id(String shop_user_id) {
		this.shop_user_id = shop_user_id;
	}

	public String getCash_yn() {
		return cash_yn;
	}

	public void setCash_yn(String cash_yn) {
		this.cash_yn = cash_yn;
	}

	public String getCash_tr_code() {
		return cash_tr_code;
	}

	public void setCash_tr_code(String cash_tr_code) {
		this.cash_tr_code = cash_tr_code;
	}

	public String getCash_id_info() {
		return cash_id_info;
	}

	public void setCash_id_info(String cash_id_info) {
		this.cash_id_info = cash_id_info;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getEnc_data() {
		return enc_data;
	}

	public void setEnc_data(String enc_data) {
		this.enc_data = enc_data;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getOrdr_idxx() {
		return ordr_idxx;
	}

	public void setOrdr_idxx(String ordr_idxx) {
		this.ordr_idxx = ordr_idxx;
	}

	public String getGood_name() {
		return good_name;
	}

	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}

	public String getGood_mny() {
		return good_mny;
	}

	public void setGood_mny(String good_mny) {
		this.good_mny = good_mny;
	}

	public String getBuyr_name() {
		return buyr_name;
	}

	public void setBuyr_name(String buyr_name) {
		this.buyr_name = buyr_name;
	}

	public String getBuyr_mail() {
		return buyr_mail;
	}

	public void setBuyr_mail(String buyr_mail) {
		this.buyr_mail = buyr_mail;
	}

	public String getBuyr_tel1() {
		return buyr_tel1;
	}

	public void setBuyr_tel1(String buyr_tel1) {
		this.buyr_tel1 = buyr_tel1;
	}

	public String getBuyr_tel2() {
		return buyr_tel2;
	}

	public void setBuyr_tel2(String buyr_tel2) {
		this.buyr_tel2 = buyr_tel2;
	}

	public String getReq_tx() {
		return req_tx;
	}

	public void setReq_tx(String req_tx) {
		this.req_tx = req_tx;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getQuotaopt() {
		return quotaopt;
	}

	public void setQuotaopt(String quotaopt) {
		this.quotaopt = quotaopt;
	}

	public String getRet_url() {
		return ret_url;
	}

	public void setRet_url(String ret_url) {
		this.ret_url = ret_url;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getCard_pay_method() {
		return card_pay_method;
	}

	public void setCard_pay_method(String card_pay_method) {
		this.card_pay_method = card_pay_method;
	}

	public String getEasy_pay_flg() {
		return easy_pay_flg;
	}

	public void setEasy_pay_flg(String easy_pay_flg) {
		this.easy_pay_flg = easy_pay_flg;
	}

	public String getUsing_point() {
		return using_point;
	}

	public void setUsing_point(String using_point) {
		this.using_point = using_point;
	}

	public String getMod_type() {
		return mod_type;
	}

	public void setMod_type(String mod_type) {
		this.mod_type = mod_type;
	}

	public String getMod_desc() {
		return mod_desc;
	}

	public void setMod_desc(String mod_desc) {
		this.mod_desc = mod_desc;
	}

	public String getMod_tax_mny() {
		return mod_tax_mny;
	}

	public void setMod_tax_mny(String mod_tax_mny) {
		this.mod_tax_mny = mod_tax_mny;
	}

	public String getMod_vat_mny() {
		return mod_vat_mny;
	}

	public void setMod_vat_mny(String mod_vat_mny) {
		this.mod_vat_mny = mod_vat_mny;
	}

	public String getMod_free_mny() {
		return mod_free_mny;
	}

	public void setMod_free_mny(String mod_free_mny) {
		this.mod_free_mny = mod_free_mny;
	}

	public String getCard_cd() {
		return card_cd;
	}

	public void setCard_cd(String card_cd) {
		this.card_cd = card_cd;
	}
	
	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

	public String getKvp_quota() {
		return kvp_quota;
	}

	public void setKvp_quota(String kvp_quota) {
		this.kvp_quota = kvp_quota;
	}

	public String getKvp_pgid() {
		return kvp_pgid;
	}

	public void setKvp_pgid(String kvp_pgid) {
		this.kvp_pgid = kvp_pgid;
	}
	
	public String getCard_expiry() {
		return card_expiry;
	}

	public void setCard_expiry(String card_expiry) {
		this.card_expiry = card_expiry;
	}

	public String getCavv() {
		return cavv;
	}

	public void setCavv(String cavv) {
		this.cavv = cavv;
	}

	public String getXid() {
		return xid;
	}

	public void setXid(String xid) {
		this.xid = xid;
	}

	public String getEci() {
		return eci;
	}

	public void setEci(String eci) {
		this.eci = eci;
	}

	public String getEnc_cardno_yn() {
		return enc_cardno_yn;
	}

	public void setEnc_cardno_yn(String enc_cardno_yn) {
		this.enc_cardno_yn = enc_cardno_yn;
	}

	public String getCard_enc_no() {
		return card_enc_no;
	}

	public void setCard_enc_no(String card_enc_no) {
		this.card_enc_no = card_enc_no;
	}

	public String getTran_cd() {
		return tran_cd;
	}

	public void setTran_cd(String tran_cd) {
		this.tran_cd = tran_cd;
	}

	public String getEnc_info() {
		return enc_info;
	}

	public void setEnc_info(String enc_info) {
		this.enc_info = enc_info;
	}

	public String getSite_cd() {
		return site_cd;
	}

	public void setSite_cd(String site_cd) {
		this.site_cd = site_cd;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public String getVcnt_yn() {
		return vcnt_yn;
	}

	public void setVcnt_yn(String vcnt_yn) {
		this.vcnt_yn = vcnt_yn;
	}
}
