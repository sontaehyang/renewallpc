<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<!-- [레이어] 장바구니 담기 완료 -->
<div id="op-layer-cart" class="btn_layer pop_cart">
	<div class="con">
		<p class="txt">${op:message('M00595')}</p> <!-- 장바구니에 상품을 담았습니다. -->
		<button onClick="javascript:Shop.closeCartWishlistLayer()" class="layer_close">닫기</a>
	</div>
	<div class="btn_wrap">
		<a href="javascript:Shop.closeCartWishlistLayer()" class="btn_st1 decision">쇼핑 계속하기</a>
		<a href="/m/cart" class="btn_st1 back">장바구니 가기</a>
	</div>
</div>

