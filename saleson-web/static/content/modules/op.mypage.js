var Mypage = {};

Mypage.orderCancel = function(orderCode, orderSequence, itemSequence) {
	
	var param = {
		'orderCode' 	: orderCode,
		'orderSequence'	: orderSequence
	}
	
	var url = '/order-claim-apply/cancel-all';
	$.post(Shop.url(url), param, function(response) {
		
		if (response.data.isSuccess) {
			location.reload();
		} else {
			if (response.data.errorCode == '1000') {
				Mypage.claimApply(orderCode, orderSequence, itemSequence, '4');
			} else {
				alert(response.data.errorMessage);
			}
		} 
		
	}, 'json');
	
}

/**
 * 클레임신청 팝업
 */
Mypage.claimApply = function(orderCode, orderSequence, itemSequence, claimType) {
	var param = {
		'orderCode'				: orderCode,
		'orderSequence'		: orderSequence,
		'itemSequence'		: itemSequence,
		'claimType'				: claimType
	};
	
	var url = "/order-claim-apply/";
	if (claimType == '1' || claimType == '4') {
		url += 'cancel';
	} else if (claimType == '2') {
		url += 'return';
	} else if (claimType == '3') {
		url += 'exchange';
	}
	
	if (Shop.isMobilePage) {
		
		Mobile.pagination.clear('order');
		location.href = Shop.url(url) + '?orderSequence=' + param.orderSequence + '&orderCode=' + param.orderCode + "&itemSequence=" + param.itemSequence + "&claimType=" + param.claimType;
		
	} else {
	
		$layer = $('#layer_claim_apply');
		if ($layer.size() == 0) {
			$('body').append($('<div id="layer_claim_apply" class="order_cancel_layer" />'));
			$layer = $('#layer_claim_apply');
		}
		
		var windowHeight = $(window).height();
		$layer.css({'height' : windowHeight - 20, 'overflow-x' : 'scroll'});
		
		$.post(Shop.url(url), param, function(html){
			if (html.indexOf("ERROR") == -1) {
				Common.dimmed.show();
				$layer.html(html).show();
			} else { 
				alert(html);
				return;
			}
		}, 'html');
	}
}

/**
 * 클레임신청 처리
 */
