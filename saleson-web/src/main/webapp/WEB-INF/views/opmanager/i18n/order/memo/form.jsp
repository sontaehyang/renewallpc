<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<!-- 본문 -->
<div class="popup_wrap">
	<h1 class="popup_title">상담등록</h1>
	<div class="popup_contents">
		<form:form modelAttribute="memo" method="post">
			<form:hidden path="orderCode" />
			<form:hidden path="claimMemoId" />
			<table class="board_write_table">
	 			<caption>${op:message('M01571')}</caption>
	 			<colgroup>
	 				<col style="width:20%;">
	 				<col style="width:auto;">
	 			</colgroup>
	 			<tbody>
	 				<tr>
	 					<td class="label">회원이름</td> 
	 					<td>
	 						<div>
	 							${user.userName}
	 							<p>${user.loginId}</p>
	 						</div>
	 					</td>
	 				</tr>
	 				
	 				<tr>
	 					<td class="label">처리상태</td> 
	 					<td>
	 						<div>
	 							<form:select path="claimStatus">
									<form:option value="2" label="처리완료" />
									<form:option value="1" label="처리중" />
								</form:select>
	 						</div>
	 					</td>
	 				</tr>
	 				
	 				<tr>
	 					<td colspan="2">
	 						<form:textarea path="memo" maxlength="200" cssClass="required" style="height:200px;" title="고객상담 메모" />
	 					</td> 
	 				</tr>
	 			</tbody>
	 		</table>
	 		
	 		<p class="popup_btns">
				<button type="submit" class="btn btn-active w250"><span>저장</span></button>
			</p>
		</form:form>
	</div>
	<a href="#" class="popup_close">창 닫기</a>
</div>

<script type="text/javascript">
	$(function(){
		$('#claimMemo').validator({
			'submitHandler' : function() {
				
			}
		});
	});
</script>