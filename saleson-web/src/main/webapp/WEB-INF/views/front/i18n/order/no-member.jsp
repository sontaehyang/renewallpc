<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a> 
			<span>비회원 약관동의</span> 
		</div>
	</div><!-- // location_area E -->
 
	<div class="noMember_guide">
		<p>비회원으로 구매하시면 리뉴올PC에서 드리는 쿠폰할인 및 ${op:message('M00246')} 적립 혜택을 받으실수 없습니다.<br>또한 비회원 주문시에는 ${op:message('M00246')}가 지급되지 않습니다. </p>
		<div class="btnZone">
			<a href="/users/login" class="btn btn-w102 btn-submit">로그인</a>
			<a href="/users/join-us" class="btn btn-w102 btn-default">회원가입</a>
		</div>
	</div><!-- // noMember_guide E --> 
	 			
	<h3 class="sub_title mt30">이용약관</h3>			
	<div class="agree_box">
		<div class="individual individual-sm">
			${op:nl2br(agreement.content)}
		</div><!-- // individual E -->				
	</div> <!-- // agree_box E -->
	
	<div class="agree_check">
		<input type="radio" name="ae1" id="agree_01" value="1"> <label for="agree_01">동의함</label>&nbsp;&nbsp;
		<input type="radio" name="ae1" id="agree_02" value="2"> <label for="agree_02">동의하지 않음</label> 
	</div>
	<h3 class="sub_title mt30 clear">개인정보의 수집·이용목적 및 항목</h3>
	<div class="agree_box">
		<div class="individual individual-sm">
			${op:nl2br(protectPolicy.content)}
		</div><!--//individual E-->
	</div>
	<div class="agree_check">
		<input type="radio" name="ae2" id="agree_03" value="1"> <label for="agree_03">동의함</label>&nbsp;&nbsp;
		<input type="radio" name="ae2" id="agree_04" value="2"> <label for="agree_04">동의하지 않음</label> 
	</div>
	
	<div class="btn_wrap clear"> 
		<a href="javascript:nextStep();" class="btn btn-success btn-lg" title="다음단계">다음단계</a>
	</div>
		
			
</div><!--// inner E-->

<page:javascript>
<script type="text/javascript">
	function nextStep() {
		
		if ($(':radio[name="ae1"]:checked').val() != '1') {
			alert('이용약관에 동의후 구매 진행이 가능합니다.');
			return;
		}
		
		if ($(':radio[name="ae2"]:checked').val() != '1') {
			alert('개인정보 취급방침에 동의후 구매 진행이 가능합니다.');
			return;
		}
		
		location.href = '/order/step1';
	}
</script>
</page:javascript>