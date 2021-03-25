<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>

	<div class="title">
		<h2>회원가입</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<ol class="join_pr cf"> 
			<li><span>STEP.1</span><p>본인인증</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			<li class="on"><span>STEP.2</span><p>약관동의</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			<li><span>STEP.3</span><p>정보입력</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
			<li><span>STEP.4</span><p>가입완료</p><img src="/content/mobile/images/common/bg_pr.gif" alt=""></li>
		</ol>
		<!-- join_pr -->
			<form id="agreement" method="get" action="/m/users/entryForm">
				<div class="order_wrap join_area">
				<c:if test="${ redirect ne '' }">
					<input type="hidden" name="redirect" value="${ redirect }" />
				</c:if>
				<div class="policy_info">
					<div class="order_tit">
						<h3>이용약관</h3>
					</div>
					<div class="policy_con">
						${op:nl2br(agreement.content)}
					</div>
					<div class="policy_check">
					 	<input id="terms" type="checkbox" name="terms" value="1">
						<label for="terms">리뉴올PC 이용약관에 동의합니다.</label>
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
					 	<input id="privacy" type="checkbox" name="privacy" value="1">
						<label for="privacy">개인정보 수집 및 이용에 동의합니다.</label>
					</div>
				</div>
				<!-- //policy_info -->
				</div>
				<div class="pr_btn cf">
					<button class="btn_st1 reset" type="button" title="가입취소" onclick="location.href='/m'">가입취소</button>
					<button class="btn_st1 b_pink" type="submit" title="다음단계">다음단계</button>
				</div>
			</form>
		<!-- pr_btn -->
	</div>
	<!-- 내용 : e -->
	 
<page:javascript>
<script type="text/javascript">
$(function() {
	$('#agreement').validator(function() {
		if (!checkAgreement($('#terms'), 0)) return false;
		if (!checkAgreement($('#privacy'), 0)) return false;
	});
});

function checkAgreement($selector, type) {
	var messages = new Array('이용약관 및 개인정보 수집이용에 동의해 주십시요.');
	
	if (!$selector.prop("checked")) {
		alert(messages[type]);
		$selector.focus();
		return false;
	}
	
	return true;
}
</script>
</page:javascript>