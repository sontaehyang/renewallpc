$(function() {
	$('#aside_category_dimmed').on('click', function() {
		Mobile.hideCategory();
	});
	
	// 카테고리 레이어 이벤트.
	Mobile.categoryEvent();
	
	// 헤더에 위시리스트 카운트 표시 
	Shop.getWishlistCount();
	
	// 장바구니 정보 조회 
	Shop.getCartInfo();
	
	// 쿠폰정보 조회
	Shop.getCouponInfo();
	
	
	// TOP 버튼 이벤트 
	Mobile.topButtonEvent();
	
	
	// 모바일 팝업 닫기 이벤트 (.mobile-popup-close)
	Mobile.closePopup();

	// 오늘본 상품 목록
	Shop.getMobileTodayItems();
});



var Mobile = {};

Mobile.logout = function() {
	$.get(url("/op_security_logout"), {}, function() {
		location.href = url("/m");
	});
};

//모바일 팝업 닫기 이벤트 (.mobile-popup-close)
Mobile.closePopup = function() {
	$('.mobile-popup-close').on('click', function(e) {
		e.preventDefault();
		var existOpener = $(window.opener).size() > 0 ? true : false;
		
		if (existOpener) {
			opener.closeItemImageWindow();
			//self.close();
		} else {
			location.href = '/m/';
		}
	});
};

Mobile.showCategory = function() {
	$('#aside_category_dimmed').show().animate({'opacity' : '0.4'});
	$('#aside_category').animate({'left' : '0px'});
	var h = $('body').scrollTop();

	$('#wrap').css({'position': 'fixed', 'top': '-' + h + 'px', 'width': '100%'});
};

Mobile.hideCategory = function() {
	$('#aside_category_dimmed').animate({'opacity' : '0'}, function(){
		$(this).hide();
	});
	$('#aside_category').animate({'left' : '-=400px'});
	var scrollTop = $('#wrap').css('top').replace('-','').replace('px', '');
	$('#wrap').css({'position': '', 'top': '', 'width': ''});
	$('body').scrollTop(scrollTop);
};

Mobile.categoryEvent = function() {
	var $firstMenu = $('a.btn_first');
	var $team = $('.lnb_team');
	var $group = $('.lnb_group');

	$team.find('> a').on('click', function(e) { 
		$firstMenu.show();
		
		$team.hide();
		// 팀 클래스 
		removeTeamClass();
		$(this).parent().addClass('on_team');
		
		var $childCategories = $(this).siblings('ul');
		var display = $childCategories.css('display');
		
		$('.lnb_team ul').hide();
		$('.lnb_team > a').removeClass('on');
		
		
		
		
		
		var $childCategories = $(this).siblings('ul');
		if ($childCategories.size() > 0) {
			e.preventDefault();
		}
		
		
		$childCategories.find('.on').removeClass("on");
		$childCategories.find('> li').show();
		
		//if (display == 'none') {
			$(this).addClass("on");
			$childCategories.show();
			
		//}
	});

	function removeTeamClass() {
		$team.removeClass('on_team').removeClass('on_group');
		for (var i = 1; i <= 4; i++) {
			$team.removeClass('on_category' + i);
		}
	}

	$('#aside_category  li > a').on('click', function(e) {
		
		
		var currentCategoryDepth = $(this).closest('ul').attr('class').replace('lnb_', 'on_');
		
		// 팀 클래스 
		removeTeamClass();
		$(this).closest('.lnb_team').addClass(currentCategoryDepth);
		
		// 
		$(this).closest('ul').find('> li').hide();
		$(this).parent().show();
		
		
		// 
		
		var $currentCategory = $('.' + $(this).closest('ul').attr('class'));
		var $childCategories = $(this).siblings('ul');
		
		if ($childCategories.size() > 0) {
			e.preventDefault();
		
			$childCategories.find('> li').show();
			$childCategories.find('.on').removeClass('on');
			$childCategories.find(' ul').hide();
			$childCategories.show();
			
			$(this).addClass('on');
		
		}
		
	});
	
	// 처음으로..
	$firstMenu.on('click', function(e) {
		e.preventDefault();
		
		removeTeamClass();
		
		
		$firstMenu.hide();
		$team.show();
		$team.removeClass("on_team");
		$team.find('.on').removeClass('on');
		
		$group.hide();
	});
};



