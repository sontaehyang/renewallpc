// 관리자팝업 이미지맵
$(window).on("load resize", function() {
	$('map').imageMapResize();
});


var lastScrollTop = 0;
$(window).on("scroll", function() {
	var st = $(this).scrollTop();
	if(st < lastScrollTop || st < 95) {
		// console.log("Scr Up");
		$('body').removeClass('scrolling_down');
	}
	else {
		// console.log("Scr Down");
		$('body').addClass('scrolling_down');
	}
	lastScrollTop = st;
});

//푸터 스크롤탑 적용

$(function(){
	$('.btn_scroll_top').click(function(){
		$('html, body').animate({scrollTop : 0}, 400);
		return false;
	});

});



//옵션버튼 선택시 옵션 영역 노출 버튼 - view_wrap 하단 적용
$(function(){
	var btnOpen = $('.view_wrap .foot_menu_m .btn_open');
	$('.view_wrap .foot_menu_m .btn_open, .view_wrap .bgDim').on('click', function(){

		$('.view_wrap .bgDim').height($(document).height()).toggle();
		$('.option_area').slideToggle(500, function(){
			$('.btn_open div').stop().toggleClass('on');
			$('html, body').css({'overflow':'hidden'});

			if(!$('.btn_open div').hasClass('on')){
				$('html, body').css({'overflow':'visible'});
			}
		});
	});
});

//메인 - 공지사항 영역 탭
$(function(){
	$('.main_info li').click(function(e){
		e.preventDefault();
		var tab_id = $(this).attr('data-tab');

		$('.main_info li').removeClass('on');
		$('.tab_contents').removeClass('on');

		$(this).addClass('on');
		$("#"+tab_id).addClass('on');
	});
});

//상세페이지 공유버튼 모달창

$(function(){
	$('.share_modal').hide();

	$(' .btn_share_wrap button').on('click', function(e){
		e.preventDefault();
		console.log(this);
		$('.share_modal').show();
		$('body').css({'overflow':'hidden'});

	});

	$(' button.modal_close').on('click', function(e){
		e.preventDefault();
		console.log(this);
		$('.share_modal').hide();
		$('body').css({'overflow':'auto'});

	});
});




$(document).ready(function(){
	// 상단여백 초기세팅
	var hd_H = $("#header_wrap").innerHeight();
	$("#wrap").css("padding-top", hd_H);


	setupClassFn(); //브라우저 체크 공통

	if($('.main_con').size() != 0){
		mains(); //메인
	}
	if($('.header_search').size() != 0){
		search(); //검색
	}
	if($('.side_menu').size() != 0){
		gnb(); //메뉴
	}
	if($('.view_wrap').size() != 0){
		product(); //제품
	}
	if($('.product_wrap').size() != 0){
		product(); //제품 (카테고리)
	}

	if($('.add_product').size() !=0){
		add_product(); // 추가 상품 슬라이드
	}
	if($('.relation').size() !=0){
		relation(); //관련 상품 슬라이드
	}
	if($('.view_detail_slider').size() !=0){
		product_detail(); //제품 확대
	}
	if($('.review_rating_info').size() !=0){
		review_star(); //별점 주기
	}
	if($('.mypage_wrap').size() !=0){
		mypage(); //마이페이지
	}
	if($('.cart_wrap').size() !=0 || $('.payment_wrap').size() !=0){
		delievery_tip();
	}
	if($('.order_wrap').size() !=0){
		order(); //결제
	}
	if($('.customer_wrap').size() !=0){
		faq(); //FAQ
	}
	if($('.login_tab').size() != 0){
		login_tab(); //로그인탭메뉴
	}

	if($('.search_result').size() !=0){
		exhibitions(); //셀렉트 탭메뉴
	}
	if($('.pop_filter').size() !=0){
		mFilter(); //셀렉트 탭메뉴
	}

	if($('.sort-product').size() !=0){
		chartSlide();
	}
	// comment(); //댓글
	/*combostar(); //별점 주기*/

	// 매입단가표 탭
	chartTab();
});

