<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	 
		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		
		<form:form modelAttribute="delivery" method="post" enctype="multipart/form-data">
			<h3><span>${op:message('M00643')}</span></h3> <!-- 배송비 설정 --> 
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M00643')}">
					<caption>${op:message('M00643')}</caption>
					<colgroup>
						<col style="width:150px;">
						<col style="width:*">
					</colgroup>
					
					<tbody>
						 <tr>
							<td class="label">${op:message('M00635')}</td> <!-- 배송비 정책 제목 --> 
						 	<td>
						 		<div>						 			 								 
									<form:input type="text" path="title" class="required full" maxlength="50" title="${op:message('M00275')}" /> <!-- 제목 --> 
									<input type="hidden" name="vendorId" value="1000"/>
								</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00636')}</td> <!-- 배송 구분 --> 
						 	<td>
						 		<div>
									<c:choose>
										<c:when test="${delivery.deliveryType == null || delivery.deliveryType == ''}">
											<form:radiobutton path="deliveryType" value="1" onclick="javascript:deliveryTypeCheck()" label="${op:message('M00637')}" checked="checked" /> <!-- 묶음배송 --> 
									  		<form:radiobutton path="deliveryType" value="2" onclick="javascript:deliveryTypeCheck()" label="${op:message('M00638')}" /> <!-- 개별배송 -->
										</c:when>
										<c:otherwise>
											${delivery.deliveryType == 1 ? op:message('M00637') : op:message('M00638')}
											<input type="hidden" name="deliveryType" id="editDeliveryType" value="${delivery.deliveryType}" />
										</c:otherwise>
									</c:choose>
								</div>
						 	</td>	
						 </tr>
						 <input type="hidden" name="mailFlag" value="N" />
						 <input type="hidden" name="deliveryChargePolicy" value="1" />
						 
						 
						 <tr>
							<td class="label">제주도</td>
						 	<td>
						 		<div>
						 			배송비 : <form:input type="text" path="deliveryExtraCharge1" class="required one _number" maxlength="6" title="제주도 배송비"/> ${op:message('M00049')} / 
						 			<form:input path="deliveryExtraChargeFree1" class="required one _number" maxlength="6" title="제주도 무료배송 조건" /> 원 이상 구매시 배송비 무료	
								</div>
						 	</td>
						</tr>
					
					</tbody>		 
				</table>								 							
			</div> <!--//board_write E-->
			<h3 class="title"><span>${op:message('M00067')}</span> <!-- 배송비 -->
			
			<!-- <div class="addDelivery btn_right">
				<button type="button" onclick="javascript:addDeliveryKind();" class="btn btn-dark-gray btn-sm"><span>${op:message('M00655')}</span></button> <!-- 배송비 추가 --> 				  
			<!-- </div> -->
			</h3>
			<div class="board_write">
				<table class="board_write_table" id="writeTable" summary="${op:message('M00067')}">
					<caption>${op:message('M00067')}</caption>
					<colgroup>
						<col style="width:150px;">
						<col style="width:*"> 
					</colgroup>
						<c:forEach items="${delivery.deliveryChargeList}" var="list" varStatus="i" >
							<c:if test="${list.deliveryChargeType != null}">	
							<tbody style="border-top : 1px solid #d5d5d5;">
								<tr>
									<td class="label">${op:message('M00652')}</td> <!-- 종류 --> 
									<td>
								 		<div>
								 			<c:choose>
												<c:when test="${list.deliveryChargeType == 1}">
									 				${op:message('M00653')} <!-- 무료 (판매자 부담) -->
									 			</c:when>
									 			<c:when test="${list.deliveryChargeType == 2}">
									 				${op:message('M00639')} <!-- 유료 -->
									 			</c:when>
									 			<c:when test="${list.deliveryChargeType == 3}">
									 				${op:message('M00640')} <!-- 조건부 무료 -->
												</c:when>
									 		</c:choose>
										</div>
								 	</td>	
								</tr> 
								<tr>	
									<td class="label">${op:message('M00654')}</td> <!-- 금액 -->			
									<td>
								 		<div>
								 			${list.deliveryCharge}${op:message('M00049')}			
								 			<c:if test="${list.deliveryChargeType == 3}">
								 				(${list.deliveryFreeAmount}${op:message('M00641')}) <!-- 원 이상 구매시 배송비 무료 -->
								 			</c:if> 
										</div>
									</td>
								</tr> 
								<tr>
									<td></td>
									<td>
										<div class="btn_right">
											<a href="javascript:deliveryEditPopup('${list.deliveryChargeId}');" class="table_btn">${op:message('M00087')}</a> <!-- 수정 --> 
											<!-- <a href="javascript:deleteCheck('${list.deliveryId}', '${list.deliveryChargeId}')" class="table_btn">${op:message('M00074')}</a> --> <!-- 삭제 -->
										</div>
									</td>
								</tr>
							</tbody>		
						</c:if> 
					</c:forEach>				 
				</table>	
			</div> <!--//board_write E-->
			<div class="btn_center">
				<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> &nbsp; <!-- 저장 --> 
				<a href="/opmanager/delivery/list" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 -->
			</div>
		</form:form>

