<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>

<c:set var="imageCount" value="0" />
<c:forEach items="${item.itemImages}" var="itemImage" varStatus="i">
	<c:if test="${itemImage.itemImageId != 0}">
		<c:set var="imageCount">${imageCount + 1}</c:set>
	</c:if>
</c:forEach>

				
<c:set var="maker"></c:set>
<c:set var="originCountry"></c:set>
				
<c:forEach items="${item.itemInfos}" var="itemInfo" varStatus="i">
	<c:if test="${itemInfo.title == '제조사'}">
		<c:set var="maker">${op:nl2br(itemInfo.description)}</c:set>
	</c:if>
	<c:if test="${itemInfo.title == '제조국'}">
		<c:set var="originCountry"> / ${op:nl2br(itemInfo.description)}</c:set>
	</c:if>													
</c:forEach>

	<%--<div class="location">
		<p class="local">
			<c:forEach items="${item.breadcrumbs}" var="breadcrumb" varStatus="i">
				<c:if test="${i.count < 2}">
					<c:forEach items="${breadcrumb.breadcrumbCategories}" var="subBreadcrumb" varStatus="categoryIndex">
						<c:set var="categoryClass">route</c:set>
						<c:if test="${categoryIndex.last}">
							<c:set var="categoryClass">current</c:set>
						</c:if>
						<span class="${categoryClass}">${subBreadcrumb.categoryName}</span>
					</c:forEach>
				</c:if>
			</c:forEach>
		</p>
	</div>--%><!-- //location -->

	<div class="con view_wrap">
		<div class="view_img${item.itemSoldOut ? ' sold-out' : ''}">
			<div class="view_img_slider">
				<div class="swiper-wrapper">
					<c:forEach items="${item.itemImages}" var="itemImage">
						<c:if test="${itemImage.itemImageId != 0}">
							<div class="swiper-slide"><img src="${shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L')}" alt="${item.itemName}"></div>
						</c:if>
					</c:forEach>
				</div><!--// swiper-wrapper E-->
				<div class="swiper-pagination"></div>
			</div>
			<%--<a href="javascript:itemImagesDetail('${item.itemId}');" id="details-image-view" class="btn_big">
				<img src="/content/mobile/images/common/btn_big.png" alt="이미지확대">
			</a>--%>
		</div><!--//view_img E-->

		<c:if test="${item.spotItem}">
			<div class="view_ts">
				<div class="ts_btm_box">
					<ul class="ts_timer">
						<li class="img_timer"></li>
						<li class="days">0</li>
						<li class="hours">00</li>
						<li class="minutes">00</li>
						<li class="seconds">00</li>
					</ul>
				</div>
			</div><!-- //view_ts -->
		</c:if>

		<!-- //view_ts -->
		<div class="view_title">
			<div class="btn_share_wrap">
				<button type="button"></button>
			</div>
			<!--공유모달창-->
			<div class="share_modal" hidden>
				<div class="share_modal_wrap">
					<div class="modal-content">
						<button type="button" data-dismiss="modal" class="modal_close">
							<span class="screen_out">팝업 닫기</span>
						</button>
						<div class="modal-header">
							<h3 class="modal_tit">공유하기</h3>
						</div>
						<div class="modal-body">
							<div class="sns_pop">
								<a href="javascript:Social.share('facebook', '${item.itemName}', '${item.eventViewUrl}')" class="sns_fb" rel="nofollow">
									<img src="/content/mobile/images/common/btn_facebook.png" alt="페이스북">
								</a>
								<a href="javascript:Social.share('twitter', '${item.itemName}', '${item.eventViewUrl}')" class="sns_tw" rel="nofollow">
									<img src="/content/mobile/images/common/btn_twitter.png" alt="트위터">
								</a>
								<a href="https://story.kakao.com/share?url=${item.eventViewUrl});" class="sns_tw" rel="nofollow">
									<img src="/content/mobile/images/common/btn_kakao.png" alt="카카오톡">
								</a>
								<%--<a href="javascript:Shop.copyUrl('${item.link}');" class="sns_tw" rel="nofollow">
									<img src="/content/mobile/images/common/btn_url.png" alt="url">
								</a>--%>
							</div>
						</div>
						<button type="button" data-dismiss="modal" class="modal_close">
							<span class="screen_out">sns공유 닫기</span>
						</button>
					</div>
				</div>
			</div>
			<!--//공유모달창-->
			<div class="label_area">
				<c:forEach items="${item.itemLabels}" var="label">
					<span><img src="${label.imageSrc}" alt="${label.description}"></span>
				</c:forEach>
				<span class="sold-out end-of-sale" style="display: none"><img src="/content/mobile/images/common/product_soldout_ico.png" alt="품절" height="14"></span>
			</div>
			<h3 id="itemTitle">${item.itemName}</h3>
			<c:if test="${!empty item.itemSummary}">
				<p class="desc">${op:nl2br(item.itemSummary)}</p>
			</c:if>
		</div>
		<!-- //view_title -->

		<div class="view_info">
			<div class="item_detail">
				<div class="item_price_wrap">
					<h2 class="p_tit" hidden>판매가</h2>
					<div class="price_box">
						<div class="price">
							<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
								<p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
							</c:if>
							<c:if test="${!(item.spotItem || (requestContext.userLogin && item.exceptUserDiscountPresentPrice != item.presentPrice))}">
								<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<p class="percentage"><span>${item.discountRate}</span><sub>%</sub></p>
								</c:if>
							</c:if>
							<p class="rst_price"><span>${op:numberFormat(item.exceptSpotDiscount)}</span>원</p>
						</div>
					</div>
					<%--<div class="price_esti"><button>견적서</button></div>--%>
				</div>

				<table>
					<caption>상품정보</caption>
					<colgroup>
						<col style="width:27%;">
						<col style="*;">
					</colgroup>
					<tbody>
						<tr>
							<th class="p_tit">판매가</th>
							<td>
								<div class="price_box">
									<div class="price">
										<%-- 세일금액이 없는 경우 정가를 표시. --%>
										<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
											<p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
										</c:if>
										<p class="rst_price"><span>${op:numberFormat(item.exceptSpotDiscount)}</span>원</p>
									</div>
									<c:if test="${!(item.spotItem || (requestContext.userLogin && item.exceptUserDiscountPresentPrice != item.presentPrice))}">
										<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
											<p class="percentage">${item.discountRate}<span>%</span></p>
										</c:if>
									</c:if>
								</div>
							</td>
						</tr>
						<c:if test="${item.spotItem}">
							<tr>
								<th class="p_tit">타임세일</th>
								<td>
									<div class="price_box cf">
										<div class="price">
											<p class="rst_price op"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
										</div>
										<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
											<p class="percentage"><span>${item.discountRate}</span>%</p>
										</c:if>
									</div>
								</td>
							</tr>
						</c:if>
						<c:if test="${requestContext.userLogin && item.exceptUserDiscountPresentPrice != item.presentPrice}">
							<tr>
								<th class="p_tit">회원할인가</th>
								<td>
									<div class="price_box cf">
										<div class="price">
											<p class="rst_price"><span>${op:numberFormat(item.presentPrice)}</span>원</p>
										</div>
										<p class="txt">
											${requestContext.user.userDetail.userlevel.levelName}등급 ${op:numberFormat(requestContext.user.userDetail.userlevel.discountRate)}% 할인
											<a href="/m/pages/rating-info" class="btn_st4">회원혜택보기</a>
										</p>
									</div>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty coupon}">
							<tr>
								<th class="p_tit">최저가</th>
								<td>
									<div class="price_box cf">
										<div class="price">
											<p class="rst_price red"><span>${op:numberFormat(coupon.couponPay)}</span>${coupon.couponPayType == '1' ? "원" : "%"}</p>
										</div>
									</div>
								</td>
							</tr>
						</c:if>
						<c:if test="${couponCount > 0}">
							<tr>
								<th class="p_tit">${op:message('M00491')}혜택</th> <!-- 쿠폰 -->
								<td>
									<a href="javascript:downloadCoupon()" class="btn_st6">
										<span>${op:message('M00490')}</span><!-- 쿠폰 다운로드 -->
									</a>
								</td>
							</tr>
						</c:if>
						<tr class="item-point">
							<th>${op:message('M00246')}적립</th>
							<td>
								<span class="t_point2"></span>
							</td>
						</tr>

						<tr class="item-card-benefit">
							<th>무이자할부</th>
							<td>
								<span></span>  <a href="javascript:cardBenefitsPopup()" class="btn_st4 card_more">더보기</a>
							</td>
						</tr>

						<tr>
							<th>상품코드</th>
							<td><span class="t_tgray">${item.itemUserCode}</span></td>
						</tr>
						<tr>
							<th>판매자</th>
							<td>${seller.sellerName}</td>
						</tr>
						<tr>
							<th>제조사/원산지</th>
							<td>${maker} ${originCountry}</td>
						</tr>
						<c:if test="${item.freeGiftFlag == 'Y'}">
							<tr>
								<th>사은품</th>
								<td>
									<div>
										${item.freeGiftName}
									</div>
									<div class="shipping">
										<c:if test="${not empty item.freeGiftItemList}">
											<c:set var="firstGiftItemName" value=""/>
											<c:set var="giftItemCount" value="0"/>
											<c:forEach var="giftItem" items="${item.freeGiftItemList}" varStatus="i">

												<c:if test="${i.first}">
													<c:set var="firstGiftItemName" value="${giftItem.name}"/>
												</c:if>
												<c:set var="giftItemCount" value="${giftItemCount +1}"/>

											</c:forEach>
											<span>
												<a href="javascript:Common.popup('/m/gift-item/list-popup/${item.itemUserCode}', 'giftItemPopup', 600, 580)"
												   class="btn_st4 arr_black region_more" >
													<c:choose>
														<c:when test="${giftItemCount == 1}">
															${firstGiftItemName}
														</c:when>
														<c:otherwise>
															${firstGiftItemName} 외 ${giftItemCount - 1}개
														</c:otherwise>
													</c:choose>
												</a>
											</span>
										</c:if>
									</div>
								</td>
							</tr>
						</c:if>
						<tr>
							<th>배송 정보</th>
							<td>
								<div class="shipping">
								<c:choose>
									<c:when test="${item.shippingType == '1'}">
										<p>무료 배송</p>
									</c:when>
									<c:when test="${item.shippingType == '2' || item.shippingType == '3' || item.shippingType == '4'}">
										<c:set var="shippingTypeText" value="" />
										<c:choose>
											<c:when test="${item.shippingType == 2}"><c:set var="shippingTypeText" value="판매자" /></c:when>
											<c:when test="${item.shippingType == 3}"><c:set var="shippingTypeText" value="출고지" /></c:when>
											<c:when test="${item.shippingType == 4}"><c:set var="shippingTypeText" value="상품" /></c:when>
										</c:choose>
										<p>${op:numberFormat(item.shipping)}원</p>
										<span>${shippingTypeText} 조건부 무료 (${op:numberFormat(item.shippingFreeAmount)}원 이상 무료배송)</span>
									</c:when>
									<c:when test="${item.shippingType == '5'}">
										<p>${op:numberFormat(item.shippingItemCount)} 개당 ${op:numberFormat(item.shipping) }</p>
									</c:when>
									<c:when test="${item.shippingType == '6'}">
									   <p>${op:numberFormat(item.shipping)}원</p>
									</c:when>
								</c:choose>

								<c:if test="${item.shippingExtraCharge1 > 0 || item.shippingExtraCharge2 > 0}">
									<span>제주/도서산간 (제주 ${op:numberFormat(item.shippingExtraCharge1)}원 /도서산간 ${op:numberFormat(item.shippingExtraCharge2)}원 추가)</span>
									<a href="javascript:Common.popup('/m/island/island-popup?mode=0&code=${item.itemUserCode}', 'islandPopup', 600, 580)" class="btn_st4 arr_black region_more">도서산간지역보기</a>
								</c:if>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div><!-- //item_detail -->

			<c:set var="isCombinationOptionSoldOut" value="false" />
			<c:if test="${(item.itemOptionFlag == 'Y' && !item.itemSoldOut) || item.itemReturnFlag == 'N'}">
				<div class="item_order">
					<table>
						<caption>주문 옵션</caption>
						<tbody>
							<c:choose>
								<c:when test="${item.itemOptionFlag == 'Y' && !item.itemSoldOut && item.itemOptionType == 'C'}">
									<!--// 옵션조합형 -->
									<tr>
										<td colspan="2">
											<div class="set_box set_box_2 view">
												<a href="#" class="set_btn">기본구성상품 변경</a>
												<ul class="set_det">
													<c:forEach items="${item.optionGroups}" var="group" varStatus="i">
														<c:choose>
															<c:when test="${group.displayType == 'fixing'}">
																<c:forEach items="${item.itemOptions}" var="itemOption">
																	<c:if test="${group.title == itemOption.optionName1}">
																		<li class="op-item-option-group-info" data-index="${i.index}" data-title="${group.title}" data-display-type="${group.displayType}">
																			<div class="sd_info" data-option-id="${itemOption.itemOptionId}">
																				<a href="javascript:void(0);" class="tit">
																					${group.title}
																					<span class="sub_tit">
																						${itemOption.optionName2}
																						<%--<c:if test="${itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0}">
																							| 재고: ${op:numberFormat(itemOption.optionStockQuantity)}개
																						</c:if>
																						<c:if test="${itemOption.soldOut}">
																							<c:set var="isCombinationOptionSoldOut" value="true" />
																							- 품절
																						</c:if>--%>
																					</span>
																				</a>
																				<p class="price ${itemOption.optionPrice > 0 ? 'add_price' : ''} op-etc-price">
																					<c:choose>
																						<c:when test="${itemOption.optionPrice > 0}">+${op:numberFormat(itemOption.optionPrice)}원</c:when>
																						<c:otherwise>기본</c:otherwise>
																					</c:choose>
																				</p>
																			</div>
																		</li>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<li class="op-item-option-group-info" data-index="${i.index}" data-title="${group.title}" data-display-type="${group.displayType}">
																	<div class="sd_info">
																		<a href="javascript:void(0);" class="tit">${group.title}</a>
																		<p class="price op-etc-price">기본</p>
																	</div>
																	<div class="select_area op-item-option-info">
																		<a href="javascript:void(0);" class="select_btn op-option-select-box">선택하세요.</a>
																		<div class="option op-option-box">
																			<p class="tit">상품 옵션선택</p>
																			<a href="javascript:void(0);" class="option_close op-close-option-box">상품옵션닫기</a>
																			<ul class="option_list01 op-option-box-type1"></ul>
																		</div>
																	</div>
																</li>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</ul><!--// set_det -->
											</div><!--// set_box -->
										</td>
									</tr>
								</c:when>
								<c:when test="${item.itemOptionFlag == 'Y' && !item.itemSoldOut && item.itemOptionType != 'C'}">
									<!--// 일반 옵션 (텍스트/선택형) -->
									<tr>
										<c:choose>
											<c:when test="${item.itemOptionType == 'T'}">
												<td class="op-item-option-info">
													<h4>옵션선택</h4>
													<c:forEach items="${item.textOptions}" var="itemOption" varStatus="i">
														<div class="select_area input_area">
															<p>${itemOption.optionName1}</p>
															<input type="hidden" class="text-option-id" value="${itemOption.itemOptionId}" />
															<input type="hidden" class="text-option-name" value="${itemOption.optionName1}" />
															<input type="text" class="text-option-value required" title="${itemOption.optionName1} 입력" placeholder="필수 옵션을 작성해주세요." />
														</div>
														<c:if test="${i.count == fn:length(item.textOptions)}">
															<div class="button_area">
																<button type="button" class="btn-add-item-option">적용</button>
															</div>
														</c:if>
													</c:forEach>
												</td>
											</c:when>
											<c:otherwise>
												<td class="op-item-option-info">
													<h4>옵션선택</h4>
													<div class="select_area">
														<a href="javascript:;" class="select_btn op-option-select-box">선택하세요.</a>
														<div class="option op-option-box">
															<p class="tit">상품 옵션선택</p>
															<a href="javascript:;" class="option_close op-close-option-box">상품옵션닫기</a>
															<ul class="option_list01 op-option-box-type1"></ul>
														</div>
													</div>
												</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:when>
							</c:choose>
							<c:if test="${item.itemReturnFlag == 'N'}">
								<tr>
									<td>
										<p class="warning"><span>해당상품은 반품/교환이 불가능한 상품입니다.</span></p>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div><!--//item_order E-->
			</div><!--//view_info E-->
		</c:if>

		<!-- 네이버 NPAY구매 영역 E-->
		<c:if test="${!item.itemSoldOut}">
			<div class="npay_sec">
				<naverPay:item-page-button />
			</div><!--// npay_sec -->
		</c:if>

		<c:if test="${item.itemAdditionFlag == 'Y'}">
			<div class="add_product">
				<div class="inner">
					<h4>추가상품</h4>
					<h5>상품을 선택하면 주문상품에 추가됩니다.</h5>
					<select>
						<c:forEach items="${additionCategory}" var="category">
							<option value="#">${category.value}</option>
						</c:forEach>
					</select>
					<div class="ap_slider_sec">
						<c:forEach items="${additionCategory}" var="category">
							<div class="add_product_slider">
								<div class="swiper-wrapper op-item-addition-info">
									<c:set var="count" value="0" />
									<c:forEach items="${item.itemAdditions}" var="itemAddition">
										<c:if test="${category.key == itemAddition.categoryId && count < 10}">
											<c:set var="count">${count + 1}</c:set>
											<div class="swiper-slide op-addition-item-box"
												 data-item-addition-id="${itemAddition.itemAdditionId}" data-item-id="${itemAddition.item.itemId}"
												 data-item-price="${itemAddition.item.exceptUserDiscountPresentPrice}" data-stock-flag="${itemAddition.item.stockFlag}"
												 data-stock-quantity="${itemAddition.item.stockQuantity}">
												<a href="${itemAddition.item.link}" class="img">
													<img src="${shop:loadImageBySrc(itemAddition.item.imageSrc, 'XS')}" alt="${itemAddition.item.itemName}">
												</a>
												<div class="btn_wrap">
													<button class="btn_buy op-addition-select-box">구매</button>
													<button class="btn_detail" onclick="location.href='${itemAddition.item.link}'">상세정보</button>
												</div>
												<div class="txt">
													<a href="${itemAddition.item.link}" class="name">${itemAddition.item.itemName}</a>
													<div class="price_wrap">
														<p class="price"><span>${op:numberFormat(itemAddition.item.exceptUserDiscountPresentPrice)}</span>원</p>
														<c:if test="${itemAddition.item.totalDiscountAmount > 0 && itemAddition.item.discountRate > 0}">
															<p class="percentage"><span>${itemAddition.item.discountRate}</span><sub>%</sub></p>
														</c:if>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</div>
								<div class="swiper-pagination"></div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<!-- //add_product -->
		</c:if>

		<!-- 상품설명 -->
		<div class="view_tab op-item-guide">
			<!-- //tab_list -->
			<div class="tab_container">
				<div class="tab_detail detail_explain_box">
					<h4>상세정보</h4>
					<div id="item_content1">
						<c:if test="${item.headerContentFlag == 'Y' && !empty config.itemHeaderContentMobile}">
							<div class="edit_top">${config.itemHeaderContentMobile}</div>
						</c:if>
						<c:choose>
							<c:when test="${!empty item.detailContentMobile}">
								<div id="mobile_detail_content">${item.detailContentMobile}</div>
							</c:when>
							<c:otherwise>
								<div id="mobile_detail_content">${item.detailContent}</div>
							</c:otherwise>
						</c:choose>
						<c:if test="${item.footerContentFlag == 'Y' && !empty config.itemFooterContentMobile}">
							<div class="edit_btm">${config.itemFooterContentMobile}</div>
						</c:if>
					</div>
					<div class="more_btn_wrap">
						<a href="#" class="itc_more_btn">상세정보 더보기</a>
					</div>
				</div>

				<!-- 구매안내 -->
				<div class="tab_info">
					<div class="info_list">
						<ul>
							<li>
								<div class="main_txt">
									<a href="javascript:;">배송/교환/반품<span class="arr"></span></a>
								</div>
								<div class="sub_txt">
									${shopContext.config.deliveryInfo}
								</div>
							</li>
							<c:if test="${!empty item.itemInfos}">
								<li>
									<div class="main_txt">
										<a href="javascript:;">정보고시<span class="arr"></span></a>
									</div>
									<div class="sub_txt">
										<table>
											<colgroup>
												<col style="width:28%;">
												<col style="*">
											</colgroup>
											<tbody>
												<c:forEach items="${item.itemInfos}" var="itemInfo" varStatus="i">
													<tr>
														<th>${itemInfo.title}</th>
														<td>${op:nl2br(itemInfo.description)}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</li>
							</c:if>
						</ul>
					</div>
				</div>

				<!-- 상품후기 -->
				<div class="tab_review">
					<div class="review_top">
						<p>고객님께서 작성해주시는 이용후기은 다른 분의 쇼핑에 소중한 정보가 됩니다.</p>

						<sec:authorize access="hasRole('ROLE_USER')">
							<a href="/m/mypage/review-nonregistered" class="review_write_btn"><span>이용후기 작성</span></a>
						</sec:authorize>
						<sec:authorize access="!hasRole('ROLE_USER')">
							<a href="/m/users/login?target=/m/mypage/review-nonregistered" class="review_write_btn"><span>이용후기 작성</span></a>
						</sec:authorize>
					</div>
					<div class="review_list">
						<ul id="op-review-list" class="list-data-subject">
							<%--리뷰 리스트를 두번 불러와서 숨김 처리 2017-06-15 yulsun.yoo --%>
							<%-- <jsp:include page="review-list.jsp" /> --%>
						</ul>
						<div class="op-review-more-load load_more"><button type="button" class="btn_st2" onclick="paginationReviewMore()"><span>더보기</span></button></a></div>
					</div>
				</div>

				<!-- 상품문의 -->
				<div class="tab_qna">
					<div class="review_top">
						<p>본 상품에 대해서 궁금하신 점을 작성해서 등록해주세요.</p>
						<a href="/m/item/create-qna/${item.itemUserCode}" class="review_write_btn"><span>문의하기</span></a>
					</div>
					<div class="review_list">
						<ul id="op-qna-list" class="list-data-subject">
							<%--Q&A 리스트를 두번 불러와서 숨김 처리 2017-06-15 yulsun.yoo --%>
							<%-- <jsp:include page="qna-list.jsp" /> --%>
						</ul>
						<div class="op-qna-more-load load_more"><button type="button" class="btn_st2" onclick="paginationQnaMore()"><span>더보기</span></button></div>
					</div>
				</div>
			</div>
		</div>

		<!--// 관련상품 -->
		<div id="op-item-relation" class="relation"></div>

		<!--옵션바 : s-->
		<div class="foot_menu_m fixed_bottom custom_index">
			<!--푸터 고정-->
			<form id="cartForm" method="post">
				<input type="hidden" name="backUrl" value="/products/view/${item.itemUserCode}" />

				<%-- 상품 정보 전송용 (옵션 없는 상품) : ITEM_ID || 수량 || --%>
				<div id="op-cart-item">
					<c:if test="${item.itemOptionFlag == 'N' && !item.itemSoldOut}">
						<input type="hidden" name="arrayRequiredItems" value="${item.itemId}||${item.orderMinQuantity < 0 ? 1 : item.orderMinQuantity}||" />
					</c:if>
				</div>
				<div class="option_bottom">
					<!--옵션-->
					<c:if test="${!item.itemSoldOut}">
						<button type="button" class="btn_open">
							<span class="screen_out">옵션버튼</span>
							<div></div>
						</button>
					</c:if>
					<div class="option_area">
						<div class="conts_area type_option">
							<div class="option_yes">
								<div class="option_list">
									<ul class="list_in">
										<c:if test="${(item.itemOptionFlag == 'N' || item.itemOptionType == 'C') && !item.itemSoldOut}">
											<!-- 일반옵션 상품일때만 노출 -->
											<li class="list_in_def">
												<div class="d-flex space-between mb20">
													<p class="op_tit">[기본상품 합계금액]</p>
													<p class="op_price"><span class="total-item-price">0</span>원</p>
												</div>
												<div class="cacul op-item-quantity">
													<button type="button" class="minus op-item-order-count-minus">감소</button>
													<input type="text" class="quantity _number num" value="${item.orderMinQuantity < 0 ? 1 : item.orderMinQuantity}">
													<button type="button" class="plus op-item-order-count-plus">증가</button>
												</div>
											</li>
										</c:if>

										<c:if test="${item.itemOptionFlag == 'Y' && item.itemOptionType != 'C' && !item.itemSoldOut}">
											<li class="list_in_def op-added-options" style="display: none;">
												<div class="d-flex space-between mb20">
													<p class="op_tit">[기본상품]</p>
												</div>
											</li>
										</c:if>

										<c:if test="${item.itemAdditionFlag == 'Y'}">
											<li class="list_in_add op-added-items" style="display: none;">
												<div class="d-flex space-between mb20">
													<p class="op_tit">[추가상품 합계금액]</p>
													<p class="op_price"><span class="total-addition-price">0</span>원</p>
												</div>
											</li>
										</c:if>
									</ul>
								</div>
							</div>
						</div>

						<div class="total_area">
							<p class="total_tit">총 구매금액</p>
							<p class="total_price"><span class="total-amount">0</span>원</p>
						</div>
					</div>
					<!-- 장바구니, 구매하기 -->
					<div class="btn_area">
						<c:choose>
							<c:when test="${item.itemSoldOut}">
								<button type="button" class="btn btn_m_restock ${isRestockNotice ? 'on' : ''}" onclick="restockNotice()">재입고알림</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn_m_save" onclick="addToWishList()">
									<span class="screen_out">찜하기</span>
								</button>
								<button type="button" class="btn btn_m_cart" onclick="addToCart()">장바구니</button>
								<button type="button" class="btn btn_m_buy" onclick="buyNow()">구매하기</button>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="fixed_pay_box">
						<ul>
							<li>간편결제</li>
							<li>
								<ul class="fixed_pay_img">
									<li><img src="/content/mobile/images/common/pay_icon_01.png"></li>
									<li><img src="/content/mobile/images/common/pay_icon_02.png"></li>
								</ul>
							</li>
							<li>삼성·페이코·SSG·L페이·SK페이</li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="bgDim"></div>
		<!--옵션바 : s-->
	</div> <!-- //view_wrap -->

	<jsp:include page="../include/layer-cart.jsp" />
	<jsp:include page="../include/layer-wishlist.jsp" />

