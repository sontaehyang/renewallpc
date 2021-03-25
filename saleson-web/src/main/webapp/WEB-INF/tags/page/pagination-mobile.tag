<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>

	<c:if test="${pagination.previousPage > 0}">
	
		<a href="${fn:replace(pagination.link, '[page]', pagination.previousPage)}" class="firstBtn">&nbsp;</a>
		
	</c:if>
	<c:if test="${pagination.previousPage == 0}">
		<a href="#" class="firstBtn">&nbsp;</a>	
	</c:if>

	<c:choose>
		<c:when test="${empty pagination.totalPages}">	
			<span class="now"><b>1</b></span>
		</c:when>
		<c:when test="${pagination.totalPages == 0}">	
			<span class="now"><b>1</b></span>
		</c:when>
	    <c:when test="${pagination.totalPages < 5}">	
		    <c:forEach begin="1" end="${pagination.totalPages}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<span class="now"><b>${i}</b></span>
		            </c:when>
		            <c:otherwise>
		        		<a href="${fn:replace(pagination.link, '[page]', i)}">${i}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>	
		</c:when>

	    <c:when test="${pagination.currentPage < 4}">	
		    <c:forEach begin="1" end="5" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<span class="now"><b>${i}</b></span>
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

		<c:when test="${pagination.currentPage > pagination.totalPages - 3}">	
			<!-- 
			<a href="${fn:replace(pagination.link, '[page]', 1)}" class="edge">1</a>
			<a href="${fn:replace(pagination.link, '[page]', 2)}" class="edge">2</a>
			<span>&hellip;</span>		
			 -->
		    <c:forEach begin="${pagination.totalPages - 4}" end="${pagination.totalPages}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<span class="now"><b>${i}</b></span>
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
			
			<c:forEach begin="${pagination.currentPage - 2}" end="${pagination.currentPage+2}" var="i" >
		        <c:choose>
		            <c:when test="${i == pagination.currentPage}">
		        		<span class="now"><b>${i}</b></span>
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
		
		<a href="${fn:replace(pagination.link, '[page]', pagination.nextPage)}" class="lastBtn" title="다음페이지">&nbsp;</a>
	</c:if>
	<c:if test="${pagination.nextPage == 0}">
		<a href="#" class="lastBtn" title="다음페이지">&nbsp;</a>
	</c:if>
