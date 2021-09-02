$(function() {

	$(".product_inner_preview li:eq(0)").addClass('preview');
	initThumbnail();

	// 옵션 바로 선택
	initOptionFixed();

	// 스크롤 이벤트 등록.
	onScrollEvent();

	// 상품 수량 체크 이벤트 (옵션 없는 경우)
	initItemQuantityEvent();

	// 상품 일반옵션 선택 추가 이벤트
	initItemOptionEvent();

	// 상품 옵션조합형 옵션 선택 추가 이벤트
	initItemCombinationOptionEvent();

	// 추가 구성 상품 선택 추가 이벤트
	initItemAdditionEvent();

	// 상품 설명 탭 설정.
	initItemDescriptionTab();

	// 섬네일 슬라이드 및 이미지 확대 팝업.
	initImageViewer();

	////////////////////////////////////////////

	// 정가 표시 방법 (정가가 숫자가 아닌 경우 처리)
	displayItemPrice();

	// 리뷰조회
	paginationReview(1);

	// QNA 페이징.
	paginationQna(1);

	// 리뷰 제목 클릭 이벤트 (내용 오픈)
	reviewClickEvent();

	// 최초계산
	calculate();

	updateItemHits();

	viewItemOthers();
	viewItemRelations();
	setBenefitInfo();
	setCustomerInfo();
});

//상품 조회 수 업데이트.
function updateItemHits() {
	$.post('/common/update-item-hits', {'itemId' : item.itemId}, function() {
	});
}

//상품상세 (사용 안함)
function _item_slide(){

	var thumb = $('.item-slider-min').find('.thumb-item-min');

	var visibleThumbs = 6;

	//큰 이미지 bxSlider
	var gallerySlider = $('.item-slider').bxSlider({
		controls: true,
		pager: false,
		easing: 'easeInOutQuint',
		infiniteLoop: false,

		onSlideAfter: function ($slideElement, oldIndex, newIndex) {
			thumb.removeClass('active');
			thumb.eq(newIndex).addClass('active');

			//상품이지미 확대 기능
			$(".product_inner_preview li:eq("+oldIndex+")").removeClass('preview');
			$(".product_inner_preview li:eq("+newIndex+")").addClass('preview');

			//슬라이드로 이동
			slideThumbs(newIndex + 1, visibleThumbs);

			initThumbnail();
		}
	});

	//썸네일 클릭시 해당 이미지로 슬라이드 이동
	thumb.click(function (e) {
		gallerySlider.goToSlide($(this).closest('.thumb-item-min').index());

		e.preventDefault();
	});

	//슬라이드로 이동
	function slideThumbs(currentSlideNumber, visibleThumbs) {

		var m = Math.floor(currentSlideNumber / visibleThumbs);
		var slideTo = m;

		thumbsSlider.goToSlide(slideTo);
	}

	// Thumbnail slider
	var thumbsSlider = $('.item-slider-min').bxSlider({
		controls: false,
		pager: false,
		easing: 'easeInOutQuint',
		touchEnabled: false,
		infiniteLoop: false,
		minSlides : 6,
		maxSlides : 10,
		slideWidth : 78,
		slideMargin : 0
	});

	//썸네일에 엑티브추가
	$('.item-slider-min .thumb-item-min').click(function (event) {

		$('.item-slider-min').find('.thumb-item-min').removeClass('active');
		$(this).addClass('active');

	});
}


//상품상세
function initThumbnail()
{
	var setHideZoom = setTimeout(function() {
		//resetElevateZoom();
		$(".zoomContainer").css("pointer-events", "none");
	}, 1000);
}

//상품상세
function resetElevateZoom()
{
	var img = $(".preview img");
	var imgTop = img.position().top;
	var imgLeft = img.position().left;
	var imgWidth = img.width;

	$(".preview img").elevateZoom({
		borderSize: 1,
		zoomWindowWidth: 528,
		zoomWindowHeight: 528,
		zoomWindowOffetx: imgLeft+10
	});
}


// 옵션 바로 선택
function initOptionFixed() {
	if (item.itemOptionFlag != 'Y') {
		return;
	}

	// 옵션 바로 선택 화살표..
	$('.btn-option-show-hide').on('click', function(e) {
		e.preventDefault();
		var $cartForm = $('#cartForm');

		if ($cartForm.hasClass('on')) {
			$cartForm.removeClass('on');
			$(this).find('span').removeClass('glyphicon-menu-up').removeClass('glyphicon-menu-down');
			$(this).find('span').addClass('glyphicon-menu-up');
		} else {
			$cartForm.addClass('on');
			$(this).find('span').removeClass('glyphicon-menu-up').removeClass('glyphicon-menu-down');
			$(this).find('span').addClass('glyphicon-menu-down');
		}

	});
}

// 스크롤 이벤트 등록
function onScrollEvent() {

	initScrollContent() ;


	$(window).on('scroll', function() {
		initScrollContent();
	});

	function initScrollContent() {

		var tabHeight = 41;
		var scrollTop = $(document).scrollTop();
		// 스크롤
		var $itemTabContentWrap = $('.item-tab-content-wrap');
		var startSize = $itemTabContentWrap.offset().top;
		var endSize = $('.btm_kind_sec').eq(0).offset().top - tabHeight;

		if (scrollTop >= startSize && scrollTop <= endSize) {
			if (item.itemOptionFlag == 'Y' && item.isItemSoldOut == false) {
				$('#cartForm').addClass('fixed');
			}
			$('.item-tab').addClass('fixed');

		} else {
			if (item.itemOptionFlag == 'Y' && item.isItemSoldOut == false) {
				$('#cartForm').removeClass('fixed');
			}
			$('.item-tab').removeClass('fixed');

		}
	}
}

// 스크롤 이벤트 삭제
function offScrollEvent() {
	$(window).off('scroll');
}


