<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
	<!-- 네이버아이디로로그인 버튼 노출 영역 -->
	<c:if test='${!isMypage && !isJoin}'>
		<li class="login_naver"><a href="javascript:naverProcess();"><span class="logo"><img src="/content/mobile/images/common/login_naver_logo.gif" alt="naver logo"></span>로그인</a></li>
		<div id="naverIdLogin" style="display:none;"></div>
	</c:if>

	<c:if test='${isMypage && !isJoin}'>
		<li class="login_naver" style="display:none;"><a href="javascript:naverProcess();"><span class="logo"><img src="/content/mobile/images/common/login_naver_logo.gif" alt="naver logo"></span>로그인</a></li>
		<div id="naverIdLogin" style="display:none;"></div>
	</c:if>
	<!-- //네이버아이디로로그인 버튼 노출 영역 -->
	<script type="text/javascript">

		var naverLogin = new naver.LoginWithNaverId(
				{
					clientId: '${op:property("naver.login.appId")}',
					callbackUrl: '${op:property("saleson.url.shoppingmall")}${op:property("naver.login.callback")}',
					isPopup: true, /* 팝업을 통한 연동처리 여부 */
					loginButton: {color: "green", type: 3, height: 40} /* 로그인 버튼의 타입을 지정 */
				}
		);

		/* 설정정보를 초기화하고 연동을 준비 */
		naverLogin.init();

		function naverResponse(snsUser) {
			var isJoined = true;
			<c:if test='${!isMypage && !isJoin}'>
				$.post("/sns-user/sns-joined-check", snsUser, function(response){
					if (response.value == 0) {
						isJoined = false;
					}
				});
			</c:if>
			if (isJoined) {
				snsUserSubmit(snsUser);
			}
			else {
				location.href="/m/users/sns-join?snsType=naver";
			}
		}
		
		function naverProcess() {
			$('#naverIdLogin_loginButton img').click();
		}
	</script>
