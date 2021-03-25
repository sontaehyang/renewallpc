<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


	<div class="practice donation">
		<form:form modelAttribute="proposal" id="proposal"  action="${ requestContext.opmanagerUri }/proposal/create" method="post" enctype="multipart/form-data">
			<form:input path="userId" value="${ requestContext.user.userId }" />
			<table>
				<tr>
					<td>제목</td> 
					<td> <form:input path="title" title="제목" cssClass="required _filter" /> </td>
				</tr>
				<tr>
					<td>신청기관</td>
					<td> <form:input path="userName" title="신청기관" cssClass="required _filter"  value="${ requestContext.user.userName }" /> </td>
				</tr>
				
				<tr>
					<td>모금기간</td>
					<td> <form:input path="startDate" title="시작일" cssClass="required _filter"  /> ~  <form:input path="endDate" title="종료일" cssClass="required _filter"  />  </td>
				</tr>
				<tr>
					<td>목표금액</td>
					<td> <form:input path="targetAmount" title="목표금액" cssClass="required _filter" value="${ requestContext.user.userName }" /> </td>
				</tr>
				<tr>
					<th scope="row">대표이미지</th>
					<td>
						<input type="file" name="uploadfile[]" class="w448 file required :file hide" id="FILEREPRESENTATIVE" allowExt="jpg,bmp,gif" title="사진" />
					</td>
				</tr>
				<tr>
					<th scope="row">내용이미지</th>
					<td>
						<input type="file" name="uploadfile[]" class="w448 file required :file hide" id="FILECONTENT" allowExt="jpg,bmp,gif" title="사진" />
					</td>
				</tr>
				<tr>
					<td> 내용 텍스트 </td>
					<td> <form:textarea path="content" /> </td>
				</tr>
				<tr>
					<td> <button type="submit"><img src="/content/images/common/btn_confirm.png" alt="" /> 등록</button> </td>
				</tr>
			</table>
		</form:form>
	</div>


<script type="text/javascript">
	$(function(){
		$('#proposal').validator(function() {
			return false;
		});
	});
</script>

