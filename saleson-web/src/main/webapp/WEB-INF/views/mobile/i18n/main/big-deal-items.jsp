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

<c:if test="${!empty bigDealItems}">
    <div class="limited_product">
        <div class="title">
            <h3><span></span>#한정수량_득템찬스</h3>
            <a href="/m/pages/limit" class="more_btn">MORE</a>
        </div>
        <div class="limited_product_slider">
            <div class="swiper-wrapper">
                <c:forEach items="${bigDealItems}" var="item" varStatus="i">
                    <div class="swiper-slide">
                        <a href="${item.link}"${item.itemSoldOut ? ' class="sold-out"' : ''}>
                            <div class="img">
                                <img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'L')}" alt="${item.itemName}">
                            </div><!--// img -->
                            <div class="price_box">
                                <div class="leave_amount">
                                    <div class="text"><span>남은수량</span></div>
                                    <div class="number"><span class="bg_black">${!empty item.displayQuantity ? item.displayQuantity : '50'}</span><span>20</span></div>
                                </div>
                                <p class="tit">${item.itemName}</p>
                                <div class="price">
                                    <p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
                                    <c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
                                        <p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
                                    </c:if>
                                    <c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
                                        <p class="percentage"><span>${item.discountRate}</span>%</p>
                                    </c:if>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
            <div class="swiper-pagination"></div>
        </div><!--// limited_product_slider -->
    </div><!--// limited_product -->
</c:if>