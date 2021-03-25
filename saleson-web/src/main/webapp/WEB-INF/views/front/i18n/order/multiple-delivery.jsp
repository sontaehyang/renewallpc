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

<div class="popup_wrap">
	<h1 class="popup_title">복수배송지 등록</h1>
	<div class="popup_contents">
	
		<form:form modelAttribute="buy" name="buy">
	
			<c:forEach items="${buy.receivers}" var="receiver">
				<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
					<c:set var="singleShipping" value="${shipping.singleShipping}"/>
					
					<c:choose>
						<c:when test="${singleShipping == true}">
							<input type="hidden" name="totalQuantity" itemSequence="${shipping.buyItem.itemSequence}" value="${shipping.buyItem.itemPrice.quantity}" />
						</c:when>
						<c:otherwise>
							<c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="itemIndex">
								<input type="hidden" name="totalQuantity" itemSequence="${buyItem.itemSequence}" value="${buyItem.itemPrice.quantity}" />
				 			</c:forEach>
						</c:otherwise>
					</c:choose>
						
				</c:forEach>
			</c:forEach>
		
			
			<c:if test="${requestContext.userLogin == true}">
				<div id="op-buyer-input-area">
					<form:hidden path="buyer.companyName" />
					<form:hidden path="buyer.userName" />
					<form:hidden path="buyer.email" />
					<form:hidden path="buyer.phone1" />
					<form:hidden path="buyer.phone2" />
					<form:hidden path="buyer.phone3" />
					<form:hidden path="buyer.mobile1" />
					<form:hidden path="buyer.mobile2" />
					<form:hidden path="buyer.mobile3" />
					<form:hidden path="buyer.newZipcode" />
					<form:hidden path="buyer.zipcode" />
					<form:hidden path="buyer.zipcode1" />
					<form:hidden path="buyer.zipcode2" />
					<form:hidden path="buyer.sido" />
					<form:hidden path="buyer.sigungu" />
					<form:hidden path="buyer.eupmyeondong" />
					<form:hidden path="buyer.address" />
					<form:hidden path="buyer.addressDetail" />
				</div>
				
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
						<form:hidden path="defaultUserDelivery.addressDetail" />
					</div>
				</c:if>
			</c:if>
		
			<c:forEach begin="0" end="${setCount - 1}" step="1" var="receiverIndex"> 
	 			<input type="hidden" name="receiverIndexs" value="${receiverIndex}" />
				<h2 ${receiverIndex > 0 ? 'class="mt30"' : ''}>배송지${receiverIndex + 1}</h2>
				<div class="board_wrap">
		 			<table cellpadding="0" cellspacing="0" class="board-write" id="op-receive-input-area-${receiverIndex}">
			 			<caption>복수배송지 등록</caption>
			 			<colgroup> 
				 			<col style="width:160px;"> 
				 			<col style="width:auto;"> 
				 			<col style="width:130px;"> 
			 			</colgroup>
			 			<tbody>
			 				<c:if test="${requestContext.userLogin == true}">
				 				<tr>
				 					<th scope="row">배송지 선택</th>
				 					<td colspan="2"> 
					 					<div class="input_wrap col-w-0"> 
					 						<c:if test="${not empty buy.defaultUserDelivery}">
												<input type="radio" id="op-default-${receiverIndex}" name="infoCopy" value="default"> <label for="op-default-${receiverIndex}">기본 배송지</label>&nbsp;&nbsp;
											</c:if>
						 					<input type="radio" id="op-new-${receiverIndex}" name="infoCopy" value="clear"> <label for="op-new-${receiverIndex}">새로운 배송지</label>&nbsp;&nbsp;
											<input type="radio" id="op-same-${receiverIndex}" name="infoCopy" value="copy"> <label for="op-same-${receiverIndex}">주문자정보와 동일</label> &nbsp;&nbsp; 
						 					<button type="button" class="btn btn-ms btn-default" onclick="Common.popup('/order/delivery?target=order&receiverIndex=${receiverIndex}', 'delivery_list', 898, 660, 1);" title="배송지 목록">배송지 목록</button>  
					 					</div>
				 					</td>
				 				</tr>
				 			</c:if>
			 				<tr>
			 					<th scope="row">받으시는 분</th>
			 					<td colspan="2">
			 						<div class="input_wrap col-w-7">
			 							<form:input path="receivers[${receiverIndex}].receiveName" title="받으시는 분" class="required" maxlength="50" />
			 						</div> 
			 					</td>
			 				</tr>
			 				<tr>
			 					<th scope="row">휴대폰번호 <span class="necessary"></span></th>
			 					<td colspan="2">
			 						<div class="hp_area">
			 							<div class="input_wrap col-w-10">
			 								<form:select path="receivers[${receiverIndex}].receiveMobile1" title="휴대폰번호" cssClass="form-control required">
												<form:option value="" label="선택"></form:option>
												<form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
											</form:select>
			 							</div>
			 							<span class="connection"> - </span>
			 							 <div class="input_wrap col-w-10">			 	 			 	 							 
			 								<form:input path="receivers[${receiverIndex}].receiveMobile2" title="휴대폰번호" class="full _number required" maxlength="4" />
			 							</div>
			 							<span class="connection"> - </span>
			 							<div class="input_wrap col-w-10">			 	 			 	 							 
			 								<form:input path="receivers[${receiverIndex}].receiveMobile3" title="휴대폰번호" class="full _number required" maxlength="4" /> 
			 							</div> 		
			 						</div><!-- // hp_area --> 
			 					</td>
			 				</tr> 
			 				<tr>
			 					<th scope="row">전화번호</th>
			 					<td colspan="2">
			 						<div class="hp_area">
			 							<div class="input_wrap col-w-10">
			 								<form:select path="receivers[${receiverIndex}].receivePhone1" title="전화번호" cssClass="form-control">
												<form:option value="" label="선택"></form:option>
												<form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
											</form:select>
			 							</div>
			 							<span class="connection"> - </span>
			 							<div class="input_wrap col-w-10">
			 								<form:input path="receivers[${receiverIndex}].receivePhone2" title="전화번호" class="_number full" maxlength="4" /> 			 	 							 
			 							</div>
			 							<span class="connection"> - </span>
			 							<div class="input_wrap col-w-10">			 	 			 	 							 
			 								<form:input path="receivers[${receiverIndex}].receivePhone3" title="전화번호" class="_number full" maxlength="4" />
			 							</div> 	
			 						</div><!-- // hp_area --> 
			 					</td>
			 				</tr>
			 				 
			 				<tr>
			 					<th scope="row" valign="top">배송지 주소 <span class="necessary"></span></th>
			 					<td colspan="2">
			 						<div>
			 							<form:hidden path="receivers[${receiverIndex}].receiveNewZipcode" />
			 							<form:hidden path="receivers[${receiverIndex}].receiveSido" />
										<form:hidden path="receivers[${receiverIndex}].receiveSigungu" />
										<form:hidden path="receivers[${receiverIndex}].receiveEupmyeondong" /> 
										 
			 							<div class="input_wrap col-w-9">
			 								<form:input path="receivers[${receiverIndex}].receiveZipcode" title="우편번호" maxlength="7" class="required" readonly="true"/>
			 							</div>	 
			 							<div class="input_wrap"><button type="button" class="btn btn-ms btn-default" title="우편번호" onclick="openDaumPostcode('receive', ${receiverIndex})">우편번호</button></div>  
			 							<div class="input_wrap mt8 col-w-0">
			 								<form:input path="receivers[${receiverIndex}].receiveAddress" title="주소" class="required full" maxlength="100" readonly="true" htmlEscape="false" />
			 							</div>			 	 				  			 
			 							<div class="input_wrap mt8 col-w-0">	
			 								<form:input path="receivers[${receiverIndex}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" htmlEscape="false" />
			 							</div>		 							 
			 						</div>
			 					</td>
			 				</tr> 
			 				<tr>
			 					<th scope="row">배송시 요구사항</th>
			 					<td colspan="2">
			 						<div class="input_wrap col-w-0">
			 							<form:input path="receivers[${receiverIndex}].content" title="배송시 요구사항" class="full _filter" placeholder="ex) 부재시 경비실에 맡겨주세요." />
			 						</div> 
			 					</td>
			 				</tr>
			 				
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
			 				
			 				<c:set var="itemIndex">0</c:set>
			 				<c:forEach items="${buy.receivers}" var="receiver">
								<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
				 					
				 					<tr>
				 						<c:if test="${itemIndex == 0}">
				 							<th scope="row" rowspan="${totalItemRowCount}" valign="top" class="noline_bottom">배송<br>상품선택</th>
				 							<c:set var="itemIndex">${itemIndex + 1}</c:set>
				 						</c:if>
				 						
					 					<c:choose>
											<c:when test="${shipping.singleShipping == true}">
												<c:set var="buyItem" value="${shipping.buyItem}" />
												<td class="address_add">
							 						 <div class="item_info" id="op-item-info-${buyItem.itemSequence}">
														<p class="photo"><img src="${buyItem.item.imageSrc}" alt="item photo"></p>
														<div class="order_option">
															<p class="item_name">
																<c:if test="${buyItem.additionItemFlag == 'Y'}">┗(추가상품) </c:if>
																${ buyItem.item.itemName }
															</p>
															${ shop:viewOptionText(buyItem.options) }
															<div class="item_price">
																<span>${op:numberFormat(buyItem.itemPrice.sumPrice)}</span>원/ <span>${op:numberFormat(buyItem.itemPrice.quantity)}</span>개
															</div>
														</div>
													</div>  
							 					</td>
							 					<td>
							 						<div class="input_wrap col-w-0"> 
								 						<select title="수량 선택" name="buyQuantity" itemSequence="${buyItem.itemSequence}" receverIndex="${receiverIndex}">
															<option value="0">0개</option>
															<c:forEach begin="1" end="${buyItem.itemPrice.quantity}" step="1" var="quantity">
																<option value="${quantity}" ${receiverIndex == 0 ? (buyItem.itemPrice.quantity == quantity ? 'selected="selected"' : '') : '' }>${quantity}개</option>
															</c:forEach>
														</select> 
							 						</div>
							 					</td>
					 						</c:when>
					 						<c:otherwise>
					 							<c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="buyItemIndex">
						 							<td class="address_add">
								 						<div class="item_info" id="op-item-info-${buyItem.itemSequence}">
															<p class="photo"><img src="${buyItem.item.imageSrc}" alt="item photo"></p>
															<div class="order_option">
																<p class="item_name">
																	<c:if test="${buyItem.additionItemFlag == 'Y'}">┗(추가상품) </c:if>
																	${ buyItem.item.itemName }
																</p>
																${ shop:viewOptionText(buyItem.options) }
																<div class="item_price">
																	<span>${op:numberFormat(buyItem.itemPrice.sumPrice)}</span>원/ <span>${op:numberFormat(buyItem.itemPrice.quantity)}</span>개
																</div>
															</div>
														</div>  
								 					</td>
								 					<td>
								 						<div class="input_wrap col-w-0"> 
									 						<select title="수량 선택" name="buyQuantity" itemSequence="${buyItem.itemSequence}" receverIndex="${receiverIndex}">
																<option value="0">0개</option>
																<c:forEach begin="1" end="${buyItem.itemPrice.quantity}" step="1" var="quantity">
																	<option value="${quantity}" ${receiverIndex == 0 ? (buyItem.itemPrice.quantity == quantity ? 'selected="selected"' : '') : '' }>${quantity}개</option>
																</c:forEach>
															</select> 
								 						</div>
								 					</td>
								 				</c:forEach>
					 						</c:otherwise>
					 					</c:choose>
					 				</tr>
				 				</c:forEach>
			 				</c:forEach>
			 			</tbody>
			 		</table>  
			 	</div> <!-- // board_wrap E -->
			</c:forEach>
			
			<div class="btn_wrap pt30">  
				<button type="submit" class="btn btn-success btn-lg" title="적용하기">적용하기</button>   
				<button type="button" class="btn btn-default btn-lg" onclick="self.close()" title="취소하기">취소하기</button>  
			</div>
		</form:form>
	 	
	 	
	</div><!--//popup_contents E--> 
	<a href="javascript:self.close()" class="popup_close">창 닫기</a>
