<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<!-- 내용 : s -->
<div class="con" id="layer_claim_apply">
	<div class="pop_title">
		<h3>취소 신청</h3>
		<a href="javascript:history.back()" class="history_back">뒤로가기</a>
	</div>
	<!-- //pop_title -->
	
	<form:form modelAttribute="claimApply" name="claimApply" method="post" onsubmit="return Mypage.claimApplyAction('1')" class="without-loading">
		
		<form:hidden path="orderCode" />
		<form:hidden path="orderSequence" />
		<form:hidden path="claimType" />
		<c:set var="orderStatus" value="${userClickItemStatus}" />
		
		<div class="pop_con pop_conA">
			<h4>취소신청 상품 정보</h4>
			<div class="cart_list bd mg0">
				 <ul class="list del_info">
				 	<c:forEach items="${claimApply.order.orderShippingInfos}" var="receiver" varStatus="receiverIndex">
						<c:forEach items="${receiver.orderItems}" var="orderItem" varStatus="orderItemIndex">
							<c:if test="${orderItem.claimApplyFlag == 'Y'}">
								<c:set var="flag" value="${orderItem.additionItemFlag}" />
							
							 	<li class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>
							 		<span class="check">
							 			<input type="checkbox" name="id" id="item-${orderItem.claimApplyItemKey}"
				 							data-item-sequence="${orderItem.itemSequence}" 
				 							data-parent-item-sequence="${orderItem.parentItemSequence}"
				 							data-addition-item-flag="${orderItem.additionItemFlag}" 
				 							value="${orderItem.claimApplyItemKey}" />
									 	
										<label for="item-${orderItem.claimApplyItemKey}">선택</label>
									</span>
									
									<%-- CJH 2016.11.13 itemSequence가 넘어가지않으면 무조건 0번 Sequence의 상품이 취소되는 효과가 발생.. 주의하도록 하자. --%>	
					 				<input type="hidden" name="claimApplyItemMap[${orderItem.claimApplyItemKey}].itemSequence" value="${orderItem.itemSequence}" />
					 				
									<div class="inner">
										<div class="con_top pd0 cf">
											<div class="cart_img">
												<img src="${shop:loadImageBySrc(orderItem.imageSrc, 'XS')}" alt="제품이미지">
											</div>
											<div class="cart_name">
												<p class="tit">${orderItem.itemName}</p>
												${ shop:viewOptionText(orderItem.options) }

												<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />
												<c:forEach items="${orderItem.additionItemList}" var="additionItem">
													<c:set var="totalSaleAmount" value="${totalSaleAmount + additionItem.saleAmount}" />

													추가구성품 : ${additionItem.itemName} ${additionItem.quantity}개 (+${op:numberFormat(additionItem.itemAmount)}원) <br />
												</c:forEach>

												${ shop:makeOrderGiftItemText(orderItem.orderGiftItemList)}
											</div>
										</div>
										<div class="con_bot">
											<c:choose>
					 							<c:when test="${orderItem.additionItemFlag == 'N'}">
					 								<div class="cacul fr">
													<select name="claimApplyItemMap[${orderItem.claimApplyItemKey}].applyQuantity" class="cancel-refund-info form-control select02">
								 						<c:forEach begin="1" end="${orderItem.quantity - orderItem.claimApplyQuantity}" step="1" var="quantity">
								 							<option value="${quantity}" ${op:selected(quantity, orderItem.quantity - orderItem.claimApplyQuantity)}>${quantity}개</option>
								 						</c:forEach>
							 						</select>				
							 						</div>	 							
					 							</c:when>
					 							<c:otherwise>
					 								${orderItem.quantity - orderItem.claimApplyQuantity}개
					 								<input type="hidden" name="claimApplyItemMap[${orderItem.claimApplyItemKey}].applyQuantity" value="${orderItem.quantity - orderItem.claimApplyQuantity}" />
					 							</c:otherwise>
					 						</c:choose>
										</div>
									</div>
							 	</li>
							</c:if>
						</c:forEach>
					</c:forEach>
				 </ul>
			</div>
			<!-- //cart_list -->
			
			<div class="single_input bd_table">
				<span class="del_tit t_gray">취소사유</span>
				<div class="select_area">
					<form:select path="claimReason" class="form-cancel_reason">
						<c:forEach var="code" items="${claimReasons}" varStatus="i">
							<form:option value="${code.detail}">${code.label}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<div class="text_area etc_area">
					<form:hidden path="claimReasonText" />
			 		<form:textarea path="claimReasonDetail" cols="30" rows="6" placeholder="내용을 입력해주세요" />
				</div>
			</div>
			<!-- //single_input -->
			
			<div class="desc_list">
				<ul>
					<li>위 상품은 배송을 준비한다는 판매자의 연락이 있었으며, 현재 상품이 발송되었을 수도 있습니다.</li>
					<li>이미 상품이 발송된 것으로 확인되면 취소 요청이 자동으로 철회되며, 이 경우 상품을 받으신 후 반품신청을 하실 수 있습니다.</li>
				</ul>
			</div>
			<!-- //desc_list -->
			
			<%--
			<h4>환불 정보 입력</h4>
			<div class="bd_table pd0">
				<ul class="del_info ">
					<li>
						<span class="del_tit t_gray">환불방법</span>
						<div class="radio_area">
							<input id="cancel_agree" type="radio" name="cancel_agree" checked="checked">
							<label for="cancel_agree"><span><span></span></span>신청하기</label>
						</div>
					</li>
					<li>
						<span class="del_tit t_gray">환불총액</span>
						<div class="price_wrap">
							<p class="total_price"><span>28,000</span>원</p>
							<p class="detail_price">상품금액[<span>25,000</span>원] + 배송비[<span>3,000</span>원]</p>
						</div>
					</li>
					<li>
						<span class="del_tit t_gray">은행명</span>
						<div class="input_area">
							<select name="" id="">
								<option value="산업은행">산업은행</option>
								<option value="산업은행">산업은행</option>
								<option value="산업은행">산업은행</option>
							</select>
						</div>
					</li>
					<li>
						<span class="del_tit t_gray">예금주</span>
						<input type="text">
					</li>
					<li>
						<span class="del_tit t_gray">계좌번호</span>
						<input type="text">
					</li>
				</ul>
			</div>
			<!-- //bd_table -->
			
			<div class="desc_list">
				<ul>
					<li><span>부분취소가 불가능한 결제방식(카드)</span>의 경우 위에 입력하신 계좌로 취소금액을 입금해드립니다.</li>
					<li>통장입금의 경우 <span>택배가 판매자에게 도착 후 2~3일 이후</span> 환불 신청하신 계좌로 입금됩니다.</li>
				</ul>
			</div>
			<!-- //desc_list -->
			
			<div class="btn_wrap">
				<button type="button" class="btn_st1 reset">취소</button>
				<button type="submit" class="btn_st1 decision">신청</button>
			</div>
			<!-- //btn_wrap -->
			--%>
		</div>
		<!-- //pop_con -->
		<div id="refundData" class="pop_con pop_conA"></div>
	</form:form>
</div>


<page:javascript>
<script type="text/javascript" src="/content/modules/op.mypage.js"></script>
<script type="text/javascript" src="/content/modules/op.order.js"></script>

<script type="text/javascript">
$(function(){
	$('.cancel-refund-info').on('change', function(){
		cancelRefundInfo();
	});

	$('li.parent :checkbox').on('change', function() {
		var className = $(this).closest('li.parent').data('class');
		$('li.child[data-class="' + className + '"] :checkbox').prop('checked', $(this).prop('checked'));

		cancelRefundInfo();
	});
});

function cancelRefundInfo() {
	
	$('.cancel-form').remove();
	if ($('input[name="id"]:checked').size() == 0) {
		return;
	}

	$.post('/m/order-claim-apply/cancel/refund-amount', $("#claimApply").serialize(), function(html){
		$('#refundData').empty()
		$('#refundData').append(html)
	}, 'html');
}
</script>
</page:javascript>