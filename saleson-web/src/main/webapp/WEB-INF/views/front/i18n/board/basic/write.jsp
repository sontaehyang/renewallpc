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

<board:header />
		<form:form modelAttribute="board" method="post">
			<form:hidden path="boardId"/>
			<input type="hidden" name="boardCode" id="boardCode" value="${boardContext.boardCfg.boardCode}" />
			<input type="hidden" name="token" id="token" value="${requestContext.token}" /> 
			
			<c:if test="${modeTitle == '답변등록'}">
				<input type="hidden" name="secret" id="secret" value="1" />
				<input type="text" name="password" id="password" value="${board.password }" />
			</c:if>
			<div class="board_write">
				<table class="board_write_table">
					<colgroup>
						<col style="width: 150px;" />
					</colgroup>
					
					<c:choose>
						<c:when test="${boardContext.boardCode == 'refer'}">
							<tr>
								<td class="label"><span class="required_mark">*</span>사이트명</td>
								<td><div>
									<form:input path="subject" cssClass="full required _filter" maxlength="100" title="사이트명" />
								</div></td>
							</tr>
						</c:when>
						<c:when test="${boardContext.boardCode == 'webzine'}">
							<tr>
								<td class="label"><span class="required_mark">*</span>제목1</td>
								<td><div>
									<form:input path="subject" cssClass="full required _filter" maxlength="100" title="제목" />
								</div></td>
							</tr>
							<tr>
								<td class="label"><span class="required_mark">*</span>제목2</td>
								<td><div>
									<form:input path="etc2" cssClass="full required _filter" maxlength="100" title="제목" />
								</div></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="label"><span class="required_mark">*</span>제목</td>
								<td><div>
									<form:input path="subject" cssClass="full required _filter" maxlength="100" title="제목" />
								</div></td>
							</tr>
						</c:otherwise>
					</c:choose>
					
					
							
					<c:if test="${boardContext.boardCfg.useCategory == '1'}">
					<tr>
						<td class="label"><span class="required_mark">*</span>구분</td>
						<td><div>
							<form:select path="category" cssClass="required" title="구분">
								<form:option value="">분류선택</form:option>
								<%-- <form:option value="common">공통</form:option> --%>
								<form:options items="${boardContext.boardCfg.categories}" itemLabel="label" itemValue="value" />
							</form:select>
						</div></td>
					</tr>
					</c:if>
					
							
					<c:if test="${boardContext.boardCode == 'qna' && modeTitle != '답변등록' && board.depth == 0}">
						<tr>
							<td class="label"><span class="required_mark">*</span>작성자</td>
							<td><div><form:input path="userName" cssClass="required _filter" title="이름" /></div></td>
						</tr>
						<tr>
							<td class="label"><span class="required_mark">*</span>비밀번호</td>
							<td><div><form:password path="password" cssClass="required" title="비밀번호" /></div></td>
						</tr>
						<tr>
							<td class="label"><span class="required_mark">*</span>전화번호</td>
							<td><div><form:input path="etc3" cssClass="required" title="전화번호" /></div></td>
						</tr>
						<tr>
							<td class="label"><span class="required_mark">*</span>이메일</td>
							<td><div><form:input path="email" cssClass="required _email" title="이메일" /></div></td>
						</tr>
					</c:if>
					
							
					<c:if test="${boardContext.boardCfg.uploadCount > 0}">
						<c:choose>
							<c:when test="${boardContext.boardCode == 'refer'}">
								<tr>
									<td class="label">이미지첨부</td>
									<td><div>
										<div class="file_attach">
											<ul class="guide">
												<li>- 이미지 최적의 해상도는 140 * 35(가로 * 세로)입니다.</li>
												<li><span>- 등록가능 파일 : jpg, bmp, gif</span> <span>- 용량제한 : 5M 이하 </span></li>
											</ul>
			
											<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 0, 1, 5)" class="btn_write" title="새창">사진첨부</a>
										
											
											<ul id="temp_image_files" class="files">
												<c:forEach items="${boardFiles}" var="file">
													<c:if test="${file.uploadType == '0'}">
														<li id="file_id_${file.fileId}">
															<p class="name"><img src="/content/images/common/icon_file.gif" alt="">${file.fileName} 
															<a href="javascript:deleteFile(${file.fileId})">삭제</a></p>
															<p class="placeholder_wrap">
																<!-- <span class="required_mark">*</span> -->
																<input type="hidden" name="fileIds" value="${file.fileId}" />
																<input type="hidden" name="fileDescriptions" value="${file.fileDescription}" class="full required _filter" title="사진에 대한 설명" />
															</p>
														</li>
													</c:if>
												</c:forEach>
											</ul>
										</div>
										</div>
									</td>
								</tr>
							</c:when>
							<c:when test="${boardContext.boardCode == 'download' || boardContext.boardCode == 'irmanage'}">
								<tr>
									<td class="label">파일첨부</td>
									<td><div>
										<div class="file_attach">
													<ul class="guide">
														<li>- 용량제한 : 5M 이하</li>
													</ul>
													<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 1, 1, 5)" class="btn_write upload-file-button" title="새창">파일첨부</a>
											<ul id="temp_attachment_files" class="files">
												<c:forEach items="${boardFiles}" var="file">
													<c:if test="${file.uploadType == '1'}">
														<li id="file_id_${file.fileId}">
															<p class="name"><img src="/content/images/common/icon_file.gif" alt="">${file.fileName} 
															<a href="javascript:deleteFile(${file.fileId})">삭제</a></p>
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
							</c:when>
							<c:when test="${boardContext.boardCode == 'webzine'}">
								<tr>
									<td class="label">이미지첨부</td>
									<td><div>
										<div class="file_attach">
											<ul class="guide">
												<li>- 이미지 최적의 해상도는 320 * 210(가로 * 세로)입니다.</li>
												<li><span>- 등록가능 파일 : jpg, bmp, gif</span> <span>- 용량제한 : 5M 이하 </span></li>
											</ul>
			
											<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 0, 1, 5)" class="btn_write" title="새창">사진첨부</a>
										
											
											<ul id="temp_image_files" class="files">
												<c:forEach items="${boardFiles}" var="file">
													<c:if test="${file.uploadType == '0'}">
														<li id="file_id_${file.fileId}">
															<p class="name"><img src="/content/images/common/icon_file.gif" alt="">${file.fileName} 
															<a href="javascript:deleteFile(${file.fileId})">삭제</a></p>
															<p class="placeholder_wrap">
																<!-- <span class="required_mark">*</span> -->
																<input type="hidden" name="fileIds" value="${file.fileId}" />
																<input type="hidden" name="fileDescriptions" value="${file.fileDescription}" class="full required _filter" title="사진에 대한 설명" />
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
														<li>- 용량제한 : 5M 이하</li>
													</ul>
													<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 1, 3, 5)" class="btn_write upload-file-button" title="새창">파일첨부</a>
											<ul id="temp_attachment_files" class="files">
												<c:forEach items="${boardFiles}" var="file">
													<c:if test="${file.uploadType == '1'}">
														<li id="file_id_${file.fileId}">
															<p class="name"><img src="/content/images/common/icon_file.gif" alt="">${file.fileName} 
																<a href="javascript:deleteFile(${file.fileId})">삭제</a>
															</p>
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
							</c:when>
							<c:otherwise>
								<tr>
									<td class="label">파일첨부</td>
									<td><div>
										<div class="file_attach">
													<ul class="guide">
														<li>- 용량제한 : 5M 이하</li>
													</ul>
													<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 2, 3, 5)" class="btn_write upload-file-button" title="새창">파일첨부</a>
											<ul id="temp_content_files" class="files">
												<c:forEach items="${boardFiles}" var="file">
													<c:if test="${file.uploadType == '2'}">
														<li id="file_id_${file.fileId}">
															<p class="name"><img src="/content/images/common/icon_file.gif" alt="">${file.fileName} 
															<a href="javascript:deleteFile(${file.fileId})">삭제</a></p>
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
							</c:otherwise>
						</c:choose>
					</c:if>
					
					<c:if test="${boardContext.boardCode == 'movie'}">
						<tr>
							<td class="label"><span class="required_mark">*</span>동영상 URL</td>
							<td><div><form:input path="etc2" cssClass="full required _filter" title="동영상 URL" /></div></td>
						</tr>
					</c:if>
					
					<c:choose>
						<c:when test="${boardContext.boardCode == 'refer' || boardContext.boardCode == 'ebook'}">
							<tr>
								<td class="label"><span class="required_mark">*</span>URL</td>
								<td><div>
									<form:input path="content" cssClass="full required _filter" maxlength="100" title="URL" />
								</div></td>
							</tr>
						</c:when>
						<c:when test="${boardContext.boardCode == 'download' || boardContext.boardCode == 'irmanage'}">
							<form:hidden path="content" cssClass="" value="목록다운로드" title="내용" />
						</c:when>
						<c:otherwise>
							<tr>
								<td class="label"><span class="required_mark">*</span>내용</td>
								<td><div>
									<form:textarea path="content" cols="30" rows="10" cssClass="" title="내용" />
								</div></td>
							</tr>
						</c:otherwise>
					</c:choose>
					
				</table>	
				
				<p class="btns">
					<button type="submit" class="btn ${btnClass}"><span>${modeTitle}</span></button>
					<c:if test="${modeTitle == '수정'}">
					<a href="javascript:board_del()" class="btn white">삭제</a>
					</c:if>
					<a href="javascript:cancel('${requestContext.prevPageUrl}')" class="btn gray">취소</a>
				</p>
			</div>
		</form:form>
		
		<form id="deleteForm" name="deleteForm" method="post" action="/opmanager/csia-board/${boardContext.boardCfg.boardCode}/delete">
			<input type="hidden" name="boardCode" id="boardCode" value="${boardContext.boardCfg.boardCode}" />
			<input type="hidden" name="boardId" id="boardId" value="${boardContext.boardId}" />
		</form>

			
				
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

function board_del(){
	Common.confirm("글을 삭제하시겠습니까?", function() {
		$("#deleteForm").submit();
	});
}

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