<page:javascript>
<script type="text/javascript" src="/content/modules/naverpay/naver.pay.js"></script>
<script type="text/javascript">
	// ksh 2020-12-03 옵션조합형 선택정보
	var SELECTED_COMBINATION_OPTION_INFOS = [];
	var IS_COMBINATION_OPTION_SOLD_OUT = '${isCombinationOptionSoldOut}';

	var item = {
		'itemId': Number('${item.itemId}'),
		'itemUserCode': '${item.itemUserCode}',
		'imageSrc': '${item.imageSrc}',
		'nonmemberOrderType': '${item.nonmemberOrderType}',
		'price' : Number('${item.presentPrice}'.replace(/,/g, "")),
		'stockFlag' : '${item.stockFlag}',
		'stockQuantity' : Number('${item.stockQuantity}' == '-1' ? '999' : '${item.stockQuantity}'),
		'orderMinQuantity': Number('${item.orderMinQuantity}' == '-1' ? '1' : '${item.orderMinQuantity}'),
		'orderMaxQuantity': Number('${item.orderMaxQuantity}' == '-1' ? '999' : '${item.orderMaxQuantity}'),
		'itemOptionFlag': '${item.itemOptionFlag}',
		'itemOptionType': '${item.itemOptionType}',
		'itemOptionTitle1': '${item.itemOptionTitle1}',
		'itemOptionTitle2': '${item.itemOptionTitle2}',
		'itemOptionTitle3': '${item.itemOptionTitle3}',
		'itemAdditionFlag': '${item.itemAdditionFlag}',
		'isItemSoldOut': '${item.itemSoldOut}' == 'true' ? true : false,		<%-- 품절여부 : 상품 + 옵션 조건 ('true', 'false') --%>
		'stockScheduleType': '${item.stockScheduleType}',
		'stockScheduleText': '${item.stockScheduleText}',
		'presentPrice' : Number('${item.presentPrice}')
	};

	var itemOptionGroups = [];
	<c:forEach items="${item.optionGroups}" var="group" varStatus="i">itemOptionGroups[${i.index}] = {'title': '${group.title}', 'displayType': '${group.displayType}'};</c:forEach>

	var itemOptions = [];
	<c:forEach items="${item.itemOptions}" var="itemOption" varStatus="i">itemOptions[${i.index}] = {'itemOptionId': '${itemOption.itemOptionId}', 'optionType': '${itemOption.optionType}', 'optionDisplayType': '${itemOption.optionDisplayType}', 'optionName1': '${itemOption.optionName1}', 'optionName2': '${itemOption.optionName2}', 'optionName3': '${itemOption.optionName3}', 'optionPrice': Number('${itemOption.optionPrice}'), 'optionStockFlag': '${itemOption.optionStockFlag}', 'optionStockQuantity': Number('${itemOption.optionStockQuantity}' == '-1' ? '99999' : '${itemOption.optionStockQuantity}'), 'soldOutFlag': '${itemOption.optionSoldOutFlag}', 'isSoldOut': '${itemOption.soldOut}'};</c:forEach>

	var itemOptionImages = [];
	<c:forEach items="${item.itemOptionImages}" var="itemOptionImage" varStatus="i">itemOptionImages[${i.index}] = {'itemOptionImageId': '${itemOptionImage.itemOptionImageId}', 'itemOptionId': '${itemOptionImage.itemOptionId}', 'itemId': '${itemOptionImage.itemId}', 'optionName': '${itemOptionImage.optionName}', 'optionImage': '/upload/item/${item.itemUserCode}/option/${itemOptionImage.optionImage}'};</c:forEach>

	var shipping = {
		'shippingType' : '${item.shippingType}'
		, 'shipping' : '${item.shipping}'
		, 'shippingFreeAmount' : '${item.shippingFreeAmount}'
		, 'shippingItemCount' : '${item.shippingItemCount}'
	};

	var isRestockNotice = '${isRestockNotice}',
		requestUri = '${requestContext.requestUri}',
		isLogin = '${requestContext.userLogin}',
		loginPopup = '${loginPopup}';

	$(function () {
		try {
			EventLog.item(item.itemUserCode);
		} catch (e) {}

		// 타임세일
		var isSpotItem = '${item.spotItem}';
		if (isSpotItem == 'true') {
			var item_tsTime = "${item.spotFormatDate}";
			$(".view_ts .ts_timer").downCount({
				date: item_tsTime,
				offset: +9
			});
		}
	});
</script>
<script src="/content/modules/mobile/item.view.js"></script>

</page:javascript>
