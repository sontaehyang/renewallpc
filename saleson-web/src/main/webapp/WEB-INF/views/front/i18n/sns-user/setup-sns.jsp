<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/order">쇼핑내역</a> 
			<span>주문/배송조회</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<c:set var="naver" value="" />
	<c:set var="facebook" />
	<c:set var="kakao" value="" />

	<c:forEach var="userSnsList" items="${userSnsList}" varStatus="i">
		<c:choose>
			<c:when test='${userSnsList.snsType eq "naver"}'>
				<c:set var="naver" value="${userSnsList}"/>
			</c:when>
			<c:when test='${userSnsList.snsType eq "facebook"}'>
				<c:set var="facebook" value="${userSnsList}"/>
			</c:when>
			<c:when test='${userSnsList.snsType eq "kakao"}'>
				<c:set var="kakao" value="${userSnsList}"/>
			</c:when>
		</c:choose>
	</c:forEach>

	<div id="contents" class="pt0">
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" />
		<div class="contents_inner easy_login">
			<h2>간편 로그인 관리</h2>
			<p>네이버, 페이스북, 카카오톡에 가입되어 있는 정보를 통해 회원 전용 서비스를 이용하실 수 있습니다.</p>
			<ul class="login_list" id="sns-list">
				<li class='${!empty naver ? "linked" : ""}' data-type='naver' data-email='${!empty naver ? naver.email : ""}' data-order='' data-id='${!empty naver ? naver.snsUserId : ""}'>
					<span class="site_logo"><img src="/content/images/icon/icon_naver_max.png" alt="네이버"></span>
					<div class="info"> 
						<p class="site_name">네이버</p>
						<p class="txt">${!empty naver ? op:datetime(naver.createdDate) : "간편로그인 연동 정보가 없습니다."}</p>
					</div>
					<a href="javascript:void(0);" class='btn btn_${!empty naver ? "release" : "connect"}' title='${!empty naver ? "연결해제" : "연결하기"}'>${!empty naver ? "연결해제" : "연결하기"}</a>
				</li>
				<li class='${!empty facebook ? "linked" : ""}' data-type='facebook' data-email='${!empty facebook ? facebook.email : ""}' data-order='' data-id='${!empty facebook ? facebook.snsUserId : ""}'>
					<span class="site_logo"><img src="/content/images/icon/icon_facebook_max.png" alt="페이스북"></span>
					<div class="info"> 
						<p class="site_name">페이스북</p>
						<p class="txt">${!empty facebook ? op:datetime(facebook.createdDate) : "간편로그인 연동 정보가 없습니다."}</p>
					</div>
					<a href="javascript:void(0);" class='btn btn_${!empty facebook ? "release" : "connect"}' title='${!empty facebook ? "연결해제" : "연결하기"}'>${!empty facebook ? "연결해제" : "연결하기"}</a>
				</li>
				<li class='${!empty kakao ? "linked" : ""}' data-type='kakao' data-email='${!empty kakao ? kakao.email : ""}' data-order='' data-id='${!empty kakao ? kakao.snsUserId : ""}'>
					<span class="site_logo"><img src="/content/images/icon/icon_kakao_max.png" alt="카카오톡"></span>
					<div class="info"> 
						<p class="site_name">카카오톡</p>
						<p class="txt">${!empty kakao ? op:datetime(kakao.createdDate) : "간편로그인 연동 정보가 없습니다."}</p>
					</div>
					<a href="javascript:void(0);" class='btn btn_${!empty kakao ? "release" : "connect"}' title='${!empty kakao ? "연결해제" : "연결하기"}'>${!empty kakao ? "연결해제" : "연결하기"}</a>
				</li>
			</ul>
		</div>
	</div>    
</div>

<jsp:include page="naver.jsp"/>
<jsp:include page="facebook.jsp"/>
<jsp:include page="kakao.jsp"/>
<script type='text/javascript'>
	$(function(){
		$("ul#sns-list li a").on("click",function(event){
			event.stopPropagation();
			event.preventDefault();
			var $li = $(this).parent();
			var type = $li.data("type");
			var status = $li.hasClass("linked") ? "on" : "off";
			var order = $li.data("order");
			var certified = "${snsUser.certifiedDate}";
			// sns연동일 경우 disconnect 진행, 미연동일 경우 연동 진행
			if (status == "on") {
				if (!confirm("선택하신 SNS의 연결해제를 진행하시겠습니까?")) {
					return false;
				}
				var id = $li.data("id");
				var param = {"snsUserId" : id};
				$.ajaxSetup({
					"async" : false
				});
				$.post("/sns-user/disconnect-sns", param, function(response){
					if (response.status == "00") {
						alert(response.message);
						if(response.value == "00") {
							location.reload();
						}
					}
					else {
						console.log("error occurred - " + response.message);
					}
				});
				return false;
			}
			else {
				if (type=="naver") {
					naverProcess();
				}
				else if (type=="facebook") {
					fb_login();
				}
				else if (type=="kakao") {
					loginWithKakao();
				}
			}
		});
	});
	
	function snsUserSubmit(snsUser) {
		snsUser.isMypage = true;
		snsUser.snsUserId = '${empty snsUser.snsUserId ? "0" : snsUser.snsUserId}';
		$.ajaxSetup({
			"async" : false
		});
		$.post("/sns-user/search", snsUser, function(response){
			console.log(response);
			if (response.status == "00") {
				if (response.value == "00") {
					alert(response.message);
					location.reload();
				}
				else if (response.value == "01") {
					alert(response.message);
					return false;
				}
			}
			else {
				console.log("Error Occured:: " + response.message);
			}
		});
	}
	
	
	/* function setNamePass() {
		$("ul.sns-list li.linked").on("click",function(event){
			<c:if test="${!empty snsUser.certifiedDate}">
				var email = $(this).data("email");
				if (email != "") {
					$(".userId").text(email);
					$("#loginId").val(email);
					$("#setSnsArea").show();
				}
				else {
					alert("이메일 인증을 거치지 않은 SNS일수 있습니다. 확인 후 다시 시도해주세요.");
				}
			</c:if>
		});
		// setSnsForm validator
		$("#setSnsForm").validator(function(){
			if ($("#password").val() != $("#password_confirm").val()) {
				alert("비밀번호와 비밀번호(확인)이 다릅니다. 확인해주세요.");
				$("#password").focus();
				return false;
			}
			
			var param = {
				"loginId" : $("#loginId").val()
			}
			
			var proceed = false;
			$.ajaxSetup({
				"async" : false
			});
			$.post("/sns-user/loginId-check", param, function(response){
				if (response.status == "00") {
					if (response.value == 0) {
						proceed = true;
					}
				}
				else {
					console.log("Error Occured:: " + response.message);
				}
			});
			
			if (!proceed) {
				alert("해당 E-mail로 생성되어있는 아이디가 이미 존재합니다.");
				return false;
			}
		});
	} */
	
</script>
