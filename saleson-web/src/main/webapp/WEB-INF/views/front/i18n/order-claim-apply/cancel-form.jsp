<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<div class="cancel-form">
	<div class="pop_table mt24"> 
		<table>
			<caption>환불 정보</caption>
			<colgroup>
				<col style="width:22%;">
				<col style="width:auto;">
			</colgroup>
			<tbody>
				<c:if test="${not empty orderPgData}">
					<input type="hidden" name="MID" value="${orderPgData.pgServiceMid}" />
					<input type="hidden" name="TID" value="${orderPgData.pgKey}" />
					<input type="hidden" name="CancelAmt" value="${orderRefund.groups.get(0).returnAmount}" />
					<input type="hidden" name="CancelMsg" value="" />
					<input type="hidden" name="CancelPwd" value="123456" /> <%--취소 패스워드(가맹점 관리 취소 패스워드 설정 시 application.properties 값 가져오기)--%>
					<input type="hidden" name="PartialCancelCode" value="${partCancel}" /> <%--부분취소 여부--%>
				</c:if>

				<tr>
					<th scope="row">환불 방법</th>
					<td>
						<div class="radio_area">

							<c:set var="buttonCount">0</c:set>
							<c:if test="${orderRefund.totalReturnAmount > 0 && orderRefund.autoCancel == true
											|| orderStatus == '10' && orderPgData.pgPaymentType == 'CARD' && claimApplyQuantity == orderRefund.totalOrderQuantity}">
								<label>
									<input type="radio" name="claimRefundType" value="1" checked="checked" />
									<c:choose>
										<c:when test="${orderRefund.totalAddShippingAmount < 0}">환불금액에서 배송비 차감후 지급</c:when>
										<c:otherwise>환불하기</c:otherwise>
									</c:choose>
								</label>
								<c:set var="buttonCount">1</c:set>
							</c:if>
							
							<%-- CJH 2016.11.13 환불하기 버튼이 노출되지 않거나 추가금이 있는 경우 신청하기 노출 --%>
							<c:if test="${buttonCount == 0 || (orderRefund.totalAddShippingAmount < 0 && orderRefund.autoCancel == true)}">
								<label><input type="radio" name="claimRefundType" value="2" ${buttonCount == 0 ? 'checked="checked"' : ''} />신청하기</label>
							</c:if>

						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">환불 총액</th>
					<td>
						<p class="cancel_price">
							<span>${op:numberFormat(orderRefund.totalReturnAmount)}</span> (상품 금액[<span>${op:numberFormat(orderRefund.totalItemReturnAmount)}</span>원] + 배송비[<span>${op:numberFormat(orderRefund.totalAddShippingAmount)}</span>원])
						</p>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<c:if test="${(orderRefund.autoCancel == false || orderRefund.writeBankInfo == true)
					&& !(orderPgData.pgPaymentType == 'CARD' && claimApplyQuantity == orderRefund.totalOrderQuantity)}">
		<h2 class="mt20">환불 계좌 정보 입력<span>환불 받으실 계좌정보를 정확히 입력해주세요.</span></h2>
		<div class="pop_table"> 
			<table>
				<caption>환불 계좌 정보 입력</caption>
				<colgroup>
					<col style="width:22%;">
					<col style="width:28%;">
					<col style="width:22%;">
					<col style="width:28%;">
				</colgroup>
				<tbody> 
					<tr>
						<th scope="col">은행명</th>
						<td>
							<select name="returnBankName" class="form-control">
 								<%-- CJH 2016.12.05 복합결제일때.. 어쩐다... --%>
 								<c:forEach items="${bankListByKey}" var="code">
									<option value="${code.key.id}">${code.label}</option>
								</c:forEach> 
 							</select>
	 					</td>
	 					<th scope="col">예금주</th>
	 					<td><input type="text" name="returnBankInName" maxlength="30" /></td>
	 				</tr>
	 				<tr>
	 					<th scope="col">계좌번호</th>
	 					<td colspan="3"><input type="text" name="returnVirtualNo" class="_number" maxlength="30" /></td>
	 				</tr>
	 			</tbody>
	 		</table>
		</div>
	</c:if>
	
	<div class="pop_desc">
		<ul>
			<li>
				<c:choose>
					<c:when test="${claimApplyQuantity == orderRefund.totalOrderQuantity}">
						자동 환불이 불가능한 결제방식의 경우 위에 입력하신 계좌로 취소금액을 입금해드립니다.
					</c:when>
					<c:otherwise>
						부분취소가 불가능한 결제방식의 경우 위에 입력하신 계좌로 취소금액을 입금해드립니다.
					</c:otherwise>
				</c:choose>
			</li>
			<li>통장입금의 경우 택배가 판매자에게 도착한 후 2 ~ 3일 이내에 환불 신청하신 계좌로 입금됩니다.</li>
		</ul>
	</div>

	<div class="btn_wrap">
		<c:choose>
			<c:when test="${orderRefund.totalReturnAmount > 0 && orderRefund.autoCancel == true
							|| orderStatus == '10' && orderPgData.pgPaymentType == 'CARD' && claimApplyQuantity == orderRefund.totalOrderQuantity}">
				<button type="submit" class="btn btn-success btn-lg" title="환불">환불</button>
			</c:when>
			<c:otherwise>
				<button type="submit" class="btn btn-success btn-lg" title="신청">신청</button>
			</c:otherwise>
		</c:choose>
		<button type="button" class="btn btn-lg btn-default" onclick="Shop.closeOrderLayer('claim_apply')" title="취소">취소</button>
	</div>
	
</div>