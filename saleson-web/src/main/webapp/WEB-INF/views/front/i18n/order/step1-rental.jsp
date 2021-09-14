<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="kakaopay"	tagdir="/WEB-INF/tags/pg/kakaopay" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>


<div class="inner">
<div class="location_area">
    <!--jsp:include page="/WEB-INF/views/layouts/front/saleson_all_categories.jsp" /-->
    <div class="breadcrumbs">
        <a href="/" class="home"><span class="hide">home</span></a>
        <span>장바구니</span>
    </div>
</div><!-- // location_area E -->

<div class="content_title">
    <h2 class="title">주문/결제</h2>
</div>
<div class="order_step">
    <ul>
        <li class="off"><span class="number">01</span> 장바구니</li>
        <li class="on"><span class="number">02</span> 주문결제</li>
        <li><span class="number">03</span> 주문완료</li>
    </ul>
</div>

<h3 class="sub_title mt30">주문상품</h3>
<form:form modelAttribute="buy" name="buy" action="/order/pay" method="post">

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

    <!-- rentalpay용 히든값 -->
    <form:hidden path="buyRentalPay" />
    <form:hidden path="rentalTotAmt" />
    <form:hidden path="rentalMonthAmt" />
    <form:hidden path="rentalPartnershipAmt" />
    <form:hidden path="rentalPer" />
    <form:hidden path="buyPayments['rentalpay'].amount" class="_number op-order-payAmounts op-default-payment"
                 paymentType="rentalpay" value="${buy.defaultPaymentType == 'rentalpay' ? buy.orderPrice.orderPayAmount : 0 }" />


    <!-- 회원 정보 -->
    <c:if test="${requestContext.userLogin == true}">
        <c:if test="${ not empty buy.defaultUserDelivery }">
            <div id="defaultDeliveryInputArea">
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
                    <%-- <form:hidden path="defaultUserDelivery.companyName" /> --%>
                <form:hidden path="defaultUserDelivery.sido" />
                <form:hidden path="defaultUserDelivery.sigungu" />
                <form:hidden path="defaultUserDelivery.eupmyeondong" />
                <form:hidden path="defaultUserDelivery.address" />
                <form:hidden path="defaultUserDelivery.addressDetail" />
            </div>
        </c:if>
    </c:if>

    <div class="board_wrap">
        <table cellpadding="0" cellspacing="0" class="mypage_list mt10 op-order-item-table">
            <caption>주문상품</caption>
            <colgroup>
                <col style="width:auto;">
                <col style="width:72px;">
                <col style="width:110px;">
                <col style="width:110px;">
                <col style="width:110px;">
                <col style="width:110px;">
            </colgroup>
            <thead>
            <tr>
                <th scope="col" class="none_left">상품명/옵션정보</th>
                <th scope="col">수량</th>
                <th scope="col">총 렌탈료</th>
                <th scope="col">렌탈 기간</th>
                <th scope="col">월 렌탈료</th>
                <th scope="col">제휴카드 렌탈료</th>
                <th scope="col">배송정보</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="viewTarget" value="order" scope="request" />
            <c:set var="cashDiscountFlag" value="N" />
            <c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
                <c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
                    <c:set var="viewCount" value="${ viewCount + 1 }" />
                    <c:set var="singleShipping" value="${shipping.singleShipping}"/>
                    <c:set var="shipping" value="${shipping}" scope="request" />
                    <c:set var="rowspan" value="1" scope="request" />
                    <c:if test="${singleShipping == false}">
                        <c:set var="rowspan" value="${ fn:length(shipping.buyItems) }" scope="request" />
                        <c:set var="rentalItemName" value="notSingle"/>
                    </c:if>
                    <c:if test="${singleShipping == true}">
                        <c:set var="rentalItemName" value="single"/>
                    </c:if>
                    <c:choose>
                        <c:when test="${singleShipping == true}">
                            <c:set var="buyItem" value="${shipping.buyItem}" scope="request" />
                            <c:set var="firstItem" value="true" scope="request" />
                            <c:if test="${buyItem.cashDiscountFlag == 'Y'}">
                                <c:set var="cashDiscountFlag" value="Y" />
                            </c:if>

                            <jsp:include page="../include/buy-rental-item-list.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="itemIndex">
                                <c:set var="buyItem" value="${buyItem}" scope="request" />
                                <c:set var="firstItem" value="${itemIndex.first}" scope="request" />
                                <c:if test="${buyItem.cashDiscountFlag == 'Y'}">
                                    <c:set var="cashDiscountFlag" value="Y" />
                                </c:if>

                                <jsp:include page="../include/buy-rental-item-list.jsp"/>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </c:forEach>
            </tbody>
        </table><!--//esthe-table E-->
            <%--            <div class="boadr_total">--%>
            <%--                <div>--%>
            <%--                    총 상품합계 금액  <label class="op-total-item-sale-Amount-text">${op:numberFormat(buy.orderPrice.totalItemSaleAmount)}</label>원  +  배송비  <label class="op-total-delivery-charge-text">${op:numberFormat(buy.orderPrice.totalShippingAmount)}</label>원  = <span class="delivery_total">결제예정금액 <span class="op-order-pay-amount-text">${ op:numberFormat(buy.orderPrice.orderPayAmount) }</span>원</span>--%>

            <%--                    <br />--%>
            <%--                    상품금액 <label class="op-total-item-price-text">${op:numberFormat(buy.orderPrice.totalItemPrice)}</label>원--%>
            <%--                    - 할인금액 <label class="op-total-discount-amount-text">${op:numberFormat(buy.orderPrice.totalDiscountAmount)}</label>원--%>
            <%--                    + 배송비 <label class="op-total-delivery-charge-text">${op:numberFormat(buy.orderPrice.totalShippingAmount)}</label>원--%>
            <%--                    = <span class="delivery_total">결제예정금액 <span class="op-order-pay-amount-text">${ op:numberFormat(buy.orderPrice.orderPayAmount) }</span>원</span>--%>

            <%--                </div>--%>
            <%--            </div><!--//boadr_total E-->--%>
    </div>	<!-- // board_wrap E -->


    <%--        <div class="btn_wrap pt10">--%>
    <%--            <div class="btn_left">--%>
    <%--                <button type="button" class="btn btn-default btn-m" title="장바구니 돌아가기" onclick="location.href='/cart';">장바구니 돌아가기</button>--%>
    <%--            </div> <!-- // btn_left E -->--%>
    <%--        </div>--%>

    <c:choose>
        <c:when test="${requestContext.userLogin == true}">
            <h3 class="sub_title mt30">주문자 정보  <span>표시가 있는 부분은 필수 입력사항입니다.</span></h3>
            <div class="board_wrap" id="op-buyer-input-area">
                <table cellpadding="0" cellspacing="0" class="board-write">
                    <caption>주문자 정보 </caption>
                    <colgroup>
                        <col style="width:160px;">
                        <col style="width:auto;">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row">주문자명 <span class="necessary"></span></th>
                        <td>
                            <div class="input_wrap col-w-7">
                                <form:input path="buyer.userName" cssClass="required" maxlength="50" title="주문자명" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" valign="top">배송지 주소 <span class="necessary"></span></th>
                        <td>
                            <div>
                                <form:hidden path="buyer.zipcode" />
                                <form:hidden path="buyer.sido" />
                                <form:hidden path="buyer.sigungu" />
                                <form:hidden path="buyer.eupmyeondong" />

                                <div class="input_wrap col-w-10">
                                    <form:input path="buyer.newZipcode" readonly="true" title="우편번호" cssClass="required" maxlength="5" />
                                </div>
                                <div class="input_wrap"><button type="button" class="btn btn-ms btn-default" onclick="openDaumPostcode('buyer')" title="검색">검색</button></div>&nbsp;
                                <span class="address_basic"><input type="checkbox" id="defaultBuyerCheck" name="defaultBuyerCheck" value="1"> <label for="defaultBuyerCheck">이 주소를 기본으로 설정함</label></span>
                                <div class="input_wrap mt8 col-w-0">
                                    <form:input path="buyer.address" cssClass="required full" readonly="true" maxlength="100" title="주소" htmlEscape="false"/>
                                </div>
                                <div class="input_wrap mt8 col-w-0">
                                    <form:input path="buyer.addressDetail" cssClass="full" maxlength="100" title="상세주소" htmlEscape="false"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">휴대폰번호 <span class="necessary"></span></th>
                        <td>
                            <div class="hp_area">
                                <div class="input_wrap col-w-10">
                                    <form:select path="buyer.mobile1" title="휴대폰번호" cssClass="form-control required">
                                        <form:option value="" label="선택"></form:option>
                                        <form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
                                    </form:select>
                                </div>
                                <span class="connection"> - </span>
                                <div class="input_wrap col-w-10">
                                    <form:input path="buyer.mobile2" title="휴대전화" cssClass="_number required full" maxlength="4" />
                                </div>
                                <span class="connection"> - </span>
                                <div class="input_wrap col-w-10">
                                    <form:input path="buyer.mobile3" title="휴대전화" cssClass="_number required full" maxlength="4" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">전화번호</th>
                        <td>
                            <div class="hp_area">
                                <div class="input_wrap col-w-10">
                                    <form:select path="buyer.phone1" cssClass="form-control" title="전화번호">
                                        <form:option value="" label="선택"></form:option>
                                        <form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
                                    </form:select>
                                </div>
                                <span class="connection"> - </span>
                                <div class="input_wrap col-w-10">
                                    <form:input path="buyer.phone2" title="전화번호" cssClass="_number full" maxlength="4" />
                                </div>
                                <span class="connection"> - </span>
                                <div class="input_wrap col-w-10">
                                    <form:input path="buyer.phone3" title="전화번호" cssClass="_number full" maxlength="4" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">이메일 <span class="necessary"></span></th>
                        <td>
                            <form:hidden path="buyer.email" />
                            <div class="hp_area">
                                <div class="input_wrap col-w-9">
                                    <form:input path="buyer.email1" title="이메일" class="full required _first_email" maxlength="40" />
                                </div>
                                <span class="connection">@</span>
                                <div class="input_wrap col-w-9">
                                    <select id="buyer.email2Etc" name="buyer.email2Etc">
                                        <option value="">직접입력</option>
                                        <c:forEach items="${op:getCodeInfoList('EMAIL')}" var="email">
                                            <option value="${email.key.id}" ${op:selected(buy.buyer.email2, email.key.id)}>${email.label}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="input_wrap col-w-8">
                                    <form:input path="buyer.email2" title="이메일주소 직접입력" class="full required _last_email" maxlength="40" placeholder="직접입력" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table><!--//view E-->
            </div> <!-- // board_wrap -->
        </c:when>
        <c:otherwise>
            <h3 class="sub_title mt30">비회원 주문자 정보  <span>표시가 있는 부분은 필수 입력사항입니다.</span></h3>
            <div class="board_wrap">
                <table cellpadding="0" cellspacing="0" class="board-write">
                    <caption>비회원 주문자 정보</caption>
                    <colgroup>
                        <col style="width:160px;">
                        <col style="width:auto;">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row">주문자명 <span class="necessary"></span></th>
                        <td>
                            <div class="input_wrap col-w-7">
                                <form:input path="buyer.userName" title="주문자명" class="required" maxlength="50" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">연락처 <span class="necessary"></span></th>
                        <td>
                            <div class="hp_area">
                                <div class="input_wrap col-w-10">
                                    <form:select path="buyer.mobile1" title="휴대폰번호" cssClass="form-control required">
                                        <form:option value="" label="선택"></form:option>
                                        <form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
                                    </form:select>
                                </div>
                                <span class="connection"> - </span>
                                <div class="input_wrap col-w-10">
                                    <form:input path="buyer.mobile2" title="휴대전화" cssClass="_number required full" maxlength="4" />
                                </div>
                                <span class="connection"> - </span>
                                <div class="input_wrap col-w-10">
                                    <form:input path="buyer.mobile3" title="휴대전화" cssClass="_number required full" maxlength="4" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">이메일 <span class="necessary"></span></th>
                        <td>
                            <form:hidden path="buyer.email" />
                            <div class="hp_area">
                                <div class="input_wrap col-w-9">
                                    <form:input path="buyer.email1" title="이메일" cssClass="full required" maxlength="40" />
                                </div>
                                <span class="connection">@</span>
                                <div class="input_wrap col-w-9">
                                    <select name="buyer.email2Etc">
                                        <option value="">직접입력</option>
                                        <c:forEach items="${op:getCodeInfoList('EMAIL')}" var="email">
                                            <option value="${email.key.id}" ${op:selected(buy.buyer.email2, email.key.id)}>${email.label}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="input_wrap col-w-8">
                                    <form:input path="buyer.email2" title="이메일주소 직접입력" cssClass="full required" maxlength="40" placeholder="직접입력" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table><!--//view E-->
            </div> <!-- // board_wrap -->
        </c:otherwise>
    </c:choose>

    <h3 class="sub_title mt30">배송지
        <span>표시가 있는 부분은 필수 입력사항입니다.</span>
        <c:if test="${buy.additionItem == false}">
            <%--            <c:if test="${buy.multipleDeliveryCount > 1}">--%>
            <%--                <div class="many_delivery">--%>
            <%--                    <p>최대 ${op:numberFormat(buy.maxMultipleDelivery)}곳까지 가능</p>--%>
            <%--                    <select id="multiple-delivery-set-count">--%>
            <%--                        <c:forEach begin="2" end="${buy.multipleDeliveryCount > buy.maxMultipleDelivery ? buy.maxMultipleDelivery : buy.multipleDeliveryCount}" step="1" var="multipleDeliveryValue">--%>
            <%--                            <option value="${multipleDeliveryValue}">${multipleDeliveryValue}</option>--%>
            <%--                        </c:forEach>--%>
            <%--                    </select>--%>
            <%--                    <button type="button" class="btn btn-m btn-default hide" id="op-cancel-multiple-delivery" onclick="Order.cancelMultipleDelivery()">한곳으로 보내기</button>--%>
            <%--                    <div class="input_wrap"><button type="button" class="btn btn-m btn-default" onclick="Order.multipleDelivery()" title="복수배송지 선택">복수배송지 선택</button></div>--%>
            <%--                </div>--%>
            <%--            </c:if>--%>
        </c:if>
    </h3>
    <div class="board_wrap">
        <div class="op-receive-input-area">
            <c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
                <c:forEach items="${receiver.buyQuantitys}" var="buyQuantity" varStatus="buyQuantityIndex">
                    <form:hidden path="receivers[${receiverIndex.index}].buyQuantitys[${buyQuantityIndex.index}].itemSequence" value="${buyQuantity.itemSequence}" />
                    <form:hidden path="receivers[${receiverIndex.index}].buyQuantitys[${buyQuantityIndex.index}].quantity" value="${buyQuantity.quantity}" />
                </c:forEach>

                <c:choose>
                    <c:when test="${fn:length(buy.receivers) > 1}">

                    </c:when>
                    <c:otherwise>

                        <table cellpadding="0" cellspacing="0" class="board-write" id="receiveInputArea-${receiverIndex.index}">
                            <caption>배송지</caption>
                            <colgroup>
                                <col style="width:160px;">
                                <col style="width:auto;">
                            </colgroup>
                            <tbody>
                            <c:if test="${requestContext.userLogin == true}">
                                <tr>
                                    <th scope="row">배송지 선택</th>
                                    <td>
                                        <div class="input_wrap col-w-3">
                                            <c:if test="${ not empty buy.defaultUserDelivery }">
                                                <input type="radio" id="default-${receiverIndex.index}" name="infoCopy" value="default"> <label for="default-${receiverIndex.index}">기본 배송지</label>&nbsp;&nbsp;
                                            </c:if>
                                            <input type="radio" id="new-${receiverIndex.index}" name="infoCopy" value="clear"> <label for="new-${receiverIndex.index}">새로운 배송지</label>&nbsp;&nbsp;
                                            <input type="radio" id="same-${receiverIndex.index}" name="infoCopy" value="copy"> <label for="same-${receiverIndex.index}">주문자정보와 동일</label>&nbsp;&nbsp;
                                                <%--input type="radio" id="a4" name="radio_btn"> <label for="a4">최근배송지</label>&nbsp;&nbsp;--%>
                                            <button type="button" class="btn btn-ms btn-default" title="배송지 목록" onclick="Common.popup('/order/delivery?target=order&receiverIndex=${receiverIndex.index}', 'delivery_list', 898, 660, 1);">배송지 목록</button>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <th scope="row">받으시는 분 <span class="necessary"></span></th>
                                <td>
                                    <div class="input_wrap col-w-7">
                                        <form:input path="receivers[${receiverIndex.index}].receiveName" title="받으시는 분" class="required" maxlength="50" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row" valign="top">배송지 주소 <span class="necessary"></span></th>
                                <td>
                                    <div>
                                        <form:hidden path="receivers[${receiverIndex.index}].receiveSido" />
                                        <form:hidden path="receivers[${receiverIndex.index}].receiveSigungu" />
                                        <form:hidden path="receivers[${receiverIndex.index}].receiveEupmyeondong" />
                                        <form:hidden path="receivers[${receiverIndex.index}].receiveZipcode" />
                                        <div class="input_wrap col-w-10">
                                            <form:input path="receivers[${receiverIndex.index}].receiveNewZipcode" title="우편번호" maxlength="5" class="required" readonly="true" />
                                        </div>
                                        <div class="input_wrap"><button type="button" class="btn btn-ms btn-default" title="검색" onclick="openDaumPostcode('receive', ${receiverIndex.index})">검색</button></div>
                                        <div class="input_wrap mt8 col-w-0">
                                            <form:input path="receivers[${receiverIndex.index}].receiveAddress" title="주소" class="required full" maxlength="100" readonly="true" htmlEscape="false"/>
                                        </div>
                                        <div class="input_wrap mt8 col-w-0">
                                            <form:input path="receivers[${receiverIndex.index}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" htmlEscape="false"/>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">휴대폰번호 <span class="necessary"></span></th>
                                <td>
                                    <div class="hp_area">
                                        <div class="input_wrap col-w-10">
                                            <form:select path="receivers[${receiverIndex.index}].receiveMobile1" title="휴대전화" cssClass="_number required form-control">
                                                <form:option value="" label="선택"></form:option>
                                                <form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
                                            </form:select>
                                        </div>
                                        <span class="connection"> - </span>
                                        <div class="input_wrap col-w-10">
                                            <form:input path="receivers[${receiverIndex.index}].receiveMobile2" title="휴대전화" class="_number required full" maxlength="4" />
                                        </div>
                                        <span class="connection"> - </span>
                                        <div class="input_wrap col-w-10">
                                            <form:input path="receivers[${receiverIndex.index}].receiveMobile3" title="휴대전화" class="_number required full" maxlength="4" />
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">전화번호 </th>
                                <td>
                                    <div class="hp_area">
                                        <div class="input_wrap col-w-10">
                                            <form:select path="receivers[${receiverIndex.index}].receivePhone1" cssClass="form-control" title="전화번호">
                                                <form:option value="" label="선택"></form:option>
                                                <form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
                                            </form:select>
                                        </div>
                                        <span class="connection"> - </span>
                                        <div class="input_wrap col-w-10">
                                            <form:input path="receivers[${receiverIndex.index}].receivePhone2" title="전화번호" class="_number full" maxlength="4" />
                                        </div>
                                        <span class="connection"> - </span>
                                        <div class="input_wrap col-w-10">
                                            <form:input path="receivers[${receiverIndex.index}].receivePhone3" title="전화번호" class="_number full" maxlength="4" />
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row">배송시 요구사항</th>
                                <td>
                                    <div class="input_wrap col-w-0">
                                        <form:input path="receivers[${receiverIndex.index}].content" title="배송시 요구사항" class="_filter" placeholder="ex) 부재시 경비실에 맡겨주세요." />
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table><!--//view E-->
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div> <!-- // board_wrap -->

    <!--// 배송방법 추가 S -->
    <%--        <h3 class="sub_title mt30">배송방법</h3>--%>
    <%--        <div class="board_wrap">--%>
    <%--            <table cellpadding="0" cellspacing="0" class="board-write">--%>
    <%--                <caption>배송방법</caption>--%>
    <%--                <colgroup>--%>
    <%--                    <col style="width:160px;">--%>
    <%--                    <col style="width:auto;">--%>
    <%--                </colgroup>--%>
    <%--                <tbody>--%>
    <%--                <tr>--%>
    <%--                    <th scope="row">배송방법</th>--%>
    <%--                    <td>--%>
    <%--                        <div class="input_wrap col-w-3">--%>
    <%--                            <input type="radio" id="normal" name="deliveryMethodType" value="NORMAL" checked="checked"> <label for="normal">일반택배</label>&nbsp;&nbsp;--%>
    <%--                            <input type="radio" id="quick" name="deliveryMethodType" value="QUICK"> <label for="quick">퀵서비스</label>&nbsp;&nbsp;--%>
    <%--                            <input type="radio" id="pickUp" name="deliveryMethodType" value="PICK_UP"> <label for="pickUp">방문수령</label>&nbsp;&nbsp;--%>
    <%--                        </div>--%>
    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <th scope="row">배송비</th>--%>
    <%--                    <td class="op-quick-delivery-text"><b class="delv_price op-total-delivery-charge-text">${ op:numberFormat(buy.orderPrice.totalShippingAmount) }</b>원</td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <th scope="row" valign="top">배송 주의사항</th>--%>
    <%--                    <td>--%>
    <%--                        <div class="box_list_02 pt0">--%>
    <%--                            <ul>--%>
    <%--                                <li><span class="tit">택배</span>제작상품이기에 출고까지 1~2일 소요됩니다</li>--%>
    <%--                                <li><span class="tit">퀵서비스</span>평일 오전 11시까지 결제 후 전화주시면 당일발송 됩니다. 퀵서비스 비용은 착불이며, 지역에 따라 비용은 상이합니다.</li>--%>
    <%--                            </ul>--%>
    <%--                        </div>--%>
    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                </tbody>--%>
    <%--            </table><!--//view E-->--%>
    <%--        </div> <!-- // board_wrap -->--%>
    <!--// 배송방법 추가 E -->

    <%--        <c:if test="${requestContext.userLogin == true}">--%>
    <%--            <h3 class="sub_title mt30">할인혜택 사용</h3>--%>
    <%--            <div class="board_wrap">--%>
    <%--                <table cellpadding="0" cellspacing="0" class="board-write">--%>
    <%--                    <caption>할인혜택 사용</caption>--%>
    <%--                    <colgroup>--%>
    <%--                        <col style="width:160px;">--%>
    <%--                        <col style="width:auto;">--%>
    <%--                    </colgroup>--%>
    <%--                    <tbody>--%>
    <%--                    <c:if test="${useCoupon == true}">--%>
    <%--                        <tr>--%>
    <%--                            <th scope="row">쿠폰할인</th>--%>
    <%--                            <td>--%>
    <%--                                <div class="input_wrap col-w-9">--%>
    <%--                                    <input type="text" title="쿠폰 할인" class="full tar bold _number totalCouponDiscountAmountText" value="${ op:numberFormat(buy.orderPrice.totalCouponDiscountAmount) }" readonly="readonly" onclick="viewCoupon()">--%>
    <%--                                    <div class="op-coupon-hide-field-area hide hidden">--%>
    <%--                                        <c:if test="${ !empty buy.makeUseCouponKeys }">--%>
    <%--                                            <c:forEach items="${ buy.makeUseCouponKeys }" var="value">--%>
    <%--                                                <input type="hidden" name="useCouponKeys" value="${ value }" class="useCoupon" />--%>
    <%--                                            </c:forEach>--%>
    <%--                                        </c:if>--%>
    <%--                                    </div>--%>
    <%--                                </div> 원 &nbsp; &nbsp;--%>
    <%--                                <div class="input_wrap"><button type="button" class="btn btn-ms btn-default" title="쿠폰 조회 및 적용" onclick="viewCoupon()">쿠폰 조회 및 적용</button></div>--%>
    <%--                            </td>--%>
    <%--                        </tr>--%>
    <%--                    </c:if>--%>
    <%--                    <tr>--%>
    <%--                        <th scope="row">포인트 할인</th>--%>
    <%--                        <td>--%>
    <%--                            <div class="input_wrap col-w-9">--%>
    <%--                                <form:hidden path="buyPayments['point'].amount" />--%>
    <%--                                <input type="text" title="포인트 할인" class="tar bold _number op-total-point-discount-amount-text" value="${ op:numberFormat(order.orderPrice.totalPointDiscountAmount) }"--%>
    <%--                                       <c:if test="${ buy.retentionPoint == 0 }">disabled="disabled"</c:if> />--%>
    <%--                            </div> 원 &nbsp; &nbsp;--%>
    <%--                            <div class="input_wrap point_area">--%>
    <%--                                <input type="checkbox" class="check01" id="retentionPointUseAll"--%>
    <%--                                       <c:if test="${ buy.retentionPoint == 0 }">disabled="disabled"</c:if>> <label for="retentionPointUseAll">모두사용</label>--%>
    <%--                            </div><!--//point_area E-->--%>
    <%--                            <div class="input_wrap point_area"><p>(보유 포인트 : <strong class="color_23ade3">${op:numberFormat(buy.retentionPoint)}P</strong> /1P=1원)</p>  </div>--%>
    <%--                        </td>--%>
    <%--                    </tr>--%>
    <%--                    </tbody>--%>
    <%--                </table><!--//view E-->--%>
    <%--            </div> <!-- // board_wrap -->--%>
    <%--        </c:if>--%>

    <div class="order_pay">
    <div class="pgInputArea">
        <c:choose>
            <c:when test="${pgService == 'inicis' }">
                <inicis:inipay-input />
            </c:when>
            <c:when test="${pgService == 'lgdacom' }">
                <lgdacom:xpay-input />
            </c:when>
            <c:when test="${pgService == 'kspay' }">
                <kspay:kspay-input />
            </c:when>
            <c:when test="${pgService == 'kcp' }">
                <kcp:kcp-input />
            </c:when>
            <c:when test="${pgService == 'easypay' }">
                <easypay:easypay-input />
            </c:when>
            <c:when test="${pgService == 'nicepay' }">
                <nicepay:nicepay-input />
            </c:when>
        </c:choose>
    </div>
    <div class="order_pay term">
        <div class="type02">
            <h3 class="sub_title">최종 결제금액 확인</h3>
            <div class="last_pay">
                <div class="cont">
                    <div class="cont_inner">
                        <span>총 렌탈료</span>
                        <span class="color_4a4a4a">${op:numberFormat(buy.rentalTotAmt)}원</span>
                    </div>
                    <div class="cont_inner sale">
                        <p>
                            <span>렌탈기간</span>
                            <span class="color_23ade3">${buy.rentalPer} 개월</span>
                        </p>
                    </div>
                </div><!--// cont E-->
                <div class="pay_money">
                    <p>렌탈료</p>
                    <p class="total_price">
                        <span>월 </span>${ op:numberFormat(buy.rentalMonthAmt) }<span>원</span>
                    </p>
                </div><!--// pay_money E-->
                <div class="pay_money">
                    <p>제휴카드 렌탈료</p>
                    <p class="total_price">
                        <span>월 </span>${ op:numberFormat(buy.rentalPartnershipAmt) }<span>원</span>
                    </p>
                </div><!--// pay_money E-->
                <c:if test="${ buy.orderPrice.totalEarnPoint > 0 }">
                    <div class="point_will">
                        <div>
                            <ul>
                                <li>적립예정 포인트</li>
                                <li><span class="op-earn-point-text">${op:numberFormat(buy.orderPrice.totalEarnPoint)}P</span></li>
                            </ul>
                        </div>
                    </div><!--// point E-->
                </c:if>
                <div class="cont">
                    <p class="guide"> 렌탈 및 구매자 동의 안내 내용을 확인하였으며, 구매에 동의 하시겠습니까?</p>
                    <p class="agree"><input type="checkbox" id="agree"> <label for="agree">동의합니다.</label></p>
                </div>
            </div><!--//last_pay E-->
            <div class="btn_end">
                <button type="submit" id="payment-button" class="btn btn-success btn-w154" style="width: 315px" title="결제하기">
                        <span>
                            <img src="/content/images/icon/icon_pay.png" alt="결제하기">
                        </span>렌탈 신청서 작성
                </button>
                    <%--                    <button type="button" class="btn btn-default btn-w154" onclick="location.href='/cart'" title="취소하기">취소하기</button>--%>
                    <%--                    <div id="naver-payment-button" style="display: none;">--%>
                    <%--                        <button type="submit" class="npay_btn_pc btn btn-w154" id="naverPayBtn"></button>--%>
                    <%--                    </div>--%>
            </div>
        </div><!--//type02 E-->
        <div class="type03">
            <h3 class="sub_title">렌탈 및 구매자 동의 안내</h3>
            <div class="rental_info_wrap">
                <p>리뉴올PC 쇼핑몰의 렌탈 서비스는 공급업체인 “㈜비에스렌탈”에서 주관하며, 구매확정 후 상품에 관련된 모든 사항(반품 및 교환, A/S 등)을 직접 관리합니다. 또한, 배송 및 설치가 완료된 시점부터 렌탈이 시작됩니다.</p>

                <p class="info_tit">[렌탈 안내]</p>

                <p class="square_tit">■ 배송 안내</p>

                <p class="info">배송 관련 문의는 리뉴올PC 쇼핑몰(1544-2432)로 연락 부탁드립니다.</p>
                <p class="square_tit">■ 교환 및 반품 안내</p>

                <p class="info"><span>구매확정 전</span> 교환 및 반품 문의는 리뉴올PC 쇼핑몰(1544-2432)을 통해 가능하며, 사유에 따라 교환 및 반품이 거절될 수 있습니다.</p>
                <p class="info"><span>구매확정 후</span> 교환 및 반품 접수는 ㈜비에스렌탈(1588-6990)을 통해 가능하며, 사유에 따라 교환 및 반품이 거절될 수 있습니다.</p>
                <p class="circle_tit">• 구매확정 안내</p>

                <p class="info">배송완료 후 직접 구매확정이 가능합니다.</p>
                <p class="info">배송완료 후 구매확정을 하지 않았을 경우 7일이 경과하면 자동으로 구매확정 상태로 전환됩니다.</p>
                <p class="circle_tit">• 교환 및 반품 불가 사유</p>

                <p class="info">단순변심(상품설치 후, 택배상품 개봉 후)</p>
                <p class="info">제품을 설치 또는 사용하거나, 상품의 가치가 훼손된 경우</p>
                <p class="info">제품포장(박스)을 개봉/훼손 또는 상품을 개봉한 경우</p>
                <p class="info">상품 구성품 중 일부 누락 또는 파손(가전제품 부속품, 사은품 등)한 경우</p>
                <p class="info_tit">[구매자 동의 안내]</p>

                - 주문한 상품의 상품명, 렌탈가격, 배송정보를 확인하였으며, 구매에 동의합니다.(전자상거래법 제8조 2항)
            </div>
                <%--                <p class="check_agree">--%>
                <%--                    <input type="checkbox" id="rental_agree">--%>
                <%--                    <label for="rental_agree">위의 내용을 확인하였고, 동의합니다.</label>--%>
                <%--                </p>--%>
        </div>
    </div><!--//order_pay E-->
