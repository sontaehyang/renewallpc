var Order = {
	'buy' 						: null,
	'pgType' 					: '',
	'totalItemPriceByCoupon'	: 0,
	'minimumPaymentAmount'		: 0,
	'pointUseMin'				: 0,
	'pointUseMax'				: 0,
	'pointUseRatio'				: 0,
	'orgReceivers'				: null,
	'useItemCoupons'			: [],
	'useAddItemCoupons'			: [],
	'useCartCoupons'			: [],
	'notMixPayType'				: [],
	'saveReceiverHtml'			: '',
	'orgZipcode'				: ''
}

/**
 * 초기화
 */
Order.init = function(buy, pgType, minimumPaymentAmount, pointUseMin, pointUseMax, pointUseRatio) {
	Order.buy = buy;
	Order.pgType = pgType; 
	Order.totalItemPriceByCoupon = buy.totalItemAmountBeforeDiscounts;
	if ($.isNumeric(minimumPaymentAmount) == false) {
		minimumPaymentAmount = 0;
	}
	
	Order.minimumPaymentAmount = Number(minimumPaymentAmount);

	if ($.isNumeric(pointUseMin) == false) {
		pointUseMin = 0;
	}
	
	if ($.isNumeric(pointUseMax) == false) {
		pointUseMax = 0;
	}

	if ($.isNumeric(pointUseRatio) == false) {
		pointUseRatio = 0;
	}

	Order.notMixPayType = Order.buy.notMixPayType;
	Order.orgReceivers = Order.buy.receivers;
	Order.pointUseMin = Number(pointUseMin);
	Order.pointUseMax = Number(pointUseMax);
	Order.pointUseRatio = Number(pointUseRatio);
}

/**
 * 마일리지 사용
 */
Order.pointUsed = function(usePoint) {
	
	Order.setPointDiscountAmount(usePoint);
	
	Order.setAmountText();
}

Order.pointUsedAllForMobile = function() {
	var usePoint = Order.buy.retentionPoint;
	Order.pointUsed(usePoint);
}

/**
 * 마일리지 전체 사용
 */
Order.pointUsedAll = function($checkbox) {
	var usePoint = 0;
	try {
		
		if ($checkbox.prop('checked') == true) {
			usePoint = Order.buy.retentionPoint;
		}
		
	} catch(e) {
		//alert(e.message);
	}
	
	Order.pointUsed(usePoint);
}


/**
 * 상품 금액 View처리
 */
Order.setItemAmountText = function(item) {

    var itemCouponDiscountAmountText = "0";
    var $itemCouponUsedArea = $('.itemCouponUsedArea-' + item.itemSequence);

    $itemCouponUsedArea.html("");
    if (item.couponDiscountAmount > 0) {
        itemCouponDiscountAmountText = "-" + Common.numberFormat(item.couponDiscountAmount);

        // SKC 쿠폰 사용 아이콘
        $itemCouponUsedArea.html("<span>쿠폰사용</span>");
    }


    // 상품 토탈 할인 금액 (스팟, 즉시, 쿠폰)
    var itemDiscountAmount = item.discountAmount;
    if (item.couponDiscountAmount > 0) {
        itemDiscountAmount += item.couponDiscountAmount
    }
    var itemDiscountAmountText = Common.numberFormat(itemDiscountAmount) + "원";

    // 쿠폰 할인 금액
    $('.itemCouponDiscountAmountText-' + item.itemSequence).html(itemCouponDiscountAmountText);
    $('.itemDiscountAmountText-' + item.itemSequence).html(itemDiscountAmountText);
    $('.itemPayAmountText-' + item.itemSequence).html(Common.numberFormat(item.saleAmount));
	
	// 포인트 금액을 반영하는 경우
	if (item.isPointApplyCouponDiscount == true) {
		
		var itemEarnPoint = 0;
		
		// 기본 포인트
		if (Number(item.point) > 0) {
			if (item.pointType == '1') { 
				
				itemEarnPoint += Math.floor(Number(item.sumPrice - item.couponDiscountPrice) * (Number(item.point) / 100));
			} else {
				// CJH 2016.11.12 금액으로 지정되어있으면?
			}
		}
		
		// 회원 포인트 
		if (Number(item.userLevelPointRate > 0)) {
			itemEarnPoint += Math.floor(Number(item.sumPrice - item.couponDiscountPrice) * (Number(item.userLevelPointRate) / 100));
		}
		
		Order.buy.totalEarnPoint += Number(itemEarnPoint * item.quantity);
	}
}

/**
 * 결제금액 계산
 */
Order.setOrderPayAmount = function(isClear) {
	var orderPayAmount = Order.buy.totalItemSaleAmount + Order.buy.totalShippingAmount - Order.buy.totalCartCouponDiscountAmount - Order.buy.totalPointDiscountAmount;
	Order.buy.orderPayAmount = orderPayAmount;
	
	if (isClear == true) {
		Order.setOrderPayAmountClear();
	}

    $('input[name="orderPrice.totalItemSaleAmount"]').val(Order.buy.totalItemSaleAmount);
    $('input[name="orderPrice.totalItemCouponDiscountAmount"]').val(Order.buy.totalItemCouponDiscountAmount);
    $('input[name="orderPrice.totalCartCouponDiscountAmount"]').val(Order.buy.totalCartCouponDiscountAmount);
    $('input[name="orderPrice.totalPointDiscountAmount"]').val(Order.buy.totalPointDiscountAmount);

    $('input[name="orderPrice.totalUserLevelDiscountAmount"]').val(Order.buy.totalUserLevelDiscountAmount);

    // 실제 마일리지 사용액
    $('input[name="buyPayments[\'point\'].amount"]').val(Order.buy.totalPointDiscountAmount);

    $('input[name="orderPrice.totalShippingAmount"]').val(Order.buy.totalShippingAmount);
    $('input[name="orderPrice.orderPayAmount"]').val(orderPayAmount);
}

/**
 * 결제금액 초기화
 */
Order.setOrderPayAmountClear = function() {
	$('input.op-order-payAmounts').val(0);
	$('input.op-default-payment').val(Order.buy.orderPayAmount);
	if (Shop.isMobilePage == true) { 
		$('.op-order-payAmount-text').text(Common.numberFormat(Order.buy.orderPayAmount));
	}
}

/**
 * 상품별 사용 쿠폰 리턴
 */
Order.getItemCoupon = function(itemCoupons, itemSequence, shippingIndex) {
	if (itemCoupons.length > 0) {
		for(i = 0; i < itemCoupons.length; i++) {
			var coupon = itemCoupons[i];
			
			if (coupon.itemSequence == itemSequence && coupon.shippingIndex == shippingIndex) {
				return coupon;
			}
		}
	}
	
	return null;
}

/**
 * 상품쿠폰 금액 Set
 */
Order.setItemCouponDiscountAmount = function(itemCoupons, item, shippingIndex) {
	var beforeDiscountAmount = item.beforeDiscountAmount;
	var itemSequence = item.itemSequence;
	
	// 상품쿠폰 사용 처리
	var coupon = Order.getItemCoupon(itemCoupons, itemSequence, shippingIndex);
	
	var couponKey = '';
	if (coupon != null) {

		item.couponDiscountPrice += coupon.discountPrice;
		item.couponDiscountAmount += coupon.discountAmount;
		
		item.saleAmount = item.beforeDiscountAmount - item.couponDiscountAmount;
		Order.buy.totalItemCouponDiscountAmount = item.couponDiscountAmount;
		couponKey = coupon.key;
	}
	
	return couponKey;
}

