<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>

		<!-- 본문 -->
		<div class="popup_wrap">
			
			<h1 class="popup_title">카테고리 추가</h1>
			
			<div class="popup_contents">				
				<h2>¶ 카테고리 추가</h2>		
				
				<form:form modelAttribute="itemParam" method="post">
					<form:hidden path="categoryId" />	
					
					<div class="category_wrap">					
						
						<form:select path="categoryGroupId" class="category multiple" size="12">
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
						
						<form:select path="categoryClass1" class="category multiple" size="12">
						</form:select>
						
						<form:select path="categoryClass2" class="category multiple" size="12">
						</form:select>
						
						<form:select path="categoryClass3" class="category multiple" size="12">
						</form:select>
						
						<form:select path="categoryClass4" class="category multiple" size="12">
						</form:select>
					</div> 
					
					<p class="popup_btns" style="padding-top: 20px;">
						<button type="submit" class="btn btn-active">추가</button>
					</p>
				</form:form>
				
				
				
			</div> <!-- // popup_contents -->
			
			<a href="#" class="popup_close">창 닫기</a>
		</div>
		
	

<script type="text/javascript">
$(function() {
	// 팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	
	Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');

	// 폼 검사.
	$('#itemParam').validator(function() {
		var $openerForm = opener.$('#listForm');
		
		
		if ($openerForm.find('input[name=id]:checked').size() == 0) {
			alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
			self.close();
			return false;
		}
		
		
		
		if ($('#categoryId').val() == '') {
			alert(Message.get("M00309"));	// 상품을 추가할 카테고리를 선택해 주세요.
			
			if ($('#categoryGroupId').val() == 'null' || $('#categoryGroupId').val() == '0') {
				$('#categoryGroupId').focus();
			} else {
				$('#categoryClass1').focus();
			}
			return false;
		}
		
		// 상품정보 추가.
		var appendHtml = '';
		$openerForm.find('input[name=id]:checked').each(function() {
			appendHtml += '<input type="text" name="id" value="' + $(this).val() + '" />'; 
		});
		$('#itemParam').append(appendHtml);

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
			Message.danger("이미 추가한 상품입니다.");
		}
		return;
	}
	
	var $item = $('#item_' + itemId);
	var item = {
		'itemId' : itemId,
		'itemName' : $item.find('.item_names').text(),
		'itemUserCode' : $item.find('.item_user_code').html(),
		'itemImage' : $item.find('.item_image').html(),
		'itemSalePrice' : $item.find('.item_sale_price').text()
	};
	opener.Shop.addRelationItem(targetId, item);
	
	if (display) {
		Message.success("추가하였습니다.");
	}
	
}

// 체크한 상품 일괄 추가
function addCheckedRelationItem() {
	if ($('input[name=id]:checked').size() == 0) {
		alert('추가할 상품을 선택해 주세요.');
		$('#check_all').focus();
		return;
	}
	
	$('input[name=id]').each(function() {
		var itemId = $(this).val();
		addRelationItem(itemId, false);
		
	});
	
	Message.success("추가하였습니다.");
}

</script>		