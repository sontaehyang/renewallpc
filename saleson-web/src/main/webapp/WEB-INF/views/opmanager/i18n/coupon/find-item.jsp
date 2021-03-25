<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<!-- 본문 -->
	<div class="popup_wrap">
		
		<h1 class="popup_title">${op:message('M01184')}</h1> <!-- 쿠폰발급상품등록(특정상품) -->
		
		<div class="popup_contents">			
			<form:form modelAttribute="coupon" method="get">
				<div class="board_write">					
					<p class="pop_tit">${op:message('M01185')}</p>				<!-- 상품검색 --> 		
					<table class="board_write_table" summary="관련상품등록">
						<caption>${op:message('M01185')}</caption>
						<colgroup>
							<col style="width: 20%;" />
							<col style="width: 80%;" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">${op:message('M00270')}</td> <!-- 카테고리 -->
								<td>
									<div>
										<form:select path="searchCouponTargetItem.categoryGroupId" id="categoryGroupId" class="category">
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
										
										<form:select path="searchCouponTargetItem.categoryClass1" id="categoryClass1" class="category">
										</form:select>
										
										<form:select path="searchCouponTargetItem.categoryClass2" id="categoryClass2" class="category">
										</form:select>
										
										<form:select path="searchCouponTargetItem.categoryClass3" id="categoryClass3" class="category">
										</form:select>
										
										<form:select path="searchCouponTargetItem.categoryClass4" id="categoryClass4" class="category">
										</form:select>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
								<td>
									<div>
										<form:select path="searchCouponTargetItem.where" title="${op:message('M01186')}"> <!-- 상세검색 선택 -->
											<form:option value="ITEM_NAME">${op:message('M00018')}</form:option> <!-- 상품명 -->
											<form:option value="ITEM_USER_CODE">${op:message('M00783')}</form:option> <!-- 상품코드 -->
										</form:select>
										<form:input path="searchCouponTargetItem.query" class="w360" title="${op:message('M01140')}" /> <!-- 상세검색 입력 -->
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					
				</div> <!--// board_write -->
			
				<div class="btn_all">
					<div class="btn_left">
						<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='item-list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
					</div>
					<div class="btn_right">
						<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
					</div>
				</div>
				
				<div class="btn_all">
					<div class="btn_left mb0">
					
					</div>
					<div class="btn_right mb0">
						<a href="#" id="addAllItems" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 검색상품 전체추가</a>
					</div>
				</div>
				
				<div class="count_title mt20">
					<h5>전체 상품수 : <span id="userCount"style="display:inline-block;color:black;">${totalCount}</span> /&nbsp;<span class="userCount" style="font-weight:bold;display:inline-block;">체크한 상품수 : 0개</span></h5>
					
					<span style="float:right;">
						<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
							onchange="$('form#coupon').submit();"> <!-- 화면 출력수 -->
							<form:option value="10" label="10${op:message('M00053')}" /> <!-- 개 출력 -->
							<form:option value="30" label="30${op:message('M00053')}" /> <!-- 개 출력 -->
							<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
							<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
						</form:select>
					</span>
				</div>
			</form:form>
			
			
			<div class="board_write">					
				<table class="board_list_table" summary="${op:message('M01187')}">
					<caption>${op:message('M01187')}</caption>
					<colgroup>
						<col style="width: 8%;" />
						<col style="width: 16%;" />
						<col style="width: auto;" />
						<col style="width: 22%;" />
						<col style="width: 19%;" />
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" id="allCheck" title="${op:message('M00164')}" /></th> <!-- 전체 선택 --> 
							<th>${op:message('M00752')}</th> <!-- 이미지 -->
							<th>${op:message('M00018')}</th> <!-- 상품명 -->
							<th>${op:message('M00783')}</th> <!-- 상품코드 -->
							<th>${op:message('M00420')}</th> <!-- 상품가격 -->					
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item" varStatus="i">
							<tr>
								<td><input type="checkbox" name="itemId" value="${item.itemId}" title="${op:message('M00169')}" /></td> <!-- 체크박스 -->
								<td>
									<div>
										<img src="${item.imageSrc}" class="item_image" alt="${op:message('M00659')}" /> <!-- 상품이미지 -->
									</div>
								</td>
								<td class="tleft">${item.itemName}</td>
								<td>${item.itemUserCode}</td>
								<td>${op:numberFormat(item.salePrice)}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div> <!--// board_write -->
			<div class="btn_all">
				<div class="btn_left mb0">
					<a href="javascript:self.close();" class="btn btn-default btn-sm">취소</a> <!-- 취소 -->
				</div>
				<div class="btn_right mb0">
					<a href="#" id="addItems" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 선택상품 추가</a> <!-- 선택상품 추가 -->
				</div>
			</div>
			<page:pagination-manager />
			
		</div> <!-- // popup_contents -->
		
		<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
	</div>
	<div id="search-target">
		<input type="hidden" name="categoryGroupId" value="${coupon.searchCouponTargetItem.categoryGroupId}" class="search-query" title="카테고리" />
		<input type="hidden" name="categoryClass1" value="${coupon.searchCouponTargetItem.categoryClass1}" class="search-query" title="카테고리" />
		<input type="hidden" name="categoryClass2" value="${coupon.searchCouponTargetItem.categoryClass2}" class="search-query" title="카테고리" />
		<input type="hidden" name="categoryClass3" value="${coupon.searchCouponTargetItem.categoryClass3}" class="search-query" title="카테고리" />
		<input type="hidden" name="categoryClass4" value="${coupon.searchCouponTargetItem.categoryClass4}" class="search-query" title="카테고리" />
		<input type="hidden" name="where" value="${coupon.searchCouponTargetItem.where}" class="search-query" />
		<input type="hidden" name="query" value="${coupon.searchCouponTargetItem.query}" class="search-query" />
		<input type="hidden" name="itemCount" value="${pagination.totalItems}" />
	</div>
