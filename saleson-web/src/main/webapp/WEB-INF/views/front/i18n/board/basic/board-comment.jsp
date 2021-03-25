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
		 		<label for="commentPassword">비밀번호</label> <input type="password" name="password" id="commentPassword" class="width-100 required"  maxlength="20" title="비밀번호" />
			</span>
			</c:if>
		
			<span class="placeholder_wrap">
				<span class="placeholder">140자 이내로 작성해주세요</span>
				<textarea id="comments" name="comments" cols="30" rows="10" maxlength="140" class="required _filter" title="댓글"></textarea>
			</span>
			<button type="submit"><span>등록</span></button>
		</p>
	</form>
	
	<div id="comment-list">
	
	
	</div>
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


<script type="text/javascript">
$(function(){
	getCommentList(1);
});

function getCommentList(page) {
	$.get('${boardContext.boardBaseUri}/${board.boardId}/comment?page='+page, '', function(result){
		$('#comment-list').html(result);
    }, 'html');	
}

function validateBoardComment() {
	var $form = $('#board_comment_form');
	var param = $form.serialize();

	$.post('${boardContext.boardBaseUri}/${boardContext.boardId}/comment', param, function(resp){
		Common.responseHandler(resp, function(){
			Common.loading.hide();
			alert('등록이 정상적으로 되었습니다. ');
			$('#comments').val("");
			getCommentList(1);
		});
    });	
	
	return false;
}

</script>

<script type="text/javascript">
$(function(){
	checkCommentLength();
	
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
	
	$(document).on('click', '.delete_comment_anonymous', function(e){
		var boardCommentId = $(this).attr("rel");
		$('#boardCommentId').val(boardCommentId);
		if ($('.board_comment_delete').css('display') == 'block') 
			$('.board_comment_delete').css('display', 'none') ;
		layerPopup('.board_comment_delete', '50%',  e.clientY + 40);

	});
	
	$(document).on('click', '.delete_comment',function(e){
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

function checkCommentLength() {
	
	$('#comments').on('keyup change', function() {  
		var str = $(this).val();  
		var maxlength = 140;  
		if (str.length > maxlength) {  
			$(this).val(str.substr(0, maxlength));
			alert('입력내용은 140자까지만 입력이 가능합니다. ');
	        $(this).focus();
			return false;  
		}  
	});
}

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



</script>

