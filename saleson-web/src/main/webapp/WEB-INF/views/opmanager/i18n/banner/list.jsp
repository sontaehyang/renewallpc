<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<h3><span>배너관리</span></h3>
<div class="board_list">
	<div class="text2">
		&nbsp;
	</div>
	<table class="board_list_table">
		<colgroup>
			<col style="width:5%;"/>												
			<col style="width:*"/>
			<col style="width:20%;"/>
			<col style="width:15%;"/>
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>상태</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="rs" varStatus="i">
				<tr>
					<td>${count - (i.count-1)}</td>
					<td><a href="/opmanager/banner/edit?bannerId=${rs.bannerId}">${rs.subject}</a></td>	
					<td>
						<c:if test="${rs.statusCode == 1}">
							<span class="txt_org">노출</span>
						</c:if>
						<c:if test="${rs.statusCode == 2}">
							비노출
						</c:if>
					</td>													
					<td>${fn:substring(rs.creationDate, 0, 4)}.${fn:substring(rs.creationDate, 4, 6)}.${fn:substring(rs.creationDate, 6, 8)}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="board_guide">
		<p class="total">전체 : <em>${count}</em></p>
	</div>
	<p class="btns">
		<c:if test="${count < 3}">
			<a href="/opmanager/banner/write" class="btn orange">글쓰기</a>
		</c:if>
	</p> 
</div>