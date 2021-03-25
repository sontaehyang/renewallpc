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
						<col style="width: 150px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00011')}</td>
							<td colspan="3">
								<div>
									<form:select path="where" title="${op:message('M00211')} ">
										<form:option value="LOGIN_ID">${op:message('M00081')}</form:option>
										<form:option value="EMAIL">E-mail</form:option>
										<form:option value="USER_NAME">이름</form:option>
										<form:option value="TEL_NUMBER">${op:message('M00016')}</form:option>
										<form:option value="PHONE_NUMBER">${op:message('M00009')}</form:option>
									</form:select>
									<form:input path="query" class="optional seven" title="${op:message('M00211')} " />
							 	</div>
							</td> 
						</tr>
					 	<%-- <tr>
							<td class="label">${op:message('M00212')}</td>
							<td colspan="3">
								<div>
									<form:select path="level" title="${op:message('M00212')}">
										<form:option value="">${op:message('M00039')}</form:option>
										<form:option value="ROLE_USER_CUST">${op:message('M00213')}</form:option>
										<form:option value="ROLE_USER_BRONZE">${op:message('M00214')}</form:option>
										<form:option value="ROLE_USER_SILVER">${op:message('M00215')}</form:option>
										<form:option value="ROLE_USER_GOLD">${op:message('M00216')}</form:option>
										<form:option value="ROLE_USER_SAPPHIRE">${op:message('M00217')}</form:option>
										<form:option value="ROLE_USER_DIAMOND">${op:message('M00218')}</form:option>
										<form:option value="ROLE_USER_VIP">VIP</form:option>
									</form:select>
							 	</div>
							</td>
						</tr> --%>
						<tr>
							<td class="label">${op:message('M00219')}</td>
							<td colspan="3">
								<div class="search-date">
									<span class="datepicker"><form:input path="startCreated" cssClass="datepicker optional " title="${op:message('M00220')}" /></span>
									<span class="wave">~</span>
									<span class="datepicker"><form:input path="endCreated" cssClass="datepicker optional " title="${op:message('M00221')}" /></span>
									<span class="day_btns"> 
										<a href="#" class="btn_date today">${op:message('M00026')}</a>  
										<a href="#" class="btn_date week">${op:message('M00222')}</a> 
										<a href="#" class="btn_date month1">${op:message('M00029')}</a> 
										<a href="#" class="btn_date month2">${op:message('M00223')}</a> 
										<a href="#" class="btn_date all">${op:message('M00039')}</a> 
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00224')} </td>
							<td colspan="3">
								<div class="search-date">
									<span class="datepicker"><form:input path="startLoginDate" cssClass="datepicker optional " title="${op:message('M00225')}" /></span>
									<span class="wave">~</span>
									<span class="datepicker"><form:input path="endLoginDate" cssClass="datepicker optional " title="${op:message('M00226')}" /></span>
									
									<span class="day_btns"> 
										<a href="#" class="btn_date today">${op:message('M00026')}</a>  
										<a href="#" class="btn_date week">${op:message('M00222')}</a> 
										<a href="#" class="btn_date month1">${op:message('M00029')}</a> 
										<a href="#" class="btn_date month2">${op:message('M00223')}</a> 
										<a href="#" class="btn_date all">${op:message('M00039')}</a> 
									</span>
								</div>							
							</td>
						</tr>

						<tr>
							<td class="label">${op:message('M00229')}</td>
							<td>
								<div>
									<form:input path="startLoginCount" cssClass="optional _number form-amount" title="${op:message('M00229')}" /> ${op:message('M00851')} <!-- 회 -->
									<span class="wave">~</span>
									<form:input path="endLoginCount" cssClass="optional  _number form-amount" title="${op:message('M00229')}" /> ${op:message('M00851')} <!-- 회 -->
							 	</div>
							</td>
							<td class="label">${op:message('M00230')}</td>
							<td>
								<div>
									<form:input path="loginCount" cssClass="optional  _number form-amount" title="${op:message('M00230')}" />
									<span>${op:message('M00231')} </span>
							 	</div>
							</td>
						</tr>
						<tr>
							<td class="label">Email 수신동의</td>
							<td>
								<div>
									<form:radiobutton path="receiveEmail" value="" title="${op:message('M00232')}" checked="checked" label="${op:message('M00039')}"/>
									<form:radiobutton path="receiveEmail" value="0" label="${op:message('M00233')}"/>
									<form:radiobutton path="receiveEmail" value="1" label="${op:message('M00234')}"/>
								</div>
							</td>
							<td class="label">SMS 수신동의</td>
							<td>
								<div>
									<form:radiobutton path="receiveSms" value="" title="${op:message('M00232')}" checked="checked" label="${op:message('M00039')}"/>
									<form:radiobutton path="receiveSms" value="0" label="${op:message('M00233')}"/>
									<form:radiobutton path="receiveSms" value="1" label="${op:message('M00234')}"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00235')} </td>
							<td colspan="3">
								<div>
									<form:radiobutton path="siteFlag" value="" title="${op:message('M00235')}" checked="checked" label="${op:message('M00039')}"/>
									<form:radiobutton path="siteFlag" value="0" label="PC"/>
									<form:radiobutton path="siteFlag" value="1" label="Mobile "/>
									<form:radiobutton path="siteFlag" value="2" label="Call"/>
									<form:radiobutton path="siteFlag" value="3" label="SNS"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">회원 그룹 검색</td>
							<td>
								<div>
									<form:select path="groupCode" title="그룹명">
										<form:option value="">전체</form:option>
										<form:option value="NO_GROUP_CODE">그룹 미지정 회원</form:option>
										<c:forEach items="${groups}" var="group">
											<form:option value="${group.groupCode}">${group.groupName}</form:option>
										</c:forEach>
									</form:select>
								</div>	
							</td>
							<td class="label">회원 Level 검색</td>
							<td>
								<div>
									<form:select path="levelId" title="회원 Level">
										<form:option value="">전체</form:option>
										<form:option value="0">Level 미지정 회원</form:option>


										<c:forEach items="${groups}" var="group">

											<optgroup label="${group.groupName}">
												<c:forEach items="${group.userLevels}" var="userLevel">
													<form:option value="${userLevel.levelId}" >${userLevel.levelName}</form:option>
												</c:forEach>
											</optgroup>
										</c:forEach>
									</form:select>
								</div>
							</td>
						</tr>
					</tbody> 
				</table>						 
			</div> <!--// board_write E-->
		
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/user/customer/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>	<!--// btn_all E-->
		</fieldset>
	</form:form>
	
	<div class="count_title mt20">
		<h5>
            전체 : ${op:numberFormat(pagination.totalItems)}건 조회
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
					<col style="width:60px;" />
					<col style="width:200px" />
					<col style="width:200px;" />
					<%-- <col style="" /> --%>
					<col style="width:120px;" />
					<col style="width:100px;" />
					<col style="width:70px;" />
					<col style="width:70px;" />
					<%--<col style="width:100px;" /><%--<col style="width:100px;" /> <!- 2015.11.09 주석처리 (OP_ORDER 및 몇몇 조건때문에 처리가 힘듬)-->--%>
					<%--<col style="width:100px;" /> <!- 2015.11.09 주석처리 (OP_ORDER 및 몇몇 조건때문에 처리가 힘듬)-->--%>
					<col style="width:80px;" />
					<col style="width:70px" />
					<col style="width:100px" />
					<col style="width:50px" />
					<col style="width:30px" />
				</colgroup>
				<thead>
				<tr>
					<th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00169')}" /></th>
					<th scope="col">${op:message('M00200')}</th>
					<th scope="col">${op:message('M00081')}</th>	<!-- 아이디 -->
					<th scope="col">${op:message('M00005')}</th>	<!-- 이름 -->
					<%-- <th scope="col">${op:message('M00175')}</th> --%>	<!-- 닉네임 -->
					<th scope="col">${op:message('M00245')}</th>
					<th scope="col">${op:message('M00246')} </th>
					<th scope="col">${op:message('M00229')}</th>
					<%--<th scope="col">${op:message('M00247')}</th> <%--<col style="width:100px;" /> <!- 2015.11.09 주석처리 (OP_ORDER 및 몇몇 조건때문에 처리가 힘듬)-->--%>
					<%-- <th scope="col">${op:message('M00248')}</th> <%--<col style="width:100px;" /> <!- 2015.11.09 주석처리 (OP_ORDER 및 몇몇 조건때문에 처리가 힘듬)-->--%>
					<th scope="col">${op:message('M00219')}</th>	<!-- 가입일 -->
					<th scope="col">Email수신동의</th>
					<th scope="col">SMS수신동의</th>
					<th scope="col">${op:message('M00235')} </th>
					<th scope="col">로그인</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="user" varStatus="i">

					<tr>
						<td>
							<input type="checkbox" name="id" value="${user.userId}" title="${op:message('M00169')}"  />
						</td>
						<td>${pagination.itemNumber - i.count}</td>
							<%-- <td rowspan="2"><a href="javascript:Manager.userDetails(${user.userId})">${user.email}</a></td> --%>
						<td><a href="javascript:Manager.userDetails(${user.userId})">${user.loginId}</a></td>
						<td>
							<a href="javascript:Manager.userDetails(${user.userId})">${user.userName}</a>
						</td>
							<%--  <td rowspan="2">${user.userDetail.nickname} --%>

						<!-- 등급
						 	<c:choose>
								<c:when test="${user.userRoles[0].authority == 'ROLE_USER_CUST'}">${op:message('M00255')}</c:when>
								<c:when test="${user.userRoles[0].authority == 'ROLE_USER_BRONZE'}">${op:message('M00256')} </c:when>
								<c:when test="${user.userRoles[0].authority == 'ROLE_USER_SILVER'}">${op:message('M00257')} </c:when>
								<c:when test="${user.userRoles[0].authority == 'ROLE_USER_GOLD'}">${op:message('M00258')}</c:when>
								<c:when test="${user.userRoles[0].authority == 'ROLE_USER_SAPPHIRE'}">${op:message('M00217')}</c:when>
								<c:when test="${user.userRoles[0].authority == 'ROLE_USER_DIAMOND'}">${op:message('M00218')}</c:when>
								<c:when test="${user.userRoles[0].authority == 'ROLE_USER_VIP'}">VIP</c:when>
							</c:choose>
							 -->
						</td>
						<td>${user.userDetail.phoneNumber}</td>
						<td class="text-right">${op:numberFormat(user.userDetail.point)}P</td>
						<td>${user.loginCount}</td>
							<%--td>${op:numberFormat(user.userDetail.buyCount)}건</td --%>
							<%--td>${op:numberFormat(user.userDetail.buyPrice)}${op:message('M00049')}</td--%>
						<td>
								${op:date(user.createdDate)}
							<!--  ${fn:substring(user.createdDate, 0, 10)}  -->
						</td>
						<td>
							<c:choose>
								<c:when test="${user.userDetail.receiveEmail == '0'}">${op:message('M00233')}</c:when>
								<c:otherwise>${op:message('M00234')} </c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${user.userDetail.receiveSms == '0'}">${op:message('M00233')}</c:when>
								<c:otherwise>${op:message('M00234')} </c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${user.userDetail.siteFlag == '0'}">PC</c:when>
								<c:when test="${user.userDetail.siteFlag == '1'}">Mobile</c:when>
								<c:when test="${user.userDetail.siteFlag == '2'}">Call</c:when>
								<c:when test="${user.userDetail.siteFlag == '3'}">SNS</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</td>
						<td><button type="button" class="btn btn-gradient btn-xs btn-shadow-login" data-login-id="${user.loginId}">로그인</button></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>			
		</form>	 
		
		<c:if test="${empty list}">
			<div class="no_content">
				${op:message('M00251')} 
			</div>
		</c:if>
		
	</div><!--//board_write E-->	
	
	<div class="btn_all">
		<div class="btn_left">				
			<%-- <button type="button" class="btn gray02">
				<a href="point_popup.html" target="_blank" onclick="Common.popup('/opmanager/user/customer/point', 'donation', 600, 550); return false;" title="새창">${op:message('M00252')}</a>
			</button> --%>
			<!-- 
			<button type="button" class="ctrl_btn">
				<a href="point_popup.html" target="_blank" onclick="Common.popup('/opmanager/user/customer/point', 'donation', 600, 550); return false;" title="새창"> </a>
			</button>
			<button type="button" class="ctrl_btn">
				<a href="point_popup.html" target="_blank" onclick="Common.popup('/opmanager/user/customer/point', 'donation', 600, 550); return false;" title="새창"> </a>
			</button>
			 -->
			<a href="point_popup.html" class="btn btn-default btn-sm" target="_blank" onclick="Common.popup('/opmanager/user/customer/point', 'donation', 600, 550); return false;" title="새창">${op:message('M00710')} <!-- 전체회원 포인트지급 --> </a>
			<button type="button" class="btn btn-default btn-sm" onclick="Common.popup('/opmanager/user/customer/point-by-excel', 'donation', 600, 550); return false;" title="새창">${op:message('M00246')} 일괄지급(엑셀업로드)	</button>
			<button type="button" class="btn btn-default btn-sm" onclick="selectPoint()" title="새창">선택회원 ${op:message('M00246')} 지급</button>
			<button type="button" class="btn btn-default btn-sm" onclick="groupSetting()" title="새창">선택회원 그룹설정</button>
		</div>
		<sec:authorize access="hasRole('ROLE_EXCEL')">
			<div class="btn_right">
				<a href="/opmanager/user/customer/list/download-excel" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save" aria-hidden="true"></span> 엑셀 다운로드</a>
			</div>
		</sec:authorize>

	</div>	<!--// btn_all E-->
		
	<page:pagination-manager />
	
	<div style="display: none;">
		<span id="today">${today}</span>
		<span id="week">${week}</span>
		<span id="month1">${month1}</span>
		<span id="month2">${month2}</span>
	</div>

 
