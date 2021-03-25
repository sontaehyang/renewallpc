<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>



<h1 class="popup_title">${op:date(statisticsParam.searchDate)} ${op:message('M01401')} ${op:message('M01296')}</h1> <!-- 매출 상세 --> <!-- 팝업 -->
<div class="popup_contents">
	 <h3><span>${op:date(statisticsParam.searchDate)} ${op:message('M01401')}</span></h3> <!-- 매출 상세 -->
	
	<div class="board_list">
		
		<table class="board_list_table">
			<thead>
				
				<tr>
					<th>${op:message('M00018')}</th> <!-- 상품명 -->
					<th class="border_left">${op:message('M00783')}</th> <!-- 상품 코드 -->
					<th class="border_left">${op:message('M00357')}</th> <!-- 수량 -->
					<th class="border_left">${op:message('M00627')}<br />(VAT ${op:message('M00958')})</th> <!-- 상품금액 --> <!-- 포함 -->
				</tr>
			</thead>
			<tbody>
				<c:set var="totalPrice">0</c:set>
				<c:forEach items="${userOrderItemList}" var="list" varStatus="i">
					<c:set var="totalPrice">${totalPrice + list.itemsExcisePrice }</c:set>
					<tr>
						<td style="text-align: left;">
							<img src="${list.itemImage}" class="item_image" alt="상품이미지"  />
							${list.itemName }
						</td>
						<td class="border_left">${list.itemUserCode }</td>
						<td class="border_left">${op:numberFormat(list.quantity) }</td>
						<td class="border_left">${minors} ${op:numberFormat(list.itemsExcisePrice) }</td>
					</tr>
				</c:forEach>
				<tr style="background-color : #d5d5d5">
					<td colspan="16" style="font-size: 17px; font-family: '나눔고딕 Bold', 'NanumGothicBold', 'ng_bold', '돋움', 'Dotum', sans-serif; text-align: right; ">
					 ${op:message('M00445')}(VAT ${op:message('M00958')}) : ${op:numberFormat(totalPrice) } - ${op:message('M00897')} : ${op:numberFormat(order.sumUsePoint + order.orderVendor.vendorAddDiscountAmount + cartCouponDiscountAmount)} + ${op:message('M01402')} ${op:numberFormat(order.sumDeliveryPrice+order.orderVendor.vendorAddDeliveryExtraCharge)} <br />
					 = ${op:message('M01403')} : ${op:numberFormat( (totalPrice+sumDeliveryPrice+order.orderVendor.vendorAddDeliveryExtraCharge+ order.sumDeliveryPrice+order.orderVendor.vendorAddDeliveryExtraCharge) - (order.sumUsePoint + order.orderVendor.vendorAddDiscountAmount + cartCouponDiscountAmount) )}  
					 
					</td>
				</tr>
			</tbody>
		</table>

	</div>
</div><!--//popup_contents E-->
	<a href="#" class="popup_close">창 닫기</a>
</div>


<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});
</script>