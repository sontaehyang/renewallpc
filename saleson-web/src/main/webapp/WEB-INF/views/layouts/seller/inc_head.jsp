<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
	<meta charset="UTF-8" />
	<title>${shopContext.config.shopName } 판매관리자</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
	<link rel="icon" href="/content/images/common/favicon.ico" />
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/content/opmanager/css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/content/opmanager/css/bootstrap/css/bootstrap.css" />">
	<link rel="stylesheet" type="text/css" href="<c:url value="/content/opmanager/css/opmanager.css" />">
	<link rel="stylesheet" type="text/css" href="<c:url value="/content/opmanager/css/opmanager_print.css" />" media="print" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/content/modules/jquery/jstree/dist/themes/default/style.min.css" />" />
	
	<script type="text/javascript">var OP_CONTEXT_PATH = '${requestContext.contextPath}';</script>
	<script type="text/javascript">var OP_LANGUAGE = '${requestContext.locale.language}';</script>
	<script type="text/javascript">var OP_MANAGER_TIMEOUT = '${shopContext.managerTimeout}';</script>
	<script type="text/javascript">var OP_MANAGER_TIMEOUT_TYPE = 'seller';</script>
	<script type="text/javascript" src="<c:url value="/content/modules/jquery/jquery-1.11.0.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/jquery/jquery.cookie.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/jquery/jquery-ui-1.10.4.custom.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/spin.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/op.common.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/op.validator.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/op.file.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/op.shop.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/op.manager.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/op.manager.order.js" />"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/css_browser_selector.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/content/modules/jquery/jstree/dist/jstree.js" />"></script>