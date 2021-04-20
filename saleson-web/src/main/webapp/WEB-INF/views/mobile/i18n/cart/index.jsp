<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div class="title">
    <h2>장바구니</h2>
    <span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
</div>

<div class="con">
    <div class="cart_wrap">
        <form id="listForm">
            <c:choose>
                <c:when test="${ count > 0 }">
                    <div class="cart_top">
                        <p class="total">장바구니(<span class="num">${count}</span>)</p>
                        <button type="button" class="btn_st3 op-cart-list-all-delete">전체삭제</button>
                    </div>

                    <c:set var="viewTarget" value="cart" scope="request" />
                    <c:set var="i" value="0" scope="request" />
                    <c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
                        <c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
                            <c:set var="viewCount" value="${ viewCount + 1 }" />
                            <c:set var="singleShipping" value="${shipping.singleShipping}"/>
                            <c:set var="shipping" value="${shipping}" scope="request" />
                            <c:set var="rowspan" value="0" scope="request" />
                            <c:set var="i" value="${i + 1}" scope="request" />
                            <c:if test="${singleShipping == false}">
                                <c:set var="rowspan" value="${ fn:length(shipping.buyItems) }" scope="request" />
                            </c:if>

                            <c:choose>
                                <c:when test="${singleShipping == true}">
                                    <c:set var="buyItem" value="${shipping.buyItem}" scope="request" />
                                    <c:set var="firstItem" value="true" scope="request" />
                                    <div class="cart_list">
                                        <ul class="list">
                                            <jsp:include page="../include/buy-item-list.jsp"/>
                                        </ul>
                                        <div class="shipping_wrap">
                                            <span class="tit">배송비</span>
                                            <div class="delivery_wrap">
                                                <c:set var="shippingTypeText" value="" />
                                                <c:choose>
                                                    <c:when test="${shipping.shippingType == 2}"><c:set var="shippingTypeText" value="판매자" /></c:when>
                                                    <c:when test="${shipping.shippingType == 3}"><c:set var="shippingTypeText" value="출고지" /></c:when>
                                                    <c:when test="${shipping.shippingType == 4}"><c:set var="shippingTypeText" value="상품" /></c:when>
                                                </c:choose>
                                                <a href="#" class="btn_st4 t_blue delv_btn">
                                                    ${shippingTypeText} 조건부 무료배송
                                                </a>
                                                <span class="delievery_tip">
                                                    <c:set var="deliveryText" value="" />
                                                    <c:choose>
                                                        <c:when test="${shipping.shippingType == 2}"><c:set var="deliveryText" value="판매자" /></c:when>
                                                        <c:when test="${shipping.shippingType == 3}"><c:set var="deliveryText" value="동일한 출고지" /></c:when>
                                                        <c:when test="${shipping.shippingType == 4}"><c:set var="deliveryText" value="해당" /></c:when>
                                                    </c:choose>
                                                    <p class="title">${shippingTypeText} 조건부 무료배송 </p>
                                                    <p>${deliveryText} 상품 ${op:numberFormat(shipping.shippingFreeAmount)}원 이상 구매 시 무료,<br>미만 구매시 ${ op:numberFormat(shipping.shipping) }원 부과</p>
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
                                                    <b class="delievery_close"><img src="/content/images/btn/btn_tooltip_close.gif" alt="close"></b>
                                                </span>
                                            </div><!--// delivery_wrap -->

                                            <p class="shipping_price">
										 			<span>
										 				<c:choose>
                                                            <c:when test="${firstItem != true }">묶음상품</c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${ shipping.realShipping == 0 }"><span>${op:message('M00448')}배송 <!-- 무료 --></span></c:when>
                                                                    <c:otherwise>
                                                                        ${ op:numberFormat(shipping.realShipping) }원
                                                                        <select id="op-shipping-payment-type-${ buyItem.cartId }" name="shippingPaymentType" style="width:54%;">
																				<option value="1" ${op:selected("1", shipping.shippingPaymentType) }>선불</option>
																				<option value="2" ${op:selected("2", shipping.shippingPaymentType) }>착불</option>
																			</select>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
										 			</span>
                                            </p>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="itemIndex">
                                        <c:set var="buyItem" value="${buyItem}" scope="request" />
                                        <c:set var="firstItem" value="${itemIndex.first}" scope="request" />
                                        <c:set var="i" value="${i + 1}" scope="request" />
                                        <div class="cart_list">
                                            <ul class="list">
                                                <jsp:include page="../include/buy-item-list.jsp"/>
                                            </ul>
                                            <div class="shipping_wrap">
                                                <span class="tit">배송비</span>
                                                <div class="delivery_wrap">
                                                    <c:set var="shippingTypeText" value="" />
                                                    <c:choose>
                                                        <c:when test="${shipping.shippingType == 2}"><c:set var="shippingTypeText" value="판매자" /></c:when>
                                                        <c:when test="${shipping.shippingType == 3}"><c:set var="shippingTypeText" value="출고지" /></c:when>
                                                        <c:when test="${shipping.shippingType == 4}"><c:set var="shippingTypeText" value="상품" /></c:when>
                                                    </c:choose>
                                                    <a href="#" class="btn_st4 t_blue delv_btn">
                                                        ${shippingTypeText} 조건부 무료배송
                                                    </a>
                                                    <span class="delievery_tip">
                                                        <c:set var="deliveryText" value="" />
                                                        <c:choose>
                                                            <c:when test="${shipping.shippingType == 2}"><c:set var="deliveryText" value="판매자" /></c:when>
                                                            <c:when test="${shipping.shippingType == 3}"><c:set var="deliveryText" value="동일한 출고지" /></c:when>
                                                            <c:when test="${shipping.shippingType == 4}"><c:set var="deliveryText" value="해당" /></c:when>
                                                        </c:choose>
                                                        <p class="title">${shippingTypeText} 조건부 무료배송 </p>
                                                        <p>${deliveryText} 상품 ${op:numberFormat(shipping.shippingFreeAmount)}원 이상 구매 시 무료,<br>미만 구매시 ${ op:numberFormat(shipping.shipping) }원 부과</p>
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
                                                        <b class="delievery_close"><img src="/content/images/btn/btn_tooltip_close.gif" alt="close"></b>
                                                    </span>
                                                </div><!--// delivery_wrap -->

                                                <p class="shipping_price">
											 			<span>
												 			<c:choose>
                                                                <c:when test="${firstItem != true }">묶음상품</c:when>
                                                                <c:otherwise>
                                                                    <c:choose>
                                                                        <c:when test="${ shipping.realShipping == 0 }"><span>${op:message('M00448')}배송 <!-- 무료 --></span></c:when>
                                                                        <c:otherwise>
                                                                            ${ op:numberFormat(shipping.realShipping) }원
                                                                            <select id="op-shipping-payment-type-${ buyItem.cartId }" name="shippingPaymentType" style="width:54%;">
																					<option value="1" ${op:selected("1", shipping.shippingPaymentType) }>선불</option>
																					<option value="2" ${op:selected("2", shipping.shippingPaymentType) }>착불</option>
																				</select>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                            </c:choose>
														</span>
                                                </p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:forEach>

                    <div class="cart_order">
                        <div class="txt_wrap">
                            <div class="order total">
                                <span class="tit">상품금액</span>
                                <p class="total_price"><span>${ op:numberFormat(buy.orderPrice.totalItemPrice) }</span>원</p>
                            </div>
                            <div class="order total">
                                <span class="tit">할인금액</span>
                                <p class="total_price"><span>-${ op:numberFormat(buy.orderPrice.totalDiscountAmount) }</span>원</p>
                            </div>
                            <div class="shipping total">
                                <span class="tit">배송비</span>
                                <p class="total_price"><span>${ op:numberFormat(buy.orderPrice.totalShippingAmount) }</span>원</p>
                            </div>
                            <div class="sum">
                                <span class="tit">결제 예정 금액</span>
                                <p class="total_price"><span>${ op:numberFormat(buy.orderPrice.orderPayAmount) }</span>원</p>
                            </div>
                            <dl class="caution">
                                <dt>장바구니 주의사항</dt>
                                <dd>장바구니에 담긴 상품은 30일 동안 보관됩니다. 30일이 초과된 상품은 자동으로 삭제됩니다.</dd>
                                <dd>더 오래 보관하고 싶으신 상품은 관심상품에 추가하시기 바랍니다.</dd>
                            </dl>
                        </div>
                        <!-- //txt_wrap -->
                        <div class="cart_btn_wrap">
                            <a href="javascript:;" class="btn_st1 b_gray op-cart-list-delete">선택삭제</a>
                            <a href="javascript:;" class="btn_st1 b_blue" id="op-cart-order-select" >선택주문</a>
                            <a href="javascript:;" class="btn_st1 b_pink" id="op-cart-order-all">전체주문</a>
                        </div>
                        <!-- //btn_wrap -->
                    </div>
                    <!-- //cart_order -->
                </c:when>
                <c:otherwise>
                    <div class="cart_list">
                        <div class="cart_none">
                            <p>장바구니에 담겨있는<br>상품이 없습니다. </p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
    <%--
    <div class="relation">
        <div class="inner">
            <h4>관련상품</h4>
            <div class="relation_slider">
                 <div class="swiper-wrapper">
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img">
                                <img src="/content/mobile/images/common/relation_img01.jpg" alt="img01">
                            </div>
                            <div class="txt">
                                <p class="name">자연한모금 현미 유기농 녹차(100티백)</p>
                                <p class="price">35,000<span>원</span></p>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img">
                                <img src="/content/mobile/images/common/relation_img02.jpg" alt="img02">
                            </div>
                            <div class="txt">
                                <p class="name">블루베리 유기농</p>
                                <p class="price">35,000<span>원</span></p>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img">
                                <img src="/content/mobile/images/common/relation_img01.jpg" alt="img01">
                            </div>
                            <div class="txt">
                                <p class="name">자연한모금 현미 유기농 녹차(100티백)</p>
                                <p class="price">35,000<span>원</span></p>
                            </div>
                        </a>
                    </div>
                    <div class="swiper-slide">
                        <a href="#">
                            <div class="img">
                                <img src="/content/mobile/images/common/relation_img02.jpg" alt="img02">
                            </div>
                            <div class="txt">
                                <p class="name">블루베리 유기농 마스크팩</p>
                                <p class="price">35,000<span>원</span></p>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="swiper-pagination"></div>
            </div>
        </div>
    </div>
    <!-- //relation -->
    --%>

    <div class="btn_layer02 pop_shipping01"> <!-- 추가 개발 필요 Kye -->
        <div class="title">
				<p class="tit">무료배송</p>
                    <button type="button" class="layer02_close">닫기</button>
        </div>
        <div class="con">
            <p>조건없이 무료배송 상품입니다.</p>
        </div>
    </div>
    <!-- //pop_shipping01 -->

    <div class="btn_layer02 pop_shipping02">
        <div class="title">
				<p class="tit">무료배송</p>
                    <button type="button" class="layer02_close">닫기</button>
        </div>
        <div class="con">
            <p>판매자 상품 30,000원 이상 구매 시 무료,<br>
                미만 구매시 2,500원 부과<br><br>
                제주/도서산간<br>
                (제주 4,000 /도서산간 4,000 추가)
            </p>
        </div>
    </div>
    <!-- //pop_shipping02 -->


    <!-- 랭킹 -->
    <%--<jsp:include page="../include/swiper-ranking-items.jsp" /> --%>

