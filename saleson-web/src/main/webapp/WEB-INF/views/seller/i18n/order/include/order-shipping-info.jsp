<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty orderShippingInfo}">
	<h3 class="mt10"><span>배송지 정보</span></h3>
	<table class="board_write_table">
		<colgroup> 						
			<col style="width:20%;" />
			<col />
			<col style="width:20%;" />
			<col />
		</colgroup> 
		<tbody>
			<tr>
				<th class="label">전화번호</th>
				<td><div>${orderShippingInfo.receivePhone}</div></td>
				<th class="label">휴대전화번호</th>
				<td><div>${orderShippingInfo.receiveMobile}</div></td>
			</tr> 
			<tr>
				<th class="label">이름</th>
				<td><div>${orderShippingInfo.receiveName}</div></td>
				<th class="label">우편번호</th>
				<td><div>${orderShippingInfo.receiveZipcode}</div></td>
			</tr>
			<tr>
				<th class="label">주소</th>
				<td colspan="3"><div>${orderShippingInfo.receiveAddress}&nbsp;${orderShippingInfo.receiveAddressDetail}</div></td>
			</tr>
		</tbody>
	</table>
</c:if>