<table class="board_write_table" id="addWriteTable" style="display: none;">		
	<tbody>
		<tr>
			<td class="label">${op:message('M00652')}</td> <!-- 종류 --> 
			<td>
		 		<div>
		 			<input type="hidden" name="deliveryChargeTypeArray" class="deliveryType"/>
		 			{INPUT_RADIO}
				</div>
		 	</td>	
		</tr> 
		<tr>	
			<td class="label">${op:message('M00654')}</td> <!-- 금액 -->			
			<td>
		 		<div>
		 			 배송비 : <input type="text" name="deliveryChargeArray" class="freeText required one _number" value="0" maxlength="6" readonly="readonly" title="${op:message('M00067')}"/> ${op:message('M00049')}  <!-- 원 -->
		 			 <span class="amountText" style="display: none;">
		 			 	/ <input type="text" name="deliveryFreeAmountArray" class="freeAmountText required one _number" value="0" maxlength="6" title="${op:message('M00067')}"/> ${op:message('M00641')} <!-- 원 이상 구매시 배송비 무료 -->
		 			 </span> 
				</div>
			</td>
		</tr> 
	</tbody>
</table>					 
			
<script type="text/javascript">
$(function() { 
	
	//초기 배송비 추가버튼 유무 지정
	if ($('#editDeliveryType').val() == 2) {
		$('.addDelivery').hide();
		$('.type2Hide').hide();
	}
	
	var urlPath =  document.location.pathname;
	
	if (urlPath == "/opmanager/delivery/create") {
		addDeliveryKind();
	}
	
	
	$('#delivery').validator(function() {
		
		$('#writeTable tbody').each(function(){
			if ($(this).find(':input[type="radio"]:checked').val() != null) {
				
				var deliveryType = $(this).find(':input[type="radio"]:checked').val();
				
				$(this).find('.deliveryType').val(deliveryType);	
			}
		});
	});
});
var addCount = 0;

function deliveryTypeCheck() {
	if ($(':radio[name="deliveryType"]:checked').val() == 1) {
		$('.addDelivery').show();
		$('.type2Hide').show();
	} else {
		$('.addDelivery').hide();
		$('.type2Hide').hide();
		$('#writeTable tbody').remove();
		addCount = 0;
		addDeliveryKind();
	}
}

//popupStyle
function ShowDiv(num, index) {
	
    if(num == "3") {
    	$('.freeText').eq(index).val("");
    	$('.freeText').eq(index).attr("readonly", false);
    	$('.freeAmountText').eq(index).val("");
    	$('.amountText').eq(index).show();
    } else if(num == "2") {
    	$('.freeText').eq(index).val("");
    	$('.freeText').eq(index).attr("readonly", false);
    	$('.freeAmountText').eq(index).val(0);
    	$('.amountText').eq(index).hide();
    } else {
    	$('.freeText').eq(index).val(0);
    	$('.freeText').eq(index).attr("readonly", true);
    	$('.freeAmountText').eq(index).val(0);
    	$('.amountText').eq(index).hide();
    }
    
}

function addDeliveryKind() {
	
	var addCharge = '<tbody id="' + addCount + '" style="border-top : 1px solid #d5d5d5;">';
	addCharge += $('#addWriteTable tbody').html();
	addCharge += '<tr><td colspan="2"><div class="btn_right">';
	//addCharge += '<a href="javascript:removeDeliveryKind(\'' + addCount + '\')" class="table_btn">삭제</a>';
	addCharge += '</div></td></tr></tbody>';
	
	var randomKey = Math.floor(Math.random() * 1000) + 1;
	var radioButtons = '';
	radioButtons += '<input type="radio" name="type' +  randomKey + '" id="type' + randomKey + '1" onclick="javascript:ShowDiv(1,' + addCount +')" value="1" checked="checked"> <label for="type' + randomKey + '1">' + Message.get("M00653") + '</label>';  // 무료(판매자 부담)   
	radioButtons += '<input type="radio" name="type' + randomKey + '" id="type' + randomKey + '2" onclick="javascript:ShowDiv(2,' + addCount +')" value="2"> <label for="type' + randomKey + '2">' + Message.get("M00639") + '</label>';	// 유료
	radioButtons += '<input type="radio" name="type' + randomKey + '" id="type' + randomKey + '3" onclick="javascript:ShowDiv(3,' + addCount +')" value="3"> <label for="type' + randomKey + '3">' + Message.get("M00640") + '</label>';	// 조건부무료
	
	$('#writeTable').append(addCharge.replace('{INPUT_RADIO}', radioButtons));
	setHeight();
	addCount++;
} 

function removeDeliveryKind(num) {
	$(document.getElementById(num)).remove();
} 

function deliveryEditPopup(chargeId) {
	Common.popup(url("/opmanager/delivery/charge/edit/" + chargeId), 'chargeEdit', 800, 350);
}

function deleteCheck(deliveryId, chargeId){
	if (confirm(Message.get("M00642"))) {	// 해당 배송비를 삭제하시겠습니까?
		location.replace("/opmanager/delivery/charge/delete/" + deliveryId + "/" + chargeId);  
	} else {
		return;
	}
}



</script>		