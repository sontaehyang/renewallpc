<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
	
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
		<div class="join_step step03">
			<ul>
				<li><span>01</span> 본인인증</li>
				<li><span>02</span> 약관동의</li>
				<li class="on"><span>03</span> 정보입력</li>
				<li><span>04</span> 가입완료</li>
			</ul>
		</div> <!-- // join_step E -->
		
		<h3 class="sub_title mt30">정보입력 <span class="necessary">표시가 있는 부분은 필수 입력사항입니다.</span></h3>
		
		<form:form modelAttribute="user" action="/users/join" method="post">
			<input type="hidden" id="idCheck" />
			<c:if test="${ redirect ne '' }">
				<input type="hidden" name="redirect" value="${ redirect }" />
			</c:if> 
			
			<div class="board_wrap">
	 			<table cellpadding="0" cellspacing="0" class="board-write">
		 			<caption>정보입력</caption>
		 			<colgroup>
		 				<col style="width:190px;">
		 				<col style="width:auto;">	 				
		 			</colgroup>
		 			<tbody>
		 				<tr>
							<tr>
								<th scope="row">회원구분 <span class="necessary"></span></th>
								<td>일반회원</td>
							</tr>
							<th scope="row">${op:message('M00081')} <span class="necessary"></span></th>
							<td>
								<div class="input_wrap col-w-7">
									<form:input path="loginId" title="${op:message('M00081')} " cssClass="full required" maxlength="100" />
								</div>&nbsp; &nbsp;
								<div class="input_wrap">
									<button type="button" id="idCheckBtn" class="btn btn-ms btn-default">${op:message('M01488')}</button> <!-- 중복확인 -->
								</div>
							</td>
		 				</tr>
		 				<tr>
		 					<th scope="row">비밀번호 <span class="necessary"></span></th>
		 					<td>
		 						<div class="input_wrap col-w-7">
		 							<input type="password"  name="password" id="password" title="비밀번호" cssClass="required _password _duplicated" minlength="4"  maxlength="20"/>
		 						</div> 
		 						<span class="coment">비밀번호는 영문/숫자/특수문자를 혼합하여 8자 이상 20자 이하로 입력하세요.</span>
		 					</td>
		 				</tr>
		 				<tr>
		 					<th scope="row">비밀번호(확인) <span class="necessary"></span></th>
		 					<td>
		 						<div class="input_wrap col-w-7">
		 							<input type="password" name="password2" id="password_confirm" title="비밀번호(확인)" class=" required _password _duplicated" minlength="4"  maxlength="20"/>
		 						</div> 
		 						<span class="coment">비밀번호 확인을 위하여 다시 한번 입력하여 주세요.</span>
		 					</td>
		 				</tr>
		 				
		 				<%--
			 				<tr>
			 					<th scope="row">닉네임 <span class="necessary"></span></th>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<input type="text" name="nickname" id="nickname" title="닉네임" maxlength="20" cssClass="full required" />
			 						</div>&nbsp; &nbsp; 
			 						<div class="input_wrap">
			 							<button type="button" id="nicknameCheckBtn" class="btn btn-ms btn-default">${op:message('M01488')}</button>
			 						</div>
			 					</td>
			 				</tr>
		 				--%>

						<tr>
							<th scope="row">이메일 <span class="necessary"></span></th>
							<td>
								<div class="input_wrap col-w-7">
									<form:input path="email" title="${op:message('M00125')}" cssClass="full required _email" maxlength="50" />
								</div>
							</td>
						</tr>

						<tr>
							<th scope="row">이름 <span class="necessary"></span></th>
							<td>
								<div class="input_wrap col-w-7">
									<form:hidden path="userName"/>
									<input type="text" name="userName" title="이름" maxlength="50" class="required disabled" disabled="disabled" value="${user.userName}"/>
								</div>
							</td>
						</tr>

						<tr>
							<th scope="row">생년월일 </th>
							<td>
								 <div class="input_wrap col-w-10">
									<select class="form-control" name="birthdayYear">
										<option value="">선택</option>
										<c:forEach begin="0" end="100" step="1" var="index">
											<option value="${years - index}" label="${years - index}">
										</c:forEach>
									</select>
								  </div>
								  <span class="connection"> 년 </span> &nbsp;&nbsp;
								  <div class="input_wrap col-w-10">
									<select class="form-control" name="birthdayMonth">
										<option value="">선택</option>
										<c:forEach begin="1" end="12" step="1" var="index">
											<option value="${index}" label="${index}">
										</c:forEach>
									</select>
								 </div>
								 <span class="connection"> 월 </span> &nbsp;&nbsp;
								 <div class="input_wrap col-w-10">
									<select class="form-control" name="birthdayDay">
										<option value="">선택</option>
										<c:forEach begin="1" end="31" step="1" var="index">
											<option value="${index}" label="${index}">
										</c:forEach>
									</select>
								 </div>
								 <span class="connection"> 일 </span> &nbsp;&nbsp;

								<div class="input_wrap">
									<input type="radio" name="birthdayType" id="birthdayType1" value="1" title="생년월일" checked="checked"><label for="birthdayType1">양력</label>&nbsp;&nbsp;
									<input type="radio" name="birthdayType" id="birthdayType2" value="2" title="생년월일"><label for="birthdayType2">음력</label>
								</div>

							</td>
						</tr>
						<tr>
							<th scope="row">성별</th>
							<td>
								 <div class="input_wrap">
									<input type="radio" id="man" name="gender" value="M" title="성별" checked="checked"> <label for="man">남성</label>&nbsp;&nbsp;
									<input type="radio" id="woman" name="gender" value="F" title="성별"> <label for="woman">여성</label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">전화번호</th>
							<td>
								<div class="hp_area">
									<%-- <div class="input_wrap col-w-10">
										<input type="text" name="telNumber1" title="전화번호" class="full required _number" maxlength="3" />
									</div> --%>
									<!-- 전화번호 코드 추가 [2017-04-26]minae.yun -->
									<select class="custom_select large" title="전화번호" id="telNumber1" name="telNumber1" style="width:95px">
										<option selected="selected" value="">-선택-</option>
										<c:forEach items="${telCodes}" var="codes">
											<option>${codes.label }</option>
										</c:forEach>
									</select>
									<span class="connection"> - </span>
									<div class="input_wrap col-w-10">
										<input type="text" name="telNumber2" title="전화번호" class="full _number" maxlength="4" />
									</div>
									<span class="connection"> - </span>
									<div class="input_wrap col-w-10">
										<input type="text" name="telNumber3" title="전화번호" class="full _number _minlength_4" maxlength="4" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">휴대폰번호 <span class="necessary"></span></th>
							<td>
								<div class="hp_area">
									<%-- <div class="input_wrap col-w-10">
										<input type="text" name="phoneNumber1" title="휴대폰번호" class="full required _number disabled" maxlength="3" readonly="readonly" value="${user.userDetail.phoneNumber1}" />
									</div> --%>
									<!-- 휴대폰번호 코드 추가 [2017-04-26]minae.yun -->
									<select class="custom_select large required" title="휴대폰번호" id="phoneNumber1" name="phoneNumber1" style="width:95px">
										<option value="">-선택-</option>
										<c:forEach items="${phoneCodes}" var="codes">
											<option ${codes.label == user.userDetail.phoneNumber1 ? 'selected="selected"' : ''} >${codes.label }</option>
										</c:forEach>
									</select>
									<span class="connection"> - </span>
									<div class="input_wrap col-w-10">
										<input type="text" name="phoneNumber2" title="휴대폰번호" class="full required _number disabled" maxlength="4" readonly="readonly" value="${user.userDetail.phoneNumber2}"/>
									</div>
									<span class="connection"> - </span>
									<div class="input_wrap col-w-10">
										<input type="text" name="phoneNumber3" title="휴대폰번호" class="full required _number disabled" maxlength="4" readonly="readonly" value="${user.userDetail.phoneNumber3}"/>
									</div>
								</div>
							</td>
						</tr>

						<tr>
							<th scope="row"  valign="top">주소 <span class="necessary"></span></th>
							<td>
								<div>

									 <div class="input_wrap col-w-10">
										<input type="hidden" name="post" id="post" title="우편번호" />
										<input type="text" name="newPost" id="newPost" title="우편번호" maxlength="7" class="required disabled" readonly="readonly">
									</div>
									<div class="input_wrap">
										<button type="button" onclick="openDaumPostcode()" class="btn btn-ms btn-default" title="우편번호">우편번호</button>
									</div>
									<div class="input_wrap mt8 col-w-0">
										<input type="text" name="address" id="address" title="주소" maxlength="100" class="full required disabled" readonly="readonly">
									</div>
									<div class="input_wrap mt8 col-w-0">
										<input type="text" name="addressDetail" id="addressDetail" title="${op:message('M00119')}" maxlength="100" class="full required"/>
									</div>

								</div>
							</td>
						</tr>

						<tr>
							<th scope="row" valign="top">SMS 수신동의 <span class="necessary"></span></th>
							<td>
								<p>
									<input type="radio" name="receiveSms" id="receiveSms1" value="0" ${op:checked('0', userDetail.receiveSms)} title="SMS 수신동의" class="required" ><label for="receiveSms1">${op:message('M00233')}</label>&nbsp;&nbsp;
									<input type="radio" name="receiveSms" id="receiveSms2" value="1" ${op:checked('1', userDetail.receiveSms)} title="SMS 수신동의" class="required" ><label for="receiveSms2">${op:message('M00234')}</label>
								</p>
								<p class="coment mt5">
									수신동의시 쇼핑몰에서 제공하는 이벤트 및 다양한 정보를 SMS로 받아볼 수 있습니다.  <br>
									단, 회원가입, 거래정보-결제/배송/교환/환불 등과 같은 운영정보는 수신동의 여부와 관계없이 발송됩니다.)

								</p>
							</td>
						</tr>

						<tr>
							<th scope="row" valign="top">Email 수신동의 <span class="necessary"></span></th>
							<td>
								<p>
									<input type="radio" name="receiveEmail" id="receiveEmail1" value="0" ${op:checked('0', userDetail.receiveEmail)} title="이메일 수신동의" class="required" ><label for="receiveEmail1">${op:message('M00233')}</label>&nbsp;&nbsp;
									<input type="radio" name="receiveEmail" id="receiveEmail2" value="1" ${op:checked('1', userDetail.receiveEmail)} title="이메일 수신동의" class="required" ><label for="receiveEmail2">${op:message('M00234')}</label>
								</p>
								<p class="coment mt5">
									수신동의시 쇼핑몰에서 제공하는 이벤트 및 다양한 정보를 E-mail로 받아볼 수 있습니다.  <br>
									단, 회원가입, 거래정보-결제/배송/교환/환불 등과 같은 운영정보는 수신동의 여부와 관계없이 발송됩니다.)

								</p>
							</td>
						</tr>

						<tr>
							<th scope="row" valign="top">Push 수신동의 <span class="necessary"></span></th>
							<td>
								<p>
									<input type="radio" name="receivePush" id="receivePush1" value="0" ${op:checked('0', userDetail.receivePush)} title="Push 수신동의" class="required" ><label for="receivePush1">${op:message('M00233')}</label>&nbsp;&nbsp;
									<input type="radio" name="receivePush" id="receivePush2" value="1" ${op:checked('1', userDetail.receivePush)} title="Push 수신동의" class="required" ><label for="receivePush2">${op:message('M00234')}</label>
								</p>
								<p class="coment mt5">
									수신동의시 쇼핑몰에서 제공하는 이벤트 및 다양한 정보를 SMS로 받아볼 수 있습니다.  <br>
									단, 회원가입, 거래정보-결제/배송/교환/환불 등과 같은 운영정보는 수신동의 여부와 관계없이 발송됩니다.)
								</p>
							</td>
						</tr>

			 			
			 			<%-- 확인필요 (현재 퍼블리싱에는 없습니다) 2017-03-21
						<tr>
							<th scope="row" valign="top">영업형태<br>(복수선택가능) <span class="necessary"></span></th>
							<td>
								<div class="search-box required">
									<ul>
										<c:set var="lastClass" value="" />
										<c:forEach items="${businessCodes}" var="businessCode" varStatus="i">
											<c:if test="${!i.first}">
												<c:set var="required" value="" />
											</c:if>
											<c:if test="${i.last}">
												<c:set var="lastClass">class="last"</c:set>
											</c:if>
											<li>
												<input type="checkbox" name="businessType" id="businessType_${i.index}" value="${businessCode.businessCodeId}" class="required" title="${op:message('M00183')}"><label for="businessType_${i.index}" class="checkbox-inline">${businessCode.id}</lable>
											</li>
											<c:if test="${i.last}">
												 <li ${lastClass}>
													<div>기타&nbsp;</div>
													<div class="input_wrap typeA col-w-1"><input type="text" name="businessTypeEtc" id="businessTypeEtc" title="기타"  value="${user.userDetail.businessTypeEtc}" maxlength="100" /></div>
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>

			 			 
						<tr>
							<th scope="row">회사명 </th>
							<td>
								<div class="input_wrap col-w-5">
									<input type="text" title="회사명" name="companyName" class="required">
								</div>
								<span class="coment">사업자의 경우 입력해주세요.</span>
							</td>
						</tr>
						<tr>
							<th scope="row">사업자 등록번호 </th>
							<td>
								<div class="input_wrap col-w-10">
									<input type="text" name="businessNumber1" title="사업자 등록번호" class="required _number" maxlength="3">
								</div>
								<span class="connection"> - </span>
								<div class="input_wrap col-w-10">
									<input type="text" name="businessNumber2" title="사업자 등록번호" class="required _number" maxlength="2">
								</div>
								<span class="connection"> - </span>
								<div class="input_wrap col-w-10">
									<input type="text" name="businessNumber3" title="사업자 등록번호" class="required _number" maxlength="5">
								</div>
								<span class="coment">사업자에게 발행하는 세금계산서 정보이며 개인은 입력하지 않습니다.</span>
							</td>
						</tr>
						<tr>
							<th scope="row">사업자 업태</th>
							<td>
								<input type="text" name="businessStatus" title="사업자 업태" class="" maxlength="50">
							</td>
						</tr>
						<tr>
							<th scope="row">사업자 업태 종목</th>
							<td>
								<input type="text" name="businessStatusType" title="사업자 업태 종목" class="" maxlength="50">
							</td>
						</tr>
			 			--%>
			 			
		 			</tbody>
		 		</table><!--//view E-->
		 			
		 		<div class="btn_wrap">
		 			<button type="button" onclick="location.href='/'" class="btn btn-default btn-lg">가입취소</button>
		 			<button type="submit" class="btn btn-success btn-lg">다음단계</button>
		 		</div> 	
		 	</div><!--// 회원정보 입력 끝--> 		
		</form:form>
	</div>

<!-- 다음 주소검색 -->
<daum:address />

<page:javascript>
<script type="text/javascript">
	var loginCheckId = '';
	$(function(){

		$('#user').validator(function() {

			//var loginId = $("#email").val();
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
	
	/* id 중복 체크 - 사용 안함 */
	//function idCheck(){
		$("#idCheckBtn").on('click',function(){
			//var loginId = $("#email").val();
			var loginId = $("#loginId").val();
			
			if (!loginId == '') {
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
						//$("#email").focus();
						alert("이미 등록된 아이디입니다. 다른 아이디를 입력하세요.");
						$("#loginId").focus();
					} else {
						$("#idCheck").val('1');
						alert("${op:message('M00161')}");
						//loginCheckId = $("#email").val();
						loginCheckId = $("#loginId").val();
					}
					
				});
			} else {
				alert("아이디를 입력해주세요.");
				$("#loginId").focus();
				return false;
			}
		});
	//}
	
	
	/* $("#nicknameCheckBtn").on('click',function(){
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
