<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<style>
.popup_contents h2 {margin-top: 0; margin-bottom: 5px;}
#result {padding: 15px 10px 30px 10px; height: 165px; overflow-y: auto; margin-bottom: 30px; border: 1px dotted #ccc; color: #444; background:#ffffdd}
#result p.upload_file {font-weight: bold; font-size: 13px; color: #222; text-decoration: underline; margin-bottom: 10px;}
#result p.sheet {padding-top: 15px;}
#result p.error-cell {color: #dc4618; padding: 0 0 5px 10px; font-size: 11px;}
#result span {display: inline-block; font-weight: bold; width: 150px; color: #000;}
</style>
<div class="popup_wrap">
	<h1 class="popup_title">출고지 주소/배송비 관리</h1>
	
	<div class="popup_contents">
		
      		
      		
      		<jsp:include page="inc-shipment.jsp" />
      		
      		
		<a href="#" class="popup_close">창 닫기</a>
	</div>				
</div>
