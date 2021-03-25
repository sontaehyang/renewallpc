<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>

<table class="board_list_table">
	<colgroup>
		<col style="width:15%;">
		<col style="width:10%;">
		<col style="width:10%;">
		<col style="width:auto;">  
	</colgroup>
	<thead>
		<tr>
			<th>작성일</th>
			<th>작성자</th>
			<th>상태</th>
			<th>상담메모</th>
		</tr>
	</thead>  
	<tbody>
		<c:forEach items="${list}" var="memo">
			<tr> 
				<td>${op:datetime(memo.createdDate)}</td>
				<td>${memo.managerLoginId}</td>
				<td>${memo.claimStatusLabel}</td>
				<td class="text-left"><a href="javascript:Common.popup('/opmanager/order/${pageType}/claim-memo/update/${memo.claimMemoId}', 'claim_write', 500, 600, 1)">${op:nl2br(memo.memo)}</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<c:if test="${empty list}">
<div class="no_content">
	${op:message('M00473')} <!-- 데이터가 없습니다. --> 
</div>
</c:if>	
<page:pagination-manager /> 