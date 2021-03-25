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
						 				<form:option value="CONTENT" label="내용" />
						 			</form:select>
						 			<form:input path="query" cssClass="nine" title="${op:message('M00021')}"/>
								</div>
						 	</td>	
						 </tr>
						 <tr>
						 	<td class="label">상단공지</td>
						 	<td>
						 		<div>
									<p>
					 					<form:radiobutton path="noticeFlag" label="전체" value="" checked="checked"/>
					 					<form:radiobutton path="noticeFlag" label="사용" value="Y" />
										<form:radiobutton path="noticeFlag" label="사용안함" value="N" />
									</p>
								</div>
						 	</td>	
						 </tr>
						 <c:if test="${op:property('shoppingmall.type') == '2'}">
						 <tr>
						 	<td class="label">노출위치</td>
						 	<td>
						 		<div>
									<p>
										<form:radiobutton path="visibleType" label="구분없음" value="0" />
					 					<form:radiobutton path="visibleType" label="전체" value="1" />
										<form:radiobutton path="visibleType" label="사용자" value="2" />
										<form:radiobutton path="visibleType" label="판매자" value="3" />
									</p>
								</div>
						 	</td>	
						 </tr>
						 </c:if>
					</tbody>					 
				</table>			
				
				<!-- 버튼시작 -->	
				<div class="btn_all">
					<div class="btn_left">
						<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/notice/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
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
						<col style="width:12%;" />
						<col style="width:12%;" />
						<col style="width:12%;" />
						<col style="width:12%;" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">${op:message('M00200')}</th>
							<th scope="col">${op:message('M00275')}</th>
							<th scope="col">공지여부</th>
							<c:if test="${op:property('shoppingmall.type') == '2'}">
							<th scope="col">노출위치</th>
							</c:if>
							<th scope="col">${op:message('M00276')}</th>	
							<th scope="col">${op:message('M00168')}</th>					
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="notice" varStatus="i">
							<tr>
								<td>${pagination.itemNumber - i.count}</td>
								<td>
									<div class="tex_l">	
									<%-- 	<a href="<c:url value="/opmanager/notice/edit/${notice.noticeId}" />">${notice.subject}</a>		 --%>
									<a href="javascript:noticeDetail('${notice.noticeId}')">${notice.subject}</a>										
									</div>
								</td>
								<td>${notice.noticeFlag == "Y" ? "공지" : "비공지"}</td>
								<c:if test="${op:property('shoppingmall.type') == '2'}">
								<td>
									<c:choose>
										<c:when test="${notice.visibleType == 1}">
											전체
										</c:when>
										<c:when test="${notice.visibleType == 2}">
											사용자
										</c:when>
										<c:otherwise>
											판매자
										</c:otherwise>
									</c:choose>
								</td>
								</c:if>
								<td>${op:date(notice.createdDate)}</td>
								<td>
									<%-- <a href="<c:url value="/opmanager/notice/edit/${notice.noticeId}" />" class="btn btn-gradient btn-xs">${op:message('M00087')}</a> --%>
									<a href="javascript:noticeDetail('${notice.noticeId}')" class="btn btn-gradient btn-xs">${op:message('M00087')}</a>
									<a href="javascript:deleteNotice(${notice.noticeId})" class="btn btn-gradient btn-xs">${op:message('M00074')}</a>
								</td>
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
			
			<div class="btn_all">
				<div class="btn_right mb0">
					<a href="<c:url value="/opmanager/notice/create" />" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
				</div>
			</div>
			
			<page:pagination-manager /> 
			
			<!--// 자주 묻는 질문관리 끝-->
<script type="text/javascript">
$(function(){
	displayChange();
	displaySelected();
})
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

	location.href = '/opmanager/notice/edit/'+noticeId;
}

</script>