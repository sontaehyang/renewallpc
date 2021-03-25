/**
 * 사용자 입력 값 검증 스크립트 
 * op.validator-1.0.0.js
 *
 * @author skc@onlinepowers.com
 * @since 1.0.0
 *  
 * <pre>
 * input class 에 required, optional 을 주어 입력 값을 검증할 수 있다.
 * 
 * - append 된 input도 검증.
 * - 하나의 폼에서 그룹핑된 클래스를 지정하여 검증. (required_tab1, required_tab2, required_tab3)
 * - 금지어 검증.
 * - submitHandler 지정 가능.
 * 
 * 2014-05-15
 * ----------
 * - 옵션의 requiredClass, optionalClass 는 콩마(,)로 구분하여 등록함. (복수개인 경우)
 * - 클래스별 분기 검증 방법.
 *   1. 옵션의 requiredClass: 'required, required_a, required_b'로 등록함) 
 *   	$('#form').validator({'requiredClass': 'required, required_a, required_b, required_c'});
 *   
 *   2. 폼 종류에 따라 현재 시점에 체크해야할 클래스를 설정 (ex..결제방법이 카드 / 계좌이체냐에 따라 검증해야할 클래스를 지정)
 *   	$.validator.currentClass = 'required_a'; or
 *   	$.validator.currentClass = 'required_b, required_c';  콤마로 구분 가능.
 *   
 * @example
 * class="required _email" : 무조건 입력해야 하며 이메일 형식을 검사한다.
 * class="optional _email" : 값이 입력된 경우에만 이메일 형식을 검사한다.
 *
 * 패턴 검사
 * _email : 이메일 형식 체크.
 * _number : 숫자만 
 * _number_comma : 1,000 단위 콤마 찍힌 숫자 
 * _date : 날짜 형식 (yyyy-mm-dd) 체크.
 * _filter : 금지어 체크.
 * </pre>
 * 
 * 
 * 2015-07-06
 * ----------
 * 	1. 기존 그룹별 분기하여 검증할 때 validator 선언시 그룹을 지정해야하는 부분을 수정. 
 * 		$('#form').validator({'requiredClass': 'required, required_a, required_b, required_c'});
 * 		
 */
