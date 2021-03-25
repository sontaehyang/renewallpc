<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<%-- 
	<div class="member_join">
		<form id="password-change-form" method="post" action="/m/users/find-password-change">
			<input type="hidden" name="userId" value="${user.userId}" />
			<h3 class="title">비밀번호 재설정</h3>
			<div class="member_guide">
				<p> <span>새로운 비밀번호</span>를 입력해 주세요. </p>
			</div><!--//member_guide E-->
			
			<div class="table_write">
				<table cellpadding="0" cellspacing="0">
					<colgroup>
						<col style="width:50%;">
						<col style="width:50%;">
					</colgroup>
					<tbody>
						<tr>
							<th>새로운 비밀번호</th>
							<td>
								<input type="password" name="password" id="password" title="새로운 비밀번호" class="full _password required optional" minlength="4"  maxlength="20"/>
							</td> 
						</tr>
						<tr>
							<th>새로운 비밀번호 확인</th>
							<td>
								<input type="password" name="password2" id="password_confirm" title="새로운 비밀번호 확인" class="full _password required optional" minlength="4"  maxlength="20"/>
							</td> 
						</tr> 
					</tbody>
				</table>		
			</div><!--//table_write E-->	
			
			<div class="btn_area wrap_btn">
				<div class="sale division-1" style="display:block"> 
					<div>
						<div>
							<button type="submit" class="btn btn_on btn-w100">확인</button>
						</div>
					</div>
				</div>				 
			</div>
		</form>
	</div><!--// member_join E--> --%>

	<!-- 내용 : s -->
	<form id="password-change-form" method="post" action="/m/users/find-password-change">
		<input type="hidden" value="${user.userId}" name="userId">
		<div class="con">
			<div class="pop_title">
				<h3>비밀번호 찾기</h3>
				<a href="javascript:history.go(-1);" class="history_back">뒤로가기</a>
			</div>
			<!-- //pop_title -->
			
			<p class="code_tit">새로운 비밀번호를 입력해 주세요.</p>
			<div class="bd_table typeB">
				<ul class="del_info">
					<li>
						<label for="a1" class="del_tit t_gray">새로운 비밀번호</label>
						<div class="input_area">
							<input id="password" name="password" type="password" class="transparent">
						</div>
					</li>
					<li>
						<label for="a2" class="del_tit t_gray">새로운 비밀번호<br/>확인</label>
						<div class="input_area">
							<input id="password_confirm" name="password_confirm" type="password" class="transparent pw_re">
						</div>
					</li>
				</ul>
			</div>
			<div class="btn_area02">
				<button class="btn_st1 decision" type="submit" title="확인">확인</button>
			</div>
			
		
	
		</div>
	</form>
	<!-- 내용 : e -->

<page:javascript>
<script type="text/javascript">
$(function() {
	$('#password-change-form').validator(function() {
		if ($("#password").val() != $("#password_confirm").val()) {
			alert("${op:message('M00158')}");
			$("#password_confirm").fucous();
			return false;
		}
	});
});
</script>
</page:javascript>