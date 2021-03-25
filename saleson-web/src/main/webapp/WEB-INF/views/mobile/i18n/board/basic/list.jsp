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


<board:header-mobile />

<header class="title_menu">
	
	<h2>${boardContext.boardCfg.subject}</h2>
	<a href="" class="prev"><span class="icon_prev_menu">이전</span></a>
	<a href="" class="next"><span class="icon_next_menu">다음</span></a>
</header>
							
<section class="contents board_list">
	<form:form modelAttribute="boardSearchParam" action="${boardContext.boardBaseUri}" method="post">
		<form:hidden path="where" value="SUBJECT_CONTENT" />
		<fieldset class="search">
			<legend>검색</legend>
			<form:input path="query" placeholder="검색어를 입력해주세요" />
			<button type="submit"><span class="icon_search">검색</span></button>
		</fieldset>
	</form:form>
	
	<ul>
		<c:forEach items="${list}" var="board" varStatus="i">
			<li><a href="${boardContext.boardBaseUri}/${board.boardId}?url=${requestContext.currentUrl}">
				<p class="title"><span>${board.subject}</span>
					<c:if test="${op:getDaysDiff(fn:substring(board.creationDate, 0, 10)) <= boardContext.boardCfg.showNewIcon}"><span class="mark_new">N</span></c:if>
				</p>
				<p class="date">${fn:substring(board.creationDate, 0, 10)}</p>
			</a></li>
		</c:forEach>
	
		<c:if test="${empty list}">
			<li class="no_content">
				등록된 글이 없습니다.
			</li>
		</c:if>
	</ul>
	
	
	<div class="nav">
		<page:pagination-mobile />
	</div>
</section>

