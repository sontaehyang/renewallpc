<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>${op:message('M00145')} </span></h3>
		<form:form modelAttribute="user" id="user2"  method="post">
			<form:hidden path="userId" />
			<div class="board_write">
					<table class="board_write_table">
						<caption>${op:message('M00145')} </caption>
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">${op:message('M00146')} </td>
								<td colspan="3">
									<div>
										<c:set var="label" value="${lavels}" />
						
										
										<c:forEach items="${adminMenuRoles}" var="adminMenuRole" varStatus="i">
											<c:set var="checked" value="" />
								
											<c:forEach items="${ user.userRoles }" var="userRole" varStatus="j" >
												<c:if test="${ adminMenuRole.authority eq userRole.authority }">
													<c:set var="checked" value="checked='checked'" />
												</c:if>
											</c:forEach>
											
											<label><input type="checkbox" name="userRoles[${i.index}].authority" id="role${i.index}" value="${adminMenuRole.authority}" title="${op:message('M00147')} " class="authority" ${checked} /> ${op:message(adminMenuRole.roleName)}</label>
											<br />
										</c:forEach>
										 
										<input type="hidden" name="adminMenu" id="adminMenu" value="${userDetails.adminMenu}" />
 									</div>
								</td>						 
							</tr>
							<!--  tr>
								<td class="label">${op:message('M00082')} </td>
								<td colspan="3"> 
									<div>
										<input type="radio" name="useFlag" id="useFlag1" value="Y" <c:if test="${userDetails.useFlag eq 'Y'}">checked='checked'</c:if> /> <label for="useFlag1">${op:message('M00083')} </label>
										<input type="radio" name="useFlag" id="useFlag2" value="N" <c:if test="${userDetails.useFlag eq 'N'}">checked='checked'</c:if>  /> <label for="useFlag2">${op:message('M00089')} </label>
									</div>
								</td>						 
							</tr-->
							<tr>
								<td class="label">${op:message('M00081')} </td>
								<td colspan="3">
									<div>
										<c:choose>
											<c:when test="${!empty userId}">
												${user.loginId}
												<input type="hidden" name="idCheck" id="idCheck" value="1" />
											</c:when>
											<c:otherwise>
												<form:input path="loginId" title="${op:message('M00081')} " cssClass="required three _id" maxlength="20"/>
												<a href="javascript:;" id="idCheckBtn" class="btn btn-dark-gray btn-sm">${op:message('M00148')} </a>
												<input type="hidden" name="idCheck" id="idCheck" value="0" />
												<span>(${op:message('M00149')} )</span>			
											</c:otherwise>
										</c:choose>
								    </div>
								</td>							
							</tr>
							<tr>
								<c:set var="required" value="" />
								<c:choose>
									<c:when test="${empty user.loginId }"><c:set var="required" value="required" /></c:when>
									<c:otherwise><c:set var="required" value="optional" /></c:otherwise>
								</c:choose>
								<td class="label">${op:message('M00150')} </td>
								<td>
									<div>
										<input type="password"  name="password" id="password" title="${op:message('M00150')} " class="form-block ${required} _password" minlength="4"  maxlength="20"/>					 
								    </div>
								</td>
								<td class="label">${op:message('M00151')} </td>
								<td>
									<div>
										<input type="password" name="password2" id="password_confirm" title="${op:message('M00151')} " class="form-block ${required} _password" minlength="4"  maxlength="20"/>					 
								    </div>
								</td>
							</tr>
							<tr>
								<td class="label">이름</td>
								<td>
									<div>
										<form:input path="userName" title="이름" maxlength="30"  cssClass="form-block required" />
								    </div>
								</td>
								<td class="label">부서명</td>
								<td>
									<div>
										<input type="text" name="userNameKatakana" id="userNameKatakana" value="${userDetails.userNameKatakana}" title="부서명" maxlength="50" class="form-block">
								    </div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00125')} </td>
								<td>
									<div>
										<form:input path="email" title="${op:message('M00125')} " style="width: 70%" cssClass="form-email required _email" maxlength="100" />
										<a href="javascript:;" id="emailCheckBtn" class="btn btn-dark-gray btn-sm">${op:message('M00148')} </a>
										<input type="hidden" id="orgEmail" value="${ user.email }" />
										<input type="hidden" name="emailCheck" id="emailCheck" value="0" />
								    </div>
								</td> 
								<td class="label">${op:message('M00154')} </td> 
								<td>
									<div>
										<input type="text" name="phoneNumber1" id="phoneNumber" value="${userDetails.phoneNumber1}" title="연락처" maxlength="4" class="form-phone _number"> -
										<input type="text" name="phoneNumber2" id="phoneNumber" value="${userDetails.phoneNumber2}" title="연락처" maxlength="4" class="form-phone _number"> -
										<input type="text" name="phoneNumber3" id="phoneNumber" value="${userDetails.phoneNumber3}" title="연락처" maxlength="4" class="form-phone _number">
									</div>
								</td>
							</tr>		
						</tbody>					 
					</table>		
					<!-- 버튼시작 -->		 
					<div class="tex_c mt20">
						<button type="submit" class="btn btn-active"><span>${op:message('M00101')} </span></button>		
						<a href="/opmanager/user/manager/list" class="btn btn-default">${op:message('M00037')} </a>		 
					</div>			 
					<!-- 버튼 끝-->	
			</div> <!-- // board_write -->
		</form:form>					 							


		
