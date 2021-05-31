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
	
	<!-- 내용 : s -->
	<div class="con" style="padding:20px 20px 20px 20px;">
		<div class="event_banner02" style="padding:0 0 20px 0;">
			<!-- // 타임세일 img src="/content/images/common/timeSale1.jpg" alt="타임세일"-->
			<img src="/content/images/common/timeSale_20210527.jpg" alt="월간특가">
		</div>
		<%--
		<div class="search_result sel">
			
			<select id="op-cateSelect">
				<option value="/m/event/spot/all">전체</option>
				<c:forEach items="${categorylist}" var="item" varStatus="i">
					<option value="/m/event/spot/${item.code}" ${code == item.code ? "selected" : ""}>${item.name}</option>
				</c:forEach>
			</select>
		
		</div>		
		 --%>
		 
		<!-- search_result -->
		<form:form modelAttribute="itemParam" method="get">
			<form:hidden path="page" />
		</form:form>
		
		<div class="product_wrap">
			<ul class="product_list" id="op-list-data">
			
			
				<jsp:include page="../include/item-spot-list.jsp"></jsp:include>
							
			
			</ul>
			
			<c:if test="${empty list }">
			<ul class="product_list">
				<li>
					<div class="common_none">
						<p>상품이 없습니다.</p>
					</div>
				</li>
			</ul>				
			</c:if>
			
			<div class="load_more" id="op-list-more">
				<button type="button" class="btn_st2" onclick="javascript:paginationMore('spot')"><span>상품 더보기</span></button>
			</div>
			
		</div>
		<!-- //product_wrap -->
	
	</div>
	<!-- 내용 : e -->
	
	<%-- 	<div class="event">
		
	 		<h3 class="title">마감임박 스팟상품</h3>  
			
			<!-- 상품 리스트 시작 -->
			<div id="list" class="ranking_list">
				<ul id="list-data" class="list_item">		
					<c:forEach items="${list}" var="item" varStatus="i">
						<li>
							<a href="/m/products/view/${item.itemUserCode}">
								<span class="down">${item.discountRate}<span>%</span></span>
								<div class="product_img">
									<img src="${item.imageSrc}" width="91" height="91" alt="제품이미지" /> 
								</div>
								<div class="cont">
									<span class="icon_close">
										<c:choose>
											<c:when test="${item.spotEndDDay == 0}">
												오늘 마감
											</c:when>
											<c:otherwise>
												마감임박 D-${item.spotEndDDay}
											</c:otherwise>
										</c:choose>
									</span>
									<p class="name">${item.itemName }</p>
									<div class="price_zone">
										<p class="last">
					 						<span class="item_price">${op:numberFormat(item.itemPrice)}</span>
											<span class="sale_price">${op:numberFormat(item.presentPrice)}</span>
										</p>									
				 					</div>
				 					<div class="sale_date">
					 			 		<p>판매요일 :
				 			 				<c:forEach items="${item.spotWeekDayList}" var="code">
												<c:if test="${code.detail == '1'}">
													<span>${fn:substring(code.label, 0, 1)}</span>
												</c:if>
											</c:forEach>
					 			 		</p>
					 			 		<p>판매시간 : <span>${op:timeFormat(item.spotStartTime)}~${op:timeFormat(item.spotEndTime)}</span> </p>
					 			 	</div>
								</div><!--//cont E-->
							</a> 			
						</li> 
					</c:forEach>
				</ul>
			</div>
			
			<div id="list-more" class="btn_area wrap_btn02">
	 			<a href="javascript:paginationMore('spot_${itemParam.orderBy}_${itemParam.sort}')" class="btn_more">스팟 더보기 <span><img src="/content/mobile/images/common/btn_mores.png" alt="상품더보기" height="15"/></span></a>
			</div>
			
		</div><!--// event E--> --%>
	
<page:javascript>	
<script type="text/javascript">
$(function() {
	
	// 더보기 버튼
	showHideMoreButton();
	
});




function paginationMore(key) {
					

	$('input[name="page"]', $("#itemParam")).val(Number($('input[name="page"]', $("#itemParam")).val()) + 1);
 	$.post('/m/event/spot/list', $("#itemParam").serialize(), function(html) {
 		$("#op-list-data").append(html);
 		// 더보기 버튼 
 		showHideMoreButton();
 		
 		history.pushState(null, null, "/m/event/spot?" + $("#itemParam").serialize());
 	});
}

function showHideMoreButton() {
	var totalItems = Number('${pagination.totalItems}');
	if ($("#op-list-data").find(' > li').size() == totalItems || totalItems == 0) {
		$('#op-list-more').hide();
	}
}


	


</script>
</page:javascript>