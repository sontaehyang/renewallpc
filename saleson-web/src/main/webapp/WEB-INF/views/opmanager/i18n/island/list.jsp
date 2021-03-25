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
		<h3><span>제주/도서산간 주소관리</span></h3>
		<form:form modelAttribute="islandDto" method="get">
			<div class="board_write">
				<table class="board_write_table" summary="추천검색어 관리">
					<caption>제주/도서산간 주소관리</caption>
					<colgroup>
						<col style="width: 150px;">
						<col style="">
						<col style="width: 150px;">
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
							<td>
								<div>
									<form:select path="where" title="상세검색 선택">
										<form:option value="ZIPCODE">우편번호</form:option>
										<form:option value="ADDRESS">주소</form:option>
									</form:select>
									<form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
								</div>
							</td>
							<td class="label">지역구분</td> <!-- 공개유무 -->
							<td>
								<div>
									<p>
										<form:radiobutton path="islandType" value="" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
										<form:radiobutton path="islandType" value="JEJU" title="제주" label="제주" />
										<form:radiobutton path="islandType" value="ISLAND" title="도서산간" label="도서산간" />
									</p>
								</div>
							</td>
						</tr>
					</tbody>
				</table>

			</div>

			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.requestUri}';"><span>${op:message('M00047')}</span></button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
				</div>
			</div>
		</form:form>

		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${op:numberFormat(count)} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 -->
		</div>

		<form id="checkedDeleteForm" method="post" action="/opmanager/island/delete">
			<div class="board_write">
				<table class="board_list_table" summary="처리내역 리스트">
					<caption>처리내역 리스트</caption>
					<colgroup>
						<col style="width:30px;">
						<col style="width:100px;">
						<col style="width:100px;">
						<col style="width:auto;">
						<col style="width:100px;">
						<col style="width:100px;">
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" class="delete_all" title="체크박스"></th>
							<th scope="col">순번</th>
							<th scope="col">우편번호</th>
							<th scope="col">주소</th>
							<th scope="col">구분</th>
							<th scope="col">추가금액</th>
						</tr>
					</thead>
					<tbody class="sortable">
						<c:forEach items="${pageContent.content}" var="list" varStatus="i">
							<tr>
								<td><input type="checkbox" name="id" title="체크박스" value="${list.id}"></td>
								<td>${op:numbering(pageContent, i.index)}</td>
								<td>${list.zipcode}</td>
								<td>
									<a href="/opmanager/island/edit/${list.id}?url=${requestContext.currentUrl}">${list.address}
								</td>
								<td>${list.islandType.title}</td>
								<td>
									<c:choose>
										<c:when test="${list.islandType.code == 'QUICK'}">
											${op:numberFormat(list.extraCharge)}원
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${empty pageContent.content}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>

			<div class="btn_all">
				<div class="btn_left mb0">
					<button type="button" class="btn btn-default btn-sm checked_delete">선택삭제</button>
				</div>
				<div class="btn_right mb0">
					<a href="/opmanager/island/create?url=${requestContext.currentUrl}" class="btn btn-active btn-sm">${op:message('M00088')}</a> <!-- 등록 -->
				</div>
			</div>

			<page:pagination-jpa />
		</form>
	</div> <!-- // item_list02 -->


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