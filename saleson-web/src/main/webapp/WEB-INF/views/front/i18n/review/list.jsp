<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<div class="content_top">
	<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
		<span>최근 이용후기</span>  
	</div>
</div><!--//content_top E-->

	
<div id="sub_contents">  
	<div class="best_reviews">
		<h2>이용후기</h2>   
		<div class="review_list">
			<div class="reviews" style="overflow:hidden;">
				
				<div class="item_wrap" >
				
					<ul>
						<c:forEach items="${bestReviewsList}" var="list" varStatus="i">
							<li>
		 						<a href="${list.item.link}">
		 							<p><img src="${list.item.imageSrc}" width="192" height="178" alt=""></p>
		 							<p class="name">${list.item.itemName}</p>	
		 							<div class="review_wrap">
		 							<!-- 
										<p class="review">이용후기<span>(${list.score})</span></p>
									 -->
										<div class="star_rating">
								 			<span style="width:${list.score * 20}%"></span><span class="point">${list.score}</span>					 			
								 		</div>	
									</div><!--//review_wrap E-->
									<div class="comment">
										<p>
											${list.subject}
										 </p>	 
									</div><!--//comment E-->
		 						</a>
		 					</li>
							
						</c:forEach>
					</ul>
				</div>
			</div><!--//reviews E-->
			
			<a href="" class="btns review_prev arrow-prev"><img src="/content/images/event/btn_review_prev.png" alt="이전"></a>
			<a href="" class="btns review_next arrow-next"><img src="/content/images/event/btn_review_next.png" alt="다음"></a>

		
		</div>
	</div>
	
		
		<div class="reviews">
			<div class="title">
				<p>
					<c:if test="${empty categories}">
						전체
					</c:if>
					
					<c:if test="${!empty categories}">
						${categories.categoryName} 카테고리 <span class="normal">관련 이용후기 검색결과 :</span>
					</c:if>
					이용후기 총 <span>${op:numberFormat(count)}</span>건
				</p>  
			</div>
	 	
	 		<table cellpadding="0" cellspacing="0" class="esthe-table list">
	 			<caption>table list</caption>
	 			<colgroup>
	 				<col style="width:auto;">
					<col style="width:100px;">
					<col style="width:100px;">
					<col style="width:120px;">
	 			</colgroup>
	 			<thead>
	 				<tr> 
						<th>상품명/이용후기</th>
						<th>평가 </th>
						<th>작성자</th>
						<th>작성일</th>
					</tr>
	 			</thead>
	 			<tbody>
	 				<c:forEach items="${reviewsList}" var="list" varStatus="i">
		 				<tr class="tit-off">
							<td class="tit subject">	
								<p class="photo"><a href="${list.item.link}"><img src="${list.item.imageSrc}" width="60" height="60" alt="" /></a></p>
								<a href="javascript:;" class="review_q" style="text-decoration:none !important">
									<div>
										<p class="name">${list.item.itemName}</p>
										<p>${list.subject}</p>
									</div>
								</a>
							</td>	
							<td class="star">
								<div class="star_rating">
									<span style="width:${list.score * 20}%"></span><span class="point">${list.score}</span>
					 			</div>
							</td>
							<td>${list.userName}</td>
							<td>${op:date(list.createdDate) }</td>			
						</tr>
						<tr class="view-off">
							<td colspan="4" class="question-open ">	 
								<div class="qna-a" style="padding-left: 85px !important;"> 
									<p>${op:nl2br(list.content)}	</p> 
								</div>								
							</td>
						</tr>
	 				</c:forEach>
	 				 
	 			</tbody>
	 		</table><!--//esthe-table E-->
	 		
	 		
	 		
	 		
	 		<c:if test="${empty reviewsList}">
		 		<div class="no_content">등록된 이용후기가 없습니다.</div>
		 	</c:if>
		 	
		 	<c:if test="${!empty reviewsList}">
		 		<page:pagination />
	 		</c:if>
		</div> <!-- // mypage_short E -->
	</div>
	
<page:javascript>	
<script type="text/javascript">
	$(function(){
		
		/*
		$("#categoriesSearchParam").validator(function(){
			var categoryCode = $("select[name='categoryClass1'] option:selected").attr("rel");
			location.href="/product_reviews/recent/"+categoryCode+"?categoryGroupId="+$("select[name='categoryGroupId'] option:selected").val();		
			return false;
		});
		
		ShopEventHandler.categorySelectboxChagneEvent();  
		Shop.activeCategoryClass('${categoriesSearchParam.categoryGroupId}', '${categoriesSearchParam.categoryClass1}', '${categories.categoryClass2}', '${categories.categoryClass3}', '${categories.categoryClass4}');
		*/
		
		$(".review_q").on("click",function(){
			var index = $(".review_q").index(this);
			$(".view-off").not(index).hide();
			$(".view-off").eq(index).show();
		});
		
		var $newItem = $('.item_wrap ul');
		var itemCount = $newItem.find('li').size();
		var currentPage = 1;
		var itemsPerPage = 3;
		var itemWidth = 242;
	    var totalPages = Math.ceil(itemCount / itemsPerPage);
	    
	    $newItem.width(itemWidth * itemsPerPage * totalPages);
	   
	   var bestReviewRolling = setInterval(function(){
	    	if (totalPages > 1) {
	    		if (currentPage == totalPages) {
	    			currentPage = 0;
	    			
	    		} else {
	    			currentPage++;
	    			
	    			var topSize = (currentPage - 1) * itemWidth * itemsPerPage;
	        		$newItem.animate({'left': '-' + topSize + 'px'}, 'fast');
	    		}
	    	} 
    	}, 4000);
	    
    	//mouseenter focusIn
    	
    	$('.item_wrap > ul > li, .arrow-prev, .arrow-next').on('mouseenter focusIn',function(){
    		clearInterval(bestReviewRolling);
		}).on('mouseleave focusOut',function(){
			
			bestReviewRolling = setInterval(function(){
		    	if (totalPages > 1) {
		    		if (currentPage == totalPages) {
		    			currentPage = 0;
		    			
		    		} else {
		    			currentPage++;
		    			
		    			var topSize = (currentPage - 1) * itemWidth * itemsPerPage;
		        		$newItem.animate({'left': '-' + topSize + 'px'}, 'fast');
		    		}
		    	} 
	    	}, 5000);
		});
	  
    	bestReviewRolling;
	    
	    $('.arrow-prev').on('click', function(e) {
	    	e.preventDefault();
	    	//alert(currentPage);
	    	if (totalPages > 1) {
	    		if (currentPage == 1) {
	    			//currentPage = totalPages;
	    		} else {
	    			currentPage--;
	    			
	    			var topSize = (currentPage - 1) * itemWidth * itemsPerPage;
	        		$newItem.animate({'left': '-' + topSize + 'px'}, 'fast');
	    		}
	    	} 
	    });
	    
	    $('.arrow-next').on('click', function(e) {
	    	e.preventDefault();
	    	
	    	if (totalPages > 1) {
	    		if (currentPage == totalPages) {
	    			//currentPage = 0;
	    			
	    		} else {
	    			currentPage++;
	    			
	    			var topSize = (currentPage - 1) * itemWidth * itemsPerPage;
	        		$newItem.animate({'left': '-' + topSize + 'px'}, 'fast');
	    		}
	    	} 
	    	
	    });
	    
	});

	
</script>
</page:javascript>