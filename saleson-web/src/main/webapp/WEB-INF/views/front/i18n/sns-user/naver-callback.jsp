<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<script type="text/javascript" src="<c:url value="/content/modules/jquery/jquery-1.11.0.min.js" />"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>

<div id="naver_id_login"></div>
<!-- //네이버아이디로로그인 버튼 노출 영역 -->
<script type="text/javascript">
    var naverLogin = new naver.LoginWithNaverId(
        {
            clientId: '${op:property("naver.login.appId")}',
            callbackUrl: '${op:property("saleson.url.shoppingmall")}${op:property("naver.login.callback")}',
            isPopup: false,
            callbackHandle: true
            /* callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다. */
        }
    );

    /* (3) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 */
    naverLogin.init();

    /* (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close) */
    window.addEventListener('load', function () {
        naverLogin.getLoginStatus(function (status) {
            if (status) {
                /* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
                var name = naverLogin.user.getName();
                if( name == undefined || name == null) {
                    alert("이름은 필수정보입니다. 정보제공을 동의해주세요.");
                    /* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */
                    naverLogin.reprompt();
                    return;
                }

                var snsUser = {
                    "snsType" : "naver",
                    "snsId" : naverLogin.user.getId(),
                    "email" : naverLogin.user.getEmail(),
                    "snsName" : naverLogin.user.getName()
                };

                parent.opener.naverResponse(snsUser);
                self.close();

            } else {
                console.log("callback 처리에 실패하였습니다.");
            }
        });
    });
</script>
