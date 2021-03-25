<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<div class="con">
	<div class="pop_title">
		<h3>${op:message('M00490')}</h3> <!-- 쿠폰 다운로드 -->
		<a href="javascript:history.back();" class="history_back">뒤로가기</a>
	</div>
	<div class="pop_con">
		<c:if test="${ !empty coupons }">
			<div class="con_top">
				<h4>다운로드 가능 쿠폰</h4>
					<%--<a href="#" class="btn_st3 t_small t_white b_blue">일괄발급</a> <!-- 개발 -->--%>
			</div>
			<div class="coupon_wrap">
				<ul class="coupon_list" id="op-list-data">
					<jsp:include page="../include/item-coupon-list.jsp"/>
				</ul>
			</div> <!-- //coupon_wrap E-->
		</c:if>
		<c:if test="${ empty coupons }">
			<div class="coupon_wrap">
				<div class="coupon_none">
					<p>다운가능 쿠폰이 없습니다.</p>
				</div>
			</div>
		</c:if>

		<div class="load_more" id="op-list-more">
			<button type="button" class="btn_st2" onclick="javascript:paginationMore('item-coupon-list')"><span>더보기</span></button>
		</div>
	</div><!-- //pop_con E-->
</div><!--// con E-->




<page:javascript>
	<script type="text/javascript">

		var currentPage = 1;

		$(function() {
			showHideMoreButton();
		});

		function paginationMore(key) {
			currentPage++;
			$.get('/m/item/coupon/${item.itemId}/list', 'page=' + currentPage, function(html) {
				$("#op-list-data").append(html);

				showHideMoreButton();

			});
		}

		function showHideMoreButton() {
			if ($("#op-list-data").find(' > li').size() == Number('${pagination.totalItems}')) {
				$('#op-list-more').hide();
			}
		}

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

		function showCouponCategory(couponId) {

			var $couponCategoryLayer = $('#coupon_category_' + couponId + ' div');

			Common.dimmed.show();
			var html = $couponCategoryLayer.parentHtml();
			$('#wrap').css({'position':'fixed', 'top': 0, 'width': '100%'});
			$('body').scrollTop(0);
			$('body').append('<div id="coupon_category"><div>' + html +'</div></div>');



			$('#dimmed, #coupon_category').on('click', function(){
				$('#wrap').css({'position':'', 'top': '', 'width': ''});
				Common.dimmed.hide();

				$('#coupon_category').remove();


			});
		}
	</script>
</page:javascript>