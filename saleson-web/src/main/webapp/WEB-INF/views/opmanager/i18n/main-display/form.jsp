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
<p class="text" style="margin-bottom: 5px;">
	* 상품 추가 버튼을 클릭하여 전시할 상품을 추가합니다.<br />
	* 추가된 상품의 전시 순서는 상품을 드레그 하여 변경이 가능합니다.<br />
	* 상품을 추가하거나 순서를 변경한 후 저장 버튼을 클릭하여 반영합니다.
</p>
<c:if test="${viewType != 'md'}">
	<form:form modelAttribute="mainDisplayItemParam"  method="post">
	
		<div class="board_write">		
			<table class="board_write_table" summary="상품리스트">
				<caption>상품리스트</caption>
				<colgroup>
					<col style="width: 150px" />
					<col style="auto;" /> 
					<col style="width: 150px" />
					<col style="auto;" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00270')} <!-- 카테고리 --></td>
						<td colspan="3">
							<div>
								<form:select path="categoryTeamCode" class="category">
									<c:forEach items="${categoryTeamList}" var="categoriesTeam">
										<c:if test="${categoriesTeam.categoryTeamFlag == 'Y' && categoriesTeam.code != 'sale_outlets'}">
											<form:option value="${categoriesTeam.code}" label="${categoriesTeam.name}" />
										</c:if>
									</c:forEach>
								</form:select>
								
								<button type="submit" class="btn btn-dark-gray btn-sm" style="margin-left: 15px;"><span>${op:message('M00048')}</span></button>	<!-- 검색 -->
							</div>
						</td>
					</tr>
			</table>
			
		</div> <!-- // board_write -->
	</form:form>
</c:if>
<form id="mainDisplayItem" action="/opmanager/main-display/${viewType}" method="post">
	<input type="hidden" name="templateId" value="${ mainDisplayItemParam.templateId }" /> 
	<input type="hidden" name="categoryTeamCode" value="${ mainDisplayItemParam.categoryTeamCode }" />
	<input type="hidden" name="minItemSize" value="${mainDisplayItemParam.minItemSize}" />

	<div style="display:none">
		<input type="radio" id="displayType0" name="displayType" value="0" />
		<input type="radio" id="displayType1" name="displayType" value="1" checked="checked" />
	</div>

	<div class="board_write">
		<table class="board_write_table" summary="메인 관리">

			<caption>메인 관리</caption>
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<!-- 
				<c:choose>
					<c:when test="${viewType == 'new'}">
						<input type="hidden" name="displayType" value="1" />
					</c:when>
					<c:otherwise>
						<tr>
							<td class="label">출력 방법</td>
							<td>
								<div>
									<input type="radio" id="displayType0" name="displayType" value="0" <c:if test="${fn:length(list) == 0}">checked="checked"</c:if> /><label for="displayType0">임의로 출력합니다.</label>
									<input type="radio" id="displayType1" name="displayType" value="1" <c:if test="${fn:length(list) > 0}">checked="checked"</c:if> /><label for="displayType1">선택합니다.</label>
							 	</div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				 -->
				<tr>
					<td class="label">선택 상품</td>
					<td>
						<div>
							<p class="mb10">
								<button type="button" id="button_add_relation_item" class="table_btn" onclick="findItem()"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
								<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('mainDisplay')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
							</p>
							
							<ul id="mainDisplay" class="sortable_item_relation">
								<li style="display: none;"></li>
								
								<c:forEach items="${list}" var="item" varStatus="i">
									<c:if test="${!empty item.itemId}">
										<li id="mainDisplay_item_${item.itemId}">
											<input type="hidden" name="mainDisplayItemIds" value="${item.itemId}" />
											<p class="image"><img src="${item.imageSrc}" class="item_image size-100 none" alt="상품이미지" /></p>
											<p class="title">[${item.itemUserCode}]<br />${item.itemName}</p>
											
											<span class="ordering">${i.count}</span>
											<a href="javascript:Shop.deleteRelationItem('mainDisplay_item_${item.itemId}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
										</li>
									</c:if>
								</c:forEach>

							</ul>
						</div>
					</td>
				</tr>
				
				<c:if test="${viewType == 'best'}">
				
					<tr style="display: none">
						<td class="label">화면출력 옵션</td>
						<td>
							<div>
								<input type="hidden" name="bestItemDisplayType" value="A" />
								
								<!-- 
								<input type="radio" name="bestItemDisplayType" value="A" id="bestItemDisplayTypeA" ${op:checked(teamInfo.bestItemDisplayType, 'A')} /><label for="bestItemDisplayTypeA">A타입</label>
								<input type="radio" name="bestItemDisplayType" value="B" id="bestItemDisplayTypeB" ${op:checked(teamInfo.bestItemDisplayType, 'B')} /><label for="bestItemDisplayTypeB">B타입</label>
								<input type="radio" name="bestItemDisplayType" value="C" id="bestItemDisplayTypeC" ${op:checked(teamInfo.bestItemDisplayType, 'C')} /><label for="bestItemDisplayTypeC">C타입</label>
								<input type="radio" name="bestItemDisplayType" value="D" id="bestItemDisplayTypeD" ${op:checked(teamInfo.bestItemDisplayType, 'D')} /><label for="bestItemDisplayTypeD">D타입</label>
								 -->
							</div>
						</td>
					</tr>
				</c:if>
					 
			</tbody>
		</table>						 
	</div> <!--// board_write E-->
	
	<div class="tex_c mt20">
		<button type="submit" class="btn btn-active">저장</button>
	</div>
</form>


<script type="text/javascript">
	$(function(){
		
		$("#mainDisplayItem").validator(function() {
			
			var confirmMessage = "등록 하시겠습니까?";

			var isMinItemSizeCheck = true;
			
			// 신상품 등록이 아닌경우 출력 타입이 있으므로 검증!
			if ($('input[name="templateId"]').val() != 'new') {
				if ($('input[name="displayType"]:checked').val() == '1') {
					if ($('input[name="mainDisplayItemIds"]').size() == 0) {
						alert('등록하실 상품을 검색후 추가해 주세요.');
						return false;
					}
				} else {
					if ($('input[name="mainDisplayItemIds"]').size() > 0) {
						confirmMessage = "임의로 출력 옵션선택시 선택하신 상품이 등록되지 않습니다.\n\n" + confirmMessage;
					}
					
					isMinItemSizeCheck = false;
				}
			}
			
			if (isMinItemSizeCheck == true) {
				var minItemSize = Number($('input[name="minItemSize"]').val());
				if (minItemSize > 0) {
					if ($('input[name="mainDisplayItemIds"]').size()< minItemSize) {
						alert('최소 ' + minItemSize + '개의 상품을 선택하셔야 합니다.');
						return fas;
					}
				}
			}
			
			if (!Common.confirm(confirmMessage)) {
				return false;
			}
			
		});
		
		$( window ).scroll(function() {
			setHeight();
		});
		
		// 관련상품 드레그
	    $(".sortable_item_relation").sortable({
	        placeholder: "sortable_item_relation_placeholder"
	    });

	});
	
	function findItem(){
		setHeight();
		Shop.findItem('mainDisplay');
	}
</script>