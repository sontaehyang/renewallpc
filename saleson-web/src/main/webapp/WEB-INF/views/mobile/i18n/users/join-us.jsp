<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div class="title">
		<h2>회원가입</h2>
		<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
	</div>
	<!-- //title -->
	
	<!-- 내용 : s -->
	<div class="con">
		<!-- join_pr -->
		<div class="join_area">
			<p class="ob_txt">원하시는 회원가입 방법을 선택해주세요.<br>리뉴올PC 회원이 되시면 쿠폰 등<br>다양한 혜택을 받으실 수 있습니다.</p>
			<ul class="ob_select cf">
				<li class="type01"><a href="/m/users/sns-join">SNS 인증</a></li>
				<li class="type02"><a href="/m/users/confirm">본인인증</a></li>
			</ul>
		</div>
		<!-- pr_btn -->
	</div>