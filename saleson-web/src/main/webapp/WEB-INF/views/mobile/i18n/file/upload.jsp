<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<body class="popup">
	<div class="pop_wrap popup_search">
		<h3 class="pop_head">사진 첨부하기</h3>
		
		<form:form modelAttribute="fileParam" method="post" enctype="multipart/form-data" target="fileUploadActionFrame">
			<form:hidden path="key"/>
			<form:hidden path="token"/>
			<form:hidden path="uploadType"/>
			<form:hidden path="maxUploadCount"/>
			<form:hidden path="separatorId"/>
			
			<div class="pop_cnt">
				<div id="file_input_tags">
				</div>
				<p class="btns">
					<button type="submit" class="btn large maroon"><span>확인</span></button>
				</p>
			</div>
			</form:form>
			<a href="javascript:self.close();" class="pop_close"><span class="icon_close">닫기</span></a>
	</div>
</body>


<page:javascript>
<script type="text/javascript">
File.mobileDisplayInputTag('${fileParam.availableUploadCount}','mobile');

$(function() {
	$('input[type=file]').on("change", $('#file_input_tags'), function() {
		var index = $('input[type=file]').index($(this));
		var filePath = $(this).val();
		var fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
		
		
		$('#fileName' + index).val(fileName);
	});
	
	try{
		parent.Common.loading.hide();
		
	} catch(e) {
		
	}
});
</script>
</page:javascript>
<iframe id="fileUploadActionFrame" name="fileUploadActionFrame" style="display:none"></iframe>