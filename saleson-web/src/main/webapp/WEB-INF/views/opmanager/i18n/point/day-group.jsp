<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<style type="text/css">
.board_list_table td {
	border : 1px solid #ececec;
}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3><span>일별 발생/사용현황</span></h3>

<form:form modelAttribute="pointParam" action="" method="get">
		
	<div class="board_write">
	
		<table class="board_write_table" summary="일별 발생/사용현황">
			<caption>일별 발생/사용현황</caption>
			<colgroup>
				<col style="width:150px;" />
				<col />
			</colgroup>
			<tbody>
				<tr>
					<td class="label">${op:message('M00011')}</td>
					<td>
						<div>
							<form:select path="where" title="${op:message('M00211')} ">
								<form:option value="LOGIN_ID" label="${op:message('M00081')}" /> <%-- 아이디 --%> 
								<form:option value="USER_NAME" label="${op:message('M00005')}" /> <%-- 이름 --%>
							</form:select>
							<form:input path="query" cssClass="optional seven _filter" title="${op:message('M00211')} " />
					 	</div>
					</td> 
				</tr> 
				<tr>
				 	<td class="label">일자</td>
				 	<td>
				 		<div>
							<span class="datepicker"><form:input type="text" path="searchStartDate" class="datepicker _number" title="${op:message('M00024')}" /></span> <%-- 시작일 --%> 
							<span class="wave">~</span>
							<span class="datepicker"><form:input type="text" path="searchEndDate" class="datepicker _number" title="${op:message('M00025')}" /></span> <%-- 종료일 --%> 
							<span class="day_btns"> 
								<a href="javascript:;" class="btn_date  clear">${op:message('M00039')}</a> <%-- 전체 --%>
								<a href="javascript:;" class="btn_date  today">${op:message('M00026')}</a> <%-- 오늘 --%> 
								<a href="javascript:;" class="btn_date  week-1">${op:message('M00027')}</a> <%-- 1주일 --%>  
								<a href="javascript:;" class="btn_date  day-15">${op:message('M00028')}</a> <%-- 15일 --%> 
								<a href="javascript:;" class="btn_date  month-1">${op:message('M00029')}</a> <%-- 한달 --%> 
								<a href="javascript:;" class="btn_date  month-3">${op:message('M00030')}</a> <%-- 3달 --%> 
								<a href="javascript:;" class="btn_date  year-1">${op:message('M00031')}</a> <%-- 1년 --%>
							</span>
						</div> 
				 	</td>
				 </tr>
			</tbody>
		</table>
		 
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/point/day-group/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}<!-- 검색 --></button>
			</div>
		</div>
	</div>
	
	<div class="count_title mt20">
		<h5>
			${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
		</h5>
		<span>
			<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"> <!-- 화면 출력수 -->
				<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="200" label="200${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="500" label="500${op:message('M00053')}" /> <!-- 개 출력 -->
			</form:select>
		</span>
	</div> 
</form:form>


 
	<form id="listForm">
		<table class="board_list_table" summary="일별 발생/사용현황">
			<colgroup>
				<col style="width:3%;" />
				<col />
				<col style="width:30%;" />
				<col style="width:30%;" />
			</colgroup>
			<caption>주문내역 리스트</caption>
			<thead>
				<tr>
					<th scope="col" rowspan="2">No</th>
					<th scope="col" rowspan="2">일자</th>
					<th scope="col" colspan="2">${op:message('M00246')}</th>
				</tr>
				<tr>
					<th scope="col">적립</th>
					<th scope="col">사용</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="item" varStatus="index">
					<tr>
						<td>${op:numberFormat(pagination.itemNumber - index.count)}</td>
						<td>${op:date(item.groupKey)}</td>
						
						<td class="text-right">
							<c:choose>
								<c:when test="${item.savedPoint == 0}">
									<span style="color:#bfbebe">0</span>
								</c:when>
								<c:otherwise>
									<strong>${op:numberFormat(item.savedPoint)}</strong>
								</c:otherwise>
							</c:choose>
						</td>
						<td class="text-right">
							<c:choose>
								<c:when test="${item.usedPoint == 0}">
									<span style="color:#bfbebe">0</span>
								</c:when>
								<c:otherwise>
									<strong>${op:numberFormat(item.usedPoint)}</strong>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>	
			</tbody> 
		</table>
		
		<c:if test="${empty list}">
		<div class="no_content">
			${op:message('M00473')} <!-- 데이터가 없습니다. --> 
		</div>
		</c:if>

		<sec:authorize access="hasRole('ROLE_EXCEL')">
			<div class="btn_all">
				<div class="btn_left">

				</div>
				<div class="btn_right">
					<a href="javascript:downloadExcel()" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save" aria-hidden="true"></span> 엑셀 다운로드</a>
				</div>
			</div>
		</sec:authorize>
		<page:pagination-manager /> 
	</form>
	

<script type="text/javascript"> 
$(function(){
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
})
function downloadExcel() {
	var param = $("#pointParam").serialize();
	location.href="/opmanager/point/day-group-download-excel?"+param;
}
</script>