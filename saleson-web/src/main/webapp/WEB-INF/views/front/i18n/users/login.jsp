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
			<span>로그인</span> 
		</div>
	</div><!-- // location_area E -->
	
	<div class="content_title"> 
		<h2 class="title">로그인</h2> 
		<p>리뉴올PC를 방문해주셔서 감사합니다. 다양한 혜택과 편리한 이용을 위해 로그인해주세요.</p>
	</div>
	
	<div class="login_wrap">
		<div class="login_area member">
			<h3>회원 로그인</h3>
			
			<div class="login_inner">
				<form id="loginForm" action="<c:url value='/op_security_login'/>" method="POST">
					<page:csrf />
					<input type="hidden" name="target" value="<c:out escapeXml="true" value="${param.target}" />" />
					<input type="hidden" name="popup" value="<c:out escapeXml="true" value="${param.popup}" />" />
					<input type="hidden" name="failureUrl" value="${requestContext.requestUri}?target=<c:out escapeXml="true" value="${param.target}" />&popup=<c:out escapeXml="true" value="${param.popup}" />" />
					<input type="hidden" name="op_login_type" value="ROLE_USER" />
					<div class="login">
						<dl>
							<dt>회원아이디</dt>
							<dd><input type="text" name="op_username" id="op_username" maxlength="100" class="required ime-mode-disabled" value="${OP_LOGIN_LAST_USERNAME}" title="회원아이디" /></dd>
							<dt class="mt5">비밀번호</dt>
							<dd class="mt5"><input type="password" name="op_password" id="op_password" class="required" title="비밀번호"></dd>
						</dl>
						<button type="submit" class="btn btn-submit btn-login" title="로그인">로그인</button>
					</div>
					<div class="login_check">
						<span>
							<input type="checkbox" id="save_id" name="op_remember_me" value="true">
							<label for="save_id">아이디 저장</label>
						</span>
						<c:choose>
							<c:when test="${param.popup == '1'}">
								<span><a href="javascript:;" onclick="openerGoUrl('/users/find-id')">아이디(이메일) 찾기</a></span>
								<span><a href="javascript:;" onclick="openerGoUrl('/users/find-password')">비밀번호 찾기</a></span>
							</c:when>
							<c:otherwise>
								<span><a href="javascript:;" onclick="Common.popup('/users/find-id', 'find-id', '526', '370', 0)">아이디(이메일) 찾기</a></span>
								<span><a href="javascript:;" onclick="Common.popup('/users/find-password', 'find-password', '526', '415', 0)">비밀번호 찾기</a></span>
							</c:otherwise>
						</c:choose>
					</div>
				</form>
				<dl class="sns_login">
					<dt>SNS 로그인</dt>
					<dd>
						<jsp:include page="../sns-user/naver.jsp"/>
						<%--<jsp:include page="../sns-user/facebook.jsp"/>--%>
						<jsp:include page="../sns-user/kakao.jsp"/>
					</dd>
				</dl>
				<jsp:include page="../sns-user/sns-response.jsp"/>
			</div><!--//login_wrap E-->
			<div class="login_bottom">
				<p>
					아직도 회원이 아니세요?<br>
					회원가입을 하시면 더 많은 혜택을 만나보실 수 있습니다.
				</p>
				<a href="/users/join-us" class="btn btn-default btn-join" title="회원가입">회원가입</a>
			</div><!--//login_bottom E-->			
		</div>
		
		<div class="login_area nomember">
			<h3>비회원 로그인(주문조회)</h3>
			
			<div class="login_inner">
				<form id="guestLoginForm" method="POST">
					<input type="hidden" name="requestToken" id="requestToken" />
					<input type="hidden" name="target" value="<c:out escapeXml="true" value="${param.target}" />" />
					<input type="hidden" name="popup" value="<c:out escapeXml="true" value="${param.popup}" />" />
					<input type="hidden" name="failureUrl" value="${requestContext.requestUri}?target=<c:out escapeXml="true" value="${param.target}" />&popup=<c:out escapeXml="true" value="${param.popup}" />" />
					<input type="hidden" name="op_login_type" value="ROLE_USER" />
					<div class="login">
						<dl>
							<dt>주문자명</dt>
							<dd><input type="text" name="guestUsername" id="userName" title="주문자명" class="required"></dd>
							<dt class="mt5">휴대폰번호</dt>
							<dd class="mt5">
								<ul class="login_hp">
									<li><input type="text" name="phoneNumber1" id="phoneNumber1" title="휴대폰번호" class="required _number hpbox" maxlength="3" /></li>
									<li class="connect">-</li>
									<li><input type="text" name="phoneNumber2" id="phoneNumber2" title="휴대폰번호" class="required _number hpbox" maxlength="4" /></li>
									<li class="connect">-</li>
									<li><input type="text" name="phoneNumber3" id="phoneNumber3" title="휴대폰번호" class="required _number hpbox" maxlength="4" /></li>
								</ul>
								<a href="javascript:sendAuthNumber();" class="btn btn-w102 btn-success" title="인증번호 전송">인증번호 전송</a>
							</dd>
							<dt class="mt5">인증번호</dt>
							<dd class="mt5"><input type="text" id="smsAuthNumber" title="인증번호"> 
								<button type="submit" class="btn btn-w102 btn-default" title="확인하기">확인하기</button>
							</dd>
						</dl> 
					</div>
				</form> 
			</div><!--//login_wrap E-->
			<div class="login_bottom">
				<p>
					비회원 주문시 입력한 이름과 휴대폰번호로 로그인해 주세요.<br>
					리뉴올PC 고객센터 : <span>1544-2432</span>
				</p> 
			</div><!--//login_bottom E-->
		</div>				
	</div>
	
	<div class="beauty_benefits">
		<div class="hide">
			<p> 회원만의 특별한 혜택</p>
			<ul>
				<li>회원가입 축하 700 Point</li>
				<li>첫 구매감사 100 Point</li>
				<li>이용후기 작성 100 Point</li>
				<li>매일매일 로그인 10 Point</li>
				<li>1:1 문의/상담 맞품형 상담 서비스</li>
			</ul>
		</div>
	</div> 
	
