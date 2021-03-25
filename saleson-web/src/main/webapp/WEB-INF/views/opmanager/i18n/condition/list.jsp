<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
	<h3><span>검색조건 관리</span></h3>
	<div class="item_list">
		<div class="board_write">
			<table class="board_list_table" summary="처리내역 리스트">
				<caption>처리내역 리스트</caption>
				<colgroup>
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 100px;">
					<col style="width:;">
					<col style="width: 80px;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">그룹</th> 
						<th scope="col">카테고리</th>
						<th scope="col">카테고리 코드</th>
						<th scope="col">관리</th>
						
						<th scope="col">조건명</th>
						<th scope="col">사용여부</th>
					</tr>
				</thead>
				<tbody class="sortable">
					<c:forEach var="group" items="${list}">
						<c:forEach var="category" items="${group.categoriesGroup.categoriesForCondition}">
							<c:forEach var="condition" items="${category.conditions}" varStatus="i">
								<tr>
									<c:if test="${i.count == 1}">
										<td rowspan="${fn:length(category.conditions)}">
											${group.categoriesGroup.name}
										</td>
										<td rowspan="${fn:length(category.conditions)}"> 
											${category.categoryName}
										</td>
										<td rowspan="${fn:length(category.conditions)}"> 
											${category.categoryCode}
										</td>
										<td rowspan="${fn:length(category.conditions)}">
											<button type="button" class="btn btn-gray btn-sm" onclick='location.href="/opmanager/condition/create/${category.categoryCode}";'>추가</button>
										</td>
									</c:if>
									<c:choose>
										<c:when test='${category.conditionsEmpty}'>
											<td colspan="2">등록된 조건이 없습니다.</td>
										</c:when>
										<c:otherwise>
											<td onclick='location.href="/opmanager/condition/detail/list/${condition.conditionId}";'>
												${condition.conditionTitle}
											</td>
											<td>
												${condition.useYn == "Y" ? "사용" : "미사용"}
											</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</c:forEach>		
					</c:forEach>
				</tbody>
			</table>				 
		</div>
	</div>