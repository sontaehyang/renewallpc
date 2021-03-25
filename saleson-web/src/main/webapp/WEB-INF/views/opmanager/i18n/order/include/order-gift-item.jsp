<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>

<c:if test="${not empty orderGiftItems}">

	<h3 class="mt10"><span>주문에 포함된 사은품 내역</span></h3>
	<table class="inner-table">
		<colgroup>
			<col style="width: 60px;" />
			<col style="width: 100px;" />
			<col style="width: 100px;" />
			<col style="width: auto;" />
			<col style="width: 100px;" />
		</colgroup>
		<thead>
			<tr>
				<th class="label">순번</th>
				<th class="label">&nbsp;</th>
				<th class="label">사은품 구분</th>
				<th class="label">사은품 정보</th>
				<th class="label">상태</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderGiftItems}" var="giftItem" varStatus="i">
				<tr>
					<td class="text-center">
						<div>
							${i.count}
						</div>
					</td>
					<td class="text-center">
						<div>
							<img src="${shop:loadImageBySrc(giftItem.image, "XS")}" alt="승인 테스트" width="100%">
						</div>
					</td>
					<td class="text-center">
						<div>
							<c:choose>
								<c:when test='${giftItem.groupType == "NONE" }'>일반</c:when>
								<c:when test='${giftItem.groupType == "ORDER" }'>주문서</c:when>
								<c:when test='${giftItem.groupType == "ORDER_PRICE" }'>주문 금액</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td>
						<div>

							<c:set var="giftItemKey" value="${giftItem.orderCode}-${giftItem.orderSequence}-${giftItem.itemSequence}" />
							<c:set var="orderItemName" value="" />
							<c:set var="orderItemUserCode" value="" />

							<c:forEach items="${order.orderShippingInfos}" var="receiver" varStatus="receiverIndex">
								<c:forEach items="${receiver.orderItems}" var="orderItem" varStatus="orderItemIndex">
									<c:set var="itemKey" value="${orderItem.orderCode}-${orderItem.orderSequence}-${orderItem.itemSequence}" />

									<c:if test='${itemKey == giftItemKey}'>

										<c:set var="orderItemName" value="${orderItem.itemName}" />
										<c:set var="orderItemUserCode" value="${orderItem.itemUserCode}" />

									</c:if>

								</c:forEach>
							</c:forEach>

							[${giftItem.giftItemCode}] ${giftItem.giftItemName}

							<c:choose>
								<c:when test='${not empty orderItemName and not empty orderItemUserCode}'>
									<br/>

									상품 : [${orderItemUserCode}] ${orderItemName}
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
							</c:choose>

						</div>
					</td>
					<td class="text-center">
						<div>
							<c:choose>
								<c:when test='${giftItem.giftOrderStatus == "NORMAL" }'>정상</c:when>
								<c:when test='${giftItem.giftOrderStatus == "RETURN" }'>반품</c:when>
								<c:when test='${giftItem.giftOrderStatus == "CANCEL" }'>취소</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>