// 상품 수량 체크 이벤트 (옵션이 없는 경우)
function initItemQuantityEvent() {
	var $itemQuantity = $('.item-quantity');

	if ($itemQuantity.length > 0) {

		// 상품 수량 +
		$itemQuantity.find('button.plus').on('click', function(e) {
			e.preventDefault();
			var $quantity = $('.quantity');
			var quantity = Number($quantity.val());
			var addedQuantity = quantity + 1;

			if (quantity == item.orderMaxQuantity) {
				alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
				return;
			}

			// 상품 최대수량 999 개 이상 넘지 않도록 수정 2017-05-10 yulsun.yoo
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
		$itemQuantity.find('button.minus').on('click', function(e) {
			e.preventDefault();
			var $quantity = $('.quantity');
			var quantity = Number($quantity.val());
			var minusQuantity = quantity - 1;
			var defaultQuantity = 1;

			if (item.orderMinQuantity > 0) {
				defaultQuantity = item.orderMinQuantity;
			}

			// 최소구매 수량 체크
			if (quantity == defaultQuantity) {
				alert('해당 상품의 최소 구매 수량은 ' + defaultQuantity + "개 입니다.");
				return;
			}

			$quantity.val(minusQuantity);

			// append ArrayRequiredItems
			setArrayRequiredItems(item.itemId, minusQuantity);

			calculate();
		});

		// 상품 수량 입력.
		$('.quantity').on('blur', function(e) {
			var $quantity = $(this);
			var defaultQuantity = 1;
			var quantity = Number($quantity.val());

			try {
				quantity = Number($(this).val());
			} catch(e) {}

			if (item.orderMinQuantity > 0) {
				defaultQuantity = item.orderMinQuantity;
			}

			// 최소구매 수량 체크
			if (item.orderMaxQuantity > 0 && quantity < defaultQuantity) {
				alert('해당 상품의 최소 구매 수량은 ' + defaultQuantity + "개 입니다.");
				$quantity.val(item.orderMinQuantity);

				// append ArrayRequiredItems
				setArrayRequiredItems(item.itemId, defaultQuantity);

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

			$('.quantity').val($(this).val());

			// append ArrayRequiredItems
			setArrayRequiredItems(item.itemId, quantity);

			calculate();
		}).on("keyup", function(e) {
			var pattern = /^[0-9]+$/;
			if (!pattern.test($(this).val())) {
				$(this).val('1');
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
	$('.option-select-box').on('click', function(e) {
		e.preventDefault();

		// 최대 구매 수량 체크
		var currentQuantity = getCurrentItemQuantity($('added-options'));

		if (currentQuantity == item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			return;
		}

		// 옵션 선택 초기화.
		initItemOption();

		$('.option-box').hide();
		$(this).closest('div').find('.option-box').show();
		$(this).closest('div').find('.option-box ul').hide().eq(0).show();

	});

	// 옵션 선택 박스 닫기
	$('.close-option-box').on('click', function(e) {
		e.preventDefault();

		$('.option-box').hide();

		// 옵션 선택 초기화.
		initItemOption();
	});


	// 전체옵션보기 버튼 이벤트
	$('.btn-option-box-type').on('click', function(e) {
		e.preventDefault();

		var $itemOptionInfo = $('.item-option-info');
		var $optionBoxType1 = $itemOptionInfo.find('.option-box-type1');
		var $optionBoxType2 = $itemOptionInfo.find('.option-box-type2');

		if ($optionBoxType1.css('display') == 'none') {
			$(this).text('전체옵션보기');
			$optionBoxType1.show();
			$optionBoxType2.hide();

		} else {
			$(this).text('이전방식보기');
			$optionBoxType1.hide();
			$optionBoxType2.show().find('ul').show();
		}
	});

	// 세부 옵션 선택 이벤트.
	$('.item-option-info .option-box').on('click', '.option-group li a', function(e) {
		e.preventDefault();

		var $optionBoxType = $(this).closest('.option-group').parent();
		var $optionGroup = $(this).closest('.option-group');
		var currentOptionGroupIndex = $optionBoxType.find('.option-group').index($optionGroup);
		var optionGroupCount = $optionBoxType.find('.option-group').length;
		var optionName = $(this).attr('data-option-name');

		// 옵션 선택 처리.
		$(this).addClass('on');
		$optionGroup.find('.option-selected-name').text(optionName);

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
				$optionBoxType.find('.option-group:lt(' + index + ')').each(function() {
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
							optionHtml += '		<li><a href="#" data-option-name="' + optionValue + '">' + getOptionImage(optionValue) + ' ' + optionValue + '</a><li>';
						}

						lastOptionValue = optionValue;
					}

				}

				// next 옵션 목록 추가.
				$optionBoxType.find('.option-group').eq(index).find('ul').append(optionHtml);
				$optionBoxType.find('.option-group').eq(index).find('.option-selected-name').text('');
			}

			$optionBoxType.find('.option-group').eq(currentIndex).find('ul').hide();
			$optionBoxType.find('.option-group').eq(index).find('ul').show();
		}

		// 옵션 선택 완료
		function completeSelectOption() {
			SELECTED_OPTION_IDS = [];

			var index = 0;
			var optionAdditionalAmount = 0;
			var optionNames = '';
			$optionBoxType.find('a.on').each(function(i) {
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

				$('.option-box').hide();

				$('.option-select-box .selected-option').text(selectedOptionSummary);
				$('.option-select-box').addClass('selected');
			}
		}
	}).on('mouseenter', '.option-group li a', function(e) {
		e.preventDefault();
		var optionName = $(this).attr('data-option-name');
		$(this).closest('.option-group').find('.option-selected-name').text(optionName);

	}).on('mouseleave', '.option-group li a', function(e) {
		e.preventDefault();
		if (!$(this).hasClass('on')) {
			$(this).closest('.option-group').find('.option-selected-name').text('');
		}

	});

	// 상품 옵션 그룹 링크
	$('.option-box-type1').on('click', '.option-group > a', function(e) {
		e.preventDefault();

		var $optionGroup = $(this).closest('.option-group');
		var $optionBoxType = $('.option-box-type1');
		var currentOptionGroupIndex = $optionBoxType.find('.option-group').index($optionGroup);

		if (item.itemOptionType == 'S') {
			var $previousOptionGroups = $optionBoxType.find('.option-group:lt(' + currentOptionGroupIndex + ')');
			var optionGroupCount = $previousOptionGroups.length;
			var selectedOptionGroupCount = $previousOptionGroups.find('a.on').length;

			if (selectedOptionGroupCount < optionGroupCount) {
				alert('위의 정보를 먼저 선택해 주세요.');
				return;
			}

			$optionBoxType.find('ul').hide();
			$optionGroup.find('a.on').removeClass('on');
			$optionGroup.find('.option-selected-name').text('');
			$optionGroup.find('ul').show();

			// 현재 이후 STEP 초기화.
			$optionBoxType.find('.option-group:gt(' + currentOptionGroupIndex + ')').each(function() {
				$(this).find('a.on').removeClass('on');
				$(this).find('.option-selected-name').text('위의 정보를 먼저 선택해 주세요.');
			});

		} else {
			if ($optionGroup.find('li').length == 0) {
				alert('위의 정보를 먼저 선택해 주세요.');
				return;
			}

			$optionBoxType.find('ul').hide();
			$optionGroup.find('a.on').removeClass('on');
			$optionGroup.find('.option-selected-name').text('');
			$optionGroup.find('ul').show();

			// 현재 이후 STEP 초기화.
			$optionBoxType.find('.option-group:gt(' + currentOptionGroupIndex + ')').each(function() {
				$(this).find('a.on').removeClass('on');
				$(this).find('.option-selected-name').text('위의 정보를 먼저 선택해 주세요.');
				$(this).find('li').remove();
			});

		}
	});

	// 텍스트 옵션 추가
	$('.item-option-info').on('click', '.btn-add-item-option', function(e) {
		e.preventDefault();

		// 선택 옵션이 있는 경우
		if (item.itemOptionType != 'T' && SELECTED_OPTION_IDS.length == 0) {
			alert('상품필수옵션을 선택해 주세요.');
			$('.option-select-box').focus();
			return;
		}

		var errorIndex = -1;
		$('.text-option-value').each(function(index) {
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

		$('.option-select-box').removeClass('selected');

		var $itemOptionInfo = $('.item-option-info');
		$itemOptionInfo.find('.selected-option').text('선택하세요.');
		var $optionBoxType1 = $itemOptionInfo.find('.option-box-type1');
		var $optionBoxType2 = $itemOptionInfo.find('.option-box-type2');

		$('.item-option-info .option-group li a').removeClass('on');

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
						optionGroupHtml += '</div>';
					}

					optionGroupHtml += '<div class="option-group">';
					optionGroupHtml += '	<a href="#"><strong>' + itemOptions[i].optionName1 + '</strong><span class="option-selected-name">선택하세요.</span></a>';
					optionGroupHtml += '	<ul>';
				}

				// 옵션항목.
				optionGroupHtml += '		<li>' + getOptionInfo(itemOptions[i]) + '</li>';

				groupTitle = itemOptions[i].optionName1;
			}

			optionGroupHtml += '	</ul>';
			optionGroupHtml += '</div>';

		} else {

			var optionGroupHtml = '';

			// option1
			optionGroupHtml += '<div class="option-group">';
			optionGroupHtml += '	<a href="#"><strong>' + item.itemOptionTitle1 + '</strong><span class="option-selected-name">선택하세요.</span></a>';
			optionGroupHtml += '	<ul>';

			var options = '';
			var optionName1 = '';
			for (var i = 0; i < itemOptions.length; i++) {
				if (itemOptions[i].optionType == 'T') {
					continue;
				}

				if (itemOptions[i].optionName1 != optionName1) {
					optionGroupHtml += '		<li><a href="#" data-option-name="' + itemOptions[i].optionName1 + '">' + getOptionImage(itemOptions[i].optionName1) + ' ' + itemOptions[i].optionName1 + '</a><li>';
				}

				optionName1 = itemOptions[i].optionName1;
			}

			optionGroupHtml += '	</ul>';
			optionGroupHtml += '</div>';


			// option2
			optionGroupHtml += '<div class="option-group">';
			optionGroupHtml += '	<a href="#"><strong>' + item.itemOptionTitle2 + '</strong><span class="option-selected-name">위의 정보를 먼저 선택해 주세요.</span></a>';
			optionGroupHtml += '	<ul>';
			optionGroupHtml += '	</ul>';
			optionGroupHtml += '</div>';

			if (item.itemOptionType == 'S3') {
				// option3
				optionGroupHtml += '<div class="option-group">';
				optionGroupHtml += '	<a href="#"><strong>' + item.itemOptionTitle3 + '</strong><span class="option-selected-name">위의 정보를 먼저 선택해 주세요.</span></a>';
				optionGroupHtml += '	<ul>';
				optionGroupHtml += '	</ul>';
				optionGroupHtml += '</div>';
			}


		}

		$optionBoxType1.find('.option-group').remove();
		$optionBoxType1.append(optionGroupHtml);

		// 옵션 선택 보기 초기
		$('.btn-option-box-type').text('전체옵션보기');
		$optionBoxType1.show();
		$optionBoxType2.hide();
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
			return '<a href="#" data-option-name="' + optionName + '" data-option-id="' + itemOption.itemOptionId + '" data-option-price="' + itemOption.optionPrice + '">' + getOptionImage(optionName, itemOption.itemOptionId) + ' ' + info + '</a>';
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

	// 선택완료된 옵션으로 옵션 아이템을 추가한다.
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
					option += '<b>' + itemOptions[i].optionName1 + '</b>'
					option += '<span>' + itemOptions[i].optionName2 + '</span>';

					optionText += itemOptions[i].optionName1 + ':' + itemOptions[i].optionName2;
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
		var $itemOptionInfo = $('.item-option-info');
		$itemOptionInfo.find('.text-option-id').each(function() {
			if (optionCount > 0) {
				optionData += '^^^';
			}

			var $textOption = $(this).closest('dd');
			var optionId = $(this).val();
			var optionName = $textOption.find('.text-option-name').val();
			var optionValue = $textOption.find('.text-option-value').val();

			//option += '<input type="hidden" name="itemOptionId" value="' + $(this) + '" />';
			option += '<input type="hidden" name="optionPrice" value="0" />';
			option += '<b>' + optionName + '</b> ' + '<span>' + optionValue + '</span>';

			optionData += optionId + '```' + optionValue;

			optionCount++;
		});

		var html = '';
		html += '<li data-option="' + optionData + '" data-option-max-quantity="' + optionMaxQuantity + '">';
		html += '	<input type="hidden" name="arrayRequiredItems" />';
		html += '	<p class="tit added-option item-name">' + option + '</p>';
		html += '	<button class="del_btn added-option-delete" title="해당 옵션 삭제">해당 옵션 삭제</button>';
		html += '	<span class="added-option-quantity amount">';
		html += '		<button type="button" class="minus" title="삭제"><img src="/content/images/icon/icon_minus.gif" alt="수량감소" /></button>';
		html += '		<input type="text" maxlength="3" class="option-quantity _number" value="1" />';
		html += '		<button type="button" class="plus" title="추가"><img src="/content/images/icon/icon_plus.gif" alt="수량증가" /></button>';
		html += '	</span>';
		html += '	<p class="price">';
		html += '		<b class="added-option-price">' + Common.numberFormat(item.price + optionAdditionAmount) + '원</b>';
		html += '	</p>';
		html += '</li>';

		var $addedOptions = $('.added-options');

		// 이미 추가된 옵션인지 체크!
		var isAlreadyAddition = false;
		$addedOptions.find('.added-option').each(function() {
			var currentText = $("<div/>").append($(this).html()).text().replace(/[\r|\n|\t]/g, '');
			var newText = $("<div/>").append(html).find('.added-option').text().replace(/[\r|\n|\t]/g, '');

			if (currentText == newText) {
				isAlreadyAddition = true;
				return false;
			}
		});

		if (isAlreadyAddition != true) {
			$addedOptions.find('ul').append(html);
		}

		// 필수 선택 옵션 초기화
		initItemOption();

		$itemOptionInfo.find('.selected-option').text(optionText);

		// 텍스트 옵션 초기화
		$itemOptionInfo.find('.text-option-value').val('');

		// 옵션 선택 박스 숨기기
		$('.option-box').hide();
		
		// 옵션 선택폼 보이기
		$addedOptions.show();

		// 금액 계산.
		calculate();
	}

	/****************************
	 * 추가된 옵션에 대한 이벤트
	 ****************************/
	// 수량 입력
	$('.added-options').on('blur', '.option-quantity', function(e) {
		var option = $(this).closest('li').data('option');
		var quantity = 1;

		try {
			quantity = Number($(this).val());
		} catch(e) {}

		// 최대구매 수량 체크.
		var currentQuantity = getCurrentItemQuantity($(this).closest('.added-options'));

		if (currentQuantity > item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			changeQuantity(option, 1);
			calculate();
			return;
		}

		// 옵션 구매 수량 체크 (옵션재고)
		var optionMaxQuantity = Number($(this).closest('li').data('option-max-quantity'));

		if (quantity > optionMaxQuantity) {
			alert('옵션 별 최대 구매 수량은 ' + optionMaxQuantity + '개 입니다.\n구매 수량을 다시 확인해주세요.');
			changeQuantity(option, 1);
			calculate();
			return;
		}

		changeQuantity(option, quantity);
		calculate();
	}).on("keyup", '.option-quantity', function(e) {
		var option = $(this).closest('li').data('option');

		var pattern = /^[0-9]+$/;
		if (!pattern.test($(this).val())) {
			changeQuantity(option, 1);
		}

		if (!canOrderByMaxQuantity($(this).closest('.added-options'))) {
			changeQuantity(option, 1);
		}

		calculate();
	});

	// 수량 +
	$('.added-options').on('click', '.plus', function(e) {
		e.preventDefault();

		var option = $(this).closest('li').data('option');

		// 최대구매 수량 체크.
		var currentQuantity = getCurrentItemQuantity($(this).closest('.added-options'));

		if (currentQuantity == item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			return;
		}

		// 옵션 최대 구매 수량 체크.
		var $quantity = $(this).closest('li').find('.option-quantity');
		var quantity = Number($quantity.val()) + 1;
		var optionMaxQuantity = Number($(this).closest('li').data('option-max-quantity'));

		if (quantity > optionMaxQuantity) {
			alert('옵션 별 최대 구매 수량은 ' + optionMaxQuantity + '개 입니다.\n구매 수량을 다시 확인해주세요.')
			return;
		}

		// 상품 수량 최대 999개 이상 되지 않도록 수정 2017-05-10 yulsun.yoo
		if (quantity > 999) {
			alert("해당 상품의 최대 구매 수량은 999개 입니다.");
			return;
		}

		changeQuantity(option, quantity);
		calculate();
	});

	// 수량 -
	$('.added-options').on('click', '.minus', function(e) {
		e.preventDefault();

		var option = $(this).closest('li').data('option');
		var $quantity = $(this).closest('li').find('.option-quantity');
		var quantity = Number($quantity.val()) - 1;

		if (quantity == 0) {
			return;
		}

		changeQuantity(option, quantity);
		calculate();
	});

	// 삭제
	$('.added-options').on('click', '.added-option-delete', function(e) {
		e.preventDefault();

		var option = $(this).closest('li').data('option');

		$('.added-options').find('li[data-option="' + option + '"]').remove();

		// 남은 옵션이 없을경우 선택폼 hide
		if ($('.added-options').find('li').length == 0) {
			$('.added-options').hide();
		}

		calculate();
	});

	// 옵션 수량 변경
	function changeQuantity(option, quantity) {
		$('.added-options').find('li[data-option="' + option + '"] .option-quantity').val(quantity);
	}

	// 최대구매 수량 체크
	function canOrderByMaxQuantity($target) {

		if (item.orderMaxQuantity == -1) {
			return true;
		}

		var currentQuantity = getCurrentItemQuantity($target);

		if (currentQuantity > item.orderMaxQuantity) {
			alert('해당 상품의 최대 구매 수량은 ' + item.orderMaxQuantity + '개 입니다.');
			return false;
		}

		return true;
	}

	// 현재 상품 추가 수량.
	function getCurrentItemQuantity($target) {
		var $addedOptions = $target.find('li');

		var currentQuantity = 0;

		$addedOptions.each(function() {
			var quantity = 0;
			try {
				quantity = Number($(this).find('.option-quantity').val());
			} catch(e) {}

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
	$('.option-select-box').on('click', function(e) {
		e.preventDefault();

		// 옵션 선택 초기화
		initItemCombinationOption($(this).closest('.item-option-group-info').data('index'));

		$('.option-box').hide();
		$(this).closest('div').find('.option-box').show();
		$(this).closest('div').find('.option-box ul').hide().eq(0).show();

		calculate();
	});

	// 옵션 선택 박스 닫기
	$('.close-option-box').on('click', function(e) {
		e.preventDefault();

		$('.option-box').hide();

		// 옵션 선택 초기화
		initItemCombinationOption($(this).closest('.item-option-group-info').data('index'));

		calculate();
	});

	// 세부 옵션 선택 이벤트
	$('.item-option-info .option-box').on('click', '.option-group li a', function(e) {
		e.preventDefault();

		var $quantity = $('.quantity');

		var $itemOptionGroupInfo = $(this).closest('.item-option-group-info');
		var index = $itemOptionGroupInfo.data('index');

		$('.option-box').hide();

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

			$itemOptionGroupInfo.find('.option-select-box .selected-option').text(optionName);
			$itemOptionGroupInfo.find('.option-select-box').addClass('selected');

			if (optionPrice != 0) {
				$itemOptionGroupInfo.find('.etc_price').val((optionPrice > 0 ? '+ ' : '- ') + Common.numberFormat(Math.abs(optionPrice)) + '원');
			}

			// 최소구매수량 고정
			$quantity.val(item.orderMinQuantity);

			if (itemOptionGroups.length == SELECTED_COMBINATION_OPTION_INFOS.length) {
				setArrayRequiredItems(item.itemId, $quantity.val());
			}

			calculate();
		}
	}).on('mouseenter', '.option-group li a', function(e) {
		e.preventDefault();
		var optionName = $(this).attr('data-option-name');
		$(this).closest('.option-group').find('.option-selected-name').text(optionName);

	}).on('mouseleave', '.option-group li a', function(e) {
		e.preventDefault();
		if (!$(this).hasClass('on')) {
			$(this).closest('.option-group').find('.option-selected-name').text('');
		}
	});

	// 옵션 구성 (초기화)
	function initItemCombinationOption(index) {
		index = index || 0;

		$('.option-select-box').removeClass('selected');

		var title = itemOptionGroups[index].title;
		var displayType = itemOptionGroups[index].displayType;

		if (displayType == 'select') {
			var $itemOptionGroupInfo = $('.item-option-group-info').eq(index);
			var $itemOptionInfo = $itemOptionGroupInfo.find('.item-option-info');
			var $optionBoxType1 = $itemOptionInfo.find('.option-box-type1');

			$itemOptionInfo.find('.selected-option').text($optionBoxType1.find('ul a').eq(0).data('option-name'));
			$itemOptionInfo.find('.option-group li a').removeClass('on');

			var optionGroupHtml = '';

			optionGroupHtml += '<div class="option-group">';
			optionGroupHtml += '	<a href="javascript:void(0);"><strong>' + $itemOptionGroupInfo.find('li.group-title').text() + '</strong><span class="option-selected-name">선택하세요.</span></a>';
			optionGroupHtml += '	<ul>';

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
			optionGroupHtml += '</div>';

			$optionBoxType1.find('.option-group').remove();
			$optionBoxType1.append(optionGroupHtml);

			$optionBoxType1.show();

			$itemOptionGroupInfo.find('.etc_price').val('');
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
 * 추가구성상품
 ***************************/
function initItemAdditionEvent() {
	// 추가구성상품 선택 이벤트
	$('.item-addition-info').on('click', '.addition-select-box', function(e) {
		e.preventDefault();

		var $additionItemBox = $(this).closest('.addition-item-box');

		var itemAdditionId = $additionItemBox.data('item-addition-id');
		var itemId = $additionItemBox.data('item-id');
		var itemPrice = $additionItemBox.data('item-price');
		var itemName = $additionItemBox.closest('li').find('p.tit').text();
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
		html += '<li class="added-list" data-item-addition-id="' + itemAdditionId + '" data-item-id="' + itemId + '" data-item-max-quantity="' + itemMaxQuantity + '" data-item-price="' + itemPrice + '">';
		html += '	<input type="hidden" name="arrayAdditionItems" />';
		html += '	<p class="tit added-option item-name">' + itemName + '</p>';
		html += '	<button class="del_btn added-option-delete" title="해당 추가상품 삭제">해당 추가상품 삭제</button>';
		html += '	<span class="added-option-quantity amount amount-sm">';
		html += '		<button type="button" class="minus" title="삭제"><img src="/content/images/icon/icon_minus.gif" alt="수량감소" /></button>';
		html += '		<input type="text" maxlength="3" class="option-quantity _number" value="1" />';
		html += '		<button type="button" class="plus" title="추가"><img src="/content/images/icon/icon_plus.gif" alt="수량증가" /></button>';
		html += '	</span>';
		html += '	<p class="price">';
		html += '		<b class="added-option-price">' + Common.numberFormat(itemPrice) + '원</b>';
		html += '	</p>';
		html += '</li>';

		var $addedItems = $('.added-items');

		// 이미 추가된 옵션인지 체크!
		var isAlreadyAddition = false;
		$addedItems.find('.added-option').each(function() {
			var currentText = $("<div/>").append($(this).html()).text().replace(/[\r|\n|\t]/g, '');
			var newText = $("<div/>").append(html).find('.added-option').text().replace(/[\r|\n|\t]/g, '');

			if (currentText == newText) {
				isAlreadyAddition = true;
				return false;
			}
		});

		if (isAlreadyAddition != true) {
			if ($addedItems.find('li.added-list').length > 0) {
				$addedItems.find('li.total').before(html);
			} else {
				$addedItems.find('ul').prepend(html);
			}
		}

		// 추가구성상품 선택폼 보이기
		$addedItems.show();

		// 금액 계산.
		calculate();
	});


	/***************************
	 * [ksh 2020-12-04] 리뉴올 PC 추가구성상품 재고체크 안함!!
	 ***************************/
	// 수량 입력
	$('.added-items').on('blur', '.option-quantity', function(e) {
		var itemAdditionId = $(this).closest('li').data('item-addition-id');
		var quantity = 1;

		try {
			quantity = Number($(this).val());
		} catch(e) {}

		changeQuantity(itemAdditionId, quantity);
		calculate();
	}).on("keyup", '.option-quantity', function(e) {
		var itemAdditionId = $(this).closest('li').data('item-addition-id');

		var pattern = /^[0-9]+$/;
		if (!pattern.test($(this).val())) {
			changeQuantity(itemAdditionId, 1);
		}

		calculate();
	});

	// 수량 +
	$('.added-items').on('click', '.plus', function(e) {
		e.preventDefault();

		var itemAdditionId = $(this).closest('li').data('item-addition-id');
		var $quantity = $(this).parent().find('.option-quantity');
		var currentQuantity = Number($quantity.val());

		changeQuantity(itemAdditionId, currentQuantity + 1);
		calculate();
	});

	// 수량 -
	$('.added-items').on('click', '.minus', function(e) {
		e.preventDefault();

		var itemAdditionId = $(this).closest('li').data('item-addition-id');
		var $quantity = $(this).closest('li').find('.option-quantity');
		var quantity = Number($quantity.val()) - 1;

		if (quantity == 0) {
			return;
		}

		changeQuantity(itemAdditionId, quantity);
		calculate();
	});

	// 삭제
	$('.added-items').on('click', '.added-option-delete', function(e) {
		e.preventDefault();

		var itemAdditionId = $(this).closest('li').data('item-addition-id');
		$('.added-items').find('li[data-item-addition-id="' + itemAdditionId + '"]').remove();

		// 남은 옵션이 없을경우 선택폼 hide
		if ($('.added-items').find('li.added-list').length == 0) {
			$('.added-items').hide();
		}

		calculate();
	});

	// 추가구성상품 수량 변경
	function changeQuantity(itemAdditionId, quantity) {
		$('.added-items').find('li[data-item-addition-id="' + itemAdditionId + '"] .option-quantity').val(quantity);
	}
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

	var $form = $('#cartForm');
	var $addedOptions = $form.find('.added-options li');
	var $addedItems = $form.find('.added-items li.added-list');

	// 단품
	var $quantity = $form.find('.quantity');
	if ($quantity.length > 0) {
		totalItemPrice = itemPrice * Number($quantity.val());
	}

	/*
	if (($quantity.length > 0 && Number($quantity.val()) < 2)
		 || ($quantity.length == 0 && $addedOptions.length == 0 && $addedItems.length == 0)) {
		$('.total-price').hide();
		return;
	}
	*/

	// 일반옵션 상품
	$addedOptions.each(function() {
		var quantity = $(this).find('.option-quantity').val();
		var extraPrice = 0;

		$(this).find('input[name=optionPrice]').each(function () {
			extraPrice += $.trim($(this).val()) == '' ? 0 : Number($(this).val());
		});

		quantity = $.trim(quantity) == '' ? 0 : Number(quantity);

		var optionPrice = (Number(itemPrice) + Number(extraPrice)) * quantity;
		totalOptionPrice += optionPrice;
		totalItemCount += quantity;

		var option = $(this).closest('li').data('option');
		$('.added-options').find('li[data-option="' + option + '"] .added-option-price').text(Common.numberFormat(optionPrice) + '원');
	});

	// 옵션조합형 상품
	for (var i = 0; i < SELECTED_COMBINATION_OPTION_INFOS.length; i++) {
		if ($quantity.length > 0) {
			var optionPrice = Number(SELECTED_COMBINATION_OPTION_INFOS[i].optionPrice) * $quantity.val();

			totalItemPrice += optionPrice;
		}
	}

	// 추가구성 상품
	$addedItems.each(function() {
		var quantity = $(this).find('.option-quantity').val();
		var itemPrice = $(this).data('item-price');

		quantity = $.trim(quantity) == '' ? 0 : Number(quantity);
		itemPrice = $.trim(itemPrice) == '' ? 0 : Number(itemPrice);

		var additionItemPrice = Number(itemPrice) * quantity;
		totalAdditionPrice += additionItemPrice;
		totalItemCount += quantity;

		var itemAdditionId = $(this).closest('li').data('item-addition-id');
		$('.added-items').find('li[data-item-addition-id="' + itemAdditionId + '"] .added-option-price').text(Common.numberFormat(additionItemPrice) + '원');
	});

	// 기본상품 합계금액
	$('.total-item-price').text(Common.numberFormat(totalItemPrice));

	// 선택옵션 합계금액
	//$('.total-option-price').text(Common.numberFormat(totalOptionPrice));

	// 선택추가상품 합계금액
	$('.total-addition-price').text(Common.numberFormat(totalAdditionPrice));

	// 총 결제금액
	$('.total-amount').text(Common.numberFormat(totalItemPrice + totalOptionPrice + totalAdditionPrice));

	//렌탈 페이는 수량이 1일때만 변화
	if ($quantity.val() == 1 ) {
		// 렌탈페이 - API 통신상 필요한 기본상품 합계 금액 저장
		$('.rental-send-amount').val(Common.numberFormat(totalItemPrice));

		// 계산후 금액 재정산
		getMonthRentalContPer(60);
	}

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

				$('.item-option-group-info').eq(i).find('.option-select-box .selected-option').text(itemOptions[j].optionName2);
				$('.item-option-group-info').eq(i).find('.option-select-box').addClass('selected');

				if (itemOptions[j].optionPrice != 0) {
					$('.item-option-group-info').eq(i).find('.etc_price').val((itemOptions[j].optionPrice > 0 ? '+ ' : '- ') + Common.numberFormat(Math.abs(itemOptions[j].optionPrice)) + '원');
				}

				// 최소구매수량 고정
				$('.quantity').val(item.orderMinQuantity);

				calculate();

				isCheck = true;
			}
		}
	}

	if ($('.item-option-group-info').length == SELECTED_COMBINATION_OPTION_INFOS.length) {
		setArrayRequiredItems(item.itemId, $('#cartForm .quantity').val());
	}
}

/////////////////////////////////////////////

// 정가 표시 방법 (정가가 숫자가 아닌 경우)
function displayItemPrice() {
	var $itemPrice = $('#item_price');
	if ($itemPrice.length > 0) {
		var itemPrice = $itemPrice.text().replace(',', '');
		var $itemPriceInfo = $('#item_price_info');

		if (isNaN(Number(itemPrice))) {
			$itemPriceInfo.hide();
		}

	}
}



// 상품 설명 탭 설정.
function initItemDescriptionTab() {
	var $itemTab = $('.item-tab li a');
	$itemTab.on('click', function(e) {
		e.preventDefault();
		$itemTab.removeClass('on');
		$(this).addClass('on');

		var index = $itemTab.index($(this));
		$('.item-tab-content').hide().eq(index).show();

		offScrollEvent();
		onScrollEvent();

	});
}

//네이버 체크아웃
function naverPayInCart() {
	var $availableItem = $(':checkbox[class^=op-available-item]');
	if ($availableItem.length == 0) {
		alert(Message.get('M00440'));
		return;
	}

	var isSuccess = true;
	var cartIds = {};
	$.each($availableItem, function(i) {

		var quantity = parseInt($('#quantity-' + $(this).val()).val());
		if (quantity <= 0) {
			isSuccess = false;
		}

		cartIds[i] = $(this).val();
	});

	if (isSuccess == false) {
		alert(Message.get("M01590")); // 상품의 수량을 확인해주세요.
		return;
	}

	var params = {
		'cartIds' : cartIds
	};
	var returnUrl = "";

	$.ajaxSetup({
		'async' : false
	});
	$.post('/open-market/naver-pay/web', params, function(response) {
		Common.responseHandler(response, function(response) {
			returnUrl = response.data;
		}, function(response) {
			alert(response.errorMessage)
		});
	}, 'json');

	return returnUrl;
}


// 섬네일 슬라이드 및 이미지 확대 팝업.
function initImageViewer() {

	// 이미지 확대보기
	$('.btn_item_view').on('click', function(e) {
		e.preventDefault();
		var itemImageId = 0;

		$('#bx-pager a').each(function(){
			if($(this).hasClass('active')){
				itemImageId = $(this).find('img').attr('id');
			}
		});

		if (itemImageId == undefined) {
			alert('등록된 상품 이미지가 존재하지 않습니다.');
			return false;
		}

		Common.popup('/item/details-image-view?itemId='+item.itemId+'&itemImageId=' + itemImageId, 'details-image-view',  858, 715, 0);
	});
}



//상품 옵션 정보 (카드 전송용 )
function getOptionValueForCart(val) {
	return val.split('|')[0];
}

// 전체 주문 수량.
function getTotalItemQuantity() {
	var totalItemQuantity = 0;
	$('#added-options input[name=arrayQuantitys]').each(function() {
		totalItemQuantity += Number($(this).val());
	});
	return totalItemQuantity;
}


// 비회원 구매 가능 상품 체크.
// 비회원 구매 불가인 경우 로그인 페이지로 이동
function checkForNonmemberOrder() {

	var nonmemberOrderType = item.nonmemberOrderType;
	if (!(isLogin == 'true' || nonmemberOrderType == '1')) {
		var message = Message.get("M00601") + '\n' + Message.get("M00602");	// 회원만 구매가 가능합니다. 로그인 페이지로 이동하시겠습니까?
		if (confirm(message)) {
			location.href = "/users/login?target="+reuqestUri;
		}
		return false;
	}
}

function naverPayInItem() {

	// 장바구니에 담을 수 있는 지 확인한다.
	if (!checkForItem('buy_now')) {
		return;
	}

	var returnUrl = "";
	alert($('#cartForm').serialize());
	$.ajaxSetup({'async': false});
	$.post('/cart/buy-now', $('#cartForm').serialize(), function(response) {
		Common.responseHandler(response, function() {
			$.post('/open-market/naver-buy-now/web', $('#cartForm').serialize(), function(response){

				Common.responseHandler(response, function(response) {
					returnUrl = response.data;
				}, function(response){
					alert(response.errorMessage)
				});
			}, 'json');
		});
	}, 'json');

	return returnUrl;
}

// 네이버 위시리스트
function naverWishInItemView() {

	// 장바구니에 담을 수 있는 지 확인한다.
	if (!checkForItem('naver_wishlist')) {
		return;
	}

	var returnUrl = "";

	$.ajaxSetup({'async': false});


	$.post('/open-market/naver-wishlist/web', $('#cartForm').serialize(), function(response){
		Common.responseHandler(response, function(response) {
			returnUrl = response.data;
		}, function(response){
			alert(response.errorMessage)
		});
	}, 'json');

	return returnUrl;
}

// 견적서 출력
function popupQuotation() {
	// 출력이 가능한 상태인지 확인한다.
	if (!checkForItem('quotation')) {
		return;
	}

	window.open('', 'popup-quotation', 'width=1145, height=' + window.innerHeight + ', top=0, left=0');

	$('#cartForm').attr('target', 'popup-quotation');
	$('#cartForm').attr('action', '/item/popup/quotation');
	$('#cartForm').submit();

	Common.loading.hide();
}

// 장바구니 담기
function addToCart() {
	// 장바구니에 담을 수 있는 지 확인한다.
	if (!checkForItem('cart')) {
		return;
	}

	$.post('/cart/add-item-to-cart', $('#cartForm').serialize(), function(response) {
		clearSelectInformation();
		Common.responseHandler(response, function() {
			Shop.getCartInfo();

			Shop.openCartWishlistLayer('cart');

			/*
			var message = Message.get("M00595") + '\n' + Message.get("M00596");	// 장바구니에 상품을 담았습니다.\n바로 확인하시겠습니까?
			if (confirm(message)) {
				location.href = '/cart';
			}
			*/
		});
	}, 'json');
}

// 바로구매
function buyNow(loginCheck) {
	// 바로구매가 가능한 지 확인한다.
	if (!checkForItem('buy_now')) {
		return;
	}

	loginCheck = loginCheck == undefined ? true : loginCheck;

	$.post('/cart/buy-now', $('#cartForm').serialize(), function(response) {
		//clearSelectInformation();
		Common.responseHandler(response, function() {
			if (isLogin == 'false' && loginCheck == true) {
				Common.popup('/users/login?target=order&popup=1&redirect=/order/step1', 'popup-login', 550, 380, 1);
			} else {
				location.href='/order/step1';
			}
		});
	}, 'json');
}

var PopupLogin = {};
PopupLogin.Callback = function() {
	buyNow(false);
};

// 장바구니/바로구매 가능한지 확인한다.
function checkForItem(target) {
	if (!(target == 'cart' || target == 'buy_now' || target == 'wishlist' || target == 'naver_wishlist' || target == 'quotation')) {
		alert('처리할 수 없습니다.');
		return false;
	}

	var $form = $('#cartForm');

	var stockFlag = item.stockFlag;
	var stockQuantity = item.stockQuantity;
	var orderMinQuantity = item.orderMinQuantity;
	var orderMaxQuantity = item.orderMaxQuantity;

	// 품절 확인.
	if (item.isItemSoldOut) {
		alert('해당 상품은 판매 종료 되었습니다.');
		return false;
	}

	// 비회원 구매 불가인 경우 로그인 페이지로 이동
	if (checkForNonmemberOrder() == false) {
		return false;
	}

	// 총 금액 체크
	var totalAmount = Number($form.find('.total-amount').text().replace(/,/g, ""));
	if (totalAmount < 0) {
		alert('상품의 구성이 잘못되었습니다. 구매가 불가능합니다.\n고객센터로 문의 바랍니다.');
		return false;
	}

	// 장바구니 체크
	var quantity = $form.find('.quantity').val();

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

		// 옵션조합형
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
		var $addedOptions = $form.find('.added-options li');

		if ($addedOptions.length == 0) {
			alert('상품필수옵션을 선택해주세요.');
			$('.option-select-box').focus();
			return false;
		}

		var optionQuantity = 0;
		$addedOptions.each(function(index) {
			var optionData = item.itemId + '||' + $(this).find('.option-quantity').val() + '||' + $(this).data('option');
			optionQuantity += Number($(this).find('.option-quantity').val());
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
	var $addedItems = $form.find('.added-items li.added-list');

	$addedItems.each(function(index) {
		var additionData = $(this).data('item-id') + '||' + $(this).find('.option-quantity').val() + '||' + $(this).data('item-addition-id');
		$('input[name=arrayAdditionItems]').eq(index).val(additionData);
	});

	return true;
}


// 관심상품 그룹 확인 레이어 보여주기
function showAddToWishListLayer() {

	if (isLogin == 'false') {
		alert('ログインしてください。');
		location.href = "/users/login?target=" + requestUri;
		return;
	}

	// 옵션 상품인 경우 옵션 선택 여부 확인.
	if (item.itemOptionFlag == 'Y') {
		// 옵션 선택 여부.
		var optionItemCount = $('input[name=arrayQuantitys]').length;

		if (optionItemCount == 0) {


			//alert('商品が選ばれていません。');		// [번역] 상품이 선택되어 있지 않습니다.
			//$('.add_option_item').focus();
			//return;

			$('.add_option_item').click();
			return false;
		}
	} else {
		clearSelectInformation();

		var template = '';
		template += '<input type="text" name="arrayItemId" value="' + item.itemId + '" />';
		template += '<input type="text" name="arrayRequiredOptions" value="" />';
		template += '<input type="text" name="arrayQuantitys" maxlength="3" value="1" />';

		$('#cart_item').append(template);

	}

	// 담기가 가능한 그룹 설정.
	// 추가할 상품 수.
	var addWishlistItemCount = $('input[name=arrayItemId]').length;
	resetWishlistItemCount(addWishlistItemCount);

	layer_open('wishlist_group_layer');
}

// 관심상품 담기.
function addToWishList() {


	if (isLogin == 'false') {
		alert('로그인해주세요.');
		location.href = "/users/login?target=" + requestUri;
		return;
	}

	var param = {
		'itemId': item.itemId
	}

	// 관심 상품으로 등록
	$.post('/wishlist/add', param, function(response) {
		Common.responseHandler(response, function() {

			Shop.getWishlistCount();
			// 확인 레이어
			Shop.openCartWishlistLayer('wishlist');

		});
		clearSelectInformation();
	}, 'json');

}

function restockNotice() {


	if (isRestockNotice == 'true' || $('.item-btn.restock').hasClass('on')) {
		return false;
	}

	if (isLogin == 'false') {
		alert('로그인해주세요.');
		location.href = "/users/login?target=" + requestUri;
		return;
	}

	var param = {
		'itemId': item.itemId
	}

	// 관심 상품으로 등록
	$.post('/restock-notice/add', param, function(response) {
		Common.responseHandler(response, function() {
			$('.item-btn.restock').addClass('on');
			alert('재입고 알림을 신청했습니다.');
		});

	}, 'json');
}

// 선택한 옵션 정보를 가져옴.
function getSelectedOptionInfo() {
	var $itemOptionInfo = getItemOptionInfo();
	var $itemOptionGroups = $itemOptionInfo.find('tr');

	var checkedOptionInfo = '';
	var checkedValues = '';
	var itemOptionIds = '';
	var extrPrices = 0;

	// 옵션 선택 여부 체크
	var radioOptionName = '';
	var index = 0;
	var hasError = false;
	$itemOptionInfo.find('input[type=radio], select').each(function() {
		var tagName = $(this).get(0).tagName;
		var $option = $(this);
		var $target = $(this);

		var isContinue = false;
		if (tagName.toLowerCase() == 'input') {
			var name = $(this).attr('name');
			if (radioOptionName != name) {
				$option = $itemOptionInfo.find('input[name=' + name + ']:checked');
				$target = $itemOptionInfo.find('input[name=' + name + ']').eq(0);

				if ($option.length == 0) {
					$.validator.validatorAlert($.validator.messages['select'].format($target.attr('title')), $target);
					$target.focus();
					hasError = true;
					return false;
				}

				isContinue = true;
			}



			radioOptionName = name;

		} else {
			if ($option.val() == '') {
				$.validator.validatorAlert($.validator.messages['select'].format($target.attr('title')), $target);
				$target.focus();
				hasError = true;
				return false;
			}
			isContinue = true;
		}

		if (isContinue) {
			// 선택한 옵션(그룹) 정보.
			var optionTitle = $option.attr('title');

			// 선택한 옵션의 정보 => '옵션ID-옵션명-가격-재고'
			var itemOption = getItemOption($option.val());
			var extraCharge = itemOption.extraPrice != '' && itemOption.extraPrice != 0 && itemOption.extraPrice > 0 ? ' (+' + Common.comma(itemOption.extraPrice) + '원)' : ' (' + Common.comma(itemOption.extraPrice) + '원)';

			checkedOptionInfo += '<li class="checked_option_' + itemOption.itemOptionId + '"><span>' + optionTitle + '</span> – ' + itemOption.optionName + extraCharge + '</li>';

			if (index > 0) {
				checkedValues += '@';
				itemOptionIds += '@';
			}
			checkedValues += getOptionValueForCart($option.val());


			extrPrices += itemOption.extraPrice;
			itemOptionIds += itemOption.itemOptionId;
			index++;
		}
	});

	if (hasError) {
		return false;
	}

	return {
		'checkedOptionInfo' : checkedOptionInfo,
		'checkedValues' : checkedValues,
		'itemOptionIds' : itemOptionIds,
		'extrPrices' : extrPrices
	}
}

// 관심 상품 추가 가능 여부
function resetWishlistItemCount(addItemCount) {
	var $wishlistGroupIds = $('input[name=wishlistGroupId]');
	$wishlistGroupIds.each(function(){
		var $wishlistGroupId = $(this);
		var $tr = $wishlistGroupId.closest('tr');
		var $wishlistItemCount = $tr.find('.wishlist_item_count');
		var $wishlistMaxItemCount = $tr.find('.wishlist_max_item_count');

		var newItemCount = Number($wishlistItemCount.text()) + addItemCount;

		$wishlistGroupId.prop('checked', false);

		if (Number($wishlistItemCount.val()) >=  Number($wishlistMaxItemCount.text())
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
	try {
		initItemOptionEvent.initItemOption();
	} catch(e) {}

	$('#cart-item').empty();

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
			// $('.item-option-group-info[data-display-type="select"]').find('.etc_price').val('');
			// $('.item-option-info').find('.selected-option').text('선택하세요.');
		} else {
			// append ArrayRequiredItems
			setArrayRequiredItems(item.itemId, $defaultQuantity);
		}
	}

	$('.added-options').hide();
	$('.added-items').hide();

	$('.added-options li').remove();
	$('.added-items li.added-list').remove();

	calculate();
}


// 옵션, 셀렉트 선택 부분 초기화.
function clearAllOptions() {
	var $itemOptionInfo = $('#item-option-info');
	// 선택 옵션 초기화 (라디오)
	$itemOptionInfo.find('input[type=radio]:checked').each(function() {
		$(this).prop('checked', false);
	});

	// 선택 옵션 초기화 (셀렉트 박스)
	$itemOptionInfo.find('select').each(function() {
		$(this).val("");
	});

}

// 리뷰 제목 클릭 이벤트 (내용 오픈)
function reviewClickEvent() {
	if ($('.review_subject').length > 0) {
		$(document).on('click', '.review_subject', function(e) {
			e.preventDefault();
			var $target = $(this).closest('tr').next('tr');
			/* 개별 오픈*/
			if ($target.css('display') == 'none') {
				$target.show();
			} else {
				$target.hide();
			}

			//$('.view-off').hide();
			//$target.show();
		});
	}
}

// 리뷰 페이징.
function paginationReview(page) {
	var param = {
		'page': page,
		'where': 'ITEM_ID',
		'query': item.itemId,
		'itemId': item.itemId
	};
	$.post('/item/review-list', param, function(response) {
		$('#review-list').html(response);
	}, 'html');
}


// QNA 페이징.
function paginationQna(page) {
	var param = {
		'page': page,
		'itemId': item.itemId
	};
	$.post('/item/qna-list', param, function(response) {
		$('#qna-list').html(response);
	}, 'html');
}


// 쿠폰 다운로드
function downloadCoupon() {
	Common.popup('/item/coupon/' + item.itemId, 'download-coupon', 800, 600);
}

function secret() {
	alert('비밀글입니다.');
}


// 추천메일 보내기
function sendRecommendMail() {
	Common.popup('/item/send-recommend-mail/' + item.itemUserCode, 'send-recommend-mail', 632, 465);
}


// append ArrayRequiredItems
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
	$('#cart-item').empty().append(template);
}

function cardBenefitsPopup() {
	var id = $('.item-card-benefit a.more').data('id');

	if (id > 0) {
		Common.popup('/item/cardBenefits-popup?benefitsId='+id, 'cardBenefits-popup', 600, 800, 1);
	}
}

function viewItemOthers() {
	$('.item-others').hide();
	$.get('/item/item-others/'+item.itemId, null,function(response) {
		$('#itemOthersArea').html(response);

		if ($('#itemOthersArea').find('.slide').length > 0) {
			$('.item-others').show();
		}
	}, 'html');

}

function viewItemRelations() {
	$('.item-relations').hide();

	$.get('/item/item-relations/' + item.itemId, null, function (response) {
		$('#itemRelationsArea').html(response);

		$("#itemRelationsArea .owl-carousel").trigger('destroy.owl.carousel');
		$("#itemRelationsArea .owl-carousel").owlCarousel({
			items : 5,
			loop : false,
			margin : 25,
			nav : true,
			dots : false
		});

		if ($('#itemRelationsArea').find('.item').length > 0) {
			$('.item-relations').show();
		}
	}, 'html');
}

function setBenefitInfo() {

	$('.item-point').hide();
	$('.item-card-benefit').hide();

	$.get('/item/benefit-info/'+item.itemId, null, function(response) {
		Common.responseHandler(response, function(response) {

			var data = response.data;

			if (data != null) {

				var pointPolicy = data.pointPolicy,
					cardBenefits = data.cardBenefits,
					itemEarnPoint = data.itemEarnPoint;

				if (itemEarnPoint != null) {

					$('dd.item-point').text(Common.numberFormat(itemEarnPoint.totalPoint) + ' P');
					$('.item-point').show();
				}

				if (cardBenefits != null) {
					var content = cardBenefits.content;
					if (content != null && content != '') {
						$('.item-card-benefit a.more').data('id', cardBenefits.benefitsId);
						$('.item-card-benefit').show();
					}
				}
			}

		});
	});
}

function setCustomerInfo() {
	$.get('/item/customer-info/'+item.itemId, null, function(response) {
		Common.responseHandler(response, function(response) {
			var data = response.data;

			if (data != null) {
				$('.item-review-count').text(data.reviewCount);
				$('.item-qna-count').text(data.qnaCount);
			}
		});
	});
}



//---------------------------

// window.onload = pageLoad;
// function pageLoad(){
// 	var head= document.getElementsByTagName('head')[0];
// 	var script= document.createElement('script');
// 	script.type= 'text/javascript';
// 	script.src= '//code.jquery.com/jquery-3.3.1.min.js';
// 	head.appendChild(script);
// };

function checkUrlForm(strUrl) {
	var expUrl = /^http[s]?\:\/\//i;
	return expUrl.test(strUrl);
}

function openRentalPg(serverType, formId){

	var ip = "";

	var curDate = new Date();
	var strDate = new Date(2021,4,30,1,30,0);
	var endDate = new Date(2021,4,30,4,30,0);

	if((curDate.getTime() > strDate.getTime()) &&(curDate.getTime() < endDate.getTime())){
		alert("코리아크레딧뷰로 차세대 시스템 오픈에 따라\n"
			+"일시 서비스 중단되었습니다.\n"
			+"- 중단 일시 : 2021.05.30 01:30 ~ 04:30 (3시간)");
		return;
	}

	if(serverType == "dev"){
		ip = "http://211.178.29.124:8082";
	}else if(serverType == "local"){
		ip = "http://10.100.1.161:8082";
	}else if(serverType == "stg"){
		ip = "http://211.178.29.124:8082";
	}else if(serverType == "prd"){
		ip = "https://rentalpg.bsrental.com:446/";
	}else{
		alert("서버 타입을 확인해주세요.");
		return false;
	}

	$('head').append('<link id="bsrental_style" rel="stylesheet" href="' + ip + '/resources/css/rentalPg.css" type="text/css" />');

	var storeId = $('#'+formId+' [name="storeId"]').val();
	var storeCode = $('#'+formId+' [name="storeCode"]').val();
	var prodName = $('#'+formId+' [name="prodName"]').val();
	var rentalPer = $('#'+formId+' [name="rentalPer"]').val();
	var prodAmt = $('#'+formId+' [name="prodAmt"]').val();
	var postcode = $('#'+formId+' [name="postcode"]').val();
	var addr = $('#'+formId+' [name="addr"]').val();
	var addrDtl = $('#'+formId+' [name="addrDtl"]').val();
	var color = $('#'+formId+' [name="prodColor"]').val();
	var returnURL = $('#'+formId+' [name="returnURL"]').val();
	var prodUrl = $('#'+formId+' [name="prodUrl"]').val();
	var offLineYn = $('#'+formId+' [name="offLineYn"]').val();
	var storeOrderNo = $('#'+formId+' [name="storeOrderNo"]').val();

	if(typeof storeId == "undefined" || storeId == null || storeId == "") {
		alert("가맹점아이디를 입력해주세요.");
		return false;
	}else if(typeof storeCode == "undefined" || storeCode == null || storeCode == "") {
		alert("가맹점코드를 입력해주세요.");
		return false;
	}else if(typeof prodName == "undefined" || prodName == null || prodName == "") {
		alert("상품명을 입력해주세요.");
		return false;
	}else if(typeof rentalPer == "undefined" || rentalPer == null || rentalPer == "") {
		alert("렌탈기간을 입력해주세요.");
		return false;
	}else if(typeof prodAmt == "undefined" || prodAmt == null || prodAmt == "") {
		alert("물건대금을 입력해주세요.");
		return false;
	}else if(typeof returnURL == "undefined" || returnURL == null || returnURL == "") {
		alert("returnURL을 입력해주세요.");
		return false;
	}else if(typeof prodUrl == "undefined" || prodUrl == null || prodUrl == "") {
		alert("상품상세URL을 입력해주세요.");
		return false;
	}

	/*2021.08.05 JHC 오프라인 호출일 경우 주소체크를 하지 않는다*/
	if(offLineYn != 'Y'){
		if(typeof addr == "undefined" || addr == null || addr == "") {
			alert("주소를 입력해주세요.");
			return false;
		}else if(typeof addrDtl == "undefined" || addrDtl == null || addrDtl == "") {
			alert("상세주소를 입력해주세요.");
			return false;
		}
	}
	/*2021.08.05 JHC 오프라인 호출일 경우 주소체크를 하지 않는다*/

	if(!checkUrlForm(returnURL)){
		alert("returnURL이 올바른 URL형식이 아닙니다.");
		return false;
	}

	var divCheck = $('#bsDivPopup').length

	if(divCheck > 0){
		return;
	}else{
		var url = ip + "/rentalPgPlatform.do";
		var popupTag = '<FORM  name="pgIframForm" id="pgIframForm"  method = "POST"  target="iFrm" action="'+url+'" >';
		popupTag = popupTag + '<input type = "hidden"  name="storeId"  value="'+storeId+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="storeCode"  value="'+storeCode+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="prodName"  value="'+prodName+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="rentalPer"  value="'+rentalPer+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="prodAmt"  value="'+prodAmt+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="postcode"  value="'+postcode+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="addr"  value="'+addr+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="addrDtl"  value="'+addrDtl+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="color"  value="'+color+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="returnURL"  value="'+returnURL+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="prodUrl"  value="'+prodUrl+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="offLineYn"  value="'+offLineYn+'" />';
		popupTag = popupTag + '<input type = "hidden"  name="storeOrderNo"  value="'+storeOrderNo+'" />';
		popupTag = popupTag + '</FORM>﻿';
		popupTag = popupTag + '<div id="bsDivPopup" class="bsRental-modal-wrap">';
		if(offLineYn != 'Y'){
			popupTag = popupTag + '<iFRAME id="iFrm" frameBorder="0" name="iFrm" width="800" height="680" id="iFrm" scrolling="yes" class="iFrm"></iFRAME>';
		}else{
			popupTag = popupTag + '<iFRAME id="iFrm" frameBorder="0" name="iFrm" width="800" height="820" id="iFrm" scrolling="yes" class="iFrm"></iFRAME>';
		}
		popupTag = popupTag + '</div>';
		$("body").append(popupTag);
		$("body").css("overflow","hidden");
		$('#pgIframForm').submit();
	}
}

window.addEventListener('message', function(e) {
	$('.modal-wrap-pop').css("display", "none");
	$('#bsDivPopup').remove();
	$('#bsrental_style').remove();
	$("body").css("overflow","auto");
});


function isPgEmptyCheck(str){
	if(typeof str == "number"){
		str = str + "";
	}
	if(typeof str == "undefined" || str == null || str == ""){
		return "empty";
	} else {
		return "notempty";
	}
}


// 렌탈구매
function buyRental(loginCheck) {
	// 바로구매가 가능한 지 확인한다.
	if (!checkForItem('buy_now')) {
		return;
	}

	loginCheck = loginCheck == undefined ? true : loginCheck;

	$.post('/cart/buy-now', $('#cartForm').serialize(), function(response) {
		//clearSelectInformation();
		Common.responseHandler(response, function() {
			if (isLogin == 'false' && loginCheck == true) {
				Common.popup('/users/login?target=order&popup=1&redirect=/order/step1', 'popup-login', 550, 380, 1);
			} else {
				location.href='/order/step1';
			}
		});
	}, 'json');
}