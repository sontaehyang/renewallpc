<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<style>
.phone_number {width: 45px;}
.adressInput 
{
	margin-left:25px;
	width:300px;
} 
.shopCheck{
	margin-left:25px;
}
.shopLocation{
	width:100px;
}

#styleContents{
	height:250px;
	width:100px;
}

#stylePoint{
	width:100px;
}
</style>
			<h3><span>카드혜택 등록</span></h3>
			
			<form:form modelAttribute="cardBenefits" method="post" enctype="multipart/form-data">
				<input type="hidden" id="modeType" name="modeType" value="${mode }" />
				<input type="hidden" id="benefitsId" name="benefitsId" value="${cardBenefits.benefitsId }" />
				<div class="board_write">
					<table class="board_write_table" summary="카드혜택 등록에 관련한 정보를 입력하는 칸입니다.">
						<caption>신규 카드혜택 등록</caption>
						<colgroup>
							<col style="width:15%;" />
							<col style="width:85%;" />
						</colgroup>
						<tbody>
							<tr>

								<c:set var="requiredStar"><span class="require">*</span></c:set>
			 					<td class="label">카드혜택 제목 ${requiredStar}</td> <!-- 점포명 -->
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<c:choose>
			 								<c:when test="${cardBenefits.subject eq '' || empty cardBenefits.subject} ">
			 									<form:input id="subject" path="subject" title="카드혜택 제목" cssClass="two required" maxlength="100" />
			 								</c:when>
			 								<c:otherwise>
			 									<form:input id="subject" path="subject" title="카드혜택 제목" cssClass="two required" maxlength="100" value="${cardBenefits.subject}" />
			 								</c:otherwise>
				 						</c:choose>
				 						
			 						</div>
			 					</td>
			 				
		 					</tr>
		 					<tr>
		 						<c:set var="requiredStar"><span class="require">*</span></c:set>
			 					<td class="label">노출 기간 지정 ${requiredStar}</td> <!-- 노출 기간 -->
			 					<td>
			 						<div class="input_wrap col-w-7">
			 							<span class="datepicker">시작일 <form:input type="text" id="startDate" path="startDate" class="datepicker _number required" title="${op:message('M00507')}" /></span> <!-- 시작일 --> 
										<span class="wave">~</span>
										<span class="datepicker">종료일 <form:input type="text" id="endDate" path="endDate" class="datepicker _number required" title="${op:message('M00509')}" /></span> <!-- 종료일 --> 
				 						
			 						</div>
			 					</td>
		 					</tr>
		 					<tr>
								<c:set var="required" value="required" />
								<c:set var="requiredStar"><span class="require">*</span></c:set>
			 					<td class="label">내용${requiredStar}</td> <!--내용 -->
			 					<td>
			 						<div class="">
										<form:textarea path="content" cols="30" rows="20" style="width: 1085px" class="" title="내용" />
									</div>
			 					</td>
			 				</tr>
		 			
		 				</tbody>
					</table>				 
				</div> <!--// board_write E-->
				
				<div class="btn_center" style="margin-right:200px;">
					 <c:if test="${mode != 1 }">
						<button type="submit" class="btn btn-active">${op:message('M00101')} <!-- 저장 --></button>	
					</c:if>
					<c:if test="${mode == 1 }">
						<button type="submit" class="btn btn-active">수정 <!-- 수정 --></button>	
					</c:if> 
					<a href="javascript:Link.list('/opmanager/card-benefits/list')" class="btn btn-default">취소<!-- 취소 --></a>
	
				</div>
			</form:form>

<module:smarteditorInit />
<module:smarteditor id="content" />		

<script type="text/javascript">

	$(function() {
		
		// 등록, 수정
		$('#cardBenefits').validator(function() {
			
		 	var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			
			if(startDate > endDate ) {
				alert("시작일이 종료일보다 클 수 없습니다.");
				return false;
			} 
			
			if (!checkPeriod()) {
				return false;
			}
			
			Common.getEditorContent("content");
			
			var mode = $("#modeType").val();
			if(mode == 1){						// 수정인지 등록인지 검사.
				$('#cardBenefits').attr('action', '/opmanager/card-benefits/edit');
			} else {
				$('#cardBenefits').attr('action', '/opmanager/card-benefits/insert');
			}
		});
		
	});
	
	
function checkPeriod() {
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	var benefitsId = $("#benefitsId").val();
	
	var param = {"startDate" : startDate, "endDate" : endDate, "benefitsId" : benefitsId};
	var result = false;
	$.get("/opmanager/card-benefits/check-period", param, function(response){
		Common.responseHandler(response, function(response) {
			result = true;
		}, function(response){
			alert(response.errorMessage);
		});
	});
	return result;
}

</script>			
			