(function ($) {

	var BANNED_WORDS;
	var defaultOptions = {
		'requiredClass': 'required',
		'optionalClass': 'optional',
		'submitHandler': ''
	};

	var methods = {
		init: function (options) {

			return this.each(function () {

				var $this = $(this);
				var data = $this.data('options');

				if (!data) {
					options = $.extend(defaultOptions, options);
					$(this).data('options', options);
				}

				$.validator.init($this, options);
			});
		},


		submitHandler: function (submitHandler) {
			return this.each(function () {
				var $this = $(this);
				var data = $this.data('options');

				var options = $.extend({}, defaultOptions);
				if (!data) {
					options.submitHandler = submitHandler;
					$(this).data('options', options);
				}

				$.validator.init($this, options);
			});
		},


		option: function (options) {
			return this.each(function () {

				var $this = $(this);
				var data = $this.data('options');

				$(this).data('options', $.extend(data, options));
				alert(options.requiredClass);
			});
		}
		
	};


	$.fn.validator = function (method) {

		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));

		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);

		} else if (typeof method === 'function' || !method) {
			return methods.submitHandler.apply(this, arguments);

		} else {
			$.error(method + '는 존재하지 않는 Method 입니다.');

		}
	};


	var VALIDATOR_MESSAGE = {
		'ko' : {
			_number: '{0} 항목을 숫자로만 입력해 주세요.',
			_number_nagetive: '{0} 항목을 숫자로만 입력해 주세요.',
			_number_comma: '{0} 항목을 숫자로만 입력해 주세요.',
			_korean: '{0} 항목을 한글로만 입력해 주세요.',
			_minlength: '{0} 항목을 {1}글자 이상 입력해 주세요.',
			_length: '{0} 항목을 {1}글자로 입력해 주세요.',
			_min: '{0} 항목을 {1} 이상으로 입력해 주세요.',
			_max: '{0} 항목을 {1} 이하로 입력해 주세요.',
			_phone: '{0} 항목을 정확히 입력해 주세요.',
			_tel: '{0} 항목을 정확히 입력해 주세요.',
			_date: '{0} 항목의 날짜 형식을 YYYYMMDD 형태로 정확히 입력해 주세요.',
			_email: '이메일 주소를 정확히 입력해 주십시오.',
            _first_email: '이메일 처음 부분에 한글을 입력할 수 없습니다.',
            _last_email: '이메일 마지막 부분에 한글을 입력할 수 없습니다.',
			//_id: '아이디는 6~20 글자로 영문(대,소문자), 숫자, 특수문자(-), 특수문자(_), 특수문자(.) 조합으로 입력해 주십시요.',
			_id: '아이디는 4~16 글자로 영문(대,소문자), 숫자 조합으로 입력해 주십시요.',
			_filter: '{0}에 사용할 수 없는 단어가 포함되어 있습니다.',
			_password: '{0}은(는) 8~20 자리의 영문+숫자+특수 문자로 되어야 합니다.',
			_duplicated : '{0}은 항목이 {1}자 이상 중복입니다.',
			_emoji: '{0}에는 이모티콘을 추가할 수 없습니다.',

			text : '{0} 항목을 입력해 주세요.',
			checkbox : '{0} 항목을 체크해 주세요.',
			select : '{0} 항목을 선택해 주세요.'
		},
		
		'ja' : {
			_number: '{0}項目を数字のみで入力してください。',
			_number_nagetive: '{0}項目を数字のみで入力してください。',
			_number_comma: '{0}項目を数字のみで入力してください。',
			_korean: '{0} 항목을 한글로만 입력해 주세요.',
			_minlength: '{0}項目を{1}文字以上入力してください。',
			_length: '{0}項目を{1}文字で入力してください。',
			_min: '{0}項目を{1}以上で入力してください。',
			_max: '{0}項目を{1}以下で入力してください。',
			_phone: '{0}を正しく入力してください。',		// {}을 정확히 입력해 주십시오.
			_tel: '{0}を正しく入力してください。',			// {}을 정확히 입력해 주십시오.
			_date: '{0}項目のYYYYMMDDで入力してください。',
			_email: 'メールアドレスを正しく入力してください。',
			_id: '아이디는 4~16 글자로 영문(대,소문자), 숫자 조합으로 입력해 주십시요.',
			_filter: '{0}項目に使用できない単語が含まれています。',
			// _password: '{0}은(는) 4~20 자리의 영문으로 하나 이상의 숫자를 포함해야 합니다.',
			// 7esthe : 비밀번호는 4 ~ 20 자의 영숫자로 입력하십시오.
			_password: '{0}は4～20文字の半角英数字で入力してください',
			// _emoji: '{0}에는 이모티콘을 추가할 수 없습니다.',

			text : '{0}を入力してください。',
			checkbox : '{0}を選択してください。',
			select : '{0}を選択してください。'
		}
	};
	
	// 스태틱 메서드로 지정 (Static Method)
	$.validator = {
		currentClass	: '',
		currentOptionalClass : '',  // 해당 값이 있는 경우에는 지정된 클래스에 해당하는 것만 체크한다.
			
		optionalKeys: ['_number', '_number_nagetive', '_number_comma', '_minlength', '_length', '_min', '_max', '_korean', '_phone', '_tel', '_date', '_email','_first_email', '_last_email', '_id', '_filter', '_password', '_emoji'],

			// _number_comma: /^[0-9]+[0-9\,]+[0-9]$/,
			// _id: /^[a-zA-Z0-9_\-.]{6,20}$/,
			// _password: /^(?=.*[a-zA-Z])(?=.*[0-9]).{4,20}$/   ==> 영문 + 숫자1개 이상 
		patterns: {
			_number: /^[\-|0-9]+$/,
			_number_nagetive: /^(0|[-]?[1-9][0-9]*)+$/,
			_number_comma: /^[0-9\,]+$/,
			_minlength: /^[0-9]+$/,
			_length: /^[0-9]+$/,
			_min: /^[0-9]+$/,
			_max: /^[0-9]+$/,
			_korean: /^[가-힝]+$/,
			_phone: /^0\d{2}[-]{0,1}\d{3,4}[-]{0,1}\d{4}$/,
			_tel: /^\d{0,3}[-]{0,1}\d{3,4}[-]{0,1}\d{4}$/,
			_date: /^\d{1,4}\d((0?\d)|(1[012]))\d([012]?\d|30|31)$/,
			_email: /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,3}$/,
			_first_email: /^([0-9a-zA-Z_\.-]+)$/,
			_last_email: /^([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,3}$/,
			_id: /^[a-zA-Z0-9]{4,16}$/,
			// 영문 + 숫자만 (7esthe)
			_password: /(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^*()\-_=+\\\|\[\]{};:\'",.<>\/?]).{8,20}$/,
            _emoji: /^[^\uD83C-\uDBFF\uDC00-\uDFFF]+$/

		},
		
		
		
		 

		messages: (function () {
			var lang = Common.isUndefined(OP_LANGUAGE) || OP_LANGUAGE == '' ? 'ko' : OP_LANGUAGE;
			return $.extend({}, VALIDATOR_MESSAGE['ko'], VALIDATOR_MESSAGE[lang]);
		}()),
		

		init: function ($selector, options) {
			$.ajaxSetup({async:false});
			
			if ($selector.find('._filter').size() > 0) {
				// 금지어 목록 조회 
				$.get(url("/ban-word/list-all"), {}, function(response) {
					if (response.isSuccess) {
						BANNED_WORDS = response.data;
					}
				}, 'json');
			}

			$selector.find("input[type=text], input[type=password], input[type=email], input[type=tel], input[type=number], input[type=checkbox], input[type=radio]").keypress(function (e) {
//				if (e.keyCode == 13) {
//					return $.validator.validate($selector, options);
//					return false;
//				}
			});
			
			$selector.find("input[type=image], input[type=submit], button[type=image], button[type=submit]").click(function () {
				return $.validator.validate($selector, options);
			});

			$selector.find(".submit").click(function () {
				return $.validator.validate($selector, options);
			});
		},


		isEmpty: function(value) {
			return  $.trim(value) == "" ? true : false;
		}, 
		
		// array => '.class1, .class2, .class3' 형태의 스트링으로 변환 
		arrayToCommaClassName: function(arr) {
			var classCount = 0;
			var commaClassNames = '';
			for (var i = 0; i < arr.length; i++) {
				var className = $.trim(arr[i]);
				if (className != '') {
					if (classCount > 0) {
						commaClassNames += ", ";
					}
					commaClassNames += "." + className;
					classCount++;
				}
			}
			return commaClassNames;
		},
		
		// XXX항목을 입력해 주세요 형식.(static으로 사용)
		required: function($selector, message) {
			if ($.validator.isEmpty($selector.val())) {
				var alertMessage = message;
				if (message == undefined) {
					alertMessage = $.validator.messages['text'].format($selector.attr("title"));
				}
				
				$.validator.validatorAlert(alertMessage, $selector);
				$selector.focus();
				return false;
			} 
			return true;
		},

		validate: function (selector, options) {

			var errors = 0;
			var requiredClassNames = options.requiredClass.split(',');
			var optionalClassNames = options.optionalClass.split(',');
			
			
			var requiredClassName = $.validator.arrayToCommaClassName(requiredClassNames);
			var optionalClassName = $.validator.arrayToCommaClassName(optionalClassNames);
			var currentRequiredClassName = $.validator.arrayToCommaClassName($.validator.currentClass.split(','));			// Dynamic Required Class
			var currentOptionalClassName = $.validator.arrayToCommaClassName($.validator.currentOptionalClass.split(','));	// Dynamic Optional Class
			
			var targetSelector = requiredClassName;
			if ($.trim(optionalClassName) != '') {
				targetSelector +=  ', ' + optionalClassName;
			}
			if ($.trim(currentRequiredClassName) != '') {
				targetSelector +=  ', ' + currentRequiredClassName;
			}
			if ($.trim(currentOptionalClassName) != '') {
				targetSelector +=  ', ' + currentOptionalClassName;
			}
			
			
			selector.find(targetSelector).each(function () {
				var tagName = $(this).get(0).tagName.toLowerCase();
				var tagType = "";
				var title = $(this).attr('title');
				var value = $(this).val();
				var name = $(this).attr('name');

				var classes = $(this).attr('class');
				var isRequired = false;

				title = title == undefined ? name : title;

				if (tagName == 'input') {
					try {
					tagType = $(this).attr("type").toLowerCase();
					} catch(e) {
						alert('input 항목에 type 속성이 없습니다.(' + name + ')');
						return false;
					}

				}
				/*	
				if (classes.indexOf('required') > -1) {
					isRequired = true;
				}*/
				
				if ($.validator.currentClass != '') {
					
					var currentClassNames = $.validator.currentClass.split(',');
					for (var i = 0; i < currentClassNames.length; i++) {
						var currentClass = $.trim(currentClassNames[i]);
						if (currentClass != '' && $(this).hasClass(currentClass)) {
							isRequired = true;
							break;
						}
					}
				}

				// 문자 중복체크 (기본이 3)
				var isDuplicated = false;

				if ($(this).hasClass('_duplicated')) {
					isDuplicated = true;
				}

				if (isDuplicated) {

					if (value != '') {
						var duplicateCount = 3;
						var isCharDuplicated = false;
						var cehcekedChar = [];
						var arrayValue = value.split('');
						var length = arrayValue.length;
						for (var i=0; i<length; i++) {
							var char = arrayValue[i];
							var pattern = '';
							for (var j=0; j<duplicateCount; j++) {
								pattern += char;
							}

							cehcekedChar[char] = pattern;
						}

						var regExp;
						for (var key in cehcekedChar) {
							var pattern = cehcekedChar[key];
							regExp = new RegExp(pattern);

							if (pattern == "...") {
                                regExp = /\.\.\./;
							}

							if (pattern == "^^^") {
                                regExp = /\^\^\^/;
							}

							isCharDuplicated = regExp.test(value);

							if (isCharDuplicated) {
								break;
							}
						}

						if (isCharDuplicated) {
							errors++;

							$.validator.validatorAlert($.validator.messages['_duplicated'].format(title, duplicateCount), $(this));
							$(this).focus();

							return false;
						}
					}
				}
				
				if ($(this).hasClass('required')) {
					isRequired = true;
				}

				if (isRequired) {
					if (tagName == 'input') {

						if (tagType == 'text' || tagType == "password" || tagType == "email"  || tagType == "tel" || tagType == "number") {
							//alert(tagType);
							if (value == '') {
								errors++;

								$.validator.validatorAlert($.validator.messages['text'].format(title), $(this));
								$(this).focus();
								return false;
							}
						} else if (tagType == "file") {

							if (value == '') {
								errors++;

								// 포커스 타겟 지정 
								var $focusTarget = $(this).find('> label');
								if ($focusTarget.size() == 0) {
									$focusTarget = $(this);
								}
								$.validator.validatorAlert($.validator.messages['select'].format(title), $focusTarget);
								$(this).focus();
								return false;
							}
						} else if (tagType == "radio") {

							var $radio = $('input[name=' + name + ']');
							var radioCheckedLength = $('input[name=' + name + ']:checked').length;

							if (radioCheckedLength == 0) {
								errors++;
								$.validator.validatorAlert($.validator.messages['select'].format(title), $radio.eq(0));
								$radio.eq(0).focus();
								return false;
							}

						} else if (tagType == "checkbox") {

							var $checkbox = $('input[name=' + name + ']');
							var checkboxCheckedLength = $('input[name=' + name + ']:checked').length;
							if (checkboxCheckedLength == 0) {
								errors++;
								$.validator.validatorAlert($.validator.messages['checkbox'].format(title), $checkbox);
								$checkbox.focus();
								return false;
							}
						}
					} else if (tagName == 'select') {

						if (value == '') {
							errors++;
							$.validator.validatorAlert($.validator.messages['select'].format(title), $(this));
							$(this).focus();
							return false;
						}

					} else if (tagName == 'textarea') {
						if (value == '') {
							errors++;
							$.validator.validatorAlert($.validator.messages['text'].format(title), $(this));
							$(this).focus();
							return false;
						}
					}
				}


				//var keys = getKeys($.validator.messages);
				var keys = $.validator.optionalKeys;

				for (var i = 0; i < keys.length; i++) {
					
					// 검증 대상인가?
					var isCurrentOptionalFlag = false;

					
					if (isRequired 
							|| ($(this).hasClass('optional') && $.trim($(this).val()) != '')
							|| ($.validator.currentOptionalClass != '' && $(this).hasClass($.validator.currentOptionalClass) && $.trim($(this).val()) != '') ) {
						isCurrentOptionalFlag = true;
						
					}
					
					if (!isCurrentOptionalFlag) {
						continue;
					}
					

					// 날짜(yyyymmdd)
					if (keys[i] == "_date") {
						if ($(this).hasClass(keys[i])) {
							if (validateDate(value) == false) {
								errors++;

								alert($.validator.messages[keys[i]].format(title));
								$(this).focus();
								return false;
							}
						}

						// 금지어 필터링.
					} else if (keys[i] == "_filter") {
						if ($(this).hasClass(keys[i])) {
							var bannedWord = $.validator.getBannedWord(value);
							
							
							if (bannedWord != null) {
								errors++;
								//alert('금지');
								alert($.validator.messages[keys[i]].format(title));
								$(this).focus();
								return false;		
							}
						}

					} else if (keys[i] == "_minlength" || keys[i] == "_length" || keys[i] == "_min" || keys[i] == "_max") {
	
						var classes = $(this).attr("class").split(" ");
						for (var j = 0; j < classes.length; j++) {
							
							var cls = classes[j].substring(0, keys[i].length + 1);
							if (cls == keys[i] + '_') {
								var classText = classes[j].split("_");
								
								try {
									var checkValue = parseInt(classText[2]);
									var hasError = false;
									
									if (keys[i] == "_minlength") {
										if (value.length < checkValue) {
											hasError = true;
										}
									} else if (keys[i] == "_length") {
										if (value.length != checkValue) {
											hasError = true;
										}
									} else if (keys[i] == "_min") {
										value = value.replace(/,/g, "");
										if (value < checkValue) {
											hasError = true;
										}
									} else if (keys[i] == "_max") {
										value = value.replace(/,/g, "");
										if (value > checkValue) {
											hasError = true;
										}
									}
									
									if (hasError) {
										errors++;
										//alert('금지');
										alert($.validator.messages[keys[i]].format(title, checkValue));
										$(this).focus();
										return false;	
									}
								} catch(e) {}
							}
						}

					} else {

						if ($(this).hasClass(keys[i]) && value != "") {
							if (!$.validator.patterns[keys[i]].test(value)) {
								errors++;
								alert($.validator.messages[keys[i]].format(title));
								$(this).focus();
								return false;
							}
						}
					}
				}


				// 이메일 검증 추가
				if ($(this).hasClass("_email1")) {
					var $email = selector.find(targetSelector).filter('._email1');
					if (checkEmail($email) == false) {
						errors++;
						return false;
					}
				}





			});


			if (errors == 0) {
				if ($.isFunction(options.submitHandler)) {
					try {
						return options.submitHandler(selector);
					} catch (e) {
						//Common.exceptionHandler(e, "op.validator의 submitHandler 처리 시 오류가 발생하였습니다.");
						return false;
					}

				} else {
					return true;
				}

			} else {

				return false;
			}

		},


		validatorAlert: function (message, selector) {
			alert(message);
		},
		
		getBannedWord : function(value) {
			
			if (BANNED_WORDS == null) return null;
			
			for (var i = 0; i < BANNED_WORDS.length; i++) {
				if (value.indexOf(BANNED_WORDS[i]) > -1) {
					return BANNED_WORDS[i];
				}
			}
			
			return null;
		},
		
		validateEditor : function(editors, id) {
			if (editors != undefined) {
				editors.getById[id].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
	
				var content = document.getElementById(id).value;
				
				
		        if (content == "" || content == "<br>") {
		            alert('내용을 입력해 주세요');
		            editors.getById["content"].exec("FOCUS"); 
		            return false;
		        }
	
	
				if ($.validator.getBannedWord(content) != null) {
					alert('내용에 금지어가 포함되어 있습니다.');
					editors.getById[id].exec("FOCUS"); 
					return false;		
				}
			}
			return true;
		}
			
	};

})(jQuery);


