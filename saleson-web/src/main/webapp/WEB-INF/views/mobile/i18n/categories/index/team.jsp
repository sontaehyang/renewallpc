<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="html_area">
	${categoryEdit.html1}
</div><!--// html_area E-->

	<!-- 최근 체크한 상품 -->
	<jsp:include page="../../include/swiper-today-items.jsp" />

<div id="item_relation" class="swiper">
	<p class="swiper-title">${op:message('M01308')}</p> <!-- 신상품 -->
	<div class="swiper-container">
		<ul class="swiper-wrapper">
			<c:forEach items="${newItemList}" var="list" varStatus="i">
				<li class="swiper-slide">
					<a href="/m${list.link}">
						<img src="${list.imageSrc}" alt="${list.itemName}">
						<p class="title">${list.itemName}</p>	
					</a>
					<p class="price">
						<span>
							<c:if test="${!empty list.itemPrice}">
	 							定価 ${op:numberFormat(list.itemPrice)}<c:if test="${op:numberFormat(list.itemPrice) != list.itemPrice}">円(${shopContext.config.taxDisplayTypeText})</c:if>
	 						</c:if>
						</span>
						<span>卸価 ${list.price}</span>
					</p> 
				</li>
			</c:forEach>
		</ul>	
	</div>			
	<div class="paging"> </div>	
	<!-- 버튼 -->
	<a href="#" class="btn_prev"><img src="/content/mobile/images/common/btn_item_prev.png" alt=""></a>
	<a href="#" class="btn_next"><img src="/content/mobile/images/common/btn_item_next.png" alt=""></a>
	<a href="/m/products/searchResult?cate00team=${categoryTeamCode}" class="btn_more">${op:message('M01451')}</a> <!-- 신상품 더보기 -->
</div><!--//swiper E-->

<div id="item_other" class="swiper">
	<p class="swiper-title">${op:message('M01452')}</p> <!-- 랭킹 -->
	<div class="swiper-container">
		<ul class="swiper-wrapper">
			<c:forEach items="${rankingList}" var="list" varStatus="i">
				<li class="swiper-slide">
					<a href="/m${list.link}">
						<span class="ranking">
							<img src="/content/mobile/images/common/hit.png" alt="hit" />
							<strong>${i.count}</strong>
						</span>
						<img src="${list.imageSrc}" alt="${list.itemName}">
						<p class="title">${list.itemName}</p>	
					</a>
					<p class="price">
						<span>
							<c:if test="${!empty list.itemPrice}">
	 							정가 ${op:numberFormat(list.itemPrice)}<c:if test="${op:numberFormat(list.itemPrice) != list.itemPrice}">원(${shopContext.config.taxDisplayTypeText})</c:if>
	 						</c:if>
						</span>
						<span>판매가 ${list.price}</span>
					</p> 
				</li>
			</c:forEach>
		</ul>	
	</div>			
	<div class="paging"> </div>	

	<!-- 버튼 -->
	<a href="#" class="btn_prev"><img src="/content/mobile/images/common/btn_item_prev.png" alt=""></a>
	<a href="#" class="btn_next"><img src="/content/mobile/images/common/btn_item_next.png" alt=""></a>
	<a href="/m/ranking/cate00team_${categoryCode}" class="btn_more">${op:message('M01453')}</a> <!-- 랭킹 더보기 -->
</div><!--//swiper E--> 


	<!-- 하위 카테고리 -->
	<c:if test="${!empty childCategories}">
		<div class="category_find">
			<p>${op:message('M01450')}</p> <!-- 카테고리에서 찾기 -->
			<ul class="category_depth01">
				
				<c:forEach items="${childCategories}" var="group">
					<li>
						<a href="/m/categories/index/${group.url}">${group.name}</a>
						
						<c:if test="${!empty group.categories}">
 						<ul class="category_depth02">
 						
 							<c:forEach items="${group.categories}" var="childCategory1">
	 							<li><a href="/m/categories/index/${childCategory1.url}">${childCategory1.name}</a>  </li>
	 						</c:forEach>
	 						
 						</ul>	
						</c:if>
					</li>
				</c:forEach>
				
			</ul>
		</div>
	</c:if>
	
	<div class="html_area">
		${categoryEdit.html2}
	</div><!--// html_area E-->
	
<page:javascript>	 
<script type="text/javascript">

// 상품이미지 swipe 
$(function() {
				
	Mobile.swiper({'id' : '#item_relation', 'slidesPerView' : 2});
	Mobile.swiper({'id' : '#item_other', 'slidesPerView' : 2});
	
	// 카테고리에서 찾기 이벤트.
	Shop.findCategory();
	
	
});

</script>
</page:javascript>
