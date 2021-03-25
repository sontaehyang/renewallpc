<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>
		
				
			<div class="item_list">			
				<h3><span>상품 랭킹 관리</span></h3>	
				<p class="text" style="margin-bottom: 5px;">
					* 상품 랭킹은 팀 / 그룹 /1차 카테고리까지 설정이 가능합니다.<br />
					* 랭킹 상품을 설정하기 위한 카테고리를 선택한 후 검색버튼을 클릭해 주십시오.<br />
					* 좌측에 실제 상품판매/조회수 순위별로 상품이 노출됩니다.<br />
					* 우측 상품이 실제 화면에 노출되는 랭킹 상품이며 운영자에 의해 설정 및 순서 변경이 가능합니다.<br />
					<strong>* 상품 순서는 상품을 드레그하여 변경하실 수 있습니다.</strong>
				</p>
				<form:form modelAttribute="rankingParam" method="post">
					<div class="board_write">		
						<table class="board_write_table" summary="상품리스트">
							<caption>상품리스트</caption>
							<colgroup>
								<col style="width: 150px" />
								<col style="auto;" /> 
								<col style="width: 150px" />
								<col style="auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00270')} <!-- 카테고리 --></td>
									<td colspan="3">
										<div>
											<form:select path="categoryTeamCode" class="category">
												<option value="">= Team =</option> <!-- 팀/그룹 -->
												<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
													<c:if test="${categoriesTeam.categoryTeamFlag == 'Y' && categoriesTeam.code != 'sale_outlets'}">
														<form:option value="${categoriesTeam.code}" label="${categoriesTeam.name}" />
													</c:if>
												</c:forEach>
												
											</form:select>
											
											
											<form:select path="categoryGroupCode" class="category">
												<option value="">= Group =</option> <!-- 팀/그룹 -->
												
												<c:if test="${!empty rankingParam.categoryTeamCode}"> 
													<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
														<c:if test="${categoriesTeam.categoryTeamFlag == 'Y' && categoriesTeam.code == rankingParam.categoryTeamCode}">
															<c:forEach items="${categoriesTeam.categoriesGroupList}" var="categoriesGroup">
																<c:if test="${categoriesGroup.categoryGroupFlag == 'Y'}">
																	<form:option value="${categoriesGroup.code}" label="${categoriesGroup.groupName}" />
																</c:if>
															</c:forEach>
														</c:if>
													</c:forEach>
												</c:if>
												
											</form:select>
											
											
											<form:select path="categoryUrl" class="category">
												<option value="">= 1${op:message('M00075')} =</option> <!-- // {}차 카테고리 -->
												<c:forEach items="${categoryList}" var="category">
													<form:option value="${category.categoryUrl}" label="${category.categoryName}" />
												</c:forEach>
											</form:select>
											
											<button type="submit" class="btn btn-dark-gray btn-sm" style="margin-left: 15px;"><span>${op:message('M00048')}</span></button>	<!-- 검색 -->
										</div>
									</td>
								</tr>
								
						</table>
						
					</div> <!-- // board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							
						</div>
						<div class="btn_right">
							
						</div>
					</div>
				</form:form>
				
	

				<c:if test="${!empty rankingParam.categoryTeamCode}">
					<table>
						<tr>
							<td>
								<h3 style="margin-top: 30px;"><span>${op:message('M00409')} <!-- 상품 랭킹 설정 --></span></h3>
							</td>
							<td align="right">
								<button type="button" id="button_add_relation_item" class="table_btn" onclick="Shop.findItem('ranking')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
							</td>
						</tr>
					</table>
					
					<form id="listForm">
						<input type="hidden" name="categoryTeamCode" value="${rankingParam.categoryTeamCode}" />
						<input type="hidden" name="categoryGroupCode" value="${rankingParam.categoryGroupCode}" />
						<input type="hidden" name="categoryUrl" value="${rankingParam.categoryUrl}" />
						<div class="ranking_wrap clear_fix">
							<div class="section left">
								<h4>${op:message('M00407')}</h4> <!-- 상품 판매 순위 -->
								<ul id="sale_ranking_items" tabinex="0">
									<c:forEach items="${saleRankingList}" var="item" varStatus="i">
									<li><a href="#" rel="${item.itemId}">
										<span class="rank">${i.count}</span>
										<span class="image"><img src="${item.imageSrc}" class="item_image"/></span>
										<p>
											<span class="item_user_code">[${item.itemUserCode}]</span>
											${item.itemName}
										</p>
										</a>
									</li>
									</c:forEach>
								</ul>
								<a href="" id="add_all_item" class="all_item">${op:message('M00410')}</a>	<!-- 전체추가 -->
							</div>
							

							<a href="javascript:;" id="add_ranking_item"><img src="/content/images/item/more_btn.png" alt="추가 버튼"></a>
							
							<div class="section right">
								<h4>${op:message('M00408')}</h4> <!-- 상품 노출 순위 -->
								<ul id="ranking_items" class="sortable_ranking_item">
									<c:forEach items="${rankingList}" var="ranking" varStatus="i">
									<li><a href="#">
										<input type="hidden" name="itemIds" value="${ranking.item.itemId}" />
										<span class="rank">${i.count}</span>
										<span class="image"><img src="${ranking.item.imageSrc}" class="item_image"/></span>
										<p>
											<span class="item_user_code">[${ranking.item.itemUserCode}]</span>
											${ranking.item.itemName}
										</p>
										</a>
										<a href="#" class="delete_ranking_item"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
									</li>
									</c:forEach>
								</ul>
								<a href="" id="delete_all_item" class="all_item">${op:message('M00411')}</a>	 <!-- 전체삭제 -->
							</div>
						</div>
						<div class="tex_c mt30">
							<button type="submit" class="btn btn-active">${op:message('M00101')}</button>	<!-- 저장 -->
						</div>
					</form>
					
				</c:if>
			</div>
	
