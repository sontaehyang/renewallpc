<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<style>
.table_btn {position: absolute; top: 77px; right: 18px;}
 li {line-height:150%; }
</style>

<div class="popup_wrap">
	<h1 class="popup_title">환불 금액 확인하기</h1> <!-- 결제정보 수정-->
	<div class="popup_contents">
		
		<form id="return-process-form" method="post">
				
			<div style="display:none">
				<input type="hidden" name="isError" value="${isError }" />
				<input type="hidden" name="errorMessage" value="${errorMessage }" />
				<input type="hidden" name="returnAmount" value="${returnAmount}" />
				<input type="hidden" name="orderCode" value="${order.orderCode}" />
				<input type="hidden" name="orderSequence" value="${order.orderSequence}" />
				<c:forEach items="${applys}" var="apply">
					<c:set var="orderItem" value="${apply.orderItem}" />
					<input type="hidden" name="returnIds" value="${apply.claimCode}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].itemSequence" value="${orderItem.itemSequence}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].claimCode" value="${apply.claimCode}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].orderSequence" value="${orderItem.orderSequence}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].orderCode" value="${orderItem.orderCode}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].shipmentReturnSellerId" value="${apply.shipmentReturnSellerId}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReason" value="${apply.returnReason}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReasonText" value="${apply.returnReasonText}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReasonDetail" value="${apply.returnReasonDetail}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnShippingCompanyName" value="${apply.returnShippingCompanyName}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnShippingNumber" value="${apply.returnShippingNumber}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveSido" value="${apply.returnReserveSido}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveSigungu" value="${apply.returnReserveSigungu}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveEupmyeondong" value="${apply.returnReserveEupmyeondong}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveName" value="${apply.returnReserveName}" />			
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveZipcode" value="${apply.returnReserveZipcode}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveAddress" value="${apply.returnReserveAddress}" /><br/>
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveAddress2" value="${apply.returnReserveAddress2}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReservePhone" value="${apply.returnReservePhone}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveMobile" value="${apply.returnReserveMobile}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].claimStatus" value="${apply.claimStatus}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnRefusalReasonText" value="${apply.returnRefusalReasonText}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnShippingAskType" value="${apply.returnShippingAskType}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].userId" value="${orderItem.userId}" />
					<input type="hidden" name="returnApplyMap[${apply.claimCode}].escrowStatus" value="${orderItem.escrowStatus}" />
				</c:forEach>
			</div>
			
			<h3 class="mt10">환불 / 추가 내역</h3>
			<table class="inner-table">
				<caption>table list</caption>
				<colgroup>
					<col />
					<col style="width: 180px" />
					<col style="width: 180px" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="none_left">내용</th>
						<th scope="col" class="none_left">구분</th>
						<th scope="col" class="none_left">금액</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>상품 환불 금액</td>
						<td class="text-center">환불</td>
						<td class="text-right">${op:numberFormat(itemReturnAmount)}원</td>
					</tr>
					
					<c:set var="index">0</c:set>
					<c:forEach items="${addPayments}" var="item">
						<tr>
							<td>${item.subject}</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${item.addPaymentType == '1'}">추가</c:when>
									<c:otherwise>환불</c:otherwise>
								</c:choose>
							</td>
							<td class="text-right">
								<c:choose>
									<c:when test="${item.addPaymentType == '1'}">
										<c:choose>
											<c:when test="${returnAmount - addAmount <= 0}">
												${op:numberFormat(item.amount)}원
											</c:when>
											<c:otherwise>
											
												<c:choose>
													<c:when test="${loginSellerId == item.sellerId}">
														<input type="text" class="_number_comma text-right add-amount" name="addPayments[${index}].amount" value="${item.amount}" />원	
													</c:when>
													<c:otherwise>
														${item.amount}
														<input type="hidden" name="addPayments[${index}].amount" value="${item.amount}" />				
													</c:otherwise>
												</c:choose>
											
												<input type="hidden" name="addPayments[${index}].addPaymentType" value="${item.addPaymentType}" />
												<input type="hidden" name="addPayments[${index}].subject" value="${item.subject}" />
												<input type="hidden" name="addPayments[${index}].issueCode" value="${item.issueCode}" />
												<input type="hidden" name="addPayments[${index}].sellerId" value="${item.sellerId}" />
												
												<c:set var="index">${index + 1}</c:set>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										${op:numberFormat(item.amount)}원
										
										<input type="hidden" name="addPayments[${index}].amount" value="${item.amount}" />
										<input type="hidden" name="addPayments[${index}].addPaymentType" value="${item.addPaymentType}" />
										<input type="hidden" name="addPayments[${index}].subject" value="${item.subject}" />
										<input type="hidden" name="addPayments[${index}].issueCode" value="${item.issueCode}" />
										<input type="hidden" name="addPayments[${index}].sellerId" value="${item.sellerId}" />
										
										<c:set var="index">${index + 1}</c:set>
										
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
			<h3 class="mt10">환불 정보</h3>
			<table class="inner-table">
				<caption>table list</caption>
				<thead>
					<tr>
						<th scope="col" class="none_left">환불금 총액</th>
						<th scope="col" class="none_left">추가금 총액</th>
						<th scope="col" class="none_left">고객 환급금</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-right">${op:numberFormat(returnAmount)}원</td>
						<td class="text-right">
							<span id="totalAddAmount">${op:numberFormat(addAmount)}</span>원
							<c:if test="${returnAmount - addAmount <= 0}">
								<p>환불금액이 추가금보다 작습니다. 추가금은 별도 청구 바랍니다.</p>
							</c:if>
						</td>
						<td class="text-right">
							<c:choose>
								<c:when test="${returnAmount - addAmount <= 0}">
									${op:numberFormat(returnAmount)}원
								</c:when>
								<c:otherwise>
									<span id="totalReturnAmount">${op:numberFormat(returnAmount - addAmount)}</span>원
									<c:if test="${addAmount > 0}">
									<p>	
										<label>
											<input type="checkbox" name="separateCharges" value="1" onclick="setSeparateCharges()" 
												data-return-amount="${returnAmount}" data-add-amount="${addAmount}" />추가금 별도 청구
										</label>
									</p>
									</c:if>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>					
				</tbody>
			</table>		
			<div class="popup_btns">
				<button type="submit" class="btn btn-active">처리하기</button> 
				<button type="button" class="btn btn-default" onclick="Shop.closeOrderLayer('return')">취소하기</button> 
			</div>
		</form>
		<a href="javascript:Shop.closeOrderLayer('return')" class="popup_close">창 닫기</a>
	</div>
</div>