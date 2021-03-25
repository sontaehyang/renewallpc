<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<style>
.table_btn {position: absolute; top: 77px; right: 18px;}
</style>


		<div class="popup_wrap">
			<h1 class="popup_title">엑셀 다운로드</h1>
			<div class="popup_contents">
				
				<form:form modelAttribute="qna" action="/opmanager/qna/download-excel" method="post" target="downloadFrame">
					<p class="popup_btns">
						<button type="submit" class="btn btn-active"><span>ダウンロード</span></button>
						<button type="button" class="table_btn" onclick="downloadSample()"><span>フォーマットをダウンロード</span></button>
					</p> 
				</form:form>
				
			</div>
			<a href="#" class="popup_close">창 닫기</a>
		</div>

		<iframe id="downloadFrame" name="downloadFrame" style="display: none;"></iframe>

<script type="text/javascript">	
$(function() {
	// 전체 선택 
	$('#check-all').on('click', function() {
		if ($(this).prop('checked')) {
			$('input[name=excelDownloadData]').prop('checked', true);
		} else {
			$('input[name=excelDownloadData]').prop('checked', false);
		}
	});
		
	
	$('#itemParam').validator(function() {
		var checkedCount = $('input[name=excelDownloadData]:checked').size();
		if (checkedCount == 0) {
			alert(Message.get("M00770"));	// 다운로드할 데이터 항목을 선택해 주십시오.
			Common.loading.hide();
			$('input[name=excelDownloadData]').eq(0).focus();
			return false;
		}
		
		$.cookie('DOWNLOAD_STATUS', 'in progress', {path:'/'});
		checkDownloadStatus();
	});
});


// 다운로드 체크
function checkDownloadStatus() {
     if ($.cookie('DOWNLOAD_STATUS') == 'complete') {
    	 $('input[type=checkbox]').prop('checked', false);
    	 $('#excelItemUserCode').val("");
     	Common.loading.hide();
		return;
     } else {
		setTimeout("checkDownloadStatus()", 1000);
     }
}

// 엑셀 양식 다운로드.
function downloadSample() {
	var checkedCount = $('input[name=excelDownloadData]:checked').size();
	if (checkedCount == 0) {
		alert(Message.get("M00770"));	// 다운로드할 데이터 항목을 선택해 주십시오.
		Common.loading.hide();
		$('input[name=excelDownloadData]').eq(0).focus();
		return;
	}
	
	$('#excelItemUserCode').val("Download excel sample!");
	$('.btn btn-active').click();
}
</script>