/**
 * 사용 가능 마일리지
 */
Order.setPointDiscountAmount = function (usePoint) {
	if ($.isNumeric(usePoint)) {
		var retentionPoint = Order.buy.retentionPoint;
		
		if ($.isNumeric(retentionPoint)) {
			retentionPoint = parseInt(retentionPoint);
			usePoint = parseInt(usePoint);
			if (retentionPoint == 0) {
				Order.buy.totalPointDiscountAmount = 0;
			} 
			
			if (retentionPoint < usePoint) {
				usePoint = retentionPoint;
			}
			
			// 이미 적용된 포인트 금액은 더해서 계산함
			var orderPayAmount = Order.buy.orderPayAmount + Order.buy.totalPointDiscountAmount;

			// 결제 예정 금액의 포인트 할인 반영율 적용
			var ratioPointAmount = Math.floor((orderPayAmount/100)*Order.pointUseRatio);
			if (orderPayAmount - Order.minimumPaymentAmount < usePoint) {
				usePoint = orderPayAmount - Order.minimumPaymentAmount;
			}
			if(usePoint > ratioPointAmount){
				usePoint = ratioPointAmount;
			}
			if (usePoint < 0) { 
				usePoint = 0;
			}
			
			if (Order.pointUseMin > usePoint) {
				usePoint = 0;
			}

			if (Order.pointUseMax > 0) {
				if (Order.pointUseMax < usePoint) {
					usePoint = Order.pointUseMax;
				}
			}
			
			if (usePoint > 0) {
				// 100원단위 사용 가능
				// usePoint = Math.floor(usePoint * 0.01) * 100;
			}

			Order.buy.totalPointDiscountAmount = usePoint;
			
			return;
		}
	}
	
	Order.buy.totalPointDiscountAmount = 0;

}

// 제주도서산간 여부 조회
Order.getIslandType = function(zipcode){
    var islandType = 0;
    if(zipcode != ''){
        $.ajaxSetup({
            "async" : false
        });
        $.post('/common/island-type', {'zipcode' : zipcode}, function(response){
            if (response.isSuccess) {
                islandType = response.data.islandType;
            }
        }, 'json');
    }
    return islandType;
}

// 도서산간, 제주 추가 배송비
Order.getShippingExtraChange = function(shipping, zipcode) {
	
	var addDeliveryCharge = 0;
	if (zipcode != '') {
		// 배송정책에 도서산간, 제주 추가 배송비가 설정되어있으면~
		if (shipping.shippingExtraCharge1 > 0 || shipping.shippingExtraCharge2 > 0) {
			// 모바일에서 검색하기 전에 결과값을 보내주는 현상발생하여 추가 2017-06-01 yulsun.yoo
			$.ajaxSetup({
				"async" : false
			});
			$.post('/common/island-type', {'zipcode' : zipcode}, function(response){
				if (response.isSuccess) {
					if (response.data.islandType == 'JEJU') { // 제주
						addDeliveryCharge = shipping.shippingExtraCharge1;
					} else if (response.data.islandType == 'ISLAND') { // 도서산간
						addDeliveryCharge = shipping.shippingExtraCharge2;
					}
				}
			}, 'json');
		} 
	}
	return Number(addDeliveryCharge);
}

