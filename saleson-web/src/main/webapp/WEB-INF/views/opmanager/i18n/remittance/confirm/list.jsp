<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3>정산확정 내역</h3>

<form:form modelAttribute="remittanceParam" action="" method="get">

	<div class="board_write">

		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				<tr>
				 	<td class="label">정산 확정일</td>
				 	<td>
				 		<div>
				 			<span class="datepicker"><form:input path="startDate" class="datepicker" maxlength="8" title="정산일자 시작일" /><!-- 정산일자 시작일 --></span>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="endDate" class="datepicker" maxlength="8" title="정산일자 종료일" /><!-- 정산일자 종료일 --></span>
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
				 <c:if test="${requestContext.sellerPage == false}">
				 <tr>
				 	<td class="label">판매자</td>
					<td>
						<div>
							<form:select path="sellerId">
								<form:option value="0">${op:message('M00039')}</form:option>
								<c:forEach items="${sellerList}" var="list" varStatus="i">
									<c:if test="${list.sellerId != remittanceParam.defaultOpmanagerSellerId}">
										<form:option value="${list.sellerId}">[${list.loginId}] ${list.sellerName}</form:option>
									</c:if>
								</c:forEach>
							</form:select>
							<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"> <span class="glyphicon glyphicon-search"></span> 검색</a>
						</div>
					</td>
				</tr>
				</c:if>
			</tbody>
		</table>


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

		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.sellerPage ? '/seller' : '/opmanager'}/remittance/confirm/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}<!-- 검색 --></button>
			</div>
		</div>

	</div>

</form:form>

<div class="board_list">

	<form id="listForm" action="${requestContext.managerUri}/remittance/confirm/list/update" method="post">
		<table class="board_list_table" summary="정산내역 리스트">
			<caption>정산내역 리스트</caption>
			<colgroup>
				<c:if test="${requestContext.sellerPage == true}">
				<col style="width:2%;" />
				</c:if>
				<col style="width:7%;" />
				<col style="width:8%;" />
				<col style="width:7%;" />
				<col style="width:7%;" />
				<col style="width:7%;" />
				<col />
			</colgroup>
			<thead>
				<tr>
					<c:if test="${requestContext.sellerPage == true}">
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					</c:if>
					<th scope="col">확정일자</th>
					<th scope="col">업체명</th>
					<th scope="col">담당자명</th>
					<th scope="col">연락처</th>
					<th scope="col">휴대폰</th>
					<th scope="col">계좌번호</th>
					<th scope="col">판매가</th>
					<th scope="col">수수료</th>
					<th scope="col">공급가</th>

					<th scope="col">판매자 할인</th>
					<th scope="col">판매자 ${op:message('M00246')}</th>
					<th scope="col">배송비</th>
					<th scope="col">추가금</th>
					<th scope="col">정산금액</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="item" varStatus="index">
					<c:set var="key">${item.sellerId}^^^${item.remittanceDate}</c:set>
					<tr>
						<c:if test="${requestContext.sellerPage == true}">
						<td>
							<input type="checkbox" name="id" value="${key}" />
							<input type="hidden" name="finishingRemittanceMap[${key}].amount" value="${item.itemRemittanceAmount + item.shippingTotalAmount + item.addPaymentTotalAmount}" />
						</td>
						</c:if>
						<td>${op:date(item.remittanceDate)}</td>
						<td>${item.sellerName}</td>
						<td>${item.userName}</td>
						<td>${item.telephoneNumber}</td>
						<td>${item.phoneNumber}</td>
						<td>[${item.bankName}] ${item.bankAccountNumber} - ${item.bankInName}</td>
						<td class="amount">${op:numberFormat(item.itemTotalCommissionBaseAmount)}원</td>
						<td class="amount">${op:numberFormat(item.itemTotalCommissionAmount)}원</td>
						<td class="amount">${op:numberFormat(item.itemTotalSupplyAmount)}원</td>
						<td class="amount">${op:numberFormat(item.itemTotalSellerDiscountAmount)}원</td>
						<td class="amount">${op:numberFormat(item.itemTotalSellerPointAmount)}P</td>
						<td class="amount">${op:numberFormat(item.shippingTotalAmount)}원</td>
						<td class="amount">${op:numberFormat(item.addPaymentTotalAmount)}원</td>

						<td class="amount">
							${op:numberFormat(item.itemRemittanceAmount + item.shippingTotalAmount + item.addPaymentTotalAmount)}원
							<c:if test="${(item.itemRemittanceAmount + item.shippingTotalAmount + item.addPaymentTotalAmount) != 0}">
								<a href="${requestContext.sellerPage ? '/seller' : '/opmanager'}/remittance/confirm/detail/view/${item.sellerId}/${item.remittanceDate}?${queryString}" class="btn btn-gradient btn-xs">상세</a>
							</c:if>
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

	<div class="btn_all">
		<div class="btn_left mb0">
			<c:if test="${requestContext.sellerPage == true}">
			<button type="button" class="btn btn-default btn-sm finishing-remittance">정산 확인</button>
			</c:if>
		</div>
		<div class="btn_right mb0">

		</div>
	</div>

	<page:pagination-manager />
</div>

<div class="board_guide ml10">
	<p class="tip">Tip</p>
	<p class="tip">정산 금액은 환불이 완료되어 취소 처리된 금액은 미포함된 금액입니다.</p>
	<p class="tip">반품 진행중인 주문건은 정산금액에 포함되고 정산 마감후 환불 완료 되면 다음 정산에 마이너스 정산이 포함되어 정산됩니다.</p>
	<p class="tip">공급가는 "(판매가 - 수수료)" 입니다.</p>
	<p class="tip">상품 정산 금액은 "(공급가 - 판매자 할인 - 판매자 ${op:message('M00246')})" 입니다.</p>
	<p class="tip">정산금액은 "(상품 정산금액 + 배송비 + 추가금)" 입니다.</p>
</div>
<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');

		$.each($('td.amount'), function(){
			if ($.trim($(this).html()) == '0원') {
				$(this).css('color', '#bfbebe');
			}
		});

		<c:if test="${requestContext.sellerPage == true}">
		$('.finishing-remittance').on('click', function() {

			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}

			if (confirm("선택하신 정산정보를 확인처리 하시겠습니까?")) {
				$form.submit();
			}

		});
		</c:if>
	});

	<c:if test="${requestContext.sellerPage == false}">
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
	</c:if>
</script>




