<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${!empty spotItems}">
	<div class="time_sale">
		<div class="title">
			<h3><span><img src="/content/mobile/images/common/ico_hot.png" alt=""></span>월간특가</h3>
			<a href="/m/event/spot" class="more_btn">MORE</a>
		</div>
		<div class="time_sale_slider">
			<div class="swiper-wrapper">
				<c:forEach items="${spotItems}" var="item" varStatus="i">
					<div class="swiper-slide time_sale_list">
						<a href="${item.link}" class="ty02${item.itemSoldOut ? ' sold-out' : ''}">
							<div class="ts_btm_box" data-spot-format-date="${item.spotFormatDate}">
								<ul class="ts_timer">
									<li class="ts_img"></li>
									<li class="days">0</li>
									<li class="hours">00</li>
									<li class="minutes">00</li>
									<li class="seconds">00</li>
								</ul><!--// ts_timer -->
							</div>
							<div class="img">
								<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<div class="img_percent"><span>${item.discountRate}</span><sub>%</sub></div>
								</c:if>
								<img src="${shop:loadImageBySrc(item.imageSrc, 'L')}" alt="${item.itemName}">
							</div>
							<p class="tit">${item.itemName}</p>
							<div class="price_box">
								<div class="price">
									<p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
									<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
										<p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
									</c:if>
								</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>
		</div><!-- //time_sale_slider -->
	</div><!-- //time_sale -->
</c:if>