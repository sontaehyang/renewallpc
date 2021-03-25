<%@ tag pageEncoding="utf-8" %>

<script type="text/javascript">

var INI_FORM_NAME = 'INIPAY_FORM';

function iniPayMobile() {
	
	//if ($('.op-app-popup-wrap').size() == 0) {
	//	$('body').wrapInner('<div class="op-app-popup-wrap"></div>');
	//}
	
	//$('.op-app-popup-wrap').hide();
	//$('.op-app-popup-content').remove();
	
	//$div = $('<div class="op-app-popup-content" style="height:'+ window.innerHeight +'px;width:100%"><iframe name="INI_PAY_FRAME" height="100%" width="100%" scrolling="yes" /></div>');
	//$('body').append($div);
	
	$('#' + INI_FORM_NAME).remove();
	
	$_submitForm = $('<form id="'+ INI_FORM_NAME +'"/>');
	$_submitForm.css('display', 'none');
	var paymethod = 'wcard';
	
	$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
		var value = $(this).val();
		if ($(this).attr('name') == 'paymethod') {
			paymethod = value;
		}
	});
	
	$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
		
		var value = $(this).val();
		
		if ($(this).attr('name') == 'P_NEXT_URL' || $(this).attr('name') == 'P_NOTI_URL' || $(this).attr('name') == 'P_RETURN_URL') {
			if (value == '') {
				return;
			}
		}
		
		if ((paymethod == "bank")||(paymethod == "wcard")) {
			if ($(this).attr('name') == 'P_APP_BASE') {
				value = "ON";
			}
		}
		
		$_submitForm.append($('<input />').attr({
			'type'		: 'hidden',
			'name'		: $(this).attr('name'),
			'value'		: value
		}));
	});
	
	$_submitForm.attr({
		'method'			: 'post',
		'accept-charset' 	: 'euc-kr',
		'action'			: 'https://mobile.inicis.com/smart/' + paymethod + '/'
	});
	
	$('body').append($_submitForm);
		
	var escrowStatus = $("#payType-" + $('input[name="payType"]:checked').val()+"-input" + " #escrowStatus").val();
		
	if($('#escrowStatus').val() != null && $('#escrowStatus').val() != 'N')
	{					
		var temp = $('input[name=P_RESERVED]').val()+'&useescrow=Y';
		
		$('input[name=P_RESERVED]').val(temp);
	}
	
	$_submitForm.submit();
	
}

/*
 * 인증결과 처리
 */
function payment_return(P_STATUS, P_REQ_URL, P_TID, orderCode) {
	
	if (P_STATUS == '00') {
		var formName = "INI_PG_SUCCESS_FORM";
		$('#' + formName).remove();
		$_submitForm = $('<form id="'+ formName +'" name="'+ formName +'" />');
		$_submitForm.attr({
			'method'	: 'post',
			'action'	: '/m/order/pay'
		});
		
		$_submitForm.append($('<input />').attr({
			'type'		: 'hidden',
			'name'		: 'P_STATUS',
			'value'		: P_STATUS
		}));
		
		$_submitForm.append($('<input />').attr({
			'type'		: 'hidden',
			'name'		: 'P_REQ_URL',
			'value'		: P_REQ_URL
		}));
		
		$_submitForm.append($('<input />').attr({
			'type'		: 'hidden',
			'name'		: 'P_TID',
			'value'		: P_TID
		}));
		
		$_submitForm.append($('<input />').attr({
			'type'		: 'hidden',
			'name'		: 'orderCode',
			'value'		: orderCode
		}));
		
		$('body').append($_submitForm);
		$_submitForm.submit();
	} else {
		alert('결제 취소 되었습니다.');
		//$('.op-app-popup-wrap').show();
		//$('.op-app-popup-content').remove();
	}
}
</script>