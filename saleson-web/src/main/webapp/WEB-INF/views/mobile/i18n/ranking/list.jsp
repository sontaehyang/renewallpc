<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	
<!-- 내용 : s -->
<div class="con">
	<div class="title">
		<h2>히트 상품 랭킹</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->
	<!-- search_result -->
	<div class="product_wrap">
		
	
		<div class="category_tab mb0">
			
			
			<ul class="category_list op-category-list">					
				<c:forEach items="${teamList}" var="teamList" >
					<c:if test="${teamList.code == 'esthetic'}">
						<li data-code="${teamList.code}"><a href="#" class="on">에스테틱</a></li>
					</c:if>
					<c:if test="${teamList.code == 'nail'}">
						<li data-code="${teamList.code}"><a href="#">네일</a></li>
					</c:if>
					<c:if test="${teamList.code == 'matsuge_extension'}">
						<li data-code="${teamList.code}"><a href="#">속눈썹</a></li>
					</c:if>
					<c:if test="${teamList.code == 'hair'}">
						<li data-code="${teamList.code}"><a href="#">헤어</a></li>
					</c:if>						
				</c:forEach>
			</ul>
			
		</div>
		<!-- //category_tab -->
		
		<div class="cate_sub_tab op-cate-sub-tab">
			<div class="scrollBlind">
				<div class="cate_scroll">
					<ul class="menu cf">
					
						<li class="op-d2-all"><a href="#" onclick="javascript:showAll();">전체</a></li>
															
							<c:forEach items="${groupCategoryList}" var="list" varStatus="i">
								<c:forEach items="${list.groups}" var="list2">
									<li class="op-${list.url}"><a href="#" onclick="javascript:showDepth3('${list2.url}');">${list2.name}</a></li>										
								</c:forEach>
							</c:forEach>
							
					</ul>
				</div>
				<!-- //cate_scroll -->
			</div>
			<!-- //scrollBlind -->
					<ul class="sub_menu op-sub-menu cf">
					
						<c:forEach items="${groupCategoryList}" var="list" varStatus="i">
							<c:forEach items="${list.groups}" var="list2">
								<c:forEach items="${list2.categories}" var="list3">
									<li class="op-d3-${list2.url} op-d3 op-d3-sub-${list.url}" style="display: none;"><a href="#">${list3.name}</a></li>
								</c:forEach>
							</c:forEach>
						</c:forEach>
					
					</ul>
			<!-- //sub_menu -->
		</div>
		<!-- //cate_sub_tab -->
	
			
			
	<form:form modelAttribute="rankingParam" method="post">
			<form:hidden path="orderBy" />
			<form:hidden path="sort" />
			
			<form:hidden path="where" />
			<form:hidden path="query" />
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
		<!-- //list_sort_area -->
		
		</form:form>
		
		
		<ul class="product_list" id="op-list-data">
			<jsp:include page="../include/item-ranking-list.jsp"></jsp:include>
		</ul>
		<div class="load_more" id="op-list-more">
			<button type="button" class="btn_st2" onclick="javascript:paginationMore('ranking')"><span>상품 더보기</span></button>
		</div>
	</div>
	<!-- //product_wrap -->

</div>
<!-- 내용 : e -->



<page:javascript>
<script type="text/javascript">
	var code = "";
	$(function(){
		code = $(".op-category-list li a.on").parent().data('code');
		
		$(".more").on("click",function(){
			$(".etc_hit_ranking").fadeToggle(200,"linear");
		});				
		
		
		// 더보기 버튼
		showHideMoreButton();
		
		category();
		
		
		$('ul.op-ordering-list li').click(function(e){ 
			var value  = $(this).children('.op-sort').val().split('__');
			$('#orderBy').val(value[0]);
			$('#rankingParam #sort').val(value[1]);
			$('#rankingParam').submit();
		});
		
		
	});
	
	//카테고리 1차2차3차 select
	function category(){

		$('.op-category-list > li > a').click(function(){
			$('.op-category-list li a').removeClass("on");
			 code = $(this).parent().data('code');
			$(this).addClass("on");
			$('.op-cate-sub-tab').show();
			 $('.op-cate-sub-tab li').hide(); 
	 		$('.op-cate-sub-tab .op-d2-all').show(); 
			$('.op-cate-sub-tab .op-'+code).show();  
			$('.op-sub-menu').hide();
			
		});
		
		$('.op-cate-sub-tab .menu a').click(function(){
			$('.op-sub-menu').show();
			
			
		});
	};
	
	
	//2차 카테고리 전체 클릭
	function showAll() {
		$(".op-d3-sub-"+code).show();
	}
	
	
	//2차 카테고리 상품목록 클릭
	function showDepth3(url) {	

		$('.op-d3').hide();
		$('.op-d3-'+url).show();
	}
	
	
	
	
	var currentPage = 1;
	function paginationMore(key) {
		currentPage++;
		$.post('/m/ranking/list/${team}', /* $("#itemParam").serialize() + */ 'page=' + currentPage, function(html) {
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

	