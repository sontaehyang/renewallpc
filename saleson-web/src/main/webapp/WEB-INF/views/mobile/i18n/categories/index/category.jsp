<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<input type="hidden" name="categoryCode" value="${category.categoryUrl}" />

<div class="con category_main">
	<div class="title">
		<h2>${category.parentCategory.name}</h2>
		<c:if test="${!empty category.parentCategory.categoryId}">
			<span class="his_back">
				<a href="/m/categories/index/${category.parentCategory.url}" class="ir_pm">뒤로가기</a>
			</span>
		</c:if>
	</div>
	<div class="product_wrap">
		<div class="category_tab">
			<ul class="category_list">
				<c:forEach items="${category.siblingCategories}" var="siblingCategory" varStatus="i">
					<li><a href="/m/categories/index/${siblingCategory.url}" ${category.categoryId == siblingCategory.categoryId ? 'class="on"' : ''}>${siblingCategory.name}</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>

<div class="con">
	<div class="product_wrap">
		<form:form action="/m/categories/index/${category.categoryUrl}" modelAttribute="itemParam" method="get">
			<form:hidden path="orderBy" />
			<form:hidden path="sort" />
			<form:hidden path="listType" />
			<form:hidden path="page" />
			<form:hidden path="fcIds" />
			<form:hidden path="maxPrice" />
			<form:hidden path="minPrice" />
			<c:set var="sortTxt"></c:set>

			<c:choose>
				<c:when test="${itemParam.orderBy == 'ORDERING'}">
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
			<div class="filter_wrap">
				<button type="button" class="btn_filter op_filter_search">
					<p><span>FILTER</span></p>
				</button>
			</div>
		</form:form>

		<c:if test="${!empty items}">
			<ul id="op-list-data" class="product_list">
				<jsp:include page="../../include/item-list.jsp" />
			</ul>
		</c:if>

		<c:if test="${empty items}">
			<ul class="product_list">
				<li>
					<div class="common_none">
						<p>상품이 없습니다.</p>
					</div>
				</li>
			</ul>
		</c:if>

		<div class="load_more" id="op-list-more">
			<button type="button" onClick="javascript:paginationMore('category_${itemParam.where}_${itemParam.query}_${itemParam.orderBy}_${itemParam.sort}')" class="btn_st2"><span>상품 더보기</span>
			</button>
		</div>
	</div>
</div>

