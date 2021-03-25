<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="title">
	<h2>MY PAGE</h2>
	<span class="his_back">
		<a href="/m" class="ir_pm">뒤로가기</a>
	</span>
</div><!--//title E -->

<!-- 내용 : s -->
<div class="con">
	<div class="mypage_wrap">
		<div class="mypage_info">
			<div class="grade">
				<span class="ico">
					<img src="/content/mobile/images/common/${userLevel.fileName}" alt="${userLevel.levelName}">
				</span>
				<p class="desc">${user.userName}님의 등급은 <span>${userLevel.levelName}</span>입니다.</p>
				<a href="/m/pages/rating-info" class="btn_st4 t_white">혜택보기</a>
			</div><!--//grade E -->
			<div class="info">
				<ul>
					<li>
						<p class="num">${op:numberFormat(userPoint)}<span> P</span></p>
						<p class="tit">${op:message('M00246')}</p>
					</li>
					<li>
						<p class="num">${op:numberFormat(userCouponCount)}<span> 장</span></p>
						<p class="tit">쿠폰</p>
					</li>
					<li>
						<p class="num">${op:numberFormat(userShippingCount)}<span> 장</span></p>
						<p class="tit">배송비쿠폰</p>
					</li>
					<%-- 2017.03.30 퍼블리싱 수정후 주석 풀기
					<li>
						<p class="num">${op:numberFormat(userEmoney)}<span> 장</span></p>
						<p class="tit">캐시</p>
					</li>
					 --%>
				</ul>
			</div><!--//info E -->
		</div><!--//mypage_info E -->
		<div class="mypage_con">
			<div class="check_wrap">
				<div class="delivery_check">
					<h3>주문/배송조회</h3>
					<div class="con">
						<ul>
							<li id="waiting-deposit-count" onclick="searchOrder('waiting-deposit')">
								<p class="num">0</p>
								<p class="tit">주문접수</p>
							</li>
							<li id="new-order-count" onclick="searchOrder('new-order')">
								<p class="num"  >0</p>
								<p class="tit">결제완료</p>
							</li>
							<li id="shipping-ready-count" onclick="searchOrder('shipping-ready')">
								<p class="num"  >0</p>
								<p class="tit">상품준비</p>
							</li>
							<li class="on" id="shipping-count" onclick="searchOrder('shipping')">
								<p class="num" >0</p>
								<p class="tit">배송중</p>
							</li>
							<li id="confirm-count" onclick="searchOrder('confirm')">
								<p class="num" >0</p>
								<p class="tit">구매확정</p>
							</li>
						</ul>
					</div>
				</div>
				<div class="order_check">
					<ul>
						<li id="cancel-request-count" onclick="searchOrder('cancel-request')">
							<p>취소 <span class="num">0</span>건</p>
						</li>
						<li class="on" id="exchange-request-count"onclick="searchOrder('exchange-request')">
							<p>교환 <span class="num"> 0</span>건</p>
						</li>
						<li id="return-request-count" onclick="searchOrder('return-request')">
							<p>반품 <span class="num">0</span>건</p>
						</li>
					</ul>
				</div>
			</div><!-- //check_wrap -->
			<div class="quick_link">
				<ul>
					<li><a href="/m/mypage/order">주문배송조회</a></li>
					<li><a href="/m/mypage/inquiry-item">상품Q&amp;A</a></li>
					<li class="last"><a href="/m/mypage/review-nonregistered">상품평</a></li><!-- 상품평? 이용후기? 명칭통일 필요 -->
					<li class="none"><a href="/m/mypage/wishlist">찜목록</a></li><!-- 찜목록? 관심상품? 명칭통일 필요 -->
					<li class="none"><a href="/m/mypage/inquiry">상담문의내역</a></li>
					<li class="last none"><a href="/m/mypage/delivery">배송지관리</a></li>
				</ul>
			</div><!-- //quick_link -->
			<div class="mapage_menu">
				<h3>MY PAGE 메뉴</h3>
				<ul class="menu">
					<li>
						<a href="#" class="oneDepth">혜택정보<span class="arr"></span></a>
						<div class="twoDepthBox">
							<ul>
								<li><a href="/m/pages/rating-info">나의 등급안내</a></li>
								<li><a href="/m/mypage/download-coupon-list">쿠폰 조회</a></li>
								<li><a href="/m/mypage/offline-coupon-exchange">오프라인 쿠폰 전환</a></li>
								<li><a href="/m/mypage/point-save-list">${op:message('M00246')} 조회</a></li>
								<li><a href="/m/mypage/shipping-save-list">배송비쿠폰 조회</a></li>
							</ul>
						</div>
					</li>
					<li>
						<a href="#" class="oneDepth">활동정보<span class="arr"></span></a>
						<div class="twoDepthBox">
							<ul>
								<li><a href="/m/mypage/inquiry">1:1문의내역</a></li>
								<li><a href="/m/mypage/review-nonregistered">이용후기</a></li>
								<li><a href="/m/mypage/inquiry-item">상품Q&amp;A</a></li>
								<li><a href="/m/mypage/wishlist">관심상품</a></li>
								<li><a href="javascript:alert('준비중입니다.');">오늘 본 상품</a></li>
							</ul>
						</div>
					</li>
					<li>
						<a href="#" class="oneDepth">회원정보<span class="arr"></span></a>
						<div class="twoDepthBox">
							<ul>
								<li><a href="/m/mypage/delivery">배송지관리</a></li>
								<li><a href="/m/users/editMode">회원정보수정</a></li>
								<li><a href="/m/sns-user/setup-sns">SNS연동설정</a></li>
								<li><a href="/m/users/secede">회원탈퇴</a></li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
		</div><!--//mypage_con E -->
	</div><!--//mypage_wrap E -->
</div><!--//con E -->
<!-- 내용 : e -->

<page:javascript>
<script type="text/javascript">

$(function(){
	setOrderCount();
})

//주문내역 Count 
function setOrderCount() {
	
	var complainCount = 0;
	$.post('/common/user/order-count', null, function(resp){
		
		if (resp.data == undefined) {
			return;
		}
		
		$.each(resp.data, function(i, state) {
			
			$object = $('#' + state.key + "-count");
			if ($object.size() > 0) {
				$object.find('.num').text(Common.numberFormat(state.count));
			}
			
		});
		
	}, 'json'); 
	
}

function searchOrder(type) {
	
	if ($('#' + type + "-count").size() == 0) {
		return;
	}
	
	var count = Number($('#' + type + "-count").find('.num').text());
	if (count > 0) {
		location.href = '/m/mypage/order?statusType=' + type;
	}
}
</script>
</page:javascript>