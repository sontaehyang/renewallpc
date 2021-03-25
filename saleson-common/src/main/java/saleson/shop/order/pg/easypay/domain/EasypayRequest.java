package saleson.shop.order.pg.easypay.domain;

import javax.servlet.http.HttpServletRequest;

import saleson.common.utils.ShopUtils;

public class EasypayRequest {
		
	public EasypayRequest() {
	
	}
	
	public EasypayRequest(HttpServletRequest request) {			
		
		if(ShopUtils.isMobilePage()){
			setEp_mall_id(request.getParameter("sp_mall_id"));
			setEp_tr_cd(request.getParameter("sp_tr_cd"));
			setEp_trace_no(request.getParameter("sp_trace_no"));		
			setEp_order_no(request.getParameter("sp_order_no"));		
			setEp_encrypt_data(request.getParameter("sp_encrypt_data"));
			setEp_sessionkey(request.getParameter("sp_sessionkey"));		
		} else {
			setEp_mall_id(request.getParameter("EP_mall_id"));
			setEp_tr_cd(request.getParameter("EP_tr_cd"));
			setEp_trace_no(request.getParameter("EP_trace_no"));		
			setEp_order_no(request.getParameter("EP_order_no"));		
			setEp_encrypt_data(request.getParameter("EP_encrypt_data"));
			setEp_sessionkey(request.getParameter("EP_sessionkey"));
		}
		
		
		setMgr_txtype(request.getParameter("mgr_txtype"));
		setMgr_subtype(request.getParameter("mgr_subtype"));
		setOrg_cno(request.getParameter("org_cno"));
		setMgr_amt(request.getParameter("mgr_amt"));
		setMgr_rem_amt(request.getParameter("mgr_rem_amt"));
		setMgr_bank_cd(request.getParameter("mgr_bank_cd"));
		setMgr_account(request.getParameter("mgr_account"));
		setMgr_depositor(request.getParameter("mgr_depositor"));

	}

	// 공통 인증 요청 값
	private String ep_return_url;				// 가맹점 CALLBACK URL
	private String ep_ci_url;					// CI LOGI URL
	private String ep_user_id;					// 가맹점 고객ID
	private String ep_memb_user_no;				// 가맹점 고객일련번호
	private String ep_user_nm;					// 가맹점 고객명
	private String ep_user_mail;				// 가맹점 고객 E-mail
	private String ep_user_phone1;				// 가맹점 고객 연락처1
	private String ep_user_phone2;				// 가맹점 고객 연락처2
	private String ep_user_addr;				// 가맹점 고객 주소
	private String ep_user_define1;				// 가맹점 필드1
	private String ep_user_define2;				// 가맹점 필드2
	private String ep_user_define3;				// 가맹점 필드3
	private String ep_user_define4;				// 가맹점 필드4
	private String ep_user_define5;				// 가맹점 필드5
	private String ep_user_define6;				// 가맹점 필드6
	private String ep_product_type;				// 상품정보구분
	private String ep_product_expr;				// 서비스 기간 (YYYYMMDD)
	private String ep_disp_cash_yn;				// 현금영수증 화면표시 여부
	private String ep_order_no;					// 가맹점 주문번호
	private String ep_pay_type;					// 결제방법
	private String ep_product_nm;				// 상품명
	private String ep_product_amt;				// 상품가격
	private String ep_mall_id;					// 몰ID
	
	// 카드 인증 요청 값
	private String ep_usedcard_code;			// 사용가능한 카드 LIST // FORMAT->카드코드:카드코드: ... :카드코드 EXAMPLE->029:027:031 // 빈값 : DB조회
	private String ep_quota;					// 할부개월 (카드코드-할부개월)
	private String ep_os_cert_flag;				// 해외안심클릭 사용여부(변경불가)
	private String ep_noinst_flag;				// 무이자 여부 (Y/N)
	private String ep_noinst_term;				// 무이자 기간 (카드코드-더할할부개월)
	private String ep_set_point_card_yn;		// 카드사포인트 사용여부 (Y/N)
	private String ep_point_card;				// 포인트카드 LIST
	private String ep_join_cd;					// 조인코드
	private String ep_kmotion_useyn;			// 국민앱카드 사용유무 (Y/N)
	
	// 가상계좌 인증 요청 값
	private String ep_vacct_bank;				// 가상계좌 사용가능한 은행 LIST
	private String ep_vacct_end_date;			// 입금 만료 날짜
	private String ep_vacct_end_time;			// 입금 만료 시간
	
