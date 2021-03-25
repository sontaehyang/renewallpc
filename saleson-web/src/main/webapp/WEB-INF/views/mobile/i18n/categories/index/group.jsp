<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


		<!-- con : s -->
		<div class="con">
			<div class="title">
				<h2>${categoryGroupName}</h2>
				<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
			</div>
			<!-- //title -->
			
			<div class="product_wrap">
				<c:if test="${!empty childCategories}">
					<div class="category_tab">
						<ul class="category_list">
							<c:forEach items="${childCategories}" var="category1">
								<li><a href="/m/categories/index/${category1.url}">${category1.name}</a></li>
								<%--
									<c:if test="${!empty category1.childCategories}">
				 						<ul class="category_depth02">
				 							<c:forEach items="${category1.childCategories}" var="childCategory2">
					 							<li><a href="/m/categories/index/${childCategory2.url}">${childCategory2.name}</a>  </li>
					 						</c:forEach>
				 						</ul>	
									</c:if>
								 --%>
							</c:forEach>
						</ul>
						<div class="category_more_btn">
							<a href="#"><span>더보기</span></a>
						</div>
					</div>
					<!-- //category_tab -->
				</c:if>
				
				<form:form modelAttribute="itemParam" method="post">
					<form:hidden path="orderBy" />
					<form:hidden path="sort" />
					<form:hidden path="listType" />
					<form:hidden path="categoryGroupCode" />
					<c:set var="sortTxt"></c:set>
						<c:choose>
							<c:when test="${itemParam.orderBy == 'HITS'}">
								<c:set var="sortTxt">신상품순</c:set>
							</c:when>
							<c:when test="${itemParam.orderBy == 'SALE_PRICE' && itemParam.sort =='DESC'}">
								<c:set var="sortTxt">높은가격순</c:set>
							</c:when>
							<c:when test="${itemParam.orderBy == 'SALE_PRICE' && itemParam.sort =='ASC'}">
								<c:set var="sortTxt">낮은가격순</c:set>
							</c:when>
							<c:otherwise>
								<c:set var="sortTxt">인기순</c:set>
							</c:otherwise>
						</c:choose>				
					<!-- 상품 리스트 시작 -->
						<div class="list_sort_area">
							<p>총 <span>${op:numberFormat(pagination.totalItems)}</span>개 상품</p>
							<div class="styled_select">
								<button type="button" class="filter_sort">${sortTxt}</button>
								<div class="filter_list_sort">
									<span class="arr"></span>
									<ul class="filter_list op-ordering-list">
										<li>
											<a href="#">인기순</a>
											<input type="hidden" class="op-sort" value=""/>
										</li>
										<li>
											<a href="#">신상품순</a>
											<input type="hidden" class="op-sort" value="HITS__DESC"/>
										</li>
										<li>
											<a href="#">높은가격순</a>
											<input type="hidden" class="op-sort" value="SALE_PRICE__DESC"/>
										</li>
										<li>
											<a href="#">낮은가격순</a>
											<input type="hidden" class="op-sort" value="SALE_PRICE__ASC"/>
										</li>
									</ul>
								</div>
								<div id="dimmedLayer" class="layer_overlay_transparent"></div>
							</div>
						</div>
				</form:form>
				
				<ul id="op-list-data" class="product_list">		
					<jsp:include page="../../include/item-list.jsp" />
				</ul>		
				
				<div class="load_more" id="op-list-more">
					<button type="button" onClick="javascript:paginationMore('group_${itemParam.where}_${itemParam.query}_${itemParam.orderBy}_${itemParam.sort}')" class="btn_st2"><span>상품 더보기</span>
					</button>
				</div>
			</div> <!-- product_wrap E -->
		</div><!-- con : E -->


<page:javascript>	 
<script type="text/javascript" src="/content/mobile/scripts/jquery/idangerous.swiper-2.1.js"></script>			
<script type="text/javascript">

// 상품이미지 swipe 
$(function() {
				
	//Mobile.swiper({'id' : '#item_relation', 'slidesPerView' : 2});
	//Mobile.swiper({'id' : '#item_other', 'slidesPerView' : 2});
	//Mobile.swiper({'id' : '#review_items', 'slidesPerView' : 2});
	
	

	$('ul.op-ordering-list li').click(function(e){ 
		var value  = $(this).children('.op-sort').val().split('__');
		$('#orderBy').val(value[0]);
		$('#itemParam #sort').val(value[1]);
		$('#itemParam').submit();
	});
	
	// 카테고리에서 찾기 이벤트.
	Shop.findCategory();
	
	
	// 이전페이지로 돌아왔을 때 More Data 유지.
	Mobile.pagination.init('group_${itemParam.where}_${itemParam.query}_${itemParam.orderBy}_${itemParam.sort}');
	
	// 더보기 버튼
	showHideMoreButton();
});


var currentPage = 1;
function paginationMore(key) {
	currentPage++;
	$.post('/m/categories/list/${categoryGroupCode}', $("#itemParam").serialize() + '&page=' + currentPage, function(html) {
		$("#op-list-data").append(html);
		// 더보기 버튼 
		showHideMoreButton();
		Mobile.pagination.set(key);
	});
}

function showHideMoreButton() {
	var totalItems = Number('${pagination.totalItems}');
	if ($("#op-list-data").find(' > li').size() == totalItems || totalItems == 0) {
		$('#op-list-more').hide();
	}
}

</script>	
</page:javascript>