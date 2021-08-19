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
						<a href="${item.link}" ${item.noFollow}>
							<div class="thumbnail_wrap">
								<div class="item-label-01">
									<span class="best">${i.index + 1 }</span>
								</div>
								<c:choose>
									<c:when test="${(item.stockQuantity == 0 && item.soldOut == '1') or item.stockQuantity == 0 && item.soldOut == '2'}">
										<span class="thumbnail soldOut"><img src="/content/images/common/sold_out.jpg" alt="sold out" class="thumbnail_photo"></span>
									</c:when>
									<c:otherwise>
										<span class="thumbnail">
											<img src="${ shop:loadImage(item.itemUserCode, item.itemImage, 'S') }" alt="thumbnail" class="thumbnail_photo">
										</span>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="item-info">
								<p class="name">${item.itemName} </p>
								<div class="price-zone">
									<c:if test="${item.discountRate > 0}">
										<p class="sale">
											${item.discountRate }<span>%</span>
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
						<%--
						<div class="review">리뷰<a href="#">(${item.reviewCount})</a></div>
						
						<div class="item-label-02">
							<c:forEach items="${item.itemLabels}" var="label">
								<span><img src="${label.imageSrc}" alt="${label.description}"></span>
							</c:forEach>
						</div>
						--%>
						<div class="item-btns">
							<c:choose>
								<c:when test="${item.itemOptionFlag != 'Y' && (item.stockFlag == 'N' || (item.stockFlag == 'Y' and item.stockQuantity > 0) )}">
		 							<button type="button" class="btn btn-s btn-submit" title="장바구니" onclick="Shop.addToCart('${item.itemId}', '${item.orderMinQuantity}', '${item.nonmemberOrderType}', '${requestContext.userLogin}', '${requestContext.requestUri}','${item.itemType}'); Shop.toCollectionScript('cart' , '${item.itemCode}');"><strong>장바구니</strong></button>
									<button type="button" class="btn btn-s btn-wish" title="찜하기" onclick="Shop.addToWishList('${item.itemId}', '${item.orderMinQuantity}', '${requestContext.userLogin}', '${requestContext.requestUri}','0','${item.itemType}'); Shop.toCollectionScript('wishlist' , '${item.itemCode}');"><strong>찜하기</strong></button>
		 						</c:when>
		 						<c:when test="${item.stockQuantity == 0 && item.soldOut == '1' && item.itemOptionFlag != 'Y'}">
		 							<%-- 
		 							<button type="button" class="btn btn-skyblue btn-min" onclick="Shop.applyForArrival('${item.itemId}', '${requestContext.userLogin}', '${requestContext.requestUri}')"><img src="/content/images/btn/btn_icon_mail.png" alt=""> 入荷通知申し込み</button>
		 						 	--%>
		 						</c:when>
		 						<c:otherwise>
		 							<%--  상세화면으로 
		 							<button type="button" class="btn btn_detail btn-min" onclick="Shop.goItemDetails('${item.itemUserCode}', '${item.nonmemberOrderType}', '${requestContext.userLogin}')"><img src="/content/images/btn/btn_icon_view.png" alt=""> ${op:message('M01044')}</button>  
		 							 --%>
		 						</c:otherwise>
		 					</c:choose> 
						</div>
					</li>
				</c:forEach>