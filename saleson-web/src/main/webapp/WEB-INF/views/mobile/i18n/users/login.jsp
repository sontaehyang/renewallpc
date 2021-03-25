<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<div class="title">
	<h2>로그인</h2>
	<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
</div>
<!-- //title -->

<!-- 내용 : s -->
<div class="con">
	<div class="pop_con">
		<div class="login_tab">
			<ul class="tab_list04">
				<li class="on"><a href="javascript:;">회원</a></li>
				<li><a href="javascript:;">비회원</a></li>
			</ul>
		</div>
		<!-- //login_tab -->
		
		<div class="login_con">
			<div class="member">
				<form method="POST" id="loginForm" action="<c:url value='/op_security_login'/>">
					<input type="hidden" name="target" value="<c:out escapeXml="true" value="${param.target}" />" />
					<input type="hidden" name="popup" value="<c:out escapeXml="true" value="${param.popup}" />" />
					<input type="hidden" name="failureUrl" value="${requestContext.requestUri}?target=<c:out escapeXml="true" value="${param.target}" />&popup=<c:out escapeXml="true" value="${param.popup}" />" />
					<input type="hidden" name="op_login_type" value="ROLE_USER" />
				
					<div class="login_input">
						<div class="input_area">
							<input type="text" name="op_username" id="op_username" class="required" value="${OP_LOGIN_LAST_USERNAME}" title="회원아이디" placeholder="아이디"/>
						</div>
						<div class="input_area">
							<input type="password" name="op_password" id="op_password" class="required" title="비밀번호" placeholder="비밀번호"/>
						</div>
						<div class="login_check">
						 	<input id="save_id" type="checkbox" name="checkbox">
							<label for="save_id">아이디 저장</label>
						</div>
					</div>
				
					<!-- //login_input -->
					<div class="login_btn">
						<button type="submit" class="btn_st1 decision">로그인</button>
						<ul class="find">
							<li><a href="/m/users/find-id">아이디 찾기</a></li>
							<li><a href="/m/users/find-password">비밀번호 찾기</a></li>
						</ul>
						<ul class="easy cf">
							<jsp:include page="../sns-user/naver.jsp"/>
							<%--<jsp:include page="../sns-user/facebook.jsp"/>--%>
							<jsp:include page="../sns-user/kakao.jsp"/>
						</ul>
					</div>
				</form>
				<jsp:include page="/WEB-INF/views/front/i18n/sns-user/sns-response.jsp"/>
				<!-- //login_btn -->
				
				<div class="join_wrap">
					<p>회원가입을 하시면 더 많은 혜택이 있습니다.</p>
					<button class="btn_st1 reverse" type="button" onclick="location.href='/m/users/join-us'">회원가입</button>
				</div>
				<!-- //join_wrap -->
			</div>
			<!-- //member -->
			
			<div class="no_member">
			
				<c:choose>
					<c:when test="${param.target == 'order' || target == '/m/order/step1'}">
						<p class="non_member_txt">비회원으로 구매서비스를 이용하시려면<br> <span>비회원구매 약관동의</span> 하셔야 합니다.<br> 다음 단계로 이동 버튼을 클릭해 주십시오.<br> 비회원 주문 시에는 쿠폰할인 및<br> ${op:message('M00246')}적립 혜택을 받으실 수 없습니다. </p>
						<div class="login_btn"> <button class="btn_st1 submit" onclick="location.href='/m/order/no-member'">다음단계로 이동</button> </div>
						<div class="join_wrap">
							<p>회원가입을 하시면 더 많은 혜택이 있습니다.</p>
							<button class="btn_st1 reverse" type="button" onclick="location.href='/m/users/join-us'">회원가입</button>
						</div>
					</c:when>
					<c:otherwise>
						<form id="guestLoginForm" method="POST">
							<input type="hidden" name="requestToken" id="requestToken" />
							<div class="login_input">
								<div class="input_area">
									<input type="text" name="guestUsername" id="userName" class="required" title="주문자명" placeholder="주문자명" />
								</div>
								<div class="tel_area cf">
									<ul class="cf">
										<li>
											<select name="phoneNumber1" id="phoneNumber1" title="휴대폰번호" class="required">
												<option value="">선택</option>
												<c:forEach items="${op:getCodeInfoList('PHONE')}" var="code">
													<option value="${code.key.id}">${code.label}</option>
												</c:forEach>
											</select>
										</li>
										<li>-</li>
										<li><input type="text" name="phoneNumber2" id="phoneNumber2" title="휴대폰번호" class="required _number tel" maxlength="4" placeholder="휴대폰번호" /></li>
										<li>-</li>
										<li><input type="text" name="phoneNumber3" id="phoneNumber3" title="휴대폰번호" class="required _number tel" maxlength="4" placeholder="휴대폰번호" /></li>
									</ul>
									<a href="javascript:sendAuthNumber();" class="btn_st1 decision" title="인증번호 전송">인증번호 전송</a>
								</div>
								<div class="input_area cert cf">
									<input type="text" id="smsAuthNumber" title="인증번호" class="_number" placeholder="인증번호" />
									<button type="submit" class="btn_st1 decision" title="확인하기">확인하기</button>
								</div>
							</div>
							<!-- //login_input -->
						</form>
						
						<div class="desc">
							<p>
								비회원 주문시 입력한 이름과 휴대폰번호로 로그인해 주세요.<br>
								리뉴올PC 고객센터 : <span>1544-2432</span>
							</p>
						</div>
					</c:otherwise>
				</c:choose>
				
				<!-- //join_wrap -->
			</div>
			<!-- //no_member -->
			
		</div>
		<!-- //login_con -->
		
	</div>
	<!-- //pop_con -->
