<%@ page contentType="text/html; charset=euc-kr" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/* -------------------------------------------------------------------------- */
/* ĳ�� ������                                                              */
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
<title>webpay ������ test page</title>
</head>
<body>
    <!-- KICC ������ ������û -->
    
    <!-- TEST -->
    <form name="frm" method="post" action="http://testsp.easypay.co.kr/ep8/MainAction.do" >
    <!-- REAL -->
    <!--form name="frm" method="post" action="https://sp.easypay.co.kr/ep8/MainAction.do" -->
    
            
        <!--����-->
        <input type="hidden" id="sp_mall_id"           name="sp_mall_id"           value="<%=request.getParameter("sp_mall_id")           %>" /> <!--[�ʼ�]������ID -->
        <input type="hidden" id="sp_mall_nm"           name="sp_mall_nm"           value="<%=request.getParameter("sp_mall_nm")           %>" /> <!--[����]�������� -->
        <input type="hidden" id="sp_order_no"          name="sp_order_no"          value="<%=request.getParameter("sp_order_no")          %>" /> <!--[�ʼ�]������ �ֹ���ȣ(��������) -->
        <input type="hidden" id="sp_pay_type"          name="sp_pay_type"          value="<%=request.getParameter("sp_pay_type")          %>" /> <!--[�ʼ�]�������� -->
        <input type="hidden" id="sp_cert_type"         name="sp_cert_type"         value="<%=request.getParameter("sp_cert_type")         %>" /> <!--[����]����Ÿ�� -->  
        <input type="hidden" id="sp_currency"          name="sp_currency"          value="<%=request.getParameter("sp_currency")          %>" /> <!--[�ʼ�]��ȭ�ڵ�(�����Ұ�) -->        
        <input type="hidden" id="sp_product_nm"        name="sp_product_nm"        value="<%=request.getParameter("sp_product_nm")        %>" /> <!--[�ʼ�]��ǰ�� -->
        <input type="hidden" id="sp_product_amt"       name="sp_product_amt"       value="<%=request.getParameter("sp_product_amt")       %>" /> <!--[�ʼ�]��ǰ�ݾ� m-->
        <input type="hidden" id="sp_return_url"        name="sp_return_url"        value="<%=request.getParameter("sp_return_url")        %>" /> <!--[�ʼ�]������ return URL -->
        <input type="hidden" id="sp_lang_flag"         name="sp_lang_flag"         value="<%=request.getParameter("sp_lang_flag")         %>" /> <!--[����]��� -->
        <input type="hidden" id="sp_charset"           name="sp_charset"           value="<%=request.getParameter("sp_charset")           %>" /> <!--[����]������ charset -->  
        <input type="hidden" id="sp_user_id"           name="sp_user_id"           value="<%=request.getParameter("sp_user_id")           %>" /> <!--[����]������ ��ID -->
        <input type="hidden" id="sp_memb_user_no"      name="sp_memb_user_no"      value="<%=request.getParameter("sp_memb_user_no")      %>" /> <!--[����]������ ���Ϸù�ȣ -->
        <input type="hidden" id="sp_user_nm"           name="sp_user_nm"           value="<%=request.getParameter("sp_user_nm")           %>" /> <!--[����]������ ���� -->
        <input type="hidden" id="sp_user_mail"         name="sp_user_mail"         value="<%=request.getParameter("sp_user_mail")         %>" /> <!--[����]������ �� E-mail -->
        <input type="hidden" id="sp_user_phone1"       name="sp_user_phone1"       value="<%=request.getParameter("sp_user_phone1")       %>" /> <!--[����]������ �� ����ó1 -->
        <input type="hidden" id="sp_user_phone2"       name="sp_user_phone2"       value="<%=request.getParameter("sp_user_phone2")       %>" /> <!--[����]������ �� ����ó2 -->
        <input type="hidden" id="sp_user_addr"         name="sp_user_addr"         value="<%=request.getParameter("sp_user_addr")         %>" /> <!--[����]������ �� �ּ� -->
        <input type="hidden" id="sp_user_define1"      name="sp_user_define1"      value="<%=request.getParameter("sp_user_define1")      %>" /> <!--[����]������ �ʵ�1 -->
        <input type="hidden" id="sp_user_define2"      name="sp_user_define2"      value="<%=request.getParameter("sp_user_define2")      %>" /> <!--[����]������ �ʵ�2 -->
        <input type="hidden" id="sp_user_define3"      name="sp_user_define3"      value="<%=request.getParameter("sp_user_define3")      %>" /> <!--[����]������ �ʵ�3 -->
        <input type="hidden" id="sp_user_define4"      name="sp_user_define4"      value="<%=request.getParameter("sp_user_define4")      %>" /> <!--[����]������ �ʵ�4 -->
        <input type="hidden" id="sp_user_define5"      name="sp_user_define5"      value="<%=request.getParameter("sp_user_define5")      %>" /> <!--[����]������ �ʵ�5 -->
        <input type="hidden" id="sp_user_define6"      name="sp_user_define6"      value="<%=request.getParameter("sp_user_define6")      %>" /> <!--[����]������ �ʵ�6 -->
        <input type="hidden" id="sp_mobilereserved1"   name="sp_mobilereserved1"   value="<%=request.getParameter("sp_mobilereserved1")   %>" /> <!--[����]������ �����ʵ�1        -->    
        <input type="hidden" id="sp_mobilereserved2"   name="sp_mobilereserved2"   value="<%=request.getParameter("sp_mobilereserved2")   %>" /> <!--[����]������ �����ʵ�2        -->    
        <input type="hidden" id="sp_reserved1"         name="sp_reserved1"         value="<%=request.getParameter("sp_reserved1")         %>" /> <!--[����]������ �����ʵ�1        -->    
        <input type="hidden" id="sp_reserved2"         name="sp_reserved2"         value="<%=request.getParameter("sp_reserved2")         %>" /> <!--[����]������ �����ʵ�2        -->    
        <input type="hidden" id="sp_reserved3"         name="sp_reserved3"         value="<%=request.getParameter("sp_reserved3")         %>" /> <!--[����]������ �����ʵ�3        -->    
        <input type="hidden" id="sp_reserved4"         name="sp_reserved4"         value="<%=request.getParameter("sp_reserved4")         %>" /> <!--[����]������ �����ʵ�4        -->            
        <input type="hidden" id="sp_product_type"      name="sp_product_type"      value="<%=request.getParameter("sp_product_type")      %>" /> <!--[����]��ǰ�������� -->
        <input type="hidden" id="sp_product_expr"      name="sp_product_expr"      value="<%=request.getParameter("sp_product_expr")      %>" /> <!--[����]���� �Ⱓ -->
        <input type="hidden" id="sp_app_scheme"        name="sp_app_scheme"        value="<%=request.getParameter("sp_app_scheme")        %>" /> <!--[����]������ APP scheme -->  
        <input type="hidden" id="sp_window_type"       name="sp_window_type"       value="<%=request.getParameter("sp_window_type")       %>" /> <!--[����]������Ÿ�� -->
        <input type="hidden" id="sp_disp_cash_yn"      name="sp_disp_cash_yn"      value="<%=request.getParameter("sp_disp_cash_yn")      %>" /> <!--[����]���ݿ����� ȭ��ǥ�ÿ���(Y/N)--> 
                                                                                   
        <!--�ſ�ī��-->                                                                           
        <input type="hidden" id="sp_usedcard_code"     name="sp_usedcard_code"     value="<%=request.getParameter("sp_usedcard_code")     %>" /> <!--[����]��밡��ī�� LIST -->
        <input type="hidden" id="sp_quota"             name="sp_quota"             value="<%=request.getParameter("sp_quota")             %>" /> <!--[����]�Һΰ��� -->
        <input type="hidden" id="sp_os_cert_flag"      name="sp_os_cert_flag"      value="<%=request.getParameter("sp_os_cert_flag")      %>" /> <!--[����]�ؿܾȽ�Ŭ�� ��뿩��-->
        <input type="hidden" id="sp_noinst_flag"       name="sp_noinst_flag"       value="<%=request.getParameter("sp_noinst_flag")       %>" /> <!--[����]������ ����(Y/N) -->
        <input type="hidden" id="sp_noinst_term"       name="sp_noinst_term"       value="<%=request.getParameter("sp_noinst_term")       %>" /> <!--[����]������ �Ⱓ -->
        <input type="hidden" id="sp_set_point_card_yn" name="sp_set_point_card_yn" value="<%=request.getParameter("sp_set_point_card_yn") %>" /> <!--[����]ī�������Ʈ ��뿩��(Y/N) -->
        <input type="hidden" id="sp_point_card"        name="sp_point_card"        value="<%=request.getParameter("sp_point_card")        %>" /> <!--[����]����Ʈī�� LIST(ī���ڵ�-���� �Һΰ���) -->
        <input type="hidden" id="sp_join_cd"           name="sp_join_cd"           value="<%=request.getParameter("sp_join_cd")           %>" /> <!--[����]�����ڵ� -->
        <input type="hidden" id="sp_kmotion_useyn"     name="sp_kmotion_useyn"     value="<%=request.getParameter("sp_kmotion_useyn")     %>" /> <!--[����]���ξ�ī�� ������� -->
                                                                                                                                                      
        <!--�������-->
        <input type="hidden" id="sp_vacct_bank"       name="sp_vacct_bank"         value="<%=request.getParameter("sp_vacct_bank")        %>" /> <!--[����]������� ��밡���� ���� LIST -->
        <input type="hidden" id="sp_vacct_end_date"   name="sp_vacct_end_date"     value="<%=request.getParameter("sp_vacct_end_date")    %>" /> <!--[����]�Ա� ���� ��¥ -->
        <input type="hidden" id="sp_vacct_end_time"   name="sp_vacct_end_time"     value="<%=request.getParameter("sp_vacct_end_time")    %>" /> <!--[����]�Ա� ���� �ð� -->
                   
        <!--����ī��-->
        <input type="hidden" id="sp_prepaid_cp"       name="sp_prepaid_cp"         value="<%=request.getParameter("sp_prepaid_cp")        %>" /> <!--[����]����ī�� CP -->
             
     </form>
</body>
</html>
