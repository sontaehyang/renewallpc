<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

	<li>
		<c:if test="${viewTarget == 'cart'}">
			<span class="check">
			 	<input id="checkbox${i}" type="checkbox" name="id" class="op-available-item" value="${ buyItem.cartId }">
				<label for="checkbox${i}">선택</label>
			</span>
		</c:if>
	
		<div class="inner">
			<div class="con_top cf">
				<div class="cart_img">
					<a href="/m/products/view/${ buyItem.item.itemUserCode }"><img src="${buyItem.item.imageSrc}" alt="${ buyItem.item.itemName }"></a>
				</div>
				<div class="cart_name">
					<p class="tit"><a href="/m/products/view/${ buyItem.item.itemUserCode }">${ buyItem.item.itemName}</a></p>
					<p class="detail">${shop:viewOptionText(buyItem.options)}</p>
				</div>
			</div>
			<div class="con_bot">
				<c:if test="${viewTarget == 'cart'}">
					<div class="cacul">
						<a href="javascript:changeQuantity('down', '${buyItem.cartId}')" class="minus">감소</a>
							<input type="hidden" name="stockFlag" id="stockFlag-${buyItem.cartId}" value="${ buyItem.item.stockFlag }" />
							<input type="hidden" name="stockQuantity" id="stockQuantity-${buyItem.cartId}" value="${ buyItem.item.stockQuantity }" />
							<input type="hidden" name="orderMinQuantity" id="op-order-min-quantity-${buyItem.cartId}" value="${ buyItem.item.orderMinQuantity }" />
							<input type="hidden" name="orderMaxQuantity" id="op-order-max-quantity-${buyItem.cartId}" value="${ buyItem.item.orderMaxQuantity }" />
							<input type="hidden" id="op-org-quantity-${buyItem.cartId}" value="${ buyItem.itemPrice.quantity }" />
						<input type="text" name="quantity" id="op-quantity-${buyItem.cartId}" maxlength="2" class="quantity _number num" value="${ buyItem.itemPrice.quantity }${op:message('M01004')}">
						<a href="javascript:changeQuantity('up', '${buyItem.cartId}')" class="plus">증가</a>
						<button id="op-save-quantity-${ buyItem.cartId }" style="display:none;" onclick="editQuantity('${buyItem.cartId}')" type="button" class="btn_st3">변경</button>
					</div> 
				</c:if>
				<div class="price">
					<c:choose>
						<c:when test="${viewTarget == 'cart'}">
							<p class="sale_price"><span>${ op:numberFormat(buyItem.itemPrice.sumPrice) }</span>원</p>
						</c:when>
						<c:otherwise>
							${shop:viewOptionText(buyItem.options)}
							<p class="all_info">판매가/수량 : ${ op:numberFormat(buyItem.itemPrice.sumPrice) }원/${ buyItem.itemPrice.quantity }개 </p> 
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
 	</li>
	<%--div class="order_total">
		<span>상품합계</span>
		<span class="total">${ op:numberFormat(buyItem.itemPrice.saleAmount) }원</span>  
	</div><!--//order_total E--> --%>



<c:if test="${lastItem == true}">
	<div class="order_all_total"> 
		<span>배송비 : </span>
		
		<c:if test="${viewTarget == 'order'}">
			<c:if test="${buy.shippingCoupon > 0}">
				<tr>
					<td colspan="2">
						<c:if test="${shipping.shippingType != '5' && shipping.shippingType != '1'}">
							<span class="total_text op-shipping-coupon-${shipping.shippingSequence}" style="${ shipping.realShipping == 0 ? 'display:none' : ''}">
								<label>배송비 쿠폰 <input type="checkbox" style="width:22px;height:22px" class="op-input-shipping-coupon-used" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].useFlag" value="Y" /> 사용</label>
							 	<input type="hidden" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].shippingGroupCode" value="${shipping.shippingGroupCode}" />
							</span> 
						</c:if>
					</td>
				</tr>
			</c:if>
		</c:if>
		
		<span class="total">
			<strong class="op-shipping-text-${shipping.shippingSequence}">
				<c:choose>
					<c:when test="${shipping.realShipping == 0}">${op:message('M00448')} <!-- 무료 --></c:when>
					<c:otherwise>${op:numberFormat(shipping.realShipping)}원</c:otherwise>
				</c:choose>
			</strong>
		</span>  
	</div><!--//order_all_total E-->
</c:if>