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


<c:set var="imageFileCount" value="0" />
<c:set var="attachFileCount" value="0" />
<c:forEach items="${board.boardFiles}" var="file">
	<c:if test="${file.uploadType == '0'}">
		<c:set var="imageFileCount">${imageFileCount + 1}</c:set>
	</c:if>		
	<c:if test="${file.uploadType == '1'}">
		<c:set var="attachFileCount">${attachFileCount + 1}</c:set>
	</c:if>					
</c:forEach>

		<div class="board_view">
			<div class="title_wrap">
				<h4 class="title">${board.subject}</h4>
				
				<p class="info info_tooltip">
					<c:if test="${boardInfo.openCloseView}">
						<span>
							<input type="radio" name="etc1" value="2" id="etc1_2" ${op:checked("2", board.etc1)}><label for="etc1_2"> 미게시</label>
							<input type="radio" name="etc1" value="1" id="etc1_1" ${op:checked("1", board.etc1)}><label for="etc1_1"> 게시</label>
						</span>
					</c:if>
					
					<c:if test="${boardInfo.statusView}">
					<span class="state">상태 : 
						<c:choose>
							<c:when test="${board.statusCode == '1'}"><span class="icon red">미확인</span></c:when>
							<c:when test="${board.statusCode == '7'}"><span class="icon purple">반송</span></c:when>
							<c:when test="${board.statusCode == '8'}"><span class="icon blue">확인중</span></c:when>
							<c:when test="${board.statusCode == '9'}"><span class="icon green">확인</span></c:when>
						</c:choose>
					</span>
					</c:if>
				</p>
				
				<c:if test="${boardInfo.statusView && board.statusCode == '7'}">
					<div class="tooltip_wrap reject_cause">
						<a href="#" class="icon_reject tooltip">반송</a>
						<div class="tooltip_area">
							<div>
								<p>반송사유</p>
								<p>${op:nl2br(board.etc10)}</p>
								<a href="#" class="close"><img src="/content/images/common/icon_close2.png" alt="툴팁 닫기" /></a>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			<div class="info">
				<c:if test="${attachFileCount > 0}">
					<p class="file">
					
						<c:forEach items="${board.boardFiles}" var="file">	
							<c:if test="${file.uploadType == 1}">
							<a href="${boardContext.boardBaseUri}/${board.boardId}/download/${file.fileId}" onclick="return File.downloadConfirm()">${file.fileName}</a>
							</c:if>
						</c:forEach>
					</p>
				</c:if>

				<p class="detail">
					<c:if test="${boardContext.boardCfg.useCategory == '1'}">
					<span class="writer">${op:getCategoryName(boardContext.boardCfg.categoryList, board.category)}</span>
					</c:if>
					
					<span class="date">${fn:substring(board.creationDate, 0, 10)}</span>
					<span class="hits">${board.email}</span>
				</p>
				
			</div>
			<div class="text">
				${op:nl2br(board.content)}
			</div>
			
			
			<div id="board_comment">
				
			</div>
			
			
			<p class="btns">
				
				<span class="right">
					
					<c:if test="${board.statusCode == '1' && op:hasRole('ROLE_OPMANAGER')}">
						<a href="javascript:accept('${board.boardId}')" class="btn green"><img src="/content/opmanager/images/icon/icon_check.png" alt="" /> 확인</a>
					</c:if>

					<a href="${requestContext.prevPageUrl}" class="btn gray">목록</a>
				
				</span>
			</p>

		</div>
		
		
<board:footer />



<c:if test="${op:hasRole('ROLE_OPMANAGER')}">
<script type="text/javascript">
function accept(boardId) {

	var approvalMessage = '내용을 확인처리 하시겠습니까?';

	Common.confirm(approvalMessage, function() {
		$.post(url("${boardContext.boardBaseUri}/" + boardId + "/accept-faq-status"), {}, function(response) {
			Common.responseHandler(response, function() {
				alert('정상적으로 처리 되었습니다. ');
				location.reload();
			});
		});
	});
}

</script>
</c:if>




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

<c:if test="${boardContext.boardAuthority.deleteAuthority}">
<c:choose>
<c:when test="${requestContext.login == false}">
	<div class="board_password_form hide">
		<p class="question">삭제하시겠습니까? <a href="javascript:hideDeleteForm()"><span>X</span></a></p>
	 
		<form id="board_delete_form" action="${boardContext.boardBaseUri}/delete" method="post" onsubmit="return deleteBoard()">
			<fieldset>
				<input type="hidden" name="boardCode" value="${boardContext.boardCfg.boardCode}" />
				<input type="hidden" name="boardId" value="${board.boardId}" />
				
				<input type="hidden" name="boardCommentId" value="" />
				<label for="password">비밀번호</label> : 
				<input type="password" name="password" id="password" />
				<input type="image" src="${boardContext.images}/btn_delete.gif" alt="삭제" />
			</fieldset>
		</form>
	</div>
	

	<script type="text/javascript">
	function deleteBoard(){
		$("div.board_password_form").show();
	}
	function hideDeleteForm() {
		$("div.board_password_form").hide();
	}
	
	function deleteBoardForm(){
		
		if ($('#password').val() != undefined && $('#password').val() == ''){
			alert('비밀번호를 입력하여 주시기 바랍니다. ');
			$('#password').focus();
			return false;
		}
		if (!confirm("삭제하시겠습니까?")) {
			return false;
		}
	}
	</script>
</c:when>
<c:otherwise>
	<form:form modelAttribute="board" action="${boardContext.boardBaseUri}/delete" method="post">
	<form:hidden path="boardCode"/>
	<form:hidden path="boardId"/>
	</form:form>	
	<script type="text/javascript">
	function deleteBoard(){
		if (!confirm("삭제하시겠습니까?")) {
			$('#board').submit();
		}
	}
	</script>

</c:otherwise>
</c:choose>



</c:if>
