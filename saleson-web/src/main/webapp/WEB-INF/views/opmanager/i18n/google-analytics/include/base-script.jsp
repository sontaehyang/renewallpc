<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
    (function(w,d,s,g,js,fjs){
        g=w.gapi||(w.gapi={});g.analytics={q:[],ready:function(cb){this.q.push(cb)}};
        js=d.createElement(s);fjs=d.getElementsByTagName(s)[0];
        js.src='https://apis.google.com/js/platform.js';
        fjs.parentNode.insertBefore(js,fjs);js.onload=function(){g.load('analytics')};
    }(window,document,'script'));
</script>
<script type="text/javascript">
    var CHART_INTO_LIST = [];
    var PAGINATION_LIST = [];

    $(function() {
	    Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="beginDate"]' , 'input[name="endDate"]');
        init();
	});


    function init() {

        var token = '${accessToken}';
        var processFlag = '${not empty accessToken and config.statisticsFlag}';

        if (processFlag == 'true') {
            gapi.analytics.ready(function() {

                gapi.analytics.auth.authorize({
                    'serverAuth': {
                        'access_token': token
                    }
                });

	            gapi.analytics.auth.on('error', function(response) {
		            handleApiExeption(response.error);
	            });
            });
        } else {
            alert('not setting');
        }
    }

    function isFunction(func) {
	    return func != null && typeof func === 'function' ? true : false;
    }

    function handleApiExeption(error, failureHandler) {

	    Common.loading.hide();

	    if (isFunction(failureHandler)) {
		    failureHandler(error);
	    } else {
	    	if (typeof error != 'undefined' && error != null) {
	    		var code = error.code;
	    		var href = '';
			    var message = '';
	    		if (401 == code) {
	    			message = '유효한 토큰이 아닙니다.';
	    			href = '${requestContext.requestUri}'+'?reload=Y';
			    }

			    if (403 == code) {
				    message = 'Google Analytics 접근 가능한 Google 계정이 아닙니다.\n' +
					    'https://analytics.google.com에서 해당 계정을 추가해 주세요.';
			    }

			    alert(message);

			    if (href !='') {
			    	location.href=href;
			    }

		    } else {
			    console.log(error);
		    }
	    }
    }

    function dateFormat(t, type) {
	    var result = "";
	    if (t != null) {
		    if (type == null || type == "time") {
			    result = t.replace(/(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/, '$1-$2-$3 $4:$5:$6');
		    } else if (type == "date") {
			    result = t.substr(0, 8).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
		    }
	    }
	    return result;
    }

    function pageWidth() {
    	return Math.round($('.contents_inner').width() * 0.99);
    }

    function roundFormat(v) {

    	try {
    		var n = parseFloat(v);
		    return n.toFixed(2);
	    } catch (e) {
		    return v;
	    }
    }

    function convertTime(v) {

    	try {
		    var time = parseFloat(v);

		    var hours = Math.floor((time / (60 * 60)));
		    time = time - (hours * 60 * 60);
		    var minutes = Math.floor((time / 60));
		    time = time - (minutes * 60);
		    var seconds = Math.round(time);

		    return Common.addZero(hours,2) + ":" + Common.addZero(minutes,2) + ":" + Common.addZero(seconds,2);
	    } catch (e) {
    		console.log(e)
		    return v;
	    }
    }

    function addChartInto(container, dimensions, metrics, todayFlag, chartType, options) {
        CHART_INTO_LIST.push({
            'container' : container,
            'dimensions' : dimensions,
            'metrics' : metrics,
            'todayFlag' : todayFlag,
            'chartType' : chartType,
            'options' : options
        });
    }


    function displayChart() {

        var profile = $('#profile').val();
        var beginDate = $('#paramTable').find('input[name=beginDate]').val();
        var endDate = $('#paramTable').find('input[name=endDate]').val();

        beginDate = dateFormat(beginDate, 'date');
	    endDate = dateFormat(endDate, 'date');

        for (var i=0; i< CHART_INTO_LIST.length; i++) {
            var s = CHART_INTO_LIST[i];

            if (s.todayFlag) {
                beginDate = 'today';
                endDate = 'today';
            }

            viewChart(s.container,
                    getQuery( profile, s.dimensions, s.metrics, beginDate, endDate),
                    s.chartType,
                    s.options
            );
        }

    }

    function getChartOptions() {

	    var width = Math.round(pageWidth());
	    var options = getChartBaseOptions(width, 300);
	    options['crosshair'] = {
		    color: '#000',
		    trigger: 'selection'
	    };
	    options['hAxis'] = { format: 'yyyy-MM-dd'}

	    return options;
    }

    function getChartBaseOptions(width, height) {
        return {
            width: width,
            height: height
        }
    }

    function getReportQuery(dimensions, metrics, page, itemPerPage) {

	    var profile = $('#profile').val();
	    var beginDate = $('#paramTable').find('input[name=beginDate]').val();
	    var endDate = $('#paramTable').find('input[name=endDate]').val();

	    beginDate = dateFormat(beginDate, 'date');
	    endDate = dateFormat(endDate, 'date');

	    var query = getQuery(profile, dimensions, metrics, beginDate, endDate);

	    if (typeof itemPerPage == 'undefined' || itemPerPage == null) {
		    itemPerPage = 10;
	    }

	    if (typeof page == 'undefined' || page == null || page < 1) {
		    page = 1;
	    }

	    var startIndex = ((page -1) * itemPerPage) + 1;

	    query["max-results"] = itemPerPage;
	    query["start-index"] = startIndex

    	return query;
    }

    function getQuery(profile, dimensions, metrics, beginDate, endDate) {

        if (isEmpty(profile)) {
            alert('속성 값은 필수 입니다.');
            return null;
        }

        if (isEmpty(dimensions)) {
            alert('조회조건 값은 필수 입니다.');
            return null;
        }

        if (isEmpty(metrics)) {
            alert('조회요소 값은 필수 입니다.');
            return null;
        }

        return {
            ids: profile,
            'dimensions': dimensions,
            'metrics': metrics,
            'start-date': beginDate,
            'end-date': endDate,
        }
    }

    function viewChart(container, query, chartType, options) {

        var dataChartTypeList = ['LINE','BAR','COLUMN','TABLE','GEO'];

        if (isEmpty(container) ||query == null|| isEmpty(chartType)) {
            return false;
        }

	    Common.loading.show();

	    var c = new gapi.analytics.googleCharts.DataChart({
		    reportType: 'ga',
		    query: query,
		    chart: {

			    type: chartType,
			    container: container,
			    options: options
		    }
	    });

	    c.execute();

	    c.on('success', function(response) {
		    Common.loading.hide();
	    });

	    c.on('error', function(response) {
		    handleApiExeption(response.error);
	    });
    }

    function report(query, callback) {

	    Common.loading.show();

	    var report = new gapi.analytics.report.Data({
		    query: query
	    });

	    report.on('success', function(response) {
		    Common.loading.hide();
		    callback(response)
	    });

	    report.on('error', function(response) {
		    handleApiExeption(response.error);
	    });

	    report.execute();
    }

    function isEmpty(value) {
        return typeof value =='undefined' || value == null || value == '';
    }

    function getPagination(key) {
    	var info = PAGINATION_LIST[key];

    	if (typeof info !='undefined' && info != null) {
    		return info;
	    }

    	return {
		    currentPage : 1,
		    totalPages : 1,
		    dimensions : '',
		    metrics : ''
	    }
    }

    function paginationEvent(selector, key) {


    	var $selector = $(selector);

	    $selector.on('click','.list-prev',function() {
		    var info = getPagination(key);
		    var page = info.currentPage;
		    if (page <= 1) {
			    alert('첫번째 페이지입니다.');
			    return false;
		    }

		    pagination(key, info.dimensions, --page);
	    });

	    $selector.on('click', '.list-next',function() {
		    var info = getPagination(key);
		    var page = info.currentPage;
		    if (page == info.totalPages) {
			    alert('마지막 페이지입니다.');
			    return false;
		    }

		    pagination(key, info.dimensions, ++page);
	    });

	    $selector.on('change', '.select-pages',function () {
		    var info = getPagination(key);
		    pagination(key, info.dimensions, $(this).val());
	    });

    }

    function setPage(key, currentPage, totalPages) {
	    var info = getPagination(key);
	    info.currentPage = currentPage;
	    info.totalPages = totalPages;
	    PAGINATION_LIST[key] = info;
    }


    function paginationReport(key, query, callback) {

    	Common.loading.show();

	    var info = getPagination(key);

	    info.dimensions = query.dimensions;
	    info.metrics = query.metrics;

	    PAGINATION_LIST[key] = info;

	    report(query, function(response) {

		    Common.loading.hide();
		    callback(response)

	    })
    }

    function getTotalPages(response) {
    	return Math.ceil(response.totalResults / response.itemsPerPage);
    }

    function displayTotalPages(key, selector, totalPages, page) {
	    $(selector).find('.total-page-label').text(totalPages);

	    var $select = $(selector).find('.select-pages');
	    $select.empty();
	    for (var i=1; i<=totalPages; i++) {
		    var selected = i == page ? 'selected="selected"' : '';
		    var option = '<option '+selected+' value="'+i+'">'+i+'</option>';

		    $select.append(option);
	    }
;
	    setPage(key, page, totalPages);
    }
</script>