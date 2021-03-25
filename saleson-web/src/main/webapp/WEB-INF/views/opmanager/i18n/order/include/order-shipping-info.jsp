<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>

<c:forEach items="${order.orderShippingInfos}" var="receiver" varStatus="receiverIndex">
	<input type="hidden" name="orderShippingInfos[${receiverIndex.index}].shippingInfoSequence" value="${receiver.shippingInfoSequence}" />
	<table class="board_write_table" ${viewType == 'info' ? 'style="border-top: 1px solid #666; border-bottom:0;"' : ''}>
		<caption>주문상품정보 - 주문자정보</caption>
		<colgroup>
			<col style="width:100px;">
			<col style="width:auto;">
		</colgroup>
		<tbody>
		<tr>
			<th scope="row">배송정보</th>
			<td>
				<div>
					<c:choose>
						<c:when test="${viewType == 'info'}">
							<ul style="margin-bottom:0px;">
								<li>${receiver.receiveName}</li>
								<li>${receiver.receivePhone} / ${receiver.receiveMobile}</li>
								<li>[${receiver.receiveNewZipcode}] ${receiver.receiveAddress} ${receiver.receiveAddressDetail}</li>
							</ul>
						</c:when>
						<c:otherwise>
							<table class="board_write_table">
								<colgroup>
									<col style="width: 10%;" />
									<col />
									<col style="width: 10%;" />
									<col />
								</colgroup>
								<tbody>
								<tr>
									<th class="label">받는사람</th>
									<td colspan="3">
										<div>
											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receiveName" class="required" maxlength="50" title="받는사람" value="${receiver.receiveName}" />
										</div>
									</td>
								</tr>
								<tr>
									<th class="label">전화번호</th>
									<td>
										<div>
											<select name="orderShippingInfos[${receiverIndex.index}].receivePhone1" title="전화번호" class="">
												<option value="">-선택-</option>
												<c:forEach items="${op:getCodeInfoList('TEL')}" var="tel">
													<option value="${tel.key.id}" ${op:selected(tel.key.id, receiver.receivePhone1)}>${tel.label}</option>
												</c:forEach>
											</select> -
											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receivePhone2"  class="_number" maxlength="4" title="전화번호" value="${receiver.receivePhone2}" /> -
											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receivePhone3"  class="_number" maxlength="4" title="전화번호" value="${receiver.receivePhone3}" />
										</div>
									</td>
									<th class="label">핸드폰번호</th>
									<td>
										<div>
											<select name="orderShippingInfos[${receiverIndex.index}].receiveMobile1" title="핸드폰번호" class="_number required">
												<option value="">-선택-</option>
												<c:forEach items="${op:getCodeInfoList('PHONE')}" var="tel">
													<option value="${tel.key.id}" ${op:selected(tel.key.id, receiver.receiveMobile1)}>${tel.label}</option>
												</c:forEach>
											</select> -
											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receiveMobile2"  class="_number required" maxlength="4" title="핸드폰번호" value="${receiver.receiveMobile2}" /> -
											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receiveMobile3"  class="_number required" maxlength="4" title="핸드폰번호" value="${receiver.receiveMobile3}" />
										</div>
									</td>
								</tr>
								<tr>
									<th class="label">주소</th>
									<td colspan="3">
										<div>
											<input type="hidden" name="orderShippingInfos[${receiverIndex.index}].receiveNewZipcode" value="${receiver.receiveNewZipcode}" />
											<input type="hidden" name="orderShippingInfos[${receiverIndex.index}].receiveSido" value="${receiver.receiveSido}" />
											<input type="hidden" name="orderShippingInfos[${receiverIndex.index}].receiveSigungu" value="${receiver.receiveSigungu}" />
											<input type="hidden" name="orderShippingInfos[${receiverIndex.index}].receiveEupmyeondong" value="${receiver.receiveEupmyeondong}" />

											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receiveZipcode" title="우편번호" maxlength="7" class="required" readonly="readonly" value="${receiver.receiveZipcode}" />
											<a href="javascript:;" onclick="openDaumPostcode('${receiverIndex.index}')" class="btn btn-gradient btn-xs">우편번호</a><br/>
											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receiveAddress" title="주소" maxlength="7" style="width:100%" class="required" readonly="readonly" value="${receiver.receiveAddress}" /><br/>
											<input type="text" name="orderShippingInfos[${receiverIndex.index}].receiveAddressDetail"  style="width:100%" maxlength="100" title="상세주소" value="${receiver.receiveAddressDetail}" />
										</div>
									</td>
								</tr>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</td>
		</tr>


		<c:choose>
			<c:when test="${viewType == 'info'}">
				<c:if test="${!empty receiver.memo}">
					<tr>
						<th scope="row">배송시 요구사항</th>
						<td>
							<div>
									${receiver.memo}
							</div>
						</td>
					</tr>
				</c:if>
			</c:when>
			<c:otherwise>
				<tr>
					<th scope="row">배송시 요구사항</th>
					<td>
						<div>
							<textarea name="orderShippingInfos[${receiverIndex.index}].memo" maxlength="200" class="" title="배송시 요구사항">${receiver.memo}</textarea>
						</div>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>


		<tr>
			<th scope="row">상품정보</th>
			<td>
				<div>
					<table class="inner-table">
						<caption>${op:message('M00059')}</caption>
						<!-- 주문정보 -->
						<colgroup>
							<col style="width: 80px" />
								<%--				<col />
                                                <col style="width: 80px" />
                                                 <col style="width: 120px" />
                                                 <col style="width: 80px" />
                                                 <col style="width: 120px" />
                                                 <col style="width: 120px" />
                                                 <col style="width: 100px" />--%>
						</colgroup>
						<thead>
						<tr>
							<th scope="col" class="none_left">이미지</th>
							<th scope="col" class="none_left">상품정보</th>
							<th scope="col" class="none_left">판매자</th>
							<th scope="col" class="none_left">수량</th>
							<th scope="col" class="none_left">상품금액</th>
							<th scope="col" class="none_left">할인금액</th>
							<th scope="col" class="none_left">결제금액</th>
							<th scope="col" class="none_left">배송비</th>
							<th scope="col" class="none_left">상태</th>
						</tr>
						</thead>
						<tbody id="order_items">
						<c:forEach items="${receiver.orderItems}" var="orderItem" varStatus="orderItemIndex">
							<tr>
								<td>
									<c:set var="imageNameSplit" value="${fn:split(orderItem.imageSrc, '/')}" />
									<c:set var="imageName" value="${imageName[fn:length(imageNameSplit)]}" />
									<img src="${shop:loadImageBySrc(orderItem.imageSrc,'XS')}" alt="${orderItem.itemName}" width="100%"/>
								</td>
								<td>
									[${orderItem.itemUserCode}] ${orderItem.itemName}
										${ shop:viewOptionText(orderItem.options) }
										${shop:viewAdditionOrderItemList(orderItem.additionItemList)}
										${ shop:viewOrderGiftItemList(orderItem.orderGiftItemList) }

										<c:set var="totalItemAmount" value="${orderItem.itemAmount}" />
										<c:set var="totalDiscountAmount" value="${orderItem.discountAmount}" />
										<c:set var="totalItemDiscountAmount" value="${orderItem.itemDiscountAmount}" />
										<c:set var="totalCouponDiscountAmount" value="${orderItem.couponDiscountAmount}" />
										<c:set var="totalUserLevelDiscountAmount" value="${orderItem.userLevelDiscountAmount}" />
										<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />

										<c:forEach items="${orderItem.additionItemList}" var="additionItem">
											<c:set var="totalItemAmount" value="${totalItemAmount + additionItem.itemAmount}" />
											<c:set var="totalDiscountAmount" value="${totalDiscountAmount + additionItem.discountAmount}" />
											<c:set var="totalItemDiscountAmount" value="${totalItemDiscountAmount + additionItem.itemDiscountAmount}" />
											<c:set var="totalCouponDiscountAmount" value="${totalCouponDiscountAmount + additionItem.couponDiscountAmount}" />
											<c:set var="totalUserLevelDiscountAmount" value="${totalUserLevelDiscountAmount + additionItem.userLevelDiscountAmount}" />
											<c:set var="totalSaleAmount" value="${totalSaleAmount + additionItem.saleAmount}" />
										</c:forEach>

									<c:if test="${orderItem.shippingDate != '00000000000000'}">
										<div style="background: #fcf8e8; font-size: 11px; padding: 5px; margin-top: 10px;">

											<c:choose>
												<c:when test="${empty orderItem.deliveryNumber}">
													<c:choose>
														<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
															퀵 배송
														</c:when>
														<c:otherwise>
															직접수령 (${op:date(orderItem.shippingDate)})
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<button type="button" onclick="tracking('${orderItem.deliveryCompanyUrl}', '${orderItem.deliveryNumber }');" class="btn btn-dark-gray btn-xs"><span>배송추적</span></button>
													${orderItem.deliveryCompanyName} [${orderItem.deliveryNumber}] (${op:date(orderItem.shippingDate)})
												</c:otherwise>
											</c:choose>
										</div>
									</c:if>
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
									${op:numberFormat(orderItem.quantity)}개
								</td>
								<td class="text-right">
									${op:numberFormat(totalItemAmount)}원
								</td>
								<td class="text-right">
									<c:choose>
										<c:when test="${totalDiscountAmount > 0}">
											<a href="#" class="op-discount-details-button">-${op:numberFormat(totalDiscountAmount)}원</a>

											<div class="op-discount-details" style="display: none; position: absolute; border: 1px solid #ccc; background: #fbfbfb; font-size: 11px">
												<c:if test="${totalItemDiscountAmount > 0}">
													상품할인 : <span style="display:inline-block; width:70px">${op:numberFormat(totalItemDiscountAmount)}원</span><br />
												</c:if>
												<c:if test="${totalCouponDiscountAmount > 0}">
													쿠폰할인 : <span style="display:inline-block; width:70px">${op:numberFormat(totalCouponDiscountAmount)}원</span><br />
												</c:if>
												<c:if test="${totalUserLevelDiscountAmount > 0}">
													회원할인 : <span style="display:inline-block; width:70px">${op:numberFormat(totalUserLevelDiscountAmount)}원</span>
												</c:if>
											</div>

										</c:when>
										<c:otherwise>
											0원
										</c:otherwise>

									</c:choose>
								</td>
								<td class="text-right" style="font-weight:bold; color:#333">
										${op:numberFormat(totalSaleAmount)}원
								</td>
								<td class="text-right">
									<c:choose>
										<c:when test="${orderItem.isShippingView == 'Y'}">
											<c:choose>
												<c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
													<span style="color:red">
														<c:choose>
															<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
																퀵
															</c:when>
															<c:otherwise>
																${op:numberFormat(orderItem.orderShipping.realShipping)}원
															</c:otherwise>
														</c:choose>
														(착불)
													</span>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${orderItem.orderShipping.payShipping == 0}">무료</c:when>
														<c:otherwise>
															${op:numberFormat(orderItem.orderShipping.payShipping)}원
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
													<span style="color:red">퀵 (착불)</span>
												</c:when>
												<c:otherwise>
													묶음배송
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-center">
									${orderItem.orderStatusLabel}
								</td>
							</tr>


						</c:forEach>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
		</tbody>
	</table>
</c:forEach>