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
    <div class="event_section">
        <div class="title">
            <h3>이벤트</h3>
            <a href="/m/event" class="more_btn">MORE</a>
        </div>
        <ul class="event_section_list">
            <c:forEach items="${eventList}" var="event">
                <c:choose>
                    <c:when test="${event.progression == '3'}">
                        <li class="list_item end">
                            <a href="javascript:void(0);">
                                <div class="img"><img src="${event.featuredImageSrc}" alt="${event.featuredName}"></div>
                                <div class="text_box">
                                    <p class="tit">${event.featuredName}</p>
                                    <p class="txt">
                                        <c:choose>
                                            <c:when test="${not empty event.startDate or not empty event.endDate}">
                                                ${op:date(event.startDate)} ~ ${op:date(event.endDate)}
                                            </c:when>
                                            <c:otherwise>
                                                상시 진행
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="list_item">
                            <a href="/m/pages/${event.featuredUrl}">
                                <div class="img"><img src="${event.featuredImageSrc}" alt="${event.featuredName}"></div>
                                <div class="text_box">
                                    <p class="tit">${event.featuredName}</p>
                                    <p class="txt">
                                        <c:choose>
                                            <c:when test="${not empty event.startDate or not empty event.endDate}">
                                                ${op:date(event.startDate)} ~ ${op:date(event.endDate)}
                                            </c:when>
                                            <c:otherwise>
                                                상시 진행
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul><!--// event_section_list -->
    </div><!--// event_section -->
</c:if>