
$(function() {
	// TOP Banner 쿠키.
	initTopBanner();
	
	// Breadcrumb 셀렉트 박스 이벤트.
	breadcrumbSelectboxChangeEventHandler();
	
	
	// GNB 고정
	gnbFixedEvent();
	
	// 상단 검색
	searchProducts();
	
	// 탭 메뉴 이벤트 
	tabContentEvent();
	
	// quick 메뉴 
	quickMenu();
	
	// 장바구니 정보 조회 
	Shop.getCartInfo();
	
	/* ui - datepicker */
	if($('input.term, input.datepicker').length){
		
		var DATEPICKER_SETTINGS = {
				'ko' : {  	
					closeText: "닫기",
					prevText: "이전 달",
					nextText: "다음 달",
					currentText: "오늘",
					monthNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
					monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
					dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
					dayNamesShort: ["일", "월", "화", "수", "목", "금", "토"],
					dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
			    	dateFormat: "yymmdd",
			    	showOn: "both", 
					buttonImage: '/content/images/icon/icon_datepicker.gif', 
					buttonImageOnly: true ,
					buttonText: "<span class='icon_calendar'>날짜 선택</span>",
					showButtonPanel: true
				},
				
				'ja' : {
					closeText: "閉じる",
					prevText: "前月",
					nextText: "来月",
					currentText: "今日",
					monthNames: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
					monthNamesShort: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
					dayNames: ["日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"],
					dayNamesShort: ["日", "月", "火", "水", "木", "金", "土"],
					dayNamesMin: ["日", "月", "火", "水", "木", "金", "土"],
			    	dateFormat: "yymmdd",
					showOn: "button",
					buttonText: "<span class='icon_calendar'>날짜 선택</span>",
					showButtonPanel: true
				}
			};
		var settings = $.extend({}, DATEPICKER_SETTINGS['ko'], DATEPICKER_SETTINGS[OP_LANGUAGE]);

		
	$.datepicker.setDefaults(settings);
		
		$("input.datepicker, input[class*=datepicker]").datepicker();
		//$("input.term").datepicker();  // append 된 데이터에서 datepicker가 동작하지 않아 주석..
		
		// IE7-6 디버그
		if(navigator.appVersion.indexOf("MSIE 7.")!=-1 || navigator.appVersion.indexOf("MSIE 6.")!=-1){
			$('.ui-datepicker-trigger').on('click', function(){
				if($(this).parent().css('z-index') !== '1000') {
					$('.datepicker, table td, .board_write').css('z-index', '');
					$(this).parent('.datepicker')
						.add($(this).closest('td'))
						.add($(this).closest('.board_write'))
						.css('z-index', '1000');
				} else {
					$(this).parent()
						.add($(this).closest('td'))
						.add($(this).closest('.board_write'))
						.css('z-index', '');
				}
			});
		}
	};
	
});


// LNB 달력조회
function getLnbCalendar(param) {
	if ($('#lnb_calendar').size() > 0) {
		
		if (Common.isUndefined(param)) {
			param = {
				'calendarYear': '',
				'calendarMonth': ''
			};
		}
		Common.loading.display = false;
		$.get('/calendar/list', param, function(response) {
			$('#calendar_data').html(response);
			Common.loading.display = true;
		}, 'html');
	}
}

//LNB 달력 이전/다음달 클릭 이벤트.
function calendarPreviousAndNextMonthClickEvent() {
	
	if ($('#lnb_calendar').size() > 0) {
		
		$("#lnb_calendar").on("click", '.prev', function(e){
			var year = $('#displayYear').text();
			var month = Number($('#displayMonth').text());
			
			e.preventDefault();
			
			if (month == 1) {
				year = parseInt(year)-1;
				month = 11;
			}else {
				month = parseInt(month)-2;
			}
			
			var param = {
					'calendarYear': year,
					'calendarMonth': month
				};
			getLnbCalendar(param);
			
		});
		
		$("#lnb_calendar").on("click", '.next', function(e) {
			var year = $('#displayYear').text();
			var month = Number($('#displayMonth').text());
			
			e.preventDefault();
			if (month == 12) {
				year = parseInt(year)+1;
				month = 0;
			}else {
				month = parseInt(month);
			}
			
			var param = {
					'calendarYear': year,
					'calendarMonth': month
				};
			getLnbCalendar(param);
			
		});
	}
	
}


