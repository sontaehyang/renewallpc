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
	<h3 class="popup_title"><span>${mallType} 반품 보류 처리</span></h3>
	<div class="popup_contents">
		<form:form modelAttribute="returnApply" name="returnApply" method="post">
			<form:hidden path="mallOrderId" />
			<form:hidden path="claimCode" />
			
			<div class="board_guide">
				<p class="tip">
					- 반품보류 후 구매자와 협의하시어 반품완료 할 수 있도록 협조바랍니다. </br/>
					- 보류 후에는 [보류해제/반품완료]만 가능하며 거부하실 수 없으니 유의하시기 바랍니다.<br/>
					- 보류 후 구매자와의 협의가 곤란하신 경우 11번가 판매고객센터로 문의하시기 바랍니다.
				</p>
			</div>
			
			<div class="board_list">
				<h3 class="mt10"><span>반품 보류 상품</span><span class="small_text">(빼송번호:${returnApply.mallOrder.shippingCode}/주문번호:${returnApply.mallOrder.orderCode}})</span></h3>
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
		 					<td>${returnApply.mallOrder.orderIndex}</td>
		 					<td>
		 						${returnApply.mallOrder.productName}
		 						<c:if test="${not empty returnApply.mallOrder.optionName}">
									<p>[옵션] ${returnApply.mallOrder.optionName}</p>
								</c:if>
		 					</td>
		 					<td>${op:numberFormat(returnApply.mallOrder.sellPrice)}원</td>
		 					<td>${op:numberFormat(returnApply.claimQuantity)}개</td>
		 					<td>
		 						<div>
									<c:choose>
										<c:when test="${returnApply.mallOrder.payShippingType == '01'}">선불</c:when>
										<c:when test="${returnApply.mallOrder.payShippingType == '02'}">착불</c:when>
										<c:when test="${returnApply.mallOrder.payShippingType == '03'}">무료</c:when>
									</c:choose>
								</div>
		 					</td> 
		 					<td>
		 						<div>
									${op:numberFormat(returnApply.mallOrder.payShipping)}원
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
									${op:datetime(returnApply.rtApplyDate)}
								</div>
							</td>
						</tr>
						<tr>
							<th class="label">사유</th>
							<td>
								<div>
									[<c:choose>
										<c:when test="${returnApply.rtReason == '101'}">구매자 - 상품에 이상 없으나 구매 의사 없어짐</c:when>
										<c:when test="${returnApply.rtReason == '110'}">구매자 - 사이즈, 색상 등을 잘못 선택함</c:when>
										<c:when test="${returnApply.rtReason == '113'}">구매자 - 기타(구매자 책임사유)</c:when>
										<c:when test="${returnApply.rtReason == '114'}">구매자 - 구매자 귀책으로 교환을 반품으로 전환</c:when>
										<c:when test="${returnApply.rtReason == '119'}">구매자 - 전세계배송(추가 해외배송비 미결제)</c:when>
										<c:when test="${returnApply.rtReason == '199'}">구매자 - 구매확정후 직권취소(구매자 책임)</c:when>
										
										<c:when test="${returnApply.rtReason == '105'}">판매자 - 상품이 상품상세 정보와 틀림</c:when>
										<c:when test="${returnApply.rtReason == '108'}">판매자 - 다른 상품이 잘못 배송됨</c:when>
										<c:when test="${returnApply.rtReason == '111'}">판매자 - 배송된 상품의 파손/하자/포장 불량</c:when>
										<c:when test="${returnApply.rtReason == '112'}">판매자 - 상품이 도착하고 있지 않음</c:when>
										<c:when test="${returnApply.rtReason == '115'}">판매자 - 판매자 귀책으로 교환을 반품으로 전환</c:when>
										<c:when test="${returnApply.rtReason == '116'}">판매자 - 기타(판매자 책임사유)</c:when>
										<c:when test="${returnApply.rtReason == '117'}">판매자 - 전세계배송 국내통관 거부</c:when>
										<c:when test="${returnApply.rtReason == '118'}">판매자 - 전세계배송 30kg 초과</c:when>
										<c:when test="${returnApply.rtReason == '198'}">판매자 - 구매확정후 직권취소(판매자 책임)</c:when>
										
									</c:choose>] ${returnApply.rtReasonText}
								</div>
							</td>
						</tr>
						
		 				<tr>
		 					<th class="label">
		 						<form:select path="rtHoldReson" class="required" title="사유">
	 								<form:option value="101">반품 상품 미입고</form:option>
	 								<form:option value="102">반품 배송비 미동봉</form:option>
	 								<form:option value="103">반품 상품 훼손</form:option>
	 								<form:option value="104">구매자 연락 두절</form:option>
	 								<form:option value="105">기타</form:option>
	 								
	 							</form:select>
		 					</th>
		 					<td class="left">
	 							
		 						<div> 
			 						<form:input path="rtHoldResonText" style="width:70%" class="required" title="사유" />
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
		$("#returnApply").validator({
			'requiredClass' : 'required',
			'submitHandler' : function() {
				
			}
		});
		
		
	});
	
	
</script>	