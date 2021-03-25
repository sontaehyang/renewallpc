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
	
	<form:form modelAttribute="faq" method="post" enctype="multipart/form-data">
		<h3><span>${op:message('M00674')}</span></h3> <!-- 자주 묻는 질문관리 -->
		<div class="board_write">
			<table class="board_write_table">
				<caption>${op:message('M00675')}</caption> <!-- 자주 묻는 질문 -->
				<colgroup>
					<col style="width:150px;">
					<col style="width:auto;">
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00676')}</td> <!-- 질문유형 --> 
						<td>
							<div>
								<form:select path="faqType" title="${op:message('M00677')}"> <!-- 질문유형 선택 -->
									<%-- <form:option value="0">${op:message('M00039')}</form:option> --%> <!-- 전체 -->
									<c:forEach var="list" items="${faqTypes}">
										<form:option value="${list.code}">${list.title }</form:option>
									</c:forEach>
								</form:select>
						    </div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00275')}</td> <!-- 제목 --> 
						<td>
							<div>
								<form:input type="text" path="title" title="${op:message('M00275')}" class="form-block required" />
							</div>
						</td>						  
					</tr>
					<tr>
						<td class="label">${op:message('M00006')}</td> <!-- 내용 --> 
						<td>
							<div>
								 <form:textarea path="content" cols="30" rows="6" class="w90" title="${op:message('M00661')}" />
							</div>
						</td>						 
					</tr>
				</tbody>					 
			</table>								 							
		</div> <!-- // board_write -->
		
		<!-- 버튼시작 -->		 
		<div class="btn_center">
			<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 --> 			 
			<a href="javascript:Link.list('${requestContext.managerUri}/faq/list')" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 -->
		</div>			 
		<!-- 버튼 끝-->
	</form:form>
	
<module:smarteditorInit />
<module:smarteditor id="content" />

<script type="text/javascript">

$(function() { 
	
	// validator
	try{
		$('#faq').validator(function() {
			
			Common.getEditorContent("content");
		});
	} catch(e) {
		alert(e.message);
	}
});

</script>