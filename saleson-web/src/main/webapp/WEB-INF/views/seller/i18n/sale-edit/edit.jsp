<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
span.require {color: #e84700; margin-left: 5px;}
</style>


		<div class="location">
			<a href="#">상품관리</a> &gt;  <a href="#">상품정보</a> &gt; <a href="#" class="on">상품등록</a>
		</div>
		
		
		<form:form modelAttribute="itemSaleEdit" method="post">
			<form:hidden path="itemSaleEditId"/>
			<form:hidden path="itemCode"/>
			<form:hidden path="itemName"/>
			<form:hidden path="message"/>
			<div class="item_info_wrap">	
				<h3>가격변경요청</h3>
				<div class="board_write">						
					<table class="board_write_table" summary="상품기본정보">
						<caption>상품기본정보</caption>
						<colgroup>
							<col style="width: 180px;" />
							<col style="" />
							<col style="width: 180px;" />
							<col style="width: 250px;" />
						</colgroup>
						<tbody>
							<c:if test="${itemSaleEdit.itemId != 0}">
								<tr>
									<td class="label">${op:message('M00019')}</td> <!-- 상품번호 -->
									<td colspan="3">
										<div>
											<c:choose>
												<c:when test='${op:property("saleson.view.type") eq "api"}'>
													<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${itemSaleEdit.itemCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
												</c:when>
												<c:otherwise>
													<a href="/products/preview/${itemSaleEdit.itemCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
													<a href="/m/products/preview/${itemSaleEdit.itemCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="" /></a>
												</c:otherwise>
											</c:choose>

											<span style="font-weight:bold; font-size: 14px; color: #000;">
												${itemSaleEdit.itemCode}
											</span>
										</div>
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="label">${op:message('M00018')}</td> <!-- 상품명 -->
								<td colspan="3">
									<div>
										${itemSaleEdit.itemName}
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00785')}</td> <!-- 정가 -->
								<td>
									<div>
										<form:input path="itemPrice" maxlength="8" class="amount optional _number_comma" title="${op:message('M00785')}" /> 원 
									</div>
								</td>
								<td class="label">${op:message('M00786')}</td> <!-- 판매가격 -->
								<td>
									<div>
										<input type="text" name="salePrice" id="salePrice" maxlength="8" value="${op:negativeNumberToEmpty(itemSaleEdit.salePrice)}" class="amount required _min_10 _number_comma" title="${op:message('M00786')}" /> 원
									</div>
								</td>
								<%-- <td class="label">원가</td>
								<td>
									<div>
										<input type="text" name="costPrice" id="costPrice" maxlength="8" value="${op:negativeNumberToEmpty(itemSaleEdit.costPrice)}" class="amount required _number_comma" title="원가" /> 원 
									</div>
								</td> --%>
							</tr>
							<tr>
								<td class="label">가격변경내역</td>
								<td colspan="3">
									<div>
										${op:nl2br(itemSaleEdit.message)}
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>	
				
				<div id="buttons" class="tex_c mt20">
					<button type="submit" class="btn btn-active">수정</button>
					<a href="javascript:Link.list('${requestContext.managerUri}/item/sale-edit/list')" class="btn btn-default">${op:message('M00480')}</a>	<!-- 목록 -->
				</div>		
			</div>		
		</form:form>
		

<script type="text/javascript">
$(function() {
	
	// 필수 입력항목 마커.
	$('.required').closest('tr').find('td.label').append(' <span class="require">*</span>');
	

	// Validation 조건 설정.
	setValidatorCondition();
	
	// 숫자 컴마.
	Common.addNumberComma();

	// 폼체크
	$("#itemSaleEdit").validator({
			'submitHandler' : function() {

				
				/* 비회원 가격 
				var $salePriceNonmember = $('#salePriceNonmember');
				if ($('input[name=nonmemberOrderType]:checked').val() == '1' &&
						($.trim($salePriceNonmember.val()) == '' || $salePriceNonmember.val() == '0')) {
					$.validator.validatorAlert($.validator.messages['text'].format($salePriceNonmember.attr('title')), $salePriceNonmember);
					$salePriceNonmember.focus();
					return false;
				}*/
				
				
				
				// 할인 가능 금액 체크
				// 할인 가능금액 100 - 수수료율 - 30 까지만 가능.
				Common.removeNumberComma(); 
				
				var salePrice = Number($('#salePrice').val());
				var discountLimitRate = 100 - (Number($('#commissionRate').val()) + 30);
				
				var sellerDiscountAmount = 0;
				if ($('input[name=sellerDiscountFlag]:checked').val() == 'Y') {
					sellerDiscountAmount = Number($('#sellerDiscountAmount').val());
				}
				
				var spotDiscountAmount = 0;
				if ($('input[name=spotFlag]:checked').val() == 'Y') {
					spotDiscountAmount = Number($('#spotDiscountAmount').val());
				}
				
				var discountRate = Math.round((sellerDiscountAmount + spotDiscountAmount) * 100 / salePrice);
				if (discountRate > discountLimitRate) {
					alert('할인 가능 금액은 판매금액의 ' + discountLimitRate + '% 까지만 설정이 가능합니다.\n판매금액 및 할인금액을 확인해 주세요.');
					Common.addNumberComma(); 
					$('#salePrice').focus();
					return false;
				}
				
					

				// 최소/최대 구매수량 
				var orderMinQuantity = $.trim($('#orderMinQuantity').val());
				var orderMaxQuantity = $.trim($('#orderMaxQuantity').val());
				if (orderMinQuantity != '' && orderMaxQuantity != ''
					&& Number(orderMinQuantity) > 0 && Number(orderMaxQuantity) > 0) {
					
					if (Number(orderMinQuantity) > Number(orderMaxQuantity)) {
						alert(Message.get("M00699"));	// 최대 구매 수량을 최소 구매 수량 보다 큰 값으로 입력해 주세요.
						$('#orderMaxQuantity').focus();
						return false;
					}
				}
				
				
				// 포인트 처리
				$('#item_point_area tr').each(function(){
					var pointType = $(this).find('input[type=radio]').eq(0).prop('checked') == true ? "1" : "2"; 
					$(this).find('input[name=pointType]').val(pointType);
				});
				

				
				
				// 상품 옵션인 경우 본 상품 재고 관리는 무제한으로 변경.
				var itemOptioinFlag = $('input[name=itemOptionFlag]:checked').val();
				var itemOptionType = $('input[name=itemOptionType]:checked').val();
				if (itemOptioinFlag == 'Y' && itemOptionType != 'T') {
					$('input[name=stockFlag]').eq(0).prop('checked', true);
					$('#trStockQuantity').hide();
					$('#stockQuantity').val('');
					setValidatorCondition();
				}
				
				$("#item-options input[name=optionType]").val(itemOptionType);

				
				// 배송비 설정
				var $shippingType = $('input[name=shippingType]:checked');
				var $shippingInfo = $shippingType.closest('tr');
				var shipping = $shippingInfo.find('.opt-shipping').val();
				var shippingFreeAmount = $shippingInfo.find('.opt-shipping-free-amount').val();
				
				$('#shipping').val(shipping);
				$('#shippingFreeAmount').val(shippingFreeAmount);
											
				
				
				
				// 관련상품
				var relationItemDisplayType = $('input[name=relationItemDisplayType]:checked').val();
				if (relationItemDisplayType == 2) {
					if ($('input[name=relatedItemIds]').size() == 0) {
						alert(Message.get("M00080"));	// 관련상품을 추가해 주세요.
						$('#button_add_relation_item').focus();
						return false;
					}
				}
				
				
				
				// 배송비 기준이 출고지조건부(3)이 아닌 경우 shipmentGroupCode 초기화
				if ($shippingType.val() != '3') {
					$('#shipmentGroupCode').val('');
				}
				
				
				if (!confirm('가격변경요청을 하시겠습니까?')) {
					return false;
				}

				
				Common.removeNumberComma(); 
				
				$(".spotFlag").removeAttr("disabled");
			}
				
		});
	
	$( window ).scroll(function() {
	
		setHeight();
	});
});


// 비회원 판매가격 이벤트 핸들러.
function initNonmemberPriceEvent() {
	showHideContent($('input[name=salePriceNonmemberFlag]:checked'));
	$('input[name=salePriceNonmemberFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
}

// 사은품 이벤트 핸들러.
function initFreeGiftEvent() {
	showHideContent($('input[name=freeGiftFlag]:checked'));
	$('input[name=freeGiftFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
}


// 상품 수수료 설정 이벤트 핸들러.
function initCommissionEvent() {
	setCommissionRate();
	
	$('input[name=commissionType]').on('click', function() {
		setCommissionRate();	
	});
	
	function setCommissionRate() {
		var commissionType = $('input[name=commissionType]:checked').val();
		var $commissionRate = $('#commissionRate');
		
		// 입점업체 수수료 적용.
		if (commissionType == '1') {
			$commissionRate.val($('#sellerCommissionRate').val());
			$commissionRate.prop('readonly', true);
			
		} else if (commissionType == '2') {
			//$commissionRate.val(0);
			$commissionRate.prop('readonly', false);
			
		} else {
			return;
		}
	}
}
	

// 상품 옵션 이벤트 핸들러.
function initItemOptionEvent() {

	// 상품옵션
	showHideItemOption();
	

	$('input[name=itemOptionFlag]').on('click', function() {
		if ($(this).val() == 'N') {
			if (!confirm('상품옵션을 사용안함으로 설정하는 경우 현재 설정된 옵션 설정이 삭제됩니다. 변경하시겠습니까?')) {
				return false;
			}
			clearItemOptions();
		}
		showHideItemOption();
		setValidatorCondition();
	});
	


	// 옵션 타입 선택 이벤트.
	$('input[name=itemOptionType]').on('click', function() {
		if (!confirm('옵션 형태를 변경하는 경우 현재 설정된 옵션 설정이 삭제됩니다. 옵션타입을 변경하시겠습니까?')) {
			return false;
		}
		//$('.option-step.step2').hide();
		$('.option-type').hide();
		
		clearItemOptions();
		
		showHideItemOption();
		setValidatorCondition();
	});
	
	// S옵션 입력 도우미 - 항목추가.
	$('.add-option-input').on('click', function() {
		var addHtml = $('#s-option-input-wrap').find('tr').eq(0).parentHtml();
		$('#s-option-input-wrap').append(addHtml);
	});
	
	// S옵션 입력 도우미 - 항목삭제.
	$('#s-option-input-wrap').on('click', ' .delete_option_input', function(e) {
		e.preventDefault();
		if ($('#s-option-input-wrap tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input').val('');
		}
	});
	
	
	// 옵션 적용 (옵션 생성)
	$('.make-item-option').on('click', function(e) {
		e.preventDefault();
		
		var optionType = $('input[name=itemOptionType]:checked').val();
		
		if (optionType == 'S') {
			// 1. 입력 검증.
			var $target = $('#s-option-input-wrap');
			var errorCount = validOptionInput($target);
			
			if (errorCount > 0) {
				return;
			}
			
			
			// 2. 옵션 값 생성.
			var options = [];
			var optionIndex = 0;
			$target.find('tr').each(function() {
				var option1 = $.trim($(this).find('input').eq(0).val());
				var option2 = $.trim($(this).find('input').eq(1).val());
				
				var optArray = option2.split(',');
				
				for (var i = 0; i < optArray.length; i++) {
					var opt2 = $.trim(optArray[i]);
					if (opt2 != '') {
						options[optionIndex] = {'optionType': 'S', 'optionName1': option1, 'optionName2': opt2, 'optionName3': ''};
						optionIndex++;
					}
				}
			});
			
			// 3. 옵션 적용.
			makeItemOptions(options);
		
		} else if (optionType == 'S2') {
			// 1. 입력 검증.
			var $target = $('#s2-option-input-wrap');
			var errorCount = validOptionInput($target);
			
			if (errorCount > 0) {
				return;
			}
			
			// 2. 옵션 값 생성.
			var options = [];
			var optionIndex = 0;
			
			var optionTitle1 = $.trim($target.find('tr').eq(0).find('input').eq(0).val());
			var optionTitle2 = $.trim($target.find('tr').eq(1).find('input').eq(0).val());
			
			var option1 = $.trim($target.find('tr').eq(0).find('input').eq(1).val());
			var option2 = $.trim($target.find('tr').eq(1).find('input').eq(1).val());
				
			var opt1Array = option1.split(',');
			var opt2Array = option2.split(',');
				
			for (var i = 0; i < opt1Array.length; i++) {
				for (var j = 0; j < opt2Array.length; j++) {
					var opt1 = $.trim(opt1Array[i]);
					var opt2 = $.trim(opt2Array[j]);
					
					if (opt1 != '' && opt2 != '') {
						options[optionIndex] = {'optionType': 'S2', 'optionName1': opt1, 'optionName2': opt2, 'optionName3': ''};
						optionIndex++;
					}
				}
			}
			
			$('#itemOptionTitle1').val(optionTitle1);
			$('#itemOptionTitle2').val(optionTitle2);
			
			// 3. 옵션 적용.
			makeItemOptions(options);
			
		} else if (optionType == 'S3') {
			// 1. 입력 검증.
			var $target = $('#s3-option-input-wrap');
			var errorCount = validOptionInput($target);
			
			if (errorCount > 0) {
				return;
			}
			
			// 2. 옵션 값 생성.
			var options = [];
			var optionIndex = 0;
			
			var optionTitle1 = $.trim($target.find('tr').eq(0).find('input').eq(0).val());
			var optionTitle2 = $.trim($target.find('tr').eq(1).find('input').eq(0).val());
			var optionTitle3 = $.trim($target.find('tr').eq(2).find('input').eq(0).val());
			
			var option1 = $.trim($target.find('tr').eq(0).find('input').eq(1).val());
			var option2 = $.trim($target.find('tr').eq(1).find('input').eq(1).val());
			var option3 = $.trim($target.find('tr').eq(2).find('input').eq(1).val());
				
			var opt1Array = option1.split(',');
			var opt2Array = option2.split(',');
			var opt3Array = option3.split(',');
				
			for (var i = 0; i < opt1Array.length; i++) {
				for (var j = 0; j < opt2Array.length; j++) {
					for (var k = 0; k < opt3Array.length; k++) {
						var opt1 = $.trim(opt1Array[i]);
						var opt2 = $.trim(opt2Array[j]);
						var opt3 = $.trim(opt3Array[k]);
						
						if (opt1 != '' && opt2 != '' && opt3 != '') {
							options[optionIndex] = {'optionType': 'S2', 'optionName1': opt1, 'optionName2': opt2, 'optionName3': opt3};
							optionIndex++;
						}
					}
				}
			}
			
			$('#itemOptionTitle1').val(optionTitle1);
			$('#itemOptionTitle2').val(optionTitle2);
			$('#itemOptionTitle3').val(optionTitle3);
			
			// 3. 옵션 적용.
			makeItemOptions(options);
		}

	});
	
	// 상품옵션 - 재고 연동 여부 변경 시 
	$('#item-options').on('change', 'select[name=optionStockFlag]', function() {
		var optionStockFlag = $(this).val();
		var $optionStockQuantity = $(this).closest('tr').find('input[name=optionStockQuantity]');
		if (optionStockFlag == 'Y') {
			$optionStockQuantity.prop('readonly', false).addClass('required-item-option');
			
		} else {
			$optionStockQuantity.val('').prop('readonly', true).removeClass('required-item-option');
		
		}
	});
	
	// 상품옵션 - 옵션추가
	$('.add-item-option').on('click', function() {
		var $templateOption = $('#item-options tr').eq(0).find('input[name=optionStockQuantity]');
		var isReadonly = $templateOption.prop('readonly');
		$templateOption.prop('readonly', true);
		
		var optionHtml = $('#item-options tr').eq(0).parentHtml();
		$('#item-options').append(optionHtml);

		$templateOption.prop('readonly', isReadonly);
				
		var $newItem = $('#item-options tr:last-child');
		$newItem.find('input').val('');
		$newItem.find('input[name=optionStockQuantity]').prop('readonly', true).removeClass('required-item-addition');
		$newItem.find('select').each(function() {
			$(this).find('option:eq(0)').prop('selected', true);
		});
		
	});
	
	// 상품옵션 - 옵션삭제
	$('#item-options').on('click', '.delete-item-option', function(e) {
		e.preventDefault();
		
		if ($('#item-options tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input').val('');
			$(this).closest('tr').find('select').each(function() {
				$(this).find('option:eq(0)').prop('selected', true);
			});
		}
	});
	
	// 상품 텍스트 옵션 - 옵션추가
	$('.add-item-text-option').on('click', function() {
		var optionHtml = $('#item-text-options tr').eq(0).parentHtml();
		$('#item-text-options').append(optionHtml);
		
		var $newItem = $('#item-text-options tr:last-child');
		$newItem.find('input').val('');
		$newItem.find('input[name=optionType]').val('T');
		$newItem.find('select').each(function() {
			$(this).find('option:eq(0)').prop('selected', true);
		});
	});
	
	// 상품 텍스트 옵션 - 옵션삭제
	$('#item-text-options').on('click', '.delete-item-text-option', function(e) {
		e.preventDefault();
		
		if ($('#item-text-options tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input').val('');
			$(this).closest('tr').find('select').each(function() {
				$(this).find('option:eq(0)').prop('selected', true);
			});
		}
	});
	
	
	
	function showHideItemOption() {
		var itemOptionFlag = $('input[name=itemOptionFlag]:checked').val();
		var itemOptionType = $('input[name=itemOptionType]:checked').val();
		$('.option-step.step1').hide();
		$('.option-step.step2').hide();
		$('.option-type').hide();
		$('.option-S3').hide();
		$('.option-S3').find('input[name=optionName3]').removeClass('required_item_option');
		
		if (itemOptionFlag == 'N' || itemOptionType === undefined) {
			return;
		}
		$('.option-step.step1').show();
		if (itemOptionType == 'S') {
			$('#itemOptionTitle1').val('옵션명');
			$('#itemOptionTitle2').val('옵션값');
			$('.item-option-table').show();

		} else if (itemOptionType == 'S2') {
			
			$('.item-option-table').show();
			
		} else if (itemOptionType == 'S3') {
			$('.item-option-table').show();
			$('.option-S3').show();
			$('.option-S3').find('input[name=optionName3]').addClass('required_item_option');
			
			
		} else if (itemOptionType == 'T') {
			$('.option-step.step2').show();
			$('.item-option-table').hide();
			
		}
		
		if (itemOptionType != 'T') {
			$('.option-step.step2').show();
			$('.option-type.type-' + itemOptionType).show();
		}
		
		// 옵션 상품 재고 수량 / readonly
		$('select[name=optionStockFlag]').each(function() {
			var optionStockFlag = $(this).val();
			var $optionStockQuantity = $(this).closest('tr').find('input[name=optionStockQuantity]');
			if (optionStockFlag == 'Y') {
				$optionStockQuantity.prop('readonly', false).addClass('required-item-option');
				
			} else {
				$optionStockQuantity.val('').prop('readonly', true).removeClass('required-item-option');
			
			}
		});
	}
	// 옵션 입력 도우미 - 입력 검증.
	function validOptionInput($target) {
		var errorCount = 0;
			
		$target.find('input').each(function(i) {
			var optionValue = $.trim($(this).val());
			var optiotTitle = $(this).attr('title');
			
			if (optionValue == '') {
				errorCount++;
				alert(optiotTitle + '을 입력해 주세요.');
				$(this).focus();
				return false;
			}
		});
		
		return errorCount;
	}
	
	// 상품 옵션 clear {
	function clearItemOptions() {
		var optionHtml = $('#item-options tr').eq(0).parentHtml();
		var $itemOptions = $('#item-options');
		
		$itemOptions.find('tr').remove();
		$itemOptions.append(optionHtml);
		$('#itemOptionTitle1, #itemOptionTitle2, #itemOptionTitle3').val('');
		resetItemOption($itemOptions);
	}
	
	
	// 상품 옵션 reset.
	function resetItemOption($target) {
		$target.find('input').val('');
		$target.find('input[name=optionStockQuantity]').prop('readonly', true);
		$target.find('select[name=optionStockFlag]').val('N');
		$target.find('select[name=optionSoldOutFlag]').val('N');
		$target.find('select[name=optionDisplayFlag]').val('Y');
	}
	
	// 상품 옵션 생성.
	function makeItemOptions(options) {
		//$('#item-options tr').eq(0).find('input[name=optionStockQuantity]').prop('readonly', true);
		var optionHtml = $('#item-options tr').eq(0).parentHtml();
		
		var makeHtml = '';
		
		for (var i = 0; i < options.length; i++) {
			makeHtml += optionHtml;
		}
		
		
		var $itemOptions = $('#item-options');
		$itemOptions.find('tr').remove();
		$itemOptions.append(makeHtml);
		

		for (var i = 0; i < options.length; i++) {
			var $tr = $itemOptions.find('tr').eq(i);
			$tr.find('input[type=text]').val('');
			
			resetItemOption($tr);
			
			$tr.find('input[name=optionType]').val(options[i].optionType);
			$tr.find('input[name=optionName1]').val(options[i].optionName1);
			$tr.find('input[name=optionName2]').val(options[i].optionName2);
			$tr.find('input[name=optionName3]').val(options[i].optionName3);

		}
		
	}
	
}


// 추가 상품 이벤트 핸들러.
function initItemAdditionEvent() {
	
	setItemAddition();
	
	// 추가 구성 사용 여부 클릭 이벤트.
	$('input[name=itemAdditionFlag]').on('click', function() {
		setItemAddition();
	});
	
	// 추가구성 상품 - 상품추가
	$('.add-item-addition').on('click', function() {
		var $itemAdditions = $('#item-additions');
	
		var optionHtml = $itemAdditions.find('tr').eq(0).parentHtml();
		$itemAdditions.append(optionHtml);
		
		
		var $newItem = $itemAdditions.find('tr:last-child');
		$newItem.find('input').val('');
		$newItem.find('input[name=additionStockQuantity]').prop('readonly', true).removeClass('required-item-addition');
		$newItem.find('select').each(function() {
			$(this).find('option:eq(0)').prop('selected', true);
		});
		
	});
	
	// 추가구성 상품 - 상품삭제
	$('#item-additions').on('click', '.delete-item-addition', function(e) {
		e.preventDefault();
		
		if ($('#item-additions tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input').val('');
			$(this).closest('tr').find('select').each(function() {
				$(this).find('option:eq(0)').prop('selected', true);
			});
		}
	});
	
	// 재고 연동여부 readonly
	$('#item-additions').on('change', 'select[name=additionStockFlag]', function(e) {
		handleAdditionStockFlagEvent($(this));
	});
	
	
	// 재고 연동 여부에 따른 재고수량 / readonly 
	function handleAdditionStockFlagEvent($target) {
		var stockFlag = $target.val();
		var $stockQuantity = $target.closest('tr').find('input[name=additionStockQuantity]');
		if (stockFlag == 'Y') {
			$stockQuantity.prop('readonly', false).addClass('required-item-addition');
			
		} else {
			$stockQuantity.val('').prop('readonly', true).removeClass('required-item-addition');
		
		}
	}
	
	// 추가 구성 상품 init
	function setItemAddition() {
		var itemAdditionFlag = $('input[name=itemAdditionFlag]:checked').val();
		var $itemAdditionWrap = $('.item-addition-wrap');
		
		if (itemAdditionFlag == 'Y') {
			$itemAdditionWrap.show();
		} else {
			$itemAdditionWrap.hide();
		}
		
		// 옵션 상품 재고 수량 / readonly
		$('select[name=additionStockFlag]').each(function() {
			handleAdditionStockFlagEvent($(this));
		});
		
		setValidatorCondition();
	}
}

// 배송 정보 설정 이벤트 핸들러.
function initShippingEvent() {
	
	// 배송구분 설정 
	setDeliveryType();
	
	// 배송비 종류 선택 설정.
	setShippingType();
	
	// 배송비 종류 노출 설정.
	showHideShippingType();
	
	// 공급사 선택 이벤트.
	$('#sellerId').on('change', function() {
		
		// 배송구분 설정 
		setDeliveryType();
	
		// 판매자 정보 / 출고지 / 반송지 정보 조회 
		setShipmentInformation();
		

	});
	
	
	// 배송구분 선택 이벤트
	$('#deliveryType').on('change', function() {
		// 배송비 종류 초기화
		setShipmentInformation();
		

		
	});
	
	
	// 배송구분 설정 
	function setDeliveryType() {
		var isHqSeller = $('#sellerId option:selected').data('isHqSeller') == 'Y' ? true : false;		// 공급사가 '본사' 인가?
		
		if (isHqSeller) {
			$('#deliveryType option').eq(2).prop('disabled', true);		// 업체 배송 disabled
			$('#deliveryType').val('1'); // 본사배송
			
			
		} else {
			$('#deliveryType option').eq(2).prop('disabled', false);
					
		}
	}
	
	
	// 배송정보 설정.
	function setShipmentInformation() {
	
		var deliveryType = $('#deliveryType').val();
		var sellerId = $('#sellerId').val();
		
		
		// 1. 배송비 종류 노출 설정.
		showHideShippingType();
	
		// 2. 배송비 설정 정보 초기화
		$('.shipping-option input[name=shippingType]').prop('checked', false);
		$('#shipmentGroupCode').val('');
		
		clearShipmentInfo();
		clearShipmentReturnInfo();

		if (sellerId == '' || (sellerId == '' && deliveryType == '2')) { // 업체배송인데 공급사가 없는 경우 
			clearSellerInfo();
			return;
		} 
		
		if (deliveryType == '1') {
			sellerId = '${shopContext.hqSellerId}';
		}
		
		$.post('/opmanager/item/seller-info/' + sellerId, {}, function(response) {
			Common.responseHandler(response, function(response) {
				var shipment = response.data.shipment;
				var shipmentReturn = response.data.shipmentReturn;
				var seller = response.data.seller;
				
				if (seller != null) {
					//$('#vnCode').val(seller.vnCode);
					
					var shipping = Common.numberFormat(seller.shipping);
					var $shippingType2 = $('.shipping-type-2');
					$shippingType2.find('.opt-shipping-text').text(shipping);
					$shippingType2.find('.opt-shipping').val(seller.shipping);
					$shippingType2.find('.opt-shipping-free-amount').val(seller.shippingFreeAmount);
					$shippingType2.find('.opt-shipping-extra-charge1').val(seller.shippingExtraCharge1);
					$shippingType2.find('.opt-shipping-extra-charge2').val(seller.shippingExtraCharge2);
					$shippingType2.find('.opt-shipping-free-amount-text').text(Common.numberFormat(seller.shippingFreeAmount));
					$('.seller-empty').hide();
					$('.seller-info').show();
					
					// mdid 자동
					<c:if test="${isSellerPage && item.itemId == 0}"> <%-- 관리자 상품 등록인 경우에만 --%>
						$('#mdId').val(seller.mdId);
					</c:if>
									
				} else {
					clearSellerInfo();
				}
				if (shipment != null) {
					var shipping = Common.numberFormat(shipment.shipping);
					var $shippingType3 = $('.shipping-type-3');
					$shippingType3.find('.opt-shipping-text').text(shipping);
					$shippingType3.find('.opt-shipping').val(shipment.shipping);
					$shippingType3.find('.opt-shipping-free-amount').val(shipment.shippingFreeAmount);
					$shippingType3.find('.opt-shipping-extra-charge1').val(shipment.shippingExtraCharge1);
					$shippingType3.find('.opt-shipping-extra-charge2').val(shipment.shippingExtraCharge2);
					$shippingType3.find('.opt-shipping-free-amount-text').text(Common.numberFormat(shipment.shippingFreeAmount));
	
				
					$('#shipmentId').val(shipment.shipmentId);
					$('#shipmentAddress').val(shipment.fullAddress);
					$('#shipmentGroupCode').val(shipment.shipmentGroupCode);
					$('.shipment-empty').hide();
					$('.shipment-info').show();
					
				} else {
					clearShipmentInfo();
				}
				
				if (shipmentReturn != null) {
				
					$('#shipmentReturnId').val(shipmentReturn.shipmentReturnId);
					$('#shipmentReturnAddress').val(shipmentReturn.shipmentReturnAddress);
					
				} else {
					clearShipmentReturnInfo();
				}
			});
		});
		
	}
	
	
	
	// 택배사 선택
	$('#deliveryCompanyId').on('change', function() {
		$('#deliveryCompanyName').val($(this).find('option:selected').text());
	});
	
	// 배송비 설정.
	$('input[name=shippingType]').on('click', function() {
		setShippingType();
		setValidatorCondition();
	});
	
	// 출고지/배송비 변경 팝업.
	$('.change-shipment-address').on('click', function(e) {
		e.preventDefault();
		var popupUrl = '${requestContext.managerUri}/shipment/list-popup';
		
		
		var $deliveryType = $('#deliveryType');
		if ($deliveryType.val() == '') {
			alert('배송구분을 선택해 주세요.');
			$deliveryType.focus();
			return;
		} else if ($deliveryType.val() == '2') {
			var $sellerId = $('#sellerId');
			if ($sellerId.val() == '') {
				alert('판매자(공급사)를 선택해 주세요.');
				return;
			} 
			
			popupUrl = '${requestContext.managerUri}/shipment/list-popup/' + $sellerId.val();
		}
		
		Common.popup(popupUrl, 'shipment_popup', 980, 750, 1);
	});
	
	// 교환반품 주소 변경 팝업.
	$('.change-shipment-return-address').on('click', function(e) {
		e.preventDefault();
		var popupUrl = '${requestContext.managerUri}/shipment-return/list-popup';
		
		
		var $deliveryType = $('#deliveryType');
		if ($deliveryType.val() == '') {
			alert('배송구분을 선택해 주세요.');
			$deliveryType.focus();
			return;
		} else if ($deliveryType.val() == '2') {
			var $sellerId = $('#sellerId');
			if ($sellerId.val() == '') {
				alert('판매자(공급사)를 선택해 주세요.');
				return;
			} 
			
			popupUrl = '${requestContext.managerUri}/shipment-return/list-popup/' + $sellerId.val();
		}
		Common.popup(popupUrl, 'shipment_return', 980, 750, 1);
	});
	
	// 배송비 종류 노출 설정.
	function showHideShippingType() {
		
		if ($('#deliveryType').val() == '2') {
			$('.shipping-type-2').show();
		} else {
			$('.shipping-type-2').hide();
		}
	}

	
	// 배송비 종류 선택 처리.
	function setShippingType() {
	
		var dbShippingType = '${item.shippingType}';
		var $shippingType = $('input[name=shippingType]:checked');
		var shippingType = $shippingType.val();

		if (shippingType == '1') {
		
		} else if (shippingType == '3') {
			if ($('#shipmentId').val() == 0) {
				alert('출고지를 선택해 주십시오.');
				$('.change-shipment-address').eq(0).focus();
				return false;
			}
		}

		$('.shipping-option tr:gt(3)').find('.opt-shipping').val('').prop('readonly', true);
		$('.shipping-option tr:eq(4)').find('.opt-shipping-free-amount').val('').prop('readonly', true);
		
		if (Number(shippingType) == 5) {
			$('#shippingItemCount').prop('readonly', false);
		} else {
			$('#shippingItemCount').prop('readonly', true);
		}
			
			
		if (Number(shippingType) >= 4) {
			$shippingType.closest('tr').find('.opt-shipping, .opt-shipping-free-amount').prop('readonly', false);
			
			
			
			if (dbShippingType == shippingType) {
				$shippingType.closest('tr').find('.opt-shipping').val($('#shipping').val());
				
				if (shippingType == 4) {
					$shippingType.closest('tr').find('.opt-shipping-free-amount').val($('#shippingFreeAmount').val());
				}
			}
		}
		
		// 제주/도서산간 추가 배송비
		if (shippingType == '2' || shippingType == '3') {
			$('#shippingExtraCharge1, #shippingExtraCharge2').prop('readonly', true);
			var shippingExtraCharge1 = $shippingType.closest('tr').find('.opt-shipping-extra-charge1').val();
			var shippingExtraCharge2 = $shippingType.closest('tr').find('.opt-shipping-extra-charge2').val();
			
			$('#shippingExtraCharge1').val(Common.numberFormat(shippingExtraCharge1));
			$('#shippingExtraCharge2').val(Common.numberFormat(shippingExtraCharge2));
		
		} else {
			$('#shippingExtraCharge1, #shippingExtraCharge2').prop('readonly', false);
		} 
	}
}
	
// 상품정보고시 관련 이벤트 핸들러.
function initItemNoticeEvent() {
	var DEFAULT_MESSAGE = '상세정보 별도표기';
	
	setItemNotice();
	
	// 상품유형 선택 시.
	$('#itemNoticeCode').on('change', function() {
		setItemNotice();
	});
	
	$('#item_info_area').on('click', '.check-all-item-notice', function() {
		var $itemInfoDescriptions = $(this).closest('table').find('input[name=itemInfoDescriptions]');
		var $itemNoticeCheckboxies = $(this).closest('table').find('.check-item-notice');
		
		if ($(this).prop('checked')) {
			$itemInfoDescriptions.prop('readonly', true).val(DEFAULT_MESSAGE);
			$itemNoticeCheckboxies.prop('checked', true);
		} else {
			$itemInfoDescriptions.prop('readonly', false).val('');
			$itemNoticeCheckboxies.prop('checked', false);
		}
	});
	
	$('#item_info_area').on('click', '.check-item-notice', function() {
		var $itemInfoDescription = $(this).closest('tr').find('input[name=itemInfoDescriptions]');
		if ($(this).prop('checked')) {
			$itemInfoDescription.prop('readonly', true).val(DEFAULT_MESSAGE);
		} else {
			$itemInfoDescription.prop('readonly', false).val('');
		}
	});
	
	function setItemNotice() {
		var itemNoticeCode = $('#itemNoticeCode').val();
		
		if (itemNoticeCode == '') {
			$('#item_info_area tr:gt(0)').remove();
			$('.check-all-item-notice-label').hide();
			
		} else {
			$('.check-all-item-notice-label').show();
			
			$.post('${requestContext.managerUri}/item/item-notice-list', {'itemNoticeCode': itemNoticeCode}, function(response) {
				Common.responseHandler(response, function() {
				
					var html = '';
					for (var i = 0; i < response.data.length; i++) {
						var itemNotice = response.data[i];
						
						html += '	<tr>';
						html += '		<td class="label">' + itemNotice.noticeTitle + '<input type="hidden" name="itemInfoTitles" value="' + itemNotice.noticeTitle + '" /></td>';
						html += '		<td>';
						html += '			<div>';
						
						if (itemNotice.noticeTitle != itemNotice.noticeDescription) {
							html += '			<p class="text-info text-sm">* ' + itemNotice.noticeDescription + '</p>';
						
						}
						html += '				<input type="text" name="itemInfoDescriptions" maxlength="100" class="required" title="' + itemNotice.noticeTitle + '" />';
						html += '				<label><input type="checkbox" class="check-item-notice" /> 상세정보 별도표기</label>';
						html += '			</div>';
						html += '		</td>';
						html += '	</tr>';
						
					}
					
					var $itemInfoArea = $('#item_info_area');
					$itemInfoArea.find('tr:gt(0)').remove();
					$itemInfoArea.append(html);
					
					
					// 모두 선택이 체크된 경우.
					if ($('.check-all-item-notice').prop('checked')) {
						
					}
					
					
					// 등록된 데이터가 있는 경우.
					var $itemNoticeData = $('#item-notice-data p');
					var selectedItemNoticeCode = '${item.itemNoticeCode}';
					
					if ($itemNoticeData.size() > 0 && selectedItemNoticeCode == itemNoticeCode) {
					
						$itemNoticeData.each(function(i) {
							var tit = $(this).find('span.title').text();
							var desc = $(this).find('span.desc').text();
							
							$itemInfoArea.find('tr').each(function() {
								var $target = $(this);
								var $itemInfoTitles = $(this).find('input[name=itemInfoTitles]');
								
								
								if ($itemInfoTitles.val() == tit) {
									$target.find('input[name=itemInfoDescriptions]').val(desc);			
							
									if (desc == DEFAULT_MESSAGE) {		
										$target.find('input[name=itemInfoDescriptions]').prop('readonly', true);
										$target.find('input.check-item-notice').prop('checked', true);
									}		
								}
							});
						});
					}
				});
			});
			
		}
	}

}

// 상품 승인 처리 이벤트 
function initItemApprovalEvent() {
	var $btnApproval = $('.btn-approval');
	
	if ($btnApproval.size() == 0) {
		return;
	}
	
	$btnApproval.on('click', function() {
		if (!confirm('승인 처리를 하시겠습니까?\n(상품 정보는 수정되지 않습니다.)')) {
			return;
		}
		
		$.post('/opmanager/item/edit/seller-item-approval/${item.itemId}', null, function(response){
			
			Common.responseHandler(response, function(response) {
				
				alert('승인이 완료 되었습니다.');
				location.href = '/opmanager/item/seller/list';
				
			}, function(response){
				
				alert(response.errorMessage);
				
			});
			
		}, 'json');
		
	});
}


// 할인 / 포인트 설정 이벤트 핸들러 
function initDiscountAndPointEvent() {
	init();
	
	$('input[name=sellerDiscountFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
	
	$('input[name=spotFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
	
	$('input[name=sellerPointFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});

	function init() {
		showHideContent($('input[name=sellerDiscountFlag]:checked'));
		showHideContent($('input[name=spotFlag]:checked'));
		showHideContent($('input[name=sellerPointFlag]:checked'));
		
		// 스팟 (운영자 스팟할인 진행시 판매자 수정불가)
		var spotType = $('#spotType').val();
		var isSellerLogin = '${sellerContext.login}';
		if (spotType == '1' && isSellerLogin == 'true') {
			$('#spot-area').find('input, select').prop('disabled', true);
			$('#spot-area').find('button').hide();
		}
	}
}

// .hide_content Show / Hide
function showHideContent($selector) {
	var flag = $selector.val();
	var $content = $selector.closest('div').find('.hide_content');
	
	if (flag == 'Y') {
		$content.show();
	} else {
		$content.hide();
	}
}


// 상품 카테고리 추가
function addItemCategory() {
	var breadcrumb = '';
	var categoryId = '';
	$('select.category').each(function(index) {
		

		var $selectedOption = $(this).find('option:selected');
		if ($selectedOption.size() > 0) {
			
			// 팀/그룹
			if (index == 0) {
				breadcrumb = $selectedOption.parent().attr('label');
			}
			
			
			categoryId = $selectedOption.attr('rel');
			breadcrumb += ' > ' + $selectedOption.text();
		}
		
	});
	
	
	if (categoryId == undefined || categoryId == '') {
		alert(Message.get("M00078"));	// 상품 카테고리를 선택해 주세요.
		$('#category_team_group').focus();
		return;
	}
	
	
	//alert(categoryId + '  : ' + breadcrumb);
	
	
	// 중복 체크
	var itemDuplicationCount = 0;
	$('input[name=categoryIds]').each(function() {
		if ($(this).val() == categoryId) {
			itemDuplicationCount++;
		}
	});
	
	if (itemDuplicationCount > 0) {
		alert(Message.get("M00079"));	// 이미 등록된 카테고리입니다.
		return;
	}
	
	
	var html = '';
	html += '	<li id="item_category_' + categoryId + '">' + breadcrumb;
	html += '		<a href="javascript:deleteItemCategory(' + categoryId + ')" class="delete">[' + Message.get("M00074") + ']</a>'; // 삭제
	html += '		<input type="hidden" name="categoryIds" value="' + categoryId + '" />';
	html += '	</li>';
	
	
	$('#item_categories').find('li.nothing').remove();
	$('#item_categories').append(html);
	
}

function deleteItemCategory(categoryId) {
	$('#item_category_' + categoryId).remove();
	
	var $itemCategories = $('#item_categories');
	if ($itemCategories.find('li').size() == 0) {
		$itemCategories.append('<li>' + Message.get("M00077") + '</li>'); // 등록된 상품 카테고리가 없습니다.
	}
}

// 포인트 설정 정보 노출여부
function displayItemPointConfig() {
	if ($('#item_point_area tr').size() == 0) {
		$('#item_point_config').hide();
	} else {
		$('#item_point_config').show();
	}
	
}

// 포인트 설정 정보 추가
function addItemPoint() {
	var optionTemplate = $('#item_point_template tbody').html();
	
	
	var randomKey = Math.floor(Math.random() * 10000) + 1;
	
	var radioButtons = '';
	radioButtons += '<input type="radio" name="R' + randomKey + '" id="R' + randomKey + '1" checked="checked"> <label for="R' + randomKey + '1">%</label>';
	radioButtons += '<input type="radio" name="R' + randomKey + '" id="R' + randomKey + '2"> <label for="R' + randomKey + '2">P</label>';
	
	
	$('#item_point_area').append(optionTemplate.replace('{INPUT_RADIO}', radioButtons));
	displayItemPointConfig();
	
	var $item = $('#item');
	$item.find('.ui-datepicker-trigger').remove();
	$item.find("input.term").each(function() {
		$(this).removeClass('hasDatepicker').datepicker(); 
	});
}

// 옵션추가
function addItemOption() {
	var optionTemplate = $('#item_option_template tbody').html();
	var randomKey = Math.floor(Math.random() * 10000) + 1;
	
	var radioButtons = '';
	
	radioButtons += '<label><input type="radio" name="optionDisplayTypeRadio' + randomKey + '" value="radio" class="option_display_type" checked="checked" /> 라디오버튼</label>';
	radioButtons += '<label><input type="radio" name="optionDisplayTypeRadio' + randomKey + '" value="select" class="option_display_type" /> 셀렉트박스</label>';
	
	
	$('#option_data').hide();
	$('#item_option_area tbody').append(optionTemplate.replace('{INPUT_RADIO}', radioButtons));
}




// validator 체크 조건 설정.
function setValidatorCondition() {
	var currentClasses = "PREFIX";
	var targetFlag = '';
	

	// 비회원 판매가격
	targetFlag = $('input[name=salePriceNonmemberFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-nonmember-price';
	}
	
	// 상품재고
	targetFlag = $('input[name=stockFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-stock-quantity';
	}
	
	// 사은품 정보
	targetFlag = $('input[name=freeGiftFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-free-gift';
	}
	
	
	// 즉시할인 
	targetFlag = $('input[name=sellerDiscountFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-seller-discount-amount';
	}
	
	
	// 스팟할인
	targetFlag = $('input[name=spotFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-spot';
	}
	
	// 포인트 지급
	targetFlag = $('input[name=sellerPointFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-seller-point';
	}
	
	// 옵션
	targetFlag = $('input[name=itemOptionFlag]:checked').val();
	
	if (targetFlag == 'Y') {
		
		// 3조합 
		targetFlag = $('input[name=itemOptionType]:checked').val();
		if (targetFlag == 'S3') {
			currentClasses += ',required-item-option';
			currentClasses += ',required-item-option-s3';
		
		} else if (targetFlag == 'T') {
			currentClasses += ',required-item-option-text';
		
		} else {
			currentClasses += ',required-item-option';
			
		}
	}
	
	// 추가구성 상품.
	targetFlag = $('input[name=itemAdditionFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-item-addition';
	}
	
	// 배송비 설정.
	targetFlag = $('input[name=shippingType]:checked').val(); 
	if (targetFlag == '4') {
		currentClasses += ',required-shipping-4';
	}
	if (targetFlag == '5') {
		currentClasses += ',required-shipping-5';
	}
	if (targetFlag == '6') {
		currentClasses += ',required-shipping-6';
	}
	
	
	currentClasses = currentClasses.replace('PREFIX,', '');
	currentClasses = currentClasses.replace('PREFIX', '');

	$.validator.currentClass = currentClasses;
	
}


function setRelationItemDisplay() {
	var relationItemDisplayType = $('input[name=relationItemDisplayType]:checked').val();
	if (relationItemDisplayType == '1') {
		$('#relation_item_area').hide();
		
	} else {
		$('#relation_item_area').show();
		
	}
	
}

// 상품이미지 삭제 
function deleteItemImage(type, id) {
	var message = Message.get("M00755"); // 이미지가 실제로 삭제됩니다.(복구불가)\n삭제하시겠습니까?
	if (!confirm(message)) {
		return;
	} 
	
	
	if (type == "main") {
		var param = {'itemId': id};
		$.post('${requestContext.managerUri}/item/delete-item-image', param, function(response){
			Common.responseHandler(response);
			$('.item_image_main').remove();
		});	
	} else if (type == "details") {
		var param = {'itemImageId': id};
		$.post('${requestContext.managerUri}/item/delete-item-details-image', param, function(response){
			Common.responseHandler(response);
			$('#item_image_id_' + param.itemImageId).remove();
		});	
	}
	
}


// 상세설명 - 추가/삭제 이벤트 핸들러 
/*
function itemInfoEventHandler() {
	// 기본으로 2개 보임.
	if ($('#item_info_area tr').size() == 0) {
		for (var i = 0; i < 1; i++) {
			addItemInfo();
		}
	}
	
	if ($('#item_info_mobile_area tr').size() == 0) {
		for (var i = 0; i < 1; i++) {
			addItemInfoMobile();
		}
	}
	
	
	// textarea resize - load
	$('#item_info_area textarea, #item_info_mobile_area textarea').each(function() {
		$(this).css('height','auto');
		$(this).height(this.scrollHeight < 30 ? 20 : this.scrollHeight);
	});
	
	// textarea resize - event
	$(document).on('keyup', '#item_info_area textarea, #item_info_mobile_area textarea' ,function(){
		$(this).css('height','auto');
		$(this).height(this.scrollHeight < 30 ? 20 : this.scrollHeight);
    });	
	
	// 항목 삭제 이벤트.
	$(document).on('click', '#item_info_area .delete_item_info' ,function(e) {
		e.preventDefault();
		
		if ($('#item_info_area tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input, textarea').val('');
			$(this).closest('tr').find('textarea').height(20);
		}
    });	
	
	// 항목 삭제 이벤트.
	$(document).on('click', '#item_info_mobile_area .delete_item_info' ,function(e) {
		e.preventDefault();
		
		if ($('#item_info_mobile_area tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input, textarea').val('');
			$(this).closest('tr').find('textarea').height(20);
		}
    });	
}
*/
// 상세설명 - 항목추가
function addItemInfo() {
	var html = '';
	html += '	<tr>';
	html += '		<td class="label"><input type="text" name="itemInfoTitles" maxlength="50" /></td>';
	html += '		<td>';
	html += '			<div>';
	html += '				<textarea name="itemInfoDescriptions" maxlength="500" rows="1"></textarea>';
	html += '			</div>';
	html += '		</td>';
	html += '		<td class="middle"><a href="#" class="fix_btn delete_item_info">' + Message.get("M00074") + '</a></td>';	// 삭제
	html += '	</tr>';
	
	$('#item_info_area').append(html);
}

//상세설명 - 항목추가
function addItemInfoMobile() {
	var html = '';
	html += '	<tr>';
	html += '		<td class="label"><input type="text" name="itemInfoMobileTitles" maxlength="50" /></td>';
	html += '		<td>';
	html += '			<div>';
	html += '				<textarea name="itemInfoMobileDescriptions" maxlength="500" rows="1"></textarea>';
	html += '			</div>';
	html += '		</td>';
	html += '		<td class="middle"><a href="#" class="fix_btn delete_item_info">' + Message.get("M00074") + '</a></td>';	// 삭제
	html += '	</tr>';
	
	$('#item_info_mobile_area').append(html);
}


function copyContent(id) {
	
	if (id == 'itemInfo') {
		var source = $('#item_info_area').html();
		source = source.replace(/itemInfoTitles/g, 'itemInfoMobileTitles');
		source = source.replace(/itemInfoDescriptions/g, 'itemInfoMobileDescriptions');
		
		$('#item_info_mobile_area').empty().append(source);
		
		$('input[name=itemInfoTitles]').each(function(i) {
			var sourceTitle = $('input[name=itemInfoTitles]').eq(i).val();
			var sourceDescription = $('#item_info_area textarea').eq(i).val();
			
			$('input[name=itemInfoMobileTitles]').eq(i).val(sourceTitle);
			$('#item_info_mobile_area textarea').eq(i).val(sourceDescription);
		});
		return;
	}
	
	var sourceHtml = editors.getById[id].getIR();
	editors.getById[id + "Mobile"].exec("SET_CONTENTS", ['']); 
	editors.getById[id + "Mobile"].exec("PASTE_HTML", [sourceHtml]);
	
	//oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
}


function scrollButtons() {
	$(window).scroll(function () {
		var st = $(window).scrollTop();
		var scrollBottom = $(document).height() - $(window).height() - $(window).scrollTop();
		
		if (scrollBottom < 170) {
			$('#buttons').removeClass('fixed_button');
		} else {
			$('#buttons').addClass('fixed_button');
		}
	});
}

// 재고 연동 여부에 따른 수량입력 show/hide 이벤트
function initStockFlagEvent() {
	
	$('input[name=stockFlag]').on('click', function() {
		var stockFlag = $('input[name=stockFlag]:checked').val();
		var $trStockQuantity = $('#trStockQuantity');
	
		if (stockFlag == 'Y') {
			$trStockQuantity.show();
		} else {
			$trStockQuantity.hide();
			$trStockQuantity.val('');
		}
		
		setValidatorCondition();
	});
	
}


// 출고지 정보 팝업 콜백 핸들러 
function handleShipmentPopupCallback(shipment) {
	alert(shipment.shipmentGroupCode);
	$('#shipmentId').val(shipment.shipmentId);
	$('#shipmentAddress').val('[' + shipment.zipcode + '] ' + shipment.address + ' ' + shipment.addressDetail);
	$('#shipmentGroupCode').val(shipment.shipmentGroupCode);
	
	var $shippingType3 = $('.shipping-type-3');
	$shippingType3.find('.opt-shipping').val(shipment.shipping);
	$shippingType3.find('.opt-shipping-text').text(Common.numberFormat(shipment.shipping));
	$shippingType3.find('.opt-shipping-free-amount').val(shipment.shippingFreeAmount);
	$shippingType3.find('.opt-shipping-free-amount-text').text(Common.numberFormat(shipment.shippingFreeAmount));
	$shippingType3.find('.opt-shipping-extra-charge1').val(shipment.shippingExtraCharge1);
	$shippingType3.find('.opt-shipping-extra-charge2').val(shipment.shippingExtraCharge2);
	
	$('.shipment-empty').hide();
	$('.shipment-info').show();
}	

function handleShipmentReturnPopupCallback(shipmentReturn) {
	$('#shipmentReturnId').val(shipmentReturn.shipmentReturnId);
	$('#shipmentReturnAddress').val('[' + shipmentReturn.zipcode + '] ' + shipmentReturn.address + ' ' + shipmentReturn.addressDetail);
	

}


// 판매자 선택 이벤트 핸들러.
function initSellerIdEvent() {
	
}


// 판매자조건부 정보 초기화
function clearSellerInfo() {
	var $shippingType2 = $('.shipping-type-3');
	$shippingType2.find('.opt-shipping-text').text("0");
	$shippingType2.find('.opt-shipping').val("0");
	$shippingType2.find('.opt-shipping-free-amount').val("0");
	$shippingType2.find('.opt-shipping-extra-charge1').val("0");
	$shippingType2.find('.opt-shipping-extra-charge2').val("0");
	
	$('.seller-empty').show();
	$('.seller-info').hide();
}


// 출고지 정보 초기화
function clearShipmentInfo() {
	var $shippingType3 = $('.shipping-type-3');
	$shippingType3.find('.opt-shipping-text').text("0");
	$shippingType3.find('.opt-shipping').val("0");
	$shippingType3.find('.opt-shipping-free-amount').val("0");
	$shippingType3.find('.opt-shipping-free-amount-text').text("0");
	$shippingType3.find('.opt-shipping-extra-charge1').val("0");
	$shippingType3.find('.opt-shipping-extra-charge2').val("0");
	
	$('#shipmentId').val(0);
	$('#shipmentAddress').val('');
	
	$('#shippingExtraCharge1').val('0');
	$('#shippingExtraCharge2').val('0');
	
	$('.shipment-empty').show();
	$('.shipment-info').hide();
}
// 반송지 정보 초기화
function clearShipmentReturnInfo() {
	$('#shipmentReturnId').val(0);
	$('#shipmentReturnAddress').val("");
}

function findMd(targetId) {
	Common.popup('/opmanager/seller/find-md?targetId=' + targetId, 'find_md', 720, 800, 1);
}

function clearMd(targetId) {
	var $target = $('#' + targetId);
	$target.val('');
	$target.closest('td').find('#mdName').val('');
}

// MD 검색 콜백 
function handleFindMdCallback(response) {
	var $target = $('#' + response.targetId);
	$target.val(response.userId);
	$target.closest('td').find('#mdName').val(response.userName);

}

</script>