Order.getTotalItems = function() {
	var tempItems = [];
	$.each(Order.buy.receivers, function(i, receiver) {
		$.each(receiver.shippingList, function(j, shipping) {
			if (shipping.singleShipping) {
				
				var quantity = shipping.buyItem.quantity;
					
				var isSet = false;
				if (tempItems.length > 0) {
					$.each(tempItems, function(z, tempItem){
						if (tempItem.itemSequence == shipping.buyItem.itemSequence) {

							var additionSumPrice = 0;
							var additionTotalSumPrice = 0;
							var additionDiscountAmount = 0;
							var additionItemDiscountAmount = 0;
							var additionUserLevelDiscountAmount = 0;

							$.each(tempItem.additionItemList, function(ii, addition) {
								var itemPrice = addition.itemPrice;
								var quantity = itemPrice.quantity;

								additionSumPrice 				+= itemPrice.sumPrice;
								additionTotalSumPrice 			+= Number(itemPrice.sumPrice) * quantity;
								additionDiscountAmount 			+= Number(itemPrice.discountAmount) * quantity;
								additionItemDiscountAmount 		+= Number(itemPrice.itemDiscountAmount) * quantity;
								additionUserLevelDiscountAmount += Number(itemPrice.userLevelDiscountAmount) * quantity;
							});

							
							quantity = Number(quantity) + Number(tempItem.quantity);
							
							tempItems[z] = {
								'itemSequence'					: tempItem.itemSequence,
								'couponDiscountPrice'			: tempItem.couponDiscountPrice,
								'couponDiscountAmount'			: Number(tempItem.couponDiscountAmount),
								'quantity'						: quantity,
								'beforeDiscountAmount'			: Number(tempItem.sumPrice) * quantity + additionTotalSumPrice,
								'saleAmount'					: (Number(tempItem.sumPrice) * quantity + additionTotalSumPrice) - (Number(tempItem.couponDiscountAmount)),
								'sumPrice'						: tempItem.sumPrice + additionSumPrice,
								'itemName'						: tempItem.itemName,
								'itemUserCode'					: tempItem.itemUserCode,
								'optionText'					: tempItem.optionText,
								'itemImageSrc'					: tempItem.itemImageSrc,
								'shipmentGroupType'				: tempItem.shipmentGroupType,
								'isPointApplyCouponDiscount'	: tempItem.isPointApplyCouponDiscount,
								'pointType'						: tempItem.pointType,
								'point'							: tempItem.point,
                                'userLevelPointRate'			: tempItem.userLevelPointRate,

                                'itemSaleAmount'				: tempItem.itemSaleAmount,
                                'baseAmountForShipping'			: tempItem.baseAmountForShipping,
                                'discountAmount'				: tempItem.discountAmount + additionDiscountAmount,
                                'itemDiscountAmount'			: tempItem.itemDiscountAmount + additionItemDiscountAmount,
                                'userLevelDiscountAmount'		: tempItem.userLevelDiscountAmount + additionUserLevelDiscountAmount
							};

							isSet = true;
						}
					});
				}
				
				if (isSet == false) {

					var additionSumPrice = 0;
					var additionTotalSumPrice = 0;
					var additionDiscountAmount = 0;
					var additionItemDiscountAmount = 0;
					var additionUserLevelDiscountAmount = 0;

					$.each(shipping.buyItem.additionItemList, function(ii, addition) {
						var itemPrice = addition.itemPrice;
						var quantity = itemPrice.quantity;

						additionSumPrice 				+= itemPrice.sumPrice;
						additionTotalSumPrice 			+= Number(itemPrice.sumPrice) * quantity;
						additionDiscountAmount 			+= Number(itemPrice.discountAmount);
						additionItemDiscountAmount 		+= Number(itemPrice.itemDiscountAmount);
						additionUserLevelDiscountAmount += Number(itemPrice.userLevelDiscountAmount);
					});

					var tempItem = {
						'itemSequence'					: shipping.buyItem.itemSequence,
						'couponDiscountPrice'			: shipping.buyItem.couponDiscountPrice,
						'couponDiscountAmount'			: Number(shipping.buyItem.couponDiscountAmount),
						'quantity'						: quantity,
						'beforeDiscountAmount'			: Number(shipping.buyItem.sumPrice) * quantity + additionTotalSumPrice,
						'saleAmount'					: (Number(shipping.buyItem.sumPrice) * quantity + additionTotalSumPrice) - (Number(shipping.buyItem.couponDiscountAmount)),
						'sumPrice'						: shipping.buyItem.sumPrice + additionSumPrice,
						'itemName'						: shipping.buyItem.itemName,
						'itemUserCode'					: shipping.buyItem.itemUserCode,
						'optionText'					: shipping.buyItem.optionText,
						'itemImageSrc'					: shipping.buyItem.itemImageSrc,
						'shipmentGroupType'				: shipping.buyItem.shipmentGroupType,
						'isPointApplyCouponDiscount'	: shipping.buyItem.isPointApplyCouponDiscount,
						'pointType'						: shipping.buyItem.pointType,
						'point'							: shipping.buyItem.point,
                        'userLevelPointRate'			: shipping.buyItem.userLevelPointRate,

                        'itemSaleAmount'				: shipping.buyItem.itemSaleAmount,
                        'baseAmountForShipping'			: shipping.buyItem.baseAmountForShipping,
                        'discountAmount'				: shipping.buyItem.discountAmount + additionDiscountAmount,
                        'itemDiscountAmount'			: shipping.itemDiscountAmount + additionItemDiscountAmount,
                        'userLevelDiscountAmount'		: shipping.userLevelDiscountAmount + additionUserLevelDiscountAmount


                    };

					tempItems.push(tempItem);
				}
					
				
				
			} else {
				$.each(shipping.buyItems, function(z, item){
					var quantity = item.quantity;
					
					var isSet = false;
					if (tempItems.length > 0) {
						$.each(tempItems, function(d, tempItem){
							if (tempItem.itemSequence == item.itemSequence) {

								var additionSumPrice = 0;
								var additionTotalSumPrice = 0;
								var additionDiscountAmount = 0;
								var additionItemDiscountAmount = 0;
								var additionUserLevelDiscountAmount = 0;

								$.each(tempItem.additionItemList, function(ii, addition) {
									var itemPrice = addition.itemPrice;
									var quantity = itemPrice.quantity;

									additionSumPrice 				+= itemPrice.sumPrice;
									additionTotalSumPrice 			+= Number(itemPrice.sumPrice) * quantity;
									additionDiscountAmount 			+= Number(itemPrice.discountAmount);
									additionItemDiscountAmount 		+= Number(itemPrice.itemDiscountAmount);
									additionUserLevelDiscountAmount += Number(itemPrice.userLevelDiscountAmount);
								});


								quantity = Number(quantity) + Number(tempItem.quantity);
								tempItems[d] = {
									'itemSequence'					: tempItem.itemSequence,
									'couponDiscountPrice'			: tempItem.couponDiscountPrice,
									'couponDiscountAmount'			: Number(tempItem.couponDiscountAmount),
									'quantity'						: quantity,
									'beforeDiscountAmount'			: Number(tempItem.sumPrice) * quantity + additionTotalSumPrice,
									'saleAmount'					: (Number(tempItem.sumPrice) * quantity + additionTotalSumPrice) - (Number(tempItem.couponDiscountAmount)),
									'sumPrice'						: tempItem.sumPrice + additionSumPrice,
									'itemName'						: tempItem.itemName,
									'itemUserCode'					: tempItem.itemUserCode,
									'optionText'					: tempItem.optionText,
									'itemImageSrc'					: tempItem.itemImageSrc,
									'shipmentGroupType'				: tempItem.shipmentGroupType,
									'isPointApplyCouponDiscount'	: tempItem.isPointApplyCouponDiscount,
									'pointType'						: tempItem.pointType,
									'point'							: tempItem.point,
                                    'userLevelPointRate'			: tempItem.userLevelPointRate,

                                    'itemSaleAmount'				: tempItem.itemSaleAmount,
                                    'baseAmountForShipping'			: tempItem.baseAmountForShipping,
                                    'discountAmount'				: tempItem.discountAmount + additionDiscountAmount,
                                    'itemDiscountAmount'			: tempItem.itemDiscountAmount + additionItemDiscountAmount,
                                    'userLevelDiscountAmount'		: tempItem.userLevelDiscountAmount + additionUserLevelDiscountAmount
								}
								
								isSet = true;
							}
						});
					}

					if (isSet == false) {

						var additionSumPrice = 0;
						var additionTotalSumPrice = 0;
						var additionDiscountAmount = 0;
						var additionItemDiscountAmount = 0;
						var additionUserLevelDiscountAmount = 0;

						$.each(item.additionItemList, function(ii, addition) {
							var itemPrice = addition.itemPrice;
							var quantity = itemPrice.quantity;

							additionSumPrice 				+= itemPrice.sumPrice;
							additionTotalSumPrice 			+= Number(itemPrice.sumPrice) * quantity;
							additionDiscountAmount 			+= Number(itemPrice.discountAmount);
							additionItemDiscountAmount 		+= Number(itemPrice.itemDiscountAmount);
							additionUserLevelDiscountAmount += Number(itemPrice.userLevelDiscountAmount);
						});

						var tempItem = {
							'itemSequence'					: item.itemSequence,
							'couponDiscountPrice'			: item.couponDiscountPrice,
							'couponDiscountAmount'			: Number(item.couponDiscountAmount),
							'quantity'						: quantity,
							'beforeDiscountAmount'			: Number(item.sumPrice) * quantity + additionTotalSumPrice,
							'saleAmount'					: (Number(item.sumPrice) * quantity + additionTotalSumPrice) - (Number(item.couponDiscountAmount)),
							'sumPrice'						: item.sumPrice + additionSumPrice,
							'itemName'						: item.itemName,
							'itemUserCode'					: item.itemUserCode,
							'optionText'					: item.optionText,
							'itemImageSrc'					: item.itemImageSrc,
							'shipmentGroupType'				: item.shipmentGroupType,
							'isPointApplyCouponDiscount'	: item.isPointApplyCouponDiscount,
							'pointType'						: item.pointType,
							'point'							: item.point,
                            'userLevelPointRate'			: item.userLevelPointRate,

                            'itemSaleAmount'				: item.itemSaleAmount,
                            'baseAmountForShipping'			: item.baseAmountForShipping,
                            'discountAmount'				: item.discountAmount + additionDiscountAmount,
                            'itemDiscountAmount'			: item.itemDiscountAmount + additionItemDiscountAmount,
                            'userLevelDiscountAmount'		: item.userLevelDiscountAmount + additionUserLevelDiscountAmount
                        }
						
						tempItems.push(tempItem);
					}
				});
			}
		});
	});
	
	return tempItems;
}

/**
 * 우편번호 정보 갱신
 */
Order.changeReceiverZipcode = function(zipcode, index) {
	Order.buy.receivers[index].zipcode = zipcode;
}

/**
 * 배송비 계산
 */
