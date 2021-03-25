<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

	<h3><span>기부현황통계</span></h3>
	<div class="proposal_synthesize">
		
	</div>
	
	<h4>개인별 참여 현황(직원)</h4>
	<div class="board_list employee">
	</div>
	
	<h4>개인별 참여 현황(직원-희망별)</h4>
	<div class="board_list star_point">
	</div>
		
<script type="text/javascript">

var employeeOrder = '';
var employeeSort  = '';
var employeeStarOrder = '';
var employeeStarSort = '';

$(function(){
	proposalStatisticsSubmit();
	pagination();
	pagination2();
	
});

function proposalStatisticsSubmit(){
	var params = {  
			'donationYear' : $("select[name=donationYear]").val(),
			'userType' : $("input:radio[name='userType']:checked").val()
	};
	
	$.post('/opmanager/proposal-statistics/proposal-synthesize', params, function(html) {
		$(".proposal_synthesize").html(html);

		setHeight();
	});
}

function pagination(page,order,sort){
	var searchYear = $("#employeeSearchYear").val() == undefined ? '' : $("#employeeSearchYear").val();
	var searchMonth = $("#employeeSearchMonth").val() == undefined ? '' : $("#employeeSearchMonth").val();
	
	page = page == undefined ? 1 : page;
	page = page == '' ? 1 : page;
	order = order == undefined ? '' : order.toUpperCase();
	sort = sort == undefined ? '' : sort.toUpperCase();

	employeeOrder = order;
	employeeSort  = sort;

	$.post('/opmanager/proposal-statistics/proposal-employee', $("#proposalEmployeeStatisticsSearchParam").serialize()+'&searchYear='+searchYear
			+ '&userType=3&searchMonth='+searchMonth+ '&page=' + page+ '&order=' + order+ '&sort=' + sort
			, function(html) {
		$(".employee").html(html);

		setHeight();
	});

	return false;
}

function pagination2(page,order,sort){
	var searchYear = $("#satrPointSearchYear").val() == undefined ? '' : $("#satrPointSearchYear").val();
	var searchMonth = $("#starPointSearchMonth").val() == undefined ? '' : $("#starPointSearchMonth").val();
	
	page = page == undefined ? 1 : page;
	page = page == '' ? 1 : page;
	order = order == undefined ? '' : order.toUpperCase();
	sort = sort == undefined ? '' : sort.toUpperCase();

	employeeStarOrder = order;
	employeeStarSort  = sort;

	$.post('/opmanager/proposal-statistics/proposal-starpoint', $("#proposalStarPointStatisticsSearchParam").serialize()+'&searchYear='+searchYear
			+ '&userType=2&searchMonth='+searchMonth+ '&page=' + page+ '&order=' + order+ '&sort=' + sort
			, function(html) {
		$(".star_point").html(html);

		setHeight();
	});

	return false;
}

function fn_employeeExcelDownload(){
	
	var searchYear = $("#employeeSearchYear").val() == undefined ? '' : $("#employeeSearchYear").val();
	var searchMonth = $("#employeeSearchMonth").val() == undefined ? '' : $("#employeeSearchMonth").val();

	
	location.href = '/opmanager/proposal-statistics/proposal-employee-excel?'+$("#proposalEmployeeStatisticsSearchParam").serialize()+'&searchYear='+searchYear
	+ '&userType=3&searchMonth='+searchMonth+ '&order=' + employeeOrder+ '&sort=' + employeeSort;
};

function fn_employeeStarExcelDownload(){
	
	var searchYear = $("#satrPointSearchYear").val() == undefined ? '' : $("#satrPointSearchYear").val();
	var searchMonth = $("#starPointSearchMonth").val() == undefined ? '' : $("#starPointSearchMonth").val();

	
	location.href = '/opmanager/proposal-statistics/proposal-employee-starpoint-excel?'+$("#proposalStarPointStatisticsSearchParam").serialize()+'&searchYear='+searchYear
	+ '&userType=2&searchMonth='+searchMonth+ '&order=' + employeeStarOrder+ '&sort=' + employeeStarSort;
};

</script>


