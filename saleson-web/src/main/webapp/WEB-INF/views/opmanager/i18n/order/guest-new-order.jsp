<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">
.find_item_layer {
	
	display: none;
	position: fixed; 
	z-index: 100000; 
	width:96%;
	left: 50%; 
	margin-left: -48%; 
	top:50%; 
	margin-top: -350px;; 
	background: #fff;
	height:700px;
	padding-bottom: 20px;  
} 
.popup_title {border-top:0px;}

.popup_close_layer {
	overflow: hidden;
	position: absolute;
	top: 0;
	right: 0;
	width: 50px;
	height: 50px;
	background: url('/content/images/common/icon_delete.png') 50% no-repeat;
	text-indent: -999px;
	white-space: nowrap;
}
</style>

<!-- 본문 -->
<div class="popup_wrap">
	<form:form modelAttribute="order" method="post" action="/opmanager/order/guest-new-order">
		<input type="hidden" name="userId" value="${ userId }" />
		<input type="hidden" name="customerCode" value="${ order.customerCode }" />
		<input type="hidden" name="orderPayAmount" value="${ order.orderPrice.orderPayAmount }" />
		<input type="hidden" name="totalItemPrice" value="${ order.orderPrice.totalItemPrice }" />
		<input type="hidden" name="totalItemCouponDiscountAmount" value="${ order.orderPrice.totalItemCouponDiscountAmount }" />
		<input type="hidden" name="totalExcisePrice" value="${ order.orderPrice.totalExcisePrice }" />
		<input type="hidden" name="totalDeliveryCharge" value="${ order.orderPrice.totalDeliveryCharge }" />
		<input type="hidden" name="totalCartCouponDiscountAmount" value="${ order.orderPrice.totalCartCouponDiscountAmount }" />
		<input type="hidden" name="totalPointDiscountAmount" value="${ order.orderPrice.totalPointDiscountAmount }" />
		<input type="hidden" name="afterUsePoint" value="${ order.orderPrice.totalPointDiscountAmount }" />
		<input type="hidden" name="differencePoint" value="${ order.orderPrice.totalPointDiscountAmount - order.editPointUse }" />
		<div class="couponHideFieldArea"></div>
	
		<h1 class="popup_title">수기 주문</h1> <!-- 주문상품 수정팝업 -->
		<div class="popup_contents">				
		<div class="board_list">
			<div style="margin:0px; text-align: right;">
				<select class="select" id="searchWhere">
					<option value="ITEM_NAME">${op:message('M00018')}</option> <!-- 상품명 -->
					<option value="ITEM_USER_CODE">${op:message('M00783')}</option> <!-- 상품코드 -->
				</select>
				<input type="text" class="text" style="width: 33%" id="searchQuery"/ >
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="searchItem()"><span>${op:message('M01185')}</span></button>
			</div> <!-- 단위 : 원 -->
			<table class="board_list_table" summary="${op:message('M01146')}">
				<caption>${op:message('M01146')}</caption> <!-- 전체상품리스트 --> 
				<colgroup> 						
					<col style="width:auto;" />
					<col style="width:10%;" />
					<col style="width:6%;" /> 
					<col style="width:6%;" /> 
					<col style="width:6%;" />
					<col style="width:7%;" /> 
					<col style="width:6%;" />
					<col style="width:15%;" />
				</colgroup> 
				<thead> 
					<tr> 
						<th>${op:message('M00018')}</th> <!-- 상품명 --> 
						<th>${op:message('M00356')} <br/>(${ shopContext.config.taxDisplayTypeText })</th> <!-- 단가 --> 	
						<th>${op:message('M01225')} <br/>(${ shopContext.config.taxDisplayTypeText })</th> <!-- 옵션가 -->
						<th>${op:message('M00357')}</th> <!-- 수량 -->
						<th>${op:message('M00628')}</th> <!-- 할인금액 -->
						<th>${op:message('M00817')}</th> <!-- 상품합계 -->	 
						<th>${op:message('M01226')}</th> <!-- 편집 -->
						<th>${op:message('M01227')}</th> <!-- 배송정책 --> 
					</tr>
				</thead>
				<tbody id="order_items">
					
					<c:forEach items="${ order.orderDeliverys }" var="delivery" varStatus="deliveryIndex">
						<c:set var="deliveryCharge" value="${delivery.deliveryCharge}" />
						
						<c:set var="rowspan" value="${ fn:length(delivery.orderItems) * 2 }" />
						<c:forEach items="${ delivery.orderItems }" var="orderItem" varStatus="orderItemIndex">
						
							<c:choose>
								<c:when test="${ delivery.deliveryType eq '1' }">
									<c:set var="deliveryKey" value="group-${ delivery.deliveryId }" />
								</c:when>
								<c:otherwise>
									<c:set var="deliveryKey" value="single-${ delivery.deliveryId }-${ orderItem.userOrderItemSequence }" />
								</c:otherwise>
							</c:choose>
							
							<c:set var="deliveryGroupKey" value="" />
							<c:if test="${ delivery.deliveryType eq '1' }">
								<c:set var="deliveryGroupKey" value="group-${ delivery.deliveryId }-${ deliveryCharge.deliveryChargeId }" />
							</c:if>
							
							<tr class="order_item ${ deliveryKey } ${deliveryGroupKey} orderItem_${ orderItem.itemId }" id="order_item_${ orderItem.userOrderItemSequence }">   
								<td class="tleft">
									
									<input type="hidden" name="orderItemIds" value="${ orderItem.orderItemId }">
									<input type="hidden" name="userOrderItemSequences" value="${ orderItem.userOrderItemSequence }" />
									<input type="hidden" name="itemIds" value="${ orderItem.itemId }" />
									<input type="hidden" name="itemTypes" value="old" />
									<input type="hidden" name="deliveryChargeId" value="${ deliveryCharge.deliveryChargeId }" />
									<input type="hidden" name="deliveryChargeType" value="${ deliveryCharge.deliveryChargeType }" />
									<input type="hidden" name="deliveryFreeAmount" value="${ deliveryCharge.deliveryFreeAmount }" />
									<input type="hidden" name="deliveryPrice" value="${ deliveryCharge.deliveryCharge }" />
	
									<c:if test="${ delivery.deliveryType eq '2' }">
										<input type="hidden" name="deliveryId" value="${ delivery.deliveryId }" />
		 								<input type="hidden" name="deliveryExtraCharge1" value="${ delivery.deliveryExtraCharge1 }" />
		 								<input type="hidden" name="deliveryExtraCharge2" value="${ delivery.deliveryExtraCharge2 }" />
		 							</c:if>
		 							
									<div class="item_img3">
										<img src="${ orderItem.item.imageSrc }" alt="상품이미지" style="width:50px;">
									</div>
									<div class="item_box_list"> 
										<p><strong>[${ orderItem.itemUserCode }]</strong></p>
										<p><strong>${ orderItem.itemName }</strong></p>
										<c:set var="itemOptions" value="///" />
										<c:choose>
											<c:when test="${ not empty orderItem.requiredOptionsList }">
												<ul class="item-list">
					 								<c:forEach items="${ orderItem.requiredOptionsList }" var="option" varStatus="optionIndex">
					 								
					 									<c:if test="${ optionIndex.index > 0 }">
															<c:set var="itemOptions">${ itemOptions }@</c:set>
														</c:if>
														<c:set var="itemOptions">${ itemOptions }${ option.itemOptionId }</c:set>
					 								
														<li>
															<span>${ option.optionName1 }</span>
															${option.optionName2} 
															<c:if test="${!empty option.extraPrice && option.extraPrice != 0}">
															(${op:numberFormat(option.extraPrice)}원)
															</c:if>
															
															<c:if test="${!empty option.optionCode}">
															(옵션코드 : ${option.optionCode})
															</c:if>
														</li>
													</c:forEach>
												</ul>
											</c:when>
											<c:otherwise><br/><br/></c:otherwise>
										</c:choose>
										
										<input type="hidden" name="itemOptions" value="${ itemOptions }" />
									</div>
								</td> 
								<td><div><input type="text" class="seven _number" name="itemPrices" value="${ orderItem.itemPrice }"></div></td>
								<td><div><input type="text" class="seven _number_nagetive" name="totalRequiredOptionsPrices" value="${ orderItem.totalRequiredOptionsPrice }"></div></td>
								<td><div><input type="text" class="seven _number" name="quantitys" value="${ orderItem.quantity }"></div></td>
								<td><div class="itemCouponDiscountAmount">${ op:numberFormat(orderItem.itemCouponDiscountAmount) }</div></td>
								<td><div class="itemPayAmount">${ op:numberFormat(((orderItem.itemPrice + orderItem.totalRequiredOptionsPrice) * orderItem.quantity) - orderItem.itemCouponDiscountAmount) }</div></td>
								<td>
									<div>
										<c:if test="${mode == 'change' || (mode == 'exchange' && (orderItem.orderStatus == '30' || orderItem.orderStatus == '31'))}">
											<c:if test="${orderItem.orderStatus == '30' || orderItem.orderStatus == '31'}">
												<p style="color:red;">교환요청</p>
											</c:if>
											<button class="table_btn" title="${op:message('M00074')}" onclick="deleteItem('${ deliveryKey }', '${orderItem.userOrderItemSequence}')">${op:message('M00074')}</button> <!-- 삭제 --> 
										</c:if>
									</div>
								</td>
								<c:if test="${ orderItemIndex.index == 0 }">
									<td rowspan="${ rowspan }" class="${ deliveryKey } delivery-info" id="${ deliveryKey }" style="border-left: 1px solid #ececec;">
									
		 								
		 								<c:if test="${ delivery.deliveryType eq '1' }">
		 									<input type="hidden" name="deliveryId" value="${ delivery.deliveryId }" />
	 										<input type="hidden" name="deliveryChargePolicy" value="${ delivery.deliveryChargePolicy }" />
		 									<input type="hidden" name="conditionFirstFlag" value="${ delivery.conditionFirstFlag }" />
		 									<input type="hidden" name="chargedSeparatelyFlag" value="${ delivery.chargedSeparatelyFlag }" />
		 									<input type="hidden" name="deliveryExtraCharge1" value="${ delivery.deliveryExtraCharge1 }" />
			 								<input type="hidden" name="deliveryExtraCharge2" value="${ delivery.deliveryExtraCharge2 }" />
		 								</c:if>
										
										<strong>${ delivery.title }</strong> <br/>
										<c:choose>
											<c:when test="${ delivery.deliveryType eq '1' }">[묶음]</c:when>
											<c:otherwise>[개별]</c:otherwise>
										</c:choose>
										
										<c:if test="${ delivery.deliveryType eq '2' }">
											<c:choose>
												<c:when test="${ deliveryCharge.deliveryChargeType eq '1' }">무료</c:when>
												<c:when test="${ deliveryCharge.deliveryChargeType eq '2' }">유료 ${ op:numberFormat(deliveryCharge.deliveryCharge) }원</c:when>
												<c:otherwise>조건 : ${ op:numberFormat(deliveryCharge.deliveryFreeAmount) } 이상 무료, 이하 ${ op:numberFormat(deliveryCharge.deliveryCharge) }원</c:otherwise>
											</c:choose>
											
										</c:if>
										<br/>
										<label id="${ deliveryKey }-text">${ op:numberFormat(delivery.deliveryPrice) }</label>원
									</td>
								</c:if>
							</tr>
							<tr id="order_item_${ orderItem.userOrderItemSequence }_coupons" class="item_coupons">
								<c:set var="colspan" value="6" />
								<c:if test="${mode == 'change'}">
									<c:set var="colspan" value="7" />
								</c:if> 
		 						<td colspan="${colspan}" class="tleft">
									<c:choose>
										<c:when test="${ not empty orderItem.itemCoupons }">
											<c:set var="itemCouponAreaKey" value="coupon-${ orderItem.userOrderItemSequence }" />
												<ul class="item-coupons pt10" id="${ itemCouponAreaKey }">
					 								<li><input type="radio" name="${ itemCouponAreaKey }-clear" id="${ itemCouponAreaKey }-clear" value="clear"><label for="${ itemCouponAreaKey }-clear"> ${op:message('M00899')}</label></li> <!-- 적용 안함 --> 
							 						<c:forEach items="${ orderItem.itemCoupons }" var="coupon">
							 							<li>
							 								<c:set var="couponKey" value="item-coupon-${coupon.couponUserId}-${ orderItem.userOrderItemSequence }" />
															<input type="radio" name="coupon-${ coupon.couponUserId }" class="itemCoupon" id="${ couponKey }" value="${ couponKey }"
																<c:if test="${ coupon.useCoupon eq 'Y' }">checked="checked"</c:if>>
															<input type="hidden" name="discount-amount-${ couponKey }-value" value="${ coupon.discountAmount }" />
															<input type="hidden" name="coupon-concurrently-${ couponKey }-value" value="${ coupon.couponConcurrently }" />
															<input type="hidden" name="coupon-pay-type-${ couponKey }-value" value="${ coupon.couponPayType }" />
															<input type="hidden" name="coupon-pay-${ couponKey }-value" value="${ coupon.couponPay }" />
															
															<label for="${ couponKey }" <c:if test="${ coupon.couponState eq '3' }">style="color:red;"</c:if>>
																${coupon.couponName}
																<c:if test="${ coupon.couponApplyStartDate != '' }">
																(
																	${ op:date(coupon.couponApplyStartDate) }
																	<c:if test="${ coupon.couponApplyEndDate != '' }">
																	 ~ ${ op:date(coupon.couponApplyEndDate) }
																	</c:if>
																)  
																</c:if>
																 
																<c:if test="${ coupon.couponConcurrently != '1' }">
																	(1개의 구입수량에만 할인 적용)
																</c:if>
																
																<span class="unit_orange" id="discount-amount-${ couponKey }-text">${op:numberFormat(coupon.discountAmount)} 원</span>
																<c:if test="${ coupon.couponState eq '3' }">
																	( ※  ${op:message('M01228')} <!-- 기존 할인 금액 -->: ${ op:numberFormat(coupon.prevDiscountAmount) } 원 )
																</c:if>
															</label>
															 
														</li> 
								 					</c:forEach>
								 				</ul>
										</c:when>
										<c:otherwise>
											적용 가능 상품 쿠폰은 없습니다.
										</c:otherwise>
									</c:choose>
					 			</td>
				 			</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table> 	 
		</div><!--board_list E-->
		
		<!-- 장바구니 쿠폰 할인-->
		<c:if test="${ !empty order.cartCoupons }">
			<h3 class="mt30"><span>${op:message('M01229')}</span></h3> <!-- 장바구니 쿠폰 할인 -->
			<div class="board_write cart-coupons">
				<table class="board_write_table">
					<caption>${op:message('M01229')}</caption>
					<colgroup>
						<col style="width:auto;"> 
					</colgroup>
					<tbody> 
						<tr> 
							<td>
								<div>
									<ul>
										<c:forEach items="${ order.cartCoupons }" var="coupon" varStatus="i">
											<li>
												<c:set var="couponKey" value="cart-coupon-${coupon.couponUserId}" />
												<input type="checkbox" name="coupon-${coupon.couponUserId}" id="${couponKey}" class="cartCoupon" title="${op:message('M00169')}" value="${ couponKey }"
													<c:if test="${ coupon.useCoupon eq 'Y' }">checked="checked"</c:if>>
												<input type="hidden" name="discount-amount-${ couponKey }-value"  value="${ coupon.discountAmount }" />
												<input type="hidden" name="coupon-pay-type-${ couponKey }" value="${ coupon.couponPayType }" />
												<input type="hidden" name="coupon-pay-${ couponKey }" value="${ coupon.couponPay }" />
												<input type="hidden" name="coupon-pay-restriction-${ couponKey }" value="${ coupon.couponPayRestriction }" />
												<label for="${couponKey}" <c:if test="${ coupon.useCoupon eq 'Y' }">style="color:red;"</c:if>>
													${coupon.couponName}
													<c:if test="${ coupon.couponApplyStartDate != '' }">
													(
														${ op:date(coupon.couponApplyStartDate) }
														<c:if test="${ coupon.couponApplyEndDate != '' }">
														 ~ ${ op:date(coupon.couponApplyEndDate) }
														</c:if>
													)  
													</c:if>
													 
													<c:if test="${ coupon.couponPayRestriction != '' }">
														${op:message('M01230')} <!-- 상품 금액 (상품 쿠폰 적용후)이 --> ${ op:numberFormat(coupon.couponPayRestriction) } ${op:message('M01231')} <!-- 원 이상일때 사용 가능 -->
													</c:if>
													 
													<span class="unit_orange" id="discount-amount-${ couponKey }-text">${op:numberFormat(coupon.discountAmount)} 원</span>
													<c:if test="${ coupon.couponState eq '3' }">
														( ※  ${op:message('M01228')} <!-- 기존 할인 금액 --> : ${ op:numberFormat(coupon.prevDiscountAmount) } 원 )
													</c:if>
												</label>
											</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr> 						 				 
					</tbody>					 
				</table>		 										 							
			</div><!--//board_write E-->
		</c:if>
		
		<!-- 포인트 할인-->
		<c:if test="${userId > 0}">
			<h3 class="mt30"><span>${op:message('M00620')}</span></h3> <!-- 포인트 할인 --> 
			<div class="board_write">
				<table class="board_write_table">
					<caption>${op:message('M00620')}</caption>
					<colgroup>
						<col style="width:auto;"> 
					</colgroup>
					<tbody> 
						<tr> 
							<td>
								<div>
									<ul class="point_list">
										<li> ${op:message('M00068')} <!-- 사용 포인트 --> : <input type="text" class="one _number totalPointDiscountAmountText" value="${ op:numberFormat(order.editPointUse) }"
			 								<c:if test="${ order.retentionPoint == 0 }">disabled="disabled"</c:if>>
											<label class="checkbox-inline"> 
									        	<input type="checkbox" id="retentionPointUseAll" 
									        		<c:if test="${ order.retentionPoint == 0 }">disabled="disabled"</c:if>> ${op:message('M00622')} <!-- 모두사용 --> (
									        	<c:if test="${ shopContext.config.pointUseMin > 0 }">
								      				${op:message('M00621')} <!-- 최소 사용 가능 포인트 --> : <span class="fb">${ op:numberFormat(shopContext.config.pointUseMin) }P,</span>
								      			</c:if>
									        	${op:message('M00623')} <!-- 사용 가능 포인트 --> : <span class="fb">${ op:numberFormat(order.retentionPoint) }</span>P )
									      	</label>
									      	<c:if test="${ shopContext.config.pointSaveType eq '2' }">
												<p class="pt5">※ ${op:message('M00872')} <!-- 결제시 포인트를 사용하시면 포인트가 지급되지 않습니다. --></p>  
											</c:if>
										</li>  
									</ul> 
								</div>
							</td> 
						</tr> 						 				 
					</tbody>					 
				</table>		 										 							
			</div><!--//board_write(포인트 할인) E-->
		</c:if>
		<!--추가할인금액, 추가배송료-->
		<table class="board_list_table01">
			<colgroup>
				<col style="width:50%;">		
				<col style="width:*">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">${op:message('M00812')}</th> <!-- 추가 할인금액 -->
					<th scope="col">${op:message('M01235')}</th> <!-- 추가 배송료 --> 
				</tr>
			</thead>
			<tbody>
				<tr>
					 <td>
					 	<div>
					 		<input type="text" class="half _number" name="addDiscountAmount" value="0">
					 	</div>
					 </td>
					 <td>
					 	<div>
					 		<input type="text" class="half _number" name="addDeliveryPrice" value="0">
					 	</div>
					 </td>
				</tr>
			</tbody>
		</table>
		<!--// 추가할인금액 끝--->
		
		<!-- total 금액-->
		<div class="total_wrap">
	 		<table cellpadding="0" cellspacing="0" class="total_price">
				<caption>${op:message('M00360')}</caption> <!-- 총 구매 금액 -->
				<colgroup>
				
					<c:set var="width" value="10%" />
					<c:if test="${ shopContext.config.taxDisplayType eq '2' }">
						<c:set var="width" value="9%" />
					</c:if>
				
					<col style="width:${ width };">
					<col style="width:2%;"> 
					<col style="width:${ width };">
					<c:if test="${ shopContext.config.taxDisplayType eq '2' }">
						<col style="width:2%;">
						<col style="width:${ width };">
					</c:if>
					<col style="width:2%;">
					<col style="width:${ width };">
					<col style="width:2%;">
					<col style="width:${ width };">
					<col style="width:2%;">
					<col style="width:${ width };">
					<col style="width:2%;">
					<col style="width:${ width };">
					<col style="width:2%;">
					<col style="width:${ width };">
					<col style="width:2%;">
					<col style="width:auto;">
				</colgroup> 
					  		
				<tbody>
					<tr>
						<th scope="col">${op:message('M00817')} (${ shopContext.config.taxDisplayTypeText })</th> <!-- 상품 합계 -->
						<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th> 
						<th scope="col">${op:message('M00818')}</th> <!-- 상품쿠폰 -->
						<c:if test="${ shopContext.config.taxDisplayType eq '2' }">
							<th rowspan="2" class="label"><img src="/content/images/btn/btn_plus.png" alt="minus">  </th>
							<th scope="col">${op:message('M00819')}</th> <!-- 세금 -->
						</c:if>
						<th rowspan="2" class="label"><img src="/content/images/btn/btn_plus.png" alt="minus">  </th>
						<th scope="col">${op:message('M01236')} (${op:message('M00608')})</th> <!-- 송료 (세금 포함) -->
						<th rowspan="2" class="label"><img src="/content/images/btn/btn_plus.png" alt="minus">  </th>
						<th scope="col">${op:message('M01235')}</th> <!-- 추가 배송료 --> 
						<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th>
						<th scope="col">${op:message('M00820')}</th> <!-- 장바구니 쿠폰 -->
						<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th>
						<th scope="col">${op:message('M00246')}</th> <!-- 포인트 -->
						<th rowspan="2" class="label"><img src="/content/images/btn/btn_minus.png" alt="minus">  </th>
						<th scope="col">${op:message('M00812')}</th> <!-- 추가 할인금액 -->
						<th rowspan="2" class="label"><img src="/content/images/btn/btn_total.png" alt="minus">  </th>
						<th scope="col">${op:message('M00629')} (${op:message('M00608')})</th><!-- 최종 결제 금액 (세금 포함) --> 
					</tr>
					<tr>
						<td><label id="totalItemPriceText">${ op:numberFormat(order.orderPrice.totalItemPrice) }</label> <span>${op:message('M00049')}</span></td>		 
						<td><label id="totalItemCouponDiscountAmountText">${ op:numberFormat(order.orderPrice.totalItemCouponDiscountAmount) }</label> <span>${op:message('M00049')}</span></td>
						<c:if test="${ shopContext.config.taxDisplayType eq '2' }"> 
							<td><label id="totalExcisePrice">${ op:numberFormat(order.orderPrice.totalExcisePrice) }</label> <span>${op:message('M00049')}</span></td>
						</c:if> 
						<td><label id="totalDeliveryCharge">${ op:numberFormat(order.orderPrice.totalDeliveryCharge) }</label> <span>${op:message('M00049')}</span></td>
						<td><label id="addDeliveryPriceText">0</label> <span>${op:message('M00049')}</span></td> 
						<td><label id="totalCartCouponDiscountAmountText">${ op:numberFormat(order.orderPrice.totalCartCouponDiscountAmount) }</label> <span>${op:message('M00049')}</span></td> 
						<td><label id="totalPointDiscountAmountText">${ op:numberFormat(order.orderPrice.totalPointDiscountAmount) }</label> <span>P</span></td>  
						<td><label id="addDiscountAmountText">0</label> <span>원</span></td>
						<td class="total_num"><label class="totalPayAmount">${ op:numberFormat(order.orderPrice.orderPayAmount) }</label> <span>${op:message('M00049')}</span></td>
					</tr>
				</tbody>
			</table>
		</div><!--//total_wrap E-->
		
		<div> 		
			<h3 class="mt30"><span>${op:message('M00315')} <!-- 주문자정보 --></span>
				<input type="checkbox" name="autoSignUp" value="1" id="autoSignUp" /><label for="autoSignUp" style="font-size:12px;color:red;">해당 정보를 이용하여 회원가입 합니다.</label>
			</h3>
			<!--주문자정보-->
			<table class="board_write_table" summary="${op:message('M00315')}">
				<caption>${op:message('M00315')}</caption>
				<colgroup>
					<col style="width:120px;">  
					<col style="width:90px;" />
					<col style="" />
				</colgroup>
				<tbody id="buyerInputArea">
					<%-- <tr>
						<td class="label">${op:message('M00105')} <!-- 회사명 --></td>
						<td colspan="2">
							<div>
								<form:input path="companyName" title="${op:message('M00105')}" cssClass="seven" maxlength="50" />
						 	</div>
						</td>
					</tr> --%>
					
					<tr>
						<td class="label"><span>*</span> ${op:message('M00311')} ${op:message('M00005')} <!-- 주문자 이름(한자) --> </td>
						<td colspan="2">
							<div>
								<form:input path="userName" title="${op:message('M00311')} ${op:message('M00005')}" cssClass="seven required" maxlength="50" />
						 	</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00016')} <!-- 전화번호 --></td>
						<td colspan="2">
							<div>
								<form:input path="phone1" title="${op:message('M00016')}" cssClass="two" maxlength="4" /> - 
								<form:input path="phone2" title="${op:message('M00016')}" cssClass="two" maxlength="4" /> -
								<form:input path="phone3" title="${op:message('M00016')}" cssClass="two" maxlength="4" />  
							</div>
						</td>
					</tr>
					<tr>
						<td class="label"><span>*</span> ${op:message('M00155')} <!-- 휴대폰번호 --></td>
						<td colspan="2">
							<div>
								<form:input path="mobile1" title="${op:message('M00016')}" cssClass="two required" maxlength="4" /> -
								<form:input path="mobile2" title="${op:message('M00016')}" cssClass="two required" maxlength="4" /> - 
								<form:input path="mobile3" title="${op:message('M00016')}" cssClass="two required" maxlength="4" />
								<p id="autoSignUpPhoneText" style="color:red"></p>
						 	</div>
						</td>
					</tr>	
					<tr>
						<td class="label"> ${op:message('M00314')} <!-- E-mail --></td>
						<td colspan="2">
							<div>
								<form:input path="email" title="${op:message('M00314')}" cssClass="seven" maxlength="50" />
								<p id="autoSignUpText" style="color:red"></p>
						 	</div>
						</td>
					</tr>
					<tr>
						<td class="label" rowspan="3"><span>*</span> ${op:message('M00118')} <!-- 주소 --></td>
						<td class="label"><span>*</span> ${op:message('M00115')} <!-- 우편번호 --></td>
						<td>
							<div>
								
								<form:input path="zipcode1" title="${op:message('M00115')}" cssClass="one required _number" readonly="true"  maxlength="3" /> -
								<form:input path="zipcode2" title="${op:message('M00115')}" cssClass="one required _number" readonly="true"  maxlength="3" />
								<a href="javascript:;" class="table_btn" onclick="openDaumPostcode()">${op:message('M00117')}</a>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label"><span>*</span> ${op:message('M00118')}<!-- 주소 --></td>
						<td>
							<div>
								<form:hidden path="sido" />
								<form:hidden path="sigungu" />
								<form:hidden path="eupmyeondong" />
								<form:input path="address" title="${op:message('M00118')}" cssClass="full required" maxlenght="100" readonly="true"  />
								<form:input path="addressDetail" title="${op:message('M00118')}" cssClass="full required" cssStyle="margin-top:10px;" maxlength="50" />
							</div>
						</td>
					</tr>				 
				</tbody>
			</table>
			
		</div>	<!--// 주문자정보 끝-->
		<!-- 수령자정보-->
		<div>
			<h3 class="mt30"><span>${op:message('M00316')} <!-- 수령자정보 --></span> <button type="button" class="btn btn-dark-gray btn-sm" onclick="infoCopy()"><span>주문자 정보와 동일</span></button></h3>				
			<table class="board_write_table" summary="${op:message('M00316')}">
				<caption>${op:message('M00316')}</caption>
				<colgroup>
					<col style="width:120px;">  
					<col style="width:90px;" />
					<col style="" />
				</colgroup>
				<tbody>
					
					<tr>
						<td class="label">${op:message('M00105')} <!-- 회사명 --></td>
						<td colspan="2">
							<div>
								<form:input path="receiveCompanyName" title="${op:message('M00105')}" cssClass="seven" maxlength="50" />
						 	</div>
						</td>
					</tr>
					
					<tr>
						<td class="label"><span>*</span> ${op:message('M00317')} ${op:message('M00005')} <!-- 수령자 이름(한자) --></td>
						<td colspan="2">
							<div>
								<form:input path="receiveName" title="${op:message('M00317')} ${op:message('M00005')}" cssClass="seven required" maxlength="50" />
						 	</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00016')} <!-- 전화번호 --></td>
						<td colspan="2">
							<div>
								<form:input path="receivePhone1" title="${op:message('M00016')}" cssClass="two" maxlength="4" /> - 
								<form:input path="receivePhone2" title="${op:message('M00016')}" cssClass="two" maxlength="4" /> -
								<form:input path="receivePhone3" title="${op:message('M00016')}" cssClass="two" maxlength="4" />
						 	</div>
						</td>
					</tr>
					<tr>
						<td class="label"><span>*</span> ${op:message('M00155')} <!-- 휴대폰번호 --></td>
						<td colspan="2">
							<div>
								<form:input path="receiveMobile1" title="${op:message('M00016')}" cssClass="two" maxlength="4" /> - 
								<form:input path="receiveMobile2" title="${op:message('M00016')}" cssClass="two" maxlength="4" /> - 
								<form:input path="receiveMobile3" title="${op:message('M00016')}" cssClass="two" maxlength="4" />
						 	</div>
						</td>
					</tr>	
					<tr>
						<td class="label" rowspan="2"><span>*</span> ${op:message('M00118')} <!-- 주소 --></td>
						<td class="label"><span>*</span> ${op:message('M00115')} <!-- 우편번호 --></td>
						<td>
							<div>
								
								<form:input path="receiveZipcode1" title="${op:message('M00115')}" cssClass="one required _number" readonly="true" maxlength="3" /> -
								<form:input path="receiveZipcode2" title="${op:message('M00115')}" cssClass="one required _number" readonly="true" maxlength="3" />
								<a href="javascript:;" class="table_btn" onclick="openDaumPostcode('receive')">${op:message('M00117')}</a>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label"><span>*</span> ${op:message('M00118')} <!-- 주소 --></td>
						<td>
							<div>
								<form:hidden path="receiveSido" />
								<form:hidden path="receiveSigungu" />
								<form:hidden path="receiveEupmyeondong" />
								<form:input path="receiveAddress" title="${op:message('M00118')}" cssClass="full required" readonly="true" maxlength="100" />
								<form:input path="receiveAddressDetail" title="${op:message('M00118')}" cssClass="full required" cssStyle="margin-top:10px;" maxlength="50" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label bL0">배송시 요구사항</td>
						<td colspan="2">
							<div>					 
								<form:input path="content" title="배송시 요구사항" class="full _filter" /> 
							</div>
						</td>
					</tr>				 
				</tbody>
			</table>
		</div>
		<!--//주문자정보 , 수령자정보  끝 -->
		
		<c:set var="payDefaultType" value="bank" />
		<div class="board_write input-view" id="add-amount-input-view">
			<h3 class="mt30">비용 결제</h3>
			<table class="board_write_table">
				<caption>기존 주문 정보</caption>
				<colgroup>
					<col style="width:150px;">
					<col />
					<col style="width:150px;">
					<col />
				</colgroup>
				<tbody> 
					<c:if test="${order.orderPayment.approvalType == 'card'}">
						<tr class="org-pay-cancel-for-card" style="display:none;">
							<th class="label">PG 취소 여부</th>
							<td colspan="3">
								<div>
									<input type="checkbox" name="orgPayCancel" value="1" id="orgPayCancel"/><label for="orgPayCancel">기존 PG 취소 (기존 PG 결제 취소는 PG사 관리 화면에서 해당 결제를 처리 하신후에 취소 바랍니다)</label>
									<br /> PG KEY : ${ order.orderPayment.orderPgData.pgKey }
								</div>
							</td>
						</tr>			
					</c:if>	
					<tr>
						<th class="label">결제 방식</th>
						<td>
							<div>
								<input type="radio" name="approvalType" value="bank" id="approvalType-bank" title="결제방식" ${op:checked(payDefaultType, 'bank')} /><label for="approvalType-bank">무통장 입금</label>
								<input type="radio" name="approvalType" value="ars" id="approvalType-ars" title="결제방식" ${op:checked(returnDefaultType, 'ars')} /><label for="approvalType-ars">ARS</label> 
								<input type="radio" name="approvalType" value="card-write" id="approvalType-card-write" title="결제방식" ${op:checked(returnDefaultType, 'card-write')} /><label for="approvalType-card-write">수기결제</label> 
							</div>
						</td>
						<th class="label">결제 금액</th>
						<td>
							<div>
								<label class="addPayAmount">
									${op:numberFormat(order.orderPrice.orderPayAmount)}원
								</label>
							</div>
						</td>
					</tr>
					<tr class="pay-type-bank-view pay-view" <c:if test="${payDefaultType != 'bank'}">style="display:none;"</c:if>>
						<th class="label">입금 계좌</th>
						<td colspan="3">
							<div>
								<c:choose>
									<c:when test="${not empty accountNumberList}">
										<select name="bankVirtualNo" class="required" title="입금계좌">
		 									<c:forEach items="${ accountNumberList }" var="list">
		 										<c:set var="accountValue" value="${ list.bankName } 계좌 : ${ list.accountNumber } (${ list.accountHolder })" />
		 										<option value="${ accountValue }">${accountValue}</option>
		 									</c:forEach>
								   		</select>
									</c:when>
									<c:otherwise>
										<input type="text" class="full required" name="bankVirtualNo" title="입금 계좌" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr class="pay-type-bank-view pay-view" <c:if test="${payDefaultType != 'bank'}">style="display:none;"</c:if>>
						<th class="label">입금자 명의</th>
						<td colspan="3">
							<div>
								<input type="text" class="full required" name="bankInName" title="입금자 명의" />
							</div>
						</td>
					</tr>
					<tr class="pay-type-bank-view pay-view" <c:if test="${payDefaultType != 'bank'}">style="display:none;"</c:if>>
	 					<th class="label">입금예정일</th>
	 					<td colspan="3">
	 						<div>
								<form:select path="bankDate" title="입금예정일">
									<c:forEach items="${ bankInDateList }" var="item">
										<form:option value="${ op:date(item) }" />
									</c:forEach>
						   		 </form:select>
							</div>
	 					</td>
	 				</tr>
				</tbody>
			</table>
		</div>
		
		<div class="board_write_type">
			<h3 class="mt30">현금영수증</h3>  
	 		<table cellpadding="0" cellspacing="0" summary="" class="board_write_table">
	 			<caption>현금영수증</caption>
	 			<colgroup> 
	 				<col style="width:158px;">	
	 				<col style="width:auto;">	 				
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<th class="label" rowspan="2">현금영수증 발행</th>
	 					<td>
	 						<div class="receipt">
	 							<form:radiobutton path="cashReceiptsType" value="1" label=" 사업자증빙용" />
	 							<form:radiobutton path="cashReceiptsType" value="2" label=" 개인소득공제" />
	 							<form:radiobutton path="cashReceiptsType" value="0" label=" 발급안함" />
	 						</div>
	 					</td>
	 				</tr>
	 				<tr>
	 					<td>
	 						<div class="hp_area cashReceiptsType" id="cashReceiptsType2" style="display:none;"> 
	 							<p>휴대폰번호</p>
	 							<div class="input_wrap col-w-10">
	 								<form:input path="cashReceiptsPhone1" maxlength="4" />		 	 			 	 							 
	 								<span class="connection"> - </span>							    
							    	<form:input path="cashReceiptsPhone2" maxlength="4" />
	 								<span class="connection"> - </span>
	 								<form:input path="cashReceiptsPhone3"  maxlength="4" /> 
	 							</div>			 
	 						</div>
	 						<div class="hp_area cashReceiptsType" id="cashReceiptsType1" style="display:none;"> 
	 							<p>사업자 번호</p>
	 							<div class="input_wrap col-w-10">
	 								<form:input path="cashReceiptsBusinessNumber1" maxlength="3" />		 	 			 	 							 
	 								<span class="connection"> - </span>							    
							    	<form:input path="cashReceiptsBusinessNumber2" maxlength="2" />
	 								<span class="connection"> - </span>
	 								<form:input path="cashReceiptsBusinessNumber3" maxlength="5" /> 
	 							</div>	
	 						</div>
	 					</td> 
	 				</tr>
	 			</tbody>
	 		</table><!--//write_pay E-->	 	
	 	</div> <!-- // board_write_type01 -->
	 	
	 	<div class="board_write_type">
			<h3 class="mt30">세금계산서</h3>  
	 		<table cellpadding="0" cellspacing="0" summary="" class="board_write_table">
	 			<caption>세금계산서</caption>
	 			<colgroup> 
	 				<col style="width:158px;">	
	 				<col style="width:auto;">
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<th class="label" rowspan="2">세금계산서</th>
	 					<td>
	 						<div class="taxInvoice">
	 							<form:radiobutton path="taxInvoiceType" value="1" label=" 발급" />
	 							<form:radiobutton path="taxInvoiceType" value="0" label=" 발급안함" />
	 						</div>
	 					</td>
	 				</tr>
	 				<tr>
	 					<td>
	 						<div class="hp_area taxInvoiceType" id="taxInvoiceType1" style="display:none;"> 
	 							<div class="input_wrap col-w-10">
	 								<table>
							 			<colgroup> 
							 				<col style="width:120px;">	
							 				<col style="width:auto;">
							 				<col style="width:120px;">	
							 				<col style="width:auto;">
							 			</colgroup>
	 									<tbody>	 									
		 									<tr>
		 										<th style="text-align: left;">사업자번호</th>
		 										<td><form:input path="taxInvoiceBusinessNumber" cssClass="full" maxlength="12"/></td>
		 										<td colspan="2"/>
		 									</tr>	 									
		 									<tr>
		 										<th style="text-align: left;">상호</th>
		 										<td><form:input path="taxInvoiceBusinessCompanyName" cssClass="full" maxlength="500"/></td>
		 										<td colspan="2"/>
		 									</tr>
		 									<tr>
		 										<th style="text-align: left;">대표자명</th>
		 										<td><form:input path="taxInvoiceBusinessBossName" cssClass="full" maxlength="100"/></td>
		 										<td colspan="2"/>
		 									</tr>
		 									<tr>
		 										<th style="text-align: left;">업태</th>
		 										<td><form:input path="taxInvoiceBusinessStatus" cssClass="full" maxlength="50"/></td>
		 										<th style="text-align: left; padding-left: 20px;">종목</th>
		 										<td><form:input path="taxInvoiceBusinessStatusType" cssClass="full" maxlength="50"/></td>
		 									</tr>
		 									<tr>
		 										<th style="text-align: left;">사업장 소재지</th>
		 										<td><form:input path="taxInvoiceBusinessLocation" cssClass="full" maxlength="100"/></td>
		 										<td colspan="2"/>
		 									</tr>
		 									<tr>
		 										<th style="text-align: left;">이메일</th>
		 										<td><form:input path="taxInvoiceBusinessEmail" cssClass="full" maxlength="100"/></td>
		 										<td colspan="2"/>
		 									</tr>
	 									</tbody>
	 								</table> 
	 							</div>			 
	 						</div>
	 					</td> 
	 				</tr>
	 			</tbody>
	 		</table><!--//write_pay E-->	 	
	 	</div> <!-- // board_write_type01 -->
	 	
		<c:if test="${not empty orgAdminMessage}">
			<div>
				<h3 class="mt30"><span>기존 주문 관리자 메모</span></h3>
				<table class="board_write_table" summary="${op:message('M00322')}">
					<caption>${op:message('M00322')}</caption>
					<colgroup>
						<col style="width:160px;">
						<col style="width:*;">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">* ${op:message('M00323')} <!-- 관리자 메모 --></td>
							<td>
								<div>
								 	<textarea cols="30" rows="6" name="orgAdminMessage" style="width:100%" title="${op:message('M00323')}">${ orgAdminMessage }</textarea>
							 	</div>
							</td>
						</tr>
					</tbody>
				</table>
					
			</div>
		</c:if>
		
		<div>
			<h3 class="mt30"><span>신규 생성 주문 관리자 메모</span></h3>
			<table class="board_write_table" summary="${op:message('M00322')}">
				<caption>${op:message('M00322')}</caption>
				<colgroup>
					<col style="width:160px;">
					<col style="width:*;">
				</colgroup>
				<tbody>
					<tr>
						<td class="label">* ${op:message('M00323')} <!-- 관리자 메모 --></td>
						<td>
							<div>
							 	<textarea cols="30" rows="6" name="adminMessage" style="width:100%" title="${op:message('M00323')}">${ order.adminMessage }</textarea>
						 	</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00323')}<br/>(물류센터 공유)</td>
						<td>
							<div>
							 	<textarea cols="30" rows="6" name="adminMessageEtc" style="width:100%" title="${op:message('M00323')}">${ order.adminMessageEtc }</textarea>
						 	</div>
						</td>
					</tr>
				</tbody>
			</table>
				
		</div>
			
		<div class="popup_btns">
			<button type="submit" class="btn btn btn-active2" style="width:auto;height:44px;">${op:message('M01237')}</button> <!-- 주문상품 변경내용 저장 --> 
		</div>
		
		</div><!--//popup_contents E-->
		<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 -->
	</form:form>
	
	
	<div class="find_item_layer" id="find_item_layer">
		<div class="popup_wrap">
			<h1 class="popup_title">${op:message('M01238')}</h1> <!-- 주문상품 검색 --> 
			<div class="popup_contents">	
				<form:form modelAttribute="itemParam">
					<form:hidden path="page" />
					<div class="board_write">					
						<table class="board_write_table" summary="관련상품등록">
							<caption>${op:message('M01238')}</caption>
							<colgroup>
								<col style="width: 100px" />
								<col style="" />
							</colgroup>
							<tbody> 
								<tr>
									<td class="label">${op:message('M00270')}</td> <!-- 카테고리 --> 
									<td>
										<div>
											<form:select path="categoryGroupId" class="category">
												<option value="0">= ${op:message('M00076')} =</option> <!-- 팀/그룹 -->
												<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
													<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
														<optgroup label="${categoriesTeam.name}">
														<c:forEach items="${categoriesTeam.categoriesGroupList}" var="categoriesGroup">
															<c:if test="${categoriesGroup.categoryGroupFlag == 'Y'}">
																<form:option value="${categoriesGroup.categoryGroupId}" label="${categoriesGroup.groupName}" />
															</c:if>
														</c:forEach>
														</optgroup>
													</c:if>
												</c:forEach>
												
											</form:select>
											
											<form:select path="categoryClass1" class="category">
											</form:select>
											
											<form:select path="categoryClass2" class="category">
											</form:select>
											
											<form:select path="categoryClass3" class="category">
											</form:select>
											
											<form:select path="categoryClass4" class="category">
											</form:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
									<td>
										<div>
											<form:select path="where" title="${op:message('M01186')}"> <!-- 상세검색 선택 --> 
												<form:option value="ITEM_NAME">${op:message('M00018')}</form:option> <!-- 상품명 -->
												<form:option value="ITEM_USER_CODE">${op:message('M00783')}</form:option> <!-- 상품코드 -->
											</form:select>
											<form:input path="query" class="w360" title="${op:message('M01140')}" /> <!-- 상세검색 입력 -->
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div> <!--// board_write -->
					
					
					<div class="btn_all">
						<div class="btn_left">
							
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
						</div>
					</div> <!-- // btn_all -->
				</form:form> 
		
				<div id="find-items" style="height:500px;"></div>
			</div>
	 
			<a href="javascript:closeFindItemLayer();" class="popup_close_layer">${op:message('M00353')}</a> <!-- 창 닫기 -->
		</div>
	</div>
