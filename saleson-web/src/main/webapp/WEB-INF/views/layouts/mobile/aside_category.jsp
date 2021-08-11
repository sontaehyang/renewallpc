<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="side_bot">
	<div class="gnb">
		<div class="title">
			<h2>카테고리</h2>
		</div>
		<ul>
			<c:forEach items="${shopContext.shopCategoryGroups}" var="group" varStatus="i">
				<c:set var="categoryDisplay" value="Y" />
				<c:if test="${categoryDisplay == 'Y'}">
					<li>
						<a href="#" class="oneDepth">${group.name}<span class="arr"></span></a>
						<div class="twoDepthBox">
							<ul>
								<c:forEach items="${group.categories}" var="category1">
									<li>
										<a href="/m/categories/index/${category1.url}" class="threeDepth_btn">
											${category1.name}
											<c:if test="${category1.childCategories.size() > 0}">
												<span class="arr" data-category-url="op-one-dept-view-${category1.url}"></span>
											</c:if>
										</a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	<div class="viewed op-today-items">
		<div class="title">
			<h2>오늘 본 상품</h2>
			<!-- a href="javascript:alert('준비중입니다.');" class="more_btn">더보기</a -->
		</div>
		<div class="viewed_list">
			<ul >
				<c:forEach items="${shopContext.todayItems}" var="item" varStatus="i">
					<c:if test="${i.index < 3}">
						<li>
							<div class="img">
								<a href="${item.link}"><img src="${shop:loadImageBySrc(item.imageSrc, 'XS')}" alt="${item.itemName}"></a>
							</div>
							<div class="txt">
								<a href="${item.link}">
									<p class="name">${item.itemName}</p>
									<p class="price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
								</a>
							</div>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div><!-- //viewed -->

	<div class="service">
		<div class="title">
			<h2>주요서비스</h2>
		</div>
		<div class="service_list">
			<ul>
				<li class="none"><a href="/m/event/spot">타임세일</a></li>
				<li><a href="/m/pages/big-deal">득템의 기쁨</a></li>
				<li class="none"><a href="/m/event">이벤트</a></li>
				<li><a href="/m/notice/list">고객센터</a></li>
				<li class="none"><a href="/m/faq">FAQ</a></li>
				<c:if test="${requestContext.user.userDetail.groupCode == 'DEALER1' }">
					<li class="none"><a href="/m/pages/dealer_shop1" class="hdt_ds">딜러샵</a></li>
				</c:if>
				<c:if test="${requestContext.user.userDetail.groupCode == 'DEALER2' }">
					<li class="none"><a href="/m/pages/dealer_shop2" class="hdt_ds">딜러샵</a></li>
				</c:if>
				<c:if test="${requestContext.user.userDetail.groupCode == 'DEALER3' }">
					<li class="none"><a href="/m/pages/dealer_shop3" class="hdt_ds">딜러샵</a></li>
				</c:if>
				<c:if test="${(requestContext.user.userDetail.groupCode == 'DEALER1' || requestContext.user.userDetail.groupCode == 'DEALER2' || requestContext.user.userDetail.groupCode == 'DEALER3' )
							&& requestContext.user.userDetail.priceTableView == 'Y'}">
					<li class="none"><a href="/m/mypage/chart" class="hdt_ct">매입단가표</a></li>
				</c:if>
			</ul>
		</div>
	</div><!-- //service -->
</div>

<c:forEach items="${shopContext.shopCategoryGroups}" var="group" varStatus="j">
	<c:if test="${!empty group.categories}">
		<c:forEach items="${group.categories}" var="category1">	
			<div class="threeDepthPop op-one-dept-view-${category1.url}"> 
				<div class="title">
					<h3>${category1.name}</h3>
					<button type="button" class="close_btn ir_pm">닫기</button>
				</div>
				<ul>
					<c:forEach items="${category1.childCategories}" var="category2" varStatus="k">
						<li data-category-url="${category2.url}" data-category-level="2" class="op-two-depth-${category2.url} ${not empty category2.childCategories ? 'have' : ''}">
							<div class="threeDepth_tit">
								<a href="${category2.link}" class="threeDepth">${category2.name}</a>
								<a href="javascript:;" class="link">상품보기</a>
							</div>
							
							<c:if test="${not empty category2.childCategories}">
								<div class="fourDepthBox op-two-child-category-${category2.url}">
									<ul> 
										<c:forEach items="${category2.childCategories}" var="category3" varStatus="k2">
											<li data-category-url="${category3.url}" 
												data-category-two-depth-url="${category2.url}" 
												data-category-level="3" ${not empty category3.childCategories ? 'class="have"' : ''}>
												<div class="fourDepth_tit">
													<a href="${category3.link}" class="fourDepth">${category3.name}</a>
													<a href="javascript:;" class="link">상품보기</a>
												</div>
												<c:if test="${not empty category3.childCategories}">
													<div class="fiveDepthBox op-three-child-category-${category3.url}">
														<ul>
															<c:forEach items="${category3.childCategories}" var="category4" varStatus="k3">
																<li data-category-url="${category4.url}" 
																	data-category-three-depth-url="${category3.url}" 
																	data-category-two-depth-url="${category2.url}"
																	data-category-level="4">
																	<span onclick="location.href='${category4.url}'">${category4.name}</span>
																</li>
															</c:forEach>
														</ul>
													</div>
												</c:if>
											</li>
										</c:forEach>
									</ul>
								</div>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:forEach>
	</c:if>
</c:forEach>