</div>

<page:javascript>

<!-- 다음 주소검색 --> 
<daum:address />
<script type="text/javascript">Common.loading.hide();</script>
<script type="text/javascript" src="/content/modules/op.order.js"></script>
<script type="text/javascript">

function reSetBuyQuantity() {
	$.each($('input[name="totalQuantity"]'), function(i, totalQuantity){
		var itemSequence = $(this).attr('itemSequence');
		var maxQuantity = $(this).val();

		$_items = $('select[name="buyQuantity"][itemSequence="'+ itemSequence +'"]');
		var totalSelectQuantity = 0;
		$.each($_items, function(j, item) {
			totalSelectQuantity += Number($(this).val());
		});

		$.each($_items, function(j, item) {
			var selectValue = Number($(this).find(':selected').val());
			$.each($(this).find('option'), function(z, option) {
				if ($(this).val() <= selectValue + (maxQuantity - totalSelectQuantity)) {
					$(this).prop('disabled', false); 
				} else { 
					$(this).prop('disabled', true);
				}
			});
		});
	});
}


$(function(){
	reSetBuyQuantity();
	$('select[name="buyQuantity"]').on('change', function(){
		reSetBuyQuantity();
	});
	
	// 받으시는분 복사
	$('input[name="infoCopy"]').on('click', function() {
		var index = $(this).attr('id').split('-')[2];
		if ($(this).val() == 'copy') {
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
				} else if ($('select[name="' + name + '"]').size() == 1) {
					$('select[name="' + name + '"]').val($(this).val()); 
				}
			});
		} else if($(this).val() == 'clear') {
			$('input, select', '#op-receive-input-area-'+index).not('input[name="infoCopy"], select[name="buyQuantity"]').val("");
		} else if ($(this).val() == 'default') { 
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
		}
	});
	
	$("#buy").validator({
		'requiredClass' : 'required',
		'submitHandler' : function() {
			
			var receivers = [];
			
			var isSuccess = true;
			$.each($('input[name="totalQuantity"]'), function() {
				var itemSequence = $(this).attr('itemSequence');
				
				var totalQuantity = $(this).val();
				$_items = $('select[name="buyQuantity"][itemSequence="'+ itemSequence +'"]');
				var totalSelectQuantity = 0;
				$.each($_items, function(){
					totalSelectQuantity += Number($(this).val());
				});
				
				if (totalQuantity != totalSelectQuantity) {
					isSuccess = false;
				}
			});
			
			if (isSuccess == false) {
				alert('배송지 설정이 되지 않은 상품이 있습니다.');
				return false;
			}
			
			$.each($('input[name="receiverIndexs"]'), function() {
				
				var receiverIndex = $(this).val();
				$_items = $('select[name="buyQuantity"][receverIndex="'+ receiverIndex +'"]');
				
				var items = [];
				$.each($_items, function() {
					if ($(this).val() > 0) {
						var itemSequence = $(this).attr('itemSequence');
						var item = {
							'itemSequence' 	: itemSequence,
							'quantity'		: $(this).val()
						}
						
						items.push(item);
					}
				});
				
				if (items.length > 0) {
					var receiver = {
						'receiverIndex' 		: receiverIndex,
						'receiveCompanyName'	: $('input[name="receivers['+receiverIndex+'].receiveCompanyName"]').val(),
						'receiveName'			: $('input[name="receivers['+receiverIndex+'].receiveName"]').val(),
						'receiveNewZipcode'		: $('input[name="receivers['+receiverIndex+'].receiveNewZipcode"]').val(),
						'receiveZipcode'		: $('input[name="receivers['+receiverIndex+'].receiveZipcode"]').val(),
						'receiveSido'			: $('input[name="receivers['+receiverIndex+'].receiveSido"]').val(),
						'receiveSigungu'		: $('input[name="receivers['+receiverIndex+'].receiveSigungu"]').val(),
						'receiveEupmyeondong'	: $('input[name="receivers['+receiverIndex+'].receiveEupmyeondong"]').val(),
						'receiveAddress'		: $('input[name="receivers['+receiverIndex+'].receiveAddress"]').val(),
						'receiveAddressDetail'	: $('input[name="receivers['+receiverIndex+'].receiveAddressDetail"]').val(),
						'receiveMobile1'		: $('select[name="receivers['+receiverIndex+'].receiveMobile1"]').val(),
						'receiveMobile2'		: $('input[name="receivers['+receiverIndex+'].receiveMobile2"]').val(),
						'receiveMobile3'		: $('input[name="receivers['+receiverIndex+'].receiveMobile3"]').val(),
						'receivePhone1'			: $('select[name="receivers['+receiverIndex+'].receivePhone1"]').val(),
						'receivePhone2'			: $('input[name="receivers['+receiverIndex+'].receivePhone2"]').val(),
						'receivePhone3'			: $('input[name="receivers['+receiverIndex+'].receivePhone3"]').val(),
						'content'				: $('input[name="receivers['+receiverIndex+'].content"]').val(),
						'items'					: items
					}
					
					receivers.push(receiver);
				}
			});
			
			if (receivers.length != $('input[name="receiverIndexs"]').size()) {
				$.each(receivers, function(i) {
					receivers[i].receiverIndex = i;
				});
			}
			
			opener.Order.setMultipleDelivery(receivers);
			self.close();
			return false;
		}
	});
});

function uppercase(text) {
	if (text == '' || text == undefined) return text;
	return text.substring(0, 1).toUpperCase() + text.substring(1);
}

//다음 우편번호 검색
function openDaumPostcode(mode, index) {
	
	var newZipcode 		= "buyer.newZipcode";
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
		'jibunAddress'			: address,
		'jibunAddressDetail' 	: addressDetail
	}
	
	openDaumAddress(tagNames);
	
}
</script>
</page:javascript>