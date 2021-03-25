<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<c:if test="${requestContext.user.shadowLogin == true}">
<style>
#shadow {
  position: fixed;
  right: 0;
  bottom: 0;
  z-index: 9999;
  width: 250px;
  padding: 10px 15px;
  border-left: 15px solid #efb53c;
  background: #fdf58f;
  opacity: 0.9;
}

#shadow a {
  color: #000;
  text-decoration: underline;
  display: inline-block;
  padding-top: 5px;
}

</style>
<div id="shadow">
	<span>&nbsp;</span>
	<strong>${requestContext.user.userName}</strong>님으로 로그인 되었습니다.<br />
	<a href="javascript:;" onclick="shadowLogoutLog()">관리자로 돌아가기</a>
	<form name="shadow-login-form" id="shadow-login-form" action="/op_security_login" method="post">
		<input type="hidden" name="target" value="/opmanager/user/customer/list" />
		<input type="hidden" name="op_login_type" value="ROLE_OPMANAGER" />
		<input type="hidden" name="op_shadow_login" value="Y" />
		<input type="hidden" id="op_username" name="op_username" value="${op:shadowLogoutKey(requestContext.user.shadowManagerId)}" />
		<input type="hidden" id="op_password" name="op_password" value="${op:shadowLoginPassword(requestContext.user.shadowManagerId)}" />
	</form>
</div>

<script type="text/javascript">
function shadowLogoutLog() {
	$.get('/common/shadow-logout-log', null ,function() {
		document.getElementById('shadow-login-form').submit();
	});
}
</script>

</c:if>

<c:if test="${sellerContext.isShadowlogin == true}">

<style>
#shadow {
  position: fixed;
  right: 0;
  bottom: 0;
  z-index: 9999;
  width: 250px;
  padding: 10px 15px;
  border-left: 15px solid #efb53c;
  background: #fdf58f;
  opacity: 0.9;
}

#shadow a {
  color: #000;
  text-decoration: underline;
  display: inline-block;
  padding-top: 5px;
}

</style>
<div id="shadow">
	<strong>${sellerContext.seller.sellerName}</strong>님으로 로그인 되었습니다.<br />
	<a href="javascript:;" onclick="document.getElementById('shadow-login-form').submit()">관리자로 돌아가기</a>
	<form name="shadow-login-form" id="shadow-login-form" action="/seller/shadow-logout" method="post">
	</form>
</div>

</c:if>

<page:javascript>
<script type="text/javascript">
var main = '${main}';
var userId = '${requestContext.user.userId}';
var RequestContext = {
	'currentUrl': '${requestContext.currentUrl}',
	'prevPageUrl': '${requestContext.prevPageUrl}'	
};

var isMobileLayer = '${shopContext.mobileLayer}';
if (isMobileLayer == 'true') {
	isMobileLayer = true;
}
$(function() {
	Common.handleFrameworkMessage(
		'${requestContext.frameworkMessage}',
		'${requestContext.frameworkJavascript}',
		'${FRAMEWORK_MESSAGE}',
		'${FRAMEWORK_JAVASCRIPT}'
	);
	
	if (main == 'main') {
		if ($.cookie('receiptPopup_'+userId) == 'null' 
				|| $.cookie('receiptPopup_'+userId) == '' 
				|| $.cookie('receiptPopup_'+userId) == undefined
		) {
			Main.receiptPopup('${receiptCount}');
		}
	}
	
});
</script>
</page:javascript>


