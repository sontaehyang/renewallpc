<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${list}" var="order" varStatus="orderIndex">
	<li>
		<div class="active_title">
			<span class="review_num">${order.orderCode}</span>
			<a href="/m/mypage/order-detail/${order.orderSequence}/${order.orderCode}" class="btn_st4 arr">상세보기</a>
		</div>
		
		<c:forEach items="${order.orderShippingInfos}" var="shipping" varStatus="shippingIndex">
			<c:forEach items="${shipping.orderItems}" var="orderItem" varStatus="i">
				<c:set var="totalItemAmount" value="${orderItem.itemAmount}" />
				<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />
				<c:forEach items="${orderItem.additionItemList}" var="addition">
					<c:set var="totalItemAmount">${totalItemAmount + addition.itemAmount}</c:set>
					<c:set var="totalSaleAmount">${totalSaleAmount + addition.saleAmount}</c:set>
				</c:forEach>

				<div class="item">
					<div class="order_img">
						<a href="/products/view/${orderItem.itemUserCode}">
							<%-- <img src="${orderItem.imageSrc}" alt="제품이미지"> --%>
							<img src="${shop:loadImageBySrc(orderItem.imageSrc, 'XS')}" alt="제품이미지">
						</a>
					</div>
					<div class="order_name">
						<a href="/products/view/${orderItem.itemUserCode}"><p class="tit">${orderItem.itemName}</p></a>
						<p class="detail">
							${ shop:viewOptionText(orderItem.options) }
							${ shop:viewAdditionOrderItemList(orderItem.additionItemList) }
						</p>

						<c:set var="orderGiftItemText" value="${shop:makeOrderGiftItemText(orderItem.orderGiftItemList)}"/>
						<c:if test="${not empty orderGiftItemText}">
							<ul class="option">
								<li>
									<span class="choice">사은품 : </span>
									<span>${orderGiftItemText}</span>
								</li>
							</ul>
						</c:if>
					</div>
					<div class="order_price">
						<p class="price">
							<span class="discount">${op:numberFormat(totalItemAmount)}원</span>
							<span class="sale_price">${op:numberFormat(totalSaleAmount)}</span>원 (${op:numberFormat(orderItem.quantity)}개)
						</p>
					</div>
				</div>
				
				<div class="shipping_detail">
					<span class="shipping_txt">${orderItem.orderStatusLabel}</span>

					<c:set var="orderItem" value="${orderItem}" scope="request"/>
					<div class="btns">
						<jsp:include page="/WEB-INF/views/mobile/i18n/mypage/order-button.jsp"></jsp:include>
					</div>
				</div>
			</c:forEach>
		</c:forEach>
	</li>
</c:forEach>