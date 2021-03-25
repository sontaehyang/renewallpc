<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="ko" class="seller">
<head>
<tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>
<body>
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<tiles:insertAttribute name="content"></tiles:insertAttribute>
<tiles:insertAttribute name="footer"></tiles:insertAttribute>
<tiles:insertAttribute name="common"></tiles:insertAttribute>
<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>
