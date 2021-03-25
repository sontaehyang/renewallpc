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
	<h3><span>${op:message('M01201')}</span></h3> <!-- 쿠폰 내역 조회 -->			
	<div class="board_write">	
		<p class="pop_tit">${op:message('M01202')}</p>					
		<table class="board_write_table" summary="${op:message('M01202')}">
			<caption>${op:message('M01202')}</caption> <!-- 쿠폰 정보 -->
			<colgroup>
				<col style="width: 200px;" />
				<col style="width: auto;" /> 
				<col style="width: 200px;" />
				<col style="width: auto;" /> 
			</colgroup>
			<tbody>
				<tr>
					<td class="label">${op:message('M00879')}</td> <!-- 쿠폰명 -->
					<td>
						<div>
							${coupon.couponName}
						</div>
					</td>
					<td class="label">적용 채널</td>
					<td>
						<div>
							${coupon.couponTypeLabel}
						</div>
					</td>
				</tr>
				<c:if test="${not empty coupon.couponComment}">
					<tr>
						<td class="label">${op:message('M01156')}</td> <!-- 쿠폰설명 -->
						<td colspan="3">
							<div>
								${coupon.couponComment} 
							</div>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="label">다운로드 가능기간</td>
					<td>
						<div>
							<c:choose>
								<c:when test="${coupon.couponIssueType == '0'}">제한없음</c:when>
								<c:otherwise>${op:date(coupon.couponIssueStartDate)} ~ ${op:date(coupon.couponIssueEndDate)}</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td class="label">사용 가능 기간</td>
					<td>
						<div>
							<c:choose>
								<c:when test="${coupon.couponApplyType == '0'}">제한없음</c:when> 
								<c:when test="${coupon.couponApplyType == '2' }">다운로드 시점부터 <strong>${coupon.couponApplyDay}일</strong> 후까지</c:when>
								<c:otherwise>${op:date(coupon.couponApplyStartDate)} ~ ${op:date(coupon.couponApplyEndDate)}</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">총 발급 수량</td>
					<td>
						<div>
							<c:choose>
								<c:when test="${coupon.couponDownloadLimit == -1}">제한없음</c:when>
								<c:otherwise>${op:numberFormat(coupon.couponDownloadLimit)}장</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td class="label">회원별 다운로드 가능 수량</td>
					<td>
						<div>
							<c:choose>
								<c:when test="${coupon.couponDownloadUserLimit == -1}">제한없음</c:when>
								<c:otherwise>${op:numberFormat(coupon.couponDownloadUserLimit)}장</c:otherwise>
							</c:choose>
							
							<c:if test="${coupon.couponMulitpleDownloadFlag == 'Y'}">
								<p class="tip">- 해당 쿠폰은 회원이 다운받은 쿠폰을 사용하지 않아도 여러장 다운 받을수 있습니다.</p>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">발급대상[회원]</td>
					<td>
						<div>
							${coupon.couponTargetUserTypeLabel}
							<c:if test='${coupon.couponTargetUserType eq "2"}'>
								&nbsp;<button type="button" class="btn btn-dark-gray btn-sm" onclick='Common.popup("/opmanager/coupon-use/popup/target-user/${coupon.couponId}", "target-user", 750, 600, 1);'>확인</button>
							</c:if>
						</div>
					</td>
					<td class="label">발급대상[상품]</td> 
					<td>
						<div>
							${coupon.couponTargetItemTypeLabel}
							<c:if test='${coupon.couponTargetItemType eq "2"}'>
								&nbsp;<button type="button" class="btn btn-dark-gray btn-sm" onclick='Common.popup("/opmanager/coupon-use/popup/target-item/${coupon.couponId}", "target-item", 750, 600, 1);'>확인</button>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">쿠폰 사용가능 상품 판매가 (개당)</td>
					<td>
						<div>
							
							<c:choose>
								<c:when test="${coupon.couponPayRestriction == -1}">제한없음</c:when>
								<c:otherwise>${op:numberFormat(coupon.couponPayRestriction)}원</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td class="label">% 할인 최대 금액 (개당)</td>
					<td>
						<div>
							<c:choose>
								<c:when test="${coupon.couponDiscountLimitPrice == -1}">제한없음</c:when>
								<c:otherwise>최대 ${op:numberFormat(coupon.couponDiscountLimitPrice)}원 까지 할인</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">할인 금액 설정 (개당)</td> 
					<td>
						<div>
							${op:numberFormat(coupon.couponPay)}<c:if test="${coupon.couponPayType == '1'}">${op:message('M00049')} <!-- 원 --></c:if>	
							<c:if test="${coupon.couponPayType == '2'}">%</c:if> ${op:message('M00452')} <!-- 할인 -->
						</div>
					</td>
					<td class="label">중복 할인 여부</td> 
					<td>
						<div>
							<c:choose>
								<c:when test="${coupon.couponConcurrently == '1'}">1개의 수량만 할인</c:when>
								<c:otherwise>구매 수량만큼 할인</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">다운로드 가능여부</td> <!-- 발급횟수 -->
					<td>
						<div>
							<c:choose>
								<c:when test="${coupon.couponFlag == 'Y'}">가능</c:when>
								<c:otherwise>불가능</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td class="label">직접 입력 쿠폰 번호</td> <!-- 직접 입력 쿠폰 번호 -->
					<td colspan=>
						<div>
							${coupon.directInputValue}
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div> <!-- // board_write -->

	<div class="board_write">
		<p class="pop_tit">${op:message('M01203')}</p> <!-- 쿠폰 발급/사용 목록 -->		
		<table class="board_list_table mt0" summary="${op:message('M01203')}">
			<caption>${op:message('M01203')}</caption>
			<thead>
				<tr>
					<th>순번</th> 
					<th>아이디</th> 
					<th>고객명</th>
					<th>발급상태</th>
					<th>다운로드 일자</th>
					<th>사용일자</th>
					<th>할인금액</th> 
					<th>${op:message('M00013')}</th> 			
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userCouponList}" var="list" varStatus="i">
					<tr>
						<td>${pagination.number + i.count}</td>
						<td>${list.loginId}</td>
						<td>
							<c:choose>
								<c:when test="${empty list.userName}">[회원정보 없음]</c:when>
								<c:otherwise>${list.userName}</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${list.couponDataStatusCode == '0'}">쿠폰 다운로드</c:when>
								<c:when test="${list.couponDataStatusCode == '1'}">쿠폰 사용</c:when>
								<c:when test="${list.couponDataStatusCode == '2'}">사용기간 만료</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</td>
						<td>${op:datetime(list.couponDownloadDate)}</td>
						<td>${op:datetime(list.couponUseDate)}</td>
						<td class="text-right">${op:numberFormat(list.discountAmount)}원</td>
						<td>
							<c:if test="${!empty list.orderCode}">
								<a href="/opmanager/order/all/order-detail/0/${list.orderCode}" target="_blank">${list.orderCode}</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${empty userCouponList}">
			<div class="no_content">
				<p>쿠폰을 다운로드한 내역이 없습니다.</p> 
			</div>
		</c:if>
	</div> <!--// board_write -->
	
	<page:pagination-manager />
	
	<div class="btn_center">
		<a href="/opmanager/coupon-use/list" class="btn btn-active">${op:message('M00480')}</a> <!-- 목록 -->
	</div>
	
</div> <!-- // item_list01 -->

