<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

	<div class="location">
		<a href="#">통계</a> &gt;  <a href="#">매출통계</a> &gt; <a href="#" class="on">${op:message('M01362')}</a>
	</div>
	<div class="statistics_web">
		<h3><span>${op:message('M01362')}</span></h3> <!-- 지역별 매출 -->
		<form:form modelAttribute="statisticsParam" method="get" >
			<form:hidden path="conditionType" />
			<form:hidden path="dodobuhyun" />
			<form:hidden path="sidoMappingGroupKey" />
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M01362')}">
					<caption>${op:message('M01362')}</caption>
					<colgroup>
						<col style="width: 140px;">
						<col style="width: auto;">
						<col style="width: 140px;">
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
							<td class="label">판매자</td>
							<td>
								<div>
									<form:select path="sellerId">
										<form:option value="0">${op:message('M00039')}</form:option>
										<c:forEach items="${sellerList}" var="list" varStatus="i">
											<form:option value="${list.sellerId}">[${list.loginId}] ${list.sellerName}</form:option>
										</c:forEach>
									</form:select>
									<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</a>
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
							<td class="label">소계 노출</td>
							<td>
								<div>
									<form:radiobutton path="displaySubtotal" value="N" label="비노출" checked="checked" />
									<form:radiobutton path="displaySubtotal" value="Y" label="노출" />
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
						<th rowspan="2" class="border_left">${op:message('M01367')}</th> <!-- 지역 -->
						<th rowspan="2" class="border_left">${op:message('M01368')}</th> <!-- 주문방법 -->
						<th colspan="4" class="division">${op:message('M01355')}</th> <!-- 결제 -->
						<th colspan="4" class="division">${op:message('M00037')}</th> <!-- 취소 -->
						<th colspan="4" class="division">${op:message('M00358')}</th> <!-- 합계 -->
					</tr>
					<tr>
						<th class="division number">건수</th> <!-- 건수 -->
						<th class="border_left number">상품판매가</th> <!-- 상품판매가 -->
						<th class="border_left number">${op:message('M00452')}</th> <!-- 할인 -->
						<th class="border_left number">${op:message('M01369')}</th> <!-- 판매액 -->

						<th class="division number">건수</th> <!-- 건수 -->
						<th class="border_left number">상품판매가</th> <!-- 상품판매가 -->
						<th class="border_left number">${op:message('M00452')}</th> <!-- 할인 -->
						<th class="border_left number">${op:message('M01361')}</th> <!-- 취소액 -->

						<th class="division number">건수</th> <!-- 건수 -->
						<th class="border_left number">상품판매가</th> <!-- 상품판매가 -->
						<th class="border_left number">${op:message('M00452')}</th> <!-- 할인 -->
						<th class="border_left number">${op:message('M01369')}</th> <!-- 판매액 -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${areaStatsList}" var="list" varStatus="i">
						<tr class="${i.count % 2 == 0 ? 'even' : '' }">
							<td rowspan="${fn:length(list.groupStats) }" class="under_line">
								<c:set var="action">/opmanager/shop-statistics/sales/area?conditionType=SIGUNGU&sidoMappingGroupKey=${list.sidoMappingGroupKey}&startDate=${statisticsParam.startDate}&endDate=${statisticsParam.endDate}&sellerId=${statisticsParam.sellerId}</c:set>
								<c:if test="${ statisticsParam.conditionType eq 'SIGUNGU'}">
									<c:set var="action">javascript:Common.popup('/opmanager/shop-statistics/sales/area/detail?sidoMappingGroupKey=${list.sidoMappingGroupKey}&dodobuhyun=${list.sigungu}&startDate=${statisticsParam.startDate}&endDate=${statisticsParam.endDate}&sellerId=${statisticsParam.sellerId}', '${list.sigungu}' , '1000', '800', 1)</c:set>
									<c:if test="${empty list.sigungu}">

									</c:if>
								</c:if>
								<a href="${ action }">
									<c:choose>
										<c:when test="${ statisticsParam.conditionType == 'SIGUNGU'}">
											<c:choose>
												<c:when test="${empty list.sigungu}">정보없음</c:when>
												<c:otherwise>
													<c:if test="${not empty list.sido}">
														${list.sido} >
													</c:if>
													${list.sigungu}
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${empty list.sido}">정보없음</c:when>
												<c:otherwise>${list.sido}</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</a>
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

					<c:if test="${not empty areaStatsList}">
						<tr class="total">
							<td colspan="2">${op:message('M00358')} </td>  <!-- 합계 -->
							<td class="division number">${op:numberFormat(total.saleCount)}</td>
							<td class="border_left number">${op:numberFormat(total.itemPrice)}</td>
							<td class="border_left number">${op:numberFormat(total.discountAmount)}</td>
							<td class="border_left number">${op:numberFormat(total.saleAmount)}</td>

							<td class="division number">${op:numberFormat(total.cancelCount)}</td>
							<td class="border_left number">${op:numberFormat(total.cancelItemPrice) }</td>
							<td class="border_left number">${op:numberFormat(total.cancelDiscountAmount) }</td>
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

			<c:if test="${empty areaStatsList}">
				<div class="no_content">
					<p>
						${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
					</p>
				</div>
			</c:if>

			<sec:authorize access="hasRole('ROLE_EXCEL')">
				<div class="btn_all">
					<div class="right">
						<a href="/opmanager/shop-statistics/sales/area/excel-download?${queryString}"  class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
					</div>
				</div>
			</sec:authorize>
			<!--
			<div class="board_guide">
				<p class="tip">Tip</p>
				<p class="tip">- 지역을 클릭하시면 상세내역을 조회하실 수 있습니다.</p>
 				<p class="tip">- 조회기간은 3개월까지만 가능합니다.</p>
			</div> -->

		</div>

		<div class="board_guide">
			<p class="tip">[안내]</p>
			<p class="tip">
				<c:choose>
					<c:when test="${ statisticsParam.conditionType == 'SIGUNGU'}">
						"정보없음"의 경우 받는사람의 주소에 시/군/구 정보가 없기때문입니다.
					</c:when>
					<c:otherwise>
						"정보없음"의 경우 받는사람의 주소에 시/도 정보가 없기때문입니다.
					</c:otherwise>
				</c:choose>

			</p>
		</div>

	</div>


<script type="text/javascript">
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
	});

	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>