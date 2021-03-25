<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

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
	<h3 class="popup_title"><span>${mallType} 판매취소 처리</span></h3>
	<div class="popup_contents">
		<form:form modelAttribute="cancelApply" name="cancelApply" method="post">
			<form:hidden path="mallOrderId" />
			
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
		 					<th scope="col" class="none_left">판매가</th>
		 					<th scope="col" class="none_left">수량</th>
		 					<th scope="col" class="none_left">배송비구분</th>
		 					<th scope="col" class="none_left">배송비</th> 
		 				</tr>
		 			</thead>
		 			<tbody>
		 				<tr>
		 					<td>${cancelApply.mallOrder.orderIndex}</td>
		 					<td class="tleft">
		 						${cancelApply.mallOrder.productName}
		 						<c:if test="${not empty cancelApply.mallOrder.optionName}">
									<p>[옵션] ${cancelApply.mallOrder.optionName}</p>
								</c:if>
		 					</td>
		 					<td>${op:numberFormat(cancelApply.mallOrder.saleAmount)}원</td>
		 					<td>${op:numberFormat(cancelApply.mallOrder.quantity)}개</td>
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
		 				  
		 				<tr>
		 					<td colspan="6" class="left">
	 							<form:select path="cancelReason" class="required" title="사유">
	 								<form:option value="LowerThanWishPrice">SR01 낙찰가격이 희망판매가격에 미치지 못함</form:option>
	 								<form:option value="ManufacturingDefect">SR03 제품에 하자가 생겨서 판매불가</form:option>
	 								<form:option value="OtherReason">SR07 기타 사유</form:option>
	 								<form:option value="RunOutOfStock">SR02 재고부족(품절)</form:option>
	 								<form:option value="SellToOtherDitstributionChannel">SR05 다른 경로로 판매하고자 함</form:option>
	 								<form:option value="SoldToOtherBuyer">SR04 구매자의 입금지연으로 인하여 다른 구매자에게 판매</form:option>
	 								<form:option value="UnreliableBuyer">SR06 장난/허위 입찰로 보여서 판매거부 </form:option>
	 							</form:select> 
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
	});
</script>	