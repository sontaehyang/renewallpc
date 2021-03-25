<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="inner wish_wrap">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<a href="/mypage/order">마이페이지</a> 
			<a href="/mypage/wishlist">활동정보</a> 
			<span>관심상품</span> 
		</div>
	</div><!-- // location_area E --> 
	
	<c:if test="${requestContext.userLogin == true }">
		<jsp:include page="../include/mypage-user-info.jsp" />
	</c:if>

	<div id="contents" class="pt0">
		<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_mypage.jsp" />
		<div class="contents_inner">
			<h2>관심상품</h2>		 
			<div class="guide_box mt20">
			 	<ul class="order_guide02">
			 		<li>상품 상세페이지에서 관심상품으로 등록하신 상품들의 리스트입니다.</li>
					<li>자주 구입하시는 상품이나 구입하신 상품을 등록해두면 다음번에 쉽게 주문하실 수 있습니다.</li> 
				</ul>	
			</div><!--// guide_box E-->
			
			<div class="list_info clear">
				<p>관심상품 총 <span>${totalItemCount}</span>건</p>			
			</div><!--// list_info E-->
			
			<div class="board_wrap">
				<form id="listForm">
					
					<table cellpadding="0" cellspacing="0" class="order_list">
						<caption>상품정보</caption>
						<colgroup>
							<col style="width:44px;">
							<col style="width:auto;">
							<col style="width:108px;"> 
							<col style="width:100px;"> 
							<col style="width:108px;"> 
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><input type="checkbox" id="check_all" title="체크박스"></th>
								<th scope="col">상품명/옵션정보</th>
								<th scope="col">상품금액</th>  
								<th scope="col">재고</th>
								<th scope="col">삭제</th>  
							</tr>
						</thead>
						<tbody> 
							<c:forEach items="${wishlists}" var="wishlist">	 
							<tr>
								<td>
									<input type="checkbox" name="id" value="${wishlist.wishlistId}" />
								</td>
								<td class="tleft">
									<a class="item_info" href="${wishlist.item.link }">
										<p class="photo">
											<img src="${ shop:loadImage(wishlist.item.itemUserCode, wishlist.item.itemImage, 'XS') }" alt="${wishlist.item.itemName }">
										</p>
										<div class="order_option">
											<p class="item_name">
													${wishlist.item.itemName }
											</p>
										</div>
									</a><!--// item_info E-->
								</td>
								<td class="price_down">
									<span class="down">
										<c:if test="${wishlist.item.listPrice != 0}">
											${op:numberFormat(wishlist.item.listPrice)}원<br/>
										</c:if>
									</span>
<%-- 								<span>${op:numberFormat(wishlist.item.salePrice)}원</span> --%>
									<span>${op:numberFormat(wishlist.item.presentPrice)}원</span>
								</td>
								<td>
									<c:choose>
										<c:when test='${wishlist.item.itemSoldOutFlag=="N"}'>
		 									${op:message('M01458')} <!-- 재고 있음 -->
		 								</c:when>
		 								<c:otherwise>
		 									${op:message('M01495')} <!-- 재고 없음 -->
		 								</c:otherwise>
									</c:choose>
								</td>
								<td>
									<button type="button" class="btn btn-s btn-default delete_item" title="삭제">삭제</button>  
								</td>
							</tr> 	 
							</c:forEach>
							
							<c:if test="${empty wishlists}">
								<tr>
				 					<td colspan="5">
				 						<div class="coupon_not">등록된 정보가 없습니다.</div>
				 					</td> 
				 				</tr> 
							</c:if>	
						</tbody>
					</table>
				</form>
			</div>
			
			<div class="btn_wrap pt10">
				<button type="button" class="btn btn-m btn-default btn_left" onclick="deleteAll()">선택삭제</button>
				<%--<button type="button" class="btn btn-m btn-default btn_left ml4">장바구니 담기</button> --%>
			</div><!--// btn_wrap E-->
			
		</div>
	</div>
	
</div>




 	
<jsp:include page="../include/layer-cart.jsp" />

<page:javascript>
<script type="text/javascript">
$(function() {
	// 메뉴 활성화
	$('#lnb_active').addClass("on");
	
	// 품절상품 체크박스 disabled
	$('input[name=arrayQuantitys].disabled').each(function() {
		$(this).closest('tr').find('input').prop('disabled', true);
	});
	
	// 관심상품 그룹명 변경 레이어 닫기
	$('#close_group_layer').on('click', function() {
		$('.layer_catalog').hide();
	});
	
	
	
	// 목록데이터 - 개별삭제처리
	$('.delete_item').on('click', function(e) {
		e.preventDefault();
		$('#check_all').prop("checked", false);
		$(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
		$(this).closest("tr").find('input[name=id]').prop("checked", true);
		
		Common.updateListData("/wishlist/list/delete", Message.get("M00196"));	// 삭제하시겠습니까?
	});
	
	
});

// 전체삭제 
function deleteAll() {
	
	// 품절상품 체크박스 disabled
	$('input[name=id]').prop('disabled', false);
	
	if ($('input[name=id]:checked').length == 0) {
		alert('삭제할 상품을 선택해주세요.');
		return;
	}
	
	if (confirm(Message.get("M00196"))) {	// 삭제하시겠습니까?
		var $form = $('#listForm');
		$.post("/wishlist/list/delete", $form.serialize(), function(response) {
			Common.responseHandler(response, function(response){
				location.reload();
			});
		});
	} else {
		// 품절상품 체크박스 disabled
		$('input[name=arrayQuantitys].disabled').each(function() {
			$(this).closest('tr').find('input').prop('disabled', true);
			$(this).closest('tr').find('input[name=id]').prop('checked', false);
		});
	}
}

</script>	
</page:javascript>	
	 	