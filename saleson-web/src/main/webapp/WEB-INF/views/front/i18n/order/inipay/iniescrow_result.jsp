<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<page:javascript>
<script type="text/javascript">
	
	function result() {

		var isSuccess = "${isSuccess}";

		try {
				if (isSuccess == 'true')
		 		{
					opener.location.reload();
					self.close();	
		 		}
		 		else
		 		{					
		 			self.close();
// 		 	 		setTimeout( 'self.close()', '1000' );
					opener.child_message("ERROR: 구매확정 중 오류가 발생하였습니다!!");		 			
		 			return;
				}
		} 
		catch (e) {
			alert(e.message);
		}
	}
	
	$(function(){
		result();
	});
</script>
</page:javascript>