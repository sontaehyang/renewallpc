<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

Form
<form id="form1" action="/demo/create" method="post">
<legend>입력 서식 </legend>
<fieldset>

	<input type="text" name="title" value="11"  class="required" title="222"/>
	<input type="text" name="email" value="" />
	<textarea name="content"></textarea>
	
	<br />
	사진등록 : 사진 : <a href="javascript:File.open('demo', '${requestContext.token}', 0, 3, 3)" class="btn">이미지업로드</a><br />
	<ul id="temp_image_files"  class="files"></ul>
	
	
	<input type="text" name="tempFileIds" value="1" />

	<input type="text" name="tempFileDescriptions" value="" />

	
	<input type="submit" value="등록" />

</fieldset>
</form>


<form id="form2" action="/demo/create" method="post">
<legend>입력 서식 </legend>
<fieldset>

	<input type="text" name="title" value="11" class="required" title="xx" />
		
	<input type="submit" value="등록" />

</fieldset>
</form>
<<page:javascript>
<script>
$(function() {
	$('#form1').validator({
	
	submitHandler : function() {
		alert('xx');
		return false;
	}});
	
	
	$('#form2').validator({
	
	submitHandler : function() {
		alert('yyyy');
		return false;
	}});
});

</script>
</page:javascript>