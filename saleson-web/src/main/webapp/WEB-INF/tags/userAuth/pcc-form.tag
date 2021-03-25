<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 

<c:if test="${op:property('user.auth.ipin.service') == 'siren24'}">
	<form id="userAuthForm" name="reqPCCForm" method="post" action="https://pcc.siren24.com/pcc_V3/jsp/pcc_V3_j10.jsp">
	    <input type="hidden" name="reqInfo" value="${encryptString}" />     <!--실명확인 회원사 아이디-->
	    <input type="hidden" name="retUrl" value="32${op:property('saleson.url.shoppingmall')}/users/user-auth-success/pcc">      <!--실명확인 결과수신 URL-->
	</form>
</c:if>