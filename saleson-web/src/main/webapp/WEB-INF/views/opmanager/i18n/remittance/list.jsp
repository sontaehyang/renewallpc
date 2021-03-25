<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3>정산관리</h3>

<form:form modelAttribute="remittanceParam" action="" method="get">
		
	<div class="board_write">
	
		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				<tr>  
				 	<td class="label">정산일자</td><!-- 정산일자 --> 
				 	<td> 
				 		<div>
				 			<span class="datepicker"><form:input path="startDate" class="datepicker" maxlength="8" title="정산일자 시작일" /><!-- 정산일자 시작일 --></span>
							<span class="wave">~</span>							
							<span class="datepicker"><form:input path="endDate" class="datepicker" maxlength="8" title="정산일자 종료일" /><!-- 정산일자 종료일 --></span>
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
				 	<td class="label">판매자</td>
					<td>
						<div>
							<select name="sellerId" title="${op:message('M01630')}"> <!-- 판매자선택 --> 
								<option value="0">${op:message('M00039')}</option> <!-- 전체 --> 
								<c:forEach items="${sellerList}" var="list" varStatus="i">
								<c:choose>
									<c:when test="${remittanceParam.sellerId == list.sellerId}">
										<c:set var='selected' value='selected'/>
									</c:when>
									<c:otherwise>
										<c:set var='selected' value=''/>
									</c:otherwise>
								</c:choose>
								<option value="${list.sellerId}" ${selected}>${list.sellerName}</option>
								</c:forEach>
							</select>
						</div>
						
					</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/remittance/list'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
			</div>
		</div>
	</div>
	
</form:form>

<div class="board_list">
	<form id="listForm">
	<table class="board_list_table" summary="정산내역 리스트">
			<caption>정산내역 리스트</caption>
			<colgroup>
				<col style="width:2%;" />
				<col style="width:10%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:10%;" />
				<col style="width:10%;" />
				<col style="width:10%;" />
				<col />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
				<col style="width:8%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" /></th>
					<th scope="col">정산일</th>
					<th scope="col">Seller</th>
					<th scope="col">이름</th>
					<th scope="col">연락처</th>
					<th scope="col">휴대폰</th>
					<th scope="col">업체명</th>
					<th scope="col">계좌번호</th>
					<th scope="col">공급가</th>
					<th scope="col">배송비</th>
					<th scope="col">${op:message('M00246')} 발행</th>
					<th scope="col">정산금액</th>
					<th scope="col">확인된 정산금액</th>
					<th scope="col">확인여부</th>
					<th scope="col">확인자</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="remittance" varStatus="index">
				<c:set var="confirmAmount">${remittance.supplyPrice + remittance.payShipping - remittance.sellerPoint }</c:set>
				<tr>
					<td>
						<!-- 정산 확인이 된후에는 선택불가 -->
						<c:if test="${remittance.confirmFlag == 'N'}">
							<input type="checkbox" name="id" value="${ remittance.remittanceId }" />
							<input type="hidden" name="confirmAmount" value="${ confirmAmount }" />
						</c:if>
						</td>
					<td>
						<a href="javascript:Common.popup('/opmanager/remittance/detail?sellerId=${remittance.sellerId}&remittanceDate=${remittance.remittanceDate}', 'remittanceDetail', 1500, 700, 1);">${op:date(remittance.remittanceDate) }</a>
					</td>
					<td>${remittance.sellerName }</td>
					<td>${remittance.userName }</td>
					<td>${remittance.phoneNumber }</td>
					<td>${remittance.telephoneNumber }</td>
					<td>${remittance.companyName }</td>
					<td>
						<c:if test="${remittance.bankAccountNumber !='' && remittance.bankAccountNumber != null}">
							(${remittance.bankName }) ${remittance.bankAccountNumber }
						</c:if>
					</td>
					<td>${op:numberFormat(remittance.supplyPrice) }원</td>
					<td>${op:numberFormat(remittance.payShipping) }원</td>
					<td>${op:numberFormat(remittance.sellerPoint) }P</td>
					<td>${op:numberFormat(confirmAmount)}원</td>
					<td>${op:numberFormat(remittance.confirmAmount) }원</td>
					<td>
						<c:choose>
							<c:when test="${remittance.confirmFlag == 'Y'}">확인</c:when>
							<c:otherwise>미확인</c:otherwise>
						</c:choose> 
					</td>
					<td>${remittance.confirmManagerName }</td>
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
			<button type="button" class="btn btn-default btn-sm confirm_remittance">선택 정산확인</button>
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div> 
			
</div>

<script type="text/javascript"> 
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
		//mergeCell();
	});
	
	$('.confirm_remittance').on('click', function() { 
		
		var $form = $('#listForm');
		if ($form.find('input[name=id]:checked').size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}
		 
		if (confirm("선택하신 정산항목을 확인하시겠습니까?")) {
			
			var $checkboxs = $('#listForm input:checkbox[name=id]');
			var $confirmAmounts =  $('#listForm input[name=confirmAmount]');
			$checkboxs.each(function(index){
				
				if ($checkboxs.eq(index).is(':checked') == false) {
					$checkboxs.eq(index).prop('disabled', true);
					$confirmAmounts.eq(index).prop('disabled', true);
				} else {
					$checkboxs.eq(index).prop('disabled', false);
					$confirmAmounts.eq(index).prop('disabled', false);
				}
				
			});
			
			
			$.post("/opmanager/remittance/remittance-confirm", $("#listForm").serialize(), function(response){
				Common.responseHandler(response, function(response) {
					location.reload();
				}, function(response){
					alert(response.errorMessage);
				});
			});
			
		}
	});
	
	
	function mergeCell() {
		
		var first = true;
		var prevRowspan1 = 1;
		var prevCell1 = null;
		// tr 추출
		var rows = $("div.board_list table.board_list_table > tbody").children();
		
		for ( var i =0 ; i < rows.length; i++) {
			
			if (first) {
				prevRow = rows[i];
				prevCell1 = $(prevRow).find("td").eq(0); // 정산일
				
				first = false;
				continue;
			}
			
			var row = rows[i];
			var tdList = $(row).find("td");
			
			var firstCell = $(tdList).eq(0);
			
			var firstCellText = $(firstCell).text();
			
			// 두 번째 row 부터 텍스트 비교
			if (prevCell1.text() == firstCellText ) {
				prevRowspan1++;
				$(prevCell1).attr("rowspan", prevRowspan1);
				$(firstCell).remove();
			} else {
				prevRowspan1 = 1;
				prevCell1 = firstCell;
			}
		}
		
	}
</script>



