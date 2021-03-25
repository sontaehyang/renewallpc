<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<c:if test="${isSellerPage == false}">
	<div class="board_list">
		<h3 class="mt10">결제정보
			<a href="javascript:Manager.payChanges('${pageType}', '${order.orderSequence}', '${order.orderCode}')">
				<button type="button" class="btn btn-dark-gray btn-sm"><span>결제정보변경</span></button>
			</a>
		</h3>
		<table class="inner-table">
			<caption>table list</caption>
			<thead>
				<tr>
					<th scope="col" class="none_left">상품금액</th>
					<th scope="col" class="none_left">할인금액</th>
					<th scope="col" class="none_left">배송비</th>
					<th scope="col" class="none_left">총 결제금액</th>

					<c:if test='${order.payAmount > 0}'>
						<th scope="col" class="none_left" style="background: #ddd">결제완료금액</th>
					</c:if>
					<c:if test='${order.cancelAmount > 0}'>
						<th scope="col" class="none_left" style="background: #ddd">취소금액</th>
					</c:if>
					<c:if test='${order.postPayAmount > 0 && !order.allItemsCanceled}'>
						<th scope="col" class="none_left" style="background: #ddd">결제 예정금액</th>
					</c:if>

				</tr>
			</thead>
			<tbody>
				<tr>
					<c:set var="totalItemAmount" value="${order.totalItemAmount}"></c:set>
					<c:set var="totalDiscountAmount" value="${order.totalDiscountAmount}"></c:set>

					<c:forEach items="${orderItem.additionItemList}" var="addition">
						<c:set var="totalItemAmount" value="${totalItemAmount + addition.totalItemAmount}"></c:set>
						<c:set var="totalDiscountAmount" value="${totalDiscountAmount + addition.totalDiscountAmount}"></c:set>
					</c:forEach>

					<td class="text-right">${op:numberFormat(totalItemAmount)}원</td>
					<td class="text-right">${op:numberFormat(totalDiscountAmount)}원</td>
					<td class="text-right">${op:numberFormat(order.totalShippingAmount)}원</td>
					<td class="text-right" style="font-weight: bold; color:#222;">${op:numberFormat(order.totalOrderAmount)}원</td>

					<c:if test='${order.payAmount > 0}'>
						<td class="text-right">${op:numberFormat(order.payAmount)}원</td>
					</c:if>

					<c:if test='${order.cancelAmount > 0}'>
						<td class="text-right">${op:numberFormat(order.cancelAmount)}원</td>
					</c:if>

					<c:if test='${order.postPayAmount > 0 && !order.allItemsCanceled}'>
						<td class="text-right"><span style="color:red">${op:numberFormat(order.postPayAmount)}원</span></td>
					</c:if>
				</tr>
			</tbody>
		</table>

		<h3 class="mt30">결제내역</h3>
		<table class="inner-table">
			<caption>table list</caption>
			<colgroup>
 				<col style="width: 150px" />
 				<col style="width: 150px" />
 				<col style="width: 100px" />
 				<col style="" />
 				<col style="width: 150px" />
			    </colgroup>
			<thead>
				<tr>
					<th scope="col" class="none_left">결제방식</th>
					<th scope="col" class="none_left">결제금액</th>
					<th scope="col" class="none_left">결제상태</th>
					<th scope="col" class="none_left">결제정보</th>
					<th scope="col" class="none_left">결제일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${order.orderPayments}" var="payment">
					<c:if test="${payment.amount > 0 || payment.cancelAmount > 0 || payment.remainingAmount > 0}">
						<c:set var="unit">원</c:set>

						<c:choose>
							<c:when test="${payment.approvalType == 'point'}">
								<c:set var="unit">P</c:set>
							</c:when>
						</c:choose>

						<tr>
							<td class="text-center">
                                <c:choose>
                                    <c:when test="${payment.refundFlag == 'Y'}">
                                        환불 (은행)
                                    </c:when>
                                    <c:otherwise>
                                        ${payment.approvalTypeLabel}
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${order.escrowStatus == 'Y' && payment.approvalType != 'point'}">
									(에스크로)
								</c:if>
							</td>
							<td class="text-right">
								<c:choose>
									<c:when test="${payment.paymentType == '1'}">
										${op:numberFormat(payment.amount)}${unit}
										<c:if test="${payment.amount != payment.remainingAmount && payment.remainingAmount > 0}">
											<br/><strong>(잔여액 : ${op:numberFormat(payment.remainingAmount)}${unit})</strong>
										</c:if>
									</c:when>
									<c:otherwise>${op:numberFormat(payment.cancelAmount)}${unit}</c:otherwise>
								</c:choose>

							</td>
							<td class="text-center">
								<c:choose>
                                    <c:when test="${payment.refundFlag == 'Y'}">환불완료</c:when>
									<c:when test="${empty payment.payDate}">미결</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${payment.paymentType == '1'}">결제완료</c:when>
											<c:otherwise>결제취소</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
                                    <c:when test="${payment.refundFlag == 'Y'}">
                                        ${payment.paymentSummary}
                                    </c:when>
									<c:when test='${order.allItemsCanceled}'>
										<strong>입금전 주문취소</strong>
									</c:when>
									<c:otherwise>
										${payment.payInfo}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="text-center">
								${op:datetime(payment.payDate)}
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
        <c:if test="${!empty cashbillIssues}">
            <h3 class="mt30">현금영수증 신청 내역</h3>
            <div>
                <table class="inner-table">
                    <caption>table list</caption>
                    <thead>
                    <tr>
                        <th scope="col" class="none_left">발행구분</th>
                        <th scope="col" class="none_left">과세구분</th>
                        <th scope="col" class="none_left">신청번호</th>
                        <th scope="col" class="none_left">신청자명</th>
                        <th scope="col" class="none_left">금액</th>
                        <th scope="col" class="none_left">상태</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cashbillIssues}" var="cashbillIssue">
                        <tr>
                            <td class="text-center">${cashbillIssue.cashbill.cashbillType.title}</td>
                            <td class="text-center">${cashbillIssue.taxType.title}</td>
                            <td class="text-center">${cashbillIssue.cashbill.cashbillCode}</td>
                            <td class="text-center">${cashbillIssue.cashbill.customerName}</td>
                            <td class="text-center">${op:numberFormat(cashbillIssue.amount)}원</td>
                            <td class="text-center">${cashbillIssue.cashbillStatus.title}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
	</div>
</c:if>

<h3 class="mt30"><span>배송 정보</span></h3>
<div style="border:1px solid #ccc; border-top: 1px solid #666;" >
	<div id="order-info-area">
		<c:set var="viewType" scope="request">info</c:set>
		<jsp:include page="../include/order-shipping-info.jsp" />
	</div>
</div>