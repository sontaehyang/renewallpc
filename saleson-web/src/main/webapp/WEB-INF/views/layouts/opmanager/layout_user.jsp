<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<!doctype html>
<html lang="ja" class="opmanager">
<head>
<tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>
<body>
<div class="popup_wrap" style="min-width:1080px;">
	<h1 class="popup_title">${op:message('M00831')}</h1> <!-- 회원정보관리 -->
	
	<!-- 이메일/연락처/휴대폰 검색 창 
	<div class="pop_search">
		<p>
			<input type="radio" name="" id="email" checked="checked"> <label for="email">이메일</label>
			<input type="radio" name="" id="tel"> <label for="tel">연락처</label>
			<input type="radio" name="" id="phone"> <label for="phone">휴대폰</label> 
			<input type="text" class="count_down2" title="검색 입력">  
			<button type="button" class="table_btn"><span>검색</span></button>
		</p>
	</div>
	-->
	<!-- // 이메일/연락처/휴대폰 검색 창 -->
			
	<!-- lnb -->
	<div class="lnb_wrap">
		<div class="lnb02">
			<ul class="lnbs02">
				<li id="details"><a href="/opmanager/user/popup/details/${userId}">${op:message('M00824')}</a> <!-- 기본정보 -->
				<%-- <li id="order"><a href="/opmanager/user/popup/order/${userId}">${op:message('M00825')}</a></li> --%> <!-- 주문내역 -->
				<%--<li id="order"><a href="javascript:opener.location.replace('/opmanager/order/list?where=EMAIL&query=${user.email}');">${op:message('M00825')}</a></li>--%> <!-- 주문내역 -->
				
				<li id="point"><a href="/opmanager/user/popup/point/point/${userId}">${op:message('M00826')}/${op:message('M00827')}</a></li>	<!-- 포인트 지급 --> <!-- 사용 관리 -->
				<li id="coupon"><a href="/opmanager/user/popup/coupon/${userId}">${op:message('M00828')}/${op:message('M00827')}</a></li> <!-- 쿠폰 지급 -->
				<!--  
				<li id="delivery"><a href="">개별배송정보</a></li>
				<li id="qna"><a href="">문의 관리</a></li>
				<li id="email"><a href="">이메일 전송</a></li>
				-->
				<li id="delivery"><a href="/opmanager/user/popup/delivery-list/${userId}">${op:message('M01571')}</a></li> <!-- 나의 배송지 관리 -->
				<li id="modify"><a href="/opmanager/user/popup/edit/${userId}">${op:message('M00829')}</a></li> <!-- 회원정보수정 -->
				<li id="secede"><a href="/opmanager/user/popup/delete/${userId}">${op:message('M00830')}</a></li> <!-- 회원탈퇴 -->
				<li><a href="javascript:Common.popup('/opmanager/order/new-order/${userId}', 'new-order', 1024, 1024, 1)">수기 결제</a></li>
			</ul>
		</div> <!-- // lnb02 --> 
		<div class="lnb_total">
			
			<h2 class="total_tit">${user.userDetail.userlevel.levelName}</h2>
			<ul class="total">
				<li class="member_name">${user.userName} (${user.email})</a>
				<li>최근접속일 : ${op:datetime(user.loginDate) }</li>
			</ul>
			
			<!-- 
			<h2 class="total_tit">TOTAL</h2>
			<ul class="total">
				<li><span class="total_l">주문금액 :</span> <span class="total_r"> 1,100,000,000원</span></li>
				<li><span class="total_l">결제금액 :</span> <span class="total_r"> 100,000,000원</span></li>
				<li><span class="total_l">주문건수 :</span> <span class="total_r"> 1,000건</span></li>
				<li><span class="total_l">주문취소 :</span> <span class="total_r"> 1,000,000건</span></li>
				<li><span class="total_l">${op:message('M00246')} :</span> <span class="total_r"> 100,000건</span></li>
			</ul>
			
			<h2 class="total_tit">TODAY</h2>
			<ul class="total">
				<li><span class="total_l">주문금액 :</span> <span class="total_r red">10,000,000원</span></li>
				<li><span class="total_l">결제금액 :</span> <span class="total_r red"> 10,000,000원</span></li>
				<li><span class="total_l">주문건수 :</span> <span class="total_r red"> 10건</span></li>
			</ul>
			 -->
		</div>
	</div> <!-- // lnb_wrap -->
	
	
	<div class="popup_contents02">
		<tiles:insertAttribute name="content"></tiles:insertAttribute>		
	</div>
	
	<a href="javascript:self.close();" class="popup_close">창 닫기</a>
</div>
		
<tiles:insertAttribute name="common"></tiles:insertAttribute>
<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>