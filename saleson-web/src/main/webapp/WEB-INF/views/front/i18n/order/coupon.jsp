<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div class="popup_wrap">
	<h1 class="popup_title">쿠폰적용</h1>
	<div class="popup_contents">

		<div class="offline_coupon_area">
			<p class="tit">오프라인 쿠폰 등록<span>쿠폰 번호를 입력하세요</span></p>
			<div class="offline_area">
				<input type="text" id="offlineCode" class="form-control box" placeholder="'-'없이 발급받은 쿠폰번호를 입력해주세요." title="'-'없이 발급받은 쿠폰번호를 입력해주세요.">
				<button type="button" class="btn btn-ms btn-default" onclick="exchangeOfflineCoupon()">등록</button>
			</div>
		</div>

		<h2>상품쿠폰 적용<span>사용 가능한 쿠폰만 보여집니다.</span> </h2>
		<div class="board_wrap">
			<c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
				<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
					<c:set var="singleShipping" value="${shipping.singleShipping}"/>
					<c:choose>
						<c:when test="${singleShipping == true}">
							<c:set var="item" value="${shipping.buyItem}" scope="request" />
							<c:set var="totalItemSaleAmount" value="${item.itemPrice.itemSaleAmount}" />
							<c:set var="totalDiscountAmount" value="${item.itemPrice.itemDiscountAmount + item.itemPrice.userLevelDiscountAmount}" />
							<c:set var="totalBeforeDiscountAmount" value="${item.itemPrice.beforeDiscountAmount}" />

							<c:forEach items="${item.additionItemList}" var="addition">
								<c:if test="${item.itemId == addition.parentItemId && item.options == addition.parentItemOptions}">
									<c:set var="totalItemSaleAmount">${totalItemSaleAmount + addition.itemPrice.itemSaleAmount}</c:set>
									<c:set var="totalDiscountAmount">${totalDiscountAmount + addition.itemPrice.discountAmount}</c:set>
									<c:set var="totalBeforeDiscountAmount">${totalBeforeDiscountAmount + addition.itemPrice.beforeDiscountAmount}</c:set>
								</c:if>
							</c:forEach>

					 		<table cellpadding="0" cellspacing="0" class="board-list">
					 			<caption>상품쿠폰 1적용</caption>

					 			<thead>
					 				<tr>
					 					<th scope="col">상품명</th>
										<th scope="col">수량</th>
										<th scope="col">상품금액</th>
										<th scope="col">상품할인</th>
					 					<th scope="col">할인적용금액</th>
					 				</tr>
					 			</thead>
					 			<tbody>
					 				<tr>
					 					<td class="tleft">	 						 
					 						<div class="item_info">
												<p class="photo"><img src="${item.item.imageSrc}" alt="item photo"></p>
												<div class="order_option">
													<p class="item_name">${ item.item.itemName }</p>
													${ shop:viewOptionText(item.options) }

													${shop:viewAdditionItemList(item.additionItemList)}
												</div>
											</div>
					 					</td>
										<td>${ item.itemPrice.quantity }</td>
										<td><span class="price">${op:numberFormat(totalItemSaleAmount)}원</span></td>
										<td><span class="price">${op:numberFormat(totalDiscountAmount)}원</span></td>
					 					<td><span class="price">${ op:numberFormat(totalBeforeDiscountAmount) }원</span></td>
					 				</tr> 
					 				<tr>
					 					<c:set var="itemCouponAreaKey" value="coupon-${ item.itemSequence }-${receiver.shippingIndex}" />
					 					<td colspan="5" class="tleft item-coupons" id="${ itemCouponAreaKey }">
					 						<c:choose>
					 							<c:when test="${ !empty item.itemCoupons }"> 
							 						<div class="input_wrap col-w-9"><strong>쿠폰적용</strong></div>  
							 						<div class="input_wrap col-w-2">
							 						
							 							<div class="hide hidden" data-item-coupon-area-key="${ itemCouponAreaKey }">
							 								<c:forEach items="${ item.itemCoupons }" var="coupon">
							 									<c:set var="couponKey" value="item-coupon-${coupon.couponUserId}-${ item.itemSequence }-${receiver.shippingIndex}" />
																<span 
																	data-coupon-key="${couponKey}"
																	data-coupon-name="${coupon.couponName}"
																	data-coupon-user-id="${coupon.couponUserId}" 
																	data-discount-amount="${coupon.discountAmount}" 
																	data-discount-price="${coupon.discountPrice}" 
																	data-coupon-concurrently="${coupon.couponConcurrently}"
																	data-coupon-type="default"
																	data-item-sequence="${ item.itemSequence }"
																	data-shipping-index="${receiver.shippingIndex}"></span>
																	
							 								</c:forEach>
							 							</div>
							 							<select class="op-item-coupon" data-coupon-area-key="${ itemCouponAreaKey }">
							 								<option value="clear">쿠폰을 선택하세요</option>
							 								<c:forEach items="${ item.itemCoupons }" var="coupon">
							 									<c:set var="couponKey" value="item-coupon-${coupon.couponUserId}-${ item.itemSequence }-${receiver.shippingIndex}" />
																<option value="${ couponKey }" data-coupon-user-id="${coupon.couponUserId}" data-item-sequence="${ item.itemSequence }" data-shipping-index="${receiver.shippingIndex}">
																	${coupon.couponUserId}. ${coupon.couponName}
																	- ${op:numberFormat(coupon.discountAmount)}원 할인
																	
																	<c:choose>
																		<c:when test="${coupon.couponConcurrently == '1'}">[1개 수량만 적용]</c:when>
																		<c:otherwise>[구매 수량 할인]</c:otherwise>
																	</c:choose>
																</option>
															</c:forEach>
														</select> 
							 						</div>
							 					</c:when>
							 					<c:otherwise>
							 						<!--  쿠폰이 없을 경우 -->
					 								<p>사용가능한 쿠폰이 없습니다.</p>
							 					</c:otherwise>
							 				</c:choose>   
										</td>	
					 				</tr>
					 			</tbody>
					 		</table><!--// board_wrap E--> 
					 		<div class="boadr_total">
					 			<div>
					 				<span class="txt">쿠폰 할인금액</span>	
									<span class="delivery_total"><span><span id="discount-${ itemCouponAreaKey }-text">0</span>원</span></span>					  
								</div>
					 		</div>
						</c:when>
						<c:otherwise>
							<c:forEach items="${shipping.buyItems}" var="item" varStatus="itemIndex">
								<c:set var="totalItemSaleAmount" value="${item.itemPrice.itemSaleAmount}" />
								<c:set var="totalDiscountAmount" value="${item.itemPrice.itemDiscountAmount + item.itemPrice.userLevelDiscountAmount}" />
								<c:set var="totalBeforeDiscountAmount" value="${item.itemPrice.beforeDiscountAmount}" />

								<c:forEach items="${item.additionItemList}" var="addition">
									<c:if test="${item.itemId == addition.parentItemId && item.options == addition.parentItemOptions}">
										<c:set var="totalItemSaleAmount">${totalItemSaleAmount + addition.itemPrice.itemSaleAmount}</c:set>
										<c:set var="totalDiscountAmount">${totalDiscountAmount + addition.itemPrice.discountAmount}</c:set>
										<c:set var="totalBeforeDiscountAmount">${totalBeforeDiscountAmount + addition.itemPrice.beforeDiscountAmount}</c:set>
									</c:if>
								</c:forEach>

								<table cellpadding="0" cellspacing="0" class="board-list">
						 			<caption>상품쿠폰2 적용</caption>
						 			<thead>
						 				<tr>
											<th scope="col">상품명</th>
											<th scope="col">수량</th>
											<th scope="col">상품금액</th>
											<th scope="col">상품할인</th>
											<th scope="col">할인적용금액</th>
						 				</tr>
						 			</thead>
						 			<tbody>
						 				<tr>
						 					<td class="tleft">	 						 
						 						<div class="item_info">
													<p class="photo"><img src="${item.item.imageSrc}" alt="item photo"></p>
													<div class="order_option">
														<p class="item_name">${ item.item.itemName }</p>
														${ shop:viewOptionText(item.options) }

														${ shop:viewAdditionItemList(item.additionItemList) }
													</div>
												</div>
						 					</td>
											<td>${ item.itemPrice.quantity }</td>
											<td><span class="price">${op:numberFormat(totalItemSaleAmount)}원</span></td>
											<td><span class="price">${op:numberFormat(totalDiscountAmount)}원</span></td>
											<td><span class="price">${ op:numberFormat(totalBeforeDiscountAmount) }원</span></td>
						 				</tr> 
						 				<tr>
						 					<c:set var="itemCouponAreaKey" value="coupon-${ item.itemSequence }-${receiver.shippingIndex}" />
						 					<td colspan="5" class="tleft item-coupons" id="${ itemCouponAreaKey }">
						 						<c:choose>
						 							<c:when test="${ !empty item.itemCoupons }"> 
								 						<div class="input_wrap col-w-9"><strong>쿠폰적용</strong></div>  
								 						<div class="input_wrap col-w-2"> 
								 							<div class="hide hidden" data-item-coupon-area-key="${ itemCouponAreaKey }">
								 								<c:forEach items="${ item.itemCoupons }" var="coupon">
								 									<c:set var="couponKey" value="item-coupon-${coupon.couponUserId}-${ item.itemSequence }-${receiver.shippingIndex}" />
																	<span 
																		data-coupon-key="${couponKey}"
																		data-coupon-name="${coupon.couponName}"
																		data-coupon-user-id="${coupon.couponUserId}" 
																		data-discount-amount="${coupon.discountAmount}" 
																		data-discount-price="${coupon.discountPrice}" 
																		data-coupon-concurrently="${coupon.couponConcurrently}"
																		data-coupon-type="default"
																		data-item-sequence="${ item.itemSequence }"
																		data-shipping-index="${receiver.shippingIndex}"></span>
								 								</c:forEach>
								 							</div>
								 							<select class="op-item-coupon" data-coupon-area-key="${ itemCouponAreaKey }">
								 								<option value="clear">쿠폰을 선택하세요</option>
								 								<c:forEach items="${ item.itemCoupons }" var="coupon">
								 									<c:set var="couponKey" value="item-coupon-${coupon.couponUserId}-${ item.itemSequence }-${receiver.shippingIndex}" />
																	<option value="${ couponKey }" data-coupon-user-id="${coupon.couponUserId}" data-item-sequence="${ item.itemSequence }" data-shipping-index="${receiver.shippingIndex}">
																		${coupon.couponUserId}. ${coupon.couponName}
																		- ${op:numberFormat(coupon.discountAmount)}원 할인
																		
																		<c:choose>
																			<c:when test="${coupon.couponConcurrently == '1'}">[1개 수량만 적용]</c:when>
																			<c:otherwise>[구매 수량 할인]</c:otherwise>
																		</c:choose>
																	</option>
																</c:forEach>
															</select>
								 						</div>
								 					</c:when>
								 					<c:otherwise>
								 						<!--  쿠폰이 없을 경우 -->
						 								<p>사용가능한 쿠폰이 없습니다.</p>
								 					</c:otherwise>
								 				</c:choose>   
											</td>	
						 				</tr>
						 			</tbody>
						 		</table><!--// board_wrap E--> 
						 		<div class="boadr_total">
						 			<div>
						 				<span class="txt">쿠폰 할인금액</span>	
										<span class="delivery_total"><span><span id="discount-${ itemCouponAreaKey }-text">0</span>원</span></span>					  
									</div>
						 		</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:forEach>
			
	 		<div class="price_box">
				<div class="price_inner">
					<div class="money">
						<p class="txt01">할인적용금액</p>
						<p class="prices"><span>${ op:numberFormat(buy.orderPrice.totalItemSaleAmount) }</span>원</p>
					</div>

					<span class="icons"><img src="/content/images/icon/icon_plus.png" alt="+"></span>
					<div class="money">
						<p class="txt01">배송비</p>
						<p class="prices"><span><label id="op-total-shipping-amount-text" class="op-total-shipping-coupon-discount-amount-text">${ op:numberFormat(buy.orderPrice.totalShippingAmount) }</label></span>원</p>
					</div>
					<span class="icons"><img src="/content/images/icon/icon_minus.png" alt="-"></span>
					<div class="money">
						<p class="txt01">쿠폰할인</p>
						<p class="prices"><span><label id="op-total-discount-amount-text">0</label></span>원</p>
					</div>
					<span class="icons"><img src="/content/images/icon/icon_sum.png" alt="="></span>
					<div class="money">
						<p class="txt01"><strong>결제예정금액</strong></p>
						<p class="prices total"><span><label id="discountPayAmountText" class="">${ op:numberFormat(buy.orderPrice.orderPayAmount) }</label></span>원</p>
					</div>
				</div> <!-- // price_inner E -->
			</div>
		</div> <!-- // board_wrap E --> 
	</div><!--//popup_contents E-->
	
	<div class="btn_wrap  pt0">
		<button type="button" class="btn btn-success btn-lg" title="적용하기" onclick="setCoupon()">적용하기</button>
		<button type="button" class="btn btn-default btn-lg" title="취소하기" onclick="couponViewClose();">취소하기</button>
	</div>
	<a href="javascript:couponViewClose()" class="popup_close">창 닫기</a>
