<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div id="popupArea"></div>

<div class="main_con">
	<c:if test="${!empty displayContent['mobile-promotion']}">
		<div class="main_visual">
			<div class="swiper-wrapper">
				<c:forEach items="${displayContent['mobile-promotion'].displayImageList}" var="list" varStatus="i">
					<c:if test="${list.displayImage != ''}">
						<div class="swiper-slide">
							<a href="${list.displayUrl == '' ? 'javascript:void(0);' : list.displayUrl}">
								<img src="${list.displayImageSrc}" alt="${list.displayContent}">
							</a>
						</div>
					</c:if>
				</c:forEach>
			</div><!--//swiper-wrapper E-->
			<div class="swiper-pagination"></div>
			<button class="swiper-button-wrap">
				<div class="swiper-button-play"></div>
				<div class="swiper-button-pause on"></div>
			</button>
		</div>
	</c:if>


	<!-- 2021.03.22 11:56 / son 수정  / ST -->
	<!-- 온에어 : s -->
	<div class="on_air">
		<div class="title">
			<h3>시언Pick<!--b>상품</b--></h3>
		</div>
		<div class="on_air_content">
			<div class="left">
				<div class="play_box" style="border:4px solid #67479e;">
					<a href="/m/pages/pick" style="display:block;">
						<img src="/content/mobile/images/common/mobile_live_img_20210521.png" alt="mv_live_img">
					</a>
				</div>
			</div>
			<div class="right t_black">
				<div class="on_air_date">
					<p class="tit_s01">이시언, <span>그의 PC가 궁금해?</span></p>
					<!--p class="tit">i7 게이밍 PC</p>
                    <p class="date"><span>게임을 렉 없이!</span></p-->
				</div>
				<div class="on_air_views">
					<div class="live">
						<a href="/m/pages/pick">
							<span>시언Pick</span>
							<span>보러가기</span>
						</a>
					</div>
					<!--div class="past">
                        <a href="/m/pages/open">
                            <span>이벤트</span>
                            <span>바로가기</span>
                        </a>
                    </div-->
				</div>
			</div>
		</div>
	</div>
	<!-- 온에어 : e -->
	<!-- 2021.03.22 11:56 / son 수정  / END -->

	<!-- 배송상품 : s -->
	<div class="delivery_pro">
		<div class="delivery_pro_content">
			<a href="/m/pages/Salesking" class="del_today">
				<p class="tit"><span>판매왕</span>PC</p>
				<p class="txt">인기상품 총집합!</p>
			</a>
			<a href="/m/pages/Lightning" class="del_quick">
				<p class="tit"><span>번개배송</span>PC</p>
				<p class="txt">무조건 당.일.발.송</p>
			</a>
		</div>
	</div>
	<!-- 배송상품 : e -->

	<!-- 아이콘 바: s -->
	<div class="nav_bar">
		<div class="nav_bar_content">
			<ul>
				<li class="nav_item">
					<a href="javascript:;" onclick="javascript:OpenMainLayer('layer-ms010201');"><span>보상판매</span></a>
				</li>
				<li class="nav_item">
					<a href="javascript:;" onclick="javascript:OpenMainLayer('layer-ms010202');"><span>대량문의</span></a>
				</li>
				<li class="nav_item">
					<a href="javascript:;" onclick="javascript:OpenMainLayer('layer-ms010203');"><span>3년 A/S</span></a>
				</li>
				<li class="nav_item">
					<a href="javascript:;" onclick="javascript:OpenMainLayer('layer-ms010204');"><span>지점안내</span></a>
				</li>
				<li class="nav_item">
					<a href="javascript:;" onclick="javascript:OpenMainLayer('layer-ms010205');"><span>이시언</span></a>
				</li>
			</ul>
		</div>
	</div>
	<!-- 아이콘 바: e -->

	<!-- // 타임세일 -->
	<div id="op-main-spot-items"></div>

	<!--// 득템의 기쁨 -->
	<div id="op-main-big-deal-items"></div>

	<!--// 중간 배너 -->
	<c:if test="${!empty displayContent['mobile-middle'].displayImageList}">
		<div class="banner_section">
			<div class="banner_section_list">
				<div class="banner_slider">
					<div class="swiper-wrapper">
						<c:forEach items="${displayContent['mobile-middle'].displayImageList}" var="list" varStatus="i">
							<c:if test="${list.displayImage != ''}">
								<div class="swiper-slide">
									<a href="${list.displayUrl == '' ? 'javascript:void(0);' : list.displayUrl}">
										<img src="${list.displayImageSrc}" alt="${list.displayContent}">
									</a>
								</div>
							</c:if>
						</c:forEach>
					</div><!--// swiper-wrapper -->
				</div><!--// banner_slider -->
			</div><!--// banner_section_list -->
		</div><!--// banner_section -->
	</c:if>

	<!-- 카테고리별 인기상품 -->
	<div id="op-main-best-items"></div>

	<!--// 현금 특가 -->
	<c:if test="${!empty displayContent['mobile-cash-specials'].displayImageList}">
		<section class="cash_event">
			<div class="title">
				<h3>현금특가</h3>
			</div>
			<div class="cash_event_wrap">
				<c:forEach items="${displayContent['mobile-cash-specials'].displayImageList}" var="list" varStatus="i">
					<c:set var="cashSpecialsUrl" value="${list.displayUrl == '' ? 'javascript:void(0);' : list.displayUrl}" />
					<c:if test="${list.displaySubCode == '0'}">
						<div class="row_bnn">
							<a href="${cashSpecialsUrl}"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a>
						</div>
					</c:if>
					<c:if test="${list.displaySubCode == '1'}">
						<div class="row_bnn">
						<div class="left"><a href="${cashSpecialsUrl}"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a></div>
					</c:if>
					<c:if test="${list.displaySubCode == '2'}">
						<div class="right">
						<div class="right_t"><a href="${cashSpecialsUrl}"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a></div>
					</c:if>
					<c:if test="${list.displaySubCode == '3'}">
						<div class="right_b"><a href="${cashSpecialsUrl}"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a></div>
					</c:if>
					<c:if test="${i.last}">
						<c:if test="${displayContent['mobile-cash-specials'].displayImageList.size() > 3}">
							</div>
						</c:if>
						<c:if test="${displayContent['mobile-cash-specials'].displayImageList.size() > 1}">
							</div>
						</c:if>
					</c:if>
				</c:forEach>
			</div><!--// cash_event_wrap -->
		</section><!--// cash_event -->
	</c:if>

	<!--// 리뷰 -->
	<div id="op-main-review"></div>

	<!-- 기획전 -->
	<c:set var="featuredBanner" value='${displayContent["mobile-featured"]}'/>
	<c:if test="${!empty featuredBanner}">
		<div class="main_event">
			<div class="title">
				<h3>기획전</h3>
				<a href="/m/featured" class="more_btn">더보기</a>
			</div>
			<div class="main_event_slider">
				<div class="swiper-wrapper">
					<c:forEach  begin="0" end="2" step="2" varStatus="featuredIdx">
						<div class="swiper-slide">
							<div class="event_img">
								<c:forEach var="list" items="${featuredBanner.displayImageList }" varStatus="imageIdx">
									<c:if test='${list.displaySubCode == (featuredIdx.index + 1)}'>
										<a href="${list.displayUrl }"><img src="${ shop:loadImageBySrc(list.displayImageSrc, 'XS') }" alt="${list.displayContent}" ></a>
									</c:if>
								</c:forEach>
							</div>
							<ul>
								<c:forEach var="list" items="${featuredBanner.displayItemList }" varStatus="itemIdx">
									<c:if test='${list.displaySubCode == featuredIdx.index }'>
										<li>
											<a href="${list.link}">
												<div class="img">
													<img src="${ shop:loadImageBySrc(list.imageSrc, 'XS') }" alt="${list.itemName }">
												</div>
												<p class="tit">${list.itemName}</p>
												<div class="price_box">
													<p class="percentage">
														<c:if test="${list.totalDiscountAmount > 0 && item.discountRate > 0}">
															<span>${list.discountRate}</span>%
														</c:if>
													</p>
													<div class="price">
														<p class="per_price"><span>${op:numberFormat(list.listPrice)}</span>원</p>
														<p class="sale_price"><span>${op:numberFormat(list.exceptUserDiscountPresentPrice)}</span>원</p>
													</div>
												</div>
											</a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</c:forEach>
				</div>
			</div><!--//main_event_slider E-->
		</div> <!--//main_event E-->
	</c:if>

	<!--// 이벤트 -->
	<div id="op-main-event"></div>

	<!--// 공지사항, FAQ -->
	<div class="main_info">
		<div class="notice">
			<ul class="tab_list03">
				<li data-tab="op-main-notice" class="on"><a href="#">공지사항</a></li>
				<li data-tab="op-main-faq"><a href="#">FAQ</a></li>
			</ul>
			<ul id="op-main-notice" class="tab_contents on"></ul>
			<ul id="op-main-faq" class="tab_contents"></ul>
		</div>
	</div>
