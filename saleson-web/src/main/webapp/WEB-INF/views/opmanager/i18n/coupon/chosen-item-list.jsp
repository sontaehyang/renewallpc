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
	
	<h1 class="popup_title">선택된 상품 목록</h1> 
	
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
 				<!-- 제외상품이 존재하는 경우 -->
	 			<p class="pop_tit">제외 상품 목록</p> 
	 			<div class="board_write mt20">
					<table class="board_list_table" summary="${op:message('M01187')}">
						<caption>${op:message('M01187')}</caption>
						<colgroup>
							<col style="width: 16%;" />
							<col style="width: auto;" />
							<col style="width: 22%;" />
							<col style="width: 19%;" />
						</colgroup>
						<thead>
							<tr>
								<th>${op:message('M00752')}</th> <!-- 이미지 -->
								<th>${op:message('M00018')}</th> <!-- 상품명 -->
								<th>${op:message('M00783')}</th> <!-- 상품코드 -->
								<th>${op:message('M00420')}</th> <!-- 상품가격 -->		
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${excludeItemList}" var="list" varStatus="i">
								<tr>
									<%-- <td>${list.email}</td> --%>
									<td>
										<div>
											<img src="${item.imageName}" class="item_image" alt="${op:message('M00659')}" /> <!-- 상품이미지 -->
										</div>
									</td>	
									<td>${list.itemName}</td>
									<td>${list.itemUserCode }</td> 
									<td>${list.salePrice }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${empty excludeItemList}">
						<div class="no_content"  style="padding:50px 0;height:122px;">
							<p>${op:message('M00591')}</p> <!-- 등록된 데이터가 없습니다. --> 
						</div>
					</c:if>
				</div>
				
 				<!-- 선택상품이 존재하는 경우 -->
 				<p class="pop_tit">선택 상품 목록</p> 
 				<div class="board_write mt20">
					<table class="board_list_table" summary="${op:message('M01187')}">
						<caption>${op:message('M01187')}</caption>
						<colgroup>
							<col style="width: 16%;" />
							<col style="width: auto;" />
							<col style="width: 22%;" />
							<col style="width: 19%;" />
						</colgroup>
						<thead>
							<tr>
								<th>${op:message('M00752')}</th> <!-- 이미지 -->
								<th>${op:message('M00018')}</th> <!-- 상품명 -->
								<th>${op:message('M00783')}</th> <!-- 상품코드 -->
								<th>${op:message('M00420')}</th> <!-- 상품가격 -->		
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${chosenItemList}" var="list" varStatus="i">
								<tr>
									<%-- <td>${list.email}</td> --%>
									<td>
										<div>
											<img src="${item.imageName}" class="item_image" alt="${op:message('M00659')}" /> <!-- 상품이미지 -->
										</div>
									</td>	
									<td>${list.itemName}</td>
									<td>${list.itemUserCode }</td> 
									<td>${list.salePrice }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${empty chosenItemList}">
						<div class="no_content" style="padding:50px 0;height:122px;">
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
