<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<script type="text/javascript" src="/content/modules/easypay/default.js"></script>
<script type="text/javascript" src="${op:property('pg.easypay.g.conf.test.js.url')}"></script>

<script type="text/javascript">

/* 인증창 호출, 인증 요청 */
function f_cert()
{
    var frm_pay = document.frm_pay;

    /*  주문정보 확인 */
    if( !frm_pay.sp_order_no.value ) 
    {
        alert("가맹점주문번호를 입력하세요!!");
        frm_pay.sp_order_no.focus();
        return;
    }

    if( !frm_pay.sp_product_amt.value ) 
    {
        alert("상품금액을 입력하세요!!");
        frm_pay.sp_product_amt.focus();
        return;
    } 

    /* UTF-8 사용가맹점의 경우 sp_charset 값 셋팅 필수 */
    if( frm_pay.sp_charset.value == "UTF-8" )
    {
        // 한글이 들어가는 값은 모두 encoding 필수.
        frm_pay.sp_mall_nm.value        = encodeURIComponent( frm_pay.sp_mall_nm.value );
        frm_pay.sp_product_nm.value     = encodeURIComponent( frm_pay.sp_product_nm.value );
        frm_pay.sp_user_nm.value        = encodeURIComponent( frm_pay.sp_user_nm.value );
        frm_pay.sp_user_addr.value      = encodeURIComponent( frm_pay.sp_user_addr.value );
    }


	/* 가맹점에서 원하는 인증창 호출 방법을 선택 */   
//     easypay_webpay(frm_pay,"/order/easypay/payment-window","hiddenifr","0","0","iframe",30);
    easypay_card_webpay(frm_pay,"/m/order/easypay/payment-window","_self","0","0","submit",30);
//     easypay_webpay(frm_pay,"/m/order/easypay/payment-window","_self","0","0","submit",30);
}

function f_submit()
{
    var frm_pay = document.frm_pay;

    frm_pay.target = "_self";
    frm_pay.action = "/order/pay";
    frm_pay.submit();
}

</script>

<script type="text/javascript">

var EASYPAY_FORM_NAME = 'frm_pay';

function easyPayLaunch(){

	try {		 
		$('#' + EASYPAY_FORM_NAME).remove();				
		$_submitForm = $('<form id="'+ EASYPAY_FORM_NAME +'" name="'+ EASYPAY_FORM_NAME +'"/>');
		$_submitForm.css('display', 'none');
		$_submitForm.attr({
			'method'	: 'post'
		});
				
		$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){			
			var value = $(this).val();
			
			$_submitForm.append($('<input />').attr({
				'type'		: 'hidden',
				'name'		: $(this).attr('name'),
				'value'		: value
			}));
			
		});
				
		$('body').append($_submitForm);
		
		$_submitForm.submit(function(e){		
			f_cert();
			e.preventDefault();
		});
	
		$_submitForm.submit(); 	
		
// 		popup_pay($_submitForm);		//팝업
// 		iframe_pay($_submitForm);	//iframe
		
	} catch(e) {
		alert(e.message)
	}
	return false;
}

/*
 * 웹표준(실시간 계좌이체)
 */
// function kcpLaunchWebStandard(){
	
// 	EASYPAY_FORM_NAME = "order_info";

// 	try {		 
// 		$('#' + EASYPAY_FORM_NAME).remove();
		
// 		$_submitForm = $('<form id="'+ EASYPAY_FORM_NAME +'" name="'+ EASYPAY_FORM_NAME +'"/>');
// 		$_submitForm.css('display', 'none');
// 		$_submitForm.attr({
// 			'method'	: 'post'
// 		});
				
// 		$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
// 			if ($(this).attr('name') == 'action') {
				
// 				var action = $(this).val();
// 				if (KCP_CHARSET == 'UTF-8') {
// 					action = '/order/pay';
// 				}
				
// 				$_submitForm.attr('action', action);
// 			} else {
// 				var value = $(this).val();
				
// 				$_submitForm.append($('<input />').attr({
// 					'type'		: 'hidden',
// 					'name'		: $(this).attr('name'),
// 					'value'		: value
// 				}));
// 			}
// 		});
		
// 		// 인코딩 관련 인풋 추가
// 		if (KCP_CHARSET == 'UTF-8') {
// 			$_submitForm.append($('<input />').attr({
// 				'name'		: 'encoding_trans',
// 				'value'		: 'UTF-8'
// 			}));
// 		}
		
// 		$('body').append($_submitForm);
		
// 		$_submitForm.submit(function(e){
// 			var frm = document.order_info;
			
// 			jsf__pay(frm);

// 			e.preventDefault();
// 		});
		
// 		$_submitForm.submit(); 		
		
// 	} catch(e) {
// 		alert(e.message)
// 	}
// 	return false;
// }

// function popup_pay(_frm) 
// {
// 	var agent		= navigator.userAgent;
// 	var midx		= agent.indexOf("MSIE");
// 	var out_size	= (midx != -1 && agent.charAt(midx+5) < '7');
	
// 	var width_	= 500;
// 	var height_	= out_size ? 568 : 518;
// 	var left_	= screen.width;
// 	var top_	= screen.height;
	
// 	left_ = left_/2 - (width_/2);
// 	top_ = top_/2 - (height_/2);

//   	op = window.open("aboutblank",'AuthFrmUp',
//   	        'height='+height_+',width='+width_+',status=yes,scrollbars=no,resizable=no,left='+left_+',top='+top_+'');

// 	if (op == null)
// 	{
// 		alert("팝업이 차단되어 결제를 진행할 수 없습니다.");
// 		return false;
// 	}

// 	_frm.submit();
	
// }

function iframe_pay($_submitForm)
{	
	var agent		= navigator.userAgent;
	var midx		= agent.indexOf("MSIE");	
	var height_ = 755;
	var main = getWindowSize(window);
	
	$_submitForm.attr('target','orderWindow_iframe');

	$('iframe[name="orderWindow_iframe"]').attr({
		'width'		:	main.right - main.left,
		'height'	:	main.bottom - main.top - 15,
		'frameborder' : 0,
		'src'		:	'/order/kcp/cardhub'
	});
	
	Common.loading.show();
	
	$_submitForm.submit();
}

function goSubmit(jsonObj){
	
	$_submitForm = $('form#'+ EASYPAY_FORM_NAME);
	
	var jsonStr = JSON.parse(jsonObj);

	$.each(jsonStr, function(key, val){
		
		if(undefined != $_submitForm.find('input[name="'+key+'"]').val()){
			$_submitForm.find('input[name="'+key+'"]').val(val);
		} else {
			$_submitForm.append($('<input />').attr({
				'type'		: 'hidden',
				'name'		: key,
				'value'		: val
			}));
		}
	});
	
	$('body').append($_submitForm);	
	
	$_submitForm.attr({		
		'action' : '/order/pay',
		'target' : null
	});
	
	$_submitForm.submit();
}

function getWindowSize(windowObj) {

    var wT = windowObj.screenTop;
    var wL = windowObj.screenLeft;
    var wW = windowObj.outerWidth;
    var wH = windowObj.outerHeight;

    return { right: wW, bottom: wH, top : wT, left : wL };
}
</script>
