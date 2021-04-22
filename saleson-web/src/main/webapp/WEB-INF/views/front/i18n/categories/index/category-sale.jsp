<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<c:set var="categoryTeamCode" />

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>특가세일</span> 
		</div>
	</div><!-- // location_area E --> 
	<div id="contents" class="pt10">
	
		<div class="event_banner02">
			<img src="/content/images/common/event_visual_04.jpg" alt="배너이미지">
		</div>
		
		<form:form modelAttribute="itemParam" method="post">
			<form:hidden path="orderBy" />
			<form:hidden path="sort" />
			<form:hidden path="listType" />
			
			<div class="category_sort event">
		 		<ul>
		 			<li><a href="javascript:itemSort('ORDERING', 'ASC')" id="ORDERING_ASC" rel="nofollow">${op:message('M00845')}</a></li> <!-- 신상품순 --> 
		 			<li><a href="javascript:itemSort('HITS', 'DESC')" id="HITS_DESC" rel="nofollow">${op:message('M00846')}</a></li> <!-- 인기순 --> 
		 			<li><a href="javascript:itemSort('SALE_PRICE', 'DESC')" id="SALE_PRICE_DESC" rel="nofollow">${op:message('M00847')}.</a></li> <!-- 높은 가격순 -->
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
		</form:form>	
		
		 <c:set var="itemListTypeCss" value="thumb"/>
	 	 <c:if test='${itemParam.listType == "c" }'>
	 	 	<c:set var="itemListTypeCss" value="basic"/>
	 	 </c:if>
	 	 <div class="item-list ${itemListTypeCss }">
	 		<ul class="list-inner">
				<jsp:include page="../../include/item-list.jsp" />
	 		</ul>
	 	</div>
	 	 
	 	<c:if test="${empty items}">
	 		<div class="no_content">${op:message('M00778')} <!-- 상품이 없습니다. --></div>
	 	</c:if>
	 	
	 	<c:if test="${!empty items}">
		<page:pagination-seo />
		</c:if>
		
	
	</div><!-- // contents E -->
</div>


<jsp:include page="../../include/layer-cart.jsp" />

<page:javascript>
<script type="text/javascript">
$(function() {
	
	// 상품 UL 높이 맞춤.
	Shop.setHeightOfItemList('.item_list');

	
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
});


// 상품평 좌.우 이동.
function setReviewItemEvent() {
	var $reviewItem = $('.selling_rating div.items ul');
	var itemCount = $reviewItem.find('li').size();
	var currentPage = 1;
	var itemsPerPage = 5;
	var itemWidth = 164;
    var totalPages = Math.ceil(itemCount / itemsPerPage);
    
    $reviewItem.width(itemWidth * itemsPerPage * totalPages);
    
    $('.arrow-prev a').on('click', function(e) {
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
    
    $('.arrow-next a').on('click', function(e) {
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
</script>
</page:javascript>