function setupClassFn(){
	var _cl = BrowserDetect.browser;
	$('.con').addClass(_cl);
}

//메인
function mains(){

	// 메인 비주얼
	setMainVisual();

	// 타임세일
	// setTimeSaleSlider();

	// 추천상품
	// setMdChoiceSlider();

	// 신상품
	// setNewProductSlider();

	// 한정수량
	//setLimitedProductSlider();

	// 배너 슬라이드
	setBannerSlider();

	// 리뷰 슬라이드
	// setReviewSlider();

	// 이벤트
	setMainEventSlider();

	// 카테고리 랭킹
	// mainBestItemsEvent();

	/* Main LayerPopup - 210504 - lmo */
	$(".close-layer, .main-overlay").on("click", function() {
		if($(".main-layer").hasClass("layer-ms010205")) {
			$(".layer-ms010205 iframe")[0].contentWindow.postMessage('{"event":"command","func":"' + 'stopVideo' + '","args":""}', '*');
			console.log("stop");
		}
		$(".main-layer").stop().fadeOut(0);
		$(".main-overlay").hide();
		$("html, body").css("overflow-y", "visible");
	});
}

/* Main LayerPopup - 210504 - lmo */
function OpenMainLayer(popupName) {
	$("."+popupName).stop().fadeIn(400);
	$(".main-overlay").stop().fadeIn(400);
	$("html, body").css("overflow-y", "hidden");
}
// 매입단가표 탭 - 210720
function chartTab () {
	$('.chart-slide .swiper-slide').on('click', function(e){
		e.preventDefault();
		$('.chart-slide .swiper-slide').removeClass('active');
		$(this).addClass('active')
	})
	$('.relation-box a').on('click', function(e){
		e.preventDefault();
		$('.relation-box a').removeClass('active');
		$(this).addClass('active')
	});
	$('.item-box a').on('click', function(e){
		e.preventDefault();
		$('.item-box a').removeClass('active');
		$(this).addClass('active')
	});
}

function setMainVisual() {
	// 메인 비주얼
	var main_visual = new Swiper('.main_visual', {
		autoHeight: true,
		pagination: {
			el: '.swiper-pagination',
			type:'fraction',
			clickable: true,
		},
		slidesPerView: 1,
		loop:true,
		autoplay: {
			delay: 5000,
			disableOnInteraction: false,
		}
	});
	// 스와이퍼 버튼 멈춤&시작 동작
	$(".swiper-button-pause").click(function(){
		main_visual.autoplay.stop();
		$(this).removeClass('on');
		$(".swiper-button-play").addClass('on');
	});
	$(".swiper-button-play").click(function(){
		main_visual.autoplay.start();
		$(this).removeClass('on');
		$(".swiper-button-pause").addClass('on');
	});
}

function setMainEventSlider() {
	// 기획전
	var main_event_slider = new Swiper('.main_event_slider', {
		//  navigation: {
		//    nextEl: '.main_event_next',
		//    prevEl: '.main_event_prev',
		// },
		slidesPerView: 1,
		loop:true,
		autoplay: {
			delay: 3000,
			disableOnInteraction: false,
		}
	});
}

function chartSlide() {
	var chart_slide = new Swiper('.chart-slide', {
		slidesPerView: 4,
		slidesPerGroup: 4,
		loop: true,
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		},
	});
}

//function setNewProductSlider(){

// 신상품
//	var new_product_slider = new Swiper('.new_product_slider', {
//		slidesPerView: 2,
//		slidesPerGroup: 2,
//		spaceBetween: 10,
//		navigation: {
//			nextEl: '.new_product_next',
//			prevEl: '.new_product_prev',
//		}
//	});
//}
function setLimitedProductSlider_H(){
	var sd_H = $(".limited_product_slider .swiper-slide").innerHeight();
	$(".limited_product_slider").css("height", sd_H*3);
}


