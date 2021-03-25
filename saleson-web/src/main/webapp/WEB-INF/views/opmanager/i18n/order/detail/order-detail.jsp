<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<style type="text/css">
	td {
		padding-left: 5px;
	}
	.no_content {
		padding:10px;
	}
	.order_return_layer {
		display: none;
		position: fixed;
		z-index: 100000;
		width:850px;
		left: 50%;
		margin-left: -425px;
		top:0px;
		padding-bottom: 20px;
		background: #fff
	}
</style>

<c:if test='${mode != "popup"}'>
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
</c:if>


<div class="board_write">

	<div class="board_list">

		<h3 class="mt10">${order.orderCode} 주문 정보</h3>
		<table class="board_write_table">
			<colgroup>
				<col style="width: 15%;" />
				<col />
				<col style="width: 15%;" />
				<col />
			</colgroup>
			<tbody>
			<tr>
				<th class="label">주문번호</th>
				<td><div>${order.orderCode}</div></td>
				<th class="label">주문일시</th>
				<td><div>${op:datetime(order.createdDate)}</div></td>
			</tr>

			<c:choose>
				<c:when test="${requestContext.sellerPage == false}">
					<tr>
						<th class="label">회원정보</th>
						<td>
							<div>
								<c:choose>
									<c:when test="${order.userId > 0}">
										${order.userName} [${order.loginId}]
										<a href="javascript:Manager.userDetails(${order.userId})" class="btn btn-gradient btn-xs">CRM</a>
									</c:when>
									<c:otherwise>비회원</c:otherwise>
								</c:choose>
							</div>
						</td>
						<th class="label">발송인</th>
						<td>
							<div>${order.buyerName}</div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<th class="label">발송인</th>
						<td colspan="3">
							<div>${order.buyerName}</div>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<th class="label">구매자 휴대폰번호</th>
				<td>
					<div>${order.mobile}</div>
				</td>
				<th class="label">구매자 전화번호</th>
				<td>
					<div>${order.phone}</div>
				</td>
			</tr>

			<c:if test="${requestContext.sellerPage == false}">
				<tr>
					<th class="label">구매자 이메일</th>
					<td>
						<div>${order.email}</div>
					</td>
					<th class="label">구매자 IP</th>
					<td>
						<div>${order.ip}</div>
					</td>
				</tr>

				<c:if test="${not empty order.returnBankName}">
					<tr>
						<th class="label">환불 은행 정보</th>
						<td>
							<div>
								은행명 : ${order.returnBankName}은행<br/>
								예금주 : ${order.returnBankInName}<br/>
								계좌번호 : ${order.returnVirtualNo}<br/>
							</div>
						</td>
					</tr>
				</c:if>
			</c:if>
			</tbody>
		</table>
	</div>

	<div class="op-item-detail-area">
		<jsp:include page="../include/item-detail.jsp"></jsp:include>
	</div>

	<c:if test="${requestContext.sellerPage == false}">
		<h3 class="mt30">고객상담 내역</h3>
		<div id="claim-memo-list"></div>

		<div style="width:50%;float:left;padding:5px;">
			<div class="board_list">
				<h3 class="mt10">상담메모</h3>
				<form id="claimMemoForm">
					<input type="hidden" name="orderCode" value="${order.orderCode}" />
					<input type="hidden" name="userId" value="${order.userId}" />
					<input type="hidden" name="userName" value="${order.userName}" />
					<input type="hidden" name="loginId" value="${order.loginId}" />
					<table class="board_write_table">
						<colgroup>
							<col style="width:150px;">
							<col style="width:auto;">
							<col style="width:80px;">
						</colgroup>
						<tbody>
						<tr>
							<td colspan="2">
								<div>
									<textarea name="memo" maxlength="1000" class="required" title="고객상담 메모"></textarea>
								</div>
							</td>
							<td>
								<p>
									<select name="claimStatus">
										<option value="2">처리완료</option>
										<option value="1">처리중</option>
									</select>
								</p>
								<button type="submit" class="btn btn-active">저장</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>

		<div style="width:50%;float:left;padding:5px;">
			<div class="board_list">
				<h3 class="mt10">관리자 메모</h3>
				<form id="orderAdminMemoForm">
					<input type="hidden" name="orderCode" value="${order.orderCode}" />
					<input type="hidden" name="orderSequence" value="${order.orderSequence}" />

					<table class="board_write_table">
						<colgroup>
							<col style="width:150px;">
							<col style="width:auto;">
							<col style="width:80px;">
						</colgroup>
						<tbody>
						<tr>
							<td colspan="2">
								<div>
									<textarea name="orderAdminMemo" maxlength="1000" class="" title="관리자 메모">${order.orderAdminMemo}</textarea>
								</div>
							</td>
							<td>
								<button type="submit" class="btn btn-active">저장</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</c:if>

	<c:if test='${mode != "popup"}'>
		<div class="popup_btns" style="clear:both">
			<button type="button" onclick="Link.list('${requestContext.managerUri}/order/${pageType == 'all'?'list':pageType}')" class="btn btn-default">목록</button>
		</div>
	</c:if>

