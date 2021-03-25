<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<page:javascript>
<script type="text/javascript">
	
	function setResult() {
		var rcid = "${reCommConId}";
		var rctype = "${reCommType}";
		var rhash = "${reHash}";
		
		try {
				if (typeof(top.opener) == "undefined" || typeof(top.opener.eparamSet) == "undefined" || typeof(top.opener.goResult) == "undefined")
		 		{
		 			alert("ERROR: 주문페이지를 확인할 수 없어 결제를 중단합니다!!");
		 			self.close();
		 			return;
		 		}
				
		 		if (null != rcid && 10 > rcid.length)
		 		{
					alert("ERROR: 결제요청정보("+rcid+")를 확인할 수 없어 결제를 중단합니다!!");
					self.close();
					return;
				}
		 		else
		 		{
					opener.eparamSet(rcid, rctype, rhash);
					opener.goResult();
				}
		} 
		catch (e) {
			alert(e.message);
		}
		setTimeout( 'self.close()', '3000' );
	}
	
	$(function(){
		setResult();
	});
</script>
</page:javascript>