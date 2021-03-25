<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!doctype html>
<html>
<head>
	<tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>

<body>
	<div id="wrap">
	
		<!-- header : s -->
		<div id="header_wrap">
			<tiles:insertAttribute name="header"></tiles:insertAttribute>
		</div>
		<!-- //#header_wrap -->
		<!-- header : e -->
			
		<tiles:insertAttribute name="side_menu" />	
			
		<!-- container : s -->
		<div id="container">		
			
			<tiles:insertAttribute name="content"></tiles:insertAttribute>
		
		</div>
		<!-- //#container -->
		<!-- container : e -->
	
	<%--모바일 팝업은 푸터가 없음 --%>	
	
	<%-- 	<div id="footer_wrap">
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		</div> --%>
		<!-- //#footer_wrap -->
		<!-- footer : e -->
	</div>	


	<tiles:insertAttribute name="common"></tiles:insertAttribute>
	
	<jsp:include page="../common/adwords-overture-script.jsp" />
	<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>
