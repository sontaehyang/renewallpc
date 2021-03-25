$(document).ready(function(){
	
	Shop.getCartInfo();
	Shop.getWishlistCount();
	Shop.getCouponInfo();
	
	// Breadcrumb 셀렉트 박스 이벤트.
	breadcrumbSelectboxChangeEventHandler();

	// quickMenuEvent 활성
	quickMenuEvent();
});



function breadcrumbSelectboxChangeEventHandler() {
	if ($('.breadcrumbs select').size() > 0) {
		$('.breadcrumbs select').on('change', function() {
			var index = $('.breadcrumbs select').index($(this));

			location.href = '/categories/index/' + $(this).val();
		});
	}
}


/**
 * quick-menu(saleson_aside.jsp) scroll
 * Test by SKI
 */
function quickMenuEvent() {
	var $quickMenu = $(".quick_menu");
	var topBannerSize = 135;            			 // 상단배너사이즈

	if ($quickMenu.length == 0) {
		return;
	}

	$(window).scroll(function () {     				 // 스크롤하면 아래 코드 실행
		var currentScrollTop = $(this).scrollTop();
		var	position = 'absolute';

		if (currentScrollTop > topBannerSize) {      // 스크롤이 상단배너사이즈를 넘길때
			position = 'fixed';
		}

		$quickMenu.css("position", position);
	});

	Shop.getTodayItems();
};



/*
function quickMenu() {
	
	if ($('#quick-menu').size() > 0)
	{
		

	    //상품목록 가져오기
		//fn_SetSkyDisplay();
		
		// 스크롤
		$(window).scroll(function() {
			var scrollTop = $(document).scrollTop();
			var isGnbFixed = true;
			
			// 상품 상세페이지는 GNB fixed가 아님.
			if (location.href.indexOf('/products/view/') > -1) {
				isGnbFixed = false;
			}
			
			// 7beauty는 GNB fix 없음. 상단 배너 여부에 따라 사이지가 달라짐.
			isGnbFixed = false;
			
			var bannerTopSize = 130;
			if ($('.main_top_banner').size() > 0 && $('.main_top_banner').css('display') != 'none') {
				bannerTopSize = bannerTopSize + $('.main_top_banner').height();
			}
			
			
			if (Number(scrollTop) > bannerTopSize) {
				$('#quick-menu').css({'position': 'fixed', 'top': '0px'});
			
			} else {
				$('#quick-menu').css({'position': 'absolute', 'top': '70px'});

			}
		});
		

		
		// ie6에서만 스크롤시
		$(window).scroll( function () {
			if(Common.browser.msie && Common.browser.version == '6') fn_ScrollSky();
	    });
	    
	    // 장바구니 클릭
	    $('.cart').click( function(){
	    	$('#quick-wish-layer').hide();
	    	if($('#quick-cart-layer').css('display') != 'none')
	    	{
	    		$('#quick-cart-layer').hide();
	    	}
	    	else
	    	{
	    		$('#quick-cart-layer').bgIframe().show();
	    	}
	    });
	    
	    // 위시리스트 클릭
	    $('.wish').click( function(){
	    	$('#quick-cart-layer').hide();
	    	
	    	if (op_is_login == '')
	    	{
	    		if(confirm('로그인이 필요한 서비스 입니다.\n\n로그인 페이지로 이동하시겠습니까?'))
	    		{
	    			document.location.href = '/member/shopLogin';
	    			return;
	    		}
	    		else
	    		{
	    			return;
	    		}
	    	}
	    	
	    	if($('#quick-wish-layer').css('display') != 'none')
	    	{
	    		$('#quick-wish-layer').hide();
	    	}
	    	else
	    	{
	    		$('#quick-wish-layer').bgIframe().show();
	    	}
	    });
	    
	    // 포인트 조회 이미지 스왑
	    $('.view_point').mouseover( function(){
	    	$(this).attr('src', $(this).attr('src').replace('_off', '_on'));
	    }).mouseout( function(){
	    	$(this).attr('src', $(this).attr('src').replace('_on', '_off'));
	    });
	}
}
*/
