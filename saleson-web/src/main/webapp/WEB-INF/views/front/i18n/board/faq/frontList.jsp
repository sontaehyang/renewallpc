<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>


<board:header />

		<div class="tab_nav col5 tab_links">
			<ul>
				<li <c:if test="${empty boardSearchParam.category}">class="on"</c:if>><a href="<c:url value="/kbboard/faq" />">전체</a></li>
				<li <c:if test="${boardSearchParam.category == 'volunteer'}">class="on"</c:if>><a href="<c:url value="/kbboard/faq?category=volunteer" />">봉사활동</a></li>
				<li <c:if test="${boardSearchParam.category == 'donation'}">class="on"</c:if>><a href="<c:url value="/kbboard/faq?category=donation" />">기부관련</a></li>
				<li <c:if test="${boardSearchParam.category == 'suggestion'}">class="on"</c:if>><a href="<c:url value="/kbboard/faq?category=suggestion" />">고객제안</a></li>
				<li <c:if test="${boardSearchParam.category == 'information'}">class="on"</c:if>><a href="<c:url value="/kbboard/faq?category=information" />">기타이용</a></li>
			</ul>
		</div>
		
		<div class="tab_content faq_list">
			<c:choose>
				<c:when test="${boardSearchParam.category == 'volunteer'}">
					<h4 class="hidden">봉사활동</h4>
				</c:when>
				<c:when test="${boardSearchParam.category == 'donation'}">
					<h4 class="hidden">기부관련</h4>
				</c:when>
				<c:when test="${boardSearchParam.category == 'suggestion'}">
					<h4 class="hidden">고객제안</h4>
				</c:when>
				<c:when test="${boardSearchParam.category == 'information'}">
					<h4 class="hidden">기타이용</h4>
				</c:when>
				<c:otherwise>
					<h4 class="hidden">전체</h4>
				</c:otherwise>
			</c:choose>
			
			<ul>
				<c:forEach items="${list}" var="board" varStatus="i">
					
					<li>
						<dl>
							<dt><img src="/content/images/help/icon_q.png" alt="Q(질문)" /></dt>
							<dd><a href="#">${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}</a>
								 <c:if test="${op:getDaysDiff(fn:substring(board.creationDate, 0, 10)) <= boardContext.boardCfg.showNewIcon}"><span class="icon small orange">new</span></c:if>
							</dd>
							<dt class="answer"><img src="/content/images/help/icon_a.png" alt="A(답변)" /></dt>
							<dd class="answer">
								${op:nl2br(board.content)}
							</dd>
						</dl>
					</li>
				</c:forEach>
					
				
			</ul>
			
			
			
			<c:if test="${empty list}">
			<div class="no_content">
				등록된 글이 없습니다.
			</div>
			</c:if>
			
				
			<div class="board_guide">
				<p class="total">전체 : <em>${op:numberFormat(pagination.totalItems)}</em></p>
			</div>
			
			<page:pagination-manager />
			
		</div>
	
<board:footer />


<script type="text/javascript">
$(function() {
	$('input[name=statusCode]').on("click", function() {
		$('#boardSearchParam').submit();
	});
});


</script>	