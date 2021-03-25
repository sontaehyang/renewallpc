<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


	<h3><span>${op:message('M00261')}</span></h3>

	<div class="board_write">
		<form:form modelAttribute="groupParam" class="opmanager-search-form clear" method="get">
			<form:hidden path="sort" />
			<form:hidden path="orderBy" />
			<form:hidden path="itemsPerPage"/>
			
			<table class="board_write_table">
				<caption>${op:message('M00099')}  ${op:message('M00088')} </caption>
				<colgroup>
					<col style="width:150px;" />
					<col style="*" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00011')} <!-- 검색구분 --></td>
						<td>
							<div>
								<form:select path="where">
									<form:option value="GROUP_NAME" label="그룹명"></form:option>
								</form:select>
								<form:input path="query" cssClass="optional seven" title="${op:message('M00022')}" />
							</div>
						</td>
					</tr>
				</tbody>		
			</table>		
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/group/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>						 							
		</form:form>			 
	</div> <!-- // board_write -->
	
	
	<div class="board_write">
		<table class="board_list_table">
			<colgroup>
		 		<col style="width: 30px" />
				<col style="width: 60px;" />
				<col style="" />			
				<col style="" />			
				<col style="width:100px;" />			
				<col style="width:150px;" />			
				<col style="width:90px;" />			
				<col style="width:120px;" />				
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" title="체크박스" /></th> 
					<th scope="col">순번</th>
					<th scope="col">회원 그룹명</th>
					<th scope="col">회원 그룹 설명</th>
					<th scope="col">그룹 회원 수</th>
					<th scope="col">등록일</th>
					<th scope="col">삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="group" varStatus="i">
					<tr>
						<td><input type="checkbox" name="id" value="${group.groupCode}" title="" /></td>
										
						<td>
						 	${pagination.itemNumber - i.count}
						</td>
						<td><a href="/opmanager/group/edit/${group.groupCode}">${group.groupName}</a></td>
						<td>${group.groupExp} </td>
						<td>${group.userCount}</td>
						<td>${op:date(group.createdDate)}</td>
						<td>
							<a href="/opmanager/user-level/list/${group.groupCode}" class="btn btn-gradient btn-xs">등급관리</a><br/>
					 		<a href="javascript:userGroupEdit('${group.groupCode}');" class="btn btn-gradient btn-xs">수정</a>
					 		<a href="javascript:userGroupDelete('${group.groupCode}');" class="btn btn-gradient btn-xs">삭제</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>				 
		<c:if test="${ empty list }">
			<div class="no_content">
				<p>${op:message('M00170')} </p>
			</div>
		</c:if>
	</div><!--//board_write E-->	
	
	
	<div class="btn_all">
		<div class="btn_right mb0">
			<a href="/opmanager/group/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 그룹 등록</a>
		</div>
	</div>

	<page:pagination-manager />

			
<script type="text/javascript">
	function userGroupEdit(groupId){
		location.href = '/opmanager/group/edit/'+groupId;
	}
	
	function userGroupDelete(groupId){
		if (confirm("삭제시 복구 되지 않습니다. 그룹을 삭제하시겠습니까?")) {
			location.href = '/opmanager/group/delete/'+groupId;
		}
	}
</script>			
			