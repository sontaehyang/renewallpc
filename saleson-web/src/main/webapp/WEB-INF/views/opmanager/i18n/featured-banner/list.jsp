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
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<c:set var="todayTime" value="${shop:todayTime('yyyyMMddHHmmss')}" />

<!-- MiniColors -->
<script src="/content/modules/jquery/minicolors/jquery.minicolors.js"></script>
<link rel="stylesheet" href="/content/modules/jquery/minicolors/jquery.minicolors.css" />


	<div class="popup_wrap">
		<h1 class="popup_title">기획전 배너관리영역 ${op:message('M01325')} <!-- 등록 팝업 --> </h1>	
		
		<form:form modelAttribute="featuredBanner" action="/opmanager/categories-edit/featured-banner/edit" method="post" enctype="multipart/form-data">
			<input type="hidden" class="deleteImage" name="deleteImage" />
			
			<div class="popup_contents">	
				<h2>¶ 기획전 배너관리영역 ${op:message('M00088')} <!-- 등록 --> </h2>
				<div class="board_write" style="overflow: visible;">
					<table class="board_write_table" summary="${op:message('M00414')}">
						<caption>${op:message('M00414')}</caption>
						<colgroup>
							<col style="width: 150px;" />
							<col style="width: 150px;" />
							<col style="*;" />	
						</colgroup>
						<tbody>
							<tr>
								<td class="label" rowspan="3">배너 이미지 (좌상)<br />(350 x 179)</td>
								<td class="label">배너 타이틀</td>
								<td>
									<div>
										<form:input path="bannerLeftTopTitle" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00413')}</td> <!-- URL -->
								<td>
									<div>
										<form:input path="bannerLeftTopLink" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<c:set var="hideFlag" value="none"/>
							<c:if test="${featuredBanner.bannerLeftTopImage == '' || featuredBanner.bannerLeftTopImage == null }">
								<c:set var="hideFlag" value=""/>
							</c:if>	 		
							<tr class="imageUpload_1" style="display: ${hideFlag}">
								<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="bannerImages[0].uploadFile" title="이미지 업로드" />
										<input type="hidden" name="bannerImages[0].code" value="left-top" />
									</div>
								</td>
							</tr>	
							<c:if test="${featuredBanner.bannerLeftTopImage != '' && featuredBanner.bannerLeftTopImage != null }">	 
								<tr class="bannerImage_1">
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div> 
											<img src="/upload/featured-banner/${featuredBanner.featuredBannerId}/${featuredBanner.bannerLeftTopImage}?${todayTime}"  width="350" height="179" alt="banner">
											${featuredBanner.bannerLeftTopImage}&nbsp;&nbsp;<a href="javascript:;" onclick="addDeleteImage('${featuredBanner.bannerLeftTopImage}', '1')" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if> 
						</tbody>
					</table>
					
					<table class="board_write_table">
						<colgroup>
							<col style="width: 150px;" />
							<col style="width: 150px;" />
							<col style="*;" />	
						</colgroup>
							<tr>
								<td class="label" rowspan="3">배너 이미지 (좌하1)<br />(175 x 180)</td>
								<td class="label">배너 타이틀</td>
								<td>
									<div>
										<form:input path="bannerLeftBottom1Title" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00413')}</td> <!-- URL -->
								<td>
									<div>
										<form:input path="bannerLeftBottom1Link" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<c:set var="hideFlag" value="none"/>		
							<c:if test="${featuredBanner.bannerLeftBottom1Image == '' || featuredBanner.bannerLeftBottom1Image == null}">
								<c:set var="hideFlag" value=""/>
							</c:if>
							<tr class="imageUpload_2" style="display: ${hideFlag}">
								<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="bannerImages[1].uploadFile" title="이미지 업로드" />
										<input type="hidden" name="bannerImages[1].code" value="left-bottom1" />
									</div>
								</td>
							</tr>	
							<c:if test="${featuredBanner.bannerLeftBottom1Image != '' && featuredBanner.bannerLeftBottom1Image != null }">	 
								<tr class="bannerImage_2">
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div> 
											<img src="/upload/featured-banner/${featuredBanner.featuredBannerId}/${featuredBanner.bannerLeftBottom1Image}?${todayTime}" width="175" height="180" alt="banner">
											${featuredBanner.bannerLeftBottom1Image}&nbsp;&nbsp;<a href="javascript:;" onclick="addDeleteImage('${featuredBanner.bannerLeftBottom1Image}', '2')" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					
						<table class="board_write_table">
							<colgroup>
								<col style="width: 150px;" />
								<col style="width: 150px;" />
								<col style="*;" />	
							</colgroup>	
							<tr>
								<td class="label" rowspan="3">배너 이미지 (좌하2)<br />(174 x 180)</td>
								<td class="label">배너 타이틀</td>
								<td>
									<div>
										<form:input path="bannerLeftBottom2Title" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00413')}</td> <!-- URL -->
								<td>
									<div>
										<form:input path="bannerLeftBottom2Link" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>		
							<c:set var="hideFlag" value="none"/>
							<c:if test="${featuredBanner.bannerLeftBottom2Image == '' || featuredBanner.bannerLeftBottom2Image == null}">
								<c:set var="hideFlag" value=""/>
							</c:if>							
							<tr class="imageUpload_3" style="display: ${hideFlag}">
								<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="bannerImages[2].uploadFile" title="이미지 업로드" />
										<input type="hidden" name="bannerImages[2].code" value="left-bottom2" />
									</div>
								</td>
							</tr>	
							<c:if test="${featuredBanner.bannerLeftBottom2Image != '' && featuredBanner.bannerLeftBottom2Image != null}">	 
								<tr class="bannerImage_3">
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div> 
											
											<img src="/upload/featured-banner/${featuredBanner.featuredBannerId}/${featuredBanner.bannerLeftBottom2Image}?${todayTime}" width="174" height="180" alt="banner">
											${featuredBanner.bannerLeftBottom2Image}&nbsp;&nbsp;<a href="javascript:;" onclick="addDeleteImage('${featuredBanner.bannerLeftBottom2Image}', '3')" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					
						<table class="board_write_table">
							<colgroup>
								<col style="width: 150px;" />
								<col style="width: 150px;" />
								<col style="*;" />	
							</colgroup>		
							<tr>
								<td class="label" rowspan="3">배너 이미지 (중앙)<br />(338 x 358)</td>
								<td class="label">배너 타이틀</td>
								<td>
									<div>
										<form:input path="bannerCenterTitle" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00413')}</td> <!-- URL -->
								<td>
									<div>
										<form:input path="bannerCenterLink" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<c:set var="hideFlag" value="none"/>	
							<c:if test="${featuredBanner.bannerCenterImage == '' || featuredBanner.bannerCenterImage == null}">
								<c:set var="hideFlag" value=""/>
							</c:if>	
							<tr class="imageUpload_4" style="display: ${hideFlag}">
								<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="bannerImages[3].uploadFile" title="이미지 업로드" />
										<input type="hidden" name="bannerImages[3].code" value="center" />
									</div>
								</td>
							</tr>	
							<c:if test="${featuredBanner.bannerCenterImage != '' && featuredBanner.bannerCenterImage != null}">	 
								<tr class="bannerImage_4">
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div> 
											<img src="/upload/featured-banner/${featuredBanner.featuredBannerId}/${featuredBanner.bannerCenterImage}?${todayTime}" width="338" height="358" alt="banner">
											${featuredBanner.bannerCenterImage}&nbsp;&nbsp;<a href="javascript:;" onclick="addDeleteImage('${featuredBanner.bannerCenterImage}', '4')" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					
						<table class="board_write_table">
							<colgroup>
								<col style="width: 150px;" />
								<col style="width: 150px;" />
								<col style="*;" />	
							</colgroup>		
							<tr>
								<td class="label" rowspan="3">배너 이미지 (우상)<br />(350 x 179)</td>
								<td class="label">배너 타이틀</td>
								<td>
									<div>
										<form:input path="bannerRightTopTitle" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00413')}</td> <!-- URL -->
								<td>
									<div>
										<form:input path="bannerRightTopLink" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<c:set var="hideFlag" value="none"/>
							<c:if test="${featuredBanner.bannerRightTopImage == '' || featuredBanner.bannerRightTopImage == null}">
								<c:set var="hideFlag" value=""/>
							</c:if>
							<tr class="imageUpload_5" style="display: ${hideFlag}">
								<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="bannerImages[4].uploadFile" title="이미지 업로드" />
										<input type="hidden" name="bannerImages[4].code" value="right-top" />
									</div>
								</td>
							</tr>	
							<c:if test="${featuredBanner.bannerRightTopImage != '' && featuredBanner.bannerRightTopImage != null}">	 
								<tr class="bannerImage_5">
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div> 
											<img src="/upload/featured-banner/${featuredBanner.featuredBannerId}/${featuredBanner.bannerRightTopImage}?${todayTime}" width="350" height="179" alt="banner">
											${featuredBanner.bannerRightTopImage}&nbsp;&nbsp;<a href="javascript:;" onclick="addDeleteImage('${featuredBanner.bannerRightTopImage}', '5')" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					
						<table class="board_write_table">
							<colgroup>
								<col style="width: 150px;" />
								<col style="width: 150px;" />
								<col style="*;" />	
							</colgroup>		
							<tr>
								<td class="label" rowspan="3">배너 이미지 (우하1)<br />(175 x 180)</td>
								<td class="label">배너 타이틀</td>
								<td>
									<div>
										<form:input path="bannerRightBottom1Title" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00413')}</td> <!-- URL -->
								<td>
									<div>
										<form:input path="bannerRightBottom1Link" class="nine" title="${op:message('M00413')}" maxlength="100" />
									</div>
								</td>
							</tr>		
							<c:set var="hideFlag" value="none"/>
							<c:if test="${featuredBanner.bannerRightBottom1Image == '' || featuredBanner.bannerRightBottom1Image == null}">
								<c:set var="hideFlag" value=""/>
							</c:if>
							<tr class="imageUpload_6" style="display: ${hideFlag}">
								<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="bannerImages[5].uploadFile" title="이미지 업로드" />
										<input type="hidden" name="bannerImages[5].code" value="right-bottom1" />
									</div>
								</td>
							</tr>	
							<c:if test="${featuredBanner.bannerRightBottom1Image != '' && featuredBanner.bannerRightBottom1Image != null}">	 
								<tr class="bannerImage_6">
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div> 
											<img src="/upload/featured-banner/${featuredBanner.featuredBannerId}/${featuredBanner.bannerRightBottom1Image}?${todayTime}" width="175" height="180" alt="banner">
											${featuredBanner.bannerRightBottom1Image}&nbsp;&nbsp;<a href="javascript:;" onclick="addDeleteImage('${featuredBanner.bannerRightBottom1Image}', '6')" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					
						<table class="board_write_table">
							<colgroup>
								<col style="width: 150px;" />
								<col style="width: 150px;" />
								<col style="*;" />	
							</colgroup>		
							<tr>
								<td class="label" rowspan="3">배너 이미지 (우하2)<br />(174 x 180)</td>
								<td class="label">배너 타이틀</td>
								<td>
									<div>
										<form:input path="bannerRightBottom2Title" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00413')}</td> <!-- URL -->
								<td>
									<div>
										<form:input path="bannerRightBottom2Link" class="nine" title="배너 타이틀" maxlength="100" />
									</div>
								</td>
							</tr>		
							<c:set var="hideFlag" value="none"/>
							<c:if test="${featuredBanner.bannerRightBottom2Image == '' || featuredBanner.bannerRightBottom2Image == null}">
								<c:set var="hideFlag" value=""/>
							</c:if>	
							<tr class="imageUpload_7" style="display: ${hideFlag}">
								<td class="label">${op:message('M00412')}</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="bannerImages[6].uploadFile" title="이미지 업로드" />
										<input type="hidden" name="bannerImages[6].code" value="right-bottom2" />
									</div>
								</td>
							</tr>	
							
							<c:if test="${featuredBanner.bannerRightBottom2Image != '' && featuredBanner.bannerRightBottom2Image != null}">	 
								<tr class="bannerImage_7">
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div> 
											<img src="/upload/featured-banner/${featuredBanner.featuredBannerId}/${featuredBanner.bannerRightBottom2Image}?${todayTime}" width="174" height="180" alt="banner">
											${featuredBanner.bannerRightBottom2Image}&nbsp;&nbsp;<a href="javascript:;" onclick="addDeleteImage('${featuredBanner.bannerRightBottom2Image}', '7')" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					
						<table class="board_write_table">
							<colgroup>
								<col style="width: 150px;" />
								<col style="width: 150px;" />
								<col style="*;" />	
							</colgroup>		
							<tr>
								<td class="label" colspan="2" style="border-right:0">색상</td>
								<td>
									<div style="overflow: visible;">
										<input type="text" name="featuredBannerColor" maxlength="7" style="width: 80px; height: 27px;" value="${featuredBanner.featuredBannerColor}" class="colorpicker" title="색상코드" />
									</div>
								</td>
							</tr>
						</tbody>
					</table>						  
				</div> <!--// board_write E-->
				
				<div class="btn_center">
					<button type="submit" class="btn btn-active"> 등록 </button>
				</div>
			</div>
		</form:form>
		<a href="#" class="popup_close">창 닫기</a>
	</div>
<style>
.board_write_table tr:first-child td:first-child {
	border-right: 1px solid #ececec; 
}

</style>	
<script type="text/javascript">
$(function(){
	$('.colorpicker').minicolors();
});

function addDeleteImage(imageName, imageIndex) {
	if (confirm("파일을 삭제하시겠습니까?")) {
		if ($('.deleteImage').val() == "") {
			$('.deleteImage').val(imageName);
		} else {
			$('.deleteImage').val($('.deleteImage').val() + ", " + imageName );	
		}
		
		$('.imageUpload_' + imageIndex).show();
		$('.bannerImage_' + imageIndex).hide();
	}
}
</script>
