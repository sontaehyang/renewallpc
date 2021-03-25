<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


	<div class="title">
		<h2>회원정보</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		<ul class="tab_list01 four_tab letter">
			<li><a href="/m/users/editMode">회원정보 수정</a></li>
			<li><a href="/m/mypage/delivery">배송주소록 관리</a></li>
			<li class="on"><a href="#">SNS연동설정</a></li>
			<li><a href="/m/users/secede">회원탈퇴</a></li>
		</ul>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
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
		<p class="easy_manage_notice">네이버, 페이스북, 카카오톡에 가입되어 있는 정보를 통해 회원 전용 서비스를 이용 하실 수 있습니다.</p>
		<ul class="easy_manage login_list" id="sns-list">
			<li class='type01 ${!empty naver ? "linked" : ""}' data-type='naver' data-email='${!empty naver ? naver.email : ""}' data-order='' data-id='${!empty naver ? naver.snsUserId : ""}'>
				<div class="login_info">
					<p class="tit">네이버</p>
					<p class="info">${!empty naver ? op:datetime(naver.createdDate) : "간편로그인 연동 정보가 없습니다."}</p>
				</div>
				<div class="btn_wrap"><button type="button" class='btn_st3 ${!empty naver ? "" : "b_blue t_white"}'>${!empty naver ? "연결해제" : "연결하기"}</button></div>
			</li>
			<li class='type02 ${!empty facebook ? "linked" : ""}' data-type='facebook' data-email='${!empty facebook ? facebook.email : ""}' data-order='' data-id='${!empty facebook ? facebook.snsUserId : ""}'>
				<div class="login_info">
					<p class="tit">페이스북</p>
					<p class="info">${!empty facebook ? op:datetime(facebook.createdDate) : "간편로그인 연동 정보가 없습니다."}</p>
				</div>
				<div class="btn_wrap"><button type="button" class='btn_st3 ${!empty facebook ? "" : "b_blue t_white"}'>${!empty facebook ? "연결해제" : "연결하기"}</button></div>
			</li>
			<li class='type03 ${!empty kakao ? "linked" : ""}' data-type='kakao' data-email='${!empty kakao ? kakao.email : ""}' data-order='' data-id='${!empty kakao ? kakao.snsUserId : ""}'>
				<div class="login_info">
					<p class="tit">카카오톡</p>
					<p class="info">${!empty kakao ? op:datetime(kakao.createdDate) : "간편로그인 연동 정보가 없습니다."}</p>
				</div>
				<div class="btn_wrap"><button type="button" class='btn_st3 ${!empty kakao ? "" : "b_blue t_white"}'>${!empty kakao ? "연결해제" : "연결하기"}</button></div>
			</li>
		</ul><!-- //easy_manage -->

			
	
	</div>
	<!-- 내용 : e -->

	<jsp:include page="naver.jsp"/>
	<jsp:include page="facebook.jsp"/>
	<jsp:include page="kakao.jsp"/>
	
<script type='text/javascript'>
	$(function(){
		$("ul#sns-list li button").on("click",function(event){
			event.stopPropagation();
			event.preventDefault();
			var $li = $(this).parent().parent();
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
	
</script>
