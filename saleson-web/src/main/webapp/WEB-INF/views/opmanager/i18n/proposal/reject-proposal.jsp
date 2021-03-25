<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<div class="popup_wrap">
	<h1 class="popup_title">반송사유</h1>
	<div class="popup_contents">
	
		<form:form modelAttribute="proposal" method="post">
			<form:hidden path="proposalId" />
			<form:hidden path="statusCode" />
			
			<textarea name="approvalRemark" cols="30" rows="4" maxlength="140" id="reject_content" class="reject required" title="반송사유"></textarea>
			<p class="popup_btns">
				<button type="submit" class="btn orange"><span>확인</span></button>
			</p>
		</form:form>
	</div>
	<a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
$(function() {
	$('#proposal').validator();
});

</script>

