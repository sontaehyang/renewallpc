<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<page:javascript>
<script type="text/javascript">

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
			
			alert('${pgData.p_RMESG1}');
			location.href="/m/order/step1";
			
		}
	}

	function setLGDResult() {
		try {
			payment_return("${pgData.p_STATUS}", "${pgData.p_REQ_URL}", "${pgData.p_TID}", "${orderCode}");
		} catch (e) {
			alert(e.message);
		}
	}
	
	$(function(){
		setLGDResult();
	})
</script>
</page:javascript>