Order.setShippingAmount = function() {



	// 배송비 계산전 초기화
	Order.buy.totalShippingAmount = 0;
	var quickDeliveryFlag = $('input[name=quickDeliveryFlag]:checked').val();

	$.each(Order.buy.receivers, function(i, receiver) {
		
		var receiverTotalDeliveryChange = 0;
		var receiverTotalDeliveryChange2 = 0;
		//console.log("receiverIndex("+i+")->"+receiver.shippingList);

        var islandType = Order.getIslandType(receiver.zipcode);

		$.each(receiver.shippingList, function(j, shipping){

            var addDeliveryCharge = 0;
            if (shipping.shippingExtraCharge1 > 0 || shipping.shippingExtraCharge2 > 0) {
                if(islandType == 'JEJU'){
                    addDeliveryCharge = shipping.shippingExtraCharge1;
                } else if(islandType == 'ISLAND'){
                    addDeliveryCharge = shipping.shippingExtraCharge2;
                }
            }

			// var addDeliveryCharge = Order.getShippingExtraChange(shipping, receiver.zipcode);
			
			var realShipping = 0; 
			if (shipping.shippingType == '1') {
				realShipping = addDeliveryCharge;
			} else if (shipping.shippingType == '2' || shipping.shippingType == '3') {
				var totalItemAmount = 0;
				
				if (shipping.shippingType == '3') {
					
					if (!shipping.shipmentGroupCode) {
						$.each(shipping.buyItems, function(i, item){
							totalItemAmount += item.baseAmountForShipping;
						});
						
					} else {
						$.each(Order.buy.receivers, function(i1, receiver1) {
							
							if (receiver.shippingIndex == receiver1.shippingIndex) {
								$.each(receiver1.shippingList, function(j1, shipping1) {
									if (shipping1.shippingType == '3') {
										$.each(shipping1.buyItems, function(z1, item1){
											if (item1.shipmentGroupCode) {
												if (shipping1.shipmentGroupCode == item1.shipmentGroupCode) {
													totalItemAmount += item1.baseAmountForShipping;
												}
											}
										});
									}
								});
							}
						});
					}
					
				} else {
					$.each(shipping.buyItems, function(i, item){
						totalItemAmount += item.baseAmountForShipping;
					});
				}
				
				
				if (shipping.shippingFreeAmount <= totalItemAmount) {
					realShipping = addDeliveryCharge;
				} else {
					realShipping = shipping.shipping + addDeliveryCharge;
				}
			} else if (shipping.shippingType == '4') {
                var baseAmountForShipping = 0;
                if (shipping.singleShipping) {
                    baseAmountForShipping = shipping.buyItem.baseAmountForShipping;
                } else {
                    var items = shipping.buyItems;
                    if (typeof items != 'undefined' && items.length > 0) {
                        for (var i=0; i<items.length; i++) {
                            baseAmountForShipping += items[i].baseAmountForShipping;
                        }
                    }
                }

                if (shipping.shippingFreeAmount <= baseAmountForShipping) {
                    realShipping = addDeliveryCharge;
                } else {
                    realShipping = shipping.shipping + addDeliveryCharge;
                }
			} else if (shipping.shippingType == '5') {
                var boxCount = 0;
                if (shipping.singleShipping) {
                    boxCount = Math.ceil(Number(shipping.buyItem.quantity) / shipping.shippingItemCount);
                } else {
                    var items = shipping.buyItems;
                    if (typeof items != 'undefined' && items.length > 0) {
                        for (var i=0; i<items.length; i++) {
                            boxCount += Math.ceil(Number(items[i].quantity) / shipping.shippingItemCount);
                        }
                    }
                }

                realShipping = (shipping.shipping + addDeliveryCharge) * boxCount;
			} else {
				realShipping = shipping.shipping + addDeliveryCharge;
			}

			shipping.realShipping = realShipping;
			shipping.addDeliveryCharge = addDeliveryCharge;

			// 퀵배송일 경우 배송비 0
			if (quickDeliveryFlag == 'Y') {
				shipping.realShipping = 0;
				shipping.addDeliveryCharge = 0;
			}

			// CJH 2016.5.4 배송비가 없을때 배송비 쿠폰 사용 버튼 비노출
			/*if (shipping.realShipping > 0) {
				$('.op-shipping-coupon-' + shipping.shippingSequence).show();
			} else {
				$('.op-shipping-coupon-' + shipping.shippingSequence).hide();
			}*/
			// 제주/도서산간 배송일 때 배송비 쿠폰 사용 버튼 비노출 2017-04-25 yulsun.yoo
			if (addDeliveryCharge > 0) {
				$('.where_buy-' + shipping.shippingSequence).hide();

				// 제주, 도서산간 지역일 때 배송비 쿠폰 체크 해제 2017-04-25 yulsun.yoo
                $('.where_buy-' + shipping.shippingSequence).find('.op-input-shipping-coupon-used').prop('checked', false);
			} else if (shipping.realShipping > 0) {
				$('.where_buy-' + shipping.shippingSequence).show();
			} else {
				$('.where_buy-' + shipping.shippingSequence).hide();
				
				// 제주, 도서산간 지역일 때 배송비 쿠폰 체크 해제 2017-04-25 yulsun.yoo
                $('.where_buy-' + shipping.shippingSequence).find('.op-input-shipping-coupon-used').prop('checked', false);
			}
		});
	});
	
	// 배송쿠폰 재설정
	Order.setShippingCoupon();

	$.each(Order.buy.receivers, function(i, receiver) {

		var receiverTotalDeliveryChange = 0;
		var receiverTotalDeliveryChange2 = 0;
		//console.log("receiverIndex("+i+")->"+receiver.shippingList);
		$.each(receiver.shippingList, function(j, shipping){ 
			
			if (shipping.shippingPaymentType != '2') {
				receiverTotalDeliveryChange += shipping.realShipping;
				Order.buy.totalShippingAmount += shipping.realShipping;
			} else {
				// 착불 금액
				receiverTotalDeliveryChange2 += shipping.realShipping;
			}
		});
		
		var receiverDeliveryChargeText = '';
		
		// 착불금액이 있는경우
		if (receiverTotalDeliveryChange2 > 0) {
			if (receiverTotalDeliveryChange > 0) {
				receiverDeliveryChargeText += Common.numberFormat(receiverTotalDeliveryChange) + '원(선불)<br/>';
			}
			
			receiverDeliveryChargeText += Common.numberFormat(receiverTotalDeliveryChange2) + '원(착불)';
		} else {
			if (receiverTotalDeliveryChange > 0) {
				receiverDeliveryChargeText += Common.numberFormat(receiverTotalDeliveryChange) + '원(선불)';
			}
		}

		if (receiverDeliveryChargeText == '') {
			receiverDeliveryChargeText = '무료배송';
		}

		if (quickDeliveryFlag == 'Y') {
			receiverDeliveryChargeText = "퀵 (착불)";
		}

		$('#deliveryCharge-receiver-' + receiver.shippingIndex).html(receiverDeliveryChargeText);
	});
	
	
	Order.setAmountText();
}

