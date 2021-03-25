<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>


<form method="post" name="LGD_PAYINFO" id="LGD_PAYINFO" action="/m/order/pay">
	<input type="hidden" name="orderCode" value="${orderCode}" />
	<input type="hidden" name="LGD_RESPCODE" value="${LGD_RESPCODE}" />
	<input type="hidden" name="LGD_RESPMSG" value="${LGD_RESPMSG}" />
	<input type="hidden" name="LGD_PAYKEY" value="${LGD_PAYKEY}" />
</form>


<page:javascript>
<script type="text/javascript">
	function setLGDResult() {
		try {
			var RESP = "${LGD_RESPCODE}";
			var MSG = "${LGD_RESPMSG}";
			var LGD_PAYKEY = "${LGD_PAYKEY}";
			var LGD_window_type = "submit";
			if (LGD_window_type == 'iframe') {
				parent.payment_return(RESP, MSG, LGD_PAYKEY);
			} else {
				
				if(RESP == '0000'){
					document.getElementById('LGD_PAYINFO').submit();
				} else {
					alert(MSG);
					location.replace('/m/cart');
				}
				
			}
		} catch (e) {
			alert(e.message);
		}
	}
	
	$(function(){
		setLGDResult();
	})
</script>
</page:javascript>