<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<div class="cancel-form">

	<h4>환불 정보 입력</h4>
	<div class="bd_table pd0">
		<ul class="del_info ">
			<li>
				<span class="del_tit t_gray">환불방법</span>
				<div class="radio_area">
					
					<c:set var="buttonCount">0</c:set>
					<c:if test="${orderRefund.totalReturnAmount > 0 && orderRefund.autoCancel == true
									|| orderStatus == '10' && orderPgData.pgPaymentType == 'CARD' && claimApplyQuantity == orderRefund.totalOrderQuantity}">
						<input type="radio" name="claimRefundType" id="apply1" value="1" checked="checked" />
						<label for="apply1"><span><span></span></span>
							<c:choose>
								<c:when test="${orderRefund.totalAddShippingAmount < 0}">환불금액에서 배송비 차감후 지급</c:when>
								<c:otherwise>환불하기</c:otherwise>
							</c:choose>
						</label>
						<c:set var="buttonCount">1</c:set>
					</c:if>
					
					<%-- CJH 2016.11.13 환불하기 버튼이 노출되지 않거나 추가금이 있는 경우 신청하기 노출 --%>
					<c:if test="${buttonCount == 0 || (orderRefund.totalAddShippingAmount < 0 && orderRefund.autoCancel == true)}">
						<input type="radio" name="claimRefundType" id="apply2" value="2" ${buttonCount == 0 ? 'checked="checked"' : ''} />
						<label for="apply2"><span><span></span></span>신청하기</label>
					</c:if>
				</div>
			</li>
			<li>
				<span class="del_tit t_gray">환불총액</span>
				<div class="price_wrap">
					<p class="total_price"><span>${op:numberFormat(orderRefund.totalReturnAmount)}</span>원</p>
					<p class="detail_price">상품금액[<span>${op:numberFormat(orderRefund.totalItemReturnAmount)}</span>원] + 배송비[<span>${op:numberFormat(orderRefund.totalAddShippingAmount)}</span>원]</p>
				</div>
			</li>
			<c:if test="${(orderRefund.autoCancel == false || orderRefund.writeBankInfo == true)
					&& !(orderPgData.pgPaymentType == 'CARD' && claimApplyQuantity == orderRefund.totalOrderQuantity)}">
				<li>
					<span class="del_tit t_gray">은행명</span>
					<div class="input_area">
						<select name="returnBankName">
							<%-- CJH 2016.12.05 복합결제일때.. 어쩐다... --%>
							<c:forEach items="${bankListByKey}" var="code">
								<option value="${code.key.id}">${code.label}</option>
							</c:forEach> 
						</select>
					</div>
				</li>
				<li>
					<span class="del_tit t_gray">예금주</span>
					<input type="text" name="returnBankInName" maxlength="30" />
				</li>
				<li>
					<span class="del_tit t_gray">계좌번호</span>
					<input type="text" name="returnVirtualNo" class="_number" maxlength="30" />
				</li>
			</c:if>
		</ul>
	</div>
	<!-- //bd_table -->
	
	<div class="desc_list">
		<ul>
			<li>
				<c:choose>
					<c:when test="${claimApplyQuantity == orderRefund.totalOrderQuantity}">
						<span>자동 환불이 불가능한 결제방식</span>의 경우 위에 입력하신 계좌로 취소금액을 입금해드립니다.
					</c:when>
					<c:otherwise>
						<span>부분취소가 불가능한 결제방식</span>의 경우 위에 입력하신 계좌로 취소금액을 입금해드립니다.
					</c:otherwise>
				</c:choose>
			</li>
			<li>통장입금의 경우 <span>택배가 판매자에게 도착 후 2~3일 이후</span> 환불 신청하신 계좌로 입금됩니다.</li>
		</ul>
	</div>
	<!-- //desc_list -->
	
	<div class="btn_wrap">
		<button type="button" onclick="history.back()" class="btn_st1 reset">취소</button>
		<button type="submit" class="btn_st1 decision">
			<c:choose>
				<c:when test="${orderRefund.totalReturnAmount > 0 && orderRefund.autoCancel == true
								|| orderStatus == '10' &&  orderPgData.pgPaymentType == 'CARD' && claimApplyQuantity == orderRefund.totalOrderQuantity}">
					환불
				</c:when>
				<c:otherwise>
					신청
				</c:otherwise>
			</c:choose>
		</button>
	</div>
	<!-- //btn_wrap -->
	
</div>