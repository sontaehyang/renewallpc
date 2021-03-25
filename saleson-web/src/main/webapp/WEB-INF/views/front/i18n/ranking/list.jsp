<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="content_top"> 
	<div class="breadcrumbs">
		<a href="/" class="home"><span class="hide">home</span></a>
		<span>랭킹목록</span> 
	</div>
</div><!--//content_top E-->

	<div id="sub_contents" class="rank_list">  
		<h2>랭킹목록</h2> 
		<div class="rank_tab">
			<ul>
				<li ${esthetic}><a href="/ranking/cate00team_esthetic">에스테틱</a></li>
				<li ${nail}><a href="/ranking/cate00team_nail">네일</a></li>
				<li ${matsuge_extension}><a href="/ranking/cate00team_matsuge_extension">속눈썹</a></li>
				<li ${hair}><a href="/ranking/cate00team_hair">헤어</a></li> 
			</ul>
		</div> 
		
		<div class="tabs tabs-4">
			<ul>
				<li><a href="/ranking/cate00team_esthetic">에스테틱</a></li>
				<li><a href="/ranking/cate00team_nail">네일</a></li>
				<li><a href="/ranking/cate00team_matsuge_extension">속눈썹</a></li>
				<li><a href="/ranking/cate00team_hair">헤어</a></li>  
			</ul>
		</div> 
		
		<c:forEach items="${groupCategoryList}" var="list">
			<h3>카테고리별 히트 상품 순위보기</h3>
			<div class="category_ranking esthetic">
				<table cellpadding="0" cellspacing="0">
					<caption>카테고리별 히트 상품</caption>
					<colgroup>
						<col style="176px;" />
						<col style="auto;" />
					</colgroup>
					<tbody>
						<c:forEach items="${list.groups}" var="list2">
							<tr>
								<th>${list2.name}</th>
								<td>
									<ul>
										<c:forEach items="${list2.categories}" var="list3">
											<li><a href="/ranking/${list3.url}_${code}">${list3.name }</a></li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:forEach>
		
		
		
		<!-- 에스테틱 화장품 -->
		<div class="ranking_wrap"> 
	 		<h2 class="rank">${name}<span></h2>
			<div class="ranking_content">
				<ul>
					<c:forEach items="${rankingList}" var="list" varStatus="i">
					<li class="division5-${i.count % 5}">
						<c:if test="${i.count <= 10}">
							<p class="best_rank"><span>${i.count < 10 ? '0' : ''}${i.count}</span></p>
						</c:if>
						<a href="${list.link}"><img src="${list.imageSrc}" width="223" height="223" alt="photo"></a>						                		
						<p class="item_name"><a href="${list.link}" ${list.noFollow}>${list.itemName}</a></p>
						<div class="price_zone">
							<p class="last">
								<span class="sale_price">${list.price}</span>
							</p>
						</div>
					</li>
					
					</c:forEach>
									                
				</ul>
			</div>	
		</div>
		 
	</div>	 		

	