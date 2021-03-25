<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
	
<div class="inner">

	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>검색결과</span> 
		</div>
	</div><!-- // location_area E -->
	
	<c:choose>
		<c:when test="${!empty items}">
			
			<div id="contents">
				<div class="search_result"> 
					<p class="tit"><span class="color_23ade3">‘${itemParam.query}’</span> 에 대한 검색결과</p>
					<p class="total">총 <strong>${op:numberFormat(pagination.totalItems)}</strong>개 상품</p> 
				</div>
				
				<form:form modelAttribute="itemParam" method="get">
					<form:hidden path="where" />
					<form:hidden path="query" />
					<form:hidden path="orderBy" />
					<form:hidden path="sort" />
					<form:hidden path="listType" />
					
					<div class="category_sort event mt10">
				 		<ul>
				 			<li><a href="javascript:itemSort('ORDERING', 'ASC')" id="ORDERING_ASC" rel="nofollow">${op:message('M00845')}</a></li> <!-- 신상품순 --> 
				 			<li><a href="javascript:itemSort('HITS', 'DESC')" id="HITS_DESC" rel="nofollow">${op:message('M00846')}</a></li> <!-- 인기순 --> 
				 			<li><a href="javascript:itemSort('SALE_PRICE', 'DESC')" id="SALE_PRICE_DESC" rel="nofollow">${op:message('M00847')}</a></li> <!-- 높은 가격순 -->
				 			<li><a href="javascript:itemSort('SALE_PRICE', 'ASC')" id="SALE_PRICE_ASC" rel="nofollow">${op:message('M00848')}</a></li> <!-- 낮은 가격순 --> 
				 		</ul>
				 		<div class="sort_right">
				 			<form:select path="itemsPerPage">
				 				
				 				<c:forEach var="i" begin="20"  end="100" step="20" >
				 					<form:option value="${i }" label="${i }${op:message('M00844')}" /> <!-- 개 보기 -->
				 				</c:forEach>
				 				
				 			</form:select>
				 			<p class="type">
					 			<span><a href="javascript:itemListType('a')" id="list_type_a" rel="nofollow"><img src="/content/images/icon/sort_icon_view.gif" alt=""></a></span>
					 			<span><a href="javascript:itemListType('c')" id="list_type_c" rel="nofollow"><img src="/content/images/icon/sort_icon_blog.gif" alt=""></a></span>
				 			</p>			 				
				 		</div>
			 		</div><!--//category_sort E-->
				 	
				 	 <c:set var="itemListTypeCss" value="thumb"/>
				 	 <c:if test='${itemParam.listType == "c" }'>
				 	 	<c:set var="itemListTypeCss" value="basic"/>
				 	 </c:if>
				 	 <div class="item-list ${itemListTypeCss }">
				 		<ul class="list-inner">
							<jsp:include page="./../include/item-list.jsp" />
				 		</ul>
				 	</div>
				 	
				 	<c:if test="${!empty items}">
					<page:pagination-seo />
					</c:if>
					
					
				</form:form>
				
			</div><!-- // contents E --> 
			
		</c:when>
		<c:otherwise>
			
			<div id="contents">
				<div class="search_result"> 
					<p class="tit"><span class="color_23ade3">‘${itemParam.query}’</span> 에 대한 검색결과</p>
					<p class="total">총 <strong>0</strong>개 상품</p> 
				</div>
				<div class="guide_box search">
				 	<ul>
				 		<li>검색어가 올바르게 입력되었는지 확인 해주세요.</li>
						<li>두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해 주세요. (예: 일회용속옷 → 일회용 속옷)</li> 
						<li>상품명을 모르시면 상품과 관련된 단어를 입력해 보세요.</li>
						<li>상품이 품절되었을 경우 검색이 되지 않을 수 있습니다.</li>
					</ul>	
				</div>
			</div><!-- // contents E --> 
			
		</c:otherwise>
	</c:choose>
	
	
</div>

	
<jsp:include page="../include/layer-cart.jsp" />
<jsp:include page="../include/layer-wishlist.jsp" />

<page:javascript>
<script type="text/javascript">
$(function() {
	$('#itemsPerPage').on('change', function() {
		$('#itemParam').submit();
	});
	
	// 검색조건 활성화
	itemSearchOptionActive();
});

//검색조건 활성화
function itemSearchOptionActive() {
	var orderBy = '${itemParam.orderBy}';
	var sort = '${itemParam.sort}';
	var listType = 'list_type_${itemParam.listType}';
	
	$('#' + orderBy + '_' + sort).addClass('on');
	var $listTypeImage = $('#' + listType).find('img');
	var src = $listTypeImage.attr('src').replace(".gif", "_on.gif");
	$listTypeImage.attr('src', src);
	
}

//상품 정렬.
function itemSort(orderBy, sortName) {
	var $form = $('#itemParam');
	$form.find('#orderBy').val(orderBy);
	$form.find('#sort').val(sortName);
	$form.submit();
}

//리스트 타입 
function itemListType(listType) {
	var $form = $('#itemParam');
	$form.find('#listType').val(listType);
	$form.submit();
}

</script>	
</page:javascript>
	