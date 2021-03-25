<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<style>
.phone_number {width: 45px;}

</style>
			<h3><span>회원가입</span></h3>
			
			<form:form modelAttribute="user" method="post">
				<input type="hidden" id="idCheck" />
				<input type="hidden" id="email" name="email" />
				<div class="board_write">
					<table class="board_write_table" summary="신규회원등록에 관련한 정보를 입력하는 칸입니다.">
						<caption>신규회원등록</caption>
						<colgroup>
							<col style="width:15%;" />
							<col style="width:85%;" />
						</colgroup>
						<tbody>
						<tr>
							<td class="label">${op:message('M01111')}(ID) <span class="require">*</span></td> <!-- 이메일주소 -->
							<td>
								<div class="input_wrap col-w-7">
									<form:input path="loginId" title="${op:message('M00125')}(ID)" cssClass="two required _email" maxlength="100" />
									<button type="button" id="idCheckBtn" class="table_btn" > ${op:message('M00148')} </button> <!-- 중복검사 -->
								</div>
							</td>
						</tr>
		 				<tr>
		 					<td class="label">${op:message('M00150')} <span class="require"><span class="require">*</span></span></td> <!-- 비밀번호 -->
		 					<td>
		 						<div class="input_wrap col-w-7">
		 							<input type="password"  name="password" id="password" title="${op:message('M00150')}" class="half required _password _duplicated" minlength="4"  maxlength="20"/>
			 						<span>비밀번호는 영문/숫자/특수문자를 혼합하여 8자 이상 20자 이하로 입력하세요.</span>
		 						</div>
		 					</td>
		 				</tr>
		 				<tr>
		 					<td class="label">${op:message('M00151')} <span class="require"><span class="require">*</span></span></td> <!-- 비밀번호 확인 -->
		 					<td>
		 						<div class="input_wrap col-w-7">
								    <input type="password" name="password2" id="password_confirm" title="${op:message('M00151')}" class="half required _password _duplicated" minlength="4"  maxlength="20"/>
			 						<span>비밀번호 확인을 위하여 다시 한번 입력하여 주세요.</span>
		 						</div>
		 					</td>
		 				</tr>
								
		 				<%-- 
			 				<tr>
			 					<td class="label">${op:message('M00175')} <span class="require">*</span></td> <!-- 닉네임 -->
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<input type="text" name="nickname" id="nickname" title="${op:message('M00175')}" class="two required"  maxlength="20" />
			 							<button type="button" id="nicknameCheckBtn" class="table_btn" > ${op:message('M00148')} </button> <!-- 중복검사 -->
			 						</div>
			 					</td>
			 				</tr>
		 				 --%>
		 				
								
						<tr>
							<td class="label">이름 <span class="require">*</span></td> <!-- 이름(한자) -->
							<td>
								<div class="input_wrap col-w-7">
									<form:input path="userName" title="이름" maxlength="50" cssClass="two required" />
								</div>
							</td>
						</tr>

			 				<tr>
			 					<td class="label">${op:message('M00118')} <span class="require">*</span></td> <!-- 주소 -->
			 					<td>
			 						<div class="">
		 								우편번호 <input type="hidden" name="post" value="${user.userDetail.post}">
										<input type="text" name="newPost" id="newPost" value="${user.userDetail.newPost}" class="required" title="${op:message('M00115')}  ${op:message('M00107')}" maxlength="7" class="one" readonly="readonly">
										<%-- <a href="#javascript:;" onclick="openDaumPostcode()" class="table_btn">${op:message('M00117')}</a>  --%>
										<button type="button" onclick="openDaumPostcode()" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</button>
										<!-- p class="mt5">
											지역 <input type="text" name="dodobuhyun" id="dodobuhyun" title="${op:message('M00063')}" maxlength="8" class="required disabled" readonly="readonly">
										</p-->
										<p class="mt5">
											주소 <input type="text" name="address" id="address" title="${op:message('M00118')}" maxlength="100" class="half required disabled" readonly="readonly">
										</p>
										<p class="mt5">
											상세주소 <input type="text" name="addressDetail" id="addressDetail" title="${op:message('M00118')}" maxlength="100" class="half required">
										</p>
									</div>
			 					</td>
			 				</tr>
			 				<tr>
			 					<td class="label">${op:message('M00016')} <span class="require">*</span></td> <!-- 전화번호 -->
			 					<td>
			 						<div class="">
			 							<!-- 전화번호 코드 추가 [2017-04-26]minae.yun -->
			 							<select class="custom_select large required" title="전화번호" id="telNumber1" name="telNumber1">
											<option selected="selected" value="">-선택-</option>
											<c:forEach items="${telCodes}" var="codes">
												<option>${codes.label }</option>
											</c:forEach>
										</select> -
			 							<input type="text" name="telNumber2" title="${op:message('M00016')}" class="phone_number required _number" maxlength="4" /> -
			 							<input type="text" name="telNumber3" title="${op:message('M00016')}" class="phone_number required _number _minlength_4" maxlength="4" />
			 						</div>
			 					</td>
			 				</tr>
			 			<%--
			 				<tr>
			 					<td class="label">${op:message('M00184')} <span class="require">*</span></td> <!-- FAX번호 -->
			 					<td>
			 						<div class="">
			 							<input type="text" name="faxNumber1" title="${op:message('M00184')}" class="phone_number required _number" maxlength="4" /> -
			 							<input type="text" name="faxNumber2" title="${op:message('M00184')}" class="phone_number required _number" maxlength="4" /> -
			 							<input type="text" name="faxNumber3" title="${op:message('M00184')}" class="phone_number required _number" maxlength="4" />
			 						</div>
			 					</td>
			 				</tr>
			 			 --%>
							<tr>
								<td class="label">${op:message('M00329')} <span class="require">*</span></td> <!-- 휴대전화번호 -->
								<td>
									<div class="">
										<!-- 휴대폰번호 코드 추가 [2017-04-26]minae.yun -->
										<select class="custom_select large required" title="휴대폰번호" id="phoneNumber1" name="phoneNumber1">
											<option selected="selected" value="">-선택-</option>
											<c:forEach items="${phoneCodes}" var="codes">
												<option>${codes.label }</option>
											</c:forEach>
										</select> -
										<input type="text" name="phoneNumber2" title="${op:message('M00329')}" class="phone_number required _number" maxlength="4" /> -
										<input type="text" name="phoneNumber3" title="${op:message('M00329')}" class="phone_number required _number" maxlength="4" />
									</div>
								</td>
							</tr>
			 				<%-- <tr>
			 					<td class="label">${op:message('M00183')}(※복수선택가능) <span class="require">*</span> </td> <!-- 영업형태 -->
			 					<td>
			 						<div class="search-box">
									<ul class="join_check">
										<c:forEach items="${businessCodes}" var="businessCode" varStatus="i">
											<c:if test="${!i.first}">
												<c:set var="required" value="" />
						 					</c:if>
											<c:if test="${i.last}">
												<c:set var="lastClass">class="last"</c:set>
						 					</c:if>
											<li>
												<label class="checkbox-inline"><input type="checkbox" name="businessType" id="businessType_${i.index}" value="${businessCode.businessCodeId}" class="required" title="${op:message('M00183')}" />${businessCode.id}</label>
											</li>
											<c:if test="${i.last}">
												<li ${lastClass}>
													<div style="float:left;text-align:right;padding-top:3px;">기타 :&nbsp;</div>
													<div style="float:left;"><input type="text" name="businessTypeEtc" id="businessTypeEtc" title="お名前（カナ）" value="" maxlength="100" /></div>
												</li>
											</c:if>
						 				</c:forEach>
									</ul>
								</div>
			 					</td>
			 				</tr> --%>

			 				<tr>
			 					<td class="label">SMS 수신동의 <span class="require">*</span></td>
			 					<td>
			 						<div>
			 							<input type="radio" name="receiveSms" id="receiveSms1" value="0" title="${op:message('M00250')}" class="required" checked="checked"><label for="receiveSms1">${op:message('M00233')}</label> 
										<input type="radio" name="receiveSms" id="receiveSms2" value="1" title="${op:message('M00250')}" class="required" ><label for="receiveSms2">${op:message('M00234')}</label>
										<p class="mt5">
											쇼핑몰에서 제공하는 다양한 정보 sms를 받아보실 수 있습니다.<br/>
											(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)
										</p>
			 						</div>
			 					</td>
			 				</tr>
			 				<tr>
			 					<td class="label">이메일 수신동의 <span class="require">*</span></td> <!-- 메일매거진 -->
			 					<td>
			 						<div>
			 							<input type="radio" name="receiveEmail" id="receiveEmail1" value="0" title="${op:message('M00250')}" class="required" checked="checked"><label for="receiveEmail1">${op:message('M00233')}</label> 
										<input type="radio" name="receiveEmail" id="receiveEmail2" value="1" title="${op:message('M00250')}" class="required" ><label for="receiveEmail2">${op:message('M00234')}</label>
										<p class="mt5">
											쇼핑몰에서 제공하는 다양한 정보 E-mail를 받아보실 수 있습니다.<br/>
											(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)
										</p>
			 						</div>
			 					</td>
			 				</tr>

			 				<tr>
			 					<td class="label">생년월일</td>
			 					<td>
			 						<div>
			 							<p class="mt5">
											<select name="birthdayYear">
												<option value="">선택</option>
												<c:forEach begin="0" end="100" step="1" var="index">
													<option value="${years - index}" label="${years - index}">${years - index}</option>
												</c:forEach>
											</select>년
											<select name="birthdayMonth">
												<option value="">선택</option>
												<c:forEach begin="1" end="12" step="1" var="index">
													<option value="${index}" label="${index}">${index}</option>
												</c:forEach>
											</select>월
											<select name="birthdayDay">
												<option value="">선택</option>
												<c:forEach begin="1" end="31" step="1" var="index">
													<option value="${index}" label="${index}">${index}</option>
												</c:forEach>
											</select>일
											&nbsp;&nbsp;&nbsp;
											<input type="radio" name="birthdayType" id="birthdayType1" value="1" title="생년월일" checked="checked"><label for="birthdayType1">양력</label>
										    <input type="radio" name="birthdayType" id="birthdayType2" value="2" title="생년월일" ><label for="birthdayType2">음력</label>
										</p>																				
			 						</div>
			 					</td>
			 				</tr>
		 				<tr>
		 					<td class="label">${op:message('M00008')} <span class="require">*</span></td> <!-- 성별 -->
		 					<td>
		 						<div>
		 							<input type="radio" name="gender" id="gender1" value="0" title="${op:message('M00008')}" class="required" checked="checked" ><label for="gender1">${op:message('M00332')}</label> 
									<input type="radio" name="gender" id="gender2" value="1" title="${op:message('M00008')}" class="required" ><label for="gender2">${op:message('M00331')}</label>
		 						</div>
		 					</td>
		 				</tr>
			 			
			 			<%--
			 				<tr id="business1" style="display:none">
			 					<td class="label">회사명 <span class="require">*</span></td>
			 					<td>
			 						<div>
			 							<input type="text" name="companyName" title="회사명" class="required half" /> (<span class="require">*</span>사업자의 경우 입력해주세요.)
			 						</div>
			 					</td>
			 				</tr>
			 				<tr id="business2" style="display:none">
			 					<td class="label">사업자번호 <span class="require">*</span></td>
			 					<td>
			 						<div>
			 							<input type="text" name="businessNumber1" title="사업자번호" class="required _number" maxlength="3" /> - 
			 							<input type="text" name="businessNumber2" title="사업자번호" class="required _number" maxlength="2" /> -
			 							<input type="text" name="businessNumber3" title="사업자번호" class="required _number" maxlength="5" />
										<p class="mt5">
											(사업자에게 발행하는 세금계산서 정보이며 개인은 입력하지 않습니다.)
										</p>
			 						</div>
			 					</td>
			 				</tr>
			 				<tr id="business3" style="display:none">
			 					<td class="label">사업자업태</td>
			 					<td>
			 						<div>
										<input type="text" name="businessStatus" title="사업자업태" class="half" maxlength="50" />
									</div>
								</td>
			 				</tr> 
			 				<tr id="business4" style="display:none">
			 					<td class="label">사업자업태 종목</td>
			 					<td>
			 						<div>
										<input type="text" name="businessStatusType" title="사업자업태 종목" class="half" maxlength="50" />
									</div>
								</td>
			 				</tr>
			 				<tr>
			 					<td class="label">${op:message('M00867')} <span class="require">*</span></td> <!-- 연령 -->
			 					<td>
			 						<div>
			 							<input type="radio" name="age" id="age1" value="10" title="${op:message('M00867')}" class="required" ><label for="age1">${op:message('M00333')}</label> 
										<input type="radio" name="age" id="age2" value="20" title="${op:message('M00867')}"  class="required" ><label for="age2">${op:message('M00334')}</label> 
										<input type="radio" name="age" id="age3" value="30" title="${op:message('M00867')}"  class="required" ><label for="age3">${op:message('M00335')}</label> 
										<input type="radio" name="age" id="age4" value="40" title="${op:message('M00867')}"  class="required" ><label for="age4">${op:message('M00336')}</label> 
										<input type="radio" name="age" id="age5" value="50" title="${op:message('M00867')}"  class="required" ><label for="age5">${op:message('M00337')}</label> 
										<input type="radio" name="age" id="age6" value="60" title="${op:message('M00867')}"  class="required" ><label for="age6">${op:message('M00338')}</label> 
										<input type="radio" name="age" id="age7" value="70" title="${op:message('M00867')}"  class="required" ><label for="age7">${op:message('M00339')}</label>
			 						</div>
			 					</td>
			 				</tr>
			 			--%>
						</tbody>
					</table>						 
				</div> <!--// board_write E-->
				<div class="btn_center_end">
					<button type="submit" class="btn btn-active">${op:message('M00101')} <!-- 저장 --></button>	
				</div>
			</form:form>
			
			
