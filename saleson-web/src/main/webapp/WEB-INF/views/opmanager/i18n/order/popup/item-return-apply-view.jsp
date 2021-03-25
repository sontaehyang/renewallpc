<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

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
<div class="popup_wrap">
	<div class="popup_contents">
		<form:form modelAttribute="orderReturnApply" name="orderReturnApply" method="post" onsubmit="return Manager.Order.orderReturnApplyAction('${pageType}')">
		
			<form:hidden path="orderItemId" />

			<div class="board_list"> 	
				<h3><span>${orderReturnApply.orderItem.orderCode} - 환불 신청 상품 정보</span></h3>
		 		<table class="board_list_table" summary="주문내역 리스트">
		 			<caption>table list</caption>
		 			<colgroup> 
		 				<col style="width:auto;">
		 				<col style="width:72px;">
		 				<col style="width:108px;">
		 				<col style="width:109px;">
		 			</colgroup>
		 			<thead>
		 				<tr> 
		 					<th scope="col" class="none_left">상품명/옵션정보</th>
		 					<th scope="col" class="none_left">신청수량</th>
		 					<th scope="col" class="none_left">상품금액</th>
		 					<th scope="col" class="none_left">상품합계금액</th>
		 					
		 				</tr>
		 			</thead>
		 			<tbody>
		 				<tr>	
							<td class="left none_left">	 						 
		 						<div class="product_img_min"><img src="${orderReturnApply.orderItem.imageSrc}" alt="" width="88" /></div>
		 						<div class="arrival3">
		 							<p class="code">[${orderReturnApply.orderItem.itemUserCode}]</p>
		 							<p class="name">${orderReturnApply.orderItem.itemName}</p>
		 							
		 							${ shop:viewOptionText(orderReturnApply.orderItem.options) }
		 						</div>	  						  
		 					</td>
		 					<td>
		 						<select name="applyQuantity">
			 						<c:forEach begin="1" end="${orderReturnApply.orderItem.quantity}" step="1" var="quantity">
			 							<option value="${quantity}" ${op:selected(quantity, orderReturnApply.orderItem.quantity)}>${quantity}개</option>
			 						</c:forEach>
		 						</select>
		 					</td>
		 					<td><span class="price">${ op:numberFormat(orderReturnApply.orderItem.saleAmount) }</span>원</td>
		 					<td><div class="sum"><span class="price">${ op:numberFormat(orderReturnApply.orderItem.saleAmount) }</span>원</div></td>
		 				</tr>
		 			</tbody>
		 		</table><!--//esthe-table E-->
		 	</div>	<!--//board_write_list01 E-->
			
			<div class="board_write">
				<h3 class="mt10"><span>환불 신청 정보</span></h3>
				<table cellpadding="0" cellspacing="0" class="board_write_table">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:150px;">
		 				<col style="width:100px;">
		 				<col style="width:auto;">
		 			</colgroup>
		 			<tbody>
		 				<tr>
		 					<th class="label">환불 사유</th>
		 					<td class="left" colspan="2">
		 						<div>
			 						<form:select path="returnReason">
		 								<c:forEach var="code" items="${returnReasons}" varStatus="i">
		 									<form:option value="${code.detail}">${code.label}</form:option>
		 								</c:forEach>
		 							</form:select>
			 						
			 						<form:input path="returnReasonText" style="width:70%" />
			 					</div>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th class="label">고객 직접 배송</th>
		 					<td class="left" colspan="2">
		 						<div>
			 						<p class="mb3">
			 							택배사 : 
			 							<form:select path="returnShippingDeliveryCompanyId">
			 								<form:options items="${orderReturnApply.deliveryCompanys}" itemValue="deliveryCompanyId" itemLabel="deliveryCompanyName" />
			 							</form:select>
			 						</p>
			 						
			 						배송시작일 : <span class="datepicker">
				 						<form:input path="returnShippingStartDate" maxlength="8" class="datepicker2 term" />
							 		</span> / 
							 		송장번호 : <form:input path="returnShippingNumber" class="three" />
							 		
							 		<div class="board_guide">
										<p class="tip">지정 택배사에서 회수하러 갈때는 송장번호를 입력하지 않으면 됩니다.</p>
									</div>
			 					</div>
		 					</td>
		 				</tr>
		 				
		 				<c:if test="${orderReturnApply.writeBankInfo == true}">
		 					
		 					<tr>
		 						<th class="label" rowspan="2">환불 계좌 정보</th>
		 						<th class="label">은행 / 예금주</th>
			 					<td>
			 						<div>
			 							은행명 : <form:select path="returnBankName" cssClass="three">
			 								<c:forEach items="${shop:getBankListByKey(orderReturnApply.bankListKey)}" var="code">
												<option value="${code.key.id}">${code.label}</option>
											</c:forEach> 
			 							</form:select> 
			 							예금주 : <form:input path="returnBankInName" title="예금주" class="three" maxlength="30"/>
			 						</div>  
			 					</td>
		 					</tr>
		 					<tr>
			 					<th class="label">계좌번호</th>
			 					<td>
			 						<div>
			 							<form:input path="returnVirtualNo" title="계좌번" class="form-block" maxlength="50"/>
			 							<div class="board_guide">
											<p class="tip">계좌정보를 정확히 입력해주시기 바랍니다.</p>
										</div>
			 						</div>  
			 					</td>
			 				</tr>
		 				</c:if>
		 				
		 				<tr>
		 					<th class="label" rowspan="4">반품 배송정보</th>
		 					<th class="label">고객명</th>		 					
		 					<td>
		 						<div>
			 						<form:input path="returnReserveName" class="form-block" style="width:30%" />
			 					</div>
		 					</td>		 					
		 				</tr>
		 				<tr>
		 					<th class="label">주소</th>		 					
		 					<td>
		 						<div>
		 							<button type="button" class="btn btn-gradient btn-xs" onclick="openDaumPostcode();">우편번호</button> 
		 							<form:input path="returnReserveZipcode" class="form-block" style="width:20%"  readonly="true" />				 						
			 						 <form:input path="returnReserveAddress" class="form-block" style="width:70%"/>
			 						 <form:input path="returnReserveAddress2" class="form-block" style="width:70%"/>
			 						
			 						<form:hidden path="returnReserveSido"/>
									<form:hidden path="returnReserveSigungu"/>
									<form:hidden path="returnReserveEupmyeondong"/>
			 					</div>
		 					</td>		 					
		 				</tr>
		 				<tr>
		 					<th class="label">전화번호</th>		 					
		 					<td>
		 						<div>
			 						<form:input path="returnReservePhone" class="form-block" style="width:50%"/>
			 					</div>
		 					</td>		 					
		 				</tr>		 				
		 				<tr>
		 					<th class="label">휴대폰</th>		 					
		 					<td>
		 						<div>
			 						<form:input path="returnReserveMobile" class="form-block" style="width:50%"/>
			 					</div>
		 					</td>		 					
		 				</tr>
		 				
		 				
		 				
		 				<tr>
		 					<th class="label">반품 택배비</th>
		 					<td class="left" colspan="2">
		 						<div>
			 						<form:input path="returnRealShipping" class="two" />원
			 						<c:if test="${orderReturnApply.orderItem.orderItemStatus == '30' || orderReturnApply.orderItem.orderItemStatus == '31'}">
			 							<span class="small_text">
			 								해당 주문건은 <strong>${orderReturnApply.orderItem.orderItemStatusLabel}</strong>건 입니다.
			 								<c:if test="${orderReturnApply.orderItem.orderExchangeApply.exchangeRealShipping > 0}">
		 										(${op:numberFormat(orderReturnApply.orderItem.orderExchangeApply.exchangeRealShipping)}원의 배송비가 고객에게 안내되었습니다.)
		 									</c:if>
			 							</span>
			 						</c:if>
			 						<div class="board_guide">
										<p class="mt3 tip">
											<strong>예상 택배비</strong><br/>
											고객 부담 : ${op:numberFormat(orderReturnApply.collectionShipping + orderReturnApply.sendShipping)} 원 / 판매자 부담 : 0원
										</p>
										<p class="mt3 tip">
											택배비 입력란은 고객에게 노출되는 정보가 아니며 판매처에서 기록용으로 사용할수 있는 영역입니다. <br/>
											<strong>판매자 부담 사유더라도 꼭 0원을 입력 해야되는 것은 아닙니다.</strong>
										</p>
										<p class="mt3 tip">
											기본 반송배송비 : ${op:numberFormat(orderReturnApply.orderItem.shippingReturn)} 원 /
				 						 	제주 : ${op:numberFormat(orderReturnApply.orderItem.shippingExtraCharge1)} 원 /
				 						 	도서산간 : ${op:numberFormat(orderReturnApply.orderItem.shippingExtraCharge2)} 원
										</p>
									</div> 
								</div>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<th class="label">메모</th>
		 					<td class="left" colspan="2">
		 						<div>
		 							<form:textarea path="returnMemo"/>
		 						</div>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<th class="label">상품 회수여부</th>
		 					<td class="left" colspan="2">
		 						<div>
									<form:select path="orderItem.orderItemStatus">
										<option value="41">회수중</option>
										<option value="45">회수완료</option>
									</form:select>
		 						</div>
		 					</td>
		 				</tr>
		 				
		 			</tbody>
		 			
		 		</table>
		 	</div>
			
			<div class="popup_btns">
				<button type="submit" class="btn btn-active">신청하기</button> 
				<button type="button" class="btn btn-default" onclick="Shop.closeOrderLayer('return')">취소하기</button> 
			</div>
		</form:form>
	</div><!--//popup_contents E-->
