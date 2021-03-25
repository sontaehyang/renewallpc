<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<h3 class="mt30">결제 내역</h3>
<table class="inner-table">
	<caption>table list</caption>
    <colgroup>
        <col />
        <col />
        <col />
        <col style="width: 300px" />
        <col style="width: 300px" />
    </colgroup>
	<thead>
		<tr>
			<th scope="col" class="none_left">결제방식</th>
			<th scope="col" class="none_left">결제금액</th>
			<th scope="col" class="none_left">부분취소금액</th>
			<th scope="col" class="none_left">결제상태</th>
			<th scope="col" class="none_left">결제일</th>
		</tr>
	</thead>
	<tbody> 
		<c:set var="index">0</c:set>
		<c:set var="waitingPaymentCount">0</c:set>
		<c:forEach items="${orderPayments}" var="payment">
			<c:if test="${empty payment.payDate}">
				<c:set var="waitingPaymentCount">${waitingPaymentCount + 1}</c:set>
			</c:if>
			<c:if test="${payment.paymentType == '1' && payment.remainingAmount > 0}">
				<c:set var="unit">
					<c:choose>
						<c:when test="${payment.approvalType == 'point'}">P</c:when>
						<c:otherwise>원</c:otherwise>
					</c:choose>
				</c:set>
				
				<c:set var="rowspan">1</c:set>
				<c:if test="${payment.approvalType == 'bank' || (payment.approvalType == 'vbank' && payment.escrowStatus ne '40')}">
					<c:set var="rowspan">2</c:set>
				</c:if>
				<tr>
					<td class="text-center" rowspan="${rowspan}">${payment.approvalTypeLabel}
					<c:if test="${payment.escrowStatus ne 'N' }">(에스크로)</c:if>
					</td>
					<td class="text-right" rowspan="${rowspan}">
						${op:numberFormat(payment.amount)}${unit}
						<c:if test="${payment.amount != payment.remainingAmount && payment.remainingAmount > 0}">
							<br/><strong>(잔여액 : ${op:numberFormat(payment.remainingAmount)}${unit})</strong>
						</c:if> 								
					</td>
					<td class="text-right" rowspan="${rowspan}">
						
						취소 가능 금액 : ${op:numberFormat(payment.remainingAmount)}${unit}
						
						<input type="hidden" name="changePayments[${index}].paymentSequence" value="${payment.paymentSequence}" />
                        <input type="text" name="changePayments[${index}].cancelAmount" data-remaining-amount="${payment.remainingAmount}" maxlength="9" class="_number form-amount op-pay-amount" value="0"> <span class="price-unit">${unit}</span>
					</td>
					<td class="text-center">
						<c:choose>
							<c:when test="${empty payment.payDate}">미결</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${payment.paymentType == '1'}">결제완료</c:when>
									<c:otherwise>결제취소</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="text-center">
						${op:datetime(payment.payDate)}
					</td>
				</tr>
				<c:if test="${payment.approvalType == 'bank' || (payment.approvalType == 'vbank' && payment.escrowStatus ne '40')}">
					<tr>
						<td colspan="2" style="padding: 10px">
							<table class="inner-table">
								<caption>table list</caption>
                                <colgroup>
                                    <col style="width: 90px" />
                                    <col style="width: 120px" />
                                    <col />
                                    <col style="width: 90px" />
                                    <col style="width: 120px" />
                                </colgroup>
								<tbody>
									<tr>
										<th>환불계좌</th>
                                        <td style="border-right: 0px; padding-right: 0;">
                                            <!-- 2017.04.28 Jun-Eu Son 환불받을 은행명 설정 -->
                                            <c:forEach items="${bankListByKey}" var="bankList">
                                                <c:if test="${bankList.key.id eq refund.returnBankName or bankList.label eq refund.returnBankName}">
                                                    <c:set var="returnBankName" value="${bankList.label}" />
                                                </c:if>
                                            </c:forEach>
                                            <select name="changePayments[${index}].returnBankName" title="은행명" class="form-block required">
                                                <c:forEach items="${bankListByKey}" var="bankList">
                                                    <option value="${bankList.key.id}" ${op:selected(bankList.label, returnBankName)}>${bankList.label}</option>	<!-- 2017.04.28 Jun-Eu Son 은행명선택 수정	 -->
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="text" name="changePayments[${index}].returnBankVirtualNo" title="계좌번호" value="${payment.returnBankVirtualNo}" class="required _number form-block" placeholder="계좌번호" />
                                            </div>
										</td>

										<th>예금주</th>
										<td>
											<input type="text" name="changePayments[${index}].returnBankInName" title="예금주" value="${payment.returnBankInName}" class="required form-block" />
										</td>
									</tr>

								</tbody>
							</table>
						</td>
					</tr>
				</c:if>
				<c:set var="index">${index + 1}</c:set>
			</c:if>
		</c:forEach>


        <tr>
            <td class="text-center" style="background: #fcf8e8;font-weight: bold;">은행 환불</td>
            <td class="text-center" style="background: #fcf8e8;">-</td>
            <td class="text-right" style="background: #fcf8e8;">

                환불 금액 :
                <input type="text" name="refundAmount" maxlength="9" class="_number form-amount op-pay-amount" data-remaining-amount="999999999" value="0"> <span class="price-unit">원</span>
            </td>

             <td colspan="2" style="background: #fcf8e8; padding: 10px">
                <table class="inner-table">
                    <caption>table list</caption>
                    <colgroup>
                        <col style="width: 90px" />
                        <col style="width: 120px" />
                        <col />
                        <col style="width: 90px" />
                        <col style="width: 120px" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>환불계좌</th>
                        <td style="border-right: 0px; padding-right: 0;">
                            <!-- 2017.04.28 Jun-Eu Son 환불받을 은행명 설정 -->
                            <c:forEach items="${bankListByKey}" var="bankList">
                                <c:if test="${bankList.key.id eq refund.returnBankName or bankList.label eq refund.returnBankName}">
                                    <c:set var="returnBankName" value="${bankList.label}" />
                                </c:if>
                            </c:forEach>
                            <select name="refundBankName" title="은행명" class="form-block">
                                <c:forEach items="${bankListByKey}" var="bankList">
                                    <option value="${bankList.key.id}" ${op:selected(bankList.label, returnBankName)}>${bankList.label}</option>	<!-- 2017.04.28 Jun-Eu Son 은행명선택 수정	 -->
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="text" name="refundAccountNumber" value="${refund.returnVirtualNo}" title="계좌번호" class="_number form-block" placeholder="계좌번호" />
                            </div>
                        </td>

                        <th>예금주</th>
                        <td>
                            <input type="text" name="refundAccountName" value="${refund.returnBankInName}" title="예금주" class="form-block" />
                        </td>
                    </tr>

                    </tbody>
                </table>
            </td>
        </tr>

		<%--<c:if test="${index == 0}">
			<tr>
				<td colspan="5" class="text-center">결제 정보가 없습니다.</td>
			</tr>
		</c:if>--%>
	</tbody>
