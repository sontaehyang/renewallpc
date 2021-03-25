<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ attribute name="name" required="false"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="cssClass" required="false"%>
<%@ attribute name="title" required="false"%>
<%@ attribute name="value" required="false"%>

<c:set var="titleAttribute"> title="휴대폰번호 앞자리"</c:set>
<c:set var="classAttribute"> class="mobile"</c:set>

<c:if test="${!empty title}">
<c:set var="titleAttribute"> title="${title}"</c:set>
</c:if>

<c:if test="${!empty cssClass}">
<c:set var="classAttribute"> class="mobile ${cssClass}"</c:set>
</c:if>


<select <c:if test="${!empty name}">name="${name}"</c:if> <c:if test="${!empty id}">id="${id}"</c:if> ${classAttribute} ${titleAttribute}>
	<option value="010" ${op:selected('010', value)}>010</option>
	<option value="011" ${op:selected('011', value)}>011</option>
	<option value="016" ${op:selected('016', value)}>016</option>
	<option value="017" ${op:selected('017', value)}>017</option>
	<option value="018" ${op:selected('018', value)}>018</option>
	<option value="019" ${op:selected('019', value)}>019</option>
</select> 