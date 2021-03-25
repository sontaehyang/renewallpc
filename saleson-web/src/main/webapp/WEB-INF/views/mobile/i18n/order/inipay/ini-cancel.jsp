<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<page:javascript>
<script type="text/javascript">
$(function(){
	parent.$('.op-app-popup-wrap').show();
	parent.$('.op-app-popup-content').hide();
});
</script>
</page:javascript>