<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<c:if test="${mode=='1'}">
<link rel="stylesheet" href="/content/styles/opmanager.css" type="text/css" />
</c:if>




<!--  650 * 530 -->
<div class="popup_wrap">
	<h1 class="popup_title">제주 / 도서산간지역</h1>
	
	<div class="popup_contents">
		
		<form:form modelAttribute="islandDto" method="get">
			
			<div class="board_info"> 
				<form:select path="where" title="${op:message('M00468')}"  class="area"> <!-- 키워드선택 --> 
					<form:option value="ADDRESS" label="${op:message('M00118')}" />  <!-- 주소 --> 
					<form:option value="ZIPCODE" label="${op:message('M00115')}" />  <!-- 우편번호 --> 
				
				</form:select> 
				<div class="board-search"> 
					<form:input path="query" title="${op:message('M00740')}"/>
					<button type="submit" class="btn btn-m btn-submit" title="검색">검색</button> 
				</div> 
			</div><!-- // region_top E -->
			
		</form:form>

     		<div class="board-list mt20">
      		<table cellpadding="0" cellspacing="0">
      			<caption>제주 / 도서산간지역</caption>
      			<colgroup>
      				<col style="width:66%">
					<col style="width:14%">
					<col style="width:20%">
      			</colgroup>
      			<thead>      			
					<tr>
						<th scope="col">우편번호/주소</th>
						<th scope="col">제주</th>
						<th scope="col">도서산간</th>	
					</tr>
				</thead>
				<tbody>	
				<c:forEach items="${pageContent.content}" var="island">
					<tr>
						<td class="tleft">${island.zipcode}<br/>${island.address}
						</td>
						<td>
							<c:if test="${island.islandType.code eq 'JEJU'}">
								O
							</c:if>
						</td>
						<td>
							<c:if test="${island.islandType.code eq 'ISLAND'}">
								O
							</c:if>
						</td>
					</tr>
				
				</c:forEach>
				
				<c:if test="${empty pageContent.content}">
				<tr>
					<td colspan="4">
						등록된 자료가 없습니다.
					</td>
				<tr>
				</c:if>
				</tbody>
				
			</table>
		</div>
		<page:pagination-jpa />
		<a href="javascript:self.close()" class="popup_close">창 닫기</a>
	</div>				
</div>

<page:javascript>
<script type="text/javascript">

function closePopup() {
	self.close();
}

</script>
</page:javascript>