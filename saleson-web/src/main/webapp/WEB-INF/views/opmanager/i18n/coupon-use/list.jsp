<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

	<!-- 본문 -->
	<div class="item_list">
		<h3><span>${op:message('M01196')}</span></h3> <!-- 쿠폰사용내역리스트 -->
		<form:form modelAttribute="couponParam" method="get">
			<div class="board_write">
				<table class="board_write_table" summary="${op:message('M01137')}">
					<caption>${op:message('M01137')}</caption>
					<colgroup>
						<col style="width: 150px;" />
						<col style="width: auto;" />
						<col style="width: 150px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">쿠폰명</td>
							<td>
								<div>
									<form:input path="query" class="w360" title="${op:message('M01140')}" /> <!-- 상세검색 입력 -->
								</div>
							</td>
							<td class="label">쇼핑채널</td>
							<td>
								<div>
									<p>
										<form:checkbox path="couponTypes" label="PC" value="WEB" />
										<form:checkbox path="couponTypes" label="모바일" value="MOBILE" />
										<form:checkbox path="couponTypes" label="앱" value="APP" />
									</p>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">사용여부</td>
							<td>
								<div>
									<p>
										<form:radiobutton path="couponFlag" checked="checked" label="${op:message('M00039')}" value="" /> <!-- 전체 -->
										<form:radiobutton path="couponFlag" label="사용" value="Y" />
										<form:radiobutton path="couponFlag" label="미사용" value="N" />
									</p>
								</div>
							</td>
							<td class="label">발급대상[회원]</td>
							<td>
								<div>
									<p>
										<form:radiobutton path="couponTargetUserType" checked="checked" label="${op:message('M00039')}" value="" /> <!-- 전체 -->
										<form:radiobutton path="couponTargetUserType" label="전체 회원" value="1" />
										<form:radiobutton path="couponTargetUserType" label="회원 선택" value="2" />
										<form:radiobutton path="couponTargetUserType" label="회원 등급별" value="3" />
									</p>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">쿠폰 발행상태</td> <!-- 쿠폰 사용상태 -->
							<td>
								<div>
									<p>
										<form:radiobutton path="dataStatusCode" checked="checked" label="${op:message('M00039')}" value="" /> <!-- 전체 -->
										<form:radiobutton path="dataStatusCode" label="발행" value="1" />
										<form:radiobutton path="dataStatusCode" label="사용중지" value="9" />
									</p>
								</div>
							</td>
							<td class="label">발급대상[상품]</td>
							<td>
								<div>
									<p>
										<form:radiobutton path="couponTargetItemType" checked="checked" label="${op:message('M00039')}" value="" /> <!-- 전체 -->
										<form:radiobutton path="couponTargetItemType" label="전체 상품" value="1" />
										<form:radiobutton path="couponTargetItemType" label="상품 선택" value="2" />
									</p>
								</div>
							</td>
						</tr>
					</tbody>
				</table>

			</div> <!-- // board_write -->

			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/coupon-use/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>

			<div class="count_title mt20">
				<h5>

				</h5>
				<span>

					<form:select path="orderBy">
						<form:option value="COUPON_ID" label="${op:message('M00200')}" />
						<form:option value="COUPON_NAME" label="${op:message('M00879')}" />
						<form:option value="COUPON_ISSUE_START_DATE" label="${op:message('M01148')}" />
						<form:option value="COUPON_APPLY_START_DATE" label="${op:message('M01149')}" />
						<form:option value="COUPON_ISSUE_COUNT" label="${op:message('M01198')}" />
						<form:option value="COUPON_APPLY_COUNT" label="${op:message('M01199')}" />
					</form:select>

					<form:select path="sort" title="검색방법 선택">
						<form:option value="DESC" label="${op:message('M00689')}" />    <!-- 내림차순 -->
						<form:option value="ASC" label="${op:message('M00690')}" />    <!-- 오름차순 -->
					</form:select>

					${op:message('M00052')} : <!-- 출력수 -->
					<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
						onchange="$('form#couponParam').submit();"> <!-- 화면 출력수 -->
						<form:option value="10" label="10${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="20" label="20${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="30" label="30${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
					</form:select>
				</span>
			</div>

		</form:form>

		<div class="board_write mt20">
			<table class="board_list_table mt0" summary="${op:message('M01137')}">
				<caption>${op:message('M01137')}</caption>
				<colgroup>
					<col style="width: 5%;" />
					<col />
					<col style="width: 8%;" />
					<col style="width: 8%;" />
					<col style="width: 10%;" />
					<col style="width: 13%;" />
					<col style="width: 8%;" />
					<col style="width: 8%;" />
					<col style="width: 5%;" />
					<col style="width: 5%;" />
				</colgroup>
				<thead>
					<tr>
						<th>${op:message('M00200')}</th> <!-- 순번 -->
						<th>${op:message('M00879')}</th> <!-- 쿠폰명 -->
						<th>발급대상[회원]</th>
						<th>발급대상[상품]</th>
						<th>${op:message('M01147')}</th> <!-- 혜택 -->
						<th>${op:message('M01148')}</th> <!-- 쿠폰 발급기간 -->
						<th>전체 발행가능 수량</th>
						<th>회원당 발행가능 수량</th>
						<th>총 발행 수량</th>
						<th>사용 수량</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${couponList}" var="list" varStatus="i">
						<tr>
							<td>${pagination.itemNumber - i.count}</td>
							<td><a href="/opmanager/coupon-use/detail/${list.couponId}">${list.couponName}</a></td>
							<td>${list.couponTargetUserTypeLabel}</td>
							<td>${list.couponTargetItemTypeLabel}</td>
							<td>
								${op:numberFormat(list.couponPay)}<c:if test="${list.couponPayType == '1'}">${op:message('M00049')} <!-- 원 --></c:if>
								<c:if test="${list.couponPayType == '2'}">%</c:if> ${op:message('M00452')} <!-- 할인 -->
							</td>
							<td>
								<c:choose>
									<c:when test="${list.couponIssueType == '0'}">제한없음</c:when>
									<c:otherwise>${op:date(list.couponIssueStartDate)} ~ ${op:date(list.couponIssueEndDate)}</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.couponDownloadLimit > 0}">최대 ${op:numberFormat(list.couponDownloadLimit)}장</c:when>
									<c:otherwise>제한없음</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.couponDownloadUserLimit > 0}">최대 ${op:numberFormat(list.couponDownloadUserLimit)}장</c:when>
									<c:otherwise>제한없음</c:otherwise>
								</c:choose>
							</td>
							<td class="text-right">${op:numberFormat(list.totalDownloadCount)}</td>
							<td class="text-right">${op:numberFormat(list.totalUsedCount)}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div> <!--// board_write -->

		<c:if test="${empty couponList}">
			<div class="no_content">
				<p>${op:message('M00591')}</p> <!-- 등록된 데이터가 없습니다. -->
			</div>
		</c:if>

		<page:pagination-manager />

	</div> <!-- // item_list01 -->



