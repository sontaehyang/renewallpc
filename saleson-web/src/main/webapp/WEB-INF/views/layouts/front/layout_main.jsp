<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="seo" 	tagdir="/WEB-INF/tags/seo" %>

<!doctype html>
<html lang="ko">
<head>
	<tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>
<body>
	<div id="wrap">	
		<header id="header"> 
			<tiles:insertAttribute name="header"></tiles:insertAttribute>
		</header>

 		<div id="container"> 
			
 			<tiles:insertAttribute name="content"></tiles:insertAttribute>
			 	
		</div>	
		
		<footer id="footer">
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		</footer>
		<div id="floating">
			<tiles:insertAttribute name="saleson_aside"></tiles:insertAttribute>
		</div>
	</div>
	<tiles:insertAttribute name="common"></tiles:insertAttribute>
	<tiles:insertAttribute name="script"></tiles:insertAttribute>	  
 </body>
</html>


