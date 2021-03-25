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
	<h3 class="popup_title"><span>입금대기 주문 정보</span></h3>
	<div class="popup_contents">
		<div class="board_write">
			
			<c:forEach items="${ list }" var="seller" varStatus="index">
				<h3 <c:if test="${index.index > 0}">class="mt30"</c:if>><span>${seller.sellerInfo.companyName}</span></h3>
				
				<table class="board_write_table" >
					<colgroup>
						<col style="width:150px;" />
						<col style="width:*;" />
						<col style="width:150px;" />
						<col style="width:*;" />
					</colgroup>
					<tbody>
						<tr>  
							<td class="label">판매자명</td>
							<td><div>${seller.sellerInfo.sellerName}</div></td>
							<td class="label">담당자명</td>
							<td><div>${seller.sellerInfo.userName}</div></td>
						</tr>
						<tr>  
							<td class="label">담당자 전화번호</td>
							<td><div>${seller.sellerInfo.telephoneNumber}</div></td>
							<td class="label">담당자 휴대폰번호</td>
							<td><div>${seller.sellerInfo.phoneNumber}</div></td>
						</tr>
					</tbody>
				</table>  
				<div class="board_list">	
			 		<table class="board_list_table" summary="주문내역 리스트">
			 			<caption>table list</caption>
			 			<colgroup> 
			 				<col style="width:auto;">
			 				<col style="width:109px;">
			 				<col style="width:120px;">
			 			</colgroup>
			 			<thead>
			 				<tr> 
			 					<th scope="col" class="none_left">상품명/옵션정보</th>
			 					<th scope="col" class="none_left">상품합계금액</th> 
			 					<th scope="col" class="none_left">상품상태</th>
			 				</tr>
			 			</thead>
			 			<tbody>
			 				
							<c:forEach items="${ seller.orderItems }" var="orderItem" varStatus="itemIndex">
				 				<tr>	
									<td class="left none_left">	 						 
				 						<div class="arrival3">
				 							<p class="name">${orderItem.itemName} [${orderItem.itemUserCode}]</p>
				 							${ shop:viewOptionText(orderItem.options) }
				 						</div>	 						  
				 					</td>
				 					<td><div class="sum"><span class="price">${ op:numberFormat(orderItem.saleAmount) }</span>원</div></td>
				 					<td>
				 						<div>
				 							${orderItem.orderItemStatusLabel}
				 						</div>
				 					</td>
				 				</tr>	
							</c:forEach>			 
			 			</tbody>
			 		</table><!--//esthe-table E-->	  		 
			 	</div>	<!--//board_write_list01 E-->
			</c:forEach>
		</div>
		
		<div class="popup_btns">
			<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 -->
		</div>
		
	</div>
</div>

<script type="text/javascript">
</script>
