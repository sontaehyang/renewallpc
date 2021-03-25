<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>

<module:OpenFlashChart />

<c:choose>
	<c:when test=""></c:when>
	<c:when test=""></c:when>
	<c:when test=""></c:when>
</c:choose>


<script type="text/javascript"> 
function open_flash_chart_data() {
    return JSON.stringify(chart);
}

var chart = {
		"elements": [ 
			{
				"type": "bar_filled", 
				"colour": "#E2D66A", 

				"outline-colour": 
					"#577261", 
					"values": [${data}]
			}
		], 
		"bg_colour": "#fbfbfb", 
		"title": { 
			"text": "${titleYear}년 ${titleMonth}월 일별 방문자 수", 
			"style": "{font-size: 20px; color: #000000; text-align: center; margin-top: 10px; margin-bottom: 20px}" 
		},

		"y_legend":{
			  "text": "Count",
			  "style": "{color: #736AFF; font-size: 16px;}"
			},
			
		"x_legend": { 
			"text": "<div style='paddding:10px'>일자</div>", 
			"style": "{color:#000000; font-size: 16px; margin-top:20px; margin-bottom: 20px}"
		}, 

		
		"x_axis": { 
			"colour": "#909090", 
			"grid-colour": "#e1e1e1", 
			 "stroke":2,
			    "tick_height":10,
			"labels": { 
				"steps": 1, 
				"colour": "#000000", 
				"labels": [${months}] 
			}
		} ,
		
		"y_axis": {
			"colour": "#909090", 
			"grid-colour": "#e1e1e1" , 
			"min": 0,
			"max": ${ofc.MAX}, 
			"steps": ${ofc.STEP}
		} 
	};

 
window.onload = function(){
	swfobject.embedSWF("/content/modules/open-flash-chart2/open-flash-chart.swf", "my_chart", "100%", "400", "9.0.0");
}
</script>
 
 
 
 
<h3>방문자 통계 - 일별<span class="h3_info"><a href="/opmanager/statistics/visit">뒤로</a></span></h3>
 
<table class="opmanager-search-table margin-top-5">
    <tr>
        <th>전체 접속자 수</th>
        <th>오늘 접속자 수</th>
        <th>어제 접속자 수</th>
        <th>최대 접속자 수</th>
    </tr>
    <tr>
        <td class="visit-count"><fmt:formatNumber value="${visit.TOTAL}" pattern="#,##0" /></td>
        <td class="visit-count-today"><fmt:formatNumber value="${visit.TODAY}" pattern="#,##0" /></td>
        <td class="visit-count"><fmt:formatNumber value="${visit.YESTERDAY}" pattern="#,##0" /></td>
        <td class="visit-count"><fmt:formatNumber value="${visit.MAX}" pattern="#,##0" /></td>
    </tr>
</table>
 
 
<br />
<br />
<div id="my_chart"></div>