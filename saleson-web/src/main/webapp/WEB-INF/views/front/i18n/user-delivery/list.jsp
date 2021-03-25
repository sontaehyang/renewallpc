<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<!-- 본문 -->
<!-- 팝업사이즈 814*293-->
<div class="popup_wrap">
	<h1 class="popup_title">배송지 목록</h1>
	<div class="popup_contents"> 
		 <div class="board_wrap">
		 	<form method="post" id="listForm">
		 		<input type="hidden" name="target" value="${ target }" />
		 		<input type="hidden" name="receiverIndex" value="${ receiverIndex }" />
		 		
	 			<table cellpadding="0" cellspacing="0" class="board-list">
		 			<caption>배송비쿠폰 조회</caption>
		 			<colgroup> 
		 				<col style="width:auto;"> 
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
		 			</tbody>
		 		</table>
		 	</form> 
	 	</div> <!-- // board_wrap E --> 
	 	
	 	<div class="btn_wrap pt10"> 
			<div class="btn_left">
			
				<c:set var="url">/delivery/write</c:set>
 				<c:choose>
 					<c:when test="${ target eq 'order' }">
 						<c:set var="url">/delivery/write?target=order&receiverIndex=${receiverIndex}</c:set>
 						<button type="button" class="btn btn-default btn-m" onclick="selectDelivery()">${op:message('M01582')}</button> <!-- 배송지로 선택 -->
 					</c:when>
 					<c:otherwise>
 						<button type="button" class="btn btn-default btn-m" onclick="editDelivery()">v ${op:message('M00087')}</button> <!-- 수정 -->
 						<button type="button" class="btn btn-default btn-m" onclick="deleteAction()">x ${op:message('M00074')}</button> <!-- 삭제 -->
 					</c:otherwise>
 				</c:choose>
 			</div> <!-- // btn_left E --> 
 			<div class="btn_right">
 				<button type="button" class="btn btn-default btn-m" onclick="setDefaultAddr()">${op:message('M01572')}</button>  <!-- 기본 배송지로 설정 -->
		 				
		 		<button type="button" class="btn btn-success btn-m" onclick="location.href='${ url }'">${op:message('M01573')}</button> <!-- 배송지 추가 -->
 			</div> <!-- // btn_left E -->  
		</div>
	</div><!--//popup_contents E--> 
	<a href="javascript:self.close()" class="popup_close">창 닫기</a>
</div>


<page:javascript>
<script type="text/javascript">
function uppercase(text) {
	if (text == '' || text == undefined) return text;
	return text.substring(0, 1).toUpperCase() + text.substring(1);
}

function selectDelivery() {
	$radio = $('#listForm').find('input[name="userDeliveryId"]:checked');
	if($radio.size() == 0) {
		return;
	} 
	var postcode = "";
	$.each($('input, select', $('#delivery-info-' + $radio.val())), function() {
		
		if ($(this).attr('id') == 'zipcode') {
			postcode = $(this).val();
		}
		
		var name = uppercase($(this).attr('id'));
		name = 'receive' + name;

		if (name == 'receiveUserName') {
			name = 'receiveName';
		}
		
		name = 'receivers[${receiverIndex}].' +  name;
		if (opener.$('input[name="' + name + '"]').size() == 1) {
			opener.$('input[name="' + name + '"]').val($(this).val());
		}else if (opener.$('select[name="' + name + '"]').size() == 1) {
			opener.$('select[name="' + name + '"]').val($(this).val());
		}
	});
	
	try {
		
		opener.Order.changeReceiverZipcode(postcode, ${receiverIndex});
		
		// 배송지에 따른 배송비 설정
		opener.Order.setShippingAmount();
		
		opener.Order.setAmountText();
	} catch(e) {}
	self.close();
}

function deleteAction() {
	$radio = $('#listForm').find('input[name="userDeliveryId"]:checked');
	if($radio.size() == 0) {
		alert(Message.get("M01574"));	// 삭제하실 배송지를 선택해 주세요. 
		return;
	} 
	
	if (!confirm(Message.get("M01575"))) {	// 선택하신 배송지를 삭제 하시겠습니까?
		return;
	}
	
	$('#listForm').attr('action', '/delivery/list-action/del').submit();
}

function editDelivery() {
	$radio = $('#listForm').find('input[name="userDeliveryId"]:checked');
	if($radio.size() == 0) {
		alert(Message.get("M01577"));	// 수정하실 배송지를 선택해 주세요.
		return;
	} 
	
	location.href = '/delivery/edit/' + $radio.val();
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
	
	$('#listForm').attr('action', '/delivery/list-action/mod').submit();
}
</script>
</page:javascript>