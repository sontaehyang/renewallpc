<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
	
	<c:if test="${!empty list}">
		<ul>
			<c:forEach items="${list}" var="comment" varStatus="i">
			<li>
				<span class="name">${comment.userName}</span>
				<span class="contents">
					${op:nl2br(comment.comments)}
					<c:choose>
						<c:when test="${(comment.userId != '' && comment.userId == requestContext.user.userId) }">
							<a href="javascript:deleteBoard('${comment.boardCommentId}');" rel="${comment.boardCommentId}" class="delete_comment"><img src="/content/images/btn/btn_file_close.gif" alt="댓글 삭제"></a>
						</c:when>
						<c:when test="${comment.userId == '' && requestContext.login == false}">
							<a href="javascript:deleteBoard('${comment.boardCommentId}');" rel="${comment.boardCommentId}" class="delete_comment_anonymous"><img src="/content/images/btn/btn_file_close.gif" alt="댓글 삭제"></a>
						</c:when>
					</c:choose>	
				</span>
				<span class="created-date">${op:date(comment.creationDate)}</span>
			</li>
			</c:forEach>
			<page:pagination />
		</ul>
	</c:if>