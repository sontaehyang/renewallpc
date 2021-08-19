<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${!empty itemRelations}">
	<div class="owl-carousel owl-theme bki_list">
		<c:forEach var="list" items="${itemRelations}">
			<div class="item">
				<div class="imgs${list.item.itemSoldOut ? ' sold-out' : ''}">
					<a href="${list.item.link}" class="img"><img src="${shop:loadImageBySrc(list.item.imageSrc, 'XS')}" alt="${list.item.itemName}"></a>
					<p class="ov_btns">
						<a href="${list.item.link}" class="blank_btn" target="_blank">새창 열기</a>
						<c:if test="${!list.item.itemSoldOut}">
							<a href="javascript:void(0);" onclick="Shop.addToWishList('${list.item.itemId}', '${list.item.orderMinQuantity}', '${requestContext.userLogin}', '${list.item.link}', '0', '${list.item.itemType}'); Shop.toCollectionScript('wishlist' , '${item.itemCode}');" class="wish_btn">관심상품 담기</a>
							<c:if test="${list.item.itemOptionFlag == 'N'}">
								<a href="javascript:void(0);" onclick="Shop.addToCart('${list.item.itemId}', '${list.item.orderMinQuantity}', '${list.item.nonmemberOrderType}', '${requestContext.userLogin}', '${list.item.link}','${list.item.itemType}'); Shop.toCollectionScript('cart' , '${item.itemCode}');" class="cart_btn">장바구니 담기</a>
							</c:if>
						</c:if>
					</p><!--// ov_btns -->
				</div><!--// imgs -->
				<a href="${list.item.link}" class="tit">${list.item.itemName}</a>
				<dl class="price">
					<dt>
						<b>${op:numberFormat(list.item.exceptUserDiscountPresentPrice)}</b>원
						<c:if test="${list.item.totalDiscountAmount > 0 && list.item.discountRate > 0}">
							<span>${op:numberFormat(list.item.listPrice)}</span>
						</c:if>
					</dt>
					<c:if test="${list.item.totalDiscountAmount > 0 && list.item.discountRate > 0}">
						<dd>${list.item.discountRate}</dd>
					</c:if>
				</dl>
			</div><!--// item -->
		</c:forEach>
	</div><!--// bki_list -->
</c:if>


