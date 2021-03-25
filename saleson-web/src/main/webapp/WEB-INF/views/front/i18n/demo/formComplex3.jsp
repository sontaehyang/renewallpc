<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<style>
#surveyContent li {padding-top: 15px;}
</style>

<form:form modelAttribute="survey" action="/demo/formComplex3" method="post" enctype="multipart/form-data">
	
	- 제목 : <form:input path="subject"/> <br />
	- 설문타입 : <form:input path="surveyType"/> <br />
	
	<br /><br />


	<h2>설문항목 : 	&nbsp;<a id="add">+ 추가</a></h2>
	<ul id="surveyContent">
		<c:forEach items="${survey.surveyContentList}" var="surveyContent" varStatus="i">
		

		<li>
			- 설문내용 : <form:input path="surveyContentList[${i.index}].content" /> <br />
			
			<ul style="border:1px dashed #ccc; padding: 10px">
				<c:forEach items="${surveyContent.surveyItemList}" var="surveyItem" varStatus="j">
					${j.count}. <form:input path="surveyContentList[${i.index}].surveyItemList[${j.index}].itemContent"/> <br />
				</c:forEach>
			</ul>
		
		</li>
		</c:forEach>
		
		
	</ul>
	
	


	
	<input type="submit" value="전송" style="padding: 10px 20px; margin-top: 10px;"/>
</form:form>


<script>
$(function() {
	$('#add').on("click", function() {
		var $surveyContent = $('#surveyContent');
		
		var index = $('#surveyContent li').size();
		
		var appendHtml = '';
		
		appendHtml += '<li>';
		appendHtml += '	- 설문내용 : <input type="text" name="surveyContentList[' + index + '].content" /> <br />';
		
		appendHtml += '		<ul style="border:1px dashed #ccc; padding: 10px">';
		appendHtml += '			1. <input type="text" name="surveyContentList[' + index + '].surveyItemList[0].itemContent" value=""/> <br />';
		appendHtml += '			2. <input type="text" name="surveyContentList[' + index + '].surveyItemList[1].itemContent" value=""/> <br />';
		appendHtml += '			3. <input type="text" name="surveyContentList[' + index + '].surveyItemList[2].itemContent" value=""/> <br />';
		appendHtml += '			4. <input type="text" name="surveyContentList[' + index + '].surveyItemList[3].itemContent" value=""/> <br />';
		appendHtml += '		</ul>';
		appendHtml += '</li>';

		
		$surveyContent.append(appendHtml);
	});
	
});

</script>
</form>