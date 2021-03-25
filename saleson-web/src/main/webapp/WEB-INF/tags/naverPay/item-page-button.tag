<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<c:if test="${naverPay.use == true}">
	<div class="sale" style="display:inline-block;">
		<c:set var="buttonType">B</c:set>
		<c:if test="${naverPay.deviceType == 'mobile'}">
			<c:set var="buttonType">MA</c:set>
		</c:if>

		<script type="text/javascript" src="https://pay.naver.com/customer/js/naverPayButton.js" charset="UTF-8"></script>
		<script type="text/javascript" src="https://pay.naver.com/customer/js/innerNaverPayButton.js?site_preference=normal&amp;416428" charset="UTF-8"></script>
		<script type="text/javascript" src="/content/modules/naverpay/naver.pay.js"></script>
		<link id="NAVER_PAY_STYLE" type="text/css" rel="stylesheet" href="https://img.pay.naver.net/static/css/button/button.css?416428">
		<div class="naver_pay" style="display: inline-block; ${naverPay.deviceType == 'web' ? 'padding-top: 20px;' : ''}">
			<style>
				.npay_type_A_2{
					width: auto !important;
					height: auto !important;
				}
			</style>
			<script type="text/javascript">
				naver.NaverPayButton
					.apply({
						//BUTTON_KEY : "2FB54236-7193-4F4B-B15E-1C1559A56B91", //"버튼 인증 키", // 체크아웃에서 제공받은 버튼 인증 키 입력
						BUTTON_KEY : "${naverPay.buttonKey}", //"버튼 인증 키", // 체크아웃에서 제공받은 버튼 인증 키 입력
						TYPE : "A", //버튼 모음 종류 설정
						COLOR : 1, // 버튼 모음의 색 설정
						COUNT : 2, // 버튼 개수 설정. 구매하기 버튼만 있으면(장바구니 페이지) 1, 찜하기 버튼도 있으면(상품 상세 페이지) 2를 입력.
						ENABLE : "Y", // 품절 등의 이유로 버튼 모음을 비활성화할 때에는 "N" 입력
						BUY_BUTTON_HANDLER : NaverPay_buyButtonHandler, // 구매하기 버튼 이벤트 Handler 함수 등록, 품절인 경우 not_buy_nc 함수 사용
						BUY_BUTTON_LINK_URL : "", // 링크 주소 (필요한 경우만 사용) (http://mydomain.com/buy/url/)
						WISHLIST_BUTTON_HANDLER : NaverPay_wishlistButtonHandler, // 찜하기 버튼 이벤트 Handler 함수 등록
						WISHLIST_BUTTON_LINK_URL : "", // 찜하기 팝업 링크 주소(http://mydomain.com/wishlist/popup/url/)
						"" : ""
					});

				var NaverPay = {"payPopupUrl" : "${naverPay.payPopupUrl}"
							, "wishlistPopupUrl" : "${naverPay.wishlistPopupUrl}", "deviceType": "${naverPay.deviceType}"};
				//console.log(NaverPay);
			</script>
		</div>
	</div>
</c:if>