<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>

<c:set var="ogTitle" value="${shopContext.seo.title}"/>
<c:set var="ogType" value="website"/>
<c:set var="ogImage" value="https://www.renewallpc.co.kr/content/images/common/renewallpc_img_og.jpg"/>
<c:set var="ogDescription" value="${shopContext.seo.description}"/>
<c:set var="ogUrl" value="${op:property('saleson.url.shoppingmall')}"/>
<c:set var="ogKeywords" value="${shopContext.seo.keywords}"/>

<c:if test="${not empty openGraphInfo}">
	<c:set var="ogTitle" value="${openGraphInfo.title}"/>
	<c:set var="ogType" value="${openGraphInfo.type}"/>
	<c:set var="ogImage" value="${openGraphInfo.image}"/>
	<c:set var="ogDescription" value="${openGraphInfo.description}"/>
	<c:set var="ogUrl" value="${openGraphInfo.url}"/>
</c:if>

<c:if test="${not empty ogTitle}">
	<meta property="og:title" content="${ogTitle}"/>
</c:if>
<c:if test="${not empty ogUrl}">
	<meta property="og:url" content="${ogUrl}"/>
</c:if>
<c:if test="${not empty ogType}">
	<meta property="og:type" content="${ogType}"/>
</c:if>
<c:if test="${not empty ogImage}">
	<meta property="og:image" content="${ogImage}"/>
</c:if>
<c:if test="${not empty ogDescription}">
	<meta property="og:description" content="${ogDescription}"/>
</c:if>
<c:if test="${not empty ogKeywords}">
	<meta property="og:keywords" content="${ogKeywords}"/>
</c:if>

<meta property="og:site_name" content="리뉴올pc">
