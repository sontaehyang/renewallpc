<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<!-- 본문 -->
<div class="popup_wrap">
	<h3 class="popup_title"><span>정산 상세 내역</span></h3>
	<div class="popup_contents">
	
		<div class="board_list">
			<table class="board_list_table" summary="정산내역 리스트">
					<caption>정산내역 리스트</caption>
					<colgroup>
						<col style="width:10%;" />
						<col style="width:10%;" />
						<col />
						<col style="width:6%;" />
						<col style="width:6%;" />
						<col style="width:6%;" />
						<col style="width:6%;" />
						<col style="width:6%;" />
						<col style="width:6%;" />
						<col style="width:6%;" /> 
					</colgroup>
					<thead>
						<tr>
							<th scope="col">주문코드</th>
							<th scope="col">구매 확정일</th>
							<th scope="col">상품명</th>
							<th scope="col">단가</th>
							<th scope="col">수량</th>
							<th scope="col">판매가</th>
							<th scope="col">공급가</th>
							<th scope="col">수수료</th>
							<th scope="col">${op:message('M00246')} 발행</th>
							<th scope="col">정산금액</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ list }" var="orderItem" varStatus="index">
						<tr>
							<td>${orderItem.orderCode}</td>
							<td>${op:date(orderItem.confirmDate) }</td>
							<td class="tleft">
								${op:date(orderItem.itemName)}
								${ shop:viewOptionText(orderItem.options) }
							</td>
							<td class="number">
								${op:numberFormat(orderItem.price + orderItem.optionPrice)}원
							</td>
							<td class="number">
								${op:numberFormat(orderItem.quantity)}개
							</td>
							<td class="number">
								${op:numberFormat(orderItem.quantity * (orderItem.price + orderItem.optionPrice))}원
							</td>
							<td class="number">
								${op:numberFormat(orderItem.supplyPrice)}원
							</td>
							<td class="number">
								${op:numberFormat(orderItem.commissionPrice)}원
							</td>
							<td>
								${op:numberFormat(orderItem.sellerEarnPoint)}
							</td>
							<td class="number">
								${op:numberFormat(orderItem.supplyPrice + (orderItem.sellerEarnPoint))}원
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>	 
				
				<c:if test="${empty list}"> 
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. --> 
				</div>
				</c:if>
					
		</div>
			
		<div class="popup_btns">
			<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 -->
		</div>
	</div>
</div>