<script type="text/javascript">

	function getUniqueIndex() {
		var maxIndex = 0;
		$.each(opener.$('ul#op-chosen-item-list > li.op-chose-item'), function(){
			maxIndex = Number($(this).data('index')) >= maxIndex ? Number($(this).data('index')) + 1 : maxIndex;
		})
			 
		return maxIndex;
	}

	$(function(){
		ShopEventHandler.categorySelectboxChagneEvent();  
		Shop.activeCategoryClass('${coupon.searchCouponTargetItem.categoryGroupId}', '${coupon.searchCouponTargetItem.categoryClass1}', '${coupon.searchCouponTargetItem.categoryClass2}', '${coupon.searchCouponTargetItem.categoryClass3}', '${coupon.searchCouponTargetItem.categoryClass4}');
		
		$("input[name='itemId']").on('click', function(){
			$('.userCount').html('체크한 상품수 : ' + Number($("input[name='itemId']:checked").size()) + '개');
		});
		
		$("#allCheck").on("click",function() {
			$("input[name='itemId']").prop('checked', $(this).prop('checked'));
			$('.userCount').html('체크한 상품수 : ' + Number($("input[name='itemId']:checked").size()) + '개');
		});
		
		$('#addItems').on("click",function(){
			
			Common.confirm("${op:message('M01188')}", function(){	// 선택하신 상품을 추가 하시겠습니까?
					
				var maxIndex = getUniqueIndex();
				var id = "op-chose-user-" + maxIndex;
				var index = opener.$('ul#op-chosen-item-list > li.op-chose-item').size();		
					
				var title = "선택 상품 추가 [총 "+ Number($("input[name='itemId']:checked").size()) +"건]";
				var data = "<li class=\"click op-chose-item\" id=\"" + id + "\" data-index=\""+ maxIndex +"\">" + title;
				
				data += "<input type=\"hidden\" name=\"couponTargetItems["+ index +"].addType\" value=\"selected\" />";
				data += "<input type=\"hidden\" name=\"couponTargetItems["+ index +"].title\" value=\""+ title + "\" />";
				
				$("input[name='itemId']:checked").each(function() {
					data += "<input type=\"hidden\" name=\"couponTargetItems["+index+"].itemIds\" value=\""+ $(this).val() +"\" />"; 
				})
				
				data += "&nbsp;<a href=\"javascript:;\" class=\"fix_btn\" style=\"right: 0;padding:2px 10px;\" onclick=\"chosenItemDelete('"+id+"')\">${op:message('M00074')}</a>";
				data += '</li>';
				opener.$("#op-chosen-item-list").append(data);
				self.close();
			});
		});
		
		$("#addAllItems").on("click",function(){
			var searchItemCount = $('#search-target').find('input[name="itemCount"]').val();
			if (searchItemCount == 0) {
				alert('검색된 상품이 없습니다.');
				return;
			}
			
			Common.confirm("검색된 모든 상품을 추가하시겠습니까?", function(){
				var title = "";
				$.each($('#search-target').find('.search-query'), function() {
					
					if ($(this).attr('class') == 'categoryParam') {
						if ($(this).attr('name') == 'categoryGroupId') {
							if ($(this).val() != '0') {
								if (title != '') {
									title += ' / ';
								}
								title += "카테고리 : ";
								title += $('form#itemParam').find('select[name="categoryGroupId"] > optgroup > option[value="'+ $('#search-target').find('input[name="categoryGroupId"]').val() +'"]').text();
							}
						}
						if ($(this).attr('name') == 'categoryClass1') {
							if ($(this).val() != '') {
								title += ">"+$('form#itemParam').find('select[name="categoryClass1"] > option[value="'+ $('#search-target').find('input[name="categoryClass1"]').val() +'"]').text();
							}
						}
						if ($(this).attr('name') == 'categoryClass2') {
							if ($(this).val() != '') {
								title += ">"+$('form#itemParam').find('select[name="categoryClass2"] > option[value="'+ $('#search-target').find('input[name="categoryClass2"]').val() +'"]').text();
							}
						}
						if ($(this).attr('name') == 'categoryClass3') {
							if ($(this).val() != '') {
								title += ">"+$('form#itemParam').find('select[name="categoryClass3"] > option[value="'+ $('#search-target').find('input[name="categoryClass3"]').val() +'"]').text();
							}
						}
					}
					if ($(this).attr('name') == 'query') {
						if ($(this).val() != '') {
							
							if (title != '') {
								title += ' / ';
							}
							
							title += $('form#itemParam').find('select[name="where"] > option[value="'+ $('#search-target').find('input[name="where"]').val() +'"]').text();
							title += " : " + $(this).val();
						}
					}
				});
				
				if (title == '') {
					title = "전체 상품 추가 [총 "+ Number(searchItemCount) +"건]";
				} else {
					title = "검색된 상품 추가 ["+ title +"] [총 "+ Number(searchItemCount) +"건]";
				}
				
				var maxIndex = getUniqueIndex();
				var id = "op-chose-item-" + maxIndex;
				var index = opener.$('ul#op-chosen-item-list > li.op-chose-item').size();
				
				var data = "<li class=\"click op-chose-item\" id=\"" + id + "\" data-index=\""+ maxIndex +"\">" + title;
				data += "<input type=\"hidden\" name=\"couponTargetItems["+ index +"].addType\" value=\"search\" />";
				data += "<input type=\"hidden\" name=\"couponTargetItems["+ index +"].title\" value=\""+ title + "\" />";
				
				var sCount = 0;
				$.each($('#search-target').find('.search-query'), function() {
					var inputName = "couponTargetItems["+index+"]."+ $(this).attr('name');
					data += "<input type=\"hidden\" name=\""+ inputName +"\" value=\""+ $(this).val() +"\" />"; 

					sCount++;
				});
				
				var isInsert = true;
				$.each(opener.$('ul#op-chosen-item-list > li.op-chose-item'), function() {
					
					var eqCount = 0;	
					$.each($(this).find('input'), function(){
						var inputName = $(this).attr('name').replace(/\[.*\]/gi, '[0]').replaceAll('couponTargetItems[0].', '');
						if ($('#search-target').find('input[name="' + inputName + '"][value="'+ $(this).val() +'"]').size() > 0) {
							eqCount++;
						}
					})
					
					if (eqCount == sCount) {
						isInsert = false;
					}
				});

				if (isInsert == false) {
					alert('이미 추가된 검색조건입니다.');
					return;
				}
				

				data += "&nbsp;<a href=\"javascript:;\" class=\"fix_btn\" style=\"right: 0;padding:2px 10px;\" onclick=\"chosenItemDelete('"+id+"')\">${op:message('M00074')}</a>";
				
				data += '</li>';
				opener.$("#op-chosen-item-list").append(data);
				
				self.close();
			});
		});
	});
</script>