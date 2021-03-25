<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<style type="text/css">
	#LGD_PAYMENTWINDOW {
		position:fixed; 
		z-index:9999;
		top:0px; 
		width:650px; 
		height:650px; 
		font-size:small; 
	}
</style>
<script src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
<script type="text/javascript">
var lgdacomPaySuccess = false;
/*
* iframe으로 결제창을 호출하시기를 원하시면 iframe으로 설정 (변수명 수정 불가)
*/
var LGD_window_type = "${op:property('pg.lgdacom.viewType')}";
var LGD_FORM_NAME = 'XPAY_FORM';
/*
* 수정불가
*/
function launchCrossPlatform() {
	
	$('form#' + LGD_FORM_NAME).remove();
	
	$_submitForm = $('<form id="'+LGD_FORM_NAME+'"/>');
	$_submitForm.attr({
		'method'	: 'post',
		'target'	: 'LGD_PAYMENTWINDOW_IFRAME'
	});
	
	$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
		$_submitForm.append($('<input />').attr({
			'name'		: $(this).attr('name'),
			'value'		: $(this).val()
		}));
	});
	
	$('body').append($_submitForm);
	
	lgdacomPaySuccess = false;
	if (LGD_window_type == 'iframe') {
		
		Common.loading.show();
		
		if ($('#LGD_PAYMENTWINDOW').size() == 0) {
			$('body').append('<div id="LGD_PAYMENTWINDOW"><iframe id="LGD_PAYMENTWINDOW_IFRAME" name="LGD_PAYMENTWINDOW_IFRAME" width="100%" height="80%" scrolling="no" frameborder="0"></iframe></div>');
		}
	}
	
	lgdwin = open_paymentwindow(document.getElementById(LGD_FORM_NAME), "${op:property('pg.lgdacom.serviceType')}", LGD_window_type);
	
	if (LGD_window_type == 'iframe') {
		$('#LGD_PAYMENTWINDOW').css({'left' : ($(window).width() / 2) - 325, 'top' : '35px'});
	}
	
}
/*
* FORM 명만  수정 가능  
*/
function getFormObject() {
	return document.getElementById('buy');
}

/*
 * 인증결과 처리
 */
function payment_return(LGD_RESPCODE, LGD_RESPMSG, LGD_PAYKEY) {
	
	if (LGD_RESPCODE == "0000") {

		$('input[name="LGD_RESPCODE"]', $('form#buy')).val(LGD_RESPCODE);
		$('input[name="LGD_RESPMSG"]', $('form#buy')).val(LGD_RESPMSG);
		$('input[name="LGD_PAYKEY"]', $('form#buy')).val(LGD_PAYKEY);
		
		lgdacomPaySuccess = true;
		document.getElementById('buy').action = "/order/pay";
		document.getElementById('buy').target = "_self";
		document.getElementById('buy').submit();
		
	} else {
		
		if (LGD_window_type == 'iframe') {
			$('#LGD_PAYMENTWINDOW').remove();
		}
		
		if (LGD_RESPMSG != undefined) {
			alert('인증실패 : ' + LGD_RESPMSG);
		}
		
		Common.loading.hide();
	}
	
	
}
</script>
