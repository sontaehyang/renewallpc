<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<%@ taglib prefix="inicis"	tagdir="/WEB-INF/tags/pg/inicis" %>
<%@ taglib prefix="lgdacom"	tagdir="/WEB-INF/tags/pg/lgdacom" %>
<%@ taglib prefix="cj"	tagdir="/WEB-INF/tags/pg/cj" %>
<%@ taglib prefix="kakaopay"	tagdir="/WEB-INF/tags/pg/kakaopay" %>
<%@ taglib prefix="kspay"	tagdir="/WEB-INF/tags/pg/kspay" %>
<%@ taglib prefix="kcp"	tagdir="/WEB-INF/tags/pg/kcp" %>
<%@ taglib prefix="easypay"	tagdir="/WEB-INF/tags/pg/easypay" %>
<%@ taglib prefix="nicepay"	tagdir="/WEB-INF/tags/pg/nicepay" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>


<div class="title">
	<h2>주문/결제</h2>
	<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
</div>
<!-- //title -->

<!-- 스마트폰에서 KCP 결제창을 레이어 형태로 구현-->
<div id="layer_all" style="position:absolute; left:0px; top:0px; width:100%;height:100%; z-index:1; display:none;">
    <table height="100%" width="100%" border="-" cellspacing="0" cellpadding="0" style="text-align:center">
        <tr height="100%" width="100%">
            <td>
                <iframe name="frm_all" frameborder="0" marginheight="0" marginwidth="0" border="0" width="100%" height="100%" scrolling="auto"></iframe>
            </td>
        </tr>
    </table>
</div>

