<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="board_list mt10">

	<h3>취소/반품/교환 신청</h3>

	<div style="border:1px solid #e5e5e5;padding:10px;" >
		<form id="op-admin-claim-form" method="post" action="${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/${pageType}/admin-claim-apply">
			<input type="hidden" name="orderCode" value="${order.orderCode}">
			<input type="hidden" name="orderSequence" value="${order.orderSequence}">
			
			<label><input type="radio" name="claimType" value="1" /> 취소</label>
			<label><input type="radio" name="claimType" value="2" /> 반품</label>
			<label><input type="radio" name="claimType" value="3" /> 교환</label>
			<table class="inner-table mt10" id="op-claim-item-table" style="display:none">
				<caption>${op:message('M00059')}</caption>
				<!-- 주문정보 -->
				<colgroup>
					<col style="width: 30px" />
					<col style="width: 80px" />
					<col />
					<col style="width: 80px" />
	 				<col style="width: 120px" />
	 				<col style="width: 80px" /> 
	 				<col style="width: 120px" />
	 				<col style="width: 120px" />
	 				<col style="width: 100px" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="none_left"><input type="checkbox" id="op-admin-claim-apply-all" title="체크박스" /></th>
						<th scope="col" class="none_left">이미지</th>
						<th scope="col" class="none_left">상품정보</th>
						<th scope="col" class="none_left">구분</th>
						<th scope="col" class="none_left">판매가</th>
						<th scope="col" class="none_left">수량</th>
						<th scope="col" class="none_left">총금액</th>
						<th scope="col" class="none_left">배송비</th>
						<th scope="col" class="none_left">상태</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${order.orderShippingInfos}" var="receiver" varStatus="receiverIndex">
						<c:forEach items="${receiver.orderItems}" var="orderItem" varStatus="orderItemIndex">
							<c:set var="itemKey" value="${orderItem.orderCode}-${orderItem.orderSequence}-${orderItem.itemSequence}" />
							<tr id="op-claim-item-${itemKey}" class="op-claim-item" style="display:none">
								<td>
									<input type="checkbox" name="adminClaimApplyKey" value="${itemKey}" data-order-status="${orderItem.orderStatus}"
											data-item-id="${orderItem.itemId}" data-item-options="${orderItem.options}" />
									<input type="hidden" name="itemMap[${itemKey}].itemSequence" value="${orderItem.itemSequence}" />
									<input type="hidden" name="itemMap[${itemKey}].key" value="${itemKey}" />
								</td>
								<td>
									<img src="${shop:loadImageBySrc(orderItem.imageSrc, 'XS')}" alt="${orderItem.itemName}" width="100%"/>
								</td>
								<td>
									[${orderItem.itemUserCode}] ${orderItem.itemName}
									${ shop:viewOptionText(orderItem.options) }

									<c:set var="totalSalePrice" value="${orderItem.salePrice}" />
									<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />

									<c:forEach items="${orderItem.additionItemList}" var="additionItem">
										<c:set var="totalSalePrice">${totalSalePrice + additionItem.salePrice}</c:set>
										<c:set var="totalSaleAmount">${totalSaleAmount + additionItem.saleAmount}</c:set>
										<c:set var="additionItemKey" value="${additionItem.orderCode}-${additionItem.orderSequence}-${additionItem.itemSequence}" />

										<input type="checkbox" name="adminClaimApplyKey" value="${additionItemKey}" data-order-status="${additionItem.orderStatus}"
											   data-parent-item-id="${orderItem.itemId}" data-parent-item-options="${orderItem.options}" data-addition-item-flag="Y" style="display:none" />
										<input type="hidden" name="itemMap[${additionItemKey}].itemSequence" value="${additionItem.itemSequence}" />
										<input type="hidden" name="itemMap[${additionItemKey}].key" value="${additionItemKey}" />
										<input type="hidden" name="itemMap[${additionItemKey}].quantity" value="${additionItem.quantity}">

										추가구성품 : ${additionItem.itemName} ${additionItem.quantity}개 (+${op:numberFormat(additionItem.itemAmount)}원) <br />
									</c:forEach>

									${shop:viewOrderGiftItemList(orderItem.orderGiftItemList)}
								</td>
								<td class="text-center">
									<c:choose>
										<c:when test="${shop:sellerId() == orderItem.sellerId}">자사</c:when>
										<c:otherwise>
											<span class="glyphicon glyphicon-user"></span>${orderItem.sellerName}
										</c:otherwise> 
									</c:choose>
								</td>
								<td class="text-right">
									${op:numberFormat(totalSalePrice)}원
								</td>
								<td class="text-right">
									<select name="itemMap[${itemKey}].quantity">
				 						<c:forEach begin="1" end="${orderItem.quantity - orderItem.claimApplyQuantity}" step="1" var="quantity">
				 							<option value="${quantity}" ${op:selected(quantity, orderItem.quantity - orderItem.claimApplyQuantity)}>${quantity}개</option>
				 						</c:forEach>
			 						</select>
								</td>
								<td class="text-right">
									${op:numberFormat(totalSaleAmount)}원
								</td>
								<td class="text-right">
									<c:choose>
			 							<c:when test="${orderItem.isShippingView == 'Y'}">
			 								<c:choose>
			 									<c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
			 										<span style="color:red">${op:numberFormat(orderItem.orderShipping.realShipping)}원 (착불)</span>
			 									</c:when>
			 									<c:otherwise>
			 										<c:choose>
			 											<c:when test="${orderItem.orderShipping.payShipping == 0}">무료</c:when>
			 											<c:otherwise>
			 												${op:numberFormat(orderItem.orderShipping.payShipping)}원
			 											</c:otherwise>
			 										</c:choose>
			 									</c:otherwise>
			 								</c:choose>
			 							</c:when>
			 							<c:otherwise>묶음배송</c:otherwise>
			 						</c:choose>
								</td>
								<td class="text-center"> 
									${orderItem.orderStatusLabel}
								</td> 
							</tr>
						</c:forEach>
					</c:forEach>
					
				</tbody>
				<tfoot>
					<tr class="op-claim-type-cancel op-claim-type-info">
						<th colspan="2">취소 사유</th>
						<td colspan="7">
							<select name="cancelClaimReason">
								<c:forEach var="code" items="${cancelClaimReasons}" varStatus="i">
									<option value="${code.detail}">${code.label}</option>
								</c:forEach>
							</select>
							
	 						<input type="hidden" name="cancelClaimReasonText"  />
                            <input type="text" name="cancelClaimReasonDetail" maxlength="80" style="width:70%" />
						</td>
					</tr>
					
					<tr class="op-claim-type-cancel op-claim-type-info">
						<th colspan="2">처리구분</th>
						<td colspan="7">
							<label><input type="checkbox" name="refundFlag" value="Y" /> 선택한 주문을 환불내역(신청 상태)으로 보냅니다.</label>
						</td>
					</tr>
					
					<tr class="op-claim-type-return op-claim-type-info">
						<th colspan="2">반품 사유</th>
						<td colspan="7">
							<select name="returnClaimReason">
								<c:forEach var="code" items="${returnClaimReasons}" varStatus="i">
									<option value="${code.detail}">${code.label}</option>
								</c:forEach>
							</select>
							
	 						<input type="hidden" name="returnClaimReasonText"  />
	 						<input type="text" name="returnClaimReasonDetail" maxlength="80" style="width:70%" />
						</td>
					</tr>
					
					<tr class="op-claim-type-exchange op-claim-type-info">
						<th colspan="2">교환 사유</th>
						<td colspan="7">
							<select name="exchangeClaimReason">
								<c:forEach var="code" items="${exchangeClaimReasons}" varStatus="i">
									<option value="${code.detail}">${code.label}</option>
								</c:forEach>
							</select>
							
	 						<input type="hidden" name="exchangeClaimReasonText"  />
	 						<input type="text" name="exchangeClaimReasonDetail" maxlength="80" style="width:70%" />
						</td>
					</tr>
					
					<tr>
						<td colspan="9" class="text-center"><button type="submit" class="btn btn-active">저장</button></td>
					</tr>
				</tfoot>
			</table>
		</form>

		<div class="op-claim-message" style="padding: 10px 20px; background: #fcf8e8; margin-top: 10px;">
			신청 항목을 선택해 주세요.
		</div>
	</div>
