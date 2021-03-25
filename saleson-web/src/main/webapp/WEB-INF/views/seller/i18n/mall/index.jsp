<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<jsp:include page="./include/inc_header.jsp" />

			<div class="mall-category">
				<ul>
					<li><a href="#" data-categoryclass="" ${itemParam.categoryClass == '' ? 'class="on"' : ''}>전체보기</a></li>
					<c:forEach items="${sellerCategories}" var="sellerCategory">
						<li>
							<a href="#" data-categoryclass="${sellerCategory.categoryClass}" ${sellerCategory.categoryClass == itemParam.categoryClass ? 'class="on"' : ''}>
								${sellerCategory.categoryName} (${sellerCategory.itemCount})
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			
			<form:form modelAttribute="itemParam" method="post">
			<form:hidden path="orderBy" />
			<form:hidden path="sort" />
			<form:hidden path="listType" />
			<form:hidden path="categoryClass" />

			<!--상품리스트-->	
		 	<div class="sort_area">
		 		<div class="sort_total">
		 			상품 <span>${op:numberFormat(pagination.totalItems)}</span>개
		 		</div>
		 		<!-- 하위 카테고리 -->
				<c:if test="${!empty category.childCategories}">
					<div class="item_tab">
						<ul>
						<c:forEach items="${category.childCategories}" var="childCategory">
							<li><a href="${childCategory.url}">${childCategory.name}</a></li>
						</c:forEach>
						</ul>
					</div>
				</c:if>
		 		
		 		<div class="category_sort">
			 		<ul>
			 			<li><a href="javascript:itemSort('ORDERING', 'ASC')" id="ORDERING_ASC" rel="nofollow">${op:message('M00845')}</a></li> <!-- 신상품순 --> 
			 			<li><a href="javascript:itemSort('HITS', 'DESC')" id="HITS_DESC" rel="nofollow">${op:message('M00846')}</a></li> <!-- 인기순 --> 
			 			<li><a href="javascript:itemSort('SALE_PRICE', 'DESC')" id="SALE_PRICE_DESC" rel="nofollow">${op:message('M00847')}</a></li> <!-- 높은 가격순 -->
			 			<li><a href="javascript:itemSort('SALE_PRICE', 'ASC')" id="SALE_PRICE_ASC" rel="nofollow">${op:message('M00848')}</a></li> <!-- 낮은 가격순 --> 
			 		</ul>
			 		<div class="sort_right">
			 			<form:select path="itemsPerPage">
			 				<form:option value="20" label="20${op:message('M00844')}" /> <!-- 개 보기 --> 
			 				<form:option value="40" label="40${op:message('M00844')}" />
			 				<form:option value="60" label="60${op:message('M00844')}" />
			 				<form:option value="80" label="80${op:message('M00844')}" />
			 				<form:option value="100" label="100${op:message('M00844')}" />
			 			</form:select>
			 			<span><a href="javascript:itemListType('a')" id="list_type_a" rel="nofollow"><img src="/content/images/common/sort_icon_view.gif" alt="" ></a></span><span><a href="javascript:itemListType('b')" id="list_type_b" rel="nofollow"><img src="/content/images/common/sort_icon_list.gif" alt="" ></a></span><span><a href="javascript:itemListType('c')" id="list_type_c" rel="nofollow"><img src="/content/images/common/sort_icon_blog.gif" alt="" /></a></span>			 				
			 		</div>
		 		</div><!--//category_sort E-->
		 	</div><!--//sort_area E-->
	 	</form:form>
	 	
	 	<div class="category_list">
	 		<ul class="item_list type_${itemParam.listType}">
				<jsp:include page="/WEB-INF/views/front/i18n/include/item-list.jsp" />
	 		</ul>
	 	</div>
	 	
	 	<c:if test="${empty items}">
	 		<div class="no_content">${op:message('M00778')} <!-- 상품이 없습니다. --></div>
	 	</c:if>
		
		<c:if test="${!empty items}">
		<page:pagination-seo />
		</c:if>


