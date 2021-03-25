<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<!-- 본문 -->
<!-- 팝업사이즈 898*307-->
<div class="con">
	<div class="pop_title">
		<h3>배송지 목록</h3>
		<a href="javascript:history.back();" class="history_back">뒤로가기</a>
	</div>
	<form method="post" id="listForm">
		<input type="hidden" name="target" value="${ target }" />
		<div class="pop_conA"> 
			<button type="button" class="btn_st3 full" onClick="location.href='/m/delivery/write?target=order'">배송지 추가</button>
			 <div class="list_info">
			 	<c:set var="target" value="${ target }" scope="request" />
		 		<jsp:include page="../include/delivery-list.jsp" />
		 	</div>
		</div>
		<c:if test="${empty list}">
			<div class="notice_none">
				<p>${op:message('M00473')}</p> <!-- 데이터가 없습니다. --> 
			</div>
		</c:if>
		
			<%--div class="popup_btns">
				 <div class="btn_wrap">  
					<div class="btn_l">
		 				<c:set var="url">/delivery/write</c:set>
		 				<c:choose>
		 					<c:when test="${ target eq 'order' }">
		 						<c:set var="url">/delivery/write?target=order</c:set>
		 						<button type="button" class="btn btn-default btn-m" onclick="selectDelivery()">${op:message('M01582')}</button> <!-- 배송지로 선택 -->
		 					</c:when>
		 					<c:otherwise>
		 						<button type="button" class="btn btn-default btn-m" onclick="editDelivery()">v ${op:message('M00087')}</button> <!-- 수정 -->
		 						<button type="button" class="btn btn-default btn-m" onclick="deleteAction()">x ${op:message('M00074')}</button> <!-- 삭제 -->
		 					</c:otherwise>
		 				</c:choose>  
		 			</div> <!-- // btn_left E --> 
		 			<div class="btn_r">
		 				<button type="button" class="btn btn-default btn-m" onclick="setDefaultAddr()">${op:message('M01572')}</button>  <!-- 기본 배송지로 설정 -->
		 				
		 				<button type="button" class="btn btn-success btn-m" onclick="location.href='${ url }'">${op:message('M01573')}</button> <!-- 배송지 추가 --> 
		 			</div> <!-- // btn_right E -->
		 		</div>
			</div> --%>
	</form>
</div>


<page:javascript>
<script type="text/javascript">

function uppercase(text) {
	if (text == '' || text == undefined) return text;
	return text.substring(0, 1).toUpperCase() + text.substring(1);
}

function selectDelivery(userDeliveryId) {
	var postcode = "";
	
	$deliverys = $('#op-delivery-info-' + userDeliveryId).find('input');
	
	$.each($deliverys, function() {
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
	
	deliveryViewClose();
}

function setDefaultAddr(userDeliveryId) {
	
	if (!confirm(Message.get("M01578"))) {	// 선택하신 배송지를 기본 배송지로 설정 하시겠습니까? 
		return;
	}
	
	$('#listForm').attr('action', '/m/delivery/list-action/mod?userDeliveryId=' + userDeliveryId).submit(); //json change
}

function deliveryViewClose() {
	if (isMobileLayer == true || isMobileLayer == 'true') {
		parent.$('.op-app-popup-wrap').show();
		parent.$('.op-app-popup-content').hide();
	} else {
		self.close();
	}
}
</script>
</page:javascript>