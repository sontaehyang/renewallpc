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

<div class="webzine_list">
	<div class="webzine_main">
		<div class="onWebzine">
			<img id="webImg_space" src="" style="width:320px; height:210px;" alt="" />
		</div>		
		<div class="webzine_summary">
			<h4 id="webTitle_space">웹진이 없습니다.</h4>
			<p>요약</p>
			<div id="web_space">- 웹진을 등록해주세요.</div>
		</div>
	</div>	<!--webzine_main E-->
	
	<div id="webzine_tab">			 
		<div id="webzine_list">					 
			<ul>
				<c:forEach items="${boardContext.boardCfg.categories}" var="rs">
					<c:set var="onoff" value=""/>
					<c:if test="${boardSearchParam.category == rs.value}">
						<c:set var="onoff" value="_on"/>
					</c:if>
					<li>
						<a href="${boardContext.boardBaseUri}/list?category=${rs.value}" class="tab">
							<img src="/content/images/news/w${rs.label}${onoff}.gif" alt="${rs.label}년" />
						</a>
					</li>
				</c:forEach>
			</ul>
			<div id="web_cont" class="cont_list" style="display: block;">
				<ul>
					<c:forEach items="${list}" var="board" varStatus="i">
						<input type="hidden" id="webTitle${i.index}" value="${board.subject }" />
						<input type="hidden" id="caption${i.index}" value="${op:nl2br(board.content)}" />
					  	<c:set var="liClass" value="" />
						<c:if test="${i.index % 3 == 0}">
							<c:set var="liClass" value="pl0" />
						</c:if>
						<li class="${liClass}">
						 	<p>
						 		<a href="javascript:;" onClick="fn_webzineSel(${i.index})">
						 			<c:forEach items="${board.boardFiles}" var="file">
							 			<c:if test="${file.uploadType == 0}">
											<img id="webImg${i.index}" src="/upload/${boardContext.boardCode}/${board.boardId}/${file.fileId}.${file.fileType}" style="width:194px; height:128px;" alt="${file.fileDescription}" />
										</c:if>
									</c:forEach>
						 		</a>
						 	</p>
						 	<p class="w_title">${board.subject }</p>
						 	<p>${board.etc2}</p>							 	
						 </li>
						
					</c:forEach>
				</ul>
			</div>
			 
		</div><!--//w2014년 끝-->
	  
 	</div><!--//webzine_tab E-->
 
</div><!--webzine_list E-->

<c:if test="${empty list}">
	<div class="no_content">
		등록된 글이 없습니다.
	</div>
</c:if>

<!-- pagination S -->
<page:pagination-manager />

<board:footer />

<script type="text/javascript">
$(function() {
	if('${empty list}' == 'false') fn_webzineSel(0);
});

function fn_webzineSel(num){
	/* alert(num);
	alert($("#webImg"+num).attr('src'));
	alert($("#webTitle"+num).val());
	alert($("#caption"+num).val()); */
	$("#webImg_space").attr("src", $("#webImg"+num).attr('src'));
	$("#webTitle_space").html($("#webTitle"+num).val());
	$("#web_space").html($("#caption"+num).val());
	
	//$("#movie_space").attr("src", $("#movie"+num).val());
	//$("#caption_space").val($("#caption"+num).val());
}


</script>	

