<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">

.cart_wishlist_layer {
	display: none;
	position: fixed; 
	z-index: 100000; 
	width:380px; 
	left: 50%; 
	margin-left: -200px; 
	top:50%; 
	margin-top: -150px; 
	padding-bottom: 30px; 
	background: #fff
}

</style>

<!-- [레이어] 장바구니 담기 완료 -->
<div id="op-layer-cart" class="popup_wrap cart_wishlist_layer" style="display: none;">
	<h1 class="popup_title">장바구니 담기</h1>
	<div class="popup_contents">
		<div class="result_box">
			<p>장바구니에 상품을 담았습니다.</p>
		</div>
	</div><!--//popup_contents E-->
	
	<div class="btn_wrap">
		<a href="/cart" class="btn btn-success btn-lg">장바구니 가기</a>  <!-- 장바구니로 이동 -->
		<a href="javascript:Shop.closeCartWishlistLayer()" class="btn btn-lg btn-default">계속 쇼핑하기</a>  <!-- 계속 쇼핑 -->
	</div>
	<a href="javascript:Shop.closeCartWishlistLayer()" class="popup_close">창 닫기</a>
</div>