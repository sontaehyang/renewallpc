<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<style type="text/css">
.op-event-wrap {

}
.op-event-wrap .op-event-tab {
	position: relative;
	width: 1080px;
	overflow: hidden;
    font-size: 14px;
    line-height: 18px;
    background-color: #d9d9d9;
    margin: 20px 0;
    border-bottom: 0.5px solid #aaa;
}
.op-event-wrap .op-event-tab.fixed {
	position: fixed;
    top: 0;
    margin: 0;
    z-index: 1000;
}
.op-event-wrap .op-event-tab ul {
	padding: 0.5px;
}
.op-event-wrap .op-event-tab ul li {
	float: left;
	width: 33.33333333333333333333333333333%;

}
.op-event-wrap .op-event-tab ul li:first-child {
	width: 33.33333333333333333333333333334%;
}
.op-event-wrap .op-event-tab.op-division-2 ul li {
	width: 50%;
}
.op-event-wrap .op-event-tab ul li a {
	display: block;
    margin: 0.5px;
    padding: 12px 20px;
    background-color: #fff;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.op-event-wrap .op-event-tab ul li a:hover {
	background: #f4f4f4;
}
.op-event-wrap .op-event-tab ul li a.on {
	background: #656875;	
	outline: 1px solid #878787;
	color: #fff;
	/*font-weight: bold*/;
}
.op-event-wrap .op-event-title {
	position: relative;
    padding: 5px 2px 9px;
    border-bottom: 2px solid #111;
    font-weight: normal;
    font-size: 22px;
    line-height: 36px;
    letter-spacing: -1px;
    color: #111;
}

.evt_wrap_html {
	padding: 0 0 20px 0 !important;
}
.item-list.thumb ul:last-child {
    padding-bottom: 0px;
    border-bottom: 0px solid #dedede;
    margin-bottom: 20px;
}
.item-list.thumb .list-inner li {
    float: left;
    width: 25%;
    min-height: 400px;
</style>

<script>
$(function() {
	
	initEventTab();
});

function initEventTab() {
	if ($('.op-event-tab').size() == 0) {
		return;
	}
	$(document).scrollTop(0);
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
		var scrollTop = $(document).scrollTop();
		// 스크롤 하단으로 이동했을 때 위치 값 구하기 2017-06-08 yulsun.yoo
		var documentHeight = $(document).height();
		var windowHeight = $(window).height();
		// E 스크롤 하단으로 이동했을 때 위치 값 구하기 2017-06-08 yulsun.yoo
		if (tabMenuTop == 0) {
			tabMenuTop = $eventTab.offset().top;
		}
		
		if (scrollTop > tabMenuTop) {
			$eventTab.addClass('fixed');
			$eventItemsWrap.css({'padding-top': (tabHeight + 20) + 'px'});
			
			if ($eventTab.find('a.on').size() == 0) { 
				$eventTab.find('li:eq(0) a').addClass('on');
			}
			
			for (var i = eventItemsScrollTop.length - 1; i >= 0 ; i--) {
				//console.log("TT = " + scrollTop + ' ,, ' + eventItemsScrollTop[i] + ' :: ' + i);
				if (scrollTop >= eventItemsScrollTop[i]) {
					$eventTab.find('a').removeClass('on');
					$eventTab.find('li:eq(' + i + ') a').addClass('on');
					break;
				} else if (scrollTop == documentHeight - windowHeight) { // 스크롤 제일 밑으로 갔을 때 마지막 탭에 class 추가 2017-06-08 yulsun.yoo
					$eventTab.find('a').removeClass('on');
					$eventTab.find('li:eq(' + (eventItemsScrollTop.length - 1) + ') a').addClass('on');
					break;
				}
			}
			
		} else {
			$eventTab.removeClass('fixed');
			$eventItemsWrap.css({'padding-top': '0px'});

			if (scrollTop < lastScroll) {
				$eventTab.find('a').removeClass('on');
			}
		}
		
		lastScroll = scrollTop;
		//alert(offset.top + ', ' + tabHeight);
	});
	
	
	$eventTab.find('a').on('click', function() {
		//$eventTab.find('a').removeClass('on');
		//$(this).addClass('on');
		
		
		var target = $( $(this).attr('href') );

	    if( target.length ) {
	        event.preventDefault();
	        $('html, body').animate({
	            scrollTop: target.offset().top - tabHeight +1
	        }, 300);
	    }
	});
}

