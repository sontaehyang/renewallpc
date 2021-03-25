<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="content_top">
	<div class="breadcrumbs">
		<a href="#" class="home"><span class="hide">home</span></a>
		<a href="#">마이페이지</a> 
		<a href="#">쇼핑내역</a> 
		<span>주문/배송조회</span> 
	</div>	<!--// breadcrumbs E--> 
</div><!--// content_top E-->

<jsp:include page="../include/mypage-user-info.jsp" />

<div class="mypage">  
	<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
	<div id="sub_contents_min">   
	 	<h2>쿠폰</h2> 	 	 
		<div class="tabs tabs-2">
			<ul>
				<c:choose>
					<c:when test="${ userCouponParam.dataStatusCode == '0' }">
						<li class="active default"><a href="javascript:;" class="on">사용 가능한 쿠폰 내역</a></li>
						<li><a href="/mypage/coupon?state=1">사용한 쿠폰</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/mypage/coupon?state=0">사용 가능한 쿠폰 내역</a></li>
						<li class="active default"><a href="javascript:;" class="on">사용한 쿠폰</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<div class="table_list_type01 mt30">
			<table cellpadding="0" cellspacing="0" class="esthe-table list coupon_table">
				<caption>사용 가능한 쿠폰내역</caption>
				<colgroup>		 				
					<col style="width:auto;">
					<col style="width:170px;"> 
				</colgroup>
				<thead>
					<tr>
						<th scope="col">쿠폰명</th>
						<th scope="col">적용대상</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ list }" var="coupon">
						<tr>
							<td>
								<div class="my_coupon">
									<div class="coupon_kind"> 
										<ul>
											<li class="hide">할인쿠폰</li>
											<li class="price">
												${ op:numberFormat(coupon.couponPay) }
												<c:choose>
						 							<c:when test="${ coupon.couponPayType eq '2' }">%</c:when>
						 							<c:otherwise>원</c:otherwise>
						 						</c:choose>
											</li> 
										</ul>
										
									</div> 
								</div>
								<div class="myCoupon_guide">
									<p class="coupon_name">${ coupon.couponName }
										<span>${ coupon.couponComment }</span>	
									</p>
									<ul class="coupon_date">
										<li>유효기간 
											<c:choose>
												<c:when test="${coupon.couponApplyType == '0'}">제한없음</c:when> 
												<c:otherwise>${op:date(coupon.couponApplyStartDate)} ~ ${op:date(coupon.couponApplyEndDate)}</c:otherwise>
											</c:choose>
										</li>
										<li>제한조건 
											<span>
												<c:choose>
													<c:when test="${ coupon.couponPayRestriction > 0 }">
														${op:numberFormat(coupon.couponPayRestriction)}원이상 구매시 적용가능
													</c:when>
													<c:otherwise>
														없음
													</c:otherwise>
												</c:choose>
											</span>
										</li>
									</ul>
								</div>
							</td> 
							<td class="tcenter">
								<c:choose>
 									<c:when test="${ coupon.couponTargetItemType == '1' }">
 										전체 상품
 									</c:when>
 									<c:otherwise>
 										<button type="button" class="btn btn-default btn-s" onclick="couponAppliesTo('${ item.coupon.couponId }', '2')">적용상품보기</button>
 									</c:otherwise>
 								</c:choose>
							</td> 
						</tr>
					</c:forEach>
					<c:if test="${empty list }">
						<tr> 
		 					<td colspan="2">
		 						<div class="coupon_not">‘적용 가능한 쿠폰이 없습니다.’</div>
		 					</td>
		 				</tr>
					</c:if>
				</tbody>
			</table>  
		</div>
		<page:pagination-seo /> 		
	 	 	 		
	</div><!--// sub_contents_min E-->
</div><!--// mypage E-->

<page:javascript>
<script type="text/javascript">
$(function(){
	// 메뉴 활성화
	$('#lnb_coupon').addClass("on");
});

function couponAppliesTo(couponId, couponTerms) {
	var popupViewName = 'coupon-applies-to' + (couponTerms == '2' ? '-item' : '-category');
	Common.popup('/coupon/applies-to/' + couponId + '/coupon-user', popupViewName, 898, 740);
}
</script>
</page:javascript>