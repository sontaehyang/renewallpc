<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<c:if test="${shopContext.naverPay == true}">
	<script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script>
	<script type="text/javascript">
		try {
			if(!wcs_add) var wcs_add = {};
			wcs_add["wa"] = "${shopContext.naverWcslogKey}";
			
			// 체크아웃 whitelist가 있을 경우
			wcs.checkoutWhitelist = []; 
			// 유입 추적 함수 호출
			wcs.inflow();
		} catch (e) {}
	</script>
</c:if>