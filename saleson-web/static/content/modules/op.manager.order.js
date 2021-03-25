//환불 팝업
if (Manager == undefined) {
	Manager = {};
}

Manager.Order = {
	'sellerPrefix' : '/seller',
	'managerPrefix' : '/opmanager'
}

Manager.Order.isSellerPage = (function () {
	if (location.href.indexOf(Manager.Order.sellerPrefix) > -1) {
		return true;
	} else { 
		return false;
	}
}());


Manager.Order.url = function(url) {
	if (Manager.Order.isSellerPage) {
		return Manager.Order.sellerPrefix + url;
	}
	
	return Manager.Order.managerPrefix + url;;
};


Manager.Order.listUpdate = function(pageType, mode) {
	
	$.post(Manager.Order.url('/order/'+ pageType +'/listUpdate/'+mode), $("#listForm").serialize(), function(response){
		Common.responseHandler(response, function(response) {
			location.reload();
		}, function(response){
			alert(response.errorMessage);
		});
	});
}

// 환불 신청
Manager.Order.orderReturnApplyView = function(pageType, orderItemId) {
	$layer = $('#layer_return');
	if ($layer.size() == 0) {
		$('body').append($('<div id="layer_return" class="order_return_layer" />'));
		$layer = $('#layer_return');
	}
	
	var windowHeight = $(window).height();
	$layer.css({'height' : windowHeight - 20, 'overflow-x' : 'scroll'});
	
	var param = {
		'orderItemId' 	: orderItemId
	};
	
	$.post(Manager.Order.url('/order/'+ pageType +'/item-return-apply-view'), param, function(html){
		if (html.indexOf("ERROR") == -1) {
			Common.dimmed.show();
			$layer.html(html).show();
		} else { 
			alert(html);
			return;
		}
	}, 'html');
}

// 환불 처리
Manager.Order.orderReturnApplyAction = function(pageType) { 
	$layer = $('#layer_return');
	
	$returnReason = $('select[name="returnReason"]', $layer);
	$returnReasonText = $('input[name="returnReasonText"]', $layer);
	
	if ($returnReason.val() == '') {
		alert('환불사유를 선택해 주세요.');
		$returnReason.focus();
		return false;
	}
	
	if ($returnReasonText.val() == '') {
		alert('환불사유를 입력해 주세요.');
		$returnReasonText.focus();
		return false;
	}
	
	$returnBankInName = $('input[name="returnBankInName"]', $layer);
	$returnVirtualNo = $('input[name="returnVirtualNo"]', $layer);
	$returnBankName = $('input[name="returnBankName"]', $layer);
	
	if ($returnBankInName.size() > 0) {
		if ($returnBankName.val() == '') {
			alert('환불 받으실 은행명을 입력해 주세요.');
			$returnBankName.focus();
			return false;
		}
		
		if ($returnVirtualNo.val() == '') {
			alert('환불 받으실 계좌번호을 입력해 주세요.');
			$returnVirtualNo.focus();
			return false;
		}
		
		if ($returnBankInName.val() == '') {
			alert('입력하신 계좌번호의 예금주를 입력해 주세요.');
			$returnBankInName.focus();
			return false;
		}
		
	}
	
	$returnReserveName = $('input[name="returnReserveName"]', $layer);
	
	if ($returnReserveName.val() == '') {
		alert('반품 배송정보 고객명을 입력해 주세요.');
		$returnReserveName.focus();
		return false;
	}
	
	$returnReservePhone = $('input[name="returnReservePhone"]', $layer);
	
	if ($returnReservePhone.val() == '') {
		alert('반품 배송정보 전화번호를 입력해 주세요.');
		$returnReservePhone.focus();
		return false;
	}
	
	$returnReserveMobile = $('input[name="returnReserveMobile"]', $layer);
	
	if ($returnReserveMobile.val() == '') {
		alert('반품 배송정보 휴대폰번호를 입력해 주세요.');
		$returnReserveMobile.focus();
		return false;
	}
	
	$returnReserveZipcode = $('input[name="returnReserveZipcode"]', $layer);
	
	if ($returnReserveZipcode.val() == '') {
		alert('반품 배송정보 우편번호를 입력해 주세요.');
		$returnReserveZipcode.focus();
		return false;
	}
	
	$returnReserveSido = $('input[name="returnReserveSido"]', $layer);
	$returnReserveSigungu = $('input[name="returnReserveSigungu"]', $layer);
	$returnReserveEupmyeondong = $('input[name="returnReserveEupmyeondong"]', $layer);
	$returnReserveAddress = $('input[name="returnReserveAddress"]', $layer);
	$returnReserveAddress2 = $('input[name="returnReserveAddress2"]', $layer);
	
	if ($returnReserveSido.val() == '') {
		alert('반품 배송정보 주소를 입력해 주세요.');		
		return false;
	}
	
	if ($returnReserveSigungu.val() == '') {
		alert('반품 배송정보 주소를 입력해 주세요.');		
		return false;
	}
	
	if ($returnReserveEupmyeondong.val() == '') {
		alert('반품 배송정보 주소를 입력해 주세요.');		
		return false;
	}
	
	if ($returnReserveAddress.val() == '') {
		alert('반품 배송정보 주소를 입력해 주세요.');		
		return false;
	}
	
	if ($returnReserveAddress2.val() == '') {
		alert('반품 배송정보 상세주소를 입력해 주세요.');
		$returnReserveAddress2.focus();
		return false;
	}
	
	
	
	
	if (confirm("해당 상품을 환불 하시겠습니까?")) {
		$.post(Manager.Order.url('/order/'+ pageType +'/item-return-apply'), $("#orderReturnApply", $layer).serialize(), function(response){
			if (response.isSuccess) {
				location.reload();
			} else {
				alert(response.errorMessage);
				location.reload();
			}
		}, 'json');
	}
	
	return false;
}

