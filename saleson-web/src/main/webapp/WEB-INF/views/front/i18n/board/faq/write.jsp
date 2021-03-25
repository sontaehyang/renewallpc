<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<c:if test="${requestContext.opmanagerPage == true || ngoPage == true}">
<p class="guide">${boardContext.boardCfg.subject} 등록하거나 수정합니다.</p>
</c:if>

<board:header />

		



		<form:form modelAttribute="board" method="post">
			<form:hidden path="boardId"/>
			<input type="hidden" name="boardCode" id="boardCode" value="${boardContext.boardCfg.boardCode}" />
			<input type="hidden" name="token" value="${requestContext.token}" /> 
			

			<div class="board_write">
				<table class="board_write_table">
					<colgroup>
						<col style="width: 150px;" />
					</colgroup>
					
					<c:if test="${boardContext.boardAuthority.boardAdmin && boardContext.boardCfg.etc1 == '1'}">
					<tr style="display: none">
						<td class="label"><span class="required_mark">*</span>게시</td>
						<td><div>
							<p>
								<form:radiobutton path="etc1" value="2" label="미게시" checked="true"  class="required" title="게시여부" />
								<form:radiobutton path="etc1" value="1" label="게시"/>
							</p>
						</div></td>
					</tr>
					</c:if>
					
					<c:if test="${boardContext.boardCfg.useCategory == '1'}">
					<tr>
						<td class="label"><span class="required_mark">*</span>구분</td>
						<td><div>
							<form:select path="category" cssClass="required" title="구분">
								<form:option value="">구분선택</form:option>
								<form:options items="${boardContext.boardCfg.categories}" itemLabel="label" itemValue="value" />
							</form:select>
						</div></td>
					</tr>
					</c:if>
					
					
					<tr>
						<td class="label"><span class="required_mark">*</span>질문</td>
						<td><div>
							<form:input path="subject" cssClass="full required _filter" maxlength="100" title="질문" />
						</div></td>
					</tr>
							
							
					<c:if test="${boardContext.boardCfg.uploadCount > 0}">
					<tr>
						<td class="label">사진첨부</td>
						<td><div>
							<div class="file_attach">
								<ul class="guide">
									<li>- 등록가능 갯수 : 최대 3장</li>
									<li>- 등록가능 파일 : jpg, bmp, gif </li>
									<li>- 용량제한 : 4M 이하</li>
								</ul>

								<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 0, 3, 4)" class="btn_write" title="새창">사진첨부</a>
							
								
								<ul id="temp_image_files" class="files">
									<c:forEach items="${board.boardFiles}" var="file">
										<c:if test="${file.uploadType == '0'}">
											<li id="file_id_${file.fileId}">
												<p class="name">${file.fileName} <a href="javascript:deleteFile(${file.fileId})"><span class="hidden">${file.fileName} </span>삭제</a></p>
												<p class="placeholder_wrap">
													<span class="required_mark">*</span>
													<input type="hidden" name="fileIds" value="${file.fileId}" />
													<input type="text" name="fileDescriptions" value="${file.fileDescription}" class="full required _filter" title="사진에 대한 설명" />
												</p>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="label">파일첨부</td>
						<td><div>
							<div class="file_attach">
								<ul class="guide">
									<li>- 등록가능 파일 : doc, ppt, xls, pdf</li>
									<li>- 용량제한 : 3M이하</li>
								</ul>

								<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 1, 1, 3)" class="btn_write upload-file-button" title="새창">파일첨부</a>
								<ul id="temp_attachment_files" class="files">
									<c:forEach items="${board.boardFiles}" var="file">
										<c:if test="${file.uploadType == '1'}">
											<li id="file_id_${file.fileId}">
												<p class="name">${file.fileName} <a href="javascript:deleteFile(${file.fileId})"><span class="hidden">${file.fileName} </span>삭제</a></p>
												<p class="placeholder_wrap">
													
													<input type="hidden" name="fileIds" value="${file.fileId}" />
													<input type="hidden" name="fileDescriptions" value="${file.fileDescription}" />
												</p>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
							</div>
						</td>
					</tr>
					</c:if>
					
					<tr>
						<td class="label"><span class="required_mark">*</span>답변</td>
						<td><div>
							<form:textarea path="content" cols="30" rows="10" cssClass="required _filter" title="내용" />
						</div></td>
					</tr>
				</table>	
				
				<p class="btns">
					<span class="right">
						<button type="submit" class="btn ${btnClass}">${modeTitle}</button>
						<a href="javascript:cancel('${requestContext.prevPageUrl}')" class="btn gray">취소</a>
					</span>
				</p>
			</div>
		</form:form>

			
				
