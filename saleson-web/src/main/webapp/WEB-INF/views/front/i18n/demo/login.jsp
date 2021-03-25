<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<form action="/op_security_login" method="post">
<legend>입력 서식 </legend>
<fieldset>

	<input type="text" name="op_username" value="${loginId}" />
	<input type="text" name="op_password" value="${password}" />
	<input type="text" name="op_login_type" value="ROLE_USER" />
	<input type="text" name="op_signature" value="${signature}" />
	<input type="text" name="target" value="/" />
	
	<input type="submit" value="로그" />

</fieldset>
</form>