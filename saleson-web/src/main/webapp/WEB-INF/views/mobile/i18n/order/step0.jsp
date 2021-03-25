<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<%@ taglib prefix="inicis"	tagdir="/WEB-INF/tags/pg/inicis" %>
<%@ taglib prefix="lgdacom"	tagdir="/WEB-INF/tags/pg/lgdacom" %>
<%@ taglib prefix="cj"	tagdir="/WEB-INF/tags/pg/cj" %>
<%@ taglib prefix="kakaopay"	tagdir="/WEB-INF/tags/pg/kakaopay" %>
<%@ taglib prefix="kspay"	tagdir="/WEB-INF/tags/pg/kspay" %>
<%@ taglib prefix="kcp"	tagdir="/WEB-INF/tags/pg/kcp" %>
<%@ taglib prefix="easypay"	tagdir="/WEB-INF/tags/pg/easypay" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<!-- 스마트폰에서 KCP 결제창을 레이어 형태로 구현-->
<div id="layer_all" style="position:absolute; left:0px; top:0px; width:100%;height:100%; z-index:1; display:none;">
    <table height="100%" width="100%" border="-" cellspacing="0" cellpadding="0" style="text-align:center">
        <tr height="100%" width="100%">
            <td>
                <iframe name="frm_all" frameborder="0" marginheight="0" marginwidth="0" border="0" width="100%" height="100%" scrolling="auto"></iframe>
            </td>
        </tr>
    </table>
</div>

