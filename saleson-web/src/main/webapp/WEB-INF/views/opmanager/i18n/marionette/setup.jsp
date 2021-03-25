<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<h3><span>안전 인형극 기간 설정</span></h3>
<div class="board_list">				 
	 
	<div class="board_view">
		 <form:form modelAttribute="marionetteTerm" action="/opmanager/marionetteSet/write" method="post">
			<div class="info" style="border-bottom:none;">
				<table class="detail">
					<colgroup>
						<col style="width:80px;">
						<col style="width:460px;">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">인형극 신청기간</td>
							<td>
								<div>
									<span class="datepicker"><form:input path="startDate" cssClass="required _number term" title="인형극신청 시작날짜" maxlength="8" /></span> 
									<span class="wave">~</span> 
									<span class="datepicker"><form:input path="endDate" cssClass="required _number term" title="인형극신청 종료날짜" maxlength="8" /></span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>		
				<p class="btns">	
					<button type="submit" class="btn orange">등록</button>					
				</p>				
			</div><!--//info-->
		</form:form>
		
		<!--게시판 리스트 시작-->
		<table class="board_list_table">
			<colgroup>
				<col style="width:5%;">
				<col style="width:*">
				<col style="width:15%;">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>설정기간</th>
					<th>삭제여부</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="rs" varStatus="i">
					<tr>
						<td>${pagination.itemNumber - i.count}</td>
						<td class="tleft">
							${fn:substring(rs.startDate, 0, 4)}-${fn:substring(rs.startDate, 4, 6)}-${fn:substring(rs.startDate, 6, 8)}
							 ~ 
							${fn:substring(rs.endDate, 0, 4)}-${fn:substring(rs.endDate, 4, 6)}-${fn:substring(rs.endDate, 6, 8)} 
						</td>
						<td>
							<c:if test="${i.count == 1}">
								<span class="txt_geern"><a href="javascript:;" onClick="fn_delete(${rs.marionetteTermId});">[삭제]</a></span>
							</c:if>
						</td>
					</tr>							 
				</c:forEach>
			</tbody>
		</table>
		<!--// 게시판 리스트 끝--> 
		 
	
		<div class="board_guide">
			<p class="total">전체 : <em>${count}</em></p>
		</div>
 
	</div><!--//board_view E-->
 
</div><!--//board_list E-->
<page:pagination-manager />

<form id="deleteForm" method="post" action="/opmanager/marionetteSet/delete">
	<input type="hidden" name="marionetteTermId" id="deleteTermId" value="" />
</form>

<script type="text/javascript">

$(function(){
	// validator
	$('#marionetteTerm').validator(function() {
		
		if($("#startDate").val() <= '${maxTerm}'){
			alert("시작일이 등록된 기간의 종료일보다 커야합니다.");
			return false;
		}
		
		if($("#startDate").val() > $("#endDate").val()){
			alert("시작일이 종료일보다 클수 없습니다.");
			return false;
		}
	});
});

function fn_delete(no){
	if(confirm("인형극신청기간을 삭제하시겠습니까?")){
		$("#deleteTermId").val(no);
		$("#deleteForm").submit();
	}
}

</script>

