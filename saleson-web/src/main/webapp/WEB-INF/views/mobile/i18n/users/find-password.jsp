<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>



<%-- 
	<div class="member_join">
		<h3 class="title">비밀번호 찾기</h3>
		<div class="member_guide">
			<p>
				회원가입 시 등록한 본인명의 휴대폰 번호로 인증 후<br> 
				<span>비밀번호를 새로 설정</span>하실 수 있습니다.
			</p>
		</div><!--//member_guide E-->
		
		<form id="authForm" method="post">
			<input type="hidden" name="requestToken" id="requestToken" />
			<div class="table_write">
				<table cellpadding="0" cellspacing="0">
					<colgroup>
						<col style="width:35%;">
						<col style="width:65%;">
					</colgroup>
					<tbody>
						<tr>
							<th>아이디(이메일)</th>
							<td><input type="text" class="full _email required" id="email" name="email" title="아이디"></td> 
						</tr>
						<tr>
							<th>이름</th>
							<td><input type="text" class="full required" id="userName" name="userName" title="이름"></td> 
						</tr>
						<tr>
							<th>휴대폰번호</th>
							<td>
								<div class="hp_write"> 
									<div>
										<input type="text" id="phoneNumber1" name="phoneNumber1" title="휴대폰번호" class="number required _number" maxlength="3">
									</div> 
									<span class="connect">-</span>
									<div>
										<input type="text" id="phoneNumber2" name="phoneNumber2" title="휴대폰번호" class="number required _number" maxlength="4">	
									</div> 
									<span class="connect">-</span>
									<div>
										<input type="text" id="phoneNumber3" name="phoneNumber3" title="휴대폰번호" class="number required _number" maxlength="4">
									</div> 	 
		 						</div>
								<a href="javascript:sendAuthNumber();" class="number">인증번호전송</a>
							</td> 
						</tr> 
						<tr>
							<th>인증번호</th>
							<td>
								<input type="text" class="full" id="smsAuthNumber" title="인증번호" disabled="disabled" placeholder="인증번호를 입력해 주세요.">
							</td> 
						</tr>  
					</tbody>
				</table>		
			</div><!--//table_write E-->	
			
			<div class="btn_area wrap_btn">
				<div class="sale division-1" style="display:block"> 
					<div>
						<div>
							<button type="submit" class="btn btn_on btn-w100">확인</button>
						</div>
					</div>
				</div>				 
			</div>
		</form>
	</div><!--// member_join E--> --%>
	

	<!-- 내용 : s -->
	<div class="con">
		<div class="pop_title">
			<h3>비밀번호 찾기</h3>
			<a href="/m" class="history_back">뒤로가기</a>
		</div>
		<!-- //pop_title -->
		<form id="authForm" method="post">
			<p class="code_tit">비밀번호를 잊으셨나요?<br/>회원가입 시 등록한 본인명의 휴대폰 번호로 인증 후 비밀번호를 새로 설정하실 수 있습니다.</p>
			<div class="bd_table typeB">
				
					<input type="hidden" name="requestToken" id="requestToken" />
					<ul class="del_info">
						<li>
							<label for="a1" class="del_tit t_gray star">아이디</label>
							<div class="input_area">
								<input type="text" class=" full required" id="loginId" name="loginId" title="아이디">
							</div>
						</li>
						<li>
							<label for="a2" class="del_tit t_gray star">이름</label>
							<div class="input_area">
								<input type="text" class="full required " id="userName" name="userName" title="이름">
							</div>
						</li>
						<li>
							<label for="a3" class="del_tit t_gray star">휴대폰</label>
							
							
								<div class="in_td">
									<div class="num">
										<div class="in_td">
											<div class="input_area">
												<input type="text" id="phoneNumber1" name="phoneNumber1" title="휴대폰번호" class="number required _number " maxlength="3">
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<input type="text" id="phoneNumber2" name="phoneNumber2" title="휴대폰번호" class="number required _number " maxlength="4">
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<input type="text" id="phoneNumber3" name="phoneNumber3" title="휴대폰번호" class="number required _number " maxlength="4">
											</div>
										</div>
									</div>
								</div>
								<div class="in_td agree">
									<button type="button" class="btn_st3 t_lgray02 b_lgray" onclick="sendAuthNumber();">인증번호 전송</button>
								</div>
						</li>
						<li>
							<label for="a4" class="del_tit t_gray star">인증번호</label>
							<div class="input_area">
								<input type="text" class="full " id="smsAuthNumber" title="인증번호" disabled="disabled" placeholder="인증번호를 입력해 주세요.">
								<span class="certification e-info" style="display: none;">입력하신 번호로 인증번호가 발송되었습니다.</span>
			 					<span class="certification e-info" style="display: none;"></span>
			 					<span class="certification e-info" style="display: none;">인증번호가 잘못 입력되었습니다. 다시 확인해 주세요.</span>
								
							</div>
						</li>
					</ul>
				
			</div>
			<div class="btn_area02">
				<button class="btn_st1 decision" type="submit" title="확인">확인</button>
			</div>
		
		</form>

	</div>
	<!-- 내용 : e -->
	
	

