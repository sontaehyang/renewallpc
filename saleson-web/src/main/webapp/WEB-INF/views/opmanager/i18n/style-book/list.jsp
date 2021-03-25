<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span></span></h3>

<form:form modelAttribute="styleBookDto" method="get">

	<div class="board_write">
		<table class="board_write_table" summary="스타일북 관리">
			<caption>스타일북 관리</caption>
			<colgroup>
				<col style="width:150px;" />
				<col style="width:auto;" />
			</colgroup>
			<tbody>
				<tr>
					<td class="label">검색 구분</td>
					<td>
						<div>
							<form:select path="where" title="검색구분">
								<option value="" ${op:selected(styleBookDto.where, '')}>전체</option>
								<option value="TITLE" ${op:selected(styleBookDto.where, 'TITLE')}>제목</option>
								<option value="CONTENT" ${op:selected(styleBookDto.where, 'CONTENT')}>내용</option>
							</form:select>
							<form:input type="text" path="query" class="three" title="검색어" />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="count_title mt20">
			<h5>
				전체 : ${op:numberFormat(pageContent.totalElements)}건 조회
			</h5>
			<span>
		        ${op:message('M00052')} :
		        <form:select path="size" title="${op:message('M00239')}"> <!-- 화면출력 -->
			        <form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 -->
			        <form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
			        <form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 -->
		        </form:select>
		    </span>
		</div>

		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/style-book/list'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
			</div>
		</div>
	</div>
</form:form>




<div class="board_list">
	<form id="dataForm" method="post">
		<table class="board_list_table" summary="스타일북 목록">
			<caption>스타일북 목록</caption>
			<colgroup>
				<col style="width:30px;">
				<col style="width:100px">
				<col style="width:auto;">
				<col style="width:150px">
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" class="check_all" title="체크박스"></th>
					<th scope="col">순번</th>
					<th scope="col">제목</th>
					<th scope="col">등록일자</th>
				</tr>
			</thead>
			<tbody class="sortable">
			<c:forEach items="${pageContent.content}" var="list" varStatus="i">
				<tr>
					<td><input type="checkbox" name="id" title="체크박스" value="${list.id}"></td>
					<td>${op:numbering(pageContent, i.index)}</td>
					<td class="left">
						<a href="javascript:Link.view('/opmanager/style-book/edit/${list.id}')">${list.title}
					</td>
					<td>${list.createdDateTime}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form>
</div>

<c:if test="${empty pageContent.content}">
<div class="no_content">
		${op:message('M00473')} <!-- 데이터가 없습니다. -->
</div>
</c:if>

<page:pagination-jpa/>

<div class="btn_all">
	<div class="btn_left mb0">
		<button type="button" class="btn btn-default btn-sm checked_delete">선택삭제</button>
	</div>
	<div class="btn_right mb0">
		<a href="/opmanager/style-book/create?url=${requestContext.currentUrl}" class="btn btn-active btn-sm">${op:message('M00088')}</a>
	</div>
</div>



<page:javascript>
<script type="text/javascript">

	var DATA_FORM_OBJECT = $('#dataForm');

	$(function(){

		$(".checked_delete").on("click",function(){
			if (DATA_FORM_OBJECT.find('input[name=id]:checked').size() == 0) {
				alert('삭제할 항목을 선택해주세요.');
				return;
			}

			Common.confirm(Message.get("M00196"),function(){	// 삭제하시겠습니까?
				if(DATA_FORM_OBJECT.find("input[name=id]:checked").size() > 0){
					DATA_FORM_OBJECT.attr('action','/opmanager/style-book/delete');
					DATA_FORM_OBJECT.submit();
				}
			});
		});

		$('.check_all').on('click',function() {
			if ($(this).is(':checked')) {
				$('input[name=id]').prop('checked', true);
			} else {
				$('input[name=id]').prop('checked', false);
			}
		});
	});
</script>
</page:javascript>