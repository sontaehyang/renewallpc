<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<!-- side_menu : s -->
<div class="side_menu">
	<div class="inner">
		<div class="side_top">
			<div class="profile">
				<sec:authorize access="hasRole('ROLE_USER')">
					<span class="name">${requestContext.user.userName}님</span>
					<span class="grade">${requestContext.user.userDetail.userlevel.levelName}</span>
					<a href="/op_security_logout?target=/m" class="logout">로그아웃</a>
				</sec:authorize>
				<sec:authorize access="!hasRole('ROLE_USER')">
					<a href="/m/users/join-us" class="join">회원가입</a>
					<a href="/m/users/login" class="logout">로그인</a>
				</sec:authorize>
			</div>
			<sec:authorize access="hasRole('ROLE_USER')">
				<div class="info">
					<ul>
						<li>
							<p class="link"><a href="/m/mypage">MY<br>PAGE</a></p>
						</li>
						<li>
							<p class="tit">${op:message('M00246')}</p>
							<p class="point_total">${op:numberFormat(shopContext.point)}P</p>
						</li>
						<li>
							<p class="tit">쿠폰</p>
							<dl class="product">
								<dt>상품</dt>
								<dd id="aside_coupon_count">0</dd>
							</dl>
							<dl class="shipping">
								<dt>배송비</dt>
								<dd id="aside_shipping_coupon_count">0</dd>
							</dl>
						</li>
					</ul>
				</div>
			</sec:authorize>
		</div>
		
		<jsp:include page="aside_category.jsp" />
		
	</div>
	<!-- //inner -->
</div>
<!-- //sideMenu -->
<button type="button" class="side_menu_close ir_pm">닫기</button>
<div class="side_menu_bgDim bgDim"></div>
<!-- side_menu : e -->