<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">판매율통계</a> &gt; <a href="#" class="on"></a> 
</div>
<div class="statistics_web">
	<h3><span>${op:message('M01383')}</span></h3> <!-- 상품별 판매율 -->
	<form:form modelAttribute="statisticsParam" method="get" >
		<div class="board_write">						
			<table class="board_write_table" summary="상품별 판매율">
				<caption>${op:message('M01383')}</caption>
				<colgroup>
					<col style="width: 20%;">
					<col style="width: auto;">
					<col style="width: 20%;">
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
						<td colspan="3">
							<div>
								<form:select path="where" title="상품명 선택">
									<form:option value="ITEM_NAME">${op:message('M00018')}</form:option> <!-- 상품명 -->
									<form:option value="ITEM_USER_CODE">${op:message('M00783')}</form:option> <!-- 상품코드 -->
								</form:select>
								<form:input path="query" class="three" title="상세검색 입력" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00790')}</td>
						<td>
							<div>
								<form:radiobutton path="orderBy" label="${op:message('M01384')}" value="PRICE" /> <!-- 판매 금액 -->
								<form:radiobutton path="orderBy" label="${op:message('M01385')}" value="QUANTITY" /> <!-- 판매 수량 -->								
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
						<td colspan="3">
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
					</tr>
					<tr>
						<td class="label">브랜드</td>
						<td >
							<div>
								<form:select path="brand">
									<form:option value="" label="선택" />
									<c:forEach items="${brandList}" var="brand">
										<c:if test="${brand.displayFlag == 'Y'}">
											<form:option value="${brand.brandName}" label="${brand.brandName}" />
										</c:if>
									</c:forEach>
								</form:select>
							</div> 
						</td>
						<td class="label">판매자</td>
						<td>
							<div>
								<form:select path="sellerId">
									<form:option value="0">${op:message('M00039')}</form:option>
									<c:forEach items="${sellerList}" var="list" varStatus="i">
										<form:option value="${list.sellerId}">[${list.loginId}] ${list.sellerName}</form:option>
									</c:forEach>
								</form:select>
								<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-gradient btn-xs">검색</a>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
		</div> <!-- // board_write -->

		<div class="btn_all">
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
			</div>
		</div>
		<div class="board_guide" style="text-align: right;">
			<p class="tip">브랜드로 지정된 상품만 검색됩니다. </p>
		</div>	
	
	</form:form>
	
	<div class="sort_area mt30">
		<div class="left">
			<span>${op:message('M01388')} : <span class="font_b">${op:numberFormat(total.totalPayCount + total.totalCancelCount)}</span>${op:message('M01004')} (${op:message('M01355')} : ${op:numberFormat(total.totalPayCount)}${op:message('M01004')}, ${op:message('M01365')} : ${op:numberFormat(total.totalCancelCount)}${op:message('M01004')})</span> | <span>${op:message('M01389')} : <span class="font_b">${op:numberFormat(total.totalRevenueAmount) }</span>${op:message('M00814')}</span>
		</div>
	</div>
	
	<div class="board_list">
		
		<!-- 판매율별 -->
		<table class="board_list_table">
			<caption>브랜드별 판매율</caption>
			<thead>
				<tr> 
					<!-- th rowspan="2">${op:message('M00200')}</th> <!-- 순번 -->
					<th rowspan="2" >브랜드명</th> <!-- 상품명 -->
					<th rowspan="2" class="border_left">${op:message('M01368')}</th> <!-- 주문방법 -->
					<th colspan="4" class="border_left">${op:message('M01355')}</th> <!-- 결제 -->
					<th colspan="4" class="border_left">${op:message('M00037')}</th> <!-- 취소 -->
					<th colspan="4" class="border_left">${op:message('M00064')}</th> <!-- 소계 -->
				</tr>
				<tr>
					<th class="border_left">${op:message('M01375')}</th> <!-- 갯수 -->
					<th class="border_left">${op:message('M00627')}</th> <!-- 상품금액 -->
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인액 -->
					<th class="border_left">${op:message('M01369')}</th> <!-- 판매액 -->
					
					<th class="border_left">${op:message('M01375')}</th> <!-- 갯수 -->
					<th class="border_left">${op:message('M00627')}</th> <!-- 상품금액 -->
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인액 -->
					<th class="border_left">${op:message('M01361')}</th> <!-- 취소액 -->
					
					<th class="border_left">${op:message('M01375')}</th> <!-- 갯수 -->
					<th class="border_left">${op:message('M00627')}</th> <!-- 상품금액 -->
					<th class="border_left">${op:message('M00452')}</th> <!-- 할인액 -->
					<th class="border_left">${op:message('M01369')}</th> <!-- 판매액 -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${brandStatisticsList}" var="list" varStatus="i">
					<tr>
						<!-- td rowspan="${fn:length(list.groupList) }">${pagination.itemNumber - i.count }</td -->
						<td rowspan="${fn:length(list.groupList) }" style="text-align: left;">
						
							<c:if test="${list.brand == '정보없음'}">
								<c:set var="brandName" value=""/>
								<c:set var="brandTitle" value="정보없음"/>
							</c:if>
							<c:if test="${list.brand != '정보없음'}">
								<c:set var="brandName" value="${list.brand}"/>
								<c:set var="brandTitle" value="${list.brand}"/>
							</c:if>
							<a href="javascript:;" onclick="openBrandDetail('${brandName}')">
								${brandTitle}
							</a>
						
						</td>

						<c:forEach items="${ list.groupList }" var="item" varStatus="groupIndex">
							<c:if test="${groupIndex.index > 0}"></tr><tr></c:if>
							<td class="border_left">${item.osType}</td>
							<td class="border_left number">${op:numberFormat(item.payCount)}</td>
							<td class="border_left number">${op:numberFormat(item.itemPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.itemCouponDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(item.payTotal)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelCount)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelItemCouponDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(item.cancelTotal)}</td>
							
							<td class="border_left number">${op:numberFormat(item.payCount + item.cancelCount)}</td>
							<td class="border_left number">${op:numberFormat(item.sumItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(item.sumItemCouponDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(item.sumTotalAmount)}</td>
						</c:forEach>
					</tr>
					
					<tr style="background: #f2f2f2;">
						<td colspan="2">${op:message('M00064')}</td> <!-- 소계 -->
						<td class="border_left number">${op:numberFormat(list.totalPayCount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayItemPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayItemCouponDiscountAmount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPay)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelCount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelItemPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancelItemCouponDiscountAmount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalCancel)}</td>
						
						<td class="border_left number">${op:numberFormat(list.totalPayCount + list.totalCancelCount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayItemPrice + list.totalCancelItemPrice)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPayItemCouponDiscountAmount + list.totalCancelItemCouponDiscountAmount)}</td>
						<td class="border_left number">${op:numberFormat(list.totalPay + list.totalCancel)}</td>
					</tr>
				</c:forEach> 
				
				<c:if test="${not empty brandStatisticsList }">
					<tr style="background: #CAC6C6;">
						<td colspan="3">${op:message('M00358')}</td> <!-- 소계 -->
						<td class="border_left number">${op:numberFormat(total.totalPayCount)}</td>
						<td class="border_left number">${op:numberFormat(total.totalItemPrice)}</td>
						<td class="border_left number">${op:numberFormat(total.totalItemCouponDiscountAmount)}</td> 
						<td class="border_left number">${op:numberFormat(total.totalPayAmount)}</td>
						<td class="border_left number">${op:numberFormat(total.totalCancelCount)}</td>
						<td class="border_left number">${op:numberFormat(total.totalCancelItemPrice) }</td>
						<td class="border_left number">${op:numberFormat(total.totalCancelItemCouponDiscountAmount) }</td>
						<td class="border_left number">${op:numberFormat(total.totalCancelAmount)}</td>
						<td class="border_left number">${op:numberFormat(total.totalRevenueItemPrice)}</td>
						<td class="border_left number">${op:numberFormat(total.totalRevenueItemCouponDiscountAmount)}</td>
						<td class="border_left number">${op:numberFormat(total.totalRevenueAmount)}</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<!-- // 판매율별 -->
		<c:if test="${empty brandStatisticsList}">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>
		
		<div class="sort_area">
			<div class="right">
				<a href="/opmanager/shop-statistics/sales/brand/excel-download?${queryString}" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>${op:message('M00254')}</span> </a> <!-- 엑셀 다운로드 -->
			</div>
		</div>
		
	</div>
	<div class="board_guide">

	</div>
</div>

<script type="text/javascript">
	$(function(){
		
		ShopEventHandler.categorySelectboxChagneEvent2();  
		Shop.activeCategoryClass2('${statisticsParam.code}','${statisticsParam.categoryGroupId}', '${statisticsParam.categoryClass1}', '${statisticsParam.categoryClass2}', '${statisticsParam.categoryClass3}', '${statisticsParam.categoryClass4}');
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
		
	});
	
	function openBrandDetail(brand){
		var orgBrand = $('#brand').val();
		
		$('#brand').val(brand);
		Common.popup('/opmanager/shop-statistics/sales/brand/brand-detail?'+$('#statisticsParam').serialize(), brand, '1000', '800', 1);
		$('#brand').val(orgBrand);
		
	}
	
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>