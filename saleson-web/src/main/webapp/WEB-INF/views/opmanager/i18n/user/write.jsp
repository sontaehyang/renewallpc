<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<h3><span>관리자계정 관리</span></h3>
<div class="board_list">				 
  <form:form modelAttribute="user" method="post">
	<form:hidden path="userId"/>
	<div class="board_view">
		<div class="info" style="border:none;">
			<table class="detail">
				<colgroup>
					<col style="width:80px;">
					<col style="width:460px;">
				</colgroup>
				<tbody>
					<tr>
						<td class="label">관리레벨</td>
						<td>
							<div>
								<form:select path="userRoles[0].authority" title="분류선택">
									<form:option value="ROLE_SUB_MANAGER">부관리자</form:option>
									<form:option value="ROLE_CONTENT_MANAGER">컨텐츠관리자</form:option>
								</form:select>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">아이디</td>
						<td>
							 <div>
							 	<c:if test="${mode == 'insert'}">
							 		 <form:input path="loginId" cssClass="required _filter full" maxlength="255" title="아이디" />
							 	</c:if>
							 	<c:if test="${mode == 'edit'}">
							 		 <form:input path="loginId" cssClass="required _filter full" maxlength="255" title="아이디" disabled="true" />
							 	</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">이름</td>
						<td>
							 <div>
							 	 <form:input path="userName" cssClass="required _filter full" maxlength="255" title="이름" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">비밀번호</td>
						<td>
							 <div>
							 	 <form:password path="password" cssClass="required _filter full" maxlength="255" title="비밀번호" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">전화번호</td>
						<td>
							<div>
							 <c:set var="tel_arr" value="${fn:split(user.userDetail.phoneNumber, '-') }"/>
							 <select name="tel1" class="choice3" style="width:100px;">
								<option value="010" <c:if test="${tel_arr[0]=='010'}">selected="selected"</c:if>>010</option>
								<option value="011" <c:if test="${tel_arr[0]=='011'}">selected="selected"</c:if>>011</option>
								<option value="016" <c:if test="${tel_arr[0]=='016'}">selected="selected"</c:if>>016</option>
							</select> - 
							<input type="text" name="tel2" value="${tel_arr[1]}" maxlength="4" class="required _number" title="전화번호 가운데자리"/> - 
							<input type="text" name="tel3" value="${tel_arr[2]}" maxlength="4" class="required _number" title="전화번호 마지막자리"/>
							</div>
						</td>
					</tr>	
					<tr>
						<td class="label">이메일</td>
						<td>
							<div>
								<c:set var="email_arr" value="${fn:split(user.email, '@') }"/>
								<input type="text" name="email1" maxlength="25" title="이메일 아이디" value="${email_arr[0]}" class="required" /> @
								<input type="text" name="email2" maxlength="25" title="이메일 도메인" value="${email_arr[1]}" class="required" /> 
								<select class="choice emailSel" style="width:200px;">
									<option value="">직접입력</option>
									<option value="naver.com" <c:if test="${email_arr[1]=='naver.com'}">selected="selected"</c:if>>naver.com</option>
									<option value="nate.com" <c:if test="${email_arr[1]=='nate.com'}">selected="selected"</c:if>>nate.com</option>
									<option value="hotmail.com" <c:if test="${email_arr[1]=='hotmail.com'}">selected="selected"</c:if>>hotmail.com</option>
									<option value="lycos.co.kr" <c:if test="${email_arr[1]=='lycos.co.kr'}">selected="selected"</c:if>>lycos.co.kr</option>
									<option value="msn.com" <c:if test="${email_arr[1]=='msn.com'}">selected="selected"</c:if>>msn.com</option>
									<option value="hanmail.net" <c:if test="${email_arr[1]=='hanmail.net'}">selected="selected"</c:if>>hanmail.net</option>
									<option value="yahoo.co.kr" <c:if test="${email_arr[1]=='yahoo.co.kr'}">selected="selected"</c:if>>yahoo.co.kr</option>
									<option value="paran.com" <c:if test="${email_arr[1]=='paran.com'}">selected="selected"</c:if>>paran.com</option>
								</select>
							</div>	
						</td>		
					</tr>							
				</tbody>
			</table>						
		</div><!--//info-->
			 
		<p class="btns">
			<c:if test="${mode == 'insert'}">
				<button type="submit" class="btn orange">등록</button>&nbsp;			
			</c:if>	
			<c:if test="${mode == 'edit'}">
				<button type="submit" class="btn white">수정</button>&nbsp;
				<a href="javascript:;" onClick="fn_delete()" class="btn white">삭제</a>&nbsp;
			</c:if>						
			<a href="/opmanager/user/list" class="btn gray">목록</a>&nbsp;
		</p>
	</div> <!--// board_view -->
  </form:form>	
 
 <form id="deleteForm" method="post" action="/opmanager/user/delete">
	<input type="hidden" name="userId" value="${user.userId }" />
</form>
</div><!--//board_list-->

<script type="text/javascript">
$(function() {
	$("#user").validator(function() {
		 if('${mode}' == 'insert'){
			 var params = {
				'loginId' : $("#loginId").val()
			};
			var i = 0;
			 $.post('/opmanager/user/checkid', params, function(resp) {
				if (resp.isSuccess) {
					
				} else {
					alert(resp.errorMessage);
					i = 1;
				}
			});
			
			if(i > 0) return false;
			 
		 }
	});
	
	$('.emailSel').on("change", function(){
		$('input[name=email2]').val($(this).val());
	});
});

function fn_delete(){
	 if(confirm("관리자계정을 삭제하시겠습니까?")){
		 $("#deleteForm").submit();
	 }
}
</script>
