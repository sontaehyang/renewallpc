<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<style>
.tbl-summary {
	border-right: 1px solid #dedede;
	border-bottom: 1px solid #dedede;
}
.tbl-summary th,
.tbl-summary td {
	border-left: 1px solid #dedede;
	border-top: 1px solid #dedede;
	text-align: center;
}

.tbl-summary th {
	background: #f4f4f4;
	padding: 10px;
	color: #000;
}
.tbl-summary td {
	padding: 0 10px;
	font-family: verdana;
}
.tbl-summary td {
	padding: 22px;
	font-size: 20px;
	font-family: verdana;
	
}
</style>

		<div class="statistics_web">
			<h3><span>접속통계</span></h3>
			<!-- 
			<p class="btns">
				<a href="#" class="btn_print"><img src="/content/images/opmanager/common/icon_print.png" alt="" /> 화면인쇄</a>
			</p>
			-->
			<div class="">
				<table class="tbl-summary">
					<thead>
						<tr>
							<th>${op:message('M00039')}</th> <!-- 전체 -->
							<th>${op:message('M00026')}</th> <!-- 오늘 -->
							<th>${op:message('M01116')}</th> <!-- 어제 -->
							<th>${op:message('M01117')}</th> <!-- 월별최대 -->
							<th>${op:message('M01118')}</th> <!-- 일별최대 -->
							<th>${op:message('M01119')}</th> <!-- 일별최소 -->
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${op:numberFormat(summary.TOTAL)}</td>
							<td>${op:numberFormat(summary.TODAY)}</td>
							<td>${op:numberFormat(summary.YESTERDAY)}</td>
							<td>${op:numberFormat(summary.MONTH_MAX)}</td>
							<td>${op:numberFormat(summary.DAY_MAX)}</td>
							<td>${op:numberFormat(summary.DAY_MIN)}</td>
						</tr>
					</tbody>
				</table>
				
				<div class="btn_all" style="margin-top: 20px">
				
					<form:form modelAttribute="searchParam" method="get">
						<fieldset>
							<legend class="hidden">${op:message('M00048')}</legend> <!-- 검색 -->
							
							<div class="left">
								<span>${op:message('M01120')} <!-- 검색기간 -->: </span>
								<form:select path="startDate">
									<c:forEach begin="2013" end="${currentYear}" var="year">
										<form:option value="${year}">${year}</form:option>
									</c:forEach>
								</form:select>
								<span class="wave">~</span>
								<form:select path="endDate">
									<c:forEach begin="2013" end="${currentYear}" var="year">
										<form:option value="${year}">${year}</form:option>
									</c:forEach>
								</form:select>
								<button type="submit" class="btn btn-dark-gray btn-sm" style="margin-left: 1%;"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
								<sec:authorize access="hasRole('ROLE_EXCEL')">
									<a href="/opmanager/stats/visit/download-excel" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>엑셀 다운로드</a> <!-- 엑셀 다운로드 -->
								</sec:authorize>
							</div>
						</fieldset>
					</form:form>
				</div>
			</div>
			
			<h4>${op:message('M01121')} <!-- 월별 접속통계 --></h4>
			<div class="chart_horizontal chart_detail">
				<ul>
					<c:forEach items="${list}" var="monthStats">
						<li>
							<p class="name"><a href="javascript:getDayStats('${monthStats.yearMonth}')">${monthStats.title}</a></p>
							<div class="bar_wrap">
								<p class="bar yellow" style="width: ${monthStats.percent}%">
									<c:choose>
										<c:when test="${monthStats.percent < 70.0 }">
											<span class="number" title="${op:numberFormat(monthStats.monthCount)} (${monthStats.percent}%)">${op:numberFormat(monthStats.monthCount)} (${monthStats.percent}%)</span>
										</c:when>
										<c:otherwise>
											<span class="number number_over" title="${op:numberFormat(monthStats.monthCount)} (${monthStats.percent}%)">${op:numberFormat(monthStats.monthCount)} (${monthStats.percent}%)</span>
										</c:otherwise>
									</c:choose>ß
									
									<span class="tip"></span>
								</p>
							</div>
							
						</li>
					</c:forEach>
				</ul>
				<c:if test="${empty list}">
					<div class="no_content">${op:message('M00473')} <!-- 데이터가 없습니다. --></div>
				</c:if>
				<div class="bar_bg">
					<div class="grid">
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
					</div>
					<p class="label label_0">0</p>
					<p class="label label_50">50%</p>
					<p class="label label_100">100%</p>
				</div>
			</div>
			
			<div id="day_stats">
				<h4>
					${currentYear}${op:message('M01076')} <!-- 년 -->
					${currentMonth}${op:message('M01077')} <!-- 월 --> 
					${op:message('M01122')} <!-- 일별 접속통계 -->
				</h4>
				<div class="chart_vertical daily">
					<table>
						<tr>
							<c:forEach items="${dayStatsList}" var="dayStats">
								<td><div>
									<p class="name">${dayStats.displayDay}</p>
									<div class="bar_wrap">
										<p class="bar" style="height: ${dayStats.percent}%">
											<span class="number">${dayStats.percent}%<br />(${op:numberFormat(dayStats.visitCount)})</span>
										</p>
									</div>
								</div></td>
							</c:forEach>
						</tr>
					</table>
					<div class="bar_bg">
						<div class="grid">
							<div><p>100%</p></div>
							<div><p>80%</p></div>
							<div><p>60%</p></div>
							<div><p>40%</p></div>
							<div><p>20%</p></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
			
<script type="text/javascript">

function getDayStats(yearMonth) {
	$.get("/opmanager/stats/visit/day", {'yearMonth' : yearMonth}, function(response) {
		$('#day_stats').html(response);
	}, 'html');
	
}

</script>
	