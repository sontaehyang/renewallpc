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
	<h1 class="popup_title">송장번호 수정</h1> <!-- 엑셀 다운로드 -->
	<div class="popup_contents">
		
		<form:form modelAttribute="orderItem" method="post">
			<form:hidden path="orderCode" />
			<form:hidden path="orderSequence" />
			<form:hidden path="itemSequence" />
			<div class="board_write">
				<table class="board_list_table">
					<tbody>
						<tr>
							<th>주문번호</th>
							<td class="text-left">
								${orderItem.orderCode}
							</td>
						</tr>
						<tr>
							<th>상품정보</th>
							<td class="text-left">
								[${orderItem.itemUserCode}] ${orderItem.itemName}
								${ shop:viewOptionText(orderItem.options) }

								<c:forEach items="${additionList}" var="addition">
									<c:if test="${orderItem.orderCode == addition.orderCode && orderItem.itemSequence == addition.parentItemSequence}">
										추가구성품 : ${addition.itemName} ${addition.quantity}개 (+${op:numberFormat(addition.itemAmount)}원) <br />
									</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th>배송정보</th>
							<td class="text-left">
								<c:choose>
									<c:when test="${empty orderItem.deliveryNumber}">
										<c:choose>
											<c:when test="${orderItem.quickDeliveryFlag == 'Y'}">
												퀵 배송
											</c:when>
											<c:otherwise>
												직접수령
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										${orderItem.deliveryCompanyName}
										[${orderItem.deliveryNumber}]
									</c:otherwise>
								</c:choose>
							</td> 
						</tr>
						<tr>
							<th>변경</th>
							<td class="text-left">
								<p class="mb5">
									<form:select path="deliveryCompanyId" cssClass="required" title="배송업체">
										<form:option value="0" label="-선택-" />
										<c:forEach items="${deliveryCompanyList}" var="deliveryCompany">
											<option value="${deliveryCompany.deliveryCompanyId}" ${op:selected(deliveryCompany.deliveryCompanyName, orderItem.deliveryCompanyName)}>${deliveryCompany.deliveryCompanyName}</option>
										</c:forEach>
									</form:select>
								</p> 
								<form:input path="deliveryNumber" maxlength="30" cssClass="required" title="송장번호" />
							</td>
						</tr>					
					</tbody>
				</table>
			</div>
			<p class="popup_btns">
				<button type="submit" class="btn btn-active">수정</span></button> <!-- 다운로드 -->
			</p> 
		</form:form>
	
	<a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
	$(function(){
		$("#orderItem").validator({
			'requiredClass' : 'required',
			'submitHandler' : function() {
				
				if ($('#deliveryCompanyId').val() == '0') {
					alert('배송업체를 선택해 주세요');
					return false;
				}
				
			}
		});
		
	})
</script>