	// 인증응답용 인증 요청 값
	private String ep_res_cd;					// 응답코드
	private String ep_res_msg;					// 응답메세지
	private String ep_tr_cd;					// 결제창 요청구분
	private String ep_ret_pay_type;				// 결제수단
	private String ep_ret_complex_yn;			// 복합결제 여부(Y/N)
	private String ep_card_code;				// 카드코드 (ISP:KVP카드코드 MPI:카드코드)
	private String ep_eci_code;					// MPI인 경우 ECI코드
	private String ep_card_req_type;			// 거래구분
	private String ep_save_useyn;				// 카드사 세이비 여부 (Y/N)
	private String ep_trace_no;					// 추적번호
	private String ep_sessionkey;				// 세션키
	private String ep_encrypt_data;				// 암호화전문
	private String ep_spay_cp;					// 간편결제 CP 코드
	private String ep_card_prefix;				// 신용카드 prefix
	private String ep_card_no_7;				// 신용카드번호 앞7자리
	    
	/* -------------------------------------------------------------------------- */
    /* ::: 변경관리 정보 설정                                                     */
    /* -------------------------------------------------------------------------- */
    private String mgr_txtype;			// 거래구분
    private String mgr_subtype;			// [선택]변경세부구분
    private String org_cno;             // [필수]원거래고유번호
    private String mgr_amt;             // [선택]부분취소/환불요청 금액
    private String mgr_rem_amt;         // [선택]부분취소 잔액    
    private String mgr_bank_cd;         // [선택]환불계좌 은행코드
    private String mgr_account;         // [선택]환불계좌 번호
    private String mgr_depositor;       // [선택]환불계좌 예금주명
    
    private String order_amount;		// 상점 주문 총액 (결제 시 상점주문금액과 실제 결제 금액 비교하기 위함)
		
	public String getEp_pay_type() {
		return ep_pay_type;
	}

	public void setEp_pay_type(String ep_pay_type) {
		this.ep_pay_type = ep_pay_type;
	}

	public String getEp_product_nm() {
		return ep_product_nm;
	}

	public void setEp_product_nm(String ep_product_nm) {
		this.ep_product_nm = ep_product_nm;
	}

	public String getEp_product_amt() {
		return ep_product_amt;
	}

	public void setEp_product_amt(String ep_product_amt) {
		this.ep_product_amt = ep_product_amt;
	}
	
	public String getEp_return_url() {
		return ep_return_url;
	}

	public void setEp_return_url(String ep_return_url) {
		this.ep_return_url = ep_return_url;
	}

	public String getEp_ci_url() {
		return ep_ci_url;
	}

	public void setEp_ci_url(String ep_ci_url) {
		this.ep_ci_url = ep_ci_url;
	}

	public String getEp_user_id() {
		return ep_user_id;
	}

	public void setEp_user_id(String ep_user_id) {
		this.ep_user_id = ep_user_id;
	}

	public String getEp_memb_user_no() {
		return ep_memb_user_no;
	}

	public void setEp_memb_user_no(String ep_memb_user_no) {
		this.ep_memb_user_no = ep_memb_user_no;
	}

	public String getEp_user_nm() {
		return ep_user_nm;
	}

	public void setEp_user_nm(String ep_user_nm) {
		this.ep_user_nm = ep_user_nm;
	}

	public String getEp_user_mail() {
		return ep_user_mail;
	}

	public void setEp_user_mail(String ep_user_mail) {
		this.ep_user_mail = ep_user_mail;
	}

	public String getEp_user_phone1() {
		return ep_user_phone1;
	}

	public void setEp_user_phone1(String ep_user_phone1) {
		this.ep_user_phone1 = ep_user_phone1;
	}

	public String getEp_user_phone2() {
		return ep_user_phone2;
	}

	public void setEp_user_phone2(String ep_user_phone2) {
		this.ep_user_phone2 = ep_user_phone2;
	}

	public String getEp_user_addr() {
		return ep_user_addr;
	}

	public void setEp_user_addr(String ep_user_addr) {
		this.ep_user_addr = ep_user_addr;
	}

	public String getEp_user_define1() {
		return ep_user_define1;
	}

	public void setEp_user_define1(String ep_user_define1) {
		this.ep_user_define1 = ep_user_define1;
	}

	public String getEp_user_define2() {
		return ep_user_define2;
	}

