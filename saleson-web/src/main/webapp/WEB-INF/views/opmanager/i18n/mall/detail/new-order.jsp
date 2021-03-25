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
	<h3 class="popup_title"><span>${orderShipping.orderCode} - 주문 정보</span></h3>
	<div class="popup_contents">
		<div class="board_write">
		
			<h3><span>주문 기본 정보</span></h3>
			<table class="board_write_table">
				<caption>주문 기본 정보</caption>
				<colgroup> 						
					<col style="width:20%;" />
					<col />
					<col style="width:20%;" />
					<col />
				</colgroup> 
				<tbody> 
					<tr>   
						<th class="label">주문번호</th>
						<td class="tleft">
							<div>
								${order.orderCode}
							</div>
						</td>
						<th class="label">주문일시</th>
						<td class="tleft">
							<div>
								${op:datetime(order.createdDate)}
							</div>
						</td>
					</tr>
					<tr>   
						<th class="label">구매자(ID)</th>
						<td class="tleft">
							<div>
								${order.buyerName}
								<c:if test="${not empty order.memberId}">
									(${order.memberId})
								</c:if>
							</div>
						</td>
						<th class="label">구매자 연락처</th>
						<td class="tleft">
							<div>
								전화 : ${order.buyerTelephoneNumber}<br/>휴대폰 : ${order.buyerPhoneNumber}
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
			<h3 class="mt10"><span>주문 상품 정보</span></h3>
			<table class="board_write_table">
				<caption>배송 정보</caption>
				<colgroup> 						
					<col style="width:11%;" />
					<col />
					<col style="width:11%;" />
					<col />
					<col style="width:11%;" />
					<col />
				</colgroup> 
				<tbody> 
					<tr>
						<th class="label">주문순번</th>
						<td class="tleft">
							<div>
								${order.orderIndex}(주문상태 : ${order.orderItemStatusLabel})
							</div>
						</td>
						<th class="label">상품번호</th>
						<td class="tleft">
							<div>
								${order.productCode} 
								<c:if test="${not empty order.itemUserCode}">
									<p>(판매자 코드 : ${order.itemUserCode})</p>
								</c:if>
							</div>
						</td>
						<th class="label">판매가</th>
						<td class="tleft">
							<div>
								${op:numberFormat(order.sellPrice)}원
							</div>
						</td>
					</tr>
					<tr>
						<th class="label">옵션가</th>
						<td class="tleft">
							<div>
								${op:numberFormat(order.optionAmount)}원
							</div>
						</td>
						<th class="label">주문수량</th>
						<td class="tleft">
							<div>
								<strong>${op:numberFormat(order.quantity - order.cancelQuantity)}개</strong>
							</div>
						</td>
						<th class="label">주문금액</th>
						<td class="tleft">
							<div>
								<strong>${op:numberFormat(order.saleAmount)}원</strong>
							</div>
						</td>
					</tr>
					<tr>
						<th class="label">할인금액</th>
						<td class="tleft" colspan="5">
							<div>
								<strong>${op:numberFormat(order.sellerDiscountAmount)}원</strong>
							</div>
						</td>
					</tr>
					<tr>
						<th class="label">상품명</th>
						<td class="tleft" colspan="5">
							<div>
								${order.productName}
								<c:if test="${not empty order.optionName}">
									<p>[옵션] ${order.optionName}</p>
								</c:if>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
			<h3 class="mt10"><span>배송 정보</span></h3>
			<table class="board_write_table">
				<caption>배송 정보</caption>
				<colgroup> 						
					<col style="width:20%;" />
					<col />
					<col style="width:20%;" />
					<col />
				</colgroup> 
				<tbody> 
					<tr>   
						<th class="label">배송비구분</th>
						<td class="tleft">
							<div>
								<c:choose>
									<c:when test="${order.mallType == '11st'}">
										<c:choose>
											<c:when test="${order.payShippingType == '01'}">선불</c:when>
											<c:when test="${order.payShippingType == '02'}">착불</c:when>
											<c:when test="${order.payShippingType == '03'}">무료</c:when>
										</c:choose> 
									</c:when>
									<c:when test="${order.mallType == 'auction'}">
										<c:choose>
											<c:when test="${order.payShippingType == 'Free'}">무료</c:when>
											<c:when test="${order.payShippingType == 'FreeConditionSatisfied'}">무료배송 조건충족</c:when>
											<c:when test="${order.payShippingType == 'PayOnArrival'}">착불</c:when>
											<c:when test="${order.payShippingType == 'Prepaid'}">선결제완료</c:when>
										</c:choose>
									</c:when>
									
								</c:choose>
								
								&nbsp;(${order.payShipping}원)
							</div>
						</td>
						<th class="label">배송번호</th>
						<td class="tleft">
							<div>
								${order.shippingCode}
							</div>
						</td>
					</tr>
					<tr>   
						<th class="label">받으시는분</th>
						<td class="tleft">
							<div>
								${order.receiverName}
							</div> 
						</td>
						<th class="label">받으시는분 연락처</th>
						<td class="tleft">
							<div>
								<c:if test="${not empty order.receiverTelephoneNumber}">
									${order.receiverTelephoneNumber} / 
								</c:if>
								${order.receiverPhoneNumber} 
							</div>
						</td>
					</tr>
					<tr>
						<th class="label">배송지 주소</th>
						<td class="tleft" colspan="3">
							<div>
								(${order.receiverZipcode}) ${order.receiverAddress} ${order.receiverAddressDetail}
							</div>
						</td>
					</tr>
					<tr>
						<th class="label">배송시 요청사항</th>
						<td class="tleft" colspan="3">
							<div>
								${order.content}
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		
			<div class="popup_btns">
				<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 -->
			</div>
		</div>
	</div>
</div>