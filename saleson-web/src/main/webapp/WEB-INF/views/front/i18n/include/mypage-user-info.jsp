<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<!-- 주문/배송조회 정보 박스 -->	
<div class="order_info">
	<div class="member_level">
		<p class="level"><%-- <img src="/content/image/icon/${userLevel.fileName}" alt="등급"> --%></p>
		<p class="name"><span>${requestContext.user.userName}</span>님</p>
		<p class="rating">${userLevel.levelName}등급</p>
		<a href="javascript:Common.popup('/pages/rating-info', 'grade', 735, 680);"  class="btn_benefit" title="혜택보기">혜택보기</a>
	</div><!--// member_level E-->
	<div class="info_list">
		<ul class="info">
			<li>
				<p class="item point">${op:message('M00246')}</p>
				<p class="remainder"><span>${op:numberFormat(userPoint)}</span>P</p>
			</li>
			<li>
				<p class="item coupons">쿠폰 <a href="javascript:Common.popup('/mypage/popup/coupon-download', 'point-create', 1000, 550);" class="btn_down_confirm">다운가능쿠폰</a></p>
				<p class="remainder"><span>${op:numberFormat(userCouponCount)}</span>개</p>
			</li>
			<li>
				<p class="item delivery">배송비 쿠폰</p>
				<p class="remainder"><span>${op:numberFormat(userShippingCount)}</span>개</p>
			</li>
			<li>
				<p class="item cash">캐시</p>
				<p class="remainder"><span>${op:numberFormat(userEmoney)}</span>원</p>
			</li>
		</ul> 
		<div class="condition_wrap">
			<div class="order_condition">
				<span class="title">주문/배송</span>
				<ol>
					<li>
						<span  class="icon"><img src="/content/images/icon/icon_oreder_step01.gif" alt="order"></span>
						<p>주문접수 <span id="waiting-deposit-count"><a href="javascript:searchOrder('waiting-deposit')">0</a>건</span></p> 
					</li>
					<li class="btn_order_next"> </li>
					<li>
						<span  class="icon"><img src="/content/images/icon/icon_oreder_step02.gif" alt="order"></span>
						<p>결제완료  <span id="new-order-count"><a href="javascript:searchOrder('new-order')">0</a>건</span></p>  
					</li>
					<li class="btn_order_next"></li>
					<li>
						<span  class="icon"><img src="/content/images/icon/icon_oreder_step03.gif" alt="order"></span>
						<p>상품준비중 <span id="shipping-ready-count"><a href="javascript:searchOrder('shipping-ready')">0</a>건</span></p>  
					</li>
					<li class="btn_order_next"></li>
					<li>
						<span  class="icon"><img src="/content/images/icon/icon_oreder_step04.gif" alt="order"></span>
						<p>배송중 <span id="shipping-count"><a href="javascript:searchOrder('shipping')" class="ing">0</a>건</span></p> 
					</li>
					<li class="btn_order_next"></li>
					<li>
						<span  class="icon"><img src="/content/images/icon/icon_oreder_step05.gif" alt="order"></span>
						<p>구매확정 <span id="confirm-count"><a href="javascript:searchOrder('confirm')">0</a>건</span></p> 
					</li>
				</ol>
			</div> <!--// order_condition E--> 
			<div class="order_refund">
				<p class="title">취소/교환/반품</p>
				<ul>
					<li>취소 <span id="cancel-process-count"><a href="javascript:searchOrder('cancel-process')">0</a>건</span></li>
					<li>교환 <span id="exchange-process-count"><a href="javascript:searchOrder('exchange-process')" class="ing">0</a>건</span></li>
					<li>반품 <span id="return-process-count"><a href="javascript:searchOrder('return-process')">0</a>건</span></li>
				</ul>
			</div><!--// order_refund E--> 
		</div><!--// condition_wrap E--> 
	</div><!--// info_list E-->
</div><!--// order_info E-->


<page:javascript>
<script type="text/javascript">

$(function(){
	setOrderCount();
})

//주문내역 Count 
function setOrderCount() {
	
	var complainCount = 0;
	$.post('/common/user/order-count', null, function(resp){
		
		if (resp.data == undefined) {
			return;
		}
		
		$.each(resp.data, function(i, state) {
			
			$object = $('span#' + state.key + "-count");
			if ($object.size() > 0) {
				$object.find('a').html(Common.numberFormat(state.count));
			}
			
		});
		
	}, 'json'); 
	
}

function searchOrder(type) {
	
	if ($('span#' + type + "-count").find('a').size() == 0) {
		return;
	}
	
	var count = Number($('span#' + type + "-count").find('a').html());
	if (count > 0) {
		location.href = '/mypage/order?statusType=' + type;
	}
}
</script>
</page:javascript>