<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>




<c:forEach items="${ list }" var="coupon">

	<li>
		<div class="img">
			<p class="coupon coupon_blue">
				<span>${ op:numberFormat(coupon.couponPay) }</span>
				<c:choose>
					<c:when test="${coupon.couponPayType eq '2'}">%</c:when>
					<c:otherwise>원</c:otherwise>
				</c:choose>
			</p>
		</div>

		<div class="txt">
			<p class="coupon_desc">
				<c:choose>
					<c:when test="${ coupon.couponPayRestriction > 0 }">
						${op:numberFormat(coupon.couponPayRestriction)}원 이상 상품 사용가능
					</c:when>
					<c:otherwise>
						제한금액 조건 없음
					</c:otherwise>
				</c:choose>
			</p>

			<p class="coupon_name">${ coupon.couponName }</p>

			<p class="coupon_date">
				<c:choose>
					<c:when test="${coupon.couponApplyType == '0'}">제한없음</c:when> 
					<c:otherwise>${op:date(coupon.couponApplyStartDate)} ~ ${op:date(coupon.couponApplyEndDate)}</c:otherwise>
				</c:choose>
			</p>

			<c:if test="${coupon.couponTargetItemType == '2' }">
				<a href="/m/coupon/applies-to/${coupon.couponId}/coupon" class="part">적용상품보기</a>
			</c:if>
			<c:if test="${coupon.couponTargetItemType == '1'}">
				<span class="all">전체상품</span>
			</c:if>

		</div>

	</li>

</c:forEach>