function setLimitedProductSlider(){
	setLimitedProductSlider_H();
//#한정수량_득템찬스

	var limited_product_slider = new Swiper('.limited_product_slider', {
		slidesPerView: 1,
		slidesPerColumn: 3,
//slidesPerColumnFill: 'row',
		//slidesPerGroup: 1,
		spaceBetween: 30,
//loop: true,
//loopFillGroupWithBlank: false,
		pagination: {
			el: '.swiper-pagination',
			clickable: true,
		},

	});
	limited_product_slider.on('slideChange', function () {
		setLimitedProductSlider_H();
	});
}

function setBannerSlider(){
	var banner_slider = new Swiper('.banner_slider', {
		slidesPerView: 'auto',
		spaceBetween: 30,
		loop: true,
	});
}

function setReviewSlider(){
	var review_slider = new Swiper('.review_slider', {
		slidesPerView: 'auto',
		spaceBetween: 30,
		loop: true,
	});
}

function setTimeSaleSlider() {
	/* Timer */
	var tsTime_list = new Array();

	$(function() {
		$('.time_sale .ts_btm_box').each(function() {
			tsTime_list.push($(this).data('spot-format-date'));
		});

		if (tsTime_list.length > 0) {
			$(".time_sale .time_sale_list").each(function (index) {
				$(this).find(".ts_timer").downCount({
					date: tsTime_list[index],
					offset: +9
				});
			});
		}
	});

	new Swiper('.time_sale_slider', {
		grabCursor: false,
		centeredSlides: false,
		slidesPerView: 'auto',
		loop: false,
	});

	$('.page_num').removeClass('swiper-pagination-fraction');
}

/*function setMdChoiceSlider() {
	// 추천상품
	var md_choice_slider = new Swiper('.md_choice_slider', {
		effect: 'coverflow',
		grabCursor: true,
		centeredSlides: true,
		slidesPerView: 'auto',
		loop:true,
		coverflowEffect: {
			rotate: 30,
			stretch: -10,
			depth: 50,
			modifier: 1,
			slideShadows : false,
		},
		// autoplay: {
		//     delay: 3000,
		//     disableOnInteraction: false,
		// },
		pagination: {
			el: '.page_num',
			type: 'fraction',
			renderFraction: function (currentClass, totalClass) {
				return '<span class="active ' + currentClass + '"></span>' +
					' / ' +
					'<span class="total ' + totalClass + '"></span>';
			}
		}
	});
	$('.page_num').removeClass('swiper-pagination-fraction');
}*/

function mainBestItemsEvent() {
	// 베스트 탭
	$('.goods_con > div').eq(0).show();
	$('.goods_list li:nth-child(2n), .hot_list li:nth-child(2n)').addClass('right');
	$('.goods_tab li a').on('click', function(e){
		e.preventDefault();
		var idx = $(this).parent().index();
		$(this).parent().siblings().removeClass('on');
		$(this).parent().addClass('on');
		$('.goods_con > div').hide();
		$('.goods_con > div').eq(idx).show();
	});
}

//검색
function search(){
	//검색 모달창 열기버튼
	$('.search a').on('click', function(e){
		e.preventDefault();
		$('.header_search').show();
		$('body').css({'overflow':'hidden'});

	});
	//검색 모달창 닫기버튼
	$(' button.modal_close').on('click', function(e){
		e.preventDefault();
		console.log(this);
		$('.header_search').hide();
		$('body').css({'overflow':'auto'});

	});

	//검색 모달창 > 인기,최근 검색어 탭
	$('.search_box .tab_con li').on('click', function(e){
		e.preventDefault();
		var tab_con_id = $(this).attr('data-tab');
		console.log(tab_con_id)

		$('.search_box .tab_con li').removeClass('on');
		$('.tab_contents_con').removeClass('on');

		$(this).addClass('on');
		$("#"+ tab_con_id).addClass('on');
	});

	//$('#headerSearch').on('click', function(e){
	//	e.preventDefault();
	//	$('.history').show();
	//
	//});

	//최근검색어
	//$('.history_close button').on('click', function(e){
	//	e.preventDefault();
	//	$('.header_search').hide();
	//	$('.history').hide();
	//});
}



