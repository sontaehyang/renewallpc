<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ attribute name="column" required="true"%>
<%@ attribute name="title" required="true"%>

<a href="javascript:fn_Sort('${column}')" id="sort-${column}" class="sort-link">${title}</a>