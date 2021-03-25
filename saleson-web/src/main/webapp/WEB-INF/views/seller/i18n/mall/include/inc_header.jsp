<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<!doctype html>
<html lang="ja">
<head>
	<jsp:include page="/WEB-INF/views/layouts/front/inc_head.jsp" />
</head>
<body>

<style>
#seller-mall-header {
	border-bottom: 1px solid #ccc;
}
#seller-mall-header h1 {
	height: 30px;
	font-size: 12px;
	margin: 0 0;
	padding-top: 4px;
}
#seller-mall-header h1 img {
	width: 100px;
}
#seller-mall-header .mall-wrap {
	position: relative;
}
.mall-wrap {
	width: 1050px;
	margin: 0 auto;
}
.mall-util-menu {
	position: absolute;
	right: 0;
	top: 8px;
	overflow: hidden;
}
.mall-util-menu li {
	float: left;
	padding: 0 8px;
}
.mall-util-menu li a {
	color: #333;
}
.mall-util-menu li a:hover {
	color: #000;
}
.mall-util-menu li a.home {
	font-weight: bold;
	color: #000;
}
.mall-util-menu li a.home:hover {
	color: #000;
	texe-decoration: none;
}
.mall-title {
	position: absolute;
	top: 90px;
	margin-left: 20px;
	font-size: 18px;
}
.mall-menu {
	position: relative;
	background: #34302d;
	color: #fff;
}
.mall-menu ul {
	overflow: hidden;
	padding-left: 10px;
}
.mall-menu li {
	float: left;
}
.mall-menu li a {
	display: block;
	padding: 10px 25px;
	color: #fff;
	
}
.mall-menu li a:hover {
	background: #6fb247;
	text-decoration: none;
}
.mall-menu .mall-search {
	position: absolute;
	right: 10px;
	top: 6px;
}
.mall-menu .mall-search .form-query {
	border: 1px solid #000;
	height: 24px;
	width: 150px;
}
.mall-category {
	padding-top: 20px;
}
.mall-category ul {
	overflow: hidden;
	border-bottom: 1px solid #ccc;
	border-left: 1px solid #ccc;
}
.mall-category li {
	float: left;
	width: 20%;
}
.mall-category li a {
	display: block;
	padding: 10px;
	text-align: center;
	border: 1px solid #ccc;
	margin-left: -1px;
	margin-bottom: -1px;
}
.mall-category li a.on {
	background: #f4f4f4;
	color: #000;
}
.mall-category li a:hover {
	background: #f4f4f4;
	text-decoration: none;
	color: #000;
}
.mall-item-search {
	border-bottom: 1px solid #34302d;
}
</style>

<div id="mini-mall">
	<div id="seller-mall-header">
		<div class="mall-wrap">
			<h1><a href="/"><img src="/content/images/common/saleson_logo.gif" alt="" /></a></h1>
			
	
			<ul class="mall-util-menu">
				<li><a href="/mall/${seller.loginId}" class="home"><span class="glyphicon glyphicon-home"></span> ${seller.sellerName}</a></li>
				<li><a href="javascript:;" onclick="addBookmark('SalesOn')">즐겨찾기</a></li>
				<sec:authorize access="hasRole('ROLE_USER')">
					<li><a href="/op_security_logout">로그아웃</a></li>
					<li><a href="/mypage/order">마이페이지</a></li>
				</sec:authorize>
				
				<sec:authorize access="!hasRole('ROLE_USER')">
					<li><a href="/users/login">로그인</a></li>
					<li><a href="/users/confirm">회원가입</a></li>
				</sec:authorize> 
	
				<li><a href="/cart">장바구니</a></li>
				<li><a href="/inquiry">1:1문의</a></li>
			</ul>
			
		</div>
	</div>
	<div class="mall-wrap">
		<div class="mall-header">
			<div class="mall-top-content">
				${seller.headerContent}
			</div>
			<%--
			<p class="mall-title">미니몰</p>
			<div>
				<img src="/content/images/seller/mini-mall.gif" alt="" />
			</div>
			--%>
			<div class="mall-menu">
				<ul>
					<li><a href="/mall/${seller.loginId}">메인</a></li>
					<%-- 
					<li><a href="#">공지사항</a></li>
					--%>
					<li><a href="/mall/${seller.loginId}/qna">Q&A</a></li>
					<li><a href="/mall/${seller.loginId}/review">리뷰</a></li>
				</ul>
				<div class="mall-search">
					<form id="MallSearchForm" action="/mall/${seller.loginId}">
						<input type="hidden" name="where" value="ITEM_NAME" />
						<input type="text" name="query" class="form-query" value="${itemParam.query}" />
						<button type="submit" class="btn btn-success btn-xs">검색</button>
					</form>

				</div>
			</div>
		</div>
		
		<div class="mall-container">
		
		
		
		
