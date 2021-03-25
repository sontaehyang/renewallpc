<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="userAuth" 	tagdir="/WEB-INF/tags/userAuth" %>

<c:if test="${type == 'ipin'}">
<userAuth:ipin-form />
</c:if>

<c:if test="${type == 'pcc'}">
<userAuth:pcc-form />
</c:if>

<page:javascript>
<script type="text/javascript">
$(function(){
	$('form#userAuthForm').submit();
})
</script>
</page:javascript>