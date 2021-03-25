<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="inicis"	tagdir="/WEB-INF/tags/pg/inicis" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/order">쇼핑내역</a> 
			<span>주문/배송조회</span> 
		</div>
	</div><!-- // location_area E -->

	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<div id="contents" class="pt0"> 
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
		<div class="contents_inner"> 	   
			<h2>주문/배송조회</h2>		 
			<div class="mypage_search_area">
				<form:form modelAttribute="orderSearchParam" name="orderSearchParam" action="/mypage/order" method="get">
					<fieldset>
						<dl>
							<dt>기간별 조회</dt>
							<dd>
								<div class="date_box">
									<span><form:input path="searchStartDate" maxlength="8" class="datepicker term" title="${op:message('M00024')}" /></span>
									<span>~</span>
									<span><form:input path="searchEndDate" maxlength="8" class="datepicker2 term" title="${op:message('M00025')}" /></span>
								</div>
								<div class="search_term">
									<button type="button" class="btn date_search week-1" title="1주일">1주일</button> 
									<button type="button" class="btn date_search month-1" title="1개월">1개월</button> 
									<button type="button" class="btn date_search month-3" title="3개월">3개월</button> 
									<button type="button" class="btn date_search month-6" title="6개월">6개월</button> 
								</div>
							</dd>
							<dt class="mt5">상품명 조회</dt>
							<dd class="mt5">
								<div class="col-w-0 search_write">
									<form:hidden path="where" value="ITEM_NAME" />
									<form:input path="query" title="상품명조회" placeholder="상품명 조회" />
									<button type="submit" class="btn btn_search_min" title="조회">조회</button>
								</div>
							</dd>
						</dl> 
					</fieldset>
				</form:form>
			</div> <!-- // mypage_search_area E -->
			<div class="btn_wrap pt10">
				<div class="btn_left">
					<button type="button" class="btn btn-m btn-default" onclick="location.href='/mypage/order'"><img src="/content/images/icon/icon_reset.png" alt="초기화">초기화</button>
				</div> 
			</div> 
			
			<c:if test="${op:property('pg.service') == 'inicis' }">
				<div class="escrowInputArea">
					<inicis:iniescrow-input />
				</div>
			</c:if>
			 
			<div class="board_wrap mt30">  
				<table cellpadding="0" cellspacing="0"  class="order_list">
					<caption>주문정보</caption>
					<colgroup>
						<col style="width:125px;"> 
						<col style="width:auto;">  
						<col style="width:130px;"> 
					</colgroup>
					<thead>
						<tr> 
							<th scope="col">주문번호/일자</th>
							<th scope="col">상품정보</th> 
							<th scope="col">주문/배송상태</th>  
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ list }" var="order">
							<c:set var="index">0</c:set>
							<c:forEach items="${ order.orderShippingInfos }" var="shippingInfo">
								<c:forEach items="${ shippingInfo.orderItems }" var="orderItem">

									<c:set var="totalItemAmount" value="${orderItem.itemAmount}" />
									<c:forEach items="${orderItem.additionItemList}" var="addition">
										<c:set var="totalItemAmount">${totalItemAmount + addition.itemAmount}</c:set>
									</c:forEach>

									<tr>
										<c:if test="${index == 0}">
											<td rowspan="${order.itemCount}"> 
												<div class="order_number">
													<a href="/mypage/order-detail/${order.orderSequence}/${order.orderCode}">${order.orderCode}</a>
													<span class="date">(${op:date(order.createdDate)})</div>
												</div><!--// order_number E-->
											</td>
										</c:if>
										<td class="tleft">
											<div class="item_info">
												<a href="/products/view/${orderItem.itemUserCode}">
													<p class="photo">
													<img src="${ shop:loadImageBySrc(orderItem.imageSrc, 'XS') }" alt="item photo">
													</p>
												</a>
												<div class="order_option">
													<a href="/products/view/${orderItem.itemUserCode}"><p class="item_name">${orderItem.itemName}</p></a>
													${shop:viewOptionText(orderItem.options)}

													${shop:viewAdditionOrderItemList(orderItem.additionItemList)}

													<c:set var="orderGiftItemText" value="${shop:makeOrderGiftItemText(orderItem.orderGiftItemList)}"/>
													<c:if test="${not empty orderGiftItemText}">
														<ul class="option">
															<li>
																<span class="choice">사은품 : </span>
																<span>${orderGiftItemText}</span>
															</li>
														</ul>
													</c:if>

													<div class="item_price">
														<span>${op:numberFormat(totalItemAmount)}</span>원 / <span>${op:numberFormat(orderItem.quantity)}</span>개
													</div>
												</div>
											</div><!--// item_info E-->
										</td>
										<td>
											<div class="condition">
												<p>${orderItem.orderStatusLabel}</p>
												<%-- CJH 입금 만료일자 작업전
												<p class="date">(2016-11-11)</p>
												--%>
												<div class="order_btnList">
													<c:set var="orderItem" value="${orderItem}" scope="request" />
													<jsp:include page="order-button.jsp" />
												</div>
											</div>
										</td>
									</tr>
								
									<c:set var="index">${index + 1}</c:set>
								</c:forEach>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table><!--//esthe-table E-->
				<c:if test="${empty list}">
					<div class="no_content">
						주문 정보가 없습니다.
					</div>
				</c:if>

			</div><!--//board_write_type01 E-->
			
			<page:pagination-seo /> 	  
		</div>	
	</div><!-- // contents E -->	