</div>

<div class="jq_tabonoff comm_tab1">
	<ul class="jq_tab tab_menu">
		<li ${viewTabIndex == '0' ? 'class="on"' : ''}><a href="javascript:;" class="tit">일반</a></li>
		<li ${viewTabIndex == '1' ? 'class="on"' : ''}>
			<a href="javascript:;" class="tit">취소 
				<c:if test="${fn:length(activeCancels) > 0}">(${op:numberFormat(fn:length(activeCancels))})</c:if>
			</a> 
		</li>
		<li ${viewTabIndex == '2' ? 'class="on"' : ''}>
			<a href="javascript:;" class="tit">반품
				<c:if test="${fn:length(activeReturns) > 0}">(${op:numberFormat(fn:length(activeReturns))})</c:if>
			</a>
		</li> 
		<li ${viewTabIndex == '3' ? 'class="on"' : ''}> 
			<a href="javascript:;" class="tit">교환
				<c:if test="${fn:length(activeExchanges) > 0}">(${op:numberFormat(fn:length(activeExchanges))})</c:if>
			</a>
		</li>
		<c:if test="${requestContext.opmanagerPage}">
			<li ${viewTabIndex == '4' ? 'class="on"' : ''}>
				<a href="javascript:;" class="tit">이력</a>
			</li>
		</c:if>

		<li ${viewTabIndex == '5' ? 'class="on"' : ''}>
			<a href="javascript:;" class="tit">사은품</a>
		</li>
	</ul>
	<div class="jq_cont tab_cont" >

		<!-- //탭1 -->
		<div class="cont" ${viewTabIndex == '0' ? '' : 'style="display:none"'}>
			<jsp:include page="../include/order-default-info.jsp"></jsp:include>
		</div>

		<div class="cont" id="order-cancel-form" ${viewTabIndex == '1' ? '' : 'style="display:none"'}>
			<jsp:include page="../include/order-item-cancel.jsp"></jsp:include>
		</div>

		<div class="cont" id="order-return-form" ${viewTabIndex == '2' ? '' : 'style="display:none"'}>
			<jsp:include page="../include/order-item-return.jsp"></jsp:include>
		</div>

		<div class="cont" id="order-exchange-form" ${viewTabIndex == '3' ? '' : 'style="display:none"'}>
			<jsp:include page="../include/order-item-exchange.jsp"></jsp:include>
		</div>

		<c:if test="${requestContext.opmanagerPage}">
			<div class="cont" id="order-log" ${viewTabIndex == '4' ? '' : 'style="display:none"'}>
				<jsp:include page="../include/order-log.jsp"></jsp:include>
			</div>
		</c:if>

		<div class="cont" id="order-gift-item" ${viewTabIndex == '5' ? '' : 'style="display:none"'}>
			<jsp:include page="../include/order-gift-item.jsp"></jsp:include>
		</div>

	</div>
</div>