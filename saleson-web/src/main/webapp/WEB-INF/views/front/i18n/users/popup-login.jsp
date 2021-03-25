<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<page:javascript>
<script type="text/javascript">
$(function(){
	try{
		opener.PopupLogin.Callback();
	} catch(e) {
		opener.reload();
	} 
	
	self.close();
});
</script>
</page:javascript>