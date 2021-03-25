<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div id="container" class="login">

	<div class="login_area">
		<form id="changeForm" method="POST" action="/seller/login-change-password">
			<fieldset>
				<legend class="hidden">로그인</legend>

				<h2>패스워드 변경</h2> <!-- 관리자 로그인 -->
				<p class="guide">패드워드 변경후 관리자 로그인 및 진입이 가능합니다.</p>

				<div class="inputs">
					<p>
						<span class="label" style="width: 165px;">${op:message('M00081')}</span> <!-- 아이디 -->
						<input type="text" name="loginId" id="loginId" maxlength="40" class="required ime-mode-disabled" title="아이디" />
					</p>
					<p>
						<input hidden name="lock" value="${lock}"/>
						<span class="label" style="width: 165px;">현재 ${op:message('M00150')}</span> <!-- 비밀번호 -->
						<input type="password" name="password" maxlength="20" class="required" title="현재 ${op:message('M00150')}" />
					</p>
					<p>
						<span class="label" style="width: 165px;">변경할 ${op:message('M00150')}</span> <!-- 비밀번호 -->
						<input type="password" name="changePassword" maxlength="20" class="required _password _duplicated" title="변경할 ${op:message('M00150')}" />
					</p>
					<p>
						<span class="label" style="width: 165px;">변경할 ${op:message('M00150')} 재입력</span> <!-- 비밀번호 -->
						<input type="password" name="reChangePassword" maxlength="20" class="required _password _duplicated" title="변경할 ${op:message('M00150')} 재입력" />
					</p>
				</div>
				<div class="footer">
					<button type="submit" class="btn btn-orange btn-lg"><span>변경</span></button>
				</div>
			</fieldset>
		</form>
	</div>
</div>

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
</script>