<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<!--------------------------->
<!-- ::: 공통 인증 요청 값 -->
<!--------------------------->

<input type="hidden" id="sp_mall_id"        name="sp_mall_id"           value="">			<!-- 가맹점 ID-->
<input type="hidden" id="sp_mall_nm"        name="sp_mall_nm"           value="">			<!-- 가맹점명-->
<input type="hidden" id="sp_currency"       name="sp_currency"          value="00">       											<!-- 통화코드 // 00 : 원화-->
<input type="hidden" id="sp_return_url"     name="sp_return_url"        value="">         											<!-- 가맹점 CALLBACK URL // -->
<input type="hidden" id="sp_ci_url"         name="sp_ci_url"            value="">         											<!-- CI LOGO URL // -->
<input type="hidden" id="sp_lang_flag"      name="sp_lang_flag"         value="">         											<!-- 언어 // -->
<input type="hidden" id="sp_charset"        name="sp_charset"           value="UTF-8">   											<!-- 가맹점 CharSet // EUC-KR,UTF-8 사용시 대문자 이용-->
<input type="hidden" id="sp_user_id"        name="sp_user_id"           value="">         											<!-- 가맹점 고객ID // -->
<input type="hidden" id="sp_memb_user_no"   name="sp_memb_user_no"      value="">         											<!-- 가맹점 고객일련번호 // -->
<input type="hidden" id="sp_user_nm"        name="sp_user_nm"           value="">         											<!-- 가맹점 고객명 // -->
<input type="hidden" id="sp_user_mail"      name="sp_user_mail"         value="">         											<!-- 가맹점 고객 E-mail // -->
<input type="hidden" id="sp_user_phone1"    name="sp_user_phone1"       value="">         											<!-- 가맹점 고객 연락처1 // -->
<input type="hidden" id="sp_user_phone2"    name="sp_user_phone2"       value="">         											<!-- 가맹점 고객 연락처2 // -->
<input type="hidden" id="sp_user_addr"      name="sp_user_addr"         value="">         											<!-- 가맹점 고객 주소 // -->
<input type="hidden" id="sp_user_define1"   name="sp_user_define1"      value="">         											<!-- 가맹점 필드1 // -->
<input type="hidden" id="sp_user_define2"   name="sp_user_define2"      value="">         											<!-- 가맹점 필드2 // -->
<input type="hidden" id="sp_user_define3"   name="sp_user_define3"      value="">         											<!-- 가맹점 필드3 // -->
<input type="hidden" id="sp_user_define4"   name="sp_user_define4"      value="">         											<!-- 가맹점 필드4 // -->
<input type="hidden" id="sp_user_define5"   name="sp_user_define5"      value="">         											<!-- 가맹점 필드5 // -->
<input type="hidden" id="sp_user_define6"   name="sp_user_define6"      value="">         											<!-- 가맹점 필드6 // -->
<input type="hidden" id="sp_product_type"   name="sp_product_type"      value="">         											<!-- 상품정보구분 // -->
<input type="hidden" id="sp_product_expr"   name="sp_product_expr"      value="">         											<!-- 서비스 기간 // (YYYYMMDD) -->
<input type="hidden" id="sp_disp_cash_yn"   name="sp_disp_cash_yn"      value="">         											<!-- 현금영수증 화면표시여부 //미표시 : "N", 그외: DB조회 -->
<input type="hidden" id="sp_pay_type" 		name="sp_pay_type"			value="">		  											<!-- 결제수단 -->
<input type="hidden" id="sp_window_type" 	name="sp_window_type"		value="submit">	  											<!-- 윈도우 타입 -->
<input type="hidden" id="sp_cert_type" 		name="sp_cert_type"			value="">		  											<!-- 인증 타입 -->
<input type="hidden" id="sp_order_no" 		name="sp_order_no"			value="">		  											<!-- 가맹점 주문번호 -->
<input type="hidden" id="sp_product_nm" 	name="sp_product_nm"		value="">		  											<!-- 상품명 -->
<input type="hidden" id="sp_product_amt" 	name="sp_product_amt"		value="">		  											<!-- 상품금액 -->

<!--------------------------->
<!-- :: 카드 인증 요청 값 :: -->
<!--------------------------->

