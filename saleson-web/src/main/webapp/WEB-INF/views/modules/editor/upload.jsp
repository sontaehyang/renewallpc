<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<script type="text/javascript" src="/content/modules/jquery/jquery-1.5.2.min.js"></script>
 
<img id="uploaded_image" src="${editorUpload.saveFileName}" />
<script language="javascript">
$(document).ready( function() {
	var image_width_limit = 580;  //에디터에 포함될 이미지의 가로 사이즈
	var uploadImageWidth = $('#uploaded_image').width();
	
	var uploadImage = {
		tempFileId 			: '${editorUpload.tempFileId}',
		editorId 			: '${editorUpload.editorId}',
		width 				: '${editorUpload.width}',
		height 				: '${editorUpload.height}',
		alt 				: '${editorUpload.alt}',
		align 				: '${editorUpload.align}',
		saveFileName 		: '${editorUpload.saveFileName}',
		uploadImageWidth 	: uploadImageWidth
	};
	
	parent.parent.insertImage(uploadImage);

});
</script>