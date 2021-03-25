<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>



<!-- MiniColors -->
<script src="/content/modules/jquery/minicolors/jquery.minicolors.js"></script>
<link rel="stylesheet" href="/content/modules/jquery/minicolors/jquery.minicolors.css" />


		<!-- 본문 -->
		<div class="popup_wrap item_color">
			
			<h1 class="popup_title">상품 색상 관리</h1>
			
			<div class="popup_contents">				
				<h2>¶ 상품 색상 등록</h2>	
				<form id="code" name="code" action="" method="post">
					<input type="hidden" name="codeType" id="codeType" value="ITEM_COLOR" />
					<table class="table_color" summary="상품 색상 등록">
						<caption>상품 색상 등록</caption>
						<colgroup>
							<col style="width: 100px" />
							<col style="" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">색상명</td>
								<td>
									<input type="text" name="label"  class="full required" title="색상명" />
								</td>
							</tr>
							<tr>
								<td class="label">색상코드</td>
								<td>
									<input type="text" name="id" maxlength="7" style="height: 27px" class="colorpicker required" title="색상코드" />
								</td>
							</tr>
						</tbody>
					</table>
					
					<div class="btn_all">
						<button type="submit" class="btn btn-dark-gray btn-sm"><span>추가</span></button>
					</div>
				</form>
				
				
				<div class="board_write" style="margin-top: 20px;">					
					<h2>¶ 상품 색상 목록</h2>						
					<table class="board_list_table" summary="상품리스트">
						<caption>상품리스트</caption>
						<colgroup>
							<col style="width: 50px" />
							<col style="width: 80px" />
							<col style="" />
							<col style="width: 60px" />
						</colgroup>
						<thead>
							<tr>
								<th>색상</th>
								<th>코드</th>
								<th>색상명</th>
								<th>추가</th>								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${colors}" var="code">
							<tr id="item">
								<td><span style="background: ${code.value}" class="color_box"></span></td>
								<td>${code.value}</td>
								<td class="title">${code.label}</td>
								<td>
									<a href="javascript:deleteCode('${code.value}')" class="btn_write gray_small">삭제</a>
								</td>
								
							</tr>
							</c:forEach>
							
							
						</tbody>
					</table>
					
					<c:if test="${empty colors}">
					<div class="no_content">
						데이터가 없습니다.
					</div>
					</c:if>
					
				</div> <!--// board_write -->
			</div> 
					
			<a href="#" class="popup_close">창 닫기</a>		
	

<script type="text/javascript">
$(function() {
	$('.colorpicker').minicolors();
	
	$('#code').validator(function() {
		$.post('/opmanager/item/color', $('#code').serialize(), function(response) {
			Common.responseHandler(response, function(){
				var $openerItemColors = opener.$('#item_colors');
				var index = $openerItemColors.find('input[type=radio]').size() + 1;
				
				var appendHtml = '';
				appendHtml += '	<span class="radio_wrap">';
				appendHtml += '		<input type="radio" name="color" id="color_' + index + '" value="' + $('input[name=id]').val() + '" checked="checked">';
				appendHtml += '		<label for="color_' + index + '"><span class="item_color_box" style="background:' + $('input[name=id]').val() + ';"></span> ' + $('input[name=label]').val() + '</label>';
				appendHtml += '	</span>';
				
				$openerItemColors.append(appendHtml);
				
				location.reload();
			});
			
		});
		
		return false;
	});

});

function deleteCode(id) {
	var param = {
		'codeType': $('#codeType').val(),
		'id': id
	};
	
	if (!confirm(Message.get("M00196"))) {		// 삭제하시겠습니까?
		return;
	}
	
	$.post('/opmanager/item/delete-color', param, function(response) {
		Common.responseHandler(response);
		var $openerItemColors = opener.$('#item_colors');
		var $radioButton = $openerItemColors.find("input[value='" + param.id + "']");
		$radioButton.parent().remove();
		
		location.reload();
	});
}

// 체크한 상품 일괄 추가
function addCheckedRelationItem() {
	if ($('input[name=id]:checked').size() == 0) {
		alert('추가할 상품을 선택해 주세요.');
		$('#check_all').focus();
		return;
	}
	
	$('input[name=id]:checked').each(function() {
		var itemId = $(this).val();
		addRelationItem(itemId, false);
		
	});
	
	Message.success("추가하였습니다.");
}

</script>		