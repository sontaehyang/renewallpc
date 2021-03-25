<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">매출통계</a> &gt; <a href="#" class="on">회원별 매출</a>
</div>
<div class="statistics_web">
	<h3><span>${op:message('M01407')}</span></h3> <!-- 회원별 매출 -->
	<form:form modelAttribute="statisticsParam" method="get" >
		<div class="board_write">						
			<table class="board_write_table" summary="${op:message('M01407')}"> <!-- 회원별 매출 -->
				<caption>${op:message('M01407')}</caption> <!-- 회원별 매출 -->
				<colgroup>
					<col style="width: 20%;">
					<col style="width: auto;"> 
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M01347')}</td> <!-- 기간 -->
						<td>
					 		<div> 
								<span class="datepicker"><form:input path="startDate" class="term datepicker" title="${op:message('M00507')}" id="dp28" /></span> <!-- 시작일 -->
								<span class="wave">~</span>
								<span class="datepicker"><form:input path="endDate" class="term datepicker" title="${op:message('M00509')}" id="dp29" /></span> <!-- 종료일 -->
								<span class="day_btns"> 
									<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 --> 
									<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 --> 
									<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 --> 
									<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 -->
									<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 --> 
									<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
								</span>
							</div> 
				 		</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00012')}</td> <!-- 검색조건 -->
						<td>
							<div>
								<form:select path="where" title="검색 조건">
									<form:option value="CUSTOMER_NAME">회원이름</form:option> <!-- 회원이름 -->
									<form:option value="LOGIN_ID">웹 회원 아이디</form:option> <!-- 회원아이디 -->
									<!-- <form:option value="ITEM_NAME">${op:message('M00018')}</form:option> --><!-- 상품명 -->
									<!-- <form:option value="ITEM_USER_CODE">${op:message('M00783')}</form:option> --><!-- 상품코드 -->
								</form:select>
								<form:input path="query" class="three" title="상세검색 입력" />
							</div> 
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00790')}</td>
						<td>
							<div>
								<form:radiobutton path="orderBy" label="${op:message('M01384')}" value="PRICE" /> <!-- 판매 금액 -->
								<form:radiobutton path="orderBy" label="${op:message('M01385')}" value="QUANTITY" /> <!-- 판매 수량 -->								
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M01386')}</td> <!-- 정렬 방법 -->
						<td>
							<div>
								<form:radiobutton path="sort" label="${op:message('M00689')}" value="DESC" /> <!-- 내림차순 -->
								<form:radiobutton path="sort" label="${op:message('M00690')}" value="ASC" /> <!-- 오름차순 -->					
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
		</div> <!-- // board_write -->
		
		
		
		<div class="btn_all">
			<!-- <div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
			</div> -->
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
			</div>
		</div>
	</form:form>
	
	
	<div class="sort_area mt30">
		<div class="left">
			<span>${op:message('M01363')} : <span class="font_b">${op:numberFormat(total.totalPayCount + total.totalCancelCount) }</span>${op:message('M00272')} (${op:message('M01364')} : ${ op:numberFormat(total.totalPayCount) }${op:message('M00272')}, ${op:message('M01365')} : ${op:numberFormat(total.totalCancelCount) }${op:message('M00272')})</span> | <span>${op:message('M01366')} : <span class="font_b">${op:numberFormat(total.totalRevenueAmount)}</span>${op:message('M00814')}</span>
		</div>
	</div>
	
	<div class="board_guide">
		<br/>
		<p class="tip">[안내]</p>
		<p class="tip">
			비회원(거래처)는 전화번호, 웹회원은 아이디(이메일)로 구분되어 있습니다.
		</p>  
	</div>
	
	<div class="board_list">
		
		<table class="board_list_table">
			<thead>
				<tr>
					<th rowspan="2">${op:message('M01356')} <br />(${op:message('M00081')})</th> <!-- 회원이름 --> <!-- 아이디 -->
					<th rowspan="2" class="border_left">${op:message('M01368')}</th> <!-- 주문방법 -->
					<th colspan="7" class="border_left">${op:message('M01355')}</th> <!-- 결제 -->
					<th colspan="7" class="border_left">${op:message('M00037')}</th> <!-- 취소 -->
					<th colspan="7" class="border_left">${op:message('M00064')}</th> <!-- 소계 -->
				</tr>
				<tr>
					<th class="border_left">${op:message('M01357')}</th> <!-- 건수 -->
					<th class="border_left">원가</th>
					<th class="border_left">${op:message('M00627')}</th> <!-- 상품금액 -->
					<th class="border_left">${op:message('M00246')}</th> <!-- 포인트 -->
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
					<th class="border_left">${op:message('M00067')}</th> <!-- 배송비 -->
					<th class="border_left">${op:message('M00069')}</th> <!-- 결제금액 -->
					
					<th class="border_left">${op:message('M01357')}</th> <!-- 건수 -->
					<th class="border_left">원가</th>
					<th class="border_left">${op:message('M00627')}</th> <!-- 상품금액 -->
					<th class="border_left">${op:message('M00246')}</th> <!-- 포인트 -->
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
					<th class="border_left">${op:message('M00067')}</th> <!-- 배송비 --> 
					<th class="border_left">${op:message('M01361')}</th> <!-- 취소액 -->
					
					<th class="border_left">원가</th>
					<th class="border_left">${op:message('M00627')}</th> <!-- 상품금액 -->
					<th class="border_left">${op:message('M00246')}</th> <!-- 포인트 -->
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
					<th class="border_left">${op:message('M00067')}</th> <!-- 배송비 -->
					<th class="border_left">${op:message('M01358')}</th> <!-- 매출액 -->
					<th class="border_left">이익률</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="list">
					<tr>
						<c:set var="action">Common.popup('/opmanager/shop-statistics/sales/user/detail?customerCode=${list.customerCode}&sd=${statisticsParam.startDate}&ed=${statisticsParam.endDate}', '${list.customerCode}', '1600', '800', 1);</c:set>
						
						<c:set var="action">javascript:;</c:set>
						<td rowspan="${fn:length(list.groupList) }">
							<a href="javascript:;" onclick="${ action }">
								<c:if test="${empty list.userName}">
									비회원
								</c:if>
								
								<c:if test="${not empty list.userName}">
									${list.userName}
									<c:choose>
										<c:when test="${not empty list.loginId}">(웹회원)</c:when>
										<c:otherwise>(거래처)</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${not empty list.loginId}">
											<br/>(${list.loginId})
										</c:when>
										<c:otherwise>
											<br/>(${list.customerTelNumber})
										</c:otherwise>
									</c:choose>
								</c:if> 
							</a>	
						</td>
						<c:forEach items="${ list.groupList }" var="item" varStatus="groupIndex">
							<c:if test="${groupIndex.index > 0}"></tr><tr></c:if>
							<td class="border_left">${item.osType}</td>
							<td class="border_left number">${op:numberFormat(item.payCount)}</td>
							<td class="border_left number">${op:numberFormat(item.costPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.itemPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.usePoint)}</td>
							<td class="border_left number">${op:numberFormat(item.discountAmount)}</td>
							<td class="border_left number">${op:numberFormat(item.deliveryPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.payTotal) }</td>
							<td class="border_left number">${op:numberFormat(item.cancelCount)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelCostPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelItemPrice) }</td>
							<td class="border_left number">${op:numberFormat(item.cancelUsePoint)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelDiscountAmount) }</td>
							<td class="border_left number">${op:numberFormat(item.cancelDeliveryPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelTotal) }</td>
							<td class="border_left number">${op:numberFormat(item.sumCostPrice) }</td>
							<td class="border_left number">${op:numberFormat(item.sumItemPrice) }</td>
							<td class="border_left number">${op:numberFormat(item.sumUsePoint) }</td>
							<td class="border_left number">${op:numberFormat(item.sumDiscountAmount) }</td>
							<td class="border_left number">${op:numberFormat(item.sumDeliveryPrice) }</td>
							<td class="border_left number">${op:numberFormat(item.sumTotalAmount) }</td>
							<td class="border_left number">${op:getRevenuePercent(item.sumItemPrice, item.sumCostPrice)}</td>
						</c:forEach>
					</tr>
					
					<tr style="background: #f2f2f2;">
						<td colspan="2">${op:message('M00064')}</td> <!-- 소계 -->
						<td class="border_left number">${op:numberFormat(list.totalPayCount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayCostPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayItemPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayUsePoint)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayDiscountAmount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayDeliveryPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPay)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelCount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelCostPrice)}</td> 
						<td class="border_left number">${op:numberFormat(list.totalCancelItemPrice) }</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelUsePoint)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelDiscountAmount) }</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelDeliveryPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancel)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayCostPrice + list.totalCancelCostPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayItemPrice + list.totalCancelItemPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayUsePoint + list.totalCancelUsePoint)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayDiscountAmount + list.totalCancelDiscountAmount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayDeliveryPrice + list.totalCancelDeliveryPrice)}</td> 
						<td class="border_left number">${op:numberFormat(list.totalPay + list.totalCancel)}</td>
						<td class="border_left number">${op:getRevenuePercent(list.totalPayItemPrice + list.totalCancelItemPrice, list.totalPayCostPrice + list.totalCancelCostPrice)}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<c:if test="${empty userList }">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>
		
		<page:pagination-manager />
		
		<div class="sort_area">
			<div class="right">
				<!-- a href="/opmanager/shop-statistics/sales/user/excel-download?${queryString}"  class="btn_write gray_small"><img src="/content/opmanager_image/icon/icon_excel.png" alt=""><span>${op:message('M00254')}</span> </a> <!-- 엑셀 다운로드 -->
			</div>
		</div>
		
		
		
		<!-- <div class="board_guide">
			<p class="tip">Tip</p>
			<p class="tip">- 회원이름(아이디)클릭하시면 상세내역을 조회하실 수 있습니다.</p>
				<p class="tip">- 조회기간은 3개월까지만 가능합니다.</p>
		</div> -->
		
	</div>

</div>

<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});
</script>