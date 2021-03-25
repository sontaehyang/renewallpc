<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>	

	<div class="title">
		<h2>활동정보</h2>
		<%--<c:set var="gnbType" value="activity" scope="request" />
		<jsp:include page="../include/mypage-header-gnb.jsp"></jsp:include> --%>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		<ul class="tab_list01 four_tab">
			<li><a href="/m/mypage/wishlist">관심상품</a></li>
			<li class="on"><a href="/m/mypage/review-nonregistered">이용후기</a></li>
			<li><a href="/m/mypage/inquiry-item">상품Q&amp;A</a></li>
			<li><a href="/m/mypage/inquiry">1:1문의</a></li>
		</ul>
	</div> <!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<div class="product_wrap">
			<div class="tab_area">
					<ul class="tab_list02">
						<li><a href="/m/mypage/review-nonregistered">미등록 이용후기(<span>${nonReviewCount}</span>)</a></li>
						<li class="on"><a href="#">작성한 이용후기(<span>${reviewCount}</span>)</a></li>
					</ul>
					<ul class="tab_txt">
						<li>이용후기는 구매확정 된 상품에 한하여 주문일로부터 90일내 작성 가능합니다.</li>
						<li>작성 건에 100${op:message('M00246')} 적립해드립니다. (단, ${op:message('M00246')}는 승인완료시 적립)</li>
					</ul>
				</div>
			<!-- //tab_area -->		
		</div>
	</div>
	<form:form modelAttribute="searchParam" method="get">
		<form:hidden path="page" />
	</form:form>
	<div class="tab_container">
		<div class="tab_con active_con">
			<ul id="op-list-data">
				<jsp:include page="../include/mypage-review-list.jsp" />
			</ul>
		</div>
	</div>
	
	
	<c:if test="${empty reviewList}">
		<ul>
			<li>
				<div class="common_none">
					<p>작성된 이용후기가 없습니다.</p>
				</div>
			</li>
		</ul>
	</c:if>
	
	<div id="op-list-more" class="load_more">
		<%-- <p class="caution">이용후기가 승인된 후에는 수정/삭제가 불가능합니다.</p> --%>
		<button type="button" class="btn_st2" onClick="javascript:paginationMore('review');"><span>더보기</span></button>
	</div>

	<%--form:form modelAttribute="itemParam" method="post">
				<form:hidden path="recommendFlag"/>
				<div class="order_history">
					<dl>
						<dt>주문기간</dt>
						<dd>
							<form:select path="searchStartDate" class="full" title="주문기간">
								<form:option value="" label="전체"/>
								<form:option value="${month3}" label="최근 3개월"/>
								<form:option value="${month1}" label="최근 1개월"/>
								<form:option value="${week}" label="최근 1주일"/>
								<form:option value="${today}" label="오늘"/>
							</form:select>
						</dd>
						<dt>상품명</dt>
						<dd>
							<div>
								<input type="hidden" name="where" value="ITEM_NAME"/>
								<form:input path="query" placeholder="상품명"/>  
								<button type="submit"> </button>
							</div> 
						</dd>
					</dl>		
				</div> 
			</form:form>
			
			<div class="review_tab">
				<ul>
					<li><a href="/m/mypage/review?recommendFlag=N" class="${recommendFlag == 'N' ? 'on' : ''}">수정 가능한 후기</a></li>
					<li><a href="/m/mypage/review?recommendFlag=Y" class="${recommendFlag == 'Y' ? 'on' : ''}">내가 작성한 후기</a></li>
				</ul>
			</div><!--//review_tab E--> --%>			 
	
<page:javascript>	
<script type="text/javascript">
$(function() {
	
	// 더보기 버튼
	showHideMoreButton();
	
	$('.op-star-rating').each(function(e){  // 별점 채우기 - 2016.01.06 kye
		
		var starlen = 5 - $(this).find("span").length;
		for (var i = 0; i < starlen; i++) {
			$(this).append("<span><span>");
        }
	});
	
});


function paginationMore(key) {
	
	$('input[name="page"]', $("#searchParam")).val(Number($('input[name="page"]', $("#searchParam")).val()) + 1);
 	$.post('/m/mypage/review/list', $("#searchParam").serialize(), function(html) {
 		$("#op-list-data").append(html);
 		// 더보기 버튼 
 		showHideMoreButton();
 		
 		history.pushState(null, null, "/m/mypage/review?" + $("#searchParam").serialize());
 	});
	
}

function showHideMoreButton() {
	if ($("#op-list-data").find(' > li').size() == Number('${pagination.totalItems}') || Number('${pagination.totalItems}') == 0) {
		$('#op-list-more').hide();
	}
}

function deleteCheck(reviewId) {	

	if(confirm(Message.get("M00196"))) {	// 삭제하시겠습니까? 
		location.replace("/m/mypage/review/delete/" + reviewId);
	} else {
		return;
	}
}
</script>
</page:javascript>