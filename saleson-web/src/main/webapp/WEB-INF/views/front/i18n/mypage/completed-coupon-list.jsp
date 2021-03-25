<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a>
			<a href="#">마이페이지</a> 
			<a href="#">쇼핑내역</a> 
			<span>주문/배송조회</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<jsp:include page="../include/mypage-user-info.jsp" />
	
	<div id="contents" class="pt0"> 
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
		<div class="contents_inner"> 	   
			<h2>쿠폰</h2>
			<div class="tabs tabs-2">
				<ul> 
					<li><a href="/mypage/download-coupon-list">사용가능 쿠폰 (<span>${op:numberFormat(downloadCouponCount)}</span>)</a></li> 
					<li class="active"><a href="javascript:;" class="on">사용한 쿠폰 (<span>${op:numberFormat(totalCount)}</span>)</a></li> 
				</ul>  
			</div><!--// tabs tabs-2 E-->
			<div class="board_wrap">
	 			<table cellpadding="0" cellspacing="0" class="board-list">
		 			<caption>다운로드 가능한 쿠폰</caption>
		 			<colgroup>
		 				<col style="width:auto;">
		 				<col style="width:170px;">	 				
		 			</colgroup>
		 			<thead>
		 				<tr>
		 					<th scope="col">쿠폰</th>
		 					<th scope="col">상태</th>
		 				</tr>
		 			</thead>
		 			<tbody> 
		 				<c:forEach items="${ list }" var="coupon">  
			 				<tr>
			 					<td class="tleft">
			 						<div class="coupon">
			 							<p>
			 								${ op:numberFormat(coupon.couponPay) }
											<c:choose>
					 							<c:when test="${ coupon.couponPayType eq '2' }">%</c:when>
					 							<c:otherwise>원</c:otherwise>
					 						</c:choose>
			 							</p>
			 						</div>
			 						<div class="coupon_guide">
										<p>${ coupon.couponName }
											<span>${ coupon.couponComment }</span>
										</p>
										<ul class="coupon_date">
											<li>유효기간 
												<c:choose>
													<c:when test="${coupon.couponApplyType == '0'}"><span>제한없음</span></c:when> 
													<c:otherwise><span>${op:date(coupon.couponApplyStartDate)} ~ ${op:date(coupon.couponApplyEndDate)}</span></c:otherwise>
												</c:choose>
											</li>
											<li>제한조건 
												<c:choose>
													<c:when test="${ coupon.couponPayRestriction > 0 }"><span>${op:numberFormat(coupon.couponPayRestriction)}원이상 구매시 사용가능</span></c:when>
													<c:otherwise><span>제한없음</span></c:otherwise>
												</c:choose>
											</li>
										</ul>
									</div>
			 					</td>
			 					<td>
			 						<c:choose>
										<c:when test="${coupon.dataStatusCode == '1'}">${ op:date(coupon.couponUseDate) } ${op:message('M00083')} <!-- 사용 --></c:when>
										<c:otherwise>${op:message('M01049')} <!-- 만료 --></c:otherwise>
									</c:choose>
			 					</td>
			 				</tr>
			 			</c:forEach>
			 			<c:if test="${empty list }">
						<tr> 
		 					<td colspan="2">
		 						<div class="coupon_not">‘사용한 쿠폰이 없습니다.’</div>
		 					</td>
		 				</tr>
					</c:if>
		 			</tbody>
		 		</table><!--//view E-->	 
		 		
		 		<page:pagination-seo /> 
		 	</div><!-- // board_wrap E -->
		</div>
	</div><!-- // contents E -->	
</div><!--// inner E-->	