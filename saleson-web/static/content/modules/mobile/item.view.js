$(function () {

	// 상품 수량 체크 이벤트 (옵션 없는 경우)
	initItemQuantityEvent();

	// 상품 일반옵션 선택 추가 이벤트
	initItemOptionEvent();

	// 상품 옵션조합형 옵션 선택 추가 이벤트
	initItemCombinationOptionEvent();

	// 추가 구성 상품 선택 추가 이벤트
	initItemAdditionEvent();

	// 최초계산
	calculate();

	// 리뷰조회
	paginationReviewMore(1);

	// QNA조회
	paginationQnaMore(1);

	updateItemHits();

	viewItemRelations();
	setBenefitInfo();
	setCustomerInfo();

});

//상품 조회 수 업데이트.
function updateItemHits() {
	$.post('/common/update-item-hits', {'itemId': item.itemId}, function () {
	});
}

// 상품 수량 체크 이벤트 (옵션이 없는 경우)
function initItemQuantityEvent() {
	var $itemQuantity = $('.op-item-quantity');

	if ($itemQuantity.length > 0) {

		// 상품 수량 +
		$itemQuantity.find('button.op-item-order-count-plus').on('click', function (e) {
			e.preventDefault();
			var $quantity = $('.quantity');
			var quantity = Number($quantity.val());
			var addedQuantity = quantity + 1;

			if (quantity == item.orderMaxQuantity) {
				alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
				return;
			}

			if (addedQuantity > 999) {
				alert("해당 상품의 최대 구매 수량은 999개 입니다.");
				return;
			}

			// 재고 체크
			if (!checkStockQuantity(addedQuantity)) {
				return;
			}

			$quantity.val(addedQuantity);

			// append ArrayRequiredItems
			setArrayRequiredItems(item.itemId, addedQuantity);

			calculate();
		});

		// 상품 수량 -
		$itemQuantity.find('button.op-item-order-count-minus').on('click', function (e) {
			e.preventDefault();
			var $quantity = $('.quantity');
			var quantity = Number($quantity.val());
			var minusQuantity = quantity - 1;
			var $defaultQuantity = 1;

			if (item.orderMinQuantity > 0) {
				$defaultQuantity = item.orderMinQuantity;
			}

			if (quantity == $defaultQuantity) {
				alert('해당 상품의 최소 구매 수량은 ' + $defaultQuantity + "개 입니다.");
				return;
			}

			$quantity.val(minusQuantity);

			// append ArrayRequiredItems
			setArrayRequiredItems(item.itemId, minusQuantity);

			calculate();
		});

		// 상품 수량 입력.
		$('.quantity').on('blur', function (e) {
			var $quantity = $(this);
			var $defaultQuantity = 1;
			var quantity = Number($quantity.val());

			try {
				quantity = Number($(this).val());
			} catch (e) {}

			if (item.orderMinQuantity > 0) {
				$defaultQuantity = item.orderMinQuantity;
			}

			// 최소구매 수량 체크
			if (item.orderMaxQuantity > 0 && quantity < $defaultQuantity) {
				alert('해당 상품의 최소 구매 수량은 ' + $defaultQuantity + "개 입니다.");
				$quantity.val(item.orderMinQuantity);

				// append ArrayRequiredItems
				setArrayRequiredItems(item.itemId, $defaultQuantity);

				calculate();
				return;
			}

			// 최대구매 수량 체크.
			if (quantity > item.orderMaxQuantity) {
				alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
				$quantity.val(item.orderMaxQuantity);

				calculate();
				return;
			}

			// 재고 체크
			if (!checkStockQuantity(quantity)) {
				return;
			}

			// append ArrayRequiredItems
			setArrayRequiredItems(item.itemId, quantity);

			calculate();
		}).on("keyup", function (e) {
			var pattern = /^[0-9]+$/;
			var quantity = 1;

			if (item.orderMinQuantity >= 0) {
				quantity = item.orderMinQuantity;
			}

			if (!pattern.test($(this).val())) {
				$(this).val(quantity);
				calculate();
				return;
			}

			var quantity = Number($(this).val());
			if (quantity > item.orderMaxQuantity) {
				alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
				$(this).val(item.orderMaxQuantity);
				calculate();
			}
		});
	}

	function checkStockQuantity(quantity) {
		if (item.itemOptionFlag == 'N') {	// 일반상품 재고 체크
			var stockFlag = item.stockFlag;
			var stockQuantity = item.stockQuantity;

			if (stockFlag == 'Y' && quantity > stockQuantity) {
				alert('해당 상품의 최대 구매 수량은 ' + stockQuantity + '개 입니다.');
				$('.quantity').val(stockQuantity);
				calculate();
				return false;
			}
		} else {							// 옵션조합형 상품 재고 체크
			// 옵션그룹 재고 오름차순 정렬
			SELECTED_COMBINATION_OPTION_INFOS.sort(function(a, b) {
				return a.optionStockQuantity < b.optionStockQuantity ? -1 : a.optionStockQuantity > b.optionStockQuantity ? 1 : 0;
			});

			// 낮은 재고 -> 높은 재고순으로 검사
			for (var i = 0; i < SELECTED_COMBINATION_OPTION_INFOS.length; i++) {

				var optionName = SELECTED_COMBINATION_OPTION_INFOS[i].optionName;
				var stockFlag = SELECTED_COMBINATION_OPTION_INFOS[i].optionStockFlag;
				var stockQuantity = SELECTED_COMBINATION_OPTION_INFOS[i].optionStockQuantity;

				if (stockFlag == 'Y' && quantity > stockQuantity) {
					alert(optionName + '의 최대 구매 수량은 ' + stockQuantity + '개 입니다.');
					$('.quantity').val(stockQuantity);
					calculate();
					return false;
				}
			}
		}

		return true;
	}
}