//쿠폰 사용 처리
Order.setCouponDiscountAmount = function(itemCoupons, addItemCoupons, cartCoupons) {
	try {

        // [SKC] 2017-09-07. 쿠폰 적용 시 포인트 할인금액을 초기화
        // 포인트 적용 후 쿠폰적용시 결제 금액이 마이너스가 나오는 경우가 있음..
        $('input#retentionPointUseAll').prop('checked', false);
        $('.op-total-point-discount-amount-text').val(0);
        Order.pointUsed(0);


        // 복합 배송 설정때 쿠폰 사용 할인금액을 각각 설정하기 위함..
		Order.useItemCoupons = itemCoupons;
		Order.useAddItemCoupons = itemCoupons;
		Order.useCartCoupons = cartCoupons;
		
		var couponInputs = "";
		
		// 상품 쿠폰 할인액
		var totalItemCouponDiscountAmount = 0;
		
		var totalItemSaleAmount = 0;
		var totalItemDiscountAmount = 0;
		
		// 전체 상품쿠폰 할인금액 초기화
		Order.buy.totalItemCouponDiscountAmount = 0;
		 
		
		var couponKeys = [];
		$.each(Order.buy.receivers, function(i, receiver){
			$.each(receiver.shippingList, function(j, shipping) {
				if (shipping.singleShipping) {

					// 추가구성상품
					var additionSaleAmount = 0;
					$.each(shipping.buyItem.additionItemList, function(ii, addition) {
						additionSaleAmount  += addition.itemPrice.beforeDiscountAmount;
					});

					shipping.buyItem.saleAmount = shipping.buyItem.beforeDiscountAmount;
					shipping.buyItem.couponDiscountAmount = 0;
					shipping.buyItem.couponDiscountPrice = 0;
					
					var key = Order.setItemCouponDiscountAmount(itemCoupons, shipping.buyItem, receiver.shippingIndex);
					
					if (key != '') {
						var isInsert = true;
						for(var z = 0; z < couponKeys.length; z++) {
							if (couponKeys[z] == key) {
								isInsert = false;
							}
						}
						
						if (isInsert) {
							couponKeys.push(key);
						}
					}
					
					var key = Order.setItemCouponDiscountAmount(addItemCoupons, shipping.buyItem, receiver.shippingIndex);
					if (key != '') {
						var isInsert = true;
						for(var z = 0; z < couponKeys.length; z++) {
							if (couponKeys[z] == key) {
								isInsert = false;
							}
						}
						
						if (isInsert) {
							couponKeys.push(key);
						}
					}

					totalItemSaleAmount += shipping.buyItem.saleAmount + additionSaleAmount;
                    totalItemCouponDiscountAmount += shipping.buyItem.couponDiscountAmount;
				} else {
					$.each(shipping.buyItems, function(z, item) {
						
						item.saleAmount = item.beforeDiscountAmount;
						item.couponDiscountAmount = 0;
						item.couponDiscountPrice = 0;
						
						var key = Order.setItemCouponDiscountAmount(itemCoupons, item, receiver.shippingIndex);
						
						if (key != '') {
							var isInsert = true;
							for(var h = 0; h < couponKeys.length; h++) {
								if (couponKeys[h] == key) {
									isInsert = false;
								}
							}
							
							if (isInsert) { 
								couponKeys.push(key);
							}
						}
						
						var key = Order.setItemCouponDiscountAmount(addItemCoupons, item, receiver.shippingIndex);
						if (key != '') {
							var isInsert = true;
							for(var h = 0; h < couponKeys.length; h++) {
								if (couponKeys[h] == key) {
									isInsert = false;
								}
							}  
							
							if (isInsert) {
								couponKeys.push(key);
							}
						}

						// 추가구성상품
						var additionSaleAmount = 0;
						$.each(item.additionItemList, function(ii, addition) {
							additionSaleAmount  += addition.itemPrice.beforeDiscountAmount;
						});

						totalItemSaleAmount += item.saleAmount + additionSaleAmount;
                        totalItemCouponDiscountAmount += item.couponDiscountAmount;
					}); 
				} 
			}); 
		}); 
		
		if (couponKeys.length > 0) {
			$.each(couponKeys, function(key, value){
				couponInputs += '<input type="hidden" name="useCouponKeys" value="'+ value +'" class="useCoupon" />';
			});
		}

        Order.buy.totalItemCouponDiscountAmount = totalItemCouponDiscountAmount;
        Order.buy.totalItemSaleAmount = totalItemSaleAmount;
		
		// 사용자가 상품 쿠폰을 사용하면 배송료가 변경될수 있음.
		Order.setShippingAmount();
		
		var totalCartCouponDiscountAmount = 0;
		if (cartCoupons.length > 0) {
			for (var i = 0; i < cartCoupons.length; i++) {
				var coupon = cartCoupons[i];
				totalCartCouponDiscountAmount = totalCartCouponDiscountAmount + coupon.discountAmount;
				couponInputs += '<input type="hidden" name="useCouponKeys" value="'+ coupon.key +'" class="useCoupon" />';
			}
		}
		
		$('.op-coupon-hide-field-area').html(couponInputs);
		
		
		Order.setAmountText();
		
	} catch(e) {
		alert('쿠폰 사용처리도중 에러가 발생하였습니다.');
		location.reload();
	}
}

Order.getApprovalType = function(array, type) {
	
	var approvalType = "";
	$.each(array, function(i, key){
		if (key == type) {
			approvalType = key;
			return true;
		}
	}); 
	
	return (approvalType == "" ? false : true);
}

/**
 * 결제금액 체크
 * 포인트 사용금액은 재한 금액
 */
Order.checkPayAmount = function() {

	var amount = 0;
	var checkedCard = false;
	if ($('#payType-card').is(':checked') || $('input#op-payType-card').prop('checked')) {
		checkedCard = true;
	}

	$.each($('input.op-order-payAmounts', $('form#buy')), function(i) {
		if ($('input[name="payType"][value="'+$(this).attr('paymentType')+'"]').prop('checked') == true || checkedCard) {
			amount += Number($(this).val());
		}
	});

	if (Order.buy.orderPayAmount != amount) {
		alert('결제금액을 확인해주세요.');
		return false;
	}
	
	return true;
}

/**
 * 사용가능 배송 쿠폰
 */
Order.setShippingCoupon = function(addDeliveryCharge) {
	
	Order.buy.totalShippingCouponDiscountAmount = 0;
	Order.buy.totalShippingCouponUseCount = 0;
	if (Order.buy.shippingCoupon > 0) {
		
		Order.useShippingCoupon = 0;
		$.each(Order.buy.receivers, function(i, receiver) {
			$.each(receiver.shippingList, function(j, shipping) {
				if (Order.buy.shippingCoupon > Order.useShippingCoupon) {
					var key = "useShippingCoupon['SHIPPING-COUPON-" + shipping.shippingSequence + "'].useFlag";
					$_input = $('input[name="'+ key +'"]');
					
					shipping.discountShipping = 0;
					if ($_input.size() > 0) {
						if ($_input.prop('checked') == true) {
							if ($_input.prop('disabled') == false) { 
								
								if (shipping.realShipping > 0) {
									
									shipping.discountShipping = shipping.realShipping - shipping.addDeliveryCharge;

									shipping.realShipping = shipping.realShipping - shipping.discountShipping;
									Order.buy.totalShippingCouponDiscountAmount += shipping.discountShipping;
									Order.buy.totalShippingCouponUseCount++;
									Order.useShippingCoupon++;
									
								} else {
									$_input.prop('checked', false);
								} 
							}
						}
					}
				}
			})
		});
		
	}
} 

