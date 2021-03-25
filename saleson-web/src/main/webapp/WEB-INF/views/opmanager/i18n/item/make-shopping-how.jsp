<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<style>
.popup_contents h2 {margin-top: 0; margin-bottom: 5px;}
#result {padding: 15px 10px 30px 10px; height: 165px; overflow-y: auto; margin-bottom: 30px; border: 1px dotted #ccc; color: #444; background:#ffffdd}
#result p.upload_file {font-weight: bold; font-size: 13px; color: #222; text-decoration: underline; margin-bottom: 10px;}
#result p.sheet {padding-top: 15px;}
#result p.error-cell {color: #dc4618; padding: 0 0 5px 10px; font-size: 11px;}
#result span {display: inline-block; font-weight: bold; width: 150px; color: #000;}
</style>
<div class="popup_wrap">
	<h1 class="popup_title">쇼핑하우 txt파일 생성</h1>
	
	<div class="popup_contents">
		<form id="editor_upimage" name="editor_upimage">
			<input type="hidden" name="r" value="1" />
			<h2>피일 관리</h2>
			<table class="board_write_table" summary="엑셀 업로드">
				<colgroup>
					<col style="width: 100px" />
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">기존 파일</td>
						<td>
							<div>
								${fileName}<br/>
								FTP 접속후 확인 바랍니다.
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">신규 생성</td> <!-- 파일 -->
						<td>
							<div>
								<button type="submit" class="btn btn-active">새로 만들기</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<a href="#" class="popup_close">창 닫기</a>
	</div>				
</div>

<iframe id="downloadFrame" name="downloadFrame" style="display: none;"></iframe>

<script>
function closeDownloadPopup(){
	opener.location.reload();
	self.close();
}
</script>