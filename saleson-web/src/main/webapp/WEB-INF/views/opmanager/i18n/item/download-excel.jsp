<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


<style>
.table_btn {position: absolute; top: 77px; right: 18px;}
</style>


		<div class="popup_wrap">
			<h1 class="popup_title">${op:message('M00254')}</h1> <!-- 엑셀 다운로드 -->
			<div class="popup_contents">
				<h2>¶ ${op:message('M01269')}</h2>	 <!-- 항목선택 -->
				
				<form:form modelAttribute="itemParam" action="${requestContext.managerUri}/item/download-excel" method="post" target="downloadFrame">
				
					<form:hidden path="categoryId" />
					<form:hidden path="categoryGroupId" />	
					<form:hidden path="categoryClass1" />
					<form:hidden path="categoryClass2" />
					<form:hidden path="categoryClass3" />
					<form:hidden path="categoryClass4" />
					<form:hidden path="where" />		
					<form:hidden path="query" />
					<form:hidden path="soldOut" />
					<form:hidden path="priceRange" />
					<form:hidden path="displayFlag" />
					<form:hidden path="searchStartDate" />
					<form:hidden path="searchEndDate" />
					<form:hidden path="orderBy" />
					<form:hidden path="sort" />
					<form:hidden path="sellerId" />
					<form:hidden path="deliveryType" />
					<form:hidden path="shipmentReturnType" />
					<form:hidden path="saleStatus" />
					
					<div class="board_write">
						<table class="board_write_table">
							<caption>상품데이터</caption>
							<colgroup>
								<col style="width:120px;" />
								<col style="width:*;" />
							</colgroup>
							<tbody>
								 <tr>
								 	<td class="label">${op:message('M01277')}</td> <!-- 상품 데이터 -->
								 	<td>
								 		<div>
								 			<p style="padding-bottom: 10px"><label><input type="checkbox" id="check-all" /> ${op:message('M00039')}</label></p> <!-- 전체 -->
								 			<p><label><input type="checkbox" name="excelDownloadData" value="item_main" /> ${op:message('M01270')} (MAIN)</label></p><!-- 상품정보 -->
								 			<p><label><input type="checkbox" name="excelDownloadData" value="item_seo" /> ${op:message('M01270')} (SEO)</label></p><!-- 상품정보 -->
								 			<p><label><input type="checkbox" name="excelDownloadData" value="item_table" /> 상품 정보고시 (테이블)</label></p><!-- 상품상세설명(테이블) -->
								 			<!-- <p><label><input type="checkbox" name="excelDownloadData" value="item_table_mobile" /> ${op:message('M01271')} [모바일]</label>	</p> --> <!-- 상품상세설명(테이블) -->
								 			<p><label><input type="checkbox" name="excelDownloadData" value="item_option" /> ${op:message('M01272')}</label></p><!-- 옵션정보 -->
								 			<p><label><input type="checkbox" name="excelDownloadData" value="item_image" /> ${op:message('M01273')}</label></p><!-- 상품이미지정보 -->
											<p><label><input type="checkbox" name="excelDownloadData" value="item_addition" /> 추가구성상품 정보</label></p><!-- 추가구성상품정보 -->
								 			<p><label><input type="checkbox" name="excelDownloadData" value="item_relation" /> ${op:message('M01276')}</label></p><!-- 관련상품정보 -->
								 			<p><label><input type="checkbox" name="excelDownloadData" value="item_category" /> ${op:message('M01274')}</label></p><!-- 상품카테고리정보 -->
								 			<!-- <p><label><input type="checkbox" name="excelDownloadData" value="item_point" /> ${op:message('M01275')}</label></p> --> <!--상품포인트정보 -->
								 			
								 			<%-- 
									 			<p style="border-top: 1px dotted #ccc; margin: 5px 0; padding: 5px 0;"><label><input type="checkbox" name="excelDownloadData" id="item_check" value="item_check" /> ${op:message('M01287')}</label>	</p> <!-- 상품 선택 정보 -->
									 			<p style="border-top: 1px dotted #ccc; padding-top: 5px;"><label><input type="checkbox" name="excelDownloadData" id="item_keyword" value="item_keyword" /> ${op:message('M01291')}</label>	</p>  <!-- 사이트 내 검색어 -->
											 --%>
										</div>
								 	</td>	
								 </tr>
								 <tr>
								 	<td class="label">${op:message('M01278')}</td> <!-- 상품 번호 지정 -->
								 	<td>
								 		<div>
								 			<p class="tip">- ${op:message('M00771')} <!-- 상품번호를 입력하는 경우 입력한 상품데이터를 다운로드합니다. --></p>
								 			<p class="tip">- 상품 번호는 공간 (전체 / 반 가능), 또는 개행으로 구분</p>		<!-- [번역] 상품 번호는 공간 (전체 / 반 가능), 또는 개행으로 구분 -->
											<textarea id="excelItemUserCode" name="excelItemUserCode" rows="8" class="full" style="margin-top: 5px;"></textarea> 
										</div>
								 	</td>	
								 </tr>
							</tbody>					 
						</table>								 							
					</div> <!-- // board_write -->
					
					<p class="popup_btns">
						<button type="submit" class="btn btn-active"><span>${op:message('M01279')}</span></button> <!-- 다운로드 -->
						<button type="button" class="table_btn" onclick="downloadSample()"><span>${op:message('M01280')}</span></button> <!-- 샘플 다운로드 -->
					</p> 
			 
				</form:form>
			</div>
			
			<a href="#" class="popup_close">창 닫기</a>
		</div>

		<iframe id="downloadFrame" name="downloadFrame" style="display: none;"></iframe>