// 상품 옵션 선택 추가 이벤트
function initItemOptionEvent() {
	var SELECTED_OPTION_IDS = [];

	// init
	if (item.itemOptionFlag == 'N' || item.itemOptionType == 'C') {
		return;
	}

	// 옵션 생성.
	initItemOption();

	// 전체 옵션 보기 버튼.
	displayAllOptionButton();

	// 상품필수옵션 셀렉트박스 클릭
	$('.op-option-select-box').on('click', function (e) {
		e.preventDefault();

		// 최대 구매 수량 체크
		var currentQuantity = getCurrentItemQuantity();

		if (currentQuantity == item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			return;
		}

		// 옵션 선택 초기화.
		initItemOption();

		$('.op-option-box').hide();
		$(this).closest('div').find('.op-option-box').show();
		$(this).closest('div').find('.op-option-box ul.option_list02').hide().eq(0).show();

	});

	// 옵션 선택 박스 닫기
	$('.op-close-option-box').on('click', function (e) {
		e.preventDefault();

		$('.op-option-box').hide();

		// 옵션 선택 초기화
		initItemOption();
	});

	// 세부 옵션 선택 이벤트
	$('.op-item-option-info .op-option-box').on('click', '.op-option-group li a', function (e) {
		e.preventDefault();

		var $optionBoxType = $(this).closest('.op-option-group').parent();
		var $optionGroup = $(this).closest('.op-option-group');
		var currentOptionGroupIndex = $optionBoxType.find('.op-option-group').index($optionGroup);
		var optionGroupCount = $optionBoxType.find('.op-option-group').length;
		var optionName = $(this).attr('data-option-name');

		// 옵션 선택 처리.
		$(this).addClass('on');
		$optionGroup.find('.option-selected-name').text(optionName);
		$optionGroup.find('.option-selected-name').addClass('sel');

		var optionSelectedCount = $optionBoxType.find('a.on').length;

		// 모든 옵션을 선택한 경우.
		if (optionGroupCount == optionSelectedCount) {
			completeSelectOption();
		} else {
			displayNextOptionGroup(currentOptionGroupIndex);
		}

		// 다음 옵션 보기.
		function displayNextOptionGroup(currentIndex) {
			var index = currentIndex + 1;

			// S2,S3는 다음 옵션을 조회해서 가져와야 한다.
			if (item.itemOptionType != 'S') {

				// 이전 까지 선택한 옵션명 가져오기.
				var optionNames = [];
				var i = 0;
				$optionBoxType.find('.op-option-group:lt(' + index + ')').each(function () {
					optionNames[i] = $(this).find('a.on').attr('data-option-name');
					i++;
				});

				// next 옵션 목록
				var optionHtml = '';
				var lastOptionValue = '';
				for (var i = 0; i < itemOptions.length; i++) {
					if (itemOptions[i].optionType == 'T') {
						continue;
					}

					var step = optionNames.length + 1;
					var optionValue = '';

					if (optionNames.length == 1 && itemOptions[i].optionName1 == optionNames[0]) {
						optionValue = itemOptions[i].optionName2;

					} else if (optionNames.length == 2 && itemOptions[i].optionName1 == optionNames[0] && itemOptions[i].optionName2 == optionNames[1]) {
						optionValue = itemOptions[i].optionName3;

					} else {
						continue;
					}

					if (optionValue != lastOptionValue) {

						// 마지막 옵션인 경우 옵션 정보까지 보여 줘야 함.
						if (step == optionGroupCount) {
							optionHtml += '		<li>' + getOptionInfo(itemOptions[i], step) + '</li>';
						} else {
							optionHtml += '		<li><a href="javascript:;" data-option-name="' + optionValue + '">' + getOptionImage(optionValue) + ' ' + optionValue + '</a><li>';
						}

						lastOptionValue = optionValue;
					}

				}

				// next 옵션 목록 추가.
				$optionBoxType.find('.op-option-group').eq(index).find('ul').append(optionHtml);
				$optionBoxType.find('.op-option-group').eq(index).find('.option-selected-name').text('');
				$optionBoxType.find('.op-option-group').eq(index).find('.option-selected-name').removeClass('sel');
			}

			$optionBoxType.find('.op-option-group').eq(currentIndex).find('ul').hide();
			$optionBoxType.find('.op-option-group').eq(index).find('ul').show();
		}

		// 옵션 선택 완료
		function completeSelectOption() {
			SELECTED_OPTION_IDS = [];
			var index = 0;
			var optionAdditionalAmount = 0;
			var optionNames = '';
			$optionBoxType.find('a.on').each(function (i) {
				var optionId = $(this).attr('data-option-id');

				if (optionId !== undefined) {
					SELECTED_OPTION_IDS[index] = optionId;
					index++;
				}

				var optionName = $(this).attr('data-option-name');
				if (optionName !== undefined) {
					if (optionNames != '') {
						optionNames += ' | ';
					}
					optionNames += optionName;
				}

				var optionPrice = $(this).attr('data-option-price');
				if (optionPrice !== undefined && optionPrice != '') {
					optionAdditionalAmount += Number(optionPrice);
				}
			});

			// 텍스트 옵션이 없는 경우 바로 추가.
			if ($('.text-option-id').length == 0) {
				// 선택된 옵션으로 아이템옵션을 추가한다.
				addItemOption();
			} else {
				var additionAmountInfo = '';
				if (optionAdditionalAmount != 0) {
					additionAmountInfo = ' (' + (optionAdditionalAmount > 0 ? '+' : '') + Common.numberFormat(optionAdditionalAmount) + '원)';
				}

				var selectedOptionSummary = optionNames + additionAmountInfo;


				$('.op-option-box').hide();

				$('.op-option-select-box').text(selectedOptionSummary);
				$('.op-option-select-box').addClass('selected');

			}
		}
	}).on('mouseenter', '.option-group li a', function (e) {
		e.preventDefault();
		var optionName = $(this).attr('data-option-name');
		$(this).closest('.option-group').find('.option-selected-name').text(optionName);
		$(this).closest('.option-group').find('.option-selected-name').addClass('sel');
	}).on('mouseleave', '.option-group li a', function (e) {
		e.preventDefault();
		if (!$(this).hasClass('on')) {
			$(this).closest('.option-group').find('.option-selected-name').text('');
			$(this).closest('.option-group').find('.option-selected-name').removeClass('sel');
		}

	});


	// 상품 옵션 그룹 링크
	$('.op-option-box-type1').on('click', '.op-option-group > span', function (e) {
		e.preventDefault();

		var $optionGroup = $(this).closest('.op-option-group');
		var $optionBoxType = $('.op-option-box-type1');
		var currentOptionGroupIndex = $optionBoxType.find('.op-option-group').index($optionGroup);

		if (item.itemOptionType == 'S') {
			var $previousOptionGroups = $optionBoxType.find('.op-option-group:lt(' + currentOptionGroupIndex + ')');
			var optionGroupCount = $previousOptionGroups.length;
			var selectedOptionGroupCount = $previousOptionGroups.find('a.on').length;

			if (selectedOptionGroupCount < optionGroupCount) {
				alert('위의 정보를 먼저 선택해 주세요.');
				return;
			}

			$optionBoxType.find('ul').hide();
			$optionGroup.find('a.on').removeClass('on');
			$optionGroup.find('.option-selected-name').text('');
			$optionGroup.find('.option-selected-name').removeClass('sel');
			$optionGroup.find('ul').show();

			// 현재 이후 STEP 초기화.
			$optionBoxType.find('.op-option-group:gt(' + currentOptionGroupIndex + ')').each(function () {
				$(this).find('a.on').removeClass('on');
				$(this).find('.option-selected-name').text('위의 정보를 먼저 선택해 주세요.');
				$(this).find('.option-selected-name').addClass('sel');
			});

		} else {
			if ($optionGroup.find('li').length == 0) {
				alert('위의 정보를 먼저 선택해 주세요.');
				return;
			}

			$optionBoxType.find('ul').hide();
			$optionGroup.find('a.on').removeClass('on');
			$optionGroup.find('.option-selected-name').text('');
			$optionGroup.find('.option-selected-name').removeClass('sel');
			$optionGroup.find('ul').show();

			// 현재 이후 STEP 초기화.
			$optionBoxType.find('.op-option-group:gt(' + currentOptionGroupIndex + ')').each(function () {
				$(this).find('a.on').removeClass('on');
				$(this).find('.option-selected-name').text('위의 정보를 먼저 선택해 주세요.');
				$(this).find('.option-selected-name').addClass('sel');
				$(this).find('li').remove();
			});

		}
	});

	// 텍스트 옵션 추가
	$('.op-item-option-info').on('click', '.btn-add-item-option', function (e) {
		e.preventDefault();

		// 선택 옵션이 있는 경우
		if (item.itemOptionType != 'T' && SELECTED_OPTION_IDS.length == 0) {
			alert('상품필수옵션을 선택해 주세요.');
			$('.op-option-select-box').focus();
			return;
		}

		var errorIndex = -1;
		$('.text-option-value').each(function (index) {
			if ($.trim($(this).val()) == '') {
				errorIndex = index;
				return false;
			}

		});

		if (errorIndex > -1) {
			var $textOption = $('.text-option-value').eq(errorIndex);
			alert($textOption.attr('title') + ' 항목을 입력해 주세요.');
			$textOption.focus();
			return;
		}

		// 옵션 추가하기!!
		addItemOption();
	});

	// 옵션 구성 (초기화)
	initItemOptionEvent.initItemOption = initItemOption;
	function initItemOption() {

		if (item.itemOptionType == 'T') {
			return;
		}

		// 이전 선택 부분 초기화.
		SELECTED_OPTION_IDS = [];

		$('.op-option-select-box').removeClass('selected');

		var $itemOptionInfo = $('.op-item-option-info');
		$itemOptionInfo.find('.op-option-select-box').text('선택하세요.');
		var $optionBoxType1 = $itemOptionInfo.find('.op-option-box-type1');

		$('.op-item-option-info .op-option-group li a').removeClass('on');

		var optionGroupHtml = '';

		if (item.itemOptionType == 'S') {
			var groupTitle = '';
			for (var i = 0; i < itemOptions.length; i++) {
				if (itemOptions[i].optionType == 'T') {
					continue;
				}

				if (itemOptions[i].optionName1 != groupTitle) {
					if (i > 0) {
						optionGroupHtml += '	</ul>';
						optionGroupHtml += '</li>';
					}

					optionGroupHtml += '<li class="op-option-group">';
					optionGroupHtml += '	<span>' + itemOptions[i].optionName1 + '<b class="option-selected-name">선택하세요.</b></span>';
					optionGroupHtml += '	<ul class="option_list02">';
				}

				// 옵션항목.
				optionGroupHtml += '			<li>' + getOptionInfo(itemOptions[i]) + '</li>';

				groupTitle = itemOptions[i].optionName1;
			}

			optionGroupHtml += '	</ul>';
			optionGroupHtml += '</li>';

		} else {

			var optionGroupHtml = '';

			// option1
			optionGroupHtml += '<li class="op-option-group">';
			optionGroupHtml += '	<span>' + item.itemOptionTitle1 + '<b class="option-selected-name sel">선택하세요.</b></span>';
			optionGroupHtml += '	<ul class="option_list02">';

			var options = '';
			var optionName1 = '';
			for (var i = 0; i < itemOptions.length; i++) {
				if (itemOptions[i].optionType == 'T') {
					continue;
				}

				if (itemOptions[i].optionName1 != optionName1) {
					optionGroupHtml += '		<li><a href="javascript:;" data-option-name="' + itemOptions[i].optionName1 + '">' + getOptionImage(itemOptions[i].optionName1) + ' ' + itemOptions[i].optionName1 + '</a><li>';
				}

				optionName1 = itemOptions[i].optionName1;
			}

			optionGroupHtml += '	</ul>';
			optionGroupHtml += '</li>';

			// option2
			optionGroupHtml += '<li class="op-option-group">';
			optionGroupHtml += '	<span>' + item.itemOptionTitle2 + '<b class="option-selected-name sel">위의 정보를 먼저 선택해 주세요.</b></span>';
			optionGroupHtml += '	<ul class="option_list02">';
			optionGroupHtml += '	</ul>';
			optionGroupHtml += '</li>';

			if (item.itemOptionType == 'S3') {
				// option3
				optionGroupHtml += '<li class="op-option-group">';
				optionGroupHtml += '	<span>' + item.itemOptionTitle3 + '<b class="option-selected-name sel">위의 정보를 먼저 선택해 주세요.</b></span>';
				optionGroupHtml += '	<ul class="option_list02">';
				optionGroupHtml += '	</ul>';
				optionGroupHtml += '</li>';
			}
		}

		$optionBoxType1.find('.op-option-group').remove();
		$optionBoxType1.append(optionGroupHtml);

		$optionBoxType1.show();
	}

	// 상품 옵션 텍스트 구성
	function getOptionInfo(itemOption, step) {
		var info = '';
		var optionName = itemOption.optionName2;

		if (itemOption.optionType != 'S') {
			if (step == 1) optionName = itemOption.optionName1;
			if (step == 2) optionName = itemOption.optionName2;
			if (step == 3) optionName = itemOption.optionName3;
		}

		info += optionName;

		// 추가금.
		if (itemOption.optionPrice != 0) {
			info += ' (' + (itemOption.optionPrice > 0 ? '+' : '') + Common.numberFormat(itemOption.optionPrice) + '원)';
		}

		// 품절, 재고연동
		if (itemOption.isSoldOut == 'true' || (itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0 && item.orderMinQuantity > itemOption.optionStockQuantity)) {
			info += ' - 품절';
		} else if (itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0) {
			info += ' | 재고: ' + Common.numberFormat(itemOption.optionStockQuantity) + '개';
		}

		if (itemOption.isSoldOut == 'true' || (itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0 && item.orderMinQuantity > itemOption.optionStockQuantity)) {
			return '<span>' + getOptionImage(optionName, itemOption.itemOptionId) + ' ' + info + '</span>';
		} else {
			return '<a href="javascript:;" data-option-name="' + optionName + '" data-option-id="' + itemOption.itemOptionId + '" data-option-price="' + itemOption.optionPrice + '">' + getOptionImage(optionName, itemOption.itemOptionId) + ' ' + info + '</a>';
		}
	}

	// 옵션 이미지 가져오기
	function getOptionImage(optionName, itemOptionId) {
		// optionId가 있는 경우.
		if (itemOptionId !== undefined && itemOptionId > 0) {
			for (var i = 0; i < itemOptionImages.length; i++) {
				if (itemOptionImages[i].itemOptionId == itemOptionId) {
					return '<img src="' + itemOptionImages[i].optionImage + '" alt="' + optionName + '" />';
				}
			}
		}

		for (var i = 0; i < itemOptionImages.length; i++) {
			if (itemOptionImages[i].optionName == optionName) {
				return '<img src="' + itemOptionImages[i].optionImage + '" alt="' + optionName + '" />';
			}
		}

		return "";
	}

	// 전체 옵션 보기
	function displayAllOptionButton() {
		if (item.itemOptionType == 'S2' || item.itemOptionType == 'S3') {
			$('.btn-option-box-type').show();
		}
	}

	// 선택완료된 옵션으로 옵션 아이텝을 추가한다.
	function addItemOption() {

		var option = '';
		var optionText = '';
		var optionData = '';
		var optionCount = 0;
		var optionAdditionAmount = 0;
		var optionMaxQuantity = 9999;

		for (var i = 0; i < itemOptions.length; i++) {
			// 필수선택옵션.
			for (var j = 0; j < SELECTED_OPTION_IDS.length; j++) {

				if (itemOptions[i].itemOptionId != SELECTED_OPTION_IDS[j]) {
					continue;
				}

				if (optionCount > 0) {
					optionText += ' | ';
					optionData += '^^^';
				}

				//option += '<input type="hidden" name="itemOptionId" value="' + itemOptions[i].itemOptionId + '" />';
				option += '<input type="hidden" name="optionPrice" value="' + itemOptions[i].optionPrice + '" />';

				if (item.itemOptionType == 'S') {
					option += '<b>' + itemOptions[i].optionName1 + '</b>';
					option += '<span>' + itemOptions[i].optionName2 + '</span>';

					optionText += itemOptions[i].optionName2;
				} else if (item.itemOptionType == 'S2') {
					option += '<b>' + item.itemOptionTitle1 + '/' + item.itemOptionTitle2 + '</b>';
					option += '<span>' + itemOptions[i].optionName1 + '/' + itemOptions[i].optionName2 + '</span>';

					optionText += itemOptions[i].optionName1 + '/' + itemOptions[i].optionName2;
				} else if (item.itemOptionType == 'S3') {
					option += '<b>' + item.itemOptionTitle1 + '/' + item.itemOptionTitle2 + '/' + item.itemOptionTitle3 + '</b>';
					option += '<span>' + itemOptions[i].optionName1 + '/' + itemOptions[i].optionName2 + '/' + itemOptions[i].optionName3 + '</span>';

					optionText += itemOptions[i].optionName1 + '/' + itemOptions[i].optionName2 + '/' + itemOptions[i].optionName3;
				}

				if (itemOptions[i].optionPrice != "0") {
					option += ' (';
					optionText += ' (';

					if (Number(itemOptions[i].optionPrice) > 0) {
						option += '+';
						optionText += '+';

					}

					option += Number(itemOptions[i].optionPrice) + '원)';
					optionText += Number(itemOptions[i].optionPrice) + '원)';
				}

				// 전송용 옵션정보
				optionData += itemOptions[i].itemOptionId + '```';

				// 옵션 추가금액
				optionAdditionAmount += itemOptions[i].optionPrice;

				// 옵션 최대 구매 수량
				var optionQty = 9999;
				if (itemOptions[i].optionStockFlag == 'Y') {
					optionQty = itemOptions[i].optionStockQuantity;
				}

				optionMaxQuantity = optionQty < optionMaxQuantity ? optionQty : optionMaxQuantity;

				optionCount++;
			}
		}

		// 텍스트 옵션
		var $itemOptionInfo = $('.op-item-option-info');
		$itemOptionInfo.find('.text-option-id').each(function () {
			if (optionCount > 0) {
				optionData += '^^^';
			}

			var $textOption = $(this).closest('.select_area');
			var optionId = $(this).val();
			var optionName = $textOption.find('.text-option-name').val();
			var optionValue = $textOption.find('.text-option-value').val();

			option += '<input type="hidden" name="optionPrice" value="0" />';
			option += '<b>' + optionName + '</b>' + '<span>' + optionValue + '</span>';

			optionData += optionId + '```' + optionValue;

			optionCount++;
		});

		var html = '';

		html += '<div class="add_item" data-option="' + optionData + '" data-option-max-quantity="' + optionMaxQuantity + '">';
		html += '	<input type="hidden" name="arrayRequiredItems" />';
		html +=	'	<div class="ai_tit mb10">';
		html += '		<p class="op_tit op-added-option item-name">' + option + '</p>';
		html += '		<a href="javascript:;" class="btn_item_delete op-added-option-delete">';
		html +=	'			<img src="/content/mobile/images/common/btn_close.png" alt="해당 옵션 삭제">';
		html +=	'		</a>';
		html += '	</div>';
		html += '	<div class="d-flex space-between">';
		html +=	'		<div class="cacul op-added-option-quantity">';
		html += '			<button type="button" class="minus op-item-order-count-minus">감소</button>';
		html += '			<input type="text" maxlength="3" class="op-option-quantity _number num" value="1" />';
		html += '			<button type="button" class="plus op-item-order-count-plus">증가</button>';
		html += '		</div>';
		html += '		<p class="op_price"><span class="op-added-option-price">' + Common.numberFormat(item.price + optionAdditionAmount) + '</span>원</p>';
		html += '	</div>';
		html += '</div>';

		var $addedOptions = $('.op-added-options');

		// 이미 추가된 옵션인지 체크!
		var isAlreadyAddition = false;
		$addedOptions.find('.op-added-option').each(function () {
			var currentText = $("<div/>").append($(this).html()).text().replace(/[\r|\n|\t]/g, '');
			var newText = $("<div/>").append(html).find('.op-added-option').text().replace(/[\r|\n|\t]/g, '');

			if (currentText == newText) {
				isAlreadyAddition = true;
				return false;
			}
		});

		if (isAlreadyAddition != true) {
			$addedOptions.append(html);
		}

		// 필수 선택 옵션 초기화
		initItemOption();

		$itemOptionInfo.find('.op-option-select-box').text(optionText);

		// 텍스트 옵션 초기화
		$itemOptionInfo.find('.text-option-value').val('');

		// 옵션 선택 박스 숨기기.
		$('.op-option-box').hide();

		// 옵션 선택폼 보이기
		$addedOptions.show();

		// 금액 계산.
		calculate();

		// 하단 footer option form 클릭 이벤트
		$('.btn_open').trigger('click');
	}

	/****************************
	 * 추가된 옵션에 대한 이벤트
	 ****************************/
	// 수량 입력
	$('.op-added-options').on('blur', '.op-option-quantity', function (e) {
		var $quantity = $(this).closest('.add_item').find('.op-option-quantity');
		var quantity = 1;

		try {
			quantity = Number($(this).val());
		} catch (e) {

		}

		// 최대구매 수량 체크.
		var currentQuantity = getCurrentItemQuantity();

		if (currentQuantity > item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			$quantity.val(1);
			calculate();
			return;
		}

		// 옵션 구매 수량 체크 (옵션재고)
		var optionMaxQuantity = Number($(this).closest('.add_item').data('option-max-quantity'));

		if (quantity > optionMaxQuantity) {
			alert('옵션 별 최대 구매 수량은 ' + optionMaxQuantity + '개 입니다.\n구매 수량을 다시 확인해주세요.')
			$quantity.val(1);
			calculate();
			return;
		}

		//$quantity.val(quantity);
		calculate();
	}).on("keyup", '.op-option-quantity', function (e) {
		var pattern = /^[0-9]+$/;
		if (!pattern.test($(this).val())) {
			$(this).val('1');
		}

		if (!canOrderByMaxQuantity()) {
			$(this).val('1');
		}

		calculate();
	});

	// 수량 +
	$('.op-added-options').on('click', '.op-item-order-count-plus', function (e) {
		e.preventDefault();

		// 최대구매 수량 체크.
		var currentQuantity = getCurrentItemQuantity();

		if (currentQuantity == item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			return;
		}

		// 옵션 최대 구매 수량 체크.
		var $quantity = $(this).closest('.add_item').find('.op-option-quantity');
		var quantity = Number($quantity.val()) + 1;
		var optionMaxQuantity = Number($(this).closest('.add_item').data('option-max-quantity'));

		if (quantity > optionMaxQuantity) {
			alert('옵션 별 최대 구매 수량은 ' + optionMaxQuantity + '개 입니다.\n구매 수량을 다시 확인해주세요.')
			return;
		}

		// 상품 수량 최대 999개 이상 되지 않도록 수정 2017-05-10 yulsun.yoo
		if (quantity > 999) {
			alert("해당 상품의 최대 구매 수량은 999개 입니다.");
			return;
		}

		$quantity.val(quantity);
		calculate();
	});

	// 수량 -
	$('.op-added-options').on('click', '.op-item-order-count-minus', function (e) {
		e.preventDefault();

		var $quantity = $(this).closest('.add_item').find('.op-option-quantity');
		var quantity = Number($quantity.val()) - 1;

		if (quantity == 0) {
			return;
		}

		$quantity.val(quantity);
		calculate();

	});

	// 삭제
	$('.op-added-options').on('click', '.op-added-option-delete', function (e) {
		e.preventDefault();
		$(this).closest('.add_item').remove();

		// 남은 옵션이 없을경우 선택폼 hide
		if ($('.op-added-options').find('.add_item').length == 0) {
			$('.op-added-options').hide();
		}

		calculate();
	});

	// 최대구매 수량 체크
	function canOrderByMaxQuantity() {

		if (item.orderMaxQuantity == -1) {
			return true;
		}

		var currentQuantity = getCurrentItemQuantity();

		if (currentQuantity > item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			return false;
		}

		return true;
	}

	// 현재 상품 추가 수량.
	function getCurrentItemQuantity() {
		var $addedOptions = $('.op-added-options .add_item');

		var currentQuantity = 0;

		$addedOptions.each(function () {
			var quantity = 0;
			try {
				quantity = Number($(this).find('.op-option-quantity').val());
			} catch (e) {
			}

			currentQuantity += quantity;
		});

		return currentQuantity;
	}
}

