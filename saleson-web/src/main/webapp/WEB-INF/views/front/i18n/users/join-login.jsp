<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
			
<form id="form-auto-login" action="/op_security_login" method="post">
<input type="hidden" name="op_username" value="${loginId}" />
<input type="hidden" name="op_password" value="${password}" />
<input type="hidden" name="op_login_type" value="ROLE_USER" />
<input type="hidden" name="op_signature" value="${signature}" />

<c:choose>
	<c:when test="${ redirect ne '' }">
		<input type="hidden" name="target" value="/users/join-complete?redirect=${ redirect }" />
	</c:when>
	<c:otherwise>
		<input type="hidden" name="target" value="/users/join-complete" />
	</c:otherwise>
</c:choose>

</form>


<page:javascript>
<script type="text/javascript">
$(function() {
	$('#form-auto-login').submit();
});

</script>
</page:javascript>