</table> 

<c:if test="${pageType != 'refund'}">
	<c:if test="${waitingPaymentCount > 0}">
		<h3 class="mt30">입금 대기 내역</h3>
		<table class="inner-table">
			<caption>table list</caption>
			<thead>
				<tr>
					<th scope="col" class="none_left">결제방식</th>
					<th scope="col" class="none_left">결제금액</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="index">0</c:set>
				<c:set var="waitingAmount">0</c:set>
				<c:forEach items="${orderPayments}" var="payment" >
					<c:if test="${empty payment.payDate}">
						<tr>
							<td><label><input type="checkbox" name="deletePaymentIds" value="${payment.paymentSequence}" /> ${payment.approvalTypeLabel} 취소</label></td>
							<td class="text-right">${op:numberFormat(payment.amount)}원</td>
						</tr>
						<c:set var="index">${index + 1}</c:set>
						<c:set var="waitingAmount">${waitingAmount + payment.amount}</c:set>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	
	<h3 class="mt5">결제 추가</h3>
	<table class="inner-table">
		<caption>table list</caption>
		<thead>
			<tr>
				<th scope="col" class="none_left">결제방식</th>
				<th scope="col" class="none_left">결제금액</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="text-center">은행입금</td>
				<td>
					<input type="hidden" name="newPayments[0].approvalType" value="bank" />
					<input type="text" name="newPayments[0].amount" maxlength="9" class="_number op-pay-amount" value="0" />
					<span style="color:red">(입금 예정 금액 : ${op:numberFormat(waitingAmount)} 원)</span>
				</td>
			</tr>
			
			<c:if test="${avilablePoint > 0}">
				<tr>
					<td class="text-center">${op:message('M00246')}</td>
					<td>
						<input type="hidden" name="newPayments[1].approvalType" value="point" />
						<input type="text" name="newPayments[1].amount" maxlength="9" class="_number op-pay-amount" value="0" />
						<span style="color:red">(사용 가능 ${op:message('M00246')} : ${op:numberFormat(avilablePoint)} P)</span>
					</td>
				</tr>
			</c:if> 
		</tbody>
	</table>
</c:if>