<script type="text/javascript">
	var loginCheckId = '';
	var loginEmail = '';
	$(function(){

		$('#user2').validator(function() {

			if ($(".authority:checked").length == 0) {
				alert("${op:message('M00156')}  ");
				$(".authority").eq(0).focus();
				return false;
			}

			if($("#idCheck").val() == 0 && loginCheckId != $("#loginId").val()){
				alert("${op:message('M00157')} ");
				$("#idCheck").focus();
				return false;
			}

			if ($('#orgEmail').val() != $('#email').val()) {
				if($("#emailCheck").val() == 0 && loginEmail != $("#email").val()){
					alert("${op:message('M01589')} ");
					$("#emailCheck").focus();
					return false;
				}
			}
			
			if ($("#password").val() != $("#password_confirm").val()) {
				alert("입력된 비밀번호와 비밀번호 확인이 다릅니다.");
				$("#password_confirm").focus();
				return false;
			}
			
			Common.confirm("${op:message('M00159')} ", function(form) {

				var adminMenu = "";
				$("input[type=checkbox]:checked").each(function(index){
					adminMenu += index == 0 ? '' : ',';
					adminMenu += $(this).val().replace('ROLE_ADMIN_MENU_', '');
				});
				
				$("#adminMenu").val(adminMenu);
				$('#user2').submit();
			});
			return false;
		});

		idCheck();
		emailCheck();
	});

	/* email 중복 체크 */
	function emailCheck(){
		$("#emailCheckBtn").on('click',function(){
			var email = $("#email").val();
			
			var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
			 
			if(regex.test(email) === false) {
			 alert("잘못된 이메일 형식입니다.");
			 return false;
			}

			if (email != '') {

				 var params = {
					'loginId' : email
				};
				
				$.post("/opmanager/user/find-user",params,function(response){
					Common.loading.hide();

					var data = response.data;
					
					if (data > 0) {
						alert("${op:message('M00160')}");
						$("#emailCheck").focus();
					} else {
						$("#emailCheck").val("1");
						alert("${op:message('M00161')}");
						loginEmail = $("#email").val();
					}

					
				});
			} 
		});
	}
	
	/* id 중복 체크 */
	function idCheck(){
		$("#idCheckBtn").on('click',function(){
			var loginId = $("#loginId").val();

			if (loginId != '') {
				
				var idReg=/^[a-zA-Z]{1}[a-zA-Z0-9]{3,14}$/g;
				
				if(!idReg.test($("#loginId").val())) {
					alert("${op:message('M00149')}"); 
					return;
				} 
				
				 var params = {
					'loginId' : loginId
				};
				
				$.post("/opmanager/user/manager/find-user",params,function(response){
					Common.loading.hide();
					
					var data =  response.data;
					
					if (data.userCount > 0) {
						alert("${op:message('M01588')}");
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
</script>
		