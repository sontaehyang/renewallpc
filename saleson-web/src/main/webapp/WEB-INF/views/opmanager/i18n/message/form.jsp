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
	
	<form:form modelAttribute="message" method="post" enctype="multipart/form-data">
		<h3><span>${op:message('MENU_1402')}</span></h3> <!-- 메세지 관리 -->
		<div class="board_write">
			<table class="board_write_table">
				<caption>${op:message('MENU_1402')}</caption> <!-- 메세지 관리 -->
				<colgroup>
					<col style="width:150px;">
					<col style="width:auto;">
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00081')}</td> <!-- 아이디 --> 
						<td>
							<div>
								<form:input type="text" path="id" title="${op:message('M00081')}" style="width=200px;" />
								<c:if test="${empty id}">
									- 문구는 ID 자동입력됩니다. 메뉴일경우만 입력해 주십시오. -   MENU_
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M01670')}</td> <!-- 한국어 --> 
						<td>
							<div>
								<form:input type="text" path="kMessage" title="${op:message('M00275')}" class="nine" />
							</div>
						</td>						  
					</tr>
					<tr>
						<td class="label">${op:message('M01671')}</td> <!-- 일본어 --> 
						<td>
							<div>
								<form:input type="text" path="jMessage" title="${op:message('M00275')}" class="nine" />
							</div>
						</td>						  
					</tr>
				</tbody>					 
			</table>								 							
		</div> <!-- // board_write -->
		
		<!-- 버튼시작 -->		 
		<div class="btn_center">
			<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 --> 			 
			<a href="/opmanager/message/list" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 --> 
		</div>			 
		<!-- 버튼 끝-->
	</form:form>
	
<script type="text/javascript">

	// max id + 1 값
	function fnMakeMaxid(maxid){
		var newid;
		newid = '00000' + maxid;
		newid = 'M' + newid.substring(newid.length-5,newid.length)
		
		$("#id").val(newid);
	}

$(function() { 
	
	// validator
	try{
		$('#message').validator(function() {
			
			var kMsg = $("#kMessage").val();
			var jMsg = $("#jMessage").val();
			  
			if (kMsg.length==0) {
				alert("한국어는 필수 입력항목입니다.");
				$("#kMessage").focus();
				return false;
			} 
			if (jMsg.length==0) {
				$("#jMessage").val(kMsg);
			} 
		});
	} catch(e) {
		alert(e.message);
	}
	
});

</script>