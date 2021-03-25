<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

			<table cellpadding="0" cellspacing="0" class="board-list">
				<colgroup>
					<col style="width:60px;">
					<col style="width:150px;"> 
					<col style="width:auto;">
					<col style="width:120px;">
					<col style="width:110px;">
					<col style="width:120px;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">문의유형 </th>
						<th scope="col">제목</th>
						<th scope="col">작성자</th>
						<th scope="col">작성일</th>
						<th scope="col">답변상태</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach items="${qnaList}" var="qna" varStatus="i">
						<tr class="tit-off" id="questTit12">
							<td>${pagination2.itemNumber - i.count}</td>
							<td>${qna.qnaGroup}</td>
							<td class="tit subject">
								<c:set var="secretFlag">${qna.secretFlag}</c:set>
								<c:if test="${qna.userId == requestContext.user.userId}">
									<c:set var="secretFlag">N</c:set>
								</c:if>							
								<a href="${secretFlag == 'Y' ? 'javascript:secret();' : '#'}" class="${secretFlag == 'Y' ? '' : 'review_subject'}">
								
									<c:choose>
										<c:when test="${empty qna.subject}">
											${op:strcut(qna.question, 40)}
										</c:when>
										<c:otherwise>
											${qna.subject}
										</c:otherwise>
									</c:choose>
									
									<c:if test="${qna.secretFlag == 'Y'}">
										<span><img src="/content/images/icon/icon_rock.png" alt="rock"></span>
									</c:if>
								</a>
							</td>	
							<td class="border">${qna.maskUsername}</td>
							<td>${op:date(qna.createdDate)}</td>	
							<td> 
								<c:choose>
									<c:when test="${qna.answerCount == 0}">
										<span class="icon_answer answer_ing">답변대기</span>
									</c:when>
									<c:otherwise>
										<span class="icon_answer answer_end">답변완료</span>
									</c:otherwise>
								</c:choose>
								
							</td>		
						</tr>
						<c:if test="${secretFlag != 'Y'}">
						<tr class="view-off" id="quest12">
							<td colspan="6" class="question-open">
								<div class="qna-q">	 								  
									<p>${op:nl2br(qna.question)}</p>								 
								</div> 	 
								
								<c:if test="${qna.answerCount > 0}">
								<div class="qna-a"> 
									<p>
										${op:nl2br(qna.qnaAnswer.title)}
										 </br>
										 ${op:nl2br(qna.qnaAnswer.answer)}
										 </br>
										 (답변일 : ${op:date(qna.qnaAnswer.answerDate)})
									</p>
									<br> 
								</div>	
								</c:if>							
							</td>
						</tr> 
						</c:if>
						
						
					</c:forEach>
									  					
				</tbody>
			</table>
			
			<c:if test="${empty qnaList}">
			<div class="no_content">
				등록된 상품 Q&A가 없습니다.
			</div>
			</c:if>
			<page:pagination2 />