</div>

<page:javascript>
    <script type="text/javascript">
        $(function() {

            // 모바일 키페드 셋팅
            Common.setMobileKeypad(true);

            changeQuantity();

            $('.op-cart-list-delete').on('click', function() {
                $.each($('input[name="quantity"]'), function() {
                    $(this).val(parseInt($(this).val()));
                });

                Common.updateListData("/m/cart/list/delete", "${op:message('M00418')}");	// 선택하신 상품을 장바구니에서 삭제하시겠습니까?
            });

            $('.op-cart-list-all-delete').on('click', function() {
                $("input[name=id]").prop("checked",true);
                $.each($('input[name="quantity"]'), function() {
                    $(this).val(parseInt($(this).val()));
                });

                Common.updateListData("/m/cart/list/delete", "${op:message('M01669')}");	// 전체 상품을 장바구니에서 삭제하시겠습니까?
            });

            $('.op-available-item').on('click', function() {
                var itemId = $(this).data('item-id');
                var itemOptions = $(this).data('item-options');

                $('input[name="id"][data-parent-item-id="' + itemId +'"][data-parent-item-options="' + itemOptions + '"][data-addition-item-flag="Y"]').prop('checked', $(this).prop('checked'));
            });


            $('input[name="quantity"]').on('focusout', function(){

                var $id = $(this).attr('id');
                var $quantity = parseInt($('#' + $id).val());
                var $orderMaxQuantityId = $(this).closest('div').find('input[name="orderMaxQuantity"]').attr('id');
                var $orderMaxQuantity = Number($('#' + $orderMaxQuantityId).val());
                var $orderMinQuantityId = $(this).closest('div').find('input[name="orderMinQuantity"]').attr('id');
                var $orderMinQuantity = Number($('#' + $orderMinQuantityId).val());

                if(checkOrderMaxQuantity($id, $quantity, $orderMaxQuantity, true) && checkOrderMinQuantity($id, $quantity, $orderMinQuantity, true)) {
                    $(this).val(parseInt($(this).val()));
                }
                quantityUpdateButton($id);
            });

            $('#op-cart-order-all').on('click', function() {
                buttonType = 'all';
                var $availableItem = $(':checkbox[class^=op-available-item]');
                saveOrderItemTemp($availableItem, "${op:message('M00440')}"); // 구매 가능한 상품이 없습니다.

            });

            $('#op-cart-order-select').on('click', function() {
                buttonType = 'select';
                var $availableItem = $(':checkbox[class^=op-available-item]:checked');
                saveOrderItemTemp($availableItem, "${op:message('M00439')}"); // 구매 하실 상품을 선택해 주세요.
            });

            if ($('.scrollContent').size() > 0) {
                var position = $('.positioning').position().top;
                $('.positioning').css('padding-top', '10px');
                $(window).scroll(function () {
                    var scrollHeight = $(window).scrollTop() + $(window).height() - 150 ;

                    if (scrollHeight >= position) {
                        $('.scrollContent').css({'position' : 'inherit', 'border-top' : '0'});
                        $('.positioning').css('padding-top', '0px');
                    } else {
                        $('.scrollContent').css({'position' : 'fixed', 'border-top' : '2px solid #545454'});
                        $('.positioning').css('padding-top', '10px');
                    }
                });
            }

            $('#listForm select[name=shippingPaymentType]').on('change', function(){
                var cartId = $(this).attr('id').replace('op-shipping-payment-type-', '');
                setShippingPaymentType(cartId, $(this).val());
            });
        });

        //네이버 체크아웃
        function naverPayInCart() {
            var $availableItem = $(':checkbox[class^=op-available-item]');
            if ($availableItem.size() == 0) {
                alert("${op:message('M00440')}");
                return;
            }

            var isSuccess = true;
            var cartIds = {};
            $.each($availableItem, function(i){

                var quantity = parseInt($('#op-quantity-' + $(this).val()).val());
                if (quantity <= 0) {
                    isSuccess = false;
                }

                cartIds[i] = $(this).val();
            });

            if (isSuccess == false) {
                alert(Message.get("M01590"));	// 상품의 수량을 확인해주세요.
                return;
            }

            var params = {'cartIds' : cartIds};
            var returnUrl = "";

            $.ajaxSetup({'async': false});
            $.post('/open-market/naver-pay/mobile', params, function(response){
                Common.responseHandler(response, function(response) {
                    returnUrl = response.data;
                }, function(response){
                    alert(response.errorMessage)
                });
            }, 'json');

            return returnUrl;
        }

        var PopupLogin = {};
        PopupLogin.Callback = function() {
            if (buttonType == 'all') {
                var $availableItem = $(':checkbox[class^=op-available-item]');
                saveOrderItemTemp($availableItem, "${op:message('M00440')}", false); // 구매 가능한 상품이 없습니다.
            } else {
                var $availableItem = $(':checkbox[class^=op-available-item]:checked');
                saveOrderItemTemp($availableItem, "${op:message('M00439')}", false); // 구매 하실 상품을 선택해 주세요.
            }
        }

        function quantityUpdateButton(id) {
            id = id.replace('op-quantity','');
            var orgQuantity = $('#op-org-quantity' + id).val();
            var quantity = parseInt($('#op-quantity'+ id).val());
            if (quantity == orgQuantity) {
                $('#op-save-quantity' + id).hide();
            } else {
                $('#op-save-quantity' + id).show();
            }
        }

        function saveOrderItemTemp($availableItem, message, loginCheck) {
            if ($availableItem.size() == 0) {
                alert(message);
                return;
            }

            var isLogin = '${requestContext.userLogin}';
            loginCheck = loginCheck == undefined ? true : loginCheck;

            var cartIds = {};
            var isSuccess = true;
            $.each($availableItem, function(i){

                var quantity = parseInt($('#op-quantity-' + $(this).val()).val());
                if (quantity <= 0) {
                    isSuccess = false;
                }

                cartIds[i] = $(this).val();
            });

            if (isSuccess == false) {
                alert(Message.get("M01590"));	// 상품의 수량을 확인해주세요.
                return;
            }

            var params = {'cartIds' : cartIds};
            $.post('/cart/save-order-item-temp', params, function(response){
                Common.responseHandler(response, function(response) {

                    if (isLogin == 'false' && loginCheck == true) {
                        location.replace('/m/users/login?target=order&popup=1&redirect=/m/order/step1');
                    } else {
                        location.replace('/m/order/step1');
                    }

                }, function(response){

                    //$('div.stock_state').html('<p>' + response.errorMessage + '</p>');
                    alert(response.errorMessage);
                    location.reload();
                });
            }, 'json');
        }

        function selectAll(obj) {

            $('.itemAll').prop('checked', $(obj).prop('checked'));
            $checkbox = $('tbody.items').find('input[type=checkbox]');
            if ($checkbox.size() > 0) {
                if ($(obj).prop('checked') == true) {
                    $checkbox.prop('checked', true);
                } else {
                    $checkbox.prop('checked', false);
                }
            }

        }

        function changeQuantity() {
            /*function changeQuantity(mode, cartId) {
             var quantity = $('#op-quantity-' + cartId).val();
            if (quantity == '') {
                quantity = 0;
            }

            quantity = parseInt(quantity);
            quantity = mode == 'up' ? quantity + 1 : quantity - 1;

            var orderMinQuantity = $('#op-order-min-quantity-' + cartId).val();

            // 모바일 장바구니 상품 개수 최소값 체크 2017-04-25 yulsun.yoo
            if (orderMinQuantity <= 0) {
                orderMinQuantity = 1;
            } */


            // 수량 증가 버튼을 클릭 시	[2017-04-27 최정아]
            $('.plus').on('click', function() {
                var $id = $(this).closest('div').find('input[name="quantity"]').attr('id');
                var $orderMaxQuantityId = $(this).closest('div').find('input[name="orderMaxQuantity"]').attr('id');
                var $quantity = Number($('#' + $id).val());
                var $orderMaxQuantity = Number($('#' + $orderMaxQuantityId).val());

                if(checkOrderMaxQuantity($id, $quantity, $orderMaxQuantity, false)) {
                    $('#' + $id).val($quantity + 1);
                }

                quantityUpdateButton($id);

            });

            // 수량 감소 버튼을 클릭시	[2017-04-27 최정아]
            $('.minus').on('click', function() {
                var $id = $(this).closest('div').find('input[name="quantity"]').attr('id');
                var $orderMinQuantityId = $(this).closest('div').find('input[name="orderMinQuantity"]').attr('id');
                var $quantity = Number($('#' + $id).val());
                var $orderMinQuantity = Number($('#' + $orderMinQuantityId).val());

                if(checkOrderMinQuantity($id, $quantity, $orderMinQuantity, false)) {
                    $('#' + $id).val($quantity - 1);
                }

                quantityUpdateButton($id);

            });

        }

        function editQuantity(cartId) {
            var quantity = $('#op-quantity-' + cartId).val();
            if (quantity == undefined) return;
            if (quantity == '') {
                alert("${op:message('M00441')}");	// 상품의 수량을 입력해 주세요.
                $('#op-quantity-' + cartId).focus();
                return;
            }

            var orderMinQuantity = $('#op-order-min-quantity-' + cartId).val();

            if ($.isNumeric(orderMinQuantity) == true) {
                if (orderMinQuantity > 0) {
                    if (parseInt(quantity) < parseInt(orderMinQuantity)) {
                        alert("${op:message('M00442')} "+ orderMinQuantity +"${op:message('M00443')}"); // 해당 상품의 최소 구매가능 수량은 00개 입니다.
                        $('#op-quantity-' + cartId).val($('#op-org-quantity-' + cartId).val());
                        return;
                    }
                }
            }

            var orderMaxQuantity = $('#op-order-max-quantity-' + cartId).val();
            if ($.isNumeric(orderMaxQuantity) == true) {
                if (orderMaxQuantity > 0) {
                    if (parseInt(quantity) > parseInt(orderMaxQuantity)) {
                        alert("${op:message('M00444')} "+ orderMaxQuantity +"${op:message('M00443')}"); // 해당 상품의 최대 구매가능 수량은 00개 입니다.
                        $('#op-quantity-' + cartId).val($('#op-org-quantity-' + cartId).val());
                        return;
                    }
                }
            }

            $.post('/m/cart/edit-quantity', {'quantity' : quantity, 'cartId' : cartId}, function(response){
                Common.responseHandler(response, function(response) {
                    location.reload();
                }, function(response){

                    alert("Error.");

                });
            }, 'json');
        }

        // 상품 수량 input의 maxlength와 상품에 설정된 최대 구매수량에 따른 최대 구매가능 수량을 체크	[2017-04-27 최정아]
        function checkOrderMaxQuantity($id, $quantity, $orderMaxQuantity, $isFocusedOut){

            var $futureQuantity = String(Number($('#' + $id).val()) + 1);	// 현재 수량에서 +1하여 출력될 다음 수량
            var $result = true;

            if($orderMaxQuantity < 0) {			// 상품에 설정된 최대 구매수량이 무제한일 경우

                var $maxLength = Number($('#' + $id).attr('maxlength'));		// 상품 수량 input의 maxlength
                var $maxQuantityString = "";									// maxlength에 따른 최대 구매수량

                if(Number($('#' + $id).val().length) > $maxLength || (Number($futureQuantity.length) > $maxLength && $isFocusedOut == false)) {		// 현재 수량이 maxlength를 넘거나 다음 수량이 maxlength를 넘을 경우
                    for(i = 0 ; i < $maxLength ; i++) {		// maxlength만큼 for문 실행
                        $maxQuantityString += "9";
                    }
                    $result = false;
                    $('#' + $id).val($maxQuantityString);
                    alert("${op:message('M00444')} "+ $maxQuantityString +"${op:message('M00443')}"); // 해당 상품의 최대 구매가능 수량은 00개 입니다.
                }
            } else {
                if ($quantity > $orderMaxQuantity || (Number($futureQuantity) > $orderMaxQuantity && $isFocusedOut == false)) {
                    alert("${op:message('M00444')} "+ $orderMaxQuantity +"${op:message('M00443')}"); // 해당 상품의 최대 구매가능 수량은 00개 입니다.
                    $result = false;
                    $('#' + $id).val($orderMaxQuantity);
                }
            }
            return $result;
        }

        // 상품에 설정된 최소 구매수량에 따른 최소 구매 수량을 체크	[2017-04-27 최정아]
        function checkOrderMinQuantity($id, $quantity, $orderMinQuantity, $isFocusedOut){

            var $futureQuantity = String(Number($('#' + $id).val()) - 1);	// 현재 수량에서 -1하여 출력될 다음 수량
            var $result = true;

            if($orderMinQuantity < 0) {			// 상품에 설정된 최소 구매수량이 무제한일 경우
                if(($futureQuantity <= 0  && $isFocusedOut == false) || ($quantity <= 0 && $isFocusedOut == true)) {	// 다음에 출력될 수량이 0이하일 경우
                    $result = false;
                    $('#' + $id).val(1);
                    alert("${op:message('M00442')} "+ 1 +"${op:message('M00443')}"); // 해당 상품의 최대 구매가능 수량은 1개 입니다.
                }
            } else {
                if ($quantity < $orderMinQuantity || (Number($futureQuantity) < $orderMinQuantity && $isFocusedOut == false)) {
                    alert("${op:message('M00444')} "+ $orderMinQuantity +"${op:message('M00443')}"); // 해당 상품의 최소 구매가능 수량은 00개 입니다.
                    $result = false;
                    $('#' + $id).val($orderMinQuantity);
                }
            }
            return $result;
        }

        function setShippingPaymentType(cartId, shippingPaymentType){
            var shippingGroupCode = $("#shippingGroupCode-" + cartId).val();
            $.post('/cart/set-shipping-payment-type', {'cartId' : cartId, 'shippingPaymentType' : shippingPaymentType, 'shippingGroupCode' : shippingGroupCode}, function(response){
                Common.responseHandler(response, function(response) {
                    location.reload();
                }, function(response){

                    alert("Error.");

                });
            }, 'json');
        }
    </script>
</page:javascript>


<!-- 전환페이지 설정 -->
<script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script> 
<script type="text/javascript"> 
var _nasa={};
if(window.wcs) _nasa["cnv"] = wcs.cnv("3","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
</script> 