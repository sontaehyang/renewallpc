<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

	<h3><span>기부목표 관리</span></h3>
	<div class="board_list">
		<div class="sort_area">
			<form:form modelAttribute="proposalTarget" method="POST">
				<c:set var="targetCount" value="0" />
				<fieldset>
					<legend class="hidden">기부목표 설정</legend>
					<div class="set_objective">
						<c:forEach items="${ list }" var="list" varStatus="i">
							<c:if test="${ year eq list.targetYear }">
								<span class="label">${ list.targetYear }년도</span> 
								<input type="text" name="amount" id="amount" value=""  maxlength="15" class="required _filter"" title="목표금액" />
								<form:input path="targetAmount" title="목표금액" cssClass="hidden" />
 								<form:input path="targetStatus" value="update" cssClass="hidden" />
								<form:input path="targetYear" value="${ year }" cssClass="hidden" />
								<c:set var="targetCount" value="1" />
							</c:if>
						</c:forEach>
						
						<c:if test="${ empty list || targetCount == 0 }">
							<span class="label">${ year }년도</span>
							<input type="text" name="amount" id="amount" value="" maxlength="9" class="required _filter" title="목표금액" />
							<form:input path="targetAmount" title="목표금액" cssClass="hidden" />
							<form:input path="targetStatus" value="insert" cssClass="hidden" />
							<form:input path="targetYear" value="${ year }" cssClass="hidden" />
						</c:if>
						<button type="submit" class="btn_write">반영</button>
					</div>
				</fieldset>
			</form:form>
			
		</div>
		<table class="board_list_table">
			<colgroup>
				<col style="width: 150px;"/>
			</colgroup>
			<thead>
				<tr>
					<th>년도</th>
					<th>목표액</th>
					<th>달성금액</th>
					<th>달성율</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="list" varStatus="i">
					<tr>
						<td>${ list.targetYear }</td>
						<td><fmt:formatNumber value="${ list.targetAmount }" pattern="#,###" /></td>
						<td><fmt:formatNumber value="${ list.donationAmount }" pattern="#,###" /></td>
						<td><fmt:formatNumber value="${list.donationParticipationRate}" pattern="###,###.##" />%</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<p class="btns">
			<a href="/opmanager/proposal/target-download-excel" class="btn orange">엑셀파일 다운로드</a>
		</p>
	</div>
	
	<script type="text/javascript">
		$(function(){
			
			$("#amount").on("keyup",function(){

				var amount = parseInt($(this).val().replace(/,/g, ''));
				
				$("#targetAmount").val(amount);
				$(this).val(Common.comma(amount));
			});

			$('#proposalTarget').validator(function() {

				if ('${op:hasRole("ROLE_KB_SUPERVISOR")}' == 'false' ) {
					alert('기부목표 관리는 총괄 관리자만 반영 할 수 있습니다.');
					return false;
				}

				$("#targetAmount").val($("#amount").val().replace(/,/g, ''));

				if($("#targetAmount").val() < 1){
					alert('목표금액 설정시 1원 이상 입력하여 주시기 바랍니다. ');
					return false;
				}
				
				if (confirm("반영하시겠습니까?")) {
					$.post('/opmanager/proposal/target', $("#proposalTarget").serialize(), function(response) {
						if (response.isSuccess) {
							alert('반영 되었습니다. ');
							location.reload();
						} else {
							alert(response.errorMessage);
						}
					});
					return false;
				}else{  return false; }
				
			});
			
		});
	</script>
	