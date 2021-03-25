<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<h2><span>¶ ${op:message('M00825')} <!-- 주문내역 --></span></h2>

<div class="f_left" style="width: 660px;">

	<table class="board_list_table" summary="상품리스트">
		<caption>상품리스트</caption>
		<colgroup>
			<col style="width: *;">
		</colgroup>
		<thead>
			<tr>
				<th>${op:message('M00825')}</th>		 <!-- 주문내역 -->				
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="f11">
					${op:message('M00839')} <!-- 총 주문 건 수 --> : <span>${ op:numberFormat(userOrderCountInfo.orderStatusAll) }</span>${op:message('M00272')} <!-- 건 -->  | 
					${op:message('M00380')} <!-- 주문완료 --> : <span>${ op:numberFormat(userOrderCountInfo.orderStatus50) }</span>${op:message('M00272')} <!-- 건 --> | 
					${op:message('M00033')} <!-- 입금대기 --> : <span>${ op:numberFormat(userOrderCountInfo.orderStatus0) }</span>${op:message('M00272')} <!-- 건 -->  | 
					${op:message('M00603')} <!-- 발송준비 --> : <span>${ op:numberFormat(userOrderCountInfo.orderStatus1) }</span>${op:message('M00272')} <!-- 건 --> | 
					${op:message('M00035')} <!-- 발송완료 --> : <span>${ op:numberFormat(userOrderCountInfo.orderStatus3) }</span>${op:message('M00272')} <!-- 건 -->  | 
					${op:message('M00802')}/${op:message('M00036')} <!-- 확인중/보류 --> : <span>${ op:numberFormat(userOrderCountInfo.orderStatus8 + userOrderCountInfo.orderStatus9) }</span>${op:message('M00272')} <!-- 건 -->  | 
					${op:message('M00037')} <!-- 취소 --> : <span>${ op:numberFormat(userOrderCountInfo.orderStatus99) }</span>${op:message('M00272')} <!-- 건 --></td>
			</tr>
		</tbody>
	</table>

</div> <!-- // f_left -->

<div class="f_right" style="width: 140px;">

	<table class="board_list_table" summary="상품리스트">
		<caption>배송완료 총 금액</caption>
		<colgroup>
			<col style="width: *;">
		</colgroup>
		<thead>
			<tr>
				<th>${op:message('M00855')}</th>		 <!-- 배송완료 총 금액 -->				
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="f11"><span>${ op:numberFormat(userOrderCountInfo.orderPayAmountStatus3) }</span>원</td>
			</tr>
		</tbody>
	</table>

</div> <!-- // f_right -->

