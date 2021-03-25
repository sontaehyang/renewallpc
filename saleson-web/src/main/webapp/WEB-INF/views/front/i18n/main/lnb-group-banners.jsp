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

<div>
	<c:forEach items="${shopCategoryGroups}" var="group" varStatus="i">
		<div class="op-main-lnb-group-${group.url}">

			<c:set var="count">0</c:set>
			<c:forEach items="${groupBanners}" var="lnbBannerTeam" varStatus="i">
				<c:forEach items="${lnbBannerTeam.categoriesGroupList}" var="lnbBannerGroup" varStatus="j">
					<c:if test="${lnbBannerGroup.code == group.url}">
						<c:forEach items="${lnbBannerGroup.groupBanners}" var="banner">
							<c:set var="count">${count + 1}</c:set>
						</c:forEach>
						<c:forEach items="${groupBannerItemsByGroup[group.url]}" var="item">
							<c:set var="count">${count + 1}</c:set>
						</c:forEach>
					</c:if>
				</c:forEach>
			</c:forEach>

			<c:if test="${count > 0}">
				<div class="lnb_banner">
					<ul>
						<c:forEach items="${groupBanners}" var="lnbBannerTeam" varStatus="i">
							<c:set var="itemCountByGroup" value="0" />
							<c:forEach items="${lnbBannerTeam.categoriesGroupList}" var="lnbBannerGroup" varStatus="j">
								<c:if test="${lnbBannerGroup.code == group.url}">
									<c:forEach items="${lnbBannerGroup.groupBanners}" var="banner">
										<li class="ban_roll"><a href="${banner.linkUrl}"><img src="${banner.imageSrc}" title="${banner.title}" /></a></li>
										<c:set var="itemCountByGroup">${itemCountByGroup + 1}</c:set>
									</c:forEach>

									<c:forEach items="${groupBannerItemsByGroup[group.url]}" var="item">
										<c:if test="${itemCountByGroup < 2}">
											<li class="item_roll">
												<a href="${item.link}">
													<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
														<span class="label">${item.discountRate}<span>%</span></span>
													</c:if>
													<p class="item">
														<img src="/thumbnail?src=${item.imageSrc}&size=160" alt="${item.itemName}">
													</p>
													<div>
														<p class="name">${item.itemName}</p>
														<div class="price">
															<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
																<p class="item_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
															</c:if>
															<p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
														</div>
													</div>
												</a>
											</li>
											<c:set var="itemCountByGroup">${itemCountByGroup + 1}</c:set>
										</c:if>
									</c:forEach>

									<c:forEach items="${bestItemsByTeam[team.url]}" var="item">
										<c:if test="${itemCountByGroup < 2}">
											<li class="item_roll">
												<a href="${item.link}">
													<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
														<span class="label">${item.discountRate}<span>%</span></span>
													</c:if>
													<p class="item">
														<img src="/thumbnail?src=${item.imageSrc}&size=160" alt="${item.itemName}">
													</p>
													<div>
														<p class="name">${item.itemName}</p>
														<div class="price">
															<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
																<p class="item_price"><span>${op:numberFormat(item.listPrice)}</span></p>
															</c:if>
															<p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
														</div>
													</div>
												</a>
											</li>
											<c:set var="itemCountByGroup">${itemCountByGroup + 1}</c:set>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</div>
	</c:forEach>
</div>