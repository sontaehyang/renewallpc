<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


<style type="text/css">
input.txt {width: 70px;}
</style>



<h3>접속통계</h3>

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
 
  
<table style="width:100%;" class="margin-top-5">
    <tr>
        <td style="border:dotted 1px #cfdee3;width:400px;padding:5px;background:#fff"><div id="my_chart"></div></td>
        <td style="padding:0 20px" valign="top">
 
			<h5>항목별 분석 통계 <span>조회할 날짜를 선택한 후 아래 항목을 클릭하세요</span></h5>
			<table class="opmanager-search-table margin-top-5">
			    <tr>
			        <th>기간</th>
					<td><input type="text" class="txt" id="sdate" value="${startDate}" /> ~ <input type="text" class="txt" id="edate" value="${endDate}" /></td>
			    </tr>
			    <tr>
			        <th>항목</th>
					<td class="statistics-type">
						<a href="#도메인" class="select" onclick="return false;">[도메인]</a>
						<a href="#브라우저" onclick="return false;">[브라우저]</a>
						<a href="#OS" onclick="return false;">[OS]</a>
						<a href="#요일" onclick="return false;">[요일]</a>		
					</td>
			    </tr>
			</table>
 
 
            <table class="opmanager-list-table margin-top-10" cellpadding="0" cellspacing="0">
            	<col width="40" />
            	<col width="" />
            	<col width="100" />
            	<col width="100" />
            	
                <tr>
                    <th>순번</th>
                    <th>${typeName}</th>
                    <th>접속자수</th>
                    <th>비율(%)</th>
                </tr>
 
				<c:if test="${!empty visitList}">
					<c:forEach items="${visitList}" var="visit" varStatus="i">
	
					        <tr>
					            <td>${i.count}</td>
					            <td align=left>${visit.title}</td>
					            <td>${visit.count}</td>
					            <td>${visit.ratio}</td>
					        </tr>			
	
					</c:forEach>
				</c:if>				

				        <tr class="bgcol2 bold col1 ht center">
				            <td></td>
				            <td>합계</td>
				            <td>${totalCount}</td>
				            <td>&nbsp;</td>
				        </tr>
			           </table>
        </td>
    </tr>
</table>
 
 
<br />
<br />
 
<h3>전체 접속경로</h3>
<div id="referer-list"></div>

 
<script type="text/javascript"> 
$(function() {
	getRefererList(1);
});
function getRefererList(page){
	$.get('/opmanager/statistics/referer/list?page='+page, '', function(result){
			$('#referer-list').html(result);
	    }, 'html');
}


 
 
function formSubmit(){
  var form = $g('OpmanagerSearchForm');
  form.submit();
}
 
 
function sendToMember(type, n){
    var obj = document.getElementsByName("mem_no");
    for(var i=0;i<obj.length;i++){
        if(i==n){
            obj[i].checked = true;
        }else{
            obj[i].checked = false;
        }
    }
    SendForm(type, '');
}
function formChk(f){
    if(f.num_per_page.value==0){
      alert("적어도 1명 이상으로 보내셔야 합니다. ");
      return false;
    }
}
 
function Sort(fnm){
    var f=$g('CurrentState');
    var sDesc = f.sortDesc.value;
 
    if (sDesc=='DESC') f.sortDesc.value ='ASC';
    else f.sortDesc.value ='DESC';
    f.page.value = 1;
    f.sort.value = fnm;
    f.method = 'get';
    f.submit();
}
</script>
 
 
<script type="text/javascript" src="/content/modules/swfobject/swfobject.min.js"></script>
<script type="text/javascript"> 
var _type = new Array('domain', 'browser', 'os', 'weekday', 'time');
$(function(){
    $('#sdate, #edate').datepicker();
 
 
    $('.statistics-type a').click(function(){
        var idx = $('.statistics-type a').index(this);
        var tp = _type[idx];
        var sdate = $('#sdate').val();
        var edate = $('#edate').val();
 
        location.href='/opmanager/statistics/referer?type='+tp+'&startDate='+sdate+'&endDate='+edate;
    });
});
  
 
function open_flash_chart_data()
{
    return JSON.stringify(chart);
}
 
 
 
var chart = {
	"title":{
	    "text":  "",
	    "style": "{font-size: 20px; color:#736AFF; font-family: Verdana; text-align: center;}"
	},

	"bg_colour": "#fbfbfb", 
  
	"elements":[ 
		{ 
			"type": "pie", 
			"start-angle": -50, 
			"animate": [ 
				{ "type": "fade" }, 
				{ "type": "bounce", "distance": 15 }
			 ],
 
			"gradient-fill": true, 
			"tip": "#val# (#percent#)", //of #total#
			"colours": [ "#FF33C9","#1F8FA1", "#848484" ], 
			"values": [ 
 
				//{ "value": 0, "label": "내부망 (0)", "colour": "#1F8FA1", "label-colour": "#1F8FA1", "font-size": 11 }, 
				//{ "value": 18, "label": "외부망 (18)", "colour": "#FF33C9", "font-size": 11 }
				${chartData}
			]
		} 
	]   
};
 
window.onload = function(){
	swfobject.embedSWF("/content/modules/open-flash-chart2/open-flash-chart.swf", "my_chart", "400", "400", "9.0.0");
}
 
</script>