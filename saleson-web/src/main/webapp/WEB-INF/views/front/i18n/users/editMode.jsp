<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/delivery">회원정보</a> 
			<span>회원정보 수정</span> 
		</div>
	</div><!-- // location_area E -->
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<div id="contents" class="pt0"> 
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
		<div class="contents_inner">
			<h2>회원정보수정</h2>
			<p class="table_top">회원정보를 안전하게 보호하기 위해 비밀번호를 다시 한 번 확인 합니다.<br/>항상 비밀번호는 타인에 노출되지 않도록 주의해 주세요.</p>
		 	
		 	<form id="editModeForm" method="post" action="/users/modify">
		 	<div class="input_info clear">
		 		<input type="hidden" name="modifyResult" value="1" />
		 		<dl class="clear">
		 		<dt>회원아이디</dt>
		 			<dd><input type="text" value="${user.loginId}" title="회원아이디" disabled="disabled" ></dd>
		 			<dt class="mt10">비밀번호</dt>
		 			<dd class="mt10"><input type="password" name="userPassword" class="required" title="비밀번호"></dd>
		 		</dl>
		 		<button type="submit" class="btn btn_confirm" >확인</button>
		 	</div>
			</form>
		</div>
	</div>
	
</div>

<page:javascript>		
<script type="text/javascript">
$(function(){
	// 메뉴 활성화
	$('#lnb_user').addClass("on");
	
	$("#editModeForm").validator(function(){
	
	});
});
</script>
</page:javascript>