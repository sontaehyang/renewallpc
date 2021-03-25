<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<li>
    <c:set var="totalItemSaleAmount" value="${buyItem.itemPrice.itemSaleAmount}" />
    <c:set var="totalDiscountAmount" value="${buyItem.itemPrice.discountAmount}" />
    <c:set var="totalSaleAmount" value="${buyItem.itemPrice.saleAmount}" />

    <c:forEach items="${buyItem.additionItemList}" var="addition">
        <c:if test="${buyItem.itemId == addition.parentItemId && buyItem.options == addition.parentItemOptions}">
            <c:set var="totalItemSaleAmount">${totalItemSaleAmount + addition.itemPrice.itemSaleAmount}</c:set>
            <c:set var="totalDiscountAmount">${totalDiscountAmount + addition.itemPrice.discountAmount}</c:set>
            <c:set var="totalSaleAmount">${totalSaleAmount + addition.itemPrice.saleAmount}</c:set>
        </c:if>
    </c:forEach>

    <c:if test="${viewTarget == 'cart'}">
        <span class="check">
            <input id="checkbox${i}" type="checkbox" name="id" class="op-available-item" value="${ buyItem.cartId}" data-item-id="${buyItem.itemId}" data-item-options="${buyItem.options}" data-parent-item-id="${buyItem.parentItemId}" data-addition-item-flag="${buyItem.additionItemFlag}">
            <label for="checkbox${i}">선택</label>
        </span>
    </c:if>

    <div class="inner">
        <a href="/m/products/view/${ buyItem.item.itemUserCode }" class="con_top cf">
            <div class="cart_img">
                <%-- <img src="${buyItem.item.imageSrc}" alt="${ buyItem.item.itemName }"> --%>
                <img src="${shop:loadImage(buyItem.item.itemUserCode, buyItem.item.itemImage, 'XS')}" alt="${ buyItem.item.itemName }">
            </div>
            <div class="cart_name">
                <p class="tit">
                    ${buyItem.item.itemName}
                </p>
                <p class="detail">${shop:viewOptionText(buyItem.options)}</p>
                <p class="detail">${shop:viewAdditionItemList(buyItem.additionItemList)}</p>
                <c:if test="${not empty buyItem.freeGiftItemText}">
                    <p class="detail">${buyItem.freeGiftItemText}</p>
                </c:if>
                <p class="sale_price"><span>${op:numberFormat(totalItemSaleAmount)}원</span></p>
            </div>
        </a>
        <div class="con_bot">
            <c:if test="${viewTarget == 'cart'}">
                <div class="cacul">
                    <a href="javascript:;" class="minus">감소</a>
                    <input type="hidden" name="stockFlag" id="stockFlag-${buyItem.cartId}" value="${ buyItem.item.stockFlag }" />
                    <input type="hidden" name="stockQuantity" id="stockQuantity-${buyItem.cartId}" value="${ buyItem.item.stockQuantity }" />
                    <input type="hidden" name="orderMinQuantity" id="op-order-min-quantity-${buyItem.cartId}" value="${ buyItem.item.orderMinQuantity }" />
                    <input type="hidden" name="orderMaxQuantity" id="op-order-max-quantity-${buyItem.cartId}" value="${ buyItem.item.orderMaxQuantity }" />
                    <input type="hidden" name="shippingGroupCode" id="shippingGroupCode-${buyItem.cartId}" value="${ shipping.shippingGroupCode }" />
                    <input type="hidden" id="op-org-quantity-${buyItem.cartId}" value="${ buyItem.itemPrice.quantity }" />
                    <input type="text" name="quantity" id="op-quantity-${buyItem.cartId}" maxlength="3" class="quantity _number num" value="${ buyItem.itemPrice.quantity }">
                    <a href="javascript:;" class="plus">증가</a>
                    <button id="op-save-quantity-${ buyItem.cartId }" style="display:none;" onclick="editQuantity('${buyItem.cartId}')" type="button" class="btn_st3">변경</button>
                </div>
            </c:if>
            <div class="price">
                <c:choose>
                    <c:when test="${viewTarget == 'cart'}">

                        <c:if test="${totalDiscountAmount > 0}">
                            <p style="padding-bottom: 3px;">-${op:numberFormat(totalDiscountAmount)}원</p>
                        </c:if>
                        <p class="sale_price"><span>${op:numberFormat(totalSaleAmount)}</span>원</p>
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
                            <c:if test="${shipping.shippingPaymentType == '1'}">
								<span class="total_text op-shipping-coupon-${shipping.shippingSequence}" style="${ shipping.realShipping == 0 ? 'display:none' : ''}">
									<label>배송비 쿠폰 <input type="checkbox" style="width:22px;height:22px" class="op-input-shipping-coupon-used" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].useFlag" value="Y" /> 사용</label>
								 	<input type="hidden" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].shippingGroupCode" value="${shipping.shippingGroupCode}" />
								</span>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </c:if>
        </c:if>

        <span class="total">
			<strong class="op-shipping-text-${shipping.shippingSequence}">
				<c:choose>
                    <c:when test="${shipping.realShipping == 0}">${op:message('M00448')} <!-- 무료 --></c:when>
                    <c:otherwise>
                        ${op:numberFormat(shipping.realShipping)}원
                    </c:otherwise>
                </c:choose>
			</strong>
		</span>
    </div><!--//order_all_total E-->
</c:if>