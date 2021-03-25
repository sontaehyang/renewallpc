<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
		
<div class="location">
	<a href="#"></a>&gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>					

<h3>거래처 관리</h3>
	
<form:form modelAttribute="customer" method="post">
	<form:hidden path="userId" />
	<div class="board_write">
		<table class="board_write_table" summary="거래처 관리"> 
			<caption>거래처 관리</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:*;" />
			</colgroup>
			<tbody>
				 <tr>
				 	<div>
						<td class="label">거래처코드
							<span class="require">*</span>
						</td>
						<td>
							<div>
				 				<form:input path="customerCode" title="거래처코드" class="required full" maxlength="10" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>
				 	<div>
				 		<td class="label">거래처명
				 			<span class="require">*</span>
				 		</td>
				 		<td>
				 			<div>
				 				<form:input path="customerName" title="거래처명" class="required full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">거래처분류
				 			<span class="require">*</span>
				 		</td>
				 		<td>
				 			<div>
				 				<form:input path="customerGroup" title="거래처분류" class="required full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">거래처구분</td>
				 		<td>
				 			<div>
				 				<form:input path="customerType" title="거래처구분" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				  <tr>		
				 	<div>
				 		<td class="label">사업자번호</td>
				 		<td>
				 			<div>
				 				<form:input path="businessNumber" title="사업자번호"  class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				  <tr>		
				 	<div>
				 		<td class="label">전화번호
				 			<span class="require">*</span>
				 		</td>	
				 		<td>
				 			<div>
				 				<form:input path="telNumber" title="전화번호" class="required full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				  <tr>		
				 	<div>
				 		<td class="label">대표자 성명</td>
				 		<td>
				 			<div>
				 				<form:input path="bossName" title="대표자 성명" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">업태</td>
				 		<td>
				 			<div>
				 				<form:input path="category" title="업태" class="full" maxlength="50" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">종목</td>
				 		<td>
				 			<div>
				 				<form:input path="event" title="종목" class="full" maxlength="50" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 
				 <tr>		
				 	<div>
				 		<td class="label">주소</td>
				 		<td>
				 			<div>
				 				<form:input path="zipcode" title="우편번호" maxlength="7" /> <br/>
				 				<form:input path="address" title="주소" class="full" maxlength="100" /> <br/>
				 				<form:input path="addressDetail" title="상세주소" class="full" maxlength="255" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">메모</td>
				 		<td>
				 			<div>
				 				<form:textarea path="memo" title="메모" class="full" maxlength="255" onkeyup="return textarea_maxlength(this)" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">담당자명</td>
				 		<td>
				 			<div>
				 				<form:input path="staffName" title="담당자명" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">담당자 부서명</td>
				 		<td>
				 			<div>
				 				<form:input path="staffDepartment" title="담당자 부서명" class="full" maxlength="50" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">담당자 전화번호</td>
				 		<td>
				 			<div>
				 				<form:input path="staffTelNumber" title="담당자 전화번호" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">담당자 휴대전화</td>
				 		<td>
				 			<div>
				 				<form:input path="staffPhoneNumber" title="담당자 휴대전화" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">주계좌 번호</td>
				 		<td>
				 			<div>
				 				<form:input path="bankNumber" title="주계좌 번호" class="full" maxlength="50" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">주계좌 은행</td>
				 		<td>
				 			<div>
				 				<form:input path="bankName" title="주계좌 은행" class="full" maxlength="50" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">주계좌 예금주명</td>
				 		<td>
				 			<div>
				 				<form:input path="bankInName" title="주계좌 예금주명" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">주계좌 CMS 코드</td>
				 		<td>
				 			<div>
				 				<form:input path="bankCmsCode" title="주계좌 CMS 코드" class="full" maxlength="50" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">거래처 직원 성명</td>
				 		<td>
				 			<div>
				 				<form:input path="customerStaffName" title="거래처 직원 성명" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">거래처 직원 직위</td>
				 		<td>
				 			<div>
				 				<form:input path="customerStaffPosition" title="거래처 직원 직위" class="full" maxlength="50" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">거래처 직원 전화번호</td>
				 		<td>
				 			<div>
				 				<form:input path="customerStaffTelNumber" title="거래처 직원 전화번호" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">거래처 직원 휴대폰</td>
				 		<td>
				 			<div>
				 				<form:input path="customerStaffPhoneNumber" title="거래처 직원 휴대폰" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">거래처 직원 전자메일</td>
				 		<td>
				 			<div>
				 				<form:input path="customerStaffEmail" title="거래처 직원 전자메일" class="full" maxlength="255" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">DM우편번호</td>
				 		<td>
				 			<div>
				 				<form:input path="dmZipcode" title="DM우편번호" class="full" maxlength="7" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">DM주소</td>
				 		<td>
				 			<div>
				 				<form:input path="dmAddress" title="DM주소" class="full" maxlength="100" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">DM나머지 주소</td>
				 		<td>
				 			<div>
				 				<form:input path="dmAddressDetail" title="DM나머지 주소" class="full" maxlength="255" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
				 <tr>		
				 	<div>
				 		<td class="label">종 사업장 번호</td>
				 		<td>
				 			<div>
				 				<form:input path="businessNumberCode" title="종 사업장 번호" class="full" maxlength="30" />
				 			</div>
				 		</td>
				 	</div>
				 </tr>
			</tbody>						
		</table>
	</div>	
	<div class="btn_center">
		<div>
			<a href="/opmanager/customer/list" class="btn btn-default"><span>목록</span></a> <!-- 목록 -->
			<button type="submit" class="btn btn-active">${buttonName}</button> <!-- 저장 -->
		</div>
	</div>
</form:form>
		 
<script type="text/javascript">
$(function() {
	
	var orgCustomerCode = '${customer.customerCode}';
	var checkCustomerCode = "";
	$('#customer').validator(function() {
		var isSubmit = false;
		var customerCode = $('#customerCode').val();
		
		// 인증받은 값과 입력된 값이 같은경우 무시
		if (checkCustomerCode == customerCode) {
			isSubmit = true;
		}
		
		// 수정인경우 기존 입력한 코드값과 값이 같으면 무시
		if (orgCustomerCode == customerCode) {
			isSubmit = true; 
		}
		
		if (isSubmit == false) {
			$.post("/opmanager/customer/codeCheck/" + customerCode, null, function(response){
				if (response.isSuccess) {
					isSubmit = true;
					checkCustomerCode = customerCode;
				}
			}, "json");
			 
			if (isSubmit == false) {
				$('#customerCode').focus();
				alert('입력하신 코드[' + customerCode + ']는\n이미 등록되어있는 코드 입니다.');
				return false;
			}
		}
		
		var message = '${buttonName}';
		if (!confirm(message + "하시겠습니까?")) {
			return false;
		}
	});
	
	
});

function textarea_maxlength(obj) {
	var maxLength = parseInt(obj.getAttribute("maxlength"));
	if(obj.value.length > maxlength) {
		alert('글자수가' +(obj.value.length - 1) + '자 이내로 제한됩니다.');
		obj.value = obj.value.substring(0, maxLength);
	}
}
</script>	 	