<div class="con pop_filter Chrome" style="display: none;">
	<div class="pop_title">
		<h4>FILTER</h4>
	</div>
	<a href="#" class="pop_close">
		<span class="screen_out">닫기</span>
	</a>
	<div class="filter_m_wrap">
		<a href="javascript:void(0)" class="tit">가격</a>
		<div class="content" id="priceFilter">
			<c:forEach items="${priceAreaList}" var="data" varStatus="i">
				<div class="lists ${itemParam.minPrice == data.beginSalePrice && itemParam.maxPrice == data.endSalePrice ? 'on' : ''}">
					<input type="radio" name="radio" id="chk${i.index}" value="${data.beginSalePrice}_${data.endSalePrice}"
						${itemParam.minPrice == data.beginSalePrice && itemParam.maxPrice == data.endSalePrice ? 'checked="checked"' : ''}>
					<label for="chk${i.index}">
						<span>
							<span></span>
						</span>
						<c:if test="${data.beginSalePrice == 0}">
							${op:numberFormat(data.endSalePrice)}원 이하
						</c:if>
						<c:if test="${data.beginSalePrice != 0 && data.endSalePrice != 0}">
							${op:numberFormat(data.beginSalePrice)}원 ~ ${op:numberFormat(data.endSalePrice)}원
						</c:if>
						<c:if test="${data.endSalePrice == 0}">
							${op:numberFormat(data.beginSalePrice)}원 이상
						</c:if>
					</label>
				</div>
			</c:forEach>
		</div>

		<c:forEach items="${categoryFilterList}" var="group">
			<c:if test="${group.filterType == 'COLOR'}">
				<a href="javascript:void(0)" class="tit">${group.label}</a>
				<div class="content color op_content">
					<c:forEach items="${group.codeList}" var="codes">
						<div class="lists ${codes.labelCode == '#ffffff' ? 'chk_b' : 'chk'}">
							<a href="javascript:void(0)" name="${group.id}_${codes.id}" onClick="javascript:filterLink('${group.id}', '${codes.id}')"
							   class="custom ${fn:indexOf(itemParam.fcIds, codes.id) > -1 ? 'on' : ''}" style="background-color:${codes.labelCode};"></a>
						</div>
					</c:forEach>
				</div>
			</c:if>

			<c:if test="${group.filterType == 'IMAGE'}">
				<a href="javascript:void(0)" class="tit">${group.label}</a>
				<div class="content kind op_content">
					<c:forEach items="${group.codeList}" var="codes">
						<div class="lists">
							<a href="javascript:void(0)" name="${group.id}_${codes.id}" onClick="javascript:filterLink('${group.id}', '${codes.id}')"
							   class="${fn:indexOf(itemParam.fcIds, codes.id) > -1 ? 'on' : ''}">
								<img src="${codes.imageSrc}" alt="${codes.label}">
								<span class="txt">${codes.label}</span>
							</a>
						</div>
					</c:forEach>
				</div>
			</c:if>

			<c:if test="${group.filterType != 'COLOR' && group.filterType != 'IMAGE'}">
				<a href="javascript:void(0)" class="tit">${group.label}</a>
				<div class="content kind op_content">
					<c:forEach items="${group.codeList}" var="codes">
						<div class="lists">
							<a href="javascript:void(0)" name="${group.id}_${codes.id}" onClick="javascript:filterLink('${group.id}', '${codes.id}')"
							   class="${fn:indexOf(itemParam.fcIds, codes.id) > -1 ? 'on' : ''}">
								<span class="txt">${codes.label}</span>
							</a>
						</div>
					</c:forEach>
				</div>
			</c:if>
		</c:forEach>

		<div class="btn_wrap">
			<button type="button" class="btn_reset" onClick="javascript:filterReset()">초기화</button>
			<button type="button" class="btn_st1 back" onclick="javascript:filterSearch()">검색하기</button>
		</div>


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


			// 카테고리 필터
			$('#priceFilter > div > input[type=radio]').on('click', function(i) {
				if ($(i.target.parentElement).hasClass('op_on')) {
					$('#priceFilter > div').removeClass('op_on');
				} else {
					$('#priceFilter > div').removeClass('op_on');
					$(i.target.parentElement).toggleClass('op_on');
				}

				var startPrice = new Array();
				var endPrice = new Array();
				if ($('#priceFilter > div').hasClass('op_on')) {
					$.each($('#priceFilter > div'), function(i) {
						if ($('#priceFilter > div:eq('+i+')').hasClass('op_on')) {
							startPrice.push($('#priceFilter > div:eq('+i+') >').val().split('_')[0]);
							endPrice.push($('#priceFilter > div:eq('+i+') >').val().split('_')[1]);
						}
					});

					$('#minPrice').val(Math.min.apply(null, startPrice));
					$('#maxPrice').val(Math.max.apply(null, endPrice));

				}
			});

			// 더보기 버튼
			showHideMoreButton();

		});




		function paginationMore(key) {
			$('input[name="page"]', $("#itemParam")).val(Number($('input[name="page"]', $("#itemParam")).val()) + 1);
			$.post('/m/categories/list/${categoryCode}', $("#itemParam").serialize(), function(html) {
				$("#op-list-data").append(html);
				// 더보기 버튼
				showHideMoreButton();

				history.pushState(null, null, "/m/categories/index/${categoryCode}?" + $("#itemParam").serialize());
			});
		}

		function showHideMoreButton() {
			var totalItems = Number('${pagination.totalItems}');
			if ($("#op-list-data").find(' > li').size() == totalItems || totalItems == 0) {
				$('#op-list-more').hide();
			}
		}

		function filterLink(groupId, filterCode) {
			var $filter = $(".op_content [name='"+groupId+'_'+filterCode+"']");
			if ($filter.hasClass("on")) {
				$filter.removeClass('on');
			} else if (!$filter.hasClass("on")) {
				$filter.addClass('on');
			}

			var fcIds = $('#fcIds').val();
			if (fcIds.indexOf(filterCode) > -1) {
				var tempArray = fcIds.split(filterCode + "N");
				$('#fcIds').val(tempArray.join(''));
			} else {
				$('#fcIds').val(fcIds + filterCode + "N");
			}
		}

		function filterSearch() {
			$('#itemParam').submit();
		}

		function filterReset() {
			$('#fcIds').val('');
			$('#minPrice').val(0);
			$('#maxPrice').val(0);

			$('#itemParam').submit();
		}

	</script>
</page:javascript>			