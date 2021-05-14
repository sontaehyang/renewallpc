<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="seo" 	tagdir="/WEB-INF/tags/seo" %>

<div class="header_top">
	<div class="inner">
		<div class="hdt_menu">
			<a href="/" class="on">SHOP</a>
			<a href="#">COMPANY</a>
		</div><!--// hdt_menu -->
	
		<!--  로그인시 class:on 삭제시 장바구니 개수 가려짐 -->
		<ul class="util_menu on"> 
			<sec:authorize access="hasRole('ROLE_USER')">
				<li><a href="/op_security_logout" title="로그아웃">로그아웃</a></li>
				<li><a href="/mypage/order" title="마이페이지">마이페이지</a></li>
			</sec:authorize>
			
			<sec:authorize access="!hasRole('ROLE_USER')">
				<li><a href="/users/login" title="로그인">로그인</a></li>
				<li><a href="/users/join-us" title="회원가입">회원가입</a></li>
			</sec:authorize> 

			<li><a href="/cart" class="cart" title="장바구니">장바구니 <span id="header_cart_quantity">0</span></a></li>
			<li><a href="/inquiry" title="문의">1:1문의</a></li>
		</ul>
		
		<sec:authorize access="hasRole('ROLE_USER')">
			<c:if test="${not empty requestContext.user.userName}">
				<p class="greetings"><span>${requestContext.user.userName}</span>님 반갑습니다.</p>
			</c:if>
		</sec:authorize>
	</div><!--// inner E-->
</div><!-- // header_top E -->

<div class="header_middle">
	<div class="inner">
		<h1 class="logo"><a href="/" title="리뉴올PC"><img src="/content/images/common/renewallpc_logo_eng.png" alt="Re.New.All PC 리뉴올 PC 되살리다.새것처럼.모든것을."></a></h1>

		<div class="hd_banner" id="op-top-banner">
		</div><!--// hd_banner -->

		<div class="header_search">
			<jsp:include page="./saleson_query.jsp" />
		</div><!--//header_search E-->
	</div><!--// inner E-->
</div><!--// header_middle -->

<div class="hd_btm">
	<div class="inner">
		<jsp:include page="./saleson_all_categories.jsp" />

		<div class="hd_feature">
			<a href="/pages/battle_grounds_pc">리그오브레전드</a>
			<a href="/pages/overwatch">로스트아크</a>
			<a href="/pages/high_professional">배틀그라운드</a>
			<a href="/pages/body_monitor">본체+모니터</a>
			<a href="/pages/renewallpc">노트북</a>
			<a href="/pages/cash_special">현금특가</a>
		</div><!--// hd_feature -->

		<div class="hd_ico_menu">
			<a href="/event/spot" class="ico_ts">타임세일</a>
			<a href="/pages/dealer_shop" class="ico_ds">딜러샵</a>
		</div><!--// hd_ico_menu -->
	</div><!--// inner -->
</div><!--// hd_btm -->

<script type="text/javascript">
	$(function() {
		Shop.getTopBanner();
	});
</script>
