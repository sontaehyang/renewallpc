/*
 * SMS 인증관련 스크립트.
 * 
 */
var Sms = {

	// SMS 인증번호 요청.
	requestAuthNumber : function(phoneNumber) {
		
		var sendPhoneNumber;
		
		var $op_username = $('#username');
		var isLoginPage = $op_username.size() > 0 ? true : false;
		
		var params = {
			'loginId' : '',
			'phoneNumber' : '',
			'loginType' : ''
		};
		
		
		if (isLoginPage) {
			if($('#username').val() == ""){
				alert("주문자명 항목을 입력해 주세요.");
				$('#username').focus();
				return false;
			}
			
			var $loginType = $('input[name=op_login_type]');
			
			if ($loginType.size() > 0 && $loginType.val() == 'opmanager') {
				params.loginType = $loginType.val();
			}
			
		}
		
		
		if (phoneNumber == undefined) {
			var $phoneNumber1 = $('#phoneNumber1');
			var $phoneNumber2 = $('#phoneNumber2');
			var $phoneNumber3 = $('#phoneNumber3');
			
			if ($.validator.isEmpty($phoneNumber1.val())) {
				alert('휴대폰번호 앞자리를 입력해 주세요.');
				$phoneNumber1.focus();
				return false;
			} 

			if ($.validator.isEmpty($phoneNumber2.val())) {
				alert('휴대폰번호 중간자리를 입력해 주세요.');
				$phoneNumber2.focus();
				return false;
			}
			
			if ($phoneNumber2.val().length < 3) {
				alert('휴대폰번호 중간자리를 3자리 이상 입력해 주세요.');
				$phoneNumber2.focus();
				return false;
			}

			if ($.validator.isEmpty($phoneNumber3.val())) {
				alert('휴대폰번호 마지막 자리를 입력해 주세요.');
				$phoneNumber3.focus();
				return false;
			}

			if ($phoneNumber3.val().length < 4) {
				alert('휴대폰번호 마지막 자리를 4자리로 입력해 주세요.');
				$phoneNumber3.focus();
				return false;
			}
			
			sendPhoneNumber = $phoneNumber1.val() + "-" + $phoneNumber2.val() + "-" + $phoneNumber3.val();
			
		} else {
			sendPhoneNumber = phoneNumber;
		}

		
		params.phoneNumber = sendPhoneNumber;
		
		$.post("/auth/sms-request", params, function(response){
			if (response.isSuccess) {
				//alert(response.data);
				Common.loading.hide();
				$('#requestToken').val(response.data);
				alert('입력하신 휴대폰 번호로 인증번호가 발송되었습니다.');
				$('#smsAuthNumber').addClass("required").attr("disabled", false).val("");
				
			} else {
				Common.loading.hide();
				alert(response.errorMessage);
				$('#requestToken').val("");
				$('#smsAuthNumber').val("").removeClass("required").attr("disabled", true);
			}

		});

	},
	
	reset : function() {
		$('#requestToken').val("");
		$('#smsAuthNumber').val("").removeClass("required").attr("disabled", true);
		
	}
		
};