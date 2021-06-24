<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="seo" 	tagdir="/WEB-INF/tags/seo" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>

<meta charset="UTF-8" />
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<jsp:include page="../common/open-graph-tag.jsp"/>

<link rel="shortcut icon" href="/content/images/common/renewallpc_favicon.ico" />
<link rel="icon" href="/content/images/common/favicon.ico" />
<link rel="apple-touch-icon" href="/content/images/common/favicon_ios.png" />

<c:choose>
	<c:when test="${requestContext.requestUri == '/m/item/details-image-view' || fn:indexOf(requestContext.requestUri, '/m/products/view') != -1}">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
	</c:when>
	<c:otherwise>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
	</c:otherwise>
</c:choose>

<meta name="format-detection" content="telephone=no">

<!-- 20210315 21:44  son 수정 / ST -->
<!--네이버소유권-->
<meta name="naver-site-verification" content="dbf36f0c218e82445272e072961b4eb467b343f3" />
<!-- 20210315 21:44  son 수정 / END -->


<!--네이버 연관채널 메타태그-->
<script type="application/ld+json">
	{
		"@context": "http://schema.org",
		"@type": "Person",
		"name": "리뉴올PC",
		"url": "https://renewallpc.co.kr",
		"sameAs": [
			"https://blog.naver.com/kaceroo4",
			"https://www.instagram.com/renewallpc",
			"https://www.facebook.com/pcnori.renewallpc",
			"https://www.youtube.com/channel/UCsRut477jiZUqkAOClFW3nA/featured",
			"https://pf.kakao.com/_cFxowT"
		]
	}
</script>


<!-- 구글 전환추적 로그분석 Global site tag (gtag.js) - Google Ads: 754482062 -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-137825376-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-137825376-1');
</script>


<title><seo:pagination-title />${shopContext.seo.title}</title>

<c:set var="noFollowFlag" value="N" />
<c:if test="${!empty pagination && op:replaceAll(pageContext.request.queryString, 'page=[0-9]+', '') != ''}">
	<c:set var="noFollowFlag" value="Y" />
</c:if>

<%-- <meta name="robots" content=""/>--%>
<%--<c:if test="${shopContext.seo.indexFlag == 'N' || noFollowFlag == 'Y'}"><meta name="robots" content="noindex,noarchive"/></c:if>--%>
<c:if test="${!empty shopContext.seo.keywords}"><meta name="keywords" content="${shopContext.seo.keywords}" /></c:if>
<c:if test="${!empty shopContext.seo.description}"><meta name="description" content="${shopContext.seo.description}" /></c:if>
<seo:pagination-link />

<c:if test="${!empty shopContext.alternateBaseUri}">
	<link rel="canonical" href="${op:property('saleson.url.shoppingmall')}${shopContext.alternateBaseUri eq '/' ? '' : shopContext.alternateBaseUri}" />
</c:if>


<link rel="stylesheet" href="/content/mobile/css/reset.css">
<link rel="stylesheet" href="/content/mobile/css/swiper.css">
<link rel="stylesheet" href="/content/mobile/css/common.css">
<link rel="stylesheet" href="/content/mobile/css/category.css">
<link rel="stylesheet" href="/content/mobile/css/product.css">
<link rel="stylesheet" href="/content/mobile/css/event.css">
<link rel="stylesheet" href="/content/mobile/css/customer.css">
<link rel="stylesheet" href="/content/mobile/css/member.css">
<link rel="stylesheet" href="/content/mobile/css/order.css">
<link rel="stylesheet" href="/content/mobile/css/mypage.css">
<link rel="stylesheet" href="/content/mobile/css/info.css">
<link rel="stylesheet" href="/content/mobile/css/popup.css">
<link rel="stylesheet" href="/content/mobile/css/main.css">

<%-- 1612 KDJ
<link rel="stylesheet" type="text/css" href="<c:url value="/content/styles/bootstrap/css/bootstrap.css" />">
<script type="text/javascript" src="/content/modules/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<c:url value="/content/modules/spin.min.js" />"></script>
<script src="<c:url value="/content/mobile/js/front.js" />" type="text/javascript"></script>
 --%>
<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">

<script type="text/javascript" src="/content/mobile/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="/content/mobile/js/browserDetect.js"></script>
<script type="text/javascript" src="/content/mobile/js/swiper.jquery.min.js"></script>
<script type="text/javascript" src="/content/mobile/js/jquery.combostars.js"></script>
<script type="text/javascript" src="/content/mobile/js/jquery.sticky.js"></script>
<script type="text/javascript" src="/content/mobile/js/front.js"></script>
<script type="text/javascript" src="/content/modules/spin.min.js"></script>
<script type="text/javascript" src="/content/js/jquery.downCount.js"></script>
<script type="text/javascript" src="/content/mobile/js/imageMapResizer.min.js"></script>

<naverPay:wcslog-head />