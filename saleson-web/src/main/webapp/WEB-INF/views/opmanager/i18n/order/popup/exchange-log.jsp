<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<style>
.table_btn {position: absolute; top: 77px; right: 18px;}
</style>

<div class="popup_wrap">
	<h1 class="popup_title">교환 로그</h1>
	<div class="popup_contents">
		
		<table class="inner-table">
			<caption>${op:message('M00059')}</caption>
			<!-- 주문정보 -->
			<colgroup>
				<col style="width: 80px" />
				<col />
				<col style="width: 120px" />
				<col style="width: 120px" />
				<col style="width: 100px" />
				<col style="width: 120px" />
				<col style="width: 120px" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col" class="none_left">이미지</th>
					<th scope="col" class="none_left">상품정보</th>
					<th scope="col" class="none_left">구분</th>
					<th scope="col" class="none_left">클레임수량</th>
					<th scope="col" class="none_left">상태</th>
					<th scope="col" class="none_left">신청일</th>
				</tr>
			</thead>
			<tbody>
				
			<c:forEach items="${apply}" var="apply">
			<c:set var="orderItem" value="${apply.orderItem}" />

				<tr ${orderItem.additionItemFlag == 'Y' ? 'style="display:none"' : ''}>
					<td>
						<img src="${orderItem.imageSrc}" alt="${orderItem.itemName}" width="100%"/>
					</td>
					<td>
						[${orderItem.itemUserCode}] ${orderItem.itemName}
						${ shop:viewOptionText(orderItem.options) }
						${shop:viewAdditionOrderItemList(orderItem.additionItemList)}
					</td>
					<td class="text-center">
						<c:choose>
							<c:when test="${shop:sellerId() == orderItem.sellerId}">자사</c:when>
							<c:otherwise>
								<span class="glyphicon glyphicon-user"></span>${orderItem.sellerName}>
							</c:otherwise> 
						</c:choose>
					</td>
					<td class="text-right">
						${op:numberFormat(apply.claimApplyQuantity)}개
					</td>
					<td class="text-center"> 
						${apply.claimStatusLabel}
					</td>
					<td class="text-center"> 
						${op:datetime(apply.createdDate)}
					</td>
				</tr>
				<tr ${orderItem.additionItemFlag == 'Y' ? 'style="display:none"' : ''}>
					<td colspan="6"> 
						<table class="inner-table">
							<colgroup>
								<col style="width:15%" />
								<col style="width:35%"/>
								<col style="width:15%" />
								<col style="width:35%"/>
							</colgroup>
							<tbody>
								<tr>
									<th>신청사유</th>
									<td>
										<p>
											<c:choose>
												<c:when test="${apply.claimApplySubject == '01'}">[구매자 신청] </c:when>
												<c:otherwise>[판매자 신청] </c:otherwise>
											</c:choose>
											
											<c:choose>
												<c:when test="${apply.exchangeReason == '2'}">고객 사유</c:when>
												<c:when test="${apply.exchangeReason == '1'}">판매자 사유</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</p>
										<p class="mt5">${apply.exchangeReasonText}</p>
										<p>${apply.exchangeReasonDetail}</p>
									</td>
									<th>반품 송장 정보</th>
									<td>
										<c:choose>
											<c:when test="${apply.exchangeShippingAskType == '1'}">
												지정택배사 [${apply.exchangeDeliveryCompanyName}] ${apply.exchangeDeliveryNumber}
											</c:when>
											<c:otherwise>
												직접발송 [${apply.exchangeShippingCompanyName}] ${apply.exchangeShippingNumber}
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th>회수 요청지 주소</th>
									<td colspan="3">
										[${apply.exchangeReceiveName}]<br/>
										(${apply.exchangeReceiveZipcode}) ${apply.exchangeReceiveAddress} ${apply.exchangeReceiveAddress2} <br/>
										${apply.exchangeReceivePhone} / ${apply.exchangeReceiveMobile}
									</td>
								</tr>
								<c:if test="${not empty apply.exchangeRefusalReasonText}">
									<tr>
										<th class="text-center">거절사유</th>
										<td colspan="3">
											${apply.exchangeRefusalReasonText}
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	
	<a href="#" class="popup_close">창 닫기</a>
</div>