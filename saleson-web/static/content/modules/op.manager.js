
var Manager = {};

Manager.setClock = function() {
    try{
        var now = new Date();
        var Hours = now.getHours();
        var Minutes = now.getMinutes();
        var Seconds = now.getSeconds();
        var Value = (Hours >= 12) ? " 오후" : " 오전";

        Value += " " + ((Hours > 12) ? Hours - 12 : Hours);
        Value += ((Minutes < 10) ? ":0" : ":") + Minutes;
        Value += ((Seconds < 10) ? ":0" : ":") + Seconds;
        if(document.getElementById("clock")){
            $("#clock").text(Value);
            setTimeout("Manager.setClock()", 1000);
        }
    }catch(e){

    }
};

Manager.setLnbHeader = function() {
    var firstMenuUrl = $('.gnb li.on a').attr("href");
    var firstMenuName = $('.gnb li.on a').text();
    $('#first-menu-name').text(firstMenuName);

    $('.lnbs > li > ul > li.on').parent().parent().addClass("on");
    //alert($('.lnbs > li > ul > li.on').parent().html());

    var secondMenuUrl = $('.lnbs > li.on > ul > li:first-child a').attr('href');
    var secondMenuName = $('.lnbs > li.on > a:first-child').text();


    var thirdMenuUrl = $('.lnbs > li.on > ul li.on a').attr('href');
    var thirdMenuName = $('.lnbs > li.on > ul li.on a').text();

    // breadcrumbs 설정
    var $locationLink = $('.contents .location a');
    $locationLink.eq(0).attr('href', firstMenuUrl).text('- ' + firstMenuName);
    $locationLink.eq(1).attr('href', secondMenuUrl).text(secondMenuName);
    $locationLink.eq(2).attr('href', thirdMenuUrl).text(thirdMenuName.replace('- ', ''));

    // 1번째 H3
    $('.contents h3').eq(0).not('.custom').find('span').text(thirdMenuName.replace('- ', ''));
};


/**
 * 관리자 목록에서 수정이 있는 경우 TD에 하이라이트.
 */
Manager.setTableListEvent = function() {
    if ($('#check_all').size() > 0) {

        var activeColorCode = '#ffffff';

        var $table = $('.board_list table');
        var $listElements = $table.find("tr");

        $("td input:text").not(".ignore").on("keyup", $table, function(event) {
            $(event.target).closest( $listElements ).css("backgroundColor", activeColorCode);
            $(event.target).closest( $listElements ).find('input[name=id]').prop('checked', true);
        });

        $("td select").not(".ignore").on("change", $table, function(event) {
            $(event.target).closest( $listElements ).css("backgroundColor", activeColorCode);
            $(event.target).closest( $listElements ).find('input[name=id]').prop('checked', true);
        });

        $("td input:checkbox").not(".ignore").on("click", $table, function(event) {
            if ($(this).prop('checked')) {
                $(event.target).closest( $listElements ).css("backgroundColor", activeColorCode);
            } else {
                $(event.target).closest( $listElements ).css("backgroundColor", "");
            }

            if($(this).attr('name') != "id") {
                $(event.target).closest( $listElements ).find('input[name=id]').prop('checked', true);
            }
        });


        $('#check_all').bind('click', function() {
            if($(this).prop('checked')) {
                $(this).closest('table').find('input[name=id]:enabled').parent().parent().has('td').css('backgroundColor', activeColorCode);

            } else {
                $(this).closest('table').find('input[name=id]:enabled').parent().parent().has('td').css('backgroundColor', '');
            }
        });
    }
};

Manager.logout = function() {
    $.get(url("/op_security_logout"), {}, function() {
        location.href = url("/opmanager/login");
    });
};

//주문 상세 정보 팝업.
Manager.orderDetails = function(pageType, orderSequence, orderCode, isPopup) {
    if (isPopup == '1') {
        Common.popup('/opmanager/order/'+pageType+'/order-detail/' + orderSequence + '/' + orderCode, "order-details", 1280, 800, 1, 0, 0);
    } else {
        location.href = '/opmanager/order/'+pageType+'/order-detail/' + orderSequence + '/' + orderCode;
    }
};

// 회원 상세 정보 팝업.
Manager.userDetails = function(userId) {
    Common.popup('/opmanager/user/popup/details/' + userId, "user-details", 1080, 800, 1, 0, 0);
};