// 주문취소 팝업
Manager.Order.orderCancelApplyView = function(pageType, orderShippingId, orderCode) {
	$layer = $('#layer_cancel');
	if ($layer.size() == 0) {
		$('body').append($('<div id="layer_cancel" class="order_cancel_layer" />'));
		$layer = $('#layer_cancel');
	}
	
	var windowHeight = $(window).height();
	$layer.css({'height' : windowHeight - 20, 'overflow-x' : 'scroll'});
	
	var param = {
		'orderShippingId' 	: orderShippingId,
		'orderCode'				: orderCode
	};
	 
	$.post(Manager.Order.url('/order/'+ pageType +'/buy-cancel-apply-view'), param, function(html){
		if (html.indexOf("ERROR") == -1) {
			Common.dimmed.show();
			$layer.html(html).show();
		} else { 
			alert(html); 
			return;
		}
	}, 'html');
}

// 취소 처리
Manager.Order.orderCancelApplyAction = function(pageType) {
	$layer = $('#layer_cancel');
	
	$cancelBankInName = $('input[name="cancelBankInName"]', $layer);
	$cancelVirtualNo = $('input[name="cancelVirtualNo"]', $layer);
	$cancelBankName = $('input[name="cancelBankName"]', $layer);
	
	$cancelReason = $('select[name="cancelReason"]', $layer);
	$cancelReasonText = $('input[name="cancelReasonText"]', $layer);
	
	if ($cancelReason.val() == '') {
		alert('환불사유를 선택해 주세요.');
		$cancelReason.focus();
		return false;
	}
	 
	if ($cancelReasonText.val() == '') {
		alert('환불사유를 입력해 주세요.');
		$cancelReasonText.focus();
		return false;
	}
	
	if ($cancelBankInName.size() > 0) {
		if ($cancelBankName.val() == '') {
			alert('환불 받으실 은행명을 입력해 주세요.');
			$cancelBankName.focus();
			return false;
		}
		
		if ($cancelVirtualNo.val() == '') {
			alert('환불 받으실 계좌번호을 입력해 주세요.');
			$cancelVirtualNo.focus();
			return false;
		}
		
		if ($cancelBankInName.val() == '') {
			alert('입력하신 계좌번호의 예금주를 입력해 주세요.');
			$cancelBankInName.focus();
			return false;
		}
	}
	
	if (confirm("해당 상품을 주문 취소 하시겠습니까?")) {
		$.post(Manager.Order.url('/order/'+ pageType +'/buy-cancel-apply'), $("#orderCancelApply", $layer).serialize(), function(response){
			if (response.isSuccess) {
				location.reload();
			} else {
				alert(response.errorMessage);
				location.reload();
			}
		}, 'json');
	}
	
	return false;
}

