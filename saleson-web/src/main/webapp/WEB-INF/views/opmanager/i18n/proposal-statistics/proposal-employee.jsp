<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>


<div class="sort_area">
	<form:form modelAttribute="proposalEmployeeStatisticsSearchParam" onsubmit="return pagination();">
	<fieldset>
		<legend class="hidden">검색</legend>
		<div class="left">
			<p class="number">
				한 페이지에 <form:input path="itemsPerPage" maxlength="3"/> 개의 데이터를 보겠습니다.
			</p>
		</div>
		<div class="right">
			<span>본부</span>
			<span>
				<form:select path="localHqCode">
					<form:option value="">전체</form:option>
					<c:forEach items="${kbGroupTypeList}" var="list">
						<form:option value="${list.localHqCode}">${list.localHqName}</form:option>
					</c:forEach>
				</form:select>
			</span>
			<span>부점</span>
			<span class="deptNum">
				<form:select path="deptNum">
					<form:option value="">전체</form:option>
					<c:forEach items="${kbGroupDetailByIdLocalHqCode}" var="list">
						<form:option value="${list.deptNum}">${list.deptName}</form:option>
					</c:forEach>
				</form:select>
			</span>
			<span>직원</span>
			<form:input path="searchUserName" cssClass="optional _filter" title="직원" />
			<button type="submit"><img src="/content/images/common/icon_search.png" alt=""> 검색</button>
		</div>
	</fieldset>
	</form:form>
</div>

<div class="year_sort">
	<span>년도</span>
	<select name="searchYear" id="employeeSearchYear" title="검색 년도">
		<c:forEach begin="2013" end="${currentYear}" varStatus="i">
			<c:set var="selected" value="" />
			<c:if test="${proposalEmployeeStatisticsSearchParam.searchYear == i.index}">
				<c:set var="selected" value="selected='selected'" />
			</c:if>
			<option value="${i.index}" ${selected}>${i.index}</option>
		</c:forEach>
	</select>
	<select name="searchMonth" id="employeeSearchMonth" title="검색 월">
		<option value="">월 전체</option>
		<c:forEach begin="1" end="12" varStatus="i">
			<c:set var="selected" value="" />
			<c:if test="${proposalEmployeeStatisticsSearchParam.searchMonth == i.index}">
				<c:set var="selected" value="selected='selected'" />
			</c:if>
			<option value="${i.index}" ${selected}>${i.index}</option>
		</c:forEach>
	</select>
</div>

<table class="board_list_table">
	<colgroup>
		<col style="width: 50px;" />
	</colgroup>
	<thead>
		<tr>
			<th>순번</th>
			<th>본부</th>
			<th>부점</th>
			<th>이름</th>
			<th class="border_left"><a href="javascript:;" class="employee_sort sort" id="employee_proposal_donation_count">총기부횟수 <span class="asc">▲</span></a></th>
			<th><a href="javascript:;" class="employee_sort sort" id="employee_total_donation_amount">총기부금액 <span class="asc">▲</span></a></th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${!empty proposalStatistics}">
			<c:forEach items="${proposalStatistics}" var="list" varStatus="i">
				<tr>
					<td>${pagination.itemNumber - i.count}</td>
					<c:forEach items="${list.kbGroups}" var="kbGroups" varStatus="j" >
						<td>${kbGroups.localHqName}</td>
						<td>${kbGroups.deptName}</td>
					</c:forEach>
					<td>${list.userName}</td>
					<td class="border_left"><fmt:formatNumber value="${ list.proposalDonationCount}" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ list.totalDonationAmount}" pattern="#,###" /></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<c:if test="${empty proposalStatistics}">
	<div class="no_content">
		<p>검색된 내용이 없습니다.</p>
	</div>
</c:if>
<page:pagination-manager/>

<p class="btns">
	<a href="javascript:;" onclick="fn_employeeExcelDownload();" class="btn orange">엑셀파일 다운로드</a>
</p>

<script type="text/javascript">
$(function(){

	$("#proposalEmployeeStatisticsSearchParam").validator(function() {});

	kbGroupDeptNumChange();

	var order = '${proposalEmployeeStatisticsSearchParam.order }'.toLowerCase();
	var sort = '${proposalEmployeeStatisticsSearchParam.sort }'.toLowerCase();

	if (order != '') {
		$('.employee_sort').removeClass('on');
		$('#employee_'+order).find('span').removeClass();
		
		$('#employee_'+order).addClass('on');
		$('#employee_'+order).find('span').addClass(sort);

		if (sort == 'desc') {
			$('#employee_'+order).find('span').text('▼');
		}
	}

	
	$("#employeeSearchYear,#employeeSearchMonth").on("change",function(){
		pagination();
	});

	$(".employee_sort").click(function(){
		var $sort = $('.employee_sort');
		var $span = $(this).find('span');

		$sort.removeClass('on');
		$(this).addClass('on');
		
		$sort.find('span').text('▲');
		$sort.not($(this)).find('span').removeClass();
		$sort.not($(this)).find('span').addClass('asc');
		

		if ($span.attr('class') == 'asc') {
			$span.removeClass();
			$span.addClass('desc');
			$span.text('▼');
		} else if ($span.attr('class') == 'desc') {
			$span.removeClass();
			$span.addClass('asc');
			$span.text('▲');
		}

		
		pagination('',$(this).attr('id').replace('employee_',''),$span.attr('class'));
	});
});

function kbGroupDeptNumChange(){
	$("#localHqCode").on("change",function(){
		$.post("/opmanager/proposal-statistics/proposal-employee-kbGroupDeptNum","localHqCode="+$(this).val(),function(response){
			$(".deptNum").html(response);
		});
	});
}

</script>