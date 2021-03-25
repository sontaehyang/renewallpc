<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>메인 관리</span></h3>

<form:form modelAttribute="item" method="post">
	<form:hidden path="spotStartTime" /> 
	<form:hidden path="spotEndTime" /> 
	<form:hidden path="spotWeekDay" /> 
	
	
	<div class="board_write">
		<table class="board_write_table" summary="메인 관리">
			<caption>메인 관리</caption>
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<c:if test="${item.itemId > 0}">
					<tr>
						<td class="label">상품번호</td>
						<td>
							<div id="item-user-code" style="font-size: 18px; color: #000; font-family: verdana;">
								${item.itemUserCode}
							</div>
							
							<input type="hidden" name="spotItemIds" value="${item.itemId}" /> 
						</td>
					</tr>
					<tr>
						<td class="label">상품명</td>
						<td>
							<div>
								${item.itemName}
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">정가 / 판매가</td>
						<td>
							<div>
								<c:if test="${not empty item.itemPrice && item.itemPrice != item.salePrice}">
									<span style="text-decoration:line-through;">${op:numberFormat(item.itemPrice)}원</span>
								</c:if>
								<p>${op:numberFormat(item.salePrice)}원</p>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">기타할인</td>
						<td>
							<div>
								${op:numberFormat(item.salePrice - item.exceptSpotDiscount)}원
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">스팟 할인</td>
						<td>
							<div>
								<input type="text" name="spotDiscountAmounts" value="${op:numberFormat(item.spotDiscountAmount)}" class="required _number_comma" title="특가" /> 원
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">할인적용 판매가</td>
						<td>
							<div>${op:numberFormat(item.presentPrice)}원</div>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="label">기간 *</td>
					<td>
						<div>
							<span class="datepicker"><form:input path="spotStartDate" maxlength="8" class="datepicker required _date" title="세일시작일" /></span>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="spotEndDate" maxlength="8" class="datepicker required _date" title="세일종료일" /></span>
						</div>
					</td>
				</tr>
				
				<tr>
					<td class="label">시간대</td>
					<td>
						<div>
							<form:select path="spotStartHour">
								<c:forEach begin="0" end="23" step="1" var="hour" varStatus="i">
									<form:option value="${hour < 10 ? '0' : ''}${hour}">${hour < 10 ? '0' : ''}${hour}</form:option>
								</c:forEach>
							</form:select> : 
							<form:select path="spotStartMinute">
								<c:forEach begin="0" end="59" step="1" var="minute" varStatus="i">
									<form:option value="${minute < 10 ? '0' : ''}${minute}">${minute < 10 ? '0' : ''}${minute}</form:option>
								</c:forEach>
							</form:select> ~ 
							
							<form:select path="spotEndHour">
								<c:forEach begin="0" end="23" step="1" var="hour" varStatus="i">
									<form:option value="${hour < 10 ? '0' : ''}${hour}">${hour < 10 ? '0' : ''}${hour}</form:option>
								</c:forEach>
							</form:select> : 
							<form:select path="spotEndMinute">
								<c:forEach begin="0" end="59" step="1" var="minute" varStatus="i">
									<form:option value="${minute < 10 ? '0' : ''}${minute}">${minute < 10 ? '0' : ''}${minute}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">요일 *</td>
					<td>
						<div>
							<c:forEach items="${item.spotWeekDayList}" var="code">
								<label style="margin-right: 10px;"><input type="checkbox" name="day_of_week" value="${code.value}" ${code.detail == '1' ? 'checked="checked"' : '' } /> ${code.label}</label>
							
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">회원 *</td>
					<td>
						<div>
							<p>
						 	 	<form:select path="spotApplyGroup">
									<form:options items="${spotApplyGroups}" itemLabel="groupName" itemValue="groupCode"/>
								</form:select> 
							</p>
						</div>
					</td>
				</tr>
				<%-- <c:if test="${item.itemId == 0}">
					<tr>
						<td class="label">특가 상품</td>
						<td>
							<div>
								<p class="mb10">
									<button type="button" id="button_add_relation_item" class="table_btn" onclick="Shop.findItem('spot')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
									<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('spot')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
								</p>
								
								<ul id="spot" class="sortable_item_relation">
									<li style="display: none;"></li>
									
									<c:forEach items="${list}" var="item" varStatus="i">
										<c:if test="${!empty item.itemId}">
											<li id="spot_item_${item.itemId}">
												<input type="hidden" name="spotItemIds" value="${item.itemId}" />
												<p class="image"><img src="${item.imageSrc}" class="item_image size-100 none" alt="상품이미지" /></p>
												<p class="title">[${item.itemUserCode}]<br />${item.itemName}</p>
												
												<span class="ordering">${i.count}</span>
												<a href="javascript:Shop.deleteRelationItem('spot_item_${item.itemId}');" class="delete_item_image"><img src="/content/opmanager_image/icon/icon_x.gif" alt="" /></a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</td>
					</tr>
				</c:if> --%>
			</tbody>
		</table>						 
	</div> <!--// board_write E-->
	<%-- <c:if test="${item.itemId == 0}"> --%>
	<c:if test="${item.itemId == 0}">		
		<div>
			<div class="count_title mt20">
				<h3 style="margin-bottom:0px; margin-top:30px;">
					<span>
						상품목록 &nbsp;
						<button type="button" id="button_add_relation_item" class="btn_gradient" onclick="Shop.findItem('spot')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
					</span>					
					<div class="btn_right mb0" style="font-size:15px; font-weight:bold;">
						판매가에서&nbsp;
						<input id="allDiscountAmounts" maxlength="8" title="일괄 할인 금액" style="font-size:15px; font-weight:bold;" type="text" />
						<select id="discountWay">
							<option label="원" value="won"/>
							<option label="%" value="per"/>
						</select> 
						을 할인하여 일괄적으로 수정&nbsp;&nbsp;
						<button type="button" class="btn btn-dark-gray btn-sm" id="all_discount"><span>일괄 할인</span></button> 
						<button type="button" id="delete_row_data" class="btn btn-dark-gray btn-sm">선택삭제</button>
					</div>
				</h3>
			</div>
		</div>
			
	
		<div class="board_list" style="height:400px; overflow-x:hidden; border-bottom: 1px solid #d5d5d5; width:100%">
			<table class="board_list_table" id="select_goods_table" summary="특가상품리스트">
			
				<caption>상품목록</caption>
				<colgroup>
					<col style="width: 50px" />
					<col style="width: 80px;" />
					<col style="" />
					<col style="width: 100px" />
					<col style="width: 100px" />
					<col style="width: 100px" />
					<col style="width: 100px" />
					<col style="width: 100px" />
					<col style="width: 200px;" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" id="check_all" title="체크박스" name="id"/></th>
						<th>${op:message('M00783')}</th> <!-- 이미지 -->
						<th>${op:message('M00018')}</th> <!-- 상품명 -->
						<th>입점사</th>
						<th>브랜드</th>
						<th>정가/판매가</th>
						<th>기타 할인</th>
						<th>할인적용 판매가</th>
						<th>스팟할인금액</th>
					</tr>
				</thead>
				<tbody class="sortable">
				
				</tbody>
			</table>	
			
			<c:if test="${empty list}">
				<div id="noContent" style="display:inline;">
					<p style="text-align:center; padding-top: 150px;">${op:message('M00473')}</p> <!-- 데이터가 없습니다. -->
				</div>				
			</c:if>
			
		</div>
	</c:if>	
	<%-- </c:if> --%>
	<div class="tex_c mt20">
		<button type="submit" class="btn btn-active">${item.itemId == '0' ? '저장' : '수정'}</button>
		<a href="javascript:Link.list('/opmanager/main-display/spot/list')" class="btn btn-default">${op:message('M00037')}</a>	<!-- 취소 -->
	</div>