<div class="con" style="display: none;">
    <div class="order_wrap mt15" style="margin-bottom: 70px;">
        <form:form modelAttribute="buy" name="buy" action="/sp/order/pay" method="post">
            <%-- 결제하기 후 뒤로가기에서 배송비 재계산을 위한 zipcode--%>
            <input type="text" id="selected-zipcode" style="display: none;"/>
            <input id="appPresence" name="appPresence" type="hidden"  value="true">
            <form:hidden path="orderCode" />
            <form:hidden path="orderPrice.totalItemCouponDiscountAmount" />
            <form:hidden path="orderPrice.totalCartCouponDiscountAmount" />
            <form:hidden path="orderPrice.totalCouponDiscountAmount" />
            <form:hidden path="orderPrice.totalPointDiscountAmount" />
            <form:hidden path="orderPrice.totalShippingCouponUseCount" />
            <form:hidden path="orderPrice.totalShippingCouponDiscountAmount" />
            <form:hidden path="orderPrice.totalItemSaleAmount" />
            <form:hidden path="orderPrice.totalShippingAmount" />
            <form:hidden path="orderPrice.orderPayAmount" />
            <form:hidden path="orderPrice.orderPayAmountTotal" />
            <form:hidden path="orderPrice.payAmount" />
            <form:hidden path="orderPrice.totalUserLevelDiscountAmount" />
            <c:if test="${requestContext.userLogin == true}">

                <c:if test="${ not empty buy.defaultUserDelivery }">
                    <div id="op-default-delivery-input-area">
                        <form:hidden path="defaultUserDelivery.userName" />
                        <form:hidden path="defaultUserDelivery.phone1" />
                        <form:hidden path="defaultUserDelivery.phone2" />
                        <form:hidden path="defaultUserDelivery.phone3" />
                        <form:hidden path="defaultUserDelivery.mobile1" />
                        <form:hidden path="defaultUserDelivery.mobile2" />
                        <form:hidden path="defaultUserDelivery.mobile3" />
                        <form:hidden path="defaultUserDelivery.newZipcode" />
                        <form:hidden path="defaultUserDelivery.zipcode" />
                        <form:hidden path="defaultUserDelivery.zipcode1" />
                        <form:hidden path="defaultUserDelivery.zipcode2" />
                        <form:hidden path="defaultUserDelivery.sido" />
                        <form:hidden path="defaultUserDelivery.sigungu" />
                        <form:hidden path="defaultUserDelivery.eupmyeondong" />
                        <form:hidden path="defaultUserDelivery.address" />
                        <form:hidden path="defaultUserDelivery.addressDetail" />
                    </div>
                </c:if>

                <c:forEach items="${ userDeliveryList }" var="item">
                    <div id="delivery-info-${ item.userDeliveryId }">
                        <input type="hidden" id="userName" value="${ item.userName }" />
                        <input type="hidden" id="phone1" value="${ item.phone1 }" />
                        <input type="hidden" id="phone2" value="${ item.phone2 }" />
                        <input type="hidden" id="phone3" value="${ item.phone3 }" />
                        <input type="hidden" id="mobile1" value="${ item.mobile1 }" />
                        <input type="hidden" id="mobile2" value="${ item.mobile2 }" />
                        <input type="hidden" id="mobile3" value="${ item.mobile3 }" />
                        <input type="hidden" id="newZipcode" value="${ item.newZipcode }" />
                        <input type="hidden" id="zipcode" value="${ item.zipcode }" />
                        <input type="hidden" id="zipcode1" value="${ item.zipcode1 }" />
                        <input type="hidden" id="zipcode2" value="${ item.zipcode2 }" />
                        <input type="hidden" id="sido" value="${ item.sido }" />
                        <input type="hidden" id="sigungu" value="${ item.sigungu }" />
                        <input type="hidden" id="eupmyeondong" value="${ item.eupmyeondong }" />
                        <input type="hidden" id="address" value="${ item.address }" />
                        <input type="hidden" id="addressDetail" value="${ item.addressDetail }" />
                    </div>
                </c:forEach>
            </c:if>

            <div class="order_info box_style_2">
                <div class="box_inner">
                    <h4>${op:message('M00315')}</h4>

                    <div class="order_con" id="op-buyer-input-area">
                        <ul class="del_info">
                            <li>
                                <span class="del_tit star">주문자명</span>
                                <span class="del_detail"><form:input path="buyer.userName" title="주문자명" class="required" maxlength="50" /></span>
                            </li>
                            <li class="chunk order_address hidden">
                                <label for="address01" class="del_tit star">배송지 주소</label>
                                <div class="user_info">
                                    <div class="in_td">
                                        <div class="input_area">
                                            <form:hidden path="buyer.sido" />
                                            <form:hidden path="buyer.sigungu" />
                                            <form:hidden path="buyer.eupmyeondong" />
                                            <form:input path="buyer.newZipcode" readonly="true" maxlength="5" title="우편번호" />
                                            <form:input path="buyer.zipcode" readonly="true" title="우편번호" />
                                        </div>
                                    </div>
                                    <div class="in_td bar"></div>
                                    <div class="in_td address">
                                        <button type="button" id="address01" class="btn_st3 required bd_orange t_point1" onclick="openDaumPostcode('buyer')">우편번호</button>
                                    </div>
                                </div>
                                <div class="input_area">
                                    <form:input path="buyer.address" class="required" readonly="true" maxlength="100" title="주소" />
                                </div>
                                <div class="input_area">
                                    <form:input path="buyer.addressDetail" class="full required" maxlength="100" title="상세주소" />
                                </div>
                            </li>
                            <li>
                                <span class="del_tit star">휴대폰번호</span>
                                <div class="num">
                                    <div class="in_td">
                                        <div class="input_area">
                                            <form:select path="buyer.mobile1" title="휴대폰번호" cssClass="form-control required">
                                                <form:option value="" label="선택"></form:option>
                                                <form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
                                            </form:select>
                                        </div>
                                        <div class="in_td dash"></div>
                                        <div class="input_area">
                                            <form:input path="buyer.mobile2" title="휴대전화" class="_number required" maxlength="4" />
                                        </div>
                                        <div class="in_td dash"></div>
                                        <div class="input_area">
                                            <form:input path="buyer.mobile3" title="휴대전화" class="_number required" maxlength="4" />
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="hidden">
                                <strong class="del_tit">연락처</strong>
                                <div class="num">
                                    <div class="in_td">
                                        <div class="input_area">
                                            <form:select path="buyer.phone1" title="전화번호" cssClass="form-control">
                                                <form:option value="" label="선택"></form:option>
                                                <form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
                                            </form:select>
                                        </div>
                                        <div class="in_td dash"></div>
                                        <div class="input_area">
                                            <form:input path="buyer.phone2" title="전화번호" class="_number" maxlength="4" />
                                        </div>
                                        <div class="in_td dash"></div>
                                        <div class="input_area">
                                            <form:input path="buyer.phone3" title="전화번호" class="_number" maxlength="4" />
                                        </div>
                                    </div>
                                </div>
                            </li>

                            <li class="">
                                <strong class="del_tit">이메일</strong>
                                <span class="del_detail">
								    <form:input path="buyer.email" title="이메일 주소" class="_email full" maxlength="50" />
							    </span>
                            </li>

                        </ul>
                    </div>
                    <!-- //order_con -->
                </div>
            </div>
            <!-- //order_info -->

            <div class="order_get">
                <!-- 향후 복수 배송시 사용시에 열어두기 -->
                <div class="shipping_pl hidden">
                    <c:if test="${buy.additionItem == false}">
                        <c:if test="${buy.multipleDeliveryCount > 1}">
                            <label for="shipping_num">복수배송지 선택</label>
                            <select id="multiple-delivery-set-count">
                                <c:forEach begin="2" end="${buy.multipleDeliveryCount > buy.maxMultipleDelivery ? buy.maxMultipleDelivery : buy.multipleDeliveryCount}" step="1" var="multipleDeliveryValue">
                                    <option value="${multipleDeliveryValue}">${multipleDeliveryValue}</option>
                                </c:forEach>
                            </select>

                            <button type="button" class="btn_st3 t_small" style="display:none;" id="op-cancel-multiple-delivery" onclick="Order.cancelMultipleDelivery()">한곳으로 보내기</button>
                            <button type="button" class="btn_st3 t_small " onclick="Order.multipleDelivery()">복수배송지 선택</button>
                        </c:if>
                    </c:if>
                </div>
                <!-- //shipping_pl -->
                <div class="op-receive-input-area">
                    <c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
                        <c:forEach items="${receiver.buyQuantitys}" var="buyQuantity" varStatus="buyQuantityIndex">
                            <form:hidden path="receivers[${receiverIndex.index}].buyQuantitys[${buyQuantityIndex.index}].itemSequence" value="${buyQuantity.itemSequence}" />
                            <form:hidden path="receivers[${receiverIndex.index}].buyQuantitys[${buyQuantityIndex.index}].quantity" value="${buyQuantity.quantity}" />
                        </c:forEach>

                        <!-- //order_tit -->
                        <div id="op-receive-input-area-${receiverIndex.index}">
                            <div class="order_con box_style_2">
                                <div class="box_inner">
                                    <h4>배송 정보</h4>

                                    <ul class="del_info">
                                        <c:if test="${requestContext.userLogin == true}">
                                            <li>
                                                <label for="chs_addr" class="del_tit">배송지 선택</label>
                                                <div name="chs_addr" id="chs_addr" class="user_info">
                                                    <select name="infoCopy" >
                                                        <c:choose>
                                                            <c:when test="${ not empty buy.defaultUserDelivery }">
                                                                <option value="default-${receiverIndex.index}">${op:message('M01581')}</option> <!-- 기본배송지 -->
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="clear-${receiverIndex.index}" selected disabled hidden>배송지를 선택하세요.</option>
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <c:forEach items="${ userDeliveryList }" var="item">
                                                            <c:if test="${ item.defaultFlag eq 'N' }">
                                                                <option value="delivery-info-${ item.userDeliveryId }-${receiverIndex.index}">${ item.title }</option>
                                                            </c:if>
                                                        </c:forEach>

                                                        <option value="copy-${receiverIndex.index}">주문자 정보와 동일</option>
                                                        <option value="clear-${receiverIndex.index}">${op:message('M00614')}</option> <!-- 새로운 주소 입력 -->
                                                    </select>
                                                </div>
                                            </li>
                                        </c:if>
                                        <li>
                                            <span class="del_tit star">받으시는 분</span>
                                            <div class="input_area">
                                                <form:input path="receivers[${receiverIndex.index}].receiveName" title="받으시는 분" class="required" maxlength="50" />
                                            </div>
                                        </li>
                                        <li class="chunk">
                                            <label for="address02" class="del_tit star">배송지 주소</label>
                                            <div class="user_info">
                                                <div class="in_td">
                                                    <div class="input_area">
                                                        <form:hidden path="receivers[${receiverIndex.index}].receiveZipcode" />
                                                        <form:input path="receivers[${receiverIndex.index}].receiveNewZipcode" title="우편번호" maxlength="5" class="required" readonly="true" />
                                                    </div>
                                                </div>
                                                <div class="in_td bar"></div>
                                                <div class="in_td address">
                                                    <button type="button" id="address02" onclick="openDaumPostcode('receive', ${receiverIndex.index})" class="btn_st3 bd_orange t_point1">우편번호</button>
                                                </div>
                                            </div>
                                            <div class="input_area">
                                                <form:hidden path="receivers[${receiverIndex.index}].receiveSido" />
                                                <form:hidden path="receivers[${receiverIndex.index}].receiveSigungu" />
                                                <form:hidden path="receivers[${receiverIndex.index}].receiveEupmyeondong" />
                                                <form:input path="receivers[${receiverIndex.index}].receiveAddress" title="주소" class="required" maxlength="100" readonly="true" />
                                            </div>
                                            <div class="input_area">
                                                <form:input path="receivers[${receiverIndex.index}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" />
                                            </div>
                                            <c:if test="${requestContext.userLogin == true}">
                                                <div class="add_addr">
                                                    <span class="check">
                                                        <%--<form:checkbox path="saveDeliveryFlag" value="Y" id="add_addr01" />--%>
                                                        <input id="add_addr01" name="saveDeliveryFlag" type="checkbox" value="Y">
                                                        <label for="add_addr01">기본 배송지에 추가</label>
                                                    </span>
                                                </div>
                                            </c:if>
                                        </li>
                                        <li>
                                            <span class="del_tit star">휴대폰번호</span>
                                            <div class="num">
                                                <div class="in_td">
                                                    <div class="input_area">
                                                        <form:select path="receivers[${receiverIndex.index}].receiveMobile1" title="휴대전화" cssClass="form-control required">
                                                            <form:option value="" label="선택"></form:option>
                                                            <form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
                                                        </form:select>
                                                    </div>
                                                    <div class="in_td dash"></div>
                                                    <div class="input_area">
                                                        <form:input path="receivers[${receiverIndex.index}].receiveMobile2" title="휴대전화" class="_number required" maxlength="4" />
                                                    </div>
                                                    <div class="in_td dash"></div>
                                                    <div class="input_area">
                                                        <form:input path="receivers[${receiverIndex.index}].receiveMobile3" title="휴대전화" class="_number required" maxlength="4" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <span class="del_tit">전화번호</span>
                                            <div class="num">
                                                <div class="in_td">
                                                    <div class="input_area">
                                                        <form:select path="receivers[${receiverIndex.index}].receivePhone1" title="전화번호" cssClass="form-control">
                                                            <form:option value="" label="선택"></form:option>
                                                            <form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
                                                        </form:select>
                                                    </div>
                                                    <div class="in_td dash"></div>
                                                    <div class="input_area">
                                                        <form:input path="receivers[${receiverIndex.index}].receivePhone2" title="전화번호" class="_number" maxlength="4" />
                                                    </div>
                                                    <div class="in_td dash"></div>
                                                    <div class="input_area">
                                                        <form:input path="receivers[${receiverIndex.index}].receivePhone3" title="전화번호" class="_number" maxlength="4" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <span class="del_tit">배송시 요구사항</span>
                                            <div class="input_area">
                                                <select id="absent-sel-${receiverIndex.index}" class="absent_sel mb5" onChange="changeDeliveryTxt(this.value, ${receiverIndex.index})">
                                                    <option value="부재시 경비실에 맡겨주세요.">부재시 경비실에 맡겨주세요.</option>
                                                    <option vlaue="부재시 휴대폰으로 연락바랍니다.">부재시 휴대폰으로 연락바랍니다.</option>
                                                    <option vlaue="부재시 문 앞에 놓아주세요.">부재시 문 앞에 놓아주세요.</option>
                                                    <option value="집 앞에 놓아주세요.">집 앞에 놓아주세요.</option>
                                                    <option vlaue="택배함에 넣어주세요.">택배함에 넣어주세요.</option>
                                                    <option value="직접 입력">직접 입력</option>
                                                </select>
                                                <div id="absent-txt-${receiverIndex.index}" class="absent_text">
                                                    <form:input path="receivers[${receiverIndex.index}].content" title="배송시 요구사항" class="full _filter" value="부재시 경비실에 맡겨주세요." readonly="true" />
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <!-- //order_con -->

                            <c:set var="totalItemRowCount">0</c:set>
                            <c:forEach items="${buy.receivers}" var="receiver">
                                <c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
                                    <c:set var="singleShipping" value="${shipping.singleShipping}"/>
                                    <c:choose>
                                        <c:when test="${singleShipping == true}">
                                            <c:set var="totalItemRowCount">${totalItemRowCount + 1}</c:set>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="itemIndex">
                                                <c:set var="totalItemRowCount">${totalItemRowCount + 1}</c:set>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:forEach>

                            <div class="order_item box_style_2">
                                <div class="box_inner order_list">
                                    <h4>주문 내역</h4>
                                    <div class="item_list_wrap list_st_2 img_100">
                                        <ul> <!-- <ul class="item_list"> -->
                                            <c:set var="itemIndex">0</c:set>
                                            <c:set var="notshipping" value="Y"></c:set>
                                            <c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
                                                <c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
                                                    <c:set var="singleShipping" value="${shipping.singleShipping}"/>
                                                    <c:choose>
                                                        <c:when test="${singleShipping == true}">
                                                            <c:set var="buyItem" value="${shipping.buyItem}" />
                                                            <%--<c:set var="notshipping" value="${buyItem.item.useIslandFlag}"></c:set>--%>
                                                            <li>
                                                                <%--<input type="hidden" id="IslandFlag" value="${buyItem.item.useIslandFlag}">--%>
                                                                <div class="product"><!--<div class="item">-->
                                                                    <div class="product_img"><!--<div class="order_img">-->
                                                                            <%-- <img src="${buyItem.item.imageSrc}" alt="item photo"> --%>
                                                                        <%--<img src="${shop:loadImage(buyItem.item.itemUserCode, buyItem.item.itemImage, 'XS')}" alt="item photo">--%>
                                                                    </div>
                                                                    <div class="product_info pd0">
                                                                        <div class="order_name">
                                                                            <p class="tit mt0">
                                                                                <c:if test="${buyItem.additionItemFlag == 'Y'}">┗(추가상품) </c:if>
                                                                                    ${ buyItem.item.itemName }
                                                                            </p>
                                                                            <p class="option">${ shop:buyViewOptionText(buyItem.options) }</p>
                                                                            <c:if test="${not empty buyItem.freeGiftItemText}">
                                                                                <p class="detail">${buyItem.freeGiftItemText}</p>
                                                                            </c:if>
                                                                        </div>
                                                                        <div class="order_price">
                                                                            <p class="price">
                                                                                    <%--<span class="discount">${op:numberFormat(buyItem.itemPrice.itemSaleAmount)}원</span>--%>
                                                                                <span class="ts_medium">수량 <strong class="ts_medium">${op:numberFormat(buyItem.itemPrice.quantity)}개</strong></span>
                                                                                <span class="fright order_price"><strong class="ts_medium">${op:numberFormat(buyItem.itemPrice.itemSaleAmount)}</strong>원</span> <%--sumPrice--%>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="buyItemIndex">
                                                                <%--<c:set var="notshipping" value="${buyItem.item.useIslandFlag}"></c:set>--%>
                                                                <li>
                                                                    <div class="product"><!--<div class="item">-->
                                                                        <div class="product_img"><!--<div class="order_img">-->
                                                                                <%-- <img src="${buyItem.item.imageSrc}" alt="item photo"> --%>
                                                                            <%--<img src="${shop:loadImage(buyItem.item.itemUserCode, buyItem.item.itemImage, 'XS')}" alt="item photo">--%>
                                                                        </div>
                                                                        <div class="product_info pr0">
                                                                            <div class="order_name">
                                                                                <p class="tit mt0">
                                                                                    <c:if test="${buyItem.additionItemFlag == 'Y'}">┗(추가상품) </c:if>
                                                                                        ${ buyItem.item.itemName }
                                                                                </p>
                                                                                <p class="detail">${ shop:buyViewOptionText(buyItem.options) }</p>
                                                                                <c:if test="${not empty buyItem.freeGiftItemText}">
                                                                                    <p class="detail">${buyItem.freeGiftItemText}</p>
                                                                                </c:if>
                                                                            </div>
                                                                            <div class="order_price">
                                                                                <p class="price">
                                                                                        <%--<span class="discount">${op:numberFormat(buyItem.itemPrice.itemSaleAmount)}원</span>--%>
                                                                                    <span class="ts_medium">수량 <strong class="ts_medium">${op:numberFormat(buyItem.itemPrice.quantity)}개</strong></span>
                                                                                    <span class="fright order_price"><strong class="ts_medium">${op:numberFormat(buyItem.itemPrice.itemSaleAmount)}</strong>원</span>
                                                                                </p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <%-- <c:if test="${buy.shippingCoupon > 0}">
                                                        <c:if test="${shipping.shippingType != '5' && shipping.shippingType != '1'}">
                                                            <c:if test="${shipping.shippingPaymentType == '1'}">
                                                                <div class="shipping_coupon where_buy-${shipping.shippingSequence}">
                                                                    <span class="del_tit t_gray">배송비 쿠폰</span>
                                                                    <span class="check">
                                                                        <input type="hidden" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].shippingGroupCode" value="${shipping.shippingGroupCode}" />
                                                                        <input type="checkbox" class="op-input-shipping-coupon-used" id="shipping_coupon_use${shipping.shippingSequence}" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].useFlag" value="Y">
                                                                        <label for="shipping_coupon_use${shipping.shippingSequence}">사용</label>
                                                                    </span>
                                                                </div>
                                                            </c:if>
                                                        </c:if>
                                                    </c:if> --%>

                                                    <div class="shipping_coupon fwrap">
                                                        <span class="del_tit">배송비</span>
                                                        <c:if test="${buy.shippingCoupon > 0}">
                                                            <c:if test="${shipping.shippingType != '5' && shipping.shippingType != '1'}">
                                                                <c:if test="${shipping.shippingPaymentType == '1'}">
                                                                    <div class="where_buy-${shipping.shippingSequence} coupon_check">
                                                                <span class="check">
                                                                    <input type="hidden" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].shippingGroupCode" value="${shipping.shippingGroupCode}" />
                                                                    <input type="checkbox" class="op-input-shipping-coupon-used" id="shipping_coupon_use${shipping.shippingSequence}" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].useFlag" value="Y">
                                                                    <label for="shipping_coupon_use${shipping.shippingSequence}" class="ts_default t_light">쿠폰 사용</label>
                                                                </span>
                                                                    </div>
                                                                </c:if>
                                                            </c:if>
                                                        </c:if>
                                                        <div class="shipping_price fright">
                                                            <input type="hidden" id="use-island-flag-${shipping.shippingSequence}" value="${notshipping}" />
                                                            <span class="op-shipping-text-${shipping.shippingSequence} ts_medium">
                                                                <c:choose>
                                                                    <c:when test="${ shipping.realShipping == 0 }">무료배송</c:when>
                                                                    <c:otherwise>
                                                                        ${ op:numberFormat(shipping.realShipping) }원
                                                                    </c:otherwise>
                                                                </c:choose>
                                                             </span>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="box_style_2">
                <div class="box_inner">
                    <h4>결제 정보</h4>
                    <div class="cart_order">
                        <div class="txt_wrap">
                            <div class="order total">
                                <span class="tit">총 상품 금액</span>
                                <p class="total_price"><span class="t_bold">${ op:numberFormat(buy.orderPrice.totalItemPrice) }</span>원</p>
                            </div>
                            <div class="shipping total">
                                <span class="tit">총 배송비</span>
                                <p class="total_price"><span class="op-total-delivery-charge-text t_bold">${ op:numberFormat(buy.orderPrice.totalShippingAmount) }</span>원</p>
                            </div>
                            <div class="sale total">
                                <c:set var="buyTotalDiscount">${buy.orderPrice.totalItemCouponDiscountAmount}</c:set>
                                <span class="tit">할인금액</span>
                                <p class="total_price">
                                    <span class="t_bold">- </span>
                                    <!-- 할인금액에 쿠폰 할인가는 제외해서 나오도록 수정 2019.01.14 한미화 -->
                                        <%--<span class="op-total-discount-amount-text t_bold">${ op:numberFormat(buyTotalDiscount) }</span>원--%>
                                    <span class="op-total-discount-amount-text-except2 t_bold">${ op:numberFormat(buyTotalDiscount) }</span>원
                                </p>
                            </div>
                            <c:if test="${requestContext.userLogin == true}">
                                <c:if test="${not empty buy.buyPayments['point']}">
                                    <div class="coupon total">
                                        <div class="fleft">
                                            <span class="tit">쿠폰 할인</span>
                                                <%--<p class="tit_desc t_small t_gray"><br />(100P 단위로 사용가능)</p>--%>
                                        </div>
                                        <div class="fleft apply_coupon"><button type="button" id="coupon" onClick="javascript:viewCoupon();" class="btn_st3 bd_orange t_point1">쿠폰적용</button></div>
                                        <div class="input_area fright discount_input">
                                            <span class="t_bold">-</span>
                                            <input type="text" title="쿠폰 할인" class="_number w_30 totalCouponDiscountAmountText" value="${ op:numberFormat(buy.orderPrice.totalCouponDiscountAmount) }" readonly="readonly" onclick="viewCoupon()" />
                                            <span>원</span>

                                            <div class="op-coupon-hide-field-area" style="display:none;">
                                                <c:if test="${ !empty buy.makeUseCouponKeys }">
                                                    <c:forEach items="${ buy.makeUseCouponKeys }" var="value">
                                                        <input type="hidden" name="useCouponKeys" value="${ value }" class="useCoupon" />
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="point total">
                                        <div class="fleft">
                                            <span class="tit">포인트</span>
                                            <!--<p class="tit_desc t_small"><br />100P 단위로 사용가능</p>-->
                                        </div>
                                        <div class="fleft apply_coupon"><button type="button" id="point" onClick="javascript:retentionPointUseAll();" class="btn_st3 bd_orange t_point1">모두사용</button></div>
                                        <div class="fright" style="text-align: right;">
                                            <div class="input_area discount_input">
                                                <span class="t_bold">-</span>
                                                <form:hidden path="buyPayments['point'].amount" />
                                                <input type="text" class="w70 op-total-point-discount-amount-text" value="${ op:numberFormat(buy.orderPrice.totalPointDiscountAmount) }"
                                                       <c:if test="${ buy.retentionPoint == 0 }">disabled="disabled"</c:if>>
                                                <span>원</span>
                                            </div>
                                            <p class="tit_desc t_small t_gray">(사용 가능 포인트
                                                <c:if test="${ shopConfig.pointUseMin > 0 && buy.retentionPoint > 0 }">
                                                    ${ op:numberFormat(shopConfig.pointUseMin) }P ~
                                                </c:if>
                                                    ${ op:numberFormat(buy.retentionPoint) }P)
                                            </p>
                                        </div>
                                    </div>
                                </c:if>
                            </c:if>
                            <div class="sum">
                                <span class="tit">최종 결제금액</span>
                                <p class="total_price"><span class="op-order-pay-amount-text">${ op:numberFormat(buy.orderPrice.orderPayAmount) }</span>원</p>
                            </div>
                            <c:if test="${requestContext.userLogin == true}">
                                <div class="point_desc">
                                    <p>적립예정 포인트 <span class="op-earn-point-text">${op:numberFormat(buy.orderPrice.totalEarnPoint)}</span></p>
                                </div>
                            </c:if>
                        </div>
                        <!-- //txt_wrap -->
                    </div>
                    <!-- //cart_order -->
                        <%-- <c:if test="${requestContext.userLogin == true}">
                            <c:if test="${not empty buy.buyPayments['point']}">
                                <div class="point_dis">
                                    <div class="order_tit">
                                        <h3>쿠폰 할인  / ${op:message('M00620')}</h3>
                                        <p class="tit_desc">100P 단위로 사용가능</p>
                                    </div>
                                    <div class="point_con">
                                        <label for="point" class="del_tit t_gray">쿠폰</label>
                                        <div class="user_info coupon-area">
                                            <div class="in_td">
                                                <div class="input_area">
                                                    <input type="text" title="쿠폰 할인" class="_number w_30 totalCouponDiscountAmountText" value="${ op:numberFormat(buy.orderPrice.totalCouponDiscountAmount) }" readonly="readonly" onclick="viewCoupon()" />
                                                </div>
                                            </div>
                                            <span>원</span>
                                            <div class="in_td point_all">
                                                <button type="button" id="coupon" onClick="javascript:viewCoupon();" class="btn_st3">쿠폰적용</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="point_rst">
                                        <div class="op-coupon-hide-field-area" style="display:none;">
                                            <c:if test="${ !empty buy.makeUseCouponKeys }">
                                                <c:forEach items="${ buy.makeUseCouponKeys }" var="value">
                                                    <input type="hidden" name="useCouponKeys" value="${ value }" class="useCoupon" />
                                                </c:forEach>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- //point_rst -->
                                    <!-- //order_tit -->
                                    <div class="point_con">
                                        <label for="point" class="del_tit t_gray">포인트</label>
                                        <div class="user_info point-area">
                                            <div class="in_td">
                                                <div class="input_area">
                                                    <form:hidden path="buyPayments['point'].amount" />
                                                    <input type="text" class="w70 op-total-point-discount-amount-text" value="${ op:numberFormat(buy.orderPrice.totalPointDiscountAmount) }"
                                                           <c:if test="${ buy.retentionPoint == 0 }">disabled="disabled"</c:if>>
                                                </div>
                                            </div>
                                            <span>P</span>
                                            <div class="in_td point_all">
                                                <button type="button" id="point" onClick="javascript:retentionPointUseAll();" class="btn_st3">모두사용</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="point_rst">
                                        <span class="del_tit t_gray t_gray">사용가능 포인트</span>
                                        <p class="point_total">
                                    <span>
                                        <c:if test="${ pointUseMin > 0 && buy.retentionPoint > 0 }">
                                            ${ op:numberFormat(pointUseMin) } ~
                                        </c:if>
                                        ${ op:numberFormat(buy.retentionPoint) }
                                    </span>P
                                        </p>
                                    </div>
                                </div>
                                <!-- //point_dis -->
                            </c:if>
                        </c:if> --%>
                </div>
            </div>

            <div class="payment_method box_style_2" <c:if test="${ buy.defaultPaymentType == 'noPay' }">style="display:none"</c:if>>
                <div class="box_inner">
                    <h4>결제 수단</h4>

                    <div class="method_tab">
                        <ul>
                            <c:if test="${ not empty buy.buyPayments['card'] }">
                                <li class="op-pay-type-select op-pay-type-card-select <c:if test="${ buy.defaultPaymentType == 'card' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('card')">
										<input type="checkbox" id="op-payType-card" value="card" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'card' }">checked="checked"</c:if> />
										신용카드
									</a>
								</span>
                                </li>
                            </c:if>
                            <c:if test="${ not empty buy.buyPayments['vbank'] }">
                                <li class="op-pay-type-select op-pay-type-vbank-select <c:if test="${ buy.defaultPaymentType == 'vbank' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('vbank')">
									<input type="checkbox" id="op-payType-vbank" value="vbank" name="payType" class="hide"
                                           <c:if test="${ buy.defaultPaymentType == 'vbank' }">checked="checked"</c:if>/>
										가상 계좌
									</a>
								</span>
                                </li>
                            </c:if>
                            <!-- 무통장입금 숨김 처리 2019.01.15 한미화 -->
                                <%--
                                <c:if test="${ not empty buy.buyPayments['bank'] }">
                                    <li class="op-pay-type-select op-pay-type-bank-select <c:if test="${ buy.defaultPaymentType == 'bank' }">on</c:if> last">
                                    <span>
                                        <a href="javascript:;" onclick="payTypeSelect('bank')">
                                            <input type="checkbox" id="op-payType-bank" value="bank" name="payType" class="hide"
                                                   <c:if test="${ buy.defaultPaymentType == 'bank' }">checked="checked"</c:if>/>
                                            무통장 입금
                                        </a>
                                    </span>
                                    </li>
                                </c:if>
                                --%>
                            <c:if test="${ not empty buy.buyPayments['escrow'] }">
                                <li class="op-pay-type-select op-pay-type-escrow-select <c:if test="${ buy.defaultPaymentType == 'escrow' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('escrow')">
										<input type="checkbox" id="op-payType-escrow" value="escrow" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'escrow' }">checked="checked"</c:if>/>
										에스크로
									</a>
								</span>
                                </li>
                            </c:if>
                            <c:if test="${ not empty buy.buyPayments['realtimebank'] }">
                                <li class="op-pay-type-select op-pay-type-realtimebank-select <c:if test="${ buy.defaultPaymentType == 'realtimebank' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('realtimebank')">
										<input type="checkbox" id="op-payType-realtimebank" value="realtimebank" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'realtimebank' }">checked="checked"</c:if>/>
										실시간 계좌이체
									</a>
								</span>
                                </li>
                            </c:if>
                            <c:if test="${ not empty buy.buyPayments['hp'] }">
                                <li class="op-pay-type-select op-pay-type-hp-select <c:if test="${ buy.defaultPaymentType == 'hp' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('hp')">
										<input type="checkbox" id="op-payType-hp" value="hp" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'hp' }">checked="checked"</c:if>/>
										휴대폰 결제
									</a>
								</span>
                                </li>
                            </c:if>
                            <c:if test="${ not empty buy.buyPayments['kakaopay'] }">
                                <li class="op-pay-type-select op-pay-type-kakaopay-select <c:if test="${ buy.defaultPaymentType == 'kakaopay' }">on</c:if> last">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('kakaopay')">
										<input type="checkbox" id="op-payType-kakaopay" value="kakaopay" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'kakaopay' }">checked="checked"</c:if>/>
										카카오페이
									</a>
								</span>
                                </li>
                            </c:if>
                            <c:if test="${ not empty buy.buyPayments['card'] }">
                                <li class="op-pay-type-select op-pay-type-samsungpay-select <c:if test="${ buy.defaultPaymentType == 'samsungpay' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('samsungpay')">
										<input type="checkbox" id="op-payType-samsungpay" value="samsungpay" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'samsungpay' }">checked="checked"</c:if> />
										삼성페이
									</a>
								</span>
                                </li>
                            </c:if>
                            <c:if test="${ not empty buy.buyPayments['card'] }">
                                <li class="op-pay-type-select op-pay-type-paycopay-select <c:if test="${ buy.defaultPaymentType == 'paycopay' }">on</c:if> last">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('paycopay')">
										<input type="checkbox" id="op-payType-paycopay" value="paycopay" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'paycopay' }">checked="checked"</c:if> />
										PAYCO
									</a>
								</span>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                    <!-- //method_tab -->
                    <div class="method_con_wrap">
                        <c:if test="${ not empty buy.buyPayments['card'] }">

                            <div class="pgInputArea">

                                <c:choose>
                                    <c:when test="${op:property('pg.service') == 'inicis' }">
                                        <inicis:inipay-mobile-input />
                                    </c:when>
                                    <c:when test="${op:property('pg.service') == 'lgdacom' }">
                                        <lgdacom:xpay-mobile-input />
                                    </c:when>
                                    <c:when test="${op:property('pg.service') == 'cj' }">
                                        <cj:cj-mobile-input />
                                    </c:when>
                                    <c:when test="${op:property('pg.service') == 'kspay' }">
                                        <kspay:kspay-mobile-input />
                                    </c:when>
                                    <c:when test="${op:property('pg.service') == 'kcp' }">
                                        <kcp:kcp-mobile-input />
                                    </c:when>
                                    <c:when test="${op:property('pg.service') == 'easypay' }">
                                        <easypay:easypay-mobile-input />
                                    </c:when>
                                </c:choose>

                            </div>

                            <div class="method_con op-payType-input" id="op-payType-card-input" <c:if test="${ buy.defaultPaymentType != 'card' }">style="display:none;"</c:if>>
                                <span class="del_tit">신용카드 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'card' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['card'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'card' ? 'op-default-payment' : '' }"
                                                 paymentType="card" value="${buy.defaultPaymentType == 'card' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                            </div>
                            <div class="method_con op-payType-input" id="op-payType-samsungpay-input" <c:if test="${ buy.defaultPaymentType != 'samsungpay' }">style="display:none;"</c:if>>
                                <span class="del_tit">삼성페이 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'samsungpay' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['samsungpay'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'samsungpay' ? 'op-default-payment' : '' }"
                                                 paymentType="samsungpay" value="${buy.defaultPaymentType == 'samsungpay' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                            </div>
                            <div class="method_con op-payType-input" id="op-payType-paycopay-input" <c:if test="${ buy.defaultPaymentType != 'paycopay' }">style="display:none;"</c:if>>
                                <span class="del_tit">PAYCO 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'paycopay' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['paycopay'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'paycopay' ? 'op-default-payment' : '' }"
                                                 paymentType="paycopay" value="${buy.defaultPaymentType == 'paycopay' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                            </div>

                            <!-- //method_con -->
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['vbank'] }">
                            <div class="method_con op-payType-input" id="op-payType-vbank-input" <c:if test="${ buy.defaultPaymentType != 'vbank' }">style="display:none;"</c:if>>
                                <span class="del_tit">가상계좌 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'vbank' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['vbank'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'vbank' ? 'op-default-payment' : '' }"
                                                 paymentType="vbank" value="${buy.defaultPaymentType == 'vbank' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                                <c:if test="${op:property('pg.useEscrow') eq 'Y'}">
                                    <tr>
                                        <th scope="row">에스크로 사용여부</th>
                                        <td>
                                            <div class="input_wrap col-w-7">
                                                <form:select id="escrowStatus" path="buyPayments['vbank'].escrowStatus" cssClass="form-control" title="에스크로 사용여부">
                                                    <form:option value="0" label="사용"></form:option>
                                                    <form:option value="N" label="사용 안함" selected="selected"></form:option>
                                                </form:select>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                            </div>
                            <!-- //method_con -->
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['bank'] }">
                            <div class="method_con op-payType-input" id="op-payType-bank-input" <c:if test="${ buy.defaultPaymentType != 'bank' }">style="display:none;"</c:if>>
                                <span class="del_tit">무통장입금 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'bank' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['bank'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'bank' ? 'op-default-payment' : '' }"
                                                 paymentType="bank" value="${buy.defaultPaymentType == 'bank' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                                <ul class="method_detail">
                                    <li>무통장 입금시 발생하는 수수료는 손님 부담입니다.</li>
                                    <li>
                                        인터넷 뱅킹 또는 은행창구 입금시 의뢰인(송금인)명은 '입금인 입력'란에 입금하신 성함과 동일하게 기재해 주시기 바랍니다.(만약 다를 경우 고객지원센터 <span>1833-6609</span>로 꼭 연락주시기 바랍니다.)
                                    </li>
                                    <li>무통장 입금시 입급자명을 입력해주세요.</li>
                                    <li>현금영수증 미신청시 현금영수증 발급이 되지 않습니다.</li>
                                </ul>
                                <div class="user_info_wrap">
                                    <label for="order_bank" class="del_tit">입금은행</label>
                                    <div class="user_info">
                                        <div class="in_td">
                                            <form:select path="buyPayments['bank'].bankVirtualNo" title="입금은행" Untouched="bank">
                                                <c:forEach items="${ buy.buyPayments['bank'].accountNumbers }" var="list">
                                                    <c:set var="accountValue" value="${ list.bankName }^${ list.accountNumber }^${ list.accountHolder }" />
                                                    <form:option value="${ accountValue }" label="${ list.bankName } (${ list.accountNumber }) ${ list.accountHolder }" />
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <!-- //user_info_wrap -->
                                <div class="user_info_wrap">
                                    <label for="deposit_name" class="del_tit">입금자명</label>
                                    <div class="user_info">
                                        <div class="in_td">
                                            <form:input path="buyPayments['bank'].bankInName" id="bankInName" title="입금자명" Untouched="depositor" cssClass="right w_96" />
                                        </div>
                                    </div>
                                </div>
                                <div class="user_info_wrap">
                                    <label for="bank_expiration_date" class="del_tit">입금예정일</label>
                                    <div class="user_info">
                                        <div class="in_td">
                                            <form:select path="buyPayments['bank'].bankExpirationDate" title="입금예정일" Untouched="expiration">
                                                <c:forEach items="${ buy.buyPayments['bank'].expirationDates }" var="item">
                                                    <form:option value="${ op:date(item) }" />
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>

                                <div class="cash_receipt_desc">
                                    <ul>
                                        <li><a href="/sp/order/payment-guide?paymentType=bank" class="t_lgray t_medium">무통장입금 이용안내</a></li>
                                        <!-- 									<li><a href="/sp/order/payment-guide?paymentType=tax" class="t_lgray t_medium">현금영수증 안내</a></li> -->
                                    </ul>
                                </div>
                                <!-- //cash_receipt_desc -->
                            </div>
                            <!-- //method_con -->
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['escrow'] }">
                            <div class="method_con op-payType-input" id="op-payType-escrow-input" <c:if test="${ buy.defaultPaymentType != 'escrow' }">style="display:none;"</c:if>>
                                <span class="del_tit">에스크로 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'escrow' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['escrow'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'escrow' ? 'op-default-payment' : '' }"
                                                 paymentType="escrow" value="${buy.defaultPaymentType == 'escrow' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                            </div>
                            <!-- //method_con -->
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['realtimebank'] }">
                            <div class="method_con op-payType-input" id="op-payType-realtimebank-input" <c:if test="${ buy.defaultPaymentType != 'realtimebank' }">style="display:none;"</c:if>>
                                <span class="del_tit">실시간 계좌이체 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'realtimebank' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['realtimebank'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'realtimebank' ? 'op-default-payment' : '' }"
                                                 paymentType="realtimebank" value="${buy.defaultPaymentType == 'realtimebank' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                                <c:if test="${op:property('pg.useEscrow') eq 'Y'}">
                                    <tr>
                                        <th scope="row">에스크로 사용여부</th>
                                        <td>
                                            <div class="input_wrap col-w-7">
                                                <form:select id="escrowStatus" path="buyPayments['realtimebank'].escrowStatus" cssClass="form-control" title="에스크로 사용여부">
                                                    <form:option value="0" label="사용"></form:option>
                                                    <form:option value="N" label="사용 안함" selected="selected"></form:option>
                                                </form:select>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                                <div class="cash_receipt_desc">
                                    <ul>
                                        <li><a href="/sp/order/payment-guide?paymentType=realtimebank" class="t_lgray t_medium">실시간 계좌이체 안내</a></li>
                                    </ul>
                                </div>
                                <!-- //cash_receipt_desc -->
                            </div>
                            <!-- //method_con -->
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['hp'] }">
                            <div class="method_con op-payType-input" id="op-payType-hp-input" <c:if test="${ buy.defaultPaymentType != 'hp' }">style="display:none;"</c:if>>
                                <span class="del_tit">휴대폰 결제금액</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'hp' ? buy.orderPrice.orderPayAmount : 0 }
                                </span>원
                                    <form:hidden path="buyPayments['hp'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'hp' ? 'op-default-payment' : '' }"
                                                 paymentType="hp" value="${buy.defaultPaymentType == 'hp' ? buy.orderPrice.orderPayAmount : 0 }" />
                                </p>
                                <ul class="method_detail">
                                    <li>휴대폰 결제 한도가 30만원에서 50만원으로 변경되었습니다.</li>
                                    <li>단, 결제한도는 통신사별 회원등급에 따라 적용됩니다.</li>
                                </ul>
                            </div>
                            <!-- //method_con -->
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['kakaopay'] }">
                            <div class="method_con op-payType-input" id="op-payType-kakaopay-input" <c:if test="${ buy.defaultPaymentType != 'kakaopay' }">style="display:none;"</c:if>>
                                <span class="del_tit">KakaoPay 결제</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'kakaopay' ? buy.orderPrice.orderPayAmount : 0 }
                                </span> 원
                                </p>
                                <div class="input_wrap col-w-7 op-kakaopay-request-data">
                                    <form:hidden path="buyPayments['kakaopay'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'kakaopay' ? 'op-default-payment' : '' }"
                                                 paymentType="kakaopay" value="${buy.defaultPaymentType == 'kakaopay' ? buy.orderPrice.orderPayAmount : 0 }" />
                                    <kakaopay:kakaopay-input />
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['payco'] }">
                            <div class="method_con op-payType-input" id="op-payType-payco-input" <c:if test="${ buy.defaultPaymentType != 'payco' }">style="display:none;"</c:if>>
                                <span class="t_gray">payco 결제</span>
                                <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'payco' ? buy.orderPrice.orderPayAmount : 0 }
                                </span> 원
                                </p>
                                <div class="input_wrap col-w-7 op-payco-request-data">
                                    <form:hidden path="buyPayments['payco'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'payco' ? 'op-default-payment' : '' }"
                                                 paymentType="payco" value="${buy.defaultPaymentType == 'payco' ? buy.orderPrice.orderPayAmount : 0 }" />
                                    <kakaopay:kakaopay-input />
                                </div>
                            </div>
                        </c:if>
                        <!--//user_info_wrap -->
                        <div id="cash_receipt_view" style="border: 1px solid #999; margin:15px; ; padding-left: 15px;display: none;">
                            <div class="cash_receipt">
                                <span class="del_tit">현금영수증</span>
                                <ul class="cash_receipt_list">
                                    <c:forEach items="${cashbillTypes}" var="cashbillType" varStatus="i">
                                        <li class="cashbillType_${i.count}">
                                            <form:radiobutton path="cashbill.cashbillType" value="${cashbillType.code}" id="cashbill_${i.count}" checked="${cashbillType.code == 'NONE' ? 'checked' : ''}" />
                                            <label for="cashbill_${i.count}"><span><span></span></span>${cashbillType.title}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <!--// cash_receipt -->

                            <div class="cash_receipt_con">
                                <div class="receipt_evidence_con op-receipt-all" id="op-receipt-evidence" style="display:none;">
                                    <span class="tit t_gray t_medium">사업자 등록번호</span>
                                    <div class="num">
                                        <div class="in_td">
                                            <div class="input_area">
                                                <form:input path="cashbill.businessNumber1" maxlength="3" class="_number businessNumber" title="사업자 등록번호 앞자리" />
                                            </div>
                                            <div class="in_td dash"></div>
                                            <div class="input_area">
                                                <form:input path="cashbill.businessNumber2" maxlength="2" class="_number businessNumber" title="사업자 등록번호 중간자리" />
                                            </div>
                                            <div class="in_td dash"></div>
                                            <div class="input_area">
                                                <form:input path="cashbill.businessNumber3" maxlength="5" class="_number businessNumber" title="사업자 등록번호 끝자리" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- //receipt_evidence_con -->
                                <div class="receipt_personal_con op-receipt-all" id="op-receipt-personal" style="display:none;">
                                    <span class="tit t_gray t_medium">휴대폰 번호</span>
                                    <div class="num">
                                        <div class="in_td">
                                            <div class="input_area">
                                                <form:input path="cashbill.cashbillPhone1" maxlength="4" class="_number cashbillPhone" title="휴대폰 번호 앞자리"/>
                                            </div>
                                            <div class="in_td dash"></div>
                                            <div class="input_area">
                                                <form:input path="cashbill.cashbillPhone2" maxlength="4"  class="_number cashbillPhone" title="휴대폰 번호 중간자리"/>
                                            </div>
                                            <div class="in_td dash"></div>
                                            <div class="input_area">
                                                <form:input path="cashbill.cashbillPhone3" maxlength="4" class="_number cashbillPhone" title="휴대폰 번호 끝자리"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--//receipt_personal_con -->
                            </div>
                        </div>
                        <!--//cash_receipt_con -->
                    </div>
                    <!-- //method_con_wrap -->
                </div>
                <!-- //box_inner -->
            </div>
            <!-- //payment_method -->

            <!-- 주문동의 화면 주석 2019.01.15 한미화 -->
            <div class="box_style_2 agree_wrap">
                <div class="box_inner">
                    <span class="check" style="text-align: center;">
                        <input id="agree_check" type="checkbox" name="checkbox" class="required" title="구매동의" checked disabled>
                        <label for="agree_check">위 내용을 확인하시고 결제를 진행해 주시기 바랍니다.</label>
                    </span>
                </div>
            </div>
            <!-- //agree_wrap -->

            <div class="order_btn_wrap fixed">
                    <%--<button type="button" class="btn_st1 b_gray" onclick="location.href='/sp/cart'" >돌아가기</button>--%>
                <button type="submit" class="btn_st1 b_orange w100p" id="payment-btn">결제하기</button>
                <a href="javascript:void(0)" onclick="alert('제주/도서산간지역 배송 불가 상품이 포함되어 있습니다.')" class="btn_st1 b_black w100p hidden" id="no-payment-btn">결제 불가</a>
            </div>

        </form:form>

        <div class="hide op-default-multiple-receiver-view">
            <div id="op-receiver-{RECEIVER_INDEX}" style="display:none">
                <div class="order_tit">
                    <h3>받는사람 정보{RECEIVER_VIEW_INDEX}</h3>
                </div>
                <div class="order_con">
                    <ul class="del_info type-view">
                        <li>
                            <span class="del_tit star t_lgray">받으시는 분</span>
                            <div class="info-view">
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveName" title="받으시는 분" class="required" maxlength="50" />
                                {receiveName}
                            </div>
                        </li>
                        <li class="chunk">
                            <span class="del_tit t_lgray star">배송지 주소</span>
                            <div class="info-view">
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveNewZipcode" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveZipcode" title="우편번호" maxlength="7" class="required" readonly="true" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveSido" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveSigungu" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveEupmyeondong" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveAddress" title="주소" style="width:80%" class="required" maxlength="100" readonly="true" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" />
                                ({receiveZipcode}) {receiveAddress} {receiveAddressDetail}
                            </div>
                        </li>
                        <li>
                            <span class="del_tit t_lgray star">휴대폰 번호</span>
                            <div class="info-view">
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile1" title="휴대전화" class="_number required" maxlength="4" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile2" title="휴대전화" class="_number required" maxlength="4" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile3" title="휴대전화" class="_number required" maxlength="4" />
                                {receiveMobile1}-{receiveMobile2}-{receiveMobile3}
                            </div>
                        </li>
                        <li>
                            <span class="del_tit t_lgray">배송시 요구사항</span>
                            <div class="info-view">
                                <input type="text" name="receivers[{RECEIVER_INDEX}].content" title="배송시 요구사항" class="full _filter" placeholder="ex) 부재시 경비실에 맡겨주세요." />
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>	<!--//order_wrap E-->
</div>	<!--//con E-->

