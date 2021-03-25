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


<c:if test="${requestContext.opmanagerPage == true || ngoPage == true}">
<p class="guide">${boardContext.boardCfg.subject} 목록을 관리합니다.</p>
</c:if>

<board:header />
<c:if test="${requestContext.opmanagerPage == false}">
	<c:if test="${boardContext.boardCfg.useCategory == 1}">
		<c:choose>
			<c:when test="${boardContext.boardCode == 'prevention'}"> <!-- 예방자료실 카테고리 -->
				<div class="accident_tab">
					<ul>
						<c:set var="onoff" value="_off"/>
						<c:if test="${empty boardSearchParam.category}">
							<c:set var="onoff" value="_on"/>
						</c:if>
						<c:set var="onoff1" value="_off"/>
						<c:if test="${boardSearchParam.category == 1}">
							<c:set var="onoff1" value="_on"/>
						</c:if>
						<c:set var="onoff2" value="_off"/>
						<c:if test="${boardSearchParam.category == 2}">
							<c:set var="onoff2" value="_on"/>
						</c:if>
						<c:set var="onoff3" value="_off"/>
						<c:if test="${boardSearchParam.category == 3}">
							<c:set var="onoff3" value="_on"/>
						</c:if>
						<li><a href="${boardContext.boardBaseUri}"><img src="/content/images/news/news_taball${onoff}.gif" alt="전체"></a></li>
						<li><a href="${boardContext.boardBaseUri}?category=1"><img src="/content/images/accident/accident_tab01${onoff1}.gif" alt="영유아" /></a></li>
						<li><a href="${boardContext.boardBaseUri}?category=2"><img src="/content/images/accident/accident_tab02${onoff2}.gif" alt="보육교직원" /></a></li>
						<li><a href="${boardContext.boardBaseUri}?category=3"><img src="/content/images/accident/accident_tab03${onoff3}.gif" alt="어린이집안전관리" /></a></li>
					</ul>
				</div><!--//accident_tab E-->
			</c:when>
			<c:when test="${boardContext.boardCode == 'notification'}"> <!-- 알림자료실 카테고리 -->
				<div class="accident_tab">
					<ul>
						<c:set var="onoff1" value="_off"/>
						<c:if test="${boardSearchParam.category == 1}">
							<c:set var="onoff1" value="_on"/>
						</c:if>
						<c:set var="onoff2" value="_off"/>
						<c:if test="${boardSearchParam.category == 2}">
							<c:set var="onoff2" value="_on"/>
						</c:if>
						<c:set var="onoff3" value="_off"/>
						<c:if test="${boardSearchParam.category == 3}">
							<c:set var="onoff3" value="_on"/>
						</c:if>
						<li><a href="${boardContext.boardBaseUri}?category=1"><img src="/content/images/news/news_tab01${onoff1}.gif" alt="홍보" /></a></li>
						<li><a href="${boardContext.boardBaseUri}?category=2"><img src="/content/images/news/news_tab02${onoff2}.gif" alt="보육뉴스" /></a></li>
						<li><a href="${boardContext.boardBaseUri}?category=3"><img src="/content/images/news/news_tab03${onoff3}.gif" alt="통계" /></a></li>
					</ul>
				</div><!--//accident_tab E-->
			</c:when>
			<c:when test="${boardContext.boardCode == 'refer'}"> <!-- 참고사이트 카테고리 -->
				<div class="accident_tab">
					<ul>
						<c:set var="onoff1" value="_off"/>
						<c:if test="${boardSearchParam.category == 1}">
							<c:set var="onoff1" value="_on"/>
						</c:if>
						<c:set var="onoff2" value="_off"/>
						<c:if test="${boardSearchParam.category == 2}">
							<c:set var="onoff2" value="_on"/>
						</c:if>
						<li><a href="${boardContext.boardBaseUri}?category=1"><img src="/content/images/accident/accident06_tab01${onoff1}.gif" alt="국내" /></a></li>
						<li><a href="${boardContext.boardBaseUri}?category=2"><img src="/content/images/accident/accident06_tab02${onoff2}.gif" alt="국외" /></a></li>
					</ul>
				</div><!--//accident_tab E-->
			</c:when>
		</c:choose>
	</c:if>
