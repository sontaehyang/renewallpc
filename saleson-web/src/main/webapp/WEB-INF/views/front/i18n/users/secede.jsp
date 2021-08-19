<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="inner">
	
	<div class="content_top">
 		<div class="breadcrumbs">
 			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/delivery">회원정보</a> 
			<span>회원탈퇴</span>  
		</div>
 	</div><!--//content_top E-->
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<div id="contents" class="pt0">
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
		
		<div class="contents_inner"> 
			<h2>회원탈퇴</h2> 
		 	<form id="secedeForm" method="post">
			 	<div class="guide_box mt20">
				 	<ul>
				 		<li>회원 탈퇴시  회원 서비스를 모두 사용할 수 없습니다.</li>
						<li>상품 구매내역, 쿠폰 및 ${op:message('M00246')} 등 모든 정보가 삭제가 됩니다.</li>
						<li>탈퇴 시 재등록이 불가능 하오니 신중이 탈퇴를 진행해주시기 바랍니다.</li>
					</ul><!--// guide_box E-->
				</div>
				<c:if test='${!isSnsLogin}'>
					<p class="table_top mt30">안전한 회원 탈퇴를 위해 고객님의 회원 정보를 입력해주세요.</p>
					
				 	<div class="input_info clear">
				 		<dl class="clear">
				 			<dt>회원아이디</dt>
				 			<dd><input type="text" value="${user.loginId}" title="회원아이디" disabled="disabled"></dd>
				 			<dt class="mt10">비밀번호</dt>
				 			<dd class="mt10"><input type="password" name="userPassword" title="비밀번호" class="required" /></dd>
				 		</dl> 
				 	</div>
			 	</c:if>
			 	<h3>앙케이트</h3>
			 	<p class="table_top">그동안 리뉴올PC를 이용해 주셔서 감사합니다.  더 나은 컨텐츠 운영을 위한 설문조사이므로 솔직한 답변 부탁드리겠습니다.</p>
			 	<div class="board_wrap">
				 		<table cellpadding="0" cellspacing="0" class="board-write">
			 			<caption>table list</caption>
			 			<colgroup>
			 				<col style="width:180px;">
			 				<col style="width:auto;">	 				
			 			</colgroup>
			 			<tbody>
			 				<tr>
			 					<th valign="top">탈퇴 이유</th>
			 					<td class="out_reason">
			 						<ul>
			 							<li><input type="radio" name="leaveReason" id="leaveReason1" value="상품설명이 알기 어렵기 때문에" checked="checked" rel="1" /> <label for="leaveReason1">상품설명이 알기 어렵기 때문에</label></li>
			 							<li><input type="radio" name="leaveReason" id="leaveReason2" value="주문 및 문의시 직원의 대응이 만족스럽지 않아서" rel="2" /> <label for="leaveReason2">주문 및 문의시 직원의 대응이 만족스럽지 않아서</label></li>
			 							<li><input type="radio" name="leaveReason" id="leaveReason3" value="상품의 상태가 좋지 않아서" rel="3" /> <label for="leaveReason3">상품의 상태가 좋지 않아서</label></li>
			 							<li><input type="radio" name="leaveReason" id="leaveReason4" value="상품의 가격이 높아서" rel="4" /> <label for="leaveReason4">상품의 가격이 높아서</label></li>
			 							<li><input type="radio" name="leaveReason" id="leaveReason5" value="원하는 상품이 없어서" rel="5" /> <label for="leaveReason5">원하는 상품이 없어서</label></li>
			 						</ul>
			 					</td>
			 				</tr>
			 				<tr>
			 					<th class="bL0" valign="top">기타</th>
			 					<td class="out_reason bL0"> 
									<textarea id="leaveReasonEtc" name="leaveReasonEtc" title="기타"></textarea>
			 					</td>
			 				</tr>
			 			</tbody>
			 		</table>
		 		</div><!--//board_write_type01 E-->
		 		
		 		<div class="btn_wrap">
		 			<button type="submit" class="btn btn-success btn-lg">회원탈퇴</button>
		 			<!-- <button type="button" class="btn btn-default btn-lg">취소하기</button> -->
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

	$('#secedeForm').validator(function() {
		
		var $leaveReasonEtc = $("#leaveReasonEtc").val();
		var $leaveReason = $("input[name='leaveReason']:checked").val();
		
		if($leaveReasonEtc.trim().length > 0) {
			$("input[name='leaveReason']:checked").val($leaveReason + "/" + $leaveReasonEtc);
		} else {
			$("input[name='leaveReason']:checked").val($leaveReason);
		}
		
		if(!confirm("회원탈퇴 하시겠습니까?")){
			return false;
		}

		<!-- 에이스카운터 설치 [회원탈퇴] -->
		var _jn='withdraw';

	});
	
});
</script>
</page:javascript>