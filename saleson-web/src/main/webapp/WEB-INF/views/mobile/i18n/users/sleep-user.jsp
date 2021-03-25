<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

		<div class="title">
			<h2>휴면계정 안내</h2>
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<!-- join_pr -->
			
			<div class="join_area">
				<div class="acc_txt">
					<p class="ob_txt3">장기간 미사용 계정 휴면 전환 안내</p>
					<p class="ob_txt sm">회원님의 계정은 개인정보보호를 위해 1년이상 리뉴올PC 서비스를 이용하지 않은 계정에 한해 정보통신망 이용 촉진 및 정보보호 등에 관한 법률에 따라 휴면계정으로 전환되었습니다.</p>
					<p class="ob_txt bd">리뉴올PC를 다시 이용하시려면, 하단의 “리뉴올PC서비스 계속 이용하기” 버튼을 클릭해 주시기 바랍니다.</p>
				</div>
				<div class="acc_btn">
					<button class="btn_st1 decision" type="button" title="리뉴올PC 서비스 계속 이용하기" onclick="wakeUpSleepUser();">리뉴올PC 서비스 계속 이용하기</button>
					<button class="btn_st1 reset" type="submit" title="휴면계정유지"onclick="location.href='/op_security_logout?target=/m'">휴면계정유지</button>
				</div>
				<!-- acc_btn -->
				<div class="search_way typeA">
					<dl>
						<dt>휴면 계정 시 제한 사항</dt>
						<dd>회원의 개인정보는 별도 분리하여 보관</dd>
						<dd>메일 수신 중지</dd>
					</dl>
					<dl>
						<dt>일반회원 전환 시 변경 사항</dt>
						<dd>개인정보 복원</dd>
						<dd>메일 수신 복원</dd>
					</dl>
				</div>
				<div class="so_noti">
					<span>리뉴올PC 이용약관</span>
					<p>회원이 12개월(365일)이상 로그인을 하지 않은 경우 해당 회원의 아이디는 휴면아이디가 되어 회원 로그인을 비롯한 모든 서비스에 대한 이용이 정지되고, 회사는 휴면아이디의 개인정보를 다른 아이디로 별도로 관리한다.</p>
				</div>
			</div>
			<!-- join_area -->
		</div>
		<!-- 내용 : e -->

		<page:javascript>
<script type="text/javascript">

function wakeUpSleepUser() {
	$.get('/m/users/wakeup-user', function(response) {
		if (response.isSuccess) {
			alert("고객님의 계정이 휴면해제 되었습니다.\n원활한 서비스 이용을 위하여 재 로그인 해주십시오.");
			location.href="/m/users/login";
		} else {
			alert(response.errorMessage);	
		}
	});
}

</script>
</page:javascript>