<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>
<style type="text/css">
.popup_contents .tip {
	background: #f8f8f8;
	border: 1px solid #c9c9c9;
	padding:5px;
}
span.small_text {
	font-size:9px;
	color:#818181;
}

</style>

<div class="popup_wrap">
	<h3 class="popup_title"><span>${mallType} 교환 거부 처리</span></h3>
	<div class="popup_contents">
		<form:form modelAttribute="exchangeApply" name="exchangeApply" method="post">
			<form:hidden path="mallOrderId" />
			<form:hidden path="claimCode" />
			
			<!-- div class="board_guide">
				<p class="tip">
					
				</p>
			</div -->
			
			<div class="board_list">
				<h3 class="mt10"><span>교환 거부 상품</span><span class="small_text">(빼송번호:${exchangeApply.mallOrder.shippingCode}/주문번호:${exchangeApply.mallOrder.orderCode}})</span></h3>
				<table cellpadding="0" cellspacing="0" class="board_list_table">
					<caption>table list</caption>
		 			<colgroup>
		 				<col style="width:72px;"> 
		 				<col style="width:auto;">
		 				<col style="width:100px;">
		 				<col style="width:80px;">
		 				<col style="width:100px;">
		 				<col style="width:100px;">
		 			</colgroup>
		 			<thead>
		 				<tr> 
		 					<th scope="col" class="none_left">주문순번</th>
		 					<th scope="col" class="none_left">상품명</th>
		 					<th scope="col" class="none_left">단가</th>
		 					<th scope="col" class="none_left">요청수량</th>
		 					<th scope="col" class="none_left">배송비구분</th>
		 					<th scope="col" class="none_left">배송비</th> 
		 				</tr>
		 			</thead>
		 			<tbody>
		 				<tr>
		 					<td>${exchangeApply.mallOrder.orderIndex}</td>
		 					<td>
		 						${exchangeApply.mallOrder.productName}
		 						<c:if test="${not empty exchangeApply.mallOrder.optionName}">
									<p>[옵션] ${exchangeApply.mallOrder.optionName}</p>
								</c:if>
		 					</td>
		 					<td>${op:numberFormat(exchangeApply.mallOrder.sellPrice)}원</td>
		 					<td>${op:numberFormat(exchangeApply.claimQuantity)}개</td>
		 					<td>
		 						<div>
									<c:choose>
										<c:when test="${exchangeApply.mallOrder.payShippingType == '01'}">선불</c:when>
										<c:when test="${exchangeApply.mallOrder.payShippingType == '02'}">착불</c:when>
										<c:when test="${exchangeApply.mallOrder.payShippingType == '03'}">무료</c:when>
									</c:choose>
								</div>
		 					</td> 
		 					<td>
		 						<div>
									${op:numberFormat(exchangeApply.mallOrder.payShipping)}원
								</div>
		 					</td>
		 				</tr>
		 			</tbody>
		 		</table>
		 	</div>
		 	
		 	<div class="board_write mt10">
				<table cellpadding="0" cellspacing="0" class="board_write_table">
					<colgroup>
		 				<col style="width:35%"> 
		 				<col style="width:auto;">
		 			</colgroup>
					<tbody>
						<tr>
							<th class="label">신청일</th>
							<td>
								<div>
									${op:datetime(exchangeApply.exApplyDate)}
								</div>
							</td>
						</tr>
						<tr>
							<th class="label">사유</th>
							<td>
								<div>
									[<c:choose>
										<c:when test="${exchangeApply.exReason == '206'}">구매자 - 사이즈 또는 색상 등을 잘못 선택함</c:when>
										<c:when test="${exchangeApply.exReason == '212'}">구매자 - 구매자 귀책으로 반품을 교환으로 전환</c:when>
										<c:when test="${exchangeApply.exReason == '211'}">구매자 - 기타(구매자 책임사유)</c:when>
										
										<c:when test="${exchangeApply.exReason == '207'}">판매자 - 배송된 상품의 파손/하자/포장 불량</c:when>
										<c:when test="${exchangeApply.exReason == '208'}">판매자 - 다른 상품이 잘못 배송됨</c:when>
										<c:when test="${exchangeApply.exReason == '209'}">판매자 - 품절 등의 사유로 판매자 협의 후 교환</c:when>
										<c:when test="${exchangeApply.exReason == '210'}">판매자 - 상품이 상품상세 정보와 틀림</c:when>
										<c:when test="${exchangeApply.exReason == '213'}">판매자 - 판매자 귀책으로 반품을 교환으로 전환</c:when>
										<c:when test="${exchangeApply.exReason == '214'}">판매자 - 기타(판매자 책임사유)</c:when>
									</c:choose>] ${exchangeApply.exReasonText}
								</div>
							</td>
						</tr>
						
		 				<tr>
		 					<th class="label">
		 						<form:select path="exRefusalReson" class="required" title="사유">
	 								<form:option value="201">교환 상품 미입고</form:option>
	 								<form:option value="202">고객 교환신청 철회 대행</form:option>
	 								<form:option value="203">교환 불가 상품</form:option>
	 								<form:option value="204">기타</form:option>
	 							</form:select>
		 					</th>
		 					<td class="left">
	 							
		 						<div> 
			 						<form:input path="exRefusalResonText" style="width:70%" class="required" title="사유" />
								</div>
		 					</td>
		 				</tr>
		 			</tbody>
				</table>
			</div>
			
			<div class="popup_btns">
				<button type="submit" class="btn btn-active">신청하기</button> 
				<button type="button" class="btn btn-default" onclick="self.close()">취소하기</button> 
			</div>
		</form:form>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$("#exchangeApply").validator({
			'requiredClass' : 'required',
			'submitHandler' : function() {
				
			}
		});
		
		
	});
	
	
</script>	