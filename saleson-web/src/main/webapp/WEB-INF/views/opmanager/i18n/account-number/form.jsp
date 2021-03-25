<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>${op:message('M00171')} </span></h3>			
		<div class="board_write">
			<form:form modelAttribute="accountNumber" name="accountNumber" method="post">
				<form:hidden path="shopConfigId"/>
				<table class="board_write_table" summary="${op:message('M00171')} ">
					<caption>${op:message('M00171')} </caption>
					<colgroup>
						<col style="width:150px;" />
						<col style="*" />
					</colgroup>
					<tbody>
						 <tr>
						 	<td class="label">${op:message('M00082')}</td>
						 	<td>
						 		<div>
						 			<form:radiobutton path="useFlag" value="Y" label="${op:message('M00083')}" />
						 			<form:radiobutton path="useFlag" value="N" label="${op:message('M00089')}" />
								</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00165')}</td>
						 	<td>
						 		<div>
						 			<form:input path="bankName" title="${op:message('M00165')}" cssClass="form-half required" maxlength="25" />
								</div> 
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00166')}</td>
						 	<td>
						 		<div>
						 			<form:input path="accountNumber" title="${op:message('M00166')}" cssClass="form-half _number required" maxlength="30" />
								</div> 
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">${op:message('M00167')}</td>
						 	<td>
						 		<div>
						 			<form:input path="accountHolder" title="${op:message('M00167')}" cssClass="form-half required" maxlength="25"/>
								</div> 
						 	</td>	
						 </tr> 
					</tbody>					 
				</table>	
				
				<!-- 버튼시작 -->		 
				<div class="tex_c pt25">				 
					<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button>	
					<a href="<c:url  value="/opmanager/account-number/list" />" class="btn btn-default">${op:message('M00037')}</a>			 
				</div>			 
				<!-- 버튼 끝-->		
			</form:form>					 							
		</div>
		
<script type="text/javascript">
$(function(){

	$('#accountNumber').validator(function() {
		
		Common.confirm("${op:message('M00159')}", function(form) {
			$('#accountNumber').submit();
		});
		return false;
	});
});
</script>		