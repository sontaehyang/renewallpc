<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

    <h3><span>이벤트 코드 통계</span></h3>

    <form:form modelAttribute="param" method="get">
        <form:hidden path="page" />

		<div class="board_write">
			<table class="board_write_table">
				<caption>${op:message('M00675')}</caption>
				<colgroup>
					<col style="width:150px;" />
					<col />
				</colgroup>
				<tbody>
					<tr>
					    <td class="label">검색 기간</td>
					 	<td>
					 		<div>
							    <span class="datepicker"><form:input path="beginDate" class="datepicker" maxlength="8" title="검색 시작" /></span>
							    <span class="wave">~</span>
							    <span class="datepicker"><form:input path="endDate" class="datepicker" maxlength="8" title="검색 종료" /></span>
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
				</tbody>
			</table>
		</div> <!-- // board_write -->

		<!-- 버튼시작 -->	
		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/evnet-statistics/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>		 
		<!-- 버튼 끝-->
		
		
		<div class="count_title mt20">
			<h5>전체 : ${op:numberFormat(pageContent.totalElements)}건</h5>
			<span>
				${op:message('M00052')} : 
				<form:select path="size" title="${op:message('M00239')}"> <!-- 화면출력 -->
					<form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 --> 
					<form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
					<form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 --> 
				</form:select>
			</span>
		</div>
	</form:form>


	<div class="board_list">
		<table class="board_list_table" summary="이벤트 통계">
			<caption>이벤트 통계</caption>
			<colgroup>
				<col style="width:auto;" />
				<col style="width:auto;" />
				<col style="width:auto;" />
			</colgroup>
			<thead>
			<tr>
				<th scope="col">방문수</th>
				<th scope="col">구매률(%)</th>
				<th scope="col">회원 가입률(%)</th>
			</tr>
			</thead>
			<tbody>
				<tr>
					<td>${op:numberFormat(totalStatistics.visitCount)}</td>
					<td>${op:numberFormat(totalStatistics.orderRate)}%</td>
					<td>${op:numberFormat(totalStatistics.joinRate)}%</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="board_list">
		<table class="board_list_table" summary="이벤트 통계"> <!-- 주문내역 리스트 -->
			<caption>이벤트 통계</caption>
			<colgroup>
				<col style="width:60px;" />
				<col style="width:200px;" />
				<col style="width:auto;" />
				<col style="width:100px;" />
				<col style="width:100px;" />
				<col style="width:100px;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">순번</th>
					<th scope="col">이벤트 코드</th>
					<th scope="col">이벤트 내용</th>
					<th scope="col">방문수</th>
					<th scope="col">구매률(%)</th>
					<th scope="col">회원 가입률(%)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="list" varStatus="i">
					<tr>
						<td>${op:numbering(pageContent, i.index)}</td>
						<td>${empty list.eventCode ? "-" : list.eventCode}</td>
						<td class="left">${list.contents}</td>
						<td>${op:numberFormat(list.visitCount)}</td>
						<td>${op:numberFormat(list.orderRate)}%</td>
						<td>${op:numberFormat(list.joinRate)}%</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<c:if test="${empty list}">
		<div class="no_content">
			${op:message('M00473')} <!-- 데이터가 없습니다. -->
		</div>
	</c:if>

    <page:pagination-jpa />

<script type="text/javascript">
$(function() {
	
	// 데이터 출력 수 설정.
	$('#size').on("change", function(){
	    $('#page').val("1");
		$('#faqDto').submit();
	});

	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="beginDate"]' , 'input[name="endDate"]');
});
</script>