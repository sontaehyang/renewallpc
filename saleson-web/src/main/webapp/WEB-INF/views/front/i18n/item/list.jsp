
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

			<div class="location">
				<a href="#">상품관리</a> &gt;  <a href="#">상품정보</a> &gt; <a href="#" class="on">상품리스트</a>
			</div>
		
				
			<div class="item_list">			
				<h3><span>상품리스트</span></h3>	
				
				<form:form modelAttribute="itemParam" method="post">
					<form:hidden path="categoryId" />			
					<div class="board_write">		
						<table class="board_write_table">
							<caption>상품리스트</caption>
							<colgroup>
								<col style="width: 150px;" />
								<col style="width: auto;" /> 
								<col style="width: 150px;" />
								<col style="width: auto;" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">카테고리선택</td>
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
												<form:option value="ITEM_USER_CODE">상품코드</form:option>
												<form:option value="ITEM_NAME">상품명</form:option>
											</form:select>
											<form:input path="query" class="w360" title="상세검색 입력" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">품절여부</td>
									<td>
										<div>
											<p>
												<form:radiobutton path="soldOutFlag" value="" label="전체" />
												<form:radiobutton path="soldOutFlag" value="Y" label="품절" />
												<form:radiobutton path="soldOutFlag" value="N" label="정상" />
											</p>
										</div>
									</td>
									<td class="label">공개여부</td>
									<td>
										<div>
											<p>
												<form:radiobutton path="displayFlag" value="" label="전체" />
												<form:radiobutton path="displayFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 --> 
												<form:radiobutton path="displayFlag" value="N" label="비공개" />
											</p>
										</div>
									</td>
								</tr>
								


								<tr>
									<td class="label">랭킹상품 노출여부</td>
									<td>
										<div>
											<p>
												<form:radiobutton path="rankingFlag" value="" label="전체" />
												<form:radiobutton path="rankingFlag" value="Y" label="노출" />
												<form:radiobutton path="rankingFlag" value="N" label="비노출" />
											</p>
										</div>
									</td>
									<td class="label">가격대 검색</td>
									<td>
										<div>
											<form:select path="priceRange" title="가격대 선택">
												<option value="">価格</option>
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
									<td class="label">상품등록일</td>
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
							<button type="button" class="btn ctrl_btn" onclick="location.href='/opmanager/item/list';"><span>초기화</span></button>
						</div>
						<div class="btn_right">
							<button type="submit" class="btn ctrl_btn"><span>검색</span></button>
						</div>
					</div>
					
					
					<div class="count_title mt60">
						<h5>
							전체 상품수 : ${op:numberFormat(pagination.totalItems)}
						</h5>
						<span>
							<form:select path="orderBy" title="등록일선택">
								<form:option value="ITEM_ID" label="등록일" />
								
								<c:if test="${!empty itemParam.categoryId}">
								<form:option value="ORDERING" label="정렬" />
								</c:if>
								
								<form:option value="SALE_PRICE" label="판매가격" />
								<form:option value="HITS" label="조회수" />
							</form:select>
							<form:select path="sort" title="검색방법 선택">
								<form:option value="DESC" label="${op:message('M00689')}" />    <!-- 내림차순 -->
								<form:option value="ASC" label="${op:message('M00690')}" />    <!-- 오름차순 -->
							</form:select>
							<span>출력수</span>
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
							<button type="button" id="change_ordering2" class="btn orange" style="position:fixed;right:0; top:470px; z-index: 1000;">순서변경</button>
							</c:if>
						</span>
					</div>
				</form:form>
				
				
				<div class="board_list">
					<form id="listForm">
						<table class="board_list_table" summary="전체상품리스트">
							<caption>전체상품리스트</caption>
							<colgroup>
								<col style="width: 30px" />
								<col style="width: 50px;" />
								<col style="width: 60px;" />
								<col style="" />
								<col style="width: 70px" />
								<col style="width: 70px" />
								<col style="width: 70px;" />
								
								<col style="width: 70px;" />
								<col style="width: 60px;" />
								<col style="width: 50px;" />
								
								<col style="width: 100px;" />
								<col style="width: 70px;" />
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="check_all" title="체크박스" /></th>
									<th>${op:message('M00200')}</th> <!-- 순번 -->
									<th>${op:message('M00752')}</th> <!-- 이미지 --> 
									<th>${op:message('M00018')}(${op:message('M00783')})</th> <!-- 상품명 -->  <!-- 상품코드 --> 
									<th>${op:message('M00785')}</th> <!-- 정가 --> 
									<th>회원가</th>
									<th>비회원가</th>
									<th>${op:message('M00787')}</th> <!-- 판매상태 --> 
									<th>${op:message('M00096')}</th> <!-- 공개 --> 
									<th>No Index</th>
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
									<th>${op:message('M00590')}</th> <!-- 관리 -->		
								</tr>
								 

 
							</thead>
							<tbody class="sortable">
							
							<c:forEach items="${list}" var="item" varStatus="i">
								<c:set var="displayFlagText" value="${op:message('M00096')}" /> <!-- 공개 --> 
								<c:if test="${item.displayFlag == 'N'}">
									<c:set var="displayFlagText"><span style="color:#e84700">${op:message('M00097')}</span></c:set> <!-- 비공개 -->
								</c:if>
								<c:set var="noindexYn" value="N" />
								<c:if test="${item.seoindexFlag == 'N'}">
									<c:set var="noindexYn"><span style="color: #25A5DC">Y</span></c:set>
								</c:if>
								
								<c:choose>
									<c:when test="${item.soldOut == '2'}">
										<c:set var="itemSaleStatusText"><span style="color:#aaa">${op:message('M00692')}</span></c:set> <!-- 판매종료 --> 
									</c:when>
									<c:when test="${item.soldOut == '1' || item.stockQuantity == 0}">
										<c:set var="itemSaleStatusText"><span style="color:#e84700">품절</span></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="itemSaleStatusText" value="${op:message('M00694')}" /> <!-- 판매중 --> 
									</c:otherwise>
								</c:choose>
								
								<tr>
									<td><input type="checkbox" name="id" value="${item.itemId}" title="${op:message('M00169')}" /></td> <!-- 체크박스 --> 
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
										<div>
											<a href="/opmanager/item/edit/${item.itemId}"><img src="${item.imageSrc}" class="item_image" alt="상품이미지" /></a>
										</div>
									</td>
									<td class="left"><a href="/opmanager/item/edit/${item.itemId}">${item.itemName}</a><br/>(${item.itemUserCode})</td>
									<td><input name="itemPrice" class="price" value="${item.itemPrice}" title="정가(시중가)" /></td>
									<td><input name="salePrice" class="price" value="${op:negativeNumberToEmpty(item.salePrice)}" title="판매가" /></td>
									<td><input name="salePriceNonmember" class="price" value="${op:negativeNumberToEmpty(item.salePriceNonmember)}" title="비회원가" /></td>
									<td>${itemSaleStatusText}</td>		
									<td>${displayFlagText}</td>
									<td>${noindexYn}</td>
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
									<td style="width: 70px;">
										<a href="/opmanager/item/copy/${item.itemId}" class="fix_btn">복사</a>
										<a href="#" class="delete_item fix_btn" style="margin-top:2px;">삭제</a>
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
							<button type="button" id="delete_list_data" class="btn gray02">일괄삭제</button>
							<button type="button" id="update_list_data_soldout" class="btn gray02">품절처리</button>
							<button type="button" id="update_list_data_resale" class="btn gray02">품절해제</button>
							<button type="button" id="update_list_data_add_category" class="btn gray02">카테고리 추가</button>
							
							<c:if test="${!empty itemParam.categoryId}">
							<button type="button" id="change_ordering" class="btn orange">순서변경</button>
							</c:if>
						</div>
						<div class="btn_right mb0">
							<a href="/opmanager/item/create" class="ctrl_btn">신규 등록</a>
							<a href="#" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>엑셀 업로드</span> </a>
						</div>
					</div>
					
					<page:pagination />
					
				</div> <!-- // board_list -->
				
			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>	

