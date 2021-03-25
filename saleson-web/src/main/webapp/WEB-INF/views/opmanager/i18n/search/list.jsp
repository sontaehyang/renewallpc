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

	<div class="item_list">
		<h3><span>추천검색어 관리</span></h3>
		<form:form modelAttribute="searchParam" method="get">
			<div class="board_write">								
				<table class="board_write_table" summary="추천검색어 관리">
					<caption>추천검색어 관리</caption>
					<colgroup>
						<col style="width: 150px;">
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
							<td>
								<div>
									<form:select path="where" title="상세검색 선택">
										<form:option value="SEARCH_CONTENTS">검색문구</form:option>
									</form:select>
									<form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00191')}</td> <!-- 공개유무 -->
							<td>
								<div>
									<p>
										<form:radiobutton path="displayFlag" value="" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
										<form:radiobutton path="displayFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 -->
										<form:radiobutton path="displayFlag" value="N" label="${op:message('M00097')}" /> <!-- 비공개 -->
									</p>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
			</div>
			
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.requestUri}';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>
		</form:form>
		
		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${op:numberFormat(searchCount)} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
		</div>
		
		<form id="checkedDeleteForm" method="post" action="/opmanager/search/checked-delete">
			<div class="board_write">
				<table class="board_list_table" summary="처리내역 리스트">
					<caption>처리내역 리스트</caption>
					<colgroup>
						<col style="width:30px;">
						<col style="width:100px">
						<col style="width:auto;">
						<col style="width:500px">
						<col style="width:100px;">
						<col style="width:200px">
						<col style="width:100px;">
						<col style="width:70px"> 
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" class="delete_all" title="체크박스"></th>
							<th scope="col">순번</th>
							<th scope="col">검색문구</th>
							<th scope="col">링크</th>
							<th scope="col">공개유무</th>
							<th scope="col">날짜</th>
							<th scope="col">${op:message('M00202')}</th>  <!-- 등록일 -->
							<th scope="col">${op:message('M00087')}</th>  <!-- 수정 -->
						</tr>
					</thead>
					<tbody class="sortable">
						<c:forEach items="${searchList}" var="list" varStatus="i">	
							<tr>
								<td><input type="checkbox" name="id" title="체크박스" value="${list.searchId}"></td>
								<td>${pagination.itemNumber - i.count}</td>
								<td class="tex_l"><a href="/opmanager/search/edit/${list.searchId}?url=${requestContext.currentUrl}">${list.searchContents}</a></td>
								<td class="tex_l">
									<span class="glyphicon glyphicon-blackboard">${list.searchLink}</span>
									<c:if test='${list.searchLinkTargetFlag == "Y"}'>
										<span class="glyphicon glyphicon-new-window"></span>
									</c:if>
									</br>
									<span class="glyphicon glyphicon-phone">${list.searchMobileLink}</span>
									<c:if test='${list.searchMobileLinkTargetFlag == "Y"}'>
										<span class="glyphicon glyphicon-new-window"></span>
									</c:if>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.searchStartDate <= today && list.searchEndDate > today}">
											공개
										</c:when>
										<c:otherwise>
											비공개
										</c:otherwise>
									</c:choose>
								</td>
								<td>${op:date(list.searchStartDate)} ~ ${op:date(list.searchEndDate)}</td>
								<td>${op:date(list.createdDate)}</td>
								<td><p><a href="/opmanager/search/edit/${list.searchId}?url=${requestContext.currentUrl}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a></p></td> <!-- 수정 -->
							</tr>
						</c:forEach>
					</tbody>
				</table>				 
			</div>
			<c:if test="${empty searchList}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>
			
			<div class="btn_all">
				<div class="btn_left mb0">
					<button type="button" class="btn btn-default btn-sm checked_delete">선택삭제</button>
				</div>
				<div class="btn_right mb0">
					<a href="/opmanager/search/create?url=${requestContext.currentUrl}" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
				</div>
			</div>
			
			<page:pagination-manager />
		</form>	
	</div> <!-- // item_list02 -->
	
	<div class="board_guide ml10">
		<p class="tip">Tip</p>
		<p class="tip">* 추천 검색어는 공개된 검색중 랜덤으로 1개가 표시됩니다.</p>
		<p class="tip">* 공개유무는 날짜에 따라 공개/비공개로 바뀝니다.</p> 
	</div>
	
<script type="text/javascript">
$(function(){
	
	$(".checked_delete").on("click",function(){
		if ($('input[name=id]:checked').size() == 0) {
			alert('삭제할 항목을 선택해주세요.');
			return;
		}
		
		Common.confirm(Message.get("M00196"),function(){	// 삭제하시겠습니까?
			if($("input[name=id]:checked").size() > 0){
				$("#checkedDeleteForm").submit();
			}
		});
	});
	
	$('.delete_all').on('click',function() {
		if ($(this).is(':checked')) {
			$('input[name=id]').prop('checked', true);
		} else {
			$('input[name=id]').prop('checked', false);
		}
	});
});
</script>