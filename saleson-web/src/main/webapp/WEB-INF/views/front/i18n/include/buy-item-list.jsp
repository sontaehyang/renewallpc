<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<tr>

    <c:set var="totalItemSaleAmount" value="${buyItem.itemPrice.itemSaleAmount}" />
    <c:set var="totalDiscountAmount" value="${buyItem.itemPrice.discountAmount}" />
    <c:set var="totalSaleAmount" value="${buyItem.itemPrice.saleAmount}" />

    <c:forEach items="${buyItem.additionItemList}" var="addition">
        <c:set var="totalItemSaleAmount">${totalItemSaleAmount + addition.itemPrice.itemSaleAmount}</c:set>
        <c:set var="totalDiscountAmount">${totalDiscountAmount + addition.itemPrice.discountAmount}</c:set>
        <c:set var="totalSaleAmount">${totalSaleAmount + addition.itemPrice.saleAmount}</c:set>
    </c:forEach>

    <c:if test="${viewTarget == 'cart'}">
        <td><input type="checkbox" name="id" value="${buyItem.cartId}" data-item-id="${buyItem.itemId}" data-item-options="${buyItem.options}" data-parent-item-id="${buyItem.parentItemId}" data-addition-item-flag="${buyItem.additionItemFlag}" title="${buyItem.itemName} 체크박스" class="op-available-item"></td>
    </c:if>

    <td class="tleft">
        <div class="item_info">
            <c:if test="${viewTarget == 'cart'}"><a href="/products/view/${buyItem.item.itemUserCode}"></c:if><p class="photo"><img src="${ shop:loadImageBySrc(buyItem.item.imageSrc, 'XS') }" alt="item photo"></p><c:if test="${viewTarget == 'cart'}"></a></c:if>
            <div class="order_option noline">
                <p class="item_name line2">
                    <c:if test="${viewTarget == 'cart'}"><a href="/products/view/${buyItem.item.itemUserCode}"></c:if>${buyItem.item.itemName}<c:if test="${viewTarget == 'cart'}"></a></c:if>
                </p>

                ${shop:viewOptionText(buyItem.options)}

                ${shop:viewAdditionItemList(buyItem.additionItemList)}

                <c:if test="${not empty buyItem.freeGiftItemText}">
                    <ul class="option">
                        <li>
                            <span class="choice">사은품 : </span>
                            <span>${buyItem.freeGiftItemText}</span>
                        </li>
                    </ul>
                </c:if>

                <c:if test="${shipping.shippingType == 2 || shipping.shippingType == 3 || shipping.shippingType == 4}">
                    <c:set var="shippingTypeText" value="" />
                    <c:choose>
                        <c:when test="${shipping.shippingType == 2}"><c:set var="shippingTypeText" value="판매자" /></c:when>
                        <c:when test="${shipping.shippingType == 3}"><c:set var="shippingTypeText" value="출고지" /></c:when>
                        <c:when test="${shipping.shippingType == 4}"><c:set var="shippingTypeText" value="상품" /></c:when>
                    </c:choose>
                    <div class="delivery_wrap">
                        <a href="#" class="delivery_type">${shippingTypeText} 조건부 무료배송▶</a>
                        <div class="delievery_tip">
                            <p class="title">${shippingTypeText} 조건부 무료배송 </p>
                            <p>
                                <c:set var="deliveryText" value="" />
                                <c:choose>
                                    <c:when test="${shipping.shippingType == 2}"><c:set var="deliveryText" value="판매자" /></c:when>
                                    <c:when test="${shipping.shippingType == 3}"><c:set var="deliveryText" value="동일한 출고지" /></c:when>
                                    <c:when test="${shipping.shippingType == 4}"><c:set var="deliveryText" value="해당" /></c:when>
                                </c:choose>
                                ${deliveryText} 상품 ${op:numberFormat(shipping.shippingFreeAmount)}원 이상 구매 시 무료,<br>미만 구매시 ${ op:numberFormat(shipping.shipping) }원 부과</p>

                                <c:if test="${shipping.shippingExtraCharge1 > 0 || shipping.shippingExtraCharge2 > 0}">
                                <p><strong>제주/도서산간</strong><br>
                                    제주
                                    <c:choose>
                                        <c:when test="${shipping.shippingExtraCharge1 > 0}">
                                            ${op:numberFormat(shipping.shippingExtraCharge1)}원 추가
                                        </c:when>
                                        <c:otherwise>
                                            추가비용 없음
                                        </c:otherwise>
                                    </c:choose>
                                    / 도서산간
                                    <c:choose>
                                        <c:when test="${shipping.shippingExtraCharge2 > 0}">
                                            ${op:numberFormat(shipping.shippingExtraCharge2)}원 추가
                                        </c:when>
                                        <c:otherwise>
                                            추가비용 없음
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </c:if>
                            <a href="#" class="delievery_close"><img src="/content/images/btn/btn_tooltip_close.gif" alt="close"></a>
                        </div>
                    </div> <!-- // delivery_wrap E -->
                </c:if>

            </div>

        </div>
    </td>
    <td>
        <div class="input_wrap col-w-10 num_input">

            <c:choose>
                <c:when test="${viewTarget == 'cart'}">
                    <input type="hidden" name="stockFlag" id="stockFlag-${buyItem.cartId}" value="${ buyItem.item.stockFlag }" />
                    <input type="hidden" name="stockQuantity" id="stockQuantity-${buyItem.cartId}" value="${ buyItem.item.stockQuantity }" />
                    <input type="hidden" name="orderMinQuantity" id="orderMinQuantity-${buyItem.cartId}" value="${ buyItem.orderQuantity.minQuantity }" />
                    <input type="hidden" name="orderMaxQuantity" id="orderMaxQuantity-${buyItem.cartId}" value="${ buyItem.orderQuantity.maxQuantity }" />
                    <input type="hidden" name="shippingGroupCode" id="shippingGroupCode-${buyItem.cartId}" value="${ shipping.shippingGroupCode }" />
                    <input type="text" name="quantity" id="quantity-${buyItem.cartId}" class="_number" value="${ buyItem.itemPrice.quantity }" maxlength="3"> <!-- 최대 수량 999개로 되도록 maxlength 수정 2017-05-10 yulsun.yoo -->
                    <button type="button" class="btn btn-default btn-s" onclick="editQuantity('${buyItem.cartId}')">변경</button>
                </c:when>
                <c:otherwise>${op:numberFormat(buyItem.itemPrice.quantity)} 개</c:otherwise>
            </c:choose>

        </div>
    </td>
    <td>
        <p class="price_01">${op:numberFormat(totalItemSaleAmount)}원 </p>

    </td>
    <td>
        <p class="price_01 itemDiscountAmountText-${buyItem.itemSequence}">${op:numberFormat(totalDiscountAmount)}원</p>

        <p class="op-used-coupon itemCouponUsedArea-${buyItem.itemSequence}">
            <c:if test="${viewTarget == 'order' && useCoupon == true}">
                <c:if test="${buyItem.itemPrice.couponDiscountAmount > 0}">
                    <span>쿠폰사용</span>
                </c:if>
            </c:if>
        </p>

        <c:if test="${viewTarget == 'order' && useCoupon == true}">
            <p class="sale_01 itemCouponDiscountAmountText-${buyItem.itemSequence}" style="display:none"></p>
        </c:if>
    </td>


    <td>
        <div class="sum"><span class="itemPayAmountText-${ buyItem.itemSequence }">${ op:numberFormat(totalSaleAmount) }</span>원</div>
    </td>

    <c:if test="${firstItem == true}">
        <td rowspan="${ rowspan }">
            <p class="delivery_price">
            <p class="price op-shipping-text-${shipping.shippingSequence}">
                <c:choose>
                    <c:when test="${ shipping.realShipping == 0 }">무료배송</c:when>
                    <c:otherwise>
                        <select id="op-shipping-payment-type-${ buyItem.cartId }" name="shippingPaymentType">
                            <option value="1" ${op:selected("1", shipping.shippingPaymentType) }>${ op:numberFormat(shipping.realShipping) }원 선불</option>
                            <option value="2" ${op:selected("2", shipping.shippingPaymentType) }>${ op:numberFormat(shipping.realShipping) }원 착불</option>
                        </select>
                        <%-- ${ op:numberFormat(shipping.realShipping) }원  --%>
                        <%--c:choose>
                            <c:when test="${shipping.shippingPaymentType == '1'}">선불</c:when>
                            <c:otherwise>착불</c:otherwise>
                        </c:choose --%>
                    </c:otherwise>
                </c:choose>
            </p>
            </p>

            <c:choose>
                <c:when test="${viewTarget == 'cart'}">
                    <p class="where_buy">${buyItem.sellerName}</p>
                </c:when>
                <c:otherwise>
                    <c:if test="${buy.shippingCoupon > 0}">
                        <c:if test="${shipping.shippingType != '5' && shipping.shippingType != '1'}">
                            <c:if test="${shipping.shippingPaymentType == '1'}">
                                <p class="where_buy-${shipping.shippingSequence}">
                                    <label><input type="checkbox" class="op-input-shipping-coupon-used" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].useFlag" value="Y" /> 배송비 쿠폰</label>
                                    <input type="hidden" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].shippingGroupCode" value="${shipping.shippingGroupCode}" />
                                </p>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </td>
    </c:if>
    <c:if test="${viewTarget == 'cart'}">
        <td>
            <div class="order_btnList">
                <c:if test="${buyItem.additionItemFlag == 'N'}">
                    <button type="button" style="float:right;" class="btn btn-s btn-submit" title="바로구매" onclick="buyNow(${ buyItem.cartId })">바로구매</button>
                </c:if>
                <button type="button" style="float:right;" class="btn btn-s btn-default" title="삭제하기" data-item-id="${buyItem.itemId}" onclick="deleteItem(${ buyItem.cartId })">삭제하기</button>
            </div>
        </td>
    </c:if>
