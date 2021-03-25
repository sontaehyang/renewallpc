<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

			<h3><span>팝업관리</span></h3>
			 
			<form method="post"> 
			
			
			
			<hidden path="popup_Id"/>

			<div class="board_write">

				<textarea id="content" name="content" cols="" style="width:100%" rows="10" cssClass="" title="내용">안녕하세</textarea>

				<hr />
				<textarea id="content2" name="content2" cols="" style="width:100%" rows="10" cssClass="" title="내용">곤니찌와</textarea>
			</div>
		</form>

<script type="text/javascript">
 $(function(){
	// validator
	$('#popup').validator(function() {
		
	});
 });
 function fn_delete(){
	 if(confirm("조직도를 삭제하시겠습니까?")){
		 $("#deleteForm").submit();
	 }
 }
 </script>
 
<module:smarteditorInit />
<module:smarteditor id="content" />
<module:smarteditor id="content2" />