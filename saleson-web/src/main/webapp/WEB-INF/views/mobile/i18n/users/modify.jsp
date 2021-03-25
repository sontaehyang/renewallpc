<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

		<div class="title">
			<h2>회원정보</h2>
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
			<ul class="tab_list01 four_tab letter">
				<li class="on"><a href="#">회원정보 수정</a></li>
				<li><a href="/m/mypage/delivery">배송주소록 관리</a></li>
				<li><a href="/m/sns-user/setup-sns">SNS연동설정</a></li>
				<li><a href="/m/users/secede">회원탈퇴</a></li>
			</ul>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<div class="mypage_wrap">
			
			<form:form modelAttribute="user" action="/m/users/modify-action" method="post" >
				<input name="userId" type="hidden" value="${user.userId}"/>
				<input type="hidden" name="isSnsLogin" value="${isSnsLogin}" />
				<input type="hidden" id="idCheck" name="idCheck" value="0"/>
						
				<div class="member_con">
					<div class="bd_table typeB">
						<ul class="del_info">
							<li>
								<span class="del_tit t_gray">구분</span>
								<div class="num pl0">
									<p>${userLevel.levelName}회원</p>
								</div>
							</li>
							<li>
								<label for="a1" class="del_tit t_gray star">아이디</label>
								<div class="input_area">
									<c:choose>
				 						<c:when test='${!isSnsLogin}'>
				 							<input type="text" class="" value="${user.loginId}" readonly="readonly">
				 						</c:when>
				 						<c:otherwise>
				 							<input type="text" name="loginId" id="loginId" title="아이디" class="required" maxlength="20" style="width: 65%;" value='${userSns.email != null ? userSns.email : ""}'/>
				 							<!-- <button type="button" onclick="loginIdCheck();" class="btn_st3 t_lgray02" style="width: 34%; height: 39px; float: right;">중복확인</button> -->
					 						<span class="e-info">아이디는 설정 후 수정이 불가능합니다.</span>
				 						</c:otherwise>
				 					</c:choose>
									 																	
								</div>								
							</li>
							<li>
								<label for="a2" class="del_tit t_gray star">비밀번호</label>
								<div class="input_area">
									<input type="password" id="password" name="password" class='_password optional  ${isSnsLogin ? "required" : ""}' title="비밀번호" minlength="8"  maxlength="20">
									<span class="e-info">패스워드은(는) 8~20 자리의 영문+숫자+특수 문자로 되어야 합니다.</span>
								</div>
							</li>
							<li>
								<label for="a3" class="del_tit t_gray star">비밀번호(확인)</label>
								<div class="input_area">
									<input type="password" id="password_confirm" name="password2" class="_password optional ${isSnsLogin ? "required" : ""}" title="비밀번호확인" minlength="8"  maxlength="20">
									<span class="e-info">비밀번호를 한번 더 입력하세요.</span>
								</div>
							</li>
							<li>
								<label for="a4" class="del_tit t_gray star">이름</label>
		 						<div class="input_area">
									<input id="userName" name="userName" type="text" class="" value="${user.userName}" ${!isSnsLogin ? "readonly" : ""}>
								</div>
								<c:if test='${isSnsLogin}'>
		 							<span class="e-info">본명이 아닐 경우에 변경해주세요. 변경 후에는 수정이 불가능합니다. </span>
		 						</c:if>
							</li>
							<li class="chunk">
								<label for="a5" class="del_tit t_gray">생년월일</label>
								<div class="user_info">
									<div class="num">
										<div class="in_sel">
											<c:choose>
						 						<c:when test="${empty user.userDetail.birthdayYear || empty user.userDetail.birthdayMonth || empty user.userDetail.birthdayDay}">
													<div class="input_area w32p none">
														<select name="birthdayYear" id="birthdayYear" class="" value="${user.userDetail.birthdayYear}">
															<option value="">선택</option>
															<c:forEach begin="0" end="100" step="1" var="index">
															<option value="${years - index}" label="${years - index}" ${op:selected(user.userDetail.birthdayYear, years - index)}/>
															</c:forEach>	
														</select>
													</div>
													<div class="input_area w22p none">
														<select name="birthdayMonth" id="birthdayMonth" class="" value="${user.userDetail.birthdayMonth }">
															<option value="">선택</option>
															<c:forEach begin="1" end="12" step="1" var="index">
											        		<option value="${index}" label="${index}" ${op:selected(user.userDetail.birthdayMonth, index)}/>
											       			 </c:forEach>
														</select>
													</div>
													<div class="input_area w22p none">
														<select name="birthdayDay" id="birthdayDay" class="" value="${user.userDetail.birthdayDay}">													
															<option value="">선택</option>
															<c:forEach begin="1" end="31" step="1" var="index">
															<option value="${index}" label="${index}" ${op:selected(user.userDetail.birthdayDay, index)}/>
															</c:forEach> 
														</select>
													</div>
												</c:when>
												<c:otherwise>
													<p style="font-weight: bold; height: auto;">
														${user.userDetail.birthdayYear}년 ${user.userDetail.birthdayMonth}월 ${user.userDetail.birthdayDay}일
													</p>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
								<div class="radio_wrap">
									<c:choose>
				 						<c:when test="${empty user.userDetail.birthdayYear || empty user.userDetail.birthdayMonth || empty user.userDetail.birthdayDay}">
											<ul>
											    <li>
											        <input type="radio" id="birthdayType1" name="birthdayType" value="1" title="생년월일" checked="checked" class="${required}" ${op:checked(user.userDetail.birthdayType,'1')}>
											        <!-- <label for="birth_agree" >양력</label>&nbsp;&nbsp; -->
											        <label for="birthdayType1"><span><span></span></span>양력</label>
											       
											    </li>
											    <li>
											        <input type="radio" id="birthdayType2"  name="birthdayType" value="2" title="생년월일" class="${required}" ${op:checked(user.userDetail.birthdayType,'2')}>
											        <!-- <label for="birth_disagree">음력</label> -->
											        <label for="birthdayType2"><span><span></span></span>음력</label>
											    </li>
											</ul>
										</c:when>
										<c:otherwise>
											<p style="font-weight: bold; margin-top: 10px; line-height: normal;">
												${user.userDetail.birthdayType == "1" ? "양력" : user.userDetail.birthdayType == "2" ? "음력" : ""}
											</p>
										</c:otherwise>
									</c:choose>
								</div>
							</li>
							<li>
								<label for="a6" class="del_tit t_gray">성별 </label>
								<div class="num">
									<div class="radio_wrap">
										<ul>
											<li>
												<input type="radio" name="gender" id="gender1" value="M" title="성별" ${op:checked(user.userDetail.gender,'M')}>
												<label for="gender1"><span><span></span></span>남성</label>
											</li>
											<li>
												<input type="radio" name="gender" id="gender2" value="F" title="성별" ${op:checked(user.userDetail.gender,'F')}>
												<label for="gender2"><span><span></span></span>여성</label>
											</li>
										</ul>
									</div>
								</div>
							</li>
							<li>
								<span class="del_tit t_gray star">휴대폰</span>
								<div class="num">
									<div class="in_td">
										<div class="input_area w22p none">
											<select class="required" title="-선택-" id="phoneNumber1" name="phoneNumber1">
												<c:forEach items="${op:getCodeInfoList('PHONE')}" var="codes">
													<option value="${codes.detail}"  ${op:selected(user.userDetail.phoneNumber1, codes.detail)}> ${codes.label}</option>
												</c:forEach>
											</select>
											<%-- <input type="text" class="_number" name="phoneNumber1" id="phoneNumber1" title="휴대폰" maxlength="3" value="${user.userDetail.phoneNumber1}"> --%>
										</div>
										<div class="in_td dash"></div>
										<div class="input_area">
											<input type="text" class="_number required" name="phoneNumber2" id="phoneNumber2" title="휴대폰" maxlength="4" value="${user.userDetail.phoneNumber2}">
										</div>
										<div class="in_td dash"></div>
										<div class="input_area">
											<input type="text" class="_number required" name="phoneNumber3" id="phoneNumber3" title="휴대폰" maxlength="4" value="${user.userDetail.phoneNumber3}">
										</div>
									</div>
								</div>
							</li>
							<li>
								<span class="del_tit t_gray">전화번호</span>
								<div class="num">
									<div class="in_td">
										<div class="input_area">
											<select title="전화번호 앞자리" id="telNumber1" name="telNumber1">
												<c:forEach items="${op:getCodeInfoList('TEL')}" var="codes">
													<option value="${codes.detail}"  ${op:selected(user.userDetail.telNumber1, codes.detail)}> ${codes.label }</option>
												</c:forEach>
											</select>
											<%-- <input type="text" class="_number" name="telNumber1" id="telNumber1" title="전화번호" maxlength="3" value="${user.userDetail.telNumber1}"> --%>
										</div>
											<div class="in_td dash"></div>
										<div class="input_area">
											<input type="text" class="_number" name="telNumber2" id="telNumber2" title="전화번호" maxlength="4" value="${user.userDetail.telNumber2}">
										</div>
											<div class="in_td dash"></div>
										<div class="input_area">
											<input type="text" class="_number" name="telNumber3" id="telNumber3" title="전화번호" maxlength="4" value="${user.userDetail.telNumber3}">
										</div>
									</div>
								</div>
							</li>
								
								
							<li class="chunk">
								<label for="a8" class="del_tit t_gray star">주소</label>
								<div class="user_info">
									<div class="in_td">
										<input id="a8" type="hidden" name="post" class="" value="${user.userDetail.post}" />
										<input type="text" name="newPost" class="required" value="${user.userDetail.newPost}" title="우편번호" readonly="readonly">											
									</div>
									<div class="in_td address">
										<button type="button" class="btn_st3 t_lgray02" onclick="openDaumPostcode();">우편번호</button>
									</div>
								</div>
								<div class="input_area">
									<input type="text" class="required" name="address" id="address" value="${user.userDetail.address}" title="${op:message('M00118')}" readonly="readonly" >
								</div>
							</li>
							<li>
								<span class="del_tit t_gray star">상세주소</span>
								<input type="text" class="required" name="addressDetail" value="${user.userDetail.addressDetail}" title="${op:message('M00119')}">
							</li>	
							<li>
								<span class="del_tit t_gray star">SMS수신여부</span>
								<p class="sms_txt">리뉴올PC에서 제공하는 이벤트 및 다양한 정보의 SMS를 받아보실 수 있습니다.</p>
								<p class="sms_desc">(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)</p>
								<div class="radio_wrap">
									<ul>
									    <li>
									        <input type="radio" name="receiveSms" id="receiveSms1" value="0" checked="checked" ${op:checked('0', user.userDetail.receiveSms)} title="SMS 수신동의">
									        <label for="receiveSms1"><span><span></span></span>수신동의</label>
									    </li>
									    <li>
									        <input type="radio" name="receiveSms" id="receiveSms2" value="1" ${op:checked('1', user.userDetail.receiveSms)} title="SMS 수신동의" >
									        <label for="receiveSms2"><span><span></span></span>수신거부</label>
									    </li>
									</ul>
								</div>
							</li>
							<li>
								<span class="del_tit t_gray star">E-MAIL수신여부</span>
								<p class="sms_txt">리뉴올PC에서 제공하는 이벤트 및 다양한 정보의 SMS를 받아보실 수 있습니다.</p>
								<p class="sms_desc">(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)</p>
								<div class="radio_wrap">
									<ul>
									    <li>
									        <input type="radio" id="receiveEmail1" name="receiveEmail" value="0" checked="checked" title="이메일 수신동의" ${op:checked(user.userDetail.receiveEmail,'0')}>
									        <label for="receiveEmail1"><span><span></span></span>수신동의</label>
									    </li>
									    <li>
									        <input type="radio" id="receiveEmail2" name="receiveEmail" value="1" title="이메일 수신동의" ${op:checked(user.userDetail.receiveEmail,'1')}>
									        <label for="receiveEmail2"><span><span></span></span>수신거부</label>
									    </li>
									</ul>
								</div>
							</li>
						</ul>
					</div>	
					<div class="btn_wrap cf">
						<button type="button" class="btn_st1 reset" title="취소" onclick="location.href='/m/users/editMode'">취소</button>
						<button type="submit" class="btn_st1 decision" title="수정">수정</button>
					</div>
				</div>
				<!-- //member_con -->
			
			</form:form>
			
			</div>
			<!-- //mypage_wrap -->
		
		</div>
		<!-- 내용 : e -->
	 
