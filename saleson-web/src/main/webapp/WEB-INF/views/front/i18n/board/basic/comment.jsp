<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>


	
	<ul>
		<c:forEach items="${list}" var="comment" varStatus="i">
		<li>
			<span class="name">${comment.userName}</span>
			<span>
				${op:nl2br(comment.comments)}
				<c:choose>
					<c:when test="${(comment.userId != '' && comment.userId == requestContext.user.userId) }">
						<a href="javascript:;" rel="${comment.boardCommentId}" class="delete_comment"><img src="<c:url value="/content/images/common/icon_delete2.png" />" alt="댓글 삭제"></a>
					</c:when>
					<c:when test="${comment.userId == '' && requestContext.login == false}">
						<a href="javascript:;" rel="${comment.boardCommentId}" class="delete_comment_anonymous"><img src="<c:url value="/content/images/common/icon_delete2.png" />" alt="댓글 삭제"></a>
					</c:when>
				</c:choose>	
			</span>
			<span>${fn:substring(comment.creationDate, 0, 10)}</span>
		</li>
		</c:forEach>
	</ul>
 	<page:pagination />