Manager.itemLog = function(itemId) {
    Common.popup('/opmanager/item/popup/log/' + itemId, "item-log", 1528, 750, 1, 0, 0);
};

//결제 정보 변경
Manager.payChanges = function(pageType, orderSequence, orderCode) {
    Common.popup('/opmanager/order/'+pageType+'/change-pay/'+ orderSequence + '/' + orderCode, "pay_changes", 850, 500, 1);
};

Manager.activeUserDetails = function(menuId) {
    $('.tab_nav #' + menuId).addClass("on");

};


/**
 * 관리자 페이지 링크롤 POST로 전송 (일괄처리)
 *
 * 페이징 class에 .op-manager-pagination 가 추가되어야 함.
 * submit되는 form에는 input[name=query]가 있어야 함.
 *
 * @author skc@onlinepowers.com
 * @date 2018-07-11
 */
Manager.handlePagination = function() {
    var $pagination = $('.op-pagination');
    var $forms = $('form');
    if ($pagination.size() == 0 || $forms.size() == 0) {
        return;
    }

    // 목록 검색 및 페이징 처리를 위한 form 찾기
    var $form = null;
    $forms.each(function() {
        if ($(this).find('input[name=query]').size() > 0) {
            $form = $(this);
            return false;
        }
    });

    // 페이지 링크 이벤트
    if ($form == null) {
        return;
    }

    // 페이지 링크 이벤트 처리.
    $pagination.find('a').on('click', function(e) {
        var href = $(this).attr('href');

        var pattern = /page=[0-9]+/;
        var m = href.match(pattern);
        if (m != null) {
            e.preventDefault();

            var page = m[0].replace('page=', '');

            if ($form.find('input[name=page]').size() == 0) {
                $form.append('<input type="hidden" name="page" value="' + page + '" />')
            } else {
                $form.find('input[name=page]').val(page);
            }

            $form.submit();

        }
    });
};



/* 서식 - 플레이스홀더 처리 */
$(document)
.on('click', '.placeholder', function(){
    $(this)
    .addClass('hidden')
    .siblings('input:visible, textarea:visible').focus();
})
.on('focusin focusout keydown', '.placeholder_wrap input, .placeholder_wrap textarea ', function(e){
    var $placeholderInput = $(this),
        $placeholder = $placeholderInput.siblings('.placeholder');

    if(e.type == 'focusin'){
        $placeholder.addClass('hidden');
        e.stopPropagation();
    } else if(e.type == 'focusout' && !$placeholderInput.val().length){
        $placeholder.removeClass('hidden');
        e.stopPropagation();
    }

});

/* 레이아웃 높이 설정 */
function setHeight(){
    // 높이 설정 대상
    var objs = {
        container : $('#container'),
        lnb : $('.lnb'),
        contents : $('.contents')
    };
    $.each(objs, function(key, value){
        $(this).css('height', '');
    });

    // 높이값
    var heights = {},
        winHeight = $(window).height(),
        minHeight = winHeight - ($('#header').outerHeight(true) + $('#footer').outerHeight(true)),
        maxHeight = null;

    // 최소 높이값 설정
    if(winHeight > $(document.body).height()){
        maxHeight = minHeight;
    }

    // 최대 높이값 설정
    $.each(objs, function(key, value){
        var $this = $(this);
        $this.css('height', '');
        heights[key] = {
            outerHeight : $this.outerHeight(),
            paddingHeight : $this.outerHeight() - $this.height()
        };

        maxHeight = maxHeight > heights[key]['outerHeight'] ? maxHeight : heights[key]['outerHeight'];
    });

    // 높이 설정
    $.each(objs, function(key, value){
        var $this = $(this);
        $this.css('height', maxHeight - (heights[key]['paddingHeight']));
    });

    $('#footer').css('position', 'static');

}
/*window.onload = setHeight;

var windowResize = {
	interval : null,
	width : $(window).width(),
	height : $(window).height()
};

$(window).on('resize', function(e){

	var newWidth = $(window).width(),
		newHeight = $(window).height();

	// IE7, 8 버그 대응
	if(windowResize.height !== newHeight || windowResize.width !== newWidth) {
		// 이벤트 호출 간격 조정
		window.clearTimeout(windowResize.interval);
		windowResize.interval = window.setTimeout(setHeight, 10);
	}

	windowResize.width = newWidth;
	windowResize.height = newHeight;
});
*/
/* lnb */
$(document).on('click', '.lnb_handle', function(e){
    e.preventDefault();

    if($('.lnb').is(':animated')){
        return;
    }

    var $this = $(this);
    var	left = $this.hasClass('close') ? '-40px' : '0';
    var	leftHandle = $this.hasClass('close') ? '0px' : '190px';
    var	w = $this.hasClass('close') ? '40px' : '190px';
    var marginLeft = $this.hasClass('close') ? '' : '190px';
    var marginLeft2 = $this.hasClass('close') ? '0' : '-10px';


    $('.lnb').animate({'left': left, 'width': w}, function(){
        $this
        .toggleClass('open close')
        .html( $this.hasClass('close') ? '메뉴 닫기' : '메뉴 열기' )
        .animate({'margin-left' : marginLeft2});
    });
    $('.lnb_handle').animate({'left': leftHandle});
    $('.contents').animate({'margin-left': marginLeft});

});
$(document).on('click', '.lnbs .handle', function(e){
    e.preventDefault();

    var $this = $(this),
        menu = $(this).prev().text();
    $this
    .toggleClass('open clse')
    .html( $this.hasClass('close') ? menu + ' 서브메뉴 닫기' : menu + ' 서브메뉴 열기' )
    .next('ul').stop().slideToggle();
});

