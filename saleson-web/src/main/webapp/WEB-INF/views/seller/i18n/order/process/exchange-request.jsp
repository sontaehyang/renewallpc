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
	<h3 class="popup_title"><span>${orderItem.orderCode} - 교환 요청 정보</span></h3>
	<div class="popup_contents">
		<div class="board_write">
			<h3><span>신청 정보</span></h3>
			<form:form modelAttribute="orderItem" name="orderItem" action="/seller/order/exchange-request/process/action" method="post">
				<form:hidden path="orderItemId" />
				<table class="board_write_table">
					<caption>${op:message('M00059')}</caption> <!-- 주문정보 --> 
					<colgroup> 						
						<col style="width:20%;" />
						<col style="width:20%;" />
						<col /> 
					</colgroup> 
					<tbody id="order_items">
					
						
					 	<tr>   
							<th class="label">교환요청일</th>
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
							<th class="label" rowspan="3">교환 배송지</th>
							<th class="label">받는사람</th>
							<td class="tleft">
								<div>
									${orderItem.orderExchangeApply.exchangeReceiveName}
								</div>
							</td> 
						</tr>
						
						<tr>   
							<th class="label">전화번호</th>
							<td class="tleft">
								<div>
									전화번호 : ${orderItem.orderExchangeApply.exchangeReceivePhone}<br/>
									휴대전화 : ${orderItem.orderExchangeApply.exchangeReceiveMobile}
								</div>
							</td> 
						</tr>
						
						<tr>   
							<th class="label">주소</th>
							<td class="tleft">
								<div>
									(${orderItem.orderExchangeApply.exchangeReceiveZipcode})<br/>
									${orderItem.orderExchangeApply.exchangeReceiveAddress}<br/>
									${orderItem.orderExchangeApply.exchangeReceiveAddress2}
								</div>
							</td> 
						</tr>
						
						<tr>   
							<th class="label">교환 요청 사유</th>
							<td colspan="2" class="tleft">
								<div>
									${orderItem.orderExchangeApply.exchangeReasonText}
								</div>
							</td>
						</tr>
						<tr>   
							<th class="label">배송(회수) 상태</th>
							<td colspan="2" class="tleft">
								<div>
									<c:choose>
										<c:when test="${orderItem.orderExchangeApply.exchangeShippingStartFlag == 'Y'}">
											택배사 : ${orderItem.orderExchangeApply.exchangeShippingCompanyName}, 송장번호 : ${orderItem.orderExchangeApply.exchangeShippingNumber}<br/>
											(해당 배송사로 고객이 <strong>${op:date(orderItem.orderExchangeApply.exchangeShippingStartDate)}일</strong>에 발송 하였습니다.)
										</c:when>
										<c:otherwise>
											지정택배사 <strong>${orderItem.deliveryCompanyName}</strong> 으로 회수신청 하였습니다.
										</c:otherwise>
									</c:choose>
								</div>
							</td> 
						</tr> 
						<tr>
							<th class="label">교환 택배비</th>
							<td colspan="2" class="tleft">
								<div>
									<form:input path="orderExchangeApply.exchangeRealShipping" />원
								</div>
							</td> 
						</tr>
						<tr>   
							<th class="label">교환 메모(관리자)</th>
							<td colspan="2" class="tleft">
								<div>
									<form:textarea path="orderExchangeApply.exchangeMemo" />
								</div>
							</td> 
						</tr>
						
						<tr>
							<th class="label">처리상태</th>
							<td colspan="2" class="tleft">
								<div>
									<form:radiobutton path="orderItemStatus" value="30" label="교환신청" />
									<form:radiobutton path="orderItemStatus" value="31" label="교환상품 회수중" />
									<form:radiobutton path="orderItemStatus" value="32" label="교환상품 발송 처리" />
									<form:radiobutton path="orderItemStatus" value="10" label="교환거절" />
									
									<div class="exchangeRefusal" <c:if test="${orderItem.orderItemStatus != '10'}">style="display:none"</c:if>>
										<p class="pt5">
											거절 사유 : <form:input path="orderExchangeApply.exchangeRefusalReasonText" maxlength="100" class="sixsix"/>
										</p>
										<div class="board_guide">
											<p class="tip">해당 내용은 사용자에게 노출되는 데이터 입니다.</p>
										</div>
									</div>
									
									<div class="exchangeShipping" <c:if test="${orderItem.orderItemStatus != '32'}">style="display:none"</c:if>>
										<p class="mb5 mt10">
											<form:select path="orderExchangeApply.exchangeDeliveryCompanyId" class="form-block">
												<form:option value="0">-선택-</form:option>
												<form:options items="${deliveryCompanyList}" itemValue="deliveryCompanyId" itemLabel="deliveryCompanyName" />
											</form:select>
										</p>
										<form:input path="orderExchangeApply.exchangeDeliveryNumber" maxlength="30" class="form-block"/>
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
$("#orderItem").validator({
	'submitHandler' : function() {
		
		var orderItemStatus = $(':radio[name="orderItemStatus"]:checked').val();
		if (orderItemStatus == '32') {
			$deliveryCompanyId= $('select[name="orderExchangeApply.exchangeDeliveryCompanyId"]');
			if ($deliveryCompanyId.val() == '' || $deliveryCompanyId.val() == '0') {
				alert('택배사를 선택해 주세요.');
				$deliveryCompanyId.focus();
				return false;
			}
			
			$deliveryNumber = $('input[name="orderExchangeApply.exchangeDeliveryNumber"]');
			if ($deliveryNumber.val() == '') {
				alert('송장번호를 입력해주세요.');
				$deliveryNumber.focus();
				return false;
			}
		} else if (orderItemStatus == '10') {
			$exchangeRefusalReasonText = $('input[name="orderExchangeApply.exchangeRefusalReasonText"]');
			if ($exchangeRefusalReasonText.val() == '') {
				alert('교환 거절사유를 입력해주세요.');
				$exchangeRefusalReasonText.focus();
				return false;
			}
		}
	}
});

$(':radio[name="orderItemStatus"]').on('change', function() {
	if ($(this).val() == '32') {
		$('.exchangeShipping').show();
	} else if ($(this).val() == '10') {
		$('.exchangeRefusal').show();
	} else { 
		$('.exchangeRefusal').hide();
		$('.exchangeShipping').hide();
	}
})

</script>