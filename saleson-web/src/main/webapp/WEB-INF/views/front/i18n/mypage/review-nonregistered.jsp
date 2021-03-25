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
				<form:form modelAttribute="searchParam" action="/mypage/review-nonregistered" method="get">
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
					<li><a href="#" class="on">미등록 이용후기(<span>${nonReviewCount }</span>)</a></li> 
					<li><a href="/mypage/review" >작성한 이용후기(<span>${reviewCount }</span>)</a></li> 
				</ul> 
			</div>
			 
			<div class="list_info clear">
				<p>미등록 이용후기 총 <strong>${op:numberFormat(nonReviewCount) }</strong>건</p>
				<span class="guide">이용후기가 승인된 후에는 수정이 불가능합니다.</span>
			</div>
			
			<div class="board_wrap">
				
				<table cellpadding="0" cellspacing="0" class="board-list">
		 			<caption>이용후기</caption>
		 			<colgroup>
		 				<col style="width:66px;">
		 				<col style="width:auto;">
		 				<col style="width:150px;">
		 			</colgroup>
		 			<thead>
		 				<tr>
		 					<th scope="col">번호</th>
		 					<th scope="col">상품</th>
		 					<th scope="col">내용/평가</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 				
		 				
		 				<c:forEach items="${reviewList}" var="list" varStatus="i">
						
							
							<tr>
			 					<td>${pagination.itemNumber - i.count }</td>
			 					<td class="tleft item_name">	 						 
			 						<div class="item">
			 							<img src="${ shop:loadImageBySrc(list.imageSrc, 'XS') }" alt="제품이미지"></div>
			 						<div class="cont">
			 							<a href="/products/view/${list.itemUserCode }">
				 							<p class="code">[${list.itemUserCode}]</p> 
											<p class="name">${list.itemName}</p>
										</a>
			 						</div>	 						 
			 					</td>
			 					<td class="qa_btn">
			 						<button type="button" class="btn btn-s btn-default" title="이용후기 작성" onclick="itemReview('${list.orderCode }','${list.itemUserCode }')">이용후기 작성</button>
			 					</td>
			 				</tr>
							
			 			</c:forEach>
		 				
		 				<c:if test="${empty reviewList}">
							<tr>
			 					<td colspan="3">
			 						<div class="coupon_not">작성 가능한 주문이 없습니다.</div>
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

function itemReview(orderCode,itemUserCode) {
	Common.popup('/item/create-review/'+orderCode+'/'+ itemUserCode, 'create_review', 820, 615, 0);
}

$(function() {
	Common.DateButtonEvent.set('.search_term button[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	
	$('#lnb_active').addClass("on");
});

</script>	
</page:javascript>