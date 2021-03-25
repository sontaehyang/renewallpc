<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<jsp:include page="./include/content-head.jsp"/>

<div class="chart-view">
	<div id="chartView"></div>
</div>

<div class="board_list" id="reportTable">
	<table class="board_list_table">
		<colgroup>
			<col style="width:150px;" />
			<col style="width:auto;" />
			<col style="width:auto;" />
			<col style="width:auto;" />
			<col style="width:auto;" />
		</colgroup>
		<thead>
		<tr>
			<th scope="col">날짜</th>
			<th scope="col">페이지 뷰</th>
			<th scope="col">이탈</th>
			<th scope="col">이탈률(%)</th>
			<th scope="col">세션당 페이지 뷰</th>
			<th scope="col">고유 페이지 뷰</th>
			<th scope="col">페이지 머문 시간(초)</th>
			<th scope="col">평균 페이지 머문 시간(초)</th>
		</tr>
		</thead>
		<tbody id="data-list">

		</tbody>
	</table>
	<div class="no_content list_no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. -->
	</div>
	<jsp:include page="./include/content-pagination.jsp"/>
</div>



<br/>
<h3><span>가장 많이 본 페이지</span></h3>

<div class="board_list" id="pageViewTable">
	<table class="board_list_table">
		<colgroup>
			<col style="width:auto;" />
			<col style="width:auto;" />
			<col style="width:150px;" />
		</colgroup>
		<thead>
		<tr>
			<th scope="col">페이지 명</th>
			<th scope="col">페이지 경로</th>
			<th scope="col">페이지 뷰</th>
		</tr>
		</thead>
		<tbody id="page-data-list">

		</tbody>
	</table>
	<div class="no_content page_no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. -->
	</div>
	<jsp:include page="./include/content-pagination.jsp"/>
</div>


<page:javascript>
	<jsp:include page="./include/base-script.jsp"/>
	<script type="text/javascript">

		$(function() {
			initDataList();

			setTimeout(function() {
				view();
			},500);
		});

		function view() {
			displayChart();
			pagination('reportTable', 'ga:date', 1);
			pagination('pageViewTable', 'ga:pagePath,ga:pageTitle', 1);
		}

		function initDataList() {

			CHART_INTO_LIST = [];

			var options = getChartOptions();
			addChartInto('chartView', 'ga:date', 'ga:pageviews,ga:exits', false, 'LINE', options);

			paginationEvent('#reportTable', 'reportTable');
			paginationEvent('#pageViewTable', 'pageViewTable');
		}

		function pagination(key, dimensions, page) {

			var metrics, query;

			if (key == 'pageViewTable') {
				metrics = 'ga:pageviews';
				query = getReportQuery(dimensions, metrics, page, 25);
				query['sort'] = '-ga:pageviews';
			} else {
				metrics =
					'ga:pageviews,' +
					'ga:pageviewsPerSession,' +
					'ga:uniquePageviews,' +
					'ga:timeOnPage,' +
					'ga:avgTimeOnPage,' +
					'ga:exits,' +
					'ga:exitRate';
				query = getReportQuery(dimensions, metrics, page, 10);
				query['sort'] = '-ga:date';
			}

			paginationReport(key, query, function(response) {
				if (typeof response != 'undefined') {


					if (key == 'pageViewTable') {
						makePageViewDataList(response.rows);
						console.log(response);

					} else {
						makeDataList(response.rows);
					}

					displayTotalPages(key, $('#'+key), getTotalPages(response), page);
				}
			});
		}

		function makeDataList(list) {

			var html = '';

			if (typeof list != 'undefined' && list != null && list.length > 0) {

				var template = $('#dataListTemplate').html();

				for (var i = 0; i < list.length; i++) {
					var rawHtml = template,
						row = list[i];

					rawHtml = rawHtml.replaceAll('{{date}}', dateFormat(row[0], 'date'))
						.replaceAll('{{ga:pageviews}}', Common.numberFormat(row[1]))
						.replaceAll('{{ga:pageviewsPerSession}}', roundFormat(row[2]))
						.replaceAll('{{ga:uniquePageviews}}', Common.numberFormat(row[3]))
						.replaceAll('{{ga:timeOnPage}}', convertTime(row[4]))
						.replaceAll('{{ga:avgTimeOnPage}}', convertTime(row[5]))
						.replaceAll('{{ga:exits}}', Common.numberFormat(row[6]))
						.replaceAll('{{ga:exitRate}}', roundFormat(row[7]));

					html += rawHtml + '\n';
				}

				if (html != '') {
					$('.list_no_content').hide();
					$('#data-list').html(html);
				} else {
					$('.list_no_content').show();
				}
			}
		}

		function makePageViewDataList(list) {

			var html = '';

			if (typeof list != 'undefined' && list != null && list.length > 0) {

				var template = $('#rankDataListTemplate').html();

				for (var i = 0; i < list.length; i++) {
					var rawHtml = template,
						row = list[i];

					rawHtml = rawHtml.replaceAll('{{ga:pagePath}}', row[0])
						.replaceAll('{{ga:pageTitle}}', row[1])
						.replaceAll('{{ga:pageviews}}', Common.numberFormat(row[2]));
					html += rawHtml + '\n';
				}

				if (html != '') {
					$('.page_no_content').hide();
					$('#page-data-list').html(html);
				} else {
					$('.page_no_content').show();
				}
			}
		}

	</script>
	<script type="text/html" id="dataListTemplate">
		<tr>
			<td>{{date}}</td>
			<td><strong>{{ga:pageviews}}</strong></td>
			<td><strong>{{ga:exits}}</strong></td>
			<td><strong>{{ga:exitRate}}</strong></td>
			<td><strong>{{ga:pageviewsPerSession}}</strong></td>
			<td><strong>{{ga:uniquePageviews}}</strong></td>
			<td><strong>{{ga:timeOnPage}}</strong></td>
			<td><strong>{{ga:avgTimeOnPage}}</strong></td>
		</tr>
	</script>
	<script type="text/html" id="rankDataListTemplate">
		<tr>
			<td class="left"><strong>{{ga:pageTitle}}</strong></td>
			<td class="left"><strong>{{ga:pagePath}}</strong></td>
			<td><strong>{{ga:pageviews}}</strong></td>
		</tr>
	</script>
</page:javascript>