</div> 


<page:javascript>
<script type="text/javascript">Common.loading.hide();</script>
<script type="text/javascript">
var minimumPaymentAmount = '${minimumPaymentAmount}'; 
if ($.isNumeric(minimumPaymentAmount) == false) {
	minimumPaymentAmount = 0;
}
minimumPaymentAmount = parseInt(minimumPaymentAmount);

var orgTotalItemSaleAmount = '${buy.orderPrice.totalItemAmountBeforeDiscounts}';
if ($.isNumeric(orgTotalItemSaleAmount) == false) {
	orgTotalItemSaleAmount = 0;
}
orgTotalItemSaleAmount = parseInt(orgTotalItemSaleAmount);

 
var discountTargetAmount = 0;
var totalItemPrice = 0;

// 최종결제금액 추가 2017-04-26_seungil.lee
var payAmount = 0;
$(function(){
	if (opener.Order.totalItemPriceByCoupon == undefined) {
		couponViewClose();
		return;
	} 
	// 팝업과 부모의 상품 합계 금액이 다른경우가 생기면 새로고침
	if (orgTotalItemSaleAmount != opener.Order.totalItemPriceByCoupon) {
		couponViewClose();
		opener.location.reload();
	}
	
	// 배송비 적용 2017-05-12 yulsun.yoo
	var shippingAmount = opener.Order.buy.totalShippingAmount;
	$('#op-total-shipping-amount-text').html(Common.numberFormat(shippingAmount));

	// 상품 금액 (소비세 별도 or 소비세 포함)
	discountTargetAmount = opener.Order.totalItemPriceByCoupon; 
	totalItemPrice = opener.Order.totalItemPriceByCoupon;
	
	// totalItemPrice에 Opener의 totalShippingAmount합산 2017-04-26_seungil.lee
	payAmount = totalItemPrice + opener.Order.buy.totalShippingAmount;
	// 예상 결제 금액 적용 2017-05-12 yulsun.yoo 
	$('#discountPayAmountText').html(Common.numberFormat(payAmount));

	$('.op-item-coupon').on('change', function(e){
		var couponAreaKey = $(this).data('couponAreaKey');
		var selectedCouponUserId = $(this).find('option:selected').data('couponUserId');
		
		$.each($('.op-item-coupon'), function(){
			var checkCouponAreaKey = $(this).data('couponAreaKey');
			$.each($('div[data-item-coupon-area-key="'+ checkCouponAreaKey +'"] > span'), function() {
				var coupon = $(this).data();
				console.log('couponKey: ' + coupon.couponKey);
				console.log('couponUserId: ' + coupon.couponUserId);
				console.log('discountAmount: ' + coupon.discountAmount);
				console.log('discountPrice: '	+ coupon.discountPrice);
				console.log('couponConcurrently: ' + coupon.couponConcurrently);
				console.log('key: ' + coupon.couponKey);
				console.log('itemSequence: ' + coupon.itemSequence);
				console.log('shippingIndex: ' + coupon.shippingIndex);
				console.log('couponType: ' + coupon.couponType);
				
				$(this).data('disabled', false);
				if (couponAreaKey == checkCouponAreaKey) {
					$(this).data('selected', coupon.couponUserId == selectedCouponUserId ? true : false); 
				} else {
					$(this).data('selected', coupon.couponUserId == selectedCouponUserId ? false : coupon.selected);
				}
			});
		});
		
		$.each($('.op-item-coupon'), function() {
			$this = $(this);
			$.each($('div[data-item-coupon-area-key="'+ $(this).data('couponAreaKey') +'"] > span'), function() {
				var coupon = $(this).data();
				if (coupon.selected) {
					var couponUserId = coupon.couponUserId;
					$.each($('.op-item-coupon').not($this), function() {
						$.each($('div[data-item-coupon-area-key="'+ $(this).data('couponAreaKey') +'"] > span'), function() {
							if (couponUserId == $(this).data('couponUserId')) {
								$(this).data('disabled', true);
							}
						});
					})
				}
			});	 
		});
		
		setCouponDiscountData();
	});
	
	var $couponUseHiddenFields = opener.$('.op-coupon-hide-field-area > input[name="useCouponKeys"]');
	if ($couponUseHiddenFields.size() > 0) {
		$.each($couponUseHiddenFields, function(){
			var key = $(this).val();

			$.each($('.op-item-coupon'), function() {
				var couponAreaKey = $(this).data('couponAreaKey');
				$.each($('div[data-item-coupon-area-key="'+ couponAreaKey +'"] > span'), function() {
					var coupon = $(this).data();
					if (coupon.couponKey == key) {
						$(this).data('selected', true);
					}
				});
			});
		});
		
		$.each($('.op-item-coupon'), function() {
			$this = $(this);
			$.each($('div[data-item-coupon-area-key="'+ $(this).data('couponAreaKey') +'"] > span'), function() {
				var coupon = $(this).data();
				if (coupon.selected) {
					var couponUserId = coupon.couponUserId;
					$.each($('.op-item-coupon').not($this), function() {
						$.each($('div[data-item-coupon-area-key="'+ $(this).data('couponAreaKey') +'"] > span'), function() {
							if (couponUserId == $(this).data('couponUserId')) {
								$(this).data('disabled', true);
							}
						});
					})
				}
			});	 
		});
		
		setCouponDiscountData();
	}
});

