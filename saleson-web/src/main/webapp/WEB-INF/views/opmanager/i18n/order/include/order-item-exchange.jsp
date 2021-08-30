<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<style type="text/css">
.inDiv {
	width:100%; 
	margin:2px 0; 
	padding:0; 
	float:left;
}
</style>
<c:if test="${not empty exchangeHistorys}">
	<h3>교환 내역 (거절, 완료, 배송대기)</h3>
	<table class="inner-table">
		<caption>${op:message('M00059')}</caption>
		<!-- 주문정보 -->
		<colgroup>
			<col />
			<col style="width: 120px" />
			<col style="width: 120px" />
			<col style="width: 100px" />
			<col style="width: 150px" />
			<col style="width: 70px" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col" class="none_left">상품정보</th>
				<th scope="col" class="none_left">구분</th>
				<th scope="col" class="none_left">클레임수량</th>
				<th scope="col" class="none_left">상태</th>
				<th scope="col" class="none_left">신청일</th>
				<th scope="col" class="none_left"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${exchangeHistorys}" var="history">
				<tr>
					<td>
						[${history.orderItem.itemUserCode}] ${history.orderItem.itemName}
						${ shop:viewOptionText(history.orderItem.options)}

						<c:set var="additionClaimCode" value="" />
						<c:forEach items="${history.orderItem.additionItemList}" var="addition" varStatus="i">
							<c:if test="${history.orderItem.itemId == addition.parentItemId && history.orderItem.options == addition.parentItemOptions}">

								<c:set var="additionClaimCode" value="${additionClaimCode}${addition.claimCode}"/>

								<c:if test="${i.first}">
									<c:set var="additionClaimCode" value=",${additionClaimCode}"/>
								</c:if>

								<c:if test="${not i.last}">
									<c:set var="additionClaimCode" value="${additionClaimCode},"/>
								</c:if>

								추가구성품 : ${addition.itemName} ${addition.quantity}개 (+${op:numberFormat(addition.itemAmount)}원) <br />
							</c:if>
						</c:forEach>

						${ shop:viewOrderGiftItemList(history.orderItem.orderGiftItemList) }

						<c:if test="${history.claimStatus == '99' && not empty history.exchangeRefusalReasonText}">
							<p style="color:red">거절 사유 : ${history.exchangeRefusalReasonText}</p>
						</c:if>
					</td>
					<td class="text-center">
						<c:choose>
							<c:when test="${shop:sellerId() == history.orderItem.sellerId}">자사</c:when>
							<c:otherwise>
								<span class="glyphicon glyphicon-user"></span>${history.orderItem.sellerName}
							</c:otherwise> 
						</c:choose>
					</td>
					<td class="text-right">${op:numberFormat(history.claimApplyQuantity)}개</td>
					<td class="text-center">${history.claimStatusLabel}</td>
					<td class="text-center">${op:datetime(history.createdDate)}</td>
					<td class="text-center"><a href="javascript:Common.popup('${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/claim/exchange-log/${history.claimCode}${additionClaimCode}', 'exchange_log', 930, 400, 1)" class="btn btn-gradient btn-xs">상세</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<form id="exchangeForm">
	<h3 class="mt10">교환 처리</h3>
	<input type="hidden" name="orderCode" value="${order.orderCode}" />
	<input type="hidden" name="orderSequence" value="${order.orderSequence}" />
	<table class="inner-table">
		<caption>${op:message('M00059')}</caption>
		<!-- 주문정보 -->
		<colgroup>
			<col style="width: 30px" />
			<col style="width: 80px" />
			<col />
			<col style="width: 120px" />
			<col style="width: 120px" />
			<col style="width: 120px" />
			<col style="width: 120px" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col" class="none_left"><input type="checkbox" id="exchange_all" title="체크박스" /></th>
				<th scope="col" class="none_left">이미지</th>
				<th scope="col" class="none_left">상품정보</th>
				<th scope="col" class="none_left">구분</th>
				<th scope="col" class="none_left">클레임수량</th>
				<th scope="col" class="none_left">상태</th>
				<th scope="col" class="none_left">신청일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${activeExchanges}" var="apply" varStatus="groupIndex">
				<c:set var="orderItem" value="${apply.orderItem}" />
				<c:set var="flag" value="${orderItem.additionItemFlag}" />

				<tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>
					<td rowspan="2">
						<c:if test="${shop:sellerId() == apply.shipmentReturnSellerId || requestContext.opmanagerPage}">
							<input type="checkbox" name="exchangeIds" value="${apply.claimCode}"
								   data-item-id="${orderItem.itemId}" data-options="${orderItem.options}" data-claim-code="${apply.claimCode}" />
							<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].itemSequence" value="${orderItem.itemSequence}" />
							<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].claimCode" value="${apply.claimCode}" />
							<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].orderSequence" value="${orderItem.orderSequence}" />
							<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].orderCode" value="${orderItem.orderCode}" />
							<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].claimApplyQuantity" value="${apply.claimApplyQuantity}" />
						</c:if>
					</td>
					<td>
						<img src="${shop:loadImageBySrc(orderItem.imageSrc,'XS')}" alt="${orderItem.itemName}" width="100%"/>
					</td>
					<td>
						[${orderItem.itemUserCode}] ${orderItem.itemName}
						${ shop:viewOptionText(orderItem.options) }
						${shop:viewAdditionOrderItemList(orderItem.additionItemList)}
						${ shop:viewOrderGiftItemList(orderItem.orderGiftItemList) }
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
						${op:numberFormat(apply.claimApplyQuantity)}개
					</td>
					<td class="text-center"> 
						${apply.claimStatusLabel}
					</td>
					<td class="text-center"> 
						${op:datetime(apply.createdDate)}
					</td>
				</tr>
				<c:choose>
					<c:when test="${shop:sellerId() == apply.shipmentReturnSellerId || requestContext.opmanagerPage}">
						<tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>
							<td colspan="8"> 
								<table class="inner-table">
									<colgroup>
										<col style="width:10%" />
										<col style="width:40%"/>
										<col style="width:10%" />
										<col style="width:40%"/>
									</colgroup>
									<tbody>
										<tr>
											<th>신청사유</th>
											<td>
												<div style="float:left; width: 100%;">
													<div class="inDiv">
														<c:choose>
															<c:when test="${apply.claimApplySubject == '01'}">[구매자 신청] </c:when>
															<c:otherwise>[판매자 신청] </c:otherwise>
														</c:choose>
													</div>
													<div class="inDiv">
														<label style="width:100px"><input type="radio" name="exchangeApplyMap[${apply.claimCode}].exchangeReason" value="2" ${op:checked('2', apply.exchangeReason)} class="exchange-reason" data-claim-code="${apply.claimCode}" /> 고객 사유</label>
														<select name="exchangeApplyMap[${apply.claimCode}].exchangeReasonText" class="exchange-reason-text">
															<c:forEach var="code" items="${exchangeClaimReasons}" varStatus="i">
																<option value="${code.label}" <c:if test="${code.label eq apply.exchangeReasonText}">selected</c:if>>${code.label}</option>
															</c:forEach>
														</select>
													</div>
													<div class="inDiv">
														<label style="width:100px"><input type="radio" name="exchangeApplyMap[${apply.claimCode}].exchangeReason" value="1" ${op:checked('1', apply.exchangeReason)} class="exchange-reason" data-claim-code="${apply.claimCode}" /> 판매자 사유</label>
														<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeReasonDetail" value="${apply.exchangeReasonDetail}" style="width:70%" maxlength="80" class="exchange-reason-detail" />
													</div>
												</div>
											</td>
											<th>회수 송장 정보</th>
											<td>
												<p>
													<label><input type="radio" name="exchangeApplyMap[${apply.claimCode}].exchangeShippingAskType" value="1" ${op:checked('1', apply.exchangeShippingAskType)} data-claim-code="${apply.claimCode}" class="exchange-shipping-ask-type" /> 지정택배사</label>
													<label><input type="radio" name="exchangeApplyMap[${apply.claimCode}].exchangeShippingAskType" value="2" ${op:checked('2', apply.exchangeShippingAskType)} data-claim-code="${apply.claimCode}" class="exchange-shipping-ask-type" /> 직접발송</label>
												</p>
												<select name="exchangeApplyMap[${apply.claimCode}].exchangeShippingCompanyName" class="exchange-shipping-company-name">
													<option value="">-선택-</option>
					 								<c:forEach items="${deliveryCompanyList}" var="deliveryCompany">
					 									<option value="${deliveryCompany.deliveryCompanyName}" ${op:selected(apply.exchangeShippingCompanyName, deliveryCompany.deliveryCompanyName)}>${deliveryCompany.deliveryCompanyName}</option>
													</c:forEach>
					 							</select>
												<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeShippingNumber" value="${apply.exchangeShippingNumber}" style="width:70%" maxlength="30" class="exchange-shipping-number" />
											</td>
										</tr>
										<tr>
											<th>회수 요청지</th>
											<td>
												<p>
													<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveSido" value="${apply.exchangeReceiveSido}" class="exchange-receive-sido" />
													<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveSigungu" value="${apply.exchangeReceiveSigungu}" class="exchange-receive-sigungu" />
													<input type="hidden" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveEupmyeondong" value="${apply.exchangeReceiveEupmyeondong}" class="exchange-receive-eupmyeondong" />
													
													<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveName" placeholder="이름" value="${apply.exchangeReceiveName}" maxlength="30" class="exchange-receive-name" />
													<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveZipcode" placeholder="우편번호" value="${apply.exchangeReceiveZipcode}" class="required exchange-receive-zipcode" maxlength="7" readonly="readonly" />
													<a href="javascript:;" onclick="openDaumPostcodeForExchange('${apply.claimCode}')" class="btn btn-gradient btn-xs">우편번호</a><br/>
													<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveAddress" title="주소" maxlength="100" style="width:100%" class="required exchange-receive-address" readonly="readonly" value="${apply.exchangeReceiveAddress}" /><br/>
													<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveAddress2"  style="width:100%" maxlength="100" title="상세주소" value="${apply.exchangeReceiveAddress2}" class="exchange-receive-address2" />
												</p>
												<p>
													<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeReceivePhone" class="required exchange-receive-phone" title="전화번호" placeholder="전화번호" value="${apply.exchangeReceivePhone}" maxlength="14" />
													<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeReceiveMobile" class="required exchange-receive-mobile" title="휴대전화번호" placeholder="휴대전화번호" value="${apply.exchangeReceiveMobile}" maxlength="14" />
												</p>
											</td>
											<th>반송지 주소</th>
											<td>
												<c:choose>
													<c:when test="${empty apply.shipmentReturn.addressName}">반송지 정보가 없습니다.</c:when>
													<c:otherwise>
														[${apply.shipmentReturn.addressName}] <br/>
														(${apply.shipmentReturn.zipcode}) ${apply.shipmentReturn.address} ${apply.shipmentReturn.addressDetail}	
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<th class="text-center">처리구분</th>
											<td colspan="3">
												<p>
													<label><input type="radio" name="exchangeApplyMap[${apply.claimCode}].claimStatus" value="99" ${op:checked('99', apply.claimStatus)} onclick="exchangeCheckedDisplay('${apply.claimCode}','99')" class="exchange-claim-status" data-claim-code="${apply.claimCode}" /> 교환 거절</label>
													<label><input type="radio" name="exchangeApplyMap[${apply.claimCode}].claimStatus" value="02" ${op:checked('02', apply.claimStatus)} onclick="exchangeCheckedDisplay('${apply.claimCode}','02')" class="exchange-claim-status" data-claim-code="${apply.claimCode}" /> 교환 보류</label>
													<label><input type="radio" name="exchangeApplyMap[${apply.claimCode}].claimStatus" value="10" ${op:checked('10', apply.claimStatus)} onclick="exchangeCheckedDisplay('${apply.claimCode}','10')" class="exchange-claim-status" data-claim-code="${apply.claimCode}" /> 회수중</label>
													<label><input type="radio" name="exchangeApplyMap[${apply.claimCode}].claimStatus" value="11" ${op:checked('11', apply.claimStatus)} onclick="exchangeCheckedDisplay('${apply.claimCode}','11')" class="exchange-claim-status" data-claim-code="${apply.claimCode}" /> 회수 완료</label>
													<label><input type="radio" name="exchangeApplyMap[${apply.claimCode}].claimStatus" value="03" ${op:checked('03', apply.claimStatus)} onclick="exchangeCheckedDisplay('${apply.claimCode}','03')" class="exchange-claim-status" data-claim-code="${apply.claimCode}" /> 교환상품 발송</label>
												<p>
												<p id="redText_${apply.claimCode}" style="margin-top:5px;color:red"></p>
											</td>
										</tr>
										
										<tr id="exchangeClaimInfo1_${apply.claimCode}" style="display:none">
											<th class="text-center">거절 사유</th>
											<td colspan="3">
												<input type="text" class="form-block exchange-refusal-reason-text" maxlength="80" name="exchangeApplyMap[${apply.claimCode}].exchangeRefusalReasonText" value="${apply.exchangeRefusalReasonText}" maxlength="100" />
												<p style="color:red">해당 거절사유는 구매 고객에게 노출되는 내용입니다.</p>
											</td>
										</tr> 
										<tr id="exchangeClaimInfo2_${apply.claimCode}" style="display:none">
											<th class="text-center">재발송 정보</th>
											<td colspan="3">
												<select name="exchangeApplyMap[${apply.claimCode}].exchangeDeliveryCompanyId" class="exchange-delivery-company-id">
													<option value="0">-선택-</option>
													<c:forEach items="${deliveryCompanyList}" var="deliveryCompany">
														<option value="${deliveryCompany.deliveryCompanyId}" ${op:selected(deliveryCompany.deliveryCompanyName, orderItem.deliveryCompanyId)}>${deliveryCompany.deliveryCompanyName}</option>
													</c:forEach>
												</select>
												<input type="text" name="exchangeApplyMap[${apply.claimCode}].exchangeDeliveryNumber" value="${apply.exchangeDeliveryNumber}" maxlength="30" cssClass="required" title="송장번호" class="exchange-delivery-number" />
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7"> 
								<table class="inner-table">
									<colgroup>
										<col style="width:10%" />
										<col style="width:40%"/>
										<col style="width:10%" />
										<col style="width:40%"/>
									</colgroup>
									<tbody>
										<tr>
											<th>신청사유</th>
											<td>
												<p>
													<c:choose>
														<c:when test="${apply.claimApplySubject == '01'}">[구매자 신청] </c:when>
														<c:otherwise>[판매자 신청] </c:otherwise>
													</c:choose>
													
													<c:choose>
														<c:when test="${apply.exchangeReason == '2'}">고객 사유</c:when>
														<c:when test="${apply.exchangeReason == '1'}">판매자 사유</c:when>
														<c:otherwise>-</c:otherwise>
													</c:choose>
												</p>
												<p class="mt5">${apply.exchangeReasonText}</p>
												<p>${apply.exchangeReasonDetail}</p>
											</td>
											<th>반품 송장 정보</th>
											<td>
												${apply.exchangeShippingNumber}
											</td>
										</tr>
										<tr>
											<th>반송지 주소</th>
											<td colspan="3">
												<c:choose>
													<c:when test="${empty apply.shipmentReturn.addressName}">반송지 정보가 없습니다.</c:when>
													<c:otherwise>
														[${apply.shipmentReturn.addressName}] <br/>
														(${apply.shipmentReturn.zipcode})&nbsp;&nbsp;&nbsp;${apply.shipmentReturn.address}&nbsp;&nbsp;&nbsp;${apply.shipmentReturn.addressDetail}	
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<th class="text-center">처리구분</th>
											<td colspan="3">
												${apply.claimStatusLabel}
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>

	<c:choose>
		<c:when test="${empty activeExchanges}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>	
		</c:when>
		<c:otherwise>
			<div class="popup_btns" style="clear:both">
				<button type="submit" class="btn btn-active">교환 주문 처리</button> 
			</div>
		</c:otherwise>
	</c:choose>
