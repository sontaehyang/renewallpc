<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>



<c:forEach var="item" items="${list}">
	<li>
		<div class="active_title">

			<c:if test="${item.sign == '+'}">
				<p class="txt">
					<span class="label save">적립</span>
				</p>
				<p class="report plus">
					<span>+ ${item.point}</span>장
				</p>
			</c:if>

			<c:if test="${item.sign == '-'}">
				<p class="txt">
					<span class="label used">사용</span>
				</p>
				<p class="report minus">
					<span>- ${item.point}</span>장
				</p>
			</c:if>

			<span class="date">${fn:substring(item.createdDate,0,4)}.${fn:substring(item.createdDate,4,6)}.${fn:substring(item.createdDate,6,8)}</span>

		</div>
		<div class="active_details">
			<p class="tit">${item.reason}</p>
			<p class="order_number">
				주문번호 <span>${item.orderCode}</span>
			</p>
			<p class="due_date">
				소멸예정일 : <span>2017.02.03</span>
			</p>
		</div>
	</li>
</c:forEach>
