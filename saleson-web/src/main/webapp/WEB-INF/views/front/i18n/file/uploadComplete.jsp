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
<ul id="fileList">
<c:forEach items="${fileList}" var="file">
	<li id="temp_file_id_${file.fileId}" class="file_list">
		<p class="name">${file.fileName} <a href="javascript:File.deleteTempFile(${file.fileId})" class="file_close"><span class="hidden">${file.fileName}</span><img src="/content/images/btn/btn_file_close.gif" alt="close"></a></p>
		<p class="placeholder_wrap">
			
			<input type="hidden" name="tempFileIds" value="${file.fileId}" />
			<input type="hidden" name="tempFileDescriptions" value="" />
			<%-- <c:choose>
				<c:when test="${file.uploadType == '0' }">
					<span class="required_mark">*</span><span class="placeholder">사진에 대한 설명을 입력해주세요.</span>
					<input type="text" name="tempFileDescriptions" class="full required _filter" title="사진에 대한 설명" />
				</c:when>
				<c:when test="${file.uploadType == '2' }">
					<span class="placeholder">내용이미지에 대한 설명을 입력해주세요.</span>
					<textarea name="tempFileDescriptions" cols="30" rows="10" class="full required _filter" title="내용이미지에 대한 설명"></textarea>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="tempFileDescriptions" value="" />
				</c:otherwise>
			</c:choose> --%>
		</p>
	</li>

</c:forEach>
</ul>

<page:javascript>
<script type="text/javascript">
$(function() {
	var fileList = $('#fileList').html();
	
	parent.opener.$('#temp_files').append(fileList);
	parent.self.close();

});
</script>
</page:javascript>
