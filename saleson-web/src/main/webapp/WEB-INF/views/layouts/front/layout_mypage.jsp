<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!doctype html>
<html lang="ko">
<head>
	<tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>
<body>
	<div id="wrap">
		<div id="header">
			<tiles:insertAttribute name="header"></tiles:insertAttribute>
		</div>
		
		<div id="container">
			<div class="inner">
				<!-- 2016-11-07 주석처리 육선미-
				<div id="left_contents">
					<tiles:insertAttribute  defaultValue="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" />
				</div>  -->

	 			<tiles:insertAttribute name="content"></tiles:insertAttribute>
	 			
			 	<div id="floating">
					<tiles:insertAttribute name="saleson_aside"></tiles:insertAttribute>
				</div>
			</div>
		</div>	
		
		<div id="footer">
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		</div>
	</div>
	<tiles:insertAttribute name="common"></tiles:insertAttribute>
	<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html> 

		