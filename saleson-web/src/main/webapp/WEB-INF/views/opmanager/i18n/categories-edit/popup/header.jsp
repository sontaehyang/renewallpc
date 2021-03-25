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
		
		<h1 class="popup_title">${op:message('M01550')} <!-- 컨텐츠 중앙 프리 HTML 영역 --></h1>
		<form id="categoryEditForm" method="post" action="/opmanager/categories-edit/category-create" >
			<input type="hidden" name="categoryId" value="${categories.categoryId}" />
			<input type="hidden" name="categoryFooter" value="" />
			<div class="popup_contents">				
				<h2>¶ ${op:message('M01324')} <!-- HTML 편집 영역 1 팝업 --> </h2>			
				<div>
					<textarea name="categoryHeader" id="categoryHeader" cols="30" rows="6" title="메일 내용" class="pop_text">${categories.categoryHeader}</textarea>
				</div>
					
				<p class="popup_btns">
					<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
					<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')} <!-- 취소 --> </a>
				</p>
				
			</div> <!-- // popup_contents -->
			
			<a href="#" class="popup_close">창 닫기</a>
		</form>
	</div>
		
	<module:smarteditorInit />
	<module:smarteditor id="categoryHeader" />
	
<script type="text/javascript">
	$(function(){
		
		$("#categoryEditForm").validator(function() {
			
			Common.getEditorContent("categoryHeader");
			
		});
	});
</script>