</form:form>
</div><!--// inner E-->

<div class="hidden hide op-default-multiple-receiver-view">
    <div id="op-receiver-{RECEIVER_INDEX}">
        <h4 class="title_type04 mt15">배송지{RECEIVER_VIEW_INDEX}</h4>
        <div class="board_wrap">
            <table cellpadding="0" cellspacing="0" class="board-write">
                <caption>복수배송지 등록</caption>
                <colgroup>
                    <col style="width:160px;">
                    <col style="width:auto;">
                    <col style="width:130px;">
                </colgroup>
                <tbody>
                <tr>
                    <th scope="row">받으시는 분</th>
                    <td colspan="2">
                        <div class="input_wrap col-w-7">
                            <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveName" title="받으시는 분" class="required" maxlength="50" />
                            {receiveName}
                        </div>
                    </td>
                </tr>
                <tr>
                    <th scope="row" valign="top">배송지 주소 <span class="necessary"></span></th>
                    <td colspan="2">
                        <div>
                            <div class="input_wrap">
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveNewZipcode" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveZipcode" title="우편번호" maxlength="7" class="required" readonly="true" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveSido" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveSigungu" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveEupmyeondong" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveAddress" title="주소" style="width:80%" class="required" maxlength="100" readonly="true" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" />
                                ({receiveZipcode}) {receiveAddress} {receiveAddressDetail}
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th scope="row">휴대폰번호 <span class="necessary"></span></th>
                    <td colspan="2">
                        <div class="hp_area">
                            <div class="input_wrap">
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile1" title="휴대전화" class="_number required" maxlength="4" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile2" title="휴대전화" class="_number required" maxlength="4" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile3" title="휴대전화" class="_number required" maxlength="4" />
                                {receiveMobile1}-{receiveMobile2}-{receiveMobile3}
                            </div>
                        </div><!-- // hp_area -->
                    </td>
                </tr>
                <tr>
                    <th scope="row">전화번호</th>
                    <td colspan="2">
                        <div class="hp_area">
                            <div class="input_wrap">
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receivePhone1" title="전화번호" class="_number" maxlength="4" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receivePhone2" title="전화번호" class="_number" maxlength="4" />
                                <input type="hidden" name="receivers[{RECEIVER_INDEX}].receivePhone3" title="전화번호" class="_number" maxlength="4" />
                                {receivePhone1}-{receivePhone2}-{receivePhone3}
                            </div>
                        </div><!-- // hp_area -->
                    </td>
                </tr>
                <tr>
                    <th scope="row">배송시 요구사항</th>
                    <td colspan="2">
                        <div class="input_wrap col-w-0">
                            <input type="text" name="receivers[{RECEIVER_INDEX}].content" title="배송시 요구사항" class="_filter full" placeholder="ex) 부재시 경비실에 맡겨주세요." />
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- rentalPay용 form -->
<form id="pgForm" name="pgForm">
    <input type="hidden" name="storeId" class="storeId"> <!-- 가맹점아이디 -->
    <input type="hidden" name="storeCode" class="storeCode"> <!-- 가맹점 코드 -->
    <input type="hidden" name="prodName" class="prodName"> <!-- 상품명 -->
    <input type="hidden" name="rentalPer" class="rentalPerData"> <!-- 렌탈기간 -->
    <input type="hidden" name="prodAmt" class="prodAmt"> <!-- 물대금액 -->
    <input type="hidden" name="addr" class="addr"> <!-- 주소 -->
    <input type="hidden" name="addrDtl" class="addrDtl"> <!-- 상세 주소 -->
    <input type="hidden" name="postcode" class="postcode"> <!-- 우편번호 -->
    <input type="hidden" name="prodColor" class="prodColor"> <!-- 색상 -->
    <input type="hidden" name="returnURL" class="returnURL"> <!-- 리다이렉트 주소 -->
    <input type="hidden" name="prodUrl" class="prodUrl"> <!-- 해당 상품의 이미지URL -->
