<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<div class="inner">

	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>비밀번호 변경</span>
		</div>
	</div>
	<div class="content_title">
		<h2 class="title">비밀번호 변경</h2>
		<p>비밀번호의 유효기간이 만료가 되었습니다.</p>
	</div>
	<div class="pw_wrap">
		<div class="pw_area">
			<h3>비밀번호 변경</h3>
			<div class="login_inner">
				<form id="changeForm"  method="POST">
					<div class="login">
						<dl>
							<dt>비밀번호</dt>
							<dd><input type="password" name="password" title="비밀번호"></dd>
							<dt class="mt5">변경할 비밀번호</dt>
							<dd class="mt5"><input type="password" name="changePassword" title="변경할 비밀번호"></dd>
							<dt class="mt5">변경할 비밀번호 재입력</dt>
							<dd class="mt5"><input type="password" name="reChangePassword" title="변경할 비밀번호 재입력"></dd>
						</dl>
						<div class="pw_btn">
							<button type="submit" class="btn btn-lg btn-success" title="변경">변경</button>
							<button type="button" class="btn btn-lg btn-default" title="연장" onclick="delayChangePassword()">연장</button>
						</div>
					</div>
				</form>
			</div><!--//login_wrap E-->
		</div><!--// login E-->
	</div>


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
			$.post('/users/delay-change-password', {}, function (response) {
				if (response.isSuccess) {
					alert('비밀번호 유효기간이 연장이 되었습니다.');
					location.href = "/op_security_logout?target=/";
				} else {
					alert(response.errorMessage);
				}
			});
		}
	}
</script>
</page:javascript>

		