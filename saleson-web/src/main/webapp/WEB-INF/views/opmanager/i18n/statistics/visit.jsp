<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>






	
	



<script type="text/javascript" src="/content/modules/swfobject/swfobject.min.js"></script>
<script type="text/javascript"> 
function link1(chart_id){
	var year = '${visitCount.LAST_YEAR}';
	var month = chart_id + 1;
	if(month < 10) month = '0'+month;
	
	location.href="/opmanager/statistics/visit/detail?month="+year+'-'+month;
}
 
function link2(chart_id){
	var year = '${visitCount.CURRENT_YEAR}';
	var month = chart_id + 1;
	if(month < 10) month = '0'+month;
	location.href="/opmanager/statistics/visit/detail?month="+year+'-'+month;
}
 
 
 
function open_flash_chart_data()
{
    return JSON.stringify(chart);
}
 
 
 
var chart = {

	"title":{
	    "text":  "${visitCount.LAST_YEAR}년 ~ ${visitCount.CURRENT_YEAR}년 방문자수",
	    "style": "{font-size: 20px; color:#000000; font-family: Verdana; text-align: center;}"
	},
 
 
	"y_legend":{
		"text": "Count",
		"style": "{color:#000000; font-size: 16px;}"
	},
 
	"x_legend":{
		"text": "Month",
		"style": "{color:#000000; font-size: 16px;}"
	},	  
  		
	"elements":[
	        	{"type":"bar_3d","colour":"#e44091","text":"${visitCount.LAST_YEAR}년","font-size":12,"values":[${visitCount.LAST_YEAR_COUNT}],"on-click": "link1" },
	        	{"type":"bar_3d","colour":"#32c5f2","text":"${visitCount.CURRENT_YEAR}년","font-size":12,"values":[${visitCount.CURRENT_YEAR_COUNT}],"on-click": "link2" }
	],
 
	"bg_colour": "#fbfbfb",
 
	"x_axis":{
		"3d": 12, 
		"colour": "#909090", 
		"grid-colour": "#e1e1e1" , 
	    "labels": {
		    "labels":["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
		}
	},
 
 
 
	"y_axis":{
		"colour": "#909090", 
		"grid-colour": "#e1e1e1" , 
		
		"min" : 0,
		"steps" : ${ofc.STEP},
		"max": ${ofc.MAX}
	}
};
 
window.onload = function(){
	swfobject.embedSWF("/content/modules/open-flash-chart2/open-flash-chart.swf", "my_chart", "100%", "400", "9.0.0");
}
</script>
 
 
 
 
<h3>방문자 통계</h3>
 
<table class="opmanager-search-table margin-top-5">
    <tr>
        <th>전체 접속자 수</th>
        <th>오늘 접속자 수</th>
        <th>어제 접속자 수</th>
        <th>최대 접속자 수</th>
    </tr>
    <tr>
	<tr>
        <td class="visit-count"><fmt:formatNumber value="${visit.TOTAL}" pattern="#,##0" /></td>
        <td class="visit-count-today"><fmt:formatNumber value="${visit.TODAY}" pattern="#,##0" /></td>
        <td class="visit-count"><fmt:formatNumber value="${visit.YESTERDAY}" pattern="#,##0" /></td>
        <td class="visit-count"><fmt:formatNumber value="${visit.MAX}" pattern="#,##0" /></td>
    </tr>
    </tr>
</table>
 
 
<br />
<br />
<div id="my_chart"></div>
	