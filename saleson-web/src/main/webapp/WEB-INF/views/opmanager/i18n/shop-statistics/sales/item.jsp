<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">판매율통계</a> &gt; <a href="#" class="on">상품별 판매율</a> 
</div>
<div class="statistics_web">
	<h3><span>${op:message('M01383')}</span></h3> <!-- 상품별 판매율 -->
	<form:form modelAttribute="statisticsParam" method="get" >
		<div class="board_write">						
			<table class="board_write_table" summary="상품별 판매율">
				<caption>${op:message('M01383')}</caption>
				<colgroup>
					<col style="width: 140px;">
					<col style="width: auto;">
					<col style="width: 140px;">
					<col style="width: auto;">  
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M01347')}</td> <!-- 기간 -->
						<td colspan="3">
					 		<div> 
								<span class="datepicker"><form:input path="startDate" class="term datepicker" title="${op:message('M00507')}" id="dp28" /></span> <!-- 시작일 -->
								<span class="wave">~</span>
								<span class="datepicker"><form:input path="endDate" class="term datepicker" title="${op:message('M00509')}" id="dp29" /></span> <!-- 종료일 -->
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
						<td class="label">${op:message('M01353')}</td> <!-- 카테고리선택 -->
						<td colspan="3">
							<div>
								<form:select path="code" >
									<option value="">= ${op:message('M00585')} =</option> <!-- 팀/그룹 -->
									<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
										<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
											<form:option value="${categoriesTeam.categoryTeamId}" label="${categoriesTeam.name}" />
										</c:if>
									</c:forEach>
								</form:select>
								
								<form:select path="categoryGroupId" class="category">
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
						<td class="label">${op:message('M00001')}</td> <!-- 상품 -->
						<td>
							<div>
								<form:select path="where" title="상품명 선택">
									<form:option value="ITEM_NAME">${op:message('M00018')}</form:option> <!-- 상품명 -->
									<form:option value="ITEM_USER_CODE">${op:message('M00783')}</form:option> <!-- 상품코드 -->
								</form:select>
								<form:input path="query" class="three" title="상세검색 입력" />
							</div>
						</td>
						<td class="label">${op:message('M00790')}</td>
						<td>
							<div>
								<form:radiobutton path="orderBy" label="${op:message('M01384')}" value="PRICE" /> <!-- 판매 금액 -->
								<form:radiobutton path="orderBy" label="${op:message('M01385')}" value="QUANTITY" /> <!-- 판매 수량 -->								
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">판매자</td>
						<td>
							<div>
								<form:select path="sellerId">
									<form:option value="0">${op:message('M00039')}</form:option>
									<c:forEach items="${sellerList}" var="list" varStatus="i">
										<form:option value="${list.sellerId}">[${list.loginId}] ${list.sellerName}</form:option>
									</c:forEach>
								</form:select>
								<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"> <span class="glyphicon glyphicon-search"></span> 검색</a>
							</div>
						</td>
						<td class="label">${op:message('M01386')}</td> <!-- 정렬 방법 -->
						<td>
							<div>
								<form:radiobutton path="sort" label="${op:message('M00689')}" value="DESC" /> <!-- 내림차순 -->
								<form:radiobutton path="sort" label="${op:message('M00690')}" value="ASC" /> <!-- 오름차순 -->					
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00052')} <!-- 출력수 --></td>
						<td>
							<div>
								<form:select path="itemsPerPage" title="${op:message('M01387')}"> <!-- 출력수 선택 -->
									<form:option value="10" label="10" />
									<form:option value="20" label="20" />
									<form:option value="30" label="30" />
									<form:option value="50" label="50" />
									<form:option value="100" label="100" />
									<form:option value="500" label="500" />
									<form:option value="1000" label="1000" />
								</form:select>
							</div>
						</td>
						<td class="label">소계 노출</td>
						<td>
							<div>
								<form:radiobutton path="displaySubtotal" value="N" label="비노출" checked="checked" />
								<form:radiobutton path="displaySubtotal" value="Y" label="노출" /> 
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
		</div> <!-- // board_write -->

		<div class="btn_all">
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>
	
	</form:form>

	<div class="sort_area mt30">
		<div class="left">
			<span>${op:message('M01363')} : <span class="font_b">${op:numberFormat(total.totalCount)}</span>${op:message('M00272')} (${op:message('M01364')} : ${op:numberFormat(total.saleCount)}${op:message('M00272')}, ${op:message('M01365')} : ${op:numberFormat(total.cancelCount)}${op:message('M00272')})</span> | <span>${op:message('M01366')} : <span class="font_b">${op:numberFormat(total.totalAmount) }</span>${op:message('M00814')}</span>
		</div>
	</div>

	<div class="board_list">

		<!-- 판매율별 -->
		<table class="board_list_table stats ${statisticsParam.displaySubtotal == 'N' ? 'odd-even' : ''}">
			<caption>상품별 판매율</caption>
			<thead>
				<tr>
					<!-- th rowspan="2">${op:message('M00200')}</th> <!-- 순번 -->
					<th rowspan="2" >${op:message('M00018')}</th> <!-- 상품명 -->
					<th rowspan="2" class="">${op:message('M01368')}</th> <!-- 주문방법 -->
					<th colspan="4" class="division">${op:message('M01355')}</th> <!-- 결제 -->
					<th colspan="4" class="division">${op:message('M00037')}</th> <!-- 취소 -->
					<th colspan="4" class="division">${op:message('M00358')}</th> <!-- 합계 -->
				</tr>
				<tr>
					<th class="division">${op:message('M01357')}</th> <!-- 건수 -->
					<th class="border_left">상품판매가</th>
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
					<th class="border_left">${op:message('M01369')}</th> <!-- 판매액 -->

					<th class="division">${op:message('M01357')}</th> <!-- 건수 -->
					<th class="border_left">상품판매가</th>
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
					<th class="border_left">${op:message('M01361')}</th> <!-- 취소액 -->

					<th class="division">${op:message('M01357')}</th> <!-- 건수 -->
					<th class="border_left">상품판매가</th>
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
					<th class="border_left">${op:message('M01369')}</th> <!-- 판매액 -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${itemStatsList}" var="list" varStatus="i">
					<tr class="${i.count % 2 == 0 ? 'even' : '' }">
						<!-- td rowspan="${fn:length(list.groupStats) }">${pagination.itemNumber - i.count}</td -->
						<td rowspan="${fn:length(list.groupStats) }" style="text-align: left;">
							<a href="javascript:;" onclick="Common.popup('/opmanager/shop-statistics/sales/item/${list.itemId}?startDate=${statisticsParam.startDate}&endDate=${statisticsParam.endDate}', '${list.itemId}', '1000', '800', 1);">
								<!-- img src="${list.itemImage}" class="item_image" alt="상품이미지"  / -->
								${list.itemName } (${list.itemUserCode })
							</a>
						</td>

						<c:forEach items="${ list.groupStats }" var="item" varStatus="statsIndex">
							<c:if test="${statsIndex.index > 0}"></tr><tr class="${i.count % 2 == 0 ? 'even' : '' }"></c:if>
							<td class="border_left">${item.deviceType}</td>
							<td class="division number">${op:numberFormat(item.saleCount)}</td>
							<td class="border_left number">${op:numberFormat(item.itemPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.discountAmount)}</td>
							<td class="border_left number">${op:numberFormat(item.saleAmount)}</td>

							<td class="division number">${op:numberFormat(item.cancelCount)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelAmount)}</td>

							<td class="division number">${op:numberFormat(item.totalCount)}</td>
							<td class="border_left number">${op:numberFormat(item.totalItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.totalDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(item.totalAmount)}</td>
						</c:forEach>
					</tr>
					
					<c:if test="${statisticsParam.displaySubtotal == 'Y'}">
						<tr class="sub-total">
							<td colspan="2">${op:message('M00064')}</td> <!-- 소계 -->

							<td class="division number">${op:numberFormat(list.subSaleCount)}</td>
							<td class="border_left number">${op:numberFormat(list.subItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(list.subDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(list.subSaleAmount)}</td>

							<td class="division number">${op:numberFormat(list.subCancelCount)}</td>
							<td class="border_left number">${op:numberFormat(list.subCancelItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(list.subCancelDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(list.subCancelAmount)}</td>

							<td class="division number">${op:numberFormat(list.subTotalCount)}</td>
							<td class="border_left number">${op:numberFormat(list.subTotalItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(list.subTotalDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(list.subTotalAmount)}</td>
						</tr>
					</c:if>
				</c:forEach> 
			</tbody>
		</table>
		<!-- // 판매율별 -->
		<c:if test="${empty itemStatsList}">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>

		<sec:authorize access="hasRole('ROLE_EXCEL')">
			<div class="btn_all">
				<div class="right">
					<a href="/opmanager/shop-statistics/sales/item/excel-download?${queryString}" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
				</div>
			</div>
		</sec:authorize>

		<page:pagination-manager />
		
	</div>
	
	<div class="board_guide">
		<br/>
		<p class="tip">[안내]</p>
		<p class="tip">
			해당 통계는 상품에 포함 되어있는 옵션별로 판매 통계를 나타내지 않습니다.
			<br/>
			예를들어 '12357-set' 상품에 포함되어있는 옵션변호 '18220'으로 검색시 옵션번호 '18220'를 선택 구매한 주문의 상품[12357-set] 합계금액을 기준으로 통계를 산정합니다.
		</p> 
	</div>
	
</div> 
 
<script type="text/javascript">
	$(function(){
		
		ShopEventHandler.categorySelectboxChagneEvent2();  
		Shop.activeCategoryClass2('${statisticsParam.code}','${statisticsParam.categoryGroupId}', '${statisticsParam.categoryClass1}', '${statisticsParam.categoryClass2}', '${statisticsParam.categoryClass3}', '${statisticsParam.categoryClass4}');
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
		
	});
	
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>