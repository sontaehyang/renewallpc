<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


<div class="popup_wrap">
	<h1 class="popup_title">비밀번호 변경</h1>
	
	<div class="popup_contents">
		<form id="passwordForm" action="/seller/edit/password-change" method="POST" >
			<fieldset>
				<legend class="hidden">비밀번호 변경</legend>

				
				<h2>비밀번호 변경</h2> <!-- 비밀번호 변경 -->
				<div class="board_write">
					<table class="board_write_table">
						<colgroup>
							<col style="width:130px;" />
							<col style="" />
						</colgroup>
						<tbody>
							<tr>
									
								<td class="label">${op:message('M00150')}</td> <!-- 패스워드 -->
								<td>
									<div>
										<input type="password" name="password" id="password" title="${op:message('M00150')}" class="full _password required" maxlength="20"/>	
								    </div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00151')}</td> <!-- 패스워드 확인-->
								<td>
									<div>
										<input type="password" name="password2" id="password_confirm" title="${op:message('M00151')}" class="full required" maxlength="20"/>		 
								    </div>
								</td>
							</tr>	
						</tbody>
					</table>
				</div>
				<div class="buttons">
					<button type="submit" class="btn btn-active">${op:message('M00087')} <!-- 저장 --></button>	
				</div>
			</fieldset>
		</form>
		<a href="#" class="popup_close">창 닫기</a>
	</div>
</div>
	

<page:javascript>
<script type="text/javascript">
try{
	$('#passwordForm').validator(function() {
		
		if($("#password_confirm").val() != ''){
			if ($("#password").val() != $("#password_confirm").val()) {
				alert("${op:message('M00158')}");
				$("#password_confirm").fucous();
				return false;
			}
		}
	});
} catch(e) {
	alert(e.message);
}
</script>			
</page:javascript>
