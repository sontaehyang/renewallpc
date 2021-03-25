<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<style type="text/css">
	#LGD_PAYMENTWINDOW {
		position:fixed; 
		z-index:9999;
		top:0px; 
		left:0px;
		width:100%; 
		height:100%; 
		font-size:small; 
	}
</style>
<script src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
<script type="text/javascript">
var lgdacomPaySuccess = false;
/*
* iframe으로 결제창을 호출하시기를 원하시면 iframe으로 설정 (변수명 수정 불가)
*/
var LGD_window_type = "submit";
/*
* 수정불가
*/
function launchCrossPlatform(){
	lgdacomPaySuccess = false;
	if (LGD_window_type == 'iframe') {
		if ($('#LGD_PAYMENTWINDOW').size() == 0) {
			$('body').append('<div id="LGD_PAYMENTWINDOW"><iframe id="LGD_PAYMENTWINDOW_IFRAME" name="LGD_PAYMENTWINDOW_IFRAME" width="100%" height="100%" scrolling="no" frameborder="0"></iframe></div>');
		}
	} 
	
	lgdwin = open_paymentwindow(document.getElementById('buy'), "${op:property('pg.lgdacom.serviceType')}", LGD_window_type);
}
/*
* FORM 명만  수정 가능  
*/
function getFormObject() {
	return document.getElementById("buy");
}

/*
 * 인증결과 처리
 */
function payment_return(LGD_RESPCODE, LGD_RESPMSG, LGD_PAYKEY) {
	
	if (LGD_RESPCODE == "0000") {

		$('input[name="LGD_RESPCODE"]').val(LGD_RESPCODE);
		$('input[name="LGD_RESPMSG"]').val(LGD_RESPMSG);
		$('input[name="LGD_PAYKEY"]').val(LGD_PAYKEY);
		
		lgdacomPaySuccess = true;
		document.getElementById("buy").action = "/m/order/pay";
		document.getElementById("buy").target = "_self";
		document.getElementById("buy").submit();
		
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
