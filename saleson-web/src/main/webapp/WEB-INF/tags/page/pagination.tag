<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>


<p class="pagination op-pagination">
	<c:if test="${pagination.previousPage > 0}">
	
		<a href="${fn:replace(pagination.link, '[page]', 1)}" class="first"><img src="/content/images/btn/btn_first.gif" alt="First Page" /></a>
		<a href="${fn:replace(pagination.link, '[page]', pagination.previousPage)}" class="prev"><img src="/content/images/btn/btn_prev.gif" alt="Previous Page" /></a>
		
	</c:if>
	<c:if test="${pagination.previousPage == 0}">
		
	</c:if>

	<c:choose>
		<c:when test="${empty pagination.totalPages}">	
			<strong>1</strong>
		</c:when>
		<c:when test="${pagination.totalPages == 0}">	
			<strong>1</strong>
		</c:when>
	    <c:when test="${pagination.totalPages < 10}">	
		    <c:forEach begin="1" end="${pagination.totalPages}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
		</c:when>

	    <c:when test="${pagination.currentPage < 9}">	
		    <c:forEach begin="1" end="10" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
			    
			<!-- 
			<span>&hellip;</span>
			<a href="${fn:replace(pagination.link, '[page]', pagination.totalPages - 1)}" class="edge">${pagination.totalPages - 1}</a>
			<a href="${fn:replace(pagination.link, '[page]', pagination.totalPages)}" class="edge">${pagination.totalPages}</a>
			 -->
		</c:when>

		<c:when test="${pagination.currentPage > pagination.totalPages - 8}">	
			<!-- 
			<a href="${fn:replace(pagination.link, '[page]', 1)}" class="edge">1</a>
			<a href="${fn:replace(pagination.link, '[page]', 2)}" class="edge">2</a>
			<span>&hellip;</span>		
			 -->
		    <c:forEach begin="${pagination.totalPages - 9}" end="${pagination.totalPages}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
		</c:when>

		<c:otherwise>
			<!-- 
			<a href="${fn:replace(pagination.link, '[page]', 1)}" class="edge">1</a>
			<a href="${fn:replace(pagination.link, '[page]', 2)}" class="edge">2</a>
			<span>&hellip;</span>	
			 -->	
			
			<c:forEach begin="${pagination.currentPage - 5}" end="${pagination.currentPage+5}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
			
			<!-- 
			<span>&hellip;</span>
			<a href="${fn:replace(pagination.link, '[page]', pagination.totalPages - 1)}" class="edge">${pagination.totalPages - 1}</a>
			<a href="${fn:replace(pagination.link, '[page]', pagination.totalPages)}" class="edge">${pagination.totalPages}</a>
			 -->
		</c:otherwise>
	</c:choose>

	<c:if test="${pagination.nextPage > 0}">
		
		<a href="${fn:replace(pagination.link, '[page]', pagination.nextPage)}" class="next"><img src="/content/images/btn/btn_next.gif" alt="Next Page" /></a>
		<a href="${fn:replace(pagination.link, '[page]', pagination.totalPages)}" class="last"><img src="/content/images/btn/btn_last.gif" alt="Last Page" /></a>
	</c:if>
	<c:if test="${pagination.nextPage == 0}">
		
	</c:if>
</p>