/* 화면인쇄 */
$(document).on('click', '.btn_print', function(e){
    e.preventDefault();
    window.print();
});


$(function() {
    Manager.setClock();
    Manager.setLnbHeader();
    Manager.setTableListEvent();

    // 관리자 페이지 처리 (POST 전송)
    Manager.handlePagination();

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
                showOn: "button",
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

        $("input.datepicker").datepicker();
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

    /* 반송사유 툴팁 */
    $('.board_list .tooltip, .donation_list .tooltip').on('focusin focusout mouseenter mouseleave click', function(e){
        var $tooltip = $(this).next('.tooltip_area');
        switch(e.type){
            case 'focusin':
            case 'mouseenter':
                $(document).finish().delay(100).queue(function(){
                    $tooltip.css('right', '0');
                });
                break;
            case 'focusout':
            case 'mouseleave':
                $(document).finish().delay(100).queue(function(){
                    $tooltip.css('right', '');
                });
                break;
            case 'click':
                return false;
        }

    });

    $('.board_view .icon_reject').click(function(){
        $(this).next('.tooltip_area').toggle();
        return false;
    });
    $('.tooltip_area .close').click(function(){
        $(this).closest('.tooltip_area').toggle();
        return false;
    });

    /* 팝업 닫기 */
    $('.popup_close').on('click', function(){
        self.close();
        return false;
    });

    // IE7-6 게시판 보기 - 부가정보 좌측 보더 설정
    if($('.board_view .title_wrap .info > span').length && (navigator.appVersion.indexOf("MSIE 7.")!=-1 || navigator.appVersion.indexOf("MSIE 6.")!=-1)){
        var $span = $('.board_view .title_wrap .info > span');
        var height = $('.board_view .title_wrap .info').height() - ($span.outerHeight() - $span.height());
        $span.css('height', height);
    }

	Manager.procSessionTimeout();
});

/* layerpopup */

function layer_open(m,el){
    var all = $('#' + m);
    var temp = $('#' + el);
    var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

    if(bg){
        all.fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다.
    }else{
        temp.fadeIn();
    }

    // 화면의 중앙에 레이어를 띄운다.
    //if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
    //else temp.css('top', '0px');
    if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
    else temp.css('left', '0px');

    temp.find('a.cbtn').click(function(e){
        if(bg){
            $('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다.
        }else{
            temp.fadeOut();
        }
        e.preventDefault();
    });

    $('.layer .bg').click(function(e){	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
        $('.layer').fadeOut();
        e.preventDefault();
    });

}

Manager.procSessionTimeout = function() {

	if (OP_MANAGER_TIMEOUT == '' || OP_MANAGER_TIMEOUT == '0') {
		return;
	}

	var timeout = Number(OP_MANAGER_TIMEOUT) * 60 * 1000;

	setTimeout(function() {

		if ('seller' == OP_MANAGER_TIMEOUT_TYPE) {
			location.href='/op_security_logout?target=/seller';
		} else {
			location.href='/op_security_logout?target=/opmanager';
		}

	}, timeout);

}
