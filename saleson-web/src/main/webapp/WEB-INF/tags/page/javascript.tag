<%@ tag pageEncoding="utf-8" body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>

<c:set var="JAVASCRIPT_BLOCK_IN_JSP_PAGE" scope="request">
${JAVASCRIPT_BLOCK_IN_JSP_PAGE}

<jsp:doBody />
</c:set>
