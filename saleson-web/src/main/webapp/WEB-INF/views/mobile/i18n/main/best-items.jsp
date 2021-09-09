<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<c:if test="${breadcrumbs.size() > 0}">
	<div class="goods_product">
		<div class="title">
			<h3>카테고리 랭킹<span></span></h3>
		</div>
		<div class="goods_tab">
			<div class="inner">
				<ul>
					<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="i">
						<li ${i.count == 1 ? 'class="on"' : ''} data-url="${breadcrumb.groupUrl}">
							<a href="javascript:;" id="${breadcrumb.groupUrl}"><span>${breadcrumb.groupName}</span></a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="goods_con">
			<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="i">
				<div class="goods_list" data-url="${breadcrumb.groupUrl}">
					<p class="${breadcrumb.groupUrl} groupp"></p>
					<ul>
						<c:forEach items="${displayItemMap[breadcrumb.groupUrl]}" var="item" varStatus="k">
							<li${item.itemSoldOut ? ' class="sold-out"' : ''}>
								<a href="${item.link}">
									<div class="img">
										<img src="${shop:loadImageBySrc(item.imageSrc, 'L')}" alt="${item.itemName}">
									</div>
									<p class="tit">${item.itemName}</p>
									<div class="price_box">
										<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
											<p class="percentage"><span>${item.discountRate}</span>%</p>
										</c:if>
										<div class="price">
											<c:if test="${item.totalDiscountAmount > 0 && item.discountRate > 0}">
												<p class="per_price"><span>${op:numberFormat(item.listPrice)}</span>원</p>
											</c:if>
											<p class="sale_price"><span>${op:numberFormat(item.exceptUserDiscountPresentPrice)}</span>원</p>
										</div>
									</div>
									<%--<span class="icon_label"><img src="/content/images/icon/label_08.png" alt="label_08"></span>--%>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div><!--// goods_list -->
			</c:forEach>
		</div><!--// goods_con -->
	</div><!--// goods_product -->
	

<!-- 탭메뉴 클릭시 하단 이동 및 각 컨텐츠별 배너 생성 // st -->
<script>
	$(function() {
	  $("p.price_pc").prepend("<a href='/m/products/view/G2000002353'><img src='/content/mobile/images/common/1_price.jpg'></a>");
	  $("p.BRD").prepend("<a href='/m/products/view/G2000002502'><img src='/content/mobile/images/common/7_brand.jpg'></a>");
	  $("p.needs_pc").prepend("<a href='/m/products/view/G2000002374'><img src='/content/mobile/images/common/2_use.jpg'></a>");
	  $("p.laptop_aio_pc").prepend("<a href='/m/products/view/G2000002395'><img src='/content/mobile/images/common/3_laptop.jpg'></a>");
	  $("p.MonitorAll").prepend("<a href='/m/products/view/G2000004519'><img src='/content/mobile/images/common/4_monitor.jpg'></a>");
	  $("p.appliance").prepend("<a href='/m/products/view/G2000002559'><img src='/content/mobile/images/common/5_life.jpg'></a>");
	});
</script>
<style>
	p.groupp{padding: 60px 0 20px 0;}
	.goods_product .goods_tab{margin-bottom: -35px;}
</style>
<script>
$(document).ready(function(){
	$('#price_pc').click(function(){
		var offset = $('.price_pc').offset();
		$('html').animate({scrollTop : offset.top}, 400);
	});
	$('#BRD').click(function(){
		var offset = $('.BRD').offset();
		$('html').animate({scrollTop : offset.top}, 400);
	});
	$('#needs_pc').click(function(){
		var offset = $('.needs_pc').offset();
		$('html').animate({scrollTop : offset.top}, 400);
	});
	$('#laptop_aio_pc').click(function(){
		var offset = $('.laptop_aio_pc').offset();
		$('html').animate({scrollTop : offset.top}, 400);
	});
	$('#MonitorAll').click(function(){
		var offset = $('.MonitorAll').offset();
		$('html').animate({scrollTop : offset.top}, 400);
	});
	$('#appliance').click(function(){
		var offset = $('.appliance').offset();
		$('html').animate({scrollTop : offset.top}, 400);
	});
});
</script><!-- 탭메뉴 클릭시 하단 이동 및 각 컨텐츠별 배너 생성 // end -->
</c:if>