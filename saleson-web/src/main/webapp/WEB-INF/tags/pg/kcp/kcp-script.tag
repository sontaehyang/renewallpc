<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
	
<script type="text/javascript">

function jsf__chk_type() {
    if ( document.v3d_form.card_code.value == "CCBC"||document.v3d_form.card_code.value == "CCKM"||document.v3d_form.card_code.value == "CCSU"||
	    document.v3d_form.card_code.value == "CCJB"||document.v3d_form.card_code.value == "CCKJ"||document.v3d_form.card_code.value == "CCPH"||
	    document.v3d_form.card_code.value == "CCSM"||document.v3d_form.card_code.value == "CCPB"||document.v3d_form.card_code.value == "CCSB"||
	    document.v3d_form.card_code.value == "CCKD"||document.v3d_form.card_code.value == "CCCJ"||document.v3d_form.card_code.value == "CCCU" ) {
    	
	   document.v3d_form.card_pay_method.value = "ISP";
	} else if ( document.v3d_form.card_code.value == "CCLG"||document.v3d_form.card_code.value == "CCDI"||document.v3d_form.card_code.value == "CCSS"||
		document.v3d_form.card_code.value == "CCKE"||document.v3d_form.card_code.value == "CCLO"||document.v3d_form.card_code.value == "CCCT"||
	    document.v3d_form.card_code.value == "CCNH"||document.v3d_form.card_code.value == "CCHN" ) {
		
		document.v3d_form.card_pay_method.value = "V3D";
	}
	   
}     

function iFrameUsed( frm ) {
    authViewSelect( "I" ); //I=ifrmae, P=Popup
    
    return jsf__pay_v3d(frm);
}        

function jsf__show_progress( show ) {
    if ( show == true ) {
        window.show_pay_btn.style.display  = 'none';
        window.show_progress.style.display = 'inline';
    } else {
        window.show_pay_btn.style.display  = 'inline';
        window.show_progress.style.display = 'none';
    }
}

function  jsf__chk_v3d_card( form ) {
    if ( form.card_code.value == "" ) {
        alert("카드종류를 선택하여 주시기 바랍니다.");
        form.card_code.focus();
        return false;
    } else {
        return true;
    }
}

function  jsf__pay_v3d( form ) {
    if (jsf__chk_v3d_card( form ) == true) {
        KCP_Pay_Execute( form );
    }
    
    return false ;
}

function m_Completepayment( frm_mpi, closeEvent ) {

    if(document.buy.payType.value == 'card') {
    	var frm = document.v3d_form;
    	
    	if( frm_mpi.res_cd.value == "0000" ) {
            GetField(frm, frm_mpi);

            frm.submit();

            closeEvent();
        } else {
            closeEvent();
    		
            setTimeout( "alert( \"[" + frm_mpi.res_cd.value + "]" + frm_mpi.res_msg.value  + "\");", 1000 );
        }
    } else {
    	var frm = document.order_info; 

        GetField( frm, frm_mpi ); 
        
        if( frm.res_cd.value == "0000" )
        {			    
            /*
                가맹점 리턴값 처리 영역
            */
         
            frm.submit(); 
        }
        else
        {
            alert( "[" + frm.res_cd.value + "] " + frm.res_msg.value );
            
            closeEvent();
        }
    }
}
    
function jsf__pay(form)
{
	try
	{
        KCP_Pay_Execute(form); 
	}
	catch (e)
	{
		/* IE 에서 결제 정상종료시 throw로 스크립트 종료 */ 
	}
}

</script>

<script type="text/javascript" src="${op:property('pg.kcp.g.conf.web.js.url')}"></script>

<script type="text/javascript">

var KCP_FORM_NAME = 'v3d_form';
var KCP_CHARSET = 'UTF-8';

