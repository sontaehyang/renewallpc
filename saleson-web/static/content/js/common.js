//var sticky_id; //퀵메뉴 변수 1
//var sticky_id_d; //퀵메뉴 변수 2
//var origin_val = {} //퀵메뉴 변수 3

$(document).ready(function(){

	// 푸터 게시판 탭 - 201112
	$('.ft_bd_nav > a').on("click", function(e){
		e.preventDefault();
		$('.ft_bd_nav > a').removeClass('acti');
		$(this).addClass('acti');
		var ft_bdN = $('.ft_bd_nav > a').index(this);
		$('.ft_bd > .ft_bd_cont').stop().fadeOut(0);
		$('.ft_bd > .ft_bd_cont:eq('+ ft_bdN +')').stop().fadeIn(400);
	});

	// 헤더 상단배너
	var Hd_Ban_Owl = $(".hd_banner > div").owlCarousel({
		//animateOut : 'fadeOut',
		//animateIn : 'fadeIn',
		items : 1,
		loop : true,
		margin : 0,
		autoplay : true,
		autoplayTimeout : 5000,
		autoplaySpeed : 2000,
		autoplayHoverPause : true,
		nav : true,
		dots : false
	});

	// 최근,추천검색어
	/*$(".header_search input[type='search']").on("focus", function(){
		$(".header_search .sch_list_box").show();
	}).on("keyout", function(){
		$(".header_search .sch_list_box").hide();
	});*/

	$(".header_search .sch_list_tabs > a").on("click", function(e){
		e.preventDefault();
		$(".header_search .sch_list_tabs > a").removeClass("on");
		$(this).addClass("on");
		var hsch_t = $(".header_search .sch_list_tabs > a").index(this);
		$(".header_search .searchArea").stop().fadeOut(0);
		$(".header_search .sch_list_box > .searchArea:eq("+ hsch_t +")").stop().fadeIn(400);
	});


	//전체메뉴 존재 체크
	if($('.all_menu').size() !=0){
		allMenu();
	}
	//main page 존재 체크
	if($('.main').size() !=0){
		mainAd();
		//Msec02_Timer();
		Msec03_Slider();
		//Msec04_Eff();

		//mdChoice();
		//mainNew();
		//uiTabsMenu();
		//tabMenuSlider();
	}
	// 카테고리 베스트랭킹 슬라이드 체크
	if($('.best_rank').size() != 0){
		bestRank();
	}
	//faq 페이지 존재 체크
	if($('#faq').size() != 0){
		faq();
	}
	//장바구니, 주문서 무료배송 팝업 체크
	if($('.op-cart-item-table').size() != 0 || $('.op-order-item-table').size() != 0) {
		cart_tip();
	}
	// 텝메뉴가 있을때 실행
	if($('.op-event-wrap').size() != 0){
		initEventTab();
	}
	// 퀵메뉴가 있을때 실행
	if($('.quick_menu').size() != 0){
		quickMenuPagination();
		//stiky_setup(); //퀵메뉴
	}

	/* 사용처 모르겠음 */
	//allMenuMain();
	//date();
	//wishItem();

	/* 필터 */
	$('.btn_filter').on('click',function(){
		$(this).toggleClass('on');
		$('.category_filter').slideToggle();
	});
});

$(window).scroll(function(){
	var Wsc = $(window).scrollTop();

	if(0 < Wsc ){
		$("#wrap").css("padding-top", 55);
		$("#header").addClass("scr");
		$("#floating").addClass("scr");
	} else {
		$("#wrap").css("padding-top", 196);
		$("#header").removeClass("scr");
		$("#floating").removeClass("scr");
	}
});

// ****************
/* all menu */
//*****************
function allMenu(){
	$(".btn_menu").on("click", function(e){
		e.preventDefault();

		function resetClass(){
			$(".btn_menu").removeClass("on");
			//$(".gnbs").removeClass("show");
			$(".gnbs").stop().slideUp(200);
		}
		if($(this).attr('class') == 'btn_menu'){
			resetClass();
			$(this).addClass("on");
			//$(this).next(".gnbs").addClass("show");
			$(this).next(".gnbs").stop().slideDown(400);
		}else{
			$(this).removeClass("on");
			//$(this).next(".gnbs").removeClass("show");
			$(this).next(".gnbs").stop().slideUp(200);
		}
	});
}

