<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>

			<c:if test="${pagination.totalPages > 1}">
				<div class="category_paging">
					<ul>
						<li>
				
							<c:if test="${pagination.previousPage == 1}">
					      		<c:set var="pageLink">${fn:replace(pagination.link, '?page=[page]&', '?')}</c:set>
					      		<c:set var="pageLink">${fn:replace(pageLink, '?page=[page]', '')}</c:set>
					      		<c:set var="pageLink">${fn:replace(pageLink, '&page=[page]&', '&')}</c:set>
							    <c:set var="pageLink">${fn:replace(pageLink, '&page=[page]', '')}</c:set>
							    
					      		<a href="${pageLink}">前へ</a>
					      	</c:if>
					      	<c:if test="${pagination.previousPage > 1}">
					  			<a href="${fn:replace(pagination.link, '[page]', pagination.previousPage)}">前へ</a>
					  		</c:if>
					  		<c:if test="${pagination.previousPage == 0}">
					  			&nbsp;
					  		</c:if>
							
					
						</li>
						
						
						<li class="number"> 
							<span>${pagination.currentPage}</span> / ${pagination.totalPages}
						</li>
						
						<li> 
							<c:if test="${pagination.nextPage > 0}">
								<a href="${fn:replace(pagination.link, '[page]', pagination.nextPage)}">次へ</a>
							</c:if>
							
							<c:if test="${pagination.nextPage == 0}">
					  			&nbsp;
					  		</c:if>
						</li>
					</ul>
				</div>
			</c:if>
