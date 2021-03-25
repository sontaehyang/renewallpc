<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
		
	<form:form modelAttribute="ClaimMemoParam" method="get" enctype="multipart/form-data">
		<!--자주 묻는 질문관리 시작-->
		<h3><span>${op:message('M00674')}</span></h3> <!-- 자주 묻는 질문관리 --> 
		<div class="board_write">
			<table class="board_write_table" summary="${op:message('M00675')}"> <!-- 자주 묻는 질문 --> 
				<caption>${op:message('M00675')}</caption>
				<colgroup>
					<col style="width:150px;" />
					<col />
					<col style="width:150px;" />
					<col />
				</colgroup>
				<tbody>
					 <tr>
					 	<td class="label">${op:message('M00276')}</td>
					 	<td colspan="3">
							<div class="search-date">
								<span class="datepicker"><form:input type="text" path="startDate" Class="datepicker optional " title="${op:message('M00225')}" /></span>
								<span class="wave">~</span>
								<span class="datepicker"><form:input type="text" path="endDate" Class="datepicker optional " title="${op:message('M00226')}" /></span>
								
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
					 	<td class="label">${op:message('M00011')}</td> <!-- 검색구분 -->  
					 	<td colspan="3">
					 		<div>
								<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드 선택 --> 
									<form:option value="USER_NAME">${op:message('M00005')}</form:option> <!-- 이름 --> 
									<form:option value="ORDER_CODE">${op:message('M00013')}</form:option> <!-- 주문번호 --> 
								</form:select> 
								<form:input type="text" path="query" class="three" title="${op:message('M00021')}" /> <!-- 검색어 입력 --> 
							</div>
					 	</td>	
					 </tr>
					  <tr>
					 	<td class="label">처리상태</td> <!-- 처리상태 --> 
					 	<td>
					 		<div>
					 			<form:radiobutton path="claimStatus" label="전체" value=""/>
					 			<form:radiobutton path="claimStatus" label="처리중" value="1"/>
					 			<form:radiobutton path="claimStatus" label="처리완료" value="2"/>
							</div>
					 	</td>	
					 	<td class="label">${op:message('M01665')}</td> <!-- 상담구분 --> 
					 	<td>
					 		<div>
					 			<form:radiobutton path="memoType" value="" label="전체"/>
					 			<form:radiobutton path="memoType" value="0" label="일반"/>
					 			<form:radiobutton path="memoType" value="1" label="주문"/>
							</div>
					 	</td>	
					 </tr>
				</tbody>					 
			</table>								 							
		</div> <!-- // board_write -->

		<!-- 버튼시작 -->	
		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/claim-memo/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>		 
		<!-- 버튼 끝-->
		
		
		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${totalCount} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
			<span>
				${op:message('M00052')} : 
				<form:select path="itemsPerPage" title="${op:message('M00239')}"> <!-- 화면출력 -->
					<form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 --> 
					<form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
					<form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 --> 
					<form:option value="100" label="${op:message('M00243')}" /> <!-- 100개 출력 --> 
				</form:select>
			</span>
		</div>
	</form:form>
	
	<form id="listForm">
		<div class="board_write">
			<table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 --> 
				<caption>${op:message('M00273')}</caption>
				<colgroup>
					<col style="width:2%;" />
					<col style="width:10%;" />					
					<col style="width:10%;" />
					<col style="width:10%;" />
					<col style="width:15%;" />
					<col style="width:10%;" />
					<col style="width:auto;" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">${op:message('M00200')}</th> <!-- 순번 --> 
						<th scope="col">${op:message('M00276')}</th> <!-- 작성일 --> 
						<th scope="col">${op:message('M00005')}</th> <!-- 이름 --> 
						<th scope="col">${op:message('M00472')}</th> <!-- 작성자 --> 
						<th scope="col">${op:message('M00013')}</th> <!-- 주문번호 --> 
						<th scope="col">${op:message('M01476')}</th> <!-- 상태 --> 
						<th scope="col">상담메모</th> <!-- 상담메모 --> 								
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ClaimMemolist}" var="memo" varStatus="i">
						<tr>
							<!--td><input type="checkbox" name="id" id="check" value="${memo.claimMemoId}" title="${op:message('M00169')}" /></td> -->
							<td>${pagination.itemNumber - i.count}</td>
							<td>${op:datetime(memo.createdDate)}</td>
							<td><a href="javascript:Manager.userDetails(${memo.userId})">${memo.userName}</a></td>
							<td>${memo.managerLoginId}</td>
							<td>
								<c:if test="${memo.orderCode != null}">
									<a href="/opmanager/order/new-order/order-detail/0/${memo.orderCode}"><span>${memo.orderCode}</span></a>
									<a class="btn btn-gradient btn-xs" href="/opmanager/order/new-order/order-detail/0/${memo.orderCode}" target="blank"><span></span>새창</button>
								</c:if>
							</td>
							<td>${memo.claimStatusLabel}</td>
							<td class="text-left"><a href="javascript:Common.popup('/opmanager/user/popup/claim-update/${memo.claimMemoId}', 'claim_write', 500, 600, 1)">${op:nl2br(memo.memo)}</a></td>		
						</tr>
					</c:forEach>
				</tbody>
			</table>				 
			<page:pagination-manager />
		</div><!--// board_write E-->
		
		<c:if test="${empty ClaimMemolist}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>
		</c:if>
		
			<!-- div class="btn_all">
				<div class="btn_left mb0">
					<a id="delete_list_data" href="#" class="ctrl_btn"><span>${op:message('M00074')}</span></a> <!-- 삭제 --> 
				<!-- /div>
				<div class="btn_right mb0">
					<a href="" class="ctrl_btn"><span>${op:message('M00088')}</span></a> <!-- 등록 --> 
			<!-- /div>-->
		</div>
			
	</form>

<div style="display: none;">
	<span id="today">${today}</span>
	<span id="week">${week}</span>
	<span id="month1">${month1}</span>
	<span id="month2">${month2}</span>
</div>	
		
		
		
<script type="text/javascript">
$(function() {
	
	//데이터 출력량 적용
	$('#itemsPerPage').on("change", function(){
		$('#ClaimMemoParam').submit();	
	});
	
	$(".btn_date").on('click',function() {

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

			$("input[type=text]", $(this).parent().parent()).eq(0).val(date1);
			$("input[type=text]", $(this).parent().parent()).eq(1).val(date2);
			
		}
	});
	
});

function claimMemoList(page) {
	if (page == undefined) {
		page = 1;
	}
	
	$('#searchParam').find('input[name="page"]').val(page);
	$('#ClaimMemoParam').submit();
}
</script>