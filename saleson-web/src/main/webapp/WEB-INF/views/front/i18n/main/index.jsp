<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>


<div class="main">
	<div class="mv_sec">
		<div class="inner">
			<!--// 카테고리 -->
			<div class="main_lnb">
				<c:set var="isMainPage" value="true" scope="request" />
				<div class="lnb_team">
					<ul>
						<c:forEach items="${shopContext.shopCategoryGroups}" var="group" varStatus="i">
							<li class="lnb_group_menu">
								<a href="#">${group.name}</a>
								<div class="lnb_group" data-lnb-group-banner-key="op-main-lnb-group-${group.url}">
									<div class="lnb_list">
										<h2>${group.name}</h2>
										<ul class="on">
											<c:forEach items="${group.categories}" var="category1" varStatus="k">
												<li>
													<a href="${category1.link}">${category1.name}</a>
													<div class="lnb_layer_category category_over clear">
														<ul ${!empty category1.childCategories ? '' : 'style="display:none;"'}>
															<c:forEach items="${category1.childCategories}" var="category2">
																<li><a href="${category2.link}">${category2.name}</a></li>
															</c:forEach>
														</ul>
													</div>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div><!--//lnb_ecthetic E-->
			</div><!--// main_lnb -->

			<!-- 2021.03.22 11:56 / son 수정  / ST -->
			<div class="mlive">
				<p class="tit">시언Pick</p>
				<a href="/pages/pick" class="ml_thumb">
					<img src="/content/images/common/pc_live_img_20210909.png">
				</a>
				<div class="ml_btm">
					<!--a href="/products/view/G2000002228">경매 PC <br>보러가기</a>
					<a href="/pages/open">이벤트 <br>바로가기</a-->
					<a href="/pages/pick">시언Pick <br>바로가기</a>
				</div><!--// ml_btm -->
			</div><!--// mlive -->
			<!-- 2021.03.22 11:56 / son 수정  / END -->

		</div><!--// inner -->
		<div class="owl-carousel owl-theme mv_list">
			<!--// 메인 배너 -->
			<c:forEach items="${displayContent['front-promotion'].displayImageList}" var="list" varStatus="i">
				<c:if test="${list.displayImage != ''}">
					<a href="${list.displayUrl == '' ? 'javascript:alert(\'상품 준비중입니다.\');' : list.displayUrl}" class="item"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a>
				</c:if>
			</c:forEach>
		</div><!--// mv_list -->
		<div class="mv_controls">
			<a href="#" class="mv_start">AutoPlay Start</a>
			<a href="#" class="mv_stop">AutoPlay Stop</a>
		</div><!--// mv_controls -->
		<p class="mv_counter"></p>
	</div><!--// mv_sec -->

	<section class="msec01">
		<div class="inner">
			<div class="ms0101_btns">
				<a href="/pages/newPC" class="btn01">
					<p class="tit"><b>신품</b>조립PC<span>바로가기</span></p>
					<p class="txt">온라인 견적 문의</p>
				</a>
				<a href="/pages/Lightning" class="btn02">
					<p class="tit"><b>번개배송</b>PC<span>보러가기</span></p>
					<p class="txt">무조건 당.일.발.송</p>
				</a>
			</div><!--// ms0101_btns -->
			<div class="ms0102_btns">
				<a href="javascript:;" class="btn01" onclick="javascript:OpenMainLayer('layer-ms010201');">보상판매</a>
				<a href="javascript:;" class="btn02" onclick="javascript:OpenMainLayer('layer-ms010202');">대량문의</a>
				<a href="javascript:;" class="btn03" onclick="javascript:OpenMainLayer('layer-ms010203');">3년 A/S</a>
				<a href="javascript:;" class="btn04" onclick="javascript:OpenMainLayer('layer-ms010204');">지점안내</a>
				<a href="javascript:;" class="btn05" onclick="javascript:OpenMainLayer('layer-ms010205');">전속모델 이시언</a>
			</div><!--// ms0102_btns -->
		</div><!--// inner -->
	</section><!--// msec01 -->

	<section class="msec02">
		<div class="inner">
			<!--// 타임세일 (최소 1개 / 최대 3개) -->
			<div class="ms0201" id="op-main-spot-items"></div><!--// ms0201 -->

			<div class="ms0202">
				<!--// 득템의 기쁨 (최소 1개 / 최대 4개) -->
				<div id="op-main-big-deal-items"></div>

				<!--// 텍스트 배너 -->
				<c:forEach items="${displayContent['front-text'].displayImageList}" var="list" varStatus="i">
					<c:if test="${list.displayContent != ''}">
						<c:set var="textColor" value="${!empty list.displayColor ? '#' : ''}${list.displayColor}" />
						<c:set var="textImageSrc" value="${list.displayImageSrc}" />
						<a href="${list.displayUrl == '' ? 'javascript:void(0);' : list.displayUrl}" class="txt_ban" style="background:${textColor} url('${textImageSrc}') no-repeat center center;">${list.displayContent}</a>
					</c:if>
				</c:forEach>
			</div><!--// ms0202 -->
		</div><!--// inner -->
	</section><!--// msec02 -->

	<!--// 중간 배너 -->
	<c:if test="${!empty displayContent['front-middle'].displayImageList}">
		<section class="msec03">
			<div class="owl-carousel owl-theme ms03_list">
				<c:forEach items="${displayContent['front-middle'].displayImageList}" var="list" varStatus="i">
					<c:if test="${list.displayImage != ''}">
						<a href="${list.displayUrl == '' ? 'javascript:void(0);' : list.displayUrl}" class="item"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a>
					</c:if>
				</c:forEach>
			</div><!--// ms03_list -->
		</section><!--// msec03 -->
	</c:if>

	<section class="msec04">
		<div class="inner">
			<div class="top_ico">
				<a href="/categories/index/office_online__pc" class="ti01">인터넷/사무용PC</a>
				<a href="/categories/index/gaming" class="ti02">게임용PC</a>
				<a href="/categories/index/win10__pc" class="ti03">윈 탑재PC</a>
				<a href="/categories/index/diy" class="ti04">고성능PC</a>
				<a href="/pages/high_professional" class="ti05">방송용</a>
				<a href="/categories/index/full_set__pc" class="ti06">풀셋트PC</a>
				<a href="/categories/index/premium__pc" class="ti10">프리미엄PC</a>
				<a href="/categories/index/laptop_all" class="ti08">노트북</a>
				<a href="/categories/index/keyboard_mouse_pad" class="ti09">주변기기</a>
				<a href="/categories/index/Monitor_J" class="ti07">모니터</a>
			</div><!--// top_ico -->

			<!-- 카테고리별 인기상품 -->
			<div id="op-main-best-items">

			</div>
		</div><!--// inner -->
	</section><!--// msec04 -->

	<!--// 현금 특가 -->
	<c:if test="${!empty displayContent['front-cash-specials'].displayImageList}">
		<section class="msec05">
			<div class="inner">
				<p class="sec_tit">파격할인 현금특가</p>
				<dl class="ms05_bans">
					<dt><p>현금결제 이벤트</p>현금특가 결제하고 <br>푸짐한 선물받고!</dt>
					<dd>
						<c:forEach items="${displayContent['front-cash-specials'].displayImageList}" var="list" varStatus="i">
							<c:if test="${list.displayImage != ''}">
								<a href="${list.displayUrl == '' ? 'javascript:void(0);' : list.displayUrl}"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a>
							</c:if>
						</c:forEach>
					</dd>
				</dl><!--// ms05_bans -->
			</div><!--// inner -->
		</section><!--// msec05 -->
	</c:if>

	<!--// 리뷰 -->
	<div id="op-main-review"></div>

	<!--// 이벤트 -->
	<div id="op-main-event"></div>

	<div id="popupArea"></div>
