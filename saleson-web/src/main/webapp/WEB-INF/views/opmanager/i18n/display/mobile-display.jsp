<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	.bg_site_m {position:relative; width:322px; height:5099px; border:1px solid #ccc; background:url(/content/opmanager/images/mobile_screen.png) no-repeat 0 center; }
	.bg_site_m a {position:absolute; width:320px; border:5px solid #999; text-align:center; text-decoration:none; padding-top:10px; background:#ffdcdc; opacity:0.8; color:#000; font-size:13px; font-weight:bold; }
	.bg_site_m a:hover {border:5px solid #ff6600; background:#fff; opacity:0.8; }
	.bg_site_m a.mobile-promotion {height:249px; top:83px; }
	.bg_site_m a.mobile-spot {height:376px; top:662px; }
	.bg_site_m a.mobile-big-deal {height:399px; top:1033px; }
	.bg_site_m a.mobile-middle {height:115px; top:1446px; }
	.bg_site_m a.mobile-best {height:1523px; top:1582px; }
	.bg_site_m a.mobile-cash-specials {height:356px; top:3111px; }
	.bg_site_m a.mobile-review {height:192px; top:3483px; }
	.bg_site_m a.mobile-event {height:809px; top:3693px; }
	.bg_site_m a.mobile-notice {height:158px; top:4508px; }
</style>


<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<div class="bg_site_m">
	<!-- <a href="" class="logo">로고 영역</a> -->
	<!-- <a href="" class="header-util">유틸 영역</a> -->
	<!-- <a href="" class="header-link">헤더 링크 영역</a> -->
	<!-- <a href="" class="header-search">헤더 검색 영역</a> -->
	<!-- <a href="" class="main-menu">메인 메뉴 영역</a> -->
	<a href="javascript:Common.popup('/opmanager/display/template/mobile-promotion', 'mobile-banner', '1000', '700', '1');" class="mobile-promotion">메인 배너 영역</a>
	<!-- <a href="" class="quick">퀵 메뉴 영역</a> -->
	<%--<a href="/opmanager/display/item/md" class="ds-md">MD 추천상품 영역</a>--%>
	<a href="/opmanager/display/spot/list" class="mobile-spot">타임세일 영역</a>
	<a href="/opmanager/display/item/big-deal" class="mobile-big-deal">득템의 기쁨 영역</a>
	<a href="javascript:Common.popup('/opmanager/display/template/mobile-middle', 'mobile-banner', '1000', '700', '1');" class="mobile-middle">중단 배너 영역</a>
	<a href="/opmanager/display/item/best" class="mobile-best">카테고리별 인기상품 영역</a>
	<a href="javascript:Common.popup('/opmanager/display/template/mobile-cash-specials', 'mobile-banner', '1000', '700', '1');" class="mobile-cash-specials">현금특가 배너 영역</a>
	<a href="/opmanager/item/review/list?recommendFlag=Y" class="mobile-review">포토리뷰 영역</a>
	<a href="/opmanager/featured/list?featuredClass=2" class="mobile-event">이벤트 영역</a>
	<a href="/opmanager/notice/list" class="mobile-notice">공지사항 영역</a>
	<!--<a href="" class="main-bank">온라인입금 계좌 영역</a>-->
	<!--<a href="" class="main-customer">고객센터 영역</a>-->
	<!--<a href="" class="foot-menu">푸터 메뉴 영역</a>-->
	<!--<a href="" class="foot-sns">SNS 영역</a>-->
	<!--<a href="" class="foot-info">푸터 회사정보 영역</a>-->

</div>
