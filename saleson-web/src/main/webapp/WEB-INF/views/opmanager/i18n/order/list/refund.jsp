<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<style type="text/css">
	.board_list_table th{
		text-align:center;
	}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3><span>환불내역 관리</span></h3>

<form:form modelAttribute="orderRefundParam" action="" method="get">
		
	<div class="board_write">
	
		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				<tr>  
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 --> 
				 	<td colspan="3"> 
				 		<div>
				 			<form:select path="searchDateType">
				 				<form:option value="R.CREATED_DATE" label="신청일" />
				 				<form:option value="R.UPDATED_DATE" label="처리완료일" />
				 			</form:select>
							<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<span class="wave">~</span>							
							<span class="datepicker"><form:input path="searchEndDate" class="datepicker" maxlength="8" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<span class="day_btns">
								<a href="javascript:;" class="btn_date clear">전체</a> 
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
				<tr>
				 	<td class="label">${op:message('M00011')} <!-- 검색구분 --> </td>
				 	<td>
				 		<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="USER_NAME" label="주문자명" />
								<form:option value="REFUND_CODE" label="환불번호" />
								<form:option value="ORDER_CODE" label="주문번호" />
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 	<td class="label">환불상태 </td>
				 	<td>
				 		<div>
							<form:radiobutton path="refundStatusCode" value="1" label="신청" />
							<form:radiobutton path="refundStatusCode" value="2" label="완료" /> 
						</div>
				 	</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/order/refund/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}<!-- 검색 --></button>
			</div>
		</div>
	</div>
	
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



<div class="board_list">
	<div class="btn_all">
		<div class="btn_left mb0">

		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 
	<form id="listForm">
		<table class="board_list_table" summary="주문내역 리스트">
			<caption>주문내역 리스트</caption>
			<thead>
				<tr>
					<th scope="col">상태</th>
					<th scope="col">환불번호</th>
					<th scope="col">주문번호</th>
					<th scope="col">구매자</th>
					<th scope="col">신청자</th>
					<th scope="col">신청일</th>
					<th scope="col">처리자</th>
					<th scope="col">처리일</th>
				</tr>
				<c:forEach items="${ list }" var="refund">
					<tr>
						<td>${refund.refundStatusLabel}</td>
						<td><a href="/opmanager/order/refund/detail/${refund.refundCode}">${refund.refundCode}</a></td>
						<td>
							<%-- <a href="javascript:Manager.orderDetails('refund', '${refund.orderSequence}', '${refund.orderCode}', '1')">${refund.orderCode}</a> --%>
							<a href="javascript:Link.view('${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/all/order-detail/${refund.orderSequence}/${refund.orderCode}', 1)">${refund.orderCode}</a>
						</td>
						<td>
							${refund.buyerName}
							<c:if test="${not empty refund.loginId}">
								<p><a href="javascript:Manager.userDetails(${refund.userId})">[${refund.loginId}]</a></p>
							</c:if>
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty refund.requestManagerUserName}">${refund.requestManagerUserName}</c:when>
								<c:otherwise>구매자</c:otherwise>
							</c:choose>
						</td>
						<td>${op:datetime(refund.createdDate)}</td>
						<td>${refund.processManagerUserName}</td>
						<td>${op:datetime(refund.updatedDate)}</td>
					</tr>
				</c:forEach>
			</thead>
			<tbody>
				
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
			
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 
	
	<page:pagination-manager /> 
	
	
	
</div> 
<script type="text/javascript"> 
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	});
</script>
