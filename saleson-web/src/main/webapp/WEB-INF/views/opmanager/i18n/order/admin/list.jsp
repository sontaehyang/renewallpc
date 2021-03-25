<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">

.scroll-wrap {
	overflow-x: scroll;
}
.scroll-wrap th {
	padding: 8px 3px !important;
}
.scroll-wrap td {
	padding: 3px !important;
}
.scroll-wrap input[type=text], 
.scroll-wrap select {
	font-size: 12px;
}
.scroll-wrap input[type=text] {
	width: 90%;
}
.scroll-wrap select {
	width: 120px;
}
.scroll-wrap td div {
	width:120px;
}
</style>

<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>
<h3><span>관리자 대량주문 목록</span></h3>

<form:form modelAttribute="orderAdminParam" action="" method="post">
		
	<div class="board_write">
	
		<table class="board_write_table" summary="${ title }">
			<caption>${ title }</caption>
			<colgroup>
				<col style="width:150px;" />
				<col />
				<col style="width:150px;" />
				<col />
			</colgroup>
			<tbody> 
				<tr>  
				 	<td class="label">${op:message('M00023')}</td><!-- 주문일자 --> 
				 	<td colspan="3"> 
				 		<div>
							<span class="datepicker"><form:input path="startDate" class="datepicker" maxlength="8" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
							<span class="wave">~</span>							
							<span class="datepicker"><form:input path="endDate" class="datepicker" maxlength="8" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
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
				 <tr>
				 	<td class="label">작업차수</td>
				 	<td colspan="3">
				 		<div>
							<form:input path="workSequence" class="input_txt" title="작업차수" maxlength="20" />
						</div>
				 	</td>
				 </tr>
			</tbody>
		</table>
		
		<div class="btn_all">
			<div class="btn_left"> 
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='${requestContext.sellerPage ? '/seller' : '/opmanager'}/order/admin/list'"><span>${op:message('M00047')}<!-- 초기화 --></span></button>
			</div> 
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}<!-- 검색 --></span></button>
			</div>
		</div>
	</div>
	
	<div class="count_title mt20">
		<h5>
			${op:message('M00039')} : ${op:numberFormat(totalCount)} ${op:message('M00743')}
		</h5>
		<span>
			${op:message('M00052')} : <!-- 출력수 --> 
			<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
				onchange="$('form#orderParam').submit();"> <!-- 화면 출력수 -->
				<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="200" label="200${op:message('M00053')}" /> <!-- 개 출력 -->
				<form:option value="500" label="500${op:message('M00053')}" /> <!-- 개 출력 -->
			</form:select>
		</span>
	</div>
