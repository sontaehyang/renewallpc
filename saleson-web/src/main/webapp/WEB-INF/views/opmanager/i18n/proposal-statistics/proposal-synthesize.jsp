<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<div class="title_area">
	<h4>기부 종합 통계</h4>
	<div class="right">
		<span>년도</span>
		<select name="donationYear" id="donationYear">
			<c:forEach begin="2013" end="${currentYear}" varStatus="i">
				<c:set var="selected" value="" />
				<c:if test="${proposalStatistics.donationYear == i.index}">
					<c:set var="selected" value="selected='selected'" />
				</c:if>
				<option value="${i.index}" ${selected}>${i.index}</option>
			</c:forEach>
		</select>
		<a href="javascript:;" class="btn_print"><img src="/content/opmanager/images/icon/icon_print.png" alt="" /> 화면인쇄</a>
	</div>
</div>
<div class="chart_area">
	<div class="ratio">
		<div>
			<div class="info">
				<p><span class="label">${searchYear}년 목표</span> <span class="amount"><em><fmt:formatNumber value="${ proposalTarget.targetAmount }" pattern="#,###" /></em>원</span></p>
				<p class="present"><span class="label">현재 모금액</span> <span class="amount"><em><fmt:formatNumber value="${ proposalTarget.donationAmount }" pattern="#,###" /></em>원</span></p>
				<p class="goal_ratio"><span class="label">목표 달성율</span> <span class="amount"><em><fmt:formatNumber value="${ proposalTarget.donationParticipationRate }" pattern="###,###.##" /></em>%</span></p>
			</div>
			<div class="chart_vertical">
				<table>
					<tr>
						<c:forEach begin="1" end="12" step="1" varStatus="i">
							<td><div>
								<p class="name">${i.index}월</p>
								<div class="bar_wrap">
									<c:forEach items="${proposalStatisticsList}" var="list" varStatus="j"> 
										<fmt:parseNumber value="${ list.donationMonth }" var="integerDonationMonth" />
										<c:if test="${i.index == integerDonationMonth }">
										<c:set var="donationPercent" value="${(list.totalDonationAmount/proposalTarget.targetAmount) * 100}" />
										<p class="bar" style="height: <fmt:parseNumber value="${ donationPercent-(donationPercent%1) }" pattern="0" />%">
											<span class="number"><fmt:parseNumber value="${ donationPercent-(donationPercent%1) }" pattern="0" /></span>
										</p>
										</c:if>
									</c:forEach>
								</div>
							</div></td>
						</c:forEach>
					</tr>
				</table>
				<div class="bar_bg">
					<p class="unit">(%)</p>
					<div class="grid">
						<c:forEach begin="0" end="${ofc.STEP * 5}" varStatus="i" step="${ofc.STEP}">
							<div><p>${(ofc.STEP * 5) - i.index }</p></div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="headcount">
		<div>
			<div class="info">
				<p>
					<span class="present"><span class="label">누적 참여자</span> <span class="amount"><em><fmt:formatNumber value="${proposalStatisticsTotal}" pattern="#,###"  /></em>명</span></span>
					<span class="sort">
						<input type="radio" name="userType"  value="" id="temp1"  checked="checked"  /> <label for="temp1">전체</label>
						<input type="radio" name="userType"   value="1" id="temp2" <c:if test="${proposalStatistics.userType == 1}"> checked="checked" </c:if> /> <label for="temp2">고객</label>
						<input type="radio" name="userType"   value="2" id="temp3" <c:if test="${proposalStatistics.userType == 2}"> checked="checked" </c:if> /> <label for="temp3">직원</label>
					</span>
				</p>
			</div>
			<div class="chart_vertical">
				<table>
					<tr>
						<c:forEach begin="1" end="12" step="1" varStatus="i">
							<td><div>
								<p class="name">${i.index}월</p>
								<div class="bar_wrap">
									<c:forEach items="${proposalStatisticsList}" var="list" varStatus="j"> 
										<fmt:parseNumber value="${ list.donationMonth }" var="integerDonationMonth" />
										<c:if test="${i.index == integerDonationMonth }">
											<p class="bar" style="height: ${(list.proposalDonationCount/(ofcMaxCount.STEP * 5))*100}%">
												<span class="number">${op:numberFormat(list.proposalDonationCount)}</span>
											</p>
										</c:if>
									</c:forEach>
								</div>
							</div></td>
						</c:forEach>
					</tr>
				</table>
				<div class="bar_bg">
					<p class="unit">(명)</p>
					<div class="grid">
						<c:forEach begin="0" end="${ofcMaxCount.STEP * 5}" varStatus="i" step="${ofcMaxCount.STEP}">
							<div><p>${op:numberFormat((ofcMaxCount.STEP * 5) - i.index) }</p></div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	/* 화면인쇄 */
	$(".btn_print").on('click', function(e){
		window.print();
		return false;
	});

	$("select[name=donationYear]").on("change",function(){
		proposalStatisticsSubmit();
	});
	
	$("input:radio[name='userType']").on("click",function(){
		proposalStatisticsSubmit();
	});

});
</script>