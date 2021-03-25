<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>${op:message('M00163')}</span></h3>			
		<form id="listForm">
		<div class="board_write">
			<table class="board_list_table" summary="${op:message('M00163')}">
				<caption>${op:message('M00163')}</caption>
				<colgroup>
						<col style="width:50px;" />
						<col style="width:200px" />
						<col style="" />
						<col style="" />
						<col style="width:100px;" />
						<col style="width:120px;" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00164')}" /></th>
						<th scope="col">${op:message('M00165')}</th>
						<th scope="col">${op:message('M00166')}</th>
						<th scope="col">${op:message('M00167')} </th>
						<th scope="col">${op:message('M00082')} </th>
						<th scope="col">${op:message('M00168')} </th>							
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="accountNumber" varStatus="i">
						<tr>		
							<td><input type="checkbox" name="id" value="${accountNumber.accountNumberId}" title="${op:message('M00169')}" /></td>
							<td><div>${accountNumber.bankName}</div></td>
							<td><div>${accountNumber.accountNumber}</div></td>
							<td><div>${accountNumber.accountHolder}</div></td>
							<td><div>
								<c:choose>
									<c:when test="${ accountNumber.useFlag eq 'Y' }">
										${op:message('M00083')}
									</c:when>
									<c:otherwise>
										${op:message('M00089')}
									</c:otherwise>
								</c:choose>
							</div></td>							
							<td>
								<div>
									<a href="<c:url value="/opmanager/account-number/edit/${accountNumber.accountNumberId}" />" class="btn btn-gradient btn-xs">${op:message('M00087')}</a>
									<a href="javascript:;" onclick="accountNumberDelete('${accountNumber.accountNumberId}')" class="btn btn-gradient btn-xs">${op:message('M00074')}</a>
								</div>
							</td>
						</tr>
					</c:forEach>
			</table>				 
			<c:if test="${ empty list }">
				<div class="no_content">
					<p>${op:message('M00170')}</p>
				</div>
			</c:if>
		</div> 
		
		<!-- 버튼시작 -->		 
		<div class="btn_all mt0">
			<div class="btn_left">
				<button type="button" onclick="Common.deleteCheckedList(url('/opmanager/account-number/delete/list'))" class="btn btn-default btn-sm"><span>${op:message('M00576')}</span></button> <!-- 선택삭제 -->
			</div>
			<div class="btn_right">
				<a href="<c:url value="/opmanager/account-number/create" />" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a>
			</div>
		</div>			 

		<!-- 버튼 끝-->
		</form> 
		<page:pagination-manager /> 

		
<script type="text/javascript">
	function accountNumberDelete(accountNumberId){
		if (confirm("선택된 데이터를 삭제하시겠습니까?")) {
			location.href = "/opmanager/account-number/delete/" + accountNumberId;			
		}
	}
</script>