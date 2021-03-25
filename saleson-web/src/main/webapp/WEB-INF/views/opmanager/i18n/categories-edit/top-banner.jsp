<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<!-- MiniColors -->
<script src="/content/modules/jquery/minicolors/jquery.minicolors.js"></script>
<link rel="stylesheet" href="/content/modules/jquery/minicolors/jquery.minicolors.css" />


	<div class="popup_wrap">
		<h1 class="popup_title">TOP/우측 공통배너영역 팝업</h1>	
		
		<form:form modelAttribute="config" method="post" enctype="multipart/form-data">
			<c:set var="hideTop" value="display: none;" />
			<c:set var="hideRight" value="" />
			<c:if test="${position == 'top'}">
				<c:set var="hideTop" value="" />
				<c:set var="hideRight" value="display: none;" />
			</c:if>
			
			<!-- 본문 -->
			<div class="popup_contents">
				<div class="board_write" style="overflow: visible; ${hideTop}">
					<h2>¶ TOP 공통 배너관리</h2>
					<table class="board_write_table" summary="${op:message('M00414')}">
						<caption>${op:message('M00414')}</caption>
						<colgroup>
							<col style="width: 150px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">${op:message('M00413')}</td><!-- URL -->
								<td>
									<div>
										<form:input type="text" path="topBannerLink" class="nine" title="${op:message('M00413')}" />
									</div>
								</td>
							</tr>
						 	 
							<tr>
								<td class="label">${op:message('M00412')} <br>(1080 x 100)</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="imageFileTop" title="${op:message('M00417')}"/> <!-- 이미지 파일 --> 
									</div>
								</td>
							</tr>
							<c:if test="${!empty config.topBannerImage}">		 
								<tr>
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div>
											<img src="${config.topBannerImageSrc}" alt="${op:message('M00659')}" width="580px" height="76px"/> <br/> <!-- 상품이미지 --> 
										</div>
										<div>
											${config.topBannerImage}&nbsp;&nbsp;<a href="/opmanager/categories-edit/top-banner/imgDelete/topBanner/top" class="table_btn">${op:message('M01511')}</a><!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
							
							<tr>
								<td class="label">색상</td> 
								<td>
									<div style="overflow: visible;">
										<input type="text" name="topBannerColor" maxlength="7" style="height: 27px;" value="${config.topBannerColor}" class="colorpicker" title="색상코드" /> 
									</div>
								</td>
							</tr>
							
						</tbody>
					</table>						  
				</div> <!--// board_write E-->
				
				
				<!-- 본문 -->
				<div class="board_write" style="overflow: visible; ${hideRight}">
					<h2>¶ 우측 공통 배너관리</h2>
					<table class="board_write_table" summary="${op:message('M00414')}">
						<caption>${op:message('M00414')}</caption>
						<colgroup>
							<col style="width: 150px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">${op:message('M00413')}</td><!-- URL -->
								<td>
									<div>
										<form:input type="text" path="rightBannerLink" class="nine" title="${op:message('M00413')}" />
									</div>
								</td>
							</tr>
						 	 
							<tr>
								<td class="label">${op:message('M00412')} <br>(200 x 80)</td> <!-- 이미지 업로드 --> 
								<td>
									<div>
										<input type="file" name="imageFileRight" title="${op:message('M00417')}"/> <!-- 이미지 파일 --> 
									</div>
								</td>
							</tr>
							<c:if test="${!empty config.rightBannerImage}">		 
								<tr>
									<td class="label">${op:message('M00416')}</td> <!-- 현재 이미지 -->
									<td>
										<div>
											<img src="${config.rightBannerImageSrc}" alt="${op:message('M00659')}" width="580px" height="76px"/> <br/> <!-- 상품이미지 --> 
										</div>
										<div>
											${config.rightBannerImage}&nbsp;&nbsp;<a href="/opmanager/categories-edit/top-banner/imgDelete/rightBanner/right" class="table_btn">${op:message('M01511')}</a> <!-- 파일 삭제 -->
										</div>
									</td>
								</tr>
							</c:if>
							
						</tbody>
					</table>						  
				</div> <!--// board_write E-->
				
				<div class="btn_center">
					<button type="submit" class="btn btn-active"> 등록 </button> <!-- 수정 -->
					<!--  
					<a href="javascript:deleteCheck()" class="btn btn-default">${op:message('M00074')}</a><!-- 삭제
					 --> 
				</div>
			</div>
		</form:form>
		<a href="#" class="popup_close">창 닫기</a>
	</div>
		
<script type="text/javascript">
$(function() {
	$('.colorpicker').minicolors();
	// validator
	try{
	$('#config').validator();
	} catch(e) {
		
		alert(e.message);
	}
});

function deleteCheck() {
	if(confirm(Message.get("M00537"))) {	// 배너를 삭제하시겠습니까? 
		location.replace("/opmanager/categories-edit/top-banner/delete");  
	} else {
		return;
	}
}
/*
function findZipcode(){

	 var params = {
		'post1' : $("#post1").val(),
		'post2' : $("#post2").val()
	 };
	
	$.post('/zipcode/find-address', params, function(resp) {
		if (resp.zipcode) {
			$("#dodobuhyun").val(resp.zipcode.c4);
			$("#address").val(resp.zipcode.c5);
			$("#addressDetail").val(resp.zipcode.c6);
		} else {
			alert("${op:message('M00128')}");
			$("#dodobuhyun").val("");
			$("#address").val("");
			$("#addressDetail").val("");
		}
	});
}*/

</script>		