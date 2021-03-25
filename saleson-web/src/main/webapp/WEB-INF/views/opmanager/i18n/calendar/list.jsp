<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<style type="text/css">
	.day_border td {height:150px; border-left: 1px solid #d5d5d5; border-right: 1px solid #d5d5d5; border-bottom: 1px solid #d5d5d5; vertical-align: top;}
	.calendar_day td div {padding: 8px;}
	.calendar_day td div span {padding-top: 5px;}
</style>

	
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
		
	<!-- 본문 -->
	<h3><span>${op:message('M00564')} <!-- 캘린더 설정 --> </span></h3>
	<div class="calendar_date">
		<ul>
			<li class="prev"><a href="javascript:;" ><img src="/content/opmanager/images/common/month_prev.png" alt="이전달" /></a></li>
			<li class="title">${year} / ${month+1}</li>
			<li class="next"><a href="javascript:;" ><img src="/content/opmanager/images/common/month_next.png" alt="다음달" /></a></li>
		</ul>
	</div>
	
	<div class="calendar_day">
		<table cellspacing="0" cellpadding="0">
			 
			<table> 
				<table  cellspacing="0" cellpadding="0">
				 <tr class="board_list_table02">
				  <th style="width: 13%;"><font color="e10000">日</font></th>
				  <th style="width: 13%;">月</th>
				  <th style="width: 13%;">火</th>
				  <th style="width: 13%;">水</th>
				  <th style="width: 13%;">木</th>
				  <th style="width: 13%;">金</th>
				  <th style="width: 13%;"><font color="#3586f3">土</font></th> 
				 </tr>
					<tr class="day_border">
				 <!-- 달력 을 출력 하기 위한 기본 변수 설정 : newLine td를 채우기 위한 변수 선언, color 일요일 색상을 채우기 위한 변수 선언 -->
				 <c:set var="newLine" value="0"></c:set>
				 <c:set var="lineCount" value="0"></c:set>
				 <c:set var="color" value="0"></c:set>				  
				 <c:set var="today" value="0"></c:set>
				 
				  <c:forEach begin="1" end="${start-1}" varStatus="index" >
				 	<td>&nbsp;</td>
				 	<c:set var="newLine" value="${index.count }" />
				  </c:forEach>
		
				  <!-- 공백이 아닌 일에 대한 값을 출력 하기 위한 작업  -->
				  <c:forEach begin="1" end="${endDay}" varStatus="index">
				   	<c:set var="newLine2" value="${newLine+index.count }"></c:set>
				   	<c:set var="newLine3" value="${newLine+1+index.count }"></c:set>
				   		<c:choose>
				 		<c:when test="${newLine2%7 == 1 }">
				 			<c:set var="color"   value="RED"></c:set>
				 		</c:when>
				 		<c:when test="${newLine3%7 == 1 }">
				 			<c:set var="color"   value="#3586f3"></c:set>
				 		</c:when>
				 		<c:otherwise>
				 			<c:set var="color"  value="BLACK"></c:set>
				 		</c:otherwise>
				 	</c:choose>
				 		
							 
					
					
				 	<c:set var="indexCount">${index.count}</c:set>
				 	<c:set var="stateCode"  value=""></c:set>
				 	<c:set var="isGetList" value="0"></c:set>
				 	<c:set var="indexText" value=""></c:set>
				 	
				 	<c:if test="${date == index.count}">
				 		<c:set var="color"   value="GREEN" />
						<c:set var="indexText" value="TODAY" />
				 	</c:if>
				 	
				 	
					<td class="day"> 
						<div>
							<c:choose>		 	
						 		<c:when test="${not empty calendarList[indexCount] }">
							 		<c:if test="${calendarList[indexCount] == 0}">
								 		<c:set var="color"   value="RED" />
								 		<c:set var="indexText" value="${op:message('M00567')}" /> <!-- 공휴일 -->
								 	</c:if>
								 	<c:if test="${calendarList[indexCount] == 1}">
								 		<c:set var="color"   value="#3586f3" />
								 		<c:set var="indexText" value="${op:message('M01481')}" /> <!-- 영업 단축일 -->
								 	</c:if>
								 </c:when>
							</c:choose>
				 			<font color="${color}"><a href="javascript:;">${index.count}</a></font>
				 			 <br/><span style="color : ${color}">${indexText}</span> 
			 			 </div>
			 		</td>				 					 	
				 	
				 	<c:if test="${newLine2%7 == 0 }">
				 		</tr>
				 		<c:set var="newLine3" value="0"></c:set>
				 	</c:if>
					
				 	<c:if test="${newLine2%7 == 0 && (newLine2 - newLine) != endDay}">
			 			<c:set var="lineCount" value="${lineCount+1 }"></c:set>
			 			<tr class="day_border">
			 		</c:if>
			 		
			 		
			 		
				  </c:forEach>
		
				  <!-- 일에 대한 나머지 공백 부를 처리 하기 위한 작업  -->
				  <c:if test="${newLine2%7 > 0 && newLine2%7 < 7 }">
					  <c:forEach begin="${ newLine2%7 }" end="${6}" varStatus="index">
					  	<td>&nbsp;</td>
					  </c:forEach>
					  </tr>
				  </c:if>
				  <c:if test="${lineCount == 4 }">
					  <tr class="day_border">
						  <c:forEach begin="0" end="${6}" varStatus="index">
						  	<td>&nbsp;</td>
						  </c:forEach>
					  </tr>
				  </c:if>
			 	</table>
			</table>
		</table>
	</div>
	
<script tpye="text/javascript">
	$(function(){
		dayClick();
		prveAndNext();
	});
	
	function dayClick(){
		$(".day > div > font").on("click",function(){
			var date = "${year}-${month+1}-"+$(this).text().trim();
			Common.popup(url("/opmanager/calendar/popup/"+date), 'reject', 460, 300);
		});
	}
	
	function prveAndNext() { 
		var year = '${year}';
		var month = '${month+1}'; 
		
		$(".prev").on("click",function(){
			
			if (month == 1) {
				year = parseInt(year)-1;
				month = 11;
			}else {
				month = parseInt(month)-2;
			}
			
			location.href="/opmanager/calendar/list?calendarYear="+year+"&calendarMonth="+month;
		});
		
		$(".next").on("click",function(){
			
			if (month == 12) {
				year = parseInt(year)+1;
				month = 0;
			}else {
				month = parseInt(month);
			}
			
			location.href="/opmanager/calendar/list?calendarYear="+year+"&calendarMonth="+month;
			
		});
	}
	
</script>		  
