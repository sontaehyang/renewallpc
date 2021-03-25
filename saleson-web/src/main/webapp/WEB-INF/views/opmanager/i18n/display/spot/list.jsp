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
				<p class="text" style="margin-bottom: 5px;">
					* 스팟 상품은 특정기간, 요일, 시간을 설정하여 일시적으로 세일판매를 하는 상품입니다.<br />
					* 설정된 기간에 설정된 금액만큼 할인되어 판매됩니다.<br />
				</p>
				<form:form modelAttribute="itemParam" method="get">
					<form:hidden path="categoryId" />		
					<form:hidden path="spotWeekDay" /> 
						
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
									<td class="label">상태</td>
									<td>
										<div>
											<p>
												<form:radiobutton path="spotStatus" value="" checked="checked" label="전체" /> <!-- 전체 -->
												<form:radiobutton path="spotStatus" value="1" label="진행중" /> <!-- 판매중 -->
												<form:radiobutton path="spotStatus" value="2" label="대기" /> <!-- 입하대기 -->
											</p>
										</div>
									</td>
									<td class="label">회원구분</td>
									<td>
										<div>
											<p>
											 	<form:select path="spotApplyGroup" id="spotApplyGroup">
													<form:options items="${spotApplyGroups}" itemLabel="groupName" itemValue="groupCode"/>
												</form:select> 
										
											</p>
										</div>
									</td>
								</tr>
								
								<tr>
									<td class="label">상품코드/상품명</td>    <!-- 검색구분 -->
									<td>
										<div>
											<form:input path="query" class="w360" title="상세검색 입력"/> 
										</div>
									</td>
									
									<td class="label">할인가격 부담 주체</td>
									<td>
										<div>
											<p>
												<form:radiobutton path="spotType" value="" checked="checked" label="전체" />
												<form:radiobutton path="spotType" value="1" label="운영사" />
												<form:radiobutton path="spotType" value="2" label="판매자" />
											</p>
										</div>
									</td>
									 
								</tr>
								
							</tbody>
						</table>
						
					</div> <!-- // board_write -->
					
					<div class="btn_all">
						<div class="btn_left">
							<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/display/spot/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
						</div> 
						<div class="btn_right">
							<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
						</div>
					</div>
					
					
				<div class="count_title mt20">
					<h5 >
						${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
					</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
					<span style="float:right;">
					 	${op:message('M00052')} : <!-- 출력수 --> 
						<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
							onchange="$('form#itemParam').submit();">	<!-- 화면 출력수 -->
							<form:option value="10" label="10${op:message('M00053')}" /> <!-- 개 출력 -->
							<form:option value="20" label="20${op:message('M00053')}" /> <!-- 개 출력 -->
							<form:option value="30" label="30${op:message('M00053')}" /> <!-- 개 출력 -->
							<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
							<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
						</form:select> 
					</span>
				</div> 
				</form:form>
				
				<div class="board_list">
					<form id="listForm">
						<table class="board_list_table" id="board_list_table" summary="전체상품리스트">
							<caption>전체상품리스트</caption>
							<colgroup>
								<col style="width: 30px" />
								<col style="width: 60px;" />
								<col style="width: 60px;" />
								<col style="" />
								<col style="width: 100px;" />
								<col style="width: 100px;" />
								<col style="width: 200px;" />
								<col style="width: 100px;" />
								<col style="width: 100px;" />
								<col style="width: 180px;" />
								<col style="width: 100px;" />
								<col style="width: 120px;" />
								<col style="width: 120px;" />
							</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="check_all" title="체크박스" /></th>
								    <%-- <th>${op:message('M00200')}</th> <!-- 순번 --> --%>
									<th>구분</th>
									<th>${op:message('M00752')}</th> <!-- 이미지 -->
									<th>${op:message('M00018')}(${op:message('M00783')})</th> <!-- 상품명 --> <!-- 상품코드 -->
									<%--
									<th>${op:message('M00785')} <!-- 정가 --></th>
									 --%>
									<th>입점사</th>
									<th>브랜드</th>
									<th>기간</th>
									<th>정가/판매가</th>
									<th>기타 할인</th>
									<th>스팟 할인</th>
									<th>할인적용 판매가</th> 
									<th>상태</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody class="sortable">
							
							<c:forEach items="${list}" var="item" varStatus="i">
								<c:set var="spotAreaKey">SPOT-${item.itemId}</c:set>
                                <tr id="${spotAreaKey}"
                                    data-salePrice="${item.salePrice}"
                                    data-commissionRate="${item.commissionRate}">
									<td>
										<input type="checkbox" name="id" data-item-sale-price="${item.exceptSpotDiscount}" value="${item.itemId}" class="${item.itemUserCode}" ${item.spotType == '1' ? '' : 'style="display:none"'} />
									</td>
									<td>
										<c:choose>
											<c:when test='${item.spotDateType eq "1"}'>시점</c:when>
											<c:when test='${item.spotDateType eq "2"}'>기간</c:when>
											<c:otherwise>${item.spotDateType}</c:otherwise>
										</c:choose>
									</td>
									<td>
										<div>
											<a href="javascript:Link.view('/opmanager/item/edit/${item.itemUserCode}')"><img src="${shop:loadImage(item.itemCode, item.itemImage, 'XS')}" class="item_image" alt="상품이미지" /></a>
										</div>
									</td>
									<td class="left break-word">
										
										<a href="javascript:Link.view('/opmanager/item/edit/${item.itemUserCode}')" class="break-word">
											<c:if test="${item.soldOut == '1'}"><strong class="tip">[품절] </strong></c:if>
											<c:if test="${item.displayFlag == 'N'}"><strong class="tip">[비공개] </strong></c:if>
											${item.itemName}
										</a>
										<a href="javascript:Link.view('/opmanager/item/edit/${item.itemUserCode}', 1)" class="break-word" style="color:#517BAB">[새탭]</a>
										
										<br/>(${item.itemUserCode})
										
									</td>
									<td>${item.sellerName}</td>
									<td>${item.brand}</td>
									<td>${op:date(item.spotStartDate)} ~ ${op:date(item.spotEndDate)}</td>
									
									<td class="text-right">
										<c:if test="${not empty item.itemPrice && item.itemPrice != item.salePrice}">
											<span style="text-decoration:line-through;">${op:numberFormat(item.itemPrice)}원</span>
										</c:if>
										<p>${op:numberFormat(item.salePrice)}원</p>
									</td>
									<td class="text-right">${op:numberFormat(item.salePrice - item.exceptSpotDiscount)}원</td>
									
									<td class="text-right">
										<c:choose>
											<c:when test="${item.spotType == '1'}">
												<input name="spotDiscountAmounts" type="text" maxlength="8" title="할인금액재수정" class="_number_comma text-right" style="width:60px" value="${op:numberFormat(item.spotDiscountAmount)}" />
												&nbsp;원&nbsp;
			 									<button type="button" class="btn btn-gradient btn-xs" onclick="javascript:oneDiscount('${spotAreaKey}');"><span>${op:message('M01154')}</span></button> <!-- 적용 -->
											</c:when>
											<c:otherwise>
												<span style="color:red">판매자 부담 할인 ${op:numberFormat(item.spotDiscountAmount)}원</span>
											</c:otherwise> 
										</c:choose>
									</td>
									
									<td class="text-right">${op:numberFormat(item.minusSpotDiscount)}원</td>
									
									<td>
										<c:choose>
											<c:when test="${item.spotItem}">
												<span style="color: #25A5DC">진행중</span><br />
												<c:choose>
													<c:when test="${item.spotEndDDay == 0}">
														(오늘 마감)
													</c:when>
													<c:otherwise>
														(마감 ${item.spotEndDDay}일 전)
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<span style="color: #444">진행예정<br />
													<c:choose>
														<c:when test="${item.spotStartDDay > 0}">
															(D-${item.spotStartDDay})
														</c:when>
														<c:otherwise>
															(요일,시간)
														</c:otherwise>
													</c:choose>
												</span>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:if test="${item.spotType == '1'}">
											<a href="javascript:Link.view('/opmanager/display/spot/edit/${item.itemId}')" class="btn btn-gradient btn-xs">수정</a>
										</c:if>
										<a href="#" class="delete_item btn btn-gradient btn-xs">삭제</a>
									</td>
								</tr>
							</c:forEach>
								 
							</tbody>
						</table>
					
						<c:if test="${empty list}">
						<div class="no_content">
							${op:message('M00473')} <!-- 데이터가 없습니다. -->
						</div>
						</c:if>
						
						<div class="btn_all">
							<div class="btn_left mb0" style="font-size:15px; font-weight:bold;">
								판매가에서&nbsp;
								<input name="discountAmount" type="text" maxlength="8" title="일괄 할인 금액 재수정" style="font-size:15px; font-weight:bold;" />
								<select id="discountWay" name="discountType">
									<option label="원" value="won">원</option>
									<option label="%" value="per">%</option>
								</select> 
								을 할인하여 일괄적으로 수정&nbsp;&nbsp;
								<button type="button" id="all_discount" class="btn btn-default btn-sm"><span>일괄 할인 재 수정</span></button>
								<button type="button" id="delete_list_data" class="btn btn-default btn-sm">선택삭제</button>
							</div>
							<div class="btn_right mb0">
								<a href="/opmanager/display/spot/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 스팟상품등록</a>
							</div>
						</div>
					</form>
					
					<page:pagination-manager />
					
				</div> <!-- // board_list -->

				
			</div>
			
<style>
td {background: #fff;}
.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
opacity: .5;}



</style>			
<script type="text/javascript">

//적용버튼으로 할인
function oneDiscount(key) {
	$tr = $('tr#' + key);
	if ($tr.size() == 0) {
		return;
	}
	
	Common.removeNumberComma(); 
	
	var discountAmount = parseInt($tr.find('input[name="spotDiscountAmounts"]').val());
	var itemSalePrice = parseInt($tr.find('input[name="id"]').data('itemSalePrice'));

    var item = $tr.data();

    var salePrice =  item.saleprice;
    var commissionRate = parseFloat(item.commissionrate);
    var discountLimitRate = parseInt(100 - (commissionRate + 30));

    var spotDiscountAmount = Number($('input[name="spotDiscountAmounts"]').val());
    var discountRate = Math.round(spotDiscountAmount * 100 / salePrice);

    if (discountRate > discountLimitRate) {
        alert('할인 가능 금액은 판매금액의 ' + discountLimitRate + '% 까지만 설정이 가능합니다.\n판매금액 및 할인금액을 확인해 주세요.');
        Common.addNumberComma();
        $tr.find($('input[name="spotDiscountAmounts"]')).focus();
        return false;
    }
	
	if(isNaN(discountAmount)){
		alert("숫자가 아닌 값이 입력되었습니다.");
		$tr.find('input[name="spotDiscountAmounts"]').focus();
		return;
	}else if(discountAmount < 0){
		alert("할인금액이 0원 이하로 설정되었습니다.");
		$tr.find('input[name="spotDiscountAmounts"]').focus();
		return;
	}else if(discountAmount > itemSalePrice){
		alert("판매금액보다 큰 값의 할인값이 설정되었습니다.");
		$tr.find('input[name="spotDiscountAmounts"]').focus();
		return;
	}
	
	var param = {
		"itemId" : $tr.find('input[name="id"]').val(),
		"oneDiscountAmount" : discountAmount
	}
	
	$.post("/opmanager/display/spot/update/one/discount", param ,function(response){
		Common.responseHandler(response, function(){
			location.reload();
		});
	});
}

$(function() {
	
	$("#itemParam").validator(function() {
		var spotWeekDay = '';
		$('input[name=day_of_week]:checked').each(function() {
			spotWeekDay += '' + $(this).val();
		});
		$('#spotWeekDay').val(spotWeekDay);
	})

	//일괄할인
	$('#all_discount').on('click',function() {
		
		var way = $('#discountWay option:selected').val();
		$discount = $('input[name="discountAmount"]');
		$table = $('#board_list_table > tbody');
		if ($table.find('tr').size() == 0) {
			return;
		}
		
		if ($('input[name=id]:checked').size() <= 0) {
			alert("처리하실 항목을 선택해 주세요.");
			return; 
		}
		
		if (isNaN(parseInt($discount.val()))) {
			alert("숫자가 아닌 값이 입력되었습니다.");
			$discount.focus();
			return;
		} else if ($discount.val() < 0) {
			alert("할인금액이 0 이하로 설정되었습니다.");
			$discount.focus();
			return;
		}
		
		if (way == 'per') {
			if($discount.val() > 100) {
				alert("할인률은 100% 이하로 설정 가능합니다.");
				$discount.focus();
				return;
			}
		}
		
		var isError = false;

        Common.removeNumberComma();

		$.each($table.find('tr'), function() {
			if ($(this).find('input[name="id"]').prop('checked') == true) {

                var itemSalePrice = parseInt($(this).find('input[name="id"]').data('itemSalePrice'));
                var discountAmount = parseInt($discount.val());

                var item = $(this).data();

                var salePrice =  item.saleprice;
                var commissionRate = parseFloat(item.commissionrate);
                var discountLimitRate = parseInt(100 - (commissionRate + 30));

                var spotDiscountAmount = Number($('input[name="spotDiscountAmounts"]').val());
                var discountRate = Math.round(discountAmount * 100 / salePrice);

                if (way == 'per') {
                    if (discountLimitRate < discountAmount) {
                        alert('할인 금액이 판매금액의 ' + discountLimitRate + '% 까지만 설정가능한 상품이 존재합니다.');
                        $(this).find('input[name="spotDiscountAmounts"]').focus();
                        isError = true;
                        return false;
                    }
                } else {
                    if (discountRate > discountLimitRate) {
                        alert('할인 금액이 판매금액의 ' + discountLimitRate + '% 까지만 설정가능한 상품이 존재합니다.');
                        $(this).find('input[name="spotDiscountAmounts"]').focus();
                        isError = true;
                        return false;
                    }
                }

                if (itemSalePrice < parseInt($discount.val())) {
                    alert("판매가보다 높은 할인가격의 상품이 존재합니다.");
                    $(this).find('input[name="spotDiscountAmounts"]').focus();
                    isError = true;
                    return false;
                }
			}
		});
		
		if (isError) {
			return;
		}
		
		$.post("/opmanager/display/spot/update/discount", $("#listForm").serialize() ,function(response){
			Common.responseHandler(response, function(){
				location.reload();
			});
		});
	});
	 
	// 상품삭제 
	$('.delete_item').on('click', function(e) {
		e.preventDefault();
		$('#check_all').prop("checked", false);
		$(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
		$(this).closest("tr").find('input[name=id]').prop("checked", true);
		
		Common.updateListData("/opmanager/display/spot/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
	});
	
	// 목록데이터 - 삭제처리
	$('#delete_list_data').on('click', function() {
		Common.updateListData("/opmanager/display/spot/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
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
			Common.updateListData("/opmanager/item/list/update", Message.get("M00307"));	// 선택된 데이터를 수정하시겠습니까?
		}
	});
	
	
	
	Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');



});


function updateListDataDisplay(flag) {
	
	if ($('#listForm').find('input[name=id]:checked').size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	} else {
		Common.updateListData("/opmanager/item/list/update-display/" + flag, "선택된 데이터를 수정하시겠습니까?");		
	}
}

function updateListDataLabel(flag) {
	
	if ($('#listForm').find('input[name=id]:checked').size() == 0) {
		alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
		return;
	} else {
		Common.updateListData("/opmanager/item/list/update-label/" + flag, "선택된 데이터를 수정하시겠습니까?");		
	}
}
</script>