</div><!--// main_con E-->


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
		<img src="/content/images/common/main-layer-ms010202.jpg" alt="대량문의">
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
					<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (공휴일 휴무) <br>점심시간 : PM 12:30 ~ PM 01:30</dd>
					<dd class="tel">02-334-3989</dd>
				</dl>
				<a href="http://kko.to/e1F-lywD0" target="_blank" class="btn-map" title="본사 지도 새창으로 바로가기">지도보기</a>
			</dt>
			<dd>
				<p class="tit">이마트 영등포점</p>
				<dl class="info">
					<dt>서울 영등포구 영중로 15 타임스퀘어</dt>
					<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (둘째,넷째 일요일 휴무)</dd>
					<dd class="tel">02-2633-2432</dd>
				</dl>
				<a href="http://kko.to/K_Er2ywDT" target="_blank" class="btn-map" title="이마트 영등포점 지도 새창으로 바로가기">지도보기</a>
				<p class="tit">이마트 킨텍스점</p>
				<dl class="info">
					<dt>경기 고양시 일산서구 킨텍스로 171</dt>
					<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (둘째,넷째 수요일 휴무)</dd>
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

<page:javascript>
	<script type="text/javascript">
		Common.dynamic.script(url("/content/modules/op.social.js"));

		$(function(){
			// 타임세일
			init('spot-items');

			// 득템의 기쁨
			init('big-deal-items');

			// 카테고리별 인기상품
			init('best-items');

			// 리뷰
			init('review');

			// 이벤트
			init('event');

			// 공지사항
			init('notice');

			// FAQ
			init('faq');

			// 팝업 조회
			displayPopup();
		});

		function init(contentsCode) {
			var requestUri = '/m/main/' + contentsCode;
			var $selector = $('#op-main-' + contentsCode);

			$.post(requestUri, function (html) {
				$selector.empty().append(html);

				switch (contentsCode) {
					case 'spot-items' :
						setTimeSaleSlider();
						break;
					case 'big-deal-items' :
						setLimitedProductSlider();
						break;
					case 'best-items' :
						mainBestItemsEvent();
						break;
					case 'review' :
						setReviewSlider();
						break;
					default :
				}
			}, 'html');
		}

		function displayPopup() {

			$.get('/main/popup', null, function (response) {
				Common.responseHandler(response, function (response) {

					makePopup(response.data);
				});
			});

			function makePopup(data) {
				try {

					var openPopupParams = []

					if (typeof data !== 'undefined' && data != null) {
						for (var i = 0; i < data.length; i++) {
							var popup = data[i];
							openPopupParams.push(getPopupParam(popup));
							if ('2' == popup.popupType) {
								makeLayerPopup(popup);
							}
						}
					}

					if (openPopupParams.length > 0) {
						for (var i = 0; i < openPopupParams.length; i++) {
							var param = openPopupParams[i];
							openPopup(param.popupId, param.width + 17, param.height + 117, param.topPosition, param.leftPosition, param.popupType);
						}
					}

				} catch (e) {
					console.error('saleson make popup error', e);
				}
			}

			function makeLayerPopup(popup) {

				var template = $('#popupTemplate').html(),
						$popupArea = $('#popupArea'),
						rawHtml = '';

				rawHtml = template;

				var popupStyle = 'position:absolute;' +
						'left:' + popup.leftPosition + 'px;' +
						'top:' + popup.topPosition + 'px;' +
						'z-index:2001;' +
						'width:' + (popup.width + 20) + 'px;' +
						'height:' + popup.height + 'px;' +
						'display:none;';

				var handleStyle = 'width:' + (popup.width + 20) + 'px;' +
						'height:15px;cursor:move;background:' + popup.backgroundColor + ';';

				var contentStyle = 'width:' + (popup.width + 20) + 'px;';

				var popupContent = '';
				var imageLink = popup.imageLink,
						popupImage = popup.popupImage,
						popupImageSrc = popup.popupImageSrc,
						content = popup.content;

				if (typeof imageLink !== 'undefined' && imageLink != '') {
					popupContent += '<a href="' + imageLink + '">';
				}

				if (popupImage != null && typeof popupImage !== 'undefined' && popupImage != '') {

					popupContent += '<img src="' + popupImageSrc + '" border="0" />';
				} else {
					popupContent += content;
				}

				if (typeof imageLink !== 'undefined' && imageLink != '') {
					popupContent += '</a>';
				}

				// 로그인 웹 링크 -> 모바일 링크로 변경
				popupContent = popupContent.replaceAll("renewallpc.co.kr/users/login", "renewallpc.co.kr/m/users/login");
				popupContent = popupContent.replace("renewallpc.co.kr/", "renewallpc.co.kr/m/");

				rawHtml = rawHtml.replaceAll('{{popupId}}', popup.popupId)
						.replaceAll('{{popupStyle}}', popupStyle)
						.replaceAll('{{handleStyle}}', handleStyle)
						.replaceAll('{{contentStyle}}', contentStyle)
						.replaceAll('{{width}}', popup.width)
						.replaceAll('{{height}}', popup.height)
						.replaceAll('{{popupContent}}', popupContent);

				$popupArea.append(rawHtml);
			}

			function getPopupParam(popup) {

				return {
					popupId: popup.popupId,
					width: popup.width,
					height: popup.height,
					topPosition: popup.topPosition,
					leftPosition: popup.leftPosition,
					popupType: popup.popupType
				}
			}
		}


		function openPopup(popupId, width, height, topPosition, leftPosition, popupType) {
			//임시로 쿠키제거
			var ck_popup = $.cookie('popup_check_' + popupId);

			if (popupType == 2 && ck_popup != 1) {
				$('#popup_' + popupId).show();
				return;
			} else if (popupType == 1 && ck_popup != 1) {
				// 윈도우 팝업일 경우 모바일에서는 띄워주지 않음
				// Common.popup(url("/m/popup/index/" + popupId), 'openPopup' + popupId, width, height, 0, leftPosition, topPosition);
			}
		}

		//레이어 팝업관련
		function popClose(popNo) {
			$('#popup_' + popNo).hide();
		}

		function closeWin(popNo) {
			setCookie(popNo);
			$('#popup_' + popNo).hide();
		}

		function setCookie(popNo) {
			var expire = new Date();

			expire.setDate(expire.getDate() + 1);
			document.cookie = "popup_check_" + popNo + "=1; expires=" + expire.toGMTString() + "; path=/";
		}

	</script>

	<jsp:include page="include/popup-template.jsp"/>

</page:javascript>