</div>



<!-- 다음 주소검색 -->
<daum:address />
<script type="text/javascript">

    $(function(){
        // 할인 상세 내역
        $('body').on('click', '.op-discount-details-button', function(e) {
            e.preventDefault();

            var $discountDetails = $(this).parent().find('.op-discount-details');
            var isBlock = $discountDetails.css('display') == 'block';
            $('.op-discount-details').hide();

            if (isBlock) {
                $discountDetails.hide();
            } else {
                $discountDetails.show();
            }
        });

        $('body').on('click', '#op-admin-claim-apply-all', function(){
            if ($(this).prop('checked') == false) {
                $('input[name="adminClaimApplyKey"]').prop('checked', false);
            } else {
                $('input[name="adminClaimApplyKey"]').prop('checked', false);
                var claimItems = $('input[name="adminClaimApplyKey"]');
                $.each(claimItems, function(){
                    var key = $(this).val();
                    if ($('#op-claim-item-' + key).css('display') != 'none') {
                        $(this).prop('checked', true);
                    }
                });
            }
        });

		$('body').on('click', 'input[name=adminClaimApplyKey]', function() {
			var itemId = $(this).data('item-id');
			var itemOptions = $(this).data('item-options');

			// 해당 주상품의 체크여부에 따라 추가구성상품도 체크 처리
			$('input[name="adminClaimApplyKey"][data-parent-item-id="'+ itemId +'"][data-parent-item-options="'+ itemOptions +'"][data-addition-item-flag="Y"]').prop('checked', $(this).prop('checked'));
		});

        /**
         1 : 취소 - CJH 2016.11.17 관리자 접수는 입금대기상태의 주문은 재외
         2 : 반품
         3 : 교환
         */
        $('body').on('click', 'input[name="claimType"]', function(){

            var claimType = $(this).val();
            var claimItems = $('input[name="adminClaimApplyKey"]');
            $('.op-claim-item').hide();
            $('#op-claim-item-table').hide();
            $('.op-claim-type-info').hide();

            var count = 0;
            $.each(claimItems, function(){
                var orderStatus = $(this).data('orderStatus');
                var key = $(this).val();
                if (claimType == '1' && (orderStatus == '10' || orderStatus == '20')) {
                    $('#op-claim-item-' + key).show();
                    $('.op-claim-type-cancel').show();

                    count++;
                } else if ((claimType == '2' || claimType == '3') && (orderStatus == '30' || orderStatus == '35' || orderStatus == '40' || orderStatus == '55' || orderStatus == '59' || orderStatus == '69')) {
                    $('#op-claim-item-' + key).show();

                    if (claimType == '2') {
                        $('.op-claim-type-return').show();
                    } else {
                        $('.op-claim-type-exchange').show();
                    }

                    count++;
                }
            });

            if (count > 0) {
                $('#op-claim-item-table').show();
                $('.op-claim-message').hide();
            } else {
                $('.op-claim-message').html('신청 가능한 상품이 없습니다.').show();
            }

        });

        $('body').on('click', '#return_all', function(){
            $('input[name="returnIds"]').prop('checked', $(this).prop('checked'));
        })

        $('body').on('click', '.returnReason', function(){
            var claimCode = $(this).data('claimCode');
            var disabled = $(this).val() == '2' ? false : true;
            $('input[name="returnApplyMap['+ claimCode +'].collectionShippingAmount"]').prop('disabled', disabled);
        });

        $('body').on('click', '#exchange_all', function(){
            $('input[name="exchangeIds"]').prop('checked', $(this).prop('checked'));
        });

        $('body').on('click', '#cancel_all', function(){
            $('input[name="cancelIds"]').prop('checked', $(this).prop('checked'));
            rePayShippingView();
        });

        $('body').on('click', '.rePayShipping', function(){
            rePayShippingView();
        });

        $('#shippingInfoForm').validator(function() {
            $.post('/opmanager/order/${pageType}/order-info/change' , $('#shippingInfoForm').serialize(), function(response) {
                if (response.isSuccess) {
                    //alert('저장완료!!');
                } else {
                    alert(response.errorMessage);
                    orderInfoReload();
                }
            }, 'json');

            return false;
        })

        $('#orderAdminMemoForm').validator(function() {
            $.post('/opmanager/order/${pageType}/admin-memo/change' , $('#orderAdminMemoForm').serialize(), function(response) {
                if (response.isSuccess) {
                    alert('저장완료!!');
                } else {
                    alert(response.errorMessage);
                }
            }, 'json');

            return false;
        })


        $('#claimMemoForm').validator(function() {
            $.post('/opmanager/order/${pageType}/claim-memo/create' , $('#claimMemoForm').serialize(), function(response) {
                if (response.isSuccess) {
                    claimMemoList('${order.orderCode}', 1);
                    $('#claimMemoForm').find('textarea[name="memo"]').val('');
                } else {
                    alert(response.errorMessage);
                }
            }, 'json');

            return false;
        });

        returnFormValidator();
        exchangeFormValidator();
        cancelFormValidator();
        adminClaimApplyFormValidator();
        claimMemoList('${order.orderCode}', 1);
        showTab();
    });

    function showTab() {
        $('.jq_tabonoff').delegate('.jq_tab>li', 'click', function() {
            var index = $(this).parent().children().index(this);
            $(this).siblings().removeClass();
            $(this).addClass('on');
            $(this).parent().next('.jq_cont').children().hide().eq(index).show();
        });
    }

    function rePayShippingView() {
        $.post('/opmanager/order/${pageType}/re-shipping-amount', $("#cancelForm").serialize(), function(response){
            Common.responseHandler(response, function(response) {

                $('.re-pay-shipping-text').html('');
                if (response.data == null || response.data.length == 0) {
                    return;
                }

                $.each(response.data, function(i, item){

                    var text = "";
                    if (item.addPayAmount > 0) {
                        text += Common.numberFormat(item.addPayAmount) + "원 " + (item.addPaymentType == '1' ? '추가' : '환불');
                    }

                    if (item.addPaymentType == '1' || item.shippingPaymentType == '2') {

                        var checekd = "";
                        if (item.shippingPaymentType == '2') {
                            checekd = 'checked="checked"';
                        }

                        text += '<label>착불 <input type="checkbox" class="rePayShipping" name="cancelShippingMap['+ item.shippingSequence +'].rePayShippingPaymentType" value="2" '+ checekd +'/></label>';
                    }

                    $('#re-pay-shipping-'+ item.shippingSequence +'-text').html(text);
                })

            })
        })
    }

    function itemDetailView() {
        var param = {
            'orderCode'		: '${order.orderCode}',
            'orderSequence'	: Number('${order.orderSequence}')
        }

        $.post(Manager.Order.url('/order/${pageType}/item-detail/${order.orderSequence}/${order.orderCode}'), param, function(html){
            $('.op-item-detail-area').html(html);

            returnFormValidator();
            exchangeFormValidator();
            cancelFormValidator();
            adminClaimApplyFormValidator();
            showTab();

        }, 'html');

		location.reload();

    }

    function cancelFormValidator() {
        $('#cancelForm').validator(function() {

            if ($('input[name="cancelIds"]:checked').size() == 0) {
                alert('취소 처리하실 주문을 선택해 주세요.');
                return false;
            }

            $.each($('input._number'), function(){
                if ($(this).val() == '') {
                    $(this).val(0);
                }
            });

	        var isSuccess = true;
            var statusList = [];
	        $.each($('input[name="cancelIds"]:checked'), function(){
		        var key = $(this).val();

		        var status = $('input[name="cancelApplyMap['+ key +'].claimStatus"]:checked').val();
                statusList.push(status);
		        if (typeof status == 'undefined' || status == '') {
			        alert('처리구분을 선택해 주세요.');
                    statusList = [];
			        $('select[name="cancelApplyMap['+ key +'].claimStatus"]').focus();
			        isSuccess = false;
			        return false;
		        }
	        });

            // 선택한 처리구분 값 중 서로 다른 값이 있다면
            if(statusList.reduce(function(a,b){if(a.indexOf(b)<0)a.push(b);return a;},[]).length > 1){
                alert('서로 다른 처리구분으로 일괄처리 할 수 없습니다.');
                statusList = [];
                return false;
            }

	        if (isSuccess == false) {
		        return false;
	        }


	        /* 취소처리시 alert 추가 2017-06-13 yulsun.yoo */
            if (confirm('취소 처리 하시겠습니까?')) {
                $.post(Manager.Order.url('/order/${pageType}/claim/cancel/process'), $("#cancelForm").serialize(), function(response) {
                    Common.responseHandler(response, function(response) {

                        itemDetailView();
                    })
                });
            } else {
                return false;

            }

            return false;
        });
    }

    function adminClaimApplyFormValidator() {
        $('#op-admin-claim-form').validator(function() {

            var mode = '';
            var claimType = $('input[name="claimType"]:checked', $('#op-admin-claim-form')).val();
            if (claimType == '2') {
                mode = "반품";
                $('input[name="returnClaimReasonText"]', $('#op-admin-claim-form')).val($('select[name="returnClaimReason"]', $('#op-admin-claim-form')).find(':selected').text());
            } else if (claimType == '3') {
                mode = "교환";
                $('input[name="exchangeClaimReasonText"]', $('#op-admin-claim-form')).val($('select[name="exchangeClaimReason"]', $('#op-admin-claim-form')).find(':selected').text());
            } else {
                mode = "취소";
                $('input[name="cancelClaimReasonText"]', $('#op-admin-claim-form')).val($('select[name="cancelClaimReason"]', $('#op-admin-claim-form')).find(':selected').text());
            }

            if ($('input[name="adminClaimApplyKey"]:checked', $('#op-admin-claim-form')).size() == 0) {
                alert(mode + ' 처리하실 주문을 선택해 주세요.');
                return false;
            }

            if (!confirm('선택하신 상품을 ' + mode + '처리 하시겠습니까?')) {
                return false;
            }
        });
    }

    function exchangeFormValidator() {
        $('#exchangeForm').validator(function() {

            if ($('input[name="exchangeIds"]:checked').size() == 0) {
                alert('교환 처리하실 주문을 선택해 주세요.');
                return false;
            }

            $.each($('input._number'), function(){
                if ($(this).val() == '') {
                    $(this).val(0);
                }
            });

            var isSuccess = true;
            var statusList = [];
            $.each($('input[name="exchangeIds"]:checked'), function(){
                var status = $('input[name="exchangeApplyMap['+ $(this).val() +'].claimStatus"]:checked').val();
                statusList.push(status);
	            if (typeof status == 'undefined' || status == '') {
		            alert('처리구분을 선택해 주세요.');
                    statusList = [];
		            $('select[name="exchangeApplyMap['+ $(this).val() +'].claimStatus"]').focus();
		            isSuccess = false;
		            return false;
	            }

	            if (status == '03') {
                    if ($('select[name="exchangeApplyMap['+ $(this).val() +'].exchangeDeliveryCompanyId"]').val() == 0) {
                        alert('재발송 택배사를 선택해 주세요.');
                        $('select[name="exchangeApplyMap['+ $(this).val() +'].exchangeDeliveryCompanyId"]').focus();
                        isSuccess = false;
                    }

                    if ($('input[name="exchangeApplyMap['+ $(this).val() +'].exchangeDeliveryNumber"]').val() == '') {
                        alert('재발송 송장번호를 입력해 주세요.');
                        $('input[name="exchangeApplyMap['+ $(this).val() +'].exchangeDeliveryNumber"]').focus();
                        isSuccess = false;
                    }
                }
            });

            // 선택한 처리구분 값 중 서로 다른 값이 있다면
            if(statusList.reduce(function(a,b){if(a.indexOf(b)<0)a.push(b);return a;},[]).length > 1){
                alert('서로 다른 처리구분으로 일괄처리 할 수 없습니다.');
                statusList = [];
                return false;
            }

            if (isSuccess == false) {
                return false;
            }

            if (!confirm('교환 처리 하시겠습니까?')) {
                return false;
            }

            $.post(Manager.Order.url('/order/${pageType}/claim/exchange/process'), $("#exchangeForm").serialize(), function(response){
                Common.responseHandler(response, function(response) {

                    itemDetailView();

                    alert('처리되었습니다.');

                })
            });

            return false;
        });
    }

    function setSeparateCharges() {
        $form = $('#return-process-form');
        $checkbox = $form.find('input[name="separateCharges"]');
        var data = $checkbox.data();
        if ($checkbox.prop('checked') == true) { // 추가금 별도 청구
            $form.find('span#totalReturnAmount').html(Common.numberFormat(data.returnAmount));
        } else {
            $form.find('span#totalReturnAmount').html(Common.numberFormat(data.returnAmount - data.addAmount));
        }
    }

    function returnFormView() {

        $('#layer_return').remove();

        $.ajax({
            type 		: 'POST',
            url			: Manager.Order.url('/order/${pageType}/claim/return/view'),
            data		: $("#returnForm").serialize(),
            dataType	: 'html',
            success		: function(html) {

                $layer = $('#layer_return');
                if ($layer.size() == 0) {
                    $('body').append($('<div id="layer_return" class="order_return_layer" />'));
                    $layer = $('#layer_return');
                }

                var windowHeight = $(window).height() / 2;
                $layer.css({'height' : windowHeight - 20, 'overflow-x' : 'scroll'});
                $layer.css({'top' : windowHeight - ($layer.height() / 2) + "px"});
                Common.dimmed.show();
                $layer.html(html).show();

                Common.addNumberComma();
                returnProcessFormValidator();
                returnAddPaymentChange();

            },
            error		: function(request, status, error) {
                alert('잘못된 접근입니다.');
            }
        });
    }

    function returnProcessFormValidator() {

        $('#return-process-form').validator(function() {
            Common.removeNumberComma();

            $.each($('#return-process-form').find('input._number_comma'), function(){
                if ($.trim($(this).val()) == '') {
                    $(this).val(0);
                }
            });

            $.post(Manager.Order.url('/order/${pageType}/claim/return/process'), $("#return-process-form").serialize(), function(response){

                itemDetailView();
                Shop.closeOrderLayer('return');

            }, 'json');

            return false;
        });
    }

    function returnAddPaymentChange() {

        $('.add-amount').on('focusout', function() {
            var addAmount = 0;
            $.each($('.add-amount'), function() {
                addAmount += Number($(this).val());
            });

            $form = $('#return-process-form');
            var returnAmount = Number($form.find('input[name="returnAmount"]').val());
            if (returnAmount < addAmount) {
                alert('환불금 총액보다 추가금을 더 청구하실수 없습니다.');

                $(this).val(0);
                $(this).focus();
            } else {

                $form.find('span#totalAddAmount').html(Common.numberFormat(addAmount));
                $checkbox = $form.find('input[name="separateCharges"]');
                $checkbox.data('addAmount', addAmount);
                setSeparateCharges()
            }
        });

    }

    function returnFormValidator() {
        $('#returnForm').validator(function() {

            if ($('input[name="returnIds"]:checked').size() == 0) {
                alert('환불 처리하실 주문을 선택해 주세요.');
                return false;
            }

            $.each($('input._number'), function(){
                if ($(this).val() == '') {
                    $(this).val(0);
                }
            });

            var isView = false;
	        var isSuccess = true;

            // 서로 다른 seller 는 묶음반품 처리 불가능
            var sellers = new Array();
            $.each($('input[name="returnIds"]:checked'), function(){
                var key = $(this).val();
                sellers.push($("input[name='returnApplyMap["+key+"].shipmentReturnSellerId']")[0].value);
                if(sellers.length > 1 && isSuccess){
                    var t = new Array();
                    sellers.reduce(function(a,b){
                        if(t.length > 0 && t.indexOf(b) < 0){
                            alert("서로 다른 판매자는 묶음 반품처리 하실 수 없습니다.");
                            isSuccess = false;
                            return false;
                        }
                        t.push(b);
                    },[]);
                }
            });
            if(!isSuccess){
                return false;
            }
            var statusList = [];
	        $.each($('input[name="returnIds"]:checked'), function(){
		        var key = $(this).val();

		        var status = $('input[name="returnApplyMap['+ key +'].claimStatus"]:checked').val();
                statusList.push(status);
		        if (typeof status == 'undefined' || status == '') {
			        alert('처리구분을 선택해 주세요.');
                    statusList = [];
			        $('select[name="returnApplyMap['+ key +'].claimStatus"]').focus();
			        isSuccess = false;
			        return false;
		        }
	        });

            // 선택한 처리구분 값 중 서로 다른 값이 있다면
            if(statusList.reduce(function(a,b){if(a.indexOf(b)<0)a.push(b);return a;},[]).length > 1){
                alert('서로 다른 처리구분으로 일괄처리 할 수 없습니다.');
                statusList = [];
                return false;
            }

	        if (isSuccess == false) {
		        return false;
	        }


	        $.each($('input[name="returnIds"]:checked'), function(){
		        var key = $(this).val();

		        if ($('input[name="returnApplyMap['+ key +'].claimStatus"]:checked').val() == '03') {
			        isView = true;
			        return false;
		        }
	        });

            if (!confirm('반품 처리 하시겠습니까?')) {
                return false;
            }

            if (isView) {
                returnFormView();
            } else {

                $.ajax({
                    type 		: 'POST',
                    url			: Manager.Order.url('/order/${pageType}/claim/return/save'),
                    data		: $("#returnForm").serialize(),
                    dataType	: 'json',
                    success		: function(response) {

                        itemDetailView();

                        alert('처리되었습니다.');

                    },
                    error		: function(request, status, error) {
                        if (request.status == '1000') {
                            returnFormView();
                        } else {
                            alert('잘못된 접근입니다.')
                        }
                    }
                })

            }

            return false;
        });
    }

    function orderInfoReload() {
        var param = {
            'orderCode'			: '${order.orderCode}',
            'orderSequence'		: '${order.orderSequence}'
        }

        $.post('/opmanager/order/${pageType}/order-info/reload' , param, function(html) {
            $('div#order-info-area').html(html);
        }, 'html');
    }

    function claimMemoList(orderCode, page) {
        if (page == undefined) {
            page = 1;
        }

        var param = {
            'orderCode' : orderCode,
            'page'		: page
        };

        $.post('/opmanager/order/${pageType}/claim-memo/list', param, function(html) {
            $('#claim-memo-list').html(html);
        });
    }

    //다음 우편번호 검색
    function openDaumPostcode(index) {

        newZipcode 		= "orderShippingInfos["+index+"].receiveNewZipcode";
        zipcode 		= "orderShippingInfos["+index+"].receiveZipcode";
        zipcode1 		= "orderShippingInfos["+index+"].receiveZipcode1";
        zipcode2 		= "orderShippingInfos["+index+"].receiveZipcode2";
        address 		= "orderShippingInfos["+index+"].receiveAddress";
        addressDetail 	= "orderShippingInfos["+index+"].receiveAddressDetail";
        sido			= "orderShippingInfos["+index+"].receiveSido";
        sigungu			= "orderShippingInfos["+index+"].receiveSigungu";
        eupmyeondong	= "orderShippingInfos["+index+"].receiveEupmyeondong";

        var tagNames = {
            'newZipcode'			: newZipcode,
            'zipcode' 				: zipcode,
            'zipcode1' 				: zipcode1,
            'zipcode2' 				: zipcode2,
            'sido'					: sido,
            'sigungu'				: sigungu,
            'eupmyeondong'			: eupmyeondong,
            'jibunAddress'			: address,
            'jibunAddressDetail' 	: addressDetail
        }

        openDaumAddress(tagNames, function(data){

        });

    }

    //다음 우편번호 검색
    function openDaumPostcodeForReturn(index) {

        zipcode 		= "returnApplyMap["+index+"].returnReserveZipcode";
        address 		= "returnApplyMap["+index+"].returnReserveAddress";
        addressDetail 	= "returnApplyMap["+index+"].returnReserveAddress2";
        sido			= "returnApplyMap["+index+"].returnReserveSido";
        sigungu			= "returnApplyMap["+index+"].returnReserveSigungu";
        eupmyeondong	= "returnApplyMap["+index+"].returnReserveEupmyeondong";

        var tagNames = {
            'newZipcode' 				: zipcode,
            'sido'					: sido,
            'sigungu'				: sigungu,
            'eupmyeondong'			: eupmyeondong,
            'roadAddress'			: address,
            'jibunAddressDetail' 	: addressDetail
        }

        openDaumAddress(tagNames, function(data){

        });

    }

    function openDaumPostcodeForExchange(index) {

        zipcode 		= "exchangeApplyMap["+index+"].exchangeReceiveZipcode";
        address 		= "exchangeApplyMap["+index+"].exchangeReceiveAddress";
        addressDetail 	= "exchangeApplyMap["+index+"].exchangeReceiveAddress2";
        sido			= "exchangeApplyMap["+index+"].exchangeReceiveSido";
        sigungu			= "exchangeApplyMap["+index+"].exchangeReceiveSigungu";
        eupmyeondong	= "exchangeApplyMap["+index+"].exchangeReceiveEupmyeondong";

        var tagNames = {
            'newZipcode' 				: zipcode,
            'sido'					: sido,
            'sigungu'				: sigungu,
            'eupmyeondong'			: eupmyeondong,
            'roadAddress'			: address,
            'jibunAddressDetail' 	: addressDetail
        }

        openDaumAddress(tagNames, function(data){

        });

    }

    function tracking(url, number) {
        Common.popup(url + number, 'shipping-tracking', 800, 600);
    }
</script>