</div>

<form id="findItemForm" method="post" action="/opmanager/order/guest-new-order-find-item">
	<input type="hidden" name="where" />
	<input type="hidden" name="query" />
</form>

<script src="https://spi.maps.daum.net/imap/map_js_init/postcode.js"></script>
<script type="text/javascript">

//최소 결제 금액
var minimumPaymentAmount = '${shopContext.config.minimumPaymentAmount}';
if ($.isNumeric(minimumPaymentAmount) == false) {
	minimumPaymentAmount = 0;
}
minimumPaymentAmount = parseInt(minimumPaymentAmount);



$(function(){
	var isSubmit = false;
	$("#order").validator({
		'requiredClass' : 'required',
		'submitHandler' : function() {
			
			if ($('table.board_list_table > tbody > tr.order_item').size() == 0) {
				alert('상품을 추가해 주세요.');
				return false;
			}
			
			setTotalPrice();
			
			setCouponData();
			
			// 중복 등록 된다내?
			if (isSubmit == true) {
				return false;
			}
			
			if (autoSignUpEmailCheck() == false) {
				return false;
			}
			
			if (autoSignUpPhoneNumberCheck() == false) {
				return false;
			}
			
			isSubmit = true;
			
		}
	});
	
	$('#autoSignUp').on('click', function(){
		autoSignUpPhoneNumberCheck();
		autoSignUpEmailCheck();
	});
	
	$('#email').on('focusout', function(){
		autoSignUpEmailCheck();
	});
	
	$('#mobile1, #mobile2, #mobile3').on('focusout', function(){
		autoSignUpPhoneNumberCheck();
	});
	
	$(document).on('keydown', 'input:not(textarea)', function(e){
		if (e.keyCode == 13) {
			return false;
		}
	});
	
	$('#searchQuery').on('keydown', function(e){
		if ($(this).val() != '') {
			if (e.keyCode == 13) {
				searchItem();
			}
		}
	});
	
	$('input[name="returnApprovalType"]').on('click', function(){
		setTotalPrice();
	});
	
	$('input[name="approvalType"]').on('click', function(){
		setTotalPrice();
		setHeight();
	});
	
	// 팀/그룹 ~ 4차 카테고리 이벤트 
	ShopEventHandler.categorySelectboxChagneEvent();  
	
	Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');

	$('#itemParam').on('submit', function(){
		$.post('/opmanager/order/find-item', $(this).serialize(), function(html){
			$('#find-items').html(html);
		}, 'html');
		
		return false;
	});
	
	// 포인트 사용 - 직접 입력
	$('input.totalPointDiscountAmountText').on('focusout', function(){
		$('input#retentionPointUseAll').prop('checked', false);
		
		var usePoint = $(this).val().replace(",", '');
		usePoint = getPointDiscountAmount(usePoint);
		
		setPointDiscountAmountText(usePoint);
		
		setTotalPrice();
	});
	  
	// 포인트 사용 - 전체 사용
	$('input#retentionPointUseAll').on('click', function(){
		
		var usePoint = 0;
		if ($(this).prop('checked') == true) {
			usePoint = '${order.retentionPoint}';
		}

		usePoint = getPointDiscountAmount(usePoint);
		setPointDiscountAmountText(usePoint);
		setTotalPrice();
		
	});
	
	// 추가 할인금액 입력
	$('input[name="addDiscountAmount"]').on('focusout', function(){
		var addDiscountAmount = $(this).val();
		
		if (addDiscountAmount == '') {
			addDiscountAmount = 0;
		}
		
		addDiscountAmount = getAddDiscountAmount(addDiscountAmount);
		
		setAddDiscountAmountText(addDiscountAmount);
		
		setTotalPrice();
	});
	
	$('input[name="addDeliveryPrice"]').on('focusout', function(){
		var addDeliveryPrice = $(this).val();
		
		setAddDeliveryPriceText(addDeliveryPrice);
		
		setTotalPrice();
	});
	
	$('input[name="orgPayCancel"]').on('click', function(){
		setTotalPrice();
	});
	
	// 추가금액 관련 Input창 노출
	changeAddPaymentInputAreaView();
	
	setOrderLnbCount();
	
	
	$('input[name="cashReceiptsType"]').on('click', function(){
		if ($(this).val() == '1') {
			$('div.cashReceiptsType').hide();
			$('div#cashReceiptsType1').show();
		} if ($(this).val() == '2') {
			$('div.cashReceiptsType').hide();
			$('div#cashReceiptsType2').show();
		} if ($(this).val() == '0') {
			$('div.cashReceiptsType').hide();
			$('div#cashReceiptsType2').hide();
		}
	});
	
	$('input[name="taxInvoiceType"]').on('click', function(){
		if ($(this).val() == '0') {
			$('div#taxInvoiceType1').hide();
			$('#taxInvoiceBusinessNumber').val('');
			$('#taxInvoiceBusinessCompanyName').val('');
			$('#taxInvoiceBusinessBossName').val('');
			$('#taxInvoiceBusinessStatus').val('');
			$('#taxInvoiceBusinessStatusType').val('');
			$('#taxInvoiceBusinessLocation').val('');
			$('#taxInvoiceBusinessEmail').val('');
		} if ($(this).val() == '1') {
			$('div#taxInvoiceType1').show();
		}
		
	});
	
});

