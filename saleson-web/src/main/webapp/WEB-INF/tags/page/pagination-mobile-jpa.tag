<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>--%>

<%-- Declaration of page, url and size tag attributes --%>

<%-- The page attribute of type org.springframework.data.domain.Page --%>
<%@ attribute name="page" required="false" type="org.springframework.data.domain.Page"%>

<%-- The url path before the page request parameter  ==> javascript:pagination([page]) --%>
<%@ attribute name="url" required="false"%>

<%--Number of page numbers to display at once --%>
<%@ attribute name="size" required="false"%>


<c:set var="page" value="${empty page ? pageContent : page}"/>
<c:set var="url" value="${empty url ? requestContext.paginationUrl : url}"/>

<%-- Declaration of the default size value --%>
<c:set var="size" value="${empty size ? 10 : size}"/>

<%-- half_size_floor = floor(size/2)  is used to display the current page in the middle --%>
<c:set var="N" value="${size/2}"/>
<c:set var="half_size_floor">
    <fmt:formatNumber value="${N-(1-(N%1))%1}" type="number" pattern="#"/>
</c:set>

<%-- current variable stands for the current page number  --%>
<c:set var="current" value="${page.number + 1}"/>

<c:set var="startPage" value="${current < half_size_floor + 1 ? 1 : current - half_size_floor }"/>
<c:set var="startPage" value="${current > page.totalPages - half_size_floor ? page.totalPages - size + 1 : startPage }"/>
<c:set var="endPage" value="${startPage + size - 1}"/>
<c:set var="endPage" value="${endPage > page.totalPages ? page.totalPages : endPage}"/>

<%--less pages then the size of the block --%>
<c:set var="startPage" value="${page.totalPages < size ? 1 : startPage}"/>
<c:set var="endPage" value="${page.totalPages < size ? page.totalPages : endPage}"/>




<c:if test="${current > 1}">
    <a href="${fn:replace(url, '[page]', current - 1)}" class="firstBtn">&nbsp;</a>
</c:if>
<c:if test="${current == 0}">
    <a href="#" class="firstBtn">&nbsp;</a>
</c:if>

<c:choose>
    <c:when test="${empty page.totalPages}">
        <span class="now"><b>1</b></span>
    </c:when>
    <c:when test="${page.totalPages == 0}">
        <span class="now"><b>1</b></span>
    </c:when>
    <c:when test="${page.totalPages < 5}">
        <c:forEach begin="1" end="${page.totalPages}" var="i" >
            <c:choose>
                <c:when test="${i == page.totalPages}">
                    <span class="now"><b>${i}</b></span>
                </c:when>
                <c:otherwise>
                    <%--<a href="${fn:replace(pagination.link, '[page]', i)}">${i}</a>--%>
                    <a href="${fn:replace(url, '[page]', i)}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${current < 4}">
        <c:forEach begin="1" end="5" var="i" >
            <c:choose>
                <c:when test="${i == current}">
                    <span class="now"><b>${i}</b></span>
                </c:when>
                <c:otherwise>
                    <a href="${fn:replace(url, '[page]', i)}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <!--
        <span>&hellip;</span>
        <a href="${fn:replace(url, '[page]', page.totalPages - 1)}" class="edge">${page.totalPages - 1}</a>
        <a href="${fn:replace(url, '[page]', page.totalPages)}" class="edge">${page.totalPages}</a>
        -->
    </c:when>

    <c:when test="${current > page.totalPages - 3}">
        <!--
        <a href="${fn:replace(url, '[page]', 1)}" class="edge">1</a>
        <a href="${fn:replace(url, '[page]', 2)}" class="edge">2</a>
        <span>&hellip;</span>
        -->
        <c:forEach begin="${page.totalPages - 4}" end="${page.totalPages}" var="i" >
            <c:choose>
                <c:when test="${i == current}">
                    <span class="now"><b>${i}</b></span>
                </c:when>
                <c:otherwise>
                    <a href="${fn:replace(url, '[page]', i)}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:otherwise>
        <!--
        <a href="${fn:replace(url, '[page]', 1)}" class="edge">1</a>
        <a href="${fn:replace(url, '[page]', 2)}" class="edge">2</a>
        <span>&hellip;</span>
        -->

        <c:forEach begin="${current - 2}" end="${current+2}" var="i" >
            <c:choose>
                <c:when test="${i == current}">
                    <span class="now"><b>${i}</b></span>
                </c:when>
                <c:otherwise>
                    <a href="${fn:replace(url, '[page]', i)}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <!--
        <span>&hellip;</span>
        <a href="${fn:replace(url, '[page]', page.totalPages - 1)}" class="edge">${page.totalPages - 1}</a>
        <a href="${fn:replace(url, '[page]', page.totalPages)}" class="edge">${page.totalPages}</a>
        -->
    </c:otherwise>
</c:choose>

<c:if test="${current < endPage}">
    <a href="${fn:replace(url, '[page]', current + 1)}" class="lastBtn" title="다음페이지">&nbsp;</a>
</c:if>
<c:if test="${current == 0}">
    <a href="#" class="lastBtn" title="다음페이지">&nbsp;</a>
</c:if>
