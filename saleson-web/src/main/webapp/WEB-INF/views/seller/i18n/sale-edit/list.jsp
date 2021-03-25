<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>


<div class="item_list">
	<h3><span>상품리스트</span></h3>

	<form:form modelAttribute="itemSaleEditParam" method="get">

		<div class="board_write">
			<table class="board_write_table" summary="상품리스트">
				<caption>상품리스트</caption>
				<colgroup>
					<col style="width: 150px" />
					<col style="width: auto;" />
					<col style="width: 150px" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
				<tr>
					<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
					<td colspan="3">
						<div>
							<form:select path="where" title="상세검색 선택">
								<form:option value="ITEM_CODE">${op:message('M00783')} <!-- 상품코드 --></form:option>
								<form:option value="ITEM_NAME">${op:message('M00018')} <!-- 상품명 --></form:option>
							</form:select>
							<form:input path="query" class="w360" title="상세검색 입력" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">가격요청상태</td>
					<td>
						<div>
							<p>
								<form:radiobutton path="status" value="" label="${op:message('M00039')}" checked="checked"/> <!-- 전체 -->
								<form:radiobutton path="status" value="0" label="승인대기" />
								<form:radiobutton path="status" value="1" label="승인완료" />
								<form:radiobutton path="status" value="2" label="승인거절" />
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">가격변경요청일</td>
					<td colspan="3">
						<div>
							<span class="datepicker"><form:input path="searchStartDate" maxlength="8" class="datepicker" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="searchEndDate" maxlength="8" class="datepicker" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<span class="day_btns">
												<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 -->
												<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 -->
												<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 -->
												<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 -->
												<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 -->
												<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
											</span>
						</div>
					</td>
				</tr>
				</tbody>
			</table>

		</div> <!-- // board_write -->

		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/seller/item/sale-edit/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>
	</form:form>



	<div class="board_list">
		<form id="listForm">
			<table class="board_list_table" summary="전체상품리스트">
				<caption>전체상품리스트</caption>
				<colgroup>
					<col style="width: 30px" />
					<col style="width: 60px;" />
					<col style="width: 150px;" />
					<col style="" />
					<col style="width: 100px" />
					<col style="width: 100px" />
					<col style="width: 150px;" />
					<col style="width: 100px;" />
				</colgroup>
				<thead>
				<tr>
					<th><input type="checkbox" id="check_all" title="체크박스" /></th>
					<th>${op:message('M00200')}</th> <!-- 순번 -->
					<th>가격변경요청상태</th>
					<th>상품</th>
					<th>${op:message('M00786')} <!-- 판매가격 --></th>
					<th>정가</th>
					<%-- <th>원가</th> --%>
					<th>${op:message('M00202')} <!-- 등록일 --></th>
					<th>${op:message('M00590')}</th>	 <!-- 관리 -->
				</tr>
				</thead>
				<tbody class="sortable">

				<c:forEach items="${list}" var="item" varStatus="i">

					<tr>
						<td><input type="checkbox" name="id" value="${item.itemSaleEditId}" class="${item.itemCode}" title="" /></td>
						<td>
							<c:choose>
								<c:when test="${itemSaleEditParam.orderBy == 'ORDERING' && itemSaleEditParam.sort == 'ASC'}">
									${pagination.number + i.count}
								</c:when>
								<c:otherwise>
									${pagination.itemNumber - i.count}
								</c:otherwise>
							</c:choose>
							<p style="padding-top: 5px;">
								<c:choose>
									<c:when test='${op:property("saleson.view.type") eq "api"}'>
										<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
									</c:when>
									<c:otherwise>
										<a href="/products/preview/${item.itemCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
										<a href="/m/products/preview/${item.itemCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="" /></a>
									</c:otherwise>
								</c:choose>
							</p>
						</td>
						<td>
							<a href="/seller/item/sale-edit/view/${item.itemSaleEditId}">
								<c:choose>
									<c:when test="${item.status == '0'}">
										승인대기
									</c:when>
									<c:when test="${item.status == '1'}">
										승인완료
									</c:when>
									<c:when test="${item.status == '2'}">
										승인거절
									</c:when>
								</c:choose>
							</a>
						</td>
						<td class="left">
							<c:choose>
							<c:when test="${item.status == '0'}">
							<a href="/seller/item/sale-edit/update/${item.itemSaleEditId}">[${item.itemCode}]<br/> ${item.itemName}</a></td>
						</c:when>
						<c:otherwise>
							${item.itemName}
						</c:otherwise>
						</c:choose>
						<td>${op:numberFormat(item.salePrice)}원</td>
						<td>
							<c:choose>
								<c:when test="${item.itemPrice != ''}">
									${op:numberFormat(item.itemPrice)}원
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
							</c:choose>
						</td>
							<%-- <td>${op:numberFormat(item.costPrice)}원</td> --%>
						<td>${op:date(item.createdDate)}</td>
						<td>
							<c:if test="${item.status == '0'}">
								<a href="/seller/item/sale-edit/update/${item.itemSaleEditId}" class="btn btn-gradient btn-xs">수정</a>
							</c:if>
							<a href="#" class="delete_item btn btn-gradient btn-xs" style="margin-top: 1px;">${op:message('M00074')}</a> <!-- 삭제 -->
						</td>
					</tr>
				</c:forEach>

				</tbody>
			</table>
		</form>

		<c:if test="${empty list}">
			<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>
		</c:if>

		<div class="btn_all">
			<div class="btn_left mb0">
				<button type="button" class="delete_item btn btn-default btn-sm">일괄삭제</button>
			</div>
		</div>
		<page:pagination-manager />

	</div> <!-- // board_list -->

</div>

<style>
	td {background: #fff;}
	.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
		opacity: .5;}



</style>
<script type="text/javascript">
    $(function() {

        // 상품삭제
        $('.delete_item').on('click', function(e) {
            e.preventDefault();
            $('#check_all').prop("checked", false);
            $(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
            $(this).closest("tr").find('input[name=id]').prop("checked", true);

            Common.updateListData("/seller/item/sale-edit/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
        });

        Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');

    });

</script>

