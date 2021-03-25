<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<!-- 본문 -->
<div class="popup_wrap">
	<h3 class="popup_title"><span>${orderCancelApply.orderCode} - 취소 요청 정보</span></h3>
	<div class="popup_contents">
		<div class="board_write">
			<h3><span>신청 정보</span></h3>
			<form:form modelAttribute="orderCancelApply" name="orderCancelApply" action="/seller/order/cancel-request/process/action" method="post">
				<c:set var="orderItem" value="${orderCancelApply.orderItem}" />
				<form:hidden path="orderCancelApplyId" />

				<table class="board_write_table">
					<caption>${op:message('M00059')}</caption> <!-- 주문정보 --> 
					<colgroup> 						
						<col style="width:20%;" />
						<col style="width:20%;" />
						<col /> 
					</colgroup> 
					<tbody id="order_items">
						
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
							<th class="label">요청수량<br/>(주문 수량)</th>
							<td colspan="2" class="tleft">
								<div>
									${op:numberFormat(orderCancelApply.applyQuantity)}개 (${op:numberFormat(orderItem.quantity)}개)
								</div>
							</td>
						</tr>
							
						<tr>   
							<th class="label">취소 요청 사유</th> 
							<td colspan="2" class="tleft">
								<div>
									${orderCancelApply.cancelReasonText}
								</div>
							</td> 
						</tr>
						
						<tr>   
							<th class="label">취소 메모<br/>(관리자)</th>
							<td colspan="2" class="tleft">
								<div>
									<form:textarea path="cancelMemo" />
								</div>
							</td> 
						</tr>
						
						<tr>
							<th class="label">처리상태</th>
							<td colspan="2" class="tleft">
								<div>
									<form:radiobutton path="orderItem.orderStatus" value="90" label="취소신청" />
									<form:radiobutton path="orderItem.orderStatus" value="95" label="취소승인" />
									<form:radiobutton path="orderItem.orderStatus" value="20" label="취소거절" />
									<form:radiobutton path="orderItem.orderStatus" value="30" label="취소거절 + 배송처리" />
									
									<div class="cancelRefusal" <c:if test="${orderItem.orderStatus != '20'}">style="display:none"</c:if>>
										<p class="pt5">
											거절 사유 : <form:input path="cancelRefusalReasonText" maxlength="100" class="sixsix"/>
										</p>
										<div class="board_guide">
											<p class="tip">해당 내용은 사용자에게 노출되는 데이터 입니다.</p>
										</div>
									</div>
									
									<div class="cancelShipping" <c:if test="${orderCancelApply.orderStatus != '30'}">style="display:none"</c:if>>
										<p class="mb5 mt10">
											<form:select path="cancelDeliveryCompanyId" class="form-block">
												<form:option value="0">-선택-</form:option>
												<form:options items="${deliveryCompanyList}" itemValue="deliveryCompanyId" itemLabel="deliveryCompanyName" />
											</form:select>
										</p>
										<form:input path="cancelDeliveryNumber" maxlength="30" class="form-block"/>
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
$("#orderShipping").validator({
	'submitHandler' : function() {
		
		var orderStatus = $(':radio[name="orderItem.orderStatus"]:checked').val();
		if (orderStatus == '30') {
			$deliveryCompanyId= $('select[name="cancelDeliveryCompanyId"]');
			if ($deliveryCompanyId.val() == '' || $deliveryCompanyId.val() == '0') {
				alert('택배사를 선택해 주세요.');
				$deliveryCompanyId.focus();
				return false;
			}
			
			$deliveryNumber = $('input[name="cancelDeliveryNumber"]');
			if ($deliveryNumber.val() == '') {
				alert('송장번호를 입력해주세요.');
				$deliveryNumber.focus();
				return false;
			}
		} else if (orderStatus == '20') {
			$cancelRefusalReasonText = $('input[name="cancelRefusalReasonText"]');
			if ($cancelRefusalReasonText.val() == '') {
				alert('취소 거절사유를 입력해주세요.');
				$cancelRefusalReasonText.focus();
				return false;
			}
		}
	}
});

$(':radio[name="orderItem.orderStatus"]').on('change', function() {
	if ($(this).val() == '30') {
		$('.cancelShipping').show();
	} else if ($(this).val() == '20') {
		$('.cancelRefusal').show();
	} else { 
		$('.cancelRefusal').hide();
		$('.cancelShipping').hide();
	}
})

</script>