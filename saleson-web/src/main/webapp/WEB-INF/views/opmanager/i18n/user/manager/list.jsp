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


	<h3><span>${op:message('M00099')} </span></h3>
	
	<div class="board_write">
		<form:form modelAttribute="searchParam" cssClass="opmanager-search-form clear" method="get">
			<form:hidden path="sort" />
			<form:hidden path="orderBy" />
			<form:hidden path="itemsPerPage"/>
			<form:hidden path="extra" value="manager" />
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
									<form:option value="LOGIN_ID" label="${op:message('M00081')}"></form:option>
									<form:option value="USER_NAME" label="${op:message('M00005')}"></form:option>
									<form:option value="EMAIL" label="이메일"></form:option>
								</form:select>
								<form:input path="query" cssClass="optional seven" title="${op:message('M00022')}" />
							</div>
						</td>
					</tr>
					<%-- <tr>
						<td class="label">${op:message('M00082')}</td>
						<td>
							<div>
								<form:radiobutton path="useFlag" value=""  title="${op:message('M00082')}" label="${op:message('M00039')} " checked="checked"/>
								<form:radiobutton path="useFlag" value="Y" label="${op:message('M00083')} " />
								<form:radiobutton path="useFlag" value="N" label="${op:message('M00089')} " />
							</div>
						</td>						 
					</tr> --%>
				</tbody>		
			</table>		
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/user/manager/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button>
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button>
				</div>
			</div>						 							
		</form:form>			 
	</div> <!-- // board_write -->
	
	<!-- 버튼시작 -->

	<!-- 버튼 끝-->
 	<div class="sort_area mt30">
		<div class="left2">
			<span>${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}   </span>
		</div>
	</div>
		
	<!-- 리스트 테이블 시작-->
	<div class="board_list mt20">		
		<form id="listForm">		
			<table class="board_list_table">
				<caption>${op:message('M00099')}  ${op:message('M00100')} </caption>
				<colgroup>
					<col style="width: 30px" />
					<col style="width: 60px;" />
					<col style="width: 90px;">
					<col style="width: 90px;">
					<col style="width: 150px;">
					<col style="width:auto">
					<col style="width:100px;">
					<col style="width:95px;">
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" id="check_all" title="${op:message('M00039')}  선택" /></th>
						<th>순번</th>
						<th>${op:message('M00081')} </th>
						<th>${op:message('M00005')}</th>
						<th>이메일</th>
						<th>운영자 역할</th>
						<th>${op:message('M00085')} </th>
						<th>${op:message('M00087')}/${op:message('M00074')} </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="user" varStatus="i">
						<tr>
							<td>
								<input type="checkbox" name="id" value="${user.userId}" title="체크박스"  />
							</td>
							<td><div> ${pagination.itemNumber - i.count}</div></td>
							<td>
								<div> ${user.loginId} </div>
							</td>
							<td><div> ${user.userName}</div></td>
							<td><div> ${user.email}</div></td>
							<td>
								<div>
									<c:set var="roleCount" value="0" />
									<c:forEach items="${adminMenuRoles}" var="adminMenuRole" varStatus="i">
										<c:forEach items="${ user.userRoles }" var="userRole" varStatus="j" >
											<c:if test="${ adminMenuRole.authority eq userRole.authority }">
												${roleCount > 0 ? ', ' : ''}
												${adminMenuRole.roleName}
												<c:set var="roleCount">${roleCount + 1}</c:set>
											</c:if>
										</c:forEach>
									</c:forEach>
								</div>
							</td>
							<td><div> ${op:datetime(user.loginDate)} </div></td>
							<td>
								<div>
									<a href="javascript:;" onclick="userDetail('${user.userId}');" class="btn btn-gradient btn-xs" title="${op:message('M00087')} ">${op:message('M00087')}</a>
									<a href="javascript:;" class="btn btn-gradient btn-xs btn-delete-user" title="${op:message('M00074')} ">${op:message('M00074')}</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<c:if test="${empty list}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>
		</form>

		<div class="btn_all">
			<div class="btn_left">
				<button type="button" onclick="Common.deleteCheckedList(url('/opmanager/user/manager/delete/list'))" class="btn btn-default btn-sm btn-delete-checked-user"><span>${op:message('M00576')} <!-- 선택삭제 --></span></button>
			</div>
			<div class="btn_right">
				<a href="/opmanager/user/manager/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')} </a>
			</div>
		</div>

		<page:pagination-manager />

	</div><!--//board_list E-->

<script type="text/javascript">
	$(function() {
		$('#searchParam').validator(function(selector) {});
		
		$('.btn-delete-user').on('click', function(e) {
			e.preventDefault();
			$('#check_all').prop('checked', false);
			$(this).closest('table').find('input[name=id]').prop('checked', false);
			$(this).closest('tr').find('input[name=id]').prop('checked', true);
			Common.deleteCheckedList(url('/opmanager/user/manager/delete/list'));
			//return false;
		});
	});

	function userDetail(userId){
		location.href = "/opmanager/user/manager/edit/"+userId;
	}

	function userDelete(userId){
		if (confirm("선택된 데이터를 삭제하시겠습니까?")) {
			location.href = "/opmanager/user/manager/delete/"+userId;			
		}
	}
</script>
