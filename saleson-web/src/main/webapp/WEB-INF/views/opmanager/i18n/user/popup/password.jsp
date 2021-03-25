<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<!-- 본문 -->
<div class="popup_wrap">
	<h1 class="popup_title">비밀번호 변경</h1>
	<form method="post" id="passwordForm">
		<div class="popup_contents pb0">
		
			<div class="calendar_popup" style="padding-top: 20px; text-align: center;">
				
				<c:choose>
					<c:when test="${empty mailConfig}">
						<p class="mb5 text-left">※환경설정 > Mail설정 > PW확인을 확인해주세요</p>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${mailConfig.buyerSendFlag == 'Y'}">
								<p class="mb5 text-left">※Email : ${user.email}</p>
							</c:when>
							<c:otherwise>
								<p class="mb5 text-left">※환경설정 > Mail설정 > PW확인의 "사용자 발송상태"를 확인해주세요</p>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			
				<c:choose>
					<c:when test="${empty smsConfig}">
						<p class="mb5 text-left">※환경설정 > SMS설정 > PW확인을 확인해주세요</p>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${smsConfig.buyerSendFlag == 'Y'}">
								<p class="mb5 text-left">※SMS : ${user.userDetail.phoneNumber}</p>
							</c:when>
							<c:otherwise>
								<p class="mb5 text-left">※환경설정 > SMS설정 > PW확인의 "사용자 발송상태"를 확인해주세요</p>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				<p class="popup_btns">
					<button type="button" class="btn btn-active email w250 mb10"><span>임시 비밀번호 Email 전송</span></button><br/>
					<button type="button" class="btn btn-active sms w250"><span>임시 비밀번호 SMS 전송</span></button>
				</p>
				
			</div>
			
			<div class="calendar_popup" style="padding-top: 20px; text-align: center;">
				
				<p class="text-left">
					비밀번호 : <input type="password" name="password" class="required _password _duplicated" title="비밀번호" />
					<button type="submit" class="btn btn-active"><span>비밀번호 직접변경</span></button>
				</p>
			</div>
			
			
		</div>
	</form>
		
	<a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
	$(function() {
		
		$('#passwordForm').validator(function() {
			
			if (!confirm("입력하신 비밀번호로 변경 하시겠습니까?\n비밀번호 직접 변경의 경우 안내 메시지가 발송되지 않습니다.")) {
				return false;
			}
		});
		
		$(".email").on("click",function(){
			Common.confirm("비밀번호를 재발급 하시겠습니까?\n임시 비밀번호가 회원의 이메일주소로 발송됩니다.", function() {
				location.href = '/opmanager/user/popup/password/${user.userId}/email';
			});
		});
		
		$(".sms").on("click",function(){
			Common.confirm("비밀번호를 재발급 하시겠습니까?\n임시 비밀번호가 회원의 SMS로 발송됩니다.", function() {
				location.href = '/opmanager/user/popup/password/${user.userId}/sms';
			});
		});
	});
</script>