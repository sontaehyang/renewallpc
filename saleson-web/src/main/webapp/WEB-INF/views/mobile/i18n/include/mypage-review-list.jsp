<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<c:forEach items="${reviewList}" var="list" varStatus="i">	
	<li>
		<div class="active_title">
			<c:if test="${reviewPageType == 'nonReview'}">
				<div class="active_title">
					<p class="review_num">${list.orderCode}</p>
					<p class="review_date">${op:date(list.createdDate)}</p>
				</div>
			</c:if>
			<c:if test="${reviewPageType == 'review'}">
				<p class="txt"><span class="label reply_${list.displayFlag == 'N' ? 'stand' : 'comp'}">${list.displayFlag == 'N' ? '승인대기' : '승인완료'}</span><span class="date">${op:date(list.createdDate)}</span></p>
				<p class="star_rating op-star-rating">
					${list.starScore}
				</p>
			</c:if>
		</div>
		<div class="active_product">
			<div class="active_img">
				<c:if test="${reviewPageType == 'nonReview'}">
					<%-- <img src="${list.imageSrc}" alt="제품이미지"> --%>
					<a href="/m/products/view/${list.itemUserCode}"><img src="${shop:loadImageBySrc(list.imageSrc, 'XS')}" alt="제품이미지"></a>
				</c:if>
				<c:if test="${reviewPageType == 'review'}">
					<%-- <img src="${list.item.imageSrc}" alt="제품이미지"> --%>
					<a href="${list.item.link}"><img src="${shop:loadImageBySrc(list.item.imageSrc, 'XS')}" alt="제품이미지"></a>
				</c:if>
			</div>
			<div class="active_name op-mypage-review">
				<c:if test="${reviewPageType == 'nonReview'}">
					<p class="tit"><a href="/m/products/view/${list.itemUserCode}">${list.itemName}</a></p>
					<a href="/m/item/create-review/${list.orderCode}/${list.itemCode}" class="btn_st3 t_small t_lgray b_white s_small review_write" onclick="location.href='/m/item/create-review/${list.orderCode }/${list.itemCode}?target=/m/mypage/review'">이용후기 작성</a>
				</c:if>
				<c:if test="${reviewPageType == 'review'}">
					<p class="tit"><a href="${list.item.link}">${list.item.itemName}</a></p>
					<p class="q_tit">${list.subject}</p>
				</c:if>
			</div>
		</div>
		<c:if test="${reviewPageType == 'review'}">
			<div class="q_con op-mypage-review-detail" style="display:none;">
				<p class="t_medium">
					${list.content}
					<c:forEach items="${list.itemReviewImages}" var="image" varStatus="i">
						<c:if test="${image.itemReviewImageId > 0}">
							<c:if test="${i.count == 1}">
								<br/><br/>
							</c:if>
							<img src="${image.imageSrc}" />
						</c:if>
					</c:forEach>
				</p>
				<div class="btn_wrap">
					<c:if test="${list.displayFlag == 'N'}">
						<!-- <a href="#" class="btn_st3 t_lgray s_small b_white">수정</a> -->
						<a href="#" class="btn_st3 t_lgray s_small b_white" onclick="deleteCheck('${list.itemReviewId}');">삭제</a>
					</c:if>
				</div>
			</div>
		</c:if>	
	</li>
</c:forEach>