function setCouponDiscountData() {
	$.each($('.op-item-coupon'), function() {
		
		var defferentPlaceSelectedCoupons = [];
		var defferentPlaceSelectNotCoupons = [];
		var selectId = 0;
		$.each($('div[data-item-coupon-area-key="'+ $(this).data('couponAreaKey') +'"] > span'), function() {
			var coupon = $(this).data();
			
			if (coupon.selected) {
				selectId = coupon.couponUserId;
			}
			
			if (coupon.disabled == true) {
				defferentPlaceSelectedCoupons.push(coupon);
			} else {
				defferentPlaceSelectNotCoupons.push(coupon);
			}
		});
		
		$(this).find('optgroup, option').remove();
		if (defferentPlaceSelectedCoupons.length > 0) {
			$_optionGroup = $('<optgroup label="다른 상품에 적용된 쿠폰" />');
			$.each(defferentPlaceSelectedCoupons, function(i, coupon) {
				$_optionGroup.append(makeOption(coupon));
			});
			
			$(this).append($_optionGroup);
			
			$_optionGroup = $('<optgroup label="사용 가능 쿠폰" />');
			
			$_option = $('<option />').val('clear').text('쿠폰을 선택하세요.');
			if (selectId == 0) {
				$_option.prop('selected', true);
			}
			
			$_optionGroup.append($_option);
			if (defferentPlaceSelectNotCoupons.length > 0) {
				$.each(defferentPlaceSelectNotCoupons, function(i, coupon) {
					$_optionGroup.append(makeOption(coupon));
				});  
			}
			
			$(this).append($_optionGroup);
		} else {
			
			$_option = $('<option />').val('clear').text('쿠폰을 선택하세요.');
			if (selectId == 0) {
				$_option.prop('selected', true);
			}
			
			$(this).append($_option);
			
			$_this = $(this);
			$.each(defferentPlaceSelectNotCoupons, function(i, coupon){
				$_this.append(makeOption(coupon));
			});
		}
		
	});
	
	var totalItemCouponDiscountAmount = 0;
	$.each($('.op-item-coupon'), function() {
		var couponAreaKey = $(this).data('couponAreaKey');
		var target = $('#discount-' + couponAreaKey + '-text');
		
		var discountAmount = 0;
		if ($(this).val() != 'clear') {
			var coupon = $(this).find('option:selected').data();
			discountAmount = Number(coupon.discountAmount);
		}
		
		totalItemCouponDiscountAmount += discountAmount;
		target.html(Common.numberFormat(discountAmount));
	});
	
	$('#op-total-discount-amount-text').html(Common.numberFormat(totalItemCouponDiscountAmount));
	// payAmount기준으로 쿠폰 할인액을 계산하도록 수정 2017-04-26_seungil.lee
// 	$('#discountPayAmountText').html(Common.numberFormat(totalItemPrice - totalItemCouponDiscountAmount));
	$('#discountPayAmountText').html(Common.numberFormat(payAmount - totalItemCouponDiscountAmount));
}

