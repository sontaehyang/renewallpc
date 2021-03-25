<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="popup_wrap">
	<h1 class="popup_title">적용가능상품</h1>
	<div class="popup_contents">
		<p class="txt_coupon"><span>“${coupon.couponName}”</span> 쿠폰으로 구매하실 수 있는 상품입니다.</p>
		<div class="board_wrap">  
	 		<table cellpadding="0" cellspacing="0" class="board-list">
	 			<caption>적용가능상품</caption>
	 			<colgroup>
	 				<col style="width:auto;">
	 				<col style="width:120px;">
	 			</colgroup>
	 			<thead>
	 				<tr>
		 				<th scope="col">상품명</th>
		 				<th scope="col">상품금액</th>
	 				</tr>
	 			</thead>
	 			<tbody>
	 			
	 				<c:forEach items="${ coupon.items }" var="item"> 
						<tr>
		 					<td class="tleft">	 		
		 						<div class="item_info">				 
			 						<div class="photo"><img src="${item.imageSrc}"></div>
			 						<div class=" order_option">
			 							<a href="javascript:goUrl('${ item.link }')">
				 							<p class="code">[${ item.itemUserCode }]</p>
				 							<p class="item_name">${ item.itemName }</p>
										</a>
			 						</div> 	
		 						</div> 					 
		 					</td>
		 					<td class="price_down">
		 						<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<span>${op:numberFormat(item.listPrice)}<span>원</span></span>
								</c:if>
		 						${op:numberFormat(item.exceptUserDiscountPresentPrice)}원
		 					</td>
		 				</tr>
	 				</c:forEach>
	 				
	 			</tbody>
	 		</table><!--//write E-->	 	
		</div>   
	</div><!--//popup_contents E-->
	<page:pagination-seo />
	<a href="javascript:self.close()" class="popup_close">창 닫기</a>
</div>


<page:javascript>
<script type="text/javascript">
	function goUrl(url) {
		opener.location.href = url;
	}
</script>
</page:javascript>