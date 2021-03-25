<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>


<!--div class="left_banner">${categoryEdit.banner}</div-->

<div class="branch_quick">
	<div class="bch_info">
		<a href="#">
			<img src="/content/images/common/bch_img01.png" alt="이마트 영등포점">
			이마트 영등포점
		</a>
		<a href="#">
			<img src="/content/images/common/bch_img02.png" alt="이마트 킨텍스점">
			이마트 킨텍스점
		</a>
	</div>
	<a href="#">지점위치 안내</a>
</div><!--// branch_quick -->

<div id="quick-menu" class="quick_menu">
	<div class="quick_menu_inner">
		<a href="/cart">
			<p class="num" id="aside_cart_quantity">0</p>
			장바구니
		</a>
		<sec:authorize access="hasRole('ROLE_USER')">
			<a href="/mypage/wishlist">
				<p class="num" id="aside_wishlist_count">0</p>
				관심상품
			</a>
		</sec:authorize>

		<div class="op-today-items"></div>
	</div><!--//quick_menu_inner E-->

	<p class="quick-btns">
		<a href="#" class="qtop" title="TOP">TOP</a>
		<a href="#" class="qbtm" title="DOWN">DOWN</a>
	</p><!--// quick-btns -->
</div><!--//quick_menu E-->

 <script type="text/javascript">
 $(function() {
	 Shop.getTodayItems();
 });
 </script>