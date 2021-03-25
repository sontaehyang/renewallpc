<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">
.options {
	margin:0px !important;
	padding-bottom:0px !important;
} 
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3 class="mt10">[${seller.sellerName}] 배송비 정산 상세 - ${op:date(remittanceParam.startDate)} ~ ${op:date(remittanceParam.endDate)}</h3>

<div class="board_write">
	
	<div class="board_list">
		<table class="board_write_table">
			<colgroup>
				<col style="width: 8%;" />
				<col style="width: 8%;" />
				<col />
				<col style="width: 8%;" />
				<col style="width: 8%;" />
				<col />
			</colgroup>
			<tbody>
				<tr>
					<th class="label" rowspan="3">담당자</th>
					<th class="label">이름</th>
					<td>
						<div>${seller.userName}</div>
					</td>
					<th class="label" rowspan="3">주문 담당자</th>
					<th class="label">이름</th>
					<td>
						<div>${seller.secondUserName}</div>
					</td>
				</tr>
				<tr>
					<th class="label">전화번호</th>
					<td>
						<div>${seller.telephoneNumber}</div>
					</td>
					<th class="label">전화번호</th>
					<td>
						<div>${seller.secondTelephoneNumber}</div>
					</td>
				</tr>
				<tr>
					<th class="label">휴대전화</th>
					<td>
						<div>${seller.phoneNumber}</div>
					</td>
					<th class="label">휴대전화</th>
					<td>
						<div>${seller.secondPhoneNumber}</div>
					</td>
				</tr>
			</tbody>
		</table>
		
		<form:form modelAttribute="remittanceParam" method="post">
			<form:hidden path="sellerId" />
			<form:hidden path="startDate" />
			<form:hidden path="endDate" />
			
			<div class="count_title mt20">
				<h5>
					${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
				</h5>
				<span>
					${op:message('M00052')} : <!-- 출력수 --> 
					<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
						onchange="$('form#remittanceParam').submit();"> <!-- 화면 출력수 -->
						<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="200" label="200${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="500" label="500${op:message('M00053')}" /> <!-- 개 출력 -->
					</form:select>
				</span>
			</div>
		</form:form>
		
		<form id="listForm" method="post">
			<input type="hidden" name="sellerId" value="${remittanceParam.sellerId}" />
			<input type="hidden" name="startDate" value="${remittanceParam.startDate}" />
			<input type="hidden" name="endDate" value="${remittanceParam.endDate}" />
			<input type="hidden" name="conditionType" />
			<table class="board_list_table" summary="정산내역 리스트">
				<caption>정산내역 리스트</caption>
				<colgroup>
					<col style="width:2%;" />
					<col />
					<col />
					<col />
					<col />
					<col />
					<col style="width:8%;" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" id="check_all" /></th>
						<th scope="col">주문번호</th>
						<th scope="col">상태</th>
						<th scope="col">결제금액</th>
						<th scope="col">할인금액</th>
						<th scope="col">정산금액</th>
						<th scope="col">정산예정일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ list }" var="item" varStatus="index">
						<c:set var="key">${item.orderCode}^^^${item.orderSequence}^^^${item.shippingSequence}</c:set>
						<tr ${item.returnFlag == 'Y' ? 'style="background-color:#f3aaaa"' : ''}>
							<td><input type="checkbox" name="id" value="${key}" /></td>
							<td>${item.orderCode}</td>
							<td>
								<c:choose>
									<c:when test="${empty item.remittanceDate}">예정</c:when>
									<c:otherwise><strong>확정</strong>
										<p>(${op:date(item.remittanceDate)})</p>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="amount text-right">${op:numberFormat(item.payShipping)}원</td>
							<td class="amount text-right">${op:numberFormat(item.discountShipping)}원</td>
							<td class="amount text-right"><c:if test="${item.returnFlag == 'Y'}">-</c:if>${op:numberFormat(item.remittanceAmount)}원</td>
							<td>
								<input type="text" name="editShippingRemittanceMap[${key}].remittanceExpectedDate" class="required" value="${item.remittanceExpectedDate}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		
		<c:if test="${empty list}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. --> 
			</div>
		</c:if>
	</div>
	
	
	<div class="btn_all">
		<div class="btn_left mb0">
			<button type="button" class="btn btn-default btn-sm update-remittance">정보 수정</button>
			<button type="button" class="btn btn-default btn-sm confirm-remittance">정산 확정</button>
		</div>
		<div class="btn_right mb0">
			<a href="/opmanager/remittance/expected/list?${queryString}" class="btn btn-active btn-sm">목록으로</a>
		</div>
	</div> 
	
	<page:pagination-manager /> 
</div>

<script type="text/javascript"> 
	$(function() {
		
		$('.update-remittance').on('click', function() { 
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			
			if (validate() == false) {
				return;
			}
			
			if (confirm("선택하신 정산정보를 수정 하시겠습니까?\n이미 확정된 내역에 대해서는 대기상태로 변경됩니다.")) {
				Common.removeNumberComma(); 
				$form.find('input[name="conditionType"]').val('update');
				$form.submit();
			}
			
			
			
		});
		
		$('.confirm-remittance').on('click', function() { 
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			
			if (validate() == false) {
				return;
			}
			
			if (confirm("선택하신 정산정보를 확정 하시겠습니까?\n확정 일자는 설정하신 정산 예정일로 설정 됩니다.")) {
				Common.removeNumberComma(); 
				$form.find('input[name="conditionType"]').val('confirm');
				$form.submit();
			}
			
		});
		
		$.each($('td.amount'), function(){
			if ($.trim($(this).html()) == '0원') {
				$(this).css('color', '#bfbebe');
			}	
		});
	}); 
	
	function validate() {
		
		var $form = $('#listForm');
		var isError = false;
		$.each($form.find('input[name=id]:checked'), function(){
			var key = $(this).val();
			
			$date = $form.find('input[name="editShippingRemittanceMap['+key+'].remittanceExpectedDate"]');
			if ($date.size() == 0) {
				isError = true;
				return false;
			} else {
				if (Common.validateDate($date.val()) == false) {
					isError = true;
					alert('입력하신 날짜를 확인바랍니다.');
					$date.focus();
					return false;
				}				
			}
		});
		
		if (isError == true) {
			return false;
		}
		
		return true;
	}
</script>