//메뉴
function gnb(){
	$('.side_menu_btn').on('click',function(e){
		e.preventDefault();

		$categoryCode = $('input[name="categoryCode"]');
		if ($categoryCode.size() > 0) {
			var code = $categoryCode.val();
			$.each($('.side_menu').find('.threeDepthPop'), function() {

				var success = false;
				$.each($(this).find('li'), function() {

					if ($(this).data('category-url') == code) {

						$('.fourDepthBox').hide();
						$('.fiveDepthBox').hide();

						var level = $(this).data('category-level');
						if (level == "4") {
							$('.op-two-child-category-' + $(this).data('category-two-depth-url')).show();
							$('.op-three-child-category-' + $(this).data('category-three-depth-url')).show();
						} else if (level == "3") {
							$('.op-two-child-category-' + $(this).data('category-two-depth-url')).show();
						}

						success = true;
						return false;
					}
				});

				if (success) {

					$(this).show();
					return false;
				}
			});
		}

		$('.side_menu').animate({left: '0%'});
		$('.side_menu_close').fadeIn();
		$('#wrap').css({'position':'fixed'});
		/*[2017-06-01 최정아] 스크롤 발생하지 않는 페이지에서 페이지 하단에 가로로 하얀 배경이 생겨 'position':'fixed'만 사용
		 * $('#wrap').css({'overflow':'hidden' , 'position':'fixed'});*/
		$('.side_menu_bgDim.bgDim').height($(document).height()).toggle();
		return false;
	});
	$('.side_menu_close, .side_menu_bgDim.bgDim').on('click',function(e){
		e.preventDefault();
		$('.side_menu').animate({left: '-100%'});
		$('.side_menu_close').hide();
		$('#wrap').css({'overflow':'' , 'position':''});
		$('.side_menu_bgDim.bgDim').height($(document).height()).toggle();
		$('.gnb .twoDepthBox').slideUp('fast');
		$('.gnb .oneDepth').removeClass('on');
		$('.gnb > ul > li').removeClass('on');
		$('.threeDepthPop').hide();
		$('.fourDepthBox').hide();
		$('.fiveDepthBox').hide();
		return false;
	});
	$('.gnb .oneDepth').click(function(e) {
		e.preventDefault();
		$('.gnb .oneDepth').removeClass('on');
		$('.gnb > ul > li').removeClass('on');
		$('.gnb .twoDepthBox ul li a').removeClass('on');
		$('.gnb .twoDepthBox').slideUp('fast');
		if (!$(this).next('.twoDepthBox').is(':visible')) {
			$(this).parent('li').addClass('on');
			$(this).next('.twoDepthBox').slideDown();
		}
	});

	$('.threeDepth_btn .arr').click(function(e) {
		e.preventDefault();
		var categoryUrl = $(this).data('category-url');

		$('.threeDepthPop').hide();
		$('.' + categoryUrl).show();
	});

	$('.threeDepth+.link').click(function(e) {
		e.preventDefault();
		$('.threeDepthPop > ul > li').removeClass('on');
		$('.fourDepthBox').slideUp('fast');
		$('.fiveDepthBox').slideUp('fast');
		if (!$(this).parent().next('.fourDepthBox').is(':visible')) {
			$(this).parent().parent().addClass('on');
			$(this).parent().next('.fourDepthBox').slideDown();
		}
	});

	$('.fourDepth+.link').click(function(e) {
		e.preventDefault();
		$('.fiveDepthBox > ul > li').removeClass('on');
		$('.fiveDepthBox').slideUp('fast');
		if (!$(this).parent().next('.fiveDepthBox').is(':visible')) {
			$(this).parent().parent().addClass('on');
			$(this).parent().next('.fiveDepthBox').slideDown();
		}
	});

	$('.fiveDepthBox span').click(function() {

	});

	$('.threeDepthPop .close_btn').click(function(e) {
		$('.threeDepthPop').hide();
		$('.fourDepthBox').hide();
		$('.fiveDepthBox').hide();
	});
}