/**
 * 가격정보를 셋팅
 */
Order.setAmountText = function(isClear) {

	if (isClear == undefined) {
		isClear = true;
	}
	
	var totalItemQuantities = 0;
	
	// 적립 예정 포인트에 쿠폰 사용금액을 차감후 계산하는 경우
	if (Order.buy.isPointApplyCouponDiscount == true) {
		Order.buy.totalEarnPoint = 0;
	}
	
	$.each(Order.getTotalItems(), function(i, item) {
		totalItemQuantities += item.quantity;
		Order.setItemAmountText(item);
	});
	
	// 배송비를 정책별로 합산한다. <상품 목록에 데이터 갱신용!!>
	var groupShippings = [];
	$.each(Order.buy.receivers, function(i, receiver) { 
		$.each(receiver.shippingList, function(j, shipping) {

			var realShipping = Number(shipping.realShipping);
			var isSet = false;
			if (groupShippings.length > 0) {
				$.each(groupShippings, function(j, tempShipping) {
					if (tempShipping.shippingSequence == shipping.shippingSequence) {
						groupShippings[j].realShipping += realShipping;
						isSet = true;
					}
				});
			}
				
			if (isSet == false) {
				var temp = {
					'shippingSequence'		: shipping.shippingSequence,
					'realShipping'			: realShipping,
					'shippingPaymentType'	: shipping.shippingPaymentType
				};
				
				groupShippings.push(temp);
			}
		});
	});

	var quickDeliveryFlag = $('input[name=quickDeliveryFlag]:checked').val();
	$.each(groupShippings, function(i, shipping) {
		var realShipping = Common.numberFormat(shipping.realShipping) + "원";

		if (shipping.shippingPaymentType == '2') {
			realShipping += "(착불)";
		}
		
		if (shipping.realShipping == 0) {
			realShipping = "무료배송";
		}

		if (quickDeliveryFlag == 'Y') {
			realShipping = "퀵 (착불)"
		}

		$('.op-shipping-text-' + shipping.shippingSequence).html(realShipping);
	})


    var totalItemDiscountAmount = Order.buy.totalItemDiscountAmount;
    var totalUserLevelDiscountAmount = Order.buy.totalUserLevelDiscountAmount;
    var totalItemCouponDiscountAmount = Order.buy.totalItemCouponDiscountAmount;
    var totalCartCouponDiscountAmount = Order.buy.totalCartCouponDiscountAmount;
    var totalPointDiscountAmount = Order.buy.totalPointDiscountAmount;
    var totalShippingCouponDiscountAmount = Order.buy.totalShippingCouponDiscountAmount;

    var totalCouponDiscountAmount = totalItemCouponDiscountAmount + totalCartCouponDiscountAmount;
    var totalDiscountAmount = totalItemDiscountAmount + totalUserLevelDiscountAmount + totalItemCouponDiscountAmount + totalCartCouponDiscountAmount + totalPointDiscountAmount + totalShippingCouponDiscountAmount;

	 
	// 상품 금액 - 상품 쿠폰 적용 금액
	$('.op-total-item-sale-Amount-text').html(Common.numberFormat(Order.buy.totalItemSaleAmount));

	// 상품 쿠폰 할인 금액 
	$('.op-total-item-coupon-discount-amount-text').html(Common.numberFormat(totalItemCouponDiscountAmount));
	
	// 할인 안된 금액
	var noDiscountOrderPayAmount = Order.buy.totalItemAmountBeforeDiscounts + Order.buy.totalShippingAmount;
	$('.op-noDiscount-order-pay-amount-text').html(Common.numberFormat(noDiscountOrderPayAmount));
	 
	// 장바구니 쿠폰 할인 금액
	$('.op-total-cart-coupon-discount-amount-text').not('input').html(Common.numberFormat(totalCartCouponDiscountAmount));
	
	// 전체 쿠폰 할인 금액
	$('input.totalCouponDiscountAmountText').val(Common.numberFormat(totalCouponDiscountAmount));
	
	// 포인트 금액
	$('.op-total-point-discount-amount-text').not('input').html(Common.numberFormat(totalPointDiscountAmount));
	$('input.op-total-point-discount-amount-text').val(Common.numberFormat(totalPointDiscountAmount));

	// 배송비 할인액
	$('.op-total-shipping-coupon-discount-amount-text').not('input').html(Common.numberFormat(totalShippingCouponDiscountAmount));
	$('input.totalShippingCouponUseCountText').val(Common.numberFormat(Order.buy.totalShippingCouponUseCount));
	
	// 전체 할인 금액
	$('.op-total-discount-amount-text').not('input').html(Common.numberFormat(totalDiscountAmount));

	// 배송 금액 
	$('.op-total-delivery-charge-text').not('input').html(Common.numberFormat(Order.buy.totalShippingAmount));
	
	// 결제금액 계산
	Order.setOrderPayAmount(isClear); 
	
	// 결제 금액
	$('.op-order-pay-amount-text').not('input').html(Common.numberFormat(Order.buy.orderPayAmount));
	
	
	// [KakaoPay] 
	var $kakaopInputs = $('.op-kakaopay-request-data');
	$kakaopInputs.find('input[name=Amt]').val(Order.buy.orderPayAmount);
	$kakaopInputs.find('input[name=GoodsCnt]').val(totalItemQuantities);
	
	// 총 적립 금액
	$('.op-earn-point-text').html(Common.numberFormat(Order.buy.totalEarnPoint) + "P");

	if (quickDeliveryFlag == 'Y') {
		if (Shop.isMobilePage == true) {
			$('.op-quick-delivery-text').html(Common.numberFormat(Order.buy.totalShippingAmount) + '원 (착불)');
		} else {
			//원본 $('.op-quick-delivery-text').html('<b class="delv_price op-total-delivery-charge-text">' + Common.numberFormat(Order.buy.totalShippingAmount) + '</b>원 (착불)');
			$('.op-quick-delivery-text').html('<b class="delv_price op-total-delivery-charge-text">착불</b>');
		}
	} else {
		if (Shop.isMobilePage == true) {
			$('.op-quick-delivery-text').html(Common.numberFormat(Order.buy.totalShippingAmount) + '원');
		} else {
			$('.op-quick-delivery-text').html('<b class="delv_price op-total-delivery-charge-text">' + Common.numberFormat(Order.buy.totalShippingAmount) + '</b>원');
		}
	}
};


Order.multipleDelivery = function() {
	var message = "복수배송지 설정 하시겠습니까?\n배송지에 입력한 내용이 모두 초기화 됩니다.\n추가 배송비가 발생할수 있습니다.";
	if (Order.useShippingCoupon > 0) {
		var message = "복수배송지는 배송비쿠폰 적용이 불가합니다.\n복수배송지 설정 하시겠습니까?\n배송지에 입력한 내용이 모두 초기화 됩니다.\n추가 배송비가 발생할수 있습니다.";
	}
	
	if (Order.useItemCoupons.length > 0 || Order.useAddItemCoupons.length > 0 || Order.useCartCoupons.length > 0) {
		message += "\n복수배송지 설정 완료후 사용하신 쿠폰을 다시 확인하시기 바랍니다."; 
	}
	
	if (confirm(message)) {
		Common.popup(Shop.url('/order/multiple-delivery?count=' + $('select#multiple-delivery-set-count').val()), 'multiple-delivery', 898, 660, 1);
	}
	
	if (Order.saveReceiverHtml == '') {
		
		Order.orgZipcode = $('input[name="receivers[0].receiveZipcode"]').val();
		Order.saveReceiverHtml = $('div.op-receive-input-area').html();
		
	}
}

