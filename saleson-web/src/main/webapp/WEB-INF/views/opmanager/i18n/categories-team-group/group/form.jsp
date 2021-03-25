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
<style type="text/css">
	.categories_color1,	.categories_color2{ background: #ffffcc; }
	.category_set {margin-top: 10px;}
	.category_set h4 {color: #fff; margin-left: 0;}
	.category_set .category_list {margin-left: 0;}
</style>
	
	
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
	
	<h3><span>${op:message('M00570')} <!-- 그룹 기본정보 --> </span></h3>
	<form:form modelAttribute="categoriesGroup" method="post">
	<div class="board_write">
		<table class="board_write_table" summary="${op:message('M00570')} <!-- 그룹 기본정보 --> ">
			<caption>${op:message('M00570')} <!-- 그룹 기본정보 --> </caption>
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<tr>
					<td class="label">${op:message('M00571')} <!-- 그룹명 --> </td>
					<td>
						<div>
							<form:input path="name" class="required full" title="${op:message('M00571')}" /><!-- 그룹명 -->
					 	</div>
					</td>
				</tr>
			 	 <tr>
					<td class="label">${op:message('M00572')} <!-- 팀 선택 --> </td>
					<td>
						<div>
							<form:select path="categoryTeamId" items="${categoriesTeamSelectBox}" class="required full" title="${op:message('M00572')}" ></form:select>
					 	</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M00191')}</td> <!-- 공개유무 -->
					<td>
						<div>
							<p>
								<form:radiobutton path="categoryGroupFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 -->
								<form:radiobutton path="categoryGroupFlag" value="N" label="${op:message('M00097')}" /> <!-- 비공개 -->
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M01604')} <!-- 접속권한 --></td>
					<td>
						<div>
							<form:radiobutton path="accessType" value="1" checked="checked" label="${op:message('M00497')}" /> <!-- 제한없음 -->
							<form:radiobutton path="accessType" value="2" label="${op:message('M01605')}" />  <!-- 회원만 -->
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">URL</td>
					<td>
						<div>
							<form:input path="code" class="required full" title="URL" />
					 	</div>
					</td>
				</tr>				 
			</tbody>
		</table>						 
	</div> <!--// board_write E-->
	
	
	
	<div class="email_tb">				
		<h4>| ${op:message('M00573')} <!-- 1차 카테고리 설정 --> </h4>				
		<div class="category_set">											
			<div class="category_short">						
				<h4>${op:message('M00574')} <!-- 전체 1차 카테고리 목록 --> </h4>						
				<div class="category_list" style="width:97.5%; overflow: scroll; height: 435px;">							
					<div class="bundle_two">			
						<c:forEach items="${categoriesGroupInList}" var="list">
						<c:if test="${list.categoryGroupId != 0 }">
							<h5>[${list.name }]</h5>
						</c:if> 
						<c:if test="${list.categoryGroupId == 0}">
							<h5>[미등록]</h5>
						</c:if>
							<ul>
							<c:forEach items="${list.categoryList}" var="list2">
								<li class="categories_go" id="${list2.categoryCode}"><a href="javascript:;">- ${list2.categoryName }</a></li>
							</c:forEach>
							</ul>
						</c:forEach>
					</div>				
				</div> <!-- // category_list -->						
			</div> <!-- // category_short -->
			
			
			<div class="category_btn">
				<a href="javascript:;" id="add"><img src="/content/opmanager/images/btn/more_btn.png" alt="추가 버튼"></a>
			</div> <!-- // category_btn -->
							
			<div class="category_short">						
				<h4>${op:message('M00575')} <!-- 등록 할 카테고리 목록 --> </h4>						
				<div class="category_list h370" style="width:97.5%; margin-bottom: 5px">							
					<div class="bundle_two">															
						<ul id="addCategory">
							<c:forEach items="${categoriesList}" var="list">
								<li class="categories_value">
									- ${list.categoryName }
									<input type="hidden" name="categoryCodes" value="${list.categoryCode}" />
								</li>
							</c:forEach>
						</ul>							
					</div> <!-- // bundle_two -->							
				</div> <!-- // category_list -->
				
				<div style="margin-left: -6px; margin-top: 2px; margin-right:10px">
					<div class="btn_left ml6">
						<button type="button" id="categoryAllDelete" class="btn ctrl_btn w98"><span>${op:message('M00411')} <!-- 전체 삭제 --> </span></button>
					</div>
					<div class="btn_right ml6">
						<button type="button" id="categorySelectDelete" class="btn ctrl_btn w98"><span>${op:message('M00576')} <!-- 선택 삭제 --> </span></button>
					</div>
				</div>												
			</div> <!-- // category_short -->										
		</div> <!-- // category_set -->
		
		<div class="board_guide ml10">
			<p class="tip">Tip</p>
			<p class="tip">${op:message('M00577')} <!-- - 타 그룹에 이미 속해 있는 카테고리도 현재 카테고리로 이동하실 수 있습니다. 단, 기존 그룹에서는 자동으로 소속이 해지됩니다. --> </p>
		</div>								
	</div> <!-- //  email_tb -->
				
	<%-- <div class="email_tb mt20" style="clear:both;">				
		<h4>| ${op:message('M00578')} <!-- 그룹별 설정 값 --> </h4>				
		<div class="team_value">						
			<h5>■ ${op:message('M00553')} <!-- 카테고리 페이지에 설정값 --> </h5>					
			<ul>
				<li class="mt20">meta (title / keyword / description)</li>
				<li><form:input path="categoriesSeo.title" class="full" title="카테고리 SEO title" /></li>
				<li><form:input path="categoriesSeo.keywords" class="full" title="카테고리 SEO keyword" /></li>
				<li><form:input path="categoriesSeo.description" class="full" title="카테고리 SEO description" /></li>
			</ul>
			
			<ul>
				<li class="mt20">H1</li>
				<li><form:input path="categoriesSeo.headerContents1" class="full" title="카테고리 SEO H1" /></li>
			</ul>
			
			<ul>
				<li class="mt20">H2 / H3</li>
				<li><form:input path="categoriesSeo.headerContents2" class="full" title="카테고리 SEO H2" /></li>
				<li><form:textarea path="categoriesSeo.headerContents3" cols="30" rows="2" class="w90" title="카테고리 SEO H3" /></li>
			</ul>
			
			<ul>
				<li class="mt20">themaword</li>
				<li><form:input path="categoriesSeo.themawordTitle" class="full" title="카테고리 SEO H2" /></li>
				<li><form:textarea path="categoriesSeo.themawordDescription" cols="30" rows="4"  class="w90" title="내용입력" /></li>
			</ul>
			 
			<h5>■ ${op:message('M00554')} <!-- 랭킹 페이지에 설정값 --> </h5>
			
			<div style="width: 98%;">
			
				<ul>
					<li class="mt20">meta (title / keyword / description)</li>
					<li><form:input path="rankSeo.title" class="nine" title="랭킹 SEO title" /> <button type="button" class="table_btn seo_copy" id="rank_title"><span>${op:message('M00555')} <!-- 만들기 --></span></button></li>
					<li><form:input path="rankSeo.keywords" class="nine" title="랭킹 SEO keyword" /> <button type="button" class="table_btn seo_copy" id="rank_keyword"><span>${op:message('M00555')} <!-- 만들기 --></span></button></li>
					<li><form:textarea path="rankSeo.description" cols="20" rows="2"  class="nine" title="랭킹 SEO description" /></li>
				</ul>
			
			</div>
			
			<div style="width: 98%;">
				
				<ul>
					<li class="mt20">H1</li>
					<li><form:input path="rankSeo.headerContents1" class="nine" title="랭킹 SEO keyword" /> <button type="button" class="table_btn seo_copy" id="rank_headercontents"><span>${op:message('M00555')} <!-- 만들기 --></span></button></li>
				</ul>
			
			</div>
			
			<div style="width: 98%;">					
				<ul>
					<li class="mt20">themaword</li>
					<li><form:input path="rankSeo.themawordTitle" class="nine" title="랭킹 SEO themaword title" /> <button type="button" class="table_btn seo_copy" id="rank_themawordtitle"><span>${op:message('M00555')} <!-- 만들기 --></span></button></li>
					<li><form:textarea path="rankSeo.themawordDescription" class="nine" title="랭킹 SEO themaword description" /></li>
				</ul>					
			</div>
			
		</div> <!-- // team_value -->
			
	</div> --%> <!-- //  email_tb -->
	
	<div class="tex_c mt20">
		<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
		<c:if test="${categoriesGroup.categoryGroupId > 0}">
		<a href="javascript:changeGroupToCategory(${categoriesGroup.categoryGroupId})" class="btn btn-default" style="width: 150px;">${op:message('M01598')}</a> <!-- 1차 카테고리로 변경 -->
		<a href="javascript:deleteCategoryGroup(${categoriesGroup.categoryGroupId})" class="btn btn-default">${op:message('M00074')}</a> <!-- 삭제 -->
		</c:if>
		<a href="/opmanager/categories-team-group/list" class="btn btn-default" style="">${op:message('M00037')} <!-- 취소 --> </a>
	</div>		
	</form:form>
	
<script type="text/javascript">
var categoryGroupId = '${categoriesGroup.categoryGroupId}';
var patterns = /^[a-zA-Z0-9_-]+$/;
$(function(){
	
	if($("input[name='code']").val() != ''){
		$("input[name='code']").attr("readonly",true);
	}
	
	fn_categoriesAdd();
	fn_categoriesDelete();
	
	$("#categoriesGroup").validator(function() {
		// 등록인 경우.
		if (categoryGroupId == 0) {
			var $code = $('#code');
			
			if( $code.val() == null || $.trim($code.val()) == '' ) {
				alert(Message.get("M01331")); // 카테고리 URL 값을 입력해주세요
				$code.focus();
				return false;
			}
			
			var value = $.trim($code.val());
			$code.val(value);
			
			if(!patterns.test(value)){
				alert(Message.get("M01332")); // 영문, 숫자, 하이픈, 언더바 만 입력가능합니다.
				$code.focus();
				return false;
			}
			

			
			var isAvailableCode = false;
			
			$.ajaxSetup({
				async: false
			});
			var param = {
				"categoryUrl" : value
			};
			$.post("/opmanager/categories/code-check",param,function(resp){
				Common.responseHandler(resp, function(){
					isAvailableCode = true;
				},function(){
					alert(resp.errorMessage);
					isAvailableCode = false;
				});
			});
			
			if (!isAvailableCode) {
				$code.focus();
				return false;
			}
		}
		
		/*
		if( $("input[name='categoryCodes']").size() <= 0 ){
			alert(Message.get("M01341"));	// 1차카테고리를 선택 하십시오. 
			$("#add").focus();
			return false;
		}
		*/
	});
	
	$(".seo_copy").on("click",function(){
		seoCopy($(this));
	});
});

//카테고리 추가 
function fn_categoriesAdd(){
	
	$(".categories_go").on("click",{
		toClass : "categories_color1"
	},backgroundChange);
	
	$(".email_tb").on("click",".categories_value",{
		toClass : "categories_color2"
	},backgroundChange);
	
	$("#add").on("click",{
		add : ".categories_color1",
		check : "input[name='categoryCodes']",
		from : "#addCategory",
		addValue : "<li class='categories_value'><a href='javacript:;'>::text::</a><input type='hidden' name='categoryCodes' value='::value::' /></li>"
	}, categortAdd);
	
}

function backgroundChange(event){
	if(!$(this).hasClass(event.data.toClass)){
		$(this).addClass(event.data.toClass);
	}else{
		$(this).removeClass(event.data.toClass);
	}
}

function categortAdd(event){
	var categoriesLi = "";
	$(event.data.add).each(function(){
		var flag = true;
		var id = $(this).attr("id");
		$(event.data.check).each(function(){
			if(id == $(this).val()){
				flag = false;
			}
		});
		
		if(flag){
			categoriesLi += event.data.addValue.replaceAll("::text::",$(this).text()).replaceAll("::value::",$(this).attr("id"));
		}
	});
	
	$(event.data.from).append(categoriesLi);
}

//카테고리 삭제
function fn_categoriesDelete(){
	$("#categoryAllDelete").on("click",function(){
		$(".categories_value").remove();
	});
	
	$("#categorySelectDelete").on("click",function(){
		$(".categories_color2").each(function(){
			$(this).remove();
		});
	});
}

// 1차 카테고리로 변경하기.
function changeGroupToCategory(categoryGroupId) {
	Common.popup('/opmanager/categories-team-group/group/change-group-to-category/' + categoryGroupId, 'change-group-to-category', 500, 400);
}


// 그룹삭제 
function deleteCategoryGroup(categoryGroupId){
	
	var param = {
		'categoryGroupId': categoryGroupId
	};
	
	var message = Message.get("M00196");	// 삭제하시겠습니까?
	if (confirm(message)) {
		$.post("/opmanager/categories-team-group/group/delete", param, function(resp){
			Common.responseHandler(resp, function(){
				location.replace('/opmanager/categories-team-group/list');
			});
		});
	}
}
</script>