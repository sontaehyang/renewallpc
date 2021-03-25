<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="title">
	<h2>비회원 약관동의</h2>
	<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
</div>
<!-- //title -->

<!-- 내용 : s -->
<div class="con">
	<div class="order_wrap">
		<div class="order_txt">
			<p class="non_member_txt">비회원으로 구매하시면 리뉴올PC에서 드리는 쿠폰할인 및 ${op:message('M00246')} 혜택을 받으실 수 없습니다.<br>
			또한 비회원 주문 시에는 ${op:message('M00246')}가 지급되지 않습니다.</p>
			<div class="order_btn_wrap">
				<a href="/m/users/login" class="btn_st1 b_blue">로그인</a>
				<a href="/m/users/join-us" class="btn_st1 b_pink">회원가입</a>
			</div>
		</div>
		<!-- //order_txt -->
		
		<div class="policy_info">
			<div class="order_tit">
				<h3>이용약관</h3>
			</div>
			<div class="policy_con">
				${op:nl2br(agreement.content)}
			</div>
			<div class="policy_check">
			 	<input id="policy01" type="checkbox" name="ae1" value="1">
				<label for="policy01">리뉴올PC 이용약관에 동의합니다.</label>
			</div>
		</div>
		<!-- //policy_info -->
		
		<div class="policy_info">
			<div class="order_tit">
				<h3>개인정보의 수집 &#183; 이용목적 및 항목</h3>
			</div>
			<div class="policy_con">
				${op:nl2br(protectPolicy.content)}
			</div>
			<div class="policy_check">
			 	<input id="policy02" type="checkbox" name="ae2" value="1">
				<label for="policy02">개인정보 수집 및 이용에 동의합니다.</label>
			</div>
		</div>
		<!-- //policy_info -->
		
		<div class="next_btn_wrap">
			<a href="javascript:nextStep();" class="btn_st1 b_sblue">다음단계로 이동</a>
		</div>
		<!-- //next_btn_wrap -->
		
	</div>
	<!-- //order_wrap -->

</div>
<!-- 내용 : e -->


<page:javascript>
<script type="text/javascript">
	function nextStep() {
		
		if ($(':checkbox[name="ae1"]:checked').val() != '1') {
			alert('이용약관에 동의후 구매 진행이 가능합니다.');
			return;
		}
		
		if ($(':checkbox[name="ae2"]:checked').val() != '1') {
			alert('개인정보 취급방침에 동의후 구매 진행이 가능합니다.');
			return;
		}
		
		location.href = '/m/order/step1';
	}
</script>
</page:javascript>