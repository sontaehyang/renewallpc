<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>





<div id="wrap">
	


	<!-- container : s -->
	<div id="container">
		<div class="title">
			<h2>휴면계정 안내</h2>
			<span class="his_back"><a href="#" class="ir_pm">뒤로가기</a></span>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<!-- join_pr -->
			
			<div class="join_area">
				<div class="acc_txt">
					<p class="ob_txt3">장기간 미사용 계정 휴면 전환 안내</p>
					<p class="ob_txt sm">회원님의 계정은 개인정보보호를 위해 1년이상 리뉴올PC 서비스를 이용하지 않은 계정에 한해 정보통신망 이용 촉진 및 정보보호 등에 관한 법률에 따라 휴면계정으로 전환되었습니다.</p>
				</div>
				<div class="acc_btn">
					<button class="btn_st1 decision" type="button" title="리뉴올PC 서비스 계속 이용하기">리뉴올PC 서비스 계속 이용하기</button>
					<button class="btn_st1 reset" type="submit" title="휴면계정유지">휴면계정유지</button>
				</div>
				<!-- acc_btn -->
				<div class="search_way typeA">
					<dl>
						<dt>휴면 계정 시 제한 사항</dt>
						<dd>회원의 개인정보는 별도 분리하여 보관</dd>
						<dd>메일 수신 중지</dd>
					</dl>
					<dl>
						<dt>일반회원 전환 시 변경 사항</dt>
						<dd>개인정보 복원</dd>
						<dd>메일 수신 복원</dd>
					</dl>
				</div>
				<div class="so_noti">
					<span>리뉴올PC 이용약관</span>
					<p>회원이 12개월(365일)이상 로그인을 하지 않은 경우 해당 회원의 아이디는 휴면아이디가 되어 회원 로그인을 비롯한 모든 서비스에 대한 이용이 정지되고, 회사는 휴면아이디의 개인정보를 다른 아이디로 별도로 관리한다.</p>
				</div>
			</div>
			<!-- join_area -->
		</div>
		<!-- 내용 : e -->
		
	</div>
	<!-- //#container -->
	<!-- container : e -->
	
</div>
	
	<!-- 다음 주소검색 -->
	<daum:address />
		
<page:javascript>
<script type="text/javascript">
	var loginCheckId = '';
	$(function(){

		$('#user').validator(function() {

			var loginId = $("#email").val();
			if (!$.validator.patterns._email.test(loginId)) {
				alert($.validator.messages._email);
				$("#loginId").focus();
				return false;
			}
			
			if (userAvailabilityCheck(loginId, 'email') == false) {
				//alert("${op:message('M00160')}");
				alert("이미 등록된 메일주소입니다. 같은 메일주소로 회원등록은 불가능합니다.");
				$("#email").focus();
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

			/* 
			if ($("#positionType_0").attr('class') == 'required' && $("input[name=positionType]:checked").length == 0) {
					alert("${op:message('M00181')}  ");
					$("input[name=positionType]").eq(0).focus();
					return false;
			}
 			*/
 			
			if ($("#businessType_0").attr('class') == 'required' && $("input[name=businessType]:checked").length == 0) {
				alert("${op:message('M00183')}  ");
				$("input[name=businessType]").eq(0).focus();
				return false;
			}
			
			//Common.confirm("${op:message('M00159')} ", function(form) {
				
			$('#user').submit();
			//});
			return false;
		});

		idCheck();
		
	});


	
	
	
	
</script>
</page:javascript>