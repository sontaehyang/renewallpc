<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

		<c:forEach items="${items}" var="item" varStatus="i">
			<li>
				<c:choose>
					<c:when test="${itemParam.listType == 'a'}">
						<div class="imgs${item.itemSoldOut ? ' sold-out' : ''}">
							<a href="${item.link}" ${item.noFollow} class="img">
								<img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'S')}"  alt="${item.itemName}" class="thumbnail_photo">
							</a>
							<p class="ov_btns">
								<a href="${item.link}" ${item.noFollow} class="blank_btn" target="_blank">새창 열기</a>
								<c:if test="${!item.itemSoldOut}">
									<c:if test="${item.itemOptionFlag != 'Y'}">
										<a href="javascript:Shop.addToCart('${item.itemId}', '${item.orderMinQuantity}', '${item.nonmemberOrderType}', '${requestContext.userLogin}', '${requestContext.requestUri}','${item.itemType}');" class="cart_btn">장바구니 담기</a>
									</c:if>
									<a href="javascript:Shop.addToWishList('${item.itemId}', '${item.orderMinQuantity}', '${requestContext.userLogin}', '${requestContext.requestUri}','0','${item.itemType}');" class="wish_btn">관심상품 담기</a>
								</c:if>
							</p>
						</div>
						<a href="${item.link}" ${item.noFollow} class="tit">${item.itemName}</a>
						<dl class="price">
							<dt>
								<b>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</b>원
								<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<span>${op:numberFormat(item.listPrice)}</span>
								</c:if>
							</dt>
							<c:if test="${item.discountRate > 0}">
								<dd>${item.discountRate}</dd>
							</c:if>
						</dl>
						<div class="item-label-02">
							<c:forEach items="${item.itemLabels}" var="label">
								<span><img src="${label.imageSrc}" alt="${label.description}"></span>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<a href="${item.link}" ${item.noFollow}>
							<div class="thumbnail_wrap">
								<span class="thumbnail${item.itemSoldOut ? ' sold-out' : ''}">
									<img src="${shop:loadImage(item.itemUserCode, item.itemImage, 'S')}"  alt="${item.itemName}" class="thumbnail_photo">
								</span>
							</div>
							<div class="item-info">
								<p class="name">${item.itemName} </p>
								<div class="price-zone">
									<c:if test="${item.discountRate > 0}">
										<p class="sale">
											${item.discountRate}<span>%</span>
										</p>
									</c:if>
									<p class="price <c:if test="${item.discountRate == 0}">-nobp</c:if>">
										<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
											<span class="before_price">${op:numberFormat(item.listPrice)}<span>원</span></span>
										</c:if>
										<span class="sale_price">${op:numberFormat(item.exceptUserDiscountPresentPrice)}<span>원</span></span>
									</p>
								</div>
							</div>
						</a>
						<div class="item-label-02">
							<c:forEach items="${item.itemLabels}" var="label">
								<span><img src="${label.imageSrc}" alt="${label.description}"></span>
							</c:forEach>
						</div>
						<div class="item-btns">
							<c:if test="${!item.itemSoldOut}">
								<c:if test="${item.itemOptionFlag != 'Y'}">
									<button type="button" class="btn btn-s btn-submit" title="장바구니" onclick="Shop.addToCart('${item.itemId}', '${item.orderMinQuantity}', '${item.nonmemberOrderType}', '${requestContext.userLogin}', '${requestContext.requestUri}','${item.itemType}')"><strong>장바구니</strong></button>
								</c:if>
								<button type="button" class="btn btn-s btn-wish" title="찜하기" onclick="Shop.addToWishList('${item.itemId}', '${item.orderMinQuantity}', '${requestContext.userLogin}', '${requestContext.requestUri}','0','${item.itemType}')"><strong>관심상품</strong></button>
							</c:if>
							<%--<div class="review">리뷰<a href="#">(${item.reviewCount})</a></div>--%>
						</div>
					</c:otherwise>
				</c:choose>
			</li>
		</c:forEach>