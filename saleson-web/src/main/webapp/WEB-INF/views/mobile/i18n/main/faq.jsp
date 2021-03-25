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

<c:forEach items="${pageContent.content}" var="faq">
    <li>
        <a href="/m/faq?faqType=${faq.faqType.code}">
            <p class="tit">${faq.title}</p>
            <p class="date">${faq.createdDate}</p>
        </a>
    </li>
</c:forEach>