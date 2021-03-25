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
	<div class="active_title">
		
		<p class="txt">
			<c:choose>
				<c:when test='${point.usedType == "2"}'>
					<span class="label delete">소멸</span>
				</c:when> 
				<c:otherwise>
					<span class="label used">사용</span>
				</c:otherwise>
			</c:choose>
			<span class="date">${op:date(point.createdDate)}</span>
		</p>
        <div class="point_info">
            <p class="report plus">
                <span>
                    <c:if test="${point.point > 0}" >
                        -
                    </c:if>
                    ${op:numberFormat(point.point)}
                </span>
                    ${unit }
            </p>
        </div>
	</div>
	<div class="active_details">
		<p class="tit">${point.details}</p>
		<c:if test="${not empty point.orderCode}">
			<p class="order_number">주문번호 <span>${point.orderCode}</span></p>
		</c:if>
	</div>
</li>

</c:forEach>	 
