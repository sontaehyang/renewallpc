<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>	
	
	
	<div class="order">
		<c:set var="gnbType" value="activity" scope="request" />
		<jsp:include page="../include/mypage-header-gnb.jsp"></jsp:include>
		
		<ul class="wish_menu">
			<li><a href="/m/mypage/wishlist">관심상품</a></li>
			<li><a href="/m/mypage/review?recommendFlag=N" class="on">이용후기</a></li>
			<li><a href="">상품Q&amp;A</a></li> 
			<li><a href="">1:1문의</a></li> 
		</ul> 
		
		<div class="review_top"> 
			<div class="product_img">
				<img src="${itemReview.item.itemImage}" alt="제품이미지">
			</div>
			<div class="cont"> 
				<p class="name">${itemReview.item.itemName}</p>
				<div class="price_zone">
						<span class="item_price">${itemReview.item.itemPrice}원</span>
					<span class="sale_price">${itemReview.item.salePrice}원</span>
					</div> 
			</div> 
		</div><!--//review_top E-->
		
		<div class="review_view"> 
			<p class="title">${itemReview.subject}</p>
			<div>
				${itemReview.content}
			</div>
			<span class="txt">평가</span>
			<div class="star_rating op-star-rating">
	 			<span style="width:${itemReview.score * 20}%"></span><span class="point">${itemReview.score}</span>
	 		</div>	  
		</div><!--//review_cont E-->
		
	</div>	<!--//order E-->
	
	<div class="btn_area wrap_btn">
		<div style="display:block" class="sale division-3">
			<c:if test="${itemReview.recommendFlag == 'N'}">
				<div>
					<div>
						<a class="btn btn_on btn-w100" href="/m/mypage/review-edit/${itemReview.itemReviewId}">수정</a>
					</div>
				</div>
				<div>
					<div>
						<a class="btn btn_out btn-w100" href="/m/mypage/review-delete/${itemReview.itemReviewId}">삭제</a>
					</div>
				</div>
			</c:if>
			<div>
				<div>
					<a class="btn btn_out btn-w100" href="/m/mypage/review?recommendFlag=${itemReview.recommendFlag}">목록</a>
				</div>
			</div>
		</div>				 
	</div>	 