<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

	<div class="location">
		<a href="#">통계</a> &gt;  <a href="#">매출통계</a> &gt; <a href="#" class="on">카테고리별 판매율</a>
	</div>

	<div class="statistics_web">
		<h3><span>${op:message('M01370')}</span></h3> <!-- 카테고리별 판매율 -->
		<form:form modelAttribute="statisticsParam" method="get">
			<form:hidden path="categoryId" />
			<div class="board_write">						
				<table class="board_write_table" summary="${op:message('M01370')}">
					<caption>${op:message('M01370')}</caption>
					<colgroup>
						<col style="width: 140px">
						<col style="width: auto;"> 
						<col style="width: 140px">
						<col style="width: auto;"> 
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00012')}</td> <!-- 검색조건 -->
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
							<td class="label">${op:message('M00270')}</td> <!-- 카테고리 -->
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
							<td class="label">판매자</td>
							<td colspan="3">
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
						</tr>
						<tr>
							<td class="label">${op:message('M00652')}</td> <!-- 종류 -->
							<td>
								<div>
									<form:radiobutton path="type" label="${op:message('M01371')}" value="1" /> <!-- 분류별 -->
									<form:radiobutton path="type" label="${op:message('M01372')}" value="2" /> <!-- 순위별 -->
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
				<!-- <div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
				</div> -->
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
			
			<!-- 매출별 -->
			<table class="board_list_table stats ${statisticsParam.displaySubtotal == 'N' ? 'odd-even' : ''}">
				<thead>
					<tr>
						<th rowspan="2">${op:message('M00200')}</th> <!-- 순번 -->
						<th rowspan="2" class="border_left">${op:message('M00270')} (${op:message('M01374')})</th> <!-- 상품명 -->
						<th rowspan="2" class="border_left">${op:message('M01368')}</th> <!-- 주문방법 -->
						<th colspan="4" class="division">${op:message('M01355')}</th> <!-- 결제 -->
						<th colspan="4" class="division">${op:message('M00037')}</th> <!-- 취소 -->
						<th colspan="4" class="division">${op:message('M00358')}</th> <!-- 합계 -->
					</tr>
					<tr>
						<th class="division">건수</th> <!-- 건수 -->
						<th class="border_left">상품판매가</th> <!-- 상품판매가 -->
						<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
						<th class="border_left">${op:message('M01369')}</th> <!-- 판매액 -->

						<th class="division">건수</th> <!-- 건수 -->
						<th class="border_left">상품판매가</th> <!-- 상품판매가 -->
						<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
						<th class="border_left">${op:message('M01361')}</th> <!-- 취소액 -->

						<th class="division">건수</th> <!-- 건수 -->
						<th class="border_left">상품판매가</th> <!-- 상품판매가 -->
						<th class="border_left">${op:message('M00452')}</th> <!-- 할인 -->
						<th class="border_left">${op:message('M01369')}</th> <!-- 판매액 -->
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${categoryStatsList}" var="list" varStatus="i">
						<tr class="${i.count % 2 == 0 ? 'even' : '' }">
							<td rowspan="${fn:length(list.groupStats) }">${i.count }</td>
							<td rowspan="${fn:length(list.groupStats) }" class="border_left" style="text-align: left;">
								<c:choose>
									<c:when test="${ statisticsParam.type == '1' }">
										<c:choose>
											<c:when test="${ list.teamId > 0 }">
												<a href="/opmanager/shop-statistics/sales/item?startDate=${statisticsParam.startDate}&endDate=${statisticsParam.endDate}&code=${ list.teamId }&categoryGroupId=${ list.groupId }&categoryClass=${ list.categoryCode }&categoryClass1=${ list.categoryClass1 }&categoryClass2=${ list.categoryClass2 }&categoryClass3=${ list.categoryClass3 }&categoryClass4=${ list.categoryClass4 }">
													${ list.title }
												</a>
											</c:when>
											<c:otherwise>
												${ list.title }
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										${ list.title }
									</c:otherwise>
								</c:choose>
							</td>
							<c:forEach items="${ list.groupStats }" var="item" varStatus="groupIndex">
								<c:if test="${groupIndex.index > 0}"></tr><tr class="${i.count % 2 == 0 ? 'even' : '' }"></c:if>
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
								<td colspan="3">${op:message('M00064')}</td> <!-- 소계 -->
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
					
					<c:if test="${not empty categoryStatsList }">
						<tr class="total">
							<td colspan="3">${op:message('M00358')}</td> <!-- 합계 -->
							<td class="division number">${op:numberFormat(total.saleCount)}</td>
							<td class="border_left number">${op:numberFormat(total.itemPrice)}</td>
							<td class="border_left number">${op:numberFormat(total.discountAmount)}</td>
							<td class="border_left number">${op:numberFormat(total.saleAmount)}</td>

							<td class="division number">${op:numberFormat(total.cancelCount)}</td>
							<td class="border_left number">${op:numberFormat(total.cancelItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(total.cancelDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(total.cancelAmount)}</td>

							<td class="division number">${op:numberFormat(total.totalCount)}</td>
							<td class="border_left number">${op:numberFormat(total.totalItemPrice)}</td>
							<td class="border_left number">${op:numberFormat(total.totalDiscountAmount)}</td>
							<td class="border_left number">${op:numberFormat(total.totalAmount)}</td>
						</tr>
					</c:if>
					
				</tbody>
			</table>
			<!-- // 매출별 -->
			
			<c:if test="${empty categoryStatsList }">
				<div class="no_content">
					<p>
						${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
					</p>
				</div>
			</c:if>

			<sec:authorize access="hasRole('ROLE_EXCEL')">
				<div class="btn_all">
					<div class="right">
						<a href="/opmanager/shop-statistics/sales/category/excel-download?${queryString}" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
					</div>
				</div>
			</sec:authorize>
			
		</div>
		<div class="board_guide">
			
			<p class="tip">[안내]</p>
			<p class="tip">
				"정보없음"의 경우 판매된 상품에 카테고리가 지정되어 있지 않기 때문입니다.
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