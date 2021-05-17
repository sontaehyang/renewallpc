<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>



<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/order">쇼핑내역</a> 
			<span>주문/배송조회</span> 
		</div>
	</div><!-- // location_area E -->   

	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>

	<div id="contents" class="pt0"> 
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
		<div class="contents_inner"> 	
			<h2>주문/배송 상세조회</h2>  
			<div class="order_numberBox">
				<p>주문번호: <strong>${order.orderCode}</strong></p>
				<p>주문일자: <strong>${op:datetime(order.createdDate)}</strong></p>	
			</div> <!--// order_numberBox E-->   

			<c:forEach items="${ order.orderShippingInfos }" var="shippingInfo" varStatus="infoIndex">
				<h3>배송지 ${infoIndex.count}</h3> 
				<div class="board_wrap">  
					<table cellpadding="0" cellspacing="0"  class="order_view">
						<caption>배송지 1</caption>
						<colgroup>
							<col style="width:128px;"> 
							<col style="width:315px;">	
							<col style="width:128px;"> 
							<col style="width:auto;">	 				
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">받으시는 분</th>
								<td>${shippingInfo.receiveName}</td> 
								<th scope="row">휴대폰번호</th>
								<td>${shippingInfo.receiveMobile}</td> 
							</tr> 
							<tr>
								<th scope="row">전화번호</th>
								<td>${shippingInfo.receivePhone}</td> 
								<th scope="row">배송시 요구사항</th>
								<td>${shippingInfo.memo}</td> 	
							</tr>
							<tr>
								<th scope="row">배송지</th>
								<td colspan="3"><p>[${empty shippingInfo.receiveNewZipcode ? shippingInfo.receiveZipcode : shippingInfo.receiveNewZipcode}] ${shippingInfo.receiveAddress}&nbsp;${shippingInfo.receiveAddressDetail}</p> </td>
							</tr>
						</tbody>
					</table> 
				</div><!--//board_wrap E--> 
				<div class="board_wrap">  
					<table cellpadding="0" cellspacing="0"  class="order_list">
						<caption>주문 정보</caption>
						<colgroup>
							<col style="width:auto;">
							<col style="width:70px;">
							<col style="width:110px;">
							<col style="width:80px;">
							<col style="width:135px;">
						</colgroup>
						<thead>
							<tr> 
								<th scope="col" class="tleft">상품정보</th>
								<th scope="col">적립포인트</th>
								<th scope="col">상품합계</th>
								<th scope="col">배송비</th> 
								<th scope="col">주문/배송상태</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ shippingInfo.orderItems }" var="orderItem" varStatus="i">
								<c:set var="totalItemAmount" value="${orderItem.itemAmount}" />
								<c:set var="totalEarnPoint" value="${orderItem.earnPoint * orderItem.quantity}"/>

								<c:forEach items="${orderItem.additionItemList}" var="addition">
									<c:set var="totalItemAmount">${totalItemAmount + addition.itemAmount}</c:set>
									<c:set var="totalEarnPoint" value="${totalEarnPoint + (addition.earnPoint * addition.quantity)}"/>
								</c:forEach>
								<tr>
									<td class="tleft line">
										<div class="item_info">
											<a href="/products/view/${orderItem.itemUserCode}"><p class="photo"><img src="${ shop:loadImageBySrc(orderItem.imageSrc, 'XS') }" alt="item photo"></p></a>
											<div class="order_option">
												<a href="/products/view/${orderItem.itemUserCode}"><p class="item_name">${orderItem.itemName}</p></a>
												${shop:viewOptionText(orderItem.options)}

												${shop:viewAdditionOrderItemList(orderItem.additionItemList)}

												${shop:viewOrderGiftItemList(orderItem.orderGiftItemList)}
												<div class="item_price">
													<span>${op:numberFormat(totalItemAmount)}</span>원 / <span>${op:numberFormat(orderItem.quantity)}</span>개
												</div>
											</div>
										</div><!--// item_info E-->
									</td>
									<td><span>${op:numberFormat(totalEarnPoint)}</span>P</td>
									<td>
										<strong><span>${op:numberFormat(totalItemAmount)}</span>원 </strong>
									</td>
									<td>
										<c:choose>
				 							<c:when test="${orderItem.isShippingView == 'Y'}">
				 								<c:choose>
				 									<c:when test="${orderItem.orderShipping.shippingCouponCount > 0}">
				 										무료배송<br/>(배송비쿠폰 -${op:numberFormat(orderItem.orderShipping.discountShipping)}원)
				 									</c:when>
				 									<c:otherwise>
					 									<c:choose>
						 									<c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
																<c:choose>
																	<c:when test="${orderItem.deliveryMethodType == 'QUICK'}">
																		${orderItem.deliveryMethodType.title}<br/>
																	</c:when>
																	<c:otherwise>
																		${op:numberFormat(orderItem.orderShipping.realShipping)}원
																	</c:otherwise>
																</c:choose>
																<span style="color:red">
																	(착불)
																</span>
						 									</c:when>
						 									<c:otherwise>
						 										<c:choose>
						 											<c:when test="${orderItem.orderShipping.payShipping == 0 && orderItem.deliveryMethodType == 'NORMAL'}">무료배송</c:when>
																	<c:when test="${orderItem.deliveryMethodType == 'PICK_UP'}">${orderItem.deliveryMethodType.title}</c:when>
						 											<c:otherwise>
						 												<span>${op:numberFormat(orderItem.orderShipping.payShipping)}</span>원
						 											</c:otherwise>
						 										</c:choose>
						 									</c:otherwise>
					 									</c:choose>
				 									</c:otherwise>
				 								</c:choose>
				 							</c:when>
				 							<c:otherwise>
												<c:choose>
													<c:when test="${orderItem.deliveryMethodType != 'NORMAL'}">
														${orderItem.deliveryMethodType.title}
														<c:if test="orderItem.deliveryMethodType == 'QUICK'">
															<span style="color:red">
																(착불)
															</span>
														</c:if>
													</c:when>
													<c:otherwise>
														무료배송
													</c:otherwise>
												</c:choose>
											</c:otherwise>
				 						</c:choose>
									</td>
									<td class="order_num">
										<p>${orderItem.orderStatusLabel}</p>

										<%-- CJH 가상계좌, 은행 입금의 만료일자를 표시하는 부분. 개발되어있나?
										<p class="date">(2016-11-11)</p>
										--%>
										<div class="order_btnList">

											<c:set var="orderItem" value="${orderItem}" scope="request" />
											<jsp:include page="order-button.jsp" />

										</div>
									</td>
								</tr>
								<c:if test="${not empty orderItem.claimRefusalReasonText}">
									<tr>
										<td colspan="5" class="tleft line">
											<strong>※ ${orderItem.orderStatus == '59' ? '교환' : '반품' } 거절 사유</strong> : ${orderItem.claimRefusalReasonText}
										</td>
									</tr>
								</c:if>
							</c:forEach>

						</tbody>
					</table> <!--// order_list E-->	  		 
				</div>	<!--// board_wrap E--> 	 
			</c:forEach>

			<h3>결제정보</h3>
			<div class="board_wrap">  
				<table cellpadding="0" cellspacing="0" class="order_view">
					<caption>결제정보</caption>
					<colgroup>
						<col style="width:160px;"> 
						<col style="width:auto;">	 				
					</colgroup>
					<tbody>
						<tr>
							<c:set var="totalItemAmount" value="${order.totalItemAmount}" />
							<c:set var="totalDiscountAmount" value="${order.totalDiscountAmount}" />
							<c:set var="totalItemDiscountAmount" value="${order.totalItemDiscountAmount}" />
							<c:set var="totalCouponDiscountAmount" value="${order.totalCouponDiscountAmount}" />
							<c:set var="totalUserLevelDiscountAmount" value="${order.totalUserLevelDiscountAmount}" />

							<c:if test="${!empty addition}">
								<c:set var="totalItemAmount" value="${totalItemAmount + addition.totalItemAmount}" />
								<c:set var="totalDiscountAmount" value="${totalDiscountAmount + addition.totalDiscountAmount}" />
								<c:set var="totalItemDiscountAmount" value="${totalItemDiscountAmountf + addition.totalItemDiscountAmount}" />
								<c:set var="totalCouponDiscountAmount" value="${totalCouponDiscountAmount + addition.totalCouponDiscountAmount}" />
								<c:set var="totalUserLevelDiscountAmount" value="${totalUserLevelDiscountAmount + addition.totalUserLevelDiscountAmount}" />
							</c:if>

							<th scope="row">주문금액</th>
							<td class="text-right mypage">${op:numberFormat(totalItemAmount + order.totalShippingAmount)}원</td>
							<td>상품금액 ${op:numberFormat(totalItemAmount)}원 + 배송비 ${op:numberFormat(order.totalShippingAmount)}원</td>
						</tr>
						<tr>
							<th scope="row">할인금액</th>
							<td class="text-right mypage">
								${op:numberFormat(totalDiscountAmount)}원
							</td>
							<td>
								상품할인 ${op:numberFormat(totalItemDiscountAmount)}원
								<c:if test="${totalCouponDiscountAmount > 0}">
									+ 쿠폰할인 ${op:numberFormat(totalCouponDiscountAmount)}원
								</c:if>
								<c:if test="${totalUserLevelDiscountAmount > 0}">
									+ 회원할인 ${op:numberFormat(totalUserLevelDiscountAmount)}원
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row">배송비</th>
							<td class="text-right mypage">
								${op:numberFormat(order.totalShippingAmount)}원
							</td>
							<td>&nbsp;</td>
						</tr>

						<tr>
							<th scope="row">총 결제금액</th>
							<td class="text-right mypage mypage" style="font-weight:bold; color:#e42222">${op:numberFormat(order.totalOrderAmount)}원</td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
				</table> 	 	 
			</div><!--// board_view E-->
			
			<h3>결제수단</h3>
			<div class="board_wrap">  
		 		<table cellpadding="0" cellspacing="0" class="order_list">
					<thead>
						<tr>
							<th scope="col">결제방식</th>
							<th scope="col">결제금액</th>
							<th scope="col">결제상태</th>
							<th scope="col">결제일</th>
						</tr>
					</thead>
					<tbody> 
						<c:forEach items="${order.orderPayments}" var="payment">
							
							<c:if test="${payment.amount > 0 || payment.cancelAmount > 0 || payment.remainingAmount > 0}">
							
								<c:set var="unit">원</c:set>
							
								<c:choose>
									<c:when test="${payment.approvalType == 'point'}">
										<c:set var="unit">P</c:set>
									</c:when>
								</c:choose>
			
								<tr>
									<td>
										<c:choose>
											<c:when test="${payment.refundFlag == 'Y'}">
												환불 (은행)
											</c:when>
											<c:otherwise>
												${payment.approvalTypeLabel}
											</c:otherwise>
										</c:choose>
									</td>
									<td class="tright" style="float:none">
										<c:choose>
											<c:when test="${payment.paymentType == '1'}">
												${op:numberFormat(payment.amount)}${unit}
												<c:if test="${payment.amount != payment.remainingAmount && payment.remainingAmount > 0}">
													<strong style="display:block; padding-top: 5px;">(잔여액 : ${op:numberFormat(payment.remainingAmount)}${unit})</strong>
												</c:if> 
											</c:when>
											<c:otherwise>${op:numberFormat(payment.cancelAmount)}${unit}</c:otherwise>
										</c:choose>
										
									</td>
									<td>
										<c:choose>
											<c:when test="${empty payment.payDate}">
												미결<br/>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${payment.paymentType == '1'}">결제완료</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${payment.refundFlag == 'Y'}">환불완료</c:when>
															<c:otherwise>결제취소</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
										<td>
											${op:datetime(payment.payDate)}
											<c:if test="${(payment.approvalType == 'vbank' || payment.approvalType == 'bank')}">
												<c:choose>
													<c:when test="${payment.paymentType == '1'}">
														<p>
															<c:choose>
																<c:when test='${order.allItemsCanceled}'>
																	<strong>입금전 주문취소</strong>
																</c:when>
																<c:otherwise>
																	<strong>${payment.approvalType == 'bank' ? payment.bankDate : op:date(payment.bankDate)}</strong>까지 입금 필요<br/>
																	(입금계좌 : <strong>${payment.bankVirtualNo}&nbsp;${payment.bankInName}</strong>)
																</c:otherwise>
															</c:choose>
														</p>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${payment.refundFlag == 'Y'}">
																${payment.paymentSummary}
															</c:when>
															<c:when test='${order.allItemsCanceled}'>
																<strong>입금전 주문취소</strong>
															</c:when>
															<c:otherwise>
																${payment.payInfo}
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>

											</c:if>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div><!--// board_view E-->
			
			<div class="btn_wrap">
				<a href="/mypage/order" class="btn btn-success btn-lg">목록</a>
			</div>  
		</div> 
	</div>	<!-- // contents E -->	
</div> <!--// inner E-->

<!-- op.mypage.js 추가  2017-04-25_seungil.lee -->
<script type="text/javascript" src="/content/modules/op.mypage.js"></script>