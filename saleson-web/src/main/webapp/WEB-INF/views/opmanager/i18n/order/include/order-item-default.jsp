<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<table class="inner-table">
	<caption>${op:message('M00059')}</caption>
	<!-- 주문정보 -->
	<colgroup>
		<col style="width: 80px" />
		<col />
		<col style="width: 120px" />
		<col style="width: 120px" />
			<col style="width: 80px" />
			<col style="width: 80px" />
			<col style="width: 120px" />
			<col style="width: 120px" />
			<col style="width: 100px" />
	</colgroup>
	<thead>
		<tr>
			<th scope="col" class="none_left">이미지</th>
			<th scope="col" class="none_left">상품정보</th>
			<th scope="col" class="none_left">구분</th>
			<th scope="col" class="none_left">판매가</th>
			<th scope="col" class="none_left">수량</th>
			<th scope="col" class="none_left">클레임수량</th>
			<th scope="col" class="none_left">총금액</th>
			<th scope="col" class="none_left">배송비</th>
			<th scope="col" class="none_left">상태</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${order.orderShippingInfos}" var="receiver" varStatus="receiverIndex">
			<c:forEach items="${receiver.orderItems}" var="orderItem" varStatus="orderItemIndex">
				<tr>
					<td>
						<img src="${orderItem.imageSrc}" alt="${orderItem.itemName}" width="100%"/>
					</td>
					<td>
						[${orderItem.itemUserCode}] ${orderItem.itemName}
						${ shop:viewOptionText(orderItem.options) }
					</td>
					<td class="text-center">
						<c:choose>
							<c:when test="${shop:sellerId() == orderItem.sellerId}">자사</c:when>
							<c:otherwise>
								<span class="glyphicon glyphicon-user"></span>${orderItem.sellerName}
							</c:otherwise> 
						</c:choose>
					</td>
					<td class="text-right">
						${op:numberFormat(orderItem.salePrice)}원
					</td>
					<td class="text-right">
						${op:numberFormat(orderItem.quantity)}개
					</td>
					<td class="text-right">
						${op:numberFormat(orderItem.claimQuantity)}개
					</td>
					<td class="text-right">
						${op:numberFormat(orderItem.saleAmount)}원
					</td>
					<td class="text-right">
						<c:choose>
 							<c:when test="${orderItem.isShippingView == 'Y'}">
 								<c:choose>
 									<c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
 										<span style="color:red">${op:numberFormat(orderItem.orderShipping.realShipping)}원 (착불)</span>
 									</c:when>
 									<c:otherwise>
 										<c:choose>
 											<c:when test="${orderItem.orderShipping.payShipping == 0}">무료</c:when>
 											<c:otherwise>
 												${op:numberFormat(orderItem.orderShipping.payShipping)}원
 											</c:otherwise>
 										</c:choose>
 									</c:otherwise>
 								</c:choose>
 							</c:when>
 							<c:otherwise>묶음배송</c:otherwise>
 						</c:choose>
					</td>
					<td class="text-center"> 
						${orderItem.orderStatusLabel}
					</td> 
				</tr>
			</c:forEach>
		</c:forEach>
	</tbody>
</table>