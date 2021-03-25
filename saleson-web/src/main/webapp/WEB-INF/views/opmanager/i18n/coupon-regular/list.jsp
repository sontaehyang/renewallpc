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
		<h3><span>정기발행${op:message('M01137')}</span></h3> <!-- 쿠폰리스트 --> 
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
										<form:radiobutton path="dataStatusCode" label="발행전" value="0" /> <!-- 사용중 --> 
										<form:radiobutton path="dataStatusCode" label="발행" value="1" /> <!-- 사용중지 --> 
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
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/coupon-regular/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>
		
		
			<div class="count_title mt20">
				<h5>
					${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
				</h5>
			</div>
		</form:form>
		
		<div class="board_list mt20">
			<form id="listForm">
				<table class="board_list_table" summary="${op:message('M01146')}">
					<caption>${op:message('M01146')}</caption> <!-- 전체상품리스트 -->
					<colgroup>
						<col style="width: 30px;" />
						<col style="width: 40px;" />
						<col />
						<col />
						<col />
						<col />
						<col style="width: 80px;"/>
						<col />
						<col style="width: 80px;" />
						<col style="width: 170px;" />
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" id="check_all" title="체크박스" /></th>
							<th>번호</th>
							<th>쿠폰종류</th>
							<th>${op:message('M00879')}</th> <!-- 쿠폰명 -->
							<th>할인 정보</th> 
							<th>정기발행 기간</th>
							<th>적용대상</th>
							<th>발행일자</th>
							<th>상태</th>
							<th>${op:message('M00590')}</th> <!-- 관리 -->
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${couponList}" var="list" varStatus="i">
							<tr>
								<td>
									<c:if test="${list.dataStatusCode == '0'}">
										<input type="checkbox" name="id" value="${list.couponId}" />
									</c:if>
								</td>
								<td>${pagination.itemNumber - i.count}</td>
								<td>${list.couponTargetTimeTypeLabel}</td>
								<td>${list.couponName}</td>
								<td>
									${op:numberFormat(list.couponPay)}
									<c:choose>
										<c:when test="${list.couponPayType == '1'}">원 </c:when>
										<c:otherwise>
											% <c:if test="${list.couponDiscountLimitPrice != -1}">, 최대 ${op:numberFormat(list.couponDiscountLimitPrice)}원 까지 </c:if>
										</c:otherwise>
									</c:choose>
									${op:message('M00452')}
								</td>
								<td>
									<c:choose>
										<c:when test="${list.couponIssueType == '0'}">제한없음</c:when>
										<c:otherwise>${op:date(list.couponIssueStartDate)} ~ ${op:date(list.couponIssueEndDate)}</c:otherwise>
									</c:choose>
								</td>
								<td>${list.couponTargetUserTypeLabel}</td>
								<td>
									${op:date(list.createdDate)}
								</td>
								<td>
									<c:choose>
										<c:when test="${list.dataStatusCode == '0'}">발행전</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${list.couponFlag == 'Y'}">발행중</c:when>
												<c:otherwise>발행중지</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									<p>
										<c:choose>
											<c:when test="${list.couponFlag == 'Y'}"><a href="javascript:;" class="btn btn-active btn-xs op-coupon-download-stop" data-coupon-id="${list.couponId}">발행 중지</a></c:when>
											<c:otherwise><a href="javascript:;" class="btn btn-default btn-xs op-coupon-download-start" data-coupon-id="${list.couponId}">발행 시작</a></c:otherwise>
										</c:choose>
									</p>
								</td>
								<td>
									<a href="/opmanager/coupon-regular/copy-create/${list.couponId}?url=${requestContext.currentUrl}" class="btn btn-gradient btn-xs">${op:message('M00788')}</a> <!-- 복사 -->
									<c:if test="${list.dataStatusCode == '0'}">
										<a href="/opmanager/coupon-regular/edit/${list.couponId}?url=${requestContext.currentUrl}" class="btn btn-gradient btn-xs">수정</a>
										<a href="javascript:;" class="btn btn-active btn-xs op-coupon-publish" data-coupon-id="${list.couponId}">발행</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			<c:if test="${empty couponList}">
				<div class="no_content">
					<p>${op:message('M00591')}</p> <!-- 등록된 데이터가 없습니다. --> 
				</div>
			</c:if>
			
			
			<div class="btn_all">
				<div class="btn_left mb0">
					<button type="button" id="delete_list_data" class="btn btn-default btn-sm">선택삭제</button>
				</div>
				<div class="btn_right mb0">
					<a href="/opmanager/coupon-regular/create?url=${requestContext.currentUrl}" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 쿠폰 등록</a>
				</div>
			</div>
			
			<page:pagination-manager />
			<div class="btn_all">
			</div>
		</div> <!-- // board_list -->
		
	</div> <!-- // item_list01 -->
	
<script type="text/javascript">
	$(function(){

		// 쿠폰 발행
		$('.op-coupon-publish').on('click', function(){
			if (!confirm("해당 쿠폰을 발행 처리 하시겠습니까?")) {
				return;
			}
			
			var couponId = $(this).data('couponId');
			couponUpdate(couponId, 'publish');
		});
		
		// 쿠폰 다운로드 중지
		$('.op-coupon-download-stop').on('click', function(){
			if (!confirm("해당 쿠폰을 다운로드 중지 처리 하시겠습니까?")) {
				return;
			}
			
			var couponId = $(this).data('couponId');
			couponUpdate(couponId, 'download-stop');
		});
		
		// 쿠폰 다운로드 시작
		$('.op-coupon-download-start').on('click', function(){
			if (!confirm("해당 쿠폰을 다운로드 시작 처리 하시겠습니까?")) {
				return;
			}
			
			var couponId = $(this).data('couponId');
			couponUpdate(couponId, 'download-start');
		});

		//목록데이터 - 삭제처리
		$('#delete_list_data').on('click', function() {
			Common.updateListData("/opmanager/coupon-regular/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
		});
		
	});
	
	function couponUpdate(couponId, mode) {
		$.post('/opmanager/coupon-regular/'+ mode +'/' + couponId, null, function(resp) {
			if (resp.isSuccess) {
				alert('처리 되었습니다.');
				location.reload();
			} else {
				alert(resp.errorMessage);
			}
		}, 'json');
	}

</script>

