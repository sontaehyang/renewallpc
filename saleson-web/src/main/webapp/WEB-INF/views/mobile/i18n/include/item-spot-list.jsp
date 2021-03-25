<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${list}" var="item" varStatus="i">
	
	<li>
		<a href="${item.link}">
			<div class="product">
				<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
					<div class="product_sale">
						<p>${item.discountRate}<span>%</span></p>
					</div>
				</c:if>
				<div class="product_img">
					<img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'M')}" alt="${item.itemName}">
				</div>
				<div class="product_info">
					<div class="label_area">
						<c:forEach items="${item.itemLabels}" var="label">
							<span><img src="${label.imageSrc}" alt="${label.description}"></span>
						</c:forEach>
					</div> 								
					<p class="tit">${item.itemName}</p>
 					<div class="price_box">
		 				<div class="price">
		 					<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
								<p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
							</c:if>
							<p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
						</div>
					</div>
				</div>
			</div>
			<div class="review">
				<%--<p>리뷰<span>(${item.reviewCount})</span></p>--%>
				<c:if test="${item.itemSoldOutFlag == 'Y'}">
					<span class="soldout_ico"><img src="/content/mobile/images/common/product_soldout_ico.png" alt="SOLD OUT"></span>
				</c:if>
			</div>
			<div class="date cf">
				<div class="det">
					<p>판매요일 :
 			 			<c:forEach items="${item.spotWeekDayList}" var="code">
							<c:if test="${code.detail == '1'}">
								<span>${fn:substring(code.label, 0, 1)}</span>
							</c:if>
						</c:forEach>
	 			 	</p>
	 			 	<p>판매시간 : <span>${op:timeFormat(item.spotStartTime)}~${op:timeFormat(item.spotEndTime)}</span> </p>
				</div>							
				
				<span class="d_day">
					<c:choose>
						<c:when test="${item.spotEndDDay == 0}">오늘 마감</c:when>
						<c:otherwise>마감임박 D-${item.spotEndDDay}</c:otherwise>
					</c:choose>
				</span>
			</div>								
		</a> 	
	</li>
</c:forEach>	
						
						