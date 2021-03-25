<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<style type="text/css">
	.board_list_table th{
		text-align:center;
	}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3><span>입금대기 목록</span></h3>

<form:form modelAttribute="orderParam" action="${requestContext.requestUri}" method="get">
		
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
								<form:option value="BANK_IN_NAME" label="입금자 명의" />
							</form:select> 
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->
						</div>
				 	</td>
				 </tr>
				 <tr>
				 	<td class="label">입금지연 내역 조회</td>
				 	<td>
				 		<div>
				 			<form:checkbox path="searchDelayDay" label="입금 예정일이 지난 입금확인전 내역을 조회합니다." value="1" />
				 		</div>
				 	</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/order/waiting-deposit'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}<!-- 초기화 --></button>
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
			<button type="button" class="btn btn-default btn-sm confirm_payment">선택 입금확인</button>
			<button type="button" class="btn btn-default btn-sm cancel_payment">선택 취소</button>
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
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:10%;" />
				<col style="width:10%;" />
				<col />
				<col style="width:8%;" />
				<col style="width:10%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">No</th>
					<th scope="col">주문일시</th>
					<th scope="col">주문번호</th>
					<th scope="col">결제방법</th>
					<th scope="col">현금영수증</th>
					<th scope="col">입금 예정 금액</th>
					<th scope="col">주문자</th>
					<th scope="col">입금자 명의</th>
					<th scope="col">입금 계좌</th>
					<th scope="col">생성일</th>
					<th scope="col">입금 예정일</th>
				</tr>
		
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="payment" varStatus="index">
					<tr>
						<td>
							<!-- 결제 확인이 된후에는 선택 취소/선택 입금확인 불가능 -->
							<c:if test="${empty payment.payDate && payment.approvalType == 'bank'}">
								<input type="checkbox" name="id" value="${payment.paymentKey}" />
							</c:if>
						</td>
						<td>${op:numberFormat(pagination.itemNumber - index.count)}</td>
						<td>${ op:datetime(payment.orderDate) }</td> 
						<td><a href="/opmanager/order/waiting-deposit/order-detail/${payment.orderSequence}/${payment.orderCode}">${payment.orderCode}</a></td>
						<td>${payment.approvalTypeLabel}</td>
						<td>
                            <c:choose>
                                <c:when test="${payment.cashbill.cashbillType.title != null}">
                                    ${payment.cashbill.cashbillType.title}
                                </c:when>
                                <c:otherwise>
                                    발급안함
                                </c:otherwise>
                            </c:choose>
						</td>
						<td>${op:numberFormat(payment.amount)}원</td>
						<td>
							${payment.buyerName}
							<c:if test="${not empty payment.loginId}">
								<p>[${payment.loginId}]</p>
							</c:if>
						</td>
						<td>${payment.bankInName}</td>
						<td>${payment.bankVirtualNo}</td>
						<td>${op:datetime(payment.createdDate)}</td>
						<td>
							${payment.approvalType == 'bank' ? payment.bankDate : op:date(payment.bankDate)}
							<c:if test="${payment.delayDay > 0}">
								<strong style="color:red">(+${payment.delayDay})</strong>
							</c:if>
							<c:if test="${not empty payment.payDate && payment.approvalType == 'bank' && payment.paymentVerificationCancel}">
								<button type="button" class="btn btn-active btn-xs" onclick="confirmCancel('${payment.paymentKey}')">입금확인 취소</button>
							</c:if> 
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
			<button type="button" class="btn btn-default btn-sm confirm_payment">선택 입금확인</button>
			<button type="button" class="btn btn-default btn-sm cancel_payment">선택 취소</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 
	 
	<page:pagination-manager /> 
	 
</div> 

<div class="board_guide ml10">
	<p class="tip">Tip</p>
	<p class="tip">입금 대기건을 취소하시면 상태를 되돌릴수 없습니다.</p> 
	<p class="tip">입금확인 처리후 당일 처리건에 한해 주문 상품이 <strong>"신규주문"</strong> 상태인 건을 <strong>"입금확인 취소"</strong>할수 있습니다.</p>
	<p class="tip">입금확인 처리후 당일 처리건에 한해 <strong>"주문정보 보기"</strong>를 확인할수 있습니다.</p> 
</div>
<script type="text/javascript"> 
	$(function(){
		
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
		
		$('.confirm_payment').on('click', function() { 
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			 
			if (confirm("선택하신 입금 대기건을 입금확인 처리 하시겠습니까?")) {
				Manager.Order.listUpdate('waiting-deposit', 'confirm');
			}
		});
		
		$('.cancel_payment').on('click', function() {

			alert('처리중입니다.');
			return;

			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return;
			}
			
			if (confirm("무통장 입금 대기건을 취소하시면 되돌릴수 없습니다.\n선택하신 입금 대기건을 취소 하시겠습니까?")) {
				Manager.Order.listUpdate('waiting-deposit', 'cancel');
			}
		});
		
		
	});
	
	// 입금확인 취소
	function confirmCancel(key) {
		if (confirm('입금확인 취소처리 하시겠습니까?')) {
			$.post('/opmanager/order/waiting-deposit/payment-verification-cancel', {'key' : key}, function(response){
				Common.responseHandler(response, function(response) {
					alert('입금확인 취소처리 되었습니다.');
					location.reload();
				}, function(response){
					alert(response.errorMessage);
				});
			});
		}
	}
	
	function downloadOrderExcel(totalCount) {
		var $id = $('input[name="id"]:checked');
		var param = "";

		if ($id.size() > 0) {
			param = "?" + $id.serialize();
		} else {
			param = "?" + $('#orderParam').serialize();
		}
		
		/*if (searchOrderStatus == '') {
			
			if ($id.size() == 0) {
				alert('다운받으실 주문을 선택해 주세요.');
				return;
			}
			searchOrderStatus = 'all';
		} else {*/
			if (totalCount > 500) {
				if ($id.size() == 0) {
					alert('주문 내역이 500건이 넘는경우 선택 다운로드 혹은 일자별 검색후 다운로드 하시기 바랍니다.');
					return;
				}
			}
		/*}*/

		location.href = '/opmanager/order/waiting-deposit/order-excel-download' + param;
	}
	
	
	
</script>