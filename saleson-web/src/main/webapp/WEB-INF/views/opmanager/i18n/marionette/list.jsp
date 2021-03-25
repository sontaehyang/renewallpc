<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<h3><span>안전인형극 신청 관리</span></h3>
	<div class="board_list">
		<div class="sort_area">
			<fieldset>
				<legend class="hidden">검색</legend>
				<div class="left">
					<a href="/opmanager/marionette/download-excel?where=${marionetteSearchParam.where}&query=${marionetteSearchParam.query}" class="btn orange">엑셀파일 다운로드</a>	 											 						
					 							 
				</div>
				<form:form modelAttribute="marionetteSearchParam" method="post">
					<div class="right">
						<form:select path="where" title="검색조건">
							<form:option value="SUBSCRIBER_NAME">이름</form:option>
							<form:option value="NURSERY_NAME">어린이집명</form:option>
						</form:select>
						
						<form:input path="query" class="input_txt required _filter" title="검색어" maxlength="20" />
						
						<button type="submit"><span class="icon_search">검색</span></button>
					</div>
				</form:form>
			</fieldset>
		</div>
		<table class="board_list_table01">
			<colgroup>
				<col style="width:10%;"/>
				<col style="width:15"/>
				<col style="width:*%;"/>
				<col style="width:15%;"/>
				<col style="width:15%;"/>
				<col style="width:15%;"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col" rowspan="2">번호</th>
					<th scope="col" rowspan="2">어린이집 명</th>
					<th scope="col" rowspan="2">신청자명</th>
					<th scope="col" rowspan="2">관람형태</th>
					<th colspan="2">연락처</th>
					 
				</tr>
				<tr>
					<th scope="col" class="border_none">휴대전화</th>
					<th scope="col" class="border_none">이메일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="rs" varStatus="i">
					<tr>
						<td>${pagination.itemNumber - i.count}</td>
						<td><a href="/opmanager/marionette/edit?marionetteId=${rs.marionetteId}">${rs.nurseryName}</a></td>
						<td>${rs.subscriberName }</td>
						<td>
							<c:if test="${rs.viewType == 1}">단독관람</c:if>
							<c:if test="${rs.viewType == 2}">공동관람</c:if>
						</td>
						<td>${rs.phone}</td>
						<td>${rs.email}</td>							 
					</tr>
				
				</c:forEach>
			</tbody>
		</table>
		<div class="board_guide">
			<p class="total">전체 : <em>${count}</em></p>
		</div>
		<page:pagination-manager />
		<p class="btns">

			<a href="/opmanager/marionette/write" class="btn orange">글쓰기</a>
		</p> 
	</div>