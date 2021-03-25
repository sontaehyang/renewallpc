<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<!--  650 * 530 -->
<div class="popup_wrap">
	<h1 class="popup_title">사은품 목록</h1>
	
	<div class="popup_contents">
		
		<div class="board-list mt20">
      		<table cellpadding="0" cellspacing="0">
      			<caption>사은품 목록</caption>
      			<colgroup>
			        <col style="width:180px;">
      				<col style="width:auto;">
      			</colgroup>
				<tbody>
					<c:forEach items="${list}" var="giftItem">
						<tr>
							<td>
								<img src="${shop:loadImageBySrc(giftItem.imageSrc, "XS")}"
								     class="item_image" alt="" style="width: 150px; height: 150px; vertical-align: middle;"/>
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
		</div>

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