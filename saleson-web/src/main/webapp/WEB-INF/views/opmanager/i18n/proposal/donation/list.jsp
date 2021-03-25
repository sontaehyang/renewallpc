<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>



			<p class="guide">기부 내역을 관리합니다 수시 기부 및 정기 기부 내역을 기간별로 확인하실 수 있습니다.</p>
			<h3><span>기부내역</span></h3>
			
			<div class="donation_state_wrap">
				<h4>정기기부 <span class="normal" id="regularDate">${op:formatDate(regularSearchParam.startDate,'-')} ~ ${op:formatDate(regularSearchParam.endDate,'-')}</span></h4>
				<div class="sort_area">
					<form:form modelAttribute="regularSearchParam" cssClass="opmanager-search-form clear" onsubmit="regularSubmit(); return false;"  method="post">
						<fieldset>
							<form:hidden path="sort" />
							<form:hidden path="orderBy" />
							<legend class="hidden">검색</legend>
								<span class="datepicker"><input type="text" name="startDate" id="startDate1" title="검색 시작일" value="${regularSearchParam.startDate}" class="required _date term term1" maxlength="8"></span>
								<span class="wave">~</span> 
								<span class="datepicker"><input type="text" name="endDate" id="endDate1" value="${ regularSearchParam.endDate }" title="검색 종료일" class="required _date term term1" maxlength="8"  /></span>
								<button type="submit"><img src="/content/images/common/icon_search.png" alt=""> 검색</button>
						</fieldset>
					</form:form>
				</div>
				
				<div class="donation_state" id="regular_donation_state"></div>
				<p class="btns">
					<button type="button" onclick="Common.popup(url('/opmanager/proposal/donation/upload-excel'), 'upload', 450, 260); return false;" class="btn orange">엑셀파일 업로드</button>
					<a href="javascript:fn_regularExcelDownload();" class="btn orange">엑셀파일 다운로드</a>
				</p>
			</div>
			<div class="board_list">
				<h4>수시기부 <span class="normal" id="donationDate">${ op:formatDate(searchParam.startDate,'-') } ~ ${ op:formatDate(searchParam.endDate,'-') }</span></h4>
				<div class="donation_state" id="donation_state">
				</div>
				<div class="sort_area">
					<form:form modelAttribute="searchParam" cssClass="opmanager-search-form clear" method="post" onsubmit="pagination(); return false;">
						<fieldset>
							<form:hidden path="sort" />
							<form:hidden path="orderBy" />
							<legend class="hidden">검색</legend>
							<div class="left">
								<span>
									<form:radiobutton path="userType" value="0" label="전체"  />
									<form:radiobutton path="userType" value="1" label="고객" />
									<form:radiobutton path="userType" value="3" label="직원(이체)" />
									<form:radiobutton path="userType" value="2" label="직원(희망별)" />
								</span>
								<span class="line">|</span>
								<span>기부일</span>
								<span class="datepicker"><form:input path="startDate" value="" title="기부일 시작일"  cssClass="required _date term term2" maxlength="8" /></span>
								<span class="wave">~</span>
								<span class="datepicker"><form:input path="endDate" value="" title="기부일 종료일" cssClass="required _date term term2" maxlength="8"  /></span>
							</div>
							<div class="right">
								<form:select path="where" items="${whereColumns}" itemValue="value" itemLabel="label" />
								<form:input path="query" title="검색어" cssClass="optional _filter" />
								<button type="submit"><img src="/content/images/common/icon_search.png" alt=""> 검색</button>
							</div>
						</fieldset>
					</form:form>
				</div>
			<div id="show"></div>
		</div>

<script type="text/javascript">

	$(function(){
		regularSubmit();
		pagination(1);
		temr1Chage();
		temr2Chage();
		EventHandler.calendarStartDateAndEndDateVaild();

		$('input[name=userType]').on("click", function() {
			pagination();
		});

		$("#searchParam").validator(function() {});
		
	});

	function fn_ApprovalAndretrocede(regularId, approvalStatusCode){
		var data = {"regularId" : regularId, "statusCode" : approvalStatusCode};
		$.post("/opmanager/proposal/donation/update-status",data,function(response){
			Common.responseHandler(response, function(){
				regularSubmit();
				alert("확인하였습니다."); 
			});
		});
	}

	function fn_regularExcelDownload(){
		location.href = "/opmanager/proposal/donation/regular-download-excel?"+$("#regularSearchParam").serialize();
	}

	function fn_donationExcelDownload(){
		location.href = "/opmanager/proposal/donation/donation-download-excel?"+$("#searchParam").serialize();
	}

	function regularSubmit(){

		var startYear = $( "#startDate1" ).val().substr(0,4);
		var endYear = $( "#endDate1" ).val().substr(0,4);

		if( startYear != endYear ){
			alert('정기기부 검색은 같은 년도만 가능 합니다. ');
			return false;
		}

		var ymd1 = $("#startDate1").val();
		var ymd2 = $("#endDate1").val();

		
		var d1 = ymd1.substring(0,4) +'-'+ ymd1.substring(4,6) +'-'+ ymd1.substring(6,8);
		var d2 = ymd2.substring(0,4) +'-'+ ymd2.substring(4,6) +'-'+ ymd2.substring(6,8); 
		
		$("#regularDate").text(d1 + " ~ " + d2 +" 현재");
		$.post('/opmanager/proposal/donation/regular-donation-list', $("#regularSearchParam").serialize(), function(html) {
			$("#regular_donation_state").html(html);
			setHeight();
		});

		
	}

	function pagination(page) {
		page = page == undefined ? 1 : page;

		var ymd1 = $("#startDate").val();
		var ymd2 = $("#endDate").val();

		
		var d1 = ymd1.substring(0,4) +'-'+ ymd1.substring(4,6) +'-'+ ymd1.substring(6,8);
		var d2 = ymd2.substring(0,4) +'-'+ ymd2.substring(4,6) +'-'+ ymd2.substring(6,8); 
		
		$("#donationDate").text(d1 + " ~ " + d2 +" 현재");
		
		$("#donation_state > div").remove();
		
		$.post('/opmanager/proposal/donation/proposal-donation-total', $("#searchParam").serialize() + '&page=' + page, function(html) {
			$("#donation_state").append(html);
			setHeight();
		});
		
		$.post('/opmanager/proposal/donation/proposal-donation-list', $("#searchParam").serialize() + '&page=' + page, function(html) {
			$("#show").html(html);
			setHeight();
		});

	}

	function temr1Chage(){
		$('.term1').on("change",function(){
			if($('#startDate1').val() != '' && $('#endDate1').val() != ''){
				dateChage($('#startDate1'), $('#endDate1'),$(this));
			} 
		});
	}

	function temr2Chage(){
		$('.term2').on("change",function(){
			if($('#startDate').val() != '' && $('#endDate').val() != ''){
				dateChage($('#startDate'), $('#endDate'),$(this));
			} 
		});
	}

	function dateChage(obj1,obj2,obj3){

		var ymd1 = $(obj1).val();
		var ymd2 = $(obj2).val();

		var d1 = new Date(ymd1.substring(1,4),ymd1.substring(4,6)-1,ymd1.substring(6,8));
		var d2 = new Date(ymd2.substring(1,4),ymd2.substring(4,6)-1,ymd2.substring(6,8));
		   
		var $dayNumber = (d2 - d1) /(1000*60*60*24);
		
		if($dayNumber < 0){
			alert('봉사기간이 종료일이 시작일보다 큽니다.\n확인하여 주시기 바랍니다. ');
			$(obj3).val('');
		}
	}

</script>