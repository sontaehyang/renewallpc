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
			<span>신상품</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<c:choose>
		<c:when test="${!empty items}">
			
			
			<div class="board_info"> 
				<p>총 <strong>${op:numberFormat(displaySubCodeCount)}</strong>개 카테고리 / 전체 <strong>${op:numberFormat(pagination.totalItems)}</strong>개의 신상품이 있습니다.</p> 
			</div>
				
			<form:form modelAttribute="displayItemParam" method="get">
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
			 	 <c:if test='${displayItemParam.listType == "c" }'>
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
		
		</c:when>
		<c:otherwise>
			
			<div class="no_content">${op:message('M00778')} <!-- 상품이 없습니다. --></div>
			
		</c:otherwise>
	</c:choose>
	
<jsp:include page="../include/layer-cart.jsp" />
<jsp:include page="../include/layer-wishlist.jsp" />
</div>
<page:javascript>	
<script type="text/javascript">
$(function() {
	$('#itemsPerPage').on('change', function() {
		$('#displayItemParam').submit();
	});
	
	// 검색조건 활성화
	itemSearchOptionActive();
});

//검색조건 활성화
function itemSearchOptionActive() {
	var orderBy = '${displayItemParam.orderBy}';
	var sort = '${displayItemParam.sort}';
	var listType = 'list_type_${displayItemParam.listType}';
	
	$('#' + orderBy + '_' + sort).addClass('on');
	var $listTypeImage = $('#' + listType).find('img');
	var src = $listTypeImage.attr('src').replace(".gif", "_on.gif");
	$listTypeImage.attr('src', src);
	
}

//상품 정렬.
function itemSort(orderBy, sortName) {
	var $form = $('#displayItemParam');
	$form.find('#orderBy').val(orderBy);
	$form.find('#sort').val(sortName);
	$form.submit();
}

//리스트 타입 
function itemListType(listType) {
	var $form = $('#displayItemParam');
	$form.find('#listType').val(listType);
	$form.submit();
}

</script>
</page:javascript>