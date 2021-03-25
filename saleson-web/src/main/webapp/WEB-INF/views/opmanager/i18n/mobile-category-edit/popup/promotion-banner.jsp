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

<!-- 본문 -->
		<div class="popup_wrap">
			
			<h1 class="popup_title">${op:message('M01295')} <!-- 프로모션 배너영역 --> ${op:message('M01296')} <!-- 팝업 --> </h1>
			
			<div class="popup_contents">				
				<h2>¶ ${op:message('M01295')} <!-- 프로모션 배너영역 --></h2>		
						
				<form id="promotionForm" method="post" enctype="multipart/form-data" action="/opmanager/mobile-category-edit/promotion-banner/create">
				
					<input name="editPosition" type="hidden" value="${categoriesEditParam.editPosition}" />
					<input name="code" type="hidden" value="${categoriesEditParam.code}" />
					<input name="editKind" type="hidden" value="${categoriesEditParam.editKind}" />
					
					<div class="board_write">					
						<table class="board_write_table" summary="프로모션 배너영역">
							<caption>${op:message('M01295')} <!-- 프로모션 배너영역 --> </caption>
							<colgroup>
								<col style="width: 20%;" />
								<col style="width: auto;" />
							</colgroup>
							<tbody>
								<c:forEach begin="0" end="9"  var="list" varStatus="i">
									<tr>
										<td class="label">${op:message('M01130')} <!-- 연결 URL(링크) --> </td>
										<td>
											<div>
												<input name="editUrls[${i.index}]" type="text" class="w478" title="연결 URL(링크)" value="${categoryPromotionList[i.index].editUrl }" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="label">${op:message('M00752')} <!-- 이미지 -->${i.count} ${op:message('M01297')} <!-- 업로드 -->
											<br/> (640 x 380)</td>
										<td>
											<div>
												<c:if test="${categoryPromotionList[i.index].editImage == null || categoryPromotionList[i.index].editImage == ''}">
													<input name="editImages[${i.index}]" type="file" title="이미지${i.count} 업로드" />
													<br />
													<input name="fileNames[${i.index}]" type="hidden" value="" />
												</c:if>
												<c:if test="${categoryPromotionList[i.index].editImage != '' && categoryPromotionList[i.index].editImage != null }">
													<img src="/upload/mobile-category-edit/${categoriesEditParam.code}/${categoriesEditParam.editPosition}/${categoryPromotionList[i.index].editImage}" alt="banner" width="150px" height="150px">
													${categoryPromotionList[i.index].editImage} <a href="javascript:;" class="table_btn file_delete" id="${categoryPromotionList[i.index].categoryEditId}" title="파일 삭제">${op:message('M01511')} </a> <!-- 파일 삭제 -->
													<br />
													<input name="editImages[${i.index}]" type="file" title="이미지${i.count} 업로드" />
													<input name="fileNames[${i.index}]" type="hidden" value="${categoryPromotionList[i.index].editImage}" /> 
												</c:if>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
					</div> <!--// board_write -->
					
					<p class="popup_btns">
						<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
						<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')} <!-- 취소 --> </a>
					</p>
				</form>
			</div> <!-- // popup_contents -->
			
			<a href="#" class="popup_close">창 닫기</a>
		</div>
	<script type="text/javascript">
		$(function(){
			$(".file_delete").on("click",function(){
				
				var param = {
					"categoryEditId" : $(this).attr("id")
				};
				
				$.post("/opmanager/mobile-category-edit/file-delete",param, function(resp){
					Common.responseHandler(resp, function(){
						location.reload();
					});
				});
			});
		});
	</script>