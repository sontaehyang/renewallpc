<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<c:set var="targetUrl" value='/sns-user/redirect?redirect=${!empty redirect ? redirect : ""}' />
<c:set var="targetUrl" value='${redirect == "/m/order/step1" ? uri : targetUrl}${redirect == "/m/order/step1" ? "?login=T" : ""}' />
<form name="sso-login-form" id="sso-login-form" action="/op_security_login" method="post" target="_parent">
	<input type="hidden" name="target" value='${popup == "1" && target == "order" ? targetUrl : target }' />
	<input type="hidden" name="op_login_type" value="ROLE_USER" />
	<input type="hidden" name="op_username" />
	<input type="hidden" name="op_password" id="op_password" />
	<input type="hidden" name="op_signature" id="op_signature" />
</form>

<script type="text/javascript">
	function snsUserSubmit(snsUser) {
		$.post("/sns-user/search", snsUser, function(response) {
			if (response.status == "00") {
				if (response.value == "00") {
					$('#sso-login-form input[name="op_username"]').val(response.shadowLoginKey);
					$("#sso-login-form #op_password").val(response.shadowLoginPassword);
					$("#sso-login-form #op_signature").val(response.shadowLoginSignature);
					$("#sso-login-form").submit();
				}
				else {
					alert(response.message);
					return false;
				}
			}
			else {
				console.log("Error Occurred - " + response.message);
			}
		});
	}
</script>
