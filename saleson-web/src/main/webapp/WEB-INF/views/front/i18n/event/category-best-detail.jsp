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
			<a href="/event/category-best" >카테고리별 BEST 상품</a>
			<span>${contentName}</span>
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
							<a href="/event/category-best/${category1.categoryId}">${category1.name}</a>
						</c:forEach>
					</dd>
				</dl>
			</li>
			
		</c:forEach>
	</ul>
		
	<div class="rank_tit clear">
		<p>
			${contentName }
		</p>
		<%--<a class="all_item" href="/event/category-best">전체보기</a> --%>
	</div>
			
	
	<div class="item-list thumb rank"> 
		 <ul class="list-inner">
		 	<jsp:include page="../include/item-best-list.jsp"/>
		 </ul>
	</div>
		
	
</div><!-- // inner E -->
	