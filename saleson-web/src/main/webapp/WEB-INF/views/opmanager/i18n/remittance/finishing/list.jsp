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
<h3>정산마감 내역</h3>

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
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.sellerPage ? '/seller' : '/opmanager'}/remittance/finish/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}<!-- 검색 --></button>
			</div>
		</div>
		
		
	</div>
	
</form:form>

<div class="board_list">
	
	
	<table class="board_list_table" summary="정산내역 리스트">
		<caption>정산내역 리스트</caption>
		<colgroup>
			<col style="width:10%;" />
			<col style="width:8%;" />
			<col style="width:8%;" />
			<col style="width:8%;" />
			<col style="width:8%;" />
			<col />
			<col style="width:8%;" />
			<col style="width:8%;" />
			<col style="width:8%;" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">마감일자</th>
				<th scope="col">업체명</th>
				<th scope="col">담당자명</th>
				<th scope="col">연락처</th>
				<th scope="col">휴대폰</th>
				<th scope="col">계좌번호</th>
				<th scope="col">정산금액</th>
				<th scope="col">확인자명</th>
				<th scope="col">비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ list }" var="item" varStatus="index">
				
				<tr>
					<td>${op:date(item.finishingDate)}</td>
					<td>${item.sellerName}</td>
					<td>${item.userName}</td>
					<td>${item.telephoneNumber}</td>
					<td>${item.phoneNumber}</td>
					<td>[${item.bankName}] ${item.bankAccountNumber} - ${item.bankInName}</td>
					<td class="amount text-right">${op:numberFormat(item.finishingAmount)}원</td>
					<td>${item.finishingManagerName}</td>
					<td>
						<a href="javascript:;" onclick="detailView('${requestContext.sellerPage ? '/seller' : '/opmanager'}', '${item.sellerId}', '${item.remittanceId}');" class="btn btn-gradient btn-xs">상세보기</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${empty list}">
	<div class="no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. --> 
	</div>
	</c:if>
		
	<div class="btn_all">
		<div class="btn_left mb0">

		</div>
		<div class="btn_right mb0">
			 
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
		
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
		
		$.each($('td.amount'), function(){
			if ($.trim($(this).html()) == '0원') {
				$(this).css('color', '#bfbebe');
			}	
		});
		
	}); 
	
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
	
	<%--이상우 [2017-04-25 추가] 상세페이지 이동 시 검색한 날짜를 parameter로 가져가도록 수정--%>
	function detailView(type, sellerId, remittanceId) {
		location.href = type+"/remittance/finish/detail/"+sellerId+"/"+remittanceId+"?"+$('form#remittanceParam').serialize();
	}
	
</script>