<jsp:include page="/WEB-INF/views/front/i18n/include/layer-cart.jsp" />
<jsp:include page="/WEB-INF/views/front/i18n/include/layer-wishlist.jsp" />

		 	
<page:javascript>	
<script type="text/javascript">
$(function() {
	
	// 상품 UL 높이 맞춤.
	//Shop.setHeightOfItemList('.item_list');

	// 카테고리 클릭 이벤트.
	initCategoryEvent();
	
	// 검색조건 활성화
	itemSearchOptionActive();
	
	if ($('#color').val() != '') {
		$('#color_box').css('background', $('#color').val()).show();
	} else {
		$('#color_box').hide();
	}
	
	$('#color, #priceRange, #itemsPerPage').on('change', function() {
		$('#itemParam').submit();
	});
	
	$('.child-categories li').each(function() {
		if ($(this).height() > 15) {
			$(this).parent().addClass("col-3");
			return;
		}
	});
	
	// 상품 마우스 오버시 다른 이미지 보이기.
	$('.item_list a').on("mouseenter", function() {
		//$(this).find('.photo_area').animate({'opacity': '0.4'}, 'fast');
		//$(this).find('.item_extra_info').show('fast');
	}).on("mouseleave", function(){
		//$(this).find('.photo_area').animate({'opacity': '1'}, 'fast');
		//$(this).find('.item_extra_info').hide('fast');
	});
	
	// 상품평 좌우 이동 이벤트
	setReviewItemEvent();
	
	// 카테고리 클릭 이벤트 
	initCategoryClickEvent();
});

// 카테고리 클릭 이벤트.
function initCategoryEvent() {
	var $category = $('.mall-category a');
	$category.on('click', function(e) {
		e.preventDefault();
		
		var categoryClass = $(this).data('categoryclass');
		
		$('#categoryClass').val(categoryClass);
		
		$('#itemParam').submit();
	});
}
	
	
	
// 상품평 좌.우 이동.
function setReviewItemEvent() {
	var $reviewItem = $('.latest_list ul');
	var itemCount = $reviewItem.find('li').size();
	var currentPage = 1;
	var itemsPerPage = 3;
	var itemWidth = 295;
    var totalPages = Math.ceil(itemCount / itemsPerPage);
    
    $reviewItem.css('position', 'relative').width(itemWidth * itemsPerPage * totalPages);
    
    $('.arrow-prev').on('click', function(e) {
    	e.preventDefault();
    	
    	if (totalPages > 1) {
    		if (currentPage == 1) {
    			//currentPage = totalPages;
    		} else {
    			currentPage--;
    			
    			var topSize = (currentPage - 1) * itemWidth * itemsPerPage;
        		$reviewItem.animate({'left': '-' + topSize + 'px'}, 'fast');
    		}
    	} 
    });
    
    $('.arrow-next').on('click', function(e) {
    	e.preventDefault();

    	if (totalPages > 1) {
    		if (currentPage == totalPages) {
    			//currentPage = 1;
    		} else {
    			currentPage++;
    			
    			var topSize = (currentPage - 1) * itemWidth * itemsPerPage;
        		$reviewItem.animate({'left': '-' + topSize + 'px'}, 'fast');
    		}
    	} 
    });
}

// 검색조건 활성화
function itemSearchOptionActive() {
	var orderBy = '${itemParam.orderBy}';
	var sort = '${itemParam.sort}';
	var listType = 'list_type_${itemParam.listType}';
	
	$('#' + orderBy + '_' + sort).addClass('on');
	var $listTypeImage = $('#' + listType).find('img');
	var src = $listTypeImage.attr('src').replace(".gif", "_on.gif");
	$listTypeImage.attr('src', src);
	
}

// 상품 정렬.
function itemSort(orderBy, sortName) {
	var $form = $('#itemParam');
	$form.find('#orderBy').val(orderBy);
	$form.find('#sort').val(sortName);
	$form.submit();
}

// 리스트 타입 
function itemListType(listType) {
	var $form = $('#itemParam');
	$form.find('#listType').val(listType);
	$form.submit();
}

// 카테고리 클릭 이벤트 
function initCategoryClickEvent() {
	$('.mall-category a').on('click', function(e) {
		e.preventDefault();
		
		var categoryClass = $(this).data('categoryclass');
		$('#categoryClass').val(categoryClass);
		
		$('#itemParam').submit();
		
	});
}
</script>		 	
</page:javascript>

<jsp:include page="./include/inc_footer.jsp" />