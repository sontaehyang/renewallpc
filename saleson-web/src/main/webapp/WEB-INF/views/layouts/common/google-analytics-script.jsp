<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>

<c:if test="${fn:indexOf(requestContext.httpHost, 'www.saleson.com') > -1 || fn:indexOf(requestContext.httpHost, '219.101.189.60') > -1}">
	<c:set var="googleAnalyticsAccount" value="UA-829260-1" />
	<c:if test="${fn:indexOf(requestContext.requestUri, '/m') > -1}">
		<c:set var="googleAnalyticsAccount" value="UA-829260-6" />
	</c:if>
	
	<!-- Google Tag Manager -->
	<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-M7JMZ7"
	height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
	<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
	new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
	j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
	'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
	})(window,document,'script','dataLayer','GTM-M7JMZ7');</script>
	<!-- End Google Tag Manager -->
	
	
	<!-- google analytics code -->
	<script type="text/javascript">
	var _gaq = _gaq || [];
	
	_gaq.push(['_setAccount', '${googleAnalyticsAccount}']);
	_gaq.push(['_trackPageview']);
	_gaq.push(['_addTrans',
	'${googleAnalytics.orderId}',                           // order ID - required
	'saleson',                     // affiliation or store name : 사이트명
	'${googleAnalytics.total}',                           // total - required
	'${googleAnalytics.tax}',                           // tax 
	'${googleAnalytics.shipping}',                           // shipping・
	'${googleAnalytics.city}',                   		// city
	'',                               // state or province
	'JAPAN'                      // country
	]);
	
	// _addItem
	_gaq.push(['_addItem',
	'${googleAnalytics.orderId}',                               // order ID - required
	'${googleAnalytics.code}',                               // SKU/code - required
	'${googleAnalytics.productName}',                               // product name
	'Green Medium',                     			// category or variation
	'${googleAnalytics.unitPrice}',                               // unit price - required
	'${googleAnalytics.quantity}'                                // quantity - required
	]);
	
	_gaq.push(['_trackTrans']); //submits transaction to the Analytics servers
	
	(function() {
		var ga = document.createElement('script'); ga.type = 'text/javascript';
		ga.async = true;
		
		ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();
	</script>
</c:if>