function autoSignUpPhoneNumberCheck() {
	var phoneNumber = $.trim($('#mobile1').val()) + "-" + $.trim($('#mobile2').val()) + "-" + $.trim($('#mobile3').val());
	$_autoSignUpText = $('#autoSignUpPhoneText');
	$_autoSignUpText.html('');
	if ($('#autoSignUp').prop('checked') == true) { 
		
		if ($.trim(phoneNumber) == '--') {
			$_autoSignUpText.html('전화번호를 입력하세요.');
			return false;
		}
		
		if (userAvailabilityCheck(phoneNumber, 'phoneNumber') == false) {
			$_autoSignUpText.html("이미 등록된 전화번호입니다. 같은 전화번호로 회원등록은 불가능합니다.");
			$('#mobile1').focus();
			return false;
		}
		
	}
}

function autoSignUpEmailCheck() {
	var email = $('#email').val();
	$_autoSignUpText = $('#autoSignUpText');
	$_autoSignUpText.html('');
	
	if ($('#autoSignUp').prop('checked') == true) { 
		
		if ($.trim(email) != '') {
			if (!$.validator.patterns._email.test(email)) {
				$_autoSignUpText.html('이메일 정확히 입력하세요.');
				return false;
			}
			
			if (userAvailabilityCheck(email, 'email') == false) {
				$_autoSignUpText.html("이미 등록된 메일주소입니다. 같은 메일주소로 회원등록은 불가능합니다.");
				$('#email').focus();
				return false;
			}
		} else {
			$_autoSignUpText.html("이메일 주소를 입력하지 않으시는 경우 wjb1588@naver.com메일 주소로 임의 가입됩니다.");
		}
	}
	 
	return true;
}

