<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<div class="popup_wrap">
		<h1 class="popup_title">파일 등록하기</h1>
		
		<form id="uploadExcel" method="post" enctype="multipart/form-data">
			<div class="popup_contents">
				<h2>엑셀파일을 등록해주세요</h2>
				<p class="file_attach">
					<input type="file" name="excelFile" id="file1" class="required" title="파일" />
				</p>
				<p class="popup_btns">
					<button type="submit" class="btn orange"><span>등록</span></button>
				</p>
			</div>
			<a href="#" class="popup_close">창 닫기</a>
		</form>
	</div>

<script type="text/javascript">
$(function() {
	$('#file1').on("change", function() {
		var filePath = $(this).val();
		var fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
		$('#fileName').val(fileName);
	});
	
	$('#uploadExcel').validator(function() {
		return Common.confirm("등록 하시겠습니까?");
	});
});

</script>