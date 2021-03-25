<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${items}" var="item" varStatus="i">

    <li${item.itemSoldOut ? ' class="sold-out"' : ''}>

        <a href="${item.link}">
            <div class="product">
                <div class="product_img">
                    <img src="${shop:loadImageBySrc(item.imageSrc, 'L')}" alt="제품이미지">
                </div>

                <div class="product_info">
                    <div class="label_area">
                        <c:forEach items="${item.itemLabels}" var="label">
                            <span><img src="${label.imageSrc}" alt="${label.description}"></span>
                        </c:forEach>
                    </div>
                    <p class="tit">${item.itemName}</p>
                    <div class="price_box">
                        <c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
                            <p class="percentage">${item.discountRate}<span>%</span></p>
                        </c:if>
                        <div class="price <c:if test="${item.discountRate == 0}">-nobp</c:if>">
                            <c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
                                <p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
                            </c:if>
                            <p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
                        </div>
                    </div>
                </div>
            </div>
        </a>
    </li>
</c:forEach>