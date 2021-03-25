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

<style>
	.board-buttons .pagination {
		display: inline-block;
		width: 50%;
		text-align: left;
	}
	.board-buttons .right {
		display: inline-block;
		width: 50%;
		text-align: right;
	}
</style>

<board:header />

<div class="sort_area">
	<div class="sort_page">
		총 <strong>${totalCount}</strong>건
	</div>
	<c:choose>
		<c:when test="${requestContext.opmanagerPage}">
			<form:form modelAttribute="boardSearchParam" action="${boardContext.boardBaseUri}" method="post">
				<fieldset>
					<legend class="hidden">검색</legend>
					<c:if test="${boardContext.boardCfg.writeAuthority == 'ROLE_OPMANAGER'}">
						<div class="left">
							<c:if test="${requestContext.opmanagerPage && boardContext.boardCfg.useCategory == 1 && boardContext.boardAuthority.boardAdmin == true}">
								<span class="line">|</span>
								<span>분류</span>
								<form:select path="category" onchange="this.form.submit()" title="분류선택">
									<form:option value="">전체</form:option>
									<c:if test="${requestContext.opmanagerPage && boardContext.boardCode == 'notice'}">
									<form:option value="common">공통</form:option>
									</c:if>
									<form:options items="${boardContext.boardCfg.categories}" itemLabel="label" itemValue="value" />
								</form:select>
							</c:if>
						</div>
					</c:if>
					
					<c:choose>
						<c:when test="${boardContext.boardCode == 'refer'}">
							<div class="right">
								<form:select path="where" title="검색조건">
									<form:option value="SUBJECT">사이트명</form:option>
									<form:option value="SUBJECT_CONTENT">사이트명+URL</form:option>
								</form:select>
						</c:when>
						<c:otherwise>
							<div class="right">
								<form:select path="where" title="검색조건">
									<form:option value="SUBJECT">제목</form:option>
									<c:if test="${boardContext.boardCode != 'irmanage' && boardContext.boardCode != 'download' && boardContext.boardCode != 'ebook'}">
									<form:option value="SUBJECT_CONTENT">제목+내용</form:option>
									</c:if>
								</form:select>
						</c:otherwise>
					</c:choose>
					<form:input path="query" class="input_txt required _filter" title="검색어" maxlength="20" />
					<button type="submit" class="btn btn-dark-gray btn-sm">검색</button>
				</fieldset>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form modelAttribute="boardSearchParam" action="${boardContext.boardBaseUri}" method="post">
				<fieldset> 	
					<form:select path="where" title="검색조건">
						<form:option value="SUBJECT">제목</form:option>
						<form:option value="CONTENT">내용</form:option>
					</form:select>
					<div>
						<form:input path="query" class="input_txt required _filter" cssClass="box" id="box" title="검색어" maxlength="20" />
						<button type="submit" class="btn_cont_search btn btn-dark-gray btn-sm">검색</button>
					</div>
				</fieldset>
			</form:form>
		</c:otherwise>	
	</c:choose>	
</div><!--// sort_area E-->
	
<div class="community_list">
	<ul>
		<c:forEach items="${list}" var="board" varStatus="i">
			<li>
				<a href="${boardContext.boardBaseUri}/${board.boardId}?url=${requestContext.currentUrl}">
					
						<c:forEach items="${board.boardFiles}" var="file" varStatus="i">
							<c:choose>
								<c:when test="${(file.fileType == 'png'
									|| file.fileType == 'jpg' || file.fileType == 'gif' || file.fileType == 'jpeg'
									|| file.fileType == 'bmp')}">
									<span class="thumbnail">
										<img src="/upload/${boardContext.boardCode}/${board.boardId}/${file.fileId}.${file.fileType}" alt="${file.fileDescription}" />
									</span>
								</c:when>
								<c:otherwise>
									
								</c:otherwise>
							</c:choose>
						</c:forEach>
					
					
					<div class="cont">
						<p class="title">
							<span>
								${op:strcut(board.subject, fn:length(board.subject))}
							</span>
							<c:if test="${today <= board.boardCfg.newIconDate}">
								<img src="/content/opmanager/images/icon/icon_new.png" alt="new_icon" />
							</c:if>
							<c:if test="${board.hit > board.boardCfg.showHotIcon}">
								<img src="/content/opmanager/images/icon/icon_new.png" alt="hot_icon" />
							</c:if>
						</p>
						<p class="txt">
							${op:strcut(op:stripTags(board.content), fn:length(board.content))}
						</p>
						<p class="date">
							<span>
								작성일 : ${fn:substring(board.creationDate, 0, 4)}-${fn:substring(board.creationDate, 4, 6)}-${fn:substring(board.creationDate, 6, 8)}
							</span>
							<span>
								조회수 : ${board.hit}
							</span>
						</p>
					</div>
				</a>
			</li>
		</c:forEach>
		<c:if test="${empty list}">
			<li style="padding:50px;text-align:center;">등록된 글이 없습니다.</li>
		</c:if>
	</ul>
</div><!-- 커뮤니티 게시판 끝 -->

<div class="board-buttons">
	<page:pagination />

	<span class="right">	
		<c:if test="${boardContext.boardAuthority.writeAuthority}">
			<a href="${boardContext.boardBaseUri}/write?url=${requestContext.currentUrl}" class="btn btn-active">글쓰기</a>
		</c:if>		
	</span>
</div>

<form method="post" action="${boardContext.boardBaseUri}/passCheck" name="passCheckForm" id="passCheckForm">
	<div id="aa" style="display:none;">
		<input name="checkBoardId" id="checkBoardId" type="text" />
		<input name="checkBoardCode" id="checkBoardCode" type="text" />
		패스워드 <input name="checkPassword" type="password" />
		<input type="submit" value="고고" />
	</div>
</form>

<board:footer />


<script type="text/javascript">
$(function() {
	$('#passCheckForm').validator(function() {
		
	});
	
	$('input[name=statusCode]').on("click", function() {
		$('#boardSearchParam').submit();
	});
});

//레이어 팝업 열기
function openLayer(IdName, tpos, lpos, no, code){
	var pop = document.getElementById(IdName);
	$("#checkBoardId").val(no);
	$("#checkBoardCode").val(code);
	pop.style.display = "block";
	pop.style.top = tpos + "px";
	pop.style.left = lpos + "px";
	
	$("#checkPassword").focus();
}

//레이어 팝업 닫기
function closeLayer(IdName){
	var pop = document.getElementById(IdName);
	pop.style.display = "none";
}

// e-book 띄우기 시작
function showEbook(url,_width,_height) 
{ 
        if(window.clientInformation.userAgent.indexOf("SV1")>0){
       newWin = window.open(url,"","toolbar=no location=no directories=no status=no menubar=no scrollbars=no resizable=no width=1024 height=768 top=0 left=0");
           newWin.moveTo(0,0);

               left1=(screen.availWidth-1024-5)/2; 
           top1=(screen.availHeight-768-10)/2; 
           
               newWin.resizeTo(1024,812);
               newWin.moveTo(left1,top1);

   }else{
           if(screen.width==_width&&screen.height==_height) { 
               window.open(url,"","fullscreen"); 
             } else { 
           left1=(screen.availWidth-1024-5)/2; 
           top1=(screen.availHeight-768-10)/2;       
           window.open(url,"","toolbar=no location=no directories=no status=no menubar=no scrollbars=no resizable=no width=1024 height=768 top="+top1+" left="+left1); 
             }
       }
}
// e-book 띄우기 끝
</script>	
