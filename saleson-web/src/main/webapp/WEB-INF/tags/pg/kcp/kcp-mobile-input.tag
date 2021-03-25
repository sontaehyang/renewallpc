<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
	<input type="hidden" name="action"      >
	
  <!-- 공통정보 -->
  <input type="hidden" name="req_tx"          value="pay">                           <!-- 요청 구분 -->
  <input type="hidden" name="shop_name"       value="${op:property('pg.kcp.g.conf.site.name') }">       <!-- 사이트 이름 -->
  <input type="hidden" name="site_cd"         value="${op:property('pg.kcp.g.conf.site.cd') }">       <!-- 사이트 키 -->
  <input type="hidden" name="currency"        value="410"/>                          <!-- 통화 코드 -->
  <input type="hidden" name="eng_flag"        value="N"/>                            <!-- 한 / 영 -->
  <!-- 결제등록 키 -->
  <input type="hidden" name="approval_key"    >
  <!-- 인증시 필요한 파라미터(변경불가)-->
  <input type="hidden" name="escw_used"       value="N">
  <input type="hidden" name="pay_method"      value="">
  <input type="hidden" name="van_code"        value="">
  <!-- 신용카드 설정 -->
  <input type="hidden" name="quotaopt"        value="${op:property('pg.kcp.quotaopt') }"/>                           <!-- 최대 할부개월수 -->
  <!-- 가상계좌 설정 -->
  <input type="hidden" name="ipgm_date"       value="">
  <!-- 가맹점에서 관리하는 고객 아이디 설정을 해야 합니다.(필수 설정) -->
  <input type="hidden" name="shop_user_id"    value="">
  <!-- 복지포인트 결제시 가맹점에 할당되어진 코드 값을 입력해야합니다.(필수 설정) -->
<!--   <input type="hidden" name="pt_memcorp_cd"   value=""/> -->
  <!-- 현금영수증 설정 -->
  <input type="hidden" name="disp_tax_yn"     value="${op:property('pg.autoCashReceipt')}">
  <!-- 리턴 URL (kcp와 통신후 결제를 요청할 수 있는 암호화 데이터를 전송 받을 가맹점의 주문페이지 URL) -->
  <input type="hidden" name="Ret_URL"         value="">
  <!-- 화면 크기조정 -->
  <input type="hidden" name="tablet_size"     value="1.0">

  <!-- 추가 파라미터 ( 가맹점에서 별도의 값전달시 param_opt 를 사용하여 값 전달 ) -->
  <input type="hidden" name="param_opt_1"     value="">
  <input type="hidden" name="param_opt_2"     value="">
  <input type="hidden" name="param_opt_3"     value="">

  <!-- 결제 정보 등록시 응답 타입 ( 필드가 없거나 값이 '' 일경우 TEXT, 값이 XML 또는 JSON 지원 -->
  <input type="hidden" name="response_type"  value="JSON">
  <input type="hidden" name="PayUrl"   >
  <input type="hidden" name="traceNo"  >
  
  <input type="hidden" name="ordr_idxx">
  <input type="hidden" name="good_name">
  <input type="hidden" name="good_mny">
  <input type="hidden" name="buyr_name">
  <input type="hidden" name="buyr_mail">
  <input type="hidden" name="buyr_tel1">
  <input type="hidden" name="buyr_tel2">
  
  <!-- 스마트폰 결제 응답 결과 -->
  <input type="hidden" name="res_cd">
  <input type="hidden" name="use_pay_method">
  <input type="hidden" name="res_msg">
  <input type="hidden" name="ActionResult">
  <input type="hidden" name="enc_info">
  <input type="hidden" name="enc_data">
  