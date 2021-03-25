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
<%!
    /*
     * �Ķ���� üũ �޼ҵ�
     */
    public String getNullToSpace(String param) 
    {
        return (param == null) ? "" : param.trim();
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="robots" content="noindex, nofollow" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<script>
    /*--KICC �����κ��� �������� �Ķ���� ����--*/
    window.onload = function() 
    {
    	/* UTF-8 ��밡������ ��� �ѱ��� ���� ���� ��� decoding �ʼ� */
        var res_msg = urldecode( "<%=request.getParameter("sp_res_msg") %>" );
    	
        // <!--����-->
        window.document.getElementById("sp_res_cd").value             = "<%=getNullToSpace(request.getParameter("sp_res_cd"))           %>";  // [�ʼ�]�����ڵ�
        window.document.getElementById("sp_res_msg").value            = res_msg;  															  // [�ʼ�]����޼���
        window.document.getElementById("sp_tr_cd").value              = "<%=getNullToSpace(request.getParameter("sp_tr_cd"))            %>";  // [�ʼ�]����â ��û����
        window.document.getElementById("sp_ret_pay_type").value       = "<%=getNullToSpace(request.getParameter("sp_ret_pay_type"))     %>";  // [�ʼ�]��������
        window.document.getElementById("sp_trace_no").value           = "<%=getNullToSpace(request.getParameter("sp_trace_no"))         %>";  // [����]������ȣ     
        window.document.getElementById("sp_order_no").value           = "<%=getNullToSpace(request.getParameter("sp_order_no"))         %>";  // [�ʼ�]������ �ֹ���ȣ
        window.document.getElementById("sp_sessionkey").value         = "<%=getNullToSpace(request.getParameter("sp_sessionkey"))       %>";  // [�ʼ�]����Ű
        window.document.getElementById("sp_encrypt_data").value       = "<%=getNullToSpace(request.getParameter("sp_encrypt_data"))     %>";  // [�ʼ�]��ȣȭ����
        window.document.getElementById("sp_mall_id").value            = "<%=getNullToSpace(request.getParameter("sp_mall_id"))          %>";  // [�ʼ�]������ ID
        window.document.getElementById("sp_mobilereserved1").value    = "<%=getNullToSpace(request.getParameter("sp_mobilereserved1"))  %>";  // [����]�����ʵ�
        window.document.getElementById("sp_mobilereserved2").value    = "<%=getNullToSpace(request.getParameter("sp_mobilereserved2"))  %>";  // [����]�����ʵ�
        window.document.getElementById("sp_reserved1").value          = "<%=getNullToSpace(request.getParameter("sp_reserved1"))        %>";  // [����]�����ʵ� 
        window.document.getElementById("sp_reserved2").value          = "<%=getNullToSpace(request.getParameter("sp_reserved2"))        %>";  // [����]�����ʵ�
        window.document.getElementById("sp_reserved3").value          = "<%=getNullToSpace(request.getParameter("sp_reserved3"))        %>";  // [����]�����ʵ�
        window.document.getElementById("sp_reserved4").value          = "<%=getNullToSpace(request.getParameter("sp_reserved4"))        %>";  // [����]�����ʵ�

        // <!--�ſ�ī��-->
        window.document.getElementById("sp_card_code").value          = "<%=getNullToSpace(request.getParameter("sp_card_code"))        %>";  // [�ʼ�]ī���ڵ� 
        window.document.getElementById("sp_eci_code").value           = "<%=getNullToSpace(request.getParameter("sp_eci_code"))         %>";  // [����]ECI�ڵ�(MPI�� ���)
        window.document.getElementById("sp_card_req_type").value      = "<%=getNullToSpace(request.getParameter("sp_card_req_type"))    %>";  // [�ʼ�]�ŷ�����
        window.document.getElementById("sp_save_useyn").value         = "<%=getNullToSpace(request.getParameter("sp_save_useyn"))       %>";  // [����]ī��� ���̺� ����
        window.document.getElementById("sp_card_prefix").value        = "<%=getNullToSpace(request.getParameter("sp_card_prefix"))      %>";  // [����]�ſ�ī�� Prefix 
        window.document.getElementById("sp_card_no_7").value          = "<%=getNullToSpace(request.getParameter("sp_card_no_7"))        %>";  // [����]�ſ�ī���ȣ ��7�ڸ�   
        
        // <!--�������-->
        window.document.getElementById("sp_spay_cp").value            = "<%=getNullToSpace(request.getParameter("sp_spay_cp"))          %>";  // [����]������� CP�ڵ�
        
        // <!--����ī��-->
        window.document.getElementById("sp_prepaid_cp").value         = "<%=getNullToSpace(request.getParameter("sp_prepaid_cp"))       %>";  // [����]����ī�� CP�ڵ�
                          
       if( "<%=request.getParameter("sp_res_cd") %>" == "0000" )
        {
            frm_pay.target = "_self";
            frm_pay.action = "../pay";
            frm_pay.submit();
        }
        else
        {
            alert( "<%=request.getParameter("sp_res_cd") %> : " + res_msg);
            location.href="/m/order/step1";
        }
    }
    
    function urldecode( str )
    {
        // ���� ������ + �� ó���ϱ� ���� +('%20') �� �������� ġȯ
        return decodeURIComponent((str + '').replace(/\+/g, '%20'));
    }
</script>
<title>EasyPay 8.0 webpay mobile</title>
</head>
<body>
 <form name="frm_pay" method="post" >  
    
    <!-- [START] �������� �ʵ� -->     
    
    <!--����-->
    <input type="hidden" id="sp_res_cd"              name="sp_res_cd"                value="" />         <!-- [�ʼ�]�����ڵ�        --> 
    <input type="hidden" id="sp_res_msg"             name="sp_res_msg"               value="" />         <!-- [�ʼ�]����޽���      --> 
    <input type="hidden" id="sp_tr_cd"               name="sp_tr_cd"                 value="" />         <!-- [�ʼ�]����â ��û���� --> 
    <input type="hidden" id="sp_ret_pay_type"        name="sp_ret_pay_type"          value="" />         <!-- [�ʼ�]��������        --> 
    <input type="hidden" id="sp_trace_no"            name="sp_trace_no"              value="" />         <!-- [����]������ȣ        --> 
    <input type="hidden" id="sp_order_no"            name="sp_order_no"              value="" />         <!-- [�ʼ�]������ �ֹ���ȣ --> 
    <input type="hidden" id="sp_sessionkey"          name="sp_sessionkey"            value="" />         <!-- [�ʼ�]����Ű          --> 
    <input type="hidden" id="sp_encrypt_data"        name="sp_encrypt_data"          value="" />         <!-- [�ʼ�]��ȣȭ����      --> 
    <input type="hidden" id="sp_mall_id"             name="sp_mall_id"               value="" />         <!-- [�ʼ�]������ ID       -->
    <input type="hidden" id="sp_mobilereserved1"     name="sp_mobilereserved1"       value="" />         <!-- [����]�����ʵ�        --> 
    <input type="hidden" id="sp_mobilereserved2"     name="sp_mobilereserved2"       value="" />         <!-- [����]�����ʵ�        --> 
    <input type="hidden" id="sp_reserved1"           name="sp_reserved1"             value="" />         <!-- [����]�����ʵ�        --> 
    <input type="hidden" id="sp_reserved2"           name="sp_reserved2"             value="" />         <!-- [����]�����ʵ�        --> 
    <input type="hidden" id="sp_reserved3"           name="sp_reserved3"             value="" />         <!-- [����]�����ʵ�        --> 
    <input type="hidden" id="sp_reserved4"           name="sp_reserved4"             value="" />         <!-- [����]�����ʵ�        --> 
    
    <!--�ſ�ī��-->                                                                                                                        
    <input type="hidden" id="sp_card_code"            name="sp_card_code"            value="" />         <!-- [�ʼ�]ī���ڵ�               -->
    <input type="hidden" id="sp_eci_code"             name="sp_eci_code"             value="" />         <!-- [����]ECI�ڵ�(MPI�� ���)    -->
    <input type="hidden" id="sp_card_req_type"        name="sp_card_req_type"        value="" />         <!-- [�ʼ�]�ŷ�����               -->
    <input type="hidden" id="sp_save_useyn"           name="sp_save_useyn"           value="" />         <!-- [����]ī��� ���̺� ����     -->
    <input type="hidden" id="sp_card_prefix"          name="sp_card_prefix"          value="" />         <!-- [����]�ſ�ī�� Prefix        -->
    <input type="hidden" id="sp_card_no_7"            name="sp_card_no_7"            value="" />         <!-- [����]�ſ�ī���ȣ ��7�ڸ�   -->
    
    <!--�������-->
    <input type="hidden" id="sp_spay_cp"              name="sp_spay_cp"              value="" />          <!-- [����]������� CP�ڵ� -->
                                                                                                        
    <!--����ī��-->                                                                                     
    <input type="hidden" id="sp_prepaid_cp"           name="sp_prepaid_cp"           value="" />          <!-- [����]����ī�� CP�ڵ� -->
    
    <!-- [END] �������� �ʵ�  --> 


</body>
</html>
