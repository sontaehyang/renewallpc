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
		<h3>사은품 목록</h3>
		<a href="${backLink }" class="history_back">뒤로가기</a>
	</div> <!-- //pop_title -->
	
	<div class="pop_con">
		<div class="boardL">
			<table cellpadding="0" cellspacing="0">
				<caption>사은품 목록</caption>
					<colgroup>
						<col style="width:20%;">
						<col style="width:80%;">
					</colgroup>
				<tbody>
					<c:forEach items="${list}" var="giftItem">
						<tr>
							<td>
								<img src="${shop:loadImageBySrc(giftItem.imageSrc, "XS")}"
								     class="item_image" alt="" style="width: 100%; vertical-align: middle;"/>
							</td>
							<td>
									${giftItem.name}
								<br/>
								[${giftItem.code}]
							</td>
						</tr>
					</c:forEach>

					<c:if test="${empty list}">
						<tr>
							<td colspan="2">
								등록된 사은품이 없습니다.
							</td>
						<tr>
					</c:if>
				</tbody>

			</table>
		</div> <!-- //boardL -->

	</div>
</div>

<page:javascript>
<script type="text/javascript">

function closePopup() {
	self.close();
}

</script>
</page:javascript>