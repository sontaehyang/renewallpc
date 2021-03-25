<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<style>
input {width: 100px}
</style>

<form:form modelAttribute="complex" method="post" enctype="multipart/form-data">
	- ID : <input type="text" name="itemId[]" value="1" />, Name : <input type="text" name="itemName[]" value="상품명1" /> <br />
	- ID : <input type="text" name="itemId[]" value="1" />, Name : <input type="text" name="itemName[]" value="상품명1" /> <br />
	<button type="submit">등록</button>
</form:form>