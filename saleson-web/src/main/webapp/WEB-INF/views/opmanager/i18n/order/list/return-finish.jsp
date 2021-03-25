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
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3><span>반품완료 목록</span></h3>

<form:form modelAttribute="orderParam" action="" method="get">
		
	<div class="board_write">
	
		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				<tr>  
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 --> 
				 	<td> 
				 		<div>
				 			<form:select path="searchDateType">
				 				<form:option value="OI.RETURN_REQUEST_FINISH_DATE" label="반품완료일" />
				 				<form:option value="OI.CREATED_DATE" label="주문일" />
				 			</form:select>
							<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<form:select path="searchStartDateTime">
								<form:option value="" label="-선택-" />
								<form:option value="00" label="00시" />
								<c:forEach varStatus="i" begin="1" end="23">
									<c:if test="${i.count < 10 }">
										<form:option value="0${i.count}" label="0${i.count}시" />
									</c:if>
									<c:if test="${i.count >= 10 }">
										<form:option value="${i.count}" label="${i.count}시" />
									</c:if>
								</c:forEach>
							</form:select>
							<span class="wave">~</span>							
							<span class="datepicker"><form:input path="searchEndDate" class="datepicker" maxlength="8" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<form:select path="searchEndDateTime">
								<form:option value="" label="-선택-" />
								<form:option value="00" label="00시" />
								<c:forEach varStatus="i" begin="1" end="23">
									<c:if test="${i.count < 10 }">
										<form:option value="0${i.count}" label="0${i.count}시" />
									</c:if>
									<c:if test="${i.count >= 10 }">
										<form:option value="${i.count}" label="${i.count}시" />
									</c:if>
								</c:forEach>
							</form:select>
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
				 	<td>
				 		<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="USER_NAME" label="주문자명" />
								<form:option value="RECEIVE_NAME" label="받는사람" />
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/order/return-finish'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
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
				onchange="$('form#orderParam').submit();"> <!-- 화면 출력수 -->
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
				<col style="width:2%;" />
				<col style="width:12%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:5%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:12%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">반품완료일</th>
					<th scope="col">주문번호</th>
					<th scope="col">판매자</th>
					<th scope="col">상품정보</th>
					<th scope="col">단가</th>
					<th scope="col">옵션가</th>
					<th scope="col">수량</th>
					<th scope="col">판매금액</th>
					<th scope="col">처리상태</th>
					<th scope="col">주문일</th>
				</tr>
		
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="orderShipping" varStatus="index">
					<c:forEach items="${orderShipping.orderItems}" var="orderItem">
						
						<tr>
							<td>${op:numberFormat(pagination.itemNumber - index.count)}</td>
							<td>${op:datetime(orderItem.returnRequestFinishDate)}</td> 
							<td>
								<a href="javascript:Common.popup('/opmanager/order/return-finish/order-detail/${orderItem.orderCode}', 'returnFinishDetail', 1000, 700, 1);">${orderItem.orderCode}</a>
							</td>
							<td>
								<c:choose>
									<c:when test="${shop:sellerId() == orderItem.sellerId}">자사</c:when>
									<c:otherwise>
										<span class="glyphicon glyphicon-user"></span>${orderItem.sellerName}
									</c:otherwise>
								</c:choose> 
							</td>
							<td class="left">
								${orderItem.itemName} [${orderItem.itemUserCode}]
								${ shop:viewOptionText(orderItem.options) }
								${ shop:viewOrderGiftItemList(orderItem.orderGiftItemList)}
							</td>
							<td>${op:numberFormat(orderItem.price)}원</td>
							<td>${op:numberFormat(orderItem.optionPrice)}원</td>
							<td><strong>${orderItem.quantity}개</strong></td>
							<td>${op:numberFormat(orderItem.saleAmount)}원</td>
							<td>${orderItem.orderItemStatusLabel}</td>
							<td>${op:datetime(orderItem.createdDate)}</td>
						</tr>
					</c:forEach>
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
</script>