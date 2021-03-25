<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
	
	<!-- //location -->

	<!-- 내용 : s -->
	<div class="con">
		<div class="search_result sel">
			
			<%-- 관리자페이지에 기능 설정이 되어있지 않아 주석처리 2017-06-27 최정아  
			<select id="op-move-page">
				<option value="/m/event/new/all">전체</option>
				<c:forEach items="${categorylist}" var="item" varStatus="i">
					<option value="/m/event/new/${item.code}" ${code == item.code ? "selected" : ""}>${item.name}</option>
				</c:forEach>
			</select> --%>
		
		</div>
				
		<!-- search_result -->
		<div class="product_wrap view_wrap">
			<div class="list_sort_area line">
				<p>총 <span>${op:numberFormat(pagination.totalItems)}</span>개 상품</p>
				
				<form:form modelAttribute="displayItemParam" method="get">
					<form:hidden path="orderBy" />
					<form:hidden path="sort" />
					<form:hidden path="listType" />
					<form:hidden path="where" />
					<form:hidden path="query" />
					<form:hidden path="page" />
					<c:set var="sortTxt"></c:set>
						<c:choose>
							<c:when test="${displayItemParam.orderBy == 'HITS'}">
								<c:set var="sortTxt">신상품순</c:set>
							</c:when>
							<c:when test="${displayItemParam.orderBy == 'SALE_PRICE' && displayItemParam.sort =='DESC'}">
								<c:set var="sortTxt">높은가격순</c:set>
							</c:when>
							<c:when test="${displayItemParam.orderBy == 'SALE_PRICE' && displayItemParam.sort =='ASC'}">
								<c:set var="sortTxt">낮은가격순</c:set>
							</c:when>
							<c:otherwise>
								<c:set var="sortTxt">인기순</c:set>
							</c:otherwise>
						</c:choose>

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
				
				</form:form>
				
			</div>
			<!-- //list_sort_area -->
			
			<ul class="product_list" id="op-list-data"> 
			
					
				<jsp:include page="../include/item-new-list.jsp"></jsp:include>
			

			</ul>
			
			<c:if test="${empty items }">
			<ul class="product_list">
				<li>
					<div class="common_none">
						<p>상품이 없습니다.</p>
					</div>
				</li>
			</ul>				
			</c:if>
			
			<div class="load_more" id="op-list-more">
				<button type="button" class="btn_st2" onclick="javascript:paginationMore('new')"><span>상품 더보기</span></button>
			</div>
		</div>
		<!-- //product_wrap -->
	
	</div>
	<!-- 내용 : e -->


<page:javascript>	
<script type="text/javascript">
$(function() {
	
	$('ul.op-ordering-list li').click(function(e) {
		
		var value  = $(this).children('.op-sort').val().split('__');
		$('#orderBy').val(value[0]);
		$('#displayItemParam #sort').val(value[1]);
		$('#displayItemParam').submit();
	});
	
	
	 $('#op-move-page').on('change', function(){
		
	   location.href= $('#op-move-page option:selected').val();
		
	});
	
	// 더보기 버튼
	showHideMoreButton();
	
});




function paginationMore(key) {
	
	
	$('input[name="page"]', $("#displayItemParam")).val(Number($('input[name="page"]', $("#displayItemParam")).val()) + 1);
 	$.get('/m/event/new/list/${code}', $("#displayItemParam").serialize(), function(html) {
 		$("#op-list-data").append(html);
 		// 더보기 버튼 
 		showHideMoreButton();
 		
 		history.pushState(null, null, "/m/event/new/${code}?" + $("#displayItemParam").serialize());
 	});
	
}

function showHideMoreButton() {
	 var totalItems = Number('${pagination.totalItems}');
	if ($("#op-list-data").find(' > li').size() == totalItems || totalItems == 0) {
		$('#op-list-more').hide();
	}
}

//상품 정렬.
function itemSort(orderBy, sortName) {
	var $form = $('#displayItemParam');
	$form.find('#orderBy').val(orderBy);
	$form.find('#sort').val(sortName);
	$form.submit();
}






</script>
</page:javascript>