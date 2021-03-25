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
			<p>원하시는 회원가입 방법을 선택해주세요. <br> 리뉴올PC 회원이 되시면 쿠폰 등 다양한 혜택을 받으실 수 있습니다.</p>
		</div>
		
		<div class="join_type">
			<div>
				<a href="/users/sns-join" title="SNS 인증">
					<h3>SNS 인증</h3>
					<p>보유하신 SNS 계정으로 <br>인증 하실 수 있습니다.</p>
				</a>
			</div>
			<div>
				<a href="/users/confirm" title="본인인증">
					<h3>본인인증</h3>
					<p>고객님의 휴대폰 인증 또는 <br>아이핀 인증을 하실 수 있습니다.</p>
				</a>
			</div>
		</div> <!-- // join_type E -->
		 
	</div>
