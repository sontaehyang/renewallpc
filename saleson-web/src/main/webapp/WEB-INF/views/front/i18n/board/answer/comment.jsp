<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>


<div class="comment">

	<form id="board_comment_form" name="board_comment_form" method="post" action="${boardContext.boardBaseUri}/${boardContext.boardId}/comment">
 		<input type="hidden" name="boardCode" value="${boardContext.boardCode}" />
 		<input type="hidden" name="boardId" value="${boardContext.boardId}" />
 		
		<p class="form">
			<span class="title">댓글</span>
			
			<c:if test="${requestContext.login == false && boardContext.boardAuthority.commentAuthority == true}">
			<span>
		 		<label for="userName">이름</label> <input type="text" name="userName" id="commentName" class="width-100 required _filter" maxlength="10" title="이름" />
		 		<label for="commentPassword">비밀번호</label> <input type="password" name="password" id="commentPassword" class="width-100 required _filter"  maxlength="20" title="비밀번호" />
			</span>
			</c:if>
		
			<span class="placeholder_wrap">
				<span class="placeholder">140자 이내로 작성해주세요</span>
				<textarea id="comments" name="comments" cols="30" rows="10" maxlength="140" class="required _filter" title="댓글"></textarea>
			</span>
			<button type="submit"><span>등록</span></button>
		</p>
	</form>
	
	<ul>
		<c:forEach items="${list}" var="comment" varStatus="i">
		<li>
			<span class="name">${comment.userName}*</span>
			<span>
				${op:nl2br(comment.comments)}
				<c:choose>
					<c:when test="${(comment.userId != '' && comment.userId == requestContext.user.userId) || boardContext.boardAuthority.boardAdmin}">
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
</div>


<div class="board_comment_delete" style="display: none">
	<p class="question">삭제하시겠습니까? <a href="javascript:hideCommentDeleteForm()"><span>X</span></a></p>
	 
 	<form id="board_comment_delete_form" action="${boardContext.boardBaseUri}/${boardContext.boardId}/comment/delete" method="post" onsubmit="return deleteComment()">
		<fieldset>
			<legend>댓글 삭제 폼</legend>
			<input type="hidden" name="boardCode" value="${boardContext.boardCfg.boardCode}" />
			<input type="hidden" name="boardId" value="${boardContext.boardId}" />
			
			<input type="hidden" name="boardCommentId" id="boardCommentId" value="" />
			<label for="password">비밀번호</label> : 
			<input type="password" name="password" id="password" />
			<input type="image" src="${boardContext.images}/btn_delete.gif" alt="삭제" />
		</fieldset>
	</form>
