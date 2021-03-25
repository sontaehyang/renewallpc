<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>

	<div class="inner">
		<div class="location_area">
			<div class="breadcrumbs">
				<a href="/" class="home"><span class="hide">home</span></a>
			<span>회원가입</span> 
			</div>
		</div><!-- // location_area E -->
		
		<div class="content_title"> 
			<h2 class="title">회원가입</h2>  
		</div>
		<div class="join_step step01">
			<ul>
				<li class="on"><span>01</span> 본인인증</li>
				<li><span>02</span> 약관동의</li>
				<li><span>03</span> 정보입력</li>
				<li><span>04</span> 가입완료</li>
			</ul>
		</div> <!-- // join_step E -->
		
		<!-- sms 인증 -->
		<div  class="box_list">
			<ul>
				<li>가입 여부 확인 및 개인정보 보호를 위하여 본인인증 확인 기관을 통해 실명을 확인하고 있습니다.</li>
				<li>회원가입 완료 후 리뉴올PC 회원서비스를 이용하실 수 있습니다.</li>
				<li>인증번호를 입력후 다음단계를 선택해 주세요.</li>
			</ul>
		</div> <!-- // box_list E -->
		
		<h3 class="sub_title mt30">SMS인증</h3>
		
		<form id="authForm" method="post">
			<input type="hidden" name="requestToken" id="requestToken" />
			<div class="board_wrap">  
	 			<table cellpadding="0" cellspacing="0" class="board-write"> 
		 			<caption>SMS인증</caption>
		 			<colgroup>
		 				<col style="width:180px;">
		 				<col style="width:auto;">	 				
		 			</colgroup>
		 			<tbody> 
		 				<tr>
		 					<th scope="row">이름</th>
		 					<td>
		 						<div class="input_wrap col-w-7">
	 								<input type="text" name="userName" id="userName" class="required" title="이름"> 
	 							</div>
		 					</td>
		 				</tr> 
		 				<tr>
		 					<th scope="row">휴대폰번호</th>
		 					<td>
		 						<div class="hp_area">
		 							<div class="input_wrap col-w-10">	
		 								<input type="text" name="phoneNumber1" id="phoneNumber1" title="휴대폰번호" class="required full _number" maxlength="3">
		 							</div>  
								    <span class="connection"> - </span>
								    <div class="input_wrap col-w-10">			 	 			 	 							 
		 								<input type="text" name="phoneNumber2" id="phoneNumber2" title="휴대폰번호" class="required full _number" maxlength="4"> 
		 							</div>
		 							<span class="connection"> - </span>	
		 							<div class="input_wrap col-w-10">			 	 			 	 							 
		 								<input type="text" name="phoneNumber3" id="phoneNumber3" title="휴대폰번호" class="required full _number" maxlength="4"> 
		 							</div>					
		 							<button type="button" onclick="sendAuthNumber()" class="btn btn-default btn-m">인증번호 발송</button>	 
		 						</div><!-- // hp_area --> 
		 					</td>
		 				</tr> 
		 				<tr>
		 					<th scope="row">인증번호</th>
		 					<td>
		 						<div class="input_wrap col-w-7">
	 								<input type="text" name="smsAuthNumber" id="smsAuthNumber" title="인증번호" disabled="disabled" placeholder="인증번호를 입력해 주세요." class="_number"> 
	 							</div>
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table><!--//write E-->
	 			 	
			</div><!--//board_write_type01 E-->
		 
			<div class="btn_wrap">
				<button type="button" onclick="location.href='/'" class="btn btn-default btn-lg">가입취소</button>
				<button type="submit" class="btn btn-success btn-lg">다음단계</button>
			</div> 	
		</form>	 		
		
		<%-- 현재 서비스중이지 않아서 숨김 처리 2017-06-09 yulsun.yoo --%>
		<!-- 휴대폰/아이핀 인증 -->
		<!-- <div  class="box_list">
			<ul>
				<li>가입 여부 확인 및 개인정보 보호를 위하여 본인인증 확인 기관을 통해 실명을 확인하고 있습니다.</li>
				<li>회원가입 완료 후 리뉴올PC 회원서비스를 이용하실 수 있습니다.</li>
				<li>인증번호를 입력후 다음단계를 선택해 주세요.</li>
			</ul>
		</div> // box_list E
		
		<div class="cert_wrap">
			<div class="cert_box">
				<div class="btn_wrap">
					<button type="button" class="btn ph" title="휴대폰 인증">휴대폰 인증</button>
					<button type="button" class="btn pin" title="아이핀 인증">아이핀(i-PIN) 인증</button>
				</div>
				<div class="cert_txt">
					<p><strong>리뉴올PC는 회원님의 개인정보를 안전하고 소중하게 관리합니다.</strong></p>
					<p>2012년 8월 18일부터 시행되는 「정보통신망 이용 촉진 및 정보보호 등에 관한 법률(이하 정보통신망법)」 제 23조의 2 ”주민등록번호의 사용 제한”에 따라 리뉴올PC 내 모든 서비스에서 주민등록번호는 입력 받지 않습니다. 이에 따라 회원으로 가입하실 때는 주민등록번호를 입력하는 실명인증 대신 “아이핀(IPIN) 인증”이나 “휴대폰 인증”을 통해 본인인증을 대신합니다.</p>
				</div>
			</div>
			<div class="cert_desc">
				<p>본인인증을 위해 입력하신 개인정보는 본인인증기관(서울신용평가정보㈜)에서 수집하는 정보이며, 이때 수집된 정보는 본인인증 외 어떠한 용도로도 이용되거나 별도 저장되지 않습니다.<br>
				문의처: 서울신용평가정보㈜ SIREN24 / 고객센터 : <span>1544-2880</span></p>
			</div>
		</div> // cert_wrap E -->
		
	</div>
	

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
					$('#authForm').attr('action', '/users/agreement');
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
</page:javascript>