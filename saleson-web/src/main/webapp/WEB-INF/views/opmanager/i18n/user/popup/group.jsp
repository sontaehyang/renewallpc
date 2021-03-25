<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<div class="popup_wrap">
	<h1 class="popup_title">그룹 설정</h1> <!-- 기본정보 -->
	
	<form:form modelAttribute="groupSetting" id="groupSetting" action="/opmanager/user/group-setting" method="post">
		<c:forEach items="${userIds}" var="id">
			<input type="hidden" name="userIds" value="${id }">
		</c:forEach>

		<table cellpadding="0" cellspacing="0" summary="" class="board_list_table mb30">
			<caption>그룹 등록/수정</caption>
			<colgroup>
				<col style="width:120px;" /> 
				<col style="width:*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">선택한 회원수</th> <!-- 아이디 --> <!-- 이메일 -->
					<td class="tleft">
						<c:if test="${not empty userIds}">
							<div>${fn:length(userIds)}명 선택</div>
						</c:if>
					</td>
				</tr>
				<tr>
					<th scope="row">그룹 리스트</th>
					<td class="tleft">
						<div>
							<ul class="join_check">
								<c:forEach items="${groupList}" var="group" varStatus="i">
									<li>
										<label class="radio-inline"><input style="margin-top:10px" type="radio" id="groupCode" name="groupCode" class="required" value="${group.groupCode}" title="그룹" ${isChecked == 'Y' ? 'checked="checked"' : ''} /> ${group.groupName}</label>
									</li>
				 				</c:forEach>
			 				</ul>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">회원 등급 재설정</th>
					<td class="tleft">
						<div>
							<label><input type="checkbox" name="resetUserLevel" value="Y" />회원 그룹을 설정하면서 회원 등급도 재설정 합니다.</label>
						</div>
					</td>
				</tr>
				<%-- <tr id="levelTr" style="display:none">
					<th scope="row">회원 Level</th>
					<td class="tleft">
						<div>
							<select name="levelId" title="회원 Level">
								<option value="0">Level 미지정</option>
								<c:forEach items="${userLevelGroup}" var="levelGroup">
									<c:set var="groupLabel">${levelGroup.key}</c:set>
									<c:forEach items="${groupList}" var="group">
										<c:if test="${groupLabel == group.groupCode}">
											<c:set var="groupLabel">${group.groupName}</c:set>
										</c:if>
									</c:forEach>
									
									<optgroup label="${groupLabel}">
										<c:forEach items="${levelGroup.value}" var="userLevel">
											<option value="${userLevel.levelId}" ${user.userDetail.levelId == userLevel.levelId ? "selected" : ""}>${userLevel.levelName}</option>
										</c:forEach>
									</optgroup> 
								</c:forEach>
							</select>
						</div>
					</td>
				</tr> --%>
			</tbody>
		</table> <!-- // 기본정보 끝 -->
		
		<div class="tex_c mt20">
			<button type="submit" class="btn btn-active">설정</button>
		</div>
	</form:form>
	
	<a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
$(function(){
	/* $("input[name='resetUserLevel']").on("click",function(){
		if(this.checked){
			$("#levelTr").show();
		}else{
			$("#levelTr").hide();
		}
	}); */
	//$('#groupSetting').validator(function() {
		
	//});
})
</script>

	