<page:javascript>
    <c:choose>
        <c:when test="${shopContext.mobileLayer == true}"><daum:address-layer /></c:when>
        <c:otherwise><daum:address /></c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${op:property('pg.service') == 'inicis'}">
            <inicis:inipay-mobile-script />
        </c:when>
        <c:when test="${op:property('pg.service') == 'lgdacom' }">
            <lgdacom:xpay-mobile-script />
        </c:when>
        <c:when test="${op:property('pg.service') == 'cj' }">
            <cj:cj-mobile-script />
        </c:when>
        <c:when test="${op:property('pg.service') == 'kspay' }">
            <kspay:kspay-mobile-script />
        </c:when>
        <c:when test="${op:property('pg.service') == 'kcp' }">
            <kcp:kcp-mobile-script />
        </c:when>
        <c:when test="${op:property('pg.service') == 'easypay' }">
            <easypay:easypay-mobile-script />
        </c:when>
    </c:choose>

    <c:if test="${ not empty buy.buyPayments['kakaopay'] }">
        <kakaopay:kakaopay-script />
    </c:if>


    <script type="text/javascript" src="/content/modules/op.order.js"></script>
    <script type="text/javascript">
        Order.init(${userData}, "${op:property('pg.service')}", '${shopConfig.minimumPaymentAmount}', '${shopConfig.pointUseMin}');
        $(function(){

            Order.setShippingAmount(); // 쿠폰 사용 체크 2017-04-25 yulsun.yoo

            $('input[name="cashbill.cashbillType"]').on('click', function(e){
                document.getElementById('cashbill.cashbillPhone1').value = '';
                document.getElementById('cashbill.cashbillPhone2').value = '';
                document.getElementById('cashbill.cashbillPhone3').value = '';

                document.getElementById('cashbill.businessNumber1').value = '';
                document.getElementById('cashbill.businessNumber2').value = '';
                document.getElementById('cashbill.businessNumber3').value = '';

                if ($(this).val() == 'BUSINESS') {
                    $('div#op-receipt-personal').hide();
                    $('div#op-receipt-evidence').show();
                } else if ($(this).val() == 'PERSONAL') {
                    $('div#op-receipt-evidence').hide();
                    $('div#op-receipt-personal').show();
                } else {
                    $('div#op-receipt-evidence').hide();
                    $('div#op-receipt-personal').hide();
                }
            });

            // 모바일 키페드 셋팅
            // Common.setMobileKeypad(true);

            historyBackDataSet();
            $("#buy").validator({
                'requiredClass' : 'required',
                'submitHandler' : function() {
                    try {
                        Order.setAmountText(false);
                        if (Order.checkPayAmount() == false) {
                            return false;
                        }
                        var orderPayAmount = $('input[name="orderPrice.orderPayAmount"]').val();

                        if (orderPayAmount < 0) {
                            alert('결제 금액을 확인하십시오. 결제 요청액 : ' + orderPayAmount);
                            return false;
                        }


                        if ($('input[name="payType"][value="bank"]').prop('checked') == true) {

                            if ($('input.op-order-payAmounts[paymentType="bank"]').val() > 0) {

                                $bankVirtualNo = $('select[name="buyPayments[\'bank\'].bankVirtualNo"]');
                                if ($bankVirtualNo.find(':selected').val() == '') {
                                    alert($bankVirtualNo.attr('title') + '입력해주세요.');
                                    $bankVirtualNo.focus();
                                    return false;
                                }

                                $bankInName = $('input[name="buyPayments[\'bank\'].bankInName"]');
                                if ($bankInName.val() == '') {
                                    alert($bankInName.attr('title') + '입력해주세요.'); // 입력
                                    $bankInName.focus();
                                    return false;
                                }

                                $bankDate = $('select[name="buyPayments[\'bank\'].bankDate"]');
                                if ($bankDate.find(':selected').val() == '') {
                                    alert($bankDate.attr('title') + '선택해주세요.'); // 선택
                                    $bankDate.focus();
                                    return false;
                                }
                            }
                        }

                        var isSuccess = false;

                        var savePaymentType = [];
                        $.post('/sp/order/save', $("#buy").serialize(), function(response){
                            Common.responseHandler(response, function(response) {
                                isSuccess = true;
                                $('input[name="orderCode"]').val(response.data.orderCode);
                                savePaymentType = response.data.savePaymentType;
                                if (Order.getApprovalType(savePaymentType, 'card') || Order.getApprovalType(savePaymentType, 'vbank') || Order.getApprovalType(savePaymentType, 'realtimebank')
                                    || Order.getApprovalType(savePaymentType, 'hp')
                                    || (Order.pgType == 'inicis' && Order.getApprovalType(savePaymentType, 'escrow'))) {

                                    if (Order.pgType == 'inicis') {
                                        $.each(response.data.pgData, function(key, value){
                                            $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                        });

                                    } else if (Order.pgType == 'lgdacom') {
                                        $.each(response.data.pgData, function(key, value){
                                            $('input[name="'+ key +'"]').val(value);

                                        });
                                    } else if (Order.pgType == 'cj') {
                                        $.each(response.data.pgData, function(key, value){
                                            $('input[name="'+ key +'"]').val(value);
                                        });
                                    } else if (Order.pgType == 'kspay') {
                                        $.each(response.data.pgData, function(key, value){
                                            $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                        });
                                    } else if (Order.pgType == 'kcp') {
                                        $.each(response.data.pgData, function(key, value){
                                            $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                        });
                                    } else if (Order.pgType == 'easypay') {
                                        $.each(response.data.pgData, function(key, value){
                                            $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                        });
                                    }

                                } else if (Order.getApprovalType(savePaymentType, 'payco')) {

                                    if (response.data.payco == undefined) {
                                        isSuccess = false;
                                        alert('PAYCO 결제 실패!!');
                                    } else {
                                        if (response.data.payco.code == "0") {
                                            location.href = response.data.payco.result.orderSheetUrl;
                                        } else {
                                            alert("[" + response.data.payco.code + "] " + response.data.payco.message);
                                            isSuccess = false;
                                        }
                                    }
                                }  else if (Order.getApprovalType(savePaymentType, 'kakaopay')) {
                                    var data = response.data;
                                    var kakaopay = response.data.kakaopay;
                                    var txnRequest = kakaopay.txnRequest;
                                    var txnResponse = kakaopay.txnResponse;

                                    isSuccess = false;
                                    if (kakaopay == undefined || txnRequest == undefined || txnResponse == undefined) {
                                        alert('KakaoPay 결제 실패!!');

                                    } else if (txnResponse.resultCode != "00") {
                                        alert("[" + txnResponse.resultCode + "] " + txnResponse.resultMsg);

                                    } else {
                                        // 결제 요청값 설정.
                                        var $kakaopayRequestData = $('.op-kakaopay-request-data');
                                        $.each(response.data.kakaopay.txnRequest, function(key, value) {
                                            $kakaopayRequestData.find('input[name="' + key + '"]').val(value);
                                            $kakaopayRequestData.find('.op-kakaopay-' + key).val(value);
                                            //	console.log(key, value);
                                        });

                                        $.each(response.data.kakaopay.txnResponse, function(key, value) {
                                            $kakaopayRequestData.find('#' + key).val(value);
                                            //$('input[name="'+ key +'"]').val(value);
                                            console.log(key, value);
                                        });

                                        // 구매자명 / 이메일
                                        $kakaopayRequestData.find('input[name=BuyerName]').val(data.userName);
                                        $kakaopayRequestData.find('input[name=BuyerEmail]').val(data.email);



                                        isSuccess = true;
                                    }

                                }



                            }, function(response){
                                alert(response.errorMessage);
                            });
                        });

                        if (!isSuccess) {
                            return false;
                        }

                        if (Order.getApprovalType(savePaymentType, 'kakaopay')) {
                            kakaopay();
                            return false;

                        } else if (Order.getApprovalType(savePaymentType, 'payco')) {
                            return false;
                        } else if (Order.getApprovalType(savePaymentType, 'card') || Order.getApprovalType(savePaymentType, 'vbank') || Order.getApprovalType(savePaymentType, 'realtimebank')
                            || Order.getApprovalType(savePaymentType, 'hp')
                            || (Order.pgType == 'inicis' && Order.getApprovalType(savePaymentType, 'escrow'))) {

                            if (Order.pgType == 'inicis') {
                                iniPayMobile();
                                return false;
                            } else if (Order.pgType == 'lgdacom') {

                                if (launchCrossPlatform()) {

                                }

                            } else if (Order.pgType == 'cj') {

                                var pgUrl = "${op:property('pg.cj.card.pay.url')}";
                                if (Order.getApprovalType(savePaymentType, 'vbank')) {
                                    pgUrl = "${op:property('pg.cj.vbank.pay.url')}";
                                } else if (Order.getApprovalType(savePaymentType, 'realtimebank')) {
                                    pgUrl = "${op:property('pg.cj.realtimebank.pay.url')}";
                                }

                                cjMobile(pgUrl);
                                return false;

                            } else if (Order.pgType == 'kspay') {
                                if (ksPayStart() == false) {
                                    return false;
                                }

                                return false;
                            } else if (Order.pgType == 'kcp') {
                                if (kcpLaunchCrossPlatform() == false) {
                                    return false;
                                }

                                return false;
                            } else if (Order.pgType == 'easypay') {
                                if (easyPayLaunch() == false) {
                                    return false;
                                }

                                return false;
                            }

                        }
                    }catch(e) {
                        alert(e.message);return false;
                    }
                }
            });

            $('input.op-input-shipping-coupon-used').on('change', function() {
                if (Order.buy.shippingCoupon <= Order.useShippingCoupon) {
                    $(this).prop('checked', false);
                }

                Order.setShippingAmount();
            });

            // 포인트 사용 - 직접 입력
            $('input.op-total-point-discount-amount-text').on('focusout', function(){
                var usePoint = $(this).val().replace(",", '');
                Order.pointUsed(usePoint);
            });

            // 받으시는분 복사
            $('select[name="infoCopy"]').on('change', function() {
                var index = $(this).val().split('-')[1];
                var type = $(this).val().split('-')[0];
                if (type == 'copy') {
                    $.each($('input, select', $('#op-buyer-input-area')), function() {
                        var id = $(this).attr('id').replace('buyer.', '');
                        var name = uppercase(id);
                        name = 'receive' + name;

                        if (name == 'receiveUserName') {
                            name = 'receiveName';
                        }

                        name = 'receivers['+index+'].' +  name;
                        if ($('input[name="' + name + '"]').size() == 1) {
                            $('input[name="' + name + '"]').val($(this).val());
                        }
                    });
                } else if(type == 'clear') {
                    $('input, select', '#op-receive-input-area-' + index).not('select[name="infoCopy"], #multiple-delivery-set-count').val("");
                } else if (type == 'default') {
                    $.each($('input, select', $('#op-default-delivery-input-area')), function() {
                        var id = $(this).attr('id').replace('defaultUserDelivery.', '');
                        var name = uppercase(id);
                        name = 'receive' + name;

                        if (name == 'receiveUserName') {
                            name = 'receiveName';
                        }

                        name = 'receivers['+index+'].' +  name;
                        if ($('input[name="' + name + '"]').size() == 1) {
                            $('input[name="' + name + '"]').val($(this).val());
                        } else if ($('select[name="' + name + '"]').size() == 1) {
                            $('select[name="' + name + '"]').val($(this).val());
                        }
                    });
                } else {
                    var temp = $(this).val().split('-');
                    if (temp.length == 4) {
                        var index = temp[3];
                        var target = temp[0] + '-' + temp[1] + '-' + temp[2];
                        $.each($('input, select', $('div#' + target)), function() {
                            var id = $(this).attr('id').replace('orderShippingInfo.', '');
                            var name = uppercase(id);
                            name = 'receive' + name;

                            if (name == 'receiveUserName') {
                                name = 'receiveName';
                            }

                            name = 'receivers['+index+'].' +  name;
                            if ($('input[name="' + name + '"]').size() == 1) {
                                $('input[name="' + name + '"]').val($(this).val());
                            } else if ($('select[name="' + name + '"]').size() == 1) {
                                $('select[name="' + name + '"]').val($(this).val());
                            }
                        });
                    }
                }

                // 배송지에 따른 배송비 설정
                var zipcode = $('input[name="receivers['+ index +'].receiveZipcode"]').val();
                Order.changeReceiverZipcode(zipcode, index);

                // 배송지에 따른 배송비 설정
                Order.setShippingAmount();

                Order.setAmountText();
            });

            $('input.op-order-payAmounts').on('keyup', function() {

                var thisPaymentType = $(this).attr('paymentType');
                var thisPay = Number($(this).val());

                $('input.op-order-payAmounts').not(this).val(0);
                if (thisPay < Order.buy.orderPayAmount) {

                    var targetIndex = 0;
                    $.each($('input[name="payType"]:checked'), function(i, object) {
                        if (thisPaymentType != $(this).val()) {

                            // 처음꺼에다가 몰아주기
                            if (targetIndex == 0) {
                                $('input.op-order-payAmounts[paymentType="'+ $(this).val() +'"]').val(Number(Order.buy.orderPayAmount) - thisPay);
                            }

                            targetIndex++;
                        }
                    });


                    if (targetIndex == 0) {
                        thisPay = Order.buy.orderPayAmount;
                    }
                } else {
                    thisPay = Order.buy.orderPayAmount;
                }

                $(this).val(thisPay);

            });

            $('input[name="payType"]').on('click', function() {

                if ($('input[name="payType"]:checked').size() == 0) {
                    return false;
                }

                var notMixPayTypeSelectCount = 0;
                $.each(Order.notMixPayType, function(i, payType) {
                    $.each($('input[name="payType"]:checked'), function(j, t) {
                        if (payType == $(this).val()) {
                            notMixPayTypeSelectCount++;
                        }
                    });
                });

                // 복합결제가 불가능한 결제타입을 1개이상 선택하였으면
                if (notMixPayTypeSelectCount > 1) {
                    $('input[name="payType"]:checked').not(this).prop('checked', false);
                }

                $('div.op-payType-input').hide();
                $('input.op-order-payAmounts').removeClass('op-default-payment');
                $.each($('input[name="payType"]:checked'), function(i) {
                    if (i == 0) {
                        $('input.op-order-payAmounts', $('#op-payType-' + $(this).val() + '-input')).addClass('op-default-payment');
                    }

                    $('#op-payType-' + $(this).val() + '-input').show();
                });

                Order.setOrderPayAmountClear();
            });

            // 상품쿠폰 선택
            $('select[name="useCouponKeys"]').on('change', function() {
                var value = $(this).val();
                var couponId = $(this).attr('id');

                if (value == '') {
                    return;
                }

                if (value == 'clear') {
                    $.each($(this).find('option'), function(){
                        if ($(this).val() != 'clear') {
                            findEqItemCouponsDisabled($(this).val(), couponId, false);
                        }
                    });
                } else {
                    $.each($(this).find('option'), function(){
                        if ($(this).val() != 'clear') {
                            if ($(this).val() != value) {
                                findEqItemCouponsDisabled($(this).val(), couponId, false);
                            }
                        }
                    });

                    findEqItemCouponsDisabled(value, couponId, true);
                }

                setUseCoupons();
            });

            $('input[name="useCouponKeys"]').on('click', function() {
                setUseCoupons();
            });

            if ($('select[name="useCouponKeys"] :selected').size() > 0) {
                $.each($('select[name="useCouponKeys"]'), function(){
                    var value = $(this).val();
                    var couponId = $(this).attr('id');

                    if (value == 'clear') {

                    } else {
                        $.each($(this).find('option'), function(){
                            if ($(this).val() != 'clear') {
                                if ($(this).val() != value) {
                                    findEqItemCouponsDisabled($(this).val(), couponId, false);
                                }
                            }
                        });

                        findEqItemCouponsDisabled(value, couponId, true);
                    }

                    setUseCoupons();
                });
            }
        });

        // 포인트 사용 - 전체 사용
        function retentionPointUseAll(){

            Order.pointUsedAllForMobile();

        }

        function payTypeSelect(payType) {

            $_this = $('input#op-payType-' + payType);

            if ($_this.prop('checked') == true) {
                if ($('input[name="payType"]:checked').size() == 1) {
                    return;
                }
                $_this.prop('checked', false);
            } else {
                $_this.prop('checked', true);
            }

            if ($('input[name="payType"]:checked').size() > 1) { // 프론트랑 마찬가지로 복합결제는 안되게. 나중에 사용시 이 부분 지우면 됨. 2016.01.20 kye
                $('input[name="payType"]:checked').not($_this).prop('checked', false);
            }

            var notMixPayTypeSelectCount = 0;
            var isOfflinePayError = false;
            $.each(Order.notMixPayType, function(i, payType) {
                $.each($('input[name="payType"]:checked'), function(j, t) {
                    if (payType == $(this).val()) {
                        notMixPayTypeSelectCount++;
                    } else if ($(this).val() == 'offlinepay' && $('input[name="payType"]:checked').size() > 2) {
                        // LG 전화승인/단말기승인의 경우 2개 이상의 복합 결제를 할수 없도록 막자..
                        notMixPayTypeSelectCount++;
                        isOfflinePayError = true;
                    }
                });
            });

            // 복합결제가 불가능한 결제타입을 1개이상 선택하였으면
            if (notMixPayTypeSelectCount > 1) {
                $('input[name="payType"]:checked').not($_this).prop('checked', false);
                if (isOfflinePayError) {
                    offlinePaymentClear();
                }
            }

            $('li.op-pay-type-select').removeClass('on');
            $.each($('input[name="payType"]:checked'), function() {
                if ($(this).prop('checked') == true) {
                    var pt = $(this).val();
                    $('li.op-pay-type-'+ pt +'-select').addClass('on');
                }
            });

            $('.op-payType-input').hide();
            $('input.op-order-payAmounts').removeClass('op-default-payment');

            $.each($('input[name="payType"]:checked'), function(i) {
                if (i == 0) {
                    $('input.op-order-payAmounts', $('#op-payType-' + $(this).val() + '-input')).addClass('op-default-payment');
                }

                $('#op-payType-' + $(this).val() + '-input').show();
                //$('.pay-type-' + $(this).val() + '-sub-input').show();
            });

            Order.setOrderPayAmountClear();

            if ('${op:property("pg.service")}' == 'kcp') {
                if(payType == 'realtimebank') {
                    payType = 'acnt';
                } else if(payType == 'vbank') {
                    payType = 'vcnt';
                }
                document.buy.ActionResult.value = payType;
            }

            // 2017.09.20 손준의 - 현금영수증 신청 정보 입력란 보이기/숨기기
            // 결제수단 선택시 함수 사용
            cashReceiptSet(payType);
        }

        function paycoPaySuccess(url) {
            location.replace(url);
        }

        function paycoErrorMessage(errorCode, message, url) {
            Common.loading.hide();
            if (message == '') {
                message = '['+errorCode+'] 페이코 결제에 실패 하였습니다.';
            }

            alert(message);
            if (url != undefined) {
                location.replace(url);
            }
        }

        // 할인 적용내용 초기화!!
        function historyBackDataSet() {
            Order.setAmountText();
        }

        //쿠폰 적용
        function viewCoupon() {
            couponPopup = Common.popup("about:blank", 'couponView', 900, 700, 1);

            var formName = "receiverForm";
            $('form#' + formName).remove();
            $form = $('<form name="'+formName+'" method="post" action="/sp/order/coupon" target="couponView" />');

            $.each($('.op-receive-input-area', '#buy').find('input'), function(){
                $form.append($('<input />').attr({
                    'type'	: 'hidden',
                    'value'	: $(this).val(),
                    'name'	: $(this).attr('name')
                }))
            });

            $('body').append($form);
            $form.submit();

            //$('#buy').attr('action', "/sp/order/coupon");
            //$('#buy').attr('target', 'couponView');
            //$('#buy').submit();

            Common.loading.hide();
        }

        function setUseCoupons() {
            var itemCoupons = [];
            var cartCoupons = [];

            var totalItemCouponDiscountAmount = 0;
            $('.op-item-coupon-discount-text').html('0');
            $.each($('select[name="useCouponKeys"] :selected'), function(){
                var key = $(this).val();
                var temps = key.split('-');

                if (temps.length == 4) {
                    var discountAmount = parseInt($('input[name="discount-amount-' + key + '-value"]').val());
                    var discountPrice = parseInt($('input[name="discount-price-' + key + '-value"]').val());
                    var couponConcurrently = parseInt($('input[name="coupon-concurrently-' + key + '-value"]').val());
                    var coupon = {
                        'key'					: key,
                        'itemSequence'			: temps[3],
                        'couponUserId'			: temps[2],
                        'discountPrice' 		: discountPrice,
                        'discountAmount' 		: discountAmount,
                        'couponConcurrently' 	: couponConcurrently
                    };

                    var target = $('#op-discount-coupon-' + temps[3] + '-text');
                    target.html(Common.numberFormat(discountAmount));
                    totalItemCouponDiscountAmount += discountAmount;
                    itemCoupons.push(coupon);
                }
            });


            var cartCouponDiscountTargetAmount = Order.totalItemPriceByCoupon - totalItemCouponDiscountAmount;
            $.each($('input[name="useCouponKeys"]'), function(){
                var key = $(this).val();

                var discountType = $('input[name="coupon-pay-type-' + key + '"]').val();
                if (discountType == '2') {
                    var couponPay = $('input[name="coupon-pay-' + key + '"]').val();
                    try {
                        var discountPay = cartCouponDiscountTargetAmount * (couponPay / 100);
                        $('input[name="discount-amount-' + key + '-value"]').val(discountPay);
                        $('#op-discount-amount-' + key + '-text').html(Common.numberFormat(discountPay));
                    } catch(e) {

                        $(this).prop('disabled', true);
                    }
                }

            });

            $.each($('input[name="useCouponKeys"]:checked'), function(){
                if ($(this).prop('disabled') == false) {
                    var key = $(this).val();
                    var temps = key.split('-');
                    if (temps.length == 3) {
                        var discountAmount = parseInt($('input[name="discount-amount-' + key + '-value"]').val());
                        var coupon = {
                            'key'			: key,
                            'couponUserId'	: temps[3],
                            'discountAmount': discountAmount
                        };

                        cartCoupons.push(coupon);
                    }
                }
            });

            Order.setCouponDiscountAmount(itemCoupons, cartCoupons);
        }

        function findEqItemCouponsDisabled(couponKey, thisId, disabled) {
            var temp = couponKey.split('-');
            if (temp.length == 4) {
                var key = temp[0] + "-" + temp[1] + "-" + temp[2];
                $.each($('select[name="useCouponKeys"]').not('#' + thisId), function(){
                    $selectBox = $(this);
                    $.each($selectBox.find('option'), function() {
                        $option = $(this);
                        if ($option.val() != 'clear') {
                            if ($option.val().indexOf(key) == 0) {
                                var textDecoration = 'none';
                                if (disabled == true) {
                                    if ($selectBox.find(':selected').val() == $option.val()) {
                                        $selectBox.val('clear');
                                    }

                                    textDecoration = 'line-through';
                                }

                                // 이거 안먹음...ㅠ
                                $option.css('text-decoration', textDecoration);
                                $option.prop('disabled', disabled);
                            }
                        }
                    });
                });
            }
        }

        function uppercase(text) {
            if (text == '' || text == undefined) return text;
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        }

        //다음 우편번호 검색
        function openDaumPostcode(mode, index) {
            var newZipcode		= "buyer.newZipcode";
            var zipcode 		= "buyer.zipcode";
            var zipcode1 		= "buyer.zipcode1";
            var zipcode2 		= "buyer.zipcode2";
            var address 		= "buyer.address";
            var addressDetail 	= "buyer.addressDetail";
            var sido			= "buyer.sido";
            var sigungu			= "buyer.sigungu";
            var eupmyeondong	= "buyer.eupmyeondong";


            if (mode == "receive") {
                newZipcode 		= "receivers["+index+"].receiveNewZipcode";
                zipcode 		= "receivers["+index+"].receiveZipcode";
                zipcode1 		= "receivers["+index+"].receiveZipcode1";
                zipcode2 		= "receivers["+index+"].receiveZipcode2";
                address 		= "receivers["+index+"].receiveAddress";
                addressDetail 	= "receivers["+index+"].receiveAddressDetail";
                sido			= "receivers["+index+"].receiveSido";
                sigungu			= "receivers["+index+"].receiveSigungu";
                eupmyeondong	= "receivers["+index+"].receiveEupmyeondong";
            }

            var tagNames = {
                'newZipcode'			: newZipcode,
                'zipcode' 				: zipcode,
                'zipcode1' 				: zipcode1,
                'zipcode2' 				: zipcode2,
                'sido'					: sido,
                'sigungu'				: sigungu,
                'eupmyeondong'			: eupmyeondong,
                'roadAddress'			: address,
                'jibunAddressDetail' 	: addressDetail
            }

            openDaumAddress(tagNames, function(data){

                // 배송비 재설정
                Order.setShippingAmount(data.postcode);

                Order.setAmountText();

            });

        }


        function isZenkaku(str) {
            for (var i = 0; i < str.length; i++) {
                var c = str.charCodeAt(i);

                if (c >= 65296 && c <= 65305) {
                    return false;
                }

                // Shift_JIS: 0x0 ～ 0x80, 0xa0 , 0xa1 ～ 0xdf , 0xfd ～ 0xff
                // Unicode : 0x0 ～ 0x80, 0xf8f0, 0xff61 ～ 0xff9f, 0xf8f1 ～ 0xf8f3
                if ((c >= 0x0 && c < 0x81) || (c == 0xf8f0) || (c >= 0xff61 && c < 0xffa0) || (c >= 0xf8f1 && c < 0xf8f4)) {
                    return false;
                }
            }

            return true;
        }

        //쿠폰 적용
        function viewDelivery() {
            couponPopup = Common.popup("about:blank", 'deliveryView', 900, 700, 1);

            var formName = "receiverForm";
            $('form#' + formName).remove();
            $form = $('<form name="'+formName+'" method="post" action="/sp/order/delivery" target="deliveryView" />');

            $.each($('.op-receive-input-area', '#buy').find('input'), function(){
                $form.append($('<input />').attr({
                    'type'	: 'hidden',
                    'value'	: $(this).val(),
                    'name'	: $(this).attr('name')
                }))
            });

            $('body').append($form);
            $form.submit();

            //$('#buy').attr('action', "/sp/order/coupon");
            //$('#buy').attr('target', 'couponView');
            //$('#buy').submit();

            Common.loading.hide();
        }

        function cashReceiptSet(payType){
            // 2017.09.20 손준의 - 현금영수증 신청 정보 입력란 보이기/숨기기

            document.getElementById('cash_receipt_view').style = 'display:none';

            document.getElementById('op-receipt-personal').style = 'display:none';
            document.getElementById('op-receipt-evidence').style = 'display:none';
            document.getElementById('cashbill_1').checked = true


            document.getElementById('cashbill.cashbillPhone1').value = '';
            document.getElementById('cashbill.cashbillPhone2').value = '';
            document.getElementById('cashbill.cashbillPhone3').value = '';

            document.getElementById('cashbill.businessNumber1').value = '';
            document.getElementById('cashbill.businessNumber2').value = '';
            document.getElementById('cashbill.businessNumber3').value = '';

            if("${op:property('pg.service')}" == "kcp"){
                if(payType == 'vcnt'){
                    payType = 'vbank';
                } else if(payType == 'acnt'){
                    payType = 'realtimebank';
                }
            }

            if(payType == 'bank' || ('${op:property("pg.autoCashReceipt")}' == 'N' && (payType == 'vbank' || payType == 'realtimebank'))){
                $('div#cash_receipt_view').css('display','');
            } else {
                $('div#cash_receipt_view').css('display','none');
            }
        }

        function autoLogin(token){
            $.post('/auth/auto-login', token, function(response) {
                if(response.status=="OK"){
                    payment();
                } else {
                    alert("로그인 실패");
                }
            }, 'json');
        }

        function payment(){
            $("#appPresence").val(true);

            // 화면 hide
            $("html").append("<div class='sec_title'><h2>처리중 입니다..</h2></div>");

            // 주문자정보 셋
            $("input[id='buyer.userName']").val("${payInfo.buyerUserName}");    // 주문자명
            $("select[id='buyer.mobile1']").val("${payInfo.buyerMobile1}");     // 주문자 연락처1
            $("input[id='buyer.mobile2']").val("${payInfo.buyerMobile2}");     // 주문자 연락처2
            $("input[id='buyer.mobile3']").val("${payInfo.buyerMobile3}");     // 주문자 연락처3
            $("input[id='buyer.email']").val("${payInfo.buyerEmail}");// 이메일
            <%--$("input[id='buyer.newZipcode']").val("${payInfo.receiveNewZipcode}");--%>
            <%--$("input[id='buyer.zipcode']").val("${payInfo.receiveZipcode}");--%>
            $("input[id='buyer.newZipcode']").val("${payInfo.receiveNewZipcode}");
            $("input[id='buyer.zipcode']").val("${payInfo.receiveZipcode}");
            $("input[id='buyer.sido']").val("${payInfo.receiveSido}");
            $("input[id='buyer.sigungu']").val("${payInfo.receiveSigungu}");
            $("input[id='buyer.eupmyeondong']").val("${payInfo.receiveEupmyeondong}");
            $("input[id='buyer.address']").val("${payInfo.receiveAddress}");
            $("input[id='buyer.addressDetail']").val("${payInfo.receiveAddressDetail}");
            <%--$("input[id='buyer.address']").val("${payInfo.receiveAddress}");--%>
            <%--$("input[id='buyer.addressDetail']").val("${payInfo.receiveAddressDetail}");--%>

            // 배송정보 셋
            $("select[name='infoCopy']").val("default-0"); // 배송지선택
            $("input[id='receivers0.receiveName']").val("${payInfo.receiveName}");     // 받으시는분

            var zipcode = "${payInfo.receiveZipcode}";
            if(zipcode == "" || zipcode == null || zipcode == undefined){
                zipcode = "${payInfo.receiveNewZipcode}";
            }
            $("input[id='receivers0.receiveZipcode']").val("${payInfo.receiveZipcode}");     // 구 우편번호
            $("input[id='receivers0.receiveNewZipcode']").val("${payInfo.receiveNewZipcode}");     // 신 우편번호

            $("input[id='receivers0.receiveSido']").val("${payInfo.receiveSido}");          // 시도
            $("input[id='receivers0.receiveSigungu']").val("${payInfo.receiveSigungu}");       // 시군구
            $("input[id='receivers0.receiveEupmyeondong']").val("${payInfo.receiveEupmyeondong}");  // 읍면동

            $("input[id='receivers0.receiveAddress']").val("${payInfo.receiveAddress}");     // 배송지 주소
            $("input[id='receivers0.receiveAddressDetail']").val("${payInfo.receiveAddressDetail}");     // 배송지 상세주소
            $("select[id='receivers0.receiveMobile1']").val("${payInfo.receiveMobile1}");     // 받는사람 모바일 연락처1
            $("input[id='receivers0.receiveMobile2']").val("${payInfo.receiveMobile2}");     // 받는사람 모바일 연락처2
            $("input[id='receivers0.receiveMobile3']").val("${payInfo.receiveMobile3}");     // 받는사람 모바일 연락처3

            $("select[id='receivers0.receivePhone1']").val("${payInfo.receivePhone1}");     // 받는사람 전화번호 연락처1
            $("input[id='receivers0.receivePhone2']").val("${payInfo.receivePhone2}");     // 받는사람 전화번호 연락처2
            $("input[id='receivers0.receivePhone3']").val("${payInfo.receivePhone3}");     // 받는사람 전화번호연락처3

            $("input[id='receivers0.content']").val("${payInfo.content}");     // 배송시 요구사항
            // 결제수단(card, vbank, bank, hp)
            payTypeSelect("${payInfo.paymentType}");
            // 무통장입금의 경우 입금은행, 입금자명 설정
            var bankVirtualNo = "${payInfo.bankVirtualNo}";
            var bankInName = "${payInfo.bankInName}";
            var bankExpirationDate = "${payInfo.bankExpirationDate}";
            if(bankVirtualNo != null && bankVirtualNo != ""){
                $("select[untouched='bank']").val(bankVirtualNo); // 입금은행
                $("input[untouched='depositor']").val(bankInName); // 입금자
                // 입금 예정일이 없을 경우 7일 뒤로 기본 설정
                if(bankExpirationDate == null || bankExpirationDate == ""){
                    var optionLength = $("select[untouched='expiration'] option").length-1;
                    var optionValue = $("select[untouched='expiration'] option:eq("+optionLength+")")[0].value;
                    $("select[untouched='expiration']").val(optionValue);  // 입금예정일
                } else {
                    $("select[untouched='expiration']").val(bankExpirationDate);  // 입금예정일
                }
            }
            // 쿠폰 Key값 세팅
            var couponList = '${payInfo.buyCouponInfoList}'.slice(0,-1).substring(1);
            if(couponList != null && couponList != undefined && couponList != ""){
                Order.setCouponDiscountAmount(JSON.parse(couponList), [], []);
            }
            // 포인트 값
            var pointVal = "${payInfo.pointAmount}";
            Order.pointUsed(pointVal);
            // 배송비 쿠폰 세팅
            var usCoupons = '${payInfo.usCoupons}';
            if(usCoupons != null && usCoupons != undefined && usCoupons != "" && usCoupons != "[{}]"){
                var ucList = JSON.parse(usCoupons);
                var itemCnt = $(".shipping_coupon").length;
                for(var i = 0; i < itemCnt; i++){
                    if($(".shipping_coupon input[type='checkbox']")[i] != null){
                        var checkName = $(".shipping_coupon input[type='checkbox']")[i].name;
                        for(var j = 0; j < ucList.length; j++){
                            var key = Object.keys(ucList[j])[0];
                            // Key가 동일하며 플래그가 Y인 경우 해당 박스 체킹
                            if(checkName.indexOf(key) > 0){
                                if("Y"==ucList[j][key]){
                                    $($(".shipping_coupon input[type='checkbox']")[i]).trigger("click");
                                }
                            }
                        }
                    }
                }
            }
            // 우편번호 체크 - 제주도서산간
            Order.changeReceiverZipcode(zipcode, 0);
            Order.setShippingAmount(zipcode);
            Order.setAmountText();
            // 가상계좌 및 무통장입금의 경우 현금영수증 세팅
            $("input:radio[name='cashbill.cashbillType']:input[value='${payInfo.cashbillType}']").attr("checked",true);

            var cashType = $("input:radio[name='cashbill.cashbillType']:checked").val();
            var cashNum1 = "${payInfo.cashbillNumber1}";
            var cashNum2 = "${payInfo.cashbillNumber2}";
            var cashNum3 = "${payInfo.cashbillNumber3}";

            // 사업자 등록번호
            if(cashType == "BUSINESS"){
                $("input[id='cashbill.businessNumber1']").val(cashNum1);
                $("input[id='cashbill.businessNumber2']").val(cashNum2);
                $("input[id='cashbill.businessNumber3']").val(cashNum3);
            } else if(cashType == "PERSONAL"){
                // 개인소득공제 휴대폰번호
                $("input[id='cashbill.cashbillPhone1']").val(cashNum1);
                $("input[id='cashbill.cashbillPhone2']").val(cashNum2);
                $("input[id='cashbill.cashbillPhone3']").val(cashNum3);
            }
            // 약관 동의
            $("input[id='agree_check']:checkbox").attr("checked",true);

            // 결제하기 서브밋
            $("#payment-btn").trigger('click');
        }

        function changeDeliveryTxt(textVal, receiverIndex) {
            var absentText = $('#absent-txt-'+receiverIndex).find('input');

            if(textVal != '직접 입력') {
                absentText.val(textVal).attr('readonly', true).css('background', '#efefef');
            } else {
                absentText.val('').attr('readonly', false).css('background', '#fff');
            }
        }


        // 가입 후 최초 주문시, 입력한 주소지로 자동 설정 2019.01.04 한미화
        $('input[name="receivers[0].receiveAddressDetail"]').on('change', function () {
            updateAddress();
        });
        $('input[name="receivers[0].receiveAddress"]').on('change', function () {
            updateAddress();
        });
        $('input[name="receivers[0].receiveAddressDetail"]').on('change', function () {
            updateAddress();
        });
        function updateAddress() {
            if(isInitAddress) {
                $('.order_address input[name="buyer.zipcode"]').val($('input[name="receivers[0].receiveZipcode"]').val());
                $('.order_address input[name="buyer.newZipcode"]').val($('input[name="receivers[0].receiveNewZipcode"]').val());
                $('.order_address input[name="buyer.address"]').val($('input[name="receivers[0].receiveAddress"]').val());
                $('.order_address input[name="buyer.addressDetail"]').val($('input[name="receivers[0].receiveAddressDetail"]').val());
            }
        }

    </script>
</page:javascript>