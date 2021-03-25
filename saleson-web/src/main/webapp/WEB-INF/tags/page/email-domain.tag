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

<c:set var="titleAttribute"> title="이메일 뒷자리"</c:set>
<c:set var="classAttribute"></c:set>

<c:if test="${!empty title}">
<c:set var="titleAttribute"> title="${title}"</c:set>
</c:if>

<c:if test="${!empty cssClass}">
<c:set var="classAttribute"> class="${cssClass}"</c:set>
</c:if>
			
			
<select <c:if test="${!empty name}">name="${name}"</c:if> <c:if test="${!empty id}">id="${id}"</c:if> ${classAttribute} ${titleAttribute}>
	<option>직접입력</option>
	<option value="naver.com" ${op:selected('naver.com', value)}>naver.com</option>
	<option value="daum.net" ${op:selected('daum.net', value)}>daum.net</option>
	<option value="nate.com" ${op:selected('nate.com', value)}>nate.com</option>
	<option value="gmail.com" ${op:selected('gmail.com', value)}>gmail.com</option>
	<option value="hotmail.com" ${op:selected('hotmail.com', value)}>hotmail.com</option>
	<option value="yahoo.co.kr" ${op:selected('yahoo.co.kr', value)}>yahoo.co.kr</option>
	<option value="paran.com" ${op:selected('paran.com', value)}>paran.com</option>
	<option value="empas.com" ${op:selected('empas.com', value)}>empas.com</option>
	<option value="dreamwiz.com" ${op:selected('dreamwiz.com', value)}>dreamwiz.com</option>
	<option value="freechal.com" ${op:selected('freechal.com', value)}>freechal.com</option>
	<option value="lycos.co.kr" ${op:selected('lycos.co.kr', value)}>lycos.co.kr</option>
	<option value="korea.com" ${op:selected('korea.com', value)}>korea.com</option>
	<option value="hanmir.com" ${op:selected('hanmir.com', value)}>hanmir.com</option>
</select> 