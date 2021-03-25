<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>

<style>
span.require {color: #e84700; margin-left: 5px;}
span.pagination {width: 100%; text-align: center;}
</style>
			
	<div class="popup_wrap">
		<h1 class="popup_title">직원검색</h1>
		<form:form modelAttribute="userSearchParam" method="get" enctype="multipart/form-data">
			<input type="hidden" name="targetId" value="<c:out escapeXml="true" value="${param.targetId}" />" />
			<div class="popup_contents">
				<div class="item_list">
					<!--입점업체 등록/수정 시작-->
					<h3><span>직원검색</span></h3>  
					<div class="board_write">
						<table class="board_write_table">
							<caption>${op:message('M00746')}</caption>
							<colgroup>
								<col style="width:150px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">회원명</td> <!-- 회원명 --> 
									<td> 
										<div>
											<form:select path="where" class="choice3">
												<form:option value="USER_NAME">회원명</form:option>
												<%-- <form:option value="PHONE_NUMBER">휴대폰번호</form:option> --%>
											</form:select>
											
											<form:input type="text" path="query" title="검색어" class="form-half required"/>	 
									    </div>
									</td>
								</tr>
							</tbody>					 
						</table>								 							
					</div> <!-- // board_write -->
				</div>
				
				<div class="btn_center">
					
					<button type="submit" class="btn btn-default"><span>${op:message('M00048')}</span></button> <!-- 취소 --> 
				</div>
			</div>
		</form:form>
		<c:if test="${!empty userList}">
			<div class="item_list">
				<!--입점업체 등록/수정 시작-->
				<h3><span>검색결과</span></h3>  
				<div class="board_write">
					<table class="board_list_table">
						<caption>${op:message('M00746')}</caption>
						<colgroup>
							<col style="width:120px;" />
							<col style="width:120px;" />
							<col style="" />
							<%-- <col style="width:120px;" /> --%>
							<col style="width:70px;" />
						</colgroup>
						<tbody>
							<tr>
								<th class="label">회원ID</th>
								<th class="label">회원명</th>
								<th class="label">이메일</th>
								<!-- <th class="label">휴대전화</th> -->
								<th class="label">선택</th>
							</tr>
							<c:forEach items="${userList}" var="user" varStatus="i">
							<tr 
								data-target-id="<c:out escapeXml="true" value="${param.targetId}" />"
								data-user-id="${user.userId}" 
								data-login-id="${user.loginId}" 
								data-user-name="${user.userName}" 
								data-email="${user.email}">
								<td><div>${user.loginId}</div></td>
								<td><div>${user.userName}</div></td>
								<td><div>${user.email}</div></td>
								<%-- <td><div>${user.userDetail.phoneNumber}</div></td> --%>
								<td><div>
									<button type="button" class="btn btn-gradient btn-sm btn-select">
										선택
									</button>
								
								</div></td>
							</tr>
							</c:forEach>
							
						</tbody>					 
					</table>								 							
				</div> <!-- // board_write -->
			</div>
		</c:if>
		<span class="pagination">
			<page:pagination-manager />
		</span>
		<a href="#" class="popup_close">창 닫기</a>
	</div>
	
<script type="text/javascript">
$(function() {
	$('.btn-select').on('click', function(e) {
		e.preventDefault();
		
		var user = $(this).closest('tr').data();
		
		opener.handleFindMdCallback(user);
		self.close();
	});

});

</script>
