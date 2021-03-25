<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>



<c:set var="imageCount" value="0" />
<c:forEach items="${item.itemImages}" var="itemImage" varStatus="i">
	<c:if test="${itemImage.itemImageId != 0}">
		<c:set var="imageCount">${imageCount + 1}</c:set>
	</c:if>
</c:forEach>

<!-- 내용 : s -->
<div class="con">
	<div class="pop_title">
		<h3>이미지 상세보기</h3>
		<a href="javascript:history.back();" class="history_back">뒤로가기</a>
	</div>
	<!-- //pop_title -->
	<div class="pop_con">
		<div class="view_detail_tit">
			<p>${item.itemName}</p>
		</div>
		<!-- //view_detail_tit -->
		
		<div class="view_detail_con">
			<div class="view_detail_slider">
				<div class="swiper-wrapper">
					<c:forEach items="${item.itemImages}" var="itemImage" varStatus="i">
						<c:if test="${itemImage.itemImageId != 0}">
							<%-- <img src="${itemImage.bigImageSrc}" id="big_image"</a> --%>
							<div class="swiper-slide">
								<img src="${itemImage.bigImageSrc}" id="big_image" alt="img${i.index}">
							</div>
							<c:set var="imageIndex">${imageIndex + 1 }</c:set>
						</c:if>													
					</c:forEach>
				</div>
			</div>
			<!--//view_detail_slider -->

			<p class="img_number">
				<span class="active">0</span>/<span class="total">0</span>
			</p>
			<!--//img_number -->
			
			<div class="view_thumb_slider">
				<div class="swiper-wrapper">
				<c:set var="imageIndex" value="0" />
					<c:forEach items="${item.itemImages}" var="itemImage" varStatus="i">
					
						<div class="swiper-slide">
							<a href="#"><img src="${itemImage.imageSrc}" alt="thumb${i.index}"></a>
						</div>
												
					</c:forEach>
				</div>
			</div>
			<!--//view_thumb_slider -->

			<div class="desc">
				<p class="line"><span class="arr_left"></span><span class="arr_right"></span></p>
				<p class="txt">슬라이드 하시면 다른 사진을 볼 수 있습니다.</p>
			</div>
			<!--//desc -->
		</div>
		<!-- //view_detail_con -->
		<div class="btn_wrap">
			<a href="javascript:history.back();" class="btn_st1 b_gray">닫기</a>
		</div>
		<!-- //btn_wrap -->
		
	</div>
	<!-- //pop_con -->

</div>
<!-- 내용 : e -->
