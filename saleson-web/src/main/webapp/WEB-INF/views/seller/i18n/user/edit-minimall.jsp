<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<style>
.phone_number {width: 45px;}

</style>
			<h3>미니몰 정보수정</h3>
			<p class="text text-info">
				* 미니몰 상단 컨텐츠를 관리합니다.
			</p>
			<form:form modelAttribute="seller" method="post">
				<input type="hidden" name="sellerId" value="${seller.sellerId}" />
				<div>
					<form:textarea path="headerContent" cols="30" rows="20" style="width: 1085px" class="" title="미니몰 상단 내용" />
				</div>
				<div class="buttons">
					<button type="submit" class="btn btn-active">${op:message('M00087')} <!-- 저장 --></button>	
				</div>
			</form:form>
			
<module:smarteditorInit />
<module:smarteditor id="headerContent" />		

<page:javascript>		
<script type="text/javascript">
$(function(){
	// 검증.
	$('#seller').validator(function() {
		Common.getEditorContent("headerContent");
	});
});

</script>	
</page:javascript>		
			
