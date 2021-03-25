<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

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
			<button type="button" class="btn btn-default btn-sm shipping-ready">발주 확인</button>
			<button type="button" class="btn btn-default btn-sm matching-save">매칭 저장</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div>
	<form id="listForm">
		<table class="board_list_table" summary="주문내역 리스트">
			<caption>주문내역 리스트</caption>
			<colgroup>
				<col style="width:2%;" />
				<col style="width:8%;" />
				<col style="width:15%;" />
				<col style="width:10%;" />
				<col />
				<col style="width:8%;" />
				<col style="width:5%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">Mall 타입</th>
					<th scope="col">매칭여부</th>
					<th scope="col">주문번호</th>
					<th scope="col">상품명</th>
					<th scope="col">판매단가</th>
					<th scope="col">수량</th>
					<th scope="col">주문금액</th>
					<th scope="col">주문자명</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="order" varStatus="index">
					<tr>
						<td><input type="checkbox" name="id" value="${order.mallOrderId}" /></td>
						<td>
							${order.mallType}
							<p>(${order.mallLoginId})</p>
						</td>
						<td class="matched-info-${order.mallOrderId}">
							<p>
								<c:choose>
									<c:when test="${order.matched == false}"><strong>비매칭</strong></c:when>
									<c:otherwise>매칭</c:otherwise>
								</c:choose> 
							</p>
							
							<input type="hidden" name="isMatched" value="${order.matched}" />
							<input type="hidden" name="orgItemUserCodes" value="${order.itemUserCode}" />
							<input type="hidden" name="orgMatchedOptions" value="${order.matchedOptions}" />
							<input type="text" name="itemUserCodes" class="form-block" value="${order.itemUserCode}" />
							<c:choose>
								<c:when test="${not empty order.item.itemOptions && order.item.itemOptionType != 'T' && order.matched == false}">
									
									<c:choose>
										<c:when test="${order.item.itemOptionType == 'S'}">
											<c:forEach items="${order.itemOptionGroups}" var="group">
												<p class="mt10">
													<select name="matchedOptionIds[${index.index}]" class="form-block matchedOptionIds">
														<option value="0">-선택-</option>
														<c:forEach items="${group.value}" var="option">
															<c:if test="${option.optionType != 'T'}">
																<option value="${option.itemOptionId}">
																	${option.optionName1}:${option.optionName2}
																</option>
															</c:if>
														</c:forEach>
													</select>
												</p>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<p class="mt10">
												<select name="matchedOptionIds[${index.index}]" class="form-block matchedOptionIds">
													<option value="0">-선택-</option>
													<c:forEach items="${order.item.itemOptions}" var="option">
														<c:if test="${option.optionType != 'T'}">
															<option value="${option.itemOptionId}">
																${order.item.itemOptionTitle1}:${option.optionName1}
																<c:if test="${option.optionType == 'S2' || option.optionType == 'S3'}">
																	, ${order.item.itemOptionTitle2}:${option.optionName2}
																	<c:if test="${option.optionType == 'S3'}">
																		, ${order.item.itemOptionTitle3}:${option.optionName3}	
																	</c:if> 
																</c:if>  
															</option>
														</c:if>
													</c:forEach>
												</select>
											</p>
										</c:otherwise>
									</c:choose>
										
								</c:when>
								<c:otherwise> 
									<input type="hidden" name="matchedOptions" class="form-block" value="${order.matchedOptions}" />
									<input type="hidden" name="matchedOptionIds[${index.index}]" class="form-block" value="0" />
									
								</c:otherwise>
							</c:choose>
						</td>
						<td> 
							<a href="javascript:Common.popup('/opmanager/mall/new-order/detail/${order.mallOrderId}', 'newOrderDetail', 900, 600, 1);">${order.orderCode}</a>
							<button type="button" class="btn btn-gradient btn-sm" onclick="Common.popup('/opmanager/mall/new-order/cancel/${order.mallType}?mallOrderId=${order.mallOrderId}', 'cancel', 700, 500, 1);">주문 취소</button>
						</td>
						<td class="tleft">
							${order.productName}
							<c:if test="${not empty order.optionName}">
								<p>[옵션] ${order.optionName}</p>
							</c:if>
						</td>
						<td>
							${op:numberFormat(order.sellPrice)}원
						</td>
						<td>
							${op:numberFormat(order.quantity)}개
							<c:if test="${order.cancelQuantity > 0}">
								<p><strong>(-${op:numberFormat(order.cancelQuantity)})</strong></p>
							</c:if>
							<c:if test="${order.soldOut == true}">
								<p><strong style="color:red">(품절)</strong></p>
							</c:if>
						</td>
						<td>
							${op:numberFormat(order.saleAmount)}원
						</td>
						<td>
							${order.buyerName}
							<c:if test="${order.memberId != ''}">
								<p>${order.memberId}</p>
							</c:if>
						</td>
					</tr>
					<c:if test="${order.systemMessage != ''}">
						<tr>
							<td colspan="8" class="tleft"><strong>${order.systemMessage}</strong></td>
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
			<button type="button" class="btn btn-default btn-sm shipping-ready">발주 확인</button>
			<button type="button" class="btn btn-default btn-sm matching-save">매칭 저장</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 

	<page:pagination-manager /> 

</div>
<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		$('.shipping-ready').on('click', function() { 
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			
			var message = "선택하신 주문을 발주확인처리 하시겠습니까?";
			$.each($form.find('input[name=id]:checked'), function(){
				var id = $(this).val();
				
				$matchedArea = $('td.matched-info-' + id, $form);
				var isMatched = $('input[name=isMatched]', $matchedArea).val();

				if (isMatched == false || isMatched == 'false') {
					message = "상품이 매칭되지 않은것이 있습니다. \n발주처리 하시겠습니까?";
				} else {
					
					var orgItemUserCode = $('input[name=orgItemUserCode]', $matchedArea).val();
					var itemUserCode = $('input[name=itemUserCode]', $matchedArea).val();
					if (orgItemUserCode != itemUserCode) {
						message = "상품 매칭정보가 변경된것이 있습니다. \n저장하지 않고 발주처리 하시겠습니까?";
					} else {
						
						if ($('.matchedOptionIds:selected').size() > 0) {
							var orgMatchedOptions = $('input[name=orgMatchedOptions]', $matchedArea).val();
							var matchedOptions = "";
							
							$.each($('.matchedOptionIds:selected'), function(i){
								matchedOptions += (i > 0 ? "^^^" : "") + $(this).val(); 
							});
							
							if (orgMatchedOptions != matchedOptions) {
								message = "옵션 매칭정보가 변경된것이 있습니다. \n저장하지 않고 발주처리 하시겠습니까?";
							}
						}
					}
				}
			});
			
			if (confirm(message)) {
				listUpdate('shipping-ready');
			}
		});
		
		$('.matching-save').on('click', function() { 
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			 
			if (confirm("선택하신 주문을 저장 하시겠습니까?")) {
				listUpdate('matching-save');
			}
		});
		
		
	});
	
	function listUpdate(mode) {
		$.post('/opmanager/mall/new-order/listUpdate/'+mode, $("#listForm").serialize(), function(response){
			Common.responseHandler(response, function(response) {
				location.reload();
			}, function(response){
				alert(response.errorMessage);
			});
		});
	}
</script>