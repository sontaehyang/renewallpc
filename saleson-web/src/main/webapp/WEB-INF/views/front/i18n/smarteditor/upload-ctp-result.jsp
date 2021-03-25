<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<c:if test="${empty editorErrorCode}">
	<div id="ctp_content">${ctpContent}</div>
	
	
	<page:javascript> 
	<script type="text/javascript">
	$(function() {
		var ctpContent = $('#ctp_content').html();
		//alert("------------->" + ctpContent);
		setTimeout(function() {
			parent.setCTPToEditor(ctpContent);
		}, 500);
	});
	</script>
	</page:javascript>
</c:if>


<c:if test="${!empty editorErrorCode}">
	<script type="text/javascript">
		var errorMessage = {
			"ERROR_01": '등록이 불가능한 파일입니다. (${editorErrorMessage})',
			"ERROR_02": '최대 등록 가능한 파일 사이즈는 ${editorErrorMessage}MB 입니다.',
			"ERROR_03": 'html 파일은 포함하여 등록해 주세요.'
		};
		parent.Common.loading.hide();
		alert(errorMessage['${editorErrorCode}']);
	</script>
</c:if>