</div><!--// main -->

<!--// 메인 컨텐츠팝업 추가 - 210504 - lmo -->
<div class="main-layer layer-ms010201">
	<p class="layer-title">보상판매</p>
	<div class="layer-content">
		<img src="/content/images/common/main-layer-ms010201.jpg" alt="보상판매">
	</div><!--// layer-content -->
	<button type="button" class="close-layer" title="보상판매 레이어팝업 닫기">보상판매 레이어팝업 닫기</button>
</div><!--// main-layer -->

<div class="main-layer layer-ms010202">
	<p class="layer-title">대량문의</p>
	<div class="layer-content">
		<img src="/content/images/common/main-layer-ms210524.jpg" alt="대량문의">
	</div><!--// layer-content -->
	<button type="button" class="close-layer" title="대량문의 레이어팝업 닫기">대량문의 레이어팝업 닫기</button>
</div><!--// main-layer -->

<div class="main-layer layer-ms010203">
	<p class="layer-title">3년 A/S</p>
	<div class="layer-content">
		<img src="/content/images/common/main-layer-ms010203.jpg" alt="3년 A/S">
	</div><!--// layer-content -->
	<button type="button" class="close-layer" title="3년 A/S 레이어팝업 닫기">3년 A/S 레이어팝업 닫기</button>
