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
			<a href="#"></a>&gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
					
		<div class="board_write">
			<form:form modelAttribute="qna" method="post" action="/opmanager/qna/delete/${qnaId}">
				<h3>1:1 문의 </h3>
				<form:hidden path="qnaId" />
				<form:hidden path="qnaGroup" />
				<table class="board_write_table" summary="${op:message('M00459')}">
					<caption>${op:message('M00458')}</caption>
					<colgroup>
						<col style="width:150px;" />
						<col style="width:*;" />
						<col style="width:150px;" />
						<col style="width:*;" />
					</colgroup>
					
					<tbody>
						 <tr>
						 	<div>
							 	<td class="label">${op:message('M00460')}</td> <!-- 문의유형 -->
							 	<td>
							 		<div>
							 			${qnaGroups}
									</div>
								</td>
							</div>
							<div>
								<td class="label">회원여부</td>
							 	<td>
							 		<div>
										${qna.userId > 0 ? op:message('M00465') : op:message('M00466')} <!-- 회원 --> <!-- 비회원 -->
							    	</div>
							 	</td>
							</div>
						 </tr>
						<tr>
							<td class="label">작성자</td>
							<td>
								<div>
									<c:choose>
										<c:when test="${qna.userId > 0}">
											<a href="javascript:Common.popup('/opmanager/user/popup/details/${qna.userId}', '/user/popup/details', 1100, 800 ,1, 0, 0)">${qna.userName}(${qna.email})</a>
							    		</c:when>
							    	
								    	<c:otherwise>
								    		-
								    	</c:otherwise>
							    	</c:choose>
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
						<tr>
							<td class="label">공개여부</td> <!-- 공개여부 -->
							<td colspan="3">
								<div>
									${qna.displayFlag == "Y" ? op:message('M00096') : op:message('M00097')}
								</div>
							</td>
						</tr>		
					</tbody>	
				</table>								 							
			</form:form>
		</div><br/>
		
			<div class="board_write">
				<c:choose>
					<c:when test="${qnaAnswer.answer > '1'}">
						<form:form modelAttribute="qnaAnswer" action="/opmanager/qna/view/${qna.qnaId}">
							<tbody>
								<table class="board_write_table">
									<colgroup>
										<col style="width:150px;">
										<col style="width:auto;">
									</colgroup>
									<h3>답변 내용</h3>
									<tr>
										<div>
											<td class="label">답변자</td> <!-- 답변자 --> 
											<td>
												<div>
													<c:if test="${qnaAnswer.userId > 0}">
														${adminUser.userName}
													</c:if>			
												</div>								
											</td>
										</div>
										<div>
											<c:if test="${qnaAnswer.answer < '0'}">	
												<td class="label">답변일</td> <!-- 답변일 --> 
												<td>
													<div>
														${op:date(qnaAnswer.answerDate)}
												    </div>
												</td>
											</c:if>	
										</div>
									</tr>
									<tr>
										<div>
											<td class="label">답변제목</td> <!-- 답변제목 -->
											<td>
												<div>
													${qnaAnswer.title}
												</div>
											</td>
										</div>
									</tr>
									<tr>
										<td class="label">${op:message('M00477')}</td> <!-- 답변내용 --> 
										<td>
											<div>
												${op:nl2br(qnaAnswer.answer)}
											</div>
										</td>						 
									</tr>
								</table>
							</tbody>
						</form:form>
						<div class="btn_center">
							<a href="/opmanager/qna/list" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 -->
							<a href="/opmanager/qna/delete/${qnaId}" id="delete_data" class="btn btn-default">삭제</a>
							<a href="/opmanager/qna/edit/${qnaId}" class="btn btn-active">수정</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="btn_center">
							<a href="/opmanager/qna/list" class="btn btn-active"><span>${op:message('M00480')}</span></a> <!-- 목록 -->
							<a href="/opmanager/qna/delete/${qnaId}" id="delete_data" class="btn btn-default">삭제</a>
							<a href="/opmanager/qna/answer/${qnaId}" class="btn btn-active">답변등록</a>
						</div>	
					</c:otherwise>
				</c:choose>
			</div>	
		
<page:javascript>		
<script type="text/javascript">
	$(function() {
		$('#delete_data').on('click', function() {
			if (!confirm("삭제된 게시글을 복구할 수 없습니다. 정말로 삭제하시겠습니까?")) {
				Common.loading.hide();
				return false;
			}
		});
	});
</script>
</page:javascript>