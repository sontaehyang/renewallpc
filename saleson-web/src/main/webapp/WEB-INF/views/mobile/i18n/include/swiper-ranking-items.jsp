<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<!-- 히트상품랭킹 -->
				<div id="ranking_items" class="swiper hit_item ">
					<h3>히트 상품 랭킹 <span><a href="/m/ranking/cate00team_esthetic"><img src="/content/mobile/images/common/btn_more.png" alt="more"></a></span></h3>
					<div class="swiper-container item_wrap">
						<ul class="swiper-wrapper item" style="height: 194px;">
						
						<c:forEach items="${shopContext.lnbCategories}" var="team" varStatus="k">
							<c:if test="${team.url == 'esthetic'}">
								<c:forEach items="${rankingItemsByTeam[team.url]}" var="item" varStatus="i">
									<li class="swiper-slide">
										<a href="${item.link}">
											<span class="label"><img src="/content/mobile/images/common/best_0${i.count}.png" height="40" alt="best 1"></span>
											<p class='photo'><img src="${item.imageSrc}" width="130" height="130" alt="item"></p>
											<p class="name">${item.itemName}</p>	
											<div class="price_zone">
						 						<span class="item_price">${op:numberFormat(item.itemPrice)}</span>
												<span class="sale_price">${item.price}</span>
						 					</div> 
										</a>
									</li>
								</c:forEach>
							</c:if>
						</c:forEach>
						
						</ul> 
					</div><!--// item_wrap E-->	
					<div class="paging"></div>
					<div class="btn_area">
						<a class="btn_prev" href="#"><img alt="" src="/content/mobile/images/common/btn_item_prev.png"></a>
						<a class="btn_next" href="#"><img alt="" src="/content/mobile/images/common/btn_item_next.png"></a> 
					</div>
				</div><!--//hit_item E-->

<page:javascript>				
<script type="text/javascript">
$(function(){
	
	Mobile.swiper({'id': '#ranking_items', "slidesPerView" : 2});
	
});
</script>
</page:javascript>