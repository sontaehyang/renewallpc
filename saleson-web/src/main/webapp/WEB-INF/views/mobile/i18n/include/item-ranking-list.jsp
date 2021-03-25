<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

	<c:forEach items="${rankingList}" var="item" varStatus="i">
		<li>
			<a href="#">
				<div class="product">
					<c:if test="${i.count < 4 }">
						<div class="product_rank hit">
							<p>${i.count}</p><span>BEST</span>
						</div>
					</c:if>
					<c:if test="${i.count > 3 }">
						<div class="product_rank">
							<p>${i.count}</p>
						</div>
					</c:if>
					<div class="product_img">
						<img src="/content/mobile/images/common/product_none.jpg" alt="제품이미지">
					</div>
					<div class="product_info">
						<div class="label_area">
							<span class="label_new"><img src="/content/mobile/images/common/label_new.gif" alt="신상품"></span>
							<span class="label_best"><img src="/content/mobile/images/common/label_best.gif" alt="BEST상품"></span>
						</div>
						<p class="tit">${item.itemName}</p>
						<div class="price_box">
							<span class="percentage">${item.discountRate}%</span>
							<div class="price">
								<p class="per_price"><span>${op:numberFormat(item.itemPrice)}</span>원</p>
								<p class="sale_price"><span>${item.price}</span>원</p>
							</div>
						</div>
					</div>
				</div>
				<div class="review">
					<p>리뷰<span>(142.643)</span></p>
					<span class="soldout_ico"><img src="/content/mobile/images/common/product_soldout_ico.png" alt="SOLD OUT"></span>
				</div>
			</a>
		</li>
	</c:forEach>