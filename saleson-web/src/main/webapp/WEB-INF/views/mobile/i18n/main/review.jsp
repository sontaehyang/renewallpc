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

<c:if test="${!empty reviewList}">
    <div class="review_section">
        <div class="title">
            <h3>솔직한 리뷰</h3>
        </div>
        <div class="review_section_list">
            <div class="review_slider">
                <div class="swiper-wrapper">
                    <c:forEach items="${reviewList}" var="review">
                        <div class="swiper-slide">
                            <a href="${review.item.link}" class="review_img">
                                <div class="img"><img src="${shop:loadImageBySrc(review.item.imageSrc, 'XS')}" alt="${review.item.itemName}"></div>
                                <button type="button">상품보기</button>
                            </a>
                            <div class="review_info">
                                <div class="rating">
                                    <div class="on" style="width:${review.score*20}%;"></div>
                                </div><!--// rating -->
                                <a href="${review.item.link}" class="tit">${review.subject}</a>
                                <p class="text">${op:nl2br(review.content)}</p>
                                <div class="btm">
                                    <div class="name">${review.maskUsername}</div>
                                    <div class="date">${op:date(review.createdDate)}</div>
                                </div>
                            </div><!--// review_info -->
                        </div>
                    </c:forEach>
                </div>
            </div><!--// review_slider -->
        </div><!--// review_section_list -->
    </div><!--// review_section -->
</c:if>