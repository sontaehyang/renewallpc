<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>

		
		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
		
		<form id="itemParam" method="post" action="/opmanager/item/review/list">
			<input type="hidden" name="where" value="${itemParam.where}">
			<input type="hidden" name="query" value="${itemParam.query}">
			<input type="hidden" name="sellerId" value="${itemParam.sellerId}">
			<input type="hidden" name="reviewDisplayFlag" value="${itemParam.reviewDisplayFlag}">
			<input type="hidden" name="recommendFlag" value="${itemParam.recommendFlag}">
			<input type="hidden" name="searchStartDate" value="${itemParam.searchStartDate}">
			<input type="hidden" name="searchEndDate" value="${itemParam.searchEndDate}">
			<input type="hidden" name="reviewScore" value="${itemParam.reviewScore}">
		</form>
		<form:form modelAttribute="itemReview" method="post" enctype="multipart/form-data">
			<form:hidden path="itemReviewId" />
			<form:hidden path="item.itemName" />
			<h3><span>${op:message('M00756')}</span></h3> <!-- 이용후기(리뷰) 관리 -->
			<div class="board_write">
				<table class="board_write_table">
					<caption>${op:message('M00765')}</caption> <!-- 이용후기(리뷰) 수정 --> 
					<colgroup>
						<col style="width:150px;">
						<col style="width:auto;">
						<col style="width:150px;">
						<col style="width:auto;">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00472')}</td> <!-- 작성자 --> 
							<td>
								<div>
									${itemReview.userName}
									<form:input type="hidden" path="loginId" value="${itemReview.loginId}" />
							    </div>
							</td>
							<td class="label">${op:message('M00276')}</td> <!-- 작성일 --> 
							<td>
								<div>
									${op:datetime(itemReview.createdDate)}				 
							    </div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00018')}/${op:message('M00019')}</td> <!-- 상품명 -->  <!-- 상품번호 --> 
							<td colspan="3">
								<div>
									<c:choose>
										<c:when test='${op:property("saleson.view.type") eq "api"}'>
											<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${itemReview.item.itemUserCode}" target="_blank">${itemReview.item.itemName} [ ${itemReview.item.itemUserCode} ]</a>
										</c:when>
										<c:otherwise>
											<a href="/products/view/${itemReview.item.itemUserCode}" target="_blank">${itemReview.item.itemName} [ ${itemReview.item.itemUserCode} ]</a>
										</c:otherwise>
									</c:choose>

									<c:if test='${itemReview.displayOptionsFlag eq "Y" and not empty itemReview.options}'>
										<br/>${shop:viewOptionTextNoUl(itemReview.options)}
									</c:if>

								</div>
							</td>						 
						</tr>
						<tr>
							<td class="label">${op:message('M00766')}</td> <!-- 만족도 --> 
							<td>
								<div>
									<form:select path="score" title="${op:message('M00767')}"><!-- 만족도 선택 --> 
										<form:option value="1">★</form:option>
										<form:option value="2">★★</form:option>
										<form:option value="3">★★★</form:option>
										<form:option value="4">★★★★</form:option>
										<form:option value="5">★★★★★</form:option>
									</form:select>
								</div>
							</td>
							<td class="label">도움됐어요</td> <!-- 만족도 -->
							<td>
								<div>
									${op:numberFormat(itemReview.likeCount)}
								</div>
							</td>
						</tr>
						<%--<tr>
							<td class="label">${op:message('M00275')}</td> <!-- 제목 --> 
							<td colspan="3">
								<div>
									<form:input type="text" path="subject" title="${op:message('M00275')}" class="nine required" />
								</div>
							</td>						 
						</tr>--%>
						<tr>
							<td class="label">${op:message('M00006')}</td> <!-- 내용 --> 
							<td colspan="3">
								<div>
									<form:textarea path="content" cols="30" rows="6" class="w90 required" title="${op:message('M00006')}" />

									<!-- 리뷰 이미지 -->
									<c:forEach items="${itemReview.itemReviewImages}" var="image" varStatus="i">
										<c:if test="${image.itemReviewImageId > 0}">
											<div id="item-reivew-image-${image.itemReviewImageId}">
												<br/>${image.reviewImage}
												<a href="javascript:;" class="delete_image" data-id="${image.itemReviewImageId}" data-image="${image.reviewImage}">[파일삭제]</a>
												<br/><img src="${image.imageSrc}" />
											</div>
										</c:if>
									</c:forEach>
							    </div>
							</td>							
						</tr>
						<%-- 
						<tr>
							<td class="label">첨부이미지</td> <!-- 제목 --> 
							<td colspan="3">
								<div>
									<img src="${itemReview.imageSrc}" class="item_image size-100" alt="" />&nbsp;&nbsp;&nbsp;&nbsp;
									${itemReview.reviewImage}
								</div>
							</td>
						</tr>
						--%>
						<tr>
							<td class="label">${op:message('M00191')}</td> <!-- 공개유무 -->
							<td colspan="3">
								<div>
									<form:radiobutton path="displayFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 --> 
									<form:radiobutton path="displayFlag" value="N" label="${op:message('M00097')}" /> <!-- 비공개 -->
									<form:input type="hidden" path="pointPayment" />
							    </div>
							</td>							
						</tr>
						<tr>
							<td class="label">메인공개 유무</td>
							<td colspan="3">
								<div>
									<form:radiobutton path="recommendFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 -->
									<form:radiobutton path="recommendFlag" value="N" label="${op:message('M00097')}" /> <!-- 비공개 -->
							    </div>
							</td>
						</tr>
						<tr>
							<td class="label">관리자 댓글</td> <!-- 내용 -->
							<td colspan="3">
								<div>
									<form:textarea path="adminComment" cols="30" rows="6" class="w90" title="${op:message('M00006')}" />
								</div>
							</td>
						</tr>
					</tbody>					  
				</table>								 							
			</div> <!-- // board_write -->
			
			<!-- 버튼시작 -->		 
			<div class="btn_center">
				<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 --> 
				<%-- <a href="/opmanager/item/review/list" class="btn btn-default"><span>${op:message('M00480')}</span></a>	 <!-- 목록 --> --%> 			 
				<a href="javascript:reviewList();" class="btn btn-default"><span>${op:message('M00480')}</span></a>	 <!-- 목록 --> 			 
			</div>			 
			<!-- 버튼 끝-->
		</form:form>
		
<!--<module:smarteditorInit />
<module:smarteditor id="content" />-->
		
<script type="text/javascript">

$(function() {

	Common.checkedMaxStringLength($('#adminComment'), null, 1000);

	// validator
	try{
		$('#itemReview').validator(function() {
		});
	} catch(e) {
		alert(e.message);
	}
	
	deleteItemReviewImage();
});

function reviewList() {
	location.href='/opmanager/item/review/list';
}

function deleteItemReviewImage() {
	$('.delete_image').on('click', function() {
		var param = {
			'itemReviewId': $('#itemReviewId').val(),
			'itemReviewImageId': $(this).data('id'),
			'reviewImage': $(this).data('image')
		};

		if(confirm("이미지가 실제로 삭제됩니다.(복구불가) \n삭제하시겠습니까?")) {
			$.post("/opmanager/item/delete-item-review-image", param, function(resp){
				Common.responseHandler(resp, function(){
					$("#item-reivew-image-" + param.itemReviewImageId).remove();
					alert("이미지가 삭제되었습니다.");
				});
			});
		}
	});
}
</script>
