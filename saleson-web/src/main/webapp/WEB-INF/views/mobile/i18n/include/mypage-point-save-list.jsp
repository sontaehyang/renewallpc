<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:set var="unit">
	<c:choose>
		<c:when test="${pointType == 'point'}">P</c:when>
		<c:when test="${pointType == 'shipping'}">장</c:when>
		<c:when test="${pointType == 'emoney'}">원</c:when>
	</c:choose>
</c:set>

<c:forEach var="point" items="${list}">							
	<li>
		<div class="active_title save">
			<p class="txt"><span class="label save">적립</span><span class="date">${op:date(point.createdDate)}</span></p>
			<div class="point_info">
				<p class="report plus"><span>+ ${op:numberFormat(point.savedPoint)}</span>${unit}</p>
				<p class="report">
					<c:choose>
						<c:when test="${point.expirationPoint > 0}"><span>소멸(${op:numberFormat(point.expirationPoint)}${unit})</span></c:when>
						<c:when test="${point.point == 0}">-</c:when>
						<c:otherwise><span>잔여: ${op:numberFormat(point.point)}</span>${unit}</c:otherwise>
					</c:choose>
				</p>
			</div>
		</div>
		<div class="active_details">
			<p class="tit">${point.reason}</p>
			<p class="order_number">
				<c:choose>
					<c:when test="${point.point > 0}">소멸예정일 : <span>${op:date(point.expirationDate)}</span></c:when>
				</c:choose>
			 </p>
		</div>
	</li>
</c:forEach>	 


	