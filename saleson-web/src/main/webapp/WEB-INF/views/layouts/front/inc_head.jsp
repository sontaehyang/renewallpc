<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="seo" 	tagdir="/WEB-INF/tags/seo" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>

<meta charset="utf-8" />

<title><seo:pagination-title />${shopContext.seo.title} </title>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<jsp:include page="../common/open-graph-tag.jsp"/>

<link rel="shortcut icon" href="/content/images/common/renewallpc_favicon.ico" />
<link rel="icon" href="/content/images/common/renewallpc_favicon.ico" />
<link rel="apple-touch-icon" href="/content/images/common/renewallpc_favicon.ico" />

<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />



<!-- 20210315 21:44  son 수정 / ST -->
<!--네이버소유권-->
<meta name="viewport" content="width=device-width">
<meta name="naver-site-verification" content="dbf36f0c218e82445272e072961b4eb467b343f3" />
<!-- 20210315 21:44  son 수정 / END -->


<!-- 카카오픽셀 설치 [방문 이벤트 전송] -->
<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
<script type="text/javascript">
	kakaoPixel('1612698247174901358').pageView();
</script>





<!--네이버 연관채널 메타태그-->
<script type="application/ld+json">
	{
		"@context": "http://schema.org",
		"@type": "Person",
		"name": "리뉴올PC",
		"url": "https://www.renewallpc.co.kr",
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


<c:set var="noFollowFlag" value="N" />
<c:if test="${!empty pagination && pagination.currentPage > 1}">
	<c:set var="noFollowFlag" value="N" />
</c:if>
<c:if test="${!empty pagination && (fn:indexOf(pagination.link, 'itemsPerPage') > 1 || fn:indexOf(pagination.link, 'orderBy') > 1)}">
	<c:set var="noFollowFlag" value="Y" />
</c:if>

<%--<meta name="robots" content=""/>--%>
<%--<c:if test="${shopContext.seo.indexFlag == 'N' || noFollowFlag == 'Y'}"><meta name="robots" content="noindex,noarchive"/></c:if>--%>
<c:if test="${!empty shopContext.seo.keywords}"><meta name="keywords" content="${shopContext.seo.keywords}" /></c:if>
<c:if test="${!empty shopContext.seo.description}"><meta name="description" content="<seo:pagination-title />${shopContext.seo.description}" /></c:if>
<seo:pagination-link />

<c:if test="${!empty shopContext.alternateBaseUri}">
	<link rel="canonical" href="${op:property('saleson.url.shoppingmall')}${shopContext.alternateBaseUri eq '/' ? '' : shopContext.alternateBaseUri}" />
</c:if>

<link rel="stylesheet" type="text/css" href="/content/css/base.css">
<link rel="stylesheet" type="text/css" href="/content/css/common.css">
<link rel="stylesheet" type="text/css" href="/content/css/layout.css">
<link rel="stylesheet" type="text/css" href="/content/css/magnify.css">
<link rel="stylesheet" type="text/css" href="/content/css/event.css">
<link rel="stylesheet" type="text/css" href="/content/css/category.css">
<link rel="stylesheet" type="text/css" href="/content/css/product.css">
<link rel="stylesheet" type="text/css" href="/content/css/main.css">
<link rel="stylesheet" type="text/css" href="/content/css/member.css">
<link rel="stylesheet" type="text/css" href="/content/css/mypage.css">
<link rel="stylesheet" type="text/css" href="/content/css/order.css">
<link rel="stylesheet" type="text/css" href="/content/css/intro.css">
<link rel="stylesheet" type="text/css" href="/content/css/popup.css">
<link rel="stylesheet" type="text/css" href="/content/css/jquery-ui-datepicker.css">
<link rel="stylesheet" type="text/css" href="/content/css/animate.css">
<link rel="stylesheet" type="text/css" href="/content/css/owl.carousel.css">
<link rel="stylesheet" type="text/css" href="/content/css/owl.theme.default.min.css">

<script type="text/javascript" src="/content/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/content/js/jquery-ui.js"></script>
<script type="text/javascript" src="/content/js/jquery.bxslider.js"></script>
<script type="text/javascript" src="/content/js/jquery.sticky.js"></script>
<script type="text/javascript" src="/content/js/owl.carousel.min.js"></script>
<script type="text/javascript" src="/content/js/jquery.downCount.js"></script>
<script type="text/javascript" src="/content/js/common.js"></script>
<script type="text/javascript" src="/content/js/lnb.js"></script>

<script type="text/javascript" src="/content/mobile/js/browserDetect.js"></script>
<script type="text/javascript" src="/content/js/jquery.magnify.js"></script>

<script type="text/javascript" src="/content/modules/jquery/jquery.cookie.js"></script>

<script type="text/javascript" src="/content/modules/spin.min.js"></script>
<script type="text/javascript" src="/content/modules/op.common.js"></script>
<script type="text/javascript" src="/content/modules/op.site.js"></script>

<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<![endif]-->

<naverPay:wcslog-head />


<!-- Smartlog 스마트로그 스크립트 -->
<script type="text/javascript">
	var hpt_info={'_account':'UHPT-16790'};
</script>
<script language="javascript" src="//a22.smlog.co.kr/smart.js" charset="utf-8"></script>
<noscript></noscript>