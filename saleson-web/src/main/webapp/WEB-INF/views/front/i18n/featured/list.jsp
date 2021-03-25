<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="/" class="home"><span class="hide">home</span></a>
			<span>
				<c:choose>
					<c:when test="${featuredParam.featuredClass == 1}">기획전</c:when>
					<c:otherwise>이벤트</c:otherwise>
				</c:choose>
			</span>
		</div>
	</div><!-- // location_area E -->
	
	<div id="contents">
		<div class="event_banner">
			<c:choose>
				<c:when test="${featuredParam.featuredClass == 1}">
					<img src="/content/images/common/sample.jpg" alt="배너이미지">
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
		</div>
		
		<div class="event_list">
			<ul>
				<c:forEach items="${featuredList}" var="list" varStatus="i">
					<li>
						<c:choose>
							<c:when test="${featuredParam.featuredClass == 2 && list.progression == '3'}">
								<a href="javascript:void(0);" ${list.target} ${list.rel} class="end">
									<div class="thumbnail_wrap">
										<span class="thumbnail"><img src="/upload/featured/${list.featuredId}/featured/${list.featuredImage}" alt="event thumbnail" style="width: 100%; height: 100%;"></span>
									</div>
									<div class="event_info">
										<p class="name">${list.featuredName}</p>
										<p class="date">
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
								<a href="${list.pageLink}" ${list.target} ${list.rel}>
									<div class="thumbnail_wrap">
										<span class="thumbnail"><img src="/upload/featured/${list.featuredId}/featured/${list.featuredImage}" alt="event thumbnail" style="width: 100%; height: 100%;"></span>
									</div>
									<div class="event_info">
										<p class="name">${list.featuredName}</p>
										<p class="date">
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
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>