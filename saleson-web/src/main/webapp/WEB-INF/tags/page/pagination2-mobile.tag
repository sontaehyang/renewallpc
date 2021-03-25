<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>

			<c:if test="${pagination2.totalPages > 1}">
				<div class="category_paging">
					<ul>
						<li>
				
							<c:if test="${pagination2.previousPage == 1}">
					      		<a href="${fn:replace(pagination.link, '[page]', 1)}">이전</a>
					      	</c:if>
					      	<c:if test="${pagination2.previousPage > 1}">
					  			<a href="${fn:replace(pagination.link, '[page]', pagination.previousPage)}">이전</a>
					  		</c:if>
					  		<c:if test="${pagination2.previousPage == 0}">
					  			&nbsp;
					  		</c:if>
							
					
						</li>
						
						
						<li class="number"> 
							<span>${pagination2.currentPage}</span> / ${pagination2.totalPages}
						</li>
						
						<li> 
							<c:if test="${pagination2.nextPage > 0}">
								<a href="${fn:replace(pagination.link, '[page]', pagination.nextPage)}">다음</a>
							</c:if>
							
							<c:if test="${pagination2.nextPage == 0}">
					  			&nbsp;
					  		</c:if>
						</li>
					</ul>
				</div>
			</c:if>