</div><!--// inner E-->

<page:javascript>
<inicis:iniescrow-script />
<!-- 다음 주소검색 -->
<daum:address />


<script type="text/javascript" src="/content/modules/op.mypage.js"></script>
<script type="text/javascript" src="/content/modules/op.order.js"></script>
<script type="text/javascript">
$(function(){
	// 메뉴 활성화
	$('#lnb_order').addClass("on");
	
	Common.DateButtonEvent.set('.search_term button[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	
 	ActiveSearchTermButtons();

});

function orderTracking(url) {
	Common.popup(url, 'order-tracking', 700, 700, 1);
}

function createdReview(itemUserCode) {
	Common.popup('/item/create-review/'+itemUserCode, 'review-write', 800, 600, 1);
}

function itemReview(orderCode,itemUserCode) {
	Common.popup('/item/create-review/'+orderCode+'/'+ itemUserCode, 'create_review', 820, 615, 0);
}

function openDaumPostcodeForReturn() {
	var tagNames = {
		/* 'zipcode'				: 'returnReserveZipcode', */
		'newZipcode'				: 'returnReserveZipcode',
		'zipcode1' 				: 'returnReserveZipcode1',
		'zipcode2' 				: 'returnReserveZipcode2',
		'sido'					: 'returnReserveSido',
		'sigungu'				: 'returnReserveSigungu',
		'eupmyeondong'			: 'returnReserveEupmyeondong',
		/* 'jibunAddress'			: 'returnReserveAddress', */
		'roadAddress'			: 'returnReserveAddress',
		'jibunAddressDetail' 	: 'returnReserveAddress2'		
	}
	openDaumAddress(tagNames);
}

function openDaumPostcodeForExchange() {
	var tagNames = {
		/* 'zipcode'				: 'exchangeReceiveZipcode', */
		'newZipcode'				: 'exchangeReceiveZipcode',
		'zipcode1' 				: 'exchangeReceiveZipcode1',
		'zipcode2' 				: 'exchangeReceiveZipcode2',
		'sido'					: 'exchangeReceiveSido',
		'sigungu'				: 'exchangeReceiveSigungu',
		'eupmyeondong'			: 'exchangeReceiveEupmyeondong',
		/* 'jibunAddress'			: 'exchangeReceiveAddress', */
		'roadAddress'			: 'exchangeReceiveAddress',
		'jibunAddressDetail' 	: 'exchangeReceiveAddress2'		
	}
	openDaumAddress(tagNames);
}

function ActiveSearchTermButtons() {
	var searchTerm = '${searchTerm}';

	if(searchTerm != null && searchTerm != '') {
		$('.' + searchTerm).addClass('active');
	}
	$('.date_search').on('click', function() {
		$('.date_search').removeClass('active');
	});
}

</script>
</page:javascript>