<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>


<div class="popup_wrap">
	<h1 class="popup_title">${title}</h1>
	<div class="popup_contents">
	
	
		<form:form modelAttribute="fileParam" method="post" class="without-loading" enctype="multipart/form-data" target="fileUploadActionFrame" onsubmit="return validFileForm(this)">
			<form:hidden path="key"/>
			<form:hidden path="token"/>
			<form:hidden path="uploadType"/>
			<form:hidden path="maxUploadCount"/>
			<form:hidden path="maxUploadSize"/>
			<form:hidden path="separatorId"/>
			
			<div id="file_input_tags">
				
			</div>

		
			<p class="popup_btns">
				<button type="submit" class="btn brown"><span>업로드</span></button>
			</p>
			
		</form:form>

	</div>
	<a href="#" class="popup_close">창 닫기</a>
</div>

<page:javascript>
<script type="text/javascript">
$(function() {
	try {
		var fileCount = '${fileParam.availableUploadCount}' - opener.$('.file_list').length;
	} catch (e){
		
	}
	
	if (fileCount <= 0) {
		alert('첨부 가능한 파일의 개수는 ' + '${fileParam.availableUploadCount}' + '개까지 입니다.');
		self.close();
	}
	
	File.displayOriginalInputTag(fileCount);
	$('input[type=file]').on("change", $('#file_input_tags'), function() {
		
		/* only File.displayInputTag() method
		var index = $('input[type=file]').index($(this));
		var filePath = $(this).val();
		var fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
		
		
		$('#fileName' + index).val(fileName);
		*/
	});
	
	try{
		parent.Common.loading.hide();
	} catch(e) {
		
	}
});

function validFileForm(form) {
	var fileInputCount = $('input[type=file]').size();
	
	var fileSelectCount = 0;
	for (var i = 0; i < fileInputCount; i++) {
		/*
		if ($.trim($('#fileName' + i).val()) != '') {
			fileSelectCount++;
			break;
		};
		*/
		
		if ($.trim($('#file' + i).val()) != '') {
			fileSelectCount++;
			break;
		};
	}
	
	if (fileSelectCount == 0) {
		alert('파일을 선택한 후 업로드 가능 합니다. ');
		return false;
	}
	Common.loading.show();

	
}
</script>
</page:javascript>

<iframe id="fileUploadActionFrame" name="fileUploadActionFrame" style="display:none"></iframe>