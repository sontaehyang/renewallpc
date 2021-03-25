<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

		<div class="title">
			<h2>회원가입</h2>
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con join3_wrap">
			<ol class="join_pr cf"> 
				<li><span>STEP.1</span><p>본인인증</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
				<li><span>STEP.2</span><p>약관동의</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
				<li class="on"><span>STEP.3</span><p>정보입력</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
				<li><span>STEP.4</span><p>가입완료</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			</ol>
			<!-- join_pr -->
			<form:form modelAttribute="user" action="/m/users/join" method="post">
			
				<input type="hidden" id="idCheck" />
				<input type="hidden" name="redirect" value="/m/users/login"/>
				<c:if test="${ redirect ne '' }">
					<input type="hidden" name="redirect" value="${ redirect }" />
				</c:if>
			
				<div class="bd_table typeB">
					<ul class="del_info">
						<li>
							<span class="del_tit t_gray">구분</span>
							<div class="num pl0">
								<p>일반회원</p>
							</div>
						</li>
						<li>
							<label for="loginId" class="del_tit t_gray star">아이디</label>
							<div class="input_area">
								<form:input path="loginId" title="아이디" cssClass="required transparent" maxlength="100" />
								<span class="e-info ok" style="display: none;">사용가능한 아이디입니다.</span>
							</div>
						</li>
						<li>
							<label class="del_tit t_gray star">비밀번호</label>
							<div class="input_area">
								<input type="password" name="password" id="password" title="비밀번호" class="required _password _duplicated transparent" minlength="4"  maxlength="20"/>
								<span class="e-info">영문/숫자/특수문자를 혼합하여 8자 이상 20자 이하로 입력하세요.</span>
							</div>
						</li>
						<li>
							<label class="del_tit t_gray star">비밀번호(확인)</label>
							<div class="input_area">
								<input type="password" name="password2" id="password_confirm" title="비밀번호확인" class="required _password _duplicated transparent" minlength="4"  maxlength="20"/>
								<span class="e-info">비밀번호를 한번 더 입력하세요.</span>
							</div>
						</li>
						<li>
							<label class="del_tit t_gray star">이름 </label>
							<div class="input_area">
								<form:hidden path="userName"/>
								<input type="text" name="userName" title="이름" maxlength="50" class="full required disabled transparent" disabled="disabled" value="${user.userName}"/>
							</div>
						</li>
						<li>
							<label for="loginId" class="del_tit t_gray star">이메일</label>
							<div class="input_area">
								<form:input path="email" title="이메일" cssClass="required _email transparent" maxlength="100" />
							</div>
						</li>
						<li class="chunk">
							<label class="del_tit t_gray">생년월일 </label>
							<div class="user_info">
								<div class="num">
									<div class="in_sel">
										<div class="input_area w32p none">
											<select name="birthdayYear" class="full transparent">
												<option value="">선택</option>
												<c:forEach begin="0" end="100" step="1" var="index">
													<option value="${years - index}">${years - index}</option>
												</c:forEach>
											</select>
										</div>
										<div class="input_area w22p none">
											<select name="birthdayMonth" class="full transparent">
												<option value="">선택</option>
												<c:forEach begin="1" end="12" step="1" var="index">
													<option value="${index}">${index}</option>
												</c:forEach>
											</select>
										</div>
										<div class="input_area w22p none">
											<select name="birthdayDay" class="full transparent">
												<option value="">선택</option>
												<c:forEach begin="1" end="31" step="1" var="index">
													<option value="${index}">${index}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="radio_wrap">
								<ul>
									<li>
										<input type="radio" name="birthdayType" id="birthdayType1" value="1" title="생년월일" checked="checked">
										<label for="birthdayType1"><span><span></span></span>양력</label>
									</li>
									<li>
										<input type="radio" name="birthdayType" id="birthdayType2" value="2" title="생년월일">
										<label for="birthdayType2"><span><span></span></span>음력</label>
									</li>
								</ul>
							</div>
						</li>
						<li>
							<label class="del_tit t_gray">성별 </label>
							<div class="num">
								<div class="radio_wrap">
									<ul>
										<li>
											<input type="radio" name="gender" id="gender1" value="M" title="성별" checked="checked">
											<label for="gender1"><span><span></span></span>남성</label>
										</li>
										<li>
											<input type="radio" name="gender" id="gender2" value="F" title="성별">
											<label for="gender2"><span><span></span></span>여성</label>
										</li>
									</ul>
								</div>
							</div>
						</li>
						<li>
							<label class="del_tit t_gray star">휴대폰 </label>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<input type="text" name="phoneNumber1" title="휴대폰번호" class="required _number disabled transparent" maxlength="3" value="${user.userDetail.phoneNumber1}" readonly="readonly"/>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<input type="text" name="phoneNumber2" title="휴대폰번호" class="required _number disabled transparent" maxlength="4" value="${user.userDetail.phoneNumber2}" readonly="readonly"/>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<input type="text" name="phoneNumber3" title="휴대폰번호" class="required _number disabled transparent" maxlength="4" value="${user.userDetail.phoneNumber3}" readonly="readonly"/>
									</div>
								</div>
							</div>
						</li>
						<li>
							<label class="del_tit t_gray">전화번호 </label>
							<div class="num">
								<div class="in_td">
									<div class="input_area">
										<select title="전화번호 앞자리" id="telNumber1" name="telNumber1">
											<c:forEach items="${op:getCodeInfoList('TEL')}" var="codes">
												<option value="${codes.detail}"  ${op:selected(user.userDetail.telNumber1, codes.detail)}> ${codes.label }</option>
											</c:forEach>
										</select>
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<input type="text" name="telNumber2" title="전화번호" class="_number transparent" maxlength="4" />
									</div>
									<div class="in_td dash"></div>
									<div class="input_area">
										<input type="text" name="telNumber3" title="전화번호" class="_number _minlength_4 transparent" maxlength="4" />
									</div>
								</div>
							</div>
						</li>
						<li class="chunk">
							<label class="del_tit t_gray star">주소 </label>
							<div class="user_info">
								<div class="in_td">
									<div class="input_area">
										<input type="hidden" name="post" id="post" title="우편번호" />
										<input type="text" name="newPost" id="newPost" title="우편번호" maxlength="7" class="required w22 disabled transparent" readonly="readonly">
									</div>
								</div>
								<div class="in_td bar"></div>
								<div class="in_td address">
									<button type="button" class="btn_st3 t_lgray02" onclick="openDaumPostcode();">우편번호</button>
								</div>
							</div>
							<div class="input_area">
								<input type="text" name="address" id="address" title="배송지주소" maxlength="100" class="required disabled transparent" readonly="readonly"/>
								<br/><br/>
								<input type="text" name="addressDetail" id="addressDetail" title="배송지상세주소" maxlength="100" class="required transparent"/>
							</div>
						</li>
					<%-- 확인후 적용 필요
						<li>
							<label for="a9" class="del_tit t_gray star">이메일</label>
							<div class="in_td">
								<div class="input_area02">
									<input type="text" class="transparent">
								</div>
								<span class="mail">@</span>
								<div class="input_area02">
									<select name="" id="" class="transparent">
										<option>&nbsp;</option>
										<option>naver.com</option>
										<option>daum.net</option>
									</select>
								</div>
							</div>
						</li>
						<li>
							<label for="a10" class="del_tit t_gray">추천인</label>
							<div class="user_info">
								<div class="in_td">
									<div class="input_area">
										<input id="a8" type="text" class="transparent">
									</div>
								</div>
								<div class="in_td bar"></div>
								<div class="in_td address">
									<button type="button" class="btn_st3 t_lgray02">추천인 검색</button>
								</div>
							</div>
						</li>
					 --%>
						<li>
							<span class="del_tit t_gray star">SMS수신여부 </span>
							<p class="sms_txt">리뉴올PC 제공하는 이벤트 및 다양한 정보의 SMS를 받아보실 수 있습니다.</p>
							<p class="sms_desc">(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)</p>
							<div class="radio_wrap">
								<ul>
									<li>
										<input type="radio" name="receiveSms" id="receiveSms1" value="0" title="SMS수신" class="required" checked="checked">
										<label for="receiveSms1"><span><span></span></span>수신동의</label>
									</li>
									<li>
										<input type="radio" name="receiveSms" id="receiveSms2" value="1" title="SMS수신" class="required" >
										<label for="receiveSms2"><span><span></span></span>수신거부</label>
									</li>
								</ul>
							</div>
						</li>
						<li>
							<span class="del_tit t_gray star">E-MAIL수신여부 </span>
							<p class="sms_txt">리뉴올PC에서 제공하는 이벤트 및 다양한 정보의 SMS를 받아보실 수 있습니다.</p>
							<p class="sms_desc">(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)</p>
							<div class="radio_wrap">
								<ul>
									<li>
										<input type="radio" name="receiveEmail" id="receiveEmail1" value="0" title="이메일수신" class="required" checked="checked">
										<label for="receiveEmail1"><span><span></span></span>수신동의</label>
									</li>
									<li>
										<input type="radio" name="receiveEmail" id="receiveEmail2" value="1" title="이메일수신" class="required" >
										<label for="receiveEmail2"><span><span></span></span>수신거부</label>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>

				<div class="pr_btn cf">
					<button class="btn_st1 reset" type="button" title="가입취소" onclick="location.href='/m'">가입취소</button>
					<button class="btn_st1 b_pink" type="submit" title="다음단계">다음단계</button>
				</div>
				<!-- pr_btn -->
			</form:form>
		</div>
		<!-- 내용 : e -->

	<!-- 다음 주소검색 -->
	<daum:address />
		
