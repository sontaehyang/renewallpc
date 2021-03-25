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

<c:if test="${!empty eventList}">
    <section class="msec07">
        <div class="inner">
            <p class="sec_tit">이벤트<a href="/event/list" class="btn_more">MORE ></a></p>
            <div class="mevent_list">
                <c:forEach items="${eventList}" var="event">
                    <c:choose>
                        <c:when test="${event.progression == '3'}">
                            <a href="javascript:void(0);" class="end">
                                <p class="img"><img src="${event.featuredImageSrc}" alt="${event.featuredName}"></p>
                                <p class="tit">
                                    ${event.featuredName}
                                    <span>
                                       <c:choose>
                                           <c:when test="${not empty event.startDate or not empty event.endDate}">
                                               ${op:date(event.startDate)} ~ ${op:date(event.endDate)}
                                           </c:when>
                                           <c:otherwise>
                                               상시 진행
                                           </c:otherwise>
                                       </c:choose>
                                    </span>
                                </p>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="/pages/${event.featuredUrl}">
                                <p class="img"><img src="${event.featuredImageSrc}" alt="${event.featuredName}"></p>
                                <p class="tit">
                                    ${event.featuredName}
                                    <span>
                                        <c:choose>
                                            <c:when test="${not empty event.startDate or not empty event.endDate}">
                                                ${op:date(event.startDate)} ~ ${op:date(event.endDate)}
                                            </c:when>
                                            <c:otherwise>
                                                상시 진행
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </p>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div><!--// mevent_list -->
        </div><!--// inner -->
    </section><!--// msec07 -->
</c:if>