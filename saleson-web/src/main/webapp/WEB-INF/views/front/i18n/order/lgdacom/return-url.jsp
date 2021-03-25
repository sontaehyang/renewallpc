<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<page:javascript>
<script type="text/javascript">
	function setLGDResult() {
		try {
			var RESP = "${LGD_RESPCODE}";
			var MSG = "${LGD_RESPMSG}";
			var LGD_PAYKEY = "${LGD_PAYKEY}";
			var LGD_window_type = "${op:property('pg.lgdacom.viewType')}";
			if (LGD_window_type == 'iframe') {
				parent.payment_return(RESP, MSG, LGD_PAYKEY);
			} else {
				opener.payment_return(RESP, MSG, LGD_PAYKEY);
				self.close();
			}
		} catch (e) {
			alert(e.message);
		}
	}
	
	$(function(){
		setLGDResult();
	});
</script>
</page:javascript>