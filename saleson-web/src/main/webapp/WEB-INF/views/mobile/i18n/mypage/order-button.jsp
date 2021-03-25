<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${orderItem.orderStatus == '0'}">
		<a href="javascript:Mypage.orderCancel('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence})" class="btn_st3 t_small t_lgray b_white s_small">주문취소</a>
	</c:when>
	<c:when test="${orderItem.orderStatus == '10' || orderItem.orderStatus == '20'}">
		<%-- 결제완료 / 배송준비중 --%>
		<a href="javascript:Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '1')" class="btn_st3 t_small t_lgray b_white s_small">주문취소</a>
	</c:when>
	<c:when test="${orderItem.orderStatus == '30' || orderItem.orderStatus == '35' || orderItem.orderStatus == '55' || orderItem.orderStatus == '59' || orderItem.orderStatus == '69'}">
		<%-- 배송중 / 배송완료 / 교환거절 / 반품거절 --%>
		<p class="delv">
			<c:choose>
				<c:when test="${empty orderItem.deliveryNumber}">
					<c:choose>
						<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
							퀵 배송
						</c:when>
						<c:otherwise>
							직접수령
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					${orderItem.deliveryCompanyName}
					<span>${orderItem.deliveryNumber}</span>
				</c:otherwise>
			</c:choose>
		</p>

		<c:if test="${!empty orderItem.deliveryCompanyUrl}">
			<!-- 배송추적 링크 추가 2017-04-25_seungil.lee -->
			<%-- <p><a href="javascript:orderTracking('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}');" style="height: 16px; float: left;">${orderItem.deliveryNumber}</a></p> --%>
			<a href="javascript:orderTracking('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}');" class="btn_st3 t_small t_lgray b_white s_small" title="배송추적">배송추적</a>
		</c:if>

		<c:if test="${orderItem.confirmDate == '00000000000000'}">
			<a href="javascript:Mypage.confirmPurchase('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence});" class="btn_st3 t_small t_white b_blue" title="구매확정">구매확정</a>
		</c:if>

		<a href="javascript:Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '3')" class="btn_st3 t_small t_lgray b_white s_small" title="교환신청">교환신청</a>
		<a href="javascript:Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '2')" class="btn_st3 t_small t_lgray b_white s_small" title="반품신청">반품신청</a>
	</c:when>
	<c:when test="${orderItem.orderStatus == '40' }">
		<%-- 구매확정 --%>
		<p class="delv">
			<c:choose>
				<c:when test="${empty orderItem.deliveryNumber}">
					<c:choose>
						<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
							퀵 배송
						</c:when>
						<c:otherwise>
							직접수령
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					${orderItem.deliveryCompanyName}
					<span>${orderItem.deliveryNumber}</span>
				</c:otherwise>
			</c:choose>
		</p>

		<c:if test="${!empty orderItem.deliveryCompanyUrl}">
			<!-- 배송추적 링크 추가 2017-04-25_seungil.lee -->
			<%-- <p><a href="javascript:orderTracking('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}');" style="height: 16px; float: left;">${orderItem.deliveryNumber}</a></p> --%>
			<a href="javascript:orderTracking('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}');" class="btn_st3 t_small t_lgray b_white s_small" title="배송추적">배송추적</a>
		</c:if>

		<a href="javascript:Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '3')" class="btn_st3 t_small t_lgray b_white s_small" title="교환신청">교환신청</a>
		<a href="javascript:Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '2')" class="btn_st3 t_small t_lgray b_white s_small" title="반품신청">반품신청</a>
	</c:when>
</c:choose>

<script>
	function orderTracking(url) {
		isMobileLayer = false;
		Common.popup(url, 'order-tracking', 700, 700, 1);
		isMobileLayer = true;
	}
</script>