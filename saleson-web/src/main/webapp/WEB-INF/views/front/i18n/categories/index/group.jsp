<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>

<div id="sub_contents_min">
	<%-- 	<div class="main_banner">
			<a href="#"><img src="/content/images/common/sub_banner.png" alt="esthetic skin care"></a>
		</div><!--//main_banner E-->
		
		<!-- 중그룹 리스트 시작-->
		<div class="breadcrumbs">
			<c:forEach items="${shopContext.gnbCategories}" var="team">
				<c:forEach items="${team.groups}" var="group">
					<c:if test="${group.url == categoryCode}">
						<a href="/categories/index/${team.url}" class="home">${team.name}用品通販HOME</a> &gt; 
						<span>${group.name}</span>
					</c:if>
				</c:forEach>
			</c:forEach>
		</div> <!-- // sub_location E-->
		
		<div class="middle_group">
			<c:forEach items="${shopContext.gnbCategories}" var="team">
				<c:forEach items="${team.groups}" var="group">
					<c:if test="${group.url == categoryCode}">
						<c:if test="${!empty group.categories}">
			
							<c:forEach items="${group.categories}" var="category">
								<div class="group_massage">
									<p class="i_massage"><a href="/categories/index/${category.url}">${category.name}</a></p>
										
							
										<c:if test="${!empty category.childCategories}">
											<ul>
												<c:forEach items="${category.childCategories}" var="childCategories">
												
													<li><a href="/categories/index/${childCategories.url}">${childCategories.name}</a>
												</c:forEach>
											</ul>
										</c:if>
								</div>
								
							</c:forEach>
						</c:if>
						
					</c:if>
				</c:forEach>
			</c:forEach>		
		</div> --%>
		
		<div class="main_banner sub_banner">
			${categoryEdit.header}
		</div><!--//main_banner E-->
		
		<c:set var="groupName"></c:set>
		<!-- 중그룹 리스트 시작-->
		<div class="breadcrumbs">
			<c:forEach items="${shopContext.gnbCategories}" var="team">
				<c:forEach items="${team.groups}" var="group">
					<c:if test="${group.url == categoryCode}">
						<a href="/categories/index/${team.url}" class="home">${team.name}用品通販HOME</a> &gt; 
						<span>${group.name}</span>
						<c:set var="groupName">${group.name}</c:set>
					</c:if>
				</c:forEach>
			</c:forEach>
		</div>  <!-- // sub_location E-->
		
		<div class="middle_group">
			<c:forEach items="${shopContext.gnbCategories}" var="team">
				<c:forEach items="${team.groups}" var="group">
					<c:if test="${group.url == categoryCode}">
						<c:if test="${!empty group.categories}">
			
							<c:forEach items="${group.categories}" var="category">
								<div class="code_base_${team.url} code_${category.url}">
									<p class="i_massage"><a href="/categories/index/${category.url}">${category.name}</a></p>
									<%-- <p class="i_down_tit"><img src="/content/images/category_group/${category.url}_tit.png" alt="" /></p> --%>
									<c:if test="${!empty category.childCategories}">
										<ul>
											<c:forEach items="${category.childCategories}" var="childCategories">
											
												<li><a href="/categories/index/${childCategories.url}">${childCategories.name}</a>
											</c:forEach>
										</ul>
									</c:if>
								</div>
								
							</c:forEach>
						</c:if>
						
					</c:if>
				</c:forEach>
			</c:forEach>		
		</div> 
		
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
					<c:if test="${i.count <= 5}">
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
	
 	<!-- 히트상품랭킹1 시작 -->
 	<h4> ${groupName}売れ筋ランキング<span><a href="/ranking/${categoryGroupCode}_${categoryTeamCode}">${groupName}の売れ筋ランキング一覧</a></span></h4>
 	<div class="selling_ranking"> 
		<div class="ranking_content">
			<ul>
				<c:forEach items="${rankingList}" var="list" varStatus="i">
					<li>
						<span><img src="/content/images/main/hit_icon_${i.count }.png" alt=""></span>
						<a href="${list.link}" ${list.noFollow}><img src="${list.imageSrc}" class="item_list_image_min" alt="${list.itemName}" /></a>						                		
						<p><a href="${list.link}" ${list.noFollow}>${list.itemName}</a></p>
					</li>
				</c:forEach>
			</ul>
		</div>	
	</div> <!--//selling_ranking E-->
	<!--// 히트상품랭킹1 끝 -->	
	
	<h4>${groupName}新着レビュー<span><a href="/product_reviews/recent">${groupName}レビュー一覧</a></span></h4>
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
		 
		
	<!-- 추천상품 시작-->
	<!-- 
	 <h4>おすすめ商品</h4>	
	 <div class="selling_rating">
		<div class="rolling_arrow">
			<p class="arrow-prev"><a href="#"><img src="/content/images/main/newItem_prev.gif" alt="prev" /></a></p>
			<p class="arrow-next"><a href="#"><img src="/content/images/main/newItem_next.gif" alt="next" /></a></p>
		</div>
		<div class="items">
			<ul>
				<c:forEach items="${recommendItemList}" var="list">
					 <li>
				 		<div><a href="${list.link}"><img src="${list.imageSrc}" class="item_list_image_min" alt=""></a></div>
				 		<p><a href="${list.link}">${list.itemName}</a></p>
				 	</li>
				</c:forEach>
									                 					                
			</ul>
		</div>
	</div>
	 -->	
 	<!--// 추천상품 끝-->
</div>		

<page:javascript>
<script type="text/javascript">
	$(function(){
		
		Gnb.active('${categoryTeamCode}');
		
		setReviewItemEvent();
		setGroupBg();
		
	});
	
	// 그룹 배경이미지 
	function setGroupBg() {
		var $groupDiv = $('.middle_group > div');
		
		var $thisSelector = [];
		var imgSrc = [];
		var image = [];
		$groupDiv.each(function(index) {
			var cls = $(this).attr('class').split(' ')[1].replace('code_', '');
			$thisSelector[index] = $(this);
			
			imgSrc[index] = '/content/images/category_group/' + cls + '.png';

			image[index] = new Image();
			image[index].onload = function () {
				$thisSelector[index].css('background-image', 'url(' + imgSrc[index] + ')');
			};
			image[index].src = imgSrc[index];
		});
		
	}
	
	function setReviewItemEvent() {
		
		var $newItem = $('.selling_rating .items ul');
		var itemCount = $newItem.find('li').size();
		var currentPage = 1;
		var itemsPerPage = 4;
		var itemWidth = 164;
	    var totalPages = Math.ceil(itemCount / itemsPerPage);
	    
	    $newItem.width(itemWidth * itemsPerPage * totalPages);
	    
	    $('.arrow-prev').on('click', function(e) {
	    	
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
	    
	    $('.arrow-next').on('click', function(e) {
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

</script>
</page:javascript>
