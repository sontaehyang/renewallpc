<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<style type="text/css">
	#KCP_PAYMENTWINDOW {
		position:fixed; 
		background-color:#fff;
		z-index:9999;
		top:0px; 
		left:0px;
		width:100%; 
		height:100%; 
		font-size:small; 
	}
</style>

<script type="text/javascript" src="${op:property('pg.kcp.g.conf.mobile.js.url')}"></script>

<script type="text/javascript">

var KCP_FORM_NAME = 'order_info';
var KCP_CHARSET = 'UTF-8';

var isMobile = 
{ 
    Android: function () {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function () {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function () {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function () {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function () {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function () {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
};

function init_orderid()
{
  var today = new Date();
  var year  = today.getFullYear();
  var month = today.getMonth() + 1;
  var date  = today.getDate();
  var time  = today.getTime();

  if (parseInt(month) < 10)
    month = "0" + month;

  if (parseInt(date) < 10)
    date  = "0" + date;
  var ipgm_date  = year + "" + month + "" + date;

  document.buy.ipgm_date.value = ipgm_date;
}

/* kcp web 결제창 호츨 (변경불가) */
function call_pay_form()
{
	var v_frm = document.order_info;
	
	document.getElementById("wrap").style.display = "none";
	document.getElementById("layer_all").style.display  = "block";
	
	if(KCP_CHARSET == "UTF-8") {
		v_frm.action = PayUrl.substring(0, PayUrl.lastIndexOf("/")) + "/jsp/encodingFilter/encodingFilter.jsp";
		v_frm.PayUrl.value = PayUrl;
	} else {
		v_frm.action = PayUrl;	
	}

	if (v_frm.Ret_URL.value == "")
	{
	 /* Ret_URL값은 현 페이지의 URL 입니다. */
	 alert("연동시 Ret_URL을 반드시 설정하셔야 됩니다.");
	  return false;
	}
	else
	{	
	  v_frm.submit();
	}
}

function jsf_chk_type()
{
  if ( document.buy.ActionResult.value == "card" )
  {
    document.buy.pay_method.value = "CARD";
    document.order_info.pay_method.value = "CARD";
  }
  else if ( document.buy.ActionResult.value == "acnt" )
  {
    document.order_info.pay_method.value = "BANK";
  }
  else if ( document.buy.ActionResult.value == "vcnt" )
  {
    document.order_info.pay_method.value = "VCNT";
  }
  else if ( document.buy.ActionResult.value == "mobx" )
  {
    document.order_info.pay_method.value = "MOBX";
  }
  else if ( document.buy.ActionResult.value == "ocb" )
  {
    document.order_info.pay_method.value = "TPNT";
    document.order_info.van_code.value = "SCSK";
  }
  else if ( document.buy.ActionResult.value == "tpnt" )
  {
    document.order_info.pay_method.value = "TPNT";
    document.order_info.van_code.value = "SCWB";
  }
  else if ( document.buy.ActionResult.value == "scbl" )
  {
    document.order_info.pay_method.value = "GIFT";
    document.order_info.van_code.value = "SCBL";
  }
  else if ( document.buy.ActionResult.value == "sccl" )
  {
    document.order_info.pay_method.value = "GIFT";
    document.order_info.van_code.value = "SCCL";
  }
  else if ( document.buy.ActionResult.value == "schm" )
  {
    document.order_info.pay_method.value = "GIFT";
    document.order_info.van_code.value = "SCHM";
  }
}

function kcpLaunchCrossPlatform(){
	try {
		
		$('#'+ KCP_FORM_NAME).remove();
		$_submitForm = $('<form id="'+ KCP_FORM_NAME +'" name="'+ KCP_FORM_NAME +'"/>');
		$_submitForm.css('display', 'none');
		$_submitForm.attr({
			'method'	: 	'post',
			'target'	:	'_self'
		});
		
		$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
			if($(this).attr('name') == 'approval_key') {
				$_input = $('<input />').attr({
					'name'		: $(this).attr('name'),
					'id'		: "approval",
					'value'		: $(this).val()
				});
				$_submitForm.append($_input);
			} else {
				$_input = $('<input />').attr({
					'name'		: $(this).attr('name'),
					'id'		: $(this).attr('name'),
					'value'		: $(this).val()					
				});
				$_input2 = $('<input />').attr({
					'name'		: $(this).attr('name'),
					'value'		: $(this).val()
				});
				$_submitForm.append($_input);
			}
		});
		
		$('body').append($_submitForm);
		
		$_submitForm.submit(function(e){
			jsf_chk_type();
			kcp_AJAX();	
			e.preventDefault();
		});
		
		$_submitForm.submit();
		//$('#KCP_PAYMENTWINDOW').show();
		
	} catch(e) {
		alert(e.message)
	}
	return false;
}



$(document).ready(function(){
	$('input[name="ActionResult"]').val('card');
	init_orderid();			
});
</script>
