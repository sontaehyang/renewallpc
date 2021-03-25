<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

		<!-- 본문 -->
		<div class="popup_wrap">
			
			<h1 class="popup_title">${op:message('M01185')}</h1> <!-- 상품검색 -->
			
			<div class="popup_contents">
				<c:if test="${itemParam.targetId == 'spot'}">				
				<p class="text-info">
					* 현재 핫딜이 진행 중인 상품은 검색되지 않습니다.
				</p>
				</c:if>
				<form:form modelAttribute="itemParam" method="get">
					<form:hidden path="targetId" />
					<form:hidden path="conditionType" />
					
					<div class="board_write">					
						<p class="pop_tit">${op:message('M01185')}</p>		 <!-- 상품검색 -->	
						
								
						<table class="board_write_table" summary="관련상품등록">
							<caption>관련상품등록 </caption>
							<colgroup>
								<col style="width: 100px" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00270')}</td> <!-- 카테고리 -->
									<td>
										<div>
											<form:select path="categoryGroupId" class="category">
												<option value="0">= ${op:message('M00076')} =</option> <!-- 팀/그룹 -->
												<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
													<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
														<optgroup label="${categoriesTeam.name}">
														<c:forEach items="${categoriesTeam.categoriesGroupList}" var="categoriesGroup">
															<c:if test="${categoriesGroup.categoryGroupFlag == 'Y'}">
																<form:option value="${categoriesGroup.categoryGroupId}" label="${categoriesGroup.groupName}" />
															</c:if>
														</c:forEach>
														</optgroup>
													</c:if>
												</c:forEach>
												
											</form:select>
											
											<form:select path="categoryClass1" class="category">
											</form:select>
											
											<form:select path="categoryClass2" class="category">
											</form:select>
											
											<form:select path="categoryClass3" class="category">
											</form:select>
											
											<form:select path="categoryClass4" class="category">
											</form:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
									<td>
										<div>
											<form:select path="where" title="상세검색 선택">
												<form:option value="ITEM_NAME">${op:message('M00018')}</form:option> <!-- 상품명 -->
												<form:option value="ITEM_USER_CODE">${op:message('M00019')}</form:option> <!-- 상품번호 -->
												<form:option value="STOCK_CODE">관리코드</form:option>
											</form:select>
											<form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
										</div>
									</td>
								</tr>
								<tr ${requestContext.sellerPage ? 'style="display:none"' : ''}>
									<td class="label">판매자</td>
									<td>
										<div>
											<c:choose>
												<c:when test="${requestContext.opmanagerPage}">
												
													<select name="sellerId" title="${op:message('M01630')}"> <!-- 판매자선택 --> 
														<option value="0">${op:message('M00039')}</option> <!-- 전체 --> 
														<c:forEach items="${sellerList}" var="list" varStatus="i">
														<c:choose>
															<c:when test="${itemParam.sellerId == list.sellerId}">
																<c:set var='selected' value='selected'/>
															</c:when>
															<c:otherwise>
																<c:set var='selected' value=''/>
															</c:otherwise>
														</c:choose>
														<option value="${list.sellerId}" ${selected}>${list.sellerName}</option>
														</c:forEach>
													</select>
												
												</c:when>
												<c:when test="${requestContext.opmanagerPage}">
													<form:input path="sellerId" />
												</c:when>
											</c:choose>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						
					</div> <!--// board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							<a href="${requestContext.managerUri}/item/find-item?targetId=${itemParam.targetId}" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</a> <!-- 초기화 -->
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
						</div>
					</div> <!-- // btn_all -->
				</form:form>
				
				<div class="board_write" style="clear:both">					
					<p class="pop_tit">${op:message('M01218')}</p>		 <!-- 상품목록 -->				
					<table class="board_list_table" summary="상품리스트">
						<caption>상품리스트</caption>
						<colgroup>
							<col style="width: 30px" />
							<col style="width: 70px" />
							<col style="" />
							<col style="width: 100px" />
							<col style="width: 100px" />
							<col style="width: 120px" />
							<col style="width: 100px" />
							<col style="width: 100px" />
							<col style="width: 100px" />
							<col style="width: 40px" />
						</colgroup>
						<thead>
							<tr>
								<th><input type="checkbox" id="check_all" title="전체 선택" /></th>
								<th>${op:message('M00752')}</th> <!-- 이미지 -->
								<th>${op:message('M00018')}</th> <!-- 상품명 -->
								<th>${op:message('M00019')}</th> <!-- 상품번호 -->
								<th>관리코드</th>
								<th>${op:message('M01630')}</th> <!-- 판매자 -->
								<th>정가/판매가</th>
								<th>할인 총액</th>
								<th>할인적용 판매가</th>						
								<th>${op:message('M00192')}</th>  <!-- 추가 -->						
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="item">
							<tr id="item_${item.itemId}">
								<td>
									<input type="checkbox" name="id" value="${item.itemId}" />
									
									<span class="item_info" style="display:none;">
										<span class="item_names">${item.itemName}</span>
										<span class="item_user_code">${item.itemUserCode}</span>
										<span class="seller_name">${item.sellerName}</span>
										<span class="item_sale_price">${item.salePrice}</span>
										<span class="brand">${item.brand}</span>
										<span class="item_price">${item.itemPrice}</span>
										<span class="minus_spot_discount">${item.minusSpotDiscount}</span>
										<span class="except_spot_discount">${item.exceptSpotDiscount}</span>
										<span class="present_price">${item.presentPrice}</span>
                                        <span class="item_commission_rate">${item.commissionRate}</span>
										<span class="item_display_quantity">${item.displayQuantity}</span>
										<span class="item_stock_flag">${item.stockFlag}</span>
										<span class="item_stock_quantity">${item.stockQuantity}</span>
										<span class="item_stock_code">${item.stockCode}</span>
										<span class="item_sold_out">${item.itemSoldOut}</span>
										<span class="quick_delivery_extra_charge_flag">${item.quickDeliveryExtraChargeFlag}</span>
									</span>
									
									<span class="item_options" style="display:none;">
				
										<c:forEach items="${item.itemOptionGroups}" var="itemOptionGroup">
											<span class="itemOptionGroup">
												<span class="groupItemId">${itemOptionGroup.itemId}</span>
												<span class="groupOptionType">${itemOptionGroup.optionType}</span>
												<span class="optionTitle">${itemOptionGroup.optionTitle}</span>
												<span class="itemOptions">
													<c:forEach items="${itemOptionGroup.itemOptions}" var="itemOption" varStatus="i">
														<span class="itemOption">
															<span class="itemOptionId">${itemOption.itemOptionId}</span>
															<span class="itemId">${itemOption.itemId}</span>
															<span class="optionType">${itemOption.optionType}</span>
															<span class="optionName1">${itemOption.optionName1}</span>
															<span class="optionName2">${itemOption.optionName2}</span>
															<span class="optionName3">${itemOption.optionName3}</span>
															<span class="optionCode">${itemOption.optionStockCode}</span>
															<span class="price">${itemOption.price}</span>
															<span class="priceNonmember">${itemOption.optionPriceNonmember}</span>
															<span class="stockQuantity">${itemOption.optionStockQuantity}</span>
															<span class="stockScheduleText">${itemOption.optionStockScheduleText}</span>
															<span class="stockScheduleDate">${itemOption.optionStockScheduleDate}</span>
															<span class="displayFlag">${itemOption.optionDisplayFlag}</span>
														</span>
													
													</c:forEach>
												</span>
											</span>
										</c:forEach>
										
									</span>
								</td>
								<td>
									<div>
										<img src="${shop:loadImage(item.itemCode, item.itemImage, 'XS')}" class="item_image" alt="상품이미지" />
									</div>
								</td>
								<td class="tleft">
									<c:choose>
										<c:when test="${item.dataStatusCode == '1'}">
											<c:if test="${item.itemSoldOutFlag == 'Y'}"><strong style="color:red">[품절]</strong></c:if>
											<c:if test="${item.displayFlag == 'N'}"><strong style="color:red">[비공개]</strong></c:if>
										</c:when>
										<c:otherwise>
											<a href="javascript:Manager.itemLog('${item.itemId}')"><strong style="color:red">[비공개]</strong></a>		
										</c:otherwise>
									</c:choose>
									${item.itemName}
								</td>
								<td>${item.itemUserCode}</td>
								<td>${item.stockCode}</td>
								<td>${item.sellerName}</td>
								<td class="text-right">
									<c:if test="${not empty item.itemPrice && item.itemPrice != item.salePrice}">
										<span style="text-decoration:line-through;">${op:numberFormat(item.itemPrice)}원</span>
									</c:if>
									<p>${op:numberFormat(item.salePrice)}원</p>
								</td>
								<td class="text-right">${op:numberFormat(item.salePrice - item.presentPrice)}원</td>
								<td class="text-right">${op:numberFormat(item.presentPrice)}원</td>
								<td><a href="javascript:addRelationItem(${item.itemId})" class="btn btn-gradient btn-xs">${op:message('M00192')}</a></td> <!-- 추가 -->
							</tr>
							</c:forEach>
							
						</tbody>
					</table>
					
					<c:if test="${empty list}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. --> 
					</div>
					</c:if>
					
					<page:pagination-manager />
				</div> <!--// board_write -->
				
				<p class="popup_btns">
					<a href="javascript:addCheckedRelationItem()" class="btn btn-active">${op:message('M01214')}</a> <!-- 선택상품 추가 -->
					<a href="javascript:self.close();" class="btn btn-default">닫기</a>
				</p>
				
			</div> <!-- // popup_contents -->
			
			<a href="#" class="popup_close">창 닫기</a>
		</div>
		
	
