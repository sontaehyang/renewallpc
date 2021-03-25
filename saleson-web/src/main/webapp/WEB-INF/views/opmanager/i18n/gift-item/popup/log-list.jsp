<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<style>
	.popup_contents02 {
		width:1500px !important;
		padding-right:7px;
	}
</style>

<div class="popup_contents02">
	<h2><span>${giftItem.name}(${giftItem.code}) 변경사항</span></h2>

		<div class="board_write">
			<table class="board_list_table">
				<colgroup>
					<col style="width:30px;">
					<col style="width:auto;">
					<col style="width:100px;">
					<col style="width:300px;">
					<col style="width:100px;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">순번</th>
						<th scope="col">사은품명</th>
						<th scope="col">금액</th>
						<th scope="col">적용기간</th>
						<th scope="col">등록일자</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageContent.content}" var="list" varStatus="i">
						<tr>
							<td>${op:numbering(pageContent, i.index)}</td>
							<td class="left break-word">
								${list.name}
							</td>
							<td class="text-right">
								${op:numberFormat(list.price)}원
							</td>
							<td>
								<c:choose>
									<c:when test="${empty list.validStartDateText && empty list.validEndDateText}">
										상시
									</c:when>
									<c:otherwise>
										${list.validStartDateText} ~ ${list.validEndDateText}
									</c:otherwise>
								</c:choose>
							</td>
							<td>${list.createdDateTime}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty pageContent.content}">
						<tr class="no_content">
							<td colspan="13">변경사항 내역이 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>				 
		</div> <!-- // board_write -->
	<page:pagination-jpa />
</div>