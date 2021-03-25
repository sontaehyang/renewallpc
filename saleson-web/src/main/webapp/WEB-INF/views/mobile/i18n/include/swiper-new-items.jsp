<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


			<div id="new_items" class="swiper ${swiperType}">
				<p class="swiper-title">${op:message('M01308')}</p> <!-- 신상품 -->
				<div class="swiper-container">
					<ul class="swiper-wrapper">
						<c:forEach items="${newItemList}" var="list" varStatus="i">
							<li class="swiper-slide">
								<a href="/m${list.link}">
									<img src="${list.imageSrc}" alt="${list.itemName}">
									<p class="title">${list.itemName}</p>
								</a>	
								<p class="price">
									<c:if test="${list.nonmemberOrderType != '3'}">
										<span>
											<c:if test="${!empty list.itemPrice}">
					 							定価 ${op:numberFormat(list.itemPrice)}<c:if test="${op:numberFormat(list.itemPrice) != list.itemPrice}">円(${shopContext.config.taxDisplayTypeText})</c:if>
					 						</c:if>
										</span>
										<span>卸価 ${list.price}</span>
									</c:if>
								</p> 
							</li>
						</c:forEach>
					</ul>
				</div>			
				<div class="paging"></div>	
				
				<!-- 버튼 -->
				<a href="#" class="btn_prev"><img src="/content/mobile/images/common/btn_item_prev.png" alt=""></a>
				<a href="#" class="btn_next"><img src="/content/mobile/images/common/btn_item_next.png" alt=""></a>
				<a href="/m/products/result?page=1&limit=40&listType=a&sort=new&field=&code=&newArrival=1&searchCategorySelecter=&searchWord=" class="btn_more">${op:message('M01451')}</a> <!-- 신상품 더보기 -->
			</div>


<page:javascript>			
<script type="text/javascript">
	$(function() {
		Mobile.swiper({'id': '#new_items', "slidesPerView" : 2});
	});
</script>	
</page:javascript>			