// ***************
/* 메인
// ***************
비쥬얼 배너 */
function mainAd(){
	var Main_Visu = $(".mv_list").owlCarousel({
		//animateOut : 'fadeOut',
		//animateIn : 'fadeIn',
		items : 1,
		loop : true,
		margin : 0,
		autoplay : true,
		autoplayTimeout : 5000,
		autoplaySpeed : 1000,
		autoplayHoverPause : true,
		nav : true,
		dots : false,
		onInitialized : Mv_counter,
		onChanged : Mv_counter,
	});

	$(".mv_controls .mv_start").on("click", function(e){
		e.preventDefault();
		$(this).hide();
		$(".mv_controls .mv_stop").css("display", "block");
		Main_Visu.trigger("play.owl.autoplay",[1000]);

		var mvOwl = $(".mv_list").data("owl.carousel");
		mvOwl.settings.autoplay = true;
		mvOwl.options.autoplay = true;
		$(".mv_list").trigger("refresh.owl.carousel");
	});
	$(".mv_controls .mv_stop").on("click", function(e){
		e.preventDefault();
		$(this).hide();
		$(".mv_controls .mv_start").css("display", "block");
		Main_Visu.trigger("stop.owl.autoplay");

		var mvOwl = $(".mv_list").data("owl.carousel");
		mvOwl.settings.autoplay = false;
		mvOwl.options.autoplay = false;
		$(".mv_list").trigger("refresh.owl.carousel");
	});
}
function Mv_counter(event) {
	if (!event.namespace) {
		return;
	}
	var slides = event.relatedTarget;
	$(".mv_sec .mv_counter").text(slides.relative(slides.current()) + 1 + ' / ' + slides.items().length);
}

/* md choice  */
function mdChoice(){
	$('.md_choice_inner').bxSlider({
		slideWidth: 223,
		moveSlides:1,
		maxSlides: 4,
		slideMargin: 50,
		auto: true,
		touchEnabled : (navigator.maxTouchPoints > 0)
		//autoControls: true
	});
}
/* best item */
function uiTabsMenu () {
	var $menu = $('.best_tab'),
		$contWrap = $('.ui_tabs_contents_wrap'),
		_content ='.ui_tab_content',
		curr = 'current';

	if(!$menu.length ) { return }
	$(_content).css('display', 'none');
	$contWrap.each(function(){
		$(this).find('div' + _content +':first').css('display', 'block');
	});
	$menu.on('click','a', function(){
		if(!$(this).hasClass(curr)){
			$(this).addClass(curr).closest('li').siblings('li').find('.' + curr).removeClass(curr);
			$($(this).attr('href')).css('display', 'block').siblings('div'+_content).css('display', 'none');
		}
		this.blur();
		return false;
	});
};
function tabMenuSlider() {
	var $tabmenu = $('.best_tab'),
		$contWrap = $('.ui_tabs_contents_wrap'),
		$sliderClass = '.bx_slider',
		config = {
			moveSlides:1,
			maxSlides:6,
			slideWidth:159,
			slideMargin: 25,
			infiniteLoop:false,
			hideControlOnEnd:false,
			pager:false,
			touchEnabled : (navigator.maxTouchPoints > 0)
		};

	var sliders = new Array();
	$($sliderClass).each(function(i, slider) {
		var len = $(slider).find('> li').length;
		if(len < 3) {
			sliders[i] = $(slider).addClass('nonslider');
		} else {
			sliders[i] = $(slider).bxSlider(config);
		}
	});

	if(!$tabmenu.length ) { return }
	$contWrap.each(function(){
		if($(this).find('div.ui_tab_content').is(':first')) {
			slider.reloadSlider(config);
		}
	});
	$tabmenu.on('click', ' a', function(e){
		var _target = $(this).attr('href');

		if($(_target).css('display') === 'block') {
			$.each(sliders, function(i, slider){
				if(!slider.hasClass('nonslider')) {
					slider.reloadSlider(config);
				}
			});
		}
		e.preventDefault();
	});
}
/* main new  */
function mainNew(){
	$('.main_new_inner').bxSlider({
		pager: true,
		slideWidth: 223,
		moveSlides:1,
		maxSlides: 4,
		slideMargin: 50,
		auto: true,
		touchEnabled : (navigator.maxTouchPoints > 0)
		//autoControls: true
	});
}

