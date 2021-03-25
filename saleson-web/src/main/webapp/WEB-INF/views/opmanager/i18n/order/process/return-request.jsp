<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">
.popup_contents .tip {
	background: #f8f8f8;
	border: 1px solid #c9c9c9;
	padding:5px;
}
span.small_text {
	font-size:9px;
	color:#818181;
} 

</style>

<!-- 본문 -->
<div class="popup_wrap">
	<h3 class="popup_title"><span>${orderReturnApply.orderItem.orderCode} - 반품 요청 정보</span></h3>
	<div class="popup_contents">
		<div class="board_write">
			<h3><span>신청 정보</span></h3>
			<form:form modelAttribute="orderReturnApply" name="orderReturnApply" action="/opmanager/order/return-request/process/action" method="post">
				<c:set var="orderItem" value="${orderReturnApply.orderItem}" />
				<form:hidden path="orderItem.orderItemId" />
				<table class="board_write_table">
					<caption>${op:message('M00059')}</caption> <!-- 주문정보 --> 
					<colgroup> 						
						<col style="width:20%;" />
						<col style="width:20%;" />
						<col /> 
					</colgroup> 
					<tbody id="order_items">
					
						
					 	<tr>   
							<th class="label">반품요청일</th>
							<td colspan="2" class="tleft">
								<div>
									${op:datetime(orderItem.exchangeRequestDate)}
								</div>
							</td> 
						</tr>
					 
						<tr>   
							<th class="label">${op:message('M00018')}</th> <!-- 상품명 -->
							<td colspan="2" class="tleft">
								<div>
									${orderItem.itemName} [${orderItem.itemUserCode}]
									${shop:viewOptionText(orderItem.options)}
								</div>
							</td> 
						</tr>
						
						<tr>   
							<th class="label">반품 요청 사유</th>
							<td colspan="2" class="tleft">
								<div>
									${orderReturnApply.returnReasonText}
								</div>
							</td>
						</tr>
						<tr>   
							<th class="label">회수 상태</th>
							<td colspan="2" class="tleft">
								<div>
									<c:choose>
										<c:when test="${orderReturnApply.returnShippingStartFlag == 'Y'}">
											택배사 : ${orderReturnApply.returnShippingCompanyName}, 송장번호 : ${orderReturnApply.returnShippingNumber}<br/>
											(해당 배송사로 고객이 <strong>${op:date(orderReturnApply.returnShippingStartDate)}일</strong>에 발송 하였습니다.)
										</c:when>
										<c:otherwise>
											지정택배사 <strong>${orderItem.deliveryCompanyName}</strong> 으로 회수신청 하였습니다.
										</c:otherwise>
									</c:choose>
								</div>
							</td> 
						</tr> 
						<tr>
							<th class="label">반송 택배비</th>
							<td colspan="2" class="tleft">
								<div>
									<form:input path="returnRealShipping" />원
								</div>
							</td> 
						</tr>
						<tr>   
							<th class="label">반품 메모(관리자)</th>
							<td colspan="2" class="tleft">
								<div>
									<form:textarea path="returnMemo" />
								</div>
							</td> 
						</tr>
						
						<tr>
							<th class="label">처리상태</th>
							<td colspan="2" class="tleft">
								<div>
									<form:radiobutton path="orderItem.orderItemStatus" value="40" label="반품신청" />
									<form:radiobutton path="orderItem.orderItemStatus" value="41" label="반품상품 회수중" />
									<form:radiobutton path="orderItem.orderItemStatus" value="45" label="반품승인 대기" />
									<form:radiobutton path="orderItem.orderItemStatus" value="10" label="반품거절" />
									
									<div class="returnRefusal" <c:if test="${orderItem.orderItemStatus != '10'}">style="display:none"</c:if>>
										<p class="pt5">
											거절 사유 : <form:input path="returnRefusalReasonText" maxlength="100" class="sixsix"/>
										</p>
										<div class="board_guide">
											<p class="tip">해당 내용은 사용자에게 노출되는 데이터 입니다.</p>
										</div>
									</div>
								</div>  
							</td>
						</tr>
						
					</tbody> 
				</table> 	
				
				<div id="buttons" class="tex_c mt20">
					<button type="submit" class="btn btn-active">저장</button>
					<button type="button" class="btn btn-default" onclick="self.close()">취소</button>
				</div>
				
			</form:form>
			<div class="popup_btns">
				<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"> 
$(function(){
	$("#orderItem").validator({
		'submitHandler' : function() {
			
			var orderItemStatus = $(':radio[name="orderItem.orderItemStatus"]:checked').val();
			if (orderItemStatus == '10') {
				
				$returnRefusalReasonText = $('input[name="returnRefusalReasonText"]');
				if ($returnRefusalReasonText.val() == '') {
					alert('환불 거절사유를 입력해주세요.');
					$returnRefusalReasonText.focus();
					return false;
				}
			}
		}
	});
	
	$(':radio[name="orderItem.orderItemStatus"]').on('change', function() {
		if ($(this).val() == '10') {
			$('.returnRefusal').show();
		} else {
			$('.returnRefusal').hide();
		}
	});
})

</script>