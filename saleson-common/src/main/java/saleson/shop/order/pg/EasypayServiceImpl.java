package saleson.shop.order.pg;

import com.inicis.inipay.escrow.INIescrow;
import com.kicc.EasyPayClient;
import com.onlinepowers.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.utils.ShopUtils;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.easypay.domain.EasypayRequest;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service("easypayService")
public class EasypayServiceImpl implements PgService {

	@Autowired
	Environment environment;

	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) {

		HashMap<String, Object> map = new HashMap<>();

		EasypayRequest easypayRequest = ((PgData)data).getEasypayRequest();

		String disp_cash_yn = "N";

		if("Y".equals(environment.getProperty("pg.autoCashReceipt")))
			disp_cash_yn = "Y";

		if(!ShopUtils.isMobilePage()){
			// 공통
			map.put("EP_mall_id", environment.getProperty("pg.easypay.g.conf.site.cd"));
			map.put("EP_mall_nm", environment.getProperty("pg.easypay.g.conf.site.name"));
			map.put("EP_order_no", easypayRequest.getEp_order_no());

			map.put("EP_currency", "00");															// 통화코드 : 00 - 원
			map.put("EP_product_nm", easypayRequest.getEp_product_nm());
			map.put("EP_product_amt", easypayRequest.getEp_product_amt());

			map.put("EP_lang_flang", "KOR");														// 언어 (KOR / ENG)
			map.put("EP_charset", "UTF-8");															// Charset : EUC-KR(default) / UTF-8
			map.put("EP_user_id", easypayRequest.getEp_user_id());
			map.put("EP_memb_user_no", easypayRequest.getEp_memb_user_no());
			map.put("EP_user_nm", easypayRequest.getEp_user_nm());
			map.put("EP_user_mail", easypayRequest.getEp_user_mail());
			map.put("EP_user_phone1", easypayRequest.getEp_user_phone1());
			map.put("EP_user_phone2", easypayRequest.getEp_user_phone2());
			map.put("EP_user_addr", easypayRequest.getEp_user_addr());
			map.put("EP_product_type", "0");														// 상품정보구분 0 - 실물 , 1 - 서비스
			//	    map.put("EP_product_expr", "");															// 상품서비스기간 : YYYYMMDD
			map.put("EP_return_url", environment.getProperty("saleson.url.shoppingmall")+"/order/easypay/order_res");	// 리턴받을 URL (ex http://test.com/web/normal/order_res.jsp)
			map.put("EP_pay_type", this.getPayType(easypayRequest.getEp_pay_type()));
			map.put("EP_cert_type", "21");															// 인증타입 (인증 - 21, 비인증 - 22)
			map.put("EP_window_type", environment.getProperty("pg.easypay.viewType"));	// 결제창 호출 타입
			map.put("EP_disp_cash_yn", disp_cash_yn);												// 결제창 현금영수증 표시 여부
			map.put("orderCode", easypayRequest.getEp_order_no());

			// 신용카드
			//	    map.put("EP_usedcard_code", easypay.getEp_usedcard_code());
			//	    map.put("EP_quota", easypay.getEp_quota());
			//	    map.put("EP_noinst_term", easypay.getEp_noinst_term());
			//	    map.put("EP_point_card", easypay.getEp_point_card());

			// 가상계좌
			//	    map.put("EP_vacct_bank", easypay.getEp_vacct_bank());
			//	    map.put("EP_vacct_end_date", easypay.getEp_vacct_end_date());
			//	    map.put("EP_vacct_end_time", easypay.getEp_vacct_end_time());
		} else {
			// 공통
			map.put("sp_mall_id", environment.getProperty("pg.easypay.g.conf.site.cd"));
			map.put("sp_mall_nm", environment.getProperty("pg.easypay.g.conf.site.name"));
			map.put("sp_order_no", easypayRequest.getEp_order_no());

			map.put("sp_currency", "00");															// 통화코드 : 00 - 원
			map.put("sp_product_nm", easypayRequest.getEp_product_nm());
			map.put("sp_product_amt", easypayRequest.getEp_product_amt());

			map.put("sp_lang_flang", "KOR");														// 언어 (KOR / ENG)
			map.put("sp_charset", "UTF-8");															// Charset : EUC-KR(default) / UTF-8
			map.put("sp_user_id", easypayRequest.getEp_user_id());
			map.put("sp_memb_user_no", easypayRequest.getEp_memb_user_no());
			map.put("sp_user_nm", easypayRequest.getEp_user_nm());
			map.put("sp_user_mail", easypayRequest.getEp_user_mail());
			map.put("sp_user_phone1", easypayRequest.getEp_user_phone1());
			map.put("sp_user_phone2", easypayRequest.getEp_user_phone2());
			map.put("sp_user_addr", easypayRequest.getEp_user_addr());
			map.put("sp_product_type", "0");														// 상품정보구분 0 - 실물 , 1 - 서비스
			//	    map.put("sp_product_expr", "");															// 상품서비스기간 : YYYYMMDD
			map.put("sp_return_url", environment.getProperty("saleson.url.shoppingmall")+"/m/order/easypay/order_res");	// 리턴받을 URL (ex http://test.com/web/normal/order_res.jsp)
			map.put("sp_pay_type", this.getPayType(easypayRequest.getEp_pay_type()));
			map.put("sp_cert_type", "21");															// 인증타입 (인증 - 21, 비인증 - 22)
			map.put("sp_disp_cash_yn", disp_cash_yn);												// 결제창 현금영수증 표시 여부
			map.put("orderCode", easypayRequest.getEp_order_no());

			// 신용카드
			//	    map.put("sp_usedcard_code", easypay.getEp_usedcard_code());
			//	    map.put("sp_quota", easypay.getEp_quota());
			//	    map.put("sp_noinst_term", easypay.getEp_noinst_term());
			//	    map.put("sp_point_card", easypay.getEp_point_card());

			// 가상계좌
			//	    map.put("sp_vacct_bank", easypay.getEp_vacct_bank());
			//	    map.put("sp_vacct_end_date", easypay.getEp_vacct_end_date());
			//	    map.put("sp_vacct_end_time", easypay.getEp_vacct_end_time());
		}




		return map;
	}

	@Override
	public OrderPgData pay(Object data, HttpSession session) {
		OrderPgData orderPgData = new OrderPgData();

		EasypayRequest easypayRequest = ((PgData)data).getEasypayRequest();

		/* -------------------------------------------------------------------------- */
		/* ::: 처리구분 설정                                                          */
		/* -------------------------------------------------------------------------- */
		final String TRAN_CD_NOR_PAYMENT    = "00101000";   // 승인(일반, 에스크로)
		final String TRAN_CD_NOR_MGR        = "00201000";   // 변경(일반, 에스크로)

		/* -------------------------------------------------------------------------- */
		/* ::: 지불정보 설정                                                          */
		/* -------------------------------------------------------------------------- */
		final String GW_URL                 =  environment.getProperty("pg.easypay.g.conf.gw.url");  // Gateway URL ( test )
		//final String GW_URL               = "gw.easypay.co.kr";      // Gateway URL ( real )
		final String GW_PORT                = environment.getProperty("pg.easypay.g.conf.gw.port");  // 포트번호(변경불가)

		/* -------------------------------------------------------------------------- */
		/* ::: 지불 데이터 셋업 (업체에 맞게 수정)                                    */
		/* -------------------------------------------------------------------------- */
		/* ※ 주의 ※                                                                 */
		/* cert_file 변수 설정                                                        */
		/* - pg_cert.pem 파일이 있는 디렉토리의  절대 경로 설정                       */
		/* log_dir 변수 설정                                                          */
		/* - log 디렉토리 설정                                                        */
		/* log_level 변수 설정                                                        */
		/* - log 레벨 설정 (1 to 99(높을수록 상세))                                   */
		/* -------------------------------------------------------------------------- */
		final String CERT_FILE              = environment.getProperty("pg.easypay.g.conf.key.dir");
		final String LOG_DIR                = environment.getProperty("pg.easypay.g.conf.log.dir");
		final int LOG_LEVEL                 = Integer.parseInt(environment.getProperty("pg.easypay.g.conf.log.level"));

		/* -------------------------------------------------------------------------- */
		/* ::: 승인요청 정보 설정                                               			      */
		/* -------------------------------------------------------------------------- */
		//[헤더]
		String tr_cd            = StringUtils.null2void(easypayRequest.getEp_tr_cd());           // [필수]요청구분
		String trace_no         = StringUtils.null2void(easypayRequest.getEp_trace_no());        // [필수]추적고유번호
		String order_no         = StringUtils.null2void(easypayRequest.getEp_order_no());        // [필수]주문번호
		String mall_id          = StringUtils.null2void(easypayRequest.getEp_mall_id());         // [필수]몰아이디
		//[공통]
		String encrypt_data     = StringUtils.null2void(easypayRequest.getEp_encrypt_data());    // [필수]암호화 데이타
		String sessionkey       = StringUtils.null2void(easypayRequest.getEp_sessionkey());      // [필수]암호화키

		/* -------------------------------------------------------------------------- */
		/* ::: 변경관리 정보 설정                                                     		  */
		/* -------------------------------------------------------------------------- */
		String mgr_txtype       = StringUtils.null2void(easypayRequest.getMgr_txtype());         // [필수]거래구분
		String mgr_subtype      = StringUtils.null2void(easypayRequest.getMgr_subtype());        // [선택]변경세부구분
		String org_cno          = StringUtils.null2void(easypayRequest.getOrg_cno());            // [필수]원거래고유번호
		String mgr_amt          = StringUtils.null2void(easypayRequest.getMgr_amt());            // [선택]부분취소/환불요청 금액
		String mgr_rem_amt      = StringUtils.null2void(easypayRequest.getMgr_rem_amt());        // [선택]부분취소 잔액
		String mgr_bank_cd      = StringUtils.null2void(easypayRequest.getMgr_bank_cd());        // [선택]환불계좌 은행코드
		String mgr_account      = StringUtils.null2void(easypayRequest.getMgr_account());        // [선택]환불계좌 번호
		String mgr_depositor    = StringUtils.null2void(easypayRequest.getMgr_depositor());      // [선택]환불계좌 예금주명

		/* -------------------------------------------------------------------------- */
		/* ::: 전문                                                                   */
		/* -------------------------------------------------------------------------- */
		String mgr_data    = "";     // 변경정보
		String mall_data   = "";     // 요청전문

		/* -------------------------------------------------------------------------- */
		/* ::: 결제 결과                                                              */
		/* -------------------------------------------------------------------------- */
		String bDBProc              = "";     //가맹점DB처리성공여부
		String res_cd               = "";     //응답코드
		String res_msg              = "";     //응답메시지
		String r_cno                = "";     //PG거래번호
		String r_amount             = "";     //총 결제금액
		String r_order_no           = "";     //주문번호
		String r_auth_no            = "";     //승인번호
		String r_tran_date          = "";     //승인일시
		String r_escrow_yn          = "";     //에스크로 사용유무
		String r_complex_yn         = "";     //복합결제 유무
		String r_stat_cd            = "";     //상태코드
		String r_stat_msg           = "";     //상태메시지
		String r_pay_type           = "";     //결제수단
		String r_mall_id            = "";     //가맹점 Mall ID
		String r_card_no            = "";     //카드번호
		String r_issuer_cd          = "";     //발급사코드
		String r_issuer_nm          = "";     //발급사명
		String r_acquirer_cd        = "";     //매입사코드
		String r_acquirer_nm        = "";     //매입사명
		String r_install_period     = "";     //할부개월
		String r_noint              = "";     //무이자여부
		String r_part_cancel_yn     = "";     //부분취소 가능여부
		String r_card_gubun         = "";     //신용카드 종류
		String r_card_biz_gubun     = "";     //신용카드 구분
		String r_cpon_flag          = "";     //쿠폰사용유무
		String r_bank_cd            = "";     //은행코드
		String r_bank_nm            = "";     //은행명
		String r_account_no         = "";     //계좌번호
		String r_deposit_nm         = "";     //입금자명
		String r_expire_date        = "";     //계좌사용만료일
		String r_cash_res_cd        = "";     //현금영수증 결과코드
		String r_cash_res_msg       = "";     //현금영수증 결과메세지
		String r_cash_auth_no       = "";     //현금영수증 승인번호
		String r_cash_tran_date     = "";     //현금영수증 승인일시
		String r_cash_issue_type    = "";     //현금영수증발행용도
		String r_cash_auth_type     = "";     //인증구분
		String r_cash_auth_value    = "";     //인증번호
		String r_auth_id            = "";     //PhoneID
		String r_billid             = "";     //인증번호
		String r_mobile_no          = "";     //휴대폰번호
		String r_mob_ansim_yn       = "";     //안심결제 사용유무
		String r_ars_no             = "";     //전화번호
		String r_cp_cd              = "";     //포인트사/쿠폰사
		String r_cpon_auth_no       = "";     //쿠폰승인번호
		String r_cpon_tran_date     = "";     //쿠폰승인일시
		String r_cpon_no            = "";     //쿠폰번호
		String r_remain_cpon        = "";     //쿠폰잔액
		String r_used_cpon          = "";     //쿠폰 사용금액
		String r_rem_amt            = "";     //잔액
		String r_bk_pay_yn          = "";     //장바구니 결제여부
		String r_canc_acq_date      = "";     //매입취소일시
		String r_canc_date          = "";     //취소일시
		String r_refund_date        = "";     //환불예정일시

		/* -------------------------------------------------------------------------- */
		/* ::: EasyPayClient 인스턴스 생성 [변경불가 !!].                             */
		/* -------------------------------------------------------------------------- */
		EasyPayClient easyPayClient = new EasyPayClient();
		easyPayClient.easypay_setenv_init( GW_URL, GW_PORT, CERT_FILE, LOG_DIR, LOG_LEVEL );
		easyPayClient.easypay_reqdata_init();

		/* -------------------------------------------------------------------------- */
		/* ::: 승인요청(플러그인 암호화 전문 설정)                                    */
		/* -------------------------------------------------------------------------- */
		if( TRAN_CD_NOR_PAYMENT.equals(tr_cd) ){

			// 승인요청 전문 설정
			easyPayClient.easypay_set_trace_no(trace_no);
			easyPayClient.easypay_encdata_set(encrypt_data, sessionkey);

			/* -------------------------------------------------------------------------- */
			/* ::: 변경관리 요청                                                          */
			/* -------------------------------------------------------------------------- */
		}else if( TRAN_CD_NOR_MGR.equals( tr_cd ) ) {

			int easypay_mgr_data_item;
			easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );

			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype"    , mgr_txtype    );          // [필수]거래구분
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_subtype"   , mgr_subtype   );          // [선택]변경세부구분
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno"       , org_cno       );          // [필수]원거래고유번호
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_amt"       , mgr_amt       );          // [선택]금액
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_rem_amt"   , mgr_rem_amt   );          // [선택]부분취소 잔액
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_bank_cd"   , mgr_bank_cd   );          // [선택]환불계좌 은행코드
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_account"   , mgr_account   );          // [선택]환불계좌 번호
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_depositor" , mgr_depositor );          // [선택]환불계좌 예금주명
			easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip"        , saleson.common.utils.CommonUtils.getClientIp());// [필수]요청자 IP
		}

		/* -------------------------------------------------------------------------- */
		/* ::: 실행                                                                   */
		/* -------------------------------------------------------------------------- */
		if ( tr_cd.length() > 0 ) {
			easyPayClient.easypay_run( mall_id, tr_cd, order_no );

			res_cd = easyPayClient.res_cd;
			res_msg = easyPayClient.res_msg;
		}
		else {
			res_cd  = "M114";
			res_msg = "연동 오류|tr_cd값이 설정되지 않았습니다.";
		}
		/* -------------------------------------------------------------------------- */
		/* ::: 결과 처리                                                              */
		/* -------------------------------------------------------------------------- */

		r_cno              = easyPayClient.easypay_get_res( "cno"             );     //PG거래번호
		r_amount           = easyPayClient.easypay_get_res( "amount"          );     //총 결제금액
		r_order_no         = easyPayClient.easypay_get_res( "order_no"        );     //주문번호
		r_auth_no          = easyPayClient.easypay_get_res( "auth_no"         );     //승인번호
		r_tran_date        = easyPayClient.easypay_get_res( "tran_date"       );     //승인일시
		r_escrow_yn        = easyPayClient.easypay_get_res( "escrow_yn"       );     //에스크로 사용유무
		r_complex_yn       = easyPayClient.easypay_get_res( "complex_yn"      );     //복합결제 유무
		r_stat_cd          = easyPayClient.easypay_get_res( "stat_cd"         );     //상태코드
		r_stat_msg         = easyPayClient.easypay_get_res( "stat_msg"        );     //상태메시지
		r_pay_type         = easyPayClient.easypay_get_res( "pay_type"        );     //결제수단
		r_mall_id          = easyPayClient.easypay_get_res( "mall_id"         );     //가맹점 Mall ID
		r_card_no          = easyPayClient.easypay_get_res( "card_no"         );     //카드번호
		r_issuer_cd        = easyPayClient.easypay_get_res( "issuer_cd"       );     //발급사코드
		r_issuer_nm        = easyPayClient.easypay_get_res( "issuer_nm"       );     //발급사명
		r_acquirer_cd      = easyPayClient.easypay_get_res( "acquirer_cd"     );     //매입사코드
		r_acquirer_nm      = easyPayClient.easypay_get_res( "acquirer_nm"     );     //매입사명
		r_install_period   = easyPayClient.easypay_get_res( "install_period"  );     //할부개월
		r_noint            = easyPayClient.easypay_get_res( "noint"           );     //무이자여부
		r_part_cancel_yn   = easyPayClient.easypay_get_res( "part_cancel_yn"  );     //부분취소 가능여부
		r_card_gubun       = easyPayClient.easypay_get_res( "card_gubun"      );     //신용카드 종류
		r_card_biz_gubun   = easyPayClient.easypay_get_res( "card_biz_gubun"  );     //신용카드 구분
		r_cpon_flag        = easyPayClient.easypay_get_res( "cpon_flag"       );     //쿠폰사용유무
		r_bank_cd          = easyPayClient.easypay_get_res( "bank_cd"         );     //은행코드
		r_bank_nm          = easyPayClient.easypay_get_res( "bank_nm"         );     //은행명
		r_account_no       = easyPayClient.easypay_get_res( "account_no"      );     //계좌번호
		r_deposit_nm       = easyPayClient.easypay_get_res( "deposit_nm"      );     //입금자명
		r_expire_date      = easyPayClient.easypay_get_res( "expire_date"     );     //계좌사용만료일
		r_cash_res_cd      = easyPayClient.easypay_get_res( "cash_res_cd"     );     //현금영수증 결과코드
		r_cash_res_msg     = easyPayClient.easypay_get_res( "cash_res_msg"    );     //현금영수증 결과메세지
		r_cash_auth_no     = easyPayClient.easypay_get_res( "cash_auth_no"    );     //현금영수증 승인번호
		r_cash_tran_date   = easyPayClient.easypay_get_res( "cash_tran_date"  );     //현금영수증 승인일시
		r_cash_issue_type  = easyPayClient.easypay_get_res( "cash_issue_type" );     //현금영수증발행용도
		r_cash_auth_type   = easyPayClient.easypay_get_res( "cash_auth_type"  );     //인증구분
		r_cash_auth_value  = easyPayClient.easypay_get_res( "cash_auth_value" );     //인증번호
		r_auth_id          = easyPayClient.easypay_get_res( "auth_id"         );     //PhoneID
		r_billid           = easyPayClient.easypay_get_res( "billid"          );     //인증번호
		r_mobile_no        = easyPayClient.easypay_get_res( "mobile_no"       );     //휴대폰번호
		r_mob_ansim_yn     = easyPayClient.easypay_get_res( "mob_ansim_yn"    );     //안심결제 사용유무
		r_ars_no           = easyPayClient.easypay_get_res( "ars_no"          );     //전화번호
		r_cp_cd            = easyPayClient.easypay_get_res( "cp_cd"           );     //포인트사/쿠폰사
		r_cpon_auth_no     = easyPayClient.easypay_get_res( "cpon_auth_no"    );     //쿠폰승인번호
		r_cpon_tran_date   = easyPayClient.easypay_get_res( "cpon_tran_date"  );     //쿠폰승인일시
		r_cpon_no          = easyPayClient.easypay_get_res( "cpon_no"         );     //쿠폰번호
		r_remain_cpon      = easyPayClient.easypay_get_res( "remain_cpon"     );     //쿠폰잔액
		r_used_cpon        = easyPayClient.easypay_get_res( "used_cpon"       );     //쿠폰 사용금액
		r_rem_amt          = easyPayClient.easypay_get_res( "rem_amt"         );     //잔액
		r_bk_pay_yn        = easyPayClient.easypay_get_res( "bk_pay_yn"       );     //장바구니 결제여부
		r_canc_acq_date    = easyPayClient.easypay_get_res( "canc_acq_date"   );     //매입취소일시
		r_canc_date        = easyPayClient.easypay_get_res( "canc_date"       );     //취소일시
		r_refund_date      = easyPayClient.easypay_get_res( "refund_date"     );     //환불예정일시

		/* -------------------------------------------------------------------------- */
		/* ::: 가맹점 DB 처리                                                         */
		/* -------------------------------------------------------------------------- */
		/* 응답코드(res_cd)가 "0000" 이면 정상승인 입니다.                            */
		/* r_amount가 주문DB의 금액과 다를 시 반드시 취소 요청을 하시기 바랍니다.     */
		/* DB 처리 실패 시 취소 처리를 해주시기 바랍니다.                             */
		/* -------------------------------------------------------------------------- */
		if ( res_cd.equals("0000") ) {

			orderPgData.setSuccess(true);
			orderPgData.setPgKey(r_cno);
			orderPgData.setPgAuthCode(res_cd);

			String partCancelFlag = "N";
			if ("card".equals(getPayType(r_pay_type))) {
				partCancelFlag = "Y".equals(r_part_cancel_yn) ? "Y" : "N";
			} else if ("vbank".equals(getPayType(r_pay_type))) {
				partCancelFlag = "Y";
			} else if ("realtimebank".equals(getPayType(r_pay_type))) {
				partCancelFlag = "Y";
			}

			orderPgData.setPartCancelFlag(partCancelFlag);
			orderPgData.setPartCancelDetail("");

			if ("vbank".equals(getPayType(r_pay_type))) {
				orderPgData.setBankVirtualNo(r_account_no);
				orderPgData.setBankCode(r_bank_cd);
				orderPgData.setBankName(r_bank_nm);
				orderPgData.setBankInName(r_deposit_nm);
				orderPgData.setBankDate(r_expire_date);
			} else if ("realtimebank".equals(getPayType(r_pay_type))) {
				orderPgData.setBankCode(r_bank_cd);
				orderPgData.setBankName(r_bank_nm);
				orderPgData.setBankDate(r_expire_date);
			}

			StringBuffer sb = new StringBuffer();

			String[] keys = new String[]{
				"cno", "amount", "tran_date"
			};

			for(String key : keys) {
				String value = easyPayClient.easypay_get_res(key);
				sb.append(key + " -> " + value + "\n");
			}

			if (r_pay_type.equals(getPayType("card"))) {
				sb.append("card_no -> " + r_card_no + "\n");
				sb.append("install_period -> " + r_install_period + "\n");
				sb.append("noint -> " + r_noint + "\n");
				sb.append("issuer_cd -> " + r_issuer_cd + "\n");
				sb.append("issuer_nm -> " + r_issuer_nm + "\n");
				sb.append("part_cancel_yn -> " + r_part_cancel_yn + "\n");

			} else if (r_pay_type.equals(getPayType("realtimebank"))){
				sb.append("bank_nm -> " + r_bank_nm + "\n");
				sb.append("bank_cd -> " + r_bank_cd + "\n");
			} else if (r_pay_type.equals(getPayType("vbank"))) {
				sb.append("bank_nm -> " + r_bank_nm + "\n");
				sb.append("bank_cd -> " + r_bank_cd + "\n");
				sb.append("deposit_nm -> " + r_deposit_nm + "\n");
				sb.append("account_no -> " + r_account_no + "\n");
			}

			if(r_cash_res_cd.equals("0000")){
				sb.append("cash_no -> " + r_cash_auth_no + "\n");
				sb.append("cash_issue_type -> " + r_cash_issue_type + "\n");
				sb.append("cash_auth_value -> " + r_cash_auth_value + "\n");
			}

			orderPgData.setPgProcInfo(sb.toString());
			orderPgData.setPgPaymentType(getPayType(r_pay_type));
			orderPgData.setErrorMessage(res_msg);
			orderPgData.setPartCancelFlag(StringUtils.isEmpty(r_part_cancel_yn) ? "N" : r_part_cancel_yn);
			orderPgData.setPartCancelDetail("");

			bDBProc = "true";     // DB처리 성공 시 "true", 실패 시 "false"

			if(!r_amount.equals(easypayRequest.getOrder_amount()))
				bDBProc = "false";

			if ( !bDBProc.equals("true") ) {
				// 승인요청이 실패 시 아래 실행
				if( TRAN_CD_NOR_PAYMENT.equals(tr_cd) ) {
					int easypay_mgr_data_item;

					easyPayClient.easypay_reqdata_init();

					tr_cd = TRAN_CD_NOR_MGR;
					easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );
					if ( !r_escrow_yn.equals( "Y" ) ) {
						easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype", "40"   );
					}
					else {
						easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype",  "61"   );
						easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_subtype", "ES02" );
					}
					easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno",  r_cno     );
					easyPayClient.easypay_deli_us( easypay_mgr_data_item, "order_no", order_no  );
					easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip",   saleson.common.utils.CommonUtils.getClientIp());
					easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_id",   "MALL_R_TRANS" );
					easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_msg",  "DB 처리 실패로 망취소"  );

					easyPayClient.easypay_run( mall_id, tr_cd, order_no );

					res_cd = easyPayClient.res_cd;
					res_msg = easyPayClient.res_msg;
					r_cno = easyPayClient.easypay_get_res( "cno"              );    // PG거래번호
					r_canc_date = easyPayClient.easypay_get_res( "canc_date"  );    //취소일시
				}
			}
		}

		if (orderPgData.isSuccess() == false) {
			orderPgData.setErrorMessage(res_msg);
		}

		return orderPgData;
	}

	@Override
	public boolean cancel(OrderPgData orderPgData) {
		/* -------------------------------------------------------------------------- */
		/* ::: 처리구분 설정                                                          */
		/* -------------------------------------------------------------------------- */
		final String TRAN_CD_NOR_MGR        = "00201000";   // 변경(일반, 에스크로)
		/* -------------------------------------------------------------------------- */
		/* ::: 지불정보 설정                                                          */
		/* -------------------------------------------------------------------------- */
		final String GW_URL                 =  environment.getProperty("pg.easypay.g.conf.gw.url");  // Gateway URL ( test )
		//final String GW_URL               = "gw.easypay.co.kr";      // Gateway URL ( real )
		final String GW_PORT                = environment.getProperty("pg.easypay.g.conf.gw.port");  // 포트번호(변경불가)

		/* -------------------------------------------------------------------------- */
		/* ::: 지불 데이터 셋업 (업체에 맞게 수정)                                    */
		/* -------------------------------------------------------------------------- */
		/* ※ 주의 ※                                                                 */
		/* cert_file 변수 설정                                                        */
		/* - pg_cert.pem 파일이 있는 디렉토리의  절대 경로 설정                       */
		/* log_dir 변수 설정                                                          */
		/* - log 디렉토리 설정                                                        */
		/* log_level 변수 설정                                                        */
		/* - log 레벨 설정 (1 to 99(높을수록 상세))                                   */
		/* -------------------------------------------------------------------------- */
		final String CERT_FILE              = environment.getProperty("pg.easypay.g.conf.key.dir");
		final String LOG_DIR                = environment.getProperty("pg.easypay.g.conf.key.log");
		final int LOG_LEVEL                 = Integer.parseInt(environment.getProperty("pg.easypay.g.conf.log.level"));

		/* -------------------------------------------------------------------------- */
		/* ::: 승인요청 정보 설정                                               			      */
		/* -------------------------------------------------------------------------- */
		//[헤더]
		String tr_cd            = StringUtils.null2void(TRAN_CD_NOR_MGR);           									// [필수]요청구분
		String order_no         = StringUtils.null2void(orderPgData.getOrderCode());        							// [필수]주문번호
		String mall_id          = StringUtils.null2void(orderPgData.getPgServiceMid());     							// [필수]몰아이디

		/* -------------------------------------------------------------------------- */
		/* ::: 변경관리 정보 설정                                                     		  */
		/* -------------------------------------------------------------------------- */
		String mgr_txtype       = StringUtils.null2void("40");         							 						// [필수]거래구분 (20 : 매입요청, 31 : 부분매입취소, 32 : 부분승인취소, 33 : 계좌이체부분취소, 40 : 즉시취소, 60 : 환불, 62 : 부분환불)
		// 20,31,32는 카드결제, 33은 실시간계좌이체, 60,62는 가상계좌의 경우일 때. 40은 공통
		String mgr_subtype      = StringUtils.null2void("RF01");        						 						// [선택]변경세부구분	(환불시 필수)
		String org_cno          = StringUtils.null2void(orderPgData.getPgKey());            	 						// [필수]원거래고유번호
		String mgr_amt          = StringUtils.null2void("");            						 						// [선택]부분취소/환불요청 금액
		String mgr_rem_amt      = StringUtils.null2void("");        							 						// [선택]부분취소 잔액
		String mgr_bank_cd 		= StringUtils.null2void("");        							 						// [선택]환불계좌 은행코드
		String mgr_account 		= StringUtils.null2void("");        							 						// [선택]환불계좌 번호
		String mgr_depositor 	= StringUtils.null2void("");        							 						// [선택]환불계좌 예금주명

		// 환불 계좌정보가 있을 경우
		if(StringUtils.isNotEmpty((orderPgData.getReturnAccountNo()))){
			mgr_bank_cd      = StringUtils.null2void(ShopUtils.getBankCode("EASYPAY", orderPgData.getReturnBankName()));        // [선택]환불계좌 은행코드
			mgr_account      = StringUtils.null2void(orderPgData.getReturnAccountNo());       						 // [선택]환불계좌 번호
			mgr_depositor    = StringUtils.null2void(orderPgData.getReturnName());      		 					 // [선택]환불계좌 예금주명
		}
		/* -------------------------------------------------------------------------- */
		/* ::: 전문                                                                  		  */
		/* -------------------------------------------------------------------------- */
		String mgr_data    = "";     // 변경정보
		String mall_data   = "";     // 요청전문

		/* -------------------------------------------------------------------------- */
		/* ::: 결제 결과                                                           			  */
		/* -------------------------------------------------------------------------- */
		String bDBProc              = "";     //가맹점DB처리성공여부
		String res_cd               = "";     //응답코드
		String res_msg              = "";     //응답메시지
		String r_cno                = "";     //PG거래번호
		String r_amount             = "";     //총 결제금액
		String r_order_no           = "";     //주문번호
		String r_auth_no            = "";     //승인번호
		String r_tran_date          = "";     //승인일시
		String r_escrow_yn          = "";     //에스크로 사용유무
		String r_complex_yn         = "";     //복합결제 유무
		String r_stat_cd            = "";     //상태코드
		String r_stat_msg           = "";     //상태메시지
		String r_pay_type           = "";     //결제수단
		String r_mall_id            = "";     //가맹점 Mall ID
		String r_card_no            = "";     //카드번호
		String r_issuer_cd          = "";     //발급사코드
		String r_issuer_nm          = "";     //발급사명
		String r_acquirer_cd        = "";     //매입사코드
		String r_acquirer_nm        = "";     //매입사명
		String r_install_period     = "";     //할부개월
		String r_noint              = "";     //무이자여부
		String r_part_cancel_yn     = "";     //부분취소 가능여부
		String r_card_gubun         = "";     //신용카드 종류
		String r_card_biz_gubun     = "";     //신용카드 구분
		String r_cpon_flag          = "";     //쿠폰사용유무
		String r_bank_cd            = "";     //은행코드
		String r_bank_nm            = "";     //은행명
		String r_account_no         = "";     //계좌번호
		String r_deposit_nm         = "";     //입금자명
		String r_expire_date        = "";     //계좌사용만료일
		String r_cash_res_cd        = "";     //현금영수증 결과코드
		String r_cash_res_msg       = "";     //현금영수증 결과메세지
		String r_cash_auth_no       = "";     //현금영수증 승인번호
		String r_cash_tran_date     = "";     //현금영수증 승인일시
		String r_cash_issue_type    = "";     //현금영수증발행용도
		String r_cash_auth_type     = "";     //인증구분
		String r_cash_auth_value    = "";     //인증번호
		String r_auth_id            = "";     //PhoneID
		String r_billid             = "";     //인증번호
		String r_mobile_no          = "";     //휴대폰번호
		String r_mob_ansim_yn       = "";     //안심결제 사용유무
		String r_ars_no             = "";     //전화번호
		String r_cp_cd              = "";     //포인트사/쿠폰사
		String r_cpon_auth_no       = "";     //쿠폰승인번호
		String r_cpon_tran_date     = "";     //쿠폰승인일시
		String r_cpon_no            = "";     //쿠폰번호
		String r_remain_cpon        = "";     //쿠폰잔액
		String r_used_cpon          = "";     //쿠폰 사용금액
		String r_rem_amt            = "";     //잔액
		String r_bk_pay_yn          = "";     //장바구니 결제여부
		String r_canc_acq_date      = "";     //매입취소일시
		String r_canc_date          = "";     //취소일시
		String r_refund_date        = "";     //환불예정일시

		/* -------------------------------------------------------------------------- */
		/* ::: EasyPayClient 인스턴스 생성 [변경불가 !!].                             */
		/* -------------------------------------------------------------------------- */
		EasyPayClient easyPayClient = new EasyPayClient();
		easyPayClient.easypay_setenv_init( GW_URL, GW_PORT, CERT_FILE, LOG_DIR, LOG_LEVEL );
		easyPayClient.easypay_reqdata_init();

		int easypay_mgr_data_item;
		easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );

		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype"    , mgr_txtype    );          				// [필수]거래구분
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_subtype"   , mgr_subtype   );          				// [선택]변경세부구분
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno"       , org_cno       );          				// [필수]원거래고유번호
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_amt"       , mgr_amt       );          				// [선택]금액
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_rem_amt"   , mgr_rem_amt   );          				// [선택]부분취소 잔액
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_bank_cd"   , mgr_bank_cd   );          				// [선택]환불계좌 은행코드
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_account"   , mgr_account   );          				// [선택]환불계좌 번호
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_depositor" , mgr_depositor );          				// [선택]환불계좌 예금주명
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip"        , saleson.common.utils.CommonUtils.getClientIp());	// [필수]요청자 IP

		/* -------------------------------------------------------------------------- */
		/* ::: 실행                                                                   */
		/* -------------------------------------------------------------------------- */
		if ( tr_cd.length() > 0 ) {
			easyPayClient.easypay_run( mall_id, tr_cd, order_no );

			res_cd = easyPayClient.res_cd;
			res_msg = easyPayClient.res_msg;
		}
		else {
			res_cd  = "M114";
			res_msg = "연동 오류|tr_cd값이 설정되지 않았습니다.";
		}
		/* -------------------------------------------------------------------------- */
		/* ::: 결과 처리                                                              */
		/* -------------------------------------------------------------------------- */

		r_cno              = easyPayClient.easypay_get_res( "cno"             );     //PG거래번호
		r_amount           = easyPayClient.easypay_get_res( "amount"          );     //총 결제금액
		r_order_no         = easyPayClient.easypay_get_res( "order_no"        );     //주문번호
		r_auth_no          = easyPayClient.easypay_get_res( "auth_no"         );     //승인번호
		r_tran_date        = easyPayClient.easypay_get_res( "tran_date"       );     //승인일시
		r_escrow_yn        = easyPayClient.easypay_get_res( "escrow_yn"       );     //에스크로 사용유무
		r_complex_yn       = easyPayClient.easypay_get_res( "complex_yn"      );     //복합결제 유무
		r_stat_cd          = easyPayClient.easypay_get_res( "stat_cd"         );     //상태코드
		r_stat_msg         = easyPayClient.easypay_get_res( "stat_msg"        );     //상태메시지
		r_pay_type         = easyPayClient.easypay_get_res( "pay_type"        );     //결제수단
		r_mall_id          = easyPayClient.easypay_get_res( "mall_id"         );     //가맹점 Mall ID
		r_card_no          = easyPayClient.easypay_get_res( "card_no"         );     //카드번호
		r_issuer_cd        = easyPayClient.easypay_get_res( "issuer_cd"       );     //발급사코드
		r_issuer_nm        = easyPayClient.easypay_get_res( "issuer_nm"       );     //발급사명
		r_acquirer_cd      = easyPayClient.easypay_get_res( "acquirer_cd"     );     //매입사코드
		r_acquirer_nm      = easyPayClient.easypay_get_res( "acquirer_nm"     );     //매입사명
		r_install_period   = easyPayClient.easypay_get_res( "install_period"  );     //할부개월
		r_noint            = easyPayClient.easypay_get_res( "noint"           );     //무이자여부
		r_part_cancel_yn   = easyPayClient.easypay_get_res( "part_cancel_yn"  );     //부분취소 가능여부
		r_card_gubun       = easyPayClient.easypay_get_res( "card_gubun"      );     //신용카드 종류
		r_card_biz_gubun   = easyPayClient.easypay_get_res( "card_biz_gubun"  );     //신용카드 구분
		r_cpon_flag        = easyPayClient.easypay_get_res( "cpon_flag"       );     //쿠폰사용유무
		r_bank_cd          = easyPayClient.easypay_get_res( "bank_cd"         );     //은행코드
		r_bank_nm          = easyPayClient.easypay_get_res( "bank_nm"         );     //은행명
		r_account_no       = easyPayClient.easypay_get_res( "account_no"      );     //계좌번호
		r_deposit_nm       = easyPayClient.easypay_get_res( "deposit_nm"      );     //입금자명
		r_expire_date      = easyPayClient.easypay_get_res( "expire_date"     );     //계좌사용만료일
		r_cash_res_cd      = easyPayClient.easypay_get_res( "cash_res_cd"     );     //현금영수증 결과코드
		r_cash_res_msg     = easyPayClient.easypay_get_res( "cash_res_msg"    );     //현금영수증 결과메세지
		r_cash_auth_no     = easyPayClient.easypay_get_res( "cash_auth_no"    );     //현금영수증 승인번호
		r_cash_tran_date   = easyPayClient.easypay_get_res( "cash_tran_date"  );     //현금영수증 승인일시
		r_cash_issue_type  = easyPayClient.easypay_get_res( "cash_issue_type" );     //현금영수증발행용도
		r_cash_auth_type   = easyPayClient.easypay_get_res( "cash_auth_type"  );     //인증구분
		r_cash_auth_value  = easyPayClient.easypay_get_res( "cash_auth_value" );     //인증번호
		r_auth_id          = easyPayClient.easypay_get_res( "auth_id"         );     //PhoneID
		r_billid           = easyPayClient.easypay_get_res( "billid"          );     //인증번호
		r_mobile_no        = easyPayClient.easypay_get_res( "mobile_no"       );     //휴대폰번호
		r_mob_ansim_yn     = easyPayClient.easypay_get_res( "mob_ansim_yn"    );     //안심결제 사용유무
		r_ars_no           = easyPayClient.easypay_get_res( "ars_no"          );     //전화번호
		r_cp_cd            = easyPayClient.easypay_get_res( "cp_cd"           );     //포인트사/쿠폰사
		r_cpon_auth_no     = easyPayClient.easypay_get_res( "cpon_auth_no"    );     //쿠폰승인번호
		r_cpon_tran_date   = easyPayClient.easypay_get_res( "cpon_tran_date"  );     //쿠폰승인일시
		r_cpon_no          = easyPayClient.easypay_get_res( "cpon_no"         );     //쿠폰번호
		r_remain_cpon      = easyPayClient.easypay_get_res( "remain_cpon"     );     //쿠폰잔액
		r_used_cpon        = easyPayClient.easypay_get_res( "used_cpon"       );     //쿠폰 사용금액
		r_rem_amt          = easyPayClient.easypay_get_res( "rem_amt"         );     //잔액
		r_bk_pay_yn        = easyPayClient.easypay_get_res( "bk_pay_yn"       );     //장바구니 결제여부
		r_canc_acq_date    = easyPayClient.easypay_get_res( "canc_acq_date"   );     //매입취소일시
		r_canc_date        = easyPayClient.easypay_get_res( "canc_date"       );     //취소일시
		r_refund_date      = easyPayClient.easypay_get_res( "refund_date"     );     //환불예정일시

		boolean isSuccess = false;

		if(("0000").equals(res_cd)) {
			isSuccess = true;
		}
		System.out.println("전체취소 테스트입니당 : "+res_msg);
		return isSuccess;
	}

	@Override
	public String getPayType(String payType) {
		if ("card".equals(payType)) {
			return "11";
		} else if ("vbank".equals(payType)) {
			return "22";
		} else if ("realtimebank".equals(payType)) {
			return "21";
		} else if ("hp".equals(payType)) {
			return "31";
		} else if ("11".equals(payType)) {
			return "card";
		} else if ("22".equals(payType)) {
			return "vbank";
		} else if ("21".equals(payType)) {
			return "realtimebank";
		} else if ("31".equals(payType)) {
			return "hp";
		}

		return "";
	}

	@Override
	public String confirmationOfPayment(PgData pgData) {

		return null;
	}

	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) {
		/* -------------------------------------------------------------------------- */
		/* ::: 처리구분 설정                                                          */
		/* -------------------------------------------------------------------------- */
		final String TRAN_CD_NOR_MGR        = "00201000";   // 변경(일반, 에스크로)
		/* -------------------------------------------------------------------------- */
		/* ::: 지불정보 설정                                                          */
		/* -------------------------------------------------------------------------- */
		final String GW_URL                 =  environment.getProperty("pg.easypay.g.conf.gw.url");  // Gateway URL ( test )
		//final String GW_URL               = "gw.easypay.co.kr";      // Gateway URL ( real )
		final String GW_PORT                = environment.getProperty("pg.easypay.g.conf.gw.port");  // 포트번호(변경불가)

		/* -------------------------------------------------------------------------- */
		/* ::: 지불 데이터 셋업 (업체에 맞게 수정)                                    */
		/* -------------------------------------------------------------------------- */
		/* ※ 주의 ※                                                                 */
		/* cert_file 변수 설정                                                        */
		/* - pg_cert.pem 파일이 있는 디렉토리의  절대 경로 설정                       */
		/* log_dir 변수 설정                                                          */
		/* - log 디렉토리 설정                                                        */
		/* log_level 변수 설정                                                        */
		/* - log 레벨 설정 (1 to 99(높을수록 상세))                                   */
		/* -------------------------------------------------------------------------- */
		final String CERT_FILE              = environment.getProperty("pg.easypay.g.conf.key.dir");
		final String LOG_DIR                = environment.getProperty("pg.easypay.g.conf.key.log");
		final int LOG_LEVEL                 = Integer.parseInt(environment.getProperty("pg.easypay.g.conf.log.level"));

		/* -------------------------------------------------------------------------- */
		/* ::: 승인요청 정보 설정                                               			      */
		/* -------------------------------------------------------------------------- */
		//[헤더]
		String tr_cd            = StringUtils.null2void(TRAN_CD_NOR_MGR);           									// [필수]요청구분
		String order_no         = StringUtils.null2void(orderPgData.getOrderCode());        							// [필수]주문번호
		String mall_id          = StringUtils.null2void(orderPgData.getPgServiceMid());     							// [필수]몰아이디

		/* -------------------------------------------------------------------------- */
		/* ::: 변경관리 정보 설정                                                     		  */
		/* -------------------------------------------------------------------------- */
		String mgr_txtype       = "";         							 												// [필수]거래구분 (20 : 매입요청, 31 : 부분매입취소, 32 : 부분승인취소, 33 : 계좌이체부분취소, 40 : 즉시취소, 60 : 환불, 62 : 부분환불)
		// 20,31,32는 카드결제, 33은 실시간계좌이체, 60,62는 가상계좌의 경우일 때. 40은 공통
		String mgr_subtype      = StringUtils.null2void("RF01");        						 						// [선택]변경세부구분	(환불시 필수)
		String org_cno          = StringUtils.null2void(orderPgData.getPgKey());            	 						// [필수]원거래고유번호
		String mgr_amt          = StringUtils.null2void("");            						 						// [선택]부분취소/환불요청 금액
		String mgr_rem_amt      = StringUtils.null2void("");        							 						// [선택]부분취소 잔액
		String mgr_bank_cd 		= StringUtils.null2void("");        							 						// [선택]환불계좌 은행코드
		String mgr_account 		= StringUtils.null2void("");        							 						// [선택]환불계좌 번호
		String mgr_depositor 	= StringUtils.null2void("");        							 						// [선택]환불계좌 예금주명

		// 환불 계좌정보가 있을 경우
		if (StringUtils.isNotEmpty((orderPgData.getReturnAccountNo()))) {
			mgr_bank_cd      = StringUtils.null2void(ShopUtils.getBankCode("EASYPAY", orderPgData.getReturnBankName()));        // [선택]환불계좌 은행코드
			mgr_account      = StringUtils.null2void(orderPgData.getReturnAccountNo());       						 // [선택]환불계좌 번호
			mgr_depositor    = StringUtils.null2void(orderPgData.getReturnName());      		 					 // [선택]환불계좌 예금주명
		}
		/* -------------------------------------------------------------------------- */
		/* ::: 전문                                                                  		  */
		/* -------------------------------------------------------------------------- */
		String mgr_data    = "";     // 변경정보
		String mall_data   = "";     // 요청전문

		/* -------------------------------------------------------------------------- */
		/* ::: 결제 결과                                                           			  */
		/* -------------------------------------------------------------------------- */
		String bDBProc              = "";     //가맹점DB처리성공여부
		String res_cd               = "";     //응답코드
		String res_msg              = "";     //응답메시지
		String r_cno                = "";     //PG거래번호
		String r_amount             = "";     //총 결제금액
		String r_order_no           = "";     //주문번호
		String r_auth_no            = "";     //승인번호
		String r_tran_date          = "";     //승인일시
		String r_escrow_yn          = "";     //에스크로 사용유무
		String r_complex_yn         = "";     //복합결제 유무
		String r_stat_cd            = "";     //상태코드
		String r_stat_msg           = "";     //상태메시지
		String r_pay_type           = "";     //결제수단
		String r_mall_id            = "";     //가맹점 Mall ID
		String r_card_no            = "";     //카드번호
		String r_issuer_cd          = "";     //발급사코드
		String r_issuer_nm          = "";     //발급사명
		String r_acquirer_cd        = "";     //매입사코드
		String r_acquirer_nm        = "";     //매입사명
		String r_install_period     = "";     //할부개월
		String r_noint              = "";     //무이자여부
		String r_part_cancel_yn     = "";     //부분취소 가능여부
		String r_card_gubun         = "";     //신용카드 종류
		String r_card_biz_gubun     = "";     //신용카드 구분
		String r_cpon_flag          = "";     //쿠폰사용유무
		String r_bank_cd            = "";     //은행코드
		String r_bank_nm            = "";     //은행명
		String r_account_no         = "";     //계좌번호
		String r_deposit_nm         = "";     //입금자명
		String r_expire_date        = "";     //계좌사용만료일
		String r_cash_res_cd        = "";     //현금영수증 결과코드
		String r_cash_res_msg       = "";     //현금영수증 결과메세지
		String r_cash_auth_no       = "";     //현금영수증 승인번호
		String r_cash_tran_date     = "";     //현금영수증 승인일시
		String r_cash_issue_type    = "";     //현금영수증발행용도
		String r_cash_auth_type     = "";     //인증구분
		String r_cash_auth_value    = "";     //인증번호
		String r_auth_id            = "";     //PhoneID
		String r_billid             = "";     //인증번호
		String r_mobile_no          = "";     //휴대폰번호
		String r_mob_ansim_yn       = "";     //안심결제 사용유무
		String r_ars_no             = "";     //전화번호
		String r_cp_cd              = "";     //포인트사/쿠폰사
		String r_cpon_auth_no       = "";     //쿠폰승인번호
		String r_cpon_tran_date     = "";     //쿠폰승인일시
		String r_cpon_no            = "";     //쿠폰번호
		String r_remain_cpon        = "";     //쿠폰잔액
		String r_used_cpon          = "";     //쿠폰 사용금액
		String r_rem_amt            = "";     //잔액
		String r_bk_pay_yn          = "";     //장바구니 결제여부
		String r_canc_acq_date      = "";     //매입취소일시
		String r_canc_date          = "";     //취소일시
		String r_refund_date        = "";     //환불예정일시

		/* -------------------------------------------------------------------------- */
		/* ::: EasyPayClient 인스턴스 생성 [변경불가 !!].                             */
		/* -------------------------------------------------------------------------- */
		EasyPayClient easyPayClient = new EasyPayClient();
		easyPayClient.easypay_setenv_init( GW_URL, GW_PORT, CERT_FILE, LOG_DIR, LOG_LEVEL );
		easyPayClient.easypay_reqdata_init();

		int easypay_mgr_data_item;
		easypay_mgr_data_item = easyPayClient.easypay_item( "mgr_data" );

		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_txtype"    , mgr_txtype    );          				// [필수]거래구분
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_subtype"   , mgr_subtype   );          				// [선택]변경세부구분
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "org_cno"       , org_cno       );          				// [필수]원거래고유번호
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_amt"       , mgr_amt       );          				// [선택]금액
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_rem_amt"   , mgr_rem_amt   );          				// [선택]부분취소 잔액
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_bank_cd"   , mgr_bank_cd   );          				// [선택]환불계좌 은행코드
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_account"   , mgr_account   );          				// [선택]환불계좌 번호
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "mgr_depositor" , mgr_depositor );          				// [선택]환불계좌 예금주명
		easyPayClient.easypay_deli_us( easypay_mgr_data_item, "req_ip"        , saleson.common.utils.CommonUtils.getClientIp());	// [필수]요청자 IP

		/* -------------------------------------------------------------------------- */
		/* ::: 실행                                                                   */
		/* -------------------------------------------------------------------------- */
		if ( tr_cd.length() > 0 ) {
			easyPayClient.easypay_run( mall_id, tr_cd, order_no );

			res_cd = easyPayClient.res_cd;
			res_msg = easyPayClient.res_msg;
		}
		else {
			res_cd  = "M114";
			res_msg = "연동 오류|tr_cd값이 설정되지 않았습니다.";
		}
		/* -------------------------------------------------------------------------- */
		/* ::: 결과 처리                                                              */
		/* -------------------------------------------------------------------------- */

		r_cno              = easyPayClient.easypay_get_res( "cno"             );     //PG거래번호
		r_amount           = easyPayClient.easypay_get_res( "amount"          );     //총 결제금액
		r_order_no         = easyPayClient.easypay_get_res( "order_no"        );     //주문번호
		r_auth_no          = easyPayClient.easypay_get_res( "auth_no"         );     //승인번호
		r_tran_date        = easyPayClient.easypay_get_res( "tran_date"       );     //승인일시
		r_escrow_yn        = easyPayClient.easypay_get_res( "escrow_yn"       );     //에스크로 사용유무
		r_complex_yn       = easyPayClient.easypay_get_res( "complex_yn"      );     //복합결제 유무
		r_stat_cd          = easyPayClient.easypay_get_res( "stat_cd"         );     //상태코드
		r_stat_msg         = easyPayClient.easypay_get_res( "stat_msg"        );     //상태메시지
		r_pay_type         = easyPayClient.easypay_get_res( "pay_type"        );     //결제수단
		r_mall_id          = easyPayClient.easypay_get_res( "mall_id"         );     //가맹점 Mall ID
		r_card_no          = easyPayClient.easypay_get_res( "card_no"         );     //카드번호
		r_issuer_cd        = easyPayClient.easypay_get_res( "issuer_cd"       );     //발급사코드
		r_issuer_nm        = easyPayClient.easypay_get_res( "issuer_nm"       );     //발급사명
		r_acquirer_cd      = easyPayClient.easypay_get_res( "acquirer_cd"     );     //매입사코드
		r_acquirer_nm      = easyPayClient.easypay_get_res( "acquirer_nm"     );     //매입사명
		r_install_period   = easyPayClient.easypay_get_res( "install_period"  );     //할부개월
		r_noint            = easyPayClient.easypay_get_res( "noint"           );     //무이자여부
		r_part_cancel_yn   = easyPayClient.easypay_get_res( "part_cancel_yn"  );     //부분취소 가능여부
		r_card_gubun       = easyPayClient.easypay_get_res( "card_gubun"      );     //신용카드 종류
		r_card_biz_gubun   = easyPayClient.easypay_get_res( "card_biz_gubun"  );     //신용카드 구분
		r_cpon_flag        = easyPayClient.easypay_get_res( "cpon_flag"       );     //쿠폰사용유무
		r_bank_cd          = easyPayClient.easypay_get_res( "bank_cd"         );     //은행코드
		r_bank_nm          = easyPayClient.easypay_get_res( "bank_nm"         );     //은행명
		r_account_no       = easyPayClient.easypay_get_res( "account_no"      );     //계좌번호
		r_deposit_nm       = easyPayClient.easypay_get_res( "deposit_nm"      );     //입금자명
		r_expire_date      = easyPayClient.easypay_get_res( "expire_date"     );     //계좌사용만료일
		r_cash_res_cd      = easyPayClient.easypay_get_res( "cash_res_cd"     );     //현금영수증 결과코드
		r_cash_res_msg     = easyPayClient.easypay_get_res( "cash_res_msg"    );     //현금영수증 결과메세지
		r_cash_auth_no     = easyPayClient.easypay_get_res( "cash_auth_no"    );     //현금영수증 승인번호
		r_cash_tran_date   = easyPayClient.easypay_get_res( "cash_tran_date"  );     //현금영수증 승인일시
		r_cash_issue_type  = easyPayClient.easypay_get_res( "cash_issue_type" );     //현금영수증발행용도
		r_cash_auth_type   = easyPayClient.easypay_get_res( "cash_auth_type"  );     //인증구분
		r_cash_auth_value  = easyPayClient.easypay_get_res( "cash_auth_value" );     //인증번호
		r_auth_id          = easyPayClient.easypay_get_res( "auth_id"         );     //PhoneID
		r_billid           = easyPayClient.easypay_get_res( "billid"          );     //인증번호
		r_mobile_no        = easyPayClient.easypay_get_res( "mobile_no"       );     //휴대폰번호
		r_mob_ansim_yn     = easyPayClient.easypay_get_res( "mob_ansim_yn"    );     //안심결제 사용유무
		r_ars_no           = easyPayClient.easypay_get_res( "ars_no"          );     //전화번호
		r_cp_cd            = easyPayClient.easypay_get_res( "cp_cd"           );     //포인트사/쿠폰사
		r_cpon_auth_no     = easyPayClient.easypay_get_res( "cpon_auth_no"    );     //쿠폰승인번호
		r_cpon_tran_date   = easyPayClient.easypay_get_res( "cpon_tran_date"  );     //쿠폰승인일시
		r_cpon_no          = easyPayClient.easypay_get_res( "cpon_no"         );     //쿠폰번호
		r_remain_cpon      = easyPayClient.easypay_get_res( "remain_cpon"     );     //쿠폰잔액
		r_used_cpon        = easyPayClient.easypay_get_res( "used_cpon"       );     //쿠폰 사용금액
		r_rem_amt          = easyPayClient.easypay_get_res( "rem_amt"         );     //잔액
		r_bk_pay_yn        = easyPayClient.easypay_get_res( "bk_pay_yn"       );     //장바구니 결제여부
		r_canc_acq_date    = easyPayClient.easypay_get_res( "canc_acq_date"   );     //매입취소일시
		r_canc_date        = easyPayClient.easypay_get_res( "canc_date"       );     //취소일시
		r_refund_date      = easyPayClient.easypay_get_res( "refund_date"     );     //환불예정일시

		boolean isSuccess = false;

		if(("0000").equals(res_cd)) {

			isSuccess = true;

			// CJH 2016.12.07 이니시스 부분취소시 TID를 새로 발급해주지만 취소 요청을 해당 거래번호로 할수가 없다... 무슨용도지?? 알수없음....
			// 이니시스는 원거래 TID말고도 입금,취소,배송등록,구매확인등등의 과정을 수행할때마다 각각의 TID를 생성하고 해당 TID는 수행한 액션에 대한 정보를 가지고 있음.
			// 부분취소시 발급되는 TID는 취소 TID로 해당 부분취소시 필요정보들을 가지고있음(관리자 사이트에서 확인가능). 부분취소후 나머지 부분에 대한 취소는 원거래 TID를 대상으로 해야함.
			// orderPgData.setPgKey(inipay.GetResult("TID"));
			orderPgData.setPgKey(orderPgData.getPgKey());

			if ("card".equals(orderPgData.getPgPaymentType().toLowerCase())) {
				orderPgData.setPartCancelFlag(r_part_cancel_yn);
			} else if ("vbank".equals(orderPgData.getPgPaymentType().toLowerCase())) {
				orderPgData.setPartCancelFlag("Y");
			} else if ("realtimebank".equals(orderPgData.getPgPaymentType().toLowerCase())) {
				orderPgData.setPartCancelFlag("Y");
			}

			orderPgData.setPgAmount(orderPgData.getRemainAmount());
			orderPgData.setPgProcInfo("");
			orderPgData.setPartCancelDetail("PART_CANCEL");
		}
		System.out.println("부분취소 테스트임미당 : " + res_msg);
		orderPgData.setSuccess(isSuccess);
		return orderPgData;
	}

	@Override
	public boolean delivery(HashMap<String, Object> paramMap) {

		return false;
	}

	public String getDeliveryCode(String deliveryName){

		String code = "";

		switch (deliveryName) {
			case "CJ대한통운":
				code = "cjgls";
				break;
			case "한진택배":
				code = "hanjin";
				break;
			case "우체국택배":
				code = "EPOST";
				break;

			default:
				break;
		}

		return code;
	}

	@Override
	public boolean escrowConfirmPurchase(HttpServletRequest request) {

		boolean isSuccess = false;

		/**************************************
		 * 1. INIpay 클래스의 인스턴스 생성 *
		 **************************************/
		INIescrow iniescrow = new INIescrow();

		/*********************
		 * 2. 지불 정보 설정 *
		 *********************/
		iniescrow.SetField("inipayhome",  environment.getProperty("pg.inipay.home"));						 // 이니페이 홈디렉터리(상점수정 필요)
		iniescrow.SetField("tid", request.getParameter("tid"));                    								 // 거래아이디
		iniescrow.SetField("mid", request.getParameter("mid"));                        							 //상점아이디
		iniescrow.SetField("admin", environment.getProperty("pg.inipay.escrow.keypass")); 					 // 키패스워드(상점아이디에 따라 변경)

		//***********************************************************************************************************
		//* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
		//* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
		//* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
		//* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
		//***********************************************************************************************************
		iniescrow.SetField("type", "escrow"); 				                         // 고정 (절대 수정 불가)
		iniescrow.SetField("escrowtype", "confirm"); 				                 // 고정 (절대 수정 불가)
		iniescrow.SetField("uip", saleson.common.utils.CommonUtils.getClientIp(request));                           // 고정
		iniescrow.SetField("debug","true");                                          // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
		iniescrow.SetField("crypto", "execure");								     // Extrus 암호화모듈 사용(고정)

		iniescrow.SetField("encrypted",request.getParameter("encrypted"));
		iniescrow.SetField("sessionkey",request.getParameter("sessionkey"));

		/*********************
		 * 3. 배송 등록 요청 *
		 *********************/
		iniescrow.startAction();

		/**********************
		 * 4. 배송 등록  결과 *
		 **********************/
		String tid          = iniescrow.GetResult("tid"); 					// 거래번호
		String resultCode   = iniescrow.GetResult("ResultCode");		// 결과코드 ("00"이면 지불 성공)
		String resultMsg    = iniescrow.GetResult("ResultMsg");    // 결과내용 (결과에 대한 설명)
		String resultDate   = iniescrow.GetResult("CNF_Date");    // 처리 날짜
		String resultTime   = iniescrow.GetResult("CNF_Time");    // 처리 시각

		if(resultDate == null || resultDate.trim().length() <= 0 )
		{
			resultDate   = iniescrow.GetResult("DNY_Date");    // 처리 날짜
			resultTime   = iniescrow.GetResult("DNY_Time");    // 처리 시각
		}

		if(resultCode.equals("00"))
			isSuccess = true;

		return isSuccess;
	}

	@Override
	public boolean escrowDenyConfirm(List<String> param) {

		boolean isSuccess = false;

		//		try {
		//			request.setCharacterEncoding("euc-kr");
		//		} catch (UnsupportedEncodingException e) {
		//			log.error("ERROR: {}", e.getMessage(), e);
		//		}
		/**************************************
		 * 1. INIpay 클래스의 인스턴스 생성 *
		 **************************************/
		INIescrow iniescrow = new INIescrow();

		/*********************
		 * 2. 지불 정보 설정 *
		 *********************/
		iniescrow.SetField("inipayhome", environment.getProperty("pg.inipay.home"));				// 이니페이 홈디렉터리(상점수정 필요)
		iniescrow.SetField("tid",param.get(0));                  // 거래아이디
		iniescrow.SetField("mid",param.get(1));                  // 상점아이디
		iniescrow.SetField("admin","1111");                                     // 키패스워드(상점아이디에 따라 변경)
		//***********************************************************************************************************
		//* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
		//* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
		//* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
		//* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
		//***********************************************************************************************************
		iniescrow.SetField("type", "escrow"); 				                    // 고정 (절대 수정 불가)
		iniescrow.SetField("escrowtype", "dcnf"); 				                // 고정 (절대 수정 불가)
		iniescrow.SetField("dcnf_name",param.get(2));      // 거절확인자
		iniescrow.SetField("debug","true");                                     // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
		iniescrow.SetField("crypto","execure");								    // Extrus 암호화모듈 사용(고정)

		/*********************
		 * 3. 배송 등록 요청 *
		 *********************/
		iniescrow.startAction();


		/**********************
		 * 4. 배송 등록  결과 *
		 **********************/

		String tid          = iniescrow.GetResult("tid"); 			// 거래번호
		String resultCode   = iniescrow.GetResult("ResultCode");	// 결과코드 ("00"이면 지불 성공)
		String resultMsg    = iniescrow.GetResult("ResultMsg");    // 결과내용 (지불결과에 대한 설명)
		String resultDate   = iniescrow.GetResult("DCNF_Date");    // 처리 날짜
		String resultTime   = iniescrow.GetResult("DCNF_Time");    // 처리 시각

		if(resultCode.equals("00"))
			isSuccess = true;

		return isSuccess;
	}

    @Override
    public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam) {
        return null;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {
        return null;
    }
}

/*	택배사 코드
 
		택배사					:				택배사 코드
		동원로엑스택배			:				ajutb
		옐로우캡					:				yellow		                        
		로젠택배					:				kgb
		KG로지스					:				dongbu
		우체국택배				:				EPOST
		우편등기					:				registpost
		한진택배					:				hanjin
		현대택배					:				hyundai
		사가와익스프레스택배		:				Sagawa
		KGB택배					:				kgbls
		하나로택배				:				Hanaro
		세덱스택배				:				sedex
		네덱스택배				:				nedex
		이노지스택배				:				innogis
		CJ대한통운				:				cjgls
		기타택배					:				9999

*/