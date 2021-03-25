<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">
	.board_list_table th{
		text-align:center;
	}
	
	.order_cancel_layer {display: none;position: fixed; z-index: 100000; width:850px; left: 50%; margin-left: -425px; top:10px; padding-bottom: 20px; background: #fff}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3><span>주문취소 목록</span></h3>

<form:form modelAttribute="claimApplyParam" method="get">
		
	<div class="board_write">
	
		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col />
				<col style="width:150px;" />
				<col />
			</colgroup>
			<tbody>
				<tr>  
				 	<td class="label">신청일자</td><!-- 주문일자 --> 
				 	<td colspan="3"> 
				 		<div>
							<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<span class="wave">~</span>							
							<span class="datepicker"><form:input path="searchEndDate" class="datepicker" maxlength="8" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<span class="day_btns"> 
								<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 --> 
								<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 --> 
								<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 --> 
								<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 --> 
								<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 --> 
								<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
							</span>
						</div> 
				 	</td>
				 </tr>
				 <tr>	
				 	<td class="label">${op:message('M00011')} <!-- 검색구분 --> </td>
				 	<td colspan="3">
				 		<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="USER_NAME" label="주문자명" />
								<form:option value="CLAIM_CODE" label="클레임번호" />
								<form:option value="ORDER_CODE" label="주문번호" />
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
				 <c:if test="${requestContext.sellerPage == false}">
					 <tr>
					 	<td class="label">반품구분</td>
					 	<td>
					 		<div>
								<form:radiobutton path="shipmentReturnType" value="" label=" 전체" />
								<form:radiobutton path="shipmentReturnType" value="1" label=" 운영사" />
								<form:radiobutton path="shipmentReturnType" value="0" label=" 판매자" />
							</div>
					 	</td>
					 	<td class="label">판매자</td>
						<td>
							<div>
								<form:select path="sellerId">
									<form:option value="0">${op:message('M00039')}</form:option>
									<c:forEach items="${sellerList}" var="list" varStatus="i">
										<form:option value="${list.sellerId}">[${list.loginId}] ${list.sellerName}</form:option>
									</c:forEach>
								</form:select>
								<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"> <span class="glyphicon glyphicon-search"></span> 검색</a>
							</div>
						</td>
					 </tr>
				 </c:if>
				 <tr>
				 	<td class="label">처리상태</td>
				 	<td colspan="3">
				 		<div>
							<form:checkbox path="claimStatus" value="01" label=" 신청" />
							<form:checkbox path="claimStatus" value="02" label=" 보류" />
							<form:checkbox path="claimStatus" value="10" label=" 회수중" />
							<form:checkbox path="claimStatus" value="11" label=" 회수완료" />
							<form:checkbox path="claimStatus" value="99" label=" 거절" />
							<form:checkbox path="claimStatus" value="03" label=" 교환상품 발송" />
						</div>
				 	</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/return/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}<!-- 검색 --></button>
			</div>
		</div>
	</div>
	
	<div class="count_title mt20">
		<h5>
			${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
		</h5>
		<span>
			${op:message('M00052')} : <!-- 출력수 --> 
			<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
				onchange="$('form#claimApplyParam').submit();"> <!-- 화면 출력수 -->
				<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="200" label="200${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="500" label="500${op:message('M00053')}" /> <!-- 개 출력 -->
			</form:select>
		</span>
	</div>
</form:form>



<div class="board_list">

	<div class="btn_all">
		<div class="btn_left mb0">

		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div>
	<form id="listForm">
		<table class="board_list_table" summary="주문내역 리스트">
			<caption>주문내역 리스트</caption>
			<colgroup>
				<col style="width:5%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:7%;" />
				<col style="width:7%;" />
				<col style="width:7%;" />
				<col style="width:7%;" />
				<c:if test="${requestContext.sellerPage == false}">
				<col style="width:7%;" />
				</c:if>
				<col />
				<col style="width:7%;" />
			</colgroup> 
			<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">주문일자</th>
					<th scope="col">신청일자</th>
					<th scope="col">주문번호</th>
					<th scope="col">처리상태</th>
					<th scope="col">클레임번호</th>
					<th scope="col">주문자</th>
					<th scope="col">수취인</th>
					<c:if test="${requestContext.sellerPage == false}">
					<th scope="col">판매자</th>
					</c:if>
					<th scope="col">상품정보</th>
					<th scope="col">신청수량</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="listIndex" value="0" />
				
				<c:forEach items="${ list }" var="apply" varStatus="index">
					<c:set var="orderItem" value="${apply.orderItem}" />
				
				
					<c:set var="tdColor">#f4f4f4</c:set>
					<c:if test="${index.count % 2 == 0}">
						<c:set var="tdColor"></c:set>					
					</c:if>

					<tr style="background:${tdColor};">
						<td>${op:numberFormat(pagination.itemNumber - index.count)}</td>
						<td>${op:datetime(orderItem.createdDate)}</td>
						<td>${op:datetime(apply.createdDate)}</td>
						<td> 
							<a href="${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/exchange/order-detail/${orderItem.orderSequence}/${orderItem.orderCode}?url=${requestContext.currentUrl}">${orderItem.orderCode}</a>
						</td>
						<td>${apply.claimStatusLabel}</td>
						<td>${apply.claimCode}</td>
						<td>
							${apply.buyerName}
							<c:if test="${not empty apply.loginId}">
								<p>[${apply.loginId}]</p>
							</c:if>
						</td>
						<td>${apply.receiveName}</td>
						<c:if test="${requestContext.sellerPage == false}">
						<td>
							<c:choose>
								<c:when test="${shop:sellerId() == orderItem.sellerId}">자사</c:when>
								<c:otherwise>
									<span class="glyphicon glyphicon-user"></span>${orderItem.sellerName}
								</c:otherwise> 
							</c:choose> 
						</td>
						</c:if>
						<td class="left">
							${orderItem.itemName} [${orderItem.itemUserCode}]
							${ shop:viewOptionText(orderItem.options) }
							${ shop:viewAdditionOrderItemList(orderItem.additionItemList)}
							${ shop:viewOrderGiftItemList(orderItem.orderGiftItemList)}
						</td>
						<td>${op:numberFormat(apply.claimApplyQuantity)}개</td>
						
					</tr>
					<c:set var="listIndex">${listIndex + 1}</c:set>
				</c:forEach>
			</tbody>
		</table>
	</form>
	
	<c:if test="${empty list}">
	<div class="no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. --> 
	</div>
	</c:if>	
	
	<div class="btn_all">
		<div class="btn_left mb0">

		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 
	
	<page:pagination-manager /> 
	
</div> 

<div class="board_guide ml10">
	<p class="tip">Tip</p>
	<p class="tip"></p> 
</div>
<script type="text/javascript"> 
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		
	});

	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>