<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<c:if test="${not empty orderLogs}">
	<div class="board_list">	
		<h3 class="mt10"><span>주문 이력</span></h3>
		<table class="board_list_table">
			<caption>주문 이력</caption> <!-- 주문정보 --> 
			<colgroup>
				<col style="auto" /> 						
				<col style="width:35%;" />
				<col style="width:30%;" />
			</colgroup>
			<thead>
				<tr>
					<th class="label">주문상태</th>
					<th class="label">일자</th>
					<th class="label">처리자</th>
				</tr>
			</thead> 
			<tbody>
				<c:forEach items="${orderLogs}" var="log">
					<tr>
						<td>
							<div>
								<c:choose>
									<c:when test="${log.orderItemStatus == '0' || log.orderItemStatus == '10'}">
										<c:if test="${log.orderStatus == ''}">
											${log.orderItemStatusLabel}
										</c:if>
										
										${log.orderStatusLabel}
									</c:when>
									<c:otherwise>
										${log.orderItemStatusLabel}
									</c:otherwise>
								</c:choose>
							</div>
						</td>
						<td>
							<div>
								${op:datetime(log.createdDate)}
							</div>
						</td>
						<td>
							<div>
								${log.userName}
							</div>
						</td>
					</tr>
				</c:forEach>					
			</tbody>
		</table>
	</div>
</c:if>