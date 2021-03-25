<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<h2><span>¶ SMS 발송 내역</span></h2>
<form id = "searchParam">
<input type="hidden" name="userId" value="${userId}">
<input type="hidden" name="page" value="${searchParam.page}" />
<input type="hidden" name="userName" value="${user.userName}">	
	<table class="board_write_table" summary="${op:message('M00269')} ">
		<caption>${op:message('M00269')} </caption>
		<colgroup>
			<col style="width:150px;" />
			<col style="width:*;" />
		</colgroup>
		<tbody>
			 <tr>
			 	<td class="label">${op:message('M01668')}</td> <!-- 발송유형 --> 
			 	<td>
			 		<div>
						<select name="sendType" class="form-control03">
							<option value="" label="전체">전체</option>
							<c:forEach items="${smsTemplateCodeList}" var="list">
								<option value="${list.key}" label="${list.value}">${list.value}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			 </tr>
			  <tr>
			 	<td class="label">${op:message('M00276')}</td>
			 	<td>
					<div class="search-date">
						<span class="datepicker"><input type="text" name="searchStartDate" Class="datepicker optional " title="${op:message('M00225')}" /></span>
						<span class="wave">~</span>
						<span class="datepicker"><input type="text" name="searchEndDate" Class="datepicker optional " title="${op:message('M00226')}" /></span>
						
						<span class="day_btns"> 
							<a href="#" class="btn_date today">${op:message('M00026')}</a>  
							<a href="#" class="btn_date week-1">${op:message('M00222')}</a> 
							<a href="#" class="btn_date month-1">${op:message('M00029')}</a> 
							<a href="#" class="btn_date month-2">${op:message('M00223')}</a> 
							<a href="#" class="btn_date clear">${op:message('M00039')}</a> 
						</span>
					</div>							
				</td>	
			 </tr>		 
		</tbody>					 
	</table>			
	
	<!-- 버튼시작 -->	
	<div class="btn_all">
		<div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm" onclick="SendSmsLogReset(${userId});"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
		</div>
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
		</div>
	</div>	
</form>

<form>
	<table>
		<colgroup>
			<col style="width:150px;">
			<col style="width:auto;">
			<col style="width:80px;">  
		</colgroup>  
		<tbody>
			<tr> 
				<td colspan="3" id="smslog-list">
					
				</td>
			</tr>    
		</tbody>
	</table> 
</form>

<script type="text/javascript">


$(function(){
	
	Manager.activeUserDetails("sendSms");
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	
	$('#searchParam').validator({
		'submitHandler' : function() {
			
			$('input[name="page"]').val(1);
			
			$.get('/opmanager/user/popup/send-sms-log/list' , $('#searchParam').serialize(), function(html) {
				$('#smslog-list').html(html);
			});
			
			return false;
		}
	});
	
	SendSmsLogList(1);

});

function SendSmsLogList(page) {
	if (page == undefined) {
		page = 1;
	}
	
	$('#searchParam').find('input[name="page"]').val(page);
	$.get('/opmanager/user/popup/send-sms-log/list', $('#searchParam').serialize(), function(html) {
		$('#smslog-list').html(html);
	});
}

function SendSmsLogReset(userId) {
	location.href = '/opmanager/user/popup/send-sms-log-list/'+userId;
}

function goUrl(orderCode) {
	opener.location.href = '/opmanager/order/new-order/order-detail/0/'+orderCode;
}

</script>