</div>

	
<!-- 
<div class="board_comment">
	<h4>댓글 <span>${op:numberFormat(pagination.totalItems)}개</span></h4>
	
	<c:choose>
		<c:when test="${!empty list}">
		
			<ul class="board_comment_list">
			<c:forEach items="${list}" var="comment" varStatus="i">
			<li>
				<span class="author">
					${comment.userName}
				</span>
				<p>
					${op:nl2br(comment.comments)}
					<span class="date">${fn:substring(comment.creationDate, 0, 16)}</span>
					
					
					
					<c:choose>
					<c:when test="${(comment.userId != '' && comment.userId == requestContext.user.userId) || boardContext.boardAuthority.boardAdmin}">
						<a href="javascript:;" rel="${comment.boardCommentId}" class="delete_comment"><img src="${boardContext.images}/icon_delete.gif" alt="삭제" class="middle" /></a>
					</c:when>
					<c:when test="${comment.userId == '' && requestContext.login == false}">
						<a href="javascript:;" rel="${comment.boardCommentId}" class="delete_comment_anonymous"><img src="${boardContext.images}/icon_delete.gif" alt="삭제" />	</a>
					</c:when>
					</c:choose>
				</p>
			</li>
			</c:forEach>
			</ul>		

		    <div class="paging">
		 	<page:pagination />
		 	</div>
 	
		</c:when>
		<c:otherwise>
			<div class="nothing">등록된 댓글이 없습니다.</div>
		</c:otherwise>
	</c:choose>


 	<div class="comment-write">
 		<form id="board_comment_form" name="board_comment_form" method="post" action="${boardContext.boardBaseUri}/${boardContext.boardId}/comment">
 		<input type="hidden" name="boardCode" value="${boardContext.boardCode}" />
 		<input type="hidden" name="boardId" value="${boardContext.boardId}" />
 		
		<c:if test="${requestContext.login == false && boardContext.boardAuthority.commentAuthority == true}">
		<p>
	 		<label for="userName">이름</label> <input type="text" name="userName" id="commentName" class="width-100 required" maxlength="10" title="이름" />
	 		<label for="commentPassword">비밀번호</label> <input type="password" name="password" id="commentPassword" class="width-100 required"  maxlength="20" title="비밀번호" />
		</p>
		</c:if>
 		<textarea id="comments" name="comments"  class="txt required" title="내용"></textarea>
 		<input type="image" src="${boardContext.images}/btn_write_comment.gif" alt="댓글등록" />
 		</form>
 	</div>
 	
	<div class="board_comment_delete hide">
		<p class="question">삭제하시겠습니까? <a href="javascript:hideCommentDeleteForm()"><span>X</span></a></p>
		 
	 	<form id="board_comment_delete_form" action="${boardContext.boardBaseUri}/${boardContext.boardId}/comment/delete" method="post" onsubmit="return deleteComment()">
			<fieldset>
				<legend>댓글 삭제 폼</legend>
				<input type="hidden" name="boardCode" value="${boardContext.boardCfg.boardCode}" />
				<input type="hidden" name="boardId" value="${boardContext.boardId}" />
				
				<input type="hidden" name="boardCommentId" id="boardCommentId" value="" />
				<label for="password">비밀번호</label> : 
				<input type="password" name="password" id="password" />
				<input type="image" src="${boardContext.images}/btn_delete.gif" alt="삭제" />
			</fieldset>
		</form>
	</div>
</div>
 -->



<script type="text/javascript">
$(function(){
	$('#board_comment_form').validator(function(form) {
		
		var comment = $('#comments').val();

		if (comment.length >= 140) {
			alert('댓글은 140자 이내로 작성하여 주시기 바랍니다. ');	
		}
		
		
			<c:if test="${requestContext.login == false && boardContext.boardCfg.commentAuthority != 'ANONYMOUS'}">
			alert('로그인하셔야 작성이 가능 합니다. ');
			return false;
			</c:if>
			
			validateBoardComment();
			return false;
	});
	
	$('.delete_comment_anonymous').click(function(e){
		var boardCommentId = $(this).attr("rel");
		$('#boardCommentId').val(boardCommentId);
		if ($('.board_comment_delete').css('display') == 'block') 
			$('.board_comment_delete').css('display', 'none') ;
		layerPopup('.board_comment_delete', '50%',  e.clientY + 40);

	});
	
	$('.delete_comment').click(function(e){
		var boardCommentId = $(this).attr("rel");
		$('#boardCommentId').val(boardCommentId);

		if (confirm("삭제하시겠습니까?")) {
			deleteComment();
		}
	});
	
	
	<c:if test="${requestContext.login == false && boardContext.boardCfg.commentAuthority != 'ANONYMOUS'}">
		$('#comments').click(function(){
			alert('로그인하셔야 작성이 가능 합니다. ');
		});
	</c:if>
});

function hideCommentDeleteForm() {
	$('.board_comment_delete').hide();
}

function deleteComment() {

	$.post("${boardContext.boardBaseUri}/${boardContext.boardId}/comment/delete", $('#board_comment_delete_form').serialize(), function (resp) {
		Common.responseHandler(resp, function(){
			hideCommentDeleteForm();	
			getCommentList(1);
		});
	});

	return false;
}

function validateBoardComment() {
	var $form = $('#board_comment_form');
	var param = $form.serialize();

	$.post('${boardContext.boardBaseUri}/${boardContext.boardId}/comment', param, function(resp){
		Common.responseHandler(resp, function(){
			getCommentList(1);
		});
    });	
	
	return false;
}

</script>