</script>

<div class="inner">

	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>기획전</span> 
			<span>${featured.featuredName }</span>
		</div>
	</div><!-- // location_area E -->
	
	<div id="contents">
		<%-- 기획전 대표이미지 숨김 처리 2017-06-05 yulsun.yoo 
		<div class="event_banner">
			<img src="/upload/featured/${featured.featuredId }/featured/${featured.featuredImage}" alt="배너이미지">
		</div> --%>
		<!-- 기획전 설명 2017-05-25 yulsun.yoo -->
		<div>
			<c:if test="${featuredUrl == 'body_monitor'}">
				<div class="op_imgWrap">
					<img src="/content/images/event/intro_01.jpg" alt="배너이미지">
					<div class="intro_click">
						<a href="https://www.renewallpc.co.kr/products/view/G2000002229" target="_blank" class="link_01">1번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002236" target="_blank" class="link_02">2번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002527" target="_blank" class="link_03">3번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000003212" target="_blank" class="link_04">4번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002304" target="_blank" class="link_05">5번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002385" target="_blank" class="link_06">6번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002231" target="_blank" class="link_07">7번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002861" target="_blank" class="link_08">8번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002089" target="_blank" class="link_09">9번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002320" target="_blank" class="link_10">10번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002309" target="_blank" class="link_11">11번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002313" target="_blank" class="link_12">12번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002304" target="_blank" class="link_13">13번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002385" target="_blank" class="link_14">14번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002231" target="_blank" class="link_15">15번</a>
						<a href="https://www.renewallpc.co.kr/products/view/G2000002861" target="_blank" class="link_16">16번</a>
					</div>
					<img src="/content/images/event/intro_03.jpg" alt="배너이미지">
				</div>
			</c:if>
		${featured.featuredContent}
		</div>
		
		<div class="op-event-wrap">
			<c:if test='${(featured.prodState == "2" || featured.prodState == "3") && fn:length(itemTypeList) > 1 }'>
				<div class="op-event-tab event_item_tab ${fn:length(itemTypeList) <= 2 ? 'op-division-2' : ''}">
					<ul>
						<c:forEach var="itemType" items="${itemTypeList}" varStatus="i">
							<li><a href="#link${i.index }">${itemType[ITEM_TYPE_NAME_KEY] }</a></li>
						</c:forEach>
					</ul>
				</div><!-- // event_tab E --> 
			</c:if>
			
			<div class="op-event-items-wrap">
				<c:forEach var="itemType" items="${itemTypeList}" varStatus="i">
					<div class="op-event-items-area" id="test${i.index}">
						<div class="op-event-title" id="link${i.index}">
							<p>
								<c:if test='${featured.prodState == "2" or featured.prodState == "3"}'>
									${itemType[ITEM_TYPE_NAME_KEY]}
								</c:if>
							</p>
						</div><!-- // tab_tit E -->
						
						<div class="item-list thumb event">
							<ul class="list-inner">
								<c:set var="items" value="${itemListMap[itemType[ITEM_TYPE_ID_KEY]]}" scope="request"/>
								<jsp:include page="./../include/item-list.jsp" />
							</ul> 
						</div>
					</div>
				</c:forEach>

                <div id="reply-list">
                    <jsp:include page="reply-list.jsp" />
                </div>

			</div>
		</div>
	</div>
</div>

<jsp:include page="./../include/layer-cart.jsp" />
<jsp:include page="./../include/layer-wishlist.jsp" />


<script type="text/javascript">
	$(document).ready(function() {

        paginationFeaturedReply(1);

        try {
	        var jsonString = '${itemUserCodes}';

	        if (jsonString != '' && jsonString != 'null') {
		        EventLog.featured(JSON.parse(jsonString));
	        }
        } catch (e) {}
	});

    function paginationFeaturedReply(page) {

        $.ajax({
            url: '/featured-reply-list',
            type: 'POST',
            data: {
                'page': page,
                'featuredUrl': '${featuredUrl}',
                'featuredId': '${featured.featuredId}'
            },
            success: function(response) {
                $('#reply-list').html(response);
            }

        });

    }

</script>