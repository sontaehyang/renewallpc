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
        /* UTF-8 ��밡������ ��� �ѱ��� ���� ���� ��� decoding �ʼ� */
        var res_msg = urldecode( "<%=request.getParameter("EP_res_msg") %>" );

        if(window.opener != null)
        {
        	window.parent.document.frm_pay.EP_res_cd.value         = "<%=request.getParameter("EP_res_cd") %>";           // �����ڵ�
            window.parent.document.frm_pay.EP_res_msg.value        = res_msg;                                             // ����޼���
            window.parent.document.frm_pay.EP_tr_cd.value          = "<%=request.getParameter("EP_tr_cd") %>";            // ���� ��û����
            window.parent.document.frm_pay.EP_ret_pay_type.value   = "<%=request.getParameter("EP_ret_pay_type") %>";     // ��������
            window.parent.document.frm_pay.EP_ret_complex_yn.value = "<%=request.getParameter("EP_ret_complex_yn") %>";   // ���հ��� ���� (Y/N)
            window.parent.document.frm_pay.EP_card_code.value      = "<%=request.getParameter("EP_card_code") %>";        // ī���ڵ� (ISP:KVPī���ڵ� MPI:ī���ڵ�)
            window.parent.document.frm_pay.EP_eci_code.value       = "<%=request.getParameter("EP_eci_code") %>";         // MPI�� ��� ECI�ڵ�
            window.parent.document.frm_pay.EP_card_req_type.value  = "<%=request.getParameter("EP_card_req_type") %>";    // �ŷ����� (0:�Ϲ�, 1:ISP, 2:MPI, 3:UPOP)
            window.parent.document.frm_pay.EP_save_useyn.value     = "<%=request.getParameter("EP_save_useyn") %>";       // ī��� ���̺� ���� (Y/N)
            window.parent.document.frm_pay.EP_trace_no.value       = "<%=request.getParameter("EP_trace_no") %>";         // ������ȣ
            window.parent.document.frm_pay.EP_sessionkey.value     = "<%=request.getParameter("EP_sessionkey") %>";       // ����Ű
            window.parent.document.frm_pay.EP_encrypt_data.value   = "<%=request.getParameter("EP_encrypt_data") %>";     // ��ȣȭ����
            window.parent.document.frm_pay.EP_spay_cp.value        = "<%=request.getParameter("EP_spay_cp") %>";          // ������� CP �ڵ�
            window.parent.document.frm_pay.EP_card_prefix.value    = "<%=request.getParameter("EP_card_prefix") %>";      // �ſ�ī��prefix
            window.parent.document.frm_pay.EP_card_no_7.value      = "<%=request.getParameter("EP_card_no_7") %>";        // �ſ�ī�� �� 7�ڸ�

            if( "<%=request.getParameter("EP_res_cd") %>" == "0000" )
            {
                window.opener.f_submit();
            }
            else
            {
                alert( "<%=request.getParameter("EP_res_cd") %> : " + res_msg );
            }

            self.close();
        }
        else
        {
        	window.parent.document.frm_pay.EP_res_cd.value         = "<%=request.getParameter("EP_res_cd") %>";           // �����ڵ�
            window.parent.document.frm_pay.EP_res_msg.value        = res_msg;                                             // ����޼���
            window.parent.document.frm_pay.EP_tr_cd.value          = "<%=request.getParameter("EP_tr_cd") %>";            // ���� ��û����
            window.parent.document.frm_pay.EP_ret_pay_type.value   = "<%=request.getParameter("EP_ret_pay_type") %>";     // ��������
            window.parent.document.frm_pay.EP_ret_complex_yn.value = "<%=request.getParameter("EP_ret_complex_yn") %>";   // ���հ��� ���� (Y/N)
            window.parent.document.frm_pay.EP_card_code.value      = "<%=request.getParameter("EP_card_code") %>";        // ī���ڵ� (ISP:KVPī���ڵ� MPI:ī���ڵ�)
            window.parent.document.frm_pay.EP_eci_code.value       = "<%=request.getParameter("EP_eci_code") %>";         // MPI�� ��� ECI�ڵ�
            window.parent.document.frm_pay.EP_card_req_type.value  = "<%=request.getParameter("EP_card_req_type") %>";    // �ŷ����� (0:�Ϲ�, 1:ISP, 2:MPI, 3:UPOP)
            window.parent.document.frm_pay.EP_save_useyn.value     = "<%=request.getParameter("EP_save_useyn") %>";       // ī��� ���̺� ���� (Y/N)
            window.parent.document.frm_pay.EP_trace_no.value       = "<%=request.getParameter("EP_trace_no") %>";         // ������ȣ
            window.parent.document.frm_pay.EP_sessionkey.value     = "<%=request.getParameter("EP_sessionkey") %>";       // ����Ű
            window.parent.document.frm_pay.EP_encrypt_data.value   = "<%=request.getParameter("EP_encrypt_data") %>";     // ��ȣȭ����
            window.parent.document.frm_pay.EP_spay_cp.value        = "<%=request.getParameter("EP_spay_cp") %>";          // ������� CP �ڵ�
            window.parent.document.frm_pay.EP_card_prefix.value    = "<%=request.getParameter("EP_card_prefix") %>";      // �ſ�ī��prefix
            window.parent.document.frm_pay.EP_card_no_7.value      = "<%=request.getParameter("EP_card_no_7") %>";        // �ſ�ī�� �� 7�ڸ�

            if( "<%=request.getParameter("EP_res_cd") %>" == "0000" )
            {
                window.parent.f_submit();
            }
            else
            {
                alert( "<%=request.getParameter("EP_res_cd") %> : " + res_msg );
            }

            window.parent.kicc_popup_close();

        }
    }

    function urldecode( str )
    {
        // ���� ������ + �� ó���ϱ� ���� +('%20') �� �������� ġȯ
        return decodeURIComponent((str + '').replace(/\+/g, '%20'));
    }

</script>
<title>webpay ������ test page</title>
</head>
<body>
</body>
</html>