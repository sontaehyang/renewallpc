<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>


			
			
			<div id="day_stats">
				<h4>${year}년 ${month}월 일별 접속통계</h4>
				<div class="chart_vertical daily">
					<table>
						<tr>
							<c:forEach items="${dayStatsList}" var="dayStats">
								<td><div>
									<p class="name">${dayStats.displayDay}</p>
									<div class="bar_wrap">
										<p class="bar" style="height: ${dayStats.percent}%">
											<span class="number">${dayStats.percent}%<br />(${dayStats.visitCount})</span>
										</p>
									</div>
								</div></td>
							</c:forEach>
						</tr>
					</table>
					<div class="bar_bg">
						<div class="grid">
							<div><p>100%</p></div>
							<div><p>80%</p></div>
							<div><p>60%</p></div>
							<div><p>40%</p></div>
							<div><p>20%</p></div>
						</div>
					</div>
				</div>
			</div>
	