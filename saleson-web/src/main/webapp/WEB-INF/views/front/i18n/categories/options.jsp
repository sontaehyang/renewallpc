<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

<c:forEach items="${list}" var="categories">
	<c:set var="selected"></c:set>
	<c:if test="${fn:substring(categories.categoryCode, 0, 3 * categories.categoryLevel) == categoriesSearchParam.categoryClass1}">
		<c:set var="selected"> selected="selected"</c:set>
	</c:if>
	
	<option value="${fn:substring(categories.categoryCode, 0, 3 * categories.categoryLevel)}" ${selected} rel="${categories.categoryId}">${categories.categoryName}</option>
</c:forEach>

