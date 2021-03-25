<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
.g_number {margin:60px auto 90px;display:table-cell;height:40px;text-align:center; vertical-align:middle;background:#f2f2f2;text-align:center;border:1px solid #e2e2e2;box-sizing:border-box;}
.tit {margin-bottom: 20px;font-size: 24px;}
.btn_large {margin: 30px 0 0; display:inline-block; background: #dc4d8a; min-width:130px; width:350px; height:60px; padding:0 50px; font-size:18px; line-height:60px; color:#fff; text-align:center; box-sizing:border-box;}
</style>



	<div class="title">
		<h2>오프라인 쿠폰 전환</h2>
		<span class="his_back"> <a href="/m/mypage" class="ir_pm">뒤로가기</a>
		</span>
	</div>

	<!-- 내용 : s -->
	<div class="con">
		<div class="mypage_wrap">
			<div class="tab_container" style="text-align:-webkit-center;">
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
			<!-- //tab_container -->

		</div>
		<!-- //mypage_wrap -->

	</div>
	<!-- 내용 : e -->



<!-- //#container -->
<!-- container : e -->



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

		$.post("/m/mypage/offline-coupon-exchange?offlineCode="+code, function(response){
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