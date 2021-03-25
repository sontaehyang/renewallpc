<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="mypage_menu">
	<li><a href="/m/mypage/order" <c:if test="${gnbType == 'order'}">class="on"</c:if>>쇼핑내역</a></li>
	<li><a href="/m/mypage/coupon" <c:if test="${gnbType == 'benefits'}">class="on"</c:if>>쇼핑혜택</a></li>
	<li><a href="/m/mypage/wishlist" <c:if test="${gnbType == 'activity'}">class="on"</c:if>>활동정보</a></li>
	<li><a href="/m/mypage/delivery" <c:if test="${gnbType == 'user'}">class="on"</c:if>>회원정보</a></li>
</ul>

