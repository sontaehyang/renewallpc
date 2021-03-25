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
	<h3 class="popup_title"><span>${mallType} 취소 거부 처리</span></h3>
	<div class="popup_contents">
		<form:form modelAttribute="cancelApply" name="cancelApply" method="post">
			<form:hidden path="mallOrderId" />
			<form:hidden path="claimCode" />
			
			<!-- div class="board_guide">
				<p class="tip">
					
				</p>
			</div -->
			
			<div class="board_list">
				<h3 class="mt10"><span>취소 상품</span><span class="small_text">(빼송번호:${mallOrder.shippingCode}/주문번호:${mallOrder.orderCode}})</span></h3>
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
		 					<td>${cancelApply.mallOrder.orderIndex}</td>
		 					<td>
		 						${cancelApply.mallOrder.productName}
		 						<c:if test="${not empty cancelApply.mallOrder.optionName}">
									<p>[옵션] ${cancelApply.mallOrder.optionName}</p>
								</c:if>
		 					</td>
		 					<td>${op:numberFormat(cancelApply.mallOrder.sellPrice)}원</td>
		 					<td>${op:numberFormat(cancelApply.claimQuantity)}개</td>
		 					<td>
		 						<div>
									<c:choose>
										<c:when test="${cancelApply.mallOrder.payShippingType == '01'}">선불</c:when>
										<c:when test="${cancelApply.mallOrder.payShippingType == '02'}">착불</c:when>
										<c:when test="${cancelApply.mallOrder.payShippingType == '03'}">무료</c:when>
									</c:choose>
								</div>
		 					</td> 
		 					<td>
		 						<div>
									${op:numberFormat(cancelApply.mallOrder.payShipping)}원
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
									${op:datetime(cancelApply.cancelApplyDate)}
								</div>
							</td>
						</tr>
						<tr>
							<th class="label">신청자</th>
							<td>
								<div>
									<c:choose>
										<c:when test="${cancelApply.claimApplySubject == '01'}">구매자</c:when>
										<c:otherwise>판매자</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
						<tr>
							<th class="label">사유</th>
							<td>
								<div>
									[<c:choose>
										<c:when test="${cancelApply.claimApplySubject == '01'}">
											<c:choose>
												<c:when test="${cancelApply.cancelReason == '00'}">무통장 미입금 취소</c:when>
												<c:when test="${cancelApply.cancelReason == '04'}">판매자의 배송 처리가 늦음</c:when>
												<c:when test="${cancelApply.cancelReason == '06'}">판매자의 상품 정보가 잘못됨</c:when>
												<c:when test="${cancelApply.cancelReason == '07'}">동일 상품 재주문(주문정보수정)</c:when>
												<c:when test="${cancelApply.cancelReason == '08'}">주문상품의 품절/재고없음</c:when>
												<c:when test="${cancelApply.cancelReason == '09'}">11번가 내 다른 상품으로 재주문</c:when>
												<c:when test="${cancelApply.cancelReason == '10'}">타사이트 상품 주문</c:when>
												<c:when test="${cancelApply.cancelReason == '11'}">상품에 이상없으나 구매 의사 없어짐</c:when>
												<c:when test="${cancelApply.cancelReason == '12'}">기타(구매자 책임사유)</c:when>
												<c:when test="${cancelApply.cancelReason == '13'}">기타(판매자 책임사유)</c:when>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${cancelApply.cancelReason == '06'}">배송 지연 예상</c:when>
												<c:when test="${cancelApply.cancelReason == '07'}">상품/가격 정보 잘못 입력</c:when>
												<c:when test="${cancelApply.cancelReason == '08'}">상품 품절(전체옵션)</c:when>
												<c:when test="${cancelApply.cancelReason == '09'}">옵션 품절(해당옵션)</c:when>
												<c:when test="${cancelApply.cancelReason == '10'}">고객변심</c:when>
												<c:when test="${cancelApply.cancelReason == '99'}">기타</c:when>
											</c:choose>
										</c:otherwise>
									</c:choose>] ${cancelApply.cancelReasonText}
								</div>
							</td>
						</tr>
						
		 				<tr>
		 					<th class="label">
		 						<form:select path="cancelRefusalReson" class="required" title="사유">
	 								<form:option value="01">취소책임사유 입력 오류</form:option>
	 								<form:option value="02">상품 발송처리 완료</form:option>
	 							</form:select>
		 					</th>
		 					<td class="left">
	 							
		 						<div> 
			 						<form:input path="cancelRefusalResonText" style="width:70%" class="required" title="사유" />
			 						
			 						<div class="cancelShipping" <c:if test="${cancelApply.cancelRefusalReson != '02'}">style="display:none;"</c:if>>
			 							<p class="mt10">
			 								발송일 : 
			 								<span class="datepicker">
						 						<form:input path="cancelDeliveryDate" maxlength="8" class="datepicker2 term" />
									 		</span>
			 							</p>
										<p class="mb5 mt5">
											<form:select path="cancelDeliveryCompanyCode" class="form-block">
												<form:option value="0">-선택-</form:option>
												<form:options items="${shop:getMallDeliveryCompanyListByMallType(mallOrder.mallType)}" itemValue="key.id" itemLabel="label" />
											</form:select>
										</p>
										<p class="mb5 mt5">
											<form:input path="cancelDeliveryNumber" maxlength="30" class="form-block" />
										</p>
									</div>
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
		$("#cancelApply").validator({
			'requiredClass' : 'required',
			'submitHandler' : function() {
				
			}
		});
		
		$('select[name="cancelRefusalReson"]').on('change', function() {
			if ($(this).val() == '02') {
				$('.cancelShipping').show();
			} else {
				$('.cancelShipping').hide();
			} 
		})
	});
	
	
</script>	