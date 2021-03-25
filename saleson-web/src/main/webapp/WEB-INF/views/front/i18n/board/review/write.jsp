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
					
					<c:if test="${boardContext.boardAuthority.boardAdmin && (boardContext.boardCfg.useNotice == '1' || boardContext.boardCfg.useSecret == 1)}">
					<tr>
						<td class="label">상단공지</td>
						<td><div>
							<p>
								<form:checkbox path="notice" value="1" label="체크 시 상단에 고정 공지됩니다." /> <em>( 고정 기간 <span>5</span>일 )</em>
								<input type="hidden" name="!notice" value="0" />
							</p>
						</div></td>
					</tr>
					

					<!--
					<tr>
						<th scope="row">옵션</th>
						<td>
						
							<input type="checkbox" name="notice" id="notice" value="1"><label for="notice">공지</label>
							<input type="checkbox" name="secret" id="secret" value="1"><label for="secret">비밀글</label>
						 
							<form:checkbox path="notice" value="1" />공지
							<form:checkbox path="secret" value="1" />비밀글
							 
						</td>
					</tr>
					-->
					</c:if>
					
					<tr>
						<td class="label"><span class="required_mark">*</span>제목</td>
						<td><div>
							<form:input path="subject" cssClass="full required _filter" maxlength="100" title="제목" />
						</div></td>
					</tr>
					
					<tr>
						<td class="label"><span class="required_mark">*</span>신청기관</td>
						<td><div>
							<p>${requestContext.user.userName}</p>
						</div></td>
					</tr>
							
							
					<c:if test="${boardContext.boardCfg.useCategory == '1'}">
					<tr>
						<td class="label"><span class="required_mark">*</span>구분</td>
						<td><div>
							<form:select path="category" cssClass="required" title="구분">
								<form:option value="">분류선택</form:option>
								<form:option value="common">공통</form:option>
								<form:options items="${boardContext.boardCfg.categories}" itemLabel="label" itemValue="value" />
							</form:select>
						</div></td>
					</tr>
					</c:if>
					
							
					<c:if test="${!requestContext.login}">
					<tr>
						<td class="label"><span class="required_mark">*</span>이름</td>
						<td><div><form:input path="userName" cssClass="required _filter" title="이름" /></div></td>
					</tr>
					<tr>
						<td class="label"><span class="required_mark">*</span>비밀번호</td>
						<td><div><form:password path="password" cssClass="required" title="비밀번호" /></div></td>
					</tr>
					</c:if>
					
					<c:if test="${boardContext.boardCfg.useEmail == 1 && requestContext.login == false}">
					<tr>
						<td class="label"><span class="required_mark">*</span>이메일</td>
						<td><div><form:input path="email" cssClass="required _email" title="이메일" /></div></td>
					</tr>
					</c:if>
							

							
					<c:if test="${boardContext.boardCfg.uploadCount > 0}">
					<tr>
						<td class="label"><span class="required_mark">*</span>내용이미지</td>
						<td><div>
							<div class="file_attach">
								<ul class="guide">
									<li>- 기부후기 내용 이미지입니다.
									</li>
									<li>- 파일유형 : Jpg, bmp, gif  /  용량제한 : 4M 이하  /  Size : 960 * 2000 이내</li>
								</ul>

								<a href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', 2, 1, 4)" id="upload_content_image" class="btn_write">이미지올리기</a>
							
								
								<ul id="temp_content_image_files" class="files">
									<c:forEach items="${board.boardFiles}" var="file">
										<c:if test="${file.uploadType == '2'}">
											<li id="file_id_${file.fileId}">
												<p class="name">${file.fileName} <a href="javascript:deleteFile(${file.fileId})"><span class="hidden">${file.fileName} </span>삭제</a></p>
												<p class="placeholder_wrap">
													
													<input type="hidden" name="fileIds" value="${file.fileId}" />
													<textarea name="fileDescriptions" cols="30" rows="10" class="full required _filter" title="내용 이미지에 대한 설명">${file.fileDescription}</textarea>
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
					
					
					<tr style="display:none">
						<td class="label"><span class="required_mark">*</span>내용</td>
						<td><div>
							<form:textarea path="content" cols="30" rows="10" title="내용" />
						</div></td>
					</tr>
					
				</table>	
				
				<p class="guide">- 기부후기 내용은 이미지로 제작하여 등록해 주시기 바랍니다.</p>
				
				<p class="btns">
					<span class="right">
						<button type="submit" class="btn ${btnClass}"><span>${modeTitle}</span></button>
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
		
		
		var fileCount = $('#temp_content_image_files > li').size();
		
		if (fileCount == 0) {
			alert('내용 이미지를 첨부해 주시기 바랍니다. ');
			$('#upload_content_image').focus();
			return false;
			
		}
		
		
		// 파일 설명은 내용으로 등록
		$('#content').val($('textarea[name=fileDescriptions]').val());
		
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

