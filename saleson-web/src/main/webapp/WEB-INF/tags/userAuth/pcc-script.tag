<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 

<c:if test="${op:property('user.auth.ipin.service') == 'siren24'}">
	
<script type="text/javascript">
	function pccAuth(target) {
		var param = {
			'target' 	: target,
			'type'		: 'pcc'
		};
		
		Common.popup("${op:property('saleson.url.shoppingmall')}/users/user-auth?type=pcc&target="+target, "PCCWindow", 450, 500, 1);
	}
</script>
	
</c:if>