/*
* HUB(카드결제)
*/
function kcpLaunchCrossPlatform(){

	try {		 
		$('#' + KCP_FORM_NAME).remove();				
		$_submitForm = $('<form id="'+ KCP_FORM_NAME +'" name="'+ KCP_FORM_NAME +'"/>');
		$_submitForm.css('display', 'none');
		$_submitForm.attr({
			'method'	: 'post',
			'target'	: 'AuthFrmUp'
		});
				
		$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
			if ($(this).attr('name') == 'action') {
				
				var action = $(this).val();
				if (KCP_CHARSET == 'UTF-8') {
					action = '/order/kcp/cardhub';
				}
				
				$_submitForm.attr('action', action);
			} else {
				var value = $(this).val();
				
				$_submitForm.append($('<input />').attr({
					'type'		: 'hidden',
					'name'		: $(this).attr('name'),
					'value'		: value
				}));
			}
		});
		
		// 인코딩 관련 인풋 추가
		if (KCP_CHARSET == 'UTF-8') {
			$_submitForm.append($('<input />').attr({
				'name'		: 'encoding_trans',
				'value'		: 'UTF-8'
			}));
		}
		
		// 카드 코드 추가
		if (document.buy.payType.value == 'card') {
	 		$_submitForm.append($('<input />').attr({
	 			'name'		: 'card_code',
	 			'value'		: document.buy.card_code.value
	 		}));
		}
		
		$('body').append($_submitForm);
		
// 		popup_pay($_submitForm);		//팝업
		iframe_pay($_submitForm);	//iframe
		
	} catch(e) {
		alert(e.message)
	}
	return false;
}

/*
 * 웹표준(실시간 계좌이체)
 */
function kcpLaunchWebStandard(){

	var good_info = "";
	var chr30 = String.fromCharCode(30);
	var chr31 = String.fromCharCode(31);
	var orderCode = $('input[name="orderCode"]').val();

	for(var i=0, length = Order.getTotalItems().length;i<length;i++){
		good_info += "seq=" + (i+1) + chr31 + "ordr_numb=" + orderCode + chr31 + "good_name=" + Order.getTotalItems()[i].itemName + chr31 + "good_cntx=" + Order.getTotalItems()[i].quantity 
			+ chr31 + "good_amtx=" + Order.getTotalItems()[i].saleAmount;
		if(i+1 != length)
			good_info += chr30;
	}

	KCP_FORM_NAME = "order_info";

	try {		 
		$('#' + KCP_FORM_NAME).remove();
		
		$_submitForm = $('<form id="'+ KCP_FORM_NAME +'" name="'+ KCP_FORM_NAME +'"/>');
		$_submitForm.css('display', 'none');
		$_submitForm.attr({
			'method'	: 'post'
		});
				
		$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
			if ($(this).attr('name') == 'action') {
				
				var action = $(this).val();
				if (KCP_CHARSET == 'UTF-8') {
					action = '/order/pay';
				}
				
				$_submitForm.attr('action', action);
			} else {
				var value = $(this).val();
				
				$_submitForm.append($('<input />').attr({
					'type'		: 'hidden',
					'name'		: $(this).attr('name'),
					'value'		: value
				}));
			}
		});
		
		// 인코딩 관련 인풋 추가
		if (KCP_CHARSET == 'UTF-8') {
			$_submitForm.append($('<input />').attr({
				'name'		: 'encoding_trans',
				'value'		: 'UTF-8'
			}));
		}
		
		// 주문번호 추가
		$_submitForm.append($('<input />').attr({
			'name'		: 'orderCode',
			'value'		: orderCode
		}));
		
		$('body').append($_submitForm);
		
		if($('#escrowStatus').val() != null && $('#escrowStatus').val() != 'N'){
			document.order_info.escw_used.value = "Y";
			document.order_info.pay_mod.value = "Y";
			document.order_info.good_info.value = good_info;
			document.order_info.bask_cntx.value = Order.getTotalItems().length;		
			document.order_info.rcvr_zipx.value = $('input[name="buyer.zipcode"]').val();
			document.order_info.rcvr_add1.value = $('input[name="buyer.address"]').val();
			document.order_info.rcvr_add2.value = $('input[name="buyer.addressDetail"]').val();
		}
		
		$_submitForm.submit(function(e){
			var frm = document.order_info;
			
			jsf__pay(frm);

			e.preventDefault();
		});
		
		$_submitForm.submit(); 		
		
	} catch(e) {
		alert(e.message)
	}
	return false;
}

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
	
	$_submitForm = $('form#'+ KCP_FORM_NAME);
	
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
