<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<c:if test="${not empty orderItem}">
	<table class="board_write_table">
		<colgroup> 						
			<col style="width:20%;" />
			<col />
			<col style="width:20%;" />
			<col />
		</colgroup> 
		<tbody>
			<tr>
				<th class="label">주문번호</th>
				<td><div>${orderItem.orderCode}</div></td>
				<th class="label">주문일시</th>
				<td><div>${op:datetime(orderItem.createdDate)}</div></td>
			</tr>
			<tr>
				<th class="label">구매자(ID)</th>
				<td><div>${orderItem.orderShippingInfo.userName}</div></td>
				<th class="label">구매자 연락처</th>
				<td><div>전화 : ${orderItem.orderShippingInfo.phone}, 휴대폰 : ${orderItem.orderShippingInfo.mobile}</div></td>
			</tr> 
		</tbody>
	</table>
</c:if>