function userAvailabilityCheck(value, type) {
	var params = {
		'type'		: type,
		'value' 	: value
	};
	
	var returnValue = false;
	$.ajaxSetup({'async': false});
	$.post("/opmanager/user/user-availability-check", params, function(response){
		Common.loading.hide();
		returnValue = response.availability;
	}, 'json').error(function(e){
		alert(e.message);
	});

	return returnValue;
}

function setOrderLnbCount() {
	
	$.post('/opmanager/order/orderLnbCount', null, function(resp){
		
		if (resp.info == undefined) {
			return;
		}
		
		$.each(resp.info, function(key, state){
			$object = $('ul.lnbs a[href="/opmanager/order/list/' + state.key + '"]');
			if ($object.size() > 0) {
				var html = $object.html();
				$object.html(html + ' (' + state.count + ')');
			}
		});
	}, 'json');
	
}

function uppercase(text) {
	if (text == '' || text == undefined) return text;
	return text.substring(0, 1).toUpperCase() + text.substring(1);
}

function infoCopy() {
	$.each($('input, select', $('#buyerInputArea')), function() {
		var name = uppercase($(this).attr('id'));
		name = 'receive' + name;
		
		if (name == 'receiveUserName') {
			name = 'receiveName';
		}
		
		if ($('#' + name).size() == 1) {
			$('#' + name).val($(this).val());
		}
	});
}

function changeAddPaymentInputAreaView(type) {
	
	if (type == undefined) {
		if ($('input[name="approvalType"]').size() == 0) {
			return;
		}
		
		type = $('input[name="approvalType"]:checked').val();
	}
	
	$('#add-amount-input-view').show().find('input').addClass('required');
	$('.pay-view').hide().find('input').removeClass('required');
	if (type == 'bank') {
		$('.pay-type-bank-view').show().find('input').addClass('required');
	} else {
		$('.pay-type-bank-view').find('input').removeClass('required');
	}
	
	var totalPayAmount = parseInt($('input[name="orderPayAmount"]').val());
	$('.addPayAmount').html(Common.numberFormat(totalPayAmount) + "원");
}

function setCouponData() {

	var couponInputs = '';
	$.each($('input.itemCoupon:checked'), function(){
		if ($(this).prop('disabled') == false) {
			var key = $(this).val();
			couponInputs += '<input type="hidden" name="useCouponKeys" value="'+ key +'" class="useCoupon" />';
		}
	});
	
	$.each($('input[class="cartCoupon"]:checked'), function(){
		if ($(this).prop('disabled') == false) {
			var key = $(this).val();
			couponInputs += '<input type="hidden" name="useCouponKeys" value="'+ key +'" class="useCoupon" />';
		}
	});
	
	$('.couponHideFieldArea').html(couponInputs);
}

