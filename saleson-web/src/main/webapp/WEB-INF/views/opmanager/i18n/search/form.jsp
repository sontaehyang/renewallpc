<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
	
	<div class="item_list">
		<h3 class="mt20"><span>추천검색어 관리</span></h3>
		
		<form:form modelAttribute="search" method="post">
			<form:hidden path="searchId" />
			<form:hidden path="searchStartDate" />
			<form:hidden path="searchEndDate" />
			<div class="board_write">						
				<table class="board_write_table" summary="추천검색어 관리">
					<caption>추천검색어 관리</caption>
					<colgroup>
						<col style="width: 150px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">검색어 문구<span class="require">*</span></td>
							<td>
								<div>
									<form:input path="searchContents" title="검색어 문구" class="required requirement" style="width : 73%;" maxlength="200" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">검색어 링크<span class="require">*</span></td>
							<td>
								<div>
									<p class="text-info text-sm">
										※ 외부 사이트로 링크할 경우 http://를 반드시 작성해 주세요.
									</p>
						 			<form:input path="searchLink" class="three optional _filter" maxlength="200" title="검색어 링크" /> &nbsp;&nbsp;
						 			<form:checkbox path="searchLinkTargetFlag" label="${op:message('M01035')}" value="Y" /> &nbsp;&nbsp;
					 				<input type="hidden" name="!searchLinkTargetFlag" value="N" />
					 			</div>
			 				</td>
						</tr>
						<tr>
							<td class="label">검색어 링크 (모바일)<span class="require">*</span></td>
							<td>
								<div>
									<p class="text-info text-sm">
										※ 외부 사이트로 링크할 경우 http://를 반드시 작성해 주세요.
									</p>
						 			<form:input path="searchMobileLink" class="three optional _filter" maxlength="200" title="검색어 링크(모바일)" /> &nbsp;&nbsp;
						 			<form:checkbox path="searchMobileLinkTargetFlag" label="${op:message('M01035')}" value="Y" /> &nbsp;&nbsp;
					 				<input type="hidden" name="!searchMobileLinkTargetFlag" value="N" />
					 			</div>
			 				</td>
						</tr>
						<tr>
							<td class="label">기간설정</td>
							<td>
								<div>
									<span class="datepicker"><form:input path="startDate" maxlength="8" class="required _number datepicker" title="시작일자" value="${fn:substring(search.searchStartDate, 0, 8)}" /></span>
									<select id="startTime" name="startTime" title="${op:message('M00508')}"> <!-- 시간 선택 -->
										<c:forEach items="${hours}" var="code">
											<option value="${code.value}" ${op:selected(fn:substring(search.searchStartDate, 8, 10), code.value)}>${code.label}</option>
										</c:forEach>
									</select>시 : 00분
									<span class="wave">~</span>
									<span class="datepicker"><form:input path="endDate" maxlength="8" class="required _number datepicker" title="종료일자" value="${fn:substring(search.searchEndDate, 0, 8)}" /></span> 
									<select id="endTime" name="endTime" title="${op:message('M00508')}">
										<c:forEach items="${hours}" var="code">
											<option value="${code.value}" ${op:selected(fn:substring(search.searchEndDate, 8, 10), code.value)}>${code.label}</option> 
										</c:forEach>
									</select>시 : 59분
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		
			<div class="btn_center" style="padding-right: 190px;">
				<button type="submit" class="btn btn-active">${op:message('M00088')}</button>	 <!-- 등록 -->
				<a href="${!empty requestContext.prevPageUrl ? requestContext.prevPageUrl : '/opmanager/search/list'}" class="btn btn-default">${op:message('M00037')}</a>	 <!-- 취소 -->
			</div>
		</form:form>		
	</div> <!-- // item_list02 -->
	
<script type="text/javascript">

var patterns = /^[a-zA-Z0-9_-]+$/;
	$(function(){
		$("#search").validator(function() {
			if (checkedDate($('#startDate').val(), $('#startTime').val(), $('#endDate').val(), $('#endTime').val())) {
				alert('사용기간의 종료기간이 시작기간보다 빠를 수 없습니다.');
				return false;
			}
			
			$('#searchStartDate').val($('#startDate').val() + $('#startTime').val() + '0000');
			$('#searchEndDate').val($('#endDate').val() + $('#endTime').val() + '5959');
		});
	});
	
	function checkedDate(startDate,startTime, endDate, endTime){
		
		return (startDate+startTime+'00') > (endDate+endTime+'59');
		
	}
</script>