<input type="hidden" id="sp_usedcard_code"      name="sp_usedcard_code"     value="">      <!-- 사용가능한 카드 LIST // FORMAT->카드코드:카드코드: ... :카드코드 EXAMPLE->029:027:031 // 빈값 : DB조회-->
<input type="hidden" id="sp_quota"              name="sp_quota"             value="">      <!-- 할부개월 (카드코드-할부개월) -->
<input type="hidden" id="sp_os_cert_flag"       name="sp_os_cert_flag"      value="2">     <!-- 해외안심클릭 사용여부(변경불가) // -->
<input type="hidden" id="sp_noinst_flag"        name="sp_noinst_flag"       value="">      <!-- 무이자 여부 (Y/N) // -->
<input type="hidden" id="sp_noinst_term"        name="sp_noinst_term"       value="">      <!-- 무이자 기간 (카드코드-더할할부개월) // -->
<input type="hidden" id="sp_set_point_card_yn"  name="sp_set_point_card_yn" value="">      <!-- 카드사포인트 사용여부 (Y/N) // -->
<input type="hidden" id="sp_point_card"         name="sp_point_card"        value="">      <!-- 포인트카드 LIST  // -->
<input type="hidden" id="sp_join_cd"            name="sp_join_cd"           value="">      <!-- 조인코드 // -->
<input type="hidden" id="sp_kmotion_useyn"      name="sp_kmotion_useyn"     value="Y">     <!-- 국민앱카드 사용유무 (Y/N)// -->

<!------------------------------->
<!-- ::: 가상계좌 인증 요청 값 -->
<!------------------------------->

<input type="hidden" id="sp_vacct_bank"         name="sp_vacct_bank"        value="">      <!-- 가상계좌 사용가능한 은행 LIST // -->
<input type="hidden" id="sp_vacct_end_date"     name="sp_vacct_end_date"    value="">      <!-- 입금 만료 날짜 // -->
<input type="hidden" id="sp_vacct_end_time"     name="sp_vacct_end_time"    value="">      <!-- 입금 만료 시간 // -->

<!------------------------------->
<!-- ::: 선불카드 인증 요청 값 -->
<!------------------------------->

<input type="hidden" id="sp_prepaid_cp"         name="sp_prepaid_cp"        value="">      <!-- 선불카드 CP // FORMAT->코드:코드: ... :코드 EXAMPLE->CCB:ECB // 빈값 : DB조회-->

<!--------------------------------->
<!-- ::: 인증응답용 인증 요청 값 -->
<!--------------------------------->

<input type="hidden" id="sp_res_cd"             name="sp_res_cd"            value="">      <!--  응답코드 // -->
<input type="hidden" id="sp_res_msg"            name="sp_res_msg"           value="">      <!--  응답메세지 // -->
<input type="hidden" id="sp_tr_cd"              name="sp_tr_cd"             value="">      <!--  결제창 요청구분 // -->
<input type="hidden" id="sp_ret_pay_type"       name="sp_ret_pay_type"      value="">      <!--  결제수단 // -->
<input type="hidden" id="sp_ret_complex_yn"     name="sp_ret_complex_yn"    value="">      <!--  복합결제 여부 (Y/N) // -->
<input type="hidden" id="sp_card_code"          name="sp_card_code"         value="">      <!--  카드코드 (ISP:KVP카드코드 MPI:카드코드) // -->
<input type="hidden" id="sp_eci_code"           name="sp_eci_code"          value="">      <!--  MPI인 경우 ECI코드 // -->
<input type="hidden" id="sp_card_req_type"      name="sp_card_req_type"     value="">      <!--  거래구분 // -->
<input type="hidden" id="sp_save_useyn"         name="sp_save_useyn"        value="">      <!--  카드사 세이브 여부 (Y/N) // -->
<input type="hidden" id="sp_trace_no"           name="sp_trace_no"          value="">      <!--  추적번호 // -->
<input type="hidden" id="sp_sessionkey"         name="sp_sessionkey"        value="">      <!--  세션키 // -->
<input type="hidden" id="sp_encrypt_data"       name="sp_encrypt_data"      value="">      <!--  암호화전문 // -->
<input type="hidden" id="sp_spay_cp"            name="sp_spay_cp"           value="">      <!--  간편결제 CP 코드 // -->
<input type="hidden" id="sp_card_prefix"        name="sp_card_prefix"       value="">      <!--  신용카드prefix // -->
<input type="hidden" id="sp_card_no_7"          name="sp_card_no_7"         value="">      <!--  신용카드번호 앞7자리 // -->

<input type="hidden" id="orderCode"          name="orderCode"         value="">      <!--  상점 주문번호 -->