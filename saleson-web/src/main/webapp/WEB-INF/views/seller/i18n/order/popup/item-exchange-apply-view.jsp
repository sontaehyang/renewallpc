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
	<form:form modelAttribute="orderExchangeApply" name="orderExchangeApply" method="post" onsubmit="return Manager.Order.orderExchangeApplyAction('${pageType}')">
		<form:hidden path="orderItemId" />
		<div class="popup_contents">

			<div class="board_list">
				<h3>${orderExchangeApply.orderItem.orderCode} - 교환 신청 상품 정보</h3>	 	
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
		 					<th scope="col" class="none_left">수량</th>
		 					<th scope="col" class="none_left">상품금액</th>
		 					<th scope="col" class="none_left">상품합계금액</th> 
		 				</tr>
		 			</thead>
		 			<tbody>
		 				<tr>	
							<td class="left none_left">	 						 
		 						<div class="product_img_min"><img src="${orderExchangeApply.orderItem.imageSrc}" alt="" width="88" /></div>
		 						<div class="arrival3">
		 							<p class="code">[${orderExchangeApply.orderItem.itemUserCode}]</p>
		 							<p class="name">${orderExchangeApply.orderItem.itemName}</p>
		 							${ shop:viewOptionText(orderExchangeApply.orderItem.options) }			
		 						</div>	 						  
		 					</td>
		 					<td>${op:numberFormat(orderExchangeApply.orderItem.quantity)}개</td>
		 					<td><span class="price">${ op:numberFormat(orderExchangeApply.orderItem.saleAmount) }</span>원</td>
		 					<td><div class="sum"><span class="price">${ op:numberFormat(orderExchangeApply.orderItem.saleAmount) }</span>원</div></td>
		 				</tr>
		 			</tbody>
		 		</table><!--//esthe-table E-->	  		 
		 	</div>	<!--//board_write_list01 E-->
		 	
		 	<div class="board_write">
				<h3 class="mt10"><span>교환 신청 정보</span></h3>
				
				<table cellpadding="0" cellspacing="0" class="board_write_table">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:150px;">
		 				<col style="width:100px;">
		 				<col style="width:auto;">
		 			</colgroup>
		 			<tbody>
		 				<tr>
		 					<th class="label">교환 사유</th>
		 					<td class="left" colspan="2">
		 						<div>
			 						<form:select path="exchangeReason">
		 								<c:forEach var="code" items="${exchangeReasons}" varStatus="i">
		 									<form:option value="${code.detail}">${code.label}</form:option>
		 								</c:forEach>
		 							</form:select>
			 						
			 						<form:input path="exchangeReasonText" style="width:70%" />
			 					</div>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th class="label">고객 직접 배송</th>
		 					<td class="left" colspan="2">
		 						<div>
			 						<p class="mb3">
			 							택배사 : 
			 							<form:select path="exchangeShippingDeliveryCompanyId">
			 								<form:options items="${orderExchangeApply.deliveryCompanys}" itemValue="deliveryCompanyId" itemLabel="deliveryCompanyName" />
			 							</form:select>
			 						</p>
			 						
			 						배송시작일 : <span class="datepicker">
				 						<form:input path="exchangeShippingStartDate" maxlength="8" class="datepicker2 term" />
							 		</span> / 
							 		송장번호 : <form:input path="exchangeShippingNumber" class="three" />
							 		
							 		<div class="board_guide">
										<p class="tip">지정 택배사에서 회수하러 갈때는 송장번호를 입력하지 않으면 됩니다.</p>
									</div>
			 					</div>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th class="label" rowspan="3">교환상품 배송지 정보</th>
		 					<th class="label">고객명</th>
		 					<td class="left">
		 						<div>
		 							${orderExchangeApply.exchangeReceiveName }
		 							<form:hidden path="exchangeReceiveName" />
								</div>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th class="label">전화번호 / 휴대폰</th>
		 					<td class="left">
		 						<div>
		 							${orderExchangeApply.exchangeReceivePhone } / ${orderExchangeApply.exchangeReceiveMobile }
		 							<form:hidden path="exchangeReceivePhone" />
		 							<form:hidden path="exchangeReceiveMobile" />
								</div>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th class="label">주소</th>
		 					<td class="left">
		 						<div>
		 							(${orderExchangeApply.exchangeReceiveZipcode1 }-${orderExchangeApply.exchangeReceiveZipcode2 })
			 						<form:hidden path="exchangeReceiveZipcode1" />
			 						<form:hidden path="exchangeReceiveZipcode2" />
			 						
			 						${orderExchangeApply.exchangeReceiveAddress }
		 							<form:hidden path="exchangeReceiveSido" />
									<form:hidden path="exchangeReceiveSigungu" />
									<form:hidden path="exchangeReceiveEupmyeondong" /> 
									<form:hidden path="exchangeReceiveAddress" />
									
									${orderExchangeApply.exchangeReceiveAddress2 }
		 							<form:hidden path="exchangeReceiveAddress2" />
		 							
		 							<div class="board_guide">
										<p class="tip">교환 배송지를 변경하고자 할때는 고객이 마이페이지에서 직접 신청하여야 합니다.</p>
									</div>
								</div>
		 					</td>
		 				</tr>
		 	
						<tr>
		 					<th class="label">교환 택배비</th>
		 					<td class="left" colspan="2">
		 						<div>
			 						<form:input path="exchangeRealShipping" class="two" />원
			 						<c:if test="${orderExchangeApply.orderItem.orderItemStatus == '40' || orderExchangeApply.orderItem.orderItemStatus == '41'}">
			 							<span class="small_text">
			 								해당 주문건은 <strong>${orderExchangeApply.orderItem.orderItemStatusLabel}</strong>건 입니다.
			 								
			 							</span>
			 						</c:if>
			 						<div class="board_guide">
										
										<p class="tip">
											<strong>배송 정보</strong><br/>
											
											<c:if test="${orderExchangeApply.orderItem.islandType == '1'}"> <strong>(제주)</strong></c:if>
											<c:if test="${orderExchangeApply.orderItem.islandType == '2'}"> <strong>(도서산간)</strong></c:if>
											${orderExchangeApply.orderItem.orderShippingInfo.receiveAddress} ${orderExchangeApply.orderItem.orderShippingInfo.receiveAddressDetail} 
											
											<c:if test="${orderExchangeApply.freeShipping == true}">
												<strong>&nbsp;(무료로 배송된 상품입니다.)</strong>
											</c:if>
										</p>
										<p class="mt3 tip">
											<strong>예상 택배비</strong><br/>
											고객 부담 : ${op:numberFormat(orderExchangeApply.collectionShipping + orderExchangeApply.resendShipping)} 원 / 판매자 부담 : 0원
										</p>
										<p class="mt3 tip">
											택배비 입력란은 고객에게 노출되는 정보가 아니며 판매처에서 기록용으로 사용할수 있는 영역입니다. <br/>
											<strong>판매자 부담 사유더라도 꼭 0원을 입력 해야되는 것은 아닙니다.</strong>
										</p>
										<p class="mt3 tip">
											기본 반송배송비 : ${op:numberFormat(orderExchangeApply.orderItem.shippingReturn)} 원 /
				 						 	제주 : ${op:numberFormat(orderExchangeApply.orderItem.shippingExtraCharge1)} 원 /
				 						 	도서산간 : ${op:numberFormat(orderExchangeApply.orderItem.shippingExtraCharge2)} 원
										</p>
									</div> 
								</div>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<th class="label">메모</th>
		 					<td class="left" colspan="2">
		 						<div>
		 							<form:textarea path="exchangeMemo"/>
		 						</div>
		 					</td>
		 				</tr>
		 				
		 				<tr>
		 					<th class="label">상품 회수여부</th>
		 					<td class="left" colspan="2">
		 						<div>
									<form:select path="orderItem.orderItemStatus">
										<option value="30">회수중</option>
										<option value="31">회수완료</option>
									</form:select>
		 						</div>
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
		 	</div>
		</div><!--//popup_contents E-->
		
		<div class="popup_btns">
			<button type="submit" class="btn btn-active">신청하기</button> 
			<button type="button" class="btn btn-default" onclick="Shop.closeOrderLayer('exchange')">취소하기</button> 
		</div>
	</form:form>
</div>
<a href="javascript:Shop.closeOrderLayer('exchange')" class="popup_close">창 닫기</a>
<script type="text/javascript">

$(function() {
	
	// datepicer
	var $orderExchangeApply = $('#orderExchangeApply');
	$orderExchangeApply.find('.ui-datepicker-trigger').remove();
	$orderExchangeApply.find("input.term").each(function() {
		$(this).removeClass('hasDatepicker').datepicker(); 
	});
	
});

</script>