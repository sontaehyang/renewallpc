<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<style type="text/css">
	.board_list_table tbody tr {
		cursor: pointer;
	}
	.board_list_table tbody tr:hover {
		background: #f4f4f4;
	}
</style>

<!-- 본문 -->
<div class="popup_wrap">
	
	<h1 class="popup_title">판매자 검색</h1>
	
	<div class="popup_contents">				
		
		<form:form modelAttribute="sellerSearchParam" method="get">
			
			<div class="board_write">					

				<table class="board_write_table" summary="">
					<caption></caption>
					<colgroup>
						<col style="width: 100px" />
						<col style="" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
							<td>
								<div>
									<form:select path="where" title="상세검색 선택">
										<form:option value="SELLER_NAME">판매자명</form:option>
									</form:select>
									<form:input path="query" title="${op:message('M00022')}" /> <!-- 검색어 -->
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="btn_all">
				<div class="btn_left">
					<a href="/opmanager/seller/find" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</a> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div> <!-- // btn_all -->
		</form:form>
		
		<div class="board_write" style="clear:both">					
			<p class="pop_tit">판매자 목록</p>		 <!-- 상품목록 -->				
			<table class="board_list_table" summary="상품리스트">
				<caption>상품리스트</caption>
				<colgroup>
					<col style="width: 100px" />
					<col style="" />
					
				</colgroup>
				<thead>
					<tr>
						<th>업체코드</th> 
						<th>업체명</th>
						<th>대표자</th>
						<th>사업자등록번호</th>							
						<th>전화번호</th>					
					</tr> 
				</thead> 
				<tbody>
					<c:forEach items="${list}" var="seller">
						<tr onclick="selectSeller('${seller.sellerId}','${seller.sellerName}')" style="cursor: pointer">
							<td class="text-center">${seller.loginId}</td>
							<td class="text-left">${seller.sellerName}</td>
							<td class="text-center">${seller.userName}</td>
							<td class="text-center">${seller.businessNumber}</td>
							<td class="text-center">${seller.telephoneNumber}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${empty list}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. --> 
			</div>
			</c:if>
			
			<page:pagination-manager />
		</div>
	</div>
	<a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
	function selectSeller(sellerId, sellerName) {
		var target = "${targetId}";
		if (target!="") {
			$("#"+target+"Id",opener.document).val(sellerId);
			$("#"+target+"Name",opener.document).val(sellerName);
			// opener.sellerTrigger();
			self.close();
			return false;
		}
		
		try {
			opener.sellerSeller(sellerId);
		} catch(e) {}
		self.close();
	}
</script>