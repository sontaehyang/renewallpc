<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>

<c:if test="${fn:indexOf(requestContext.httpHost, 'www.domain.com') > -1 || fn:indexOf(requestContext.httpHost, '219.101.189.60') > -1}">
	
	<c:set var="conversionValue">${googleAnalytics.total}</c:set>
	
	<c:choose>
	<c:when test="${requestContext.requestUri == '/users/join-complete'}">
	
		<!-- [ADWARDS] 회원가입 완료 - PC -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var google_conversion_id = 1066026586;
		var google_conversion_language = "ja";
		var google_conversion_format = "1";
		var google_conversion_color = "ffffff";
		var google_conversion_label = "jrkQCLO6lFYQ2oyp_AM";
		var google_remarketing_only = false;
		/* ]]> */
		</script>
		<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/1066026586/?label=jrkQCLO6lFYQ2oyp_AM&amp;guid=ON&amp;script=0"/>
			</div>
		</noscript>
			
		<!-- [Overture] Yahoo Code 会員登録完了 -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var yahoo_conversion_id = 1000025077;
		var yahoo_conversion_label = "omgHCJfNtgMQ-d_nygM";
		var yahoo_conversion_value = 0;
		/* ]]> */
		</script>
		<script type="text/javascript" src="https://s.yimg.jp/images/listing/tool/cv/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="https://b91.yahoo.co.jp/pagead/conversion/1000025077/?label=omgHCJfNtgMQ-d_nygM&amp;guid=ON&amp;script=0&amp;disvt=true"/>
			</div>
		</noscript>
		
	</c:when>
	<c:when test="${requestContext.requestUri == '/m/users/join-complete'}">
		
		<!-- [Adwords] 회원가입 완료 - Mobile -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var google_conversion_id = 1066026586;
		var google_conversion_language = "en";
		var google_conversion_format = "1";
		var google_conversion_color = "ffffff";
		var google_conversion_label = "fa-JCPW9lFYQ2oyp_AM";
		var google_remarketing_only = false;
		/* ]]> */
		</script>
		<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/1066026586/?label=fa-JCPW9lFYQ2oyp_AM&amp;guid=ON&amp;script=0"/>
			</div>
		</noscript>
	
		<!-- [Overture] Yahoo Code 会員登録完了 -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var yahoo_conversion_id = 1000025077;
		var yahoo_conversion_label = "1qt5CPjYllYQ-d_nygM";
		var yahoo_conversion_value = 0;
		/* ]]> */
		</script>
		<script type="text/javascript" src="https://s.yimg.jp/images/listing/tool/cv/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="https://b91.yahoo.co.jp/pagead/conversion/1000025077/?value=0&amp;label=1qt5CPjYllYQ-d_nygM&amp;guid=ON&amp;script=0&amp;disvt=true"/>
			</div>
		</noscript>
		
	</c:when>
	<c:when test="${fn:indexOf(requestContext.requestUri, '/order/step3') == 0}">
	
		<!-- [Adwords] 구매완료 - PC -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var google_conversion_id = 1066026586;
		var google_conversion_language = "ja";
		var google_conversion_format = "1";
		var google_conversion_color = "ffffff";
		var google_conversion_label = "fCR_CJaqwgEQ2oyp_AM";
		
		if ('${conversionValue}' != '') {
		    var google_conversion_value = '${conversionValue}';
		}
		
		/* ]]> */
		</script>
		<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/1066026586/?value=${conversionValue}&label=fCR_CJaqwgEQ2oyp_AM&guid=ON&script=0"/>
			</div>
		</noscript>
		
		<!-- [Overture] Yahoo Code for &#36092;&#20837;&#23436;&#20102; Conversion Page -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var yahoo_conversion_id = 1000025077;
		var yahoo_conversion_label = "hy82CJ_MtgMQ-d_nygM";
		var yahoo_conversion_value = 0;
		
		if ('${conversionValue}' != '') {
			yahoo_conversion_value = '${conversionValue}';
		}
		
		/* ]]> */
		</script>
		<script type="text/javascript" src="https://s.yimg.jp/images/listing/tool/cv/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="https://b91.yahoo.co.jp/pagead/conversion/1000025077/?value=${conversionValue}&amp;label=hy82CJ_MtgMQ-d_nygM&amp;guid=ON&amp;script=0&amp;disvt=true"/>
			</div>
		</noscript>
		
	</c:when>
	
	<c:when test="${fn:indexOf(requestContext.requestUri, '/m/order/step3') == 0}">
	
		<!-- [Adwards] 구매완료 - Mobile -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var google_conversion_id = 1066026586;
		var google_conversion_language = "ja";
		var google_conversion_format = "1";
		var google_conversion_color = "ffffff";
		var google_conversion_label = "zzQRCPrQlFYQ2oyp_AM";
		
		if ('${conversionValue}' != '') {
		    var google_conversion_value = '${conversionValue}';
		}
		
		/* ]]> */
		</script>
		<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/1066026586/?value=${conversionValue}&label=zzQRCPrQlFYQ2oyp_AM&guid=ON&script=0"/>
			</div>
		</noscript>
		
		<!-- [Overture] Yahoo Code for your Conversion Page -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var yahoo_conversion_id = 1000025077;
		var yahoo_conversion_label = "M1XGCN_WllYQ-d_nygM";
		var yahoo_conversion_value = 0;
		
		if ('${conversionValue}' != '') {
			yahoo_conversion_value = '${conversionValue}';
		}
		
		/* ]]> */
		</script>
		<script type="text/javascript" src="https://s.yimg.jp/images/listing/tool/cv/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
				<img height="1" width="1" style="border-style:none;" alt="" src="https://b91.yahoo.co.jp/pagead/conversion/1000025077/?value=${conversionValue}&amp;label=M1XGCN_WllYQ-d_nygM&amp;guid=ON&amp;script=0&amp;disvt=true"/>
			</div>
		</noscript>
		
	</c:when>
	</c:choose>
</c:if>