// 상품 옵션조합형 옵션 선택 추가 이벤트
function initItemCombinationOptionEvent() {

	// init
	if (item.itemOptionFlag == 'N' || item.itemOptionType != 'C') {
		return;
	}

	// 옵션 초기 세팅
	initOptionSetting();

	// 상품필수옵션 셀렉트박스 클릭
	$('.op-option-select-box').on('click', function(e) {
		e.preventDefault();

		// 옵션 선택 초기화
		initItemCombinationOption($(this).closest('.op-item-option-group-info').data('index'));

		$('.op-option-box').hide();
		$(this).closest('div').find('.op-option-box').show();
		$(this).closest('div').find('.op-option-box ul.option_list02').hide().eq(0).show();

		calculate();
	});

	// 옵션 선택 박스 닫기
	$('.op-close-option-box').on('click', function(e) {
		e.preventDefault();

		$('.op-option-box').hide();

		// 옵션 선택 초기화
		initItemCombinationOption($(this).closest('.op-item-option-group-info').data('index'));

		calculate();
	});

	// 세부 옵션 선택 이벤트
	$('.op-item-option-info .op-option-box').on('click', '.op-option-group li a', function(e) {
		e.preventDefault();

		var $quantity = $('.quantity');

		var $itemOptionGroupInfo = $(this).closest('.op-item-option-group-info');
		var index = $itemOptionGroupInfo.data('index');

		$('.op-option-box').hide();

		var title = $(this).attr('data-title');
		var optionId = $(this).attr('data-option-id');
		var optionName = $(this).attr('data-option-name');
		var optionPrice = $.trim($(this).attr('data-option-price')) == '' ? 0 : Number($(this).attr('data-option-price'));
		var optionStockFlag = $(this).attr('data-option-stock-flag');
		var optionStockQuantity = $.trim($(this).attr('data-option-stock-quantity')) == '' ? 0 : Number($(this).attr('data-option-stock-quantity'));

		if (optionId != null) {
			// 선택 옵션 정보 loop
			for (var i=0; i<SELECTED_COMBINATION_OPTION_INFOS.length; i++) {
				// 선택 정보 중복시, 기존 선택 정보 삭제
				if (SELECTED_COMBINATION_OPTION_INFOS[i].index == index) {
					SELECTED_COMBINATION_OPTION_INFOS.splice(i, 1);
					break;
				}
			}

			SELECTED_COMBINATION_OPTION_INFOS.push({
				'index': index,
				'title': title,
				'itemOptionId': optionId,
				'optionName': optionName,
				'optionPrice': optionPrice,
				'optionStockFlag': optionStockFlag,
				'optionStockQuantity': optionStockQuantity
			});

			$itemOptionGroupInfo.find('.op-option-select-box').text(optionName);
			$itemOptionGroupInfo.find('.op-option-select-box').addClass('selected');

			if (optionPrice != 0) {
				$itemOptionGroupInfo.find('.op-etc-price').addClass('add_price').text((optionPrice > 0 ? '+' : '-') + Common.numberFormat(Math.abs(optionPrice)) + '원');
			} else {
				$itemOptionGroupInfo.find('.op-etc-price').removeClass('add_price').text('기본');
			}

			// 최소구매수량 고정
			$quantity.val(item.orderMinQuantity);

			if (itemOptionGroups.length == SELECTED_COMBINATION_OPTION_INFOS.length) {
				setArrayRequiredItems(item.itemId, $quantity.val());
			}

			calculate();
		}
	}).on('mouseenter', '.op-option-group li a', function(e) {
		e.preventDefault();
		var optionName = $(this).attr('data-option-name');
		$(this).closest('.op-option-group').find('.option-selected-name').text(optionName);
		$(this).closest('.op-option-group').find('.option-selected-name').addClass('sel');
	}).on('mouseleave', '.op-option-group li a', function(e) {
		e.preventDefault();
		if (!$(this).hasClass('on')) {
			$(this).closest('.op-option-group').find('.option-selected-name').text('');
			$(this).closest('.op-option-group').find('.option-selected-name').removeClass('sel');
		}
	});

	// 옵션 구성 (초기화)
	function initItemCombinationOption(index) {
		index = index || 0;

		$('.op-option-select-box').removeClass('selected');

		var title = itemOptionGroups[index].title;
		var displayType = itemOptionGroups[index].displayType;

		if (displayType == 'select') {
			var $itemOptionGroupInfo = $('.op-item-option-group-info').eq(index);
			var $itemOptionInfo = $itemOptionGroupInfo.find('.op-item-option-info');
			var $optionBoxType1 = $itemOptionInfo.find('.op-option-box-type1');

			$itemOptionInfo.find('.op-option-select-box').text($optionBoxType1.find('ul a').eq(0).data('option-name'));
			$itemOptionInfo.find('.op-option-group li a').removeClass('on');

			var optionGroupHtml = '';

			optionGroupHtml += '<li class="op-option-group">';
			optionGroupHtml += '	<ul class="option_list02">';

			var isCheck = false;
			for (var i = 0; i < itemOptions.length; i++) {
				if (title == itemOptions[i].optionName1) {
					optionGroupHtml += '	<li>' + getOptionInfo(itemOptions[i]) + '</li>';	// 옵션항목

					if (!isCheck) {
						// 현재 선택정보 삭제 후 기본값 세팅
						for (var j = 0; j < SELECTED_COMBINATION_OPTION_INFOS.length; j++) {
							if (SELECTED_COMBINATION_OPTION_INFOS[j].index == index) {
								SELECTED_COMBINATION_OPTION_INFOS[j] = {
									'index': j,
									'title': title,
									'itemOptionId': itemOptions[i].itemOptionId,
									'optionName': itemOptions[i].optionName2,
									'optionPrice': itemOptions[i].optionPrice,
									'optionStockFlag': itemOptions[i].optionStockFlag,
									'optionStockQuantity': itemOptions[i].optionStockQuantity
								};
								break;
							}
						}
						isCheck = true;
					}
				}
			}

			optionGroupHtml += '	</ul>';
			optionGroupHtml += '</li>';

			$optionBoxType1.find('.op-option-group').remove();
			$optionBoxType1.append(optionGroupHtml);

			$optionBoxType1.show();

			$itemOptionGroupInfo.find('.op-etc-price').removeClass('add_price').text('기본');
		}
	}

	// 상품 옵션 텍스트 구성.
	function getOptionInfo(itemOption) {
		var info = itemOption.optionName2;

		// 추가금.
		if (itemOption.optionPrice != 0) {
			info += ' (' + (itemOption.optionPrice > 0 ? '+' : '') + Common.numberFormat(itemOption.optionPrice) + '원)';
		}

		// 품절, 재고연동
		if (itemOption.isSoldOut == 'true' || (itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0 && item.orderMinQuantity > itemOption.optionStockQuantity)) {
			info += ' - 품절';
		} else if (itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0) {
			info += ' | 재고: ' + Common.numberFormat(itemOption.optionStockQuantity) + '개';
		}

		if (itemOption.isSoldOut == 'true' || (itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0 && item.orderMinQuantity > itemOption.optionStockQuantity)) {
			return '<span>' + info + '</span>';
		} else {
			return '<a href="#" data-title="' + itemOption.optionName1 + '" data-option-name="' + itemOption.optionName2 + '" data-option-id="' + itemOption.itemOptionId + '" data-option-price="' + itemOption.optionPrice + '" data-option-stock-flag="' + itemOption.optionStockFlag + '" data-option-stock-quantity="' + itemOption.optionStockQuantity + '">' + info + '</a>';
		}
	}
}