</tr>

<%--
<tr >
	<c:if test="${viewTarget == 'cart'}">
		<td><input type="checkbox" name="id" value="${ buyItem.cartId }" data-item-id="${buyItem.itemId}" data-parent-item-id="${buyItem.parentItemId}" title="체크박스" class="op-available-item" /></td>
	</c:if>
	<td class="tleft">
		<div class="item_info">
			<c:if test="${viewTarget == 'cart'}"><a href="${buyItem.item.link}"></c:if>
				<p class="photo"><img src="${buyItem.item.imageSrc}" alt="item photo"></p >
				<div class="order_option">
					<p class="item_number">${buyItem.item.itemUserCode}</p>
					<p class="item_name">
						<c:if test="${buyItem.additionItemFlag == 'Y'}">┗(추가상품) </c:if>
						${buyItem.item.itemName}
					</p>

					${ shop:viewOptionText(buyItem.options) }


					<div class="item_price">
						<span>${op:numberFormat(buyItem.itemPrice.sumPrice)}</span>원/ <span>${op:numberFormat(buyItem.itemPrice.quantity)}</span>개
					</div>

				</div>
			<c:if test="${viewTarget == 'cart'}"></a></c:if>
		</div><!--// item_info E-->
	</td>
	<td>
		<div class="input_wrap col-w-10 num_input">
			<c:choose>
				<c:when test="${viewTarget == 'cart'}">
					<input type="hidden" name="stockFlag" id="stockFlag-${buyItem.cartId}" value="${ buyItem.item.stockFlag }" />
					<input type="hidden" name="stockQuantity" id="stockQuantity-${buyItem.cartId}" value="${ buyItem.item.stockQuantity }" />
					<input type="hidden" name="orderMinQuantity" id="orderMinQuantity-${buyItem.cartId}" value="${ buyItem.orderQuantity.minQuantity }" />
					<input type="hidden" name="orderMaxQuantity" id="orderMaxQuantity-${buyItem.cartId}" value="${ buyItem.orderQuantity.maxQuantity }" />
					<input type="text" name="quantity" id="quantity-${buyItem.cartId}" class="_number" value="${ buyItem.itemPrice.quantity }" maxlength="4">
					<button type="button" class="btn btn-default btn-s" onclick="editQuantity('${buyItem.cartId}')">변경</button>
				</c:when>
				<c:otherwise>${op:numberFormat(buyItem.itemPrice.quantity)} 개</c:otherwise>
			</c:choose>
		</div>

	</td>
	<td>
		<%-- CJH 2016.11.10 첫구매 할인 기능 구현 안됨
			<p class="sale">첫구매할인</p>

		<p><span>${op:numberFormat(buyItem.itemPrice.sumPrice)}</span>원</p>
	</td>

	<c:if test="${viewTarget == 'order'}">
		<c:if test="${ useCoupon == true }">
			<td>
				<span class="itemCouponDiscountAmountText-${buyItem.itemSequence}">
					<c:if test="${ buyItem.itemPrice.couponDiscountAmount > 0 }">-</c:if>
					${ op:numberFormat(buyItem.itemPrice.couponDiscountAmount) }
				</span>원
			</td>
		</c:if>
	</c:if>
	<td>
		<div class="sum"><span class="price itemPayAmountText-${ buyItem.itemSequence }">${ op:numberFormat(buyItem.itemPrice.saleAmount) }</span>원</div>
		<c:if test="${viewTarget == 'cart'}">
			<%-- CJH 2016.11.10 장바구니 쿠폰 다운로드 기능 구현안됨
			<button type="button" class="btn btn-normal btn-s">쿠폰다운</button>

		</c:if>
	</td>
	<c:if test="${firstItem == true}">
		<td rowspan="${ rowspan }">

			<c:choose>
				<c:when test="${viewTarget == 'order'}">
					<p class="price op-shipping-text-${shipping.shippingSequence}">
						<c:choose>
							<c:when test="${ shipping.realShipping == 0 }">${op:message('M00448')} <!-- 무료 --></c:when>
							<c:otherwise>
								${ op:numberFormat(shipping.realShipping) }원
								<c:choose>
									<c:when test="${shipping.shippingPaymentType == '1'}">선불</c:when>
									<c:otherwise>착불</c:otherwise>
								</c:choose>
						    </c:otherwise>
						</c:choose>
					</p>
				</c:when>
				<c:otherwise>
					<p class="op-shipping-text-${shipping.shippingSequence}">
						<c:choose>
							<c:when test="${ shipping.realShipping == 0 }">${op:message('M00448')} <!-- 무료 --></c:when>
							<c:otherwise>
								<select id="op-shipping-payment-type-${ buyItem.cartId }" name="shippingPaymentType">
									<option value="1" ${op:selected("1", shipping.shippingPaymentType) }>${ op:numberFormat(shipping.realShipping) }원 선불</option>
									<option value="2" ${op:selected("2", shipping.shippingPaymentType) }>${ op:numberFormat(shipping.realShipping) }원 착불</option>
								</select>
						    </c:otherwise>
						</c:choose>
					</p>
				</c:otherwise>
			</c:choose>
			<c:if test="${viewTarget == 'order'}">
				<c:if test="${buy.shippingCoupon > 0}">
					<c:if test="${shipping.shippingType != '5' && shipping.shippingType != '1'}">
						<c:if test="${shipping.shippingPaymentType == '1'}">
							<p class="op-shipping-coupon-${shipping.shippingSequence}" style="${ shipping.realShipping == 0 ? 'display:none' : ''}"> 배송비 쿠폰
							 	<label><input type="checkbox" class="op-input-shipping-coupon-used" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].useFlag" value="Y" /> 사용</label>
							 	<input type="hidden" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].shippingGroupCode" value="${shipping.shippingGroupCode}" />
							</p>
						</c:if>
					</c:if>
				</c:if>
			</c:if>
			<c:if test="${shipping.shippingType == 2 || shipping.shippingType == 3 || shipping.shippingType == 4}">
				<p>조건부 ${op:numberFormat(shipping.shippingFreeAmount)}원 이상 무료</p>
			</c:if>
		</td>
	</c:if>
	<c:if test="${viewTarget == 'cart'}">
		<td>
			<div class="order_btnList">
				<c:if test="${buyItem.additionItemFlag == 'N'}">
					<button type="button" class="btn btn-s btn-submit" title="바로구매" onclick="buyNow(${ buyItem.cartId })">바로구매</button>
				</c:if>
				<button type="button" class="btn btn-s btn-default" title="삭제하기" onclick="deleteItem(${ buyItem.cartId })">삭제하기</button>
			</div>
		</td>
	</c:if>
</tr>

--%>