Order.resetShippingCoupon = function() {
	if (Order.buy.receivers.length > 1) {
		$('input.op-input-shipping-coupon-used').prop('disabled', true);
	} else {
		$('input.op-input-shipping-coupon-used').prop('disabled', false);
	}
}

Order.getShippingInfoForOriginal = function(item, tempShippings) {
    var tempShipping = null;

    $.each(Order.orgReceivers, function(i, orgReceiver) {

        if (tempShipping != null) {
            return false;
        }

        $.each(orgReceiver.shippingList, function(j, orgShipping) {

            if (orgShipping.singleShipping) {
                if (orgShipping.buyItem.itemSequence == item.itemSequence) {

                    var tempItem = {
                        'itemSequence'					: item.itemSequence,
                        'couponDiscountPrice'			: orgShipping.buyItem.couponDiscountPrice,
                        'couponDiscountAmount'			: orgShipping.buyItem.couponDiscountPrice * item.quantity,
                        'quantity'						: item.quantity,
                        'beforeDiscountAmount'			: orgShipping.buyItem.sumPrice * item.quantity,
                        'saleAmount'					: (orgShipping.buyItem.sumPrice * item.quantity) - (orgShipping.buyItem.couponDiscountPrice * item.quantity),
                        'sumPrice'						: orgShipping.buyItem.sumPrice,
                        'itemName'						: orgShipping.buyItem.itemName,
                        'itemUserCode'					: orgShipping.buyItem.itemUserCode,
                        'optionText'					: orgShipping.buyItem.optionText,
                        'itemImageSrc'					: orgShipping.buyItem.itemImageSrc,
                        'shipmentGroupType'				: orgShipping.buyItem.shipmentGroupType,
                        'isPointApplyCouponDiscount'	: orgShipping.buyItem.isPointApplyCouponDiscount,
                        'pointType'						: orgShipping.buyItem.pointType,
                        'point'							: orgShipping.buyItem.point,
                        'userLevelPointRate'			: orgShipping.buyItem.userLevelPointRate,
						'discountAmount'				: orgShipping.buyItem.discountAmount,
						'couponDiscountAmount'			: orgShipping.buyItem.couponDiscountAmount
                    }

                    tempShipping = {
                        'shippingSequence'		: orgShipping.shippingSequence,
                        'shippingType'			: orgShipping.shippingType,
                        'shipping'				: orgShipping.shipping,
                        'shippingExtraCharge1'	: orgShipping.shippingExtraCharge1,
                        'shippingExtraCharge2'	: orgShipping.shippingExtraCharge2,
                        'shippingReturn'		: orgShipping.shippingReturn,
                        'shippingFreeAmount'	: orgShipping.shippingFreeAmount,
                        'realShipping'			: orgShipping.realShipping,
                        'shippingPaymentType'	: orgShipping.shippingPaymentType,
                        'shippingItemCount'		: orgShipping.shippingItemCount,
                        'singleShipping'		: orgShipping.singleShipping,
                        'shipmentGroupType'		: orgShipping.shipmentGroupType,
                        'buyItem'				: tempItem
                    };

                    tempShippings.push(tempShipping);
                    return false;

                }
            } else {
                var tempItem = null;
                $.each(orgShipping.buyItems, function(z, orgItem){
                    if (orgItem.itemSequence == item.itemSequence) {
                        tempItem = {
                            'itemSequence'					: item.itemSequence,
                            'couponDiscountPrice'			: orgItem.couponDiscountPrice,
                            'couponDiscountAmount'			: orgItem.couponDiscountPrice * item.quantity,
                            'quantity'						: item.quantity,
                            'beforeDiscountAmount'			: orgItem.sumPrice * item.quantity,
                            'saleAmount'					: (orgItem.sumPrice * item.quantity) - (orgItem.couponDiscountPrice * item.quantity),
                            'sumPrice'						: orgItem.sumPrice,
                            'itemName'						: orgItem.itemName,
                            'itemUserCode'					: orgItem.itemUserCode,
                            'optionText'					: orgItem.optionText,
                            'itemImageSrc'					: orgItem.itemImageSrc,
                            'shipmentGroupType'				: orgItem.shipmentGroupType,
                            'isPointApplyCouponDiscount'	: orgItem.isPointApplyCouponDiscount,
                            'pointType'						: orgItem.pointType,
                            'point'							: orgItem.point,
                            'userLevelPointRate'			: orgItem.userLevelPointRate,

                            'itemSaleAmount'				: orgItem.itemSaleAmount,
                            'baseAmountForShipping'			: (orgItem.sumPrice * item.quantity) - (orgItem.couponDiscountPrice * item.quantity),
                            'discountAmount'				: orgItem.discountAmount,
                            'itemDiscountAmount'			: orgItem.itemDiscountAmount,
                            'userLevelDiscountAmount'		: orgItem.userLevelDiscountAmount
                        }

                    }
                });

                if (tempItem != null) {

                    var isInsert = false;
                    if (tempShippings.length > 0) {
                        $.each(tempShippings, function(z, ts){
                            if (ts.shippingSequence == orgShipping.shippingSequence) {

                                ts.buyItems.push(tempItem);
                                isInsert = true;
                            }
                        });
                    }

                    if (isInsert == false) {
                        var tempItems = [];
                        tempItems.push(tempItem);

                        tempShipping = {
                            'shippingSequence'		: orgShipping.shippingSequence,
                            'shippingType'			: orgShipping.shippingType,
                            'shipping'				: orgShipping.shipping,
                            'shippingExtraCharge1'	: orgShipping.shippingExtraCharge1,
                            'shippingExtraCharge2'	: orgShipping.shippingExtraCharge2,
                            'shippingReturn'		: orgShipping.shippingReturn,
                            'shippingFreeAmount'	: orgShipping.shippingFreeAmount,
                            'realShipping'			: orgShipping.realShipping,
                            'shippingPaymentType'	: orgShipping.shippingPaymentType,
                            'shippingItemCount'		: orgShipping.shippingItemCount,
                            'singleShipping'		: orgShipping.singleShipping,
                            'shipmentGroupType'		: orgShipping.shipmentGroupType,
                            'buyItems'				: tempItems
                        };

                        tempShippings.push(tempShipping);
                    }

                    return false;
                }
            }
        });
    });

    return tempShippings;
}

Order.cancelMultipleDelivery = function() {
	
	$('div.op-receive-input-area').html(Order.saveReceiverHtml);
	
	Order.buy.receivers = Order.orgReceivers;
	
	Order.setCouponDiscountAmount([], [], []);
	
	Order.resetShippingCoupon();
	
	Order.changeReceiverZipcode(Order.orgZipcode, 0);
	
	Order.resetShippingCoupon();
	
	Order.setShippingAmount();
	
	Order.setAmountText();
	
	// 한곳으로 보내기 버튼 생성
	$('#op-cancel-multiple-delivery').addClass('hide').hide();
	
	// 비회원 주문자 정보 복사
	$('#op-nomember-info-copy').prop('checked', false).show();
}

