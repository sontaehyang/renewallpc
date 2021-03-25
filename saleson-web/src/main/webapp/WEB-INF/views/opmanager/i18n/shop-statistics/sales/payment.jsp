<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<style type="text/css">
.board_list_table td {
	border:1px solid #ECECE1;
}
</style>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">매출통계</a> &gt; <a href="#" class="on">일자별 매출</a>
</div>
<!-- 본문 -->
<div class="statistics_web">
	<h3><span>${op:message('M01376')}</span></h3> <!-- 일자별 매출 -->
	<form:form modelAttribute="statisticsParam" method="get" >
		<div class="board_write">						
			<table class="board_write_table" summary="${op:message('M01376')}">
				<caption>${op:message('M01376')}</caption>
				<colgroup>
					<col style="width: 140px;">
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
						<td class="label">결제타입</td>
						<td>
							<div>
								<form:checkboxes items="${approvalTypes}" path="approvalTypes" itemLabel="label" itemValue="key.id" />
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
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>
	</form:form>
	
	<div class="sort_area mt30">
		<div class="left">
			
		</div>
	</div>
	
	<div class="board_list" style="overflow-x:auto">
		
		<c:set var="totalPayCount">0</c:set>
		<c:set var="totalCancelCount">0</c:set>
		<c:set var="totalPayAmount">0</c:set>
		<c:set var="totalCancelAmount">0</c:set>
		<c:forEach items="${list}" var="item">
			<c:set var="totalPayCount">${totalPayCount + item.payCount}</c:set>
			<c:set var="totalCancelCount">${totalCancelCount + item.cancelCount}</c:set>
			<c:set var="totalPayAmount">${totalPayAmount + item.payAmount}</c:set>
			<c:set var="totalCancelAmount">${totalCancelAmount + item.cancelAmount}</c:set>	
		</c:forEach>
		
		<table class="board_list_table">
			<colgroup>
				<col style="width: 20%;">
				<c:forEach items="${approvalTypes}" var="type">
					<col style="width: ${80 / fn:length(approvalTypes)}%;"> 
				</c:forEach>
			</colgroup>
			<thead>
				<tr>
					<th>총 매출금액</th>
					<c:forEach items="${approvalTypes}" var="type">
						<th class="border_left">${type.label}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${op:numberFormat(totalPayAmount - totalCancelAmount)}원</td>
					<c:forEach items="${approvalTypes}" var="type">
						<td>
							<c:set var="value">0</c:set>
							<c:forEach items="${list}" var="item">
								<c:if test="${item.approvalType == type.key.id}">
									<c:set var="value">${value + (item.payAmount - item.cancelAmount)}</c:set>
								</c:if>
							</c:forEach>
							${op:numberFormat(value)}원
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
		<c:if test="${not empty list }">
		<table class="board_list_table" >
			<colgroup>
				<col style="width: 8%;">
				<col style="width: 10%;">
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2" class="border_left">날짜</th>
					<th rowspan="2" class="border_left">매출총액</th>
					<c:forEach items="${approvalTypes}" var="type">
						<th colspan="3" class="border_left">${type.label}</th>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${approvalTypes}" var="type">
						<th class="border_left">PC</th>
						<th>모바일</th>
						<th>기타</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${days}" var="day">
					<c:set var="dayTotal">0</c:set>
					<c:forEach items="${list}" var="item">
						<c:if test="${day == item.payDate}">
							<c:set var="dayTotal">${dayTotal + (item.payAmount - item.cancelAmount)}</c:set>
						</c:if>
					</c:forEach>
					
					<tr>
						<td>
							${op:date(day)}
						</td>
						<td class="text-right">
							<c:choose>
								<c:when test="${dayTotal == 0}">
									<span style="color:#bfbebe">0원</span>
								</c:when>
								<c:when test="${dayTotal < 0 }">
									<strong style="color:red">${op:numberFormat(dayTotal)}</strong>원
								</c:when>
								<c:otherwise>
									<strong style="color:blue">${op:numberFormat(dayTotal)}</strong>원
								</c:otherwise>
							</c:choose>
						</td>
							
						<c:forEach items="${approvalTypes}" var="type">
							<td class="text-right">
								<c:set var="value">0</c:set>
								<c:forEach items="${list}" var="item">
									<c:if test="${item.approvalType == type.key.id && day == item.payDate && item.deviceType == 'WEB'}">
										<c:set var="value">${value + (item.payAmount - item.cancelAmount)}</c:set>
									</c:if>
								</c:forEach>
								<c:choose>
									<c:when test="${value == 0}">
										<span style="color:#bfbebe">0원</span>
									</c:when>
									<c:otherwise>
										<strong>${op:numberFormat(value)}</strong>원
									</c:otherwise>
								</c:choose>
							</td>
							<td class="text-right">
								<c:set var="value">0</c:set>
								<c:forEach items="${list}" var="item">
									<c:if test="${item.approvalType == type.key.id && day == item.payDate && item.deviceType == 'MOBILE'}">
										<c:set var="value">${value + (item.payAmount - item.cancelAmount)}</c:set>
									</c:if>
								</c:forEach>
								<c:choose>
									<c:when test="${value == 0}">
										<span style="color:#bfbebe">0원</span>
									</c:when>
									<c:otherwise>
										<strong>${op:numberFormat(value)}</strong>원
									</c:otherwise>
								</c:choose>
							</td> 
							<td class="text-right">
								<c:set var="value">0</c:set>
								<c:forEach items="${list}" var="item">
									<c:if test="${item.approvalType == type.key.id && day == item.payDate && !(item.deviceType == 'WEB' || item.deviceType == 'MOBILE')}">
										<c:set var="value">${value + (item.payAmount - item.cancelAmount)}</c:set>
									</c:if>
								</c:forEach>
								<c:choose>
									<c:when test="${value == 0}">
										<span style="color:#bfbebe">0원</span>
									</c:when>
									<c:otherwise>
										<strong>${op:numberFormat(value)}</strong>원
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
				</c:forEach> 
			</tbody>
		</table>
		</c:if>
		<c:if test="${empty list }">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>

		<sec:authorize access="hasRole('ROLE_EXCEL')">
			<div class="btn_all">
				<div class="right">
					<%-- <a href="/opmanager/shop-statistics/sales/payment/excel-download?${queryString}"  class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 --> --%>
					<a href="javascript:excelDownload();"  class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
				</div>
			</div>
		</sec:authorize>
		
	</div>

	
</div>

<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});
	
	function excelDownload() {
		var param = $('#statisticsParam').serialize();
		location.href="/opmanager/shop-statistics/sales/payment/excel-download?"+param;
	}
</script>