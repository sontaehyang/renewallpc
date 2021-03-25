<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${coupon.items}" var="item">
	<li>
		<div class="item">

			<a href="javascript:goUrl('${ item.link }')">
				<div class="order_img">
					<img src="${item.imageSrc}" alt="제품이미지">
				</div>
				<div class="order_name">
					<p class="tit">${ item.itemName }</p>
				</div>
				<div class="order_price">
					<p>
						<span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원
					</p>
				</div>
			</a>
		</div>
	</li>
</c:forEach>