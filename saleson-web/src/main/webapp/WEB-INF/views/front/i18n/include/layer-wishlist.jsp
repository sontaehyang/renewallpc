<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<!-- [레이어] 관심상품 담기 완료 -->
<div id="op-layer-wishlist" class="cart_wishlist_layer popup_wrap" style="display: none;">
	<h1 class="popup_title">관심상품 담기</h1>
	<div class="popup_contents">
		<div class="result_box">
			<p>상품이 관심상품에 담겼습니다.</p>
			<p>바로 확인하시겠습니까?</p>
		</div>
	</div><!--//popup_contents E-->
	
	<div class="btn_wrap">
		<a href="/mypage/wishlist" class="btn btn-success btn-lg">관심상품 가기</a>
		<a href="javascript:Shop.closeCartWishlistLayer()" class="btn btn-lg btn-default">계속 쇼핑하기</a>
	</div>
	<a href="javascript:Shop.closeCartWishlistLayer()" class="popup_close">창 닫기</a>
</div>