Mypage.claimApplyAction = function(claimType) {
	
	$layer = $('#layer_claim_apply');
	if (Shop.isMobilePage) {
		$layer = $('#layer_claim_apply');
	}

	if ($('input[name="CancelMsg"]').length > 0) {
        $('input[name="CancelMsg"]').val($('input[name="claimReasonDetail"]').val());
    }

	if (claimType == '1') {
		$layer = $('#claimApply');
		if ($('input[name="id"]:checked', $('#claimApply')).size() == 0) {
			alert('신청하실 상품을 선택해 주세요.');
			return false;
		}
		
		if ($('input[name="returnBankInName"]').val() == '') {
			alert('환불 받으실 계좌의 예금주 명을 입력해주세요.');
			$('input[name="returnBankInName"]').focus();
			return false;
		}
		
		if ($('input[name="returnVirtualNo"]').val() == '') {
			alert('환불 받으실 계좌의 계좌번호를 입력해주세요.');
			$('input[name="returnVirtualNo"]').focus();
			return false;
		}
		
		var $val = $('select[name="claimReason"] option:selected').attr('title');
		$('input[name=claimReasonText]').val($val);
		
		if($('input[name=claimReasonDetail]').val() == '' || $('input[name=claimReasonDetail]').val() == null) {
			$('input[name=claimReasonDetail]').val(' ');
		}
	}
	
	var url = "/order-claim-apply/";
	var formName = "claimApply";
	if (claimType == '1' || claimType == '4') {
		url += 'cancel';
		krName = "취소";
		
		if ($('input[name="claimRefundType"]:checked', $layer).size() == 0) {
			alert('환불방법을 선택해 주세요.');
			return false;
		}
	} else if (claimType == '2') {
		$layer = $('#returnApply');
		url += 'return';
		formName = "returnApply";
		krName = "반품";
		
		if ($('input[name="returnShippingAskType"]:checked', $layer).val() == '2') {
			if ($('input[name="returnShippingNumber"]').val() == '') {
				alert('반송 송장번호를 입력해주세요.');
				$('input[name="returnShippingNumber"]').focus();
				return false;
			}
		}
		
		if ($('input[name="returnReserveZipcode"]').val() == '') {
			alert('우편번호 항목을 입력해 주세요.');
			$('input[name="returnReserveZipcode"]').focus();
			return false;
		}
		
		if ($('input[name="returnReserveAddress"]').val() == '') {
			alert('주소 항목을 입력해 주세요.');
			$('input[name="returnReserveAddress"]').focus();
			return false;
		}
		
		if ($('input[name="returnReserveAddress2"]').val() == '') {
			alert('주소 항목을 입력해 주세요.');
			$('input[name="returnReserveAddress2"]').focus();
			return false;
		}
		
		if ($('input[name="returnReserveMobile"]').val() == '') {
			alert('휴대폰 항목을 입력해 주세요.');
			$('input[name="returnReserveMobile"]').focus();
			return false;
		}
		
		if (Shop.isMobilePage) {
			if ($('input[name="returnReserveMobile1"]').val() == '') {
				alert('휴대폰 항목을 입력해 주세요.');
				$('input[name="returnReserveMobile1"]').focus();
				return false;
			}
			if ($('input[name="returnReserveMobile2"]').val() == '') {
				alert('휴대폰 항목을 입력해 주세요.');
				$('input[name="returnReserveMobile2"]').focus();
				return false;
			}
			if ($('input[name="returnReserveMobile3"]').val() == '') {
				alert('휴대폰 항목을 입력해 주세요.');
				$('input[name="returnReserveMobile3"]').focus();
				return false;
			}
		}
		
		if ($('input[name="returnBankInName"]').val() == '') {
			alert('환불 받으실 계좌의 예금주 명을 입력해주세요.');
			$('input[name="returnBankInName"]').focus();
			return false;
		}
		
		if ($('input[name="returnVirtualNo"]').val() == '') {
			alert('환불 받으실 계좌의 계좌번호를 입력해주세요.');
			$('input[name="returnVirtualNo"]').focus();
			return false;
		}
		
	} else if (claimType == '3') {
		$layer = $('#exchangeApply');
		url += 'exchange';
		formName = "exchangeApply";
		krName = "교환";
		
		if ($('input[name="exchangeShippingAskType"]:checked', $layer).val() == '2') {
			if ($('input[name="exchangeShippingNumber"]').val() == '') {
				alert('반송 송장번호를 입력해주세요.');
				$('input[name="exchangeShippingNumber"]').focus();
				return false;
			}
		}
		
		if ($('input[name="exchangeReceiveZipcode"]').val() == '') {
			alert('우편번호 항목을 입력해 주세요.');
			$('input[name="exchangeReserveZipcode"]').focus();
			return false;
		}
		
		if ($('input[name="exchangeReceiveAddress"]').val() == '') {
			alert('주소 항목을 입력해 주세요.');
			$('input[name="exchangeReserveAddress"]').focus();
			return false;
		}
		
		if ($('input[name="exchangeReceiveAddress2"]').val() == '') {
			alert('주소 항목을 입력해 주세요.');
			$('input[name="exchangeReceiveAddress2"]').focus();
			return false;
		}
		
		if ($('input[name="exchangeReceiveMobile"]').val() == '') {
			alert('휴대폰 항목을 입력해 주세요.');
			$('input[name="exchangeReceiveMobile"]').focus();
			return false;
		}
		
		if (Shop.isMobilePage) {
			if ($('input[name="exchangeReceiveMobile1"]').val() == '') {
				alert('휴대폰 항목을 입력해 주세요.');
				$('input[name="exchangeReceiveMobile1"]').focus();
				return false;
			}
			if ($('input[name="exchangeReceiveMobile2"]').val() == '') {
				alert('휴대폰 항목을 입력해 주세요.');
				$('input[name="exchangeReceiveMobile2"]').focus();
				return false;
			}
			if ($('input[name="exchangeReceiveMobile3"]').val() == '') {
				alert('휴대폰 항목을 입력해 주세요.');
				$('input[name="exchangeReceiveMobile3"]').focus();
				return false;
			}
		}
		
	}
	
	$claimReason = $('select[name="claimReason"]', $layer);
	if ($claimReason.val() == '') {
		alert('사유를 선택해 주세요.'); 
		$claimReason.focus(); 
		return false;
	}
	
	if($('input[name=claimReasonDetail]').val() == '' || $('input[name=claimReasonDetail]').val() == null) {
		$('input[name=claimReasonDetail]').val(' ');
	}
	
	url += '/process';
	
	$('input[name="claimReasonText"]').val($claimReason.find(':selected').text());
	if (confirm("선택하신 상품을 "+krName+"신청 하시겠습니까?")) {
		$.post(Shop.url(url), $("#" + formName).serialize(), function(response){
			if (response.isSuccess) {
				if (Shop.isMobilePage) {
					alert('정상적으로 '+krName+'접수 되었습니다.');
					parent.location.href="/m/mypage/order";
				} else {
					alert('정상적으로 '+krName+'접수 되었습니다.'); 
					location.reload();
				}
			} else {
				alert(response.errorMessage);
				location.reload();
			}
		}, 'json');
	}
	
	return false;
}

