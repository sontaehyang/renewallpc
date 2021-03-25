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
				<th scope="col">사용자</th>
				<th scope="col">신규 사용자</th>
				<th scope="col">신규 사용자률(%)</th>
				<th scope="col">사용자당 세션수</th>
			</tr>
		</thead>
		<tbody id="data-list">

		</tbody>
	</table>
	<div class="no_content">
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
    }

    function initDataList() {

    	CHART_INTO_LIST = [];

        var options = getChartOptions();
        addChartInto('chartView', 'ga:date', 'ga:users,ga:newUsers', false, 'LINE', options);

	    paginationEvent('#reportTable', 'reportTable');
    }

	function pagination(key, dimensions, page) {
		var metrics = 'ga:users,ga:newUsers,ga:percentNewSessions,ga:sessionsPerUser';

		var query = getReportQuery(dimensions, metrics, page, 10);

		query['sort'] = '-ga:date';

		paginationReport(key, query, function(response) {
			if (typeof response != 'undefined') {

				makeDataList(response.rows);
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
				    .replaceAll('{{ga:users}}', Common.numberFormat(row[1]))
				    .replaceAll('{{ga:newUsers}}', Common.numberFormat(row[2]))
				    .replaceAll('{{ga:percentNewSessions}}', roundFormat(row[3]))
					.replaceAll('{{ga:sessionsPerUser}}', roundFormat(row[4]));

			    html += rawHtml + '\n';
		    }

		    if (html != '') {
			    $('.no_content').hide();
			    $('#data-list').html(html);
		    } else {
			    $('.no_content').show();
		    }
	    }
    }

</script>
<script type="text/html" id="dataListTemplate">
<tr>
	<td>{{date}}</td>
	<td><strong>{{ga:users}}</strong></td>
	<td><strong>{{ga:newUsers}}</strong></td>
	<td><strong>{{ga:percentNewSessions}}</strong></td>
	<td><strong>{{ga:sessionsPerUser}}</strong></td>
</tr>
</script>
</page:javascript>