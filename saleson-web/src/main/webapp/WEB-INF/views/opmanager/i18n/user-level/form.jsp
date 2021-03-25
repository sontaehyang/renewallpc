<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


<h3><span>${op:message('M00267')}</span></h3>

<form:form modelAttribute="userLevel" action="${ requestContext.opmanagerUri }/user-level/${formAction}" method="post" enctype="multipart/form-data">
	<form:hidden path="groupCode" />
	<form:hidden path="levelId" />
	<div class="board_write">
		<table class="board_write_table" summary="신규회원등록에 관련한 정보를 입력하는 칸입니다.">
			<caption>신규회원등록</caption>
			<colgroup>
				<col style="width:15%;" />
				<col style="width:85%;" />
			</colgroup>
			<tbody>
				<tr>
					<th class="label">${op:message('M00262')}</th>
					<td>	
						<div> 
						 	<c:choose>
				 				<c:when test="${!empty userLevel.fileName}">
						 			<img alt="${userLevel.fileName}" src="/upload/user_level/${userLevel.levelId}/${userLevel.fileName}" style="width: 60px;" />
		 							<a href="javascript:fileDelete('${userLevel.levelId}');">${op:message('M00074')}</a>
		 							<input type="file" name="uploadfile[]" id="file1" title="${op:message('M00268')}" style="display: none;" />
	 							</c:when>
	 							<c:otherwise>
						 			<input type="file" name="uploadfile[]" id="file1" title="${op:message('M00268')}" />
								</c:otherwise>
					 		</c:choose>
					 	</div>
					</td>
				</tr>
				<tr>
					<th class="label">등급 적용 Level</th>
					<td>
						<div>
							<form:input path="depth" cssClass="required _number" title="등급 적용 Level" maxlength="4" />
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">${op:message('M00263')}</th>
					<td>
						<div>
							<form:input path="levelName" cssClass="required half" title="${op:message('M00263')}" />
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">구매금액</th>
					<td>
						<div>
							<form:input path="priceStart" cssClass="required _number" title="구매금액(시작)" maxlength="9" />이상 ~
							<form:input path="priceEnd" cssClass="required _number" title="구매금액(끝)" maxlength="9" />미만
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">구매시 추가 적립 포인트</th>
					<td>
						<div>
							<form:input path="pointRate" cssClass="required" title="구매시 추가 적립 포인트" maxlength="3" />%
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">구매시 추가 할인율</th>
					<td>
						<div>
							<form:input path="discountRate" cssClass="required" title="구매시 추가 할인율" maxlength="3" />%
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">배송비 쿠폰 발행수(월별)</th>
					<td>
						<div>
							<form:input path="shippingCouponCount" cssClass="required _number" title="배송비 쿠폰 발행수(월별)" maxlength="3" />장
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">등급 심사 주기</th>
					<td>
						<div>
							<form:input path="retentionPeriod" cssClass="required _number" title="등급 심사 주기" maxlength="3" />개월
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">매출 기준기간</th>
					<td>
						<div>
							<form:input path="referencePeriod" cssClass="required _number" title="매출 기준기간" maxlength="3" />개월
					 	</div>
					 </td>
				</tr>
				<tr>
					<th class="label">매출 제외 기준 일자</th>
					<td>
						<div>
							<form:input path="exceptReferencePeriod" cssClass="required _number" title="매출 제외 기준 일자" maxlength="3" />일
					 	</div>
					 </td>
				</tr>
			</tbody> 
		</table>				 
	</div><!--//board_write E-->	
		
	<div class="tex_c mt20">
		<button type="submit" class="btn btn-active">${op:message('M00101')}</button>
		<a href="/opmanager/user-level/list/${userLevel.groupCode}" class="btn btn-default">${op:message('M00037')}</a>
	</div>
</form:form>



<script type="text/javascript">
	$(function(){
		if ($("#levelCount").val() == 0) {
			$("#levelCount").val('');
		}
		$('#userLevel').validator(function() {});
	});

	function fileDelete(levelId){
		Common.confirm("${op:message('M00196')}", function() {
			$.post(url("/opmanager/user-level/file-delete/" + levelId), {}, function(response) {
				Common.responseHandler(response, function() {
					alert("${op:message('M00205')}");
					location.reload();
				});
			});
		});
	}
</script>

