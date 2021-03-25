<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<script language=javascript src="http://plugin.inicis.com/pay60_escrow.js"></script>
<script type="text/javascript" src="/content/modules/op.mypage.js"></script>
<script type="text/javascript">

var INI_FORM_NAME = 'ini';
var frm = $('form#'+INI_FORM_NAME);

function iniEscrowConfirm(orderCode, orderSequence, itemSequence) {
		
	$('#' + INI_FORM_NAME).remove();			
	$_submitForm = $('<form id="'+ INI_FORM_NAME +'" name="'+ INI_FORM_NAME +'"/>');
	$_submitForm.css('display', 'none');			
	$.each($('div.escrowInputArea').find('input'), function(){							
		var value = $(this).val();
		
		$_submitForm.append($('<input />').attr({
			'type'		: 'hidden',
			'name'		: $(this).attr('name'),
			'value'		: value
		}));
		
	});
	var param = "orderCode="+orderCode+"&orderSequence="+orderSequence+"&itemSequence="+itemSequence;
	var url = getLocalUrl('popup/iniescrow?'+param);
	
	$_submitForm.attr({
		'method'	:	'post',
		'target'	:	'AuthFrmUp',
		'action'	:	url
	});	
	
	$('body').append($_submitForm);
	
	var agent		= navigator.userAgent;
	var midx		= agent.indexOf("MSIE");
	var out_size	= (midx != -1 && agent.charAt(midx+5) < '7');
	
	var width_	= 650;
	var height_	= out_size ? 360 : 360;
	var left_	= screen.width;
	var top_	= screen.height;
	
	left_ = left_/2 - (width_/2);
	top_ = top_/2 - (height_/2);
	
//   	op = window.open(url,'AuthFrmUp',
//   	        'height='+height_+',width='+width_+',status=yes,scrollbars=no,resizable=no,left='+left_+',top='+top_+'');
  	
//   	frm.attr('action', url);
  	op = Common.popup(url, 'AuthFrmUp', 650, 350, 1);
  	
	frm.submit();
// 	Common.loading.show();
			
}


function getLocalUrl(mypage) 
{ 
	var myloc = location.href; 	
	return myloc.substring(0, myloc.lastIndexOf('/')) + '/' + mypage;
} 

//구매확인창
function escrowPopup(frm) {
	var popup = Common.popup('/mypage/popup/iniescrow', 'AuthFrmUp', 650, 350, 1);
	frm.submit();
	Common.loading.hide();
}

function child_message(msg) {
	alert(msg);
}
</script>


