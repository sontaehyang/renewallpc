<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<script type="text/javascript">
var FORM_NAME = 'KSPAY_FORM';

function ksPayStart() {
	$('#' + FORM_NAME).remove();

	$_submitForm = $('<form id="'+ FORM_NAME +'" name="'+ FORM_NAME +'"/>');
	$_submitForm.css('display', 'none');
	
	$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
		var value = $(this).val().replace(/['"]/g,'');
		
		$_submitForm.append($('<input />').attr({
			'type'		: 'hidden',
			'name'		: $(this).attr('name'),
			'value'		: value
		}));
	});
	
	$_submitForm.attr({
		'method'	: 'post',
		'target'	: 'AuthFrmUp'
	});
	
	$('body').append($_submitForm);
	
	_pay(FORM_NAME);
		
	Common.loading.show();
	$('.ui-spinner').css("display","none");	//dimmed layer에 불필요한 버튼들 표시안함;

}

function _pay(_frm) 
{
	$('form#'+_frm).find('input[name="sndReply"]').val(getLocalUrl("kspay/return"));

	var agent		= navigator.userAgent;
	var midx		= agent.indexOf("MSIE");
	var out_size	= (midx != -1 && agent.charAt(midx+5) < '7');
	
	var width_	= 500;
	var height_	= out_size ? 568 : 518;
	var left_	= screen.width;
	var top_	= screen.height;
	
	left_ = left_/2 - (width_/2);
	top_ = top_/2 - (height_/2);

  	op = window.open('about:blank','AuthFrmUp',
  	        'height='+height_+',width='+width_+',status=yes,scrollbars=no,resizable=no,left='+left_+',top='+top_+'');

   	$('form#'+_frm).attr('action', '${op:property('pg.kspay.url')}');
   	
	if (op == null)
	{
		alert("팝업이 차단되어 결제를 진행할 수 없습니다.");
		return false;
	}
	
	$('form#'+_frm).submit();
	
}

function getLocalUrl(mypage) 
{ 
	var myloc = location.href; 	
	return myloc.substring(0, myloc.lastIndexOf('/')) + '/' + mypage;
} 

// goResult() - 함수설명 : 결재완료후 결과값을 지정된 결과페이지(kspay_wh_result.jsp)로 전송합니다.
function goResult(){
	
	Common.loading.hide();
	
	$('form#buy').submit();
}
// eparamSet() - 함수설명 : 결재완료후 (kspay_wh_rcv.jsp로부터)결과값을 받아 지정된 결과페이지(kspay_wh_result.jsp)로 전송될 form에 세팅합니다.
function eparamSet(rcid, rctype, rhash){

	$('form#buy').find('input[name="reWHCid"]').val(rcid);
	$('form#buy').find('input[name="reWHCtype"]').val(rctype);
	$('form#buy').find('input[name="reWHHash"]').val(rhash);
	
}

</script>