<style>
#fade_out_message {
	position: fixed; top: 45%; left: 50%; margin: 0 auto; padding: 15px; min-width: 200px; text-align: center;
	border-radius: 5px;
}
.bg_default {background: #ffffff}
.bg_primary {background: #428bca}
.bg_success {background: #dff0d8}
.bg_info {background: #d9edf7}
.bg_warning {background: #f2dede}
.bg_danger {background: #f2dede}
</style>	
<script type="text/javascript">
$(function() {
	// 팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	
	Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');
});

function addRelationItem(itemId, messageDisplay) {
	var display = true; 
	if (messageDisplay == false) {
		display = false;	
	} 

	var targetId = $('#targetId').val();
	
	// 추천 테마 상품 갯수 확인
	if (targetId == 'mainTheme') {
		if(!opener.Shop.isAvilableItemCount(targetId)){
			Message.danger("한 테마에 상품은 최대 4개까지 등록 가능합니다.");
			return;
		}
	}
	
	if (targetId == 'related') {
		if(!opener.Shop.isAvilableRelatedItemCount(targetId)){
			Message.danger("관련상품은 최대 20개까지 등록 가능합니다.");
			return;
		}
	}
	
	if (opener.Shop.isAddedRelationItem(targetId, itemId)) {
		if (display) {
			var message = Message.get("M01215");	// 이미 추가한 상품입니다.
			Message.danger(message);
		}
		return;
	}

	var $item = $('#item_' + itemId);
	var item = {
		'itemId' : itemId,
		'itemName' : $item.find('.item_names').text(),
		'itemUserCode' : $item.find('.item_user_code').html(),
		'itemImage' : $item.find('.item_image').attr('src'),
		'itemSalePrice' : $item.find('.item_sale_price').text(),
		'brand' : $item.find('.brand').text(),
		'itemPrice' : $item.find('.item_price').text(),
		'presentPrice' : $item.find('.present_price').text(),
		'sellerName' : $item.find('.seller_name').text(),
		'minusSpotDiscount' : $item.find('.minus_spot_discount').text(),
		'exceptSpotDiscount' : $item.find('.except_spot_discount').text(),
		'itemOptions' : [],
        'commissionRate' : $item.find('.item_commission_rate').text(),
		'displayQuantity': $item.find('.item_display_quantity').text(),
		'stockFlag': $item.find('.item_stock_flag').text(),
		'stockQuantity': $item.find('.item_stock_quantity').text(),
		'stockCode': $item.find('.item_stock_code').text(),
		'isItemSoldOut': $item.find('.item_sold_out').text() == 'true' ? true : false,
		'quickDeliveryExtraChargeFlag' : $item.find('.quick_delivery_extra_charge_flag').text()
	};
	
	// 상품 옵션 정보
	var itemOptionGroups = [];
	
	$item.find('span.itemOptionGroup').each(function() {
		var itemOptionGroup = {};
		itemOptionGroup.itemId = $(this).find('span.groupItemId').text();
		itemOptionGroup.optionType = $(this).find('span.groupOptionType').text();
		itemOptionGroup.optionTitle = $(this).find('span.optionTitle').text();
		
		var itemOptions = [];
		$(this).find('span.itemOption').each(function() {
	
			var itemOption = {};
			$(this).find('span').each(function() {
				
				itemOption[$(this).attr('class')] = $(this).text();
			});
			itemOptions.push(itemOption);
		});
		
		itemOptionGroup.itemOptions = itemOptions;
		itemOptionGroups.push(itemOptionGroup);
		
	});
	
	item.itemOptionGroups = itemOptionGroups;

	if (targetId.indexOf('itemOption') > -1) {
		addErpItemOption(targetId, item);
	} else {
		opener.Shop.addRelationItem(targetId, item);
	}

	if (display) {
		Message.success(Message.get("M01217"));	// 추가하였습니다.
	}
}

// 체크한 상품 일괄 추가
function addCheckedRelationItem() {
	var targetId = $('#targetId').val();
	if ($('input[name=id]:checked').size() == 0) {
		var message = Message.get("M01216");	// 추가할 상품을 선택해 주세요.
		alert(message);
		$('#check_all').focus();
		return;
	}
	
	// 추천 테마 상품 갯수 확인
	if (targetId == 'mainTheme') {
		if($('input[name=id]:checked').size() > 4 || !opener.Shop.isAvilableItemCount(targetId) ){
			Message.danger("한 테마에 상품은 최대 4개까지 등록 가능합니다.");
			return;
		}
	}
	
	if (targetId == 'related') {
		if($('input[name=id]:checked').size() > 20){
			Message.danger("관련상품등록은 최대 20개까지 등록 가능합니다.");
			return;
		}
	}
	
	$('input[name=id]:checked').each(function() {
		var itemId = $(this).val();
		addRelationItem(itemId, false);
	});
	
	Message.success(Message.get("M01217"));	// 추가하였습니다.
}

// 옵션추가 (ERP)
function addErpItemOption(targetId, item) {
	var $itemOptions = opener.$('#' + targetId).find('.item-combination-options');
	var $itemOptionWrapper = $itemOptions.closest('.combination-option-wrapper');

	var optionName1 = $itemOptionWrapper.find('.item-combination-option-title input:text').val();
	var optionDisplayType = $itemOptionWrapper.find('.item-combination-option-title input:radio:checked').val();

	/* fixing - 기존 데이터 수정, select - 데이터 추가 */
	// 변경할 옵션의 tr element
	var $newItem = $itemOptions.find('tr:last-child');

	if (optionDisplayType == 'select') {
		// 옵션 tr 추가
		$itemOptions.append($itemOptions.find('tr').eq(0).parentHtml());

		// 추가된 tr element 로 재정의
		$newItem = $itemOptions.find('tr:last-child');
	}

	// 옵션 input 데이터 세팅
	$newItem.find('input').prop('readonly', false).val('');
	$newItem.find('input[name="optionPrice"]').prop('readonly', true);
	$newItem.find('input[name="optionStockCode"]').prop('readonly', true);
	$newItem.find('input[name="optionStockQuantity"]').prop('readonly', true).removeClass('required-item-option-combination');
	$newItem.find('select').find('option').prop('disabled', false);
	$newItem.find('select').each(function() {
		$(this).find('option:eq(0)').prop('selected', true);
	});
	$newItem.find('input[name="optionType"]').val('C');
	$newItem.find('input[name="optionName1"]').val(optionName1);
	$newItem.find('input[name="optionDisplayType"]').val(optionDisplayType);

	// 상품명, 원가 복사
	$newItem.find('input[name="optionName2"]').val(item.itemName);
	$newItem.find('input[name="optionCostPrice"]').val(Common.numberFormat(item.itemSalePrice));
	$newItem.find('input[name="optionQuantity"]').val(1);

	// 관리코드가 존재하는 경우, 연동 항목 disabled 처리
	if (item.stockCode != '') {
		$newItem.find('input[name="erpItemCode"]').val(item.itemUserCode);
		$newItem.find('input[name="optionStockCode"]').val(item.stockCode);

		$newItem.find('input[name="optionCostPrice"]').prop('readonly', true);

		$newItem.find('select[name="optionStockFlag"]').val(item.stockFlag);
		$newItem.find('select[name="optionStockFlag"]').find('option[value="' + (item.stockFlag == 'Y' ? 'N' : 'Y') + '"]').prop('disabled', true);

		$newItem.find('select[name="optionSoldOutFlag"]').val(item.isItemSoldOut ? 'Y' : 'N');
		$newItem.find('select[name="optionSoldOutFlag"]').find('option[value="' + (item.isItemSoldOut ? 'N' : 'Y') + '"]').prop('disabled', true);
	}
}
</script>