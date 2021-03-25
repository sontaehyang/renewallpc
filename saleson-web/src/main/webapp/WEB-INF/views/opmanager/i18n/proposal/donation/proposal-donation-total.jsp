<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
	${searchParam.userType}
	<div>
		<p class="total"><em>총 기부금액</em> <span class="amount"><em>
			<c:choose>
				<c:when test="${!empty donationSums.totalDonationAmount}">
					<fmt:formatNumber value="${ donationSums.totalDonationAmount }" pattern="#,###" />
				</c:when>
				<c:otherwise>
				0
				</c:otherwise>
			</c:choose>
		</em>원</span></p>
		<div>
			<p>이체 기부금액 <span class="amount"><em>
				<c:choose>
					<c:when test="${!empty donationSums.custDonationAmount}">
						<fmt:formatNumber value="${ donationSums.custDonationAmount }" pattern="#,###" />
					</c:when>
					<c:otherwise>
					0
					</c:otherwise>
				</c:choose>
			</em>원</span></p>
			<p>희망별 기부금액 <span class="amount"><em>
				<c:choose>
					<c:when test="${!empty donationSums.kbDonationAmount}">
						<fmt:formatNumber value="${ donationSums.kbDonationAmount }" pattern="#,###" />
					</c:when>
					<c:otherwise>
					0
					</c:otherwise>
				</c:choose>
			</em>원</span></p>
		</div>
	</div>