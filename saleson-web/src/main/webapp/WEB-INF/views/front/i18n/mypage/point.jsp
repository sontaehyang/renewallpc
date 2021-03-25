<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

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
			<h2>${op:message('M00246')}</h2>
			<div class="mypage_search_area">
				<form:form modelAttribute="pointParam" action="/mypage/point" method="post">
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
									<button type="button" class="btn date_search op-day-7" title="1주일">1주일</button> 
									<button type="button" class="btn date_search op-month-1" title="1개월">1개월</button> 
									<button type="button" class="btn date_search op-month-3" title="3개월">3개월</button> 
									<button type="button" class="btn date_search op-month-4" title="6개월">6개월</button>  
								</div>
								<button type="button" class="btn btn_search_min" title="조회">조회</button>
							</dd> 
						</dl> 
					</fieldset>
				</form:form>	 
			</div> <!-- // mypage_search_area E --> 
			<div class="btn_wrap pt10">
				<div class="btn_left">
					<button type="button" class="btn btn-m btn-default" onclick="location.href='/mypage/point'"><img src="/content/images/icon/icon_reset.png" alt="초기화">초기화</button>
				</div> 
			</div><!--// btn_wrap E--> 
			
			<p class="point_nitoce">고객님의 소멸예정 ${op:message('M00246')}는 <span class="point">1,200P</span>입니다. <span class="sub">(다음달에 소멸예정인 ${op:message('M00246')}의 총합이며, 소멸은 일단위로 진행됩니다.)</span></p>
		
			<div class="board_wrap">
	 			<table cellpadding="0" cellspacing="0" class="board-list">
		 			<caption>${op:message('M00246')}</caption>
		 			<colgroup>
		 				<col style="width:170px;">
		 				<col style="width:auto;">
		 				<col style="width:170px;">
		 				<col style="width:170px;">
		 			</colgroup>
		 			<thead>
		 				<tr>
		 					<th scope="col">날짜</th>
		 					<th scope="col">적립/사용 내역</th>
		 					<th scope="col">적립/사용 개수</th>
		 					<th scope="col">소멸예정일</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 				<c:forEach items="${ list }" var="point">
		 				
		 					<c:set var="color">
		 						<c:choose>
		 							<c:when test="${point.sign == '-'}">color_4a4a4a</c:when>
		 							<c:otherwise>color_23ade3</c:otherwise>
		 						</c:choose>
		 					</c:set>
		 				
		 					<tr>
			 					<td class="date"><b>${ op:date(point.createdDate) }</b></td> 
			 					<td>${point.reason}</td>
			 					<td class="${color}"><b>${point.sign}${op:numberFormat(point.point)}</b></td>
			 					<td>${op:date(point.expirationDate)}</td>
			 				</tr>
		 				</c:forEach>
		 			</tbody>
		 		</table><!--//view E-->	
		 		
		 		<page:pagination-seo /> 	 		
				
		 		<div class="guide_box mt20">
				 	<ul class="order_guide02">
				 		<li>${op:message('M00246')}는 ${op:message('M00246')}를 지급하는 상품을 구매하신 후 구매확정 된 이후에 자동으로 적립 됩니다.</li>
						<li>결제 시 ${op:message('M00246')} 가용금액을 확인하신 후 잔여 ${op:message('M00246')}를 100원단위로 기입하여 결제하실 수 있습니다.</li>
						<li>${op:message('M00246')}는 현금과 동일하게 결제시 사용하실 수 있으며 유효기간은 적립일로부터 1년입니다.</li>
					</ul>	
				</div><!--//guide_box E-->
				
		 	</div><!-- // board_wrap E -->
		</div>
	</div><!-- // contents E -->	
</div><!--// inner E-->