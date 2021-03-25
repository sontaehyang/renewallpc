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
        /* UTF-8 사용가맹점의 경우 한글이 들어가는 값은 모두 decoding 필수 */
        var res_msg = urldecode( "<%=request.getParameter("EP_res_msg") %>" );

        if(window.opener != null)
        {
        	window.parent.document.frm_pay.EP_res_cd.value         = "<%=request.getParameter("EP_res_cd") %>";           // 응답코드
            window.parent.document.frm_pay.EP_res_msg.value        = res_msg;                                             // 응답메세지
            window.parent.document.frm_pay.EP_tr_cd.value          = "<%=request.getParameter("EP_tr_cd") %>";            // 결제 요청구분
            window.parent.document.frm_pay.EP_ret_pay_type.value   = "<%=request.getParameter("EP_ret_pay_type") %>";     // 결제수단
            window.parent.document.frm_pay.EP_ret_complex_yn.value = "<%=request.getParameter("EP_ret_complex_yn") %>";   // 복합결제 여부 (Y/N)
            window.parent.document.frm_pay.EP_card_code.value      = "<%=request.getParameter("EP_card_code") %>";        // 카드코드 (ISP:KVP카드코드 MPI:카드코드)
            window.parent.document.frm_pay.EP_eci_code.value       = "<%=request.getParameter("EP_eci_code") %>";         // MPI인 경우 ECI코드
            window.parent.document.frm_pay.EP_card_req_type.value  = "<%=request.getParameter("EP_card_req_type") %>";    // 거래구분 (0:일반, 1:ISP, 2:MPI, 3:UPOP)
            window.parent.document.frm_pay.EP_save_useyn.value     = "<%=request.getParameter("EP_save_useyn") %>";       // 카드사 세이브 여부 (Y/N)
            window.parent.document.frm_pay.EP_trace_no.value       = "<%=request.getParameter("EP_trace_no") %>";         // 추적번호
            window.parent.document.frm_pay.EP_sessionkey.value     = "<%=request.getParameter("EP_sessionkey") %>";       // 세션키
            window.parent.document.frm_pay.EP_encrypt_data.value   = "<%=request.getParameter("EP_encrypt_data") %>";     // 암호화전문
            window.parent.document.frm_pay.EP_spay_cp.value        = "<%=request.getParameter("EP_spay_cp") %>";          // 간편결제 CP 코드
            window.parent.document.frm_pay.EP_card_prefix.value    = "<%=request.getParameter("EP_card_prefix") %>";      // 신용카드prefix
            window.parent.document.frm_pay.EP_card_no_7.value      = "<%=request.getParameter("EP_card_no_7") %>";        // 신용카드 앞 7자리

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
        	window.parent.document.frm_pay.EP_res_cd.value         = "<%=request.getParameter("EP_res_cd") %>";           // 응답코드
            window.parent.document.frm_pay.EP_res_msg.value        = res_msg;                                             // 응답메세지
            window.parent.document.frm_pay.EP_tr_cd.value          = "<%=request.getParameter("EP_tr_cd") %>";            // 결제 요청구분
            window.parent.document.frm_pay.EP_ret_pay_type.value   = "<%=request.getParameter("EP_ret_pay_type") %>";     // 결제수단
            window.parent.document.frm_pay.EP_ret_complex_yn.value = "<%=request.getParameter("EP_ret_complex_yn") %>";   // 복합결제 여부 (Y/N)
            window.parent.document.frm_pay.EP_card_code.value      = "<%=request.getParameter("EP_card_code") %>";        // 카드코드 (ISP:KVP카드코드 MPI:카드코드)
            window.parent.document.frm_pay.EP_eci_code.value       = "<%=request.getParameter("EP_eci_code") %>";         // MPI인 경우 ECI코드
            window.parent.document.frm_pay.EP_card_req_type.value  = "<%=request.getParameter("EP_card_req_type") %>";    // 거래구분 (0:일반, 1:ISP, 2:MPI, 3:UPOP)
            window.parent.document.frm_pay.EP_save_useyn.value     = "<%=request.getParameter("EP_save_useyn") %>";       // 카드사 세이브 여부 (Y/N)
            window.parent.document.frm_pay.EP_trace_no.value       = "<%=request.getParameter("EP_trace_no") %>";         // 추적번호
            window.parent.document.frm_pay.EP_sessionkey.value     = "<%=request.getParameter("EP_sessionkey") %>";       // 세션키
            window.parent.document.frm_pay.EP_encrypt_data.value   = "<%=request.getParameter("EP_encrypt_data") %>";     // 암호화전문
            window.parent.document.frm_pay.EP_spay_cp.value        = "<%=request.getParameter("EP_spay_cp") %>";          // 간편결제 CP 코드
            window.parent.document.frm_pay.EP_card_prefix.value    = "<%=request.getParameter("EP_card_prefix") %>";      // 신용카드prefix
            window.parent.document.frm_pay.EP_card_no_7.value      = "<%=request.getParameter("EP_card_no_7") %>";        // 신용카드 앞 7자리

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
        // 공백 문자인 + 를 처리하기 위해 +('%20') 을 공백으로 치환
        return decodeURIComponent((str + '').replace(/\+/g, '%20'));
    }

</script>
<title>webpay 가맹점 test page</title>
</head>
<body>
</body>
</html>