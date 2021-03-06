<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<style type="text/css">
	.bg_site {
		position: relative;
		width: 800px;
		height: 3100px;
		border: 1px solid #ccc;
		background: url(/content/opmanager/images/web_screen_responsive.jpg) no-repeat 0 0;
		background-size: 800px;
	}

	.bg_site a {
		position: absolute;
		left: 85px;
		width: 730px;
		border: 5px solid #999;
		text-align: center;
		text-decoration: none;
		padding-top: 10px;
		background: #ffdcdc;
		opacity: 0.8;
		color: #000;
		font-size: 13px;
		font-weight: bold;
	}

	.bg_site a:hover {
		border: 5px solid #ff6600;
		background: #fff;
		opacity: 0.8;
	}

	.bg_site a.logo {
		width: 216px;
		height: 50px;
		top: 70px;
		left: 37px;
	}

	.bg_site a.header-util {
		width: 254px;
		height: 37px;
		top: 0;
		left: 862px;
		padding-top: 5px;
	}

	.bg_site a.header-link {
		width: 314px;
		height: 50px;
		top: 70px;
		left: 803px;
	}

	.bg_site a.header-search {
		width: 430px;
		height: 50px;
		top: 70px;
		left: 335px;
	}

	.bg_site a.main-menu {
		width: 182px;
		height: 506px;
		top: 142px;
		left: 37px;
	}

	.bg_site a.main-banner {
		width: 630px;
		height: 345px;
		top: 77px;
		left: 90px;
	}

	.bg_site a.quick {
		width: 100px;
		height: 80px;
		top: 142px;
		left: 1137px;
	}

	.bg_site a.ds-md {
		width: 660px;
		height: 240px;
		top: 500px;
		left: 70px;
	}

	.bg_site a.ds-new {
		width: 660px;
		height: 480px;
		top: 760px;
		left: 70px;
	}

	.bg_site a.ds-best {
		width: 660px;
		height: 400px;
		top: 1270px;
		left: 70px;
	}

	.bg_site a.ds-promotion {
		width: 660px;
		height: 470px;
		top: 1700px;
		left: 70px;
	}

	.bg_site a.main-notice {
		width: 378px;
		height: 176px;
		bottom: 291px;
		left: 37px;
	}

	.bg_site a.main-bank {
		width: 221px;
		height: 176px;
		bottom: 291px;
		left: 416px;
	}

	.bg_site a.main-customer {
		width: 478px;
		height: 176px;
		bottom: 291px;
		left: 638px;
	}

	.bg_site a.foot-menu {
		width: 416px;
		height: 50px;
		bottom: 160px;
		left: 37px;
	}

	.bg_site a.foot-sns {
		width: 90px;
		height: 48px;
		bottom: 161px;
		left: 1027px;
	}

	.bg_site a.foot-info {
		width: 1080px;
		height: 79px;
		bottom: 57px;
		left: 37px;
	}

	.bg_site a.style-book-1 {

		width: 136px;
		height: 136px;
		top: 2375px;
		left: 97px;
	}

	.bg_site a.style-book-2 {

		width: 295px;
		height: 295px;
		top: 2342px;
		left: 250px;
	}

	.bg_site a.style-book-3 {

		width: 136px;
		height: 136px;
		top: 2375px;
		left: 563px;
	}

	.bg_site a.style-book-4 {

		width: 136px;
		height: 280px;
		top: 2530px;
		left: 97px;
	}

	.bg_site a.style-book-5 {

		width: 295px;
		height: 148px;
		top: 2655px;
		left: 250px;
	}

	.bg_site a.style-book-6 {

		width: 136px;
		height: 136px;
		top: 2534px;
		left: 563px;
	}

	.bg_site a.style-book-7 {

		width: 136px;
		height: 125px;
		top: 2685px;
		left: 563px;
	}

</style>

<!-- ?????? -->
<div class="location">
	<a href="#"></a> &gt; <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<div class="bg_site">
	<!-- <a href="" class="logo">?????? ??????</a> -->
	<!-- <a href="" class="header-util">?????? ??????</a> -->
	<!-- <a href="" class="header-link">?????? ?????? ??????</a> -->
	<!-- <a href="" class="header-search">?????? ?????? ??????</a> -->
	<!-- <a href="" class="main-menu">?????? ?????? ??????</a> -->
	<a href="javascript:Common.popup('/opmanager/display/template/front-promotion', 'promotion-banner', '1000', '700', '1');"
	   class="main-banner">?????? ?????? ??????</a>
	<!-- <a href="" class="quick">??? ?????? ??????</a> -->
	<a href="/opmanager/display/item/md" class="ds-md">MD ???????????? ??????</a>
	<a href="/opmanager/display/item/best/TOP_100" class="ds-best">BEST ???????????? ??????</a>
	<a href="/opmanager/display/item/new" class="ds-new">NEW ????????? ??????</a>

	<a href="javascript:Common.popup('/opmanager/display/template/front-featured', 'promotion-banner', '1000', '700', '1');"
	   class="ds-promotion">?????????</a>
	<!--<a href="" class="main-notice">???????????? ??????</a>-->
	<!--<a href="" class="main-bank">??????????????? ?????? ??????</a>-->
	<!--<a href="" class="main-customer">???????????? ??????</a>-->
	<!--<a href="" class="foot-menu">?????? ?????? ??????</a>-->
	<!--<a href="" class="foot-sns">SNS ??????</a>-->
	<!--<a href="" class="foot-info">?????? ???????????? ??????</a>-->

	<a href="javascript:Common.popup('/opmanager/display/template/style-book-1', 'style-book-1', '1000', '700', '1');"
	   class="style-book-1">STYLE BOOK 1 ??????</a>
	<a href="javascript:Common.popup('/opmanager/display/template/style-book-2', 'style-book-2', '1000', '700', '1');"
	   class="style-book-2">STYLE BOOK 2 ??????</a>
	<a href="javascript:Common.popup('/opmanager/display/template/style-book-3', 'style-book-3', '1000', '700', '1');"
	   class="style-book-3">STYLE BOOK 3 ??????</a>
	<a href="javascript:Common.popup('/opmanager/display/template/style-book-4', 'style-book-4', '1000', '700', '1');"
	   class="style-book-4">STYLE BOOK 4 ??????</a>
	<a href="javascript:Common.popup('/opmanager/display/template/style-book-5', 'style-book-5', '1000', '700', '1');"
	   class="style-book-5">STYLE BOOK 5 ??????</a>
	<a href="javascript:Common.popup('/opmanager/display/template/style-book-6', 'style-book-6', '1000', '700', '1');"
	   class="style-book-6">STYLE BOOK 6 ??????</a>
	<a href="javascript:Common.popup('/opmanager/display/template/style-book-7', 'style-book-7', '1000', '700', '1');"
	   class="style-book-7">STYLE BOOK 7 ??????</a>
</div>