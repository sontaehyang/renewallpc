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

    <td class="tleft">
        <div class="item_info">
            <p class="photo"><img src="${ shop:loadImageBySrc(buyItem.item.imageSrc, 'XS') }" id="step1ItemImage" alt="item photo"></p>
            <div class="order_option noline">
                <p class="item_name line2">
                    ${buyItem.item.itemName}
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
            ${op:numberFormat(buyItem.itemPrice.quantity)} 개
        </div>
    </td>
    <td>
        <div class="sum"><p class="price_01">${op:numberFormat(buy.rentalTotAmt)}원 </p></div>

    </td>
    <td>
        <p class="price_01">${buy.rentalPer}개월</p>

<%--        <p class="op-used-coupon itemCouponUsedArea-${buyItem.itemSequence}">--%>
<%--            <c:if test="${viewTarget == 'order' && useCoupon == true}">--%>
<%--                <c:if test="${buyItem.itemPrice.couponDiscountAmount > 0}">--%>
<%--                    <span>쿠폰사용</span>--%>
<%--                </c:if>--%>
<%--            </c:if>--%>
<%--        </p>--%>

<%--        <c:if test="${viewTarget == 'order' && useCoupon == true}">--%>
<%--            <p class="sale_01 itemCouponDiscountAmountText-${buyItem.itemSequence}" style="display:none"></p>--%>
<%--        </c:if>--%>
    </td>


    <td>
        <div class="sum">${ op:numberFormat(buy.rentalMonthAmt) }원</div>
    </td>

    <td>
        <p class="price_01">${op:numberFormat(buy.rentalPartnershipAmt)}원 </p>

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
        </td>
    </c:if>
</tr>
