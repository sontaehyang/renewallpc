var File = {};

// multiple 가능여부;
File.isSupportMultiple = (function () {
	var el = document.createElement("input");
    return ("multiple" in el);
    
    /*
	var inp = document.createElement("input");
	inp.setAttribute("multiple", "true");
	return inp.multiple===true;
	*/
}());

File.uploadTypeKey= new Array("temp_image_files", "temp_attachment_files", "temp_content_files");



File.open = function(key, token, uploadType, maxUploadCount, maxUploadSize, uniqueId, agentType) {
	var separatorId = "";
	var isMobile = "";

	if (Common.isDefined(uniqueId)) {
		separatorId = "_" + uniqueId;
	}
	
	if (Common.isDefined(agentType)) {
		isMobile = agentType+"/";
	}
	
	
	
	// 등록된 파일 
	var uploadedFileCount = File.getUploadedFileCount(uploadType, separatorId);
	var availableUploadCount = maxUploadCount - uploadedFileCount;
	
	if (uploadedFileCount >= maxUploadCount) {
		alert('파일은 ' + maxUploadCount + '개 까지 첨부하실 수 있습니다.\n등록된 파일을 삭제한 후 다시 첨부하실 수 있습니다.');
		return;
	}
	
	var param = {
		'key' : key,
		'token' : token,
		'uploadType' : uploadType,
		'maxUploadCount' : maxUploadCount,
		'maxUploadSize' : maxUploadSize,
		'availableUploadCount' : availableUploadCount,
		'separatorId' : separatorId
	};
		
	
	
	Common.popup(url('/'+isMobile+'file/upload?' + $.param(param)), 'FileUpload', 500, 500);
	
};

File.setTempIds = function(uploadType, separatorId, inputTag) {
	$('#' + File.uploadTypeKey[uploadType] + separatorId).find("li.default").remove();
	$('#' + File.uploadTypeKey[uploadType] + separatorId).append(inputTag);
};

File.setMobileTempIds = function(uploadType, separatorId, inputTag) {
	$('#' + File.uploadTypeKey[uploadType] + separatorId).append(inputTag);
};

/**
 * 임시 첨부 파일 목록 삭제.
 */
File.deleteTempFile = function(tempFileId) {
	if (confirm('파일을 삭제 하시겠습니까?')) {
		$('#temp_file_id_' + tempFileId).remove();
	}
};


/**
 * 첨부파일 삭제.
 */
File.deleteFile = function(processUri, fileId) {
	if (confirm('파일을 삭제 하시겠습니까?')) {
		$.post(processUri, {'fileId': fileId}, function(response) {
			Common.responseHandler(response, function() {
				$('#file_id_' + fileId).remove();
			});
		});
	}
};



File.popup = {};


File.displayInputTag = function(availableUploadCount) {
	var appendHtml = '';
	for (var i = 0; i < availableUploadCount; i++) {
		
		appendHtml += '<p class="file_attach">';
		appendHtml += '	<input type="text" id="fileName' + i + '" class="path" readonly="readonly" title="파일 경로" />';
		appendHtml += '	<input type="file" id="file' + i + '" name="file[]" class="hidden" title="파일 올리기" />';
		appendHtml += '	<label for="file' + i + '" class="btn_write maroon">찾아보기</label>';
		appendHtml += '</p>\n\n';
		
	}
	$('#file_input_tags').append(appendHtml);
	
};


File.displayOriginalInputTag = function(availableUploadCount) {
	var appendHtml = '';
	for (var i = 0; i < availableUploadCount; i++) {
		
		appendHtml += '<p class="file_attach">';
		appendHtml += '	<input type="file" id="file' + i + '" name="file[]" title="파일 올리기" />';
		appendHtml += '</p>\n\n';
		
	}
	$('#file_input_tags').append(appendHtml);
	
};






File.mobileDisplayInputTag = function(availableUploadCount) {
	var appendHtml = '';
	for (var i = 0; i < availableUploadCount; i++) {
		appendHtml += '<p class="file_attach">';
		appendHtml += '	<input type="text" id="fileName' + i + '" class="path" readonly="readonly" title="파일 경로" />';
		appendHtml += '	<input type="file" id="file' + i + '" name="file[]" class="hidden" title="파일 올리기" />';
		appendHtml += '	<label for="file' + i + '" class="btn">찾아보기</label>';
		appendHtml += '</p>\n\n';
		
	}
	
	$('#file_input_tags').append(appendHtml);
	
};

File.getUploadedFileCount = function(uploadType, separatorId) {
	return $('#' + File.uploadTypeKey[uploadType] + separatorId).find('li').not(".default").size();
};

File.downloadConfirm = function() {
	return confirm('파일을 다운로드 받으시겠습니까?');
};

