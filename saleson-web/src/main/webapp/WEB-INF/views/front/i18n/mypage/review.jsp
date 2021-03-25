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
			<span>이용후기</span>  
		</div>
	</div><!-- // location_area E --> 
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>
	
	<div id="contents" class="pt0">
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" />
		
		<div class="contents_inner">
			<h2>이용후기</h2>
			<div class="guide_box mt20">
			 	<ul class="order_guide02">
			 		<li>이용후기는 구매확정 된 상품에 한하여 주문일로부터 90일내 작성 가능합니다. </li>
					<li>작성 건에 100${op:message('M00246')} 적립해드립니다. (단, ${op:message('M00246')}는 승인완료시 적립)</li>
				</ul>	
			</div>
			
			<div class="mypage_search_area">
				<form:form modelAttribute="searchParam" action="/mypage/review" method="get">
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
							<dt class="mt5">상품명 조회</dt>
							<dd class="mt5">
								<div class="col-w-0 search_write">
									<form:hidden path="where" value="ITEM_NAME" />
									<form:input path="query" title="상품명조회" placeholder="상품명 조회" />
									<button type="submit" class="btn btn_search_min" title="조회">조회</button>
								</div>
							</dd>
						</dl> 
					</fieldset>		  
		 		</form:form>
			</div> <!-- // mypage_search_area E -->
			
			<div class="btn_wrap pt10">
				<div class="btn_left">
					<button type="button" class="btn btn-m btn-default" onclick="location.href='/mypage/review'"><img src="/content/images/icon/icon_reset.png" alt="초기화">초기화</button>
				</div> 
			</div><!--// btn_wrap E-->
			 
			<div class="tabs tabs-2">
				<ul> 
					<li><a href="/mypage/review-nonregistered">미등록 이용후기(<span>${nonReviewCount }</span>)</a></li> 
					<li><a href="#" class="on">작성한 이용후기(<span>${reviewCount }</span>)</a></li> 
				</ul> 
			</div>
			 
			<div class="list_info clear">
				<p>이용후기 총 <strong>${op:numberFormat(reviewCount) }</strong>건</p>
				<span class="guide">이용후기가 승인된 후에는 수정이 불가능합니다.</span>
			</div>
			
			<div class="board_wrap">
				
				<table cellpadding="0" cellspacing="0" class="board-list">
		 			<caption>이용후기</caption>
		 			<colgroup>
		 				<col style="width:50px;">
		 				<col style="width:257px;">
		 				<col style="width:auto;">
		 				<col style="width:125px;">
		 				<col style="width:92px;">
		 				<col style="width:88px;">
		 			</colgroup>
		 			<thead>
		 				<tr>
		 					<th scope="col">번호</th>
		 					<th scope="col">상품</th>
		 					<th scope="col">내용/평가</th>
		 					<th scope="col">작성일</th>
		 					<th scope="col">승인유무</th>
		 					<th scope="col">수정</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 				
		 				
		 				<c:forEach items="${reviewList}" var="list" varStatus="i">
					
							<tr id="questTit${ i.index }" class="tit-off">
								<td>${pagination.itemNumber - i.count }</td>
								<td class="tleft item_name">	 						 
			 						<div class="item">
			 							<img src="${ shop:loadImageBySrc(list.item.imageSrc, 'XS') }" alt="${list.item.itemName}">
<%-- 			 							<img src="${list.item.imageSrc}" alt="${list.item.itemName}"> --%>
			 						</div>
			 						<div class="cont">
			 							<a href="${list.item.link}">
				 							<p class="code">[${list.item.itemUserCode}]</p> 
											<p class="name">${list.item.itemName}</p>
										</a>
			 						</div>		 						 
			 					</td>
			 					<td>
			 						<div class="tleft review_cont">
			 							<a href="javascript:showQnA(${ i.index });" id="questTit${ i.index }">
				 							<p class="tit">${list.subject}</p>
				 							<p class="txt">${op:strcut(op:stripTags(list.content),10)}</p>
				 							<div class="star_rating">
									 			<span style="width:${list.score * 20}%"></span><span class="point">${list.score}</span>
									 		</div>
									 	</a>
			 						</div>
		 						</td>
		 						<td>
		 							<div><span>${op:date(list.createdDate)}</span></div>
		 						</td>
			 					<td>
			 						<c:if test="${list.displayFlag == 'N' }">
				 						<strong class="ok">승인대기</strong>
			 						</c:if>
			 						<c:if test="${list.displayFlag == 'Y' }">
			 							<strong class="ok">승인완료</strong>
			 						</c:if>
			 					</td> 
			 					<td>
			 						<c:if test="${list.displayFlag == 'N' }">
				    					<button type="button" class="btn btn-s btn-default" onclick="deleteCheck('${list.itemReviewId}')">삭제</button> 
				    				</c:if> 
			 					</td> 
							</tr>
							<!-- 후기작성 -->
			 				<tr id="quest${ i.index }" class="view-off">
								<td colspan="6" class="question-open tleft">
									${list.content}
									<c:forEach items="${list.itemReviewImages}" var="image" varStatus="i">
										<c:if test="${image.itemReviewImageId > 0}">
											<c:if test="${i.count == 1}">
												<br/><br/>
											</c:if>
											<img src="${image.imageSrc}" />
										</c:if>
									</c:forEach>
								</td>
							</tr>
								
			 			</c:forEach>
		 				
		 				<c:if test="${empty reviewList}">
							<tr>
			 					<td colspan="6">
			 						<div class="coupon_not">등록된 이용후기가 없습니다.</div>
			 					</td> 
			 				</tr> 
						</c:if>	
		 				
		 			</tbody>
		 		</table><!--//view E-->	
				
				<page:pagination />
			</div>
		</div>
			 
	</div>
	
</div>

<page:javascript>
<script type="text/javascript">

$(function() {
	Common.DateButtonEvent.set('.search_term button[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	
	$('#lnb_active').addClass("on");
	
	ActiveSearchTermButtons();
});

function deleteCheck(reviewId) {	

	if(confirm(Message.get("M00196"))) {	// 삭제하시겠습니까? 
		location.replace("/mypage/review/delete/" + reviewId);
	} else {
		return;
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