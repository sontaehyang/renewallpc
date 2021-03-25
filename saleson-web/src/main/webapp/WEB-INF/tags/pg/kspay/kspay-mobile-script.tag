<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<script type="text/javascript">
var FORM_NAME = 'KSPAY_FORM';
var iframe_name = 'orderWindow_iframe';
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
		'target'	: iframe_name,
		'action'	: '${op:property("pg.kspay.mbUrl")}'
	});

	$('body').append($_submitForm);
	
	_pay(FORM_NAME);
}

function _iframe($_submitForm)
{	
	var agent		= navigator.userAgent;
	var midx		= agent.indexOf("MSIE");	
	var height_ = 755;
	var main = getWindowSize(window);

	$('iframe[name="'+iframe_name+'"]').attr({
		'width'		:	main.right - main.left,
		'height'	:	main.bottom - main.top - 15,
		'frameborder' : 0,
		'src'		:	'${op:property("pg.kspay.mbUrl")}'
	})
	
	showIframe();
}

function _pay(_frm) 
{
	$('form#'+_frm).find('input[name="sndReply"]').val(getLocalUrl("kspay/return"));

	_iframe($_submitForm);

	$('form#'+_frm).submit();
}

function getLocalUrl(mypage) 
{ 
	var myloc = location.href; 	
	return myloc.substring(0, myloc.lastIndexOf('/')) + '/' + mypage;
} 

// goResult() - 함수설명 : 결재완료후 결과값을 지정된 결과페이지(kspay_wh_result.jsp)로 전송합니다.
function goResult(){	
 	$('form#buy').submit();
}
// eparamSet() - 함수설명 : 결재완료후 (kspay_wh_rcv.jsp로부터)결과값을 받아 지정된 결과페이지(kspay_wh_result.jsp)로 전송될 form에 세팅합니다.
function eparamSet(rcid, rctype, rhash){

	$('form#buy').find('input[name="reWHCid"]').val(rcid);
	$('form#buy').find('input[name="reWHCtype"]').val(rctype);
	$('form#buy').find('input[name="reWHHash"]').val(rhash);
	
}

function hideIframe(){
	$.each($('div.category_page > div'), function(){
		if($(this).attr('class') != "iframe" )
			$(this).css('display','');
	});
 	$('header').css('display','');
	$('form#buy').css('display','');
	$('footer').css('display','');
	$('.tit_basic_wrap').css('display','');
 	$('div.iframe').css('display','none');
 	$('#container').css('padding-top','86px');
}

function showIframe(){
	$.each($('div.category_page > div'), function(){
		if($(this).attr('class') != "iframe" )
			$(this).css('display','none');
	});
 	$('header').css('display','none');
	$('form#buy').css('display','none');
	$('.tit_basic_wrap').css('display','none');
	$('footer').css('display','none');
 	$('div.iframe').css('display',''); 	
 	$('#container').css('padding-top','0px');
}

function getWindowSize(windowObj) {

    var wT = windowObj.screenTop;
    var wL = windowObj.screenLeft;
    var wW = windowObj.outerWidth;
    var wH = windowObj.outerHeight;

    return { right: wW, bottom: wH, top : wT, left : wL };
}
</script>

