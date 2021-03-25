<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${not empty mdItems}">
	<div class="title">
		<h3><span>MD Choice!</span> 추천상품</h3>
		<p class="page_num">
			<%--<span class="active">0</span>/<span class="total">0</span>--%>
		</p>
	</div>
	<div class="md_choice_slider">
		<div class="swiper-wrapper">
			<c:forEach items="${mdItems}" var="item" varStatus="i">
				<div class="swiper-slide">
					<a href="${item.link}">
						<div class="img">
							<img src="${ shop:loadImageBySrc(item.imageSrc, 'L') }" alt="md_choice_img01">
								<%-- 											<img src="/thumbnail?src=${item.imageSrc}&size=200" alt="md_choice_img01"> --%>
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
	</div><!-- //md_choice_slider -->
</c:if>