/***************************
 * 추가 구성 상품.
 ***************************/
function initItemAdditionEvent() {
	// 추가구성상품 선택 이벤트
	$('.op-item-addition-info').on('click', '.op-addition-select-box', function (e) {
		e.preventDefault();

		var $additionItemBox = $(this).closest('.op-addition-item-box');

		var itemAdditionId = $additionItemBox.data('item-addition-id');
		var itemId = $additionItemBox.data('item-id');
		var itemPrice = $additionItemBox.data('item-price');
		var itemName = $additionItemBox.find('.txt .name').text();
		var itemMaxQuantity = 9999;
		var stockFlag = $additionItemBox.data('stock-flag');
		var	stockQuantity = $additionItemBox.data('stock-quantity');

		// 옵션 최대 구매 수량
		var itemQuantity = 9999;
		if (stockFlag == 'Y') {
			itemQuantity = Number(stockQuantity);
		}

		itemMaxQuantity = itemQuantity < itemMaxQuantity ? itemQuantity : itemMaxQuantity;

		var html = '';
		html += '<div class="add_item" data-item-addition-id="' + itemAdditionId + '" data-item-id="' + itemId + '" data-item-max-quantity="' + itemMaxQuantity + '" data-item-price="' + itemPrice + '">';
		html += '	<input type="hidden" name="arrayAdditionItems" />';
		html += '	<div class="ai_tit mb10">';
		html += '		<p class="op_tit op-added-option item-name">' + itemName + '</p>';
		html += '		<a href="#" class="btn_item_delete op-added-option-delete">';
		html +=	'			<img src="/content/mobile/images/common/btn_close.png" alt="해당 추가상품 삭제">';
		html += '		</a>';
		html += '	</div>';
		html += '	<div class="d-flex space-between">';
		html += '		<div class="cacul op-added-option-quantity">';
		html += '			<button type="button" class="minus">감소</button>';
		html += '			<input type="text" maxlength="3" class="op-option-quantity _number num" value="1" />';
		html += '			<button type="button" class="plus">증가</button>';
		html += '		</div>';
		html += '		<p class="op_price"><span class="op-added-option-price">' + Common.numberFormat(itemPrice) + '</span>원</p>';
		html += '	</div>';
		html += '</div>';

		var $addedItems = $('.op-added-items');

		// 이미 추가된 옵션인지 체크!
		var isAlreadyAddition = false;
		$addedItems.find('.op-added-option').each(function() {
			var currentText = $("<div/>").append($(this).html()).text().replace(/[\r|\n|\t]/g, '');
			var newText = $("<div/>").append(html).find('.op-added-option').text().replace(/[\r|\n|\t]/g, '');

			if (currentText == newText) {
				isAlreadyAddition = true;
				return false;
			}
		});

		if (isAlreadyAddition != true) {
			$addedItems.append(html);
		}

		// 추가구성상품 선택폼 보이기
		$addedItems.show();

		// 금액 계산.
		calculate();

		// 하단 footer option form 클릭 이벤트
		$('.btn_open').trigger('click');
	});

	/***************************
	 * [ksh 2020-12-04] 리뉴올 PC 추가구성상품 재고체크 안함!!
	 ***************************/
	// 수량 입력
	$('.op-added-items').on('blur', '.op-option-quantity', function (e) {
		calculate();
	}).on("keyup", '.op-option-quantity', function (e) {
		var pattern = /^[0-9]+$/;
		if (!pattern.test($(this).val())) {
			$(this).val('1');
		}
		calculate();
	});

	// 수량 +
	$('.op-added-items').on('click', '.plus', function (e) {
		e.preventDefault();

		var $quantity = $(this).parent().find('.op-option-quantity');
		var currentQuantity = Number($quantity.val());

		$quantity.val(currentQuantity + 1);
		calculate();
	});

	// 수량 -
	$('.op-added-items').on('click', '.minus', function (e) {
		e.preventDefault();

		var $quantity = $(this).closest('.add_item').find('.op-option-quantity');
		var quantity = Number($quantity.val()) - 1;

		if (quantity == 0) {
			return;
		}

		$quantity.val(quantity);
		calculate();
	});

	// 삭제
	$('.op-added-items').on('click', '.op-added-option-delete', function (e) {
		e.preventDefault();
		$(this).closest('.add_item').remove();

		// 남은 옵션이 없을경우 선택폼 hide
		if ($('.op-added-items').find('.add_item').length == 0) {
			$('.op-added-items').hide();
		}

		calculate();
	});
}

