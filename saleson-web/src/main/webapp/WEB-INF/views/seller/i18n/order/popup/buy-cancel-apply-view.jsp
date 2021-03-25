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
	<form:form modelAttribute="orderCancelApply" name="orderCancelApply" method="post" onsubmit="return Manager.Order.orderCancelApplyAction('${pageType}')">
		<form:hidden path="orderShippingId" />
		<form:hidden path="orderCode" />

		<div class="popup_contents">

			<div class="board_list">	
				<h3><span>${orderCancelApply.orderCode} - 취소 신청 상품 정보</span></h3>
		 		<table class="board_list_table" summary="주문내역 리스트">
		 			<caption>table list</caption>
		 			<colgroup> 
		 				<col style="width:72px;">
		 				<col style="width:auto;">
		 				<col style="width:72px;">
		 				<col style="width:108px;">
		 				<col style="width:109px;">
		 				<col style="width:120px;">
		 			</colgroup>
		 			<thead>
		 				<tr> 
		 					<th scope="col" class="none_left">선택</th>
		 					<th scope="col" class="none_left">상품명/옵션정보</th>
		 					<th scope="col" class="none_left">신청수량</th>
		 					<th scope="col" class="none_left">상품금액</th>
		 					<th scope="col" class="none_left">상품합계금액</th> 
		 					<th scope="col" class="none_left">배송비</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 				
						<c:forEach items="${ orderCancelApply.orderShipping.orderItems }" var="orderItem" varStatus="itemIndex">
			 				<tr>
			 					<td>
			 						<input type="hidden" name="orderItemIds" value="${orderItem.orderItemId}" />
			 						<input type="checkbox" name="ids" value="${ orderItem.orderItemId }" checked="checked" />
			 					</td>	
								<td class="left none_left">	 						 
			 						<div class="arrival3">
			 							<p class="code">[${orderItem.itemUserCode}]</p>
			 							<p class="name">${orderItem.itemName}</p>
			 							${ shop:viewOptionText(orderItem.options) }
			 						</div>	 						  
			 					</td>
			 					<td>
			 						<select name="applyQuantitys">
				 						<c:forEach begin="1" end="${orderItem.quantity}" step="1" var="quantity">
				 							<option value="${quantity}" ${op:selected(quantity, orderItem.quantity)}>${quantity}개</option>
				 						</c:forEach>
			 						</select>
			 					</td>
			 					<td><span class="price">${ op:numberFormat(orderItem.saleAmount) }</span>원</td>
			 					<td><div class="sum"><span class="price">${ op:numberFormat(orderItem.saleAmount) }</span>원</div></td>
			 					<c:if test="${itemIndex.first == true}">
				 					<td rowspan="${fn:length(orderCancelApply.orderShipping.orderItems) }">
				 						<c:choose>
											<c:when test="${orderCancelApply.orderShipping.payShipping > 0}">배송비 : ${op:numberFormat(orderCancelApply.orderShipping.payShipping)}원</c:when>
											<c:otherwise>배송비 : 무료</c:otherwise>
										</c:choose>
				 					</td>
			 					</c:if>
			 				</tr>	
						</c:forEach>			 
		 			</tbody>
		 		</table><!--//esthe-table E-->	  		 
		 	</div>	<!--//board_write_list01 E-->
			
			<div class="board_write">
				<h3 class="mt10"><span>취소 신청 정보</span></h3>
				
				<table cellpadding="0" cellspacing="0" class="board_write_table">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:150px;">
		 				<col style="width:100px;">
		 				<col style="width:auto;">
		 			</colgroup>
		 			<tbody>
		 				<tr>
		 					<th class="label">취소 사유</th>
		 					<td class="left" colspan="2">
		 						<div>
			 						<form:select path="cancelReason">
			 							<form:option value="1">판매자 사유</form:option>
		 								<c:forEach var="code" items="${cancelReasons}" varStatus="i">
		 									<form:option value="${code.detail}">${code.label}</form:option>
		 								</c:forEach>
		 							</form:select>
			 						
			 						<form:input path="cancelReasonText" style="width:70%" />
			 					</div>
		 					</td>
		 				</tr>
		 				
		 				<c:if test="${orderCancelApply.writeBankInfo == true}">
		 					<tr>
		 						<th class="label" rowspan="2">환불 계좌 정보</th>
		 						<th class="label">은행 / 예금주</th>
			 					<td>
			 						<div>
			 							은행명 : <form:input path="cancelBankName" title="은행명" class="three" maxlength="30"/>
			 							예금주 : <form:input path="cancelBankInName" title="예금주" class="three" maxlength="30"/>
			 						</div>  
			 					</td>
		 					</tr>
		 					<tr>
			 					<th class="label">계좌번호</th>
			 					<td>
			 						<div>
			 							<form:input path="cancelVirtualNo" title="계좌번" class="form-block" maxlength="50"/>
			 							<div class="board_guide">
											<p class="tip">계좌정보를 정확히 입력해주시기 바랍니다.</p>
										</div>
			 						</div>  
			 					</td>
			 				</tr>
		 				</c:if>
		 				
		 				<tr>
		 					<th class="label">메모</th>
		 					<td class="left" colspan="2">
		 						<div>
		 							<form:textarea path="cancelMemo"/>
		 						</div>
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
		 	</div>
			 
			<div class="popup_btns">
				<button type="submit" class="btn btn-active">신청하기</button> 
				<button type="button" class="btn btn-default" onclick="Shop.closeOrderLayer('cancel')">취소하기</button> 
			</div>
		</div>
	</form:form>
</div>