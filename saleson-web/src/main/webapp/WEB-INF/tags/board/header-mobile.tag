<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>


<script type="text/javascript" src="<c:url value="/content/modules/op.board.js" />"></script>
<script type="text/javascript">
Board.init('${boardContext.boardBaseUri}');
</script>

<c:if test="${boardContext.boardCfg.useSocial == '1'}">
<script type="text/javascript">
Common.dynamic.script(url("/content/modules/op.social.js"));
</script>
</c:if>


	<c:choose>
		<c:when test="${boardContext.boardCode == 'notice'}">
			<script type="text/javascript">Menu.set(8);</script>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'press'}">
			<script type="text/javascript">Menu.set(1);</script>
		</c:when>
	
	</c:choose>