<page:javascript>
<script type="text/javascript" src="<c:url value="/content/modules/op.sms.js" />"></script>
<script type="text/javascript">
$(function(){
	$('#authForm').validator(function(){
		var requestToken = $('#requestToken').val();
		var smsAuthNumber = $('#smsAuthNumber').val();
		if ($.validator.isEmpty(requestToken)) {
			sendAuthNumber();
			return false;
		} else {
			var authCheck = false;
			var url = '/users/authCheck/' + smsAuthNumber + "/" + requestToken;
			$.post(url, $('#authForm').serialize(), function(response){
				if (response.isSuccess) {
					$('#authForm').attr('action', '/m/users/find-password');
					authCheck = true;
				} else {
					alert(response.errorMessage);
					$('.certification').hide();
					$('.certification').eq(2).show();
					$('#smsAuthNumber').focus();
				}
			});
			
			if(!authCheck){
				return false;
			}
		}
	});
});

function sendAuthNumber() {

	var loginId = $('#loginId').val();
	var userName = $('#userName').val();
	var phoneNumber1 = $('#phoneNumber1').val();
	var phoneNumber2 = $('#phoneNumber2').val();
	var phoneNumber3 = $('#phoneNumber3').val();

	if ($.validator.isEmpty(loginId)) {
		alert("아이디를 입력해 주세요.");
		$('#loginId').focus();
		return;
	}

	if ($.validator.isEmpty(userName)) {
		alert("이름을 입력해 주세요.");
		$('#userName').focus();
		return;
	}

	if ($.validator.isEmpty(phoneNumber1)) {
		alert("휴대폰번호를 입력해 주세요.");
		$('#phoneNumber1').focus();
		return;
	}

	if ($.validator.isEmpty(phoneNumber2)) {
		alert("휴대폰번호를 입력해 주세요.");
		$('#phoneNumber2').focus();
		return;
	}

	if ($.validator.isEmpty(phoneNumber3)) {
		alert("휴대폰번호를 입력해 주세요.");
		$('#phoneNumber3').focus();
		return;
	}

	var phoneNumber = phoneNumber1 + '-' + phoneNumber2 + '-' + phoneNumber3;
	
	var params = {
		'userName'		: userName,
		'phoneNumber' 	: phoneNumber,
		'loginId'		: loginId
	};
	
	$.post('/users/check-account', params, function(response){
		if (!response.isSuccess) {
			alert("입력하신 정보와 일치하는 회원정보가 없습니다.");
			$('#email').focus();
		} else {
			
			var isConfirm = true;
			if ($.validator.isEmpty($('#requestToken').val()) == false) {
				if (!confirm("인증번호를 다시 받으시겠습니까?")) {
					isConfirm = false;
				}
			}
			if (isConfirm == true) {
				Sms.requestAuthNumber();
				$('#userName').prop('readonly', true);
				$('#phoneNumber1').prop('readonly', true);
				$('#phoneNumber2').prop('readonly', true);
				$('#phoneNumber3').prop('readonly', true);
				$('.certification').hide();
				$('.certification').eq(0).show();
			}
		}
	});
}	
</script>
</page:javascript>