<page:javascript>
<script type="text/javascript">
	var loginCheckId = '';
	$(function(){

		$('#user').validator(function() {

			var loginId = $("#loginId").val();
			// 아이디 - 이메일 패턴 체크 주석 처리
			/*if (!$.validator.patterns._email.test(loginId)) {
				alert($.validator.messages._email);
				$("#loginId").focus();
				return false;
			}*/
			
			if (userAvailabilityCheck(loginId, 'loginId') == false) {
				//alert("${op:message('M00160')}");
				alert("이미 등록된 아이디입니다. 다른 아이디를 입력하세요.");
				$("#loginId").focus();
				return false;
			}
			 
			/* var nickname = $('#nickname').val();
			if (userAvailabilityCheck(nickname, 'nickname') == false) {
				alert("이미 등록된 닉네임입니다."); 
				$('#nickname').focus();
				return false;
			} */
			
			/*
			if($("#idCheck").val() == 0 || loginCheckId != $("#email").val()){
				alert("${op:message('M00157')} ");
				$("#idCheckBtn").focus();
				return false;
			}
			*/

			if ($("#password").val() != $("#password_confirm").val()) {
				alert("${op:message('M00158')}");
				$("#password_confirm").fucous();
				return false;
			}

			//Common.confirm("${op:message('M00159')} ", function(form) {
				
			$('#user').submit();
			//});
			return false;
		});

		//idCheck();
		
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
	//function idCheck(){
		/* $("#idCheckBtn").on('click',function(){ */
			$('input:text[name=loginId]').on('change', function(){
				
			var loginId = $("#loginId").val();

			if (loginId != '') {

				// 아이디 - 이메일 패턴 체크 주석 처리
				/*if (!$.validator.patterns._email.test(loginId)) {
					alert($.validator.messages._email);
					$("#loginId").focus();
					return;
				}*/
				
				 var params = {
					'loginId' : loginId
				};
				
				$.post("/users/find-user",params,function(response){
					Common.loading.hide();
					
					var data = response.data;
					
					if (data > 0) {
						//alert("${op:message('M00160')}");
						alert("이미 등록된 아이디입니다. 다른 아이디를 입력하세요.");
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
	//}
	
	
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

	/* function findZipcode(){

		 var params = {
			'post1' : $("#post1").val(),
			'post2' : $("#post2").val()
		 };
		
		$.post('/zipcode/find-address', params, function(resp) {
			if (resp.zipcode) {
				$("#dodobuhyun").val(resp.zipcode.c4);
				$("#address").val(resp.zipcode.c5);
				$("#addressDetail").val(resp.zipcode.c6);
			} else {
				alert("${op:message('M00128')}");
				$("#dodobuhyun").val("");
				$("#address").val("");
				$("#addressDetail").val("");
			}
		});
	} */
	
	function openDaumPostcode() {
		
		var tagNames = {
			'newZipcode'			: 'newPost',
			'zipcode' 				: 'post',
			'zipcode1' 				: 'post1',
			'zipcode2' 				: 'post2',
		}
		
		openDaumAddress(tagNames);
	}
	
</script>
</page:javascript>