<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

		<div class="location">
			<a href="#">상품관리</a> &gt;  <a href="#">상품정보</a> &gt; <a href="#" class="on">상품등록</a>
		</div>
		

		<form:form modelAttribute="item" action="${action}" method="post" enctype="multipart/form-data">		
			<form:hidden path="sellerDiscountFlag" />	
			<form:hidden path="taxType" />	
			<form:hidden path="commissionType" />	
			
			<input type="hidden" id="useItemUserCode" value="${useItemUserCode}" />
			
			
			
			<div class="item_list">
			
				<h3><span>상품 기본정보</span></h3>
				
				
				<!-- 등록된 상품 카테고리가 없음경우 -->
				<ul id="item_categories" class="category_box">
					

					<c:forEach items="${item.breadcrumbs}" var="breadcrumb">
						<li id="item_category_${breadcrumb.categoryClass}">

							${breadcrumb.teamName} > ${breadcrumb.groupName}
							
							<c:forEach items="${breadcrumb.breadcrumbCategories}" var="subBreadcrumb">
								 > ${subBreadcrumb.categoryName}
							</c:forEach>
							<a href="javascript:deleteItemCategory(${breadcrumb.categoryClass})" class="delete">[${op:message('M00074') }]</a>
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
					
					<span class="category_btn2" style="position: absolute; left: 800px; top: 0; ">
						<button type="button" class="btn btn-dark-gray btn-sm" onclick="addItemCategory()"><span>+ 카테고리 추가</span></button>
					</span>
					
				</div> <!-- // category_wrap -->
				
				
				
				<div class="board_write">						
					<table class="board_write_table" summary="상품기본정보">
						<caption>상품기본정보</caption>
						<colgroup>
							<col style="width: 150px;" />
							<col style="" />
						</colgroup>
						<tbody>
							
							<tr>
								<td class="label">상품번호 (필수)</td>
								<td>
									<div>
										<c:choose>
											<c:when test="${mode == 'edit'}">
												<form:input path="itemUserCode" maxlength="20" class="required" readonly="true" title="상품번호 (필수)" />
											</c:when>
											<c:otherwise>
												<form:input path="itemUserCode" maxlength="20" class="required" title="상품번호 (필수)" /> <a href="#" id="btn_check_duplicate" class="btn_write gray">중복검사</a>
											</c:otherwise>
										</c:choose>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">상품명 ( 필수)</td>
								<td>
									<div>
										<form:input path="itemName" maxlength="100" class="full required" title="상품명 ( 필수)" /> 									
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">소속팀</td>
								<td>
									<div>
										<form:radiobuttons path="team" items="${categoryTeamList}" itemValue="code" itemLabel="name" class="required" title="소속팀" /> 									
									</div>
								</td>
							</tr>
							
							
							<tr>
								<td class="label">정가</td>
								<td>
									<div>
										<form:input path="itemPrice" maxlength="8" class="optional _number" title="정가" /> <span>※ VAT별도</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">회원 가격</td>
								<td>
									<div>
										<input type="text" name="salePrice" id="salePrice" maxlength="8" value="${op:zeroToEmpty(item.salePrice)}" class="required _number _min_1" title="회원 가격" /> <span>※ VAT별도</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">비회원 가격</td>
								<td>
									<div>
										<input type="text" name="salePriceNonmember" id="salePriceNonmember" maxlength="8" value="${op:zeroToEmpty(item.salePriceNonmember)}" class="optional _number" title="비회원 가격" /> <span>※ VAT별도</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">비회원 구매 설정</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="nonmemberOrderType" value="1" label="비회원 구매가능" />
											<form:radiobutton path="nonmemberOrderType" value="2" label="회원가격 비표시" />
											<form:radiobutton path="nonmemberOrderType" value="3" label="상세페이지 접속 불가" />
										</p>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">공개 유무</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="displayFlag" value="Y" label="공개" />
											<form:radiobutton path="displayFlag" value="N" label="비공개" />
										</p>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">상품 라벨</td>
								<td>
									<div>
										<span><form:radiobutton path="itemLabel" value="0" label="없음" /></span>
										<span><form:radiobutton path="itemLabel" value="2" label="NEW" /></span>
										<span><form:radiobutton path="itemLabel" value="3" label="SALE" /></span>
										<span><form:radiobutton path="itemLabel" value="4" label="사기" /></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">상품 라벨 (판매상태)</td>
								<td>
									<div>
										<span>
											<form:radiobutton path="soldOut" value="1" label="입하대기" />
										</span>
										<span>
											<form:radiobutton path="soldOut" value="2" label="판매완료" />
										</span>	
										
										<p class="mt10">
											<form:checkbox path="itemLabelAuto" value="1" label="판매 다할 때 자동으로 「입하 대기」가 표기됩니다." />
											<input type="hidden" name="!itemLabelAuto" value="0" />
										</p>
										<p>
											자식 상품의 재고가 모두 0이거나 부모 상품의 재고가 0이면 자동으로 체크가 들어갑니다.<br />
											(먼저 자식 상품의 재고 0을 확인하고 후 부모 상품의 재고 0보기)
										</p>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">상품유형</td>
								<td>
									<div>
										<span>
											<form:checkbox path="itemType1" value="1" label="무료배송" />
											<input type="hidden" name="!itemType1" value="0" />
										</span>
										<span>
											<form:checkbox path="itemType2" value="1" label="대금상환" />
											<input type="hidden" name="!itemType2" value="0" />
										</span>
										<span>
											<form:checkbox path="itemType4" value="1" label="추천상품" />
											<input type="hidden" name="!itemType4" value="0" />
										</span>
										<span>
											<form:checkbox path="itemType5" value="1" label="스테디 셀러 상품" />
											<input type="hidden" name="!itemType5" value="0" />
										</span>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">색상</td>
								<td>
									<div id="item_colors">
										<button type="button" class="table_btn mb10" onclick="Common.popup('/opmanager/item/color', 'item-color', 450, 700)"><span>색상관리</span></button>
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
							
							
							
							
							<tr>
								<td class="label">재고연동</td>
								<td>
									<div>
										<form:radiobutton path="stockFlag" value="Y" label="연동함" />
										<form:radiobutton path="stockFlag" value="N" label="연동안함" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">상품수량</td>
								<td>
									<div>
										<input type="text" name="stockQuantity" id="stockQuantity" maxlength="5" value="${op:negativeNumberToEmpty(item.stockQuantity)}" class="optional _number" title="상품수량" />
									</div>
								</td>
							</tr>
							
							
							<tr>
								<td class="label">재입고 예정일</td>
								<td>
									<div>
										<p>
											<form:checkbox path="stockScheduleAutoFlag" value="Y" label="재고가없는 경우는 자동으로 다음 설정이 적용됩니다." />
											<input type="hidden" name="!stockScheduleAutoFlag" value="N" />
										</p>
										<p>
											체크 박스에 체크가되어 재고가 0이되면 자동으로 설정된 값이 출력됩니다.<br />
											판매 다할 때 자동으로 「입하 대기」가 표기됩니다. * 무보기도 자동 표시 <br />
											(먼저 자식 상품의 재고 0을 확인하고 후 부모 상품의 재고 0보기)
										</p>
										
										<p class="mt10">
											<form:radiobutton path="stockScheduleType" value="" label="없음" /><br />
										</p>
										<p>
											<form:radiobutton path="stockScheduleType" value="date" label="입하 예정일" />
											<form:input path="stockScheduleDate" title="입하 예정일"/><br />
										</p>
										<p>
											<form:radiobutton path="stockScheduleType" value="text" label="기타 텍스트" />
											<form:input path="stockScheduleText" title="기타 텍스트"/>
										</p>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">최소주문수량</td>
								<td>
									<div>
										<input type="text" name="orderMinQuantity" id="orderMinQuantity" maxlength="5" value="${op:negativeNumberToEmpty(item.orderMinQuantity)}" class="optional _number" title="최소주문수량" />
										<span class="f11">※ 입력 값이 없는 경우는 제한이 없습니다.</span>
								</div></td>
							</tr>
							<tr>
								<td class="label">최대수량</td>
								<td>
									<div>
										<input type="text" name="orderMaxQuantity" id="orderMaxQuantity" maxlength="5" value="${op:negativeNumberToEmpty(item.orderMaxQuantity)}" class="optional _number" title="최대수량" />
										<span class="f11">※ 입력 값이 없는 경우는 제한이 없습니다.</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">자사상품/타사상품</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="otherFlag" value="1" label="자사상품" />
											<form:radiobutton path="otherFlag" value="2" label="타사상품" />
										</p>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">추천상품</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="recommendFlag" value="Y" label="포함" />
											<form:radiobutton path="recommendFlag" value="N" label="포함하지 않음" />
										</p>
									</div>
								</td>
							</tr>
							
							
							<tr>
								<td class="label">상품구분</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="itemType" value="1" label="통상 상품" />
											<form:radiobutton path="itemType" value="2" label="메이커직송 상품" />
											<form:radiobutton path="itemType" value="3" label="메일편 상품" />
										</p>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">배송비 설정</td>
								<td>
									<div>
										<form:hidden path="deliveryId" />
										<form:hidden path="deliveryChargeId" />
										
										<select id="deliveryCharge" class="full required" title="배송비">
											<option value="">선택하세요</option>
											
											<c:forEach items="${deliveryList}" var="delivery">
												<c:set var="deliveryTypeText">묶음</c:set>
												<c:if test="${delivery.deliveryType == 2}">
													<c:set var="deliveryTypeText">개별</c:set>
												</c:if>
												
												<c:forEach items="${delivery.deliveryChargeList}" var="deliveryCharge">
													<c:choose>
														<c:when test="${deliveryCharge.deliveryChargeType == '1'}">
															<option value="${delivery.deliveryId}_${deliveryCharge.deliveryChargeId}" ${op:selected(deliveryCharge.deliveryChargeId, item.deliveryChargeId)} class="mail_${delivery.mailFlag}">[${deliveryTypeText}] ${delivery.title} (무료)</option>
														
														</c:when>
														<c:when test="${deliveryCharge.deliveryChargeType == '2'}">
															<option value="${delivery.deliveryId}_${deliveryCharge.deliveryChargeId}" ${op:selected(deliveryCharge.deliveryChargeId, item.deliveryChargeId)} class="mail_${delivery.mailFlag}">[${deliveryTypeText}] ${delivery.title} (유료-${op:numberFormat(deliveryCharge.deliveryCharge)}원)</option>
														
														</c:when>
														<c:when test="${deliveryCharge.deliveryChargeType == '3'}">
															<option value="${delivery.deliveryId}_${deliveryCharge.deliveryChargeId}" ${op:selected(deliveryCharge.deliveryChargeId, item.deliveryChargeId)} class="mail_${delivery.mailFlag}">[${deliveryTypeText}] ${delivery.title} (조건-${op:numberFormat(deliveryCharge.deliveryCharge)}원 | ${op:numberFormat(deliveryCharge.deliveryFreeAmount)}원 이상 무료)</option>
														
														</c:when>
													</c:choose>
												</c:forEach>
											</c:forEach>
											
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">${op:message('M00246')}</td>
								<td>
									<div>
										<p class="mb5">
											<button type="button" class="table_btn add_item_point" onclick="addItemPoint()"><span>기간별 ${op:message('M00246')} 추가</span></button>
										</p>
										
											<!-- 포인트 추가시 -->
											<table id="item_point_config" class="board_list_table" summary="${op:message('M00246')} 추가">
												<caption>${op:message('M00246')} 추가 리스트</caption>
												<colgroup>
													<col style="width: 160px;">
													<col style="">
													<col style="width: 70px;">
													<col style="width: 70px;">
												</colgroup>
												<thead>
													<tr>
														<th>${op:message('M00246')}</th>
														<th>${op:message('M00246')} 적용 기간</th>
														<th>특정일 적용</th>
														<th>삭제</th>		
													</tr>
												</thead>
												<tbody id="item_point_area">
												
													<c:forEach items="${pointConfigList}" var="pointConfig">
														<tr>
															<td>
																<input type="text" name="point" maxlength="4" value="${pointConfig.point}"  class="three required _number" title="${op:message('M00246')}" />
																<input type="hidden" name="pointType" value="${pointConfig.pointType}" />
																
																<input type="radio" name="R${pointConfig.pointConfigId}" id="R${pointConfig.pointConfigId}1" ${op:checked('1', pointConfig.pointType)}> <label for="R${pointConfig.pointConfigId}1">%</label>
																<input type="radio" name="R${pointConfig.pointConfigId}" id="R${pointConfig.pointConfigId}2" ${op:checked('2', pointConfig.pointType)}> <label for="R${pointConfig.pointConfigId}2">원</label>
															</td>
															<td>
																<span class="datepicker"><input type="text" name="pointStartDate" class="term  _date" maxlength="8" value="${pointConfig.startDate}" title="시작일"></span>
																<select name="pointStartTime" title="시간 선택">
																	<c:forEach items="${hours}" var="code">
																		<option value="${code.value}" ${op:selected(code.value, pointConfig.startTime)}>${code.label}</option> 
																	</c:forEach>
																</select> 시 ~
																<span class="datepicker"><input type="text" name="pointEndDate" class="term required _date" maxlength="8" value="${pointConfig.endDate}" title="종료일"></span>
																<select name="pointEndTime" title="시간 선택">
																	<c:forEach items="${hours}" var="code">
																		<option value="${code.value}" ${op:selected(code.value, pointConfig.endTime)}>${code.label}</option> 
																	</c:forEach>
																</select> 시
															</td>
															<td>
																<input type="text" name="pointRepeatDay" maxlength="2" class="optional _number" value="${pointConfig.repeatDay}" style="width: 25px;" title="일" /> 일
															</td>
															
															<td><a href="#" class="fix_btn delete_item_point">삭제</a></td>
														</tr>
														
													</c:forEach>
												</tbody>
											</table>
									
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">캐치카피</td>
								<td>
									<div>
										<form:textarea path="simpleContent" class="full" title="캐치카피" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">캐치설명</td>
								<td>
									<div>
										<p>
											<form:textarea path="listContent" cols="30" rows="3" title="캐치설명" />
										</p>
										<span class="f11 ml0">
											※ 제품 화면의 상품명은 일본어 (히라가나, 한자 이외는 제외)에서 30 문자까지 볼 수 있습니다.
										</span>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">사이트 내 검색 용 키워드</td>
								<td>
									<div>
										<form:textarea path="itemKeyword" cols="30" rows="3" title="검색 키워드" />
										<span> (0 / 240byte (120 자)</span>
									</div>
								</td>
							</tr>
							
							
						</tbody>
					</table>
							
				</div>			
			</div>
			
			
			<div class="item_list mt60">
			
				<h3><span>상품옵션등록</span></h3>
				
				<div class="board_write">
						
					<table class="board_write_table" summary="상품옵션등록">
						<caption>상품옵션등록</caption>
						<colgroup>
							<col style="width: 150px;" />
							<col style="" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">상품옵션등록여부</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="itemOptionFlag" value="N" label="옵션없음" /> 
											<form:radiobutton path="itemOptionFlag" value="Y" label="옵션사용" /> 
											
											<button type="button" class="table_btn ml10 add_item_option" style="display:none" onclick="addItemOption()"><span>상품옵션 등록</span></button>
										</p>
									</div>
								</td>
							</tr>
							
						</tbody>
					</table>
					
			
					<div id="item_option_area">
						<table class="board_list_table" summary="상품옵션 추가">
							<caption>상품옵션</caption>
							<colgroup>
								<col style="">
								<col style="width: 640px;">
								<col style="width: 70px;">
							</colgroup>
							<thead>
								<tr>
									<th>옵션명</th>
									<th class="border-left">설정</th>		
									<th>삭제</th>		
								</tr>
							</thead>
							<tbody>
							
								<c:forEach items="${item.itemOptionGroups}" var="itemOptionGroup" varStatus="i">
									
									<tr>
										<td>
											<input type="text" name="option_title" class="full required_item_option" maxlength="100" value="${itemOptionGroup.optionTitle}" title="옵션명" /><br /><br />
											<a href="#" class="fix_btn add_sub_option">항목추가</a>
										</td>
										<td class="item_sub_option_area">
											<div>
												<span class="option_display">숨김</span>
												<span class="option_name">옵션명</span>
												<span class="option_code">상품번호</span>
												<span class="option_stock_quantity">재고</span>
												<span class="option_price">회원가격</span>
												<span class="option_price_nonmember">비회원가격</span>
												<span class="option_stock_schedule_text">옵션텍스트</span>
												<span class="option_stock_schedule_date">입하예정일</span>
												<span class="option_delete"></span>
											</div>
									
											<c:forEach items="${itemOptionGroup.itemOptions}" var="itemOption" varStatus="i">
												<div>
													<input type="hidden" name="optionName1" value="${itemOption.optionName1}" />
													<span class="option_display"><input type="checkbox" name="optionDisplayFlagCheck" ${op:checked('N', itemOption.optionDisplayFlag)}/><input type="hidden" name="optionDisplayFlag" value="${itemOption.optionDisplayFlag}" /></span>
													<span class="option_name"><input type="text" name="optionName2" class="required_item_option full" value="${itemOption.optionName2}" title="옵션항목명" /></span>
													<span class="option_code"><input type="text" name="optionCode" class="required_item_option full" value="${itemOption.optionStockCode}" title="상품코드" /></span>
													<span class="option_stock_quantity"><input type="text" name="optionStockQuantity" class="full" value="${op:negativeNumberToEmpty(itemOption.stockQuantity)}" title="재고" /></span>
													<span class="option_price"><input type="text" name="optionPrice" class="full" value="${op:zeroToEmpty(itemOption.price)}" title="회원가격" /></span>
													<span class="option_price_nonmember"><input type="text" name="optionPriceNonmemer" class="full" value="${op:zeroToEmpty(itemOption.priceNonmember)}" title="비회원가격" /></span>
													<span class="option_stock_schedule_text"><input type="text" name="optionStockScheduleText" class="full" value="${itemOption.stockScheduleText}" title="입하예정일 텍스" /></span>
													<span class="option_stock_schedule_date"><input type="text" name="optionStockScheduleDate" class="full" value="${itemOption.stockScheduleDate}" title="입하예정일" /></span>
													<span class="option_delete"><a href="" class="delete_sub_option">삭제</a></span>
												</div>
											</c:forEach>
									
						
										</td>
										<td><a href="#" class="fix_btn delete_item_option">삭제</a></td>
									</tr>
								</c:forEach>	
							</tbody>
						</table>
					</div>
										
				</div> <!-- // board_write -->
			</div>
			
			
			<div class="item_list mt60">
			
				<h3>
					<span>상품 이미지</span>
					<span class="f12">※ 이미지를 클릭하면 해당이미지가 삭제 됩니다.</span>
				</h3>
				
				<div class="board_write">
						
					<table class="board_write_table item_image_info">
						<colgroup>
							<col style="width: 150px;">
							<col style="">
						</colgroup>
						<tbody>
							<tr>
								<td class="label">대표이미지</td>
								<td>
									<div>
										<p>
											<input type="file" name="imageFile" title="대표이미지" style="width: 550px;" />
											<!-- 
											<button type="button" class="table_btn mr10"><span>찾아보기</span></button>
											<input type="checkbox" id="size3" title="전체 선택"> <label for="size3">이미지사이즈 자동변환</label>
											-->
										</p>
										<p class="f11 mt5">
											- 대표이미지를 등록하는 경우 아래 이미지들 따로 등록하지 않아도 사이즈에 맞게 자동 변환되어 저장됩니다.<br/>
											- 대표이미지를 등록하거나 이미지 사이즈 자동변환을 체크한 경우 움직이는 이미지(GIF)는 사용이 불가능합니다.<br/>
										</p>
										<p class="f11 mt5">- 추가 이미지 변환사이즈  <input type="text" title="상품별배송" /></p>
										
										<c:if test="${!empty item.itemImage}">
											<p class="item_image_main">
												<img src="${item.imageSrc}" class="item_image size-100" alt="" />
												<a href="javascript:deleteItemImage('main', ${itemId});" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
											</p>
										</c:if>
									
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label"><p>상세이미지</p><p>(340픽셀*340픽셀)</p></td>
								<td>
									<div>
									
										<button type="button" id="add_detail_image_file" style="display:none" class="table_btn"><span>+ 파일추가</span></button>
										<p>
											<input type="file" name="detailImageFiles[]" multiple="multiple" />
										</p>
										
										<div id="multiple_files">
										
										</div>
										
										<ul id="item_details_images" class="sortable_item_image clear">
											<c:forEach items="${item.itemImages}" var="itemImage" varStatus="i">
												<c:if test="${itemImage.itemImageId != 0}">
													<li id="item_image_id_${itemImage.itemImageId}">
														<img src="${itemImage.imageSrc}" class="item_image size-100" alt="" />
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
			
			
			<div class="item_list mt60">
				<h3><span>관련상품등록</span></h3>
				
				<div class="board_write">
						
					<table class="board_write_table" summary="관련상품등록">
						<caption>관련상품등록</caption>
						<colgroup>
							<col style="width: 150px;" />
							<col style="" />
						</colgroup>
						<tbody>
							<tr>
								<td class="label">관련상품출력방법</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="relationItemDisplayType" value="1" label="관련상품을 해당카테고리에서 임의로 출력합니다." /> 
											<form:radiobutton path="relationItemDisplayType" value="2" label="관련상품을 선택합니다." /> 
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
								<td class="label">관련상품</td>
								<td>
									<div>
										<p class="mb10">
											<button type="button" id="button_add_relation_item" class="table_btn" onclick="Shop.findItem('related')"><span>상품 추가</span></button>
											<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll('related')"><span>전체삭제</span></button>
										</p>
										
										<ul id="related" class="sortable_item_relation">
											<li style="display: none;"></li>
											
											<c:forEach items="${item.itemRelations}" var="itemRelation" varStatus="i">
												<c:if test="${!empty itemRelation.item.itemId}">
													<li id="related_item_${itemRelation.item.itemId}">
														<input type="hidden" name="relatedItemIds" value="${itemRelation.relatedItemId}" />
														<p class="image"><img src="${itemRelation.item.imageSrc}" class="item_image size-100 none" alt="상품이미지" /></p>
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
			
			
			<div class="item_list mt60">
				<h3><span>상품 상세 설명</span></h3>
							
				<form:textarea path="detailContent" cols="30" rows="20" style="width: 100%" class="" title="상품상세설명" />
			</div>
			
			
			<div class="item_list mt30">
				<h3><span>상품 상세 설명 (모바일)</span></h3>
							
				<form:textarea path="detailContentMobile" cols="30" rows="6" style="width: 100%" title="모바일 상품상세설명" />
			</div>
			
			<div class="item_list mt30">
				<h3><span>사용방법</span></h3>
							
				<form:textarea path="useManual" cols="30" rows="3" style="width: 100%" title="사용방법" />
			</div>
			
			<div class="item_list mt30">
				<h3><span>조립방법</span></h3>
							
				<form:textarea path="makeManual" cols="30" rows="3" style="width: 100%" title="조립방법" />
			</div>
			
			<div class="item_list mt60">
				<h3>
					<span>SEO 설정</span>
					<span class="f12"></span>
				</h3>
				
				<div class="board_write">
						
					<table class="board_write_table">
						<colgroup>
							<col style="width: 150px;">
							<col style="">
						</colgroup>
						<tbody>
							<tr>
								<td class="label">NO INDEX</td>
								<td>
									<div>
										<p>
											<form:radiobutton path="seo.noIndexDisplayFlag" value="Y" label="노출" />
											<form:radiobutton path="seo.noIndexDisplayFlag" value="N" label="노출안함" />
										</p>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">브라우저제목</td>
								<td>
									<div>
										<form:input path="seo.title" class="eight" title="브라우저제목" />
										<span>(100자/한글50자)</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">Meta 키워드</td>
								<td>
									<div>
										<form:input path="seo.keywords" class="eight" title="Meta 키워드" />
										<span>(최대 200 자 / 한글 100 자)</span>
										<p class="f11 ml0">
											※ &lt;meta name ='keywords' content=''&gt; 태그 내에 기술 
										</p>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">Meta용 기술서</td>
								<td>
									<div>
										<form:textarea path="seo.description" class="full" rows="5" title="Meta용 기술서" />
										<span>(최대 200 자 / 한글 100 자)</span>
										<p class="f11 ml0">
											※ &lt;meta name ='keywords' content=''&gt; 태그 내에 기술 
										</p>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="label">H1태크</td>
								<td>
									<div>
										<p>
											<form:input path="seo.headerContents1" maxlength="100" title="H1태그" class="eight" />
											<p>※ 페이지 상단의   &lt;H1&gt; 태그에 배포됩니다. </p>
										</p>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">테마워드용 타이틀</td>
								<td>
									<div>
										<p>
											<form:input path="seo.themawordTitle" title="테마워드용 타이틀" class="eight" />
											<button type="button" class="copy_item_name table_btn"><span>상품명 복사</span></button>
										</p>
										<p>※ HTML 태그도 사용 가능하지만, 속성 값은 "(큰 따옴표)로 묶어야합니다.</p>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label">테마 워드용 기술서</td>
								<td>
									<div>
										<p>
											<form:textarea path="seo.themawordDescription" cols="30" rows="3" title="테마 워드용 기술서" />
										</p>
										<span class="f11 ml0">
											※ HTML 태그도 사용 가능하지만, 속성 값은 "(큰 따옴표)로 묶어야합니다.
										</span>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
							
				</div> <!-- // board_write -->					
			</div> <!-- // item_list02 -->
			
			<div class="tex_c mt20">
				<button type="submit" class="btn btn-active">등록</button>
				<a href="/opmanager/item/list" class="btn btn-default">취소</a>
			</div>
				
		</form:form>
		
		

		
<table id="item_point_template" style="display:none">
	<tbody>
		<tr>
			<td>
				<input type="text" name="point" maxlength="4" class="three required _number" title="${op:message('M00246')}" />
				<input type="hidden" name="pointType" />
				{INPUT_RADIO}
			</td>
			<td>
				<span class="datepicker"><input type="text" name="pointStartDate" class="term required _date" maxlength="8" title="시작일"></span>
				<select name="pointStartTime" title="시간 선택">
					<c:forEach items="${hours}" var="code">
						<option value="${code.value}">${code.label}</option> 
					</c:forEach>
				</select> 시 ~
				<span class="datepicker"><input type="text" name="pointEndDate" class="term required _date" maxlength="8" title="종료일"></span>
				<select name="pointEndTime" title="시간 선택">
					<c:forEach items="${hours}" var="code">
						<option value="${code.value}">${code.label}</option> 
					</c:forEach>
				</select> 시
			</td>
			<td>
				<input type="text" name="pointRepeatDay" maxlength="2" class="optional _number" style="width: 25px;" title="일" /> 일
			</td>
			
			<td><a href="#" class="fix_btn delete_item_point">삭제</a></td>
		</tr>
	</tbody>
</table>


<table id="item_option_template" style="display:none">
	<tbody>
		<tr>
			<td>
				<input type="text" name="option_title" class="full required_item_option" maxlength="100" title="옵션명" /><br /><br />
				<a href="#" class="fix_btn add_sub_option">항목추가</a>
			</td>
			<td class="item_sub_option_area">
				<div>
					<span class="option_display">숨김</span>
					<span class="option_name">옵션명</span>
					<span class="option_code">상품번호</span>
					<span class="option_stock_quantity">재고</span>
					<span class="option_price">회원가격</span>
					<span class="option_price_nonmember">비회원가격</span>
					<span class="option_stock_schedule_text">옵션텍스트</span>
					<span class="option_stock_schedule_date">입하예정일</span>
					<span class="option_delete"></span>
				</div>
				<div>
					<input type="hidden" name="optionName1" />
					<span class="option_display"><input type="checkbox" name="optionDisplayFlagCheck" /><input type="hidden" name="optionDisplayFlag" /></span>
					<span class="option_name"><input type="text" name="optionName2" class="required_item_option full" title="옵션항목명" /></span>
					<span class="option_code"><input type="text" name="optionCode" class="required_item_option full" title="상품코드" /></span>
					<span class="option_stock_quantity"><input type="text" name="optionStockQuantity" class="full" title="재고" /></span>
					<span class="option_price"><input type="text" name="optionPrice" class="full" title="회원가격" /></span>
					<span class="option_price_nonmember"><input type="text" name="optionPriceNonmemer" class="full" title="비회원가격" /></span>
					<span class="option_stock_schedule_text"><input type="text" name="optionStockScheduleText" class="full" title="입하예정일 텍스" /></span>
					<span class="option_stock_schedule_date"><input type="text" name="optionStockScheduleDate" class="full" title="입하예정일" /></span>
					<span class="option_delete"><a href="" class="delete_sub_option">삭제</a></span>
				</div>
		
			</td>
			<td><a href="#" class="fix_btn delete_item_option">삭제</a></td>
		</tr>
	</tbody>
</table>



<module:smarteditorInit />
<module:smarteditor id="detailContent" />		
<module:smarteditor id="detailContentMobile" />		
<module:smarteditor id="useManual" />		
<module:smarteditor id="makeManual" />		


<script type="text/javascript">



$(function() {

	//팀/그룹 ~ 4차 카테고리 이벤트
	ShopEventHandler.categorySelectboxChagneEvent();  
	
	
	// datepicer
	var $item = $('#item');
	$item.find('.ui-datepicker-trigger').remove();
	$item.find("input.term").each(function() {
		$(this).removeClass('hasDatepicker').datepicker(); 
	});


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
	
	// 상세이미지 드레그
	$('.sortable_item_image').sortable({
		placeholder: "sortable_item_image_placeholder"
	});
	 
	// 관련상품 드레그
    $(".sortable_item_relation").sortable({
        placeholder: "sortable_item_relation_placeholder"
    });
	
    $(".sortable_item_image, .sortable_item_relation").disableSelection(); 
	
	      
	$('#itemUserCode').on("change", function() {
		$('#useItemUserCode').val("N");
	});
	
	// 배송비정책
	setDeliveryOption();
	$('input[name=itemType]').on("click", function(){
		$('#deliveryCharge').val('');
		$('#deliveryId').val('');
		$('#deliveryChargeId').val('');
		setDeliveryOption();
	});
	
	// 배송비 선택 (셀렉트박스)
	$('#deliveryCharge').on("change", function(){
		var deliveryInfo = $(this).val();
		var $deliveryId = $('#deliveryId');
		var $deliveryChargeId = $('#deliveryChargeId');
		
		if (deliveryInfo == '') {
			$deliveryId.val('');
			$deliveryChargeId.val('');
			
		} else {
			var deliveryInfoArray = deliveryInfo.split('_');
			$deliveryId.val(deliveryInfoArray[0]);
			$deliveryChargeId.val(deliveryInfoArray[1]);
			
		}
	});
	
	
	// option 추가
	
	// 포인트 설정 정보 노출 여부 체크
	displayItemPointConfig();
	

	// 포인트 설정 삭제
	$('#item_point_area').on('click', '.delete_item_point', function(e){
		e.preventDefault();

		$(this).closest("tr").remove();
		displayItemPointConfig();
	});
	
	
	
	// 상품옵션
	setItemOptionDisplay();
	$('input[name=itemOptionFlag]').on('click', function() {
		setItemOptionDisplay();
	});
	
	// 상품 옵션 - 항목추가
	$('#item_option_area').on('click', '.add_sub_option', function(e){
		e.preventDefault();
		
		var optionTemplate = '<div>' + $('#item_option_template div').eq(1).html() + '</div>';
		$(this).closest("tr").find('.item_sub_option_area').append(optionTemplate);

	});
	
	// 상품 옵션 삭제	
	$('#item_option_area').on('click', '.delete_item_option', function(e){
		e.preventDefault();
		
		$(this).closest("tr").remove();
		
		var optionCount = $('#item_option_area tbody tr').size();
		if (optionCount == 0) {
			$('#itemOptionFlag1').prop("checked", true);
			$('#item_option_area, .add_item_option').hide();
		}
	});
	
	
	// 상품 옵션 항목 삭제	
	$('#item_option_area').on('click', '.delete_sub_option', function(e){
		e.preventDefault();
		$(this).closest("div").remove();
	});
	
	
	// 관련상품 선택방법 
	setRelationItemDisplay();
	$('input[name=relationItemDisplayType]').on('click', function() {
		setRelationItemDisplay();
	});
	
	
	// 상품코드 중복체크
	$('#btn_check_duplicate').on("click", function(e){
		e.preventDefault();

		var $itemUserCode = $('#itemUserCode');
		if (!$.validator.required($itemUserCode)) {
			return false;
		}
		

		$.post("/opmanager/item/check-for-duplicate-item-user-code", {'itemUserCode': $itemUserCode.val()}, function(response) {
			Common.responseHandler(response, function(response) {
				$('#useItemUserCode').val("Y");
				alert("사용가능.");
				
			}, function(response){
				$('#useItemUserCode').val("N");
				alert("사용불가.");
				$('#itemUserCode').val("").focus();
				
			});
			
		}, 'json');

	});
	
	
	// 상품명 복사
	$('.copy_item_name').on('click', function() {
		$(this).prev().val($('#itemName').val());
	});
	
	
	// 폼체크
	$("#item").validator({
			'requiredClass' : 'required, required_item_option',
			'submitHandler' : function() {

				
				
				if ($('input[name=categoryIds]').size() == 0) {
					alert(Message.get("M00078"));	// 상품 카테고리를 선택해 주세요.
					$('#categoryGroupId').focus();
					return false;
				}
				

				if ($('#useItemUserCode').val() != "Y") {
					alert(Message.get("M00379"));		// 상품코드 중복 여부가 체크되지 않았습니다.
					$('#itemUserCode').focus();
					return false;
					
				}
				
				// 비회원 가격 
				var $salePriceNonmember = $('#salePriceNonmember');
				if ($('input[name=nonmemberOrderType]:checked').val() == '1' &&
						($.trim($salePriceNonmember.val()) == '' || $salePriceNonmember.val() == '0')) {
					$.validator.validatorAlert($.validator.messages['text'].format($salePriceNonmember.attr('title')), $salePriceNonmember);
					$salePriceNonmember.focus();
					return false;
				}
	
				
				
				// 포인트 처리
				$('#item_point_area tr').each(function(){
					var pointType = $(this).find('input[type=radio]').eq(0).prop('checked') == true ? "1" : "2"; 
					$(this).find('input[name=pointType]').val(pointType);
				});
				

				
				// 옵션상품
				var itemOptionFlag = $('input[name=itemOptionFlag]:checked').val();
				
				if (itemOptionFlag == 'Y') {
					$('#item input[name=optionName1]').each(function(index) {
						if ($('#item input[name=optionDisplayFlagCheck]').eq(index).prop("checked")) {
							$('#item input[name=optionDisplayFlag]').eq(index).val("N");
							
						} else {
							$('#item input[name=optionDisplayFlag]').eq(index).val("Y");
							
						}

						$(this).val($(this).closest('tr').find('input[name=option_title]').val());
					});
				}
				
				
				
				// 관련상품
				var relationItemDisplayType = $('input[name=relationItemDisplayType]:checked').val();
				if (relationItemDisplayType == 2) {
					if ($('input[name=relatedItemIds').size() == 0) {
						alert(Message.get("M00080"));	// 관련상품을 추가해 주세요.
						$('#button_add_relation_item').focus();
						return false;
					}
				}
				
				
				

				
				// 에디터 내용 검증 (내용 입력 여부 / 필터)
				if ($.validator.validateEditor(editors, "detailContent") == false) return false;
				
				// 에디터 내용 설정.
				Common.getEditorContent("detailContentMobile");
				Common.getEditorContent("useManual");
				Common.getEditorContent("makeManual");
				
			}
				
		});
	
	$( window ).scroll(function() {
	
		setHeight();
	});
});


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
	
}

