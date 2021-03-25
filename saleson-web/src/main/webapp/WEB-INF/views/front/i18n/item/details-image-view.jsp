<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


		<!-- 본문 -->
		<!-- 팝업사이즈 824*700-->
		<div class="popup_wrap">
			<h1 class="popup_title">${op:strcut(item.itemName,30)}</h1>
			<div class="popup_contents type02">
				
				<div class="popup_itemView">
					
					<div class="photoView">
						<c:forEach items="${item.itemImages}" var="itemImage">
							<c:set var="displayImage" value="display : none;"/>
							<c:if test="${itemImage.itemImageId == itemImageId}">
								<c:set var="displayImage" value="display : block;"/>
							</c:if>
							<a href="javascript:nextImage()" style="${displayImage}">
								<img src="${shop:loadImage(item.itemUserCode, itemImage.imageName, 'L')}" id="big_image"  style="width:  576px; height: auto; "/></a>
						</c:forEach>
					</div>
					
					<div class="min_img thumbs">
						<ul class="clear">
							<c:forEach items="${item.itemImages}" var="itemImage">
								<li>
									<a href="javascript:;" ${itemImage.itemImageId == itemImageId ? 'class="on"' : ''} data-init="${itemImage.itemImageId == itemImageId}">
										<img src="${ shop:loadImage(item.itemUserCode, itemImage.imageName, 'XS') }" style="width: 68px; height: 68px;"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
					
				</div>
				
				
			</div><!--//popup_contents E-->
			
			<a href="javascript:self.close()" class="popup_close">창 닫기</a>
		</div>

<page:javascript>
<script type="text/javascript">

var $thumbnails = $('.thumbs li a');
var $itemImage = $('.photoView a');

$(function() {
	$thumbnails.on('click', function(e) {
		//e.prevendDefault();
		
		$thumbnails.removeClass('on');
		$(this).addClass('on');
		
		var index = $(this).parent().index();
		
		$itemImage.hide();
		$itemImage.eq(index).show();
	});

	init();
});

function init() {
	$('.thumbs li').each(function(idx) {
		var flag = $(this).find('a').data('init');

		if (flag == 'true') {
			$itemImage.hide();
			$itemImage.eq(idx).show();
			return;
		}
	});

	var initItemImageId = '${itemImageId}';

	if (initItemImageId == '0') {
		$thumbnails.eq(0).click();
	}
}

function nextImage() {
	var $thumbnails = $('.thumbs li a');
	var thumbSize = $thumbnails.size();
	var nextIndex = $thumbnails.index($('.thumbs li a.on')) + 1;
	if (nextIndex == thumbSize) {
		nextIndex = 0;
	}
	$thumbnails.eq(nextIndex).click();
}
</script>
</page:javascript>