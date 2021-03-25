<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${noticeList}" var="notice">
	<c:set var="target"></c:set>
	<c:set var="rel"></c:set>

	<c:if test="${notice.targetOption == 'Y' }">
		<c:set var="target">target="_blank"</c:set>
	</c:if>
	<c:if test="${notice.relOption == 'Y' }">
		<c:set var="rel">rel="nofollow"</c:set>
	</c:if>

	<li><a href="${notice.url}" ${target} ${rel}>${notice.subject}</a> <span class="date">${op:date(notice.createdDate)}</span></li>
</c:forEach>