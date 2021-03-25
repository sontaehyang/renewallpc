<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div class="inner">
	
	<div class="content_top">
 		<div class="breadcrumbs">
 			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/delivery">회원정보</a> 
			<span>회원정보수정</span>  
		</div>
 	</div><!--//content_top E-->
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<div id="contents" class="pt0">
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
		
		<div class="contents_inner">  
		 	<h2>회원정보수정</h2>
		 	<h3 class="type02 mt30">정보입력 <span>표시가 있는 부분은 필수 입력사항입니다.</span></h3> 
		 	<form:form modelAttribute="user" action="/users/modify-action" method="post">
			 	<div class="board_write_type01">
			 		<input name="userId" type="hidden" value="${user.userId}" />
					<input type="hidden" id="idCheck" name="idCheck" value="0" />
					<input type="hidden" name="isSnsLogin" value="${isSnsLogin}" />
					
		 			<table cellpadding="0" cellspacing="0" class="board-write">
			 			<caption>회원정보수정</caption>
			 			<colgroup>
			 				<col style="width:190px;">
			 				<col style="width:auto;">	 				
			 			</colgroup>
			 			<tbody>
			 				<tr>
			 					<th scope="row">회원구분 <span class="necessary"></span></th>
			 					<td>
			 						 <div>${userLevel.levelName}회원</div>
			 					</td>
			 				</tr>
			 				
							<tr>
								<th scope="row">이름 <span class="necessary"></span></th>
								<td>

									<c:choose>
										<c:when test='${!isSnsLogin}'>
											<div>
												<span class="userName">${user.userName}</span>
												<form:hidden path="userName" style="margin-right: 5px;" class="required"/>
											</div>
										</c:when>
										<c:otherwise>
											<div class="input_wrap col-w-7">
												<form:input path="userName" style="margin-right: 5px;" class="required w90"/>
											</div>
											<span class="coment">본명이 아닐 경우에 변경해주세요. 변경 후에는 수정이 불가능합니다. </span>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
			 				
			 				<tr>
			 					<th scope="row">아이디 <span class="necessary"></span></th>
			 					<td>
			 						<c:choose>
				 						<c:when test='${!isSnsLogin}'>
				 							<div>${user.loginId}</div>
				 						</c:when>
				 						<c:otherwise>
				 							<div class="input_wrap col-w-7">
					 							<input type="text" name="loginId" id="loginId" title="아이디" class="w90 optional required" maxlength="40" value='${userSns.email != null ? userSns.email : ""}'/>
					 						</div>
				 							<div class="input_wrap">
					 							<button type="button" onclick="loginIdCheck();" class="btn btn-ms btn-default">중복확인</button>
				 							</div>
				 							<span class="coment">아이디는 설정 후 수정이 불가능합니다.</span>
				 						</c:otherwise>
				 					</c:choose>
			 					</td>
			 				</tr>
							<tr>
								<th scope="row">이메일주소 <span class="necessary"></span></th>
								<td>
									<div>${user.email}</div>
									<input type="hidden" id="orgEmail" name="orgEmail" value="${ user.email }" />
								</td>
							</tr>
			 				<%-- <tr>
				 					<th scope="row">이메일주소 <span class="necessary"></span></th>
				 					<td>
				 						 <div>${user.email}</div>
				 						 <input type="hidden" id="orgEmail" name="orgEmail" value="${ user.email }" />
				 					</td>
				 					<th scope="row">이메일주소 <span class="necessary"></span></th>
				 					<td>
				 						 <div>${user.email}</div>
				 						 <input type="hidden" id="orgEmail" name="orgEmail" value="${ user.email }" />
				 					</td>
			 				</tr> --%>
			 				<tr>
			 					<th scope="row">비밀번호 <span class="necessary"></span></th>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<input type="password" name="password" id="password" title="패스워드" class="w90 _password optional required" minlength="8"  maxlength="20" />
			 						</div> 
			 						<span class="coment">패스워드은(는) 8~20 자리의 영문+숫자+특수 문자로 되어야 합니다.</span>
			 					</td>
			 				</tr>
			 				<tr>
			 					<th scope="row">비밀번호(확인) <span class="necessary"></span></th>
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<input type="password" name="password2" id="password_confirm" title="패스워드(확인)" class="w90 _password optional required" minlength="8"  maxlength="20" />
			 						</div> 
			 						<span class="coment">비밀번호 확인을 위하여 다시 한번 입력하여 주세요.</span>
			 					</td>
			 				</tr>
				 			
							<tr>
								<th scope="row">휴대폰번호 <span class="necessary"></span></th>
								<td>
									<div>
										<%-- <div class="input_wrap col-w-10">
											<input type="text" name="phoneNumber1" name="phoneNumber1" title="휴대폰번호" value="${user.userDetail.phoneNumber1}" class="required full" maxlength="4" />
										</div> --%>
										<!-- 휴대폰번호 selectBox로 수정 [2017-04-26]minae.yun -->
										<select class="custom_select large" title="-선택-" id="input_number" style="width:95px" name="phoneNumber1">
											<option value="">-선택-</option>
											<c:forEach items="${phoneCodes}" var="codes">
												<option value="${codes.label }"  ${op:selected(user.userDetail.phoneNumber1, codes.label)}> ${codes.label }</option>
											</c:forEach>
										</select>
										<span class="connection"> - </span>
										<div class="input_wrap col-w-10">
											<input type="text" name="phoneNumber2" name="phoneNumber2" title="휴대폰번호" value="${user.userDetail.phoneNumber2}" class="required full" maxlength="4" />
										</div>
										<span class="connection"> - </span>
										<div class="input_wrap col-w-10">
											<input type="text" name="phoneNumber3" name="phoneNumber3" title="휴대폰번호" value="${user.userDetail.phoneNumber3}" class="required full" maxlength="4" />
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">전화번호</th>
								<td>
									<div>
										<%-- <div class="input_wrap col-w-10">
											<input type="text" name="telNumber1" id="telNumber1" title="전화번호" value="${user.userDetail.telNumber1}" class="required full" maxlength="4" />
										</div> --%>
										<!-- 전화번호 selectBox로 수정 [2017-04-26]minae.yun -->
										<select class="custom_select large" title="-선택-" id="input_number" style="width:95px" name="telNumber1">
											<option value="">-선택-</option>
											<c:forEach items="${telCodes}" var="codes">
												<option value="${codes.label }"  ${op:selected(user.userDetail.telNumber1, codes.label)}> ${codes.label }</option>
											</c:forEach>
										</select>
										<span class="connection"> - </span>
										<div class="input_wrap col-w-10">
											<input type="text" name="telNumber2" id="telNumber2" title="전화번호" value="${user.userDetail.telNumber2}" class="full" maxlength="4" />
										</div>
										<span class="connection"> - </span>
										<div class="input_wrap col-w-10">
											<input type="text" name="telNumber3" id="telNumber3" title="전화번호" value="${user.userDetail.telNumber3}" class="full" maxlength="4" />
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" valign="top">주소 <span class="necessary"></span></th>
								<td>
									<div>
										<input type="hidden" name="post" id="post" value="${user.userDetail.post}" >

										<div class="input_wrap col-w-10">
											<input type="text" name="newPost" title="새우편번호" value="${user.userDetail.newPost}" title="${op:message('M00115')}  ${op:message('M00107')}" maxlength="7" class="required" />
										</div>

										<div class="input_wrap"><button type="button" onclick="openDaumPostcode()" class="btn btn-ms btn-default">주소검색</button></div>

										<div class="input_wrap mt5 col-w-1">
											<input type="text" name="address" id="address" value="${user.userDetail.address}" title="${op:message('M00118')}" maxlength="100" class="required full">
										</div>
										<div class="input_wrap mt5 col-w-1">
											<input type="text" name="addressDetail" id="addressDetail" value="${user.userDetail.addressDetail}" title="${op:message('M00119')}" maxlength="100" class="required full"/>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">생년월일</th>
								<td>
									<c:choose>
										<c:when test="${empty user.userDetail.birthdayYear || empty user.userDetail.birthdayMonth || empty user.userDetail.birthdayDay}">
											<div class="input_wrap col-w-9">
												<select class="form-control hp" name="birthdayYear" value="${user.userDetail.birthdayYear}">
													<option value="">선택</option>
													<c:forEach begin="0" end="100" step="1" var="index">
														<option value="${years - index}" label="${years - index}" ${op:selected(user.userDetail.birthdayYear, years - index)}/>
													</c:forEach>
												</select>
											 </div>
											 <span class="connection"> 년 </span> &nbsp;&nbsp;
											<div class="input_wrap col-w-9">
												<select class="form-control hp" name="birthdayMonth" value="${user.userDetail.birthdayMonth}">
													<option value="">선택</option>
													<c:forEach begin="1" end="12" step="1" var="index">
														<option value="${index}" label="${index}" ${op:selected(user.userDetail.birthdayMonth, index)}/>
													</c:forEach>
												</select>
											</div>
											<span class="connection"> 월 </span> &nbsp;&nbsp;
											<div class="input_wrap col-w-9">
												<select class="form-control hp" name="birthdayDay" value="${user.userDetail.birthdayDay}">
													<option value="">선택</option>
													<c:forEach begin="1" end="31" step="1" var="index">
														<option value="${index}" label="${index}" ${op:selected(user.userDetail.birthdayDay, index)}/>
													</c:forEach>
												</select>
											</div>
											<span class="connection"> 일 </span> &nbsp;&nbsp;
											<div class="input_wrap">
												<input type="radio" name="birthdayType" id="birthdayType1" value="1" title="생년월일" ${op:checked(user.userDetail.birthdayType,'1')}><label for="birthdayType1">양력</label>&nbsp;&nbsp;
												<input type="radio" name="birthdayType" id="birthdayType2" value="2" title="생년월일" ${op:checked(user.userDetail.birthdayType,'2')}><label for="birthdayType2">음력</label>
											</div>
											<p class="coment mt5">
												생년월일은 변경이 불가능한 정보입니다. 설정시 신중하게 선택하여 주십시오.
											</p>
										</c:when>
										<c:otherwise>
											<div class="input_wrap">
												${user.userDetail.birthdayYear}년 ${user.userDetail.birthdayMonth}월 ${user.userDetail.birthdayDay}일
												${user.userDetail.birthdayType == "1" ? "(양력)" : user.userDetail.birthdayType == "2" ? "(음력)" : ""}
											</div>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row">성별</th>
								<td>
									 <div class="input_wrap">
										<input type="radio" id="man" name="gender" value="M" ${op:checked(user.userDetail.gender,'M')}> <label for="man">남성</label>&nbsp;&nbsp;
										<input type="radio" id="woman" name="gender" value="F" ${op:checked(user.userDetail.gender,'F')}> <label for="woman">여성</label>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" valign="top">SMS 수신동의 <span class="necessary"></span></th>
								<td>
									<div>
										<input type="radio" name="receiveSms" id="receiveSms1" value="0" ${op:checked('0', user.userDetail.receiveSms)} title="SMS 수신동의" class="required" ><label for="receiveSms1">${op:message('M00233')}</label>&nbsp;&nbsp;
										<input type="radio" name="receiveSms" id="receiveSms2" value="1" ${op:checked('1', user.userDetail.receiveSms)} title="SMS 수신동의" class="required" ><label for="receiveSms2">${op:message('M00234')}</label>
										<p class="coment mt5">
											수신동의시 쇼핑몰에서 제공하는 이벤트 및 다양한 정보를 SMS/E-mail로 받아볼 수 있습니다.<br/>
											단, 회원가입, 거래정보-결제/배송/교환/환불 등과 같은 운영정보는 수신동의 여부와 관계없이 발송됩니다.)
										</p>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" valign="top">Email수신동의 <span class="necessary"></span></th>
								<td>
									<div>
										<input type="radio" name="receiveEmail" id="receiveEmail1" value="0" title="이메일 수신동의" ${op:checked(user.userDetail.receiveEmail,'0')} class="required" /><label for="receiveEmail1">${op:message('M00233')}</label>&nbsp;&nbsp;
										<input type="radio" name="receiveEmail" id="receiveEmail2" value="1" title="이메일 수신동의" ${op:checked(user.userDetail.receiveEmail,'1')} class="required" /><label for="receiveEmail2">${op:message('M00234')}</label>
										<p class="coment mt5">
											수신동의시 쇼핑몰에서 제공하는 이벤트 및 다양한 정보를 SMS/E-mail로 받아볼 수 있습니다.<br/>
											단, 회원가입, 거래정보-결제/배송/교환/환불 등과 같은 운영정보는 수신동의 여부와 관계없이 발송됩니다.)
										</p>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" valign="top">Push수신동의 <span class="necessary"></span></th>
								<td>
									<div>
										<input type="radio" name="receivePush" id="receivePush1" value="0" title="Push 수신동의" ${op:checked(user.userDetail.receivePush,'0')} class="required" /><label for="receivePush1">${op:message('M00233')}</label>&nbsp;&nbsp;
										<input type="radio" name="receivePush" id="receivePush2" value="1" title="Push 수신동의" ${op:checked(user.userDetail.receivePush,'1')} class="required" /><label for="receivePush2">${op:message('M00234')}</label>
										<p class="coment mt5">
											수신동의시 쇼핑몰에서 제공하는 이벤트 및 다양한 정보를 Push로 받아볼 수 있습니다.<br/>
											단, 회원가입, 거래정보-결제/배송/교환/환불 등과 같은 운영정보는 수신동의 여부와 관계없이 발송됩니다.)
										</p>
									</div>
								</td>
							</tr>

				 			<%--
				 				<tr>
									<th scope="row">회사명</th>
				 					<td>
				 						<div class="input_wrap col-w-5">
				 							<input type="text" title="회사명" name="companyName" class="required" value="${user.userDetail.companyName}"/>
				 						</div>
				 						<span class="coment">사업자의 경우 입력해주세요.</span>
				 					</td>
				 				</tr>
			 				 --%>
			 				 <%--
				 				<tr>
									<th scope="row">사업자 등록번호</th>
				 					<td>
				 						<div class="input_wrap col-w-10">
				 							<input type="text" name="businessNumber1" title="사업자 등록번호" class="required _number" maxlength="3" value="${user.userDetail.businessNumber1}"/>
				 						</div>
				 						<span class="connection"> - </span>
				 						<div class="input_wrap col-w-10">
				 							<input type="text" name="businessNumber2" title="사업자 등록번호" class="required _number" maxlength="2" value="${user.userDetail.businessNumber2}"/>
				 						</div>
				 						<span class="connection"> - </span>
				 						<div class="input_wrap col-w-10">
				 							<input type="text" name="businessNumber3" title="사업자 등록번호" class="required _number" maxlength="5" value="${user.userDetail.businessNumber3}"/>
				 						</div>  
				 						<span class="coment">사업자에게 발행하는 세금계산서 정보이며 개인은 입력하지 않습니다.</span>
				 					</td>
				 				</tr>
				 				 
				 				<tr>
				 					<th scope="row">사업자 업태</th>  
				 					<td>
										<input type="text" name="businessStatus" title="사업자 업태" class="" maxlength="50"  value="${user.userDetail.businessStatus}"/>
									</td>
				 				</tr> 
				 				<tr>
				 					<th scope="row">사업자 업태 종목</th>   
				 					<td>
										<input type="text" name="businessStatusType" title="사업자 업태 종목" class="" maxlength="50"  value="${user.userDetail.businessStatusType}"/>
									</td>
				 				</tr> 
				 			--%>
			 			</tbody>
			 		</table><!--//view E-->	
			 		<div class="btn_wrap"> 
			 			<button type="submit" class="btn btn-success btn-lg">회원정보수정</button>
			 			<a href="/users/editMode" class="btn btn-default btn-lg">취소하기</a>
			 		</div> 	
			 	</div>
			 </form:form>
		 	 		
		</div><!--// sub_contents_min E-->
	</div>
