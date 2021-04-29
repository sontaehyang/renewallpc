<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

	<c:set var="mypageUrl"></c:set>
	
	<sec:authorize access="hasRole('ROLE_USER')">
	<c:set var="mypageUrl">/m/mypage/order</c:set>
	</sec:authorize>
	
	<sec:authorize access="!hasRole('ROLE_USER')">
	<c:set var="mypageUrl">/m/users/login?target=/m/mypage/order&mode=guest</c:set>
	</sec:authorize>

	<div class="title">
		<h2>주문완료</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- 내용 : s -->
		<div class="con">
			<div class="order_wrap">
				<div class="order_txt">
					<p class="main_txt">리뉴올PC를<br>이용해 주셔서 감사합니다.</p>
					<p class="sub_txt">고객님의 주문하신 결제가 완료되었습니다.<br>주문하신 내역은 <a href="${mypageUrl}">마이페이지</a>에서 확인이 가능합니다.</p>
					<p class="order_number">주문번호 : <span>${ orderCode }</span></p>
				</div>
				<div class="order_list">
					<div class="order_tit">
						<h3>주문내역</h3>
					</div>
					<ul class="item_list">
						<c:forEach items="${ order.orderShippingInfos }" var="shippingInfo" varStatus="orderItemGroupIndex">
							<c:forEach items="${ shippingInfo.orderItems }" var="orderItem" varStatus="i">
								<c:set var="totalItemAmount" value="${orderItem.itemAmount}" />
								<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />

								<c:forEach items="${orderItem.additionItemList}" var="addition">
									<c:set var="totalEarnPoint" value="${totalEarnPoint + (addition.earnPoint * addition.quantity)}"/>
									<c:set var="totalItemAmount">${totalItemAmount + addition.itemAmount}</c:set>
									<c:set var="totalSaleAmount">${totalSaleAmount + addition.saleAmount}</c:set>
								</c:forEach>
								<li>
									<div class="item">
										<div class="order_img">
											<%-- <img src="${orderItem.imageSrc}" alt="item photo"> --%>
											<img src="${shop:loadImageBySrc(orderItem.imageSrc, 'XS')}" alt="item photo">
										</div>
										<div class="order_name">
											<p class="tit">${orderItem.itemName}</p>
											<p class="detail">
												${ shop:viewOptionText(orderItem.options) }
												${shop:viewAdditionOrderItemList(orderItem.additionItemList)}
											</p>

											<c:set var="orderGiftItemText" value="${shop:makeOrderGiftItemText(orderItem.orderGiftItemList)}"/>
											<c:if test="${not empty orderGiftItemText}">
												<p class="detail">${ orderGiftItemText }</p>
											</c:if>
										</div>
										<div class="order_price">
											<p class="price">
												<span class="discount">${op:numberFormat(totalItemAmount)}원</span>
												<span class="sale_price">${op:numberFormat(totalSaleAmount)}</span>원 (${op:numberFormat(orderItem.quantity)}개)
											</p>
										</div>
									</div>
									<!-- //item -->
								</li>
							</c:forEach>
						</c:forEach>
					</ul>
					<!-- //item_list -->
				</div>
				<!-- //order_list -->

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
								<div class="info-view">
									<c:choose>
										<c:when test="${order.quickDeliveryFlag == 'Y'}">
											퀵서비스
										</c:when>
										<c:otherwise>
											일반택배
										</c:otherwise>
									</c:choose>
								</div>
							</li>
							<li>
								<span class="del_tit t_lgray">배송비</span>
								<div class="info-view">
									${op:numberFormat(order.totalShippingAmount)}원
									<c:if test="${order.quickDeliveryFlag == 'Y'}">
										(착불)
									</c:if>
								</div>
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

				<div class="payment_info">
					<div class="order_tit">
						<h3>결제정보</h3>
					</div>
					<div class="order_con">
						<ul class="del_info">
							<li>
								<strong class="del_tit t_gray">상품금액</strong>
								<p class="del_detail"><span>${op:numberFormat(order.totalItemAmount)}</span>원</p>
							</li>
							<li>
								<strong class="del_tit t_gray">할인금액</strong>
								<p class="del_detail"><span>${op:numberFormat(order.totalDiscountAmount)}</span>원</p>
							</li>
							<li>
								<strong class="del_tit t_gray">배송비</strong>
								<p class="del_detail"><span>${op:numberFormat(order.totalShippingAmount)}</span>원</p>
							</li>
							<li>
								<strong class="del_tit t_gray">결제금액</strong>
								<p class="del_detail"><span>${op:numberFormat(order.totalOrderAmount)}</span>원 </p>
							</li>
							<c:forEach items="${order.orderPayments}" var="payment">
								<c:if test="${payment.amount > 0 || payment.cancelAmount > 0 || payment.remainingAmount > 0}">
									<c:set var="unit">원</c:set>
									<c:choose>
										<c:when test="${payment.approvalType == 'point'}">
											<c:set var="unit">P</c:set>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${empty payment.payDate}">
											<c:set var="paymentTypeLabel">미결</c:set>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${payment.paymentType == '1'}">
													<c:set var="paymentTypeLabel">결제완료</c:set>
												</c:when>
												<c:otherwise>
													<c:set var="paymentTypeLabel">결제취소</c:set>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									<li>
										<strong class="del_tit t_gray">${payment.approvalTypeLabel}</strong>
										<p class="del_detail">
											<span>
												<c:choose>
													<c:when test="${payment.paymentType == '1'}">
														${op:numberFormat(payment.amount)}
														<c:if test="${payment.amount != payment.remainingAmount && payment.remainingAmount > 0}">
															<strong style="display:block; padding-top: 5px;">(잔여액 : ${op:numberFormat(payment.remainingAmount)}${unit})</strong>
														</c:if> 
													</c:when>
													<c:otherwise>${op:numberFormat(payment.cancelAmount)}</c:otherwise>
												</c:choose>
											</span>${unit} (${paymentTypeLabel})
										</p>
										<p class="account_info">
											<c:if test="${payment.approvalType == 'bank' || payment.approvalType == 'vbank'}">
                                                    입금시간 : ${payment.approvalType == 'bank' ? payment.bankDate : op:date(payment.bankDate)}까지 <br/>
													입금정보 : ${payment.bankVirtualNo} <br>
													입금자명 : ${payment.bankInName}  <br>
											</c:if>
										</p>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!-- //payment_info -->
				
				<div class="payment_info">
					<div class="order_tit">
						<h3>주문자정보</h3>
					</div>
					<div class="order_con">
						<ul class="del_info">
							<li>
								<strong class="del_tit t_gray">주문자명</strong>
								<span class="del_detail">${order.buyerName}</span>
							</li>
							<li>
								<strong class="del_tit t_gray">이메일</strong>
								<span class="del_detail">${order.email}</span>
							</li>
							<li>
								<strong class="del_tit t_gray">연락처</strong>
								<p class="del_detail"><span>${order.mobile}</span>
								${order.phone == null ? '' : '/ '}
								${order.phone == null ? '' : order.phone}
								 </p>
							</li>
						</ul>
					</div>
				</div>
				<!-- //payment_info -->
				
				<div class="payment_info">
					<div class="order_tit">
						<h3>배송지 주소</h3>
					</div>
					<div class="shipping_con">
						<ul class="shipping_list">
							<c:forEach items="${ order.orderShippingInfos }" var="shippingInfo" varStatus="orderItemGroupIndex">
								<li>
									<span class="del_tit t_gray">배송지 ${orderItemGroupIndex.count}</span>
									<span class="del_name">${shippingInfo.receiveName}</span>
									<p class="del_text">(${shippingInfo.receiveNewZipcode})  ${shippingInfo.receiveAddress}&nbsp;${shippingInfo.receiveAddressDetail}</p>
									<p class="del_text"><span>${shippingInfo.receiveMobile}</span>
									${shippingInfo.receivePhone == null ? '' : '/ '}
									${shippingInfo.receivePhone == null ? '' : shippingInfo.receivePhone}
									</p>
									<c:if test="${not empty shippingInfo.memo}">
										<p class="del_msg">배송메시지 : <span>${shippingInfo.memo}</span></p>
									</c:if>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!-- //payment_info -->
				
				<div class="order_btn_wrap">
					<a href="${mypageUrl}" class="btn_st1 b_pink">주문내역 조회</a>
					<a href="/" class="btn_st1 b_blue">쇼핑 계속하기</a>
				</div>
				<!-- //order_btn_wrap -->
				
			</div>
			<!-- //order_wrap -->
		</div>
		<!-- 내용 : e -->

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