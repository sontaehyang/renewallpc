<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

<option value=""> = 그룹 = </option>
<c:forEach items="${list}" var="group">
	<c:set var="selected"></c:set>
	<c:if test="${group.categoryGroupId == categoriesTeamGroupSearchParam.categoryGroupId}">
		<c:set var="selected"> selected="selected"</c:set>
	</c:if>
	<option value="${group.categoryGroupId}" ${selected} >${group.name}</option>
</c:forEach>

