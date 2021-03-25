<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<h2><span>¶ ${op:message('M00830')} <!-- 회원탈퇴 --></span></h2>
	<form:form modelAttribute="user" method="post">
		<div class="board_write">
			<table class="board_write_table" summary="회원탈퇴에 관련한 정보를 입력하는 칸입니다.">
				<caption>회원탈퇴</caption>
				<colgroup>
					<col style="width:15%;" />
					<col style="width:85%;" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00868')}</td> <!-- 탈퇴사유 -->
						<td>
							<div>				<!-- 탈퇴사유 -->				 
								<textarea name="leaveReason" cols="30" rows="6" title="${op:message('M00868')}" class="required">${user.userDetail.leaveReason}</textarea>
						 	</div>
						</td>
					</tr>
				</tbody>
			</table>						 
		</div> <!--// board_write E-->
		
		<div class="popup_btns">
			<button type="submit" class="btn btn-active">${op:message('M00101')}</button> <!-- 저장 -->
		</div>
	</form:form>			

<script type="text/javascript">
$(function() {
	$('#user').validator(function() {
		
		Common.confirm("회원을 탈퇴 시키겠습니까?", function() {
			$("#user").submit();
		});
		
		return false;
	});
	// 메뉴 활성화
	Manager.activeUserDetails("secede");
});
</script>
