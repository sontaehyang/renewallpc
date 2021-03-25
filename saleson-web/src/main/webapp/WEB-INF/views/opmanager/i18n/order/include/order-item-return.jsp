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
<c:if test="${not empty returnHistorys}">
	<h3>반품 내역 (거절, 완료, 환불대기)</h3>
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
			<c:forEach items="${returnHistorys}" var="history">
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
						
						<c:if test="${history.claimStatus == '99' && not empty history.returnRefusalReasonText}">
							<p style="color:red">거절 사유 : ${history.returnRefusalReasonText}</p>
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
					<td class="text-center"><a href="javascript:Common.popup('/opmanager/order/claim/return-log/${history.claimCode}${additionClaimCode}', 'return_log', 930, 400, 1)" class="btn btn-gradient btn-xs">상세</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<form id="returnForm">
	<h3 class="mt10">반품 처리</h3>
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
			<col style="width: 100px" />
			<col style="width: 120px" />
			<col style="width: 120px" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col" class="none_left"><input type="checkbox" id="return_all" title="체크박스" /></th>
				<th scope="col" class="none_left">이미지</th>
				<th scope="col" class="none_left">상품정보</th>
				<th scope="col" class="none_left">구분</th>
				<th scope="col" class="none_left">클레임수량</th>
				<th scope="col" class="none_left">환불금액(상품)</th>
				<th scope="col" class="none_left">상태</th>
				<th scope="col" class="none_left">신청일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${activeReturns}" var="apply" varStatus="groupIndex">
				<c:set var="orderItem" value="${apply.orderItem}" />
				<c:set var="flag" value="${orderItem.additionItemFlag}" />

				<c:set var="totalClaimApplyAmount" value="${apply.claimApplyAmount}" />
				<c:forEach items="${orderItem.additionItemList}" var="addition">
					<c:set var="totalClaimApplyAmount">${totalClaimApplyAmount + addition.returnApply.claimApplyAmount}</c:set>
				</c:forEach>

				<tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>					<td rowspan="2">
						<c:if test="${shop:sellerId() == apply.shipmentReturnSellerId || requestContext.opmanagerPage}">
							<input type="checkbox" name="returnIds" value="${apply.claimCode}" />
							<input type="hidden" name="returnApplyMap[${apply.claimCode}].itemSequence" value="${orderItem.itemSequence}" />
							<input type="hidden" name="returnApplyMap[${apply.claimCode}].claimCode" value="${apply.claimCode}" />
							<input type="hidden" name="returnApplyMap[${apply.claimCode}].orderSequence" value="${orderItem.orderSequence}" />
							<input type="hidden" name="returnApplyMap[${apply.claimCode}].orderCode" value="${orderItem.orderCode}" />
							<input type="hidden" name="returnApplyMap[${apply.claimCode}].shipmentReturnSellerId" value="${apply.shipmentReturnSellerId}" />
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
					<td class="text-right">
						${op:numberFormat(totalClaimApplyAmount)}원
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
						<tr class="${flag == 'Y' ? 'child' : 'parent'}" data-class="group-${flag == 'Y' ? orderItem.parentItemId : orderItem.itemId}-${flag == 'Y' ? orderItem.parentItemOptions : orderItem.options}" ${flag == 'Y' ? 'style="display:none"' : ''}>							<td colspan="8">
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
														<label style="width:100px"><input type="radio" name="returnApplyMap[${apply.claimCode}].returnReason" value="2" ${op:checked('2', apply.returnReason)} class="return-reason" data-claim-code="${apply.claimCode}" /> 고객 사유</label>
														<select name="returnApplyMap[${apply.claimCode}].returnReasonText" class="return-reason-text">
															<c:forEach var="code" items="${returnClaimReasons}" varStatus="i">
																<option value="${code.label}" <c:if test="${code.label eq apply.returnReasonText}">selected</c:if>>${code.label}</option>
															</c:forEach>
														</select>
													</div>
													<div class="inDiv">
														<label style="width:100px"><input type="radio" name="returnApplyMap[${apply.claimCode}].returnReason" value="1" ${op:checked('1', apply.returnReason)} class="return-reason" data-claim-code="${apply.claimCode}" /> 판매자 사유</label>
														<input type="text" name="returnApplyMap[${apply.claimCode}].returnReasonDetail" maxlength="80" value="${apply.returnReasonDetail}" style="width:70%" class="return-reason-detail" />
													</div>
												</div>
											</td>
											<th>회수 송장 정보</th>
											<td>
												<p>
													<label><input type="radio" name="returnApplyMap[${apply.claimCode}].returnShippingAskType" value="1" ${op:checked('1', apply.returnShippingAskType)} data-claim-code="${apply.claimCode}" class="return-shipping-ask-type" /> 지정택배사</label>
													<label><input type="radio" name="returnApplyMap[${apply.claimCode}].returnShippingAskType" value="2" ${op:checked('2', apply.returnShippingAskType)} data-claim-code="${apply.claimCode}" class="return-shipping-ask-type" /> 직접발송</label>
												</p>
												<p>
													<select name="returnApplyMap[${apply.claimCode}].returnShippingCompanyName" class="return-shipping-companyName">
														<option value="">-선택-</option>
						 								<c:forEach items="${deliveryCompanyList}" var="deliveryCompany">
						 									<option value="${deliveryCompany.deliveryCompanyName}" ${op:selected(apply.returnShippingCompanyName, deliveryCompany.deliveryCompanyName)}>${deliveryCompany.deliveryCompanyName}</option>
														</c:forEach>
						 							</select>
													<input type="text" name="returnApplyMap[${apply.claimCode}].returnShippingNumber" value="${apply.returnShippingNumber}" style="width:70%" maxlength="30" class="return-shipping-number" />
												</p>
											</td>
										</tr>
										<tr>
											<th>회수 요청지</th>
											<td>
												<p>
													<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveSido" value="${apply.returnReserveSido}" class="return-reserve-sido" />
													<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveSigungu" value="${apply.returnReserveSigungu}" class="return-reserve-sigungu" />
													<input type="hidden" name="returnApplyMap[${apply.claimCode}].returnReserveEupmyeondong" value="${apply.returnReserveEupmyeondong}" class="return-reserve-eupmyeondong" />
													
													<input type="text" name="returnApplyMap[${apply.claimCode}].returnReserveName" placeholder="이름" value="${apply.returnReserveName}" class="return-reserve-name" />
													<input type="text" name="returnApplyMap[${apply.claimCode}].returnReserveZipcode" placeholder="우편번호" value="${apply.returnReserveZipcode}" readonly="readonly" class="return-reserve-zipcode" />
													<a href="javascript:;" onclick="openDaumPostcodeForReturn('${apply.claimCode}')" class="btn btn-gradient btn-xs">우편번호</a><br/>
													<input type="text" name="returnApplyMap[${apply.claimCode}].returnReserveAddress" title="주소" maxlength="7" style="width:100%" class="required return-reserve-address" readonly="readonly" value="${apply.returnReserveAddress}" /><br/>
													<input type="text" name="returnApplyMap[${apply.claimCode}].returnReserveAddress2"  style="width:100%" maxlength="100" title="상세주소" value="${apply.returnReserveAddress2}" class="return-reserve-address2" />
												</p>
												<p>
													<%--<input type="text" name="returnApplyMap[${apply.claimCode}].returnReservePhone" class="required" title="전화번호" placeholder="전화번호" value="${apply.returnReservePhone}" maxlength="14" />--%>
													<input type="text" name="returnApplyMap[${apply.claimCode}].returnReserveMobile" class="required" title="휴대전화번호" placeholder="휴대전화번호" value="${apply.returnReserveMobile}" maxlength="14" class="return-reserve-mobile" />
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
													<label><input type="radio" name="returnApplyMap[${apply.claimCode}].claimStatus" value="99" ${op:checked('99', apply.claimStatus)} onclick="returnCheckedDisplay('${apply.claimCode}','99')" class="return-claim-status" data-claim-code="${apply.claimCode}" /> 반품 거절</label>
													<label><input type="radio" name="returnApplyMap[${apply.claimCode}].claimStatus" value="02" ${op:checked('02', apply.claimStatus)} onclick="returnCheckedDisplay('${apply.claimCode}','02')" class="return-claim-status" data-claim-code="${apply.claimCode}" /> 반품 보류</label>
													<label><input type="radio" name="returnApplyMap[${apply.claimCode}].claimStatus" value="10" ${op:checked('10', apply.claimStatus)} onclick="returnCheckedDisplay('${apply.claimCode}','10')" class="return-claim-status" data-claim-code="${apply.claimCode}" /> 회수중</label>
													<label><input type="radio" name="returnApplyMap[${apply.claimCode}].claimStatus" value="11" ${op:checked('11', apply.claimStatus)} onclick="returnCheckedDisplay('${apply.claimCode}','11')" class="return-claim-status" data-claim-code="${apply.claimCode}" /> 회수 완료</label>
													<label><input type="radio" name="returnApplyMap[${apply.claimCode}].claimStatus" value="03" ${op:checked('03', apply.claimStatus)} onclick="returnCheckedDisplay('${apply.claimCode}','03')" class="return-claim-status" data-claim-code="${apply.claimCode}" /> 반품 승인</label>
												</p>
												<p id="redText_${apply.claimCode}" style="margin-top:5px;color:red"></p>
											</td>
										</tr>
										
										<tr id="returnClaimInfo1_${apply.claimCode}" style="display:none">
											<th class="text-center">거절 사유</th>
											<td colspan="3">
												<input type="text" class="form-block return-refusal-reason-text" maxlength="80" name="returnApplyMap[${apply.claimCode}].returnRefusalReasonText" value="${apply.returnRefusalReasonText}"  />
												<p style="color:red">해당 거절사유는 구매 고객에게 노출되는 내용입니다.</p>
											</td>
										</tr>
										
										<tr id="returnClaimInfo2_${apply.claimCode}" style="display:none">
											<th class="text-center">회수 비용</th>
											<td colspan="3">
												<input type="text" class="form-block _number collection-shipping-amount" maxlength="100" name="returnApplyMap[${apply.claimCode}].collectionShippingAmount" ${apply.returnReason == '1' ? 'disabled="disabled"' : ''} value="${apply.collectionShippingAmount}" />
											</td>
										</tr>
										
									</tbody>
								</table>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
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
												<p>
													<c:choose>
														<c:when test="${apply.claimApplySubject == '01'}">[구매자 신청] </c:when>
														<c:otherwise>[판매자 신청] </c:otherwise>
													</c:choose>
													
													<c:choose>
														<c:when test="${apply.returnReason == '2'}">고객 사유</c:when>
														<c:when test="${apply.returnReason == '1'}">판매자 사유</c:when>
														<c:otherwise>-</c:otherwise>
													</c:choose>
												</p>
												<p class="mt5">${apply.returnReasonText}</p>
												<p>${apply.returnReasonDetail}</p>
											</td>
											<th>반품 송장 정보</th>
											<td>
												${apply.returnShippingNumber}
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
		<c:when test="${empty activeReturns}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>	
		</c:when>
		<c:otherwise>
			<div class="popup_btns" style="clear:both">
				<button type="submit" class="btn btn-active">반품 주문 처리</button> 
			</div>
		</c:otherwise>
	</c:choose>