<div class="con payment_wrap">
	<div class="order_wrap">
	 	<form:form modelAttribute="buy" name="buy" action="/m/order/pay" method="post">
			<form:hidden path="orderCode" />
			<form:hidden path="orderPrice.totalItemCouponDiscountAmount" />
			<form:hidden path="orderPrice.totalCartCouponDiscountAmount" />
			<form:hidden path="orderPrice.totalCouponDiscountAmount" />
			<form:hidden path="orderPrice.totalPointDiscountAmount" />
			<form:hidden path="orderPrice.totalShippingCouponUseCount" />
			<form:hidden path="orderPrice.totalShippingCouponDiscountAmount" />
			<form:hidden path="orderPrice.totalItemSaleAmount" />
			<form:hidden path="orderPrice.totalShippingAmount" />
			<form:hidden path="orderPrice.orderPayAmount" />
			<form:hidden path="orderPrice.orderPayAmountTotal" />
			<form:hidden path="orderPrice.payAmount" />

			<form:hidden path="orderPrice.totalUserLevelDiscountAmount" />
			
			<c:if test="${requestContext.userLogin == true}">
								
				<c:if test="${ not empty buy.defaultUserDelivery }">
					<div id="op-default-delivery-input-area">
						<form:hidden path="defaultUserDelivery.userName" />
						<form:hidden path="defaultUserDelivery.phone1" />
						<form:hidden path="defaultUserDelivery.phone2" />
						<form:hidden path="defaultUserDelivery.phone3" />
						<form:hidden path="defaultUserDelivery.mobile1" />
						<form:hidden path="defaultUserDelivery.mobile2" />
						<form:hidden path="defaultUserDelivery.mobile3" />
						<form:hidden path="defaultUserDelivery.newZipcode" />
						<form:hidden path="defaultUserDelivery.zipcode" />
						<form:hidden path="defaultUserDelivery.zipcode1" />
						<form:hidden path="defaultUserDelivery.zipcode2" />
						<form:hidden path="defaultUserDelivery.sido" />
						<form:hidden path="defaultUserDelivery.sigungu" />
						<form:hidden path="defaultUserDelivery.eupmyeondong" />
						<form:hidden path="defaultUserDelivery.address" />
						<form:hidden path="defaultUserDelivery.addressDetail"/>
					</div>
				</c:if>
				
				<c:forEach items="${ userDeliveryList }" var="item">
					<div id="delivery-info-${ item.userDeliveryId }">
						<input type="hidden" id="userName" value="${ item.userName }" />
						<input type="hidden" id="phone1" value="${ item.phone1 }" />
						<input type="hidden" id="phone2" value="${ item.phone2 }" />
						<input type="hidden" id="phone3" value="${ item.phone3 }" />
						<input type="hidden" id="mobile1" value="${ item.mobile1 }" />
						<input type="hidden" id="mobile2" value="${ item.mobile2 }" />
						<input type="hidden" id="mobile3" value="${ item.mobile3 }" />
						<input type="hidden" id="zipcode" value="${ item.zipcode }" />
						<input type="hidden" id="zipcode1" value="${ item.zipcode1 }" />
						<input type="hidden" id="zipcode2" value="${ item.zipcode2 }" />
						<input type="hidden" id="sido" value="${ item.sido }" />
						<input type="hidden" id="sigungu" value="${ item.sigungu }" />
						<input type="hidden" id="eupmyeondong" value="${ item.eupmyeondong }" />
						<input type="hidden" id="address" value="${ item.address }" />
						<input type="hidden" id="addressDetail" value="${ item.addressDetail }" />
					</div>
				</c:forEach>
			</c:if>
			
			<div class="order_info">
				<div class="order_tit">
					<h3>${op:message('M00315')}</h3> <!-- 주문자정보 -->
				</div>
				<!-- //order_tit -->
				
				<div class="order_con" id="op-buyer-input-area">
					<ul class="del_info">
						<li>
							<strong class="del_tit t_lgray star">주문자명</strong>
							<span class="del_detail"><form:input path="buyer.userName" title="주문자명" class="required" maxlength="50" /></span>
						</li>
						<li class="chunk">
							<label for="address01" class="del_tit t_lgray star">배송지 주소</label>
							<div class="user_info">
								<div class="in_td">
									<div class="input_area">
										<form:hidden path="buyer.zipcode" />
										<form:hidden path="buyer.sido" />
										<form:hidden path="buyer.sigungu" />
										<form:hidden path="buyer.eupmyeondong" />
										<form:input path="buyer.newZipcode" readonly="true" maxlength="5" title="우편번호" />
									</div>
								</div>
								<div class="in_td bar"></div>
								<div class="in_td address">
									<button type="button" id="address01" class="btn_st3 required" onclick="openDaumPostcode('buyer')">우편번호</button>
								</div>
							</div>
							<div class="input_area">
								<form:input path="buyer.address" class="required" readonly="true" maxlength="100" title="주소" htmlEscape="false"/>
							</div>
							<div class="input_area">
								<form:input path="buyer.addressDetail" class="full required" maxlength="100" title="상세주소" htmlEscape="false"/>
							</div>
						</li>
						<li>
							<strong class="del_tit t_lgray star">휴대폰 번호</strong>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<form:select path="buyer.mobile1" title="휴대폰번호" cssClass="form-control required">
											<form:option value="" label="선택"></form:option>
											<form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
										</form:select>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="buyer.mobile2" title="휴대전화" class="_number required" maxlength="4" /> 
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="buyer.mobile3" title="휴대전화" class="_number required" maxlength="4" /> 
									</div>
								</div>
							</div>
						</li>
						<li>
							<strong class="del_tit t_lgray">전화번호</strong>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<form:select path="buyer.phone1" title="전화번호" cssClass="form-control">
											<form:option value="" label="선택"></form:option>
											<form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
										</form:select>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="buyer.phone2" title="전화번호" class="_number" maxlength="4" /> 
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="buyer.phone3" title="전화번호" class="_number" maxlength="4" /> 
									</div>
								</div>
							</div>
						</li>
						<li>
							<strong class="del_tit t_lgray star">이메일</strong>
							<span class="del_detail">
								<form:input path="buyer.email" title="이메일 주소" class="required _email full" maxlength="50" />
							</span>
						</li>
					</ul>
				</div>
				<!-- //order_con -->
			</div>
			<!-- //order_info -->
			
			<div class="order_get">
				<div class="order_tit">
					<h3>받는사람 정보</h3>
					<div class="shipping_pl">
						<c:if test="${buy.additionItem == false}">
							<c:if test="${buy.multipleDeliveryCount > 1}">
							<label for="shipping_num">복수배송지 선택</label>
								<select id="multiple-delivery-set-count">
									<c:forEach begin="2" end="${buy.multipleDeliveryCount > buy.maxMultipleDelivery ? buy.maxMultipleDelivery : buy.multipleDeliveryCount}" step="1" var="multipleDeliveryValue">
										<option value="${multipleDeliveryValue}">${multipleDeliveryValue}</option>
									</c:forEach>
								</select>
								
								<button type="button" class="btn_st3 t_small" style="display:none;" id="op-cancel-multiple-delivery" onclick="Order.cancelMultipleDelivery()">한곳으로 보내기</button>
								<button type="button" class="btn_st3 t_small " onclick="Order.multipleDelivery()">복수배송지 선택</button>
							</c:if>
						</c:if>
					</div>
					<!-- //shipping_pl -->
				</div>
				<div class="op-receive-input-area">	
					<c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
						<c:forEach items="${receiver.buyQuantitys}" var="buyQuantity" varStatus="buyQuantityIndex">
							<form:hidden path="receivers[${receiverIndex.index}].buyQuantitys[${buyQuantityIndex.index}].itemSequence" value="${buyQuantity.itemSequence}" />
							<form:hidden path="receivers[${receiverIndex.index}].buyQuantitys[${buyQuantityIndex.index}].quantity" value="${buyQuantity.quantity}" />							
						</c:forEach>
						
						<!-- //order_tit -->
						<div id="op-receive-input-area-${receiverIndex.index}">
							<div class="order_con">
								<ul class="del_info">
									<c:if test="${requestContext.userLogin == true}">
										<li>
											<label for="chs_addr" class="del_tit t_lgray">배송지 선택</label>
											<div name="chs_addr" id="chs_addr" class="user_info">
												<select name="infoCopy">
													<c:if test="${ not empty buy.defaultUserDelivery }">
						 								<option value="default-${receiverIndex.index}">${op:message('M01581')}</option> <!-- 기본배송지 -->
						 							</c:if>
													<c:forEach items="${ userDeliveryList }" var="item">
						 								<c:if test="${ item.defaultFlag eq 'N' }">
					 										<option value="delivery-info-${ item.userDeliveryId }-${receiverIndex.index}">${ item.title }</option>
					 									</c:if>
					 								</c:forEach>
					 								
													<option value="copy-${receiverIndex.index}">주문자정보와 동일</option>
													<option value="clear-${receiverIndex.index}">${op:message('M00614')}</option> <!-- 새로운 주소 입력 -->
												</select>
											</div>
										</li>
									</c:if>
									<li>
										<span class="del_tit star t_lgray">받으시는 분</span>
										<div class="input_area">
											<form:input path="receivers[${receiverIndex.index}].receiveName" title="받으시는 분" class="required" maxlength="50" /> 
										</div>
									</li>
									<li class="chunk">
										<label for="address02" class="del_tit t_lgray star">배송지 주소</label>
										<div class="user_info">
											<div class="in_td">
												<div class="input_area">
													<form:hidden path="receivers[${receiverIndex.index}].receiveZipcode" />
													<form:input path="receivers[${receiverIndex.index}].receiveNewZipcode" title="우편번호" maxlength="5" class="required" readonly="true" />
												</div>
											</div>
											<div class="in_td bar"></div>
											<div class="in_td address">
												<button type="button" id="address02" onclick="openDaumPostcode('receive', ${receiverIndex.index})" class="btn_st3">우편번호</button>
											</div>
										</div>
										<div class="input_area">
											<form:hidden path="receivers[${receiverIndex.index}].receiveSido" />
											<form:hidden path="receivers[${receiverIndex.index}].receiveSigungu" />
											<form:hidden path="receivers[${receiverIndex.index}].receiveEupmyeondong" /> 
											<form:input path="receivers[${receiverIndex.index}].receiveAddress" title="주소" class="required" maxlength="100" readonly="true" htmlEscape="false"/>
										</div>
										<div class="input_area">
											<form:input path="receivers[${receiverIndex.index}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" htmlEscape="false"/>
										</div>
										<c:if test="${requestContext.userLogin == true}">
											<div class="add_addr">
												<span class="check">
													<form:checkbox path="saveDeliveryFlag" value="Y" id="add_addr01" />
													<label for="add_addr01">기본 배송지에 추가</label>
												</span>
											</div>
										</c:if>
									</li>
									<li>
										<span class="del_tit t_lgray star">휴대폰 번호</span>
										<div class="num">
											<div class="in_td">
												<div class="input_area">
													<form:select path="receivers[${receiverIndex.index}].receiveMobile1" title="휴대전화" cssClass="form-control required">
														<form:option value="" label="선택"></form:option>
														<form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
													</form:select>
												</div>
												<div class="in_td dash"></div>
												<div class="input_area">
													<form:input path="receivers[${receiverIndex.index}].receiveMobile2" title="휴대전화" class="_number required" maxlength="4" /> 
												</div>
												<div class="in_td dash"></div>
												<div class="input_area">
													<form:input path="receivers[${receiverIndex.index}].receiveMobile3" title="휴대전화" class="_number required" maxlength="4" /> 
												</div>
											</div>
										</div>
									</li>
									<li>
										<span class="del_tit t_lgray">전화번호</span>
										<div class="num">
											<div class="in_td">
												<div class="input_area">
													<form:select path="receivers[${receiverIndex.index}].receivePhone1" title="전화번호" cssClass="form-control">
														<form:option value="" label="선택"></form:option>
														<form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
													</form:select>
												</div>
												<div class="in_td dash"></div>
												<div class="input_area">
													<form:input path="receivers[${receiverIndex.index}].receivePhone2" title="전화번호" class="_number" maxlength="4" /> 
												</div>
												<div class="in_td dash"></div>
												<div class="input_area">
													<form:input path="receivers[${receiverIndex.index}].receivePhone3" title="전화번호" class="_number" maxlength="4" /> 
												</div>
											</div>
										</div>
									</li>
									<li>
										<span class="del_tit t_lgray">배송시 요구사항</span>
										<div class="input_area">
											<form:input path="receivers[${receiverIndex.index}].content" title="배송시 요구사항" class="full _filter" placeholder="ex) 부재시 경비실에 맡겨주세요." />
										</div>
									</li>
								</ul>
							</div>
							<!-- //order_con -->
							
							<c:set var="totalItemRowCount">0</c:set>
							<c:forEach items="${buy.receivers}" var="receiver">
								<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
									<c:set var="singleShipping" value="${shipping.singleShipping}"/>
									<c:choose>
										<c:when test="${singleShipping == true}">
											<c:set var="totalItemRowCount">${totalItemRowCount + 1}</c:set>
										</c:when>
										<c:otherwise>
											<c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="itemIndex">
												<c:set var="totalItemRowCount">${totalItemRowCount + 1}</c:set>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:forEach>
							
							<div class="order_item">
								<ul class="item_list">
									<c:set var="itemIndex">0</c:set>
									<c:set var="cashDiscountFlag" value="N" />
									<c:forEach items="${buy.receivers}" var="receiver" varStatus="receiverIndex">
										<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
						 					<c:set var="singleShipping" value="${shipping.singleShipping}"/>
											<c:choose>
												<c:when test="${singleShipping == true}">
													<c:set var="buyItem" value="${shipping.buyItem}" />
													<c:set var="totalItemSaleAmount" value="${buyItem.itemPrice.itemSaleAmount}" />
													<c:set var="totalSaleAmount" value="${buyItem.itemPrice.saleAmount}" />
													<c:if test="${buyItem.cashDiscountFlag == 'Y'}">
														<c:set var="cashDiscountFlag" value="Y" />
													</c:if>

													<c:forEach items="${buyItem.additionItemList}" var="addition">
														<c:set var="totalItemSaleAmount">${totalItemSaleAmount + addition.itemPrice.itemSaleAmount}</c:set>
														<c:set var="totalSaleAmount">${totalSaleAmount + addition.itemPrice.saleAmount}</c:set>
													</c:forEach>
													<li>
														<div class="item">
															<div class="order_img">
																<%-- <img src="${buyItem.item.imageSrc}" alt="item photo"> --%>
																<img src="${shop:loadImage(buyItem.item.itemUserCode, buyItem.item.itemImage, 'XS')}" alt="item photo">
															</div> 
															<div class="order_name">
																<p class="tit">${ buyItem.item.itemName }</p>
																<p class="detail">
																	${ shop:buyViewOptionText(buyItem.options) }
																	${ shop:viewAdditionItemList(buyItem.additionItemList) }
																</p>
																<c:if test="${not empty buyItem.freeGiftItemText}">
																	<p class="detail">${buyItem.freeGiftItemText}</p>
																</c:if>
															</div>
															<div class="order_price">
																<p class="price">
																	<span class="discount">${op:numberFormat(totalItemSaleAmount)}원</span>
																	<span class="sale_price">${op:numberFormat(totalSaleAmount)}</span>원 (${op:numberFormat(buyItem.itemPrice.quantity)}개)
																</p>
															</div>
														</div>
													</li>
												</c:when>
												<c:otherwise>
													<c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="buyItemIndex">
														<c:set var="buyItem" value="${buyItem}" />
														<c:set var="totalItemSaleAmount" value="${buyItem.itemPrice.itemSaleAmount}" />
														<c:set var="totalSaleAmount" value="${buyItem.itemPrice.saleAmount}" />
														<c:if test="${buyItem.cashDiscountFlag == 'Y'}">
															<c:set var="cashDiscountFlag" value="Y" />
														</c:if>

														<c:forEach items="${buyItem.additionItemList}" var="addition">
															<c:set var="totalItemSaleAmount">${totalItemSaleAmount + addition.itemPrice.itemSaleAmount}</c:set>
															<c:set var="totalSaleAmount">${totalSaleAmount + addition.itemPrice.saleAmount}</c:set>
														</c:forEach>
														<li>
															<div class="item">
																<div class="order_img">
																	<%-- <img src="${buyItem.item.imageSrc}" alt="item photo"> --%>
																	<img src="${shop:loadImage(buyItem.item.itemUserCode, buyItem.item.itemImage, 'XS')}" alt="item photo">
																</div> 
																<div class="order_name">
																	<p class="tit">${ buyItem.item.itemName }</p>
																	<p class="detail">
																		${ shop:buyViewOptionText(buyItem.options) }
																		${ shop:viewAdditionItemList(buyItem.additionItemList) }
																	</p>
																	<c:if test="${not empty buyItem.freeGiftItemText}">
																		<p class="detail">${buyItem.freeGiftItemText}</p>
																	</c:if>
																</div>
																<div class="order_price">
																	<p class="price">
																		<span class="discount">${op:numberFormat(totalItemSaleAmount)}원</span>
																		<span class="sale_price">${op:numberFormat(totalSaleAmount)}</span>원 (${op:numberFormat(buyItem.itemPrice.quantity)}개)
																	</p>
																</div>
															</div>
														</li>
										 			</c:forEach>
												</c:otherwise>
											</c:choose>
						 					
						 					<c:if test="${buy.shippingCoupon > 0}">
												<c:if test="${shipping.shippingType != '5' && shipping.shippingType != '1'}">
													<c:if test="${shipping.shippingPaymentType == '1'}">
														<div class="shipping_coupon where_buy-${shipping.shippingSequence}">
															<span class="del_tit t_gray">배송비 쿠폰</span>
															<span class="check">
																<input type="hidden" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].shippingGroupCode" value="${shipping.shippingGroupCode}" />
														 		<input type="checkbox" class="op-input-shipping-coupon-used" id="shipping_coupon_use${shipping.shippingSequence}" name="useShippingCoupon['SHIPPING-COUPON-${shipping.shippingSequence}'].useFlag" value="Y">
														 		<label for="shipping_coupon_use${shipping.shippingSequence}">사용</label>
															</span>
														</div>
													</c:if>
												</c:if>
											</c:if>
											
											<div class="shipping_coupon">
												<span class="del_tit t_gray">배송비</span>
												<div class="delivery_wrap">
													<c:set var="shippingTypeText" value="" />
													<c:choose>
														<c:when test="${shipping.shippingType == 2}"><c:set var="shippingTypeText" value="판매자" /></c:when>
														<c:when test="${shipping.shippingType == 3}"><c:set var="shippingTypeText" value="출고지" /></c:when>
														<c:when test="${shipping.shippingType == 4}"><c:set var="shippingTypeText" value="상품" /></c:when>
													</c:choose>
													<a href="#" class="btn_st4 t_blue delv_btn">
														${shippingTypeText} 조건부 무료배송
													</a>
													<span class="delievery_tip">
                                                        <c:set var="deliveryText" value="" />
                                                        <c:choose>
															<c:when test="${shipping.shippingType == 2}"><c:set var="deliveryText" value="판매자" /></c:when>
															<c:when test="${shipping.shippingType == 3}"><c:set var="deliveryText" value="동일한 출고지" /></c:when>
															<c:when test="${shipping.shippingType == 4}"><c:set var="deliveryText" value="해당" /></c:when>
														</c:choose>
                                                        <p class="title">${shippingTypeText} 조건부 무료배송 </p>
                                                        <p>${deliveryText} 상품 ${op:numberFormat(shipping.shippingFreeAmount)}원 이상 구매 시 무료,<br>미만 구매시 ${ op:numberFormat(shipping.shipping) }원 부과</p>
														<p><strong>제주/도서산간</strong><br>
															제주
															<c:choose>
																<c:when test="${shipping.shippingExtraCharge1 > 0}">
																	${op:numberFormat(shipping.shippingExtraCharge1)}원 추가
																</c:when>
																<c:otherwise>
																	추가비용 없음
																</c:otherwise>
															</c:choose>
															/ 도서산간
															<c:choose>
																<c:when test="${shipping.shippingExtraCharge2 > 0}">
																	${op:numberFormat(shipping.shippingExtraCharge2)}원 추가
																</c:when>
																<c:otherwise>
																	추가비용 없음
																</c:otherwise>
															</c:choose>
														</p>
                                                        <b class="delievery_close"><img src="/content/images/btn/btn_tooltip_close.gif" alt="close"></b>
                                                    </span>
												</div><!--// delivery_wrap -->

												<div class="shipping_price">
													<span class="op-shipping-text-${shipping.shippingSequence}">
														<c:choose>
															<c:when test="${ shipping.realShipping == 0 }">무료배송</c:when>
															<c:otherwise>
																${ op:numberFormat(shipping.realShipping) }원 
																<%--c:choose>
																	<c:when test="${shipping.shippingPaymentType == '1'}">선불</c:when>
																	<c:otherwise>착불</c:otherwise>
																</c:choose --%>
														    </c:otherwise>
														</c:choose>
													</span>
												</div>
											</div>
										</c:forEach>
									</c:forEach> 
								</ul>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

			<!--// 배송방법 추가 S - 201124 -->
			<div class="delv_met">
				<div class="order_tit">
					<h3>배송방법</h3>
				</div>
				<!-- //order_tit -->
				<div class="order_con">
					<ul class="del_info type-view">
						<li>
							<span class="del_tit t_lgray">배송방법</span>
							<div class="info-view rad_g">
								<p>
									<input id="quickDeliveryFlag1" type="radio" name="quickDeliveryFlag" value="N" checked="checked">
									<label for="quickDeliveryFlag1"><span><span></span></span>일반택배</label>
								</p>
								<p>
									<input id="quickDeliveryFlag2" type="radio" name="quickDeliveryFlag" value="Y">
									<label for="quickDeliveryFlag2"><span><span></span></span>퀵서비스</label>
								</p>
							</div>
						</li>
						<li>
							<span class="del_tit t_lgray">배송비</span>
							<div class="info-view op-quick-delivery-text">${ op:numberFormat(buy.orderPrice.totalShippingAmount) }원</div>
						</li>
						<li>
							<span class="del_tit t_lgray">배송 주의사항</span>
							<div class="info-view txt">
								<p>&middot; 택배</p>
								평일 00시까지 결제완료하시면 당일 발송됩니다.
								<p>&middot; 퀵서비스</p>
								평일 00시까지 결제완료하시면 당일 발송됩니다. <br>퀵서비스 특성 상 다품목, 대량 주문 시 결제하신 운송비 보다 초과할 경우 착불처리되오니 이용에 참고부탁드립니다.
							</div>
						</li>
					</ul>
				</div>
			</div><!--// delv_met -->
			<!--// 배송방법 추가 E -->

			<c:if test="${requestContext.userLogin == true}">		
				<c:if test="${not empty buy.buyPayments['point']}">
					<div class="point_dis">
						<div class="order_tit">
							<h3>쿠폰 할인  / ${op:message('M00620')}</h3>
							<p class="tit_desc">100P 단위로 사용가능</p>
						</div>
						<div class="point_con">
							<label for="point" class="del_tit t_gray">쿠폰</label>
							<div class="user_info coupon-area">
								<div class="in_td">
									<div class="input_area">
										<input type="text" title="쿠폰 할인" class="_number w_30 totalCouponDiscountAmountText" value="${ op:numberFormat(buy.orderPrice.totalCouponDiscountAmount) }" readonly="readonly" onclick="viewCoupon()" />
									</div>
								</div>
								<span>원</span>
								<div class="in_td point_all">
									<button type="button" id="coupon" onClick="javascript:viewCoupon();" class="btn_st3">쿠폰적용</button>
								</div>
							</div>
						</div>
						<div class="point_rst">
							<div class="op-coupon-hide-field-area" style="display:none;">
								<c:if test="${ !empty buy.makeUseCouponKeys }">
		 							<c:forEach items="${ buy.makeUseCouponKeys }" var="value">
		 								<input type="hidden" name="useCouponKeys" value="${ value }" class="useCoupon" />
		 							</c:forEach>
 								</c:if>
							</div>
						</div>
						<!-- //point_rst -->
						<!-- //order_tit -->
						<div class="point_con">
							<label for="point" class="del_tit t_gray">${op:message('M00246')}</label>
							<div class="user_info point-area">
								<div class="in_td">
									<div class="input_area">
										<form:hidden path="buyPayments['point'].amount" />
										<input type="text" class="w70 op-total-point-discount-amount-text" value="${ op:numberFormat(buy.orderPrice.totalPointDiscountAmount) }"
			 							<c:if test="${ buy.retentionPoint == 0 }">disabled="disabled"</c:if>>
									</div>
								</div>
								<span>P</span>
								<div class="in_td point_all">
									<button type="button" id="point" onClick="javascript:retentionPointUseAll();" class="btn_st3">모두사용</button>
								</div>
							</div>
						</div>
						<div class="point_rst">
							<span class="del_tit t_gray t_gray">사용가능 ${op:message('M00246')}</span>
							<p class="point_total">
								<span>
									<c:if test="${ pointUseMin > 0 && buy.retentionPoint > 0 }">
										${ op:numberFormat(pointUseMin) } ~ 
									</c:if>
									${ op:numberFormat(buy.retentionPoint) }
								</span>P
							</p>
						</div>
					</div>
					<!-- //point_dis -->
				</c:if>
			</c:if>
			<div class="cart_order">
				<div class="txt_wrap">
					<div class="order total">
						<span class="tit">상품금액</span>
						<p class="total_price"><span>${ op:numberFormat(buy.orderPrice.totalItemPrice) }</span>원</p>
					</div>
					<div class="sale total"> 
						<c:set var="buyTotalDiscount">${buy.orderPrice.totalItemCouponDiscountAmount}</c:set>
						<span class="tit">할인금액</span>
						<p class="total_price"><span class="op-total-discount-amount-text">${ op:numberFormat(buyTotalDiscount) }</span>원</p>  
					</div>
					<div class="shipping total"> 
						<span class="tit">배송비(세금포함)</span>
						<p class="total_price"><span><label class="op-total-delivery-charge-text">${ op:numberFormat(buy.orderPrice.totalShippingAmount) }</label></span>원</p>  
					</div>
					<div class="sum">
						<span class="tit">최종 결제금액</span>
						<p class="total_price"><span class="op-order-pay-amount-text">${ op:numberFormat(buy.orderPrice.orderPayAmount) }</span>원</p>
					</div>
					<c:if test="${requestContext.userLogin == true}">	
						<div class="point_desc">
							<p>적립예정 ${op:message('M00246')} <span class="op-earn-point-text">${op:numberFormat(buy.orderPrice.totalEarnPoint)}</span></p>
						</div>
					</c:if>
				</div>
				<!-- //txt_wrap -->
			</div>
			<!-- //cart_order -->
			
			<div class="payment_method" <c:if test="${ buy.defaultPaymentType == 'noPay' }">style="display:none"</c:if>>
				<div class="order_tit">
					<h3>결제 정보</h3>
				</div>
				<!-- //order_tit -->
				<div class="method_tab">
					<ul>
						<c:if test="${ cashDiscountFlag == 'N'}">
							<li class="op-pay-type-select op-pay-type-naverpay-card-select">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('naverpayCard')">
										<input type="checkbox" id="op-payType-naverpayCard" value="card" name="payType" class="hide" />
										네이버페이
									</a>
								</span>
							</li>
						</c:if>
						<c:if test="${ not empty buy.buyPayments['card'] && cashDiscountFlag == 'N'}">
							<li class="op-pay-type-select op-pay-type-card-select <c:if test="${ buy.defaultPaymentType == 'card' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('card')">
										<input type="checkbox" id="op-payType-card" value="card" name="payType" class="hide" 
										<c:if test="${ buy.defaultPaymentType == 'card' }">checked="checked"</c:if> />
										신용카드
									</a>
								</span>
							</li>
						</c:if>
						<c:if test="${ not empty buy.buyPayments['vbank'] }">
							<li class="op-pay-type-select op-pay-type-vbank-select <c:if test="${ buy.defaultPaymentType == 'vbank' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('vbank')">
									<input type="checkbox" id="op-payType-vbank" value="vbank" name="payType" class="hide" 
										<c:if test="${ buy.defaultPaymentType == 'vbank' }">checked="checked"</c:if>/>
										가상 계좌
									</a>
								</span>
							</li>
						</c:if>
						<c:if test="${ not empty buy.buyPayments['bank'] }">
							<li class="op-pay-type-select op-pay-type-bank-select <c:if test="${ buy.defaultPaymentType == 'bank' }">on</c:if> last">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('bank')">
										<input type="checkbox" id="op-payType-bank" value="bank" name="payType" class="hide" 
											<c:if test="${ buy.defaultPaymentType == 'bank' }">checked="checked"</c:if>/>
										무통장 입금
									</a>
								</span>
							</li>
						</c:if>
						<c:if test="${ not empty buy.buyPayments['escrow'] && cashDiscountFlag == 'N'}">
							<li class="op-pay-type-select op-pay-type-escrow-select <c:if test="${ buy.defaultPaymentType == 'escrow' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('escrow')">
										<input type="checkbox" id="op-payType-escrow" value="escrow" name="payType" class="hide" 
											<c:if test="${ buy.defaultPaymentType == 'escrow' }">checked="checked"</c:if>/>
										에스크로
									</a>
								</span>
							</li>
						</c:if>
						<c:if test="${ not empty buy.buyPayments['realtimebank'] }">
							<li class="op-pay-type-select op-pay-type-realtimebank-select <c:if test="${ buy.defaultPaymentType == 'realtimebank' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('realtimebank')">
										<input type="checkbox" id="op-payType-realtimebank" value="realtimebank" name="payType" class="hide" 
											<c:if test="${ buy.defaultPaymentType == 'realtimebank' }">checked="checked"</c:if>/>
										실시간 계좌이체
									</a>
								</span>
							</li>
						</c:if>
                        <c:if test="${ not empty buy.buyPayments['hp'] && cashDiscountFlag == 'N'}">
                            <li class="op-pay-type-select op-pay-type-hp-select <c:if test="${ buy.defaultPaymentType == 'hp' }">on</c:if>">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('hp')">
										<input type="checkbox" id="op-payType-hp" value="hp" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'hp' }">checked="checked"</c:if>/>
										휴대폰 결제
									</a>
								</span>
                            </li>
                        </c:if>
                        <c:if test="${ not empty buy.buyPayments['naverpay'] && cashDiscountFlag == 'N'}">
                            <li class="op-pay-type-select op-pay-type-naverpay-select <c:if test="${ buy.defaultPaymentType == 'naverpay' }">on</c:if> last">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('naverpay')">
										<input type="checkbox" id="op-payType-naverpay" value="naverpay" name="payType" class="hide"
                                               <c:if test="${ buy.defaultPaymentType == 'naverpay' }">checked="checked"</c:if>/>
										네이버페이
									</a>
								</span>
                            </li>
                        </c:if>
						<%--<c:if test="${ not empty buy.buyPayments['kakaopay'] }">
							<li class="op-pay-type-select op-pay-type-kakaopay-select <c:if test="${ buy.defaultPaymentType == 'kakaopay' }">on</c:if> last">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('kakaopay')">
										<input type="checkbox" id="op-payType-kakaopay" value="kakaopay" name="payType" class="hide" 
											<c:if test="${ buy.defaultPaymentType == 'kakaopay' }">checked="checked"</c:if>/>
										카카오페이
									</a>
								</span>
							</li>
						</c:if>
						<c:if test="${ not empty buy.buyPayments['payco'] }">
							<li class="op-pay-type-select op-pay-type-payco-select <c:if test="${ buy.defaultPaymentType == 'payco' }">on</c:if> last">
								<span>
									<a href="javascript:;" onclick="payTypeSelect('payco')">
										<input type="checkbox" id="op-payType-payco" value="payco" name="payType" class="hide" 
											<c:if test="${ buy.defaultPaymentType == 'payco' }">checked="checked"</c:if>/>
										payco
									</a>
								</span>
							</li>
						</c:if>--%>
					</ul>
				</div>
					<!-- //method_tab -->
					
				<div class="method_con_wrap">
					<c:if test="${ not empty buy.buyPayments['card'] }">
					
						<div class="pgInputArea">
						
							<c:choose>
								<c:when test="${pgService == 'inicis' }">
									<inicis:inipay-mobile-input />
								</c:when>
								<c:when test="${pgService == 'lgdacom' }">
									<lgdacom:xpay-mobile-input />
								</c:when>
								<c:when test="${pgService == 'cj' }">
									<cj:cj-mobile-input />
								</c:when>
								<c:when test="${pgService == 'kspay' }">
									<kspay:kspay-mobile-input />
								</c:when>
								<c:when test="${pgService == 'kcp' }">
									<kcp:kcp-mobile-input />
								</c:when>
								<c:when test="${pgService == 'easypay' }">
									<easypay:easypay-mobile-input />
								</c:when>
								<c:when test="${pgService == 'nicepay' }">
									<nicepay:nicepay-mobile-input />
								</c:when>
							</c:choose>
	
						</div>
						
						<div class="method_con op-payType-input" id="op-payType-card-input" <c:if test="${ buy.defaultPaymentType != 'card' }">style="display:none;"</c:if>>
							<span class="del_tit t_gray card_title">신용카드 결제금액</span>
							<p class="method_price">
								<span class="op-order-payAmount-text">
									${buy.defaultPaymentType == 'card' ? buy.orderPrice.orderPayAmount : 0 }
								</span>원
								<form:hidden path="buyPayments['card'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'card' ? 'op-default-payment' : '' }"  
										paymentType="card" value="${buy.defaultPaymentType == 'card' ? buy.orderPrice.orderPayAmount : 0 }" />
							</p>
						</div>
						<!-- //method_con -->
					</c:if>
					<c:if test="${ not empty buy.buyPayments['vbank'] }">
						<div class="method_con op-payType-input" id="op-payType-vbank-input" <c:if test="${ buy.defaultPaymentType != 'vbank' }">style="display:none;"</c:if>>
							<span class="del_tit t_gray">가상계좌 결제금액</span>
							<p class="method_price">
								<span class="op-order-payAmount-text">
									${buy.defaultPaymentType == 'vbank' ? buy.orderPrice.orderPayAmount : 0 }
								</span>원
								<form:hidden path="buyPayments['vbank'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'vbank' ? 'op-default-payment' : '' }" 
											paymentType="vbank" value="${buy.defaultPaymentType == 'vbank' ? buy.orderPrice.orderPayAmount : 0 }" />
							</p>
							<c:if test="${useEscrow eq 'Y'}">
								<tr> 
									<th scope="row">에스크로 사용여부</th> 
									<td> 
										<div class="input_wrap col-w-7">
											<form:select id="escrowStatus" path="buyPayments['vbank'].escrowStatus" cssClass="form-control" title="에스크로 사용여부">
												<form:option value="0" label="사용"></form:option>
												<form:option value="N" label="사용 안함" selected="selected"></form:option>													
											</form:select>
										</div>
									</td> 
								</tr>
							</c:if>							
						</div>
						<!-- //method_con -->
					</c:if>
					<c:if test="${ not empty buy.buyPayments['bank'] }">
						<div class="method_con op-payType-input" id="op-payType-bank-input" <c:if test="${ buy.defaultPaymentType != 'bank' }">style="display:none;"</c:if>>
							<span class="del_tit t_gray">무통장입금 결제금액</span>
							<p class="method_price">
								<span class="op-order-payAmount-text">
									${buy.defaultPaymentType == 'bank' ? buy.orderPrice.orderPayAmount : 0 }
								</span>원
								<form:hidden path="buyPayments['bank'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'bank' ? 'op-default-payment' : '' }"  
										paymentType="bank" value="${buy.defaultPaymentType == 'bank' ? buy.orderPrice.orderPayAmount : 0 }" />
							</p>
							<ul class="method_detail">
								<li>무통장 입금시 발생하는 수수료는 손님 부담입니다.</li>
	 							<li>
	 								인터넷 뱅킹 또는 은행창구 입금시 의뢰인(송금인)명은 ‘입금인 입력’란에 입금하신 성함과 동일하게 
									기재해 주시기 바랍니다. ( 만약 다를 경우 고객센터 <span>1544-2880</span>로 꼭 연락주시기 바랍니다.)
	 							</li>
	 							<li>무통장 입금시 입금자와 입금 예정일을 입력해주세요.</li>
	 							<li>현금영수증 미신청시 현금영수증 발급이 되지 않습니다.</li>
							</ul>
							<div class="user_info_wrap">
								<label for="order_bank" class="del_tit t_gray">입금은행</label>
								<div class="user_info">
									<div class="in_td">
										<form:select path="buyPayments['bank'].bankVirtualNo" title="입금은행">
											<c:forEach items="${ buy.buyPayments['bank'].accountNumbers }" var="list">
		 										<c:set var="accountValue" value="${ list.bankName } 계좌번호： ${ list.accountNumber } (${ list.accountHolder })" />
		 										<form:option value="${ accountValue }" />
		 									</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
							<!-- //user_info_wrap -->
							<div class="user_info_wrap">
								<label for="deposit_name" class="del_tit t_gray">입금자명</label>
								<div class="user_info">
									<div class="in_td">
										<form:input path="buyPayments['ourvbank'].bankInName" title="입금자명" cssClass="right w_96" />
									</div>
								</div>
							</div>
							
							<div class="cashbill_desc">
								<ul>
									<li><a href="/m/order/payment-guide?paymentType=bank" class="t_lgray t_medium">무통장입금 이용안내</a></li>
<!-- 									<li><a href="/m/order/payment-guide?paymentType=tax" class="t_lgray t_medium">현금영수증 안내</a></li> -->
								</ul>
							</div>
							<!-- //cashbill_desc -->
						</div>
						<!-- //method_con -->
					</c:if>
					<c:if test="${ not empty buy.buyPayments['escrow'] }">
						<div class="method_con op-payType-input" id="op-payType-escrow-input" <c:if test="${ buy.defaultPaymentType != 'escrow' }">style="display:none;"</c:if>>
							<span class="del_tit t_gray">에스크로 결제금액</span>
							<p class="method_price">
								<span class="op-order-payAmount-text">
									${buy.defaultPaymentType == 'escrow' ? buy.orderPrice.orderPayAmount : 0 }
								</span>원
								<form:hidden path="buyPayments['escrow'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'escrow' ? 'op-default-payment' : '' }" 
											paymentType="escrow" value="${buy.defaultPaymentType == 'escrow' ? buy.orderPrice.orderPayAmount : 0 }" />
							</p>
						</div>
						<!-- //method_con -->
					</c:if>
					<c:if test="${ not empty buy.buyPayments['realtimebank'] }">
						<div class="method_con op-payType-input" id="op-payType-realtimebank-input" <c:if test="${ buy.defaultPaymentType != 'realtimebank' }">style="display:none;"</c:if>>
							<span class="del_tit t_gray">실시간 계좌이체 결제금액</span>
							<p class="method_price">
								<span class="op-order-payAmount-text">
									${buy.defaultPaymentType == 'realtimebank' ? buy.orderPrice.orderPayAmount : 0 }
								</span>원
								<form:hidden path="buyPayments['realtimebank'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'realtimebank' ? 'op-default-payment' : '' }" 
											paymentType="realtimebank" value="${buy.defaultPaymentType == 'realtimebank' ? buy.orderPrice.orderPayAmount : 0 }" />
							</p>
							<c:if test="${useEscrow eq 'Y'}">
								<tr> 
									<th scope="row">에스크로 사용여부</th> 
									<td> 
										<div class="input_wrap col-w-7">
											<form:select id="escrowStatus" path="buyPayments['realtimebank'].escrowStatus" cssClass="form-control" title="에스크로 사용여부">
												<form:option value="0" label="사용"></form:option>
												<form:option value="N" label="사용 안함" selected="selected"></form:option>													
											</form:select>
										</div>
									</td> 
								</tr>
							</c:if>
							<div class="cashbill_desc">
								<ul>
									<li><a href="/m/order/payment-guide?paymentType=realtimebank" class="t_lgray t_medium">실시간 계좌이체 안내</a></li>
								</ul>
							</div>
							<!-- //cashbill_desc -->
						</div>
						<!-- //method_con -->
					</c:if>
					<c:if test="${ not empty buy.buyPayments['hp'] }">
						<div class="method_con op-payType-input" id="op-payType-hp-input" <c:if test="${ buy.defaultPaymentType != 'hp' }">style="display:none;"</c:if>>
							<span class="del_tit t_gray">휴대폰 결제금액</span>
							<p class="method_price">
								<span class="op-order-payAmount-text">
									${buy.defaultPaymentType == 'hp' ? buy.orderPrice.orderPayAmount : 0 }
								</span>원
								<form:hidden path="buyPayments['hp'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'hp' ? 'op-default-payment' : '' }" 
											paymentType="hp" value="${buy.defaultPaymentType == 'hp' ? buy.orderPrice.orderPayAmount : 0 }" />
							</p>
							<ul class="method_detail">
								<li>휴대폰 결제 한도가 30만원에서 50만원으로 변경되었습니다.</li>
								<li>단, 결제한도는 통신사별 회원등급에 따라 적용됩니다.</li>
							</ul>
						</div>
						<!-- //method_con -->
					</c:if>
					<c:if test="${ not empty buy.buyPayments['kakaopay'] }">
					 	<div class="method_con op-payType-input" id="op-payType-kakaopay-input" <c:if test="${ buy.defaultPaymentType != 'kakaopay' }">style="display:none;"</c:if>>
							<span class="del_tit t_gray">KakaoPay 결제</span>
							<p class="method_price">
								<span class="op-order-payAmount-text">
									${buy.defaultPaymentType == 'kakaopay' ? buy.orderPrice.orderPayAmount : 0 }
								</span> 원
							</p>
							<div class="input_wrap col-w-7 op-kakaopay-request-data"> 
								<form:hidden path="buyPayments['kakaopay'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'kakaopay' ? 'op-default-payment' : '' }" 
									paymentType="kakaopay" value="${buy.defaultPaymentType == 'kakaopay' ? buy.orderPrice.orderPayAmount : 0 }" />
								<kakaopay:kakaopay-input />
							</div>
						</div> 
					</c:if>
                    <c:if test="${ not empty buy.buyPayments['payco'] }">
                        <div class="method_con op-payType-input" id="op-payType-payco-input" <c:if test="${ buy.defaultPaymentType != 'payco' }">style="display:none;"</c:if>>
                            <span class="del_tit t_gray">payco 결제</span>
                            <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'payco' ? buy.orderPrice.orderPayAmount : 0 }
                                </span> 원
                            </p>
                            <div class="input_wrap col-w-7 op-payco-request-data">
                                <form:hidden path="buyPayments['payco'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'payco' ? 'op-default-payment' : '' }"
                                             paymentType="payco" value="${buy.defaultPaymentType == 'payco' ? buy.orderPrice.orderPayAmount : 0 }" />
                                <kakaopay:kakaopay-input />
                            </div>
                        </div>
                    </c:if>
					<!--//user_info_wrap -->
					<c:if test="${pgService != 'nicepay'}">
						<div id="cashbill_view" style="border: 1px solid #999; margin:15px; ; padding-left: 15px;display: none;">
							<div class="cashbill">
								<span class="del_tit">현금영수증</span>
								<ul class="cashbill_list">
									<c:forEach items="${cashbillTypes}" var="cashbillType" varStatus="i">
										<li class="cashbillType_${i.count}">
											<form:radiobutton path="cashbill.cashbillType" value="${cashbillType.code}" id="cashbill_${i.count}" checked="${cashbillType.code == 'NONE' ? 'checked' : ''}" />
											<label for="cashbill_${i.count}"><span><span></span></span>${cashbillType.title}</label>
										</li>
									</c:forEach>
								</ul>
							</div>
							<!--// cashbill -->

							<div class="cashbill_con">
								<div class="receipt_evidence_con op-receipt-all" id="op-receipt-evidence" style="display:none;">
									<span class="tit t_gray t_medium">사업자 등록번호</span>
									<div class="num">
										<div class="in_td">
											<div class="input_area">
												<form:input path="cashbill.businessNumber1" maxlength="3" class="_number businessNumber" title="사업자 등록번호 앞자리" />
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<form:input path="cashbill.businessNumber2" maxlength="2" class="_number businessNumber" title="사업자 등록번호 중간자리" />
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<form:input path="cashbill.businessNumber3" maxlength="5" class="_number businessNumber" title="사업자 등록번호 끝자리" />
											</div>
										</div>
									</div>
								</div>
								<!-- //receipt_evidence_con -->
								<div class="receipt_personal_con op-receipt-all" id="op-receipt-personal" style="display:none;">
									<span class="tit t_gray t_medium">휴대폰 번호</span>
									<div class="num">
										<div class="in_td">
											<div class="input_area">
												<form:input path="cashbill.cashbillPhone1" maxlength="4" class="_number cashbillPhone" title="휴대폰 번호 앞자리"/>
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<form:input path="cashbill.cashbillPhone2" maxlength="4"  class="_number cashbillPhone" title="휴대폰 번호 중간자리"/>
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<form:input path="cashbill.cashbillPhone3" maxlength="4" class="_number cashbillPhone" title="휴대폰 번호 끝자리"/>
											</div>
										</div>
									</div>
								</div>
								<!--//receipt_personal_con -->
							</div>
						</div>
						<!--//cashbill_con -->
					</c:if>
                    <c:if test="${ not empty buy.buyPayments['naverpay'] }">
                        <div class="method_con op-payType-input" id="op-payType-naverpay-input" <c:if test="${ buy.defaultPaymentType != 'naverpay' }">style="display:none;"</c:if>>
                            <span class="del_tit t_gray">네이버페이</span>
                            <p class="method_price">
								<span class="op-order-payAmount-text">
                                        ${buy.defaultPaymentType == 'naverpay' ? buy.orderPrice.orderPayAmount : 0 }
                                </span> 원
                            </p>
                            <div class="input_wrap col-w-7 op-naverpay-request-data">
                                <form:hidden path="buyPayments['naverpay'].amount" class="_number op-order-payAmounts ${buy.defaultPaymentType == 'naverpay' ? 'op-default-payment' : '' }"
                                             paymentType="naverpay" value="${buy.defaultPaymentType == 'naverpay' ? buy.orderPrice.orderPayAmount : 0 }" />
                            </div>
                            <ul class="method_detail">
                                <li>주문 변경 시 카드사 혜택 및 할부 적용 여부는 해당 카드사 정책에 따라
                                    변경될 수 있습니다.</li>
                                <li>네이버페이는 네이버ID로 별도 앱 설치 없이 신용카드 또는 은행계좌 정보를 등록하여
                                    네이버페이 비밀번호로 결제할 수 있는 간편결제 서비스입니다.</li>
                                <li>결제 가능한 신용카드: 신한, 삼성, 현대, BC, 국민, 하나, 롯데, NH농협, 씨티,
                                    카카오뱅크</li>
                                <li>결제 가능한 은행: NH농협, 국민, 신한, 우리, 기업, SC제일, 부산, 경남, 수협, 우체국,
                                    미래에셋대우, 광주, 대구, 전북, 새마을금고, 제주은행, 신협, 하나은행, 케이뱅크,
                                    카카오뱅크, 삼성증권, KDB산업은행,씨티은행, SBI은행, 유안타증권</li>
                                <li>네이버페이 카드 간편결제는 네이버페이에서 제공하는 카드사 별 무이자,
                                    청구할인 혜택을 받을 수 있습니다.</li>
                            </ul>
                        </div>
                    </c:if>
				</div>
				<!-- //method_con_wrap -->
			</div>
			<!-- //payment_method -->
	
			<div class="agree_wrap">
				<span class="check">
				 	<input id="agree_check" type="checkbox" name="checkbox" class="required" title="구매동의">
					<label for="agree_check">주문 상품의 상품명, 상품가격, 배송정보를 확인하였으며, 구매에 동의 하시겠습니까?</label>
				</span>
				
				<c:if test="${not empty buy.sellerNames}">
					<div class="agree_con">
						<p class="tit t_medium t_black">개인정보 제3자 제공 및 수집/이용동의</p>
						<div class="desc"><p class="t_medium t_gray">개인정보 제3자 제공 및 수집/이용</p><a href="javascript:;" class="open_btn t_medium t_gray"><span class="txt">펼쳐보기</span><span class="arr"></span></a></div>
						<div class="agree_box">
							<ul>
								<li>개인정보를 제공받는 자 : ${buy.sellerNames}</li>
								<li>개인정보를 제공받는 자의 개인정보 이용 목적 : 주문상품의 배송, 고객상담 의 불만처리</li>
								<li>제공하는 개인정보의 항목 : 성명,주소,연락처(안심번호 적용시 연락처는 제외)</li>
								<li>개인정보를 제공받는 자의 개인정보 보유 및 이용기간 : 이용목적 달성 시 까지 제공정보는 주문처리 및 배송을 위한 목적으로만 사용됩니다.</li>
								<li>고객님께서는 동의를 거부하실 수 있으며, 거부하실 경우 거래가 제한되며 고객님의 정보는 판매자에게 제공되지 않습니다.</li>
							</ul>
						</div>
					</div>
				</c:if>
			</div>
			<!-- //agree_wrap -->
			
			<div class="order_btn_wrap">
				<button type="button" class="btn_st1 b_gray" onclick="location.href='/m/cart'" >돌아가기</button>
				<button type="submit" id="payment-button" class="btn_st1 b_pink">결제하기</button>
                <button type="submit" id="naver-payment-button" class="btn_st1 npay_btn_mobile" style="display: none;">&nbsp</button>
			</div>

		</form:form>
		
		<div class="hide op-default-multiple-receiver-view">
			<div id="op-receiver-{RECEIVER_INDEX}" style="display:none">
				<div class="order_tit">
					<h3>받는사람 정보{RECEIVER_VIEW_INDEX}</h3>
				</div>
				<div class="order_con">
					<ul class="del_info type-view">
						<li>
							<span class="del_tit star t_lgray">받으시는 분</span>
							<div class="info-view">
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveName" title="받으시는 분" class="required" maxlength="50" />
								{receiveName}
							</div>
						</li>
						<li class="chunk">
							<span class="del_tit t_lgray star">배송지 주소</span>
							<div class="info-view">
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveNewZipcode" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveZipcode" title="우편번호" maxlength="7" class="required" readonly="true" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveSido" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveSigungu" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveEupmyeondong" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveAddress" title="주소" style="width:80%" class="required" maxlength="100" readonly="true" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" />
								({receiveZipcode}) {receiveAddress} {receiveAddressDetail}
							</div> 
						</li>
						<li>
							<span class="del_tit t_lgray star">휴대폰 번호</span>
							<div class="info-view">
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile1" title="휴대전화" class="_number required" maxlength="4" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile2" title="휴대전화" class="_number required" maxlength="4" />
								<input type="hidden" name="receivers[{RECEIVER_INDEX}].receiveMobile3" title="휴대전화" class="_number required" maxlength="4" />
								{receiveMobile1}-{receiveMobile2}-{receiveMobile3}
							</div>
						</li>
						<li>
							<span class="del_tit t_lgray">배송시 요구사항</span>
							<div class="info-view">
								<input type="text" name="receivers[{RECEIVER_INDEX}].content" title="배송시 요구사항" class="full _filter" placeholder="ex) 부재시 경비실에 맡겨주세요." />
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>	<!--//order_wrap E-->
</div>	<!--//con E-->

<%--
<div class="order_item">
	<ul class="item_list">
		<li>
			<div class="item">
				<div class="order_img">
					<img src="/content/mobile/images/common/cart_noimage.jpg" alt="제품이미지">
				</div>
				<div class="order_name">
					<p class="tit">[자연의벗] 첫 눈의 화사함 파우더 팩트 2호 (베이지,15g)</p>
					<p class="detail">01 첫눈의 화사함 파우더 팩트 15g 2호 추가구성품 리필2호(+10,000원)</p>
				</div>
				<div class="order_price">
					<p class="price">상품가격 <span>21,254,000</span>원 / </p>
					<p class="quantity">수량 <span>1</span>개</p>
				</div>
			</div>
		</li>
		<li>
			<div class="item">
				<div class="order_img">
					<img src="/content/mobile/images/common/cart_noimage.jpg" alt="제품이미지">
				</div>
				<div class="order_name">
					<p class="tit">[자연의벗] 첫 눈의 화사함 파우더 팩트 2호 (베이지,15g)</p>
					<p class="detail">01 첫눈의 화사함 파우더 팩트 15g 2호 추가구성품 리필2호(+10,000원)</p>
				</div>
				<div class="order_price">
					<p class="price">상품가격 <span>21,254,000</span>원 / </p>
					<p class="quantity">수량 <span>1</span>개</p>
				</div>
			</div>
		</li>
	</ul>
	<!-- //item_list -->
</div>
--%>

<page:javascript>
<c:choose>
	<c:when test="${shopContext.mobileLayer == true}"><daum:address-layer /></c:when>
	<c:otherwise><daum:address /></c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${pgService == 'inicis'}">
		<inicis:inipay-mobile-script />
	</c:when>
	<c:when test="${pgService == 'lgdacom' }">
		<lgdacom:xpay-mobile-script />
	</c:when>
	<c:when test="${pgService == 'cj' }">
		<cj:cj-mobile-script />
	</c:when>
	<c:when test="${pgService == 'kspay' }">
		<kspay:kspay-mobile-script />
	</c:when>
	<c:when test="${pgService == 'kcp' }">
		<kcp:kcp-mobile-script />
	</c:when>
	<c:when test="${pgService == 'easypay' }">
		<easypay:easypay-mobile-script />
	</c:when>
	<c:when test="${pgService == 'nicepay' }">
		<nicepay:nicepay-mobile-script />
	</c:when>
</c:choose>

<c:if test="${ not empty buy.buyPayments['kakaopay'] }">
	<kakaopay:kakaopay-script />
</c:if>


<script type="text/javascript" src="/content/modules/op.order.js"></script>
<script src="https://nsp.pay.naver.com/sdk/js/naverpay.min.js"></script>
<script type="text/javascript">
//<![CDATA[
var DaumConversionDctSv="type=P,orderID=,amount=";
var DaumConversionAccountID="AhEsM4T6uIfTz.omUTDgpw00";
if(typeof DaumConversionScriptLoaded=="undefined"&&location.protocol!="file:"){
	var DaumConversionScriptLoaded=true;
	document.write(unescape("%3Cscript%20type%3D%22text/javas"+"cript%22%20src%3D%22"+(location.protocol=="https:"?"https":"http")+"%3A//t1.daumcdn.net/cssjs/common/cts/vr200/dcts.js%22%3E%3C/script%3E"));
}
//]]>

Order.init(${userData}, "${pgService}", '${shopConfig.minimumPaymentAmount}', '${shopConfig.pointUseMin}', '', '${shopConfig.pointUseRatio}');
$(function(){
	
	Order.setShippingAmount(); // 쿠폰 사용 체크 2017-04-25 yulsun.yoo

    $('input[name="cashbill.cashbillType"]').on('click', function(e){
        document.getElementById('cashbill.cashbillPhone1').value = '';
        document.getElementById('cashbill.cashbillPhone2').value = '';
        document.getElementById('cashbill.cashbillPhone3').value = '';

        document.getElementById('cashbill.businessNumber1').value = '';
        document.getElementById('cashbill.businessNumber2').value = '';
        document.getElementById('cashbill.businessNumber3').value = '';

        $('input.businessNumber').removeClass('required');
        $('input.cashbillPhone').removeClass('required');

        if ($(this).val() == 'BUSINESS') {
            $('div#op-receipt-personal').hide();
            $('div#op-receipt-evidence').show();

            $('input.businessNumber').addClass('required');
        } else if ($(this).val() == 'PERSONAL') {
            $('div#op-receipt-evidence').hide();
            $('div#op-receipt-personal').show();

            $('input.cashbillPhone').addClass('required');
        } else {
            $('div#op-receipt-evidence').hide();
            $('div#op-receipt-personal').hide();
        }
    });
	
	// 모바일 키페드 셋팅
	Common.setMobileKeypad(true);
	
	historyBackDataSet();
	
	$("#buy").validator({
		'requiredClass' : 'required',
		'submitHandler' : function() {
			try {
                Order.setAmountText(false);
                if (Order.checkPayAmount() == false) {
                    return false;
                }

                var orderPayAmount = $('input[name="orderPrice.orderPayAmount"]').val();

                if (orderPayAmount < 0) {
                    alert('결제 금액을 확인하십시오. 결제 요청액 : ' + orderPayAmount);
                    return false;
                }


                if ($('input[name="payType"][value="bank"]').prop('checked') == true) {

                    if ($('input.op-order-payAmounts[paymentType="bank"]').val() > 0) {

                        $bankVirtualNo = $('select[name="buyPayments[\'bank\'].bankVirtualNo"]');
                        if ($bankVirtualNo.find(':selected').val() == '') {
                            alert($bankVirtualNo.attr('title') + '입력해주세요.');
                            $bankVirtualNo.focus();
                            return false;
                        }

                        $bankInName = $('input[name="buyPayments[\'bank\'].bankInName"]');
                        if ($bankInName.val() == '') {
                            alert($bankInName.attr('title') + '입력해주세요.'); // 입력
                            $bankInName.focus();
                            return false;
                        }

                        $bankDate = $('select[name="buyPayments[\'bank\'].bankDate"]');
                        if ($bankDate.find(':selected').val() == '') {
                            alert($bankDate.attr('title') + '선택해주세요.'); // 선택
                            $bankDate.focus();
                            return false;
                        }
                    }
                }

                var isSuccess = false;

                var savePaymentType = [];
                $.post('/m/order/save', $("#buy").serialize(), function(response){
                    Common.responseHandler(response, function(response) {

                        isSuccess = true;
                        $('input[name="orderCode"]').val(response.data.orderCode);
                        savePaymentType = response.data.savePaymentType;

                        if (Order.getApprovalType(savePaymentType, 'card') || Order.getApprovalType(savePaymentType, 'vbank') || Order.getApprovalType(savePaymentType, 'realtimebank')
                                || Order.getApprovalType(savePaymentType, 'hp')
                                || (Order.pgType == 'inicis' && Order.getApprovalType(savePaymentType, 'escrow'))) {

                            if (Order.pgType == 'inicis') {

                                $.each(response.data.pgData, function(key, value){
                                    $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                });

                            } else if (Order.pgType == 'lgdacom') {
                                $.each(response.data.pgData, function(key, value){
                                    $('input[name="'+ key +'"]').val(value);

                                });
                            } else if (Order.pgType == 'cj') {
                                $.each(response.data.pgData, function(key, value){
                                    $('input[name="'+ key +'"]').val(value);
                                });
                            } else if (Order.pgType == 'kspay') {
                                $.each(response.data.pgData, function(key, value){
                                    $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                });
                            } else if (Order.pgType == 'kcp') {
                                $.each(response.data.pgData, function(key, value){
                                    $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                });
                            } else if (Order.pgType == 'easypay') {
                                $.each(response.data.pgData, function(key, value){
                                    $('input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                });
                            } else if (Order.pgType == 'nicepay') {
                                $.each(response.data.pgData, function(key, value){
                                    $('.mobile-nicepay-input-area > input[name="'+ key +'"]', $('div.pgInputArea')).val(value);
                                });

                                $('#payType-kakaopay-input input[name="GoodsName"]').attr("name", "GoodsNameKakao");
                                $('#payType-kakaopay-input input[name="BuyerName"]').attr("name", "BuyerNameKakao");
                            }

                        } else if (Order.getApprovalType(savePaymentType, 'payco')) {

                            if (response.data.payco == undefined) {
                                isSuccess = false;
                                alert('PAYCO 결제 실패!!');
                            } else {
                                if (response.data.payco.code == "0") {
                                    location.href = response.data.payco.result.orderSheetUrl;
                                } else {
                                    alert("[" + response.data.payco.code + "] " + response.data.payco.message);
                                    isSuccess = false;
                                }
                            }
                        }  else if (Order.getApprovalType(savePaymentType, 'kakaopay')) {
                            var data = response.data;
                            var kakaopay = response.data.kakaopay;
                            var txnRequest = kakaopay.txnRequest;
                            var txnResponse = kakaopay.txnResponse;

                            isSuccess = false;
                            if (kakaopay == undefined || txnRequest == undefined || txnResponse == undefined) {
                                alert('KakaoPay 결제 실패!!');

                            } else if (txnResponse.resultCode != "00") {
                                alert("[" + txnResponse.resultCode + "] " + txnResponse.resultMsg);

                            } else {
                                // 결제 요청값 설정.
                                var $kakaopayRequestData = $('.op-kakaopay-request-data');
                                $.each(response.data.kakaopay.txnRequest, function(key, value) {
                                    $kakaopayRequestData.find('input[name="' + key + '"]').val(value);
                                    $kakaopayRequestData.find('.op-kakaopay-' + key).val(value);
                                    //	console.log(key, value);
                                });

                                $.each(response.data.kakaopay.txnResponse, function(key, value) {
                                    $kakaopayRequestData.find('#' + key).val(value);
                                        //$('input[name="'+ key +'"]').val(value);
                                        console.log(key, value);
                                });

                                // 구매자명 / 이메일
                                $kakaopayRequestData.find('input[name=BuyerName]').val(data.userName);
                                $kakaopayRequestData.find('input[name=BuyerEmail]').val(data.email);



                                isSuccess = true;
                            }

                        } else if (Order.getApprovalType(savePaymentType, 'naverpay')) {

                            if (Order.buy.orderPayAmount < 100) {
                                alert("총 결제금액이 100원 미만일 경우 네이버페이를 이용 할 수 없습니다.");
                                return false;
                            }

                            var oPay = Naver.Pay.create({
                                "mode" : "development",
                                "clientId" : Order.buy.buyPayments['naverpay'].mid,
                                "openType" : "page",
                                // "onAuthorize" : function(oData) {
                                //     if (oData.resultCode === 'Success') {
                                //         var param = {
                                //             "paymentId" : oData.paymentId,
                                //             "orderCode" : response.data.orderCode,
                                //             "amount" : response.data.naverpay.totalPayAmount
                                //         };
                                //     } else {
                                //         if (oData.resultMessage === 'userCancel') {
                                //             alert('결제를 취소하셨습니다.');
                                //         } else if (oData.resultMessage === 'webhookFail') {
                                //             alert('webhookUrl 호출 응답에 실패하였습니다.');
                                //         } else if (oData.resultMessage === 'paymentTimeExpire') {
                                //             alert('결제 시간이 초과되었습니다.');
                                //         } else if (oData.resultMessage === 'OwnerAuthFail') {
                                //             alert('본인 카드 인증 오류가 발생했습니다.');
                                //         }
                                //         Common.loading.hide();
                                //     }
                                // }
                            });

                            oPay.open({
                                "returnUrl" : '${op:property("saleson.url.shoppingmall")}'
                                    + '/m/order/naverpay/return-url?orderCode='
                                    + response.data.orderCode+'&amount='
                                    + response.data.naverpay.totalPayAmount,
                                "merchantPayKey": response.data.orderCode,
                                "productName": response.data.naverpay.productName,
                                "totalPayAmount": response.data.naverpay.totalPayAmount,
                                "taxScopeAmount": response.data.naverpay.taxScopeAmount,
                                "taxExScopeAmount": response.data.naverpay.taxExScopeAmount,
                                "productItems": response.data.naverpay.productItems,
                                "deliveryFee": Order.buy.totalShippingAmount,
                                "productCount": response.data.naverpay.productCount
                            });
                        }
                    }, function(response){
                        alert(response.errorMessage);
                    });
                });

                if (!isSuccess) {
                    return false;
                }

                if (Order.getApprovalType(savePaymentType, 'kakaopay')) {
                    kakaopay();
                    return false;

                } else if (Order.getApprovalType(savePaymentType, 'payco')) {
                    return false;
                } else if (Order.getApprovalType(savePaymentType, 'card') || Order.getApprovalType(savePaymentType, 'vbank') || Order.getApprovalType(savePaymentType, 'realtimebank')
                        || Order.getApprovalType(savePaymentType, 'hp')
                        || (Order.pgType == 'inicis' && Order.getApprovalType(savePaymentType, 'escrow'))) {

                    if (Order.pgType == 'inicis') {
                        iniPayMobile();
                        return false;
                    } else if (Order.pgType == 'lgdacom') {

                        if (launchCrossPlatform()) {

                        }

                    } else if (Order.pgType == 'cj') {

                        var pgUrl = "${op:property('pg.cj.card.pay.url')}";
                        if (Order.getApprovalType(savePaymentType, 'vbank')) {
                            pgUrl = "${op:property('pg.cj.vbank.pay.url')}";
                        } else if (Order.getApprovalType(savePaymentType, 'realtimebank')) {
                            pgUrl = "${op:property('pg.cj.realtimebank.pay.url')}";
                        }

                        cjMobile(pgUrl);
                        return false;

                    } else if (Order.pgType == 'kspay') {
                        if (ksPayStart() == false) {
                            return false;
                        }

                        return false;
                    } else if (Order.pgType == 'kcp') {
                        if (kcpLaunchCrossPlatform() == false) {
                            return false;
                        }

                        return false;
                    } else if (Order.pgType == 'easypay') {
                        if (easyPayLaunch() == false) {
                            return false;
                        }

                        return false;
                    } else if (Order.pgType == 'nicepay') {
                        nicepayStart();

                        $('#payType-kakaopay-input input[name="GoodsNameKakao"]').attr("name", "GoodsName");
                        $('#payType-kakaopay-input input[name="BuyerNameKakao"]').attr("name", "BuyerName");
                        return false;
                    }
                } else if (Order.getApprovalType(savePaymentType, 'naverpay')) {
                    Common.loading.show();
                    return false;
                }
			}catch(e) {alert(e.message);return false;}
		}
	}); 
	
	$('input.op-input-shipping-coupon-used').on('change', function() {
		if (Order.buy.shippingCoupon <= Order.useShippingCoupon) { 
			$(this).prop('checked', false);
		}
		
		Order.setShippingAmount();
	});

	$('input[name=quickDeliveryFlag]').on('change', function() {

		Order.setShippingAmount();
	});
	
	// 포인트 사용 - 직접 입력
	$('input.op-total-point-discount-amount-text').on('focusout', function(){
		var usePoint = $(this).val().replace(",", '');
		Order.pointUsed(usePoint);
	});
	  
	// 받으시는분 복사
	$('select[name="infoCopy"]').on('change', function() {
		var index = $(this).val().split('-')[1];
		var type = $(this).val().split('-')[0];
		if (type == 'copy') {
			$.each($('input, select', $('#op-buyer-input-area')), function() {
				var id = $(this).attr('id').replace('buyer.', '');
				var name = uppercase(id);
				name = 'receive' + name;
				
				if (name == 'receiveUserName') {
					name = 'receiveName';
				}
				
				name = 'receivers['+index+'].' +  name;
				if ($('input[name="' + name + '"]').size() == 1) {
					$('input[name="' + name + '"]').val($(this).val());
				}
			});
		} else if(type == 'clear') {
			$('input, select', '#op-receive-input-area-' + index).not('select[name="infoCopy"], #multiple-delivery-set-count').val(""); 
		} else if (type == 'default') {
			$.each($('input, select', $('#op-default-delivery-input-area')), function() {
				var id = $(this).attr('id').replace('defaultUserDelivery.', '');
				var name = uppercase(id);
				name = 'receive' + name;
				 
				if (name == 'receiveUserName') {
					name = 'receiveName';
				}
				
				name = 'receivers['+index+'].' +  name;
				if ($('input[name="' + name + '"]').size() == 1) {
					$('input[name="' + name + '"]').val($(this).val());
				} else if ($('select[name="' + name + '"]').size() == 1) {
					$('select[name="' + name + '"]').val($(this).val());
				}
			});
		} else {
			var temp = $(this).val().split('-');
			if (temp.length == 4) {
				var index = temp[3];
				var target = temp[0] + '-' + temp[1] + '-' + temp[2]; 
				$.each($('input, select', $('div#' + target)), function() {
					var id = $(this).attr('id').replace('orderShippingInfo.', '');
					var name = uppercase(id);
					name = 'receive' + name;
					
					if (name == 'receiveUserName') {
						name = 'receiveName'; 
					}
					
					name = 'receivers['+index+'].' +  name;
					if ($('input[name="' + name + '"]').size() == 1) {
						$('input[name="' + name + '"]').val($(this).val());
					} else if ($('select[name="' + name + '"]').size() == 1) {
						$('select[name="' + name + '"]').val($(this).val());
					}
				});
			}
		}
		
		// 배송지에 따른 배송비 설정
		var zipcode = $('input[name="receivers['+ index +'].receiveZipcode"]').val();
		Order.changeReceiverZipcode(zipcode, index);
		
		// 배송지에 따른 배송비 설정
		Order.setShippingAmount();
		
		Order.setAmountText();
	});
	
	$('input.op-order-payAmounts').on('keyup', function() {
		
		var thisPaymentType = $(this).attr('paymentType');
		var thisPay = Number($(this).val());
		
		$('input.op-order-payAmounts').not(this).val(0);
		if (thisPay < Order.buy.orderPayAmount) {

			var targetIndex = 0;
			$.each($('input[name="payType"]:checked'), function(i, object) {
				if (thisPaymentType != $(this).val()) {
					
					// 처음꺼에다가 몰아주기
					if (targetIndex == 0) {
						$('input.op-order-payAmounts[paymentType="'+ $(this).val() +'"]').val(Number(Order.buy.orderPayAmount) - thisPay);
					}
					
					targetIndex++; 
				}
			});
			 
			
			if (targetIndex == 0) {
				thisPay = Order.buy.orderPayAmount;
			}
		} else {
			thisPay = Order.buy.orderPayAmount;
		}
		
		$(this).val(thisPay);
		
	});

	// 상품쿠폰 선택
	$('select[name="useCouponKeys"]').on('change', function() {
		var value = $(this).val();
		var couponId = $(this).attr('id');
		
		if (value == '') {
			return;
		}
		
		if (value == 'clear') {
			$.each($(this).find('option'), function(){
				if ($(this).val() != 'clear') {
					findEqItemCouponsDisabled($(this).val(), couponId, false);
				}
			});
		} else {
			$.each($(this).find('option'), function(){
				if ($(this).val() != 'clear') {
					if ($(this).val() != value) {
						findEqItemCouponsDisabled($(this).val(), couponId, false);
					}
				}
			});
			
			findEqItemCouponsDisabled(value, couponId, true);
		}
		
		setUseCoupons();
	});
	
	$('input[name="useCouponKeys"]').on('click', function() {
		setUseCoupons();
	});
	
	if ($('select[name="useCouponKeys"] :selected').size() > 0) {
		$.each($('select[name="useCouponKeys"]'), function(){
			var value = $(this).val();
			var couponId = $(this).attr('id');

			if (value == 'clear') {
				
			} else {
				$.each($(this).find('option'), function(){
					if ($(this).val() != 'clear') {
						if ($(this).val() != value) {
							findEqItemCouponsDisabled($(this).val(), couponId, false);
						}
					}
				});
				
				findEqItemCouponsDisabled(value, couponId, true);
			}
			
			setUseCoupons();
		});
	}
});

// 포인트 사용 - 전체 사용
function retentionPointUseAll(){
	
	Order.pointUsedAllForMobile();

}

function payTypeSelect(payType) {
	
	$_this = $('input#op-payType-' + payType);

	if ($_this.prop('checked') == true) {
		if ($('input[name="payType"]:checked').size() == 1) {
			return;
		}
		$_this.prop('checked', false);
	} else {
		$_this.prop('checked', true);
	}

	if ($('input[name="payType"]:checked').size() > 1) { // 프론트랑 마찬가지로 복합결제는 안되게. 나중에 사용시 이 부분 지우면 됨. 2016.01.20 kye
		$('input[name="payType"]:checked').not($_this).prop('checked', false);
	}

	var notMixPayTypeSelectCount = 0;
	var isOfflinePayError = false;
	$.each(Order.notMixPayType, function(i, payType) {
		$.each($('input[name="payType"]:checked'), function(j, t) {
			if (payType == $(this).val()) {
				notMixPayTypeSelectCount++;
			} else if ($(this).val() == 'offlinepay' && $('input[name="payType"]:checked').size() > 2) {
				// LG 전화승인/단말기승인의 경우 2개 이상의 복합 결제를 할수 없도록 막자..
				notMixPayTypeSelectCount++;
				isOfflinePayError = true;
			}
		});
	});

    if (payType == 'naverpay') {
        document.getElementById('payment-button').style = 'display:none';
        document.getElementById('naver-payment-button').style = 'display:inline-block';
    } else {
        document.getElementById('naver-payment-button').style = 'display:none';
        document.getElementById('payment-button').style = 'display:inline-block';
    }
	
	// 복합결제가 불가능한 결제타입을 1개이상 선택하였으면
	if (notMixPayTypeSelectCount > 1) {
		$('input[name="payType"]:checked').not($_this).prop('checked', false);
		if (isOfflinePayError) {
			offlinePaymentClear();
		}
	}
	
	$('li.op-pay-type-select').removeClass('on');
	$.each($('input[name="payType"]:checked'), function() {
		if ($(this).prop('checked') == true) {
			var pt = $(this).val();
			$('li.op-pay-type-'+ pt +'-select').addClass('on');
		}
	});
	
	$('.op-payType-input').hide();
	$('input.op-order-payAmounts').removeClass('op-default-payment');
	
	$.each($('input[name="payType"]:checked'), function(i) {
		if (i == 0) {
			$('input.op-order-payAmounts', $('#op-payType-' + $(this).val() + '-input')).addClass('op-default-payment');
		}

        $('#op-payType-' + $(this).val() + '-input').show();
		$('.pay-type-' + $(this).val() + '-sub-input').show();
	});

	if (payType == 'naverpayCard') {
		$(".card_title").text("네이버페이 결제");
		$(".op-pay-type-naverpay-card-select").addClass("on");
		$(".op-pay-type-card-select").removeClass("on");

	} else if (payType == 'card') {
		$(".card_title").text("신용카드 결제");
		$(".op-pay-type-naverpay-card-select").removeClass("on");
		$(".op-pay-type-card-select").addClass("on");
	}
	
	Order.setOrderPayAmountClear();

	if ('${pgService}' == 'kcp') {
		if(payType == 'realtimebank') {
			payType = 'acnt';
		} else if(payType == 'vbank') {
			payType = 'vcnt';
		}
		document.buy.ActionResult.value = payType;
	}

	<c:if test="${pgService != 'nicepay'}">
	// 2017.09.20 손준의 - 현금영수증 신청 정보 입력란 보이기/숨기기
	// 결제수단 선택시 함수 사용
	cashReceiptSet(payType);
	</c:if>
}

function paycoPaySuccess(url) {
	location.replace(url);
}

function paycoErrorMessage(errorCode, message, url) {
	Common.loading.hide();
	if (message == '') {
		message = '['+errorCode+'] 페이코 결제에 실패 하였습니다.';
	}
	
	alert(message);
	if (url != undefined) {
		location.replace(url);
	}
}

// 할인 적용내용 초기화!!
function historyBackDataSet() {
	Order.setAmountText();
}

//쿠폰 적용
function viewCoupon() {
	couponPopup = Common.popup("about:blank", 'couponView', 900, 700, 1);
	
	var formName = "receiverForm";
	$('form#' + formName).remove();
	var $form = $('<form name="'+formName+'" method="post" action="/m/order/coupon" target="couponView" />');
	
	$.each($('.op-receive-input-area', '#buy').find('input'), function(){
		$form.append($('<input />').attr({
			'type'	: 'hidden',
			'value'	: $(this).val(),
			'name'	: $(this).attr('name')
		}))
	});

	$form.append(Common.getCsrfNode());
	$('body').append($form);
	$form.submit();
	
	//$('#buy').attr('action', "/m/order/coupon");
	//$('#buy').attr('target', 'couponView');
	//$('#buy').submit();
	
	Common.loading.hide();
}

function setUseCoupons() {
	var itemCoupons = [];
	var cartCoupons = [];
	
	var totalItemCouponDiscountAmount = 0;
	$('.op-item-coupon-discount-text').html('0');
	$.each($('select[name="useCouponKeys"] :selected'), function(){
		var key = $(this).val();
		var temps = key.split('-');

		if (temps.length == 4) {
			var discountAmount = parseInt($('input[name="discount-amount-' + key + '-value"]').val());
			var discountPrice = parseInt($('input[name="discount-price-' + key + '-value"]').val());
			var couponConcurrently = parseInt($('input[name="coupon-concurrently-' + key + '-value"]').val());
			var coupon = {
				'key'					: key,
				'itemSequence'			: temps[3],
				'couponUserId'			: temps[2],
				'discountPrice' 		: discountPrice,
				'discountAmount' 		: discountAmount,
				'couponConcurrently' 	: couponConcurrently
			};
			
			var target = $('#op-discount-coupon-' + temps[3] + '-text');
			target.html(Common.numberFormat(discountAmount));
			totalItemCouponDiscountAmount += discountAmount; 
			itemCoupons.push(coupon);
		}
	});

	
	var cartCouponDiscountTargetAmount = Order.totalItemPriceByCoupon - totalItemCouponDiscountAmount;
	$.each($('input[name="useCouponKeys"]'), function(){
		var key = $(this).val();
		
		var discountType = $('input[name="coupon-pay-type-' + key + '"]').val();
		if (discountType == '2') {
			var couponPay = $('input[name="coupon-pay-' + key + '"]').val();
			try {
				var discountPay = cartCouponDiscountTargetAmount * (couponPay / 100);
				$('input[name="discount-amount-' + key + '-value"]').val(discountPay);
				$('#op-discount-amount-' + key + '-text').html(Common.numberFormat(discountPay));
			} catch(e) { 
				
				$(this).prop('disabled', true);
			}
		} 
		
	});
	
	$.each($('input[name="useCouponKeys"]:checked'), function(){
		if ($(this).prop('disabled') == false) {
			var key = $(this).val();
			var temps = key.split('-');
			if (temps.length == 3) {
				var discountAmount = parseInt($('input[name="discount-amount-' + key + '-value"]').val());
				var coupon = {
					'key'			: key,
					'couponUserId'	: temps[3],
					'discountAmount': discountAmount
				};
				
				cartCoupons.push(coupon);
			}
		}
	});
	
	Order.setCouponDiscountAmount(itemCoupons, cartCoupons);
}

function findEqItemCouponsDisabled(couponKey, thisId, disabled) {
	var temp = couponKey.split('-');
	if (temp.length == 4) {
		var key = temp[0] + "-" + temp[1] + "-" + temp[2];
		$.each($('select[name="useCouponKeys"]').not('#' + thisId), function(){
			$selectBox = $(this);
			$.each($selectBox.find('option'), function() {
				$option = $(this);
				if ($option.val() != 'clear') {
					if ($option.val().indexOf(key) == 0) {
						var textDecoration = 'none';
						if (disabled == true) {
							if ($selectBox.find(':selected').val() == $option.val()) {
								$selectBox.val('clear');
							}
							
							textDecoration = 'line-through';
						}
						
						// 이거 안먹음...ㅠ
						$option.css('text-decoration', textDecoration);
						$option.prop('disabled', disabled);
					}
				}
			});
		});
	}
}

function uppercase(text) {
	if (text == '' || text == undefined) return text;
	return text.substring(0, 1).toUpperCase() + text.substring(1);
}

//다음 우편번호 검색
function openDaumPostcode(mode, index) {
	var newZipcode		= "buyer.newZipcode";
	var zipcode 		= "buyer.zipcode";
	var zipcode1 		= "buyer.zipcode1";
	var zipcode2 		= "buyer.zipcode2";
	var address 		= "buyer.address";
	var addressDetail 	= "buyer.addressDetail";
	var sido			= "buyer.sido";
	var sigungu			= "buyer.sigungu";
	var eupmyeondong	= "buyer.eupmyeondong";
	
	
	if (mode == "receive") {
		newZipcode 		= "receivers["+index+"].receiveNewZipcode";
		zipcode 		= "receivers["+index+"].receiveZipcode";
		zipcode1 		= "receivers["+index+"].receiveZipcode1";
		zipcode2 		= "receivers["+index+"].receiveZipcode2";
		address 		= "receivers["+index+"].receiveAddress";
		addressDetail 	= "receivers["+index+"].receiveAddressDetail";
		sido			= "receivers["+index+"].receiveSido";
		sigungu			= "receivers["+index+"].receiveSigungu";
		eupmyeondong	= "receivers["+index+"].receiveEupmyeondong";
	}
	
	var tagNames = {
		'newZipcode'			: newZipcode,
		'zipcode' 				: zipcode,
		'zipcode1' 				: zipcode1,
		'zipcode2' 				: zipcode2,
		'sido'					: sido,
		'sigungu'				: sigungu,
		'eupmyeondong'			: eupmyeondong,
		'roadAddress'			: address,
		'jibunAddressDetail' 	: addressDetail
	}
	
	openDaumAddress(tagNames, function(data){

		var post = data.postcode;
		if (post == '') {
			post = data.zonecode;
		}

		if (mode == "receive") {
			Order.changeReceiverZipcode(post, index);
		}

		// 배송비 재설정
		Order.setShippingAmount();

		Order.setAmountText();
		
	});
	
}

 
function isZenkaku(str) {
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);

		if (c >= 65296 && c <= 65305) {
			return false;
		}
		
		// Shift_JIS: 0x0 ～ 0x80, 0xa0 , 0xa1 ～ 0xdf , 0xfd ～ 0xff
		// Unicode : 0x0 ～ 0x80, 0xf8f0, 0xff61 ～ 0xff9f, 0xf8f1 ～ 0xf8f3
		if ((c >= 0x0 && c < 0x81) || (c == 0xf8f0) || (c >= 0xff61 && c < 0xffa0) || (c >= 0xf8f1 && c < 0xf8f4)) {
			return false;
		}
	}
	
	return true;
}

//쿠폰 적용
function viewDelivery() {
	couponPopup = Common.popup("about:blank", 'deliveryView', 900, 700, 1);
	
	var formName = "receiverForm";
	$('form#' + formName).remove();
	$form = $('<form name="'+formName+'" method="post" action="/m/order/delivery" target="deliveryView" />');
	
	$.each($('.op-receive-input-area', '#buy').find('input'), function(){
		$form.append($('<input />').attr({
			'type'	: 'hidden',
			'value'	: $(this).val(),
			'name'	: $(this).attr('name')
		}))
	});
	
	$('body').append($form);
	$form.submit();
	
	//$('#buy').attr('action', "/m/order/coupon");
	//$('#buy').attr('target', 'couponView');
	//$('#buy').submit();
	
	Common.loading.hide();
}

function cashReceiptSet(payType){
    // 2017.09.20 손준의 - 현금영수증 신청 정보 입력란 보이기/숨기기

    document.getElementById('cashbill_view').style = 'display:none';

    document.getElementById('op-receipt-personal').style = 'display:none';
    document.getElementById('op-receipt-evidence').style = 'display:none';
    document.getElementById('cashbill_1').checked = true


    document.getElementById('cashbill.cashbillPhone1').value = '';
    document.getElementById('cashbill.cashbillPhone2').value = '';
    document.getElementById('cashbill.cashbillPhone3').value = '';

    document.getElementById('cashbill.businessNumber1').value = '';
    document.getElementById('cashbill.businessNumber2').value = '';
    document.getElementById('cashbill.businessNumber3').value = '';

    $('input#bankInName').removeClass('required')
    $('input.businessNumber').removeClass('required');
    $('input.cashbillPhone').removeClass('required');


    if("${pgService}" == "kcp"){
        if(payType == 'vcnt'){
            payType = 'vbank';
        } else if(payType == 'acnt'){
            payType = 'realtimebank';
        }
    }

    if(payType == 'bank'){
        $('div#cashbill_view').css('display','');
        $('input#bankInName').addClass('required');
    } else if ('${autoCashReceipt}' == 'N' && (payType == 'vbank' || payType == 'realtimebank')) {
        $('div#cashbill_view').css('display','');
    } else {
        $('div#cashbill_view').css('display','none');
    }
}
</script>
</page:javascript>