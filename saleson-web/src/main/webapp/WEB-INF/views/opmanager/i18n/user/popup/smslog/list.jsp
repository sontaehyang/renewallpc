<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>

<div class="count_title mt20">
	<h5>
		${op:message('M00039')} : ${op:numberFormat(totalCount)}${op:message('M00272')} <!-- 건 --> │
		${op:message('M00271')} : ${op:numberFormat(count)}${op:message('M00272')} <!-- 건 -->
	</h5>	 
</div>

<table class="board_list_table">
	<colgroup>
		<col style="width:15%;">
		<col style="width:10%;">
		<col style="width:10%;">
		<col style="width:auto;">  
		<col style="width:10%;">
	</colgroup>
	<thead>
		<tr>
			<th>발송일</th>
			<th>내용</th>
			<th>발송유형</th>
		</tr>
	</thead>  
	<tbody>
		<c:forEach items="${list}" var="log">
			<tr> 
				<td>${op:replaceAll(log.created,"T"," ")}</td>
				<td class="text-left">${op:nl2br(log.message)}</td>
				<td>
					<c:forEach var="list" items="${smsTemplateCodeList }">
						<c:if test="${log.templateCode == list.key }">
							${list.value}
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>


<div style="display: none;">
	<span id="today">${today}</span>
	<span id="week">${week}</span>
	<span id="month1">${month1}</span>
	<span id="month2">${month2}</span>
</div>

<c:if test="${empty list}">
<div class="no_content">
	${op:message('M00473')} <!-- 데이터가 없습니다. --> 
</div>
</c:if>	
<page:pagination-manager /> 