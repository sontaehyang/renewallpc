<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<c:set var="backLink" value="javascript:history.back();"/>
<c:if test="${not empty itemUserCode }">
	<c:set var="backLink" value="/m/products/view/${itemUserCode }"/>
</c:if>


<div class="con">
	<div class="pop_title">
		<h3>제주 / 도서산간지역</h3>
		<a href="${backLink }" class="history_back">뒤로가기</a>
	</div> <!-- //pop_title -->
	
	<div class="pop_con">
		<div class="con_top search_adr">
			<form:form modelAttribute="islandDto" method="get">
				<fieldset>
					<legend>주소검색창</legend>
						<form:select path="where" title="${op:message('M00468')}" style="width:30%;"> <!-- 키워드선택 --> 
							<form:option value="ADDRESS" label="${op:message('M00118')}" />  <!-- 주소 --> 
							<form:option value="ZIPCODE" label="${op:message('M00115')}" />  <!-- 우편번호 --> 
							
						</form:select> 
						<form:input type="text" path="query" placeholder="주소를 입력해 주세요"/>
						<button type="submit" class="search" ><img src="/content/mobile/images/common/header_search_ico.png" alt="검색"/></button>
				</fieldset>
			</form:form>
		</div><!-- //con_top -->
		
		<div class="boardL">
			<table>
				<caption>우편번호/주소 정보</caption>
				<colgroup>
					<col style="width: 66%">
					<col style="width: 14%">
					<col style="width: 20%">
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
							<td class="adr"><a href="#">${island.zipcode}<br/>${island.address}</a></td>
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
							<td colspan="3">
								등록된 자료가 없습니다.
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div> <!-- //boardL -->
		<div class="paging">
			<%--<page:pagination-mobile />--%>
			<page:pagination-mobile-jpa />
		</div>
	</div>
</div>

<page:javascript>
<script type="text/javascript">

function closePopup() {
	self.close();
}

</script>
</page:javascript>