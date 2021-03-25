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
			<button type="button" class="btn btn-default btn-sm return-confirm">반품 완료</button>
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
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">Mall 타입</th>
					<th scope="col">상태</th>
					<th scope="col">주문번호</th>
					<th scope="col">상품명</th>
					<th scope="col">요청수량</th>
					<th scope="col">상품코드</th>
					<th scope="col">주문자명</th>
				</tr>
		
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="returnApply" varStatus="index">
					<tr>
						<td><input type="checkbox" name="id" value="${returnApply.mallOrderId}||${returnApply.claimCode}" /></td>
						<td>
							${returnApply.mallOrder.mallType}
							<p>(${returnApply.mallOrder.mallLoginId})</p>
						</td>
						<td>
							<c:choose>
							
								<c:when test="${returnApply.mallOrder.mallType == '11st'}">
									<c:choose>
										<c:when test="${returnApply.claimStatus == '105'}">반품신청</c:when>
										<c:when test="${returnApply.claimStatus == '104'}">반품보류</c:when>
									</c:choose>
								</c:when>
								<c:when test="${returnApply.mallOrder.mallType == 'auction'}">
									<c:choose>
										<c:when test="${returnApply.claimStatus == 'Requested'}">반품신청</c:when>
										<c:when test="${returnApply.claimStatus == 'Hold'}">반품보류</c:when>
									</c:choose>
								</c:when>
							</c:choose>
						</td>
						<td>
							${returnApply.mallOrder.orderCode}
							<c:if test="${returnApply.claimStatus == '105' || returnApply.claimStatus == 'Requested'}">
								<button type="button" class="btn btn-gradient btn-sm" onclick="Common.popup('/opmanager/mall/return-request/return-hold/${returnApply.mallOrder.mallType}?id=${returnApply.mallOrderId}&code=${returnApply.claimCode}', 'returnHold', 700, 500, 1);">반품 보류</button>
								<c:if test="${returnApply.mallOrder.mallType != 'auction'}">
									<button type="button" class="btn btn-gradient btn-sm" onclick="Common.popup('/opmanager/mall/return-request/return-refusal/${returnApply.mallOrder.mallType}?id=${returnApply.mallOrderId}&code=${returnApply.claimCode}', 'returnRefusal', 700, 500, 1);">반품 거부</button>
								</c:if>
							</c:if>
						</td>
						<td>
							${returnApply.mallOrder.productName}
							<c:if test="${not empty returnApply.mallOrder.optionName}">
								<p>[옵션] ${returnApply.mallOrder.optionName}</p>
							</c:if>
						</td>
						<td>
							${op:numberFormat(returnApply.claimQuantity)}개
						</td>
						<td>${returnApply.mallOrder.mallProductCode}</td>
						<td>
							${returnApply.mallOrder.buyerName}
							<c:if test="${returnApply.mallOrder.memberId != ''}">
								<p>${returnApply.mallOrder.memberId}</p>
							</c:if>
						</td>
					</tr>
					<c:if test="${not empty returnApply.mallOrder.systemMessage}">
						<tr>
							<td colspan="7" class="tleft"><strong>${returnApply.mallOrder.systemMessage}</strong></td>
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
			<button type="button" class="btn btn-default btn-sm return-confirm">반품 완료</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 

	<page:pagination-manager /> 

</div>
<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		
		$('.return-confirm').on('click', function() { 
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			 
			if (confirm("선택하신 주문을 반품 승인처리 하시겠습니까?")) {
				listUpdate('return-confirm');
			}
		});
		
	});
	
	function listUpdate(mode) {
		$.post('/opmanager/mall/return-request/listUpdate/'+mode, $("#listForm").serialize(), function(response){
			Common.responseHandler(response, function(response) {
				location.reload();
			}, function(response){
				alert(response.errorMessage);
			});
		});
	}
</script>