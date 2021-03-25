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
	<h2><span>상품변경사항</span></h2>
	<form method="post" id="listForm">
		<input type="hidden" name="userId" value="${userId}" />
		<div class="board_write">
			<table class="board_list_table">
				<colgroup>
					<col style="width:50px" />
					<col style="" />
					<col style="width:70px" />
					<col style="width:70px" />
					<col style="width:70px" />
					<col style="width:90px" />
					<col style="width:90px" />
					<col style="width:150px" />
					<col style="width:90px" />
					<col style="width:90px" />
					<col style="width:100px" />
					<col style="width:100px" />
					<col style="width:90px" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">순번</th>
						<th scope="col">상품명(상품코드)</th>
						<th scope="col">판매자</th>
						<th scope="col">공개유무</th>
						<th scope="col">품절여부</th>
						<th scope="col">정가</th>
						<th scope="col">판매가격</th>
						<th scope="col">수수료 설정</th>
						<th scope="col">수수료율</th>
	 					<th scope="col">등록/수정위치</th>
	 					<th scope="col">등록/수정자</th>
	 					<th scope="col">처리 구분</th>
	 					<th scope="col">등록/수정일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="item" varStatus="i">
						<tr>
							<td>
								${pagination.itemNumber - i.count}
							</td>
							<td class="text-left">
								${item.itemName}
								<br/>
								(${item.itemUserCode})
							</td>
							<td>
								${item.sellerName}
							</td>
							<td>
								<c:choose>
									<c:when test="${item.displayFlag == 'Y'}">공개</c:when>
									<c:when test="${item.displayFlag == 'N'}">비공개</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${item.soldOut == '0'}">판매가능</c:when>
									<c:when test="${item.soldOut == '1'}">재고품절</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>
								${op:numberFormat(op:negativeNumberToEmpty(item.itemPrice))}원
							</td>
							<td>
								${op:numberFormat(op:negativeNumberToEmpty(item.salePrice))}원
							</td>
							<td>
								<c:choose>
									<c:when test="${item.commissionType == '1'}">입점업체 수수료 설정</c:when>
									<c:when test="${item.commissionType == '2'}">상품별 수수료 설정</c:when>
									<c:when test="${item.commissionType == '3'}">공급가 설정</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>
								${op:numberFormat(item.commissionRate)}%
							</td>
							<td>
								<c:choose>
									<c:when test="${item.processPage == 'manager'}">관리자</c:when>
									<c:when test="${item.processPage == 'seller'}">판매자</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${item.processPage == 'manager'}">${item.managerLoginId} (${item.userName})</c:when>
									<c:when test="${item.processPage == 'seller'}">${item.sellerLoginId} (${item.sellerName})</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<td>
							
								<c:choose>
									<c:when test="${item.actionType == 'insert'}">등록</c:when>
									<c:when test="${item.actionType == 'update'}">수정</c:when>
									<c:when test="${item.actionType == 'approval'}">승인</c:when>
									<c:when test="${item.actionType == 'insert-by-excel'}">엑셀등록</c:when>
									<c:when test="${item.actionType == 'apply-reg-by-excel'}">엑셀등록신청</c:when>
									<c:when test="${item.actionType == 'update-by-excel'}">엑셀수정</c:when>
									<c:when test="${item.actionType == 'apply-mod-by-excel'}">엑셀수정신청</c:when>
									<c:otherwise>${item.actionType}</c:otherwise>
								</c:choose>
							</td>
							<td>${op:datetime(item.createdDate)}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty list}">
						<tr class="no_content">
							<td colspan="13">변경사항 내역이 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>				 
		</div> <!-- // board_write -->
	</form>
	<page:pagination-manager />
</div>