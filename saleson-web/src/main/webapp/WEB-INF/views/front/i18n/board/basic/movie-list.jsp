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


<board:header />
	
<div class="pr_wrap mt45">

		<div class="pr_video pb30">
			
			<div class="pr_left" >
				<iframe id="movie_space" width="393px" height="247px" src="" frameborder="0" allowfullscreen></iframe>
			</div> <!-- // pr_left -->

			<div class="pr_right">
				<h4>자막내용</h4>
				<textarea id="caption_space" rows="15" cols="24" class="video_txt"></textarea>
			</div> <!-- // pr_right -->	

		</div> <!-- // pr_video -->
		<div class="pr_list">
			<ul>
				<c:forEach items="${list}" var="board" varStatus="i">
					<c:set var="liClass" value="" />
					<c:if test="${(i.index + 1) % 3 == 0 && i.index > 0}">
						<c:set var="liClass" value="last" />
					</c:if>
					<li class="${liClass}">
						<dl class="one_list">
							<dt>
								<span class="img">
									<c:if test="${requestContext.opmanagerPage == false}">
										<a href="javascript:;" onClick="fn_movieSel(${i.index});">
											<c:set var="mImage" value="${fn:substringBefore(board.etc2, '?')}" />
											<c:set var="mImage" value="${fn:substringAfter(mImage, 'embed/')}" />
											<img src="http://img.youtube.com/vi/${mImage}/0.jpg" alt="" width="180px" height="114px"/>
											<input type="hidden" id="movie${i.index}" value="${board.etc2 }" />
											<input type="hidden" id="caption${i.index}" value="${op:nl2br(board.content)}" />
										</a>
									</c:if>
									<c:if test="${requestContext.opmanagerPage == true}">
										<a href="${boardContext.boardBaseUri}/${board.boardId}/modify?url=${requestContext.currentUrl}">
											<c:set var="mImage" value="${fn:substringBefore(board.etc2, '?')}" />
											<c:set var="mImage" value="${fn:substringAfter(mImage, 'embed/')}" />
											<img src="http://img.youtube.com/vi/${mImage}/0.jpg" alt="" width="180px" height="114px"/>
										</a>
									</c:if>
								</span>
							</dt>
							<dd class="pr_tit">${board.subject }</dd>
							<dd class="pr_date">
								${fn:substring(board.creationDate, 0, 4)}-${fn:substring(board.creationDate, 4, 6)}-${fn:substring(board.creationDate, 6, 8)}
							</dd>
						</dl>
					</li>
				</c:forEach>
			</ul>

		</div> <!-- // pr_list -->
		<c:if test="${empty list}">
			<div class="no_content">
				등록된 글이 없습니다.
			</div>
		</c:if>
		<!-- pagination S -->
		<page:pagination-manager />
		
		<c:if test="${requestContext.opmanagerPage}">
			<p class="btns">
				<span class="right">	
					<c:if test="${boardContext.boardAuthority.writeAuthority}">
						<a href="${boardContext.boardBaseUri}/write?url=${requestContext.currentUrl}" class="btn ${btnClass}">등록</a>
					</c:if>		
				</span>
			</p>
		</c:if>

	</div> <!-- // pr_wrap -->	
	
<board:footer />


<script type="text/javascript">
$(function() {
	$('input[name=statusCode]').on("click", function() {
		$('#boardSearchParam').submit();
	});
	
	if('${empty list}' == 'false') fn_movieSel(0);
});

function fn_movieSel(num){
	$("#movie_space").attr("src", $("#movie"+num).val());
	$("#caption_space").val($("#caption"+num).val());
}


</script>	