</form:form>


 
<div class="board_list">
	<div class="btn_all">
		<div class="btn_left mb0"> 
			
		</div>
		<div class="btn_right mb0">
			 
		</div>
	</div>
	<form id="listForm" method="POST">
		<div class="scroll-wrap">
			<table class="board_list_table" summary="주문내역 리스트">
				<caption>주문내역 리스트</caption>
				<thead>
					<tr>
						<th scope="col" rowspan="2"><input type="checkbox" id="check_all" /></th>
						<th scope="col" rowspan="2">No</th>
						<th scope="col" rowspan="2">상태</th>
						<th scope="col" rowspan="2">작업일자/차수</th>
						<th scope="col" rowspan="2">주문 그룹번호 <span style="color:red">(*)</span></th>
						<th scope="col" colspan="6">상품정보</th>
						<th scope="col" colspan="3">구매자</th>
						<th scope="col" colspan="6">받는사람</th>
						<th scope="col" rowspan="2">배송 요구사항</th>
					</tr>
					<tr>
						<th scope="col">상품코드</th>
						<th scope="col">이미지</th>
						<th scope="col">상품명</th>
						<th scope="col">옵션<span style="color:red">(*)</span></th>
						<th scope="col">판매가<span style="color:red">(*)</span></th>
						<th scope="col">수량<span style="color:red">(*)</span></th>
					
						<th scope="col">이름<span style="color:red">(*)</span></th>
						<th scope="col">전화번호</th>
						<th scope="col">휴대폰<span style="color:red">(*)</span></th>
						
						<th scope="col">이름<span style="color:red">(*)</span></th>
						<th scope="col">우편번호<<span style="color:red">(*)</span></th>
						<th scope="col">주소<span style="color:red">(*)</span></th>
						<th scope="col">상세 주소<span style="color:red">(*)</span></th>
						<th scope="col">전화번호</th>
						<th scope="col">휴대폰<span style="color:red">(*)</span></th>
					</tr>
				</thead>
				<tbody>
					
					<c:forEach items="${list}" var="order" varStatus="index">
						<c:set var="key">${order.workDate}-${order.workSequence}-${order.itemSequence}</c:set>
						<tr>
							<td>
								<div style="width:20px;">
								<c:if test="${order.error != true}">
									<input type="checkbox" name="id" value="${key}" 
										data-guest-flag="${order.buyer.userId > 0 ? 'N' : 'Y'}" 
										data-user-id="${order.buyer.userId}" />
										
									<input type="hidden" name="orderAdminMap[${key}].userId" value="${order.buyer.userId}" />
								</c:if>
								</div>
							</td>
							<td>
								<div style="width:20px;">
									${op:numberFormat(pagination.itemNumber - index.count)}
								</div>
							</td>
							<td>
								<div style="width:80px;">
									<c:choose>
										<c:when test="${order.dataStatusCode == '1'}">작업전</c:when>
										<c:otherwise>완료</c:otherwise>
									</c:choose>
								</div>
							</td>
							<td>
								<div style="width:100px;">
									${op:date(order.workDate)} / ${order.workSequence}
								</div>
							</td>
							<c:choose>
								<c:when test="${order.error == true}">
									<td colspan="17" class="text-left">
										${order.errorMessage}
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<div style="width:100px;">
											<input type="text" name="orderAdminMap[${key}].orderGroupCode" class="required-optional" title="주문 그룹번호" maxlength="10" value="${order.orderGroupCode}" />
										</div>
									</td>
									<td>
										<div>
											${order.item.itemUserCode}
											<input type="hidden" name="orderAdminMap[${key}].itemId" value="${order.item.itemId}" />
										</div>
									</td>
									<td>
										<div style="width:50px;">
											<img src="${order.item.imageSrc}" alt="${order.item.itemName}" width="100%"/>
										</div>
									</td>
									<td>
										<div style="width:200px;" class="text-left">
											${order.item.itemName}
										</div>
									</td>
									<td>
										<div style="width:200px;text-align:right; margin-right: 10px">
											<c:choose>
												<c:when test="${order.item.itemOptionType == 'S'}">
													<c:set var="groupTitle"></c:set>
													<ul>
														<c:forEach items="${order.item.optionGroups}" var="group">
															<li style="line-height:28px">
															${group.title} :
																<select name="orderAdminMap[${key}].optionIds" class="required-optional" title="옵션 - ${group.title}">
																	<option value="">-선택-</option>
																	<c:forEach items="${order.item.itemOptions}" var="option">
																		<c:if test="${option.optionType != 'T' && group.title == option.optionName1}">
																			<option value="${option.itemOptionId}">
																				${option.optionName2}
																				
																				<c:if test="${ option.optionPrice != 0}">
																				(<c:choose>
																					<c:when test="${option.optionPrice > 0}">+</c:when>
																					<c:otherwise>-</c:otherwise>
																				</c:choose>
																				${op:numberFormat(option.optionPrice)}원)
																				</c:if>
																				
																				<c:if test="${option.optionStockFlag == 'Y' && option.optionStockQuantity > 0}">
																					| 재고 : ${op:numberFormat(option.optionStockQuantity)}개
																				</c:if>																			
																				
																				<c:if test="${option.soldOut}">
																					- 품절
																				</c:if>
																			</option>
																		</c:if>
																	</c:forEach>
																</select>
															</li>
														
														</c:forEach>
													</ul>
												</c:when>
												<c:otherwise>
													<select name="orderAdminMap[${key}].optionIds" class="required-optional" title="옵션 - ${group.title}">
															<option value="">-선택-</option>
															<c:forEach items="${order.item.itemOptions}" var="option">
																<c:if test="${option.optionType != 'T'}">
																	<option value="${option.itemOptionId}">
																		<c:choose>
																			<c:when test="${option.optionType == 'S2'}">${option.optionName1} / ${option.optionName2}</c:when>
																			<c:when test="${option.optionType == 'S3'}">${option.optionName1} / ${option.optionName2} / ${option.optionName3}</c:when>
																		</c:choose>
																		
																		<c:if test="${ option.optionPrice != 0}">
																		(<c:choose>
																			<c:when test="${option.optionPrice > 0}">+</c:when>
																			<c:otherwise>-</c:otherwise>
																		</c:choose>
																		${op:numberFormat(option.optionPrice)}원)
																		</c:if>
																		
																		<c:if test="${option.optionStockFlag == 'Y' && option.optionStockQuantity > 0}">
																			| 재고 : ${op:numberFormat(option.optionStockQuantity)}개
																		</c:if>																			
																		
																		<c:if test="${option.soldOut}">
																			- 품절
																		</c:if>
																	</option>
																</c:if>
															</c:forEach>
														</select>
												</c:otherwise>
											</c:choose>
										</div>
									</td>
									<td>
										<div style="width:100px;">
											<input type="text" class="_number_comma text-right required-optional" name="orderAdminMap[${key}].salePrice" title="판매가" maxlength="9" value="${op:numberFormat(order.item.presentPrice)}" />
										</div>
									</td>
									<td>
										<div style="width:100px;">
											<input type="text" class="_number_comma text-right" name="orderAdminMap[${key}].quantity" title="수량" maxlength="9" value="${order.quantity}" />
										</div>
									</td>
									<td>
										<div>
											<c:choose>
												<c:when test="${order.buyer.userId == 0}">[비회원]</c:when>
												<c:otherwise>[${order.buyer.loginId}]</c:otherwise>
											</c:choose>
											<input type="text" name="orderAdminMap[${key}].userName" class="required-optional" title="구매자 이름" maxlength="20" value="${order.buyer.userName}" /> 
										</div>
									</td>
									<td>
										<div>
											<input type="text" name="orderAdminMap[${key}].phone" title="구매자 전화번호" maxlength="20" value="${order.buyer.phone}" />
										</div>
									</td>
									<td>
										<div>
											<input type="text" name="orderAdminMap[${key}].mobile" class="required-optional" title="구매자 휴대폰" maxlength="20" value="${order.buyer.mobile}" />
										</div>
									</td>
									<td>
										<div>
											<input type="text" name="orderAdminMap[${key}].receiveName" class="required-optional" title="받는사람 이름" maxlength="20" value="${order.receiver.receiveName}" />
										</div>
									</td>
									<td>
										<div>
											<input type="text" name="orderAdminMap[${key}].receiveZipcode" class="required-optional" title="받는사람 우편번호" maxlength="7" value="${order.receiver.receiveZipcode}" />
										</div>
									</td>
									<td>
										<div style="width:250px;">
											<input type="text" name="orderAdminMap[${key}].receiveAddress" class="required-optional" title="받는사람 주소" maxlength="100" value="${order.receiver.receiveAddress}" />
										</div>
									</td>
									<td>
										<div style="width:250px;">
											<input type="text" name="orderAdminMap[${key}].receiveAddressDetail" class="required-optional" title="받는사람 상세 주소" maxlength="255" value="${order.receiver.receiveAddressDetail}" />
										</div>
									</td>
									<td>
										<div>
											<input type="text" name="orderAdminMap[${key}].receivePhone" title="받는사람 전화번호" maxlength="14" value="${order.receiver.receivePhone}" />
										</div>
									</td>
									<td>
										<div>
											<input type="text" name="orderAdminMap[${key}].receiveMobile" title="받는사람 휴대폰" maxlength="14" value="${order.receiver.receiveMobile}" />
										</div>
									</td>
									<td>
										<div>
											<input type="text" name="orderAdminMap[${key}].content" title="배송 요구사항" maxlength="50" value="${order.receiver.content}" />
										</div>
									</td>
								</c:otherwise>
							</c:choose>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:if test="${empty list}">
		<div class="no_content">
			${op:message('M00473')} <!-- 데이터가 없습니다. --> 
		</div>
		</c:if>	
		
		<div class="btn_all">
			<div class="btn_left mb0">
				<select name="shippingPaymentType">
					<option value="1">배송비 선불</option>
					<option value="2">배송비 착불</option>
				</select>
				<button type="submit" class="btn btn-default btn-sm confirm-remittance">주문 처리</button>
			</div>
			<div class="btn_right mb0">
				 <a href="javascript:uploadExcel()" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-open" aria-hidden="true"></span> ${op:message('M00793')}</a> <!-- 엑셀 업로드 -->
			</div>
		</div> 
	</form>
	<page:pagination-manager /> 
	