// 상품 옵션의 기준 셀렉터.
function getItemOptionInfo() {
	return $('#op-item-option-info');
}

// 모든 상품 옵션을 선택했는가?
function isSelectedAllOption() {
	var $itemOptionInfo = getItemOptionInfo();
	var optionGroupCount = $itemOptionInfo.find('tr').length;
	var optionCheckedCount = 0;

	// 라디오 옵션 체크 카운트
	optionCheckedCount += $itemOptionInfo.find('input[type=radio]:checked').length;

	// 셀렉트박스 옵션 체크 카운트
	$itemOptionInfo.find('select').each(function () {
		if ($(this).val() != '') {
			optionCheckedCount++;
		}
	});

	if (optionGroupCount == optionCheckedCount) {
		return true;
	}
	return false;
}

// 상품 옵션 정보
function getItemOption(val) {
	var itemOptionInfo = val.split('|');
	var optionInfos = itemOptionInfo[1].split('^');
	return {
		'itemOptionTitle': optionInfos[0],
		'itemOptionId': Number(optionInfos[1]),
		'optionName': optionInfos[2],
		'extraPrice': Number(optionInfos[3]),			// 추가금액
		'stockQuantity': Number(optionInfos[4]),
		'stockScheduleText': optionInfos[5],
		'stockScheduleDate': optionInfos[6]
	};

}