<!-- 다음 주소검색 -->
<daum:address />

<script type="text/javascript">
	var loginCheckId = '';
	$(function(){

		$('#user').validator(function() {

			
			if($("#idCheck").val() == 0 || loginCheckId != $("#loginId").val()){
				alert("${op:message('M00157')}");
				$("#idCheckBtn").focus();
				return false;
			}
			
			
			if (userAvailabilityCheck($("#loginId").val(), 'email') == false) {
				//alert("${op:message('M00160')}");
				alert("이미 등록된 메일주소입니다. 같은 메일주소로 회원등록은 불가능합니다.");
				$("#loginId").focus();
				return false;
			}
			
			/* var nickname = $('#nickname').val();
			if (userAvailabilityCheck(nickname, 'nickname') == false) {
				alert("이미 등록된 닉네임입니다.");
				$('#nickname').focus();
				return false;
			} */
			
			if ($("#password").val() != $("#password_confirm").val()) {
				alert("${op:message('M00158')}");
				$("#password_confirm").fucous();
				return false;
			}

			// 로그인 아이디 = 이메일.
			$('#email').val($('#loginId').val());
			
			Common.confirm("${op:message('M00159')} ", function(form) {
				
				$('#user').submit();
			});
			return false;
			
		});
		
		$("#idCheckBtn").on('click',function(){
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
				 
				$.post("/opmanager/user/find-user",params,function(response){
					Common.loading.hide();
					if (response.data > 0) {
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
		
		/* $('#nicknameCheckBtn').on('click', function() {
			var nickname = $('#nickname').val();
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
	});

	function userAvailabilityCheck(value, type) {
		var params = {
			'type'		: type,
			'value' 	: value
		};
		
		var returnValue = false;
		$.post("/opmanager/user/user-availability-check", params, function(response){
			Common.loading.hide();
			returnValue = response.data;
		}, 'json').error(function(e){
			alert(e.message);
		});
		
		return returnValue;
	}
	
	function openDaumPostcode() {
		
		var tagNames = {
			'newZipcode'			: 'newPost',
			'zipcode' 				: 'post',
			'zipcode1' 				: 'post1',
			'zipcode2' 				: 'post2',
		};
		
		openDaumAddress(tagNames);
	
	}
</script>			
			
