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

	<!-- 내용 : s -->
	<div class="con best_wrap">
		<!-- search_result -->
		<div class="product_wrap">
			<!-- product_cate -->
			<ul class="product_list typeA line cf">
				<jsp:include page="../include/item-best-list.jsp"></jsp:include>
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
			
			<%-- <div class="load_more"><button type="button" class="btn_st2"><span>상품 더보기</span></button></div> --%>
		</div>
		<!-- //product_wrap -->
	
	</div>
	<!-- 내용 : e -->





<page:javascript>
<script type="text/javascript">
	$(function() {
		
		 $('#op-move-page').on('change', function(){
				
			   location.href= $('#op-move-page option:selected').val();
				
			});
		
		/*
		// 이전페이지로 돌아왔을 때 More Data 유지.
		Mobile.pagination.init('${itemParam.team}');
		
		// 더보기 버튼
		showHideMoreButton();
		*/
	});
	
/*	
	
	var currentPage = 1;
	function paginationMore(key) {
		currentPage++;
		$.post('/m/event/spot', $("#itemParam").serialize() + '&page=' + currentPage, function(html) {
			$("#list-data").append(html);
			// 더보기 버튼 
			showHideMoreButton();
			Mobile.pagination.set(key);
		});
	}
	
	function showHideMoreButton() {
		var totalItems = Number('${pagination.totalItems}');
		if ($("#best_list").find(' > li').size() == totalItems || totalItems == 0) {
			$('#list-more').hide();
		}
	}
	*/
</script>
</page:javascript>