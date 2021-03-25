<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>


<page:javascript>
<script type="text/javascript">

var KCP_FORM_NAME = 'KCP_PAY_FORM';
var KCP_CHARSET = 'UTF-8';

	function setKCPResult() {
		try {
			<c:choose>
				<c:when test="${kcpRequest.res_cd == '0000'}">
					payment_return('${kcpRequest.res_cd}',"", '${json}');
				</c:when>
				<c:otherwise>					
					payment_return('${kcpRequest.res_cd}',"[${kcpRequest.res_cd}]거래가 취소되었습니다.");
				</c:otherwise>
			</c:choose>		
		} catch (e) {
			alert(e.message);
		}
	}
	
	$(function(){
		setKCPResult();
	});
	
	
	/*
	 * 인증결과 처리
	 */
	function payment_return(KCP_RESPCODE, KCP_RESPMSG, KCP_JSON) {
				
		if (KCP_RESPCODE == "0000") {
			var KCP_JSON = JSON.parse(KCP_JSON);	
			try {
				$('<form id="'+ KCP_FORM_NAME +'"/>').remove();
				$_submitForm = $('<form id="'+ KCP_FORM_NAME +'"/>');
				$_submitForm.css('display', 'none');
				$_submitForm.attr({
					'action'		: '/m/order/pay',
					'target'		: 'tar_opener',
					'method'		: 'post'
				});
				
				$.each(KCP_JSON, function(key, value){
					if (key == 'ordr_idxx') {
						$_input = $('<input />').attr({
							'name'		: 'orderCode',
							'value'		: value
						});
						$_submitForm.append($_input);
					}
					
					if (key != 'orderCode') {
						$_input = $('<input />').attr({
							'name'		: key,
							'value'		: value
						});
					}
					$_submitForm.append($_input);
				});
				$('body').append($_submitForm);
 				$_submitForm.submit();
 				
			} catch(e) {
				alert(e.message);
			}
		} else {			
			if (KCP_RESPMSG != undefined) {
				alert('인증실패 : ' + KCP_RESPMSG);
				location.replace('/m/order/step1');
			}
		}
		
		
	}
	
</script>
</page:javascript>