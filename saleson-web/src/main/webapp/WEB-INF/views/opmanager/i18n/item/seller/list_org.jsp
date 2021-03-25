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
				<h3><span>상품리스트</span></h3>	
				
				<form:form modelAttribute="itemParam" method="post">
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
								<tr>
									<td class="label">${op:message('M00784')} <!-- 가격 --></td>
									<td>
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
									<td class="label">판매자</td>
									<td>
										<div>
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
										</div>
										
									</td>
								</tr>
								
							</tbody>
						</table>
						
					</div> <!-- // board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/item/list';">${op:message('M00047')}</button> <!-- 초기화 -->
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
						<input type="hidden" id="processType" name="processType" />
						
						<table class="board_list_table" summary="전체상품리스트">
							<caption>전체상품리스트</caption>
							<colgroup>
								<col style="width: 30px" />
								<col style="width: 60px;" />
								<col style="width: 70px;" />
								<col style="width: 60px;" />
								<col style="" />
								<col style="width: 70px" />
								<col style="width: 70px" />
								<col style="width: 70px;" />
								<col style="width: 60px;" />
								<col style="width: 60px;" />
								
								<col style="width: 100px;" />
								<col style="width: 120px;" />
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="check_all" title="체크박스" /></th>
									<th>${op:message('M00200')}</th> <!-- 순번 -->
									<th>${op:message('M00787')} <!-- 판매상태 --></th>
									<th>${op:message('M00752')}</th> <!-- 이미지 -->
									<th>${op:message('M00018')}(${op:message('M00783')})</th> <!-- 상품명 --> <!-- 상품코드 -->
									<th>옵션유무</th>
									<th>${op:message('M00786')} <!-- 판매가격 --></th>
									<th>${op:message('M01462')} <!-- 재고수 --></th>
									<th>${op:message('M00191')} <!-- 공개유무 --></th>
									<th>INDEX</th>
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
									<c:when test="${item.dataStatusCode == '20' || item.dataStatusCode == '21' || item.dataStatusCode == '30' || item.dataStatusCode == '31' || item.dataStatusCode == '40' || item.dataStatusCode == '41'}">
										<c:set var="itemSaleStatusText">등록대기</c:set>
									</c:when>
									<c:when test="${item.dataStatusCode == '90'}">
										<c:set var="itemSaleStatusText">판매종료</c:set>
									</c:when>
									<c:when test="${item.itemSoldOutFlag == 'Y'}">
										<c:set var="itemSaleStatusText"><span style="color:#e84700">${op:message('M00693')}</span></c:set>	 <!-- 입하대기 -->
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
									<td>${itemSaleStatusText}</td>
									<td>
										<div>
											<a href="javascript:Link.view('/opmanager/item/seller/edit/${item.itemId}')"><img src="${item.imageSrc}" class="item_image" alt="상품이미지" /></a>
										</div>
									</td>
									<td class="left break-word">
										<a href="javascript:Link.view('/opmanager/item/seller/edit/${item.itemId}')" class="break-word">${item.itemName}</a>
										<a href="javascript:Link.view('/opmanager/item/seller/edit/${item.itemId}', 1)" class="break-word" style="color:#517BAB">[새탭]</a>
										<br/>(${item.itemUserCode})
									</td>
									
									<td>${item.itemOptionFlag}</td>
									<td>${op:numberFormat(item.salePrice)}원</td>
									<td>
										<c:choose>
											<c:when test="${item.stockQuantity == -1}">
												${op:message('M01497')} <!-- 무제한 -->
											</c:when>
											<c:otherwise>
												${op:numberFormat(item.stockQuantity)}
											</c:otherwise>
										</c:choose>
									</td>
									
									<td>${displayFlagText}</td>
									<td>
										${noindexYn}
									</td>
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
									<td>
										<a href="/opmanager/item/copy/${item.itemId}" class="table_btn">${op:message('M00788')}</a> <!-- 복사 -->
										<a href="#" class="delete_item table_btn" style="margin-top:2px;">${op:message('M00074')}</a> <!-- 삭제 -->
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
							<button type="button" id="update_list_data" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-ok"></span> 승인처리</button>
						</div>
						<div class="btn_right mb0">
							<a href="/opmanager/item/create" class="btn btn-active btn-sm">${op:message('M00773')}</a> <!-- 상품등록 -->
						</div>
					</div>
					
					<page:pagination-manager />
					
				</div> <!-- // board_list -->

			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>			
<script type="text/javascript">
$(function() {

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
		$('#processType').val('');
		
		$('#check_all').prop("checked", false);
		$(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
		$(this).closest("tr").find('input[name=id]').prop("checked", true);
		
		Common.updateListData("/opmanager/item/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});

	
	// 목록데이터 - 승인처리
	$('#update_list_data').on('click', function() {
		
		var $form = $('#listForm');
		if ($form.find('input[name=id]:checked').size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}
		
		$('#processType').val('approval');
		Common.updateListData("/opmanager/item/list/update", "선택한 상품을 승인처리 하시겠습니까?");	

	});
	
	
	
	
	
	// 팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');



});

</script>