//제품
function product(){
	// 상세정보 더보기 - 201218
	setTimeout(function(){
		if (600 <= $("#item_content1").innerHeight()){
			$(".tab_detail").addClass("overf");
			//console.log("dd");
		}
	},2000);

	$('.itc_more_btn').click(function(e){
		e.preventDefault();
		var contentHeight = $('.detail_explain_box').offset().top;
		var classList = document.querySelector('.detail_explain_box').classList;
		if(classList.contains('show_step')){
			classList.remove('show_step');
		}else{
			classList.add('show_step');
		}

	});
	//제품 정렬
	$('.styled_select .filter_sort').on('click', function(e){
		if(!$(this).hasClass('on')){
			$(this).addClass('on');
			$(this).next('.filter_list_sort').show();
			$(this).nextAll('#dimmedLayer').show();
		} else {
			$(this).removeClass('on');
			$(this).next('.filter_list_sort').hide();
			$(this).next('#dimmedLayer').hide();
		}
	});
	$('.styled_select #dimmedLayer, .filter_list_sort a').on('click', function(e){
		var bool = $(this).parent().parent().hasClass('filter_list');
		var txt = $(this).text();
		if(bool){
			$('.styled_select .filter_sort').text(txt);
		}
		$('.styled_select .filter_sort').removeClass('on');
		$('.styled_select .filter_list_sort').hide();
		$('.styled_select #dimmedLayer').hide();
	});

	//카테고리
	var isBoolean = false;
	$('.category_list li:lt(8)').show();
	$('.category_more_btn a').on('click', function(e){
		e.preventDefault();
		if(!isBoolean){
			$('.category_list li').show();
			$(this).find('span').text('닫기').end().addClass('active');
			isBoolean = true;
		} else {
			$('.category_list li').not(':lt(8)').hide();
			$(this).find('span').text('더보기').end().removeClass('active');
			isBoolean = false;
		}
	});

	//제품 슬라이드
	var view_img_slider = new Swiper('.view_img_slider', {
		pagination: {
			el: '.swiper-pagination',
			clickable: true,
		}
	});

	//탭 내용
	$('.op-mypage-review').on('click', function(e){
		e.preventDefault();
		if($(this).hasClass('on')){
			$(this).removeClass('on');
			$(this).parent().next('.op-mypage-review-detail').hide();
		} else {
			$('.op-mypage-review').removeClass('on');
			$(this).addClass('on');
			$('.op-mypage-review').next('.op-mypage-review-detail').hide();
			$(this).parent().next('.op-mypage-review-detail').show();
		}
	});

	//제품 상세 탭 리스트
	var $eventTab = $('.product_detail_bar');
	var tabHeight = $eventTab.height();
	var $eventCon = $('.tab_container');

	$('.tab_list03 li a').on('click', function(e){
		e.preventDefault();
		var idx = $(this).parent().index();
		$(this).parent().siblings().removeClass('on');
		$(this).parent().addClass('on');
		$('.tab_container > div').hide();
		$('.tab_container > div').eq(idx).show();
	});
	// $eventTab.sticky();
	$eventTab.find('a').on('click', function(e) {
		e.preventDefault();
		var target = $(this).parent('li').index();
		if( $('.tab_container > div').length ) {
			$('html, body').animate({
				scrollTop: $('.tab_container > div').eq(target).offset().top - $('#header_wrap').height()+36
			}, 300);
		}
	});

	//탭 내용
	$('.list-data-subject').on('click', 'a', function(e){
		e.preventDefault();

		if ($(this).hasClass('secret')) {
			alert('비밀글입니다.');
			return false;
		}

		if ($(this).hasClass('on')) {
			$(this).removeClass('on');
			$(this).parent().next('.sub_txt').hide();
		} else {
			$('.main_txt a').removeClass('on');
			$(this).addClass('on');
			$('.main_txt').next('.sub_txt').hide();
			$(this).parent().next('.sub_txt').show();
		}
	});

	// 세트상품 옵션박스 - 201007
	$(".set_box .set_btn").on("click", function(e){
		e.preventDefault();

		if($(this).parent().is(".view")){
			$(this).parent().removeClass("view");
			$(this).parent().find(".set_det").stop().slideUp(400);
		} else {
			$(this).parent().addClass("view");
			$(this).parent().find(".set_det").stop().slideDown(400);
		}
	});

}

