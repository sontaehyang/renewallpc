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

<h3><span>Keyword</span></h3>
<div class="board_list" id="keywordTable">
	<table class="board_list_table">
		<colgroup>
			<col style="width:auto;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">&nbsp;</th>
				<th scope="col">사용자</th>
				<th scope="col">신규 사용자</th>
				<th scope="col">세션</th>
				<th scope="col">이탈률(%)</th>
				<th scope="col">세션당 페이지수</th>
				<th scope="col">평균 세션 시간(초)</th>
			</tr>
		</thead>
		<tbody class="data-list">

		</tbody>
	</table>
	<div class="no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. -->
	</div>
	<jsp:include page="./include/content-pagination.jsp"/>
</div>

<br/>
<h3><span>Referrer</span></h3>
<div class="board_list" id="referralTable">
	<table class="board_list_table">
		<colgroup>
			<col style="width:auto;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
		</colgroup>
		<thead>
		<tr>
			<th scope="col">&nbsp;</th>
			<th scope="col">사용자</th>
			<th scope="col">신규 사용자</th>
			<th scope="col">세션</th>
			<th scope="col">이탈률(%)</th>
			<th scope="col">세션당 페이지수</th>
			<th scope="col">평균 세션 시간(초)</th>
		</tr>
		</thead>
		<tbody class="data-list">

		</tbody>
	</table>
	<div class="no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. -->
	</div>
	<jsp:include page="./include/content-pagination.jsp"/>
</div>

<br/>
<h3><span>Social Network</span></h3>
<div class="board_list" id="socialTable">
	<table class="board_list_table">
		<colgroup>
			<col style="width:auto;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
			<col style="width:200px;" />
		</colgroup>
		<thead>
		<tr>
			<th scope="col">&nbsp;</th>
			<th scope="col">사용자</th>
			<th scope="col">신규 사용자</th>
			<th scope="col">세션</th>
			<th scope="col">이탈률(%)</th>
			<th scope="col">세션당 페이지수</th>
			<th scope="col">평균 세션 시간(초)</th>
		</tr>
		</thead>
		<tbody class="data-list">

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
	    pagination('keywordTable', 'ga:keyword', 1);
	    pagination('referralTable', 'ga:source', 1);
	    pagination('socialTable', 'ga:socialNetwork', 1);
    }

    function initDataList() {

	    paginationEvent('#keywordTable', 'keywordTable');
	    paginationEvent('#referralTable', 'referralTable');
	    paginationEvent('#socialTable', 'socialTable');

    }

    function pagination(key, dimensions, page) {
	    var metrics = 'ga:users,ga:newUsers,ga:sessions,ga:exitRate,ga:pageviewsPerSession,ga:avgSessionDuration';

	    var query = getReportQuery(dimensions, metrics, page, 10);

	    query['sort'] = '-ga:users';

	    paginationReport(key, query, function(response) {
		    if (typeof response != 'undefined') {
			    makeDataList(key,response.rows);
			    displayTotalPages(key, $('#'+key), getTotalPages(response), page);
		    }
	    });
    }

	function makeDataList(tableId, list) {

		var html = '';
		var $table = $('#'+tableId);

		if (typeof list != 'undefined' && list != null && list.length > 0) {

			var template = $('#dataListTemplate').html();

			for (var i = 0; i < list.length; i++) {
				var rawHtml = template,
					row = list[i];

				rawHtml = rawHtml.replaceAll('{{key}}', row[0])
					.replaceAll('{{ga:users}}', Common.numberFormat(row[1]))
					.replaceAll('{{ga:newUsers}}', Common.numberFormat(row[2]))
					.replaceAll('{{ga:sessions}}', Common.numberFormat(row[3]))
					.replaceAll('{{ga:exitRate}}', roundFormat(row[4]))
					.replaceAll('{{ga:pageviewsPerSession}}', roundFormat(row[5]))
					.replaceAll('{{ga:avgSessionDuration}}', convertTime(row[6]));

				html += rawHtml + '\n';
			}

			if (html != '') {
				$table.find('.no_content').hide();
				$table.find('.data-list').html(html);
			} else {
				$table.find('.no_content').show();
			}
		}
	}

</script>
<script type="text/html" id="dataListTemplate">
<tr>
	<td class="left">{{key}}</td>
	<td><strong>{{ga:users}}</strong></td>
	<td><strong>{{ga:newUsers}}</strong></td>
	<td><strong>{{ga:sessions}}</strong></td>
	<td><strong>{{ga:exitRate}}</strong></td>
	<td><strong>{{ga:pageviewsPerSession}}</strong></td>
	<td><strong>{{ga:avgSessionDuration}}</strong></td>
</tr>
</script>
</page:javascript>