</div>
<a href="javascript:Shop.closeOrderLayer('return')" class="popup_close">창 닫기</a>
<script type="text/javascript">
$(function() {
	
	// datepicer
	var $orderReturnApply = $('#orderReturnApply');
	$orderReturnApply.find('.ui-datepicker-trigger').remove();
	$orderReturnApply.find("input.term").each(function() {
		$(this).removeClass('hasDatepicker').datepicker(); 
	});

});

function openDaumPostcode() {
	var newZipcode		= "returnReserveNewZipcode";
	var zipcode 		= "returnReserveZipcode";
	var zipcode1 		= "returnReserveZipcode1";
	var zipcode2 		= "returnReserveZipcode2";
	var address 		= "returnReserveAddress";
	var addressDetail 	= "returnReserveAddressDetail";
	var sido			= "returnReserveSido";
	var sigungu			= "returnReserveSigungu";
	var eupmyeondong	= "returnReserveEupmyeondong";
	
	var tagNames = {
		'newZipcode'			: newZipcode,
		'zipcode' 				: zipcode,
		'zipcode1' 				: zipcode1,
		'zipcode2' 				: zipcode2,
		'sido'					: sido,
		'sigungu'				: sigungu,
		'eupmyeondong'			: eupmyeondong,
		'jibunAddress'			: address,
		'jibunAddressDetail' 	: addressDetail
	}
	
	openDaumAddress(tagNames, function(data){
		
		
	});
	
}

</script>