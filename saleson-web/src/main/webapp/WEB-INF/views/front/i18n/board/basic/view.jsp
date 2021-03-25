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

<!--board_view S-->	 
<div class="board_view mt45">  

	<div class="stit">
		<dl>
			<dt>제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목</dt>
			<dd class="txt_bl">
				<c:choose>
					<c:when test="${boardContext.boardCode == 'webzine'}">
						${board.subject}&nbsp;${board.etc2}
					</c:when>
					<c:otherwise>
						${board.subject}
					</c:otherwise>
				</c:choose>
			</dd>
		</dl>
	</div>
	<div class="stit">
		<dl>
			<dt>등 록 일</dt>
			<dd>${fn:substring(board.creationDate, 0, 4)}-${fn:substring(board.creationDate, 4, 6)}-${fn:substring(board.creationDate, 6, 8)}</dd>
		</dl>
		<dl class="tright">
			<dt class="dt01">조회수</dt>
			<dd>${board.hit}</dd>
		</dl>
	</div>
		<c:forEach items="${boardFiles}" var="file">	
			<c:if test="${file.uploadType == '1' || file.uploadType == '2'}">
				<div class="stit">
					<dl>
						<dt>첨부파일</dt>
						<dd>
							<img src="/content/images/common/icon_file.gif" alt="" />
							<a href="${boardContext.boardBaseUri}/${board.boardId}/download/${file.fileId}" onclick="return File.downloadConfirm()">${file.fileName}</a>
						</dd>
					</dl>
				</div>
			</c:if>
		</c:forEach>
	<dl class="cont">
		<dt class="hide">제목</dt>
		<dd>
			<div class="conts">
				<c:forEach items="${board.boardFiles}" var="file">
					<c:if test="${file.uploadType == '0'}">
						<img src="/upload/${boardContext.boardCode}/${board.boardId}/thumb/600x0_${file.fileId}.${file.fileType}" alt="${file.fileDescription}" />
					</c:if>
				</c:forEach>
				${op:nl2br(board.content)}
			</div>	
		</dd>
	</dl>
</div><!--//board_view E -->


<c:choose>
	<c:when test="${board.boardCode == 'qna'}">
		<dl class="board_prevnext"></dl>
	</c:when>
	<c:otherwise>
		<dl class="board_prevnext">
			<c:if test="${!empty boardPrev}">
				<dt class="first">이전글</dt>
				<dd class="first">
					<a href="${boardContext.boardBaseUri}/${boardPrev.boardId}?url=${url}">${op:strcut(boardPrev.subject, boardContext.boardCfg.subjectLength + 20)}</a>
				</dd>
			</c:if>
			<c:if test="${empty boardPrev}">
				<dt class="first">이전글</dt>
				<dd class="first"><a href="javascript:;">이전글이 없습니다.</a></dd>
			</c:if>
			<c:if test="${!empty boardNext}">
				<dt>다음글</dt>
				<dd>
					<a href="${boardContext.boardBaseUri}/${boardNext.boardId}?url=${url}">${op:strcut(boardNext.subject, boardContext.boardCfg.subjectLength + 20)}</a>
				</dd>
			</c:if>
			<c:if test="${empty boardNext}">
				<dt>다음글</dt>
				<dd><a href="javascript:;">다음글이 없습니다.</a></dd>
			</c:if>
		</dl>
	</c:otherwise>
</c:choose>

<div class="btnarea02">
	<c:if test="${board.boardCode == 'qna' && board.depth == 0}">
		<a href="${boardContext.boardBaseUri}/${board.boardId}/qnaModify?url=${requestContext.currentUrl}">
			<img src="/content/images/btn/btn_refresh.gif" alt="수정" />
		</a>
	</c:if>
	<a href="${requestContext.prevPageUrl}"><img src="/content/images/btn/btn_list.gif" alt="목록" /></a>		
</div>

<!--//webzine_content S 예외 질의응답-->
<input name="code" value="${board.boardCode }" id="code" type="hidden"/>
	<fieldset>
		<legend>만족도조사</legend>
		<div class="satisfaction">
			<p><img src="/content/images/news/webzine_txt01.gif" alt="홈페이지의 서비스 향상을 위해 여러분의 만족도 조사를 실시하고 있습니다."></p>
			<p>- 본 홈페이지에서 제공하는 정보에 만족하셨습니까?</p>
			<div class="surveyBox">
				<ul>
					<li><input type="radio" id="survey_01" name="survey_good" class ="survey_good" value ="very_satis" title="매우만족">&nbsp;<label for="survey_01">매우만족</label> </li>
					<li><input type="radio" id="survey_02" name="survey_good" class ="survey_good" value ="satis" title="만족">&nbsp;<label for="survey_02">만족</label> </li>
					<li><input type="radio" id="survey_03" name="survey_good" class ="survey_good" value ="nomal" title="보통">&nbsp;<label for="survey_03">보통</label> </li>
					<li><input type="radio" id="survey_04" name="survey_good" class ="survey_good" value ="dissatis" title="불만족">&nbsp;<label for="survey_04">불만족</label> </li>
					<li><input type="radio" id="survey_05" name="survey_good" class ="survey_good" value ="very_dissatis" title="매우불만족">&nbsp;<label for="survey_05">매우불만족</label> </li>
				</ul>

				&nbsp;<input type="image" id="survey_insert" src="/content/images/btn/btn_content.gif" title="만족도등록" alt="만족도등록">
			</div> 
		</div><!--//webzine_content E -->
	</fieldset>	


<script type="text/javascript">
$(function(){
	$('#survey_insert').on("click", function(){
		if($('.survey_good:checked').size() == 0){
			alert("만족도를 선택해주시기 바랍니다..");
		}else{
			var params = {
				'code' : $("#code").val(),
				'survey_good'    : $(".survey_good:checked").val()
			};
			 $.post('/satis/write', params, function(resp) {
				 if (resp.isSuccess) {
					 alert("만족도가 등록되었습니다.");
				 }else{
					 alert(resp.errorMessage);
				 }
			 });
		}
	});
});
</script>