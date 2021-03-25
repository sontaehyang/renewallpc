<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
  google.load("visualization", "1.1", {packages:["bar"]});
  google.setOnLoadCallback(drawStuff);

  function drawStuff() {
    var data = new google.visualization.arrayToDataTable([
      ['', '${growth.lastYear}년', '${growth.nowYear}년']
      <c:forEach items="${ growth.list }" var="item" varStatus="i">
			
			<c:set var="lastData" value="0" />
			<c:set var="nowData" value="0" />
			<c:set var="month" />
			<c:forEach items="${item}" var="map">
				
				<c:if test="${fn:substring(map.key, 0, 4) == growth.lastYear}">
					<c:set var="month">${fn:substring(map.key, 4, 6)}월</c:set>
					<c:set var="lastData">${map.value}</c:set>
				</c:if> 
				<c:if test="${fn:substring(map.key, 0, 4) == growth.nowYear}">
					<c:set var="nowData">${map.value}</c:set>
				</c:if> 
			</c:forEach>
			
			,['${month}', ${lastData}, ${nowData}]
		</c:forEach>
		 
    ]);

    var options = {
      bars: 'horizontal', // Required for Material Bar Charts.
      series: {
        0: { axis: 'distance' }, // Bind series 0 to an axis named 'distance'.
        1: { axis: 'distance' } // Bind series 1 to an axis named 'brightness'.
      },
      axes: {
        x: {
          distance: {label: ''}, // Bottom x-axis.
          brightness: {side: 'top'} // Top x-axis.
        }
      }
    };

  var chart = new google.charts.Bar(document.getElementById('dual_x_div'));
  chart.draw(data, options);
};
</script>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">판매율통계</a> &gt; <a href="#" class="on">상품별 집계</a> 
</div>
<div class="statistics_web">
	<h3><span>신장률</span></h3>
	
	<form:form modelAttribute="statisticsParam" method="get" >
		<div class="board_write">						
			<table class="board_write_table" summary="${op:message('M01346')}">
				<caption>${op:message('M01346')}</caption><!-- 상품별 집계 -->
				<colgroup>
					<col style="width: 10%;">
					<col style="width: auto;"> 
				</colgroup>
				<tbody>
					<tr>
						<td class="label">기준 년도</td>
						<td>
					 		<div> 
								<form:select path="searchYear">
									<c:forEach begin="2015" end="${year}" step="1" var="i">
										<form:option value="${i}">${i}</form:option>
									</c:forEach>
								</form:select>
							</div> 
				 		</td>
					</tr>
				</tbody>
			</table>
			
		</div> <!-- // board_write -->
		
		<div class="btn_all">
			<div class="btn_right">
				<a href="/opmanager/shop-statistics/sales/growth/excel-download?${queryString}"  class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>${op:message('M00254')}</span> </a> <!-- 엑셀 다운로드 -->
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button><!-- 검색 -->
			</div>
		</div>
	
	</form:form>
	
	<div class="sort_area mt30">
	</div>
	
	<div id="dual_x_div" style="width: 100%; height: 400px;"></div>
	
	<div class="board_list" style="overflow-y: auto;">
		
		<table class="board_list_table">
			<colgroup>
				<col style="width:17.5%">
				<col style="width:17.5%">
				<col style="width:17.5%">
				<col style="width:17.5%">
				<col style="width:20%">
			</colgroup>
			<thead>
				<tr>
					<th colspan="2" class="border_left">${growth.lastYear}</th>
					<th colspan="2" class="border_left">${growth.nowYear}</th>
					<th rowspan="2" class="border_left">
						((${growth.nowYear} 매출 - ${growth.lastYear} 매출) / ${growth.lastYear} 매출) * 100 <br/>= 신장률(%)
					</th>
				</tr>
				<tr>
					<th class="border_left">일자</th>
					<th class="border_left">매출액</th>
					<th class="border_left">일자</th>
					<th class="border_left">매출액</th>
					
				</tr> 
			</thead>
			<tbody>
				<c:set var="lastDataTotal" value="0" />
				<c:set var="nowDataTotal" value="0" />
				<c:forEach items="${ growth.list }" var="item">
					<tr> 
						<c:set var="lastDate" />
						<c:set var="lastData" />
						<c:set var="nowDate" />
						<c:set var="nowData" />
						
						<c:forEach items="${item}" var="map">
							<c:if test="${fn:substring(map.key, 0, 4) == growth.lastYear}">
								<c:set var="lastDate">${map.key}</c:set>
								<c:set var="lastData">${map.value}</c:set>
							</c:if> 
							<c:if test="${fn:substring(map.key, 0, 4) == growth.nowYear}">
								<c:set var="nowDate">${map.key}</c:set>
								<c:set var="nowData">${map.value}</c:set>
							</c:if>
						</c:forEach>
						
						<c:set var="lastDataTotal">${lastDataTotal + lastData}</c:set>
						<c:set var="nowDataTotal">${nowDataTotal + nowData}</c:set>
						 
						<td>${lastDate}</td>
						<td>${op:numberFormat(lastData)}원</td>
						<td>${nowDate}</td>
						<td>
							<c:if test="${not empty nowData}">
								${op:numberFormat(nowData)}원
							</c:if>
						</td>
						<td>	
							<c:if test="${not empty nowData}">
								${op:getGrowthPercent(nowData, lastData)}
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<tr style="background: #CAC6C6;">
					<td>${growth.lastYear} 매출 합계</td>
					<td>${op:numberFormat(lastDataTotal)}원</td>
					<td>${growth.nowYear} 매출 합계</td>
					<td>${op:numberFormat(nowDataTotal)}원</td>
					<td>${op:getGrowthPercent(nowDataTotal, lastDataTotal)}</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>