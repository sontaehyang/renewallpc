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
    <p class="sec_tit">한정수량! 득템의 기쁨<a href="/pages/limit" class="btn_more">MORE ></a></p>
    <dl class="ms0202_list">
        <c:forEach items="${bigDealItems}" var="item" varStatus="i" begin="0" end="0">
            <dt>
                <div class="imgs${item.itemSoldOut ? ' sold-out' : ''}">
                    <a href="${item.link}" class="img"><img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'M')}" alt="${item.itemName}"></a>
                    <p class="ov_btns">
                        <a href="${item.link}" class="blank_btn" target="_blank">새창 열기</a>
                        <c:if test="${!item.itemSoldOut}">
                            <a href="javascript:void(0);" onclick="Shop.addToWishList('${item.itemId}', '${item.orderMinQuantity}', '${requestContext.userLogin}', '${item.link}', '0', '${item.itemType}'); Shop.toCollectionScript('wishlist' , '${item.itemCode}');" class="wish_btn">관심상품 담기</a>
                            <c:if test="${item.itemOptionFlag == 'N'}">
                                <a href="javascript:void(0);" onclick="Shop.addToCart('${item.itemId}', '${item.orderMinQuantity}', '${item.nonmemberOrderType}', '${requestContext.userLogin}', '${item.link}','${item.itemType}'); Shop.toCollectionScript('cart' , '${item.itemCode}');" class="cart_btn">장바구니 담기</a>
                            </c:if>
                        </c:if>
                    </p><!--// ov_btns -->
                </div><!--// imgs -->
                <div class="mid"><p class="count"><b>${!empty item.displayQuantity ? item.displayQuantity : '50'}</b>20</p></div>
                <a href="${item.link}" class="tit">${item.itemName}</a>
                <dl class="btm">
                    <dt>
                        <b>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</b>원
                        <c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
                            <span>${op:numberFormat(item.listPrice)}</span>
                        </c:if>
                    </dt>
                    <c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
                        <dd class="sale">${item.discountRate}</dd>
                    </c:if>
                </dl>
            </dt>
        </c:forEach>
        <c:forEach items="${bigDealItems}" var="item" varStatus="i" begin="1" end="4">
            <dd>
                <div class="imgs${item.itemSoldOut ? ' sold-out' : ''}">
                    <a href="${item.link}" class="img"><img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'XS')}" alt="${item.itemName}"></a>
                    <p class="ov_btns">
                        <a href="${item.link}" class="blank_btn" target="_blank">새창 열기</a>
                        <c:if test="${!item.itemSoldOut}">
                            <a href="javascript:void(0);" onclick="Shop.addToWishList('${item.itemId}', '${item.orderMinQuantity}', '${requestContext.userLogin}', '${item.link}', '0', '${item.itemType}')" class="wish_btn">관심상품 담기</a>
                            <c:if test="${item.itemOptionFlag == 'N'}">
                                <a href="javascript:void(0);" onclick="Shop.addToCart('${item.itemId}', '${item.orderMinQuantity}', '${item.nonmemberOrderType}', '${requestContext.userLogin}', '${item.link}','${item.itemType}')" class="cart_btn">장바구니 담기</a>
                            </c:if>
                        </c:if>
                    </p><!--// ov_btns -->
                </div><!--// imgs -->
                <div class="top">
                    <c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
                        <span class="sale">${item.discountRate}</span>
                    </c:if>
                    <p class="count"><b>${!empty item.displayQuantity ? item.displayQuantity : '50'}</b>20</p>
                </div>
                <a href="${item.link}" class="tit">${item.itemName}</a>
                <p class="price">${op:numberFormat(item.exceptUserDiscountPresentPrice)}</p>
            </dd>
        </c:forEach>
    </dl><!--// ms0202_list -->
</c:if>