<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<form:form modelAttribute="claimApply" name="claimApply" method="post" onsubmit="return Mypage.claimApplyAction('1');">
	
	<form:hidden path="orderCode" />
	<form:hidden path="orderSequence" />
	<form:hidden path="claimType" />
	<c:set var="orderStatus" value="${userClickItemStatus}" />

	<div class="popup_wrap">
		<h1 class="popup_title">취소 신청</h1>
		<div class="popup_contents">
			<h2>취소 신청 상품 정보</h2>   
			<div class="pop_table">
		 		<table cellpadding="0" cellspacing="0">
		 			<caption>취소 신청 상품 정보</caption>
		 			<colgroup>
		 				<col style="width:4%;">
		 				<col style="width:16%;">
		 				<col style="width:auto;">
		 				<col style="width:16%;">
		 				<col style="width:17%;">
		 			</colgroup>
		 			<thead>
	 					<tr>
	 						<th scope="row"><input type="checkbox" id="check_all" title="체크박스" /></th>
		 					<th scope="row" colspan="2">상품명/옵션정보</th>
		 					<th scope="row">수량</th>
		 					<th scope="row">합계</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 			
		 				<c:forEach items="${claimApply.order.orderShippingInfos}" var="receiver" varStatus="receiverIndex">
							<c:forEach items="${receiver.orderItems}" var="orderItem" varStatus="orderItemIndex">
								<c:if test="${orderItem.claimApplyFlag == 'Y'}">
									<c:set var="flag" value="${orderItem.additionItemFlag}" />
									<tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}"
														${flag == 'Y' ? 'style="display:none"' : ''}>
					 					<td>
											<input type="checkbox" name="id"
												   data-item-sequence="${orderItem.itemSequence}"
												   data-parent-item-sequence="${orderItem.parentItemSequence}"
												   data-addition-item-flag="${orderItem.additionItemFlag}"
												   value="${orderItem.claimApplyItemKey}" />
					 						
					 						<%-- CJH 2016.11.13 itemSequence가 넘어가지않으면 무조건 0번 Sequence의 상품이 취소되는 효과가 발생.. 주의하도록 하자. --%>	
					 						<input type="hidden" name="claimApplyItemMap[${orderItem.claimApplyItemKey}].itemSequence" value="${orderItem.itemSequence}" />
					 					</td>
										<td colspan="2" class="txt_c bd_gray">
											<div class="pop_item">
												<p class="photo"><img src="${shop:loadImageBySrc(orderItem.imageSrc, 'XS')}" alt="item photo"></p>
												<div class="pop_item_info">
													<p class="item_name">${orderItem.itemName}</p>
													${ shop:viewOptionText(orderItem.options) }
													${ shop:makeOrderGiftItemText(orderItem.orderGiftItemList)}

													<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />
													<c:forEach items="${orderItem.additionItemList}" var="additionItem">
														<c:set var="totalSaleAmount" value="${totalSaleAmount + additionItem.saleAmount}" />

														추가구성품 : ${additionItem.itemName} ${additionItem.quantity}개 (+${op:numberFormat(additionItem.itemAmount)}원) <br />
													</c:forEach>
												</div>
											</div>	 						 
					 					</td>
					 					<td>
											<select name="claimApplyItemMap[${orderItem.claimApplyItemKey}].applyQuantity" class="cancel-refund-info form-control select02 apply-quantity">
												<c:forEach begin="1" end="${orderItem.quantity - orderItem.claimApplyQuantity}" step="1" var="quantity">
													<option value="${quantity}" ${op:selected(quantity, orderItem.quantity - orderItem.claimApplyQuantity)}>${quantity}개</option>
												</c:forEach>
											</select>
					 					</td>
					 					<td>
					 						<p><span>${ op:numberFormat(totalSaleAmount) }</span>원</p>
					 					</td>
					 				</tr>
					 			</c:if>
				 			</c:forEach>
				 		</c:forEach>
		 			 
		 				<tr>
		 					<th scope="col" colspan="2">취소 사유</th>	
							<td colspan="3" class="b_none">
								<div>
									<form:select path="claimReason" class="form-control select02">
		 								<c:forEach var="code" items="${claimReasons}" varStatus="i">
		 									<form:option value="${code.detail}">${code.label}</form:option>
		 								</c:forEach>
		 							</form:select>
		 							
			 						<form:hidden path="claimReasonText" />
			 						<form:input path="claimReasonDetail" class="select_input" />
		 						</div>
							</td>
		 				</tr>
		 			</tbody>
		 		</table>
			</div>
			<div class="pop_desc">
				<ul>
					<li>위 상품은 배송을 준비한다는 판매자의 연락이 있었으며, 현재 상품이 발송되었을 수 있습니다.</li>
					<li>이미 상품이 발송도니 것으로 확인되면 취소 요청이 자동으로 철회되며, 이 경우 상품을 받으신 후 반품신청을 하실 수 있습니다.</li>
				</ul>
			</div>
			
		</div><!--//popup_contents E-->
		
		<a href="javascript:Shop.closeOrderLayer('claim_apply')" class="popup_close">창 닫기</a>
	</div>
</form:form>

<page:javascript>
<script type="text/javascript" src="<c:url value="/content/modules/op.mypage.js" />"></script>
<script type="text/javascript">
$(function(){
	
	$('#check_all').on('click', function(){
		$('input[name="id"]').prop('checked', $(this).prop('checked'));
		
		cancelRefundInfo();
	});

	$('.cancel-refund-info').on('change', function(){
		cancelRefundInfo();
	});

	$('tr.parent :checkbox').on('change', function() {
		var className = $(this).closest('tr.parent').data('class');
		$('tr.child[data-class="' + className + '"] :checkbox').prop('checked', $(this).prop('checked'));

		cancelRefundInfo();
	});

});

function cancelRefundInfo() {
	$('.cancel-form').remove();

	if ($('input[name="id"]:checked').size() == 0) {
		return;
	}

	$.post('/order-claim-apply/cancel/refund-amount', $("#claimApply").serialize(), function(html){
		$('.popup_contents').append(html)
	}, 'html');
}
</script>
</page:javascript>