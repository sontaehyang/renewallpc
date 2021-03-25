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
		<h3 class="mt20"><span>추천검색어 관리</span></h3>

		<form:form modelAttribute="island" method="post">
			<%--<form:hidden path="islandId" />--%>
			<form:hidden path="id" />
			<div class="board_write">
				<table class="board_write_table" summary="제주/도서산간 주소관리">
					<caption>제주/도서산간 주소관리</caption>
					<colgroup>
						<col style="width: 150px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">구분</td>
							<td colspan="3">
								<div>
									<form:radiobutton path="islandType" value="JEJU" title="제주" label="제주" checked="checked" />
									<form:radiobutton path="islandType" value="ISLAND" title="도서산간" label="도서산간" />
									<form:radiobutton path="islandType" value="QUICK" title="퀵" label="퀵" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">우편번호<span class="require">*</span></td>
							<td colspan="3">
								<div>
									<form:input path="zipcode" title="우편번호" class="required _number" maxlength="7" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">주소<span class="require">*</span></td>
							<td colspan="3">
								<div>
						 			<form:input path="address" class="required" maxlength="200" title="주소" cssStyle="width: 40%;"/>
					 			</div>
			 				</td>
						</tr>
						<tr id="island_extra_charge">
							<td class="label">추가금액<span class="require">*</span></td>
							<td colspan="3">
								<div>
									<form:input path="extraCharge" class="amount _number_comma" maxlength="5" title="추가금액" /> 원
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="btn_center" style="padding-right: 190px;">
				<button type="submit" class="btn btn-active">${op:message('M00088')}</button>	 <!-- 등록 -->
				<a href="${!empty requestContext.prevPageUrl ? requestContext.prevPageUrl : '/opmanager/search/list'}" class="btn btn-default">${op:message('M00037')}</a>	 <!-- 취소 -->
			</div>
		</form:form>
	</div> <!-- // item_list02 -->

<script type="text/javascript">

	$(function(){
		setExtraChargeDisplay();
		$('input[name="islandType"]').on('click', function() {
			setExtraChargeDisplay();
		});

		$("#island").validator(function() {
			Common.removeNumberComma();

            if ($('input[name="islandType"]:checked').val() == undefined) {
                alert('제주/도서산간 구분을 선택해 주세요.');
                return false;
            }
		});
	});

	function setExtraChargeDisplay() {
		var islandType = $('input[name=islandType]:checked').val();
		if (islandType == 'QUICK') {
			$('#island_extra_charge').show();
			$('#extraCharge').addClass('required');
		} else {
			$('#island_extra_charge').hide();
			$('#extraCharge').removeClass('required');
			$('#extraCharge').val(0);
		}
	}
</script>
