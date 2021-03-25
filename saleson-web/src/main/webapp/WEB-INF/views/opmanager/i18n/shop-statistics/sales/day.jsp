<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

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
						<td class="label">소계 노출</td>
						<td>
							<div>
								<form:radiobutton path="displaySubtotal" value="N" label="비노출" checked="checked" />
								<form:radiobutton path="displaySubtotal" value="Y" label="노출" /> 
							</div>
						</td>
					</tr>
					<%-- 
					<tr>
						<td class="label">판매자</td>
						<td>
							<div>
								<form:select path="sellerId">
									<form:option value="0">${op:message('M00039')}</form:option>
									<c:forEach items="${sellerList}" var="list" varStatus="i">
										<form:option value="${list.sellerId}">[${list.loginId}] ${list.sellerName}</form:option>
									</c:forEach>
								</form:select>
								<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-gradient btn-xs">검색</a>
							</div>
						</td>
					</tr>
					--%>
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
			<span>${op:message('M01363')} : <span class="font_b">${op:numberFormat(total.totalCount)}</span>${op:message('M00272')} (${op:message('M01364')} : ${op:numberFormat(total.saleCount)}${op:message('M00272')}, ${op:message('M01365')} : ${op:numberFormat(total.cancelCount)}${op:message('M00272')})</span> | <span>${op:message('M01366')} : <span class="font_b">${op:numberFormat(total.totalPayAmount)}</span>${op:message('M00814')}</span>
		</div>
	</div>
	
	<div class="board_list">
		
		<c:set var="viewType" value="day" scope="request" /> 
		<jsp:include page="../include/date-list.jsp" />
		
		<c:if test="${empty dateList}">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>

		<sec:authorize access="hasRole('ROLE_EXCEL')">
			<div class="btn_all">
				<div class="right">
					<a href="/opmanager/shop-statistics/sales/day/excel-download?${queryString}" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
				</div>
			</div>
		</sec:authorize>
	</div>

	
</div>

<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});
	
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>