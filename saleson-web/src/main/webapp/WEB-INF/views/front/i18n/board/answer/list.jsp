<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>

<c:if test="${requestContext.opmanagerPage == true || ngoPage == true}">
<p class="guide">${boardContext.boardCfg.subject} 목록을 관리합니다.</p>
</c:if>

<board:header />



		<div class="board_list">
			<div class="sort_area">
				<form:form modelAttribute="boardSearchParam" action="${boardContext.boardBaseUri}" method="post">
				<fieldset>
						<legend class="hidden">검색</legend>
						
						<div class="left">
							<c:if test="${boardInfo.statusView}">
								<span>
									<c:choose>
										<c:when test="${boardContext.boardCfg.writeAuthority == 'ROLE_OPMANAGER'}">
											<form:radiobutton path="statusCode" value="8" label="확인중" checked="true" />
											<form:radiobutton path="statusCode" value="7" label="반송" />
										</c:when>
										<c:otherwise>
											<form:radiobutton path="statusCode" value="1" label="미확인" checked="true" />
											<form:radiobutton path="statusCode" value="8" label="확인중" />
											<!-- 
											<form:radiobutton path="statusCode" value="7" label="반송" />
											 -->
										</c:otherwise>
									</c:choose>
									
									<form:radiobutton path="statusCode" value="" label="전체" title="상태"/>
								</span>
							</c:if>
							
							<c:if test="${boardContext.boardCfg.useAnswer == '1'}">
								<c:if test="${boardInfo.statusView}">
								<span class="line">|</span>
								</c:if>
								<span>답변여부</span>
								<span>
									<form:select path="answerStatusCode" onchange="this.form.submit()" title="답변여부">
										<form:option value="">전체</form:option>
										<form:option value="2">답변</form:option>
										<form:option value="1">미답변</form:option>
									</form:select>
								</span>
							</c:if>
							
							<c:if test="${boardContext.boardCfg.useCategory == '1'}">
							<span class="line">|</span>
							<span>분야</span>
							<form:select path="category" onchange="this.form.submit()" title="분류선택">
								<form:option value="">전체</form:option>
								<form:options items="${boardContext.boardCfg.categories}" itemLabel="label" itemValue="value" />
							</form:select>
							</c:if>
							
								
						</div>
						
						<div class="right">
							
							<form:select path="where" title="검색조건">
								<form:option value="SUBJECT">제목</form:option>
								<form:option value="SUBJECT_CONTENT">제목+내용</form:option>
							</form:select>
							<form:input path="query" class="input_txt required _filter" title="검색어" />
							<button type="submit"><span class="icon_search">검색</span></button>
						</div>
					</fieldset>
				</form:form>
			</div>
			
			<table class="board_list_table">
				<caption class="hidden">${boardContext.boardCfg.etc10}</caption>
				<colgroup>
				
					<c:if test="${boardContext.boardAuthority.boardAdmin == true}">
					<!-- 전체 선택 
					<col style="width: 30px" />
					 -->
					</c:if>
				
					<col style="width: 60px" />
					
					<c:if test="${boardContext.boardCfg.useCategory == 1}">
           			<col style="width: 100px" />
            		</c:if>
            			
					<col style="width: auto" />
					
					<!-- 
					<col style="width: 70px" />
					-->
						
					<c:if test="${requestContext.opmanagerPage && boardContext.boardCfg.etc1 == '1' && boardContext.boardAuthority.boardAdmin == true}">
           			<col style="width: 70px" />
            		</c:if>	
						
					<col style="width: 110px" />
	
					
					<c:if test="${boardInfo.statusView}">
					<col style="width: 65px" />
					</c:if>
				</colgroup>
				<thead>
					<tr>
						<c:if test="${boardContext.boardAuthority.boardAdmin == true}">
						<!--
						<th scope="col"><input type="checkbox" id="check_all" /></th>
						-->
						</c:if>
			
						<th scope="col">순번</th>
						
						<c:if test="${boardContext.boardCfg.useCategory == 1}">
           				<th scope="col">분야</th>
            			</c:if>
            
						<th scope="col">제목</th>
						<!-- 
						<th scope="col">작성자</th>
						-->
						<c:if test="${requestContext.opmanagerPage && boardContext.boardCfg.etc1 == '1' && boardContext.boardAuthority.boardAdmin == true}">
						<th scope="col">게시여부</th>
						</c:if>
						
						<th scope="col">등록일</th>
						
						
						<c:if test="${boardInfo.statusView}">
						<th scope="col">상태</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
				
				
				
				<c:forEach items="${list}" var="board" varStatus="i">
					<c:set var="open">미게시</c:set>
					<c:if test="${board.etc1 == '1'}">
						<c:set var="open"><span class="important">게시</span></c:set>
					</c:if>
				
					<tr>
						<c:if test="${requestContext.opmanagerPage && boardContext.boardAuthority.boardAdmin == true}">
						<!-- 
						<td><input type="checkbox" name="boardId[]" class="checkAll" value="${user.boardId}" /></td>
						 -->
						</c:if>
						
						<td>${pagination.itemNumber - i.count}</td>	
						
						<c:if test="${boardContext.boardCfg.useCategory == 1}">
			            <td>${op:getCategoryName(boardContext.boardCfg.categoryList, board.category)}</td>
			            </c:if>
			            
						<td class="title">
							<c:if test="${board.depth > 0}">
								<img src="${boardContext.images}/icon_reply.gif" alt="reply" style="margin-left:${board.depth * 12}px" />
							</c:if>
							
							<sec:authorize access="hasRole('ROLE_KB,ROLE_CUST')">
							<a href="${boardContext.boardBaseUri}/${board.boardId}?url=${requestContext.currentUrl}">${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}</a>
							</sec:authorize>

							<sec:authorize access="hasRole('ROLE_KB,ROLE_CUST')">
							${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}
							</sec:authorize>
							
							<c:if test="${board.fileCount > 0}"><img src="/content/images/common/icon_clip2.png" alt="첨부파일 있음" /> </c:if>
							<c:if test="${board.answerStatusCode == '2'}"><span class="icon small yellow">답변</span></c:if>
							<c:if test="${op:getDaysDiff(fn:substring(board.creationDate, 0, 10)) <= boardContext.boardCfg.showNewIcon}"><span class="icon small orange">new</span></c:if>
							<c:if test="${boardContext.boardCfg.useComment == 1 && board.commentCount > 0}">
								<span class="icon_reply"><span class="hidden">댓글</span>${board.commentCount}</span>
							</c:if>
						</td>	
						
						
						
						<!-- 
						<td>${board.userName}</td>	
						-->
						
						<c:if test="${requestContext.opmanagerPage && boardContext.boardCfg.etc1 == '1' && boardContext.boardAuthority.boardAdmin == true}">
						<td>${open}</td>	
						</c:if>
						
						<td>${fn:substring(board.creationDate, 0, 10)}</td>	

						
						
						<c:if test="${boardInfo.statusView}">
							<c:choose>
								<c:when test="${board.statusCode == '1'}">
									<td><span class="icon red">미확인</span></td>
								
								</c:when>
								<c:when test="${board.statusCode == '7'}">
								
									<td>
										<span class="icon purple">반송</span>
										<div class="tooltip_wrap reject_cause">
											<a href="#" class="icon_reject tooltip">반송</a>
											<div class="tooltip_area">
												<div>
													<p>반송사유</p>
													<p>${board.etc10}</p>
												</div>
											</div>
										</div>
									</td>
								
								</c:when>
								
								<c:when test="${board.statusCode == '8'}">
									<td><span class="icon blue">확인중</span></td>
								
								</c:when>
								
								<c:when test="${board.statusCode == '9'}">
									<td><span class="icon green">확인</span></td>
								
								</c:when>
							
							</c:choose>
						
						
						</c:if>
					</tr>
				</c:forEach>
					
				</tbody>
			</table>
			
			<c:if test="${empty list}">
			<div class="no_content">
				등록된 글이 없습니다.
			</div>
			</c:if>
			
				
			<div class="board_guide">
				<p class="total">전체 : <em>${op:numberFormat(pagination.totalItems)}</em></p>
			</div>
			
			<page:pagination-manager />
			

			<p class="btns">
				<span class="right">
					<c:if test="${!requestContext.opmanagerPage}">
						<c:choose>
							<c:when test="${boardContext.boardAuthority.writeAuthority}">
								<a href="${boardContext.boardBaseUri}/write?url=${requestContext.currentUrl}" class="btn ${btnClass}">등록</a>
							</c:when>	
							<c:otherwise>
								<sec:authorize access="hasRole('ROLE_KB')">
									<sec:authorize access="!hasRole('ROLE_CUST')">
										<a href="javascript:login('${boardContext.boardBaseUri}')" class="btn ${btnClass}">등록</a>
										
									</sec:authorize>
								</sec:authorize>
								
								<sec:authorize access="!hasRole('ROLE_KB,ROLE_CUST')">
									<a href="<c:url value="/user/login?target=/kbboard/askdonation" />" class="btn ${btnClass}">등록</a>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
					</c:if>
				</span>
			</p>
			
		</div>
	
<board:footer />





<script type="text/javascript">
$(function() {

	$('input[name=statusCode], input[name=answerStatusCode]').on("click", function() {
		$('#boardSearchParam').submit();
	});
});

function login(link) {
	if (confirm('고객 전용 메뉴입니다.\n고객으로 로그인 하시겠습니까?')) {
		location.href = url('/user/login?target=' + link);
	}
}

</script>	