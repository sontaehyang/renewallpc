<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<c:set var="title">${op:message('M00246')}</c:set>
	<c:if test="${pointType == 'shipping'}">
		<c:set var="title">배송비쿠폰</c:set>
	</c:if>
	
	<h2><span>¶ ${title}</span></h2>

	<div class="sort_area">
		<div class="right">
			<button type="button" class="btn btn-active btn-sm" onclick="Common.popup('/opmanager/user/popup/point-create/${pointType}/${user.userId}', 'point-create', 600, 550);">${title} 지급/차감</button> <!-- 포인트지급 -->  <!-- 차감 -->
		</div>
	</div>

	<div class="board_write">
		<table class="board_list_table" summary="처리내역 리스트">
			<caption>처리내역 리스트</caption>
			<colgroup>
				<col style="width:20%;">
				<col style="width:*;">
				<col style="width:20%;">
				<col style="width:15%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">${op:message('M00856')}</th> <!-- 처리일자 -->
					<th scope="col">${title} 지급/사용내역</th> <!-- 포인트 지급 --> <!-- 사용내역 -->
					<th scope="col">
					${op:message('M00860')}
					<c:choose>
						<c:when test="${pointType == 'shipping'}">(장)</c:when>
						<c:otherwise>/${op:message('M00862')}</c:otherwise>
					</c:choose>
					</th> <!-- 지급 --> <!-- 사용금액 -->
					<th scope="col">처리자</th> <!-- 지급 --> <!-- 사용금액 -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pointList}" var="list" varStatus="i">
					<tr>
						 <td>${op:datetime(list.createdDate)}</td>
						 <td class="tleft">
						 	${list.reason}
						 </td> 
						 <c:if test="${list.sign == '-' }">
							 <td class="red">
							 	- ${op:numberFormat(list.point)} 
							 	<c:choose>
									<c:when test="${pointType == 'shipping'}"> 장</c:when>
									<c:otherwise> ${title}</c:otherwise>
								</c:choose>
							 </td>
						 </c:if>
						 <c:if test="${list.sign == '+' }">
							 <td style="color: #0072bc;">
							 	<c:if test="${list.point > 0}">+</c:if> ${op:numberFormat(list.point)} 
							 	<c:choose>
									<c:when test="${pointType == 'shipping'}"> 장</c:when>
									<c:otherwise> ${title}</c:otherwise>
								</c:choose>
							 </td>
						 </c:if>
						 <td>
						 	${list.managerName}
						 </td>
					</tr>
				</c:forEach>
				
				<c:if test="${empty pointList}">
					<tr class="no_content">
						<td colspan="4">${op:message('M00473')}</td>
					</tr>
				</c:if>
							
				<tr>
					<th colspan="2">
						현재 보유한 ${title} <!--  포인트 --> 
					</th>
					<th> 
						${ op:numberFormat(pageAvilablePoint)}
						<c:choose>
							<c:when test="${pointType == 'shipping'}"> 장</c:when>
							<c:otherwise> ${title}</c:otherwise>
						</c:choose>
					</th>
					<th></th>
				</tr>
			</tbody>
		</table>				 
	</div> <!-- // board_write -->
	
	<page:pagination-manager />
	
	
<script type="text/javascript">
$(function() {
	
	Manager.activeUserDetails("${pointType}");
});
</script>
