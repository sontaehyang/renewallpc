<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<!-- 본문 -->
<div class="popup_wrap">
	<h1 class="popup_title">${op:message('M00565')} <!-- 공휴일/단축영업일 설정 --> </h1>
	<div class="popup_contents">
		<form id="calendarForm" method="post" action="/opmanager/calendar/create" >
			<input type="hidden" name="calendarYear" value="${calendarSearchParam.calendarYear}" />
			<input type="hidden" name="calendarMonth" value="${calendarSearchParam.calendarMonth}" />
			<input type="hidden" name="calendarDay" value="${calendarSearchParam.calendarDay}" />
			<div class="calendar_popup">
				<p class="info">
					<span>
						${calendarSearchParam.calendarYear}${op:message('M01076')} <!-- 년 --> 
						${calendarSearchParam.calendarMonth}${op:message('M01077')} <!-- 월 -->
						${calendarSearchParam.calendarDay}${op:message('M00511')} <!-- 일 -->
					</span> 
					${op:message('M00566')} <!-- 휴일 또는 단축 영업일을 선택하세요. --> </p>
				<div class="calendar_popup_radio">
					<input type="radio" name="hday" id="holiday01" class="required" value="0" ${op:checked(calendar.hday,'0') } title="공휴일/단축영업일 설정" > <label for="holiday01">${op:message('M00567')} <!-- 공휴일 --> </label>
					<input type="radio" name="hday" id="holiday02" class="required" value="1" ${op:checked(calendar.hday,'1') } title="공휴일/단축영업일 설정" > <label for="holiday02">${op:message('M00568')} <!-- 단축 영업일 --> </label>
					<input type="radio" name="hday" id="holiday03" class="required" value=""  title="공휴일/단축영업일 설정" > <label for="holiday03">${op:message('M00801')} <!-- 없음 --> </label> 
				</div>
			</div>
			
			<p class="btn_center">
				<button type="submit" class="btn btn-dark-gray btn-sm">${op:message('M00101')} <!-- 저장 --> </button>
				<a href="#" class="popup_close">${op:message('M00569')} <!-- 닫기 --> </a>
			</p>
		</form>
	</div>
	<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
</div>
<script type="text/javascript">
	$(function(){
		$("#calendarForm").validator(function() {});	
	});
</script>