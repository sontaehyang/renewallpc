<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div id="container">
	<div class="title">
		<h2>주문/배송조회</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<div class="mypage_wrap">
			<form:form modelAttribute="orderParam" action="/m/mypage/order" method="get">
				<form:hidden path="page" />
				<div class="date_search">
					<div class="date_search_info">
						<p class="txt">찾아보고 싶은 날짜를 설정해 주세요.</p>
						<button type="button" class="date_search_btn">기간검색</button>
					</div>
					<div class="date_search_con">
						<ul>
							<li>
								<span class="del_tit t_black">주문기간</span>
								<div class="date_area">
									<form:select path="searchDate">
										<form:option value="" label="선택" />
										<form:option value="today" label="오늘" />
										<form:option value="week-1" label="최근 1주일" />
										<form:option value="month-1" label="최근 1개월" />
										<form:option value="month-3" label="최근 3개월" />
										<form:option value="month-6" label="최근 6개월" />
									</form:select>
								</div>
								<div class="date_area last">
									<form:select path="statusType">
										<form:option value="" label="선택" />
										<form:option value="waiting-deposit" label="주문접수" />
										<form:option value="new-order" label="결제완료" />
										<form:option value="shipping-ready" label="상품준비" />
										<form:option value="shipping" label="배송중" />
										<form:option value="confirm" label="구매확정" />
										<form:option value="cancel-request" label="취소신청" />
										<form:option value="exchange-request" label="교환신청" />
										<form:option value="return-request" label="반품신청" />
									</form:select>
								</div>
							</li>
							<li>
								<span class="del_tit t_black">상품명</span>
								<div class="input_search">
									<form:hidden path="where" value="ITEM_NAME" />
									<form:input path="query" title="상품명조회" placeholder="상품명 조회" />
								</div>
								<p class="desc">최근 1년까지의 주문내역이 제공되며, 한번 조회기간은 6개월 이내로 선택하셔야 됩니다.</p>
							</li>
						</ul>
						<button type="submit" class="btn_st1 decision">검색</button>
					</div>
				</div>
			</form:form>
			<!-- //date_search -->
			
			<div class="active_con">
				<ul id="list-data">
					<jsp:include page="/WEB-INF/views/mobile/i18n/include/mypage-order-list.jsp" />
				</ul>
				
				<c:if test="${empty list }">
					<ul>
						<li>
							<div class="common_none">
								<p>주문정보가 없습니다.</p>
							</div>
						</li>
					</ul>				
				</c:if>
				
				<div class="load_more" id="list-more"><button type="button" class="btn_st2" onclick="javascript:paginationMore('point')"><span>더보기</span></button></div>
			</div>
			<!-- //active_con -->
			
		</div>
		<!-- //mypage_wrap -->
	
	</div>
	<!-- 내용 : e -->
	
</div>
<page:javascript>
<script type="text/javascript" src="/content/modules/op.mypage.js"></script>
<script type="text/javascript" src="/content/modules/op.order.js"></script>

<script type="text/javascript">

$(function(){
	showHideMoreButton();
	$("#orderParam").validator(function(){
		$('input[name="page"]', $("#orderParam")).val('1');
	});
});

function paginationMore(key) {
	$('input[name="page"]', $("#orderParam")).val(Number($('input[name="page"]', $("#orderParam")).val()) + 1);
	$.post('/m/mypage/order/list', $("#orderParam").serialize(), function(html) {
		$("#list-data").append(html); 
		showHideMoreButton();
		
		history.pushState(null, null, "/m/mypage/order?" + $("#orderParam").serialize());
	}); 
}

function showHideMoreButton() {
	if ($("#list-data").find(' > li').size() == Number('${pagination.totalItems}')) {
		$('#list-more').hide();
	}
}

function createdReview(itemUserCode) {
	Common.popup('/m/item/create-review/'+itemUserCode, 'review-write', 800, 600, 1);
}

function orderTracking(url) {
	isMobileLayer = false;
	Common.popup(url, 'order-tracking', 700, 700, 1);
	isMobileLayer = true;
}
</script>
</page:javascript>
	