// 기획전 상세 셀렉트 탭
function exhibitions(){
	var $selectTab = $('.search_result.sel');
	if(!$selectTab.length ) { return }
	$selectTab.sticky({ topSpacing:43 });
}

function review_star(){
	$('.review_rating_info .star').on('click', function(){
		var idx = $(this).index();
		$('.rating_point').val(idx+1);
		var $img_obj, img_src;
		$('.review_rating_info .star').each(function(index){
			$img_obj = $(this).find('img');
			img_src = $img_obj.prop('src');
			if(idx < index){
				$img_obj.prop('src', img_src.replace('_on.gif','_off.gif'));
			}else{
				$img_obj.prop('src', img_src.replace('_off.gif','_on.gif'));
			}
		});
	});
}

//제품 확대
function product_detail(){
	//제품 자세히보기 슬라이드
	$('p.img_number span.active').text($('.view_detail_slider .swiper-slide').size() > 1 ? '1' : '0');
	// 확대이미지
	var view_detail_slider = new Swiper('.view_detail_slider', {
		onSlideChangeEnd: function (swiper) {
			$('p.img_number span.active').text(view_detail_slider.activeIndex + 1);
			view_thumb_slider.slideTo(view_detail_slider.activeIndex);
			// 섬네일 활성화
			$('.view_thumb_slider a').removeClass('on');
			$('.view_thumb_slider a').eq(view_detail_slider.activeIndex).addClass('on');
		}
	});
	// 제품 자세히보기 섬네일
	var view_thumb_slider = new Swiper('.view_thumb_slider',{
		slidesPerView: 5,
		spaceBetween: 13
	});

	// 섬네일 이미지 링크
	var $thumbnail = $('.view_thumb_slider a');
	// 전체이미지 수
	$('p.img_number span.total').text($thumbnail.size());
	// 섬네일 클릭 이벤트
	$thumbnail.on('click', function(e) {
		e.preventDefault();
		var index = $thumbnail.index($(this));
		view_detail_slider.slideTo(index);
		$('p.img_number span.active').text((index + 1));
	});
	// 확대 클릭한 섬네일 활성화.
	var currentIndex = $thumbnail.index($('.view_thumb_slider a.on'));
	$thumbnail.eq(0).click();
}
//추가 상품 슬라이드
function add_product(){
	var add_product_slider = new Swiper('.add_product_slider', {
		grabCursor: false,
		centeredSlides: false,
		slidesPerView: 'auto',
		loop:false,
		//pagination: {
		//	el: '.swiper-pagination',
		//	clickable: true,
		//},
		spaceBetween: 14
	});

	/* 추가상품 탭추가  -210513 - lmo */
	var $selectprdAdd = $(".add_product .inner > select");
	var $sliderprdAdd = $(".ap_slider_sec .add_product_slider");
	$sliderprdAdd.eq(0).addClass("view");
	$selectprdAdd.change(function() {
		$sliderprdAdd.removeClass("view");
		var indexSelect = $(".add_product .inner > select option").index($(".add_product .inner > select option:selected"));
		//console.log(indexSelect);
		$sliderprdAdd.eq(indexSelect).addClass("view");
	});
}
//관련 상품 슬라이드
function relation(){
	var relation_slider = new Swiper('.relation_slider', {
		grabCursor: false,
		centeredSlides: false,
		slidesPerView: 'auto',
		loop:false,
		//pagination: {
		//	el: '.swiper-pagination',
		//	clickable: true,
		//},
		spaceBetween: 15
	});
}

