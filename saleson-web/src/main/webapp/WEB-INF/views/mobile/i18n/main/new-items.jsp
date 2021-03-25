<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${not empty newItems}">
	<div class="title">
		<h3><span>NEW!</span>신상품</h3>
		<a href="/m/event/new" class="more_btn">더보기</a>
	</div>
	<div class="new_product_slider">
		<div class="swiper-wrapper">

			<c:forEach items="${newItems}" var="item" varStatus="i">
				<div class="swiper-slide">
					<a href="${item.link}">
						<div class="img">
							<img src="${ shop:loadImageBySrc(item.imageSrc, 'L')}" alt="new_product_img01">
						</div>
						<p class="tit">${item.itemName}</p>
						<div class="price_box">
							<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
								<p class="percentage">${item.discountRate}<span>%</span></p>
							</c:if>
							<div class="price">
								<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
								</c:if>
								<p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
							</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
	<!-- //new_product_slider -->
	<div class="new_product_next"></div>
	<div class="new_product_prev"></div>
</c:if>