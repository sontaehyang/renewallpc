<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>



<h1 class="popup_title">${statisticsParam.userName} ${op:message('M01401')} ${op:message('M01296')}</h1> <!-- 매출 상세 --> <!-- 팝업 -->
<div class="popup_contents">
	 <h3><span>${statisticsParam.userName} ${op:message('M01401')}</span></h3> <!-- 매출 상세 -->
	
	<div class="board_list">
		
		<table class="board_list_table">
			<thead>
				
				<tr>
					<th>${op:message('M00013')}</th> <!-- 주문번호 -->
					<th class="border_left">${op:message('M01368')}</th> <!-- 주문 방법 -->
					<th class="border_left">${op:message('M00072')}</th> <!-- 주문 상태 -->
					<th class="border_left">${op:message('M00018')}</th> <!-- 상품명 -->
					<th class="border_left">${op:message('M00783')}</th> <!-- 상품 코드 -->
					<th class="border_left">${op:message('M00357')}</th> <!-- 수량 -->
					<th class="border_left">${op:message('M00627')}<br />(VAT ${op:message('M00958')})</th> <!-- 상품금액 --> <!-- 포함 -->
					<th class="border_left">${op:message('M01404')}<br />(VAT ${op:message('M00958')})</th> <!-- 주문 총 상품금액 --> <!-- 포함 -->
					<th class="border_left">${op:message('M00818')}</th> <!-- 상품 쿠폰 -->
					<th class="border_left">${op:message('M00820')}</th> <!-- 장바구니 쿠폰 -->
					<th class="border_left">${op:message('M00246')}</th> <!-- 포인트 -->
					<th class="border_left">${op:message('M00811')}</th> <!-- 추가 할인 -->
					<th class="border_left">${op:message('M00067')}</th> <!-- 배송비 -->
					<th class="border_left">${op:message('M00815')}</th> <!-- 추가 배송비 -->
					<th class="border_left">${op:message('M01384')}</th> <!-- 판매 금액 -->
				</tr>
			</thead>
			<tbody>
				<c:set var="AllTotalPrice">0</c:set>
				<c:set var="AllTotalPriceOk">0</c:set>
				<c:set var="AllTotalPriceNo">0</c:set>
				
				<c:forEach items="${userOrderList}" var="list">
				
				<c:set var="minors"></c:set>
				
					<c:set var="rowTotalPrice">${  (list.itemExcisePrice + list.sumDeliveryPrice + vendorAddDeliveryExtraCharge) - (list.couponDiscountAmount + list.cartCouponDiscountAmount + list.sumUsePoint + list.vendorAddDiscountAmount)}</c:set>
					<c:if test="${list.orderType == '1' }">
						<c:set var="AllTotalPriceOk">${AllTotalPriceOk + rowTotalPrice }</c:set>
					</c:if>
					<c:if test="${list.orderType == '2' }">
						<c:set var="minors">-</c:set>
						<c:set var="AllTotalPriceNo">${AllTotalPriceNo + rowTotalPrice }</c:set>
					</c:if>
					<tr>
						<td rowspan="${list.orderCount}">${list.orderCode }</td>
						<td rowspan="${list.orderCount}" class="border_left">${list.osType }</td>
						<td rowspan="${list.orderCount}" class="border_left">${list.orderStatus }</td>
						<c:forEach items="${list.itemsList}" var="list2" varStatus="i">
							<c:if test="${i.count == 1 }">
								<td class="border_left" style="text-align: left;">
									<img src="${list2.itemImage}" class="item_image" alt="상품이미지"  />
									${list2.itemName }
								</td>
								<td class="border_left">${list2.itemUserCode }</td>
								<td class="border_left">${op:numberFormat(list2.quantity) }</td>
								<td class="border_left">${minors} ${op:numberFormat(list2.itemsExcisePrice) }</td>
							</c:if>
						</c:forEach>
						<td rowspan="${list.orderCount}" class="border_left">${minors} ${op:numberFormat(list.itemExcisePrice)}</td>
						<td rowspan="${list.orderCount}" class="border_left">${minors} ${op:numberFormat(list.couponDiscountAmount) }</td>
						<td rowspan="${list.orderCount}" class="border_left">${minors}${op:numberFormat(list.cartCouponDiscountAmount) }</td>
						<td rowspan="${list.orderCount}" class="border_left">${minors} ${op:numberFormat(list.sumUsePoint) }</td>
						<td rowspan="${list.orderCount}" class="border_left">${minors} ${op:numberFormat(list.vendorAddDiscountAmount) }</td>
						<td rowspan="${list.orderCount}" class="border_left">${minors} ${op:numberFormat(list.sumDeliveryPrice) }</td>
						<td rowspan="${list.orderCount}" class="border_left">${minors} ${op:numberFormat(list.vendorAddDeliveryExtraCharge) }</td>
						<td rowspan="${list.orderCount}" class="border_left">${minors} ${op:numberFormat(rowTotalPrice)}</td>
					</tr>
					
					<c:forEach items="${list.itemsList}" var="list2" varStatus="i">
						<c:if test="${i.count > 1 }">
							<tr>
								<td class="border_left" style="text-align: left;">
									<img src="${list2.itemImage}" class="item_image" alt="상품이미지"  />
									${list2.itemName }
								</td>
								<td class="border_left">${list2.itemUserCode }</td>
								<td class="border_left">${op:numberFormat(list2.quantity) }</td>
								<td class="border_left">${minors} ${op:numberFormat(list2.itemsExcisePrice) }</td>
							</tr>
						</c:if>
					</c:forEach>
					
				</c:forEach>
				<c:if test="${!empty userOrderList }">
					<tr style="background-color : #d5d5d5">
						<td colspan="16" style="font-size: 17px; font-family: '나눔고딕 Bold', 'NanumGothicBold', 'ng_bold', '돋움', 'Dotum', sans-serif; text-align: right; ">
						 ${op:message('M00069')} : ${op:numberFormat(AllTotalPriceOk) } - ${op:message('M01405')} : ${op:numberFormat(AllTotalPriceNo) } =	${op:message('M01406')} : ${op:numberFormat(AllTotalPriceOk - AllTotalPriceNo)}
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		
		<c:if test="${empty userOrderList }">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>

	</div>
</div><!--//popup_contents E-->
	<a href="#" class="popup_close">창 닫기</a>
</div>


<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});
</script>