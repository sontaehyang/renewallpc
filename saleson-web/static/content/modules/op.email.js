/*
 * 이메일 인증관련 스크립트.
 * 
 */
var Email = {

	// 이메일 인증번호 요청.
	requestAuthNumber : function() {
		var $loginId = $('#loginId');

		var $email1 = $('#email1');
		var $email2 = $('#email2');
		
		if ($email1.size() > 0) {
			if ($.trim($email1.val()) == '') {
				alert('이메일 앞자리를 입력해 주십시요.');
				$email1.focus();
				return;
			}
			
			if ($.trim($email2.val()) == '') {
				alert('이메일 뒷자리를 선택하거나 직접 입력해 주십시요.');
				$email2.focus();
				return;
			}
			
			var email = $email1.val() + '@' + $email2.val();
			
			if (!$.validator.patterns._email.test(email)) {
				alert($.validator.messages._email);
				$email1.focus();
				return;
			}
			
			$loginId.val($email1.val() + '@' + $email2.val());
		}
		
		
		
		if ($.trim($loginId.val()) == '') {
			alert('이메일을 입력해 주세요.');
			$loginId.focus();
			return;
		}
		if (!$.validator.patterns._email.test($loginId.val())) {
			alert($.validator.messages._email);
			$loginId.focus();
			return;
		}
		
		var phoneNumber;
		for (var i = 1; i <=  3; i++) {
			var isSuccess = this.validate($('#phoneNumber' + i));
			if (!isSuccess) {
				return false;
			}
			if (i == 1) {
				phoneNumber = $('#phoneNumber' + i).val();
			} else {
				phoneNumber += "-" + $('#phoneNumber' + i).val();
			}
		}

		var params = {
			'loginId' : $loginId.val(),
			'phoneNumber1' : $('#phoneNumber1').val(),
			'phoneNumber2' : $('#phoneNumber2').val(),
			'phoneNumber3' : $('#phoneNumber3').val()
		};
		
		
		$.post("/auth/email-request", params, function(response){
			Common.loading.hide();
			if (response.isSuccess) {
				$('#requestToken').val(response.data);
				alert('입력하신 이메일로 인증번호가 발송되었습니다.');
				$('#emailAuthNumber').addClass("required").attr("disabled", false);
				
			} else {
				alert(response.errorMessage);
				$('#requestToken').val("");
				$('#emailAuthNumber').val("").removeClass("required").attr("disabled", true);
			}

		});

	},
	
	validate : function($selector) {
		if ($.trim($selector.val()) == '') {
			alert($selector.attr("title") + '을(를) 입력해 주세요.');
			$selector.focus();
			return false;
		}
		if ($selector.attr("id") == 'loginId') {
			if (!$.validator.patterns._email.test($selector.val())) {
				alert($.validator.messages._email);
				$selector.focus();
				return false;
			}
		} else {
			if (!$.validator.patterns['_number'].test($selector.val())) {
				$.validator.validatorAlert($.validator.messages['_number'].format($selector.attr("title")), $selector);
				$selector.focus();
				return false;
			}
		}
		
		return true;
	},
	
	reset : function() {
		$('#requestToken').val("");
		$('#smsAuthNumber').val("").removeClass("required").attr("disabled", true);
		
	}
		
};