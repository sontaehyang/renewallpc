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
	
<div class="inner">
	
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>카테고리별 BEST 상품</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<h2 class="page_title">카테고리별 BEST 상품</h2>
	
	<ul class="category_rank">
		<c:forEach items="${shopContext.shopCategoryGroups}" var="group">
			
			<li>
				<dl class="clear">
					<dt><a href="/event/category-best/${group.url}">${group.name}</a></dt>
					<dd>
						<c:forEach items="${group.categories}" var="category1">
							<a href="/event/category-best/${category1.url}">${category1.name}</a>
						</c:forEach>
					</dd>
				</dl>
			</li>
			
		</c:forEach>
	</ul>
	
	<c:forEach items="${shopContext.shopCategoryGroups}" var="group" varStatus="k">
		
		<%-- 카테고리 상품 --%>
		<c:set var="items" value="${bestItems[group.url]}" scope="request"/>
		
		<div class="rank_tit clear">
			<p>${group.name}</p>
			<a class="all_item" href="/event/category-best/${group.url}">상세보기</a>
		</div>
		
		<c:if test='${not empty items}'>
		<div class="item-list thumb rank"> 
			 <ul class="list-inner">
			 	<jsp:include page="../include/item-best-list.jsp"/>
			 </ul>
		</div>
		</c:if>
			
	</c:forEach>
	
</div><!-- // inner E -->
	