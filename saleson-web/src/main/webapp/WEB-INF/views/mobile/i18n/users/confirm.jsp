<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.del_tit {position: inherit;}
.btn_st2 {margin-top: 5px;}
</style>
	<div class="title">
		<h2>회원가입</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<ol class="join_pr cf"> 
			<li class="on"><span>STEP.1</span><p>본인인증</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			<li><span>STEP.2</span><p>약관동의</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			<li><span>STEP.3</span><p>정보입력</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			<li><span>STEP.4</span><p>가입완료</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
		</ol>
		<form id="authForm" method="post">
			<input type="hidden" name="requestToken" id="requestToken" />
			<div class="bd_table typeB -login">
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
		
		<%-- 휴대폰/ 아이핀 인증 현재 서비스 중이지 않아서 숨김 처리 2017-06-09 yulsun.yoo  --%>
		<!-- join_pr -->
		<!--<form id="authForm" method="post">
			<input type="hidden" name="requestToken" id="requestToken" />  휴대폰 인증 코딩 나오면 진행 -->
			<!-- 
			<div class="join_area">
				<p class="ob_txt">리뉴올PC에 회원가입을 위해 아래 중 편리한 방법으로<br/>본인인증을 선택해주세요.</p>
				<div class="ob_btn">
					<button class="btn_st1 submit ph" type="button" title="휴대폰 인증">휴대폰 인증</button>
					<button class="btn_st1 submit pin" type="button" title="아이핀 인증">아이핀 인증</button>
				</div>
				<p class="ob_txt2">본인인증을 위해 입력하신 개인정보는 본인인증기관<br/>(서울신용평가정보㈜)에서 수집하는 정보이며, 이때 수집된 정보는 본인인증 외 어떠한 용도로도 이용되거나 별도  저장되지 않습니다. </p>
				<p class="ob_txt2">문의처 : 서울신용평가정보㈜ SIREN24 / 고객센터 (1577-1006)</p>
				
				<div class="ob_notice">
					<span>리뉴올PC는 회원님의 개인정보를 안전하고 소중하게 관리합니다.</span>
					<p>2012년 8월 18일 부터 시행되는 『정보통신망 이용 촉진 및 정보보호 등에 관한 법률(이하 정보통신망법) 』 제 23조의 2 "주민등록번호의 사용 제한"에 따라 리뉴올PC 내 모든 서비스에서 주민등록번호는 입력 받지 않습니다. 이에 따라 회원으로 가입하실 때는 주민등록번호를 입력하는 실명인증 대신 “아이핀(IPIN) 인증이나 “휴대폰”을 통해 본인인증으로 대신 합니다.</p>
				</div>
				ob_notice
			</div> -->
			<!-- join_wrap -->
			<div class="pr_btn cf">
				<button class="btn_st1 reset" type="button" title="가입취소" onclick="location.href='/m'">가입취소</button>
				<!-- <button class="btn_st1 b_pink" onClick="javascript:alert('사용자 인증이 필요합니다.);" title="다음단계">다음단계</button> -->
				<button type="submit" class="btn_st1 b_pink" title="다음단계">다음단계</button>
				<!-- <button class="btn_st1 b_pink" type="submit" title="다음단계">다음단계</button> -->
			</div>
			<!-- pr_btn -->
		<!-- </form> 휴대폰 인증 코딩 나오면 진행-->
		</form>
	</div>
	<!-- 내용 : e -->
	
<%-- 	<div class="member_join">
		<h3 class="title">회원가입</h3> 
		<div class="join_wrap">
			<div class="join_step">
				<ul>
					<li><a class="on">본인인증</a></li>
					<li><a>약관동의</a></li>
					<li><a>정보입력</a></li>
					<li><a>가입완료</a></li>
				</ul>
			</div><!--//join_step E-->
			
			<ul class="join_guide">
				<li>가입 여부 확인 및 개인정보 보호를 위하여 본인인증 확인 기관을 통해 실명을 확인하고 있습니다.</li>
				<li>회원가입 완료 후  회원서비스를 이용하실 수 있습니다.</li>
				<li>인증번호를 입력후 다음단계를 선택해 주세요.</li>  
			</ul> 
		</div><!--//join_wrap E-->
		
		<form id="authForm" method="post">
			<input type="hidden" name="requestToken" id="requestToken" />
			<div class="table_write">
				<table cellpadding="0" cellspacing="0">
					<colgroup>
						<col style="width:36%;">
						<col style="width:65%;">
					</colgroup>
					<tbody>
						<tr>
							<th>이름</th>
							<td><input type="text" name="userName" id="userName" class="required" title="이름"></td> 
						</tr>
						<tr>
							<th>휴대폰번호</th>
							<td>
								<div class="hp_write"> 
									<div><input type="text" name="phoneNumber1" id="phoneNumber1" class="required _number" maxlength="3" title="휴대폰번호"></div>
									<span class="connect">-</span>
									<div><input type="text" name="phoneNumber2" id="phoneNumber2" class="required _number" maxlength="4" title="휴대폰번호"></div>
									<span class="connect">-</span>
									<div><input type="text" name="phoneNumber3" id="phoneNumber3" class="required _number" maxlength="4" title="휴대폰번호"></div>
								</div>
								<a href="javascript:sendAuthNumber();" class="number">인증번호전송</a>
							</td> 
						</tr> 
						<tr>
							<th>인증번호</th>
							<td>
								<input type="text" name="smsAuthNumber" id="smsAuthNumber" title="인증번호" disabled="disabled" placeholder="인증번호를 입력해 주세요.">
							</td> 
						</tr>  
					</tbody>
				</table>		
			</div><!--//table_write E-->
			
			<div class="btn_area wrap_btn">
				<div class="sale division-2" style="display:block"> 
					<div>
						<div>
							<button type="button" onclick="location.href='/m'" class="btn btn_out btn-w100">가입취소</button>
						</div>
					</div>
					<div>
						<div>
							<button type="submit" class="btn btn_on btn-w100">다음으로</button> 
						</div>
					</div>
				</div>				 
			</div>
		</form>
	</div><!--// member_join E--> --%>	
	
<page:javascript>
<script type="text/javascript" src="<c:url value="/content/modules/op.sms.js" />"></script>	

</page:javascript>
<script type="text/javascript">
$(function() {
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
					$('#authForm').attr('action', '/m/users/agreement');
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
		'email'			: ''
	};
	
	$.post('/users/check-account-join', params, function(response){
		if (!response.isSuccess) {
			alert(response.errorMessage);
		} else {
			
			var isConfirm = true;
			if ($.validator.isEmpty($('#requestToken').val()) == false) {
				if (!confirm("인증번호를 다시 받으시겠습니까?")) {
					isConfirm = false;
				}
			}
			if (isConfirm == true) {
				if (Sms.requestAuthNumber() != false) {
					$('#userName').prop('readonly', true);
					$('#phoneNumber1').prop('readonly', true);
					$('#phoneNumber2').prop('readonly', true);
					$('#phoneNumber3').prop('readonly', true);
				}
			}
		}
	});
}
</script>