<script type="text/javascript">
$(function() {
	
	// 카테고리 셀렉트 박스 선택 이벤트.
	var $categorySelectBoxes = $('.category');
	$categorySelectBoxes.on("change", function() {
		var index = $categorySelectBoxes.index($(this));
		for (var i = 0; i < $categorySelectBoxes.size(); i++) {
			if (i > index) {
				$categorySelectBoxes.eq(i).val("");
			}
		}
		$('#rankingParam').submit();
	});
	
	
	// 상품선택
	$('#sale_ranking_items li a').on('click', function(e) {
		e.preventDefault();
		
		if ($(this).parent().hasClass('selected')) {
			$(this).parent().removeClass('selected');
		} else {
			$(this).parent().addClass('selected');
			
		}
	});
	
	
	// 상품추가 (더블클릭)
	$('#sale_ranking_items li a').on('dblclick', function(e) {
		e.preventDefault();

		$(this).parent().addClass('selected');
		$('#add_ranking_item').click();
	});
	
	// 상품삭제 (더블클릭)
	$(document).on({
		'dblclick': function(e) {
			e.preventDefault();
	
			$(this).parent().remove();
		}, 
		'click': function(e) {
			e.preventDefault();
		}
	}, '#ranking_items li a');
	
	
	// 전체추가
	$('#add_all_item').on('click', function(e) {
		e.preventDefault();
		$('#sale_ranking_items li').addClass('selected');
		$('#add_ranking_item').click();
	});
	
	
	// 전체삭제.
	$('#delete_all_item').on('click', function(e) {
		e.preventDefault();
		$('#ranking_items li').remove();
	});
	
	
	// 상품추가
	$('#add_ranking_item').on("click", function() {
		
		// 상품 추가 여부 체크
		var $selectedItems  = $('#sale_ranking_items li.selected');
		if ($selectedItems.size() == 0) {
			
			alert(Message.get("M00405"));	// 추가할 상품을 클릭해 주세요.
			$('#sale_ranking_items').focus();
			return;
		}
		
		
		$selectedItems.each(function() {
			var itemId = $(this).find('a').attr('rel');
			var matchItemCount = 0;
			var $rankingItemIds = $('#ranking_items input[name=itemIds]');
			
			$rankingItemIds.each(function() {
				if (itemId == $(this).val()) {
					matchItemCount++;
				}
			});
			
			if (matchItemCount == 0) {
				// 순위
				var rank = $rankingItemIds.size() + 1;
				var $itemContent = $(this).find('a').clone();
				$itemContent.find('span.rank').text(rank);

				
				var html = '';
				html += '<li><a href="#">';
				html += '	<input type="hidden" name="itemIds" value="' + itemId + '" />';
				html += 	$itemContent.html();
				html += '	</a>';
				html += '	<a href="#" class="delete_ranking_item"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>';
				html += '</li>';
				$('#ranking_items').append(html);
			}
			$selectedItems.removeClass('selected');
			
		});
		
		//alert('추가되었습니다.');
	});
	
	
	// 랭킹 노출 상품 삭제.
	$('.ranking_wrap').on('click', '.delete_ranking_item', function(e) {
		e.preventDefault();
		//if (confirm(Message.get("M00196"))) {		// 삭제하시겠습니까?
			$(this).closest('li').remove();
		//}
	});
	
	
	// 랭킹 순서 변경.
	$('.sortable_ranking_item').sortable({
		placeholder: "sortable_ranking_item_placeholder"
	});
	
	
	$('#listForm').validator(function() {
		
		/*
		// 추가 상품 체크
		if ($('#ranking_items li').size() == 0) {
			alert(Message.get("M00405"));	// 추가할 상품을 클릭해 주세요.
			$('#sale_ranking_items').focus();
			return false;
		}
		*/
		
		$.post('/opmanager/ranking/create', $('#listForm').serialize(), function(response) {
			Common.responseHandler(response, function() {
				alert(Message.get("M00406"));	// 저장되었습니다.
				location.reload();
			});
		});
		
		return false;
	});
});


function findItemCallback(id, item){
	 
	var matchItemCount = 0;
	var $rankingItemIds = $('#ranking_items input[name=itemIds]');
	
	var itemCount = $('#ranking_items > li').size();
	
	if(itemCount >= 100){
		return;
	}
	
	$rankingItemIds.each(function() {
		if (item.itemId == $(this).val()) {
			matchItemCount++;
		}
	});
	
	if (matchItemCount == 0) {
		// 순위
		var rank = $rankingItemIds.size() + 1;
		var $itemContent = $(this).find('a').clone();
		$itemContent.find('span.rank').text(rank);

		var html = '';
		html += '<li><a href="#">';
		html += '	<input type="hidden" name="itemIds" value="' +  item.itemId  + '" />';
		html += 	'	<span class="rank">'+rank+'</span>';
		html += 	'	<span class="image"><img src="' + item.itemImage + '" class="item_image" alt="상품이미지" /></span>';
		html += 	'	<p>';
		html += 	'	<span class="item_user_code">['+item.itemUserCode+']</span>';
		html += item.itemName	;
		html += 	'	</p>';
		html += '	</a>';
		html += '	<a href="#" class="delete_ranking_item"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>';
		html += '</li>';
		$('#ranking_items').append(html);
	}
	
}
</script>