<!-- <p class="btns">
	<span class="right">
		<button type="button" onclick="Common.deleteCheckedList(url('/opmanager/user/customer/delete/list'))" class="btn gray">선택삭제</button>
	</span>	
</p> -->
			
<form name="shadow-login-form" id="shadow-login-form" action="/op_security_login" method="post">
	<input type="hidden" name="target" value="/" />
	<input type="hidden" name="op_login_type" value="ROLE_USER" />
	<input type="hidden" id="op_username" name="op_username" />
	<input type="hidden" id="op_password" name="op_password" />
	<input type="hidden" id="op_signature" name="op_signature" />
</form>					
			
<script type="text/javascript">
$(function() {
	$('#searchParam').validator(function(selector) {});
	serachDate();
	displayChange();
	displaySelected();
	
	// 사용자 로그인.
	initShadowLogin();
});

function initShadowLogin() {
	$('.btn-shadow-login').on('click', function() {
		
		var loginId = $(this).data('loginId');
		
		var shadowLoginId = $(this).data('shadowLoginId');
		var shadowLoginPassword = $(this).data('shadowLoginPassword');
		var shadowLoginSignature = $(this).data('shadowLoginSignature');
		
		var isSuccess = false;
		$.post('/opmanager/user/shadow-login', {'loginId' : loginId}, function(resp){
			if (resp.isSuccess) {
				isSuccess = true;
				
				shadowLoginId = resp.data.shadowLoginId;
				shadowLoginPassword = resp.data.shadowLoginPassword;
				shadowLoginSignature = resp.data.shadowLoginSignature;
			}	
		}, 'json');
		
		
		if (!isSuccess) {
			alert('로그인 처리에 실패 하였습니다.');
			return;
		}
		
		$('#op_username').val(shadowLoginId);
		$('#op_password').val(shadowLoginPassword);
		$('#op_signature').val(shadowLoginSignature);
		
		if (confirm('선택한 회원으로 로그인하시겠습니까?')) {
			$('#shadow-login-form').submit();
		}
		
	});
}

