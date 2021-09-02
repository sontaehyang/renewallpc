<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
span.require {color: #e84700; margin-left: 5px;}
</style>


		<div class="location">
			<a href="#">상품관리</a> &gt;  <a href="#">상품정보</a> &gt; <a href="#" class="on">상품등록</a>
		</div>
		
		
		<form:form modelAttribute="item" method="post" enctype="multipart/form-data">
			
			<input type="hidden" name="listPage" value="${listPage }"/>

			<form:hidden path="otherFlag" value="1" /> <%-- 자사상품/업체배송 : 추후 삭제 예정  --%>
			<form:hidden path="opentime" />	
			
			<input type="hidden" id="useItemUserCode" value="${useItemUserCode}" />
			<input type="hidden" id="itemType" value="1" /> <%-- 상품구분: 1.일반상품, 2.사업자상품 --%>

			<form:hidden path="itemCode" />
			
			<form:hidden path="itemDataType" />	<%-- 상품데이터 형태: 1.일반상품, 2.추가구성상품 --%>
			<form:hidden path="brandId" />	<%-- 브랜드 ID: ???? --%>
			<form:hidden path="priceCriteria" />	<%-- 가격 입력 기준 (1: 공급가기준-수수료자동입력, 2: 수수료기준-공급가 자동입력) --%>
			
			<form:textarea path="dataStatusMessage" style="display: none"/>
			
			<div class="item_list">
			
				<h3 class="custom"><span>${item.itemId == 0 ? op:message('M00773') : op:message('M00907')}</span></h3>	<!-- 상품등록 --><!-- 상품기본정보 -->
				
				<p class="text-info text-sm">*카테고리 2개 이상 추가시 드래그로 카테고리 순서를 변경할 수 있습니다.</p>
				<!-- 등록된 상품 카테고리가 없음경우 -->
				<ul id="item_categories" class="sortable_item_category category_box" style="margin-top:0">
					

					<c:forEach items="${item.breadcrumbs}" var="breadcrumb" varStatus="i">
						<li id="item_category_${breadcrumb.categoryClass}">

							${breadcrumb.teamName} > ${breadcrumb.groupName}
							
							<c:forEach items="${breadcrumb.breadcrumbCategories}" var="subBreadcrumb">
								 > ${subBreadcrumb.categoryName}
							</c:forEach>
							<a href="javascript:deleteItemCategory(${breadcrumb.categoryClass})" class="delete">[${op:message('M00074')}]</a>
							<input type="hidden" name="categoryIds" value="${breadcrumb.categoryClass}" />
						</li>
					</c:forEach>
					
					<c:if test="${empty item.breadcrumbs}">
						<li class="nothing">${op:message("M00077")}</li>	<!-- 등록된 상품 카테고리가 없습니다. -->
					</c:if>
				</ul>
				
	
				<div class="category_wrap mb20">					
					<select id="categoryGroupId" class="category multiple" size="12">
						<option value="0">= ${op:message('M00076')} =</option> <!-- 팀/그룹 -->
						<c:forEach items="${categoryTeamGroupList}" var="categoriesTeam">
							<c:if test="${categoriesTeam.categoryTeamFlag == 'Y'}">
								<optgroup label="${categoriesTeam.name}">
								<c:forEach items="${categoriesTeam.categoriesGroupList}" var="categoriesGroup">
									<c:if test="${categoriesGroup.categoryGroupFlag == 'Y'}">
										<option value="${categoriesGroup.categoryGroupId}">${categoriesGroup.groupName}</option>
									</c:if>
								</c:forEach>
								</optgroup>
							</c:if>
						</c:forEach>
						
					</select> <!-- // category_step -->
					
					<select id="categoryClass1" class="category multiple"  size="12">
						<option value="">= 1${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<select id="categoryClass2" class="category multiple"  size="12">
						<option value="">= 2${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<select id="categoryClass3" class="category multiple"  size="12">
						<option value="">= 3${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<select id="categoryClass4" class="category multiple"  size="12">
						<option value="">= 4${op:message('M00075')} =</option> <!-- {}차 카테고리 -->
					</select> <!-- // category_step -->
					
					<span style="display: inline-block !important;">
						<button type="button" class="btn btn-success btn-lg" onclick="addItemCategory()"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00789')}</button> <!-- 카테고리추가 -->
					</span>
					
				</div> <!-- // category_wrap -->
			</div>	
				
				
			<div class="item_info_wrap">	
				<h3>기본정보</h3>
				<div class="board_write">						
					<table class="board_write_table" summary="상품기본정보">
						<caption>상품기본정보</caption>
						<colgroup>
							<col style="width: 180px;" />
							<col style="" />
							<col style="width: 160px;" />
							<col style="width: 250px;" />
						</colgroup>
						<tbody>
							<c:if test="${item.itemId != 0}">
								<tr>
									<td class="label">${op:message('M00019')}</td> <!-- 상품번호 -->
									<td>
										<div>
											<c:choose>
												<c:when test='${op:property("saleson.view.type") eq "api"}'>
													<a href="${op:property("saleson.url.frontend")}/items/details.html?code=${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
												</c:when>
												<c:otherwise>
													<a href="/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" alt="" /></a>
													<a href="/m/products/preview/${item.itemUserCode}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_mobile.gif" alt="" /></a>
												</c:otherwise>
											</c:choose>

											<span style="font-weight:bold; font-size: 14px; color: #000;">
												${item.itemCode}
											</span>
										</div>
									</td>
									<td class="label">재입고 알림</td>
									<td>
										<div>
											<span class="restock-button hide">${restockNoticeCount}명에게 <button type="button" onclick="sendRestockNotice();" class="btn btn-gradient btn-sm"> 메시지 전송</button></span>
											<span class="restock-text hide">신청한 회원이 없습니다.</span>
										</div>
									</td>
								</tr>
							</c:if>

							<%-- 
							<tr>
								<td class="label">${op:message('M00960')}</td> <!-- 상품구분 -->
								<td>
									<div>
										<p>
											<form:radiobutton path="itemType" value="1" label="일반상품" /> 
											<form:radiobutton path="itemType" value="2" label="사업자상품" /> 
										</p>
									</div>
								</td>
							</tr>
							--%>

							<tr>
								<td class="label">${op:message('M00018')}</td> <!-- 상품명 -->
								<td>
									<div>
										<form:input path="itemName" maxlength="100" class="form-block required" title="${op:message('M00018')}" /> 									
									</div>
								</td>
								<td class="label">공급사</td>
								<td>
									<div>
											<%--
                                            <form:hidden path="sellerId" />
                                             --%>

										<c:if test="${!isSellerPage}">
											<%-- 본사는 본사 상품만 등록하는 경우
											<input type="hidden" name="sellerId" id="sellerId" data-is-hq-seller="Y" value="${shopContext.hqSellerId}"/>
											<c:forEach items="${sellerList}" var="seller" varStatus="i">
												<c:if test="${seller.sellerId == shopContext.hqSellerId}">
													${seller.sellerName}
												</c:if>
											</c:forEach>
											--%>

											<%-- 본사가 판매자를 지정하여 상품을 등록할 수 있는 경우 --%>
											<select id="sellerId" name="sellerId" class="required" title="공급사">
												<option value="">선택</option>

												<c:forEach items="${sellerList}" var="seller" varStatus="i">
													<c:set var="isHqSeller" value="N" />
													<c:if test="${seller.sellerId == shopContext.hqSellerId}">
														<c:set var="isHqSeller" value="Y" />
													</c:if>

													<c:choose>
														<c:when test="${item.sellerId == seller.sellerId}">
															<option value="${seller.sellerId}" data-is-hq-seller="${isHqSeller}" selected="selected">${seller.sellerName}</option>
														</c:when>
														<c:otherwise>
															<c:if test="${!isSellerPage}">
																<option value="${seller.sellerId}" data-is-hq-seller="${isHqSeller}">${seller.sellerName}</option>
															</c:if>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>

										</c:if>

										<c:if test="${isSellerPage}">
											<input type="hidden" name="sellerId" id="sellerId" data-is-hq-seller="N" value="${sellerContext.seller.sellerId}"/>
											${sellerContext.seller.sellerName} (${sellerContext.seller.loginId})
										</c:if>
									</div>
								</td>
							</tr>
							
							<%-- 1.일반 --%>
							<c:if test="${op:property('shoppingmall.type') == '1'}">
								<tr>
									<td class="label">${op:message('M00019')}</td> <!-- 상품번호 -->
									<td>
										<div>
											<c:choose>
												<c:when test="${mode == 'edit'}">
													<form:input path="itemUserCode" maxlength="20" class="required" title="${op:message('M00019')}" /> <a href="#" id="btn_check_duplicate" class="table_btn">${op:message('M00148')}</a> <!-- 중복검사 -->
												</c:when>
												<c:otherwise>
													<form:input path="itemUserCode" maxlength="20" class="required" title="${op:message('M00019')}" /> <a href="#" id="btn_check_duplicate" class="table_btn">${op:message('M00148')}</a> <!-- 중복검사 -->
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${mode == 'copy'}">
													<form:input path="itemUserCode" maxlength="20" class="required" title="${op:message('M00019')}" /> <a href="#" id="btn_check_duplicate" class="table_btn">${op:message('M00148')}</a> <!-- 중복검사 -->
												</c:when>
												<c:otherwise>
													<form:input path="itemUserCode" maxlength="20" class="required" title="${op:message('M00019')}" /> <a href="#" id="btn_check_duplicate" class="table_btn">${op:message('M00148')}</a> <!-- 중복검사 -->
												</c:otherwise>
											</c:choose>
											<p class="text-info text-sm"> ※ 주의 : 아래 문자는 사용하시면 안됩니다. <br/>1. [ / (슬러시)] 상품 상세페이지가 정상적으로 연결되지 않습니다 <br/>2. [ , (콤마) ][ _ (언더바) ][ : (콜론) ][ . (${op:message('M00246')}) ] 오픈 마켓에 상품구분으로 사용하고 있습니다</p>
										</div>
									</td>
								</tr>
							</c:if>
							
							<%-- 2.몰인몰 --%>
							<c:if test="${op:property('shoppingmall.type') == '2'}">
								<input type="hidden" name="itemUserCode" value="${itemCode}" />
							</c:if>

							<tr>
								<td class="label">제조사</td>
								<td>
									<div>
										<form:input path="manufacturer" maxlength="30" class="form-block" title="제조사" /> 	
									</div>
								</td>
								<td class="label">브랜드</td>
								<td>
									<div>
										<form:select path="brand">
											<form:option value="" label="선택" />
											<c:forEach items="${brandList}" var="brand">
												<c:if test="${brand.displayFlag == 'Y'}">
													<form:option value="${brand.brandName}" label="${brand.brandName}" data-id="${brand.brandId}" />
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</td>
							</tr>
							
							<tr>
								
								<td class="label">원산지</td>
								<td>
									<div>
										<form:input path="originCountry" maxlength="30" class="form-block" title="원산지" /> 			
									</div>
								</td>
								<td class="label">${op:message('M01627')} <%-- 무게 --%></td>
								<td>
									<div>
										<form:input path="weight" maxlength="5" class="amount _number_comma" /> g
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">수량<span class="require"></span></td>
								<td>
									<div>
										<p class="text-info text-sm">
											* 실제 재고가 아닌 화면 노출용 항목입니다.
										</p>
										<form:input path="displayQuantity" maxlength="5" class="amount _number_comma" /> 개
									</div>
								</td>
								<td class="label">과세구분<span class="require">*</span></td>
								<td>
									<div>
										<p>
											<form:radiobutton path="taxType" value="1" label="과세" />
											<form:radiobutton path="taxType" value="2" label="면세" />
										</p>
									</div>
								</td>
							</tr>

							<tr>
								<td class="label">${op:message('M01624')} <%-- 사은품 정보 --%></td>
								<td colspan="3">
									<div>
										<form:radiobutton path="freeGiftFlag" value="N" label="${op:message('M00089')}" />  <!-- 사용안함 -->
										<form:radiobutton path="freeGiftFlag" value="Y" label="${op:message('M00083')}" />  <!-- 사용 -->

										<div class="hide_content">
											<form:input path="freeGiftName" placeholder="사은품 정보를 입력해 주세요." maxlength="80" class="required-free-gift form-block mb10" title="사은품 정보" />

											<div>
												<p class="mb10">
													<button type="button" class="table_btn" onclick="findGiftItem()"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
													<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('freeGift')"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
												</p>

												<ul id="freeGift" class="sortable_item_relation">
													<li style="display: none;"></li>
													<c:forEach items="${item.freeGiftItemList}" var="giftItem" varStatus="i">
														<c:if test="${not empty giftItem}">
															<li id="freeGift_item_${giftItem.id}">
																<input type="hidden" name="freeGiftItemIds" value="${giftItem.id}" />
																<p class="image"><img src="${shop:loadImageBySrc(giftItem.imageSrc, "XS")}" class="item_image size-100 none" alt="상품이미지" /></p>
																<p class="title">
																	[${giftItem.code}] ${giftItem.name}
																</p>

																<c:choose>
																	<c:when test="${not empty giftItem.notProcessLabel}">
																		<span class="error">[${giftItem.notProcessLabel}]</span>
																	</c:when>
																	<c:otherwise>
																		<span class="ordering">${i.count}</span>
																	</c:otherwise>
																</c:choose>
																<a href="javascript:Shop.deleteRelationItem('freeGift_item_${giftItem.id}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>

										</div>
									</div>
								</td>
							</tr>

							<tr>
								<td class="label">상품간략설명</td>
								<td colspan="3">
									<div>
										<form:textarea path="itemSummary" class="full" title="${op:message('M00971')}" maxlength="180" />
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">검색어</td>
								<td colspan="3">
									<div>
										<form:textarea path="itemKeyword" maxlength="100" class="full" title="${op:message('M00971')}" />
									</div>
								</td>
							</tr>
							
							
							
							<%-- 
							<!-- 추천상품 -->
							<tr>
								<td class="label">${op:message('M00581')}</td> <!-- 추천상품 -->
								<td>
									<div>
										<p>
											<form:radiobutton path="recommendFlag" value="Y" label="${op:message('M00958')}" /> <!-- 포함  -->
											<form:radiobutton path="recommendFlag" value="N" label="${op:message('M00959')}" /> <!-- 포함하지 않음  -->
										</p>
									</div>
								</td>
							</tr>
							--%>
							
							<%-- 
							<tr>
								<td class="label">${op:message('M00246')}</td> <!-- 포인트 -->
								<td>
									<div>
										<p class="mb5">
											<button type="button" class="table_btn add_item_point" onclick="addItemPoint()"><span>${op:message('M00501')}</span></button> <!-- 기간별 포인트 추가 -->
										</p>
										
											<!-- 포인트 추가시 -->
											<table id="item_point_config" class="board_list_table" summary="${op:message('M00246')} 추가">
												<caption>${op:message('M00246')} 추가 리스트</caption>
												<colgroup>
													<col style="width: 160px;">
													<col style="">
													<!--  
													<col style="width: 100px;">
													-->
													<col style="width: 70px;">
												</colgroup>
												<thead>
													<tr>
														<th>${op:message('M00246')}</th> <!-- 포인트 -->
														<th>${op:message('M00505')}</th> <!-- 포인트 적용 기간 -->
														<!-- <th>${op:message('M00506')}</th> 특정일 적용 -->
														<th>${op:message('M00074')}</th> <!-- 삭제 -->	
													</tr>
												</thead>
												<tbody id="item_point_area">
												
													<c:forEach items="${pointConfigList}" var="pointConfig">
														<tr>
															<td>
																<input type="text" name="point" maxlength="4" value="${pointConfig.point}"  class="three required _number" title="${op:message('M00246')}" /> <!-- 포인트 -->
																<input type="hidden" name="pointType" value="${pointConfig.pointType}" />
																
																<input type="radio" name="R${pointConfig.pointConfigId}" id="R${pointConfig.pointConfigId}1" ${op:checked('1', pointConfig.pointType)}> <label for="R${pointConfig.pointConfigId}1">%</label>
																<input type="radio" name="R${pointConfig.pointConfigId}" id="R${pointConfig.pointConfigId}2" ${op:checked('2', pointConfig.pointType)}> <label for="R${pointConfig.pointConfigId}2">P</label>
															</td>
															<td>
																<span class="datepicker"><input type="text" name="pointStartDate" class="term  _date" maxlength="8" value="${pointConfig.startDate}" title="${op:message('M00507')}"></span> <!-- 시작일 -->
																<select name="pointStartTime" title="시간 선택">
																	<c:forEach items="${hours}" var="code">
																		<option value="${code.value}" ${op:selected(code.value, pointConfig.startTime)}>${code.label}</option> 
																	</c:forEach>
																</select>시 ~
																<span class="datepicker"><input type="text" name="pointEndDate" class="term required _date" maxlength="8" value="${pointConfig.endDate}" title="${op:message('M00509')}"></span> <!-- 종료일 -->
																<select name="pointEndTime" title="시간 선택">
																	<c:forEach items="${hours}" var="code">
																		<option value="${code.value}" ${op:selected(code.value, pointConfig.endTime)}>${code.label}</option> 
																	</c:forEach>
																</select>시 59분
																
																
																<input type="hidden" name="pointRepeatDay" value="" />
															</td>
															<!-- 
															<td>
																<select name="pointRepeatDay">
																	<option value="">${op:message('M00039')}</option> <!-- 전체 ->
																	<c:forEach begin="1" end="31" step="1" var="i">
																		<option value="${ i }"
																			<c:if test="${pointConfig.repeatDay eq i}">selected="selected"</c:if>>${ i }</option>
																	</c:forEach>
																</select> ${op:message('M00511')} <!-- 일 -> 
															</td>
															 -->
															<td><a href="#" class="fix_btn delete_item_point">${op:message('M00074')}</a></td> <!-- 삭제 -->
														</tr>
														
													</c:forEach>
												</tbody>
											</table>
									
									</div>
								</td>
							</tr>
							 --%>
							
						</tbody>
					</table>
				</div>	
				
				<div class="item_list mt30">
					<h3>
						<span>판매설정</span>
					</h3>
					<div class="board_write">						
						<table class="board_write_table">
							<caption>상품기본정보</caption>
							<colgroup>
								<col style="width: 180px;" />
								<col style="width: 400px;" />
								<col style="width: 160px;" />
								<col style="" />
							</colgroup>
							<tbody>
								
								<tr>

									<td class="label">${op:message('M00786')}</td> <!-- 판매가격 -->
									<td>
										<div>
											<p class="text-info text-sm">
												* 실제 판매할 가격을 입력해 주십시오.<br/>
												* 입력한 판매가격에서 할인(즉시할인, 스팟할인)이 적용되며 배송비 조건 <br/>
												&nbsp;&nbsp;&nbsp;기준이 됩니다. <br/>
												* 입점몰로 운영되는 경우 입점판매자 정산금액의 기준가격이 됩니다.
											</p>
											<input type="text" name="salePrice" id="salePrice" maxlength="8" value="${op:negativeNumberToEmpty(item.salePrice)}" class="amount required _min_10 _number_comma" title="${op:message('M00786')}" /> 원 <%-- <span>(${shopContext.config.taxDisplayTypeText})</span> (세금포함)--%>
											&nbsp(수수료율 : <form:input path="commissionRate" maxlength="4" class="required _number_float form-xs" title="수수료율" /> %)
											<input type="hidden" id="sellerCommissionRate" value="${seller.commissionRate}" />
										</div>
									</td>
									<td class="label">${op:message('M00785')}</td> <!-- 정가 -->
									<td>
										<div>
											<p class="text-info text-sm">
												* 정가는 필수 입력항목이 아니며 금액 입력시 단순 표기용으로 <br/>
												&nbsp;&nbsp;&nbsp;사용됩니다.
											</p>
											<form:input path="itemPrice" maxlength="8" class="amount optional _number_comma" title="${op:message('M00785')}" /> 원 <%-- <span>(${shopContext.config.taxDisplayTypeText})</span> (세금포함)--%>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">공급가 설정</td>
									<td>
										<div>
												<c:if test="${isSellerPage}">
													<c:if test="${item.commissionType == '3'}">
														공급가 설정
													</c:if>
													<c:if test="${item.commissionType == '1'}">
														${op:message('M01610')} (${item.commissionRate}%)	<%-- 입점업체 수수료로 설정 --%>
													</c:if>
													<c:if test="${item.commissionType == '2'}">
														${op:message('M01611')} <%-- 상품별 수수료로 설정 --%>
													</c:if>
												</c:if>
													
												<div ${isSellerPage ? 'style="display:none"': ''}>
													<form:radiobutton path="commissionType" value="3" label="공급가 설정" />
													<form:radiobutton path="commissionType" value="1" label="${op:message('M01610')}" /> <%-- 입점업체 수수료로 설정 --%>
													<form:radiobutton path="commissionType" value="2" label="${op:message('M01611')}" /> <%-- 상품별 수수료로 설정 --%>
												</div>
										</div>
									</td>
									<td class="label">공급가</td>
									<td>
										<div>
											<form:input path="supplyPrice" maxlength="8" class="amount optional _number_comma" readonly="true" title="공급가" /> 원
										</div>
									</td>
									<%-- <td class="label">원가</td>
									<td>
										<div>
											<p class="text-info text-sm"> 
												※ 주의 : 옵션을 구성하실 경우 옵션별로 원가를 입력하시기 바랍니다. <br />
												단, 상품 원가에 금액을 입력하시고 옵션별 원가를 입력하지 않으셨을 때에는 상품별 원가를 사용합니다.
											</p>
											<input type="text" name="costPrice" id="costPrice" maxlength="8" value="${op:negativeNumberToEmpty(item.costPrice)}" class="amount required _number_comma" title="원가" /> 원 <span>(${shopContext.config.taxDisplayTypeText})</span> (세금포함)
										</div>
									</td> --%>
								</tr>
								
								<tr style="display:none">
									<td class="label">비회원 판매가격 설정</td>
									<td>
										<div>
											<form:radiobutton path="salePriceNonmemberFlag" value="N" label="${op:message('M00089')}" />  <!-- 사용안함 -->
											<form:radiobutton path="salePriceNonmemberFlag" value="Y" label="${op:message('M00083')}" />  <!-- 사용 -->
												
											
											<div class="hide_content">
												<input type="text" name="salePriceNonmember" id="salePriceNonmember" maxlength="8" value="${op:negativeNumberToEmpty(item.salePriceNonmember)}" class="amount required-nonmember-price _min_10 _number_comma" title="비회원 가격" /> 원 <%-- <span>※ VAT별도</span>--%>
											</div>
										</div>
									</td>
								</tr>
								
								
								
								<%-- 
								<tr>
									<td class="label">색상</td>
									<td>
										<div id="item_colors">
											<button type="button" class="table_btn mb10" onclick="Common.popup('${requestContext.managerUri}/item/color', 'item-color', 450, 700, 1)"><span>색상관리</span></button>
											<br />
											
											<span class="radio_wrap">
												<input type="radio" name="color" id="color_0" value="" checked="checked" />
												<label for="color_0">없음</label>
											</span>
											
											<c:forEach items="${colors}" var="code" varStatus="i">
												<span class="radio_wrap">
													<input type="radio" name="color" id="color_${i.count}" value="${code.value}" ${op:checked(code.value, item.color)} />
													<label for="color_${i.count}"><span class="item_color_box" style="background:${code.value};"></span> ${code.label}</label>
												</span>
											</c:forEach>
												
											
											
										</div>
									</td>
								</tr>
								 --%>

								<tr id="trStockQuantity">
									<td class="label">재고연동</td>
									<td>
										<div>
											<p class="text-info text-sm">* 옵션이 등록된 경우 옵션재고 수량으로 판단됩니다.</p>
											
											<form:radiobutton path="stockFlag" value="N" label="연동안함 (무제한)" />
											<form:radiobutton path="stockFlag" value="Y" label="연동함" />
										</div>
									</td>
									<td class="label">${op:message('M00930')}</td> <!-- 상품재고 -->
									<td>
										<div>
											<input type="text" name="stockQuantity" id="stockQuantity" maxlength="5" value="${op:negativeNumberToEmpty(item.stockQuantity)}" class="amount required-stock-quantity _min_0 _number_comma" title="${op:message('M00930')}" ${item.stockFlag == 'N' ? 'disabled="disabled"' : ''} /> 개
										</div>
									</td>
								</tr>

								<tr>
									<td class="label">${op:message('M00774')} <%-- 품절여부 --%></td>
									<td>
										<div>
											<p class="text-info text-sm">* 체크 시 상품/옵션 재고여부와 상관없이 무조건 품절로 표시됩니다.</p>

											<form:checkbox path="soldOut" value="1" label="${op:message('M00693')}" /> <%-- 품절 --%>
											<input type="hidden" name="!soldOut" value="0" />
										</div>
									</td>
									<td class="label">${op:message('M01626')} <%-- 관리코드 --%></td>
									<td>
										<div>
											<form:input path="stockCode" maxlength="25" readonly="true" />
											<button type="button" onclick="findErpItem()" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 조회</button>
										</div>
									</td>
								</tr>

								<%--
								<tr>
									<td class="label">${op:message('M00910')} (${op:message('M00787')})</td> <!-- 상품라벨 --> <!-- 판매상태 -->
									<td>
										<div>
											<p class="mb10">
												${op:message('M00931')} <!-- 상품 재고가 0이 되면 자동으로 설정된 상품라벨이 표기됩니다. -->
											</p>
											<span>
												<form:radiobutton path="soldOut" value="1" label="${op:message('M00693')}" /> <!-- 입하대기 -->
											</span>
											<span>
												<form:radiobutton path="soldOut" value="2" label="${op:message('M00692')}" /> <!-- 판매종료 -->
											</span>	
	
											
										</div>
									</td>
								</tr>
								
								<tr>
									<td class="label">${op:message('M00933')}</td> <!-- 재입고 예정일 -->
									<td>
										<div>
											<p>
												${op:message('M00934')} <!-- 상품 재고가 0이되면 자동으로 설정된 값이 출력됩니다. -->
											</p>
											
											<p class="mt10">
												<form:radiobutton path="stockScheduleType" value="" label="${op:message('M00801')}" /><br /> <!-- 없음 -->
											</p>
											<p class="mt5">
												<span style="display:inline-block; width: 100px">
													<form:radiobutton path="stockScheduleType" value="date" label="${op:message('M00935')}" /> <!-- 입하 예정일 -->
												</span>
												<form:input path="stockScheduleDate" maxlength="10" title="${op:message('M00935')}"/><br />
											</p>
											<p class="mt5">
												<span style="display:inline-block; width: 100px">
													<form:radiobutton path="stockScheduleType" value="text" label="${op:message('M00936')}" /> <!-- 기타 텍스트 -->
												</span>
												<form:input path="stockScheduleText" maxlength="30" title="${op:message('M00936')}"/>
											</p>
										</div>
									</td>
								</tr>
								--%>
								<tr>
									<td class="label">${op:message('M00953')}</td> <!-- 최소주문수량 -->
									<td>
										<div>
											<input type="text" name="orderMinQuantity" id="orderMinQuantity" maxlength="5" value="${op:negativeNumberToEmpty(item.orderMinQuantity)}" class="amount optional _min_1 _number_comma" title="${op:message('M00953')}" />
											<span class="f11">※ ${op:message('M00955')} <!-- 입력 값이 없는 경우는 제한이 없습니다. --></span>
									</div></td>
									<td class="label">${op:message('M00954')}</td> <!-- 최대주문수량 -->
									<td>
										<div>
											<input type="text" name="orderMaxQuantity" id="orderMaxQuantity" maxlength="5" value="${op:negativeNumberToEmpty(item.orderMaxQuantity)}" class="amount optional _min_1 _number_comma" title="${op:message('M00954')}" />
											<span class="f11">※ ${op:message('M00955')} <!-- 입력 값이 없는 경우는 제한이 없습니다. --></span>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">쿠폰 사용 가능 여부</td>
									<td colspan="3">
										<div>
											<form:radiobutton path="couponUseFlag" label="사용 가능" value="Y" />
											<form:radiobutton path="couponUseFlag" label="사용 불가능" value="N" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">네이버 쇼핑 사용 여부</td>
									<td>
										<div>
											<form:radiobutton path="naverShoppingFlag" label="활성" value="Y" />
											<form:radiobutton path="naverShoppingFlag" label="비활성" value="N" />
										</div>
									</td>
									<td class="label">네이버 쇼핑 상품명</td>
									<td>
										<div>
											<form:input path="naverShoppingItemName" maxlength="100" class="form-block" disabled="${item.naverShoppingFlag == 'Y' ? 'false' : 'true'}" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">네이버 페이 사용 여부</td>
									<td colspan="3">
										<div>
											<form:radiobutton path="naverPayFlag" label="활성" value="Y" />
											<form:radiobutton path="naverPayFlag" label="비활성" value="N" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">현금특가</td>
									<td colspan="3">
										<div>
											<form:radiobutton path="cashDiscountFlag" label="사용" value="Y" />
											<form:radiobutton path="cashDiscountFlag" label="미사용" value="N" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">렌탈페이 적용 여부</td>
									<td colspan="3">
										<div>
											<form:radiobutton path="rentalPayFlag" label="적용" value="Y" />
											<form:radiobutton path="rentalPayFlag" label="미적용" value="N" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>		
				
				<div class="item_list mt30">
					<h3>
						<span>할인/${op:message('M00246')} 설정</span> <!-- 상품 이미지 등록 -->
					</h3>
					
					<div class="board_write">
							
						<table class="board_write_table">
							<colgroup>
								<col style="width: 180px;">
								<col style="">
							</colgroup>
							<tbody>
								<tr>
									<td class="label">즉시할인</td>
									<td>
										<div>
											<form:radiobutton path="sellerDiscountFlag" value="N" label="${op:message('M00089')}" />  <!-- 사용안함 -->
											<form:radiobutton path="sellerDiscountFlag" value="Y" label="${op:message('M00083')}" />  <!-- 사용 -->
											
											<div class="hide_content">
												판매가에서 
												<form:input path="sellerDiscountAmount" maxlength="6" class="required-seller-discount-amount _min_10 _number_comma amount" title="즉시할인 금액" /> ${op:space()}
												
												<form:select path="sellerDiscountType">
													<form:option value="1">원</form:option>
													<%-- <form:option value="2">%</form:option> --%>
												</form:select>
												할인
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">스팟할인</td>
									<td>
										<form:hidden path="spotStartDate" />
										<form:hidden path="spotEndDate" />
										<form:hidden path="spotStartTime" />
										<form:hidden path="spotEndTime" />
										<c:set var="disabled" value="false" />
										<c:if test="${item.spotFlag == 'Y'}">
											<c:choose>
												<c:when test="${item.spotType == '1' && requestContext.sellerPage == true}">
													<c:set var="disabled" value="true" />
												</c:when>
												<c:when test="${item.spotType == '2' && requestContext.sellerPage == false}">
													<c:set var="disabled" value="true" />
												</c:when>
											</c:choose> 
										</c:if> 
										
										<form:hidden path="spotType" />
										<div id="spot-area">
											<form:radiobutton path="spotFlag" value="N" label="${op:message('M00089')}" disabled="${disabled}" class="spotFlag" />  <!-- 사용안함 -->
											<form:radiobutton path="spotFlag" value="Y" label="${op:message('M00083')}" disabled="${disabled}" class="spotFlag" />  <!-- 사용 -->
											
											<div class="hide_content">
												<c:if test="${item.spotFlag == 'Y'}">
													<p class="text-info">
														(${item.spotType == '1' ? '운영자' : '판매자'}에 의해 스팟할인이 진행 중 입니다.)
													</p>
												</c:if>
												<table class="inner-table">
													<colgroup>
														<col style="width: 150px;" />
														<col style="width: auto;" />
													</colgroup>
													<tbody>


														<tr>
															<td class="label">
																스팟 기간 구분
															</td>
															<td>
																<form:radiobutton path="spotDateType" value="1" label="시점" checked="true"/>
																<form:radiobutton path="spotDateType" value="2" label="기간" />
															</td>
														</tr>

														<tr class="spotDateTypeOne">
															<td class="label">기간</td>
															<td>
																<c:choose>
																	<c:when test="${item.spotFlag == 'Y' && item.spotType == '1' && (!empty sellerContext.login)  && item.spotEndDate > today}">
																		<%--<span class=""><input type="text" id="spotStartDateOne" value="${item.spotStartDate}" maxlength="8" style="width: 80px" class="required-spot _date" title="세일시작일" disabled="${disabled}"/></span>--%>
																		<span class="datepicker"><input type="text" id="spotStartDateOne" value="${item.spotStartDate}" maxlength="8" style="width: 80px" class="_date datepicker" title="세일시작일" disabled="${disabled}"/></span>
																		<span class="wave">~</span>
																		<span class="datepicker"><input id="spotEndDateOne" type="text" value="${item.spotEndDate}" maxlength="8" style="width: 80px" class="_date datepicker" title="세일종료일" disabled="${disabled}" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="datepicker"><input id="spotStartDateOne" type="text" value="${item.spotStartDate}" maxlength="8" class="_date datepicker" title="세일시작일" /></span>
																		<span class="wave">~</span>
																		<span class="datepicker"><input id="spotEndDateOne" type="text" value="${item.spotEndDate}" maxlength="8" class="_date datepicker" title="세일종료일" /></span>

																	</c:otherwise>
																</c:choose>
															</td>
														</tr>
                                                        <tr class="spotDateTypeOne">
															<td class="label">시간대</td>
															<td>
																<select id="spotStartHourOne">
																	<c:forEach begin="0" end="23" step="1" var="hour" varStatus="i">
																		<option value="${hour < 10 ? '0' : ''}${hour}" ${spotStartHour eq hour ? 'selected' : ''}>${hour < 10 ? '0' : ''}${hour}</option>
																	</c:forEach>
																</select> :
																<select id="spotStartMinuteOne">
																	<c:forEach begin="0" end="59" step="1" var="minute" varStatus="i">
																		<option value="${minute < 10 ? '0' : ''}${minute}" ${spotStartMinute eq minute ? 'selected' : ''}>${minute < 10 ? '0' : ''}${minute}</option>
																	</c:forEach>
																</select> ~

																<select id="spotEndHourOne">
																	<c:forEach begin="0" end="23" step="1" var="hour" varStatus="i">
																		<option value="${hour < 10 ? '0' : ''}${hour}" ${spotEndHour eq hour ? 'selected' : ''}>${hour < 10 ? '0' : ''}${hour}</option>
																	</c:forEach>
																</select> :
																<select id="spotEndMinuteOne">
																	<c:forEach begin="0" end="59" step="1" var="minute" varStatus="i">
																		<option value="${minute < 10 ? '0' : ''}${minute}" ${spotEndMinute eq minute ? 'selected' : ''}>${minute < 10 ? '0' : ''}${minute}</option>
																	</c:forEach>
																</select>
															</td>
														</tr>


														<tr class="spotDateTypeTwo">
                                                            <td class="label">기간</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${item.spotFlag == 'Y' && item.spotType == '1' && (!empty sellerContext.login)  && item.spotEndDate > today}">
                                                                        <span class="datepicker"><input id="spotStartDateTwo" type="text" value="${item.spotStartDate}" maxlength="8" style="width: 80px" class="_date datepicker" title="세일시작일" disabled="${disabled}"/></span>
                                                                        <span class="wave">~</span>
                                                                        <span class="datepicker"><input id="spotEndDateTwo" type="text" value="${item.spotEndDate}" maxlength="8" style="width: 80px" class="_date datepicker" title="세일종료일" disabled="${disabled}" /></span>

                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="datepicker"><input id="spotStartDateTwo" type="text" value="${item.spotStartDate}" maxlength="8" class="_date datepicker" title="세일시작일"/></span>
                                                                        <select id="spotStartHourTwo">
                                                                            <c:forEach begin="0" end="23" step="1" var="hour" varStatus="i">
                                                                                <option value="${hour < 10 ? '0' : ''}${hour}" ${spotStartHour eq hour ? 'selected' : ''}>${hour < 10 ? '0' : ''}${hour}</option>
                                                                            </c:forEach>
                                                                        </select> :
                                                                        <select id="spotStartMinuteTwo">
                                                                            <c:forEach begin="0" end="59" step="1" var="minute" varStatus="i">
                                                                                <option value="${minute < 10 ? '0' : ''}${minute}" ${spotStartMinute eq minute ? 'selected' : ''}>${minute < 10 ? '0' : ''}${minute}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                        <span class="wave">~</span>
                                                                        <span class="datepicker"><input id="spotEndDateTwo" type="text" value="${item.spotEndDate}" maxlength="8" class="_date datepicker" title="세일종료일" /></span>
                                                                        <select id="spotEndHourTwo">
                                                                            <c:forEach begin="0" end="23" step="1" var="hour"  varStatus="i">
                                                                                <option value="${hour < 10 ? '0' : ''}${hour}" ${spotEndHour eq hour ? 'selected' : ''}>${hour < 10 ? '0' : ''}${hour}</option>
                                                                            </c:forEach>
                                                                        </select> :
                                                                        <select id="spotEndMinuteTwo">
                                                                            <c:forEach begin="0" end="59" step="1" var="minute" varStatus="i">
                                                                                <option value="${minute < 10 ? '0' : ''}${minute}" ${spotEndMinute eq minute ? 'selected' : ''}>${minute < 10 ? '0' : ''}${minute}</option>
                                                                            </c:forEach>
                                                                        </select>

                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
														</tr>













														<tr>
															<td class="label">요일</td>
															<td>
																<form:hidden path="spotWeekDay" />
																<c:forEach items="${item.spotWeekDayList}" var="code">
																	<label style="margin-right: 10px;"><input type="checkbox" name="day_of_week" ${disabled == true ? 'disabled="disabled"' : ''} value="${code.value}" ${code.detail == '1' ? 'checked="checked"' : '' } /> ${code.label}</label>
																</c:forEach>
															</td>
														</tr>
														<tr>
															<td class="label">할인금액</td>
															<td>
																<form:input path="spotDiscountAmount" title="할인금액" class="_min_10 _number_comma amount" disabled="${disabled}" /> 원
															</td>
														</tr>

















													</tbody>
												</table>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00246')} 지급</td>
									<td>
										<div>
											<p class="text-info text-sm">
												* 사용 설정 시 상품을 구매하는 경우 판매자 부담 ${op:message('M00246')}가 지급됩니다.
											</p>
											<form:radiobutton path="sellerPointFlag" value="N" label="${op:message('M00089')}" />  <!-- 사용안함 -->
											<form:radiobutton path="sellerPointFlag" value="Y" label="${op:message('M00083')}" />  <!-- 사용 -->
										
											<div class="hide_content">
												<c:forEach items="${pointConfigList}" var="pointConfig" varStatus="i">
													<c:if test="${i.index == 0}">
													
														<c:set var="pointConfig" value="${pointConfig}" scope="request" />
														<jsp:include page="include-item-point.jsp" />
													
													</c:if>
												
												</c:forEach>
												
												<c:if test="${empty pointConfigList}">
													<jsp:include page="include-item-point.jsp" />
												</c:if>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="item_list mt30"  ${isSellerPage ? 'style="display:none"' : ''}>
					<h3>
						<span>관리항목</span>
					</h3>
					<div class="board_write">						
						<table class="board_write_table">
							<caption>상품기본정보</caption>
							<colgroup>
								<col style="width: 180px;" />
								<col style="" />
								<col style="width: 160px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">상품${op:message('M00191')}</td> <!-- 공개유무 -->
									<td>
										<div>
											<p>
												<form:radiobutton path="displayFlag" value="N" label="${op:message('M00097')}" /> <!-- 비공개 -->
												<form:radiobutton path="displayFlag" value="Y" label="${op:message('M00096')}" /> <!-- 공개 -->
											</p>
										</div>
									</td>
									<td class="label">전용상품</td>
									<td>
										<div>
											<p>
												<form:select path="privateType">
													<form:options items="${privateTypes}" itemLabel="label" itemValue="value" />
												</form:select>
											</p>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">상품라벨</td>
									<td colspan="3">
										<div>
											<c:forEach items="${labels}" var="label">
												<form:checkbox path="itemLabelValues" value="${label.id}" label="<img src='${label.imageSrc}' alt='${label.description}' class='label_image'>" />
											</c:forEach>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">담당MD</td> <!-- 담당MD -->
									<td colspan="3">
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
									<td class="label">${op:message('M00909')}</td>
									<td colspan="3">
										<div>
											<p>
												<form:radiobutton path="nonmemberOrderType" value="1" label="${op:message('M00920')}" /> <!-- 비회원 구매가능 -->
												<form:radiobutton path="nonmemberOrderType" value="2" label="${op:message('M00921')}" /> <!-- 회원가격 비표시 -->
												<form:radiobutton path="nonmemberOrderType" value="3" label="${op:message('M00922')}" /> <!-- 상세페이지 접속 불가 -->
											</p>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="item_list mt30">
					<h3>
						<span>${op:message('M01003')}</span> <!-- 상품 이미지 등록 -->
					</h3>
					
					<div class="board_write">
							
						<table class="board_write_table item_image_info">
							<colgroup>
								<col style="width: 180px;">
								<col style="">
							</colgroup>
							<tbody>
								<!--  
								<tr>
									<td class="label">${op:message('M00982')}</td>
									<td>
										<div>
											<p>
												<input type="file" name="imageFile" title="${op:message('M00982')}" style="width: 550px;" />
											</p>
											
											<c:if test="${!empty item.itemImage}">
												<p class="item_image_main">
													<img src="${item.imageSrc}" class="item_image size-100" alt="" />
													<a href="javascript:deleteItemImage('main', ${itemId});" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
												</p>
											</c:if>
										
										</div>
									</td>
								</tr>
								-->
								<tr>
									<td class="label"><p>${op:message('M00983')}</p><p>(500px * 500px)</p></td> <!-- 상세이미지 -->
									<td>
										<div>
										
											<button type="button" id="add_detail_image_file" style="display:none" class="table_btn"><span>+ ${op:message('M00984')}</span></button> <!-- 이미지추가 -->
											<p class="text-info text-sm">
												* 상품 이미지는 제한 없이 등록이 가능합니다.<br />
												* [파일선택] 버튼을 선택한 후 파일 선택 창에서 상품 이미지를 복수로 선택하거나 드레그 하여 선택 후 등록해 주십시오.<br /><br />
											</p>
											<p>
												<input type="file" name="detailImageFiles[]" multiple="multiple" />
											</p>
											
											<div id="multiple_files">
											
											</div>
											
											<ul id="item_details_images" class="sortable_item_image clear">
												<c:forEach items="${item.itemImages}" var="itemImage" varStatus="i">
													<c:if test="${itemImage.itemImageId != 0}">
														<li id="item_image_id_${itemImage.itemImageId}">
															<img src="${shop:loadImage(item.itemCode, itemImage.imageName, 'XS')}" class="item_image size-100" alt="" />
															<span class="ordering">${i.count}</span>
															<a href="javascript:deleteItemImage('details', ${itemImage.itemImageId});" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
															
															<input type="hidden" name="itemImageIds" value="${itemImage.itemImageId}" />
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
				</div>

				<div class="item_list mt30">
				
					<h3><span>${op:message('M00975')}</span></h3> <!-- 상품옵션등록 -->
					
					<div class="board_write">
							
						<table class="board_write_table" summary="상품옵션등록">
							<caption>상품옵션등록</caption>
							<colgroup>
								<col style="width: 180px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00976')}</td> <!-- 상품옵션 사용여부 -->
									<td>
										<div>
											<p>
												<form:radiobutton path="itemOptionFlag" value="N" label="${op:message('M00089')}" />  <!-- 사용안함 -->
												<form:radiobutton path="itemOptionFlag" value="Y" label="${op:message('M00083')}" />  <!-- 사용 -->
												<input type="hidden" name="itemOptionFlagValue" value="${item.itemOptionFlag}" />
											</p>
										</div>
									</td>
								</tr>
								<tr class="option-step step1">
									<td class="label">${op:message('M01629')} <%-- 상품옵션 형태 --%></td>
									<td>
										<div>
											<form:radiobutton path="itemOptionType" value="S" label="선택형" />
											<form:radiobutton path="itemOptionType" value="S2" label="2조합형" />
											<form:radiobutton path="itemOptionType" value="S3" label="3조합형" />
											<form:radiobutton path="itemOptionType" value="T" label="텍스트형" />
											<form:radiobutton path="itemOptionType" value="C" label="옵션조합형" />
											<input type="hidden" name="itemOptionTypeValue" value="${item.itemOptionType}" />
										</div>
									</td>
								</tr>
								
							</tbody>
						</table>

						<div class="option-wrapper option-step step2">
							<div class="option-type type-S">
								<h4>■ 선택형</h4>
								<p class="text-info text-sm">* 선택해야하는 옵션이 한 종류인 경우, 세트상품(여러 항목)</p>
								
								<button type="button" class="btn btn-gradient btn-sm add-option-input">+ 항목추가</button>
								<table class="inner-table tbl-option">
									<col style="" />
									<col style="" />
									<col style="width: 100px" />
									<thead>
										<tr>
											<th>옵션명</th>
											<th>옵션값</th>
											<th>삭제</th>
										</tr>
									</thead>
									<tbody id="s-option-input-wrap">
										<tr>
											<td><input type="text" title="옵션명" placeholder="예) 색상" /></td>
											<td><input type="text" title="옵션값" placeholder="예) 빨강,노랑,파랑" /></td>
											<td><a href="#" class="btn btn-dark-gray btn-sm delete_option_input">삭제</a></td>
										</tr>
									</tbody>
								</table>
							</div>
							
							
							<div class="option-type type-S2" style="display:none">
								<h4>■ 2개 조합형</h4>
								<table class="inner-table tbl-option">
									<col style="" />
									<col style="" />
									<col style="" />
									<thead>
										<tr>
											<th>옵션명</th>
											<th>옵션값</th>
										</tr>
									</thead>
									<tbody id="s2-option-input-wrap">
										<tr>
											<td><input type="text" title="옵션명1" placeholder="예) 색상" /></td>
											<td><input type="text" title="옵션값1" placeholder="예) 빨강,노랑,파랑." /></td>
										</tr>
										<tr>
											<td><input type="text" title="옵션명2" placeholder="예) 사이즈" /></td>
											<td><input type="text" title="옵션값2" placeholder="예) S,M,L,XL" /></td>
										</tr>
									</tbody>
								</table>
							</div>
							
							
							<div class="option-type type-S3" style="display:none">
								<h4>■ 3조합형</h4>
								<table class="inner-table tbl-option">
									<col style="" />
									<col style="" />
									<col style="" />
									<thead>
										<tr>
											<th>옵션명</th>
											<th>옵션값</th>
										</tr>
									</thead>
									<tbody id="s3-option-input-wrap">
										<tr>
											<td><input type="text" title="옵션명1" placeholder="예) 상품구분" /></td>
											<td><input type="text" title="옵션값1" placeholder="예) 야구모자,스냅백" /></td>
										</tr>
										<tr>
											<td><input type="text" title="옵션명2" placeholder="예) 색상" /></td>
											<td><input type="text" title="옵션값2" placeholder="예) 빨강,노랑,파랑." /></td>
										</tr>
										<tr>
											<td><input type="text" title="옵션명3" placeholder="예) 사이즈" /></td>
											<td><input type="text" title="옵션값3" placeholder="예) S,M,L,XL" /></td>
										</tr>
									</tbody>
								</table>
							</div>
	
							<div class="item-option-table">
								<div class="buttons">
									<a href="#" class="btn btn-warning btn-sm make-item-option">적용</a>
								</div>
									
								<div>
									<table class="inner-table tbl-option active">
										<colgroup>
											<col style="" />
											<col style="" />
											<col style="" />
											<col style="" />
											<col style="" />
											<col style="width: 100px" />
											<col style="" />
											<col style="" />
											<col style="width: 150px" />
											<col style="width: 70px" />
										</colgroup>
										<thead>
											<tr>
												<th><form:input path="itemOptionTitle1" /></th>
												<th><form:input path="itemOptionTitle2" /></th>
												<th class="option-S3"><form:input path="itemOptionTitle3" /></th>
												<th>추가금액</th>
												<th>원가</th>
												<th>재고연동</th>
												<th>재고수량</th>
												<th>판매상태</th>
												<th>노출여부</th>
												<th>관리코드</th>
												<th>삭제</th>
											</tr>
										</thead>
										<tbody id="item-options">
										
											<c:set var="optionCount" value="0" />
											<c:forEach items="${item.itemOptions}" var="itemOption" varStatus="i">
												<c:if test="${itemOption.optionType != 'T' && itemOption.optionType != 'C'}">
													<c:set var="optionCount">${optionCount + 1}</c:set>
													<c:set var="itemOption" value="${itemOption}" scope="request"></c:set>
 													
 													<jsp:include page="include-item-option.jsp" />
 													
												</c:if>
											</c:forEach>
											
											<c:if test="${optionCount == 0}">
												<jsp:include page="include-item-option.jsp" />
											</c:if>
										</tbody>
									</table>
									<div class="item-options-info">
										<button type="button" class="btn btn-gradient btn-sm add-item-option"><span>+ 옵션추가</span></button>
									</div>
								</div>
							</div>
							
							<div class="option-type type-T" style="display:none;">
								<h4>■ 텍스트형</h4> 
								<table class="inner-table tbl-option active">
									<colgroup>
										<col style="" />
										<col style="width: 70px" />
										<col style="width: 70px" />
									</colgroup>
									<thead>
										<tr>
											<th>옵션명</th>
											<th>노출여부</th>
											<th>삭제</th>
										</tr>
									</thead>
									<tbody id="item-text-options">
										<c:set var="textOptionCount" value="0" />
										<c:forEach items="${item.itemOptions}" var="itemTextOption" varStatus="i">
											<c:if test="${itemTextOption.optionType == 'T'}">
												<c:set var="textOptionCount">${textOptionCount + 1}</c:set>
												<c:set var="itemTextOption" value="${itemTextOption}" scope="request" />
 												
 												<jsp:include page="include-item-text-option.jsp" />
											</c:if>
										</c:forEach>
										
										<c:if test="${textOptionCount == 0}">
											<jsp:include page="include-item-text-option.jsp" />
										</c:if>
									</tbody>
								</table>
								<div class="item-options-info">
									<button type="button" class="btn btn-gradient btn-sm add-item-text-option"><span>+ 옵션추가</span></button>
								</div>
							</div>

							<div class="option-type type-C">
								<h4>
									■ 옵션조합형
									<button type="button" class="btn btn-gradient btn-sm add-combination-option-group">+ 그룹추가</button>
								</h4>
								<p class="text-info text-sm">
                                    * 옵션 타입이 기본인 경우, 옵션은 1개만 등록 가능합니다.</br>
									* 옵션을 드래그하여 정렬 순서를 변경할 수 있습니다. </br/>
									* 옵션 관리코드가 존재하는 경우 (추가금액, 원가, 재고연동, 재고수량, 판매상태)는 원본 상품 데이터로 자동 설정됩니다.
								</p>
								<c:set var="index" value="0" scope="request" />
								<c:choose>
									<c:when test="${item.itemOptionType == 'C'}">
										<c:forEach items="${item.optionGroups}" var="group" varStatus="i">
											<c:set var="group" value="${group}" scope="request" />
											<c:set var="index" value="${i.index}" scope="request" />
											<jsp:include page="include-item-combination-group.jsp" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<jsp:include page="include-item-combination-group.jsp" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div> <!-- // board_write -->
				</div>

				<div class="item_list mt30">
					<h3><span>${op:message('M01619')}</span></h3>  <!-- 추가구성상품 -->
					<div class="board_write">
						<table class="board_write_table" summary="추가구성상품">
							<caption>추가구성상품</caption>
							<colgroup>
								<col style="width: 180px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M01620')}</td> <!-- 추가구성 사용여부 -->
									<td>
										<div>
											<p>
												<form:radiobutton path="itemAdditionFlag" value="N" label="${op:message('M00089')}" />  <!-- 사용안함 -->
												<form:radiobutton path="itemAdditionFlag" value="Y" label="${op:message('M00083')}" />  <!-- 사용 -->
											</p>
										</div>
									</td>
								</tr>
								<!-- 추가구성상품 선택시 노출 -->
								<c:set var="additionItemDisplay" value="display:none;" />
								<c:if test="${item.itemAdditionFlag == 'Y' || !empty item.itemAdditions}">
									<c:set var="additionItemDisplay" value="" />
								</c:if>
								<tr id="addition_item_area" style="${additionItemDisplay}">
									<td class="label">추가구성상품</td>
									<td>
										<div>
											<p class="mb10">
												<button type="button" id="button_add_addition_item" class="btn btn-gradient btn-sm" onclick="findItemPopup('addition', '${item.itemUserCode}')">+ 추가구성상품 추가</button>
												<button type="button" class="btn btn-gradient btn-sm" onclick="Shop.deleteRelationItemAll('addition')">x ${op:message('M00411')}</button> <!-- 전체삭제 -->
											</p>

											<ul id="addition" class="sortable_item_addition">
												<li style="display: none;"></li>

												<c:forEach items="${item.itemAdditions}" var="itemAddition" varStatus="i">
													<c:if test="${!empty itemAddition.item.itemId}">
														<li id="addition_item_${itemAddition.item.itemId}">
															<input type="hidden" name="additionItemIds" value="${itemAddition.item.itemId}" />
															<p class="image"><img src="${shop:loadImageBySrc(itemAddition.item.imageSrc, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
															<p class="title">
																[${itemAddition.item.itemUserCode}]<br />${itemAddition.item.itemName}<br/>
																&#60;퀵배송 요금 ${itemAddition.item.quickDeliveryExtraChargeFlag}&#62;
															</p>
															<span class="ordering">${i.count}</span>
															<a href="javascript:Shop.deleteRelationItem('addition_item_${itemAddition.item.itemId}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
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
				</div>

				<div class="item_list mt30">
					<h3><span>${op:message('M01612')}</span></h3> <%-- 배송정보 설정 --%>
					
					<div class="board_write">
						<form:hidden path="shipmentGroupCode" />
						<table class="board_write_table" summary="배송비설정">
							<colgroup>
								<col style="width: 180px;" />
								<col style="" />
								<col style="width: 160px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">배송구분</td>
									<td>
										<div>
											<form:select path="deliveryType" class="required" title="배송구분">
												<option value="">선택</option>
												<form:option value="1">본사배송</form:option>
												<form:option value="2">업체배송</form:option>
											</form:select>
										</div>
									</td>
								
									<td class="label">택배사</td>
									<td>
										<div>
											<form:hidden path="deliveryCompanyName" />
											<form:select path="deliveryCompanyId" class="required" title="택배사">
												<option value="">택배사 선택</option>
												<form:options items="${deliveryCompanyList}" itemValue="deliveryCompanyId" itemLabel="deliveryCompanyName" />
											</form:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M01613')}</td> <%-- 출고지 주소 --%>
									<td colspan="3">
										<div>
											<form:hidden path="shipmentId" />
											<form:input path="shipmentAddress" readonly="true" class="half required" title="출고지 주소" /> 
											
											<button type="button" class="btn btn-dark-gray btn-sm change-shipment-address"><span>주소변경</span></button>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00643')} <span class="require">*</span></td> <%-- 배송비 설정 --%>
									<td colspan="3">
										<div>
											<h4>■ 배송비 설정 </h4>
											
											<form:hidden path="shippingGroupCode" />
											<form:hidden path="shipping" />
											<form:hidden path="shippingFreeAmount" />
											
											<table class="inner-table th-center shipping-option">
												<col style="width: 150px;" />
												<col style="width: 170px;" />
												<col style="" />
												<tr>
													<th>배송비 종류</th>
													<th>배송비</th>
													<th>기준 (구매금액:판매가+옵션가+추가구성상품금액)</th>
												</tr>
												<tr class="shipping-type-1">
													<td><form:radiobutton path="shippingType" value="1" class="required" title="배송비" label="${op:message('M00928')}" /> <%-- 무료배송 --%></td>
													<td class="text-right">
														무료
														<input type="hidden" class="opt-shipping" value="0" />
														<input type="hidden" class="opt-shipping-free-amount" value="0" />
													</td>
													<td>수량/주문금액에 상관없이 무료배송</td>
												</tr>
												<tr class="shipping-type-2">
													<td><form:radiobutton path="shippingType" value="2" class="required" title="배송비" label="${op:message('M01614')}" /> <%-- 판매자조건부 --%></td>
													<td class="text-right">
														<span class="seller-empty" ${!empty seller ? 'style="display:none"' : ''}>
															-
														</span>
														
														<span class="seller-info" ${empty seller ? 'style="display:none"' : ''}>
															<span class="opt-shipping-text">${op:numberFormat(seller.shipping)}</span> 원
														<span>
														
														<input type="hidden" class="opt-shipping" value="${seller.shipping}" />
														<input type="hidden" class="opt-shipping-free-amount" value="${seller.shippingFreeAmount}" />	
														<input type="hidden" class="opt-shipping-extra-charge1" value="${seller.shippingExtraCharge1}" />	
														<input type="hidden" class="opt-shipping-extra-charge2" value="${seller.shippingExtraCharge2}" />	
													</td>
													<td>
														<span class="seller-empty" ${!empty seller ? 'style="display:none"' : ''}>
															판매자 조건부 배송비를 설정해 주세요.
														</span> 
													
														<span class="seller-info" ${empty seller ? 'style="display:none"' : ''}>
															<span class="opt-shipping-text">${op:numberFormat(seller.shipping)}</span>원 
															(<span class="opt-shipping-free-amount-text">${op:numberFormat(seller.shippingFreeAmount)}</span>원 이상 구매시 무료)
														</span>
													</td>
												</tr>
												<tr class="shipping-type-3">
													<td><form:radiobutton path="shippingType" value="3" class="required" title="배송비" label="${op:message('M01615')}" /> <%-- 출고지조건부 --%></td>
													<td class="text-right">
														<span class="shipment-empty" ${!empty shipment ? 'style="display:none"' : ''}>
															-
														</span>
														
														<span class="shipment-info" ${empty shipment ? 'style="display:none"' : ''}>
															<span class="opt-shipping-text">${op:numberFormat(shipment.shipping)}</span> 원
														<span>
														
														<input type="hidden" class="opt-shipping" value="${shipment.shipping}" />
														<input type="hidden" class="opt-shipping-free-amount" value="${shipment.shippingFreeAmount}" />
														<input type="hidden" class="opt-shipping-extra-charge1" value="${shipment.shippingExtraCharge1}" />	
														<input type="hidden" class="opt-shipping-extra-charge2" value="${shipment.shippingExtraCharge2}" />	
													</td>
													<td>
														<span class="shipment-empty" ${!empty shipment ? 'style="display:none"' : ''}>
															출고지 조건부 배송비를 설정해 주세요.
														</span> 
													
														<span class="shipment-info" ${empty shipment ? 'style="display:none"' : ''}>
															<span class="opt-shipping-text">${op:numberFormat(shipment.shipping)}</span>원 
															(<span class="opt-shipping-free-amount-text">${op:numberFormat(shipment.shippingFreeAmount)}</span>원 이상 구매시 무료)
														</span>
														<!-- <a href="#" class="btn btn-dark-gray btn-xs change-shipment-address">기준설정</a> -->
													</td>
												</tr>
												<tr class="shipping-type-4">
													<td><form:radiobutton path="shippingType" value="4" class="required" title="배송비" label="${op:message('M01616')}" /> <%-- 상품조건부 --%></td>
													<td class="text-right">
														<input type="text" maxlength="6" value="${item.shipping}" readonly="readonly" class="required-shipping-4 _min_10 _number opt-shipping amount" title="배송비" /> 원
													</td>
													<td><input type="text" maxlength="6" value="${item.shippingFreeAmount}" readonly="readonly" class="required-shipping-4 _min_10 _number opt-shipping-free-amount amount" title="무료배송 조건 금액" /> 원 이상 구매 시 무료</td>
												</tr>
												<tr class="shipping-type-5">
													<td><form:radiobutton path="shippingType" value="5" class="required" title="배송비" label="${op:message('M01617')}" /> <%-- 개당배송비 --%></td>
													<td class="text-right"><input type="text" maxlength="6" value="${item.shipping}" readonly="readonly" class="required-shipping-5 _min_10 _number opt-shipping amount" title="배송비" /> 원</td>
													<td>
														<input type="hidden" class="opt-shipping-free-amount" value="0" /> 
														수량 <form:input path="shippingItemCount" maxlength="2" readonly="readonly" class="required-shipping-5 _min_1 _number form-xs" title="개방배송비 부과 기준 상품 수" />개마다 배송비 추가
													</td>
												</tr>
												<tr class="shipping-type-6">
													<td><form:radiobutton path="shippingType" value="6" class="required" title="배송비" label="${op:message('M01618')}" /> <%-- 고정배송 --%></td>
													<td class="text-right"><input type="text" maxlength="6" value="${item.shipping}" readonly="readonly" class="required-shipping-6 _min_10 _number opt-shipping amount" title="배송비" /> 원</td>
													<td><input type="hidden" class="opt-shipping-free-amount" value="0" /> 수량/주문금액과 상관없이 고정 배송비</td>
												</tr>
											</table>
										
										
											<h4 style="margin-top: 20px;">■ 제주/도서산간 추가 배송비 설정</h4>
											
											<p class="text-info text-sm" style="position: relative;">
												* 제주/도서산간 지역 배송지의 경우 구매자에게 추가 운송비를 부담하게 설정합니다.<br />
												<a href="javascript:Common.popup('/island/island-popup?mode=1', 'islandPopup', 600, 580)" class="btn btn-dark-gray btn-xs" style="position: absolute; right: 0; bottom: 3px;">제주/도서산간지역 보기</a>
											</p>
											
											<table class="inner-table th-center shipping-option">
												<col style="width: 150px;" />
												<col style="" />
												<col style="width: 150px;" />
												<col style="" />
												<tr>
													<th>제주</th>
													<td><form:input path="shippingExtraCharge1" readonly="true" class="amount reauired _number_comma" title="제주도 추가 배송비" /> 원</td>
													<th>도서산간</th>
													<td><form:input path="shippingExtraCharge2" readonly="true" class="amount reauired _number_comma" title="도서산간 추가 배송비" /> 원</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">퀵배송 추가요금 설정</td>
									<td colspan="3">
										<div>
											<p class="text-info text-sm" style="position: relative;">
												* 본 상품이 추가구성상품으로 포함될 경우 퀵배송 요금을 추가합니다.
											</p>
											<form:radiobutton path="quickDeliveryExtraChargeFlag" value="Y" label="설정" />
											<form:radiobutton path="quickDeliveryExtraChargeFlag" value="N" label="미설정" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">반품/교환 신청 가능 여부</td>
									<td colspan="3">
										<div>
											<form:radiobutton path="itemReturnFlag" value="Y" label="가능" />
											<form:radiobutton path="itemReturnFlag" value="N" label="불가능" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">반품/교환 구분</td>
									<td>
										<div>
											<form:select path="shipmentReturnType" class="required" title="반송구분">
												<option value="">선택</option>
												<form:option value="1">본사반품</form:option>
												<form:option value="2">업체반품</form:option>
											</form:select>
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">반품/교환 주소</td>
									<td colspan="3">
										<div>
											<form:hidden path="shipmentReturnId" />
											<form:input path="shipmentReturnAddress" readonly="true" class="half required" title="반송지 주소" /> 
											
											<button type="button" class="btn btn-dark-gray btn-sm change-shipment-return-address">주소변경</button>
										</div>
									</td>
								</tr>
								
								<tr>
									<td class="label">반품/교환 배송비</td>
									<td colspan="3">
										<div>
											<p class="text-info text-sm">
												* 반품/교환 시 배송비를 고객/판매자에게 안내됩니다. (정산에 반영되지 않습니다.)<br />
												* 편도 기준 금액으로 입력해 주세요. <br />
												* 교환의 경우 입력한 금액 * 2 한 금액으로 안내됩니다. (2,500원 * 2 = 5,000원)<br />
												* 단순 변심, 상품 파손 등의 반품/교환 사유를 확인하고 필요 시 구매자로 부터 반품/교환비를 오프라인으로 결제한 후에 반품/교환 절차를 진행해 주십시오.
											</p>
											<form:input path="shippingReturn" class="amount required _number_comma" title="반품/교환 배송비" /> 원 (편도 기준 금액)
										</div>
									</td>
								</tr>
								
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="item_list item_info mt30">
					<h3><span>${op:message('M01621')}</span></h3> <%-- 상품정보고시 --%>
					
					<%--
					<button type="button" class="btn btn-gradient btn-sm add_item_info" onclick="addItemInfo()"><span>${op:message('M01002')}</span></button>	 <!-- 항목추가 -->	
					--%>
					<table class="board_write_table">
						<caption>상품정보등록</caption>
						<colgroup>
							<col style="width: 180px;" />
							<col style="" />
						</colgroup>
	
						<tbody id="item_info_area">
							<tr>
								<td class="label">${op:message('M01628')} <%-- 상품의 상품군 --%></td>
								<td>
									<div>
										<form:select path="itemNoticeCode" title="${op:message('M01628')}"> <%-- 상품의 상품군 --%>
											<option value="">선택하세요</option>
											<form:options items="${itemNoticeCodes}"  itemValue="itemNoticeCode" itemLabel="itemNoticeTitle" />
										</form:select>
										
										<label class="check-all-item-notice-label"><input type="checkbox" class="check-all-item-notice" /> ${op:message('M01622')} <%-- ‘상세정보 별도표기’ 모두 선택 --%></label>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					
					<div id="item-notice-data" style="display:none">
						<c:forEach items="${item.itemInfos}" var="itemInfo">
							<p>
								<span class="title">${itemInfo.title}</span>
								<span class="desc">${itemInfo.description}</span>
							</p>
						</c:forEach>
					</div>
					
					<div class="check-all-item-notice-label">
						* 하기의 정보는 <strong>전자상거래법(제13조2항) 및 공정위 상품정보제공고시</strong>에 의거하여 판매회원이 스스로의 책임으로 입력하여야 하는 사항입니다.<br />
						* 여러 상품(주된 상품에 부수되는 상품은 제외)을 추가선택으로 판매하는 경우에는 각 상품에 해당하는 정보를 모두 입력하여야 합니다.<br />
						* 제공되는 입력란의 공간이 부족한 경우에는 광고화면에 직접 입력하셔도 무방합니다.
					</div>
				</div>

				<div class="item_list mt30">
					<h3><span>상품 상/하단 내용 설정</span></h3>

					<div class="board_write">
						<table class="board_write_table" summary="상품 상/하단 내용 설정">
							<colgroup>
								<col style="width: 180px;" />
								<col style="" />
								<col style="width: 160px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">상품 상단 내용 사용여부</td>
									<td colspan="3">
										<div>
											<form:radiobutton path="headerContentFlag" label="사용안함" value="N" />
											<form:radiobutton path="headerContentFlag" label="사용" value="Y" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">상품 하단 내용 사용여부</td>
									<td colspan="3">
										<div>
											<form:radiobutton path="footerContentFlag" label="사용안함" value="N" />
											<form:radiobutton path="footerContentFlag" label="사용" value="Y" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="item_list mt30">
					<h3><span>${op:message('M00990')}</span></h3> <!-- 상품 상세 설명 -->
								
					<form:textarea path="detailContent" cols="30" rows="20" style="width: 1085px" class="" title="${op:message('M00990')}" />
				</div>
				
				<div class="copy_button">
					<button type="button" class="btn btn-gradient btn-sm add_item_point" onclick="copyContent('detailContent')"><span>&nbsp;⬇︎ COPY &nbsp;︎</span></button>
				</div>
				
				<div class="item_list">
					<h3><span>${op:message('M00990')} (${op:message('M00236')})</span></h3> <!-- 상품 상세 설명 --> <!-- 모바일 -->
								
					<form:textarea path="detailContentMobile" cols="30" rows="6" style="width: 1085px" title="${op:message('M00990')} (${op:message('M00236')})" />
				</div>

				<div class="item_list mt30">
					<h3><span>카테고리 필터설정</span></h3> <!-- 카테고리 필터 -->
					<div class="board_write">
						<table class="board_write_table" id="categoryFilterSetting">
							<colgroup>
								<col style="width: 180px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td colspan="2">
										<div>
											<h4>카테고리 선택 시 등록 된 필터가 노출됩니다.</h4>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div id="categoryInput"></div>

				<div class="item_list mt30">
					<h3><span>${op:message('M00985')}</span></h3> <!-- 관련상품등록 -->
					
					<div class="board_write">

						<table class="board_write_table" summary="관련상품등록">
							<caption>관련상품등록</caption>
							<colgroup>
								<col style="width: 180px;" />
								<col style="" />
							</colgroup>
							<tbody>
								<tr>
									<td class="label">${op:message('M00986')}</td> <!-- 관련상품 출력 방법 -->
									<td>
										<div>
											<p>
												<form:radiobutton path="relationItemDisplayType" value="1" label="${op:message('M00987')}" />  <!-- 관련상품을 해당카테고리에서 임의로 출력합니다. -->
												<form:radiobutton path="relationItemDisplayType" value="2" label="${op:message('M00988')}" />  <!-- 관련상품을 선택합니다. -->
											</p>
										</div>
									</td>
								</tr>
								
								<!-- 관련상품을 선택시 노출 -->
								<c:set var="relationItemDisplay" value="display:none;" />
								<c:if test="${item.relationItemDisplayType == '2' || !empty item.itemRelations}">
									<c:set var="relationItemDisplay" value="" />
								</c:if>
								<tr id="relation_item_area" style="${relationItemDisplay}">
									<td class="label">${op:message('M01213')}</td> <!-- 관련상품 -->
									<td>
										<div>
											<p class="mb10">
												<%-- <button type="button" id="button_add_relation_item" class="btn btn-gradient btn-sm" onclick="Shop.findItem('related')">+ ${op:message('M00989')}</button> --%> <!-- 관련상품 추가 -->
												<button type="button" id="button_add_relation_item" class="btn btn-gradient btn-sm" onclick="findItemPopup('related','${item.itemUserCode}')">+ ${op:message('M00989')}</button> <!-- 관련상품 추가 -->
												<button type="button" class="btn btn-gradient btn-sm" onclick="Shop.deleteRelationItemAll('related')">x ${op:message('M00411')}</button> <!-- 전체삭제 -->
											</p>
											
											<ul id="related" class="sortable_item_relation">
												<li style="display: none;"></li>
												<c:forEach items="${item.itemRelations}" var="itemRelation" varStatus="i">
													<c:if test="${!empty itemRelation.item.itemId}">
														<li id="related_item_${itemRelation.item.itemId}">
															<input type="hidden" name="relatedItemIds" value="${itemRelation.item.itemId}" />
															<p class="image"><img src="${shop:loadImageBySrc(itemRelation.item.imageSrc, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
															<p class="title">[${itemRelation.item.itemUserCode}]<br />${itemRelation.item.itemName}</p>
															
															<span class="ordering">${i.count}</span>
															<a href="javascript:Shop.deleteRelationItem('related_item_${itemRelation.item.itemId}');" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
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
				</div>
				
				
				<div class="item_list mt30">
					<h3>
						<span>${op:message('M00995')}</span> <!-- SEO 설정 -->
						<span class="f12"></span>
					</h3>
					
					<div class="board_write">
							
						<table class="board_write_table">
							<colgroup>
								<col style="width: 180px;">
								<col style="">
							</colgroup>
							<tbody>
								<tr>
									<td class="label">검색 엔진에 공개</td>
									<td>
										<div>
											<p class="text-info text-sm">
												* 검색 엔진에 상품 페이지 공개 여부를 설정합니다.
											</p>
											<p>
												<form:radiobutton path="seo.indexFlag" value="Y" label="${op:message('M01418')}" /> <!-- index 시킴 -->
												<form:radiobutton path="seo.indexFlag" value="N" label="${op:message('M01419')}" /> <!-- index 시키지 않음 -->
											</p>
										</div>
									</td>
								</tr>
								
								<tr>
									<td class="label">${op:message('M00090')}</td> <!-- 브라우저 타이틀 -->
									<td>
										<div>
											<p class="text-info text-sm">
												* 제목은 브라우저의 상단과 검색 엔진에 페이지 제목으로 나타납니다. <br />
												* 제목은 최대 55-60자 까지 가능합니다.  <br />
												* 2-5 단어로 페이지를 설명할 수 있는 제목을 선택하세요.<br />
												* (예: 상품명, 페이지명 등)
												* (예: 상품페이지의 경우 상품명을 입력해 주시면 됩니다.)
											</p>
											<form:input path="seo.title" maxlength="100" class="eight" title="${op:message('M00090')}" />
											
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00997')}</td> <!-- Meta 키워드 -->
									<td>
										<div>
											<p class="text-info text-sm">
												* 최대 10 키워드로 페이지를 홍보할 수 있습니다. <br />
												* 키워드는 웹사이트 콘텐츠를 설명하는 단어나 짧은 구문이며 검색 사이트에서 사용자가 해당 페이지를 검색할 때 사용할 만한 단어를 포함해야 합니다.<br />
												* 태그란에 키워드를 입력할 때는 쉼표로 키워드들을 구분합니다.
											</p>
											<form:input path="seo.keywords" maxlength="100" class="eight" title="${op:message('M00997')}" />
											
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M00998')}</td> <!-- Meta용 기술서 -->
									<td>
										<div>
											<p class="text-info text-sm">
												* 해당 문구는 검색 엔진 목록의 사이트 제목 아래에 표시됩니다. <br />
												* 페이지 설명은 최대 250자 까지 사용 가능합니다. (또는 약  15-20 단어) <br />
												* (예: 페이지 제목이 Google 지도인 경우, 사이트 설명은 "웹에서 지도와 운전 경로를 보고 지역 정보를 검색하세요!"가 될 수 있습니다.)
											</p>
											<form:textarea path="seo.description" maxlength="200" class="full" rows="5" title="${op:message('M00998')}" />
										</div>
									</td>
								</tr>
								
								<tr>
									<td class="label">${op:message('M00999')}</td> <!-- H1태그 -->
									<td>
										<div>
											<p class="text-info text-sm">
											※ ${op:message('M01040')} <!-- 페이지 상단의 <H1> 태그에 배포됩니다. -->
											</p>
											<form:input path="seo.headerContents1" maxlength="100" title="${op:message('M00999')}" class="eight" />
										</div>
									</td>
								</tr>
								<%-- 
								<tr>
									<td class="label">${op:message('M01000')}</td> <!-- 테마워드용 타이틀 -->
									<td>
										<div>
											<form:input path="seo.themawordTitle" title="${op:message('M01000')}" class="eight" />
											<button type="button" class="copy_item_name btn btn-gradient btn-sm"><span>${op:message('M01023')}</span></button> <!-- 상품명 복사 -->
										</div>
									</td>
								</tr>
								<tr>
									<td class="label">${op:message('M01001')}</td> <!-- 테마워드용 기술서 -->
									<td>
										<div>
											<form:textarea path="seo.themawordDescription" cols="30" rows="3" title="${op:message('M01001')}" />
										</div>
									</td>
								</tr>
								 --%>
							</tbody>
						</table>
								
					</div> <!-- // board_write -->		
					
						
				</div> <!-- // item_list02 -->
				
				<div id="buttons" class="tex_c mt20">
					<c:if test="${pendingApproval == '1'}">
					<button type="button" class="btn btn-warning btn-approval"><span class="glyphicon glyphicon-ok"></span> 승인처리</button>
					</c:if>
					<button type="submit" class="btn btn-active">${item.itemId == 0 ? op:message('M00088') : op:message('M00087')}</button>
					<a href="javascript:Link.list('${requestContext.managerUri}/item/list')" class="btn btn-default">${op:message('M00480')}</a>	<!-- 목록 -->
				</div>		
			</div>		
		</form:form>
		
		

		
<table id="item_point_template" style="display:none">
	<tbody>
		<tr>
			<td>
				<input type="text" name="point" maxlength="4" class="three _number" title="${op:message('M00246')}" />
				<input type="hidden" name="pointType" />
				{INPUT_RADIO}
			</td>
			<td>
				<span class="datepicker"><input type="text" name="pointStartDate" class="term _date" maxlength="8" title="시작일"></span>
				<select name="pointStartTime" title="시간 선택">
					<c:forEach items="${hours}" var="code">
						<option value="${code.value}">${code.label}</option> 
					</c:forEach>
				</select>시 ~
				<span class="datepicker"><input type="text" name="pointEndDate" class="term _date" maxlength="8" title="종료일"></span>
				<select name="pointEndTime" title="시간 선택">
					<c:forEach items="${hours}" var="code">
						<option value="${code.value}">${code.label}</option> 
					</c:forEach>
				</select>시 59분
				
				<input type="hidden" name="pointRepeatDay" value="" />
			</td>
			<%--
			<td>
				<select name="pointRepeatDay">
					<option value="">전체</option>
					<c:forEach begin="1" end="31" step="1" var="i">
						<option value="${ i }">${ i }</option>
					</c:forEach>
				</select> ${op:message('M00511')} <!-- 일 ->
			</td>
			--%>
			
			<td><a href="#" class="fix_btn delete_item_point">${op:message('M00074')}</a></td> <!-- 삭제 -->
		</tr>
	</tbody>
</table>


<module:smarteditorInit />
<module:smarteditor id="detailContent" />		
<module:smarteditor id="detailContentMobile" />

<script type="text/javascript">
$(function() {
	// 필수 입력항목 마커.
	Common.displayRequireMark();

	// 상세설명 - 추가/삭제 이벤트 핸들러
	// itemInfoEventHandler();
	
	$('#brand').on('change', function() {
		$("#brandId").val($("#brand option:selected").attr("data-id"));
	});

	$("input[name='spotDateType']").on('change', function() {
        selectSpotDateType($(this).val());
    });

	// 스팟 기간 구분
    selectSpotDateType('${item.spotDateType}');


	//팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	
	
	// 버튼 스크롤
	scrollButtons();
	
	
	// 비회원 판매가격 이벤트 핸들러.
	initNonmemberPriceEvent();
	
	// 판매자 선택 이벤트 핸들러.
	initSellerIdEvent();

	// 재고 연동 여부에 따른 수량입력 show/hide 이벤트
	initStockFlagEvent();
	
	// 사은품 이벤트 핸들러.
	initFreeGiftEvent();

	// 상품 수수료 설정 이벤트 핸들러.
	initCommissionEvent();
	
	// 할인 / 포인트 설정 이벤트 핸들러 
	initDiscountAndPointEvent();
	
	// 상품 옵션 이벤트 핸들러.
	initItemOptionEvent();
	
	// 추가 상품 이벤트 핸들러.
	initItemAdditionEvent();
	
	// 배송 정보 설정 이벤트 핸들러.
	initShippingEvent();
		
	// 상품정보고시 관련 이벤트 핸들러.
	initItemNoticeEvent();
	
	
	// 상품 승인 처리 이벤트
	initItemApprovalEvent();
	

	// Validation 조건 설정.
	setValidatorCondition();

	// 카테고리 필터 조회(수정 시)
	if(${mode == 'edit'}){
		getItemCategoryFilter(${categoryId})
	}

	// 카테고리 필터 조회(복사 시)
	if(${mode == 'copy'}){
		getItemCategoryFilter(${categoryId})
	}

	// 숫자 컴마.
	Common.addNumberComma();

	// multiple file is not support
	if (!File.isSupportMultiple) {
		// 파일 추가
		$('#add_detail_image_file').css('margin-bottom', '10px').show().on("click", function() {
			var html = '<p><input type="file" name="detailImageFiles[]" multiple="multiple" /> <a href="#" class="delete_detail_image_file">[' + Message.get("M00074") + ']</a></p>';  // 삭제
			$('#multiple_files').append(html);
		});
		
		// 추가항목 삭제
		$('#multiple_files').on('click', '.delete_detail_image_file', function(e) {
			e.preventDefault();
			$(this).parent().remove();
		});
	}
	
	
	// 상품 카테고리 드레그
	$('.sortable_item_category').sortable({
		placeholder: "sortable_item_category_placeholder",
		stop : function(event, ui){
			var cId = $(ui.item[0]).find("input").val();
			getItemCategoryFilter(cId);
		}
	});
	
	
	// 상세이미지 드레그
	$('.sortable_item_image').sortable({
		placeholder: "sortable_item_image_placeholder"
	});
	 
	// 관련상품 드레그
    $(".sortable_item_relation").sortable({
        placeholder: "sortable_item_relation_placeholder"
    });

	// 추가구성상품 드레그
	$(".sortable_item_addition").sortable({
		placeholder: "sortable_item_addition_placeholder"
	});

	// 옵션조합형 드레그
	$(".sortable_item_combination_option").sortable({
		placeholder: "sortable_item_combination_option_placeholder"
	});
	
    $(".sortable_item_image, .sortable_item_relation, .sortable_item_addition, .sortable_item_combination_option").disableSelection();
	
	      
	$('#itemUserCode').on("change", function() {
		$('#useItemUserCode').val("N");
	});

	// 포인트 설정 정보 노출 여부 체크
	displayItemPointConfig();
	

	// 포인트 설정 삭제
	$('#item_point_area').on('click', '.delete_item_point', function(e){
		e.preventDefault();

		$(this).closest("tr").remove();
		displayItemPointConfig();
	});

	// 관련상품 선택방법
	setRelationItemDisplay();
	$('input[name=relationItemDisplayType]').on('click', function() {
		setRelationItemDisplay();
	});

	// 추가구성상품 사용여부
	setAdditionItemDisplay();
	$('input[name="itemAdditionFlag"]').on('click', function() {
		setAdditionItemDisplay();
	});
	
	// 상품코드 중복체크
	$('#btn_check_duplicate').on("click", function(e){
		e.preventDefault();

		var $itemUserCode = $('#itemUserCode');
		if (!$.validator.required($itemUserCode)) {
			return false;
		}

		$.post("${requestContext.managerUri}/item/check-for-duplicate-item-user-code", {'itemUserCode': $itemUserCode.val()}, function(response) {
			Common.responseHandler(response, function(response) {
				$('#useItemUserCode').val("Y");
				alert(Message.get("M00885"));	// 사용가능
				
			}, function(response){
				$('#useItemUserCode').val("N");
				alert(Message.get("M01088"));	// 사용불가
				$('#itemUserCode').val("").focus();
				
			});
			
		}, 'json');

	});

	// 상품명 복사
	$('.copy_item_name').on('click', function() {
		$(this).prev().val($('#itemName').val());
	});

	// 네이버 쇼핑 사용 여부
	$('input[name="naverShoppingFlag"]').on('click', function () {
		if ($(this).val() == 'Y') {
			$('#naverShoppingItemName').attr('disabled', false);
		} else {
			$('#naverShoppingItemName').val('');
			$('#naverShoppingItemName').attr('disabled', true);
		}
	});

	// 폼체크
	$("#item").validator({
		'submitHandler' : function() {
            var type = $("input[name='spotDateType']:checked").val();

            if (type == '2') {
                $("#spotStartDate").val($("#spotStartDateTwo").val());
                $("#spotEndDate").val($("#spotEndDateTwo").val());
                $("#spotStartTime").val($("#spotStartHourTwo").val() + "" + $("#spotStartMinuteTwo").val() + "00");
                $("#spotEndTime").val($("#spotEndHourTwo").val() + "" + $("#spotEndMinuteTwo").val() + "59");
            } else {
                $("#spotStartDate").val($("#spotStartDateOne").val());
                $("#spotEndDate").val($("#spotEndDateOne").val());
                $("#spotStartTime").val($("#spotStartHourOne").val() + "" + $("#spotStartMinuteOne").val() + "00");
                $("#spotEndTime").val($("#spotEndHourOne").val() + "" + $("#spotEndMinuteOne").val() + "59");
            }

            if ($("input[name='spotFlag']:checked").val() == 'Y') {
                if ($("#spotStartDate").val().length != 8 || $("#spotEndDate").val().length != 8) {
                    alert("스팟 기간을 정확히 입력해주세요.");
                    return false;
				}
			}

			if ($('input[name=categoryIds]').size() == 0) {
				alert(Message.get("M00078"));	// 상품 카테고리를 선택해 주세요.
				$('#categoryGroupId').focus();
				return false;
			}

			if ($('#itemUserCode').size() > 0 && $('#useItemUserCode').val() != "Y") {
				alert(Message.get("M00379"));		// 상품코드 중복 여부가 체크되지 않았습니다.
				$('#itemUserCode').focus();
				return false;

			}

			/* 비회원 가격
			var $salePriceNonmember = $('#salePriceNonmember');
			if ($('input[name=nonmemberOrderType]:checked').val() == '1' &&
					($.trim($salePriceNonmember.val()) == '' || $salePriceNonmember.val() == '0')) {
				$.validator.validatorAlert($.validator.messages['text'].format($salePriceNonmember.attr('title')), $salePriceNonmember);
				$salePriceNonmember.focus();
				return false;
			}*/

			var commissionType = $('input[name=commissionType]:checked').val();
			if (commissionType == '3') {     // 공급가인 경우
				var $supplyPrice = $('#supplyPrice');
				var supplyPrice = $.trim($supplyPrice.val());
				if (supplyPrice == '' || supplyPrice == 0) {
						alert('공급가를 입력해 주세요.');
						$supplyPrice.focus();
						return false;
					}
			}

			// 할인 가능 금액 체크
			// 할인 가능금액 100 - 수수료율 - 30 까지만 가능.
			Common.removeNumberComma();

			var salePrice = Number($('#salePrice').val());
			var discountLimitRate = 100 - (Number($('#commissionRate').val()) + 30);

			var sellerDiscountAmount = 0;
			if ($('input[name=sellerDiscountFlag]:checked').val() == 'Y') {
				sellerDiscountAmount = Number($('#sellerDiscountAmount').val());
			}

			var spotDiscountAmount = 0;
			if ($('input[name=spotFlag]:checked').val() == 'Y') {
				spotDiscountAmount = Number($('#spotDiscountAmount').val());

				var spotWeekDay = '';
				$('input[name=day_of_week]:checked').each(function() {
					spotWeekDay += '' + $(this).val();
				});
				$('#spotWeekDay').val(spotWeekDay);

				/*var $spotStartTime = $('#spotStartTime');
				var $spotEndTime = $('#spotEndTime');*/

				// 기본값 설정
				/*var spotStartTime = $('#spotStartHour').val() + "" + $('#spotStartMinute').val() + "00";
				var spotEndTime = $('#spotEndHour').val() + "" + $('#spotEndMinute').val() + "59";
				$spotStartTime.val(spotStartTime);
				$spotEndTime.val(spotEndTime);*/
			}

			var discountRate = Math.round((sellerDiscountAmount + spotDiscountAmount) * 100 / salePrice);
			if (discountRate > discountLimitRate) {
				alert('할인 가능 금액은 판매금액의 ' + discountLimitRate + '% 까지만 설정이 가능합니다.\n판매금액 및 할인금액을 확인해 주세요.');
				Common.addNumberComma();
				$('#spotDiscountAmount').focus();
				return false;
			}

			// 최소/최대 구매수량
			var orderMinQuantity = $.trim($('#orderMinQuantity').val());
			var orderMaxQuantity = $.trim($('#orderMaxQuantity').val());
			if (orderMinQuantity != '' && orderMaxQuantity != ''
				&& Number(orderMinQuantity) > 0 && Number(orderMaxQuantity) > 0) {

				if (Number(orderMinQuantity) > Number(orderMaxQuantity)) {
					alert(Message.get("M00699"));	// 최대 구매 수량을 최소 구매 수량 보다 큰 값으로 입력해 주세요.
					$('#orderMaxQuantity').focus();
					return false;
				}
			}

			// 포인트 처리
			$('#item_point_area tr').each(function(){
				var pointType = $(this).find('input[type=radio]').eq(0).prop('checked') == true ? "1" : "2";
				$(this).find('input[name=pointType]').val(pointType);
			});

			if ($('input[name=sellerPointFlag]:checked').val() == 'Y') {
				if($("select[name=pointType]").val() == '1'){
					if($('input[name=point]').val() > 100){
						alert("${op:message('M00246')} 지급시 100%이상으로는 지급할 수 없습니다. 다시 입력하여 주십시요.");
						$('input[name=point]').focus();
						return false;
					}
				}

			}

			// 상품 옵션인 경우 본 상품 재고 관리는 무제한으로 변경.
			var itemOptionFlag = $('input[name=itemOptionFlag]:checked').val();
			var itemOptionType = $('input[name=itemOptionType]:checked').val();
			if (itemOptionFlag == 'Y' && itemOptionType != 'T') {
				$('input[name=stockFlag]').eq(0).prop('checked', true);
				$('#trStockQuantity').find('input[name="stockQuantity"]').attr('disabled', true);
				$('#stockQuantity').val('');
				setValidatorCondition();

				// 옵션조합형 옵션
				if (itemOptionType == 'C') {
					var isDuplicate = false;
					var optionTitles = [];

					$('.combination-option-wrapper').each(function() {
						var $itemOptionTitle = $(this).find('.item-combination-option-title');
						var $itemOptions = $(this).find('.item-combination-options tr');

						var title = $itemOptionTitle.find('input:text').val();
						var displayType = $itemOptionTitle.find('input:radio:checked').val();

						// 1. 옵션그룹 필수값 세팅 (옵션명, 옵션타입)
						$itemOptions.find('input[name="optionName1"]').val(title);
						$itemOptions.find('input[name="optionDisplayType"]').val(displayType);

						// 2. 옵션명 체크
						if (optionTitles.includes(title)) {
							alert('옵션 그룹명 "' + title + '" 중복입니다. 옵션 구성을 변경해주세요.');
							isDuplicate = true;
							return;
						} else {
							optionTitles.push($(this).find('input:text').val());
						}

						// 3. 옵션타입 체크 (기본 타입일 경우, 옵션값 1개 고정)
						if (displayType == 'fixing') {
							$itemOptions.not(':eq(0)').remove();
						}

						// 4. 각 옵션 순서 부여
						var ordering = 0;
						$itemOptions.each(function() {
							$(this).find('input[name="optionOrdering"]').val(ordering);
							ordering++;
						});
					});

					if (isDuplicate) {
						return false;
					}
				}
			}

			$("#item-options #option-type-table").val(itemOptionType);
			$("#item-text-options #option-text-table").val('T');

			// 배송비 설정
			var $shippingType = $('input[name=shippingType]:checked');
			var $shippingInfo = $shippingType.closest('tr');
			var shipping = $shippingInfo.find('.opt-shipping').val();
			var shippingFreeAmount = $shippingInfo.find('.opt-shipping-free-amount').val();

			$('#shipping').val(shipping);
			$('#shippingFreeAmount').val(shippingFreeAmount);

			// 관련상품
			var relationItemDisplayType = $('input[name=relationItemDisplayType]:checked').val();
			if (relationItemDisplayType == 2) {
				if ($('input[name=relatedItemIds]').size() == 0) {
					alert(Message.get("M00080"));	// 관련상품을 추가해 주세요.
					$('#button_add_relation_item').focus();
					return false;
				}
			}

			// 배송비 기준이 출고지조건부(3)이 아닌 경우 shipmentGroupCode 초기화
			if ($shippingType.val() != '3') {
				$('#shipmentGroupCode').val('');
			}


			if (!confirm('상품 정보를 저장하시겠습니까?')) {
				return false;
			}


			// 에디터 내용 검증 (내용 입력 여부 / 필터) - 필수 아님?
			//if ($.validator.validateEditor(editors, "detailContent") == false) return false;

			// 에디터 내용 설정.
			Common.getEditorContent("detailContent");
			Common.getEditorContent("detailContentMobile");
			//Common.getEditorContent("useManual");
			//Common.getEditorContent("makeManual");

			//if ($.trim($('#useManual').val()).toLowerCase() == '<p>&nbsp;</p>') {
			//	$('#useManual').val('');
			//}

			//if ($.trim($('#makeManual').val()).toLowerCase() == '<p>&nbsp;</p>') {
			//	$('#makeManual').val('');
			//}

			Common.removeNumberComma();

			$(".spotFlag").removeAttr("disabled");
		}
	});

	var restockNoticeCount = '${restockNoticeCount}';
	if (restockNoticeCount > 0) {
	    $('.restock-button').removeClass('hide');
    } else {
        $('.restock-text').removeClass('hide');
    }
	
	$( window ).scroll(function() {
		setHeight();
	});

	// 추가구성상품 파일 이벤트
	$('input[name="additionImageFile"]').on('change', function() {
		var file = $(this).val();
		var fileText = file.substring(file.lastIndexOf('\\') + 1, file.length);

		$(this).closest('td').find('ul.item_addition_image_main').remove();
		$(this).closest('p').prepend('<p>' + fileText + '</p></br>');
	});
});


// 비회원 판매가격 이벤트 핸들러.
function initNonmemberPriceEvent() {
	showHideContent($('input[name=salePriceNonmemberFlag]:checked'));
	$('input[name=salePriceNonmemberFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
}

// 사은품 이벤트 핸들러.
function initFreeGiftEvent() {
	showHideContent($('input[name=freeGiftFlag]:checked'));
	$('input[name=freeGiftFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
}


// 상품 수수료 설정 이벤트 핸들러.
function initCommissionEvent() {
	setCommissionRate();
	
	$('input[name=commissionType]').on('click', function() {
		setCommissionRate();	
	});
	
	function setCommissionRate() {
		var commissionType = $('input[name=commissionType]:checked').val();
		var $commissionRate = $('#commissionRate');
        var $supplyPrice = $('#supplyPrice');

		// 입점업체 수수료 적용.
		if (commissionType == '1') {
			$commissionRate.val($('#sellerCommissionRate').val());
			$commissionRate.prop('readonly', true);
            $supplyPrice.prop('readonly', true);

		} else if (commissionType == '2') {
			//$commissionRate.val(0);
			$commissionRate.prop('readonly', false);
            $supplyPrice.prop('readonly', true);

		} else if (commissionType == '3') {     // 공급가 기준
            //$commissionRate.val(0);
            $commissionRate.prop('readonly', true);

            if (${requestContext.sellerPage}) {
                $supplyPrice.prop('readonly', true);
			} else {
	            $supplyPrice.prop('readonly', false);
            }

        } else {
			return;
		}
	}
}
	

// 상품 옵션 이벤트 핸들러.
function initItemOptionEvent() {

	// 상품옵션
	showHideItemOption();

	$('input[name=itemOptionFlag]').on('click', function() {
		if ($(this).val() == 'N') {
			if (!confirm('상품옵션을 사용안함으로 설정하는 경우 현재 설정된 옵션 설정이 삭제됩니다. 변경하시겠습니까?')) {
				return false;
			}
		}
		showHideItemOption();
		setValidatorCondition();
        clearItemOptions();
	});
	
	// 옵션 타입 선택 이벤트.
	$('input[name=itemOptionType]').on('click', function() {
		if (!confirm('옵션 형태를 변경하는 경우 현재 설정된 옵션 설정이 삭제됩니다. 옵션타입을 변경하시겠습니까?')) {
			return false;
		}
		//$('.option-step.step2').hide();
		$('.option-type').hide();
		
		clearItemOptions();
		showHideItemOption();
		setValidatorCondition();
	});
	
	// S옵션 입력 도우미 - 항목추가.
	$('.add-option-input').on('click', function() {
		var addHtml = $('#s-option-input-wrap').find('tr').eq(0).parentHtml();
		$('#s-option-input-wrap').append(addHtml);
	});
	
	// S옵션 입력 도우미 - 항목삭제.
	$('#s-option-input-wrap').on('click', ' .delete_option_input', function(e) {
		e.preventDefault();
		if ($('#s-option-input-wrap tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input').val('');
		}
	});

	// 옵션 적용 (옵션 생성)
	$('.make-item-option').on('click', function(e) {
		e.preventDefault();
		
		var optionType = $('input[name=itemOptionType]:checked').val();
		
		if (optionType == 'S') {
			// 1. 입력 검증.
			var $target = $('#s-option-input-wrap');
			var errorCount = validOptionInput($target);
			
			if (errorCount > 0) {
				return;
			}

			// 2. 옵션 값 생성.
			var options = [];
			var optionIndex = 0;
			$target.find('tr').each(function() {
				var option1 = $.trim($(this).find('input').eq(0).val());
				var option2 = $.trim($(this).find('input').eq(1).val());
				
				var optArray = option2.split(',');
				
				for (var i = 0; i < optArray.length; i++) {
					var opt2 = $.trim(optArray[i]);
					if (opt2 != '') {
						options[optionIndex] = {'optionType': 'S', 'optionName1': option1, 'optionName2': opt2, 'optionName3': ''};
						optionIndex++;
					}
				}
			});
			
			// 3. 옵션 적용.
			makeItemOptions(options);
		
		} else if (optionType == 'S2') {
			// 1. 입력 검증.
			var $target = $('#s2-option-input-wrap');
			var errorCount = validOptionInput($target);
			
			if (errorCount > 0) {
				return;
			}
			
			// 2. 옵션 값 생성.
			var options = [];
			var optionIndex = 0;
			
			var optionTitle1 = $.trim($target.find('tr').eq(0).find('input').eq(0).val());
			var optionTitle2 = $.trim($target.find('tr').eq(1).find('input').eq(0).val());
			
			var option1 = $.trim($target.find('tr').eq(0).find('input').eq(1).val());
			var option2 = $.trim($target.find('tr').eq(1).find('input').eq(1).val());
				
			var opt1Array = option1.split(',');
			var opt2Array = option2.split(',');
				
			for (var i = 0; i < opt1Array.length; i++) {
				for (var j = 0; j < opt2Array.length; j++) {
					var opt1 = $.trim(opt1Array[i]);
					var opt2 = $.trim(opt2Array[j]);
					
					if (opt1 != '' && opt2 != '') {
						options[optionIndex] = {'optionType': 'S2', 'optionName1': opt1, 'optionName2': opt2, 'optionName3': ''};
						optionIndex++;
					}
				}
			}
			
			$('#itemOptionTitle1').val(optionTitle1);
			$('#itemOptionTitle2').val(optionTitle2);
			
			// 3. 옵션 적용.
			makeItemOptions(options);
			
		} else if (optionType == 'S3') {
			// 1. 입력 검증.
			var $target = $('#s3-option-input-wrap');
			var errorCount = validOptionInput($target);
			
			if (errorCount > 0) {
				return;
			}
			
			// 2. 옵션 값 생성.
			var options = [];
			var optionIndex = 0;
			
			var optionTitle1 = $.trim($target.find('tr').eq(0).find('input').eq(0).val());
			var optionTitle2 = $.trim($target.find('tr').eq(1).find('input').eq(0).val());
			var optionTitle3 = $.trim($target.find('tr').eq(2).find('input').eq(0).val());
			
			var option1 = $.trim($target.find('tr').eq(0).find('input').eq(1).val());
			var option2 = $.trim($target.find('tr').eq(1).find('input').eq(1).val());
			var option3 = $.trim($target.find('tr').eq(2).find('input').eq(1).val());
				
			var opt1Array = option1.split(',');
			var opt2Array = option2.split(',');
			var opt3Array = option3.split(',');
				
			for (var i = 0; i < opt1Array.length; i++) {
				for (var j = 0; j < opt2Array.length; j++) {
					for (var k = 0; k < opt3Array.length; k++) {
						var opt1 = $.trim(opt1Array[i]);
						var opt2 = $.trim(opt2Array[j]);
						var opt3 = $.trim(opt3Array[k]);
						
						if (opt1 != '' && opt2 != '' && opt3 != '') {
							options[optionIndex] = {'optionType': 'S3', 'optionName1': opt1, 'optionName2': opt2, 'optionName3': opt3};
							optionIndex++;
						}
					}
				}
			}
			
			$('#itemOptionTitle1').val(optionTitle1);
			$('#itemOptionTitle2').val(optionTitle2);
			$('#itemOptionTitle3').val(optionTitle3);

			// 3. 옵션 적용.
			makeItemOptions(options);
		}

	});
	
	// 상품옵션 - 재고 연동 여부 변경 시 
	$('#item-options').on('change', 'select[name=optionStockFlag]', function() {
		var optionStockFlag = $(this).val();
		var $optionStockQuantity = $(this).closest('tr').find('input[name=optionStockQuantity]');
		if (optionStockFlag == 'Y') {
			$optionStockQuantity.prop('readonly', false).addClass('required-item-option');
			
		} else {
			$optionStockQuantity.val('').prop('readonly', true).removeClass('required-item-option');
		
		}
	});
	
	// 상품옵션 - 옵션추가
	$('.add-item-option').on('click', function() {
		var $templateOption = $('#item-options tr').eq(0).find('input[name=optionStockQuantity]');
		var isReadonly = $templateOption.prop('readonly');
		$templateOption.prop('readonly', true);

		var optionHtml = $('#item-options tr').eq(0).parentHtml();
		$('#item-options').append(optionHtml);

		$templateOption.prop('readonly', isReadonly);
				
		var $newItem = $('#item-options tr:last-child');
		resetItemOption($newItem);
	});
	
	// 상품옵션 - 옵션삭제
	$('#item-options').on('click', '.delete-item-option', function(e) {
		e.preventDefault();
		
		if ($('#item-options tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			resetItemOption($(this).closest('tr'));
		}
	});
	
	// 상품 텍스트 옵션 - 옵션추가
	$('.add-item-text-option').on('click', function() {
		var optionHtml = $('#item-text-options tr').eq(0).parentHtml();
		$('#item-text-options').append(optionHtml);
		
		var $newItem = $('#item-text-options tr:last-child');
		resetTextOption($newItem, 'add');
	});
	
	// 상품 텍스트 옵션 - 옵션삭제
	$('#item-text-options').on('click', '.delete-item-text-option', function(e) {
		e.preventDefault();
		resetTextOption($(this));
	});

	// 상품 옵션조합형 옵션 - 그룹추가
	$('.add-combination-option-group').on('click', function() {
		var itemOptionWrapper = '.combination-option-wrapper';
		var index = Number($(itemOptionWrapper).last().data('index')) + 1;

		var itemOptionTitle =
				'<div class="item-combination-option-title">' +
					'<input type="text" name="op-option-name" class="required-item-option-combination" title="옵션명" value="" maxlength="200">' +
						'<span class="check-box">' +
							'<input type="radio" id="op-option-display-type1-' + index + '" name="op-option-display-type' + index + '" class="required-item-option-combination" title="구분" value="fixing" checked="checked"> <label for="op-option-display-type1-' + index + '">기본</label> ' +
							'<input type="radio" id="op-option-display-type2-' + index + '" name="op-option-display-type' + index + '" class="required-item-option-combination" title="구분" value="select"> <label for="op-option-display-type2-' + index + '">옵션</label>' +
						'</span> ' +
					'<button type="button" class="btn btn-dark-gray btn-sm remove-combination-option-group">- 그룹삭제</button>' +
				'</div>';

		var $itemOptionTable = $(itemOptionWrapper).last().find('.item-combination-option-contents table');

		var itemOptionInfo = '<div class="item-options-info">' +
				'<button type="button" class="btn btn-dark-gray btn-sm" onclick="Shop.findItem(\'itemOption' + index + '\')"><span class="glyphicon glyphicon-search"></span> 조회</button> ' +
				'<button type="button" class="btn btn-gradient btn-sm add-item-combination-option"><span>+ 옵션추가</span></button>' +
			'</div>';

		// 추가
		var addHtml = '<div id="itemOption' + index + '" class="combination-option-wrapper" data-index="' + index + '">' +
				itemOptionTitle + '<div class="item-combination-option-contents">' + $itemOptionTable.parentHtml() + itemOptionInfo + '</div>' +
			'</div>';

		$(itemOptionWrapper).last().after(addHtml);
		$(itemOptionWrapper).last().find('.item-combination-options tr').not(':eq(0)').remove();

		var $newItems = $(itemOptionWrapper).last().find('.item-combination-options tr');
		resetCombinationOption($newItems);

		// 옵션조합형 드레그
		$(".sortable_item_combination_option").last().sortable({
			placeholder: "sortable_item_combination_option_placeholder"
		});
		$(".sortable_item_combination_option").last().disableSelection();
	});

	// 상품 옵션조합형 옵션 - 그룹삭제
	$('.option-type.type-C').on('click', '.remove-combination-option-group', function() {
		var $itemOptionWrapper = $(this).closest('.combination-option-wrapper');

		if ($('.combination-option-wrapper').size() > 1) {
			$itemOptionWrapper.remove();
		} else {
			var $itemOptionTitle = $(this).closest('.item-combination-option-title');
			var $itemOptions = $itemOptionWrapper.find('.item-combination-options tr');

			$itemOptionTitle.find('input:text').val('');
			$itemOptionTitle.find('input:radio[value="fixing"]').prop('checked', true);
			$itemOptions.not(':eq(0)').remove();

			resetCombinationOption($itemOptions);
		}
	});

	// 상품 옵션조합형 옵션 - 그룹명 입력
	$('.option-type.type-C').on('blur', '.item-combination-option-title input:text', function() {
		var $itemOptionWrapper = $(this).closest('.combination-option-wrapper');
		var $itemOptions = $itemOptionWrapper.find('.item-combination-options tr');

		$itemOptions.find('input[name=optionName1]').val($(this).val());
	});

	// 상품 옵션조합형 옵션 - 그룹 옵션타입 변경
	$('.option-type.type-C').on('change', '.item-combination-option-title input:radio', function() {
		var $itemOptionWrapper = $(this).closest('.combination-option-wrapper');
		var $itemOptionTitle = $itemOptionWrapper.find('.item-combination-option-title');
		var $itemOptions = $itemOptionWrapper.find('.item-combination-options tr');

		if ($(this).val() == 'fixing' && $itemOptions.size() > 1) {
			if (confirm('옵션타입을 기본으로 변경하는 경우 현재 설정된 옵션 설정이 삭제됩니다. 옵션타입을 변경하시겠습니까?')) {
				$itemOptions.not(':eq(0)').remove();
				resetCombinationOption($itemOptions, $itemOptionTitle.find('input:text').val());
			} else {
				$(this).prop('checked', false);
				$itemOptionWrapper.find('input:radio[value="select"]').prop('checked', true);
			}
		}

		$itemOptions.find('input[name="optionDisplayType"]').val($itemOptionWrapper.find('input:radio:checked').val());
	});

	// 상품 옵션조합형 옵션 - 재고연동 여부 변경 시
	$('.option-type.type-C').on('change', '.item-combination-options select[name="optionStockFlag"]', function() {
		var optionStockFlag = $(this).val();
		var $optionStockQuantity = $(this).closest('tr').find('input[name=optionStockQuantity]');

		if (optionStockFlag == 'Y') {
			$optionStockQuantity.prop('readonly', false).addClass('required-item-option-combination');
		} else {
			$optionStockQuantity.val('').prop('readonly', true).removeClass('required-item-option-combination');
		}
	});

	// 상품 옵션조합형 옵션 - 옵션추가
	$('.option-type.type-C').on('click', '.add-item-combination-option', function() {
		var $itemOptionWrapper = $(this).closest('.combination-option-wrapper');
		var $itemOptions = $itemOptionWrapper.find('.item-combination-options');

		var optionName1 = $itemOptionWrapper.find('.item-combination-option-title input:text').val();
		var optionDisplayType = $itemOptionWrapper.find('.item-combination-option-title input:radio:checked').val();

		// 기본 타입일경우 옵션 추가 불가
		if (optionDisplayType == 'fixing') {
			alert('옵션 타입이 기본인 경우, 옵션은 1개만 등록 가능합니다.');
			return false;
		}

		$itemOptions.append($itemOptions.find('tr').eq(0).parentHtml());

		var $newItem = $itemOptions.find('tr:last-child');
		resetCombinationOption($newItem, optionName1, optionDisplayType);
	});

	// 상품 옵션조합형 옵션 - 옵션삭제
	$('.option-type.type-C').on('click', '.item-combination-options .delete-item-combination-option', function(e) {
		e.preventDefault();

		var $itemOptions = $(this).closest('.item-combination-options');

		if ($itemOptions.find('tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			var optionName1 = $(this).closest('.combination-option-wrapper').find('.item-combination-option-title input:text').val();
			resetCombinationOption($(this).closest('tr'), optionName1);
		}
	});

	function showHideItemOption() {
		var itemOptionFlag = $('input[name=itemOptionFlag]:checked').val();
		var itemOptionType = $('input[name=itemOptionType]:checked').val();

		$('.option-step.step1').hide();
		$('.option-step.step2').hide();
		$('.option-type').hide();
		$('.option-S3').hide();
		$('.option-S3').find('input[name=optionName3]').removeClass('required-item-option');
		
		if (itemOptionFlag == 'N' || itemOptionType === null) {
			return;
		}

		$('.option-step.step1').show();

		if (itemOptionType == 'S') {
			$('#itemOptionTitle1').val('옵션명');
			$('#itemOptionTitle2').val('옵션값');
			$('.item-option-table').show();
		} else if (itemOptionType == 'S2') {
			$('.item-option-table').show();
		} else if (itemOptionType == 'S3') {
			$('.item-option-table').show();
			$('.option-S3').show();
			$('.option-S3').find('input[name=optionName3]').addClass('required-item-option');
		} else if (itemOptionType == 'T') {
			$('.option-step.step2').show();
			$('.item-option-table').hide();
			$('.option-type.type-T').show();
		} else if (itemOptionType == 'C') {
			$('.item-option-table').hide();
		}
		
		if (itemOptionType != 'T') {
			$('.option-step.step2').show();
			$('.option-type.type-' + itemOptionType).show();
		}
		
		// 옵션 상품 재고 수량 / readonly
		$('select[name=optionStockFlag]').each(function() {
			var $erpItemCode = $(this).closest('tr').find('input[name=erpItemCode]');

			if ($erpItemCode.val() == '') {
				var optionStockFlag = $(this).val();
				var $optionStockQuantity = $(this).closest('tr').find('input[name=optionStockQuantity]');
				if (optionStockFlag == 'Y') {
					$optionStockQuantity.prop('readonly', false).addClass('required-item-option');
				} else {
					$optionStockQuantity.val('').prop('readonly', true).removeClass('required-item-option');
				}
			}
		});
	}
	// 옵션 입력 도우미 - 입력 검증.
	function validOptionInput($target) {
		var errorCount = 0;
			
		$target.find('input').each(function(i) {
			var optionValue = $.trim($(this).val());
			var optiotTitle = $(this).attr('title');
			
			if (optionValue == '') {
				errorCount++;
				alert(optiotTitle + '을 입력해 주세요.');
				$(this).focus();
				return false;
			}
		});
		
		return errorCount;
	}
	
	// 상품 옵션 clear {
	function clearItemOptions() {
		// S, S2, S3
		var optionHtml = $('#item-options tr').eq(0).parentHtml();
		var $itemOptions = $('#item-options');
		
		$itemOptions.find('tr').remove();
		$itemOptions.append(optionHtml);
		$('#itemOptionTitle1, #itemOptionTitle2, #itemOptionTitle3').val('');

		resetItemOption($itemOptions);

		// T
		$('#item-text-options tr').each(function(){
			resetTextOption($(this));
		});

		// C
		var $combinationOptionWrapper = $('.combination-option-wrapper');
		var $combinationOptionTitle = $('.item-combination-option-title');
		var $combinationOptions = $('.item-combination-options');

		var combinationOptionTitleHtml =
			'<div class="item-combination-option-title">' +
				'<input type="text" name="op-option-name" class="required-item-option-combination" title="옵션명" value="" maxlength="200">' +
				'<span class="check-box">' +
					'<input type="radio" id="op-option-display-type1-0" name="op-option-display-type0" class="required-item-option-combination" title="구분" value="fixing" checked="checked"> <label for="op-option-display-type1-0">기본</label> ' +
					'<input type="radio" id="op-option-display-type2-0" name="op-option-display-type0" class="required-item-option-combination" title="구분" value="select"> <label for="op-option-display-type2-0">옵션</label>' +
				'</span> ' +
				'<button type="button" class="btn btn-dark-gray btn-sm remove-combination-option-group">- 그룹삭제</button>' +
			'</div>';
		var combinationOptionHtml = $combinationOptionWrapper.eq(0).find('.item-combination-options tr').eq(0).parentHtml();

		$combinationOptionWrapper.not(':eq(0)').remove();
		$combinationOptionTitle.html(combinationOptionTitleHtml);
		$combinationOptions.find('tr').remove();
		$combinationOptions.append(combinationOptionHtml);

		resetCombinationOption($combinationOptions);
	}

	// 상품 옵션 생성.
	function makeItemOptions(options) {
		//$('#item-options tr').eq(0).find('input[name=optionStockQuantity]').prop('readonly', true);
		var optionHtml = $('#item-options tr').eq(0).parentHtml();
		
		var makeHtml = '';
		
		for (var i = 0; i < options.length; i++) {
			makeHtml += optionHtml;
		}
		
		var $itemOptions = $('#item-options');
		$itemOptions.find('tr').remove();
		$itemOptions.append(makeHtml);

		for (var i = 0; i < options.length; i++) {
			var $tr = $itemOptions.find('tr').eq(i);
			$tr.find('input[type=text]').val('');
			
			resetItemOption($tr);
			
			$tr.find('input[name=optionType]').val(options[i].optionType);
			$tr.find('input[name=optionName1]').val(options[i].optionName1);
			$tr.find('input[name=optionName2]').val(options[i].optionName2);
			$tr.find('input[name=optionName3]').val(options[i].optionName3);
		}
	}

	// 옵션 클리어
	function resetItemOption($target) {
		$target.find('input').val('');
		$target.find('input[name="optionStockQuantity"]').prop('readonly', true).removeClass('required-item-option');
		$target.find('select[name="optionStockFlag"]').val('N');
		$target.find('select[name="optionSoldOutFlag"]').val('N');
		$target.find('select[name="optionDisplayFlag"]').val('Y');

		$target.find('input[name="optionType"]').val($('input[name=itemOptionType]:checked').val());
		$target.find('input[name="optionDisplayType"]').val('select');
	}

	// 텍스트 옵션 클리어
	function resetTextOption($selector, type) {
		if ($('#item-text-options tr').size() > 1 && type != 'add') {
			$selector.closest('tr').remove();
		} else {
			$selector.closest('tr').find('input').val('');
			$selector.closest('tr').find('select').each(function() {
				$selector.find('option:eq(0)').prop('selected', true);
			});
			$selector.closest('tr').find('input[name="optionType"]').val('T');
			$selector.closest('tr').find('input[name="optionDisplayType"]').val('text');
		}
	}

	// 옵션조합형 옵션 클리어
	function resetCombinationOption($selector, optionName1, optionDisplayType) {
		$selector.find('input').prop('readonly', false).val('');
		$selector.find('input[name="optionPrice"]').prop('readonly', true);
		$selector.find('input[name="optionStockCode"]').prop('readonly', true);
		$selector.find('input[name="optionStockQuantity"]').prop('readonly', true).removeClass('required-item-option-combination');

		$selector.find('select').find('option').prop('disabled', false);
		$selector.find('select').each(function() {
			$(this).find('option:eq(0)').prop('selected', true);
		});

		$selector.find('input[name="optionType"]').val('C');
		$selector.find('input[name="optionName1"]').val(optionName1);
		$selector.find('input[name="optionDisplayType"]').val(optionDisplayType == null ? 'fixing' : 'select');

		$selector.find('input[name="optionQuantity"]').val(1);
	}
}

// 옵션추가
function addItemOption() {
	var optionTemplate = $('#item_option_template tbody').html();
	var randomKey = Math.floor(Math.random() * 10000) + 1;

	var radioButtons = '';

	radioButtons += '<label><input type="radio" name="optionDisplayTypeRadio' + randomKey + '" value="radio" class="option_display_type" checked="checked" /> 라디오버튼</label>';
	radioButtons += '<label><input type="radio" name="optionDisplayTypeRadio' + randomKey + '" value="select" class="option_display_type" /> 셀렉트박스</label>';


	$('#option_data').hide();
	$('#item_option_area tbody').append(optionTemplate.replace('{INPUT_RADIO}', radioButtons));
}

// 추가 상품 이벤트 핸들러.
function initItemAdditionEvent() {
	
	setItemAddition();
	
	// 추가 구성 사용 여부 클릭 이벤트.
	$('input[name=itemAdditionFlag]').on('click', function() {
		setItemAddition();
	});
	
	// 추가구성 상품 - 상품추가
	$('.add-item-addition').on('click', function() {
		var $itemAdditions = $('#item-additions');
	
		var optionHtml = $itemAdditions.find('tr').eq(0).parentHtml();
		$itemAdditions.append(optionHtml);
		
		
		var $newItem = $itemAdditions.find('tr:last-child');
		$newItem.find('input').val('');
		$newItem.find('input[name=additionStockQuantity]').prop('readonly', true).removeClass('required-item-addition');
		$newItem.find('select').each(function() {
			$(this).find('option:eq(0)').prop('selected', true);
		});
		
	});
	
	// 추가구성 상품 - 상품삭제
	$('#item-additions').on('click', '.delete-item-addition', function(e) {
		e.preventDefault();
		
		if ($('#item-additions tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input').val('');
			$(this).closest('tr').find('select').each(function() {
				$(this).find('option:eq(0)').prop('selected', true);
			});
		}
	});
	
	// 재고 연동여부 readonly
	$('#item-additions').on('change', 'select[name=additionStockFlag]', function(e) {
		handleAdditionStockFlagEvent($(this));
	});
	
	
	// 재고 연동 여부에 따른 재고수량 / readonly 
	function handleAdditionStockFlagEvent($target) {
		var stockFlag = $target.val();
		var $stockQuantity = $target.closest('tr').find('input[name=additionStockQuantity]');
		if (stockFlag == 'Y') {
			$stockQuantity.prop('readonly', false).addClass('required-item-addition');
			
		} else {
			$stockQuantity.val('').prop('readonly', true).removeClass('required-item-addition');
		
		}
	}
	
	// 추가 구성 상품 init
	function setItemAddition() {
		var itemAdditionFlag = $('input[name=itemAdditionFlag]:checked').val();
		var $itemAdditionWrap = $('.item-addition-wrap');
		
		if (itemAdditionFlag == 'Y') {
			$itemAdditionWrap.show();
		} else {
			$itemAdditionWrap.hide();
		}
		
		// 옵션 상품 재고 수량 / readonly
		$('select[name=additionStockFlag]').each(function() {
			handleAdditionStockFlagEvent($(this));
		});
		
		setValidatorCondition();
	}
}

// 배송 정보 설정 이벤트 핸들러.
function initShippingEvent() {
	
	// 배송구분 설정 
	setDeliveryType();
	
	// 배송비 종류 선택 설정.
	setShippingType();
	
	// 배송비 종류 노출 설정.
	showHideShippingType();
	
	// 공급사 선택 이벤트.
	$('#sellerId').on('change', function() {
		
		// 배송구분 설정 
		setDeliveryType();
	
		// 판매자 정보 / 출고지 / 반송지 정보 조회 
		setShipmentInformation();
		

		// 반품/교환 종류 초기화
		clearShipmentReturnInfo();
		

	});
	
	
	// 배송구분 선택 이벤트
	$('#deliveryType').on('change', function() {
		// 배송비 종류 초기화
		setShipmentInformation();
	});
	
	// 배송구분 선택 이벤트
	$('#shipmentReturnType').on('change', function() {
		// 반품/교환 종류 초기화
		clearShipmentReturnInfo();
	});
	
	
	// 배송구분 설정 
	function setDeliveryType() {
		if (isHqSeller()) {
			$('#deliveryType option').eq(2).prop('disabled', true);		// 업체 배송 disabled
			$('#shipmentReturnType option').eq(1).prop('disabled', false);		// 본사반품 O
			$('#shipmentReturnType option').eq(2).prop('disabled', true);		// 업체 배송 disabled
			$('#deliveryType').val('1'); // 본사배송
			$('#shipmentReturnType').val('1'); // 업체반품
			
			
		} else {
			$('#deliveryType option').eq(2).prop('disabled', false);
			$('#shipmentReturnType option').eq(1).prop('disabled', true);		// 본사반품 X
			$('#shipmentReturnType option').eq(2).prop('disabled', false);
			$('#deliveryType').val('2'); // 업체배송
			$('#shipmentReturnType').val('2'); // 업체반품
					
		}
	}
	
	function isHqSeller() {
		var isHqSeller = false;
		if ($('#sellerId').data('isHqSeller') == undefined) {
			isHqSeller = $('#sellerId option:selected').data('isHqSeller') == 'Y' ? true : false;
		} else {
			isHqSeller = $('#sellerId').data('isHqSeller') == 'Y' ? true : false;
		}
		return isHqSeller;
	}
	
	
	// 배송정보 설정.
	function setShipmentInformation() {
	
		var deliveryType = $('#deliveryType').val();
		var sellerId = $('#sellerId').val();
		
		
		// 1. 배송비 종류 노출 설정.
		showHideShippingType();
	
		// 2. 배송비 설정 정보 초기화
		$('.shipping-option input[name=shippingType]').prop('checked', false);
		$('#shipmentGroupCode').val('');
		
		clearShipmentInfo();
		clearShipmentReturnInfo();

		if (sellerId == '' || (sellerId == '' && deliveryType == '2')) { // 업체배송인데 공급사가 없는 경우 
			clearSellerInfo();
			return;
		} 
		
		if (deliveryType == '1') {
			sellerId = '${shopContext.hqSellerId}';
		}

		var url = '${isSellerPage?"/seller":"/opmanager"}/item/seller-info/' + sellerId;
		$.post(url, {}, function(response) {
			Common.responseHandler(response, function(response) {
				var shipment = response.data.shipment;
				var shipmentReturn = response.data.shipmentReturn;
				var seller = response.data.seller;
				
				if (seller != null) {
					//$('#vnCode').val(seller.vnCode);
					
					var shipping = Common.numberFormat(seller.shipping);
					var $shippingType2 = $('.shipping-type-2');
					$shippingType2.find('.opt-shipping-text').text(shipping);
					$shippingType2.find('.opt-shipping').val(seller.shipping);
					$shippingType2.find('.opt-shipping-free-amount').val(seller.shippingFreeAmount);
					$shippingType2.find('.opt-shipping-extra-charge1').val(seller.shippingExtraCharge1);
					$shippingType2.find('.opt-shipping-extra-charge2').val(seller.shippingExtraCharge2);
					$shippingType2.find('.opt-shipping-free-amount-text').text(Common.numberFormat(seller.shippingFreeAmount));
					$('.seller-empty').hide();
					$('.seller-info').show();
					
					// 수수료 
					$('#sellerCommissionRate').val(seller.commissionRate);
					// if ($('input[name=commissionType]').eq(0).prop('checked')) {
                    var commissionType = $('input[name=commissionType]:checked').val();
					if (commissionType == '1') {     // 입점업체 수수료인 경우
						$('#commissionRate').val(seller.commissionRate);
					}
					
					// mdid 자동
					<c:if test="${isSellerPage && item.itemId == 0}"> <%-- 관리자 상품 등록인 경우에만 --%>
						$('#mdId').val(seller.mdId);
					</c:if>
									
				} else {
					clearSellerInfo();
				}
				if (shipment != null) {
					var shipping = Common.numberFormat(shipment.shipping);
					var $shippingType3 = $('.shipping-type-3');
					$shippingType3.find('.opt-shipping-text').text(shipping);
					$shippingType3.find('.opt-shipping').val(shipment.shipping);
					$shippingType3.find('.opt-shipping-free-amount').val(shipment.shippingFreeAmount);
					$shippingType3.find('.opt-shipping-extra-charge1').val(shipment.shippingExtraCharge1);
					$shippingType3.find('.opt-shipping-extra-charge2').val(shipment.shippingExtraCharge2);
					$shippingType3.find('.opt-shipping-free-amount-text').text(Common.numberFormat(shipment.shippingFreeAmount));
	
				
					$('#shipmentId').val(shipment.shipmentId);
					$('#shipmentAddress').val(shipment.fullAddress);
					$('#shipmentGroupCode').val(shipment.shipmentGroupCode);
					$('.shipment-empty').hide();
					$('.shipment-info').show();
					
				} else {
					clearShipmentInfo();
				}
				
				if (shipmentReturn != null) {
				
					$('#shipmentReturnId').val(shipmentReturn.shipmentReturnId);
					$('#shipmentReturnAddress').val(shipmentReturn.shipmentReturnAddress);
					
				} else {
					clearShipmentReturnInfo();
				}
			});
		});
		
	}
	

	
	// 택배사 선택
	$('#deliveryCompanyId').on('change', function() {
		$('#deliveryCompanyName').val($(this).find('option:selected').text());
	});
	
	// 배송비 설정.
	$('input[name=shippingType]').on('click', function() {
		setShippingType();
		setValidatorCondition();
	});
	
	// 출고지/배송비 변경 팝업.
	$('.change-shipment-address').on('click', function(e) {
		e.preventDefault();
		var popupUrl = '${requestContext.managerUri}/shipment/list-popup';


		var $deliveryType = $('#deliveryType');
		if ($deliveryType.val() == '') {
			alert('배송구분을 선택해 주세요.');
			$deliveryType.focus();
			return;
		} else if ($deliveryType.val() == '2') {
			var $sellerId = $('#sellerId');
			if ($sellerId.val() == '') {
				alert('판매자(공급사)를 선택해 주세요.');
				return;
			} 
			
			popupUrl = '${requestContext.managerUri}/shipment/list-popup/' + $sellerId.val();
		}
		
		Common.popup(popupUrl, 'shipment_popup', 980, 750, 1);
	});
	
	// 교환반품 주소 변경 팝업.
	$('.change-shipment-return-address').on('click', function(e) {
		e.preventDefault();
		var popupUrl = '${requestContext.managerUri}/shipment-return/list-popup';
		
		
		var $shipmentReturnType = $('#shipmentReturnType');
		if ($shipmentReturnType.val() == '') {
			alert('반품/교환구분을 선택해 주세요.');
			$shipmentReturnType.focus();
			return;
		} else if ($shipmentReturnType.val() == '2') {
			var $sellerId = $('#sellerId');
			if ($sellerId.val() == '') {
				alert('판매자(공급사)를 선택해 주세요.');
				return;
			} 
			
			popupUrl = '${requestContext.managerUri}/shipment-return/list-popup/' + $sellerId.val();
		}
		Common.popup(popupUrl, 'shipment_return', 980, 750, 1);
	});
	
	// 배송비 종류 노출 설정.
	function showHideShippingType() {
		
		if ($('#deliveryType').val() == '2') {
			$('.shipping-type-2').show();
		} else {
			$('.shipping-type-2').hide();
		}
	}

	
	// 배송비 종류 선택 처리.
	function setShippingType() {
	
		var dbShippingType = '${item.shippingType}';
		var $shippingType = $('input[name=shippingType]:checked');
		var shippingType = $shippingType.val();

		if (shippingType == '1') {
		
		} else if (shippingType == '3') {
			if ($('#shipmentId').val() == 0) {
				alert('출고지를 선택해 주십시오.');
				$('.change-shipment-address').eq(0).focus();
				return false;
			}
		}

		$('.shipping-option tr:gt(3)').find('.opt-shipping').val('').prop('readonly', true);
		$('.shipping-option tr:eq(4)').find('.opt-shipping-free-amount').val('').prop('readonly', true);
		
		if (Number(shippingType) == 5) {
			$('#shippingItemCount').prop('readonly', false);
		} else {
			$('#shippingItemCount').prop('readonly', true);
		}
			
			
		if (Number(shippingType) >= 4) {
			$shippingType.closest('tr').find('.opt-shipping, .opt-shipping-free-amount').prop('readonly', false);
			
			
			
			if (dbShippingType == shippingType) {
				$shippingType.closest('tr').find('.opt-shipping').val($('#shipping').val());
				
				if (shippingType == 4) {
					$shippingType.closest('tr').find('.opt-shipping-free-amount').val($('#shippingFreeAmount').val());
				}
			}
		}
		
		// 제주/도서산간 추가 배송비
		if (shippingType == '2' || shippingType == '3') {
			$('#shippingExtraCharge1, #shippingExtraCharge2').prop('readonly', true);
			var shippingExtraCharge1 = $shippingType.closest('tr').find('.opt-shipping-extra-charge1').val();
			var shippingExtraCharge2 = $shippingType.closest('tr').find('.opt-shipping-extra-charge2').val();
			
			$('#shippingExtraCharge1').val(Common.numberFormat(shippingExtraCharge1));
			$('#shippingExtraCharge2').val(Common.numberFormat(shippingExtraCharge2));
		
		} else {
			$('#shippingExtraCharge1, #shippingExtraCharge2').prop('readonly', false);
		} 
	}
}
	
// 상품정보고시 관련 이벤트 핸들러.
function initItemNoticeEvent() {
	var DEFAULT_MESSAGE = '상세정보 별도표기';
	
	setItemNotice();
	
	// 상품유형 선택 시.
	$('#itemNoticeCode').on('change', function() {
		setItemNotice();
	});
	
	$('#item_info_area').on('click', '.check-all-item-notice', function() {
		var $itemInfoDescriptions = $(this).closest('table').find('input[name=itemInfoDescriptions]');
		var $itemNoticeCheckboxies = $(this).closest('table').find('.check-item-notice');
		
		if ($(this).prop('checked')) {
			$itemInfoDescriptions.prop('readonly', true).val(DEFAULT_MESSAGE);
			$itemNoticeCheckboxies.prop('checked', true);
		} else {
			$itemInfoDescriptions.prop('readonly', false).val('');
			$itemNoticeCheckboxies.prop('checked', false);
		}
	});
	
	$('#item_info_area').on('click', '.check-item-notice', function() {
		var $itemInfoDescription = $(this).closest('tr').find('input[name=itemInfoDescriptions]');
		if ($(this).prop('checked')) {
			$itemInfoDescription.prop('readonly', true).val(DEFAULT_MESSAGE);
		} else {
			$itemInfoDescription.prop('readonly', false).val('');
		}
	});
	
	function setItemNotice() {
		var itemNoticeCode = $('#itemNoticeCode').val();
		
		if (itemNoticeCode == '') {
			$('#item_info_area tr:gt(0)').remove();
			$('.check-all-item-notice-label').hide();
			
		} else {
			$('.check-all-item-notice-label').show();
			
			$.post('${requestContext.managerUri}/item/item-notice-list', {'itemNoticeCode': itemNoticeCode}, function(response) {
				Common.responseHandler(response, function() {
				
					var html = '';
					for (var i = 0; i < response.data.length; i++) {
						var itemNotice = response.data[i];
						
						html += '	<tr>';
						html += '		<td class="label">' + itemNotice.noticeTitle + '<input type="hidden" name="itemInfoTitles" value="' + itemNotice.noticeTitle + '" /></td>';
						html += '		<td>';
						html += '			<div>';
						
						if (itemNotice.noticeTitle != itemNotice.noticeDescription) {
							html += '			<p class="text-info text-sm">* ' + itemNotice.noticeDescription + '</p>';
						
						}
						html += '				<input type="text" name="itemInfoDescriptions" maxlength="100" class="required" title="' + itemNotice.noticeTitle + '" />';
						html += '				<label><input type="checkbox" class="check-item-notice" /> 상세정보 별도표기</label>';
						html += '			</div>';
						html += '		</td>';
						html += '	</tr>';
						
					}
					
					var $itemInfoArea = $('#item_info_area');
					$itemInfoArea.find('tr:gt(0)').remove();
					$itemInfoArea.append(html);
					
					
					// 모두 선택이 체크된 경우.
					if ($('.check-all-item-notice').prop('checked')) {
						
					}
					
					
					// 등록된 데이터가 있는 경우.
					var $itemNoticeData = $('#item-notice-data p');
					var selectedItemNoticeCode = '${item.itemNoticeCode}';
					
					if ($itemNoticeData.size() > 0 && selectedItemNoticeCode == itemNoticeCode) {
					
						$itemNoticeData.each(function(i) {
							var tit = $(this).find('span.title').text();
							var desc = $(this).find('span.desc').text();
							
							$itemInfoArea.find('tr').each(function() {
								var $target = $(this);
								var $itemInfoTitles = $(this).find('input[name=itemInfoTitles]');
								
								
								if ($itemInfoTitles.val() == tit) {
									$target.find('input[name=itemInfoDescriptions]').val(desc);			
							
									if (desc == DEFAULT_MESSAGE) {		
										$target.find('input[name=itemInfoDescriptions]').prop('readonly', true);
										$target.find('input.check-item-notice').prop('checked', true);
									}		
								}
							});
						});
					}
				});
			});
			
		}
	}

}

// 상품 승인 처리 이벤트 
function initItemApprovalEvent() {
	var $btnApproval = $('.btn-approval');
	
	if ($btnApproval.size() == 0) {
		return;
	}
	
	$btnApproval.on('click', function() {
		if (!confirm('승인 처리를 하시겠습니까?\n(상품 정보는 수정되지 않습니다.)')) {
			return;
		}
		
		$.post('/opmanager/item/edit/seller-item-approval/${item.itemId}', null, function(response){
			
			Common.responseHandler(response, function(response) {
				
				alert('승인이 완료 되었습니다.');
				location.href = '/opmanager/item/seller/list';
				
			}, function(response){

				alert(response.errorMessage);
				
			});
			
		}, 'json');
		
	});
}


// 할인 / 포인트 설정 이벤트 핸들러 
function initDiscountAndPointEvent() {
	init();
	
	$('input[name=sellerDiscountFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
	
	$('input[name=spotFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});
	
	$('input[name=sellerPointFlag]').on('click', function() {
		showHideContent($(this));
		setValidatorCondition();
	});

	$('select[name=pointType]').on('change', function(){
		$('input[name=point]').val('0');
		setNumberClass($('input[name=point]'), $(this).val());
	});

	function init() {
		showHideContent($('input[name=sellerDiscountFlag]:checked'));
		showHideContent($('input[name=spotFlag]:checked'));
		showHideContent($('input[name=sellerPointFlag]:checked'));
		
		// 스팟 (운영자 스팟할인 진행시 판매자 수정불가)
		var spotType = $('#spotType').val();
		var isSellerLogin = '${sellerContext.login}';
		if (spotType == '1' && isSellerLogin == 'true') {
			$('#spot-area').find('input, select').prop('disabled', true);
			$('#spot-area').find('button').hide();
		}
		setNumberClass($('input[name=point]'), $('select[name=pointType]').val());
	}

	function setNumberClass(obj, pointType) {

		if (obj.length > 0) {
			obj.removeClass('_number_comma');
			obj.removeClass('_number_float');

			if ('1' == pointType) {
				obj.addClass('_number_float');
			} else {
				obj.addClass('_number_comma');
			}
		}
	}
}

// .hide_content Show / Hide
function showHideContent($selector) {
	var flag = $selector.val();
	var $content = $selector.closest('div').find('.hide_content');
	
	if (flag == 'Y') {
		$content.show();
	} else {
		$content.hide();
	}
}

function getItemSetCategoryFilter(itemId){
	var returnValue = false;
	$.ajaxSetup({'async': false});
	$.get("${requestContext.managerUri}/categories-filter/getItemFilterList?itemId="+itemId, '', function(response){
		// 성공
		if(response.isSuccess){
			itemCategoryCheck(response.data);
		}
	}, 'json').error(function(e){
		alert(e.message);
	});
}

function getItemCategoryFilter(categoryId){
	var returnValue = false;
	$.ajaxSetup({'async': false});
	if(categoryId != null){
		$.get("${requestContext.managerUri}/categories-filter/filterGroupList?categoryId="+categoryId, '', function(response){
			// 성공
			if(response.isSuccess){
				addItemCategoryFilter(response.data);
			}
		}, 'json').error(function(e){
			alert(e.message);
		});
	}
}

function addItemCategoryFilter(data){
	$("#categoryFilterSetting tbody").empty();
	var group = '';
	if(data.length > 0){
		$.each(data, function(i){
			var f_id = data[i].id;
			var f_name = data[i].label;
			var f_desc = data[i].description;
			var vals = data[i].codeList;
			group += "<tr fId='"+f_id+"'><td class='label'>"+f_name+"("+f_desc+")</td><td><div>";
			$.each(vals, function(j){
				group += "<span><label id='filter_"+vals[j].id+"'><input type='checkbox' id='filter_"+vals[j].id+"' name='filterCodeId"+j+"' onclick='categoryFilterCheck()' value='"+vals[j].id+"'> "+vals[j].label+"</label></span>";
			});
			group += "</div></td></tr>";
		});
	} else {
		group = "<tr><td colspan=\"2\"><div><h4>카테고리 선택 시 등록 된 필터가 노출됩니다.</h4></div></td></tr>";
	}
	$("#categoryFilterSetting tbody").append(group);

	// 상세인 경우
	if(${mode == 'edit'}){
		getItemSetCategoryFilter(${item.itemId});
	}
}

function itemCategoryCheck(data){
	var fL = $("#categoryFilterSetting tr");
	for(var i = 0; i < fL.length; i++){
		var fId = $(fL[i]).attr("fId");
		// 1차 filterGroupId 체크
		for(var j = 0; j < data.length; j++){
			if(fId == data[j].filterGroupId){
				var input = $(fL[i]).find("input");
				// 일치 할 시 CODE 체크
				for(var z = 0; z < input.length; z++){
					if(input[z].value == data[j].filterCodeId){
						input[z].checked = true;
					}
				}
			}
		}
	}
}

function categoryFilterCheck(){
	$("#categoryInput").empty();
	var l = $("#categoryFilterSetting tbody >"); // 항목 갯수
	var filterSetList = new Array();
	$.each(l, function(i){
		var filterMap = {};
		var fid = $(l[i]).attr("fid");	// 항목 ID
		// 체크항목
		var cvL = $(l[i]).find("input[type='checkbox']:checked");
		var cvList = new Array();
		var filterGroups = "<input type='hidden' name='filterGroups["+i+"]' value='"+fid+"'>";
		$("#categoryInput").append(filterGroups);
		$.each(cvL, function(j){
			var filterCodes = "<input type='hidden' name='filterCodes["+fid+"]["+j+"]' value='"+cvL[j].value+"'>";
			$("#categoryInput").append(filterCodes);
		});
	});
}

// 상품 카테고리 추가
function addItemCategory() {
	var breadcrumb = '';
	var categoryId = '';
	$('select.category').each(function(index) {

		var $selectedOption = $(this).find('option:selected');
		if ($selectedOption.size() > 0) {
			
			// 팀/그룹
			if (index == 0) {
				breadcrumb = $selectedOption.parent().attr('label');
			}

			categoryId = $selectedOption.attr('rel');

			breadcrumb += ' > ' + $selectedOption.text();
		}
		
	});
	
	
	if (categoryId == undefined || categoryId == '') {
		alert(Message.get("M00078"));	// 상품 카테고리를 선택해 주세요.
		$('#category_team_group').focus();
		return;
	}
	
	
	//alert(categoryId + '  : ' + breadcrumb);
	
	
	// 중복 체크
	var itemDuplicationCount = 0;
	$('input[name=categoryIds]').each(function() {
		if ($(this).val() == categoryId) {
			itemDuplicationCount++;
		}
	});
	
	if (itemDuplicationCount > 0) {
		alert(Message.get("M00079"));	// 이미 등록된 카테고리입니다.
		return;
	}
	
	
	var html = '';
	html += '	<li id="item_category_' + categoryId + '">' + breadcrumb;
	html += '		<a href="javascript:deleteItemCategory(' + categoryId + ')" class="delete">[' + Message.get("M00074") + ']</a>'; // 삭제
	html += '		<input type="hidden" name="categoryIds" value="' + categoryId + '" />';
	html += '	</li>';
	
	
	$('#item_categories').find('li.nothing').remove();
	$('#item_categories').append(html);

	// 카테고리 추가 시 카테고리 필터 조회
	getItemCategoryFilter(categoryId);
}

function deleteItemCategory(categoryId) {
	$('#item_category_' + categoryId).remove();
	
	var $itemCategories = $('#item_categories');
	if ($itemCategories.find('li').size() == 0) {
		$itemCategories.append('<li class="nothing">' + Message.get("M00077") + '</li>'); // Jun-Eu Son 2017.4.24 카테고리 삭제시 메시지 처리 수정
	}
	addItemCategoryFilter("");
}

// 포인트 설정 정보 노출여부
function displayItemPointConfig() {
	if ($('#item_point_area tr').size() == 0) {
		$('#item_point_config').hide();
	} else {
		$('#item_point_config').show();
	}
	
}

// 포인트 설정 정보 추가
function addItemPoint() {
	var optionTemplate = $('#item_point_template tbody').html();

	var randomKey = Math.floor(Math.random() * 10000) + 1;
	
	var radioButtons = '';
	radioButtons += '<input type="radio" name="R' + randomKey + '" id="R' + randomKey + '1" checked="checked"> <label for="R' + randomKey + '1">%</label>';
	radioButtons += '<input type="radio" name="R' + randomKey + '" id="R' + randomKey + '2"> <label for="R' + randomKey + '2">P</label>';
	
	
	$('#item_point_area').append(optionTemplate.replace('{INPUT_RADIO}', radioButtons));
	displayItemPointConfig();
	
	var $item = $('#item');
	$item.find('.ui-datepicker-trigger').remove();
	$item.find("input.term").each(function() {
		$(this).removeClass('hasDatepicker').datepicker(); 
	});
}

// validator 체크 조건 설정.
function setValidatorCondition() {
	var currentClasses = "PREFIX";
	var targetFlag = '';
	

	// 비회원 판매가격
	targetFlag = $('input[name=salePriceNonmemberFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-nonmember-price';
	}
	
	// 상품재고
	targetFlag = $('input[name=stockFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-stock-quantity';
	}
	
	// 사은품 정보
	targetFlag = $('input[name=freeGiftFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-free-gift';
	}
	
	
	// 즉시할인 
	targetFlag = $('input[name=sellerDiscountFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-seller-discount-amount';
	}
	
	
	// 스팟할인
	/*targetFlag = $('input[name=spotFlag]:checked').val();
	if (targetFlag == 'Y') {
		currentClasses += ',required-spot';
	}*/
	
	// 포인트 지급
	targetFlag = $('input[name=sellerPointFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-seller-point';
	}
	
	// 옵션
	targetFlag = $('input[name=itemOptionFlag]:checked').val();
	
	if (targetFlag == 'Y') {
		// 옵션 형태
		targetFlag = $('input[name=itemOptionType]:checked').val();

		if (targetFlag == 'S3') {
			currentClasses += ',required-item-option';
			currentClasses += ',required-item-option-s3';
		} else if (targetFlag == 'T') {
			currentClasses += ',required-item-option-text';
		} else if (targetFlag == 'C') {
			currentClasses += ',required-item-option-combination';
		} else {
			currentClasses += ',required-item-option';
		}
	}
	
	// 추가구성 상품.
	targetFlag = $('input[name=itemAdditionFlag]:checked').val(); 
	if (targetFlag == 'Y') {
		currentClasses += ',required-item-addition';
	}
	
	// 배송비 설정.
	targetFlag = $('input[name=shippingType]:checked').val(); 
	if (targetFlag == '4') {
		currentClasses += ',required-shipping-4';
	}
	if (targetFlag == '5') {
		currentClasses += ',required-shipping-5';
	}
	if (targetFlag == '6') {
		currentClasses += ',required-shipping-6';
	}
	
	
	currentClasses = currentClasses.replace('PREFIX,', '');
	currentClasses = currentClasses.replace('PREFIX', '');

	$.validator.currentClass = currentClasses;
	
}

function setRelationItemDisplay() {
	var relationItemDisplayType = $('input[name=relationItemDisplayType]:checked').val();
	if (relationItemDisplayType == '1') {
		$('#relation_item_area').hide();
	} else {
		$('#relation_item_area').show();
	}
}

function setAdditionItemDisplay() {
	var itemAdditionFlag = $('input[name=itemAdditionFlag]:checked').val();
	if (itemAdditionFlag == 'N') {
		$('#addition_item_area').hide();
	} else {
		$('#addition_item_area').show();
	}
}

// 상품이미지 삭제 
function deleteItemImage(type, id) {
	var message = '이미지가 실제로 삭제됩니다.(복구불가)\n삭제하시겠습니까?'; 
	if (!confirm(message)) {
		return;
	} 
	
	
	if (type == "main") {
		var param = {'itemId': id};
		$.post('${requestContext.managerUri}/item/delete-item-image', param, function(response){
			Common.responseHandler(response);
			$('.item_image_main').remove();
		});	
	} else if (type == "details") {
		var param = {'itemImageId': id};
		$.post('${requestContext.managerUri}/item/delete-item-details-image', param, function(response){
			Common.responseHandler(response);
			$('#item_image_id_' + param.itemImageId).remove();
		});	
	}
	
}


// 상세설명 - 추가/삭제 이벤트 핸들러 
/*
function itemInfoEventHandler() {
	// 기본으로 2개 보임.
	if ($('#item_info_area tr').size() == 0) {
		for (var i = 0; i < 1; i++) {
			addItemInfo();
		}
	}
	
	if ($('#item_info_mobile_area tr').size() == 0) {
		for (var i = 0; i < 1; i++) {
			addItemInfoMobile();
		}
	}
	
	
	// textarea resize - load
	$('#item_info_area textarea, #item_info_mobile_area textarea').each(function() {
		$(this).css('height','auto');
		$(this).height(this.scrollHeight < 30 ? 20 : this.scrollHeight);
	});
	
	// textarea resize - event
	$(document).on('keyup', '#item_info_area textarea, #item_info_mobile_area textarea' ,function(){
		$(this).css('height','auto');
		$(this).height(this.scrollHeight < 30 ? 20 : this.scrollHeight);
    });	
	
	// 항목 삭제 이벤트.
	$(document).on('click', '#item_info_area .delete_item_info' ,function(e) {
		e.preventDefault();
		
		if ($('#item_info_area tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input, textarea').val('');
			$(this).closest('tr').find('textarea').height(20);
		}
    });	
	
	// 항목 삭제 이벤트.
	$(document).on('click', '#item_info_mobile_area .delete_item_info' ,function(e) {
		e.preventDefault();
		
		if ($('#item_info_mobile_area tr').size() > 1) {
			$(this).closest('tr').remove();
		} else {
			$(this).closest('tr').find('input, textarea').val('');
			$(this).closest('tr').find('textarea').height(20);
		}
    });	
}
*/
// 상세설명 - 항목추가
function addItemInfo() {
	var html = '';
	html += '	<tr>';
	html += '		<td class="label"><input type="text" name="itemInfoTitles" maxlength="50" /></td>';
	html += '		<td>';
	html += '			<div>';
	html += '				<textarea name="itemInfoDescriptions" maxlength="500" rows="1"></textarea>';
	html += '			</div>';
	html += '		</td>';
	html += '		<td class="middle"><a href="#" class="fix_btn delete_item_info">' + Message.get("M00074") + '</a></td>';	// 삭제
	html += '	</tr>';
	
	$('#item_info_area').append(html);
}

//상세설명 - 항목추가
function addItemInfoMobile() {
	var html = '';
	html += '	<tr>';
	html += '		<td class="label"><input type="text" name="itemInfoMobileTitles" maxlength="50" /></td>';
	html += '		<td>';
	html += '			<div>';
	html += '				<textarea name="itemInfoMobileDescriptions" maxlength="500" rows="1"></textarea>';
	html += '			</div>';
	html += '		</td>';
	html += '		<td class="middle"><a href="#" class="fix_btn delete_item_info">' + Message.get("M00074") + '</a></td>';	// 삭제
	html += '	</tr>';
	
	$('#item_info_mobile_area').append(html);
}


function copyContent(id) {
	
	if (id == 'itemInfo') {
		var source = $('#item_info_area').html();
		source = source.replace(/itemInfoTitles/g, 'itemInfoMobileTitles');
		source = source.replace(/itemInfoDescriptions/g, 'itemInfoMobileDescriptions');
		
		$('#item_info_mobile_area').empty().append(source);
		
		$('input[name=itemInfoTitles]').each(function(i) {
			var sourceTitle = $('input[name=itemInfoTitles]').eq(i).val();
			var sourceDescription = $('#item_info_area textarea').eq(i).val();
			
			$('input[name=itemInfoMobileTitles]').eq(i).val(sourceTitle);
			$('#item_info_mobile_area textarea').eq(i).val(sourceDescription);
		});
		return;
	}
	
	var sourceHtml = editors.getById[id].getIR();
	editors.getById[id + "Mobile"].exec("SET_CONTENTS", ['']); 
	editors.getById[id + "Mobile"].exec("PASTE_HTML", [sourceHtml]);
	
	//oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
}


function scrollButtons() {
	$(window).scroll(function () {
		var st = $(window).scrollTop();
		var scrollBottom = $(document).height() - $(window).height() - $(window).scrollTop();
		
		if (scrollBottom < 170) {
			$('#buttons').removeClass('fixed_button');
		} else {
			$('#buttons').addClass('fixed_button');
		}
	});
}

// 재고 연동 여부에 따른 수량입력 show/hide 이벤트
function initStockFlagEvent() {
	
	$('input[name=stockFlag]').on('click', function() {
		var stockFlag = $('input[name=stockFlag]:checked').val();
		var $trStockQuantity = $('#trStockQuantity').find('input[name="stockQuantity"]');
	
		if (stockFlag == 'Y') {
			$trStockQuantity.attr('disabled', false);
		} else {
			$trStockQuantity.attr('disabled', true);
			$trStockQuantity.val('');
		}
		
		setValidatorCondition();
	});
	
}


// 출고지 정보 팝업 콜백 핸들러 
function handleShipmentPopupCallback(shipment) {
	//alert(shipment.shipmentGroupCode);
	$('#shipmentId').val(shipment.shipmentId);
	$('#shipmentAddress').val('[' + shipment.zipcode + '] ' + shipment.address + ' ' + shipment.addressDetail);
	$('#shipmentGroupCode').val(shipment.shipmentGroupCode);
	
	var $shippingType3 = $('.shipping-type-3');
	$shippingType3.find('.opt-shipping').val(shipment.shipping);
	$shippingType3.find('.opt-shipping-text').text(Common.numberFormat(shipment.shipping));
	$shippingType3.find('.opt-shipping-free-amount').val(shipment.shippingFreeAmount);
	$shippingType3.find('.opt-shipping-free-amount-text').text(Common.numberFormat(shipment.shippingFreeAmount));
	$shippingType3.find('.opt-shipping-extra-charge1').val(shipment.shippingExtraCharge1);
	$shippingType3.find('.opt-shipping-extra-charge2').val(shipment.shippingExtraCharge2);
	
	$('.shipment-empty').hide();
	$('.shipment-info').show();
}	

function handleShipmentReturnPopupCallback(shipmentReturn) {
	$('#shipmentReturnId').val(shipmentReturn.shipmentReturnId);
	$('#shipmentReturnAddress').val('[' + shipmentReturn.zipcode + '] ' + shipmentReturn.address + ' ' + shipmentReturn.addressDetail);
	

}


// 판매자 선택 이벤트 핸들러.
function initSellerIdEvent() {
	
}


// 판매자조건부 정보 초기화
function clearSellerInfo() {
	var $shippingType2 = $('.shipping-type-3');
	$shippingType2.find('.opt-shipping-text').text("0");
	$shippingType2.find('.opt-shipping').val("0");
	$shippingType2.find('.opt-shipping-free-amount').val("0");
	$shippingType2.find('.opt-shipping-extra-charge1').val("0");
	$shippingType2.find('.opt-shipping-extra-charge2').val("0");
	
	$('.seller-empty').show();
	$('.seller-info').hide();
}


// 출고지 정보 초기화
function clearShipmentInfo() {
	var $shippingType3 = $('.shipping-type-3');
	$shippingType3.find('.opt-shipping-text').text("0");
	$shippingType3.find('.opt-shipping').val("0");
	$shippingType3.find('.opt-shipping-free-amount').val("0");
	$shippingType3.find('.opt-shipping-free-amount-text').text("0");
	$shippingType3.find('.opt-shipping-extra-charge1').val("0");
	$shippingType3.find('.opt-shipping-extra-charge2').val("0");
	
	$('#shipmentId').val(0);
	$('#shipmentAddress').val('');
	
	$('#shippingExtraCharge1').val('0');
	$('#shippingExtraCharge2').val('0');
	
	$('.shipment-empty').show();
	$('.shipment-info').hide();
}
// 반송지 정보 초기화
function clearShipmentReturnInfo() {
	$('#shipmentReturnId').val(0);
	$('#shipmentReturnAddress').val("");
}

function findMd(targetId) {
	Common.popup('/opmanager/seller/find-md?targetId=' + targetId, 'find_md', 720, 800, 1);
}

function clearMd(targetId) {
	var $target = $('#' + targetId);
	$target.val('');
	$target.closest('td').find('#mdName').val('');
}

// MD 검색 콜백 
function handleFindMdCallback(response) {
	var $target = $('#' + response.targetId);
	$target.val(response.userId);
	$target.closest('td').find('#mdName').val(response.userName);

}

function findItemPopup(targetId, itemUserCode){
	<c:choose>
		<c:when test="${isSellerPage}">
			Common.popup("/seller/item/find-item?targetId=" + targetId + "&itemUserCode=" + itemUserCode, 'find-item', 1000, 600, 1);
		</c:when>
		<c:otherwise>
		Common.popup("/opmanager/item/find-item?targetId=" + targetId + "&itemUserCode=" + itemUserCode, 'find-item', 1000, 600, 1);
		</c:otherwise>
	</c:choose>
}

function sendRestockNotice() {
    var param = {
        'itemId' : '${item.itemId}'
	};

    if (confirm('재입고 메시지를 전송하시겠습니까?')) {
		$.post('/opmanager/item/restock-notice/message', param, function(response) {
			Common.responseHandler(response, function () {
                $('.restock-button').addClass('hide');
                $('.restock-text').removeClass('hide');
				alert('메시지가 전송되었습니다.')
			});
		}, 'json');
    }
}

function findGiftItem(){

	var targetId = 'freeGift';

	<c:choose>
		<c:when test="${isSellerPage}">
			Common.popup("/seller/gift-item/find-item?processType=progress&targetId=" + targetId, 'find-gift-item', 1000, 600, 1);
		</c:when>
		<c:otherwise>
			Common.popup("/opmanager/gift-item/find-item?processType=progress&targetId=" + targetId + "&sellerId="+$('#sellerId').val(), 'find-gift-item', 1000, 600, 1);
		</c:otherwise>
	</c:choose>

}

function selectSpotDateType(type) {

    if (type == '2') {
        $('.spotDateTypeOne').css('display', 'none');
        $('.spotDateTypeTwo').css('display', '');

    } else {
        $('.spotDateTypeOne').css('display', '');
        $('.spotDateTypeTwo').css('display', 'none');

    }

}

function findErpItem() {
	Common.popup('/opmanager/item/find-erp-item', 'find_erp_item', 500, 200, 1);
}
</script>