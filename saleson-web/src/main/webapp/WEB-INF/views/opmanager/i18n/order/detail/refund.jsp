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

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<div class="board_write">
	<h3 class="mt10">환불 신청 정보</h3>
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
				<td><div><a href="javascript:Manager.orderDetails('refund', '${refund.orderSequence}', '${refund.orderCode}', '1')">${refund.orderCode}</a></div></td>
				<th class="label">환불코드</th>
				<td><div>${refund.refundCode}</div></td>
			</tr>

			<tr>
				<th class="label">신청일</th>
				<td><div>${op:datetime(refund.createdDate)}</div></td>
				<th class="label">신청자</th>
				<td><div>${refund.requestManagerUserName}</div></td>
			</tr>

			<tr>
				<th class="label">회원정보</th>
				<td colspan="3">
					<div>
						<c:choose>
							<c:when test="${refund.userId > 0}">
								${refund.userName} [${refund.loginId}]
								<a href="javascript:Manager.userDetails(${refund.userId})" class="btn btn-gradient btn-xs">CRM</a>
							</c:when>
							<c:otherwise>${refund.buyerName} [비회원]</c:otherwise>
						</c:choose>
					</div>
				</td>

			</tr>

		</tbody>
	</table>
</div>

<div class="board_list">

	<c:forEach items="${refund.groups}" var="group">
		<div style="border:1px solid #a29b9b;padding:10px;" class="mt10">
			<h3>[${group.seller.sellerName}] 정보</h3>
			<table class="inner-table">
				<colgroup>
					<col style="width:15%;">
					<col style="width:35%;">
					<col style="width:15%;">
					<col style="width:35%;">
				</colgroup>
				<tbody>
					<tr>
						<th>담당자 정보</th>
						<td>${group.seller.userName} / ${group.seller.telephoneNumber} / ${group.seller.phoneNumber}</td>
						<th>주문 담당자 정보</th>
						<td>
							${group.seller.secondUserName} / ${group.seller.secondTelephoneNumber} / ${group.seller.secondPhoneNumber}
						</td>
					</tr>
					<tr>
						<th>총 환불금액</th>
						<td colspan="3" class="text-right" style="font-size:30px;"><strong>${op:numberFormat(group.returnAmount)}원</strong></td>
					</tr>
				</tbody>
			</table>

			<table class="inner-table">
				<colgroup>
					<col style="width: 120px" />
					<col style="width: 120px" />
					<col style="width: 80px" />
					<col />
					<col style="width: 120px" />
					<col style="width: 120px" />
					<col style="width: 160px" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="none_left">구분</th>
						<th scope="col" class="none_left">클레임번호</th>
						<th scope="col" class="none_left">이미지</th>
						<th scope="col" class="none_left">상품정보</th>
						<th scope="col" class="none_left">클레임수량</th>
						<th scope="col" class="none_left">환불금액(상품)</th>
						<th scope="col" class="none_left">신청일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${group.orderCancelApplys}" var="cancel">
						<c:set var="totalClaimApplyAmount" value="${cancel.claimApplyAmount}" />

						<c:forEach items="${cancel.orderItem.additionItemList}" var="addition">
							<c:set var="totalClaimApplyAmount">${totalClaimApplyAmount + addition.cancelApply.claimApplyAmount}</c:set>
						</c:forEach>

						<tr class="${cancel.orderItem.additionItemFlag == 'Y' ? 'child' : 'parent'}" ${cancel.orderItem.additionItemFlag == 'Y' ? 'style="display:none"' : ''}>
							<td class="text-center">취소 ${cancel.claimStatusLabel}</td>
							<td class="text-center">${cancel.claimCode}</td>
							<td>
								<img src="${ shop:loadImageBySrc(cancel.orderItem.imageSrc, 'XS') }" alt="${cancel.orderItem.itemName}" width="100%"/>
							</td>
							<td>
								${cancel.orderItem.itemName}
								${ shop:viewOptionText(cancel.orderItem.options) }
								${ shop:viewAdditionOrderItemList(cancel.orderItem.additionItemList) }
								${ shop:viewOrderGiftItemList(cancel.orderItem.orderGiftItemList) }
							</td>
							<td class="text-right">${op:numberFormat(cancel.claimApplyQuantity)}개</td>
							<td class="text-right">${op:numberFormat(totalClaimApplyAmount)}원</td>
							<td class="text-center">${op:datetime(cancel.createdDate)}</td>
						</tr>
					</c:forEach>
					<c:forEach items="${group.orderReturnApplys}" var="returnApply">
						<c:set var="totalClaimApplyAmount" value="${returnApply.claimApplyAmount}" />

						<c:forEach items="${returnApply.orderItem.additionItemList}" var="addition">
							<c:set var="totalClaimApplyAmount">${totalClaimApplyAmount + addition.returnApply.claimApplyAmount}</c:set>
						</c:forEach>

						<tr class="${returnApply.orderItem.additionItemFlag == 'Y' ? 'child' : 'parent'}" ${returnApply.orderItem.additionItemFlag == 'Y' ? 'style="display:none"' : ''}>
							<td class="text-center">반품 ${returnApply.claimStatusLabel}</td>
							<td class="text-center">${returnApply.claimCode}</td>
							<td>
								<img src="${ shop:loadImageBySrc(returnApply.orderItem.imageSrc, 'XS') }" alt="${returnApply.orderItem.itemName}" width="100%"/>
							</td>
							<td>
								${returnApply.orderItem.itemName}
								${ shop:viewOptionText(returnApply.orderItem.options) }
								${ shop:viewAdditionOrderItemList(returnApply.orderItem.additionItemList) }
								${ shop:viewOrderGiftItemList(returnApply.orderItem.orderGiftItemList) }
							</td>
							<td class="text-right">${op:numberFormat(returnApply.claimApplyQuantity)}개</td>
							<td class="text-right">${op:numberFormat(totalClaimApplyAmount)}원</td>
							<td class="text-center">${op:datetime(returnApply.createdDate)}</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>

			<c:if test="${not empty group.orderAddPayments}">
				<table class="inner-table">
					<colgroup>
						<col style="width: 120px" />
						<col style="width: 120px" />
						<col />
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="none_left">구분</th>
							<th scope="col" class="none_left">금액</th>
							<th scope="col" class="none_left">내용</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${group.orderAddPayments}" var="addPayment">
							<tr>
								<td class="text-center">
									<c:choose>
										<c:when test="${addPayment.addPaymentType == '1'}">추가</c:when>
										<c:otherwise>환불</c:otherwise>
									</c:choose>
								</td>
								<td class="text-right">${op:numberFormat(addPayment.amount)}원</td>
								<td class="text-center">${addPayment.subject}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</c:forEach>

