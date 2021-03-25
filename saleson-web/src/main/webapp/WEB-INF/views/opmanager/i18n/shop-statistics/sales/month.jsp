<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">매출통계</a> &gt; <a href="#" class="on">월별 매출</a>
</div>
<!-- 본문 -->
<div class="statistics_web">
	<h3><span>월별 매출</span></h3>
	<form:form modelAttribute="statisticsParam" method="get" >
		<div class="board_write">						
			<table class="board_write_table" summary="${op:message('M01391')}"> <!-- 월별 매출 -->
				<caption>${op:message('M01391')}</caption> <!-- 월별 매출 -->
				<colgroup>
					<col style="width: 140px;">
					<col style="width: auto;"> 
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M01347')}</td> <!-- 기간 -->
						<td>
					 		<div>
								<select title="${op:message('M01392')}" name="startYear"> <!-- 시작 년도 --> 
									<c:forEach begin="2010" end="${lastYear}" varStatus="i">
										<option value="${i.index}" ${op:selected(i.index,statisticsParam.startYear) }>${i.index }</option>
									</c:forEach>
								</select> ${op:message('M01076')}<!-- 년 --> 
								
								<select title="${op:message('M01393')}" name="startMonth"> <!-- 시작 월 -->
									<c:forEach begin="1" end="12" varStatus="i">
										<c:if test="${i.index < 10 }">
											<c:set var="month">0${i.index}</c:set>
											<option value="0${i.index}" ${op:selected(month,statisticsParam.startMonth) }>0${i.index }</option>
										</c:if>
										<c:if test="${i.index >= 10 }">
											<option value="${i.index}" ${op:selected(i.index,statisticsParam.startMonth) }>${i.index }</option>
										</c:if>
									</c:forEach>
								</select> ${op:message('M01077')}<!-- 월 -->
								
								~
								
								<select title="${op:message('M01394')}" name="endYear"> <!-- 종료 년도 -->
									<c:forEach begin="2010" end="${lastYear}" varStatus="i">
										<option value="${i.index }" ${op:selected(i.index,statisticsParam.endYear) } >${i.index }</option>
									</c:forEach>
								</select> ${op:message('M01076')}<!-- 년 --> 
								
								<select title="${op:message('M01396')}" name="endMonth"> <!-- 종료 월 -->
									<c:forEach begin="1" end="12" varStatus="i">
										<c:if test="${i.index < 10 }">
											<c:set var="month">0${i.index}</c:set>
											<option value="0${i.index}" ${op:selected(month,statisticsParam.endMonth) }>0${i.index }</option>
										</c:if>
										<c:if test="${i.index >= 10 }">
											<option value="${i.index}" ${op:selected(i.index,statisticsParam.endMonth) }>${i.index }</option>
										</c:if>
									</c:forEach>
								</select> ${op:message('M01077')}<!-- 월 -->
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
					</tr>
					
					<%-- 
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
								<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-gradient btn-xs">검색</a>
							</div>
						</td>
					</tr>
					--%>
				</tbody>
			</table>
		
		</div> <!-- // board_write -->
		
		<div class="btn_all">
			<!-- <div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
			</div> -->
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</button>
			</div>
		</div>
	</form:form>

	<div class="sort_area mt30">
		<div class="left">
			<span>${op:message('M01363')} : <span class="font_b">${op:numberFormat(total.totalCount)}</span>${op:message('M00272')} (${op:message('M01364')} : ${op:numberFormat(total.saleCount)}${op:message('M00272')}, ${op:message('M01365')} : ${op:numberFormat(total.cancelCount)}${op:message('M00272')})</span> | <span>${op:message('M01366')} : <span class="font_b">${op:numberFormat(total.totalPayAmount)}</span>${op:message('M00814')}</span>
		</div>
	</div>
	
	<div class="board_list">
		
		<c:set var="viewType" value="month" scope="request" /> 
		<jsp:include page="../include/date-list.jsp" />
		
		<c:if test="${empty dateList }">
			<div class="no_content">
				<p>
					${op:message('M00591')} <!-- 등록된 데이터가 없습니다. -->
				</p>
			</div>
		</c:if>

		<sec:authorize access="hasRole('ROLE_EXCEL')">
			<div class="btn_all">
				<div class="right">
					<a href="/opmanager/shop-statistics/sales/month/excel-download?${queryString}"  class="btn btn-success btn-sm"><span class="glyphicon glyphicon-save"></span>${op:message('M00254')}</a> <!-- 엑셀 다운로드 -->
				</div>
			</div>
		</sec:authorize>
		
	</div>
	

</div>

<script type="text/javascript">
	function sellerSeller(sellerId) {
		$('#sellerId').val(sellerId)
	}
</script>
