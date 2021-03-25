<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<h2><span>¶ 상담 내역 <!-- 나의 배송지 관리 --></span></h2>
<form id = "searchParam">
	<input type="hidden" name="userId" value="${userId}" />
	<input type="hidden" name="page" value="${searchParam.page}" />
	<input type="hidden" name="userName" value="${user.userName}" />

	<table class="board_write_table" summary="${op:message('M00269')} ">
		<caption>${op:message('M00269')} </caption>
		<colgroup>
			<col style="width:150px;" />
			<col style="width:*;" />
		</colgroup>
		<tbody>
			 <tr>
			 	<td class="label">${op:message('M00011')}</td>
			 	<td>
			 		<div>
			 			<select name="where">
			 				<option value="ORDERCODE" label="${op:message('M00013')}">${op:message('M00013')}</option> <!-- 주문번호 -->
			 				<option value="MEMO" label="${op:message('M00006')}">${op:message('M00006')}</option> <!-- 내용 -->
			 			</select>
			 			<input type="text" name="query" Class="nine" title="${op:message('M00021')}"/>
					</div>
			 	</td>	
			 </tr>
			  <tr>
			 	<td class="label">${op:message('M00276')}</td>
			 	<td>
					<div class="search-date">
						<span class="datepicker"><input type="text" name="startDate" Class="datepicker optional " title="${op:message('M00225')}" /></span>
						<span class="wave">~</span>
						<span class="datepicker"><input type="text" name="endDate" Class="datepicker optional " title="${op:message('M00226')}" /></span>
						
						<span class="day_btns"> 
							<a href="#" class="btn_date today">${op:message('M00026')}</a>  
							<a href="#" class="btn_date week">${op:message('M00222')}</a> 
							<a href="#" class="btn_date month1">${op:message('M00029')}</a> 
							<a href="#" class="btn_date month2">${op:message('M00223')}</a> 
							<a href="#" class="btn_date all">${op:message('M00039')}</a> 
						</span>
					</div>							
				</td>	
			 </tr>
			 <tr>
			 	<td class="label">${op:message('M01665')}</td>
			 	<td>
					<div>
						<input type="radio" name="memoType" ${op:checked("", searchParam.memoType)} value=""/><label>전체</label>
						<input type="radio" name="memoType" ${op:checked("0", searchParam.memoType)} value="0"/><label>일반</label>
						<input type="radio" name="memoType" ${op:checked("1", searchParam.memoType)} value="1" /><label>주문</label>
					</div>							
				</td>	
			 </tr>
			 <tr>
			 	<td class="label">처리상태</td>
			 	<td>
					<div>
						<label><input type="radio" name="claimStatus" ${op:checked("", searchParam.claimStatus)} value=""/>전체</label>
						<label><input type="radio" name="claimStatus" ${op:checked("1", searchParam.claimStatus)} value="1"/>처리중</label>
						<label><input type="radio" name="claimStatus" ${op:checked("2", searchParam.claimStatus)} value="2" />처리완료</label>
					</div>							
				</td>	
			 </tr>
		</tbody>					 
	</table>			
	
	<!-- 버튼시작 -->	
	<div class="btn_all">
		<div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm" onclick="claimReset(${userId});"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
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
				<td colspan="3" id="claim-memo-list">
					
				</td>
			</tr>    
		</tbody>
	</table> 
</form>

<div style="display: none;">
	<span id="today">${today}</span>
	<span id="week">${week}</span>
	<span id="month1">${month1}</span>
	<span id="month2">${month2}</span>
</div>

<script type="text/javascript">


$(function(){
	
	Manager.activeUserDetails("memo");
	claimMemoList(1);

	$(".btn_date").on('click',function(){

		var $id = $(this).attr('class').replace('btn_date ','');		// id[0] : type, id[1] : value

		if ($id == 'all') {
			
			$("input[type=text]",$(this).parent().parent()).val('');
			
		} else {

			var today = $("#today").text();

			var date1 = '';
			var date2 = '';

			if ($id == 'today') {
				date1 = today;
				date2 = today;
			} else {
				date1 = $("#"+$id).text();
				date2 = today;
			}

			$("input[type=text]",$(this).parent().parent()).eq(0).val(date1);
			$("input[type=text]",$(this).parent().parent()).eq(1).val(date2);
			
		}
		
	});
	
	$('#searchParam').validator({
		'submitHandler' : function() {
			claimMemoList(1);
			return false;
		}
	});
	
	claimMemoList(1);

});

function claimMemoList(page) {
	if (page == undefined) {
		page = 1;
	}
	
	$('#searchParam').find('input[name="page"]').val(page);
	$.get('/opmanager/user/popup/claim-memo/list', $('#searchParam').serialize(), function(html) {
		$('#claim-memo-list').html(html);
	});
}

function claimReset(userId) {
	location.href = '/opmanager/user/popup/claim-memo-list/'+userId;
}

function goUrl(orderCode) {
	opener.location.href = '/opmanager/order/new-order/order-detail/0/'+orderCode;
}

</script>