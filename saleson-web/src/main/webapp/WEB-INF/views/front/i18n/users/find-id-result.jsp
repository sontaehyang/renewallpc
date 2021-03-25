<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
	
	
	<!-- 본문 -->
	<!-- 팝업사이즈 482*270-->
	
	<div class="popup_wrap">
		<h1 class="popup_title">아이디(이메일) 찾기</h1>
		<div class="popup_contents">
			<div class="popup_id"> 
				<p>등록된 아이디는 <span>${user.loginId}</span> 입니다.</p>
			</div><!--//popup_id E-->
		</div><!--//popup_contents E-->
		
		<div class="btn_wrap">
			<button type="button" onclick="movePage()" class="btn btn-success btn-lg">로그인</button> 
			<button type="button" onclick="finePassword()" class="btn btn-default btn-lg">비밀번호 찾기</button> 
		</div>
		<a href="javascript:self.close();" class="popup_close">창 닫기</a>
	</div>
	
<page:javascript>	
<script type="text/javascript">
function movePage() {
	opener.parent.location.href = "/users/login";
	self.close();
}

function finePassword() {
	Common.popup('/users/find-password', 'find-password', '526', '415');
	self.close();
}
</script>
</page:javascript>