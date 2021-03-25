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
		<form id="changePaymentForm" method="post" action="/opmanager/order/${pageType}/change-pay/process" >
			<input type="hidden" name="orderCode" value="${order.orderCode}"/>
			<input type="hidden" name="orderSequence" value="${order.orderSequence}"/>
			
			<h1 class="popup_title">결제정보 수정</h1> <!-- 결제정보 수정-->
			<div class="popup_contents">
				
				<h3>결제 정보</h3>
				<table class="inner-table">
					<caption>table list</caption>
					<thead>
						<tr>
							<th scope="col" class="none_left">총 상품금액</th>
							<th scope="col" class="none_left">총 배송비</th>
							<th scope="col" class="none_left">총 결제금액</th>
							<th scope="col" class="none_left">결제 예정금액</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-right">${op:numberFormat(order.itemTotalAmount)}원</td>
							<td class="text-right">${op:numberFormat(order.shippingTotalAmount)}원</td>
							<td class="text-right">${op:numberFormat(order.payAmount)}원</td>
							<td class="text-right">
								<c:choose>
                                    <c:when test="${order.postPayAmount > 0}">
                                        <span style="color:red">${op:numberFormat(order.postPayAmount)}원</span>
                                    </c:when>
									<c:otherwise>없음</c:otherwise>
								</c:choose>
							</td>
						</tr>					
					</tbody>
				</table>
				
				<c:set var="orderPayments" scope="request" value="${order.orderPayments}" />
				<c:set var="pageType" scope="request" value="" />
				<jsp:include page="../include/change-payment.jsp" />

				<div id="buttons" class="tex_c mt20">
					<button type="submit" class="btn btn-active">저장</button>	
				</div>
			</div>	
		</form>
		<a href="#" class="popup_close">창 닫기</a>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$('#changePaymentForm').validator(function() {
			
			var isError = false;
			$.each($('input.payAmounts'), function(){
				
				if ($(this).val() == '') {
					$(this).val('0');
				} 
				
				var remainingAmount = Number($(this).data('remainingAmount'));
				if (remainingAmount > 0) {
					var cancelAmount = Number($(this).val());
					if (cancelAmount > remainingAmount) {
						isError = true;
						alert('취소 가능 금액을 확인해 주세요.');
						return false;
					}
				}
			});
			
			if (isError) {
				return false;
			}
		}) 
	})
</script>