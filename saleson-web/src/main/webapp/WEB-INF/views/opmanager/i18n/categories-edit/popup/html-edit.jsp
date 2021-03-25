<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

	<div class="popup_wrap">
		
		<h1 class="popup_title">회사소개 프리 HTML 영역 ${op:message('M01325')} <!-- 등록 팝업 --> </h1>
		<form id="categoryEditForm" method="post" action="/opmanager/categories-edit/${mode}">
			<c:if test="${mode == 'update' }">
			<input name="categoryEditId" type="hidden" value="${categoriesEdit.categoryEditId}" />
			</c:if>
			<input name="editPosition" type="hidden" value="${categoriesEditParam.editPosition}" />
			<input name="code" type="hidden" value="${categoriesEditParam.code}" />
			<input name="editKind" type="hidden" value="${categoriesEditParam.editKind}" />
			
			<div class="popup_contents">				
				<h2>¶ 회사소개 프리 HTML 영역 ${op:message('M00088')} <!-- 등록 --> </h2>				
				<div class="board_write">					
					
					<div>
						<textarea name="editContent" id="editContent" cols="30" rows="6" title="메일 내용" class="pop_text">${categoriesEdit.editContent}</textarea>
					</div>
					
				</div> <!--// board_write -->
				
				<p class="popup_btns">
					<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
					<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')} <!-- 취소 --> </a>
				</p>
				
			</div> <!-- // popup_contents -->
		</form>
		<a href="#" class="popup_close">창 닫기</a>
	</div>
	
	<module:smarteditorInit />
	<module:smarteditor id="editContent" />		
	
<script type="text/javascript">
	$(function(){
		
		
		
		$("#categoryEditForm").validator(function() {
			
			Common.getEditorContent("editContent");
			
		});
	});
</script>