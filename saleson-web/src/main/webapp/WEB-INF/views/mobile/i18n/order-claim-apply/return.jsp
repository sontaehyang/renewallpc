<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<div class="con" id="layer_claim_apply">
	<div class="pop_title">
		<h3>반품 신청</h3>
		<a href="javascript:history.back()" class="history_back">뒤로가기</a>
	</div>
	
	<form:form modelAttribute="returnApply" name="returnApply" method="post" class="without-loading" onsubmit="return Mypage.claimApplyAction('2')">
		
		<form:hidden path="orderCode" />
		<form:hidden path="orderSequence" />
		<form:hidden path="shipmentReturnId" />
		<form:hidden path="itemSequence" />

		<!-- //pop_title -->
		<div class="pop_con pop_conA">
			<h4>반품신청 상품 정보</h4>
			<div class="cart_list bd mg0">
				 <ul class="list pd0 del_info">
					<li>
						<div class="inner02">
							<div class="con_top pd0 cf">
								<div class="cart_img">
									<img src="${shop:loadImageBySrc(returnApply.orderItem.imageSrc, 'XS')}" alt="제품이미지">
								</div>
								<div class="cart_name">
									<p class="tit">${returnApply.orderItem.itemName}</p>
									${ shop:viewOptionText(returnApply.orderItem.options) }

									<c:forEach items="${returnApply.orderItem.additionItemList}" var="additionItem">
										추가구성품 : ${additionItem.itemName} ${additionItem.quantity}개 (+${op:numberFormat(additionItem.itemAmount)}원) <br />
									</c:forEach>

									${ shop:makeOrderGiftItemText(returnApply.orderItem.orderGiftItemList)}
								</div>
							</div>
							<div class="con_bot">
								<div class="cacul fr">
									<form:select path="applyQuantity" class="return-refund-info form-control select01">
				 						<c:forEach begin="1" end="${returnApply.orderItem.quantity - returnApply.orderItem.claimApplyQuantity}" step="1" var="quantity">
				 							<form:option value="${quantity}">${quantity}개</form:option>
				 						</c:forEach>
			 						</form:select>
								</div>
							</div>
						</div>
				 	</li>
				 </ul>
			</div>
			<!-- //cart_list -->
			<div class="single_input bd_table">
				<span class="del_tit t_gray">반품사유</span>
				<div class="select_area">
					<form:select path="claimReason" cssClass="return-refund-info cancel_reason">
						<c:forEach var="code" items="${claimReasons}" varStatus="i">
							<form:option value="${code.detail}" title="${code.label}">${code.label}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<div class="text_area etc_area">
					<form:hidden path="claimReasonText" />
			 		<form:textarea path="claimReasonDetail" cols="30" rows="6" placeholder="내용을 입력해주세요" />
				</div>
			</div>
			<!-- //single_input -->
			
			<h4 class="mt10">반송 송장 정보</h4>
			<div class="bd_table pd0">
				<ul class="del_info">
					<li>
						<span class="del_tit t_gray">화수요청구분</span>
						<ul class="radio_list">
							<li>
								<input id="request01" type="radio" name="returnShippingAskType" value="1" ${op:checked('1', returnApply.returnShippingAskType)} title="회수 요청 구분" />
								<label for="request01"><span><span></span></span>지정택배사 이용하기</label>
							</li>
							<li>
								<input id="request02" type="radio" name="returnShippingAskType" value="2" ${op:checked('2', returnApply.returnShippingAskType)} title="회수 요청 구분" />
								<label for="request02"><span><span></span></span>직접발송</label>
							</li>
						</ul>
					</li>
					<li class="return-shipping-number" ${returnApply.returnShippingAskType == '1' ? 'style="display:none"' : ''}>
						<span class="del_tit t_gray">택배사</span>
						<div class="input_area"> 
							<form:select path="returnShippingCompanyName">
 								<c:forEach items="${deliveryCompanyList}" var="deliveryCompany">
 									<form:option value="${deliveryCompany.deliveryCompanyName}">${deliveryCompany.deliveryCompanyName}</form:option>
								</c:forEach>
 							</form:select>
						</div>
					</li>
					<li class="return-shipping-number" ${returnApply.returnShippingAskType == '1' ? 'style="display:none"' : ''}>
						<span class="del_tit t_gray">반송송장번호</span>
						<form:input path="returnShippingNumber" title="반송 송장번호" />
					</li>
				</ul>
			</div>
			<!-- //bd_table -->
			
			<h4 class="mt10">반송지 정보</h4>
			<div class="bd_table pd0">
				<ul class="del_info">
					<li>
						<span class="del_tit t_gray">고객명</span>
						<span class="del_detail">
							${returnApply.returnReserveName}
		 					<form:hidden path="returnReserveName" />
						</span>
					</li>
					<li>
						<span class="del_tit t_gray star">휴대폰</span>
						<div class="num">
							<div class="in_td">
								<div class="input_area">
									<form:select path="returnReserveMobile1" title="휴대폰번호">
										<form:option value="" label="선택"></form:option>
										<form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
									</form:select>
								</div>
								<div class="in_td dash"></div>
								<div class="input_area">
									<form:input path="returnReserveMobile2" title="휴대폰" maxlength="4" />
								</div>
								<div class="in_td dash"></div>
								<div class="input_area">
									<form:input path="returnReserveMobile3" title="휴대폰" maxlength="4" />
								</div>
							</div>
						</div>
					</li>
					<li>
						<span class="del_tit t_gray">전화번호</span>
						<div class="num">
							<div class="in_td">
								<div class="input_area">
									<form:select path="returnReservePhone1" title="전화번호">
										<form:option value="-" label="선택"></form:option>
										<form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
									</form:select>
								</div>
								<div class="in_td dash"></div>
								<div class="input_area">
									<form:input path="returnReservePhone2" title="전화번호" maxlength="4" />
								</div>
								<div class="in_td dash"></div>
								<div class="input_area">
									<form:input path="returnReservePhone3" title="전화번호" maxlength="4" />
								</div>
							</div>
						</div>
					</li>
					<li class="chunk">
						<label class="del_tit t_gray star">배송지 주소</label>
						<div class="user_info">
							<div class="in_td">
								<form:hidden path="returnReserveSido" />
	 							<form:hidden path="returnReserveSigungu" />
	 							<form:hidden path="returnReserveEupmyeondong" />
	 							<form:input path="returnReserveZipcode" title="우편번호" readonly="true" />
							</div>
							<div class="in_td address">
								<button type="button" class="btn_st3 static" onclick="openDaumPostcode()">우편번호</button>
							</div>
						</div>
						<div class="input_area">
							<form:input path="returnReserveAddress" title="주소" readonly="true" />
						</div>
					</li>
					<li>
						<span class="del_tit t_gray star">상세주소</span>
						<form:input path="returnReserveAddress2" title="상세 주소" />
					</li>
				</ul>
			</div>
			<!-- //bd_table -->
			
			<h4 class="mt10">환불 정보 입력</h4>
			<div class="bd_table pd0">
				<ul class="del_info ">
					<li>
						<span class="del_tit t_gray">은행명</span>
						<div class="input_area">
							<select name="returnBankName">
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
						<input type="text" name="returnVirtualNo" maxlength="30" />
					</li>
				</ul>
			</div>
			<!-- //bd_table -->
			
			<div class="desc_list">
				<ul>
					<li><span>부분취소가 불가능한 결제방식(카드)</span>의 경우 위에 입력하신 계좌로 취소금액을 입금해드립니다.</li>
					<li>통장입금의 경우 <span>택배가 판매자에게 도착 후 2~3일 이후</span> 환불 신청하신 계좌로 입금됩니다.</li>
				</ul>
			</div>
			<!-- //desc_list -->
			
			<div class="btn_wrap">
				<button type="button" class="btn_st1 reset" onclick="location.href = '/m/mypage/order'">취소</button>
				<button type="submit" class="btn_st1 decision">신청</button>
			</div>
			<!-- //btn_wrap -->
		
		</div>
		<!-- //pop_con -->
	</form:form>
</div>


<page:javascript>
<!-- 다음 주소검색 -->
<daum:address-layer />

<script type="text/javascript" src="/content/modules/op.mypage.js"></script>
<script type="text/javascript" src="/content/modules/op.order.js"></script>

<script type="text/javascript">
$(function() {
	
	$('input[name="returnShippingAskType"]').on('click', function(){
		if ($(this).val() == '2') {
			$('.return-shipping-number').show();
		} else {
			$('.return-shipping-number').hide();
		}
	});
	
});

function openDaumPostcode() {
	var tagNames = {
		'newZipcode'			: 'returnReserveZipcode',
		'zipcode1' 				: 'returnReserveZipcode1',
		'zipcode2' 				: 'returnReserveZipcode2',
		'sido'					: 'returnReserveSido',
		'sigungu'				: 'returnReserveSigungu',
		'eupmyeondong'			: 'returnReserveEupmyeondong',
		'roadAddress'			: 'returnReserveAddress',
		'jibunAddressDetail' 	: 'returnReserveAddress2'		
	}
	openDaumAddress(tagNames);
}
</script>
</page:javascript>