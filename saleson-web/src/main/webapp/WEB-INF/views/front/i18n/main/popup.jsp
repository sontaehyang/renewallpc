<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>


<script type="text/javascript">
function setCookie(popupId) {
	var expire = new Date();
	
	expire.setDate(expire.getDate() + 1 );
	document.cookie = "popup_check_"+popupId+"=1; expires=" + expire.toGMTString()+ "; path=/";
	
	popClose();
}

function popLink(url)
{
	opener.document.location.href = '${popup.popupImageSrc}';
	self.close();
}

/**
 * 팝업닫기 버튼 클릭시
 * @return
 */
function popClose(){
	self.close();
}

</script>
<body style="overflow-x:hidden;overflow-y:hidden">
<div class="popup_wrap">
	<h1 class="popup_title">${popup.subject}</h1>

	<div id="popup_contents">
		<c:choose>
			<c:when test="${popup.popupImage != null}">
				<c:if test="${popup.imageLink != ''}">
					<a href="${popup.imageLink}" target="_blank">
				</c:if>
				<img src="${popup.popupImageSrc}" border="0" />
				<c:if test="${popup.imageLink != ''}">
					</a>
				</c:if>
			</c:when>
			<c:otherwise>
				${popup.content}
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="popup_bottom">
	    <fieldset>
	        <legend>${op:message('M01475')}<!-- 오늘하루 이창을 열지 않음 --></legend>
	        <div class="fieldset_area">
	            <input type="hidden" name="num" value="1" />
	            <input type="checkbox" id="close" onclick="setCookie('${popup.popupId}')" onkeypress="setCookie('${popup.popupId}')" style="margin-bottom:1px" /><label for="close">${op:message('M01475')}<!-- 오늘하루 이창을 열지 않음 --></label> &nbsp;
	            <a href="javascript:popClose();"><img src="/content/images/btn/btn_close2.gif" style="cursor:hand" /></a>
	        </div>
	    </fieldset>
	</div>
</div>
</body>
