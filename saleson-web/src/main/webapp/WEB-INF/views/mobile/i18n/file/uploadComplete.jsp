<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<!-- 
${fileTempIds}
 -->
<div id="fileList">

<c:forEach items="${fileList}" var="file">

	<div id="temp_file_id_${file.fileId}">
		<p class="attached name">${file.fileName} <a href="javascript:File.deleteTempFile(${file.fileId})" class="icon_delete"><span class="hidden">${file.fileName} </span>삭제</a></p>
		<input type="hidden" name="tempFileIds" value="${file.fileId}" />
		<c:choose>
			<c:when test="${file.uploadType == '0' }">
				<input type="text" name="tempFileDescriptions" class="required _filter" placeholder="사진에 대한 설명을 입력해주세요." />
			</c:when>
			<c:when test="${file.uploadType == '2' }">
				<span class="placeholder">사진에 대한 설명을 입력해주세요.</span>
				<textarea name="tempFileDescriptions" cols="30" rows="10" class="full optional _filter" title="대체 텍스트"></textarea>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="tempFileDescriptions" value="" />
			</c:otherwise>
		</c:choose>
	</div>

</c:forEach>
</div>
 
<page:javascript>
<script type="text/javascript">
$(function() {
	var fileList = $('#fileList').html();
	parent.opener.File.setMobileTempIds('${fileParam.uploadType}', '${fileParam.separatorId}', fileList);
	parent.self.close();
	
	// 관리자 페이지 높이 설정.
	try {
		parent.opener.setHeight();
	} catch(e) {}
});
</script>
</page:javascript>
