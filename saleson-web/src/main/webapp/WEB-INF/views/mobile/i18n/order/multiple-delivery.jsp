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

<!-- 내용 : s -->
<div class="con">
	<div class="pop_title">
		<h3>복수배송지 등록</h3>
		<a href="javascript:multipleDeliveryViewClose();" class="history_back">뒤로가기</a>
	</div>
	<!-- //pop_title -->
	
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
		
		
		<div class="pop_con pop_conA">
			<c:forEach begin="0" end="${setCount - 1}" step="1" var="receiverIndex">
				<input type="hidden" name="receiverIndexs" value="${receiverIndex}" />
				<h4>배송지${receiverIndex + 1}</h4>
				<div class="bd_table" id="op-receive-input-area-${receiverIndex}">
					<ul class="del_info">
						<c:if test="${requestContext.userLogin == true}">
							<li>
								<span class="del_tit t_gray">배송지 이름</span>
								<div class="input_area">
									<select name="infoCopy">
										<c:if test="${not empty buy.defaultUserDelivery}">
			 								<option value="default-${receiverIndex}">${op:message('M01581')}</option> <!-- 기본배송지 -->
			 							</c:if>
			 							
			 							<c:forEach items="${userDeliveryList}" var="item">
			 								<c:if test="${ item.defaultFlag eq 'N' }">
		 										<option value="delivery-info-${item.userDeliveryId}-${receiverIndex}">${ item.title }</option>
		 									</c:if>
		 								</c:forEach>
		 								
										<option value="copy-${receiverIndex}">주문자정보와 동일</option> <!-- 본인 -->
										<option value="clear-${receiverIndex}" ${receiverIndex > 0 ? "selected='selected'" : ""}>${op:message('M00614')}</option> <!-- 새로운 주소 입력 -->
									</select> 
								</div>
							</li>
						</c:if>
						
						<li>
							<span class="del_tit t_gray star">받으시는 분</span>
							<div class="input_area">
								<form:input path="receivers[${receiverIndex}].receiveName" title="받으시는 분" class="required" maxlength="50" />
							</div>
						</li>
						<li>
							<span class="del_tit t_gray star">휴대폰</span>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<form:select path="receivers[${receiverIndex}].receiveMobile1" title="휴대전화" cssClass="required">
											<form:option value="" label="선택"></form:option>
											<form:options items="${op:getCodeInfoList('PHONE')}" itemLabel="label" itemValue="key.id" />
										</form:select>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="receivers[${receiverIndex}].receiveMobile2" title="${op:message('M00329')}" class="required _number" maxlength="4" /> 
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="receivers[${receiverIndex}].receiveMobile3" title="${op:message('M00329')}" class="required _number" maxlength="4" />
									</div>
								</div>
							</div>
						</li>
						<li>
							<span class="del_tit t_gray">전화번호</span>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<form:select path="receivers[${receiverIndex}].receivePhone1" title="전화번호">
											<form:option value="" label="선택"></form:option>
											<form:options items="${op:getCodeInfoList('TEL')}" itemLabel="label" itemValue="key.id" />
										</form:select>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="receivers[${receiverIndex}].receivePhone2" title="${op:message('M00016')}" class="_number" maxlength="4" /> 
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<form:input path="receivers[${receiverIndex}].receivePhone3" title="${op:message('M00016')}" class="_number" maxlength="4" />
									</div>
								</div>
							</div>
						</li>
						<li class="chunk">
							<label class="del_tit t_gray star">주소</label>
							<div class="user_info">
								<div class="in_td">
									<form:hidden path="receivers[${receiverIndex}].receiveNewZipcode" />
									<form:input path="receivers[${receiverIndex}].receiveZipcode" title="우편번호" maxlength="7" class="required" readonly="true" />
								</div>
								<div class="in_td address">
									<button type="button" class="btn_st3 t_lgray02" onClick="javascript:openDaumPostcode('receive', ${receiverIndex});">우편번호</button>
								</div>
							</div>
							<div class="input_area">
								<form:hidden path="receivers[${receiverIndex}].receiveSido" />
								<form:hidden path="receivers[${receiverIndex}].receiveSigungu" />
								<form:hidden path="receivers[${receiverIndex}].receiveEupmyeondong" /> 
								<form:input path="receivers[${receiverIndex}].receiveAddress" title="주소" class="required" maxlength="100" readonly="true" /> 
							</div>
						</li>
						<li>
							<span class="del_tit t_gray star">상세주소</span>
							<form:input path="receivers[${receiverIndex}].receiveAddressDetail" title="상세주소" class="full" maxlength="50" />
						</li>
						<li>
							<span class="del_tit t_gray">배송메세지</span>
							<form:input path="receivers[${receiverIndex}].content" title="배송시 요구사항" class="full _filter" placeholder="ex) 부재시 경비실에 맡겨주세요." />
						</li>
					</ul>
					<div class="cart_list">
						<ul class="list">
							<c:forEach items="${buy.receivers}" var="receiver">
								<c:forEach items="${receiver.itemGroups}" var="shipping" varStatus="deliveryIndex">
				 					<c:set var="singleShipping" value="${shipping.singleShipping}"/>
									<c:choose>
										<c:when test="${singleShipping == true}">
											<c:set var="buyItem" value="${shipping.buyItem}" />
											<li>
												<div class="inner">
													<div class="con_top cf">
														<div class="cart_img">
															<img src="${buyItem.item.imageSrc}" alt="${ buyItem.item.itemName }">
														</div>
														<div class="cart_name">
															<p class="tit">
																<c:if test="${buyItem.additionItemFlag == 'Y'}">┗(추가상품) </c:if>
																${ buyItem.item.itemName }
															</p>
															<p class="detail">${shop:viewOptionText(buyItem.options)}</p>
														</div>
													</div>
													<div class="con_bot">
														
														<select name="buyQuantity" itemSequence="${buyItem.itemSequence}" receverIndex="${receiverIndex}">
															<option value="0">0개</option>
															<c:forEach begin="1" end="${buyItem.itemPrice.quantity}" step="1" var="quantity">
																<option value="${quantity}" ${receiverIndex == 0 ? (buyItem.itemPrice.quantity == quantity ? 'selected="selected"' : '') : '' }>${quantity}개</option>
															</c:forEach>
														</select>
														
													</div>
												</div>
											</li>
										</c:when>
										<c:otherwise>
											<c:forEach items="${shipping.buyItems}" var="buyItem" varStatus="itemIndex">
												<li>
													<div class="inner">
														<div class="con_top cf">
															<div class="cart_img">
																<img src="${buyItem.item.imageSrc}" alt="${ buyItem.item.itemName }">
															</div>
															<div class="cart_name">
																<p class="tit">
																	<c:if test="${buyItem.additionItemFlag == 'Y'}">┗(추가상품) </c:if>
																	${ buyItem.item.itemName }
																</p>
																<p class="detail">${shop:viewOptionText(buyItem.options)}</p>
															</div>
														</div>
														<div class="con_bot">
															
															<select name="buyQuantity" itemSequence="${buyItem.itemSequence}" receverIndex="${receiverIndex}">
																<option value="0">0개</option>
																<c:forEach begin="1" end="${buyItem.itemPrice.quantity}" step="1" var="quantity">
																	<option value="${quantity}" ${receiverIndex == 0 ? (buyItem.itemPrice.quantity == quantity ? 'selected="selected"' : '') : '' }>${quantity}개</option>
																</c:forEach>
															</select>
															
														</div>
													</div>
												</li>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:forEach>
						</ul>
					</div>
					<!-- cart_list -->
				</div>
				<!-- bd_table -->
			</c:forEach>
			
			<!-- bd_table -->
			<div class="btn_wrap">
				<button type="button" class="btn_st1" onclick="multipleDeliveryViewClose()">취소</button>
				<button type="submit" class="btn_st1 decision">등록</button>
			</div>
		</div>
	</form:form>
