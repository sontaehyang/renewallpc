<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<%-- 	<div class="order">
		<c:set var="gnbType" value="activity" scope="request" />
		<jsp:include page="../include/mypage-header-gnb.jsp"></jsp:include>
		
		<ul class="wish_menu">
			<li><a href="/m/mypage/wishlist" class="on">관심상품</a></li>
			<li><a href="/m/mypage/review?recommendFlag=N">이용후기</a></li>
			<li><a href="/m/mypage/inquiry-item">상품Q&amp;A</a></li>
			<li><a href="/m/mypage/inquiry">1:1문의</a></li>
		</ul> 
		
		<form id="listForm">
			<div class="sort_area">
				<span class="left"><label><input type="checkbox" class="check_all"/> ${op:message('M00164')}</label></span>
				<span class="right"><a href="javascript:deleteSelectedItem()" class="btn_choice_del">${op:message('M00576')}</a></span>
			</div> 
			<div class="wish_list">
				<p>관심상품 <span>${totalItemCount}</span>건</p>
				<ul class="item" id="list-data">
					<jsp:include page="../include/mypage-wishlist-list.jsp" />
				</ul>
			</div><!--//wish_list E-->
		</form>
			
		<div class="btn_area wrap_btn" id="list-more">
 			<a href="javascript:paginationMore('wishlist')" class="btn_more">더보기 <span><img src="/content/mobile/images/common/btn_mores.png" alt="더보기" height="15"></span></a>
		</div> 
			 
	</div>	<!--//order E-->


<jsp:include page="../include/layer-cart.jsp" /> --%>


	<!-- container : s -->
	<div id="container">
		<div class="title">
			<h2>활동정보</h2>
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
			<ul class="tab_list01 four_tab">				
				<li class="on"><a href="#">관심상품</a></li>
				<li><a href="/m/mypage/review-nonregistered">이용후기</a></li>
				<li><a href="/m/mypage/inquiry-item">상품Q&amp;A</a></li>
				<li><a href="/m/mypage/inquiry">1:1문의</a></li>
			</ul>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
		
		<form:form modelAttribute="wishlistParam" method="post">
			<form:hidden path="page" />
		</form:form>
			<div class="mypage_wrap">
				<div class="fv_product cf">
					<p class="fv_num">관심상품(<span>${totalItemCount}</span>)</p>
					<button type="button" title="전체삭제" class="btn_st3 s_small" onclick="deleteAllItem();">전체삭제</button>
				</div>
				<!-- //fv_product -->
				
				<div class="active_con">
					<ul  id="list-data">
					
					
						<jsp:include page="../include/mypage-wishlist-list.jsp"></jsp:include>
												
						
					</ul>
					
					
					<c:if test="${empty wishlists }">
						<ul class="product_list">
							<li>
								<div class="common_none">
									<p>등록된 관심상품이 없습니다.</p>
								</div>
							</li>
						</ul>				
					</c:if>
					<div class="load_more" id="list-more">
						<button type="button" class="btn_st2" onclick="javascript:paginationMore('wishlist')"><span>더보기</span></button>
					</div>
				</div>
				<!-- //active_con -->

			</div>
			<!-- //mypage_wrap -->
			
		
		
		</div>
		<!-- 내용 : e -->
		
	</div>
	<!-- //#container -->
	<!-- container : e -->
	
<page:javascript>
<script type="text/javascript">
$(function() {

	showHideMoreButton();
});

function deleteSelectedItem(wishlistId){
	
	if (confirm(Message.get("M00196"))) { // 삭제하시겠습니까?
		var param = {};
		
		$.post('/m/wishlist/list/delete/'+wishlistId, param, function(response){
			if (response.isSuccess) {
				location.reload();
			} else {
				alert(response.errorMessage);
				location.reload();
			}
		}, 'json');
	}
	
}


// 선택삭제 
function deleteAllItem() {

	if (confirm(Message.get("M00196"))) { // 삭제하시겠습니까?
		var param = {};
		
		$.post('/m/wishlist/list/delete/all', param, function(response){
			if (response.isSuccess) {
				location.reload();
			} else {
				alert(response.errorMessage);
				location.reload();
			}
		}, 'json');
	}
			
}

function showHideMoreButton() {
	
	if ($("#list-data").find(' > li').size() == Number('${pagination.totalItems}') || Number('${pagination.totalItems}') == 0) {
		$('#list-more').hide();
	}
}

function paginationMore(key) {
	$('input[name="page"]', $("#wishlistParam")).val(Number($('input[name="page"]', $("#wishlistParam")).val()) + 1);
	$.post('/m/mypage/wishlist/list', $("#wishlistParam").serialize(), function(html) {
		$("#list-data").append(html);
		
		showHideMoreButton();
		history.pushState(null, null, "/m/mypage/wishlist?" + $("#wishlistParam").serialize());
	});
}


</script>	 	
</page:javascript>	 	