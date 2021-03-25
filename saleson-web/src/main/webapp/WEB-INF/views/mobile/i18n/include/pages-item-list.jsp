<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>

	<c:forEach items="${featuredList}" var="list" varStatus="i">
		<c:choose>
			<c:when test="${featuredParam.featuredClass == 2}">
				<a href="${list.progression == '3' ? 'javascript:void(0);' : list.pageLink}" ${list.target} ${list.rel} class="pd_list ${list.progression == '3' ? 'end' : 'ing'}">
					<div class="img">
						<img src="/upload/featured/${list.featuredId}/featured/${list.featuredImage}" alt="${list.featuredName}">
					</div>
					<div class="pd_tit">
						<h2>${list.featuredName}</h2>
						<p>
							<c:choose>
								<c:when test="${not empty list.startDate or not empty list.endDate}">
									${op:date(list.startDate)} ~ ${op:date(list.endDate)}
								</c:when>
								<c:otherwise>
									상시 진행
								</c:otherwise>
							</c:choose>
						</p>
					</div>
				</a>
			</c:when>
			<c:otherwise>
				<a href="${list.pageLink}" ${list.target} ${list.rel} class="pd_list">
					<div class="img">
						<img src="/upload/featured/${list.featuredId}/featured/${list.featuredImage}" alt="${list.featuredName}">
					</div>
					<div class="pd_tit">
						<h2>${list.featuredName}</h2>
						<p>
							<c:choose>
								<c:when test="${not empty list.startDate or not empty list.endDate}">
									${op:date(list.startDate)} ~ ${op:date(list.endDate)}
								</c:when>
								<c:otherwise>
									상시 진행
								</c:otherwise>
							</c:choose>
						</p>
					</div>
				</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>