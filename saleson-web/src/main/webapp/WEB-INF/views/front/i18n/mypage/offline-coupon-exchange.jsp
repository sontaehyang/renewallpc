<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop"%>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a> <a href="#">마이페이지</a> <a href="#">쇼핑내역</a> <span>주문/배송조회</span>
		</div>
	</div>
	<!-- // location_area E -->

	<jsp:include page="../include/mypage-user-info.jsp" />

	<div id="contents" class="pt0">
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" />
		<div class="contents_inner">
			<h2>오프라인 쿠폰 전환</h2>
			<div class="box_wrap">
				<div class="g_number">
					<p class="tit">쿠폰 번호</p>
					<div>
						<input type="text" id="offlineCode" class="required coupon_input" title="'-'없이 발급받은 쿠폰번호를 입력해주세요.">
					</div>
					<div>
						<button type="button" onclick="submit();" class="btn_large">검색</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- // contents E -->
</div>
<!--// inner E-->

<page:javascript>
	<script type="text/javascript">
		$(function() {
			// 메뉴 활성화
			$('#lnb_benefits').addClass("on");
		});

		function submit() {

			var $offlineCode = $('#offlineCode');
			var code = $offlineCode.val();

			if(code == '') {
				alert("쿠폰번호를 입력해주세요.");
				$offlineCode.focus();
				return false;
			}

			$.post("/mypage/offline-coupon-exchange?offlineCode="+code, function(response){
				if (response.isSuccess) {
					alert("전환되었습니다.");
					location.reload();
				} else {
					alert(response.errorMessage);
					$offlineCode.focus();
				}
			});
		}

	</script>
</page:javascript>