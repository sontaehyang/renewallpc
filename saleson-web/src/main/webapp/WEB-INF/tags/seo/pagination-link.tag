<%@ tag pageEncoding="utf-8" %><%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %><%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<c:if test="${pagination.totalPages > 0 && pagination.previousPage > 0}">
	<c:set var="prevPageLink">${fn:replace(pagination.link, '[page]', pagination.previousPage)}</c:set>
	<c:if test="${pagination.previousPage == 1}">
		<c:set var="prevPageLink">${fn:replace(fn:replace(prevPageLink, '?page=1', ''), '&page=1', '')}</c:set>
	</c:if>
	<link href="${prevPageLink}" rel="prev" />
</c:if>
<c:if test="${pagination.totalPages > 0 && pagination.nextPage > 0}">	<link href="${fn:replace(pagination.link, '[page]', pagination.nextPage)}" rel="next" /></c:if>