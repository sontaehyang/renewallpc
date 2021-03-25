<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<!-- 내용 : s -->
<div class="con">
	<div class="pop_title">
		<h3>쿠폰적용</h3>
		<a href="javascript:couponViewClose();" class="history_back">뒤로가기</a>
	</div>
	<!-- //pop_title -->
	<div class="pop_con">
		<div class="offline_coupon_area">
			<p class="tit">오프라인 쿠폰 등록<span>쿠폰 번호를 입력하세요</span></p>
			<div class="offline_area">
				<input type="text" id="offlineCode" class="form-control box" placeholder="'-'없이 발급받은 쿠폰번호를 입력해주세요." title="'-'없이 발급받은 쿠폰번호를 입력해주세요.">
				<button type="button" class="btn_st3" onclick="exchangeOfflineCoupon()">등록</button>
			</div>
		</div>
		<c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
			<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
				<c:set var="singleShipping" value="${shipping.singleShipping}"/>
				<c:choose>
					<c:when test="${singleShipping == true}">
						<c:set var="item" value="${shipping.buyItem}" scope="request" />
						<c:set var="totalSaleAmount" value="${item.itemPrice.saleAmount}" />

						<c:forEach items="${item.additionItemList}" var="addition">
							<c:if test="${item.itemId == addition.parentItemId && item.options == addition.parentItemOptions}">
								<c:set var="totalSaleAmount">${totalSaleAmount + addition.itemPrice.saleAmount}</c:set>
							</c:if>
						</c:forEach>

						<div class="order_item">
							<ul class="item_list">
								<li>
									<div class="item">
										<div class="order_img">
											<img src="${item.item.imageSrc}" alt="제품이미지">
										</div>
										<div class="order_name">
											<p class="tit">${ item.item.itemName }</p>
											<p class="detail">
												${ shop:buyViewOptionText(item.options) }

												${ shop:viewAdditionItemList(item.additionItemList) }
											</p>
										</div>
										<div class="order_price">
											<p class="price">상품가격 <span>${ op:numberFormat(totalSaleAmount) }</span>원 / </p>
											<p class="quantity">수량 <span>${ item.itemPrice.quantity }</span>개</p>
										</div>
									</div>
									<!-- //item -->
								</li>
							</ul>
							<!-- //item_list -->
							<div class="bd_table pb10">
								<c:set var="itemCouponAreaKey" value="coupon-${ item.itemSequence }-${receiver.shippingIndex}" />
								<ul class="del_info none" id="${ itemCouponAreaKey }">
									<li>
										<span class="del_tit t_gray">쿠폰적용</span>
										<div class="input_area">
											<c:choose>
					 							<c:when test="${ !empty item.itemCoupons }"> 
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
							 					</c:when>
							 					<c:otherwise>
							 						<p class="cp_noti">사용가능한 쿠폰이 없습니다.</p>
							 					</c:otherwise>
							 				</c:choose> 
										</div>
									</li>
								</ul>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${shipping.buyItems}" var="item" varStatus="itemIndex">
							<c:set var="totalSaleAmount" value="${item.itemPrice.saleAmount}" />

							<c:forEach items="${item.additionItemList}" var="addition">
								<c:if test="${item.itemId == addition.parentItemId && item.options == addition.parentItemOptions}">
									<c:set var="totalSaleAmount">${totalSaleAmount + addition.itemPrice.saleAmount}</c:set>
								</c:if>
							</c:forEach>

							<div class="order_item">
								<ul class="item_list">
									<li>
										<div class="item">
											<div class="order_img">
												<img src="${item.item.imageSrc}" alt="제품이미지">
											</div>
											<div class="order_name">
												<p class="tit">${ item.item.itemName }</p>
												<p class="detail">
													${ shop:buyViewOptionText(item.options) }

													${ shop:viewAdditionItemList(item.additionItemList) }
												</p>
											</div>
											<div class="order_price">
												<p class="price">상품가격 <span>${ op:numberFormat(totalSaleAmount) }</span>원 / </p>
												<p class="quantity">수량 <span>${ item.itemPrice.quantity }</span>개</p>
											</div>
										</div>
										<!-- //item -->
									</li>
								</ul>
								<!-- //item_list -->
								<div class="bd_table pb10">
									<c:set var="itemCouponAreaKey" value="coupon-${ item.itemSequence }-${receiver.shippingIndex}" />
									<ul class="del_info none" id="${ itemCouponAreaKey }">
										<li>
											<span class="del_tit t_gray">쿠폰적용</span>
											<div class="input_area">
												<c:choose>
						 							<c:when test="${ !empty item.itemCoupons }"> 
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
								 					</c:when>
								 					<c:otherwise>
								 						<p class="cp_noti">사용가능한 쿠폰이 없습니다.</p>
								 					</c:otherwise>
								 				</c:choose> 
											</div>
										</li>
									</ul>
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>
		</c:forEach>
	
		<!-- order_item -->
		<div class="cart_order">
			<div class="txt_wrap">
				<div class="order total">
					<span class="tit">총 상품금액</span>
					<p class="total_price"><span>${ op:numberFormat(buy.orderPrice.totalItemSaleAmount) }</span>원</p>  
				</div>
				<div class="sale total"> 
					<span class="tit">총 할인혜택</span>
					<p class="total_price"><span id="op-total-discount-amount-text">0</span>원</p>  
				</div>
				<div class="sum">
					<span class="tit">최종 결제금액</span>
					<p class="total_price"><span id="discountPayAmountText">${ op:numberFormat(buy.orderPrice.totalItemSaleAmount) }</span>원</p>
				</div>
			</div>
			<!-- //txt_wrap -->
		</div>
		<!-- cart_order -->
		<div class="btn_wrap">
			<button type="button" class="btn_st1 reset" onclick="couponViewClose();">취소</button>
			<button type="submit" class="btn_st1 decision" onclick="setCoupon()">적용</button>
		</div>
	</div>
	<!-- pop_con -->
