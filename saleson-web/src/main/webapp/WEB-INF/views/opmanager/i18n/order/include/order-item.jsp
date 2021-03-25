<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<c:if test="${not empty orderItem}">

	<table class="board_write_table">
		<caption>${op:message('M00059')}</caption> <!-- 주문정보 --> 
		<colgroup> 						
			<col style="width:15%;" />
			<col />
			<col style="width:15%;" />
			<col />
			<col style="width:15%;" />
			<col />
		</colgroup> 
		<tbody id="order_items">
			<tr>
				<th class="label">주문상태</th>
				<td class="tleft">
					<div> 
						${orderItem.orderItemStatusLabel}
					</div>
				</td>
				<th class="label">상품번호</th>
				<td class="tleft">
					<div> 
						${orderItem.itemCode}
					</div>
				</td>
				<th class="label">판매가</th>
				<td class="tleft">
					<div>
						${op:numberFormat(orderItem.price)}원
					</div>
				</td>
			</tr>
			<tr>
				<th class="label">옵션가</th>
				<td class="tleft">
					<div>
						${op:numberFormat(orderItem.optionPrice)}원
					</div>
				</td>
				<th class="label">주문수량</th>
				<td class="tleft">
					<div>
						${op:numberFormat(orderItem.quantity)}개
					</div>
				</td>
				<th class="label">주문금액</th>
				<td class="tleft">
					<div>
						${ op:numberFormat(orderItem.saleAmount) }원
					</div>
				</td>
			</tr>
			<tr>
				<th class="label">판매자 할인</th>
				<td>
					<div>
						<c:if test="${orderItem.sellerDiscountAmount > 0}">-</c:if>
						${op:numberFormat(orderItem.sellerDiscountAmount)}원
					</div>
				</td>
				<th class="label">판매자 할인 상세</th>
				<td colspan="3" class="tleft">
					<div>${orderItem.sellerDiscountDetail}</div>
				</td>
			</tr>
			<tr>
				<th class="label">운영사 할인</th>
				<td>
					<div>
						<c:if test="${orderItem.adminDiscountAmount > 0}">-</c:if>
						${op:numberFormat(orderItem.adminDiscountAmount)}원
					</div>
				</td>
				<th class="label">운영사 할인 상세</th>
				<td colspan="3" class="tleft">
					<div>${orderItem.adminDiscountDetail}</div>
				</td>
			</tr>
			<tr>   
				<th class="label">판매 수수료</th>
				<td>
					<div>${op:numberFormat(orderItem.commissionPrice)}원</div>
				</td>
				<th class="label">정산예정 금액</th>
				<td colspan="3">
					<div>
						${op:numberFormat(orderItem.supplyPrice)}원
						<c:if test="${orderItem.payShipping > 0}">
							(배송료 : +${op:numberFormat(orderItem.payShipping)}원)
						</c:if>
					</div>
				</td>
			</tr> 
			<tr>   
				<th class="label">${op:message('M00018')}</th> <!-- 상품명 -->
				<td colspan="5" class="tleft">
					<div>
						${orderItem.itemName} [${orderItem.itemUserCode}]
						${shop:viewOptionText(orderItem.options)}
					</div>
				</td>
			</tr>
			<tr>   
				<th class="label">${op:message('M01624')}</th> <!-- 사은품정보 -->
				<td colspan="5" class="tleft">
					<div>
						${orderItem.freeGiftName}
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</c:if>