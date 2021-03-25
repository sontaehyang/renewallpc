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
			<h2 class="title">회원가입</h2>  
		</div>
		<div class="join_step step02">
			<ul>
				<li><span>01</span> 본인인증</li>
				<li class="on"><span>02</span> 약관동의</li>
				<li><span>03</span> 정보입력</li>
				<li><span>04</span> 가입완료</li>
			</ul>
		</div> <!-- // join_step E -->
		
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
			
			<div class="btn_wrap clear">
				<button type="button" onclick="location.href='/'" class="btn btn-default btn-lg">가입취소</button>
				<button type="submit" class="btn btn-success btn-lg">다음단계</button>
			</div> 
			
		</form>
		 
	</div>
	
	
<page:javascript>
<script type="text/javascript">
$(function() {
	$('#agreement').validator(function() {
		if (!checkAgreement($('#terms_agree1'), 0)) return false;
		if (!checkAgreement($('#terms_agree2'), 1)) return false;
	});
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
</script>
</page:javascript>
