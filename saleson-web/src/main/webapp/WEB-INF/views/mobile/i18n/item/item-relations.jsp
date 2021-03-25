<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${not empty itemRelations}">
	<div class="inner">
		<h4>이 상품은 어때요?</h4>
		<div class="relation_slider">
			<div class="swiper-wrapper">
				<c:forEach items="${itemRelations}" var="list" varStatus="i">
					<div class="swiper-slide">
						<a href="${list.item.link}" class="${list.item.itemSoldOut ? 'sold-out' : ''}">
							<div class="img">
								<img src="${shop:loadImageBySrc(list.item.imageSrc, 'S')}" alt="${list.item.itemName}">
							</div>
							<div class="txt">
								<p class="name">${list.item.itemName}</p>
								<div class="price_wrap">
									<c:if test="${list.item.totalDiscountAmount > 0 && list.item.discountRate > 0}">
										<p class="sale_price"><span>${op:numberFormat(list.item.listPrice)}</span>원</p>
									</c:if>
									<p class="price"><span>${op:numberFormat(list.item.exceptUserDiscountPresentPrice)}</span>원</p>
									<c:if test="${list.item.totalDiscountAmount > 0 && list.item.discountRate > 0}">
										<p class="percentage"><span>${list.item.discountRate}</span><sub>%</sub></p>
									</c:if>
								</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>
			<div class="swiper-pagination"></div>
		</div>
	</div>
</c:if>


