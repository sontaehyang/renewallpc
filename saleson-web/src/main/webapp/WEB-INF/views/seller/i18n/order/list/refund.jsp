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
<h3><span>주문취소완료 목록</span></h3>

<form:form modelAttribute="orderParam" action="" method="post">
		
	<div class="board_write">
	
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
								<form:option value="ORDER_CODE" label="주문코드" />
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
				 <tr>
				 	<td class="label">환불상태 </td>
				 	<td>
				 		<div>
							<form:radiobutton path="refundStatusCode" value="1" label="환불요청" />
							<form:radiobutton path="refundStatusCode" value="2" label="환불완료" /> 
						</div>
				 	</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/seller/order/refund'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
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
	<form id="listForm">
		<table class="board_list_table" summary="주문내역 리스트">
			<caption>주문내역 리스트</caption>
			<colgroup>
				<col style="width:2%;" />
				<col style="width:10%;" />
				<col style="width:10%;" />
				<col style="width:15%;" />
				<col />				
				<col style="width:12%;" />
				<col style="width:8%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">처리상태</th>
					<th scope="col">주문번호</th>					
					<th scope="col">환불금액</th>
					<th scope="col">환불계좌</th>
					<th scope="col">환불생성일</th>
					<th scope="col">지연</th>
				</tr>
		
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="refund" varStatus="index">
					<tr>
						<td><input type="checkbox" name="id" value="${refund.orderCancelId}" /></td>
						<td>
							<c:if test="${refund.statusCode == 1}">환불요청</c:if>
							<c:if test="${refund.statusCode == 2}">환불완료</c:if>
						</td>
						<td>${refund.orderCode}</td>
						<td>${op:numberFormat(refund.returnAmount)}</td>
						<td>(${refund.returnBankName}) ${refund.returnVirtualNo} <br/>예금주 : ${refund.returnBankInName}</td>
						<td>${op:datetime(refund.createdDate)}</td>
						<td>
							<c:if test="${refund.delayDay > 0}">
								<strong style="color:red">+${refund.delayDay}</strong>
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

	<page:pagination-manager /> 
	
	
	
</div> 


<page:javascript>
<script type="text/javascript">

</script>
</page:javascript>