<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<div class="inner">
	<div class="location_area">
		<!--jsp:include page="/WEB-INF/views/layouts/front/saleson_all_categories.jsp" /-->
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a> 
			<span>주문완료</span> 
		</div>
	</div><!-- // location_area E -->
	  
	<div class="content_title">
		<h2 class="title">주문완료</h2>
	</div>		<!--//content_title E-->	

	<div class="order_step">
		<ul>
			<li><span class="number">01</span> 장바구니</li>
			<li class="off"><span class="number">02</span> 주문결제</li>
			<li class="on"><span class="number">03</span> 주문완료</li> 
		</ul>
	</div> <!--// order_step E -->	
	
	<div class="order_thank"> 
		<p class="txt02">리뉴올PC를 이용해 주셔서 감사합니다.</p>
		<p class="txt03">고객님의 주문이 완료되었습니다. 주문하신 내역은 <strong><a href="/mypage/order">[마이페이지]</a></strong>에서 확인이 가능합니다.</p>
	</div>
		
	<div class="order_confirm"> 
		<p class="number">주문번호 : <span>${ orderCode }</span></p> 
	</div><!--// order_confirm E--> 
	
	<h3 class="sub_title mt30">주문내역</h3>
	
	<c:set var="totalPrice" value="0"/>
	<c:set var="totalEarnPoint" value="0"/>
	<c:set var="totalCouponDiscountAmount" value="0"/>
	<c:set var="totalUsedPoint" value="0"/>

	<c:forEach items="${order.orderPayments}" var="payment">
		<c:if test="${payment.amount > 0 || payment.cancelAmount > 0 || payment.remainingAmount > 0}">
			<c:if test="${payment.approvalType == 'point'}">
				<c:if test="${payment.paymentType == '1'}">
					<c:set var="totalUsedPoint" value="${totalUsedPoint + payment.amount }"/>
				</c:if>
			</c:if>
		</c:if>
	</c:forEach>
	
	
	<c:forEach var="shippingInfo" items="${order.orderShippingInfos }" varStatus="i"> 
		<div class="board_wrap">  
			<table cellpadding="0" cellspacing="0" class="order_view">
				<caption>배송지 ${i.index }</caption>
				<colgroup>
					<col style="width:160px;"> 
					<col style="width:auto;">	 				
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">구매자 이름</th>
						<td>${order.buyerName }</td> 
					</tr> 
					<tr>
						<th scope="row">수취인 이름</th>
						<td>${shippingInfo.receiveName }</td> 
					</tr> 
					<tr>
						<th scope="row">배송지 주소</th>
						<td>(${shippingInfo.receiveNewZipcode }) ${shippingInfo.receiveAddress }&nbsp;${shippingInfo.receiveAddressDetail }</td> 
					</tr> 
					<tr>
						<th scope="row">수취인 휴대전화</th>
						<td>${shippingInfo.receiveMobile }</td> 
					</tr> 
					<tr>
						<th scope="row">수취인 연락처</th>
						<td>${shippingInfo.receivePhone }</td> 
					</tr> 
					<tr>
						<th scope="row">배송시 요구사항</th>
						<td>${shippingInfo.memo }</td> 
					</tr>
				</tbody>
			</table><!--//view E-->	 	
		</div>

		<div class="board_wrap mt15"> 	
			<table cellpadding="0" cellspacing="0" class="order_list">
				<caption>상품 list</caption>
				<colgroup> 
					<col style="width:auto;">
					<col style="width:72px;">
					<col style="width:108px;">
					<col style="width:108px;">
					<col style="width:108px;">
					<col style="width:120px;">  
				</colgroup>
				<thead>
					<tr> 
						<th scope="col" class="none_left">상품명/옵션정보</th>
						<th scope="col">수량</th>
						<th scope="col">상품금액</th>
						<th scope="col">할인금액</th>
						<th scope="col">할인금액적용</th> 
						<th scope="col">배송비</th>  
					</tr>
				</thead>
				<tbody>
					<c:forEach var="orderItem" items="${shippingInfo.orderItems }">
						<c:set var="totalEarnPoint" value="${totalEarnPoint + (orderItem.earnPoint * orderItem.quantity)}"/>
						<c:set var="totalCouponDiscountAmount" value="${totalCouponDiscountAmount + orderItem.couponDiscountAmount }"/>
						<c:set var="totalPrice" value="${totalPrice + ((orderItem.price + orderItem.optionPrice) * orderItem.quantity)}"/>

						<c:set var="totalItemAmount" value="${orderItem.itemAmount}" />
						<c:set var="totalDiscountAmount" value="${orderItem.discountAmount}" />
						<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />

						<c:forEach items="${orderItem.additionItemList}" var="addition">
							<c:set var="totalEarnPoint" value="${totalEarnPoint + (addition.earnPoint * addition.quantity)}"/>
							<c:set var="totalItemAmount">${totalItemAmount + addition.itemAmount}</c:set>
							<c:set var="totalDiscountAmount">${totalDiscountAmount + addition.discountAmount}</c:set>
							<c:set var="totalSaleAmount">${totalSaleAmount + addition.saleAmount}</c:set>
						</c:forEach>

						<tr>
							<td class="tleft">
								<div class="item_info">
									<p class="photo"><img src="${ shop:loadImageBySrc(orderItem.imageSrc, 'XS') }" alt="item photo"></p>
									<div class="order_option noline">
										<p class="item_name line2">
											${orderItem.itemName}
										</p>
										${ shop:viewOptionText(orderItem.options) }

                                        ${shop:viewAdditionOrderItemList(orderItem.additionItemList)}

										<c:set var="orderGiftItemText" value="${shop:makeOrderGiftItemText(orderItem.orderGiftItemList)}"/>
										<c:if test="${not empty orderGiftItemText}">
											<ul class="option">
												<li>
													<span class="choice">사은품 : </span>
													<span>${orderGiftItemText}</span>
												</li>
											</ul>
										</c:if>

									</div>
								</div>
							</td>
							<td>${op:numberFormat(orderItem.quantity)} 개</td>
							<td>
								<p class="price_01">${op:numberFormat(totalItemAmount)}원</p>
							</td>
							<td>
								<p class="price_01">${ op:numberFormat(totalDiscountAmount) }원</p>
							</td>
							<td>
								<div class="sum">${op:numberFormat(totalSaleAmount)}원</div>
							</td>
							<td>
								<p class="delivery_price">
									<c:choose>
			 							<c:when test="${orderItem.isShippingView == 'Y'}">
			 								<c:choose>
			 									<c:when test="${orderItem.orderShipping.shippingPaymentType == '2'}">
			 										<c:choose>
														<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
															퀵
														</c:when>
														<c:otherwise>
															${op:numberFormat(orderItem.orderShipping.realShipping)}원
														</c:otherwise>
													</c:choose>
													(착불)
			 									</c:when>
			 									<c:otherwise>
			 										<c:choose>
			 											<c:when test="${orderItem.orderShipping.payShipping == 0}">무료배송</c:when>
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
													퀵 (착불)
												</c:when>
												<c:otherwise>
													무료배송
												</c:otherwise>
											</c:choose>
										</c:otherwise>
			 						</c:choose>
								</p>
								<%--<p class="where_buy">조건부 10,000원 이상 무료</p> --%>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table><!--// mypage_list E-->	  	 		 
		</div> <!-- /// board_wrap E -->
	
	</c:forEach>

	<!--// 배송방법 추가 S -->
	<h3 class="sub_title mt30">배송방법</h3>
	<div class="board_wrap">
		<table cellpadding="0" cellspacing="0" class="order_view">
			<caption>배송방법</caption>
			<colgroup>
				<col style="width:160px;">
				<col style="width:auto;">
			</colgroup>
			<tbody>
			<tr>
				<th scope="row">배송방법</th>
				<td>
					<c:choose>
						<c:when test="${order.quickDeliveryFlag == 'Y'}">
							퀵서비스
						</c:when>
						<c:otherwise>
							일반택배
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th scope="row">배송비</th>
				<td><b class="delv_price">${op:numberFormat(order.totalShippingAmount)}</b>
					원
					<c:if test="${order.quickDeliveryFlag == 'Y'}">
						(착불)
					</c:if>
				</td>
			</tr>
			<tr>
				<th scope="row" valign="top">배송 주의사항</th>
				<td>
					<div class="box_list_02 pt0">
						<ul>
							<li><span class="tit">택배</span>평일 00시까지 결제완료하시면 당일 발송됩니다.</li>
							<li><span class="tit">퀵서비스</span>평일 00시까지 결제완료하시면 당일 발송됩니다.<br>퀵서비스 특성상 다품목, 대량 주문 시 결제하신 운송비보다 초과할 경우 착불처리되오니 이용에 참고부탁드립니다.</li>
						</ul>
					</div>
				</td>
			</tr>
			</tbody>
		</table><!--//view E-->
	</div>
	<!--// 배송방법 추가 E -->
	
	<%--<div class="boadr_total">
		<div>
			총 상품합계 금액  ${op:numberFormat(order.itemTotalAmount)}원  +  배송비  ${op:numberFormat(order.shippingTotalAmount)}원  = <span class="delivery_total">총액 <span>${op:numberFormat(order.orderTotalAmount)}원</span></span>					  
		</div> 
	</div> --%>
	
	<h3 class="sub_title mt30">결제내역</h3>
	<div class="price_box" style="margin-top: 10px">
		<div class="price_inner">
			<div class="money">
				<p class="txt01">상품금액</p>
				<p class="prices"><span>${op:numberFormat(order.totalItemAmount)}</span>원</p>
			</div>

			<span class="icons"><img src="/content/images/icon/icon_minus.png" alt="빼기"></span>
			<div class="money">
				<p class="txt01">할인금액</p>
				<p class="prices"><span>${op:numberFormat(order.totalDiscountAmount)}</span>원</p>
			</div>

			<span class="icons"><img src="/content/images/icon/icon_plus.png" alt="더하기"></span>
			<div class="money">
				<p class="txt01">배송비</p>
				<p class="prices"><span>${op:numberFormat(order.totalShippingAmount)}</span>원</p>
			</div>


			<%--
			<span class="icons"><img src="/content/image/icon/icon_minus.png" alt="빼기"></span>
			<div class="money">
				<p class="txt01">${op:message('M00246')} 사용</p>
				<p class="prices"><span>${op:numberFormat(totalUsedPoint)}</span>원</p>
			</div>
			 --%>
			<span class="icons"><img src="/content/images/icon/icon_sum.png" alt="더하기"></span>
			<div class="money">
				<p class="txt01"><strong>결제금액</strong></p>
				<p class="prices total"><span>${op:numberFormat(order.totalOrderAmount)}</span>원</p>
			</div>
		</div> <!-- // price_inner E -->
		<div class="add_point">
			<p>적립예정 ${op:message('M00246')} : <span>${op:numberFormat(totalEarnPoint)}  P</span> </p>
		</div>
	</div>
	
 	<h3 class="sub_title mt30">결제수단</h3>  
 	<div class="board_wrap">  
 	<c:forEach items="${order.orderPayments}" var="payment">
 		<c:if test="${payment.amount > 0 || payment.cancelAmount > 0 || payment.remainingAmount > 0}">
			<c:set var="unit">원</c:set>
				
			<c:choose>
				<c:when test="${payment.approvalType == 'point'}">
					<c:set var="unit">P</c:set>
				</c:when>
			</c:choose>			
			<table cellpadding="0" cellspacing="0" class="order_view">
				<caption>결제수단</caption>
				<colgroup>
					<col style="width:15%;"> 
					<col style="width:auto;"> 	 				
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">결제방식</th>
						<td>${payment.approvalTypeLabel}</td>  
					</tr>
					<tr>
						<th scope="row">결제금액</th>
						<td>
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
					</tr>
					<tr>
						<th scope="row">결제상태</th>
						<td>
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
					</tr>
					<c:if test="${payment.approvalType == 'vbank'}">
						<tr>
							<th scope="row">입금시간</th>
							<td>${op:date(payment.bankDate)}까지</td>
						</tr>
						<tr>
							<th scope="row">입금정보</th>
							<td>${payment.bankVirtualNo}</td>
						</tr>
						<tr>
							<th scope="row">입금자명</th>
							<td>${payment.bankInName}</td>
						</tr>
					</c:if>
				</tbody>
			</table> 
		</c:if>			
	</c:forEach> 
	</div><!-- // board_wrap E -->
	
	<div class="btn_wrap pt30">  
		<a href="/" class="btn btn-default btn-lg" title="쇼핑계속하기">쇼핑계속하기</a>  
		<a href="/mypage/order" class="btn btn-success btn-lg" title="주문내역조회">주문내역조회</a> 
	</div>	 		
	
</div><!--// inner E-->

<page:javascript>
<script type="text/javascript">
	//<![CDATA[
	var DaumConversionDctSv="type=P,orderID=,amount=";
	var DaumConversionAccountID="AhEsM4T6uIfTz.omUTDgpw00";
	if(typeof DaumConversionScriptLoaded=="undefined"&&location.protocol!="file:"){
		var DaumConversionScriptLoaded=true;
		document.write(unescape("%3Cscript%20type%3D%22text/javas"+"cript%22%20src%3D%22"+(location.protocol=="https:"?"https":"http")+"%3A//t1.daumcdn.net/cssjs/common/cts/vr200/dcts.js%22%3E%3C/script%3E"));
	}
	//]]>

	$(document).ready(function() {

		try {
			var jsonString = '${orderItemUserCodes}';
			var orderCode = '${orderCode}';

			if (jsonString != '' && jsonString != 'null') {
				EventLog.order(orderCode, JSON.parse(jsonString));
			}
		} catch (e) {}
	});

</script>
</page:javascript>

<!-- 전환페이지 설정 -->
<script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script> 
<script type="text/javascript"> 
	var _nasa={};
	if(window.wcs) _nasa["cnv"] = wcs.cnv("1","1"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
</script> 
