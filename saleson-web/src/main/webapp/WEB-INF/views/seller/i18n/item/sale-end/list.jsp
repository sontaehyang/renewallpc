<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
#reject-message-wrap {
	border: 1px solid #ccc;
	overflow-y: auto;
	height: 100px;

}
.reject-message-layer {
	position: fixed;
	width: 420px;
	margin-left: -210px;
	margin-top: -150px;
	left: 50%;
	top: 50%;
	border: 3px solid #434755;
	display: none;
	background: #fff;
}
.reject-message-layer h1 {
	border-top: 0;
}
.reject-message-layer p {
	padding-bottom: 10px;
}
.reject-message-layer .popup_contents {
	padding: 30px 20px !important;
}
.data-status-message {
	display: none;
}
.show-reject-message {
	text-decoration: underline;
}
.show-reject-message:hover {
	color: #067aac;
}
</style>

			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>
		
				
			<div class="item_list">			
				<h3><span>상품리스트</span></h3>	
				
				<form:form modelAttribute="itemParam" method="post">
					<form:hidden path="categoryId" />		
						
					<div class="board_write">		
						<table class="board_write_table" summary="상품리스트">
							<caption>상품리스트</caption>
							<colgroup>
								<col style="width: 150px" />
								<col style="width: 600px;" /> 
								<col style="width: 150px" />
								<col style="width: auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00270')} <!-- 카테고리 --></td>
									<td colspan="3">
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
									<td colspan="3">
										<div>
											<form:select path="where" title="상세검색 선택">
												<form:option value="ITEM_USER_CODE">${op:message('M00783')} <!-- 상품코드 --></form:option>
												<form:option value="ITEM_NAME">${op:message('M00018')} <!-- 상품명 --></form:option>
											</form:select>
											<form:input path="query" class="w360" title="상세검색 입력" />
										</div>
									</td>
									
								</tr>
								
								
								<%-- 1.일반 --%>
								<c:if test="${op:property('shoppingmall.type') == '1'}">
								<tr>
									<td class="label">상품구분</td>
									<td colspan="3">
										<div>
											<p>
												<form:radiobutton path="itemType" value="" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
												<form:radiobutton path="itemType" value="1" label="일반상품" />
												<form:radiobutton path="itemType" value="2" label="사업자상품" /> 
											</p>
										</div>
									</td>
								</tr>
								</c:if>
								
								<tr>
									<td class="label">담당MD명</td>
									<td>
										<div>
											<input type="hidden" name="currentMdId" value="${seller.mdId}" />
											<input type="hidden" id="mdId" name="mdId" value="${seller.mdId}" />
											<form:input path="mdName" title="담당MD" readonly="true" />

											<button type="button" onclick="findMd('mdId')" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> MD검색</button>
											<button type="button" onclick="clearMd('mdId')" class="btn btn-gradient btn-sm"><span class="glyphicon glyphicon-remove"></span> 초기화</button>
										</div>
									</td>
									<td class="label">${op:message('M00784')} <!-- 가격 --></td>
									<td >
										<div>
											<form:select path="priceRange" title="가격대 선택">
												<option value="">${op:message('M00039')} <!-- 전체 --></option>
												<form:option value="0|10000" label="0～10,000" />
												<form:option value="10000|50000" label="10,000～50,000" />
												<form:option value="50000|100000" label="50,000～100,000" />
												<form:option value="100000|1000000" label="100,000～1,000,000" />
												<form:option value="1000000|90000000" label="1,000,000～" />
											</form:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">배송구분</td>
									<td>
										<div>
											<form:radiobutton path="deliveryType" value="" label="${op:message('M00039')}" checked="checked" /> <!-- 전체 -->
											<form:radiobutton path="deliveryType" value="1" label="본사배송" /> <!-- 인덱스 시킨다. -->
											<form:radiobutton path="deliveryType" value="2" label="업체배송" /> <!-- 인덱스 시키지 않는다. -->
										</div>
									</td>
									<td class="label">과세구분</td>
									<td>
										<div>
											<form:select path="taxType">
												<form:option value="" label="전체" />
												<form:option value="1" label="과세" />
												<form:option value="2" label="면세" />
											</form:select>
										</div> 
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00782')} <!-- 상품등록일 --></td>
									<td colspan="3">
										<div>
											<span class="datepicker"><form:input path="searchStartDate" maxlength="8" class="datepicker" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
											<span class="wave">~</span>
											<span class="datepicker"><form:input path="searchEndDate" maxlength="8" class="datepicker" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
											<span class="day_btns"> 
												<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 --> 
												<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 --> 
												<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 --> 
												<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 --> 
												<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 --> 
												<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
											</span>
										</div>
									</td>
								</tr>
								
							</tbody>
						</table>
						
					</div> <!-- // board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/seller/item/sale-end/list';">${op:message('M00047')}</button> <!-- 초기화 -->
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm">${op:message('M00048')}</button> <!-- 검색 -->
						</div>
					</div>
					
					<div class="count_title mt20">
						<h5>
							${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
						</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
						<span>
							<form:select path="orderBy" title="등록일선택">
								<form:option value="ITEM_ID" label="${op:message('M00202')}" /> <!-- 등록일 -->
								
								<c:if test="${!empty itemParam.categoryId}">
								<form:option value="ORDERING" label="${op:message('M00790')}" /> <!-- 정렬 -->
								</c:if>
								
								<form:option value="SALE_PRICE" label="${op:message('M00786')}" /> <!-- 판매가격 -->
								<form:option value="HITS" label="${op:message('M00685')}" /> <!-- 조회수 -->
							</form:select>
							<form:select path="sort" title="검색방법 선택">
								<form:option value="DESC" label="${op:message('M00689')}" />    <!-- 내림차순 -->
								<form:option value="ASC" label="${op:message('M00690')}" />    <!-- 오름차순 -->
							</form:select>
							<span>${op:message('M00052')} <!-- 출력수 --></span>
							<form:select path="itemsPerPage" title="출력수 선택">
								<form:option value="10" label="10" />
								<form:option value="20" label="20" />
								<form:option value="30" label="30" />
								<form:option value="50" label="50" />
								<form:option value="100" label="100" />
								<form:option value="500" label="500" />
								<form:option value="1000" label="1000" />
							</form:select>
							
							<c:if test="${!empty itemParam.categoryId}">
							<button type="button" id="change_ordering2" class="btn ctrl_btn " style="position:fixed;right:0; bottom:260px; z-index: 1000;">${op:message('M00791')}</button> <!-- 정렬순서변경 -->
							</c:if>
						</span>
					</div>
				</form:form>
				
				<div class="board_guide" style="border:1px solid #d5d5d5; padding: 15px; margin-top:15px; display: none">
					<p class="tip">
						<a href="javascript:;" class="btn_write gray_small" onclick="javascript:shoppingHow()"><span>쇼핑하우 txt파일 생성</span> </a>
					</p>
					<p class="tip">
						<br/>쇼핑하우 상품정보 파일을 생성합니다.
					</p> 
				</div> 
				
				<div class="board_list">
					<form id="listForm">
						<table class="board_list_table" summary="전체상품리스트">
							<caption>전체상품리스트</caption>
							<colgroup>
								<col style="width: 30px" />
								<col style="width: 60px;" />
								<col style="width: 70px;" />
								<col style="width: 60px;" />
								<col style="" />
								<col style="width: 100px" />
								<col style="width: 50px" />
								<col style="width: 80px" />
								<col style="width: 80px" />
								<col style="width: 80px" />
								<col style="width: 50px" />
								<col style="width: 80px" />
								<col style="width: 125px" />
								<col style="width: 50px" />
								<col style="width: 55px;" />
								<col style="width: 55px;" />
								<col style="width: 90px;" />
								<col style="width: 65px;" />
								<col style="width: 60px;" />
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="check_all" title="체크박스" /></th>
									<th>${op:message('M00200')}</th> <!-- 순번 -->
									<th>${op:message('M00787')} <!-- 판매상태 --></th>
									<th>${op:message('M00752')}</th> <!-- 이미지 -->
									<th>${op:message('M00018')}(${op:message('M00783')})</th> <!-- 상품명 --> <!-- 상품코드 -->
									<th>${op:message('M00783')}</th><!-- 상품코드 -->
									<th>과세</th>
									<th>정가</th>
									<th>${op:message('M00786')} <!-- 판매가격 --></th>
									<th>공급가</th>
									<th>수수료</th>
									<th>배송비구분</th>
									<th>배송비</th>
									<th>옵션</th>
									<th>${op:message('M01462')} <!-- 재고수 --></th>
									<th>${op:message('M00191')} <!-- 공개유무 --></th>
									<th>
									<c:choose>
										<c:when test="${itemParam.orderBy == 'HITS'}">
											${op:message('M00685')} <!-- 조회수 -->
										</c:when>
										<c:otherwise>
											${op:message('M00202')} <!-- 등록일 -->
										</c:otherwise>
									</c:choose>
									</th>
									<th>담당MD</th>
									<th>${op:message('M00590')}</th>	 <!-- 관리 -->		
								</tr>
							</thead>
							<tbody class="sortable">
							
							<c:forEach items="${list}" var="item" varStatus="i">
								<c:set var="displayFlagText">${op:message('M00096')}</c:set> <!-- 공개 -->
								<c:if test="${item.displayFlag == 'N'}">
									<c:set var="displayFlagText"><span style="color:#e84700">${op:message('M00097')}</span></c:set> <!-- 비공개 -->
								</c:if>
								
								<!-- 인덱스 시킴 -->
								<c:set var="noindexYn"><span style="color: #25A5DC">Y</span></c:set>	<!-- 인덱스 시킴. -->
								<c:if test="${item.seo.indexFlag == 'N'}">
									<c:set var="noindexYn" value="N" /> <!-- 인덱스 시키지 않음. -->
								</c:if>
								
								<c:choose>
									<c:when test="${item.dataStatusCode == '20' || item.dataStatusCode == '31' || item.dataStatusCode == '40' || item.dataStatusCode == '41'}">
										<c:set var="itemSaleStatusText">등록대기</c:set>
									</c:when>
									<c:when test="${item.dataStatusCode == '21'}">
										<c:set var="itemSaleStatusText"><a href="#" class="show-reject-message">등록보류</a></c:set>
									</c:when>
									<c:when test="${item.dataStatusCode == '30'}">
										<c:set var="itemSaleStatusText">재등록신청</c:set>
									</c:when>
									<c:when test="${item.dataStatusCode == '90'}">
										<c:set var="itemSaleStatusText">판매종료</c:set>
									</c:when>
									<c:otherwise>
										<c:set var="itemSaleStatusText">${op:message('M00694')}</c:set> <!-- 판매중 -->
									</c:otherwise>
								</c:choose>
								
								
								<tr>
									<td><input type="checkbox" name="id" value="${item.itemId}" class="${item.itemUserCode}" title="" /></td>
									<td>
										<c:choose>
											<c:when test="${itemParam.orderBy == 'ORDERING' && itemParam.sort == 'ASC'}">
												${pagination.number + i.count}
											</c:when>
											<c:otherwise>
												${pagination.itemNumber - i.count}
											</c:otherwise>
										</c:choose>
										<p style="padding-top: 5px;">
											<c:choose>
												<c:when test='${op:property("saleson.view.type") eq "api"}'>
													<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
												</c:when>
												<c:otherwise>
													<a href="/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
													<a href="/m/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="" /></a>
												</c:otherwise>
											</c:choose>
										</p>
									</td>
									<td>
										${itemSaleStatusText}
										<div class="data-status-message">
											${op:nl2br(item.dataStatusMessage)}
										</div>	
									</td>
									<td>
										<div>
											<a href="javascript:Link.view('/seller/item/edit/${item.itemId}')"><img src="${ shop:loadImage(item.itemUserCode, item.itemImage, 'XS') }" class="item_image" alt="상품이미지" /></a>
										</div>
									</td>
									<td class="left break-word">
										<a href="javascript:Link.view('/seller/item/edit/${item.itemUserCode}')" class="break-word">${item.itemName}</a>
										<a href="javascript:Link.view('/seller/item/edit/${item.itemUserCode}', 1)" class="break-word" style="color:#517BAB">[새탭]</a>
									</td>
									<td>${item.itemUserCode}</td>									
									
									<td>${item.taxType == '1' ? '과세' : '면세'}</td>
									<td class="text-right">
										<c:choose>
											<c:when test="${empty item.itemPrice}">
												-
											</c:when>
											<c:otherwise>
												${op:numberFormat(item.itemPrice)}원
											</c:otherwise>
										</c:choose>
									</td>
									<td class="text-right">
										${op:numberFormat(item.salePrice)}원
									</td>
									<td class="text-right">
										${op:numberFormat(item.salePrice - (item.salePrice * item.commissionRate * 0.01))}원
									</td>
									<td class="text-right">
										${op:numberFormat(item.commissionRate)}%
									</td>
									<td>
										<c:choose>
											<c:when test="${item.shippingType == '1'}">
												무료배송
											</c:when>
											<c:when test="${item.shippingType == '2'}">
												판매자조건부
											</c:when>
											<c:when test="${item.shippingType == '3'}">
												출고지조건부
											</c:when>
											<c:when test="${item.shippingType == '4'}">
												상품조건부
											</c:when>
											<c:when test="${item.shippingType == '5'}">
												개방배송비
											</c:when>
											<c:when test="${item.shippingType == '65'}">
												고정배송비
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
									<td class="text-right">
										${op:numberFormat(item.shipping)}원
										
										<c:if test="${item.shippingFreeAmount > 0}">
											<p style="font-size: 11px">(${op:numberFormat(item.shippingFreeAmount)}원 이상 무료)</p>
										</c:if>
									</td>
									<td>${item.itemOptionFlag}</td>
									<td>
										<c:choose>
											<c:when test="${item.stockQuantity == -1}">
												${op:message('M01497')} <!-- 무제한 -->
											</c:when>
											<c:otherwise>
												${op:numberFormat(item.stockQuantity)}개
											</c:otherwise>
										</c:choose>
									</td>
									
									<td>${displayFlagText}</td>
									
									<td>
										<c:choose>
											<c:when test="${itemParam.orderBy == 'HITS'}">
												${op:numberFormat(item.hits)}
											</c:when>
											<c:otherwise>
												${op:date(item.createdDate)}
											</c:otherwise>
										</c:choose>
									</td>
									<td>${empty item.mdName ? '-' : item.mdName}</td>
									<td>
										<c:if test="${itemSaleStatusText == '판매중'}">
											<a href="javascript:Link.view('/seller/item/sale/edit/${item.itemId}')" class="btn btn-gradient btn-xs">가격변경</a><br />
										</c:if>
										<a href="/seller/item/copy/${item.itemId}" class="btn btn-gradient btn-xs">${op:message('M00788')}</a><br />
										<a href="#" class="delete_item btn btn-gradient btn-xs">${op:message('M00074')}</a> <!-- 삭제 -->
									</td>
								</tr>
							</c:forEach>
								 
							</tbody>
						</table>
					</form>
					
					<c:if test="${empty list}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
					</c:if>
					
					
					
					<div class="btn_all">
						<div class="btn_left mb0">
	
						</div>
						<div class="btn_right mb0">
							<a href="/seller/item/create" class="btn btn-active btn-sm">${op:message('M00773')}</a> <!-- 상품등록 -->
							
						</div>
					</div>
					
					<page:pagination-manager />
					
				</div> <!-- // board_list -->

				<div class="reject-message-layer popup_wrap">
					<h1 class="popup_title">등록보류</h1>
					
					<div class="popup_contents">
						<p>등록보류 사유입니다. 상품 정보를 수정해 주세요.</p>
						<div id="reject-message-wrap"></div>
						
						<div class="buttons">
							<button type="button" class="close-reject-message btn btn-active btn-sm">확인</button>
						</div>
					</div>
					<a href="#" class="popup_close close-reject-message">창 닫기</a>
				</div>
			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>	


<page:javascript>		
<script type="text/javascript">
$(function() {
	$('.show-reject-message').on('click', function(e) {
		e.preventDefault();
		var rejectMessage = $(this).closest('tr').find('.data-status-message').html();
		$('#reject-message-wrap').html(rejectMessage);
		$('.reject-message-layer').show();
	});
	
	$('.close-reject-message').on('click', function() {
		$('#reject-message-wrap').empty();
		$('.reject-message-layer').hide();
	});
	
	if (isChangingOrdering()) {
		// 관련상품 drag sortable
	    $( ".sortable" ).sortable({
	        placeholder: "sortable-placeholder"
	    });
	    $( ".sortable" ).disableSelection(); 
	    
	    $('#change_ordering, #change_ordering2').css({'background-color': '#25a5dc', 'border': '1px solid #25a5dc'});
	} else {
		$('#change_ordering, #change_ordering2').css({'background-color': '#c34e00', 'border': '1px solid #c34e00'});
	}
	
	$('#orderBy, #sort, #itemsPerPage').on("change", function(){
		$('#sort option').eq(0).prop("disabled", false);
		if ($(this).val() == 'ORDERING') {
			$('#sort').val("ASC").find('option').eq(0).prop("disabled", true);
		}
		
		$('#itemParam').submit();	
	});
	
	// 상품삭제 
	$('.delete_item').on('click', function(e) {
		e.preventDefault();
		$('#check_all').prop("checked", false);
		$(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
		$(this).closest("tr").find('input[name=id]').prop("checked", true);
		
		Common.updateListData("/seller/item/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/seller/item/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 수정처리
	$('#update_list_data').on('click', function() {
		
		var $form = $('#listForm');
		if ($form.find('input[name=id]:checked').size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}
		
		var errors = 0;
		$form.find('input[name=id]:checked').each(function() {
			var $salePrice = $(this).closest('tr').find('input[name=salePrice]');
			if ($.trim($salePrice.val()) == '') {
				alert($.validator.messages['text'].format($salePrice.attr('title')));
				$salePrice.focus();
				errors++;
				return;
			}
		});
		if (errors == 0) {
			Common.updateListData("/seller/item/list/update", Message.get("M00307"));	// 선택된 데이터를 수정하시겠습니까?
		}
	});
	
	// 목록데이터 - 품절처리
	$('#update_list_data_soldout').on('click', function() {
		Common.updateListData("/seller/item/list/update-sold-out", "선택한 상품을 품절로 처리하시겠습니까?");	// 선택된 데이터를 수정하시겠습니까?
	});
	
	// 목록데이터 - 품절해제(재판매)
	$('#update_list_data_soldout').on('click', function() {
		Common.updateListData("/seller/item/list/update-resale", "선택한 상품을 품절해제로 처리하시겠습니까?");	// 선택된 데이터를 수정하시겠습니까?
	});
	
	// 목록데이터 - 품절해제(재판매)
	$('#update_list_data_add_category').on('click', function() {
		var $form = $('#listForm');
		if ($form.find('input[name=id]:checked').size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}
		
		Common.popup('/seller/item/add-items-to-category', 'add-items-to-category', 970, 420);
	});
	
	
	
	
	
	// 목록데이터 - 순서변경 버튼 처리
	$('#change_ordering, #change_ordering2').on('click', function() {
		if (!isChangingOrdering()) {
			if (confirm(Message.get("M00298") + '\n' + Message.get("M00299"))) { // '현재 검색 조건으로는 순서 정렬이 불가능합니다. 순서 변경이 가능한 조건으로 다시 검색하시겠습니까?'
				var orderBy = '${itemParam.orderBy}';
				var totalItems = '${pagination.totalItems}';
				var itemsPerPage = '${itemParam.itemsPerPage}';
				
				// 순서 변경이 가능한 조건인지 체크.
				var $query = $('#query');
				var $soldOutFlag = $('#soldOutFlag1');
				var $displayFlag = $('#displayFlag2');
				var $rankingFlag = $('#rankingFlag1');
				var $priceRange = $('#priceRange');
				var $searchStartDate = $('#searchStartDate');
				var $searchEndDate = $('#searchEndDate');
				var $orderBy = $('#orderBy');
				var $sort = $('#sort');
				var $itemsPerPage = $('#itemsPerPage');
					
				$query.val('');
				$soldOutFlag.prop('checked', true);
				$displayFlag.prop('checked', true);
				$rankingFlag.prop('checked', true);
				$priceRange.val('');
				$searchStartDate.val('');
				$searchEndDate.val('');
				$orderBy.val('ORDERING');
				$sort.val('ASC');
				$itemsPerPage.val('1000');
				
				$('#itemParam').submit();
				
			} 
		} else {
			Common.changeListOrdering('/seller/item/list/change-ordering');
		}
				
	});
	
	// 팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');



});

function shoppingHow() {
	Common.popup("/seller/item/make-shopping-how", 'makeShoppingHow', 500, 300, 1);
}

// 순서변경 가능여부 체크
function isChangingOrdering() {
	var orderBy = '${itemParam.orderBy}';
	var totalItems = '${pagination.totalItems}';
	var itemsPerPage = '${itemParam.itemsPerPage}';
	
	// 순서 변경이 가능한 조건인지 체크.
	var $query = $('#query');
	var $soldOutFlag = $('#soldOutFlag1');
	var $displayFlag = $('#displayFlag2');
	var $rankingFlag = $('#rankingFlag1');
	var $priceRange = $('#priceRange');
	var $searchStartDate = $('#searchStartDate');
	var $searchEndDate = $('#searchEndDate');
	var $orderBy = $('#orderBy');
	var $sort = $('#sort');
	var $itemsPerPage = $('#itemsPerPage');
	
	if ($soldOutFlag.prop('checked') == false 
			|| $displayFlag.prop('checked') == false 
			|| $rankingFlag.prop('checked') == false 
			|| $query.val() != ''
			|| $priceRange.val() != ''
			|| $searchStartDate.val() != ''
			|| $searchEndDate.val() != ''
			|| $orderBy.val() != 'ORDERING' 
			|| $sort.val() != 'ASC' 
			|| Number(totalItems) > Number(itemsPerPage) 
			) {
		
		return false;
	}
	return true;
}

function deferredResult() {
	$.post("/seller/item/deferred-result", {}, function(text) {
		alert(text);
	}, 'text');
}

//엑셀 다운로드 팝업.
function downloadExcel() {
	var deliveryType = '${itemParam.deliveryType}';
	
	if (deliveryType == '') {
		alert('배송구분을 검색후 다운로드하세요.');
		return false;
	}
	
	var parameters = $('#itemParam').serialize();
	Common.popup('/opmanager/item/download-excel?' + parameters, 'download-excel', 580, 670, 0);
}

// 엑셀 업로드
function uploadExcel() {
	Common.popup('/opmanager/item/upload-excel', 'upload-excel', 600, 550, 0);
}

function updateListDataDisplay(flag) {
	
	if ($('#listForm').find('input[name=id]:checked').size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	} else {
		Common.updateListData("/seller/item/list/update-display/" + flag, "선택된 데이터를 수정하시겠습니까?");		
	}
}

function updateListDataLabel(flag) {
	
	if ($('#listForm').find('input[name=id]:checked').size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	} else {
		Common.updateListData("/seller/item/list/update-label/" + flag, "선택된 데이터를 수정하시겠습니까?");		
	}
}

function findMd(targetId) {
	Common.popup('/opmanager/seller/find-md?targetId=' + targetId, 'find_md', 720, 800, 1);
}

function clearMd(targetId) {
	var $target = $('#' + targetId);
	$target.val('');
	$target.closest('td').find('#mdName').val('');
}

// MD 검색 콜백 
function handleFindMdCallback(response) {
	var $target = $('#' + response.targetId);
	$target.val(response.userId);
	$target.closest('td').find('#mdName').val(response.userName);

}
</script>
</page:javascript>
