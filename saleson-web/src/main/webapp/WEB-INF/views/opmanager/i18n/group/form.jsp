<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<h3><span>${op:message('M00267')}</span></h3>
	
	<form:form modelAttribute="Group" id="Group"  action="${ requestContext.opmanagerUri }/group/${formAction}" method="post">
		
		<div class="board_write">
			<table class="board_write_table">
				<colgroup>
					<col style="width:150px;" />
					<col style="" />		
				</colgroup>
				<thead>
					<tr>
						<td class="label">그룹코드</th>
						 <td>
						 	<div>
								<c:choose>
									<c:when test="${empty Group.groupCode}">
										
										<form:input path="groupCode" class="required form-half" title="그룹코드" maxlength="10" />
								
									</c:when>
									<c:otherwise>
										${Group.groupCode}<form:hidden path="groupCode" />
									</c:otherwise>
								</c:choose>
					 		</div>
						 </td>
					</tr>
					<tr>
						<td class="label">그룹명</th>
						 <td>
						 	<div>
								<form:input path="groupName" class="required form-half" title="그룹명" />
						 	</div>
						 </td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="label">그룹 설명</th>
						 <td>
						 	<div>
						 		<form:input path="groupExp" class="required form-block" title="그룹 설명" /> 
						 	</div>
						 </td>
					</tr>
				</tbody> 
			</table>				 
		</div><!--//board_write E-->	
		
		<div class="tex_c mt20">
			<button type="submit" class="btn btn-active">${op:message('M00101')}</button>
			<a href="/opmanager/group/list" class="btn btn-default">${op:message('M00037')}</a>
		</div>
	</form:form>



<script type="text/javascript">
	$(function(){
		$('#Group').validator(function(){
			
		});
	});

</script>
