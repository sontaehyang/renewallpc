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
			<span>주문/배송조회</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<div id="contents" class="pt0">
		 <jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" /> 
		 <div class="contents_inner">
		 	<h2>배송주소록 관리</h2>
			<p class="table_top">고객님께서 주문시 사용하셨던 배송지 목록입니다.</p>
			
			<div class="board_wrap">
				<form method="post" id="listForm">
		 			<table cellpadding="0" cellspacing="0" class="board-list">
			 			<caption>배송주소록 관리</caption>
			 			<colgroup>
			 				<col style="width:50px;">
			 				<col style="width:100px;">
			 				<col style="width:auto;">
			 				<col style="width:100px;">
			 				<col style="width:140px;">
			 				<col style="width:140px;">
			 			</colgroup>
			 			<thead>
			 				<tr>
			 					<th scope="col">선택</th>
			 					<th scope="col">배송지</th>
			 					<th scope="col">주소</th>
			 					<th scope="col">받는사람</th>
			 					<th scope="col">전화번호</th>
			 					<th scope="col">휴대전화번호</th>
			 				</tr>
			 			</thead>
			 			<tbody>
			 				<jsp:include page="../include/delivery-list.jsp" />
			 				
			 				<c:if test="${empty list}">
								<tr>
				 					<td colspan="7">
				 						<div class="coupon_not">등록된 배송지 정보가 없습니다.</div>
				 					</td> 
				 				</tr> 
							</c:if>	
			 				  
			 			</tbody>
			 		</table><!--//view E-->	
		 		</form>
		 	</div><!--//board_wrap E-->
		 	
		 	<div class="btn_wrap pt10">
			<div class="btn_left">
				<button type="button" class="btn btn-default btn-m" onclick="setDefaultAddr()">배송지로 선택</button> 
				<button type="button" class="btn btn-normal btn-m" onclick="editDelivery()">수정</button> 
				<button type="button" class="btn btn-normal btn-m" onclick="deleteAction()">삭제</button> 
			</div> <!-- // btn_left E --> 
			<div class="btn_right">
				<button type="button" class="btn btn-success btn-m " onclick="writeDelivery()">배송지 추가</button> 
			</div> <!-- // btn_right E --> 
		</div>
		 	
		 </div>
	</div>
	
</div>


<page:javascript>
<script type="text/javascript">
$(function(){
	// 메뉴 활성화
	$('#lnb_user').addClass("on");
});

function deleteAction() {
	$radio = $('#listForm').find('input[name="userDeliveryId"]:checked');
	if($radio.size() == 0) {
		alert(Message.get("M01574"));	// 삭제하실 배송지를 선택해 주세요. 
		return;
	} 
	
	if (!confirm(Message.get("M01575"))) {	// 선택하신 배송지를 삭제 하시겠습니까?
		return;
	}
	
	$('#listForm').attr('action', '/delivery/list-action/del?target=/mypage/delivery').submit();
}

function editDelivery() {
	$radio = $('#listForm').find('input[name="userDeliveryId"]:checked');
	if($radio.size() == 0) {
		alert(Message.get("M01577"));	// 수정하실 배송지를 선택해 주세요.
		return;
	} 
	
	Common.popup('/delivery/edit/' + $radio.val() + '?target=mypage', 'delivery', 898, 660);
}

function setDefaultAddr() {
	$radio = $('#listForm').find('input[name="userDeliveryId"]:checked');
	if($radio.size() == 0) {
		alert(Message.get("M01576"));	// 기본배송지로 설정하실 배송지를 선택해주세요.  
		return;
	} 
	
	if (!confirm(Message.get("M01578"))) {	// 선택하신 배송지를 기본 배송지로 설정 하시겠습니까? 
		return;
	} 
	
	$('#listForm').attr('action', '/delivery/list-action/mod?target=/mypage/delivery').submit();
}

function writeDelivery() {
	Common.popup('/delivery/write?target=mypage', 'delivery', 898, 660);
	return;
}
</script>
</page:javascript>