<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>
<body>
<tiles:insertAttribute name="content"></tiles:insertAttribute>
<tiles:insertAttribute name="common"></tiles:insertAttribute>
<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>
