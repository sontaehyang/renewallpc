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
			
			<h1 class="popup_title">사은품 검색</h1> <!-- 상품검색 -->
			
			<div class="popup_contents">
				<form:form modelAttribute="giftItemDto" method="get">
					<form:hidden path="targetId" />

					<div class="board_write">
						<table class="board_write_table" summary="사은품 관리">
							<caption>사은품 관리</caption>
							<colgroup>
								<col style="width: 150px;">
								<col style="">
							</colgroup>
							<tbody>
							<tr>
								<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
								<td>
									<div>
										<form:select path="where" title="상세검색 선택">
											<form:option value="name">사은품명</form:option>
											<form:option value="code">사은품코드</form:option>
										</form:select>
										<form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">진행 여부</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="processType" value="" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
											<form:radiobutton path="processType" value="not_progress" title="진행 전" label="진행 전" />
											<form:radiobutton path="processType" value="progress" title="진행 중" label="진행 중" />
											<form:radiobutton path="processType" value="end" title="종료" label="종료" />

										</p>
									</div>
								</td>
							</tr>
							<c:if test="${!isSellerPage}">
								<tr>
									<td class="label">판매자</td>
									<td>
										<div>
											<form:select path="sellerId">
												<option value="0">전체</option>
												<c:forEach var="seller" items="${sellerList}">
													<option value="${seller.sellerId}" ${op:selected(seller.sellerId, giftItemDto.sellerId)}>${seller.sellerName}</option>
												</c:forEach>
											</form:select>
										</div>
									</td>
								</tr>
							</c:if>
							</tbody>
						</table>
					</div>
					
					<div class="btn_all">
						<div class="btn_left">
							<a href="${requestContext.managerUri}/item/find-item?targetId=${itemParam.targetId}" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</a> <!-- 초기화 -->
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
						</div>
					</div> <!-- // btn_all -->
				</form:form>

				<div class="board_list">
					<form id="dataForm" method="post">
						<table class="board_list_table" summary="처리내역 리스트">
							<caption>처리내역 리스트</caption>
							<colgroup>
								<col style="width:30px;">
								<col style="width:150px;">
								<col style="width:100px;">
								<col style="width:auto;">
								<col style="width:100px;">
								<col style="width:100px;">
								<col style="width:300px;">
								<col style="width:40px;">
							</colgroup>
							<thead>
							<tr>
								<th scope="col"><input type="checkbox" class="check_all" title="체크박스"></th>
								<th scope="col">상태</th>
								<th scope="col">이미지</th>
								<th scope="col">사은품명</th>
								<th scope="col">금액</th>
								<th scope="col">판매자</th>
								<th scope="col">적용기간</th>
								<th>${op:message('M00192')}</th>  <!-- 추가 -->
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${pageContent.content}" var="list" varStatus="i">
								<tr id="item_${list.id}">
									<td>
										<input type="checkbox" name="id" title="체크박스" value="${list.id}">
										<span class="item_info" style="display:none;">
											<span class="process_type">${list.processType}</span>
											<span class="item_names">${list.name}</span>
											<span class="item_user_code">${list.code}</span>
											<span class="item_price">${list.price}</span>
										</span>

									</td>
									<td>
										<c:choose>
											<c:when test='${list.processType == "NOT_PROGRESS"}'>
												진행 전
											</c:when>
											<c:when test='${list.processType == "PROGRESS"}'>
												진행 중
											</c:when>
											<c:when test='${list.processType == "END"}'>
												종료
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<img src="${shop:loadImageBySrc(list.imageSrc, "S")}" class="item_image" alt="" />
									</td>
									<td class="left break-word">
										${list.name}&nbsp;(${list.code})
									</td>
									<td class="text-right">
										${op:numberFormat(list.price)}원
									</td>
									<td>
										<c:set var="sellerName" value=""/>
										<c:forEach var="seller" items="${sellerList}">
											<c:if test="${seller.sellerId == list.sellerId}">
												<c:set var="sellerName" value="${seller.sellerName}"/>
											</c:if>
										</c:forEach>
										<span class="glyphicon glyphicon-user"></span>${sellerName}
									</td>
									<td>
										<c:choose>
											<c:when test="${empty list.validStartDateText && empty list.validEndDateText}">
												상시
											</c:when>
											<c:otherwise>
												${list.validStartDateText} ~ ${list.validEndDateText}
											</c:otherwise>
										</c:choose>
									</td>
									<td><a href="javascript:addRelationItem(${list.id})" class="btn btn-gradient btn-xs">${op:message('M00192')}</a></td> <!-- 추가 -->
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</form>
				</div>
				<c:if test="${empty pageContent.content}">
					<div class="no_content">
							${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
				</c:if>

				<page:pagination-jpa />
				
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

	$('.check_all').on('click',function() {
		if ($(this).is(':checked')) {
			$('input[name=id]').prop('checked', true);
		} else {
			$('input[name=id]').prop('checked', false);
		}
	});

});

function addRelationItem(itemId, messageDisplay) {
	var display = true; 
	if (messageDisplay == false) {
		display = false;	
	} 
	
	var targetId = $('#targetId').val();

	if (opener.Shop.isAddedRelationItem(targetId, itemId)) {
		if (display) {
			var message = Message.get("M01215");	// 이미 추가한 상품입니다.
			Message.danger(message);
		}
		return;
	}

	var $item = $('#item_' + itemId);
	var processType =  $item.find('.process_type').text();
	var item = {
		'itemId' : itemId,
		'itemName' : $item.find('.item_names').text(),
		'itemUserCode' : $item.find('.item_user_code').html(),
		'itemImage' : $item.find('.item_image').attr('src'),
		'itemSalePrice' : 0,
		'brand' : '',
		'itemPrice' : $item.find('.item_price').text(),
		'presentPrice' : 0,
		'sellerName' : '',
		'minusSpotDiscount' : 0,
		'exceptSpotDiscount' : 0,
		'itemOptions' : [],
        'commissionRate' : 0
	};

	if (processType != 'PROGRESS') {
		if (display) {
			Message.danger("적용 가능한 사은품이 아닙니다.");
		}
		return;
	}

	console.log(item);

	opener.Shop.addRelationItem(targetId, item);
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

	$('input[name=id]:checked').each(function() {
		var itemId = $(this).val();
		addRelationItem(itemId, false);
		
	});
	
	Message.success(Message.get("M01217"));	// 추가하였습니다.
}

</script>		