<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.onlinepowers.framework.context.*"%>

<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<c:set var="imageFileCount" value="0" />
<c:forEach items="${board.boardFiles}" var="file">
	<c:if test="${file.uploadType == '0'}">
		<c:set var="imageFileCount">${imageFileCount + 1}</c:set>
	</c:if>						
</c:forEach>


<board:header-mobile />


<header class="title_menu">
	<h2>${boardContext.boardCfg.subject}</h2>
	<a href="${requestContext.prevPageUrl}" class="back"><span class="icon_back">이전</span></a>
</header>
<article class="contents board_view">
	<header>
		<h3>${board.subject}</h3>
		<p>
			<span class="date">${fn:substring(board.creationDate, 0, 10)}</span>
			
			<c:if test="${board.fileCount > 0}">
				<c:forEach items="${board.boardFiles}" var="file">	
					<c:if test="${file.uploadType == '0'}">
						<a href="${boardContext.boardBaseUri}/${board.boardId}/download/${file.fileId}"><span class="icon_file">첨부파일(${file.fileName})</span></a>
					</c:if>
				</c:forEach>
			</c:if>

			
			
			
		</p>
	</header>
	<section>
		<c:if test="${imageFileCount > 0}">
			<c:forEach items="${board.boardFiles}" var="file">
				<p class="img">
					<c:if test="${file.uploadType == '0'}">
					<img src="/upload/${boardContext.boardCode}/${board.boardId}/${file.fileId}.${file.fileType}" alt="${file.fileDescription}" />
					</c:if>
				</p>
			</c:forEach>
		</c:if>

		<p>${op:nl2br(board.content)}</p>
		
		<c:if test="${boardContext.boardCfg.useSocial == '1' && !requestContext.opmanagerPage}">
			<p class="sns">
				<a href="javascript:Social.facebook('${board.subject}', '${requestContext.requestFullUrl}', '${board.boardCode}', '${board.boardId}')"><img src="/content/images/common/btn_facebook.png" alt="'${board.subject}' 페이스북 공유하기" /></a>
				<a href="javascript:Social.twitter('${board.subject}', '${requestContext.requestFullUrl}', '${board.boardCode}', '${board.boardId}')"><img src="/content/images/common/btn_twitter.png" alt="'${board.subject}' 트위터 공유하기" /></a>
			</p>
		</c:if>
		
	</section>
	<footer>
	
		<c:if test="${boardContext.boardCfg.etc4 == '1' && (!empty boardPrev || !empty boardNext)}">
			<ul>
				<c:if test="${!empty boardPrev}">
					<li>
						<a href="${boardContext.boardBaseUri}/${boardPrev.boardId}?url=${url}">
							이전글 <span class="icon_prev_article"></span>
							<span class="title">${boardPrev.subject}</span>
						</a>
					</li>
				</c:if>	
							
				<c:if test="${!empty boardNext}">
					<li>
						<a href="${boardContext.boardBaseUri}/${boardNext.boardId}?url=${url}">
							다음글 <span class="icon_next_article"></span>
							<span class="title">${boardNext.subject}</span>
						</a>
					</li>
				</c:if>
			</ul>
		</c:if>
	</footer>
</article>



		
	
