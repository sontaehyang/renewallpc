<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 

<c:if test="${op:property('user.auth.ipin.service') == 'siren24'}">
	<form id="userAuthForm" name="reqCBAForm" method="post" action="https://ipin.siren24.com/i-PIN/jsp/ipin_j10.jsp">
	    <input type="hidden" name="reqInfo" value="${encryptString}" />     <!--실명확인 회원사 아이디-->
	    <input type="hidden" name="retUrl" value="23${op:property('saleson.url.shoppingmall')}/users/user-auth-success/ipin">      <!--실명확인 결과수신 URL-->
	</form>
</c:if>