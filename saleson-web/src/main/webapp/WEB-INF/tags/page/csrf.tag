<%@ tag pageEncoding="utf-8" body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />