<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<style>
.scroll-wrap {
	overflow-x: scroll;
}
.scroll-wrap th {
	padding: 8px 3px !important;
}
.scroll-wrap td {
	padding: 3px !important;
}
.scroll-wrap input[type=text], 
.scroll-wrap select {
	font-size: 12px;
}
.scroll-wrap input[type=text] {
	width: 70px;
}
.scroll-wrap input[name=itemNames] {
	width: 250px;
}

.scroll-wrap input[name=supplyPrices], 
.scroll-wrap input[name=salePrices], 
.scroll-wrap input[name=costPrices], 
.scroll-wrap input[name=itemPrices] {
	text-align: right;
}
</style>
			<div class="location">
				<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
			</div>
		
				
			<div class="item_list">			
				<h3><span>상품리스트</span></h3>	
				
				<form:form modelAttribute="itemParam" method="get">
					<form:hidden path="categoryId" />		
						
					<div class="board_write">		
						<table class="board_write_table" summary="상품리스트">
							<caption>상품리스트</caption>
							<colgroup>
								<col style="width: 150px" />
								<col style="width: auto;" /> 
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
									<td>
										<div>
											<form:select path="where" title="상세검색 선택">
												<form:option value="ITEM_NAME">${op:message('M00018')} <!-- 상품명 --></form:option>
												<form:option value="ITEM_USER_CODE">${op:message('M00783')} <!-- 상품코드 --></form:option>
												<form:option value="SELLER_NAME">판매자명</form:option>
											</form:select>
											<form:input path="query" class="w38" title="상세검색 입력" />
										</div>
									</td>
									<td class="label">결과 내 검색</td>
									<td>
										<div>
											<form:select path="addWhere" title="상세검색 선택">
												<form:option value="ITEM_NAME">${op:message('M00018')} <!-- 상품명 --></form:option>
												<form:option value="ITEM_USER_CODE">${op:message('M00783')} <!-- 상품코드 --></form:option>
												<form:option value="SELLER_NAME">판매자명</form:option>
											</form:select>
											<form:input path="addQuery" style="width:200px" title="결과 내 재 검색어" />
										</div>
									</td>
								</tr>
								<tr>
									
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
									<td class="label">${op:message('M00787')} <!-- 판매상태 --></td>
									<td>
										<div>
											<p>
												<form:radiobutton path="saleStatus" value="" label="${op:message('M00039')}" /> <!-- 전체 -->
												<form:radiobutton path="saleStatus" value="sale" label="${op:message('M00694')}" /> <!-- 판매중 -->
												<form:radiobutton path="saleStatus" value="soldOut" label="${op:message('M00693')}" /> <!--  품절 -->
												<form:radiobutton path="saleStatus" value="saleEnd" label="${op:message('M00695')}" /> <!-- 판매종료 -->
											</p>
										</div>
									</td>
									<td class="label">${op:message('M00784')} <!-- 가격 --></td>
									<td >
										<div>
											<form:select path="priceRange" title="가격대 선택">
												<option value="">${op:message('M00039')} <!-- 전체 --></option>
												<form:option value="0|10000" label="0～10000" />
												<form:option value="10000|50000" label="10000～50000" />
												<form:option value="50000|100000" label="50000～100000" />
												<form:option value="100000|1000000" label="100000～1000000" />
												<form:option value="1000000|90000000" label="1000000～" />
											</form:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00191')} <!-- 공개유무 --></td>
									<td>
										<div>
											<p>
												<form:radiobutton path="displayFlag" value="" label="${op:message('M00039')}" /> <!-- 전체 -->
												<form:radiobutton path="displayFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 -->
												<form:radiobutton path="displayFlag" value="N" label="${op:message('M00097')}" /> <!-- 비공개 -->
											</p>
										</div> 
									</td>
									
									<td class="label">${op:message('M00782')} <!-- 상품등록일 --></td>
									<td>
										<div>
											<span class="datepicker"><form:input path="searchStartDate" maxlength="8" class="datepicker" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
											<span class="wave">~</span>
											<span class="datepicker"><form:input path="searchEndDate" maxlength="8" class="datepicker" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
											<span class="day_btns">
												<a href="javascript:;" class="btn_date clear">전체</a> 
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
								<tr>
									<td class="label">배송구분</td>
									<td>
										<div>
											<form:radiobutton path="deliveryType" value="" label="${op:message('M00039')}" checked="checked" /> <!-- 전체 -->
											<form:radiobutton path="deliveryType" value="1" label="본사배송" />
											<form:radiobutton path="deliveryType" value="2" label="업체배송" />
										</div>
									</td>
									<td class="label">반품/교환 구분</td>
									<td>
										<div>
											<form:radiobutton path="shipmentReturnType" value="" label="${op:message('M00039')}" checked="checked" /> <!-- 전체 -->
											<form:radiobutton path="shipmentReturnType" value="1" label="본사반송" />
											<form:radiobutton path="shipmentReturnType" value="2" label="업체반송" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">브랜드</td>
									<td>
										<div>
											<form:select path="brand">
												<form:option value="" label="선택" />
												<c:forEach items="${brandList}" var="brand">
													<c:if test="${brand.displayFlag == 'Y'}">
														<form:option value="${brand.brandName}" label="${brand.brandName}" />
													</c:if>
												</c:forEach>
											</form:select>
										</div> 
									</td>
									
									<td class="label">판매자</td>
									<td>
										<div>
											<select name="sellerId" id="sellerId" title="${op:message('M01630')}"> <!-- 판매자선택 --> 
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
											<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</a>
										</div>
										
									</td>
									
									
								</tr>
								<tr>
									
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
								</tr>
							</tbody>
						</table>
						
					</div> <!-- // board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/item/simple/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
						</div>
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
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
								<form:option value="1500" label="1500" />
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
						<input type="hidden" id="processType" name="processType" />
						<div class="scroll-wrap">
							<table class="board_list_table" summary="전체상품리스트">
								<caption>전체상품리스트</caption>
								<thead>
									<tr>
										<th><input type="checkbox" id="check_all" title="체크박스" /></th>
										<th>${op:message('M00200')}</th> <!-- 순번 -->
										<th>보기</th>
										<th>${op:message('M00783')}</th> <!-- 상품코드 -->
										<th>${op:message('M00018')}</th> <!-- 상품명 --> 
										<%-- <th>원가</th> --%>
										<th>정가</th>
										<th>판매가</th>
										<th>판매자</th>
										<th>제조사</th>
										<th>원산지</th>
										<th>품절 <input type="checkbox" id="soldOutAll" /></th>
										<th>게시 <input type="checkbox" id="displayFlagAll" /></th>
										<th>${op:message('M00018')}</th>
									</tr>
								</thead>
								<tbody>
								
								<c:forEach items="${list}" var="item" varStatus="i">
			
									<tr>
										<td>
											<div style="width: 30px; text-align: center">
												<input type="checkbox" name="id" value="${item.itemId}" class="${item.itemUserCode}" title="" />
											</div>									
										</td>
										<td>
											<c:choose>
												<c:when test="${itemParam.orderBy == 'ORDERING' && itemParam.sort == 'ASC'}">
													${pagination.number + i.count}
												</c:when>
												<c:otherwise>
													${pagination.itemNumber - i.count}
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<div style="width: 45px">
												<c:choose>
													<c:when test='${op:property("saleson.view.type") eq "api"}'>
														<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
													</c:when>
													<c:otherwise>
														<a href="/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
														<a href="/m/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="" /></a>
													</c:otherwise>
												</c:choose>
											</div>
										</td>
										<td>
											${item.itemUserCode}
											<input type="hidden" name="itemUserCodes" value="${item.itemUserCode}" />
										</td>
										<td class="left break-word">
											<input type="text" name="itemNames" value="${item.itemName}" class="required" title="상품명" />
										</td>
										<%-- <td>
											<input type="text" name="costPrices" value="${item.costPrice}" class="required _number_comma" title="원가" />
										</td> --%>
										<td> 
											<input type="text" name="itemPrices" class="_number_comma" value="${item.itemPrice}" />
										</td>
										<td>
											<input type="text" name="salePrices" value="${item.salePrice}" class="required _number_comma" title="판매가"  />
										</td>
										<td>
											<div style="width: 120px" class="text-center">
												${item.sellerName}
											</div> 
										</td>
										<td>
											<input type="text" name="manufacturers" value="${item.manufacturer}" title="제조사" />
										</td>
										<td>
											<input type="text" name="originCountries" value="${item.originCountry}" title="원산지" />
										</td>
										<td style="padding: 3px 25px !important;">
											<input type="hidden" name="soldOuts" value="${item.soldOut == '1' ? '1' : '0'}" />
											<input type="checkbox" class="sold-out-checkbox" ${op:checked('1', item.soldOut)}  style="margin-left: 0"/>
											
										</td>
										<td style="padding: 3px 25px !important;">
											<input type="hidden" name="displayFlags" value="${item.displayFlag == 'Y' ? 'Y' : 'N'}" />
											<input type="checkbox" class="display-flag-checkbox" ${op:checked('Y', item.displayFlag)}  style="margin-left: 0"/>
										</td>
										<td class="left">
											<div style="width: 350px; height: 21px; padding: 3px 5px; overflow: hidden;">
											${item.itemName}
											</div>
										</td>
									</tr>
								</c:forEach>
									 
								</tbody>
							</table>
						</div>
					</form>
					
					<c:if test="${empty list}">
					<div class="no_content">
						${op:message('M00473')} <!-- 데이터가 없습니다. -->
					</div>
					</c:if>
					
					
					
					<div class="btn_all">
						<div class="btn_left mb0">
							<button type="button" id="update_list_data" class="btn btn-default btn-sm">선택수정</button>
						</div>
						<div class="btn_right mb0">
							<a href="/opmanager/item/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00773')}</a> <!-- 상품등록 -->
						</div>
					</div>
					
					<page:pagination-manager />
					
				</div> <!-- // board_list -->
				
				
				
				<div class="board_guide ml10">
					<p class="tip">Tip</p>
					<p class="tip">${op:message('M01414')}</p> <!-- 상품순서 변경은 1차카테고리까지 선택 후 검색한 뒤 -> 기본값 정렬옵션이 정렬.오름차순 -> 순서변경 클릭 -> 상품 드래그 -> 순서변경 -->
					<p class="tip">품절해지, 판매종료처리 해지는 상세페이지에서 가능합니다.</p> 
				</div>
				
			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>			
<script type="text/javascript">
$(function() {
	// 숫자 컴마.
	Common.addNumberComma();
		
	// 판매 전체 선택.
	$('#pdRtGubnAll').on('click', function() {
		if ($(this).prop('checked')) {
			$('.pd-rt-gubn-checkbox').prop('checked', true);
			$('input[name=pdRtGubns]').val('1');
		} else {
			$('.pd-rt-gubn-checkbox').prop('checked', false);
			$('input[name=pdRtGubns]').val('0');		
		}
		$('input[name=id]').prop('checked', true);
	});
	
	// 품절 전체 선택.
	$('#soldOutAll').on('click', function() {
		if ($(this).prop('checked')) {
			$('.sold-out-checkbox').prop('checked', true);
			$('input[name=soldOuts]').val('1');
		} else {
			$('.sold-out-checkbox').prop('checked', false);
			$('input[name=soldOuts]').val('0');		
		}
		$('input[name=id]').prop('checked', true);
	});
	
	// 게시 전체 선택.
	$('#displayFlagAll').on('click', function() {
		if ($(this).prop('checked')) {
			$('.display-flag-checkbox').prop('checked', true);
			$('input[name=displayFlags]').val('Y');
		} else {
			$('.display-flag-checkbox').prop('checked', false);
			$('input[name=displayFlags]').val('N');		
		}
		$('input[name=id]').prop('checked', true);
	});
	
	// 판매구분 체크박스 이벤트
	$('.pd-rt-gubn-checkbox').on('click', function() {
		var $target = $(this).siblings('input[name=pdRtGubns]');
		
		if ($(this).prop('checked')) {
			$target.val('1');
		} else {
			$target.val('0');
		}
	});
	
	// 품절구분 체크박스 이벤트
	$('.sold-out-checkbox').on('click', function() {
		var $target = $(this).siblings('input[name=soldOuts]');
		
		if ($(this).prop('checked')) {
			$target.val('1');
		} else {
			$target.val('0');
		}
	});
	
	// 게시구분 체크박스 이벤트
	$('.display-flag-checkbox').on('click', function() {
		var $displayFlags = $(this).siblings('input[name=displayFlags]');
		
		if ($(this).prop('checked')) {
			$displayFlags.val('Y');
		} else {
			$displayFlags.val('N');
		}
	});
	
	
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
		
		Common.updateListData("/opmanager/item/list/delete", '${op:message("M00306")}');	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/item/list/delete", '${op:message("M00306")}');	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 수정처리
	$('#update_list_data').on('click', function() {
		
		var $form = $('#listForm');
		
		
		if ($form.find('input[name=id]:checked').size() == 0) {
			alert('${op:message("M00308")}');	// 처리할 항목을 선택해 주세요.
			return;
		}
		
		var errors = 0;
			// 숫자 컴마.
		Common.removeNumberComma();
		
		$form.find('input[name=id]:checked').each(function() {
		
			var $requiredSelector = $(this).closest('tr').find('.required');
			
			$requiredSelector.each(function() {
				if (errors == 0) {
					if ($.validator.required($(this)) == false) {
						errors++;
						return false;
					}
					if ($(this).hasClass('_number') || $(this).hasClass('_number_comma')) {
						if ($(this).val() == '') {
							$.validator.validatorAlert($.validator.messages['text'].format($(this).attr('title')), $(this));
							$(this).focus();
							errors++;
							return false;
						}	
					}
				}
			});
		});
		if (errors == 0) {
			$('#processType').val('update-item-simple');
			Common.updateListData("/opmanager/item/list/update", '${op:message("M00307")}');	// 선택된 데이터를 수정하시겠습니까?
		}
		Common.addNumberComma();
		
	});
	
	// 목록데이터 - 품절처리
	$('#update_list_data_soldout').on('click', function() {
		Common.updateListData("/opmanager/item/list/update-sold-out", "선택한 상품을 품절로 처리하시겠습니까?");	// 선택된 데이터를 수정하시겠습니까?
	});
	
	// 목록데이터 - 품절해제(재판매)
	$('#update_list_data_soldout').on('click', function() {
		Common.updateListData("/opmanager/item/list/update-resale", "선택한 상품을 품절해제로 처리하시겠습니까?");	// 선택된 데이터를 수정하시겠습니까?
	});
	
	// 목록데이터 - 품절해제(재판매)
	$('#update_list_data_add_category').on('click', function() {
		var $form = $('#listForm');
		if ($form.find('input[name=id]:checked').size() == 0) {
			alert('${op:message("M00308")}');	// 처리할 항목을 선택해 주세요.
			return;
		}
		
		Common.popup('/opmanager/item/add-items-to-category', 'add-items-to-category', 970, 420);
	});
	
	
	
	// 팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');



});

