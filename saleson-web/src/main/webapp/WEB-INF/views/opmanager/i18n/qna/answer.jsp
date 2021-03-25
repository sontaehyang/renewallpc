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
<div class="board_write">
	<h3>1:1문의</h3>
	<table class="board_write_table">
		<colgroup>
			<col style="width:150px;">
			<col style="width:auto;">
			<col style="width:150px;">
			<col style="width:auto;">
		</colgroup>
		<tbody>
			<tr>
				<td class="label">${op:message('M00460')}</td> <!-- 문의구분 --> 
				<td>
					<div>
						${qnaAnswerTypeLabel}
					</div> 
				</td>
					
				<td class="label">${op:message('M00467')}</td> <!-- 회원여부 -->
				<td>
					<div>
						${qna.userId > 0 ? op:message('M00465') : op:message('M00466')} <!-- 회원 --> <!-- 비회원 -->
				    </div>
				</td>
			</tr>
			<tr>
				<td class="label">${op:message('M00472')}</td> <!-- 작성자 --> 
				<td>
					<div>
						<a href="/opmanager/user/popup/details/${qna.userId}">${qna.userName}/ ${qna.email}</a>
					</div>
				</td> 
				<td class="label">${op:message('M00276')}</td> <!-- 작성일 --> 
				<td>
					<div>
						${op:datetime(qna.createdDate)}
					</div>
				</td>
			</tr>
			<tr>
										 
			<tr>
				<td class="label">제목</td> <!-- 제목 -->
				<td colspan="3">
					<div>
						${qna.subject}
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">내용</td> <!-- 내용 -->
				<td colspan="3">
					<div>
						${ op:nl2br(qna.question) }
					</div>
				</td>						 
			</tr> 
			<tr>
				<td class="label">공개여부</td> <!-- 공개여부 -->
				<td colspan="3">
					<div>
						<c:choose>
							<c:when test="${qna.displayFlag == 'Y'}">
								공개
							</c:when>
							<c:otherwise>
								비공개
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="board_write">
	<h3 class="mt30">
		<span>1:1문의 답변하기</span> 
	</h3>
	<form:form modelAttribute="qnaAnswer" action="/opmanager/qna/answer/${qna.qnaId}" method="post">
		<input type="hidden" name="email" value="${qna.email}" />
		<input type="hidden" name="subject" value="${qna.subject}" />
		<input type="hidden" name="userName" value="${qna.userName}" />
		<form:hidden path="qnaAnswerId" />
		
		<table class="board_write_table">
			<caption></caption> 
			<colgroup>
				<col style="width:150px;">
				<col style="width:auto;">
				<col style="width:150px;">
				<col style="width:auto;">
			</colgroup>
		
			<tbody>		
				<tr>
					<td class="label">${op:message('M00475')}</td> <!-- 답변자 --> 
					<td>
						<div>
							${qnaAnswer.userId}
					    </div>
					</td>
					<td class="label">${op:message('M00476')}</td> <!-- 답변일 --> 
					<td>
						<div>
							${op:datetime(qnaAnswer.createdDate)}
					    </div>
					</td>
				</tr>
				<tr>
					<td class="label">답변제목</td> <!-- 답변제목 -->
					<td colspan="3">
						<div>
							<form:input path="title" cssClass="full" title="답변제목" maxlength="50"/>
						</div>
					</td>
				</tr> 
				<tr>
					<td class="label">${op:message('M00477')}</td> <!-- 답변내용 --> 
					<td colspan="3">
						<div>
							<form:textarea path="answer" rows="5" cols="30" />
						</div>
					</td>						 
				</tr>
				<tr>
					<td class="label">답변전달방식</td> <!-- 답변전달방식 -->
					<td colspan="3">
						<div>
							<form:checkbox path="sendSmsFlag" title="SMS" value="Y" label=" SMS" />
							<form:checkbox path="sendMailFlag" title="이메일" value="Y" label=" 이메일" />(* 체크하시면 답변 완료시 동시에 전달이 됩니다.)
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btn_center">
			<div>
				<%-- <a href="/opmanager/qna/list" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 --> --%>
				<a href="javascript:qnaList();" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 -->
				<button type="submit" class="btn btn-active">저장</button>
				<button type="button" class="btn btn-default" onclick="location.href='/opmanager/qna/view/${qnaId}';"><span>취소</span></button>
			</div>
		</div>							 							
	</form:form>
</div>	

<page:javascript>
<!-- 2015.1.15 QNA 검증 -->
<script type="text/javascript">
$(function() {
	Common.checkedMaxStringLength('textarea[name=answer]',null, 500);
});
function qnaList() {
	$("#qnaParam").attr("method", "get").attr("action", "/opmanager/qna/list").submit();
}
</script>
</page:javascript>