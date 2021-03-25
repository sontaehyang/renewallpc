<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<page:javascript>
<script type="text/javascript">
$(function() {
	var fileList = $('#fileList').html();
	
	parent.opener.File.setTempIds('${fileParam.uploadType}', '${fileParam.separatorId}', fileList);
	parent.self.close();
	
	// 관리자 페이지 높이 설정.
	try {
		parent.opener.setHeight();
	} catch(e) {}
});
</script>
</page:javascript>
