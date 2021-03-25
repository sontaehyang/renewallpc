<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
	.reject-message {
		position: absolute;
		margin-top: -255px;
		border: 3px solid #434755;
		display: none;
		background: #fff;
	}
	.reject-message h1 {
		border-top: 0;
	}
	.reject-message p {
		padding-bottom: 10px;
	}
	.reject-message .popup_contents {
		padding: 30px 20px !important;
	}



	#reject-message-wrap {
		border: 1px solid #ccc;
		overflow-y: auto;
		height: 100px;

	}
	.reject-message-layer {
		position: fixed;
		width: 420px;
		margin-left: -210px;
		margin-top: -150px;
		left: 50%;
		top: 50%;
		border: 3px solid #434755;
		display: none;
		background: #fff;
	}
	.reject-message-layer h1 {
		border-top: 0;
	}
	.reject-message-layer p {
		padding-bottom: 10px;
	}
	.reject-message-layer .popup_contents {
		padding: 30px 20px !important;
	}
	.data-status-message {
		display: none;
	}
	.show-reject-message {
		text-decoration: underline;
	}
	.show-reject-message:hover {
		color: #067aac;
	}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>


<div class="item_list">
	<h3><span>상품리스트</span></h3>

	<form:form modelAttribute="itemParam" method="get">
		<form:hidden path="categoryId" />

		<div class="board_write">
			<table class="board_write_table" summary="상품리스트">
				<caption>상품리스트</caption>
				<colgroup>
					<col style="width: 150px" />
					<col style="" />
					<col style="width: 150px" />
					<col style="width: 400px" />

				</colgroup>
				<tbody>
				<tr>
					<td class="label">${op:message('M00270')} <!-- 카테고리 --></td>
					<td colspan="3">
						<div>
							<form:select path="categoryGroupId" class="category">
								<option value="0">= ${op:message('M00076')} =</option> <!-- 팀/그룹 -->
								<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
									<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
										<optgroup label="${categoriesTeam.name}">
											<c:forEach items="${categoriesTeam.categoriesGroupList}" var="categoriesGroup">
												<c:if test="${categoriesGroup.categoryGroupFlag == 'Y'}">
													<form:option value="${categoriesGroup.categoryGroupId}" label="${categoriesGroup.groupName}" />
												</c:if>
											</c:forEach>
										</optgroup>
									</c:if>
								</c:forEach>

							</form:select>

							<form:select path="categoryClass1" class="category">
							</form:select>

							<form:select path="categoryClass2" class="category">
							</form:select>

							<form:select path="categoryClass3" class="category">
							</form:select>

							<form:select path="categoryClass4" class="category">
							</form:select>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
					<td>
						<div>
							<form:select path="where" title="상세검색 선택">
								<form:option value="ITEM_NAME">${op:message('M00018')} <!-- 상품명 --></form:option>
								<form:option value="ITEM_USER_CODE">${op:message('M00783')} <!-- 상품코드 --></form:option>
							</form:select>
							<form:input path="query" class="w360" title="상세검색 입력" />
						</div>
					</td>
					<td class="label">담당MD명</td>
					<td>
						<div>
							<input type="hidden" name="currentMdId" value="${seller.mdId}" />
							<input type="hidden" id="mdId" name="mdId" value="${seller.mdId}" />
							<form:input path="mdName" title="담당MD" readonly="true" />

							<button type="button" onclick="findMd('mdId')" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> MD검색</button>
							<button type="button" onclick="clearMd('mdId')" class="btn btn-gradient btn-sm"><span class="glyphicon glyphicon-remove"></span> 초기화</button>
						</div>
					</td>
				</tr>


				<tr>
					<td class="label">배송구분</td>
					<td>
						<div>
							<form:radiobutton path="deliveryType" value="" label="${op:message('M00039')}" checked="checked" /> <!-- 전체 -->
							<form:radiobutton path="deliveryType" value="1" label="본사배송" /> <!-- 인덱스 시킨다. -->
							<form:radiobutton path="deliveryType" value="2" label="업체배송" /> <!-- 인덱스 시키지 않는다. -->
						</div>
					</td>
					<td class="label">${op:message('M00784')} <!-- 가격 --></td>
					<td>
						<div>
							<form:select path="priceRange" title="가격대 선택">
								<option value="">${op:message('M00039')} <!-- 전체 --></option>
								<form:option value="0|10000" label="0～10000" />
								<form:option value="10000|50000" label="10000～50000" />
								<form:option value="50000|100000" label="50000～100000" />
								<form:option value="100000|1000000" label="100000～1000000" />
								<form:option value="1000000|90000000" label="1000000～" />
							</form:select>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">상태</td>
					<td>
						<div>
							<form:radiobutton path="dataStatusCode" value="" label="${op:message('M00039')}" checked="checked" /> <!-- 전체 -->
							<form:radiobutton path="dataStatusCode" value="20" label="등록대기" />
							<form:radiobutton path="dataStatusCode" value="21" label="등록보류" />
							<form:radiobutton path="dataStatusCode" value="30" label="재등록신청" />
						</div>
					</td>
					<td class="label">판매자</td>
					<td>
						<div>
							<select id="sellerId" name="sellerId" title="${op:message('M01630')}"> <!-- 판매자선택 -->
								<option value="0">${op:message('M00039')}</option> <!-- 전체 -->
								<c:forEach items="${sellerList}" var="list" varStatus="i">
									<c:choose>
										<c:when test="${itemParam.sellerId == list.sellerId}">
											<c:set var='selected' value='selected'/>
										</c:when>
										<c:otherwise>
											<c:set var='selected' value=''/>
										</c:otherwise>
									</c:choose>
									<option value="${list.sellerId}" ${selected}>${list.sellerName}</option>
								</c:forEach>
							</select>
							<a href="javascript:Common.popup('/opmanager/seller/find', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</a>
						</div>

					</td>

				</tr>
				<tr>
					<td class="label">상품수정일</td>
					<td>
						<div>
							<span class="datepicker"><form:input path="searchStartDate" maxlength="8" class="datepicker" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<span class="wave">~</span>
							<span class="datepicker"><form:input path="searchEndDate" maxlength="8" class="datepicker" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
							<span class="day_btns">
												<a href="javascript:;" class="btn_date clear">전체</a>
												<a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 -->
												<a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 -->
												<a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 -->
												<a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 -->
												<a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 -->
												<a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
											</span>
						</div>
					</td>
					<td class="label">과세구분</td>
					<td>
						<div>
							<form:select path="taxType">
								<form:option value="" label="전체" />
								<form:option value="1" label="과세" />
								<form:option value="2" label="면세" />
							</form:select>
						</div>
					</td>
				</tr>
				</tbody>
			</table>

		</div> <!-- // board_write -->

		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/item/seller/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>

		<div class="count_title mt20">
			<h5>
					${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 -->
			<span>

							<span>${op:message('M00052')} <!-- 출력수 --></span>
							<form:select path="itemsPerPage" title="출력수 선택">
								<form:option value="10" label="10" />
								<form:option value="20" label="20" />
								<form:option value="30" label="30" />
								<form:option value="50" label="50" />
								<form:option value="100" label="100" />
								<form:option value="500" label="500" />
								<form:option value="1000" label="1000" />
							</form:select>


						</span>
		</div>
	</form:form>

	<div class="board_list">
		<form id="listForm">
			<input type="hidden" id="processType" name="processType" />
			<input type="hidden" id="approvalMessage" name="approvalMessage" />
			<table class="board_list_table" summary="전체상품리스트">
				<caption>전체상품리스트</caption>
				<colgroup>
					<col style="width: 30px" />
					<col style="width: 60px;" />
					<col style="width: 80px;" />
					<col style="width: 60px;" />
					<col style="" />

					<col style="width: 50px" />
					<col style="width: 80px" />
					<col style="width: 80px" />
					<col style="width: 80px" />
					<col style="width: 50px" />

					<col style="width: 80px" />
					<col style="width: 125px" />

					<col style="width: 50px" />
					<col style="width: 55px;" />
					<col style="width: 55px;" />
					<col style="width: 90px;" />
					<col style="width: 60px;" />
					<col style="width: 65px;" />

				</colgroup>
				<thead>
				<tr>
					<th><input type="checkbox" id="check_all" title="체크박스" /></th>
					<th>${op:message('M00200')}</th> <!-- 순번 -->
					<th>상태</th>
					<th>${op:message('M00752')}</th> <!-- 이미지 -->
					<th>상품</th>
					<th>과세</th>
					<th>정가</th>
					<th>${op:message('M00786')} <!-- 판매가격 --></th>
					<th>공급가</th>
					<th>수익율</th>
					<th>배송비구분</th>
					<th>배송비</th>
					<th>옵션</th>
					<th>${op:message('M01462')} <!-- 재고수 --></th>
					<th>${op:message('M00191')} <!-- 공개유무 --></th>
					<th>
						<c:choose>
							<c:when test="${itemParam.orderBy == 'HITS'}">
								판매자/${op:message('M00685')} <!-- 조회수 -->
							</c:when>
							<c:otherwise>
								판매자/${op:message('M00202')} <!-- 등록일 -->
							</c:otherwise>
						</c:choose>
					</th>
					<th>변경사항</th>
					<th>담당MD</th>
				</tr>
				</thead>
				<tbody class="sortable">

				<c:forEach items="${list}" var="item" varStatus="i">
					<c:set var="displayFlagText">${op:message('M00096')}</c:set> <!-- 공개 -->
					<c:if test="${item.displayFlag == 'N'}">
						<c:set var="displayFlagText"><span style="color:#e84700">${op:message('M00097')}</span></c:set> <!-- 비공개 -->
					</c:if>

					<!-- 인덱스 시킴 -->
					<c:set var="noindexYn"><span style="color: #25A5DC">Y</span></c:set>	<!-- 인덱스 시킴. -->
					<c:if test="${item.seo.indexFlag == 'N'}">
						<c:set var="noindexYn" value="N" /> <!-- 인덱스 시키지 않음. -->
					</c:if>

					<c:choose>
						<c:when test="${item.dataStatusCode == '20' || item.dataStatusCode == '31' || item.dataStatusCode == '40' || item.dataStatusCode == '41'}">
							<c:set var="itemSaleStatusText">등록대기</c:set>
						</c:when>
						<c:when test="${item.dataStatusCode == '21'}">
							<c:set var="itemSaleStatusText"><a href="#" class="show-reject-message">등록보류</a></c:set>
						</c:when>
						<c:when test="${item.dataStatusCode == '30'}">
							<c:set var="itemSaleStatusText">재등록신청</c:set>
						</c:when>
						<c:when test="${item.dataStatusCode == '90'}">
							<c:set var="itemSaleStatusText">판매종료</c:set>
						</c:when>
						<c:otherwise>
							<c:set var="itemSaleStatusText">${op:message('M00694')}</c:set> <!-- 판매중 -->
						</c:otherwise>
					</c:choose>


					<tr>
						<td><input type="checkbox" name="id" value="${item.itemId}" class="${item.itemUserCode}" title="" /></td>
						<td>
							<c:choose>
								<c:when test="${itemParam.orderBy == 'ORDERING' && itemParam.sort == 'ASC'}">
									${pagination.number + i.count}
								</c:when>
								<c:otherwise>
									${pagination.itemNumber - i.count}
								</c:otherwise>
							</c:choose>
							<p style="padding-top: 5px;">
								<c:choose>
									<c:when test='${op:property("saleson.view.type") eq "api"}'>
										<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
									</c:when>
									<c:otherwise>
										<a href="/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
										<a href="/m/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="" /></a>
									</c:otherwise>
								</c:choose>

							</p>
						</td>
						<td>
								${itemSaleStatusText}
							<div class="data-status-message">
									${op:nl2br(item.dataStatusMessage)}
							</div>
						</td>
						<td>
							<div>
								<c:choose>
									<c:when test='${op:property("saleson.view.type") eq "api"}'>
										<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
									</c:when>
									<c:otherwise>
										<a href="/products/preview/${item.itemUserCode}" target="_blank"><img src="${shop:loadImage(item.itemCode, item.itemImage, 'XS')}" class="item_image" alt="상품이미지" /></a>
									</c:otherwise>
								</c:choose>

							</div>
						</td>
						<td class="left break-word">

							<a href="javascript:Link.view('/opmanager/item/seller/edit/${item.itemUserCode}')" class="break-word">[${item.itemUserCode}]<br/> ${item.itemName}</a>
							<a href="javascript:Link.view('/opmanager/item/seller/edit/${item.itemUserCode}', 1)" class="break-word" style="color:#517BAB">[새탭]</a>
						</td>

						<td>${item.taxType == '1' ? '과세' : '면세'}</td>
						<td class="text-right">
							<c:choose>
								<c:when test="${empty item.itemPrice}">
									-
								</c:when>
								<c:otherwise>
									${op:numberFormat(item.itemPrice)}원
								</c:otherwise>
							</c:choose>
						</td>
						<td class="text-right">
								${op:numberFormat(item.salePrice)}원
						</td>
						<td class="text-right">
								${op:numberFormat(item.salePrice - (item.salePrice * item.commissionRate * 0.01))}원
						</td>
						<td class="text-right">
								${op:numberFormat(item.commissionRate)}%
						</td>
						<td>
							<c:choose>
								<c:when test="${item.shippingType == '1'}">
									무료배송
								</c:when>
								<c:when test="${item.shippingType == '2'}">
									판매자조건부
								</c:when>
								<c:when test="${item.shippingType == '3'}">
									출고지조건부
								</c:when>
								<c:when test="${item.shippingType == '4'}">
									상품조건부
								</c:when>
								<c:when test="${item.shippingType == '5'}">
									개방배송비
								</c:when>
								<c:when test="${item.shippingType == '65'}">
									고정배송비
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
							</c:choose>
						</td>
						<td class="text-right">
								${op:numberFormat(item.shipping)}원

							<c:if test="${item.shippingFreeAmount > 0}">
								<p style="font-size: 11px">(${op:numberFormat(item.shippingFreeAmount)}원 이상 무료)</p>
							</c:if>
						</td>
						<td>${item.itemOptionFlag}</td>
						<td>
							<c:choose>
								<c:when test="${item.stockQuantity == -1}">
									${op:message('M01497')} <!-- 무제한 -->
								</c:when>
								<c:otherwise>
									${op:numberFormat(item.stockQuantity)}개
								</c:otherwise>
							</c:choose>
						</td>

						<td>${displayFlagText}</td>

						<td>
							<a href="/opmanager/seller/edit/${item.sellerId}" style="padding-top: 5px;font-size: 11px; color: #000" target="_blank"><span class="glyphicon glyphicon-user"></span>${item.sellerName}</a>

							<c:choose>
								<c:when test="${itemParam.orderBy == 'HITS'}">
									<br />${op:numberFormat(item.hits)}
								</c:when>
								<c:otherwise>
									<br />${op:date(item.createdDate)}
								</c:otherwise>
							</c:choose>

						</td>
						<td>
							<button type="button" class="btn btn-gradient btn-xs" onclick="javascript:Manager.itemLog('${item.itemId}')">보기</button>
						</td>
						<td>${empty item.mdName ? '-' : item.mdName}</td>

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
				<button type="button" id="update_list_data" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-ok"></span> 승인처리</button>
				<button type="button" id="update_list_data_reject" class="btn btn-default btn-sm">등록보류</button>

				<div class="reject-message popup_wrap">
					<h1 class="popup_title">등록보류 처리</h1>

					<div class="popup_contents">
						<p>등록보류 사유를 입력해 주세요</p>
						<textarea id="rejectMessage" name="rejectMessage"></textarea>

						<div class="buttons">
							<button type="button" id="update_list_data_reject_process" class="btn btn-active btn-sm">등록보류처리</button>
							<button type="button" class="cancel-reject btn btn-default btn-sm">취소</button>
						</div>
					</div>
					<a href="#" class="popup_close cancel-reject">창 닫기</a>
				</div>
			</div>
		</div>

		<page:pagination-manager />

	</div> <!-- // board_list -->

	<div class="reject-message-layer popup_wrap">
		<h1 class="popup_title">등록보류</h1>

		<div class="popup_contents">
			<p>등록보류 사유입니다.</p>
			<div id="reject-message-wrap"></div>

			<div class="buttons">
				<button type="button" class="close-reject-message btn btn-active btn-sm">확인</button>
			</div>
		</div>
		<a href="#" class="popup_close close-reject-message">창 닫기</a>
	</div>
</div>

<style>
	td {background: #fff;}
	.sortable-placeholder td{height: 100px; background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
		opacity: .5;}



</style>
<script type="text/javascript">
    $(function() {
        $('.show-reject-message').on('click', function(e) {
            e.preventDefault();
            var rejectMessage = $(this).closest('tr').find('.data-status-message').html();
            $('#reject-message-wrap').html(rejectMessage);
            $('.reject-message-layer').show();
            $('.reject-message').hide();
        });

        $('.close-reject-message').on('click', function() {
            $('#reject-message-wrap').empty();
            $('.reject-message-layer').hide();
        });


        $('#orderBy, #sort, #itemsPerPage').on("change", function(){
            $('#sort option').eq(0).prop("disabled", false);
            if ($(this).val() == 'ORDERING') {
                $('#sort').val("ASC").find('option').eq(0).prop("disabled", true);
            }

            $('#itemParam').submit();
        });

        // 상품삭제
        $('.delete_item').on('click', function(e) {
            e.preventDefault();
            $('#processType').val('');

            $('#check_all').prop("checked", false);
            $(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
            $(this).closest("tr").find('input[name=id]').prop("checked", true);

            Common.updateListData("/opmanager/item/list/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
        });


        // 목록데이터 - 승인처리
        $('#update_list_data').on('click', function() {

            var $form = $('#listForm');
            if ($form.find('input[name=id]:checked').size() == 0) {
                alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
                return;
            }

            $('#processType').val('approval');
            Common.updateListData("/opmanager/item/list/update", "판매자가 등록(수정)한 상품 정보를 정확히 확인된 경우에만 일괄 승인처리를 하시기 바랍니다.\n\n선택한 상품을 승인처리 하시겠습니까?");

        });

        // 목록데이터 - 등록보류
        $('#update_list_data_reject').on('click', function() {

            var $form = $('#listForm');
            if ($form.find('input[name=id]:checked').size() == 0) {
                alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
                return;
            }
            $('.reject-message').show();
            $('.reject-message-layer').hide();
            $('#approvalMessage').val("");
            $('#rejectMessage').val("");
            $('#processType').val('reject');

        });

        // 목록데이터 - 등록보류 전송
        $('#update_list_data_reject_process').on('click', function() {

            var $form = $('#listForm');
            if ($form.find('input[name=id]:checked').size() == 0) {
                alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
                return;
            }

            if ($.trim($('#rejectMessage').val()) == '') {
                alert("등록보류 사유를 입력해 주세요.");
                $('#rejectMessage').focus();
                return;
            }

            $('#processType').val('reject');
            $('#approvalMessage').val($('#rejectMessage').val());
            Common.updateListData("/opmanager/item/list/update", "선택한 상품을 등록보류로 처리하시겠습니까?");
            $('.reject-message').hide();
        });


        $('.cancel-reject').on('click', function() {
            $('#approvalMessage').val("");
            $('#rejectMessage').val("");
            $('#processType').val('');
            $('.reject-message').hide();
        });





        // 팀/그룹 ~ 4차 카테고리 이벤트
        ShopEventHandler.categorySelectboxChagneEvent();
        Shop.activeCategoryClass('${itemParam.categoryGroupId}', '${itemParam.categoryClass1}', '${itemParam.categoryClass2}', '${itemParam.categoryClass3}', '${itemParam.categoryClass4}');

        Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');



    });
    function sellerSeller(sellerId) {
        $('#sellerId').val(sellerId);
    }
    function findMd(targetId) {
        Common.popup('/opmanager/seller/find-md?targetId=' + targetId, 'find_md', 720, 800, 1);
    }

    function clearMd(targetId) {
        var $target = $('#' + targetId);
        $target.val('');
        $target.closest('td').find('#mdName').val('');
    }

    //MD 검색 콜백
    function handleFindMdCallback(response) {
        var $target = $('#' + response.targetId);
        $target.val(response.userId);
        $target.closest('td').find('#mdName').val(response.userName);

    }
</script>

