<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<div class="location">
		<a href="#">통계</a> &gt;  <a href="#">매출통계</a> &gt; <a href="#" class="on">매출 제로 상품 통계</a>
	</div>
	<div class="statistics_web">
		<h3><span>${op:message('M01399')}</span></h3> <!-- 매출 제로 상품 통계 -->
		<form:form modelAttribute="statisticsParam" method="get" >
			<div class="board_write">						
				<table class="board_write_table" summary="${op:message('M01399')}"><!-- 매출 제로 상품 통계 -->
					<caption>${op:message('M01399')}</caption><!-- 매출 제로 상품 통계 -->
					<colgroup>
						<col style="width: 20%;">
						<col style="width: auto;"> 
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M01399')}</td>
							<td>
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
							<td class="label">${op:message('M00270')}</td> <!-- 카테고리 -->
							<td>
								<div>
									<form:select path="code" >
										<option value="">= ${op:message('M00585')} =</option> <!-- 팀/그룹 --> <!-- 팀 -->
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
							<td class="label">${op:message('M00018')}</td> <!-- 상품명 -->
							<td>
								<div>
									<form:input path="itemName" class="three" title="상품명 입력" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">매출건수</td>
							<td>
								<div>
									<form:input path="salesCount" class="_number" title="매출건수"/> 이하
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">매출금액</td>
							<td>
								<div>
									<form:input path="salesPrice" class="_number" title="매출금액"/> 이하
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
			</div> <!-- // board_write -->
			
			
			
			<div class="btn_all">
				<!-- <div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
				</div> -->
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
				</div>
			</div>
		</form:form>
		<div class="board_list mt30">
			
			<table class="board_list_table">
				<caption>${op:message('M01399')}</caption><!-- 매출 제로 상품 통계 -->
				<colgroup>
					<col style="width: 30%;" />
					<col style="width: auto;" />
					<col style="width: 7%;" />
					<col style="width: 10%;" />
				</colgroup>
				<thead>
					<tr>
						<th>${op:message('M00270')}</th> <!-- 카테고리 -->
						<th class="border_left">${op:message('M00018')}</th> <!-- 상품명 -->
						<th class="border_left">매출건수</th>
						<th class="border_left">매출금액</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${itemList}" var="list">
						<tr>
							<td style="text-align: left;"><a href="/categories/index/${list.categoryCode }" target="_blank"> ${list.title }</a></td>
							<td class="border_left" style="text-align: left;">
								<c:choose>
									<c:when test='${op:property("saleson.view.type") eq "api"}'>
										<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemUserCode}" target="_blank"><img src="${list.itemImage}" class="item_image" alt="상품이미지"  /></a>
										<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemUserCode}" target="_blank">${list.itemName } (${list.itemUserCode })</a>
									</c:when>
									<c:otherwise>
										<a href="/products/view/${list.itemUserCode}" target="_blank"><img src="${list.itemImage}" class="item_image" alt="상품이미지"  /></a>
										<a href="/products/view/${list.itemUserCode}" target="_blank">${list.itemName } (${list.itemUserCode })</a>
									</c:otherwise>
								</c:choose>

							</td>
							<td class="border_left number">${list.payCount }</td>
							<td class="border_left number">${ op:numberFormat(list.totalItemPrice) }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="sort_area">
				<div class="right">
					<a href="/opmanager/shop-statistics/sales/no-sales/excel-download?${queryString}"  class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>${op:message('M00254')}</span> </a> <!-- 엑셀 다운로드 -->
				</div>
			</div>
			
			<page:pagination-manager />
		
		</div>
		
	</div>
	
	
	
<script type="text/javascript">
	$(function(){
		ShopEventHandler.categorySelectboxChagneEvent2();  
		Shop.activeCategoryClass2('${statisticsParam.code}','${statisticsParam.categoryGroupId}', '${statisticsParam.categoryClass1}', '${statisticsParam.categoryClass2}', '${statisticsParam.categoryClass3}', '${statisticsParam.categoryClass4}');
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});
</script>