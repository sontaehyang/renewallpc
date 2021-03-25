<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<h3><span>배너관리</span></h3>

<form:form modelAttribute="banner" method="post" enctype="multipart/form-data">

<form:hidden path="bannerId" />
<div class="board_write">
	<table class="board_write_table">
		<colgroup>
			<col style="width: 150px;">
		</colgroup>
		
		
		<tbody>
			 
		<tr>
			<td class="label"><span class="required_mark">*</span>제목</td>
			<td><div>
				<form:input path="subject" cssClass="full required _filter" title="제목" maxlength="255"/>
			</div></td>
		</tr>
		
		<tr>
			<td class="label"><span class="required_mark">*</span>배너 실행</td>
			<td>
				<div>
					<form:radiobutton path="statusCode" value="1" cssClass="required" label="노출" checked="true"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<form:radiobutton path="statusCode" value="2" cssClass="required" label="비노출"/>
			 	</div>
			</td>
		</tr>
			<tr>
			<td class="label">U R L</td>
			<td>
				<div>
					 <div>
					 	<form:input path="bannerLink" cssClass="full required _filter" title="URL" maxlength="255" />
						<br />
						※ URL주소를 넣으실경우 http:// 까지 포함하여 넣어 주세요.
				 	</div>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="label">이미지</td>
			<td>
				 <div class="file_attach">
				 	<a href="javascript:File.open('banner', '${requestContext.token}', 0, 1, 5)" class="btn_write" title="새창">사진첨부</a>
				 	<ul id="temp_image_files" class="files">
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
					</ul>
					<br />
						※ 썸네일 최족의 해상도는 265 * 64 (가로 * 세로) 입니다.
				 </div>
			</td>
		</tr>
		<tr>
			<td class="label">Text 설명</td>
			<td>
				<div>									  
					<form:textarea path="content" cssClass="_filter" title="내용" rows="10" cols="30"/>
				</div> 
			</td>
		</tr>
		 
		 
	</tbody></table>	
	
	<p class="btns">
		<c:if test="${mode == '등록'}">
			<button type="submit" class="btn orange">등록</button>
		</c:if>
		<c:if test="${mode == '수정'}">
			<button type="submit" class="btn white">수정</button>&nbsp;
			<a href="javascript:;" onClick="banner_del()" class="btn white">삭제</a>		&nbsp;
		</c:if>
		<a href="/opmanager/banner/list" class="btn gray">목록</a> &nbsp;
	</p>
</div>
</form:form>
<form id="deleteForm" name="deleteForm" method="post" action="/opmanager/banner/delete">
	<input type="hidden" name="bannerId" id="bannerId" value="${banner.bannerId }" />
</form>

<script type="text/javascript">
$(function() {
	// validator
	$('#banner').validator(function() {
		
	});
});

function banner_del(){
	Common.confirm("배너를 삭제하시겠습니까?", function() {
		$("#deleteForm").submit();
	});
}

function deleteFile(fileId) {
	var bannerId = $("#bannerId").val();
	var deleteFileUri = '/opmanager/banner/deleteFile';
	var params = {
					'bannerId' : bannerId
				};
	
	Common.confirm("파일을 삭제 하시겠습니까?", function() {
		$.post(deleteFileUri, params, function(response) {
			Common.responseHandler(response, function() {
				alert('파일이 삭제 되었습니다. ');
				$('#file_id_' + fileId).remove();
			});
		});
	});
}

</script>