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
		<h3 class="mt20"><span>사은품 그룹 관리</span></h3>

		<form:form modelAttribute="giftGroup" method="post">
			<form:hidden path="id" />
			<div class="board_write">
				<table class="board_write_table" summary="사은품 그룹 관리">
					<caption>사은품 그룹 관리</caption>
					<colgroup>
						<col style="width: 150px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">사은품 그룹명</td>
							<td>
								<div>
									<form:input path="name" maxlength="30" class="form-block required" title="사은품 그룹명" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">그룹 타입</td>
							<td>
								<div>
									<form:radiobutton path="groupType" value="ORDER_PRICE" cssClass="required" title="그룹 타입" label="주문 금액별" checked="checked"/>
									<form:radiobutton path="groupType" value="ORDER" cssClass="required" title="그룹 타입" label="주문서 전체"/>
								</div>
							</td>
						</tr>
						<tr id="condition-order-price">
							<td class="label">그룹 조건</td>
							<td>
								<div>
									<c:set var="overOrderPrice" value='${giftGroup.groupType != "ORDER_PRICE" ? 0 : giftGroup.overOrderPrice}'/>
									<c:set var="overOrderPrice" value="${empty overOrderPrice ? 0 : overOrderPrice}"/>
									<input type="text" name="overOrderPrice" id="overOrderPrice" maxlength="8" value="${op:negativeNumberToEmpty(overOrderPrice)}" class="amount _number_comma" title="그룹 조건" /> 원 이상
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
									<span class="wave">~</span>
									<span class="datepicker">
										<form:input path="endDate" maxlength="8" class="datepicker valid-date" title="종료일" />
									</span>
									<span class="day_btns">
										<a href="javascript:;" id="clearValidDateTime" class="btn_date clear">상시</a>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">사은품</td>
							<td>
								<div>
									<p class="mb10">
										<button type="button" id="button_add_relation_item" class="table_btn" onclick="findGiftItem()"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
										<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('gift')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
									</p>

									<ul id="gift" class="sortable_item_relation">
										<li style="display: none;"></li>

										<c:forEach items="${giftGroup.itemList}" var="giftGroupItem" varStatus="i">

											<c:set var="item" value="${giftGroupItem.item}"/>
											<c:if test="${not empty item}">
												<li id="gift_item_${item.id}">
													<input type="hidden" name="giftItemIds" value="${item.id}" />
													<p class="image"><img src="${shop:loadImageBySrc(item.imageSrc, "XS")}" class="item_image size-100 none" alt="상품이미지" /></p>
													<p class="title">
														[${item.code}] ${item.name}
													</p>

													<c:choose>
														<c:when test="${not empty item.notProcessLabel}">
															<span class="error">[${item.notProcessLabel}]</span>
														</c:when>
														<c:otherwise>
															<span class="ordering">${i.count}</span>
														</c:otherwise>
													</c:choose>
													<a href="javascript:Shop.deleteRelationItem('gift_item_${item.id}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
												</li>
											</c:if>
										</c:forEach>

									</ul>
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

	var DATA_FORM_OBJECT = $('#giftGroup');

	$(function(){

		$('#clearValidDateTime').on('click', function() {
			DATA_FORM_OBJECT.find('input.valid-date').val('');
		});

		var confirmMessage = '사은품 그룹 정보를 '+ '${mode != "edit" ? op:message('M00088') : op:message('M00087')}' + '하시겠습니까?';

		DATA_FORM_OBJECT.validator(function() {

			if (!confirm(confirmMessage)) {
				return false;
			}

			Common.removeNumberComma();

		});

		// 관련상품 드레그
		$(".sortable_item_relation").sortable({
			placeholder: "sortable_item_relation_placeholder"
		});
	});

	function findGiftItem(){
		//setHeight();
		Shop.findGiftItem('gift');
	}

</script>
