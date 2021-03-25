<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<!-- 본문 -->
<div class="popup_wrap">
	
	<h1 class="popup_title">선택된 회원 목록</h1> <!-- 쿠폰 발급하기 -->
	
	<div class="popup_contents">
		<%-- 	<form:form modelAttribute="couponParam" method="post">
			
			<div class="count_title mt20">
				<p class="pop_tit">${op:message('M00210')}</p> <!-- 회원 리스트 --> 
				<h5>전체 회원수 : ${userCount} /&nbsp;</h5>
				
				<span style="float:right;">
					<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
						onchange="$('form#searchParam').submit();"> <!-- 화면 출력수 -->
						<form:option value="10" label="10${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="30" label="30${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
					</form:select>
				</span>
			</div>
 			</form:form> --%>
 			
 		
			<div class="board_write mt20">
				<p style="float:right;">선택된 회원 수 : ${userCount }명</p>
				<table class="board_list_table mt0" summary="${op:message('M00210')}">
					<caption>${op:message('M00210')}</caption>
					<colgroup>
						<col style="width: 15%;">
						<col style="width: 13%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 15%;">
					</colgroup>
					<thead>
						<tr>
							<th>${op:message('M00081')}</th> <!-- 아이디 -->
							<th>${op:message('M01192')}</th> <!-- 고객명 -->
							<th>${op:message('M00212')}</th> <!-- 회원등급 -->
							<th>${op:message('M01193')}</th> <!-- 회원그룹 -->
							<th>${op:message('M00125')}</th> <!-- 이메일 -->			
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${chosenUserList}" var="list" varStatus="i">
							<tr>
								<%-- <td>${list.email}</td> --%>
								<td class="loginId">${list.loginId}</td>
								<td>${list.userName}</td>
								<td>${list.levelName }</td> <!-- 회원등급 -->
								<td>${list.groupName }</td>
								<td>${list.email}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${empty chosenUserList}">
					<div class="no_content"  style="padding:50px 0;height:122px;">
						<p>${op:message('M00591')}</p> <!-- 등록된 데이터가 없습니다. --> 
					</div>
				</c:if>
				
			</div>
	
			<div class="btn_all">
				<div class="btn_center mb0">
					<a href="javascript:self.close();" class="btn btn-default">확인</a>
				</div>
			</div>
		<%-- 
		<page:pagination-manager />
		 --%>
	</div> <!-- // popup_contents -->
	
	<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
</div>

<script type="text/javascript">
	
		
</script>
