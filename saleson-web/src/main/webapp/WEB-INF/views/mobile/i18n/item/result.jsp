<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

	<div class="title">
		<h2>검색결과</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	
	<div class="product_wrap">
		<div class="search_result_txt">
			<p><span>‘${itemParam.query}’</span>에 대한 검색결과</p>
		</div>

		<form:form action="/m/products/result" modelAttribute="itemParam" method="get">
			<form:hidden path="orderBy" />
			<form:hidden path="sort" />
			<form:hidden path="listType" />
			<form:hidden path="where" />
			<form:hidden path="query" />
			<form:hidden path="page" />
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
								<input type="hidden" class="op-sort" value="HITS__DESC"/>
							</li>
							<li>
								<a href="#">신상품순</a>
								<input type="hidden" class="op-sort" value="ORDERING__ASC"/>
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
			<jsp:include page="../include/item-list.jsp" />
		</ul>
		
		<div class="load_more" id="op-list-more">
			<button type="button" onClick="javascript:paginationMore('result')" class="btn_st2"><span>상품 더보기</span>
			</button>
		</div>
	</div>

<page:javascript>
<script type="text/javascript">
$(function() {
	// page load check
	//alert($.cookie('SERVER_LOAD'));
	//$.cookie('SERVER_LOAD', null);
	
	$('ul.op-ordering-list li').click(function(e){
		var value  = $(this).children('.op-sort').val().split('__');
		$('#orderBy').val(value[0]);
		$('#itemParam #sort').val(value[1]);
		$('#itemParam').submit();
	});
	
	// 카테고리에서 찾기 이벤트.
	Shop.findCategory();

	// 더보기 버튼
	showHideMoreButton();
});



function paginationMore(key) {
	$('input[name="page"]', $("#itemParam")).val(Number($('input[name="page"]', $("#itemParam")).val()) + 1);
	$.get('/m/products/result/list', $("#itemParam").serialize(), function(html) {
		$("#op-list-data").append(html);
		// 더보기 버튼 
		showHideMoreButton();

		history.pushState(null, null, "/m/products/result?" + $("#itemParam").serialize());
	});
}

function showHideMoreButton() {
	var totalItems = Number('${pagination.totalItems}');
	if ($("#op-list-data").find(' > li').size() == totalItems || totalItems == 0) {
		$('#op-list-more').hide();
	}
}

</script>

<!-- 카카오픽셀 설치 [검색 이벤트 전송] -->
<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
<script type="text/javascript">
	kakaoPixel('1612698247174901358').pageView();
	kakaoPixel('1612698247174901358').search({
		keyword: '${itemParam.query}'
	});
</script>

<!-- *) 쇼핑몰에서 검색을 이용한 제품찾기 페이지 -->
<script language='javascript'>
	var m_skey='${itemParam.query}';
</script>

</page:javascript>