<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


		<c:if test="${!empty shopContext.todayItems}">			
			<div id="today_items" class="swiper ${swiperType}">
				<p class="swiper-title">${op:message('M00854')}</p> <!-- 최근 체크한 상품 --> 
				<div class="swiper-container">
					<ul class="swiper-wrapper">
						<c:forEach items="${shopContext.todayItems}" var="item" varStatus="i">
							<c:if test="${i.count <= 10}">
								<li class="swiper-slide">
									<a href="/m${item.link}">
										<img src="${item.imageSrc}" alt="">
										<p class="title">${item.itemName}</p>
									</a>
									
									<p class="price">
										<span>
											<c:if test="${!empty item.itemPrice}">
					 							定価 ${op:numberFormat(item.itemPrice)}<c:if test="${op:numberFormat(item.itemPrice) != item.itemPrice}">円(${shopContext.config.taxDisplayTypeText})</c:if>
					 						</c:if>
										</span>
										<span>卸価 ${item.price}</span>
									</p> 
									
								</li>
					     	</c:if>
						</c:forEach>
					</ul>
				</div>			
				<div class="paging"></div>	
				
				<!-- 버튼 -->
				<a href="#" class="btn_prev"><img src="/content/mobile/images/common/btn_item_prev.png" alt=""></a>
				<a href="#" class="btn_next"><img src="/content/mobile/images/common/btn_item_next.png" alt=""></a>
			</div>


			<page:javascript>			
			<script type="text/javascript">
				$(function() {
					Mobile.swiper({'id': '#today_items', "slidesPerView" : 2});
				});
			</script>
			</page:javascript>
		</c:if>					