<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${not empty itemOtherList}">
	<c:set var="relationItemCount" value="0" />
	<c:forEach items="${itemOtherList}" var="itemOther" varStatus="i">
		<c:if test="${!empty itemOther.item.itemUserCode}">
			<c:set var="relationItemCount">${relationItemCount + 1}</c:set>
			<div class="slide">
				<a href="${itemOther.item.link}" ${itemOther.item.noFollow}>
					<span class="thumbnail"><img src="${ shop:loadImage(itemImage.itemUserCode, itemImage.imageName, 'S') }" alt="${itemOther.item.itemName}"></span>
					<img src="${shop:loadImageBySrc(itemRelation.item.imageSrc, 'S')}" alt="${itemRelation.item.itemName}">
					<div class="cont">
						<p class="name">${itemOther.item.itemName}</p>
						<p class="price">
							<span class="before_price">${op:numberFormat(itemOther.item.listPrice)}원</span>
							<span class="sale_price">${itemOther.item.exceptUserDiscountPresentPrice}원</span>
						</p>
					</div>
				</a>
			</div> <!-- // slide -->
		</c:if>
	</c:forEach>
</c:if>

