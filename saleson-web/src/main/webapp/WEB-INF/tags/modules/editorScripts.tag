<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<c:set var="images" value="/content/modules/editor/images" />
<script type="text/javascript" src="/content/modules/editor/scripts/messages/message_${pageContext.response.locale}.js"></script>
<script type="text/javascript" src="/content/modules/editor/scripts/editor.js"></script>
<link rel="stylesheet" type="text/css" href="/content/modules/editor/css/editor.css" />

<script type="text/javascript">
var _op_editor_images = '${images}';
var _OP_PATH = '/';
var _RandomId_ = '${requestContext.token}';
</script>
