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
			<jsp:include page="inc_gnb.jsp" />
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
		
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		<!-- //#footer_wrap -->
		<!-- footer : e -->
	</div>	


	<tiles:insertAttribute name="common"></tiles:insertAttribute>
	
	<jsp:include page="../common/adwords-overture-script.jsp" />
	<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>
