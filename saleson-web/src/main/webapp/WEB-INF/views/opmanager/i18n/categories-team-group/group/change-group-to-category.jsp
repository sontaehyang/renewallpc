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
	<h1 class="popup_title">${op:message('M01598')} <!-- 1차 카테고리로 변경 --></h1>

	<div class="popup_contents">



<c:choose>
<c:when test="${maxSubCategoryLevel == -1}">		
		
		<table class="board_write_table">
			<tr>
				<td style="padding: 50px 10px; text-align: center;">${op:message('M01221')} <!-- 처리되었습니다. --></td>
			</tr>
		</table>
		
		<p class="popup_btns">
			<a href="javascript:moveCategoryPage(${categoriesTeamGroupParam.targetCategoryGroupId})" class="btn btn-active">${op:message('M01194')}</a> <!-- 확인 -->
		</p>
		
		<script>
		function moveCategoryPage(categoryGroupId) {
			opener.location.replace('/opmanager/categories/list?categoryGroupId=' + categoryGroupId);
			self.close();
		}
		</script>
</c:when>

<c:when test="${maxSubCategoryLevel == 4}">		
		
		<table class="board_write_table" summary="엑셀 업로드">
			<tr>
				<td style="padding: 50px 10px; text-align: center; color:#dc4618">${op:message('M01599')}</td> <!-- 하위 카테고리가 4차 카테고리까지 포함된 경우 변경이 불가능합니다. -->
			</tr>
		</table>
		
		<p class="popup_btns">
			<a href="javascript:self.close()" class="btn btn-active">${op:message('M01194')}</a> <!-- 확인 -->
		</p>
</c:when>

<c:when test="${maxSubCategoryLevel < 4}">
		<form id="categoryTeamGroup" name="categoryTeamGroup" action="/opmanager/categories-team-group/group/change-group-to-category" method="post">
			<input type="hidden" name="categoryGroupId" value="${categoryGroupId}" />

			<table class="board_write_table" summary="엑셀 업로드">
				<colgroup>
					<col style="width: 120px" />
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M01600')} <!-- 부모 카테고리 --></td>
						<td>
							<div>
								
								<select name="targetCategoryGroupId" class="required" title="${op:message('M01600')}"> 
									<option value="" >${op:message('M00039')} <!-- 전체 --> </option>
									<c:forEach items="${categoryTeamGroupList}" var="list">
										<optgroup label="${list.name}">
											<c:forEach items="${list.categoriesGroupList}" var="list2">
												<c:if test="${list2.categoryGroupId != categoryGroupId}">
													<option value="${list2.categoryGroupId}" ${op:selected(searchParam.categoryGroupId, list2.categoryGroupId)} >-${list2.groupName}</option>	
												</c:if>
											</c:forEach>
										</optgroup>
									</c:forEach>
								</select>	
								
							</div>
						</td>
					</tr>
				</tbody>
			</table>

	      	
			<p class="popup_btns">
				<button type="submit" class="btn btn-active">${op:message('M00088')}</button> <!-- 등록 -->
				<a href="javascript:closeDownloadPopup();" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
			</p>

		</form>
		
		<script>
		$(function() {
			$('#categoryTeamGroup').validator();
		});
		</script>
		
		
</c:when>
</c:choose>
	
		<a href="#" class="popup_close">창 닫기</a>
	</div>				
</div>

<script>
function closeDownloadPopup(){
	opener.location.reload();
	self.close();
}
</script>