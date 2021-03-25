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

<h3><span>${op:message('M00368')} <!-- 메일발송로그 리스트 --></span></h3>
<form:form modelAttribute="sendSmsLogParam" action="" method="post">
	<div class="board_write">
		<table class="board_write_table" summary="${op:message('M00368')}">
			<caption>${op:message('M00368')}</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:auto;" />
			</colgroup>
			<tbody>
				<tr>
					<td class="label">${op:message('M00372')} <!-- 발송모드 --></td>
					<td>
						<div>
							<select title="${op:message('M00372')}" class="w20" name="templateId">
								<option value="" <c:if test="${ sendSmsLogParam.templateId eq '' }">selected="selected"</c:if>>${op:message('M00039')}</option><!-- 전체 --> 
								<c:forEach items="${smsTemplateCodeList}" var="list">
									<option value="${ list.key }" <c:if test="${ sendSmsLogParam.templateId eq list.key }">selected="selected"</c:if>>${op:message(list.value)}</a></li>
								</c:forEach>
							</select> 
							 
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M00011')}</td>
					<td>
						<div>
							<form:select path="where" title="${op:message('M00011')}">
								<form:option value="RECEIVE_TEL_NUMBER" label="전화번호" /><!-- 수신자 이름 -->
								<form:option value="CONTENT" label="내용" /><!-- 메일제목 -->
							</form:select>
							<form:input path="query" class="input_txt required _filter" title="${op:message('M00022')}" maxlength="20" /><!-- 검색어 -->

							<span class="text-info text-sm" style="margin-left:20px">* 휴대폰번호 입력 시 <span style="font-size: 13px">-</span> 를 같이 입력해주세요. ex) 010-1234-5678</span>
						</div>
					</td>
				</tr>				 
				<tr>
					<td class="label">${op:message('M00373')} <!-- 발송기간 --></td>
					<td>
						<div>
							<span class="datepicker"><form:input path="searchStartDate" maxlength="8" class="datepicker" title="발송기간 시작일"/></span>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="searchEndDate" maxlength="8" class="datepicker" title="발송기간 종료일"/></span>
								<span class="day_btns"> 
								<a href="javascript:;" class="btn_date clear">전체</a>
								<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 --> 
								<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 --> 
								<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 --> 
								<a href="javascript:;" class="btn_date month-2">${op:message('M00223')}</a><!-- 두달 -->
								<a href="javascript:;" class="btn_date clear">${op:message('M00039')}</a><!-- 전체 -->
							</span>
						</div>  
 					</td>
				</tr>
			</tbody>
		</table>						 
	</div> <!--// board_write E-->
	
	<div class="btn_all">
		<div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/send-sms-log/list'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
		</div>
		<div class="btn_right">
			<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
		</div>
	</div><!--//btn_all E-->
			
	<div class="count_title mt20">
		<h5>
			${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
		</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
		<span>
			${op:message('M00052')} :  
			<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}" onchange="$('form#orderParam').submit();"> <!-- 화면 출력수 -->
				<form:option value="10" label="10${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="20" label="20${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
			</form:select>
		</span>
	</div>
</form:form>

<div class="board_write">
	<form id="listForm">
		<table class="board_list_table" summary="${op:message('M00371')}"><!-- 메일로그 리스트 --> 
			<caption>${op:message('M00371')}</caption>
			<colgroup>
				<col style="width:10%;" />
				<col style="width:10%;" />
				<col style="width:15%;" />	
				<col style="width:*" />					
				<col style="width:15%;" />
				</colgroup>
			<thead>
				<tr>
					<th scope="col">${op:message('M00200')} <!-- 순번 --></th>
					<th scope="col">회원아이디</th>
					<th scope="col">전화번호</th>
					<th scope="col">내용</th>
					<th scope="col">${op:message('M00376')} <!-- 발송일 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="log" varStatus="i">
				
					<tr>
						<td>${ op:numberFormat(pagination.itemNumber - i.count) }</td>
						<td>${ log.userLoginId }</td>
						<td>${ log.receiveTelNumber }</td>
						<td class="text-left">${ log.content }</td>
						<td>${ op:datetime(log.createdDate) }</td>							
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
</div>

<div class="btn_all">
	<div class="btn_left mb0">
	</div>	
</div>

<page:pagination-manager />

<script type="text/javascript">
$(function() {
	
	// 목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/send-sms-log/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	
});
</script>