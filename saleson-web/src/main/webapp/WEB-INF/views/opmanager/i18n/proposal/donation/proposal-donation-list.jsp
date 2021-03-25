<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<table class="board_list_table">
		<caption class="hidden">관리자 기부활동관리 기부내역 수시기부</caption>
		<colgroup>
				<col style="width: 126px" />
				<col style="width: 140px" />
				<col style="width: 150px" />
				<col style="width: 150px" />
				<col style="width: auto" />
			</colgroup>
		<thead>
			<tr>
				<th>기부일</th>
				<th>기부자</th>
				<th>기부금액</th>
				<th>기부단체</th>
				<th>기부사연</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="proposal" varStatus="i">
			
			<tr>
				<td> ${fn:replace(proposal.creationDate,".","-")} </td>
				<td>
					<c:choose>
						<c:when test="${ proposal.userType == 2 }">
							<img src="/content/opmanager/images/icon/icon_star.png" alt="직원" class="icon_star" />
							${ proposal.userName } 
						</c:when>
						<c:when test="${ proposal.userType == 3 }">
							${ proposal.userName } 
						</c:when>
						<c:otherwise>
							${proposal.loginId }
						</c:otherwise>
					</c:choose>
				</td>
				<td><fmt:formatNumber value="${ proposal.donationAmount }" pattern="#,###" /></td>
				<td>${proposal.ngoUserName}</td>
				<td>${proposal.title}</td>
			</tr>
			
			</c:forEach>
		</tbody>
	</table>
	
	<c:if test="${ empty list }">
	<div class="no_content">
		<p>등록된 내용이 없습니다.</p>
	</div>
	</c:if>
	<div class="board_guide">
		<p class="total">전체 : <em>${op:numberFormat(pagination.totalItems)}</em></p>
	</div>
	
	<page:pagination-manager />
	<p class="btns">
		<a href="javascript:fn_donationExcelDownload();" class="btn orange">엑셀파일 다운로드</a>
	</p>