//마이페이지
function mypage(){
	//마이페이지 메뉴
	$('.mapage_menu .oneDepth').click(function(e) {
		e.preventDefault();
		$('.menu .oneDepth').removeClass('on');
		$('.menu > ul > li > a').removeClass('on');
		$('.menu .twoDepthBox ul li a').removeClass('on');

		$('.menu .twoDepthBox').slideUp('fast');
		if (!$(this).next('.twoDepthBox').is(':visible')) {
			$(this).addClass('on');
			$(this).next('.twoDepthBox').slideDown();
		}
	});



	//기간검색
	$('.date_search .date_search_btn').on('click', function(e){
		e.preventDefault();
		$(this).toggleClass('on');
		if($(this).hasClass('on')){
			$(this).text('닫기');
		} else {
			$(this).text('기간검색');
		}
		$('.date_search .date_search_con').toggle();
	});

	//주문 배송 조회
	$('.order_view_btn').on('click', function(e){
		e.preventDefault();
		$(this).toggleClass('on');
		$(this).next('.order_view_con').toggle();
	});
	$('.active_con ul li').eq(0).children('.order_view_btn').trigger('click');
}

//결제
function order(){
	//개인정보 수집동의
	$('.agree_wrap .open_btn').on('click', function(e){
		e.preventDefault();
		$(this).toggleClass('on');
		$('.agree_box').toggle();
		if($(this).hasClass('on')){
			$(this).find('span.txt').text('전문닫기');
		} else {
			$(this).find('span.txt').text('펼쳐보기');
		}
	});

	$('.layer02_close').on('click', function(e){
		e.preventDefault();
		$(this).parent().parent().parent().hide();
		$('.layer_pop_bg').hide();
	});

}

// 장바구니, 주문결제 배송비관련 팝업
function delievery_tip(){
	$('.delivery_wrap .delv_btn').on('click', function(e){
		e.preventDefault();
		$(this).next('.delievery_tip').addClass('show');
		//console.log("oo");
	});
	$('.delievery_tip .delievery_close').on('click', function(e){
		e.preventDefault();
		$(this).parent().removeClass('show');
		console.log("dd");
	});
}

//FAQ
function faq(){
	//FAQ 리스트
	$('.faq_list ul li .oneDepth').on('click', function(){
		$('.faq_list .oneDepth').removeClass('on');
		$('.faq_list .twoDepthBox').slideUp('fast');
		if (!$(this).next('.twoDepthBox').is(':visible')) {
			$(this).addClass('on');
			$(this).next('.twoDepthBox').slideDown();
		}
	});
}
//로그인탭메뉴
function login_tab(){
	$('.tab_list04 li a').on('click', function(e){
		e.preventDefault();
		var idx = $(this).parent().index();
		$(this).parent().siblings().removeClass('on');
		$(this).parent().addClass('on');
		$('.login_con > div').hide();
		$('.login_con > div').eq(idx).show();
	});

}


// 2020.08.04 필터 추가
function mFilter(){
	$('.pop_filter').on('click','.filter_m_wrap .tit',function(){
		$(this).toggleClass('on');
		$(this).next('.filter_m_wrap .content').stop().slideToggle();
	});
	//filter 버튼 클릭 시, 필터 팝업 뜸
	$('.filter_wrap').on('click','.btn_filter',function(){
		$('.pop_filter').show();
		$('.layer_pop_bg').show();
	});
	$('.pop_filter').on('click','.pop_close',function(){
		$('.pop_filter').hide();
		$('.layer_pop_bg').hide();
	});
}