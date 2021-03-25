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

	<h3><span>상품진열관리</span></h3>
			
	<div class="cnt_area mt30">		
		<div id="tab_nav" class="tab_nav col6">
			<ul>
				<li><a href="/opmanager/categories-edit/index?type=main">메인화면</a></li>
				<li class="on"><a href="/opmanager/categories-edit/index?type=categories">카테고리 화면</a></li>
				<li><a href="/opmanager/categories-edit/index?type=etc">${op:message('M00172')}</a></li> <!-- 기타 -->
			</ul>
		</div>
		
		<!-- 메인화면 끝-->
		
		<!-- 카테고리화면 시작-->
		<div id="activity4" class="tab_content on">
			<h4>| ${op:message('M01081')} <!-- 카테고리 구분 --></h4>
			<div class="category_wrap mb20">			
 				
				<div class="category_wrap mb20">					
					<select id="categoryGroupId" class="category multiple" size="12">
						<option value="0">= ${op:message('M00076')} =</option> <!-- 팀/그룹 -->
						<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
							<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
								<optgroup label="${categoriesTeam.name}">
								<c:forEach items="${categoriesTeam.categoriesGroupList}" var="categoriesGroup">
									<c:if test="${categoriesGroup.categoryGroupFlag == 'Y'}">
										<option value="${categoriesGroup.categoryGroupId}">${categoriesGroup.groupName}</option>
									</c:if>
								</c:forEach>
								</optgroup>
							</c:if>
						</c:forEach>
						
					</select> <!-- // category_step -->
					
					<select id="categoryClass1" class="category multiple"  size="12">
						<option value="">= 1${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<select id="categoryClass2" class="category multiple"  size="12">
						<option value="">= 2${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<select id="categoryClass3" class="category multiple"  size="12">
						<option value="">= 3${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<select id="categoryClass4" class="category multiple"  size="12">
						<option value="">= 4${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<button type="button" class="btn btn-dark-gray btn-sm" id="selected" style="float:right;">${op:message('M00431')}</button> <!-- 선택 -->
				</div>
			</div>
		</div>	 
		
		<div class="screen_main">						
		</div>
		
		<!-- 카테고리화면 끝-->
		
	</div><!--//cnt_area E-->
	
	<!-- <div class="tex_c mt20">
		<a href="#" class="btn btn-active">저장</a>
	</div>
	 -->
<script type="text/javascript">
	$(function(){
		ShopEventHandler.categorySelectboxChagneEvent(); 
		
		$("body").on("click",".edit_popup",function(){
			var popupSize = $(this).attr("rel").split("*");
			Common.popup($(this).attr("href"),"promotion-banner",popupSize[0],popupSize[1]);
			return false;
		});
		
		$("#selected").on("click",function(){
			categoryLayout();
		}); 
		
		
	});
	
	function categoryLayout(){
		var categoryId = "";
		for ( var i =1; i <= 4;i++ ){
			if( $("#categoryClass"+i+" option:selected").attr("rel") != null ){
				categoryId = $("#categoryClass"+i+" option:selected").attr("rel");
			}
		}
		
		$.post("/opmanager/categories-edit/category-layout/"+categoryId , "" , function(resp){
			$(".screen_main").html(resp);	
			setHeight();
		},'html');
	}

</script>