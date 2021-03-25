<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>

<div class="comment">

	<div class="comment-list">
		<jsp:include page="comment.jsp"></jsp:include>
	</div>

	<form:form modelAttribute="boardComment" name="board_comment_form"
		method="post"
		action="${boardContext.boardBaseUri}/${boardContext.boardId}/comment">
		<form:hidden path="boardCode" value="${boardContext.boardCode}" />
		<form:hidden path="boardId" value="${boardContext.boardId}" />
		<p class="form">
			<c:if test="${requestContext.login == false}">
				<div class="detail">
					<label for="commentName">이름 :</label>
					<form:input path="userName" id="commentName"
						class="width-100 required _filter" maxlength="20" title="이름" />
					<label for="commentPassword">비밀번호 :</label>
					<form:password path="password" id="commentPassword"
						class="width-100 required _filter" maxlength="20" title="비밀번호" />
				</div>
			</c:if>
		<p>
			<span class="placeholder_wrap"> <form:textarea path="comments"
					rows="4" maxlength="140" class="required _filter" title="댓글"
					placeholder="댓글은 140자 이내로 작성해주세요."></form:textarea>
				<button type="submit">
					<span>등록</span>
				</button>
			</span>
		</p>
		</p>
	</form:form>
</div>
<c:choose>
	<c:when test="${requestContext.login == false}">
		<div class="layer">
			<div class="bg"></div>
			<div id="boardLayer" class="board_password_form">
				<p class="question">
					비밀번호를 입력해 주세요. <button type="button" class="popup_close">창 닫기</button>
				</p>
	
				<form:form modelAttribute="board" action="${boardContext.boardBaseUri}/${board.boardId}/comment/delete" method="post">
					<fieldset>
						<form:hidden path="boardCode" />
						<form:hidden path="boardId" />
						<input type="hidden" name="boardCommentId" /> <label for="password">비밀번호</label> :
						<form:password path="password" title="비밀번호" class="required" maxlength="20" />
						<button type="submit" class="btn-delete">삭제</button>
					</fieldset>
				</form:form>
			</div>
		</div>
		<page:javascript>
		<script type="text/javascript">
			$(function(){
				$('#board').validator(function() {
					if (!confirm("삭제하시겠습니까?")) {
						return false;
					}
				});
			});
			function deleteBoard(commentId) {
				$('input[name=boardCommentId]').val(commentId);
				layer_open('boardLayer');
			}
		</script>
		</page:javascript>
	</c:when>
	<c:otherwise>
		<form:form modelAttribute="board"
			action="${boardContext.boardBaseUri}/${board.boardId}/comment/delete"
			method="post">
			<form:hidden path="boardCode" />
			<form:hidden path="boardId" />
			<input type="hidden" name="boardCommentId" />
		</form:form>
		<page:javascript>
		<script type="text/javascript">
			function deleteBoard(commentId) {
				if (confirm("삭제하시겠습니까?")) {
					$('input[name=boardCommentId]').val(commentId);
					$('#board').submit();
				}
			}
		</script>
		</page:javascript>
	</c:otherwise>
</c:choose>
<page:javascript>
<script type="text/javascript">
	$(function() {
		$('#boardComment').validator(function() {
			var comment = $('#comments').val();

			if (comment.length >= 140) {
				alert('댓글은 140자 이내로 작성하여 주시기 바랍니다. ');
				return false;
			}

			if (!confirm('등록 하시겠습니까?')) {
				return false;
			}
		});
	});
	function getCommentList(page) {
		location.href = "${boardContext.boardBaseUri}/${board.boardId}?page=" + page;
	}
	function layer_open(el){

		var temp = $('#' + el);
		var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

		if(bg){
			$('.layer').fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다. 
		}else{
			temp.fadeIn();
		}

		// 화면의 중앙에 레이어를 띄운다.
		if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
		else temp.css('top', '0px');
		if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
		else temp.css('left', '0px');

		temp.find('.popup_close').click(function(e){
			if(bg){
				$('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
			}else{
				temp.fadeOut();
			}
			e.preventDefault();
		});

		$('.layer .bg').click(function(e){	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
			$('.layer').fadeOut();
			e.preventDefault();
		});

	}			
</script>
</page:javascript>