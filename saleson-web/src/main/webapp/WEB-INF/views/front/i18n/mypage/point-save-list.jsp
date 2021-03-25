<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:set var="title">
	<c:choose>
		<c:when test="${pointType == 'point'}">${op:message('M00246')}</c:when>
		<c:when test="${pointType == 'shipping'}">배송비 쿠폰</c:when>
		<c:when test="${pointType == 'emoney'}">캐시</c:when>
	</c:choose>	
</c:set>

<c:set var="unit">
	<c:choose>
		<c:when test="${pointType == 'point'}">P</c:when>
		<c:when test="${pointType == 'shipping'}">장</c:when>
		<c:when test="${pointType == 'emoney'}">원</c:when>
	</c:choose>
</c:set>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a>
			<a href="#">마이페이지</a> 
			<a href="#">쇼핑내역</a> 
			<span>주문/배송조회</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<jsp:include page="../include/mypage-user-info.jsp" />
	
	<div id="contents" class="pt0"> 
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" />
		<div class="contents_inner"> 	   
			<h2>${title}</h2>
			<div class="mypage_search_area">
				<form:form modelAttribute="pointParam" action="/mypage/${pointType}-save-list" method="get">
					<fieldset>
						<dl>
							<dt>기간별 조회</dt>
							<dd>
								<div class="date_box">
									<span><form:input path="searchStartDate" maxlength="8" class="datepicker term" title="${op:message('M00024')}" /></span>
									<span>~</span>
									<span><form:input path="searchEndDate" maxlength="8" class="datepicker2 term" title="${op:message('M00024')}" /></span>
								</div>
								<div class="search_term day_btns">
									<button type="button" class="btn date_search week-1" title="1주일">1주일</button> 
									<button type="button" class="btn date_search month-1" title="1개월">1개월</button> 
									<button type="button" class="btn date_search month-3" title="3개월">3개월</button> 
									<button type="button" class="btn date_search month-6" title="6개월">6개월</button>  
								</div>
								<button type="submit" class="btn btn_search_min" title="조회">조회</button>
							</dd> 
						</dl> 
					</fieldset>
				</form:form>	 
			</div> <!-- // mypage_search_area E --> 
			<div class="btn_wrap pt10">
				<div class="btn_left">
					<button type="button" class="btn btn-m btn-default" onclick="location.href='/mypage/${pointType}-save-list'"><img src="/content/images/icon/icon_reset.png" alt="초기화">초기화</button>
				</div> 
			</div><!--// btn_wrap E-->
			
			<div class="tabs tabs-2">
				<ul> 
					<li><a href="javascript:;" class="on">적립내역</a></li> 
					<li><a href="/mypage/${pointType}-used-list">사용내역</a></li> 
				</ul> 
			</div>
			
			<c:if test="${expirationPointAmount > 0}">
				<p class="point_nitoce">고객님의 소멸예정 ${title}는 <span class="point">${op:numberFormat(expirationPointAmount)}${unit}</span>입니다. <span class="sub">(다음달에 소멸예정인 ${title}의 총합이며, 소멸은 일단위로 진행됩니다.)</span></p>
			</c:if>
			<div class="board_wrap">
	 			<table cellpadding="0" cellspacing="0" class="board-list">
		 			<caption>${op:message('M00246')}</caption>
		 			<colgroup>
		 				<col style="width:150px;">
		 				<col style="width:auto;">
		 				<col style="width:120px;">
		 				<col style="width:120px;">
		 				<col style="width:150px;">
		 			</colgroup>
		 			<thead> 
		 				<tr>
		 					<th scope="col">날짜</th>
		 					<th scope="col">적립내역</th>
		 					<th scope="col">적립</th>
		 					<th scope="col">잔여</th>
		 					<th scope="col">소멸예정일</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 				<c:forEach items="${list}" var="point">
		 					<tr>
			 					<td class="date"><b>${op:date(point.createdDate)}</b></td>
			 					<td>${point.reason}</td>
			 					<td class="color_23ade3 tright"><b>+${op:numberFormat(point.savedPoint)}${unit}</b></td>
			 					<td class="tright">
			 						<c:choose>
			 							<c:when test="${point.expirationPoint > 0}">소멸(${op:numberFormat(point.expirationPoint)}${unit})</c:when>
			 							<c:when test="${point.point == 0}">-</c:when>
			 							<c:otherwise>${op:numberFormat(point.point)}${unit}</c:otherwise>
			 						</c:choose>
			 					</td>
			 					<td>
			 						<c:choose>
			 							<c:when test="${point.point > 0}">${op:date(point.expirationDate)}</c:when>
			 						</c:choose>
			 					</td>
			 				</tr>	
		 				</c:forEach>
		 				
		 				<c:if test="${empty list}">
							<tr>
			 					<td colspan="5">
			 						<div class="coupon_not">적립내역이 없습니다.</div>
			 					</td> 
			 				</tr> 
						</c:if>	  
		 			</tbody>
		 		</table><!--//view E-->	
		 		<page:pagination-seo />
				
				<c:choose>
					<c:when test="${pointType == 'point'}">
				 		<div class="guide_box mt20">
						 	<ul class="order_guide02">
						 		<li>${op:message('M00246')}는 ${op:message('M00246')}를 지급하는 상품을 구매하신 후 구매확정 된 이후에 자동으로 적립 됩니다.</li>
								<li>결제 시 ${op:message('M00246')} 가용금액을 확인하신 후 잔여 ${op:message('M00246')}를 100원단위로 기입하여 결제하실 수 있습니다.</li>
								<li>${op:message('M00246')}는 현금과 동일하게 결제시 사용하실 수 있으며 유효기간은 적립일로부터 1년입니다.</li>
							</ul>	
						</div><!--//guide_box E-->
					</c:when>
				</c:choose>
				
		 	</div><!-- // board_wrap E --> 
		</div>
	</div><!-- // contents E -->	
</div><!--// inner E-->

<page:javascript>
<script type="text/javascript">
$(function(){
	// 메뉴 활성화
	$('#lnb_benefits').addClass("on");
	
	Common.DateButtonEvent.set('.day_btns button[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	
	ActiveSearchTermButtons();
})

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