<!-- 다음 주소검색 -->
<daum:address />

<page:javascript>
<script type="text/javascript">
<c:choose>
	<c:when test='${!isSnsLogin}'>
		var isIdDuplicated = false;
	</c:when>
	<c:otherwise>
		var isIdDuplicated = true;
	</c:otherwise>
</c:choose>
	var loginCheckId = '';
	$(function(){

		$('#user').validator(function() {
			// 아이디 중복체크(SNS아이디일 경우) 2017-06-12_seungil.lee
			/* if (isIdDuplicated) {
				alert("아이디 중복체크를 해주세요.");
				return false;
			} */
			
			if(isIdDuplicated == true) {
				var loginId = $('#loginId').val();
				if (userAvailabilityCheck(loginId, 'loginId') == false) {
					//alert("${op:message('M00160')}");
					alert("이미 등록된 메일주소입니다. 같은 메일주소로 회원등록은 불가능합니다.");
					$("#loginId").focus();
					return false;
				}
			}

			/* var nickname = $('#nickname').val();
			if ($('#orgNickname').val() != nickname) {
				if (userAvailabilityCheck(nickname, 'nickname') == false) {
					alert("이미 등록된 닉네임입니다.");
					$('#nickname').focus();
					return false;
				}
			} */
			
			if ($("#password").val() != $("#password_confirm").val()) {
				alert("${op:message('M00158')}");
				$("#password_confirm").focus();
				return false;
			}
				
			// 생년월일 입력 체크 2017-06-12_seungil.lee
			var year = $('select[name="birthdayYear"] option:selected').val();
			var month = $('select[name="birthdayMonth"] option:selected').val();
			var day = $('select[name="birthdayDay"] option:selected').val();
			
			if (year == "" && month == "" && day == "") {
				return true;
			}
			else if (year != "" && month != "" && day != "") {
				return true;
			}
			else {
				alert("생년월일을 정확하게 입력하여 주세요.");
				return false;
			}

			Common.confirm("회원정보를 수정하시겠습니까?", function(form) {
				
				$('#user').submit();
			});
			return false;
		});

		//idCheck();
		
		loginIdEvent();
		
		/* $('#nicknameCheckBtn').on('click', function() {
			var nickname = $("#nickname").val();
			if (nickname == '') {
				alert("닉네임을 입력해주세요.");
				$('#nickname').focus();
				return false;
			}
			
			if (userAvailabilityCheck(nickname, 'nickname') == false) {
				alert("이미 등록된 닉네임입니다.");
				$('#nickname').focus();
				return false;
			} else {
				alert("사용가능한 닉네임입니다.");
				return false;
			}
		}); */
		
	});

	function userAvailabilityCheck(value, type) {
		var params = {
			'type'		: type,
			'value' 	: value
		};
		
		var returnValue = false;
		$.post("/users/user-availability-check-join", params, function(response){
			Common.loading.hide();
			var data = response.data;
			returnValue = data;
		}, 'json').error(function(e){
			alert(e.message);
		});
		
		return returnValue;
	}
	
	/* id 중복 체크 */
	function idCheck(){
		$("#idCheckBtn").on('click',function(){
			var loginId = $("#email").val();

			if (loginId != '') {

				 var params = {
					'loginId' : loginId
				};
				
				$.post("/users/find-user",params,function(response){
					Common.loading.hide();

					if (response.userCount > 0) {
						alert("${op:message('M00160')}");
						$("#idCheck").focus();
					} else {
						$("#idCheck").val('1');
						alert("${op:message('M00161')}");
						loginCheckId = $("#loginId").val();
					}
					
				});
			} else {
				alert("${op:message('M00162')}");
				return false;
			}
		});
	}

	
	function openDaumPostcode() {
		var tagNames = {
			'newZipcode'			: 'newPost',
			'zipcode' 				: 'post',
			'zipcode1' 				: 'post1',
			'zipcode2' 				: 'post2',
		}
		openDaumAddress(tagNames);
	}
	
	function loginIdCheck() {
		var param = {
				"loginId" : $("#loginId").val()
		}
		$.post("/sns-user/loginId-check", param, function(response){
			if (response.status == "00") {
				if (response.value == 0) {
					isIdDuplicated = false;
					alert("사용가능한 아이디입니다.");
				}
				else {
					isIdDuplicated = true;
					alert("이미 사용중인 아이디입니다. 확인 후 다시 입력해주세요.");
				}
				
			}
			else {
				console.log("Error Occurred - " + response.message);
			}
		});
	}
	
	function loginIdEvent() {
		$('input:text[name=loginId]').on('change', function(){
			var loginId = $("#loginId").val();

			if (loginId != '') {

				if (!$.validator.patterns._email.test(loginId)) {
					alert($.validator.messages._email);
					$("#loginId").focus();
					return;
				}
				
				 var params = {
					'loginId' : loginId
				};
				
				$.post("/users/find-user",params,function(response){
					Common.loading.hide();
					
					var data = response.data;
					
					if (data > 0) {
						alert("${op:message('M00160')}");
						$("#loginId").focus();
					} else {
						$("#idCheck").val('1');
						$('.e-info').css('display', 'block');
						loginCheckId = $("#loginId").val();
					}
					
				});
			} else {
				alert("이메일 주소를 입력해주세요.");
				return false;
			}
		});
	}
</script>
</page:javascript>