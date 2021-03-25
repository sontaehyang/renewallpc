<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<div id="sub_contents_min">
	<c:set var="teamName"></c:set>
	<c:forEach items="${shopContext.gnbCategories}" var="team">
		<c:if test="${team.url == categoryCode}">
			<c:set var="teamName">${team.name}</c:set>
		</c:if>
	</c:forEach>

<!--대그룹 타이틀-->
	<div class="subMain_title">
		<img src="/content/images/common/${categoryTeamCode}_title.gif" alt="hair" /> 
	</div><!--//subMain_title-->
	
	<div class="breadcrumbs">
		<a href="/categories/index/${categoryTeamCode}" class="home">${teamName}用品通販HOME</a>
	</div>
	
	<c:set var="i">1</c:set>
	<div class="main_banner">
		<c:forEach items="${promotionList}" var="list">
			<c:if test="${i == 1 && list.editImage != ''  }">
				<a href="${list.editUrl }"><img src="/upload/category-edit/${list.code}/defult/${list.editImage}" alt="${list.editContent}" width="670" height="320" /></a>
				<c:set var="i">2</c:set>
			</c:if>
		</c:forEach>
	</div><!--//main_banner E-->
	
	<ul class="min-banner">
		<c:forEach items="${promotionList}" var="list" varStatus="i">
			<c:if test="${list.editImage != ''}">
				<li><a href="${list.editUrl}"><img src="/upload/category-edit/${list.code}/thumb/${list.editImage}" alt="${list.editContent}" width="167" height="80" /></a></li>
			</c:if>
		</c:forEach>
	</ul> 
	
	<c:if test="${categoryTeamCode != 'sale_outlets'}">
		<c:set var="i">1</c:set>
		<h4>${teamName}の新着情報 <span><a href="/board/index/${categoryTeamCode}">${teamName}の新着情報一覧</a></span></h4>
		<div class="tab_info">					 	 
		    <div class="tab_container">
		        <div class="tab_content news" id="synthesis" style="display: block;">
		            <ul>
			            <c:forEach items="${noticeList}" var="list">
			           		<c:if test="${i <= 5 && fn:indexOf(list.categoryTeam, categoryTeamCode) > -1 }">
							 	<c:set var="target"></c:set>
		            			<c:set var="rel"></c:set>
	            			
	            				<c:if test="${list.targetOption == 'Y' }">
	            					<c:set var="target">target="_blank"</c:set>	
	            				</c:if>
	            				<c:if test="${list.relOption == 'Y' }">
	            					<c:set var="rel">rel="nofollow"</c:set>	
	            				</c:if>
							 	
							 	<li>
							 		<span>${op:date(list.createdDate) }</span>
							 		<c:forEach items="${subCategoryCode}" var="list2">
						 				<c:if test="${list2.key.id == list.subCategory }">
						 					<img src="/content/images/btn/images/btn_notice_${list2.key.id}.gif" alt="${list2.label}" />
						 				</c:if>
						 			</c:forEach>
							 		<a href="${list.url}" ${target} ${rel}>${list.subject }</a>
							 	</li>
							 	<c:set var="i">${i+1}</c:set>
						 	</c:if>
						 </c:forEach>
				 	</ul>
		        </div>
		    </div><!--//tab_container E-->
		</div> <!--//tab_info E-->
	</c:if>
	
	
	<!-- 신상품 시작-->
	<h4>新着商品<span><a href="/products/searchResult?cate00team=${categoryTeamCode}">新着商品情報一覧</a></span></h4>		
	<div class="selling_rating" id="selling_rating1">
		<div class="rolling_arrow">
			<p class="arrow-prev"><a href="#"><img src="/content/images/main/newItem_prev.gif" alt="prev" /></a></p>
			<p class="arrow-next"><a href="#"><img src="/content/images/main/newItem_next.gif" alt="next" /></a></p>
		</div>
		<div class="items" id="newItem1">
			<ul>
				<c:forEach items="${newItemList}" var="list" varStatus="i">
					 <li> 
					 	<span style="position: absolute; top: 10px;"><img src="/content/images/common/item-new.png" alt="new" /></span>
				 		<div><a href="${list.link}" ${list.noFollow}><img src="${list.imageSrc}" class="item_list_image_min" alt="${list.itemName}" /></a></div>
				 		<p><a href="${list.link}" ${list.noFollow} style="display:block;">${list.itemName}</a></p>
				 	</li>
				</c:forEach>
									                 					                
			</ul>
		</div>
	</div>
	
	<c:if test="${!empty categoryEdit.event}">
		<div class="free-space-content">
			${categoryEdit.event}
		</div>
	</c:if>
	
	<c:if test="${!empty categoryEdit.recommend}">
		<div class="free-space-content">
			${categoryEdit.recommend}
		</div>
	</c:if>
	
	<!--// 최근본 상품 시작-->
	<c:if test="${!empty shopContext.todayItems}">
	 	<h4>${op:message('M00854')}</h4>
		<div class="selling_rating">
			<ul>
				<c:forEach items="${shopContext.todayItems}" var="list" varStatus="i">
					<c:if test="${i.count <= 4}">
				    	<li>
				        	<a href="${list.link}" ${list.noFollow}><img src="${list.imageSrc}" class="item_list_image_min" alt="${list.itemName}" /></a>	
				         	<p><a href="${list.link}" ${list.noFollow}>${list.itemName}</a></p>					         	
				     	</li>
			     	</c:if>
				</c:forEach>
			 </ul>
		</div><!--//selling_rating E-->
	</c:if>
 	<!--// 최근본 상품 끝-->
	
	
	<!-- <div id="carousel" class="new_item">
		<ul>
		    <li>
		 		<div><a href="#"><img src="/content/images/main/newItem_img01.gif" alt="newItem"></a></div>
		 		<p>＜エトゥベラ＞ネイルテーブ ル 全2色</p>
		 	</li>
		 	<li>
		 		<div><a href="#"><img src="/content/images/main/newItem_img02.gif" alt="newItem"></a></div>
		 		<p>十分な毛の長さと人毛100％ のカットウィッグ</p>
		 	</li>
		 	<li>
		 		<div><a href="#"><img src="/content/images/main/newItem_img03.gif" alt="newItem"></a></div>
		 		<p>うっとり香るふっくら手肌へ</p>
		 	</li>
		 	<li>
		 		<div><a href="#"><img src="/content/images/main/newItem_img04.gif" alt="newItem"></a></div>
		 		<p>耳つぼパワージュエリー ト ルコ石8個入 - 23253</p>
		 	</li> 
		 	<li>
		 		<div><a href="#"><img src="/content/images/main/newItem_img04.gif" alt="newItem"></a></div>
		 		<p>耳つぼパワージュエリー ト ルコ石8個入 - 23253</p>
		 	</li>
		 	<li>
		 		<div><a href="#"><img src="/content/images/main/newItem_img04.gif" alt="newItem"></a></div>
		 		<p>耳つぼパワージュエリー ト ルコ石8個入 - 23253</p>
		 	</li>                                                
		</ul>  
		<div class="rolling_arrow">
			<p class="arrow-prev"><a href="#" ><img src="/content/images/main/newItem_prev.gif" alt="next" /></a></P>
			<p class="arrow-next"><a href="#"><img src="/content/images/main/newItem_next.gif" alt="prev" /></a></P>
		</div>	
	</div -->
	<!--// 신상품 끝-->
		
		<!-- 히트상품랭킹1 시작 -->
		<h4>${teamName}売れ筋ランキング<span><a href="/ranking/cate00team_${categoryTeamCode}">もっと見る</a></span></h4>
		<div class="selling_ranking"> 
			<div class="ranking_content sub">
				<ul>
					<c:forEach items="${rankingList}" var="list" varStatus="i">
						<c:if test="${i.count <= 4}">
							<li>
								<span><img src="/content/images/main/hit_icon_${i.count }.png" alt=""></span>
								<a href="${list.link}" ${list.noFollow}><img src="${list.imageSrc}" class="item_list_image_min" alt="${list.itemName}" /></a>						                		
								<p><a href="${list.link}" ${list.noFollow}>${list.itemName}</a></p>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>				  
		</div> <!--//selling_ranking E-->
	<!--// 히트상품랭킹1 끝 -->
	
	<!-- 리뷰 시작 -->
	<h4>${teamName}新着レビュー<span><a href="/product_reviews/recent">${teamName}レビュー一覧</a></span></h4>
	<div class="selling_rating">
		<ul style="position: relative;">
			<c:forEach items="${reviewList}" var="itemReview" varStatus="i">
		    	<li>
		        	<a href="${itemReview.item.link}" ${itemReview.item.noFollow}><img src="${itemReview.item.imageSrc}" class="item_list_image_min" alt="${itemReview.item.itemName}" /></a>	
		        	<div class="star_rating">
		        		<span style="width:${itemReview.score * 20}%"></span><span class="point">${itemReview.score}</span>
			 			<!-- <span style="width:70%"></span><span class="point">5</span> -->
			 		</div>					                		
		         	<p><a href="${itemReview.item.link}" ${itemReview.item.noFollow}>${itemReview.item.itemName}</a></p>					         	
		     	</li>
			</c:forEach> 
		 </ul>
	</div><!--//selling_rating E-->
	<!--// 히트상품랭킹2 끝 -->
	
	<c:if test="${!empty categoryEdit.footer1}">
		<div class="free-space-content">
			${categoryEdit.footer1}
		</div>
	</c:if>
	
	
	
	<!-- 추천상품 시작-->
	<c:if test="${!empty itemList}">
		<h4>${teamName}のおすすめ商品</h4>		
		<div class="selling_rating" id="selling_rating2">
			<div class="rolling_arrow">
				<p class="arrow-prev"><a href="#"><img src="/content/images/main/newItem_prev.gif" alt="prev" /></a></p>
				<p class="arrow-next"><a href="#"><img src="/content/images/main/newItem_next.gif" alt="next" /></a></p>
			</div>
			<div class="items" id="newItem2">
				<ul>
					<c:forEach items="${itemList}" var="list" varStatus="i">
						 <li>
					 		<div><a href="${list.link}" ${list.noFollow}><img src="${list.imageSrc}" class="item_list_image_min" alt="${list.itemName}" /></a></div>
					 		<p><a href="${list.link}" ${list.noFollow}>${list.itemName}</a></p>
					 	</li>
					</c:forEach>
										                 					                
				</ul>
			</div>
		</div>
	</c:if>
	
