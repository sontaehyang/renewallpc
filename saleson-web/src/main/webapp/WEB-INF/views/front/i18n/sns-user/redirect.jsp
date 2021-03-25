<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<script type="text/javascript">
	$(function(){
		var isLogin = '${requestContext.userLogin}';
		if (isLogin) {
			<c:if test='${opener == "view"}'>
				opener.buyNow(false);
			</c:if>
			<c:if test='${opener == "cart"}'>
				opener.PopupLogin.Callback();
			</c:if>
		}
		else {
			alert("다시 로그인 해주세요.");
		}
		self.close();		
	});
</script>