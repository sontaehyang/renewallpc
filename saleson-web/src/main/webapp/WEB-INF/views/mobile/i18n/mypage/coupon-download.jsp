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
		<span class="his_back">
			<a href="/m/mypage" class="ir_pm">뒤로가기</a>
		</span>
	</div>

	<!-- 내용 : s -->
	<div class="con">
		<div class="mypage_wrap">
			<div class="tab_area">
				<ul class="tab_list02">
					<li><a href="/m/mypage/download-coupon-list">사용가능 쿠폰 <span>(${op:numberFormat(totalCount)})</span></a></li>
					<li class="on"><a href="javascript:;">다운로드 가능쿠폰 <span>(${op:numberFormat(couponDownloadCount)})</span></a></li>
				</ul>
			</div>
			<!-- //tab_area -->


			<div class="tab_container">
				<div class="tab_con down_list">

					<c:if test="${!empty list}">

						<ul class="coupon_list" id="op-list-data">
							<jsp:include page="../include/mypage-coupon-download-list.jsp"/>
						</ul>
						<!-- //coupon_list -->

						<div class="load_more" id="op-list-more">
							<button type="button" class="btn_st2" onclick="javascript:paginationMore('coupon-download')"><span>더보기</span></button>
						</div>

						<dl class="caution">
							<dt>쿠폰 이용안내</dt>
							<dd>쿠폰은 다운받은 직후에 사용가능합니다.</dd>
							<dd>결제 시 쿠폰적용을 확인하신 후 바로 결제하실 수 있습니다.</dd>
						</dl>

					</c:if>

					<!-- coupon_none -->
					<c:if test="${empty list}">
						<div class="coupon_none">
							<p>다운로드 가능 쿠폰이 없습니다.</p>
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
<!-- //#container -->
<!-- container : e -->



<page:javascript>
	<script type="text/javascript">

		var currentPage = 1;

		$(function(){

			showHideMoreButton();

		})


		function paginationMore(key) {
			currentPage++;
			$.get('/m/mypage/coupon-download/list', 'page=' + currentPage, function(html) {
				$("#op-list-data").append(html);

				showHideMoreButton();

			});
		}

		function downloadCoupon(couponId) {

			Common.confirm(Message.get("M01502"), function(){ 	// 쿠폰을 다운 받으시겠습니까?
				$.post("/m/mypage/coupon-download/"+couponId,"",function(resp){
					Common.responseHandler(resp, function(){
						location.reload();
					});
				});

			});
		}

		function showHideMoreButton() {
			if ($("#op-list-data").find(' > li').size() == Number('${pagination.totalItems}')) {
				$('#op-list-more').hide();
			}
		}
	</script>
</page:javascript>