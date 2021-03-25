<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${noticeList}" var="notice" varStatus="i">
	<c:set var="target"></c:set>
	<c:set var="rel"></c:set>

	<c:if test="${notice.targetOption == 'Y' }">
		<c:set var="target">target="_blank"</c:set>
	</c:if>
	<c:if test="${notice.relOption == 'Y' }">
		<c:set var="rel">rel="nofollow"</c:set>
	</c:if>

	<li>
		<a href="/m/notice/view/${notice.noticeId}" ${target} ${rel}>
			<p class="tit">${notice.subject}</p>
			<p class="date">${op:date(notice.createdDate)}</p>
		</a>
	</li>
</c:forEach>