function deleteItemCategory(categoryId) {
	$('#item_category_' + categoryId).remove();
	
	var $itemCategories = $('#item_categories');
	if ($itemCategories.find('li').size() == 0) {
		$itemCategories.append('<li>' + Message.get("M00077") + '</li>'); // 등록된 상품 카테고리가 없습니다.
	}
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
	radioButtons += '<input type="radio" name="R' + randomKey + '" id="R' + randomKey + '2"> <label for="R' + randomKey + '2">원</label>';
	
	
	$('#item_point_area').append(optionTemplate.replace('{INPUT_RADIO}', radioButtons));
	displayItemPointConfig();
	
	var $item = $('#item');
	$item.find('.ui-datepicker-trigger').remove();
	$item.find("input.term").each(function() {
		$(this).removeClass('hasDatepicker').datepicker(); 
	});
}

// 옵션추가
function addItemOption() {
	var optionTemplate = $('#item_option_template tbody').html();
	$('#option_data').hide();
	$('#item_option_area tbody').append(optionTemplate);
}

function setItemOptionDisplay() {
	var itemOptionFlag = $('input[name=itemOptionFlag]:checked').val();
	if (itemOptionFlag == 'N') {
		$('.add_item_option').hide();
		$('#item_option_area').hide();
		
		
		$.validator.currentClass = '';
	} else {
		if ($('#item_option_area tbody tr').size() == 0) {
			addItemOption();								
		}
		$('.add_item_option').show();
		$('#item_option_area').show();
		
		$.validator.currentClass = 'required_item_option';
		
	}
}

