<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<!doctype html>
<html lang="ja" class="opmanager">
<head>
<tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>
<body>
<div class="popup_wrap" style="min-width:1080px;">
	<h1 class="popup_title">변경내역조회</h1>
	<div class="popup_contents02">
		<tiles:insertAttribute name="content"></tiles:insertAttribute>		
	</div>
	
	<a href="javascript:self.close();" class="popup_close">창 닫기</a>
</div>
		
<tiles:insertAttribute name="common"></tiles:insertAttribute>
<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>