</div>
<!-- 내용 : e -->

<page:javascript>
<c:choose>
	<c:when test="${shopContext.mobileLayer == true}"><daum:address-layer /></c:when>
	<c:otherwise><daum:address /></c:otherwise>
</c:choose>

<script type="text/javascript" src="/content/modules/op.order.js"></script>
<script type="text/javascript">

function multipleDeliveryViewClose() {
	if (isMobileLayer == true || isMobileLayer == 'true') {
		parent.$('.op-app-popup-wrap').show();
		/* 주문자 정보가 두개로 나와서 remove 로 처리 2017-03-09 yulsun.yoo 
		parent.$('.op-app-popup-content').hide(); */
		parent.$('.op-app-popup-content').remove();
	} else {
		self.close();
	}
}

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
				} else if ($('select[name="' + name + '"]').size() == 1) {
					$('select[name="' + name + '"]').val($(this).val());
				}
			});
		} else if(type == 'clear') {
			$('input, select', '#op-receive-input-area-' + index).not('select[name="infoCopy"], select[name="buyQuantity"]').val("");
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
		Order.setShippingAmount();
		
		Order.setAmountText();
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
						'receiveMobile1'		: $('select[name="receivers['+receiverIndex+'].receiveMobile1"] :selected').val(),
						'receiveMobile2'		: $('input[name="receivers['+receiverIndex+'].receiveMobile2"]').val(),
						'receiveMobile3'		: $('input[name="receivers['+receiverIndex+'].receiveMobile3"]').val(),
						'receivePhone1'			: $('select[name="receivers['+receiverIndex+'].receivePhone1"] :selected').val(),
						'receivePhone2'			: $('input[name="receivers['+receiverIndex+'].receivePhone2"]').val(),
						'receivePhone3'			: $('input[name="receivers['+receiverIndex+'].receivePhone3"]').val(),
						'content'				: $('input[name="receivers['+receiverIndex+'].content"]').val(),
						'items'					: items
					}
					
					receivers.push(receiver);
				}
			});
			
			if (isMobileLayer == true || isMobileLayer == 'true') {
				parent.Order.setMultipleDelivery(receivers);
			} else {
				opener.Order.setMultipleDelivery(receivers);
			}
			
			multipleDeliveryViewClose(); 
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