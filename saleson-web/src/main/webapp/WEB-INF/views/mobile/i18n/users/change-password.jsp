<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<div class="title">
	<h2>비밀번호 변경</h2>
	<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
</div>
<!-- //title -->

<!-- 내용 : s -->
<div class="con login_wrap">
	<div class="pop_con">
		<!-- //login_tab -->
		<div class="login_con">
			<!-- 회원 로그인 s -->
			<div class="member">
				<form method="POST" id="changeForm" >
					<div class="login_input">
						<div class="input_area">
							<input type="password" name="password" class="required" title="비밀번호" placeholder="비밀번호">
						</div>
						<div class="input_area">
							<input type="password" name="changePassword" class="required _password _duplicated" title="변경할 비밀번호" placeholder="변경할 비밀번호">
						</div>
						<div class="input_area">
							<input type="password" name="reChangePassword" class="required _password _duplicated" title="변경할 비밀번호 재입력" placeholder="변경할 비밀번호 재입력">
						</div>
					</div>
					<!-- //login_input -->

					<div class="pw_btn">
						<button type="submit" class="btn_st1 decision">변경</button>
						<button type="button" class="btn_st1 reset" onclick="delayChangePassword()">연장</button>
					</div>
				</form>
				<!-- //login_btn -->
			</div>
			<!-- 회원 로그인 e -->
		</div>
		<!-- //login_con -->
	</div>
	<!-- //pop_con -->
</div>



<page:javascript>
<script type="text/javascript">

$(function() {
	$('#changeForm').validator(function() {

		if (!confirm("비밀번호를 변경 하시겠습니까?")) {
			return false;
		}

		if($('input[name=changePassword]').val() != $('input[name=reChangePassword]').val()){
			alert('변경할 비밀번호가 맞지 않습니다.');
			$('input[name=reChangePassword]').focus();
			return false;
		}
	});
});

function delayChangePassword () {

	if (confirm("현 비밀번호를 연장 하시겠습니까?")) {
		$.post('/m/users/delay-change-password', {}, function(response) {
			if (response.isSuccess) {
				alert('비밀번호 유효기간이 연장이 되었습니다.');
				location.href = "/op_security_logout?target=/m";
			} else {
				alert(response.errorMessage);
			}
		});
	}
}

</script>
</page:javascript>