<script type="text/javascript">	
$(function() {
	// 부모창 체크된 상품 정보 가져오기.
	bindOpenerCheckedItems();
	
	// 전체 선택 
	$('#check-all').on('click', function() {
		if ($(this).prop('checked')) {
			$('input[name=excelDownloadData]').prop('checked', true);
		} else {
			$('input[name=excelDownloadData]').prop('checked', false);
		}
		$('#item_check').prop('checked', false);
		$('#item_keyword').prop('checked', false);
	});
	
	$('input[name=excelDownloadData]').not('#item_check, #item_keyword').on('click', function() {
		$('#item_check').prop('checked', false);
		$('#item_keyword').prop('checked', false);
	});
	
	// '#item_check'
	$('#item_check').on('click', function() {
		if ($(this).prop('checked')) {
			$('input[name=excelDownloadData]').not('#item_check').prop('checked', false);
			$('#check-all').prop('checked', false);
		} 
	});
	
	$('#item_keyword').on('click', function() {
		if ($(this).prop('checked')) {
			$('input[name=excelDownloadData]').not('#item_keyword').prop('checked', false);
			$('#check-all').prop('checked', false);
		} 
	});
	
		
	
	$('#itemParam').validator(function() {
		var checkedCount = $('input[name=excelDownloadData]:checked').size();
		if (checkedCount == 0) {
			alert(Message.get("M00770"));	// 다운로드할 데이터 항목을 선택해 주십시오.
			Common.loading.hide();
			$('input[name=excelDownloadData]').eq(0).focus();
			return false;
		}
		
		$.cookie('DOWNLOAD_STATUS', 'in progress', {path:'/'});
		checkDownloadStatus();
	});
});


// 다운로드 체크
function checkDownloadStatus() {
     if ($.cookie('DOWNLOAD_STATUS') == 'complete') {
    	 $('input[type=checkbox]').prop('checked', false);
    	 $('#excelItemUserCode').val("");
     	Common.loading.hide();
		return;
     } else {
		setTimeout("checkDownloadStatus()", 1000);
     }
}

// 엑셀 양식 다운로드.
function downloadSample() {
	var checkedCount = $('input[name=excelDownloadData]:checked').size();
	if (checkedCount == 0) {
		alert(Message.get("M00770"));	// 다운로드할 데이터 항목을 선택해 주십시오.
		Common.loading.hide();
		$('input[name=excelDownloadData]').eq(0).focus();
		return;
	}
	
	$('#excelItemUserCode').val("Download excel sample!");
	$('.btn btn-active').click();
}

//부모창 체크된 상품 정보 가져오기.
function bindOpenerCheckedItems() {
	var $itemIds = opener.$('input[name=id]:checked');
	if ($itemIds.size() > 0) {
		var itemUserCodes = '';
		$itemIds.each(function(i) {
			itemUserCodes += i == 0 ? '' : '\n';
			itemUserCodes += $(this).attr('class');
		});
		
		$('#excelItemUserCode').val(itemUserCodes);
	}
}
</script>