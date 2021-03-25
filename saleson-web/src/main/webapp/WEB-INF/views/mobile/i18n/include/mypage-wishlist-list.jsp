<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${wishlists}" var="wishlist">
	<c:set var="isOptionSoldOut" value="N" />
	<c:set var="arrayRequiredOptions" value="" />
	<c:forEach items="${wishlist.item.itemOptionGroups}" var="itemOptionGroup" varStatus="i">
		<c:if test="${!empty itemOptionGroup.optionTitle}">
			<c:forEach items="${itemOptionGroup.itemOptions}" var="itemOption" varStatus="j">
				<c:if test="${itemOption.stockQuantity == 0}">
					<c:set var="isOptionSoldOut" value="Y" />
				</c:if>
				
				<c:choose>
					<c:when test="${arrayRequiredOptions == ''}">
						<c:set var="arrayRequiredOptions" value="${itemOption.itemOptionId}^^^${itemOption.optionName2}^^^${itemOption.price}/${itemOption.priceNonmember}" />
					</c:when>
					<c:otherwise>
						<c:set var="arrayRequiredOptions">${arrayRequiredOptions}@${itemOption.itemOptionId}^^^${itemOption.optionName2}^^^${itemOption.price}/${itemOption.priceNonmember}</c:set>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>
		</c:if>
	</c:forEach>
	
	<li>
		<input type="hidden" name="itemType" id="itemType_${wishlist.wishlistId}" value="${wishlist.item.itemType }"/> 
 		<span class="check"><input type="checkbox" name="id" id="wishlist-${wishlist.wishlistId}" value="${wishlist.wishlistId}"/></span> 
		
		<div class="active_product">
			<div class="active_img">
				<%-- <img src="${wishlist.item.imageSrc}" alt="${wishlist.item.itemName}"> --%>
				<img src="${shop:loadImageBySrc(wishlist.item.imageSrc, 'XS')}" alt="${wishlist.item.itemName}">
			</div>
			
			<div class="active_name">
					<!-- <div class="cont"> -->
				<p class="tit">${wishlist.item.itemName}</p>
				<p class="option">
				<c:choose>
					<c:when test='${wishlist.item.itemSoldOutFlag=="N"}'>
							${op:message('M01458')} <!-- 재고 있음 -->
						</c:when>
						<c:otherwise>
							${op:message('M01495')} <!-- 재고 없음 -->
						</c:otherwise>
				</c:choose>
				</p>
				<%--<p class="option">옵션있음</p> --%>
				<p class="price">
					<c:if test="${wishlist.item.listPrice != 0}">
						<span class="discount">${op:numberFormat(wishlist.item.listPrice)}원</span>
					</c:if>
					${op:numberFormat(wishlist.item.presentPrice)}원
				</p>
			</div>
		</div>
			
		<div class="active_btn cf">
		
			
				<c:set var="action">addToCart('${wishlist.wishlistId}')</c:set>
				
				<c:if test="${wishlist.item.stockQuantity == 0 || isOptionSoldOut == 'Y'}">
					<c:set var="action">alert('상품의 재고가 없습니다.')</c:set>
				</c:if>   
				
				<%--<button type="button" onclick="${action}" class="btn_st3 bd_blue">장바구니</button> --%>
				<button type="button" class="btn_st3 s_small" onclick="deleteSelectedItem('${wishlist.wishlistId}');" >삭제</button>
				<%--
				<c:if test="${wishlist.item.itemOptionFlag == 'Y'}">
					<input type="hidden" name="arrayItemId"
						value="${wishlist.item.itemId}" />
					<input type="hidden" name="arrayRequiredOptions"
						value="${arrayRequiredOptions}" />
				</c:if>
				
				<c:if test="${wishlist.item.itemOptionFlag != 'Y'}">
					<input type="hidden" name="arrayItemId"
						value="${wishlist.item.itemId}" />
					<input type="hidden" name="arrayRequiredOptions" value="" />
				</c:if>
 				--%>
		</div>	
		
			<%-- <div class="count number">
				<a href="" class="down"><img src="/content/mobile/images/common/btn_minus.png"></a> 
				<input type="text" name="arrayQuantitys" maxlength="3" class="quantity ${wishlist.item.stockQuantity == 0 || isOptionSoldOut == 'Y' ? 'disabled' : ''} _number" ${wishlist.item.stockQuantity == 0 || isOptionSoldOut == 'Y' ? 'disabled="disabled"' : ''} value="${wishlist.item.stockQuantity == 0 || isOptionSoldOut == 'Y' ? '0' : '1'}">
				<a href="" class="up"><img src="/content/mobile/images/common/btn_plus.png"></a> 
			</div> 
			<div class="btns">
				<c:set var="action">addToCart('${wishlist.wishlistId}')</c:set>
				<c:if test="${wishlist.item.stockQuantity == 0 || isOptionSoldOut == 'Y'}">
					<c:set var="action">alert('상품의 재고가 없습니다.')</c:set>
				</c:if>   
				<button type="button" onclick="${action}" class="cart">장바구니</button>
				<button type="button" class="del delete_item">삭제</button>
			</div> 
		 
		
		<c:if test="${wishlist.item.itemOptionFlag == 'Y'}">
			<input type="hidden" name="arrayItemId" value="${wishlist.item.itemId}" />			
			<input type="hidden" name="arrayRequiredOptions" value="${arrayRequiredOptions}" />
		</c:if>
		<c:if test="${wishlist.item.itemOptionFlag != 'Y'}">
			<input type="hidden" name="arrayItemId" value="${wishlist.item.itemId}" />			
				<input type="hidden" name="arrayRequiredOptions" value="" />
		</c:if> --%>
		
	</li> 
</c:forEach>