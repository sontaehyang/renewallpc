<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<style type="text/css">
	.board_list_table th{
		text-align:center;
	}
	.order_return_layer {display: none;position: fixed; z-index: 100000; width:850px; left: 50%; margin-left: -425px; top:10px; padding-bottom: 20px; background: #fff}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3><span>배송중 목록</span></h3>

<form:form modelAttribute="orderParam" action="${requestContext.requestUri}" method="get">
		
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
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 --> 
				 	<td colspan="3"> 
				 		<div>
				 			<form:select path="searchDateType">
				 				<form:option value="OI.SHIPPING_DATE" label="배송시작일" />
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
				 	<td colspan="3">
				 		<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="USER_NAME" label="주문자명" />
								<form:option value="RECEIVE_NAME" label="받는사람" />
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
				 <c:if test="${requestContext.sellerPage == false}">
					 <tr>
					 	<td class="label">배송구분</td>
					 	<td>
					 		<div>
								<form:radiobutton path="deliveryType" value="" label=" 전체" />
								<form:radiobutton path="deliveryType" value="1" label=" 운영사" />
								<form:radiobutton path="deliveryType" value="0" label=" 판매자" />
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
				 	<td class="label">처리구분</td>
				 	<td colspan="3">
				 		<div>
							<form:checkbox path="shippingStatus" value="30" label=" 배송중" />
							<form:checkbox path="shippingStatus" value="35" label=" 배송완료" />
							<form:checkbox path="shippingStatus" value="55" label=" 교환배송중" />
						</div>
				 	</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/shipping'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
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
	<sec:authorize access="hasRole('ROLE_EXCEL')">
		<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px;">
			<p class="tip">
				<a href="javascript:;" class="btn_write gray_small" onclick="downloadOrderExcel( ${totalCount})"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>주문내역 다운로드</span> </a>
			</p>

			<p class="tip">
				<br/>다운로드 하실 주문을 선택후 다운로드 하시면 선택다운로드 됩니다.
				<br/>한번에 선택 다운로드 가능한 주문은 최대 500건 입니다.(우측 출력수 조정 최대치)
			</p>
		</div>
	</sec:authorize>
	<div class="btn_all">
		<div class="btn_left mb0"> 
			<button type="button" class="btn btn-default btn-sm cancel-delivery">배송 취소</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div>
	<form id="listForm">
		<table class="board_list_table" summary="주문내역 리스트">
			<caption>주문내역 리스트</caption>
			<colgroup>
				<col style="width:2%;" />
				<col style="width:2%;" />
				<col style="width:5%;" />
				<col style="width:5%;" />
				<col style="width:8%;" />
				<col style="width:5%;" />
				<c:if test="${requestContext.sellerPage == false}">
				<col style="width:6%;" />
				</c:if>
				<col />
				<col style="width:6%;" />
				<col style="width:6%;" />
				<col style="width:12%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">No</th>
					<th scope="col">주문번호</th>
					<th scope="col">주문자</th>
					<th scope="col">배송시작일<br/>(경과일)</th>
					<th scope="col">수취인</th>
					<c:if test="${requestContext.sellerPage == false}">
					<th scope="col">판매자</th>
					</c:if>
					<th scope="col">상품정보</th>
					<th scope="col">수량</th>
					<th scope="col">판매금액</th>
					<th scope="col">배송정보</th>
				</tr>
		
			</thead>
			<tbody>
				<c:forEach items="${list}" var="orderItem" varStatus="index">
					
					<tr>
						<td><input type="checkbox" name="id" value="${orderItem.itemKey}" data-item-sequence="${orderItem.itemSequence}" /></td>
						<td>${op:numberFormat(pagination.itemNumber - index.count)}</td>
						<td>
							<a href="${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/shipping/order-detail/${orderItem.orderSequence}/${orderItem.orderCode}">${orderItem.orderCode}</a><br />
						</td>
						<td>
							${orderItem.userName}
							<c:if test="${not empty orderItem.loginId}">
								<p>[${orderItem.loginId}]</p>
							</c:if>
						</td>
						<td>
							${op:datetime(orderItem.shippingDate)}
							
							<!-- 배송 경과일 -->
							<c:if test="${orderItem.delayDay > 0}">
								<br/><strong style="color:red">(+${orderItem.delayDay})</strong>
							</c:if>
						</td> 
						<td>${orderItem.receiveName}</td>
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
							<p style="font-size: 11px; color: #333;font-weight: bold;">${orderItem.itemUserCode}</p>
							${orderItem.itemName}
							${ shop:viewOptionText(orderItem.options) }

							<c:set var="totalSaleAmount" value="${orderItem.saleAmount}" />
							<c:forEach items="${additionList}" var="addition">

								<c:if test="${orderItem.orderCode == addition.orderCode && orderItem.itemSequence == addition.parentItemSequence}">
									<c:set var="itemKey" value="${addition.orderCode}///${addition.orderSequence}///${addition.itemSequence}" />
									<c:set var="totalSaleAmount">${totalSaleAmount + addition.saleAmount}</c:set>

									<input type="checkbox" name="id" value="${itemKey}" class="addition-${addition.orderCode}" style="display:none"
										   data-parent-item-sequence="${addition.parentItemSequence}"
										   data-order-code="${addition.orderCode}"
										   data-addition-item-flag="${addition.additionItemFlag}" />

									추가구성품 : ${addition.itemName} ${addition.quantity}개 (+${op:numberFormat(addition.itemAmount)}원) <br />
								</c:if>
							</c:forEach>

							${ shop:viewOrderGiftItemList(orderItem.orderGiftItemList)}
						</td>
						<td class="text-right"><strong>${orderItem.quantity}개</strong></td>
						<td class="text-right"><strong>${op:numberFormat(totalSaleAmount)}원</strong></td>
						<td>
							<p>${orderItem.orderStatusLabel}</p>
							<p>
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
										${orderItem.deliveryCompanyName}<br /> (${orderItem.deliveryNumber})
									</c:otherwise>
								</c:choose>
							</p>
							<p><button type="button" class="btn btn-gradient btn-xs" onclick="Common.popup('${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/shipping/change-shipping-number/${orderItem.orderSequence}/${orderItem.itemSequence}/${orderItem.orderCode}', 'change_shipping_number', 530, 400, 1)">송장번호 수정</button></p>
						</td>
					</tr>
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
<daum:address />
<script type="text/javascript"> 
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		
		//운송장입력 취소/배송준비중 목록으로 보냄
		$('.cancel-delivery').on('click', function() { 
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}

			if (confirm("배송을 취소하시겠습니까?\n해당 주문은 배송준비중 목록으로 이동합니다.")) {
				Manager.Order.listUpdate('shipping', 'cancel-delivery');
			}
		});

		// 주상품 체크시 추가구성 상품 체크 처리
		$(':checkbox[name=id]').on('click', function () {
			var itemSequence = $(this).data('itemSequence');
			var orderCode = $(this).val().split('///')[0];

			$('input[name="id"][data-order-code="' + orderCode + '"][data-parent-item-sequence="'+ itemSequence +'"][data-addition-item-flag="Y"]').prop('checked', $(this).prop('checked'));
		});
	});
	
	function downloadOrderExcel(totalCount) {
		var $id = $('input[name="id"]:checked');
		var param = "";
		if ($id.size() > 0) {
			param = "?" + $id.serialize();
		} else{
            param = "?" + $('#orderParam').serialize();
        }

		if (totalCount > 500) {
			if ($id.size() == 0) {
				alert('주문 내역이 500건이 넘는경우 선택 다운로드 혹은 일자별 검색후 다운로드 하시기 바랍니다.');
				return;
			}
		}
		
		
		location.href = "${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/shipping/order-excel-download" + param;
	}
	
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>