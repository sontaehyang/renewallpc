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
			<h3>TV광고 PC<!--b>상품</b--></h3>
		</div>
		<div class="on_air_content">
			<div class="left">
				<div class="play_box">
					<a href="/m/products/view/G2000002228">
						<img src="/content/mobile/images/common/mv_live_img.png" alt="mv_live_img">
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
						<a href="/m/products/view/G2000002228">
							<span>TV광고 상품</span>
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
					<a href="#"><span>보상판매</span></a>
				</li>
				<li class="nav_item">
					<a href="#"><span>대량문의</span></a>
				</li>
				<li class="nav_item">
					<a href="#"><span>3년 A/S</span></a>
				</li>
				<li class="nav_item">
					<a href="#"><span>지점안내</span></a>
				</li>
				<li class="nav_item">
					<a href="#"><span>이시언</span></a>
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
				Common.popup(url("/popup/index/" + popupId), 'openPopup' + popupId, width, height, 0, leftPosition, topPosition);
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
