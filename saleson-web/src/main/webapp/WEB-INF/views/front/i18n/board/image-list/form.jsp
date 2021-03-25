<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>

<style>
	.file_attach .files li {
		padding: 0;
	}
	.file_close {
		border-bottom: none !important;
	}
</style>

<board:header />

<form:form modelAttribute="board" method="post">
	<form:hidden path="boardId" />
	<input type="hidden" name="boardCode" id="boardCode"
		value="${boardContext.boardCfg.boardCode}" />
	<input type="hidden" name="token" id="token"
		value="${requestContext.token}" />

	<div class="board_write">
		<table class="board_write_table">
			<colgroup>
				<col style="width: 150px;" />
			</colgroup>
			<c:if
				test="${boardContext.boardAuthority.boardAdmin && (boardContext.boardCfg.useNotice == '1' || boardContext.boardCfg.useSecret == 1)}">
				<tr>
					<td class="label">상단공지</td>
					<td><div>
							<p>
								<form:checkbox path="notice" value="1"
									label="체크 시 상단에 고정 공지됩니다." />
								<em>( 고정 기간 <span>5</span>일 )
								</em> <input type="hidden" name="!notice" value="0" />
							</p>
						</div></td>
				</tr>
			</c:if>

			<c:if test="${boardContext.boardCfg.useCategory == '1'}">
				<tr>
					<td class="label"><span class="required_mark">*</span>구분</td>
					<td><div>
							<form:select path="category" cssClass="required" title="구분">
								<form:option value="">분류선택</form:option>
								<form:option value="common">공통</form:option>
								<form:options items="${boardContext.boardCfg.categories}"
									itemLabel="label" itemValue="value" />
							</form:select>
						</div></td>
				</tr>
			</c:if>


			<tr>
				<td class="label">제목<span class="required_mark">*</span></td>
				<td><div>
						<form:input path="subject" class="subject form-block required"
							maxlength="100" title="제목" />
					</div></td>
			</tr>
			<c:if test="${!requestContext.login}">
				<tr>
					<td class="label">작성자<span class="required_mark">*</span></td>
					<td><div>
							<form:input path="userName" cssClass="required"
								title="이름" />
						</div></td>
				</tr>
				<tr>
					<td class="label">비밀번호<span class="required_mark">*</span></td>
					<td><div>
							<form:password path="password" cssClass="required" title="비밀번호" />
						</div></td>
				</tr>
			</c:if>

			<c:if
				test="${boardContext.boardCfg.useEmail == 1 && !requestContext.login}">
				<tr>
					<td class="label">이메일<span class="required_mark">*</span></td>
					<td><div>
							<form:input path="email" cssClass="required _email" title="이메일" />
						</div></td>
				</tr>
			</c:if>
			<tr>
				<td class="label">파일첨부</td>
				<td><div>
						<div class="file_attach">
						<%-- 
							<ul class="guide">
								<li>- 용량제한 : ${boardCfg.uploadSize}M 이하</li>
								<li>- 파일 : *.doc;*.docx;*.xls;*xlsx;*.ppt;*.pptx;*.pdf;*.tif;*.tiff</li>
							</ul>
						--%>
							<a
								href="javascript:File.open('${boardContext.boardCode}', '${requestContext.token}', ${boardCfg.uploadExtension}, ${boardCfg.uploadCount}, ${boardCfg.uploadSize})"
								class="btn btn-dark-gray btn-sm upload-file-button btn-normal" title="새창">파일첨부</a>
							<ul id="temp_files" class="files" style="margin-bottom: 0">
								<c:forEach items="${boardFiles}" var="file">
									<li id="file_id_${file.fileId}" class="file_list">
										<p class="name">
											${file.fileName}
											<a href="javascript:deleteFile(${file.fileId})" class="file_close">
												<img src="/content/images/btn/btn_file_close.gif" alt="close">
											</a>
										</p>
										<p class="placeholder_wrap">
											<input type="hidden" name="fileIds" value="${file.fileId}" />
											<input type="hidden" name="fileDescriptions"
												value="${file.fileDescription}" />
										</p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div></td>
			</tr>
			<tr>
				<td class="label">내용<span class="required_mark">*</span></td>
				<td><div>
						<form:textarea path="content" cols="30" rows="10" title="내용" class="content" />
					</div></td>
			</tr>
		</table>

		<p class="btns board-button">
			<button type="submit" class="btn btn-active">
				<span>저장</span>
			</button>
			<a href="javascript:cancel('${requestContext.prevPageUrl}')"
				class="btn btn-normal">취소</a>
		</p>
	</div>
</form:form>

<board:footer />


<page:javascript>
<script type="text/javascript"
	src="<c:url value="/content/modules/op.file.js" />"></script>

<script type="text/javascript">
	$(function() {
		// validator
		$('#board').validator(function() {

			// 에디터 내용 검증 (내용 입력 여부 / 필터)
			try {
				if ($.validator.validateEditor(editors, "content") == false)
					return false;
			} catch (e) {
			}

			return Common.confirm("내용을 등록 하시겠습니까?");
		});
	});

	function cancel(prevUrl) {
		if (prevUrl == '')
			location.href = '${boardContext.boardBaseUri}';
		location.href = prevUrl;
	}

	function deleteFile(fileId) {
		var boardId = $('#boardId').val();
		var deleteFileUri = '${boardContext.boardBaseUri}/' + boardId
				+ '/deleteFile/' + fileId;
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
				return;
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
			async : false
		});
		for (var i = selector.size() - 1; i >= 0; i--) {
			if (selector.eq(i).attr("class") == "uploaded") {
				var fileId = selector.eq(i).attr("fileId");

				$.post('/board/' + boardCode + '/' + boardId + '/deleteFile/'
						+ fileId, params, function(resp) {
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

			if (errorCount > 0)
				break;

		}
		currentFileUploadLimit += deleteFileCount;

		swfu.setFileUploadLimit(currentFileUploadLimit);
		swfu.setFileQueueLimit(currentFileUploadLimit);
	}
</script>


<c:if test="${!empty boardFiles}">
	<c:forEach items="${boardFiles}" var="file" varStatus="i">
		<script type="text/javascript">
			fn_DisplayUploadedFile('${file.fileId}', '${file.fileName}',
					'${file.fileSize}');
		</script>
	</c:forEach>
</c:if>


<c:if test="${boardContext.boardCfg.useEditor == '1'}">
	<module:smarteditorInit />
	<module:smarteditor id="content" />
</c:if>
</page:javascript>