</form:form>


<script type="text/javascript">

function deleteRow() {	
	
	$table = $('#select_goods_table > tbody');
	if ($table.find('tr').size() == 0) {
		return false;
	}
	
	if ($('input[name=id]:checked').size() <= 0) {
		return false;
	}
	
	$.each($table.find('tr'), function() {
		if ($(this).find('input[name="id"]').prop('checked') == true) {
			$(this).remove();
		}
	});
	
	if ($table.find('tr').size() == 0) {
		$('#noContent').css('display','inline');
	}
}

$(function(){
		
	//일괄할인
	$('#all_discount').on('click', function(){
		//할인 방식 과 할인금액 설정
		var way = $('#discountWay option:selected').val();
		
		$discount = $('#allDiscountAmounts');
		$table = $('#select_goods_table > tbody');
		if ($table.find('tr').size() == 0) {
			return false;
		}
		
		if ($('input[name=id]:checked').size() <= 0) {
			alert("처리하실 항목을 선택해 주세요.");
			return false;
		}
		
		if (isNaN(parseInt($discount.val()))) {
			alert("숫자가 아닌 값이 입력되었습니다.");
			$discount.focus();
			return false;
		} else if ($discount.val() < 0) {
			alert("할인금액이 0 이하로 설정되었습니다.");
			$discount.focus();
			return false;
		}
		
		if (way == 'per') {
			if($discount.val() > 100) {
				alert("할인률은 100% 이하로 설정 가능합니다.");
				$discount.focus();
				return false;
			}
		}
		
		
		$.each($table.find('tr'), function() {
			if ($(this).find('input[name="id"]').prop('checked') == true) {
				
				var itemSalePrice = parseInt($(this).find('input[name="id"]').data('itemSalePrice'));
				if (itemSalePrice > parseInt($discount.val())) {
					
					var discountAmount = parseInt($discount.val());
					if (way == 'per') {
						discountAmount = (itemSalePrice * discountAmount ) / 100;
					}
					
					$(this).find('input[name="spotDiscountAmounts"]').val(Common.numberFormat(discountAmount));
				} else {
					$(this).find('input[name="spotDiscountAmounts"]').val('');
				}
			}
		});
	});
	
	$('#delete_row_data').on('click', function() {
		
		var result = confirm('선택된 항목을 삭제 하시겠습니까?');
		
		// 삭제 여부 확인
		if(result) {
			deleteRow();
		}
	});

	$("#item").validator(function() {
		var $spotStartDate = $('#spotStartDate');
		var $spotEndDate = $('#spotEndDate');
		var $spotStartTime = $('#spotStartTime');
		var $spotEndTime = $('#spotEndTime');
		
		// 시스템의 년도, 월, 일 설정
		var $systemDate =  new Date();
		var $spotSystemNowYear = $systemDate.getFullYear().toString();
		var $spotSystemNowMonth = ($systemDate.getMonth()+1).toString();
		var $spotSystemNowDay = $systemDate.getDate().toString();
		var $spotSystemNowDate = $spotSystemNowYear + ($spotSystemNowMonth[1] ? $spotSystemNowMonth : '0'+$spotSystemNowMonth[0]) + 
				($spotSystemNowDay[1] ? $spotSystemNowDay : '0'+$spotSystemNowDay[0]);

		
		// 현재날짜 비교
		if($spotSystemNowDate > $spotEndDate.val()) {
			alert('종료일이 현재일 이전으로 설정되었습니다.');
			$spotEndDate.focus();
			return false;
		}

		
		// 기본값 설정
		var spotStartTime = $('#spotStartHour').val() + "" + $('#spotStartMinute').val() + "00"; 
		var spotEndTime = $('#spotEndHour').val() + "" + $('#spotEndMinute').val() + "59"; 
		$spotStartTime.val(spotStartTime);
		$spotEndTime.val(spotEndTime);

		
		
		// 날짜 비교
		if ($spotStartDate.val() > $spotEndDate.val()) {
			alert('시작일이 종료일 이후로 설정되었습니다.');
			$spotStartDate.focus();
			return false;
		}

		// 시간 비교
		if ($spotStartTime.val() > $spotEndTime.val()) {
			alert('시작시간이 종료시간 이후로 설정되었습니다.');
			$('#spotStartHour').focus();
			return false;
		}

		// 요일선택 
		var dayOfWeekSize = $('input[name=day_of_week]:checked').size();
		if (dayOfWeekSize == 0) {
			alert('요일을 선택해 주십시오.');
			$('input[name=day_of_week]').eq(0).focus();
			return false;
		}

		var spotWeekDay = '';
		$('input[name=day_of_week]:checked').each(function() {
			spotWeekDay += '' + $(this).val();
		});
		$('#spotWeekDay').val(spotWeekDay);
		
		// 상품 선택여부 
		if ($('input[name="spotItemIds"]').size() == 0) {
			alert('등록하실 상품을 추가해 주세요.');
			$('#button_add_relation_item').focus();
			return false;
		}else{
			
			$table = $('#select_goods_table > tbody');
			var isError = false;
			$.each($table.find('tr'), function() {
				
				var discountAmount = $(this).find('input[name="spotDiscountAmounts"]').val();
				if ($.trim(discountAmount) == '') {
					alert("할인가가 지정되지 않은 상품이 존재합니다.");
					$(this).find('input[name="spotDiscountAmounts"]').focus();
					isError = true;
					return false;
				}
				
				var itemSalePrice = parseInt($(this).find('input[name="id"]').data('itemSalePrice'));
				if (itemSalePrice < parseInt(discountAmount)) {
					alert("판매가보다 높은 할인가격의 상품이 존재합니다.");
					$(this).find('input[name="spotDiscountAmounts"]').focus();
					isError = true;
					return false;
				}
			});
			
			if (isError) {
				return false;
			}
		}
		
		var message = "등록하시겠습니까?";
		if ($('#item-user-code').size() > 0) {
			message = "수정하시겠습니까?";
		}

		if (!Common.confirm(message)) {
			return false;
		}
		
		Common.removeNumberComma(); 
		
	});
	
	$( window ).scroll(function() {
		//setHeight();
	});
	
	// 관련상품 드레그
    $(".sortable_item_relation").sortable({
        placeholder: "sortable_item_relation_placeholder"
    });

});

