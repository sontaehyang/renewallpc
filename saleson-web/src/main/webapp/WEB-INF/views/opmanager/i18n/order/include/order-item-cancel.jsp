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
    .inDiv {
        width:100%;
        margin:2px 0;
        padding:0;
        float:left;
    }
</style>
<c:if test="${not empty cancelHistorys}">
    <h3>취소 내역 (거절, 완료, 환불대기)</h3>
    <table class="inner-table">
        <caption>${op:message('M00059')}</caption>
        <!-- 주문정보 -->
        <colgroup>
            <col />
            <col style="width: 120px" />
            <col style="width: 120px" />
            <col style="width: 100px" />
            <col style="width: 150px" />
        </colgroup>
        <thead>
        <tr>
            <th scope="col" class="none_left">상품정보</th>
            <th scope="col" class="none_left">구분</th>
            <th scope="col" class="none_left">클레임수량</th>
            <th scope="col" class="none_left">상태</th>
            <th scope="col" class="none_left">신청일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cancelHistorys}" var="history">
            <tr>
                <td>
                    [${history.orderItem.itemUserCode}] ${history.orderItem.itemName}
                        ${ shop:viewOptionText(history.orderItem.options)}

                        <c:forEach items="${history.orderItem.additionItemList}" var="addition">
                            <c:if test="${history.orderItem.itemId == addition.parentItemId && history.orderItem.options == addition.parentItemOptions}">

                                추가구성품 : ${addition.itemName} ${addition.quantity}개 (+${op:numberFormat(addition.itemAmount)}원) <br />
                            </c:if>
                        </c:forEach>

                        ${ shop:viewOrderGiftItemList(history.orderItem.orderGiftItemList) }

                    <c:if test="${history.claimStatus == '99' && not empty history.cancelRefusalReasonText}">
                        <p style="color:red">거절 사유 : ${history.cancelRefusalReasonText}</p>
                    </c:if>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${shop:sellerId() == history.orderItem.sellerId}">자사</c:when>
                        <c:otherwise>
                            <span class="glyphicon glyphicon-user"></span>${history.orderItem.sellerName}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-right">${op:numberFormat(history.claimApplyQuantity)}개</td>
                <td class="text-center">${history.claimStatusLabel}</td>
                <td class="text-center">${op:datetime(history.createdDate)}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<form id="cancelForm">
    <h3 class="mt10">취소 처리</h3>
    <input type="hidden" name="orderCode" value="${order.orderCode}" />
    <input type="hidden" name="orderSequence" value="${order.orderSequence}" />
    <table class="inner-table">
        <caption>${op:message('M00059')}</caption>
        <!-- 주문정보 -->
        <colgroup>
            <col style="width: 30px" />
            <col style="width: 80px" />
            <col />
            <col style="width: 120px" />
            <col style="width: 120px" />
            <col style="width: 100px" />
            <col style="width: 120px" />
            <col style="width: 120px" />
            <%-- <col style="width: 120px" /> --%>
        </colgroup>
        <thead>
        <tr>
            <th scope="col" class="none_left"><input type="checkbox" id="cancel_all" title="체크박스" /></th>
            <th scope="col" class="none_left">이미지</th>
            <th scope="col" class="none_left">상품정보</th>
            <th scope="col" class="none_left">구분</th>
            <th scope="col" class="none_left">클레임수량</th>
            <th scope="col" class="none_left">환불금액(상품)</th>
            <th scope="col" class="none_left">상태</th>
            <th scope="col" class="none_left">신청일</th>
            <!-- <th scope="col" class="none_left">배송비</th> -->
        </tr>
        </thead>
        <tbody>
        <c:set var="cancelCount">0</c:set>
        <c:forEach items="${activeCancels}" var="group" varStatus="groupIndex">
            <c:forEach items="${group.orderItems}" var="orderItem" varStatus="orderItemIndex">
                <c:set var="apply" value="${orderItem.cancelApply}" />
                <c:set var="flag" value="${orderItem.additionItemFlag}" />

                <c:set var="totalClaimApplyAmount" value="${orderItem.cancelApply.claimApplyAmount}" />
                <c:forEach items="${orderItem.additionItemList}" var="addition">
                    <c:set var="totalClaimApplyAmount">${totalClaimApplyAmount + addition.cancelApply.claimApplyAmount}</c:set>
                </c:forEach>

                <tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>
                    <td>
                        <c:if test="${shop:sellerId() == orderItem.sellerId || requestContext.opmanagerPage}">
                            <input type="checkbox" name="cancelIds" class="rePayShipping" value="${apply.claimCode}" />
                            <input type="hidden" name="cancelApplyMap[${apply.claimCode}].itemSequence" value="${orderItem.itemSequence}" />
                            <input type="hidden" name="cancelApplyMap[${apply.claimCode}].claimCode" value="${apply.claimCode}" />
                            <input type="hidden" name="cancelApplyMap[${apply.claimCode}].orderSequence" value="${orderItem.orderSequence}" />
                            <input type="hidden" name="cancelApplyMap[${apply.claimCode}].orderCode" value="${orderItem.orderCode}" />
                            <input type="hidden" name="cancelApplyMap[${apply.claimCode}].claimApplyQuantity" value="${orderItem.cancelApply.claimApplyQuantity}" />
                        </c:if>
                    </td>
                    <td>
                        <img src="${shop:loadImageBySrc(orderItem.imageSrc,'XS')}" alt="${orderItem.itemName}" width="100%"/>
                    </td>
                    <td>
                        [${orderItem.itemUserCode}] ${orderItem.itemName}
                        ${ shop:viewOptionText(orderItem.options) }
                        ${shop:viewAdditionOrderItemList(orderItem.additionItemList)}
                        ${ shop:viewOrderGiftItemList(orderItem.orderGiftItemList) }
                    </td>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${shop:sellerId() == orderItem.sellerId}">자사</c:when>
                            <c:otherwise>
                                <span class="glyphicon glyphicon-user"></span>${orderItem.sellerName}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-right">
                        ${op:numberFormat(orderItem.cancelApply.claimApplyQuantity)}개
                    </td>
                    <td class="text-right">
                        ${op:numberFormat(totalClaimApplyAmount)}원
                    </td>
                    <td class="text-center">
                        ${orderItem.cancelApply.claimStatusLabel}
                    </td>
                    <td class="text-center">
                        ${op:datetime(orderItem.cancelApply.createdDate)}
                    </td>
                </tr>
                <c:choose>
                    <c:when test="${shop:sellerId() == orderItem.sellerId || requestContext.opmanagerPage}">
                        <tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>
                            <td colspan="8">
                                <table class="inner-table">
                                    <colgroup>
                                        <col style="width: 10%" />
                                        <col />
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>신청사유</th>
                                        <td>
                                            <div style="float:left; width: 70%;">
                                                <div class="inDiv">
                                                    <c:choose>
                                                        <c:when test="${apply.claimApplySubject == '01'}">[구매자 신청] </c:when>
                                                        <c:otherwise>[판매자 신청] </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="inDiv">
                                                    <label style="width:100px"><input type="radio" name="cancelApplyMap[${apply.claimCode}].cancelReason" value="2" ${op:checked('2', apply.cancelReason)} data-claim-code="${apply.claimCode}" class="cancel-reason" /> 고객 사유</label>
                                                    <select name="cancelApplyMap[${apply.claimCode}].cancelReasonText" class="cancel-reason-text">
                                                        <c:forEach var="code" items="${cancelClaimReasons}" varStatus="i">
                                                            <option value="${code.label}" <c:if test="${code.label eq apply.cancelReasonText}">selected</c:if>>${code.label}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="inDiv">
                                                    <label style="width:100px"><input type="radio" name="cancelApplyMap[${apply.claimCode}].cancelReason" value="1" ${op:checked('1', apply.cancelReason)} data-claim-code="${apply.claimCode}" class="cancel-reason" /> 판매자 사유</label>
                                                    <input type="text" name="cancelApplyMap[${apply.claimCode}].cancelReasonDetail" maxlength="80" value="${apply.cancelReasonDetail}" class="cancel-reason-detail" style="width:80%" />
                                                </div>
                                            </div>
                                            <div style="float:left; width: 29%; text-align:left;">
                                                <c:if test="${orderItemIndex.first == true}">
                                                    <input type="hidden" name="cancelShippingMap[${group.shippingSequence}].shippingSequence" value="${group.shippingSequence}" />

                                                    <p>
                                                            ${orderItem.orderShipping.shippingTypeLabel} 정책
                                                        <c:if test="${orderItem.orderShipping.shippingType == '2' || orderItem.orderShipping.shippingType == '3' || orderItem.orderShipping.shippingType == '4'}">
                                                            <br/> ${op:numberFormat(orderItem.orderShipping.shippingFreeAmount)}이상 무료
                                                        </c:if>

                                                        <c:choose>
                                                            <c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
                                                                <br />착불 (${op:numberFormat(orderItem.orderShipping.realShipping)}원)
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:if test="${orderItem.orderShipping.payShipping > 0}">
                                                                    <br />${op:numberFormat(orderItem.orderShipping.payShipping)}원 결제
                                                                </c:if>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>

                                                    <p>
                                                        <label><input type="checkbox" name="cancelShippingMap[${group.shippingSequence}].rePayShipping" value="Y" checked="checked" class="re-pay-shipping" /> 배송비 재계산 (취소에 따른 배송료 부과 또는 환불)</label>
                                                        <label id="re-pay-shipping-${group.shippingSequence}-text" class="re-pay-shipping-text" style="color:red"></label>
                                                    </p>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="text-center">처리구분</th>
                                        <td>
                                            <p>
                                                <label><input type="radio" name="cancelApplyMap[${apply.claimCode}].claimStatus" value="02" ${op:checked('02', apply.claimStatus)} onclick="cancelCheckedDisplay('${apply.claimCode}','02')" class="claim-status" data-claim-code="${apply.claimCode}" /> 취소 보류</label>
                                                <label><input type="radio" name="cancelApplyMap[${apply.claimCode}].claimStatus" value="99" ${op:checked('99', apply.claimStatus)} onclick="cancelCheckedDisplay('${apply.claimCode}','99')" class="claim-status" data-claim-code="${apply.claimCode}" /> 취소 거절</label>
                                                <label><input type="radio" name="cancelApplyMap[${apply.claimCode}].claimStatus" value="98" ${op:checked('98', apply.claimStatus)} onclick="cancelCheckedDisplay('${apply.claimCode}','98')" class="claim-status" data-claim-code="${apply.claimCode}" /> 취소 거절 + 배송 처리</label>
                                                <label><input type="radio" name="cancelApplyMap[${apply.claimCode}].claimStatus" value="03" ${op:checked('03', apply.claimStatus)} onclick="cancelCheckedDisplay('${apply.claimCode}','03')" class="claim-status" data-claim-code="${apply.claimCode}" /> 취소 승인</label>
                                            </p>
                                            <p id="redText_${apply.claimCode}" style="margin-top:5px;color:red"></p>
                                        </td>
                                    </tr>
                                    <tr id="cancelClaimInfo1_${apply.claimCode}" style="display:none">
                                        <th class="text-center">거절사유</th>
                                        <td>
                                            <input type="text" class="form-block cancel-refusal-reason-text" maxlength="80" name="cancelApplyMap[${apply.claimCode}].cancelRefusalReasonText" />
                                        </td>
                                    </tr>
                                    <tr id="cancelClaimInfo2_${apply.claimCode}" style="display:none">
                                        <th class="text-center">배송정보</th>
                                        <td>
                                            <p class="mb5 mt10">
                                                택배사 : <select name="cancelApplyMap[${apply.claimCode}].deliveryCompanyId" class="delivery-company-id">
                                                <option value="0">선택</option>
                                                <c:forEach items="${deliveryCompanyList}" var="deliveryCompany">
                                                    <option value="${deliveryCompany.deliveryCompanyId}">${deliveryCompany.deliveryCompanyName}</option>
                                                </c:forEach>
                                            </select>
                                                송장번호 : <input type="text" name="cancelApplyMap[${apply.claimCode}].deliveryNumber" maxlength="30" class="six delivery-number" />
                                            </p>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>
                            <td colspan="8">
                                <table class="inner-table">
                                    <colgroup>
                                        <col style="width: 10%" />
                                        <col />
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>신청사유</th>
                                        <td>
                                            <div style="float:left; width: 70%;">
                                                <div class="inDiv">
                                                    <c:choose>
                                                        <c:when test="${apply.claimApplySubject == '01'}">[구매자 신청] </c:when>
                                                        <c:otherwise>[판매자 신청] </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="inDiv">
                                                    <label style="width:100px"><input type="radio" name="cancelApplyMap[${apply.claimCode}].cancelReason" value="2" ${op:checked('2', apply.cancelReason)} class="cancel-reason" data-claim-code="${apply.claimCode}" /> 고객 사유</label>
                                                    <select name="cancelApplyMap[${apply.claimCode}].cancelReasonText" class="cancel-reason-text">
                                                        <c:forEach var="code" items="${cancelClaimReasons}" varStatus="i">
                                                            <option value="${code.label}" <c:if test="${code.label eq apply.cancelReasonText}">selected</c:if>>${code.label}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="inDiv">
                                                    <label style="width:100px"><input type="radio" name="cancelApplyMap[${apply.claimCode}].cancelReason" value="1" ${op:checked('1', apply.cancelReason)} class="cancel-reason" data-claim-code="${apply.claimCode}" /> 판매자 사유</label>
                                                    <input type="text" name="cancelApplyMap[${apply.claimCode}].cancelReasonDetail" maxlength="80" value="${apply.cancelReasonDetail}" class="cancel-reason-detail" style="width:80%" />
                                                </div>
                                            </div>
                                            <div style="float:left; width: 29%; text-align:left;">
                                                <c:if test="${orderItemIndex.first == true}">
                                                    <input type="hidden" name="cancelShippingMap[${group.shippingSequence}].shippingSequence" value="${group.shippingSequence}" />

                                                    <p>
                                                            ${orderItem.orderShipping.shippingTypeLabel} 정책
                                                        <c:if test="${orderItem.orderShipping.shippingType == '2' || orderItem.orderShipping.shippingType == '3' || orderItem.orderShipping.shippingType == '4'}">
                                                            <br/> ${op:numberFormat(orderItem.orderShipping.shippingFreeAmount)}이상 무료
                                                        </c:if>

                                                        <c:choose>
                                                            <c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
                                                                <br />착불 (${op:numberFormat(orderItem.orderShipping.realShipping)}원)
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:if test="${orderItem.orderShipping.payShipping > 0}">
                                                                    <br />${op:numberFormat(orderItem.orderShipping.payShipping)}원 결제
                                                                </c:if>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>

                                                    <p>
                                                        <label><input type="checkbox" name="cancelShippingMap[${group.shippingSequence}].rePayShipping" value="Y" checked="checked" class="re-pay-shipping" /> 배송비 재계산 (취소에 따른 배송료 부과)</label>
                                                        <label id="re-pay-shipping-${group.shippingSequence}-text" class="re-pay-shipping-text" style="color:red"></label>
                                                    </p>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="text-center">처리구분</th>
                                        <td colspan="3">
                                                ${apply.claimStatusLabel}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>

    <c:choose>
        <c:when test="${empty activeCancels}">
            <div class="no_content">
                    ${op:message('M00473')} <!-- 데이터가 없습니다. -->
            </div>
        </c:when>
        <c:otherwise>
            <div class="popup_btns" style="clear:both">
                <button type="submit" class="btn btn-active">취소 주문 처리</button>
            </div>
        </c:otherwise>
    </c:choose>