//상품 옵션 정보 (카드 전송용 )
function getOptionValueForCart(val) {
	return val.split('|')[0];
}

// 전체 주문 수량.
function getTotalItemQuantity() {
	var totalItemQuantity = 0;
	$('#added-options input[name=arrayQuantitys]').each(function () {
		totalItemQuantity += Number($(this).val());
	});
	return totalItemQuantity;
}

// 가격계산
function calculate() {
	var itemPrice = item.presentPrice;
	if ($.trim(itemPrice) == '') {
		return;
	}

	var totalItemPrice = 0;
	var totalItemCount = 0;

	var totalOptionPrice = 0;
	var totalAdditionPrice = 0;

	var $addedOptions = $('.op-added-options .add_item');
	var $addedItems = $('.op-added-items .add_item');

	// 단품
	var $quantity = $('.quantity');
	if ($quantity.length > 0) {
		totalItemPrice = itemPrice * Number($quantity.val());
	}

	// 일반옵션 상품
	$addedOptions.each(function() {
		var quantity = $(this).find('.op-option-quantity').val();
		var extraPrice = $(this).find('input[name=optionPrice]').val();

		quantity = $.trim(quantity) == '' ? 0 : Number(quantity);
		extraPrice = $.trim(extraPrice) == '' ? 0 : Number(extraPrice);

		var optionPrice = (Number(itemPrice) + Number(extraPrice)) * quantity;
		totalOptionPrice += optionPrice;
		totalItemCount += quantity;

		$(this).find('.op-added-option-price').text(Common.numberFormat(optionPrice));
	});

	// 옵션조합형 상품
	for (var i = 0; i < SELECTED_COMBINATION_OPTION_INFOS.length; i++) {
		if ($quantity.length > 0) {
			var optionPrice = Number(SELECTED_COMBINATION_OPTION_INFOS[i].optionPrice) * $quantity.val();

			totalItemPrice += optionPrice;
		}
	}

	// 추가구성 상품
	$addedItems.each(function () {
		var quantity = $(this).find('.op-option-quantity').val();
		var itemPrice = $(this).data('item-price');

		quantity = $.trim(quantity) == '' ? 0 : Number(quantity);
		itemPrice = $.trim(itemPrice) == '' ? 0 : Number(itemPrice);

		var additionItemPrice = Number(itemPrice) * quantity;
		totalAdditionPrice += additionItemPrice;
		totalItemCount += quantity;

		$(this).find('.op-added-option-price').text(Common.numberFormat(additionItemPrice));
	});

	// 기본상품 합계금액
	$('.total-item-price').text(Common.numberFormat(totalItemPrice));

	// 선택옵션 합계금액
	//$('.total-option-price').text(Common.numberFormat(totalOptionPrice));

	// 선택추가상품 합계금액
	$('.total-addition-price').text(Common.numberFormat(totalAdditionPrice));

	// 총 결제금액
	$('.total-amount').text(Common.numberFormat(totalItemPrice + totalOptionPrice + totalAdditionPrice));

	$('.total-price').show();
}

function initOptionSetting() {
	for (var i = 0; i < itemOptionGroups.length; i++) {
		var isCheck = false;
		for (var j = 0; j < itemOptions.length; j++) {
			if (itemOptionGroups[i].title == itemOptions[j].optionName1
					&& itemOptions[j].isSoldOut == 'false' && !(itemOptions[j].optionStockFlag == 'Y' && itemOptions[j].optionStockQuantity > 0 && item.orderMinQuantity > itemOptions[j].optionStockQuantity)
					&& !isCheck) {
				SELECTED_COMBINATION_OPTION_INFOS.push({
					'index': i,
					'title': itemOptionGroups[i].title,
					'itemOptionId': itemOptions[j].itemOptionId,
					'optionName': itemOptions[j].optionName2,
					'optionPrice': itemOptions[j].optionPrice,
					'optionStockFlag': itemOptions[j].optionStockFlag,
					'optionStockQuantity': itemOptions[j].optionStockQuantity
				});

				$('.op-item-option-group-info').eq(i).find('.op-option-select-box').text(itemOptions[j].optionName2);
				$('.op-item-option-group-info').eq(i).find('.op-option-select-box').addClass('selected');

				if (itemOptions[j].optionPrice != 0) {
					$('.op-item-option-group-info').eq(i).find('.op-etc-price').addClass('add_price').text((itemOptions[j].optionPrice > 0 ? '+' : '-') + Common.numberFormat(Math.abs(itemOptions[j].optionPrice)) + '원');
				} else {
					$('.op-item-option-group-info').eq(i).find('.op-etc-price').removeClass('add_price').text('기본');
				}

				// 최소구매수량 고정
				$('.quantity').val(item.orderMinQuantity);

				calculate();

				isCheck = true;
			}
		}
	}

	if ($('.op-item-option-group-info').length == SELECTED_COMBINATION_OPTION_INFOS.length) {
		setArrayRequiredItems(item.itemId, $('.quantity').val());
	}
}