/* Msec02 Timer */
function Msec02_Timer() {
	/* Timer */
	var tsTime = "";
	var tsTime_list = new Array();

	$(function() {
		$('.ms0201 .ts_box').each(function(i) {
			var spotFormatDate = $(this).data('spot-format-date');

			if (i == 0) {
				tsTime = spotFormatDate;
			} else {
				tsTime_list.push(spotFormatDate);
			}
		});

		if (tsTime != '') {
			$(".ms0201 .ts_l .ts_box").downCount({
				date: tsTime,
				offset: +9
			});
		}

		if (tsTime_list.length > 0) {
			$(".ms0201 .ts_r > li").each(function (index) {
				$(this).find(".ts_box").downCount({
					date: tsTime_list[index],
					offset: +9
				});
			});
		}
	});
}

/* Msec03 Banner */
function Msec03_Slider() {
	var Msec03_Ban = $(".msec03 .ms03_list").owlCarousel({
		//animateOut : 'fadeOut',
		//animateIn : 'fadeIn',
		items : 1,
		loop : true,
		margin : 0,
		autoplay : true,
		autoplayTimeout : 5000,
		autoplaySpeed : 1000,
		autoplayHoverPause : true,
		nav : true,
		dots : true
	});
}

/* Msec04 Tabs */
function Msec04_Eff() {
	// 각 첫번째 1차 카테고리 on
	$(".msec04 .cps_cate").each(function() {
		$(this).find('> dd > a:eq(0)').addClass("on");
	});

	// 각 첫번째 1차 카테고리에 해당하는 상품 view
	$(".msec04 .cps_prd").each(function() {
		$(this).find('> ul:eq(0)').addClass("view");
	});

	// 1차 카테고리 클릭 이벤트
	$(".msec04 .cps_cate > dd > a").on("click", function(e){
		e.preventDefault();

		// 현재 그룹에 해당하는 1차 카테고리 off -> on
		$(this).closest("dd").find("a").removeClass("on");
		$(this).addClass("on");

		var index = $(".msec04 .cps_cate > dd > a").index(this);
		var $category = $(this).closest('.cate_prd_sec');

		// 현재 그룹에 해당하는 1차 카테고리 close -> view
		$category.find('.cps_prd > ul').removeClass("view");
		$(".msec04 .cps_prd > ul:eq(" + index + ")").addClass("view");
	});

	var options = {
		animateOut : 'fadeOut',
		animateIn : 'fadeIn',
		items : 1,
		margin : 0,
		autoplay : true,
		autoplayTimeout : 5000,
		autoplaySpeed : 1000,
		autoplayHoverPause : true,
		nav : false,
		dots : true
	};

	// 배너가 1개일 경우 루프 제외
	$(".msec04 .cps_bans").each(function() {
		if ($(this).find(".ms04_list a").length > 1) {
			options.loop = true;
		} else {
			options.loop = false;
		}
		$(this).find(".ms04_list").owlCarousel(options);
	});
}

// **************
/* faq */
//***************
function faq(){
	var faq_ul = $('#faq > li > .panel'),
		faq_list  = $('#faq > li > .list');

	faq_ul.hide();
	faq_list.click(function(e) {
		e.preventDefault();
		var text = $(this).children('div.panel');
		if(!$(this).hasClass('on')) {
			faq_list.removeClass('on');
			faq_ul.filter(':visible').slideUp('800');
			$(this).addClass('on').next().stop(true,true).slideDown('800');

		} else {
			$(this).removeClass('on');
			$(this).next().stop(true,true).slideUp('800');
		}
	});

}

