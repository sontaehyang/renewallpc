<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div class="inner cart_wrap">
    <div class="location_area">
        <div class="breadcrumbs">
            <a href="/" class="home"><span class="hide">home</span></a>
            <span>장바구니</span>
        </div>
    </div><!-- // location_area E -->
    <div class="content_title">
        <h2 class="title">장바구니</h2>
    </div><!--//content_title E-->

    <div class="order_step">
        <ul>
            <li class="on"><span class="number">01</span> 장바구니 </li>
            <li><span class="number">02</span> 주문결제</li>
            <li><span class="number">03</span> 주문완료</li>
        </ul>
    </div>

    <c:choose>
        <c:when test="${ count > 0 }">
            <div class="box_list pt0">
                <ul>
                    <li>장바구니상품은 30일간 보관됩니다. 더 오래 보관하시려면 [관심상품]으로 등록하세요.</li>
                    <li>장바구니상품이 품절되면 자동으로 목록에서 삭제됩니다.</li>
                </ul>
            </div>
            <h3 class="order_title mt40">장바구니 선택상품 <span>(${count})</span></h3>
            <form id="listForm">
                <div class="board_wrap mt15">
                    <table cellpadding="0" cellspacing="0" class="mypage_list op-cart-item-table">
                        <caption>장바구니 list</caption>
                        <colgroup>
                            <col style="width:40px;">
                            <col style="width:auto;">
                            <col style="width:72px;">
                            <col style="width:108px;">
                            <col style="width:108px;">
                            <col style="width:120px;">
                            <col style="width:135px;">
                            <col style="width:100px;">
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col"><input type="checkbox" name="" id="op-item-select-all" title="체크박스"></th>
                            <th scope="col" class="none_left">상품명/옵션정보</th>
                            <th scope="col">수량</th>
                            <th scope="col">상품금액</th>
                            <th scope="col">할인금액</th>
                            <th scope="col">할인적용금액</th>
                            <th scope="col">배송정보</th>
                            <th scope="col">주문</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="viewTarget" value="cart" scope="request" />
                        <c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
                            <c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
                                <c:set var="viewCount" value="${ viewCount + 1 }" />
                                <c:set var="singleShipping" value="${shipping.singleShipping}"/>
                                <c:set var="shipping" value="${shipping}" scope="request" />
                                <c:set var="rowspan" value="1" scope="request" />
                                <c:if test="${singleShipping == false}">
                                    <c:set var="rowspan" value="${ fn:length(shipping.buyItems) }" scope="request" />
                                </c:if>

                                <c:choose>
                                    <c:when test="${singleShipping == true}">
                                        <c:set var="buyItem" value="${shipping.buyItem}" scope="request" />
                                        <c:set var="itemIndex" value="${itemIndex}" scope="request" />
                                        <c:set var="firstItem" value="true" scope="request" />
                                        <%-- CJH 2016.12.05 해당 파일은 주문과 장바구니에서 사용함 --%>
                                        <jsp:include page="../include/buy-item-list.jsp"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="itemIndex">
                                            <c:set var="buyItem" value="${buyItem}" scope="request" />
                                            <c:set var="firstItem" value="${itemIndex.first}" scope="request" />
                                            <jsp:include page="../include/buy-item-list.jsp"/>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table><!--// mypage_list E-->
                </div>	<!-- // board_view E -->
            </form>
            <div class="clear btn_wrap pt10">
                <div class="btn_left">
                    <button type="button" class="btn btn-default btn-m" id="op-delete-list-data" title="선택상품 삭제">선택상품 삭제</button>
                </div>
                <p class="order_guide">※ 실 결제금액은 쿠폰할인 적용에 따라 달라집니다.</p>
            </div><!--//btn_wrap E-->

            <div class="price_box">
                <div class="price_inner">
                    <div class="money">
                        <p class="txt01">총 상품합계 금액</p>
                        <p class="prices"><span>${op:numberFormat(buy.orderPrice.totalItemPrice)}</span>원</p>
                    </div>
                    <span class="icons"><img src="/content/images/icon/icon_plus.png" alt="더하기"></span>
                    <div class="money">
                        <p class="txt01">배송비</p>
                        <p class="prices"><span>${op:numberFormat(buy.orderPrice.totalShippingAmount)}</span>원</p>
                    </div>
                    <span class="icons"><img src="/content/images/icon/icon_minus.png" alt="빼기"></span>
                    <div class="money">
                        <p class="txt01">할인금액</p>
                        <p class="prices"><span>${op:numberFormat(buy.orderPrice.totalDiscountAmount)}</span>원</p>
                    </div>
                    <span class="icons"><img src="/content/images/icon/icon_sum.png" alt="더하기"></span>
                    <div class="money">
                        <p class="txt01"><strong>결제예정금액</strong></p>
                        <p class="prices total"><span>${op:numberFormat(buy.orderPrice.orderPayAmount)}</span>원</p>
                    </div>
                </div> <!-- // price_inner E -->
                <div class="add_point">
                    <p>적립예정 ${op:message('M00246')} : <span>${op:numberFormat(buy.orderPrice.totalEarnPoint)} P</span> </p>
                </div>
            </div><!--// price_box E-->

            <div class="btn_wrap pt30">
                <button type="button" class="btn btn-success btn-lg" id="op-selected-order" title="선택 상품주문">선택 상품주문</button>
                <button type="button" class="btn btn-submit btn-lg" id="op-all-order" title="전체 상품주문">전체 상품주문</button>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cart_none">
                <p>장바구니에 담겨있는 상품이 없습니다.</p>
            </div>
        </c:otherwise>
    </c:choose>


