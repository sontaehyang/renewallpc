<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<style type="text/css">

.board_list_table th {
	padding : 2px;
	border: 1px solid #999999;
}
.board_list_table td {
	padding : 2px;
	border: 1px solid #999999;
}
.order_color {
	background : #f6f1d3
}

.cancel_color {
	background : #fde9fc;
}

.total-price-title {
	background-color: #f6f6f6;
}
</style>

<h1 class="popup_title">${brandName}</h1>
<div class="popup_contents">
	
	<div class="board_list">
		<table class="board_list_table">
			<thead>
				<tr>
					<th>${op:message('M01377')}</th> <!-- 일자 -->
					<th>구분</th> <!-- 구분 -->
					<th>${op:message('M01368')}</th> <!-- 주문방법 -->
					<th>${op:message('M00018')}</th> <!-- 상품명 -->
					<th>${op:message('M00019')}</th> <!-- 상품코드 -->
					<th>${op:message('M00357')}</th> <!-- 수량 -->
					<th>${op:message('M00356')}</th> <!-- 단가 -->
					<th>상품쿠폰</th> <!-- 상품쿠폰 -->
					<th>판매가</th> <!-- 판매가 -->
					<th>${op:message('M00064')}</th> <!-- 소계 -->
				</tr>
			</thead>
			<tbody>
				
				<c:set var="totalAmount" value="0" />
				<c:forEach items="${ brandDetailList }" var="base">
					<c:set var="payTotalAmount" value="0" />
					<c:set var="cancelTotalAmount" value="0" />
					<tr>  
						<td rowspan="${ base.rowspan }">${ op:date(base.key) }</td>
						<c:set var="payIndex" value="0" />
						<c:set var="index" value="0" />
						<c:forEach items="${ base.list }" var="list" varStatus="i">
						
							<c:if test ="${ list.osType == 'Admin' }">
								<c:set var="osType" value="Call"/>
							</c:if>
							<c:if test ="${ list.osType != 'Admin' }">
								<c:set var="osType" value="${ list.osType }"/>
							</c:if>
							<c:if test="${ list.orderType eq 'PAY' }">
								
								<c:set var="rowspan" value="${ fn:length(list.items) }" />
								<c:if test="${ index > 0 }"></tr><tr></c:if>
								
								<c:if test="${ payIndex == 0 }">
									<td rowspan="${ base.payRowspan + 1 }" class="order_color">결제</td> <!-- 결제 -->
								</c:if>
								
								<td rowspan="${ rowspan }">${ osType }</td>
								<c:forEach items="${ list.items }" var="item" varStatus="itemIndex">
									
									<c:if test="${ itemIndex.first == false }"></tr><tr></c:if>
									
									<td style="text-align:left;">${ item.itemName }</td>
									<td>${ item.itemUserCode }</td>
									<td class="border_left number">${ op:numberFormat(item.quantity) }</td>
									<td class="border_left number">${ op:numberFormat(item.totalItemPrice) }</td>
									<td class="border_left number">${ op:numberFormat(item.itemCouponDiscountAmount) }</td>
									<td class="border_left number">${ op:numberFormat(item.itemPay) }</td>
									
									<c:if test="${ itemIndex.first == true }">
										<td class="border_left number" rowspan="${ fn:length(list.items) }">${ op:numberFormat(list.orderPayAmount) }</td>
									</c:if>

								</c:forEach> 
								 
								<c:set var="payIndex">${ payIndex + 1 }</c:set>
								<c:set var="index">${ index + 1 }</c:set>
								<c:set var="payTotalAmount">${ payTotalAmount + list.orderPayAmount }</c:set>
							</c:if>
						</c:forEach>

						<c:if test="${ payIndex > 0 }"> 
							<c:if test="${ true }"></tr><tr></c:if>
							<td class="order_color" colspan="7" style="text-align:right; padding:5px;">결제총합</td> <!-- 결제총합 -->
							<td class="order_color number ">${ op:numberFormat(payTotalAmount) }</td>							
						</c:if>
						
						<c:set var="cancelIndex" value="0" />
						<c:forEach items="${ base.list }" var="list" varStatus="i">
						
							<c:if test ="${ list.osType == 'Admin' }">
								<c:set var="osType" value="Call"/>
							</c:if>
							<c:if test ="${ list.osType != 'Admin' }">
								<c:set var="osType" value="${ list.osType }"/>
							</c:if>
							
							<c:if test="${ list.orderType eq 'CANCEL' }">
								<c:set var="rowspan" value="${ fn:length(list.items) }" />

								<c:if test="${ index > 0 || payIndex > 0 }"></tr><tr></c:if>
								
								<c:if test="${ cancelIndex == 0 }">
									<td rowspan="${ base.cancelRowspan + 1 }" class="cancel_color">취소</td> <!-- 취소 -->
								</c:if>
								
								<td rowspan="${ rowspan }">${ osType }</td>
								<c:forEach items="${ list.items }" var="item" varStatus="itemIndex">
									
									<c:if test="${ itemIndex.first == false }"></tr><tr></c:if>
									
									<td style="text-align:left;">${ item.itemName }</td>
									<td>${ item.itemUserCode }</td>
									<td class="border_left number">${ op:numberFormat(item.quantity) }</td>
									<td class="border_left number">${ op:numberFormat(item.totalItemPrice) }</td>
									<td class="border_left number">${ op:numberFormat(item.itemCouponDiscountAmount) }</td>
									<td class="border_left number">${ op:numberFormat(item.itemPay) }</td>
									
									<c:if test="${ itemIndex.first == true }">
										<td class="border_left number" rowspan="${ fn:length(list.items) }">${ op:numberFormat(list.orderPayAmount) }</td>
									</c:if>
									
								</c:forEach>
								
								<c:set var="index">${ index + 1 }</c:set>
								<c:set var="cancelIndex">${ cancelIndex + 1 }</c:set>
								<c:set var="cancelTotalAmount">${ cancelTotalAmount + list.orderPayAmount }</c:set>
							</c:if>							
						</c:forEach>
						
						<c:if test="${ cancelIndex > 0 }">
							<c:if test="${ true }"></tr><tr></c:if>
							<td class="cancel_color" colspan="7" style="text-align:right; padding:5px;">취소총합</td> <!-- 취소총합 -->
							<td class="cancel_color number">${ op:numberFormat(cancelTotalAmount) }</td>							
						</c:if>
					</tr>
					<tr>
						<td class="total-price-title" colspan="8" style="text-align:right; padding:5px;">${ op:date(base.key) }소계</td>
						<td class="total-price-title number">${ op:numberFormat(payTotalAmount + cancelTotalAmount) }</td>
					</tr>
					
					<c:set var="totalAmount">${ totalAmount + (payTotalAmount + cancelTotalAmount) }</c:set>
					
				</c:forEach>
				<tr>
					<td class="total-price-title" colspan="9" style="text-align:right; padding:5px;">합계</td>
					<td class="total-price-title number">${ op:numberFormat(totalAmount) }</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>



<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="sd"]' , 'input[name="ed"]');
	});
</script>