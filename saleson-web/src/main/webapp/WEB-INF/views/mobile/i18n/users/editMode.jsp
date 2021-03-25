<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

	<div class="title">
		<h2>회원정보</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		<ul class="tab_list01 four_tab letter">
			<li class="on"><a href="#">회원정보 수정</a></li>
			<li><a href="/m/mypage/delivery">배송주소록 관리</a></li>
			<li><a href="/m/sns-user/setup-sns">SNS연동설정</a></li>
			<li><a href="/m/users/secede">회원탈퇴</a></li>
		</ul>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<div class="mypage_wrap">
			<div class="info_modi">
				<p class="txt">안전한 회원정보 보호를 위해<br><span>비밀번호를 다시 한번 확인</span>합니다.</p>
				<form id="op-editModeForm" method="post" action="/m/users/modify">
				<input type="hidden" name="modifyResult" value="1" />
				<div class="bd_table">
					<ul class="del_info">
						<li>
							<span class="del_tit t_gray">아이디</span>
							<span class="del_detail">${user.loginId}</span>
						</li>
						<li>
							<span class="del_tit t_gray">비밀번호</span>
							<div class="input_area">
								<input class="transparent" type="password" name="userPassword" placeholder="비밀번호를 입력해주세요." >
							</div>
						</li>
					</ul>
				</div>
				<!-- //bd_table -->
				
				<div class="btn_wrap">
					<button type="reset" class="btn_st1 reset" onclick="location.href='/m'">취소</button>
					<button type="submit" class="btn_st1 decision">확인</button>
				</div>
				<!-- //btn_wrap -->
				
				</form>
				
			</div>
			<!-- //info_modi -->
			
		</div>
		<!-- //mypage_wrap -->
	</div>
	<!-- 내용 : e -->
		


<page:javascript>	
<script type="text/javascript">
$(function() {
	$('#op-editModeForm').validator(function() {
		
	});
});	
</script>
</page:javascript>