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

<c:if test="${!empty newItems}">
	<div class="main_new mainSlider">
		<h2>
			<img src="/content/images/common/main_new.jpg" alt="New! 신상품">
			<a href="/event/new" class="btn_more" title="New! 신상품 더보기">더보기</a>
		</h2>	
		<div class="op-bxslider main_new_inner"> 
			<c:forEach items="${newItems}" var="item" varStatus="i">
				<div class="slide"> 
					<a href="${item.link}">   
						<span class="thumbnail"><img src="${ shop:loadImageBySrc(item.imageSrc, 'S') }" width="90" height="90" alt="item"></span>
						<div class="cont">
							<p class="name">${item.itemName}</p>
							<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
								<span class="sale">${item.discountRate}<span>%</span></span>
							</c:if>
							<p class="price">
								<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
									<span class="before_price">${op:numberFormat(item.listPrice)}원</span>
								</c:if> 
								<span class="sale_price">${op:numberFormat(item.exceptUserDiscountPresentPrice)}원</span> 
							</p>
						</div> 
					</a>
				</div> 
			</c:forEach>  
		</div>
	</div>
</c:if>
	