<form:form modelAttribute="orderParam" action="" method="post">
	<div style="clear: both;">
	
		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				 <tr>
				 	<td class="label">${op:message('M00011')} <!-- 검색구분 --> </td>
				 	<td>
				 		<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="ORDER_ID" label="${op:message('M00013')}" /><!-- 주문번호 -->
								<form:option value="USER_NAME" label="${op:message('M00014')}" /><!-- 주문자명 --> 
								<form:option value="USER_ID" label="${op:message('M00015')}" /><!-- 주문자ID -->
								<form:option value="PHONE" label="${op:message('M00016')}" /><!-- 전화번호 -->
								<form:option value="RECEIVE_NAME" label="${op:message('M00017')}" /><!-- 수령인명 -->
								<form:option value="ITEM_NAME" label="${op:message('M00018')}" /><!-- 상품명 -->
								<form:option value="ITEM_CODE" label="${op:message('M00019')}" /><!-- 상품번호 -->
								<form:option value="EMAIL" label="${op:message('M00020')}" /><!-- 메일주소 -->
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
				 <tr>  
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 --> 
				 	<td>
				 		<div>
							<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="searchEndDate" class="datepicker" maxlength="8" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<span class="day_btns"> 
								<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 --> 
								<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 --> 
								<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 --> 
								<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 --> 
								<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 --> 
								<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
							</span>
						</div> 
				 	</td>	
				 </tr>
				 <c:if test="${ not empty searchOrderStatusList }">
					 <tr>
					 	<td class="label">${op:message('M00032')}<!-- 진행상태 --></td>
					 	<td>
					 		<div>
					 			<form:checkboxes items="${searchOrderStatusList}" path="searchOrderStatus" itemLabel="label" itemValue="value" />
							</div> 
					 	</td>	
					 </tr>
				 </c:if>
				 <tr>
				 	<td class="label">${op:message('M00038')} <!-- 결제 수단 --></td>
				 	<td>
				 		<div>
							<input type="radio" name="approvalType" id="pay01" value="all" 
								<c:if test="${ empty orderParam.approvalType }">checked="checked"</c:if> /> <label for="pay01">${op:message('M00039')} <!-- 전체 --></label>
							
							<c:forEach items="${ payTypeList }" var="item">
								<c:set var="payTypeLabel" value="${ item.label }" />
								<c:if test="${payTypeLabel == '現金代引き'}">
									<c:set var="payTypeLabel" value="代金引換" />
								</c:if>
								<c:if test="${payTypeLabel == '銀行振り込み'}">
									<c:set var="payTypeLabel" value="銀行振込" />
								</c:if>
								<form:radiobutton path="approvalType" label="${payTypeLabel}" value="${ item.value }" />
							</c:forEach>
						</div>  
				 	</td>	
				 </tr>
				 <tr>
				 	<td class="label">${op:message('M00044')} <!-- 소속팀 --></td>
				 	<td>
				 		<div>
							<!-- input type="checkbox" name="tempId" id="team01" /> <label for="team01">${op:message('M00039')} </label -->
							<c:forEach items="${categoryCategoriesTeamList}" var="item" varStatus="i">
								<form:checkbox path="searchTeam" label="${ item.name }" value="${ item.code }" /> &nbsp;
							</c:forEach>
						</div> 
				 	</td>
				 </tr>
				 
	 
				 <tr>
				 	<td class="label">${op:message('M00051')}<!-- 주문금액 --></td>
				 	<td>
				 		<div>
				 			<form:input path="searchStartPayAmount" class="_number" maxlength="9" /> ${op:message('M00049')} ${op:message('M00050')} <!-- 원 이상 --> 
							<span class="wave">~</span>
							<form:input path="searchEndPayAmount" class="_number"  maxlength="9" /> ${op:message('M00049')} <!-- 원 -->
					 	</div>
				 	</td>
				 </tr>
			</tbody>					 
		</table>
									 							
	</div> <!-- // board_write -->
	
	<!-- 버튼시작 -->	
	<div class="btn_all">
		<div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/user/popup/order/${user.userId}'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
		</div> 
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
		</div>
	</div>		 
	<!-- 버튼 끝-->

	<div class="count_title mt20">
		<h5>
			${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
		</h5>
		<span>
			${op:message('M00052')} : <!-- 출력수 --> 
			<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
				onchange="$('form#orderParam').submit();"> <!-- 화면 출력수 -->
				<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="200" label="200${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="500" label="500${op:message('M00053')}" /> <!-- 개 출력 -->
			</form:select>
		</span>
	</div>
</form:form> 

<form id="listForm" name="listForm">
	<div class="sort_area mt15">
		<div class="left">
			<span>${op:message('M00804')} <!-- 선택한 주문서를 일괄 --></span> 
			<c:if test="${ searchOrderStatus != '7' }">	
				<select title="주문상태" id="orderStatus1">
					<option value="50">${op:message('M00380')}</option> <!-- 주문완료 -->
					<option value="0">${op:message('M00033')}</option> <!-- 입금대기 -->
					<option value="1">${op:message('M00603')}</option> <!-- 발송준비 -->
					<option value="3">${op:message('M00035')}</option> <!-- 발송완료 -->
					<option value="8">${op:message('M00802')}</option> <!-- 확인중 -->					
					<option value="9">${op:message('M00036')}</option> <!-- 보류 -->
				</select>
				<button type="button" id="change_order_status1" class="btn btn-dark-gray btn-sm">${op:message('M00433')}</button> <!-- 변경 -->
			</c:if>
			<button type="button" class="btn ctrl_btn order_print">${op:message('M00343')}</button> <!-- 주문서 인쇄 -->
			<button type="button" class="btn ctrl_btn order_mail">${op:message('M00803')}</button> <!-- 주문 메일 발송 -->
		</div>
	</div> 
	<div class="board_write">
		<table class="board_list_table" summary="주문내역 리스트">
			<caption>주문내역 리스트</caption>
			<colgroup>
				<col style="width:2%;" />
				<col style="width:3%;" />
				<col style="width:4%;" />
				<col style="width:4%;" />
				<col />
				<col style="width:6%;" /> 
				<col style="width:7%;" />
				<col style="width:6%;" />
				<col style="width:6%;" />
				<col style="width:6%;" />
				<col style="width:6%;" />
				<col style="width:6%;" />
				<col style="width:6%;" />
				<col style="width:6%;" />
				<col style="width:6%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">No</th>
					<th scope="col">${op:message('M00056')} <!-- 출력 --></th>							
					<th scope="col">${op:message('M00057')} <!-- 메일 --></th>
					<th scope="col">${op:message('M00013')}/${op:message('M00058')}<!-- 주문번호/주문구분 --></th>
					<th scope="col">${op:message('M00060')} <!-- 결제방법 --></th>
					<th scope="col">${op:message('M00062')} <!-- 배송지정일 --></th>
					
					
					<th scope="col">${op:message('M00064')} <!-- 소계 --></th>
					<th scope="col">${op:message('M00065')} <!-- 소비세 --></th>
					<th scope="col">${op:message('M00067')} <!-- 배송비 --></th>
					<th scope="col">${op:message('M00068')} <!-- 사용포인트 --></th>
					<th scope="col">추가 할인</th>
					<th scope="col">쿠폰 할인</th>
					<th scope="col">${op:message('M00069')} <!-- 결제금액 --></th>
					<th scope="col">${op:message('M00072')} <!-- 주문상태 --></th>							
				</tr>
	
			</thead>
			<tbody>
				<c:forEach items="${ orderList }" var="order" varStatus="i">
					<c:set var="vendor" value="${ order.orderVendor }"/>
					<c:set var="href" value="/opmanager/order/view?orderId=${ order.orderId }" />
					<tr>
						<td>
							<c:if test="${ !(vendor.orderStatus eq '99' || vendor.orderStatus eq '10' || vendor.orderStatus eq '16') }">
								<input type="checkbox" name="id" value="${ order.orderId }" />
							</c:if>
						</td>
						<td>${pagination.itemNumber - i.count}</td>
						<td>
							<c:choose>
								<c:when test="${ vendor.printFlag eq 'Y' }">O</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${ vendor.mailSendFlag eq 'Y' }">
									<img src="/content/opmanager/images/icon/icon_email.png" alt="email" />
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</td> 
						
						<td>
							<div><a href="${ href }" target="_blank" onclick="Common.popup(this.href, 'orderView', 1200, 1000, 1); return false;"> 
								${ order.orderCode } <br/>
								<c:choose>
									<c:when test="${ order.hpMailFlag eq 'Y' }">MOBILE</c:when>
									<c:otherwise>E-Mail</c:otherwise>
								</c:choose>
								<p class="gray">${ op:datetime(order.createdDate) }</p>
							</a></div> 
						</td>
						<td>${ order.approvalTypeLabel }</td>
						<td>${ order.deliveryPrearrangedDate }</td>
					
						<td>${ op:numberFormat(vendor.sumItemPrice) }</td>
						<td>${ op:numberFormat(vendor.sumExcisePrice) }</td>
						<td>${ op:numberFormat(vendor.sumDeliveryCharge) }</td>
						<td>${ op:numberFormat(vendor.sumUsePoint) }</td>
						<td>${ op:numberFormat(vendor.vendorAddDiscountAmount) }</td>
						<td>${ op:numberFormat(order.cartCouponDiscountAmount + vendor.sumItemCouponDiscountAmount) }</td>
						<td>${ op:numberFormat(order.orderPayAmount - vendor.vendorAddDiscountAmount + vendor.vendorAddDeliveryExtraCharge) }</td>
						<td>${ vendor.orderStatusLabel }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		
		<c:if test="${empty orderList}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. --> 
			</div>
		</c:if>			 
	</div><!--// board_write E-->
	
	<div class="sort_area mt15">
		<div class="left">
			<span>${op:message('M00804')} <!-- 선택한 주문서를 일괄 --></span> 
			<c:if test="${ searchOrderStatus != '7' }">	
				<select title="주문상태" id="orderStatus2">
					<option value="50">${op:message('M00380')}</option> <!-- 주문완료 -->
					<option value="0">${op:message('M00033')}</option> <!-- 입금대기 -->
					<option value="1">${op:message('M00603')}</option> <!-- 발송준비 -->
					<option value="3">${op:message('M00035')}</option> <!-- 발송완료 -->
					<option value="8">${op:message('M00802')}</option> <!-- 확인중 -->					
					<option value="9">${op:message('M00036')}</option> <!-- 보류 -->
				</select>
				<button type="button" id="change_order_status2" class="btn btn-dark-gray btn-sm">${op:message('M00433')}</button> <!-- 변경 -->
			</c:if>
			<button type="button" class="btn ctrl_btn order_print">${op:message('M00343')}</button> <!-- 주문서 인쇄 -->
			<button type="button" class="btn ctrl_btn order_mail">${op:message('M00803')}</button> <!-- 주문 메일 발송 -->
		</div>
	</div>
	<input type="hidden" name="orderStatus" value="50" />
</form>
<page:pagination-manager /> 
 
<!--// 전체주문 내역 끝-->

<script type="text/javascript">
$(function() {
	// 메뉴 활성화
	Manager.activeUserDetails("order");
	
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		
	$('#orderStatus1, #orderStatus2').on('change', function(){
		$('#orderStatus1').val($(this).val());
		$('#orderStatus2').val($(this).val());
		$('input[name="orderStatus"]').val($(this).val());
	});
	
	$('.order_print').on('click', function(){
		$id = $('input[name="id"]:checked');
		if ($id.size() == 0) {
			alert('처리할 항목을 선택해 주세요.');
			return;
		}
		
		var popup = Common.popup('', 'print-view-list', 672, 900, 1);
		document.listForm.target = 'print-view-list';
		document.listForm.action = "/opmanager/order/order-print";
		document.listForm.submit();

		return;
	});
	
	$('.order_mail').on('click', function(){
		$id = $('input[name="id"]:checked');
		if ($id.size() == 0) {
			alert('처리할 항목을 선택해 주세요.');
			return;
		}
		
		var popup = Common.popup('', 'mail-send-list', 1000, 600, 1);
		document.listForm.target = 'mail-send-list';
		document.listForm.action = "/opmanager/order/order-mail";
		document.listForm.submit();

		return;
	});
	
	$('#change_order_status1, #change_order_status2').on('click', function(){
		$id = $('input[name="id"]:checked');
		if ($id.size() == 0) {
			alert('처리할 항목을 선택해 주세요.');
			return;
		}
		
		$.post('/opmanager/order/change-order-status', $("#listForm").serialize(), function(response){
				Common.responseHandler(response, function(response) {
					
					location.reload();
					
				}, function(response){
					
					alert(response.errorMessage);
					
				});
			});

		return;
	});
});
</script>
