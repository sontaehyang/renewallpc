<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div id="container">
	<div class="title">
		<h2>주문/배송조회</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<div class="mypage_wrap">
			<div class="active_con">
				<div class="order_info">
					<p class="order_number">주문번호 : <span>${order.orderCode}</span></p>
					<p class="order_date">주문일자 : <span>${op:datetime(order.createdDate)}</span></p>
				</div>
				<ul>
					<c:forEach items="${ order.orderShippingInfos }" var="shippingInfo" varStatus="orderItemGroupIndex">
						<li>
							<a href="#" class="order_view_btn">
								<div class="active_title">
									<span class="review_text">배송지${orderItemGroupIndex.count}</span>
									<span class="more_arr"></span>
								</div>
							</a>
							<div class="order_view_con">
								<c:forEach items="${shippingInfo.orderItems}" var="orderItem" varStatus="i">
									<c:set var="totalItemAmount" value="${orderItem.itemAmount}" />
									<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />

									<c:forEach items="${orderItem.additionItemList}" var="addition">
										<c:set var="totalItemAmount">${totalItemAmount + addition.itemAmount}</c:set>
										<c:set var="totalSaleAmount">${totalSaleAmount + addition.saleAmount}</c:set>
									</c:forEach>

									<div class="item">
										<div class="order_img">
											<a href="/products/view/${orderItem.itemUserCode}">
												<%-- <img src="${orderItem.imageSrc}" alt="제품이미지"> --%>
												<img src="${shop:loadImageBySrc(orderItem.imageSrc, 'XS')}" alt="제품이미지">
											</a>
										</div>
										<div class="order_name">
											<p class="tit"><a href="/products/view/${orderItem.itemUserCode}">${orderItem.itemName}</a></p>
											${ shop:viewOptionText(orderItem.options) }
											${shop:viewAdditionOrderItemList(orderItem.additionItemList)}
											${ shop:viewOrderGiftItemList(orderItem.orderGiftItemList) }
										</div>
										<div class="order_price">
											<p class="price">
												<span class="discount">${op:numberFormat(totalItemAmount)}원</span>
												<span class="sale_price">${op:numberFormat(totalSaleAmount)}</span>원 (${op:numberFormat(orderItem.quantity)}개)
											</p>
										</div>
										<div class="order_shipping_amount">
											<p class="price">배송비 
												
													<c:choose>
							 							<c:when test="${orderItem.isShippingView == 'Y'}">
							 								<c:choose>
							 									<c:when test="${orderItem.orderShipping.shippingCouponCount > 0}">
							 										무료배송 (배송비쿠폰 -${op:numberFormat(orderItem.orderShipping.discountShipping)}원)
							 									</c:when>
							 									<c:otherwise>
								 									<c:choose>
									 									<c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
																			<c:choose>
																				<c:when test="${orderItem.deliveryMethodType == 'QUICK'}">
																					${orderItem.deliveryMethodType.title}
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
									 												${op:numberFormat(orderItem.orderShipping.payShipping)}원  
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
												
											</p>
										</div>
									</div>
									
									<div class="shipping_detail">
										<span class="shipping_txt">${orderItem.orderStatusLabel}
											<c:if test="${not empty orderItem.claimRefusalReasonText}">
												(사유 : ${orderItem.claimRefusalReasonText})
											</c:if>
										</span>

										<c:set var="orderItem" value="${orderItem}" scope="request"/>
										<div class="btns">
											<jsp:include page="order-button.jsp"></jsp:include>
										</div>
									</div>
								</c:forEach>
								
								<div class="order_detail">
									<table> 
										<colgroup>
											<col style="width:30%;">
											<col style="*">
										</colgroup>
										<tbody>
											<tr>
												<th>배송지 정보</th>
												<td>
													<p>받는사람 이름 : <span>${shippingInfo.receiveName}</span></p>
													<p>주소 : <span>[${empty shippingInfo.receiveNewZipcode ? shippingInfo.receiveZipcode : shippingInfo.receiveNewZipcode}] ${shippingInfo.receiveAddress}&nbsp;${shippingInfo.receiveAddressDetail}</span></p>
													<p>연락처 : <span>${shippingInfo.receiveMobile}</span> /<br><span>${shippingInfo.receivePhone}</span></p>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</li>
					</c:forEach>

					<li>
						<div class="price_title">
							<span class="review_text">결제 정보</span>
						</div>
						<div class="price_detail">
							<table> 
								<tbody>
									<tr>
										<td>
											<p>상품금액 : <span>${op:numberFormat(order.totalItemAmount)}</span>원</p>
											<p>할인금액 : <span>${op:numberFormat(order.totalDiscountAmount)}</span>원</p>
											<p>배송비 : <span>${op:numberFormat(order.totalShippingAmount)}</span>원</p>
											<p>결제금액 : <span class="sale_price">${op:numberFormat(order.totalOrderAmount)}</span><span class="t_bold">원</span></p>
										</td>
									</tr>
								</tbody>
							</table>
						</div>	
					</li>
					<li>
						<div class="price_title">
							<span class="review_text">결제 수단</span>
						</div>
						<div class="price_detail">
							<table>
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
													<p>
														<c:choose>
															<c:when test="${payment.refundFlag == 'Y'}">
																환불 (은행)
															</c:when>
															<c:otherwise>
																${payment.approvalTypeLabel}
															</c:otherwise>
														</c:choose> :
														<span>
															<c:choose>
																<c:when test="${payment.paymentType == '1'}">
																	${op:numberFormat(payment.amount)}${unit}
																	<c:if test="${payment.amount != payment.remainingAmount && payment.remainingAmount > 0}">
																		<strong style="display:block; padding-top: 5px;">(잔여액 : ${op:numberFormat(payment.remainingAmount)}${unit})</strong>
																	</c:if> 
																</c:when>
																<c:otherwise>
																	${op:numberFormat(payment.cancelAmount)}${unit}
																	<br/>
																	<c:choose>
																		<c:when test="${payment.refundFlag == 'Y'}">
																			${payment.paymentSummary}
																		</c:when>
																		<c:otherwise>
																			${payment.payInfo}
																		</c:otherwise>
																	</c:choose>
																	<br/>
																</c:otherwise>
															</c:choose>
															<c:choose>
																<c:when test="${empty payment.payDate}">(미결)<br/>${payment.bankVirtualNo}</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${payment.paymentType == '1'}">(결제완료)</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${payment.refundFlag == 'Y'}">(환불완료)</c:when>
																				<c:otherwise>(결제취소)</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
														</span>
													</p>
													
													<%-- <c:if test="${payment.approvalType == 'vbank' && payment.paymentType == '1'}">
														<p class="account_info">
															<c:choose>
																<c:when test='${order.allItemsCanceled}'>
																	입금전 주문취소
																</c:when>
																<c:otherwise>
																	입금은행 : ${payment.bankVirtualNo} ${payment.bankInName}
																</c:otherwise>
															</c:choose>
														</p>
													</c:if> --%>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>	
					</li>
				</ul>
			</div>
			<!-- //active_con -->

			
		</div>
		<!-- //mypage_wrap -->
	
	</div>
	<!-- 내용 : e -->
	
</div>

<script type="text/javascript" src="/content/modules/op.mypage.js"></script>
<script type="text/javascript" src="/content/modules/op.order.js"></script>