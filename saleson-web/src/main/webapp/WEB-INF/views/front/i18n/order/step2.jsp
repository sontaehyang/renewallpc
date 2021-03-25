<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>


<div id="sub_contents">
	<form:form modelAttribute="order" action="/order/pay" method="post">
		<input type="hidden" name="approvalType" value="${ order.approvalType }" />
		<input type="hidden" name="orderPayAmount" value="${ order.orderPayAmount }" />
		<div class="order_step">
			<ul>
				<li><img src="/content/images/order/step03_1off.gif" alt="step1 カートイン" /></li>
				<li><img src="/content/images/order/step03_2off.gif" alt="step2 ご注文/お支払い" /></li>
				<li><img src="/content/images/order/step03_3on.gif" alt="step3 ご注文の最終確認" /></li>
				<li><img src="/content/images/order/step03_4off.gif" alt="step4 注文受付完了" /></li>
			</ul>
		</div> <!--// order_step E -->
		
		
		<!-- div class="order_txt2 space">
			<div class="order_num">【${op:message('M00937')}]</div>
			<div class="order_tex">
				동일본 대지진에 의해 피해를 받으 셨습니다 여러분에게 진심으로 병문안을 말씀드립니다. <br/>
				이번 지진의 영향에 의해, 상품의 신고에 영향이 나오고 있습니다. <br/>
				· 신고를 할 수없는 지역<br/>
				【후쿠시마 현】 미나미 소마시 (소고 지역 만) 후타바 군 (나미에 마치 떡잎 마을, 큰곰 도시, 토미 오카 쵸, 나라 하마치)<br/><br/>
				※ 상기 지역의 "영업점 방지 서비스」로의 주문도 받고 있지 않으므로 양해 바랍니다.<br/>
				※ 또한 상기 이외의 지역에서도 신고가 통상보다 시간을받는 경우가 있습니다. 여러분에게는 불편을 끼칩니다 만, 제발 이해 하시도록 부탁드립니다.
			</div>
		</div-->	<!-- // order_txt2 -->
	 
	 
	 	
		<c:set var="isSingleView" value="0" />
		<c:forEach items="${ order.orderVendors }" var="vendor" varStatus="vendorIndex">
			<c:forEach items="${ vendor.orderDeliverys }" var="delivery" varStatus="deliveryIndex">
				
				<c:if test="${ delivery.deliveryType eq '2' }">
					<c:set var="isSingleView" value="1" />
				</c:if>
			</c:forEach>
		</c:forEach>
		<c:if test="${ isSingleView eq '1' }">
			<div class="table_list_type01 pt10">  
				<p class="unit"><span>“${op:message('M00638')}” ${op:message('M00903')}</span><!-- 개별배송 --><!-- 대상상품 -->  ${op:message('M00427')}<!-- 단위 --> : ${unit}</p>
			
		 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:auto;">	 				
		 				<col style="width:100px;">
		 				<col style="width:60px;">
		 				
		 				<c:if test="${ useCoupon == true }">
		 					<col style="width:100px;">
		 				</c:if>
		 				
		 				<col style="width:100px;">
		 				<col style="width:100px;">
		 			</colgroup>
		 			<thead>
		 				<tr>
		 					<th scope="col" class="none_left">${op:message('M00018')} <!-- 상품명 --></th>
		 					<th scope="col">${op:message('M00356')}<br />(${ shopContext.config.taxDisplayTypeText })</th> <!-- 단가 -->
		 					<th scope="col">${op:message('M00357')} <!-- 수량 --></th>
		 					
		 					<c:if test="${ useCoupon == true }">
		 						<th scope="col">${op:message('M00628')} <!-- 할인금액 --> </th>
		 					</c:if>
		 					
		 					<th scope="col">${op:message('M00817')}<br />(${ shopContext.config.taxDisplayTypeText })</th> <!-- 상품합계 -->
		 					<th scope="col">${op:message('M00359')}</th> <!-- 배송료 -->
		 				</tr>
		 			</thead>
		 			<tbody> 
						<c:forEach items="${ order.orderVendors }" var="vendor" varStatus="vendorIndex">
	 						<c:forEach items="${ vendor.orderDeliverys }" var="delivery" varStatus="deliveryIndex">
	 							
	 							<c:if test="${ delivery.deliveryType eq '2' }">
	 								<c:set var="viewCount" value="${ viewCount + 1 }" />
				 					<c:set var="rowspan" value="${ fn:length(delivery.orderItems) }" />
				 					<c:forEach items="${ delivery.orderItems }" var="orderItem" varStatus="itemIndex">
				 						<c:set var="deliveryKey" value="single-${ delivery.deliveryId }-${ orderItem.itemId }" />
					 					<tr>
						 					<td class="left none_left">	 						  
						 						<div class="order_img_min"><img src="${orderItem.item.imageSrc}" alt="상품이미지" width="90" /></div>
						 						<div class="arrival" style="padding-left:  92px;"> 
						 							
						 							<p>${op:message('M00019')} <!-- 상품번호 --> : ${ orderItem.item.itemUserCode }</p>
						 							<p class="sale">${ orderItem.item.itemName }</p>
						 							<c:choose>
						 								<c:when test="${ !empty orderItem.requiredOptionsList }">
							 								<br />
							 								<c:forEach items="${ orderItem.requiredOptionsList }" var="option">
																<p class="option" style="font-size: 11px">
																	<span>${ option.optionName1 }</span> : 
																	${option.optionName2} 
																	<c:if test="${!empty option.extraPrice && option.extraPrice != 0}">
																	(<c:if test="${ option.extraPrice > 0}">+</c:if>${op:numberFormat(option.extraPrice)}円)
																	</c:if>
																	
																	<c:if test="${!empty option.optionCode}">
																	(商品番号 : ${option.optionCode})
																	</c:if>
																</p>
															</c:forEach>
						 								</c:when>
						 								<c:otherwise>
						 									<p class="name">&nbsp;</p>
						 								</c:otherwise>
						 							</c:choose>
						 							
						 						</div>	 						 
						 					</td>
						 					<td>${ op:numberFormat(orderItem.itemPriceData.sumItemPrice) }</td> 
						 					<td>${ op:numberFormat(orderItem.quantity) }</td>
						 					
						 					<c:if test="${ useCoupon == true }">
							 					<td>
						 							<c:if test="${ orderItem.itemPriceData.itemCouponDiscountAmount > 0 }">-</c:if>
						 							${ op:numberFormat(orderItem.itemPriceData.itemCouponDiscountAmount) } ${ unit }
							 					</td>
							 				</c:if>
							 				
						 					<td>
						 						<div class="sum">${ op:numberFormat(orderItem.itemPriceData.itemPayAmount) }</div>
						 					</td>
						 					<c:if test="${ itemIndex.index == 0 }">
					 							<td rowspan="${ rowspan }">
					 								<c:choose>
						 								<c:when test="${ delivery.deliveryPrice == 0 }">${op:message('M00448')} <!-- 무료 --></c:when>
						 								<c:otherwise>${ op:numberFormat(delivery.deliveryPrice) } ${unit}</c:otherwise>
						 							</c:choose>
						 						</td>
						 					</c:if>
						 				</tr>  
				 					</c:forEach>
				 				</c:if>
				 			</c:forEach>
				 		</c:forEach>
		 			</tbody>
		 		</table><!--//esthe-table E-->
		 	</div>	<!-- // table_list_type01 E -->
	 	</c:if>
	 	
		<c:forEach items="${ order.orderVendors }" var="vendor" varStatus="vendorIndex">
			<c:forEach items="${ vendor.orderDeliverys }" var="delivery" varStatus="deliveryIndex">
				<c:if test="${ delivery.deliveryType eq '1' }">
				 	<div class="table_list_type01 pt10">
						<p class="unit"><span>“${ delivery.title }” ${op:message('M00001')} <!-- 상품 --> </span> ${op:message('M00427')}<!-- 단위 --> : ${unit}</p>
					
				 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table">
				 			<caption>table list</caption>
				 			<colgroup>
				 				<col style="width:auto;">	 				
				 				<col style="width:100px;">
				 				<col style="width:60px;">
				 				
				 				<c:if test="${ useCoupon == true }">
				 					<col style="width:100px;">
				 				</c:if>
				 				
				 				<col style="width:100px;">
				 				<col style="width:100px;"> 
				 			</colgroup>
				 			<thead>
				 				<tr>
				 					<th scope="col" class="none_left">${op:message('M00018')} <!-- 상품명 --></th>
				 					<th scope="col">${op:message('M00356')} <!-- 단가 --> <br />(${ shopContext.config.taxDisplayTypeText })</th>
				 					<th scope="col">${op:message('M00357')} <!-- 수량 --></th>
				 					
				 					<c:if test="${ useCoupon == true }">
				 						<th scope="col">${op:message('M00628')} <!-- 할인금액 --> </th>
				 					</c:if>
				 					
				 					<th scope="col">${op:message('M00817')} <!-- 상품합계 --><br />(${ shopContext.config.taxDisplayTypeText })</th>
				 					<th scope="col">${op:message('M00359')} <!-- 배송료 --> </th>
				 				</tr>
				 			</thead>
				 			<tbody>
			 					<c:set var="rowspan" value="${ fn:length(delivery.orderItems) }" />
			 					<c:forEach items="${ delivery.orderItems }" var="orderItem" varStatus="itemIndex">
				 					<tr>
					 					<td class="left none_left">	 						  
					 						<div class="order_img_min"><img src="${orderItem.item.imageSrc}" alt="${op:message('M00659')} <!-- 상품이미지 --> " width="90" /></div>
					 						<div class="arrival" style="padding-left:  92px;"> 
					 							
					 							<p>${op:message('M00019')} <!-- 상품번호 --> : ${ orderItem.item.itemUserCode }</p>
					 							<p class="sale">${ orderItem.item.itemName }</p>
					 							<c:choose>
					 								<c:when test="${ !empty orderItem.requiredOptionsList }">
						 								<br />
						 								<c:forEach items="${ orderItem.requiredOptionsList }" var="option">
															<p class="option" style="font-size: 11px">
																<span>${ option.optionName1 }</span> : 
																${option.optionName2} 
																<c:if test="${!empty option.extraPrice && option.extraPrice != 0}">
																(<c:if test="${ option.extraPrice > 0}">+</c:if>${op:numberFormat(option.extraPrice)}円)
																</c:if>
																
																<c:if test="${!empty option.optionCode}">
																(商品番号 : ${option.optionCode})
																</c:if>
															</p>
														</c:forEach>
					 								</c:when>
					 								<c:otherwise>
					 									<p class="name">&nbsp;</p>
					 								</c:otherwise>
					 							</c:choose>
					 							
					 						</div> 						 
					 					</td>
					 					<td>${ op:numberFormat(orderItem.itemPriceData.sumItemPrice) }</td> 
					 					<td>${ op:numberFormat(orderItem.quantity) }</td>
					 					
					 					<c:if test="${ useCoupon == true }">
						 					<td>
					 							<c:if test="${ orderItem.itemPriceData.itemCouponDiscountAmount > 0 }">-</c:if>
					 							${ op:numberFormat(orderItem.itemPriceData.itemCouponDiscountAmount) } ${ unit }
						 					</td>
						 				</c:if>
						 				
					 					<td class="${ deliveryGroupKey } ${deliveryChargeKey} orderItem" id="item-${ orderItem.userOrderItemSequence }">
					 						<div class="sum">${ op:numberFormat(orderItem.itemPriceData.itemPayAmount) }</div>
					 					</td>
					 					
					 					<c:if test="${ itemIndex.index == 0 }">
				 							<td rowspan="${ rowspan }">
				 								<c:choose>
					 								<c:when test="${ delivery.deliveryPrice == 0 }">${op:message('M00448')} <!-- 무료 --></c:when>
					 								<c:otherwise>${ op:numberFormat(delivery.deliveryPrice) } ${unit}</c:otherwise>
					 							</c:choose>
					 						</td> 
					 					</c:if> 
					 				</tr>  
			 					</c:forEach>
			 				
				 			</tbody>
				 		</table><!--//esthe-table E-->	
				 	</div> <!-- // table_list_type01 E -->
			 	</c:if>
		 	</c:forEach>
		</c:forEach>
		
	 	<c:if test="${ isLogin == true }">
	 		<div class="boadr_total">
				<c:if test="${ order.orderPrice.totalEarnPoints > 0 }">
		 			<p><span class="sum">${op:message('M00964')} <!-- 총 적립예정금 -->  : ${ op:numberFormat(order.orderPrice.totalEarnPoints) }</span>ポイント</p>
					<p>${op:message('M00939')} <!-- 고객의 경우 본 주문의 지불이 완료되면 -->  ${ op:numberFormat(order.orderPrice.totalEarnPoints) }${op:message('M00940')} <!-- 원의 포인트가 적립됩니다. 포인트에 대한 자세한 설명은 -->  <a href="/pages/etc_guide#08" class="">${op:message('M00941')} <!-- 여기 --> </a>${op:message('M00942')} <!-- 를 참조하십시오. --> </p>
				</c:if>
			</div><!--//boadr_total E-->
	 	</c:if>
	 	<div class="total_wrap">
	 		<table cellpadding="0" cellspacing="0" summary="상품금액, 배송비, 할인금액, 최종 결제 금액" class="total_price">
				<caption>${op:message('M00360')} <!-- 총 구매금액 --> </caption>
				<colgroup>
					<c:set var="width" value="140" />
					<c:if test="${ useCoupon == false }">
						<c:set var="width" value="200" />
					</c:if>
				
					<col width="${ width }px;">
					
					<c:if test="${ useCoupon == true }">
						<col width="${ width }px;">
					</c:if>
					
					<col width="${ width }px;">
					<col width="${ width }px;">
					
					<c:if test="${ useCoupon == true }">
						<col width="${ width }px;">
					</c:if>
					
					<col width="${ width }px;">
					<col width="*;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">商品合計 (${ shopContext.config.taxDisplayTypeText })</th>
						
						<c:if test="${ useCoupon == true }">
							<th scope="col">${op:message('M00818')} <!-- 상품쿠폰 --> </th>
						</c:if>
						
						<th scope="col">消費税 <!-- 소비세 --> </th>
						<th scope="col">${op:message('M00924')} <!-- 배송료(세금포함) --> </th>
						
						<c:if test="${ useCoupon == true }">
							<th scope="col">${op:message('M00820')} <!-- 장바구니쿠폰 --> </th>
						</c:if>
						
						<th scope="col">${op:message('M00246')} <!-- 포인트 --> </th> 
						<th scope="col">最終決済金額（税込）</th>
					</tr>
				</thead>	
				<tbody>
					<tr>
						<td>${ op:numberFormat(order.orderPrice.totalItemPrice) }<span>円</span></td>
						
						<c:if test="${ useCoupon == true }">
							<td><label class="op-total-item-coupon-discount-amount-text">${ op:numberFormat(order.orderPrice.totalItemCouponDiscountAmount) }</label><span>円</span></td>
						</c:if>
						
						<td><label class="totalExcisePriceText">${ op:numberFormat(order.orderPrice.totalExcisePrice) }</label><span>円</span></td>
						<td><label class="op-total-delivery-charge-text">${ op:numberFormat(order.orderPrice.totalDeliveryCharge) }</label><span>円</span></td>
						
						<c:if test="${ useCoupon == true }">
							<td><label class="op-total-cart-coupon-discount-amount-text">${ op:numberFormat(order.orderPrice.totalCartCouponDiscountAmount) }</label><span>円</span></td>
						</c:if>
						
						<td><label class="op-total-point-discount-amount-text">${ op:numberFormat(order.orderPrice.totalPointDiscountAmount) }</label><span>円</span></td> 
						<td class="total_num"><label class="orderPayAmountText">${ op:numberFormat(order.orderPrice.orderPayAmount) }</label><span>円</span></td>
					</tr>
				</tbody>
			</table>
			
			<c:set var="plus" value="${ width }" />
			<c:set var="left" value="${ width - 4 }" />
			
			<c:if test="${ useCoupon == true }">
				<span class="btn_minus01" style="left:${ left }px;"><img src="/content/images/btn/btn_minus.png" alt="minus" /></span>
				<c:set var="left" value="${ left + plus }" />
			</c:if>
			
			
			<span class="btn_plus01" style="left:${ left }px;"><img src="/content/images/btn/btn_plus.png" alt="plus" /></span>
			<c:set var="left" value="${ left + plus }" />
			<span class="btn_plus02" style="left:${ left }px;"><img src="/content/images/btn/btn_plus.png" alt="plus" /></span>
			
			<c:if test="${ useCoupon == true }">
				<c:set var="left" value="${ left + plus }" />		
				<span class="btn_minus02" style="left:${ left }px;"><img src="/content/images/btn/btn_minus.png" alt="minus" /></span>
			</c:if>
			
			<c:set var="left" value="${ left + plus }" />
			<span class="btn_minus03" style="left:${ left }px;"><img src="/content/images/btn/btn_minus.png" alt="minus" /></span>
			
			<c:set var="left" value="${ left + plus }" />
			<span class="btn_total" style="left:${ left }px;"><img src="/content/images/btn/btn_total.png" alt="total" /></span>
		</div> <!-- // total_wrap -->
	 	
	 	<div class="board_view_type01">
	 		<h4>${op:message('M00428')} <!-- 주문하시는분 --></h4>
	 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
	 			<caption>table list</caption>
	 			<colgroup>
	 				<col style="width:180px;">
	 				<col style="width:auto;">	 				
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<td class="label">${op:message('M00105')} <!-- 회사명 --></td>
	 					<td>${ order.companyName }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00429')} <!-- 주문자 이름 (한자) --></td>
	 					<td>${ order.userName }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00430')} <!-- 주문자 이름(가나) --></td>
	 					<td>${ order.userNameKatakana }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00125')} <!-- 이메일 --></td>
	 					<td>${ order.email }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00016')} <!-- 전화번호 --></td>
	 					<td>${ order.phone }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00329')} <!-- 휴대전화번호 --></td>
	 					<td>${ order.mobile }</td>
	 				</tr>
	 				<tr>
	 					<td class="label last">${op:message('M00118')} <!-- 주소 --></td>
	 					<td class="last">
	 						<div>
		 						${ order.zipcode } <br><br>
								${ order.dodobuhyun } ${ order.address } <br><br>
								${ order.addressDetail }
							</div>
	 					</td>
	 				</tr>
	 			</tbody>
	 		</table><!--//view E-->	 	
	 	</div> <!-- // board_view_type01 E -->
	 	
	 	<div class="board_view_type01">
	 		<h4>${op:message('M00432')} <!-- 상품 받으시는 분 --></h4>
	 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
	 			<caption>table list</caption>
	 			<colgroup>
	 				<col style="width:180px;">
	 				<col style="width:auto;">	 				
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<td class="label">${op:message('M00943')} <!-- 배송지명 --> </td>
	 					<td>${ order.receiveCompanyName }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00944')} <!-- 수취인 이름(한자) --> </td>
	 					<td>${ order.receiveName }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00945')} <!-- 수취인 이름(카나) --> </td>
	 					<td>${ order.receiveNameKatakana }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00016')} <!-- 전화번호 --> </td>
	 					<td>${ order.receiveMobile }</td>
	 				</tr>
	 				<tr>
	 					<td class="label">${op:message('M00329')} <!-- 휴대전화번호 --> </td>
	 					<td>${ order.receivePhone }</td>
	 				</tr>
	 				<tr>
	 					<td class="label last">${op:message('M00118')} <!-- 주소 --> </td>
	 					<td class="last">
	 						<div>
		 						${ order.receiveZipcode } <br><br>
								${ order.receiveDodobuhyun } ${ order.receiveAddress } <br><br>
								${ order.receiveAddressDetail }
							</div>
	 					</td>
	 				</tr>
	 			</tbody>
	 		</table><!--//view E-->	 	
	 	</div> <!-- // board_view_type01 E -->

	 	<div class="board_view_type01">
	 		<h4>${op:message('M00611')} <!-- 배송방법 --> </h4>
	 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
	 			<caption>table list</caption>
	 			<colgroup>
	 				<col style="width:180px;">
	 				<col style="width:auto;">	 				
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<td class="label">${op:message('M00611')} <!-- 배송방법 --> </td>
	 					<td>
							<c:choose>
	 							<c:when test="${ order.deliveryMailButtonViewFlag eq 'Y' }">
	 								${op:message('M00609')} <!-- 메일편 --> 
	 							</c:when>
	 							<c:otherwise>
	 								${op:message('M00610')} <!-- 사가와 익스프레스 --> 
	 							</c:otherwise>
	 						</c:choose>
		 				</td>
	 				</tr>
	 			</tbody>
	 		</table><!--//view E-->	 	
	 	</div> <!-- // board_view_type01 E -->
		 	<div class="board_view_type01">
		 		<h4>${op:message('M00946')} <!-- 배송날짜정보 --> </h4>
		 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:180px;">
		 				<col style="width:auto;">	 				
		 			</colgroup>
		 			<tbody>
		 				<tr>
		 					<td class="label">${op:message('M00319')} <!-- 배송희망일 --> </td>
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
		 					<td class="label last">${op:message('M00320')} <!-- 배송희망시간 --> </td>
		 					<td class="last">
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
		 		</table><!--//view E-->	 	
		 	</div> <!-- // board_view_type01 E -->
	 	
	 	<c:if test="${ order.approvalType ne 'noPay' }">
		 	<div class="board_view_type01">
		 		<h4>${op:message('M00947')} <!-- 지불방법 --> </h4>
		 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
		 			<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:180px;">
		 				<col style="width:auto;">	 				
		 			</colgroup>
		 			<tbody>
		 				<c:choose>
		 					<c:when test="${ order.approvalType eq 'card' }">
		 						<tr>
				 					<td class="label">支払方法</td>
				 					<td>クレジットカード</td>
				 				</tr>
				 				<tr>
				 					<td class="label">${op:message('M00626')} <!-- 카드번호 --></td>
				 					<td>
				 						${ pgData.cardNumber }
				 						<input type="hidden" name="cardNumber" value="${ pgData.cardNumber }" />
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="label">有効期限</td>
				 					<td>
				 						${ pgData.expMonth }月/${ pgData.expYear }年
				 						<input type="hidden" name="expMonth" value="${ pgData.expMonth }" />
				 						<input type="hidden" name="expYear" value="${ pgData.expYear }" />
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="label">名義人</td>
				 					<td>
				 						${ pgData.cardName }
				 						<input type="hidden" name="cardName" value="${ pgData.cardName }" />
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="label">お支払い回数</td>
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
				 					<td class="label last">支払方法</td>
				 					<td class="last">現金代引き</td>
				 				</tr>
		 					</c:when>
		 					<c:when test="${ order.approvalType eq 'conv' }">
		 						<tr>
				 					<td class="label">支払方法</td>
				 					<td><div>コンビニ払い</div></td>
				 				</tr> 
				 				<tr>
				 					<td class="label">利用するコンビニ</td>
				 					<td>
				 						${ pgData.cvsName }
				 						<input type="hidden" name="cvsType" value="${ pgData.cvsType }" />
				 					</td>
				 				</tr> 
				 				<tr>
				 					<td class="label">お名前(姓)</td>
				 					<td>
				 						${ pgData.reqName1 }
				 						<input type="hidden" name="reqName1" value="${ pgData.reqName1 }" />
				 					</td>
				 				</tr> 
				 				<tr>
				 					<td class="label">お名前(名)</td>
				 					<td>
				 						${ pgData.reqName2 }
				 						<input type="hidden" name="reqName2" value="${ pgData.reqName2 }" />
				 					</td>
				 				</tr> 
				 				<tr>
				 					<td class="label">お名前(カナ)</td>
				 					<td>
				 						${ pgData.reqKana }
				 						<input type="hidden" name="reqKana" value="${ pgData.reqKana }" />
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="label">電話番号</td>
				 					<td>	
				 						${ pgData.reqTelNumber }
				 						<input type="hidden" name="reqTelNumber" value="${ pgData.reqTelNumber }" />
				 					</td>
				 				</tr> 
				 				<tr>
				 					<td class="label last" colspan="2">
				 						<div class="pay_tex2">
			 								<ul>
			 									<li class="accent3">※前払いです。</li>
												<li><p>ローソン、セイコーマート、ファミリーマート、サークルKサンクス、ミニストップ、ヤマザキデイリーの各コンビニエンスストアでお支払いいただけます。</p>
													
												</li>
												<li>セブンイレブンはご利用できませんのでご了承ください。 </li>
												<li>追加注文などがある場合はコンビニ払い以外の支払い方法を選んでください。</li>
												<li>支払番号には有効期限がございますので7日以内にお支払をしないと自動キャンセルになります。 </li>
												<li>支払番号は、ご購入者が選択されたコンビニでのみ支払可能です。</li>
												<li>返金は、コンビニ経由ではなく、店舗からの直接の返金となります。</li>
												<li>お支払手順は以下の各コンビニー名をクリックしてください。</li>
			 								</ul>	
			 							</div>
			 							<div class="store_ic2">
					 						<ul>
					 							<li><a href="#"><img src="/content/images/order/img_store_seven_m.png" alt=""></a></li>
					 							<li><a href="#"><img src="/content/images/order/img_store_lawson_m.png" alt=""></a></li>
					 						</ul>
					 						<ul>
					 							<li><a href="#"><img src="/content/images/order/img_store_family_m.png" alt=""></a></li>
					 							<li><a href="#"><img src="/content/images/order/img_store_mini_m.png" alt=""></a></li>
					 						</ul>
					 						<ul>
					 							<li><a href="#"><img src="/content/images/order/img_store_k_m.png" alt=""></a></li>
					 							<li><a href="#"><img src="/content/images/order/img_store_seico_m.png" alt=""></a></li>
					 							<li><a href="#"><img src="/content/images/order/img_store_01_m.png" alt=""></a></li>
					 							<li><a href="#"><img src="/content/images/order/img_store_02_m.png" alt=""></a></li>
					 						</ul>
					 					</div>
				 					</td>
				 				</tr>
		 					</c:when>
		 					<c:when test="${ order.approvalType eq 'bank' }">
		 						<tr>
				 					<td class="label">支払方法</td>
				 					<td>銀行振込</td>
				 				</tr>
		 						<tr>
				 					<td class="label">${op:message('M00541')} <!-- 입금은행 --></td>
				 					<td>
			 							<p>
			 								${ order.bankVirtualNo }
			 							</p>
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="label">${op:message('M00542')} <!-- 입금명의 --></td>
				 					<td>
				 						<p>
											${ order.bankInName }
										</p>
				 					</td>
				 				</tr>
				 				<tr>
				 					<td class="label last">${op:message('M00543')} <!-- 입금예정일 --></td>
				 					<td class="last">
				 						<p>
											${ order.bankDate }
										</p>
				 					</td>
				 				</tr> 
		 					</c:when>
		 				</c:choose>
		 			</tbody>
		 		</table><!--//view E-->	 	
		 	</div> <!-- // board_view_type01 E -->
	 	</c:if>
	 	
	 	<div class="board_view_type01">
	 		<h4>注文追加情報  <!-- 메모 --> </h4>
	 		<table cellpadding="0" cellspacing="0" summary="" class="esthe-table view">
	 			<caption>table list</caption>
	 			<colgroup>
	 				<col style="width:180px;">
	 				<col style="width:auto;">	 				
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<td class="label last">コメント</td>
	 					<td class="last">${ op:nl2br(order.content) }</td>
	 				</tr>
	 			</tbody>
	 		</table><!--//view E-->	 	
	 	</div> <!-- // board_view_type01 E -->
	 	
	 	<div class="txt_bottom">
		 	<p class="txt-notice">※ 以下の「注文する」ボタンを押してご注文いただくことで、お客様は当サイトの「プライバシー規約」に同意の上、商品をご注文されたことになります。</p>
		 	<ul>
		 		<li class="txt-notice2">処理が正しく行われない恐れがありますので、以下の行為は行わないで下さい。</li>
				<li>- 注文するボタンは２回以上クリックしないで下さい。</li>
				<li>- 決済開始後はブラウザの戻るボタン、中止ボタン、F5(更新ボタン)を押さないでください。</li>
		 	</ul>
	 	</div>
	 	
	 	
	 	
	 	<div class="btn_end">
			<button type="submit" class="btn btn-success btn-lg">注文する</button>
			<button type="button" class="btn btn-default btn-lg" onclick="location.href='/order/step1'">入力情報修正</button>
		</div> <!-- // btn_end -->
 	</form:form>
 	<div class="mission_wrap cart_top">	 		
 		${shopContext.config.deliveryInfo} 		
 	</div> <!-- // mission_wrap E -->
		 		
</div><!--// sub_contents E-->


<page:javascript>
<script type="text/javascript">
$(function(){
	<c:if test="${order.approvalType eq 'card'}">
		var cardNumber = "${ pgData.cardNumber }";
		if (cardNumber == '') {
			location.href = '/order/step1';
		}
	</c:if>
})
</script>
</page:javascript>