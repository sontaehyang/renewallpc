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
    <form name="frm" method="post" action="https://testpg.easypay.co.kr/webpay/MainAction.do"> <!-- 테스트 -->
    <!--form name="frm" method="post" action="https://pg.easypay.co.kr/webpay/MainAction.do"--> <!-- 운영 -->

        <input type="hidden" id="EP_mall_id"           name="EP_mall_id"            value="<%=request.getParameter("EP_mall_id") %>" />
        <input type="hidden" id="EP_mall_nm"           name="EP_mall_nm"            value="<%=request.getParameter("EP_mall_nm") %>" />
        <input type="hidden" id="EP_order_no"          name="EP_order_no"           value="<%=request.getParameter("EP_order_no") %>" />
        <input type="hidden" id="EP_pay_type"          name="EP_pay_type"           value="<%=request.getParameter("EP_pay_type") %>" />
        <input type="hidden" id="EP_currency"          name="EP_currency"           value="<%=request.getParameter("EP_currency") %>" />
        <input type="hidden" id="EP_product_nm"        name="EP_product_nm"         value="<%=request.getParameter("EP_product_nm") %>" />
        <input type="hidden" id="EP_product_amt"       name="EP_product_amt"        value="<%=request.getParameter("EP_product_amt") %>" />
        <input type="hidden" id="EP_return_url"        name="EP_return_url"         value="<%=request.getParameter("EP_return_url") %>" />
        <input type="hidden" id="EP_ci_url"            name="EP_ci_url"             value="<%=request.getParameter("EP_ci_url") %>" />
        <input type="hidden" id="EP_lang_flag"         name="EP_lang_flag"          value="<%=request.getParameter("EP_lang_flag") %>" />
        <input type="hidden" id="EP_charset"           name="EP_charset"            value="<%=request.getParameter("EP_charset") %>" />
        <input type="hidden" id="EP_user_id"           name="EP_user_id"            value="<%=request.getParameter("EP_user_id") %>" />
        <input type="hidden" id="EP_memb_user_no"      name="EP_memb_user_no"       value="<%=request.getParameter("EP_memb_user_no") %>" />
        <input type="hidden" id="EP_user_nm"           name="EP_user_nm"            value="<%=request.getParameter("EP_user_nm") %>" />
        <input type="hidden" id="EP_user_mail"         name="EP_user_mail"          value="<%=request.getParameter("EP_user_mail") %>" />
        <input type="hidden" id="EP_user_phone1"       name="EP_user_phone1"        value="<%=request.getParameter("EP_user_phone1") %>" />
        <input type="hidden" id="EP_user_phone2"       name="EP_user_phone2"        value="<%=request.getParameter("EP_user_phone2") %>" />
        <input type="hidden" id="EP_user_addr"         name="EP_user_addr"          value="<%=request.getParameter("EP_user_addr") %>" />
        <input type="hidden" id="EP_user_define1"      name="EP_user_define1"       value="<%=request.getParameter("EP_user_define1") %>" />
        <input type="hidden" id="EP_user_define2"      name="EP_user_define2"       value="<%=request.getParameter("EP_user_define2") %>" />
        <input type="hidden" id="EP_user_define3"      name="EP_user_define3"       value="<%=request.getParameter("EP_user_define3") %>" />
        <input type="hidden" id="EP_user_define4"      name="EP_user_define4"       value="<%=request.getParameter("EP_user_define4") %>" />
        <input type="hidden" id="EP_user_define5"      name="EP_user_define5"       value="<%=request.getParameter("EP_user_define5") %>" />
        <input type="hidden" id="EP_user_define6"      name="EP_user_define6"       value="<%=request.getParameter("EP_user_define6") %>" />
        <input type="hidden" id="EP_product_type"      name="EP_product_type"       value="<%=request.getParameter("EP_product_type") %>" />
        <input type="hidden" id="EP_product_expr"      name="EP_product_expr"       value="<%=request.getParameter("EP_product_expr") %>" />
        <input type="hidden" id="EP_usedcard_code"     name="EP_usedcard_code"      value="<%=request.getParameter("EP_usedcard_code") %>" />
        <input type="hidden" id="EP_quota"             name="EP_quota"              value="<%=request.getParameter("EP_quota") %>" />
        <input type="hidden" id="EP_os_cert_flag"      name="EP_os_cert_flag"       value="<%=request.getParameter("EP_os_cert_flag") %>" />
        <input type="hidden" id="EP_noinst_flag"       name="EP_noinst_flag"        value="<%=request.getParameter("EP_noinst_flag") %>" />
        <input type="hidden" id="EP_noinst_term"       name="EP_noinst_term"        value="<%=request.getParameter("EP_noinst_term") %>" />
        <input type="hidden" id="EP_set_point_card_yn" name="EP_set_point_card_yn"  value="<%=request.getParameter("EP_set_point_card_yn") %>" />
        <input type="hidden" id="EP_point_card"        name="EP_point_card"         value="<%=request.getParameter("EP_point_card") %>" />
        <input type="hidden" id="EP_join_cd"           name="EP_join_cd"            value="<%=request.getParameter("EP_join_cd") %>" />
        <input type="hidden" id="EP_kmotion_useyn"     name="EP_kmotion_useyn"      value="<%=request.getParameter("EP_kmotion_useyn") %>" />
        <input type="hidden" id="EP_vacct_bank"        name="EP_vacct_bank"         value="<%=request.getParameter("EP_vacct_bank") %>" />
        <input type="hidden" id="EP_vacct_end_date"    name="EP_vacct_end_date"     value="<%=request.getParameter("EP_vacct_end_date") %>" />
        <input type="hidden" id="EP_vacct_end_time"    name="EP_vacct_end_time"     value="<%=request.getParameter("EP_vacct_end_time") %>" />
        <input type="hidden" id="EP_prepaid_cp"        name="EP_prepaid_cp"         value="<%=request.getParameter("EP_prepaid_cp") %>"     />
        <input type="hidden" id="EP_disp_cash_yn"      name="EP_disp_cash_yn"       value="<%=request.getParameter("EP_disp_cash_yn") %>" />
        <input type="hidden" id="EP_cert_type"         name="EP_cert_type"          value="<%=request.getParameter("EP_cert_type") %>" />
    </form>
</body>
</html>