function setPointDiscountAmountText(usePoint) {
	$('input.totalPointDiscountAmountText').val(Common.numberFormat(usePoint));
	$('label#totalPointDiscountAmountText').html(Common.numberFormat(usePoint));
	
	$('input[name="totalPointDiscountAmount"]').val(usePoint);
	
	var afterUsePoint = parseInt($('input[name="afterUsePoint"]').val());
	var differencePoint = afterUsePoint - usePoint;
	if (differencePoint < 0) differencePoint = 0;
	$('input[name="differencePoint"]').val(differencePoint);
	$('label#differencePointText').html(Common.numberFormat(differencePoint));
}

// 상품 금액 변경
$(document).on('focusout', 'tbody#order_items > tr.order_item input', function(){

	$orderItem = $(this).parent().parent().parent();
	
	setItemCouponDiscountAmount($orderItem);
	
	setItemPrice($orderItem);
	
	setCartCouponsDiscountAmount();
	
	setTotalPrice();

}); 

//상품쿠폰 선택
$(document).on('click', 'tbody#order_items > tr.item_coupons input[type="radio"]', function(){
	$('input[type=radio]', $(this).parent().parent()).not(this).prop('checked', false);
	
	// 상품 쿠폰 적용 변경으로 상품 전채에 대한 할인 적용금액 조정
	setOrderItemsCouponDiscuntAmount();
	
	setCartCouponsDiscountAmount();
	
	setTotalPrice();
});

//장바구니 쿠폰 선택
$(document).on('click', 'div.cart-coupons input[type="checkbox"]', function(){
	
	setCartCouponsDiscountAmount();
	
	setTotalPrice();
	
});

function setAddDeliveryPriceText(addDeliveryPrice) {
	addDeliveryPrice = addDeliveryPrice == '' ? 0 : addDeliveryPrice;
	
	$('input[name="addDeliveryPrice"]').val(addDeliveryPrice);
	$('label#addDeliveryPriceText').html(Common.numberFormat(addDeliveryPrice));
}

function getAddDeliveryPrice() {
	return parseInt($('input[name="addDeliveryPrice"]').val());
}

function getAddDiscountAmount(addDiscountAmount) {
	var orderPayAmount = getOrderPay();
	addDiscountAmount = parseInt(addDiscountAmount);
	if (addDiscountAmount == '') {
		addDiscountAmount = 0;
	}
	
	if (orderPayAmount < addDiscountAmount) {
		return 0;
	}
	
	return addDiscountAmount;
}

function setAddDiscountAmountText(addDiscountAmount) {
	$('input[name="addDiscountAmount"]').val(addDiscountAmount);
	$('label#addDiscountAmountText').html(Common.numberFormat(addDiscountAmount));
}

function setCartCouponsDiscountAmount() {
	$cartCoupons = $('div.cart-coupons input[type="checkbox"]');
	if ($cartCoupons.size() > 0) {
		var itemPriceData = getItemPriceData();
		var totalItemPayAmount = itemPriceData.totalItemPrice - itemPriceData.totalItemCouponDiscountAmount;
		
		$.each($cartCoupons, function(){
			$coupon = $(this);
			var key = $coupon.val();
			var couponPayType = $('input[name="coupon-pay-type-' + key + '"]').val();
			var couponPay = $('input[name="coupon-pay-' + key + '"]').val();
			var couponPayRestriction = $('input[name="coupon-pay-restriction-' + key + '"]').val();
			var discountAmount = 0;
			if ($.isNumeric(couponPayRestriction) == false) {
				couponPayRestriction = 0;
			}
			couponPayRestriction = parseInt(couponPayRestriction);
			
			$coupon.prop('disabled', false);
			
			if ($.isNumeric(couponPay) == false) {
				$coupon.prop('disabled', true);
			} else {
				if (couponPayRestriction > totalItemPayAmount) {
					$coupon.prop('disabled', true);
				}
				
				if (couponPayType == '1') {
					discountAmount = couponPay;
				} else {
					if (couponPay > 100) {
						$coupon.prop('disabled', true);
					}
					
					discountAmount = totalItemPayAmount * (couponPay / 100);
				}
				
				if (discountAmount > totalItemPayAmount) {
					$coupon.prop('disabled', true);
				}
				
				$('input[name="discount-amount-' + key + '-value"]', $('div.cart-coupons')).val(discountAmount);
				$('#discount-amount-' + key + '-text', $('div.cart-coupons')).html(Common.numberFormat(discountAmount) + ' 원');
			}
		});
	}
}

// 상품의 개별 금액을 조회함
function getItemPrice($orderItem, oneItemPrice) {
	var quantity = getInputValue($('input[name="quantitys"]', $orderItem));
	var totalRequiredOptionsPrice = getInputValue($('input[name="totalRequiredOptionsPrices"]', $orderItem));
	var itemPrice = getInputValue($('input[name="itemPrices"]', $orderItem));
	
	if (oneItemPrice == undefined) {
		oneItemPrice = false;
	}
	
	var price = (itemPrice + totalRequiredOptionsPrice);
	
	if (oneItemPrice == false) {
		price = price * quantity;
	}
	
	return price;
}

// 상품들의 판매가격 및 할인 금액 조정
function setOrderItemsCouponDiscuntAmount() {
	$items = $('tbody#order_items > tr.order_item');
	if ($items.size() > 0) {
		$.each($items, function(){
			setItemPrice($(this));
		});
	}
}

// 전체 상품 금액 조회
function getItemPriceData() {

	var totalItemPrice = 0;
	var totalItemCouponDiscountAmount = 0;
	var totalItemPayAmount = 0;
	
	$.each($('tbody#order_items > tr.order_item'), function() {
		var itemPrice = getItemPrice($(this));
		var itemCouponDiscountAmount = getItemCouponDiscountAmount($(this));

		totalItemPrice += itemPrice;
		totalItemCouponDiscountAmount += itemCouponDiscountAmount
		 
		var itemPayAmount = itemPrice - itemCouponDiscountAmount;
		totalItemPayAmount = totalItemPayAmount + itemPayAmount;
	});
	
	var totalTax = getItemTax(totalItemPayAmount);
	return {
		'totalItemPrice' : totalItemPrice,
		'totalItemCouponDiscountAmount' : totalItemCouponDiscountAmount,
		'totalTax' : totalTax
	};
}

// 상품 금액 View 셋팅
function setItemPrice($orderItem) {
	
	var itemPrice = getItemPrice($orderItem);
	
	var itemCouponDiscountAmount = getItemCouponDiscountAmount($orderItem);
	var itemPayAmount = itemPrice - itemCouponDiscountAmount;

	$('div.itemCouponDiscountAmount', $orderItem).html(Common.numberFormat(itemCouponDiscountAmount));
	$('div.itemPayAmount', $orderItem).html(Common.numberFormat(itemPayAmount));
}

// 상품에 사용할수 있는 쿠폰 영역 조회
function getItemCouponAreaByOrderItem($orderItem) {
	return $('tr#' + $orderItem.attr('id') + '_coupons');
}

function setTotalPrice() {
	var itemPriceData = getItemPriceData();
	
	$('label#totalItemPriceText').html(Common.numberFormat(itemPriceData.totalItemPrice));
	$('input[name="totalItemPrice"]').val(itemPriceData.totalItemPrice);
	
	$('label#totalItemCouponDiscountAmountText').html(Common.numberFormat(itemPriceData.totalItemCouponDiscountAmount));
	$('input[name="totalItemCouponDiscountAmount"]').val(itemPriceData.totalItemCouponDiscountAmount);

	$('label#totalExcisePrice').html(Common.numberFormat(itemPriceData.totalTax));
	$('input[name="totalExcisePrice"]').val(itemPriceData.totalTax);
	
	var totalCartCouponDiscountAmount = getCartCouponDiscountAmount();
	
	$('label#totalCartCouponDiscountAmountText').html(Common.numberFormat(totalCartCouponDiscountAmount));
	$('input[name="totalCartCouponDiscountAmount"]').val(totalCartCouponDiscountAmount);
	
	var deliveryCharge = getTotalDeliveryPrice();
	
	$('label#totalDeliveryCharge').html(Common.numberFormat(deliveryCharge));
	$('input[name="totalDeliveryCharge"]').val(deliveryCharge);
	
	// 포인트 할인 금액
	var totalPointDiscountAmount = $('input[name="totalPointDiscountAmount"]').val();
	if ($.isNumeric(totalPointDiscountAmount) == false) {
		totalPointDiscountAmount = 0;
	}
	totalPointDiscountAmount = parseInt(totalPointDiscountAmount);

	// 포인트 사용 가능금액 조정 - 금액 변동으로 인한 포인트 사용 금액이 잘못되는 경우가 생김.
	var point = getPointDiscountAmount(totalPointDiscountAmount);
	if (point != totalPointDiscountAmount) {
		$('input#retentionPointUseAll').prop('checked', false);
		setPointDiscountAmountText(point);
		totalPointDiscountAmount = point;
	}
	
	var orgAddDiscountAmount = parseInt($('input[name="addDiscountAmount"]').val());
	var addDiscountAmount = getAddDiscountAmount(orgAddDiscountAmount);
	
	if (orgAddDiscountAmount != addDiscountAmount) {
		setAddDiscountAmountText(addDiscountAmount);
	}
	
	var totalPayAmount = itemPriceData.totalItemPrice - itemPriceData.totalItemCouponDiscountAmount + deliveryCharge + itemPriceData.totalTax - totalCartCouponDiscountAmount - totalPointDiscountAmount - addDiscountAmount + getAddDeliveryPrice();
	$('label.totalPayAmount').html(Common.numberFormat(totalPayAmount));
	$('input[name="orderPayAmount"]').val(totalPayAmount);
	
	changeAddPaymentInputAreaView();
}
 
function getCartCouponDiscountAmount() {
	var totalDiscountAmount = 0;
	$cartCoupons = $('div.cart-coupons input[type="checkbox"]:checked');
	if ($cartCoupons.size() > 0) {
		$.each($cartCoupons, function() {
			$coupon = $(this);
			var couponKey = $coupon.val();
			
			if (couponKey == 'clear') {
				return 0;
			}
			
			if ($coupon.prop('disabled') == false) {
				var discountAmount = $('input[name="discount-amount-'+ couponKey +'-value"]').val();
				if (discountAmount == undefined) {
					discountAmount = 0;
				}
				
				totalDiscountAmount = totalDiscountAmount + parseInt(discountAmount);
			}
		});
	}
	
	return parseInt(totalDiscountAmount);
}

function getItemCouponDiscountAmount($orderItem) {
	var discountAmount = 0;
	var couponUserId = 0;
	$couponArea = getItemCouponAreaByOrderItem($orderItem);
	if ($couponArea.size() > 0) {
		$coupon = $('input[type="radio"]:checked', $couponArea);
		if ($coupon.size() > 0) {
			var couponKey = $coupon.val();
			
			if (couponKey == 'clear') {
				return 0;
			}
			
			if ($coupon.prop('disabled') == false) {
				discountAmount = $('input[name="discount-amount-'+ couponKey +'-value"]', $couponArea).val();
				
				var temp = couponKey.split("-");
				if (temp.length == 4) {
					couponUserId = temp[3];
				}
			}
		}
	}
	
	
	return parseInt(discountAmount);
}

// 상품별 상품 쿠폰 할인금액 변경
function setItemCouponDiscountAmount($orderItem) {
	var itemPrice = getItemPrice($orderItem, true);
	var quantity = getInputValue($('input[name="quantitys"]', $orderItem));

	$couponArea = getItemCouponAreaByOrderItem($orderItem);
	if ($couponArea.size() > 0) {
		
		$coupons = $('input[type="radio"]', $couponArea);
		
		$.each($coupons, function(){
			$coupon = $(this);

			$coupon.prop('disabled', false);
			
			var discountAmount = 0;
			var couponKey = $coupon.val();
			if (couponKey != 'clear') {
				var couponPayType = $('input[name="coupon-pay-type-'+ couponKey +'-value"]', $couponArea).val();
				var couponPay = parseInt($('input[name="coupon-pay-'+ couponKey +'-value"]', $couponArea).val());
				var couponConcurrently = parseInt($('input[name="coupon-concurrently-'+ couponKey +'-value"]', $couponArea).val());
				
				if ($.isNumeric(couponPay) == false) {
					$coupons.prop('disabled', true);
				} else {
					
					couponConcurrently = couponConcurrently == '' ? '1' : couponConcurrently;
					
					if (couponPayType == '1') {
						discountAmount = couponPay;
					} else {
						if (couponPay > 100) {
							$coupon.prop('disabled', true);
						}
						
						discountAmount = itemPrice * (couponPay / 100);
					}
					
					if (discountAmount > itemPrice) {
						$coupon.prop('disabled', true);
					}
					
					if (couponConcurrently == '1') {
						discountAmount = discountAmount * quantity;
					}
				}
				
				$('input[name="discount-amount-' + couponKey + '-value"]', $couponArea).val(discountAmount);
				$('#discount-amount-' + couponKey + '-text', $couponArea).html(Common.numberFormat(discountAmount) + ' 원');
				
			}
			
			
		});
			
	} 
} 

//상품별 소비세 계산
function getItemTax(itemPayAmount) {
	
	// 소비세 미포함
	var taxDisplayType = '${shopContext.config.taxDisplayType}';
	
	var excisePrice = 0;
	
	if (taxDisplayType == '2') {
		var tax = '${shopContext.config.tax}';
		var taxType = '${shopContext.config.taxType}';
		
		var ja = Math.pow(10, 0);
		excisePrice = (itemPayAmount * (tax / 100));
		
		/*
		if (taxType == '1') {
			excisePrice =  Math.round((itemPayAmount * tax / 100) * ja) / ja;
		} else {
			excisePrice =  Math.floor((itemPayAmount * tax / 100) * ja) / ja;
		}
		*/
	}
	
	return parseInt(excisePrice);
}

