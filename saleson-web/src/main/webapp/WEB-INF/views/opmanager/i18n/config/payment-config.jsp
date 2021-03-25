<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
					
		<form:form modelAttribute="config" method="post">
		<h3><span>기본 ${op:message('M00137')}</span></h3>
		<div class="board_write">
			<table class="board_write_table" summary="온라인 입금 사용 여부">
				<caption>온라인 입금 사용 여부</caption>
				<colgroup>
					<col style="width:150px;" />
					<col style="" />
				</colgroup>
				<tbody>				 
					 <tr>
					 	<td class="label">${op:message('M00038')} </td>
					 	<td>
					 		<div>
					 			<p class="text-info text-sm">* PG결제는 신청하신 결제 서비스만 노출됩니다.</p>
					 		 	<form:checkbox path="paymentBank" value="Y" label="무통장 입금" />
					 		 	<form:checkbox path="paymentCard" value="Y" label="PG 결제 (카드)" />
					 		 	<form:checkbox path="paymentVbank" value="Y" label="PG 결제 (가상계좌)" />
					 		 	<form:checkbox path="paymentEscrow" value="Y" label="PG 결제 (에스크로)" />
					 		 	<form:checkbox path="paymentRealtime" value="Y" label="PG 결제 (실시간 계좌이체)" />
					 		 	<form:checkbox path="paymentHp" value="Y" label="PG 결제 (휴대폰)" />
								<form:checkbox path="naverPayFlag" value="Y" label="네이버페이" />
					 		 	<form:hidden path="paymentDlv" /> 
					 			<form:hidden path="paymentId" value="Veritrans"/>       
							</div>
					 	</td>	 
					 </tr>	
					 <tr>
					 	<td class="label">${op:message('M00539')} <!-- 최소 결재 금액 --></td>
					 	<td>
					 		<div>
								 <form:input path="minimumPaymentAmount" cssClass="form-amount text-right _number" title="${op:message('M00539')}" maxlength="6" /> ${op:message('M00049')} <!-- 원 -->
							</div> 
					 	</td>	
					 </tr>
				</tbody>					 
			</table>								 							
		</div> <!-- // board_write -->

		<h3 class="mt50"><span>은행 입금 기한 설정</span></h3>
		<div class="board_write">
			<table class="board_write_table" summary="은행 입금 기한 설정">
				<caption>은행 입금 기한 설정</caption>
				<colgroup>
					<col style="width:150px;" />
					<col style="" />
				</colgroup>
				<tbody>
				<tr>
					<td class="label">은행 입금 만료일 설정</td>
					<td>
						<div>
							<form:input path="bankDepositDueDay" cssClass="form-sm text-center _number" maxlength="2" />일
						</div>
					</td>
				</tr>
				</tbody>
			</table>
		</div> <!-- // board_write -->

		<h3 class="mt50"><span>구매확정 정보 설정</span></h3>
		<div class="board_write">
			<table class="board_write_table" summary="구매확정 정보 설정">
				<caption>구매확정 정보 설정</caption>
				<colgroup>
					<col style="width:150px;" />
					<col style="" />
				</colgroup>
				<tbody>	
					<%-- <tr>
						<td class="label">${op:message('M00138')} </td>
					 	<td>
					 		<div>
					 			<form:select path="paymentId" title="${op:message('M00138')} ">
					 				<form:option value="">${op:message('M00139')}</form:option>
					 				<form:option value="Veritrans">Veritrans</form:option>
					 			</form:select>
							</div>
					 	</td>	
					</tr> --%>
					<tr>
						<td class="label">구매확정 요청 메시지 발송일 설정</td>
						<td>
							<div>
								<p class="text-info text-sm">* 매일 오후 12시 30분(12:30)에 자동실행 됩니다.</p>
								배송중 상태값 변경일로부터 <form:input path="confirmPurchaseRequestDate" cssClass="form-sm text-center _number" maxlength="2" />일 이후 적용
							</div>
						</td>
					</tr>
					<tr>
					 	<td class="label">구매확정일 설정</td>
					 	<td>
					 		<div>                    
					 			<p class="text-info text-sm">* 매일 저녁 11시(23:00)에 자동실행 됩니다.</p>
					 			배송중 상태값 변경일로부터 <form:input path="confirmPurchaseDate" cssClass="form-sm text-center _number" maxlength="2" />일 이후 적용
							</div>
					 	</td>	
					</tr>
				</tbody>
			</table>								 				 			
		</div> <!-- // board_write -->

		<%-- 
		<h3 class="mt50"><span>Veritrans ${op:message('M00144')}</span></h3>			
		<div class="board_write">
			<table class="board_write_table" summary="Veritrans ${op:message('M00144')}">
				<caption>Veritrans ${op:message('M00144')}</caption>
				<colgroup>
					<col style="width:10%;" />
					<col style="width:90%;" />
				</colgroup>
				<tbody>				 
					 <tr>
					 	<td class="label">${op:message('M00042')}</td>
					 	<td>
					 		<div>
								 ${op:message('M00142')} <form:input path="paymentConvLimit" cssClass="one _number" title="${op:message('M00142')} ${op:message('M00143')}" maxlength="3" /> ${op:message('M00511')} <!-- 일 -->
							</div> 
					 	</td>	
					 </tr>
					 
				</tbody>					 
			</table>								 							
		</div> <!-- // board_write -->
		 --%>
		<!-- 버튼시작 -->		 
		<div class="tex_c mt20">
			<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button>				 
		</div>			 
		<!-- 버튼 끝-->
		
		</form:form>
		
		
<script type="text/javascript">
$(function() {
	// validator
	$('#config').validator(function() {
		
		
		var minimumPaymentAmount = Number($('input[name="minimumPaymentAmount"]').val());
		/* if (minimumPaymentAmount < 1000) {
			alert('최소 결제 금액은 1,000원 이상 입력하셔야 합니다.');
			$('input[name="minimumPaymentAmount"]').val('1000');
			return false;
		} */
		
	});
});

</script>	