/**
 * 조회 기간 설정
 * @return
 */
function serachDate()
{

	$(".btn_date").on('click',function(){

		var $id = $(this).attr('class').replace('btn_date ','');		// id[0] : type, id[1] : value

		if ($id == 'all') {
			
			$("input[type=text]",$(this).parent().parent()).val('');
			
		} else {

			var today = $("#today").text();

			var date1 = '';
			var date2 = '';

			if ($id == 'today') {
				date1 = today;
				date2 = today;
			} else {
				date1 = $("#"+$id).text();
				date2 = today;
			}

			$("input[type=text]",$(this).parent().parent()).eq(0).val(date1);
			$("input[type=text]",$(this).parent().parent()).eq(1).val(date2);
			
		}
		
	});

	
}

function displayChange() {
	$("#displayCount").on('change', function(){
		$("#itemsPerPage").val($(this).val());
		$('#searchParam').submit();
	});
}

function displaySelected(){
	$("#displayCount").val($("#itemsPerPage").val());
	
}

function downloadExcel() {
	Common.popup('/opmanager/user/customer/download-excel', 'customer-download-excel',  890, 700, 1);
	/*
	var $form = $('#searchParam');
	$form.attr("action", "/opmanager/user/customer/download-excel");
	Common.loading.display = false;
	$form.submit();
	
	$form.attr("action", "/opmanager/user/customer/list");
	Common.loading.display = true;
	*/
}