</div>

<page:javascript>	
<script type="text/javascript">
	$(function(){
		
		Gnb.active('${categoryTeamCode}');
		
		$(".min-banner > li").on("focusin, mouseenter",function(){
			$(".main_banner > a > img").attr("src",$(this).find("img").attr("src").replace("thumb","defult"));
			
			var href = $(this).find("a").attr('href');
			if (href == '') {
				href = 'javascript:;';
			}
			
			$('.main_banner > a').attr('href', href);
		});
		
		setReviewItemEvent();
		setReviewItemEvent2();
		mainBannerRolling();
		
	});
	

	function setReviewItemEvent() {
		
		var $newItem = $('#selling_rating1  #newItem1  ul');
		var itemCount = $newItem.find('li').size();
		var currentPage = 1;
		var itemsPerPage = 5;
		var itemWidth = 154;
		
		$newItem.find('li').css("margin","0 10px");
	    var totalPages = Math.ceil(itemCount / itemsPerPage);
	    
	    $newItem.width(itemWidth * itemsPerPage * totalPages);
	    
	    $('#selling_rating1 > .rolling_arrow > .arrow-prev > a').on('click', function(e) {
	    	
	    	e.preventDefault();
	    	
	    	if (totalPages > 1) {
	    		if (currentPage == 1) {
	    			//currentPage = totalPages;
	    		} else {
	    			currentPage--;
	    			
	    			var topSize = (currentPage - 1) * itemWidth * totalPages;
	        		$newItem.animate({'left': '-' + topSize + 'px'}, 'fast');
	    		}
	    	} 
	    });
	    
	    $('#selling_rating1 > .rolling_arrow > .arrow-next > a').on('click', function(e) {
	    	e.preventDefault();
	    	
	    	if (totalPages > 1) {
	    		if (currentPage == itemsPerPage) {
	    			//currentPage = 1;
	    			
	    		} else {
	    			currentPage++;
	    			
	    			var topSize = (currentPage - 1) * itemWidth * totalPages;
	        		$newItem.animate({'left': '-' + topSize + 'px'}, 'fast');
	    		}
	    	} 
	    });
	}
	
	
	function setReviewItemEvent2() {
		
		var $newItem2 = $('#selling_rating2 #newItem2 ul');
		var itemCount2 = $newItem2.find('li').size();
		var currentPage2 = 1;
		var itemsPerPage2 = 5;
		var itemWidth2 = 154;
		
		$newItem2.find('li').css("margin","0 10px");
	    var totalPages2 = Math.ceil(itemCount2 / itemsPerPage2);
	    
	    $newItem2.width(itemWidth2 * itemsPerPage2 * totalPages2);
	    
	    $('#selling_rating2 > .rolling_arrow > .arrow-prev > a').on('click', function(e) {
	    	
	    	e.preventDefault();
	    	
	    	if (totalPages2 > 1) {
	    		if (currentPage2 == 1) {
	    			//currentPage = totalPages;
	    		} else {
	    			currentPage2--;
	    			
	    			var topSize = (currentPage2 - 1) * itemWidth2 * totalPages2;
	        		$newItem2.animate({'left': '-' + topSize + 'px'}, 'fast');
	    		}
	    	} 
	    });
	    
	    $('#selling_rating2 > .rolling_arrow > .arrow-next > a').on('click', function(e) {
	    	e.preventDefault();
	    	
	    	if (totalPages2 > 1) {
	    		if (currentPage2 == itemsPerPage2) {
	    			//currentPage = 1;
	    			
	    		} else {
	    			currentPage2++;
	    			
	    			var topSize = (currentPage2 - 1) * itemWidth2 * totalPages2;
	        		$newItem2.animate({'left': '-' + topSize + 'px'}, 'fast');
	    		}
	    	} 
	    });
	}
	

	function mainBannerRolling(){
		
		var index = -1;
		
		var imagesRolling = setInterval(function(){
	    	if (3 > -1) {
	    		if (index == 3) {
	    			index = -1;
	    			
	    		} else {
	    			index++;
	    			$(".main_banner > a > img").attr("src",$(".min-banner > li").eq(index).find("img").attr("src").replace("thumb","defult"));
	    			$(".main_banner > a > img").attr("alt", $(".min-banner > li").eq(index).find("img").attr("alt"));
	    			
	    			var href = $('.min-banner > li').eq(index).find("a").attr('href');
	    			if (href == '') {
	    				href = 'javascript:;';
	    			}
	    			
	    			$('.main_banner > a').attr('href', href);
	    		}
	    	} 
		}, 4000);
		
		$(".min-banner > li").on("focusin, mouseenter",function(){
			
			clearInterval(imagesRolling);
			index = $(".min-banner > li").index(this);
			$(".main_banner > a > img").attr("src",$(".min-banner > li").eq(index).find("img").attr("src").replace("thumb","defult"));
			$(".main_banner > a > img").attr("alt", $(".min-banner > li").eq(index).find("img").attr("alt"));
			
			var href = $('.min-banner > li').eq(index).find("a").attr('href');
			if (href == '') {
				href = 'javascript:;';
			}
			
			$('.main_banner > a').attr('href', href);
			
			
		}).on('mouseleave focusOut',function(){
			
			imagesRolling = setInterval(function(){
		    	if (3 > -1) {
		    		if (index == 3) {
		    			index = -1;
		    			
		    		} else {
		    			index++;
		    			$(".main_banner > a > img").attr("src",$(".min-banner > li").eq(index).find("img").attr("src").replace("thumb","defult"));
		    			$(".main_banner > a > img").attr("alt", $(".min-banner > li").eq(index).find("img").attr("alt"));
		    			$('.main_banner > a').attr('href', $('.min-banner > li').eq(index).find("a").attr('href'));
		    		}
		    	} 
	    	}, 4000);
			
		});
		
	}
	
</script>
</page:javascript>
