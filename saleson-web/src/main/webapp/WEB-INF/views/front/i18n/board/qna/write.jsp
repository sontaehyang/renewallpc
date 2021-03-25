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
					
					
					<c:if test="${boardContext.boardCfg.useCategory == '1'}">
					<tr>
						<td class="label"><span class="required_mark">*</span>분야</td>
						<td><div>
							<form:select path="category" cssClass="required" title="분야">
								<form:option value="">분야</form:option>
								<form:options items="${boardContext.boardCfg.categories}" itemLabel="label" itemValue="value" />
							</form:select>
						</div></td>
					</tr>
					</c:if>
					
					<tr>
						<td class="label"><span class="required_mark">*</span>이메일</td>
						<td><div class="email">
							<p class="info">- 답변회신 받으실 메일주소를 입력해 주세요</p> 
							<span class="email_wrap">
								<input type="text" name="email1" id="email1" maxlength="20" class="required _email1" title="이메일 앞자리" /> <span>@</span>
								<page:email-domain id="email_domain" />
								<input type="text" name="email2" id="email2" class="required _email1" title="이메일 뒷자리" />
							</span>
							<form:hidden path="email" />
						</div></td>
					</tr>
					
					<tr>
						<td class="label"><span class="required_mark">*</span>제목</td>
						<td><div>
							<form:input path="subject" cssClass="full required _filter" maxlength="100" title="제목" />
						</div></td>
					</tr>
							
					
					<tr>
						<td class="label"><span class="required_mark">*</span>내용</td>
						<td><div>
							<form:textarea path="content" cols="30" rows="10" cssClass="required _filter" title="내용" />
						</div></td>
					</tr>
				</table>	
				
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
		
		$('#email').val($('#email1').val() + "@" + $('#email2').val());
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