</div> 

<div class="board_guide ml10">
	<p class="tip">Tip</p>
	<p class="tip">비회원 주문의 경우 <strong>"구매자 이름" + "구매자 휴대폰"</strong> 으로 같은 주문인지 체크합니다. 같은 주문이라고 판단되는경우 첫번째 구매자 정보를 이용해 주문을 생성합니다.</p>
	<p class="tip">회원 주문의 경우 <strong>회원 ID</strong>로 같은 주문인지 체크합니다. 같은 주문이라고 판단되는경우 첫번째 구매자 정보를 이용해 주문을 생성합니다.</p>
	<p class="tip">주문 그룹번호를 동일하게 입력하셔야 주문이 주문번호 하나로 생성됩니다.</p>
	<p class="tip">동일 배송지 체크는 <strong>"받는사람 우편번호" + "받는사람 주소" + "받는사람 상세 주소"</strong>로 체크됩니다.</p>
</div>
<daum:address />
<script type="text/javascript"> 
	$(function(){
		Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="startDate"]' , 'input[name="endDate"]');
		
		$('#listForm').find('input').on('keydown', function(e){
			if (e.keyCode == 13) {
				return false;
			}
		})
		
		$('#listForm').validator(function() {
			
			var $form = $('#listForm');
			if ($form.find('input[name=id]:checked').size() == 0) {
				alert(Message.get("M00308"));	// 처리할 항목을 선택해 주세요.
				return false;
			}
			
			var isSuccess = true;
			$.each($form.find('input[name="id"]:checked'), function() {
				
				$.each($(this).parent().parent().parent().find('.required-optional'), function() {
					
					var tagName = $(this).get(0).tagName.toLowerCase();
					if ($(this).val() == '') {
						
						var title = $(this).attr('title');
						
						if (tagName == 'select') {
							$.validator.validatorAlert($.validator.messages['select'].format(title), $(this));
						} else {
							$.validator.validatorAlert($.validator.messages['text'].format(title), $(this));
						}
						
						isSuccess = false;
						return false;
					}
				})
				
				if (!isSuccess) return false;
				
				var key = $(this).val();
				var data = $(this).data();
				var userId = Number(data.userId);
				var orderGroupCode = $form.find('input[name="orderAdminMap['+ key +'].orderGroupCode"]').val();
				
				var buyerName = $form.find('input[name="orderAdminMap['+ key +'].userName"]').val();
				var buyerMobile = $form.find('input[name="orderAdminMap['+ key +'].mobile"]').val();
				
				if (data.guestFlag == 'Y') {
					
					$.each($form.find('input[name="id"]:checked'), function() {
						
						var _orderGroupCode = $('input[name="orderAdminMap['+ $(this).val() +'].orderGroupCode"]').val()
						if (orderGroupCode == _orderGroupCode) {
							
							var _buyerName = $('input[name="orderAdminMap['+ $(this).val() +'].userName"]').val();
							var _buyerMobile = $('input[name="orderAdminMap['+ $(this).val() +'].mobile"]').val();
							
							if (buyerName != _buyerName || buyerMobile != _buyerMobile) {
								alert('같은 주문으로 등록하실수 없는 타입의 주문이 있습니다. \n주문 그룹번호를 확인해 주세요.');
								isSuccess = false;
								return false;
							}
							
						}
						
					});
					
				} else {
					
					
					$.each($form.find('input[name="id"]:checked'), function() {
						
						var _orderGroupCode = $('input[name="orderAdminMap['+ $(this).val() +'].orderGroupCode"]').val()
						if (orderGroupCode == _orderGroupCode) {
							
							var _userId = Number($(this).data('userId'));
							if (userId != _userId) {
								alert('같은 주문으로 등록하실수 없는 타입의 주문이 있습니다. \n주문 그룹번호를 확인해 주세요.');
								isSuccess = false;
								return false;
							}
							
						}
						
					});
					
				}
				
				
				if (!isSuccess) return false;
			})
			
			if (!isSuccess) return false;
			
			Common.removeNumberComma(); 
		})
	});
	
	// 엑셀 업로드
	function uploadExcel() {
		Common.popup('/opmanager/order/admin/upload-excel', 'admin-upload-excel', 600, 550, 0);
	}
	
</script>