// ******************
/* 상품 Q&A */
// ******************
var oldN = "0"
function showQnA(curN) {
	if(curN != oldN ) {
		document.getElementById("questTit"+curN).className='tit-on';
		document.getElementById("quest"+curN).className='view-on';
		if(oldN > 0) {
			document.getElementById("questTit"+oldN).className='tit-off';
			document.getElementById("quest"+oldN).className='view-off';
		}
		oldN = curN;
	}else if(curN == oldN && document.getElementById("questTit"+curN).className =='tit-on') {
		document.getElementById("questTit"+oldN).className='tit-off';
		document.getElementById("quest"+oldN).className='view-off';
	}else{
		document.getElementById("questTit"+curN).className='tit-on';
		document.getElementById("quest"+curN).className='view-on';

	}
}

/* 장바구니/주문결제 무료배송 */
function cart_tip(){
	$('.delievery_close').click(function(e){
		e.preventDefault();
		$(this).parent(".delievery_tip").removeClass("show");
	});
	$('.delivery_type').click(function(e){
		e.preventDefault();
		$(this).next(".delievery_tip").addClass("show");
	});
}


// *******************
/* 퀵메뉴 */
// *******************

function quickMenuPagination() {
	// QUICK MENU
	var $quickMenu = $('#quick-menu');
	var $todayItems = $('.item_area ul');
	var todayItemCount = $todayItems.find('> li').size();
	var todayItemsIndex = 0;
	var itemHeight = 65;
	var totalPages = Math.ceil(todayItemCount / 3);
	var currentPage = 1;
	var itemsPerPage = 3;


	$quickMenu.find('.page p span').text(totalPages);

	$('.page a.prev').on('click', function(e) {
		e.preventDefault();

		if (totalPages > 1) {
			if (currentPage == 1) {
				currentPage = totalPages;
			} else {
				currentPage--;
			}

			var topSize = (currentPage - 1) * itemHeight * itemsPerPage;
			$todayItems.css('margin-top', '-' + topSize + 'px');
			$quickMenu.find('.page p strong').text(currentPage);
		}



	});

	$('.page a.next').on('click', function(e) {
		e.preventDefault();

		if (totalPages > 1) {
			if (currentPage == totalPages) {
				currentPage = 1;
			} else {
				currentPage++;
			}

			var topSize = (currentPage - 1) * itemHeight * itemsPerPage;
			$todayItems.css('margin-top', '-' + topSize + 'px');
			$quickMenu.find('.page p strong').text(currentPage);
		}

	});

	$(".quick-btns .qtop").on("click", function(e){
		e.preventDefault();
		$("html, body").animate({scrollTop : 0 }, 400);
	});
	$(".quick-btns .qbtm").on("click", function(e){
		e.preventDefault();
		var wd_Top = $(window).scrollTop();
		var wd_Btm = $('body').height() - $(window).height();
		$("html, body").animate({scrollTop : wd_Btm }, 400);
	});
}
// 퀵메뉴 설정
/*function stiky_setup(){
	//quickMenu 번수선언
	sticky_id = '#quick-menu';
	ticky_id_d = $(sticky_id);
	origin_val.top = $(sticky_id).offset().top;
	origin_val.position = $(sticky_id).css('position');
	$(window).scroll(function(){
		stiky_custom(sticky_id);
	});
}
// 퀵메뉴 따라다니게 하기
function stiky_custom(id)
{
	var tid = $(id);
	var tid_t = tid.offset().top;
	var window_t = $(window).scrollTop();
	// console.log(tid_t, window_t)

	if( 0 < window_t )
	{
		tid.css('position', 'fixed').css('top', 85).css('width','100%');
	}
	else
	{
		tid.css('position', origin_val.position).css({'top':origin_val.top , 'width' : 'auto'});
	}
}*/

// *********************
/* 카테고리 베스트 랭킹 */
// *********************

