<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


	<c:if test="${!empty reviewList}">
		<c:forEach items="${reviewList}" var="itemReview" varStatus="i">
			<li>
				<c:if test="${i.first}">
					<div id="page-${pagination.currentPage}" style="display: none;">
						<div class="total-items">${pagination.totalItems}</div>
						<div class="total-pages">${pagination.totalPages}</div>
					</div>
				</c:if>

				<div class="main_txt">
					<a href="#;">
						<p class="star_rating op-star-rating">${itemReview.starScore}</p>
						<p class="info">
							<span class="date">${op:date(itemReview.createdDate)}</span>
							<span class="id">${itemReview.maskUsername}</span>
						</p>
						<p class="content">${itemReview.subject}</p>
					</a>
				</div>
				<div class="sub_txt">
					${op:nl2br(itemReview.content)}
					<c:forEach items="${itemReview.itemReviewImages}" var="image" varStatus="i">
						<c:if test="${image.itemReviewImageId > 0}">
							<c:if test="${i.count == 1}">
								<br/><br/>
							</c:if>
							<img src="${image.imageSrc}" />
						</c:if>
					</c:forEach>
				</div>
			</li>
		</c:forEach>
	</c:if>

	<c:if test="${empty reviewList}">
		<li>
			<p class="review_none">등록된 이용후기가 없습니다.</p>
		</li>
	</c:if>
