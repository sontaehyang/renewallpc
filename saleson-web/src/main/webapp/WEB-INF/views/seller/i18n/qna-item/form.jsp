<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop" uri="/WEB-INF/tlds/shop" %>
    
    
    <div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>
	
	<div class="board_write">
		<table class="board_write_table">
			<colgroup>
				<col style="width:150px;">
				<col style="width:auto;">
				<col style="width:150px;">
				<col style="width:auto;">
			</colgroup>
			<h3>상품문의</h3>
			<tbody>
				<tr>
					<td class="label">${op:message('M00460')}</td> <!-- 문의구분 --> 
					<td colspan="3">
						<div>상품문의</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M00472')}</td> <!-- 작성자 --> 
					<td>
						<div>
							<a href="/seller/user/popup/details/${qna.userId}">${qna.userName}/ ${qna.email}</a>
						</div>
					</td>
					<td class="label">${op:message('M00276')}</td> <!-- 작성일 --> 
					<td>
						<div>
							${op:date(qna.createdDate)}
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
							${op:nl2br(qna.question)}
						</div>
					</td>						 
				</tr>
			</tbody>
		</table>
	</div>
					
					
	<div class="board_write">
		<form id="qnaParam" method="post" action="/opmanager/qna-item/list">
			<input type="hidden" name="answerCount" value="${qnaParam.answerCount}" />
			<input type="hidden" name="qnaId" value="${qnaParam.qnaId}"/>
			<input type="hidden" name="sellerId" value="${qnaParam.sellerId}"/>
			<input type="hidden" name="searchStartDate" value="${qnaParam.searchStartDate}"/>
			<input type="hidden" name="searchEndDate" value="${qnaParam.searchEndDate}"/>
			<input type="hidden" name="where" value="${qnaParam.where}"/>
			<input type="hidden" name="query" value="${qnaParam.query}"/>
			<input type="hidden" name="itemsPerPage" value="${qnaParam.itemsPerPage}"/>
		</form>
		<form:form modelAttribute="qnaAnswer" action="/seller/qna-item/answer/${qna.qnaId}" method="post">
			<input type="hidden" name="qnaAnswerId" value="${qnaAnswer.qnaAnswerId}" />
			<input type="hidden" name="userId" value="${seller.sellerId}" />
			<table class="board_write_table">
				<caption></caption> 
				<colgroup>
					<col style="width:150px;">
					<col style="width:auto;">
				</colgroup>
				<br/>		
				<h3>
					<span>상품문의 답변하기</span> <!-- 문의내용 --> 
				</h3>	
			
				 <!-- 2015.1.5 화면변경 -->
				<div style="display:none">
					<tr>
						<td class="label">답변제목</td> <!-- 답변제목 -->
						<td>
							<div>
								<form:input path="title" title="답변제목" class="half" />
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
								<form:checkbox path="sendSmsFlag" title="SMS" value="Y" label="SMS" />
								<form:checkbox path="sendMailFlag" title="이메일" value="Y" label="이메일" /> (* 체크하시면 답변 완료시 동시에 전달이 됩니다.)
							</div>
						</td>
					</tr>
				</div>
			</table>
			
			<div class="btn_center">
				<div>
					<%-- <a href="/seller/qna-item/list" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 --> --%>
					<a href="javascript:qnaList();" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 -->
					<button type="submit" class="btn btn-active">저장</button>
					<button type="button" class="btn btn-default" onclick="location.href='/seller/qna-item/view/${qna.qnaId}'"><span>취소</span></button>
				</div>
			</div>							 							
		</form:form>
	</div>

<script type="text/javascript">
function qnaList() {
	$("#qnaParam").attr("method", "get").attr("action", "/seller/qna-item/list").submit();
}

</script>		
			