</form>

<page:javascript>
    <c:choose>
        <c:when test="${pgService == 'inicis'}">
            <inicis:inipay-script />
        </c:when>
        <c:when test="${pgService == 'lgdacom' }">
            <lgdacom:xpay-script />
        </c:when>
        <c:when test="${pgService == 'kspay' }">
            <kspay:kspay-script />
        </c:when>
        <c:when test="${pgService == 'kcp' }">
            <kcp:kcp-script />
        </c:when>
        <c:when test="${pgService == 'easypay' }">
            <easypay:easypay-script />
        </c:when>
        <c:when test="${pgService == 'nicepay' }">
            <nicepay:nicepay-script />
        </c:when>
    </c:choose>

    <c:if test="${ not empty buy.buyPayments['kakaopay'] }">
        <kakaopay:kakaopay-script />
    </c:if>
    <!-- 다음 주소검색 -->
    <daum:address />

    <div class="iframe" style="display: none; position: fixed; width:100%" >
        <iframe name="orderWindow_iframe" frameborder="no" width="565" height="400" align="center" scrolling="no"></iframe>
    </div>

    <script type="text/javascript" src="/content/modules/op.order.js"></script>
    <script src="https://nsp.pay.naver.com/sdk/js/naverpay.min.js"></script>
    <script type="text/javascript" src="http://211.178.29.124:8082/resources/common/rentalPg.js"></script>
    <script type="text/javascript">
        //<![CDATA[
        var DaumConversionDctSv="type=P,orderID=,amount=";
        var DaumConversionAccountID="AhEsM4T6uIfTz.omUTDgpw00";
        if(typeof DaumConversionScriptLoaded=="undefined"&&location.protocol!="file:"){
            var DaumConversionScriptLoaded=true;
            document.write(unescape("%3Cscript%20type%3D%22text/javas"+"cript%22%20src%3D%22"+(location.protocol=="https:"?"https":"http")+"%3A//t1.daumcdn.net/cssjs/common/cts/vr200/dcts.js%22%3E%3C/script%3E"));
        }
        //]]>

        // 주문시 스크립트에서 사용할 데이터 초기화
        Order.init(${userData}, "${pgService}", '${shopConfig.minimumPaymentAmount}', '${shopConfig.pointUseMin}', '', '${shopConfig.pointUseRatio}');

        var couponPopup;
        $(function(){

            Order.setShippingAmount(); // 쿠폰 사용 체크 2017-04-25 yulsun.yoo

            if ($('select[name="buyer.email2Etc"]').find('option:selected').val() == '') {
                $('input[name="buyer.email2"]').show();
            } else {
                $('input[name="buyer.email2"]').hide();
            }

            $('select[name="buyer.email2Etc"]').on('change', function(){
                if ($(this).val() == '') {
                    $('input[name="buyer.email2"]').show();
                } else {
                    $('input[name="buyer.email2"]').hide();
                }

                $('input[name="buyer.email2"]').val($(this).val());
            });

            $('input[name="cashbill.cashbillType"]').on('click', function(e){
                if ($(this).val() == 'BUSINESS') {
                    $('div.cashbillType').hide();
                    $('div#cashbillType1').show();
                } else if ($(this).val() == 'PERSONAL') {
                    $('div.cashbillType').hide();
                    $('div#cashbillType2').show();
                } else {
                    $('div.cashbillType').hide();
                }
            });

            historyBackDataSet();

            $("#buy").validator({
                'requiredClass' : 'required',
                'submitHandler' : function() {
                    // 구매동의 확인 안내 alert 추가 2017-04-25_seungil.lee
                    if(!$("#agree").prop("checked")) {
                        alert("구매동의 안내를 확인 후 동의해주세요.");
                        return false;
                    }

                    if($("#agree02").val() != undefined && !$("#agree02").prop("checked")) {
                        alert("개인정보 제 3자 제공 및 수집·이용 안내를 확인 후 동의해주세요.");
                        return false;
                    }

                    var email = $('input[name="buyer.email1"]').val() + '@' + $('input[name="buyer.email2"]').val();
                    $('input[name="buyer.email"]').val(email);

                    $('#buy').attr('action', '/order/pay');
                    $('#buy').attr('target', '_self');


                    var rentalTotAmt = $('input[name="rentalTotAmt"]').val();
                    if (rentalTotAmt < 0) {
                        alert('결제 금액을 확인하십시오. 결제 요청액 : ' + rentalTotAmt);
                        return false;
                    }


                    var isSuccess = false;
                    var savePaymentType = [];
                    alert('통과');
                    $.post('/order/save-rental', $("#buy").serialize(), function(response){
                        Common.responseHandler(response, function(response) {
                            alert('아작스 통과');
                            isSuccess = true;
                            savePaymentType = response.data.savePaymentType;
                            alert(response);
                            alert(savePaymentType);
                            $('input[name="orderCode"]').val(response.data.orderCode);

                            // 렌탈페이 제공폼에 데이터 넣기
                            var rentalPayShopId = '${op:property("rentalpay.seller.id")}';
                            var rentalPayShopCode = '${op:property("rentalpay.seller.code")}';
                            var singleShippingVal = '${rentalItemName}';
                            if (singleShippingVal == 'single') {
                                itemNameForRental = '${buy.receivers[0].itemGroups[0].buyItem.itemName}_'+ $('input[name="orderCode"]').val();
                            } else {
                                itemNameForRental = '${buy.receivers[0].itemGroups[0].buyItems[0].itemName}_'+ $('input[name="orderCode"]').val();
                            }
                            var siteUrl = '${op:property("saleson.url.shoppingmall")}';
                            $('.storeId').val(rentalPayShopId);     <!-- 가맹점아이디 -->
                            $('.storeCode').val(rentalPayShopCode);   <!-- 가맹점 코드 -->
                            $('.prodName').val(itemNameForRental);    <!-- 상품명 -->
                            $('.rentalPerData').val($('#rentalPer').val());   <!-- 렌탈기간 -->
                            $('.prodAmt').val($('input[name="orderPrice.totalItemSaleAmount"]').val());     <!-- 물대금액 -->
                            $('.addr').val($('input[name="receivers[0].receiveAddress"]').val());        <!-- 주소 -->
                            $('.addrDtl').val($('input[name="receivers[0].receiveAddressDetail"]').val());     <!-- 상세 주소 -->
                            $('.postcode').val($('input[name="receivers[0].receiveNewZipcode"]').val());    <!-- 우편번호 -->
                            $('.prodColor').val('');   <!-- 색상 -->
                            $('.returnURL').val(siteUrl + '/order/pay');   <!-- 리다이렉트 주소 -->
                            $('.prodUrl').val(siteUrl + $('#step1ItemImage').attr("src"));     <!-- 해당 상품의 이미지URL -->
                            <!-- 해당 상품의 이미지URL $('#step1ItemImage').attr("src")-->
                            console.log( 'ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ');
                            console.log( $('.storeId').val());
                            console.log( $('.storeCode').val());
                            console.log( $('.prodName').val());
                            console.log( $('.rentalPerData').val());
                            console.log( $('.prodAmt').val());
                            console.log( $('.addr').val());
                            console.log( $('.addrDtl').val());
                            console.log( $('.postcode').val());
                            console.log( $('.prodColor').val());
                            console.log( $('.returnURL').val());
                            console.log( $('.prodUrl').val());

                            openRentalPg('stg', 'pgForm');

                            alert('통과2');
                            return false;

                        }, function(response){
                            alert(response.errorMessage);
                        });
                    });

                    if (!isSuccess) {
                        return false;
                    }

                    if (Order.getApprovalType(savePaymentType, 'kakaopay')) {
                        //Common.loading.show();
                        kakaopay();
                        return false;

                    } else if (Order.getApprovalType(savePaymentType, 'payco')) {
                        //Common.loading.show();
                        return false;
                    } else if (Order.getApprovalType(savePaymentType, 'card') || Order.getApprovalType(savePaymentType, 'vbank') || Order.getApprovalType(savePaymentType, 'realtimebank')
                        || Order.getApprovalType(savePaymentType, 'hp')
                        || (Order.pgType == 'inicis' && Order.getApprovalType(savePaymentType, 'escrow'))) {

                        if (Order.pgType == 'inicis') {
                            var iniPayType = 'etc';
                            if (Order.getApprovalType(savePaymentType, 'escrow')) {
                                iniPayType = 'escrow';
                            }

                            if (iniPayStart(iniPayType) == false) {
                                return false;
                            }

                            if ("${op:property('pg.inipay.web.type')}" == 'webStandard') {
                                return false;
                            }

                        } else if (Order.pgType == 'lgdacom') {
                            if (launchCrossPlatform()) {

                            }

                            return false;
                        } else if (Order.pgType == 'kspay') {
                            if (ksPayStart() == false) {
                                return false;
                            }

                            return false;
                        } else if (Order.pgType == 'kcp') {
                            if(document.buy.payType.value == 'card'){
                                kcpLaunchCrossPlatform();
                            } else {
                                kcpLaunchWebStandard();
                            }

                            return false;
                        } else if (Order.pgType == 'easypay') {
                            if (easyPayLaunch() == false) {
                                return false;
                            }

                            return false;
                        } else if (Order.pgType == 'nicepay') {
                            nicepayStart();

                            $('#payType-kakaopay-input input[name="GoodsNameKakao"]').attr("name", "GoodsName");
                            $('#payType-kakaopay-input input[name="BuyerNameKakao"]').attr("name", "BuyerName");
                            return false;
                        }
                    } else if (Order.getApprovalType(savePaymentType, 'naverpay')) {
                        Common.loading.show();
                        return false;
                    } else if (Order.getApprovalType(savePaymentType, 'rentalpay')) {
                        //Common.loading.show();
                        //kakaopay();
                        return false;
                    }
                }
            });

            $('input.op-input-shipping-coupon-used').on('change', function() {
                if (Order.buy.shippingCoupon <= Order.useShippingCoupon) {
                    $(this).prop('checked', false);
                }

                Order.setShippingAmount();
            });

            $('input[name=deliveryMethodType]').on('change', function() {

                // 복수배송지가 설정되어있을 경우 한곳으로 보내기 처리 (퀵, 방문수령)
                if ($(this).val() != 'NORMAL' && $('.op-receive-input-area').children('div').length > 1) {

                    if (confirm('일반택배가 아닐 경우 복수배송지가 초기화됩니다.\n초기화 하시겠습니까?')) {
                        Order.cancelMultipleDelivery();
                    } else {
                        $('input[name=deliveryMethodType]').eq(0).prop('checked', true);
                        return false;
                    }
                }

                // 포인트 적용 후 배송방법 변경 시 결제 금액이 마이너스가 나오는 경우가 있음
                $('input#retentionPointUseAll').prop('checked', false);
                $('.op-total-point-discount-amount-text').val(0);
                Order.pointUsed(0);

                Order.setShippingAmount();
            });

            // 포인트 사용 - 직접 입력
            $('input.op-total-point-discount-amount-text').on('focusout', function(){
                $('input#retentionPointUseAll').prop('checked', false);

                var usePoint = $(this).val().replace(",", '');
                Order.pointUsed(usePoint);

            });

            // 포인트 사용 - 전체 사용
            $('input#retentionPointUseAll').on('click', function(){

                Order.pointUsedAll($(this));

            });

            // 받으시는분 복사
            $('body').on('click', 'input[name="infoCopy"]', function() {
                var index = $(this).attr('id').split('-')[1];

                if ($(this).val() == 'copy') {
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
                        } else if ($('select[name="' + name + '"]').size() == 1) {
                            $('select[name="' + name + '"]').val($(this).val());
                        }
                    });
                } else if($(this).val() == 'clear') {

                    $('input, select', '#receiveInputArea-'+index).not('input[name="infoCopy"], input[name=saveDeliveryFlag]').val("");
                    $('input[name=saveDeliveryFlag]').prop('checked', false);

                } else if ($(this).val() == 'default') {
                    $.each($('input, select', $('#defaultDeliveryInputArea')), function() {
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

                if (this.id == 'payType-naverpay') {
                    document.getElementById('payment-button').style = 'display:none';
                    document.getElementById('naver-payment-button').style = 'display:inline-block';
                } else {
                    document.getElementById('naver-payment-button').style = 'display:none';
                    document.getElementById('payment-button').style = 'display:inline-block';
                }

                if (this.id == 'payType-naverpayCard') {
                    $(".card_title").text("네이버페이 결제");
                } else if (this.id == 'payType-card') {
                    $(".card_title").text("신용카드 결제");
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

                $('div.payType-input').hide();
                $('input.op-order-payAmounts').removeClass('op-default-payment');
                $.each($('input[name="payType"]:checked'), function(i) {
                    if (i == 0) {
                        $('input.op-order-payAmounts', $('#payType-' + $(this).val() + '-input')).addClass('op-default-payment');
                    }

                    $("#cashbill_0").prop("checked", true);

                    $('#payType-' + $(this).val() + '-input').show();
                    $('.payType-' + $(this).val() + '-input').show();
                });

                Order.setOrderPayAmountClear();
                // cashReceiptSet($(this).val());
            });

            // 2017.05.25 Jun-Eu Son loading-dimmed 처리(Kspay결제모듈 사용시)
            if('${pgService}' == 'kspay') {
                $('div').on('focusin', function(){
                    if(top.op && top.op.closed)
                        Common.loading.hide();
                });

                $('div#loading-dimmed').on('click',function(){
                    if(top.op.closed)
                        Common.loading.hide();
                });
            }
            //2017.05.25 Jun-Eu Son loading-dimmed 처리 End

        });

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

        // 쿠폰 적용
        function viewCoupon() {
            couponPopup = Common.popup('/order/coupon', 'couponView', 900, 700, 1);
            $('#buy').attr('action', '/order/coupon');
            $('#buy').attr('target', 'couponView');
            $('#buy').submit();
            Common.loading.hide();
        }

        function uppercase(text) {
            if (text == '' || text == undefined) return text;
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        }

        //다음 우편번호 검색
        function openDaumPostcode(mode, index) {

            var newZipcode 		= "buyer.newZipcode";
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

                var post = data.postcode;
                if (post == '') {
                    post = data.zonecode;
                }

                if (mode == "receive") {
                    Order.changeReceiverZipcode(post, index);
                }

                // 배송비 재설정
                Order.setShippingAmount();

                Order.setAmountText();

            });

        }

        function selOpt()
        {
            $('div.pgInputArea').find('input[name="quota"]').val($('#selQuota').val());
            $('div.pgInputArea').find('input[name="KVP_QUOTA_INF"]').val($('#selQuota').val());
            $('div.pgInputArea').find('input[name="inst_term"]').val($('#selQuota').val());
        }

        function cashReceiptSet(payType){
            // 2017.09.20 손준의 - 현금영수증 신청 정보 입력란 보이기/숨기기

            document.getElementById('cashReceiptType1').style = 'display:none';
            document.getElementById('cashReceiptType2').style = 'display:none';
            document.getElementById('cashReceipt.cashReceiptType3').checked = true


            document.getElementById('cashReceipt.cashReceiptPhone1').value = '';
            document.getElementById('cashReceipt.cashReceiptPhone2').value = '';
            document.getElementById('cashReceipt.cashReceiptPhone3').value = '';

            document.getElementById('cashReceipt.cashReceiptBusinessNumber1').value = '';
            document.getElementById('cashReceipt.cashReceiptBusinessNumber2').value = '';
            document.getElementById('cashReceipt.cashReceiptBusinessNumber3').value = '';

            if(payType == 'bank' || ('${autoCashReceipt}' == 'N' && (payType == 'vbank' || payType == 'realtimebank'))){
                $('div#cashReceipt').css("display","");
            } else {
                $('div#cashReceipt').css("display","none");
            }
        }

        function jsf__chk_type() {
            if ( document.buy.card_code.value == "CCBC"||document.buy.card_code.value == "CCKM"||document.buy.card_code.value == "CCSU"||
                document.buy.card_code.value == "CCJB"||document.buy.card_code.value == "CCKJ"||document.buy.card_code.value == "CCPH"||
                document.buy.card_code.value == "CCSM"||document.buy.card_code.value == "CCPB"||document.buy.card_code.value == "CCSB"||
                document.buy.card_code.value == "CCKD"||document.buy.card_code.value == "CCCJ"||document.buy.card_code.value == "CCCU" ) {

                document.buy.card_pay_method.value = "ISP";
            } else if ( document.buy.card_code.value == "CCLG"||document.buy.card_code.value == "CCDI"||document.buy.card_code.value == "CCSS"||
                document.buy.card_code.value == "CCKE"||document.buy.card_code.value == "CCLO"||document.buy.card_code.value == "CCCT"||
                document.buy.card_code.value == "CCNH"||document.buy.card_code.value == "CCHN" ) {

                document.buy.card_pay_method.value = "V3D";
            }

        }
    </script>
</page:javascript>