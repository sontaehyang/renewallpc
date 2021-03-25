<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>			
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<style>
#lnb.main > div {display: block !important;}
</style>				

<page:javascript>
<script type="text/javascript">
$(function() {
	var $group = $('#lnb .depth_02');
	
	$group.find('> li > a').on('mouseenter focusin', function() {
		$(this).parent().addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).parent().removeClass('over');
		
	}).on('click', function(e) {
		e.preventDefault();
		
	});



	$group.find('.menu_group > .depth_04').on('mouseenter focusin', function() {
		$(this).closest('.menu_group').addClass('over');
		$(this).closest('.menu_category1').addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).closest('.menu_group').removeClass('over');
		$(this).closest('.menu_category1').removeClass('over');
	});

	$group.find('.menu_group > .depth_04 > li > a').on('mouseenter focusin', function() {
		$(this).parent().addClass('over');
		$(this).closest('.menu_group').addClass('over');
		$(this).closest('.menu_category1').addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).parent().removeClass('over');
		$(this).closest('.menu_group').removeClass('over');
		$(this).closest('.menu_category1').removeClass('over');
	});


	$group.find('.menu_group > .depth_04 > li > .depth_05').on('mouseenter focusin', function() {
		$(this).parent().addClass('over');
		$(this).closest('.menu_group').addClass('over');
		$(this).closest('.menu_category1').addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).parent().removeClass('over');
		$(this).closest('.menu_group').removeClass('over');
		$(this).closest('.menu_category1').removeClass('over');
	});


	// 1차 카테고리 오버
	$group.find('.menu_category1 > a').on('mouseenter focusin', function() {
		$(this).parent().addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).parent().removeClass('over');
	});
	
	$group.find('.menu_category1 > .depth_04').on('mouseenter focusin', function() {
		$(this).closest('.menu_category1').addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).closest('.menu_category1').removeClass('over');
	});

	$group.find('.menu_category1 > .depth_04 > li > a').on('mouseenter focusin', function() {
		$(this).parent().addClass('over');
		$(this).closest('.menu_category1').addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).parent().removeClass('over');
		$(this).closest('.menu_category1').removeClass('over');
	});


	$group.find('.menu_category1 > .depth_04 > li > .depth_05').on('mouseenter focusin', function() {
		$(this).parent().addClass('over');
		$(this).closest('.menu_category1').addClass('over');
		
	}).on('mouseleave focusout', function() {
		$(this).parent().removeClass('over');
		$(this).closest('.menu_category1').removeClass('over');
	});
	
});

</script>	
</page:javascript>				
<!-- 팀 -->
					<jsp:include page="saleson_all_categories.jsp" />
					<div id="lnb" class="${lnbType}">
					
						<c:forEach items="${shopContext.lnbCategories}" var="team" varStatus="i">	
							<c:if test="${team.url != 'sale_outlets'}">
								<div class="category_team lnb_${team.url}" ${team.url == categoryTeamCode ? 'style="display:block"' : ''}>
									<span>
										<img src="/content/images/common/${team.url}.gif"  alt="${team.name}" />
									</span>
									<ul class="depth_02" >
										<c:forEach items="${team.groups}" var="group" varStatus="j">
										<li class="menu_group">
											<a href="javascript:;" class="${group.url == categoryGroupCode ? 'on' : ''}"><span>${group.name}</span></a>	
									
											<ul class="depth_03" ${group.url == categoryGroupCode ? '' : 'style="display:none"'}>
												<c:forEach items="${group.categories}" var="category1" varStatus="k">
													<li class="menu_category1">
														<a href="${category1.link}" class="${category1.url == breadcrumbs[0].categoryUrl1 ? 'on' : ''}"><span>${category1.name}</span></a>
														
														<!-- 2차/3차 카테고리 레이어 -->
														<ul class="depth_04" >
															<c:forEach items="${category1.childCategories}" var="category2" varStatus="k">
															<li>
																<a href="${category2.link}" class=""><span>${category2.name}</span></a>
														
																<c:if test="${!empty category2.childCategories}">
																	<ul class="depth_05">
																		<c:forEach items="${category2.childCategories}" var="category3" varStatus="k">
																			<li><a href="${category3.link}" class=""><span>${category3.name}</span></a></li>
																		</c:forEach>
																	</ul>
																</c:if>
															</li> 
															</c:forEach>
														</ul>
													</li>
												</c:forEach>
											</ul><!--//3depth E-->
											
											<!-- 1차/2차 카테고리 레이어 -->
											<ul class="depth_04" >
												<c:forEach items="${group.categories}" var="category1" varStatus="k">
												<li>
													<a href="${category1.link}" class=""><span>${category1.name}</span></a>	
											
													<c:if test="${!empty category1.childCategories}">
														<ul class="depth_05">
															<c:forEach items="${category1.childCategories}" var="category2" varStatus="k">
																<li><a href="${category2.link}" class=""><span>${category2.name}</span></a></li>
															</c:forEach>
														</ul>
													</c:if>
												</li> 
												</c:forEach>
											</ul>
									
										</li> 
										</c:forEach>
									</ul>
									
									
								</div>
							</c:if>
						</c:forEach>
						
					</div> 	<!--//lnb E-->
					