</div>

<!-- 2017.04.28 Jun-Eu Son 환불받을 은행명 설정 -->
<c:forEach items="${bankListByKey}" var="bankList">
	<c:if test="${bankList.key.id eq refund.returnBankName or bankList.label eq refund.returnBankName}">
		<c:set var="returnBankName" value="${bankList.label}" />
	</c:if>
</c:forEach>

<c:choose>
	<c:when test="${refund.refundStatusCode == '1'}">
		<div class="board_list">
			<form id="changePaymentForm" method="post" enctype="multipart/form-data">
				<c:forEach var="refundPayment" items="${refund.orderPayments}">
					<c:if test="${refundPayment.approvalType == 'vbank'}">
						<input type="hidden" name="vbankFlag" value="true">
					</c:if>
					<c:if test="${refundPayment.approvalType == 'realtimebank'}">
						<input type="hidden" name="realtimebankFlag" value="true">
					</c:if>
					<c:if test="${refundPayment.approvalType == 'card'}">
						<input type="hidden" name="cardFlag" value="true">
					</c:if>
				</c:forEach>

				<c:if test="${not empty nicepayPgData}">
					<input type="hidden" name="MID" value="${nicepayPgData.pgServiceMid}" />
					<input type="hidden" name="TID" value="${nicepayPgData.pgKey}" />
					<input type="hidden" name="CancelAmt" value="${refund.groups.get(0).returnAmount}" />
					<input type="hidden" name="CancelMsg" value="환불" />
					<input type="hidden" name="CancelPwd" value="123456" /> <%--취소 패스워드(가맹점 관리 취소 패스워드 설정 시 application.properties 값 가져오기)--%>
					<input type="hidden" name="PartialCancelCode" value="${partCancel}" /> <%--부분취소 여부--%>
					<input type="hidden" name="ccPartCl" value="${nicepayPgData.partCancelFlag}" /> <%--부분취소 가능여부--%>
				</c:if>

				<table class="inner-table">
					<caption>table list</caption>
					<colgroup>
						<col style="width: 15%" />
						<col style="width: 15%" />
						<col />
					</colgroup>
					<tbody>
						<tr>
							<th colspan="2">환불 총액</th>
							<td class="text-right" style="font-size:30px;"><strong>${op:numberFormat(refund.totalReturnAmount)}원</strong></td>
						</tr>
						<%--
						<c:if test="${not empty refund.returnVirtualNo && refund.orderPayments[0].escrowStatus ne '40'}">
							<tr>
								<th class="label" rowspan="3">환불정보</th>
								<th>은행명</th>
								<td>
									<input type="text" name="returnBankName" value="${returnBankName}" style="width:100%" />
								</td>
							</tr>
							<tr>
								<th>예금주</th>
								<td>
									<input type="text" name="returnBankInName" value="${refund.returnBankInName}" style="width:100%" />
								</td>
							</tr>
							<tr>
								<th>계좌번호</th>
								<td>
									<input type="text" name="returnVirtualNo" value="${refund.returnVirtualNo}" style="width:100%" class="_number" />
								</td>
							</tr>
						</c:if>--%>
					</tbody>
				</table>

				<input type="hidden" name="orderCode" value="${refund.orderCode}"/>
				<input type="hidden" name="orderSequence" value="${refund.orderSequence}"/>

				<c:set var="orderPayments" scope="request" value="${refund.orderPayments}" />
				<c:set var="pageType" scope="request" value="refund" />
				<jsp:include page="../include/change-payment.jsp" />

                <div class="text-info mt10">
                    - 결제 수단별 부분 취소가 되지 않는 경우에는 <strong>'은행 환불'</strong>을 통해 환불 처리가 가능합니다.
                </div>

				<div class="popup_btns" style="clear:both">
					<button type="submit" class="btn btn-active"><span class="glyphicon glyphicon-ok"></span> 환불 완료</button>
					<button type="button" class="btn btn-success op-btn-cancel-refund"><span class="glyphicon glyphicon-remove"></span> 환불 신청 취소</button>
					<button type="button" onclick="Link.list('${requestContext.managerUri}/order/refund/list')" class="btn btn-default">목록</button>
				</div>
			</form>
		</div>
	</c:when>
	<c:otherwise>

		<c:set var="refundBankInfo" value=""/>
		<c:forEach var="refundPayment" items="${refund.orderPayments}">
			<c:if test='${refundPayment.approvalType == "bank" and refundPayment.paymentType == "2" and refundPayment.refundFlag == "Y"}'>
				<c:set var="refundBankInfo" value="${refundPayment.paymentSummary}"/>
			</c:if>
		</c:forEach>

		<table class="inner-table">
			<caption>table list</caption>
			<colgroup>
				<col style="width: 15%" />
				<col style="width: 15%" />
				<col />
			</colgroup>
			<tbody>
				<tr>
					<th colspan="2">환불 총액</th>
					<td class="text-right" style="font-size:30px;"><strong>${op:numberFormat(refund.totalReturnAmount)}원</strong></td>
				</tr>

				<c:if test="${not empty refund.returnVirtualNo && refund.orderPayments[0].escrowStatus ne '40'}">
					<tr>
						<th class="label" rowspan="3">환불정보</th>
						<c:choose>
							<c:when test="${refund.orderPayments[0].escrowStatus ne '40' && refund.orderPayments[0].escrowStatus ne '60'}">
									<th>은행명</th>
									<td>
										${returnBankName == null ?  refund.returnBankName : returnBankName}
									</td>
								</tr>
								<tr>
									<th>예금주</th>
									<td>
										${refund.returnBankInName}
									</td>
								</tr>
								<tr>
									<th>계좌번호</th>
									<td>
										${refund.returnVirtualNo}
									</td>
								</tr>
							</c:when>
							<c:otherwise>
									<th rowspan="3">에스크로</th>
									<td align="center">
										에스크로 구매거부 시 PG사에서 환불을 직접 처리기때문에 환불정보 알수없음
									</td>
								</tr>
							</c:otherwise>
						</c:choose>

				</c:if>
				<c:if test="${not empty refundBankInfo}">
					<tr>
						<th class="label">환불정보</th>
						<th>환불 (은행) </th>
						<td>
							${refundBankInfo}
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<div class="popup_btns" style="clear:both">
			<button type="button" onclick="Link.list('${requestContext.managerUri}/order/refund/list')" class="btn btn-default">목록</button>
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
$(function() {
	// 환불신청 취소처리.
    $('.op-btn-cancel-refund').on('click', function(e) {
		e.preventDefault();

		if (!confirm('환불 신청을 취소하시겠습니까?')) {
		    return;
		}

		// 신청 취소 처리...
		var refundCode = '${refund.refundCode}';
		var orderCode =  '${refund.orderCode}';

		var param = {
				orderCode 	:	orderCode,
				refundCode	:	refundCode
		};

		$.post("/opmanager/order/refund/cancel/" + refundCode, param, function(response) {
			Common.responseHandler(response, function() {
			    alert('환불 신청이 취소되었습니다.');
			    // 주문 상세페이지로 이동.
				location.href='/opmanager/order/new-order/order-detail/0/'+orderCode;

			}, function() {
			    alert(response.errorMessage);

			})
		})
	});

	$('#changePaymentForm').validator(function() {

		var isError = false;
        var totalReturnAmount = Number('${refund.totalReturnAmount}');

        if ($('input[name=refundAmount]').val() > 0) {

        	if($('input[name=refundAccountNumber]').val() == '') {
        		alert('은행 환불 계좌번호 항목을 입력해 주세요.');
		        return false;
	        }

	        if($('input[name=refundAccountName]').val() == '') {
		        alert('은행 환불 예금주 항목을 입력해 주세요.');
		        return false;
	        }
        }

        var totalCancelAmount = 0;
        var cancelAmountFlag = false;
		var vbankFlag = $('input[name=vbankFlag]').val();
		var realtimebankFlag = $('input[name=realtimebankFlag]').val();
		var cardFlag = $('input[name=cardFlag]').val();

		$.each($('input.op-pay-amount'), function(i) {
			if ($(this).val() == '') {
				$(this).val('0');
			}

			var remainingAmount = Number($(this).data('remainingAmount'));
			if (remainingAmount > 0) {
				var cancelAmount = Number($(this).val());
				if (cancelAmount > remainingAmount) {
					isError = true;
					alert('취소 가능 금액을 확인해 주세요.');
					return false;
				}
                totalCancelAmount += cancelAmount;
			}

			if ((vbankFlag == 'true' || realtimebankFlag == 'true' || cardFlag == 'true') && $('input[name="changePayments[' + i + '].cancelAmount"]').val() > 0) {
				cancelAmountFlag = true;
			}
		});

		if (isError) {
			return false;
		}

		if (totalReturnAmount != totalCancelAmount) {
		    alert('환불 금액은 ' + totalReturnAmount + '원 입니다. 환불할 금액을 확인해 주세요.');
		    return false;
        }

		if (cancelAmountFlag == true) {
			var type = '';
			var partCancel = $('input[name=PartialCancelCode]').val() == '1' ? ' 부분' : '';
			var content = ' 취소일 경우 계약 여부에 따라 환불 완료후 PG 관리자에서 환불 처리를 해야 합니다. 계속 진행하시겠습니까?'

            if (vbankFlag == 'true') {
                type = '가상계좌';
            } else if (realtimebankFlag == 'true') {
                type = '실시간 계좌이체';
            }

			content = type + partCancel + content;

			// 부분취소가 불가능한 카드결제일 경우
			var ccPartCl = $('input[name=ccPartCl]').val();
			if (cardFlag == 'true') {
				if (ccPartCl == 'N') {
					content = '부분취소가 불가능한 결제방식의 경우 은행 환불 처리를 해야 합니다. 계속 진행하시겠습니까?';
				} else {
					content = '';
				}
			}

			if (content != '' && !confirm(content)) {
				isError = true;
			}
		}

		if (isError) {
			return false;
		}
	})
})
</script>
