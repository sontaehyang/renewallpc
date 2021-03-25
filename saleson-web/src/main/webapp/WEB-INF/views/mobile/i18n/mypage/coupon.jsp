<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>



<div id="container">
	<div class="title">
		<h2>쿠폰 조회</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>

	<!-- 내용 : s -->
	<div class="con">
		<div class="mypage_wrap">
			<div class="tab_area">
				<ul class="tab_list02">
					<li class="on"><a href="javascript:;">사용가능 쿠폰 <span>(${op:numberFormat(totalCount)})</span></a></li>
					<li><a href="/m/mypage/coupon-download">다운로드 가능쿠폰 <span>(${op:numberFormat(couponDownloadCount)})</span></a></li>
				</ul>
			</div>
			<!-- //tab_area -->

			<div class="tab_container">
				<div class="tab_con use_list">

					<c:if test="${!empty list}">

						<ul class="coupon_list" id="op-list-data">

							<jsp:include page="../include/mypage-coupon-list.jsp"></jsp:include>

						</ul>
						<!-- //coupon_list -->

						<dl class="caution">
							<dt>쿠폰 이용안내</dt>
							<dd>쿠폰은 다운받은 직후에 사용가능합니다.</dd>
							<dd>결제 시 쿠폰적용을 확인하신 후 바로 결제하실 수 있습니다.</dd>
						</dl>

					</c:if>

					<!-- coupon_none -->
					<c:if test="${empty list}">
						<div class="coupon_none">
							<p>사용 가능 쿠폰이 없습니다.</p>
						</div>
					</c:if>


					<c:if test="${totalCount > 10 }">
					<div class="load_more" id="op-list-more">
						<button type="button" class="btn_st2" onclick="javascript:paginationMore('coupon')"><span>더보기</span></button>
					</div>
					</c:if>

					</div>
				<!-- //use_list -->

			</div>
			<!-- //tab_container -->

		</div>
		<!-- //mypage_wrap -->

	</div>
	<!-- 내용 : e -->

</div>


</div>
<!-- //#container -->
<!-- container : e -->



<page:javascript>
<script type="text/javascript">
$(function(){
	// 이전페이지로 돌아왔을 때 More Data 유지.
	Mobile.pagination.init('coupon');
	
	showHideMoreButton();
	
	
})

var currentPage = 1;
function paginationMore(key) {
	currentPage++;
	$.post('/m/mypage/coupon/' + currentPage, 'page=' + currentPage, function(html) {
		$("#op-list-data").append(html);
		
		showHideMoreButton();
		
		Mobile.pagination.set(key);
	});
}

function showHideMoreButton() {
	if ($("#op-list-data").find(' > li').size() == Number('${pagination.totalItems}')) {
		$('#op-list-more').hide();
	}
}

function couponAppliesTo(couponId, couponTerms) {
	var popupViewName = 'coupon-applies-to' + (couponTerms == '2' ? '-item' : '-category');
	Common.popup('/m/coupon/applies-to/' + couponId + '/' +  couponTerms, popupViewName, 898, 740);
}




</script>
</page:javascript>