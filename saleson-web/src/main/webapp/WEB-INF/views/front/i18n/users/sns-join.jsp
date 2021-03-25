<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>

	<div class="inner">
		<div class="location_area">
			<div class="breadcrumbs">
				<a href="/" class="home"><span class="hide">home</span></a>
			<span>회원가입</span> 
			</div>
		</div><!-- // location_area E -->
		
		
		<div class="content_title"> 
			<h2 class="title">SNS인증 회원가입</h2> 
<!-- 			<p>원하시는 회원가입 방법을 선택해주세요. <br> 리뉴올PC 회원이 되시면 쿠폰 등 다양한 혜택을 받으실 수 있습니다.</p> -->
			<p>보유하고 계신 SNS 계정으로 간편하게 회원가입 하실 수 있습니다.</p> 
		</div>
		
		<form id="agreement" action="/users/entryForm" method="post">
			<c:if test="${ redirect ne '' }">
				<input type="hidden" name="redirect" value="${ redirect }" />
			</c:if>
			
			<h3 class="sub_title mt30">이용약관</h3>
			
			<div class="agree_box">
				<div class="individual individual-sm">
					${op:nl2br(agreement.content)}
				</div><!-- // individual E -->	
			</div> <!-- // agree_box E -->
			
			<div class="agree_check">
				<input type="radio" id="terms_agree1" name="terms" value="1"> <label for="terms_agree1">동의함</label>&nbsp;&nbsp;
				<input type="radio" id="terms_agree1-2" name="terms"> <label for="terms_agree1-2">동의하지 않음</label>
			</div><!--//agree_check E--> 
			
			<h3 class="sub_title mt30 clear">개인정보의 수집·이용목적 및 항목</h3>
			
			<div class="agree_box">
				<div class="individual individual-sm">
					${op:nl2br(protectPolicy.content)}
				</div><!-- // individual E -->	
			</div> <!-- // agree_box E -->
			
			<div class="agree_check">
				<input type="radio" id="terms_agree2" name="privacy" value="1"> <label for="terms_agree2">동의함</label>&nbsp;&nbsp;
				<input type="radio" id="terms_agree2-2" name="privacy"> <label for="terms_agree2-2">동의하지 않음</label>
			</div> <!--//agree_check E--> 
		</form>
		<div class="sns_join">
			<ul>
				<c:if test='${snsType=="naver" || empty snsType}'>
					<li><a href="#" class="naver">네이버 계정으로 회원가입</a></li>
					<div id="naverIdLogin" style="display:none;"></div>
				</c:if>
				<%--<c:if test='${snsType=="facebook" || empty snsType}'>
					<li><a href="#" class="facebook">페이스북 계정으로 회원가입</a></li>
				</c:if>--%>
				<c:if test='${snsType=="kakao" || empty snsType}'>
					<li><a href="#" class="kakao">카카오 계정으로 회원가입</a></li>
				</c:if>
			</ul>
		</div>		 
	</div>
	
	<jsp:include page="../sns-user/naver.jsp"/>
	<jsp:include page="../sns-user/facebook.jsp"/>
	<jsp:include page="../sns-user/kakao.jsp"/>
	<jsp:include page="../sns-user/sns-response.jsp"/>
	
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
			$("div.sns_join ul li a").on('click',function(){
				if (!checkAgreement($('#terms_agree1'), 0)) return false;
				if (!checkAgreement($('#terms_agree2'), 1)) return false;
				
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