</div><!--// inner E-->

<page:javascript>
    <script type="text/javascript">
        var buttonType = '';
        $(function(){
            $('#op-delete-list-data').on('click', function() {

                Common.updateListData("/cart/list/delete", "${op:message('M00418')}");	// 선택하신 상품을 장바구니에서 삭제하시겠습니까?
            });

            $('.op-available-item').on('click', function() {
                var itemId = $(this).data('item-id');
                var itemOptions = $(this).data('item-options');

                $('input[name="id"][data-parent-item-id="' + itemId +'"][data-parent-item-options="' + itemOptions + '"][data-addition-item-flag="Y"]').prop('checked', $(this).prop('checked'));
            });

            $('#op-all-order').on('click', function() {
                buttonType = 'all';
                var $availableItem = $(':checkbox[class^=op-available-item]');
                saveOrderItemTemp($availableItem, "${op:message('M00440')}"); // 구매 가능한 상품이 없습니다.

            });

            $('#op-selected-order').on('click', function() {
                buttonType = 'select';
                var $availableItem = $(':checkbox[class^=op-available-item]:checked');

                if ($availableItem.size() > 0) {
                    $.each($availableItem, function() {
                        if ($(this).prop('checked') == true) {
                            $(':checkbox[data-item-id="'+ $(this).data('parent-item-id') +'"]').eq(0).prop('checked', true);
                        }
                    });
                }

                saveOrderItemTemp($availableItem, "${op:message('M00439')}"); // 구매 하실 상품을 선택해 주세요.
            });

            $('input[name="quantity"]').on('keydown', function(e){
                if (e.keyCode == '13') {
                    var cartId = $(this).attr('id').replace('quantity-', '');
                    editQuantity(cartId);
                }
            });

            $('#op-item-select-all').on('click', function(){

                $checkbox = $('.op-cart-item-table').find('input[type=checkbox]');
                if ($checkbox.size() > 0) {
                    if ($(this).prop('checked') == true) {
                        $checkbox.prop('checked', true);
                    } else {
                        $checkbox.prop('checked', false);
                    }
                }
            });

            $('.op-cart-item-table select[name=shippingPaymentType]').on('change', function(){
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
            $.each($availableItem, function(i) {

                var quantity = parseInt($('#quantity-' + $(this).val()).val());
                if (quantity <= 0) {
                    isSuccess = false;
                }

                cartIds[i] = $(this).val();
            });

            if (isSuccess == false) {
                alert(Message.get("M01590")); // 상품의 수량을 확인해주세요.
                return;
            }

            var params = {
                'cartIds' : cartIds
            };
            var returnUrl = "";

            $.ajaxSetup({
                'async' : false
            });
            $.post('/open-market/naver-pay/web', params, function(response) {
                Common.responseHandler(response, function(response) {
                    returnUrl = response.data;
                }, function(response) {
                    alert(response.errorMessage)
                });
            }, 'json');

            return returnUrl;
        }

        // 삭제
        function deleteItem(cartId) {
            $(':checkbox[class^=op-available-item]:checked').prop('checked', false);

            $('#tempId2').prop('checked', false);
            $(':checkbox[value="' + cartId + '"]').prop('checked', true);
            var delButtons = $('button.btn.btn-cart.item-del');

            delButtons.each(function(index){

                var id = $('#listForm input.op-available-item').eq(index).val();
                if (cartId != id) {
                    $(':checkbox[value="' + id + '"]').prop('checked', false);
                }

            });

            var itemId = $(':checkbox[value="' + cartId + '"]').data('item-id');
            var itemOptions = $(':checkbox[value="' + cartId + '"]').data('item-options');
            $('input[name="id"][data-parent-item-id="' + itemId +'"][data-parent-item-options="' + itemOptions + '"][data-addition-item-flag="Y"]').prop('checked', true);

            Common.updateListData("/cart/list/delete");
        }

        // 바로구매
        function buyNow(cartId) {
            $(':checkbox[class^=op-available-item]:checked').prop('checked', false);

            buttonType = 'select';
            $(':checkbox[value="' + cartId + '"]').prop('checked', true);
            var $availableItem = $(':checkbox[class^=op-available-item]:checked');

            if ($availableItem.size() > 0) {
                $.each($availableItem, function() {
                    if ($(this).prop('checked') == true) {
                        $(':checkbox[data-parent-item-id="'+ $(this).data('itemId') +'"]').prop('checked', true);
                    }
                });

                $availableItem = $(':checkbox[class^=op-available-item]:checked');
            }

            saveOrderItemTemp($availableItem, "${op:message('M00439')}"); // 구매 하실 상품을 선택해 주세요.
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

                var quantity = parseInt($('#quantity-' + $(this).val()).val());
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
                        Common.popup('/users/login?target=order&popup=1&redirect=/order/step1', 'popup_login', 550, 435, 1);
                    } else {
                        location.replace('/order/step1');
                    }

                }, function(response){

                    //$('div.stock_state').html('<p>' + response.errorMessage + '</p>');
                    alert(response.errorMessage);
                    //location.reload();
                });
            }, 'json');
        }

        function selectAll(key, obj) {

            $checkbox = $('tbody.' + key).find('input[type=checkbox]');
            if ($checkbox.size() > 0) {
                if ($(obj).prop('checked') == true) {
                    $checkbox.prop('checked', true);
                } else {
                    $checkbox.prop('checked', false);
                }
            }

        }

        function editQuantity(cartId) {
            var quantity = $('#quantity-' + cartId).val();
            if (quantity == undefined) return;
            if (quantity == '') {
                alert("${op:message('M00441')}");	// 상품의 수량을 입력해 주세요.
                return;
            }

            var orderMinQuantity = $('#orderMinQuantity-' + cartId).val();

            if ($.isNumeric(orderMinQuantity) == true) {
                if (orderMinQuantity > 0) {
                    if (parseInt(quantity) < parseInt(orderMinQuantity)) {
                        alert("${op:message('M00442')} "+ orderMinQuantity +"${op:message('M00443')}"); // 해당 상품의 최소 구매가능 수량은 00개 입니다.
                        return;
                    }
                }
            }

            var orderMaxQuantity = $('#orderMaxQuantity-' + cartId).val();
            if ($.isNumeric(orderMaxQuantity) == true) {
                if (orderMaxQuantity > 0) {
                    if (parseInt(quantity) > parseInt(orderMaxQuantity)) {
                        alert("${op:message('M00444')} "+ orderMaxQuantity +"${op:message('M00443')}"); // 해당 상품의 최대 구매가능 수량은 00개 입니다.
                        return;
                    }
                }
            }

            $.post('/cart/edit-quantity', {'quantity' : quantity, 'cartId' : cartId}, function(response){
                Common.responseHandler(response, function(response) {
                    location.reload();
                }, function(response){

                    alert("Error.");

                });
            }, 'json');
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