/**
 * KDJ (추가)
 * 모바일클레임신청 처리 
 */
Mypage.mobileClaimApplyAction = function(claimType) {
	
	// claimType 1:취소, 2:반품, 3:교환
	
	$layer = $('.con');
	var url = "/order-claim-apply/";
	var formName = "claimApply";
	
	if (claimType == '1' || claimType == '4'){
		url += 'cancel';
		krName = "취소";
		
		if ($('input[name="id"]:checked', $layer).size() == 0) {
			alert('신청하실 상품을 선택해 주세요.');
			return false;
		}
		
		if ($('input[name="returnBankInName"]').val() == '') {
			alert('환불 받으실 계좌의 예금주 명을 입력해주세요.');
			$('input[name="returnBankInName"]').focus();
			return false;
		}
		
		if ($('input[name="returnVirtualNo"]').val() == '') {
			alert('환불 받으실 계좌의 계좌번호를 입력해주세요.');
			$('input[name="returnVirtualNo"]').focus();
			return false;
		}
	}
	
	if(claimType == '2' || claimType == '3') {
		if(claimType == '2'){
			url += 'return';
			krName = "반품";
		} else {
			url += 'exchange';
			krName = "교환";
		}
		
		if ($('input[name="returnReserveMobile"]').val() == '') {
			alert('휴대폰 항목을 입력해 주세요.');
			$('input[name="returnReserveMobile"]').focus();
			return false;
		}
		
		if ($('input[name="returnReservePhone"]').val() == '') {
			alert('전화번호 항목을 입력해 주세요.');
			$('input[name="returnReservePhone"]').focus();
			return false;
		}
		
		if ($('input[name="returnShippingAskType"]:checked', $layer).val() == '1') {
			if ($('input[name="returnReserveZipcode"]').val() == '') {
				alert('우편번호 항목을 입력해 주세요.');
				$('input[name="returnReserveZipcode"]').focus();
				return false;
			}
			
			if ($('input[name="returnReserveAddress"]').val() == '') {
				alert('주소 항목을 입력해 주세요.');
				$('input[name="returnReserveAddress"]').focus();
				return false;
			}
			
			if ($('input[name="returnReserveAddress2"]').val() == '') {
				alert('주소 항목을 입력해 주세요.');
				$('input[name="returnReserveAddress2"]').focus();
				return false;
			}
			
		} else if($('input[name="returnShippingAskType"]:checked', $layer).val() == '2'){
			if ($('input[name="returnShippingNumber"]').val() == '') {
				alert('반송 송장번호를 입력해주세요.');
				return false;
			}
			
		}
		
		if ($('input[name="returnBankInName"]').val() == '') {
			alert('환불 받으실 계좌의 예금주 명을 입력해주세요.');
			$('input[name="returnBankInName"]').focus();
			return false;
		}
		
		if ($('input[name="returnVirtualNo"]').val() == '') {
			alert('환불 받으실 계좌의 계좌번호를 입력해주세요.');
			$('input[name="returnVirtualNo"]').focus();
			return false;
		}
		
	}
	
	if($('input[name=claimReasonDetail]').val() == '' || $('input[name=claimReasonDetail]').val() == null) {
		$('input[name=claimReasonDetail]').val(' ');
	}
	
	url += '/process';
	$('input[name="claimReasonText"]').val($claimReason.find(':selected').text());
	
	if (confirm(krName+"을 신청 하시겠습니까?")) {
		$.post(Shop.url(url), $("#" + formName, $layer).serialize(), function(response){
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

/**
 * 구매 확정
 */
Mypage.confirmPurchase = function(orderCode, orderSequence, itemSequence) {
	if (confirm("해당 상품을 구매확정 하시겠습니까?")) {
		
		var param = {
			'orderCode' 		: orderCode,
			'orderSequence'		: orderSequence,
			'itemSequence'		: itemSequence
		}
		
		$.post(Shop.url('/mypage/confirm-purchase'), param, function(response){
			if (response.isSuccess) {
				if (Shop.isMobilePage) {
					Mobile.pagination.clear('order');
				}
				
				location.reload();
			} else {
				alert(response.errorMessage);
				location.reload();
			}
		}, 'json');
	}
}