function shoppingHow() {
	Common.popup("/opmanager/item/make-shopping-how", 'makeShoppingHow', 500, 300, 1);
}


function deferredResult() {
	$.post("/opmanager/item/deferred-result", {}, function(text) {
		alert(text);
	}, 'text');
}


// CSV 다운로드 - 사용안함.
function downloadCsv(type) {
	var $form = $('#itemParam');
	$form.attr('action', '/opmanager/item/download-' + type + '-csv');
	$form.submit();
	$form.attr('action', '/opmanager/item/list');
}

// 엑셀 다운로드 팝업.
function downloadExcel() {
	var parameters = $('#itemParam').serialize();
	Common.popup('/opmanager/item/download-excel?' + parameters, 'download-excel', 580, 670, 0);
}

// 엑셀 업로드
function uploadExcel() {
	Common.popup('/opmanager/item/upload-excel', 'upload-excel', 600, 550, 0);
}

// 아피리츠.
function openApilits() {
	Common.popup('/opmanager/item/apilits', 'apilits', 390, 280, 0);
}

function updateListDataDisplay(flag) {
	
	if ($('#listForm').find('input[name=id]:checked').size() == 0) {
		alert('${op:message("M00308")}');	// 처리할 항목을 선택해 주세요.
		return;
	} else {
		Common.updateListData("/opmanager/item/list/update-display/" + flag, "선택된 데이터를 수정하시겠습니까?");		
	}
}

function updateListDataLabel(flag) {
	
	if ($('#listForm').find('input[name=id]:checked').size() == 0) {
		alert('${op:message("M00308")}');	// 처리할 항목을 선택해 주세요.
		return;
	} else {
		Common.updateListData("/opmanager/item/list/update-label/" + flag, "선택된 데이터를 수정하시겠습니까?");		
	}
}

function sellerSeller(sellerId) {
	$('#sellerId').val(sellerId);
}

function findMd(targetId) {
	Common.popup('/opmanager/seller/find-md?targetId=' + targetId, 'find_md', 720, 800, 1);
}

function clearMd(targetId) {
	var $target = $('#' + targetId);
	$target.val('');
	$target.closest('td').find('#mdName').val('');
}

//MD 검색 콜백 
function handleFindMdCallback(response) {
	var $target = $('#' + response.targetId);
	$target.val(response.userId);
	$target.closest('td').find('#mdName').val(response.userName);

}

</script>
