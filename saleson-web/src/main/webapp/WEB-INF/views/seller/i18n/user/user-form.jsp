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
		<form:form modelAttribute="user" id="user"  method="post">
			<form:hidden path="userId" />
			<div class="board_write">
					<table class="board_write_table">
						<caption>${op:message('M00145')} </caption>
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tbody>

							<tr>
								<td class="label">${op:message('M00081')} </td>
								<td>
									<div>
										<c:choose>
											<c:when test="${!empty user.loginId}">
												${user.loginId}
												<input type="hidden" name="idCheck" id="idCheck" value="1" />
											</c:when>
											<c:otherwise>
												<form:input path="loginId" title="${op:message('M00081')} " cssClass="required _id" maxlength="20"/>
												<a href="javascript:;" id="idCheckBtn" class="btn btn-dark-gray btn-sm">${op:message('M00148')} </a>
												<input type="hidden" name="idCheck" id="idCheck" value="0" />
												<span>(${op:message('M00149')})</span>
											</c:otherwise>
										</c:choose>
								    </div>
								</td>
							</tr>
							<tr>
								<td class="label">이름</td>
								<td>
									<div>
										<form:input path="userName" title="이름" maxlength="30"  cssClass="form-half required" />
								    </div>
								</td>					
							</tr>
							<c:if test="${formAction == 'edit'}">
								<tr>
									<td class="label">${op:message('M00150')} </td>
									<td>
										<div>
											<button type="button" id="newPassword" class="btn btn-gradient btn-sm add-option">임시비밀번호 발송</button>
										</div>
									</td>
								</tr>
							</c:if>
							<tr>
								<c:set var="required" value="" />
								<c:choose>
									<c:when test="${empty user.loginId }"><c:set var="required" value="required" /></c:when>
									<c:otherwise><c:set var="required" value="optional" /></c:otherwise>
								</c:choose>
								<td class="label">${op:message('M00150')} </td>
								<td>
									<div>
										<input type="password"  name="password" id="password" title="${op:message('M00150')} " class="form-half ${required} _password _duplicated" minlength="4"  maxlength="20"/>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00151')} </td>
								<td>
									<div>
										<input type="password" name="password2" id="password_confirm" title="${op:message('M00151')} " class="form-half ${required} _password _duplicated" minlength="4"  maxlength="20"/>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">핸드폰 번호</td>
								<td>
									<input type="hidden" name="phoneNumber" id="phoneNumber"/>
									<div>
										<input type="text" id="phoneNumber1" maxlength="3" Class="form-phone _number required" value="${phoneNumber1}"> -
										<input type="text" id="phoneNumber2" maxlength="4" Class="form-phone _number required" value="${phoneNumber2}"> -
										<input type="text" id="phoneNumber3" maxlength="4" Class="form-phone _number required" value="${phoneNumber3}">
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00125')} </td>
								<td>
									<div>
										<form:input path="email" title="${op:message('M00125')} " cssClass="form-half optional _email" maxlength="100" />
								    </div>
								</td>
							</tr>		
						</tbody>					 
					</table>		
					<!-- 버튼시작 -->		 
					<div class="tex_c mt20">
						<button type="submit" class="btn btn-active"><span>${op:message('M00101')} </span></button>		
						<a href="/seller/user/list" class="btn btn-default">${op:message('M00037')} </a>
					</div>			 
					<!-- 버튼 끝-->	
			</div> <!-- // board_write -->
		</form:form>					 							


		
<script type="text/javascript">
	var loginCheckId = '';
	$(function(){

		// 필수 입력항목 마커.
		Common.displayRequireMark();
	
		$('#user').validator(function() {

			if($("#idCheck").val() == 0 && loginCheckId != $("#loginId").val()){
				alert("${op:message('M00157')} ");
				$("#idCheck").focus();
				return false;
			}

			if ($("#password").val() != $("#password_confirm").val()) {
				alert("입력된 비밀번호와 비밀번호 확인이 다릅니다.");
				$("#password_confirm").focus();
				return false;
			}

			var phoneNumber = $('#phoneNumber1').val() + "-" + $('#phoneNumber2').val() + "-"+ $('#phoneNumber3').val();
			$('#phoneNumber').val(phoneNumber);
			
			Common.confirm("${op:message('M00159')} ", function(form) {

				$('#user').submit();
			});
			return false;
		});

		idCheck();
	});

	/* id 중복 체크 */
	function idCheck(){
		$("#idCheckBtn").on('click',function(){
			var loginId = $("#loginId").val();

			if (loginId != '') {
				
				var idReg=$.validator.patterns._id;

				if(!idReg.test($("#loginId").val())) {
					alert("${op:message('M00149')}"); 
					return;
				}
				
				 var params = {
					'loginId' : loginId
				};
				
				$.post("/seller/user/duplicate-user",params,function(response){
					Common.loading.hide();
					
					var data =  response.data;
					
					if (data) {
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

	$('#newPassword').on('click', function() {
		var userId = $('#userId').val();
		var params = {
			'userId' : userId
		};

		$.post("/seller/user/tempPassword", params ,function(response) {
			if (response.isSuccess) {
				alert("임시비밀번호가 발송되었습니다.");
			} else {
				alert("임시비밀번호 발송이 실패하였습니다.");
			}
		});
	});
</script>
		