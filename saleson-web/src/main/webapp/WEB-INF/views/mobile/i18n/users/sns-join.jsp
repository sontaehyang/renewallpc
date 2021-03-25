<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

	<div class="title">
		<h2>SNS인증 회원가입</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con snsJoin_wrap">
		<div class="order_wrap join_area">
			<div class="policy_info">
				<div class="order_tit">
					<h3>이용약관</h3>
				</div>
				<div class="policy_con">
					${op:nl2br(agreement.content)}
				</div>
				<div class="policy_check">
					<input id="policy01" type="checkbox" name="checkbox">
					<label for="policy01">리뉴올PC 이용약관에 동의합니다.</label>
				</div>
			</div>
		<!-- //policy_info -->
		
		<div class="policy_info">
			<div class="order_tit">
				<h3>개인정보의 수집 · 이용목적 및 항목</h3>
			</div>
			<div class="policy_con">
				${op:nl2br(protectPolicy.content)}
			</div>
			<div class="policy_check">
			 	<input id="policy02" type="checkbox" name="checkbox">
				<label for="policy02">개인정보 수집 및 이용에 동의합니다.</label>
			</div>
		</div>
		<!-- //policy_info -->
		
		
	</div>
		<!-- join_pr -->
		<div class="join_area">
			<p class="ob_txt">보유하고 계신 SNS 계정으로<br>간편하게 회원가입 하실 수 있습니다.</p>
			<ul class="ob_easy">
				<c:if test='${snsType=="naver" || empty snsType}'>
					<li class="join_naver naver"><a href="#">네이버 계정으로 회원가입</a></li>
					<div id="naverIdLogin" style="display:none;"></div>
				</c:if>
				<%--<c:if test='${snsType=="facebook" || empty snsType}'>
					<li class="join_fb facebook"><a href="#">페이스북 계정으로 회원가입</a></li>
				</c:if>--%>
				<c:if test='${snsType=="kakao" || empty snsType}'>
					<li class="join_kakao kakao"><a href="#">카카오 계정으로 회원가입</a></li>
				</c:if>
			</ul>
		</div>
		<!-- pr_btn -->
		<div class="pr_btn cf">
			<button class="btn_st1 reset w100p" type="button" title="가입취소" onclick="history.back();">가입취소</button>
		</div>
	</div>
	<!-- 내용 : e -->
	<jsp:include page="../sns-user/naver.jsp"/>
<%--<jsp:include page="../sns-user/facebook.jsp"/>--%>
	<jsp:include page="../sns-user/kakao.jsp"/>
	<jsp:include page="/WEB-INF/views/front/i18n/sns-user/sns-response.jsp"/>
		
	<page:javascript>
	<script type="text/javascript">
		$(function(){
			joinWithSns();
		});
		function checkAgreement($selector, type) {
			var messages = new Array('회원 이용약관에 동의해 주십시요.','개인정보 수집 및 이용에 대한 약관에 동의해 주십시요.');
			
			if (!$selector.prop("checked")) {
				alert(messages[type]);
				$selector.focus();
				return false;
			}
			
			return true;
		}
		
		function joinWithSns() {
			$("ul.ob_easy li").on('click',function(){
				if (!checkAgreement($('#policy01'), 0)) return false;
				if (!checkAgreement($('#policy02'), 1)) return false;
				
				if ($(this).hasClass("naver")) {
					naverProcess();
				}
				else if ($(this).hasClass("facebook")) {
					fb_login();
				}
				else if ($(this).hasClass("kakao")) {
					loginWithKakao();
				}
			});
		}
	</script>
	</page:javascript>
