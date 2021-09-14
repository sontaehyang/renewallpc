<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>
	<div class="view_wrap">
		<div class="inner item_top_sec">
			<div class="location_area">
				<div class="breadcrumbs">
					<a href="/" class="home"><span class="hide">home</span></a>
					<c:forEach items="${breadcrumbsForSelectbox}" var="codes" varStatus="i">
						<c:if test="${!i.first}">
							<span>
								<select title="">
									<c:forEach items="${codes}" var="code">
										<c:if test="${code.detail == '1'}">
											<option value="${code.value}" ${code.detail == '1' ? 'selected="selected"' : ''}>
												${code.label}
												<c:set var="ctLabel" value="${code.label}"></c:set>
											</option>
										</c:if>
									</c:forEach>
									<optgroup label="--------------------">
										<c:forEach items="${codes}" var="code">
											<option value="${code.value}">
												${code.label}
												<c:set var="ctLabel" value="${code.label}"></c:set>
											</option>
										</c:forEach>
									</optgroup>
								</select>
							</span>
						</c:if>
					</c:forEach>
				</div><!--// breadcrumbs -->
				<p class="prd_code">상품코드<b>${item.itemUserCode}</b></p>
			</div><!-- // location_area E -->
			<div class="view_top">
				<div class="photo_wrap">
					<div class="item_photo${item.itemSoldOut ? ' sold-out' : ''}">
						<c:set var="imageCount" value="0" />
						<c:set var="imageAlt">${item.itemName}</c:set>
						<div class="product_inner_preview">
							<div class="productScreen">
								<div class="swiper-container item-slider">
									<div class="swiper-wrapper">
										<c:forEach items="${item.itemImages}" var="itemImage">
											<c:if test="${imageCount > 0}">
												<c:set var="imageAlt">${item.itemName}_${imageCount}</c:set>
											</c:if>

											<c:if test="${itemImage.itemImageId != 0}">
												<div class="swiper-slide">
													<img src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" class="zoom" data-magnify-src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" alt="${imageAlt}" />
												</div>
												<c:set var="imageCount">${imageCount + 1}</c:set>
											</c:if>
										</c:forEach>
										<c:if test="${imageCount == 0}">
											<div class="swiper-slide">
												<img src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" class="zoom" data-magnify-src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" alt="${imageAlt}" />
											</div>
										</c:if>
									</div>
								</div><!--// item-slider -->

								<ul class="item-slider">
									<c:forEach items="${item.itemImages}" var="itemImage">
										<c:if test="${imageCount > 0}">
											<c:set var="imageAlt">${item.itemName}_${imageCount}</c:set>
										</c:if>

										<c:if test="${itemImage.itemImageId != 0}">
											<li>
												<img src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" class="zoom" data-magnify-src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" alt="${imageAlt}" />
											</li>
											<c:set var="imageCount">${imageCount + 1}</c:set>
										</c:if>
									</c:forEach>
									<c:if test="${imageCount == 0}">
										<li>
											<img src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" class="zoom" data-magnify-src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'L') }" alt="${imageAlt}" />
										</li>
									</c:if>
								</ul>
							</div><!--// productScreen -->
						</div><!--// product_inner_preview -->

						<div class="pager_sec">
							<!-- Add Arrows -->
							<div class="swiper-button-prev"></div>
							<div class="swiper-button-next"></div>

							<div class="swiper-container item-thumbs">
								<div class="swiper-wrapper">
									<c:set var="imageCount" value="0" />
									<c:set var="imageAlt">${item.itemName}</c:set>
									<c:forEach items="${item.itemImages}" var="itemImage" varStatus="i">
										<c:if test="${imageCount > 0}">
											<c:set var="imageAlt">${item.itemName}_${imageCount}</c:set>
										</c:if>

										<c:if test="${itemImage.itemImageId != 0}">
											<div class="swiper-slide"><img src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'XS') }" alt="${imageAlt}"  id="${itemImage.itemImageId}" /></div>
											<c:set var="imageCount">${imageCount + 1}</c:set>
										</c:if>
									</c:forEach>

									<c:if test="${imageCount == 0}">
										<div class="swiper-slide"><img src="${item.imageSrc}" alt="${imageAlt}" /></div>
									</c:if>
								</div>
							</div><!--// item-thumbs -->
						</div><!--// pager_sec -->

						<div class="sns_list">
							<ul>
								<%--<li><a href="javascript:Common.popup('http://band.us/@7beauty','naverband_popup', 1000, 1000, 1);" rel="nofollow"><img src="/content/images/product/sns_line.gif" alt="naverline" width="32" height="32" /></a></li> --%>
								<li><a href="javascript:Social.share('facebook', '${item.itemName}', '${item.eventViewUrl}')" rel="nofollow"><img src="/content/images/common/sns_facebook.gif" alt="facebook" /></a></li>
								<li><a href="javascript:Social.share('twitter', '${item.itemName}', '${item.eventViewUrl}')" rel="nofollow"><img src="/content/images/common/sns_twitter.gif" alt="twitter" /></a></li>
								<%--<li><a href="javascript:sendRecommendMail()"><img src="/content/images/product/sns_mail.gif" alt="email"></a></li> --%>
								<li><a href="javascript:Common.popup('https://story.kakao.com/share?url=${item.eventViewUrl}','kakaostory_popup', 500, 500, 1);" rel="nofollow"><img src="/content/images/common/sns_story.gif" alt="kakao-story" width="32" height="32" /></a></li>
								<%--<li><a href="#"><img src="/content/images/common/sns_talk.gif" alt="kakao-talk" title="카카오톡"></a></li>--%>
							</ul>
						</div><!-- // sns_list E -->
					</div><!-- // item_photo E -->
					<a href="#" class="btn_item_view" title="상세보기"><img src="/content/images/icon/btn_item_view.png" alt="상세보기"></a>
				</div><!-- // photo_wrap E -->

				<div class="item_info_view">
					<c:if test="${!item.itemSoldOut}">
						<div class="info_top_btns">
							<a href="javascript:popupQuotation();" class="print_btn">견적서출력</a>
								<%--<a href="#" class="share_btn">공유하기</a>--%>
						</div><!--// info_top_btns -->
					</c:if>
					<div class="item_label">
						<c:forEach items="${item.itemLabels}" var="label">
							<span><img src="${label.imageSrc}" alt="${label.description}"></span>
						</c:forEach>
					</div>
					<p class="item_title" id="itemTitle">${item.itemName}</p>
					<c:if test="${!empty item.itemSummary}">
						<p class="item_subTitle">${op:nl2br(item.itemSummary)}</p>
					</c:if>
					<c:if test="${item.spotItem}">
						<div class="spot_time">
							타임세일
							<div class="ts_box">
								<p class="days">0</p>
								<span class="hours">00</span>
								<span class="minutes">00</span>
								<span class="seconds">00</span>
							</div>
							<a href="/event/spot" class="btn_more">타임세일 더보기</a>
						</div><!-- // spot_time E -->
					</c:if>
					<dl class="view_sale_price typeA">
						<dt>판매가</dt>
						<dd class="sale_price_info">
							<div class="price">
								<p class="cost">
									<%-- 세일금액이 없는 경우 정가를 표시. --%>
									<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
										<span>${op:numberFormat(item.listPrice)}</span>원

										<c:if test="${item.sellerDiscountPrice > 0}">
											(즉시할인 : ${op:numberFormat(item.sellerDiscountPrice)}원)
										</c:if>
									</c:if>
								</p>
								<p class="sale">
									<span>${op:numberFormat(item.exceptSpotDiscount)}</span>원
									<c:if test="${!(item.spotItem || (requestContext.userLogin && item.exceptUserDiscountPresentPrice != item.presentPrice))}">
										<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
											<span class="percent">${item.discountRate }%</span>
										</c:if>
									</c:if>
								</p>
							</div>
						</dd>
					</dl>
					<c:if test="${item.spotItem || (requestContext.userLogin && item.exceptUserDiscountPresentPrice != item.presentPrice) || !empty coupon}">
						<dl class="view_sale_price typeB">
						<c:if test="${item.spotItem}">
							<dt>타임세일</dt>
							<dd>
								<p class="sale ${requestContext.userLogin && item.exceptUserDiscountPresentPrice != item.presentPrice ? '' : 'strong'}">
								<span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원
								<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<span class="percent">${item.discountRate }%<img src="/content/images/icon/icon_sale_down.png" alt="sale"></span>
								</c:if>
								</p>
							</dd>
						</c:if>
						<c:if test="${requestContext.userLogin && item.exceptUserDiscountPresentPrice != item.presentPrice}">
							<dt>회원할인가</dt>
								<dd class="members">
								<p class="sale strong"><span>${op:numberFormat(item.presentPrice)}</span>원</p>
								<span class="rating"><span>${requestContext.user.userDetail.userlevel.levelName}</span> 등급 <span>${requestContext.user.userDetail.userLevelDiscountRate}%</span>할인 </span>
								<a href="javascript:Common.popup('/pages/rating-info', 'grade', 735, 680);" class="btn_membership" title="회원헤택보기">회원헤택보기</a>
							</dd>
						</c:if>
						<c:if test="${not empty coupon}">
							<dt>최저가</dt>
							<dd>
								<p class="sale strong"><span>${op:numberFormat(item.presentPrice - coupon.discountPrice)}</span>원</p>
								<span class="rating">
									${coupon.couponName}
									<c:if test='${coupon.discountPrice > 0}'>
										<span> ${op:numberFormat(coupon.couponPay)}${coupon.couponPayType == '1' ? "원" : "%"} 할인</span>
									</c:if>
								</span>
							</dd>
						</c:if>
						</dl>
					</c:if>

					<dl>
						<c:if test="${couponCount > 0}">
							<dt>쿠폰혜택</dt>
							<dd><a href="javascript:downloadCoupon()" class="btn btn-coupon" title="쿠폰다운">쿠폰다운</a></dd>
						</c:if>
						<dt class="item-point">${op:message('M00246')} 적립</dt>
						<dd class="item-point"></dd>
						<dt class="item-card-benefit">무이자할부</dt>
						<dd class="item-card-benefit"><a data-id="0" href="javascript:cardBenefitsPopup();" class="more" title="더보기">더보기▶</a></dd>
						<dt>상품코드</dt>
						<dd>${item.itemUserCode}</dd>
						<dt>판매자</dt>
						<dd>${seller.sellerName}</dd>
						<c:if test="${not empty item.originCountry or not empty item.manufacturer}">
							<dt>제조사/원산지</dt>
							<dd>${item.manufacturer} / ${item.originCountry }</dd>
						</c:if>
						<c:if test='${item.freeGiftFlag == "Y"}'>
							<dt>사은품</dt>
							<dd class="delivery">
								<p>${item.freeGiftName}</p>
								<c:if test="${not empty item.freeGiftItemList}">
									<c:set var="firstGiftItemName" value=""/>
									<c:set var="giftItemCount" value="0"/>
									<c:forEach var="giftItem" items="${item.freeGiftItemList}" varStatus="i">
										<c:if test="${i.first}">
											<c:set var="firstGiftItemName" value="${giftItem.name}"/>
										</c:if>
										<c:set var="giftItemCount" value="${giftItemCount +1}"/>
									</c:forEach>
									<p>
										<a href="javascript:Common.popup('/gift-item/list-popup/${item.itemUserCode}', 'giftItemPopup', 600, 580)">
											<c:choose>
												<c:when test="${giftItemCount == 1}">
													${firstGiftItemName}
												</c:when>
												<c:otherwise>
													${firstGiftItemName} 외 ${giftItemCount - 1}개
												</c:otherwise>
											</c:choose>
										</a>
									</p>
								</c:if>
							</dd>
						</c:if>
					</dl>

					<dl>
						<dt>배송</dt>
						<dd class="delivery">
							<p>배송방법 : 택배 (${item.deliveryCompanyName})</p>
							<p>
							<c:choose>
								<c:when test="${item.shippingType == '1'}">
									무료배송
								</c:when>
								<c:when test="${item.shippingType == '2' || item.shippingType == '3' || item.shippingType == '4'}">
									<c:set var="shippingTypeText" value="" />
									<c:choose>
										<c:when test="${item.shippingType == 2}"><c:set var="shippingTypeText" value="판매자" /></c:when>
										<c:when test="${item.shippingType == 3}"><c:set var="shippingTypeText" value="출고지" /></c:when>
										<c:when test="${item.shippingType == 4}"><c:set var="shippingTypeText" value="상품" /></c:when>
									</c:choose>
									${shippingTypeText} 조건부 무료 (${op:numberFormat(item.shipping)}원 | ${op:numberFormat(item.shippingFreeAmount)} 이상 무료)
								</c:when>
								<c:when test="${item.shippingType == '5'}">
									${op:numberFormat(item.shippingItemCount)} 개당 ${op:numberFormat(item.shipping) }
								</c:when>
								<c:when test="${item.shippingType == '6'}">
									${op:numberFormat(item.shipping)}원
								</c:when>
							</c:choose>
							</p>
							<c:if test="${item.shippingExtraCharge1 > 0 || item.shippingExtraCharge2 > 0}">
								<p>도서산간 배송비 추가 (제주: ${op:numberFormat(item.shippingExtraCharge1)}원, 도서산간 : ${op:numberFormat(item.shippingExtraCharge2)}원)</p>
								<a href="javascript:Common.popup('/island/island-popup?mode=0', 'islandPopup', 600, 580)" title="도서산간지역보기">도서산간지역보기▶</a>
							</c:if>
						</dd>
					</dl>

					<c:set var="isCombinationOptionSoldOut" value="false" />
					<c:if test="${item.itemOptionFlag == 'Y' && !item.itemSoldOut}">
						<c:choose>
							<c:when test="${item.itemOptionType == 'C'}">
								<!--// 옵션조합형 -->
								<div class="def_option_sec">
									<p class="tit">기본 구성상품 변경</p>
									<div class="def_box">
										<ul class="head">
											<li>구성</li>
											<li>PC 주요부품</li>
											<li>변동사항</li>
										</ul><!--// head -->
										<c:forEach items="${item.optionGroups}" var="group" varStatus="i">
											<ul class="item-option-group-info" data-index="${i.index}" data-display-type="${group.displayType}">
												<li class="group-title">
													${group.title}
												</li>
												<li>
													<c:set var="etcPrice" value="" />
													<div class="option_info">
														<c:choose>
															<c:when test="${group.displayType == 'fixing'}">
																<c:forEach items="${item.itemOptions}" var="itemOption">
																	<c:if test="${group.title == itemOption.optionName1}">
																		<span data-option-id="${itemOption.itemOptionId}">
																			${itemOption.optionName2}
																			<%--<c:if test="${itemOption.optionStockFlag == 'Y' && itemOption.optionStockQuantity > 0}">
																				| 재고: ${op:numberFormat(itemOption.optionStockQuantity)}개
																			</c:if>
																			<c:if test="${itemOption.soldOut}">
																				<c:set var="isCombinationOptionSoldOut" value="true" />
																				- 품절
																			</c:if>--%>
																			<c:if test="${itemOption.optionPrice > 0}">
																				<c:set var="etcPrice" value="+ ${op:numberFormat(itemOption.optionPrice)}원" />
																			</c:if>
																		</span>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<div class="option-area">
																	<div class="option-wrap">
																		<div class="item-option-info">
																			<div id="option_quantity">
																				<a href="#" class="option-select-box">
																					<span class="selected-option">선택하세요.</span>
																					<span class="glyphicon glyphicon-chevron-down"></span>
																				</a>
																				<div class="option-box" style="display: none;">
																					<div class="option-header">상품옵션 선택
																						<a href="#" class="close-option-box"><img src="/content/images/btn/option-close-btn.gif" alt="닫기"></a>
																					</div>

																					<div class="option-box-type1">
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</c:otherwise>
														</c:choose>
													</div>
												</li>
												<li><input type="text" class="etc_price" placeholder="기본" value="${etcPrice}" readonly></li>
											</ul>
										</c:forEach>
									</div>
								</div><!--// def_option_sec -->
							</c:when>
							<c:otherwise>
								<!--// 일반 옵션 (텍스트/선택형) -->
								<c:choose>
									<c:when test="${item.itemOptionType == 'T'}">
										<dl class="opt_sel item-option-info">
											<dt>옵션선택</dt>
											<c:forEach items="${item.textOptions}" var="itemOption" varStatus="i">
												<dd class="txt_opt">
													<p>${itemOption.optionName1}</p>
													<input type="hidden" class="text-option-id" value="${itemOption.itemOptionId}" />
													<input type="hidden" class="text-option-name" value="${itemOption.optionName1}" />
													<input type="text" class="text-option-value" title="${itemOption.optionName1}" placeholder="필수 옵션을 작성해주세요." />
												</dd>
											</c:forEach>
											<dd class="bbtns">
												<button type="button" class="btn-add-item-option">적용</button>
											</dd>
										</dl>
									</c:when>
									<c:otherwise>
										<dl class="opt_sel">
											<dt>옵션선택</dt>
											<dd>
												<div class="option_info">
													<div class="option-area">
														<div class="option-wrap">
															<div class="item-option-info">
																<a href="#" class="option-select-box">
																	<span class="selected-option">선택하세요.</span>
																	<span class="glyphicon glyphicon-chevron-down"></span>
																</a>
																<div class="option-box" style="display: none;">
																	<div class="option-header">
																		상품옵션 선택
																		<c:if test="${item.itemOptionType == 'S2' || item.itemOptionType == 'S3'}">
																			<a href="#" class="btn-option-box-type btn btn-default btn-xs">전체옵션보기</a>
																		</c:if>
																		<a href="#" class="close-option-box"><img src="/content/images/btn/option-close-btn.gif" alt="닫기"></a>
																	</div>

																	<div class="option-box-type1">

																	</div> <!--// option-box-type1 E-->

																	<div class="option-box-type2">
																		<div class="option-group">
																			<ul>
																				<c:forEach items="${item.itemOptions}" var="itemOption">
																					<c:if test="${itemOption.optionType == 'S2' || itemOption.optionType == 'S3'}">
																						<li>
																							<c:if test="${!itemOption.soldOut}">
																								<a href="#" data-option-id="${itemOption.itemOptionId}">
																									${itemOption.optionName1}/${itemOption.optionName2}${empty itemOption.optionName3 ? '' : '/'}${itemOption.optionName3}
                                                                                                    <c:if test="${itemOption.optionPrice > 0}">
                                                                                                        (+${op:numberFormat(itemOption.optionPrice)}원)
                                                                                                    </c:if>
																								</a>
																							</c:if>
																							<c:if test="${itemOption.soldOut}">
																								<span>
																									${itemOption.optionName1}/${itemOption.optionName2}${empty itemOption.optionName3 ? '' : '/'}${itemOption.optionName3}
																									 <c:if test="${itemOption.optionPrice > 0}">
																										 (+${op:numberFormat(itemOption.optionPrice)}원)
																									 </c:if>
																									- 품절
																								</span>
																							</c:if>
																						</li>
																					</c:if>
																				</c:forEach>
																			</ul>
																		</div>
																	</div>
																</div><!--// option-box E-->
															</div>
														</div>
													</div>
												</div><!--// option_info E-->
											</dd>
										</dl>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:if>

					<form id="cartForm" method="post">

						<%-- 상품 정보 전송용 (옵션 없는 상품) : ITEM_ID || 수량 || --%>
						<div id="cart-item">
							<c:if test="${item.itemOptionFlag == 'N' && !item.itemSoldOut}">
								<input type="hidden" name="arrayRequiredItems" value="${item.itemId}||${item.orderMinQuantity < 0 ? 1 : item.orderMinQuantity}||" />
							</c:if>
						</div>
						<c:if test="${item.rentalPayFlag == 'Y' && !item.itemSoldOut}">
							<input type="hidden" id="buyRentalPay" name="buyRentalPay" />
							<input type="hidden" id="rentalTotAmt" name="rentalTotAmt" />
							<input type="hidden" id="rentalMonthAmt" name="rentalMonthAmt" />
							<input type="hidden" id="rentalPartnershipAmt" name="rentalPartnershipAmt" />
							<input type="hidden" id="rentalPer" name="rentalPer" />
						</c:if>


						<div class="btm_fix_sec">
							<c:if test="${(item.itemOptionFlag == 'N' || item.itemOptionType == 'C') && !item.itemSoldOut}">
								<!-- 일반상품 또는 옵션조합형일때만 노출 -->
								<dl class="order-amount">
									<dt>기본상품 합계금액</dt>
									<dd>
										<span class="amount item-quantity">
											<button type="button" class="minus" title="삭제"><img src="/content/images/icon/icon_minus.gif" alt="삭제"></button>
											<input type="text" title="수량" class="quantity _number" value="${item.orderMinQuantity < 0 ? 1 : item.orderMinQuantity}" maxlength="3">
											<button type="button" class="plus" title="추가"><img src="/content/images/icon/icon_plus.gif" alt="추가"></button>
										</span>
									</dd>
									<dd class="price"><b class="total-item-price">0</b>원</dd>
								</dl>
							</c:if>

							<c:if test="${item.itemOptionFlag == 'Y' && item.itemOptionType != 'C' && !item.itemSoldOut}">
								<!-- 일반옵션 상품일때만 노출 -->
								<div class="append-area opt_append added-options" style="display: none;">
									<p class="area_tit">기본상품</p>
									<ul></ul><!--// append-area E-->
								</div><!--// append-area -->
							</c:if>

							<c:if test="${item.itemAdditionFlag == 'Y'}">
								<div class="append-area added-items" style="display: none;">
									<p class="area_tit">추가상품</p>
									<ul>
										<li class="total">
											<p class="tit">추가상품 합계금액</p>
											<p class="price"><b class="total-addition-price">0</b>원</p>
										</li>
									</ul><!--// append-area E-->
								</div><!--// append-area -->
							</c:if>

							<div class="bfs_btm">
								<div class="total-price clear">
									<span>총 결제금액</span>
									<strong><span class="total-amount">0</span>원</strong>
								</div><!--// total-price E-->

								<c:if test="${item.itemReturnFlag == 'N'}">
									<div class="item-warning">
										<span>해당상품은 반품/교환이 불가능한 상품입니다.</span>
									</div><!--// item-warning E-->
								</c:if>

								<div class="item-view-buttons">
									<c:choose>
										<c:when test="${item.itemSoldOut}">
											<button type="button" class="item-btn restock ${isRestockNotice ? 'on' : ''}" onclick="javascript:restockNotice()" title="재입고알림">재입고알림</button>
										</c:when>
										<c:otherwise>
											<button type="button" class="item-btn buy" onclick="try{AW_PRODUCT($('.quantity').val());}catch(e){}; javascript:buyNow()" title="구매하기">구매하기</button>
											<c:choose>
												<c:when test="${item.rentalPayFlag == 'Y' && !item.itemSoldOut}">
													<button type="button" class="item-btn cart" onclick="try{AW_PRODUCT($('.quantity').val());}catch(e){}; javascript:addToCartItemView(); toCollectionScript('cart');" title="장바구니">장바구니</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="item-btn cart" onclick="try{AW_PRODUCT($('.quantity').val());}catch(e){}; javascript:addToCart(); toCollectionScript('cart');" title="장바구니">장바구니</button>
												</c:otherwise>
											</c:choose>
											<button type="button" class="item-btn wish" onclick="javascript:addToWishList(); toCollectionScript('wishlist');"  title="관심상품">관심상품</button>

											<!-- 네이버 NPAY구매 영역 E-->
											<naverPay:item-page-button />
										</c:otherwise>
									</c:choose>
								</div>

								<div class="box_list info-item-pay">
									<div class="pay_box01">
										<p>결제 가능한 <strong>페이안내<i>!</i></strong><span>주문/결제시 다양한 간편결제로 결제가 가능합니다.</span></p>
										<ul>
											<li><span><img src="/content/images/common/pay_img_01.png"></span><span><img src="/content/images/common/pay_img_02.png"></span></li>
											<li><img src="/content/images/common/pay_img_03.png"></li>
										</ul>
									</div>
								</div>
							</div><!--// bfs_btm -->
							<c:if test="${item.rentalPayFlag == 'Y' && !item.itemSoldOut}">
								<script type="text/javascript" src="http://211.178.29.124:8082/resources/common/rentalPg.js"></script>
								<div class="rental_area">
									<p class="tit">렌탈로 리뉴올 PC 이용하기</p>
									<div class="month_select monthRentalContPerRentalPg">
										<div class="month">
											<button type="button" id="rental_per_24" onclick="getMonthRentalPer('24')">24개월</button>
											<button type="button" id="rental_per_36" onclick="getMonthRentalPer('36')">36개월</button>
											<button type="button" id="rental_per_48" onclick="getMonthRentalPer('48')">48개월</button>
											<button type="button" id="rental_per_60" onclick="getMonthRentalPer('60')">60개월</button>
										</div>
										<span class="txt">무료배송</span>
									</div>
									<dl class="month_price">
										<dt class="rental_txt">렌탈료</dt>
										<dd class="rental_price">월 <span class="rental_price_month">0</span>원</dd>
										<dt class="rental_txt card">제휴카드 렌탈료</dt>
										<dd class="rental_price card">월 <span class="rental_price_month_partnership">0</span>원</dd>
									</dl>
									<div class="rental_total">
										<p class="txt">총 렌탈료</p>
										<p class="total"><span class="rental_total_price">0</span>원</p>
									</div>
									<button type="button" onclick="javascript:buyRental()" class="btn_rental">렌탈 구매하기</button>
								</div>
							</c:if>
						</div><!--// btm_fix_sec -->
					</form>
				</div>	<!-- // item_info_view E -->
			</div><!--//view_top E-->
		</div><!--// item_top_sec -->

		<c:if test="${item.itemAdditionFlag == 'Y'}">
			<div class="inner item_add_sec">
				<p class="top_tit">추가상품<span>상품을 선택하면 주문상품에 추가됩니다.</span></p>
				<div class="iadd_tabs">
					<c:forEach items="${additionCategory}" var="category" varStatus="i">
						<a href="#" class="${i.index == 0 ? 'on' : ''}">${category.value}</a>
					</c:forEach>
				</div><!--// iadd_tabs -->
				<div class="iadd_tc_sec">
					<c:forEach items="${additionCategory}" var="category">
						<div class="iadd_tabcont">
							<ul class="iadd_list item-addition-info">
								<c:set var="count" value="0" />
								<c:forEach items="${item.itemAdditions}" var="itemAddition">
									<c:if test="${category.key == itemAddition.categoryId && count < 10}">
										<c:set var="count">${count + 1}</c:set>
										<li>
											<div class="ov_sel addition-item-box" data-item-addition-id="${itemAddition.itemAdditionId}" data-item-id="${itemAddition.item.itemId}"
												 data-item-price="${itemAddition.item.exceptUserDiscountPresentPrice}" data-stock-flag="${itemAddition.item.stockFlag}"
												 data-stock-quantity="${itemAddition.item.stockQuantity}">
												<button class="sel_btn addition-select-box">함께 구매</button>
												<a href="${itemAddition.item.link}" class="det_btn" target="_blank">상세화면</a>
											</div><!--// ov_sel -->
											<p class="img"><img src="${shop:loadImageBySrc(itemAddition.item.imageSrc, 'XS')}" alt="${itemAddition.item.itemName}"></p>
											<p class="tit">${itemAddition.item.itemName}</p>
											<dl class="price">
												<dt><b>${op:numberFormat(itemAddition.item.exceptUserDiscountPresentPrice)}</b>원</dt>
												<c:if test="${itemAddition.item.totalDiscountAmount > 0 && itemAddition.item.discountRate > 0}">
													<dd>${itemAddition.item.discountRate}</dd>
												</c:if>
											</dl>
										</li>
									</c:if>
								</c:forEach>
							</ul><!--// iadd_list -->
						</div>
					</c:forEach>
				</div><!--// iadd_tc_sec -->
			</div><!--// inner item_add_sec -->
		</c:if>

		<div class="item_tabc_sec">
			<div class="inner">
				<div class="item-tab-content-wrap">
					<div class="cont_tabs">
						<a href="#item_content1" class="on">상품정보</a>
						<a href="#item_content4">구매안내</a><!--배송/반품/교환 -->
						<a href="#item_content2">상품후기 (<span class="item-review-count">0</span>)</a><!--이용후기 -->
						<a href="#item_content3">상품문의 (<span class="item-qna-count">0</span>) </a><!--상품문의 -->
					</div><!--// cont_tabs -->

					<!-- 상품정보 시작-->
					<div id="item_content1" class="item-tab-content">
						<c:if test="${item.headerContentFlag == 'Y' && !empty config.itemHeaderContent}">
							<div class="edit_top">${config.itemHeaderContent}</div>
						</c:if>
						<c:if test="${!empty item.detailContent}">
							<div>${item.detailContent}</div>
						</c:if>
						<c:if test="${item.footerContentFlag == 'Y' && !empty config.itemFooterContent}">
							<div class="edit_btm">${config.itemFooterContent}</div>
						</c:if>
						<a href="#" class="itc_more_btn">상세정보 더보기</a>
					</div><!--// #item_content1 -->

					<!-- 배송/반품/교환 시작-->
					<div id="item_content4" class="item-tab-content">
						${itemDescriptionTab}
						<div>
							<h3>배송/반품/교환 상세 정보</h3>
							${shopContext.config.deliveryInfo}
						</div>
					</div><!--// item_content4 -->

					<!--이용후기 시작-->
					<div id="item_content2" class="item-tab-content">
						<div class="reviews">
							<div class="reviews_title">
								<p>총 <span class="item-review-count">0</span>건</p>
								<sec:authorize access="hasRole('ROLE_USER')">
									<a class="btn btn-m btn-default" href="/mypage/review-nonregistered">상품후기 작성</a>
								</sec:authorize>
								<sec:authorize access="!hasRole('ROLE_USER')">
									<a class="btn btn-m btn-default" href="${op:property('saleson.url.shoppingmall')}/users/login?target=/mypage/review-nonregistered">상품후기 작성</a>
								</sec:authorize>
							</div>

							<div id="review-list">
								<jsp:include page="review-list.jsp" />
							</div>
						</div>
					</div><!--// #item_content2 -->

					<!--상품 QnA 시작 -->
					<div id="item_content3" class="item-tab-content">
						<div class="reviews">
							<div class="reviews_title">
								<p>총 <span class="item-qna-count">0</span>건</p>
								<a class="btn btn-m btn-default" href="javascript:Common.popup('/item/create-qna/${item.itemUserCode}', 'create_qna', 820, 615, 0)">상품문의 작성</a>
							</div>
							<div id="qna-list">
								<jsp:include page="qna-list.jsp" />
							</div>
						</div>
					</div><!--// #item_content3 -->
				</div><!--// item-tab-content-wrap -->

				<div class="scr_fix_sec">
					<c:if test="${(item.itemOptionFlag == 'N' || item.itemOptionType == 'C') && !item.itemSoldOut}">
						<!-- 일반상품 또는 옵션조합형일때만 노출 -->
						<dl class="order-amount">
							<dt>기본상품 합계금액</dt>
							<dd>
								<span class="amount item-quantity">
									<button type="button" class="minus" title="삭제"><img src="/content/images/icon/icon_minus.gif" alt="삭제"></button>
									<input type="text" title="수량" class="quantity _number" value="${item.orderMinQuantity < 0 ? 1 : item.orderMinQuantity}" maxlength="3">
									<button type="button" class="plus" title="추가"><img src="/content/images/icon/icon_plus.gif" alt="추가"></button>
								</span>
							</dd>
							<dd class="price"><b class="total-item-price">0</b>원</dd>
						</dl>
					</c:if>

					<c:if test="${item.itemOptionFlag == 'Y' && item.itemOptionType != 'C' && !item.itemSoldOut}">
						<!-- 일반옵션 상품일때만 노출 -->
						<div class="append-area opt_append added-options" style="display: none;">
							<p class="area_tit">기본상품</p>
							<ul></ul><!--// append-area E-->
						</div><!--// append-area -->
					</c:if>

					<c:if test="${item.itemAdditionFlag == 'Y'}">
						<div class="append-area added-items" data-position="sub" style="display: none;">
							<p class="area_tit">추가상품</p>
							<ul>
								<li class="total">
									<p class="tit">추가상품 합계금액</p>
									<p class="price"><b class="total-addition-price">0</b>원</p>
								</li>
							</ul><!--// append-area E-->
						</div><!--// append-area -->
					</c:if>

					<div class="bfs_btm">
						<div class="total-price clear">
							<span>총 결제금액</span>
							<strong><span class="total-amount">0</span>원</strong>
						</div><!--// total-price E-->

						<c:if test="${item.itemReturnFlag == 'N'}">
							<div class="item-warning">
								<span>해당상품은 반품/교환이 불가능한 상품입니다.</span>
							</div><!--// item-warning E-->
						</c:if>

						<div class="item-view-buttons">
							<c:choose>
								<c:when test="${item.itemSoldOut}">
									<button type="button" class="item-btn restock ${isRestockNotice ? 'on' : ''}" onclick="javascript:restockNotice()" title="재입고알림">재입고알림</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="item-btn buy" onclick="try{AW_PRODUCT($('.quantity').val());}catch(e){}; javascript:buyNow()" title="구매하기">구매하기</button>
									<c:choose>
										<c:when test="${item.rentalPayFlag == 'Y' && !item.itemSoldOut}">
											<button type="button" class="wqitem-btn cart" onclick="try{AW_PRODUCT($('.quantity').val());}catch(e){}; javascript:addToCartItemView(); toCollectionScript('cart');" title="장바구니">장바구니</button>
										</c:when>
										<c:otherwise>
											<button type="button" class="wqitem-btn cart" onclick="try{AW_PRODUCT($('.quantity').val());}catch(e){}; javascript:addToCart(); toCollectionScript('cart');" title="장바구니">장바구니</button>
										</c:otherwise>
									</c:choose>
									<button type="button" class="item-btn wish" onclick="javascript:addToWishList(); toCollectionScript('wishlist');"  title="관심상품">관심상품</button>
									<!-- 네이버 NPAY구매 영역 E-->
									<naverPay:item-page-button />
								</c:otherwise>
							</c:choose>
						</div>
					</div><!--// bfs_btm -->
				</div><!--// scr_fix_sec -->
			</div><!--// inner -->
		</div><!--// item_tabc_sec -->

		<div class="btm_kind_sec">
			<div class="inner">
				<p class="item_title item-relations">이런 상품 어때요?</p>
				<div class="kind_slide" id="itemRelationsArea">
				</div><!--// kind_slide -->
			</div><!--// inner -->
		</div><!--// btm_kind_sec -->
	</div><!--// view_wrap -->

	<!-- 렌탈 구매하기용 총금액 -->
	<hidden class="rental-send-amount"></hidden>

	<jsp:include page="../include/layer-cart.jsp" />
	<jsp:include page="../include/layer-wishlist.jsp" />


	<page:javascript>
		<!-- 카카오픽셀 설치 [콘텐츠 / 상품 조회 이벤트 전송] -->
		<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
		<script type="text/javascript">
			kakaoPixel('1612698247174901358').pageView();
			kakaoPixel('1612698247174901358').viewContent({
				id: '${item.itemUserCode}'
			});
		</script>

		<!-- 에이스카운터 설치 PC [제품상세페이지 분석코드] -->
		<!-- AceCounter eCommerce (Product_Detail) v8.0 Start -->
		<script language='javascript'>
			var _pd ="${item.itemName}";
			var _ct ="${ctLabel}";
			var _amt ="${item.salePrice}";
		</script>

		<script src="/content/modules/op.social.js"></script>
		<script src="/content/modules/op.imageviewer.js"></script>
		<script src="/content/modules/popup.js"></script>
		<script src="/content/modules/front/item.view.js"></script>
		<script type="text/javascript">

			// 초기 렌탈료 조회  (디폴트 24개월)
			// getMonthRentalPer(24);

			$('.rental-send-amount').on('change', function(){
				alert(1);
			});

			function getMonthRentalPer(rentalVal){
				//alert(rentalVal);
				var rentalAmount;
				$('.monthRentalContPerRentalPg .month').children().removeClass("on");
				$('#rental_per_'+ rentalVal).addClass("on");
				if ($('.rental-send-amount').val() == '') {
					rentalAmount = "${item.salePrice}";
				} else {
					rentalAmount = $('.rental-send-amount').val();
				}

				calculateRentalFee(rentalVal, rentalAmount);
			}

			// ksh 2020-12-03 옵션조합형 선택정보
			var SELECTED_COMBINATION_OPTION_INFOS = [];
			var IS_COMBINATION_OPTION_SOLD_OUT = '${isCombinationOptionSoldOut}';

			var item = {
				'itemId': Number('${item.itemId}'),
				'itemUserCode': '${item.itemUserCode}',
				'imageSrc': '${item.imageSrc}',
				'nonmemberOrderType': '${item.nonmemberOrderType}',
				'price' : Number('${item.exceptUserDiscountPresentPrice}'.replace(/,/g, "")),
				'stockFlag' : '${item.stockFlag}',
				'stockQuantity' : Number('${item.stockQuantity}' == '-1' ? '99999' : '${item.stockQuantity}'),
				'orderMinQuantity': Number('${item.orderMinQuantity}' == '-1' ? '1' : '${item.orderMinQuantity}'),
				'orderMaxQuantity': Number('${item.orderMaxQuantity}' == '-1' ? '99999' : '${item.orderMaxQuantity}'),
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
				isLogin = '${requestContext.userLogin}';

			$(function () {
				try {
					EventLog.item(item.itemUserCode);
				} catch (e) {}

				// 타임세일
				var isSpotItem = '${item.spotItem}';
				if (isSpotItem == 'true') {
					var item_tsTime = "${item.spotFormatDate}";
					$(".spot_time .ts_box").downCount({
						date: item_tsTime,
						offset: +9
					});
				}
			});

			function toCollectionScript(text) {

				if (text == 'cart') {
					<!-- 카카오픽셀 설치 [장바구니 추가 이벤트 전송] -->
					kakaoPixel('1612698247174901358').addToCart({
						id: '${item.itemUserCode}'
					});
					//alert('카카오픽셀 카트 스크립트');

				}

				if (text == 'wishlist') {
					<!-- 카카오픽셀 설치 [장바구니 추가 이벤트 전송] -->
					kakaoPixel('1612698247174901358').addToWishList({
						id: '${item.itemUserCode}'
					});
					//alert('카카오픽셀 관심상품 스크립트');
				}

			}


			/* 월렌탈료 총렌탈료 구하기 */
			function calculateRentalFee(rentalVal, rentalAmount){
				var prodAmt = rentalAmount;
				prodAmt = prodAmt.replace(/,/g, '');
				var rentalPer = rentalVal;
				var storeCode = '${op:property("rentalpay.seller.code")}';
				console.log(prodAmt);
				console.log(rentalPer);
				console.log(storeCode);
				var rentalPerApiUrl = '${op:property("rentalpay.rentalPer.api.url")}';
				$.ajax({
					url : rentalPerApiUrl,
					type : "POST",
					cache : false,
					dataType: "json",
					data : {
						"prodAmt"   : prodAmt,
						"rentalPer" : rentalPer,
						"storeCode" : storeCode
					},
					success : function(data){
						var rentalTotAmt = data.rentalTotAmt;
						var rentalAmt    = data.rentalAmt;
						var resultMsg = data.resultMsg;
						var resultCode    = data.resultCode;
						console.log("총 렌탈료 ==> " + rentalTotAmt);
						console.log("월 렌탈료 ==> " +rentalAmt);
						console.log("결과 코드 ==> " +resultCode);
						console.log("결과 메시지 ==> " +resultMsg);
						$('#rentalTotAmt').val(rentalTotAmt);
						$('#rentalMonthAmt').val(rentalAmt);
						$('#rentalPartnershipAmt').val(rentalAmt - 13000);
						$('#rentalPer').val(rentalPer);

						$('.rental_total_price').text(priceToString(rentalTotAmt));
						$('.rental_price_month').text(priceToString(rentalAmt));
						$('.rental_price_month_partnership').text(priceToString(rentalAmt - 13000));
					}
				});
			}

			function priceToString(price) {
				return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			}

			function toCollectionScript(text) {

				if (text == 'cart') {
					<!-- 카카오픽셀 설치 [장바구니 추가 이벤트 전송] -->
					kakaoPixel('1612698247174901358').addToCart({
						id: '${item.itemUserCode}'
					});
					//alert('카카오픽셀 카트 스크립트')

				}

				if (text == 'wishlist') {
					<!-- 카카오픽셀 설치 [장바구니 추가 이벤트 전송] -->
					kakaoPixel('1612698247174901358').addToWishList({
						id: '${item.itemUserCode}'
					});
					//alert('카카오픽셀 관심상품 스크립트');
				}

			}



			// window.onload = function () {
			// 	cookieJquery();
			// }
			//
			// function cookieJquery () {
			// 	var script1 = document.createElement("script");
			// 	script1.type = "text/javascript";
			// 	script1.charset = "utf-8";
			// 	script1.src = "/content/modules/jquery/jquery.cookie.js";
			// 	document.getElementsByTagName("head")[0].appendChild(script1);
			// }
			// function rentalPg() {
			// 	var script2 = document.createElement("script");
			// 	script2.type = "text/javascript";
			// 	script2.src = "http://211.178.29.124:8082/resources/common/rentalPg.js";
			// 	document.getElementsByTagName("head")[0].appendChild(script2);
			// }
		</script>

		<link rel="stylesheet" type="text/css" href="/content/css/swiper.css">
		<script src="/content/js/swiper.jquery.min.js"></script>
		<script src="/content/js/view.js"></script>
		<script type="text/javascript" src="/content/modules/naverpay/naver.pay.js"></script>

	</page:javascript>
