<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


			<div class="statistics_web">
				<h3><span>접속경로 통계</span></h3>
				<!--
				<p class="btns">
					<a href="#" class="btn_print"><img src="/content/images/opmanager/common/icon_print.png" alt="" /> 화면인쇄</a>
				</p>
				-->
				<div class="sort_area">
					<form:form modelAttribute="searchParam" method="get">
						<fieldset>
							<legend class="hidden">검색</legend>
							<!--
							<div class="left">
								<span>검색기간 : ${op:formatDate(searchParam.startDate,'-')} ~ ${op:formatDate(searchParam.endDate,'-')}</span>
							</div>
							 -->
							<div class="left" style="z-index:11">
								<span>${op:message('M01120')} <!-- 검색기간 -->: </span>
								<span class="datepicker"><form:input path="startDate" style="width:70px" maxlength="8" class="datepicker optional _date" title="검색 시작일" /></span>
								<span class="wave">~</span>
								<span class="datepicker"><form:input path="endDate" style="width:70px" maxlength="8" class="datepicker optional _date" title="검색 종료일"  /></span>
								<button type="submit" class="btn btn-dark-gray btn-xs"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
							</div>
						</fieldset>
					</form:form>
				</div>

				<h4>${op:message('M01123')}</h4> <!-- 유입 경로 통계 -->
				<div class="chart_horizontal">
					<ul style="z-index: -1">
						<c:forEach items="${domainList}" var="domain">
							<li>
								<p class="name">${domain.TITLE}</p>
								<div class="bar_wrap">
									<p class="bar yellow" style="width: ${domain.PERCENT}%">
										<c:choose>
											<c:when test="${domain.PERCENT < 70 }">
												<span class="number">${op:numberFormat(domain.VISIT_COUNT)} (${domain.PERCENT}%)</span>
											</c:when>
											<c:otherwise>
												<span class="number number_over">${op:numberFormat(domain.VISIT_COUNT)} (${domain.PERCENT}%)</span>
											</c:otherwise>
										</c:choose>

										<span class="tip"></span>
									</p>
								</div>
							</li>
						</c:forEach>

						<c:if test="${empty domainList}">
							<div class="no_content">통계 데이터가 없습니다.</div>
						</c:if>
					</ul>
					<div class="bar_bg">
						<div class="grid">
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
						</div>
						<p class="label label_0">0</p>
						<p class="label label_50">50%</p>
						<p class="label label_100">100%</p>
					</div>
				</div>

				<h4>${op:message('M01124')}</h4> <!-- 브라우저별 접속현황 -->
				<div class="chart_horizontal">
					<ul>
						<c:forEach items="${browserList}" var="browser">
						<li>
							<p class="name">${browser.TITLE}</p>
							<div class="bar_wrap">
								<p class="bar sky" style="width: ${browser.PERCENT}%">
									<c:choose>
										<c:when test="${browser.PERCENT < 70 }">
											<span class="number">${op:numberFormat(browser.VISIT_COUNT)} (${browser.PERCENT}%)</span>
										</c:when>
										<c:otherwise>
											<span class="number number_over">${op:numberFormat(browser.VISIT_COUNT)} (${browser.PERCENT}%)</span>
										</c:otherwise>
									</c:choose>



									<span class="tip"></span>
								</p>
							</div>
						</li>
						</c:forEach>

						<c:if test="${empty browserList}">
							<div class="no_content">${op:message('M00473')} <!-- 데이터가 없습니다. --></div>
						</c:if>

					</ul>
					<div class="bar_bg">
						<div class="grid">
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
						</div>
						<p class="label label_0">0</p>
						<p class="label label_50">50%</p>
						<p class="label label_100">100%</p>
					</div>
				</div>



				<h4>OS별 접속현황</h4>
				<div class="chart_horizontal">
					<ul>
						<c:forEach items="${osList}" var="os">
						<li>
							<p class="name">${os.TITLE}</p>
							<div class="bar_wrap">
								<p class="bar sky" style="width: ${os.PERCENT}%">
									<c:choose>
										<c:when test="${os.PERCENT < 70 }">
											<span class="number">${op:numberFormat(os.VISIT_COUNT)} (${os.PERCENT}%)</span>
										</c:when>
										<c:otherwise>
											<span class="number number_over">${op:numberFormat(os.VISIT_COUNT)} (${os.PERCENT}%)</span>
										</c:otherwise>
									</c:choose>



									<span class="tip"></span>
								</p>
							</div>
						</li>
						</c:forEach>

						<c:if test="${empty osList}">
							<div class="no_content">${op:message('M00473')} <!-- 데이터가 없습니다. --></div>
						</c:if>

					</ul>
					<div class="bar_bg">
						<div class="grid">
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
						</div>
						<p class="label label_0">0</p>
						<p class="label label_50">50%</p>
						<p class="label label_100">100%</p>
					</div>
				</div>
			</div>

<script type="text/javascript">
$(function() {
	EventHandler.calendarStartDateAndEndDateVaild();
	$('#searchParam').validator();


});
</script>
