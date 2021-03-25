<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${breadcrumbs.size() > 0}">
	<div class="goods_product">
		<div class="title">
			<h3>카테고리 랭킹<span></span></h3>
		</div>
		<div class="goods_tab">
			<div class="inner">
				<ul>
					<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="i">
						<li ${i.count == 1 ? 'class="on"' : ''} data-url="${breadcrumb.groupUrl}"><a href="javascript:;"><span>${breadcrumb.groupName}</span></a></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="goods_con">
			<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="i">
				<div class="goods_list" data-url="${breadcrumb.groupUrl}">
					<ul>
						<c:forEach items="${displayItemMap[breadcrumb.groupUrl]}" var="item" varStatus="k">
							<li${item.itemSoldOut ? ' class="sold-out"' : ''}>
								<a href="${item.link}">
									<div class="img">
										<img src="${shop:loadImageBySrc(item.imageSrc, 'L')}" alt="${item.itemName}">
									</div>
									<p class="tit">${item.itemName}</p>
									<div class="price_box">
										<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
											<p class="percentage"><span>${item.discountRate}</span>%</p>
										</c:if>
										<div class="price">
											<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
												<p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
											</c:if>
											<p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
										</div>
									</div>
									<%--<span class="icon_label"><img src="/content/images/icon/label_08.png" alt="label_08"></span>--%>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div><!--// goods_list -->
			</c:forEach>
		</div><!--// goods_con -->
	</div><!--// goods_product -->
</c:if>