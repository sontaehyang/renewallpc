<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<!-- 본문 -->
<div class="popup_wrap">
	<h3 class="popup_title"><span>${orderItem.orderCode} - 주문 정보</span></h3>
	<div class="popup_contents">
		<div class="board_write">

			<h3 class="mt10"><span>주문 기본 정보</span></h3>
			<jsp:include page="../include/order-default-info.jsp" />
		
			<h3 class="mt10"><span>주문 상품 정보</span></h3>
			<jsp:include page="../include/order-item.jsp" />
			
			<c:set var="orderShippingInfo" value="${orderItem.orderShippingInfo}" scope="request" />
			<jsp:include page="../include/order-shipping-info.jsp" />
		</div>
		
		<h3 class="mt10"><span>처리 현황 정보</span></h3>
		<table class="board_write_table">
			<colgroup> 						
				<col style="width:20%;" />
				<col />
				<col style="width:20%;" />
				<col />
			</colgroup> 
			<tbody>
				<tr>
					<th class="label">처리 상태</th>
					<td class="tleft">
						<div>
							<c:choose>
								<c:when test="${orderItem.orderItemStatus == '40'}">반품신청</c:when>
								<c:when test="${orderItem.orderItemStatus == '41'}">반품상품 회수중</c:when>
							</c:choose>
						</div>
					</td>   
					<th class="label">요청 사유</th>
					<td class="tleft">
						<div>
							${orderItem.orderReturnApply.returnReasonText}
						</div>
					</td>
				</tr>
				<tr>   
					<th class="label">회수 상태</th>
					<td class="tleft">
						<div>
							<c:choose>
								<c:when test="${orderItem.orderReturnApply.returnShippingStartFlag == 'Y'}">
									택배사 : ${orderItem.orderReturnApply.returnShippingCompanyName}, 송장번호 : ${orderItem.orderReturnApply.returnShippingNumber}<br/>
									(해당 배송사로 고객이 <strong>${op:date(orderItem.orderReturnApply.returnShippingStartDate)}일</strong>에 발송 하였습니다.)
								</c:when>
								<c:otherwise>
									지정택배사 <strong>${orderItem.deliveryCompanyName}</strong> 으로 회수신청 하였습니다.
								</c:otherwise>
							</c:choose>
						</div>
					</td> 
					<th class="label">반송 택배비</th>
					<td class="tleft">
						<div>
							${op:numberFormat(orderItem.orderReturnApply.returnRealShipping)}원
						</div>
					</td> 
				</tr> 
				<tr>   
					<th class="label">메모(관리자)</th>
					<td colspan="3" class="tleft">
						<div>
							${op:nl2br(orderItem.orderReturnApply.returnMemo)}
						</div>
					</td> 
				</tr> 
			</tbody>
		</table>
		
		<div class="popup_btns">
			<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 -->
		</div>
	</div>
</div>