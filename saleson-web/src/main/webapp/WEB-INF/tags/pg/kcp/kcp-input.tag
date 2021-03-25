<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<!-- 필수 사항 (변경불가)-->
<input type="hidden" name="site_name" value="${op:property('pg.kcp.g.conf.site.name') }">
<input type="hidden" name="site_midx" value="PGNW">
<input type="hidden" name="term_idxx" value="${op:property('pg.kcp.g.conf.site.cd') }V">
<input type="hidden" name="site_cd"   value="${op:property('pg.kcp.g.conf.site.cd') }">
<input type="hidden" name="ret_url"   value="">

<!-- KCP 관련 셋팅 -->
<input type="hidden" name="card_pay_method" value="">
<input type="hidden" name="pay_method" value="">
<input type="hidden" name="ordr_idxx"  value="">
<input type="hidden" name="good_name"  value="">
<input type="hidden" name="good_mny"   value="">
<input type="hidden" name="buyr_name"  value="">
<input type="hidden" name="buyr_mail"  value="">
<input type="hidden" name="buyr_tel1"  value="">
<input type="hidden" name="buyr_tel2"  value="">
<input type="hidden" name="req_tx"     value="">
<input type="hidden" name="currency"   value="${op:property('pg.kcp.currency')}">
<input type="hidden" name="quotaopt"   value="${op:property('pg.kcp.quotaopt')}">
<input type="hidden" name="quota"      value="00">
<input type="hidden" name="enc_type"   value="NO"               >
<input type="hidden" name="layer_overlay_view"  value="Y"> <!-- 고정 값(변경 불가) -->

<input type="hidden" name="res_cd"      value="">
<input type="hidden" name="res_msg"     value="">

<!-- ISP 셋팅 -->
<input type="hidden" name="KVP_QUOTA_INF"    value="00"> <!-- ISP 할부개월수 세팅 정보 -->
<input type="hidden" name="KVP_NOINT_INF"    value="NONE"/>
<input type="hidden" name="kvp_quota"        value="">
<input type="hidden" name="card_cd"          value="">
<input type="hidden" name="session_key"      value="">
<input type="hidden" name="enc_data"         value="">
<input type="hidden" name="kvp_pgid"         value="">
<input type="hidden" name="easy_pay_flg"     value=""> <!-- K모션 사용시 K 리턴-->
<input type="hidden" name="using_point"      value=""> <!-- K모션 사용시 K모션 내 포인트 사용 금액 리턴-->

<!-- ISP 레이어 삭제 -->
<input type="hidden"   name="kcp_layer_view" value="N"> 

<!-- V3D 관련 필수 사항 (변경불가)-->
<input type="hidden" name="expiry_yy"       value="79">
<input type="hidden" name="expiry_mm"       value="12">
<input type="hidden" name="cavv"            value="">
<input type="hidden" name="xid"             value="">
<input type="hidden" name="eci"             value="">
<input type="hidden" name="card_mony"       value="">
<input type="hidden" name="curr_code"       value="410">
<input type="hidden" name="card_no"         value="">
<input type="hidden" name="card_enc_no"     value="">
<input type="hidden" name="enc_cardno_yn"   value="Y">
<!-- 삼성카드사 안심클릭 변경으로 추가됨( 2007/11/07 )-->
<input type="hidden" name="inst_term"       value="${op:property('pg.kcp.quotaopt')}"> <!-- 할부개월 -->

<!-- 웹표준 플러그인 필요정보 -->
<input type="hidden" name="module_type"     value="${op:property('pg.kcp.module.type')}">
<input type="hidden" name="trace_no"        value="">
<input type="hidden" name="enc_info"        value="">
<input type="hidden" name="ret_pay_method"  value="">
<input type="hidden" name="tran_cd"         value="">    
<input type="hidden" name="bank_issu"       value="">
<input type="hidden" name="use_pay_method"  value="">
<!--  현금영수증 관련 정보 : Payplus Plugin 에서 설정하는 정보입니다 -->    
<input type="hidden" name="cash_yn"         value="">
<input type="hidden" name="disp_tax_yn"     value="${op:property('pg.autoCashReceipt')}">
<input type="hidden" name="cash_tr_code"    value="">
<input type="hidden" name="cash_id_info"    value="">
<!-- 가맹점에서 관리하는 고객 아이디 설정을 해야 합니다.(필수 설정) -->
<input type="hidden" name="shop_user_id"    value="">
<!-- 복지포인트 결제시 가맹점에 할당되어진 코드 값을 입력해야합니다.(필수 설정) -->
<input type="hidden" name="pt_memcorp_cd"   value="">

<input type="hidden" name="action">
<!-- 주문정보 검증 관련 정보 : Payplus Plugin 에서 설정하는 정보입니다 -->
<input type="hidden" name="ordr_chk"        value="">


<!-- ============================================== -->
<!-- 3-1. Payplus Plugin 에스크로결제 사용시 필수 정보 -->
<!--------------------------------------------------->
<!----- 결제에 필요한 주문 정보를 입력 및 설정합니다. ----->
<!--------------------------------------------------->

<!-- 에스크로 사용 여부 : 반드시 Y 로 설정 -->
<input type="hidden" name="escw_used"       value=""/>
<!-- 에스크로 결제처리 모드 : 에스크로: Y, 일반: N, KCP 설정 조건: O  -->
<input type="hidden" name="pay_mod"         value=""/>
<!-- 배송 소요일 : 예상 배송 소요일을 입력 -->
<input type="hidden"  name="deli_term" value="03"/>
<!-- 장바구니 상품 개수 : 장바구니에 담겨있는 상품의 개수를 입력(good_info의 seq값 참조) -->
<input type="hidden"  name="bask_cntx" value=""/>
<!-- 장바구니 상품 상세 정보 (자바 스크립트 샘플 create_goodInfo()가 온로드 이벤트시 설정되는 부분입니다.) -->
<input type="hidden" name="good_info"       value=""/>

<!-------------------------------------------------------->
<!-- 3-1. Payplus Plugin 에스크로결제 사용시 필수 정보  END -->
<!-- ================================================== -->
<input type="hidden" name="rcvr_name"       value=""/>		<!-- 수취인명 -->
<input type="hidden" name="rcvr_tel1"       value=""/>		<!-- 수취인 전화번호 -->
<input type="hidden" name="rcvr_tel2"       value=""/>		<!-- 수취인 휴대폰번호 -->
<input type="hidden" name="rcvr_mail"       value=""/>		<!-- 수취인 E-mail -->
<input type="hidden" name="rcvr_zipx"       value=""/>		<!-- 수취인 우편번호 -->
<input type="hidden" name="rcvr_add1"       value=""/>		<!-- 수취인 주소 -->
<input type="hidden" name="rcvr_add2"       value=""/>		<!-- 수취인 상세주소 -->
