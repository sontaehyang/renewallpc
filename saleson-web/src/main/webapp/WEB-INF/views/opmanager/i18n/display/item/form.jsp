 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<style>
.category-list {
	list-style : none;
	margin: 0;
	padding: 0;
}
.category-list li{
	
	width:200px;
	
	border-right: 1px solid #999;
	border-bottom: 1px solid #999;
	
	float: left;
}

.category-list li div{
	margin: 10px;
}

.categories tbody tr{
	border-top: 1px solid #999;
}

.categories tbody tr td.label{
	border-right: 1px solid #999;
}

.category-list li.on{
	background-color: #CCC;
}

.categories tbody tr td.on{
	background-color: #CCC;
}

</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>메인 관리</span></h3>
<p class="text" style="margin-bottom: 5px;">
	* 상품 추가 버튼을 클릭하여 전시할 상품을 추가합니다.<br />
	* 추가된 상품의 전시 순서는 상품을 드레그 하여 변경이 가능합니다.<br />
	* 상품을 추가하거나 순서를 변경한 후 저장 버튼을 클릭하여 반영합니다.
</p>

<form:form modelAttribute="displayItemParam" id="displayItemForm" action="${action}" method="post">

	<form:hidden path="displayGroupCode" />
	<form:hidden path="displaySubCode" />
	<form:hidden path="viewTarget" />
	<form:hidden path="viewType" />

	<c:if test="${displayItemType == 'BANNER'}">
		<div class="board_write">
			<table class="board_write_table" summary="배너 영역 관리">
				<caption>배너 영역 관리</caption>
				<colgroup>
					<col style="width: 150px" />
					<col style="auto;" />
				</colgroup>
				<tbody>
				<tr>
					<td class="label">배너 영역</td>
					<td>
						<div>
							<button type="button" id="button_edit_banner" class="table_btn" onclick="editBanner('${displayItemParam.displayGroupCode}', '${displayItemParam.displaySubCode}')"><span>배너 관리</span></button>
						</div>
					</td>
				</tr>
			</table>
		</div> <!-- // board_write -->
	</c:if>

	<c:if test="${!empty categories}">
		<div class="board_write">
			<table class="board_write_table" summary="카테고리 리스트">
				<caption>카테고리 리스트</caption>
				<colgroup>
					<col style="width: 150px" />
					<col style="auto;" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00270')} <!-- 카테고리 --></td>
						<td>
							<div>
								<table class="board_write_table categories" >
									<colgroup>
										<col style="width: 300px" />
										<col style="auto;" />
									</colgroup>
									<tbody>
										<c:forEach items="${categories}" var="group">
											<tr>
												<c:set var="groupOn" value='${group.url == displayItemParam.displaySubCode ? "on" : ""}'/>
												<td class="label ${groupOn}">

													<c:set var="totalItemCount" value="${totalItemMap[group.url]}"/>
													<c:set var="notDisplayItemCount" value="${notDisplayItemMap[group.url]}"/>
													<c:set var="soldOutItemCount" value="${soldOutItemMap[group.url]}"/>

													<c:if test="${empty totalItemCount }">
														<c:set var="totalItemCount" value="0"/>
													</c:if>

													<c:if test="${empty notDisplayItemCount }">
														<c:set var="notDisplayItemCount" value="0"/>
													</c:if>

													<c:if test="${empty soldOutItemCount}">
														<c:set var="soldOutItemCount" value="0"/>
													</c:if>
													<c:set var="formParam" value='displayGroupCode=${displayItemParam.displayGroupCode}&displaySubCode=${group.url}'/>
													<a href="/opmanager/display/item/${displayItemParam.displayGroupCode}?${formParam}">${group.name}</a><br/>
													(총 : ${totalItemCount}개 | 품절 ${soldOutItemCount}개 | 미노출 ${notDisplayItemCount}개)
												</td>
												<td>
													<ul class="category-list">
														<c:forEach items="${group.categories}" var="category1">

															<c:set var="totalItemCount" value="${totalItemMap[category1.categoryId]}"/>
															<c:set var="notDisplayItemCount" value="${notDisplayItemMap[category1.categoryId]}"/>
															<c:set var="soldOutItemCount" value="${soldOutItemMap[category1.categoryId]}"/>

															<c:if test="${empty totalItemCount}">
																<c:set var="totalItemCount" value="0"/>
															</c:if>

															<c:if test="${empty notDisplayItemCount}">
																<c:set var="notDisplayItemCount" value="0"/>
															</c:if>

															<c:if test="${empty soldOutItemCount}">
																<c:set var="soldOutItemCount" value="0"/>
															</c:if>

															<li class='${category1.categoryId ==  displayItemParam.displaySubCode ? "on" : ""}'>
																<div>
																	<c:set var="formParam" value='displayGroupCode=${displayItemParam.displayGroupCode}&displaySubCode=${category1.categoryId}'/>
																	<a href="/opmanager/display/item/${displayItemParam.displayGroupCode}?${formParam}">${category1.name}</a>
																	<br/>
																	(총 : ${totalItemCount}개 | 품절 ${soldOutItemCount}개 | 미노출 ${notDisplayItemCount}개)
																</div>
															</li>
														</c:forEach>
													</ul>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</td>
					</tr>
			</table>
		</div> <!-- // board_write -->
	</c:if>

	<div class="board_write">
		<table class="board_write_table" summary="상품 관리">

			<caption>상품 관리</caption>
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<tr>
					<td class="label">상품 관리</td>
					<td>
						<div>
							<p class="mb10">
								<button type="button" id="button_add_relation_item" class="table_btn" onclick="findItem()"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
								<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('display')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
							</p>
							
							<ul id="display" class="sortable_item_relation">
								<li style="display: none;"></li>
								
								<c:forEach items="${list}" var="item" varStatus="i">
									<c:if test="${!empty item.itemId}">
										<li id="display_item_${item.itemId}">
											<input type="hidden" name="displayItemIds" value="${item.itemId}" />
											<p class="image"><img src="${shop:loadImage(item.itemCode, item.itemImage, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
											<p class="title">
												<c:choose>
													<c:when test="${item.dataStatusCode == '1'}">
														<c:if test="${item.itemSoldOutFlag == 'Y'}"><strong style="color:red">[품절]</strong></c:if>
														<c:if test="${item.displayFlag == 'N'}"><strong style="color:red">[비공개]</strong></c:if>
													</c:when>
													<c:otherwise>
														<a href="javascript:Manager.itemLog('${item.itemId}')"><strong style="color:red">[비공개]</strong></a>		
													</c:otherwise>
												</c:choose>
												[${item.itemUserCode}] ${item.itemName}

												<!-- 노출용 수량 표시 -->
												<c:if test="${item.displayQuantity > 0}">
													<br/>&#60;노출 수량 ${op:numberFormat(item.displayQuantity)}개&#62;
												</c:if>
											</p>
											
											<span class="ordering">${i.count}</span>
											<a href="javascript:Shop.deleteRelationItem('display_item_${item.itemId}');" class="delete_item_image">
												<img src="/content/opmanager/images/icon/icon_x.gif" alt="" />
											</a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>						 
	</div> <!--// board_write E-->
	
	<div class="tex_c mt20">
		<button type="submit" class="btn btn-active">저장</button>
	</div>
	
</form:form>

<script type="text/javascript">
	$(function(){
		
		$("#displayItemForm").validator(function() {
			
			var confirmMessage = "등록 하시겠습니까?";

			// var isMinItemSizeCheck = true;
			/*if ($('input[name="displayItemIds"]').size() == 0) {
				alert('등록하실 상품을 검색후 추가해 주세요.');
				return false;
			}*/
			
			/*
			if (isMinItemSizeCheck == true) {
				var minItemSize = Number($('input[name="minItemSize"]').val());
				if (minItemSize > 0) {
					if ($('input[name="mainDisplayItemIds"]').size()< minItemSize) {
						alert('최소 ' + minItemSize + '개의 상품을 선택하셔야 합니다.');
						return fas;
					}
				}
			}
			*/
			
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
		//setHeight();
		Shop.findItem('display');
	}

	function editBanner(displayGroupCode, displaySubCode) {
		Common.popup('/opmanager/display/template/' + displayGroupCode + '/' + displaySubCode, displayGroupCode + '-edit-banner', '1000', '700', '1');
	}
</script>