	public void setEp_user_define2(String ep_user_define2) {
		this.ep_user_define2 = ep_user_define2;
	}

	public String getEp_user_define3() {
		return ep_user_define3;
	}

	public void setEp_user_define3(String ep_user_define3) {
		this.ep_user_define3 = ep_user_define3;
	}

	public String getEp_user_define4() {
		return ep_user_define4;
	}

	public void setEp_user_define4(String ep_user_define4) {
		this.ep_user_define4 = ep_user_define4;
	}

	public String getEp_user_define5() {
		return ep_user_define5;
	}

	public void setEp_user_define5(String ep_user_define5) {
		this.ep_user_define5 = ep_user_define5;
	}

	public String getEp_user_define6() {
		return ep_user_define6;
	}

	public void setEp_user_define6(String ep_user_define6) {
		this.ep_user_define6 = ep_user_define6;
	}

	public String getEp_product_type() {
		return ep_product_type;
	}

	public void setEp_product_type(String ep_product_type) {
		this.ep_product_type = ep_product_type;
	}

	public String getEp_product_expr() {
		return ep_product_expr;
	}

	public void setEp_product_expr(String ep_product_expr) {
		this.ep_product_expr = ep_product_expr;
	}

	public String getEp_disp_cash_yn() {
		return ep_disp_cash_yn;
	}

	public void setEp_disp_cash_yn(String ep_disp_cash_yn) {
		this.ep_disp_cash_yn = ep_disp_cash_yn;
	}

	public String getEp_usedcard_code() {
		return ep_usedcard_code;
	}

	public void setEp_usedcard_code(String ep_usedcard_code) {
		this.ep_usedcard_code = ep_usedcard_code;
	}

	public String getEp_quota() {
		return ep_quota;
	}

	public void setEp_quota(String ep_quota) {
		this.ep_quota = ep_quota;
	}

	public String getEp_os_cert_flag() {
		return ep_os_cert_flag;
	}

	public void setEp_os_cert_flag(String ep_os_cert_flag) {
		this.ep_os_cert_flag = ep_os_cert_flag;
	}

	public String getEp_noinst_flag() {
		return ep_noinst_flag;
	}

	public void setEp_noinst_flag(String ep_noinst_flag) {
		this.ep_noinst_flag = ep_noinst_flag;
	}

	public String getEp_noinst_term() {
		return ep_noinst_term;
	}

	public void setEp_noinst_term(String ep_noinst_term) {
		this.ep_noinst_term = ep_noinst_term;
	}

	public String getEp_set_point_card_yn() {
		return ep_set_point_card_yn;
	}

	public void setEp_set_point_card_yn(String ep_set_point_card_yn) {
		this.ep_set_point_card_yn = ep_set_point_card_yn;
	}

	public String getEp_point_card() {
		return ep_point_card;
	}

	public void setEp_point_card(String ep_point_card) {
		this.ep_point_card = ep_point_card;
	}

	public String getEp_join_cd() {
		return ep_join_cd;
	}

	public void setEp_join_cd(String ep_join_cd) {
		this.ep_join_cd = ep_join_cd;
	}

	public String getEp_kmotion_useyn() {
		return ep_kmotion_useyn;
	}

	public void setEp_kmotion_useyn(String ep_kmotion_useyn) {
		this.ep_kmotion_useyn = ep_kmotion_useyn;
	}

	public String getEp_vacct_bank() {
		return ep_vacct_bank;
	}

	public void setEp_vacct_bank(String ep_vacct_bank) {
		this.ep_vacct_bank = ep_vacct_bank;
	}

	public String getEp_vacct_end_date() {
		return ep_vacct_end_date;
	}

	public void setEp_vacct_end_date(String ep_vacct_end_date) {
		this.ep_vacct_end_date = ep_vacct_end_date;
	}

	public String getEp_vacct_end_time() {
		return ep_vacct_end_time;
	}

	public void setEp_vacct_end_time(String ep_vacct_end_time) {
		this.ep_vacct_end_time = ep_vacct_end_time;
	}

	public String getEp_res_cd() {
		return ep_res_cd;
	}

	public void setEp_res_cd(String ep_res_cd) {
		this.ep_res_cd = ep_res_cd;
	}

	public String getEp_res_msg() {
		return ep_res_msg;
	}

	public void setEp_res_msg(String ep_res_msg) {
		this.ep_res_msg = ep_res_msg;
	}

	public String getEp_tr_cd() {
		return ep_tr_cd;
	}

