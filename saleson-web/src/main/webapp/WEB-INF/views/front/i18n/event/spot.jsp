<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<div class="inner">
	
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>타임세일</span>
		</div>
	</div><!-- // location_area E -->
	<div id="contents" class="pt10">
		<div class="event_banner02">
			<img src="/content/images/common/timeSale1.jpg" alt="타임세일">
		</div>

	<form:form modelAttribute="itemParam" method="get">
			<form:hidden path="orderBy" />
			<form:hidden path="sort" />
			<form:hidden path="listType" />
			
			<!--상품리스트-->	
		 	<div class="category_sort">
		 		<ul>
		 			<li><a href="javascript:itemSort('SPOT_END_DATE', 'ASC')" id="SPOT_END_DATE_ASC" rel="nofollow">종료 기간순</a></li> <!-- 신상품순 -->
		 			<li><a href="javascript:itemSort('HITS', 'DESC')" id="HITS_DESC" rel="nofollow">인기순</a></li> <!-- 인기순 --> 
		 			<li><a href="javascript:itemSort('SALE_PRICE', 'DESC')" id="SALE_PRICE_DESC" rel="nofollow">높은 가격순</a></li> <!-- 높은 가격순 -->
		 			<li><a href="javascript:itemSort('SALE_PRICE', 'ASC')" id="SALE_PRICE_ASC" rel="nofollow">낮은 가격순</a></li> <!-- 낮은 가격순 --> 
		 		</ul>
		 		<!-- 
		 		<div class="sort_right">
		 			<form:select path="itemsPerPage">
		 				<form:option value="20" label="20${op:message('M00844')}" />
		 				<form:option value="40" label="40${op:message('M00844')}" />
		 				<form:option value="60" label="60${op:message('M00844')}" />
		 				<form:option value="80" label="80${op:message('M00844')}" />
		 				<form:option value="100" label="100${op:message('M00844')}" />
		 			</form:select>
		 			<span><a href="javascript:itemListType('a')" id="list_type_a" rel="nofollow"><img src="/content/images/common/sort_icon_view.gif" alt="" ></a></span><span><a href="javascript:itemListType('b')" id="list_type_b" rel="nofollow"><img src="/content/images/common/sort_icon_list.gif" alt="" ></a></span><span><a href="javascript:itemListType('c')" id="list_type_c" rel="nofollow"><img src="/content/images/common/sort_icon_blog.gif" alt="" /></a></span>			 				
		 		</div>
		 		 -->
	 		</div><!--//sort_area E-->
	 	</form:form>

		<c:if test="${!empty list}">
			<div class="item-list thumb spot">
				<c:set var="lineCount" value="1" />
				<c:forEach items="${list}" var="item" varStatus="i">
					<c:if test="${i.count % lineCount == 0}">
						<c:set var="lineCount" value="${lineCount + 4}" />
						<ul class="list-inner">
					</c:if>
					<li>
						<a href="/products/view/${item.itemUserCode}">
							<div class="thumbnail_wrap">
								<div class="item-label-01">
									<span class="last">
										<c:choose>
											<c:when test="${item.spotEndDDay == 0}">
												D-DAY
											</c:when>
											<c:otherwise>
												D-${item.spotEndDDay}
											</c:otherwise>
										</c:choose>
									</span>
								</div>
								<span class="thumbnail${item.itemSoldOut ? ' sold-out' : ''}"><img src="${ shop:loadImage(item.itemUserCode, item.itemImage, 'S') }" alt="thumbnail" class="thumbnail_photo"></span>
							</div>
							<div class="item-info">
								<p class="name">${item.itemName}</p>
								<div class="price-zone clear">
									<p class="price">
										<span class="before_price">${op:numberFormat(item.salePrice)}<span>원</span></span>
										<span class="sale_price">${op:numberFormat(item.presentPrice)}<span>원</span></span>
									</p>
									<p class="sale">${item.discountRate}<span>%</span></p>
								</div>
							</div>
							<div class="sell">
								<p>판매요일 :
									<c:forEach items="${item.spotWeekDayList}" var="code">
										<c:if test="${code.detail == '1'}">
											<span>${fn:substring(code.label, 0, 1)}</span>
										</c:if>
									</c:forEach>
								</p>
								<p>판매시간 : <span>${op:timeFormat(item.spotStartTime)}~${op:timeFormat(item.spotEndTime)}</span> </p>
							</div>
						</a>
					</li>
					<c:if test="${i.count % 4 == 0 || i.last}">
						</ul>
					</c:if>
				</c:forEach>
			</div><!-- // item-list E -->
		</c:if>
		
		<c:if test="${empty list}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>
		</c:if>
		
</div>

	 	
<page:javascript>	
<script type="text/javascript">
$(function() {
	// 검색조건 활성화
	itemSearchOptionActive();

	// UL 높이 맞춤.
	Shop.setHeightOfItemList('.item_list');
});

//검색조건 활성화
function itemSearchOptionActive() {
	var orderBy = '${itemParam.orderBy}';
	var sort = '${itemParam.sort}';
	var listType = 'list_type_${itemParam.listType}';
	
	$('#' + orderBy + '_' + sort).addClass('on');

    var $listTypeImage = $('#' + listType).find('img');

	if ($listTypeImage.attr('src') != undefined) {
        var src = $listTypeImage.attr('src').replace(".gif", "_on.gif");
        $listTypeImage.attr('src', src);
    }

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