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
	<h3>상품문의</h3>
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
				<td colspan="3">
					<div>
						상품문의
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
				<td >
					<div>
						${op:datetime(qna.createdDate)}
					</div>
				</td>
			</tr>
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
		</tbody>
	</table>
</div>
<div class="board_write">
	<h3 class="mt30">
		<span>상품문의 답변하기</span> <!-- 문의내용 --> 
	</h3>
	<form:form modelAttribute="qnaAnswer" action="/opmanager/qna-item/answer/${qna.qnaId}" method="post">
		<form:hidden path="qnaAnswerId" />
			<table class="board_write_table">
				<caption></caption> 
				<colgroup>
					<col style="width:150px;">
					<col style="width:auto;">
				</colgroup>
			
				<tr>
					<td class="label">${op:message('M00476')}</td> <!-- 답변일 --> 
					<td >
						<div>
							${op:datetime(qnaAnswer.createdDate)}
					    </div>
					</td>
				</tr>
				<tr>
					<td class="label">답변제목</td> <!-- 답변제목 -->
					<td>
						<div>
							<form:input path="title" title="답변제목" cssClass="full" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M00477')}</td> <!-- 답변내용 --> 
					<td>
						<div>
							<form:textarea path="answer" rows="5" cols="30" />
						</div>
					</td>						 
				</tr>
				<tr>
					<td class="label">답변전달방식</td> <!-- 답변전달방식 -->
					<td>
						<div>
							<form:checkbox path="sendSmsFlag" title="SMS" value="Y" />SMS
							<form:checkbox path="sendMailFlag" title="이메일" value="Y" />이메일 (* 체크하시면 답변 완료시 동시에 전달이 됩니다.)
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btn_center">
			<div>
				<a href="/opmanager/qna-item/list" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 -->
				<button type="submit" class="btn btn-active">저장</button>
				<button type="button" class="btn btn-default" onclick="location.href='/opmanager/qna-item/view/${qna.qnaId}';"><span>취소</span></button>
			</div>
		</div>							 							
	</form:form>
</div>
								
			