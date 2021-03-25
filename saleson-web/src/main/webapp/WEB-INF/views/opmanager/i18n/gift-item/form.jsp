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
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<div class="item_list">
		<h3 class="mt20"><span>사은품 관리</span></h3>

		<form:form modelAttribute="giftItem" method="post" enctype="multipart/form-data">
			<form:hidden path="id" />
			<form:hidden path="code" />
			<div class="board_write">
				<table class="board_write_table" summary="사은품 관리">
					<caption>사은품 관리</caption>
					<colgroup>
						<col style="width: 150px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">판매자</td>
							<td>
								<div>
									${seller.sellerName}
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">상품 코드</td>
							<td>
								<div>
									<c:choose>
										<c:when test="${giftItem.id > 0}">
											${giftItem.code}
										</c:when>
										<c:otherwise>
											등록시 생성됩니다.
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">사은품 명</td>
							<td>
								<div>
									<form:input path="name" maxlength="30" class="form-block required" title="사은품 명" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">사은품 금액</td>
							<td>
								<div>
									<input type="text" name="price" id="price" maxlength="8" value="${op:negativeNumberToEmpty(giftItem.price)}" class="amount required _number_comma" title="사은품 금액" /> 원
								</div>
							</td>
						</tr>
						<tr>
							<td class="label"><p>${op:message('M00983')}</p><p>(500px * 500px)</p></td> <!-- 상세이미지 -->
							<td>
								<div>

									<button type="button" id="add_detail_image_file" style="display:none" class="table_btn"><span>+ ${op:message('M00984')}</span></button> <!-- 이미지추가 -->
									<p class="text-info text-sm">
										* 사은품 이미지는 1개만 등록이 가능합니다.<br/>
										등록 / 수정시 이미지가 존재할 경우 이미지가 교체가 됩니다.
									</p>
									<p>
										<input type="file" name="imageFile" />
									</p>

									<ul id="item_details_images" class="sortable_item_image clear">
										<c:if test="${not empty giftItem.image}">
											<li id="item_image_id_0">
												<img src="${shop:loadImageBySrc(giftItem.imageSrc, "S")}" class="item_image size-100" alt="" />
												<span class="ordering">0</span>
												<a href="javascript:deleteItemImage();" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>

												<input type="hidden" name="image" value="${giftItem.image}" />
											</li>
										</c:if>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">적용 기간</td>
							<td>
								<div>
									<span class="datepicker">
										<form:input path="startDate" maxlength="8" class="datepicker valid-date" title="시작일" />
									</span>
									<c:set var="startTimeSufix" value="0000"/>
									<c:set var="endTimeSufix" value="5959"/>
									<form:select path="startTime" cssClass="valid-date">
										<form:option value="00${startTimeSufix}" label="00시" />
										<c:forEach varStatus="i" begin="1" end="23">
											<c:if test="${i.count < 10 }">
												<form:option value="0${i.count}${startTimeSufix}" label="0${i.count}시" />
											</c:if>
											<c:if test="${i.count >= 10 }">
												<form:option value="${i.count}${startTimeSufix}" label="${i.count}시" />
											</c:if>
										</c:forEach>
									</form:select>
									00분 00초
									<span class="wave">~</span>
									<span class="datepicker">
										<form:input path="endDate" maxlength="8" class="datepicker valid-date" title="종료일" />
									</span>
									<form:select path="endTime" cssClass="valid-date">
										<form:option value="00${endTimeSufix}" label="00시" />
										<c:forEach varStatus="i" begin="1" end="23">
											<c:if test="${i.count < 10 }">
												<form:option value="0${i.count}${endTimeSufix}" label="0${i.count}시" />
											</c:if>
											<c:if test="${i.count >= 10 }">
												<form:option value="${i.count}${endTimeSufix}" label="${i.count}시" />
											</c:if>
										</c:forEach>
									</form:select>
									59분 59초
									<span class="day_btns">
										<a href="javascript:;" id="clearValidDateTime" class="btn_date clear">상시</a>
									</span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="btn_center" style="padding-right: 190px;">
				<button type="submit" class="btn btn-active">${mode != "edit" ? op:message('M00088') : op:message('M00087')}</button>	 <!-- 등록 / 수정 -->
				<a href="${!empty requestContext.prevPageUrl ? requestContext.prevPageUrl : '${requestContext.managerUri}/gift-item/list'}" class="btn btn-default">${op:message('M00037')}</a>	 <!-- 취소 -->
			</div>
		</form:form>
	</div> <!-- // item_list02 -->

<script type="text/javascript">

	var DATA_FORM_OBJECT = $('#giftItem');

	$(function(){

		$('#clearValidDateTime').on('click', function() {
			DATA_FORM_OBJECT.find('input.valid-date').val('');
		});

		var confirmMessage = '사은품 정보를 '+ '${mode != "edit" ? op:message('M00088') : op:message('M00087')}' + '하시겠습니까?';

		DATA_FORM_OBJECT.validator(function() {

			if (!confirm(confirmMessage)) {
				return false;
			}

			Common.removeNumberComma();

		});

	});

	function deleteItemImage() {

		var confirmMessage = '사은품 이미지를 삭제하시겠습니까?';

		if (confirm(confirmMessage)) {

			var param = {
				"id" : '${giftItem.id}'
			}

			$.post('${requestContext.managerUri}/gift-item/delete-image', param, function(response) {
				Common.responseHandler(response, function() {
					$('#item_details_images').empty();
				});
			});

		}

	}

</script>
