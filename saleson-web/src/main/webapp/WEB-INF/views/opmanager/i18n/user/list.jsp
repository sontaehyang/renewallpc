<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<h3><span>관리자계정 관리</span></h3>
	<div class="board_list">
		<form:form modelAttribute="searchParam" action="" method="post">
			<div class="sort_area">
				<fieldset>
					<legend class="hidden">검색</legend>
					<div class="right">
						<form:select path="where" title="검색조건">
							<form:option value="USERNAME">이름</form:option>
							<form:option value="LOGIN_ID">아이디</form:option>
						</form:select>
						<form:input path="query" class="input_txt required _filter" title="검색어" maxlength="20" />
						<button type="submit"><span class="icon_search">검색</span></button>
					</div>
				</fieldset>
			</div>
		</form:form>
		
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
					<th scope="col" rowspan="2">이름</th>
					<th scope="col" rowspan="2">아이디</th>
					<th scope="col" rowspan="2">구분</th>
					<th colspan="2">연락처</th>
					 
				</tr>
				<tr>
					<th scope="col" class="border_none">휴대전화</th>
					<th scope="col" class="border_none">이메일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="user" varStatus="i">
					<tr>
						<td>${pagination.itemNumber - i.count}</td>
						<td><a href="/opmanager/user/edit?userId=${user.userId}">${user.userName}</a></td>
						<td><a href="/opmanager/user/edit?userId=${user.userId}">${user.loginId}</a></td>
						<td>${user.userRoles[0].authority}</td>
						<td>${user.userDetail.phoneNumber}</td>
						<td>${user.email}</td>							 
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		
		<div class="board_guide">
			<p class="total">전체 : <em>${count}</em></p>
		</div>
		<page:pagination-manager />
		<p class="btns">

			<span class="right">						
				<a href="/opmanager/user/write" class="btn orange">등록</a>							
			</span>
			
		</p> 
	</div>
