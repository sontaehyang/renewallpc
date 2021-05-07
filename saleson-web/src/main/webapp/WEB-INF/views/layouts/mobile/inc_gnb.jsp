<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>

<c:choose>
	<c:when test="${fn:indexOf(requestContext.requestUri, '/m/products/view') > -1}">
		<!--  상품 상세 페이지 헤더 -->
		<div class="product_detail_bar">
			<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
			<ul class="tab_list03 tab_list_top">
				<li class="on"><a href="#tab_detail">상세정보</a></li>
				<li><a href="#tab_info;">구매안내</a></li>
				<li><a href="#tab_review;">후기 <span class="item-review-count" style="display:inline-block;">0</span></a></li>
				<li><a href="#tab_qna;">상품문의</a></li>
			</ul>
		</div>
		<!--  상품 상세 페이지 헤더 -->
	</c:when>
	<c:otherwise>
		<c:set var="gnbHome" value="" />
		<c:set var="gnbBattleGrounds" value="" />
		<c:set var="gnbOverwatch" value="" />
		<c:set var="gnbHighProfessional" value="" />
		<c:set var="gnbBodyMonitor" value="" />
		<c:set var="gnbLabtop" value="" />
		<c:set var="gnbCashSpecial" value="" />

		<c:choose>
			<c:when test="${fn:startsWith(requestContext.requestUri, '/m/pages/battle_grounds_pc') == 'true'}">
				<c:set var="gnbBattleGrounds" value="class='on'" />
			</c:when>
			<c:when test="${fn:startsWith(requestContext.requestUri, '/m/pages/overwatch') == 'true'}">
				<c:set var="gnbOverwatch" value="class='on'" />
			</c:when>
			<c:when test="${fn:startsWith(requestContext.requestUri, '/m/pages/high_professional') == 'true'}">
				<c:set var="gnbHighProfessional" value="class='on'" />
			</c:when>
			<c:when test="${fn:startsWith(requestContext.requestUri, '/m/pages/body_monitor') == 'true'}">
				<c:set var="gnbBodyMonitor" value="class='on'" />
			</c:when>
			<c:when test="${fn:startsWith(requestContext.requestUri, '/m/pages/renewallpc') == 'true'}">
				<c:set var="gnbLabtop" value="class='on'" />
			</c:when>
			<c:when test="${fn:startsWith(requestContext.requestUri, '/m/pages/cash_special') == 'true'}">
				<c:set var="gnbCashSpecial" value="class='on'" />
			</c:when>
			<c:otherwise>
				<c:set var="gnbHome" value="class='on'" />
			</c:otherwise>
		</c:choose>

		<!-- 공통 헤더 -->
		<div class="category">
			<ul>
				<li><a href="/m" ${gnbHome}>홈</a></li>
				<li><a href="/m/pages/battle_grounds_pc" ${gnbBattleGrounds}>배틀그라운드</a></li>
				<li><a href="/m/pages/overwatch" ${gnbOverwatch}>오버워치</a></li>
				<li><a href="/m/pages/high_professional" ${gnbHighProfessional}>고성능/전문가용PC</a></li>
				<li><a href="/m/pages/body_monitor" ${gnbBodyMonitor}>본체+모니터</a></li>
				<li><a href="/m/pages/renewallpc" ${gnbLabtop}>노트북</a></li>
				<li><a href="/m/pages/cash_special" ${gnbCashSpecial}>현금특가</a></li>
			</ul>
		</div>
	</c:otherwise>
</c:choose>