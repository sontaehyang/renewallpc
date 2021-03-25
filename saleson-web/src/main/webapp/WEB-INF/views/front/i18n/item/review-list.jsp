<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

			<table cellpadding="0" cellspacing="0" class="board-list">
				<caption>상품후기</caption>
				<colgroup>
					<col style="width:60px;">
					<col style="width:auto;">
					<col style="width:130px;">
					<col style="width:130px;">
					<col style="width:130px;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">순번</th>
						<th scope="col">이용후기</th>
						<th scope="col">평가</th>
						<th scope="col">작성자</th>
						<th scope="col">작성일</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach items="${reviewList}" var="itemReview" varStatus="i">
						<tr class="tit-off" id="questTit3">
							<td>${pagination.itemNumber - i.count}</td>
							<td class="tit subject">									
								<a href="#" class="review_subject">
									${itemReview.subject}
								</a>
							</td>	
							<td class="star">
								<div class="star_rating">
						 			<span style="width:${itemReview.score * 20}%"> </span><span class="point">${itemReview.score}</span>
						 		</div>
							</td>
							<td>${itemReview.maskUsername}</td>
							<td>${op:date(itemReview.createdDate)}</td>			
						</tr>
						<tr class="view-off" id="quest3">
							<td colspan="5" class="question-open">	
								<div class="qna-a"> 
									<p>${op:nl2br(itemReview.content)}</p>
									<c:forEach items="${itemReview.itemReviewImages}" var="image" varStatus="i">
										<c:if test="${image.itemReviewImageId > 0}">
											<c:if test="${i.count == 1}">
												<br/><br/>
											</c:if>
											<img src="${image.imageSrc}" />
										</c:if>
									</c:forEach>
								</div>								
							</td>
						</tr>
						
					</c:forEach>
									  					
				</tbody>
			</table>
			
			<c:if test="${empty reviewList}">
			<div class="no_content">
				등록된 이용후기가 없습니다.
			</div>
			</c:if>
			<page:pagination />