function groupSetting() {
	
	if ($("input:checkbox[name='id']:checked").size() == 0) {
		
		alert('그룹을 설정하실 회원을 선택하세요.');
		return;
		
	}
	
	if ($("input:checkbox[name='id']:checked").size() > 100) {
		
		alert('선택회원 그룹설정의 경우 100명씩 변경 가능합니다.');
		return;
		
	}
	
	var userIds = []; 
	
 	$("input:checkbox[name='id']:checked").each(function(index){
		userIds[index] = $(this).val();
	}); 
	
	Common.popup('/opmanager/user/popup/group?userIds='+userIds+"&mode="+true, 'group', 600, 400, 1);
}

function selectPoint() {
	
	if ($("input:checkbox[name='id']:checked").size() == 0) {
		
		alert('${op:message('M00246')} 지급할 회원을 선택하세요.');
		return;
		
	}
	
	if ($("input:checkbox[name='id']:checked").size() > 100) {
		
		alert('선택회원 ${op:message('M00246')} 지급의 경우 100명씩 변경 가능합니다.');
		return;
		
	}
	
	var userIds = []; 
	
 	$("input:checkbox[name='id']:checked").each(function(index){
		userIds[index] = $(this).val();
	}); 
	
	Common.popup('/opmanager/user/customer/point-pay?userIds='+userIds+"&mode="+true, 'point-pay', 600, 400, 1);
}
</script>			
			