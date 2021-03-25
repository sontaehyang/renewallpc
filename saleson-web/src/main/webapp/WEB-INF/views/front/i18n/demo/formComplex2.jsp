<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<form action="/demo/formComplex2" method="post" enctype="multipart/form-data">
<a  id="add">추가</a>
<ul id="data_list">
<!-- 
	<c:forEach begin="0" end="10" step="1" var="i">
	<li style="padding:10px; border-top: 1px dotted #ccc; margin-top: 10px;">
		제목 : <input type="text" name="demos[${i}].title" value="제목${i}" /><br />
		내용 : <input type="text" name="demos[${i}].content" value="내용${i}" /><br />
		날짜 : <input type="text" name="demos[${i}].date" value="날짜${i}" /><br />
		템프파일 : <input type="text" name="demos[${i}].tempFileId" value="템프파일${i}_1" /><br />
		템프파일 : <input type="text" name="demos[${i}].tempFileId" value="템프파일${i}_2" />
	</li>
	</c:forEach>
	 -->
</ul>

<input type="submit" value="전송" />



<script>
$(function() {
	$('#add').on("click", function() {
		var index = $('#data_list li').size();
		
		var appendHtml = '';
		appendHtml += '<li style="padding:10px; border-top: 1px dotted #ccc; margin-top: 10px;">';
		appendHtml += '제목 : <input type="text" name="demos[' + index + '].title" value="제목' + index + '" /><br />';
		appendHtml += '내용 : <input type="text" name="demos[' + index + '].content" value="내용' + index + '" /><br />';
		appendHtml += '날짜 : <input type="text" name="demos[' + index + '].date" value="날짜' + index + '" /><br />';
		appendHtml += '<a href="javascript:File.open(\'notice\', \'565757\', 1, 1, ' + index + ')" class=\"btn_write\">파일첨부</a><br />';
		appendHtml += '<ul id="temp_attachment_files_' + index + '" class="files"></ul>';
		
		
		appendHtml += '</li>';
		
		$('#data_list').append(appendHtml);
	});
	
});

</script>
</form>