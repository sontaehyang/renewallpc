<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<style>
	.fb-login-button {
		float: left;
		margin-right: 3px;
	}
</style>
<script type="text/javascript">
	// facebook api 선언 스크립트
	window.fbAsyncInit = function() {
		FB.init({
			appId		: '${op:property("fb.login.appId")}',
			xfbml		: true,
			version		: 'v6.0'
		});

		FB.AppEvents.logPageView();
	};

	(function(d, s, id){
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) {return;}
		js = d.createElement(s); js.id = id;
		js.src = "https://connect.facebook.net/en_US/sdk.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	
</script>

	<!-- login button -->
	<c:if test="${!isMypage && !isJoin}">
		<a href="javascript:fb_login();" class="sns_image" title="페이스북 로그인"><img src="/content/images/btn/btn_login_facebook.png" alt="페이스북 로그인"></a>
	</c:if>
	
<script type="text/javascript">
function fb_login(){
    FB.login(function(response) {
        if (response.authResponse) {
            access_token = response.authResponse.accessToken; //get access token
            user_id = response.authResponse.userID; //get FB UID

            FB.api('/me', {fields: 'email, name, id'}, function(response) {
            	var snsUser = {
					"snsType" : "facebook",            			
					"snsId" : response.id,
					"email" : response.email,
					"snsName" : response.name
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
					location.href="/users/sns-join?snsType=facebook";
				}
            });
        } else {
            console.log('User cancelled login or did not fully authorize.');
        }
    }, {scope: 'public_profile, email'});
}
</script>