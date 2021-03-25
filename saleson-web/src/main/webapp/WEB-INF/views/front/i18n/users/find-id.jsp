<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>	
	
	<!-- 본문 -->
	<!-- 팝업사이즈 526*354-->
	<div class="popup_wrap">
		<h1 class="popup_title">아이디 찾기</h1>
		<form id="authForm" method="post">
			<div class="popup_contents"> 
				<p class="id_txt">아래의 항목을 입력하시면 ID 일부를  찾을 수 있습니다.</p>
				<div class="board_wrap">  
			 		<table class="board-write">
			 			<input type="hidden" name="requestToken" id="requestToken" />
			 			<caption>아이디 찾기</caption>
			 			<colgroup>
			 				<col style="width:110px;">
			 				<col style="width:auto;">	 				
			 			</colgroup>
			 			<tbody> 
			 				<tr>
			 					<th scope="row">이름</th>
			 					<td>
			 						<div class="input_wrap col-w-5"">
		 								<input type="text" class="full required" id="userName" name="userName" title="이름"> 
		 							</div>
			 					</td>
			 				</tr> 
			 				<tr>
			 					<th scope="row">휴대폰번호</th>
			 					<td>
			 						<div class="hp_area">
				 						<div class="input_wrap col-w-9">
				 							<input type="text" id="phoneNumber1" name="phoneNumber1" title="휴대폰번호" class="full number required _number" maxlength="3">
										</div>
										<span class="connection">-</span>
										<div class="input_wrap col-w-9"> 
											<input type="text" id="phoneNumber2" name="phoneNumber2" title="휴대폰번호" class="full number required _number" maxlength="4"> 
				 						</div>
				 						<span class="connection">-</span>
				 						<div class="input_wrap col-w-9"> 	
				 							<input type="text" id="phoneNumber3" name="phoneNumber3" title="휴대폰번호" class="full number required _number" maxlength="4">   
				 						</div>
				 						<button type="button" onclick="sendAuthNumber()" class="btn btn-default btn-ms" title="인증번호 발송">인증번호 전송</button>
									</div>
			 					</td>
			 				</tr> 
			 				<tr>
			 					<th scope="row">인증번호</th>
			 					<td>
			 						<div class="input_wrap col-w-2">
		 								<input type="text" class="full _number" id="smsAuthNumber" title="인증번호" disabled="disabled" placeholder="인증번호를 입력해 주세요."> 
		 							</div>
		 							<p class="certification guide_txt" style="display: none;">입력하신 번호로 인증번호가 발송되었습니다.</p>
		 							<p class="certification guide_txt" style="display: none;">입력하신 정보와 일치하는 회원정보가 없습니다.</p>
		 							<p class="certification guide_txt" style="display: none;">인증번호가 잘못 입력되었습니다. 다시 확인해 주세요.</p>
			 					</td>
			 				</tr>
			 			</tbody>
			 		</table><!--//write E-->	 	
				</div>   
			</div><!--//popup_contents E-->
			
			<div class="btn_wrap"> 
				<button type="submit" class="btn btn-success btn-lg">확인하기</button> 
			</div>
			<a href="javascript:self.close();" class="popup_close">창 닫기</a>
		</form>
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
					$('#authForm').attr('action', '/users/find-id');
					authCheck = true;
				} else {
					//alert(response.errorMessage);
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
		/* 'email'			: '' */
		'loginId'			: '' 
	};
	
	$.post('/users/check-account', params, function(response){
		if (!response.isSuccess) {
			//alert("입력하신 정보와 일치하는 회원정보가 없습니다.");
			$('.certification').hide();
			$('.certification').eq(1).show();
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
				$('.certification').hide();
				$('.certification').eq(0).show();
			}
		}
	});
	
}

</script>
</page:javascript>