// input의 value를 int형으로 변환하고 공백인경우 0으로 셋팅
function getInputValue($obj) {
	var value = $obj.val();
	if (value == '') {
		value = 0;
	}
	
	if ($.isNumeric(value) == false) {
		value = 0;
	}
	
	value = parseInt(value);
	$obj.val(value);
	return value;
}



function getPointDiscountAmount(usePoint) {
	if ($.isNumeric(usePoint)) {
		var retentionPoint = '${order.retentionPoint}';
		var pointUseMin = '${shopContext.config.pointUseMin}';
		
		if ($.isNumeric(pointUseMin) == false) {
			pointUseMin = 0;
		}
		pointUseMin = parseInt(pointUseMin);

		if ($.isNumeric(retentionPoint)) {
			retentionPoint = parseInt(retentionPoint);
			usePoint = parseInt(usePoint);
			if (retentionPoint == 0) {
				return 0;
			} 
			
			if (retentionPoint < usePoint) {
				usePoint = retentionPoint;
			}
			
			var orderPayAmount = getOrderPay();
			if (orderPayAmount - minimumPaymentAmount < usePoint) {
				usePoint = orderPayAmount - minimumPaymentAmount;
			}
			 
			if (usePoint < 0) { 
				usePoint = 0;
			}
			
			if (pointUseMin > usePoint) {
				usePoint = 0;
			}

			if (usePoint > 0) {
				// 100원단위 사용 가능
				usePoint = Math.floor(usePoint * 0.01) * 100;
			}
			
			return usePoint;
		}
	}
	
	return 0;
} 

function getOrderPay() {
	// 상품 전체 금액 (상품 쿠폰 미적용)
	var itemPriceData = getItemPriceData();
	var totalItemPrice = itemPriceData.totalItemPrice;
	
	// 소비세 
	var totalExcisePrice = itemPriceData.totalTax;
	if ($.isNumeric(totalExcisePrice) == false) {
		totalExcisePrice = 0;
	}
	totalExcisePrice = parseInt(totalExcisePrice);

	// 송료
	var totalDeliveryCharge = getTotalDeliveryPrice();
	if ($.isNumeric(totalDeliveryCharge) == false) {
		totalDeliveryCharge = 0;
	}
	totalDeliveryCharge = parseInt(totalDeliveryCharge);
	
	// 상품 쿠폰 할인 금액
	var totalItemCouponDiscountAmount = itemPriceData.totalItemCouponDiscountAmount;
	if ($.isNumeric(totalItemCouponDiscountAmount) == false) {
		totalItemCouponDiscountAmount = 0;
	}
	totalItemCouponDiscountAmount = parseInt(totalItemCouponDiscountAmount);

	//장바구니 쿠폰 할인 금액
	var totalCartCouponDiscountAmount =getCartCouponDiscountAmount();
	if ($.isNumeric(totalCartCouponDiscountAmount) == false) {
		totalCartCouponDiscountAmount = 0;
	}
	totalCartCouponDiscountAmount = parseInt(totalCartCouponDiscountAmount);
	
	return totalItemPrice + totalExcisePrice + totalDeliveryCharge - totalItemCouponDiscountAmount - totalCartCouponDiscountAmount + getAddDeliveryPrice();
}

// 기존 주문 상품, 옵션별 주문 수량을 가져옴
function getOldOrderItemQuantity(itemId, itemOptionId) {
	
	$items = $('div.oldOrderItems > div');
	if ($items.size() > 0) {
		$.each($items, function(){
			
			var id = $('> input[name="id"]', $(this)).val();
			var quantity = parseInt($('> input[name="amount"]', $(this)).val());
			var option = $('> input[name="options"]', $(this)).val();
			
			if (itemId == id) {
				if (itemOptionId == undefined) {
					return quantity;
				} else {
					
					if (option == '') {
						return 0;
					}
					
					var temp = option.split("@");
					for(i = 0; i < temp.length; i++) {
						if (temp[i] == itemOptionId) {
							return quantity;
						}
					}
					
				}
			}
			
		});
	}
	
	return 0;
}

function findAddItemQuantity(itemId, itemOptionId) {
	$items = $('tbody#order_items > tr.orderItem_' + itemId);
	var totalQuantity = 0;
	if ($items.size() > 0) {
		$.each($items, function(){
			var itemType = $('input[name="itemTypes"]', $(this)).val();
			if(itemType == 'new') {
				var id = $('input[name="itemIds"]', $(this)).val();
				var quantity = parseInt($('input[name="quantitys"]', $(this)).val());
				var option = $('input[name="itemOptions"]', $(this)).val();

				if (itemId == id) {
					if (itemOptionId == undefined) {
						totalQuantity = totalQuantity + quantity;
					} else {
						
						if (option != '') {
							var temp = option.split("@");
							for(i = 0; i < temp.length; i++) {
								if (temp[i] == itemOptionId) {
									totalQuantity = totalQuantity + quantity;
								}
							}
						}
					}
				}
			}
		});
	}
	
	return totalQuantity;
}

// 상품 추가 재고량 검증
function addRelationItem(itemId, messageDisplay) {
	var $item = $('#item_' + itemId);
	
	var orderMaxQuantity = $item.find('input[name="orderMaxQuantity"]').val();
	var orderMinQuantity = $item.find('input[name="orderMinQuantity"]').val();
	var itemQuantity = $item.find('input[name="stockQuantity"]').val();
	
	orderMinQuantity = orderMinQuantity <= -1 ? 1 : Number(orderMinQuantity);
	
	if (itemQuantity >= 0) {
		var q = itemQuantity + getOldOrderItemQuantity(itemId) - orderMinQuantity - findAddItemQuantity(itemId);
		if (q <= 0) {
			alert('해당 상품은 재고가 없습니다.');		// [번역] 이쪽의 상품은 재고가 없습니다. 
			return;
		}
	}
	
	var hasError = false;
	$item.find('select[class="itemOption"]').each(function() {
		var tagName = $(this).get(0).tagName;
		var $option = $(this);
		var $target = $(this);
		
		if (tagName.toLowerCase() == 'input') {
			var name = $(this).attr('name');
			if (radioOptionName != name) {
				$option = $itemOptionInfo.find('input[name=' + name + ']:checked');
				$target = $itemOptionInfo.find('input[name=' + name + ']').eq(0);
				
				if ($option.size() == 0) {
					$.validator.validatorAlert($.validator.messages['select'].format($target.attr('title')), $target);
					$target.focus();
					hasError = true;
					return false;
				}
			}
			
		} else {
			if ($option.val() == '') {
				$.validator.validatorAlert($.validator.messages['select'].format($target.attr('title')), $target);
				$target.focus();
				hasError = true;
				return;
			}
			
			isContinue = true;
		}
	});
	
	if (hasError) {
		return;
	}
	
	var deliveryId = $('input[name="deliveryId"]', $item).val();
	var deliveryType = $('input[name="deliveryType"]', $item).val();
	var deliveryExtraCharge1 = $('input[name="deliveryExtraCharge1"]', $item).val();
	var deliveryExtraChargeFree1 = $('input[name="deliveryExtraChargeFree1"]', $item).val();
	var deliveryExtraCharge2 = $('input[name="deliveryExtraCharge2"]', $item).val();
	var deliveryExtraChargeFree2 = $('input[name="deliveryExtraChargeFree2"]', $item).val();
	var deliveryChargeId = $('input[name="deliveryChargeId"]', $item).val();
	var deliveryChargeType = $('input[name="deliveryChargeType"]', $item).val();
	var deliveryFreeAmount = $('input[name="deliveryFreeAmount"]', $item).val();
	var deliveryCharge = $('input[name="deliveryCharge"]', $item).val();
	var title = $('input[name="title"]', $item).val();
	
	var userOrderItemSequence = $('tbody#order_items >  tr.order_item').size();
	
	var item = {
		'itemId' : itemId,
		'itemName' : $item.find('.item_names').text(),
		'userOrderItemSequence' : parseInt(userOrderItemSequence) + 1,
		'itemUserCode' : $item.find('.item_user_code').html(),
		'itemImage' : $item.find('.item_image').attr('src'),
		'itemSalePrice' : $item.find('.item_sale_price').text().replace(",", ""),
		'orderMaxQuantity' : orderMaxQuantity,
		'orderMinQuantity' : orderMinQuantity,
		'itemQuantity' : itemQuantity,
		'itemOptions' : [],
		'delivery' : {
			'deliveryId' : deliveryId,
			'title' : title,
			'deliveryType' : deliveryType,
			'deliveryExtraCharge1' : deliveryExtraCharge1,
			'deliveryExtraChargeFree1' : deliveryExtraChargeFree1,
			'deliveryExtraCharge2' : deliveryExtraCharge2,
			'deliveryExtraChargeFree2' : deliveryExtraChargeFree2
		},
		'deliveryCharge' : {
			'deliveryChargeId' : deliveryChargeId,
			'deliveryChargeType' : deliveryChargeType,
			'deliveryFreeAmount' : deliveryFreeAmount,
			'deliveryCharge' : deliveryCharge
		}
	};
	
	if (deliveryType == '1') {
		item.deliveryCharge.deliveryChargePolicy = $('> input[name="deliveryChargePolicy"]', $item).val();
		item.deliveryCharge.conditionFirstFlag = $('> input[name="conditionFirstFlag"]', $item).val();
		item.deliveryCharge.chargedSeparatelyFlag = $('> input[name="chargedSeparatelyFlag"]', $item).val();
		item.deliveryCharge.deliveryGroupKey = "group-" + deliveryId + "-" + deliveryChargeId;
	}
	
	// 상품 옵션 정보
	var itemOptions = [];
	$item.find('select[class="itemOption"]').each(function() {
		var itemOptionGroupKey = $(this).attr('name') + "_info";
		var optionId = $(this).val();
		$itemOptionGroup = $('span.' + itemOptionGroupKey);
		if ($itemOptionGroup.size() > 0) {
			$itemOptions = $('> span.itemOptions', $itemOptionGroup);
			$itemOption = $itemOptions.find('> span.itemOption_' + optionId +'_info', $itemOptions);
			if ($itemOption.size() > 0) {
 
				var itemOption = [];
				$itemOption.find('span').each(function() {
					itemOption[$(this).attr('class')] = $(this).text();
				});
				
				itemOptions.push(itemOption);
			}
		}
	});
	
	item.itemOptions = itemOptions;
	for (var i = 0; i < item.itemOptions.length; i++) {
		if (item.itemOptions[i].stockQuantity >= 0) {
			var itemOptionId = item.itemOptions[i].itemOptionId;
			if (item.itemOptions[i].stockQuantity >= 0) {
				var deductedOptionQuantity = parseInt(item.itemOptions[i].stockQuantity) + getOldOrderItemQuantity(itemId, itemOptionId) - orderMinQuantity - findAddItemQuantity(itemId, itemOptionId);
				if (deductedOptionQuantity < 0) {
					alert('해당 상품은 재고가 없습니다.');		// [번역] 이쪽의 상품은 재고가 없습니다. 
					return;
				}
			}
		}
	}
	
	addItem(item);
}

// 날짜 만들기
function makeDate(inDate) {
	if (inDate.length != 8) {
		return inDate;
	}

	var strDate = String(inDate); 
	var nYear = strDate.substring(0,4); 
	var nMonth = strDate.substring(4,6); 
	var nDay = strDate.substring(6,8); 
	
	return nYear + '-' + nMonth + '-' + nDay;
}

function getUsedItemCouponInfo($orderItem) {
	$couponArea = getItemCouponAreaByOrderItem($orderItem);
	if ($couponArea.size() > 0) {
		$coupon = $('input[type="radio"]:checked', $couponArea);
		
		if ($coupon.val() == 'clear') {
			return {
				'itemCouponDiscountAmount' : 0,
				'couponConcurrently' : ''
			};
		}
		
		if ($coupon.size() > 0) {
			var couponKey = $coupon.val();
			
			return {
				'itemCouponDiscountAmount' : parseInt($('input[name="discount-amount-'+ couponKey +'-value"]', $couponArea).val()),
				'couponConcurrently' : $('input[name="coupon-concurrently-'+ couponKey +'-value"]', $couponArea).val(),
			}
		}
	}
	
	return {
		'itemCouponDiscountAmount' : 0,
		'couponConcurrently' : ''
	};
}

