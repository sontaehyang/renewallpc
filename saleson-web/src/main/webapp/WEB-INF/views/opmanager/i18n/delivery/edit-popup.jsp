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

<div class="popup_wrap">
	
	<h1 class="popup_title">${op:message('M00709')}</h1> <!-- 배송비 수정 --> 
	<div class="popup_contents">
		<form:form modelAttribute="deliveryCharge" method="post" enctype="multipart/form-data" action="/opmanager/delivery/charge/edit-action">
			<table class="board_write_table" id="addWriteTable">
				<caption>${op:message('M00067')}</caption>
				<colgroup>
					<col style="width:200px;">
					<col style="width:*"> 
				</colgroup>		
				<tbody>
					<tr>
						<td class="label">${op:message('M00652')}</td> <!-- 종류 --> 
						<td>
					 		<div>
					 			<form:radiobutton path="deliveryChargeType" class="chargeType" value="1" label="${op:message('M00653')}"/> <!-- 무료(판매자 부담) --> 
					 			<form:radiobutton path="deliveryChargeType" class="chargeType" value="2" label="${op:message('M00639')}"/> <!-- 유료 --> 
					 			<form:radiobutton path="deliveryChargeType" class="chargeType" value="3" label="${op:message('M00640')}"/> <!-- 조건부무료 --> 
							</div>
					 	</td>	
					</tr> 
					<tr>	
						<td class="label">${op:message('M00654')}</td> <!-- 금액 -->			
						<td>
					 		<div>						 		
					 			<form:input type="text" path="deliveryCharge" class="freeText required one _number" title="${op:message('M00067')}"/> ${op:message('M00049')} <!-- 원 -->
				 			 	<span class="amountText">
				 			 		<form:input type="text" path="deliveryFreeAmount" class="freeAmountText required one _number" title="배송비"/> ${op:message('M00641')}) <!-- 원 이상 구매시 배송비 무료 -->
				 			 	</span>
							</div>
						</td>
					</tr> 
					<form:input type="hidden" path="deliveryChargeId" value="${deliveryCharge.deliveryChargeId}" />
					<form:input type="hidden" path="deliveryId" value="${deliveryCharge.deliveryId}" />
				</tbody>
			</table>
			<p class="btn_center">
				<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> &nbsp; <!-- 저장 --> 
				<a href="javascript:self.close();" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 -->
			</p>
		</form:form> 
	</div>
	<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
</div>

<script type="text/javascript">

$(function() {
	
	if ($('.chargeType:checked').val() != 3) {
		$('.amountText').hide();
	};
	
	
	$('.chargeType').on('change', function() {
		var num = $('.chargeType:checked').val();
		
		if(num == "3") {
	    	$('.freeText').val("");
	    	$('.freeAmountText').val("");
	    	$('.amountText').show();
	    } else if(num == "2") {
	    	$('.freeText').val("");
	    	$('.freeAmountText').val(0);
	    	$('.amountText').hide();
	    } else {           
	    	$('.freeText').val(0);
	    	$('.freeAmountText').val(0);
	    	$('.amountText').hide();
	    }
	});
});

</script>