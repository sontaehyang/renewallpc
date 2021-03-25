<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<c:choose>
	<c:when test="${op:property('pg.inipay.web.type') == 'webStandard'}">
<!-- 		<script language="javascript" type="text/javascript" src="https://stdpay.inicis.com/stdjs/INIStdPay.js" charset="UTF-8"></script> -->
		<script language="javascript" type="text/javascript" src="https://stgstdpay.inicis.com/stdjs/INIStdPay.js" charset="UTF-8"></script>
		<script type="text/javascript">
			var INI_FORM_NAME = 'INIPAY_FORM';
			function iniPayStart(payType) {				
				$('#' + INI_FORM_NAME).remove();
				
				$_submitForm = $('<form id="'+ INI_FORM_NAME +'" name="'+ INI_FORM_NAME +'"/>');
				$_submitForm.css('display', 'none');
				
				$.each($('div.pgInputArea', $('form#buy')).find('input'), function(){
					
					var value = $(this).val();
					$_submitForm.append($('<input />').attr({
						'type'		: 'hidden',
						'name'		: $(this).attr('name'),
						'value'		: value
					}));
				});
				
				if (payType == 'escrow') {
					$_submitForm.append($('<input />').attr({
						'type'		: 'hidden',
						'name'		: 'useescrow',
						'value'		: 'useescrow'
					}));
				}
				
				$_submitForm.attr({
					'method'	: 'post'
				});
				
				$('body').append($_submitForm);
				
				var escrowStatus = $("#payType-" + $('input[name="payType"]:checked').val()+"-input" + " #escrowStatus").val();
				
				if($('#escrowStatus').val() != null && $('#escrowStatus').val() != 'N')
				{					
					var temp = $('input[name=acceptmethod]').val()+':useescrow';
					
					$('input[name=acceptmethod]').val(temp);
				}
				
				INIStdPay.pay(INI_FORM_NAME);
			}
		</script>
	</c:when>
	<c:otherwise>
		<script language=javascript src="https://plugin.inicis.com/pay61_secunissl_cross.js"></script>
		<script language=javascript>
		StartSmartUpdate();
		function iniPayStart() {
			$cardNumber = $('input[name="cardNumber"]');
			
			if(( navigator.userAgent.indexOf("MSIE") >= 0 || navigator.appName == 'Microsoft Internet Explorer' ) &&  (document.INIpay == null || document.INIpay.object == null) ) {
				alert("\n이니페이 플러그인 128이 설치되지 않았습니다. \n\n안전한 결제를 위하여 이니페이 플러그인 128의 설치가 필요합니다. \n\n다시 설치하시려면 Ctrl + F5키를 누르시거나 메뉴의 [보기/새로고침]을 선택하여 주십시오.");
				return false;
			} 
			
			if (MakePayMessage(document.buy)) {
				document.buy.clickcontrol.value = "disable";
			} else {
				if (IsPluginModule()) {
					alert("결제를 취소하셨습니다.");
				}
				
				return false;
			}
		}
		</script>
	</c:otherwise>
</c:choose>