function findItemCallback(id, item) { 
	var key = id + '_item_' + item.itemId;

	var ordering = $('input[name=' + id + 'ItemIds]').size() + 1;
	var html = '';
	html += '<tr id="' + key + '">';
	html += '	<input type="hidden" name="spotItemIds" value="'+item.itemId+'"/>';
	html += '	<td><input type="checkbox" name="id" data-item-sale-price="'+ item.exceptSpotDiscount +'" value="' + item.itemId + '" class="'+item.itemUserCode+'"/></td>';
	html += '	<td>'+item.itemUserCode+'</td>';
	html += '	<td>'+item.itemName+'</td>';
	html += '	<td>'+item.sellerName+'</td>'; 
	html += '	<td>'+item.brand+'</td>';
	
	html += '	<td class="text-right">';
	if (item.itemPrice != '' && item.itemPrice != item.itemSalePrice) {
		html += '<span style="text-decoration:line-through;">' + Common.numberFormat(item.itemPrice) + '원' + '</span>';	
	}
	html += '<p>' + Common.numberFormat(item.itemSalePrice) + '원</p>'; 
	html += '	</td>';
	
	html += '<td class="text-right">' + Common.numberFormat(item.itemSalePrice - item.exceptSpotDiscount) + '원</td>';
	html += '<td class="text-right">' + Common.numberFormat(item.minusSpotDiscount) + '원</td>';
	
	html += '	<td class="text-right" id="'+item.itemId+'">';
	html += '		<input name="spotDiscountAmounts" maxlength="8" title="할인금액" class="_number_comma text-right" id="'+item.itemId+'"';
	html += ' style="height: 28px;padding: 3px 6px;line-height: 1.42857143;background-color: #fff;background-image: none; border: 1px solid #ccc; border-radius: 3px;"/>'
	html += '&nbsp;원&nbsp;';
	html += '	</td>';
	html += '</tr>';
	
	$('#noContent').css('display','none');
	$('.sortable').append(html);
	
	setHeight();
}
</script>