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

	<h3><span>${op:message('M00210')}</span></h3>
	<form:form modelAttribute="searchParam" cssClass="opmanager-search-form clear" method="get">
		<fieldset>
			<legend class="hidden">${op:message('M00048')}</legend>
			<form:hidden path="sort" />
			<form:hidden path="orderBy" />
			<form:hidden path="itemsPerPage"/>
			
			<div class="board_write">
				<table class="board_write_table">
					<colgroup>
						<col style="width: 150px;" />
						<col style="width: auto;" />
						
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00011')}</td>
							<td >
								<div>
									<form:select path="where" title="${op:message('M00211')} ">
										<form:option value="LOGIN_ID">${op:message('M00081')}(E-mail)</form:option>
										<form:option value="USERNAME">이름</form:option>
									</form:select>
									<form:input path="query" cssClass="optional seven _filter" title="${op:message('M00211')} " />
							 	</div>
							</td> 
						</tr>
					 	<tr>
							<td class="label">탈퇴일</td>
							<td>
								<div>
									<span class="datepicker"><form:input path="startCreated" class="datepicker" maxlength="8" title="${op:message('M00024')}" /></span>
									<span class="wave">~</span>
									<span class="datepicker"><form:input path="endCreated" class="datepicker" maxlength="8" title="${op:message('M00024')}" /></span>
									<span class="day_btns"> 
										<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 --> 
										<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 --> 
										<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 --> 
										<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 --> 
										<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 -->
									</span>
								</div>								
							</td>
						</tr>
					</tbody> 
				</table>						 
			</div> <!--// board_write E-->
		
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/user/secede-user/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button>
				</div>
			</div>	<!--// btn_all E-->
		</fieldset>
	</form:form>
	
	<div class="count_title mt20">
		<h5>
			${op:message('M00238')} : ${op:numberFormat(pagination.totalItems)}${op:message('M01220')} <!-- 명 -->
		</h5>	 
		<span>
			<select name="displayCount" id="displayCount" title="${op:message('M00239')} ">
				<option value="10">${op:message('M00240')}</option>
				<option value="20">${op:message('M00241')}</option>
				<option value="50">${op:message('M00242')}</option>
				<option value="100">${op:message('M00243')} </option>
			</select>
		</span>
	</div>
			
	
	<div class="board_write">
		<form id="listForm">
			<table class="board_list_table">
				<colgroup>
					<col style="width:30px;" />
					<col style="width:300px;" />
					<col style="" />
					<%-- <col style="width:200px;" /> --%>
					<col style="width:200px" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">${op:message('M00200')}</th>
						<th scope="col">${op:message('M00081')}(E-mail)</th>	<!-- 아이디 -->
						<%-- <th scope="col">${op:message('M00005')}</th> --%>			<!-- 이름 -->
						<th>탈퇴사유</th>
						<th scope="col">탈퇴일자</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="user" varStatus="i">
						<tr>
							 <td>${pagination.itemNumber - i.count}</td>
							 <td>${user.loginId}</td>
							 <td>${user.userDetail.leaveReason}</td>
							 <%-- <td>${user.userName}</td> --%>
							 <td>
								${op:date(user.leaveDate)}
							 </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>			
		</form>	 
		
		<c:if test="${empty list}">
			<div class="no_content">
				데이터가 없습니다.
			</div>
		</c:if>
		
	</div><!--//board_write E-->	
	
	<page:pagination-manager />
	
<script type="text/javascript">
$(function() {
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startCreated"]' , 'input[name="endCreated"]');
	$('#searchParam').validator(function(selector) {});
	
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

</script>			
			