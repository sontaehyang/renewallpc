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

<board:header />

<div class="board-view"> 
	<table class="board-view-table">
		<caption>건강미디어</caption>
		<colgroup>
			<col style="width:auto;"> 
			<col style="width:100px;">
			<col style="width:60px;">  
		</colgroup>
		<tbody>
			<tr> 
				<th class="subject">
					<span>제목</span>
					${board.subject}
				</th>
				<th class="time">${fn:substring(board.creationDate, 0, 4)}-${fn:substring(board.creationDate, 4, 6)}-${fn:substring(board.creationDate, 6, 8)}</th>
				<th>${board.hit}</th>
			</tr>
			<tr> 
				<td colspan="3" class="bL0">
					<div class="board_conts"> 
						${op:nl2br(board.content)}
					</div>
				</td>
			</tr>	 				 
		</tbody>
	</table> 	
	
	<c:if test="${boardContext.boardCfg.useComment == 1}">
		<div id="board_comment">
			<jsp:include page="comment.jsp"></jsp:include>
		</div>
	</c:if>	 	
</div><!--//board-view E-->		
<dl class="prevnext">
	<c:if test="${!empty boardPrev}">
		<dt class="prev">이전글</dt>
		<dd>
			<a href="${boardContext.boardBaseUri}/${boardPrev.boardId}?url=${url}">${op:strcut(boardPrev.subject, boardContext.boardCfg.subjectLength + 20)}</a>
		</dd>
	</c:if>
	<c:if test="${empty boardPrev}">
		<dt class="prev">이전글</dt>
		<dd><a href="javascript:;">이전글이 없습니다.</a></dd>
	</c:if>
	<c:if test="${!empty boardNext}">
		<dt class="next">다음글</dt>
		<dd>
			<a href="${boardContext.boardBaseUri}/${boardNext.boardId}?url=${url}">${op:strcut(boardNext.subject, boardContext.boardCfg.subjectLength + 20)}</a>
		</dd>
	</c:if>
	<c:if test="${empty boardNext}">
		<dt class="next">다음글</dt>
		<dd><a href="javascript:;">다음글이 없습니다.</a></dd>
	</c:if>
</dl>
      
<div class="btn_center board-button">
	<c:if test="${boardContext.boardAuthority.writeAuthority}">
		<a href="${boardContext.boardBaseUri}/write?url=${url}" class="btn btn-active">글쓰기</a>
	</c:if>

	<c:if test="${boardContext.boardAuthority.modifyAuthority}">
		<a href="${boardContext.boardBaseUri}/${board.boardId}/edit?url=${requestContext.currentUrl}" class="btn btn-normal">수정</a>
	</c:if>
	<c:if test="${boardContext.boardAuthority.deleteAuthority && board.userId == requestContext.user.userId}">
		
		<a href="javascript:;" onclick="deleteBoardView()" class="btn btn-normal">삭제</a>
	</c:if>
	
	<a href="${requestContext.prevPageUrl}" class="btn btn-normal">목록</a>
</div> 

<form id="deleteForm" action="${boardContext.boardBaseUri}/delete" method="post">
	<input type="hidden" name="boardCode" id="boardCode" value="${boardContext.boardCfg.boardCode}" />
	<input type="hidden" name="boardId" id="boardId" value="${boardContext.boardId}" />
</form>

<script type="text/javascript">
function deleteBoardView(boardCode, boardId) {
	if(confirm('삭제하시겠습니까?')) {
		$('#deleteForm').submit();
	} else {
		return false;
	}
}
</script>




<c:if test="${boardContext.boardCfg.useComment == 1}">
<script type="text/javascript">
$(function(){
	getCommentList(1);
});

function getCommentList(page) {
	$.get('${boardContext.boardBaseUri}/${board.boardId}/comment?page='+page, '', function(result){
		$('#board_comment').html(result);
		try {
			setHeight();
		} catch(e) {}
    }, 'html');	
}


</script>
</c:if>