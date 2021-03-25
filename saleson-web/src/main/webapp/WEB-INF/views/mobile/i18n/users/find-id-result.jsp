<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
	
	<%-- 
	<div class="member_join">
		<h3 class="title">아이디 찾기 결과</h3>
		<div class="member_guide">
			<p>고객님의  아이디입니다.</p>
		</div><!--//member_guide E-->
		
		<div class="find_result">
			<p>${op:strcut(user.email, 3)}</p>
		</div><!--//find_result E-->
		<p class="find_txt01">개인정보 도용 방지를 위해 아이디 일부는 ***로 표시합니다.</p>
		 
		
		<div class="btn_area wrap_btn">
			<div class="sale division-2" style="display:block"> 
				<div>
					<div>
						<a href="/m/users/find-password" class="btn btn_out btn-w100">비밀번호 찾기</a>
					</div>
				</div>
				<div>
					<div>
						<a href="/m/users/login" class="btn btn_on btn-w100">로그인</a>
					</div>
				</div>
			</div>				 
		</div>
	</div><!--// member_join E-->
	 --%>

	<!-- 내용 : s -->
	<div class="con">
		<div class="pop_title">
			<h3>아이디 찾기</h3>
			<a href="javascript:history.go(-1);" class="history_back">뒤로가기</a>
		</div>
		<!-- //pop_title -->
		<div class="pop_con">

			<div class="join_area">
				<p class="ob_txt result_box">등록된 아이디는 <span class="user_id">${user.loginId}</span>입니다.</p>
				<div class="btn_area02">
					<button class="btn_st1 decision" type="button" title="로그인" onclick='location.href="/m/users/login"'>로그인</button>
				</div>
				<div class="pw_sh dash">
					<span>비밀번호를 잊으셨나요?</span>
					<button class="btn_st1" type="button" title="비밀번호 찾기" onclick="location.href='/m/users/find-password'">비밀번호 찾기</button>
				</div>
			</div>
			<!-- //join_area -->
			
		</div>
		<!-- //pop_con -->

	</div>
	<!-- 내용 : e -->
