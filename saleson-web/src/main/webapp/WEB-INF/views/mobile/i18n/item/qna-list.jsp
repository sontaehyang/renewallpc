<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

					<c:if test="${!empty qnaList}">
						<c:forEach items="${qnaList}" var="qna" varStatus="i">
						
							<c:set var="secretFlag">${qna.secretFlag}</c:set>
							<c:if test="${qna.userId == requestContext.user.userId}">
								<c:set var="secretFlag">N</c:set>
							</c:if>
							
							<li>
								<c:if test="${i.first}">
									<div id="page-${pagination2.currentPage}" style="display: none;">
										<div class="total-items">${pagination2.totalItems}</div>
										<div class="total-pages">${pagination2.totalPages}</div>
									</div>
								</c:if>
								<div class="main_txt">
									<c:set var="secretFlag">${qna.secretFlag}</c:set>
									<c:if test="${qna.userId == requestContext.user.userId}">
										<c:set var="secretFlag">N</c:set>
									</c:if>
									<a href="#;" class="main_txt ${secretFlag == 'Y' ? 'secret' : ''}">
										<c:choose>
											<c:when test="${qna.answerCount == 0}">
												<span class="status_off">답변대기</span>
											</c:when>
											<c:otherwise>
												<span class="status_on">답변완료</span>
											</c:otherwise>
										</c:choose>
										<span class="date">${op:date(qna.createdDate)}</span>
										<span class="id">${qna.maskUsername}</span>
										<p class="content">
											<c:choose>
												<c:when test="${empty qna.subject}">
													${op:strcut(qna.question, 30)}
												</c:when>
												<c:otherwise>
													${qna.subject}
												</c:otherwise>
											</c:choose>
										</p>
									</a>
								</div> 
								<c:if test="${secretFlag != 'Y'}">
									<div class="sub_txt" style="display: none;">
										<p class="question">
											<p class="question">${op:nl2br(qna.question)}</p> 
										</p>
										<c:if test="${qna.answerCount > 0}">
											<p class="answer">
												${op:nl2br(qna.qnaAnswer.title)}
												<br/>
												${op:nl2br(qna.qnaAnswer.answer)} 
												<span class="a_date">
													(답변일 : <span>${op:date(qna.qnaAnswer.answerDate)}</span>) 
												</span>
											</p>
										</c:if>							
									</div>
								</c:if>
							</li>
						</c:forEach>
					</c:if>	
					<c:if test="${empty qnaList}">
						<li>
							<p class="review_none">등록된 문의가 없습니다.</p>
						</li>
					</c:if>