// 비회원 구매 가능 상품 체크.
// 비회원 구매 불가인 경우 로그인 페이지로 이동
function checkForNonmemberOrder() {
	
	var nonmemberOrderType = item.nonmemberOrderType;
	if (!(isLogin == 'true' || nonmemberOrderType == '1')) {
		var message = Message.get("M00601") + '\n' + Message.get("M00602");	// 회원만 구매가 가능합니다. 로그인 페이지로 이동하시겠습니까?
		if (confirm(message)) {
			location.href = "/m/users/login?target="+requestUri;
		}
		return false;
	}
}

// 장바구니 담기.
function addToCart() {

	// 장바구니에 담을 수 있는 지 확인한다.
	if (!checkForItem('cart')) {
		return;
	}

	$.post('/m/cart/add-item-to-cart', $('#cartForm').serialize(), function (response) {
		clearSelectInformation();
		Common.responseHandler(response, function () {
			Shop.getCartInfo();

			Shop.openCartWishlistLayer('cart');

			// 하단 footer option form 클릭 이벤트
			if ($('.btn_open div').hasClass('on')) {
				$('.btn_open').trigger('click');
			}
		});
	}, 'json');
}

//네이버 위시리스트
function naverWishInItemView() {

	// 장바구니에 담을 수 있는 지 확인한다.
	if (!checkForItem('naver_wishlist')) {
		return;
	}

	var returnUrl = "";
	$.ajaxSetup({'async': false});
	$.post('/open-market/naver-wishlist/mobile', $('#cartForm').serialize(), function (response) {
		Common.responseHandler(response, function (response) {
			returnUrl = response.data;
		}, function (response) {
			alert(response.errorMessage)
		});
	}, 'json');

	return returnUrl;
}

function naverPayInItem() {
	// 장바구니에 담을 수 있는 지 확인한다.
	if (!checkForItem('buy_now')) {
		return;
	}

	var returnUrl = "";

	$.ajaxSetup({'async': false});
	$.post('/m/cart/buy-now', $('#cartForm').serialize(), function (response) {
		Common.responseHandler(response, function () {
			$.post('/open-market/naver-buy-now/mobile', $('#cartForm').serialize(), function (response) {

				Common.responseHandler(response, function (response) {
					returnUrl = response.data;
				}, function (response) {
					alert(response.errorMessage)
				});
			}, 'json');
		});
	}, 'json');

	return returnUrl;
}

// 바로구매
function buyNow(loginCheck) {

	// 바로구매가 가능한 지 확인한다.
	if (!checkForItem('buy_now')) {
		return;
	}

	
	loginCheck = loginCheck == undefined ? true : loginCheck;

	$.post('/cart/buy-now', $('#cartForm').serialize(), function (response) {
		//clearSelectInformation();
		Common.responseHandler(response, function () {

			if (isLogin == 'false' && loginCheck == true) {
				location.href = "/m/users/login?target=/m/order/step1&popup=1&redirect=/m/order/step1&uri=/m/products/view/"+item.itemUserCode;
			} else {
				location.href = '/m/order/step1';
			}

		});
	}, 'json');
}

var PopupLogin = {};
PopupLogin.Callback = function () {
	buyNow(false);
}

// 모바일 로그인 후 바로구매 처리 [2018-04-19] yulsun.yoo
if (loginPopup == 'T') {
	buyNow(false);
}


// 장바구니/바로구매 가능한지 확인한다.
function checkForItem(target) {
	if (!(target == 'cart' || target == 'buy_now' || target == 'wishlist' || target == 'naver_wishlist')) {
		alert('처리할 수 없습니다.');
		return false;
	}

	var stockFlag = item.stockFlag;
	var stockQuantity = item.stockQuantity;
	var orderMinQuantity = item.orderMinQuantity;
	var orderMaxQuantity = item.orderMaxQuantity;

	// 품절 확인.
	if (item.isItemSoldOut) {
		alert('해당 상품은 판매 종료 되었습니다.'); 		// [번역] 이상품은 판매종료 입니다.
		return false;
	}

	// 비회원 구매 불가인 경우 로그인 페이지로 이동
	if (checkForNonmemberOrder() == false) {
		return false;
	}

	// 총 금액 체크
	var totalAmount = Number($('.total-amount').text().replace(/,/g, ""));
	if (totalAmount < 0) {
		alert('상품의 구성이 잘못되었습니다. 구매가 불가능합니다.\n고객센터로 문의 바랍니다.');
		return false;
	}

	// 장바구니 체크
	var quantity = $('.quantity').val();

	// 일반 상품
	if (item.itemOptionFlag == 'N' || item.itemOptionType == 'C' || target == 'naver_wishlist') {
		// 상품 최소 수량 체크 2017-04-24 yulsun.yoo
		if (quantity < orderMinQuantity) {
			alert('해당 상품의 최소 구매 수량은 ' + orderMinQuantity + "개 입니다.");
			$('.quantity').val(orderMinQuantity);
			return false;
		}

		// 상품 최대 수량 체크
		if (quantity > orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + orderMaxQuantity + "개 입니다.");
			$('.quantity').val(orderMaxQuantity);
			return false;
		}

		if (stockFlag == 'Y' && quantity > stockQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + stockQuantity + '개 입니다.');
			$('.quantity').val(stockQuantity);
			return false;
		}

		if (item.itemOptionType == 'C') {
			if (IS_COMBINATION_OPTION_SOLD_OUT == 'true') {
				alert('해당 상품은 판매 종료 되었습니다.');
				return false;
			}

			if (itemOptionGroups.length != SELECTED_COMBINATION_OPTION_INFOS.length) {
				alert('상품필수옵션을 선택해주세요.');
				$('.option-select-box').focus();
				return false;
			}
		}
	} else {
		// 일반옵션
		var $addedOptions = $('.op-added-options .add_item');
		var $textOptions = $('.text-option-value');

		var rst = true;
		if ($addedOptions.length == 0 && $('.text-option-value').length > 0) {
			$textOptions.each(function () {
				if ($(this).val() == null || $(this).val() == '') {
					alert('텍스트 옵션값을 입력 후 옵션 추가 버튼을 눌러 옵션을 추가해주세요.');
					$(this).focus();
					rst = false;
					return false;
				}
			});

			if (rst == false) return false;
		}

		if ($('.text-option-value').length > 0 && $addedOptions.length == 0) {
			alert('옵션 추가 버튼을 눌러 옵션을 추가해주세요.');
			return false;

		} else if ($addedOptions.length == 0) {
			alert('상품필수옵션을 선택해주세요.');
			$('.op-option-select-box').focus();
			return false;
		}

		var optionQuantity = 0;
		$addedOptions.each(function (index) {
			var optionData = item.itemId + '||' + $(this).find('.op-option-quantity').val() + '||' + $(this).data('option');
			optionQuantity += Number($(this).find('.op-option-quantity').val());
			$('input[name=arrayRequiredItems]').eq(index).val(optionData);
		});

		// 상품 최소 수량 체크
		if (optionQuantity < orderMinQuantity) {
			alert('해당 상품의 최소 구매 수량은 ' + orderMinQuantity + "개 입니다.");
			return false;
		}

		// 상품 최대 수량 체크
		if (optionQuantity > orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + orderMaxQuantity + "개 입니다.");
			return false;
		}
	}

	if (target == 'wishlist') {
		return true;
	}

	// 추가 구성 상품.
	var $addedItems = $('.op-added-items .add_item');

	$addedItems.each(function (index) {
		var additionData = $(this).data('item-id') + '||' + $(this).find('.op-option-quantity').val() + '||' + $(this).data('item-addition-id');
		$('input[name=arrayAdditionItems]').eq(index).val(additionData);
	});

	return true;
}


// 관심상품 담기.
function addToWishList() {
	// 관심상품 담기 클래스 추가 2017-05-16 yulsun.yoo
	$(".favorite").toggleClass('on');

	
	if (isLogin == 'false') {
		alert('로그인 해주세요.');
		location.href = "/m/users/login?target="+requestUri;
		return;
	}

	var $wishlistGroupIds = $('input[name=wishlistGroupId]');
	$wishlistGroupIds.eq(0).prop('checked', true);

	var param = {
		'itemId': item.itemId
	};

	// 관심 상품으로 등록
// 	$.post('/m/wishlist/add', $('#cartForm').serialize(), function(response) {
	$.post('/m/wishlist/add', param, function (response) {
		Common.responseHandler(response, function () {
			resetWishlistItemCount(0);

			// 헤더에 위시리스트 카운트 표시
			Shop.getWishlistCount();

			// 확인 레이어
			Shop.openCartWishlistLayer('wishlist');

		});
		clearSelectInformation();
	}, 'json');
}