</form>
<script type="text/javascript">
var rmessage1 = "!!처리할 항목을 선택하세요";
var rmessage2 = "!!반품신청을 거절합니다. 신청은 철회되며 사용자화면에는 '반품거절'로 표기됩니다.<br/>!!거절사유를 입력하세요";
var rmessage3 = "!!반품신청을 보류합니다. 차후에 다시 처리할 수 있습니다.<br/>!!사용자화면에는 '반품처리중'으로 표기됩니다.";
var rmessage4 = "!!반품신청 후 상품을 발송하여 배송되는 중입니다.<br/>!!사용자화면에는 '반품처리중'로 표기됩니다.";
var rmessage5 = "!!반품신청 후 고객이 발송한 상품을 확인하였습니다..<br/>!!사용자화면에는 '반품처리중'로 표기됩니다.";
var rmessage6 = "!!반품신청을 승인하면 환불내역으로 이동합니다.<br/>!!환불이 처리될때까지 사용자화면에는 '반품처리중'으로 표기됩니다.";

$(function(){
	$.each($("input[name$='].claimCode']"),function(){
		var val = $(this).val();
		var checkedVal = $("input:radio[name='returnApplyMap["+val+"].claimStatus']:checked").val();
		
		if(checkedVal == "99"){  					// 반품거절
			$("#redText_"+val).html(rmessage2);
		}else if(checkedVal == "02"){				// 반품보류
			$("#redText_"+val).html(rmessage3);
		}else if(checkedVal == "10"){				// 회수중
			$("#redText_"+val).html(rmessage4);
		}else if(checkedVal == "11"){				// 회수완료
			$("#redText_"+val).html(rmessage5);
		}else if(checkedVal == "03"){				// 반품승인
			$("#redText_"+val).html(rmessage6);
		}else{										
			$("#redText_"+val).html(rmessage1);
			
			/* var deliveryCompanyId = $("select[name='returnApplyMap["+val+"].returnDeliveryCompanyId']").val();
			var deliveryNumber = $("input[name='returnApplyMap["+val+"].returnDeliveryNumber']").val();
			if(deliveryCompanyId !="" && deliveryNumber != ""){
				$("input:radio[name='returnApplyMap["+val+"].claimStatus'][value='10']").click();
			} */
		}
	});

	$('tr.parent :radio').on('change', function() {
		var parentClass = $(this).closest('tr.parent').data('class');
		var className = $(this).attr('class');
		var value = $(this).val();

		var $selector = $('tr.child[data-class="' + parentClass + '"] :radio.' + className + '[value=' +value + ']');
		$selector.prop('checked', $(this).prop('checked'));

		if (className == 'return-claim-status') {
			$.each($selector, function () {
				returnCheckedDisplay($(this).data('claim-code'), value);
			});
		}
	});


	$('tr.parent input[type=text]').on('input', function() {
		var parentClass = $(this).closest('tr.parent').data('class');
		var className = $(this).attr('class').replace('form-block ', '').replace('required ', '');
		var value = $(this).val();

		$('tr.child[data-class="' + parentClass + '"] input[type=text].' + className).val(value);
		$('tr.child[data-class="' + parentClass + '"] input[type=hidden].return-reserve-sido').val($('tr.parent[data-class="' + parentClass + '"] input[type=hidden].return-reserve-sido').val());
		$('tr.child[data-class="' + parentClass + '"] input[type=hidden].return-receive-sigungu').val($('tr.parent[data-class="' + parentClass + '"] input[type=hidden].return-reserve-sigungu').val());
		$('tr.child[data-class="' + parentClass + '"] input[type=hidden].return-receive-eupmyeondong').val($('tr.parent[data-class="' + parentClass + '"] input[type=hidden].return-reserve-eupmyeondong').val());
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


function returnCheckedDisplay(claimCode,value){
	$("#returnClaimInfo1_"+claimCode).hide();
	$("#returnClaimInfo2_"+claimCode).hide();
	
	if(value == "99"){  					// 반품거절
		$("#redText_"+claimCode).html(rmessage2);
		$("#returnClaimInfo1_"+claimCode).show();
	}else if(value == "02"){				// 반품보류
		$("#redText_"+claimCode).html(rmessage3);
	}else if(value == "10"){				// 회수중
		$("#redText_"+claimCode).html(rmessage4);
	}else if(value == "11"){				// 회수완료
		$("#redText_"+claimCode).html(rmessage5);
	}else if(value == "03"){				// 반품승인
		$("#redText_"+claimCode).html(rmessage6);
		$("#returnClaimInfo2_"+claimCode).show();
	}
}
</script>