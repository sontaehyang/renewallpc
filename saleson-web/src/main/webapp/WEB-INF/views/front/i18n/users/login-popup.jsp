<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<!-- 팝업사이즈 550*435-->
<div class="popup_wrap">
	<h1 class="popup_title">로그인</h1>
	<div class="popup_contents"> 
		<div class="login_tab">
			<ul>
				<li><a href="javascript:show('user', 0)" class="tab on">회원</a></li>
				<li><a href="javascript:show('nomember', 1)" class="tab">비회원</a></li>	
			</ul>
		</div><!--//login_tab E-->
		
		<!-- 회원 로그인 -->
		<div class="login_wrap  user-view" style="display:block;">
		<form id="loginForm" action="/op_security_login" method="POST">
            <page:csrf />
			<c:set var="target" value="${target}" />
			<c:if test="${param.target == 'order'}">
				<c:set var="target" value="/users/popup-login" />
			</c:if>
			<input type="hidden" name="target" value="${target}" />
			<input type="hidden" name="popup" value="<c:out escapeXml="true" value="${param.popup}" />" />
			<input type="hidden" name="failureUrl" value="${requestContext.requestUri}?target=<c:out escapeXml="true" value="${param.target}" />&popup=<c:out escapeXml="true" value="${param.popup}" />" />
			<input type="hidden" name="op_login_type" value="ROLE_USER" />
				<div class="login">
					<dl>
						<dt>회원아이디</dt>
						<dd><input type="text" name="op_username" id="op_username" maxlength="100" class="required ime-mode-disabled" value="${OP_LOGIN_LAST_USERNAME}" title="회원아이디" /></dd>
						<dt class="mt10">비밀번호</dt>
						<dd class="mt10"><input type="password" name="op_password" id="op_password" class="required" title="비밀번호" /></dd>
					</dl>
					<button type="submit" class="btn btn-submit btn-login">로그인</button>
				</div>
				<div class="login_check">
					<span><input type="checkbox" id="op_remember_me" name="op_remember_me" value="true" checked="checked" /> <label for="op_remember_me">자동로그인</label></span>
					<span><a href="javascript:;" onclick="Common.popup('/users/find-id', 'find-id', '526', '370', 0)">아이디(이메일) 찾기</a></span>
					<span><a href="javascript:;" onclick="Common.popup('/users/find-password', 'find-password', '526', '415', 0)">비밀번호 찾기</a></span>
				</div> 
				<dl class="sns_login">
					<dt>SNS 로그인</dt>
					<dd>
						<jsp:include page="../sns-user/naver.jsp"/>
						<%--<jsp:include page="../sns-user/facebook.jsp"/>--%>
						<jsp:include page="../sns-user/kakao.jsp"/>
					</dd>
				</dl>
				<div class="login_bottom">
					<p>
						아직도 회원이 아니세요?<br>
						회원가입을 하시면 더 많은 혜택을 만나보실 수 있습니다.
					</p>
					<a href="javascript:openerGoUrl('/users/join-us')" class="btn btn-default btn-join">회원가입</a>
				</div>
			</form>
		</div><!--//login_wrap E-->
		<jsp:include page="../sns-user/sns-response.jsp"/>
		
		<!-- 비회원 로그인 -->
		<div class="login_wrap nomember-view no_member" style="display:none;">
			<c:choose>
				<c:when test="${param.target == 'order'}">
					<div class="nomember">
						<p> 비회원으로 구매서비스를 이용하시려면 비회원구매 약관동의하셔야 합니다. </p>
						<p>	다음 단계로 이동 버튼을 클릭해 주십시오.</p>
						<p>비회원 주문 시에는 쿠폰할인 및 ${op:message('M00246')} 적립 혜택을 받으실 수 없습니다.  </p>
						<a href="javascript:openerGoUrl('/order/no-member')">다음단계로 이동</a>
					</div> 
					
				</c:when>
				<c:otherwise>
					
						<form id="guestLoginForm" method="POST">
							<input type="hidden" name="requestToken" id="requestToken" />
							<input type="hidden" name="target" value="<c:out escapeXml="true" value="${param.target}" />" />
							<input type="hidden" name="popup" value="<c:out escapeXml="true" value="${param.popup}" />" />
							<input type="hidden" name="failureUrl" value="${requestContext.requestUri}?target=<c:out escapeXml="true" value="${param.target}" />&popup=<c:out escapeXml="true" value="${param.popup}" />" />
							<input type="hidden" name="op_login_type" value="ROLE_USER" />						
							<div class="login">
								<dl>
									<dt>주문자명</dt>
									<dd><input type="text" name="guestUsername" id="userName" title="주문자명"></dd>
									<dt class="mt8">휴대폰번호</dt>
									<dd class="mt8">
										<ul class="login_hp">
											<li><input type="text" title="휴대폰번호" name="phoneNumber1" id="phoneNumber1" class="required _number hpbox" maxlength="3"></li>
											<li class="connect">-</li>
											<li><input type="text" title="휴대폰번호" name="phoneNumber2" id="phoneNumber2" class="required _number hpbox" maxlength="4"></li>
											<li class="connect">-</li>
											<li><input type="text" title="휴대폰번호" name="phoneNumber3" id="phoneNumber3" class="required _number hpbox" maxlength="4"></li>
										</ul>
										<a href="javascript:sendAuthNumber();" class="btn btn-w102 btn-success">인증번호 전송</a>
									</dd>
									<dt class="mt10">인증번호</dt>
									<dd class="mt10"><input type="text" id="smsAuthNumber" title="인증번호">
										<button type="submit" class="btn btn-w102 btn-default">확인하기</button>   
									</dd>
								</dl> 
							</div>
						</form>

					
				</c:otherwise>
			</c:choose>
			<div class="login_bottom">
				<p>
					비회원 주문시 입력한 이름과 휴대폰번호로 로그인해 주세요.<br>
					리뉴올PC 고객센터 : <span>1544-2432</span>
				</p> 
			</div>
			<%-- 확인필요
			<div class="login_bottom">
				<p>
					아직도 회원이 아니세요?<br>
					회원가입을 하시면 더 많은 혜택을 만나보실 수 있습니다.
				</p>
				<a href="javascript:openerGoUrl('/users/confirm')" class="btn btn-default btn-join">회원가입</a>
			</div>
			 --%>
		</div><!--//login_wrap E-->
	</div><!--//popup_contents E-->
	 
	
	<a href="javascript:self.close()" class="popup_close">창 닫기</a>