function getTotalDeliveryPrice() {
	var dodobuhyun = $('input[name="receiveSido"]').val();
	$deliverys = $('td.delivery-info');
	var totalDeliveryPrice = 0;
	
	$.each($deliverys, function(){
		
		var price = 0;
		var key = $(this).attr('id');
		
		if (key.indexOf('single-') == 0) {
			$delivery = $('tr.' + key);
			
			var quantity = parseInt($('input[name="quantitys"]', $delivery).val());
			var deliveryChargeType = $('input[name="deliveryChargeType"]', $delivery).val();
			var deliveryPrice = parseInt($('input[name="deliveryPrice"]', $delivery).val());
			
			
			if (dodobuhyun.indexOf('제주') != -1) {
				
				deliveryPrice = parseInt($('input[name="deliveryExtraCharge1"]', $delivery).val());
				
				var itemPrice = parseInt($('input[name="itemPrices"]', $delivery).val());
				var totalRequiredOptionsPrice = parseInt($('input[name="totalRequiredOptionsPrices"]', $delivery).val());
				
				var sumItemPrice = (itemPrice + totalRequiredOptionsPrice) * quantity;
				var deliveryFreeAmount = parseInt($('input[name="deliveryExtraChargeFree1"]', $delivery).val());
				
				var coupon = getUsedItemCouponInfo($delivery);
				
				var itemCouponDiscountAmount = coupon.itemCouponDiscountAmount;
				
				if (itemCouponDiscountAmount > 0) {
					var couponConcurrently = coupon.couponConcurrently;
					if (couponConcurrently == '1') {
						
						itemCouponDiscountAmount = itemCouponDiscountAmount / quantity;
						
						if (sumItemPrice - itemCouponDiscountAmount < deliveryFreeAmount) {
							price = deliveryPrice * quantity;
						}
						
					} else {
						if (quantity > 1) {
							
							if (sumItemPrice - itemCouponDiscountAmount < deliveryFreeAmount) {
								price = price + deliveryPrice;
							}
							
							if (sumItemPrice < deliveryFreeAmount) {
								price = price + (deliveryPrice * (quantity - 1));
							}
						} else {
							if (sumItemPrice - itemCouponDiscountAmount < deliveryFreeAmount) {
								price = deliveryPrice * quantity;
							}
							
						}
					}
					
				} else {
					if (sumItemPrice < deliveryFreeAmount) {
						price = deliveryPrice * quantity;
					}
				}
				
			} else {

				if (deliveryChargeType == '2') {
					price = deliveryPrice * quantity;
				} else if (deliveryChargeType == '3') {
					// 조건부
					
					var itemPrice = parseInt($('input[name="itemPrices"]', $delivery).val());
					var totalRequiredOptionsPrice = parseInt($('input[name="totalRequiredOptionsPrices"]', $delivery).val());
					
					var sumItemPrice = (itemPrice + totalRequiredOptionsPrice) * quantity;
					var deliveryFreeAmount = parseInt($('input[name="deliveryFreeAmount"]', $delivery).val());
					
					var coupon = getUsedItemCouponInfo($delivery);
					
					var itemCouponDiscountAmount = coupon.itemCouponDiscountAmount;
					
					if (itemCouponDiscountAmount > 0) {
						var couponConcurrently = coupon.couponConcurrently;
						if (couponConcurrently == '1') {
							
							itemCouponDiscountAmount = itemCouponDiscountAmount / quantity;
							
							if (sumItemPrice - itemCouponDiscountAmount < deliveryFreeAmount) {
								price = deliveryPrice * quantity;
							}
							
						} else {
							if (quantity > 1) {
								
								if (sumItemPrice - itemCouponDiscountAmount < deliveryFreeAmount) {
									price = price + deliveryPrice;
								}
								
								if (sumItemPrice < deliveryFreeAmount) {
									price = price + (deliveryPrice * (quantity - 1));
								}
							} else {
								if (sumItemPrice - itemCouponDiscountAmount < deliveryFreeAmount) {
									price = deliveryPrice * quantity;
								}
								
							}
						}
						
					} else {
						if (sumItemPrice < deliveryFreeAmount) {
							price = deliveryPrice * quantity;
						}
					}
				}				
			}
			 
			totalDeliveryPrice += parseInt(price);
		} else {
			
			var deliveryChargePolicy = $('input[name="deliveryChargePolicy"]', $(this)).val();
			var conditionFirstFlag = $('input[name="conditionFirstFlag"]', $(this)).val();
			
			var addDeliveryPrice = 0;
			
			if (dodobuhyun.indexOf('제주') != -1) {
				var deliveryPrice = parseInt($('input[name="deliveryExtraCharge1"]', $(this)).val());
				var deliveryFreeAmount = parseInt($('input[name="deliveryExtraChargeFree1"]', $(this)).val());
				
				$.each($('tr.' + key), function(i){
					var deliveryChargeType = $('input[name="deliveryChargeType"]', $(this)).val();

					$policyItems = $('tr.' + key);
					var totalItemPayAmount = 0; 
					
					$.each($policyItems, function(){
						var itemPrice = parseInt($('input[name="itemPrices"]', $(this)).val());
						var totalRequiredOptionsPrice = parseInt($('input[name="totalRequiredOptionsPrices"]', $(this)).val());
						var quantity = parseInt($('input[name="quantitys"]', $(this)).val());
						
						var sumItemPrice = (itemPrice + totalRequiredOptionsPrice) * quantity;
						
						var coupon = getUsedItemCouponInfo($(this));
						
						var itemCouponDiscountAmount = coupon.itemCouponDiscountAmount;
						
						itemPayAmount = sumItemPrice - itemCouponDiscountAmount;
						if ($.isNumeric(itemPayAmount) == true) {
							totalItemPayAmount = totalItemPayAmount + parseInt(itemPayAmount);
						}
					});
					
					if (totalItemPayAmount >= deliveryFreeAmount) {
						deliveryPrice = 0;
					}
				}); 
				
				price = deliveryPrice;

			} else {
				var tempDeliverys = [];
				var chargedSeparatelyFlag = $('input[name="chargedSeparatelyFlag"]', $(this)).val();
				$.each($('tr.' + key), function(i){
					var deliveryChargeType = $('input[name="deliveryChargeType"]', $(this)).val();
					var deliveryPrice = parseInt($('input[name="deliveryPrice"]', $(this)).val());
					
					if (deliveryChargeType == '3') {
						$policyItems = $('tr.' + key);
						var totalItemPayAmount = 0;
						$.each($policyItems, function(){
							var itemPrice = parseInt($('input[name="itemPrices"]', $(this)).val());
							var totalRequiredOptionsPrice = parseInt($('input[name="totalRequiredOptionsPrices"]', $(this)).val());
							var quantity = parseInt($('input[name="quantitys"]', $(this)).val());
							
							var sumItemPrice = (itemPrice + totalRequiredOptionsPrice) * quantity;
							
							var coupon = getUsedItemCouponInfo($(this));
							
							var itemCouponDiscountAmount = coupon.itemCouponDiscountAmount;
							
							itemPayAmount = sumItemPrice - itemCouponDiscountAmount;
							if ($.isNumeric(itemPayAmount) == true) {
								totalItemPayAmount = totalItemPayAmount + parseInt(itemPayAmount);
							}
						});
						
						var deliveryFreeAmount = $('input[name="deliveryFreeAmount"]', $(this)).val();
						if (totalItemPayAmount >= deliveryFreeAmount) {
							deliveryPrice = 0;
						}
					} else if (deliveryChargeType == '2') {
						if (chargedSeparatelyFlag == 'Y') {
							
							addDeliveryPrice = addDeliveryPrice + deliveryPrice;
							
						}
					}
					
					
					tempDeliverys[i] = {
						'deliveryChargeType' 	: deliveryChargeType,
						'deliveryPrice'			: deliveryPrice
					};
				});
				
				var tempDelivery = tempDeliverys[0];
				var price = tempDelivery.deliveryPrice;
				
				var isConditionFirstSetting = false;
				if (conditionFirstFlag == 'Y') {
					for(i = 0; i < tempDeliverys.length; i++) {
						var temp = tempDeliverys[i];
						if (temp.deliveryChargeType == '3') {
							if (deliveryChargePolicy == '1') {
								if (price >= temp.deliveryPrice) {
									price = temp.deliveryPrice;
									tempDelivery = temp;
								}
							} else {
								if (price < temp.deliveryPrice) {
									price = temp.deliveryPrice;
									tempDelivery = temp;
								}
							}
							
							isConditionFirstSetting = true;
						}
					}
				}
				
				if (isConditionFirstSetting == false) {
					for(i = 0; i < tempDeliverys.length; i++) {
						var temp = tempDeliverys[i];
						if (deliveryChargePolicy == '1') {
							if (price >= temp.deliveryPrice) {
								price = temp.deliveryPrice;
								tempDelivery = temp;
							}
						} else {
							if (price < temp.deliveryPrice) {
								price = temp.deliveryPrice;
								tempDelivery = temp;
							}
						}
					}
				}
				
				price = tempDelivery.deliveryPrice;
				if (tempDelivery.deliveryChargeType != '2') {
					price = price + addDeliveryPrice;
				} else {
					if (chargedSeparatelyFlag == 'Y') {
						price = addDeliveryPrice;
					}
				}
			}

			totalDeliveryPrice += parseInt(price);
		}
		
		if (price == 0) {
			price = "0";
		} else {
			price = Common.numberFormat(price);
		}
		
		$('> label', $(this)).html(price);
		
	});
	
	return totalDeliveryPrice;
}