</c:if>
<c:if test="${requestContext.opmanagerPage}">
	<div class="board_list">
</c:if>
	<c:choose>
		<c:when test="${boardContext.boardCode == 'faq' && requestContext.opmanagerPage == false}">
			<div class="faq_search">
					<ul>
						<li><img src="/content/images/news/news_faq_txt01.gif" alt="자주묻는 질문 검색 [FAQ}" /></li>
						<li>
							<form:form modelAttribute="boardSearchParam" action="${boardContext.boardBaseUri}" method="post">
							<fieldset>
									<legend>검색</legend>
									<form:hidden path="where" title="검색조건" value="SUBJECT" />	
									<form:input path="query" class="faq_box required _filter" cssClass="box" title="검색어" maxlength="20" />
									<input type="image" src="/content/images/btn/btn_search.gif" title="검색" alt="검색" />
								</fieldset>
							</form:form>
							<p>Ex) 공제상품, 보증공제</p>
						</li>
					</ul>
			</div><!--//faq_search E-->
		</c:when>
		<c:when test="${requestContext.opmanagerPage}">
			<div class="sort_area">
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
						<button type="submit"><span class="icon_search">검색</span></button>
						</div>
					</fieldset>
				</form:form>
			</div>
		</c:when>
		<c:otherwise>
			<div class="sort_area">
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
								<form:select path="where" title="검색조건">
									<form:option value="SUBJECT">사이트명</form:option>
									<form:option value="SUBJECT_CONTENT">사이트명+URL</form:option>
								</form:select>
							</c:when>
							<c:otherwise>
								<form:select path="where" title="검색조건">
									<form:option value="SUBJECT">제목</form:option>
									<c:if test="${boardContext.boardCode != 'irmanage' && boardContext.boardCode != 'download' && boardContext.boardCode != 'ebook'}">
									<form:option value="SUBJECT_CONTENT">제목+내용</form:option>
									</c:if>
								</form:select>
							</c:otherwise>
						</c:choose>
						<form:input path="query" class="input_txt required _filter" cssClass="box" title="검색어" maxlength="20" />
						<input type="image" src="/content/images/btn/btn_search.gif" title="검색" alt="검색" />
					</fieldset>
				</form:form>
			</div>
		</c:otherwise>	
	</c:choose>	
	<c:if test="${requestContext.opmanagerPage == false}">
		<div class="board_list">
	</c:if>
			<table cellspacing="0" cellpadding="0" summary="공지사항 글번호, 등록일, 첨부파일, 조회수, 제목 링크를 통해서 상세 내용으로 이동합니다." class="board_list_table">
				<caption class="hidden">게시판 리스트</caption>
				<colgroup>
					<col style="width:10%" />
					<c:choose>
						<c:when test="${boardContext.boardCode == 'refer'}">
							<col style="width:25%" />
							<col style="width:35%" />
							<col style="width:auto" />
						</c:when>
						<c:otherwise>
							<c:if test="${boardContext.boardCode == 'faq'}">
								<col style="width:65%" />
							</c:if>
							<c:if test="${boardContext.boardCode != 'faq'}">
								<col style="width:45%" />
								<c:if test="${boardContext.boardCode == 'qna'}">
									<col style="width:15%" />
								</c:if>
								<col style="width:15%" />
								<c:if test="${boardContext.boardCode != 'ebook' && boardContext.boardCode != 'qna'}">
									<col style="width:10%" />
								</c:if>
							</c:if>
							<col style="width:auto" />
							<c:if test="${boardContext.boardCode == 'qna' && requestContext.opmanagerPage}">
								<col style="width:10%" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<c:choose>
								<c:when test="${boardContext.boardCode == 'refer'}">
									<th scope="col">이미지</th>
									<th scope="col">사이트명</th>
									<th scope="col">URL</th>
								</c:when>
								<c:otherwise>
									<th scope="col">제목</th>
									<c:if test="${boardContext.boardCode == 'qna'}">
										<th scope="col">작성자</th>
									</c:if>
									<c:if test="${boardContext.boardCode != 'faq'}">
										<th scope="col">등록일</th>
										<c:if test="${boardContext.boardCode != 'ebook' && boardContext.boardCode != 'qna'}">
											<th scope="col">첨부파일</th>
										</c:if>
									</c:if>
									<th scope="col">조회수</th>
									<c:if test="${boardContext.boardCode == 'qna' && requestContext.opmanagerPage}">
										<th scope="col">답변</th>
									</c:if>
									
								</c:otherwise>
							</c:choose>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="board" varStatus="i">
							<tr>
								<td>${pagination.itemNumber - i.count}</td>
								<c:choose>
								
									<c:when test="${boardContext.boardCode == 'refer'}"> <!-- 참고사이트 리스트에서 이미지노출 -->
										<td>
											
											
												<c:forEach items="${board.boardFiles}" var="file" varStatus="i">
													<c:if test="${file.fileId == 0}">
														<img src="/content/images/accident/no_image.gif" alt="">
													</c:if>
													<c:if test="${file.fileId > 0}">
														<img src="/upload/${boardContext.boardCode}/${board.boardId}/${file.fileId}.${file.fileType}" style="width:140px; height:32px;" alt="${file.fileDescription}" />
													</c:if>
												</c:forEach>
											
										</td>
										<td class="tal">
											<c:if test="${requestContext.opmanagerPage == false}">${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}</c:if>
											<c:if test="${requestContext.opmanagerPage == true}"><a href="${boardContext.boardBaseUri}/${board.boardId}/modify?url=${requestContext.currentUrl}">${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}</a></c:if>
										</td>
										<td><a href="${board.content}" target="_blank">${board.content}</a></td>
									</c:when>
									
									<c:when test="${boardContext.boardCode == 'download' || boardContext.boardCode == 'irmanage'}"> <!-- 양식다운로드, 경영공시는 리스트에서 파일 다운로드 -->
										<td class="tal">
											<c:if test="${requestContext.opmanagerPage == false}">
												<c:forEach items="${board.boardFiles}" var="file" varStatus="i">
													<a href="${boardContext.boardBaseUri}/${board.boardId}/download/${file.fileId}" onclick="return File.downloadConfirm()">${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}</a>
												</c:forEach>
											</c:if>
											<c:if test="${requestContext.opmanagerPage == true}"><a href="${boardContext.boardBaseUri}/${board.boardId}/modify?url=${requestContext.currentUrl}">${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}</a></c:if>
											<c:if test="${op:getDaysDiff(fn:substring(board.creationDate, 0, 8)) <= boardContext.boardCfg.showNewIcon}"><span class="icon small orange">new</span></c:if>
										</td>
										<td>${fn:substring(board.creationDate, 0, 4)}-${fn:substring(board.creationDate, 4, 6)}-${fn:substring(board.creationDate, 6, 8)}</td>
										<td><c:if test="${board.fileCount > 0}"><img src="/content/images/common/icon_file.gif" alt="" /></c:if></td>
										<td>${board.hit}</td>
									</c:when>
									
									<c:otherwise>
										<td class="tal">
											<c:if test="${board.depth > 0}">
												<img src="/content/images/btn/icon_answer.gif" alt="reply" style="margin-left:${board.depth * 12}px" />
											</c:if>
											<c:choose>
												<c:when test="${boardContext.boardCode == 'ebook'}">
													<c:if test="${requestContext.opmanagerPage == false}">
														<a href="javascript:;" onClick="showEbook('${board.content}',1000,70)">
															${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}
														</a>
													</c:if>
													<c:if test="${requestContext.opmanagerPage == true}">
														<a href="${boardContext.boardBaseUri}/${board.boardId}/modify?url=${requestContext.currentUrl}">
															${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}
														</a>
													</c:if>
												</c:when>
												<c:when test="${boardContext.boardCode == 'qna'}">
													<c:if test="${requestContext.opmanagerPage == false}">
														<a href="javascript:;" onclick="openLayer('layerPop',230,280,${board.boardId},'${board.boardCode }')">
															${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}
														</a>
													</c:if>
													<c:if test="${requestContext.opmanagerPage == true}">
														<a href="${boardContext.boardBaseUri}/${board.boardId}/modify?url=${requestContext.currentUrl}">
															${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}
														</a>
													</c:if>
												</c:when>
												<c:otherwise>
													<c:if test="${requestContext.opmanagerPage == false}">
														<a href="${boardContext.boardBaseUri}/${board.boardId}?url=${requestContext.currentUrl}">
															${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}
														</a>
													</c:if>
													<c:if test="${requestContext.opmanagerPage == true}">
														<a href="${boardContext.boardBaseUri}/${board.boardId}/modify?url=${requestContext.currentUrl}">
															${op:strcut(board.subject, boardContext.boardCfg.subjectLength)}
														</a>
													</c:if>
												</c:otherwise>
											</c:choose>
											
											<c:if test="${op:getDaysDiff(fn:substring(board.creationDate, 0, 8)) <= boardContext.boardCfg.showNewIcon}"><span class="icon small orange">new</span></c:if>
										</td>
										<c:if test="${boardContext.boardCode == 'qna'}">
											<td>
												${board.userName}
											</td>
										</c:if>
										<c:if test="${boardContext.boardCode != 'faq'}">
											<td>${fn:substring(board.creationDate, 0, 4)}-${fn:substring(board.creationDate, 4, 6)}-${fn:substring(board.creationDate, 6, 8)}</td>
											<c:if test="${boardContext.boardCode != 'ebook' && boardContext.boardCode != 'qna'}">
												<td><c:if test="${board.fileCount > 0}"><img src="/content/images/common/icon_file.gif" alt="" /></c:if></td>
											</c:if>
										</c:if>
										<td>${board.hit}</td>
										<c:if test="${boardContext.boardCode == 'qna' && requestContext.opmanagerPage}">
											<td>
												<a href="${boardContext.boardBaseUri}/${board.boardId}/reply?url=${requestContext.currentUrl}">[답변하기]</a>
											</td>
										</c:if>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
						<c:if test="${empty list}">
							<tr>
								<td colspan="10">등록된 글이 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div><!--// table_type01 E -->
		
		
		
		
		<!-- 사이즈 314*205-->
		<div id="layerPop" style="display:none;">
			<form method="post" action="${boardContext.boardBaseUri}/passCheck" name="passCheckForm" id="passCheckForm">
				<div class="password">
					<input name="checkBoardId" id="checkBoardId" type="hidden" />
					<input name="checkBoardCode" id="checkBoardCode" type="hidden" />
					<p>입력하신 패스워드를 넣어주세요.</p>
					<input name="checkPassword" id="checkPassword" class="required" type="password" title="패스워드" />
				</div>
				<div class="btn_center">
					<input type="image" src="/content/images/news/btn_confirm.gif" alt="확인" />
					<a href="javascript:;" onclick="closeLayer('layerPop')"><img src="/content/images/news/btn btn-default.gif" alt="취소" /></a>
				</div>
				<a href="javascript:;" onclick="closeLayer('layerPop')" class="close"><img src="/content/images/news/btn_close.gif" alt="close" /></a>
			</form>
		</div>
		
		<c:if test="${requestContext.opmanagerPage}">
			<div class="board_guide">
				<p class="total">전체 : <em>${op:numberFormat(pagination.totalItems)}</em></p>
			</div>
			<page:pagination-manager />
		</c:if>
		<c:if test="${requestContext.opmanagerPage == false}">
			<page:pagination />
		</c:if>
		
		
		<c:if test="${requestContext.opmanagerPage}">
			<p class="btns">
				<span class="right">	
					<c:if test="${boardContext.boardAuthority.writeAuthority}">
						<a href="${boardContext.boardBaseUri}/write?url=${requestContext.currentUrl}" class="btn orange">글쓰기</a>
					</c:if>		
				</span>
			</p>
		</c:if>
		<c:if test="${boardContext.boardCode == 'qna' && requestContext.opmanagerPage == false}">
			<div class="btnarea">
				<a href="${boardContext.boardBaseUri}/qnaWrite?url=${requestContext.currentUrl}">
					<img src="/content/images/btn/btn btn-active.gif" alt="등록하기" />
				</a>
			</div>
		</c:if>
		
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