// GNB 메뉴 고정 스크립스 
function gnbFixedEvent() {

	var $gnb = $('#gnb_fixed');
	if ($gnb.size() > 0 && location.href.indexOf('/products/view/') == -1) {
		
		
		$(window).scroll(function() {
			var h = $(window).scrollTop();
			if (h > 167) {
				$gnb.css({'position': 'fixed', 'top': '0px',  'width': '100%', 'z-index': '100'});
				$('#container').css('margin-top', '98px');
			} else {
				$gnb.css('position', '');
				$('#container').css('margin-top', '0px');
			}
		});
	}
}


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

function searchProducts() {
	var $kbmjSearchParam =  $('#kbmjSearchParam');
	
	if ($kbmjSearchParam.size() > 0) {
		$kbmjSearchParam.validator(function() {
			$kbmjSearchParam.find('#code').val("");
			$kbmjSearchParam.find('#field').val("");
			$kbmjSearchParam.find('#page').val("1");
			$kbmjSearchParam.find('#limit').val("40");
			$kbmjSearchParam.find('#sort').val("viewcount");
		});
		
	}
	
}


/* 탭 컨텐츠2 */
function tabContentEvent() {
	$(".tab_content2").hide();
    $("ul.tab2 li:first").addClass("active").show();
    $(".tab_content2:first").show();
    $("ul.tab2 li").click(function() {
    	
    	var index = $("ul.tab2 li").index(this);
    	
        $("ul.tab2 li").removeClass("active");
        $(this).addClass("active");
        $(".tab_content2").hide();
        
        var link = $(this).find("a").attr("href");
        $("#rankLink2").attr("href",link);
        
        var links = link.split("/");
        
        $("#"+links[2]).fadeIn();
        
        return false;
        
    });
    
    /* 탭 컨텐츠1 */
    $(".tab_content").hide();
    $("ul.tab li:first").addClass("active").show();
    $(".tab_content:first").show();
    $("ul.tab li").click(function() {
    	var index = $("ul.tab li").index(this);
    	
        $("ul.tab li").removeClass("active");
        $(this).addClass("active");
        $(".tab_content").hide();
        
        var link = $(this).find("a").attr("href");
        $("#rankLink").attr("href",link);
        $(".tab_content").eq(index).fadeIn();

        return false;
    });
}
 
// 모바일 페이지로 이동
function moveToMobileWebSite() {
	var $alternate = $('link[rel="alternate"]');
	if ($alternate.size() == 0) {
		location.href = '/m/';
	} else {
		location.href = $alternate.eq(0).attr('href').replace('http://www.7beauty.co.kr', '');
	}
}


// 2019.11.25   Test by SKI   Git branch 379-
// Quick 메뉴    미사용
function quickMenu() {

	if ($('#quick-menu').size() > 0) {

		//상품목록 가져오기
		//fn_SetSkyDisplay();

		// 스크롤
		$(window).scroll(function () {
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
                $('#quick-menu').css({'position': 'absolute', 'top': '0px'});

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
}


// breadcrumbs 
function breadcrumbSelectboxChangeEventHandler() {
	if ($('.breadcrumbs select').size() > 0) {
		$('.breadcrumbs select').on('change', function() {
			var index = $('.breadcrumbs select').index($(this));
			
			if (index == 0) {
				Shop.showAllCategoriesLayer();
			} else {
				location.href = '/categories/index/' + $(this).val();
			}
		});
	}
}


// Top Banner
function initTopBanner() {
	try {
		var $topBanner = $('.main_top_banner');
		if ($topBanner.size() > 0) {
			var topBannerCookieValue = $.cookie("TOP_BANNER");
			
			if (topBannerCookieValue != "1") {
				$topBanner.show();
			}
		}
	} catch(e) {
		//alert(e.message)
	}
}

function closeTopBanner() {
	$.cookie("TOP_BANNER", "1", {expires: 1});
	$('.main_top_banner').hide();
	
}
