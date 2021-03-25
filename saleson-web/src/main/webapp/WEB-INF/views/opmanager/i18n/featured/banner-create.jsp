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

		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>		
		<!-- 본문 -->
		<h3><span>특집페이지 상단 배너관리</span></h3> <!-- TOP공통배너관리 -->
		 
		<form method="post" enctype="multipart/form-data">
		
			<input name="editPosition" type="hidden" value="${categoriesEditParam.editPosition}" />
			<input name="code" type="hidden" value="${categoriesEditParam.code}" />
			<input name="editKind" type="hidden" value="${categoriesEditParam.editKind}" />
			<input name="editContents[0]" type="hidden" value="" />
			
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M00414')}">
					<caption>${op:message('M00414')}</caption>
					<colgroup>
						<col style="width: 150px;" />
						<col style="*;" />	
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00413')}</td><!-- URL -->
							<td>
								<div>
									<input type="text" name="editUrls[0]" class="nine" title="${op:message('M00413')}" value="${categoryPromotionList[0].editUrl}" />
								</div>
							</td>
						</tr>
					 	 
						<tr>
							<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
							<td>
								<div>
									<input name="editImages[0]" type="file" title="이미지0 업로드" />
									<input name="fileNames[0]" type="hidden" value="${categoryPromotionList[0].editImage}" /> 
								</div>
							</td>
						</tr>		
						<c:if test="${categoryPromotionList[0].editImage != '' && categoryPromotionList[0].editImage != null }">	 
							<tr>
								<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
								<td>
									<div> 
										<img src="/upload/category-edit/${categoriesEditParam.code}/defult/${categoryPromotionList[0].editImage}" alt="banner">
										<a href="javascript:;" class="file_delete" id="${categoryPromotionList[0].categoryEditId}">[${op:message('M00658')}]</a> <!-- 이미지파일 삭제 -->
									</div>
								</td>
							</tr>
						</c:if> 
					</tbody>
				</table>						  
			</div> <!--// board_write E-->
			
			<div class="btn_center">
				<button type="submit" class="btn btn-active"> ${op:message('M00087')} </button> <!-- 수정 -->
			</div>
		</form>
	
<script type="text/javascript">
	$(function(){
		
		$(".file_delete").on("click",function(){
			
			var param = {
				"categoryEditId" : $(this).attr("id")
			};
			
			$.post("/opmanager/categories-edit/file-delete",param, function(resp){
				Common.responseHandler(resp, function(){
					location.reload();
				});
			});
		});
	});
</script>
