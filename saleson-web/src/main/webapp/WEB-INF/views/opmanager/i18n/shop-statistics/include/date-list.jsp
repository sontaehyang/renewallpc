<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


<table class="board_list_table stats ${statisticsParam.displaySubtotal == 'N' ? 'odd-even' : ''}">
	<thead>
		<tr>
			<th rowspan="2">${op:message('M01377')}</th> <!-- 일자 -->
			<th rowspan="2">${op:message('M01368')}</th> <!-- 주문방법 -->
			<th colspan="6" class="division">${op:message('M01355')}</th> <!-- 결제 -->
			<th colspan="6" class="division">${op:message('M00037')}</th> <!-- 취소 -->
			<th colspan="3" class="division">합계</th>
		</tr>
		<tr>
			<th class="division">${op:message('M01357')}</th> <!-- 건수 -->
			<th>상품판매가</th>
			<th>할인</th>
			<th class="strong">판매액</th>
			<th class="strong">배송비</th>
			<th class="strong">소계</th>

			<th class="division">${op:message('M01357')}</th> <!-- 건수 -->
			<th>상품판매가</th>
			<th>할인</th>
			<th class="strong">판매액</th>
			<th class="strong">배송비</th>
			<th class="strong">소계</th>

			<th class="strong division">판매액</th>
			<th class="strong">배송비</th>
			<th class="strong">합계</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${dateList}" var="list" varStatus="i">
			<tr class="${i.count % 2 == 0 ? 'even' : '' }">
				<c:set var="param"></c:set>
				<c:set var="action">
					<c:choose>
						<c:when test="${ viewType eq 'year' }">
							<!-- 년도에서 월별검색시 검색조건 변경 2017-02-02_seungil.lee -->
							location.href = '/opmanager/shop-statistics/sales/month?startYear=${ list.searchDate }&startMonth=01&endYear=${ list.searchDate }&endMonth=12';
							<%-- location.href = '/opmanager/shop-statistics/sales/month?startDate=${ list.searchDate }0101&endDate=${ list.searchDate }1231'; --%>
						</c:when>
						<c:otherwise>
							Common.popup('/opmanager/shop-statistics/sales/${ viewType }/detail?startDate=${list.searchDate}&endDate=${ list.searchDate }&sellerId=${statisticsParam.sellerId}&type=detail', 'order-popup-${list.searchDate}', '1600', '800', 1);
						</c:otherwise>
					</c:choose>
				</c:set>

				<td rowspan="${fn:length(list.groupStats) }"><a href="javascript:;" onclick="${action}">${op:date(list.searchDate) }</a></td>
				<c:forEach items="${ list.groupStats }" var="item" varStatus="groupIndex">
					<c:if test="${groupIndex.index > 0}"></tr><tr class="${i.count % 2 == 0 ? 'even' : '' }"></c:if>

					<td>${item.deviceType}</td>
					<td class="division number">${op:numberFormat(item.saleCount)}</td>
					<td class="number">${op:numberFormat(item.itemPrice)}</td>
					<td class="number">${op:numberFormat(item.discountAmount)}</td>
					<td class="number">${op:numberFormat(item.saleAmount)}</td>
					<td class="number">${op:numberFormat(item.shipping)}</td>
					<td class="number">${op:numberFormat(item.payAmount)}</td>

					<td class="division number">${op:numberFormat(item.cancelCount)}</td>
					<td class="number">${op:numberFormat(item.cancelItemPrice)}</td>
					<td class="number">${op:numberFormat(item.cancelDiscountAmount)}</td>
					<td class="number">${op:numberFormat(item.cancelAmount)}</td>
					<td class="number">${op:numberFormat(item.cancelShipping)}</td>
					<td class="number">${op:numberFormat(item.cancelPayAmount)}</td>

					<td class="division number">${op:numberFormat(item.totalAmount)}</td>
					<td class="number">${op:numberFormat(item.totalShipping)}</td>
					<td class="number">${op:numberFormat(item.totalPayAmount)}</td>
				</c:forEach>
			</tr>
			
			<c:if test="${statisticsParam.displaySubtotal == 'Y'}">
				<tr class="sub-total">
					<td colspan="2">${op:message('M00064')}</td> <!-- 소계 -->
					<td class="division number">${op:numberFormat(list.subSaleCount)}</td>
					<td class="number">${op:numberFormat(list.subItemPrice)}</td>
					<td class="number">${op:numberFormat(list.subDiscountAmount)}</td>
					<td class="number">${op:numberFormat(list.subSaleAmount)}</td>
					<td class="number">${op:numberFormat(list.subShipping)}</td>
					<td class="number">${op:numberFormat(list.subPayAmount)}</td>

					<td class="division number">${op:numberFormat(list.subCancelCount)}</td>
					<td class="number">${op:numberFormat(list.subCancelItemPrice)}</td>
					<td class="number">${op:numberFormat(list.subCancelDiscountAmount)}</td>
					<td class="number">${op:numberFormat(list.subCancelAmount)}</td>
					<td class="number">${op:numberFormat(list.subCancelShipping)}</td>
					<td class="number">${op:numberFormat(list.subCancelPayAmount)}</td>

					<td class="division number">${op:numberFormat(list.subTotalAmount)}</td>
					<td class="number">${op:numberFormat(list.subTotalShipping)}</td>
					<td class="number">${op:numberFormat(list.subTotalPayAmount)}</td>
				</tr>
			</c:if>
		</c:forEach>
		
		<c:if test="${not empty dateList}">
			<tr class="total">
				<td colspan="2">${op:message('M00358')}</td> <!-- 합계 -->
				<td class="division number">${op:numberFormat(total.saleCount)}</td>
				<td class="number">${op:numberFormat(total.itemPrice)}</td>
				<td class="number">${op:numberFormat(total.discountAmount)}</td>
				<td class="number">${op:numberFormat(total.saleAmount)}</td>
				<td class="number">${op:numberFormat(total.shipping)}</td>
				<td class="number">${op:numberFormat(total.payAmount)}</td>

				<td class="division number">${op:numberFormat(total.cancelCount)}</td>
				<td class="number">${op:numberFormat(total.cancelItemPrice)}</td>
				<td class="number">${op:numberFormat(total.cancelDiscountAmount)}</td>
				<td class="number">${op:numberFormat(total.cancelAmount)}</td>
				<td class="number">${op:numberFormat(total.cancelShipping)}</td>
				<td class="number">${op:numberFormat(total.cancelPayAmount)}</td>

				<td class="division number">${op:numberFormat(total.totalAmount)}</td>
				<td class="number">${op:numberFormat(total.totalShipping)}</td>
				<td class="number">${op:numberFormat(total.totalPayAmount)}</td>
			</tr>
		</c:if>
	</tbody> 
</table>