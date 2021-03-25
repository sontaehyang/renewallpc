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

<c:if test="${!empty spotItems}">
	<p class="sec_tit">타임세일 - 오늘의 찬스<a href="/event/spot" class="btn_more">MORE ></a></p>
	<div class="ts_l">
		<c:forEach items="${spotItems}" var="item" varStatus="i" begin="0" end="0">
			<dl class="ts_box" data-spot-format-date="${item.spotFormatDate}">
				<dt class="days">0</dt>
				<dd class="hours">00</dd>
				<dd class="minutes">00</dd>
				<dd class="seconds">00</dd>
			</dl>
			<div class="imgs${item.itemSoldOut ? ' sold-out' : ''}">
				<a href="${item.link}" class="img"><img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'M')}" alt="${item.itemName}"></a>
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
			<a href="${item.link}" class="tit">${item.itemName}</a>
			<dl class="price">
				<dt>
					<b>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</b>원
					<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
						<span>${op:numberFormat(item.listPrice)}</span>
					</c:if>
				</dt>
				<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
					<dd>${item.discountRate}</dd>
				</c:if>
			</dl>
		</c:forEach>
	</div><!-- // ts_l E-- -->
	<ul class="ts_r">
		<c:forEach items="${spotItems}" var="item" varStatus="i" begin="1" end="3">
			<li>
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
				<dl class="ts_box" data-spot-format-date="${item.spotFormatDate}">
					<dt class="days">0</dt>
					<dd class="hours">00</dd>
					<dd class="minutes">00</dd>
					<dd class="seconds">00</dd>
				</dl>
				<a href="${item.link}" class="tit">${item.itemName}</a>
				<p class="price">${op:numberFormat(item.exceptUserDiscountPresentPrice)}</p>
			</li>
		</c:forEach>
	</ul>
</c:if>