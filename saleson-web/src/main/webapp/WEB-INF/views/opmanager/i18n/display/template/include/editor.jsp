<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<div class="board_write sortable_display_editor">
	<h2>${template.title} / 롤링여부 (${template.rollingYN})</h2>

	<table class="board_write_table" summary="">
		<caption></caption>
		<colgroup>
			<col style="width: auto;" />
		</colgroup>
		<tbody>
			<tr>
				<td>
					<div>
						<c:choose>
							<c:when test="${displayEditorList.size() > 0}">
								<c:forEach items="${displayEditorList}" var="editor" varStatus="i">
									<c:if test="${templateIndex == editor.displaySubCode}">
										<%--<form:textarea path="displayEditorContent[${templateIndex}]" class="required requirement" cols="30" rows="3" title="${op:message('M01084')}"></form:textarea> --%>
										<textarea id="displayEditorContents${templateIndex}" name="displayEditorContents[${templateIndex}]" class="" cols="30" rows="3" title="">${editor.displayEditorContent}</textarea>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<textarea id="displayEditorContents${templateIndex}" name="displayEditorContents[${templateIndex}]" class="" cols="30" rows="3" title=""></textarea>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>