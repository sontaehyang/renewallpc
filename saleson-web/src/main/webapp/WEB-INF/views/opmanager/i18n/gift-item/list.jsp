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
		<h3><span>사은품 관리</span></h3>

		<form:form modelAttribute="giftItemDto" method="get">
			<div class="board_write">
				<table class="board_write_table" summary="사은품 관리">
					<caption>사은품 관리</caption>
					<colgroup>
						<col style="width: 150px;">
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
							<td>
								<div>
									<form:select path="where" title="상세검색 선택">
										<form:option value="name">사은품명</form:option>
										<form:option value="code">사은품코드</form:option>
									</form:select>
									<form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">진행 여부</td>
							<td>
								<div>
									<p>
										<form:radiobutton path="processType" value="" checked="checked" label="${op:message('M00039')}" /> <!-- 전체 -->
										<form:radiobutton path="processType" value="not_progress" title="진행 전" label="진행 전" />
										<form:radiobutton path="processType" value="progress" title="진행 중" label="진행 중" />
										<form:radiobutton path="processType" value="end" title="종료" label="종료" />

									</p>
								</div>
							</td>
						</tr>
						<c:if test="${!isSellerPage}">
							<tr>
								<td class="label">판매자</td>
								<td>
									<div>
										<form:select path="sellerId">
											<option value="0">전체</option>
											<c:forEach var="seller" items="${sellerList}">
												<option value="${seller.sellerId}" ${op:selected(seller.sellerId, giftItemDto.sellerId)}>${seller.sellerName}</option>
											</c:forEach>
										</form:select>
									</div>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>

			</div>

			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.requestUri}';"><span>${op:message('M00047')}</span></button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
				</div>
			</div>
		</form:form>

		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${op:numberFormat(pageContent.totalElements)} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 -->
		</div>

		<div class="board_list">
			<form id="dataForm" method="post">
				<table class="board_list_table" summary="처리내역 리스트">
					<caption>처리내역 리스트</caption>
					<colgroup>
						<col style="width:30px;">
						<col style="width:30px;">
						<col style="width:150px;">
						<col style="width:100px;">
						<col style="width:auto;">
						<col style="width:100px;">
						<col style="width:100px;">
						<col style="width:300px;">
						<col style="width:100px;">
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" class="check_all" title="체크박스"></th>
							<th scope="col">순번</th>
							<th scope="col">상태</th>
							<th scope="col">이미지</th>
							<th scope="col">사은품명</th>
							<th scope="col">금액</th>
							<th scope="col">판매자</th>
							<th scope="col">적용기간</th>
							<th scope="col">변경이력</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageContent.content}" var="list" varStatus="i">
							<tr>
								<td><input type="checkbox" name="id" title="체크박스" value="${list.id}"></td>
								<td>${op:numbering(pageContent, i.index)}</td>
								<td>
									<c:choose>
										<c:when test='${list.processType == "NOT_PROGRESS"}'>
											진행 전
										</c:when>
										<c:when test='${list.processType == "PROGRESS"}'>
											진행 중
										</c:when>
										<c:when test='${list.processType == "END"}'>
											종료
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="javascript:Link.view('${requestContext.managerUri}/gift-item/edit/${list.id}')">
										<img src="${shop:loadImageBySrc(list.imageSrc, "S")}" class="item_image" alt="" />
									</a>
								</td>
								<td class="left break-word">
									<a href="javascript:Link.view('${requestContext.managerUri}/gift-item/edit/${list.id}')">
										${list.name}
										<br/>
										(${list.code})
									</a>
								</td>
								<td class="text-right">
									${op:numberFormat(list.price)}원
								</td>
								<td>
									<c:set var="sellerName" value=""/>
									<c:forEach var="seller" items="${sellerList}">
										<c:if test="${seller.sellerId == list.sellerId}">
											<c:set var="sellerName" value="${seller.sellerName}"/>
										</c:if>
									</c:forEach>
									<span class="glyphicon glyphicon-user"></span>${sellerName}
								</td>
								<td>
									<c:choose>
										<c:when test="${empty list.validStartDateText && empty list.validEndDateText}">
											상시
										</c:when>
										<c:otherwise>
											${list.validStartDateText} ~ ${list.validEndDateText}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<button type="button" class="btn btn-gradient btn-xs" onclick="viewLog('${list.id}')">보기</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<c:if test="${empty pageContent.content}">
			<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>
		</c:if>

		<page:pagination-jpa />

		<div class="btn_all">
			<div class="btn_left mb0">
				<button type="button" class="btn btn-default btn-sm checked_delete">선택삭제</button>
			</div>
			<div class="btn_right mb0">
				<a href="${requestContext.managerUri}/gift-item/create?url=${requestContext.currentUrl}" class="btn btn-active btn-sm">${op:message('M00088')}</a> <!-- 등록 -->
			</div>
		</div>
	</div>


<script type="text/javascript">

	var DATA_FORM_OBJECT = $('#dataForm');

	$(function(){

		$(".checked_delete").on("click",function(){
			if (DATA_FORM_OBJECT.find('input[name=id]:checked').size() == 0) {
				alert('삭제할 항목을 선택해주세요.');
				return;
			}

			Common.confirm(Message.get("M00196"),function(){	// 삭제하시겠습니까?
				if(DATA_FORM_OBJECT.find("input[name=id]:checked").size() > 0){
					DATA_FORM_OBJECT.attr('action','${requestContext.managerUri}/gift-item/delete');
					DATA_FORM_OBJECT.submit();
				}
			});
		});

		$('.check_all').on('click',function() {
			if ($(this).is(':checked')) {
				$('input[name=id]').prop('checked', true);
			} else {
				$('input[name=id]').prop('checked', false);
			}
		});

	});

	function viewLog(id) {
		Common.popup('${requestContext.managerUri}/gift-item/popup/log/' + id, "gift-item-log", 1528, 750, 1, 0, 0);
	}
</script>