// 검색상품 추가
function addItem(item) {
	
	var itemId = item.itemId;
	
	var deliveryKey = "";
	
	var delivery = item.delivery;
	var deliveryCharge = item.deliveryCharge;
	
	if (delivery.deliveryType == '1') {
		deliveryKey = "group-" + delivery.deliveryId;
	} else {
		deliveryKey = "single-" + delivery.deliveryId + "-" + item.userOrderItemSequence;
	}
	
	var deliveryAddFlag = true;
	if ($('tbody#order_items > tr.' + deliveryKey).size() > 0) {
		deliveryAddFlag = false;
	}
	
	var deliveryGroupKey = "";
	if (delivery.deliveryType == '1') {
		deliveryGroupKey = deliveryKey + '-' + deliveryCharge.deliveryChargeId;
	}
	
	var html = '	<tr class="order_item '+ deliveryKey +' '+ deliveryGroupKey +' orderItem_'+ itemId +'" id="order_item_'+ item.userOrderItemSequence +'">';
	html += '			<td class="tleft">';
	html += '				<input type="hidden" name="orderItemIds" value="0" />';
	html += '				<input type="hidden" name="userOrderItemSequences" value="'+ item.userOrderItemSequence +'" />';
	html += '				<input type="hidden" name="itemIds" value="'+ itemId +'" />';
	html += '				<input type="hidden" name="itemTypes" value="new" />';
	html += '				<input type="hidden" name="deliveryChargeId" value="'+ deliveryCharge.deliveryChargeId +'" />';
	html += '				<input type="hidden" name="deliveryChargeType" value="'+ deliveryCharge.deliveryChargeType +'" />';
	html += '				<input type="hidden" name="deliveryFreeAmount" value="'+ deliveryCharge.deliveryFreeAmount +'" />';
	html += '				<input type="hidden" name="deliveryPrice" value="'+ deliveryCharge.deliveryCharge +'" />';
	
	if (delivery.deliveryType == '2') {
		html += '			<input type="hidden" name="deliveryId" value="'+ delivery.deliveryId +'" />';
		html += '			<input type="hidden" name="deliveryExtraCharge1" value="'+ delivery.deliveryExtraCharge1 +'" />';
		html += '			<input type="hidden" name="deliveryExtraChargeFree1" value="'+ delivery.deliveryExtraChargeFree1 +'" />';
		html += '			<input type="hidden" name="deliveryExtraCharge2" value="'+ delivery.deliveryExtraCharge2 +'" />';
		html += '			<input type="hidden" name="deliveryExtraChargeFree2" value="'+ delivery.deliveryExtraChargeFree2 +'" />';
	}
	
	html += '				<div class="item_img3">';
	html += '					<img src="'+ item.itemImage +'" alt="'+ item.itemName +'" style="width:50px;">';
	html += '				</div>';
	html += '				<div class="item_box_list">';
	html += '					<p><strong>['+ item.itemUserCode +']</strong></p>';
	html += '					<p><strong>'+ item.itemName +'</strong></p>';
	
	var totalRequiredOptionsPrice = 0;
	var itemOptions = '///';
	if (item.itemOptions.length > 0) {
		html += '<ul class="item-list">'; 
		
		for(i = 0; i < item.itemOptions.length; i++) {
			var option = item.itemOptions[i];
			
			if (i > 0) {
				itemOptions = itemOptions + '@';
			}
			
			itemOptions = itemOptions + option.itemOptionId;
			
			html += '<li>';
			html += '	<span>' + option.optionName1 + '</span>' +  option.optionName2;
			
			if (option.extraPrice != 0) {
				var price = parseInt(option.extraPrice);
				totalRequiredOptionsPrice += price;
				html += ' (' + (price > 0 ? "+" : "");
				html += Common.numberFormat(price) + '원)';
			}
			
			if (option.optionCode != '') {
				html += ' (옵션번호 : '+ option.optionCode +')';
			}
			
			html += '</li>';
		}
		
		html += '</ul>';
 		
	} else {
		html += '				<br/><br/>';
	}
	html += '					<input type="hidden" name="itemOptions" value="'+ itemOptions +'" />';
	html += '				</div>';
	html += '			</td>';
	html += '			<td><div><input type="text" class="seven _number" name="itemPrices" value="'+ item.itemSalePrice +'"></div></td>';
	html += '			<td><div><input type="text" class="seven _number_nagetive" name="totalRequiredOptionsPrices" value="'+ totalRequiredOptionsPrice +'"></div></td>';
	html += '			<td><div><input type="text" class="seven _number" name="quantitys" value="'+ item.orderMinQuantity +'"></div></td>';
	html += '			<td><div class="itemCouponDiscountAmount">0</div></td>';
	html += '			<td><div class="itemPayAmount">'+ Common.numberFormat((parseInt(item.itemSalePrice) + parseInt(totalRequiredOptionsPrice)) * item.orderMinQuantity) +'</div></td>';
	html += '			<td><div><button class="table_btn" title="'+Message.get("M00074")+'" onclick="deleteItem(\''+ deliveryKey +'\', \''+ item.userOrderItemSequence +'\')">'+Message.get("M00074")+'</button></div></td>';
	
	if (deliveryAddFlag == true) {
		
		//var deliveryPrice = getDeliveryPriceByNew(item);
		
		html += '		<td rowspan="2" class="' + deliveryKey + ' delivery-info" id="' + deliveryKey + '" style="border-left: 1px solid #ececec;">';
		
		if (delivery.deliveryType == '1') {
			html += '		<input type="hidden" name="deliveryChargePolicy" value="'+delivery.deliveryChargePolicy +'" />';
			html += '		<input type="hidden" name="conditionFirstFlag" value="'+delivery.conditionFirstFlag +'" />';
			html += '		<input type="hidden" name="chargedSeparatelyFlag" value="'+delivery.chargedSeparatelyFlag +'" />';
			html += '		<input type="hidden" name="deliveryId" value="'+ delivery.deliveryId +'" />';
			html += '		<input type="hidden" name="deliveryExtraCharge1" value="'+ delivery.deliveryExtraCharge1 +'" />';
			html += '		<input type="hidden" name="deliveryExtraChargeFree1" value="'+ delivery.deliveryExtraChargeFree1 +'" />';
			html += '		<input type="hidden" name="deliveryExtraCharge2" value="'+ delivery.deliveryExtraCharge2 +'" />';
			html += '		<input type="hidden" name="deliveryExtraChargeFree2" value="'+ delivery.deliveryExtraChargeFree2 +'" />';
		}

		html +=	'			<strong>'+ delivery.title +'</strong> <br/>';
		
		if (delivery.deliveryType == '1') {
			html +=	'		[묶음]';
		} else {
			html +=	'		[개별]';

			if (deliveryCharge.deliveryChargeType == '1') {
				html +=	'		무료';
			} else if (deliveryCharge.deliveryChargeType == '1') {
				html +=	'		유료' + Common.numberFormat(deliveryCharge.deliveryCharge) + '원';
			} else {
				html +=	'		조건부 : ' + Common.numberFormat(deliveryCharge.deliveryFreeAmount)+'원 이상 무료, 이하 '+ Common.numberFormat(deliveryCharge.deliveryCharge) + '원';
			}
		}
		
		
		html += '			<label id="'+ deliveryKey +'-text">0</label>원';
		html += '		</td>';
	} else {
		var rowspan = parseInt($('tbody#order_items > tr.' + deliveryKey + ':first > td:last').attr('rowspan'));
		$('tbody#order_items > tr.' + deliveryKey + ':first > td:last').attr('rowspan', rowspan + 2);
	}

	html += '		</tr>';
	
	var param = {
		'userId'	: '${userId}',
		'orderId' 	: '${order.orderId}',
		'itemPrice' : item.itemSalePrice,
		'totalRequiredOptionsPrice' : totalRequiredOptionsPrice,
		'quantity' : item.orderMinQuantity
	}
	
	var coupons = [];
	
	<c:if test="${userId > 0}">
	$.ajaxSetup({async: false});
	$.post('/opmanager/order/item-coupons', param, function(resp){
		if (resp.data.length > 0) {
			coupons = resp.data;
		} 
	}, 'json');
	
	</c:if>
	
	html += '<tr id="order_item_'+ item.userOrderItemSequence +'_coupons" class="item_coupons"><td colspan="7" class="tleft">';
	if (coupons.length > 0) {
		var itemCouponAreaKey = "coupon-" + item.userOrderItemSequence;
		html += '	<ul class="item-coupons pt10" id="' + itemCouponAreaKey + '">';
		html += '		<li><input type="radio" name="' + itemCouponAreaKey + '-clear" id="' + itemCouponAreaKey + '-clear" value="clear"><label for="' + itemCouponAreaKey + '-clear"> '+Message.get("M00899")+'</label></li>';
		for(i = 0; i < coupons.length; i++) {
			var coupon = coupons[i];
			var couponKey = "item-coupon-" + coupon.couponUserId + "-" + item.userOrderItemSequence;
			var checked = coupon.useCoupon == 'Y' ? 'checked="checked"' : '';
			var style = coupon.couponState == '3' ? 'style="color:red;"' : '';
			
			html += '	<li>';
			html += '		<input type="radio" name="coupon-' + coupon.couponUserId + '" class="itemCoupon" id="' + couponKey + '" value="'+ couponKey +'" ' + checked + ' />'; 
			html += '		<input type="hidden" name="discount-amount-' + couponKey + '-value" value="' + coupon.discountAmount +'" />';
			html += '		<input type="hidden" name="coupon-concurrently-' + couponKey + '-value" value="' + coupon.couponConcurrently +'" />';
			html += '		<input type="hidden" name="coupon-pay-type-' + couponKey + '-value" value="' + coupon.couponPayType + '" />';
			html += '		<input type="hidden" name="coupon-pay-' + couponKey + '-value" value="' + coupon.couponPay + '" />';
			
			html += '		<label for="' + couponKey + '" '+ style +'>' + coupon.couponName;
			
			if (coupon.couponApplyStartDate != '') {
				html += '(';
				html += makeDate(coupon.couponApplyStartDate);
				if (coupon.couponApplyEndDate != '') {
					html += ' ~ ' + makeDate(coupon.couponApplyEndDate);
				}
				html += ')';
			}
			
			if (coupon.couponConcurrently != '1') {
				html += '(1개의 구입수량에만 할인 적용)';
			}
			
			html += '		<span class="unit_orange" id="discount-amount-' + couponKey + '-text">' + Common.numberFormat(coupon.discountAmount) + '원</span>';
			
			if (coupon.couponState == '3') {
				html += ' ( ※  '+ Message.get("M01228") +' : ' + Common.numberFormat(coupon.prevDiscountAmount) + ' 원 )';
			}
			
			html += '	</li>';
		}
		
		html += '	</ul>';
	} else {
		html += '적용 가능 상품 쿠폰은 없습니다.';
	}
	
	html += '</td></tr>';
	
	if (deliveryAddFlag == false) {
		$('tbody#order_items > tr.' + deliveryKey + ':last').next('tr').after(html);
	} else {
		$('tbody#order_items').append(html);
	}
	
	setCartCouponsDiscountAmount();
	
	setTotalPrice(); 
	
	setHeight();
	
	closeFindItemLayer();
}

// 상품 삭제
function deleteItem(deliveryKey, userOrderItemSequence) {
	
	var count = $('tr.' + deliveryKey).size();
	var isDeliveryCopy = false;

	if (count > 1) {
		var index = $('tr.' + deliveryKey).index($('tr#order_item_' + userOrderItemSequence));

		if (index == 0) {
			isDeliveryCopy = true;
			$delivery = $('tr.' + deliveryKey+':first > td:last').clone();
		} else {
			$delivery = $('tr.' + deliveryKey + ':first > td:last');
		}
	}
	
	$('tr#order_item_' + userOrderItemSequence).remove();
	$('tr#order_item_' + userOrderItemSequence + '_coupons').remove();
	
	if (count > 1) {
		$delivery.attr('rowspan', parseInt($('tr.' + deliveryKey).size()) * 2);
		
		if(isDeliveryCopy == true) {
			$('tr.' + deliveryKey+':first').append($delivery);
		}
	}
	
	setOrderItemsCouponDiscuntAmount();
	
	setCartCouponsDiscountAmount();
	
	setTotalPrice();
}
 
// 상품검색 레이어 보기
function openFindItemLayer() {
	Common.dimmed.show();
	$('#find_item_layer').show();
}

// 상품검색 레이어 닫기
function closeFindItemLayer() {
	$('#find-items').html("");
	Common.dimmed.hide();
	$('#find_item_layer').hide();
	return;
}


function searchItem(){
	
	
	var searchWhere = $('#searchWhere').val();
	var searchQuery = $('#searchQuery').val();
	var param = {
			'searchWhere': searchWhere,
			'searchQuery': searchQuery
		}; 
	
	$.post("/opmanager/order/guest-new-order-find-item-count",param,function(response){
		Common.responseHandler(response, function(){
			
			var openFlag = false;
			
			if (response.count == 1) {
				
				var respItem = response.item;
				
				var itemId = respItem.itemId;
				var orderMaxQuantity = respItem.item.orderMaxQuantity;
				var orderMinQuantity = respItem.item.orderMinQuantity;
				var itemQuantity =respItem.item.stockQuantity;
				
				
				var itemOptionFlag = respItem.item.itemOptionFlag;
				
				if (itemOptionFlag == 'Y') {
					
					$('input[name="where"]', '#findItemForm').val(searchWhere);
					$('input[name="query"]', '#findItemForm').val(searchQuery);

					var popup = Common.popup('', 'findItemPopup', 960, 800, 1);
					$('#findItemForm').attr('target', 'findItemPopup');
					$('#findItemForm').submit();
					
					return;
				}
				
				orderMinQuantity = orderMinQuantity <= -1 ? 1 : Number(orderMinQuantity);
				
				if (itemQuantity >= 0) {
					var q = itemQuantity + getOldOrderItemQuantity(itemId) - orderMinQuantity - findAddItemQuantity(itemId);
					if (q <= 0) {
						alert('해당 상품은 재고가 없습니다.');		// [번역] 이쪽의 상품은 재고가 없습니다. 
						return;
					}
				}
				
				var userOrderItemSequence = $('tbody#order_items >  tr.order_item').size()+1;
				var item = {
					'itemId' : itemId,
					'itemName' : respItem.item.itemName,
					'userOrderItemSequence' : userOrderItemSequence,
					'itemUserCode' : respItem.item.itemUserCode,
					'itemImage' : respItem.item.imageSrc,
					'itemSalePrice' : respItem.item.salePrice,
					'orderMaxQuantity' : orderMaxQuantity,
					'orderMinQuantity' : orderMinQuantity,
					'itemQuantity' : itemQuantity,
					'itemOptions' : [],
					'delivery' : {
						'deliveryId' : respItem.delivery.deliveryId,
						'title' : respItem.delivery.title,
						'deliveryType' : respItem.delivery.deliveryType,
						'deliveryExtraCharge1' : respItem.delivery.deliveryExtraCharge1,
						'deliveryExtraChargeFree1' : respItem.delivery.deliveryExtraChargeFree1,
						'deliveryExtraCharge2' : respItem.delivery.deliveryExtraCharge2,
						'deliveryExtraChargeFree2' : respItem.delivery.deliveryExtraChargeFree2
					},
					'deliveryCharge' : {
						'deliveryChargeId' : respItem.deliveryCharge.deliveryChargeId,
						'deliveryChargeType' : respItem.deliveryCharge.deliveryChargeType,
						'deliveryFreeAmount' : respItem.deliveryCharge.deliveryFreeAmount,
						'deliveryCharge' : respItem.deliveryCharge.deliveryCharge
					}
					
				};
				
				if (respItem.deliveryType == '1') {
					item.deliveryCharge.deliveryChargePolicy = respItem.delivery.deliveryChargePolicy;
					item.deliveryCharge.conditionFirstFlag = respItem.delivery.conditionFirstFlag;
					item.deliveryCharge.chargedSeparatelyFlag = respItem.delivery.chargedSeparatelyFlag;
					item.deliveryCharge.deliveryGroupKey = "group-" + item.deliveryId + "-" + item.deliveryChargeId;
				}
				
				addItem(item);
			}else{
				openFlag = true;
			}
			
			if ( openFlag ) {
				$('input[name="where"]', '#findItemForm').val(searchWhere);
				$('input[name="query"]', '#findItemForm').val(searchQuery);

				var popup = Common.popup('', 'findItemPopup', 960, 800, 1);
				$('#findItemForm').attr('target', 'findItemPopup');
				$('#findItemForm').submit();
				
				return;
			}
			
		});
	});
	
}

// 상품검색 리스트 페이징용
function goPage(page) {
	$('input[name="page"]', '#itemParam').val(page);
	$('#itemParam').submit();
}

// 다음 우편번호 검색
function openDaumPostcode(mode) {
	
	var zipcode1 		= "zipcode1";
	var zipcode2 		= "zipcode2";
	var address 		= "address";
	var addressDetail 	= "addressDetail";
	var sido			= "sido";
	var sigungu			= "sigungu";
	var eupmyeondong	= "eupmyeondong";
	
	
	if (mode == "receive") {
		zipcode1 		= "receiveZipcode1";
		zipcode2 		= "receiveZipcode2";
		address 		= "receiveAddress";
		addressDetail 	= "receiveAddressDetail";
		sido			= "receiveSido";
		sigungu			= "receiveSigungu";
		eupmyeondong	= "receiveEupmyeondong";
	}
	
    new daum.Postcode({
        oncomplete: function(data) {	        	
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 우편번호와 주소 정보를 해당 필드에 넣고, 커서를 상세주소 필드로 이동한다.
            document.getElementById(zipcode1).value = data.postcode1;
            document.getElementById(zipcode2).value = data.postcode2;
            
            document.getElementById(address).value = data.address;
            document.getElementById(sido).value = Zipcode.getSido(data.address);
            document.getElementById(sigungu).value = Zipcode.getSigungu(data.address);
            document.getElementById(eupmyeondong).value = Zipcode.getEupmyeondong(data.address);
            
            //var indexNum = data.address.indexOf(" ");
            //document.getElementById('dodobuhyun').value = data.address.substring(0, indexNum);

            //전체 주소에서 연결 번지 및 ()로 묶여 있는 부가정보를 제거하고자 할 경우,
            //아래와 같은 정규식을 사용해도 된다. 정규식은 개발자의 목적에 맞게 수정해서 사용 가능하다.
            //var addr = data.address.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
            //document.getElementById('address').value = addr;
			document.getElementById(addressDetail).value = "";
            document.getElementById(addressDetail).focus();
            
            setTotalPrice();
        }
    }).open();
}
</script>
