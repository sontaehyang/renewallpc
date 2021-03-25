<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<!-- 본문 -->
	<!-- 팝업사이즈 680*572-->
	<div class="popup_wrap">
		<h1 class="popup_title">${op:message('M00490')}</h1> <!-- 쿠폰 다운로드 -->
		<div class="popup_contents">
			<c:if test="${not empty coupons}">
				<div class="popup_review">
					<div class="board_wrap">
						<table cellpadding="0" cellspacing="0" class="board-list">
							<caption>쿠폰 다운로드</caption>
							<colgroup>
								<col style="width:auto;">
								<col style="width:120px;">
								<col style="width:220px;">
								<col style="width:120px;">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">할인 쿠폰명</th>
									<th scope="col">할인금액</th>
									<th scope="col">유효기간</th>
									<th scope="col">상태</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${coupons}" var="coupon">
								<tr>
									<td>${coupon.couponName}</td>
									<td>
										<span class="coupons">
											<c:choose>
												<c:when test="${!empty couponPayRestriction}">
													${op:numberFormat(coupon.couponPay)}${coupon.couponPayType == '1' ? '원' : '%'}
													${op:numberFormat(couponPayRestriction)}원 ${op:message('M00489')} <!-- 이상 구매 시 사용 -->
												</c:when>
												<c:otherwise>
													${op:numberFormat(coupon.couponPay)}${coupon.couponPayType == '1' ? '원' : '%'}
												</c:otherwise>
											</c:choose>
										</span>
									</td>
									<td>
										<span class="date">
										<c:choose>
											<c:when test="${coupon.couponApplyType == '0'}">제한없음</c:when>
											<c:when test="${coupon.couponApplyType == '2' }">다운로드 시점부터 <strong>${coupon.couponApplyDay}일</strong> 후까지</c:when>
											<c:otherwise>${op:date(coupon.couponApplyStartDate)} ~ ${op:date(coupon.couponApplyEndDate)}</c:otherwise>
										</c:choose>
										</span>
									</td>
									<td><a href="javascript:downloadCoupon('${coupon.couponId}');" class="btn btn-s btn-normal" title="쿠폰다운">쿠폰다운</a></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<page:pagination/>

				</div>

				<div class="pop_desc">
					<p><strong>알아두세요!</strong></p>
					<ul class="mt10">
						<li>다운로드한 할인 쿠폰은 <strong>[마이페이지 > 쿠폰]</strong> 에서 확인하실 수 있습니다.</li>
						<li>다운로드한 할인쿠폰의 유효기간 경과시 사용할 수 없습니다.</li>
						<li>할인 쿠폰을 사용하여 주문하신 후 취소/반품/교환하신 경우 <strong>쿠폰 유효기간 경과 시 사용하실 수 없습니다.</strong></li>
						<li>할인 쿠폰을 사용하여 주문하신 후 취소/반품/교환하신 경우 <strong>쿠폰 유효기간이 남아 있는 경우 사용이 가능합니다.</strong></li>
					</ul>
				</div>
			</c:if>
			<c:if test="${empty coupons}">

				<div class="popup_contents">
					<div class="result_box">
						<p>‘다운 가능한 쿠폰이 없습니다.’</p>
					</div>
				</div><!--//popup_contents E-->

			</c:if>

		</div><!--//popup_contents E-->

		<a href="javascript:self.close()" class="popup_close">창 닫기</a>
	</div>

<page:javascript>
<script src="/content/modules/front/item.view.js"></script>
<script type="text/javascript">
	function downloadCoupon(couponId) {
		var param = {
			'couponId': couponId
		};

		$.post('/item/download-coupon', param, function(response) {
			Common.responseHandler(response, function() {
				alert(Message.get("M01448"));	// 쿠폰을 다운로드했습니다.
				location.reload();
			});
		});
	}

	<c:if test="${!empty openerReload}">
		$(function(){
			opener.location.reload();
		});
	</c:if>
</script>
</page:javascript>