<board:footer />


<script type="text/javascript" src="<c:url value="/content/modules/op.file.js" />"></script>


<script type="text/javascript">
$(function() {
	// validator
	$('#board').validator(function() {
		
		// 에디터 내용 검증 (내용 입력 여부 / 필터)
		try {
			if ($.validator.validateEditor(editors, "content") == false) return false;
		} catch(e) {}
		
		return Common.confirm("내용을 ${modeTitle} 처리 하시겠습니까?");
	});
});

function cancel(prevUrl) {
	Common.confirm("${modeTitle}을 취소하시겠습니까?", function() {
		if (prevUrl == '') location.href = '${boardContext.boardBaseUri}';
		location.href = prevUrl;
	});
}

function deleteFile(fileId) {
	var boardId = $('#boardId').val();
	var deleteFileUri = '${boardContext.boardBaseUri}/' + boardId + '/deleteFile/' + fileId;
	var params = $('#board').serialize();
	
	Common.confirm("파일을 삭제 하시겠습니까?", function() {
		$.post(deleteFileUri, params, function(response) {
			Common.responseHandler(response, function() {
				alert('파일이 삭제 되었습니다. ');
				$('#file_id_' + fileId).remove();
			});
		});
	});
}

function fn_DeleteSWFUploadFile() {
	var selector = $('.uploaded:checked');
	var boardCode = $('#boardCode').val();
	var boardId = $('#boardId').val();
	var password = $('input[name=password]').val();

	if (selector.size() == 0) {
		alert('삭제하실 파일을 선택하여 주시기 바랍니다. ');
		return;
	}

	try {
		if (password == '') {
			alert('비밀번호를 입력하여 주시기 바랍니다. ');
			return ;
		}
	} catch (e) {
		password = '';
	}

	var params = {
		'boardCode' : boardCode,
		'boardId' : boardId,
		'password' : password
	};

	var deleteFileCount = 0;
	var errorCount = 0;
	$.ajaxSetup({
		async: false
	});
	for (var i= selector.size() - 1; i >= 0 ; i--) {
		if (selector.eq(i).attr("class") == "uploaded") {
			var fileId = selector.eq(i).attr("fileId");
			
			$.post('/board/' + boardCode + '/' + boardId + '/deleteFile/' + fileId, params, function(resp) {
				if (resp.isSuccess) {
					//alert('ok');
					selector.eq(i).parent().remove();
				} else {
					alert(resp.errorMessage);
					errorCount++;
				}
			});
			
		} else {
			selector.eq(i).parent().parent().parent().parent().remove();
			deleteFileCount++;
		}

		if (errorCount > 0) break;
		
	}
	currentFileUploadLimit += deleteFileCount;
	
	swfu.setFileUploadLimit(currentFileUploadLimit);
	swfu.setFileQueueLimit(currentFileUploadLimit);
}
</script>


	<c:if test="${!empty boardFiles}">
	<c:forEach items="${boardFiles}" var="file" varStatus="i">
	<script type="text/javascript">
	fn_DisplayUploadedFile('${file.fileId}', '${file.fileName}', '${file.fileSize}');
	</script>
	</c:forEach>
	</c:if>
	

<c:if test="${boardContext.boardCfg.useEditor == 1}">
<module:smarteditorInit />
<module:smarteditor id="content" />
</c:if>

