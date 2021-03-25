<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>	
<style type="text/css">
	.del_tit {position: inherit;}
	.btn_st2 {margin-top: 5px;}
	.pw_sh {margin-top: 10px;}
</style>
		<!-- 내용 : s -->
		<div class="con">
			<div class="pop_title">
				<h3>아이디 찾기</h3>
				<a href="javascript:history.go(-1);" class="history_back">뒤로가기</a>
			</div>
			<div class="pop_con">
				<form id="authForm" method="post">
					<input type="hidden" name="requestToken" id="requestToken" />
					<div class="join_area">
						<p class="ob_txt">
							회원가입 시 등록한 <br>본인명의 휴대폰 번호로 인증 후<br>ID를 확인하실수 있습니다.
						</p>
						<div class="bd_table typeB">
							<ul>
								<li>
									<label class="del_tit t_gray">이름</label>
									<div class="input_area">
										<input type="text" name="userName" id="userName" class="required" title="이름">
									</div>
								</li>
								<li>
									<label class="del_tit t_gray star">휴대폰번호</label>
									<div class="num">
										<div class="in_td">
											<div class="input_area">
												<input type="text" name="phoneNumber1" id="phoneNumber1" class="required _number" maxlength="3" title="휴대폰번호">							
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<input type="text" name="phoneNumber2" id="phoneNumber2" class="required _number" maxlength="4" title="휴대폰번호">							
											</div>
											<div class="in_td dash"></div>
											<div class="input_area">
												<input type="text" name="phoneNumber3" id="phoneNumber3" class="required _number" maxlength="4" title="휴대폰번호">							
											</div>
										</div>
									</div>
									<div class="in_td bar">
										<button type="button" onclick="sendAuthNumber()" class="btn_st2 t_lgray02">인증번호 발송</button>	 
									</div>
								</li>
								<li>
									<label class="del_tit t_gray">인증번호</label>
									<div class="input_area">
										<input type="text" name="smsAuthNumber" id="smsAuthNumber" title="인증번호" disabled="disabled" placeholder="인증번호를 입력해 주세요.">
									</div>
								</li>
							</ul>
						</div><!--//bd_table typeB E-->
						<div class="pw_sh">
							<span>비밀번호를 잊으셨나요?</span>
							<button class="btn_st1" type="button" title="비밀번호 찾기" onclick="location.href='/m/users/find-password'">비밀번호 찾기</button>
						</div>
					</div>
					<div class="btn_wrap">
						<button class="btn_st1 reset" type="button" onclick="location.href='/m/users/login'">취소</button>
						<button class="btn_st1 decision" type="submit">확인</button>
					</div>
				</form>
			</div>
			<!-- //pop_title -->
			<!-- 서비스 하고 있지 않아서 주석 처리 2017-06-12 yulsun.yoo 
			<div class="pop_con">

				<div class="join_area">
					<p class="ob_txt">
						회원님의 아이디를 찾기 위해서는<br/>본인임을 인증한 후 확인이 가능합니다.<br/>회원님의 인증할 방법을 선택하여 입력해주세요.
						회원가입 시 등록한 <br>본인명의 휴대폰 번호로 인증 후<br><span>ID(E-mail)를 확인</span>하실수 있습니다. 
					</p>
					<div class="btn_area">
						<button class="btn_st1 submit ph" type="button" title="휴대폰 인증">휴대폰 인증</button>
						<button class="btn_st1 submit pin" type="button" title="아이핀 인증">아이핀 인증</button>
					</div>
					<div class="pw_sh">
						<span>비밀번호를 잊으셨나요?</span>
						<button class="btn_st1" type="button" title="비밀번호 찾기" onclick="location.href='/m/users/find-password'">비밀번호 찾기</button>
					</div>
					<div class="ob_notice">
						<span>본인 인증 서비스는 신용정보 취급 정식 인가 업체인 서울신용평가정보(주)에서 제공 합니다.</span>
						<p>개인 정보를 도용하는 경우, 서비스 이용에 제한이 있을 수 있습니다. 본인 인증에 문제가 있는 경우, 서울신용평가정보(주) SIREN24 고객센터 (1577-10 06)로 문의해 주시기 바랍니다.</p>
					</div>
				</div>
				<div class="btn_wrap">
					<button class="btn_st1 reset">취소</button>
					<button class="btn_st1 decision">확인</button>
				</div>
				//join_area
				
			</div> -->
			<!-- //pop_con -->

		</div>
		<!-- 내용 : e -->
		
	<%-- <div class="member_join">
		<h3 class="title">아이디(이메일) 찾기</h3>
		<div class="member_guide">
			<p> 
				회원가입 시 등록한 <br>
				본인명의 휴대폰 번호로 인증 후<br>
				<span>ID(E-mail)를 확인</span>하실수 있습니다. 
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
					$('#authForm').attr('action', '/m/users/find-id');
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
});

function sendAuthNumber() {

	var userName = $('#userName').val();
	var phoneNumber1 = $('#phoneNumber1').val();
	var phoneNumber2 = $('#phoneNumber2').val();
	var phoneNumber3 = $('#phoneNumber3').val();

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
		'loginId'			: ''
	};
	
	$.post('/users/check-account', params, function(response){
		if (!response.isSuccess) {
			alert("입력하신 정보와 일치하는 회원정보가 없습니다.");
			$('#userName').focus();
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
			}
		}
	});
	
}
</script>
</page:javascript>