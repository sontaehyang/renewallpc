<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>			
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
 

<c:if test="${requestContext.userLogin == true}">
	<div id="lnb"> 
		<div class="category_team">
			<ul class="depth_02">
				<li>
					<a href="/mypage/order" id="lnb_order">쇼핑내역</a>
					<ul class="depth_03">
						<li><a href="/mypage/order">주문/배송조회</a></li>
					</ul>
				</li>
				<li>
					<a href="/mypage/download-coupon-list" id="lnb_benefits">쇼핑혜택</a>
					<ul class="depth_03">
						<li><a href="/mypage/download-coupon-list">쿠폰</a></li>
						<li><a href="/mypage/offline-coupon-exchange">오프라인 쿠폰 전환</a></li>
						<li><a href="/mypage/point-save-list">${op:message('M00246')}</a></li>
						<li><a href="/mypage/shipping-save-list">배송비 쿠폰</a></li>
						<li><a href="/mypage/emoney-save-list">캐시</a></li>
					</ul>
				</li>
				<li>
					<a href="/mypage/wishlist" id="lnb_active">활동정보</a>
					<ul class="depth_03">
						<li><a href="/mypage/wishlist">관심상품</a></li>
						<li><a href="/mypage/review">이용후기</a></li>
						<li><a href="/mypage/inquiry-item">상품 Q&amp;A</a></li>
						<li><a href="/mypage/inquiry">1:1문의</a></li>
					</ul>
				</li>
				<li>
					<a href="/mypage/delivery" id="lnb_user">회원정보</a>
					<ul class="depth_03">
						<li><a href="/mypage/delivery">배송주소록 관리</a></li>
						<li><a href="/users/editMode">회원정보수정</a></li>
						<li><a href="/sns-user/setup-sns">SNS연동설정</a></li>
						<li><a href="/users/secede">회원탈퇴</a></li>
					</ul>
				</li>
			</ul>  
		</div>
	</div>	
</c:if>
 
<c:if test="${requestContext.userLogin == false}">
	<div class="customer_info">
		<div class="hide">
			<p>온라인 입금계좌</p>
			<p>국민은행 월드제이비(주) 999-666-666-43</p>
			<p>고객센터 1544-2880</p>
			<ul>
				<li>fax : 02-3446-5339</li>
				<li>main : wjb1588@naver.com</li>
			</ul>
			<ul>
				<li>평일 10:00 ~ 18:00</li>
				<li>토요일 10:00 ~ 14:00</li>
				<li>점심 12:00 ~ 13:00</li>
			</ul>
		</div>
	</div>
</c:if>