// 관심 상품 추가 가능 여부
function resetWishlistItemCount(addItemCount) {
	var $wishlistGroupIds = $('input[name=wishlistGroupId]');
	$wishlistGroupIds.each(function () {
		var $wishlistGroupId = $(this);
		var $tr = $wishlistGroupId.closest('tr');
		var $wishlistItemCount = $tr.find('.wishlist_item_count');
		var $wishlistMaxItemCount = $tr.find('.wishlist_max_item_count');

		var newItemCount = Number($wishlistItemCount.text()) + addItemCount;

		$wishlistGroupId.prop('checked', false);

		if (Number($wishlistItemCount.val()) >= Number($wishlistMaxItemCount.text())
			|| newItemCount > Number($wishlistMaxItemCount.text())) {
			//$wishlistGroupId.prop('disabled', true);   // 옵션상품 중 재고가 없는 경우 disabled 했으나 입하신청 때문에 주석으로 처리
		} else {
			//$wishlistGroupId.prop('disabled', false);	// 옵션상품 중 재고가 없는 경우 disabled 했으나 입하신청 때문에 주석으로 처리
		}
	});
}


// 선택정보 초기화
function clearSelectInformation() {
	// 관심상품.
	//closeLayer('wishlist_group_layer');
	$('#op-item-option-info select').val('');

	$('#cart_item').empty();

	if (item.itemOptionFlag == 'N' || item.itemOptionType == 'C') {
		var $defaultQuantity = 1;
		$('.quantity').val($defaultQuantity);

		if (item.orderMinQuantity > 0) {
			$('.quantity').val(item.orderMinQuantity);
			$defaultQuantity = item.orderMinQuantity;
		}

		if (item.itemOptionType == 'C') {
			SELECTED_COMBINATION_OPTION_INFOS = [];
			initOptionSetting();

			// 옵션 폼 초기화
			// $('.op-item-option-group-info[data-display-type="select"]').find('.op-etc-price').removeClass('add_price').text('기본');
			// $('.op-item-option-info').find('.op-option-select-box').text('선택하세요.');
		} else {
			// append ArrayRequiredItems
			setArrayRequiredItems(item.itemId, $defaultQuantity);
		}

		// append ArrayRequiredItems
		setArrayRequiredItems(item.itemId, $defaultQuantity);
	}

	$('.op-added-options').hide();
	$('.op-added-items').hide();

	$('.op-added-options .add_item').remove();
	$('.op-added-items .add_item').remove();

	$('.quantity').val(item.orderMinQuantity > 0 ? item.orderMinQuantity : '0');

	calculate();
}

var currentReviewPage = 0;
// 리뷰 페이징.
function paginationReviewMore(page) {
	currentReviewPage++;

	var param = {
		'page': currentReviewPage,
		'where': 'ITEM_ID',
		'query': item.itemId,
		'itemId': item.itemId
	};
	$.post('/m/item/review-list', param, function (response) {
		$('#op-review-list').append(response);
		showHideReviewMoreButton();
	}, 'html');
}

//리뷰 더보기 버튼 hide 처리
function showHideReviewMoreButton() {

	var totalItems = 0;
	var $info = $("#op-review-list").find('#page-' + currentReviewPage);

	if ($info.length > 0) {
		totalItems = Number($info.find('.total-items').text());
	}

	if ($("#op-review-list").find(' > li').length == totalItems || totalItems == 0) {
		$('.op-review-more-load').hide();
	}
}

var currentQnaPage = 0;

// QNA 페이징.
function paginationQnaMore() {
	currentQnaPage++;

	var param = {
		'page': currentQnaPage,
		'itemId': item.itemId
	};

	$.post('/m/item/qna-list', param, function (response) {
		$('#op-qna-list').append(response);
		showHideQnaMoreButton();
	}, 'html');
}

//QNA 더보기 버튼 hide 처리
function showHideQnaMoreButton() {

	var totalItems = 0;
	var $info = $("#op-qna-list").find('#page-' + currentQnaPage);

	if ($info.length > 0) {
		totalItems = Number($info.find('.total-items').text());
	}

	if ($("#op-qna-list").find(' > li').length == totalItems || totalItems == 0) {
		$('.op-qna-more-load').hide();
	}
}


// 쿠폰 다운로드
//(2014.10.28)
function downloadCoupon() {
	//Common.popup('/m/item/coupon/' + item.itemId, 'download-coupon', 800, 600);
	location.href = '/m/item/coupon/' + item.itemId;
}

// 카드 혜택
function cardBenefitsPopup() {
	location.href = '/m/item/cardBenefitsPopup';
}

function itemImagesDetail(itemId) {
	//location.href = '/m/item/details-image-view?itemId=' + itemId + '&itemImageId=' + itemImageId;
	location.href = '/m/item/details-image-view?itemId=' + itemId;

}

function setArrayRequiredItems(itemId, quantity) {
	// 옵션조합형 타입
	var optionData = '';
	if (item.itemOptionType == 'C') {
		// 옵션그룹 index 오름차순 정렬
		SELECTED_COMBINATION_OPTION_INFOS.sort(function(a, b) {
			return a.index < b.index ? -1 : a.index > b.index ? 1 : 0;
		});

		for (var i = 0; i < SELECTED_COMBINATION_OPTION_INFOS.length; i++) {
			if (i > 0) {
				optionData += '^^^';
			}

			// 전송용 옵션정보
			optionData += SELECTED_COMBINATION_OPTION_INFOS[i].itemOptionId + '```';
		}
	}

	var template = '<input type="hidden" name="arrayRequiredItems" value="' + itemId + '||' + quantity + '||' + optionData + '" />';
	$('#op-cart-item').empty().append(template);
}

function restockNotice() {
	if (isRestockNotice == 'true' || $('.restock_btn_wrap .restock').hasClass('on')) {
		return false;
	}

	if (isLogin == 'false') {
		alert('로그인해주세요.');
		location.href = "/m/users/login?target="+requestUri;
		return;
	}

	var param = {
		'itemId': item.itemId
	};

	// 관심 상품으로 등록
	$.post('/restock-notice/add', param, function (response) {
		Common.responseHandler(response, function () {
			$('.restock_btn_wrap .restock').addClass('on');
			alert('재입고 알림을 신청했습니다.');
		});

	}, 'json');
}

function viewItemRelations() {
	$.get('/m/item/item-relations/' + item.itemId, null, function (response) {
		$('#op-item-relation').empty().html(response);
		relation();
	}, 'html');
}

function setBenefitInfo() {

	$('.item-point').hide();
	$('.item-card-benefit').hide();

	$.get('/item/benefit-info/' + item.itemId, null, function (response) {
		Common.responseHandler(response, function (response) {

			var data = response.data;

			if (data != null) {

				var pointPolicy = data.pointPolicy,
					cardBenefits = data.cardBenefits,
					itemEarnPoint = data.itemEarnPoint;

				if (itemEarnPoint != null) {

					$('.item-point span').text(Common.numberFormat(itemEarnPoint.totalPoint) + ' P');
					$('.item-point').show();
				}

				if (cardBenefits != null) {
					var content = cardBenefits.content;
					if (content != null && content != '') {
						$('.item-card-benefit span').text(cardBenefits.subject);
						$('.item-card-benefit').show();
					}
				}
			}

		});
	});
}

function setCustomerInfo() {
	$.get('/item/customer-info/' + item.itemId, null, function (response) {
		Common.responseHandler(response, function (response) {
			var data = response.data;

			if (data != null) {
				$('.item-review-count').text('(' + Common.numberFormat(data.reviewCount) + ')');
				$('.item-qna-count').text('(' + Common.numberFormat(data.qnaCount) + ')');
			}
		});
	});
}