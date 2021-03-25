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
<h3 class="mt10">[${seller.sellerName}] 상품 정산 상세 - ${op:date(remittanceParam.startDate)} ~ ${op:date(remittanceParam.endDate)}</h3>

<div class="board_write">
	
	<div class="board_list">
		<c:if test="${requestContext.sellerPage == false}">
		
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
		
		</c:if>
		
		<form:form modelAttribute="remittanceParam" method="post">
			<form:hidden path="remittanceId" />
			
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
		
		
		<table class="board_list_table" summary="정산내역 리스트">
			<caption>정산내역 리스트</caption>
			<colgroup>
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col />
				<col style="width:12%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">주문번호</th>
					<th scope="col">상품 타입</th>
					<th scope="col">상품코드</th>
					<th scope="col">상품명</th>
					<th scope="col">옵션</th>
					<th scope="col">판매가</th>
					<th scope="col">수수료</th>
					<th scope="col">수수료율</th>
					<th scope="col">구분</th>
					<th scope="col">공급가</th>
					<th scope="col">판매자 할인</th>
					<th scope="col">판매자 ${op:message('M00246')}</th>
					<th scope="col">주문수량</th>
					<th scope="col">정산금액</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="item" varStatus="index">
					<tr>
						<td>${item.orderCode}</td>
						<td>
							<c:choose>
								<c:when test="${item.itemType == 'ITEM'}">상품</c:when>
								<c:otherwise>배송비</c:otherwise>
							</c:choose>
						</td>
						<td>${item.itemUserCode}</td>
						<td>${item.itemName}</td>
						<td>${shop:viewOptionText(item.options)}</td>
						<td class="amount text-right">${op:numberFormat(item.commissionBasePrice * item.quantity)}원</td>
						<td class="amount text-right">${op:numberFormat(item.commissionPrice * item.quantity)}원</td>
						<td class="amount text-right">${op:numberFormat(item.commissionRate)}%</td>
						<td>
							<c:choose>
								<c:when test="${item.commissionType == '3'}">공급가</c:when>
								<c:when test="${item.commissionType == '1' or item.commissionType == '2'}">수수료</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</td>
						<td class="amount text-right">${op:numberFormat(item.supplyPrice * item.quantity)}원</td>
						
						<td class="amount text-right">${op:numberFormat(item.sellerDiscountPrice * item.quantity)}원</td>
						<td class="amount text-right">${op:numberFormat(item.sellerPoint * item.quantity)}원</td>
						
						<td class="amount text-right">${op:numberFormat(item.quantity)}개</td>
						<td class="amount text-right">${op:numberFormat(item.remittancePrice * item.quantity)}원</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${empty list}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. --> 
			</div>
		</c:if>
	</div>
	
	
	<div class="btn_all">
		<div class="btn_left mb0">
		</div>
		<div class="btn_right mb0">
			<a href="${requestContext.getRequestUri()}/excel-download?${queryString}" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span> ${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
			<a href="${requestContext.sellerPage ? '/seller' : '/opmanager'}/remittance/finish/list?${queryString}" class="btn btn-active btn-sm">목록으로</a>
		</div>
	</div> 
	
	<page:pagination-manager /> 
</div>

<div class="board_guide ml10">
	<p class="tip">Tip</p>
	<p class="tip">공급가는 "(판매가 - 수수료)" 입니다.</p>
	<p class="tip">상품 정산 금액은 "(공급가 - 판매자 할인 - 판매자 ${op:message('M00246')})" 입니다.</p>
	<p class="tip">정산금액은 "(상품 정산금액 + 배송비 + 추가금)" 입니다.</p>
</div>


<script type="text/javascript"> 
	$(function(){
		$.each($('td.amount'), function(){
			if ($.trim($(this).html()) == '0원') {
				$(this).css('color', '#bfbebe');
			}	
		});
	}); 
</script>