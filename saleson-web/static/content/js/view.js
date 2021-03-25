// ****************
/* 상품상세 전용 */
//*****************


$(function(){
	// 상단 이미지
	$('.zoom').magnify();

	var galleryThumbs = new Swiper('.item-thumbs', {
		spaceBetween: 10,
		slidesPerView: 5,
		freeMode: true,
		watchSlidesVisibility: true,
		watchSlidesProgress: true,
	});
	var photo_slider = new Swiper('.item-slider', {
		//spaceBetween: 10,
		navigation: {
			nextEl: '.pager_sec .swiper-button-next',
			prevEl: '.pager_sec .swiper-button-prev',
		},
		thumbs: {
			swiper: galleryThumbs
		}
	});

	if($(".item-thumbs .swiper-slide").length < 5){
		$(".pager_sec").addClass("min");
	}



	// 타임세일
	var item_tsTime = "12/25/2020 00:00:00";
	$(".spot_time .ts_box").downCount({
		//date: tsTime2,
		date: item_tsTime,
		offset: +9
	});


	// 기본구성상품변경
	$(".def_option_sec > .tit").on("click", function(){
		if($(this).is(".fold")){
			$(this).removeClass("fold");
			$(".def_option_sec > .def_box").stop().slideDown(400);
		} else {
			$(this).addClass("fold");
			$(".def_option_sec > .def_box").stop().slideUp(400);
		}
	});


	// Tab관련
	var $itemTab = $(".cont_tabs");
	//var tabHeight = $itemTab.height();
	$itemTab.sticky({topSpacing:54});

	// min-height 설정
	var itc_H = $(window).height() - 54 // header Height
	var $contentDetail = $("#item_content1");
	$(".item-tab-content").css("min-height", itc_H);
	//console.log(itc_H);

	/*
	$(".item-tab-content").each(function(){
		if(itc_H < $(this).innerHeight()) {
			$(this).addClass("overf");
			$(this).css("height", itc_H);
		}
		//console.log("each Comp");
	});

	$(".item-tab-content .itc_more_btn").on("click", function(e){
		e.preventDefault();
		$(this).parent().removeClass("overf");
		$(this).parent().css("height", "auto");
	});
	*/
	if(itc_H < $contentDetail.innerHeight()) {
		$contentDetail.addClass("overf");
		$contentDetail.css("height", itc_H);
		console.log("if");
	}

	$contentDetail.find(".itc_more_btn").on("click", function(e){
		e.preventDefault();
		$contentDetail.removeClass("overf");
		$contentDetail.css("height", "auto");
	});


	$itemTab.find('a').on('click', function(e) {
		e.preventDefault();
		var idx = $(this).index();

		$(this).siblings().removeClass('on');
		$(this).addClass('on');
		$('.item-tab-content').stop().fadeOut(0);
		$('.item-tab-content:eq('+idx+')').stop().fadeIn(400);

		var target = $( $(this).attr('href') );
		if( target.length ) {
			$('html, body').animate({
				//scrollTop: target.offset().top - tabHeight +1
				scrollTop: target.offset().top - 114 // header Height + Tab Height
			}, 400);
		}
	});


	// 하단 관심상품
	var view_kindItem_Owl = $(".view_wrap .bki_list").owlCarousel({
		//animateOut : 'fadeOut',
		//animateIn : 'fadeIn',
		items : 5,
		loop : false,
		margin : 25,
		autoplay : false,
		autoplayTimeout : 5000,
		autoplaySpeed : 2000,
		//autoplayHoverPause : true,
		nav : true,
		dots : false
	});
});


$(document).ready(function(){
	// 옵션선택 화면Fix
	$(window).on("scroll", function () {
		var wd_Top = $(window).scrollTop();
		var wd_Btm = $('body').height() - $(window).height() - wd_Top;
		var itemTC_Top = $('.item_tabc_sec').offset().top;
		var Btm_H = $("#footer").innerHeight() + $(".btm_kind_sec").innerHeight();
		//var Btm_H = $(".btm_kind_sec").offset().top;
		//console.log(Btm_H);

		if ((itemTC_Top - 54) <= wd_Top && 0 < (wd_Btm - Btm_H)) {
			//var bfSec_H = $(".btm_fix_sec").innerHeight();
			//$(".def_option_sec").css("margin-bottom", bfSec_H);
			$(".scr_fix_sec").addClass("fix");

			//console.log("Safe");
		} else {
			//$(".def_option_sec").css("margin-bottom", 0);
			$(".scr_fix_sec").removeClass("fix");

			//console.log("Out");
		}
	});
});



/*
function item_tabFn(){
	$('.tab_list03 li a').on('click', function(e){
		e.preventDefault();
		var idx = $(this).parent().index();
		$(this).parent().siblings().removeClass('on');
		$(this).parent().addClass('on');
		$('.tab_container > div').hide();
		$('.tab_container > div').eq(idx).show();
	});
}
*/

// 세트상품 옵션박스 - 201007
/*
function set_optionV(){
	$(".set_box .set_btn").click(function(e){
		e.preventDefault();
		if($(this).parent().hasClass("view")){
			$(this).parent().removeClass("view");
			$(this).parent().find(".set_det").stop().slideUp(400);
		} else {
			$(this).parent().addClass("view");
			$(this).parent().find(".set_det").stop().slideDown(400);
		}
	});
}
*/