<page:javascript>		
<script type="text/javascript">
$(function() {
	
	
	if (isChangingOrdering()) {
		// 관련상품 drag sortable
	    $( ".sortable" ).sortable({
	        placeholder: "sortable-placeholder"
	    });
	    $( ".sortable" ).disableSelection(); 
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
		
		Common.updateListData("/opmanager/item/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/item/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 수정처리
	$('#update_list_data').on('click', function() {
		Common.updateListData("/opmanager/item/list/update", Message.get("M00307"));	// 선택된 데이터를 수정하시겠습니까?
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
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			return;
		}
		
		Common.popup('/opmanager/item/add-items-to-category', 'add-items-to-category', 970, 420);
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
				var $displayFlag = $('#displayFlag1');
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
			Common.changeListOrdering('/opmanager/item/list/change-ordering');
		}
				
	});
	
	// 팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');



});

// 순서변경 가능여부 체크
function isChangingOrdering() {
	var orderBy = '${itemParam.orderBy}';
	var totalItems = '${pagination.totalItems}';
	var itemsPerPage = '${itemParam.itemsPerPage}';
	
	// 순서 변경이 가능한 조건인지 체크.
	var $query = $('#query');
	var $soldOutFlag = $('#soldOutFlag1');
	var $displayFlag = $('#displayFlag1');
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
</script>
</page:javascript>
