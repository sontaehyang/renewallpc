<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


	<div id="sub_contents_min" class="customer">
		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		<h3><span>${op:message('M00269')} </span></h3>

		 <form id="noticeParam" method="post">
		 	<input type="hidden" name="where" value="${noticeParam.where}">
		 	<input type="hidden" name="query" value="${noticeParam.query}">
		 	<input type="hidden" name="sort" value="${noticeParam.sort}">
		 	<input type="hidden" name="orderBy" value="${noticeParam.orderBy}">
		 	<input type="hidden" name="itemsPerPage" value="${noticeParam.itemsPerPage}">
		 </form>

		<div class="board_view_type01 "> 
	 		<table cellpadding="0" cellspacing="0" summary="" class="board_list_table">
	 			<caption>table list</caption>
	 			<colgroup>
	 				<col style="width:auto;"> 
	 				<col style="width:100px;">
	 				<col style="width:50px;">  
	 			<tbody>
	 				<tr> 
	 					<th class="label">제목</th>
	 					<th>작성일</th>
	 					<th class="last">조회수</th>
	 				</tr> 
	 				<tr> 
	 					<td class="label">${notice.subject}</td>
	 					<td>${op:date(notice.createdDate)}</td>
	 					<td class="last">${notice.hits}</td>
	 				</tr> 
	 				<tr> 
	 					<td colspan="3" class="bL0">
	 						<div>
	 							${notice.content}
	 						</div>
	 					</td>
	 				</tr>
	 				<c:if test="${beforeNotice.noticeId != 0}">	 
	 				<tr>
	 					<td colspan="3" class="tleft">
	 						<strong style="padding-right:20px;">이전글</strong>
	 						<a href="/seller/notice/view/${beforeNotice.noticeId}" style="padding-right:20px;">${beforeNotice.subject}</a> 
	 					</td>
	 				</tr>
	 				</c:if>
	 				<c:if test="${beforeNotice.noticeId == 0}">
	 				<tr>
						<td colspan="3" class="tleft">
							<strong style="padding-right:20px;">이전글</strong>이전 글이 존재하지 않습니다.
						</td>
					</tr>
					</c:if> 
					<c:if test="${afterNotice.noticeId != 0}">
	 				<tr>
	 					<td colspan="3" class="tleft">
	 						<strong style="padding-right:20px;">다음글</strong>
	 						<a href="/seller/notice/view/${afterNotice.noticeId}" style="padding-right:20px;">${afterNotice.subject}</a> 
	 					</td>
	 				</tr>
	 				</c:if>
	 				<c:if test="${afterNotice.noticeId == 0}">
	 				<tr>
						<td colspan="3" class="tleft">
							<strong style="padding-right:20px;">다음글</strong>다음 글이 존재하지 않습니다.
						</td>
					</tr>
					</c:if>				 
	 			</tbody>
	 		</table><!--//view E-->		 		 	
		</div><!--//board_view_type01 E-->
        
        <div class="btn_center"> 
	 		<a href="/seller/notice/list" class="btn btn-default">목록보기</a>
	 	</div>
	</div><!--// sub_contents_min E-->
	

	
<page:javascript>	
<script type="text/javascript">

</script>
</page:javascript>