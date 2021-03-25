<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
	
	<form:form modelAttribute="messageParam" method="get" enctype="multipart/form-data">
		<h3><span>${op:message('MENU_1402')}</span></h3> <!-- 메세지 관리 -->
		<div class="board_write">
			<table class="board_write_table" summary="${op:message('MENU_1402')}"> <!-- 메세지 관리 -->
				<caption>${op:message('MENU_1402')}</caption>
				<colgroup>
					<col style="width:150px;" />
					<col style="width:*;" />
				</colgroup>
				<tbody>
					 <tr>
					 	<td class="label">${op:message('M00011')}</td> <!-- 검색구분 -->  
					 	<td>
					 		<div>
								<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드 선택 --> 
									<form:option value="ID">${op:message('M00081')}</form:option> <!-- 아이디 --> 
									<form:option value="MESSAGE">${op:message('M01672')}</form:option> <!-- 메세지 --> 
								</form:select> 
								<form:input type="text" path="query" class="three" title="${op:message('M00021')}" /> <!-- 검색어 입력 --> 
							</div>
					 	</td>	
					 </tr>
				</tbody>					 
			</table>								 							
		</div> <!-- // board_write -->

		<!-- 버튼시작 -->	
		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/message/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>		 
		<!-- 버튼 끝-->
		
		
		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${messageCount} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
			<span>
				${op:message('M00052')} : 
				<form:select path="itemsPerPage" title="${op:message('M00239')}"> <!-- 화면출력 -->
					<form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 --> 
					<form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
					<form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 --> 
					<form:option value="100" label="${op:message('M00243')}" /> <!-- 100개 출력 --> 
				</form:select>
			</span>
		</div>
	</form:form>
		
	<form id="listForm">
		<div class="board_write">
			<table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 --> 
				<caption>${op:message('M00273')}</caption>
				<colgroup>
					<col style="width:5%;" />
					<col style="width:10%;" />	
					<col style="width:15%;" />	
					<col style="width:35%;" />	
					<col style="width:35%;" />	
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" name="tempId2" id="check_all" title="${op:message('M00169')}" /></th> <!-- 체크박스 --> 
						<th scope="col">${op:message('M00200')}</th> <!-- 순번 --> 
						<th scope="col">${op:message('M00081')}</th> <!-- 아이디 --> 
						<th scope="col">${op:message('M01670')}</th> <!-- 한국어 --> 	
						<th scope="col">${op:message('M01671')}</th> <!-- 일본어 --> 			
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${messageList}" var="list" varStatus="i">
						<tr>
							<td><input type="checkbox" name="id" id="check" value="${list.id}" title="${op:message('M00169')}" /></td>
							<td>${pagination.itemNumber - i.count}</td>
							<td>${list.id}</td>
							<td>
								<div class="tex_l">	
									<a href="/opmanager/message/edit/${list.id}">${list.kMessage}</a>								 
								</div></td>
							<td>
								<div class="tex_l">	
									<a href="/opmanager/message/edit/${list.id}">${list.jMessage}</a>								 
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>				 
		</div><!--// board_write E-->
		
		<c:if test="${empty messageList}">
			<div class="no_content">
				${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>
		</c:if>
		
		<div class="btn_all">
			<div class="btn_left mb0">
				<a id="delete_list_data" href="#" class="btn btn-default btn-sm"><span>${op:message('M00576')}</span></a> <!-- 선택삭제 -->
			</div>
			<div class="btn_right mb0">
				<a href="/opmanager/message/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
			</div>
		</div>
			
		<page:pagination-manager /><br/> 
	</form>
		
		
		
<script type="text/javascript">
$(function() {
	
	//데이터 출력량 적용
	$('#itemsPerPage').on("change", function(){
		$('#messageParam').submit();	
	});
	
	//목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/message/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
});
</script>