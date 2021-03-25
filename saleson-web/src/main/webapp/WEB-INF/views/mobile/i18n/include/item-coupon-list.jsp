<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>

<c:forEach items="${coupons}" var="coupon">
	<li>
		<div class="img">
			<p class="coupon coupon_blue"><span>${op:numberFormat(coupon.couponPay)}</span>${coupon.couponPayType == '1' ? '원' : '%'}</p> <!-- %, 원 개발 -->
			<a href="javascript:downloadCoupon(${coupon.couponId})" class="btn_st6"><span>쿠폰다운</span></a>
		</div>
		<div class="txt">
			<p class="coupon_desc">
				<c:choose>
					<c:when test="${coupon.couponTargetItemType == '1'}">
						${op:message('M00494')} <!-- 사용 가능 상품 --> : ${op:message('M00498')} <!-- 모든 상품 -->
					</c:when>
					<c:when test="${coupon.couponTargetItemType == '2'}">
						${op:message('M00494')} <!-- 사용 가능 상품 --> : ${item.itemName} (${item.itemUserCode})
					</c:when>
				</c:choose>
			</p>
			<p class="coupon_name">${coupon.couponName}</p>
			<p class="coupon_date">
				<c:choose>
					<c:when test="${coupon.couponApplyType == '0'}">제한없음</c:when>
					<c:when test="${coupon.couponApplyType == '2' }">다운로드 시점부터 <strong>${coupon.couponApplyDay}일</strong> 후까지</c:when>
					<c:otherwise>${op:date(coupon.couponApplyStartDate)} ~ ${op:date(coupon.couponApplyEndDate)}</c:otherwise>
				</c:choose>
			</p>
		</div>
	</li>
</c:forEach>