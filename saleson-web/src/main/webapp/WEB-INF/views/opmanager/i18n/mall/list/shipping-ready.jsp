<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>
<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<!-- 본문 -->


<!--전체주문 내역 시작-->
<h3><span>${ title }<!-- 전체주문 내역 --></span></h3>

<form:form name="mallOrderParam" modelAttribute="mallOrderParam" action="" method="post">
	<div class="board_write">
	
		<table class="board_write_table">
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup> 
			<tbody>
				 <tr>  
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 --> 
				 	<td>
				 		<div>
							<span class="datepicker"><form:input path="searchStartDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<form:select path="searchStartDateTime">
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
								<a href="javascript:;" class="btn_date day-1" onclick='setYesterDayTimeToCurrentTime("15")'>전일 15시부터 현재까지</a><!-- 1년 -->
							</span>
						</div> 
				 	</td>	
				 </tr>
			</tbody>					  
		</table>
									 							
	</div> <!-- // board_write -->
	<div class="btn_all">
		<div class="btn_left">

		</div> 
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span>검색</span></button>
		</div>
	</div>
</form:form>

<div class="board_list">
	<div class="btn_all">
		<div class="btn_left mb0">
			<button type="button" class="btn btn-default btn-sm shipping-start">배송 시작</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div>
	<form id="listForm">
		<table class="board_list_table" summary="주문내역 리스트">
			<caption>주문내역 리스트</caption>
			<colgroup>
				<col style="width:2%;" />
				<col style="width:10%;" />
				<col style="width:10%;" />
				<col style="width:10%;" />
				<col />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:12%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">Mall 타입</th>
					<th scope="col">Mall Login Id</th>
					<th scope="col">주문번호</th>
					<th scope="col">상품명</th>
					<th scope="col">수량</th>
					<th scope="col">상품코드</th>
					<th scope="col">주문자명</th>
					<th scope="col">배송 정보</th>
				</tr>
		
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="order" varStatus="index">
					<tr>
						<td><input type="checkbox" name="id" value="${order.mallOrderId}" /></td>
						<td>${order.mallType}</td>
						<td>${order.mallLoginId}</td>
						<td>
							${order.orderCode}
							<button type="button" class="btn btn-gradient btn-sm" onclick="Common.popup('/opmanager/mall/shipping-ready/cancel/${order.mallType}?mallOrderId=${order.mallOrderId}', 'cancel', 700, 500, 1);">주문 취소</button>
						</td>
						<td class="tleft">
							${order.productName}
							<c:if test="${not empty order.optionName}">
								<p>[옵션] ${order.optionName}</p>
							</c:if>
						</td>
						<td>
							${op:numberFormat(order.quantity)}개
						</td>
						<td>${order.mallProductCode}</td>
						<td>
							${order.buyerName}
							<c:if test="${order.memberId != ''}">
								<p>${order.memberId}</p>
							</c:if>
						</td>
						<td>
							<input type="hidden" name="mallOrderIds[${index.index}]" value="${order.mallOrderId}" />
							<p class="mb5">
								<select name="deliveryCompanyCodes[${index.index}]" class="form-block">
									<option value="0">-선택-</option>
									<c:forEach items="${shop:getMallDeliveryCompanyListByMallType(order.mallType)}" var="code">
										<option value="${code.key.id}">${code.label}</option>
									</c:forEach> 
								</select>
							</p>
							<input type="text" name="deliveryNumbers[${index.index}]" class="form-block" maxlength="30" />
						</td>
					</tr>
					<c:if test="${order.systemMessage != ''}">
						<tr>
							<td colspan="9" class="tleft"><strong>${order.systemMessage}</strong></td>
						</tr>
					</c:if>
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
			<button type="button" class="btn btn-default btn-sm shipping-start">배송 시작</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 

	<page:pagination-manager /> 

</div>
<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		$('.shipping-start').on('click', function() { 
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			 
			if (confirm("선택하신 주문을 배송처리 하시겠습니까?")) {
				listUpdate('shipping-start');
			}
		});
		
	});
	
	function listUpdate(mode) {
		$.post('/opmanager/mall/shipping-ready/listUpdate/'+mode, $("#listForm").serialize(), function(response){
			Common.responseHandler(response, function(response) {
				location.reload();
			}, function(response){
				alert(response.errorMessage);
			});
		});
	}
</script>