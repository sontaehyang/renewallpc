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
	
	<h1 class="popup_title">발급대상[상품]</h1>
	
	<div class="popup_contents">
	
		<div class="count_title mt20">
			<h5>검색된 상품수 : <span id="userCount"style="display:inline-block;color:black;">${totalCount}</span></h5>
		</div>
	
		<div class="board_write mt20">
			<table class="board_list_table mt0" summary="${op:message('M00210')}">
				<caption>${op:message('M00210')}</caption>
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
					<c:forEach items="${list}" var="item" varStatus="i">
						<tr>
							<td>
								<div>
									<img src="${item.imageSrc}" class="item_image" alt="${op:message('M00659')}" /> <!-- 상품이미지 -->
								</div>
							</td>
							<td class="tleft">${item.itemName}</td>
							<td>${item.itemUserCode}</td>
							<td>${op:numberFormat(item.salePrice)}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:if test="${empty list}">
			<div class="no_content">
				${op:message('M00251')} 
			</div>
		</c:if>
		
		<page:pagination-manager />
		
	</div> <!-- // popup_contents -->
	
	<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
</div>