Order.getMultipleItemView = function(rowspan, itemIndex, viewItem, receiver, j) {
	itemInput = "";
	if (Shop.isMobilePage == false) {
		itemInput += '<tr>';
		
		if (itemIndex == 0) {
			itemInput += '	<th scope="row" rowspan="'+rowspan+'" valign="top" class="noline_bottom">배송<br>상품선택</th>';
		}
		
		itemInput += '	<td class="address_add" colspan="2">';
		itemInput += '		<input type="hidden" name="receivers['+ receiver.shippingIndex +'].buyQuantitys['+itemIndex+'].itemSequence" value="'+ viewItem.itemSequence +'" />';
		itemInput += '		<input type="hidden" name="receivers['+ receiver.shippingIndex +'].buyQuantitys['+itemIndex+'].quantity" value="'+ viewItem.quantity +'" />';
		
		itemInput += '		<div class="item_info">';
		itemInput += '			<p class="photo"><img src="'+ viewItem.itemImageSrc +'" alt="item photo"></p>';
		itemInput += '			<div class="order_option">';
		itemInput += '				<p class="item_name">'+ viewItem.itemName +'</p>';
		itemInput += '				<div class="item_price">';
		itemInput += '					<span>'+ Common.numberFormat(viewItem.sumPrice) +'</span>원/ <span>'+viewItem.quantity+'</span>개';
		itemInput += '				</div>';
		itemInput += '			</div>'; 
		itemInput += '		</div>';
		itemInput += '	</td>'; 
		itemInput += '</tr>';
	} else {
		itemInput += '<li>';
		itemInput += '	<div class="item">';
		itemInput += '		<div class="order_img">';
		itemInput += '			<img src="'+ viewItem.itemImageSrc +'" alt="item photo">';
		itemInput += '		</div>';
		itemInput += '		<div class="order_name">  ';
		itemInput += '			<p class="tit">'+ viewItem.itemName +'</p> ';
		itemInput += '			<input type="hidden" name="receivers['+ receiver.shippingIndex +'].buyQuantitys['+itemIndex+'].itemSequence" value="'+ viewItem.itemSequence +'" />';
		itemInput += '			<input type="hidden" name="receivers['+ receiver.shippingIndex +'].buyQuantitys['+itemIndex+'].quantity" value="'+ viewItem.quantity +'" />';
		itemInput += '		</div>';
		itemInput += '		<div class="order_price">';
		itemInput += '			<p class="tit"><p class="price">상품가격 <span>'+ viewItem.sumPrice +'</span>원 / </p></p> ';
		itemInput += '			<p class="quantity">수량 <span>'+ viewItem.quantity +'</span>개</p>';
		itemInput += '		</div>';
		itemInput += '	</div>'; 
		itemInput += '</li>'; 
	}
	
	return itemInput;
}

Order.setMultipleDelivery = function(receivers) {
	try {
		if (receivers.length > 0) {
			var tempReceivers = [];
			$.each(receivers, function(i, receiver) {  
				if (receiver.items.length > 0) {
					var tempShippings = [];
					$.each(receiver.items, function(j, item) {
						Order.getShippingInfoForOriginal(item, tempShippings);
					});
					
					//console.log("tempShippings.length->"+tempShippings.length);
					
					if (tempShippings.length > 0) {
						
						var receiverInfo = {};
						$.each(receiver, function(key, value) {
							receiverInfo[key] = value;
						});
						
						var tempReceiver = {
							'shippingIndex'		: receiver.receiverIndex,
							'zipcode'			: receiver.receiveZipcode,
							'shippingList'		: tempShippings,
							'info'				: receiverInfo
						};
						
						tempReceivers.push(tempReceiver);
					}
				}
			}); 

			if (tempReceivers.length == 0) {
				alert('복수배송지 설정도중 에러가 발생하였습니다.');
				location.reload();
			} 
			
			Order.buy.receivers = tempReceivers;
			
			//console.log(Order.buy.receivers);
			
			Order.resetShippingCoupon();
			
			$('div.op-receive-input-area').html('');
			$.each(Order.buy.receivers, function(i, receiver) { 
				
				var viewHtml = $('div.op-default-multiple-receiver-view').html();
				viewHtml = viewHtml.replaceAll('{RECEIVER_INDEX}', receiver.shippingIndex);
				viewHtml = viewHtml.replaceAll('{RECEIVER_VIEW_INDEX}', Number(receiver.shippingIndex) + 1);
				
				$.each(receiver.info, function(key, value) {
					viewHtml = viewHtml.replaceAll('{'+key+'}', value);
				});
				$('div.op-receive-input-area').append(viewHtml);
				
				$.each(receiver.info, function(key, value) {
					var findKey = 'receivers['+ receiver.shippingIndex +'].' + key;
					$('input[name="' + findKey + '"]', $('#op-receiver-' + receiver.shippingIndex)).val(value); 
				});
				
				var rowspan=0;
				$.each(receiver.shippingList, function(j, shipping){
					if (shipping.singleShipping) {
						rowspan++;
					} else {
						rowspan += shipping.buyItems.length;
					}
				});
				
				var itemIndex = 0;
				var itemInput = '';
				$.each(receiver.shippingList, function(j, shipping){

					
					if (shipping.singleShipping) {
						var viewItem = shipping.buyItem;
						
						itemInput += Order.getMultipleItemView(rowspan, itemIndex, viewItem, receiver, j);
						
						itemIndex++; 
					} else {
						$.each(shipping.buyItems, function(z, viewItem) {
							
							itemInput += Order.getMultipleItemView(rowspan, itemIndex, viewItem, receiver, z);
							
							itemIndex++;
						});
					}
					
					 
				});
				
				if (Shop.isMobilePage == true) {
					
					itemInput = '	<div class="order_item"><ul class="item_list">'+itemInput+'</ul>';
					itemInput += '	<div class="shipping_coupon">';
					itemInput += '		<span class="del_tit t_gray">배송비</span>';
					itemInput += '		<p class="shipping_price" id="deliveryCharge-receiver-'+ receiver.shippingIndex +'"><span>0</span>원</p>';
					itemInput += '	</div></div>';
				}
				
				if (itemInput != '') {
					if (Shop.isMobilePage == true) { 
						$('#op-receiver-' + receiver.shippingIndex).append(itemInput);
					} else {
						$('#op-receiver-' + receiver.shippingIndex + ' table > tbody').append(itemInput);
					}
				} 
				
				$('#op-receiver-' + receiver.shippingIndex).show();
				
			});  
			
			// 쿠폰 재설정 - 초기화
			Order.setCouponDiscountAmount([], [], []);
			
			// 배송비 재설정
			Order.setShippingAmount(); 
			
			Order.setAmountText();
		
			$('#op-nomember-info-copy').hide();
		
			// 한곳으로 보내기 버튼 생성
			$('#op-cancel-multiple-delivery').removeClass('hide').css('display', 'inline-block');
		}
	} catch(e) {
		alert('복수배송지 설정도중 에러가 발생하였습니다.\n' + e.message);
		location.reload();
	}
}