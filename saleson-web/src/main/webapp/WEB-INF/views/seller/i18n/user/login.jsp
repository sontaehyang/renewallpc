<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<style>
	.login-type {
		width: 50px !important;
		height: 20px !important;
	}
</style>
<!-- 내용 -->

<div id="container" class="login">

	<div class="login_area">
		<form id="loginForm"  action="/op_security_login" method="POST" >
			<fieldset>
				<legend class="hidden">로그인</legend>
				<input type="hidden"  name="_csrf" value="${_csrf.token}"/>
				<input type="hidden" name="_csrf_header" value="${_csrf.headerName}"/>
				<input type="hidden" name="op_login_type" value="ROLE_SELLER" />
				<input type="hidden" name="target" value="<c:out escapeXml="true" value="${param.target}" />" />
				<input type="hidden" name="failureUrl" value="${requestContext.requestUri}?target=<c:out escapeXml="true" value="${param.target}" />" />

				<h2>판매관리자 로그인</h2> <!-- 관리자 로그인 -->
				<p class="guide">로그인 후 상품 및 주문을 관리하실 수 있습니다.</p> <!-- 관리자 로그인 후 홈페이지를 관리하실 수 있습니다. -->
				<div class="inputs">
					<p>
						<span class="label">${op:message('M00081')}</span> <!-- 아이디 -->
						<%-- <input type="text" name="op_username" id="op_username" maxlength="40" class="required ime-mode-disabled" value="${OP_LOGIN_LAST_USERNAME}" title="아이디" /> --%>
						<input type="text" name="op_username" id="op_username" maxlength="40" class="required ime-mode-disabled" title="아이디" />
					</p>
					<p> 
						<span class="label">${op:message('M00150')}</span> <!-- 비밀번호 -->
						<input type="password" name="op_password" id="op_password" maxlength="20" class="required" title="${op:message('M00150')}" />
					</p>
				</div>
				<div class="footer">
					<button type="submit" class="btn btn-orange btn-lg"><span>${op:message('M00796')}</span></button> <!-- 로그인 ログイン -->
				</div>
			</fieldset>
		</form>
	</div>
</div>


<!-- 하단 
<div id="footer">
	<p><span class="copy">copyright (c) onlinepowers all tights riserved.</span>
		<em>T</em> : 02-6737-9200 <em>F</em> : 02-6737-3330 <em>E</em> : help@onlinepowers.com</p>
</div>
-->


<page:javascript>
<script type="text/javascript">
$(function() {
	
	<c:if test="${requestContext.serviceType.local || requestContext.serviceType.testing}">
		//$('#op_username').val('onpa');
		//$('#op_password').val('1111');
		$('#op_username').val('seller');
		$('#op_password').val('1111');
		//$('#loginForm').submit();
	</c:if>
	
	// 포커스.
	$('#op_username').val() == '' ? $('#op_username').focus() : $('#op_password').focus();
	
	// 폼 검증..
	$('#loginForm').validator(function() {
		
	});

	var errorCode = '<c:out escapeXml="true" value="${param.error}" />';
	
	if (errorCode != "") {

		var errorMsg = '관리자정보가 존재하지 않아 로그인할 수 없습니다';
		var errorLink = '';

		if (errorCode == '4') {
			errorMsg = '계정이 잠겨 있습니다.';
			errorLink = '/seller/login-lock';
		} else if (errorCode == '5') {
			errorMsg = '비밀번호상태 가 임시 또는 만료되었습니다.';
			errorLink = '/seller/login-change-password';
		}

		setTimeout(function() {
			alert(errorMsg);
			if (errorLink != '') {
				location.href = errorLink
			}
		}, 100);
	}


});

try {
if (opener) {
	self.close();
	opener.location.href=url("/seller/login?taget=<c:out escapeXml="true" value="${param.target}" />");
}
} catch(e) {alert(e.message)};
</script>			
</page:javascript>