Mobile.swiper = function (event){
	var $swiperSlide = $(event.id + ' .swiper-slide');
	
	var mySwiper = new Swiper(event.id+" > .swiper-container",{
	   pagination: event.id+' .paging',
	   slidesPerView : event.slidesPerView,
	   grabCursor: true,
	   calculateHeight: true
	 });
	
	
	if ($swiperSlide.size() > 2) {
		$(event.id+' .btn_next').on('click', function(e){
			e.preventDefault();
			mySwiper.swipeNext();
		 });
	 
		$(event.id+' .btn_prev').on('click', function(e){
			e.preventDefault();
			mySwiper.swipePrev();
		   
		});
		
		$(event.id + ' .btn_prev').show();
		$(event.id + ' .btn_next').show();
	}
	 
	 try {
		 mySwiper.addCallback('TouchStart', function(swiper){
			 $(event.id + ' .swiper-slide').removeClass('first');
		 });
		 mySwiper.addCallback('TouchEnd', function(swiper){
			 $(event.id + ' .swiper-slide-visible').removeClass('first');
			 $(event.id + ' .swiper-slide-visible').eq(0).addClass('first');
		 });
		 mySwiper.addCallback('SlideChangeEnd', function(swiper){
			 $(event.id + ' .swiper-slide-visible').removeClass('first');
			 $(event.id + ' .swiper-slide-visible').eq(0).addClass('first');
		
		 });
	 } catch (e) {
		 console.log(e.message);
	 }
};

//PC 페이지로 이동.
Mobile.moveToWebsite = function() {
	// 모바일 페이지로 이동
	var $canonical = $('link[rel="canonical"]');
	
	if ($canonical.size() == 0) {
		location.href = '/?SITE_REFERENCE=normal';
	} else {
		var link = $canonical.eq(0).attr('href');
		
		if (link == '') { 
			link = '/';
		}
		
		if (link.indexOf('?') > -1 || link.indexOf('=') > -1) {
			link = link + '&SITE_REFERENCE=normal';
		} else {
			link = link + '?SITE_REFERENCE=normal';
		}
		location.href = link;
	}
};


// TOP 버튼 이벤트 
Mobile.topButtonEvent = function() {
	var $top = $('#top');
	
	if ($top.size() > 0) {
		
		$(window).scroll(function() {
			var h = $(window).scrollTop();
			if (h > 0) {
				$top.show();
			} else {
				$top.hide();
			}
		});
	}
};


// ScrollTopInfo 
Mobile.saveScrollTop = function() {

	$(window).scroll(function() {
		var h = $(window).scrollTop();
		sessionStorage['scrollTop'] = h;
		$('#top').text(h);
	});

};


// 모바일 페이징 - 더보기
Mobile.pagination = {
	options: {
		expireSecond: 3 * 60 * 1000 // 3분
	},
	init: function(key, $selector, sec) {

		if ($selector == undefined) {
			$selector = $('#list-data');
		}
		
		if (sec == undefined) {
			sec = this.options.expireSecond;
		} else {
			this.options.expireSecond = sec;
		}
		

		
		if (sessionStorage[key + '_expire'] != undefined && Number(sessionStorage[key + '_expire']) < new Date().getTime()) {
			Mobile.pagination.clear(key);
			$(window).scrollTop(0);
			setTimeout(function() {$('#top a').click()}, 500);
			Mobile.pagination.set(key);
		}
		
		
		// 이전 페이지 데이터.
		if ($.cookie('SERVER_LOAD') == 'null' 
				&& sessionStorage[key + '_listData'] != undefined) {
			
			$selector.empty().append(sessionStorage[key + '_listData']);
			currentPage = sessionStorage[key + '_page'];
			$(window).scrollTop(sessionStorage[key + '_scrollTop']);
			
		} 
		
		$(window).scroll(function() {
			var h = $(window).scrollTop();
			sessionStorage[key + '_scrollTop'] = h;
			sessionStorage[key + '_expire'] = new Date().getTime() + Mobile.pagination.options.expireSecond;
			
		});
		
		$.cookie('SERVER_LOAD', null, {path: "/"});
	}, 
	set: function(key) {
		var expireTime = new Date().getTime() + this.options.expireSecond;
		sessionStorage[key + '_page'] = currentPage;
		sessionStorage[key + '_expire'] = expireTime;
		sessionStorage[key + '_listData'] = $('#list-data').html();
		
	},
	
	clear: function(key) {
		sessionStorage.removeItem(key + '_page');
		sessionStorage.removeItem(key + '_expire');
		sessionStorage.removeItem(key + '_listData');
	}
	
};

Mobile.mainTabSelected = function(value) {
	
	var objectList = $('#gnb').find('a');
	
	for (var i=0; i<objectList.length; i++) {
		
		var object = $(objectList[i]);
		
		var id = object.attr('id');
		
		object.removeClass('on');
		
		if (id == value) {
			object.addClass('on');
		}
	}
	
};

/**
 * 상단 통합검색 
 * 검색된 카테고리 클릭 시 값을 할당 하고 조회한다.
 * @param field
 * @param code
 */
function findItems(field, code) {
	$('#field').val(field);
	$('#code').val(code);
	
	$('#kbmjSearchParam').submit();
	
}
