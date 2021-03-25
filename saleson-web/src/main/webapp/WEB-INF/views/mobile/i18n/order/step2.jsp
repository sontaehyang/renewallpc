<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>


<form:form modelAttribute="order" action="/m/order/pay" method="post">
	<input type="hidden" name="approvalType" value="${ order.approvalType }" />
	<input type="hidden" name="orderPayAmount" value="${ order.orderPayAmount }" />

	<div class="order">
		<h3 class="title">ご注文の最終確認</h3> <!-- 주문/결제 -->
		<div class="order_write">
			<p class="info_title">1.注文内容／金額</p>
			
			<c:set var="isSingleView" value="0" />
			<c:forEach items="${ order.orderVendors }" var="vendor" varStatus="vendorIndex">
				<c:forEach items="${ vendor.orderDeliverys }" var="delivery" varStatus="deliveryIndex">
					
					<c:if test="${ delivery.deliveryType eq '2' }">
						<c:set var="isSingleView" value="1" />
					</c:if>
				</c:forEach>
			</c:forEach>
			<c:if test="${ isSingleView eq '1' }">
				<div class="cart_shipping">
					<h5>"${op:message('M00638')}" ${op:message('M00903')}</h5> <!-- 개별배송 대상상품 -->
					
					<c:forEach items="${ order.orderVendors }" var="vendor" varStatus="vendorIndex">
 						<c:forEach items="${ vendor.orderDeliverys }" var="delivery" varStatus="deliveryIndex">
 							<c:if test="${ delivery.deliveryType eq '2' }">
 								<c:forEach items="${ delivery.orderItems }" var="orderItem" varStatus="itemIndex">
								
									<div class="cart_info order_a">
										<table cellpadding="0" cellspacing="0" summary="">
											<colgroup>
												<col width="2%">
												<col width="98%">
											</colgroup> 
											<tbody>
												<tr> 
													<td colspan="2">
														<div class="cont01">
															<span class="photo"><img src="${orderItem.item.imageSrc}" style="width:67px" alt="${ orderItem.item.itemName }" /></span>
															<div>
																<p class="naming">${ orderItem.item.itemName }</p>
																<p class="price">${op:message('M00356')} (${ shopContext.config.taxDisplayTypeText }) <!-- 단가 -->：<span>${ op:numberFormat(orderItem.itemPriceData.sumItemPrice) }</span>円</p>
																<p class="price02"> 数量： <span>${ orderItem.quantity }</span>${op:message('M01004')}</p>
															</div>
														</div>
														
														<c:if test="${ !empty orderItem.requiredOptionsList }"> 
															<div class="cont02">
																<ul>
																	<c:forEach items="${ orderItem.requiredOptionsList }" var="option">
																		<li>
																			<span>${ option.optionName1 }</span>
																			– ${option.optionName2}
																			<c:if test="${!empty option.extraPrice && option.extraPrice != 0}">
																				<span>(<c:if test="${ option.extraPrice > 0}">+</c:if>${op:numberFormat(option.extraPrice)}円)</span>
																			</c:if>
																			<c:if test="${!empty option.optionCode}">
																				<span>商品番号 : ${ option.optionCode }</span>
																			</c:if>
																		</li>
																	</c:forEach>
																</ul>
															</div>
														</c:if>
														
													</td>
												</tr>
											</tbody>
										</table>
										<div class="order_total">
											<ul>
												<c:if test="${ useCoupon == true }">
													<li>割引額 
														<span class="total02">
															<c:if test="${ orderItem.itemPriceData.itemCouponDiscountAmount > 0 }">-</c:if>
															${ op:numberFormat(orderItem.itemPriceData.itemCouponDiscountAmount) } 円
														</span>
													</li>
												</c:if>
												
												<li>${op:message('M00817')}(${ shopContext.config.taxDisplayTypeText }) <!-- 상품 합계 --> <span class="total">${ op:numberFormat(orderItem.itemPriceData.itemPayAmount) } 円</span></li>
												<li>${op:message('M00359')} : <!-- 배송료 -->
													<c:choose>
						 								<c:when test="${ delivery.deliveryPrice == 0 }">${op:message('M00448')} <!-- 무료 --></c:when>
						 								<c:otherwise>${ op:numberFormat(delivery.deliveryPrice) } ${unit}</c:otherwise>
						 							</c:choose>
												</li>
											</ul>
										</div><!--//order_total E--> 
									</div><!--//cart_info E-->					 					
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:forEach>
				</div><!--//cart_shipping E-->
			</c:if>
			
			<c:forEach items="${ order.orderVendors }" var="vendor" varStatus="vendorIndex">
				<c:forEach items="${ vendor.orderDeliverys }" var="delivery" varStatus="deliveryIndex">
					<c:if test="${ delivery.deliveryType eq '1' }">
					
						<div class="cart_shipping">
							<h5>"${ delivery.title }" ${op:message('M00001')}</h5>
							<c:forEach items="${ delivery.orderItems }" var="orderItem" varStatus="itemIndex">
							
								<div class="cart_info order_a">
									<table cellpadding="0" cellspacing="0" summary="">
										<colgroup>
											<col width="2%">
											<col width="98%">
										</colgroup> 
										<tbody>
											<tr> 
												<td colspan="2">
													<div class="cont01">
														<span class="photo"><img src="${orderItem.item.imageSrc}" style="width:67px" alt="${ orderItem.item.itemName }" /></span>
														<div>
															<p class="naming">${ orderItem.item.itemName }</p>
															<p class="price">${op:message('M00356')} (${ shopContext.config.taxDisplayTypeText }) <!-- 단가 -->：<span>${ op:numberFormat(orderItem.itemPriceData.sumItemPrice) }</span>円</p>
															<p class="price02"> 数量： <span>${ orderItem.quantity }</span>${op:message('M01004')}</p>
														</div>
													</div>
													
													<c:if test="${ !empty orderItem.requiredOptionsList }">
														<div class="cont02">
															<ul>
																<c:forEach items="${ orderItem.requiredOptionsList }" var="option">
																	<li>
																		<span>${ option.optionName1 }</span>
																		– ${option.optionName2}
																		<c:if test="${!empty option.extraPrice && option.extraPrice != 0}">
																			<span>(<c:if test="${ option.extraPrice > 0}">+</c:if>${op:numberFormat(option.extraPrice)}円)</span>
																		</c:if>
																		<c:if test="${!empty option.optionCode}">
																			<span>商品番号 : ${ option.optionCode }</span>
																		</c:if>
																	</li>
																</c:forEach>
															</ul>
														</div>
													</c:if>
													
												</td>
											</tr>
										</tbody>
									</table>
									<div class="order_total">
										<ul>
											<c:if test="${ useCoupon == true }">
												<li>割引額 
													<span class="total02">
														<c:if test="${ orderItem.itemPriceData.itemCouponDiscountAmount > 0 }">-</c:if>
														${ op:numberFormat(orderItem.itemPriceData.itemCouponDiscountAmount) } 円
													</span>
												</li>
											</c:if>
											
											<li>${op:message('M00817')}(${ shopContext.config.taxDisplayTypeText }) <!-- 상품 합계 --> <span class="total">${ op:numberFormat(orderItem.itemPriceData.itemPayAmount) } 円</span></li>
										</ul> 
									</div><!--//order_total E--> 
								</div><!--//cart_info E-->
							</c:forEach>
							
							<div class="order_all_total">
								<ul>
									<li class="first">
										<strong>${op:message('M01463')} <!-- 묶음 배송비 --> : 
											<span>
												<c:choose>
					 								<c:when test="${ delivery.deliveryPrice == 0 }">${op:message('M00448')} <!-- 무료 --></c:when>
					 								<c:otherwise>${ op:numberFormat(delivery.deliveryPrice) } ${unit}</c:otherwise>
					 							</c:choose>
											</span>
										</strong> 
									</li> 
								</ul>
							</div><!--//order_all_total E-->		
									
						</div><!--//cart_shipping E--> 
					</c:if>
				</c:forEach>
			</c:forEach>
			 
			<c:if test="${ isLogin == true }">
				<c:if test="${ order.orderPrice.totalEarnPoints > 0 }">
					<div class="order_all_total type01">
						<ul>
							<li class="first"><strong>総支給ポイント : <span>${ op:numberFormat(order.orderPrice.totalEarnPoints) }P</span></strong> </li> 
						</ul>
					</div><!--//order_all_total E-->
				</c:if>
		 	</c:if>

			<div class="last_price">
				<table cellpadding="0" cellspacing="0" summary="">
					<colgroup>
						<col width="50%">
						<col width="50%">
					</colgroup>
					<thead>
						<tr>
							<td>合計商品金額 (${ shopContext.config.taxDisplayTypeText })</td>
							<td><strong><span>${ op:numberFormat(order.orderPrice.totalItemPrice) }</span></strong> 円</td>
						<tr>
					</thead>
					<tfoot>
						<tr>
							<td>総決済金額（税込）:</td>
							<td><strong><span>${ op:numberFormat(order.orderPrice.orderPayAmount) }</span></strong> 円</td>
						<tr>
					</tfoot>
					<tbody>
						<c:if test="${ useCoupon == true }">
							<tr>
								<td><span class="icon"><img src="/content/mobile/images/common/icon_td_minus.png" alt=""></span> ${op:message('M00818')}</td> <!-- 상품쿠폰 -->
								<td><span>${ op:numberFormat(order.orderPrice.totalItemCouponDiscountAmount) }</span> 円</td>
							</tr>
						</c:if>
						
						<c:if test="${ shopContext.config.taxDisplayType eq '2'}">
							<tr>
								<td><span class="icon"><img src="/content/mobile/images/common/icon_plus.png" alt=""></span> 消費税</td> <!-- 소비세 -->
								<td><span>${ op:numberFormat(order.orderPrice.totalExcisePrice) }</span> 円</td>
							</tr>
						</c:if>
						<tr>
							<td><span class="icon"><img src="/content/mobile/images/common/icon_plus.png" alt=""></span> ${op:message('M00924')}</td> <!-- 배송료(세금포함) -->
							<td><span>${ op:numberFormat(order.orderPrice.totalDeliveryCharge) }</span> 円</td>
						</tr>
						
						<c:if test="${ useCoupon == true }">
							<tr>
								<td><span class="icon"><img src="/content/mobile/images/common/icon_td_minus.png" alt=""></span> ${op:message('M00820')}</td> <!-- 장바구니쿠폰 -->
								<td><span>${ op:numberFormat(order.orderPrice.totalCartCouponDiscountAmount) }</span> 円</td>
							</tr>
						</c:if>
						
						<tr>
							<td class="last"><span class="icon"><img src="/content/mobile/images/common/icon_td_minus.png" alt=""></span> ${op:message('M00246')}</td> <!-- 포인트 -->
							<td class="last"><span>${ op:numberFormat(order.orderPrice.totalPointDiscountAmount) }</span> P</td>
						</tr> 
					</tbody>
				</table>		
			</div><!--//last_price E-->			
		</div><!--//order_write E-->
		
		<div class="order_write">
			<p class="info_title">2. ${op:message('M01465')}</p> <!-- 주문/결제 정보 -->
			<div class="table_wrap_a"> 
				<table class="type02">
					<colgroup>
						<col style="width:45%;">
						<col style="width:55%;">
					</colgroup> 
					<thead>
						<tr>
							<td colspan="2">${op:message('M00428')}</td> <!-- 주문하시는분 -->
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>${op:message('M00105')}</th> <!-- 회사명 -->
							<td>${ order.companyName }</td> 
						</tr>
						<tr>
							<th>注文者名 <span>(漢字)</span></th> <!-- 주문자 이름 (한자) -->
							<td>${ order.userName }</td> 
						</tr> 
						<tr>
							<th>注文者名<span>（カナ）</span></th> <!-- 주문자 이름(가나) -->
							<td>${ order.userNameKatakana }</td> 
						</tr> 
						<tr>
							<th>${op:message('M00125')}</th> <!-- 이메일 -->
							<td>${ order.email }</td> 
						</tr> 
						<tr>
							<th>${op:message('M00016')}</th> <!-- 전화번호 -->
							<td>${ order.phone }</td>  
						</tr> 
						<tr>
							<th>${op:message('M00329')}</th> <!-- 휴대전화번호 -->
							<td>${ order.mobile }</td> 
						</tr> 
						<tr>
							<th class="top">${op:message('M00118')}</th> <!-- 주소 -->
							<td>
								<ul>
									<li>${ order.zipcode }</li>
									<li>${ order.dodobuhyun }</li>
									<li>${ order.address }</li>
									<li>${ order.addressDetail }</li>
								</ul>
							</td> 
						</tr> 
					</tbody>
				</table>	<!--//type02 E-->
			</div><!--//table_wrap_a E-->
			<div  class="table_wrap_a">	
				<table class="type02">
					<colgroup>
						<col style="width:45%;">
						<col style="width:55%;">
					</colgroup> 
					<thead>
						<tr>
							<td colspan="2">${op:message('M00432')}</td> <!-- 상품 받으시는 분 -->
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>${op:message('M00943')}</th> <!-- 배송지명 -->
							<td>${ order.receiveCompanyName }</td> 
						</tr>
						<tr>
							<th>${op:message('M00944')}</th> <!-- 수취인 이름(한자) --> 
							<td>${ order.receiveName }</td> 
						</tr> 
						<tr>
							<th>${op:message('M00945')} </th> <!-- 수취인 이름(카나) --> 
							<td>${ order.receiveNameKatakana }</td> 
						</tr> 
						<tr>
							<th>${op:message('M00016')}</th> <!-- 전화번호 -->
							<td>${ order.receiveMobile }</td> 
						</tr> 
						<tr>
							<th>${op:message('M00329')}</th> <!-- 휴대전화번호 -->
							<td>${ order.receivePhone }</td> 
						</tr>   
						<tr>
							<th class="top">${op:message('M00118')}</th> <!-- 주소 -->
							<td>
								<ul>
									<li>${ order.receiveZipcode }</li>
									<li>${ order.receiveDodobuhyun }</li>
									<li>${ order.receiveAddress }</li>
									<li>${ order.receiveAddressDetail }</li>
								</ul>
							</td> 
						</tr> 
					</tbody>
				</table>	<!--//type02 E-->
			</div>	<!--//table_wrap_a E-->		
			
			
			<div class="table_wrap_a">	
				<table class="type02">
					<colgroup>
						<col style="width:45%;">
						<col style="width:55%;">
					</colgroup> 
					<thead>
						<tr>
							<td colspan="2">${op:message('M00946')}</td> <!-- 배송날짜정보 -->
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>${op:message('M00319')}</th> <!-- 배송희망일 -->
							<td>
								<c:choose>
		 							<c:when test="${ empty order.deliveryReqDay }">
		 								指定なし <!-- 지정없음 -->
		 							</c:when>
		 							<c:otherwise>
		 								${order.deliveryReqDay }
		 							</c:otherwise>
		 						</c:choose>
							</td> 
						</tr>
						<tr>
							<th>${op:message('M00320')}</th> <!-- 배송희망시간 -->
							<td>
								<c:choose>
		 							<c:when test="${ empty order.deliveryReqHour }">
		 								指定なし <!-- 지정없음 -->
		 							</c:when>
		 							<c:otherwise>
		 								${ order.deliveryReqHour }
		 							</c:otherwise>
		 						</c:choose>
							</td> 
						</tr>
					</tbody>
				</table>	<!--//type02 E-->
			</div>	<!--//table_wrap_a E-->	
			
			<c:if test="${ order.approvalType ne 'noPay' }">
				<div  class="table_wrap_a">	
					<table class="type02">
						<colgroup>
							<col style="width:45%;">
							<col style="width:55%;">
						</colgroup> 
						<thead>
							<tr>
								<td colspan="2">お支払い</td>
							</tr>
						</thead>
						<tbody>
							<c:choose>
		 						<c:when test="${ order.approvalType eq 'card' }">
		 							<tr>
					 					<td>支払方法</td>
					 					<td>クレジットカード</td>
					 				</tr>
					 				<tr>
					 					<td>${op:message('M00626')} <!-- 카드번호 --></td>
					 					<td>
					 						${ pgData.cardNumber }
					 						<input type="hidden" name="cardNumber" value="${ pgData.cardNumber }" />
					 					</td>
					 				</tr>
					 				<tr>
					 					<td>有効期限</td>
					 					<td>
					 						${ pgData.expMonth }月/${ pgData.expYear }年
					 						<input type="hidden" name="expMonth" value="${ pgData.expMonth }" />
					 						<input type="hidden" name="expYear" value="${ pgData.expYear }" />
					 					</td>
					 				</tr>
					 				<tr>
					 					<td>名義人</td>
					 					<td>
					 						${ pgData.cardName }
					 						<input type="hidden" name="cardName" value="${ pgData.cardName }" />
					 					</td>
					 				</tr>
					 				<tr>
					 					<td>お支払い回数</td>
					 					<td>
					 						<c:choose>
					 							<c:when test="${ pgData.installment eq '3' }">分割払い(3回)</c:when>
					 							<c:when test="${ pgData.installment eq '5' }">分割払い(5回)</c:when>
					 							<c:otherwise>一括払い</c:otherwise>
					 						</c:choose>
					 						<input type="hidden" name="installment" value="${ pgData.installment }" />
					 					</td>
					 				</tr>
		 						</c:when>
		 						<c:when test="${ order.approvalType eq 'dlv' }">
		 							<tr>
					 					<td>支払方法</td>
					 					<td>現金代引き</td>
					 				</tr>
		 						</c:when>
		 						<c:when test="${ order.approvalType eq 'conv' }">
		 							<tr>
					 					<td>支払方法</td>
					 					<td><div>コンビニ払い</div></td>
					 				</tr> 
					 				<tr>
					 					<td>利用するコンビニ</td>
					 					<td>
					 						${ pgData.cvsName }
					 						<input type="hidden" name="cvsType" value="${ pgData.cvsType }" />
					 					</td>
					 				</tr> 
					 				<tr>
					 					<td>お名前(姓)</td>
					 					<td>
					 						${ pgData.reqName1 }
					 						<input type="hidden" name="reqName1" value="${ pgData.reqName1 }" />
					 					</td>
					 				</tr> 
					 				<tr>
					 					<td>お名前(名)</td>
					 					<td>
					 						${ pgData.reqName2 }
					 						<input type="hidden" name="reqName2" value="${ pgData.reqName2 }" />
					 					</td>
					 				</tr> 
					 				<tr>
					 					<td>お名前(カナ)</td>
					 					<td>
					 						${ pgData.reqKana }
					 						<input type="hidden" name="reqKana" value="${ pgData.reqKana }" />
					 					</td>
					 				</tr>
					 				<tr>
					 					<td>電話番号</td>
					 					<td>	
					 						${ pgData.reqTelNumber }
					 						<input type="hidden" name="reqTelNumber" value="${ pgData.reqTelNumber }" />
					 					</td>
					 				</tr>
		 						</c:when>
		 						<c:when test="${ order.approvalType eq 'bank' }">
		 							<tr>
					 					<td>支払方法</td>
					 					<td>銀行振込</td>
					 				</tr>
			 						<tr>
					 					<td>${op:message('M00541')} <!-- 입금은행 --></td>
					 					<td>${ order.bankVirtualNo }</td>
					 				</tr>
					 				<tr>
					 					<td>${op:message('M00542')} <!-- 입금명의 --></td>
					 					<td>${ order.bankInName }</td>
					 				</tr>
					 				<tr>
					 					<td>${op:message('M00543')} <!-- 입금예정일 --></td>
					 					<td>${ order.bankDate }</td>
					 				</tr>
		 						</c:when>
		 					</c:choose>
		 				
							
						</tbody>
					</table>	<!--//type02 E-->
				</div>	<!--//table_wrap_a E-->		
			</c:if>							
		</div><!--//order_write E-->
	
		<div class="btn_area wrap_btn mt15">
			<div class="sale division-2" style="display:block">
				<div>
					<div>
						<a href="/m/order/step1" class="btn btn_default btn-w100"> ${op:message('M01466')} </a> <!-- 이전페이지로 -->
					</div>
				</div>
				<div>
					<div>
						<button type="submit" class="btn btn_on btn-w100">${op:message('M01467')}</button> <!-- 주문하기 -->
					</div>
				</div>
			</div>				 
		</div><!--//btn_area view_btn E-->
		
	</div>	<!--//order E-->
	
</form:form>