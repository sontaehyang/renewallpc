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

<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="i">
	<div class="cate_prd_sec">
		<dl class="cps_cate">
			<dt>${breadcrumb.groupName}<a href="/categories/index/${breadcrumb.categoryUrl1}" class="btn_more">MORE ></a></dt>
			<dd>
				<c:forEach items="${breadcrumb.breadcrumbCategories}" var="category1" varStatus="j">
					<a href="#">${category1.categoryName}</a>
				</c:forEach>
			</dd>
		</dl><!--// cps_cate -->
		<div class="cps_bans">
			<div class="owl-carousel owl-theme ms04_list">
				<c:forEach items="${displayImageMap[breadcrumb.groupUrl]}" var="list" varStatus="k">
					<a href="${list.displayUrl == '' ? 'javascript:void(0);' : list.displayUrl}" class="item"><img src="${list.displayImageSrc}" alt="${list.displayContent}"></a>
				</c:forEach>
			</div><!--// mv_list -->
		</div><!--// cps_bans -->
		<div class="cps_prd">
			<c:forEach items="${breadcrumb.breadcrumbCategories}" var="category1" varStatus="j">
				<ul class="prd_list">
					<c:forEach items="${displayItemMap[category1.categoryId]}" var="item" varStatus="k">
						<li>
							<div class="imgs${item.itemSoldOut ? ' sold-out' : ''}">
								<a href="${item.link}" class="img"><img src="${shop:loadImageBySrc(item.imageSrc, 'XS')}" alt="${item.itemName}"></a>
								<p class="ov_btns">
									<a href="${item.link}" class="blank_btn" target="_blank">새창 열기</a>
									<c:if test="${!item.itemSoldOut}">
										<a href="javascript:void(0);" onclick="Shop.addToWishList('${item.itemId}', '${item.orderMinQuantity}', '${requestContext.userLogin}', '${item.link}', '0', '${item.itemType}')" class="wish_btn">관심상품 담기</a>
										<c:if test="${item.itemOptionFlag == 'N'}">
											<a href="javascript:void(0);" onclick="Shop.addToCart('${item.itemId}', '${item.orderMinQuantity}', '${item.nonmemberOrderType}', '${requestContext.userLogin}', '${item.link}','${item.itemType}')" class="cart_btn">장바구니 담기</a>
										</c:if>
									</c:if>
								</p><!--// ov_btns -->
							</div>
							<a href="${item.link}" class="tit">${item.itemName}</a>
							<dl class="btm">
								<dt><b>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</b>원</dt>
								<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<dd>${item.discountRate}</dd>
								</c:if>
							</dl>
						</li>
					</c:forEach>
				</ul><!--// prd_list -->
			</c:forEach>
		</div><!--// cps_prd -->
	</div><!--// cate_prd_sec -->
</c:forEach>