<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<div>
		<p class="total"><em>총 기부금액</em> <span class="amount"><em><fmt:formatNumber value="${ totalAmount.totalDonationAmount }" pattern="#,###" /></em>원</span></p>
		<div>
			<c:forEach items="${ regularGroupList }" var="groupList" varStatus="i" >
				<p> ${groupList.ngoUsername} <span class="amount"><em><fmt:formatNumber value="${ groupList.donationAmount }" pattern="#,###" /></em>원</span></p>
			</c:forEach>
		</div>
	</div>
	<c:if test="${!empty regularDonation}">
		<ul class="inbox">
			<c:forEach items="${regularDonation}" var="regularList" varStatus="i">
				<li><span><span>[확인대기]</span> ${regularList.ngoUsername} :</span> <fmt:formatNumber value="${ regularList.donationAmount }" pattern="#,###" />원 / <em>${regularList.regularMonth }월 내역</em> <a href="javascript:fn_ApprovalAndretrocede('${regularList.regularId}',${regularList.statusCode});"><span class="icon green">확인</span></a></li>
			</c:forEach>
		</ul>
	</c:if>