function couponViewClose() {
	self.close();
}

function setCoupon() {
	setCouponDiscountData();
	
	var itemCoupons = [];
	var addItemCoupons = [];
	var cartCoupons = [];
	$.each($('.op-item-coupon'), function() {
		if ($(this).val() != 'clear') {
			var coupon = $(this).find('option:selected').data();
			itemCoupons.push(coupon);
		}
	});
	
	opener.Order.setCouponDiscountAmount(itemCoupons, addItemCoupons, cartCoupons);
	couponViewClose();
}

function makeOption(coupon) {
	var optionText = coupon.couponUserId + '. ' + coupon.couponName;
	optionText += ' - ' + Common.numberFormat(coupon.discountAmount) + '원 할인';
	if (coupon.couponConcurrently == '1') {
		optionText += ' [1개 수량만 적용]';
	} else {
		optionText += ' [구매 수량 할인]';
	}
	
	$_option = $('<option />').val(coupon.couponKey).text(optionText);
	if (coupon.selected) {
		$_option.prop('selected', true);
	}
	
	$_option.data({
		'couponKey'				: coupon.couponKey,
		'couponUserId'			: coupon.couponUserId,
		'discountAmount'		: coupon.discountAmount,
		'discountPrice'			: coupon.discountPrice,
		'couponConcurrently'	: coupon.couponConcurrently,
		
		'key'					: coupon.couponKey,
		'itemSequence'			: coupon.itemSequence,
		'shippingIndex'			: coupon.shippingIndex,
		'couponType'			: coupon.couponType
	});
	
	return $_option;
}

function exchangeOfflineCoupon() {
	var $offlineCode = $('#offlineCode');
	var code = $offlineCode.val();

	if(code == '') {
		alert("쿠폰번호를 입력해주세요.");
		$offlineCode.focus();
		return false;
	}

	$.post("/mypage/offline-coupon-exchange?offlineCode="+code, function(response){
		if (response.isSuccess) {
			alert("전환되었습니다.");
			location.reload();
		} else {
			alert(response.errorMessage);
			$offlineCode.focus();
		}
	});
}
</script>
</page:javascript>