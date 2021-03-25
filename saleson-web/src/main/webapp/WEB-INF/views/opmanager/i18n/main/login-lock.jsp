<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<!-- 상단
<div id="header">
<h1><a href=""><img src="/content/images/opmanager/common/logo.png" alt="KB희망별" /></a></h1>
<div class="tnb"></div>
</div>
-->

<!-- 내용 -->


<div id="container" class="login">

	<div class="login_area">

		<form id="lockForm"  method="POST">
			<fieldset>
				<legend class="hidden">계정 잠김 해지</legend>

				<h2>계정 잠김 해지</h2> <!-- 관리자 로그인 -->
				<p class="guide">계정 잠김 해지후 이용이 가능합니다.</p>

				<div class="inputs">
					<p>
						<span class="label">${op:message('M00081')}</span> <!-- 아이디 -->
						<input type="text" name="loginId" id="loginId" maxlength="40" class="required ime-mode-disabled" title="아이디" />
					</p>
					<p>
						<span class="label">휴대폰번호</span> <!-- 휴대폰번호 -->
						<input type="text" name="phoneNumber" id="phoneNumber" maxlength="20" class="required ime-mode-disabled _number"/>
					</p>
					<p>
						<span class="label"></span> <!-- 휴대폰번호 -->
						<button type="button" onclick="sendAuthNumber()" class="btn btn-default btn-m">인증번호 발송</button>
					</p>
					<p>
						<span class="label">인증번호</span> <!-- 인증번호 -->
						<input type="text" name="smsAuth" id="smsAuth" maxlength="20" class="required" title="인증번호" />
						<input type="hidden" name="requestToken" id="requestToken" />
					</p>
					<p class="tip">휴대폰 입력시 하이픈( - )을 넣어주세요.</p>
				</div>

				<div class="footer">
					<button type="submit" class="btn btn-orange btn-lg"><span>임시비밀번호 발송</span></button>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<script type="text/javascript">
	function sendAuthNumber() {
		var phoneNumber = $('#phoneNumber').val();
		if ($.validator.isEmpty(phoneNumber)) {
			alert("전화번호를 입력해 주세요.");
			$('#phoneName').focus();
			return;
		}

		var phoneNumber = phoneNumber.replace(/-/g,'');

		var params = {
			'phoneNumber' 	: phoneNumber
		};
		$.post('/auth/sms-request', params, function(response){
			if (!response.isSuccess) {
				alert(response.errorMessage);
			} else {
				$('#requestToken').val(response.data);
			}
		});
	}


	$(function() {
		// 폼 검증..
		$('#lockForm').validator(function() {
		});
	});

</script>

