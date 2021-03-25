<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>

	<c:if test="${pagination.previousPage > 0}">
		<a href="${fn:replace(pagination.link, '[page]', 1)}" class="prev btn"><span class="icon_first_list">처음</span></a>
		<a href="${fn:replace(pagination.link, '[page]', pagination.previousPage)}" class="prev btn"><span class="icon_prev_list">이전</span></a>
	</c:if>
	<c:if test="${pagination.previousPage == 0}">
		
	</c:if>
	
		<span class="pagination"><span>${pagination.currentPage}</span>/<span>${pagination.totalPages}</span></span>
		

	<c:if test="${pagination.nextPage > 0}">
		<a href="${fn:replace(pagination.link, '[page]', pagination.nextPage)}" class="next btn"><span class="icon_next_list">다음</span></a>
		<a href="${fn:replace(pagination.link, '[page]', pagination.totalPages)}" class="next btn"><span class="icon_last_list">끝</span></a>
	</c:if>
	<c:if test="${pagination.nextPage == 0}">
		
	</c:if>