</form>
<script type="text/javascript">
    var cmessage1 = "!!처리할 항목을 선택하세요";
    var cmessage2 = "!!취소신청을 보류합니다. 차후에 다시 처리할 수 있습니다.<br/>!!사용자화면에는 '취소처리중'으로 표기됩니다.";
    var cmessage3 = "!!취소신청을 거절합니다. 신청은 철회되어 해당 주문건은 신규목록으로 이동합니다.<br/>!!사용자화면에는 '결제완료'로 표기됩니다.";
    var cmessage4 = "!!취소신청을 거절합니다. 신청은 철회되어 해당 주문건은 배송중으로 이동합니다.<br/>!!사용자화면에는 '배송중'로 표기됩니다.";
    var cmessage5 = "!!취소신청을 승인하면 환불내역으로 이동합니다.<br/>!!환불이 처리될때까지 사용자화면에는 '취소처리중'으로 표기됩니다.";

    $(function(){
        $.each($("input[name$='].claimCode']"),function(){
            var val = $(this).val();
            var checkedVal = $("input:radio[name='cancelApplyMap["+val+"].claimStatus']:checked").val();

            if(checkedVal == "02"){
                $("#redText_"+val).html(cmessage2);
            }else if(checkedVal == "99"){
                $("#redText_"+val).html(cmessage3);
            }else if(checkedVal == "98"){
                $("#redText_"+val).html(cmessage4);
            }else if(checkedVal == "03"){
                $("#redText_"+val).html(cmessage5);
            }else{
                $("#redText_"+val).html(cmessage1);
            }
        });

        $('tr.parent :checkbox').on('change', function() {
            var className = $(this).closest('tr.parent').data('class');
            $('tr.child[data-class="' + className + '"] :checkbox').prop('checked', $(this).prop('checked'));
        });

        $('tr.parent :radio').on('change', function() {
            var parentClass = $(this).closest('tr.parent').data('class');
            var className = $(this).attr('class');
            var value = $(this).val();

            var $selector = $('tr.child[data-class="' + parentClass + '"] :radio.' + className + '[value=' +value + ']');
            $selector.prop('checked', $(this).prop('checked'));

            if (className == 'claim-status') {
                $.each($selector, function () {
                    cancelCheckedDisplay($(this).data('claim-code'), value);
                });
            }
        });

        $('tr.parent select').on('change', function() {
            var parentClass = $(this).closest('tr.parent').data('class');
            var className = $(this).attr('class');
            var value = $(this).val();

            $('tr.child[data-class="' + parentClass + '"] select.' + className).val(value).prop('selected', true);
        });

        $('tr.parent input[type=text]').on('keyup', function() {
            var parentClass = $(this).closest('tr.parent').data('class');
            var className = $(this).attr('class').replace('form-block ', '').replace('six ', '').replace('required ', '');
            var value = $(this).val();

            $('tr.child[data-class="' + parentClass + '"] input[type=text].' + className).val(value);
        });


        /* 취소처리 되지 않아서 숨김 처리 2017-06-13 yulsun.yoo
        $("#cancelForm").validator({
            'submitHandler' : function() {
                if (!confirm('취소 처리 하시겠습니까?')) {
                    return false;
                }
            }
        });  */
    });

    function cancelCheckedDisplay(claimCode, value) {
        $("#cancelClaimInfo1_"+claimCode).hide();
        $("#cancelClaimInfo2_"+claimCode).hide();

        if(value == "02"){
            $("#redText_"+claimCode).html(cmessage2);
        }else if(value == "99"){
            $("#redText_"+claimCode).html(cmessage3);
            $("#cancelClaimInfo1_"+claimCode).show();
        }else if(value == "98"){
            $("#redText_"+claimCode).html(cmessage4);
            $("#cancelClaimInfo1_"+claimCode).show();
            $("#cancelClaimInfo2_"+claimCode).show();
        }else if(value == "03"){
            $("#redText_"+claimCode).html(cmessage5);
        }
    }
</script>