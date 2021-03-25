<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<!-- 내용 : s -->
	<div class="con">
		<div class="pop_title">
			<h3>쿠폰 적용가능 대상</h3>
			<a href="#" class="history_back">뒤로가기</a>
		</div>
		<!-- //pop_title -->
		<div class="pop_con bg">
			<div class="pop_coupon">
				<div class="inner">
					<div class="img">							
						<c:if test="${item.coupon.couponType == 1 }">		
							<p class="coupon coupon_blue">
						</c:if>
									
						<c:if test="${item.coupon.couponType == 2 }">		
							<p class="coupon coupon_green">
						</c:if>
									
						<c:if test="${item.coupon.couponType == 3 }">		
							<p class="coupon coupon_red">
						</c:if>
						
						<span>${ op:numberFormat(item.coupon.couponPay) }</span>
							
						<c:choose>
							<c:when test="${item.coupon.couponPayType eq '2'}">%</c:when>
						</c:choose>
							</p>
					</div>
					<div class="txt">
						<p class="coupon_desc"><c:choose>
							<c:when test="${ not empty item.coupon.couponPayRestriction }">
								${op:numberFormat(item.coupon.couponPayRestriction)}원 이상 상품 사용가능
							</c:when>
							<c:otherwise>
							제한금액 조건 없음
							</c:otherwise>
							</c:choose>
						</p>
						<p class="coupon_name">${ item.coupon.couponName }	</p>
						<p class="coupon_date">
							<c:choose>
							
							<c:when test="${ item.coupon.couponApplyStartDate == null && item.coupon.couponApplyEndDate == null || item.coupon.couponApplyStartDate == '' && item.coupon.couponApplyEndDate == ''}">
							유효기간 없음
							</c:when>					
							
							<c:otherwise>
							<c:if test="${item.coupon.couponApplyStartDate != '' }">				
								${ op:datetime(item.coupon.couponApplyStartDate) }					
							<c:if test="${item.coupon.couponApplyEndDate == '' }">	
							 	~				
							</c:if>						
							</c:if>
							<c:if test="${ item.coupon.couponApplyEndDate != '' }">
							~  ${ op:datetime(item.coupon.couponApplyEndDate) }
							</c:if>							
							</c:otherwise>
									
							</c:choose>
						</p>
					</div>
				</div>
			</div>
			<!-- //pop_coupon -->
			
			<div class="pop_coupon_list">
				<div class="title">
					<p>쿠폰으로 구입 가능한 제품(<span>10</span>)</p>
				</div>
				<div class="active_con">
					<ul>
						<li>
							<div class="item">
								<div class="order_img">
									<img src="/content/mobile/images/common/cart_noimage.jpg" alt="제품이미지">
								</div>
								<div class="order_name">
									<p class="tit">[자연의벗] 첫 눈의 화사함 파우더 팩트 2호 (베이지,15g)</p>
								</div>
								<div class="order_price">
									<p><span>21,254,000</span>원</p>
								</div>
							</div>
						</li>
						<li>
							<div class="item">
								<div class="order_img">
									<img src="/content/mobile/images/common/cart_noimage.jpg" alt="제품이미지">
								</div>
								<div class="order_name">
									<p class="tit">[자연의벗] 첫 눈의 화사함 파우더 팩트 2호 (베이지,15g)</p>
								</div>
								<div class="order_price">
									<p><span>21,254,000</span>원</p>
								</div>
							</div>
						</li>
						<li>
							<div class="item">
								<div class="order_img">
									<img src="/content/mobile/images/common/cart_noimage.jpg" alt="제품이미지">
								</div>
								<div class="order_name">
									<p class="tit">[자연의벗] 첫 눈의 화사함 파우더 팩트 2호 (베이지,15g)</p>
								</div>
								<div class="order_price">
									<p><span>21,254,000</span>원</p>
								</div>
							</div>
						</li>
					</ul>
					<div class="load_more"><button type="button" class="btn_st2"><span>더보기</span></button></div>
				</div>
				<!-- //active_con -->
				
			</div>
			<!-- //pop_coupon_list -->
			
		</div>
		<!-- //pop_con -->

	</div>
	<!-- 내용 : e -->


<page:javascript>
<script type="text/javascript">

 $(function(){
	// 이전페이지로 돌아왔을 때 More Data 유지.
	Mobile.pagination.init('point');
	
	showHideMoreButton();
})

var currentPage = 1;
function paginationMore(key) {
	currentPage++;
	$.post('/m/mypage/point/' + currentPage, 'page=' + currentPage, function(html) {
		$("#list-data").append(html);
		
		showHideMoreButton();
		
		Mobile.pagination.set(key);
	});
}

function showHideMoreButton() {
	if ($("#list-data").find(' > div').size() == Number('${pagination.totalItems}')) {
		$('#list-more').hide();
	}
}



$(function (){
	Common.DateButtonEvent.set('.day_btns a[class^=btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
	dateEvent();
});



//이벤트 change (select 작동되면 a태그 작동 )
function dateEvent() {
	$("#pointdate").on("change", function(){
		var mode = $(this).find("option:selected").val();
		$("." + mode).trigger("click");
	});
}

function setDefaultAddr() {
/* 	if (!confirm("해당 배송지를 기본 배송지로 설정 하시겠습니까?")) { 
		return;
	}  */
	
	location.href = '/m/delivery/list-action/mod?target=/m/mypage/delivery&userDeliveryId='+id;
}



function openDaumPostcode() {
	var tagNames = {
		'newZipcode'			: 'newZipcode',
		'zipcode' 				: 'zipcode',
		'zipcode1' 				: 'zipcode1',
		'zipcode2' 				: 'zipcode2',
	}
	
	openDaumAddress(tagNames);
} 


</script>
</page:javascript>