// 교환 신청
Manager.Order.orderExchangeApplyView = function(pageType, orderItemId) {
	$layer = $('#layer_exchange');
	if ($layer.size() == 0) {
		$('body').append($('<div id="layer_exchange" class="order_return_layer" />'));
		$layer = $('#layer_exchange');
	}
	
	var windowHeight = $(window).height();
	$layer.css({'height' : windowHeight - 20, 'overflow-x' : 'scroll'});
	
	var param = {
		'orderItemId' 	: orderItemId
	};
	
	$.post(Manager.Order.url('/order/'+ pageType +'/item-exchange-apply-view'), param, function(html){
		if (html.indexOf("ERROR") == -1) {
			Common.dimmed.show();
			$layer.html(html).show();
		} else { 
			alert(html);
			return;
		}
	}, 'html');
}

// 교환 처리
Manager.Order.orderExchangeApplyAction = function(pageType) {
	$layer = $('#layer_exchange');
	
		
	$receiveName = $('input[name="exchangeReceiveName"]', $layer);
	if ($receiveName.val() == '') {
		alert("고객명을 입력해주세요.");
		$receiveName.focus();
		return false;
	}
	
	$receiveZipcode1 = $('input[name="exchangeReceiveZipcode1"]', $layer);
	if ($receiveZipcode1.val() == '') {
		alert("우편번호를 입력해주세요.");
		$receiveZipcode1.focus();
		return false;
	}
	
	$receiveZipcode2 = $('input[name="exchangeReceiveZipcode2"]', $layer);
	if ($receiveZipcode2.val() == '') {
		alert("우편번호를 입력해주세요.");
		$receiveZipcode2.focus();
		return false;
	}
	
	$receivePhone = $('input[name="exchangeReceivePhone"]', $layer);
	if ($receivePhone.val() == '') {
		alert("전화번호를 입력해주세요.");
		$receivePhone.focus();
		return false;
	}
	
	$receiveAddress = $('input[name="exchangeReceiveAddress"]', $layer);
	if ($receiveAddress.val() == '') {
		alert("주소를 입력해주세요.");
		$receiveAddress.focus();
		return false;
	}
	
	$receiveMobile = $('input[name="exchangeReceiveMobile"]', $layer);
	if ($receiveMobile.val() == '') {
		alert("휴대폰 번호를 입력해주세요.");
		$receiveMobile.focus();
		return false;
	}
	
	$exchangeReason = $('select[name="exchangeReason"]', $layer);
	$exchangeReasonText = $('input[name="exchangeReasonText"]', $layer);
	
	if ($exchangeReason.val() == '') {
		alert('교환사유를 선택해 주세요.');
		$exchangeReason.focus();
		return false;
	}  
	
	if ($exchangeReasonText.val() == '') {  
		alert('교환사유를 입력해 주세요.');
		$exchangeReasonText.focus();
		return false;
	}
	
	if (confirm("해당 상품을 교환신청 하시겠습니까?")) {
		$.post(Manager.Order.url('/order/'+ pageType +'/item-exchange-apply'), $("#orderExchangeApply", $layer).serialize(), function(response){
			if (response.isSuccess) {
				location.reload();
			} else {
				alert(response.errorMessage);
				location.reload();
			}
		}, 'json');
	}
	
	return false;
}
 
// 교환 배송지 조회 
Manager.Order.exchangeFindAddress = function(pageType, orderItemId, orgShipingPay, selector) {
	var tagNames = {
		'zipcode1' 				: 'exchangeReceiveZipcode1',
		'zipcode2' 				: 'exchangeReceiveZipcode2',
		'sido'					: 'exchangeReceiveSido',
		'sigungu'				: 'exchangeReceiveSigungu',
		'eupmyeondong'			: 'exchangeReceiveEupmyeondong',
		'jibunAddress'			: 'exchangeReceiveAddress',
		'jibunAddressDetail' 	: 'exchangeReceiveAddressDetail'
	}
	
	openDaumAddress(tagNames, function(data){
			
			var shipingPay = orgShipingPay;
		
			$.post(Manager.Order.url('/order/'+pageType+'/item-exchange-return-shipping-pay'), {'zipcode' : data.postcode, 'orderItemId' : orderItemId}, function(response){
				if (response.isSuccess) {
					shipingPay =  Number(response.data.returnShippingPay);
				}
				
				$(selector).text(shipingPay);
			}, 'json');
			
	});
}