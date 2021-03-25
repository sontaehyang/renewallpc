<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${orderItem.orderStatus == '0'}">
		<%-- 입금대기 --%>
		<button type="submit" class="btn btn-s btn-default" title="주문취소" onclick="Mypage.orderCancel('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence})">주문취소</button>
	</c:when>
	<c:when test="${orderItem.orderStatus == '10' || orderItem.orderStatus == '20'}">
		<%-- 결제완료 / 배송준비중 --%>
		<button type="submit" class="btn btn-s btn-default" title="주문취소" onclick="Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '1')">주문취소</button>
	</c:when>
	<c:when test="${orderItem.orderStatus == '30' || orderItem.orderStatus == '35' || orderItem.orderStatus == '55' || orderItem.orderStatus == '59' || orderItem.orderStatus == '69'}">
		<%-- 배송중 / 배송완료 / 교환거절 / 반품거절 --%>
		<c:choose>
			<c:when test="${empty orderItem.deliveryNumber}">
				<p class="mt5">
					<c:choose>
						<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
							퀵 배송
						</c:when>
						<c:otherwise>
							직접수령
						</c:otherwise>
					</c:choose>
				</p>
			</c:when>
			<c:otherwise>
				<p class="mt5">${orderItem.deliveryCompanyName}</p>
				<!-- 배송추적 링크 추가 2017-04-25_seungil.lee -->
				<c:choose>
					<c:when test='${!empty orderItem.deliveryCompanyUrl}'>
						<%-- <p><a href="javascript:Common.popup('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}', '배송추적', '', '', 1);">${orderItem.deliveryNumber}</a></p> --%>
						<p>${orderItem.deliveryNumber}</p>
						<button type="button" class="btn btn-s btn-default" title="배송추적" onclick="javascript:Common.popup('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}', '배송추적', '', '', 1);">배송추적</button>
					</c:when>
					<c:otherwise>
						<p>${orderItem.deliveryNumber}</p>
					</c:otherwise>
				</c:choose>	
			</c:otherwise>
		</c:choose>
		
		<c:if test="${orderItem.confirmDate == '00000000000000'}">
				<button type="submit" class="btn btn-s btn-submit" title="구매확정" onclick="Mypage.confirmPurchase('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence})">구매확정</button>
				<button type="submit" class="btn btn-s btn-normal" title="교환신청" onclick="Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '3')">교환신청</button><br>
				<button type="submit" class="btn btn-s btn-normal" title="반품신청" onclick="Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '2')">반품신청</button>

<%-- 				<button type="submit" class="btn btn-s btn-default" title="Escrow구매확인" onclick="escrowConfirm('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence})">Escrow구매확인</button>		 --%>
		</c:if>
	</c:when>
	<c:when test="${orderItem.orderStatus == '40' }">
		<%-- 구매확정 --%>
		
		<c:choose>
			<c:when test="${empty orderItem.deliveryNumber}">
				<p class="mt5">
					<c:choose>
						<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
							퀵 배송
						</c:when>
						<c:otherwise>
							직접수령
						</c:otherwise>
					</c:choose>
				</p>
			</c:when>
			<c:otherwise>
				<p class="mt5">${orderItem.deliveryCompanyName}</p>
				<!-- 배송추적 링크 추가 2017-04-25_seungil.lee -->
				<c:choose>
					<c:when test='${!empty orderItem.deliveryCompanyUrl}'>
						<%-- <p><a href="javascript:Common.popup('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}', '배송추적', '', '', 1);">${orderItem.deliveryNumber}</a></p> --%>
						<p>${orderItem.deliveryNumber}</p>
						<button type="button" class="btn btn-s btn-default" title="배송추적" onclick="javascript:Common.popup('${orderItem.deliveryCompanyUrl}${orderItem.deliveryNumber}', '배송추적', '', '', 1);">배송추적</button>
					</c:when>
					<c:otherwise>
						<p>${orderItem.deliveryNumber}</p>
					</c:otherwise>
				</c:choose>	
			</c:otherwise>
		</c:choose>
		
		
		<c:forEach var="nonreview" items="${nonregisteredReviewList}">
			<c:if test="${orderItem.orderCode == nonreview.orderCode}">
				<c:if test="${orderItem.orderSequence == nonreview.orderSequence}">
					<c:if test="${orderItem.itemSequence == nonreview.itemSequence}">
						<button type="submit" class="btn btn-s btn-default" title="상품평작성" onclick="itemReview('${orderItem.orderCode}','${orderItem.itemUserCode}')">상품평작성</button>
					</c:if>
				</c:if>
			</c:if>
		</c:forEach>
		
		<button type="submit" class="btn btn-s btn-normal" title="교환신청" onclick="Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '3')">교환신청</button><br>
		<button type="submit" class="btn btn-s btn-normal" title="반품신청" onclick="Mypage.claimApply('${orderItem.orderCode}', ${orderItem.orderSequence}, ${orderItem.itemSequence}, '2')">반품신청</button>
	</c:when>
</c:choose>

<script>
function guideMessage(){
	alert('※ 에스크로 결제 시 교환, 반품 신청 전 Escrow구매확인을 통해\n구매거절을 선택하십시오.\n(배송완료 이후 일정기간 구매거절을 하지 않을경우 자동으로 구매가 확정됩니다.)');
}

function escrowConfirm(orderCode, orderSequence, itemSequence) {
	if (confirm("해당 상품을 구매확정 하시겠습니까?")) {
		iniEscrowConfirm(orderCode, orderSequence, itemSequence);				
	}
}
</script>