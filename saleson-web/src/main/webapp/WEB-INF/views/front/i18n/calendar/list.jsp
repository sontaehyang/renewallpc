<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>

							<div class="calendar_top">
								<!-- a href="#" class="prev"><img src="/content/images/common/prev_month.gif" alt="prev"></a -->
								<p><span id="displayYear">${year}</span>. <span id="displayMonth">${month + 1}</span></p>
								<!-- a href="#" class="next"><img src="/content/images/common/next_month.gif" alt="next"></a -->
							</div>
							<table cellpadding="0" cellspacing="0" summary="calendar">
								<caption>calendar</caption>
								<colgroup>
									<col style="width:15%;">
									<col style="width:14%;">
									<col style="width:14%;">
									<col style="width:14%;">
									<col style="width:14%;">
									<col style="width:15%;">
								</colgroup>	
								<thead>
									<tr>
										<th scope="col" class="sun"><img src="/content/images/common/sun.gif" alt="Sun"></th>
										<th scope="col"><img src="/content/images/common/mon.gif" alt="Mon"></th>
										<th scope="col"><img src="/content/images/common/tue.gif" alt="Tue"></th>
										<th scope="col"><img src="/content/images/common/wed.gif" alt="Wed"></th>
										<th scope="col"><img src="/content/images/common/thu.gif" alt="Thu"></th>
										<th scope="col"><img src="/content/images/common/fri.gif" alt="Fri"></th>
										<th scope="col"><img src="/content/images/common/sat.gif" alt="Sat"></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<c:set var="dayCount" value="1" />
										
										<c:forEach begin="1" end="${start-1}" varStatus="index" >
										 	<td class="dafault">&nbsp;</td>
										 	<c:set var="dayCount">${dayCount + 1}</c:set>
										</c:forEach>
										
										
										<c:forEach begin="1" end="${endDay}" varStatus="index">
											<c:set var="day">DAY_${index.count}</c:set>
											<c:if test="${dayCount % 7 == 1 && dayCount > 1}">
												<tr>
											</c:if>
											
											
											
											<c:choose>
												<c:when test="${year == todayYear && month == todayMonth && date == index.count}">
													<c:set var="dayClass">today</c:set>
												</c:when>
												<c:when test="${dayCount % 7 == 1}">
													<c:set var="dayClass">sun</c:set>
												</c:when>
												<c:when test="${dayCount % 7 == 0}">
													<c:set var="dayClass">sat</c:set>
												</c:when>
												<c:otherwise>
													<c:set var="dayClass"></c:set>
												</c:otherwise>
											</c:choose>
											
											<c:choose>
												<c:when test="${!empty calendarList[day] && calendarList[day] == '0'}">
													<td class="${dayClass} holiday">${index.count}</td>
												</c:when>
												<c:when test="${!empty calendarList[day] && calendarList[day] == '1'}">
													<td class="${dayClass} shorten">${index.count}</td>
												</c:when>
												<c:otherwise>
													<td class="${dayClass}">${index.count}</td>
												</c:otherwise>
											</c:choose>
											
											
											<c:if test="${dayCount % 7 == 0 && dayCount > 1}">
												</tr>
											</c:if>
											
											<c:set var="dayCount">${dayCount + 1}</c:set>
										</c:forEach>
										
										<c:if test="${dayCount % 7 != 1}">
											<c:forEach begin="${(dayCount - 1) % 7}" end="6" varStatus="index">
												<td class="dafault">&nbsp;</td>
												
												<c:if test="${index.count == 6}">
													</tr>
												</c:if>
											</c:forEach>
				  						</c:if>
									

								</tbody>
							</table>	
							
	