</div>


<page:javascript>
<script type="text/javascript" src="<c:url value="/content/modules/op.sms.js" />"></script>
<script type="text/javascript">

$(function() {
	
	var $loginId = $('#op_username');
	$loginId.val() == '' ? $loginId.focus() : $('#op_password').focus();
	
	$('#loginForm').validator();
	
	$('#guestLoginForm').validator(function() {
		var requestToken = $('#requestToken').val();
		var smsAuthNumber = $('#smsAuthNumber').val();

		// 주문내역이 있는지 조회
		if (isSendAuthNumber() == false) {
			return false;
		}
		
		if ($.validator.isEmpty(requestToken)) {
			sendAuthNumber();
			return false;
		} else {
			var authCheck = false;
			var url = '/users/authCheck/' + smsAuthNumber + "/" + requestToken;
			$.post(url, $('#guestLoginForm').serialize(), function(response) {
				if (response.isSuccess) {
					
					/*
					$('#guestLoginForm').attr('action', '/users/guestLogin');
					authCheck = true;
					*/
					opener.location.href="/users/guestLogin?"+$('#guestLoginForm').serialize();
					return false;
				} else {
					alert(response.errorMessage);
					$('#smsAuthNumber').focus();
				}
			});
			
			if(!authCheck){
				return false;
			}
		}
	});
	
	$('#passwordForm').validator();
	
	var errorCode = '<c:out escapeXml="true" value="${param.error}" />';

	if (errorCode != "") {
		var errorMsg = '아이디(메일) 비밀번호가 다릅니다. 다시 확인해주십시오';

		if (errorCode == '4') {
			errorMsg = '계정이 잠겨 있습니다.';
		}

		setTimeout(function() {
			alert(errorMsg);
		}, 100);
	}
	
	//Common.alertLoginError('<c:out escapeXml="true" value="${param.error}" />');
	
});

function show(mode, eq) {
	$('div.login_wrap').hide();
	$('a.tab').removeClass('on').eq(eq).addClass('on');
	$('div.' + mode + '-view').show();
}

function openerGoUrl(url) {
	opener.location.href = url;
	self.close();
}

function sendAuthNumber() {
	
	var isConfirm = true;
	
	// 주문내역이 있는지 조회
	if (isSendAuthNumber() == false) {
		isConfirm = false;
	}
	
	
	if ($.validator.isEmpty($('#requestToken').val()) == false) {
		if (!confirm("인증번호를 다시 받으시겠습니까?")) {
			isConfirm = false;
		}
	}
	
	if (isConfirm == true) {
		if(Sms.requestAuthNumber() != false) {
			$('#userName').prop('readonly', true);
			$('#phoneNumber1').prop('readonly', true);
			$('#phoneNumber2').prop('readonly', true);
			$('#phoneNumber3').prop('readonly', true);		
		}
	}
}

function isSendAuthNumber() {
	var isSend = true;
	$.post('/order/guest-order-count', $('#guestLoginForm').serialize(), function(resp){
		
		if (!resp.isSuccess) {
			isSend = false;
			alert(resp.errorMessage);
		}
		
	}, 'json');
	
	return isSend;
}


function getTarget(){
	
	var target = '<c:out escapeXml="true" value="${param.target}" />';
	return target.replace('-after-login',''); 
}
</script>
</page:javascript>