$(function () {
	$("input[type=text], input[type=password], input[type=file]").keypress(function (e) {
		if (e.keyCode == 13) {
			//return false;
		}
	});
});


String.prototype.format = function () {
	var args = arguments;
	return this.replace(/\{\{|\}\}|\{(\d+)\}/g, function (m, n) {
		if (m == "{{") { return "{"; }
		if (m == "}}") { return "}"; }
		return args[n];
	});
};


function getKeys(obj) {
	var r = []
	for (var k in obj) {
		if (!obj.hasOwnProperty(k))
			continue;
		r.push(k)
	}
	return r
}


function checkEmail($email) {
	var email = "";
	var emailInputSize = $email.size();

	if (emailInputSize == 1) {
		email = $email.eq(0).val();
		if (validateEmail(email) == false) {
			$.validator.validatorAlert($.validator.messages._email, $email.eq(0));
			$email.eq(0).focus();
			return false;
		}
	} else if (emailInputSize == 2) {
		var email1 = $email.eq(0).val();
		var email2 = $email.eq(1).val();

		if (email1 != '' && email2 != '') {
			email = email1 + '@' + email2;

			if (validateEmail(email) == false) {
				$.validator.validatorAlert($.validator.messages._email, $email.eq(0));
				$email.eq(0).focus();
				return false;
			}
		}
	}
}



function validateEmail(email) {
	if ($.validator.patterns['_email'].test(email))
		return true;
	else
		return false;
}


/** 
* 날짜 정보를 입력 받아서 유효성 체크 
* @param inDate YYYYMMDD 
**/	
function validateDate(inDate){ 
	var END_OF_MONTH = [0,31,28,31,30,31,30,31,31,30,31,30,31]; 
	if (isNaN(inDate)) {
		return false;
	}

	if (Number(inDate)/10000000 <= 1) return false; 
	
	var strDate = String(inDate); 
	var nYear = Number(strDate.substring(0,4)); 
	var nMonth= Number(strDate.substring(4,6)); 
	var nDay	= Number(strDate.substring(6,8)); 
	
	//년 확인	
	if (nYear <= 0) 
		return false; 
	
	//월 확인	
	if (nMonth < 1 || nMonth > 12) return false; 
	
	//윤달 확인	
	if (nYear % 4 == 0) 
		if (nYear % 100 != 0 || nYear % 400 == 0) 
			 END_OF_MONTH[2] = 29; 
	
	//일 확인	
	if (nDay < 1 || END_OF_MONTH[nMonth] < nDay) 
		return false; 
	
	return	true ; 
}

