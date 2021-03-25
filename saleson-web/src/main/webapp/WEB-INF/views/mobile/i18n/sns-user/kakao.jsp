<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

	<c:if test='${!isMypage && !isJoin}'>
	    <li class="login_kakao"><a href="javascript:loginWithKakao();"><span class="logo"><img src="/content/mobile/images/common/login_kakao_logo.gif" alt="kakaotalk logo"></span>로그인</a></li>
    </c:if>
    
<script type='text/javascript'>

	// 사용할 앱의 JavaScript 키를 설정해 주세요.
	Kakao.init('${op:property("kakao.login.appId")}');

	function loginWithKakao() {
		// 로그인 창을 띄웁니다.
		Kakao.Auth.login({
			success : function(authObj) {
				Kakao.API.request({
					url : '/v2/user/me',
					success : function(response) {
                        // 카톡 닉네임 이모티콘 제거 정규식
                        var patterns = "[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+";
                        var nickName = response.properties.nickname;
                        nickName = nickName.replace(new RegExp(patterns), '');
                        response.properties.nickname = nickName;

						var snsUser = {
							"snsType" : "kakao",
							"snsId" : response.id,
							"email" : response.kakao_account.email,
							"snsName" : response.properties.nickname
						};
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
							location.href="/m/users/sns-join?snsType=kakao";
						}
					},
					fail : function(error) {
						console.log(JSON.stringify(error));
					}
				});
			},
			fail : function(err) {
				alert(JSON.stringify(err));
			}
		});
	};

	// 카카오 로그인 버튼을 생성합니다.
	/* Kakao.Auth.createLoginButton({
		container : '#kakao-login-btn',
		size: 'small',
		success : function(authObj) {
			Kakao.API.request({
				url : '/v1/user/me',
				success : function(res) {
					console.log(JSON.stringify(res));
				},
				fail : function(error) {
					console.log(JSON.stringify(error));
				}
			});
		},
		fail : function(err) {
			console.log(JSON.stringify(err));
		}
	}); */
	//]]>
</script>
