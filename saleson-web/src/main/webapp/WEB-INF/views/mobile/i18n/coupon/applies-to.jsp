<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<!-- 내용 : s -->
<div class="con">
	<div class="pop_title">
		<h3>쿠폰 적용가능 대상</h3>
		<a href="javascript:history.back();" class="history_back">뒤로가기</a>
	</div>
	
	<!-- //pop_title -->
	<div class="pop_con bg">

		<div class="pop_coupon_list">
			<div class="title">
				<p>쿠폰으로 구입 가능한 제품(<span>${ op:numberFormat(totalCount) }</span>)</p>
			</div>
			
			<div class="active_con">
				<ul id="op-list-data">
					<jsp:include page="../include/mypage-applies-list.jsp"></jsp:include>
				</ul>
				
				<c:if test="${totalCount > 10 }">
					<div class="load_more" id="op-list-more">
						<button type="button" class="btn_st2" onclick="javascript:paginationMore('applies')"><span>더보기</span></button>
					</div>
				</c:if>
			</div>
			<!-- //active_con -->
								
			
		</div>
		<!-- //pop_coupon_list -->
		<%-- </c:if> --%>
	</div>
	<!-- //pop_con -->
</div>
<!-- 내용 : e -->

<page:javascript>
<script type="text/javascript">
	function goUrl(url) {
		location.href = url;
	}
	
	
	$(function(){
		// 이전페이지로 돌아왔을 때 More Data 유지.
		Mobile.pagination.init('applies');
		
		showHideMoreButton();
		
		
	})
	
	
	var currentPage = 1;
	function paginationMore(key) {
		currentPage++;
		$.post('/m/coupon/applies-to/'+ '${couponId}' +'/coupon-user/' + currentPage,'page=' + currentPage, function(html) {
			$("#op-list-data").append(html);
			
			showHideMoreButton();
			
			Mobile.pagination.set(key);
		});
	}
	
	

	function showHideMoreButton() {
		if ($("#op-list-data").find(' > li').size() == Number('${pagination.totalItems}')) {
			$('#op-list-more').hide();
		}
	}
	

</script>
</page:javascript>