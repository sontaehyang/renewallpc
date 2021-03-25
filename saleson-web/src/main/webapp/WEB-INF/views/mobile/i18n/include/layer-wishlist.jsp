<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<!-- [레이어] 관심상품 담기 완료 -->
<div id="op-layer-wishlist" class="btn_layer pop_favorite">
	<div class="con">
		<p class="txt">찜목록에 추가되었습니다.</p>
		<button onClick="javascript:Shop.closeCartWishlistLayer()" class="layer_close">닫기</a>
	</div>
	<div class="btn_wrap">
		<a href="javascript:Shop.closeCartWishlistLayer()" class="btn_st1 decision">쇼핑 계속하기</a>
		<a href="/m/mypage/wishlist" class="btn_st1 back">찜목록 바로가기</a>
	</div>
</div>

