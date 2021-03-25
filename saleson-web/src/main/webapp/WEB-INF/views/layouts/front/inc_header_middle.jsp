<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="seo" 	tagdir="/WEB-INF/tags/seo" %>

<div class="inner">
	<h1 class="logo"><a href="/" title="SALESON"><img src="/content/images/common/saleson_logo.gif" alt="SALESON"></a></h1>
  	<div class="header_search"> 
		<jsp:include page="./saleson_query.jsp" />
	</div><!--//header_search E--> 
	<ul class="header_list">
		<li><a href="/featured/list" title="기획전">기획전</a></li>
		<li><a href="/event/new" title="신상품">신상품</a></li>
		<li><a href="/event/best" title="베스트상품">베스트상품</a></li>
		<li><a href="/event/spot" title="특가세일">특가세일</a></li>
	</ul>  
</div><!--// inner E-->
