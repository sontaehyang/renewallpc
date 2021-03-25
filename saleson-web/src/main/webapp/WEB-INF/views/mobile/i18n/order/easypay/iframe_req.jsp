<%@ page contentType="text/html; charset=euc-kr" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/* -------------------------------------------------------------------------- */
/* 캐쉬 사용안함                                                              */
/* -------------------------------------------------------------------------- */
response.setHeader("cache-control","no-cache");
response.setHeader("expires","-1");
response.setHeader("pragma","no-cache");

request.setCharacterEncoding("euc-kr");
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="robots" content="noindex, nofollow" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<script>
    window.onload = function()
    {
        document.frm.submit();
    }
</script>
<title>webpay 가맹점 test page</title>
</head>
<body>
    <!-- KICC 서버로 인증요청 -->
    
    <!-- TEST -->
    <form name="frm" method="post" action="http://testsp.easypay.co.kr/ep8/MainAction.do" >
    <!-- REAL -->
    <!--form name="frm" method="post" action="https://sp.easypay.co.kr/ep8/MainAction.do" -->
    
            
        <!--공통-->
        <input type="hidden" id="sp_mall_id"           name="sp_mall_id"           value="<%=request.getParameter("sp_mall_id")           %>" /> <!--[필수]가맹점ID -->
        <input type="hidden" id="sp_mall_nm"           name="sp_mall_nm"           value="<%=request.getParameter("sp_mall_nm")           %>" /> <!--[선택]가맹점명 -->
        <input type="hidden" id="sp_order_no"          name="sp_order_no"          value="<%=request.getParameter("sp_order_no")          %>" /> <!--[필수]가맹점 주문번호(인증응답) -->
        <input type="hidden" id="sp_pay_type"          name="sp_pay_type"          value="<%=request.getParameter("sp_pay_type")          %>" /> <!--[필수]결제수단 -->
        <input type="hidden" id="sp_cert_type"         name="sp_cert_type"         value="<%=request.getParameter("sp_cert_type")         %>" /> <!--[선택]인증타입 -->  
        <input type="hidden" id="sp_currency"          name="sp_currency"          value="<%=request.getParameter("sp_currency")          %>" /> <!--[필수]통화코드(수정불가) -->        
        <input type="hidden" id="sp_product_nm"        name="sp_product_nm"        value="<%=request.getParameter("sp_product_nm")        %>" /> <!--[필수]상품명 -->
        <input type="hidden" id="sp_product_amt"       name="sp_product_amt"       value="<%=request.getParameter("sp_product_amt")       %>" /> <!--[필수]상품금액 m-->
        <input type="hidden" id="sp_return_url"        name="sp_return_url"        value="<%=request.getParameter("sp_return_url")        %>" /> <!--[필수]가맹점 return URL -->
        <input type="hidden" id="sp_lang_flag"         name="sp_lang_flag"         value="<%=request.getParameter("sp_lang_flag")         %>" /> <!--[선택]언어 -->
        <input type="hidden" id="sp_charset"           name="sp_charset"           value="<%=request.getParameter("sp_charset")           %>" /> <!--[선택]가맹점 charset -->  
        <input type="hidden" id="sp_user_id"           name="sp_user_id"           value="<%=request.getParameter("sp_user_id")           %>" /> <!--[선택]가맹점 고객ID -->
        <input type="hidden" id="sp_memb_user_no"      name="sp_memb_user_no"      value="<%=request.getParameter("sp_memb_user_no")      %>" /> <!--[선택]가맹점 고객일련번호 -->
        <input type="hidden" id="sp_user_nm"           name="sp_user_nm"           value="<%=request.getParameter("sp_user_nm")           %>" /> <!--[선택]가맹점 고객명 -->
        <input type="hidden" id="sp_user_mail"         name="sp_user_mail"         value="<%=request.getParameter("sp_user_mail")         %>" /> <!--[선택]가맹점 고객 E-mail -->
        <input type="hidden" id="sp_user_phone1"       name="sp_user_phone1"       value="<%=request.getParameter("sp_user_phone1")       %>" /> <!--[선택]가맹점 고객 연락처1 -->
        <input type="hidden" id="sp_user_phone2"       name="sp_user_phone2"       value="<%=request.getParameter("sp_user_phone2")       %>" /> <!--[선택]가맹점 고객 연락처2 -->
        <input type="hidden" id="sp_user_addr"         name="sp_user_addr"         value="<%=request.getParameter("sp_user_addr")         %>" /> <!--[선택]가맹점 고객 주소 -->
        <input type="hidden" id="sp_user_define1"      name="sp_user_define1"      value="<%=request.getParameter("sp_user_define1")      %>" /> <!--[선택]가맹점 필드1 -->
        <input type="hidden" id="sp_user_define2"      name="sp_user_define2"      value="<%=request.getParameter("sp_user_define2")      %>" /> <!--[선택]가맹점 필드2 -->
        <input type="hidden" id="sp_user_define3"      name="sp_user_define3"      value="<%=request.getParameter("sp_user_define3")      %>" /> <!--[선택]가맹점 필드3 -->
        <input type="hidden" id="sp_user_define4"      name="sp_user_define4"      value="<%=request.getParameter("sp_user_define4")      %>" /> <!--[선택]가맹점 필드4 -->
        <input type="hidden" id="sp_user_define5"      name="sp_user_define5"      value="<%=request.getParameter("sp_user_define5")      %>" /> <!--[선택]가맹점 필드5 -->
        <input type="hidden" id="sp_user_define6"      name="sp_user_define6"      value="<%=request.getParameter("sp_user_define6")      %>" /> <!--[선택]가맹점 필드6 -->
        <input type="hidden" id="sp_mobilereserved1"   name="sp_mobilereserved1"   value="<%=request.getParameter("sp_mobilereserved1")   %>" /> <!--[선택]가맹점 여유필드1        -->    
        <input type="hidden" id="sp_mobilereserved2"   name="sp_mobilereserved2"   value="<%=request.getParameter("sp_mobilereserved2")   %>" /> <!--[선택]가맹점 여유필드2        -->    
        <input type="hidden" id="sp_reserved1"         name="sp_reserved1"         value="<%=request.getParameter("sp_reserved1")         %>" /> <!--[선택]가맹점 여유필드1        -->    
        <input type="hidden" id="sp_reserved2"         name="sp_reserved2"         value="<%=request.getParameter("sp_reserved2")         %>" /> <!--[선택]가맹점 여유필드2        -->    
        <input type="hidden" id="sp_reserved3"         name="sp_reserved3"         value="<%=request.getParameter("sp_reserved3")         %>" /> <!--[선택]가맹점 여유필드3        -->    
        <input type="hidden" id="sp_reserved4"         name="sp_reserved4"         value="<%=request.getParameter("sp_reserved4")         %>" /> <!--[선택]가맹점 여유필드4        -->            
        <input type="hidden" id="sp_product_type"      name="sp_product_type"      value="<%=request.getParameter("sp_product_type")      %>" /> <!--[선택]상품정보구분 -->
        <input type="hidden" id="sp_product_expr"      name="sp_product_expr"      value="<%=request.getParameter("sp_product_expr")      %>" /> <!--[선택]서비스 기간 -->
        <input type="hidden" id="sp_app_scheme"        name="sp_app_scheme"        value="<%=request.getParameter("sp_app_scheme")        %>" /> <!--[선택]가맹점 APP scheme -->  
        <input type="hidden" id="sp_window_type"       name="sp_window_type"       value="<%=request.getParameter("sp_window_type")       %>" /> <!--[선택]윈도우타입 -->
        <input type="hidden" id="sp_disp_cash_yn"      name="sp_disp_cash_yn"      value="<%=request.getParameter("sp_disp_cash_yn")      %>" /> <!--[선택]현금영수증 화면표시여부(Y/N)--> 
                                                                                   
        <!--신용카드-->                                                                           
        <input type="hidden" id="sp_usedcard_code"     name="sp_usedcard_code"     value="<%=request.getParameter("sp_usedcard_code")     %>" /> <!--[선택]사용가능카드 LIST -->
        <input type="hidden" id="sp_quota"             name="sp_quota"             value="<%=request.getParameter("sp_quota")             %>" /> <!--[선택]할부개월 -->
        <input type="hidden" id="sp_os_cert_flag"      name="sp_os_cert_flag"      value="<%=request.getParameter("sp_os_cert_flag")      %>" /> <!--[선택]해외안심클릭 사용여부-->
        <input type="hidden" id="sp_noinst_flag"       name="sp_noinst_flag"       value="<%=request.getParameter("sp_noinst_flag")       %>" /> <!--[선택]무이자 여부(Y/N) -->
        <input type="hidden" id="sp_noinst_term"       name="sp_noinst_term"       value="<%=request.getParameter("sp_noinst_term")       %>" /> <!--[선택]무이자 기간 -->
        <input type="hidden" id="sp_set_point_card_yn" name="sp_set_point_card_yn" value="<%=request.getParameter("sp_set_point_card_yn") %>" /> <!--[선택]카드사포인트 사용여부(Y/N) -->
        <input type="hidden" id="sp_point_card"        name="sp_point_card"        value="<%=request.getParameter("sp_point_card")        %>" /> <!--[선택]포인트카드 LIST(카드코드-더할 할부개월) -->
        <input type="hidden" id="sp_join_cd"           name="sp_join_cd"           value="<%=request.getParameter("sp_join_cd")           %>" /> <!--[선택]조인코드 -->
        <input type="hidden" id="sp_kmotion_useyn"     name="sp_kmotion_useyn"     value="<%=request.getParameter("sp_kmotion_useyn")     %>" /> <!--[선택]국민앱카드 사용유무 -->
                                                                                                                                                      
        <!--가상계좌-->
        <input type="hidden" id="sp_vacct_bank"       name="sp_vacct_bank"         value="<%=request.getParameter("sp_vacct_bank")        %>" /> <!--[선택]가상계좌 사용가능한 은행 LIST -->
        <input type="hidden" id="sp_vacct_end_date"   name="sp_vacct_end_date"     value="<%=request.getParameter("sp_vacct_end_date")    %>" /> <!--[선택]입금 만료 날짜 -->
        <input type="hidden" id="sp_vacct_end_time"   name="sp_vacct_end_time"     value="<%=request.getParameter("sp_vacct_end_time")    %>" /> <!--[선택]입금 만료 시간 -->
                   
        <!--선불카드-->
        <input type="hidden" id="sp_prepaid_cp"       name="sp_prepaid_cp"         value="<%=request.getParameter("sp_prepaid_cp")        %>" /> <!--[선택]선불카드 CP -->
             
     </form>
</body>
</html>
