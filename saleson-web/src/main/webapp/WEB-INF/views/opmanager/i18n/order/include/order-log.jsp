<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty orderLogs}">
	<div class="board_list">
		<h3 class="mt10"><span>주문 상태 변경 이력</span></h3>
		<table class="board_list_table">
			<colgroup>
				<col style="width: 60px" />
			</colgroup>
			<thead>
				<tr>
					<th class="label">순번</th>
					<th class="label">주문 상품</th>
					<th class="label">상태 변경</th>
					<th class="label">처리자</th>
					<th class="label">변경일자</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderLogs}" var="log" varStatus="i">
					<tr>
						<td>
							<div>
								${i.count}
							</div>
						</td>
						<td>
							<div>
								${log.itemName}
							</div>
						</td>
						<td>
							<div>
								${log.orgOrderStatus} > ${log.orderStatus}
							</div>
						</td>
						<td>
							<div>
								${log.createdBy}
							</div>
						</td>
						<td>
							<div>
								${log.createdAtText}
							</div>
						</td>
					</tr>
				</c:forEach>					
			</tbody>
		</table>
	</div>
</c:if>