<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/wishlist">활동정보</a> 
			<span>1:1문의</span>  
		</div>
	</div><!-- // location_area E --> 
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<div id="contents" class="pt0">
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" />
		<div class="contents_inner">
			<h2>1:1문의</h2>
			
			<div class="mypage_search_area">
				<form:form modelAttribute="qnaParam" action="/mypage/inquiry" method="get">
			 		<fieldset>
						<dl>
							<dt>기간별 조회</dt>
							<dd>
								<div class="date_box">
									<span><form:input path="searchStartDate" maxlength="8" class="datepicker term" title="${op:message('M00024')}" /></span>
									<span>~</span>
									<span><form:input path="searchEndDate" maxlength="8" class="datepicker2 term" title="${op:message('M00025')}" /></span>
								</div>
								<div class="search_term">
									<button type="button" class="btn date_search week-1" title="1주일">1주일</button> 
									<button type="button" class="btn date_search month-1" title="1개월">1개월</button> 
									<button type="button" class="btn date_search month-3" title="3개월">3개월</button> 
									<button type="button" class="btn date_search month-6"   title="6개월">6개월</button> 
								</div>
							</dd>
							<dt class="mt5">내용 조회</dt>
							<dd class="mt5">
								<div class="col-w-0 search_write">
									<form:input path="query" title="조회"/>
									<input type="hidden" name="where" value="SUBJECT"/>
									<button type="submit" class="btn btn_search_min" title="조회">조회</button>
								</div>
							</dd>
						</dl> 
					</fieldset>		  
		 		</form:form>
			</div> <!-- // mypage_search_area E -->
			
			<div class="btn_wrap pt10">
				<div class="btn_left">
					<button type="button" onclick="location.href='/mypage/inquiry'" class="btn btn-m btn-default" title="초기화"><img src="/content/images/icon/icon_reset.png" alt="초기화">초기화</button>
				</div> 
			</div><!--// btn_wrap E-->
			
			<div class="list_info clear">
				<p class="pt10">1:1문의 총 <span>${count }</span>건</p>
			</div><!--// list_info E-->
			
			<div class="board_wrap">
				
				<table cellpadding="0" cellspacing="0" class="board-list mypage_qna">
		 			<caption>1:1문의</caption>
		 			<colgroup>
		 				<col style="width:66px;">
		 				<%--<col style="width:257px;"> --%>
		 				<col style="width:auto;">
		 				<col style="width:125px;">
		 				<col style="width:92px;">
		 				<col style="width:88px;">
		 			</colgroup>
		 			<thead>
		 				<tr>
		 					<th scope="col">번호</th>
		 					<%--<th scope="col">상품</th> --%>
		 					<th scope="col">내용</th>
		 					<th scope="col">작성일</th>
		 					<th scope="col">답변유무</th>
		 					<th scope="col">수정</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 				
		 				<c:forEach items="${qnaList}" var="list" varStatus="i">
		 					
		 					<tr id="questTit${i.count}" class="tit-off">
			 					<td>${pagination.itemNumber - i.count}</td>
			 					<%--
			 					<td class="tleft item_name">
			 						<c:if test="${not empty list.itemUserCode }">	 						 
			 						<div class="item"><img src="${list.imageSrc }" alt="${list.itemName }"></div>
			 						<div class="cont">
			 							<a href="#">
				 							<p class="code">${list.itemUserCode }</p> 
											<p class="name">${list.itemName }</p>
										</a>
			 						</div>
			 						</c:if> 						 
			 					</td>
			 					 --%>
			 					<td class="tleft  "><a href="#quest${i.count}" onclick="javascript:showQnA(${i.count})">${list.subject}</a></td>
			 					<td>${op:date(list.createdDate) }</td>
			 					<td>
			 						<c:choose>
			 							<c:when test="${list.answerCount > 0}">
			 								<strong>답변완료</strong>
			 							</c:when>
			 							<c:otherwise>
			 								<strong>답변대기</strong>
			 							</c:otherwise>
			 						</c:choose>
			 					</td>
			 					<td class="qa_btn">
			 						<c:choose>
			 							<c:when test="${list.answerCount > 0}">
			 								-
			 							</c:when>
			 							<c:otherwise>
			 								<%--<button type="button" class="btn btn-s btn-default" title="수정">수정</button> --%>
		 									<button type="button" class="btn btn-s btn-default mt5" title="삭제" onclick="inquiryDelete('${list.qnaId}');">삭제</button>
			 							</c:otherwise>
			 						</c:choose>
			 					</td>
			 				</tr>
	 						<tr id="quest${i.count}" class="view-off">
								<td colspan="5" class="question-open tleft">
									<div class="qna-q clear">
										<p>${list.question}</p>
									</div> 	 
									<c:if test="${list.answerCount > 0}">
										<div class="qna-a">
											<p>${list.qnaAnswer.answer}</p>
											<span class="date">(답변일 : ${op:date(list.qnaAnswer.answerDate)})</span>
											<br> 
										</div>
									</c:if>								
								</td>
							</tr>
	 					
					</c:forEach>
		 				
		 				<c:if test="${empty qnaList}">
							<tr>
			 					<td colspan="5">
			 						<div class="coupon_not">등록된 1:1문의가 없습니다.</div>
			 					</td> 
			 				</tr> 
						</c:if>	
		 				
		 			</tbody>
		 		</table><!--//view E-->
				
			</div>
			<page:pagination />
		</div> 	 
	</div>
</div>

<page:javascript>	
<script type="text/javascript"> 
$(function() {
	
	$('#lnb_active').addClass("on");
	
	Common.DateButtonEvent.set('.search_term button[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	ActiveSearchTermButtons();
		 
});

function inquiryDelete(qnaId) {
	if (confirm("해당 문의 내용을 삭제하시겠습니까?")) {
		location.href = "/mypage/inquiry-delete/inquiry/" + qnaId;
	}
}

function ActiveSearchTermButtons() {
	var searchTerm = '${searchTerm}';

	if(searchTerm != null && searchTerm != '') {
		$('.' + searchTerm).addClass('active');
	}
	
	$('.date_search').on('click', function() {
		$('.date_search').removeClass('active');
	});
	
}
 </script>
 </page:javascript>