</div>

	
	  
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
			if (isIdDuplicated) {
				alert("아이디 중복체크를 해주세요.");
				return false;
			}
			/*
			if ($("#email").val() != $('#orgEmail').val()) {
				
				if (userAvailabilityCheck($("#email").val(), 'email') == false) {
					//alert("${op:message('M00160')}");
					alert("이미 등록된 메일주소입니다.");
					$("#email").focus();
					return false;
				}
				
				if($("#idCheck").val() == 0 && loginCheckId != $("#email").val()){
					alert("${op:message('M00157')} ");
					$("#idCheck").focus();
					return false;
				}
			}
			*/
			
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
				$("#password_confirm").fucous();
				return false;
			}
			
			// 생년월일 입력 체크 2017-05-30_seungil.lee
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

			Common.confirm("${op:message('M00159')} ", function(form) {
				$('#user').submit();
			});
			
			return false;
		});

		//idCheck();
		loginIdEvent();
	});

	function userAvailabilityCheck(value, type) {
		var params = {
			'type'		: type,
			'value' 	: value
		};
		
		var returnValue = false;
		$.post("/users/user-availability-check", params, function(response){
			Common.loading.hide();
			returnValue = response.availability;
		}, 'json').error(function(e){
			alert(e.message);
		});
		
		return returnValue;
	}
	
	/* id 체크 */
	/*
	function idCheck(){
		$("#idCheckBtn").on('click',function(){
			
			if ($("#email").val() != $('#orgEmail').val()) {
				var loginId = $("#email").val();
	
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
			}
		});
	}
	*/
	/* 
	$("#nicknameCheckBtn").on('click',function(){
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
			'newZipcode' 			: 'newPost',
			'zipcode' 				: 'post',
			'zipcode1' 				: 'post1',
			'zipcode2' 				: 'post2',
		}
		
		openDaumAddress(tagNames);
	}
	
	function loginIdCheck() {
		var loginId = $("#loginId").val();

		if (loginId != '') {

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
					alert("이미 등록된 아이디입니다.");
					$("#loginId").focus();
				} else {
					$("#idCheck").val('1');
					$('.e-info').css('display', 'block');
					isIdDuplicated = false;
					loginCheckId = $("#loginId").val();
					alert("사용가능한 아이디입니다.");
				}
				
			});
		} else {
			alert("이메일 주소를 입력해주세요.");
			return false;
		}
	}
	
	function loginIdEvent() {
		$("#loginId").on("keyup", function(event){
			if (Common.isEdited(event.keyCode)) {
				isIdDuplicated = true;
			}
		});
	}
</script>

</page:javascript>