function setDeliveryOption() {
	var itemTypeValue = $('input[name=itemType]:checked').val();
	if (itemTypeValue == '3') {
		$('.mail_Y').prop("disabled", false);
		$('.mail_N').prop("disabled", true);
		
	} else {
		$('.mail_Y').prop("disabled", true);
		$('.mail_N').prop("disabled", false);
		
	}
}

function setRelationItemDisplay() {
	var relationItemDisplayType = $('input[name=relationItemDisplayType]:checked').val();
	if (relationItemDisplayType == '1') {
		$('#relation_item_area').hide();
		
	} else {
		$('#relation_item_area').show();
		
	}
	
}

// 상품이미지 삭제 
function deleteItemImage(type, id) {
	if (!confirm('이미지가 실제로 삭제됩니다.(복구불가)\n삭제하시겠습니까?')) {
		return;
	} 
	
	
	if (type == "main") {
		var param = {'itemId': id};
		$.post('/opmanager/item/delete-item-image', param, function(response){
			Common.responseHandler(response);
			$('.item_image_main').remove();
		});	
	} else if (type == "details") {
		var param = {'itemImageId': id};
		$.post('/opmanager/item/delete-item-details-image', param, function(response){
			Common.responseHandler(response);
			$('#item_image_id_' + param.itemImageId).remove();
		});	
	}
	
}

</script>