<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
		<div class="title">
			<h2>회원정보</h2>
			<span class="his_back"><a href="javascript:history.back();" class="ir_pm">뒤로가기</a></span>
			<ul class="tab_list01 four_tab letter">
				<li><a href="/m/users/editMode">회원정보 수정</a></li>
				<li><a href="/m/mypage/delivery">배송주소록 관리</a></li>
				<li><a href="/m/sns-user/setup-sns">SNS연동설정</a></li>
				<li class="on"><a href="#">회원탈퇴</a></li>
			</ul>
		</div>
		<!-- //title -->
		
		<!-- 내용 : s -->
		<div class="con">
			<div class="mypage_wrap">
			
			
			<form id="op-secedeForm" method="post">
				<div class="member_con">
					<c:if test='${!isSnsLogin}'>
						<h3>안전한 회원 탈퇴를 위해<br/>고객님의 <span>회원정보를 입력</span>해주세요.</h3>
						<div class="bd_table">
							<ul class="del_info">
								<li>
									<label for="a1" class="del_tit t_gray">아이디</label>
									<div class="input_area">
										<input id="a1" type="text" class="transparent" value="${user.loginId}" readonly="readonly">
									</div>
								</li>
								<li>
									<label for="a2" class="del_tit t_gray">비밀번호</label>
									<div class="input_area">
										<input id="a2" type="password" name="userPassword" class="required transparent" placeholder="비밀번호를 입력해주세요." title="비밀번호">
									</div>
								</li>
							</ul>
						</div>
					</c:if>
					<!-- //bd_table -->	
					<div class="search_way">
						<dl>
							<dd>회원 탈퇴 시 리뉴올PC 회원 서비스를 모두 사용할 수 없습니다.</dd>
							<dd>상품 구매내역, 쿠폰 및 ${op:message('M00246')} 등 모든 정보가 삭제가 됩니다.</dd>
							<dd>탈퇴 시 재등록이 불가능 하오니 신중이 탈퇴를 진행해 주시기바랍니다.</dd>
						</dl>
					</div>
					<!-- //search_way -->
					
					<div class="survey_wrap">
						<p class="txt">그 동안 리뉴올PC를 이용해 주셔서 감사합니다.<br/>더 나은 서비스를 위한 설문조사 이므로 솔직한 답변 부탁 드리겠습니다.</p>
						<h4>회원 탈퇴사유</h4>
						<ul class="survey_list">
							<li>
						        <input id="leaveReason1" type="radio" name="leaveReason" checked="checked" value="상품설명이 알기 어렵기 때문에">
						        <label for="leaveReason1"><span><span></span></span>상품설명이 알기 어렵기 때문에</label>
						    </li>
							<li>
						        <input id="leaveReason2" type="radio" name="leaveReason" value="직원의 대응이 만족스럽지 않아서">
						        <label for="leaveReason2"><span><span></span></span>직원의 대응이 만족스럽지 않아서</label>
						    </li>
							<li>
						        <input id="leaveReason3" type="radio" name="leaveReason" value="상품의 상태가 좋지 않아서">
						        <label for="leaveReason3"><span><span></span></span>상품의 상태가 좋지 않아서</label>
						    </li>
							<li>
						        <input id="leaveReason4" type="radio" name="leaveReason" value="상품의 가격이 높아서">
						        <label for="leaveReason4"><span><span></span></span>상품의 가격이 높아서</label>
						    </li>
							<li>
						        <input id="leaveReason5" type="radio" name="leaveReason" value="원하는 상품이 없어서">
						        <label for="leaveReason5"><span><span></span></span>원하는 상품이 없어서</label>
						    </li>
							<li>
						        <input id="leaveReason6" type="radio" name="leaveReason" >
						        <label for="leaveReason6"><span><span></span></span>기타</label>
						        <textarea id="leaveReasonEtc" name="leaveReasonEtc" id="op-textarea6" placeholder="기타 사유를 입력해주세요."></textarea>
						    </li>
					    </ul>
						
					
					</div>
					<div class="btn_wrap cf">
						<button type="reset" class="btn_st1 reset" onclick="location.href='/m'">취소</button>
						<button type="submit" class="btn_st1 decision">확인</button>
					</div>
				</div>
				<!-- //member_con -->
				</form>
			
			</div>
			<!-- //mypage_wrap -->
		
		</div>
		<!-- 내용 : e -->
<page:javascript>
<script type="text/javascript">
$(function(){
	$('#op-secedeForm').validator(function() {
		
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