function bestRank(){
	if(4 < $(".best-rank .slide").not(".bx-clone").length){
		$('.best-rank').bxSlider({
			maxSlides: 4,
			slideMargin: 0,
			touchEnabled : (navigator.maxTouchPoints > 0)
		});
	}
}


// *******************
/* 기획전 상세 탭메뉴 */
// *******************

function initEventTab() {

	if ($('.op-event-tab').size() == 0) {
		return;
	}

	var $eventTab = $('.op-event-tab');
	var $eventItemsWrap = $('.op-event-items-wrap');
	var $eventTitle = $('.op-event-title');
	var tabMenuTop = $eventTab.offset().top;
	tabMenuTop = 0;
	var tabHeight = $eventTab.height();

	var lastScroll = 0;
	var eventItemsScrollTop = [];

	$eventTitle.each(function(i) {
		eventItemsScrollTop[i] = $(this).offset().top - tabHeight - 20;
	});


	$(window).scroll(function() {
		var scrollTop = $(window).scrollTop();
		// 스크롤 하단으로 이동했을 때 위치 값 구하기 2017-06-08 yulsun.yoo
		var documentHeight = $(document).height();
		var windowHeight = $(window).height();
		// E 스크롤 하단으로 이동했을 때 위치 값 구하기 2017-06-08 yulsun.yoo
		if (tabMenuTop == 0) {
			tabMenuTop = $eventTab.offset().top;
		}

		if (scrollTop > tabMenuTop) {
			for (var i = eventItemsScrollTop.length - 1; i >= 0 ; i--) {
				//console.log("TT = " + scrollTop + ' ,, ' + eventItemsScrollTop[i] + ' :: ' + i,tabMenuTop);
				if (scrollTop >= eventItemsScrollTop[i]) {
					$eventTab.find('a').removeClass('on');
					$eventTab.find('li:eq(' + i + ') a').addClass('on');
					break;
				} else if ($(window).scrollTop() == ($(document).height()- $(window).height())) {
					//console.log('li:eq(' + (eventItemsScrollTop.length - 1) + ') a');
					$eventTab.find('a').removeClass('on');
					$eventTab.find('li:eq(' + (eventItemsScrollTop.length - 2) + ') a').addClass('on');

					break;
				}
			}

		}

		lastScroll = scrollTop;
	});

	$(".op-event-wrap .op-event-tab").sticky({ topSpacing:55 });

	$eventTab.find('a').on('click', function() {
		var target = $( $(this).attr('href') );

		if( target.length ) {
			event.preventDefault();
			$('html, body').animate({
				scrollTop: target.offset().top - (tabHeight + 55)
			}, 300);
		}
	});

}



/* Datepicker */
// function date(){
// 	$( ".datepicker, .datepicker2" ).datepicker({
// 		showOn: "both",
// 		buttonImage: '/content/images/icon/icon_datepicker.gif',
// 		buttonImageOnly: true ,
// 		changeMonth: true,
// 		changeYear: true,
// 		nextText: '다음 달',
// 		prevText: '이전 달',
// 		dateFormat: "yymmdd",
// 		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
// 		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
// 		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
// 		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
//   });
// }

/* 관심상품 */
// function wishItem(){
// 	$('.wish_item_inner').bxSlider({
// 		slideWidth: 180,
// 		maxSlides: 5,
// 		slideMargin: 24
// 	});
// }

/* all menu main */
// function allMenuMain(){
// 	$(".btn_menu_main").on("click", function(e){
// 		e.preventDefault();

// 		function resetClass(){
// 			$(".btn_menu_main").removeClass("on"); 
// 			$(".gnbs").removeClass("show"); 
// 		}
// 		if($(this).attr('class') == 'btn_menu_main'){
// 			resetClass();
// 			$(this).addClass("on");
// 			$(this).next(".gnbs").addClass("show");   
// 		}else{
// 			$(this).removeClass("on"); 
// 			$(this).next(".gnbs").removeClass("show");  
// 		} 
// 	}); 
// }
