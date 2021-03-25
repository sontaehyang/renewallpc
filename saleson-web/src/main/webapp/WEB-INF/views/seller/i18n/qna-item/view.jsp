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
		<form id="qnaParam" method="post" action="/seller/qna-item/list">
			<input type="hidden" name="answerCount" value="${qnaParam.answerCount}" />
			<input type="hidden" name="searchStartDate" value="${qnaParam.searchStartDate}"/>
			<input type="hidden" name="searchEndDate" value="${qnaParam.searchEndDate}"/>
			<input type="hidden" name="where" value="${qnaParam.where}"/>
			<input type="hidden" name="query" value="${qnaParam.query}"/>
			<input type="hidden" name="itemsPerPage" value="${qnaParam.itemsPerPage}"/>
			<input type="hidden" name="qnaId" value="${qnaId}" />
		</form>
		
		<form:form modelAttribute="qna" method="post" action="/seller/qna-item/delete/${qnaId}">
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M00459')}">
					<caption>${op:message('M00458')}</caption>
					<colgroup>
						<col style="width:150px;" />
						<col style="width:*;" />
						<col style="width:150px;" />
						<col style="width:*;" />
					</colgroup>
					
					<h3>상품문의 </h3>
					<form:hidden path="qnaId" />
					<form:hidden path="qnaGroup" />
						
					<tbody>
						 <tr>
						 	<div>
							 	<td class="label">작성자</td>
							 	<td>
							 		<div>
							   			<c:choose>
							   				<c:when test="${qna.userId > 0}">
							   					<%-- <a href="javascript:Common.popup('/seller/user/popup/details/${qna.userId}', '/user/popup/details', 1100, 800 ,1, 0, 0)"> --%>
							   					${qna.userName}(${qna.email})
							   					<!-- </a> -->
							   				</c:when>
							   				<c:otherwise> - </c:otherwise>
							   			</c:choose>
							   		</div>
								</td>
							</div>	
							<div>
								<td class="label">작성일</td>
							 	<td colspan="3">
							 		<div>
							 			${op:datetime(qna.createdDate)}
							    	</div>
							 	</td>
							 </div>
						</tr>
						<tr>
							<td class="label">상품명</td>
							<td colspan="5">
								<div>
									<c:choose>
										<c:when test='${op:property("saleson.view.type") eq "api"}'>
											<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${qna.itemUserCode}" target="_blank">${qna.itemName}</a>
										</c:when>
										<c:otherwise>
											<a href="/products/preview/${qna.itemUserCode}" target="_blank">${qna.itemName}</a>
										</c:otherwise>
									</c:choose>

							    </div>
							</td>
						</tr>
						<tr>
							<td class="label">제목</td>  
							<td colspan="5">
								<div>
									${qna.subject}
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td colspan="5">
								<div>
									${op:nl2br(qna.question)}
							    </div>
							</td>
						</tr>								
					</tbody>	
				</table>								 							
			</div><br/>
		</form:form>
		
		<div class="board_write">
			<c:choose>
				<c:when test="${qnaAnswer.qnaAnswerId > 0}">
					<form:form modelAttribute="qnaAnswer" action="/seller/qna-item/answer/${qna.qnaId}" method="post">
						<tbody>
							<table class="board_write_table">
								<colgroup>
									<col style="width:150px;">
									<col style="width:auto;">
								</colgroup>
								<h3>상품문의 답변하기</h3> 
								 <!-- 2015.1.5 화면변경 -->
								<tr>
									<div>
										<td class="label">답변자</td> <!-- 답변자 --> 
										<td colspan="4">
											<div>
												<c:if test="${qnaAnswer.userId > 0}">
													${qnaAnswer.userNm}
												</c:if>
										    </div>
										</td>
									</div>
								</tr>
								<tr>
									<td class="label">답변제목</td> <!-- 답변제목 -->
									<td colspan="4">
										<div>
											${qnaAnswer.title}
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00477')}</td> <!-- 답변내용 --> 
									<td colspan="4">
										<div>
											${op:nl2br(qnaAnswer.answer)}
										</div>
									</td>
								</tr>
							</table>
						</tbody>
					</form:form>
					<div class="btn_center">
						<%-- <a href="/seller/qna-item/list" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 --> --%>
						<a href="javascript:qnaItemList();" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 -->
						<a href="/seller/qna-item/delete/${qnaId}" id="delete_data" class="btn btn-default">삭제</a>
						<%-- <a href="/seller/qna-item/answer/${qnaId}" class="btn btn-active">수정</a> --%>
						<a href="javascript:qnaAnswer(${qnaId});" class="btn btn-active">수정</a>
					</div>
				</c:when>
				<c:otherwise>
					<!-- 버튼시작 -->
					<div class="btn_center">
						<%-- <a href="/seller/qna-item/list" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 --> --%>
						<a href="javascript:qnaItemList();" class="btn btn-default"><span>${op:message('M00480')}</span></a> <!-- 목록 -->
						<a href="/seller/qna-item/delete/${qnaId}" id="delete_data" class="btn btn-default">삭제</a>
						<%-- <a href="/seller/qna-item/answer/${qnaId}" class="btn btn-active">답변등록</a> --%>
						<a href="javascript:qnaAnswer(${qnaId});" class="btn btn-active">답변등록</a>
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
	function qnaItemList() {
        $('#qnaParam').attr("method", "get").submit();
	}
	function qnaAnswer(qnaId) {
        $('#qnaParam').attr("method", "get").attr('action', '/seller/qna-item/answer/' + qnaId).submit();
	}
</script>
</page:javascript>