	public void setEp_tr_cd(String ep_tr_cd) {
		this.ep_tr_cd = ep_tr_cd;
	}

	public String getEp_ret_pay_type() {
		return ep_ret_pay_type;
	}

	public void setEp_ret_pay_type(String ep_ret_pay_type) {
		this.ep_ret_pay_type = ep_ret_pay_type;
	}

	public String getEp_ret_complex_yn() {
		return ep_ret_complex_yn;
	}

	public void setEp_ret_complex_yn(String ep_ret_complex_yn) {
		this.ep_ret_complex_yn = ep_ret_complex_yn;
	}

	public String getEp_card_code() {
		return ep_card_code;
	}

	public void setEp_card_code(String ep_card_code) {
		this.ep_card_code = ep_card_code;
	}

	public String getEp_eci_code() {
		return ep_eci_code;
	}

	public void setEp_eci_code(String ep_eci_code) {
		this.ep_eci_code = ep_eci_code;
	}

	public String getEp_card_req_type() {
		return ep_card_req_type;
	}

	public void setEp_card_req_type(String ep_card_req_type) {
		this.ep_card_req_type = ep_card_req_type;
	}

	public String getEp_save_useyn() {
		return ep_save_useyn;
	}

	public void setEp_save_useyn(String ep_save_useyn) {
		this.ep_save_useyn = ep_save_useyn;
	}

	public String getEp_trace_no() {
		return ep_trace_no;
	}

	public void setEp_trace_no(String ep_trace_no) {
		this.ep_trace_no = ep_trace_no;
	}

	public String getEp_sessionkey() {
		return ep_sessionkey;
	}

	public void setEp_sessionkey(String ep_sessionkey) {
		this.ep_sessionkey = ep_sessionkey;
	}

	public String getEp_encrypt_data() {
		return ep_encrypt_data;
	}

	public void setEp_encrypt_data(String ep_encrypt_data) {
		this.ep_encrypt_data = ep_encrypt_data;
	}

	public String getEp_spay_cp() {
		return ep_spay_cp;
	}

	public void setEp_spay_cp(String ep_spay_cp) {
		this.ep_spay_cp = ep_spay_cp;
	}

	public String getEp_card_prefix() {
		return ep_card_prefix;
	}

	public void setEp_card_prefix(String ep_card_prefix) {
		this.ep_card_prefix = ep_card_prefix;
	}

	public String getEp_card_no_7() {
		return ep_card_no_7;
	}

	public void setEp_card_no_7(String ep_card_no_7) {
		this.ep_card_no_7 = ep_card_no_7;
	}
	
	public String getEp_order_no() {
		return ep_order_no;
	}

	public void setEp_order_no(String ep_order_no) {
		this.ep_order_no = ep_order_no;
	}
	
	public String getMgr_txtype() {
		return mgr_txtype;
	}

	public void setMgr_txtype(String mgr_txtype) {
		this.mgr_txtype = mgr_txtype;
	}

	public String getMgr_subtype() {
		return mgr_subtype;
	}

	public void setMgr_subtype(String mgr_subtype) {
		this.mgr_subtype = mgr_subtype;
	}

	public String getOrg_cno() {
		return org_cno;
	}

	public void setOrg_cno(String org_cno) {
		this.org_cno = org_cno;
	}

	public String getMgr_amt() {
		return mgr_amt;
	}

	public void setMgr_amt(String mgr_amt) {
		this.mgr_amt = mgr_amt;
	}

	public String getMgr_rem_amt() {
		return mgr_rem_amt;
	}

	public void setMgr_rem_amt(String mgr_rem_amt) {
		this.mgr_rem_amt = mgr_rem_amt;
	}

	public String getMgr_bank_cd() {
		return mgr_bank_cd;
	}

	public void setMgr_bank_cd(String mgr_bank_cd) {
		this.mgr_bank_cd = mgr_bank_cd;
	}

	public String getMgr_account() {
		return mgr_account;
	}

	public void setMgr_account(String mgr_account) {
		this.mgr_account = mgr_account;
	}

	public String getMgr_depositor() {
		return mgr_depositor;
	}

	public void setMgr_depositor(String mgr_depositor) {
		this.mgr_depositor = mgr_depositor;
	}

	public String getEp_mall_id() {
		return ep_mall_id;
	}

	public void setEp_mall_id(String ep_mall_id) {
		this.ep_mall_id = ep_mall_id;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
}
