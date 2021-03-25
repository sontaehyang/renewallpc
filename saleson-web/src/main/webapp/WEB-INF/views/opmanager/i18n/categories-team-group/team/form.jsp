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


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

	<h3><span>${op:message('M00579')} <!-- 팀 기본정보 --> </span></h3>
	<form:form modelAttribute="categoriesTeam" method="post">
		<div class="board_write">
			<table class="board_write_table" summary="${op:message('M00579')} <!-- 팀 기본정보 --> ">
				<caption>${op:message('M00579')} <!-- 팀 기본정보 --> </caption>
				<colgroup>
					<col style="width: 150px;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00580')} <!-- 팀명 --> </td>
						<td>
							<div>
								<form:input path="name"  class="required full" title="${op:message('M00580')}" /> <!-- 팀명 --> 
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
					<tr>
						<td class="label">${op:message('M00581')} <!-- 추천상품 --> </td>
						<td>
							<div>
								<p class="mb10">
									<button type="button" id="button_add_relation_item" class="table_btn" onclick="Shop.findItem('related')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
									<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('related')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
								</p>
								
								<ul id=related class="sortable_item_relation">
									<li style="display: none;"></li>
									
									<c:forEach items="${itemList}" var="item" varStatus="i">
										<c:if test="${!empty item.itemId}">
											<li id="related_item_${item.itemId}">
												<input type="hidden" name="relatedItemIds" value="${item.itemId}" />
												<p class="image"><img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
												<p class="title">[${item.itemUserCode}]<br />${item.itemName}</p>
												
												<span class="ordering">${i.count}</span>
												<a href="javascript:Shop.deleteRelationItem('related_item_${item.itemId}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
											</li>
										</c:if>
									</c:forEach>

								</ul>
							</div>
						</td>
					</tr>	 
				</tbody>
			</table>						 
		</div> <!--// board_write E-->
		
		<%-- <div class="email_tb">				
			<h4>| ${op:message('M00583')} <!-- 팀별 설정값 --> </h4>				
			<div class="team_value">						
				<h5>■ ${op:message('M00553')} <!-- 카테고리 페이지에 설정값 --> </h5>					
				<ul>
					<li class="mt20">meta (title / keyword / description)</li>
					<li><form:input path="categoriesSeo.title" class="full" title="사진에 대한 설명" /></li>
					<li><form:input path="categoriesSeo.keywords" class="full" title="사진에 대한 설명" /></li>
					<li><form:textarea path="categoriesSeo.description" cols="20" rows="2"  class="nine" title="내용입력"></form:textarea></li>
				</ul>
				
				<ul>
					<li class="mt20">H1</li>
					<li><form:input path="categoriesSeo.headerContents1" class="full" title="사진에 대한 설명" /></li>
				</ul>
				
				<ul>
					<li class="mt20">H2 / H3</li>
					<li><form:input path="categoriesSeo.headerContents2"  class="full" title="사진에 대한 설명" /></li>
					<li><form:textarea path="categoriesSeo.headerContents3" cols="30" rows="2"  class="w90" title="내용입력"></form:textarea></li>
				</ul>
				
				<ul>
					<li class="mt20">themaword</li>
					<li><form:input path="categoriesSeo.themawordTitle" class="full" title="사진에 대한 설명" /></li>
					<li><form:textarea path="categoriesSeo.themawordDescription" cols="30" rows="4" class="w90" title="내용입력"></form:textarea></li>
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
				
				
				<h5>■ ${op:message('M00558')} <!-- 리뷰 페이지에 설정값 --> </h5>					
				<div style="width: 98%;">					
					<ul>
						<li class="mt20">meta (title / keyword / description)</li>
						<li><form:input path="reviewSeo.title" class="nine" title="사진에 대한 설명" /> <button type="button" class="table_btn seo_copy" id="rev_title"><span>${op:message('M00555')} <!-- 만들기 --> </span></button></li>
						<li><form:input path="reviewSeo.keywords" class="nine" title="사진에 대한 설명" /> <button type="button" class="table_btn seo_copy" id="rev_keyword"><span>${op:message('M00555')} <!-- 만들기 --> </span></button></li>
						<li><form:textarea path="reviewSeo.description" cols="20" rows="2"  class="nine" title="내용입력" /></li>
					</ul>					
				</div>
				
				<div style="width: 98%;">						
					<ul>
						<li class="mt20">H1</li>
						<li><form:input path="reviewSeo.headerContents1" class="nine" title="사진에 대한 설명" /> <button type="button" class="table_btn seo_copy" id="rev_headercontents"><span>${op:message('M00555')} <!-- 만들기 --> </span></button></li>
					</ul>					
				</div>
				
				<div style="width: 98%;">					
					<ul>
						<li class="mt20">themaword</li>
						<li><form:input path="reviewSeo.themawordTitle" class="nine" title="사진에 대한 설명" /> <button type="button" class="table_btn seo_copy" id="rev_themawordtitle"><span>${op:message('M00555')} <!-- 만들기 --> </span></button></li>
						<li><form:textarea path="reviewSeo.themawordDescription" cols="30" rows="4" class="nine" title="내용입력" /></li>
					</ul>					
				</div>
			
			</div> <!-- // team_value -->
			
				
		</div> --%> <!-- //  email_tb -->
		
		<div class="tex_c mt20">
			<button type="submit" class="btn btn-active">${op:message('M00088')} <!-- 등록 --> </button>
			<button type="button" class="btn btn-default" onclick="cancle();">${op:message('M00037')} <!-- 취소 --> </button>
		</div>
	
	</form:form>
	
	<script type="text/javascript">
		$(function(){
			
			$(".seo_copy").on("click",function(){
				seoCopy($(this));
			});
			
			if($("input[name='code']").val() != ''){
				$("input[name='code']").attr("readonly",true);
			}
			
			$(".btn btn-default").on("click",function(){
				location.href="/opmanager/categories-team-group/list";
			});
			
			$("#categoriesTeam").validator(function() {
				
			});
			 
			$( window ).scroll(function() {
				
				setHeight();
			});
			
			// 관련상품 드레그
		    $(".sortable_item_relation").sortable({
		        placeholder: "sortable_item_relation_placeholder"
		    });
			
			
		});
		
		function cancle() {
			location.replace('/opmanager/categories-team-group/list');
		}
	</script>
	
	