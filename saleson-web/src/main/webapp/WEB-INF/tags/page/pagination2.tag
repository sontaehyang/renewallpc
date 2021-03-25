<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>


<p class="pagination op-pagination">
	<c:if test="${pagination2.previousPage > 0}">
	
		<a href="${fn:replace(pagination2.link, '[page]', 1)}" class="first"><img src="/content/images/btn/btn_first.gif" alt="First Page" /></a>
		<a href="${fn:replace(pagination2.link, '[page]', pagination2.previousPage)}" class="prev"><img src="/content/images/btn/btn_prev.gif" alt="Previous Page" /></a>
		
	</c:if>
	<c:if test="${pagination2.previousPage == 0}">
		
	</c:if>

	<c:choose>
		<c:when test="${empty pagination2.totalPages}">	
			<strong>1</strong>
		</c:when>
		<c:when test="${pagination2.totalPages == 0}">	
			<strong>1</strong>
		</c:when>
	    <c:when test="${pagination2.totalPages < 10}">	
		    <c:forEach begin="1" end="${pagination2.totalPages}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination2.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination2.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
		</c:when>

	    <c:when test="${pagination2.currentPage < 9}">	
		    <c:forEach begin="1" end="10" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination2.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination2.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
			    
			<!-- 
			<span>&hellip;</span>
			<a href="${fn:replace(pagination2.link, '[page]', pagination2.totalPages - 1)}" class="edge">${pagination2.totalPages - 1}</a>
			<a href="${fn:replace(pagination2.link, '[page]', pagination2.totalPages)}" class="edge">${pagination2.totalPages}</a>
			 -->
		</c:when>

		<c:when test="${pagination2.currentPage > pagination2.totalPages - 8}">	
			<!-- 
			<a href="${fn:replace(pagination2.link, '[page]', 1)}" class="edge">1</a>
			<a href="${fn:replace(pagination2.link, '[page]', 2)}" class="edge">2</a>
			<span>&hellip;</span>		
			 -->
		    <c:forEach begin="${pagination2.totalPages - 9}" end="${pagination2.totalPages}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination2.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination2.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
		</c:when>

		<c:otherwise>
			<!-- 
			<a href="${fn:replace(pagination2.link, '[page]', 1)}" class="edge">1</a>
			<a href="${fn:replace(pagination2.link, '[page]', 2)}" class="edge">2</a>
			<span>&hellip;</span>	
			 -->	
			
			<c:forEach begin="${pagination2.currentPage - 5}" end="${pagination2.currentPage+5}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination2.currentPage}">
		        		<strong>${i}</strong>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination2.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
			
			<!-- 
			<span>&hellip;</span>
			<a href="${fn:replace(pagination2.link, '[page]', pagination2.totalPages - 1)}" class="edge">${pagination2.totalPages - 1}</a>
			<a href="${fn:replace(pagination2.link, '[page]', pagination2.totalPages)}" class="edge">${pagination2.totalPages}</a>
			 -->
		</c:otherwise>
	</c:choose>

	<c:if test="${pagination2.nextPage > 0}">
		
		<a href="${fn:replace(pagination2.link, '[page]', pagination2.nextPage)}" class="next"><img src="/content/images/btn/btn_next.gif" alt="Next Page" /></a>
		<a href="${fn:replace(pagination2.link, '[page]', pagination2.totalPages)}" class="last"><img src="/content/images/btn/btn_last.gif" alt="Last Page" /></a>
	</c:if>
	<c:if test="${pagination2.nextPage == 0}">
		
	</c:if>
</p>