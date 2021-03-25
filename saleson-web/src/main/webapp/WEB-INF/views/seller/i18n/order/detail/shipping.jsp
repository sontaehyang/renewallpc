<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<!-- 본문 -->
<div class="popup_wrap">
	<h3 class="popup_title"><span>${orderItem.orderCode} - 주문 정보</span></h3>
	<div class="popup_contents">
		<div class="board_write">

			<h3 class="mt10"><span>주문 기본 정보</span></h3>
			<jsp:include page="../include/order-default-info.jsp" />
		
			<h3 class="mt10"><span>주문 상품 정보</span></h3>
			<jsp:include page="../include/order-item.jsp" />
			
			<c:set var="orderShippingInfo" value="${orderItem.orderShippingInfo}" scope="request" />
			<jsp:include page="../include/order-shipping-info.jsp" />
		</div>
		
		<jsp:include page="../include/order-log.jsp" />
		
		<div class="popup_btns">
			<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 -->
		</div>
	</div>
</div>