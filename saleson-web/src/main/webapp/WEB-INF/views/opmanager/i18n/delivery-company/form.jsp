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
		
			<h3><span>${op:message('M00670')}</span></h3> <!-- 배송업체 등록/수정 -->
			<form:form modelAttribute="deliveryCompany" method="post" enctype="multipart/form-data">
				<input type="hidden" id="kind" value="${kind}" />
				<form:hidden path="sendFlag"/>
				<div class="board_write">
					<table class="board_write_table">
						<caption>${op:message('M00055')}</caption> <!-- 전체주문 내역 -->
						<colgroup>
							<col style="width:150px;" />
							<col style="width:auto;" />
						</colgroup>
						<tbody>
							 <tr>
							 	<td class="label">${op:message('M00665')}</td> <!-- 배송 업체명 --> 
							 	<td>
							 		<div>
										<form:input type="text" path="deliveryCompanyName" class="required full" title="${op:message('M00665')}" />
									</div>
							 	</td>	
							 </tr>
							 <tr>
							 	<td class="label">${op:message('M00666')}</td> <!-- 대표연락처 --> 
							 	<td>
							 		<div>
										<form:input type="text" path="telNumber" class="full" title="${op:message('M00666')}" /> 
									</div>
							 	</td>	
							 </tr>
							 <tr>
							 	<td class="label">${op:message('M00671')}</td> <!-- 배송 조회 URL --> 
							 	<td>
							 		<div>
										<form:input type="text" path="deliveryCompanyUrl" class="required full" title="${op:message('M00671')}" /> 
									</div>
							 	</td>	
							 </tr>	
							<%-- <tr>
							 	<td class="label">${op:message('M00672')}</td> <!-- 전송방법 --> 
							 	<td>
							 		<div>
										<form:radiobutton path="sendFlag" value="1" label="GET" checked="checked"/>
										<form:radiobutton path="sendFlag" value="2" label="POST" />							 
									</div>
							 	</td>	
							 </tr>
							 <tr>
							 	<td class="label">${op:message('M00673')}</td> <!-- 송장번호 파라미터 -->
							 	<td>
							 		<div>
										<form:input type="text" path="deliveryNumberParameter" class="full" title="${op:message('M00673')}" /> 
									</div>
							 	</td>	
							 </tr> --%>
							 <tr>
							 	<td class="label">${op:message('M00669')}</td> <!-- 사용유무 --> 
							 	<td>
							 		<div>
										<form:radiobutton path="useFlag" value="Y" label="${op:message('M00083')}" checked="checked"/> <!-- 사용 --> 
										<form:radiobutton path="useFlag" value="N" label="${op:message('M00089')}" /> <!-- 사용안함 -->				 
									</div>
							 	</td>	
							 </tr>					 
						</tbody>					 
					</table>								 							
				</div> <!-- // board_write -->
				
				<p class="btn_center">
					<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 --> 
					<a href="/opmanager/delivery-company/list" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 -->
				</p>
			</form:form>
			
			 
			
<script type="text/javascript">
$(function() { 
	
	$('#deliveryCompany').validator(function() {
		if (confirm("배송업체 정보를 " + $('#kind').val() + "하시겠습니까?")) {
			
		} else {
			return false;
		}
	});
	
});

</script>