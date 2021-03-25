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
<%--
<script type="text/javascript" src="<c:url value="/content/mobile/old/scripts/jquery/idangerous.swiper-2.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/jquery/jquery-ui-1.10.4.custom.js" />"></script>
 --%>
 
<script type="text/javascript" src="<c:url value="/content/modules/op.file.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.link.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.common.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.shop.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.validator.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.mobile.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.social.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/jquery/jquery.cookie.js" />"></script>
<script type="text/javascript" src="<c:url value="/content/modules/op.eventlog.js"/>"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
 
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<sec:authorize access="hasRole('ROLE_USER')">
 <script type="text/javascript">
	 Shop.procSessionTimeout();
 </script>
</sec:authorize>
<%-- JSP 페이지 내 선언된 javascript 출력 (<page:javascript></page:javascript>)  --%>
${JAVASCRIPT_BLOCK_IN_JSP_PAGE}
