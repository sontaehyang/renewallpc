<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>

			<!--공지사항 시작-->
			<h3><span>${op:message('M00269')} </span></h3>
			<div class="board_write" >
				<form:form modelAttribute="searchParam" method="get">
				<form:hidden path="sort" />
				<form:hidden path="orderBy" />
				<form:hidden path="itemsPerPage"/>
				<table class="board_write_table" summary="${op:message('M00269')} ">
					<caption>${op:message('M00269')} </caption>
					<colgroup>
						<col style="width:150px;" />
						<col style="width:*;" />
					</colgroup>
					<tbody>
						 <tr>
						 	<td class="label">${op:message('M00011')}</td>
						 	<td>
						 		<div>
						 			<form:select path="where">
						 				<form:option value="SUBJECT" label="${op:message('M00275')}" />
						 			</form:select>
						 			<form:input path="query" cssClass="nine" title="${op:message('M00021')}"/>
								</div>
						 	</td>
						 </tr>
					</tbody>
				</table>

				<!-- 버튼시작 -->
				<div class="btn_all">
					<div class="btn_left">
						<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/seller/notice/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
					</div>
					<div class="btn_right">
						<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
					</div>
				</div>
				<!-- 버튼 끝-->
				</form:form>
			</div> <!-- // board_write -->

			<div class="count_title mt20">
				<h5>
					${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
				</h5>	 <!-- 전체 -->   <!-- 건 조회 -->
				<span>
					${op:message('M00052')} :
					<select name="displayCount" id="displayCount" title="${op:message('M00239')}">
						<option value="10">${op:message('M00240')}</option>
						<option value="20">${op:message('M00241')}</option>
						<option value="50">${op:message('M00242')}</option>
						<option value="100">${op:message('M00243')}</option>
					</select>
				</span>
			</div>

			<div class="board_write">
				<table class="board_list_table" summary="${op:message('M00273')}">
					<caption>${op:message('M00273')}</caption>
					<colgroup>
						<col style="width:7%;" />
						<col style="width:auto;" />
						<col style="width:7%;" />
						<col style="width:12%;" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">${op:message('M00200')}</th>
							<th scope="col">${op:message('M00275')}</th>
							<th scope="col">조회수</th>
							<th scope="col">${op:message('M00276')}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="notice" varStatus="i">
						<!-- 2017.04.24 Jun-Eu Son 상단공지 표시 변경 -->
							<tr>
								<c:choose>
									<c:when test="${notice.noticeFlag eq 'Y' }"><td style="color: red;">공지</td></c:when>
									<c:otherwise><td>${pagination.itemNumber - i.count}</td></c:otherwise>
								</c:choose>
								<td>
									<div class="tex_l">
										<%-- <a href="<c:url value="/seller/notice/view/${notice.noticeId}" />">${notice.subject}</a> --%>
										<a href="javascript:noticeDetail(${notice.noticeId});">${notice.subject}</a>
									</div>
								</td>
								<td>${notice.hits}</td>
								<td>${op:date(notice.createdDate)}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${empty list}">
				<div class="no_content">
					${op:message('M00277')}
				</div>
				</c:if>
			</div><!--// board_write E-->

			<page:pagination-manager />

			<!--// 자주 묻는 질문관리 끝-->


<page:javascript>
<script type="text/javascript">
$(function(){
	displayChange();
	displaySelected();
});
function displayChange() {
	$("#displayCount").on('change', function(){
		$("#itemsPerPage").val($(this).val());
		$('#searchParam').submit();
	});
}
function displaySelected(){
	$("#displayCount").val($("#itemsPerPage").val());
}

function deleteNotice(noticeId) {
	Common.confirm("${op:message('M00196')}", function() {
		$.post(url("/opmanager/notice/delete/" + noticeId), {}, function(response) {
			Common.responseHandler(response, function() {
				alert("${op:message('M00205')}");
				location.reload();
			});
		});
	});

}

function noticeDetail(noticeId) {
	$("#searchParam").attr('action', '/seller/notice/view/' + noticeId).submit();
}
</script>
</page:javascript>