</form>
<script type="text/javascript">
var emessage1 = "!!처리할 항목을 선택하세요";
var emessage2 = "!!교환신청을 거절합니다. 신청은 철회되며 사용자화면에는 '교환거절'로 표기됩니다.<br/>!!거절사유를 입력하세요";
var emessage3 = "!!교환신청을 보류합니다. 차후에 다시 처리할 수 있습니다.<br/>!!사용자화면에는 '교환처리중'으로 표기됩니다.";
var emessage4 = "!!교환신청 후 상품을 발송하여 배송되는 중입니다.<br/>!!사용자화면에는 '교환처리중'로 표기됩니다.";
var emessage5 = "!!교환 정보가 ERP와 연동됩니다.<br/>!!교환신청 후 고객이 발송한 상품을 확인하였습니다..<br/>!!사용자화면에는 '교환처리중'로 표기됩니다.";
var emessage6 = "!!배송정보를 입력하세요.<br/>!!사용자화면에는 '교환배송중'로 표기됩니다.";

$(function() {
	$.each($("input[name$='].claimCode']"), function() {
		var val = $(this).val();
		var checkedVal = $("input:radio[name='exchangeApplyMap["+val+"].claimStatus']:checked").val();
		
		if(checkedVal == "99"){  					// 교환거절
			$("#redText_"+val).html(emessage2);
		}else if(checkedVal == "02"){				// 교환보류
			$("#redText_"+val).html(emessage3);
		}else if(checkedVal == "10"){				// 회수중
			$("#redText_"+val).html(emessage4);
		}else if(checkedVal == "11"){				// 회수완료
			$("#redText_"+val).html(emessage5);
		}else if(checkedVal == "03"){				// 교환승인
			$("#redText_"+val).html(emessage6);
		}else{										
			$("#redText_"+val).html(emessage1);
			/* var deliveryCompanyId = $("select[name='exchangeApplyMap["+val+"].exchangeDeliveryCompanyId']").val();
			var deliveryNumber = $("input[name='exchangeApplyMap["+val+"].exchangeDeliveryNumber']").val();
			if(deliveryCompanyId !="" && deliveryNumber != ""){
				$("input:radio[name='exchangeApplyMap["+val+"].claimStatus'][value='10']").click();
			} */
		}
	});

	$('tr.parent :radio').on('change', function() {
		var parentClass = $(this).closest('tr.parent').data('class');
		var className = $(this).attr('class');
		var value = $(this).val();

		var $selector = $('tr.child[data-class="' + parentClass + '"] :radio.' + className + '[value=' +value + ']');
		$selector.prop('checked', $(this).prop('checked'));

		if (className == 'exchange-claim-status') {
			$.each($selector, function () {
				exchangeCheckedDisplay($(this).data('claim-code'), value);
			});
		}
	});


	$('tr.parent input[type=text]').on('input', function() {
		var parentClass = $(this).closest('tr.parent').data('class');
		var className = $(this).attr('class').replace('form-block ', '').replace('required ', '');
		var value = $(this).val();

		$('tr.child[data-class="' + parentClass + '"] input[type=text].' + className).val(value);
		$('tr.child[data-class="' + parentClass + '"] input[type=hidden].exchange-receive-sido').val($('tr.parent[data-class="' + parentClass + '"] input[type=hidden].exchange-receive-sido').val());
		$('tr.child[data-class="' + parentClass + '"] input[type=hidden].exchange-receive-sigungu').val($('tr.parent[data-class="' + parentClass + '"] input[type=hidden].exchange-receive-sigungu').val());
		$('tr.child[data-class="' + parentClass + '"] input[type=hidden].exchange-receive-eupmyeondong').val($('tr.parent[data-class="' + parentClass + '"] input[type=hidden].exchange-receive-eupmyeondong').val());
	});
});

(function ($) {
	var originalVal = $.fn.val;
	$.fn.val = function (value) {
		var res = originalVal.apply(this, arguments);

		if (this.is('input:text') && arguments.length >= 1) {
			this.trigger('input');
		}
		return res;
	};
})(jQuery);


function exchangeCheckedDisplay(claimCode,value){
	$("#exchangeClaimInfo1_"+claimCode).hide();
	$("#exchangeClaimInfo2_"+claimCode).hide();
	
	if(value == "99"){  					// 교환거절
		$("#redText_"+claimCode).html(emessage2);
		$("#exchangeClaimInfo1_"+claimCode).show();
	}else if(value == "02"){				// 교환보류
		$("#redText_"+claimCode).html(emessage3);
	}else if(value == "10"){				// 회수중
		$("#redText_"+claimCode).html(emessage4);
	}else if(value == "11"){				// 회수완료
		$("#redText_"+claimCode).html(emessage5);
	}else if(value == "03"){				// 교환승인
		$("#redText_"+claimCode).html(emessage6);
		$("#exchangeClaimInfo2_"+claimCode).show();
	}
}
</script>