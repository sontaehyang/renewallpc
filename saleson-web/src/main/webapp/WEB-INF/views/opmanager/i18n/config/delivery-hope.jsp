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
		
		<h3><span>${op:message('M00717')}</span></h3> <!-- 배송 희망일 설정 --> 
			<form:form modelAttribute="config" method="post" enctype="multipart/form-data">
				<div class="board_write">
					<table class="board_write_table" summary="${op:message('M00717')}">
						<caption>${op:message('M00717')}</caption>
						<colgroup>
							<col style="width:150px;">
							<col style="width:*">
						</colgroup>
						<tbody>
							 <tr>
							 	<td class="label">${op:message('M00717')}</td>
							 	<td>
							 		<div>
							 			<span>
											<form:radiobutton path="deliveryHopeFlag" value="Y" label="${op:message('M00718')}"/> <!-- 사용함(주문 날짜의 --> 
											<form:input type="text" path="deliveryHopeStartDate" class="one" title="${op:message('M00719')}" maxlength="2"/>${op:message('M00721')} <!-- 일 이후부터 -->  <!-- 사용함 -->
											<form:input type="text" path="deliveryHopeEndDate" class="one" title="${op:message('M00720')}" maxlength="2"/>${op:message('M00722')} <!-- 일 이내 지정) -->  <!-- 일수 --> 
											<br> 
									  		<form:radiobutton path="deliveryHopeFlag" value="N" label="${op:message('M00178')}"/> <!-- 사용하지 않음 --> 
								  		</span>								  		 
									</div> 
							 	</td>	 
							 </tr> 
						</tbody>					 
					</table>
				</div> <!--//board_write E-->
				<div class="btn_center">
					<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 -->  
				</div>
			</form:form>
			
			<h3><span>${op:message('M00723')}</span></h3> <!-- 배송 시간대 설정 --> 
			<div class="board_write">
				<form id="insertForm" action="/opmanager/config/delivery-hope/insert" method="post">
					<table class="board_write_table" summary="${op:message('M00723')}">
						<caption>${op:message('M00723')}</caption>
						<colgroup>
							<col style="width:150px;">
							<col style="width:*">
						</colgroup>
						<tbody>
							 <tr>
							 	<td class="label">${op:message('M00724')}</td> <!-- 배송 시간대 --> 
							 	<td>
							 		<div>
							 			<input type="text" name="deliveryHopeTime" id="addTimeText" class="required half" title="${op:message('M00724')}">&nbsp<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00192')}</span></button> <!-- 추가 --> 
									</div>
							 	</td>	
							 </tr> 
						</tbody>					 
					</table>			
				</form>					 							
			</div> <!--//board_write E-->

			<form id="updateForm" action="/opmanager/config/delivery-hope/update" method="post">
				<div class="board_write mt20">
					<table class="board_list_table">
						<colgroup>
							<col style="width:*">
							<col style="width:200px;">
	
						</colgroup>
						<thead>
							<tr>
								<th scope="col">${op:message('M00724')}</th>
								<th scope="col">${op:message('M00192')}/${op:message('M00074')}</th>
							</tr>
						</thead>
						<tbody id="writeBody">
							<input type="hidden" name="deliveryHopeTime" id="deliveryHopeTime"/>
							<input type="hidden" name="deliveryHopeId" id="deliveryHopeId"/>
							<c:forEach items="${deliveryHopeList}" var="list">
								<tr>
									<td class="tleft">
										<input type="text" value="${list.deliveryHopeTime}" class="hopeTime half"/>
										<input type="hidden" class="hopeId" value="${list.deliveryHopeId}"/>
									</td>
									<td>
										<div>
											<a href="#" class="updateSubmit table_btn">${op:message('M00087')}</a> <!-- 수정 --> 
											<a href="javascript:deleteCheck('${list.deliveryHopeId}')" class="table_btn">${op:message('M00074')}</a> <!-- 삭제 --> 
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>  
					</table>
				</div>
			</form>
			
			
<script type="text/javascript">

$(function() {
	
	$('#deliveryHopeFlag2').on('click', function() {
		$('#deliveryHopeStartDate').val("");
		$('#deliveryHopeEndDate').val("");
	});
	
	
	$('.updateSubmit').on('click', function() {
		$('#deliveryHopeTime').val($(this).parent().parent().parent().find('.hopeTime').val());
		$('#deliveryHopeId').val($(this).parent().parent().parent().find('.hopeId').val());
		
		$('#updateForm').submit();
	});
	
	
	$('#insertForm').validator(function() {
		
		if ($('#addTimeText').val() == "") {
			alert(Message.get("M00725"));	// 배송시간대를 입력해주세요.
			$('#addTimeText').focus();
			return false;
		}
	});
});


function deleteCheck(hopeId) {
	if (confirm(Message.get("M00726"))) {	// 해당 배송희망 시간을 삭제하시겠습니까? 
		location.replace("/opmanager/config/delivery-hope/delete/" + hopeId);  
	} else {
		return;
	}
}

</script>