</div>
<!-- 내용 : e -->

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

var target; 
var discountTargetAmount = 0;
var totalItemPrice = 0; 
$(function(){
	target = opener;
	if (isMobileLayer == true || isMobileLayer == 'true') {
		target = parent;
	}
	
	if (target.Order.totalItemPriceByCoupon == undefined) {
		couponViewClose();
		return;
	} 
	
	// 팝업과 부모의 상품 합계 금액이 다른경우가 생기면 새로고침
	if (orgTotalItemSaleAmount != target.Order.totalItemPriceByCoupon) {
		couponViewClose();
		target.location.reload();
	}
	
	// 상품 금액 (소비세 별도 or 소비세 포함)
	discountTargetAmount = target.Order.totalItemPriceByCoupon; 
	totalItemPrice = target.Order.totalItemPriceByCoupon;

	$('.op-item-coupon').on('change', function(e){
		var couponAreaKey = $(this).data('couponAreaKey');
		var selectedCouponUserId = $(this).find('option:selected').data('couponUserId');
		
		$.each($('.op-item-coupon'), function(){
			var checkCouponAreaKey = $(this).data('couponAreaKey');
			$.each($('div[data-item-coupon-area-key="'+ checkCouponAreaKey +'"] > span'), function() {
				var coupon = $(this).data();
				
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
	
	var $couponUseHiddenFields = target.$('.op-coupon-hide-field-area > input[name="useCouponKeys"]');
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
	$('#discountPayAmountText').html(Common.numberFormat(totalItemPrice - totalItemCouponDiscountAmount));
}

function couponViewClose() {
	if (isMobileLayer == true || isMobileLayer == 'true') {
		parent.$('.op-app-popup-wrap').show();
		parent.$('.op-app-popup-content').hide();
	} else {
		self.close();
	}
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
	
	target.Order.setCouponDiscountAmount(itemCoupons, addItemCoupons, cartCoupons);
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

	$.post("/m/mypage/offline-coupon-exchange?offlineCode="+code, function(response){
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