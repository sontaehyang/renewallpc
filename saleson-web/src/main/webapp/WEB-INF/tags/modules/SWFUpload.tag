<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>

<%@ attribute name="fileUploadLimit" required="false"%>
<%@ attribute name="fileSizeLimit" required="false"%>
<%@ attribute name="fileTypes" required="false"%>
<%@ attribute name="useEncrypt" required="false"%>

<c:set var="images" value="/content/modules/SWFUpload/images" />

<c:if test="${empty fileTypes}">
	<c:set var="fileTypes" value="*.jpg;*.jpeg;*.gif;*.exe;*.hwp;*.pdf;*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.txt;*.wmv;*.asf;*.avi;*.mov;*.swf;" />
</c:if>

<link href="/content/modules/SWFUpload/css/SWFUpload.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var swfu;
var op_path_swfupload = "/content/modules/SWFUpload";

</script>
<script type="text/javascript" src="/content/modules/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="/content/modules/SWFUpload/swfupload.queue.js"></script>
<script type="text/javascript" src="/content/modules/SWFUpload/fileprogress.js"></script>
<script type="text/javascript" src="/content/modules/SWFUpload/handlers.js"></script>
<script type="text/javascript">
var currentFileUploadLimit = ${fileUploadLimit} - $('#fsUploadProgress div.file').size();
 $(function(){
	 var fileUploadLimit = ${fileUploadLimit};
	 var possibleUploadLimit = fileUploadLimit - $('#fsUploadProgress div.file').size();

     $('#uploaded-file-check').click(function() {
     	if ($(this).attr("checked") == true) {
     		$('#fsUploadProgress input:checkbox').attr("checked", "checked");
     	} else {
     		$('#fsUploadProgress input:checkbox').attr("checked", "");
     	}
     });
     
     try{
         var settings = {
             flash_url : op_path_swfupload +"/swfupload.swf",
             file_post_name: "file",
             upload_url: "/SWFUpload/upload",	// Relative to the SWF file
             post_params: {"token" : "${requestContext.token}", "useEncrypt" : "${useEncrypt}"},
             file_size_limit : "${fileSizeLimit} MB",
             file_types : "${fileTypes}",
             file_types_description : "Image And Document Files",
             file_upload_limit : possibleUploadLimit,
             file_queue_limit : 0,
             custom_settings : {
                 progressTarget : "fsUploadProgress",
                 cancelButtonId : "btnCancel",
     			submitButtonId : "btnSubmit"
             },
             debug: false,

             // Button Settings
             button_image_url : "/content/modules/SWFUpload/images/btn_add_file.gif",	// Relative to the SWF file
             button_placeholder_id : "spanButtonPlaceHolder",
             button_width: 68,
             button_height: 20,
             
             // The event handler functions are defined in handlers.js
             file_queued_handler : fileQueued,
             file_queue_error_handler : fileQueueError,
             file_dialog_complete_handler : fileDialogComplete,
             upload_start_handler : uploadStart,
             upload_progress_handler : uploadProgress,
             upload_error_handler : uploadError,
             upload_success_handler : uploadSuccess,
             upload_complete_handler : uploadComplete,
          	 // Queue plugin event
             queue_complete_handler : queueComplete	
         };
         swfu = new SWFUpload(settings);
	}catch(e){
		alert(e.message);
	}
});

function fn_DisplayUploadedFile(fileId, fileName, fileSize) {
	var statusHTML = '';
	
	statusHTML += '<div class="file">';
	statusHTML += '<input type="checkbox" name="uploadFileSeq" class="uploaded" fileId="'+fileId+'" /><input type="hidden" name="uploadFileIndex" />';
	statusHTML += '<p style="background:url(/content/images/framework/fileType/'+fn_GetExtension(fileName)+'.gif) no-repeat left 0">' + fileName +'</p>';
	statusHTML += '<span class="fileSize">'+fn_GetFileSize(fileSize)+'</span>';
	statusHTML += '</div>';

	$('#fsUploadProgress').append(statusHTML);
}

function aa(){
	$('#text').val($('#fsUploadProgress').html());
}
</script>


<!-- SWFUpload -->
<input type="hidden" id="fileUploadLimit" value="${fileUploadLimit}" />
<div id="swfupload">
	<div class="swfupload-buttons-area">
		- Max. <strong>${fileUploadLimit}</strong> files / <strong>${fileSizeLimit}</strong> MB
		<!-- 
		- 파일은 총 <strong>${fileUploadLimit}개</strong> 까지  <strong>${fileSizeLimit}MB</strong> 이하로 첨부하실 수 있습니다.
		 -->
	</div>
	<div id="fsUploadHeader">
		<input id="uploaded-file-check" type="checkbox"  style="border:0"/>
		<p><spring:message code='text.filename' /></p>
		<span class="fileSize"><spring:message code='text.filesize' /></span>
	</div>
	<div id="fsUploadProgress">

	</div>
	<p class="swf-upload-buttons">
		<a href="javascript:fn_DeleteSWFUploadFile()" class="delete_file"><spring:message code='button.delete.file' /></a>
		<span id="spanButtonPlaceHolder" class="pointer"></span><img id="btnCancel" src="/content/modules/SWFUpload/images/btn_cancel_trans_disabled.gif" alt="취소" onclick="swfu.cancelQueue();" class="pointer" disabled="disabled"  />
		<!-- <a href="javascript:fn_DeleteSWFUploadFile()">파일삭제</a> -->
	</p>
		
	
</div>

