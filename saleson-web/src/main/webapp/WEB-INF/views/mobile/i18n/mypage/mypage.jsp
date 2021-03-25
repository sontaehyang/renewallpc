<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>





<!-- container : s -->
	<div id="container">
		<div class="title">
			<h2>MY PAGE</h2>
			<span class="his_back"><a href="#" class="ir_pm">뒤로가기</a></span>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<div class="mypage_wrap">
				<div class="mypage_info">
					<div class="grade">
						<span class="ico"><img src="/content/mobile/images/common/grade_ico_premium.png" alt="등급이미지"></span>
						<p class="desc">홍길동님의 등급은 <span>PRIMIUM</span>입니다.</p>
						<a href="#" class="btn_st4 t_white">혜택보기</a>
					</div>
					<div class="info">
						<ul>
							<li>
								<p class="num">1,000,000<span> P</span></p></dt>
								<p class="tit">${op:message('M00246')}</p>
							</li>
							<li>
								<p class="num">2,287<span> 장</span></p>
								<p class="tit">쿠폰</p>
							</li>
							<li>
								<p class="num">56<span > 장</span></p>
								<p class="tit">배송비쿠폰</p>
							</li>
						</ul>
					</div>
				</div>
				<!-- //mypage_info -->
				
				<div class="mypage_con">
					<div class="check_wrap">
						<div class="delivery_check">
							<h3>주문/배송조회</h3>
							<div class="con">
								<ul>
									<li>
										<p class="num">0</p>
										<p class="tit">주문접수</p>
									</li>
									<li>
										<p class="num">0</p>
										<p class="tit">결제완료</p>
									</li>
									<li>
										<p class="num">0</p>
										<p class="tit">상품준비</p>
									</li>
									<li class="on">
										<p class="num">99</p>
										<p class="tit">배송중</p>
									</li>
									<li>
										<p class="num">0</p>
										<p class="tit">배송완료</p>
									</li>
								</ul>
							</div>
						</div>
						<div class="order_check">
							<ul>
								<li><p>취소 <span>0</span>건</p></li>
								<li class="on"><p>교환 <span>99</span>건</p></li>
								<li><p>반품 <span>0</span>건</p></li>
							</ul>
						</div>
					</div>
					<!-- //check_wrap -->
					<div class="quick_link">
						<ul>
							<li><a href="#">주문배송조회</a></li>
							<li><a href="#">상품Q&A</a></li>
							<li class="last"><a href="#">상품평</a></li>
							<li class="none"><a href="#">찜목록</a></li>
							<li class="none"><a href="#">상담문의내역</a></li>
							<li class="last none"><a href="#">배송지관리</a></li>
						</ul>
					</div>
					<!-- //quick_link -->
					<div class="mapage_menu">
						<h3>MY PAGE 메뉴</h3>
						<ul class="menu">
							<li>
								<a href="#" class="oneDepth">혜택정보<span class="arr"></span></a>
								<div class="twoDepthBox">
									<ul>
										<li><a href="#">나의 등급안내</a></li>
										<li><a href="#">쿠폰 조회</a></li>
										<li><a href="#">${op:message('M00246')} 조회</a></li>
										<li><a href="#">배송비쿠폰 조회</a></li>
									</ul>
								</div>
							</li>
							<li>
								<a href="#" class="oneDepth">활동정보<span class="arr"></span></a>
								<div class="twoDepthBox">
									<ul>
										<li><a href="#">나의 등급안내</a></li>
										<li><a href="#">쿠폰 조회</a></li>
										<li><a href="#">${op:message('M00246')} 조회</a></li>
										<li><a href="#">배송비쿠폰 조회</a></li>
									</ul>
								</div>
							</li>
							<li>
								<a href="#" class="oneDepth">회원정보<span class="arr"></span></a>
								<div class="twoDepthBox">
									<ul>
										<li><a href="#">나의 등급안내</a></li>
										<li><a href="#">쿠폰 조회</a></li>
										<li><a href="#">${op:message('M00246')} 조회</a></li>
										<li><a href="#">배송비쿠폰 조회</a></li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
					<!-- //mapage_menu -->
				</div>
				<!-- //mypage_con -->
			</div>
			<!-- //mypage_wrap -->
			
		</div>
		<!-- 내용 : e -->
		
	</div>
	<!-- //#container -->
	<!-- container : e -->
	


		
