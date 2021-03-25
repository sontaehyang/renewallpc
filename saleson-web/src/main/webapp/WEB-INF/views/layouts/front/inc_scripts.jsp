<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="seo" 	tagdir="/WEB-INF/tags/seo" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">var OP_CONTEXT_PATH = '${requestContext.contextPath}';</script>
<script type="text/javascript">var OP_LANGUAGE = '${requestContext.locale.language}';</script>
<script type="text/javascript">var OP_HTTP = "${op:property('saleson.url.shoppingmall')}";</script>
<script type="text/javascript">var OP_HTTPS = "${op:property('saleson.url.shoppingmall')}";</script>
<script type="text/javascript">var OP_USER_TIMEOUT = '${shopContext.userTimeout}';</script>
<script type="text/javascript" src="<c:url value="/content/modules/jquery/jquery-ui-1.10.4.custom.js" />"></script>
<script type="text/javascript" src="/content/modules/jquery/jquery.cookie.js"></script>

<script type="text/javascript" src="<c:url value="/content/modules/op.validator.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.file.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.shop.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.eventlog.js"/>"></script>


<c:if test="${shopContext.config.sourceFlag == 'N'}">
<script type="text/javascript">
	$(function() {
		Common.diableContextMenu();
        <sec:authorize access="hasRole('ROLE_USER')">
        Shop.procSessionTimeout();
        </sec:authorize>
        // Shop.quickMenuEvent();
	});
</script>  
</c:if>
<%-- JSP 페이지 내 선언된 javascript 출력 (<page:javascript></page:javascript>)  --%>
${JAVASCRIPT_BLOCK_IN_JSP_PAGE}