</div><!--// main-layer -->

<div class="main-layer layer-ms010204">
	<p class="layer-title">지점안내</p>
	<div class="layer-content">
		<dl class="sub-layout">
			<dt>
				<p class="tit">본사</p>
				<p class="txt">리뉴올PC 본사에서도 직접 구매하세요!</p>
				<p class="img"><img src="/content/images/common/main-layer-ms010204-map.jpg"></p>
				<dl class="info">
					<dt>경기도 고양시 덕양구 화랑로139</dt>
					<dd>상담시간 : 매일 AM 11:00 ~ PM 09:00 (공휴일 휴무) <br>점심시간 : PM 12:30 ~ PM 01:30</dd>
					<dd class="tel">02-334-3989</dd>
				</dl>
				<a href="http://kko.to/e1F-lywD0" target="_blank" class="btn-map" title="본사 지도 새창으로 바로가기">지도보기</a>
			</dt>
			<dd>
				<p class="tit">이마트 영등포점</p>
				<dl class="info">
					<dt>서울 영등포구 영중로 15 타임스퀘어</dt>
					<dd>상담시간 : 매일 AM 11:00 ~ PM 09:00 (둘째,넷째 일요일 휴무)</dd>
					<dd class="tel">02-2633-2432</dd>
				</dl>
				<a href="http://kko.to/K_Er2ywDT" target="_blank" class="btn-map" title="이마트 영등포점 지도 새창으로 바로가기">지도보기</a>
				<p class="tit">이마트 킨텍스점</p>
				<dl class="info">
					<dt>경기 고양시 일산서구 킨텍스로 171</dt>
					<dd>상담시간 : 매일 AM 11:00 ~ PM 09:00 (둘째,넷째 수요일 휴무)</dd>
					<dd class="tel">031-924-2432</dd>
				</dl>
				<a href="http://kko.to/rl7Sly6DH" target="_blank" class="btn-map" title="이마트 킨텍스점 지도 새창으로 바로가기">지도보기</a>
				<p class="tit">이마트 연수점</p>
				<dl class="info">
					<dt>인천 연수구 경원대로 184</dt>
					<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (둘째,넷째 수요일 휴무)</dd>
					<dd class="tel">032-811-2432</dd>
				</dl>
				<a href="http://kko.to/I4nPly6Do" target="_blank" class="btn-map" title="이마트 연수점 지도 새창으로 바로가기">지도보기</a>
			</dd>
		</dl><!--// sub-layout -->
		<p class="btm-txt">지금 리뉴올PC를 방문해주세요! <br>친절하게 상담해드립니다!</p>
	</div><!--// layer-content -->
	<button type="button" class="close-layer" title="지점안내 레이어팝업 닫기">지점안내 레이어팝업 닫기</button>
</div><!--// main-layer -->

<div class="main-layer layer-ms010205">
	<p class="layer-title">전속모델 이시언</p>
	<div class="layer-content">
		<div class="youtube-box">
			<iframe width="580" height="325" src="https://www.youtube.com/embed/_1PczAu_LPs?enablejsapi=1&version=3&playerapiid=ytplayer" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		</div><!--// youtube-box -->
		<p class="cont-title">이시언 인터뷰!</p>
		이시언이 직접 밝히는 리뉴올PC, 이래서 좋다! <br>이시언의 리뉴올PC 생생 인터뷰 구매는 리뉴올PC에서…
		<a href="https://www.youtube.com/watch?v=_1PczAu_LPs&ab_channel=%EB%A6%AC%EB%89%B4%EC%98%ACPC" target="_blank" class="btn-youtube">유튜브 더보기</a>
	</div><!--// layer-content -->
	<button type="button" class="close-layer" title="전속모델 이시언 레이어팝업 닫기">전속모델 이시언 레이어팝업 닫기</button>
</div><!--// main-layer -->

<div class="main-overlay"></div><!--// main-overlay -->

<jsp:include page="../include/layer-cart.jsp" />
<jsp:include page="../include/layer-wishlist.jsp" />

<page:javascript>
	<script src="/content/modules/front/main.view.js"></script>

	<jsp:include page="include/popup-template.jsp"/>
</page:javascript>