</div>

<page:javascript>	
<script type="text/javascript" src="<c:url value="/content/modules/op.sms.js" />"></script>
<script type="text/javascript">
$(function() {
	var $loginId = $('#op_username');
	$loginId.val() == '' ? $loginId.focus() : $('#op_password').focus();
	
	//쿠키에 저장된 아이디가 존재할 시 불러온다.
	getIdFromCookie();
	
	$('#loginForm').validator(function() {
		
		// 아이디 저장 여부 확인
		isCheckedSaveId();
	}); 
	
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
					$('#guestLoginForm').attr('action', '/users/guestLogin');
					authCheck = true;
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

		alert(errorMsg);
	}
	
	//Common.alertLoginError('<c:out escapeXml="true" value="${param.error}" />');
	
});

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

function openerGoUrl(url) {
	opener.location.href= url;
	self.close();
}

function sendAuthNumber() {
	
	var isConfirm = true;
	if ($.validator.isEmpty($('#requestToken').val()) == false) {
		if (!confirm("인증번호를 다시 받으시겠습니까?")) {
			isConfirm = false;
		}
	}
	
	if (isConfirm == true) {
		if(Sms.requestAuthNumber() == true) {
			$('#userName').prop('readonly', true);
			$('#phoneNumber1').prop('readonly', true);
			$('#phoneNumber2').prop('readonly', true);
			$('#phoneNumber3').prop('readonly', true);		
		}
	}
}

function getIdFromCookie() {
	var $loginId = $('#op_username');
	var $chkRememberUserId = $('#save_id');
	
	if (typeof $.cookie('REMEMBER_USERID') != "undefined" && $.cookie('REMEMBER_USERID') != "undefined") {
		$loginId.val($.cookie('REMEMBER_USERID'));
	}
	
	if (typeof $.cookie('CHK_REMEMBER_USERID') != "undefined" && $.cookie('CHK_REMEMBER_USERID') == 'true') {
		$chkRememberUserId.prop("checked",true);
	} else {
		$chkRememberUserId.prop("checked", false);
	}
}

function isCheckedSaveId() {
	var $loginId = $('#op_username');
	var $chkRememberUserId = $('#save_id');
	
	//쿠키 추가
	if ($chkRememberUserId.prop("checked") == true) {
		$.cookie('REMEMBER_USERID', $loginId.val());
		$.cookie('CHK_REMEMBER_USERID', $chkRememberUserId.prop("checked"));
	} else {
		$.removeCookie('REMEMBER_USERID');
		$.removeCookie('CHK_REMEMBER_USERID');
	}
}
</script>
</page:javascript>

		