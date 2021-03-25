<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<form:form modelAttribute="exchangeApply" name="exchangeApply" method="post" onsubmit="return Mypage.claimApplyAction('3')">
	
	<form:hidden path="orderCode" />
	<form:hidden path="orderSequence" />
	<form:hidden path="itemSequence" />
	<form:hidden path="shipmentReturnId" />
	
	<div class="popup_wrap">
		<h1 class="popup_title">교환신청</h1>
		<div class="popup_contents">
			<h2>교환 신청 상품 정보</h2>
			<div class="pop_table">
		 		<table cellpadding="0" cellspacing="0">
		 			<caption>교환 신청 상품 정보</caption>
		 			<colgroup>
		 				<col style="width:22%;">
		 				<col style="width:auto;">
		 				<col style="width:16%;">
		 				<col style="width:17%;">
		 			</colgroup>
		 			<thead>
	 					<tr>
		 					<th scope="row" colspan="2">상품명/옵션정보</th>
		 					<th scope="row">수량</th>
		 					<th scope="row">합계</th>
		 				</tr>
		 			</thead>
		 			<tbody> 
		 				<tr class="txt_c bd_gray">
		 					<td colspan="2">
								<div class="pop_item">
									<p class="photo"><img src="${shop:loadImageBySrc(exchangeApply.orderItem.imageSrc, 'XS')}" alt="item photo"></p>
									<div class="pop_item_info">
										<p class="item_name">${exchangeApply.orderItem.itemName}</p>
										${ shop:viewOptionText(exchangeApply.orderItem.options) }

										<c:set var="totalSaleAmount" value="${exchangeApply.orderItem.saleAmount}" />
										<c:forEach items="${exchangeApply.orderItem.additionItemList}" var="additionItem">
											<c:set var="totalSaleAmount" value="${totalSaleAmount + additionItem.saleAmount}" />

											추가구성품 : ${additionItem.itemName} ${additionItem.quantity}개 (+${op:numberFormat(additionItem.itemAmount)}원) <br />
										</c:forEach>

										${ shop:makeOrderGiftItemText(exchangeApply.orderItem.orderGiftItemList)}
									</div>
								</div>
		 					</td>
		 					<td>
		 						<form:select path="applyQuantity" class="return-refund-info form-control select01">
			 						<c:forEach begin="1" end="${exchangeApply.orderItem.quantity - exchangeApply.orderItem.claimApplyQuantity}" step="1" var="quantity">
			 							<form:option value="${quantity}">${quantity}개</form:option>
			 						</c:forEach>
		 						</form:select>
		 					</td>
		 					<td>
		 						<p><span>${ op:numberFormat(totalSaleAmount) }</span>원</p>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th scope="col">교환 사유</th>
		 					<td colspan="3" class="b_none">
		 						<form:select path="claimReason" cssClass="return-refund-info form-control select02">
	 								<c:forEach var="code" items="${claimReasons}" varStatus="i">
	 									<form:option value="${code.detail}" title="${code.label}">${code.label}</form:option>
	 								</c:forEach>
	 							</form:select> 
	 							<form:hidden path="claimReasonText" />
			 					<form:input path="claimReasonDetail" class="select_input" />
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
			</div>
			
			<h2 class="mt20">반송 송장 정보</h2>
			<div class="pop_table"> 
		 		<table>
		 			<caption>반송 송장 정보</caption>
		 			<colgroup>
		 				<col style="width:22%;">
		 				<col style="width:auto;">
		 			</colgroup>
		 			<tbody> 
		 				<tr>
		 					<th scope="row">회수 요청 구분</th>
		 					<td>
		 						<div class="radio_area">
		 							<form:radiobutton path="exchangeShippingAskType" value="1" label=" 지정택배사 이용하기" title="회수 요청 구분" />&nbsp;&nbsp;
		 							<form:radiobutton path="exchangeShippingAskType" value="2" label=" 직접발송" title="회수 요청 구분" />
								</div>
		 					</td>
		 				</tr>
		 				<tr id="exchange-shipping-number" ${exchangeApply.exchangeShippingAskType == '1' ? 'style="display:none"' : ''}>
		 					<th scope="row">반송 송장정보</th>
		 					<td class="b_none">
	 							<form:select path="exchangeShippingCompanyName" cssClass="form-control select02">
	 								<c:forEach items="${deliveryCompanyList}" var="deliveryCompany">
	 									<form:option value="${deliveryCompany.deliveryCompanyName}">${deliveryCompany.deliveryCompanyName}</form:option>
									</c:forEach>
	 							</form:select>
	 							<form:input path="exchangeShippingNumber" title="반송 송장번호" class="select_input" />
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
			</div>
			
			<h2 class="mt20">반송지 정보</h2>
			<div class="pop_table"> 
		 		<table>
		 			<caption>반송지 정보</caption>
		 			<colgroup>
		 				<col style="width:22%;">
		 				<col style="width:28%;">
		 				<col style="width:22%;">
		 				<col style="width:28%;">
		 			</colgroup>
		 			<tbody> 
		 				<tr>
		 					<th scope="col">고객명</th>
		 					<td colspan="3">
		 						${exchangeApply.exchangeReceiveName}
		 						<form:hidden path="exchangeReceiveName" />
		 					</td>
		 				</tr>
		 				<tr>
		 					<th scope="col">전화번호</th>
		 					<td><form:input path="exchangeReceivePhone" title="전화번호" /></td>
		 					<th scope="col">휴대폰</th>
		 					<td><form:input path="exchangeReceiveMobile" title="휴대폰" /></td>
		 				</tr>
						<tr>
		 					<th scope="col">배송지 주소</th>
		 					<td colspan="3">
			 					<div> 
		 							<div class="input_wrap col-w-7">
		 								<form:hidden path="exchangeReceiveSido" />
			 							<form:hidden path="exchangeReceiveSigungu" />
			 							<form:hidden path="exchangeReceiveEupmyeondong" />
			 							<form:input path="exchangeReceiveZipcode" title="우편번호" readonly="true" /> 
		 							</div>
		 							<div class="input_wrap"><button type="button" onclick="openDaumPostcodeForExchange()" class="btn btn-ms btn-default">검색</button></div>  
		 							<div class="input_wrap mt8 col-w-0">
		 								<form:input path="exchangeReceiveAddress" title="주소" readonly="true" class="full" />
		 							</div>
		 							<div class="input_wrap mt8 col-w-0">
		 								<form:input path="exchangeReceiveAddress2" title="상세 주소" class="full" />
		 							</div>
		 						</div>
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
			</div>
				 
		</div><!--//popup_contents E-->
		
		<div class="btn_wrap"> 
			<button type="submit" class="btn btn-success btn-lg" title="신청">신청</button> 
			<button type="button" class="btn btn-lg btn-default" onclick="Shop.closeOrderLayer('claim_apply')"  title="취소">취소</button>
		</div>
		<a href="javascript:Shop.closeOrderLayer('claim_apply')" class="popup_close">창 닫기</a>
	</div>
</form:form>

<script type="text/javascript">
$(function() {
	$('input[name="exchangeShippingAskType"]').on('click', function(){
		if ($(this).val() == '2') {
			$('#exchange-shipping-number').show();
		} else {
			$('#exchange-shipping-number').hide();
		}
	});
	
	$('#exchangeApply').validator(function() {
		var $val = $('select[name="claimReason"] option:selected').attr('title');
		$('input[name=claimReasonText]').val($val);
		
		if($('input[name=claimReasonDetail]').val() == '' || $('input[name=claimReasonDetail]').val() == null) {
			$('input[name=claimReasonDetail]').val(' ');
		}
	});
});
</script>