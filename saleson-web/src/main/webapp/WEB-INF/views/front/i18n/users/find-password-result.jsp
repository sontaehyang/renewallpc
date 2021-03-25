<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


	<!-- 본문 -->
	<!-- 팝업사이즈 482*292-->
	<div class="popup_wrap">
		<h1 class="popup_title">비밀번호 찾기</h1>
		<form id="password-change-form" method="post">
			<div class="popup_contents"> 
				<p class="id_txt">새로운 비밀번호를 입력해 주세요.</p>
				<div class="board_wrap">  
			 		<table cellpadding="0" cellspacing="0" class="board-write">
			 			<input type="hidden" name="userId" value="${user.userId}" />
			 			<caption>비밀번호 찾기</caption>
			 			<colgroup>
			 				<col style="width:170px;">
			 				<col style="width:auto;">	 				
			 			</colgroup>
			 			<tbody> 
			 				<tr>
			 					<th scope="row">새로운 비밀번호</th>
			 					<td>
			 						<div class="input_wrap col-w-1">
		 								<input type="password" name="password" id="password" title="새로운 비밀번호" class="full _password required optional" minlength="4"  maxlength="20"/>
		 							</div>
			 					</td>
			 				</tr>
			 				<tr>
			 					<th scope="row">새로운 비밀번호 확인</th>
			 					<td>
			 						<div class="input_wrap col-w-1">
		 								<input type="password" name="password2" id="password_confirm" title="새로운 비밀번호 확인" class="full _password required optional" minlength="4"  maxlength="20"/>
		 							</div>
			 					</td>
			 				</tr>  
			 			</tbody>
			 		</table><!--//write E-->	 	
				</div>  
					 
			</div><!--//popup_contents E-->
			
			<div class="btn_wrap"> 
				<button type="submit" class="btn btn-success btn-lg">확인하기</button> 
			</div>
			<a href="javascript:self.close();" class="popup_close">창 닫기</a>
		</form>
	</div>

<page:javascript>
<script type="text/javascript">
$(function() {
	$('#password-change-form').validator(function() {
		if ($("#password").val() != $("#password_confirm").val()) {
			alert("${op:message('M00158')}");
			$("#password_confirm").fucous();
			return false;
		}
		
		$.post("/users/find-password-change", $('#password-change-form').serialize(), function(response){
			if (response.isSuccess) {
				alert("비밀번호 변경이 정상적으로 처리됬습니다.");
				self.close();
			} else {
				alert(response.errorMessage);
			}
		});
	});
});
</script>
</page:javascript>