</div>


<page:javascript>
<script type="text/javascript" src="<c:url value="/content/modules/op.sms.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.social.js" />"></script>
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
					$('#guestLoginForm').attr('action', '/m/users/guestLogin');
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

	var errorCode = '<c:out escapeXml="true" value="${param.error}" />';

	if (errorCode != "") {
		var errorMsg = '아이디(메일) 비밀번호가 다릅니다. 다시 확인해주십시오';

		if (errorCode == '4') {
			errorMsg = '계정이 잠겨 있습니다.';
		}

		alert(errorMsg);
	}
	
	// 모바일 키페드 셋팅
	//Common.setMobileKeypad();
	
	//Common.alertLoginError('<c:out escapeXml="true" value="${param.error}" />');
});

function nextStep() {
	
	if ($(':checkbox[name="ae1"]').prop('checked') == false) {
		alert('이용약관 및 개인정보 수집이용에 동의후 구매 진행이 가능합니다.');
		return;
	}
	
	location.href = '/m/order/step1';
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

function sendAuthNumber() {
	
	var isConfirm = true;
	if ($.validator.isEmpty($('#requestToken').val()) == false) {
		if (!confirm("인증번호를 다시 받으시겠습니까?")) {
			isConfirm = false;
		}
	}
	
	if (isConfirm == true) {
		if (Sms.requestAuthNumber() == true) {
			$('#userName').prop('readonly', true);
			$('#phoneNumber1').prop('readonly', true);
			$('#phoneNumber2').prop('readonly', true);
			$('#phoneNumber3').prop('readonly', true);
		}
	}
}

function showLogin(type, aTag) {
	
	if(type == 'member') {
		$('.title h2').text('로그인');
	}else if(type == 'non_member') {
		$('.title h2').text('비회원 로그인');
	}
	
	$('div.op-login-form').hide();
	$('div.'+type).show();
	
	$('div.op-login-tab ul li').removeClass('on');
	$('li#'+type+'ATag').addClass('on');
	
}

function openerGoUrl(url) {
	opener.location.href= url;
	self.close();
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