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
<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/notice/list">고객센터</a>
			<span>공지사항</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<div id="contents">
 		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_customer.jsp" />   
	 	<div class="contents_inner">   
			<h2>공지사항</h2>
			<div class="board_info"> 
				<p>공지사항 총 <strong>${count}</strong>건</p> 
				<div class="board-search"> 
					<form:form action="/notice/list" modelAttribute="noticeParam" method="get">
						<form:hidden path="where" value="SUBJECT" />
						<form:input path="query" title="검색어입력" />
						<button type="submit" class="btn btn-m btn-submit" title="검색">검색</button>
					</form:form> 
				</div> 
			</div>
			<div class="board_wrap"> 	
		 		<table cellpadding="0" cellspacing="0" class="board-list">
		 			<caption>공지사항</caption>
		 			<colgroup> 
			 			<col style="width:85px;"> 
			 			<col style="width:auto;">
						<col style="width:150px;">
						<col style="width:85px;">
		 			</colgroup>
		 			<thead>
		 				<tr>
		 					<th scope="col">번호</th>
		 					<th scope="col">제목</th> 
		 					<th scope="col">작성일</th> 
		 					<th scope="col">조회수</th> 
		 				</tr>
		 			</thead>
		 			<tbody>
						<c:if test="${count == 0}">
							<tr>
								<td colspan="4">입력하신 검색 결과가 없습니다.</td>
							</tr>
						</c:if>
		 				<c:forEach items="${noticeList}" var="list" varStatus="i">
			 				<tr>
			 					<td>
			 						<c:if test="${list.noticeFlag == 'Y'}">
			 							<span class="label_notice">공지</span>
			 						</c:if>
			 						<c:if test="${list.noticeFlag == 'N'}">
			 							${pagination.itemNumber - i.count}
			 						</c:if>
			 					</td>
								<td class="tleft <c:if test="${list.noticeFlag == 'Y'}">notice</c:if>" >
									<div><a href="${list.url}" ${list.rel} class="btn-a">${list.subject}</a></div>
								</td>
			 					<td>
			 						<div>${op:date(list.createdDate)}</div> 
			 					</td> 
			 					<td>
			 						<div>${list.hits}</div> 
			 					</td> 
			 				</tr> 
		 				</c:forEach>
		 			</tbody>
		 		</table>  		 
			</div><!--// board_wrap E-->  
			 
			<page:pagination />
		</div><!--// contents_inner E-->
	</div><!--// contents E--> 
</div> <!-- // inner E -->