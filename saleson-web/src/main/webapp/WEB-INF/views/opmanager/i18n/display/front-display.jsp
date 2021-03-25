<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	.bg_site {position:relative; width:1247px; height:4412px; border:1px solid #ccc; background:url(/content/opmanager/images/web_screen_jsp.png) no-repeat 0 0; }
	.bg_site a {position:absolute; left:85px; width:730px; border:5px solid #999; text-align:center; text-decoration:none; padding-top:10px; background:#ffdcdc; opacity:0.8; color:#000; font-size:13px; font-weight:bold;	}
	.bg_site a:hover {border:5px solid #ff6600; background:#fff; opacity:0.8; }
	.bg_site a.front-popular-search {width:294px; height:46px; top:35px; left:479px; }
	.bg_site a.front-top {width:178px; height:62px; top:26px; left:856px; }
	.bg_site a.front-promotion {width:1247px; height:342px; top:126px; left:0px; }
	.bg_site a.front-spot {width:393px; height:349px; top:594px; left:204px; }
	.bg_site a.front-big-deal {width:403px; height:333px; top:594px; left:639px; }
	.bg_site a.front-text {width:381px; height:40px; top:940px; left:649px; }
	.bg_site a.front-middle {width:1247px; height:183px; top:997px; left:0px; }
	.bg_site a.front-best {width:814px; height:1158px; top:1381px; left:217px; }
	.bg_site a.front-cash-specials {width:1247px; height:352px; top:2626px; left:0px; }
	.bg_site a.front-review {width:1247px; height:547px; top:2973px; left:0px; }
	.bg_site a.front-event {width:842px; height:458px; top:3563px; left:203px; }
	.bg_site a.front-notice {width:326px; height:150px; top:4102px; left:203px; }
</style>

<!-- 본문 -->
<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<div class="bg_site">
	<!-- <a href="" class="logo">로고 영역</a> -->
	<!-- <a href="" class="header-util">유틸 영역</a> -->
	<!-- <a href="" class="header-link">헤더 링크 영역</a> -->
	<!-- <a href="" class="header-search">헤더 검색 영역</a> -->
	<!-- <a href="" class="main-menu">메인 메뉴 영역</a> -->
	<a href="/opmanager/search/list" class="front-popular-search">인기 검색어 영역</a>
	<a href="javascript:Common.popup('/opmanager/display/template/front-top', 'front-banner', '1000', '700', '1');" class="front-top">상단 배너 영역</a>
	<a href="javascript:Common.popup('/opmanager/display/template/front-promotion', 'front-banner', '1000', '700', '1');" class="front-promotion">메인 배너 영역</a>
	<!-- <a href="" class="quick">퀵 메뉴 영역</a> -->
	<a href="/opmanager/display/spot/list" class="front-spot">타임세일 영역</a>
	<a href="/opmanager/display/item/big-deal" class="front-big-deal">득템의 기쁨 영역</a>
	<a href="javascript:Common.popup('/opmanager/display/template/front-text', 'front-banner', '1000', '700', '1');" class="front-text">텍스트 배너 영역</a>
	<%--<a href="/opmanager/display/item/new" class="front-new">NEW 신상품 영역</a>--%>
	<a href="javascript:Common.popup('/opmanager/display/template/front-middle', 'front-banner', '1000', '700', '1');" class="front-middle">중단 배너 영역</a>
	<a href="/opmanager/display/item/best" class="front-best">카테고리별 인기상품 영역</a>
	<a href="javascript:Common.popup('/opmanager/display/template/front-cash-specials', 'front-banner', '1000', '700', '1');" class="front-cash-specials">현금특가 배너 영역</a>
	<a href="/opmanager/item/review/list?recommendFlag=Y" class="front-review">포토리뷰 영역</a>
	<a href="/opmanager/featured/list?featuredClass=2" class="front-event">이벤트 영역</a>
	<a href="/opmanager/notice/list" class="front-notice">공지사항 영역</a>
	<!--<a href="" class="main-bank">온라인입금 계좌 영역</a>-->
	<!--<a href="" class="main-customer">고객센터 영역</a>-->
	<!--<a href="" class="foot-menu">푸터 메뉴 영역</a>-->
	<!--<a href="" class="foot-sns">SNS 영역</a>-->
	<!--<a href="" class="foot-info">푸터 회사정보 영역</a>-->
</div>